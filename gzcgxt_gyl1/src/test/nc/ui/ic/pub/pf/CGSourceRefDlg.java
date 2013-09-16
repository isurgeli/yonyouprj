/**
 * $�ļ�˵��$
 *
 * @author yangb
 * @version 
 * @see
 * @since
 * @time 2008-8-1 ����01:01:16
 */
package nc.ui.ic.pub.pf;

import java.awt.Container;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.sc.IOrder;
import nc.itf.sc.order.ISCForCGOrderService;
import nc.itf.uif.pub.IUifService;
import nc.ui.ml.NCLangRes;
import nc.ui.pf.change.PfUtilUITools;
import nc.ui.pf.changedir.CHG61CGTO4F;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.util.NCOptionPane;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.pf.BillSourceDLG;
import nc.vo.pf.change.IchangeVO;
import nc.vo.pf.change.PfUtilBaseTools;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.sc.order.OrderForRef4FVO;
import nc.vo.sc.order.OrderHeaderVO;
import nc.vo.sc.order.OrderVO;
import nc.vo.scm.pub.session.ClientLink;

public class CGSourceRefDlg extends BillSourceDLG {

	private UIButton ivjbtnMYCancel = null;

	private UIButton ivjbtnMYOk = null;

	private UIButton ivjbtnMYQuery = null;

	private UIButton ivjbtnAll = null;
	private UIButton ivjbtnNone = null;
	private UIButton ivjbtnJoin = null;
	private UIButton ivjbtnPrint = null;
	private UIButton ivjbtnXcl = null;// �ִ���
	private UIPanel ivjPanlMYCmd = null;
	private JPanel ivjUIDialogContentPane = null;

	private boolean isneedAllSelect = true;

	private boolean isneedLinkQuery = false;

	protected ICSplitModeDlg ivjSplitDlg;
	// ���صļ���Vo
	protected AggregatedValueObject retSrcBillVo;
	// ���ؼ���VO����
	protected AggregatedValueObject[] retSrcBillVos;

	public CGSourceRefDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
				businessType, templateId, currentBillType, parent);
		// TODO �Զ����ɹ��캯�����
		addListener();
		addListenerEvent();
		// getbillListPanel().setmsetMultiSelect(false);
		getbillListPanel().setMultiSelect(false);
		getbillListPanel().getChildListPanel().setTotalRowShow(false);
		getbillListPanel().repaint();
		// ��ѡ����
		getbillListPanel().getBodyTable().setSelectionMode(
				javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		// getbillListPanel().getBodyTable().getSelectionModel().addListSelectionListener(new
		// ListSelectionListener(){
		//
		// public void valueChanged(ListSelectionEvent e)
		// {
		// if (!e.getValueIsAdjusting()) {
		// int bodyRow = ((ListSelectionModel)
		// e.getSource()).getAnchorSelectionIndex();
		// // if (headRow >= 0) {
		// // headRowChange(headRow);
		// // }
		// }
		//
		// }
		//
		// }) ;
		// getbillListPanel().setEnabled(true);
		// Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
		// setSize((new UFDouble(scrSize.width)).intValue(),
		// (new UFDouble(scrSize.height)).intValue());
	}

	public CGSourceRefDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			String nodeKey, Object userObj, Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
				businessType, templateId, currentBillType, nodeKey, userObj,
				parent);
	}

	protected void addListener() {
		getbillListPanel().getHeadTable().getSelectionModel()
				.addListSelectionListener(this);
	}

	@Override
	public String getBodyCondition() {
		// TODO Auto-generated method stub
		return super.getBodyCondition();
	}

	@Override
	public AggregatedValueObject getRetVo() {
		// TODO Auto-generated method stub
		return super.getRetVo();
	}

	@Override
	public AggregatedValueObject[] getRetVos() {
		// TODO Auto-generated method stub
		return super.getRetVos();
	}

	@Override
	public void loadBodyData(int row) {
		try {
			// �������ID
			String id = getbillListPanel().getHeadBillModel()
					.getValueAt(row, getpkField()).toString();
			// ��ѯ�ӱ�VO����
			IUifService bo = NCLocator.getInstance().lookup(IUifService.class);
			OrderDdlbVO[] tmpBodyVo = (OrderDdlbVO[]) bo.queryByCondition(
					OrderDdlbVO.class, "corderid='" + id + "'");
			if (getbillListPanel().getBillListData().isMeataDataTemplate())
				getbillListPanel().getBillListData()
						.setBodyValueObjectByMetaData(tmpBodyVo);
			else
				getbillListPanel().setBodyValueVO(tmpBodyVo);
			getbillListPanel().getBodyBillModel().execLoadFormula();
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	}

	@Override
	public void loadHeadData() {
		try {
			// ���ò�Ʒ�鴫��������뵱ǰ��ѯ�������������������ѯ����
			String tmpWhere = "";
			if (getHeadCondition() != null) {
				if (m_whereStr == null) {
					tmpWhere = "  (" + getHeadCondition() + ")";
				} else {
					tmpWhere = "  (" + m_whereStr + ") and ("
							+ getHeadCondition() + ")";
				}
			} else {
				tmpWhere = m_whereStr == null ? "" : m_whereStr;
			}
			String businessType = null;
			if (getIsBusinessType()) {
				businessType = getBusinessType();
			}
//			ISCForCGOrderService service = NCLocator.getInstance().lookup(
//					ISCForCGOrderService.class);
//			OrderHeaderVO[] headvos = service.queryRefHead(tmpWhere);
			IOrder bo = (IOrder) nc.bs.framework.common.NCLocator.getInstance()
					.lookup(IOrder.class.getName());
			OrderHeaderVO[] headvos = bo.queryAllHeadForOrder("",
					tmpWhere,
					new ClientLink(nc.ui.pub.ClientEnvironment.getInstance()),
					true);
			List<OrderHeaderVO> lt_h = new ArrayList<OrderHeaderVO>();
			List<OrderVO> lt_bill = new ArrayList<OrderVO>();
			// if(billvos != null && billvos.length > 0){
			// for (int i = 0; i < billvos.length; i++) {
			// lt_h.add((OrderHeaderVO) billvos[i].getParentVO());
			// OrderDdlbVO []itemvos = billvos[i].getDdlbvos();
			// List<OrderDdlbVO> lt_b = new ArrayList<OrderDdlbVO>();
			// for (int j = 0; j < itemvos.length; j++) {
			// UFDouble accsend = itemvos[i].getNaccumsendnum() == null ?
			// UFDouble.ZERO_DBL : itemvos[i].getNaccumsendnum();
			// if(itemvos[i].getNnum().sub(accsend).doubleValue() > 0){
			// if(!lt_h.contains((OrderHeaderVO) billvos[i].getParentVO())){
			// lt_h.add((OrderHeaderVO) billvos[i].getParentVO());
			// }
			// lt_b.add(itemvos[i]);
			// }else{
			// }
			// }
			// if(lt_b.size() > 0){
			// OrderVO vo = new OrderVO();
			// vo.setParentVO(billvos[i].getParentVO());
			// vo.setChildrenVO(lt_b.toArray(new OrderDdlbVO[0]));
			// lt_bill.add(vo);
			// map_item.put(billvos[i].getParentVO().getPrimaryKey(),
			// lt_b.toArray(new OrderDdlbVO[0]));
			// }
			// }
			// }
			// headvos = lt_h.toArray(new OrderHeaderVO[0]);
			// billvo = lt_bill.toArray(new OrderVO[0]);

			if (getbillListPanel().getBillListData().isMeataDataTemplate())
				getbillListPanel().getBillListData()
						.setHeaderValueObjectByMetaData(headvos);
			else
				getbillListPanel().setHeaderValueVO(headvos);
			getbillListPanel().getHeadBillModel().execLoadFormula();

		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
			MessageDialog.showErrorDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("pfworkflow",
							"UPPpfworkflow-000237")/* @res "����" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("pfworkflow",
							"UPPpfworkflow-000490")/* @res "���ݼ���ʧ�ܣ�" */);
		}
	}

	@Override
	public String getBillType() {
		// TODO Auto-generated method stub
		return "61";
	}

	@Override
	public String getBusinessType() {
		// TODO Auto-generated method stub
		return super.getBusinessType();
	}

	public boolean isIsneedLinkQuery() {
		return isneedLinkQuery;
	}

	public void setIsneedLinkQuery(boolean isneedLinkQuery) {
		this.isneedLinkQuery = isneedLinkQuery;
	}

	@Override
	public String getHeadCondition() {
		return " sc_order.pk_corp='"
				+ ClientEnvironment.getInstance().getCorporation().getPrimaryKey()
				+ "' "
				+ " and sc_order.ibillstatus=3"
				+ " and sc_order.corderid in ( select corderid from sc_order_ddlb "
				+ "where (nvl(sc_order_ddlb.nnum,0)-nvl(sc_order_ddlb.naccumsendnum,0) >0 and isnull(sc_order_ddlb.dr,0)=0))";
	}

	public void onAll() {
		int maxindex = getbillListPanel().getHeadBillModel().getRowCount();
		if (maxindex > 0)
			getbillListPanel().getHeadTable().setRowSelectionInterval(0,
					maxindex - 1);
		for (int i = maxindex - 1; i >= 0; i--) {
			setRowState(i, BillModel.SELECTED);
		}
	}

	private void setRowState(int headRow, int state) {
		// ���л�
		BillModel bodyModel = getbillListPanel().getBodyBillModel();
		BillModel headModel = getbillListPanel().getHeadBillModel();
		headModel.setRowState(headRow, state);
		loadBodyData(headRow);
		getbillListPanel().getBodyBillModel().execLoadFormula();
		for (int i = 0; i < bodyModel.getRowCount(); i++)
			bodyModel.setRowState(i, headModel.getRowState(headRow));
		// ��������
		getbillListPanel().setBodyModelDataCopy(headRow);
	}

	public void onNone() {
		int maxindex = getbillListPanel().getHeadBillModel().getRowCount();
		if (maxindex > 0)
			getbillListPanel().getHeadTable().clearSelection();
		// ȫѡ����
		for (int i = 0; i < maxindex; i++) {
			setRowState(i, BillModel.UNSTATE);
		}
	}

	/**
	 * "ȷ��"��ť����Ӧ���ӽ����ȡ��ѡ����VO
	 */
	public void onOk() {


		try {
			retBillVos = null;
			retBillVo = null;
			retSrcBillVos = null;
			retSrcBillVo = null;

			AggregatedValueObject[] selectedBillVOs = getbillListPanel()
					.getMultiSelectedVOs(m_billVo, m_billHeadVo, m_billBodyVo);
			retBillVo = selectedBillVOs.length > 0 ? selectedBillVOs[0] : null;
			retBillVos = selectedBillVOs;
			// retBillVos =
			// getSelectedSourceVOs();//getbillListPanel().getSelectedSourceVOs();
			retSrcBillVos = retBillVos;

			// if(Bill53Const.cbilltype.equals(getCurrentBillType()))
//			retBillVos = PfChangeBO_Client.pfChangeBillToBillArray(retBillVos,
//					"61CG", getCurrentBillType());
			CHG61CGTO4F tool = new CHG61CGTO4F();
			AggregatedValueObject[] destVo = PfUtilUITools.pfInitVosClass("4F", retBillVos);
			retSrcBillVos = ((IchangeVO) tool).retChangeBusiVOs(retBillVos,destVo);
			// else
			// retBillVos =
			// ICDefaultSrcRefCtl.getTargetVOsForUIRef(retBillVos,getBillType()
			// , getCurrentBillType(),
			// ((BillRefListPanel)getbillListPanel()).getHeadBillModel().getFormulaParse(),
			// getICSplitModeDlg().getIC035());

		} catch (BusinessException e) {
			showErroMessage(e.getMessage());
			return;
		}

		if (retBillVos == null || retBillVos.length <= 0) {
			showErroMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000199")/* @res "��ѡ�񵥾�" */);
			return;
		}

		retBillVo = retBillVos[0];

		retSrcBillVo = retSrcBillVos[0];
		
		retBillVos = retSrcBillVos;
		
		this.getAlignmentX();
		this.closeOK();
	}

	/**
	 * get�� �������ڣ�(2001-5-9 8:52:00)
	 * 
	 * @param row
	 *            int
	 */

	public ICSplitModeDlg getICSplitModeDlg() {
		if (ivjSplitDlg == null) {
			ivjSplitDlg = new ICSplitModeDlg(this);
		}
		return ivjSplitDlg;
	}

	private nc.ui.pub.beans.UIButton getbtnAll() {
		if (ivjbtnAll == null) {
			ivjbtnAll = new nc.ui.pub.beans.UIButton();
			ivjbtnAll.setName("ivjbtnAll");
			ivjbtnAll.setText("ȫѡ");
		}
		return ivjbtnAll;
	}

	private nc.ui.pub.beans.UIButton getbtnNone() {
		if (ivjbtnNone == null) {
			ivjbtnNone = new nc.ui.pub.beans.UIButton();
			ivjbtnNone.setName("ivjbtnNone");
			ivjbtnNone.setText("ȫ��");
		}
		return ivjbtnNone;
	}

	private nc.ui.pub.beans.UIButton getbtnJoin() {
		if (ivjbtnJoin == null) {
			ivjbtnJoin = new nc.ui.pub.beans.UIButton();
			ivjbtnJoin.setName("ivjbtnJoin");
			ivjbtnJoin.setText("����");
		}
		return ivjbtnJoin;
	}

	private nc.ui.pub.beans.UIButton getbtnPrint() {
		if (ivjbtnPrint == null) {
			ivjbtnPrint = new nc.ui.pub.beans.UIButton();
			ivjbtnPrint.setName("ivjbtnPrint");
			ivjbtnPrint.setText("��ӡ");
		}
		return ivjbtnPrint;
	}

	private nc.ui.pub.beans.UIButton getbtnMYCancel() {
		if (ivjbtnMYCancel == null) {

			ivjbtnMYCancel = new nc.ui.pub.beans.UIButton();
			ivjbtnMYCancel.setName("btnMYCancel");
			ivjbtnMYCancel.setText(NCLangRes.getInstance().getStrByID("common",
					"UC001-0000008")/* @res "ȡ��" */);
		}
		return ivjbtnMYCancel;
	}

	private nc.ui.pub.beans.UIButton getbtnOk() {
		if (ivjbtnMYOk == null) {
			ivjbtnMYOk = new nc.ui.pub.beans.UIButton();
			ivjbtnMYOk.setName("btnOk");
			// ivjbtnOk.setText(NCLangRes.getInstance().getStrByID("common",
			// "UC001-0000044")/*@res "ȷ��"*/);
			ivjbtnMYOk.setText("ȷ��");
		}
		return ivjbtnMYOk;
	}

	private nc.ui.pub.beans.UIButton getbtnMYQuery() {
		if (ivjbtnMYQuery == null) {

			ivjbtnMYQuery = new nc.ui.pub.beans.UIButton();
			ivjbtnMYQuery.setName("btnMYQuery");
			ivjbtnMYQuery.setText(NCLangRes.getInstance().getStrByID("common",
					"UC001-0000006")/* @res "��ѯ" */);
		}
		return ivjbtnMYQuery;
	}

	private nc.ui.pub.beans.UIButton getbtnXcl() {
		if (ivjbtnXcl == null) {
			ivjbtnXcl = new nc.ui.pub.beans.UIButton();
			ivjbtnXcl.setName("ivjbtnXcl");
			ivjbtnXcl.setText("�ִ���");
		}
		return ivjbtnXcl;
	}

	private nc.ui.pub.beans.UIPanel getPanlMYCmd() {
		if (ivjPanlMYCmd == null) {
			ivjPanlMYCmd = new nc.ui.pub.beans.UIPanel();
			ivjPanlMYCmd.setName("PanlMYCmd");
			ivjPanlMYCmd.setPreferredSize(new java.awt.Dimension(0, 40));
			ivjPanlMYCmd.setLayout(new java.awt.FlowLayout());
			ivjPanlMYCmd.add(getbtnMYQuery(), getbtnMYQuery().getName());
			if (isIsneedAllSelect()) {
				ivjPanlMYCmd.add(getbtnAll(), getbtnAll().getName());
				ivjPanlMYCmd.add(getbtnNone(), getbtnNone().getName());
			}
			if (isIsneedLinkQuery()) {
				ivjPanlMYCmd.add(getbtnJoin(), getbtnJoin().getName());
			}
			// ivjPanlMYCmd.add(getbtnPrint(), getbtnPrint().getName());
			ivjPanlMYCmd.add(getbtnOk(), getbtnOk().getName());
			ivjPanlMYCmd.add(getbtnMYCancel(), getbtnMYCancel().getName());
			if (this.isShowXclBtn()) {
				ivjPanlMYCmd.add(getbtnXcl(), getbtnXcl().getName());
			}
		}
		return ivjPanlMYCmd;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == getbtnOk()) {
			onOk();
		} else if (e.getSource() == getbtnMYCancel()) {
			this.closeCancel();
		} else if (e.getSource() == getbtnMYQuery()) {
			onQuery();
		} else if (e.getSource() == getbtnAll()) {
			onAll();
		} else if (e.getSource() == getbtnNone()) {
			onNone();
		} else if (e.getSource() == getbtnJoin()) {
			onJoin();
		} else if (e.getSource() == getbtnPrint()) {

		} else if (e.getSource() == getbtnXcl()) {
			onShowXcl();
		} else
			super.actionPerformed(e);
	}

	@Override
	protected BillListPanel getbillListPanel() {
		if (ivjbillListPanel == null) {
			try {
				ivjbillListPanel = new BillListPanel();
				ivjbillListPanel.setName("billListPanel");
				// �����ʾλ��ֵ
				// װ��ģ��
				nc.vo.pub.bill.BillTempletVO vo = ivjbillListPanel
						.getTempletData("1002AA1000000000P54J");

				BillListData billDataVo = new BillListData(vo);

				// �����������ʾλ��
				String[][] tmpAry = getHeadShowNum();
				if (tmpAry != null) {
					setVoDecimalDigitsHead(billDataVo, tmpAry);
				}
				// �����ӱ����ʾλ��
				tmpAry = getBodyShowNum();
				if (tmpAry != null) {
					setVoDecimalDigitsBody(billDataVo, tmpAry);
				}

				ivjbillListPanel.setListData(billDataVo);

				// �������������е��ж�
				if (getHeadHideCol() != null) {
					for (int i = 0; i < getHeadHideCol().length; i++) {
						ivjbillListPanel.hideHeadTableCol(getHeadHideCol()[i]);
					}
				}
				if (getBodyHideCol() != null) {
					for (int i = 0; i < getBodyHideCol().length; i++) {
						ivjbillListPanel.hideBodyTableCol(getBodyHideCol()[i]);
					}
				}

				ivjbillListPanel.setMultiSelect(true);
				ivjbillListPanel.getChildListPanel().setTotalRowShow(true);
			} catch (java.lang.Throwable e) {
				Logger.error(e.getMessage(), e);
			}
		}
		return ivjbillListPanel;
	}

	public boolean isShowXclBtn() {//
		return false;
	}

	public void onShowXcl() {
		// �˷���Ҫ��д��
	}

	public void onJoin() {
		int[] rows = getbillListPanel().getHeadTable().getSelectedRows();
		if (rows == null | rows.length == 0) {
			NCOptionPane.showMessageDialog(this, "����ѡ�����鵥�ݣ�");
			return;
		}
		String id = getbillListPanel().getHeadBillModel()
				.getValueAt(rows[rows.length - 1], getpkField()).toString();

		nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(
				this, getBillType(), id, null, getOperator(), getPkCorp());
		soureDlg.showModal();
	}

	public void addListenerEvent() {
		getbillListPanel().addEditListener(this);
		getbillListPanel().addMouseListener(this);

		// ��ͷ�б� ���л��¼�������
		getbillListPanel().getParentListPanel().getTable().getSelectionModel()
				.addListSelectionListener(this);

		getbtnOk().addActionListener(this);
		getbtnMYCancel().addActionListener(this);
		getbtnMYQuery().addActionListener(this);
		getbtnAll().addActionListener(this);
		getbtnNone().addActionListener(this);
		getbtnJoin().addActionListener(this);
		getbtnPrint().addActionListener(this);
		getbtnXcl().addActionListener(this);
		getbillListPanel().getHeadTable().setSelectionMode(
				javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	}

	protected javax.swing.JPanel getUIDialogContentPane() {
		if (ivjUIDialogContentPane == null) {
			ivjUIDialogContentPane = super.getUIDialogContentPane();

			ivjUIDialogContentPane.add(getPanlMYCmd(), "South");
		}
		return ivjUIDialogContentPane;
	}

	public void bodyRowChange(BillEditEvent e) {

		int row = this.getbillListPanel().getHeadTable().getSelectedRow();
		int rowstate = this.getbillListPanel().getHeadBillModel()
				.getRowState(row);
		// ȡ��״̬
		if (rowstate == -1) {
			setRowState(row, BillModel.UNSTATE);
		}
		// ѡ��״̬
		else if (rowstate == 4) {
			setRowState(row, BillModel.SELECTED);
		}
	}

	public boolean isIsneedAllSelect() {
		return isneedAllSelect;
	}

	public void setIsneedAllSelect(boolean isneedAllSelect) {
		this.isneedAllSelect = isneedAllSelect;
	}

	@Override
	public void getBillVO() {
		try {
			String[] retString = PfUtilBaseTools.getStrBillVo(getBillType());
			//MatchTableBO_Client.querybillVo(getBillType());
			//0--����vo;1-����Vo;2-�ӱ�Vo;
			m_billVo = OrderForRef4FVO.class.getName();
			m_billHeadVo = retString[1];
			m_billBodyVo = OrderDdlbVO.class.getName();
		} catch (Exception e) {
			Logger.error(e.getMessage(), e);
		}
	}

	protected void showErroMessage(String errmsg) {
		MessageDialog.showHintDlg(
				this,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000059")/* @res "����" */, errmsg);
	}
}
