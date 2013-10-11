package nc.ui.ic.ic221;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import nc.ui.ic.pub.PageCtrlBtn;
import nc.ui.ic.pub.bill.GeneralBillUICtl;
import nc.ui.ic.pub.bill.QueryDlgHelpForSpec;
import nc.ui.ic.pub.bill.SpecialBillBaseUI;
import nc.ui.ic.pub.bill.SpecialBillHelper;
import nc.ui.ic.pub.bill.initref.RefFilter;
import nc.ui.ic.pub.print.PrintDataInterface;
import nc.ui.ic.pub.query.QueryDlgUtil;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.scm.pub.redunmulti.ISourceRedunUI;
import nc.ui.scm.pub.redunmulti.PubRedunMultiDlg;
import nc.ui.scm.pub.redunmulti.PubTransBillPaneVO;
import nc.vo.ic.pub.BillTypeConst;
import nc.vo.ic.pub.GenMethod;
import nc.vo.ic.pub.ICConst;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.bill.IItemKey;
import nc.vo.ic.pub.bill.QryConditionVO;
import nc.vo.ic.pub.bill.QryInfoConst;
import nc.vo.ic.pub.bill.SpecialBillHeaderVO;
import nc.vo.ic.pub.bill.SpecialBillItemVO;
import nc.vo.ic.pub.bill.SpecialBillVO;
import nc.vo.ic.pub.lang.ResBase;
import nc.vo.ic.pub.smallbill.SMSpecialBillVO;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.constant.ic.BillMode;
import nc.vo.scm.constant.ic.InOutFlag;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.bill.WhVO;
import nc.vo.scm.print.PrintConst;
import nc.vo.scm.print.ScmPrintlogVO;
import nc.vo.scm.pub.BD_TYPE;
import nc.vo.scm.pub.BillRowNoVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.query.DataPowerCtl;
import nc.vo.scm.pub.vosplit.SplitBillVOs;

/*
 * 创建者：仲瑞庆
 * 创建日期：2001-04-20
 * 功能：转库单界面
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * lxt 2013-07 cgxf：赣州晨光稀土项目，根据发货单生成。
 */
public class ClientUI extends SpecialBillBaseUI {
	private ButtonObject m_boAdd4331;

	/**
	 * ClientUI 构造子注解。
	 */
	public ClientUI() {
		super();
		initialize();
	}

	/**
	 * 李俊 功能：对转单后的普通单据VO设置行号 参数： 返回： 例外： 日期：(2005-1-18 14:45:22)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param voBill
	 *            nc.vo.ic.pub.bill.GeneralBillVO
	 */
	private void setRowNo(GeneralBillVO voBill) {

		if (voBill == null || voBill.getChildrenVO().length <= 0)
			return;
		BillRowNoVO.setVOsRowNoByRule(voBill.getItemVOs(), "4I", "crowno");
		// int len = voBill.getChildrenVO().length;
		// for (int i=1;i<=len;i++){
		// voBill.getChildrenVO()[i-1].setAttributeValue("crowno",(new
		// Integer(i)).toString());
		// }

	}
	public void afterWhOutEdit(nc.ui.pub.bill.BillEditEvent e) {
		super.afterWhOutEdit(e);
		
		WhVO voWh = getWhInfoByRef("coutwarehouseid");
		// 库存组织处理
		nc.ui.pub.bill.BillItem biCalBody = getBillCardPanel()
				.getHeadItem("pk_calbody_out");
		if (biCalBody != null) {
			if (voWh != null){
				biCalBody.setValue(voWh.getPk_calbody());
				m_voBill.setHeaderValue("pk_calbody_out", voWh.getPk_calbody());
			}
			else{
				biCalBody.setValue(null);
				m_voBill.setHeaderValue("pk_calbody_out", null);
			}
			
			
			String sNewID = null;
			if (voWh != null)
				sNewID = voWh.getPk_calbody();
			// 清空了库存组织
			// 如果当前的仓库不属于
			// 过滤仓库参照
			String sConstraint[] = null;
			String sConstraint1[] = null;
			if (sNewID != null) {
				sConstraint = new String[1];
				sConstraint[0] = " AND isdirectstore = 'N' AND pk_calbody='"
						+ sNewID + "'";
				sConstraint1 = new String[1];
				sConstraint1[0] = " and mm_jldoc.gcbm='" + sNewID + "'";
			}
			nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
					"coutwarehouseid");
			RefFilter.filtWh(bi, m_sCorpID, sConstraint);
			
			bi = getBillCardPanel().getHeadItem(
			"cinwarehouseid");
	RefFilter.filtWh(bi, m_sCorpID, sConstraint);
		}
		nc.ui.pub.bill.BillItem biCalBodyname = getBillCardPanel()
				.getHeadItem("vcalbodyname_out");
		if (biCalBodyname != null) {
			if (voWh != null){
				biCalBodyname.setValue(voWh.getVcalbodyname());
				m_voBill.setHeaderValue("vcalbodyname_out", voWh.getVcalbodyname());
			}
			else{
				biCalBodyname.setValue(null);
				m_voBill.setHeaderValue("vcalbodyname_out", null);
			}
		}
	}
	
	public void afterCalbodyOutEdit(nc.ui.pub.bill.BillEditEvent e) {
		try {
			String sNewID = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
					.getHeadItem(e.getKey()).getComponent()).getRefPK();
			// 清空了库存组织
			// 如果当前的仓库不属于
			// 过滤仓库参照
			String sConstraint[] = null;
			String sConstraint1[] = null;
			if (sNewID != null) {
				sConstraint = new String[1];
				sConstraint[0] = " AND isdirectstore = 'N' AND pk_calbody='"
						+ sNewID + "'";
				sConstraint1 = new String[1];
				sConstraint1[0] = " and mm_jldoc.gcbm='" + sNewID + "'";
			}
			nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
					"coutwarehouseid");
			RefFilter.filtWh(bi, m_sCorpID, sConstraint);
			// 处理:clear warehouse
			WhVO whvo = m_voBill.getWhOut();
			nc.ui.pub.bill.BillItem biWh = getBillCardPanel().getHeadItem(
					"coutwarehouseid");
			if (biWh != null
					&& (whvo == null || (sNewID != null && !sNewID.equals(whvo
							.getPk_calbody())))){
				biWh.setValue(null);
				m_voBill.setWhOut(null);
			}
			
			bi = getBillCardPanel().getHeadItem(
			"cinwarehouseid");
	RefFilter.filtWh(bi, m_sCorpID, sConstraint);
	// 处理:clear warehouse
	whvo = m_voBill.getWhIn();
	biWh = getBillCardPanel().getHeadItem(
			"cinwarehouseid");
	if (biWh != null
			&& (whvo == null || (sNewID != null && !sNewID.equals(whvo
					.getPk_calbody())))){
		biWh.setValue(null);
		m_voBill.setWhOut(null);
	}		
			

			// 过滤成本对象
			// filterCostObject();

		} catch (Exception e2) {
			SCMEnv.out(e2);
		}

	}
	
	public void afterWhInEdit(nc.ui.pub.bill.BillEditEvent e) {
		super.afterWhInEdit(e);
		WhVO voWh = getWhInfoByRef("cinwarehouseid");
		// 库存组织处理
		nc.ui.pub.bill.BillItem biCalBody = getBillCardPanel()
				.getHeadItem("pk_calbody_out");
		if (biCalBody != null) {
			if (voWh != null){
				biCalBody.setValue(voWh.getPk_calbody());
				m_voBill.setHeaderValue("pk_calbody_out", voWh.getPk_calbody());
			}
			else{
				biCalBody.setValue(null);
				m_voBill.setHeaderValue("pk_calbody_out", null);
			}
			
			String sNewID = null;
			if (voWh != null)
				sNewID = voWh.getPk_calbody();
			// 清空了库存组织
			// 如果当前的仓库不属于
			// 过滤仓库参照
			String sConstraint[] = null;
			String sConstraint1[] = null;
			if (sNewID != null) {
				sConstraint = new String[1];
				sConstraint[0] = " AND isdirectstore = 'N' AND pk_calbody='"
						+ sNewID + "'";
				sConstraint1 = new String[1];
				sConstraint1[0] = " and mm_jldoc.gcbm='" + sNewID + "'";
			}
			nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
					"cinwarehouseid");
			RefFilter.filtWh(bi, m_sCorpID, sConstraint);
			
			bi = getBillCardPanel().getHeadItem(
			"coutwarehouseid");
	RefFilter.filtWh(bi, m_sCorpID, sConstraint);
		}
		nc.ui.pub.bill.BillItem biCalBodyname = getBillCardPanel()
				.getHeadItem("vcalbodyname_out");
		if (biCalBodyname != null) {
			if (voWh != null){
				biCalBodyname.setValue(voWh.getVcalbodyname());
				m_voBill.setHeaderValue("vcalbodyname_out", voWh.getVcalbodyname());
			}
			else{
				biCalBodyname.setValue(null);
				m_voBill.setHeaderValue("vcalbodyname_out", null);
			}
		}
	}
	
	public void afterCalbodyInEdit(nc.ui.pub.bill.BillEditEvent e) {
		try {
			String sNewID = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
					.getHeadItem(e.getKey()).getComponent()).getRefPK();
			// 清空了库存组织
			// 如果当前的仓库不属于
			// 过滤仓库参照
			String sConstraint[] = null;
			String sConstraint1[] = null;
			if (sNewID != null) {
				sConstraint = new String[1];
				sConstraint[0] = " AND isdirectstore = 'N' AND pk_calbody='"
						+ sNewID + "'";
				sConstraint1 = new String[1];
				sConstraint1[0] = " and mm_jldoc.gcbm='" + sNewID + "'";
			}
			nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
					"cinwarehouseid");
			RefFilter.filtWh(bi, m_sCorpID, sConstraint);
			// 处理:clear warehouse
			WhVO whvo = m_voBill.getWhIn();
			nc.ui.pub.bill.BillItem biWh = getBillCardPanel().getHeadItem(
					"cinwarehouseid");
			if (biWh != null
					&& (whvo == null || (sNewID != null && !sNewID.equals(whvo
							.getPk_calbody())))){
				biWh.setValue(null);
			m_voBill.setWhIn(null);
			}

			// 过滤成本对象
			// filterCostObject();

		} catch (Exception e2) {
			SCMEnv.out(e2);
		}

	}

	/**
	 * 创建者：仲瑞庆 功能：当发生数据修改时 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * 
	 * 
	 * 
	 */
	public void afterEdit(nc.ui.pub.bill.BillEditEvent e) {

		super.afterEdit(e);
		
		if (e.getKey().equals("pk_calbody_out")) { //出库仓库
			afterCalbodyOutEdit(e);
		} else if (e.getKey().equals("pk_calbody_in")) { //入库仓库
			afterCalbodyInEdit(e);
		}

		// 自动更新VO中数据
		// ********************************************************************//
		String strColName = e.getKey().trim();
		int rownum = e.getRow();
		if (strColName.equals("coutwarehouseid")) {
			// 给存货参照传参数
			nc.ui.pub.bill.BillItem bi = getBillCardPanel().getBodyItem(
					"cinventorycode");
			nc.ui.pub.beans.UIRefPane invRef = (nc.ui.pub.beans.UIRefPane) bi
					.getComponent();
			nc.ui.bd.ref.AbstractRefModel m = invRef.getRefModel();
			nc.ui.pub.bill.BillItem bi1 = getBillCardPanel().getHeadItem(
					"coutwarehouseid");
			nc.ui.pub.beans.UIRefPane invRef1 = (nc.ui.pub.beans.UIRefPane) bi1
					.getComponent();

			String[] o = new String[] { m_sCorpID, invRef1.getRefPK() };
			m.setUserParameter(o);

		}
		// 检查表体行
		if (rownum >= 0) {
			if (strColName.equals(m_sNumItemKey)) {
				calculateByHsl(rownum, m_sNumItemKey, m_sAstItemKey, 0);
			} else if (strColName.equals(m_sAstItemKey)) {
				// 辅数量修改带出主数量
				afterNshldtransastnumEdit(e);
			}

		}

	}
	
	protected void calculateByHsl(
			int iRow,
			String sMainNum,
			String sAstNum,
			int iWhichChanged) {
		Object temphsl= getBillCardPanel().getBodyValueAt(iRow, "hsl");
		if (temphsl == null || temphsl.toString().trim().length() == 0) {
			temphsl= ZERO;
		} else {
			UFDouble hsl= (UFDouble) temphsl;
			if (hsl.doubleValue() <= 0) { //换算率小于零
				if (m_voBill.getItemValue(iRow, "isAstUOMmgt") != null
					&& m_voBill.getItemValue(iRow, "isAstUOMmgt").toString().trim().length() != 0) {
					//是辅计量管理,清主数量，清换算率，置入辅数量
					getBillCardPanel().setBodyValueAt(null, iRow, sMainNum);
					m_voBill.setItemValue(iRow, sMainNum, null);
					m_voBill.setItemValue(
						iRow,
						sAstNum,
						getBillCardPanel().getBodyValueAt(iRow, sAstNum));
					getBillCardPanel().setBodyValueAt(null, iRow, "hsl");
					m_voBill.setItemValue(iRow, "hsl", null);
				} else {
					//非辅计量管理,清辅数量，清换算率，置入主数量
					m_voBill.setItemValue(
						iRow,
						sMainNum,
						getBillCardPanel().getBodyValueAt(iRow, sMainNum));
					getBillCardPanel().setBodyValueAt(null, iRow, sAstNum);
					getBillCardPanel().setBodyValueAt(null, iRow, "hsl");
					m_voBill.setItemValue(iRow, sAstNum, null);
					m_voBill.setItemValue(iRow, "hsl", null);
				}
			} else { //换算率大于零
				UFDouble ufdMainNum= null;
				UFDouble ufdAstNum= null;
				if ((null != getBillCardPanel().getBodyValueAt(iRow, sMainNum))
					&& (getBillCardPanel().getBodyValueAt(iRow, sMainNum).toString().trim().length()
						!= 0))
					ufdMainNum= (UFDouble) getBillCardPanel().getBodyValueAt(iRow, sMainNum);
				if ((null != getBillCardPanel().getBodyValueAt(iRow, sAstNum))
					&& (getBillCardPanel().getBodyValueAt(iRow, sAstNum).toString().trim().length()
						!= 0))
					ufdAstNum= (UFDouble) getBillCardPanel().getBodyValueAt(iRow, sAstNum);
				if (isFixFlag(iRow).booleanValue()) { //是固定换算率
					if (iWhichChanged == 1) {
						//是辅数量更改
						if ((getBillCardPanel().getBodyValueAt(iRow, sAstNum) != null)
							&& (getBillCardPanel().getBodyValueAt(iRow, sAstNum).toString().trim().length()
								!= 0)
							&& (((UFDouble) getBillCardPanel().getBodyValueAt(iRow, sAstNum)).doubleValue()
								!= 0)) {
							ufdMainNum = ufdAstNum.multiply( hsl );
							getBillCardPanel().setBodyValueAt(ufdMainNum, iRow, sMainNum);
							m_voBill.setItemValue(iRow, sMainNum,
									getBillCardPanel().getBodyValueAt(iRow, sMainNum));
						} else if (
							(getBillCardPanel().getBodyValueAt(iRow, sAstNum) != null)
								&& (getBillCardPanel().getBodyValueAt(iRow, sAstNum).toString().trim().length()
									!= 0)
								&& (((UFDouble) getBillCardPanel().getBodyValueAt(iRow, sAstNum)).doubleValue()
									== 0)) {
							getBillCardPanel().setBodyValueAt(ZERO, iRow, sMainNum);
							m_voBill.setItemValue(iRow, sMainNum, ZERO);
							m_voBill.setItemValue(iRow, sAstNum, ZERO);
						} else {
							getBillCardPanel().setBodyValueAt(null, iRow, sMainNum);
							m_voBill.setItemValue(iRow, sMainNum, null);
							m_voBill.setItemValue(iRow, sAstNum, null);
						}
					} else {
						//是主数量更改或主数量不变
						if ((getBillCardPanel().getBodyValueAt(iRow, sMainNum) != null)
							&& (getBillCardPanel().getBodyValueAt(iRow, sMainNum).toString().trim().length()
								!= 0)
							&& (((UFDouble) getBillCardPanel().getBodyValueAt(iRow, sMainNum)).doubleValue()
								!= 0)) {
							ufdAstNum= ufdMainNum.div(hsl);
							getBillCardPanel().setBodyValueAt(ufdAstNum, iRow, sAstNum);
							m_voBill.setItemValue(iRow, sAstNum,
								getBillCardPanel().getBodyValueAt(iRow, sAstNum));
						} else if (
							(getBillCardPanel().getBodyValueAt(iRow, sMainNum) != null)
								&& (getBillCardPanel().getBodyValueAt(iRow, sMainNum).toString().trim().length()
									!= 0)
								&& (((UFDouble) getBillCardPanel().getBodyValueAt(iRow, sMainNum)).doubleValue()
									== 0)) {
							getBillCardPanel().setBodyValueAt(ZERO, iRow, sAstNum);
							m_voBill.setItemValue(iRow, sMainNum, ZERO);
							m_voBill.setItemValue(iRow, sAstNum, ZERO);
						} else {
							getBillCardPanel().setBodyValueAt(null, iRow, sAstNum);
							m_voBill.setItemValue(iRow, sMainNum, null);
							m_voBill.setItemValue(iRow, sAstNum, null);
						}
					}
				} else { //非固定换算率
					if (((null == ufdMainNum) || (ufdMainNum.doubleValue() == 0))
						&& ((null == ufdAstNum) || (ufdAstNum.doubleValue() == 0))) { //都为空或零
						//do nothing
					} else if ((iWhichChanged == 1)) {
						// 辅数量修改
						if (ufdAstNum != null) {
							ufdMainNum  = ufdAstNum.multiply( hsl );
							getBillCardPanel().setBodyValueAt(ufdMainNum, iRow, sMainNum);
						} else if (ufdAstNum == null && ufdMainNum != null && hsl != null) {
							ufdAstNum = ufdMainNum.div(hsl);
							getBillCardPanel().setBodyValueAt(ufdAstNum, iRow, sAstNum);
						}
					} else if ((iWhichChanged == 2)) {
						//换算率修改
						if ((null == ufdMainNum) || (ufdMainNum.doubleValue() == 0)) {
							getBillCardPanel().setBodyValueAt(null, iRow, sAstNum);
							getBillCardPanel().setBodyValueAt(null, iRow, sMainNum);
						} else {
							ufdAstNum= ufdMainNum.div(hsl);
							getBillCardPanel().setBodyValueAt(ufdAstNum, iRow, sAstNum);
						}
					} else if ( (iWhichChanged == 0)
							 && ((null == ufdAstNum) || (ufdAstNum.doubleValue() == 0))) {
						//辅数量为空或零,主数量修改
						if(hsl!=null&&ufdMainNum!=null){
							ufdAstNum=ufdMainNum.div(hsl);
							getBillCardPanel().setBodyValueAt(ufdAstNum, iRow, sAstNum);//陈倪娜 2010-10-21
						}
					} else if ((iWhichChanged == 0)
							&& !((null == ufdAstNum) || (ufdAstNum.doubleValue() == 0))) {
						//辅数量不为空或零，主数量修改，此时修改换算率
						//修改人：刘家清 修改日期：2007-11-1上午10:20:45 修改原因：换算率永远为正
						if ((0 > ufdMainNum.doubleValue() && 0 < ufdAstNum.doubleValue())
								||(0 < ufdMainNum.doubleValue() && 0 > ufdAstNum.doubleValue())){
							ufdAstNum = ufdAstNum.div(new UFDouble(-1));
							getBillCardPanel().setBodyValueAt(ufdAstNum, iRow, sAstNum);
						}
						
						hsl= ufdMainNum.div(ufdAstNum);
						getBillCardPanel().setBodyValueAt(hsl, iRow, "hsl");
						m_voBill.setItemValue(
							iRow,
							"hsl",
							getBillCardPanel().getBodyValueAt(iRow, "hsl"));
					}
					m_voBill.setItemValue(
						iRow,
						sMainNum,
						getBillCardPanel().getBodyValueAt(iRow, sMainNum));
					m_voBill.setItemValue(
						iRow,
						sAstNum,
						getBillCardPanel().getBodyValueAt(iRow, sAstNum));
				}
			}
		}
		getBillCardPanel().getBillModel().execEditFormulaByKey(iRow, sMainNum);	
	}

	/**
	 * 初始化类。
	 */
	/* 警告：此方法将重新生成。 */
	protected void initialize() {
		try {
			initVariable();
			super.initialize();

			super.setQuryPlanprice(true);


		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}
	}

	/**
	 * 李俊 功能： 参数： 返回： 例外： 日期：(2005-1-14 15:13:27) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return nc.vo.ic.pub.bill.GeneralBillVO[]
	 * @param voSp
	 *            nc.vo.ic.pub.bill.SpecialBillVO[]
	 */
	private GeneralBillVO[] pfVOConvert(SpecialBillVO[] voSp,
			String sSrcBillType, String sDesBillType) {
		GeneralBillVO[] gbvo = null;
		try {
			gbvo = (GeneralBillVO[]) nc.ui.pub.change.PfChangeBO_Client
					.pfChangeBillToBillArray(voSp, sSrcBillType, sDesBillType);
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.error(e);
		}
		return gbvo;
	}

	/**
	 * onButtonClicked 方法注解。
	 */
	public void onButtonClicked(nc.ui.pub.ButtonObject bo) { // finished

		showHintMessage(bo.getName());
		if (bo == m_boOut)
			onOut();
		else if (bo == m_boIn)
			onIn();
		else if (bo == m_boDirectOut)
			onDirectOut();
		else if (bo == m_boRowQuyQty)
			onRowQuyQty();
		else if (bo == m_boAdd4331)
			onAdd4331();
		else
			super.onButtonClicked(bo);

	}
	
	protected void onAdd4331() {
		try {

			PubTransBillPaneVO[] sourecVO = new PubTransBillPaneVO[1];
			sourecVO[0] = new PubTransBillPaneVO(
					"4331",
					NCLangRes.getInstance().getStrByID("40080802",
							"ClientUI-000002")/* 发货单 */,
					(ISourceRedunUI) Class
							.forName(
									"nc.ui.so.soreceive.redun.ReceiveTo4KRedunSourceCtrl")
							.newInstance(), null, "SO", null, null);

			PubTransBillPaneVO[] curVO = new PubTransBillPaneVO[1];
			curVO[0] = new PubTransBillPaneVO(getBillType(), "转库单", null,
					null, "IC", null, null);

			//HashMap<String, String> haparam = new HashMap<String, String>();
			//haparam.put("cbiztype", "100989100000000001JQ");

			PubRedunMultiDlg refdlg = new PubRedunMultiDlg(this, sourecVO,	curVO, true, null);
			if (refdlg.showModal() != UIDialog.ID_OK)
				return;
			AggregatedValueObject[] sourcebillvos = refdlg.getRetBillVos();
			if (sourcebillvos == null || sourcebillvos.length <= 0)
				return;

			AggregatedValueObject[] vos = null;
			try {
				vos = PfChangeBO_Client.pfChangeBillToBillArray(sourcebillvos,
						"4331", getBillType());
				vos = nc.vo.ic.pub.GenMethod.splitTargetVOs(vos, "4331",
						getBillType());
				
			} catch (Exception e) {
				nc.vo.scm.pub.SCMEnv.out(e);
				return;
			}
			
			if (vos != null && vos.length == 1) {
				onAdd();
				((SpecialBillVO)vos[0]).getHeaderVO().setVbillcode(getBillCardPanel().getHeadItem("vbillcode").getValueObject().toString());
				setBillValueVO((SpecialBillVO)vos[0]);
				((UIRefPane)getBillCardPanel().getHeadItem("vuserdef9").getComponent()).setPK(
						((SpecialBillVO)vos[0]).getHeaderVO().getVuserdef9());
				for(int i=0;i<((SpecialBillVO)vos[0]).getChildrenVO().length;i++){
					SpecialBillItemVO itemVO = (SpecialBillItemVO)((SpecialBillVO)vos[0]).getChildrenVO()[i];
					String[] refPks = new String[]{itemVO.getCinventoryid()};
					InvVO[] invVOs =getInvoInfoBYFormula().getBillQuryInvVOs(refPks,true,true);
					invVOs[0].setFreeItemVOValue(itemVO.getFreeItemVO());
					m_voBill.setItemInv(i, invVOs[0]);
					//表体
					setBodyInvValue(i, invVOs[0]);
				}
				//getBillCardPanel().execHeadTailLoadFormulas();
			} 
		} catch (Exception e) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008busi", "UPP4008busi-000297")/* @res "发生错误:" */
					+ e.getMessage());
		}
	}
	
	private String getBillType() {
		return "4K";
	}

	/* (non-Javadoc)
	 * @see nc.ui.ic.pub.bill.SpecialBillBaseUI#onRowQuyQty()
	 */
	public void onRowQuyQty()
	{
		SpecialBillVO nowVObill = null;
		int rownow = -1;
		if (m_iMode != BillMode.List)
		{
			rownow = getBillCardPanel().getBillTable().getSelectedRow();
			nowVObill = m_voBill;
		}
		else
		{
			rownow = getBillListPanel().getBodyTable().getSelectedRow();
			nowVObill = (SpecialBillVO) m_alListData.get(m_iLastSelListHeadRow);
		}
		if (rownow < 0)
		return;

		String WhID ="";
		String InvID ="";
		String castunitid = null;
		String cvendorid = null;

		if ((nowVObill != null) && (rownow >= 0))
		{
			
			WhID = (String) nowVObill.getHeaderValue("coutwarehouseid");
			InvID = (String) nowVObill.getItemValue(rownow, "cinventoryid");
			castunitid = (String) nowVObill.getItemValue(rownow, "castunitid");
			cvendorid = (String) nowVObill.getItemValue(rownow, "cvendorid");

		}
		if (WhID==null||WhID.trim().equals("")||InvID==null||InvID.trim().equals("")){
		    showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill","UPP4008bill-000157")/*@res "没有得到仓库ID或者存货ID"*/);
		    return;
		}

		getIohdDlg().setParam(WhID, InvID,castunitid,cvendorid);
		if (getIohdDlg().showModal() == nc.ui.pub.beans.UIDialog.ID_OK){
			nc.vo.ic.pub.InvOnHandVO[] selectVOs = getIohdDlg().m_SelectVOs;
			afterSelectOnhandVOs(selectVOs);
		}
	}

	public void onAdd() {

		super.onAdd();

		getBillCardPanel().setHeadItem("cshlddiliverdate", m_sLogDate);
		getBillCardPanel().setHeadItem("vshldarrivedate", m_sLogDate);
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2002-1-25 14:33:10) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void setButtonState() {
		super.setButtonState();

		m_boRowQuyQty.setEnabled(false);
		// /////////////
		switch (m_iMode) {
		case BillMode.New: // 新增

			m_boDirectOut.setEnabled(false);
			m_boRowQuyQty.setEnabled(true);
			m_boAdd4331.setEnabled(false);
			break;
		case BillMode.Update: // 修改

			m_boDirectOut.setEnabled(false);
			m_boRowQuyQty.setEnabled(true);
			m_boAdd4331.setEnabled(false);
			break;
		case BillMode.Browse: // 浏览

			if (!isCanPressCopyButton()) {
				m_boCopyBill.setEnabled(false);

			}
			m_boDirectOut.setEnabled(m_iTotalListHeadNum > 0);
			m_boAdd4331.setEnabled(true);
			break;
		case BillMode.List: // 列表状态
			if (!isCanPressCopyButton()) {
				m_boCopyBill.setEnabled(false);

			}

			m_boDirectOut.setEnabled(false);
			m_boAdd4331.setEnabled(true);
			break;
		}
		
		// addied by liuzy 2010-11-23 上午10:18:14 为了控制来源为备料申请的转库单不能够手工修改和删除
		if (null != m_alListData && m_alListData.size() > 0) {
      SpecialBillVO currVO = (SpecialBillVO) m_alListData
          .get(m_iLastSelListHeadRow);

      if (null != currVO)

        if (null != currVO) {
          boolean isSrcAm = false;
          String srctype = null;
          SpecialBillItemVO[] items = currVO.getItemVOs();
          for (SpecialBillItemVO item : items) {
            srctype = item.getCsourcetype();
            if (StringUtil.isEmptyWithTrim(srctype))
              continue;
            if (!"AM".equals(srctype))
              continue;
            else {
              isSrcAm = true;
              break;
            }
          }
          if (isSrcAm) {
            m_boChange.setEnabled(false);
            m_boDelete.setEnabled(false);
            m_boCopyBill.setEnabled(false);
          }
        }
    }

    // 使设置生效
    if (m_aryButtonGroup != null) {
      updateButtons();
    }

		// //////////////
	}

	/**
	 * 创建者：仲瑞庆 功能：转入 参数： 返回： 例外： 日期：(2001-5-10 下午 2:50) 修改日期，修改人，修改原因，注释标志：
	 */
	public void onIn() {
		if (m_alListData == null || m_alListData.size() < m_iLastSelListHeadRow
				|| m_iLastSelListHeadRow < 0) {

			return;
		}

		ArrayList alInGeneralVO = new ArrayList();
		SpecialBillVO voSp = (SpecialBillVO) m_alListData
				.get(m_iLastSelListHeadRow);
		// GeneralBillVO gbvo = changeFromSpecialVOtoGeneralVO(voSp,
		// InOutFlag.IN);
		// 将特殊单入库VO通过VO对照转换为普通单VO
		GeneralBillVO[] gbvo = pfVOConvert(new SpecialBillVO[] { voSp }, "4K",
				nc.vo.ic.pub.BillTypeConst.m_otherIn);
		if (gbvo == null || gbvo[0] == null) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008spec", "UPP4008spec-000118")/* @res "转单失败！" */);
			return;
		}
		// 出库时，应转库数量关系为：主数量：nshouldoutnum －》dshldtransnum - nchecknum ,
		// 辅助数量：nshouldoutassistnum－》dshldtransnum - nchecknum/hsl
		// 入库时，应转库数量关系为：主数量：nshouldinnum－>nchecknum-nadjustnum
		// ,辅助数量：nneedinassistnum－》nchecknum-nadjustnum/hsl
		// if (gbvo[0].getChildrenVO()==null||gbvo[0].getChildrenVO()[0]==null)
		// return;
		// if ( gbvo[0].getChildrenVO()[0].getAttributeValue("nshouldinnum")
		// ==null ||
		// ((UFDouble)gbvo[0].getChildrenVO()[0].getAttributeValue("nshouldinnum")).doubleValue()<=0)
		// {
		// showWarningMessage("无转入数量！");
		// return;
		// }
		// 获取仓库数据
		WhVO voWh = voSp.getWhIn();

		if (voWh == null) {
			nc.vo.scm.pub.SCMEnv.out("no wh ,data err.");
			return;
		}
		// 需要库存组织
		String sCalBodyID = voWh.getPk_calbody();
		String sCalBodyName = voWh.getVcalbodyname();
		// -------------- 没有的化，读一下 ------------
		if (sCalBodyID == null || sCalBodyID.trim().length() == 0) {
			voWh = getWhInfoByID(voWh.getCwarehouseid());
			if (voWh != null) {
				sCalBodyID = voWh.getPk_calbody();
				sCalBodyName = voWh.getVcalbodyname();
				// fresh bill
				voSp.setWhIn(voWh);
				m_alListData.set(m_iLastSelListHeadRow, voSp);
			}
		}

		gbvo[0].setHeaderValue("pk_calbody", sCalBodyID);
		gbvo[0].setHeaderValue("vcalbodyname", sCalBodyName);

		// 设置行号
		// setRowNo(gbvo[0]);
		BillRowNoVO.setVOsRowNoByRule(gbvo, "4A", "crowno");
		alInGeneralVO.add(gbvo[0]);
		// m_dlgInOut= null;
		filterZeroBill(gbvo[0]);
		if (gbvo[0].getItemVOs() == null || gbvo[0].getItemCount() == 0) {
			return;
		}

		getAuditDlg().setIsOK(false); // 设置未保存
		getAuditDlg().setSpBill(m_voBill);
		int ret = getAuditDlg(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPP4008spec-000119")/* @res "转入单" */, alInGeneralVO,
				null).showModal();
		// 2003-05-09.01 wnj:加上判断isOK刷新，因为保存后可以点X退出。
		if (ret == nc.ui.pub.beans.UIDialog.ID_OK || getAuditDlg().isOK()) {
			try {
				// 查出新的表单
				//SpecialBillVO voMyTempBill = null; // 一个的表头表体数据
				QryConditionVO qcvo = new nc.vo.ic.pub.bill.QryConditionVO();
				qcvo.setQryCond("cbilltypecode='" + m_sBillTypeCode
						+ "' and cspecialhid='"
						+ m_voBill.getPrimaryKey().trim() + "'");
				SMSpecialBillVO voMySmallBill = (SMSpecialBillVO) ((ArrayList) SpecialBillHelper
						.querySmallBills(m_sBillTypeCode, qcvo)).get(0);
				
				// 将后台信息更新到界面
				freshVOBySmallVO(voMySmallBill);
/*				voMyTempBill = (SpecialBillVO) ((ArrayList) SpecialBillHelper
						.queryBills(m_sBillTypeCode, qcvo)).get(0);
				// 特殊单没有保存换算率，变动hsl必须根据数量计算
				GenMethod mthod = new GenMethod();
				mthod.calAllConvRate(voMyTempBill.getChildrenVO(), "fixedflag",
						"hsl", "dshldtransnum", "nshldtransastnum", "", "");
				execFormula(voMyTempBill);
				m_alListData.set(m_iLastSelListHeadRow, voMyTempBill.clone());

				m_iFirstSelListHeadRow = -1;
				switchListToBill();*/
				setButtonState();
				setBillState();
				// 调出转入窗口
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008spec", "UPT40081002-000021")
						+ ResBase.getSuccess()/* @res "转入成功" */);

			} catch (Exception e) {
				handleException(e);
				nc.ui.pub.beans.MessageDialog
						.showErrorDlg(
								this,
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"SCMCOMMON", "UPPSCMCommon-000059")/*
																			 * @res
																			 * "错误"
																			 */,
								e.getMessage());
			}
		}
	}

	private void execFormula(SpecialBillVO vo) {
		if (vo == null)
			return;
		ArrayList<SpecialBillVO> al = new ArrayList<SpecialBillVO>();
		al.add(vo);
		getFormulaBillContainer().formulaBill(al, getFormulaItemHeader(),
				getFormulaItemBody());
		// SpecialBillHeaderVO hvo=vo.getHeaderVO();
		// SpecialBillItemVO[] bvos=vo.getItemVOs();
		// if(hvo!=null)
		// getFormulaBillContainer().formulaHeaders(getFormulaItemHeader(),
		// hvo);
		// if(bvos!=null&&bvos.length>0)
		// getFormulaBillContainer().formulaBodys(getFormulaItemBody(), bvos);

	}

	/**
	 * 创建者：仲瑞庆 功能：转出 参数： 返回： 例外： 日期：(2001-5-10 下午 2:50) 修改日期，修改人，修改原因，注释标志：
	 */
	public void onOut() {
		if (m_alListData == null || m_alListData.size() < m_iLastSelListHeadRow
				|| m_iLastSelListHeadRow < 0) {

			return;
		}

		ArrayList alOutGeneralVO = new ArrayList();
		SpecialBillVO voSp = (SpecialBillVO) m_alListData
				.get(m_iLastSelListHeadRow);
		// GeneralBillVO gbvo = changeFromSpecialVOtoGeneralVO(voSp,
		// InOutFlag.OUT);
		// 将特殊单入库VO通过VO对照转换为普通单VO
		GeneralBillVO[] gbvo = pfVOConvert(new SpecialBillVO[] { voSp }, "4K",
				"4I");
		if (gbvo == null || gbvo[0] == null) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008spec", "UPP4008spec-000118")/* @res "转单失败！" */);
			return;
		}
		// 获取仓库数据
		WhVO voWh = voSp.getWhOut();

		if (voWh == null) {
			nc.vo.scm.pub.SCMEnv.out("no wh ,data err.");
			return;
		}
		// 需要库存组织
		String sCalBodyID = voWh.getPk_calbody();
		String sCalBodyName = voWh.getVcalbodyname();
		// -------------- 没有的化，读一下 ------------
		if (sCalBodyID == null || sCalBodyID.trim().length() == 0) {
			voWh = getWhInfoByID(voWh.getCwarehouseid());
			if (voWh != null) {
				sCalBodyID = voWh.getPk_calbody();
				sCalBodyName = voWh.getVcalbodyname();
				// fresh bill
				voSp.setWhOut(voWh);
				m_alListData.set(m_iLastSelListHeadRow, voSp);
			}
		}
		gbvo[0].setHeaderValue("pk_calbody", sCalBodyID);
		gbvo[0].setHeaderValue("vcalbodyname", sCalBodyName);
		gbvo[0].setHeaderValue("vshldarrivedate", voSp
				.getHeaderValue("vshldarrivedate"));

		// 设置行号
		// setRowNo(gbvo[0]);
		BillRowNoVO.setVOsRowNoByRule(gbvo, "4I", "crowno");
		alOutGeneralVO.add(gbvo[0]);

		filterZeroBill(gbvo[0]);
		if (gbvo[0].getItemVOs() == null || gbvo[0].getItemCount() == 0) {
			return;
		}
		getAuditDlg().setIsOK(false); // 设置未保存
		getAuditDlg().setSpBill(m_voBill);
		int ret = getAuditDlg(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPP4008spec-000120")/* @res "转出单" */, null,
				alOutGeneralVO).showModal();
		// 2003-05-09.01 wnj:加上判断isOK刷新，因为保存后可以点X退出。
		if (ret == nc.ui.pub.beans.UIDialog.ID_OK || getAuditDlg().isOK()) {
			try {
				// 查出新的表单
				//SpecialBillVO voMyTempBill = (SpecialBillVO)((SpecialBillVO)m_alListData.get(m_iLastSelListHeadRow)).clone(); // 一个的表头表体数据
				QryConditionVO qcvo = new nc.vo.ic.pub.bill.QryConditionVO();
				qcvo.setQryCond("cbilltypecode='" + m_sBillTypeCode
						+ "' and cspecialhid='"
						+ m_voBill.getPrimaryKey().trim() + "'");
				// voMyTempBill= (SpecialBillVO)
				// SpecialHBO_Client.queryBills(qcvo).get(0);
				SMSpecialBillVO voMySmallBill = (SMSpecialBillVO) ((ArrayList) SpecialBillHelper
						.querySmallBills(m_sBillTypeCode, qcvo)).get(0);
				
				
			
				// 将后台信息更新到界面
				freshVOBySmallVO(voMySmallBill);
				
				
				/*voMyTempBill.setSmallVO(voMySmallBill);

				// 特殊单没有保存换算率，变动hsl必须根据数量计算
				GenMethod mthod = new GenMethod();
				mthod.calAllConvRate(voMyTempBill.getChildrenVO(), "fixedflag",
						"hsl", "dshldtransnum", "nshldtransastnum", "", "");
				//execFormula(voMyTempBill);
				m_alListData.set(m_iLastSelListHeadRow, voMyTempBill.clone());

				m_iFirstSelListHeadRow = -1;
				switchListToBill();*/
				setButtonState();
				setBillState();
				// 调出转出窗口
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"4008spec", "UPT40081002-000022")
						+ ResBase.getSuccess()/* @res "转出成功" */);

			} catch (Exception e) {
				handleException(e);
				nc.ui.pub.beans.MessageDialog.showErrorDlg(this, null, e
						.getMessage());
			}
		}
		// }
	}

	/**
	 * 创建者：仲瑞庆 功能：初始化按钮 参数： 返回： 例外： 日期：(2001-5-15 下午 3:12) 修改日期，修改人，修改原因，注释标志：
	 * 修改人：刘家清 修改日期：2008-4-3上午10:44:56 修改原因：增加浏览、首页、上页、下页、末页
	 */
	protected void initButtons() {
		m_boAdd = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"common", "UC001-0000002")/* @res "增加" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000308")/* @res "增加单据" */, 0, "增加"); /*-=notranslate=-*/
		//lxt 2013-07 gzcg
		m_boAdd4331 = new ButtonObject("发货单", "发货单", 0, "发货单");
		//lxt 2013-07 gzcg
		m_boChange = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000045")/* @res "修改" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000291")/* @res "修改单据" */, 0, "修改"); /*-=notranslate=-*/
		m_boDelete = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000039")/* @res "删除" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPP4008spec-000504")/* @res "删除单据" */, 0, "删除"); /*-=notranslate=-*/
		m_boCopyBill = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000043")/* @res "复制" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPP4008spec-000505")/* @res "复制单据" */, 0, "复制"); /*-=notranslate=-*/
		m_boJointAdd = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000003")/* @res "业务类型" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPP4008spec-000506")/* @res "选择新增单据方式" */, 0, "业务类型"); /*-=notranslate=-*/
		m_boSave = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000001")/* @res "保存" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000001")/* @res "保存" */, 0, "保存"); /*-=notranslate=-*/
		m_boCancel = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000008")/* @res "取消" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000008")/* @res "取消" */, 0, "取消"); /*-=notranslate=-*/

		m_boAddRow = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000012")/* @res "增行" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000012")/* @res "增行" */, 0, "增行"); /*-=notranslate=-*/
		m_boDeleteRow = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000013")/* @res "删行" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000013")/* @res "删行" */, 0, "删行"); /*-=notranslate=-*/
		m_boInsertRow = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000016")/* @res "插入行" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000016")/* @res "插入行" */, 0, "插入行"); /*-=notranslate=-*/
		m_boCopyRow = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000014")/* @res "复制行" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000014")/* @res "复制行" */, 0, "复制行"); /*-=notranslate=-*/
		m_boPasteRow = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000015")/* @res "粘贴行" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000015")/* @res "粘贴行" */, 0, "粘贴行"); /*-=notranslate=-*/
		m_boPasteRowTail = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("4008bill", "UPP4008bill-000556")/* @res "粘贴行到表尾" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
						"UPP4008bill-000556")/* @res "粘贴行到表尾" */, 0, "粘贴行到表尾"); /*-=notranslate=-*/
		m_boNewRowNo = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("4008bill", "UPP4008bill-000551")/* @res "重排行号" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
						"UPP4008bill-000551")/* @res "重排行号" */, 0, "重排行号"); /*-=notranslate=-*/
    m_boLineCardEdit = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
        .getStrByID("common", "SCMCOMMONIC55YB002")/* @res "卡片编辑" */,
        nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
            "SCMCOMMONIC55YB002")/* @res "卡片编辑" */, 0, "卡片编辑"); /*-=notranslate=-*/


		m_boAuditBill = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000027")/* @res "审批" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000027")/* @res "审批" */, 0, "审批"); /*-=notranslate=-*/
		m_boCancelAudit = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000028")/* @res "弃审" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000028")/* @res "弃审" */, 0, "弃审"); /*-=notranslate=-*/
		m_boQuery = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000006")/* @res "查询" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000006")/* @res "查询" */, 0, "查询"); /*-=notranslate=-*/
		// 2003-05-03联查
		m_boJointCheck = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("SCMCOMMON", "UPPSCMCommon-000145")/* @res "联查" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000145")/* @res "联查" */, 0, "联查"); /*-=notranslate=-*/

		m_boLocate = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("4008spec", "UPPSCMCommon-000089")/* @res "定位" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPPSCMCommon-000089")/* @res "定位" */, 0, "定位"); /*-=notranslate=-*/
		m_PrintMng = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000007")/* @res "打印" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000007")/* @res "打印" */, 0, "打印管理"); /*-=notranslate=-*/
		m_boPrint = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000007")/* @res "打印" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000007")/* @res "打印" */, 0, "打印"); /*-=notranslate=-*/
		m_boPreview = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("4008spec", "UPPSCMCommon-000305")/* @res "预览" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPPSCMCommon-000305")/* @res "预览" */, 0, "预览"); /*-=notranslate=-*/
		{
			m_PrintMng.addChildButton(m_boPrint);
			m_PrintMng.addChildButton(m_boPreview);
		}
		m_boList = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("SCMCOMMON", "UPPSCMCommon-000186")/* @res "切换" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000186")/* @res "切换" */, 0, "切换"); /*-=notranslate=-*/

		m_boOut = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"4008spec", "UPT40081002-000022")/* @res "转出" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPT40081002-000022")/* @res "转出" */, 0, "转出"); /*-=notranslate=-*/
		m_boIn = new ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"4008spec", "UPT40081002-000021")/* @res "转入" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPT40081002-000021")/* @res "转入" */, 0, "转入"); /*-=notranslate=-*/

		m_billMng = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("4008spec", "UPT40080816-000037")/* @res "单据维护" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000074")/* @res "单据维护操作" */, 0, "单据维护"); /*-=notranslate=-*/
		m_billRowMng = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000011")/* @res "行操作" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000011")/* @res "行操作" */, 0, "行操作"); /*-=notranslate=-*/

		m_boRowQuyQty = new ButtonObject(
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000481")/* @res "存量转库" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000481")/* @res "存量转库" */, 0, "存量转库"); /*-=notranslate=-*/
		m_boDirectOut = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("4008spec", "UPT40081002-000024")/* @res "直接转出" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
						"UPP4008spec-000507")/* @res "自动生成其它出入库单" */, 0, "直接转出"); /*-=notranslate=-*/
		// 上下翻页的控制
		m_pageBtn = new PageCtrlBtn(this);
		m_boBrowse = new ButtonObject(nc.ui.ml.NCLangRes.getInstance()
				.getStrByID("common", "UC001-0000021")/* @res "浏览" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
						"UC001-0000021")/* @res "浏览" */, 0, "浏览"); /*-=notranslate=-*/
		/*
		 * m_boTop = new
		 * ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000059")@res
		 * "首页",
		 * nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000059")@res
		 * "首页", 0,"首页"); -=notranslate=- m_boPrevious = new
		 * ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","SCMCOMMON000000163")@res
		 * "上页",
		 * nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","SCMCOMMON000000163")@res
		 * "上页", 0,"上页"); -=notranslate=- m_boNext = new
		 * ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000023")@res
		 * "下页",
		 * nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000023")@res
		 * "下页", 0,"下页"); -=notranslate=- m_boBottom = new
		 * ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000062")@res
		 * "末页",
		 * nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000062")@res
		 * "末页", 0,"末页"); -=notranslate=-
		 *//*m_boTop = m_pageBtn.getFirst();
		m_boPrevious = m_pageBtn.getPre();
		m_boNext = m_pageBtn.getNext();
		m_boBottom = m_pageBtn.getLast();*/

		m_billMng.addChildButton(m_boAdd);
		m_billMng.addChildButton(m_boAdd4331); //lxt 2013-07 gzcg
		m_billMng.addChildButton(m_boDelete);
		m_billMng.addChildButton(m_boChange);
		m_billMng.addChildButton(m_boCopyBill);
		m_billMng.addChildButton(m_boSave);
		m_billMng.addChildButton(m_boCancel);

		m_billRowMng.addChildButton(m_boAddRow);
		m_billRowMng.addChildButton(m_boDeleteRow);
		m_billRowMng.addChildButton(m_boInsertRow);
		m_billRowMng.addChildButton(m_boCopyRow);
		m_billRowMng.addChildButton(m_boPasteRow);
		m_billRowMng.addChildButton(m_boPasteRowTail);
		m_billRowMng.addChildButton(m_boNewRowNo);
    m_billRowMng.addChildButton(m_boLineCardEdit);

		m_boBrowse.addChildButton(m_pageBtn.getFirst());
		m_boBrowse.addChildButton(m_pageBtn.getPre());
		m_boBrowse.addChildButton(m_pageBtn.getNext());
		m_boBrowse.addChildButton(m_pageBtn.getLast());

		m_aryButtonGroup = new ButtonObject[] { m_billMng, m_billRowMng,
				m_boOut, m_boIn, m_boDirectOut, m_boQuery, m_boBrowse,
				m_boJointCheck, m_boRowQuyQty, m_boLocate, m_PrintMng, m_boList };

	}

	/**
	 * 创建者：仲瑞庆 功能：初始化变量 参数： 返回： 例外： 日期：(2001-5-24 下午 6:27) 修改日期，修改人，修改原因，注释标志：
	 */
	protected void initVariable() {
		// 常量
		/** 转库 */
		m_sBillTypeCode = nc.vo.ic.pub.BillTypeConst.m_transfer;
		m_sBillCode = "IC_BILL_TEMPLET_004K";
		m_sPNodeCode = "40081002";
		// sSpecialHBO_Client = "nc.ui.ic.ic221.SpecialHBO_Client";
		m_iFirstAddRows = 1;

	}

	/**
	 * 创建者：仲瑞庆 功能：VO校验 参数： 返回： 例外： 日期：(2001-5-24 下午 5:17) 修改日期，修改人，修改原因，注释标志：
	 */
	protected boolean checkVO() {
		return super.checkVO();
	}

	/**
	 * 此处插入方法说明。 创建者：仲瑞庆 功能：初始化表头表体中的参照 参数： 返回： 例外： 日期：(2001-7-17 10:33:20)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 */
	public void filterRef(String cropid) {
		super.filterRef(cropid);
		// 表体单位编码过滤存货且为非封存存货,非劳务存货，非折扣存货
		BillItem bi = getBillCardPanel().getBodyItem("cinventorycode");
		if (bi != null && bi.getComponent() != null) {
			// 过滤存货编码

			nc.ui.pub.beans.UIRefPane invRef = (nc.ui.pub.beans.UIRefPane) bi
					.getComponent();
			invRef.setTreeGridNodeMultiSelected(true);
			invRef.setMultiSelectedEnabled(true);
			// 存货参照显示现存量
			invRef.getRefModel().setIsDynamicCol(true);
			invRef.getRefModel().setDynamicColClassName(
					"nc.ui.ic.ic221.RefOnhandDynamic");

			invRef
					.setWhereString(" bd_invbasdoc.discountflag='N' and bd_invbasdoc.laborflag='N'  "
							+ "and bd_invmandoc.sealflag ='N' and bd_invmandoc.pk_corp='"
							+ cropid + "'");
		}

	}

	/**
	 * 创建者：仲瑞庆 功能：由传入的单据类型、字段，获得当该字段改变后，应改变的其他字段列表 参数：iBillFlag
	 * 单据类型，当为普通单据，传入0，当为特殊单据，传入1 已有 存货 cinventorycode， 表体仓库 cwarehousename， 自由项
	 * vfree0， 表头出库仓库 coutwarehouseid， 表头仓库 cwarehouseid 返回： 例外： 日期：(2001-7-18
	 * 上午 9:20) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return java.lang.String[]
	 * @param sWhatChange
	 *            java.lang.String
	 * 
	 */
	// protected String[] getClearIDs(int iBillFlag, String sWhatChange) {
	// if (sWhatChange == null)
	// return null;
	//  
	// String[] sReturnString = null;
	// sWhatChange = sWhatChange.trim();
	// if (sWhatChange.equals("cinventorycode")) {
	// //存货
	// sReturnString = new String[6];
	// sReturnString[0] = "vbatchcode";
	// sReturnString[1] = "vfree0";
	// sReturnString[2] = m_sNumItemKey;
	// sReturnString[3] = m_sAstItemKey;
	// //sReturnString[4]= "castunitid";
	// //sReturnString[5]= "castunitname";
	// //sReturnString[6]= "hsl";
	// sReturnString[4] = "scrq";
	// sReturnString[5] = "dvalidate";
	// } else if ((sWhatChange.equals("cwarehousename")) && (iBillFlag == 1)) {
	// //特殊单的表体行内仓库
	// sReturnString = new String[6];
	// sReturnString[0] = "vbatchcode";
	// sReturnString[2] = m_sNumItemKey;
	// sReturnString[3] = m_sAstItemKey;
	// sReturnString[4] = "scrq";
	// sReturnString[5] = "dvalidate";
	// //showWarningMessage("请重新确认批次号！");
	// return null;
	// } else if (sWhatChange.equals("vfree0")) {
	// //自由项
	// sReturnString = new String[3];
	// sReturnString[0] = "vbatchcode";
	// //sReturnString[1]= m_sNumItemKey;
	// //sReturnString[2]= m_sAstItemKey;
	// sReturnString[1] = "scrq";
	// sReturnString[2] = "dvalidate";
	// //showWarningMessage("请重新确认批次号！");
	// //修改人：刘家清 修改日期：2007-8-21下午02:45:36 修改原因：修改自由项时清批次号
	// //return null;
	// } else if (sWhatChange.equals("coutwarehouseid")) {
	// sReturnString = new String[5];
	// sReturnString[0] = "vbatchcode";
	// sReturnString[1] = m_sNumItemKey;
	// sReturnString[2] = m_sAstItemKey;
	// sReturnString[3] = "scrq";
	// sReturnString[4] = "dvalidate";
	// //showWarningMessage("请重新确认批次号！");
	// return null;
	// } else if ((sWhatChange.equals("cwarehouseid")) && (iBillFlag == 0)) {
	// sReturnString = new String[5];
	// sReturnString[0] = "vbatchcode";
	// sReturnString[1] = m_sNumItemKey;
	// sReturnString[2] = m_sAstItemKey;
	// sReturnString[3] = "scrq";
	// sReturnString[4] = "dvalidate";
	// //showWarningMessage("请重新确认批次号！");
	// return null;
	// }
	// return sReturnString;
	// }
	ButtonObject m_boDirectOut = null;

	/**
	 * ClientUI 构造子注解。 nc 2.2 提供的单据联查功能构造子。
	 * 
	 */
	public ClientUI(String pk_corp, String billType, String businessType,
			String operator, String billID) {
		super();
		// 初始化界面
		initialize(pk_corp, operator, "jc", businessType, "0001", "2003-04-17");
		// 查单据
		SpecialBillVO voBill = qryBill(pk_corp, billType, businessType,
				operator, billID);
		if (voBill == null)
			nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
					.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
							"UPP4008spec-000121")/* @res "没有符合查询条件的单据！" */);
		else
			// 显示单据
			setBillValueVO(voBill);

	}

	/**
	 * 编辑前处理,只在单据模板初始化时调用，不能用于设置billitemkey编辑属性。 创建日期：(2001-3-23 2:02:27)
	 * 
	 * @param e
	 *            ufbill.BillEditEvent
	 */
	public boolean beforeEdit(nc.ui.pub.bill.BillEditEvent e) {
		return true;
		// nc.vo.scm.pub.ctrl.GenMsgCtrl.printHint("beforeEdit:" + e.getKey());
		// boolean isEditable = super.beforeEdit(e);
		// BillItem bi = getBillCardPanel().getBodyItem(e.getKey());
		// if (isEditable) {
		// if (e.getKey().equals("cinventorycode")) {
		//
		// nc.ui.pub.beans.UIRefPane invRef = (nc.ui.pub.beans.UIRefPane)
		// bi.getComponent();
		// nc.ui.bd.ref.AbstractRefModel m = invRef.getRefModel();
		// if (getBillCardPanel().getHeadItem("coutwarehouseid") != null) {
		// String[] o =
		// new String[] { m_sCorpID,
		// getBillCardPanel().getHeadItem("coutwarehouseid").getValue()};
		// m.setUserParameter(o);
		// }
		// }
		// //向批次号传递参数
		// else if (e.getKey().equals("vbatchcode")) {
		// WhVO wvo = m_voBill.getWhOut();
		// getLotNumbRefPane().setParameter(wvo,
		// m_voBill.getItemInv(e.getRow()));
		// } else if (e.getKey().equals("dvalidate") ||
		// e.getKey().equals("scrq")) {
		//
		// isEditable = false;
		//
		// }
		// }
		// bi.setEnabled(isEditable);
		// return isEditable;

	}

	/**
	 * 每选中一个BillItem时调用，由billModel自动调用
	 */

	public boolean isCellEditable(boolean value, int row, String itemkey) {
		boolean isEditable = super.isCellEditable(value, row, itemkey);
		BillItem bi = getBillCardPanel().getBodyItem(itemkey);
		if (isEditable) {
			if (itemkey.equals("cinventorycode")) {

				nc.ui.pub.beans.UIRefPane invRef = (nc.ui.pub.beans.UIRefPane) bi
						.getComponent();
				nc.ui.bd.ref.AbstractRefModel m = invRef.getRefModel();
				if (getBillCardPanel().getHeadItem("coutwarehouseid") != null) {
					String[] o = new String[] {
							m_sCorpID,
							getBillCardPanel().getHeadItem("coutwarehouseid")
									.getValue() };
					m.setUserParameter(o);
				}
			}
			// 向批次号传递参数
			else if (itemkey.equals("vbatchcode")) {
				WhVO wvo = m_voBill.getWhOut();
				getLotNumbRefPane().setParameter(wvo, m_voBill.getItemInv(row));
			}
		}
		bi.setEnabled(isEditable);
		return isEditable;

	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-16 12:53:03) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param voBillHeader
	 *            nc.vo.ic.pub.bill.GeneralBillVO
	 * @param svo
	 *            nc.vo.ic.pub.bill.SpecialBillVO
	 * @param iInOutFlag
	 *            int
	 */
	protected GeneralBillHeaderVO changeFromSpecialVOtoGeneralVOAboutHeader(
			GeneralBillVO gvo, SpecialBillVO svo, int iInOutFlag) {

		if (iInOutFlag == InOutFlag.OUT) {
			gvo.setWh(svo.getWhOut());
		} else {
			gvo.setWh(svo.getWhIn());
		}

		GeneralBillHeaderVO voBillHeader = super
				.changeFromSpecialVOtoGeneralVOAboutHeader(gvo, svo, iInOutFlag);
		SpecialBillHeaderVO voSpHeader = svo.getHeaderVO();
		// 个性化处理
		if (iInOutFlag == InOutFlag.OUT) {
			voBillHeader.setCdptid(voSpHeader.getCoutdeptid());
		} else {
			voBillHeader.setCdptid(voSpHeader.getCindeptid());
		}
		if (iInOutFlag == InOutFlag.OUT) {
			voBillHeader.setCdptname(voSpHeader.getCoutdeptname());
		} else {
			voBillHeader.setCdptname(voSpHeader.getCindeptname());
		}
		if (iInOutFlag == InOutFlag.OUT) {
			voBillHeader.setCbizid(voSpHeader.getCoutbsor());
		} else {
			voBillHeader.setCbizid(voSpHeader.getCinbsrid());
		}
		if (iInOutFlag == InOutFlag.OUT) {
			voBillHeader.setCbizname(voSpHeader.getCoutbsorname());
		} else {
			voBillHeader.setCbizname(voSpHeader.getCinbsrname());
		}
		return voBillHeader;
	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-8-16 12:53:03) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param gbvo
	 *            nc.vo.ic.pub.bill.GeneralBillVO
	 * @param sbvo
	 *            nc.vo.ic.pub.bill.SpecialBillVO
	 * @param iInOutFlag
	 *            int
	 */
	protected GeneralBillItemVO changeFromSpecialVOtoGeneralVOAboutItem(
			GeneralBillVO gbvo, SpecialBillVO sbvo, int iInOutFlag, int j) {

		// GeneralBillItemVO voItem =
		super
				.changeFromSpecialVOtoGeneralVOAboutItem(gbvo, sbvo,
						iInOutFlag, j);
		// 总入或出值
		UFDouble ufdTotal = (sbvo.getItemValue(j, "dshldtransnum") == null ? ZERO
				: (UFDouble) sbvo.getItemValue(j, "dshldtransnum"));
		// 累入值
		UFDouble ufdAlreadyIn = (sbvo.getItemValue(j, "nadjustnum") == null ? ZERO
				: (UFDouble) sbvo.getItemValue(j, "nadjustnum"));
		// 累出值
		UFDouble ufdAlreadyOut = (sbvo.getItemValue(j, "nchecknum") == null ? ZERO
				: (UFDouble) sbvo.getItemValue(j, "nchecknum"));
		// 换算率
		UFDouble ufdHsl = (sbvo.getItemValue(j, "hsl") == null
				|| sbvo.getItemValue(j, "hsl").toString().trim().length() == 0 ? ZERO
				: (UFDouble) sbvo.getItemValue(j, "hsl"));

		// 出库时，应转库数量关系为：主数量：nshouldoutnum －》dshldtransnum - nchecknum ,
		// 辅助数量：nshouldoutassistnum－》dshldtransnum - nchecknum/hsl
		// 入库时，应转库数量关系为：主数量：nshouldinnum－>nchecknum-nadjustnum
		// ,辅助数量：nneedinassistnum－》nchecknum-nadjustnum/hsl
		if (iInOutFlag == InOutFlag.OUT) {
			gbvo.setItemValue(j, "nshouldoutnum", ufdTotal.sub(ufdAlreadyOut));
			if (sbvo.getItemValue(j, "nshldtransastnum") != null
					&& sbvo.getItemValue(j, "nshldtransastnum").toString()
							.trim().length() != 0 && ufdHsl.doubleValue() != 0) {
				gbvo.setItemValue(j, "nshouldoutassistnum", ufdTotal.sub(
						ufdAlreadyOut).div(ufdHsl));
			}
			// 2004-10-13 天音要求不填实发数量
			// gbvo.setItemValue(j, "noutnum", ufdTotal.sub(ufdAlreadyOut));

			gbvo.setItemValue(j, "ninnum", null);
			if (sbvo.getItemValue(j, "nshldtransastnum") != null
					&& sbvo.getItemValue(j, "nshldtransastnum").toString()
							.trim().length() != 0 && ufdHsl.doubleValue() != 0) {
				gbvo.setItemValue(j, "noutassistnum", ufdTotal.sub(
						ufdAlreadyOut).div(ufdHsl));
				gbvo.setItemValue(j, "ninassistnum", null);
			}

		} else {
			// gbvo.setItemValue(j, sBodyItemKeyName[i],
			// ufdTotal.sub(ufdAlreadyIn));
			// 根据刘剑锋亚大新需求:转库,其它入库数量改为在途数量(累计出-累计入)
			gbvo.setItemValue(j, "nshouldinnum", ufdAlreadyOut
					.sub(ufdAlreadyIn));
			if (sbvo.getItemValue(j, "nshldtransastnum") != null
					&& sbvo.getItemValue(j, "nshldtransastnum").toString()
							.trim().length() != 0 && ufdHsl.doubleValue() != 0) {
				// gbvo.setItemValue(j, sBodyItemKeyName[i],
				// ufdTotal.sub(ufdAlreadyIn).div(ufdHsl));
				// 根据刘剑锋亚大新需求:转库,其它入库数量改为在途数量(累计出-累计入)
				gbvo.setItemValue(j, "nneedinassistnum", ufdAlreadyOut.sub(
						ufdAlreadyIn).div(ufdHsl));

			}

			// gbvo.setItemValue(j, sBodyItemKeyName[i],
			// ufdTotal.sub(ufdAlreadyIn));
			// 根据刘剑锋亚大新需求:转库,其它入库数量改为在途数量(累计出-累计入)
			// 2004-10-13 天音要求不填实发数量
			// gbvo.setItemValue(j, "ninnum", ufdAlreadyOut.sub(ufdAlreadyIn));
			gbvo.setItemValue(j, "noutnum", null);
			if (sbvo.getItemValue(j, "nshldtransastnum") != null
					&& sbvo.getItemValue(j, "nshldtransastnum").toString()
							.trim().length() != 0 && ufdHsl.doubleValue() != 0) {

				// gbvo.setItemValue(j, sBodyItemKeyName[i],
				// ufdTotal.sub(ufdAlreadyIn).div(ufdHsl));
				// 根据刘剑锋亚大新需求:转库,其它入库数量改为在途数量(累计出-累计入)
				gbvo.setItemValue(j, "ninassistnum", ufdAlreadyOut.sub(
						ufdAlreadyIn).div(ufdHsl));
				gbvo.setItemValue(j, "noutassistnum", null);
			}
		}
		// 2004-10-13 天音要求不填实发数量
		gbvo.setItemValue(j, "dbizdate", null);
		return (GeneralBillItemVO) gbvo.getItemVOs()[j];
	}

	/**
	 * 简单初始化类。按传入参数，不读环境设置的操作员，公司等。
	 */
	/* 警告：此方法将重新生成。 */
	protected void initialize(String pk_corp, String sOperatorid,
			String sOperatorname, String sBiztypeid, String sGroupid,
			String sLogDate) {
		try {
			initVariable();
			super.initialize(pk_corp, sOperatorid, sOperatorname, sBiztypeid,
					sGroupid, sLogDate);
			// 修改人：刘家清 修改日期：2007-12-26上午11:05:02 修改原因：右键增加"重排行号"
			UIMenuItem[] oldUIMenuItems = getBillCardPanel().getBodyMenuItems();
			if (oldUIMenuItems.length > 0) {
				ArrayList<UIMenuItem> newMenuList = new ArrayList<UIMenuItem>();
				for (UIMenuItem oldUIMenuItem : oldUIMenuItems)
					newMenuList.add(oldUIMenuItem);
				newMenuList.add(getAddNewRowNoItem());
        newMenuList.add(getLineCardEditItem());
				getAddNewRowNoItem().removeActionListener(this);
				getAddNewRowNoItem().addActionListener(this);
        getLineCardEditItem().removeActionListener(this);
        getLineCardEditItem().addActionListener(this);
				UIMenuItem[] newUIMenuItems = new UIMenuItem[newMenuList.size()];
				m_Menu_AddNewRowNO_Index = newMenuList.size() - 1;
				newMenuList.toArray(newUIMenuItems);
				// getBillCardPanel().setBodyMenu(newUIMenuItems);
				getBillCardPanel().getBodyPanel().setMiBody(newUIMenuItems);
				getBillCardPanel().getBodyPanel().setBBodyMenuShow(true);
				getBillCardPanel().getBodyPanel().addTableBodyMenu();

			}
			
		} catch (java.lang.Throwable ivjExc) {
			handleException(ivjExc);
		}

	}

	/**
	 * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2002-1-25 14:38:12) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return boolean
	 */
	protected boolean isCanPressCopyButton() {

		if (m_alListData != null && m_iLastSelListHeadRow >= 0
				&& m_iLastSelListHeadRow < m_alListData.size()
				&& m_alListData.get(m_iLastSelListHeadRow) != null) {
			SpecialBillVO vo = (SpecialBillVO) m_alListData
					.get(m_iLastSelListHeadRow);
			SpecialBillItemVO voaItems[] = vo.getItemVOs();
			if (voaItems != null && voaItems.length > 0 && voaItems[0] != null) {
				Object obj = voaItems[0].getCsourcebillhid();
				if (obj == null || obj.toString().trim().length() == 0)
					return true;
				else
					return false;
			}
		}
		return false;
	}

	/**
	 * 创建者：仲瑞庆 功能：指定为非负项 参数： 返回： 例外： 日期：(2001-12-5 14:31:47) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param sFieldCode
	 *            java.lang.String
	 * @param bcp
	 *            nc.ui.pub.bill.BillCardPanel
	 * @param vo
	 *            nc.vo.ic.pub.bill.SpecialBillVO
	 */
	protected void mustNoNegative(String sFieldCode, int iRow,
			BillCardPanel bcp, SpecialBillVO vo) {
		// 2002-10-18.01:王乃军:转库单可为负数
		// do nothing here.
	}

	/**
	 * 李俊 功能： 参数： 返回： 例外： 日期：(2005-5-12 11:13:49) 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param voHead
	 *            nc.vo.ic.pub.bill.SpecialBillHeaderVO
	 */
	protected void qryCalbodyByWhid(SpecialBillHeaderVO voHead) {

		String sWhID = voHead.getCoutwarehouseid();
		String sCalID = null;
		try {
			if (sCalID == null && sWhID != null) {
				sCalID = (String) ((Object[]) nc.ui.scm.pub.CacheTool
						.getCellValue("bd_stordoc", "pk_stordoc", "pk_calbody",
								sWhID))[0];
				((SpecialBillHeaderVO) voHead).setPk_calbody_in(sCalID);
				((SpecialBillHeaderVO) voHead).setPk_calbody_out(sCalID);

			}
		} catch (Exception e1) {
			nc.vo.scm.pub.SCMEnv.error(e1);
		}

	}

	/**
	 * 创建者：仲瑞庆 功能：直接转出 参数： 返回： 例外： 日期：(2001-5-10 下午 2:50) 修改日期，修改人，修改原因，注释标志：
	 * 2004-01-05 hanwei 直接转出不能转出负数的序列号存货
	 */
	public void onDirectOut() {
		if (m_alListData == null || m_alListData.size() < m_iLastSelListHeadRow
				|| m_iLastSelListHeadRow < 0 || m_iMode != BillMode.Browse) {

			return;
		}

		// 调出转出窗口
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec",
				"UPT40081002-000022")/* @res "转出" */);

		ArrayList alOutGeneralVO = new ArrayList();
		SpecialBillVO voSp = (SpecialBillVO) m_alListData
				.get(m_iLastSelListHeadRow);
		
		
		/**  
		 * 重后台查询该特殊业务单据 ， 做TS检查  qinchao 2009-05-06
		 * */
		try{
			
			nc.ui.ic.pub.tools.GenMethod.callICEJBService("nc.bs.ic.pub.check.CheckDMO",
					"checkTimeStamp",
					new Class[] {nc.vo.pub.AggregatedValueObject.class},
					new Object[] {voSp });
			
		    
		} catch (Exception e) {
			nc.vo.scm.pub.SCMEnv.out("校验TS出现不一致！"); /*-=notranslate=-*/
			showErrorMessage(e.getMessage());
			handleException(e);
			return ;
		}
		
		
		// 将特殊单入库VO通过VO对照转换为普通单VO
		GeneralBillVO[] gbvo = pfVOConvert(new SpecialBillVO[] { voSp }, "4K",
				"4I");
		if (gbvo == null || gbvo[0] == null) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008spec", "UPP4008spec-000118")/* @res "转单失败！" */);
			return;
		}
		// //设置行号
		// setRowNo(gbvo[0]);
		// alOutGeneralVO.add(gbvo[0]);
		
		// 获取仓库数据
		WhVO voWh = voSp.getWhOut();

		if (voWh == null) {
			nc.vo.scm.pub.SCMEnv.out("no wh ,data err.");
			return;
		}
		// 需要库存组织
		String sCalBodyID = voWh.getPk_calbody();
		String sCalBodyName = voWh.getVcalbodyname();
		// -------------- 没有的化，读一下 ------------
		if (sCalBodyID == null || sCalBodyID.trim().length() == 0) {
			voWh = getWhInfoByID(voWh.getCwarehouseid());
			if (voWh != null) {
				sCalBodyID = voWh.getPk_calbody();
				sCalBodyName = voWh.getVcalbodyname();
				// fresh bill
				voSp.setWhOut(voWh);
				m_alListData.set(m_iLastSelListHeadRow, voSp);
			}
		}
		gbvo[0].setHeaderValue("pk_calbody", sCalBodyID);
		gbvo[0].setHeaderValue("vcalbodyname", sCalBodyName);
		gbvo[0].setHeaderValue("vshldarrivedate", voSp
				.getHeaderValue("vshldarrivedate"));

		// 入库
		ArrayList alInGeneralVO = new ArrayList();
		GeneralBillVO[] gbvo1 = pfVOConvert(new SpecialBillVO[] { voSp }, "4K",
				"4A");

		if (gbvo1 == null || gbvo1[0] == null) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008spec", "UPP4008spec-000118")/* @res "转单失败！" */);
			return;
		}
		
		
		// 获取仓库数据
		WhVO voInWh = voSp.getWhIn();

		if (voWh == null) {
			nc.vo.scm.pub.SCMEnv.out("no wh ,data err.");
			return;
		}
		// 需要库存组织
		String sInCalBodyID = voInWh.getPk_calbody();
		String sInCalBodyName = voInWh.getVcalbodyname();
		// -------------- 没有的化，读一下 ------------
		if (sInCalBodyID == null || sInCalBodyID.trim().length() == 0) {
			voInWh = getWhInfoByID(voInWh.getCwarehouseid());
			if (voWh != null) {
				sInCalBodyID = voInWh.getPk_calbody();
				sInCalBodyName = voInWh.getVcalbodyname();
				// fresh bill
				voSp.setWhIn(voInWh);
				m_alListData.set(m_iLastSelListHeadRow, voSp);
			}
		}

		gbvo1[0].setHeaderValue("pk_calbody", sInCalBodyID);
		gbvo1[0].setHeaderValue("vcalbodyname", sInCalBodyName);

		// 将出库的单价转换到入库单据上
		for (int i = 0; i < gbvo[0].getItemCount(); i++) {
			gbvo1[0].setItemValue(i, "ninnum", gbvo[0].getItemValue(i,
					"noutnum"));
			gbvo1[0].setItemValue(i, "ninassistnum", gbvo[0].getItemValue(i,
					"noutassistnum"));
			gbvo1[0].setItemValue(i, "nshouldinnum", gbvo[0].getItemValue(i,
					"nshouldoutnum"));
			gbvo1[0].setItemValue(i, "nneedinassistnum", gbvo[0].getItemValue(
					i, "nshouldoutassistnum"));

		}

		// 过滤转出
		filterZeroBill(gbvo[0]);
		if (gbvo[0].getItemVOs() == null || gbvo[0].getItemCount() == 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"4008spec", "UPP4008spec-000580")/* @res "没有可转数量！" */);
			return;
		}
		// 设置行号
		setRowNo(gbvo[0]);
		alOutGeneralVO.add(gbvo[0]);

		// 过滤转入
		filterZeroBill(gbvo1[0]);
		if (gbvo1[0].getItemVOs() == null || gbvo1[0].getItemCount() == 0) {
			return;
		}
		// 设置行号
		setRowNo(gbvo1[0]);
		alInGeneralVO.add(gbvo1[0]);

		getAuditDlg().setIsOK(false); // 设置未保存
		getAuditDlg().setSpBill(m_voBill);
		getAuditDlg().setVO4Direct(alInGeneralVO, alOutGeneralVO,
				BillTypeConst.m_transfer, m_voBill.getPrimaryKey().trim(),
				m_sCorpID, m_sUserID);
		// 直接转出不能转出负数的序列号存货 2004-01-05 hanwei
		if (nc.ui.ic.pub.bill.GeneralBillUICtl.isSeriaoInvNegative(gbvo[0])) {
			nc.ui.pub.beans.MessageDialog
					.showErrorDlg(
							this,
							nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"SCMCOMMON", "UPPSCMCommon-000059")/*
																		 * @res
																		 * "错误"
																		 */,
							nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"4008spec", "UPP4008spec-000122")/*
																		 * @res
																		 * "直接转库情况下，序列号管理的存货列应发数量不能小于0!"
																		 */);
			return;
		}

		int ret = getAuditDlg().showModal();
		// 2003-05-09.01 wnj:加上判断isOK刷新，因为保存后可以点X退出。
		if (ret == nc.ui.pub.beans.UIDialog.ID_OK || getAuditDlg().isOK()) {
			try {
				// 查出新的表单
				//SpecialBillVO voMyTempBill = null; // 一个的表头表体数据
				QryConditionVO qcvo = new nc.vo.ic.pub.bill.QryConditionVO();
				qcvo.setQryCond("cbilltypecode='" + m_sBillTypeCode
						+ "' and cspecialhid='"
						+ m_voBill.getPrimaryKey().trim() + "'");
				SMSpecialBillVO voMySmallBill = (SMSpecialBillVO) ((ArrayList) SpecialBillHelper
						.querySmallBills(m_sBillTypeCode, qcvo)).get(0);
				
				// 将后台信息更新到界面
				freshVOBySmallVO(voMySmallBill);
/*				voMyTempBill = (SpecialBillVO) ((ArrayList) SpecialBillHelper
						.queryBills(m_sBillTypeCode, qcvo)).get(0);
				// 特殊单没有保存换算率，变动hsl必须根据数量计算
				GenMethod mthod = new GenMethod();
				mthod.calAllConvRate(voMyTempBill.getChildrenVO(), "fixedflag",
						"hsl", "dshldtransnum", "nshldtransastnum", "", "");
				execFormula(voMyTempBill);
				// 修改人：刘家清 修改日期：2007-11-14下午04:53:00 修改原因：执行批次号档案公式
				BatchCodeDefSetTool.execFormulaForBatchCode(voMyTempBill
						.getItemVOs());

				m_alListData.set(m_iLastSelListHeadRow, voMyTempBill.clone());

				m_iFirstSelListHeadRow = -1;
				switchListToBill();*/
				setButtonState();
				setBillState();
			} catch (Exception e) {
				getAuditDlg().setIsDirectOut(false);
				handleException(e);
				nc.ui.pub.beans.MessageDialog
						.showErrorDlg(
								this,
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"SCMCOMMON", "UPPSCMCommon-000059")/*
																			 * @res
																			 * "错误"
																			 */,
								e.getMessage());
			}
		}
		getAuditDlg().setIsDirectOut(false);

	}

	/**
	 * V501新增功能，过滤数量为零的记录 zhanghaiyan 2006-12-29
	 */
	private void filterZeroBill(GeneralBillVO vo) {
		GeneralBillItemVO[] voItems = vo.getItemVOs();

		Vector<GeneralBillItemVO> v = new Vector<GeneralBillItemVO>();
		if (voItems != null && voItems.length > 0) {
			for (int i = 0; i < voItems.length; i++) {
				if (!GenMethod.isNotEqualsZero(voItems[i].getNshouldinnum())
						&& !GenMethod.isNotEqualsZero(voItems[i]
								.getNshouldoutnum()))
					continue;
				v.add(voItems[i]);
			}
		}
		voItems = null;
		if (v != null || v.size() > 0) {
			for (int i = 0; i < v.size(); i++) {
				voItems = new GeneralBillItemVO[v.size()];
				v.copyInto(voItems);
			}
		}
		vo.setChildrenVO(voItems);
	}
	
	/**
	 * 创建人：刘家清 创建时间：2008-7-29 上午11:17:20 创建原因： 支持选择存量出库的功能
	 * @param selectVOs
	 */
	public void afterSelectOnhandVOs(nc.vo.ic.pub.InvOnHandVO[] selectVOs){
		if (null != selectVOs && 0 < selectVOs.length){
			Integer iCurRow = getBillCardPanel().getBillTable().getSelectedRow();
			/*if (1 < selectVOs.length)
				nc.ui.scm.pub.report.BillRowNo.addLineRowNos(
						getBillCardPanel(),
						m_sBillTypeCode,
						m_sBillRowNo,selectVOs.length -1);*/
			ArrayList<String> refPkList = new ArrayList<String>();
			for(nc.vo.ic.pub.InvOnHandVO selectVO:selectVOs)
				refPkList.add(selectVO.getCinventoryid());
			String[] refPks = new String[refPkList.size()];
			refPkList.toArray(refPks);
			try{
				//仓库和库存组织信息
				String sWhID = null;
				String sCalID = null;
				if (getBillCardPanel().getHeadItem(m_sMainWhItemKey) != null) {
					sWhID = getBillCardPanel().getHeadItem(m_sMainWhItemKey).getValue();
				}
				long ITime = System.currentTimeMillis();
			
				if (isQuryPlanprice())
				{
				if (sCalID == null && sWhID != null) {
					
				    sCalID  = (String) ((Object[]) nc.ui.scm.pub.CacheTool.getCellValue(
							"bd_stordoc",
							"pk_stordoc",
							"pk_calbody",
							sWhID))[0];
					
				}
				}
			
				SCMEnv.showTime(ITime, "存货解析参数设置:"); /*-=notranslate=-*/
			
				ITime = System.currentTimeMillis();
				//存货解析
				InvVO[] invVOs = null;
				if (isQuryPlanprice())
				    invVOs = getInvoInfoBYFormula().getInvParseWithPlanPrice(refPks, sWhID, sCalID,true,true);
				 else
				    invVOs =getInvoInfoBYFormula().getBillQuryInvVOs(refPks,true,true);
			
				SCMEnv.showTime(ITime, "存货解析:"); /*-=notranslate=-*/
			
			
				ITime = System.currentTimeMillis();
				
				for(int i=0;i<invVOs.length;i++){
					
					// 修改人：陈倪娜 下午02:04:54_2009-10-26 修改原因:存量转库辅计量带入有错
					invVOs[i].setCastunitid(selectVOs[i].getCastunitid());
					invVOs[i].setCastunitname(selectVOs[i].getCastunitcodename());
					
					invVOs[i].setVbatchcode(selectVOs[i].getVbatchcode());
					invVOs[i].setCspaceid(selectVOs[i].getCspaceid());
					if (null != invVOs[i].getFreeItemVO()){
						for (int j = 1; j < 10; j++) {
							invVOs[i].setAttributeValue(
							"vfree" + j,
							selectVOs[i].getAttributeValue("vfree" + j));
						}
						invVOs[i].setAttributeValue(
								"vfree0" ,
								invVOs[i].getAttributeValue("vfree0"));
					}
					
				}
				// addied by liuzy 2010-8-13 下午09:04:13 处理换算率
				getInvoInfoBYFormula().getInVoOfHSL(invVOs);
				// added by lirr 2009-10-30下午03:46:16 存量转出时不应该清除批次
				afterInvMutiEdit(invVOs,iCurRow,true);
				
				for (int i = 0 ; i< selectVOs.length;i++){
					if (null != selectVOs[i].getNmaster())
						getBillCardPanel().setBodyValueAt(selectVOs[i].getNmaster(), iCurRow + i, "dshldtransnum");
					if (null != selectVOs[i].getNassist())
						getBillCardPanel().setBodyValueAt(selectVOs[i].getNassist(), iCurRow + i, "nshldtransastnum");
					if(null != selectVOs[i].getM_cvendorid())
						getBillCardPanel().setBodyValueAt(selectVOs[i].getM_cvendorid(), iCurRow + i, "cvendorid");
					// addied by liuzy 2010-11-17 上午11:14:52 带入辅计量、取到换算率、带入数量后，需要计算一下
					calculateByHsl(iCurRow, m_sNumItemKey, m_sAstItemKey, 0);
				}
				
				getBillCardPanel().getBillModel().execLoadFormula();

			}catch(Exception e1){
				showErrorMessage(e1.getMessage());
			
			}
			
		}
	}
	
	OnhandNumTransOutDialog dlg = null;
	
	protected OnhandNumTransOutDialog getIohdDlg() {
		if (null == dlg) {
			dlg= new OnhandNumTransOutDialog(this,nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
			"UPPSCMCommon-000481")/* @res "存量转库" */,m_sBillTypeCode);
		}
		return dlg;
	}
  
  protected QueryDlgHelpForSpec getQueryHelp() {
    if(this.m_queryHelp==null){
      this.m_queryHelp = new QueryDlgHelpForSpec(this){
        protected void init() {
          super.init();
          getQueryDlg().setLogFields(new String[]{
              "qbillstatus",
              "boutnumnull",
              "freplenishflag",
              "dbilldate.from",
              "dbilldate.end",
              "head.pk_calbody",
              "pk_calbody",
              "head.cwarehouseid",
              "cwarehouseid",
              "pk_corp",
              "head.pk_corp",
              "coutwarehouseid",
              "cinwarehouseid",
              "coutdeptid",
              "cindeptid"
          });
        }
        
        protected DataPowerCtl getDataPowerCtl() {
          if(datapowerctl==null){
            datapowerctl = new DataPowerCtl(ClientEnvironment.getInstance().getUser().getPrimaryKey(),
                ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
            QueryDlgUtil.setDataPowerCtlFields(datapowerctl,new String[]{
                "coutwarehouseid",//去掉“head.”这样在查询模板里的”权限SQL是否添加isNull“才能生效 wanghna 2010.10.30
                "head.coutdeptid",
                "head.coutbsor",
                "cinwarehouseid",//去掉“head.”这样在查询模板里的”权限SQL是否添加isNull“才能生效 wanghna 2010.10.30
                "head.cindeptid",
                "head.cinbsrid",
                
//                "body.cwarehouseid",
                "body.cinventoryid",
                "body.cinventoryid",
                "body.cvendorid",
                "body.cspaceid",
            }, 
            new BD_TYPE[]{
                BD_TYPE.Warehouse,
                BD_TYPE.Dept,
                BD_TYPE.Psn,
                BD_TYPE.Warehouse,
                BD_TYPE.Dept,
                BD_TYPE.Psn,
                
//                BD_TYPE.Warehouse,          
                BD_TYPE.InvCl,
                BD_TYPE.Inv,
                BD_TYPE.Cust,
                BD_TYPE.CargoDoc,
            });
          }
          return datapowerctl;
        }
      };
    }
    return this.m_queryHelp;
  }
  
  @Override
  public void onPrint() {

//    调出打印窗口
    //依当前是列表还是表单而定打印内容
    if (m_iMode == BillMode.Browse) { //浏览
      filterNullLine();
      //准备数据
      SpecialBillVO vo =
        (SpecialBillVO) getBillCardPanel().getBillValueVO(
          SpecialBillVO.class.getName(),
          SpecialBillHeaderVO.class.getName(),
          SpecialBillItemVO.class.getName());
      
      String sOutwharehousename = null,sInwharehousename = null;
      UIRefPane uiref;
      if(null != getBillCardPanel().getHeadItem("cinwarehouseid")){
        uiref = (UIRefPane)getBillCardPanel().getHeadItem("cinwarehouseid").getComponent();
        sInwharehousename = uiref.getText();
        vo.getHeaderVO().setCinwarehousename(sInwharehousename);
      }
      
      if(null != getBillCardPanel().getHeadItem("coutwarehouseid")){
        uiref = (UIRefPane)getBillCardPanel().getHeadItem("coutwarehouseid").getComponent();
        sOutwharehousename = uiref.getText();
        vo.getHeaderVO().setCoutwarehousename(sOutwharehousename);
      }
      
      if (null == vo || null == vo.getParentVO() || null == vo.getChildrenVO()) {
          return;
      }

      if (getPrintEntry().selectTemplate() < 0)
        return;

      String sBillID = vo.getPrimaryKey();//单据主表的ID
        ScmPrintlogVO voSpl = new ScmPrintlogVO();
        voSpl.setCbillid(sBillID);
        voSpl.setVbillcode(vo.getVBillCode());//传入单据号，用于显示。
        voSpl.setCbilltypecode(vo.getBillTypeCode());//单据类型编码
        voSpl.setCoperatorid((String) (vo.getParentVO().getAttributeValue("coperatorid")) );//操作员ID
        voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));//固定
        voSpl.setPk_corp(m_sCorpID);//公司
        voSpl.setTs((String)(vo.getParentVO().getAttributeValue("ts")));//单据主表的TS

        nc.vo.scm.pub.SCMEnv.out("ts=========tata" + voSpl.getTs());
      nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();
      plc.setPrintEntry(getPrintEntry());
      //设置单据信息
      plc.setPrintInfo(voSpl);
      //设置ts和printcount刷新监听.
      plc.addFreshTsListener(this);
      //设置打印监听
      getPrintEntry().setPrintListener(plc);

      //向打印置入数据源，进行打印
      getDataSource().setVO(vo);
      getPrintEntry().setDataSource(getDataSource());
      getPrintEntry().print();

    } else { //列表
      if (null == m_alListData || m_alListData.size() == 0) {
        return;
      }

      if (getPrintEntry().selectTemplate() < 0)
        return;
      ArrayList alBill = getSelectedBills();
      if (alBill == null)
        return;

      SpecialBillVO vo = null;
      nc.ui.scm.print.PrintLogClient plc = new nc.ui.scm.print.PrintLogClient();
      plc.setBatchPrint(true);//设置是批打
      PrintDataInterface ds = null;
      //设置打印监听
      getPrintEntry().setPrintListener(plc);
      plc.setPrintEntry(getPrintEntry());

      //设置TS刷新监听.
      plc.addFreshTsListener(this);
      //打印操作
      try{
          getPrintEntry().beginBatchPrint();
          for (int i = 0; i < alBill.size(); i++) {
              vo = (SpecialBillVO)alBill.get(i);

              ScmPrintlogVO voSpl = new ScmPrintlogVO();
            voSpl.setCbillid(vo.getPrimaryKey());
            voSpl.setVbillcode(vo.getVBillCode());//传入单据号，用于显示。
            voSpl.setCbilltypecode(vo.getBillTypeCode());//单据类型编码
            voSpl.setCoperatorid((String) (vo.getParentVO().getAttributeValue("coperatorid")) );//操作员ID
            voSpl.setIoperatetype(new Integer(PrintConst.PAT_OK));//固定
            voSpl.setPk_corp(m_sCorpID);//公司
            voSpl.setTs((String)(vo.getParentVO().getAttributeValue("ts")));//单据主表的TS

            nc.vo.scm.pub.SCMEnv.out("ts=========tata" + voSpl.getTs());
            //设置单据信息
          plc.setPrintInfo(voSpl);

          if (plc.check()) {//检查通过才执行打印，有错误的话自动插入打印日志，这里不用处理。
               ds = getNewDataSource();
               ds.setVO(vo);
               getPrintEntry().setDataSource(ds);

               //常量定义在Setup（很小）中，在现场很容易定制它。
//               while (getPrintEntry().dsCountInPool() > PrintConst.PL_MAX_TAST_NUM) {
//                   Thread.currentThread().sleep(PrintConst.PL_SLEEP_TIME); //如果有PL_MAX_TAST_NUM个以上任务，就等待PL_SLEEP_TIME秒。
//               }
           }
          }
          getPrintEntry().endBatchPrint();

      } catch (Exception e) {
        nc.vo.scm.pub.SCMEnv.error(e);
        showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill","UPP4008bill-000051")/*@res "打印出错！\n"*/ + e.getMessage());
      }
    }
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000133")/*@res "就绪"*/);

  }
  
}