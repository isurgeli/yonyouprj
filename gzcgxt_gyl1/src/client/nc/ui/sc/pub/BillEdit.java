package nc.ui.sc.pub;

import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import nc.ui.pu.pub.PubHelper;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.sc.order.OrderHelper;
import nc.ui.scm.ic.freeitem.FreeItemRefPane;
import nc.ui.scm.ic.measurerate.InvMeasRate;
import nc.ui.scm.inv.InvTool;
import nc.ui.scm.pub.CacheTool;
import nc.ui.scm.pub.redun.RedunListPanel;
import nc.vo.mm.pub.pub1020.DisConditionVO;
import nc.vo.mm.pub.pub1020.DisassembleVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.formulaset.util.StringUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.sc.pub.BD_ConvertVO;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.redun.RedunUtil;

/**
 * 编辑单据时数据关系
 * 创建日期：(2001-8-31 8:46:40)
 * @author：xjl
 */
public class BillEdit {

/**
 * EditDeptAndEmployee 构造子注解。
 */
public BillEdit() {
	super();
}
/**
 * 此处插入方法说明。
 * 功能描述:对自由项管理的存货，自由项必输
 * 输入参数:
 * 返回值:
 * 异常处理:
 * 日期:
 * @return boolean
 * @param panel nc.ui.pub.bill.BillCardPanel
 */
public static boolean checkFreeInput(BillCardPanel panel) {

	panel.stopEditing();
	boolean flag = true;
	int rowCount = panel.getRowCount();
	if(rowCount <= 0) return true;

	int nCol = -1;
	for(int i = 0; i < panel.getBillTable().getColumnCount(); i++){
//		String s = panel.getBillTable().getColumnName(i);
		String s = panel.getBodyPanel().getBodyKeyByCol(i);
//		if(s.equals("自由项")){
		if(s.equals("vfree0")){
			nCol = i;
			break;
		}
	}

	String errMsg = "";
	for(int i = 0; i < rowCount; i ++){
		Object o = panel.getBodyValueAt(i, "invvo");
		if(o == null) continue;
		
		//Modified by xhq 2003/05/03 begin
		//if(vo.getIsFreeItemMgt().intValue() == 1){
		//int nCol = panel.getBodyColByKey("vfree0");
		if(nCol < 0) continue;
		if(panel.getBillTable().isCellEditable(i,nCol)){
		//Modified by xhq 2003/05/03 end
			String free0 = panel.getBodyValueAt(i, "vfree0") == null ? null :panel.getBodyValueAt(i, "vfree0").toString();
			if(free0 == null || free0.trim().length() == 0){
				errMsg += errMsg.trim().length() > 0 ? "," + (i + 1) : "" +(i + 1);
				}
			}
		}
	if(errMsg.length() > 0) {
		nc.ui.pub.beans.MessageDialog.showErrorDlg(panel, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000059")/*@res "错误"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000002",null,new String[]{errMsg})/*@res "第{0}行自由项不可为空"*/);
		return false;
		}
	else
		return flag;
}
/**
 * 是否定义了自由项
 * 创建日期：(2001-11-13 15:54:54)
 * @return boolean
 */
static public boolean definedFreeItem(nc.vo.scm.ic.bill.FreeVO freeVO) {
	if (freeVO == null) return false;
	if (freeVO.getVfreeid1() != null || freeVO.getVfreeid2() != null || freeVO.getVfreeid3() != null)
		return true;
	if (freeVO.getVfreeid4() != null || freeVO.getVfreeid5() != null || freeVO.getVfreeid6() != null)
		return true;
	if (freeVO.getVfreeid7() != null || freeVO.getVfreeid8() != null || freeVO.getVfreeid9() != null)
		return true;
	if (freeVO.getVfreeid10() != null)
		return true;

	return false;
}
/**
 * 计划到货日期
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editArrDate(BillCardPanel billcardPanel, int rowindex, String key, UFDate curDate) {

	try {
		Object pk_invbasdoc = billcardPanel.getBodyValueAt(rowindex, "cbaseid");
		if (pk_invbasdoc == null || pk_invbasdoc.toString().trim().equals(""))
			return;
		Object dplanarrdate = billcardPanel.getBodyValueAt(rowindex, "dplanarrvdate");
		if (key.equals("nordernum") && dplanarrdate != null && !dplanarrdate.toString().trim().equals(""))
			return;
		//如果编辑存货但数量为空，不处理计划到货日期
		if (key.equals("cinventorycode") && billcardPanel.getBodyValueAt(rowindex, "nordernum") == null)
			return;
		int advancedDays = getAdvanceDays(billcardPanel, rowindex);

		billcardPanel.setBodyValueAt(curDate.getDateAfter(advancedDays), rowindex, "dplanarrvdate");
	}
	catch (Exception e) {
		SCMEnv.out(e);
		return;

	}

}
/**
 * 编辑数量、辅计量单位、辅计量数量，若辅计量单位对某存货为固定换算率，
 * 则数量改变维护辅计量数量，辅计量数量改变维护数量；
 * 否则改变辅数量或换算率，主数量随之变化。改变主数量，显示的换算率随之变化
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editAssistUnit(BillCardPanel billcardPanel, int rowindex, String key) {

	try {
		//是否辅计量管理
		Object isAssist = billcardPanel.getBodyValueAt(rowindex, "isassist");
		if (isAssist == null || isAssist.equals("N")) {
			billcardPanel.setBodyValueAt("", rowindex, "cassistunitname");
			billcardPanel.setBodyValueAt("", rowindex, "measrate");
			billcardPanel.setBodyValueAt("", rowindex, "nassistnum");
			billcardPanel.setCellEditable(rowindex, "measrate", false);
			billcardPanel.setCellEditable(rowindex, "nassistnum", false);
			billcardPanel.setCellEditable(rowindex, "cassistunitname", false);
			billcardPanel.setCellEditable(rowindex, "nordernum", true);
			return;
		}
		billcardPanel.setCellEditable(rowindex, "measrate", true);
		billcardPanel.setCellEditable(rowindex, "nassistnum", true);
		billcardPanel.setCellEditable(rowindex, "cassistunitname", true);
		Object oAssistnum = billcardPanel.getBodyValueAt(rowindex, "nassistnum");
		Object oNum = billcardPanel.getBodyValueAt(rowindex, "nordernum");

		// 存货基本档案ID
		String pk_invbasdoc = (String)billcardPanel.getBodyValueAt(rowindex, "cbaseid");
		// 当前存货的主计量单位
		Object pk_measdoc = billcardPanel.getBodyValueAt(rowindex, "pk_measdoc"); 
		// 辅计量单位ID
		String pk_assistmeas = (String)billcardPanel.getBodyValueAt(rowindex, "cassistunit");
		if (key.equals("cinventorycode")) {
			billcardPanel.setBodyValueAt("", rowindex, "nordernum");
			// 当前公司
			String pk_corp = (String)billcardPanel.getHeadItem("pk_corp").getValueObject();
			// 当前存货管理档案ID
			String cmangid = (String)billcardPanel.getBodyValueAt(rowindex, "cmangid");
			// 辅计量参照
			UIRefPane measRef = (UIRefPane) billcardPanel.getBodyItem("cassistunitname").getComponent();
			
			new InvMeasRate().filterMeas(pk_corp, cmangid, measRef);
		}

		if (pk_invbasdoc == null || pk_assistmeas == null) {
			billcardPanel.setBodyValueAt("", rowindex, "nassistnum");
			return;
		}

		//获得是否固定换算率、与主计量换算率
		BD_ConvertVO convertVO[] = null;
		if (pk_invbasdoc != null && pk_invbasdoc.toString().trim().length() > 0 && pk_assistmeas != null && pk_assistmeas.toString().trim().length() > 0) {
			convertVO = OrderHelper.findBd_Converts(new String[] {(String) pk_invbasdoc }, new String[] {(String) pk_assistmeas });
		}

		if (convertVO == null || convertVO.length == 0) {

			if (pk_assistmeas.equals(pk_measdoc) && pk_assistmeas != null) {
				convertVO = new BD_ConvertVO[1];
				convertVO[0] = new BD_ConvertVO();
				convertVO[0].setBfixedflag(new UFBoolean("Y"));
				convertVO[0].setNmainmeasrate(new UFDouble(1));
			} else {
				billcardPanel.setBodyValueAt("", rowindex, "measrate");
				return;
			}

		}
		//显示换算率

		//add by yye begin 当辅单位和主单位选择为同一计量单位时，换算率 = 1
		if (pk_assistmeas.equals(pk_measdoc) && pk_assistmeas != null) {
			convertVO = new BD_ConvertVO[1];
			convertVO[0] = new BD_ConvertVO();
			convertVO[0].setBfixedflag(new UFBoolean("Y"));
			convertVO[0].setNmainmeasrate(new UFDouble(1));
		}
		//add by yye end

		UFDouble measrate = convertVO[0].getNmainmeasrate();
		SCMEnv.out("---------------------------------------换算率：" + measrate);
		//billcardPanel.setBodyValueAt(measrate, rowindex, "measrate");
		UFBoolean fixedflag = convertVO[0].getBfixedflag();
		if (!key.equals("measrate")) billcardPanel.setBodyValueAt(measrate, rowindex, "measrate");		
		//固定换算率
		if (fixedflag == null || fixedflag.booleanValue()) {

			billcardPanel.setCellEditable(rowindex, "measrate", false);
			if (key.equals("nordernum")) {
				String formula = "nassistnum-> nordernum/measrate";
				billcardPanel.getBillModel().execFormula(rowindex, new String[] { formula });
			}
			if (key.equals("nassistnum")) {
				String formula = "nordernum->nassistnum*measrate";
				billcardPanel.getBillModel().execFormula(rowindex, new String[] { formula });
				billcardPanel.setCellEditable(rowindex, "nassistnum", true);
			}
			if (key.equals("cassistunitname")) {
				String formula = "nassistnum->nordernum/measrate";
				billcardPanel.getBillModel().execFormula(rowindex, new String[] { formula });
				billcardPanel.setCellEditable(rowindex, "nassistnum", true);				
			}
		}
		//非固定换算率
		else {
			billcardPanel.setCellEditable(rowindex, "measrate", true);
			if (key.equals("nordernum")) {
				String formula = null;
				if(oAssistnum != null) formula = "measrate->nordernum/nassistnum";
				else formula = "nassistnum->nordernum/measrate"; 
				billcardPanel.getBillModel().execFormula(rowindex, new String[] { formula });
				billcardPanel.setCellEditable(rowindex, "nordernum", true);
			}
			if (key.equals("nassistnum")) {
				String formula = null;
				if(oNum != null) formula = "measrate->nordernum/nassistnum";
				else formula = "nordernum->nassistnum*measrate";
				billcardPanel.getBillModel().execFormula(rowindex, new String[] { formula });
				billcardPanel.setCellEditable(rowindex, "nassistnum", true);
			}
			if (key.equals("cassistunitname") || key.equals("measrate")){
				String formula = null;
				if(oNum == null) formula = "nordernum->nassistnum*measrate";
				else formula = "nassistnum->nordernum/measrate";
				billcardPanel.getBillModel().execFormula(rowindex, new String[] { formula });
				billcardPanel.setCellEditable(rowindex, "nassistnum", true);				
			}
		}

	} catch (Exception e) {
		SCMEnv.out(e);
		return;

	}

}
/**
 * 编辑数量、辅计量单位、辅计量数量，若辅计量单位对某存货为固定换算率，
 * 则数量改变维护辅计量数量，辅计量数量改变维护数量；
 * 否则改变辅数量或换算率，主数量随之变化。改变主数量，显示的换算率随之变化
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editAssistUnitForMultiSelected(BillCardPanel billcardPanel, int iBeginRow, int iEndRow, String key) {
	try {
		for (int i = iBeginRow; i <= iEndRow; i++) {
			String pk_invbasdoc = (String)billcardPanel.getBodyValueAt(i, "cbaseid");
			String pk_measdoc = (String)billcardPanel.getBodyValueAt(i, "cassistunit");

			//是否辅计量管理
			if ( !InvTool.isAssUnitManaged( pk_invbasdoc ) ) {
				billcardPanel.setBodyValueAt("", i, "cassistunitname");
				billcardPanel.setBodyValueAt("", i, "measrate");
				billcardPanel.setBodyValueAt("", i, "nassistnum");
				billcardPanel.setCellEditable(i, "measrate", false);
				billcardPanel.setCellEditable(i, "nassistnum", false);
				billcardPanel.setCellEditable(i, "cassistunitname", false);
				billcardPanel.setCellEditable(i, "nordernum", true);
				continue;
			}
			
			billcardPanel.setCellEditable(i, "measrate", true);
			billcardPanel.setCellEditable(i, "nassistnum", true);
			billcardPanel.setCellEditable(i, "cassistunitname", true);

			if ( i == iBeginRow ) {
				// 当前公司
				String pk_corp = (String)billcardPanel.getHeadItem("pk_corp").getValueObject();
				// 当前存货管理档案ID
				String cmangid = (String)billcardPanel.getBodyValueAt(i, "cmangid"); 
				// 辅计量参照
				UIRefPane measRef = (UIRefPane) billcardPanel.getBodyItem("cassistunitname").getComponent();
				
				new InvMeasRate().filterMeas(pk_corp, cmangid, measRef);
			}

			if (pk_invbasdoc == null || pk_measdoc == null) {
				billcardPanel.setBodyValueAt("", i, "nassistnum");
				continue;
			}
			
			//显示换算率
			UFDouble measrate = InvTool.getInvConvRateValue(pk_invbasdoc, pk_measdoc);
			billcardPanel.setBodyValueAt(measrate, i, "measrate");
			
			//固定换算率
			if ( InvTool.isFixedConvertRate(pk_invbasdoc, pk_measdoc) ) {
				billcardPanel.setCellEditable(i, "measrate", false);
			}
			//非固定换算率
			else {
				billcardPanel.setCellEditable(i, "measrate", true);
			}
		}
	} catch (Exception e) {
		SCMEnv.out(e);
		return;

	}
}

/**
 * 编辑币种
 * 功能描述:
 * 输入参数:
 * 返回值:
 * 异常处理:
 * @param currencytypeid java.lang.String
 */
public static void editCurrency(String currencytypeid,nc.bs.bd.b21.CurrencyRateUtil m_CurrArith) {}
/**
 * 用于委外加工订单
 * 编辑供应商，带出默认的部门、业务员、发运方式、收付款协议、开户银行
 *
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editCust(BillCardPanel billcardPanel, String key) {
	if (!key.equals("cvendormangid"))
		return;
	
	billcardPanel.getHeadItem("cgiveinvoicevendor").setValue("");//发票方
	billcardPanel.getHeadItem("caccountbankid").setValue("");
	//billcardPanel.getHeadItem("caccountbankid").setValue("");
	//取供应商基础ID
	billcardPanel.execHeadFormula("cvendorid->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,cvendormangid)");
	String cvendorid = billcardPanel.getHeadItem("cvendorid").getValue();
	if(cvendorid ==null||cvendorid.trim().equals(""))
		return;
	
    // 该供应商对应默认开户银行
    setDefaultBankAccountForAVendor(billcardPanel,cvendorid);
	//
	//部门
	String formula = "cdeptid->getColValue(bd_cumandoc,pk_respdept1,pk_cumandoc,cvendormangid)";
	if (billcardPanel.getHeadItem("cdeptid").getValue() == null || billcardPanel.getHeadItem("cdeptid").getValue().toString().trim().equals(""))
		billcardPanel.execHeadFormula(formula);
	//业务员
	formula = "cemployeeid->getColValue(bd_cumandoc,pk_resppsn1,pk_cumandoc,cvendormangid)";
	if (billcardPanel.getHeadItem("cemployeeid").getValue() == null || billcardPanel.getHeadItem("cemployeeid").getValue().toString().trim().equals(""))
		billcardPanel.execHeadFormula(formula);
	//发运方式
	formula = "ctransmodeid->getColValue(bd_cumandoc,pk_sendtype,pk_cumandoc,cvendormangid)";
	if (billcardPanel.getHeadItem("ctransmodeid").getValue() == null || billcardPanel.getHeadItem("ctransmodeid").getValue().toString().trim().equals(""))
		billcardPanel.execHeadFormula(formula);
	//收付款协议
	formula = "ctermprotocolid->getColValue(bd_cumandoc,pk_payterm,pk_cumandoc,cvendormangid)";
	if (billcardPanel.getHeadItem("ctermprotocolid").getValue() == null || billcardPanel.getHeadItem("ctermprotocolid").getValue().toString().trim().equals(""))
		billcardPanel.execHeadFormula(formula);
	//发票方
	formula = "cgiveinvoicevendor->getColValue(bd_cumandoc,pk_cusmandoc2,pk_cumandoc,cvendormangid)";
	if (billcardPanel.getHeadItem("cgiveinvoicevendor").getValue() == null || billcardPanel.getHeadItem("cgiveinvoicevendor").getValue().toString().trim().equals(""))
		billcardPanel.execHeadFormula(formula);
	if (billcardPanel.getHeadItem("cgiveinvoicevendor").getValue() == null || billcardPanel.getHeadItem("cgiveinvoicevendor").getValue().toString().trim().equals("")) {
		billcardPanel.getHeadItem("cgiveinvoicevendor").setValue(billcardPanel.getHeadItem("cvendormangid").getValue());
	}
	//
	billcardPanel.updateUI();

}
/**
 * 用于委外加工订单、调整单
 * 修改业务员，则部门为该业务员所在的部门
 * 修改部门 ，则业务员为该部门中的业务员
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editDeptAndEmployee(BillCardPanel billcardPanel, String key) {
	if (key.equals("cemployeeid")) {
		//取表头部门
		/*Object cdeptid = billcardPanel.getHeadItem("cdeptid").getValue();
		if (cdeptid == null || cdeptid.toString().trim().equals("")) {
	        UIRefPane ref = (UIRefPane) billcardPanel.getHeadItem("cemployeeid").getComponent();
	        ref.setWhereString(null);

		}*/

		Object cemployeeid = billcardPanel.getHeadItem("cemployeeid").getValue(); //表头业务员

		if (cemployeeid == null || cemployeeid.toString().trim().equals(""))
			return;

		//根据人员取部门
		String formula = "cdeptid->getColValue(bd_psndoc,pk_deptdoc,pk_psndoc,cemployeeid)";
		billcardPanel.execHeadFormula(formula).toString();


	}
	if (key.equals("cdeptid")) {

//		billcardPanel.getHeadItem("cemployeeid").setValue("");
//		Object cdeptid = billcardPanel.getHeadItem("cdeptid").getValue(); //表头部门
//
//		UIRefPane ref = (UIRefPane) billcardPanel.getHeadItem("cemployeeid").getComponent();
//
//		nc.ui.pub.ClientEnvironment env = nc.ui.pub.ClientEnvironment.getInstance();
//		String pk_corp = env.getCorporation().getPk_corp();
//
//		if (cdeptid == null || cdeptid.toString().trim().equals("")) {
//			ref.setWhereString(" where bd_psndoc.pk_corp='"+pk_corp+"'");
//			return;
//		}
//
//		ref.setWhereString(" where bd_psndoc.pk_corp='"+pk_corp+"'"+" and bd_psndoc.pk_deptdoc='" + cdeptid + "' ");

	}
}

/**
 *
 *
 *
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editFreeItem(BillCardPanel billcardPanel, int rowindex, String key, String strInvBaseid, String strInvMangid, String strInvcode, String strInvname, String strInvspec, String strInvtype) {

	try {
		Object pk_invbasedoc = billcardPanel.getBodyValueAt(rowindex, strInvBaseid);
		Object pk_invmangdoc = billcardPanel.getBodyValueAt(rowindex, strInvMangid);
		Object cinventorycode = billcardPanel.getBodyValueAt(rowindex, strInvcode);
		Object cinventoryname = billcardPanel.getBodyValueAt(rowindex, strInvname);
		Object invspec = billcardPanel.getBodyValueAt(rowindex, strInvspec);
		Object invtype = billcardPanel.getBodyValueAt(rowindex, strInvtype);
		if (pk_invmangdoc == null)
			return;
		nc.vo.scm.ic.bill.FreeVO freeVO = null;
		nc.vo.scm.ic.bill.InvVO invVO = null;

		if (key.equals(strInvcode)) {
			freeVO = nc.ui.sc.adjust.AdjustbillHelper.queryFreeVOByInvID(pk_invmangdoc.toString());
			if (!definedFreeItem(freeVO)) {
				billcardPanel.setCellEditable(rowindex, "vfree0", false);
			}
			else {
				billcardPanel.setCellEditable(rowindex, "vfree0", true);
				invVO = new nc.vo.scm.ic.bill.InvVO();
				invVO.setFreeItemVO(freeVO);
				//invVO.setCinvmanid(pk_invmangdoc.toString());
				//invVO.setCinventoryid(pk_invbasedoc.toString());
				invVO.setCinvmanid(pk_invbasedoc.toString());
				invVO.setCinventoryid(pk_invmangdoc.toString());

				invVO.setIsFreeItemMgt(new Integer(1));
				invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
				invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
				invVO.setInvspec(invspec == null ? "" : invspec.toString());
				invVO.setInvtype(invtype == null ? "" : invtype.toString());

				//
				FreeItemRefPane freeRef = (FreeItemRefPane) billcardPanel.getBodyItem("vfree0").getComponent();
				freeRef.setFreeItemParam(invVO);

				billcardPanel.setBodyValueAt(invVO, rowindex, "invvo");
				billcardPanel.setBodyValueAt("", rowindex, "vfree0");
			}
		}
		if (key.equals("vfree0")) {
			FreeItemRefPane freeRef = (FreeItemRefPane) billcardPanel.getBodyItem("vfree0").getComponent();
			freeVO = freeRef.getFreeVO();
			billcardPanel.setBodyValueAt(freeVO.getVfree1(), rowindex, "vfree1");
			billcardPanel.setBodyValueAt(freeVO.getVfree2(), rowindex, "vfree2");
			billcardPanel.setBodyValueAt(freeVO.getVfree3(), rowindex, "vfree3");
			billcardPanel.setBodyValueAt(freeVO.getVfree4(), rowindex, "vfree4");
			billcardPanel.setBodyValueAt(freeVO.getVfree5(), rowindex, "vfree5");
			//
			invVO = new nc.vo.scm.ic.bill.InvVO();
			invVO.setFreeItemVO(freeVO);
			//invVO.setCinvmanid(pk_invmangdoc.toString());
			//invVO.setCinventoryid(pk_invbasedoc.toString());
			invVO.setCinvmanid(pk_invbasedoc.toString());
			invVO.setCinventoryid(pk_invmangdoc.toString());

			invVO.setIsFreeItemMgt(new Integer(1));
			invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
			invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
			invVO.setInvspec(invspec == null ? "" : invspec.toString());
			invVO.setInvtype(invtype == null ? "" : invtype.toString());

			billcardPanel.setBodyValueAt(invVO, rowindex, "invvo");
		}

	}
	catch (Exception e) {
		SCMEnv.out(e);
		return;

	}

}
/**
 *
 *
 *
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editFreeItem(BillCardPanel billcardPanel, int rowindex, String key, String strInvBaseid, String strInvMangid) {

	try {
		Object pk_invbasedoc = billcardPanel.getBodyValueAt(rowindex, strInvBaseid);
		Object pk_invmangdoc = billcardPanel.getBodyValueAt(rowindex, strInvMangid);
		Object cinventorycode = billcardPanel.getBodyValueAt(rowindex, "cinventorycode");
		Object cinventoryname = billcardPanel.getBodyValueAt(rowindex, "cinventoryname");
		Object invspec = billcardPanel.getBodyValueAt(rowindex, "invspec");
		Object invtype = billcardPanel.getBodyValueAt(rowindex, "invtype");
		if (pk_invmangdoc == null)
			return;
		
		FreeVO freeVO = InvTool.getInvFreeVO( (String)pk_invmangdoc );
		nc.vo.scm.ic.bill.InvVO invVO = null;
		if (key.equals("cinventorycode")) {
			if (!definedFreeItem(freeVO)) {
				billcardPanel.setCellEditable(rowindex, "vfree0", false);
			}
			else {
				billcardPanel.setCellEditable(rowindex, "vfree0", true);
				invVO = new nc.vo.scm.ic.bill.InvVO();
				invVO.setFreeItemVO(freeVO);
				invVO.setCinvmanid(pk_invbasedoc.toString());
				invVO.setCinventoryid(pk_invmangdoc.toString());
				invVO.setIsFreeItemMgt(new Integer(1));
				invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
				invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
				invVO.setInvspec(invspec == null ? "" : invspec.toString());
				invVO.setInvtype(invtype == null ? "" : invtype.toString());

				FreeItemRefPane freeRef = (FreeItemRefPane) billcardPanel.getBodyItem("vfree0").getComponent();
				freeRef.setFreeItemParam(invVO);

				billcardPanel.setBodyValueAt(invVO, rowindex, "invvo");
				billcardPanel.setBodyValueAt(freeVO.getVfree0(),rowindex,"vfree0");
			}
		}
		else if (key.equals("vfree0")) {
			FreeItemRefPane freeRef = (FreeItemRefPane) billcardPanel.getBodyItem("vfree0").getComponent();
			freeVO = freeRef.getFreeVO();
			billcardPanel.setBodyValueAt(freeVO.getVfree1(), rowindex, "vfree1");
			billcardPanel.setBodyValueAt(freeVO.getVfree2(), rowindex, "vfree2");
			billcardPanel.setBodyValueAt(freeVO.getVfree3(), rowindex, "vfree3");
			billcardPanel.setBodyValueAt(freeVO.getVfree4(), rowindex, "vfree4");
			billcardPanel.setBodyValueAt(freeVO.getVfree5(), rowindex, "vfree5");
			//
			invVO = new nc.vo.scm.ic.bill.InvVO();
			invVO.setFreeItemVO(freeVO);
			invVO.setCinvmanid(pk_invbasedoc.toString());
			invVO.setCinventoryid(pk_invmangdoc.toString());

			invVO.setIsFreeItemMgt(new Integer(1));
			invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
			invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
			invVO.setInvspec(invspec == null ? "" : invspec.toString());
			invVO.setInvtype(invtype == null ? "" : invtype.toString());

			billcardPanel.setBodyValueAt(invVO, rowindex, "invvo");
		}

	}
	catch (Exception e) {
		SCMEnv.out(e);
		return;

	}

}
/**
*
*gc 2013
*
* 创建日期：(2001-8-31 8:47:59)
* @param billcardPanel nc.ui.pub.bill.BillCardPanel
* @param key java.lang.String
*/
static public void editFreeItem(BillCardPanel billcardPanel,String tab ,int rowindex, String key, String strInvBaseid, String strInvMangid) {

	try {
		Object pk_invbasedoc = billcardPanel.getBillModel(tab).getValueAt(rowindex, strInvBaseid);
		Object pk_invmangdoc = billcardPanel.getBillModel(tab).getValueAt(rowindex, strInvMangid);
		Object cinventorycode = billcardPanel.getBillModel(tab).getValueAt(rowindex, "cinventorycode");
		Object cinventoryname = billcardPanel.getBillModel(tab).getValueAt(rowindex, "cinventoryname");
		Object invspec = billcardPanel.getBillModel(tab).getValueAt(rowindex, "invspec");
		Object invtype = billcardPanel.getBillModel(tab).getValueAt(rowindex, "invtype");
		if (pk_invmangdoc == null)
			return;
		
		FreeVO freeVO = InvTool.getInvFreeVO( (String)pk_invmangdoc );
		nc.vo.scm.ic.bill.InvVO invVO = null;
		if (key.equals("cinventorycode")) {
			if (!definedFreeItem(freeVO)) {
				billcardPanel.setCellEditable(rowindex, "vfree0", false);
			}
			else {
				billcardPanel.setCellEditable(rowindex, "vfree0", true);
				invVO = new nc.vo.scm.ic.bill.InvVO();
				invVO.setFreeItemVO(freeVO);
				invVO.setCinvmanid(pk_invbasedoc.toString());
				invVO.setCinventoryid(pk_invmangdoc.toString());
				invVO.setIsFreeItemMgt(new Integer(1));
				invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
				invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
				invVO.setInvspec(invspec == null ? "" : invspec.toString());
				invVO.setInvtype(invtype == null ? "" : invtype.toString());

				FreeItemRefPane freeRef = (FreeItemRefPane) billcardPanel.getBodyItem("vfree0").getComponent();
				freeRef.setFreeItemParam(invVO);

				billcardPanel.getBillModel(tab).setValueAt(invVO, rowindex, "invvo");
				billcardPanel.getBillModel(tab).setValueAt(freeVO.getVfree0(),rowindex,"vfree0");
			}
		}
		else if (key.equals("vfree0")) {
			FreeItemRefPane freeRef = (FreeItemRefPane) billcardPanel.getBodyItem("vfree0").getComponent();
			freeVO = freeRef.getFreeVO();
			billcardPanel.getBillModel(tab).setValueAt(freeVO.getVfree1(), rowindex, "vfree1");
			billcardPanel.getBillModel(tab).setValueAt(freeVO.getVfree2(), rowindex, "vfree2");
			billcardPanel.getBillModel(tab).setValueAt(freeVO.getVfree3(), rowindex, "vfree3");
			billcardPanel.getBillModel(tab).setValueAt(freeVO.getVfree4(), rowindex, "vfree4");
			billcardPanel.getBillModel(tab).setValueAt(freeVO.getVfree5(), rowindex, "vfree5");
			//
			invVO = new nc.vo.scm.ic.bill.InvVO();
			invVO.setFreeItemVO(freeVO);
			invVO.setCinvmanid(pk_invbasedoc.toString());
			invVO.setCinventoryid(pk_invmangdoc.toString());

			invVO.setIsFreeItemMgt(new Integer(1));
			invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
			invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
			invVO.setInvspec(invspec == null ? "" : invspec.toString());
			invVO.setInvtype(invtype == null ? "" : invtype.toString());

			billcardPanel.getBillModel(tab).setValueAt(invVO, rowindex, "invvo");
		}

	}
	catch (Exception e) {
		SCMEnv.out(e);
		return;

	}

}
	
/**
 * 存货带出默认税率
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 */
static public void editTaxRate(BillCardPanel billcardPanel, int rowindex, String key) {

	try {

		Object pk_invbasdoc = billcardPanel.getBodyValueAt(rowindex, "cbaseid");
		Object taxRate = billcardPanel.getBodyValueAt(rowindex, "ntaxrate");
		if (taxRate != null && !taxRate.toString().trim().equals(""))
			return;
		if (pk_invbasdoc == null)
			return;
		String formula1 = "pk_taxitems->getColValue(bd_invbasdoc,pk_taxitems,pk_invbasdoc,cbaseid)";
		String formula2 = "taxratio->getColValue(bd_taxitems,taxratio,pk_taxitems,pk_taxitems)";
		//
		FormulaParse f = new FormulaParse();
		f.setExpress(formula1);
    f.addVariable("cbaseid", pk_invbasdoc);
		//
		String pk_taxitems = f.getValue();
		f.setExpress(formula2);
    f.addVariable("pk_taxitems", pk_taxitems);
		billcardPanel.setBodyValueAt(f.getValue(), rowindex, "ntaxrate");

	}
	catch (Exception e) {
		SCMEnv.out(e);
		return;

	}

}
/**
 *
 * 批次管理
 *
 * 创建日期：(2001-8-31 8:47:59)
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param key java.lang.String
 * 2003-04-03 ljq 换行不清空批次
 */
static public boolean editVbatch(BillCardPanel billcardPanel, int rowindex, String strInvMangid) {

	try {

		Object pk_invmangdoc = billcardPanel.getBodyValueAt(rowindex, strInvMangid);
		if (pk_invmangdoc == null)
			return false;
		String formula = "getColValue(bd_invmandoc,wholemanaflag,pk_invmandoc,cmangid)";
		FormulaParse f = new FormulaParse();
		f.setExpress(formula);
		f.addVariable("cmangid", pk_invmangdoc);
		String wholemanaflag = f.getValue();
		if (wholemanaflag.equalsIgnoreCase("N")) {
			billcardPanel.setBodyValueAt(null,rowindex,"vbatch");
			billcardPanel.setCellEditable(rowindex, "vbatch", false);
			return false;
		}
		else {
			billcardPanel.setCellEditable(rowindex, "vbatch", true);
			return true;
		}
	}

	catch (Exception e) {
		SCMEnv.out(e);
		return false;

	}

}
/**
 * 加工品批次管理
 * <p>
 * <b>examples:</b>
 * <p>
 * 使用示例
 * <p>
 * <b>参数说明</b>
 * @param billcardPanel nc.ui.pub.bill.BillCardPanel
 * @param rowindex 当前行
 * @param strInvMangid 加工品基本档案ID
 * @return UFDouble
 * @throws 
 * <p>
 * @author lixiaodong
 * @time 2008-01-02 上午10:05:11
 */
static public boolean editVprocessbatch(BillCardPanel billcardPanel, int rowindex, String strInvMangid) {

	try {

		Object pk_invmangdoc = billcardPanel.getBodyValueAt(rowindex, strInvMangid);
		if (pk_invmangdoc == null)
			return false;
		String formula = "getColValue(bd_invmandoc,wholemanaflag,pk_invmandoc,cmangid)";
		FormulaParse f = new FormulaParse();
		f.setExpress(formula);
    f.addVariable("cmangid", pk_invmangdoc);
		String wholemanaflag = f.getValue();
		if (wholemanaflag.equalsIgnoreCase("N")) {
			billcardPanel.setBodyValueAt(null,rowindex,"vprocessbatch");
			billcardPanel.setCellEditable(rowindex, "vprocessbatch", false);
			return false;
		}
		else {
			billcardPanel.setCellEditable(rowindex, "vprocessbatch", true);
			return true;
		}
	}

	catch (Exception e) {
		SCMEnv.out(e);
		return false;

	}

}
/**
 * 此处插入方法说明。
 * 功能描述:获得存货的采购提前期
 * 输入参数:
 * 返回值:
 * 异常处理:
 */
static public int getAdvanceDays(String pk_corp ,String cwareid,String cbaseid,UFDouble d) {


	Vector v = new Vector();

	try {
		if (cwareid != null && cwareid.length() > 0 && cbaseid != null && cbaseid.length() > 0)
			v = PublicHelper.queryAdvanceDays(pk_corp, cwareid, cbaseid);
		else
			return 0;
	}
	catch (java.sql.SQLException e) {
		MessageDialog.showErrorDlg(new java.awt.Container(), nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000004")/*@res "SQL语句错误"*/);
		SCMEnv.out(e);
		return -1;
	}
	catch (ArrayIndexOutOfBoundsException e) {
		MessageDialog.showErrorDlg(new java.awt.Container(), nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000005")/*@res "数组越界错误"*/);
		SCMEnv.out(e);
		return -1;
	}
	catch (NullPointerException e) {
		MessageDialog.showErrorDlg(new java.awt.Container(), nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000006")/*@res "空指针错误"*/);
		SCMEnv.out(e);
		return -1;
	}
	catch (Exception e) {
		MessageDialog.showErrorDlg(new java.awt.Container(), nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000007")/*@res "其它错误"*/);
		SCMEnv.out(e);
		return -1;
	}

	if (v.size() == 0)
		return 0;

	UFDouble dFixedahead = (UFDouble) v.elementAt(0);
	UFDouble dAheadcoff = (UFDouble) v.elementAt(1);
	UFDouble dAheadbatch = (UFDouble) v.elementAt(2);


	if (d != null && dFixedahead != null && dAheadcoff != null && dAheadbatch != null && dAheadbatch.doubleValue() != 0.0) {
		double d1 = d.doubleValue();
		double d2 = dFixedahead.doubleValue();
		double d3 = dAheadcoff.doubleValue();
		double d4 = dAheadbatch.doubleValue();
		if (d1 > d4) {
			double dd = d2 + (d1 - d4) * d3 / d4;
			int k = (int) dd;
			if (dd - k > 0)
				k++;
			return k;
		}
		else
			return (int) d2;
	}
	else
		return 0;
}
/**
 * 此处插入方法说明。
 * 功能描述:获得存货的采购提前期
 * 输入参数:
 * 返回值:
 * 异常处理:
 */
static public int getAdvanceDays(BillCardPanel billcardPanel, int rowIndex) {


	Vector v = new Vector();
	String pk_corp = billcardPanel.getHeadItem("pk_corp").getValue();
	try {
		String s1 = billcardPanel.getHeadItem("cwareid").getValue();

		String s2=null;
		if (billcardPanel.getBodyValueAt(rowIndex, "cbaseid") != null){
		    s2 = billcardPanel.getBodyValueAt(rowIndex, "cbaseid").toString();
		}
		//String s2 = "1001AA10000000000F5F";
		if (s1 != null && s1.length() > 0 && s2 != null && s2.length() > 0)
			v = PublicHelper.queryAdvanceDays(pk_corp, s1, s2);
		else
			return 0;
	}
	catch (java.sql.SQLException e) {
		MessageDialog.showErrorDlg(billcardPanel, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000004")/*@res "SQL语句错误"*/);
		SCMEnv.out(e);
		return -1;
	}
	catch (ArrayIndexOutOfBoundsException e) {
		MessageDialog.showErrorDlg(billcardPanel, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000005")/*@res "数组越界错误"*/);
		SCMEnv.out(e);
		return -1;
	}
	catch (NullPointerException e) {
		MessageDialog.showErrorDlg(billcardPanel, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000006")/*@res "空指针错误"*/);
		SCMEnv.out(e);
		return -1;
	}
	catch (Exception e) {
		MessageDialog.showErrorDlg(billcardPanel, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000003")/*@res "采购提前期"*/, nc.ui.ml.NCLangRes.getInstance().getStrByID("40120002","UPP40120002-000007")/*@res "其它错误"*/);
		SCMEnv.out(e);
		return -1;
	}

	if (v.size() == 0)
		return 0;

	UFDouble dFixedahead = (UFDouble) v.elementAt(0);
	UFDouble dAheadcoff = (UFDouble) v.elementAt(1);
	UFDouble dAheadbatch = (UFDouble) v.elementAt(2);
	UFDouble d = (UFDouble) billcardPanel.getBodyValueAt(rowIndex, "nordernum");

	if (d != null && dFixedahead != null && dAheadcoff != null && dAheadbatch != null && dAheadbatch.doubleValue() != 0.0) {
		double d1 = d.doubleValue();
		double d2 = dFixedahead.doubleValue();
		double d3 = dAheadcoff.doubleValue();
		double d4 = dAheadbatch.doubleValue();
		if (d1 > d4) {
			double dd = d2 + (d1 - d4) * d3 / d4;
			int k = (int) dd;
			if (dd - k > 0)
				k++;
			return k;
		}
		else
			return (int) d2;
	}
	else
		return 0;
}
/**
 * 处理BOM接口
 * 创建日期：(2001-9-18 13:41:44)
 * @return nc.vo.mm.pub.pub1020.DisassembleVO[]
 * @param condition nc.vo.mm.pub.pub1020.DisConditionVO
 */
public static DisassembleVO[] getBomVO(DisConditionVO condition) {

	Vector vec = new Vector();

	try {
		//DisassembleVO[] bomVO = DisassembleBO_Client.getChild(condition);
	    DisassembleVO[] bomVO = PublicHelper.getChild(condition);
		int num = 0;
		if(bomVO != null && bomVO.length > 0) num = bomVO.length;
		for (int i = 0; i < num; i++) {

			UFBoolean isSC = bomVO[i].getSfwwfl();
			if(!isSC.booleanValue())
				continue;

//			int zxlx = bomVO[i].getZxlx().intValue();
//			if (zxlx == 0)
//				vec.addElement(bomVO[i]);
//			if (zxlx == 1) {

      Object oTemp = CacheTool.getCellValue("bd_produce", "pk_produce", "virtualflag", bomVO[i].getPk_produce());
      boolean virtualflag = false;
      if (oTemp != null) {
        Object o[] = (Object[]) oTemp;
        if (o != null && o.length > 0 && o[0] != null && o[0].equals("Y"))
          virtualflag = true;
      }
      
      //int zxlx = bomVO[i].getZxlx().intValue();
      if (!virtualflag)
        vec.addElement(bomVO[i]);
      
      if (virtualflag) {
				String wlbm = bomVO[i].getWlbmid();
				String zjldw = bomVO[i].getJldwid();
				UFDouble sl = bomVO[i].getSl();
				UFDouble shxs = bomVO[i].getShxs(); // 损耗率
				condition.setWlbmid(wlbm);
				condition.setJldwid(zjldw);
				condition.setSl(sl);
				condition.setCm(new Integer(1));
				DisassembleVO[] zxbomVO = getBomVO(condition);
				UFDouble trueSL = sl.add(shxs.multiply(sl)); // 如果父项为虚项，则缓存父项所需要的子项数量=父项所需子项数量 * 损耗系数
				
				for (int j = 0; j < zxbomVO.length; j++){
					zxbomVO[j].setSl(zxbomVO[j].getSl().multiply(trueSL));
					vec.addElement(zxbomVO[j]);
				}
			}

		}
	}
	catch (Exception e) {
		SCMEnv.out(e);
		return null;
	}
	DisassembleVO[] returnBomVO = null;
	if (vec.size() > 0) {
		returnBomVO = new DisassembleVO[vec.size()];
		vec.copyInto(returnBomVO);
	}

	return returnBomVO;
}

  /**
   * 处理BOM接口，支持批量处理
   * 创建日期：hanbin (2009-11-17 13:41:44)
   */
  public static List<DisassembleVO[]> getBomVOBatch(DisConditionVO[] conditions)
      throws BusinessException {
    
    return PublicHelper.getChildBatch(conditions);
    
  }

/*
 *生产制造标准分解条件VO
 *
 *
 */
public  static DisConditionVO getDisConditionVO(String pk_corp,UFDate date ,String operator,String cwareid) {

	DisConditionVO conditionVO=new DisConditionVO();
	conditionVO.setLogdate(date);//登陆日期
	conditionVO.setYxrq(date);
	conditionVO.setOperid(operator);//操作员
	conditionVO.setPk_corp(pk_corp);//公司
	conditionVO.setSffp(new UFBoolean("N"));//是否考虑废品
	conditionVO.setSfplgz(new UFBoolean("N"));//是否考虑批量规则
	conditionVO.setSfsh(new UFBoolean("N"));//是否考虑损耗
	conditionVO.setGcbm(cwareid);//库存组织（工厂）
	conditionVO.setSl(new UFDouble("1"));
	conditionVO.setCm(new Integer(1));
	//存货ID、主计量单位

	return conditionVO;
}

static public void editFreeItem(RedunListPanel redunListPanel, int rowindex, String key, String strInvBaseid, String strInvMangid) {

  try {
    Object pk_invbasedoc = redunListPanel.getHeadBillModel().getValueAt(rowindex, strInvBaseid);
    Object pk_invmangdoc = redunListPanel.getHeadBillModel().getValueAt(rowindex, strInvMangid);
    Object cinventorycode = redunListPanel.getHeadBillModel().getValueAt(rowindex,RedunUtil.CHD + "cinventorycode");
    Object cinventoryname = redunListPanel.getHeadBillModel().getValueAt(rowindex,RedunUtil.CHD +  "cinventoryname");
    Object invspec = redunListPanel.getHeadBillModel().getValueAt(rowindex, RedunUtil.CHD + "invspec");
    Object invtype = redunListPanel.getHeadBillModel().getValueAt(rowindex, RedunUtil.CHD + "invtype");
    if (pk_invmangdoc == null)
      return;
    nc.vo.scm.ic.bill.FreeVO freeVO = null;
    nc.vo.scm.ic.bill.InvVO invVO = null;

    if (key.equals(RedunUtil.CHD + "cinventorycode")) {
      freeVO = nc.ui.sc.adjust.AdjustbillHelper.queryFreeVOByInvID(pk_invmangdoc.toString());
      if (!definedFreeItem(freeVO)) {
        redunListPanel.getSourceBodyItem(RedunUtil.CHD + "vfree0").setEdit(false);
      }
      else {
        redunListPanel.getSourceBodyItem(RedunUtil.CHD + "vfree0").setEdit(true);
        invVO = new nc.vo.scm.ic.bill.InvVO();
        invVO.setFreeItemVO(freeVO);
        //Modified by xhq 2003/05/10 begin
        //invVO.setCinvmanid(pk_invmangdoc.toString());
        //invVO.setCinventoryid(pk_invbasedoc.toString());
        invVO.setCinvmanid(pk_invbasedoc.toString());
        invVO.setCinventoryid(pk_invmangdoc.toString());
        invVO.setIsFreeItemMgt(new Integer(1));
        //Modified by xhq 2003/05/10 end
        invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
        invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
        invVO.setInvspec(invspec == null ? "" : invspec.toString());
        invVO.setInvtype(invtype == null ? "" : invtype.toString());

        //
        FreeItemRefPane freeRef = (FreeItemRefPane) redunListPanel.getHeadItem(RedunUtil.CHD + "vfree0").getComponent();
        freeRef.setFreeItemParam(invVO);

        redunListPanel.getHeadBillModel().setValueAt(invVO, rowindex, RedunUtil.CHD + "invvo");
        redunListPanel.getHeadBillModel().setValueAt("", rowindex, RedunUtil.CHD + "vfree0");
      }

    }
    if (key.equals(RedunUtil.CHD + "vfree0")) {
      FreeItemRefPane freeRef = (FreeItemRefPane) redunListPanel.getHeadItem(RedunUtil.CHD + "vfree0").getComponent();
      freeVO = freeRef.getFreeVO();
      redunListPanel.getHeadBillModel().setValueAt(freeVO.getVfree1(), rowindex, RedunUtil.CHD + "vfree1");
      redunListPanel.getHeadBillModel().setValueAt(freeVO.getVfree2(), rowindex, RedunUtil.CHD + "vfree2");
      redunListPanel.getHeadBillModel().setValueAt(freeVO.getVfree3(), rowindex, RedunUtil.CHD + "vfree3");
      redunListPanel.getHeadBillModel().setValueAt(freeVO.getVfree4(), rowindex, RedunUtil.CHD + "vfree4");
      redunListPanel.getHeadBillModel().setValueAt(freeVO.getVfree5(), rowindex, RedunUtil.CHD + "vfree5");
      //
      invVO = new nc.vo.scm.ic.bill.InvVO();
      invVO.setFreeItemVO(freeVO);
      //invVO.setCinvmanid(pk_invmangdoc.toString());
      //invVO.setCinventoryid(pk_invbasedoc.toString());
      invVO.setCinvmanid(pk_invbasedoc.toString());
      invVO.setCinventoryid(pk_invmangdoc.toString());

      invVO.setIsFreeItemMgt(new Integer(1));
      invVO.setCinventorycode(cinventorycode == null ? "" : cinventorycode.toString());
      invVO.setInvname(cinventoryname == null ? "" : cinventoryname.toString());
      invVO.setInvspec(invspec == null ? "" : invspec.toString());
      invVO.setInvtype(invtype == null ? "" : invtype.toString());

      redunListPanel.getHeadBillModel().setValueAt(invVO, rowindex, RedunUtil.CHD + "invvo");

    }

  }
  catch (Exception e) {
    SCMEnv.out(e);
    return;

  }

}


/**
 * 编辑供应商，设置供应商默认银行账户
 * @param billcardPanel
 * @param strVendorBase
 */
static void setDefaultBankAccountForAVendor(BillCardPanel billcardPanel,String strVendorBase) {

	if (strVendorBase == null || strVendorBase.trim().equals("")) {
		return;
	}


	Object[][] retObj = null;
	try {
		retObj = PubHelper.queryResultsFromTableByWhere("bd_bankaccbas", new String[] { "pk_bankaccbas" }, new String[] { "pk_bankaccbas in (select pk_accbank from bd_custbank where pk_cubasdoc = '"+strVendorBase+"' and defflag='Y')" });
	}
	catch (BusinessException be) {
	}

	String caccountbankid = "";
	if (retObj != null && retObj.length > 0 && retObj[0] != null) {
		caccountbankid = PuPubVO.getString_TrimZeroLenAsNull(retObj[0][0]);
	}

	if (caccountbankid != null) {
		// 设置参照主键:开户银行
		billcardPanel.setHeadItem("caccountbankid", caccountbankid);
	}
	else {
		// 设置参照主键:开户银行
		billcardPanel.setHeadItem("caccountbankid", null);
	}

}

}