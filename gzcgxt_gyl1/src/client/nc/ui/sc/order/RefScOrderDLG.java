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
 * ����ί�ⶩ�����ɿ��ί�мӹ���ⵥ���ս���
 * �汾��:
 * ��������:
 * ����:����
 * ��������:
 * �޸���:ljq ���Ӷ��Զ�����Ĵ���
 * <p>
 * <b>�����ʷ��</b>
 * <p>
 * <hr>
 * <p>�޸����� 2008.8.26
 * <p>�޸��� zhaoyha
 * <p>�汾 5.5
 * <p>˵����
 * <ul>
 * <li>ѡ��ģʽ����
 * <li>�μ�:{@link #getbillListPanel()}
 * </ul>
 */

public class RefScOrderDLG extends nc.ui.pub.pf.BillSourceDLG {
	
	private nc.ui.pub.bill.BillListPanel m_ScOrderListPanel = null;
	
	/**
	 * RefScOrderDLG ������ע�⡣
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
		// ���þ���
    //V55ȥ����������
//		if (getbillListPanel().getBodyItem("nexchangeotoarate") != null)
//			getbillListPanel().getBodyItem("nexchangeotoarate")
//					.setDecimalDigits(4);
		if (getbillListPanel().getBodyItem("nexchangeotobrate") != null)
			getbillListPanel().getBodyItem("nexchangeotobrate")
					.setDecimalDigits(4);
		Vector v = new Vector();
		
		nc.vo.scm.pub.DigitalSetPara para = null;
		// ����
		String[] keys = new String[] { "nordernum" };
		for (int i = 0; i < keys.length; i++) {
			para = new DigitalSetPara(keys[i], "BD501", new Integer(1),
					new UFBoolean(false));
			v.addElement(para);
		}
		// ������
		para = new DigitalSetPara("nassistnum", "BD502", new Integer(1),
				new UFBoolean(false));
		v.addElement(para);
		// ����
		keys = new String[] { "noriginalcurprice", "noriginalnetprice" };
		for (int i = 0; i < keys.length; i++) {
			para = new DigitalSetPara(keys[i], "BD505", new Integer(1),
					new UFBoolean(false));
			v.addElement(para);
		}
		// ������
		para = new nc.vo.scm.pub.DigitalSetPara("measrate", "BD503",
				new Integer(1), new UFBoolean(false));
		v.addElement(para);
		// ���
		keys = new String[] { "noriginalcurmny", "noriginaltaxmny",
				"noriginalsummny", "nmoney", "ntaxmny", "nsummny" };
		for (int i = 0; i < keys.length; i++) {
			para = new DigitalSetPara(keys[i], "BD301", new Integer(1),
					new UFBoolean(true));
			v.addElement(para);
		}
		// ���ù��÷������þ���
		DigitalSetPara[] paras = new DigitalSetPara[v.size()];
		v.copyInto(paras);
		DigitalSetTool.setBillListPnlDigital(pkCorp, getbillListPanel(), paras);
	}

	/**
	 * ���б�ģ����д��� ���أ� ���⣺ ���ڣ�(2003-3-4 9:02:24) ���ߣ������ �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * <p>
 * <b>�����ʷ��</b>
 * <p>
 * <hr>
 * <p>�޸����� 2008.8.26
 * <p>�޸��� zhaoyha
 * <p>�汾 5.5
 * <p>˵����
 * <ul>
 * <li>ѡ��ģʽ����
 * </ul>
	 * @return nc.ui.pub.bill.BillListPanel
	 */
	protected nc.ui.pub.bill.BillListPanel getbillListPanel() {
		if (m_ScOrderListPanel == null) {
			m_ScOrderListPanel = super.getbillListPanel();
			// �����Զ�����
			nc.ui.sc.pub.ScTool.changeListDataByUserDef(m_ScOrderListPanel,
					nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
							.getPrimaryKey(), "61", nc.ui.pub.ClientEnvironment
							.getInstance().getUser().getPrimaryKey());

			// V55���������˵�  
      UIMenuItem[] miBody = {m_ScOrderListPanel.getParentListPanel().getMiAllSelctRow(),m_ScOrderListPanel.getParentListPanel().getMiCancelAllSelctRow() };
      m_ScOrderListPanel.getParentListPanel().setMiBody(miBody);
      m_ScOrderListPanel.getParentListPanel().setBBodyMenuShow(true);
      m_ScOrderListPanel.getParentListPanel().addTableBodyMenu();
      			
			//v55ѡ��ģʽ����
      SCMPuTool.setLineSelectedList(m_ScOrderListPanel);
      //���������ӱ�,�����ѡ��ģʽ
      m_ScOrderListPanel.getBodyTable().setRowSelectionAllowed(true);
      m_ScOrderListPanel.getBodyTable().setColumnSelectionAllowed(false);
      m_ScOrderListPanel.getBodyTable().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      m_ScOrderListPanel.setMultiSelect(true);
      //�����б� ���л��¼�������
      m_ScOrderListPanel.getBodyTable().getSelectionModel()
          .addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
              //������ѡ��
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
	 * ��������������ί�ⶩ������ί�мӹ���ⵥʱ����ȡ��������
	 * ���أ�String
	 * ���⣺�� 
	 * �޸����ڣ�2006-11-22
	 * �޸��ˣ�lxd
	 * �޸�ԭ��������ϵԼ�����ۼ��������-�˿�����<��������
	 */
	public String getBodyCondition() {
		return " abs(isnull(sc_order_b.naccumstorenum,0))-abs(isnull(sc_order_b.nbackstorenum,0))<abs(isnull(sc_order_b.nordernum,0)) and isnull(sc_order_b.nordernum,0) > 0 ";
	}

	public String[] getBodyHideCol() {
		return null;
	}

	/**
	 * ��������������ί�ⶩ������ί�мӹ���ⵥʱ����ȡ��ͷ����
	 * ���أ�String
	 * ���⣺�� 
	 * �޸����ڣ�2006-11-22
	 * �޸��ˣ�lxd
	 * �޸�ԭ��������ϵԼ�����ۼ��������-�˿�����<��������
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
   * ���෽����д
   * 
   * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
   */
  public void valueChanged(ListSelectionEvent e) {
    //���ñ�ͷѡ��
    int nSelected[] = getbillListPanel().getHeadTable().getSelectedRows();
    if (nSelected != null && nSelected.length > 0) {
      for (int i = 0; i < nSelected.length; i++) {
        getbillListPanel().getHeadBillModel().setRowState(nSelected[i],BillModel.SELECTED);
      }
    }
   //���ø������������ѡ��
    super.valueChanged(e);
    getbillListPanel().getChildListPanel().selectAllTableRow();
  }
}
