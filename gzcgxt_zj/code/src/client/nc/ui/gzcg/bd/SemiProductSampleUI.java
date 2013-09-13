package nc.ui.gzcg.bd;

import nc.ui.trade.bill.ICardController;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;

public class SemiProductSampleUI extends SampleManUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SemiProductSampleUI() {
		super();
		getBillCardPanel().getBodyItem(samplevo.VQCTYPE).setEdit(false);
		getBillCardPanel().getBodyItem(samplevo.VORDERBILL).setEdit(false);
		getBillCardPanel().getBodyItem(samplevo.VDEF1).setEdit(true);
		getBillCardPanel().getBodyItem("vinvmancode").setEdit(false);
	}
	
	protected ICardController createController() {
		return new SampleManController(samplevo.VQCTYPE+"='S' and pk_corp='"+_getCorp().getPrimaryKey()+"'");
	}
	
	@Override
	public void setLineDefaultData(int rowNo) throws ValidationException, BusinessException {
		getBillCardPanel().setBodyValueAt("S", rowNo, samplevo.VQCTYPE);
		
		String code = getBillCode();
		code = String.valueOf(5000000+Integer.parseInt(code));
		getBillCardPanel().setBodyValueAt(code, rowNo, samplevo.VDEF2);
	}

	@Override
	protected String getBillType() {
		return "36ZA";
	}
}
