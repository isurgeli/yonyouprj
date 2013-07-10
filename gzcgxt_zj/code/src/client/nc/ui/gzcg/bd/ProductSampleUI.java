package nc.ui.gzcg.bd;

import nc.ui.trade.bill.ICardController;
import nc.vo.gzcg.bd.samplevo;

public class ProductSampleUI extends SampleManUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ProductSampleUI() {
		super();
		getBillCardPanel().getBodyItem(samplevo.VQCTYPE).setEdit(false);
		getBillCardPanel().getBodyItem(samplevo.VORDERBILL).setEdit(false);
	}
	
	protected ICardController createController() {
		return new SampleManController(samplevo.VQCTYPE+"='P' and pk_corp='"+_getCorp().getPrimaryKey()+"'");
	}
	
	@Override
	public void setLineDefaultData(int rowNo) {
		getBillCardPanel().setBodyValueAt("P", rowNo, samplevo.VQCTYPE);
	}

}
