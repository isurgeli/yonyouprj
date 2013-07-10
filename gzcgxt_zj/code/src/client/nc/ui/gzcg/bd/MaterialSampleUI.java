package nc.ui.gzcg.bd;

import nc.ui.trade.bill.ICardController;
import nc.vo.gzcg.bd.samplevo;

public class MaterialSampleUI extends SampleManUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MaterialSampleUI() {
		super();
		getBillCardPanel().getBodyItem(samplevo.VQCTYPE).setEdit(false);
		getBillCardPanel().getBodyItem(samplevo.VSTOCKBATCH).setEdit(false);
	}
	
	protected ICardController createController() {
		return new SampleManController(samplevo.VQCTYPE+"='M' and pk_corp='"+_getCorp().getPrimaryKey()+"'");
	}
	
	@Override
	public void setLineDefaultData(int rowNo) {
		getBillCardPanel().setBodyValueAt("M", rowNo, samplevo.VQCTYPE);
	}

}
