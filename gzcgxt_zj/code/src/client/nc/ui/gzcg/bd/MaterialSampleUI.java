package nc.ui.gzcg.bd;

import nc.ui.trade.bill.ICardController;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ValidationException;

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
	public void setLineDefaultData(int rowNo) throws ValidationException, BusinessException {
		getBillCardPanel().setBodyValueAt("M", rowNo, samplevo.VQCTYPE);
		String code = getBillCode();
		code = String.valueOf(3000000+Integer.parseInt(code));
		getBillCardPanel().setBodyValueAt(code, rowNo, samplevo.VDEF2);
	}

	@Override
	protected String getBillType() {
		return "36LL";
	}

}
