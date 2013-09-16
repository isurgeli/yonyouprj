/**
 * $文件说明$
 *
 * @author yangb
 * @version 
 * @see
 * @since
 * @time 2008-8-1 下午01:01:16
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
	private UIButton ivjbtnXcl = null;// 现存量
	private UIPanel ivjPanlMYCmd = null;
	private JPanel ivjUIDialogContentPane = null;

	private boolean isneedAllSelect = true;

	private boolean isneedLinkQuery = false;

	protected ICSplitModeDlg ivjSplitDlg;
	// 返回的集合Vo
	protected AggregatedValueObject retSrcBillVo;
	// 返回集合VO数组
	protected AggregatedValueObject[] retSrcBillVos;

	public CGSourceRefDlg(String pkField, String pkCorp, String operator,
			String funNode, String queryWhere, String billType,
			String businessType, String templateId, String currentBillType,
			Container parent) {
		super(pkField, pkCorp, operator, funNode, queryWhere, billType,
				businessType, templateId, currentBillType, parent);
		// TODO 自动生成构造函数存根
		addListener();
		addListenerEvent();
		// getbillListPanel().setmsetMultiSelect(false);
		getbillListPanel().setMultiSelect(false);
		getbillListPanel().getChildListPanel().setTotalRowShow(false);
		getbillListPanel().repaint();
		// 多选监听
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
			// 获得主表ID
			String id = getbillListPanel().getHeadBillModel()
					.getValueAt(row, getpkField()).toString();
			// 查询子表VO数组
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
			// 利用产品组传入的条件与当前查询条件获得条件组成主表查询条件
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
							"UPPpfworkflow-000237")/* @res "错误" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("pfworkflow",
							"UPPpfworkflow-000490")/* @res "数据加载失败！" */);
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
		// 行切换
		BillModel bodyModel = getbillListPanel().getBodyBillModel();
		BillModel headModel = getbillListPanel().getHeadBillModel();
		headModel.setRowState(headRow, state);
		loadBodyData(headRow);
		getbillListPanel().getBodyBillModel().execLoadFormula();
		for (int i = 0; i < bodyModel.getRowCount(); i++)
			bodyModel.setRowState(i, headModel.getRowState(headRow));
		// 备份数据
		getbillListPanel().setBodyModelDataCopy(headRow);
	}

	public void onNone() {
		int maxindex = getbillListPanel().getHeadBillModel().getRowCount();
		if (maxindex > 0)
			getbillListPanel().getHeadTable().clearSelection();
		// 全选处理
		for (int i = 0; i < maxindex; i++) {
			setRowState(i, BillModel.UNSTATE);
		}
	}

	/**
	 * "确定"按钮的响应，从界面获取被选单据VO
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
					"SCMCOMMON", "UPPSCMCommon-000199")/* @res "请选择单据" */);
			return;
		}

		retBillVo = retBillVos[0];

		retSrcBillVo = retSrcBillVos[0];
		
		retBillVos = retSrcBillVos;
		
		this.getAlignmentX();
		this.closeOK();
	}

	/**
	 * get。 创建日期：(2001-5-9 8:52:00)
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
			ivjbtnAll.setText("全选");
		}
		return ivjbtnAll;
	}

	private nc.ui.pub.beans.UIButton getbtnNone() {
		if (ivjbtnNone == null) {
			ivjbtnNone = new nc.ui.pub.beans.UIButton();
			ivjbtnNone.setName("ivjbtnNone");
			ivjbtnNone.setText("全消");
		}
		return ivjbtnNone;
	}

	private nc.ui.pub.beans.UIButton getbtnJoin() {
		if (ivjbtnJoin == null) {
			ivjbtnJoin = new nc.ui.pub.beans.UIButton();
			ivjbtnJoin.setName("ivjbtnJoin");
			ivjbtnJoin.setText("联查");
		}
		return ivjbtnJoin;
	}

	private nc.ui.pub.beans.UIButton getbtnPrint() {
		if (ivjbtnPrint == null) {
			ivjbtnPrint = new nc.ui.pub.beans.UIButton();
			ivjbtnPrint.setName("ivjbtnPrint");
			ivjbtnPrint.setText("打印");
		}
		return ivjbtnPrint;
	}

	private nc.ui.pub.beans.UIButton getbtnMYCancel() {
		if (ivjbtnMYCancel == null) {

			ivjbtnMYCancel = new nc.ui.pub.beans.UIButton();
			ivjbtnMYCancel.setName("btnMYCancel");
			ivjbtnMYCancel.setText(NCLangRes.getInstance().getStrByID("common",
					"UC001-0000008")/* @res "取消" */);
		}
		return ivjbtnMYCancel;
	}

	private nc.ui.pub.beans.UIButton getbtnOk() {
		if (ivjbtnMYOk == null) {
			ivjbtnMYOk = new nc.ui.pub.beans.UIButton();
			ivjbtnMYOk.setName("btnOk");
			// ivjbtnOk.setText(NCLangRes.getInstance().getStrByID("common",
			// "UC001-0000044")/*@res "确定"*/);
			ivjbtnMYOk.setText("确定");
		}
		return ivjbtnMYOk;
	}

	private nc.ui.pub.beans.UIButton getbtnMYQuery() {
		if (ivjbtnMYQuery == null) {

			ivjbtnMYQuery = new nc.ui.pub.beans.UIButton();
			ivjbtnMYQuery.setName("btnMYQuery");
			ivjbtnMYQuery.setText(NCLangRes.getInstance().getStrByID("common",
					"UC001-0000006")/* @res "查询" */);
		}
		return ivjbtnMYQuery;
	}

	private nc.ui.pub.beans.UIButton getbtnXcl() {
		if (ivjbtnXcl == null) {
			ivjbtnXcl = new nc.ui.pub.beans.UIButton();
			ivjbtnXcl.setName("ivjbtnXcl");
			ivjbtnXcl.setText("现存量");
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
				// 获的显示位数值
				// 装载模板
				nc.vo.pub.bill.BillTempletVO vo = ivjbillListPanel
						.getTempletData("1002AA1000000000P54J");

				BillListData billDataVo = new BillListData(vo);

				// 更改主表的显示位数
				String[][] tmpAry = getHeadShowNum();
				if (tmpAry != null) {
					setVoDecimalDigitsHead(billDataVo, tmpAry);
				}
				// 更改子表的显示位数
				tmpAry = getBodyShowNum();
				if (tmpAry != null) {
					setVoDecimalDigitsBody(billDataVo, tmpAry);
				}

				ivjbillListPanel.setListData(billDataVo);

				// 进行主子隐藏列的判断
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
		// 此方法要重写出
	}

	public void onJoin() {
		int[] rows = getbillListPanel().getHeadTable().getSelectedRows();
		if (rows == null | rows.length == 0) {
			NCOptionPane.showMessageDialog(this, "请先选择联查单据！");
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

		// 表头列表 行切换事件处理器
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
		// 取消状态
		if (rowstate == -1) {
			setRowState(row, BillModel.UNSTATE);
		}
		// 选中状态
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
			//0--单据vo;1-主表Vo;2-子表Vo;
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
						"UPPSCMCommon-000059")/* @res "错误" */, errmsg);
	}
}
