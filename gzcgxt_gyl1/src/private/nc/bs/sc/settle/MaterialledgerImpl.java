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
 * �����Ϻ������ĺ�̨ʵ���࣬�ṩ��ѯ������¼��������������淴�����ȹ���
 * 
 * �������ڣ�(2001-7-10)
 */
public class MaterialledgerImpl implements IMateriallledger {
	/**
	 * MaterialledgerImpl ������ע�⡣
	 */
	public MaterialledgerImpl() {
		super();
	}

	/**
	 * ���ܣ������ⵥ����(���ݱ�ͷID)
	 * 
	 * @param key
	 *            ��ͷID���� �������ڣ�(2001-7-4)
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
			 * ί��ӹ�����Լ��������GeneralBillItemVO��nplanedmny������Ϸ�,
			 * GeneralBillItemVO��nplanedprice�����ݹ�����
			 */
			if (generalItemVOs != null) {
				MaterialledgerDMO dmo1 = new MaterialledgerDMO();

				// ��������ȡ���Ϸ�/�ݹ�����
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
						// �ݹ��۸�
						if (returnObj[0] == null)
							generalItemVOs[i].setNplannedprice(null);
						else
							generalItemVOs[i].setNplannedprice(new UFDouble(""
									+ returnObj[0]));

						// ���Ϸ�
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
	 * ���ܣ�������ⵥ�ı�����ID����ö�Ӧ���ۼƽ����ID
	 * 
	 * @param cgeneralbid
	 *            ������ID���� �������ڣ�(2001-7-4)
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
	 * ���ܣ������ݿ��в���һ��VO����,�������ʱ����
	 * 
	 * @param materialledgers
	 *            ���Ϻ���VO����
	 * @param estimateVOs
	 *            �����ݹ���VO����
	 * @param paramList
	 *            ���δ�ţ�����ԱID����ǰ���ڡ���ⵥͷ��ts �������ڣ�(2001-7-4)
	 */
	public String[] insertArray(MaterialledgerVO[] materialledgers,
			EstimateVO[] estimateVOs, List paramList) throws BusinessException {
		if (paramList == null || paramList.size() != 3)
			throw new BusinessException(NCLangResOnserver.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000028")/*
																	 * @res
																	 * "��������"
																	 */);

		// ����ԱID����ǰ���ڡ���ⵥͷ��ts
		String coperatorid = (String) paramList.get(0);
		UFDate curDate = (UFDate) paramList.get(1);
		String generalHeaderTS = (String) paramList.get(2);

		boolean bLock = false;
		String generalKeys[] = null;
		try {
			// �Ե��ݼ���
			Vector<String> vTemp = new Vector<String>();
			materialledgers[0].setDbilldate(curDate);
			vTemp.addElement(materialledgers[0].getCbillid());
			vTemp.addElement(materialledgers[0].getCbill_bid());
			for (int i = 1; i < materialledgers.length; i++) {
				// ί�������ϸ�ʰ�ί�мӹ���ⵥ�ĺ������ڼ���.since v5.01
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
																	 * "���ڽ�����ز��������Ժ�����"
																	 */);

			// ������ϸ��DMO
			MaterialledgerDMO dmo = new MaterialledgerDMO();

			// �����ⵥ��ts��״̬
			this.checkTsAndBillStatus(materialledgers, generalHeaderTS, dmo);

			// ����Ƿ񲹵�
			for (MaterialledgerVO vo : materialledgers)
				dmo.checkMaterialLedgerDate(vo, curDate);

			// ��һ���� ά��ί��ӹ�������ϸ�ʽ���
			BalanceVO[] balanceVOs = trimToBalanceVO(materialledgers, true);
			BalanceDMO dmo2 = new BalanceDMO();
			dmo2.updateBalanceDatas(balanceVOs, new UFBoolean("N"));

			// �ڶ����� �ٳɱ����㣬����������ɱ��������ݻ����
			IPuToSc_EstimateBO esti = (IPuToSc_EstimateBO) NCLocator
					.getInstance().lookup(IPuToSc_EstimateBO.class.getName());
			esti.estimateForSc(estimateVOs, coperatorid, curDate);

			// ��������������ϸ���Ƿ��������
			this.checkBalanceNum(balanceVOs, dmo2);

			// ���Ĳ�������ԭ������ϸ
			String[] keys = dmo.insertArray(materialledgers);

			//���岽��gc ��д�����ϱ�ҳ
			reWriteDDLB(materialledgers, true);
			return keys;
		} catch (Exception e) {
			SCMEnv.out(e);
			PublicDMO.throwBusinessException(e);
		} finally {
			// �Ե��ݽ���
			if (bLock)
				PKLock.getInstance().releaseBatchLock(generalKeys, coperatorid,
						PublicDMO.getDsName());
		}
		return null;
	}

	/**
	 * ���ܣ���������ϸ���Ƿ��������
	 * 
	 * @param balanceVOs
	 *            ���VO����
	 * @param balanceDMO
	 *            ���DMO
	 * @author hanbin 2009-11-9
	 * @throws Exception 
	 * @throws NamingException 
	 * @throws SystemException 
	 */
	private void checkBalanceNum(BalanceVO[] balanceVOs, BalanceDMO balanceDMO)
			throws Exception {

		// ��ȡ���Ƿ�������桱����
		SysInitBO sysInitService = new SysInitBO();
		SysInitVO initVO[] = sysInitService.querySysInit(balanceVOs[0]
				.getPk_corp(), "SC06");
		if (initVO == null || initVO.length == 0)
			throw new BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000075"));// �޷���ȡί�����SC06
		String sCross = initVO[0].getValue(); // �Ƿ���Բ���

		// �������Ϊ�գ�����������ڸ���棬��ֱ�ӷ���
		if (StringUtils.isBlank(sCross) || !"��"/*-=notranslate=-*/.equals(sCross))
			return;

		// ѭ������Ƿ���ڸ����ļ�¼
		for (int i = 0; i < balanceVOs.length; i++) {
			BalanceVO tempVO[] = balanceDMO.queryByVONormally(balanceVOs[i],
					new Boolean(true));

			// ��ѯ���Ϊ�գ���������ѭ��
			if (ArrayUtils.isEmpty(tempVO))
				continue;

			for (int j = 0; j < tempVO.length; j++) {
				UFDouble nBalanceNum = tempVO[j].getNbalancenum();
				// ������ϸ�ʽ����������Ϊ��

				Object[][] invo = null;
				if (nBalanceNum != null && nBalanceNum.doubleValue() < 0) {// ncm_wanghfa_�������_���������
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
																 * "������ϸ�ʽ����������Ϊ��"
																 */);
				}
			}

		}
	}

	/**
	 * ���ܣ�����VO�����趨�������������з���������VO����
	 * 
	 * @param condMaterialledgerVOs
	 *            nc.vo.sc.settle.MaterialledgerVO
	 * @param isAnd
	 *            boolean ����������ѯ�����Ի�������ѯ �������ڣ�(2001-7-10)
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
	 * ���ܣ���ò�����ϸ�ʽ�浥��
	 * 
	 * @param condBalanceVO
	 * @return nc.vo.sc.settle.MaterialledgerVO �������ڣ�(2001-7-10)
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
	 * ��ò�����ϸ�ʽ�浥�� �������ڣ�(2001-10-11 10:11:25)
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
	 * ���Ϻ���VOת��Ϊ����VO;���Ϻ����޸ġ�������ʱ��Ҫ�����޸�ǰ�������������������ϲ��迼�� �������ڣ�(2001-10-11
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

				oneVO.setPk_corp(itemVOs[i].getPk_corp());// ��˾
				// ��Ӧ��
				oneVO.setCvendorid(itemVOs[i].getCvendorid());
				oneVO.setCvendormangid(itemVOs[i].getCvendormangid());
				// �ӹ�Ʒ����
				oneVO.setCprocessbaseid(itemVOs[i].getCprocessbaseid());
				oneVO.setCprocessmangid(itemVOs[i].getCprocessmangid());
				// ԭ���ϵ���
				oneVO.setCmaterialbaseid(itemVOs[i].getCmaterialbaseid());
				oneVO.setCmaterialmangid(itemVOs[i].getCmaterialmangid());
				// ������
				oneVO.setVfree1(itemVOs[i].getVfree1());
				oneVO.setVfree2(itemVOs[i].getVfree2());
				oneVO.setVfree3(itemVOs[i].getVfree3());
				oneVO.setVfree4(itemVOs[i].getVfree4());
				oneVO.setVfree5(itemVOs[i].getVfree5());
				oneVO.setVbatch(itemVOs[i].getVbatch());

				// �������������
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
				// �������
				oneVO.setNbalancenum(new UFDouble(num));
				// �����
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
	 * ���ܣ���VO���������ֵ�������ݿ�,���淴����ʱ����
	 * 
	 * @param materialledgers
	 *            ���Ϻ���VO����
	 * @param estimateVOs
	 *            �����ݹ���VO����
	 * @param paramList
	 *            ���δ�ţ�����ԱID����ǰ���ڡ���ⵥͷ��ts �������ڣ�(2001-7-4)
	 */
	public void updateArray(MaterialledgerVO[] materialledgers,
			EstimateVO[] estimateVOs, List paramList) throws BusinessException {
		if (paramList == null || paramList.size() != 3)
			throw new BusinessException(NCLangResOnserver.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000028")/*
																	 * @res
																	 * "��������"
																	 */);

		// ����������ԱID����ǰ���ڡ���ⵥͷ��ts
		String coperatorid = (String) paramList.get(0);
		UFDate curDate = (UFDate) paramList.get(1);
		String generalHeaderTS = (String) paramList.get(2);

		boolean bLock = false;
		String generalKeys[] = null;
		try {
			// �Ե��ݼ���
			Vector<String> vTemp = new Vector<String>();
			materialledgers[0].setDbilldate(curDate);
			vTemp.addElement(materialledgers[0].getCbillid());
			vTemp.addElement(materialledgers[0].getCbill_bid());
			for (int i = 1; i < materialledgers.length; i++) {
				// ί�������ϸ�ʰ�ί�мӹ���ⵥ�ĺ������ڼ���.since v5.01
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
																	 * "���ڽ�����ز��������Ժ�����"
																	 */);

			// ������ϸ��DMO
			MaterialledgerDMO dmo = new MaterialledgerDMO();

			// �����ⵥ��ts��״̬
			this.checkTsAndBillStatus(materialledgers, generalHeaderTS, dmo);
			// ����Ƿ񲹵�
			for (MaterialledgerVO vo : materialledgers)
				dmo.checkMaterialLedgerDate(vo, curDate);

			dmo.updateArray(materialledgers);

			// �ݹ��������ݹ���
			IPuToSc_EstimateBO esti = (IPuToSc_EstimateBO) NCLocator
					.getInstance().lookup(IPuToSc_EstimateBO.class.getName());
			esti.antiEstimateForSc(estimateVOs, coperatorid);

			// ά��ί��ӹ�������ϸ�ʽ���
			BalanceVO[] balanceVOs = trimToBalanceVO(materialledgers, true);

			//gc
			reWriteDDLB(materialledgers, false);
			
			BalanceDMO dmo2 = new BalanceDMO();
			dmo2.updateBalanceDatas(balanceVOs, new UFBoolean("N"));
		} catch (Exception e) {
			SCMEnv.out(e);

			PublicDMO.throwBusinessException(e);
		} finally {
			// �Ե��ݽ���
			if (bLock)
				PKLock.getInstance().releaseBatchLock(generalKeys, coperatorid,
						PublicDMO.getDsName());
		}
	}

	/**
	 * ���ܣ������ⵥ��ts��״̬ �������ڣ�(2001-7-4)
	 */
	private void checkTsAndBillStatus(MaterialledgerVO[] materialledgers,
			String generalHeaderTS, MaterialledgerDMO dmo) throws SQLException,
			BusinessException {
		// ����ʱ����жϣ������û�ȡ��ǩ��-���޸�-��ǩ�֣��ٺ���
		String newTs = dmo.queryGeneralBillhTs(materialledgers[0].getCbillid());
		if (newTs == null || !newTs.equals(generalHeaderTS))
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000143"));// ��Դ�����ѱ��޸Ļ�ɾ��������

		/******************** �����ⵥ��״̬ *********************/
		Vector queryRet = dmo.queryGeneralBillStatus(materialledgers[0]
				.getCbillid());
		int dr = ((Integer) queryRet.elementAt(0)).intValue();
		if (dr == 1)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000072")/*
																				 * @res
																				 * "��ⵥ������"
																				 */);

		String cregister = (String) queryRet.elementAt(1);
		if (cregister == null || cregister.trim().length() == 0)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000073")/*
																				 * @res
																				 * "��ⵥδǩ��"
																				 */);

		boolean bSettled = ((UFBoolean) queryRet.elementAt(2)).booleanValue();
		if (bSettled)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000074")/*
																				 * @res
																				 * "��ⵥ�ѽ���"
																				 */);

		// �Ƿ����ɲɹ���Ʊ
		boolean isInvoiced = ((UFBoolean) queryRet.elementAt(3)).booleanValue();
		if (isInvoiced)
			throw new nc.vo.pub.BusinessException(nc.bs.ml.NCLangResOnserver
					.getInstance().getStrByID("40120001", "UPP40120001-000079"));// ��ⵥ�����ɷ�Ʊ
	}

	/**
	 * ���ܣ���VO���������ֵ�������ݿ⡣ �������ڣ�(2001-7-4)
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
	 * ���ܣ�����ָ����sql����ѯ��ⵥ��ͷVO���ϣ������ز��Ϻ�����ͷVO����
	 * 
	 * @param strWhereSql
	 *            ָ����sql
	 * @param cl
	 *            �ͻ���������Ϣ
	 * @return ���Ϻ�����ͷVO
	 * @author hanbin
	 */
	public MaterialHeaderVO[] findLightGeneralHead(String strWhereSql,
			ClientLink cl) throws BusinessException {
		// ����ָ����sql����ѯ��ⵥ��ͷVO����
		IPublic bo = (IPublic) NCLocator.getInstance().lookup(
				IPublic.class.getName());
		GeneralBillHeaderVO[] generalHeaderVOs = bo.findGeneralHead(
				strWhereSql, cl);

		// ת��Ϊ���Ϻ�����ͷVO���ϣ�������
		return this.convetToMaterialHeadVO(generalHeaderVOs);
	}

	/**
	 * ���ܣ����ݱ�ͷkey����ѯ��Ӧ����ⵥ���壬��ת��Ϊ������ϸMaterialledgerVO����
	 * 
	 * @param itemVOs
	 *            ��ⵥ�ı�ͷ����
	 * @return ���Ϻ����ı�ͷVO����
	 * @author hanbin
	 */
	public MaterialledgerVO[] findLightGeneralItem(String headerKey)
			throws BusinessException {
		// ��ö�Ӧ��ⵥ�ı���VO����
		GeneralBillItemVO[] generalItemVOs = this.findGeneralBody(headerKey);

		// ת��Ϊ������ϸMaterialledgerVO���ϣ�������
		return this.convetToMaterialledgerVO(generalItemVOs);
	}

	/**
	 * ���ܣ�����ⵥHeadVO����ת��Ϊ���Ϻ���HeadVO����
	 * 
	 * @param itemVOs
	 *            ��ⵥ�ı�ͷVO����
	 * @return ���Ϻ����ı�ͷVO����
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
				// �������
				VOs[i].setDbilldate(headVOs[i].getDbilldate());
				// ��ⵥ��ͷID
				VOs[i].setCbillid(headVOs[i].getCgeneralhid());
				// ��ⵥ��
				VOs[i].setCbillcode(headVOs[i].getVbillcode());
				// ����
				VOs[i].setCdeptid(headVOs[i].getCdptid());
				// ��Ӧ�̹���ID
				VOs[i].setCvendormangid(headVOs[i].getCproviderid());
				// ͨ����Ӧ�̹���ID����û���ID
				String vendorbaseid = (String) dmo.fetchCellValue(
						"bd_cumandoc", "pk_cubasdoc", "pk_cumandoc", headVOs[i]
								.getCproviderid());
				VOs[i].setCvendorid(vendorbaseid);

				// ��˾
				VOs[i].setPk_corp(headVOs[i].getPk_corp());
				// �ֿ�ID
				VOs[i].setCwarehouseid(headVOs[i].getCwarehouseid());

				// ���ݲֿ�id����ÿ��id
				String cwareid = (String) dmo.fetchCellValue("bd_stordoc",
						"pk_calbody", "pk_stordoc", headVOs[i]
								.getCwarehouseid());
				VOs[i].setCwareid(cwareid);
				// ҵ��Ա
				VOs[i].setCemployeeid(headVOs[i].getCbizid());
				// ��¼ic_general_h�е�ts,���ڲ�������
				VOs[i].setTs(headVOs[i].getTimeStamp());

				// �Զ�����
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

				// ��ⵥ��ҵ�����ͣ��ݹ�ʱҪʹ��
				VOs[i].setBiztypeid(headVOs[i].getCbiztypeid());
			}
		} catch (Exception e) {
			PublicDMO.throwBusinessException(e);
		}
		return VOs;
	}

	/**
	 * ���ܣ�����ⵥ�ı���VO����,ת��Ϊ���Ϻ����ı���VO����
	 * 
	 * @param itemVOs
	 *            ��ⵥ�ı���VO����
	 * @return ���Ϻ����ı���VO����
	 * @author hanbin
	 */
	private MaterialledgerVO[] convetToMaterialledgerVO(
			GeneralBillItemVO[] itemVOs) throws BusinessException {

		if (itemVOs == null)
			// throw new BusinessException("�Ҳ�����ⵥ�������ݣ������Ѿ���ɾ��!");
			return new MaterialledgerVO[0];

		int num = itemVOs.length;
		MaterialledgerVO[] VOs = new MaterialledgerVO[num];
		try {
			PublicDMO dmo = new PublicDMO();
			for (int i = 0; i < num; i++) {
				VOs[i] = new MaterialledgerVO();

				// �޸ģ����Ϻ������ݹ������ɵĴ������ί��ӹ��ջ�����û��ҵ�����ڣ�
				VOs[i].setDbizdate(itemVOs[i].getDbizdate());
				// �ӹ�Ʒ��ʶ
				VOs[i].setCinvshow(NCLangResOnserver.getInstance().getStrByID(
						"40120003", "UPP40120003-000010"));// ��Ʒ
				// ί�ⶩ��
				VOs[i].setCorderid(itemVOs[i].getCfirstbillhid());
				VOs[i].setCorder_bid(itemVOs[i].getCfirstbillbid());

				// ��Դ��ⵥ
				VOs[i].setCbillid(itemVOs[i].getCgeneralhid());
				VOs[i].setCbill_bid(itemVOs[i].getCgeneralbid());

				// ���Ϲ�����ID
				VOs[i].setCmaterialmangid(itemVOs[i].getCinventoryid());
				// ���ϻ�������ID
				String invBaseID = (String) dmo.fetchCellValue("bd_invmandoc",
						"pk_invbasdoc", "pk_invmandoc", itemVOs[i]
								.getCinventoryid());
				VOs[i].setCmaterialbaseid(invBaseID);
				// �ӹ�Ʒ����
				VOs[i].setNnum(itemVOs[i].getNinnum());

				// �ӹ���
				if (itemVOs[i].getBzgflag() != null
						&& itemVOs[i].getBzgflag().booleanValue())
					VOs[i].setNprocessmny(itemVOs[i].getNplannedprice()); // ����Ѿ��ݹ���
				// ��
				// �ӹ���
				// =
				// ��
				// �ƻ�����
				// ��
				else
					VOs[i].setNprocessmny(itemVOs[i].getNprice()); // ���û�б��ݹ���
				// �ӹ��� =
				// �����ۡ�

				// ���Ϸ�
				VOs[i].setNmny(itemVOs[i].getNplannedmny()); // ȡ�ԡ��ƻ���
				// ���Ϸѵ��ۼ��� = ���Ϸ� / �ӹ�Ʒ����
				if (VOs[i].getNmny() != null && VOs[i].getNnum() != null
						&& VOs[i].getNnum().doubleValue() != 0)
					VOs[i].setNprice(VOs[i].getNmny().div(VOs[i].getNnum()));
				// ���κ�
				VOs[i].setVbatch(itemVOs[i].getVbatchcode());
				// ������
				if (itemVOs[i].getFreeItemVO() != null) {
					VOs[i].setVfree1(itemVOs[i].getFreeItemVO().getVfree1());
					VOs[i].setVfree2(itemVOs[i].getFreeItemVO().getVfree2());
					VOs[i].setVfree3(itemVOs[i].getFreeItemVO().getVfree3());
					VOs[i].setVfree4(itemVOs[i].getFreeItemVO().getVfree4());
					VOs[i].setVfree5(itemVOs[i].getFreeItemVO().getVfree5());
				}
				// �Զ�����
				VOs[i].setVdef1(itemVOs[i].getVuserdef1());
				VOs[i].setVdef2(itemVOs[i].getVuserdef2());
				VOs[i].setVdef3(itemVOs[i].getVuserdef3());
				VOs[i].setVdef4(itemVOs[i].getVuserdef4());
				VOs[i].setVdef5(itemVOs[i].getVuserdef5());
				VOs[i].setVdef6(itemVOs[i].getVuserdef6());

				// ��������λID
				VOs[i].setCastunitid(itemVOs[i].getCastunitid());
				// ������
				VOs[i].setHsl(itemVOs[i].getHsl());
				// ʵ�븨����
				VOs[i].setNinassistnum(itemVOs[i].getNinassistnum());
				// ��ע
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
			//�����������������һ�£�˵���޸ĵ��������ֶΡ�
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
				throw new BusinessException("û���ҵ���Ӧ��ԭ����Ϣ��������������ͬԭ����Ϣ��һ��");
			}
		}
		
	}
}