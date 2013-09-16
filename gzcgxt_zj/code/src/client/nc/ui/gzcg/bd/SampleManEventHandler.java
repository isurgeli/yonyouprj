package nc.ui.gzcg.bd;


import nc.ui.bd.pub.AbstractBdBillCardUI;
import nc.ui.bd.pub.DefaultBDBillCardEventHandle;
import nc.ui.bd.pub.IBDUIExtendStauts;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.businessaction.BdBusinessAction;
import nc.ui.trade.businessaction.IBusinessController;
import nc.ui.trade.card.BillCardUI;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFBoolean;

public class SampleManEventHandler extends DefaultBDBillCardEventHandle
{
	/**
	 * @param billUI
	 * @param control
	 */
	public SampleManEventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
	}

	protected void onBoSave() throws Exception {
		try{
			super.onBoSave();
		
			String strWhere = "1=1";

			strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";

			if (getUIController().getBodyCondition() != null)
				strWhere = strWhere + " and " + getUIController().getBodyCondition();
		
			doBodyQuery(strWhere);
		}catch(nc.uif.pub.exception.UifException ex){
			String msg = ex.getMessage();
			MessageDialog.showErrorDlg(getBillUI(), "错误", msg);
			
			msg = msg.split(":")[1];
			String vdef1 = msg.split(",")[1].split("=")[1];
			String sampleno = msg.split(",")[2].split("=")[1];
			sampleno = sampleno.substring(0, sampleno.length()-1);
			
			for (int i=0;i<getBillCardPanelWrapper().getBillCardPanel().getBillTable().getRowCount();i++){
				if (getBillCardPanelWrapper().getBillCardPanel().getBillModel().getValueAt(i, samplevo.VDEF1).equals(vdef1)
						&& getBillCardPanelWrapper().getBillCardPanel().getBillModel().getValueAt(i, samplevo.VSAMPLENO).equals(sampleno)){
					getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectionModel().setSelectionInterval(i, i);
					break;
				}
			}
		}
	}

	protected void processCopyedBodyVOsBeforePaste(CircularlyAccessibleValueObject[] vos) {
		super.processCopyedBodyVOsBeforePaste(vos);
	}

	protected CircularlyAccessibleValueObject processNewBodyVO(CircularlyAccessibleValueObject newBodyVO)	{
		return super.processNewBodyVO(newBodyVO);
	}

	protected IBusinessController createBusinessAction() {
		return new BdBusinessAction(getBillUI());
	}
	
	@Override
	protected void onBoCancel() throws Exception {
		super.onBoCancel();
	}
	
	@Override
	public void onBoAdd(ButtonObject bo) throws Exception {
		getBufferData().clear();
		updateBuffer();
		super.onBoAdd(bo);
	}
	
	@Override
	protected void onBoLineAdd() throws Exception {
		super.onBoLineAdd();
		int rowNo = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), rowNo, samplevo.PK_CORP);
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getDate(), rowNo, samplevo.DDATE);
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(ClientEnvironment.getInstance().getUser().getPrimaryKey(), rowNo, samplevo.PK_USER);
		getBillCardPanelWrapper().getBillCardPanel().setBodyValueAt(new UFBoolean(false), rowNo, samplevo.BURGENT);
		
		((SampleManUI)getBillUI()).setLineDefaultData(rowNo);
	}
	
	@Override
	protected void onBoLinePaste() throws Exception {
		processCopyedBodyVOsBeforePaste(getBillCardPanelWrapper()
				.getCopyedBodyVOs());
		
		if (getBillCardPanelWrapper().getCopyedBodyVOs() != null)
			for (int i = 0; i < getBillCardPanelWrapper().getCopyedBodyVOs().length; i++) {
				getBillCardPanelWrapper().getBillCardPanel().stopEditing();
				getBillCardPanelWrapper().getBillCardPanel().insertLine();
				int selectedRow = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().setBodyRowVO(getBillCardPanelWrapper().getCopyedBodyVOs()[i], selectedRow);
				((SampleManUI)getBillUI()).setLineDefaultData(selectedRow);
				
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().execLoadFormulaByRow(selectedRow);
			}
	}
	
	@Override
	protected void onBoLinePasteToTail() throws Exception {
		processCopyedBodyVOsBeforePaste(getBillCardPanelWrapper()
				.getCopyedBodyVOs());
		
		if (getBillCardPanelWrapper().getCopyedBodyVOs() != null)
			for (int i = 0; i < getBillCardPanelWrapper().getCopyedBodyVOs().length; i++) {
				getBillCardPanelWrapper().getBillCardPanel().stopEditing();
				getBillCardPanelWrapper().getBillCardPanel().addLine();
				int lastrow = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getRowCount()-1;
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().setBodyRowVO(getBillCardPanelWrapper().getCopyedBodyVOs()[i], lastrow);
				((SampleManUI)getBillUI()).setLineDefaultData(lastrow);
				
				getBillCardPanelWrapper().getBillCardPanel().getBillModel().execLoadFormulaByRow(lastrow);
			}
	}
	
	@Override
	protected void onBoDelete() throws Exception {
		int[] rows = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRows();
		
		if (rows.length <= 0){
			getBillUI().showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("10082202","UPP10082202-000014")/*@res "请先要选择删除的行"*/);
			return;
		}
		if (MessageDialog.showOkCancelDlg(getBillUI(), NCLangRes4VoTransl
				.getNCLangRes()
				.getStrByID("10082202", "UPT10082202-000007"/* 确定删除 */),
				NCLangRes4VoTransl.getNCLangRes().getStrByID("10082202",
						"UPT10082202-000008")/* 您确定要删除所选数据吗 */,MessageDialog.ID_CANCEL) != MessageDialog.ID_OK)
			return;
		
		for (int i=0;i<rows.length;i++){
			deleteLine(rows[i]-i);
		}
	}
	
	private void deleteLine(int row) throws Exception {
		getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectionModel().setSelectionInterval(row, row);
		
		getBillUI().setBillOperate(IBillOperate.OP_EDIT);
		getBillCardPanelWrapper().setRowStateToNormal();
		getBillCardPanelWrapper().getBillCardPanel().delLine();
		
		try
		{
			onBoSave();
		}
		catch(BusinessException e)
		{
				getBufferData().setCurrentRow(getBufferData().getCurrentRow());
				getBillUI().setBillOperate(IBillOperate.OP_NOTEDIT);
				throw e;				
		}
		finally {
			int rowCount = getBillCardPanelWrapper().getBillCardPanel().getRowCount();
			getBillCardPanelWrapper().getBillCardPanel().getBillTable().changeSelection(Math.min(row,rowCount-1),0,false,false);
			getBillUI().showHintMessage("");
		}
	}
	
	@Override
	protected void onBoEdit() throws Exception {
		int[] editrows = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRows();
		
		if (editrows.length <= 0){
			throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("10082202","UPP10082202-000015")/*@res "必须先选中一行才可进行编辑"*/);
		}
		super.onBoEdit();


		((AbstractBdBillCardUI) getBillUI())
				.setBDUIExtendStatus(IBDUIExtendStauts.BD_EDIT);
		//		getBillCardPanelWrapper().getBillCardPanel().getBillTable().setEnabled(false);
		int rowCount = getBillCardPanelWrapper().getBillCardPanel()
				.getRowCount();

		getBillCardPanelWrapper().getBillCardPanel().getBillModel()
				.setRowEditState(true);
		int index = 0;
		int[] rows = new int[rowCount - 1];
		for (int i = 0; i < rowCount; i++)
		{
			boolean have = false;
			for (int j=0;j<editrows.length;j++){
				if (i==editrows[j]){
					have = true;
					break;
				}
			}
			if (have)
				continue;
			rows[index++] = i;
		}
		getBillCardPanelWrapper().getBillCardPanel().getBillModel()
				.setNotEditAllowedRows(rows);
	}
}