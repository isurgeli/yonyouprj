package nc.ui.gzcg.bd;


import nc.ui.bd.pub.DefaultBDBillCardEventHandle;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.bill.BillItem;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.businessaction.BdBusinessAction;
import nc.ui.trade.businessaction.IBusinessController;
import nc.ui.trade.card.BillCardUI;
import nc.vo.gzcg.bd.samplevo;
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
		super.onBoSave();
		
		String strWhere = "1=1";

		strWhere = "(" + strWhere + ") and (isnull(dr,0)=0)";

		if (getUIController().getBodyCondition() != null)
			strWhere = strWhere + " and " + getUIController().getBodyCondition();
		
		doBodyQuery(strWhere);
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
}