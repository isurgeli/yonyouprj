package nc.ui.gzcg.bd;

import java.util.List;

import nc.bs.pub.billcodemanage.BillcodeGenerater;
import nc.itf.gzcg.bd.SampleManGetCheck;
import nc.itf.trade.excelimport.IImportableEditor;
import nc.itf.trade.excelimport.ImportableInfo;
import nc.ui.bd.pub.AbstractBdBillCardUI;
import nc.ui.bd.pub.IBDUIExtendStauts;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.IBillItem;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.trade.excelimport.InputItem;
import nc.ui.trade.excelimport.InputItemCreator;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ExtendedAggregatedValueObject;
import nc.vo.pub.ValidationException;

public abstract class SampleManUI extends AbstractBdBillCardUI implements IImportableEditor, BillEditListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7110028852065776536L;

	/**
	 * 
	 */
	public SampleManUI() {
		super();
		getBillCardPanel().addBillEditListenerHeadTail(this);
	}
	
	protected ICardController createController() {
		return new SampleManController("pk_corp='"+_getCorp().getPrimaryKey()+"'");
	}

	public String getRefBillType() {
		return null;
	}

	protected void initSelfData() {
		//super.initSelfData();
	}

	public void setDefaultData() throws Exception {
	}
	
	abstract protected String getBillType();

	public Object getUserObject() {
		return new SampleManGetCheck(getUIControl().getBodyCondition());
	}
	
	protected CardEventHandler createEventHandler() {
		return new SampleManEventHandler(this, getUIControl());
	}
	
	protected void initPrivateButton()
	{
	}
	
	/**
	 * �����¼����������
	 */
	private SampleManEventHandler getEhd() {
		return (SampleManEventHandler) getCardEventHandler();
	}
	
	/** 
	 * ���ص����Ƿ�ɵ�����Ϣ(Ĭ�Ͽɵ���)
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#getImportableInfo()
	 */
	public ImportableInfo getImportableInfo() {
		return new ImportableInfo();
	}
	
	/**
	 * ��������
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#addNew()
	 */
	public void addNew() {
		try {
            setBDUIExtendStatus(IBDUIExtendStauts.BD_ADD);
			setBillOperate(IBillOperate.OP_EDIT);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ȡ��
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#cancel()
	 */
	public void cancel() {
		try {
			getEhd().onBoCancel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#save()
	 */
	public void save() throws Exception {
		getEhd().onBoSave();
	}

	/**
	 * ���������õ�������
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object obj) {
		ExtendedAggregatedValueObject vo = (ExtendedAggregatedValueObject) obj;
		getBillCardPanel().getBillData().setImportBillValueVO(vo);
	}

	/**
	 * �����������б�
	 * 
	 * @see nc.itf.trade.excelimport.IImportableEditor#getInputItems()
	 */
	public List<InputItem> getInputItems() {
		return InputItemCreator.getInputItems(getBillCardPanel().getBillData(),
				false);
	}
	
	public void afterEdit(BillEditEvent e) {
		if (IBillItem.BODY == e.getPos() && e.getKey().equals("vinvmancode")){
			int rowNo = getBillCardPanel().getBillTable().getSelectedRow();
			getBillCardPanel().execBodyFormula(rowNo, samplevo.PK_INVMANDOC);
		}
	}
	
	protected String getBillCode() throws ValidationException, BusinessException{
		BillcodeGenerater gen = new BillcodeGenerater();
		String code = gen.getBillCode(getBillType(), _getCorp().getPrimaryKey(), null, null);
		
		return code;
	}
	
	public abstract void setLineDefaultData(int rowNo) throws ValidationException, BusinessException;
}
