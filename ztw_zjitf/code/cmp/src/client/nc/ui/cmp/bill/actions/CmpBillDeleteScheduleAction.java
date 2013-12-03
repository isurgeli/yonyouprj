package nc.ui.cmp.bill.actions;

import java.awt.event.ActionEvent;
import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.itf.ztwzj.sapitf.IBillService;
import nc.ui.cmp.bill.service.DeleteBillService;
import nc.ui.cmp.view.MutilTransBillListView;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pubapp.pub.task.ISingleBillService;
import nc.ui.pubapp.uif2app.actions.DeleteAction;
import nc.ui.pubapp.uif2app.model.BillManageModel;
import nc.ui.pubapp.uif2app.view.MutilTransBillForm;
import nc.ui.uif2.components.ITabbedPaneAwareComponent;
import nc.ui.uif2.editor.BillForm;
import nc.vo.cmp.BusiStatus;
import nc.vo.cmp.bill.BaseBillVO;
import nc.vo.cmp.bill.BillUtils;
import nc.vo.cmp.bill.CmpBillCommand;
import nc.vo.cmp.bill.CmpBillFieldGet;
import nc.vo.cmp.bill.CommonContext;
import nc.vo.cmp.exception.ExceptionHandler;
import nc.vo.cmp.settlement.CheckException;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFBoolean;

@SuppressWarnings("restriction")
public class CmpBillDeleteScheduleAction extends DeleteAction {

	private MutilTransBillForm editor;

	private MutilTransBillListView listView;

	private DeleteBillService singleBillService;

	public void setSingleBillService(DeleteBillService singleBillService) {
		this.singleBillService = singleBillService;
	}

	public void setSingleBillView(ITabbedPaneAwareComponent singleBillView) {
//		this.singleBillView = singleBillView;
	}
	
	/**
	 *
	 */
	private static final long serialVersionUID = 6853742710821393924L;
	@Override
	public boolean beforeStartDoAction(ActionEvent actionEvent) throws Exception {
		return true;
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		beforeAction();

		AggregatedValueObject[] selectedOperaData = getSelectedAggVOs();

		CheckException.checkArgument(selectedOperaData == null,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0", "03607mng-0034")/*
																									 * @
																									 * res
																									 * "请选择需要删除的单据"
																									 */);
		if (UIDialog.ID_OK == MessageDialog.showOkCancelDlg(getModel().getContext().getEntranceUI(), null,
				nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0", "03607mng-0035")/*
																									 * @
																									 * res
																									 * "您确定要删除所选数据吗？"
																									 */)) {
			setSAPPabyBillFlag(selectedOperaData);
			BaseBillVO basebill = new BaseBillVO();
			basebill.setBeanList(selectedOperaData);
			basebill.setCommand(CmpBillCommand.DELETE);
			CommonContext defaultContext = BillUtils.getDefaultContext(basebill);
			BillUiUtil.executeBatchPM(defaultContext, getModel().getContext().getEntranceUI());

			Integer[] rows = ((BillManageModel) getModel()).getSelectedOperaRows();
			if (rows.length > 0) {
				((BillManageModel) getModel()).setSelectedRowWithoutEvent(rows[0]);
			}

			((BillManageModel) getModel()).directlyDelete(selectedOperaData);
			this.showSuccessInfo();

		}

	}

	private void setSAPPabyBillFlag(AggregatedValueObject[] selectedOperaData) throws BusinessException {
		ArrayList<String> sapPayNos = new ArrayList<String>();
		for (int i=0;i<selectedOperaData.length;i++){
			Object def20 = selectedOperaData[i].getParentVO().getAttributeValue("def20");
			if (def20 != null && def20.toString().length() > 1)
				sapPayNos.add(def20.toString());
		}
		
		IBillService bs = NCLocator.getInstance().lookup(IBillService.class);
		bs.setPayBillNCFlag("D", sapPayNos);
	}

	@Override
	protected boolean isActionEnable() {
		if (listView != null && listView.isShowing()) {
			return true;
		}

		if (editor.isShowing() && ((BillForm) editor).getModel().getSelectedData() != null) {
			AggregatedValueObject agg = (AggregatedValueObject) ((BillForm) editor).getModel().getSelectedData();
			SuperVO parentVO = (SuperVO) agg.getParentVO();
			String src_syscode = (String) parentVO.getAttributeValue(CmpBillFieldGet.getInstance().getFieldName(
					CmpBillFieldGet.H_SRC_SYSCODE));
			if ("2".equals(src_syscode) || "8".equals(src_syscode) || "9".equals(src_syscode)) {
				Integer billstatus = (Integer) parentVO.getAttributeValue(CmpBillFieldGet.getInstance().getFieldName(
						CmpBillFieldGet.H_BILLSTATUS));
				if (billstatus != null
						&& (billstatus.equals(BusiStatus.Tempeorary.getBillStatusKind()) || billstatus
								.equals(BusiStatus.Save.getBillStatusKind()))) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * 来源系统不是现金管理的单据，应控制不能手工删除，需要由上游单据来反操作才能删除
	 * 
	 * @throws BusinessException
	 */
	private void beforeAction() throws BusinessException {
		AggregatedValueObject[] aggs = getSelectedAggVOs();
		if (aggs == null) {
			ExceptionHandler.cteateandthrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0",
					"03607mng-0036")/* @res "请选择要操作的数据" */);
		}
		CmpBillFieldGet cmpBillFieldGet = CmpBillFieldGet.getInstance();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0, length = aggs.length; i < length; i++) {
			SuperVO parentVO = (SuperVO) aggs[i].getParentVO();
			String src_syscode = (String) parentVO.getAttributeValue(cmpBillFieldGet
					.getFieldName(CmpBillFieldGet.H_SRC_SYSCODE));
			if (!("2".equals(src_syscode) || "8".equals(src_syscode) || "9".equals(src_syscode))) {
				String billno = (String) parentVO.getAttributeValue(cmpBillFieldGet
						.getFieldName(CmpBillFieldGet.H_BILLNO));
				sBuilder.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0", "03607mng-0432",
						null, new String[] { billno }));

			} else {
				Integer billstatus = (Integer) parentVO.getAttributeValue(CmpBillFieldGet.getInstance().getFieldName(
						CmpBillFieldGet.H_BILLSTATUS));
				if (billstatus != null
						&& !(billstatus.equals(BusiStatus.Tempeorary.getBillStatusKind()) || billstatus
								.equals(BusiStatus.Save.getBillStatusKind()))) {
					String billno = (String) parentVO.getAttributeValue(cmpBillFieldGet
							.getFieldName(CmpBillFieldGet.H_BILLNO));
					sBuilder.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0", "03607mng-0433",
							null, new String[] { billno }));

				}
			}

			if (UFBoolean.TRUE.equals(parentVO.getAttributeValue("sddreversalflag"))) {
				String billno = (String) parentVO.getAttributeValue(cmpBillFieldGet
						.getFieldName(CmpBillFieldGet.H_BILLNO));
				sBuilder.append(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0", "03607mng-0434",
						null, new String[] { billno }));

			}
		}
		if (sBuilder.length() > 0) {
			ExceptionHandler.cteateandthrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607mng_0",
					"03607mng-0039")/* @res "请重新选择：\n" */
					+ sBuilder.toString());
		}
	}

	/*
	 * 获取选择VOs的聚合aggvos
	 * 
	 * @return
	 */
	private AggregatedValueObject[] getSelectedAggVOs() {
		Object[] value = null;
		// 判断显示什么界面
		if (listView != null && listView.isShowing()) {
			value = (Object[]) (listView).getModel().getSelectedOperaDatas();
		} else if (editor.isShowing()) {
			value = new Object[1];
			value[0] = ((BillForm) editor).getModel().getSelectedData();
			if (value[0] == null) {
				return null;
			}
		}

		if (null == value || value.length == 0) {
			return null;
		}
		AggregatedValueObject[] aggs = new AggregatedValueObject[value.length];
		System.arraycopy(value, 0, aggs, 0, aggs.length);
		return aggs;
	}

	public MutilTransBillForm getEditor() {
		return editor;
	}

	public void setEditor(MutilTransBillForm editor) {
		this.editor = editor;
	}

	public MutilTransBillListView getListView() {
		return listView;
	}

	public void setListView(MutilTransBillListView listView) {
		this.listView = listView;
	}

}