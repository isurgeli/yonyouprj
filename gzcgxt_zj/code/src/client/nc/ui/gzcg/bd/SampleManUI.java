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
	 * 返回事件处理类对象
	 */
	private SampleManEventHandler getEhd() {
		return (SampleManEventHandler) getCardEventHandler();
	}
	
	/** 
	 * 返回档案是否可导入信息(默认可导入)
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#getImportableInfo()
	 */
	public ImportableInfo getImportableInfo() {
		return new ImportableInfo();
	}
	
	/**
	 * 新增数据
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
	 * 取消
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
	 * 保存
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#save()
	 */
	public void save() throws Exception {
		getEhd().onBoSave();
	}

	/**
	 * 将数据设置到界面上
	 *
	 * @see nc.itf.trade.excelimport.IImportableEditor#setValue(java.lang.Object)
	 */
	public void setValue(Object obj) {
		ExtendedAggregatedValueObject vo = (ExtendedAggregatedValueObject) obj;
		getBillCardPanel().getBillData().setImportBillValueVO(vo);
	}

	/**
	 * 返回输入项列表
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
