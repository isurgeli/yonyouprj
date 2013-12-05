package nc.ui.ztwzj.sapitf.bill;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.Action;

import nc.bs.framework.common.NCLocator;
import nc.funcnode.ui.action.INCAction;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.ztwzj.sapitf.IBillService;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.cmp.bill.actions.BillUiUtil;
import nc.ui.cmp.bill.actions.PayTransTypeAction;
import nc.ui.lxt.pub.editor.NCUITool;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.uif2.AppEvent;
import nc.ui.uif2.model.AbstractAppModel;
import nc.ui.uif2.model.AppEventConst;
import nc.ui.uif2.model.BillManageModel;
import nc.vo.cmp.bill.BaseBillVO;
import nc.vo.cmp.bill.BillAggVO;
import nc.vo.cmp.bill.BillDetailVO;
import nc.vo.cmp.bill.BillUtils;
import nc.vo.cmp.bill.BillVO;
import nc.vo.cmp.bill.CmpBillCommand;
import nc.vo.cmp.bill.CommonContext;
import nc.vo.logging.Debug;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDouble;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse.OTab;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse.OTab.Item;

public class GenPayBillAction extends PayTransTypeAction {

	private static final long serialVersionUID = 1L;
	private static final String RMB="1002Z0100000000001K1";
	private static String PATBILLBT="0001A510000000000ASX";
	
	private AbstractAppModel model;
	
	public GenPayBillAction() {
		setBtnName("生成付款单");
		putValue(Action.SHORT_DESCRIPTION, getBtnName());
		putValue(INCAction.CODE, "生成付款单");
	}
	
	public String showTrantypeDlgEx() {
		UIRefPane uiRefPane = this.getTransTypeRef();
		uiRefPane.showModel();
		if (uiRefPane.getReturnButtonCode() == UIDialog.ID_CANCEL) {
			return null;
		}
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> cur = uiRefPane.getRefModel().getSelectedData();
		return cur.get(0).get(2).toString();
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		PATBILLBT = showTrantypeDlgEx();
		if (PATBILLBT == null)
			return;
		Integer[] rows = ((BillManageModel)getModel()).getSelectedOperaRows();
		ArrayList<String> sPayNos = new ArrayList<String>();
		for (int i=0;i<rows.length;i++) {
			int curRow = rows[i];
			OTab.Item curData = (OTab.Item)((BillManageModel)getModel()).getData().get(curRow);
			
			String[] payBillInfo = checkHaveAlreadyGenerated(curData);
			if (payBillInfo != null && payBillInfo.length == 2) {
				curData.setPk_paybill(payBillInfo[0]);
				curData.setVstatus(payBillInfo[1]);
				sPayNos.add(curData.getPrepay_req_num());
				continue;
			}
			
			AggregatedValueObject billBean;
			try {
				billBean = getPayBillVO(curData);
			} catch (BusinessException be) {
				curData.setVerrmsg(be.getMessage());
				curData.setVstatus("处理异常");
				continue;
			}
			
			BaseBillVO base = new BaseBillVO();

			base.setBeanList(new AggregatedValueObject[] { billBean });
			base.setCommand(CmpBillCommand.SAVE);
			
			// 执行流程平台接口
			CommonContext defaultContext = BillUtils.getDefaultContext(base);
			Object executePM = BillUiUtil.executeBatchPM(defaultContext, null);
			AggregatedValueObject[] aggs = (AggregatedValueObject[]) executePM;

			billBean = (AggregatedValueObject) aggs[0];

			Object ntberrmsg = billBean.getParentVO().getAttributeValue("ntberrmsg");
			if (null != ntberrmsg) {
				curData.setVerrmsg(ntberrmsg.toString());
				curData.setVstatus("处理异常");
			}else{
				curData.setPk_paybill(billBean.getParentVO().getPrimaryKey());
				curData.setVstatus(((BillVO)billBean.getParentVO()).getBill_no());
				sPayNos.add(curData.getPrepay_req_num());
			}
		}
		
		try {
			if (sPayNos.size() > 0) {
				IBillService bs = NCLocator.getInstance().lookup(IBillService.class);
				bs.setPayBillNCFlag("S", sPayNos);
			}
		}catch (BusinessException be){
			Debug.error(be.getMessage());
		}
		
		((BillManageModel)getModel()).fireEvent(new AppEvent(AppEventConst.SELECTED_DATE_CHANGED));
	}

	private String[] checkHaveAlreadyGenerated(Item curData) throws BusinessException {
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		StringBuffer sql = new StringBuffer();
		sql.append("select cmp_paybill.pk_paybill, cmp_paybill.bill_no from cmp_paybill where nvl(dr,0)=0 and cmp_paybill.def20 = '");
		sql.append(curData.getPrepay_req_num());
		sql.append("'");
		
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> info = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
		if (info.size() == 0)
			return null;
		else
			return new String[]{info.get(0).get(0).toString(), info.get(0).get(1).toString()};
	}

	private AggregatedValueObject getPayBillVO(Item curData) throws BusinessException {
		BillAggVO ret = new BillAggVO();
		BillVO parent = getPayBillHeadVO(curData);
		ret.setParentVO(parent);
		ret.setChildrenVO(getPayBillChildVO(parent, curData));
		
		return ret;
	}

	private BillDetailVO[] getPayBillChildVO(BillVO parent,	Item curData) {
		BillDetailVO detail = new BillDetailVO();
		detail.setAttributeValue("accountname", parent.getDef1());
		detail.setAttributeValue("accountopenbank", parent.getDef2());
		parent.setDef1(null);
		parent.setDef2(null);
		
		detail.setAttributeValue("accounttype", "0");
		detail.setAttributeValue("bill_date", parent.getBill_date());
		detail.setAttributeValue("bill_type", "F5");
		detail.setAttributeValue("billclass", "fj");
		detail.setAttributeValue("billdetail_no", "0");
		detail.setAttributeValue("creationtime", parent.getCreationtime());
		detail.setAttributeValue("creator", parent.getCreator());
		detail.setAttributeValue("direction", "-1");
		detail.setAttributeValue("djxtflag", "N");
		detail.setAttributeValue("is_refuse", "N");
		detail.setAttributeValue("isauthpass", "N");
		detail.setAttributeValue("local_rate", "1.00");
		detail.setAttributeValue("objecttype", "1");
		detail.setAttributeValue("pay_local", new UFDouble(curData.getAmt_prepay_p()));
		detail.setAttributeValue("pay_primal", new UFDouble(curData.getAmt_prepay_p()));
		//detail.setAttributeValue("pk_account", parent.getPk_account());
		detail.setAttributeValue("pk_balatype", parent.getPk_balatype());
		detail.setAttributeValue("pk_currtype", parent.getPk_currtype());
		detail.setAttributeValue("pk_group", parent.getPk_group());
		detail.setAttributeValue("pk_oppaccount", parent.getPk_oppaccount());
		detail.setAttributeValue("pk_org", parent.getPk_org());
		detail.setAttributeValue("pk_recproject", parent.getPk_recproject());
		detail.setAttributeValue("pk_supplier", parent.getPk_supplier());
		detail.setAttributeValue("pk_tradetypeid", parent.getPk_tradetypeid());
		detail.setAttributeValue("rec_flag", "N");
		detail.setAttributeValue("trade_type", "D5");
		return new BillDetailVO[]{detail};
	}

	private BillVO getPayBillHeadVO(Item curData) throws BusinessException {
		BillVO parent = new BillVO();
		parent.setAttributeValue("bill_date", new UFDate().toString());
		parent.setAttributeValue("bill_status", "-1");
		parent.setAttributeValue("bill_type", "F5");
		parent.setAttributeValue("billclass", "fj");
		parent.setAttributeValue("billmaker", getModel().getContext().getPk_loginUser());
		parent.setAttributeValue("billmaker_date", new UFDate().toString());
		parent.setAttributeValue("creationtime", new UFDate().toString());
		parent.setAttributeValue("creator", getModel().getContext().getPk_loginUser());
		parent.setAttributeValue("is_cf", "N");
		parent.setAttributeValue("isnetready", "N");
		parent.setAttributeValue("isreded", "N");
		parent.setAttributeValue("isrefused", "N");
		parent.setAttributeValue("local_rate", "1.00");
		parent.setAttributeValue("memo", curData.getMemo());
		parent.setAttributeValue("objecttype", "1");
		String[] accountInfo = getAccountPk(curData.getZbankn()); 
		//parent.setAttributeValue("pk_account", accountInfo[0]);
		parent.setAttributeValue("def1", accountInfo[1]);
		parent.setAttributeValue("def2", accountInfo[2]);
		parent.setAttributeValue("pk_balatype", getBalaTypePk(curData.getZlsch()));
		parent.setAttributeValue("pk_currtype", RMB);
		parent.setAttributeValue("pk_group", getModel().getContext().getPk_group());
		parent.setAttributeValue("pk_oppaccount", accountInfo[0]);
		parent.setAttributeValue("pk_org", getModel().getContext().getPk_org());
		parent.setAttributeValue("pk_recproject", getRecPrjPk(curData.getCat_qian()));
		parent.setAttributeValue("pk_supplier", getSupplierPk(curData.getLifnr()));
		parent.setAttributeValue("pk_tradetypeid", PATBILLBT);
		parent.setAttributeValue("sddreversalflag", "N");
		parent.setAttributeValue("source_flag", "2");
		parent.setAttributeValue("trade_type", "D5");
		
		parent.setDef20(curData.getPrepay_req_num());
		
		return parent;
	}

	private String getSupplierPk(String code) throws BusinessException {
		code = code.substring(2);
		String[] formulas = new String[] {"pk_supplier->getColValue(bd_supplier,pk_supplier,code,code)"};
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("code", code);
		Object[][] values = NCUITool.getValueByFormula(formulas, vars);
		
		if (values[0][0] == null || values[0][0].toString().length() == 0)
			throw new BusinessException("供应商编码["+code+"]无法解析。");
		
		return values[0][0].toString();
	}

	private String getRecPrjPk(String cat_qian) throws BusinessException {
		String[] formulas = new String[] {"pk_inoutbusiclass->getColValue(bd_inoutbusiclass,pk_inoutbusiclass,code,code)"};
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("code", cat_qian);
		Object[][] values = NCUITool.getValueByFormula(formulas, vars);
		
		if (values[0][0] == null || values[0][0].toString().length() == 0)
			throw new BusinessException("收支项目编码["+cat_qian+"]无法解析。");
		
		return values[0][0].toString();
	}

	private String[] getAccountPk(String zbankn) throws BusinessException {
		String[] formulas = new String[] {"pk_bankaccsub->getColValue(bd_bankaccsub,pk_bankaccsub,accnum,code)",
				"accname->getColValue(bd_bankaccsub,accname,accnum,code)",
				"pk_bankaccbas->getColValue(bd_bankaccsub,pk_bankaccbas,accnum,code)",
				"pk_bankdoc->getColValue(bd_bankaccbas,pk_bankdoc,pk_bankaccbas,pk_bankaccbas)",
				"bankname->getColValue(bd_bankdoc,name,pk_bankdoc,pk_bankdoc)"};
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("code", zbankn);
		Object[][] values = NCUITool.getValueByFormula(formulas, vars);
		
		if (values[0][0] == null || values[0][0].toString().length() == 0)
			throw new BusinessException("银行账号["+zbankn+"]无法解析。");
		
		return new String[]{values[0][0].toString(), values[1][0].toString(), values[4][0].toString()};
	}

	private String getBalaTypePk(String zlsch) {
		final String BANKHP = "0001Z0100000000000Y5";
		final String COMMHP = "0001Z0100000000000Y6";
		final String NETBANK = "0001Z0100000000000Y2";
		
		if (zlsch.equals("A") || zlsch.equals("Q"))
			return COMMHP;
		else if (zlsch.equals("B") || zlsch.equals("P"))
			return BANKHP;
		else
			return NETBANK;
	}

	public AbstractAppModel getModel() {
		return model;
	}

	public void setModel(AbstractAppModel model) {
		this.model = model;
	}

}
