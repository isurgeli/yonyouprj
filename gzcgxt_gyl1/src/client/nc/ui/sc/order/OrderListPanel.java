package nc.ui.sc.order;

import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillTabbedPane;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.scm.inv.InvTool;
import nc.ui.scm.pu.SCMPuTool;
import nc.vo.pub.lang.UFDouble;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.sc.order.OrderHeaderVO;
import nc.vo.sc.order.OrderItemVO;
import nc.vo.sc.order.OrderVO;
import nc.vo.sc.pub.ScConstants;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.SCMEnv;

/**
 * ί�ⶩ����������������ʹ�ô��б���� �������ڣ�(01-6-7 8:50:59)
 * <p>
 * <b>�����ʷ��</b>
 * <p>
 * <hr>
 * <p>�޸����� 2008.8.21
 * <p>�޸��� zhaoyha
 * <p>�汾 5.5
 * <p>˵����
 * <ul>
 * <li>ѡ��ģʽ�����˵���
 * </ul>
 * 
 * @author��xjl
 */
public class OrderListPanel extends nc.ui.pub.bill.BillListPanel implements
		BillEditListener, ListSelectionListener {
	// gc�������tab����
	private String TAB1 = "table";
	private String TAB2 = "lby";
	private static final long serialVersionUID = 1L;

	private int ibillstatus;

	private OrderUI m_orderUI;

	private int iState = ScConstants.STATE_LIST;

	private ArrayList m_listComeVO = null;

	private nc.bs.bd.b21.CurrencyRateUtil m_CurrArith = null;

  // ǰ̨������ģ�ͣ�ί�ⶩ����������������ʹ��
  private OrderModel orderModel;
  
	/**
	 * ScAdjustListPanel ������ע�⡣
	 */
	public OrderListPanel() {
		super();
		init();
	}

	/**
	 * ScAdjustListPanel ������ע�⡣
	 */
	public OrderListPanel(OrderUI orderUI) {
		super(false);
		this.m_orderUI = orderUI;
		init();
	}

	public void afterEdit(BillEditEvent e) {
	}

	public void bodyRowChange(BillEditEvent e) {
		if (e.getRow() < 0) {
			return;
		}

		if (iState == ScConstants.STATE_LIST) {
			// ���ر���
			if (getHeadBillModel().getValueAt(e.getRow(), "corderid") != null) {
				setBodyModelDataCopy(e.getOldRow());
				loadBodyData(e.getRow());
				
				if (e.getRow() == 0 && getBodyBillModel().getRowCount() == 0)
					loadBodyData(0);
			}

			if (m_orderUI != null) {
				// ���ݿ�Ƭ�ϰ�ť״̬���޸ġ����ϣ�
				SCMEnv.out(e.getRow());
				String billstatus = getHeadBillModel().getValueAt(
						e.getRow(), "ibillstatus").toString();
				ibillstatus = new Integer(billstatus).intValue();
				m_orderUI.setOperateState(ibillstatus);
			}
		}
		
		else if (iState == ScConstants.STATE_OTHER) {
			if (m_listComeVO.size() > 0) {
        if (e.getRow() >= m_listComeVO.size())
          loadBodyData(0);
        else
          loadBodyData(e.getRow());
				m_orderUI.setOperateState(10);
			}
		}
	}

	/**
	 * ���㻻���� �������ڣ�(2001-9-17 17:30:05)
	 */
	private void calMeasRate(OrderItemVO[] orderItemVOs) {
		try {
			int rowcount = orderItemVOs.length;
			List<String> listBaseId = new ArrayList<String>();
			List<String> listAssitUnitId = new ArrayList<String>();
			for (int i = 0; i < rowcount; i++) {
				listBaseId.add(orderItemVOs[i].getCbaseid());
				listAssitUnitId.add(orderItemVOs[i].getCassistunit());
			}
			
			//װ��������
			InvTool.loadBatchInvConvRateInfo(
					listBaseId.toArray(new String[listBaseId.size()]), 
					listAssitUnitId.toArray(new String[listAssitUnitId.size()]));
			for (int i = 0; i < rowcount; i++) {
				// ���س���Ϊ2�����飺retValues[0]:������, retValues[1]:�Ƿ�̶�������
				Object[] retValues = InvTool.getInvConvRateInfo(
						listBaseId.get(i), listAssitUnitId.get(i));
				if ( (retValues == null) || (retValues.length < 2) ) {
					continue;
				}
				
				UFDouble measrate = (UFDouble)retValues[0];
				if (measrate == null || measrate.compareTo(new UFDouble(0)) == 0) {
					continue;
				}

				getBodyBillModel().setValueAt(measrate, i, "measrate");
				Object nordernum = getBodyBillModel().getValueAt(i, "nordernum");
				if (nordernum == null || nordernum.toString().trim().equals("")) {
					continue;	
				}

				UFDouble nassisnum = new UFDouble(nordernum.toString()).div(measrate);
				getBodyBillModel().setValueAt(nassisnum, i, "nassistnum");
			}
		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * ����ת��VO �������ڣ�(2001-9-5 15:28:14)
	 * 
	 * @return java.util.Vector
	 */
	public ArrayList getComeVO() {
		return m_listComeVO;
	}

	/**
	 * �˴����뷽��˵���� ��������: �������: ����ֵ: �쳣����:
	 * 
	 * @return nc.ui.bd.b21.CurrArith
	 */
	public nc.bs.bd.b21.CurrencyRateUtil getCurrArith() {
		if (m_CurrArith == null) {
			m_CurrArith = new nc.bs.bd.b21.CurrencyRateUtil(getPk_corp());
		}
		return m_CurrArith;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-30 12:19:49)
	 * 
	 * @return int
	 */
	public int getIbillStatus() {
		return ibillstatus;
	}

	/**
	 * �˴����뷽��˵���� ��������: �������: ����ֵ: �쳣����:
	 * 
	 * @return java.lang.String
	 */
	public String getPk_corp() {
		return nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-6-1 15:06:15)
	 */
	public void hideTableCol() {
		String[] hideCols = { "cpurorganization", "cwareid", "cvendorid",
				"cvendormangid", "caccountbankid", "cdeptid", "cemployeeid",
				"creciever", "cgiveinvoicevendor", "ctransmodeid",
				"ctermprotocolid", "coperator", "cbiztype", "cauditpsn" };
		for (int i = 0; i < hideCols.length; i++)
			hideHeadTableCol(hideCols[i]);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-5-12 11:21:34)
	 * <p>
	 * <b>�����ʷ��</b>
	 * <p>
	 * <hr>
	 * <p>�޸����� 2008.8.21
	 * <p>�޸��� zhaoyha
	 * <p>�汾 5.5
	 * <p>˵����
	 * <ul>
	 * <li>ʹ��UAP55ѡ��ģʽ�����˵���
	 * </ul>
	 */
	protected void init() {

		super.init();
		this.addEditListener(this);
		getHeadTable().getSelectionModel().addListSelectionListener(this);
		//V55������ѡ������
		SCMPuTool.setLineSelectedList(this);

	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-23 9:17:37)
	 */
	public void loadBodyData(int row) {
		String tab = getSelectTab();
		//gc ���õ�һҳΪѡ��ҳ��Ҫ��Ȼ�ĵ���̫��
				BillTabbedPane tp = getBodyTabbedPane();
				tp.setSelectedIndex(0);//.setTabVisible(0, true);
//		if (tab.equals(TAB1)) {
			try {
				OrderItemVO[] orderItemVO = null;

				// ����ת��״̬
				if (iState == ScConstants.STATE_OTHER) {
					orderItemVO = (OrderItemVO[]) ((OrderVO) m_listComeVO
							.get(row)).getChildrenVO();
				}
				// ��ͨ�б�״̬
				else {
					orderItemVO = (OrderItemVO[]) this.getModel()
							.getOrderAt(row).getChildrenVO();
				}

				// ������Ϊ���λ�������ͨ��cellrender�����ƾ��徫��
				getBodyItem("noriginalcurmny").setDecimalDigits(8);
				getBodyItem("noriginaltaxmny").setDecimalDigits(8);
				getBodyItem("noriginalsummny").setDecimalDigits(8);
				getBodyItem("nexchangeotobrate").setDecimalDigits(8);

//				setBodyValueVO(orderItemVO);
				setBodyValueVO(TAB1, orderItemVO);

				// ��˰���
				String sRaxType = null;
				for (int i = 0; i < orderItemVO.length; i++) {
					Integer idiscounttaxtype = orderItemVO[i]
							.getIdiscounttaxtype();

					if (idiscounttaxtype.intValue() == 0)
						sRaxType = ScConstants.TaxType_Including;// 0 Ӧ˰�ں�
					else if (idiscounttaxtype.intValue() == 1)
						sRaxType = ScConstants.TaxType_Not_Including;// 1 Ӧ˰���
					else if (idiscounttaxtype.intValue() == 2)
						sRaxType = ScConstants.TaxType_No;// 2 ����˰
					else
						sRaxType = "";

					getBodyBillModel().setValueAt(sRaxType, i,
							"idiscounttaxtype");
				}

				// ����/����ͨ���������þ�������֤���ȣ�ȱһ���ɣ��мǡ�
				nc.ui.sc.pub.tool.Precision p = new nc.ui.sc.pub.tool.Precision(
						this.getBodyBillModel(), this.getBodyTable());
				p.setBusinessPrecision("ccurrencytypeid", new String[] {
						"nmoney", "ntaxmny", "nsummny" },
						new String[] { "noriginalcurmny", "noriginaltaxmny",
								"noriginalsummny" });

				// ���û��ʾ���
				p.setExchangeRatePercision(nc.ui.pub.ClientEnvironment
						.getInstance().getCorporation().getPrimaryKey(),
						"ccurrencytypeid", "nexchangeotobrate");

				// ����������
				loadFreeItems(orderItemVO);

				// �������̶�����������
				nc.ui.sc.pub.ScTool.setListConvertRate(this, new String[] {
						"cbaseid", "cassistunit", "nordernum", "nassistnum",
						"measrate" });

				getBodyBillModel(TAB1).execLoadFormula();

				if (iState == ScConstants.STATE_OTHER) {
					calMeasRate(orderItemVO);
				}

				nc.ui.scm.sourcebill.SourceBillTool.loadSourceBillData(this,
						"61");
				nc.ui.scm.sourcebill.SourceBillTool.loadAncestorBillData(this,
						"61");
			} catch (Exception e) {
				SCMEnv.out("�б�������ݼ���ʧ��");
				SCMEnv.out(e);
			}
//		} else {
			try {
				tp.setSelectedIndex(1);
				OrderDdlbVO[] ddlbvos = null;
				// ����ת��״̬
				if (iState == ScConstants.STATE_OTHER) {
					ddlbvos = (OrderDdlbVO[]) ((OrderVO) m_listComeVO.get(row))
							.getDdlbvos();
				}
				// ��ͨ�б�״̬
				else {
					ddlbvos = (OrderDdlbVO[]) this.getModel().getOrderAt(row)
							.getDdlbvos();
				}

				// ������Ϊ���λ�������ͨ��cellrender�����ƾ��徫��
				// getBodyItem("noriginalcurmny").setDecimalDigits(8);
				// getBodyItem("noriginaltaxmny").setDecimalDigits(8);
				// getBodyItem("noriginalsummny").setDecimalDigits(8);
				// getBodyItem("nexchangeotobrate").setDecimalDigits(8);

				setBodyValueVO(TAB2, ddlbvos);

				// // ��˰���
				// String sRaxType = null;
				// for (int i = 0; i < orderItemVO.length; i++) {
				// Integer idiscounttaxtype =
				// orderItemVO[i].getIdiscounttaxtype();
				//
				// if (idiscounttaxtype.intValue() == 0)
				// sRaxType = ScConstants.TaxType_Including;//0 Ӧ˰�ں�
				// else if (idiscounttaxtype.intValue() == 1)
				// sRaxType = ScConstants.TaxType_Not_Including;//1 Ӧ˰���
				// else if (idiscounttaxtype.intValue() == 2)
				// sRaxType = ScConstants.TaxType_No;//2 ����˰
				// else
				// sRaxType = "";
				//
				// getBodyBillModel().setValueAt(sRaxType, i,
				// "idiscounttaxtype");
				// }

				// // ����/����ͨ���������þ�������֤���ȣ�ȱһ���ɣ��мǡ�
				// nc.ui.sc.pub.tool.Precision p = new
				// nc.ui.sc.pub.tool.Precision(this
				// .getBodyBillModel(), this.getBodyTable());
				// p.setBusinessPrecision("ccurrencytypeid", new String[] {
				// "nmoney", "ntaxmny", "nsummny"}, new String[] {
				// "noriginalcurmny", "noriginaltaxmny", "noriginalsummny"});
				//
				// // ���û��ʾ���
				// p.setExchangeRatePercision(nc.ui.pub.ClientEnvironment.getInstance()
				// .getCorporation().getPrimaryKey(), "ccurrencytypeid",
				// "nexchangeotobrate");

				// ����������
				loadFreeItems(ddlbvos);

				// �������̶�����������
				nc.ui.sc.pub.ScTool.setListConvertRate(this, new String[] {
						"cbaseid", "cassistunit", "nordernum", "nassistnum",
						"measrate" });

				getBodyBillModel(TAB2).execLoadFormula();

				// if (iState == ScConstants.STATE_OTHER) {
				// calMeasRate(ddlbvos);
				// }

				// nc.ui.scm.sourcebill.SourceBillTool.loadSourceBillData(this,
				// "61");
				// nc.ui.scm.sourcebill.SourceBillTool.loadAncestorBillData(this,
				// "61");
				if(tab.endsWith(TAB1))
					tp.setSelectedIndex(0);
			} catch (Exception e) {
				SCMEnv.out("�б�������ݼ���ʧ��");
				SCMEnv.out(e);
			}
//		}

	}

	private String getSelectTab() {
		// TODO Auto-generated method stub
		return getBodyTabbedPane().getSelectedTableCode();
	}

	/**
	 * ��������� �������ڣ�(2001-9-28 11:09:44)
	 */
	private void loadFreeItems(OrderItemVO[] orderItemVOs) {
		if (orderItemVOs == null || orderItemVOs.length == 0)
			return;
		// ����������
		try {
			int num = orderItemVOs.length;
			java.util.ArrayList<String> listMangId = new java.util.ArrayList<String>();
			for (int i = 0; i < num; i++) {
				listMangId.add(orderItemVOs[i].getCmangid());
			}

			if (listMangId == null || listMangId.size() <= 0)
				return;

			//��Դ�ں�ͬʱ���������id����Ϊ��
			if(PuPubVO.getString_TrimZeroLenAsNull(listMangId.get(0))==null)
				return;
			
			//װ��������
			InvTool.loadBatchFreeVO(
					(String[])listMangId.toArray(new String[listMangId.size()]));

			nc.vo.scm.ic.bill.InvVO invVO = new nc.vo.scm.ic.bill.InvVO();
			nc.vo.scm.ic.bill.FreeVO freeVO = new nc.vo.scm.ic.bill.FreeVO();
			for (int i = 0; i < num; i++) {
				freeVO = InvTool.getInvFreeVO(listMangId.get(i).toString());
				if (freeVO != null) {
					freeVO.setVfree1(orderItemVOs[i].getVfree1());
					freeVO.setVfree2(orderItemVOs[i].getVfree2());
					freeVO.setVfree3(orderItemVOs[i].getVfree3());
					freeVO.setVfree4(orderItemVOs[i].getVfree4());
					freeVO.setVfree5(orderItemVOs[i].getVfree5());
					//
					Object pk_invbasedoc = orderItemVOs[i].getCbaseid();
					Object pk_invmangdoc = orderItemVOs[i].getCmangid();
					Object cinventorycode = getBodyBillModel().getValueAt(i,
							"cinventorycode");
					Object cinventoryname = getBodyBillModel().getValueAt(i,
							"cinventoryname");
					Object invspec = getBodyBillModel()
							.getValueAt(i, "invspec");
					Object invtype = getBodyBillModel()
							.getValueAt(i, "invtype");

					invVO = new nc.vo.scm.ic.bill.InvVO();
					invVO.setFreeItemVO(freeVO);
					invVO.setCinvmanid(pk_invmangdoc.toString());
					invVO.setCinventoryid(pk_invbasedoc.toString());
					invVO.setIsFreeItemMgt(new Integer(1));
					invVO.setCinventorycode(cinventorycode == null ? ""
							: cinventorycode.toString());
					invVO.setInvname(cinventoryname == null ? null
							: cinventoryname.toString());
					invVO.setInvspec(invspec == null ? null : invspec
							.toString());
					invVO.setInvtype(invtype == null ? null : invtype
							.toString());

					getBodyBillModel().setValueAt(freeVO.getVfree0(), i,
							"vfree0");
					getBodyBillModel().setValueAt(invVO, i, "invvo");

				}
			}
		} catch (Exception e) {
			nc.ui.pub.beans.MessageDialog.showErrorDlg(this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC000-0003327")/* @res "������" */, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000022")/* @res "����������ʧ��" */);
			SCMEnv.out(e);
		}
	}
	/**
	 * ��������� �������ڣ�(2001-9-28 11:09:44)
	 */
	private void loadFreeItems(OrderDdlbVO[] orderItemVOs) {
		if (orderItemVOs == null || orderItemVOs.length == 0)
			return;
		// ����������
		try {
			int num = orderItemVOs.length;
			java.util.ArrayList<String> listMangId = new java.util.ArrayList<String>();
			for (int i = 0; i < num; i++) {
				listMangId.add(orderItemVOs[i].getCmangid());
			}

			if (listMangId == null || listMangId.size() <= 0)
				return;

			//��Դ�ں�ͬʱ���������id����Ϊ��
			if(PuPubVO.getString_TrimZeroLenAsNull(listMangId.get(0))==null)
				return;
			
			//װ��������
			InvTool.loadBatchFreeVO(
					(String[])listMangId.toArray(new String[listMangId.size()]));

			nc.vo.scm.ic.bill.InvVO invVO = new nc.vo.scm.ic.bill.InvVO();
			nc.vo.scm.ic.bill.FreeVO freeVO = new nc.vo.scm.ic.bill.FreeVO();
			for (int i = 0; i < num; i++) {
				freeVO = InvTool.getInvFreeVO(listMangId.get(i).toString());
				if (freeVO != null) {
					freeVO.setVfree1(orderItemVOs[i].getVfree1());
					freeVO.setVfree2(orderItemVOs[i].getVfree2());
					freeVO.setVfree3(orderItemVOs[i].getVfree3());
					freeVO.setVfree4(orderItemVOs[i].getVfree4());
					freeVO.setVfree5(orderItemVOs[i].getVfree5());
					//
					Object pk_invbasedoc = orderItemVOs[i].getCbaseid();
					Object pk_invmangdoc = orderItemVOs[i].getCmangid();
					Object cinventorycode = getBodyBillModel().getValueAt(i,
							"cinventorycode");
					Object cinventoryname = getBodyBillModel().getValueAt(i,
							"cinventoryname");
					Object invspec = getBodyBillModel()
							.getValueAt(i, "invspec");
					Object invtype = getBodyBillModel()
							.getValueAt(i, "invtype");

					invVO = new nc.vo.scm.ic.bill.InvVO();
					invVO.setFreeItemVO(freeVO);
					invVO.setCinvmanid(pk_invmangdoc.toString());
					invVO.setCinventoryid(pk_invbasedoc.toString());
					invVO.setIsFreeItemMgt(new Integer(1));
					invVO.setCinventorycode(cinventorycode == null ? ""
							: cinventorycode.toString());
					invVO.setInvname(cinventoryname == null ? null
							: cinventoryname.toString());
					invVO.setInvspec(invspec == null ? null : invspec
							.toString());
					invVO.setInvtype(invtype == null ? null : invtype
							.toString());

					getBodyBillModel().setValueAt(freeVO.getVfree0(), i,
							"vfree0");
					getBodyBillModel().setValueAt(invVO, i, "invvo");

				}
			}
		} catch (Exception e) {
			nc.ui.pub.beans.MessageDialog.showErrorDlg(this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC000-0003327")/* @res "������" */, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000022")/* @res "����������ʧ��" */);
			SCMEnv.out(e);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-23 9:17:37)
	 */
	public void loadHeadData() {
		// ����ת��״̬
		if (iState == ScConstants.STATE_OTHER) {
			if (m_listComeVO == null)
				return;
			try {
				int num = m_listComeVO.size();

				OrderHeaderVO[] orderHeaderVO = new OrderHeaderVO[num];
				for (int i = 0; i < num; i++) {
					orderHeaderVO[i] = (OrderHeaderVO) ((OrderVO) m_listComeVO
							.get(i)).getParentVO();
				}

				setHeaderValueVO(orderHeaderVO);
				getHeadBillModel().execLoadFormula();
				
				if ( m_listComeVO.size() > 0 ) { 
					getHeadTable().setRowSelectionInterval(0, 0);
				}
				
			} catch (Exception t) {
				SCMEnv.out(t.getMessage());
			}
		}
		else {
			// ��ͨ�б�״̬
			try {
				OrderHeaderVO[] orderHeaderVO = OrderHelper.queryAllHead("");

				setHeaderValueVO(orderHeaderVO);
				getHeadBillModel().execLoadFormula();

			} catch (Exception e) {
				SCMEnv.out("�б��ͷ���ݼ���ʧ��");
				SCMEnv.out(e);
			}
		}
		
		SCMEnv.out("�б��ͷ���ݼ��سɹ�");
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-9-5 15:27:41)
	 * 
	 * @param vec
	 *            java.util.Vector
	 */
	public void setComeVO(ArrayList vec) {
		m_listComeVO = vec;
	}

	/**
	 * �˴����뷽��˵���� ��������:���þ��ȣ����������ۡ��� �������: ����ֵ: �쳣����:
	 */
	public void setNormalPrecision() {
		try {
			int numPrecision = 2;
			int assnumPrecision = 2;
			int ratePrecision = 2;
			int pricePrecision = 2;

			// ����
			try {
				numPrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD501").intValue();
			} catch (Exception e) {
				numPrecision = 2;
			}
			// ������
			try {
				assnumPrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD502").intValue();
			} catch (Exception e) {
				assnumPrecision = 2;
			}
			// ������
			try {
				ratePrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD503").intValue();
			} catch (Exception e) {
				ratePrecision = 2;
			}
			// ����
			try {
				pricePrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD505").intValue();
			} catch (Exception e) {
				pricePrecision = 2;
			}

			//������
			getBodyItem("nordernum").setDecimalDigits(numPrecision);
			getBodyItem("nbackarrvnum").setDecimalDigits(numPrecision);//�˻�����
			getBodyItem("nbackstorenum").setDecimalDigits(numPrecision);//�˿�����
			//������
			getBodyItem("nassistnum").setDecimalDigits(assnumPrecision);
			//������
			getBodyItem("measrate").setDecimalDigits(ratePrecision);
			//����:
			getBodyItem("noriginalcurprice").setDecimalDigits(pricePrecision);
			getBodyItem("noriginalnetprice").setDecimalDigits(pricePrecision);
			getBodyItem("norgtaxprice").setDecimalDigits(pricePrecision);
			getBodyItem("norgnettaxprice").setDecimalDigits(pricePrecision);

		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * �˴����뷽��˵����
	 * �������ڣ�(2001-9-5 10:21:12)
	 * @param state int
	 */
	public void setState(int state) {
		iState = state;
	}

	public void valueChanged(ListSelectionEvent e) {
		int iCount = getHeadTable().getRowCount();
		for (int i = 0; i < iCount; i++) {
			getHeadBillModel().setRowState(i, BillModel.UNSTATE);
		}

		//�õ���ѡ�е���
		int[] selectRows = getHeadTable().getSelectedRows();
		if (selectRows != null) {
			iCount = selectRows.length;

			if ( (iCount >= 1) && 
				 (m_orderUI != null) && 
				 (iState != ScConstants.STATE_OTHER) ) {
				// ѡ�еĵ�һ��Ϊ��ǰ��
				this.getModel().setCurrentIndex( selectRows[0] );
			}

			// ѡ�е��б�ʾΪ�򣪺�
			for (int i = 0; i < iCount; i++) {
				if (getHeadBillModel().getRowAttribute(selectRows[i]) != null)
					getHeadBillModel().setRowState(selectRows[i],
							BillModel.SELECTED);
			}
		}

	}
  
  public OrderModel getModel() {
    if (orderModel == null) {
      orderModel = new OrderModel();
    }
    return orderModel;
  }
}