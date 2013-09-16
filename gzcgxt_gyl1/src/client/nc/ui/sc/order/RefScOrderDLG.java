package nc.ui.sc.order;

import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.bill.BillModel;
import nc.ui.scm.pu.SCMPuTool;
import nc.ui.scm.pub.DigitalSetTool;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.scm.pub.DigitalSetPara;

/**
 * 根据委外订单生成库存委托加工入库单参照界面
 * 版本号:
 * 功能描述:
 * 作者:许京黎
 * 创建日期:
 * 修改人:ljq 增加对自定义项的处理
 * <p>
 * <b>变更历史：</b>
 * <p>
 * <hr>
 * <p>修改日期 2008.8.26
 * <p>修改人 zhaoyha
 * <p>版本 5.5
 * <p>说明：
 * <ul>
 * <li>选中模式调整
 * <li>参见:{@link #getbillListPanel()}
 * </ul>
 */

public class RefScOrderDLG extends nc.ui.pub.pf.BillSourceDLG {
	
	private nc.ui.pub.bill.BillListPanel m_ScOrderListPanel = null;
	
	/**
	 * RefScOrderDLG 构造子注解。
	 * 
	 * @param pkField
	 *            java.lang.String
	 * @param pkCorp
	 *            java.lang.String
	 * @param operator
	 *            java.lang.String
	 * @param funNode
	 *            java.lang.String
	 * @param queryWhere
	 *            java.lang.String
	 * @param billType
	 *            java.lang.String
	 * @param businessType
	 *            java.lang.String
	 * @param templateId
	 *            java.lang.String
	 * @param currentBillType
	 *            java.lang.String
	 * @param parent
	 *            java.awt.Container
	 */
	public RefScOrderDLG(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			java.awt.Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
				businessType, templateId, currentBillType, parent);
		// 设置精度
    //V55去除辅币折算
//		if (getbillListPanel().getBodyItem("nexchangeotoarate") != null)
//			getbillListPanel().getBodyItem("nexchangeotoarate")
//					.setDecimalDigits(4);
		if (getbillListPanel().getBodyItem("nexchangeotobrate") != null)
			getbillListPanel().getBodyItem("nexchangeotobrate")
					.setDecimalDigits(4);
		Vector v = new Vector();
		
		nc.vo.scm.pub.DigitalSetPara para = null;
		// 数量
		String[] keys = new String[] { "nordernum" };
		for (int i = 0; i < keys.length; i++) {
			para = new DigitalSetPara(keys[i], "BD501", new Integer(1),
					new UFBoolean(false));
			v.addElement(para);
		}
		// 辅数量
		para = new DigitalSetPara("nassistnum", "BD502", new Integer(1),
				new UFBoolean(false));
		v.addElement(para);
		// 单价
		keys = new String[] { "noriginalcurprice", "noriginalnetprice" };
		for (int i = 0; i < keys.length; i++) {
			para = new DigitalSetPara(keys[i], "BD505", new Integer(1),
					new UFBoolean(false));
			v.addElement(para);
		}
		// 换算率
		para = new nc.vo.scm.pub.DigitalSetPara("measrate", "BD503",
				new Integer(1), new UFBoolean(false));
		v.addElement(para);
		// 金额
		keys = new String[] { "noriginalcurmny", "noriginaltaxmny",
				"noriginalsummny", "nmoney", "ntaxmny", "nsummny" };
		for (int i = 0; i < keys.length; i++) {
			para = new DigitalSetPara(keys[i], "BD301", new Integer(1),
					new UFBoolean(true));
			v.addElement(para);
		}
		// 调用公用方法设置精度
		DigitalSetPara[] paras = new DigitalSetPara[v.size()];
		v.copyInto(paras);
		DigitalSetTool.setBillListPnlDigital(pkCorp, getbillListPanel(), paras);
	}

	/**
	 * 对列表模版进行处理 返回： 例外： 日期：(2003-3-4 9:02:24) 作者：李金巧 修改日期，修改人，修改原因，注释标志：
 * <p>
 * <b>变更历史：</b>
 * <p>
 * <hr>
 * <p>修改日期 2008.8.26
 * <p>修改人 zhaoyha
 * <p>版本 5.5
 * <p>说明：
 * <ul>
 * <li>选中模式调整
 * </ul>
	 * @return nc.ui.pub.bill.BillListPanel
	 */
	protected nc.ui.pub.bill.BillListPanel getbillListPanel() {
		if (m_ScOrderListPanel == null) {
			m_ScOrderListPanel = super.getbillListPanel();
			// 处理自定义项
			nc.ui.sc.pub.ScTool.changeListDataByUserDef(m_ScOrderListPanel,
					nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
							.getPrimaryKey(), "61", nc.ui.pub.ClientEnvironment
							.getInstance().getUser().getPrimaryKey());

			// V55增加下拉菜单  
      UIMenuItem[] miBody = {m_ScOrderListPanel.getParentListPanel().getMiAllSelctRow(),m_ScOrderListPanel.getParentListPanel().getMiCancelAllSelctRow() };
      m_ScOrderListPanel.getParentListPanel().setMiBody(miBody);
      m_ScOrderListPanel.getParentListPanel().setBBodyMenuShow(true);
      m_ScOrderListPanel.getParentListPanel().addTableBodyMenu();
      			
			//v55选中模式调整
      SCMPuTool.setLineSelectedList(m_ScOrderListPanel);
      //再设置主子表,表体的选中模式
      m_ScOrderListPanel.getBodyTable().setRowSelectionAllowed(true);
      m_ScOrderListPanel.getBodyTable().setColumnSelectionAllowed(false);
      m_ScOrderListPanel.getBodyTable().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      m_ScOrderListPanel.setMultiSelect(true);
      //表体列表 行切换事件处理器
      m_ScOrderListPanel.getBodyTable().getSelectionModel()
          .addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
              //设置行选中
              int nSelected[] = m_ScOrderListPanel.getBodyTable().getSelectedRows();
              if (nSelected != null && nSelected.length > 0) {
                for (int i = 0; i < nSelected.length; i++) {
                  m_ScOrderListPanel.getBodyBillModel().setRowState(nSelected[i],BillModel.SELECTED);
                }
              }
            }
            
          });
      
		}
		return m_ScOrderListPanel;

	}

	/**
	 * 功能描述：参照委外订单生成委托加工入库单时，获取表体条件
	 * 返回：String
	 * 例外：无 
	 * 修改日期：2006-11-22
	 * 修改人：lxd
	 * 修改原因：数量关系约束：累计入库数量-退库数量<订单数量
	 */
	public String getBodyCondition() {
		return " abs(isnull(sc_order_b.naccumstorenum,0))-abs(isnull(sc_order_b.nbackstorenum,0))<abs(isnull(sc_order_b.nordernum,0)) and isnull(sc_order_b.nordernum,0) > 0 ";
	}

	public String[] getBodyHideCol() {
		return null;
	}

	/**
	 * 功能描述：参照委外订单生成委托加工入库单时，获取表头条件
	 * 返回：String
	 * 例外：无 
	 * 修改日期：2006-11-22
	 * 修改人：lxd
	 * 修改原因：数量关系约束：累计入库数量-退库数量<订单数量
	 */
	public String getHeadCondition() {
		String whereSql = " sc_order.ibillstatus=3 and sc_order.pk_corp='"
				+ getPkCorp()
				+ "' and abs(isnull(sc_order_b.naccumstorenum,0))-abs(isnull(sc_order_b.nbackstorenum,0))<abs(isnull(sc_order_b.nordernum,0)) and isnull(sc_order_b.nordernum,0) > 0 ";
		return whereSql;
	}

	public String[] getHeadHideCol() {
		String[] hideCols = { "cpurorganization", "cwareid", "cvendorid",
				"cvendormangid", "caccountbankid", "cdeptid", "cemployeeid",
				"creciever", "cgiveinvoicevendor", "ctransmodeid",
				"ctermprotocolid", "coperator", "cbiztype", "cauditpsn" };
		return hideCols;
	}

  /**
   * 
   * 父类方法重写
   * 
   * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
   */
  public void valueChanged(ListSelectionEvent e) {
    //设置表头选中
    int nSelected[] = getbillListPanel().getHeadTable().getSelectedRows();
    if (nSelected != null && nSelected.length > 0) {
      for (int i = 0; i < nSelected.length; i++) {
        getbillListPanel().getHeadBillModel().setRowState(nSelected[i],BillModel.SELECTED);
      }
    }
   //调用父方法处理表体选择
    super.valueChanged(e);
    getbillListPanel().getChildListPanel().selectAllTableRow();
  }
}
