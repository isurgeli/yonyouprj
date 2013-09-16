package nc.bs.sc.settle;

import java.sql.SQLException;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import javax.naming.NamingException;

import nc.bs.dao.BaseDAO;
import nc.bs.dao.DAOException;
import nc.bs.dbcache.intf.IDBCacheBS;
import nc.bs.framework.common.NCLocator;
import nc.bs.ml.NCLangResOnserver;
import nc.bs.pub.SystemException;
import nc.bs.pub.para.SysInitBO;
import nc.bs.sc.pub.PublicDMO;
import nc.bs.scm.pub.ScmPubDMO;
import nc.bs.uap.lock.PKLock;
import nc.itf.ic.service.IICPub_GeneralBillDMO;
import nc.itf.pu.inter.IPuToSc_EstimateBO;
import nc.itf.sc.IMateriallledger;
import nc.itf.sc.IPublic;
import nc.itf.uap.IUAPQueryBS;
import nc.vo.bd.b15.GroupInventoryVO;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ps.estimate.EstimateVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.para.SysInitVO;
import nc.vo.sc.settle.BalanceVO;
import nc.vo.sc.settle.MaterialHeaderVO;
import nc.vo.sc.settle.MaterialledgerVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.session.ClientLink;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

/**
 * “材料核销”的后台实现类，提供查询核销记录、保存核销、保存反核销等功能
 * 
 * 创建日期：(2001-7-10)
 */
public class MaterialledgerImpl implements IMateriallledger {
	/**
	 * MaterialledgerImpl 构造子注解。
	 */
	public MaterialledgerImpl() {
		super();
	}

	/**
	 * 功能：获得入库单表体(根据表头ID)
	 * 
	 * @param key
	 *            表头ID数组 创建日期：(2001-7-4)
	 */
	public GeneralBillItemVO[] findGeneralBody(String key)
			throws BusinessException {
		GeneralBillItemVO[] generalItemVOs = null;
		try {
			IICPub_GeneralBillDMO dmo = (IICPub_GeneralBillDMO) NCLocator
					.getInstance()
					.lookup(IICPub_GeneralBillDMO.class.getName());
			generalItemVOs = (GeneralBillItemVO[]) dmo
					.queryItemDataByBillPk(key);

			/*
			 * 委外加工特殊约定：利用GeneralBillItemVO的nplanedmny代表材料费,
			 * GeneralBillItemVO的nplanedprice代表暂估单价
			 */
			if (generalItemVOs != null) {
				MaterialledgerDMO dmo1 = new MaterialledgerDMO();

				// 批量处理取材料费/暂估单价
				String[] ids = new String[generalItemVOs.length];
				for (int i = 0; i < generalItemVOs.length; i++) {
					ids[i] = generalItemVOs[i].getCgeneralbid();
				}
				Hashtable table = dmo1.queryGeneral_bb3s(ids);

				for (int i = 0; i < generalItemVOs.length; i++) {
					String cgeneralbid = generalItemVOs[i].getCgeneralbid();
					// Object[] returnObj = dmo1.queryGeneral_bb3(cgeneralbid);
					if (table.containsKey(cgeneralbid)) {
						Object[] returnObj = (Object[]) table.get(cgeneralbid);
						// 暂估价格
						if (returnObj[0] == null)
							generalItemVOs[i].setNplannedprice(null);
						else
							generalItemVOs[i].setNplannedprice(new UFDouble(""
									+ returnObj[0]));

						// 材料费
						if (returnObj[1] == null)
							generalItemVOs[i].setNplannedmny(null);
						else
							generalItemVOs[i].setNplannedmny(new UFDouble(""
									+ returnObj[1]));
					}
				}
			}
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return generalItemVOs;
	}

	/**
	 * 功能：根据入库单的表体行ID，获得对应的累计结算表ID
	 * 
	 * @param cgeneralbid
	 *            表体行ID数组 创建日期：(2001-7-4)
	 */
	public String[] getIc_bb3info(String cgeneralbid[])
			throws BusinessException {
		String[] obj = null;
		try {
			MaterialledgerDMO dmo = new MaterialledgerDMO();
			obj = dmo.getIc_bb3info(cgeneralbid);
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return obj;
	}

	/**
	 * 功能：向数据库中插入一批VO对象,保存核销时调用
	 * 
	 * @param materialledgers
	 *            材料核销VO数组
	 * @param estimateVOs
	 *            用于暂估的VO数组
	 * @param paramList
	 *            依次存放：操作员ID、当前日期、入库单头部ts 创建日期：(2001-7-4)
	 */
	public String[] insertArray(MaterialledgerVO[] materialledgers,
			EstimateVO[] estimateVOs, List paramList) throws BusinessException {
		if (paramList == null || paramList.size() != 3)
			throw new BusinessException(NCLangResOnserver.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000028")/*
																	 * @res
																	 * "参数错误"
																	 */);

		// 操作员ID、当前日期、入库单头部ts
		String coperatorid = (String) paramList.get(0);
		UFDate curDate = (UFDate) paramList.get(1);
		String generalHeaderTS = (String) paramList.get(2);

		boolean bLock = false;
		String generalKeys[] = null;
		try {
			// 对单据加锁
			Vector<String> vTemp = new Vector<String>();
			materialledgers[0].setDbilldate(curDate);
			vTemp.addElement(materialledgers[0].getCbillid());
			vTemp.addElement(materialledgers[0].getCbill_bid());
			for (int i = 1; i < materialledgers.length; i++) {
				// 委外材料明细帐按委托加工入库单的核销日期记账.since v5.01
				materialledgers[i].setDbilldate(curDate);
				if (!vTemp.contains(materialledgers[i].getCbillid()))
					vTemp.addElement(materialledgers[i].getCbillid());
				if (!vTemp.contains(materialledgers[i].getCbill_bid()))
					vTemp.addElement(materialledgers[i].getCbill_bid());
			}
			generalKeys = new String[vTemp.size()];
			vTemp.copyInto(generalKeys);

			bLock = PKLock.getInstance().acquireBatchLock(generalKeys,
					coperatorid, PublicDMO.getDsName());
			if (!bLock)
				throw new nc.vo.pub.BusinessException(
						nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000316")/*
																	 * @res
																	 * "正在进行相关操作，请稍后再试"
																	 */);

			// 材料明细帐DMO
			MaterialledgerDMO dmo = new MaterialledgerDMO();

			// 检查入库单的ts、状态
			this.checkTsAndBillStatus(materialledgers, generalHeaderTS, dmo);

			// 检查是否补单
			for (MaterialledgerVO vo : materialledgers)
				dmo.checkMaterialLedgerDate(vo, curDate);

			// 第一步、 维护委外加工材料明细帐结存表
			BalanceVO[] balanceVOs = trimToBalanceVO(materialledgers, true);
			BalanceDMO dmo2 = new BalanceDMO();
			dmo2.updateBalanceDatas(balanceVOs, new UFBoolean("N"));

			// 第二步、 再成本计算，否则存货核算成本计算数据会错误
			IPuToSc_EstimateBO esti = (IPuToSc_EstimateBO) NCLocator
					.getInstance().lookup(IPuToSc_EstimateBO.class.getName());
			esti.estimateForSc(estimateVOs, coperatorid, curDate);

			// 第三步、材料明细帐是否允许负结存
			this.checkBalanceNum(balanceVOs, dmo2);

			// 第四步、保存原材料明细
			String[] keys = dmo.insertArray(materialledgers);

			//第五步、gc 回写订单料比页
			reWriteDDLB(materialledgers, true);
			return keys;
		} catch (Exception e) {
			SCMEnv.out(e);
			PublicDMO.throwBusinessException(e);
		} finally {
			// 对单据解锁
			if (bLock)
				PKLock.getInstance().releaseBatchLock(generalKeys, coperatorid,
						PublicDMO.getDsName());
		}
		return null;
	}

	/**
	 * 功能：检查材料明细帐是否允许负结存
	 * 
	 * @param balanceVOs
	 *            结存VO数组
	 * @param balanceDMO
	 *            结存DMO
	 * @author hanbin 2009-11-9
	 * @throws Exception 
	 * @throws NamingException 
	 * @throws SystemException 
	 */
	private void checkBalanceNum(BalanceVO[] balanceVOs, BalanceDMO balanceDMO)
			throws Exception {

		// 读取“是否允许负结存”参数
		SysInitBO sysInitService = new SysInitBO();
		SysInitVO initVO[] = sysInitService.querySysInit(balanceVOs[0]
				.getPk_corp(), "SC06");
		if (initVO == null || initVO.length == 0)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000075"));// 无法获取委外参数SC06
		String sCross = initVO[0].getValue(); // 是否忽略差异

		// 如果参数为空，或者允许存在负结存，则直接返回
		if (StringUtils.isBlank(sCross) || !"否"/*-=notranslate=-*/.equals(sCross))
			return;

		// 循环检查是否存在负结存的记录
		for (int i = 0; i < balanceVOs.length; i++) {
			BalanceVO tempVO[] = balanceDMO.queryByVONormally(balanceVOs[i],
					new Boolean(true));

			// 查询结果为空，跳过本次循环
			if (ArrayUtils.isEmpty(tempVO))
				continue;

			for (int j = 0; j < tempVO.length; j++) {
				UFDouble nBalanceNum = tempVO[j].getNbalancenum();
				// 材料明细帐结存数量不能为负

				Object[][] invo = null;
				if (nBalanceNum != null && nBalanceNum.doubleValue() < 0) {// ncm_wanghfa_橡果国际_编码和名称
                       invo= new ScmPubDMO().queryArrayValue("bd_invbasdoc", "pk_invbasdoc", new String[] { "invcode", "invname" },
                    		   new String[]{tempVO[j].getCmaterialbaseid()}, "");
                       if(null==invo || invo.length==0 || null==invo[0] || invo[0].length<2)
                    	   invo=new String[][]{{"","","",""}};
					Object[] tempvo = (Object[]) invo[0];
					String message = "(" + tempvo[0] + ":"
							+ tempvo[1] + ")";
					throw new nc.vo.pub.BusinessException(message
							+ nc.bs.ml.NCLangResOnserver.getInstance()
									.getStrByID("40120001",
											"UPP40120001-000076")/*
																 * @res
																 * "材料明细帐结存数量不能为负"
																 */);
				}
			}

		}
	}

	/**
	 * 功能：根据VO中所设定的条件返回所有符合条件的VO数组
	 * 
	 * @param condMaterialledgerVOs
	 *            nc.vo.sc.settle.MaterialledgerVO
	 * @param isAnd
	 *            boolean 以与条件查询还是以或条件查询 创建日期：(2001-7-10)
	 */
	public Vector queryByVOs(MaterialledgerVO[] condMaterialledgerVOs,
			Boolean[] isAnds) throws BusinessException {
		Vector v = new Vector();
		try {
			MaterialledgerDMO dmo = new MaterialledgerDMO();
			v = dmo.queryByVOs(condMaterialledgerVOs, isAnds);
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return v;
	}

	/**
	 * 功能：获得材料明细帐结存单价
	 * 
	 * @param condBalanceVO
	 * @return nc.vo.sc.settle.MaterialledgerVO 创建日期：(2001-7-10)
	 */
	public BalanceVO queryPrice(BalanceVO condBalanceVO)
			throws BusinessException {
		BalanceVO balanceVO = null;
		try {
			BalanceDMO dmo = new BalanceDMO();
			balanceVO = dmo.queryPrice(condBalanceVO);
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return balanceVO;
	}

	/**
	 * 获得材料明细帐结存单价 创建日期：(2001-10-11 10:11:25)
	 * 
	 * @return BalanceVO[]
	 * @param MaterialledgerVO
	 *            []
	 */
	public BalanceVO[] queryPriceBatch(BalanceVO condBalanceVO[])
			throws BusinessException {

		Vector<BalanceVO> vTemp = new Vector<BalanceVO>();
		try {
			BalanceDMO dmo = new BalanceDMO();
			for (int i = 0; i < condBalanceVO.length; i++) {
				BalanceVO balanceVO = dmo.queryPrice(condBalanceVO[i]);
				vTemp.addElement(balanceVO);
			}
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		if (vTemp.size() > 0) {
			BalanceVO balanceVO[] = new BalanceVO[vTemp.size()];
			vTemp.copyInto(balanceVO);
			return balanceVO;
		}
		return null;
	}

	/**
	 * 材料核销VO转换为结存表VO;材料核销修改、反核销时需要考虑修改前的数量、金额，新增、作废不需考虑 创建日期：(2001-10-11
	 * 10:11:25)
	 * 
	 * @return BalanceVO[]
	 * @param MaterialledgerVO
	 *            []
	 */
	public BalanceVO[] trimToBalanceVO(MaterialledgerVO[] itemVOs,
			boolean isModify) throws BusinessException {
		if (itemVOs == null)
			return null;

		BalanceDMO dmo2 = null;
		try {
			dmo2 = new BalanceDMO();

			int count = itemVOs.length;
			BalanceVO[] newItemVOs = new BalanceVO[count];
			for (int i = 0; i < count; i++) {
				BalanceVO oneVO = new BalanceVO();

				oneVO.setPk_corp(itemVOs[i].getPk_corp());// 公司
				// 供应商
				oneVO.setCvendorid(itemVOs[i].getCvendorid());
				oneVO.setCvendormangid(itemVOs[i].getCvendormangid());
				// 加工品档案
				oneVO.setCprocessbaseid(itemVOs[i].getCprocessbaseid());
				oneVO.setCprocessmangid(itemVOs[i].getCprocessmangid());
				// 原材料档案
				oneVO.setCmaterialbaseid(itemVOs[i].getCmaterialbaseid());
				oneVO.setCmaterialmangid(itemVOs[i].getCmaterialmangid());
				// 自由项
				oneVO.setVfree1(itemVOs[i].getVfree1());
				oneVO.setVfree2(itemVOs[i].getVfree2());
				oneVO.setVfree3(itemVOs[i].getVfree3());
				oneVO.setVfree4(itemVOs[i].getVfree4());
				oneVO.setVfree5(itemVOs[i].getVfree5());
				oneVO.setVbatch(itemVOs[i].getVbatch());

				// 处理数量、金额
				double num = 0.0;
				double money = 0.0;
				if (itemVOs[i].getNnum() != null) {
					num = itemVOs[i].getNnum().doubleValue();
				}
				if (itemVOs[i].getNmny() != null) {
					money = itemVOs[i].getNmny().doubleValue();
				}
				if (isModify) {
					switch (itemVOs[i].getStatus()) {
					case VOStatus.NEW:
						if ("1".equals(itemVOs[i].getIsourcetype())) {
							String id = dmo2.findPrimaryKey(oneVO);
							if (id == null)
								num = 0 - num;
						}
						break;
					case VOStatus.UPDATED:
						if (itemVOs[i].getNoldnum() != null)
							num = num - itemVOs[i].getNoldnum().doubleValue();
						if (itemVOs[i].getNoldmoney() != null)
							money = money
									- itemVOs[i].getNoldmoney().doubleValue();
						break;
					case VOStatus.DELETED:
						if (itemVOs[i].getNoldnum() != null)
							num = 0 - itemVOs[i].getNoldnum().doubleValue();
						if (itemVOs[i].getNoldmoney() != null)
							money = 0 - itemVOs[i].getNoldmoney().doubleValue();
					}
				}
				// 结存数量
				oneVO.setNbalancenum(new UFDouble(num));
				// 结存金额
				oneVO.setNbalancemny(new UFDouble(money));

				newItemVOs[i] = oneVO;
			}

			return newItemVOs;
		} catch (Exception ex) {
			SCMEnv.out(ex);
			PublicDMO.throwBusinessException(ex);
		}
		return null;
	}

	/**
	 * 功能：用VO对象的属性值更新数据库,保存反核销时调用
	 * 
	 * @param materialledgers
	 *            材料核销VO数组
	 * @param estimateVOs
	 *            用于暂估的VO数组
	 * @param paramList
	 *            依次存放：操作员ID、当前日期、入库单头部ts 创建日期：(2001-7-4)
	 */
	public void updateArray(MaterialledgerVO[] materialledgers,
			EstimateVO[] estimateVOs, List paramList) throws BusinessException {
		if (paramList == null || paramList.size() != 3)
			throw new BusinessException(NCLangResOnserver.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000028")/*
																	 * @res
																	 * "参数错误"
																	 */);

		// 参数：操作员ID、当前日期、入库单头部ts
		String coperatorid = (String) paramList.get(0);
		UFDate curDate = (UFDate) paramList.get(1);
		String generalHeaderTS = (String) paramList.get(2);

		boolean bLock = false;
		String generalKeys[] = null;
		try {
			// 对单据加锁
			Vector<String> vTemp = new Vector<String>();
			materialledgers[0].setDbilldate(curDate);
			vTemp.addElement(materialledgers[0].getCbillid());
			vTemp.addElement(materialledgers[0].getCbill_bid());
			for (int i = 1; i < materialledgers.length; i++) {
				// 委外材料明细帐按委托加工入库单的核销日期记账.since v5.01
				materialledgers[i].setDbilldate(curDate);
				if (!vTemp.contains(materialledgers[i].getCbillid()))
					vTemp.addElement(materialledgers[i].getCbillid());
				if (!vTemp.contains(materialledgers[i].getCbill_bid()))
					vTemp.addElement(materialledgers[i].getCbill_bid());
			}
			generalKeys = new String[vTemp.size()];
			vTemp.copyInto(generalKeys);

			bLock = PKLock.getInstance().acquireBatchLock(generalKeys,
					coperatorid, PublicDMO.getDsName());
			if (!bLock)
				throw new nc.vo.pub.BusinessException(
						nc.bs.ml.NCLangResOnserver.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000316")/*
																	 * @res
																	 * "正在进行相关操作，请稍后再试"
																	 */);

			// 材料明细帐DMO
			MaterialledgerDMO dmo = new MaterialledgerDMO();

			// 检查入库单的ts、状态
			this.checkTsAndBillStatus(materialledgers, generalHeaderTS, dmo);
			// 检查是否补单
			for (MaterialledgerVO vo : materialledgers)
				dmo.checkMaterialLedgerDate(vo, curDate);

			dmo.updateArray(materialledgers);

			// 暂估处理（反暂估）
			IPuToSc_EstimateBO esti = (IPuToSc_EstimateBO) NCLocator
					.getInstance().lookup(IPuToSc_EstimateBO.class.getName());
			esti.antiEstimateForSc(estimateVOs, coperatorid);

			// 维护委外加工材料明细帐结存表
			BalanceVO[] balanceVOs = trimToBalanceVO(materialledgers, true);

			//gc
			reWriteDDLB(materialledgers, false);
			
			BalanceDMO dmo2 = new BalanceDMO();
			dmo2.updateBalanceDatas(balanceVOs, new UFBoolean("N"));
		} catch (Exception e) {
			SCMEnv.out(e);

			PublicDMO.throwBusinessException(e);
		} finally {
			// 对单据解锁
			if (bLock)
				PKLock.getInstance().releaseBatchLock(generalKeys, coperatorid,
						PublicDMO.getDsName());
		}
	}

	/**
	 * 功能：检查入库单的ts、状态 创建日期：(2001-7-4)
	 */
	private void checkTsAndBillStatus(MaterialledgerVO[] materialledgers,
			String generalHeaderTS, MaterialledgerDMO dmo) throws SQLException,
			BusinessException {
		// 加入时间戳判断，以免用户取消签字-》修改-》签字，再核销
		String newTs = dmo.queryGeneralBillhTs(materialledgers[0].getCbillid());
		if (newTs == null || !newTs.equals(generalHeaderTS))
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000143"));// 来源单据已被修改或删除，请检查

		/******************** 检查入库单的状态 *********************/
		Vector queryRet = dmo.queryGeneralBillStatus(materialledgers[0]
				.getCbillid());
		int dr = ((Integer) queryRet.elementAt(0)).intValue();
		if (dr == 1)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000072")/*
																				 * @res
																				 * "入库单已作废"
																				 */);

		String cregister = (String) queryRet.elementAt(1);
		if (cregister == null || cregister.trim().length() == 0)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000073")/*
																				 * @res
																				 * "入库单未签字"
																				 */);

		boolean bSettled = ((UFBoolean) queryRet.elementAt(2)).booleanValue();
		if (bSettled)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000074")/*
																				 * @res
																				 * "入库单已结算"
																				 */);

		// 是否生成采购发票
		boolean isInvoiced = ((UFBoolean) queryRet.elementAt(3)).booleanValue();
		if (isInvoiced)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000079"));// 入库单已生成发票
	}

	/**
	 * 功能：用VO对象的属性值更新数据库。 创建日期：(2001-7-4)
	 */
	public void updateArrayByAdjust(MaterialledgerVO[] materialledgers)
			throws BusinessException {

		try {
			MaterialledgerDMO dmo = new MaterialledgerDMO();
			dmo.updateArrayByAdjust(materialledgers);
		} catch (Exception e) {

			PublicDMO.throwBusinessException(e);
		}
	}

	/**
	 * 功能：根据指定的sql，查询入库单表头VO集合，并返回材料核销表头VO集合
	 * 
	 * @param strWhereSql
	 *            指定的sql
	 * @param cl
	 *            客户端连接信息
	 * @return 材料核销表头VO
	 * @author hanbin
	 */
	public MaterialHeaderVO[] findLightGeneralHead(String strWhereSql,
			ClientLink cl) throws BusinessException {
		// 根据指定的sql，查询入库单表头VO集合
		IPublic bo = (IPublic) NCLocator.getInstance().lookup(
				IPublic.class.getName());
		GeneralBillHeaderVO[] generalHeaderVOs = bo.findGeneralHead(
				strWhereSql, cl);

		// 转换为材料核销表头VO集合，并返回
		return this.convetToMaterialHeadVO(generalHeaderVOs);
	}

	/**
	 * 功能：根据表头key，查询对应的入库单表体，并转换为材料明细MaterialledgerVO集合
	 * 
	 * @param itemVOs
	 *            入库单的表头主键
	 * @return 材料核销的表头VO数组
	 * @author hanbin
	 */
	public MaterialledgerVO[] findLightGeneralItem(String headerKey)
			throws BusinessException {
		// 获得对应入库单的表体VO数组
		GeneralBillItemVO[] generalItemVOs = this.findGeneralBody(headerKey);

		// 转换为材料明细MaterialledgerVO集合，并返回
		return this.convetToMaterialledgerVO(generalItemVOs);
	}

	/**
	 * 功能：将入库单HeadVO数组转换为材料核销HeadVO数组
	 * 
	 * @param itemVOs
	 *            入库单的表头VO数组
	 * @return 材料核销的表头VO数组
	 * @author hanbin
	 */
	private MaterialHeaderVO[] convetToMaterialHeadVO(
			GeneralBillHeaderVO[] headVOs) throws BusinessException {
		if (headVOs == null)
			return null;

		MaterialHeaderVO[] VOs = new MaterialHeaderVO[headVOs.length];
		try {
			PublicDMO dmo = new PublicDMO();
			for (int i = 0; i < headVOs.length; i++) {
				VOs[i] = new MaterialHeaderVO();
				// 入库日期
				VOs[i].setDbilldate(headVOs[i].getDbilldate());
				// 入库单表头ID
				VOs[i].setCbillid(headVOs[i].getCgeneralhid());
				// 入库单号
				VOs[i].setCbillcode(headVOs[i].getVbillcode());
				// 部门
				VOs[i].setCdeptid(headVOs[i].getCdptid());
				// 供应商管理ID
				VOs[i].setCvendormangid(headVOs[i].getCproviderid());
				// 通过供应商管理ID，获得基本ID
				String vendorbaseid = (String) dmo.fetchCellValue(
						"bd_cumandoc", "pk_cubasdoc", "pk_cumandoc", headVOs[i]
								.getCproviderid());
				VOs[i].setCvendorid(vendorbaseid);

				// 公司
				VOs[i].setPk_corp(headVOs[i].getPk_corp());
				// 仓库ID
				VOs[i].setCwarehouseid(headVOs[i].getCwarehouseid());

				// 根据仓库id，获得库存id
				String cwareid = (String) dmo.fetchCellValue("bd_stordoc",
						"pk_calbody", "pk_stordoc", headVOs[i]
								.getCwarehouseid());
				VOs[i].setCwareid(cwareid);
				// 业务员
				VOs[i].setCemployeeid(headVOs[i].getCbizid());
				// 记录ic_general_h中的ts,便于并发控制
				VOs[i].setTs(headVOs[i].getTimeStamp());

				// 自定义项
				VOs[i].setVdef1(headVOs[i].getVuserdef1());
				VOs[i].setVdef2(headVOs[i].getVuserdef2());
				VOs[i].setVdef3(headVOs[i].getVuserdef3());
				VOs[i].setVdef4(headVOs[i].getVuserdef4());
				VOs[i].setVdef5(headVOs[i].getVuserdef5());
				VOs[i].setVdef6(headVOs[i].getVuserdef6());
				VOs[i].setVdef7(headVOs[i].getVuserdef7());
				VOs[i].setVdef8(headVOs[i].getVuserdef8());
				VOs[i].setVdef9(headVOs[i].getVuserdef9());
				VOs[i].setVdef10(headVOs[i].getVuserdef10());

				// 入库单的业务类型，暂估时要使用
				VOs[i].setBiztypeid(headVOs[i].getCbiztypeid());
			}
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return VOs;
	}

	/**
	 * 功能：把入库单的表体VO数组,转化为材料核销的表体VO数组
	 * 
	 * @param itemVOs
	 *            入库单的表体VO数组
	 * @return 材料核销的表体VO数组
	 * @author hanbin
	 */
	private MaterialledgerVO[] convetToMaterialledgerVO(
			GeneralBillItemVO[] itemVOs) throws BusinessException {

		if (itemVOs == null)
			// throw new BusinessException("找不到入库单表体数据，可能已经被删除!");
			return new MaterialledgerVO[0];

		int num = itemVOs.length;
		MaterialledgerVO[] VOs = new MaterialledgerVO[num];
		try {
			PublicDMO dmo = new PublicDMO();
			for (int i = 0; i < num; i++) {
				VOs[i] = new MaterialledgerVO();

				// 修改：材料核销、暂估，生成的存货核算委外加工收货单，没有业务日期；
				VOs[i].setDbizdate(itemVOs[i].getDbizdate());
				// 加工品标识
				VOs[i].setCinvshow(NCLangResOnserver.getInstance().getStrByID(
						"40120003", "UPP40120003-000010"));// 成品
				// 委外订单
				VOs[i].setCorderid(itemVOs[i].getCfirstbillhid());
				VOs[i].setCorder_bid(itemVOs[i].getCfirstbillbid());

				// 来源入库单
				VOs[i].setCbillid(itemVOs[i].getCgeneralhid());
				VOs[i].setCbill_bid(itemVOs[i].getCgeneralbid());

				// 材料管理档案ID
				VOs[i].setCmaterialmangid(itemVOs[i].getCinventoryid());
				// 材料基本档案ID
				String invBaseID = (String) dmo.fetchCellValue("bd_invmandoc",
						"pk_invbasdoc", "pk_invmandoc", itemVOs[i]
								.getCinventoryid());
				VOs[i].setCmaterialbaseid(invBaseID);
				// 加工品数量
				VOs[i].setNnum(itemVOs[i].getNinnum());

				// 加工费
				if (itemVOs[i].getBzgflag() != null
						&& itemVOs[i].getBzgflag().booleanValue())
					VOs[i].setNprocessmny(itemVOs[i].getNplannedprice()); // 如果已经暂估了
				// ，
				// 加工费
				// =
				// “
				// 计划单价
				// ”
				else
					VOs[i].setNprocessmny(itemVOs[i].getNprice()); // 如果没有被暂估，
				// 加工费 =
				// “单价”

				// 材料费
				VOs[i].setNmny(itemVOs[i].getNplannedmny()); // 取自“计划金额”
				// 材料费单价计算 = 材料费 / 加工品数量
				if (VOs[i].getNmny() != null && VOs[i].getNnum() != null
						&& VOs[i].getNnum().doubleValue() != 0)
					VOs[i].setNprice(VOs[i].getNmny().div(VOs[i].getNnum()));
				// 批次号
				VOs[i].setVbatch(itemVOs[i].getVbatchcode());
				// 自由项
				if (itemVOs[i].getFreeItemVO() != null) {
					VOs[i].setVfree1(itemVOs[i].getFreeItemVO().getVfree1());
					VOs[i].setVfree2(itemVOs[i].getFreeItemVO().getVfree2());
					VOs[i].setVfree3(itemVOs[i].getFreeItemVO().getVfree3());
					VOs[i].setVfree4(itemVOs[i].getFreeItemVO().getVfree4());
					VOs[i].setVfree5(itemVOs[i].getFreeItemVO().getVfree5());
				}
				// 自定义项
				VOs[i].setVdef1(itemVOs[i].getVuserdef1());
				VOs[i].setVdef2(itemVOs[i].getVuserdef2());
				VOs[i].setVdef3(itemVOs[i].getVuserdef3());
				VOs[i].setVdef4(itemVOs[i].getVuserdef4());
				VOs[i].setVdef5(itemVOs[i].getVuserdef5());
				VOs[i].setVdef6(itemVOs[i].getVuserdef6());

				// 辅计量单位ID
				VOs[i].setCastunitid(itemVOs[i].getCastunitid());
				// 换算率
				VOs[i].setHsl(itemVOs[i].getHsl());
				// 实入辅数量
				VOs[i].setNinassistnum(itemVOs[i].getNinassistnum());
				// 备注
				VOs[i].setVmemo(itemVOs[i].getVnotebody());
			}
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return VOs;
	}
	/**
	 * 
	 * @param materialledgervo
	 * @throws BusinessException 
	 */
	private void reWriteDDLB(MaterialledgerVO[] materialledgervo,boolean add) throws BusinessException{
		BaseDAO dao = new BaseDAO();
		for (int i = 0; i < materialledgervo.length; i++) {
			MaterialledgerVO materialledgerVO2 = materialledgervo[i];
			String cbillid = materialledgerVO2.getCorderid();
			String cmangid = materialledgerVO2.getCmaterialmangid();
			String vfree1 = materialledgerVO2.getVfree1() ;
			String vfree2 = materialledgerVO2.getVfree2() ;
			String vfree3 = materialledgerVO2.getVfree3() ;
			String vfree4 = materialledgerVO2.getVfree4() ;
			String vfree5 = materialledgerVO2.getVfree5() ;
			UFDouble num = materialledgerVO2.getNnum();
			materialledgerVO2.getNoldnum();
			//如果旧数量和新数量一致，说明修改的是其它字段。
			if(!add)
				num = num.multiply(-1);
			
			String sql = "UPDATE sc_order_ddlb SET  naccumwritenum = COALESCE(naccumwritenum,0.0)+("
					+ num
					+ ") WHERE corderid = '"
					+ cbillid
					+ "'"
					+" and cmangid='"
					+cmangid
					+"'";
			if(vfree1 !=null)
				sql = sql +" and vfree1='"+vfree1+"'";
			else
				sql = sql + " and vfree1 is null ";
			if(vfree2 !=null)
				sql = sql +" and vfree2='"+vfree2+"'";
			else
				sql = sql + " and vfree2 is null ";
			if(vfree3 !=null)
				sql = sql +" and vfree3='"+vfree3+"'";
			else
				sql = sql + " and vfree3 is null ";
			if(vfree4 !=null)
				sql = sql +" and vfree4='"+vfree4+"'";
			else
				sql = sql + " and vfree4 is null ";
			if(vfree5 !=null)
				sql = sql +" and vfree5='"+vfree5+"'";
			else
				sql = sql + " and vfree5 is null ";
			int count = dao.executeUpdate(sql);
			if(count <=0){
				throw new BusinessException("没有找到对应的原料信息，可能是自由项同原料信息不一致");
			}
		}
		
	}
}