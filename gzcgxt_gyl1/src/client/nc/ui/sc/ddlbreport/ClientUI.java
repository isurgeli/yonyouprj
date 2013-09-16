package nc.ui.sc.ddlbreport;

import javax.swing.table.TableColumn;

import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.table.GroupableTableHeader;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillItemEvent;
import nc.ui.pub.bill.BillMouseEnent;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pub.bill.BillTableMouseListener;
import nc.ui.trade.base.IBillOperate;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.vo.pub.SuperVO;
import nc.vo.trade.button.ButtonVO;
import nc.vo.trade.pub.HYBillVO;

/**
 * 说明：
 */
public class ClientUI extends BillCardUI implements nc.ui.pub.bill.BillCardBeforeEditListener{

	private static final long serialVersionUID = 2345667L;
	/**
	 * 
	 */
	
	public ClientUI() {
		// TODO 自动生成构造函数存根
		super();
//		init();
		this.getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
		getBillCardPanel().addBodyMouseListener(new BillTableMouseListener() {
			
			public void mouse_doubleclick(BillMouseEnent e) {
			}
		});
	}
//	private void init()
//	{
//  		readData();
//	}
	@Override
	protected void initPrivateButton() {
		ButtonVO btnVo = new ButtonVO();
		btnVo.setBtnNo(1000);
		btnVo.setBtnName("联查");
		btnVo.setBtnChinaName("联查");
		btnVo.setHintStr("联查");
		// 已接收状态可以
		btnVo.setOperateStatus(new int[]{IBillOperate.OP_NOTEDIT});
		addPrivateButton(btnVo);
		super.initPrivateButton();
	}
	
	/**
	 * 
	 * 说明：获取数据库中数据,并显示在界面
	 *
	 */
	 public void readData()
	    {
		   
            HYBillVO[] dataVOs = new HYBillVO[1];
			SuperVO[] bodyVOs = null;
			try
			{
				Class<?> c = Class.forName(createController().getBillVoName()[1]);
				bodyVOs =  getBusiDelegator().queryByCondition(c, 
						" isnull(dr,0)=0 "+ getQueryWherePart());
				if (bodyVOs != null)
				{
					dataVOs[0] = new HYBillVO();
					dataVOs[0].setChildrenVO(bodyVOs);
					dataVOs[0].setParentVO(null);
				}
			}
			catch (java.lang.Exception e)
			{
				e.printStackTrace();
			}

			getBufferData().clear();
			if(getBufferData() == null || getBufferData().getVOBufferSize() <=0){
				getBufferData().addVOsToBuffer(dataVOs);
			}else{
				getBufferData().clear();
				if(dataVOs[0].getChildrenVO() != null && dataVOs[0].getChildrenVO().length > 0)
					getBufferData().setCurrentVO(dataVOs[0]);
				else
					getBufferData().clear();
			}
//	    	getBufferData().setCurrentRow(0);
			
			try {
				if (getBufferData().getVOBufferSize() != 0) {
						setListHeadData(
								getBufferData().getAllHeadVOsFromBuffer());
					
					setBillOperate(IBillOperate.OP_NOTEDIT);
					getBufferData().setCurrentRow(0);
				} else {
					setListHeadData(null);
					setBillOperate(IBillOperate.OP_INIT);
					getBufferData().setCurrentRow(-1);
					showHintMessage(
							nc.ui.ml.NCLangRes.getInstance().getStrByID("uifactory",
									"UPPuifactory-000066")/* @res "没有查到任何满足条件的数据!" */);
				}
			} catch (Exception e) {
				// TODO 自动生成 catch 块
				e.printStackTrace();
			}
	    }

	/**
	 * 说明：
	 * 
	 */
	public ClientUI(String pk_corp, String pk_billType, String pk_busitype,
			String operater, String billId) {
		super(pk_corp, pk_billType, pk_busitype, operater, billId);
//		init();
		this.getBillCardPanel().setBillBeforeEditListenerHeadTail(this);
		// TODO 自动生成构造函数存根
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.card.BillCardUI#createController()
	 */
	@Override
	protected ICardController createController() {
		// TODO 自动生成方法存根
		return new ClientController();
	}
	@Override
	protected CardEventHandler createEventHandler()
	{
		return new ClientEventHandler(this,getUIControl());
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.base.AbstractBillUI#getRefBillType()
	 */
	@Override
	public String getRefBillType() {
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.base.AbstractBillUI#initSelfData()
	 */
	@Override
	protected void initSelfData() {
		reCombineDevelColumn();
	}
	/* （非 Javadoc）
	 * @see nc.ui.trade.base.AbstractBillUI#setDefaultData()
	 */
	@Override
	public void setDefaultData() throws Exception {
	}
	@Override
	public boolean beforeEdit(BillEditEvent e) {
		return true;
	}
	public boolean beforeEdit(BillItemEvent e) {
		// TODO 自动生成方法存根
		return false;
	}
	
	protected String getQueryWherePart(){
		
		return "";
		
	}
	/**
	 * 合并表头
	 */
	private void reCombineDevelColumn(){

		BillScrollPane scrollPane = this.getBillCardPanel().getBodyPanel();
		GroupableTableHeader tableheaer = (GroupableTableHeader)scrollPane.getTable().getTableHeader();

		ColumnGroup  cp = new  ColumnGroup("加工品情况");
		ColumnGroup  cl = new  ColumnGroup("材料情况");

		BillItem []items = getBillCardPanel().getBillModel().getBodyItems();
		for (int i = 0; i < items.length; i++) {
			if(items[i].getKey().startsWith("b_")){
				TableColumn temp = scrollPane.getShowCol(items[i].getKey());
				cp.add(temp);
			}else if(items[i].getKey().startsWith("d_")){
				TableColumn temp = scrollPane.getShowCol(items[i].getKey());
				cl.add(temp);
			}else{}
		}
		tableheaer.addColumnGroup(cp);
		tableheaer.addColumnGroup(cl);
	}
}
