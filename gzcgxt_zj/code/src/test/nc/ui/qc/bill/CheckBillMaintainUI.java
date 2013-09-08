package nc.ui.qc.bill;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableColumn;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.uap.IVOPersistence;
import nc.itf.uap.rbac.IPowerManageQuery;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.ic.pub.lot.LotNumbRefPane;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.FramePanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIEditorPane;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.beans.UIRadioButton;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITabbedPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.table.ColumnGroup;
import nc.ui.pub.beans.textfield.UITextDocument;
import nc.ui.pub.beans.textfield.UITextType;
import nc.ui.pub.bill.BillBodyMenuListener;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillListPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pub.bill.IBillRelaSortListener2;
import nc.ui.pub.bill.UITabbedPaneUI;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.pub.linkoperate.ILinkApprove;
import nc.ui.pub.linkoperate.ILinkApproveData;
import nc.ui.pub.linkoperate.ILinkMaintain;
import nc.ui.pub.linkoperate.ILinkMaintainData;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.pub.linkoperate.ILinkType;
import nc.ui.pub.para.SysInitBO_Client;
import nc.ui.qc.mode.CheckmodeDef;
import nc.ui.qc.pub.CheckitemTreeDef;
import nc.ui.qc.pub.CheckstateDef;
import nc.ui.qc.pub.CheckstategroupDef;
import nc.ui.qc.pub.DefectprocessDef;
import nc.ui.qc.pub.DefecttypeDef;
import nc.ui.qc.pub.MultiCorpQueryClient;
import nc.ui.qc.pub.OtherRefModel;
import nc.ui.qc.pub.ProductOrderDef;
import nc.ui.qc.pub.QCAccuracySetHelper;
import nc.ui.qc.pub.QCRefModel;
import nc.ui.qc.pub.QCTool;
import nc.ui.qc.pub.WorkshopDef;
import nc.ui.qc.standard.CheckStandardUI;
import nc.ui.qc.standard.CheckstandardDef;
import nc.ui.qc.standard.CheckstandardHelper;
import nc.ui.qc.standard.QCStandTableModel;
import nc.ui.qc.standard.StandInputDlg;
import nc.ui.qc.type.ChecktypeDef;
import nc.ui.qc.type.ChecktypeHelper;
import nc.ui.scm.ic.freeitem.DefHelper;
import nc.ui.scm.ic.freeitem.FreeItemRefPane;
import nc.ui.scm.inv.InvTool;
import nc.ui.scm.pu.ParaVOForBatch;
import nc.ui.scm.pub.cache.CacheTool;
import nc.ui.scm.pub.def.DefSetTool;
import nc.vo.bd.b06.PsndocVO;
import nc.vo.bd.def.DefVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.CommonConstant;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.bill.BillTempletBodyVO;
import nc.vo.pub.bill.BillTempletHeadVO;
import nc.vo.pub.bill.BillTempletVO;
import nc.vo.pub.formulaset.VarryVO;
import nc.vo.pub.formulaset.util.StringUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;
import nc.vo.pub.query.ConditionVO;
import nc.vo.qc.pub.QCConst;
import nc.vo.qc.query.CheckstandardItemVO;
import nc.vo.qc.type.ChecktypeVO;
import nc.vo.scm.bd.SmartVODataUtils;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.bill.WhVO;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.scm.qc.bill.CheckbillB2HeaderVO;
import nc.vo.scm.qc.bill.CheckbillB2ItemVO;
import nc.vo.scm.qc.bill.CheckbillB2VO;
import nc.vo.scm.qc.bill.CheckbillHeaderVO;
import nc.vo.scm.qc.bill.CheckbillItemVO;
import nc.vo.scm.qc.bill.CheckbillVO;
import nc.vo.scm.qc.bill.InfoVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.rbac.IOrgType;
import nc.vo.uap.rbac.power.UserPowerQueryVO;

import org.apache.commons.lang.StringUtils;

/**
 * 此处插入类型说明。 版本号: 功能描述:维护质量检验单 作者: 熊海情 创建日期:2002/04/18 修改人: 修改日期: 修改原因:
 * <p>
 * modified:zhongwei 20050915 质检单审批增加限制条件：审批日期不小于制单日期(报告日期)
 * <p>
 * modified:zhongwei 20061116 在client端操作的日期项一律取登陆日期(精确到时间时，取client系统时间)
 * <p>
 * modified:czp 20070621 支持库存报检
 * 
 * modify:liyc 20080725 支持各种类型质检单多页签，检验信息多表头，检验信息动态列打印
 * 
 * lxt 2013-07 cgxf：赣州晨光稀土项目，样本增加功能与质检数据自动填写。
 */
public class CheckBillMaintainUI extends nc.ui.pub.ToftPanel implements ChangeListener, BillEditListener,
		ActionListener, javax.swing.event.ListSelectionListener, IBillRelaSortListener2, ILinkApprove,// 审批流
		ILinkMaintain,// 单据驳回
		BillEditListener2, MouseListener, ILinkQuery,// 联查
		BillBodyMenuListener// 表体右键菜单监听
{

	// 修正监听错误，补充监听是否查询过表体缓存
	class BillListHeadSortListener implements IBillRelaSortListener2 {
		public Object[] getRelaSortObjectArray() {
			return m_bBodyQuery;
		}
	}

	// 标识当前界面是审批流处理
	private boolean m_bWorkFlow = false;

	// 是否查询过标志，决定刷新按钮是否可用，since v53
	public boolean m_bQueried = false;

	CheckbillButtonManager buttonManager = null;

	// 页签
	protected UITabbedPane m_tabbedPane = null;

	// 检验单卡片
	private BillCardPanel m_cardBill = null;

	// 检验单列表
	private BillListPanel m_listBill = null;

	// 检验信息卡片
	private BillCardPanel m_cardInfo = null;

	// 检验单界面状态(0卡片1列表2信息3报检)
	private int m_nUIState = 0;

	// 查询模板
	private MultiCorpQueryClient m_condClient = null;

	// 查询条件
	private ConditionVO m_conditionVOs[] = null;

	// 查询模板其它条件
	private UIRadioButton m_rbFree = null;

	private UIRadioButton m_rbPass = null;

	private UIRadioButton m_rbGoing = null;

	private UIRadioButton m_rbNoPass = null;

	private UIRadioButton m_rbAll = null;

	private int m_nAddCondition = -1;

	// 登陆公司主键
	private String m_sUnitCode = getCorpPrimaryKey();

	// 质检单记录缓存
	public CheckbillVO m_checkBillVOs[] = null;

	// 检验信息记录缓存
	public CheckbillB2VO m_grandVOs[] = null;

	// 检验信息记录缓存
	private CheckbillB2VO m_grandVOs2[] = null;

	// 质检单记录缓存指针
	public int m_nBillRecord = 0;

	// 检验信息记录缓存指针
	public int m_nInfoRecord = 0;

	// 编辑状态
	public boolean m_bBillAdd = false;

	public boolean m_bBillEdit = false;

	private boolean m_bInfoAdd = false;

	private boolean m_bInfoEdit = false;

	private boolean m_bInfoEmend = false;

	// 检验类型选择对话框
	protected nc.ui.qc.type.ChecktypeDlg m_dialog = null;

	// 表体信息是否已查询
	private UFBoolean m_bBodyQuery[] = null;

	// 定位对话框及按钮
	private UIComboBox m_comLocate = null;

	private UIButton m_btnLocateOK = null;

	private UIButton m_btnLocateCancel = null;

	private UIDialog m_dlgLocate = null;

	// 检验标准类型下拉框
	private UIComboBox m_comType = null;

	// 执行标准对话框
	private UIDialog m_dlgText = null;

	private UIEditorPane m_textPane = null;

	private UITextField m_textExec = null;

	// 部门
	private AbstractRefModel m_deptDef = null;

	// 存货
	private AbstractRefModel m_invDef = null;

	// 操作员
	private AbstractRefModel m_operatorDef = null;

	// 检验方式
	private CheckmodeDef m_modeDef = null;

	// 检验标准
	private CheckstandardDef m_standardDef = null;

	// 主数量精度控制
	private int m_numDecimal = 2;

	// 辅数量精度控制
	private int m_assistDecimal = 2;

	// 换算率精度控制
	private int m_convertDecimal = 2;

	// 单价精度控制
	@SuppressWarnings("unused")
	private int m_priceDecimal = 2;

	// 金额精度控制
	@SuppressWarnings("unused")
	private int m_moneyDecimal = 2;

	// 卡片/列表转到报检
	@SuppressWarnings("unused")
	private boolean m_bCardPray = true;

	// 检验信息是否删除操作
	private boolean m_bInfoDel = false;

	// 来源单据信息
	private Hashtable m_HTSourcebillInfo = null;

	// 是否允许本人审批\弃审(依据参数QC01)
	private boolean m_bAllowSelf = true;

	// 是否允许他人修改删除单据(依据参数QC02)
	private boolean m_bAllowAnother = true;

	// 是否允许弃审他人审批的单据(依据参数QC03)
	private boolean m_bAllow = true;

	// 删除的检验信息缓存
	private Hashtable m_hDelInfo = new Hashtable();

	//
	protected String billCardID;

	// add by yye begin 2005-03-28 联查主料质检信息功能
	private UITabbedPane m_tabbedPaneForMMQuery = null;// 联查主料质检信息页签

	private BillListPanel m_listBillForMMQuery = null;// 联查主料质检检验单列表

	private BillCardPanel m_cardInfoForMMQuery = null;// 联查主料质检检验信息卡片

	private Hashtable m_HTSourcebillInfoForMMQuery = null;// 联查主料质检检验信息来源单据信息

	private CheckbillB2VO m_grandVOsForMMQuery[] = null;// 联查主料质检检验信息记录缓存

	private UFBoolean m_bBodyQueryForMMQuery[] = null;// 联查主料 表体信息是否已查询

	private CheckbillVO m_checkBillVOsForMMQuery[] = null;// 联查主料质检单记录缓存

	private int m_nBillRecordForMMQuery = 0;// 联查主料质检单记录缓存指针

	private int m_nInfoRecordForMMQuery = 0;// 联查主料检验信息记录缓存指针

	private boolean m_bMMQuery = false;// 是否为联查主料检验信息状态

	private int m_nUIStateMMQuery = 0;// 联查主料界面状态（0列表 2信息）

	// add by yye end 2005-03-28 联查主料质检信息功能
	private boolean mbol_hasInitQuery = false;

	private boolean mbol_CancelFromDisCardInfo = false;

	private nc.ui.ic.pub.lot.LotNumbRefPane m_LotNumbRefPane = null;// 批次号档案参照

	private int m_doubleClick = 2;

	// 保质期存货 标志和天数
	private Vector vQualityMan = new Vector();

	// 表体换算率缓存
	private UFDouble iNexchangrateb = new UFDouble(0);

	// 摸板类型，支持多脚本审批
	public String strBilltemplet = null;

	// 区分节点数打开还是其他地方打开单据标志
	private int opentype = ILinkType.NONLINK_TYPE;

	private String strBilltemplate = null;

	// 缓存改变前的表体批次号
	Hashtable<String, String> vContain = new Hashtable<String, String>();

	// 记录改变前的检验单号
	private String strCheckbillCode = null;

	private CheckStatusBatchDLG batchDLG = null;

	// 模版注册缓存
	private BillTempletVO tmpletvo = null;

	// 当前单据对应的标准id
	private String[] sCheckStandards = null;

	// 质量等级组
	private String[] sCheckState = null;

	// 供应商
	private String strOldVendor = null;

	// 分配检验项目权限的sql
	private String strSubSqlPower = null;

	// 分配权限的检验项目id
	private HashMap hPowerCheckitemID = new HashMap();

	public boolean bIsLinked = false;

	/**
   * CheckBillMaintainUI 构造子注解。
   */
	public CheckBillMaintainUI(FramePanel fp) {
		super();
		opentype = fp.getLinkType();
		if (opentype == ILinkType.LINK_TYPE_APPROVE || opentype == ILinkType.LINK_TYPE_MAINTAIN
				|| opentype == ILinkType.LINK_TYPE_QUERY) {
			buttonManager = new CheckbillButtonManager(this);
			return;
		}
		init();
	}

	public CheckBillMaintainUI(String cardID) {

		m_nAddCondition = -1;
		m_sUnitCode = getCorpPrimaryKey();
		m_bCardPray = true;

		// resultVo = null;
		billCardID = cardID;
		init();
	}

	public void doApproveAction(ILinkApproveData approvedata) {
		if (approvedata == null)
			return;

		m_bWorkFlow = true;

		String billID = approvedata.getBillID();

		initDataAndPanel(billID);
	}

	/**
   * 此处插入方法说明。 功能描述: 输入参数: 返回值: 异常处理: 日期:
   */
	public void actionPerformed(java.awt.event.ActionEvent event) {
		Object source = event.getSource();
		if (source == ((UIRefPane) getBillCardPanel().getHeadItem("cvendormangid").getComponent()).getUIButton()) {
			strOldVendor = ((UIRefPane) getBillCardPanel().getHeadItem("cvendormangid").getComponent()).getRefPK();
		}
		if (source instanceof UIButton && (UIButton) event.getSource() == m_btnLocateCancel) {
			// 定位对话框
			m_dlgLocate.closeCancel();

		} else if (source instanceof UIButton && (UIButton) event.getSource() == m_btnLocateOK) {
			// 定位对话框
			m_dlgLocate.closeOK();
			int n = m_comLocate.getSelectedIndex();
			if (n < 0)
				return;

			m_nBillRecord = n;

			// 若表体未查询，则查询表体
			if (!m_bBodyQuery[m_nBillRecord].booleanValue()) {
				try {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
					String chooseDataPower = null;
					chooseDataPower = getCheckItemDataPower();
					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs(),
							chooseDataPower);
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);

					if (list != null && list.size() > 0) {
						// 有返回结果
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
						CheckbillB2VO grandVO[] = null;
						Object o = list.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
							m_grandVOs = grandVO;
						} else {
							m_grandVOs = null;
						}

						m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
						m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);

						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);
					} else {
						// 无返回结果
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000015")/*
                                       * @res "查询表体"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                               * @res
                                                                                               * "表体不存在！"
                                                                                               */);
						return;
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作"
                                                                                                                         */, e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/* @res "查询表体" */, e.getMessage());
					return;
				}
			} else {
				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();
			}
			initMultiPanel(m_checkBillVOs[m_nBillRecord]);
			QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);

			// since v53, 按钮逻辑重构, by Czp
			buttonManager.setButtonsStateCard();

		} else if (source instanceof UITextField && (UITextField) event.getSource() == m_textExec) {
			try {
				m_textPane.setPage("file:" + m_textExec.getText());
				m_dlgText.showModal();
			} catch (Exception e) {
				SCMEnv.out(e);
			}
		}
	}

	/**
   * 此处插入方法说明。 功能描述: 返回主料联查专用BillListPanel 返回值: BillListPanel 日期:2005/03/29 袁野
   */

	private BillListPanel getBillListPanelMMQuery() {
		if (m_listBillForMMQuery == null) {
			try {
				m_listBillForMMQuery = new BillListPanel(false);
				// user code begin {1}
				// 加载模板
				String key = m_dialog.getCheckbillid();
				if (key == null || key.trim().length() == 0) {
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000021")/*
                                                             * @res "加载模板"
                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000022")/*
                                                                                             * @res
                                                                                             * "模板不存在！"
                                                                                             */);
					return null;
				}
				m_listBillForMMQuery.loadTemplet(key);

				BillListData bd = m_listBillForMMQuery.getBillListData();

				// 设置数量的精度控制
				BillItem item = bd.getHeadItem("nchecknum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nassistchecknum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);
				item = bd.getHeadItem("nexchangerate");
				if (item.isShow())
					item.setDecimalDigits(m_convertDecimal);
				item = bd.getBodyItem("nnum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getBodyItem("nassistnum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);
				item = bd.getBodyItem("nexchangerateb");
				if (item.isShow())
					item.setDecimalDigits(m_convertDecimal);
				item = bd.getHeadItem("nqualifiednum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nunqualifiednum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nqualifiedrate");
				if (item.isShow())
					item.setDecimalDigits(m_convertDecimal);

				// add by yye begin 2005-05-21
				item = bd.getHeadItem("nqualifiedassinum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nunqualifiedassinum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nchangeassinum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nchangenum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);

				// add by yye end 2005-05-21

				m_listBillForMMQuery.setListData(bd);
				// 处理自定义项
				QCTool.changeListDataByUserDef(m_listBillForMMQuery, getClientEnvironment().getCorporation().getPk_corp(),
						"WN", getClientEnvironment().getUser().getPrimaryKey());

				m_listBillForMMQuery.addEditListener(this);

				// 表头选择监听
				m_listBillForMMQuery.getHeadTable().setCellSelectionEnabled(false);
				m_listBillForMMQuery.getHeadTable().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
				m_listBillForMMQuery.getHeadTable().getSelectionModel().addListSelectionListener(this);

				m_listBillForMMQuery.getChildListPanel().setTotalRowShow(true);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.getMessage());
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                       * @res
                                                                                                                       * "加载模板"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000023")/*
                                                                                           * @res
                                                                                           * "检验单列表模板不存在！"
                                                                                           */);
				return null;
			}
		}
		return m_listBillForMMQuery;
	}

	/**
   * 此处插入方法说明。 功能描述: 返回主料联查专用BillCardPanel 返回值: BillCardPanel 日期:2005/03/29 袁野
   */

	private BillCardPanel getBillCardPanelMMQuery() {
		if (m_cardInfoForMMQuery == null) {
			try {
				m_cardInfoForMMQuery = new BillCardPanel();
				// user code begin {1}
				// 加载模板
				String key = m_dialog.getCheckinfoid();
				if (key == null || key.trim().length() == 0) {
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000021")/*
                                                             * @res "加载模板"
                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000022")/*
                                                                                             * @res
                                                                                             * "模板不存在！"
                                                                                             */);
					return null;
				}
				// m_cardInfoForMMQuery.loadTemplet(key);
				BillData bd = new BillData(m_cardInfoForMMQuery.getTempletData(key));

				// 设置数量的精度控制
				BillItem item = bd.getHeadItem("nnum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);

				m_cardInfoForMMQuery.setBillData(bd);
				m_cardInfoForMMQuery.setAutoExecHeadEditFormula(true);
				// 处理自定义项
				nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef(m_cardInfoForMMQuery, getClientEnvironment()
						.getCorporation().getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdefitem", "vdefitem");

				m_cardInfoForMMQuery.setEnabled(false);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.getMessage());
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                       * @res
                                                                                                                       * "加载模板"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000025")/*
                                                                                           * @res
                                                                                           * "检验信息卡片模板不存在！"
                                                                                           */);
				return null;
			}
		}
		return m_cardInfoForMMQuery;
	}

	/**
   * 为了减少初始化时前后台交互的次数，一次性获取多个系统参数 作者:袁野 日期：2005-06-17
   */
	public void initpara() {
		try {
			Hashtable table = SysInitBO_Client.queryBatchParaValues(m_sUnitCode, new String[] { "BD501", "BD502", "BD503",
					"BD505", "QC01", "QC02", "QC03" });

			String s = (String) table.get("BD501");
			if (s != null && s.trim().length() > 0)
				m_numDecimal = Integer.parseInt(s);
			s = (String) table.get("BD502");
			if (s != null && s.trim().length() > 0)
				m_assistDecimal = Integer.parseInt(s);
			s = (String) table.get("BD503");
			if (s != null && s.trim().length() > 0)
				m_convertDecimal = Integer.parseInt(s);
			s = (String) table.get("BD505");
			if (s != null && s.trim().length() > 0)
				m_priceDecimal = Integer.parseInt(s);
			s = (String) table.get("QC01");
			if (s != null && s.trim().length() > 0)
				m_bAllowSelf = (new UFBoolean(s)).booleanValue();
			// 获得参数QC02,是否允许他人修改删除单据
			s = (String) table.get("QC02");
			if (s != null && s.trim().length() > 0) {
				m_bAllowAnother = (new UFBoolean(s)).booleanValue();
			}
			s = (String) table.get("QC03");
			if (s != null && s.trim().length() > 0) {
				m_bAllow = (new UFBoolean(s)).booleanValue();
			}
			s = QCAccuracySetHelper.getCurrDigitByPKCorp(m_sUnitCode);
			// s = (String) objs[1];
			if (s != null && s.length() > 0)
				m_moneyDecimal = Integer.parseInt(s.trim());
			// m_moneyDecimal = 2;

		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000027")/*
                                                                                                                     * @res
                                                                                                                     * "获取系统初始化参数出错"
                                                                                                                     */, e.getMessage());
			return;
		}
	}

	/**
   * 此处插入方法说明。 功能描述:卡片单据编辑后，调用该方法 输入参数: 返回值: 异常处理: 日期:2002/04/18
   */
	public void afterEdit(BillEditEvent event) {

		String key = event.getKey().trim();

		if (m_nUIState == 0 && event.getPos() == BillItem.HEAD) { // 检验单卡片头
			// 自定义项PK处理
			if (key.equals("vdef1")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef1", "pk_defdoc1");
			} else if (key.equals("vdef2")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef2", "pk_defdoc2");
			} else if (key.equals("vdef3")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef3", "pk_defdoc3");
			} else if (key.equals("vdef4")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef4", "pk_defdoc4");
			} else if (key.equals("vdef5")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef5", "pk_defdoc5");
			} else if (key.equals("vdef6")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef6", "pk_defdoc6");
			} else if (key.equals("vdef7")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef7", "pk_defdoc7");
			} else if (key.equals("vdef8")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef8", "pk_defdoc8");
			} else if (key.equals("vdef9")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef9", "pk_defdoc9");
			} else if (key.equals("vdef10")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef10", "pk_defdoc10");
			} else if (key.equals("vdef11")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef11", "pk_defdoc11");
			} else if (key.equals("vdef12")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef12", "pk_defdoc12");
			} else if (key.equals("vdef13")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef13", "pk_defdoc13");
			} else if (key.equals("vdef14")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef14", "pk_defdoc14");
			} else if (key.equals("vdef15")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef15", "pk_defdoc15");
			} else if (key.equals("vdef16")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef16", "pk_defdoc16");
			} else if (key.equals("vdef17")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef17", "pk_defdoc17");
			} else if (key.equals("vdef18")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef18", "pk_defdoc18");
			} else if (key.equals("vdef19")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef19", "pk_defdoc19");
			} else if (key.equals("vdef20")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditHead(getBillCardPanel().getBillData(), "vdef20", "pk_defdoc20");
			}

			if (key.equals("cinventorycode")) {

				afterEditWhenHeadInventoryCode(event); // 修改表头存货后的相应变化

			} else if (key.equals("cassistunitid")) {

				afterEditWhenHeadAssistUnitId(event); // 修改表头辅单位后的相应变化

			} else if (key.equals("nexchangerate")) {

				afterEditWhenHeadExchangeRate(event); // 修改表头换算率后的相应变化

			} else if (key.equals("nchecknum")) {

				afterEditWhenHeadCheckNum(event); // 修改表头报检数量后的相应变化

			} else if (key.equals("nassistchecknum")) {

				afterEditWhenHeadAssistCheckNum(event); // 修改表头报检辅数量后的相应变化

			} else if (key.equals("ccheckmodeid")) {

				afterEditWhenHeadCheckModeId(event); // 修改表头检验方式后的相应变化

			} else if (key.equals("ccheckstateid")) {

				afterEditWhenHeadCheckStateId(event); // 修改表头检验状态组后的相应变化

			} else if (key.equals("ccheckstandardid")) {

				afterEditWhenHeadCheckStandardId(event); // 修改表头检验标准后的相应变化

			} else if (key.equalsIgnoreCase("cwarehouseid")) {
				afterEditWhenBodyCwareHouse(event);
			} else if (key.equalsIgnoreCase("vcheckbillcode")) {
				afterEditWhenHeadCheckBillCode(event);
			} else if (key.equals("cvendormangid")) {

				afterEditWhenHeadVendormangId(event); // 修改表头辅单位后的相应变化

			}
		} else if (m_nUIState == 0 && event.getPos() == BillItem.BODY) { // 检验单卡片体

			// *************************************历史原因Begin*******************************
			// 合格，则不合格类型，不合格项目不可编辑，且为空
			if (key.equals("vstobatchcode")) {
				afterEditWhenProdNum(event);
			}
			Object oTemp = getBillCardPanel().getBodyValueAt(event.getRow(), "bqualified");
			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				UFBoolean b = new UFBoolean(oTemp.toString());
				if (!b.booleanValue()) {
					getBillCardPanel().setCellEditable(event.getRow(), "cdefecttypename", true);
				} else {
					getBillCardPanel().setBodyValueAt(null, event.getRow(), "cdefecttypename");
					getBillCardPanel().setBodyValueAt(null, event.getRow(), "cdefecttypeid");
					getBillCardPanel().setBodyValueAt(null, event.getRow(), "ccheckitemid");
					getBillCardPanel().setCellEditable(event.getRow(), "cdefecttypename", false);
				}
			}
			// *************************************历史原因End*******************************

			if (key.equals("bchange")) {

				afterEditWhenBodyChange(event); // 修改表体是否改判后的相应变化

			} else if (key.equals("cchginventorycode")) {

				afterEditWhenBodyChgInventoryCode(event); // 修改表体改判存货后的相应变化

			} else if (key.equals("bqualified")) {

				afterEditWhenBodyQualified(event); // 修改表体是否合格后的相应变化

			} else if (key.equals("nnum")) {

				afterEditWhenBodyNum(event); // 修改表体主数量后的相应变化

			} else if (key.equals("nassistnum")) {

				afterEditWhenBodyAssistNum(event); // 修改表体辅数量后的相应变化

			} else if (key.equals("cchgassistunitname")) {

				afterEditWhenBodyChgAssistUnitName(event); // 修改表体辅单位后的相应变化

			} else if (key.equals("nexchangerateb")) {

				afterEditWhenBodyExchangeRateB(event); // 修改表体换算率后的相应变化

			} else if (key.equals("cchargedeptname")) {

				afterEditWhenBodyChargeDeptName(event); // 修改表体责任部门后的相应变化

			} else if (key.equals("cdefecttypename")) {

				afterEditWhenBodyDefectTypeName(event); // 修改表体不合格类型后的相应变化

			} else if (key.equals("ccheckstatename")) {

				afterEditWhenBodyCheckStateName(event); // 修改表体检验状态后的相应变化
			} else if (key.equals("cb_cqualitylevelname")) {
				afterEditWhenBodyCheckState(event);

			} else if (key.equalsIgnoreCase("dproducedate")) {
				afterEditWhenBodydproducedate(event);

			} else if (key.equalsIgnoreCase("cb_dvalidate")) {
				afterEditWhenBodydvalidatedate(event);

			} else if (key.equalsIgnoreCase("nexchangerateb")) {
				afterEditWhenBodyNexchangerateb(event);
			}

		} else if (m_nUIState == 2 && event.getPos() == BillItem.BODY) {// 检验信息体

			// 自定义项PK处理
			if (key.equals("vdefitem1")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem1",
						"pk_defdoc1");
			} else if (key.equals("vdefitem2")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem2",
						"pk_defdoc2");
			} else if (key.equals("vdefitem3")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem3",
						"pk_defdoc3");
			} else if (key.equals("vdefitem4")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem4",
						"pk_defdoc4");
			} else if (key.equals("vdefitem5")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem5",
						"pk_defdoc5");
			} else if (key.equals("vdefitem6")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem6",
						"pk_defdoc6");
			} else if (key.equals("vdefitem7")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem7",
						"pk_defdoc7");
			} else if (key.equals("vdefitem8")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem8",
						"pk_defdoc8");
			} else if (key.equals("vdefitem9")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem9",
						"pk_defdoc9");
			} else if (key.equals("vdefitem10")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem10",
						"pk_defdoc10");
			} else if (key.equals("vdefitem11")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem11",
						"pk_defdoc11");
			} else if (key.equals("vdefitem12")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem12",
						"pk_defdoc12");
			} else if (key.equals("vdefitem13")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem13",
						"pk_defdoc13");
			} else if (key.equals("vdefitem14")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem14",
						"pk_defdoc14");
			} else if (key.equals("vdefitem15")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem15",
						"pk_defdoc15");
			} else if (key.equals("vdefitem16")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem16",
						"pk_defdoc16");
			} else if (key.equals("vdefitem17")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem17",
						"pk_defdoc17");
			} else if (key.equals("vdefitem18")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem18",
						"pk_defdoc18");
			} else if (key.equals("vdefitem19")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem19",
						"pk_defdoc19");
			} else if (key.equals("vdefitem20")) {
				nc.ui.scm.pub.def.DefSetTool.afterEditBody(getBillCardPanelInfo().getBillModel(), event.getRow(), "vdefitem20",
						"pk_defdoc20");
			}

			if (key.equals("ccheckitemname")) {

				afterEditWhenInfoBodyCheckItemName(event);// 修改信息表体检验项目后的相应变化

			} else if (key.equals("cstandardvalue") && (!m_bInfoAdd)) {
				// 标准值
				Object oTemp = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "cstandardvalue");
				if (oTemp == null || oTemp.toString().trim().length() == 0) {
					CheckbillB2ItemVO tempVOs[] = (CheckbillB2ItemVO[]) m_grandVOs[m_nInfoRecord].getChildrenVO();
					if (tempVOs != null && tempVOs.length > 0) {
						oTemp = tempVOs[event.getRow()].getCstandardvalue();
						getBillCardPanelInfo().setBodyValueAt(oTemp, event.getRow(), "cstandardvalue");
					}
				} else {
					getBillCardPanelInfo().setBodyValueAt(oTemp, event.getRow(), "cstandardvalue");
				}
				return;

			} else if (key.equals("cresult")) {
				afterEditWhenInfoBodyResult(event);// 修改信息表体实际检验值后的相应变化

			}

		}
	}

	/**
   * 表头供应商编辑后事件
   * 
   * @param event
   */
	private void afterEditWhenHeadVendormangId(BillEditEvent event) {
		HashMap hData = new HashMap();
		String strVendorbaseid = null;
		BillItem headItem;

		UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem("cinventorycode").getComponent();

		// 获得存货管理ID,存货编码及名称
		String cmangid = refPane.getRefPK();
		if (cmangid == null) {
			return;
		}
		String cinventorycode = refPane.getRefCode();
		String cinventoryname = refPane.getRefName();

		refPane.setValue(cinventorycode);
		getBillCardPanel().getHeadItem("cmangid").setValue(cmangid);
		// getBillCardPanel().getHeadItem("cinventorycode").setValue(cinventorycode);
		getBillCardPanel().getHeadItem("cinventoryname").setValue(cinventoryname);
		// 取供应商值
		refPane = (UIRefPane) getBillCardPanel().getHeadItem("cvendormangid").getComponent();
		String strVendormangid = refPane.getRefPK();
		if (strVendormangid != null) {
			if (strVendormangid != null) {
				Object[] obName = null;
				try {
					obName = (Object[]) CacheTool.getCellValue("bd_cumandoc", "pk_cumandoc", "pk_cubasdoc", strVendormangid);
				} catch (Exception e) {
					SCMEnv.out(e);
					QcTool.outException(this, e);
				}
				strVendorbaseid = obName[0].toString();
			}
		}
		// 获得存货基础ID，规格，型号，主计量，检验方式ID，检验标准ID
		CheckbillHeaderVO headVO = new CheckbillHeaderVO();
		getBillCardPanel().getBillData().getHeaderValueVO(headVO);
		ArrayList list = null;
		try {
			list = CheckbillHelper.queryInvRelateInfo(new String[] { cmangid }, null, m_dialog.getChecktypeid(), m_sUnitCode,
					new String[] { strVendorbaseid });
			if (list == null || list.size() == 0) {
				sCheckStandards = null;
				return;
			}
			getBillCardPanel().getHeadItem("cbaseid").setValue(((Vector) list.get(0)).get(0));
			getBillCardPanel().getHeadItem("cinventoryspec").setValue(((Vector) list.get(1)).get(0));
			getBillCardPanel().getHeadItem("cinventorytype").setValue(((Vector) list.get(2)).get(0));
			getBillCardPanel().getHeadItem("cinventoryunit").setValue(((Vector) list.get(3)).get(0));

			if (list.size() < 5 || list.get(4) == null || ((Vector) list.get(4)).size() == 0
					|| ((Vector) list.get(4)).get(0) == null) {
				BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
				BillTempletBodyVO[] initBodyVO = tmpletvo.getBodyVO();
				BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
				BillData newbd = new BillData(initVO);
				m_cardBill.setBillData(newbd);
				m_cardBill.setAutoExecHeadEditFormula(true);
				m_cardBill.addEditListener(this);
				getBillCardPanel().getBillData().setHeaderValueVO(headVO);
				m_cardBill.setEnabled(true);
				m_cardBill.updateUI();
				setAddEditState();
				sCheckStandards = null;
				return;
			}
			int iRet = showYesNoMessage(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000003")/*检查到新的检验标准，是否更新多页签模板*/);
			if (iRet == MessageDialog.ID_YES) {
				if (sCheckStandards != null) {
					for (int k = 0; k < sCheckStandards.length; k++) {
						CheckbillItemVO[] voBody = (CheckbillItemVO[]) getBillCardPanel().getBillModel("table" + String.valueOf(k))
								.getBodyValueVOs(CheckbillItemVO.class.getName());
						hData.put(sCheckStandards[k], voBody);
					}
				}
				int iSize = ((Vector) (((Vector) list.get(4)).get(0))).size() / 4;
				String[][] cMode = new String[1][];
				String[][] cStandard = new String[1][];
				String[][] cDefault = new String[1][];
				String[][] cState = new String[1][];
				Vector vTempData = (Vector) (((Vector) list.get(4)).get(0));
				cMode[0] = new String[iSize];
				cStandard[0] = new String[iSize];
				cDefault[0] = new String[iSize];
				cState[0] = new String[iSize];
				for (int j = 0; j < iSize; j++) {
					cStandard[0][j] = vTempData.get(0 + j * 4).toString();
					cMode[0][j] = vTempData.get(1 + j * 4).toString();
					cDefault[0][j] = vTempData.get(2 + j * 4).toString();
					cState[0][j] = vTempData.get(3 + j * 4).toString();
				}
				headVO.setCcheckmodeid(cMode[0][0]);
				String[] strStandardids = new String[cStandard[0].length];
				for (int i = 0; i < strStandardids.length; i++) {
					strStandardids[i] = cStandard[0][i];
				}
				sCheckState = new String[cState[0].length];
				for (int i = 0; i < strStandardids.length; i++) {
					sCheckState[i] = cState[0][i];
				}
				initMultiStandardPanel(strStandardids);
			} else {
				if (sCheckStandards != null && sCheckStandards.length > 0) {
					for (int k = 0; k < sCheckStandards.length; k++) {
						CheckbillItemVO[] voBody = (CheckbillItemVO[]) getBillCardPanel().getBillModel("table" + String.valueOf(k))
								.getBodyValueVOs(CheckbillItemVO.class.getName());
						hData.put(sCheckStandards[k], voBody);
					}
				}
				headVO.setCvendormangid(strOldVendor);

			}
		} catch (Exception e) {
			SCMEnv.out(e);
			sCheckStandards = null;
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000050")/*
                                                                                                                     * @res
                                                                                                                     * "根据存货获得检验标准/方式"
                                                                                                                     */, e.getMessage());
			return;
		}

		getBillCardPanel().getHeadItem("cassistunitid").setValue(null);
		getBillCardPanel().getHeadItem("nexchangerate").setValue(null);
		boolean lb_isAssUnitManaged = nc.ui.scm.inv.InvTool.isAssUnitManaged(list.get(0).toString());
		getBillCardPanel().getHeadItem("bassunitmanaged").setValue(new UFBoolean(lb_isAssUnitManaged));

		if (lb_isAssUnitManaged) {
			// 是否可编辑
			headItem = getBillCardPanel().getHeadItem("nassistchecknum");
			if (headItem.isShow())
				headItem.setEnabled(true);
			headItem = getBillCardPanel().getHeadItem("cassistunitid");
			if (headItem.isShow())
				headItem.setEnabled(true);
			headItem = getBillCardPanel().getHeadItem("nexchangerate");
			if (headItem.isShow())
				headItem.setEnabled(false);

			// 设置辅计量参照
			UIRefPane ref = (UIRefPane) getBillCardPanel().getHeadItem("cassistunitid").getComponent();
			String wherePart = "bd_convert.pk_invbasdoc='" + ((Vector) list.get(0)).get(0).toString() + "' ";
			ref.setWhereString(wherePart);
			String unionPart = " union all (";
			unionPart += "select bd_measdoc.shortname,bd_measdoc.measname,bd_invbasdoc.pk_measdoc ";
			unionPart += "from bd_invbasdoc ";
			unionPart += "left join bd_measdoc  ";
			unionPart += "on bd_invbasdoc.pk_measdoc=bd_measdoc.pk_measdoc ";
			unionPart += "where bd_invbasdoc.pk_invbasdoc='" + ((Vector) list.get(0)).get(0).toString() + "') ";
			ref.getRefModel().setGroupPart(unionPart);

			// 默认情况下，辅计量同主计量换算率为1.00且换算率不可编辑
			String lstr_MianUnitid = nc.ui.scm.inv.InvTool.getMainUnit(((Vector) list.get(0)).get(0).toString());
			getBillCardPanel().getHeadItem("cassistunitid").setValue(lstr_MianUnitid);
			getBillCardPanel().getHeadItem("nexchangerate").setValue(new UFDouble(1.0000));
			getBillCardPanel().getHeadItem("bfixed").setValue(new UFBoolean(true));

			// 如果存在辅数量，则用辅数量写主数量（辅数量优先）
			String lStr_nassistchecknum = (String) getBillCardPanel().getHeadItem("nassistchecknum").getValueObject(); // 辅数量
			if (lStr_nassistchecknum != null && lStr_nassistchecknum.trim().length() > 0) {
				getBillCardPanel().getHeadItem("nchecknum").setValue(lStr_nassistchecknum); // 主数量
			} else { // 如果不存在辅数量，则用主数量写辅数量
				String lStr_nchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject();
				if (lStr_nchecknum != null && lStr_nchecknum.trim().length() > 0) {
					getBillCardPanel().getHeadItem("nassistchecknum").setValue(lStr_nchecknum); //

				}
			}

		} else {
			// 是否可编辑
			headItem = getBillCardPanel().getHeadItem("nassistchecknum");
			headItem.setValue(null);
			if (headItem.isShow())
				headItem.setEnabled(false);
			headItem = getBillCardPanel().getHeadItem("cassistunitid");
			if (headItem.isShow())
				headItem.setEnabled(false);
			headItem = getBillCardPanel().getHeadItem("nexchangerate");
			if (headItem.isShow())
				headItem.setEnabled(false);

		}

		// 表头换存货表体全部删除,等待用户重新增行
		int rowCount = getBillCardPanel().getBillModel().getRowCount();
		int[] lines = new int[rowCount];
		for (int i = 0; i < rowCount; i++) {
			lines[i] = i;
		}

		getBillCardPanel().getBillModel().delLine(lines);
		// getBillCardPanel().getBillModel().clearBodyData();

		if (!m_dialog.getCheckbilltypecode().trim().equals("WH")) {
			headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
			if (headItem.isShow())
				headItem.setEnabled(false);
		} else {
			headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
			boolean lb_isBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(cmangid);
			if (lb_isBatchManaged) {
				if (headItem.isShow())
					headItem.setEnabled(true);
			} else {
				if (headItem.isShow())
					headItem.setEnabled(false);
			}
			headItem.setValue(null);
		}

		// 设置自由项自定义参照
		BillItem item = getBillCardPanel().getHeadItem("vfreeitem");
		if (item != null) {

			try {
				InvVO invVO = CheckbillHelper.setFreeItem(((Vector) list.get(0)).get(0).toString());
				invVO.setCinventoryid(list.get(0).toString());
				invVO.setCinventorycode(cinventorycode);
				invVO.setInvname(cinventoryname);
				invVO.setInvspec(((Vector) list.get(1)).get(0) == null ? null : ((Vector) list.get(1)).get(0).toString());
				invVO.setInvtype(((Vector) list.get(2)).get(0) == null ? null : ((Vector) list.get(2)).get(0).toString());
				FreeItemRefPane freeRef = new FreeItemRefPane();
				freeRef.setFreeItemParam(invVO);
				getBillCardPanel().getHeadItem("vfreeitem").setComponent(freeRef);
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000051")/*
                                                                                                                       * @res
                                                                                                                       * "根据存货设置自由项"
                                                                                                                       */, e.getMessage());
				return;
			}
		}
		getBillCardPanel().getBillData().setHeaderValueVO(headVO);
		if (sCheckStandards != null && sCheckStandards.length > 0) {
			for (int i = 0; i < sCheckStandards.length; i++) {
				if (hData.get(sCheckStandards[i]) != null) {
					CheckbillItemVO[] voBody = (CheckbillItemVO[]) hData.get(sCheckStandards[i]);
					getBillCardPanel().getBillModel("table" + String.valueOf(i)).setBodyDataVO(voBody);
				} else {
					getBillCardPanel().addLine("table" + String.valueOf(i));
					onCardAddLine(i);
				}
			}
		}

		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();
		getBillCardPanel().setEnabled(true);
		// 设置编辑状态
		setAddEditState();

	}

	private void afterEditWhenHeadCheckBillCode(BillEditEvent event) {
		//
		String sCheckBillCode = (String) getBillCardPanel().getHeadItem("vcheckbillcode").getValueObject();
		//
		if (sCheckBillCode != null && !sCheckBillCode.equalsIgnoreCase(strCheckbillCode)) {
			// modify by hanbin 原因：修改单据时才进行同步处理，解决：手工质检单，新增保存后，列表显示，第一张单子没有单据号
			if (m_bBillEdit && !m_bBillAdd && m_checkBillVOs != null && m_checkBillVOs.length > 0
					&& m_checkBillVOs[m_nBillRecord] != null && m_checkBillVOs[m_nBillRecord].getHeadVo() != null) {
				m_checkBillVOs[m_nBillRecord].getHeadVo().setVoldcheckbillcode(sCheckBillCode);
			}
		}
	}

	/**
   * 表体换算率必须大于0
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param event
   *          <p>
   * @author liyc
   * @time 2007-10-17 上午10:02:30
   */
	private void afterEditWhenBodyNexchangerateb(BillEditEvent event) {
		String strNexchangerateb = (String) getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");
		int iNum = Integer.parseInt(strNexchangerateb);
		if (iNum < 0) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001117")/*
                                                                                                                     * @res
                                                                                                                     * "表体换算率不能小于0"
                                                                                                                     */, "表体换算率不能小于0"/*-=notranslate=-*/);
		}
		getBillCardPanel().setBodyValueAt(iNexchangrateb, event.getRow(), "nexchangerateb");
	}

	/**
   * 根据保质期换算出失效日期
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param e
   *          <p>
   * @author liyc
   * @time 2007-9-12 下午02:36:21
   */
	private void afterEditWhenBodydproducedate(BillEditEvent e) {
		if (vQualityMan.size() != 0 && vQualityMan.get(0) != null && ((String) vQualityMan.get(0)).equals("Y")) {
			Object strDate = getBillCardPanel().getBodyValueAt(e.getRow(), "dproducedate");
			if (strDate != null) {
				// modify by hanbin 2010-12-13
				// 原因：（NCdp202857184）存货管理档案启用批次管理、保质期管理，存货保质期单位为月。若保质期时间大于1年后，则质量失效期每年相差5天。
				// 说明：代码主要是从维护开发部的补丁中抽取的。
				// UFDate dvalidate = new
				// UFDate(strDate.toString()).getDateAfter(((Integer)
				// vQualityMan.get(1)));
				// getBillCardPanel().getBillModel().setValueAt(dvalidate, e.getRow(),
				// "cb_dvalidate");
				try {
					String strCmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();
					if (StringUtils.isEmpty(strCmangid))
						return;
					// 保质期天数、保质期单位
					Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
							"qualitydaynum", strCmangid);
					if(objStr==null || objStr.length==0)return;
					int iQualityDayNum = Integer.parseInt(objStr[0].toString());
					objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
							"qualityperiodunit", strCmangid);
					if(objStr==null || objStr.length==0)return;
					int unit = Integer.parseInt(objStr[0].toString());
					// 根据保质期天数、保质期单位，计算失效日期
					UFDate dvalidate = calcQualityDate(SmartVODataUtils.getUFDate(strDate), unit, iQualityDayNum);
					getBillCardPanel().getBillModel().setValueAt(dvalidate, e.getRow(), "cb_dvalidate");
				} catch (Exception ex) {
					reportException(ex);
				}
			} else {
				getBillCardPanel().getBillModel().setValueAt(null, e.getRow(), "cb_dvalidate");
			}
		}
	}

	/**
   * 根据保质期换算出生产日期
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param e
   *          <p>
   * @author liyc
   * @time 2007-9-12 下午02:39:52
   */
	private void afterEditWhenBodydvalidatedate(BillEditEvent e) {
		if (vQualityMan.size() != 0 && vQualityMan.get(0) != null && ((String) vQualityMan.get(0)).equals("Y")) {
			Object dproducedate = getBillCardPanel().getBodyValueAt(e.getRow(), "dproducedate");
			if (dproducedate == null) {
				try {
					//modify by hanbin 2010-12-13 原因：（NCdp202857184）存货管理档案启用批次管理、保质期管理，存货保质期单位为月。若保质期时间大于1年后，则质量失效期每年相差5天。
					//说明：代码主要是从维护开发部的补丁中抽取的。
					String strCmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();
					if (StringUtils.isEmpty(strCmangid))
						return;
					Object Ddvalidate = getBillCardPanel().getBodyValueAt(e.getRow(), "cb_dvalidate");
					if(Ddvalidate==null)return;
					// UFDate Ddproducedate = new UFDate(Ddvalidate == null ? null :
					// Ddvalidate.toString())
					// .getDateBefore(((Integer) vQualityMan.get(1)));
					Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
							"qualitydaynum", strCmangid);
					if(objStr==null || objStr.length==0)return;
					int iQualityDayNum = Integer.parseInt(objStr[0].toString());
					objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
							"qualityperiodunit", strCmangid);
					if(objStr==null || objStr.length==0)return;
					int unit = Integer.parseInt(objStr[0].toString());
					UFDate Ddproducedate = calcQualityScrqDate(SmartVODataUtils.getUFDate(Ddvalidate), unit,
							iQualityDayNum);
					getBillCardPanel().getBillModel().setValueAt(Ddproducedate, e.getRow(), "dproducedate");
				} catch (Exception ex) {
					reportException(ex);
				}
			}
		}
	}

	/**
   * 仓库编辑后事件
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param event
   *          <p>
   * @author liyc
   * @time 2007-9-24 下午02:23:49
   */
	private void afterEditWhenBodyCwareHouse(BillEditEvent event) {
		UIRefPane bRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cwarehouseid").getComponent();
		Object[] strcstorgname = null;
		try {
			strcstorgname = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_stordoc", "pk_stordoc", "pk_calbody",
					bRefPanel.getRefPK());
		} catch (Exception e) {
			SCMEnv.out(e);
		}
		if (strcstorgname != null && strcstorgname.length != 0) {
			try {
				strcstorgname = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_calbody", "pk_calbody", "bodyname",
						strcstorgname[0].toString());
			} catch (Exception e) {
				SCMEnv.out(e);
			}
		}
		bRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cstoreorganization").getComponent();
		if (strcstorgname != null && strcstorgname.length != 0) {
			bRefPanel.setAutoCheck(false);
			bRefPanel.setText(strcstorgname[0].toString());
		}
	}

	private void afterEditWhenBodyCheckState(BillEditEvent e) {
		UIRefPane bRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("cb_cqualitylevelname").getComponent();
		Object[] checkname = null;
		try {
			checkname = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("qc_checkstate_b", "ccheckstate_bid",
					"ccheckstatename", bRefPanel.getRefPK());
		} catch (Exception ex) {
			SCMEnv.out(ex);
		}

		getBillCardPanel().getBillModel().setValueAt(checkname[0], e.getRow(), "cb_cqualitylevelname");

	}

	/**
   * 方法功能描述：将批次档案具体信息代到表体。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param e
   *          <p>
   * @author
   * @time 2007-7-31 下午02:50:14
   */
	private void afterEditWhenProdNum(BillEditEvent e) {
		// 供应商
		getBillCardPanel().setBodyValueAt(null, e.getRow(), "cb_wendbatchcode");
		try {
			QcTool.setCheckBillBatchCodeInfo(getBillCardPanel(), e.getRow(), (String) getBillCardPanel().getHeadItem(
					"cmangid").getValueObject(), (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(),
					getBillCardPanel().getBodyValueAt(e.getRow(), "vstobatchcode") == null ? null : getBillCardPanel()
							.getBodyValueAt(e.getRow(), "vstobatchcode").toString(), getCorpPrimaryKey());

			if (vQualityMan.size() == 0) {
				String strCmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();
				Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
						"qualitymanflag", strCmangid);
				String strQualityManFlag = objStr[0].toString();
				objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc", "qualitydaynum",
						strCmangid);
				if (objStr[0] != null) {
					int iQualityDayNum = Integer.parseInt(objStr[0].toString());
					objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
							"qualityperiodunit", strCmangid);
					if (objStr[0] != null && (Integer) objStr[0] == 0) {
						iQualityDayNum = iQualityDayNum * 365;
					} else if (objStr[0] != null && (Integer) objStr[0] == 1) {
						iQualityDayNum = iQualityDayNum * 30;
					}
					vQualityMan.add(strQualityManFlag);
					vQualityMan.add(iQualityDayNum);
				} else {
					vQualityMan.add(strQualityManFlag);
					vQualityMan.add(null);
				}
			}

		} catch (Exception ex) {
			reportException(ex);
		}
	}

	/**
   * 作者：袁野 功能：修改表体辅数量后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyAssistNum(BillEditEvent event) {
		// 如果改判存货，则必须在录入辅数量前录入改判存货和辅单位，否则无法得到换算率信息
		// 辅数量,先变主数量
		Object o1 = getBillCardPanel().getBodyValueAt(event.getRow(), "nassistnum");
		Object o2 = getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");
		if (o1 != null && o1.toString().trim().length() > 0 && o2 != null && o2.toString().trim().length() > 0) {
			UFDouble d1 = new UFDouble(o1.toString());
			UFDouble d2 = new UFDouble(o2.toString());
			getBillCardPanel().setBodyValueAt(new UFDouble(d1.doubleValue() * d2.doubleValue()), event.getRow(), "nnum");
		}
		calculateNum();

	}

	/**
   * 作者：袁野 功能：修改表体是否改判后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyChange(BillEditEvent event) {

		Object lobj_bchange = getBillCardPanel().getBodyValueAt(event.getRow(), "bchange");

		if (lobj_bchange == null || lobj_bchange.toString().trim().length() == 0) {
			return;
		}

		boolean lb_bchange = (new UFBoolean(lobj_bchange.toString())).booleanValue();

		if (lb_bchange) { // 选中是否改判
			getBillCardPanel().setCellEditable(event.getRow(), "cchginventorycode", true);
			getBillCardPanel().setCellEditable(event.getRow(), "cchgassistunitname", false);

			// 清空所有和改判相关字段
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchginventorycode");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgbaseid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgmangid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchginventoryname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgmeasdocname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitid");
			getBillCardPanel().setBodyValueAt(new UFBoolean(false), event.getRow(), "bassunitmanagedb");
			getBillCardPanel().setBodyValueAt(new UFBoolean(true), event.getRow(), "bfixedb");
			// 清空表体辅数量和换算率，因为原辅数量和换算率是和表头相关得，一但改判，将意味着和表体相关
			getBillCardPanel().setCellEditable(event.getRow(), "nassistnum", false);
			getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nassistnum");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nexchangerateb");

		} else { // 不选中是否改判
			// 改判存货和辅计量不可编辑
			getBillCardPanel().setCellEditable(event.getRow(), "cchginventorycode", false);
			getBillCardPanel().setCellEditable(event.getRow(), "cchgassistunitname", false);
			// 清空所有和改判相关字段
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchginventorycode");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgbaseid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgmangid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchginventoryname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgmeasdocname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitid");
			getBillCardPanel().setBodyValueAt(new UFBoolean(false), event.getRow(), "bassunitmanagedb");
			getBillCardPanel().setBodyValueAt(new UFBoolean(true), event.getRow(), "bfixedb");

			// 清空表体辅数量和换算率
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nassistnum");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nexchangerateb");

			// 如果表头辅计量管理存货，则表体辅数量可以编辑
			// 如果表头辅计量管理存货，且为固定换算率，则表体换算率不可编辑，且表体换算率=表头换算率
			// 如果表头辅计量管理存货，且为非固定换算率，则表体换算率可编辑
			// 如果表头非辅计量管理存货，则表体辅数量和换算率都不可以编辑

			String lstr_cassistunitid = (String) getBillCardPanel().getHeadItem("cassistunitid").getValueObject(); // 辅单位id
			String lstr_cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(); // 存货

			// baseid
			if (lstr_cbaseid != null && lstr_cbaseid.trim().length() > 0 && lstr_cassistunitid != null
					&& lstr_cassistunitid.trim().length() > 0) {
				// 表头存货辅计量非空,认为辅计量管理存货(该判断适用于各种报检类型)
				getBillCardPanel().setCellEditable(event.getRow(), "nassistnum", true);

				Object[] lobj_ConvRateInfo = nc.ui.scm.inv.InvTool.getInvConvRateInfo(lstr_cbaseid, lstr_cassistunitid);
				if (lobj_ConvRateInfo != null) {

					UFBoolean lUFB_isFixedConvRate = (UFBoolean) lobj_ConvRateInfo[1];

					String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject(); // 表头换算率
					UFDouble lUFD_ConvRate = null;
					if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
						lUFD_ConvRate = new UFDouble(lStr_ConvRate);
					}

					if (lUFB_isFixedConvRate.booleanValue()) { // 如果固定换算率,则换算率不可编辑,且表体换算率=表头换算率
						getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
						getBillCardPanel().setBodyValueAt(lUFD_ConvRate, event.getRow(), "nexchangerateb");

					} else { // 如果非固定换算率,则换算率可编辑,且默认表体换算率=表头换算率
						getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", true);
						getBillCardPanel().setBodyValueAt(lUFD_ConvRate, event.getRow(), "nexchangerateb");

					}

				}

				// 不管是否固定换算率,只要当前行上定义了主数量和换算率,则 表体辅数量 = 主数量/换算率
				Object lObj_Nnum = getBillCardPanel().getBodyValueAt(event.getRow(), "nnum");
				Object lObj_ConvRate = getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");

				if (lObj_Nnum != null && lObj_Nnum.toString().trim().length() > 0 && lObj_ConvRate != null
						&& lObj_ConvRate.toString().trim().length() > 0) {

					String lStr_Nnum = lObj_Nnum.toString().trim();
					String lStr_ConvRate = lObj_ConvRate.toString().trim();

					UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);

					if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {

						UFDouble lUFD_nassistchecknum = new UFDouble(lStr_Nnum).div(lUFD_ConvRate);
						getBillCardPanel().setBodyValueAt(lUFD_nassistchecknum, event.getRow(), "nassistnum"); // 辅数量

					} else {
						getBillCardPanel().setBodyValueAt(new UFDouble(0.00), event.getRow(), "nassistnum"); // 辅数量
					}
				}

			} else { // 表头为非辅计量管理存货
				getBillCardPanel().setCellEditable(event.getRow(), "nassistnum", false);
				getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			}

		}
		getBillCardPanel().setBodyValueAt(null, event.getRow(), "vstobatchcode");
		getBillCardPanel().setCellEditable(event.getRow(), "vstobatchcode", true);
		calculateNum();
	}

	/**
   * 作者：袁野 功能：修改表体责任部门后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyChargeDeptName(BillEditEvent event) {

		if (event.getKey().equals("cchargedeptname")) {
			// 责任部门,请除责任人,并设置责任人的参照
			UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("cchargedeptname").getComponent();
			String sDeptID = nRefPane.getRefPK();
			nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("cchargepsnname").getComponent();
			String sWhere = " bd_psndoc.pk_deptdoc = '" + sDeptID + "'";
			if (sDeptID != null && sDeptID.trim().length() > 0) {
				nRefPane.setWhereString(sWhere);
				getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchargepsnname");
				getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchargepsnid");
			} else {
				nRefPane.setWhereString("bd_deptdoc.pk_corp = '" + m_sUnitCode
						+ "' and bd_deptdoc.pk_deptdoc = bd_psndoc.pk_deptdoc");
			}
		}

	}

	/**
   * 作者：袁野 功能：修改表体检验状态后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 入库批次检查规则: 若入库批次号存在,则: 存货(改判存货)+入库批次号->质量等级唯一 若入库批次号不存在,则:
   * 存货(改判存货)+来源单据行ID->质量等级不允许重复 2007-03-15 xhq 日期：(2004-10-28 11:39:21)
   * 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyCheckStateName(BillEditEvent event) {
		int nReturn = isCheckStateDuplicated();
		int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
		if (nReturn == 1) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0003840")/*
                                                                                                               * @res
                                                                                                               * "质量等级"
                                                                                                               */, "存货+入库批次号对应的质量等级不唯一!"/*-=notranslate=-*/);
		} else if (nReturn == 2) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0003840")/*
                                                                                                               * @res
                                                                                                               * "质量等级"
                                                                                                               */, "存货+来源单据行ID对应的质量等级重复!"/*-=notranslate=-*/);
		}
		if (nReturn > 0) {
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, event.getRow(),
					"ccheckstatename");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, event.getRow(),
					"ccheckstate_bid");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(new UFBoolean(false), event.getRow(),
					"bqualified");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, event.getRow(),
					"cdefectprocessid");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, event.getRow(),
					"cdefectprocessname");
			return;
		}
		// 将表体质量等级带入批次档案质量等级
		UIRefPane bRefPanel = (UIRefPane) getBillCardPanel()
				.getBodyItem("table" + String.valueOf(index), "ccheckstatename").getComponent();
		String checkname = bRefPanel.getRefName();
		getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(checkname, event.getRow(),
				"cb_cqualitylevelname");

		// 数量,计算合格数量,不合格数量和合格率
		calculateNum();
		// 对于合格品，默认可以入库，不合格不可入库
		if (new UFBoolean(getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(event.getRow(),
				"bqualified") == null ? false : true).booleanValue()) {
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(new UFBoolean(true), event.getRow(),
					"bcheckin");
			getBillCardPanel().setCellEditable(event.getRow(), "vmemo", false, "table" + String.valueOf(index));
		} else {
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(new UFBoolean(false), event.getRow(),
					"bcheckin");
			getBillCardPanel().setCellEditable(event.getRow(), "vmemo", true, "table" + String.valueOf(index));
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, event.getRow(), "vmemo");
		}

	}

	/**
   * 作者：袁野 功能：修改表体辅单位后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyChgAssistUnitName(BillEditEvent event) {
		// 如果用户清空辅单位，则
		// 1）将换算率、辅数量等设置为空,保留主数量，同时换算率不可编辑
		Object lobj_cchgassistunitid = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgassistunitid");

		if (lobj_cchgassistunitid == null || lobj_cchgassistunitid.toString().trim().length() == 0) {
			// 清空表体辅数量和换算率
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nassistnum");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nexchangerateb");
			getBillCardPanel().setBodyValueAt(new UFBoolean(true), event.getRow(), "bfixedb");
			getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			return;
		}

		// 根据辅单位id和存货baseid 去查询换算率信息(是否固定换算率\换算率)
		Object lobj_cchgbaseid = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgbaseid");
		if (lobj_cchgbaseid == null || lobj_cchgbaseid.toString().trim().length() == 0) {
			return;
		}

		String lstr_cchgassistunitid = lobj_cchgassistunitid.toString().trim();
		String lstr_cchgbaseid = lobj_cchgbaseid.toString().trim();

		Object[] lobj_ConvRateInfo = nc.ui.scm.inv.InvTool.getInvConvRateInfo(lstr_cchgbaseid, lstr_cchgassistunitid);

		if (lobj_ConvRateInfo != null) {
			UFDouble lUFD_ConvRate = (UFDouble) lobj_ConvRateInfo[0];
			UFBoolean lUFB_isFixedConvRate = (UFBoolean) lobj_ConvRateInfo[1];
			getBillCardPanel().setBodyValueAt(lUFD_ConvRate, event.getRow(), "nexchangerateb");
			getBillCardPanel().setBodyValueAt(lUFB_isFixedConvRate, event.getRow(), "bfixedb");

			if (lUFB_isFixedConvRate.booleanValue()) { // 如果固定换算率,则换算率不可编辑
				getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			} else {
				getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", true);
			}
		}

		// 不管是否固定换算率,只要当前行上定义了主数量和换算率,则 表体辅数量 = 主数量/换算率
		Object lObj_Nnum = getBillCardPanel().getBodyValueAt(event.getRow(), "nnum");
		Object lObj_ConvRate = getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");

		if (lObj_Nnum != null && lObj_Nnum.toString().trim().length() > 0 && lObj_ConvRate != null
				&& lObj_ConvRate.toString().trim().length() > 0) {

			String lStr_Nnum = lObj_Nnum.toString().trim();
			String lStr_ConvRate = lObj_ConvRate.toString().trim();

			UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);

			if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {

				UFDouble lUFD_nassistchecknum = new UFDouble(lStr_Nnum).div(lUFD_ConvRate);
				getBillCardPanel().setBodyValueAt(lUFD_nassistchecknum, event.getRow(), "nassistnum"); // 辅数量

			} else {
				getBillCardPanel().setBodyValueAt(new UFDouble(0.00), event.getRow(), "nassistnum"); // 辅数量
			}
		}
		calculateNum();
	}

	/**
   * 作者：袁野 功能：修改表体改判存货后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyChgInventoryCode(BillEditEvent event) {

		// 如果用户将表体存货清空
		Object lobj_cchgbaseid = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgbaseid");

		if (lobj_cchgbaseid == null || lobj_cchgbaseid.toString().trim().length() == 0) {
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgbaseid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgmangid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchginventoryname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgmeasdocname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitid");
			getBillCardPanel().setBodyValueAt(new UFBoolean(false), event.getRow(), "bassunitmanagedb");
			getBillCardPanel().setBodyValueAt(new UFBoolean(true), event.getRow(), "bfixedb");

			getBillCardPanel().setCellEditable(event.getRow(), "nassistnum", false);
			getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nassistnum");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nexchangerateb");
			return;
		}

		// 改变存货，首先将辅助计量
		// 如果用户选择得存货为辅计量管理，则辅计量可编辑，否则不可编辑
		Object lobj_bassunitmanagedb = getBillCardPanel().getBodyValueAt(event.getRow(), "bassunitmanagedb");
		if (lobj_bassunitmanagedb == null || lobj_bassunitmanagedb.toString().trim().length() == 0) {
			return;
		}
		boolean lb_bassunitmanagedb = (new UFBoolean(lobj_bassunitmanagedb.toString())).booleanValue();
		if (lb_bassunitmanagedb) { // 辅计量管理存货
			getBillCardPanel().setCellEditable(event.getRow(), "cchgassistunitname", true);
			getBillCardPanel().setCellEditable(event.getRow(), "nassistnum", true);
			// 设置辅计量参照
			// 设置辅计量参照
			UIRefPane ref = (UIRefPane) getBillCardPanel().getBodyItem("cchgassistunitname").getComponent();

			String wherePart = "bd_convert.pk_invbasdoc='" + lobj_cchgbaseid.toString().trim() + "' ";
			ref.setWhereString(wherePart);
			String unionPart = " union all (";
			unionPart += "select bd_measdoc.shortname,bd_measdoc.measname,bd_invbasdoc.pk_measdoc ";
			unionPart += "from bd_invbasdoc ";
			unionPart += "left join bd_measdoc  ";
			unionPart += "on bd_invbasdoc.pk_measdoc=bd_measdoc.pk_measdoc ";
			unionPart += "where bd_invbasdoc.pk_invbasdoc='" + lobj_cchgbaseid.toString().trim() + "') ";
			ref.getRefModel().setGroupPart(unionPart);

			// 默认情况下，辅计量同主计量换算率为1.00且换算率不可编辑
			Object lObj_cchgmeasdocid = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgmeasdocid");
			Object lObj_cchgmeasdocname = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgmeasdocname");
			Object lObj_nnum = getBillCardPanel().getBodyValueAt(event.getRow(), "nnum");

			// /////////////////////////////////////
			getBillCardPanel().setBodyValueAt(lObj_cchgmeasdocname, event.getRow(), "cchgassistunitname");
			getBillCardPanel().setBodyValueAt(lObj_cchgmeasdocid, event.getRow(), "cchgassistunitid");
			getBillCardPanel().setBodyValueAt(new UFDouble(1.0000), event.getRow(), "nexchangerateb");
			getBillCardPanel().setBodyValueAt(lObj_nnum, event.getRow(), "nassistnum");
			getBillCardPanel().setBodyValueAt(new UFBoolean(true), event.getRow(), "bfixedb");
			getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			// ////////////////////////////////////////

		} else { // 非辅计量管理存货

			getBillCardPanel().setCellEditable(event.getRow(), "cchgassistunitname", false);
			getBillCardPanel().setCellEditable(event.getRow(), "nassistnum", false);

			getBillCardPanel().setCellEditable(event.getRow(), "nexchangerateb", false);
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitid");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "cchgassistunitname");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nassistnum");
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "nexchangerateb");

		}

		calculateNum();

		// add by yye begin 20060512 入库批次号是否可编辑的判断
		boolean lbol_RowBatchManaged = false;// 该行是否批次管理
		Object lobj_cchgmangid = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgmangid");
		if (lobj_cchgmangid != null) {
			lbol_RowBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(lobj_cchgmangid.toString().trim());
		}

		if (lbol_RowBatchManaged) {// 如果为批次管理存货行
			getBillCardPanel().setCellEditable(event.getRow(), "vstobatchcode", true);
		} else {
			getBillCardPanel().setCellEditable(event.getRow(), "vstobatchcode", false);
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "vstobatchcode");
		}
		// add by yye end 20060512

	}

	/**
   * 作者：袁野 功能：修改表体不合格类型后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyDefectTypeName(BillEditEvent event) {

		// 不合格类型,自动携带不合格项目
		UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("cdefecttypename").getComponent();
		String sID = nRefPane.getRefPK();
		// nRefPane =
		// (UIRefPane)getBillCardPanel().getBodyItem("ccheckitemname").getComponent();
		if (sID != null && sID.trim().length() > 0) {
			try {
				String s[] = CheckbillHelper.getCheckitemNameByDefecttype(sID);
				String ss = "";
				if (s != null && s.length > 0) {
					for (int i = 0; i < s.length; i++) {
						if (i < s.length - 1 && s[i] != null)
							ss += s[i] + ",";
						else
							ss += s[i];
					}
				}
				if (ss.length() == 0)
					ss = null;
				getBillCardPanel().setBodyValueAt(ss, event.getRow(), "ccheckitemid");
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000046")/*
                                                                                                                       * @res
                                                                                                                       * "不合格类型自动携带不合格项目"
                                                                                                                       */, e.getMessage());
				return;
			}

		} else {
			getBillCardPanel().setBodyValueAt(null, event.getRow(), "ccheckitemid");

		}

	}

	/**
   * 作者：袁野 功能：修改表体换算率后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyExchangeRateB(BillEditEvent event) {

		// 换算率,先变主数量
		Object o1 = getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");
		Object o2 = getBillCardPanel().getBodyValueAt(event.getRow(), "nnum");
		if (o1 != null && o1.toString().trim().length() > 0 && o2 != null && o2.toString().trim().length() > 0) {
			UFDouble d1 = new UFDouble(o1.toString());
			UFDouble d2 = new UFDouble(o2.toString());
			getBillCardPanel()
					.setBodyValueAt(new UFDouble(d2.doubleValue() / d1.doubleValue()), event.getRow(), "nassistnum");
		}
		calculateNum();

	}

	/**
   * 作者：袁野 功能：修改表体主数量后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyNum(BillEditEvent event) {

		// 数量,计算合格数量,不合格数量和合格率
		// Object o =
		// getBillCardPanel().getHeadItem("cassistunitid").getValue();
		// if (o == null) {
		// calculateNum();
		// return;
		// }
		// 辅计量管理
		// 如果用户选中改判，则是否固定换算率从表体取得
		// 反之，从表头取得
		Object o;
		Object lobj_bchange = getBillCardPanel().getBodyValueAt(event.getRow(), "bchange");
		if (lobj_bchange == null || lobj_bchange.toString().trim().length() == 0) {
			// calculateNum();
			// return;
			lobj_bchange = "N";
			getBillCardPanel().setBodyValueAt("N", event.getRow(), "bchange");
		}

		boolean lb_bchange = (new UFBoolean(lobj_bchange.toString().trim())).booleanValue();

		if (lb_bchange) { // 选中是否改判{
			o = getBillCardPanel().getBodyValueAt(event.getRow(), "bfixedb");
		} else {
			o = getBillCardPanel().getHeadItem("bfixed").getValueObject();
		}

		if (o != null) {
			if ((new UFBoolean(o.toString())).booleanValue()) {
				// 固定换算率,先变辅数量
				Object o1 = getBillCardPanel().getBodyValueAt(event.getRow(), "nnum");
				Object o2 = getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");
				Object o3 = getBillCardPanel().getBodyValueAt(event.getRow(), "nassistnum");
				if (o1 != null && o1.toString().trim().length() > 0 && o2 != null && o2.toString().trim().length() > 0) {
					UFDouble d1 = new UFDouble(o1.toString());
					UFDouble d2 = new UFDouble(o2.toString());
					if (Math.abs(d2.doubleValue()) > 0) {
						// 换算率不为0
						getBillCardPanel().setBodyValueAt(new UFDouble(d1.doubleValue() / d2.doubleValue()), event.getRow(),
								"nassistnum");
					} else {
						// 换算率为0,则计算换算率
						if (o3 != null && o3.toString().trim().length() > 0) {
							UFDouble d3 = new UFDouble(o3.toString());
							if (Math.abs(d3.doubleValue()) > 0) {
								getBillCardPanel().setBodyValueAt(new UFDouble(d1.doubleValue() / d3.doubleValue()), event.getRow(),
										"nexchangerateb");
							}
						}
					}
				}
			} else {

				// 固定换算率,先变辅数量
				Object o1 = getBillCardPanel().getBodyValueAt(event.getRow(), "nnum");
				Object o2 = getBillCardPanel().getBodyValueAt(event.getRow(), "nexchangerateb");
				Object o3 = getBillCardPanel().getBodyValueAt(event.getRow(), "nassistnum");
				if (o1 != null && o1.toString().trim().length() > 0 && o2 != null && o2.toString().trim().length() > 0) {
					UFDouble d1 = new UFDouble(o1.toString());
					UFDouble d2 = new UFDouble(o2.toString());
					if (Math.abs(d2.doubleValue()) > 0) {
						// 换算率不为0
						getBillCardPanel().setBodyValueAt(new UFDouble(d1.doubleValue() / d2.doubleValue()), event.getRow(),
								"nassistnum");
					} else {
						// 换算率为0,则计算换算率
						if (o3 != null && o3.toString().trim().length() > 0) {
							UFDouble d3 = new UFDouble(o3.toString());
							if (Math.abs(d3.doubleValue()) > 0) {
								getBillCardPanel().setBodyValueAt(new UFDouble(d1.doubleValue() / d3.doubleValue()), event.getRow(),
										"nexchangerateb");
							}
						}
					}
				}
			}
		}
		calculateNum();

	}

	/**
   * 作者：袁野 功能：修改表体是否合格后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenBodyQualified(BillEditEvent event) {

		// 是否合格品,计算合格数量,不合格数量和合格率
		calculateNum();

	}

	/**
   * 作者：袁野 功能：修改表头报检辅数量后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadAssistCheckNum(BillEditEvent event) {

		String lStr_nassistchecknum = (String) getBillCardPanel().getHeadItem("nassistchecknum").getValueObject(); // 辅数量

		// 如果用户清空辅数量,则主数量设置为空
		if (lStr_nassistchecknum == null || lStr_nassistchecknum.trim().length() == 0) {
			getBillCardPanel().getHeadItem("nchecknum").setValue(null); //
			return;
		}

		// baseid
		boolean lb_isAssUnitManaged = new UFBoolean((String) getBillCardPanel().getHeadItem("bassunitmanaged")
				.getValueObject()).booleanValue(); // 是否辅计量管理
		if (lb_isAssUnitManaged) {

			// 计算其他
			// 改变辅数量时,不管是否固定换算率,都改变主数量
			String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject();
			if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
				UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);
				UFDouble lUFD_nchecknum = new UFDouble(lStr_nassistchecknum).multiply(lUFD_ConvRate);
				getBillCardPanel().getHeadItem("nchecknum").setValue(lUFD_nchecknum); // 主数量
			}
		}
	}

	/**
   * 作者：袁野 功能：修改表头辅单位后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadAssistUnitId(BillEditEvent event) {

		BillItem headItem;

		String lstr_cassistunitid = (String) getBillCardPanel().getHeadItem("cassistunitid").getValueObject(); // 辅单位id
		String lstr_cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(); // 存货

		// baseid

		// 如果用户清空辅单位，则
		// 1）表头将换算率、主数量等设置为空,保留辅数量，同时换算率不可编辑
		// 2）表体将所有非改判行的辅数量和换算率清空，且不可编辑
		if (lstr_cassistunitid == null || lstr_cassistunitid.trim().length() == 0) {
			// 1）表头将换算率、主数量等设置为空,保留辅数量，同时换算率不可编辑
			getBillCardPanel().getHeadItem("nexchangerate").setValue(null);
			getBillCardPanel().getHeadItem("nchecknum").setValue(null);
			getBillCardPanel().getHeadItem("bfixed").setValue(null);
			headItem = getBillCardPanel().getHeadItem("nexchangerate");
			if (headItem.isShow())
				headItem.setEnabled(false);

			// 2）表体将所有非改判行的辅数量和换算率清空，且不可编辑
			int li_RowCount = getBillCardPanel().getRowCount();
			for (int i = 0; i < li_RowCount; i++) {
				// 判断是否为改判行
				boolean lb_bchange = false;
				Object lobj_bchange = getBillCardPanel().getBodyValueAt(i, "bchange");
				if (lobj_bchange != null && lobj_bchange.toString().trim().length() > 0) {
					lb_bchange = (new UFBoolean(lobj_bchange.toString())).booleanValue();
				}
				// 如果非改判行，则将辅数量和换算率清空，且不可编辑
				if (!lb_bchange) {
					getBillCardPanel().setCellEditable(i, "nassistnum", false);
					getBillCardPanel().setCellEditable(i, "nexchangerateb", false);
					getBillCardPanel().setBodyValueAt(null, i, "nassistnum");
					getBillCardPanel().setBodyValueAt(null, i, "nexchangerateb");

				}
			}
			return;
		}

		// ******************表头处理********************************

		// 根据辅单位id和存货baseid 去查询换算率信息(是否固定换算率\换算率)
		if (lstr_cbaseid != null && lstr_cbaseid.trim().length() > 0) {
			Object[] lobj_ConvRateInfo = nc.ui.scm.inv.InvTool.getInvConvRateInfo(lstr_cbaseid, lstr_cassistunitid);
			if (lobj_ConvRateInfo != null) {
				UFDouble lUFD_ConvRate = (UFDouble) lobj_ConvRateInfo[0];
				UFBoolean lUFB_isFixedConvRate = (UFBoolean) lobj_ConvRateInfo[1];
				getBillCardPanel().getHeadItem("nexchangerate").setValue(lUFD_ConvRate);
				getBillCardPanel().getHeadItem("bfixed").setValue(lUFB_isFixedConvRate);

				headItem = getBillCardPanel().getHeadItem("nexchangerate");
				if (headItem.isShow()) {
					if (lUFB_isFixedConvRate.booleanValue()) { // 如果固定换算率,则换算率不可编辑
						headItem.setEnabled(false);
					} else {
						headItem.setEnabled(true);
					}
				}

				// 改变辅单位时，换算率改变后，主数量不变，改辅数量
				String lStr_nchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject(); // 主数量
				if (lStr_nchecknum != null && lStr_nchecknum.trim().length() > 0) {
					UFDouble lUFD_nassistchecknum = new UFDouble(lStr_nchecknum).div(lUFD_ConvRate);
					getBillCardPanel().getHeadItem("nassistchecknum").setValue(lUFD_nassistchecknum); // 辅数量
				}

			}

		}

		// ******************表体处理********************************
		// 如果表头选择的为固定换算率辅计量，则表体将所有非改判行换算率不可编辑且为该固定换算率
		// 如果表头选择的为非固定换算率辅计量，则表体将所有非改判行换算率可编辑且表体换算率=表头换算率
		int li_RowCount = getBillCardPanel().getRowCount();

		for (int i = 0; i < li_RowCount; i++) {
			// 判断是否为改判行
			boolean lb_bchange = false;
			Object lobj_bchange = getBillCardPanel().getBodyValueAt(i, "bchange");
			if (lobj_bchange != null && lobj_bchange.toString().trim().length() > 0) {
				lb_bchange = (new UFBoolean(lobj_bchange.toString())).booleanValue();
			}

			if (!lb_bchange) { // 如果非改判行

				if (lstr_cbaseid != null && lstr_cbaseid.trim().length() > 0) {

					getBillCardPanel().setCellEditable(i, "nassistnum", true); // 辅数量可以编辑

					Object[] lobj_ConvRateInfo = nc.ui.scm.inv.InvTool.getInvConvRateInfo(lstr_cbaseid, lstr_cassistunitid);
					if (lobj_ConvRateInfo != null) {

						UFBoolean lUFB_isFixedConvRate = (UFBoolean) lobj_ConvRateInfo[1];

						String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject(); // 表头换算率
						UFDouble lUFD_ConvRate = null;
						if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
							lUFD_ConvRate = new UFDouble(lStr_ConvRate);
						}

						if (lUFB_isFixedConvRate.booleanValue()) { // 如果固定换算率,则换算率不可编辑,且表体换算率=表头换算率
							getBillCardPanel().setCellEditable(i, "nexchangerateb", false);
							getBillCardPanel().setBodyValueAt(lUFD_ConvRate, i, "nexchangerateb");

						} else { // 如果非固定换算率,则换算率可编辑,且默认表体换算率=表头换算率
							getBillCardPanel().setCellEditable(i, "nexchangerateb", true);
							getBillCardPanel().setBodyValueAt(lUFD_ConvRate, i, "nexchangerateb");

						}

					}

					// 不管是否固定换算率,只要当前行上定义了主数量和换算率,则 表体辅数量 = 主数量/换算率
					Object lObj_Nnum = getBillCardPanel().getBodyValueAt(i, "nnum");
					Object lObj_ConvRate = getBillCardPanel().getBodyValueAt(i, "nexchangerateb");

					if (lObj_Nnum != null && lObj_Nnum.toString().trim().length() > 0 && lObj_ConvRate != null
							&& lObj_ConvRate.toString().trim().length() > 0) {

						String lStr_Nnum = lObj_Nnum.toString().trim();
						String lStr_ConvRate = lObj_ConvRate.toString().trim();

						UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);

						if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {

							UFDouble lUFD_nassistchecknum = new UFDouble(lStr_Nnum).div(lUFD_ConvRate);
							getBillCardPanel().setBodyValueAt(lUFD_nassistchecknum, i, "nassistnum"); // 辅数量

						} else {
							getBillCardPanel().setBodyValueAt(new UFDouble(0.00), i, "nassistnum"); // 辅数量
						}
					}

				}
			}
		}

	}

	/**
   * 作者：袁野 功能：修改表头检验方式后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadCheckModeId(BillEditEvent event) {

		// 检验方式
		UIRefPane o = (UIRefPane) getBillCardPanel().getHeadItem("ccheckmodeid").getComponent();
		String pk = o.getRefPK();

		// 运用公式获得检验方式的属性
		FormulaParse f = new FormulaParse();
		String sExpress1 = "getColValue(qc_checkmode,bsample,ccheckmodeid,ccheckmodeid)";
		String sExpress2 = "getColValue(qc_checkmode,bratiosample,ccheckmodeid,ccheckmodeid)";
		String sExpress3 = "getColValue(qc_checkmode,nsamplenum,ccheckmodeid,ccheckmodeid)";
		String sExpress4 = "getColValue(qc_checkmode,nsamplevolume,ccheckmodeid,ccheckmodeid)";
		String sExpress5 = "getColValue(qc_checkmode,nsampleratio,ccheckmodeid,ccheckmodeid)";
		f.setExpressArray(new String[] { sExpress1, sExpress2, sExpress3, sExpress4, sExpress5 });
		VarryVO varrys[] = f.getVarryArray();

		Hashtable table = new Hashtable();
		table.put(varrys[0].getVarry()[0], new String[] { pk });

		f.setDataSArray(new Hashtable[] { table, table, table, table, table });
		String s[][] = f.getValueSArray();

		getBillCardPanel().getHeadItem("bsample").setValue(s[0][0]);
		getBillCardPanel().getHeadItem("bratiosample").setValue(s[1][0]);
		getBillCardPanel().getHeadItem("nsamplenum").setValue(s[2][0]);
		getBillCardPanel().getHeadItem("nsamplevolume").setValue(s[3][0]);
		getBillCardPanel().getHeadItem("nsampleratio").setValue(s[4][0]);
	}

	/**
   * 作者：袁野 功能：修改表头报检数量后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadCheckNum(BillEditEvent event) {

		String lstr_nchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject(); // 主数量
		boolean lb_isAssUnitManaged = new UFBoolean((String) getBillCardPanel().getHeadItem("bassunitmanaged")
				.getValueObject()).booleanValue(); // 是否辅计量管理
		String lstr_isFixedConvRate = (String) getBillCardPanel().getHeadItem("bfixed").getValueObject();

		// 如果用户清空主数量
		if (lstr_nchecknum == null || lstr_nchecknum.trim().length() == 0) {
			if (lb_isAssUnitManaged && lstr_isFixedConvRate != null && lstr_isFixedConvRate.trim().length() > 0
					&& (new UFBoolean(lstr_isFixedConvRate)).booleanValue()) {
				getBillCardPanel().getHeadItem("nassistchecknum").setValue(null); // 固定换算率,则辅数量设置为空
			} else { // 非固定换算率,则换算率设置为空
				getBillCardPanel().getHeadItem("nexchangerate").setValue(null);
			}
			return;
		}

		if (lb_isAssUnitManaged) {
			// 计算其他
			if (lstr_isFixedConvRate != null && lstr_isFixedConvRate.trim().length() > 0) {
				if ((new UFBoolean(lstr_isFixedConvRate)).booleanValue()) {
					// 固定换算率
					String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject();
					if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
						UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);
						if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {
							UFDouble lUFD_nassistchecknum = new UFDouble(lstr_nchecknum).div(lUFD_ConvRate);
							getBillCardPanel().getHeadItem("nassistchecknum").setValue(lUFD_nassistchecknum); // 辅数量
						} else {
							getBillCardPanel().getHeadItem("nassistchecknum").setValue(new UFDouble(0.00));
						}
					}
				} else { // 非固定换算率,改变主数量时，辅数量不变，改变换算率

					String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject();
					if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
						UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);
						if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {
							UFDouble lUFD_nassistchecknum = new UFDouble(lstr_nchecknum).div(lUFD_ConvRate);
							getBillCardPanel().getHeadItem("nassistchecknum").setValue(lUFD_nassistchecknum); // 辅数量
						} else {
							getBillCardPanel().getHeadItem("nassistchecknum").setValue(new UFDouble(0.00));
						}
					}

				}

			}
		}

	}

	/**
   * 作者：袁野 功能：修改表头检验标准后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadCheckStandardId(BillEditEvent event) {

		// 检验标准，自动显示检验状态组,执行标准,并设置检验状态参照
		UIRefPane o = (UIRefPane) getBillCardPanel().getHeadItem("ccheckstandardid").getComponent();
		String pk = o.getRefPK();

		// 运用公式获得检验状态组,执行标准
		FormulaParse f = new FormulaParse();
		String sExpress1 = "getColValue(qc_checkstandard,cexecstandard,ccheckstandardid,ccheckstandardid)";
		String sExpress2 = "getColValue(qc_checkstandard,ccheckstateid,ccheckstandardid,ccheckstandardid)";
		f.setExpressArray(new String[] { sExpress1, sExpress2 });
		VarryVO varrys[] = f.getVarryArray();

		Hashtable table1 = new Hashtable();
		table1.put(varrys[0].getVarry()[0], new String[] { pk });
		Hashtable table2 = new Hashtable();
		table2.put(varrys[0].getVarry()[0], new String[] { pk });

		f.setDataSArray(new Hashtable[] { table1, table2 });
		String s[][] = f.getValueSArray();

		getBillCardPanel().getHeadItem("cexecstandard").setValue(s[0][0]);
		getBillCardPanel().getHeadItem("ccheckstateid").setValue(s[1][0]);

		sExpress1 = "getColValue(qc_checkstate,ccheckstategroupname,ccheckstateid,ccheckstateid)";
		f.setExpress(sExpress1);
		VarryVO varry = f.getVarry();
		table1 = new Hashtable();
		table1.put(varry.getVarry()[0], new String[] { s[1][0] });
		f.setDataS(table1);
		String ss[] = f.getValueS();
		UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getHeadItem("ccheckstateid").getComponent();
		nRefPane.getUITextField().setText(ss[0]);

		// 根据检验状态组设置检验状态参照
		nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("ccheckstatename").getComponent();
		if (s[1][0] != null && s[1][0].trim().length() > 0) {
			String sWhere = " ccheckstateid = '" + s[1][0] + "' ";
			nRefPane.getRefModel().setWherePart(sWhere);
		} else {
			String sWhere = " pk_corp = '" + m_sUnitCode + "' and dr = 0 ";
			nRefPane.getRefModel().setWherePart(sWhere);
		}

		// 清除表体的检验状态
		int nRow = getBillCardPanel().getRowCount();
		for (int i = 0; i < nRow; i++) {
			getBillCardPanel().setBodyValueAt(null, i, "ccheckstatename");
			getBillCardPanel().setBodyValueAt(null, i, "ccheckstate_bid");
		}

		// 检验标准改变，清除检验信息
		if (pk != null && pk.trim().length() > 0 && m_grandVOs != null && m_grandVOs.length > 0) {
			if (MessageDialog.showYesNoDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("C0020101", "UPPC0020101-000048")/* @res "检验标准改变" */, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("C0020101", "UPPC0020101-000049")/* @res "清除检验信息?" */) == MessageDialog.ID_YES) {
				if (m_grandVOs == null) {

				} else {
					Vector vData = new Vector();
					for (int i = 0; i < m_grandVOs.length; i++) {
						for (int j = 0; j < m_grandVOs[i].getChildrenVO().length; j++) {
							vData.add(m_grandVOs[i].getChildrenVO()[j]);
						}
					}
					CheckbillB2ItemVO[] tempVOs = new CheckbillB2ItemVO[vData.size()];
					vData.copyInto(tempVOs);
					m_hDelInfo.put(m_checkBillVOs[0].getHeadVo().getCcheckbillid(), tempVOs);
					m_grandVOs = null;
					m_checkBillVOs[0].setGrandVO(null);
				}
			}
		}

	}

	/**
   * 作者：袁野 功能：修改表头检验状态组后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadCheckStateId(BillEditEvent event) {

		// 检验状态组
		// 根据检验状态组设置检验状态参照
		UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getHeadItem("ccheckstateid").getComponent();
		String s = nRefPane.getRefPK();
		nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("ccheckstatename").getComponent();
		if (s != null && s.trim().length() > 0) {
			String sWhere = " ccheckstateid = '" + s + "' ";
			nRefPane.getRefModel().setWherePart(sWhere);
			// 清除检验结果的检验状态
			for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
				getBillCardPanel().setBodyValueAt(new UFBoolean(false), i, "bqualified");
				getBillCardPanel().setBodyValueAt(null, i, "ccheckstate_bid");
				getBillCardPanel().setBodyValueAt(null, i, "ccheckstatename");
				getBillCardPanel().setBodyValueAt(null, i, "cdefectprocessid");
				getBillCardPanel().setBodyValueAt(null, i, "cdefectprocessname");
			}
		} else {
			String sWhere = " pk_corp = '" + m_sUnitCode + "' and dr = 0 ";
			nRefPane.getRefModel().setWherePart(sWhere);
		}
	}

	/**
   * 作者：袁野 功能：修改表头换算率后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadExchangeRate(BillEditEvent event) {

		String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject(); // 换算率

		// 如果用户清空换算率，则将主数量等设置为空,保留辅数量
		if (lStr_ConvRate == null || lStr_ConvRate.trim().length() == 0) {
			getBillCardPanel().getHeadItem("nchecknum").setValue(null);
			return;
		}

		//
		UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);
		String lStr_nassistchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject(); // 辅数量
		if (lStr_nassistchecknum != null && lStr_nassistchecknum.trim().length() > 0) {
			UFDouble lUFD_nchecknum = new UFDouble(lStr_nassistchecknum).div(lUFD_ConvRate);
			getBillCardPanel().getHeadItem("nassistchecknum").setValue(lUFD_nchecknum); // 主数量
		}

	}

	/**
   * 作者：袁野 功能：修改表头存货后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenHeadInventoryCode(BillEditEvent event) {
		String strVendorbaseid = null;
		BillItem headItem;

		UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem("cinventorycode").getComponent();

		// 获得存货管理ID,存货编码及名称
		String cmangid = refPane.getRefPK();
		String cinventorycode = refPane.getRefCode();
		String cinventoryname = refPane.getRefName();

		refPane.setValue(cinventorycode);
		getBillCardPanel().getHeadItem("cmangid").setValue(cmangid);
		// getBillCardPanel().getHeadItem("cinventorycode").setValue(cinventorycode);
		getBillCardPanel().getHeadItem("cinventoryname").setValue(cinventoryname);
		// 取供应商值
		refPane = (UIRefPane) getBillCardPanel().getHeadItem("cvendormangid").getComponent();
		String strVendormangid = refPane.getRefPK();
		if (strVendormangid != null) {
			Object[] obName = null;
			try {
				obName = (Object[]) CacheTool.getCellValue("bd_cumandoc", "pk_cumandoc", "pk_cubasdoc", strVendormangid);
			} catch (Exception e) {
				SCMEnv.out(e);
				QcTool.outException(this, e);
			}
			strVendorbaseid = obName[0].toString();
		}
		// 获得存货基础ID，规格，型号，主计量，检验方式ID，检验标准ID
		CheckbillHeaderVO headVO = new CheckbillHeaderVO();
		getBillCardPanel().getBillData().getHeaderValueVO(headVO);
		ArrayList list = null;
		try {
			list = CheckbillHelper.queryInvRelateInfo(new String[] { cmangid }, null, m_dialog.getChecktypeid(), m_sUnitCode,
					new String[] { strVendorbaseid });
			if (list == null || list.size() == 0) {
				BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
				BillTempletBodyVO[] initBodyVO = tmpletvo.getBodyVO();
				BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
				BillData newbd = new BillData(initVO);
				m_cardBill.setBillData(newbd);
				m_cardBill.setAutoExecHeadEditFormula(true);
				m_cardBill.addEditListener(this);
				m_cardBill.setEnabled(true);
				m_cardBill.updateUI();
				setAddEditState();
				sCheckStandards = null;
				return;
			}

			getBillCardPanel().getHeadItem("cbaseid").setValue(((Vector) list.get(0)).get(0));
			getBillCardPanel().getHeadItem("cinventoryspec").setValue(((Vector) list.get(1)).get(0));
			getBillCardPanel().getHeadItem("cinventorytype").setValue(((Vector) list.get(2)).get(0));
			getBillCardPanel().getHeadItem("cinventoryunit").setValue(((Vector) list.get(3)).get(0));

			if (list.size() < 5 || ((Vector) list.get(4)).size() == 0 || ((Vector) list.get(4)).get(0) == null) {
				BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
				BillTempletBodyVO[] initBodyVO = tmpletvo.getBodyVO();
				BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
				BillData newbd = new BillData(initVO);

				m_cardBill.setBillData(newbd);
				m_cardBill.setAutoExecHeadEditFormula(true);
				// 刷界面时重新进行自定义项转换
				nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef(m_cardBill, getClientEnvironment().getCorporation()
						.getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdef", "vdef");
				//
				m_cardBill.addEditListener(this);
				getBillCardPanel().getBillData().setHeaderValueVO(headVO);
				m_cardBill.setEnabled(true);
				m_cardBill.updateUI();
				setAddEditState();
				sCheckStandards = null;
				return;
			}
			int iSize = ((Vector) (((Vector) list.get(4)).get(0))).size() / 4;
			String[][] cMode = new String[1][];
			String[][] cStandard = new String[1][];
			String[][] cDefault = new String[1][];
			String[][] cState = new String[1][];
			Vector vTempData = (Vector) (((Vector) list.get(4)).get(0));
			cMode[0] = new String[iSize];
			cStandard[0] = new String[iSize];
			cDefault[0] = new String[iSize];
			cState[0] = new String[iSize];
			for (int j = 0; j < iSize; j++) {
				cStandard[0][j] = vTempData.get(0 + j * 4).toString();
				cMode[0][j] = vTempData.get(1 + j * 4).toString();
				cDefault[0][j] = vTempData.get(2 + j * 4).toString();
				cState[0][j] = vTempData.get(3 + j * 4).toString();
			}
			headVO.setCcheckmodeid(cMode[0][0]);
			// getBillCardPanel().getHeadItem("ccheckmodeid").setValue(cMode[0][0]);
			String[] strStandardids = new String[cStandard[0].length];
			sCheckState = new String[cState[0].length];
			for (int i = 0; i < strStandardids.length; i++) {
				strStandardids[i] = cStandard[0][i];
			}
			for (int i = 0; i < strStandardids.length; i++) {
				sCheckState[i] = cState[0][i];
			}
			initMultiStandardPanel(strStandardids);

		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000050")/*
                                                                                                                     * @res
                                                                                                                     * "根据存货获得检验标准/方式"
                                                                                                                     */, e.getMessage());
			return;
		}
		getBillCardPanel().setEnabled(true);
		// 设置编辑状态
		setAddEditState();

		getBillCardPanel().getBillData().setHeaderValueVO(headVO);
		getBillCardPanel().getHeadItem("cbaseid").setValue(((Vector) list.get(0)).get(0));
		getBillCardPanel().getHeadItem("cinventoryspec").setValue(((Vector) list.get(1)).get(0));
		getBillCardPanel().getHeadItem("cinventorytype").setValue(((Vector) list.get(2)).get(0));
		getBillCardPanel().getHeadItem("cinventoryunit").setValue(((Vector) list.get(3)).get(0));

		getBillCardPanel().getHeadItem("cassistunitid").setValue(null);
		getBillCardPanel().getHeadItem("nexchangerate").setValue(null);
		boolean lb_isAssUnitManaged = nc.ui.scm.inv.InvTool.isAssUnitManaged(list.get(0) == null ? null
				: (String) ((Vector) list.get(0)).get(0));
		getBillCardPanel().getHeadItem("bassunitmanaged").setValue(new UFBoolean(lb_isAssUnitManaged));

		if (lb_isAssUnitManaged) {
			// 是否可编辑
			headItem = getBillCardPanel().getHeadItem("nassistchecknum");
			if (headItem.isShow())
				headItem.setEnabled(true);
			headItem = getBillCardPanel().getHeadItem("cassistunitid");
			if (headItem.isShow())
				headItem.setEnabled(true);
			headItem = getBillCardPanel().getHeadItem("nexchangerate");
			if (headItem.isShow())
				headItem.setEnabled(false);

			// 设置辅计量参照
			UIRefPane ref = (UIRefPane) getBillCardPanel().getHeadItem("cassistunitid").getComponent();
			String wherePart = "bd_convert.pk_invbasdoc='" + ((Vector) list.get(0)).get(0).toString() + "' ";
			ref.setWhereString(wherePart);
			String unionPart = " union all (";
			unionPart += "select bd_measdoc.shortname,bd_measdoc.measname,bd_invbasdoc.pk_measdoc ";
			unionPart += "from bd_invbasdoc ";
			unionPart += "left join bd_measdoc  ";
			unionPart += "on bd_invbasdoc.pk_measdoc=bd_measdoc.pk_measdoc ";
			unionPart += "where bd_invbasdoc.pk_invbasdoc='" + ((Vector) list.get(0)).get(0).toString() + "') ";
			ref.getRefModel().setGroupPart(unionPart);

			// 默认情况下，辅计量同主计量换算率为1.00且换算率不可编辑
			String lstr_MianUnitid = nc.ui.scm.inv.InvTool.getMainUnit(((Vector) list.get(0)).get(0).toString());
			getBillCardPanel().getHeadItem("cassistunitid").setValue(lstr_MianUnitid);
			getBillCardPanel().getHeadItem("nexchangerate").setValue(new UFDouble(1.0000));
			getBillCardPanel().getHeadItem("bfixed").setValue(new UFBoolean(true));

			// 如果存在辅数量，则用辅数量写主数量（辅数量优先）
			String lStr_nassistchecknum = (String) getBillCardPanel().getHeadItem("nassistchecknum").getValueObject(); // 辅数量
			if (lStr_nassistchecknum != null && lStr_nassistchecknum.trim().length() > 0) {
				getBillCardPanel().getHeadItem("nchecknum").setValue(lStr_nassistchecknum); // 主数量
			} else { // 如果不存在辅数量，则用主数量写辅数量
				String lStr_nchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject();
				if (lStr_nchecknum != null && lStr_nchecknum.trim().length() > 0) {
					getBillCardPanel().getHeadItem("nassistchecknum").setValue(lStr_nchecknum); //

				}
			}

		} else {
			// 是否可编辑
			headItem = getBillCardPanel().getHeadItem("nassistchecknum");
			headItem.setValue(null);
			if (headItem.isShow())
				headItem.setEnabled(false);
			headItem = getBillCardPanel().getHeadItem("cassistunitid");
			if (headItem.isShow())
				headItem.setEnabled(false);
			headItem = getBillCardPanel().getHeadItem("nexchangerate");
			if (headItem.isShow())
				headItem.setEnabled(false);

		}

		// 表头换存货表体全部删除,等待用户重新增行
		int rowCount = getBillCardPanel().getBillModel().getRowCount();
		int[] lines = new int[rowCount];
		for (int i = 0; i < rowCount; i++) {
			lines[i] = i;
		}

		getBillCardPanel().getBillModel().delLine(lines);
		// getBillCardPanel().getBillModel().clearBodyData();

		if (!m_dialog.getCheckbilltypecode().trim().equals("WH")) {
			headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
			if (headItem.isShow())
				headItem.setEnabled(false);
		} else {
			headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
			boolean lb_isBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(cmangid);
			if (lb_isBatchManaged) {
				if (headItem.isShow())
					headItem.setEnabled(true);
			} else {
				if (headItem.isShow())
					headItem.setEnabled(false);
			}
			headItem.setValue(null);
		}

		// 设置自由项自定义参照
		BillItem item = getBillCardPanel().getHeadItem("vfreeitem");
		if (item != null || (item.isShow())) {
			try {
				InvVO invVO = CheckbillHelper.setFreeItem(((Vector) list.get(0)).get(0).toString());
				invVO.setCinventoryid(((Vector) list.get(0)).get(0).toString());
				invVO.setCinventorycode(cinventorycode);
				invVO.setInvname(cinventoryname);
				invVO.setInvspec(((Vector) list.get(1)).get(0) == null ? null : ((Vector) list.get(1)).get(0).toString());
				invVO.setInvtype(((Vector) list.get(2)).get(0) == null ? null : ((Vector) list.get(2)).get(0).toString());
				FreeItemRefPane freeRef = new FreeItemRefPane();
				freeRef.setFreeItemParam(invVO);
				getBillCardPanel().getHeadItem("vfreeitem").setComponent(freeRef);
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000051")/*
                                                                                                                       * @res
                                                                                                                       * "根据存货设置自由项"
                                                                                                                       */, e.getMessage());
				return;
			}
		}

		getBillCardPanel().updateUI();
		for (int i = 0; i < sCheckStandards.length; i++) {
			getBillCardPanel().addLine("table" + String.valueOf(i));
			onCardAddLine(i);
		}

	}

	/**
   * 作者：袁野 功能：修改信息表体检验项目后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenInfoBodyCheckItemName(BillEditEvent event) {

		try {

			getBillCardPanelInfo().setBodyValueAt(null, event.getRow(), "cresult");
			getBillCardPanelInfo().setBodyValueAt(null, event.getRow(), "ccheckresult");

			// 检验项目（检验项目不可重复）
			Object oTemp = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckitemname");
			if (oTemp == null || oTemp.toString().trim().length() == 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0002807")/*
                                                                                                                 * @res
                                                                                                                 * "检验项目"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000053")/*
                                                                                           * @res
                                                                                           * "检验项目不可为空！"
                                                                                           */);
				return;
			}
			String s = oTemp.toString().trim();
			boolean b = false;
			int nRow = getBillCardPanelInfo().getRowCount();
			for (int i = 0; i < nRow; i++) {
				if (i != event.getRow()) {
					oTemp = getBillCardPanelInfo().getBodyValueAt(i, "ccheckitemname");
					String ss = null;
					if (oTemp != null && oTemp.toString().trim().length() > 0) {
						ss = oTemp.toString().trim();
					} else
						continue;
					if (s.equals(ss)) {
						b = true;
						break;
					}
				}
			}
			if (b) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0002807")/*
                                                                                                                 * @res
                                                                                                                 * "检验项目"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000054")/*
                                                                                           * @res
                                                                                           * "检验项目不可重复！"
                                                                                           */);
				getBillCardPanelInfo().setBodyValueAt(null, event.getRow(), "ccheckitemname");
				getBillCardPanelInfo().setBodyValueAt(null, event.getRow(), "ccheckitemid");
				return;
			}
			getBillCardPanelInfo().setBodyValueAt(null, event.getRow(), "icheckstandard");

			// 运用公式获得检验单位
			UIRefPane o = (UIRefPane) getBillCardPanelInfo().getBodyItem("ccheckitemname").getComponent();
			String pk = o.getRefPK();
			FormulaParse f = new FormulaParse();
			String sExpress = "getColValue(qc_checkitem,ccheckunitid,ccheckitemid,ccheckitemid)";
			f.setExpress(sExpress);
			VarryVO varry = f.getVarry();
			Hashtable table = new Hashtable();
			table.put(varry.getVarry()[0], new String[] { pk });
			f.setDataS(table);
			String ss1[] = f.getValueS();

			sExpress = "getColValue(bd_measdoc,measname,pk_measdoc,ccheckunitid)";
			f.setExpress(sExpress);
			varry = f.getVarry();
			table = new Hashtable();
			table.put(varry.getVarry()[0], ss1);
			f.setDataS(table);
			String ss2[] = f.getValueS();

			getBillCardPanelInfo().setBodyValueAt(ss1[0], event.getRow(), "ccheckunitid");
			if (ss2[0] != null && !ss2[0].trim().equals(""))
				getBillCardPanelInfo().setBodyValueAt(ss2[0], event.getRow(), "ccheckunitname");

			/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */
			if (getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckunitname") == null) {
				getBillCardPanelInfo().setBodyValueAt(getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckunitid"),
						event.getRow(), "ccheckunitname");
			}
			/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */

			// 修改检验项目后,取检验标准
			// UIRefPane lobj_temp2 = (UIRefPane)
			// getBillCardPanel().getHeadItem(
			// "ccheckstandardid").getComponent();
			String lstr_checkstandardid = (String) getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckstandardid");

			if (lstr_checkstandardid != null && lstr_checkstandardid.trim().length() > 0) {
				lstr_checkstandardid = lstr_checkstandardid.trim();

				CheckstandardItemVO[] l_CheckstandardItemVO = null;
				l_CheckstandardItemVO = CheckstandardHelper.queryItems(lstr_checkstandardid);

				if (l_CheckstandardItemVO != null && l_CheckstandardItemVO.length > 0) {
					Object lobj_temp3 = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckitemid");

					if (lobj_temp3 != null && lobj_temp3.toString().length() > 0) {
						String lstr_checkitemid = lobj_temp3.toString().trim();

						for (int i = 0; i < l_CheckstandardItemVO.length; i++) {

							if (!lstr_checkitemid.equals(l_CheckstandardItemVO[i].getCcheckitemid())) {
								getBillCardPanelInfo().setBodyValueAt(l_CheckstandardItemVO[i].getCcheckstandard_bid(), event.getRow(),
										"ccheckstandardid");
								getBillCardPanelInfo().setBodyValueAt(l_CheckstandardItemVO[i].getCstandardvalue(), event.getRow(),
										"cstandardvalue");
								getBillCardPanelInfo().setBodyValueAt(l_CheckstandardItemVO[i].getIstandardtype(), event.getRow(),
										"istandardtype");
								break;

							}

						}
					}

				}
			} else {
				String strCheckitemid = (String) getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckitemid");
				CheckstandardItemVO[] l_CheckstandardItemVO = null;
				l_CheckstandardItemVO = CheckstandardHelper.queryItemsByCheckitemid(strCheckitemid);
				if (l_CheckstandardItemVO != null && l_CheckstandardItemVO.length > 0) {
					for (int i = 0; i < l_CheckstandardItemVO.length; i++) {
						getBillCardPanelInfo().setBodyValueAt(l_CheckstandardItemVO[i].getCcheckstandard_bid(), event.getRow(),
								"ccheckstandardid");
						getBillCardPanelInfo().setBodyValueAt(l_CheckstandardItemVO[i].getCstandardvalue(), event.getRow(),
								"cstandardvalue");
						if (l_CheckstandardItemVO[i].getIstandardtype() == 0) {
							getBillCardPanelInfo().setBodyValueAt("数值型", event.getRow(), "icheckstandard");/*-=notranslate=-*/
						}
						if (l_CheckstandardItemVO[i].getIstandardtype() == 1) {
							getBillCardPanelInfo().setBodyValueAt("枚举型", event.getRow(), "icheckstandard");/*-=notranslate=-*/
						}
						break;
					}
				}
			}
			String strCheckitemid = (String) getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckitemid");
			if (hPowerCheckitemID.containsKey(strCheckitemid)) {
				getBillCardPanelInfo().setBodyValueAt(true, event.getRow(), "bdefault");
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			return;
		}

	}

	/**
   * 作者：袁野 功能：修改信息表体实际检验值后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
   * 日期：(2004-10-28 11:39:21) 修改日期，修改人，修改原因，注释标志：
   */
	public void afterEditWhenInfoBodyResult(BillEditEvent event) {

		// 2010/01/31 tianft 设置检验员为当前登录人员对应的操作员
		setDefaultCurChecker(event);

		// 自动判断检验结果，自动设置"是否达标"
		autoJudgeCheckResult(event);
	}

	/**
   * 
   * 设置检验员为当前登录人员对应的操作员
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param event
   *          <p>
   * @author tianft
   * @time 2010-1-31 下午07:12:06
   */
	private void setDefaultCurChecker(BillEditEvent event) {
		PsndocVO voPsnDoc = QCTool.getPsnByUser(getClientEnvironment().getUser().getPrimaryKey(), getPk_corp());
		Object psn = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckerid");
		if (voPsnDoc != null && psn == null) {
			getBillCardPanelInfo().setBodyValueAt(voPsnDoc.getPk_psndoc(), event.getRow(), "ccheckerid");
			getBillCardPanelInfo().getBillModel().execLoadFormula();
			getBillCardPanelInfo().updateUI();
		}

	}

	/**
   * 此处插入方法说明。 功能描述:卡片单据行变化，调用该方法 输入参数: 返回值: 异常处理: 日期:2002/04/18
   */
	public void bodyRowChange(BillEditEvent event) {
		if (m_bMMQuery) {
			if (m_nUIStateMMQuery == 0 && (UITable) event.getSource() == getBillListPanelMMQuery().getHeadTable()) {
				// 列表头换行响应
				m_nBillRecordForMMQuery = event.getRow();

				// 刷新界面
				CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) m_checkBillVOsForMMQuery[m_nBillRecordForMMQuery]
						.getChildrenVO();
				HashMap<String, String> hStandard = new HashMap<String, String>();
				for (int j = bodyVO.length, i = 0; i < j; i++) {
					String sCheckStandard = bodyVO[i].getCcheckstandardid();
					if (!hStandard.containsKey(sCheckStandard)) {
						hStandard.put(sCheckStandard, sCheckStandard);
					}
				}
				sCheckStandards = hStandard.values().toArray(new String[hStandard.size()]);
				Vector vecBody = new Vector();
				for (int i = 0; i < bodyVO.length; i++) {
					if (bodyVO[i].getCcheckstandardid().equals(sCheckStandards[0])) {
						vecBody.add(bodyVO[i]);
					}
				}
				CheckbillItemVO[] voaBody = new CheckbillItemVO[vecBody.size()];
				vecBody.copyInto(voaBody);
				getBillListPanelMMQuery().getBodyBillModel().setBodyDataVO(voaBody);
				getBillListPanelMMQuery().getBodyBillModel().execLoadFormula();
				getBillListPanelMMQuery().getBodyBillModel().updateValue();
				getBillListPanelMMQuery().updateUI();

				// add by yy begin取得单据行号信息
				Vector lvec_SourceBillTypeCode = new Vector();
				Vector lvec_SourceBillHID = new Vector();
				Vector lvec_SourceBillBID = new Vector();

				CheckbillItemVO l_CheckbillItemVO[] = bodyVO;
				if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
					for (int j = 0; j < l_CheckbillItemVO.length; j++) {
						if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null
								&& l_CheckbillItemVO[j].getCsourcebillid() != null
								&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
							lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
							lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
							lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
						}
					}
				}

				String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
				String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
				String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

				lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
				lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
				lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

				SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
				m_HTSourcebillInfoForMMQuery = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode,
						lstrary_SourceBillHID, lstrary_SourceBillBID);

				for (int i = 0; i < getBillListPanelMMQuery().getBodyBillModel().getRowCount(); i++) {
					if (getBillListPanelMMQuery().getBodyBillModel().getValueAt(i, "csourcebillrowid") != null
							&& !getBillListPanelMMQuery().getBodyBillModel().getValueAt(i, "csourcebillrowid").toString().trim()
									.equals("")) {
						String ls_csourcebillrowid = getBillListPanelMMQuery().getBodyBillModel().getValueAt(i, "csourcebillrowid")
								.toString().trim();
						if (m_HTSourcebillInfoForMMQuery.containsKey(ls_csourcebillrowid)) {
							String[] l_strary = (String[]) m_HTSourcebillInfoForMMQuery.get(ls_csourcebillrowid);
							getBillListPanelMMQuery().getBodyBillModel().setValueAt(l_strary[0], i, "csourcebilltypename");
							getBillListPanelMMQuery().getBodyBillModel().setValueAt(l_strary[2], i, "csourcebillrowno");
						}
					}
				}
				// add by yy end

			}

			return;
		}
		if (getBillListPanel() != null && (UITable) event.getSource() == getBillListPanel().getHeadTable()) {
			// 列表头换行响应
			m_nBillRecord = event.getRow();

			if (m_nBillRecord < 0)
				return;
			try {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
				strCheckbillCode = headVO.getVcheckbillcode();
				String chooseDataPower = null;
				chooseDataPower = getCheckItemDataPower();
				ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs(),
						chooseDataPower);
				if (list == null || list.size() == 0)
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                     * @res
                                                                                                                     * "并发操作，请刷新！"
                                                                                                                     */);
				if (list != null && list.size() > 0) {
					// 有返回结果
					CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
					Vector orderData = new Vector();
					for (int i = 0; i < bodyVO.length; i++) {
						if (bodyVO[i].getInorder() != null && bodyVO[i].getInorder().intValue() == 0) {
							orderData.add(0, bodyVO[i]);
						} else {
							// 重新过滤主检验标准
							orderData.add(bodyVO[i]);
						}
					}
					CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
					orderData.copyInto(vos);
					m_checkBillVOs[m_nBillRecord].setChildrenVO(vos);
					orderData.clear();
					CheckbillB2VO grandVO[] = null;
					// 设置来源信息
					if (bodyVO != null && bodyVO.length > 0) {
						headVO.setCsource(bodyVO[0].getCsource());
						// 设置检验单头的工序信息
						// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
						// 设置自由项
						headVO.setVfree1(bodyVO[0].getVfree1());
						headVO.setVfree2(bodyVO[0].getVfree2());
						headVO.setVfree3(bodyVO[0].getVfree3());
						headVO.setVfree4(bodyVO[0].getVfree4());
						headVO.setVfree5(bodyVO[0].getVfree5());
						nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
						freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
					}

					Object o = list.get(1);
					if (o != null) {
						grandVO = (CheckbillB2VO[]) o;
						m_grandVOs = grandVO;
					} else {
						m_grandVOs = null;
					}

					m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
					m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);
					HashMap<String, String> hStandard = new HashMap<String, String>();
					for (int j = bodyVO.length, i = 0; i < j; i++) {
						String sCheckStandard = bodyVO[i].getCcheckstandardid();
						if (!hStandard.containsKey(sCheckStandard)) {
							hStandard.put(sCheckStandard, sCheckStandard);
						}
					}
					sCheckStandards = hStandard.values().toArray(new String[hStandard.size()]);
					Vector vecBody = new Vector();
					for (int i = 0; i < bodyVO.length; i++) {
						if (bodyVO[i].getInorder().intValue() == 0) {
							vecBody.add(bodyVO[i]);
						}
					}
					CheckbillItemVO[] voaBody = new CheckbillItemVO[vecBody.size()];
					vecBody.copyInto(voaBody);
					if (voaBody != null && voaBody.length > 0) {
						for (int j = 0; j < voaBody.length; j++) {
							Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
									"qualitymanflag", m_checkBillVOs[m_nBillRecord].getHeadVo().getCmangid());
							objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
									"qualitydaynum", m_checkBillVOs[0].getHeadVo().getCmangid());
							if (objStr[0] != null) {
								int iQualityDayNum = Integer.parseInt(objStr[0].toString());
								objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
										"qualityperiodunit", m_checkBillVOs[0].getHeadVo().getCmangid());
								if (objStr[0] != null && (Integer) objStr[0] == 0) {
									iQualityDayNum = iQualityDayNum * 365;
								} else if (objStr[0] != null && (Integer) objStr[0] == 1) {
									iQualityDayNum = iQualityDayNum * 30;
								}

								Object strDate = voaBody[j].getDproducedate();
								if (strDate != null) {
									UFDate dvalidate = new UFDate(strDate.toString()).getDateAfter(iQualityDayNum);
									voaBody[j].setAttributeValue("cb_dvalidate", dvalidate);
								}
							}
						}
					}
					getBillListPanel().getBodyBillModel().setBodyDataVO(voaBody);
					getBillListPanel().getBodyBillModel().execLoadFormula();
					getBillListPanel().getBodyBillModel().updateValue();
					getBillListPanel().updateUI();

					// add by yy begin取得单据行号信息
					Vector lvec_SourceBillTypeCode = new Vector();
					Vector lvec_SourceBillHID = new Vector();
					Vector lvec_SourceBillBID = new Vector();

					CheckbillItemVO l_CheckbillItemVO[] = bodyVO;
					if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
						for (int j = 0; j < l_CheckbillItemVO.length; j++) {
							if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null
									&& l_CheckbillItemVO[j].getCsourcebillid() != null
									&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
								lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
								lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
								lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
							}
						}
					}

					String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
					String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
					String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

					lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
					lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
					lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

					SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
					m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode,
							lstrary_SourceBillHID, lstrary_SourceBillBID);

					for (int i = 0; i < getBillListPanel().getBodyBillModel().getRowCount(); i++) {
						if (getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid") != null
								&& !getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid").toString().trim()
										.equals("")) {
							String ls_csourcebillrowid = getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid")
									.toString().trim();
							if (m_HTSourcebillInfo.containsKey(ls_csourcebillrowid)) {
								String[] l_strary = (String[]) m_HTSourcebillInfo.get(ls_csourcebillrowid);
								getBillListPanel().getBodyBillModel().setValueAt(l_strary[0], i, "csourcebilltypename");
								getBillListPanel().getBodyBillModel().setValueAt(l_strary[2], i, "csourcebillrowno");
							}
						}
					}
					// add by yy end

				}

			} catch (BusinessException e) {
				SCMEnv.out(e);
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作"
                                                                                                                       */, e.getMessage());
				return;
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000015")/*
                                                                                                                       * @res
                                                                                                                       * "查询表体"
                                                                                                                       */, e.getMessage());
				return;
			}

		} else if (m_nUIState == 2 && m_bInfoEdit) {
			// 检验信息表体
			// 加载标准值
			Object oTemp = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "cstandardvalue");
			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				UIRefPane refPane = (UIRefPane) getBillCardPanelInfo().getBodyItem("cstandardvalue").getComponent();
				StandInputDlg dlg = new StandInputDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
						"UPPC0020101-000055")/* @res "标准值输入" */, CheckStandardUI.getStandardType(getBillCardPanelInfo()
						.getBillModel(), event.getRow()));
				Object oData[][] = getStandardValue(oTemp.toString());
				((QCStandTableModel) dlg.getUITable().getModel()).setData(oData);
				dlg.setComsPointSize(oData.length);
				refPane.setRefUI(dlg);
			}

			// 设置枚举型检验值输入
			BillItem item = getBillCardPanelInfo().getBodyItem("cresult");
			oTemp = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "ccheckitemid");
			if (oTemp != null && item != null) {

			}

		} else if (m_nUIState == 0 && (m_bBillEdit || m_bBillAdd)
				&& (UITable) event.getSource() == getBillCardPanel().getBillTable()) {

			// 设置辅计量参照(切换行时,行间的辅计量参照不一样)
			Object lobj_bchange = getBillCardPanel().getBodyValueAt(event.getRow(), "bchange");
			Object lobj_cchgbaseid = getBillCardPanel().getBodyValueAt(event.getRow(), "cchgbaseid");
			Object lobj_bassunitmanagedb = getBillCardPanel().getBodyValueAt(event.getRow(), "bassunitmanagedb");

			if (lobj_bchange != null && lobj_bchange.toString().trim().length() > 0 && lobj_cchgbaseid != null
					&& lobj_cchgbaseid.toString().trim().length() > 0 && lobj_bassunitmanagedb != null
					&& lobj_bassunitmanagedb.toString().trim().length() > 0) {
				boolean lb_bchange = (new UFBoolean(lobj_bchange.toString().trim())).booleanValue();
				boolean lb_bassunitmanagedb = (new UFBoolean(lobj_bassunitmanagedb.toString().trim())).booleanValue();

				if (lb_bchange && lb_bassunitmanagedb) {
					// 设置辅计量参照
					UIRefPane ref = (UIRefPane) getBillCardPanel().getBodyItem("cchgassistunitname").getComponent();
					String wherePart = "bd_convert.pk_invbasdoc='" + lobj_cchgbaseid.toString().trim() + "' ";
					ref.setWhereString(wherePart);
					String unionPart = " union all (";
					unionPart += "select bd_measdoc.shortname,bd_measdoc.measname,bd_invbasdoc.pk_measdoc ";
					unionPart += "from bd_invbasdoc ";
					unionPart += "left join bd_measdoc  ";
					unionPart += "on bd_invbasdoc.pk_measdoc=bd_measdoc.pk_measdoc ";
					unionPart += "where bd_invbasdoc.pk_invbasdoc='" + lobj_cchgbaseid.toString().trim() + "') ";
					ref.getRefModel().setGroupPart(unionPart);
				}
			}

		}
	}

	/**
   * 此处插入方法说明。 功能描述: 计算合格数量等 输入参数: 返回值: 异常处理: 日期: 2005-03-16 袁野
   */
	private void calculateNum() {
		int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
		if (m_nUIState == 0 && m_bBillEdit) {
			// 卡片编辑状态

			// add by yye begin 2005-03-30 增加合格辅数量等栏目

			double ldbl_qualifiedassinum = 0;// 合格辅数量
			double ldbl_unqualifiedassinum = 0;// 不合格辅数量
			double ldbl_changenum = 0;// 改判数量
			double ldbl_changeassinum = 0;// 改判辅数量
			double ldbl_changerate = 0;// 改判率
			double ldbl_checknum = 0;// 报检数量

			if (getBillCardPanel().getHeadItem("nchecknum") != null
					&& getBillCardPanel().getHeadItem("nchecknum").getValueObject().toString().trim().length() > 0) {
				ldbl_checknum = new UFDouble(getBillCardPanel().getHeadItem("nchecknum").getValueObject().toString().trim())
						.doubleValue();
			}

			for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
				Object lobj_bqualified = getBillCardPanel().getBodyValueAt(i, "bqualified");
				Object lobj_bchange = getBillCardPanel().getBodyValueAt(i, "bchange");
				Object lobj_num = getBillCardPanel().getBodyValueAt(i, "nnum");
				Object lobj_assinum = getBillCardPanel().getBodyValueAt(i, "nassistnum");

				boolean lbol_bchange = false;
				boolean lbol_bqualified = false;
				double ldbl_assistnumtemp = 0;
				double ldbl_numtemp = 0;

				if (lobj_bqualified != null && lobj_bqualified.toString().length() > 0) {
					lbol_bqualified = new UFBoolean(lobj_bqualified.toString()).booleanValue();
				}

				if (lobj_bchange != null && lobj_bchange.toString().length() > 0) {
					lbol_bchange = new UFBoolean(lobj_bchange.toString()).booleanValue();
				}

				if (lobj_num != null && lobj_num.toString().trim().length() > 0) {
					ldbl_numtemp = new UFDouble(lobj_num.toString().trim()).doubleValue();
				}

				if (lobj_assinum != null && lobj_assinum.toString().trim().length() > 0) {
					ldbl_assistnumtemp = new UFDouble(lobj_assinum.toString().trim()).doubleValue();
				}
				// 首先计算改判数量和改判辅数量
				if (lbol_bchange) {
					ldbl_changenum = ldbl_changenum + ldbl_numtemp;
					ldbl_changeassinum = ldbl_changeassinum + ldbl_assistnumtemp;
				}
				// 计算合格辅数量和不合格辅数量
				if (lbol_bqualified) {
					ldbl_qualifiedassinum = ldbl_qualifiedassinum + ldbl_assistnumtemp;
				} else {
					ldbl_unqualifiedassinum = ldbl_unqualifiedassinum + ldbl_assistnumtemp;
				}

			}

			// 计算改判率：改判数量/报检数量
			if (Math.abs(ldbl_checknum) > 0) {
				ldbl_changerate = ldbl_changenum / ldbl_checknum;
			} else {
				ldbl_changerate = 0;
			}
			// 默认标准才设值
			if (index == 0) {
				getBillCardPanel().getHeadItem("nqualifiedassinum").setValue(new UFDouble(ldbl_qualifiedassinum));
				getBillCardPanel().getHeadItem("nunqualifiedassinum").setValue(new UFDouble(ldbl_unqualifiedassinum));
				getBillCardPanel().getHeadItem("nchangenum").setValue(new UFDouble(ldbl_changenum));
				getBillCardPanel().getHeadItem("nchangeassinum").setValue(new UFDouble(ldbl_changeassinum));
				getBillCardPanel().getHeadItem("nchangerate").setValue(new UFDouble(ldbl_changerate));
			}
			// add by yye end 2005-03-30 增加合格辅数量等栏目

			int nRow = getBillCardPanel().getRowCount();
			double d1 = 0;// 合格数量
			double d2 = 0;// 不合格数量
			double dd1 = 0;// 合格辅数量
			double dd2 = 0;// 不合格辅数量

			Hashtable t = new Hashtable();
			Vector v = new Vector();

			for (int i = 0; i < nRow; i++) {
				Object o1 = getBillCardPanel().getBodyValueAt(i, "nnum");
				Object o2 = getBillCardPanel().getBodyValueAt(i, "bqualified");
				Object o3 = getBillCardPanel().getBodyValueAt(i, "nassistnum");

				// ****
				Object sourcebillrowid = getBillCardPanel().getBodyValueAt(i, "csourcebillrowid");
				if (sourcebillrowid != null && o1 != null && o1.toString().trim().length() > 0) {
					if (!v.contains(sourcebillrowid))
						v.addElement(sourcebillrowid);
					UFDouble d = (UFDouble) o1;
					Object o = t.get(sourcebillrowid);
					if (o == null)
						t.put(sourcebillrowid, d);
					else {
						d = new UFDouble(d.doubleValue() + ((UFDouble) o).doubleValue());
						t.put(sourcebillrowid, d);
					}
				}
				// ****

				if (o2 != null && o1 != null && o1.toString().trim().length() > 0) {
					UFBoolean b = new UFBoolean(o2.toString());
					UFDouble d = (UFDouble) o1;
					if (d.doubleValue() == 0.0 && !m_dialog.getCheckbilltypecode().equals("WH")) {
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0002282")/*
                                                                                                                     * @res
                                                                                                                     * "数量"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000056")/*
                                                                                               * @res
                                                                                               * "数量不能为零！"
                                                                                               */);
						getBillCardPanel().setBodyValueAt(null, i, "nnum");
						return;
					}
					if (b.booleanValue()) {
						d1 += d.doubleValue();
						if (o3 != null && o3.toString().trim().length() > 0)
							dd1 += (new UFDouble(o3.toString())).doubleValue();
					} else {
						d2 += d.doubleValue();
						if (o3 != null && o3.toString().trim().length() > 0)
							dd2 += (new UFDouble(o3.toString())).doubleValue();
					}
				}
			}
			if (index == 0) {
				getBillCardPanel().getHeadItem("nqualifiednum").setValue(new UFDouble(d1));
				getBillCardPanel().getHeadItem("nunqualifiednum").setValue(new UFDouble(d2));
				getBillCardPanel().getHeadItem("nunqualifiedassinum").setValue(new UFDouble(dd2));
			}
			String total = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject();

			String strInvName = (String) getBillCardPanel().getHeadItem("cinventoryname").getValueObject();
			if (total != null && total.trim().length() > 0) {
				double dd = (new UFDouble(total.trim())).doubleValue();
				double dNum = getRoundDouble(d1 + d2 - dd);
// arvin 去掉数量校验
//				if (dNum > 0) {
//					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
//							.getStrByID("C0020101", "UPPC0020101-000057")/*
//                                                             * @res "数量检验"
//                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000058")
//							+ nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001113") + strInvName
//							+ nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001114")
//							+ new UFDouble(total).intValue()
//							+ nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001115")
//							+ new UFDouble(d1).add(new UFDouble(d2)).intValue()
//							+ nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001116")
//							+ new UFDouble(dNum).intValue()/*
//                                               * @res "累计数量大于报检数量！"
//                                               */);
//					return;
//				}
				if (dd != 0.0)
					dd = d1 / dd;
				else
					return;
				if (index == 0) {
					getBillCardPanel().getHeadItem("nqualifiedrate").setValue(new UFDouble(dd));
				}
			}

			if (v.size() > 0 && t.size() > 0) {
				String key[] = new String[v.size()];
				v.copyInto(key);

				Object value = null;
				try {
					value = CacheTool.getColumnValue("po_arriveorder_b", "carriveorder_bid", "narrvnum", key);
				} catch (BusinessException e) {
					SCMEnv.out(e.getMessage());
				}

				if (value != null) {
					Object oValue[] = (Object[]) value;
					for (int i = 0; i < v.size(); i++) {
						Object oTemp = t.get(v.elementAt(i));
						if (oTemp != null && oValue[i] != null) {
							UFDouble ddd1 = new UFDouble(oValue[i].toString().trim());
							UFDouble ddd2 = new UFDouble(oTemp.toString().trim());
							if (Math.abs(ddd2.doubleValue()) > Math.abs(ddd1.doubleValue())) {
//							 arvin 去掉数量校验
//								MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
//										"UPPC0020101-000057")/*
//                                           * @res "数量检验"
//                                           */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000059")/*
//                                                                                                   * @res
//                                                                                                   * "行累计数量大于行报检数量！"
//                                                                                                   */);
								return;
							}
						}
					}
				}
			}

		} else {
			double dtotal = new UFDouble(getBillCardPanel().getHeadItem("nchecknum").getValueObject().toString())
					.doubleValue();
			// 卡片浏览状态或列表状态
			CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();

			Vector vecCheckstandardid = new Vector();
			for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
				if (m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid().equals(sCheckStandards[0])) {
					vecCheckstandardid.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				}
			}
			CheckbillItemVO[] bodyVO = new CheckbillItemVO[vecCheckstandardid.size()];
			vecCheckstandardid.copyInto(bodyVO);
			// add by yye begin 2005-03-30 增加合格辅数量等栏目

			if (headVO == null || bodyVO == null || bodyVO.length == 0) {
				return;
			}

			double ldbl_qualifiedassinum = 0;// 合格辅数量
			double ldbl_unqualifiedassinum = 0;// 不合格辅数量
			double ldbl_changenum = 0;// 改判数量
			double ldbl_changeassinum = 0;// 改判辅数量
			double ldbl_changerate = 0;// 改判率
			double ldbl_checknum = 0;// 报检数量

			if (headVO.getNchecknum() != null) {
				ldbl_checknum = headVO.getNchecknum().doubleValue();
			}

			for (int i = 0; i < bodyVO.length; i++) {

				boolean lbol_bchange = false;
				boolean lbol_bqualified = false;
				double ldbl_assistnumtemp = 0;
				double ldbl_numtemp = 0;

				if (bodyVO[i].getBqualified() != null) {
					lbol_bqualified = bodyVO[i].getBqualified().booleanValue();
				}

				if (bodyVO[i].getBchange() != null) {
					lbol_bchange = bodyVO[i].getBchange().booleanValue();
				}

				if (bodyVO[i].getNnum() != null) {
					ldbl_numtemp = bodyVO[i].getNnum().doubleValue();
				}

				if (bodyVO[i].getNassistnum() != null) {
					ldbl_assistnumtemp = bodyVO[i].getNassistnum().doubleValue();
				}

				// 首先计算改判数量和改判辅数量
				if (lbol_bchange) {
					ldbl_changenum = ldbl_changenum + ldbl_numtemp;
					ldbl_changeassinum = ldbl_changeassinum + ldbl_assistnumtemp;
				}
				// 计算合格辅数量和不合格辅数量
				if (lbol_bqualified) {
					ldbl_qualifiedassinum = ldbl_qualifiedassinum + ldbl_assistnumtemp;
				} else {
					ldbl_unqualifiedassinum = ldbl_unqualifiedassinum + ldbl_assistnumtemp;
				}

			}

			// 计算改判率：改判数量/报检数量
			if (Math.abs(ldbl_checknum) > 0) {
				ldbl_changerate = ldbl_changenum / ldbl_checknum;
			} else {
				ldbl_changerate = 0;
			}
			if (index == 0) {
				headVO.setNqualifiedassinum(new UFDouble(ldbl_qualifiedassinum));
				headVO.setNunqualifiedassinum(new UFDouble(ldbl_unqualifiedassinum));
				headVO.setNchangenum(new UFDouble(ldbl_changenum));
				headVO.setNchangeassinum(new UFDouble(ldbl_changeassinum));
				headVO.setNchangerate(new UFDouble(ldbl_changerate));

				getBillCardPanel().getHeadItem("nqualifiedassinum").setValue(new UFDouble(ldbl_qualifiedassinum));
				getBillCardPanel().getHeadItem("nunqualifiedassinum").setValue(new UFDouble(ldbl_unqualifiedassinum));
				getBillCardPanel().getHeadItem("nchangenum").setValue(new UFDouble(ldbl_changenum));
				getBillCardPanel().getHeadItem("nchangeassinum").setValue(new UFDouble(ldbl_changeassinum));
				getBillCardPanel().getHeadItem("nchangerate").setValue(new UFDouble(ldbl_changerate));
			}
			// add by yye end 2005-03-30 增加合格辅数量等栏目

			UFDouble total = headVO.getNchecknum();
			double d1 = 0;// 合格数量
			double d2 = 0;// 不合格数量
			double d3 = 0;// 合格率
			double dd2 = 0;
			for (int i = 0; i < bodyVO.length; i++) {
				if (bodyVO[i].getBqualified() != null && bodyVO[i].getBqualified().booleanValue()) {
					if (bodyVO[i].getNnum() != null) {
						d1 += bodyVO[i].getNnum().doubleValue();
					}

				}
			}
			d2 = dtotal - d1;
			UFDouble dNexChangeRate = new UFDouble(getBillCardPanel().getHeadItem("nexchangerate").getValueObject()
					.toString());
			dd2 = new UFDouble(d2).div(dNexChangeRate).doubleValue();

			if (index == 0) {
				headVO.setNqualifiednum(new UFDouble(d1));
				headVO.setNunqualifiednum(new UFDouble(d2));
				headVO.setNunqualifiedassinum(new UFDouble(dd2));
				getBillCardPanel().getHeadItem("nunqualifiedassinum").setValue(new UFDouble(dd2));
				if (total != null && total.doubleValue() != 0.0) {
					d3 = d1 / total.doubleValue();
					headVO.setNqualifiedrate(new UFDouble(d3));
				}

				getBillCardPanel().getHeadItem("nqualifiednum").setValue(new UFDouble(d1));
				getBillCardPanel().getHeadItem("nunqualifiednum").setValue(new UFDouble(d2));
				getBillCardPanel().getHeadItem("nqualifiedrate").setValue(new UFDouble(d3));
			}
			if (m_nUIState == 1) {
				if (index == 0) {
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(ldbl_qualifiedassinum), m_nBillRecord,
							"nqualifiedassinum");
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(ldbl_unqualifiedassinum), m_nBillRecord,
							"nunqualifiedassinum");
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(ldbl_changenum), m_nBillRecord, "nchangenum");
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(ldbl_changeassinum), m_nBillRecord,
							"nchangeassinum");
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(ldbl_changerate), m_nBillRecord, "nchangerate");
					// add by yye end 2005-03-30 增加合格辅数量等栏目

					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(d1), m_nBillRecord, "nqualifiednum");
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(d2), m_nBillRecord, "nunqualifiednum");
					getBillListPanel().getHeadBillModel().setValueAt(new UFDouble(d3), m_nBillRecord, "nqualifiedrate");
					// 设置自由项
					getBillListPanel().getHeadBillModel().setValueAt(headVO.getVfree1(), m_nBillRecord, "vfree1");
					getBillListPanel().getHeadBillModel().setValueAt(headVO.getVfree2(), m_nBillRecord, "vfree2");
					getBillListPanel().getHeadBillModel().setValueAt(headVO.getVfree3(), m_nBillRecord, "vfree3");
					getBillListPanel().getHeadBillModel().setValueAt(headVO.getVfree4(), m_nBillRecord, "vfree4");
					getBillListPanel().getHeadBillModel().setValueAt(headVO.getVfree5(), m_nBillRecord, "vfree5");
					getBillListPanel().getHeadBillModel().setValueAt(headVO.getVfreeitem(), m_nBillRecord, "vfreeitem");
				}
			}
		}
	}

	/**
   * 创建日期： 2005-9-20 功能描述： 更新按钮状态（递归方式）
   */
	private void update(ButtonObject bo) {
		updateButton(bo);
		if (bo.getChildCount() > 0) {
			for (int i = 0, len = bo.getChildCount(); i < len; i++)
				update(bo.getChildButtonGroup()[i]);
		}
	}

	/**
   * 此处插入方法说明。 功能描述:查询的常用条件 输入参数: 返回值: 异常处理: 日期:
   */
	private void changeQueryModelLayout() {
		UILabel label = new UILabel(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000018")/*
                                                                                                               * @res
                                                                                                               * "选择条件："
                                                                                                               */);
		label.setBounds(30, 45, 100, 25);

		m_rbFree = new UIRadioButton();
		m_rbFree.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000340")/*
                                                                                                     * @res
                                                                                                     * "自由"
                                                                                                     */);
		m_rbFree.setBackground(this.getBackground());
		m_rbFree.setForeground(java.awt.Color.black);
		m_rbFree.setSize(80, m_rbFree.getHeight());
		m_rbFree.setSelected(true);

		m_rbGoing = new UIRadioButton();
		m_rbGoing.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000019")/*
                                                                                                     * @res
                                                                                                     * "正在审批"
                                                                                                     */);
		m_rbGoing.setBackground(m_rbFree.getBackground());
		m_rbGoing.setForeground(m_rbFree.getForeground());
		m_rbGoing.setSize(m_rbFree.getSize());

		m_rbPass = new UIRadioButton();
		m_rbPass.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000020")/*
                                                                                                   * @res
                                                                                                   * "审批通过"
                                                                                                   */);
		m_rbPass.setBackground(m_rbFree.getBackground());
		m_rbPass.setForeground(m_rbFree.getForeground());
		m_rbPass.setSize(m_rbFree.getSize());

		m_rbNoPass = new UIRadioButton();
		m_rbNoPass.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000242")/*
                                                                                                       * @res
                                                                                                       * "审批未通过"
                                                                                                       */);
		m_rbNoPass.setBackground(m_rbFree.getBackground());
		m_rbNoPass.setForeground(m_rbFree.getForeground());
		m_rbNoPass.setSize(m_rbFree.getSize());

		m_rbAll = new UIRadioButton();
		m_rbAll.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000217")/*
                                                                                                     * @res
                                                                                                     * "全部"
                                                                                                     */);
		m_rbAll.setBackground(m_rbFree.getBackground());
		m_rbAll.setForeground(m_rbFree.getForeground());
		m_rbAll.setSize(m_rbFree.getSize());

		m_rbFree.setLocation(100, 45);
		m_rbGoing.setLocation(m_rbFree.getX(), m_rbFree.getY() + m_rbFree.getHeight() + 20);
		m_rbPass.setLocation(m_rbFree.getX(), m_rbGoing.getY() + m_rbGoing.getHeight() + 20);
		m_rbNoPass.setLocation(m_rbFree.getX(), m_rbPass.getY() + m_rbPass.getHeight() + 20);
		m_rbAll.setLocation(m_rbFree.getX(), m_rbNoPass.getY() + m_rbNoPass.getHeight() + 20);

		//
		javax.swing.ButtonGroup bg = new javax.swing.ButtonGroup();
		bg.add(m_rbFree);
		bg.add(m_rbGoing);
		bg.add(m_rbPass);
		bg.add(m_rbNoPass);
		bg.add(m_rbAll);
		bg.setSelected(m_rbFree.getModel(), true);

		m_condClient.getUIPanelNormal().setLayout(null);

		m_condClient.getUIPanelNormal().add(m_rbFree);
		m_condClient.getUIPanelNormal().add(m_rbGoing);
		m_condClient.getUIPanelNormal().add(m_rbPass);
		m_condClient.getUIPanelNormal().add(m_rbNoPass);
		m_condClient.getUIPanelNormal().add(m_rbAll);
		m_condClient.getUIPanelNormal().add(label);

		return;
	}

	/**
   * ?user> 功能：自动判断检验结果和是否达标 参数： 返回： 例外： 日期：(2004-5-20 15:55:10)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * @param event
   *          nc.ui.pub.bill.BillEditEvent 是否达标的控制只根据默认标准控制
   */
	private void autoJudgeCheckResult(BillEditEvent event) {
		Object lobj_result = null;
		Object lobj_standardvalue = null;
		Object lobj_standardtype = null;
		// 为了适配多标准做的调整
		String index = "";// 多标准下标
		boolean isBmeeted = false;
		// 自动判断检验结果 begin
		lobj_result = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "cresult");// 实际输入的结果
		if (lobj_result == null || lobj_result.toString().trim().length() == 0) {
			// //add by yye 2005-04-01 begin 是否达标
			getBillCardPanelInfo().setBodyValueAt(new UFBoolean(false), event.getRow(), "bmeet");// 是否达标
			getBillCardPanelInfo().setBodyValueAt(null, event.getRow(), "ccheckresult");
			// add by yye 2005-04-01 end
			return;// 实际结果为空，则直接退出
		}
		for (int i = 0; i < sCheckStandards.length; i++) {
			if (i > 0) {
				index = "" + (i + 1);
			}
			lobj_standardvalue = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "cstandardvalue" + index);// 标准值域
			lobj_standardtype = getBillCardPanelInfo().getBodyValueAt(event.getRow(), "icheckstandard" + index);// 标准值类型
			if (lobj_standardvalue == null || lobj_standardvalue.toString().trim().length() == 0 || lobj_standardtype == null
					|| lobj_standardtype.toString().trim().length() == 0) {
				continue;
			}

			String lstr_result = lobj_result.toString().trim();
			String lstr_standardvalue = lobj_standardvalue.toString().trim();
			String lstr_standardtype = lobj_standardtype.toString().trim();
			int lint_standardtype = QCConst.QC_STANDARDTYPE_NUMBER;

			if (lstr_standardtype.equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                                 * @res
                                                                                                                 * "数值型"
                                                                                                                 */)) {
				lint_standardtype = QCConst.QC_STANDARDTYPE_NUMBER;
			} else {// 枚举
				lint_standardtype = QCConst.QC_STANDARDTYPE_ENUM;
			}

			String lstr_CheckResult = CheckstandardItemVO.getStateNameByResult(lstr_result, lstr_standardvalue,
					lint_standardtype);
			getBillCardPanelInfo().setBodyValueAt(lstr_CheckResult, event.getRow(), "ccheckresult" + index);
			// 自动判断检验结果 end
			if (!isBmeeted) {
				// 自动判定是否达标，以多标准中索引最小的进行判断
				setIsBmeet(lstr_CheckResult, event.getRow());
				isBmeeted = true;
			}
		}
	}

	/**
   * 设置是否达标
   * 
   */
	private void setIsBmeet(String lstr_CheckResult, int row) {
		// add by yye 2005-04-01 begin 是否达标
		if (lstr_CheckResult != null && lstr_CheckResult.trim().length() > 0) {
			if (getBillCardPanel().getBillModel().getValueAt(0, "ccheckstateid") == null) {
				return;
			}
			String ccheckstateid = getBillCardPanel().getBillModel().getValueAt(0, "ccheckstateid").toString();

			Object tempData[][] = nc.ui.scm.pub.CacheTool.getAnyValue2("qc_checkstate_b", "bqualified", "ccheckstateid='"
					+ ccheckstateid + "' and ccheckstatename='" + lstr_CheckResult + "'");
			if (tempData != null && tempData.length > 0 && tempData[0] != null && tempData[0].length > 0) {
				Object o = tempData[0][0];
				if (o != null)
					getBillCardPanelInfo().setBodyValueAt(new UFBoolean(o.toString()), row, "bmeet");
				else
					getBillCardPanelInfo().setBodyValueAt(new UFBoolean(false), row, "bmeet");
			}
		} else {
			getBillCardPanelInfo().setBodyValueAt(new UFBoolean(false), row, "bmeet");
		}

		// add by yye 2005-04-01 end
	}

	/**
   * ?user> 功能：自动判断检验结果和是否达标并显示 参数： 返回： 例外： 日期：(2005-3-20 15:55:10) 袁野
   */
	private void displayCheckResultForInfoCard() {

		int lint_RowCount = getBillCardPanelInfo().getRowCount();
		Object lobj_result = null;
		Object lobj_standardvalue = null;
		Object lobj_standardtype = null;
		if (sCheckStandards != null && sCheckStandards.length > 0) {

			// 2009/10/20 田锋涛 - 存放检验项目ID，针对是否达标，已经处理的行不再处理
			HashMap<String, Integer> handleResults = new HashMap<String, Integer>();

			for (int j = 0; j < sCheckStandards.length; j++) {
				for (int i = 0; i < lint_RowCount; i++) {

					String sCheckItemId = getBillCardPanelInfo().getBodyValueAt(i, "ccheckitemid").toString();// 检验项目ID

					// 自动判断检验结果 begin
					lobj_result = getBillCardPanelInfo().getBodyValueAt(i, "cresult");
					if (j == 0) {
						lobj_standardvalue = getBillCardPanelInfo().getBodyValueAt(i, "cstandardvalue");
						lobj_standardtype = getBillCardPanelInfo().getBodyValueAt(i, "icheckstandard");// 标准值类型
					} else {
						lobj_standardvalue = getBillCardPanelInfo().getBodyValueAt(i, "cstandardvalue" + String.valueOf(j + 1));
						lobj_standardtype = getBillCardPanelInfo().getBodyValueAt(i, "icheckstandard" + String.valueOf(j + 1));// 标准值类型
					}
					if (lobj_result == null || lobj_result.toString().trim().length() == 0) {
						// add by yye 2005-04-01 begin 是否达标
						if (!handleResults.containsKey(sCheckItemId)) {
							getBillCardPanelInfo().setBodyValueAt(new UFBoolean(false), i, "bmeet");// 是否达标
							handleResults.put(sCheckItemId, 1);
						}
						continue;
						// add by yye 2005-04-01 end
					}

					if (lobj_standardvalue == null || lobj_standardvalue.toString().trim().length() == 0) {
						continue;
					}

					if (lobj_standardtype == null || lobj_standardtype.toString().trim().length() == 0) {
						continue;
					}

					String lstr_result = lobj_result.toString().trim();
					String lstr_standardvalue = lobj_standardvalue.toString().trim();
					String lstr_standardtype = lobj_standardtype.toString().trim();
					int lint_standardtype = 0;

					if (lstr_standardtype.equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                                     * @res
                                                                                                                     * "数值型"
                                                                                                                     */)) {
						lint_standardtype = 0;
						/*
             * if(!QcTool.Regex(lstr_result)){ continue; }
             */
					} else {// 枚举
						lint_standardtype = 1;
					}

					String lstr_CheckResult = CheckstandardItemVO.getStateNameByResult(lstr_result, lstr_standardvalue,
							lint_standardtype);
					if (j == 0) {
						getBillCardPanelInfo().setBodyValueAt(lstr_CheckResult, i, "ccheckresult");
					} else {
						getBillCardPanelInfo().setBodyValueAt(lstr_CheckResult, i, "ccheckresult" + String.valueOf(j + 1));
					}
					// 自动判断检验结果 end

					// add by yye 2005-04-01 begin 是否达标
					if (handleResults.containsKey(sCheckItemId))
						continue;
					if (lstr_CheckResult != null && lstr_CheckResult.trim().length() > 0) {
						if (getBillCardPanel().getBillModel().getValueAt(0, "ccheckstateid") == null) {
							continue;
						}

						String ccheckstateid = getBillCardPanel().getBillModel().getValueAt(0, "ccheckstateid").toString();
						Object tempData[][] = nc.ui.scm.pub.CacheTool.getAnyValue2("qc_checkstate_b", "bqualified",
								"ccheckstateid='" + ccheckstateid + "' and ccheckstatename='" + lstr_CheckResult + "'");
						if (tempData != null && tempData.length > 0 && tempData[0] != null && tempData[0].length > 0) {
							Object o = tempData[0][0];
							if (o != null) {
								getBillCardPanelInfo().setBodyValueAt(new UFBoolean(o.toString()), i, "bmeet");
								handleResults.put(sCheckItemId, 1);
							}
						}
					}
					// add by yye 2005-04-01 end
				}
			}
		}
	}

	/**
   * ?user> 功能：自动判断检验结果并显示 参数： 返回： 例外： 日期：(2005-3-20 15:55:10) 袁野
   */
	private void displayCheckResultForInfoCardMMQuery() {

		int lint_RowCount = getBillCardPanelMMQuery().getRowCount();

		for (int i = 0; i < lint_RowCount; i++) {
			// 自动判断检验结果 begin
			Object lobj_result = getBillCardPanelMMQuery().getBodyValueAt(i, "cresult");
			Object lobj_standardvalue = getBillCardPanelMMQuery().getBodyValueAt(i, "cstandardvalue");
			Object lobj_standardtype = getBillCardPanelMMQuery().getBodyValueAt(i, "icheckstandard");// 标准值类型

			if (lobj_result == null || lobj_result.toString().trim().length() == 0) {
				// add by yye 2005-04-01 begin 是否达标
				getBillCardPanelMMQuery().setBodyValueAt(new UFBoolean(false), i, "bmeet");// 是否达标
				// add by yye 2005-04-01 end
				continue;
			}

			if (lobj_standardvalue == null || lobj_standardvalue.toString().trim().length() == 0) {
				continue;
			}

			if (lobj_standardtype == null || lobj_standardtype.toString().trim().length() == 0) {
				continue;
			}

			String lstr_result = lobj_result.toString().trim();
			String lstr_standardvalue = lobj_standardvalue.toString().trim();
			String lstr_standardtype = lobj_standardtype.toString().trim();
			int lint_standardtype = 0;

			if (lstr_standardtype.equals(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                                 * @res
                                                                                                                 * "数值型"
                                                                                                                 */)) {
				lint_standardtype = 0;
			} else {// 枚举
				lint_standardtype = 1;
			}

			String lstr_CheckResult = CheckstandardItemVO.getStateNameByResult(lstr_result, lstr_standardvalue,
					lint_standardtype);

			getBillCardPanelMMQuery().setBodyValueAt(lstr_CheckResult, i, "ccheckresult");
			// 自动判断检验结果 end

			// /add by yye 2005-04-01 begin 是否达标
			if (lstr_CheckResult != null && lstr_CheckResult.trim().length() > 0) {
				String ccheckstateid = m_checkBillVOsForMMQuery[m_nBillRecordForMMQuery].getParentVO().getAttributeValue(
						"ccheckstateid").toString();
				Object tempData[][] = nc.ui.scm.pub.CacheTool.getAnyValue2("qc_checkstate_b", "bqualified", "ccheckstateid='"
						+ ccheckstateid + "' and ccheckstatename='" + lstr_CheckResult + "'");
				if (tempData != null && tempData.length > 0) {
					Object o = tempData[0][0];
					if (o != null)
						getBillCardPanelMMQuery().setBodyValueAt(new UFBoolean(o.toString()), i, "bmeet");
				}
			}
		}
	}

	/**
   * 得到当前操作员
   * 
   * @return
   */
	public String getOperatorId() {
		String operatorid = getClientEnvironment().getUser().getPrimaryKey();
		if (operatorid == null || operatorid.trim().equals("") || operatorid.equals("88888888888888888888")) {
			operatorid = "10013488065564590288";
		}
		return operatorid;
	}

	/**
   * 根据检验单据类型返回对应的质量检验单单据类型。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param strCheckType
   * @return
   *          <p>
   * @author czp
   * @time 2007-6-27 下午01:15:46
   */
	private String getBillTypeQC(String strCheckType) {
		String strTypeQC = "WR";
		if (strCheckType == null) {
			return strTypeQC;
		}
		if (strCheckType.equals("23")) {
			strTypeQC = "WH";
		} else if (strCheckType.equals("A4")) {
			strTypeQC = "WQ";
		} else if (strCheckType.equals("3V")) {
			strTypeQC = "WP";
		} else if (strCheckType.equals("4Z")) {
			strTypeQC = "WS";
		}
		return strTypeQC;
	}

	/**
   * 此处插入方法说明。 功能描述:检验单卡片单据控制 输入参数: 返回值: 异常处理: 日期:2002/04/18
   */

	protected BillCardPanel getBillCardPanel() {
		if (m_cardBill == null) {
			try {
				m_cardBill = new BillCardPanel();

				if (strBilltemplate != null) {

					tmpletvo = m_cardBill
							.getDefaultTemplet(strBilltemplate, null, getOperatorID(), getPk_corp(), strBilltemplate);
					BillData bd = new BillData(tmpletvo);
					m_cardBill.setBillData(bd);
					m_cardBill.setAutoExecHeadEditFormula(true);
				} else {

					String sType = m_dialog.getCheckbilltypecode().trim();

					String lStr_Type = getBillTypeQC(sType);

					String lStr_NodeKey = lStr_Type;

					tmpletvo = m_cardBill.getDefaultTemplet(lStr_Type, null, getOperatorID(), getPk_corp(), lStr_NodeKey);
					BillData bd = new BillData(tmpletvo);
					m_cardBill.setBillData(bd);
					m_cardBill.setAutoExecHeadEditFormula(true);
				}
				m_cardBill.setBodyMenuShow(false);
				m_cardBill.addEditListener(this);
				m_cardBill.addBodyEditListener2(this);
				m_cardBill.addBodyMenuListener(this);
				BillData bd = m_cardBill.getBillData();
				BillItem item = bd.getHeadItem("vfreeitem");
				if (item.isShow()) {
					// 设置自由项
					FreeItemRefPane refPane = new FreeItemRefPane();
					refPane.setMaxLength(item.getLength());
					item.setComponent(refPane);
				}

				// 设置数量的精度控制
				item = bd.getHeadItem("nchecknum");
				if (item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchecknum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_numDecimal);
				}
				item = bd.getHeadItem("nassistchecknum");
				if (item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getHeadItem("nassistchecknum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_assistDecimal);
				}
				item = bd.getHeadItem("nexchangerate");
				if (item.isShow())
					item.setDecimalDigits(m_convertDecimal);
				item = bd.getBodyItem("nnum");
				if (item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getBodyItem("nnum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_numDecimal);
				}
				item = bd.getBodyItem("nassistnum");
				if (item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getBodyItem("nassistnum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_assistDecimal);
				}
				item = bd.getBodyItem("nexchangerateb");
				if (item.isShow())
					item.setDecimalDigits(m_convertDecimal);
				item = bd.getHeadItem("nqualifiednum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nunqualifiednum");
				if (item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nqualifiedrate");
				if (item.isShow())
					item.setDecimalDigits(m_convertDecimal);

				// add by yye begin 2005-05-21
				item = bd.getHeadItem("nqualifiedassinum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nunqualifiedassinum");
				if (item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nchangeassinum");
				if (item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchangeassinum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_assistDecimal);
				}

				item = bd.getHeadItem("nchangenum");
				if (item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchangenum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_numDecimal);
				}

				// add by yye end 2005-05-21

				DefVO[] defBody = DefSetTool.getBatchcodeDef(getClientEnvironment().getCorporation().getPk_corp());
				if (defBody != null)
					bd.updateItemByDef(defBody, "cb_vuserdef", false);
				//
				m_cardBill.setBillData(bd);
				// 处理自定义项
				/*
         * nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef( m_cardBill,
         * getClientEnvironment().getCorporation() .getPk_corp(),
         * nc.vo.scm.constant.ScmConst.QC_CheckManual, "vdef", "vdef");
         */

				nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef(m_cardBill, getClientEnvironment().getCorporation()
						.getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdef", "vdef");
				//
				m_cardBill.setBillData(m_cardBill.getBillData());
				m_cardBill.setAutoExecHeadEditFormula(true);
				((UIRefPane) getBillCardPanel().getHeadItem("cvendormangid").getComponent()).getUIButton().addActionListener(
						this);
				//
			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.getMessage());
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                       * @res
                                                                                                                       * "加载模板"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000061")/*
                                                                                           * @res
                                                                                           * "检验单卡片模板不存在！"
                                                                                           */);
				return null;
			}
		}
		return m_cardBill;
	}

	/**
   * 此处插入方法说明。 创建日期：(2001-8-2 13:21:20)
   * 
   * @return java.lang.String
   */
	private String getOperatorID() {
		return nc.ui.pub.ClientEnvironment.getInstance().getUser().getPrimaryKey();
	}

	/**
   * 此处插入方法说明。 创建日期：(2001-8-2 12:42:41)
   * 
   * @return java.lang.String
   */
	private String getPk_corp() {
		return nc.ui.pub.ClientEnvironment.getInstance().getCorporation().getPk_corp();
	}

	/**
   * 此处插入方法说明。 功能描述:获得单据号 输入参数: 返回值: 异常处理: 日期:2002/05/09
   */

	private String getBillCode(CheckbillHeaderVO head) {
		String vcode = null;

		// 获取检验单号
		try {
			CheckbillVO vo = new CheckbillVO();
			vo.setParentVO(head);
			vo.setChildrenVO(null);
			vcode = CheckbillHelper.getSysBillCode(vo);
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000062")/*
                                                                                                                     * @res
                                                                                                                     * "获取检验单号"
                                                                                                                     */, e.getMessage());
			return null;
		}
		return vcode;
	}

	/**
   * 此处插入方法说明。 功能描述:检验单卡片单据控制 输入参数: 返回值: 异常处理: 日期:2002/04/18
   */

	public BillListPanel getBillListPanel() {
		if (m_listBill == null) {
			try {
				String sType = null;
				String lStr_Type = "WR";
				m_listBill = new BillListPanel(false);
				// user code begin {1}
				if (strBilltemplet == null) {
					// strBilltemplet
					if (m_dialog == null) {
						return null;
					}
					// 加载模板
					sType = m_dialog.getCheckbilltypecode().trim();
					if (sType.equals("23")) {
						lStr_Type = "WH";
					} else if (sType.equals("A4")) {
						lStr_Type = "WQ";
					} else if (sType.equals("3V")) {
						lStr_Type = "WP";
					}
				} else
					lStr_Type = strBilltemplet;

				String lStr_NodeKey = lStr_Type;
				try {
					m_listBill.loadTemplet(lStr_Type, null, getOperatorID(), getPk_corp(), lStr_NodeKey);
				} catch (java.lang.Throwable ivjExc) {
					String key = m_dialog.getCheckbillid();
					m_listBill.loadTemplet(key);
				}

				BillListData bd = m_listBill.getBillListData();

				// 设置数量的精度控制
				BillItem item = bd.getHeadItem("nchecknum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nassistchecknum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nexchangerate");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_convertDecimal);
				item = bd.getBodyItem("nnum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getBodyItem("nassistnum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_assistDecimal);
				item = bd.getBodyItem("nexchangerateb");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_convertDecimal);
				item = bd.getHeadItem("nqualifiednum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nunqualifiednum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);
				item = bd.getHeadItem("nqualifiedrate");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);

				item = bd.getHeadItem("nqualifiedassinum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nunqualifiedassinum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nassistchecknum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_assistDecimal);

				item = bd.getHeadItem("nchangeassinum");
				if (item != null && item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchangeassinum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_assistDecimal);
				}

				item = bd.getHeadItem("nchangenum");
				if (item != null && item.isShow()) {
					UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchangenum").getComponent();
					refpane.getUITextField().setMaxLength(16);
					item.setDecimalDigits(m_numDecimal);
				}
				DefVO[] defBody = DefSetTool.getBatchcodeDef(getClientEnvironment().getCorporation().getPk_corp());
				if (item != null && item.isShow())
					bd.updateItemByDef(defBody, "cb_vuserdef", false);
				m_listBill.setListData(bd);
				// 处理自定义项
				nc.ui.scm.pub.def.DefSetTool.updateBillListPanelUserDef(m_listBill, getClientEnvironment().getCorporation()
						.getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdef", "vdef");

				m_listBill.addEditListener(this);
				// 鼠标监听
				m_listBill.getHeadTable().addMouseListener(this);
				// 表头选择监听
				m_listBill.getHeadTable().setCellSelectionEnabled(false);

				m_listBill.getHeadTable().setRowSelectionAllowed(true);
				m_listBill.getHeadTable().setColumnSelectionAllowed(false);
				m_listBill.getHeadTable().setSelectionMode(javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
				m_listBill.getHeadTable().getSelectionModel().addListSelectionListener(this);

				m_listBill.getChildListPanel().setTotalRowShow(true);
				m_listBill.getHeadBillModel().addSortRelaObjectListener2(this);
				// 补充监听是否查询过表体
				m_listBill.getHeadBillModel().addSortRelaObjectListener2(new BillListHeadSortListener());
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.getMessage());
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                       * @res
                                                                                                                       * "加载模板"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000023")/*
                                                                                           * @res
                                                                                           * "检验单列表模板不存在！"
                                                                                           */);
				return null;
			}
		}
		return m_listBill;
	}

	/**
   * 此处插入方法说明。 功能描述: 返回主料联查专用UITabbedPane 返回值: UITabbedPane 日期:2005/03/29 袁野
   */

	private UITabbedPane getTabbedPaneForMMQuery() {
		if (m_tabbedPaneForMMQuery == null) {
			try {
				m_tabbedPaneForMMQuery = new UITabbedPane(3);
				m_tabbedPaneForMMQuery.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC0020101-000026")/*
                                                                                                                     * @res
                                                                                                                     * "检验单"
                                                                                                                     */, getBillListPanelMMQuery());
				m_tabbedPaneForMMQuery.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC00102-000010")/*
                                                                                                                   * @res
                                                                                                                   * "检验信息"
                                                                                                                   */, getBillCardPanelMMQuery());
				// 初始时，检验信息页签不可用
				// m_tabbedPane.setEnabledAt(1, false);

				m_tabbedPaneForMMQuery.addChangeListener(this);

			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.getMessage());
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                       * @res
                                                                                                                       * "加载模板"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000063")/*
                                                                                           * @res
                                                                                           * "检验单模板不存在！"
                                                                                           */);
				return null;
			}
		}
		return m_tabbedPaneForMMQuery;
	}

	/**
   * 此处插入方法说明。 功能描述:检验单卡片单据控制 输入参数: 返回值: 异常处理: 日期:2002/04/18
   */

	protected BillCardPanel getBillCardPanelInfo() {
		if (m_cardInfo == null) {
			try {
				m_cardInfo = new BillCardPanel();

				String sType = null;
				String sTypeTemplate = null;

				if (strBilltemplate != null) {

					sTypeTemplate = strBilltemplate;

				} else {

					sType = m_dialog.getCheckbilltypecode().trim();

				}

				String lStr_Type = "WRIF";
				if (sTypeTemplate != null) {
					if (sTypeTemplate.equalsIgnoreCase("WH")) {
						lStr_Type = "WHIF";
					} else if (sTypeTemplate.equalsIgnoreCase("WQ")) {
						lStr_Type = "WQIF";
					} else if (sTypeTemplate.equalsIgnoreCase("WP")) {
						lStr_Type = "WPIF";
					} else if (sTypeTemplate.equalsIgnoreCase("WS")) {
						lStr_Type = "WSIF";
					}
				} else if (sType != null) {
					if (sType.equals("23")) {
						lStr_Type = "WHIF";
					} else if (sType.equals("A4")) {
						lStr_Type = "WQIF";
					} else if (sType.equals("3V")) {
						lStr_Type = "WPIF";
					} else if (sType.equals("4Z")) {
						lStr_Type = "WSIF";
					}
				}

				String lStr_NodeKey = lStr_Type;
				try {
					m_cardInfo.loadTemplet(lStr_Type, null, getOperatorID(), getPk_corp(), lStr_NodeKey);
				} catch (java.lang.Throwable ivjExc) {
					// user code begin {1}
					// 加载模板
					String key = m_dialog.getCheckinfoid();
					if (key == null || key.trim().length() == 0) {
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000021")/*
                                       * @res "加载模板"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000022")/*
                                                                                               * @res
                                                                                               * "模板不存在！"
                                                                                               */);
						return null;
					}
					m_cardInfo.loadTemplet(key);
				}

				// BillData bd = new BillData(m_cardInfo.getTempletData(key));
				BillData bd = m_cardInfo.getBillData();

				BillItem item = bd.getBodyItem("cstandardvalue");
				if (item != null) {
					QCRefModel refModel = new QCRefModel();
					UIRefPane refPane = new UIRefPane();
					refPane.setIsCustomDefined(true);
					refPane.setRefModel(refModel);

					refPane.setMaxLength(item.getLength());
					refPane.setReturnCode(true);
					bd.getBodyItem("cstandardvalue").setComponent(refPane);
				}
				item = bd.getBodyItem("bdefault");
				item.setEnabled(false);
				// 设置数量的精度控制
				item = bd.getHeadItem("nnum");
				if (item != null && item.isShow())
					item.setDecimalDigits(m_numDecimal);
				bd.getBodyItem("icheckstandard").setWithIndex(true);
				m_comType = (UIComboBox) getBillCardPanelInfo().getBodyItem("icheckstandard").getComponent();
				m_comType.setTranslate(true);
				if (m_comType.getItemCount() == 0) {
					m_comType.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                           * @res
                                                                                                           * "数值型"
                                                                                                           */);
					m_comType.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000030")/*
                                                                                                           * @res
                                                                                                           * "枚举型"
                                                                                                           */);
				}
				for (int i = 2; i < 11; i++) {
					bd.getBodyItem("icheckstandard" + String.valueOf(i)).setWithIndex(true);
					UIComboBox m_comType1 = (UIComboBox) getBillCardPanelInfo().getBodyItem("icheckstandard" + String.valueOf(i))
							.getComponent();
					m_comType1.setTranslate(true);
					if (m_comType1.getItemCount() == 0) {
						m_comType1.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                             * @res
                                                                                                             * "数值型"
                                                                                                             */);
						m_comType1.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000030")/*
                                                                                                             * @res
                                                                                                             * "枚举型"
                                                                                                             */);
					}
				}
				m_cardInfo.setBillData(bd);
				// 处理自定义项
				nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef(m_cardInfo, getClientEnvironment().getCorporation()
						.getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdefitem", "vdefitem");

				m_cardInfo.setBodyMenuShow(false);
				m_cardInfo.addEditListener(this);
				m_cardInfo.addBodyEditListener2(this);
				// 不支持排序
				m_cardInfo.getBillTable().setSortEnabled(false);
				// user code end
			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.getMessage());
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                       * @res
                                                                                                                       * "加载模板"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000025")/*
                                                                                           * @res
                                                                                           * "检验信息卡片模板不存在！"
                                                                                           */);
				return null;
			}
		}
		return m_cardInfo;
	}

	/**
   * 此处插入方法说明。 功能描述: 输入参数: 返回值: 异常处理: 日期:2003-03-26
   */
	private double getRoundDouble(double d) {
		double temp = 1.0;
		for (int i = 1; i <= m_numDecimal; i++)
			temp *= 10.0;

		long i = Math.round(d * temp);
		double dd = i / temp;

		return dd;
	}

	/**
   * 此处插入方法说明。 功能描述:根据报检数量和抽样比例参照获得抽样比例 输入参数: 返回值: 异常处理: 日期:2002/09/24
   * 
   * @return nc.vo.pub.lang.UFDouble
   * @param nCheckNum
   *          nc.vo.pub.lang.UFDouble
   * @param nSampleRatio
   *          java.lang.String
   */
	private UFDouble getSampleRatioByNum(UFDouble nCheckNum, String sValue) {
		// 获得抽样比例序列值
		Vector vStart = new Vector();
		Vector vEnd = new Vector();
		Vector vValue = new Vector();

		if (sValue == null || sValue.trim().length() == 0)
			return null;
		int nFirst = -1;
		int nSecond = -1;
		int nThird = -1;
		int nFourth = -1;
		do {
			nFirst = sValue.indexOf("(", nFourth);
			nSecond = sValue.indexOf(",", nFourth);
			nThird = sValue.indexOf(")", nFourth);
			nFourth = sValue.indexOf("]", nFourth);

			if (nFourth >= 0) {
				String s1 = sValue.substring(nFirst + 1, nSecond);
				String s2 = sValue.substring(nSecond + 1, nThird);
				String s3 = sValue.substring(nThird + 2, nFourth);

				vStart.addElement(s1);
				vEnd.addElement(s2);
				vValue.addElement(s3);
				nFourth += 2;
			}
		} while (nFourth >= 0);

		if (vStart.size() == 0)
			return null;
		Object data[][] = new Object[vStart.size()][3];
		for (int i = 0; i < vStart.size(); i++) {
			data[i][0] = vStart.elementAt(i);
			data[i][1] = vEnd.elementAt(i);
			data[i][2] = vValue.elementAt(i);
		}

		// 根据报检数量获得抽样比例
		// 比较末行之前的数量，以获得抽样比例
		UFDouble nSampleRatio = null;
		for (int i = 0; i < data.length - 1; i++) {
			UFDouble nStart = new UFDouble((String) data[i][0]);
			UFDouble nEnd = new UFDouble((String) data[i][1]);
			UFDouble nValue = new UFDouble((String) data[i][2]);
			if (nCheckNum.doubleValue() >= nStart.doubleValue() && nCheckNum.doubleValue() < nEnd.doubleValue()) {
				nSampleRatio = nValue;
				break;
			}
		}

		// 如果未能获得抽样比例，则比较末行的数量，以获得抽样比例
		if (nSampleRatio == null) {
			UFDouble nStart = new UFDouble((String) data[data.length - 1][0]);
			UFDouble nValue = new UFDouble((String) data[data.length - 1][2]);
			if (nCheckNum.doubleValue() >= nStart.doubleValue()) {
				nSampleRatio = nValue;
			}
		}

		return nSampleRatio;
	}

	/**
   * ?user> 功能： 参数： 返回： 例外： 日期：(2004-5-20 15:38:04) 修改日期，修改人，修改原因，注释标志：
   */
	private Object[][] getStandardValue(String sValue) {

		return CheckstandardItemVO.getStandardValue(sValue);// modify by yye

	}

	/**
   * ?user> 功能： 参数： 返回： 例外： 日期：(2004-5-20 15:38:04) 修改日期，修改人，修改原因，注释标志：
   */
	private void getRefreshedBillSourceInfo() {

		Vector lvec_SourceBillTypeCode = new Vector();
		Vector lvec_SourceBillHID = new Vector();
		Vector lvec_SourceBillBID = new Vector();

		if (m_checkBillVOs == null) {
			return;
		}

		for (int i = 0; i < m_checkBillVOs.length; i++) {
			CheckbillHeaderVO l_CheckbillHeaderVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
			if (i == 0) {
				// 设置自由项
				nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
				freeVOParse.setFreeVO(new CheckbillHeaderVO[] { l_CheckbillHeaderVO }, "vfreeitem", "vfree", null, "cmangid",
						false);
				//
			}
			CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[i].getChildrenVO();
			if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
				for (int j = 0; j < l_CheckbillItemVO.length; j++) {
					if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null && l_CheckbillItemVO[j].getCsourcebillid() != null
							&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
						lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
						lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
						lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
					}
				}
			}
		}
		String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
		String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
		String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

		lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
		lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
		lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

		SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
		m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode, lstrary_SourceBillHID,
				lstrary_SourceBillBID);

	}

	/**
   * 子类实现该方法，返回业务界面的标题。
   * 
   * @version (00-6-6 13:33:25)
   * @return java.lang.String
   */
	public String getTitle() {
		if (m_cardBill != null)
			return m_cardBill.getTitle();
		return nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000065")/*
                                                                                         * @res
                                                                                         * "维护质量检验单"
                                                                                         */;
	}

	/**
   * 功能描述:改变界面按钮状态
   * 
   * @author czp
   */
	public void updateButtonsAll() {
		int iLen = buttonManager.getBtnTree().getButtonArray().length;
		for (int i = 0; i < iLen; i++) {
			update1(buttonManager.getBtnTree().getButtonArray()[i]);
		}
	}

	public void updateButton(ButtonObject bo) {
		super.updateButton(bo);
	}

	/**
   * 更新按钮状态（递归方式）
   * 
   * @author czp
   */
	private void update1(ButtonObject bo) {
		updateButton(bo);
		if (bo.getChildCount() > 0) {
			for (int i = 0, len = bo.getChildCount(); i < len; i++)
				update(bo.getChildButtonGroup()[i]);
		}
	}

	/**
   * 类初始化方法。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author czp
   * @time 2007-6-25 下午03:55:37
   */
	public void init() {

		// 初始化参数
		initpara();

		// 初始化检验类型对话框
		m_dialog = new nc.ui.qc.type.ChecktypeDlg(getClientEnvironment().getDesktopApplet());
		m_dialog.showModal();

		// 初始化页签
		String keyType = m_dialog.getChecktypeid();
		if (keyType == null) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001119")/*
                                                                                                                     * @res
                                                                                                                     * "初始化"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000028")/*
                                                                                         * @res
                                                                                         * "未选择检验类型！"
                                                                                         */);
			throw new Error(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000028")/*
                                                                                                     * @res
                                                                                                     * "未选择检验类型！"
                                                                                                     */);
		}
		// 初始化多页签和面板
		String key = m_dialog.getCheckinfoid();
		if (key != null && key.trim().length() > 0) {
			m_tabbedPane = new UITabbedPane(3);
			m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC0020101-000026")/*
                                                                                                         * @res
                                                                                                         * "检验单"
                                                                                                         */, getBillCardPanel());
			m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC00102-000010")/*
                                                                                                       * @res
                                                                                                       * "检验信息"
                                                                                                       */, getBillCardPanelInfo());
		} else {
			m_tabbedPane = new UITabbedPane(1);
			m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC0020101-000026")/*
                                                                                                         * @res
                                                                                                         * "检验单"
                                                                                                         */, getBillCardPanel());
		}
		m_tabbedPane.addChangeListener(this);

		// 初始化摸板参数
		strBilltemplet = m_dialog.getCheckbilltypecode();
		if (strBilltemplet.equalsIgnoreCase("4Z")) {
			strBilltemplet = "WS";
		} else if (strBilltemplet.equalsIgnoreCase("3V")) {
			strBilltemplet = "WP";
		} else if (strBilltemplet.equalsIgnoreCase("A2") || strBilltemplet.equalsIgnoreCase("A4")) {
			strBilltemplet = "WQ";
		} else if (strBilltemplet.equalsIgnoreCase("23")) {
			strBilltemplet = "WH";
		} else {
			strBilltemplet = "WR";
		}

		// 加载页签
		setLayout(new java.awt.BorderLayout());
		add(m_tabbedPane, java.awt.BorderLayout.CENTER);
		getBillCardPanel().setEnabled(false);

		// 初始时，检验信息页签不可用
		m_tabbedPane.setEnabledAt(1, false);
		// 初始化参照
		// initBillReference();
		if (key != null && key.trim().length() > 0)
			initInfoReference();

		// 初始化表体批次号参照
		// initLotNumbRefPane();

		// 显示标准类型下拉框
		if (key != null && key.trim().length() > 0) {
			getBillCardPanelInfo().getBodyItem("icheckstandard").setWithIndex(true);
			m_comType = (UIComboBox) getBillCardPanelInfo().getBodyItem("icheckstandard").getComponent();
			m_comType.setTranslate(true);
			if (m_comType.getItemCount() == 0) {
				m_comType.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                         * @res
                                                                                                         * "数值型"
                                                                                                         */);
				m_comType.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000030")/*
                                                                                                         * @res
                                                                                                         * "枚举型"
                                                                                                         */);
			}
			for (int i = 2; i < 11; i++) {
				getBillCardPanelInfo().getBodyItem("icheckstandard" + String.valueOf(i)).setWithIndex(true);
				UIComboBox m_comType1 = (UIComboBox) getBillCardPanelInfo().getBodyItem("icheckstandard" + String.valueOf(i))
						.getComponent();
				m_comType1.setTranslate(true);
				if (m_comType1.getItemCount() == 0) {
					m_comType1.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                           * @res
                                                                                                           * "数值型"
                                                                                                           */);
					m_comType1.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000030")/*
                                                                                                           * @res
                                                                                                           * "枚举型"
                                                                                                           */);
				}
			}
		}
		buttonManager = new CheckbillButtonManager(this);
		// 初始化检验项目权限sql
		initDataPower();
		// 实例化各界面的按钮
		buttonManager.initButtons();

		// 设置按钮(包含按钮扩展)
		setButtons(buttonManager.getBtnTree().getButtonArray());

		// 设置按钮状态
		buttonManager.setButtonsStateCard();
	}

	/**
   * 初始化检验项目权限sql
   */
	private void initDataPower() {
		// 取质量检验项目权限sql
		IPowerManageQuery pwerSrv = (IPowerManageQuery) NCLocator.getInstance().lookup(IPowerManageQuery.class.getName());
		UserPowerQueryVO queryVO = new UserPowerQueryVO();
		queryVO.setOrgTypeCode(IOrgType.COMPANY_TYPE);
		queryVO.setCorpPK(m_sUnitCode);
		queryVO.setOrgPK(m_sUnitCode);
		queryVO.setResouceId(118);
		queryVO.setUserPK(ClientEnvironment.getInstance().getUser().getPrimaryKey());
		try {
			strSubSqlPower = pwerSrv.getSql4UserPower(queryVO);
			if (strSubSqlPower != null && strSubSqlPower.trim().length() > 0) {
				strSubSqlPower = " (qc_checkbill_b2.ccheckitemid is null or qc_checkbill_b2.ccheckitemid in (" + strSubSqlPower
						+ "))";
				CheckbillB2ItemVO[] bodyVOs = CheckbillHelper.queryB2ItemsByDataPower(null, strSubSqlPower);
				if (bodyVOs != null && bodyVOs.length > 0) {
					for (int i = 0; i < bodyVOs.length; i++) {
						if (!hPowerCheckitemID.containsKey(bodyVOs[i].getCcheckitemid())) {
							hPowerCheckitemID.put(bodyVOs[i].getCcheckitemid(), null);
						}
					}
				}
			}
		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
   * 是否手工报检
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-25 下午04:24:27
   */
	public boolean isFromQC() {
		if (strBilltemplate != null) {
			return "WR".equals(strBilltemplate);
		}
		return "WH".equals(PuPubVO.getString_TrimZeroLenAsNull(m_dialog.getCheckbilltypecode()));
	}

	/**
   * 是否库存冻结报检
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-25 下午04:24:27
   */
	public boolean isFromIC() {
		if (strBilltemplate != null) {
			return "WS".equals(strBilltemplate);
		}
		return "4Z".equals(PuPubVO.getString_TrimZeroLenAsNull(m_dialog.getCheckbilltypecode()));
	}

	/**
   * 是否采购到货报检
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-25 下午04:24:27
   */
	private boolean isFromPU() {
		if (strBilltemplate != null) {
			return "WH".equals(strBilltemplate);
		}
		return "23".equals(PuPubVO.getString_TrimZeroLenAsNull(m_dialog.getCheckbilltypecode()));
	}

	/**
   * 是否完工报检
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-25 下午04:24:27
   */
	public boolean isFromMM() {
		if (strBilltemplate != null) {
			return "WQ".equals(strBilltemplate);
		}
		return "A4".equals(PuPubVO.getString_TrimZeroLenAsNull(m_dialog.getCheckbilltypecode()));
	}

	/**
   * 初始化审批流 2007-01-05 xhq
   * <p>
   * modified by czp since v53, 重构按钮下标为按钮名称
   */
	private void initWorkflow() {
		// 初始化按钮

		initpara();

		if (strBilltemplate == null || strBilltemplate.trim().length() == 0) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("100902", "UPT100902-000002")/*
                                                                                                                 * @res
                                                                                                                 * "初始化"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000028")/*
                                                                                         * @res
                                                                                         * "未选择检验类型！"
                                                                                         */);
			return;
		}

		this.setButtons(buttonManager.getBtnTree().getButtonArray());

		if (strBilltemplate != null && strBilltemplate.trim().length() > 0) {
			m_tabbedPane = new UITabbedPane(3);
			m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC0020101-000026")/*
                                                                                                         * @res
                                                                                                         * "检验单"
                                                                                                         */, getBillCardPanel());
			m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC00102-000010")/*
                                                                                                       * @res
                                                                                                       * "检验信息"
                                                                                                       */, getBillCardPanelInfo());
		} else {
			m_tabbedPane = new UITabbedPane(1);
			m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC0020101-000026")/*
                                                                                                         * @res
                                                                                                         * "检验单"
                                                                                                         */, getBillCardPanel());
		}
		m_tabbedPane.addChangeListener(this);

		// 加载页签
		setLayout(new java.awt.BorderLayout());
		add(m_tabbedPane, java.awt.BorderLayout.CENTER);
		getBillCardPanel().setEnabled(false);

		// 初始时，检验信息页签不可用
		m_tabbedPane.setEnabledAt(1, false);
		//

		// 初始化参照
		// initBillReference();
		if (strBilltemplate != null && strBilltemplate.trim().length() > 0)
			initInfoReference();

		// 显示标准类型下拉框
		if (strBilltemplate != null && strBilltemplate.trim().length() > 0) {
			getBillCardPanelInfo().getBodyItem("icheckstandard").setWithIndex(true);
			m_comType = (UIComboBox) getBillCardPanelInfo().getBodyItem("icheckstandard").getComponent();
			m_comType.setTranslate(true);
			if (m_comType.getItemCount() == 0) {
				m_comType.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000029")/*
                                                                                                         * @res
                                                                                                         * "数值型"
                                                                                                         */);
				m_comType.addItem(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000030")/*
                                                                                                         * @res
                                                                                                         * "枚举型"
                                                                                                         */);
			}
		}

		m_nUIState = 4;

		return;
	}

	/**
   * 方法功能描述：初始化批次档案参照。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>无
   * <p>
   * 
   * @author
   * @time 2007-7-31 下午02:47:03
   */
	private void initLotNumbRefPane() {
		for (int i = 0; i < sCheckStandards.length; i++) {
			BillItem bt = getBillCardPanel().getBodyItem("table" + String.valueOf(i), "vstobatchcode");
			if (m_LotNumbRefPane == null) {
				m_LotNumbRefPane = new LotNumbRefPane();
				m_LotNumbRefPane.setName("LotNumbRefPane");
				m_LotNumbRefPane.setMaxLength(bt.getLength());
			}
			if (bt != null)
				bt.setComponent(m_LotNumbRefPane);
		}
	}

	/**
   * 此处插入方法说明。 功能描述:参照设置 输入参数: 返回值: 异常处理: 日期:2002/05/09
   */
	private void initBillReference() {
		// 检验单头参照设置

		// 手工报检时设置辅计量参照
		if (m_dialog != null) {
			if (m_dialog.getCheckbilltypecode().trim().equals("WH")) {

				UIRefPane nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cassistunitid").getComponent();
				nRefPanel.setIsCustomDefined(true);
				nRefPanel.setReturnCode(false);
				nRefPanel.setRefModel(new OtherRefModel(OtherRefModel.REF_ASSMEAS));
			}
		} else {
			if (strBilltemplate.equals("WR")) {
				UIRefPane nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cassistunitid").getComponent();
				nRefPanel.setIsCustomDefined(true);
				nRefPanel.setReturnCode(false);
				nRefPanel.setRefModel(new OtherRefModel(OtherRefModel.REF_ASSMEAS));
			}
		}

		// 表头存货参照
		UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem("cinventorycode").getComponent();
		refPane.setReturnCode(true);

		// 设置检验类型
		UIRefPane nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cchecktypeid").getComponent();
		nRefPanel.setIsCustomDefined(true);
		nRefPanel.setRefModel(new ChecktypeDef());

		// 设置检验方式
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("ccheckmodeid").getComponent();
		nRefPanel.setIsCustomDefined(true);
		nRefPanel.setRefModel(new CheckmodeDef(m_sUnitCode));

		// 设置执行标准
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cexecstandard").getComponent();
		m_textExec = nRefPanel.getUITextField();
		// m_textExec.setLineStyle(true);
		m_textExec.addActionListener(this);

		// 设置工作中心
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cworkshopid").getComponent();
		nRefPanel.setIsCustomDefined(true);
		nRefPanel.setRefModel(new WorkshopDef(m_sUnitCode));

		// 设置生产订单
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("vproductordercode").getComponent();
		nRefPanel.setIsCustomDefined(true);
		nRefPanel.setRefModel(new ProductOrderDef(m_sUnitCode));

		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("vmemo").getComponent();
		nRefPanel.setReturnCode(false);
		nRefPanel.setAutoCheck(false);

		BillItem item = getBillCardPanel().getHeadItem("cvendormangid");
		if (item != null) {
			UIRefPane pane = (UIRefPane) item.getComponent();
			pane.getRef().getRefModel().setRefNameField("bd_cubasdoc.custshortname");
		}

		// 设置非负数
		// 表头检验数量
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("nchecknum").getComponent();
		UITextField text = nRefPanel.getUITextField();
		text.setDelStr("-");

		// 表头检验辅数量
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("nassistchecknum").getComponent();
		text = nRefPanel.getUITextField();
		text.setDelStr("-");

		// 表头换算率
		nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("nexchangerate").getComponent();
		text = nRefPanel.getUITextField();
		text.setDelStr("-");

		for (int i = 0; i < sCheckStandards.length; i++) {
			// 设置检验状态组
			nRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "ccheckstateid")
					.getComponent();
			nRefPanel.setIsCustomDefined(true);
			nRefPanel.setRefModel(new CheckstategroupDef(m_sUnitCode));

			// 设置检验标准
			nRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "ccheckstandardid")
					.getComponent();
			nRefPanel.setIsCustomDefined(true);
			nRefPanel.setRefModel(new CheckstandardDef(m_sUnitCode));
			// 表体辅计量参照
			UIRefPane ref = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "cchgassistunitname")
					.getComponent();
			ref.setIsCustomDefined(true);
			ref.setReturnCode(false);
			ref.setRefModel(new OtherRefModel(OtherRefModel.REF_ASSMEAS));

			// 检验单体参照设置
			// 设置检验状态
			nRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "ccheckstatename")
					.getComponent();
			nRefPanel.setIsCustomDefined(true);
			nRefPanel.setRefModel(new CheckstateDef(m_sUnitCode));

			// 设置处理方式
			nRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "cdefectprocessname")
					.getComponent();
			nRefPanel.setIsCustomDefined(true);
			nRefPanel.setRefModel(new DefectprocessDef(m_sUnitCode));

			// 设置不合格类型
			nRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "cdefecttypename")
					.getComponent();
			nRefPanel.setIsCustomDefined(true);
			nRefPanel.setRefModel(new DefecttypeDef(m_sUnitCode));

			// 表体的数量
			nRefPanel = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(i), "nnum").getComponent();
			text = nRefPanel.getUITextField();
			text.setDelStr("-");
		}
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:参照设置 输入参数: 返回值: 异常处理: 日期:2002/05/09
   */
	private void initInfoReference() {

		// 检验信息参照设置
		// 设置处理方式
		BillItem item = getBillCardPanelInfo().getBodyItem("ccheckitemname");
		if (item != null) {
			UIRefPane nRefPanel = (UIRefPane) item.getComponent();
			nRefPanel.setIsCustomDefined(true);
			nRefPanel.setRefModel(new CheckitemTreeDef(m_sUnitCode));
		}
		((UITextDocument) ((UIRefPane) getBillCardPanelInfo().getBodyItem("cresult").getComponent()).getUITextField()
				.getDocument()).setChange(UITextType.TextStr, 10, 8, "10", 0, Double.parseDouble("1000000"), Double
				.parseDouble("1000000"), true, true, false, false, 0);
		// item = getBillCardPanelInfo().getBodyItem("cresult");
		// if (item != null) {
		// item.setDataType(5);
		// UIRefPane nRefPanel = (UIRefPane) item.getComponent();
		// nRefPanel.setIsCustomDefined(true);
		// nRefPanel.setAutoCheck(false);
		// nRefPanel.setRefModel(new EnumCheckValueRefModel(m_sUnitCode));
		// }

		return;
	}

	/**
   * 此处插入方法说明。 功能描述:初始化查询模板 输入参数: 返回值: 异常处理: 日期:
   */
	private void initQuery() {

		// ********************注意**************************
		// 效率优化，将initQuery()改为由按钮触发，且只触发一次。
		// 这样已提高打开结点时的速度。合理分配用时。
		// yye 2005-06-17

		// ********************************************
	  String checkbillTypecode=null;
	  if(m_dialog!=null){
	    checkbillTypecode=m_dialog.getCheckbilltypecode();
	  }else{
	    checkbillTypecode="23";//todo 需根据id重新查找
	  }
		// 初始化查询模板
		m_condClient = new MultiCorpQueryClient(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
				"UPPC0020101-000031")/* @res "检验单查询" */, getClientEnvironment().getUser().getPrimaryKey(), m_sUnitCode,
				checkbillTypecode);

		changeQueryModelLayout();

		try {
			m_condClient.setTempletID(m_sUnitCode, "C0020101", getClientEnvironment().getUser().getPrimaryKey(), null);

			// 加载自定义项名称 (20100823 v57升级)
			nc.ui.scm.pub.def.DefSetTool.updateQueryConditionClientUserDef(m_condClient, // 当前模板
					m_sUnitCode, // 公司主键
					ScmConst.QC_CheckArrive, // 单据类型
					"checkbill.vdef", // 查询模板中单据头的自定义项前缀
					"checkbill_b1.vdef" // 查询模板中单据体的自定义项前缀
			);

		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000021")/*
                                                                                                                     * @res
                                                                                                                     * "加载模板"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000032")/*
                                                                                         * @res
                                                                                         * "模板数据不存在！"
                                                                                         */);
			return;
		}

		m_condClient.setValueRef("dpraydate", "日历"/*-=notranslate=-*/);

		m_condClient.setValueRef("dauditdate", "日历"/*-=notranslate=-*/);

		UIRefPane ref1 = new UIRefPane();
		ref1.setIsCustomDefined(true);
		m_modeDef = new CheckmodeDef(m_sUnitCode);
		ref1.setRefModel(m_modeDef);
		m_condClient.setValueRef("ccheckmodeid", ref1);

		UIRefPane ref2 = new UIRefPane();
		ref2.setIsCustomDefined(true);
		m_standardDef = new CheckstandardDef(m_sUnitCode);
		ref2.setRefModel(m_standardDef);
		m_condClient.setValueRef("ccheckstandardid", ref2);

		UIRefPane ref4 = new UIRefPane();
		ref4.setRefNodeName("部门档案"/*-=notranslate=-*/);
		m_deptDef = ref4.getRefModel();
		m_condClient.setValueRef("cpraydeptid", ref4);
		m_condClient.setValueRef("ccheckdeptid", ref4);

		UIRefPane ref5 = new UIRefPane();
		ref5.setRefNodeName("存货档案"/*-=notranslate=-*/);
		m_invDef = ref5.getRefModel();
		m_condClient.setValueRef("cbaseid", ref5);

		UIRefPane ref6 = new UIRefPane();
		ref6.setRefNodeName("操作员"/*-=notranslate=-*/);
		m_operatorDef = ref6.getRefModel();
		m_condClient.setValueRef("cprayerid", ref6);
		m_condClient.setValueRef("cauditpsn", ref6);

		UIRefPane ref7 = new UIRefPane();
		ref7.setRefNodeName("供应商档案"/*-=notranslate=-*/);
		m_condClient.setValueRef("cvendorbaseid", ref7);

		UIRefPane ref8 = new UIRefPane();
		ref8.setIsCustomDefined(true);
		ref8.setRefModel(new nc.ui.qc.pub.CheckstategroupDef(m_sUnitCode));
		m_condClient.setValueRef("ccheckstateid", ref8);

		m_condClient.setDefaultValue("dpraydate", "dpraydate", getClientEnvironment().getDate().toString());

		m_condClient.setIsWarningWithNoInput(true);
		m_condClient.getUITabbedPane().addChangeListener(this);
		m_condClient.setSealedDataShow(true);

		UIRefPane ref9 = new UIRefPane();
		ref9.setIsCustomDefined(true);
		ref9.setRefModel(new CheckitemTreeDef(m_sUnitCode));
		m_condClient.setValueRef("ccheckitemid", ref9);

		// 初始化执行标准对话框
		m_dlgText = new UIDialog(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0002058")/*
                                                                                                           * @res
                                                                                                           * "执行标准"
                                                                                                           */);
		m_textPane = new UIEditorPane();
		m_textPane.setEditable(false);
		BillScrollPane scrollPane = new BillScrollPane(m_textPane);
		m_textPane.setBounds(10, 10, 375, 200);
		scrollPane.setBounds(9, 9, 377, 202);
		m_dlgText.getContentPane().add(scrollPane);

		// 数据权限控制
		m_condClient.setRefsDataPowerConVOs(nc.ui.pub.ClientEnvironment.getInstance().getUser().getPrimaryKey(),
				new String[] { nc.ui.pub.ClientEnvironment.getInstance().getCorporation().getPrimaryKey() }, new String[] {
						"供应商档案"/*-=notranslate=-*/, "部门档案"/*-=notranslate=-*/, "部门档案"/*-=notranslate=-*/, "存货档案"/*-=notranslate=-*/, "检验项目"/*-=notranslate=-*/ }, new String[] { "cvendorbaseid", "ccheckdeptid", "cpraydeptid",
						"cbaseid", "ccheckitemid" }, new int[] { 0, 2, 2, 0, 0 });
	}

	/**
   * 此处插入方法说明。 功能描述:检验累计数量是否大于报检数量 输入参数: 返回值:大于,返回FALSE;否则,返回TRUE 异常处理:
   * 日期:2002/06/13
   * 
   * @return boolean
   */
	private boolean isNumLegal() {
		try {
			Hashtable t = new Hashtable();
			Vector v = new Vector();

			int nRow = getBillCardPanel().getRowCount();
			double d = 0;// 合格数量

			for (int i = 0; i < nRow; i++) {
				Object oTemp = getBillCardPanel().getBodyValueAt(i, "nnum");
				if (oTemp != null && oTemp.toString().trim().length() > 0) {
					d += ((UFDouble) oTemp).doubleValue();
				}

				// ****
				Object sourcebillrowid = getBillCardPanel().getBodyValueAt(i, "csourcebillrowid");
				if (sourcebillrowid != null && oTemp != null && oTemp.toString().trim().length() > 0) {
					if (!v.contains(sourcebillrowid))
						v.addElement(sourcebillrowid);
					UFDouble dd = (UFDouble) oTemp;
					Object o = t.get(sourcebillrowid);
					if (o == null)
						t.put(sourcebillrowid, dd);
					else {
						dd = new UFDouble(dd.doubleValue() + ((UFDouble) o).doubleValue());
						t.put(sourcebillrowid, dd);
					}
				}
				// ****

			}

			String total = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject();
			if (total != null && total.trim().length() > 0) {
				double dd = (new UFDouble(total.trim())).doubleValue();
				if (getRoundDouble(dd) < getRoundDouble(d))
					return false;
			}

			if (v.size() > 0 && t.size() > 0) {
				String key[] = new String[v.size()];
				v.copyInto(key);
				Object value = CacheTool.getColumnValue("po_arriveorder_b", "carriveorder_bid", "narrvnum", key);

				if (value != null) {
					Object oValue[] = (Object[]) value;
					for (int i = 0; i < v.size(); i++) {
						Object oTemp = t.get(v.elementAt(i));
						if (oTemp != null && oValue[i] != null) {
							UFDouble ddd1 = new UFDouble(oValue[i].toString().trim());
							UFDouble ddd2 = new UFDouble(oTemp.toString().trim());
							if (Math.abs(ddd2.doubleValue()) > Math.abs(ddd1.doubleValue())) {
								return false;
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e) {
			SCMEnv.out(e);

			return false;
		}
	}

	/**
   * 此处插入方法说明。 功能描述:审批 输入参数: 返回值: 异常处理: 日期:2003/04/02
   */
	private void onCardAudit() {
		try {

			CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
			// 是否允许本人审批\弃审
			if (!m_bAllowSelf) {
				String lstr_Oper = getClientEnvironment().getUser().getPrimaryKey();
				String lstr_Reporter = headVO.getCreporterid();
				if (lstr_Oper.trim().equals(lstr_Reporter.trim())) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000059")/*
                                     * @res "错误"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000034")/*
                                                                                             * @res
                                                                                             * "不允许本人审批/弃审自己的单据！"
                                                                                             */);
					return;
				}
			}
			if (headVO.getCreporterid() == null) {
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON", "UPPSCMCommon-000059")/*
                                                                                                                         * @res
                                                                                                                         * "错误"
                                                                                                                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001112")/*
                                                                                           * @res
                                                                                           * "没有报告人！"
                                                                                           */);
				return;
			}
			boolean bQulityLevel = false;
			for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
				if (m_checkBillVOs[m_nBillRecord].getBodyVos()[0].getCcheckstandardid().equals(
						m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid())) {
					if (m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstate_bid() == null && !isFromQC()) {
						bQulityLevel = true;
						break;
					}
				}
			}
			if (bQulityLevel) {
				throw new BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("C0020101",
						"UPPC0020101-000009")/* @res "质量等级为空！" */);
			}
			// add by yye begin 2005-03-14
			/*
       * 质量检验单审批时增加检查： 关键检验项目的实际检验值不能为空， 但有成份检验标志的质量检验单不作此检查，
       * “是否返回检验结果”为“否”的检验类型的质量检验单审批时也不需进行此检查
       */

			// 得到当前检验类型的pk
			// 效率优化，只触发一次。
			// yye 2005-06-17
			// if (!mbol_hasReturncheck) {
			// String lStr_Checktypeid = m_dialog.getChecktypeid();
			//
			// ChecktypeVO[] lary_CheckTypeVOs =
			// ChecktypeHelper.queryAllChecktypes();
			// if (lary_CheckTypeVOs != null && lary_CheckTypeVOs.length > 0) {
			// for (int i = 0; i < lary_CheckTypeVOs.length; i++) {
			// if
			// (lary_CheckTypeVOs[i].getCchecktypeid().equals(lStr_Checktypeid))
			// {
			// // mbol_returncheck = lary_CheckTypeVOs[i]
			// // .getBreturncheck().booleanValue();
			// break;
			// }
			// }
			//
			// }
			//
			// mbol_hasReturncheck = true;
			// }
			// add by yye end 2005-03-14
			// -------------------v31sp1 zhongwei added 审批日期限制 审批日期不小于报告日期
			if (headVO.getDreportdate() == null) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPc0020101-000125")
				/* @res "报告日期为空不能审批，请检查单据不完整的内容" */);
				return;
			}

			String reportDate = headVO.getDreportdate().substring(0, 10);

			if (reportDate == null) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPc0020101-000125")
				/* @res "报告日期为空不能审批，请检查单据不完整的内容" */);
				return;
			}
			if (reportDate.compareTo(getClientEnvironment().getDate().toString()) > 0)
				throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPc0020101-000124")
				/* @res "审批日期小于制单日期，不能审批，请重新登陆\n质检单号：{#}" */.replaceAll("#", headVO.getVcheckbillcode()));

			// -------------------v31sp1 zhongwei added 审批日期限制 审批日期不小于报告日期

			// 给当前检验单赋操作员和时间
			headVO.setCauditpsn(getClientEnvironment().getUser().getPrimaryKey());
			headVO.setDauditdate(getClientEnvironment().getDate().toString());
			// 支持库存回写参数
			Object[] userOb = { getClientEnvironment().getUser().getPrimaryKey(), getClientEnvironment().getDate(),
					headVO.getPk_corp() };
			Object[] userObjAry = new Object[1];
			userObjAry[0] = userOb;
			HashMap hmPfExParams = new HashMap();
			CheckbillVO newVO = new CheckbillVO();
			headVO.setCcheckbillid(m_checkBillVOs[m_nBillRecord].getHeadVo().getCcheckbillid());
			headVO.setTs(m_checkBillVOs[m_nBillRecord].getHeadVo().getTs());
			newVO.setParentVO(headVO);
			// 此处要求平台重新加载单据，会导致审批人为空的现象，导致默认取了制单人
			// hmPfExParams.put(PfUtilBaseTools.PARAM_RELOAD_VO,
			// PfUtilBaseTools.PARAM_RELOAD_VO);
			// 报检单据参数，支持多模板审批脚本
			nc.ui.pub.pf.PfUtilClient.runBatch(this, "APPROVE", strBilltemplet, getClientEnvironment().getDate().toString(),
					new CheckbillVO[] { newVO }, userObjAry, null, hmPfExParams);
			if (!nc.ui.pub.pf.PfUtilClient.isSuccess())
				return;

			// 审批成功后刷新数据
			CheckbillVO VO = CheckbillHelper.queryCheckbillByHeadKey(headVO.getCcheckbillid());
			if (VO != null) {
				// 设置自由项
				nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
				freeVOParse.setFreeVO(new CheckbillHeaderVO[] { (CheckbillHeaderVO) VO.getParentVO() }, null, "cmangid", false);
				//
				m_checkBillVOs[m_nBillRecord] = VO;
				VO.getHeadVo().setVinvbatchcode(VO.getBodyVos()[0].getVstobatchcode());
			}
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000036")/*
                                                                                                                     * @res
                                                                                                                     * "检验单审批"
                                                                                                                     */, e.getMessage());
			SCMEnv.out(e);
			return;
		}
		this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000037")/*
                                                                                                       * @res
                                                                                                       * "检验单审批成功！"
                                                                                                       */);

		// 审批后置入批次档案的各个属性
		CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[m_nBillRecord].getChildrenVO();
		QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());

		// 审批成功后刷新界面
		getBillCardPanel().getBillData().setHeaderValueVO(m_checkBillVOs[m_nBillRecord].getHeadVo());
		HashMap hVO = new HashMap();
		for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
			if (!hVO.containsKey(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid())) {
				Vector vecVO = new Vector();
				vecVO.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				hVO.put(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid(), vecVO);
			} else {
				((Vector) hVO.get(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid()))
						.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
			}
		}
		for (int i = 0; i < sCheckStandards.length; i++) {
			Vector vecTemp = (Vector) hVO.get(sCheckStandards[i]);
			CheckbillItemVO[] voBody = new CheckbillItemVO[vecTemp.size()];
			vecTemp.copyInto(voBody);
			getBillCardPanel().getBillModel("table" + String.valueOf(i));
		}

		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();

		setValues();

		// add by yy begin
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();

		// add by yy end
		getBillCardPanel().getBodyTabbedPane().setSelectedIndex(0);
		calculateNum();

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH010")/*
                                                                                     * @res
                                                                                     * "审批检验单成功"
                                                                                     */);
	}

	/**
   * 当前界面是否是“检验单卡片”。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-26 下午04:46:42
   */
	private boolean isFromCard() {
		return getBillCardPanel().isShowing();
	}

	/**
   * 当前界面是否是“检验单列表”。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-26 下午04:46:42
   */
	private boolean isFromList() {
		return getBillListPanel().isShowing();
	}

	/**
   * 当前界面是否是“检验单信息卡片”。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-26 下午04:46:42
   */
	private boolean isFromCardInfo() {
		return getBillCardPanelInfo().isShowing();
	}

	/**
   * 当前界面是否是“主料质量卡片”。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-26 下午04:46:42
   */
	private boolean isFromCardMM() {
		return getBillCardPanelMMQuery().isShowing();
	}

	/**
   * 当前界面是否是“主料质量列表”。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-26 下午04:46:42
   */
	private boolean isFromListMM() {
		return getBillListPanelMMQuery().isShowing();
	}

	/**
   * 当前界面是否是“审批流界面”。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @return
   * <p>
   * @author czp
   * @time 2007-6-26 下午04:46:42
   */
	private boolean isFromWorkFlow() {
		return m_bWorkFlow;
	}

	/**
   * 按钮响应事件--检验结果卡片。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param bo
   *          <p>
   * @author czp
   * @time 2007-6-27 上午09:01:49
   */
	private void onButtonClickedCard(ButtonObject bo) {

		// 卡片
		if (bo == buttonManager.m_btnAdd) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH028")/*
                                                                                       * @res
                                                                                       * "正在增加检验单"
                                                                                       */);
			onCardAdd();
		}

		else if (bo == buttonManager.m_btnModify) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH027")/*
                                                                                       * @res
                                                                                       * "正在修改检验单"
                                                                                       */);
			onCardModify();
		}

		else if (bo == buttonManager.m_btnDelBill) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH051")/*
                                                                                       * @res
                                                                                       * "正在删除检验单"
                                                                                       */);
			onCardDiscard();
		}

		else if (bo == buttonManager.m_btnSave) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH044")/*
                                                                                       * @res
                                                                                       * "正在保存检验单"
                                                                                       */);
			onCardSave();
		}

		else if (bo == buttonManager.m_btnCancel) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH045")/*
                                                                                       * @res
                                                                                       * "正在取消"
                                                                                       */);
			onCardCancel();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH008")/*
                                                                                       * @res
                                                                                       * "取消成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnLineAdd) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH056")/*
                                                                                       * @res
                                                                                       * "正在增行"
                                                                                       */);
			if (sCheckStandards == null) {
				// 提示该存货未设置检验标准或检验方式
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000099")/*
                                                                                                                       * @res
                                                                                                                       * "报检单"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000101")/*
                                                                                           * @res
                                                                                           * "检验标准不能为空！"
                                                                                           */);
				this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000101")/*
                                                                                                           * @res
                                                                                                           * "检验标准不能为空！"
                                                                                                           */);
				return;
			}
			int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
			getBillCardPanel().addLine("table" + String.valueOf(index));
			onCardAddLine(index);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH036")/*
                                                                                       * @res
                                                                                       * "增行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnLineDel) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH057")/*
                                                                                       * @res
                                                                                       * "正在删行"
                                                                                       */);
			onCardDelLine();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH037")/*
                                                                                       * @res
                                                                                       * "删行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnLineIns) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH058")/*
                                                                                       * @res
                                                                                       * "正在插入行"
                                                                                       */);
			onCardInsertLine();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH038")/*
                                                                                       * @res
                                                                                       * "插入行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnLineCpy) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH059")/*
                                                                                       * @res
                                                                                       * "正在复制行"
                                                                                       */);
			onCardCopyLine();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH039")/*
                                                                                       * @res
                                                                                       * "复制行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnLinePst) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH060")/*
                                                                                       * @res
                                                                                       * "正在粘贴行"
                                                                                       */);
			onCardPasteLine();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH040")/*
                                                                                       * @res
                                                                                       * "粘贴行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnSwitch) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH055")/*
                                                                                       * @res
                                                                                       * "正在切换检验单"
                                                                                       */);
			onCardSwitch();
		}

		else if (bo == buttonManager.m_btnFirst) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH031")/*
                                                                                       * @res
                                                                                       * "正在显示首页检验单"
                                                                                       */);
			onCardFirst();
		}

		else if (bo == buttonManager.m_btnPrev) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH033")/*
                                                                                       * @res
                                                                                       * "正在显示上页检验单"
                                                                                       */);
			onCardPrevious();
		}

		else if (bo == buttonManager.m_btnNext) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH034")/*
                                                                                       * @res
                                                                                       * "正在显示下页检验单"
                                                                                       */);
			onCardNext();
		}

		else if (bo == buttonManager.m_btnLast) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH032")/*
                                                                                       * @res
                                                                                       * "正在显示末页检验单"
                                                                                       */);
			onCardLast();
		}

		else if (bo == buttonManager.m_btnLinkQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在联查"
                                                                                       */);
			onCardRelateQuery();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH009")/*
                                                                                       * @res
                                                                                       * "联查成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在查询检验单"
                                                                                       */);
			onCardQuery();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH009")/*
                                                                                       * @res
                                                                                       * "查询检验单成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH048")/*
                                                                                       * @res
                                                                                       * "正在审批检验单"
                                                                                       */);
			onCardAudit();
		} else if (bo == buttonManager.m_btnUnAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH049")/*
                                                                                       * @res
                                                                                       * "正在弃审检验单"
                                                                                       */);
			onCardUnAudit();
		}

		else if (bo == buttonManager.m_btnLocate) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH054")/*
                                                                                       * @res
                                                                                       * "正在定位"
                                                                                       */);
			onCardLocate();
		}

		else if (bo == buttonManager.m_btnRefresh) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在刷新"
                                                                                       */);
			onCardRefresh();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH007")/*
                                                                                       * @res
                                                                                       * "刷新成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnPrint) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH061")/*
                                                                                       * @res
                                                                                       * "正在打印"
                                                                                       */);
			onCardPrint();
		}

		else if (bo == buttonManager.m_btnWorkFlowQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH050")/*
                                                                                       * @res
                                                                                       * "正在进行审批状态查询"
                                                                                       */);
			onCardWorkFlowQuery();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH035")/*
                                                                                       * @res
                                                                                       * "审批状态查询成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnDocument) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000147")/*
                                                                                                     * @res
                                                                                                     * "正在文件管理..."
                                                                                                     */);
			onListDocument();
		}

		else if (bo == buttonManager.m_btnSendAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH047")/*
                                                                                       * @res
                                                                                       * "正在送审"
                                                                                       */);
			onCardSend();
		}

		else if (bo == buttonManager.m_btnMainMaterialQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在联查"
                                                                                       */);
			onCardMainMaterialQuery();
		} else if (bo == buttonManager.m_btnbatch) {
			showHintMessage(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000006")/*执行批处理*/);
			onShowBatchDLG();
		}
	}

	/**
   * 按钮响应事件--检验结果列表。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param bo
   *          <p>
   * @author czp
   * @time 2007-6-27 上午09:01:49
   */
	private void onButtonClickedList(ButtonObject bo) {
		if (bo == buttonManager.m_btnSelectAll) {
			onListSelectAll();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "4004COMMON000000033")/*
                                                                                                   * @res
                                                                                                   * "全选成功"
                                                                                                   */);
		}

		else if (bo == buttonManager.m_btnSelectNo) {
			onListSelectNo();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "4004COMMON000000034")/*
                                                                                                   * @res
                                                                                                   * "全消成功"
                                                                                                   */);
		}

		else if (bo == buttonManager.m_btnModify) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH027")/*
                                                                                       * @res
                                                                                       * "正在修改"
                                                                                       */);
			onListModify();
		}

		else if (bo == buttonManager.m_btnDelBill) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH051")/*
                                                                                       * @res
                                                                                       * "正在删除检验单"
                                                                                       */);
			onListDiscard();
		}

		else if (bo == buttonManager.m_btnLinkQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在联查单据成功"
                                                                                       */);
			onListRelateQuery();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH009")/*
                                                                                       * @res
                                                                                       * "联查单据成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在查询检验单"
                                                                                       */);
			onListQuery();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH009")/*
                                                                                       * @res
                                                                                       * "查询检验单成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnRefresh) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH046")/*
                                                                                       * @res
                                                                                       * "正在刷新"
                                                                                       */);
			onListRefresh();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH007")/*
                                                                                       * @res
                                                                                       * "刷新成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnSwitch) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH055")/*
                                                                                       * @res
                                                                                       * "正在切换"
                                                                                       */);
			onListSwitch();
		}

		else if (bo == buttonManager.m_btnPrint) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH061")/*
                                                                                       * @res
                                                                                       * "正在打印"
                                                                                       */);
			onListPrint();
		}

		else if (bo == buttonManager.m_btnDocument) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000147")/*
                                                                                                     * @res
                                                                                                     * "正在文件管理..."
                                                                                                     */);
			onListDocument();
		}
		// 审批
		else if (bo == buttonManager.m_btnAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH048")/*
                                                                                       * @res
                                                                                       * "正在审批检验单"
                                                                                       */);
			onListAudit();
		}
		// 弃审
		else if (bo == buttonManager.m_btnUnAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH049")/*
                                                                                       * @res
                                                                                       * "正在弃审检验单"
                                                                                       */);
			onListUnAudit();
		}
	}

	/**
   * 此处插入方法说明。 功能描述:列表“审批” 输入参数: 返回值: 异常处理: 日期:2002/05/08
   */
	private void onListAudit() {
		// 还未查询表体信息的单据，查询表体信息 comment by yye
		Vector v1 = new Vector();
		Vector v2 = new Vector();
		for (int i = 0; i < m_checkBillVOs.length; i++) {
			int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
			if (nStatus == BillModel.SELECTED) {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
				if (headVO.getCreporterid() == null) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000059")/*
                                     * @res "错误"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001112")/*
                                                                                             * @res
                                                                                             * "没有报告人！"
                                                                                             */);
					return;
				}
				v1.addElement(headVO.getCcheckbillid());
				v2.addElement(headVO.getTs());

			}
		}

		String headKey[] = new String[v1.size()];
		String ts[] = new String[v2.size()];
		v1.copyInto(headKey);
		v2.copyInto(ts);
		if (headKey != null && headKey.length > 0) {
			try {
				ArrayList list = CheckbillHelper.queryCheckbillItemAndInfoBatch(headKey, ts);
				if (list == null || list.size() == 0)
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                     * @res
                                                                                                                     * "并发操作，请刷新！"
                                                                                                                     */);
				int j = 0;
				for (int i = 0; i < m_checkBillVOs.length; i++) {
					int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
					if (!m_bBodyQuery[i].booleanValue() && nStatus == BillModel.SELECTED) {
						CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
						ArrayList list0 = (ArrayList) list.get(j);
						if (list0 == null || list0.size() == 0)
							throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作，请刷新！"
                                                                                                                         */);
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list0.get(0);
						CheckbillB2VO grandVO[] = null;
						// String sTemp[] = null;
						// Object oTemp = list0.get(2);
						// if(oTemp != null) sTemp = (String[]) oTemp;
						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());
							// headVO.setCsourcebillcode(bodyVO[0].getCsourcebillcode());
							// headVO.setCsourcebilltypecode(bodyVO[0].getCsourcebilltypecode());
							// headVO.setCsourcebillid(bodyVO[0].getCsourcebillid());
							// headVO.setCsourcebillrowid(bodyVO[0].getCsourcebillrowid());
							// 设置检验单头的工序信息
							// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
							// 设置自由项
							headVO.setVfree1(bodyVO[0].getVfree1());
							headVO.setVfree2(bodyVO[0].getVfree2());
							headVO.setVfree3(bodyVO[0].getVfree3());
							headVO.setVfree4(bodyVO[0].getVfree4());
							headVO.setVfree5(bodyVO[0].getVfree5());
							// if(sTemp != null && sTemp.length > 0)
							// headVO.setVfreeitem(sTemp[j]);
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
							headVO.setVinvbatchcode(bodyVO[0].getVstobatchcode());
						}

						Object o = list0.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
						}
						m_checkBillVOs[i].setChildrenVO(bodyVO);
						m_checkBillVOs[i].setGrandVO(grandVO);

						m_bBodyQuery[i] = new UFBoolean(true);

						// 计算合格数量等
						calculateNum();
						j++;
					}
				}
			} catch (BusinessException e) {
				SCMEnv.out(e);
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作"
                                                                                                                       */, e.getMessage());
				return;
			} catch (Exception e) {
				SCMEnv.out(e);
				return;
			}
		}
		//

		// 获得选择的记录
		Vector vTemp1 = new Vector(); // 选择的记录
		Vector vTemp2 = new Vector(); // 未选择的记录
		Vector vTemp3 = new Vector();
		int nRow = getBillListPanel().getHeadBillModel().getRowCount();
		for (int i = 0; i < nRow; i++) {
			int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
			if (nStatus == BillModel.SELECTED) {
				vTemp1.addElement(m_checkBillVOs[i]);
			} else {
				vTemp2.addElement(m_checkBillVOs[i]);
				vTemp3.addElement(m_bBodyQuery[i]);
			}
		}

		if (vTemp1.size() == 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000027")/*
                                                                                                               * @res
                                                                                                               * "审批"
                                                                                                               */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000033")/*
                                                                                         * @res
                                                                                         * "未选中单据！"
                                                                                         */);
			return;
		}

		// 是否允许本人审批\弃审
		if (!m_bAllowSelf) {
			String lstr_Oper = getClientEnvironment().getUser().getPrimaryKey();
			for (int i = 0; i < vTemp1.size(); i++) {
				String lstr_Reporter = ((CheckbillHeaderVO) ((CheckbillVO) vTemp1.elementAt(i)).getParentVO()).getCreporterid();
				if (lstr_Oper.trim().equals(lstr_Reporter.trim())) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000059")/*
                                     * @res "错误"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000034")/*
                                                                                             * @res
                                                                                             * "不允许本人审批/弃审自己的单据！"
                                                                                             */);
					return;
				}
			}
		}

		this.showHintMessage("");

		try {
			// 给所有检验单赋操作员和时间
			CheckbillVO tempVO[] = new CheckbillVO[vTemp1.size()];
			vTemp1.copyInto(tempVO);
			for (int i = 0; i < tempVO.length; i++) {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) tempVO[i].getParentVO();
				headVO.setCauditpsn(getClientEnvironment().getUser().getPrimaryKey());
				headVO.setDauditdate(getClientEnvironment().getDate().toString());
			}

			// add by yye begin 2005-03-14
			/*
       * 质量检验单审批时增加检查： 关键检验项目的实际检验值不能为空， 但有成份检验标志的质量检验单不作此检查，
       * “是否返回检验结果”为“否”的检验类型的质量检验单审批时也不需进行此检查
       */

			// 得到当前检验类型的pk
			String lStr_Checktypeid = null;
			if(m_dialog==null){
			  if(tempVO[0]!=null){
			    lStr_Checktypeid=tempVO[0].getHeadVo().getCchecktypeid();
			  }
			}else{
			  lStr_Checktypeid=  m_dialog.getChecktypeid();
			}
			//
			boolean lbol_returncheck = false;
			ChecktypeVO[] lary_CheckTypeVOs = ChecktypeHelper.queryAllChecktypes();
			if (lary_CheckTypeVOs != null && lary_CheckTypeVOs.length > 0) {
				for (int i = 0; i < lary_CheckTypeVOs.length; i++) {
					if (lary_CheckTypeVOs[i].getCchecktypeid().equals(lStr_Checktypeid)) {
						lbol_returncheck = lary_CheckTypeVOs[i].getBreturncheck().booleanValue();
						break;
					}
				}

			}

			if (lbol_returncheck) {

				for (int ii = 0; ii < tempVO.length; ii++) {

					// 是否成分报检
					boolean lbol_compcheck = false;
					if (m_dialog.getCheckbilltypecode().trim().equals("A4")) {
						CheckbillHeaderVO headVO = (CheckbillHeaderVO) tempVO[ii].getParentVO();
						if (headVO.getBcompcheck() != null && headVO.getBcompcheck().toString().trim().length() > 0) {
							lbol_compcheck = headVO.getBcompcheck().booleanValue();
						}
					}

					if (!lbol_compcheck) {

						CheckbillB2VO[] grandVOs = (CheckbillB2VO[]) tempVO[ii].getGrandVO();
						if (grandVOs != null && grandVOs.length > 0) {
							for (int i = 0; i < grandVOs.length; i++) {
								CheckbillB2ItemVO itemVO[] = (CheckbillB2ItemVO[]) grandVOs[i].getChildrenVO();
								if (itemVO != null && itemVO.length > 0) {
									for (int j = 0; j < itemVO.length; j++) {

										if (itemVO[j].getBcritical() != null && itemVO[j].getBcritical().booleanValue()) {
											if (itemVO[j].getCresult() == null || itemVO[j].getCresult().trim().length() == 0) {
												// MessageDialog
												// .showHintDlg(
												// this,
												// nc.ui.ml.NCLangRes
												// .getInstance()
												// .getStrByID(
												// "SCMCOMMON",
												// "UPPSCMCommon-000059")/*
												// * @res
												// * "错误"
												// */,
												// nc.ui.ml.NCLangRes
												// .getInstance()
												// .getStrByID(
												// "C0020101",
												// "UPPC0020101-000035")/*
												// * @res
												// * "检验信息必须输入实际检验值！"
												// */);
												// return;
											}

										}

									}
								}
							}
						}
					}

				}

			}

			// add by yye end 2005-03-14

			// -------------------v31sp1 zhongwei added 审批日期限制 审批日期不小于报告日期

			Object[] userObjAry = new Object[tempVO.length];
			Vector v = new Vector();
			for (int i = 0, len = tempVO.length; i < len; i++) {
				String reportDate = ((CheckbillHeaderVO) tempVO[i].getParentVO()).getDreportdate().substring(0, 10);
				Object[] userObj = { getClientEnvironment().getUser().getPrimaryKey(), getClientEnvironment().getDate(),
						tempVO[i].getHeadVo().getPk_corp() };
				userObjAry[i] = userObj;
				if (reportDate != null && reportDate.compareTo(getClientEnvironment().getDate().toString()) > 0)
					v.add(((CheckbillHeaderVO) tempVO[i].getParentVO()).getVcheckbillcode());
			}

			if (v.size() > 0)
				throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPc0020101-000124")
				/* @res "审批日期小于制单日期，不能审批\n质检单号：{#}" */.replaceAll("#", getInvalidateCheckbillCode(v)));

			// -------------------v31sp1 zhongwei added 审批日期限制 审批日期不小于报告日期

			// 报检单据参数，支持多模板审批脚本
			nc.ui.pub.pf.PfUtilClient.processBatchFlow(this, "APPROVE", strBilltemplet, getClientEnvironment().getDate()
					.toString(), tempVO, userObjAry);
			if (!nc.ui.pub.pf.PfUtilClient.isSuccess())
				return;
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000036")/*
                                                                                                                     * @res
                                                                                                                     * "检验单审批"
                                                                                                                     */, e.getMessage());
			SCMEnv.out(e);
			return;
		}

		// 审批后的检验单不再显示在界面
		if (vTemp2.size() == 0) {
			// 所有单据审批完毕
			m_bBodyQuery = null;
			m_checkBillVOs = null;
			m_grandVOs = null;
			getBillListPanel().getHeadBillModel().clearBodyData();
			getBillListPanel().getHeadBillModel().updateValue();
			getBillListPanel().getBodyBillModel().clearBodyData();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();
			//
			// //除查询,打印外所有按钮为灰
			// for (int i = 0; i < 8; i++) {
			// if (i == 4 || i == 7)
			// m_nButtonState[i] = 0;
			// else
			// m_nButtonState[i] = 1;
			// }
			//
			// changeButtonState();
			m_tabbedPane.setEnabledAt(1, false);
		} else {
			// 缓存还有单据
			m_checkBillVOs = new CheckbillVO[vTemp2.size()];
			vTemp2.copyInto(m_checkBillVOs);
			m_bBodyQuery = new UFBoolean[vTemp3.size()];
			vTemp3.copyInto(m_bBodyQuery);

			m_nBillRecord = 0;

			// 若表体未查询，则查询表体
			if (!m_bBodyQuery[m_nBillRecord].booleanValue()) {
				try {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs());
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);

					if (list != null && list.size() > 0) {
						// 有返回结果
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
						CheckbillB2VO grandVO[] = null;
						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());
							// headVO.setCsourcebillcode(bodyVO[0].getCsourcebillcode());
							// headVO.setCsourcebilltypecode(bodyVO[0].getCsourcebilltypecode());
							// headVO.setCsourcebillid(bodyVO[0].getCsourcebillid());
							// headVO.setCsourcebillrowid(bodyVO[0].getCsourcebillrowid());
							// 设置检验单头的工序信息
							// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
							// 设置自由项
							// headVO.setVfree1(bodyVO[0].getVfree1());
							// headVO.setVfree2(bodyVO[0].getVfree2());
							// headVO.setVfree3(bodyVO[0].getVfree3());
							// headVO.setVfree4(bodyVO[0].getVfree4());
							// headVO.setVfree5(bodyVO[0].getVfree5());
							// Object oTemp = list.get(2);
							// if(oTemp != null)
							// headVO.setVfreeitem(oTemp.toString());
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, null, "cmangid", false);
						}

						Object o = list.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
							m_grandVOs = grandVO;
						}

						m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
						m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);

						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);
						// 计算合格数量等
						calculateNum();

					} else {
						// 无返回结果
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000015")/*
                                       * @res "查询表体"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                               * @res
                                                                                               * "表体不存在！"
                                                                                               */);
						return;
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作"
                                                                                                                         */, e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/* @res "查询表体" */, e.getMessage());
					return;
				}
			} else
				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();

			// 刷新界面
			vTemp1 = new Vector();
			for (int i = 0; i < m_checkBillVOs.length; i++)
				vTemp1.addElement(m_checkBillVOs[i].getParentVO());
			CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp1.size()];
			vTemp1.copyInto(headVOs);
			vTemp1.clear();
			for (int i = 0; i < m_checkBillVOs[0].getBodyVos().length; i++) {
				if (m_checkBillVOs[0].getBodyVos()[i].getInorder().intValue() == 0) {
					vTemp1.add(m_checkBillVOs[0].getBodyVos()[i]);
				}
			}
			CheckbillItemVO[] bodyVOs = new CheckbillItemVO[vTemp1.size()];
			vTemp1.copyInto(bodyVOs);

			getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);
			getBillListPanel().getHeadBillModel().execLoadFormula();
			getBillListPanel().getHeadBillModel().updateValue();

			getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
			getBillListPanel().getBodyBillModel().execLoadFormula();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();
			m_tabbedPane.setEnabledAt(1, true);
		}
		setListSourceBillInfo();
		// 设置列表按钮状态
		buttonManager.setButtonsStateList();
		//
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH010")/*
                                                                                     * @res
                                                                                     * "审批检验单成功"
                                                                                     */);
	}

	/**
   * 此处插入方法说明。 功能描述:列表“弃审” 输入参数: 返回值: 异常处理: 日期:2002/05/08
   */
	private void onListUnAudit() {
		// //还未查询表体信息的单据，查询表体信息 comment by yye
		Vector v1 = new Vector();
		Vector v2 = new Vector();
		for (int i = 0; i < m_checkBillVOs.length; i++) {
			int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
			if (!m_bBodyQuery[i].booleanValue() && nStatus == BillModel.SELECTED) {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
				v1.addElement(headVO.getCcheckbillid());
				v2.addElement(headVO.getTs());
			}
		}
		String headKey[] = new String[v1.size()];
		String ts[] = new String[v2.size()];
		v1.copyInto(headKey);
		v2.copyInto(ts);
		if (headKey != null && headKey.length > 0) {
			try {
				ArrayList list = CheckbillHelper.queryCheckbillItemAndInfoBatch(headKey, ts);
				if (list == null || list.size() == 0)
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                     * @res
                                                                                                                     * "并发操作，请刷新！"
                                                                                                                     */);
				int j = 0;
				for (int i = 0; i < m_checkBillVOs.length; i++) {
					int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
					if (!m_bBodyQuery[i].booleanValue() && nStatus == BillModel.SELECTED) {
						CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
						ArrayList list0 = (ArrayList) list.get(j);
						if (list0 == null || list0.size() == 0)
							throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作，请刷新！"
                                                                                                                         */);
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list0.get(0);
						CheckbillB2VO grandVO[] = null;
						// String sTemp[] = null;
						// Object oTemp = list0.get(2);
						// if(oTemp != null) sTemp = (String[]) oTemp;
						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());
							// headVO.setCsourcebillcode(bodyVO[0].getCsourcebillcode());
							// headVO.setCsourcebilltypecode(bodyVO[0].getCsourcebilltypecode());
							// headVO.setCsourcebillid(bodyVO[0].getCsourcebillid());
							// headVO.setCsourcebillrowid(bodyVO[0].getCsourcebillrowid());
							// 设置检验单头的工序信息
							// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
							// 设置自由项
							headVO.setVfree1(bodyVO[0].getVfree1());
							headVO.setVfree2(bodyVO[0].getVfree2());
							headVO.setVfree3(bodyVO[0].getVfree3());
							headVO.setVfree4(bodyVO[0].getVfree4());
							headVO.setVfree5(bodyVO[0].getVfree5());
							// if(sTemp != null && sTemp.length > 0)
							// headVO.setVfreeitem(sTemp[j]);
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
							headVO.setVinvbatchcode(bodyVO[0].getVstobatchcode());
						}
						Object o = list0.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
						}

						m_checkBillVOs[i].setChildrenVO(bodyVO);
						m_checkBillVOs[i].setGrandVO(grandVO);

						m_bBodyQuery[i] = new UFBoolean(true);

						// 计算合格数量等
						calculateNum();
						j++;
					}
				}
			} catch (BusinessException e) {
				SCMEnv.out(e);
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作"
                                                                                                                       */, e.getMessage());
				return;
			} catch (Exception e) {
				SCMEnv.out(e);
				return;
			}
		}
		//

		// 获得选择的记录
		Vector vTemp1 = new Vector();// 选择的记录
		Vector vTemp2 = new Vector();// 未选择的记录
		Vector vTemp3 = new Vector();
		int nRow = getBillListPanel().getHeadBillModel().getRowCount();
		for (int i = 0; i < nRow; i++) {
			int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
			if (nStatus == BillModel.SELECTED) {
				vTemp1.addElement(m_checkBillVOs[i]);
			} else {
				vTemp2.addElement(m_checkBillVOs[i]);
				vTemp3.addElement(m_bBodyQuery[i]);
			}
		}

		if (vTemp1.size() == 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000028")/*
                                                                                                               * @res
                                                                                                               * "弃审"
                                                                                                               */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000033")/*
                                                                                         * @res
                                                                                         * "未选中单据！"
                                                                                         */);
			return;
		}

		// 是否允许本人审批\弃审
		if (!m_bAllowSelf) {
			String lstr_Oper = getClientEnvironment().getUser().getPrimaryKey();
			for (int i = 0; i < vTemp1.size(); i++) {
				String lstr_Reporter = ((CheckbillHeaderVO) ((CheckbillVO) vTemp1.elementAt(i)).getParentVO()).getCreporterid();
				if (lstr_Oper.trim().equals(lstr_Reporter.trim())) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000059")/*
                                     * @res "错误"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000034")/*
                                                                                             * @res
                                                                                             * "不允许本人审批/弃审自己的单据！"
                                                                                             */);
					return;
				}
			}
		}
		// 是否允许他人弃审单据
		if (!m_bAllow) {
			String lstr_Oper = getClientEnvironment().getUser().getPrimaryKey();
			for (int i = 0; i < vTemp1.size(); i++) {
				String lstr_Reporter = ((CheckbillHeaderVO) ((CheckbillVO) vTemp1.elementAt(i)).getParentVO()).getCreporterid();
				if (!lstr_Oper.trim().equals(lstr_Reporter.trim())) {
					MessageDialog.showHintDlg(this, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000007")/*非本人审核单据不允许弃审*/, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000007")/*非本人审核单据不允许弃审*/);
					return;
				}
			}
		}
		try {

			Object[] userObjAry = new Object[vTemp1.size()];
			// 给所有检验单赋操作员
			CheckbillVO tempVO[] = new CheckbillVO[vTemp1.size()];
			vTemp1.copyInto(tempVO);
			for (int i = 0; i < tempVO.length; i++) {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) tempVO[i].getParentVO();
				headVO.setCauditpsn(getClientEnvironment().getUser().getPrimaryKey());
				Object[] userObj = { getClientEnvironment().getUser().getPrimaryKey(), getClientEnvironment().getDate(),
						tempVO[i].getHeadVo().getPk_corp() };
				userObjAry[i] = userObj;
			}
			nc.ui.pub.pf.PfUtilClient.processBatch("UNAPPROVE", strBilltemplet, getClientEnvironment().getDate().toString(),
					tempVO, userObjAry);
			if (!nc.ui.pub.pf.PfUtilClient.isSuccess())
				return;
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000043")/*
                                                                                                                     * @res
                                                                                                                     * "检验单弃审"
                                                                                                                     */, e.getMessage());
			SCMEnv.out(e);
			return;
		}

		// 弃审后的检验单不再显示在界面
		if (vTemp2.size() == 0) {
			// 所有单据弃审完毕
			m_bBodyQuery = null;
			m_checkBillVOs = null;
			m_grandVOs = null;
			getBillListPanel().getHeadBillModel().clearBodyData();
			getBillListPanel().getHeadBillModel().updateValue();
			getBillListPanel().getBodyBillModel().clearBodyData();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();
			m_tabbedPane.setEnabledAt(1, false);
		} else {
			// 缓存还有单据
			m_checkBillVOs = new CheckbillVO[vTemp2.size()];
			vTemp2.copyInto(m_checkBillVOs);
			m_bBodyQuery = new UFBoolean[vTemp3.size()];
			vTemp3.copyInto(m_bBodyQuery);

			m_nBillRecord = 0;

			// 若表体未查询，则查询表体
			if (!m_bBodyQuery[m_nBillRecord].booleanValue()) {
				try {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs());
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);

					if (list != null && list.size() > 0) {
						// 有返回结果
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
						CheckbillB2VO grandVO[] = null;
						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());
							// headVO.setCsourcebillcode(bodyVO[0].getCsourcebillcode());
							// headVO.setCsourcebilltypecode(bodyVO[0].getCsourcebilltypecode());
							// headVO.setCsourcebillid(bodyVO[0].getCsourcebillid());
							// headVO.setCsourcebillrowid(bodyVO[0].getCsourcebillrowid());
							// 设置检验单头的工序信息
							// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
							// 设置自由项
							// headVO.setVfree1(bodyVO[0].getVfree1());
							// headVO.setVfree2(bodyVO[0].getVfree2());
							// headVO.setVfree3(bodyVO[0].getVfree3());
							// headVO.setVfree4(bodyVO[0].getVfree4());
							// headVO.setVfree5(bodyVO[0].getVfree5());
							// Object oTemp = list.get(2);
							// if(oTemp != null)
							// headVO.setVfreeitem(oTemp.toString());
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, null, "cmangid", false);
						}

						Object o = list.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
							m_grandVOs = grandVO;
						}

						m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
						m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);

						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);
						// 计算合格数量等
						calculateNum();

					} else {
						// 无返回结果
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000015")/*
                                       * @res "查询表体"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                               * @res
                                                                                               * "表体不存在！"
                                                                                               */);
						return;
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作"
                                                                                                                         */, e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/* @res "查询表体" */, e.getMessage());
					return;
				}
			} else
				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();

			// 刷新界面
			vTemp1 = new Vector();
			for (int i = 0; i < m_checkBillVOs.length; i++)
				vTemp1.addElement(m_checkBillVOs[i].getParentVO());
			CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp1.size()];
			vTemp1.copyInto(headVOs);
			vTemp1.clear();
			for (int i = 0; i < m_checkBillVOs[0].getBodyVos().length; i++) {
				if (m_checkBillVOs[0].getBodyVos()[i].getInorder().intValue() == 0) {
					vTemp1.add(m_checkBillVOs[0].getBodyVos()[i]);
				}
			}
			CheckbillItemVO[] bodyVOs = new CheckbillItemVO[vTemp1.size()];
			vTemp1.copyInto(bodyVOs);

			getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);
			getBillListPanel().getHeadBillModel().execLoadFormula();
			getBillListPanel().getHeadBillModel().updateValue();

			getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
			getBillListPanel().getBodyBillModel().execLoadFormula();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();
			m_tabbedPane.setEnabledAt(1, true);
		}
		setListSourceBillInfo();
		// 设置列表按钮状态
		buttonManager.setButtonsStateList();
		//
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH011")/*
                                                                                     * @res
                                                                                     * "弃审检验单成功"
                                                                                     */);
	}

	/**
   * 创建日期： 2005-9-15 功能描述：
   */
	private String getInvalidateCheckbillCode(Vector v) {
		String s = "";
		for (int i = 0, len = v.size(); i < len; i++) {
			s += v.get(i).toString() + ",";
		}

		return s.substring(0, s.length() - 1);
	}

	/**
   * 按钮响应事件--检验信息卡片。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param bo
   *          <p>
   * @author czp
   * @time 2007-6-27 上午09:01:49
   */
	private void onButtonClickedCardInfo(ButtonObject bo) {
		if (bo == buttonManager.m_btnAdd) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH028")/*
                                                                                       * @res
                                                                                       * "正在增加检验信息"
                                                                                       */);
			onInfoAdd();
		}

		else if (bo == buttonManager.m_btnModify) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH027")/*
                                                                                       * @res
                                                                                       * "正在修改检验信息"
                                                                                       */);
			onInfoModify();
		}

		else if (bo == buttonManager.m_btnDelBill) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH051")/*
                                                                                       * @res
                                                                                       * "正在删除检验信息"
                                                                                       */);
			onInfoDiscard();
		}

		else if (bo == buttonManager.m_btnLineAdd) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH056")/*
                                                                                       * @res
                                                                                       * "正在增行"
                                                                                       */);
			onInfoAddLine();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH036")/*
                                                                                       * @res
                                                                                       * "增行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnLineDel) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH057")/*
                                                                                       * @res
                                                                                       * "正在删行"
                                                                                       */);
			onInfoDelLine();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH037")/*
                                                                                       * @res
                                                                                       * "删行成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnSave) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH044")/*
                                                                                       * @res
                                                                                       * "正在保存检验信息"
                                                                                       */);
			onInfoSave();
		}

		else if (bo == buttonManager.m_btnCancel) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH045")/*
                                                                                       * @res
                                                                                       * "正在取消"
                                                                                       */);
			onInfoCancel();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH008")/*
                                                                                       * @res
                                                                                       * "取消成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnFirst) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH031")/*
                                                                                       * @res
                                                                                       * "正在显示首页检验信息"
                                                                                       */);
			onInfoFirst();
		}

		else if (bo == buttonManager.m_btnPrev) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH033")/*
                                                                                       * @res
                                                                                       * "正在显示上页检验信息"
                                                                                       */);
			onInfoPrevious();
		}

		else if (bo == buttonManager.m_btnNext) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH034")/*
                                                                                       * @res
                                                                                       * "正在显示下页检验信息"
                                                                                       */);
			onInfoNext();
		}

		else if (bo == buttonManager.m_btnLast) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH032")/*
                                                                                       * @res
                                                                                       * "正在显示末页检验信息"
                                                                                       */);
			onInfoLast();
		}
		// 修订
		else if (bo == buttonManager.m_btnReviseInfo) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000164")/*
                                                                                                     * @res
                                                                                                     * "正在修订检验信息..."
                                                                                                     */);
			onInfoEmend();
		} else if (bo == buttonManager.m_btnPrint) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH061")/*
                                                                                       * @res
                                                                                       * "正在打印"
                                                                                       */);
			onInfoPrint();
		} /*lxt 2013-07 gzcg*/else if (bo == buttonManager.m_btnAddSample) {
			showHintMessage("增加样本");
			onInfoAddSample();
		} else if (bo == buttonManager.m_btnUpdateSample) {
			showHintMessage("检测数据");
			onInfoUpdateSample();
		} /*lxt 2013-07 gzcg*/
	}

	private void onInfoUpdateSample() {
		// TODO 关联检测数据
		String vsmplecode = getBillCardPanelInfo().getHeadItem("vsamplecode").getValueObject().toString();
		ArrayList<Hashtable<String, String>> data = new ArrayList<Hashtable<String,String>>();
		String[] retsmplecodes = getAutoCheckData(vsmplecode, data);
		
		if (retsmplecodes==null || retsmplecodes.length==0){
			MessageDialog.showWarningDlg(this, "提示", "没有同检测批次的数据。");
			return;
		}
		int selectIdx = 0;
		if (retsmplecodes.length>1){
			SampleNoDataDlg dlg = new SampleNoDataDlg(this, data);
			if (dlg.showModal()==UIDialog.ID_OK)
				selectIdx = dlg.selectedIdx;
		}
		
		for (int i=0;i<getBillCardPanelInfo().getBillModel().getRowCount();i++){
			String checkItem = getBillCardPanelInfo().getBillModel().getValueAt(i, "ccheckitemid").toString();
			if (data.get(selectIdx).containsKey(checkItem)){
				getBillCardPanelInfo().getBillModel().setValueAt(data.get(selectIdx).get(checkItem), i, "cresult");
				BillEditEvent event = new BillEditEvent(getBillCardPanelInfo().getBillModel(), i, i);
				autoJudgeCheckResult(event);
			}
		}
	}

	private String[] getAutoCheckData(String vsmplecode, ArrayList<Hashtable<String, String>> data) {
		// TODO 获取检测数据
		StringBuffer sql = new StringBuffer();
		sql.append("select gzcg_bd_sampledoc.vsampleno, qc_cghzbg_b.checkitem, qc_cghzbg_b.checkvalue from gzcg_bd_sampledoc, qc_cghzbg_b, qc_cghzbg_h where gzcg_bd_sampledoc.vsampleno=qc_cghzbg_b.jcpici");
		sql.append(" and gzcg_bd_sampledoc.vdef1=qc_cghzbg_b.ypname and qc_cghzbg_b.pk_cghzbg_h=qc_cghzbg_h.pk_cghzbg_h and (gzcg_bd_sampledoc.vsampleno='");
		sql.append(vsmplecode);
		sql.append("' or gzcg_bd_sampledoc.vsamplenofar='");
		sql.append(vsmplecode);
		sql.append("') and gzcg_bd_sampledoc.pk_corp='");
		sql.append(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		sql.append("' and qc_cghzbg_h.pk_corp='");
		sql.append(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		sql.append("' and qc_cghzbg_h.VBILLSTATUS="+IBillStatus.CHECKPASS);
		sql.append("' and nvl(qc_cghzbg_h.dr,0)=0 order by gzcg_bd_sampledoc.vsampleno");
		
		ArrayList<String> vsamplecodes = new ArrayList<String>();
		
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		try {
			Vector<Vector<Object>> vsamplecodedata = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			if (vsamplecodedata!=null && vsamplecodedata.size()>0){
				String cursampleno = "";
				Hashtable<String, String> curmap = null;
				for(int i=0;i<vsamplecodedata.size();i++){
					if (!cursampleno.equals(vsamplecodedata.get(i).get(0).toString())){
						cursampleno = vsamplecodedata.get(i).get(0).toString();
						vsamplecodes.add(cursampleno);
						curmap = new Hashtable<String, String>();
						curmap.put("vsampleno", cursampleno);
						data.add(curmap);
					}
					if (vsamplecodedata.get(i).get(2)!=null && vsamplecodedata.get(i).get(2).toString().length()>0)
						curmap.put(vsamplecodedata.get(i).get(1).toString(), vsamplecodedata.get(i).get(2).toString());
				}
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return vsamplecodes.toArray(new String[]{});
	}

	/**
   * 按钮响应事件--联查主料质量界面。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param bo
   *          <p>
   * @author czp
   * @time 2007-6-27 上午09:01:49
   */
	private void onButtonClickedMM(ButtonObject bo) {
		if (bo == buttonManager.m_btnMainMaterialQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000165")/*
                                                                                                     * @res
                                                                                                     * "正在联查主料..."
                                                                                                     */);
			onCardMainMaterialQuery();// 联查主料质量
		}

		else if (bo == buttonManager.m_btnReturnMMQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000161")/*
                                                                                                     * @res
                                                                                                     * "正在返回..."
                                                                                                     */);
			onReturnMMQuery();// 返回
		}

		else if (bo == buttonManager.m_btnInfoPreviousMMQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH033")/*
                                                                                       * @res
                                                                                       * "正在显示上页主料信息"
                                                                                       */);
			onInfoPreviousMMQuery();// 上张
		}

		else if (bo == buttonManager.m_btnInfoNextMMQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH034")/*
                                                                                       * @res
                                                                                       * "正在显示下页主料信息"
                                                                                       */);
			onInfoNextMMQuery();// 下张
		}
	}

	/**
   * 按钮响应事件--审批流界面。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param bo
   *          <p>
   * @author czp
   * @time 2007-6-27 上午09:01:49
   */
	private void onButtonClickedWorkFlow(ButtonObject bo) {
		if (bo == buttonManager.m_btnAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH048")/*
                                                                                       * @res
                                                                                       * "正在审批检验单"
                                                                                       */);
			onCardAudit();
		} else if (bo == buttonManager.m_btnUnAudit) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH049")/*
                                                                                       * @res
                                                                                       * "正在弃审检验单"
                                                                                       */);
			onCardUnAudit();
		}

		else if (bo == buttonManager.m_btnWorkFlowQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH050")/*
                                                                                       * @res
                                                                                       * "正在进行审批状态查询"
                                                                                       */);
			onCardWorkFlowQuery();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH035")/*
                                                                                       * @res
                                                                                       * "审批状态查询成功"
                                                                                       */);
		}

		else if (bo == buttonManager.m_btnDocument) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000147")/*
                                                                                                     * @res
                                                                                                     * "正在文件管理..."
                                                                                                     */);
			onListDocument();
		}
	}

	/**
   * 子类实现该方法，响应按钮事件。
   * 
   * @version (00-6-1 10:32:59)
   * @param bo
   *          ButtonObject
   * @modify: czp, for V53 ,重构按钮
   */
	public void onButtonClicked(ButtonObject bo) {
		// 检验单卡片
		if (isFromCard()) {
			onButtonClickedCard(bo);
		}
		// 检验单列表界面
		else if (isFromList()) {
			onButtonClickedList(bo);
		}
		// 检验信息界面
		else if (isFromCardInfo()) {
			onButtonClickedCardInfo(bo);
		}
		// 制造主料质量界面
		else if (isFromCardMM() || isFromListMM()) {
			onButtonClickedMM(bo);
		}
		// 审批流界面
		else if (isFromWorkFlow()) {
			onButtonClickedWorkFlow(bo);
		}
		// 处理扩展按钮事件
		onExtendBtnsClick();
	}

	/**
   * 功能描述:退出系统
   */
	public boolean onClosing() {
		// 正在编辑单据时退出提示
		if (m_bBillAdd || m_bBillEdit) {
			int nReturn = MessageDialog.showYesNoCancelDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
					"UPPSCMCommon-000270")/* @res "提示" */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH001")/*
                                                                                                                 * @res
                                                                                                                 * "是否保存已修改的数据？"
                                                                                                                 */);
			if (nReturn == MessageDialog.ID_NO) {
				return true;
			} else if (nReturn == MessageDialog.ID_YES) {
				if (onCardSave())
					return true;
			}
			return false;
		}
		return true;
	}

	/**
   * 此处插入方法说明。 功能描述:联查主料上张 输入参数: 返回值: 异常处理: 日期: 2005-03-16 袁野
   */
	private void onInfoPreviousMMQuery() {

		m_nInfoRecordForMMQuery--;

		// 刷新界面
		getBillCardPanelMMQuery().setBillValueVO(m_grandVOsForMMQuery[m_nInfoRecordForMMQuery]);
		getBillCardPanelMMQuery().getBillModel().execLoadFormula();
		getBillCardPanelMMQuery().updateValue();
		getBillCardPanelMMQuery().updateUI();

		displayCheckResultForInfoCardMMQuery();

		// 设置浏览键的状态
		if (m_grandVOsForMMQuery.length == 1) {
			// 除返回外，按钮均为灰
			buttonManager.m_btnReturnMMQuery.setEnabled(true);
			buttonManager.m_btnInfoPreviousMMQuery.setEnabled(false);
			buttonManager.m_btnInfoNextMMQuery.setEnabled(false);

		} else if (m_nInfoRecordForMMQuery == 0) {
			// 返回下张正常,上张为灰
			buttonManager.m_btnReturnMMQuery.setEnabled(true);
			buttonManager.m_btnInfoPreviousMMQuery.setEnabled(false);
			buttonManager.m_btnInfoNextMMQuery.setEnabled(true);

		} else {
			// 都正常
			buttonManager.m_btnReturnMMQuery.setEnabled(true);
			buttonManager.m_btnInfoPreviousMMQuery.setEnabled(true);
			buttonManager.m_btnInfoNextMMQuery.setEnabled(true);
		}
		updateButtons();
		return;

	}

	/**
   * 此处插入方法说明。 功能描述:联查主料下张 输入参数: 返回值: 异常处理: 日期: 2005-03-16 袁野
   */
	private void onInfoNextMMQuery() {

		m_nInfoRecordForMMQuery++;

		// 刷新界面
		getBillCardPanelMMQuery().setBillValueVO(m_grandVOsForMMQuery[m_nInfoRecordForMMQuery]);
		getBillCardPanelMMQuery().getBillModel().execLoadFormula();
		getBillCardPanelMMQuery().updateValue();
		getBillCardPanelMMQuery().updateUI();

		displayCheckResultForInfoCardMMQuery();

		// 设置浏览键的状态
		if (m_grandVOsForMMQuery.length == 1) {
			// 除返回外，按钮均为灰
			buttonManager.m_btnReturnMMQuery.setEnabled(true);
			buttonManager.m_btnInfoPreviousMMQuery.setEnabled(false);
			buttonManager.m_btnInfoNextMMQuery.setEnabled(false);

		} else if (m_nInfoRecordForMMQuery == m_grandVOsForMMQuery.length - 1) {
			// 返回上张正常,下张为灰
			buttonManager.m_btnReturnMMQuery.setEnabled(true);
			buttonManager.m_btnInfoPreviousMMQuery.setEnabled(true);
			buttonManager.m_btnInfoNextMMQuery.setEnabled(false);

		} else {
			// 都正常
			buttonManager.m_btnReturnMMQuery.setEnabled(true);
			buttonManager.m_btnInfoPreviousMMQuery.setEnabled(true);
			buttonManager.m_btnInfoNextMMQuery.setEnabled(true);
		}
		updateButtons();
		return;

	}

	/**
   * 此处插入方法说明。 功能描述:联查主料返回 输入参数: 返回值: 异常处理: 日期: 2005-03-16 袁野
   */
	private void onReturnMMQuery() {

		m_tabbedPane.setVisible(true);
		getTabbedPaneForMMQuery().setVisible(false);
		m_bMMQuery = false;
		// this.setButtons(m_btnCardDisplay);
		this.setButtons(buttonManager.getBtnTree().getButtonArray());
		buttonManager.setButtonsStateCard();

		// updateButtons();

	}

	/**
   * 此处插入方法说明。 功能描述:卡片"增加" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardAdd() {
		getBillCardPanel().addNew();
		getBillCardPanel().setEnabled(true);
		m_bBillAdd = true;
		m_bBillEdit = true;
		m_grandVOs = null;

		// 设置增加时的编辑控制
		setAddEditState();

		buttonManager.setButtonsStateCardModify();

		// 设置焦点
		getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);

		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();
		getBillCardPanel().getBillModel().execLoadFormula();
		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"增行" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardAddLine(int index) {
		if (sCheckStandards == null) {
			return;
		}

		m_bBillEdit = true;
		addLineAction(index);
	}

	private void addLineAction(int index) {
		int nRow = getBillCardPanel().getRowCount();

		// 如果表头辅计量管理存货，则表体辅数量可以编辑
		// 如果表头辅计量管理存货，且为固定换算率，则表体换算率不可编辑，且表体换算率=表头换算率
		// 如果表头辅计量管理存货，且为非固定换算率，则表体换算率可编辑
		// 如果表头非辅计量管理存货，则表体辅数量和换算率都不可以编辑

		String lstr_cassistunitid = (String) getBillCardPanel().getHeadItem("cassistunitid").getValueObject(); // 辅单位id
		String lstr_cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(); // 存货
		// baseid
		String lstr_nchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject(); // 主数量

		// 不管是否表头存货辅计量管理,只要表头存在主数量,先计算默认的新增行主数量
		if (lstr_nchecknum != null && lstr_nchecknum.trim().length() > 0) {
			UFDouble d1 = new UFDouble(lstr_nchecknum);
			double d = 0;
			for (int i = 0; i < nRow - 1; i++) {
				Object o = getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(i, "nnum");
				if (o != null && o.toString().trim().length() > 0)
					d += (new UFDouble(o.toString())).doubleValue();
			}
			d = d1.doubleValue() - d;
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(new UFDouble(d), nRow - 1, "nnum");

		}
		getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(index, nRow - 1, "inorder");
		if (lstr_cbaseid != null && lstr_cbaseid.trim().length() > 0 && lstr_cassistunitid != null
				&& lstr_cassistunitid.trim().length() > 0) {
			// 表头存货辅计量非空,认为辅计量管理存货(该判断适用于各种报检类型)
			getBillCardPanel().setCellEditable(nRow - 1, "nassistnum", true, "table" + String.valueOf(index));

			Object[] lobj_ConvRateInfo = nc.ui.scm.inv.InvTool.getInvConvRateInfo(lstr_cbaseid, lstr_cassistunitid);
			if (lobj_ConvRateInfo != null) {

				UFBoolean lUFB_isFixedConvRate = (UFBoolean) lobj_ConvRateInfo[1];

				String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject(); // 表头换算率
				UFDouble lUFD_ConvRate = null;
				if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
					lUFD_ConvRate = new UFDouble(lStr_ConvRate);
				}

				if (lUFB_isFixedConvRate.booleanValue()) { // 如果固定换算率,则换算率不可编辑,且表体换算率=表头换算率
					getBillCardPanel().setCellEditable(nRow - 1, "nexchangerateb", false, "table" + String.valueOf(index));
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(lUFD_ConvRate, nRow - 1,
							"nexchangerateb");

				} else { // 如果非固定换算率,则换算率可编辑,且默认表体换算率=表头换算率
					getBillCardPanel().setCellEditable(nRow - 1, "nexchangerateb", true, "table" + String.valueOf(index));
					getBillCardPanel().getBillModel().setValueAt(lUFD_ConvRate, nRow - 1, "nexchangerateb");

				}
			}

			// 不管是否固定换算率,只要当前行上定义了主数量和换算率,则 表体辅数量 = 主数量/换算率
			Object lObj_Nnum = getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(nRow - 1, "nnum");
			Object lObj_ConvRate = getBillCardPanel().getBillModel().getValueAt(nRow - 1, "nexchangerateb");

			if (lObj_Nnum != null && lObj_Nnum.toString().trim().length() > 0 && lObj_ConvRate != null
					&& lObj_ConvRate.toString().trim().length() > 0) {

				String lStr_Nnum = lObj_Nnum.toString().trim();
				String lStr_ConvRate = lObj_ConvRate.toString().trim();

				UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);

				if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {

					UFDouble lUFD_nassistchecknum = new UFDouble(lStr_Nnum).div(lUFD_ConvRate);
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(lUFD_nassistchecknum, nRow - 1,
							"nassistnum"); // 辅数量

				} else {
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(new UFDouble(0.00), nRow - 1,
							"nassistnum"); // 辅数量
				}
			}

		} else { // 表头为非辅计量管理存货
			getBillCardPanel().setCellEditable(nRow - 1, "nassistnum", false, "table" + String.valueOf(index));
			getBillCardPanel().setCellEditable(nRow - 1, "nexchangerateb", false, "table" + String.valueOf(index));
		}

		// 获得存货管理及基础ID
		String cmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();

		// add by yye begin 20060512 入库批次号是否可编辑的判断
		if (cmangid != null && cmangid.trim().length() > 0) {
			boolean lb_isHeadBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(cmangid);
			if (lb_isHeadBatchManaged) {// 如果为批次管理存货行
				getBillCardPanel().setCellEditable(nRow - 1, "vstobatchcode", true, "table" + String.valueOf(index));
				BillItem headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(headItem.getValueObject(),
						nRow - 1, "vstobatchcode");
			} else {
				getBillCardPanel().setCellEditable(nRow - 1, "vstobatchcode", false, "table" + String.valueOf(index));
			}
		}

		// 初始化表体质量等级组
		String sID = null;
		UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(index), "ccheckstatename")
				.getComponent();
		String sWhere = null;
		if (sID != null && sID.trim().length() > 0) {
			sWhere = " ccheckstateid = '" + sID + "' ";
			nRefPane.getRefModel().setWherePart(sWhere);
		} else {
			sWhere = " pk_corp = '" + m_sUnitCode + "' and dr = 0 ";
			nRefPane.getRefModel().setWherePart(sWhere);
		}

		Object[] obj = null;
		String strCheckState = "errorCheckState";
		if (sCheckState != null && sCheckState.length >= index) {
			strCheckState = sCheckState[index];
		}
		try {
			obj = (Object[]) CacheTool.getCellValue("qc_checkstandard", "ccheckstandardid", "ccheckstandardname",
					sCheckStandards[index]);
		} catch (Exception e) {
			QcTool.outException(this, e);
		}
		for (int i = 0; i < nRow; i++) {
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(sCheckStandards[index], i,
					"ccheckstandardid");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(obj[0], i, "ccheckstandardname");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(strCheckState, i, "ccheckstateid");
		}
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"取消" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardCancel() {
		m_bBillAdd = false;
		m_bBillEdit = false;
		getBillCardPanel().setEnabled(false);

		if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
			m_bBodyQuery = null;
			m_grandVOs = null;
			BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
			BillTempletBodyVO[] initBodyVO = tmpletvo.getBodyVO();
			BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
			BillData newbd = new BillData(initVO);
			m_cardBill.setBillData(newbd);
			m_cardBill.setAutoExecHeadEditFormula(true);
			m_cardBill.addEditListener(this);
			m_cardBill.setEnabled(false);
			getBillCardPanel().getBillData().clearViewData();
			getBillCardPanel().updateValue();
			getBillCardPanel().updateUI();

			// 检验信息页签不可用
			m_tabbedPane.setEnabledAt(1, false);

		} else {
			// 刷新界面
			if (mbol_CancelFromDisCardInfo) {
				m_checkBillVOs[m_nBillRecord].setGrandVO(m_grandVOs2);
				mbol_CancelFromDisCardInfo = false;
			}
			m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();
			if (isFromPU()) {
				initMultiPanel(m_checkBillVOs[m_nBillRecord]);
			}

			QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);

			setValues();

		}
		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();
		
		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();

		// add by yy end
		m_hDelInfo = new Hashtable();
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"拷贝" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardCopyLine() {
		int nSelected[] = getBillCardPanel().getBillTable().getSelectedRows();
		if (nSelected == null || nSelected.length == 0) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPT10082002-000001")/*
                                                                                                                     * @res
                                                                                                                     * "拷贝"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000066")/*
                                                                                         * @res
                                                                                         * "没有选中请购单表体行！"
                                                                                         */);
			return;
		}

		getBillCardPanel().copyLine();
		m_bBillEdit = true;
		getBillCardPanel().getBodyItem("nexchangerateb").setEnabled(false);

	}

	/**
   * 此处插入方法说明。 功能描述:卡片"删行" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardDelLine() {
		int nSelected = getBillCardPanel().getBillTable().getSelectedRow();
		if (nSelected < 0) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000013")/*
                                                                                                             * @res
                                                                                                             * "删行"
                                                                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000067")/*
                                                                                         * @res
                                                                                         * "没有选中表体行！"
                                                                                         */);
			return;
		}

		Object cSourcebilltype = getBillCardPanel().getBodyValueAt(nSelected, "csourcebilltypecode");
		if (cSourcebilltype != null && cSourcebilltype.equals("23")) {
			Object cSourcebillrowid = getBillCardPanel().getBodyValueAt(nSelected, "csourcebillrowid");
			boolean bCanDel = false;
			for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
				Object oTemp = getBillCardPanel().getBodyValueAt(i, "csourcebillrowid");
				if (i == nSelected && oTemp != null && cSourcebillrowid.equals(oTemp)) {
					bCanDel = true;
					break;
				}
			}
			if (!bCanDel) {
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000013")/*
                                                                                                               * @res
                                                                                                               * "删行"
                                                                                                               */, nc.ui.ml.NCLangRes.getInstance().getStrByID("c0010112", "UPPc0010112-000130")/*
                                                                                           * @res
                                                                                           * "不能删除！"
                                                                                           */);
				// MessageDialog.showHintDlg(this, "删行","不能删除!");
				return;
			}
		}

		if (!getBillCardPanel().delLine()) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000013")/*
                                                                                                             * @res
                                                                                                             * "删行"
                                                                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000067")/*
                                                                                         * @res
                                                                                         * "没有选中表体行！"
                                                                                         */);
			return;
		}

		// getBillCardPanel().setEnabled(true);
		m_bBillEdit = true;

		calculateNum();
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"作废" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardDiscard() {
		if (MessageDialog.showYesNoDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000039")/*
                                                                                                                 * @res
                                                                                                                 * "作废"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH002")/*
                                                                         * @res
                                                                         * "确定要作废检验单？"
                                                                         */) != MessageDialog.ID_YES)
			return;
		if (!m_bAllowAnother) {
			if ((((UIRefPane) getBillCardPanel().getTailItem("creporterid").getComponent()).getRefName()) != null
					&& !getClientEnvironment().getUser().getUserName().equals(
							((UIRefPane) getBillCardPanel().getTailItem("creporterid").getComponent()).getRefName())) {
				String message = NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000008")/*操作员和报告人不相同，不允许修改和删除单据*/;
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000039")/*
                                                                                                                 * @res
                                                                                                                 * "作废"
                                                                                                                 */, message);
				return;
			}
		}
		try {
			CheckbillHelper.discard(new CheckbillVO[] { m_checkBillVOs[m_nBillRecord] }, getClientEnvironment().getUser()
					.getPrimaryKey());
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000039")/*
                                                                                                               * @res
                                                                                                               * "作废"
                                                                                                               */, e.getMessage());
			return;
		}

		this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH006")/*
                                                                                         * @res
                                                                                         * "作废成功！"
                                                                                         */);

		// 作废成功，从缓存清除相应的记录
		Vector vTemp1 = new Vector();
		Vector vTemp2 = new Vector();
		for (int i = 0; i < m_checkBillVOs.length; i++) {
			if (i != m_nBillRecord) {
				vTemp1.addElement(m_checkBillVOs[i]);
				vTemp2.addElement(m_bBodyQuery[i]);
			}
		}

		// 更改缓存记录指针
		if (m_checkBillVOs.length == 1) {
			// 缓存仅一条记录
			m_nBillRecord = 0;
		} else {
			// 缓存多条记录
			if (m_nBillRecord == m_checkBillVOs.length - 1)
				m_nBillRecord = 0;
		}

		if (vTemp1.size() > 0) {
			// 缓存尚有记录
			m_checkBillVOs = new CheckbillVO[vTemp1.size()];
			vTemp1.copyInto(m_checkBillVOs);

			m_bBodyQuery = new UFBoolean[vTemp2.size()];
			vTemp2.copyInto(m_bBodyQuery);

			// 若表体未查询，则查询表体
			if (m_checkBillVOs[m_nBillRecord].getChildrenVO() == null) {
				try {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();

					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs());
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);

					if (list != null && list.size() > 0) {
						// 有返回结果
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
						CheckbillB2VO grandVO[] = null;

						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());

							// //设置自由项
							headVO.setVfree1(bodyVO[0].getVfree1());
							headVO.setVfree2(bodyVO[0].getVfree2());
							headVO.setVfree3(bodyVO[0].getVfree3());
							headVO.setVfree4(bodyVO[0].getVfree4());
							headVO.setVfree5(bodyVO[0].getVfree5());
							// Object oTemp = list.get(2);
							// if (oTemp != null)
							// headVO.setVfreeitem(oTemp.toString());
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
						}

						Object o = list.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
							m_grandVOs = grandVO;
						} else {
							m_grandVOs = null;
						}
						m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
						m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);
						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);
						initMultiPanel(m_checkBillVOs[m_nBillRecord]);
						QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);

						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);

						// 计算合格数量等
						calculateNum();

					} else {
						// 无返回结果
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000015")/*
                                       * @res "查询表体"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                               * @res
                                                                                               * "表体不存在！"
                                                                                               */);
						return;
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作"
                                                                                                                         */, e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/* @res "查询表体" */, e.getMessage());
					return;
				}
			} else {
				initMultiPanel(m_checkBillVOs[m_nBillRecord]);
				QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);

				setValues();

				m_tabbedPane.setEnabledAt(1, true);
			}
		} else {
			// 缓存无记录
			m_checkBillVOs = null;
			m_bBodyQuery = null;
			m_grandVOs = null;
			BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
			BillTempletBodyVO[] initBodyVO = tmpletvo.getBodyVO();
			BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
			BillData newbd = new BillData(initVO);
			m_cardBill.setBillData(newbd);
			m_cardBill.setAutoExecHeadEditFormula(true);
			m_cardBill.setEnabled(false);
			getBillCardPanel().getBillData().clearViewData();
			getBillCardPanel().updateValue();
			getBillCardPanel().updateUI();

			// 检验信息页签不可用
			m_tabbedPane.setEnabledAt(1, false);

		}

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();

		// add by yy begin
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();

		// add by yy end
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"首张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardFirst() {
		m_nBillRecord = 0;

		initMultiPanelRow();
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"插行" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardInsertLine() {
		if (sCheckStandards == null) {
			return;
		}

		if (!getBillCardPanel().insertLine()) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPT10081406-000007")/*
                                                                                                                     * @res
                                                                                                                     * "插行"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000067")/*
                                                                                         * @res
                                                                                         * "没有选中表体行！"
                                                                                         */);
			return;
		}
		int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
		m_bBillEdit = true;
		insertLineAction(index);
	}

	private void insertLineAction(int index) {

		int nRow = getBillCardPanel().getRowCount();
		int currLine = getBillCardPanel().getBillTable().getSelectedRow();
		// 如果表头辅计量管理存货，则表体辅数量可以编辑
		// 如果表头辅计量管理存货，且为固定换算率，则表体换算率不可编辑，且表体换算率=表头换算率
		// 如果表头辅计量管理存货，且为非固定换算率，则表体换算率可编辑
		// 如果表头非辅计量管理存货，则表体辅数量和换算率都不可以编辑

		String lstr_cassistunitid = (String) getBillCardPanel().getHeadItem("cassistunitid").getValueObject(); // 辅单位id
		String lstr_cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(); // 存货

		getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(index, currLine, "inorder");
		if (lstr_cbaseid != null && lstr_cbaseid.trim().length() > 0 && lstr_cassistunitid != null
				&& lstr_cassistunitid.trim().length() > 0) {
			// 表头存货辅计量非空,认为辅计量管理存货(该判断适用于各种报检类型)
			getBillCardPanel().setCellEditable(currLine, "nassistnum", true, "table" + String.valueOf(index));

			Object[] lobj_ConvRateInfo = nc.ui.scm.inv.InvTool.getInvConvRateInfo(lstr_cbaseid, lstr_cassistunitid);
			if (lobj_ConvRateInfo != null) {

				UFBoolean lUFB_isFixedConvRate = (UFBoolean) lobj_ConvRateInfo[1];

				String lStr_ConvRate = (String) getBillCardPanel().getHeadItem("nexchangerate").getValueObject(); // 表头换算率
				UFDouble lUFD_ConvRate = null;
				if (lStr_ConvRate != null && lStr_ConvRate.trim().length() > 0) {
					lUFD_ConvRate = new UFDouble(lStr_ConvRate);
				}

				if (lUFB_isFixedConvRate.booleanValue()) { // 如果固定换算率,则换算率不可编辑,且表体换算率=表头换算率
					getBillCardPanel().setCellEditable(currLine, "nexchangerateb", false, "table" + String.valueOf(index));
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(lUFD_ConvRate, currLine,
							"nexchangerateb");

				} else { // 如果非固定换算率,则换算率可编辑,且默认表体换算率=表头换算率
					getBillCardPanel().setCellEditable(currLine, "nexchangerateb", true, "table" + String.valueOf(index));
					getBillCardPanel().getBillModel().setValueAt(lUFD_ConvRate, currLine, "nexchangerateb");

				}
			}

			// 不管是否固定换算率,只要当前行上定义了主数量和换算率,则 表体辅数量 = 主数量/换算率
			Object lObj_Nnum = getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(currLine, "nnum");
			Object lObj_ConvRate = getBillCardPanel().getBillModel().getValueAt(currLine, "nexchangerateb");

			if (lObj_Nnum != null && lObj_Nnum.toString().trim().length() > 0 && lObj_ConvRate != null
					&& lObj_ConvRate.toString().trim().length() > 0) {

				String lStr_Nnum = lObj_Nnum.toString().trim();
				String lStr_ConvRate = lObj_ConvRate.toString().trim();

				UFDouble lUFD_ConvRate = new UFDouble(lStr_ConvRate);

				if (Math.abs(lUFD_ConvRate.doubleValue()) > 0) {

					UFDouble lUFD_nassistchecknum = new UFDouble(lStr_Nnum).div(lUFD_ConvRate);
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(lUFD_nassistchecknum, currLine,
							"nassistnum"); // 辅数量

				} else {
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(new UFDouble(0.00), currLine,
							"nassistnum"); // 辅数量
				}
			}

		} else { // 表头为非辅计量管理存货
			getBillCardPanel().setCellEditable(currLine, "nassistnum", false, "table" + String.valueOf(index));
			getBillCardPanel().setCellEditable(currLine, "nexchangerateb", false, "table" + String.valueOf(index));
		}

		// 获得存货管理及基础ID
		String cmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();

		// add by yye begin 20060512 入库批次号是否可编辑的判断
		if (cmangid != null && cmangid.trim().length() > 0) {
			boolean lb_isHeadBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(cmangid);
			if (lb_isHeadBatchManaged) {// 如果为批次管理存货行
				getBillCardPanel().setCellEditable(currLine, "vstobatchcode", true, "table" + String.valueOf(index));
				BillItem headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(headItem.getValueObject(),
						currLine, "vstobatchcode");
			} else {
				getBillCardPanel().setCellEditable(currLine, "vstobatchcode", false, "table" + String.valueOf(index));
			}
		}

		// 初始化表体质量等级组
		String sID = null;
		UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(index), "ccheckstatename")
				.getComponent();
		String sWhere = null;
		if (sID != null && sID.trim().length() > 0) {
			sWhere = " ccheckstateid = '" + sID + "' ";
			nRefPane.getRefModel().setWherePart(sWhere);
		} else {
			sWhere = " pk_corp = '" + m_sUnitCode + "' and dr = 0 ";
			nRefPane.getRefModel().setWherePart(sWhere);
		}

		Object[] obj = null;
		String strCheckState = "errorCheckState";
		if (sCheckState != null && sCheckState.length >= index) {
			strCheckState = sCheckState[index];
		}
		try {
			obj = (Object[]) CacheTool.getCellValue("qc_checkstandard", "ccheckstandardid", "ccheckstandardname",
					sCheckStandards[index]);
		} catch (Exception e) {
			QcTool.outException(this, e);
		}
		for (int i = 0; i < nRow; i++) {
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(sCheckStandards[index], i,
					"ccheckstandardid");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(obj[0], i, "ccheckstandardname");
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(strCheckState, i, "ccheckstateid");
		}

	}

	/**
   * 此处插入方法说明。 功能描述:卡片"末张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardLast() {
		m_nBillRecord = m_checkBillVOs.length - 1;

		initMultiPanelRow();
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"修改" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardModify() {
		getBillCardPanelInfo().getBodyValueAt(0, "cstandardvalue2");
		try {
			String sMessage = "";
			if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
				return;
			}
			// 根据参数 是否允许别人修改 判断
			if (!m_bAllowAnother) {
				String strReporter = ((UIRefPane) getBillCardPanel().getTailItem("creporterid").getComponent()).getRefName();
				if (strReporter != null && !getClientEnvironment().getUser().getUserName().equals(strReporter)) {
					sMessage += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-001108")/*
                                                                                                     * @res
                                                                                                     * "系统不允许他人修改报告人提交的单据!"
                                                                                                     */;
				}
			}
			// 获得单据状态
			CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
			Vector vStatus = CheckbillHelper.queryBillStatus(new String[] { headVO.getCcheckbillid() });
			Object nStatus[] = (Object[]) vStatus.elementAt(0);
// arvin nStatus：dr,billstatus,billcode
			if (((Integer) nStatus[0]).intValue() == 1 || ((Integer) nStatus[1]).intValue() == 1)
				sMessage += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPC0020101-000001")/*
                                                                                                   * @res
                                                                                                   * "单据已作废！"
// arvin 审批中的单据可以修改                                                                                                  */;
//			if (((Integer) nStatus[1]).intValue() == 2)
//				sMessage += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPC0020101-000002")/*
//                                                                                                   * @res
//                                                                                                   * "单据正在审批！"
//                                                                                                   */;
			if (((Integer) nStatus[1]).intValue() == 3)
				sMessage += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPC0020101-000003")/*
                                                                                                   * @res
                                                                                                   * "单据已审批！"
                                                                                                   */;
			if (((Integer) nStatus[1]).intValue() == 4
					&& !m_checkBillVOs[m_nBillRecord].getHeadVo().getCreporterid().equals(
							getClientEnvironment().getUser().getPrimaryKey()))
				sMessage += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPC0020101-000004")/*
                                                                                                   * @res
                                                                                                   * "单据审批未通过！"
                                                                                                   */;

			if (sMessage.trim().length() > 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000045")/*
                                                                                                                 * @res
                                                                                                                 * "修改"
                                                                                                                 */, sMessage);
				return;
			}

		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000070")/*
                                                                                                                     * @res
                                                                                                                     * "获得单据状态"
                                                                                                                     */, e.getMessage());
			return;
		}
		if (getBillCardPanel().getTailItem("dauditdate").getValue() != null) {
			getBillCardPanel().getTailItem("dauditdate").setValue(null);
			getBillCardPanel().getTailItem("taudittime").setValue(null);
		}
		getBillCardPanel().setEnabled(true);
		getBillCardPanelInfo().setEnabled(false);
		m_bBillEdit = true;

		// 设置编辑状态
		setModifyEditState();

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCardModify();

		// 设置焦点
		getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);

		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();
		getBillCardPanel().getBillModel().execLoadFormula();
		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"下张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardNext() {
		m_nBillRecord++;
		if (m_nBillRecord > m_checkBillVOs.length - 1)
			m_nBillRecord = m_checkBillVOs.length - 1;

		initMultiPanelRow();

		return;
	}

	private void initMultiPanelRow() {
		// 若表体未查询，则查询表体
		if (!m_bBodyQuery[m_nBillRecord].booleanValue()) {
			/*
       * String chooseDataPower = null; for(int i = 0 ; i <
       * m_conditionVOs.length ; i ++){
       * if(m_conditionVOs[i].getFieldCode().equals("ccheckitemid")){
       * chooseDataPower = "qc_checkbill_b2.ccheckitemid in
       * ('"+m_conditionVOs[i].getRefResult().getRefPK()+"')"; break; } }
       */
			try {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
				strCheckbillCode = headVO.getVcheckbillcode();
				String chooseDataPower = null;
				chooseDataPower = getCheckItemDataPower();
				ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs(),
						chooseDataPower);
				if (list == null || list.size() == 0)
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                     * @res
                                                                                                                     * "并发操作，请刷新！"
                                                                                                                     */);

				if (list != null && list.size() > 0) {
					// 有返回结果
					CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
					CheckbillB2VO grandVO[] = null;

					// 设置来源信息
					if (bodyVO != null && bodyVO.length > 0) {
						headVO.setCsource(bodyVO[0].getCsource());
						// 设置检验单头的工序信息
						// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
						// 设置自由项
						headVO.setVfree1(bodyVO[0].getVfree1());
						headVO.setVfree2(bodyVO[0].getVfree2());
						headVO.setVfree3(bodyVO[0].getVfree3());
						headVO.setVfree4(bodyVO[0].getVfree4());
						headVO.setVfree5(bodyVO[0].getVfree5());
						nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
						freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
					}

					Object o = list.get(1);
					if (o != null) {
						grandVO = (CheckbillB2VO[]) o;
						m_grandVOs = grandVO;
					} else {
						m_grandVOs = null;
					}

					m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
					m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);

					m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);

					// 多公司查询，改变检验单表头参照
					String pk_corp = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getPk_corp();
					if (pk_corp != null && pk_corp.trim().length() > 0) {
						// changeBillReference(pk_corp);
						m_sUnitCode = pk_corp;
					}

				} else {
					// 无返回结果
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/*
                                                             * @res "查询表体"
                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                             * @res
                                                                                             * "表体不存在！"
                                                                                             */);
					return;
				}
			} catch (BusinessException e) {
				SCMEnv.out(e);
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作"
                                                                                                                       */, e.getMessage());
				return;
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000015")/*
                                                                                                                       * @res
                                                                                                                       * "查询表体"
                                                                                                                       */, e.getMessage());
				return;
			}
		} else {
			m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();
		}
		Vector orderData = new Vector();
		for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getChildrenVO().length; i++) {
			if (((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]).getInorder() != null
					&& ((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]).getInorder().intValue() == 0) {
				orderData.add(0, ((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]));
			} else {
				// 重新过滤主检验标准
				orderData.add(((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]));
			}
		}
		CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
		orderData.copyInto(vos);
		m_checkBillVOs[m_nBillRecord].setChildrenVO(vos);
		orderData.clear();
		// 刷新界面
		/** V55有查询结果根据表体的检验标准加载多页签 */
		initMultiPanel(m_checkBillVOs[m_nBillRecord]);

		QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);
		// 计算合格数量等
		calculateNum();
		setValues();
		strCheckbillCode = m_checkBillVOs[m_nBillRecord].getHeadVo().getVcheckbillcode();
		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();

		// add by yy begin
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();

	}

	/**
   * 此处插入方法说明。 功能描述:卡片"粘贴" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardPasteLine() {
		int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
		// 粘贴前总行数
		int org_rowcount = getBillCardPanel().getRowCount();
		// 被粘贴行的起始位置（当前选择行位置）
		int pastedline = getBillCardPanel().getBillTable().getSelectedRow();

		getBillCardPanel().pasteLine();

		m_bBillEdit = true;

		// 界面显示控制
		// //质量等级控制
		/* 由于不同行的质量等级不能相同，所以粘贴后入库批次号，质量等级、是否合格品、是否可入库和建议处理方式等字段清空 */
		for (int i = 0, len = getBillCardPanel().getRowCount() - org_rowcount; i < len; i++) {
			getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(index, pastedline + 1, "inorder");
			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "ccheckstatename");
			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "ccheckstate_bid");
			getBillCardPanel().getBillModel().setValueAt(new UFBoolean(false), pastedline + 1, "bqualified");
			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "cdefectprocessid");
			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "cdefectprocessname");
			getBillCardPanel().getBillModel().setValueAt(new UFBoolean(false), org_rowcount, "bcheckin");
			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "vstobatchcode");

			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "cb_tbatchtime");
			getBillCardPanel().getBillModel().setValueAt(null, pastedline + 1, "ccheckbill_b1id");
		}
		// getBillCardPanel().getBodyItem("nexchangerateb").setEnabled(true);
		// //数量控制
		calculateNum();
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"上张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardPrevious() {
		m_nBillRecord--;
		if (m_nBillRecord < 0)
			m_nBillRecord = 0;
		initMultiPanelRow();
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"打印" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardPrint() {
		nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(this, null);
		print.setDataSource(new nc.ui.qc.pub.QCPrintDS("C0020101", m_cardBill, getInfoVOs(), sCheckStandards));
		print.setTemplateID(m_sUnitCode, "C0020101", getClientEnvironment().getUser().getPrimaryKey(), null);
		int ret = print.selectTemplate();
		if (ret > 0) {
			print.preview();
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH041")/*
                                                                                     * @res
                                                                                     * "打印成功"
                                                                                     */);
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"打印" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoPrint() {
		nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(this, null);
		print.setDataSource(new nc.ui.qc.pub.QCPrintDS("C0020101", m_cardBill, getInfoVOs(), m_grandVOs, sCheckStandards));
		print.setTemplateID(m_sUnitCode, "C0020101", getClientEnvironment().getUser().getPrimaryKey(), null, "C002010101");
		int ret = print.selectTemplate();
		if (ret > 0) {
			print.preview();
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH041")/*
                                                                                     * @res
                                                                                     * "打印成功"
                                                                                     */);
	}

	/**
   * 获得打印必需的打印信息值，并保存到infovo中
   * 
   * @return
   */
	private InfoVO[] getInfoVOs() {

		CheckbillB2VO[] b2vos = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();

		if (b2vos == null || b2vos.length == 0)
			return null;

		int len = b2vos.length;
		InfoVO[] infos = new InfoVO[len];
		CheckbillB2ItemVO[] children;

		// 数据准备，后台查询
		HashSet set_itemid = new HashSet();
		HashSet set_checkerid = new HashSet();
		String s_item, s_checker;
		for (int i = 0; i < len; i++) {
			children = (CheckbillB2ItemVO[]) b2vos[i].getChildrenVO();
			for (int j = 0, len2 = children.length; j < len2; j++) {
				s_item = children[j].getCcheckitemid();
				if (s_item != null)
					set_itemid.add(s_item);

				s_checker = children[j].getCcheckerid();
				if (s_checker != null)
					set_checkerid.add(s_checker);
			}
		}

		HashMap hmap_itemname;
		HashMap hmap_checker;

		Object[] results;
		try {
			results = CheckbillHelper.queryBatchParaForPrint(set_itemid, set_checkerid);
		} catch (Exception e) {
			SCMEnv.out(e.getMessage());
			return null;
		}
		hmap_itemname = (HashMap) results[0];
		hmap_checker = (HashMap) results[1];

		for (int i = 0; i < len; i++) {
			infos[i] = new InfoVO();

			// -------------------------head
			String[] sHead = new String[2];
			sHead[0] = ((CheckbillB2HeaderVO) b2vos[i].getParentVO()).getVsamplecode();
			sHead[1] = ((CheckbillB2HeaderVO) b2vos[i].getParentVO()).getNnum().toString();

			infos[i].head = sHead;

			// -------------------------body
			children = (CheckbillB2ItemVO[]) b2vos[i].getChildrenVO();
			int bodyLen = children.length;
			String[][] sBody = new String[bodyLen][30];

			for (int j = 0; j < bodyLen; j++) {
				sBody[j][0] = (String) hmap_itemname.get(children[j].getCcheckitemid());
				sBody[j][1] = children[j].getCcheckunitid();
				sBody[j][2] = children[j].getIcheckstandard() == null ? null
						: children[j].getIcheckstandard().intValue() == 0 ? "数值型" : "字符型";/*-=notranslate=-*/
				sBody[j][3] = children[j].getCresult();
				sBody[j][4] = children[j].getVmemo();
				sBody[j][5] = (String) hmap_checker.get(children[j].getCcheckerid());
				sBody[j][6] = children[j].getDcheckdate();
				sBody[j][7] = children[j].getCstandardvalue();
				sBody[j][8] = CheckstandardItemVO.getStateNameByResult(children[j].getCresult(), children[j]
						.getCstandardvalue(), children[j].getIcheckstandard() == null ? -1 : children[j].getIcheckstandard()
						.intValue());

				sBody[j][9] = children[j].getVdefitem1();
				sBody[j][10] = children[j].getVdefitem2();
				sBody[j][11] = children[j].getVdefitem3();
				sBody[j][12] = children[j].getVdefitem4();
				sBody[j][13] = children[j].getVdefitem5();
				sBody[j][14] = children[j].getVdefitem6();
				sBody[j][15] = children[j].getVdefitem7();
				sBody[j][16] = children[j].getVdefitem8();
				sBody[j][17] = children[j].getVdefitem9();
				sBody[j][18] = children[j].getVdefitem10();
				sBody[j][19] = children[j].getVdefitem11();
				sBody[j][20] = children[j].getVdefitem12();
				sBody[j][21] = children[j].getVdefitem13();
				sBody[j][22] = children[j].getVdefitem14();
				sBody[j][23] = children[j].getVdefitem15();
				sBody[j][24] = children[j].getVdefitem16();
				sBody[j][25] = children[j].getVdefitem17();
				sBody[j][26] = children[j].getVdefitem18();
				sBody[j][27] = children[j].getVdefitem19();
				sBody[j][28] = children[j].getVdefitem20();
				// 20100825 57升级
				sBody[j][29] = children[j].getCcheckbill_b2id();
			}

			infos[i].body = sBody;
		}

		return infos;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"查询" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardQuery() {

		// 效率优化，将initQuery()改为由按钮触发，且只触发一次。
		// 这样已提高打开结点时的速度。合理分配用时。
		// yye 2005-06-17
		if (!mbol_hasInitQuery) {
			initQuery();
			mbol_hasInitQuery = true;
		}

		// /////////////////

		m_condClient.hideUnitButton();
		m_condClient.showModal();

		if (m_condClient.isCloseOK()) {
			m_bQueried = true;
			// 获取查询条件
			String sCorpID[] = m_condClient.getSelectedCorpIDs();
			if (sCorpID == null || sCorpID.length == 0) {
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                       * @res
                                                                                                                       * "查询检验单"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                           * @res
                                                                                           * "没有符合条件的检验单！"
                                                                                           */);
				return;
			}
			m_conditionVOs = m_condClient.getConditionVO();
			if (m_rbFree.isSelected())
				m_nAddCondition = 0;
			else if (m_rbGoing.isSelected())
				m_nAddCondition = 2;
			else if (m_rbPass.isSelected())
				m_nAddCondition = 3;
			else if (m_rbNoPass.isSelected())
				m_nAddCondition = 4;
			else
				m_nAddCondition = -1;
			/*
       * String chooseDataPower = null; for(int i = 0 ; i <
       * m_conditionVOs.length ; i ++){
       * if(m_conditionVOs[i].getFieldCode().equals("ccheckitemid")){
       * chooseDataPower = "qc_checkbill_b2.ccheckitemid in
       * ('"+m_conditionVOs[i].getRefResult().getRefPK()+"')"; break; } }
       */
			try {
				String chooseDataPower = null;
				chooseDataPower = getCheckItemDataPower();
				//
				m_checkBillVOs = CheckbillHelper.queryCheckbill(sCorpID, m_conditionVOs, new Integer(m_nAddCondition), m_dialog
						.getChecktypeid(), m_condClient.getSourceSQL(), chooseDataPower);

				// 无查询结果
				if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
					m_bBodyQuery = null;
					m_grandVOs = null;
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                         * @res
                                                                                                                         * "查询检验单"
                                                                                                                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                             * @res
                                                                                             * "没有符合条件的检验单！"
                                                                                             */);
					getBillCardPanel().getBillData().clearViewData();
					getBillCardPanel().updateValue();
					getBillCardPanel().updateUI();

					buttonManager.m_btnMainMaterialQuery.setEnabled(false);
					this.updateButton(buttonManager.m_btnMainMaterialQuery);
					buttonManager.setButtonsStateCard();
					// 检验信息页签不可用
					m_tabbedPane.setEnabledAt(1, false);
					this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPC0020101-000071")/*
                                                                                                             * @res
                                                                                                             * "不存在符合条件的记录！"
                                                                                                             */);
					return;
				}
				// 有查询结果
				// add by yy begin取得单据行号信息
				Vector lvec_SourceBillTypeCode = new Vector();
				Vector lvec_SourceBillHID = new Vector();
				Vector lvec_SourceBillBID = new Vector();

				CheckbillItemVO l_CheckbillItemVO[] = null;
				Vector orderData = new Vector(1000);
				for (int i = 0; i < m_checkBillVOs.length; i++) {
					CheckbillHeaderVO l_CheckbillHeaderVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
					if (i == 0) {
						// 设置自由项
						nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
						freeVOParse.setFreeVO(new CheckbillHeaderVO[] { l_CheckbillHeaderVO }, "vfreeitem", "vfree", null,
								"cmangid", false);
						//
					}
					l_CheckbillItemVO = (CheckbillItemVO[]) m_checkBillVOs[i].getChildrenVO();
					if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {

						for (int j = 0; j < l_CheckbillItemVO.length; j++) {
							if (l_CheckbillItemVO[j].getVstobatchcode() != null) {
								if (!vContain.contains(l_CheckbillItemVO[j].getVstobatchcode())) {
									vContain.put(l_CheckbillItemVO[j].getCmangid() + l_CheckbillItemVO[j].getCcheckstate_bid(),
											l_CheckbillItemVO[j].getVstobatchcode());
								}
							}
							l_CheckbillItemVO[j].setCinventoryid(l_CheckbillHeaderVO.getCinventorycode());
							if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null
									&& l_CheckbillItemVO[j].getCsourcebillid() != null
									&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
								lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
								lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
								lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
							}
							if (l_CheckbillItemVO[j].getInorder().intValue() == 0) {
								orderData.add(0, l_CheckbillItemVO[j]);
							} else {
								// 重新过滤主检验标准
								orderData.add(l_CheckbillItemVO[j]);
							}
						}
						QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());
						CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
						orderData.copyInto(vos);
						m_checkBillVOs[i].setChildrenVO(vos);
						orderData.clear();
					}
					if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
						for (int j = 0; j < l_CheckbillItemVO.length; j++) {
							Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
									"qualitymanflag", m_checkBillVOs[0].getHeadVo().getCmangid());
							objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
									"qualitydaynum", m_checkBillVOs[0].getHeadVo().getCmangid());
							if (objStr[0] != null) {
								int iQualityDayNum = Integer.parseInt(objStr[0].toString());
								objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
										"qualityperiodunit", m_checkBillVOs[0].getHeadVo().getCmangid());
								if (objStr[0] != null && (Integer) objStr[0] == 0) {
									iQualityDayNum = iQualityDayNum * 365;
								} else if (objStr[0] != null && (Integer) objStr[0] == 1) {
									iQualityDayNum = iQualityDayNum * 30;
								}

								Object strDate = l_CheckbillItemVO[j].getDproducedate();
								if (strDate != null) {
									UFDate dvalidate = new UFDate(strDate.toString()).getDateAfter(iQualityDayNum);
									l_CheckbillItemVO[j].setAttributeValue("cb_dvalidate", dvalidate);
								}
							}
						}
					}
				}

				String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
				String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
				String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

				lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
				lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
				lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

				SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
				m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode,
						lstrary_SourceBillHID, lstrary_SourceBillBID);

				// add by yy end

				// 检验信息页签可用

				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();

				m_bBodyQuery = new UFBoolean[m_checkBillVOs.length];
				for (int i = 0; i < m_bBodyQuery.length; i++) {
					if (i == 0)
						m_bBodyQuery[i] = new UFBoolean(true);
					else
						m_bBodyQuery[i] = new UFBoolean(false);
				}

				m_nBillRecord = 0;

				// 多公司查询，改变检验单表头参照
				String pk_corp = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getPk_corp();
				if (pk_corp != null && pk_corp.trim().length() > 0) {
					// changeBillReference(pk_corp);
					m_sUnitCode = pk_corp;
				}
				// 取换算率
				String sBaseId = m_checkBillVOs[0].getHeadVo().getCbaseid();
				String sassistid = m_checkBillVOs[0].getHeadVo().getCassistunitid();
				boolean bFixed = InvTool.isFixedConvertRate(sBaseId, sassistid);
				if (bFixed) {
					m_checkBillVOs[0].getHeadVo().setNexchangerate(InvTool.getInvConvRateValue(sBaseId, sassistid));
					for (int i = 0; i < m_checkBillVOs[0].getBodyVos().length; i++) {
						m_checkBillVOs[0].getBodyVos()[i].setNexchangerateb(InvTool.getInvConvRateValue(sBaseId, sassistid));
					}
				}

				/** V55有查询结果根据表体的检验标准加载多页签 */
				initMultiPanel(m_checkBillVOs[0]);
				m_tabbedPane.setEnabledAt(1, true);
				QcTool.setValue(getBillCardPanel(), m_checkBillVOs[0]);

				setCardSourceBillInfo();
				// 刷新界面

				if (m_checkBillVOs[0].getHeadVo().getCprayerid() != null
						&& m_checkBillVOs[0].getHeadVo().getCprayerid().equalsIgnoreCase("system")) {
					UIRefPane refPane = (UIRefPane) getBillCardPanel().getTailItem("cprayerid").getComponent();
					refPane.setText("system");
				}

				Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
						"qualitymanflag", m_checkBillVOs[0].getHeadVo().getCmangid());
				String strQualityManFlag = objStr[0].toString();
				objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc", "qualitydaynum",
						m_checkBillVOs[0].getHeadVo().getCmangid());
				if (objStr[0] != null) {
					int iQualityDayNum = Integer.parseInt(objStr[0].toString());
					objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
							"qualityperiodunit", m_checkBillVOs[0].getHeadVo().getCmangid());
					if ((Integer) objStr[0] == 0) {
						iQualityDayNum = iQualityDayNum * 365;
					} else if ((Integer) objStr[0] == 1) {
						iQualityDayNum = iQualityDayNum * 30;
					}
					vQualityMan.add(strQualityManFlag);
					vQualityMan.add(iQualityDayNum);
				} else {
					vQualityMan.add(strQualityManFlag);
					vQualityMan.add(null);
				}
				this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000120")/*
                                                                                                           * @res
                                                                                                           * "符合条件的记录数："
                                                                                                           */
						+ m_checkBillVOs.length);
				strCheckbillCode = m_checkBillVOs[0].getHeadVo().getVcheckbillcode();

				// since v53, 按钮逻辑重构
				buttonManager.setButtonsStateCard();
				calculateNum();
				// add by yy end

			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                       * @res
                                                                                                                       * "查询检验单"
                                                                                                                       */, e.getMessage());
			}
		}
		getBillCardPanel().getHeadItem("cinventoryname").setLength(50);
		getBillCardPanel().execHeadFormula("cinventoryname->getColValue(bd_invbasdoc,invname,pk_invbasdoc,cbaseid)");
		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();
		getBillCardPanel().getBillModel().execLoadFormula();
		return;
	}

	/**
   * v55质检单加载多标准页签模版
   */
	private void initMultiPanel(CheckbillVO checkvo) {
		HashMap<String, String> hStandard = new HashMap<String, String>();
		Vector vecData = new Vector(100);
		for (int j = checkvo.getBodyVos().length, i = 0; i < j; i++) {
			String sCheckStandard = checkvo.getBodyVos()[i].getCcheckstandardid();
			if (!hStandard.containsKey(sCheckStandard)) {
				hStandard.put(sCheckStandard, sCheckStandard);
				if (checkvo.getBodyVos()[i].getInorder().intValue() == 0) {
					vecData.add(0, sCheckStandard);
				} else {
					vecData.add(sCheckStandard);
				}
			}
		}
		String[] sTemp = new String[vecData.size()];
		vecData.copyInto(sTemp);
		initMultiStandardPanel(sTemp);
	}

	/**
   * v55质检单加载多标准页签模版
   */
	private void initMultiStandardPanel(String[] sCheckStandard) {
		sCheckStandards = sCheckStandard;
		// 根据检验标准id找到检验标准名称
		String[] strStandardNames = new String[sCheckStandard.length];
		for (int k = 0; k < sCheckStandard.length; k++) {
			Object[] obName = null;
			try {
				obName = (Object[]) CacheTool.getCellValue("qc_checkstandard", "ccheckstandardid", "ccheckstandardname",
						sCheckStandard[k]);
			} catch (Exception e) {
				SCMEnv.out(e);
				QcTool.outException(this, e);
			}
			if (obName != null) {
				strStandardNames[k] = obName[0].toString();
			}
		}
		// 根据n个检验标准复制n个页签，页签名称为检验标准名称，页签code为table0 table1 table2........
		Vector<BillTempletBodyVO> vecTempletBody = new Vector<BillTempletBodyVO>();
		BillTempletBodyVO[] templetBodyVO = null;

		for (int j = 0; j < sCheckStandard.length; j++) {
			try {
				templetBodyVO = (BillTempletBodyVO[]) ObjectUtils.serializableClone(tmpletvo.getBodyVO());
			} catch (Exception e) {
				SCMEnv.out(e);
			}
			for (int i = 0; i < templetBodyVO.length; i++) {
				if (templetBodyVO[i].getPos() == 1) {
					templetBodyVO[i].setTableCode("table" + String.valueOf(j));
					templetBodyVO[i].setTableName(strStandardNames[j]);
					templetBodyVO[i].setTable_code("table" + String.valueOf(j));
					if (j == 0) {
						templetBodyVO[i].setTable_name(strStandardNames[j]);
					} else {
						templetBodyVO[i].setTable_name(strStandardNames[j]);
					}
					vecTempletBody.add(templetBodyVO[i]);
				}
			}
		}
		for (int k = 0; k < tmpletvo.getBodyVO().length; k++) {
			if (templetBodyVO[k].getPos() != 1) {
				vecTempletBody.add(templetBodyVO[k]);
			}
		}
		BillTempletBodyVO[] initBodyVO = new BillTempletBodyVO[vecTempletBody.size()];
		vecTempletBody.copyInto(initBodyVO);
		BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
		// 重新组织质检单模版注册信息
		BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
		BillData newbd = new BillData(initVO);
		// if(m_dialog!=null &&
		// m_dialog.getCheckbilltypecode().trim().equals("WH")){
		newbd.removeTabItems(1, "table");
		// }
		m_cardBill = new BillCardPanel();
		initMultiDetail(newbd, strStandardNames);
		m_cardBill.setBillData(newbd);
		m_cardBill.setAutoExecHeadEditFormula(true);
		// 刷界面时重新进行自定义项转换
		nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef(m_cardBill, getClientEnvironment().getCorporation()
				.getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdef", "vdef", strStandardNames.length);
		// m_cardBill
		for (int i = 0; i < strStandardNames.length; i++) {
			m_cardBill.setBodyMenuShow("table" + i, false);
		}
		//
		m_cardBill.setBodyMenuShow(false);
		initBillReference();
		initLotNumbRefPane();

		// else{

		m_cardBill.setEnabled(false);
		// }
		m_cardBill.updateUI();
		m_tabbedPane.removeAll();
		m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC0020101-000026")/*
                                                                                                       * @res
                                                                                                       * "检验单"
                                                                                                       */, m_cardBill);
		m_tabbedPane.addTab(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC00102-000010")/*
                                                                                                     * @res
                                                                                                     * "检验信息"
                                                                                                     */, getBillCardPanelInfo());
		m_tabbedPane.updateUI();
		updateUI();
		//
		m_cardBill.getBodyTabbedPane().setUI(new UITabbedPaneUI(true));
	}

	/**
   * 重新初始化模版注册项目
   */
	private void initMultiDetail(BillData bd, String[] strStandardNames) {
		// m_cardBill.setBodyMenuShow(true);
		m_cardBill.addEditListener(this);
		m_cardBill.addBodyMenuListener(this);
		final UIRefPane ref = (UIRefPane) bd.getHeadItem("cvendormangid").getComponent();
		ref.getUITextField().addMouseListener(new MouseAdapter() {
			public void mouseEntered(MouseEvent e) {
				strOldVendor = ref.getRefPK();
			}

			public void mouseExited(MouseEvent e) {
				strOldVendor = ref.getRefPK();
			}
		});
		for (int i = 0; i < strStandardNames.length; i++) {

			m_cardBill.addEditListener("table" + String.valueOf(i), this);
			m_cardBill.addBodyEditListener2("table" + String.valueOf(i), this);
		}
		BillItem item = bd.getHeadItem("vfreeitem");
		if (item.isShow()) {
			// 设置自由项
			FreeItemRefPane refPane = new FreeItemRefPane();
			refPane.setMaxLength(item.getLength());
			item.setComponent(refPane);
		}

		// 设置数量的精度控制
		item = bd.getHeadItem("nchecknum");
		if (item.isShow()) {
			UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchecknum").getComponent();
			refpane.getUITextField().setMaxLength(16);
			item.setDecimalDigits(m_numDecimal);
		}
		item = bd.getHeadItem("nassistchecknum");
		if (item.isShow()) {
			UIRefPane refpane = (UIRefPane) bd.getHeadItem("nassistchecknum").getComponent();
			refpane.getUITextField().setMaxLength(16);
			item.setDecimalDigits(m_assistDecimal);
		}
		item = bd.getHeadItem("nexchangerate");
		if (item.isShow())
			item.setDecimalDigits(m_convertDecimal);

		item = bd.getHeadItem("nqualifiednum");
		if (item.isShow())
			item.setDecimalDigits(m_numDecimal);
		item = bd.getHeadItem("nunqualifiednum");
		if (item.isShow())
			item.setDecimalDigits(m_numDecimal);
		item = bd.getHeadItem("nqualifiedrate");
		if (item.isShow())
			item.setDecimalDigits(m_convertDecimal);

		// add by yye begin 2005-05-21
		item = bd.getHeadItem("nqualifiedassinum");
		if (item.isShow())
			item.setDecimalDigits(m_assistDecimal);

		item = bd.getHeadItem("nunqualifiedassinum");
		if (item.isShow())
			item.setDecimalDigits(m_assistDecimal);

		item = bd.getHeadItem("nchangeassinum");
		if (item.isShow()) {
			UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchangeassinum").getComponent();
			refpane.getUITextField().setMaxLength(16);
			item.setDecimalDigits(m_assistDecimal);
		}

		item = bd.getHeadItem("nchangenum");
		if (item.isShow()) {
			UIRefPane refpane = (UIRefPane) bd.getHeadItem("nchangenum").getComponent();
			refpane.getUITextField().setMaxLength(16);
			item.setDecimalDigits(m_numDecimal);
		}
		// 处理表体多页签
		for (int i = 0; i < strStandardNames.length; i++) {
			item = bd.getBodyItem("table" + String.valueOf(i), "nnum");
			if (item.isShow()) {
				UIRefPane refpane = (UIRefPane) bd.getBodyItem("table" + String.valueOf(i), "nnum").getComponent();
				refpane.getUITextField().setMaxLength(16);
				item.setDecimalDigits(m_numDecimal);
			}
			item = bd.getBodyItem("table" + String.valueOf(i), "nassistnum");
			if (item.isShow()) {
				UIRefPane refpane = (UIRefPane) bd.getBodyItem("table" + String.valueOf(i), "nassistnum").getComponent();
				refpane.getUITextField().setMaxLength(16);
				item.setDecimalDigits(m_assistDecimal);
			}
			item = bd.getBodyItem("table" + String.valueOf(i), "nexchangerateb");
			if (item.isShow())
				item.setDecimalDigits(m_convertDecimal);

			// add by yye end 2005-05-21

			DefVO[] defBody = DefSetTool.getBatchcodeDef(getClientEnvironment().getCorporation().getPk_corp());
			if (defBody != null)
				bd.updateItemByDef("table" + String.valueOf(i), defBody, "cb_vuserdef", false);
			//
		}
		m_cardBill.setBillData(bd);
		m_cardBill.setAutoExecHeadEditFormula(true);
		((UIRefPane) getBillCardPanel().getHeadItem("cvendormangid").getComponent()).getUIButton().addActionListener(this);
		// 处理多页签自定义项
		nc.ui.scm.pub.def.DefSetTool.updateBillCardPanelUserDef(m_cardBill, getClientEnvironment().getCorporation()
				.getPk_corp(), nc.vo.scm.constant.ScmConst.QC_CheckArrive, "vdef", "vdef", strStandardNames.length);
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"刷新" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardRefresh() {
		String sCorpID[] = m_condClient.getSelectedCorpIDs();
		if (sCorpID == null || sCorpID.length == 0) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                     * @res
                                                                                                                     * "查询检验单"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                         * @res
                                                                                         * "没有符合条件的检验单！"
                                                                                         */);
			return;
		}
		/*
     * for(int i = 0 ; i < m_conditionVOs.length ; i ++){
     * if(m_conditionVOs[i].getFieldCode().equals("ccheckitemid")){
     * strSubSqlPower = "qc_checkbill_b2.ccheckitemid in
     * ('"+m_conditionVOs[i].getRefResult().getRefPK()+"')"; break; } }
     */
		try {
			//
			String chooseDataPower = null;
			chooseDataPower = getCheckItemDataPower();
			m_checkBillVOs = CheckbillHelper.queryCheckbill(sCorpID, m_conditionVOs, new Integer(m_nAddCondition), m_dialog
					.getChecktypeid(), m_condClient.getSourceSQL(), chooseDataPower);

			// 无查询结果
			if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
				m_bBodyQuery = null;
				m_grandVOs = null;
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                       * @res
                                                                                                                       * "查询检验单"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                           * @res
                                                                                           * "没有符合条件的检验单！"
                                                                                           */);
				getBillCardPanel().getBillData().clearViewData();
				getBillCardPanel().updateValue();
				getBillCardPanel().updateUI();
				this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000071")/*
                                                                                                           * @res
                                                                                                           * "不存在符合条件的记录！"
                                                                                                           */);
				m_tabbedPane.setEnabledAt(1, false);
				return;
			}

			// 有查询结果
			m_tabbedPane.setEnabledAt(1, true);
			// add by yy begin取得单据行号信息
			Vector lvec_SourceBillTypeCode = new Vector();
			Vector lvec_SourceBillHID = new Vector();
			Vector lvec_SourceBillBID = new Vector();
			Vector orderData = new Vector(1000);
			for (int i = 0; i < m_checkBillVOs.length; i++) {

				CheckbillHeaderVO l_CheckbillHeaderVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
				if (i == 0) {
					// 设置自由项
					nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
					freeVOParse.setFreeVO(new CheckbillHeaderVO[] { l_CheckbillHeaderVO }, "vfreeitem", "vfree", null, "cmangid",
							false);
					//
				}

				CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[i].getChildrenVO();
				if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
					for (int j = 0; j < l_CheckbillItemVO.length; j++) {
						if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null
								&& l_CheckbillItemVO[j].getCsourcebillid() != null
								&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
							lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
							lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
							lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
						}
						if (l_CheckbillItemVO[j].getInorder().intValue() == 0) {
							orderData.add(0, l_CheckbillItemVO[j]);
						} else {
							// 重新过滤主检验标准
							orderData.add(l_CheckbillItemVO[j]);
						}
					}
					QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());
				}
				CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
				orderData.copyInto(vos);
				m_checkBillVOs[i].setChildrenVO(vos);
				orderData.clear();
			}
			String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
			String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
			String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

			lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
			lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
			lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

			SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
			m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode, lstrary_SourceBillHID,
					lstrary_SourceBillBID);
			// add by yy end

			m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();
			m_bBodyQuery = new UFBoolean[m_checkBillVOs.length];
			for (int i = 0; i < m_bBodyQuery.length; i++) {
				if (i == 0)
					m_bBodyQuery[i] = new UFBoolean(true);
				else
					m_bBodyQuery[i] = new UFBoolean(false);
			}

			m_nBillRecord = 0;

			// 计算合格数量等
			calculateNum();

			// 多公司查询，改变检验单表头参照
			String pk_corp = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getPk_corp();
			if (pk_corp != null && pk_corp.trim().length() > 0) {
				// changeBillReference(pk_corp);
				m_sUnitCode = pk_corp;
			}
			initMultiPanel(m_checkBillVOs[0]);
			QcTool.setValue(getBillCardPanel(), m_checkBillVOs[0]);

			this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000120")/*
                                                                                                         * @res
                                                                                                         * "符合条件的记录数："
                                                                                                         */
					+ m_checkBillVOs.length);
			setValues();

			// add by yy begin
			setCardSourceBillInfo();

			buttonManager.setButtonsStateCard();
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                     * @res
                                                                                                                     * "查询检验单"
                                                                                                                     */, e.getMessage());
		}
		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();
		getBillCardPanel().getBillModel().execLoadFormula();
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"联查" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardRelateQuery() {
		// 查询报检点单据信息
		CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
		if (headVO == null) {
			return;
		}
		nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(this, "WH", headVO
				.getCcheckbillid(), null, getClientEnvironment().getUser().getPrimaryKey(), headVO.getVcheckbillcode());
		soureDlg.showModal();

	}

	/*
   * 入库批次检查规则: 若入库批次号存在,则: 存货(改判存货)+入库批次号->质量等级唯一 若入库批次号不存在,则:
   * 存货(改判存货)+来源单据行ID->质量等级不允许重复
   */
	private int isCheckStateDuplicated() {
		Hashtable t1 = new Hashtable();// key: 存货基础ID+入库批次号, value: 质量等级
		Hashtable t2 = new Hashtable();// key: 存货基础ID+来源单据行ID, value:
		// Vector(质量等级)

		int nRow = getBillCardPanel().getRowCount();
		Object oTemp = null;
		Vector vTemp = null;
		String cbaseid = null;

		for (int i = 0; i < nRow; i++) {
			cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(); // 表头存货
			String ccheckstatename = null;
			oTemp = getBillCardPanel().getBodyValueAt(i, "ccheckstatename");
			if (oTemp == null || oTemp.toString().trim().length() == 0)
				continue;
			ccheckstatename = oTemp.toString().trim();

			boolean bchange = false;
			oTemp = getBillCardPanel().getBodyValueAt(i, "bchange");

			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				bchange = (new UFBoolean(oTemp.toString())).booleanValue();
			}

			if (bchange) {
				oTemp = getBillCardPanel().getBodyValueAt(i, "cchgbaseid");
				if (oTemp == null || oTemp.toString().trim().length() == 0)
					continue;
				cbaseid = oTemp.toString().trim();
			}

			String vstobatchcode = null;// 入库批次号
			oTemp = getBillCardPanel().getBodyValueAt(i, "vstobatchcode");
			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				vstobatchcode = oTemp.toString().trim();
			}
			String csourcebillrowid = null;// 来源于单据行ID
			oTemp = getBillCardPanel().getBodyValueAt(i, "csourcebillrowid");
			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				csourcebillrowid = oTemp.toString().trim();
			}

			if (vstobatchcode != null && vstobatchcode.length() > 0) {
				// 若入库批次号存在,则: 存货(改判存货)+入库批次号->质量等级唯一
				oTemp = t1.get(cbaseid + vstobatchcode);
				if (oTemp == null) {
					t1.put(cbaseid + vstobatchcode, ccheckstatename);
				} else {
					if (!oTemp.toString().equals(ccheckstatename))
						return 1;
				}
			} else if (csourcebillrowid != null && csourcebillrowid.length() > 0) {
				// 若入库批次号不存在,则: 存货(改判存货)+来源单据行ID->质量等级不允许重复
				oTemp = t2.get(cbaseid + csourcebillrowid);
				if (oTemp == null) {
					vTemp = new Vector();
					vTemp.add(ccheckstatename);
					t2.put(cbaseid + csourcebillrowid, vTemp);
				} else {
					vTemp = (Vector) oTemp;
					if (vTemp.contains(ccheckstatename))
						return 2;
				}
			}
		}

		return 0;
	}

	/*
   * 送审 2006-12-29 xhq
   */
	private void onCardSend() {
		if (m_bBillEdit) {
			if (!onCardSave()) {
				showHintMessage("");
				return;
			}
		} else {
			if (m_checkBillVOs[m_nBillRecord].getHeadVo().getCreporterid() == null) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001118")/*
                                                                                                                       * @res
                                                                                                                       * "操作员不能为空，请修改单据置入操作员"
                                                                                                                       */, "操作员不能为空，请修改单据置入操作员"/*-=notranslate=-*/);
				showHintMessage("");
				return;
			}
		}

		CheckbillVO vo = m_checkBillVOs[m_nBillRecord];
		if (vo.getHeadVo().getIbillstatus() != 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "SCMCOMMON000000080")/*
                                                                                                                   * @res
                                                                                                                   * "送审"
                                                                                                                   */, "单据送审必须为自由状态，请修改保存后再提交送审"/*-=notranslate=-*/);
			showHintMessage("");
			return;
		}
		Object[] userOb = { getClientEnvironment().getUser().getPrimaryKey(), getClientEnvironment().getDate(),
				m_checkBillVOs[m_nBillRecord].getHeadVo().getPk_corp() };
		Object[] userObjAry = new Object[1];
		userObjAry[0] = userOb;
		try {
			if (isNullQulityLevel()) {
				throw new BusinessException(nc.bs.ml.NCLangResOnserver.getInstance().getStrByID("C0020101",
						"UPPC0020101-000009")/* @res "质量等级为空！" */);
			}
			// PfUtilClient.processAction("SAVE", strBilltemplet,
			// dToday.toString(), vo, userObjAry);
			nc.ui.pub.pf.PfUtilClient.processBatchFlow(this, "SAVE", strBilltemplet, getClientEnvironment().getDate()
					.toString(), new CheckbillVO[] { vo }, userObjAry);
			CheckbillVO VO = CheckbillHelper.queryCheckbillByHeadKey(vo.getHeadVo().getCcheckbillid());
			if (VO != null) {
				// 设置自由项
				nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
				freeVOParse.setFreeVO(new CheckbillHeaderVO[] { (CheckbillHeaderVO) VO.getParentVO() }, null, "cmangid", false);
				//
				m_checkBillVOs[m_nBillRecord] = VO;
				VO.getHeadVo().setVinvbatchcode(VO.getBodyVos()[0].getVstobatchcode());
			}
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "SCMCOMMON000000080")/*
                                                                                                                   * @res
                                                                                                                   * "送审"
                                                                                                                   */, e.getMessage());
			showHintMessage("");
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH023")/*
                                                                                     * @res
                                                                                     * "已送审"
                                                                                     */);
		// 审批后置入批次档案的各个属性
		CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[m_nBillRecord].getChildrenVO();
		QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());

		QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);
		setValues();

		// add by yy begin
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();

		// add by yy end

		calculateNum();

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();
	}

	/**
   * 
   * 作者：田锋涛 功能：用于质量等级的非空校验 参数： 返回： 例外： 日期：2009-10-23 修改日期， 修改人，修改原因，注释标志
   */
	private boolean isNullQulityLevel() {
		for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
			if (m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstate_bid() == null)
				return true;
		}
		return false;
	}

	/*
   * 审批状态查询 2006-12-29 xhq
   */
	public void onCardWorkFlowQuery() {

		if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {
			// 如果该单据处于正在审批状态，执行下列语句：
			nc.ui.pub.workflownote.FlowStateDlg approvestatedlg = new nc.ui.pub.workflownote.FlowStateDlg(
			// this, "WH",
					this, strBilltemplet, // 2009/10/19 田锋涛
					((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getCcheckbillid());
			approvestatedlg.showModal();
		}
	}

	/**
   * 
   * 入库批次号校验，主要用于手工把单行拆分多行时候的校验，即如果拆分的行来源于同一行，则质量等级相同的情况下，入库批次号不能相同。 校验规则为：
   * 存货基础ID + 入库批次号 + 来源行ID -> 质量等级
   * 
   * 2009/10/22 田锋涛 添加了校验规则：来源行id
   */
	private boolean batchCodeDuplicated() {
		Hashtable t1 = new Hashtable();// key: 存货基础ID+入库批次号+质量等级, value: 质量等级

		int nRow = getBillCardPanel().getRowCount();
		Object oTemp = null;
		String cbaseid = null;

		for (int i = 0; i < nRow; i++) {
			cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject(); // 表头存货
			String ccheckstatename = null;
			oTemp = getBillCardPanel().getBodyValueAt(i, "ccheckstatename");
			if (oTemp == null || oTemp.toString().trim().length() == 0)
				continue;
			ccheckstatename = oTemp.toString().trim();

			boolean bchange = false;
			oTemp = getBillCardPanel().getBodyValueAt(i, "bchange");// 是否改判

			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				bchange = (new UFBoolean(oTemp.toString())).booleanValue();
			}

			if (bchange) {
				oTemp = getBillCardPanel().getBodyValueAt(i, "cchgbaseid");
				if (oTemp == null || oTemp.toString().trim().length() == 0)
					continue;
				cbaseid = oTemp.toString().trim();
			}

			String vstobatchcode = null;// 入库批次号
			oTemp = getBillCardPanel().getBodyValueAt(i, "vstobatchcode");
			if (oTemp != null && oTemp.toString().trim().length() > 0) {
				vstobatchcode = oTemp.toString().trim();
			}
			String sourceRowId = "";// 来源行id
			if (getBillCardPanel().getBodyValueAt(i, "csourcebillrowid") != null)
				sourceRowId = getBillCardPanel().getBodyValueAt(i, "csourcebillrowid").toString();

			if (vstobatchcode != null && vstobatchcode.length() > 0) {
				String sKey = cbaseid + vstobatchcode + sourceRowId;
				if (t1.containsKey(sKey))// 包含key，数据重复
					return true;
				t1.put(sKey, ccheckstatename);
			}
		}

		return false;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"保存" 输入参数: 返回值: 异常处理: 日期:
   */
	private boolean onCardSave() {

		getBillCardPanel().stopEditing();

		// 增加对校验公式的支持,错误显示由UAP处理
		if (!getBillCardPanel().getBillData().execValidateFormulas()) {
			return false;
		}

		// 获得界面数据
		int nRow = getBillCardPanel().getRowCount();
		CheckbillVO VO = new CheckbillVO(nRow);
		getBillCardPanel().getBillValueVO(VO);
		boolean isBcompcheck = false;// 是否成分检验
		if (VO.getHeadVo().getBcompcheck() != null)
			isBcompcheck = VO.getHeadVo().getBcompcheck().booleanValue();

		if (VO.getHeadVo().getCprayerid() == null) {
			VO.getHeadVo().setCprayerid("system");
		}

		// 保存前检查
		if (nRow == 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000074")/*
                                                                                                                     * @res
                                                                                                                     * "检验单保存"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000075")/*
                                                                                         * @res
                                                                                         * "行不能为空!"
                                                                                         */);
			return false;
		}

//		  arvin 去掉数量校验
		// 数量检查
//		if (!isNumLegal()) {
//			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000057")/*
//                                                                                                                     * @res
//                                                                                                                     * "数量检验"
//                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000058")/*
//                                                                                         * @res
//                                                                                         * "累计数量大于报检数量！"
//                                                                                         */);
//			return false;
//		}

		// 入库批次重复性检查
		int nReturn = isCheckStateDuplicated();
		if (nReturn == 1) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000074")/*
                                                                                                                     * @res
                                                                                                                     * "检验单保存"
                                                                                                                     */, "存货+入库批次号对应的质量等级不唯一!"/*-=notranslate=-*/);
			return false;
		} else if (nReturn == 2) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000074")/*
                                                                                                                     * @res
                                                                                                                     * "检验单保存"
                                                                                                                     */, "存货+来源单据行ID对应的质量等级重复!"/*-=notranslate=-*/);
			return false;
		}
		boolean bcheck = batchCodeDuplicated();
		if (bcheck) {
			MessageDialog.showErrorDlg(this, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000010")/*同一存货不能有相同的批次*/, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000010")/*同一存货不能有相同的批次*/);
			return false;
		}

		// 如果是手工保检，则表头报检数量不能<=0
		// v53改为表头报检数量不能<0
		// if (isFromPU()) {
		String lstr_nchecknum = (String) getBillCardPanel().getHeadItem("nchecknum").getValueObject(); // 主数量
		double iNum;
		if (lstr_nchecknum == null || lstr_nchecknum.equals("") || lstr_nchecknum.equals("0.00")) {
			lstr_nchecknum = "0";
			VO.getHeadVo().setNchecknum(new UFDouble(0));
		}
		try {
			iNum = new UFDouble(lstr_nchecknum).doubleValue();
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000011")/*不能输入字符，谢谢！*/, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000011")/*不能输入字符，谢谢！*/);
			return false;
		}
		if (iNum < 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000057")/*
                                                                                                                     * @res
                                                                                                                     * "数量检验"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000076")/*
                                                                                         * @res
                                                                                         * "必须输入报检数量！"
                                                                                         */);
			return false;
		}

		double ldbl_nchecknum = (new UFDouble(lstr_nchecknum)).doubleValue();
		if (isFromPU() && ldbl_nchecknum < 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000057")/*
                                                                                                                     * @res
                                                                                                                     * "数量检验"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000077")/*
                                                                                         * @res
                                                                                         * "报检数量必须大于0！"
                                                                                         */);
			return false;
		} else if (!isFromPU() && ldbl_nchecknum <= 0 && !isBcompcheck) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000057")/*
                                                                                                                     * @res
                                                                                                                     * "数量检验"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000077")/*
                                                                                         * @res
                                                                                         * "报检数量必须大于0！"
                                                                                         */);
			return false;
		}

		// }

		/*
     * comment by yye begin 2004-11-01 为支持改判而注掉 if (!isAssistNumLegal()) {
     * MessageDialog.showErrorDlg(this, "辅数量检验", "累计辅数量大于报检辅数量！"); return; }
     */// comment by yye end
		// 获得存货管理及基础ID
		String cmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();
		String cbaseid = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject();

		// add by yye begin 20060512 入库批次号是否能为空的判断
		if (cmangid != null && cmangid.trim().length() > 0) {
			boolean lb_isHeadBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(cmangid);
			for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
				Object lobj_bchange = getBillCardPanel().getBodyValueAt(i, "bchange");
				boolean lb_bchange = false;// 是否改判了
				if (lobj_bchange != null && lobj_bchange.toString().trim().length() > 0) {
					lb_bchange = (new UFBoolean(lobj_bchange.toString())).booleanValue();
				}

				boolean lbol_RowBatchManaged = false;// 综合判断的该行是否批次管理
				if (lb_bchange) {// 如果改判了
					Object lobj_cchgmangid = getBillCardPanel().getBodyValueAt(i, "cchgmangid");
					if (lobj_cchgmangid != null) {
						lbol_RowBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(lobj_cchgmangid.toString().trim());
					}
				} else {
					lbol_RowBatchManaged = lb_isHeadBatchManaged;
				}

				if (lbol_RowBatchManaged) {// 如果为批次管理存货行
					Object lobj_vstobatchcode = getBillCardPanel().getBillModel("table0").getValueAt(i, "vstobatchcode");

					if (lobj_vstobatchcode == null || lobj_vstobatchcode.toString().trim().length() == 0) {
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000074")/*
                                       * @res "检验单保存"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000126")/*
                                                                                               * @res
                                                                                               * "入库批次号不能为空!"
                                                                                               */);
						return false;
					}
				}

			}
		}

		// 获得工序和生产订单号
		// UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem(
		// "cprocedureid").getComponent();
		// String cprocedureid = refPane.getUITextField().getText();
		UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem("vproductordercode").getComponent();
		String vproductordercode = refPane.getUITextField().getText();

		// 获得备注
		refPane = (UIRefPane) getBillCardPanel().getHeadItem("vmemo").getComponent();
		String vmemo = refPane.getUITextField().getText();

		// 所有行集合(包括删除行)
		Vector vData = new Vector();

		// 设置表头信息
		CheckbillHeaderVO headVO = (CheckbillHeaderVO) VO.getParentVO();
		try {
			// 获得单据号
			if (m_bBillAdd) {
				String vCode = getBillCode(headVO);
				headVO.setVcheckbillcode(vCode);

			}
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000078")/*
                                                                                                                     * @res
                                                                                                                     * "获得单据号"
                                                                                                                     */, e.getMessage());
			return false;
		}
		headVO.setPk_corp(m_sUnitCode);
		headVO.setDr(new Integer(0));
//		arvin 审批中修改
		if(headVO.getIbillstatus()==null ||headVO.getIbillstatus().intValue()==0){
		  headVO.setIbillstatus(new Integer(0));
		}
//		headVO.setIbillstatus(new Integer(0));
		if (m_bBillAdd)
			headVO.setNversion(new Integer(0));
		// headVO.setCprocedureid(cprocedureid);
		headVO.setVproductordercode(vproductordercode);
		headVO.setVmemo(vmemo);

		if (isFromPU()) {
			refPane = (UIRefPane) getBillCardPanel().getHeadItem("vfreeitem").getComponent();
			if (refPane != null && refPane.isShowing()) {
				FreeItemRefPane freeRef = (FreeItemRefPane) refPane;
				FreeVO freeVO = freeRef.getFreeVO();
				headVO.setVfree1(freeVO.getVfree1());
				headVO.setVfree2(freeVO.getVfree2());
				headVO.setVfree3(freeVO.getVfree3());
				headVO.setVfree4(freeVO.getVfree4());
				headVO.setVfree5(freeVO.getVfree5());
			}
		}

		// 如果检验类型中规定保存即审批，则给审批人和审批时间赋值
		if (m_dialog != null && m_dialog.getSaveAudit()) {
			headVO.setCauditpsn(getClientEnvironment().getUser().getPrimaryKey());
			headVO.setDauditdate(getClientEnvironment().getDate().toString());
		}
		Vector vecCirTotal = new Vector();

		CheckbillItemVO[] voaBody = null;
		for (int i = 0; i < sCheckStandards.length; i++) {
			Vector vecCir = new Vector();
			voaBody = (CheckbillItemVO[]) getBillCardPanel().getBillModel("table" + String.valueOf(i)).getBodyValueVOs(
					CheckbillItemVO.class.getName());
			if (voaBody.length == 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000074")/*
                                                                                                                       * @res
                                                                                                                       * "检验单保存"
                                                                                                                       */, QCTool.getStandardNameByID(sCheckStandards[i])
						+ nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000075")/*
                                                                                             * @res
                                                                                             * "行不能为空!"
                                                                                             */);
				return false;
			}
			for (int j = 0; j < voaBody.length; j++) {
				vecCir.add(voaBody[j]);
			}
			vecCirTotal.addAll(vecCir);
		}
		voaBody = new CheckbillItemVO[vecCirTotal.size()];
		vecCirTotal.copyInto(voaBody);
		VO.setChildrenVO(voaBody);
		// 设置行编辑状态
		CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) VO.getChildrenVO();
		for (int i = 0; i < bodyVO.length; i++) {
			if (m_bBillEdit) {
				bodyVO[i].setStatus(2);
			}
			if (bodyVO[i].getInorder().compareTo(new UFDouble(0)) == 0) {
				bodyVO[i].setBdefault(UFBoolean.TRUE);
			}
			bodyVO[i].setEditStatus(getBillCardPanel().getBillModel().getRowState(i));
			bodyVO[i].setPk_corp(m_sUnitCode);
			bodyVO[i].setDr(new Integer(0));
			bodyVO[i].setCmangid(cmangid); // 设置存货信息
			bodyVO[i].setCbaseid(cbaseid); // 设置存货信息
			// v5.02起表体供应商批次号不再取值表头“存货批次号”
			// bodyVO[i].setVinvbatchcode(lStr_invbatchcode); // 设置存货批次

			// 设置来源单据信息
			if (bodyVO[i].getCsource() == null)
				bodyVO[i].setCsource(headVO.getCsource());

			// 设置自由项
			bodyVO[i].setVfree1(headVO.getVfree1());
			bodyVO[i].setVfree2(headVO.getVfree2());
			bodyVO[i].setVfree3(headVO.getVfree3());
			bodyVO[i].setVfree4(headVO.getVfree4());
			bodyVO[i].setVfree5(headVO.getVfree5());

			/** *****V5.0版本后，模板修改产生问题****** */
			// 是否合格品：boolean项默认值为空，手工赋值false
			if (bodyVO[i].getBqualified() == null) {
				bodyVO[i].setBqualified(UFBoolean.FALSE);
				// bodyVO[i].setEditStatus(2);
			}
			if (bodyVO[i].getCcheckbill_b1id() == null) {
				bodyVO[i].setStatus(1);
			}
			/** *****V5.0版本后，模板修改产生问题****** */

			vData.addElement(bodyVO[i]);
		}

		// 保存前检验
		int nError = -1;
		try {
			headVO.validate();

			// 非成分检验单需要检查表体数量不能为空
			boolean lbol_compcheck = false;
			if (isFromMM()) {
				if (getBillCardPanel().getHeadItem("bcompcheck") != null
						&& getBillCardPanel().getHeadItem("bcompcheck").getValueObject() != null
						&& getBillCardPanel().getHeadItem("bcompcheck").getValueObject().toString().trim().length() > 0) {
					lbol_compcheck = new UFBoolean(getBillCardPanel().getHeadItem("bcompcheck").getValueObject().toString()
							.trim()).booleanValue();
				}
			}

			if (!lbol_compcheck) {
				for (nError = 0; nError < bodyVO.length; nError++) {
					if (sCheckStandards[0].equals(bodyVO[nError].getCcheckstandardid()))
						bodyVO[nError].validate();// 检查表体数量不能为空
				}
			}
			//

		} catch (Exception e) {
			SCMEnv.out(e);
			String sMsg = "";
			if (nError == -1) {
				sMsg += e.getMessage();
			} else {
				sMsg += e.getMessage() + nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000121")/*
                                                                                                               * @res "
                                                                                                               * 出现问题的检验结果行为："
                                                                                                               */
						+ CommonConstant.BEGIN_MARK + (nError + 1) + CommonConstant.END_MARK;
			}
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000001")/*
                                                                                                               * @res
                                                                                                               * "保存"
                                                                                                               */, sMsg);
			// 保存不成功，对于增加单据，回退单据号
			if (m_bBillAdd) {
				try {
					CheckbillHelper.returnBillCode(VO);
				} catch (Exception ex) {
					SCMEnv.out(ex);
				}
			}
			//
			return false;

		}
		// 2010-09-06 tianft 得到字段在模板上的位置，避免vTemp.elementAt(0)这样的形式
		int tsPos = getBillCardPanel().getBodyColByKey("ts");
		int ccheckbill_b1idPos = getBillCardPanel().getBodyColByKey("ccheckbill_b1id");
		for (int j = 0; j < sCheckStandards.length; j++) {
			// 获得删行
			Vector vDelData = getBillCardPanel().getBillModel("table" + String.valueOf(j)).getDeleteRow();
			if (vDelData != null && vDelData.size() > 0) {
				for (int i = 0; i < vDelData.size(); i++) {
					Vector vTemp = (Vector) vDelData.elementAt(i);
					// //只处理已经保存过的行，不处理新增行
					// if (vTemp.elementAt(0) != null) {
					CheckbillItemVO tempVO = new CheckbillItemVO();
					tempVO.setCcheckbill_b1id((String) vTemp.elementAt(ccheckbill_b1idPos));
					tempVO.setTs((String) vTemp.elementAt(tsPos));
					tempVO.setEditStatus(3); // 删除状态
					tempVO.setDr(new Integer(1));
					tempVO.setStatus(3);
					vData.addElement(tempVO);
					// }
				}
			}
		}
		// 设置检验信息的存货信息
		if (m_grandVOs != null && m_grandVOs.length > 0) {
			for (int i = 0; i < m_grandVOs.length; i++) {
				CheckbillB2ItemVO itemVO[] = (CheckbillB2ItemVO[]) m_grandVOs[i].getChildrenVO();
				if (itemVO != null && itemVO.length > 0) {
					for (int j = 0; j < itemVO.length; j++) {
						itemVO[j].setCmangid(cmangid);
						itemVO[j].setCbaseid(cbaseid);

						// 设置自由项
						itemVO[j].setVfree1(headVO.getVfree1());
						itemVO[j].setVfree2(headVO.getVfree2());
						itemVO[j].setVfree3(headVO.getVfree3());
						itemVO[j].setVfree4(headVO.getVfree4());
						itemVO[j].setVfree5(headVO.getVfree5());
					}
				}
			}
		}

		// 所有行均传送到BO保存
		CheckbillItemVO tempVOs[] = new CheckbillItemVO[vData.size()];
		vData.copyInto(tempVOs);
		VO.setChildrenVO(tempVOs);
		VO.setGrandVO(m_grandVOs);

		for (int i = 0; i < VO.getBodyVos().length; i++) {
			// 由于表体的单据模板上面没有物料批次号字段，在此向VO中设置物料批次号
			VO.getBodyVos()[i].setVinvbatchcode(VO.getHeadVo().getVinvbatchcode());
			if (VO.getBodyVos()[i].getNnum() == null && m_dialog.getCheckbilltypecode().trim().equals("WH")) {
				VO.getBodyVos()[i].setNnum(new UFDouble(0));
			}
			try {
				if (!isBcompcheck)
					VO.getBodyVos()[i].validate();
				else {
					if (VO.getBodyVos()[i].getBchange() != null && VO.getBodyVos()[i].getBchange().booleanValue()
							&& PuPubVO.getString_TrimZeroLenAsNull(VO.getBodyVos()[i].getCchgmangid()) == null) {
						throw new NullFieldException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("C0020101",
								"UPPC0020101-000103")/* @res "下列字段不能为空：" */
								+ nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("C0020101", "UPTC0020101-000012")/*
                                                                                                           * @res
                                                                                                           * "改判存货编码"
                                                                                                           */);
					}
				}
			} catch (ValidationException e) {
				MessageDialog.showErrorDlg(this, NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000074")/*
                                                                                                               * @res
                                                                                                               * "检验单保存"
                                                                                                               */, e.getMessage());
				return false;
			}
		}

		// 删除的检验信息处理*****
		CheckbillVO dealingVO = VO;
		if (m_hDelInfo.size() > 0 && m_hDelInfo.get(headVO.getCcheckbillid()) != null && m_grandVOs != null) {
			CheckbillB2ItemVO itemVO[] = (CheckbillB2ItemVO[]) m_hDelInfo.get(headVO.getCcheckbillid());
			CheckbillB2VO grandVO[] = (CheckbillB2VO[]) dealingVO.getGrandVO();
			for (int i = 0; i < grandVO.length; i++) {
				Vector vTemp = new Vector();
				CheckbillB2HeaderVO head = (CheckbillB2HeaderVO) grandVO[i].getParentVO();
				CheckbillB2ItemVO body[] = (CheckbillB2ItemVO[]) grandVO[i].getChildrenVO();
				for (int j = 0; j < body.length; j++)
					vTemp.addElement(body[j]);
				for (int j = 0; j < itemVO.length; j++) {
					if (head.getVsamplecode().equals(itemVO[j].getVsamplecode()))
						// 需要删除的
						itemVO[j].setEditStatus(3);
					vTemp.addElement(itemVO[j]);
				}

				body = new CheckbillB2ItemVO[vTemp.size()];
				vTemp.copyInto(body);
				grandVO[i].setChildrenVO(body);
			}
			dealingVO.setGrandVO(grandVO);
		}
		// *****
		dealingVO.getHeadVo().setCoperatoridnow(getOperatorId());
		if (strCheckbillCode != null && !strCheckbillCode.equalsIgnoreCase(dealingVO.getHeadVo().getVcheckbillcode())
				&& !dealingVO.getHeadVo().getVcheckbillcode().equals(dealingVO.getHeadVo().getVcheckbillcode())) {
			dealingVO.getHeadVo().setVoldcheckbillcode(strCheckbillCode);
		}

		// 保存
		try {
			// 支持库存回写参数
			Object[] userObj = { getClientEnvironment().getUser().getPrimaryKey(), getClientEnvironment().getDate(),
					getClientEnvironment().getCorporation().getPk_corp() };
			Object[] userObjAry = new Object[dealingVO.getBodyVos().length];
			for (int i = 0; i < userObjAry.length; i++) {
				userObjAry[i] = userObj;
			}
			ArrayList list = CheckbillHelper.doSave(dealingVO, new UFBoolean(m_bBillAdd), getClientEnvironment().getUser()
					.getPrimaryKey(), new UFBoolean(m_dialog == null ? false : m_dialog.getSaveAudit()),
					new UFBoolean(m_bInfoDel), userObjAry, strBilltemplet, vContain);
			// 判断是否保存及审批

			if (list != null && list.size() > 0) {
				ArrayList list1 = (ArrayList) list.get(0); // 检验单头主键+新增体主键
				ArrayList list2 = (ArrayList) list.get(1); // 检验单头时间戳+新增/修改体时间戳
				ArrayList list3 = (ArrayList) list.get(2); // 新增检验信息主键
				ArrayList list4 = (ArrayList) list.get(3); // 新增/修改信息时间戳
				CheckbillHeaderVO[] headerVos = (CheckbillHeaderVO[]) list.get(4);
				if (headerVos != null && headerVos.length > 0) {
					headVO.setTaudittime(headerVos[0].getTaudittime());
				}

				if (list1 != null && list1.size() > 0) {
					// 设置检验单头主键+新增体主键
					headVO.setCcheckbillid((String) list1.get(0));
					int j = 1;
					for (int i = 0; i < bodyVO.length; i++) {
						if (bodyVO[i].getStatus() == 1) {
							bodyVO[i].setCcheckbillid((String) list1.get(0));
							bodyVO[i].setCcheckbill_b1id((String) list1.get(j));
							j++;
						}
					}
				}

				if (list2 != null && list2.size() > 0) {
					// 设置检验单头时间戳+新增/修改体时间戳
					headVO.setTs((String) list2.get(0));
					int j = 1;
					for (int i = 0; i < bodyVO.length; i++) {
						if (bodyVO[i].getStatus() == 1 || bodyVO[i].getStatus() == 2) {
							bodyVO[i].setTs((String) list2.get(j));
							bodyVO[i].setEditStatus(0);
							j++;
						}
					}
				}
				
				if (list3 != null && list3.size() > 0) {
					// 设置新增检验信息主键
					int k = 0;
					if (m_grandVOs != null && m_grandVOs.length > 0) {
						for (int i = 0; i < m_grandVOs.length; i++) {
							CheckbillB2ItemVO itemVO[] = (CheckbillB2ItemVO[]) m_grandVOs[i].getChildrenVO();
							if (itemVO != null && itemVO.length > 0 && itemVO[0].getCcheckbill_b2id()==null) {
								for (int j = 0; j < itemVO.length; j++) {
									if (itemVO[j].getEditStatus() == 1 || itemVO[j].getEditStatus() == 0) {
										itemVO[j].setCcheckbillid((String) list1.get(0));
										itemVO[j].setCcheckbill_b2id((String) list3.get(k));
										k++;
									}
								}
							}
						}
					}
				}

				if (list4 != null && list4.size() > 0) {
					// 设置新增/修改信息时间戳
					if (m_grandVOs != null && m_grandVOs.length > 0) {
						for (int i = 0; i < m_grandVOs.length; i++) {
							CheckbillB2ItemVO itemVO[] = (CheckbillB2ItemVO[]) m_grandVOs[i].getChildrenVO();
							if (itemVO != null && itemVO.length > 0) {
								int k = 0;
								for (int j = 0; j < itemVO.length; j++) {
									if (itemVO[j].getEditStatus() == 0 || itemVO[j].getEditStatus() == 1
											|| itemVO[j].getEditStatus() == 2) {
										itemVO[j].setTs((String) list4.get(k));
										itemVO[j].setEditStatus(0);
										k++;
									}
								}
							}
						}
					}
				}

			} else {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000001")/*
                                                                                                                 * @res
                                                                                                                 * "保存"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000079")/*
                                                                                           * @res
                                                                                           * "无返回值！数据错误！"
                                                                                           */);

				// 保存不成功，对于增加单据，回退单据号
				if (m_bBillAdd) {
					try {
						CheckbillHelper.returnBillCode(VO);
					} catch (Exception ex) {
						SCMEnv.out(ex);
					}
				}
				//

				return false;
			}

			// 保存成功，重新设置VO
			if (m_grandVOs != null && m_grandVOs.length > 0) {
				for (int i = 0; i < m_grandVOs.length; i++) {
					CheckbillB2ItemVO itemVO[] = (CheckbillB2ItemVO[]) m_grandVOs[i].getChildrenVO();
					if (itemVO != null && itemVO.length > 0) {
						Vector vTemp = new Vector();
						for (int j = 0; j < itemVO.length; j++) {
							if (itemVO[j].getEditStatus() != 3)
								vTemp.addElement(itemVO[j]);
						}
						if (vTemp.size() > 0) {
							itemVO = new CheckbillB2ItemVO[vTemp.size()];
							vTemp.copyInto(itemVO);
						} else {
							itemVO = null;
						}
						m_grandVOs[i].setChildrenVO(itemVO);
					}
				}
			}
			// modify by hanbin 原因：不知道此处代码干什么用的，解决：手工质检单，新增保存后，列表显示，第一张单子没有单据号
			// if (m_checkBillVOs != null)
			// m_checkBillVOs[m_nBillRecord].getHeadVo().setVcheckbillcode(null);
			strCheckbillCode = headVO.getVcheckbillcode();
			VO.setParentVO(headVO);
			VO.setChildrenVO(bodyVO);
			VO.setGrandVO(m_grandVOs);

			if (m_bBillAdd) {
				// 新增
				if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {
					// 已存在检验单
					Vector vTemp1 = new Vector();
					Vector vTemp2 = new Vector();
					for (int i = 0; i < m_checkBillVOs.length; i++) {
						vTemp1.addElement(m_checkBillVOs[i]);
						vTemp2.addElement(m_bBodyQuery[i]);
					}
					vTemp1.addElement(VO);
					vTemp2.addElement(new UFBoolean(true));
					m_checkBillVOs = new CheckbillVO[vTemp1.size()];
					vTemp1.copyInto(m_checkBillVOs);
					m_bBodyQuery = new UFBoolean[vTemp2.size()];
					vTemp2.copyInto(m_bBodyQuery);

					m_nBillRecord = vTemp1.size() - 1;
				} else {
					// 不存在检验单
					m_checkBillVOs = new CheckbillVO[1];
					m_checkBillVOs[0] = VO;
					m_bBodyQuery = new UFBoolean[1];
					m_bBodyQuery[0] = new UFBoolean(true);

					m_nBillRecord = 0;
				}
			} else {
				// 编辑,已存在检验单
				m_checkBillVOs[m_nBillRecord] = VO;
			}

			// 清除检验信息缓存
			m_grandVOs = null;

		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000001")/*
                                                                                                               * @res
                                                                                                               * "保存"
                                                                                                               */, e.getMessage());

			// 保存不成功，对于增加单据，回退单据号
			if (m_bBillAdd) {
				try {
					CheckbillHelper.returnBillCode(VO);
				} catch (Exception ex) {
					SCMEnv.out(ex);
				}
			}
			//

			return false;
		}

		this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH005")/*
                                                                                         * @res
                                                                                         * "检验单保存成功！"
                                                                                         */);
		// 改变编辑状态
		m_bBillAdd = false;
		m_bBillEdit = false;
		m_bInfoDel = false;
		getBillCardPanel().setEnabled(false);
		// 保存置入批次档案的各个属性
		CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) VO.getChildrenVO();
		QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());
		// 批次档案时间戳
		m_checkBillVOs[m_nBillRecord] = VO;
		if (m_dialog != null && m_dialog.getSaveAudit()) {
			m_checkBillVOs[m_nBillRecord].getHeadVo().setIbillstatus(3);

		}
		QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);
		
		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();

		setValues();

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();

		// add by yy begin
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();
		// add by yy end
		if (mbol_CancelFromDisCardInfo) {

			mbol_CancelFromDisCardInfo = false;
		}
		m_hDelInfo = new Hashtable();
		return true;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"切换" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardSwitch() {
		m_tabbedPane.setVisible(false);
		add(getBillListPanel(), java.awt.BorderLayout.CENTER);
		getBillListPanel().setEnabled(false);

		this.setButtons(buttonManager.m_btnList);

		// since v53, 按钮逻辑重构, by Czp
		// m_hButtonState = new Hashtable();

		// 隐藏pk_corp,cpraycorp,cchecktypeid，cpraydeptid，cvendormangid，ccheckstateid,ccheckmodeid,ccheckdeptid
		// cprayerid,creporterid,cauditpsn,ccheckstandardid,cworkshopid,cproducerid
		// cstoreorganization,cpurorganization,cprojectid,cemployeeid,cwarehouseid
		getBillListPanel().hideHeadTableCol("pk_corp");
		getBillListPanel().hideHeadTableCol("cpraycorp");
		getBillListPanel().hideHeadTableCol("cchecktypeid");
		getBillListPanel().hideHeadTableCol("cpraydeptid");
		getBillListPanel().hideHeadTableCol("cvendormangid");
		getBillListPanel().hideHeadTableCol("ccheckstateid");
		getBillListPanel().hideHeadTableCol("ccheckmodeid");
		getBillListPanel().hideHeadTableCol("ccheckstandardid");
		getBillListPanel().hideHeadTableCol("ccheckdeptid");
		getBillListPanel().hideHeadTableCol("cprayerid");
		getBillListPanel().hideHeadTableCol("creporterid");
		getBillListPanel().hideHeadTableCol("cauditpsn");
		getBillListPanel().hideHeadTableCol("cworkshopid");
		getBillListPanel().hideHeadTableCol("cproducerid");
		getBillListPanel().hideHeadTableCol("cstoreorganization");
		getBillListPanel().hideHeadTableCol("cpurorganization");
		getBillListPanel().hideHeadTableCol("cprojectid");
		getBillListPanel().hideHeadTableCol("cemployeeid");
		getBillListPanel().hideHeadTableCol("cwarehouseid");
		getBillListPanel().hideHeadTableCol("cassistunitid");

		// 刷新界面
		if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {
			// 缓存中有数据
			Vector vTemp = new Vector();
			for (int i = 0; i < m_checkBillVOs.length; i++)
				vTemp.addElement(m_checkBillVOs[i].getParentVO());
			CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp.size()];
			vTemp.copyInto(headVOs);
			Vector vecBody = new Vector();
			for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
				if (m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getInorder().intValue() == 0) {
					vecBody.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				}
			}
			CheckbillItemVO bodyVOs[] = new CheckbillItemVO[vecBody.size()];
			vecBody.copyInto(bodyVOs);

			getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);

			getBillListPanel().getHeadBillModel().execLoadFormula();
			getBillListPanel().getHeadBillModel().updateValue();

			getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
			getBillListPanel().getBodyBillModel().execLoadFormula();
			for (int i = 0; i < headVOs.length; i++) {
				if (headVOs[i].getCprayerid() != null && headVOs[i].getCprayerid().equalsIgnoreCase("system")) {
					getBillListPanel().getHeadBillModel().setValueAt("system", i, "cprayername");
				}
			}
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().getHeadBillModel().setRowState(m_nBillRecord, BillModel.SELECTED);
			getBillListPanel().updateUI();

			// since v53, 按钮逻辑重构, by Czp
			// // 除全消外其它按钮正常
			// for (int i = 0; i < 10; i++) {
			// if (i == 1)
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(false) });
			// else
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(true) });
			// }

			// 设置存货编码
			setInventoryCode(headVOs);

			// add by yy begin
			for (int i = 0; i < getBillListPanel().getBodyBillModel().getRowCount(); i++) {
				if (getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid") != null
						&& !getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid").toString().trim().equals("")) {
					String ls_csourcebillrowid = getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid")
							.toString().trim();
					if (m_HTSourcebillInfo != null && m_HTSourcebillInfo.containsKey(ls_csourcebillrowid)) {
						String[] l_strary = (String[]) m_HTSourcebillInfo.get(ls_csourcebillrowid);
						getBillListPanel().getBodyBillModel().setValueAt(l_strary[0], i, "csourcebilltypename");
						getBillListPanel().getBodyBillModel().setValueAt(l_strary[2], i, "csourcebillrowno");

					}
				}
			}
			// add by yy end

		} else {
			// 缓存无数据
			getBillListPanel().getHeadBillModel().clearBodyData();
			getBillListPanel().getHeadBillModel().updateValue();
			getBillListPanel().getBodyBillModel().clearBodyData();
			getBillListPanel().getBodyBillModel().updateValue();
			getBillListPanel().updateUI();

		}

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateList();

		m_nUIState = 1;
		getBillListPanel().getHeadTable().setRowSelectionInterval(m_nBillRecord, m_nBillRecord);
		// since v53, 按钮逻辑重构, by Czp
		// changeButtonState();

		return;
	}

	private void onShowBatchDLG() {
		if (batchDLG == null) {
			batchDLG = new CheckStatusBatchDLG(this, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000012")/*批处理对话框*/, m_sUnitCode);
		}
		int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
		batchDLG.refPanel.getRefModel().setWherePart(
				" ccheckstateid = '"
						+ (String) getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(0, "ccheckstateid")
						+ "' ");
		batchDLG.showModal();
		if (batchDLG.getResult() == UIDialog.ID_OK) {

			String strCheckStatus = batchDLG.getStrCheckStatus();
			int iRowCount = getBillCardPanel().getBillTable().getRowCount();
			if (getBillCardPanel().getBodyItem("table" + String.valueOf(index), "ccheckstatename").isEnabled()
					&& iRowCount > 0) {
				for (int i = 0; i < iRowCount; i++) {
					getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(strCheckStatus, i,
							"ccheckstatename");
				}
				getBillCardPanel()
						.getBillModel("table" + String.valueOf(index))
						.execFormulas(
								new String[] {
										"ccheckstate_bid->getColValue(qc_checkstate_b,ccheckstate_bid,ccheckstatename,ccheckstatename)",
										"ccheckstatename->getColValue(qc_checkstate_b,ccheckstatename,ccheckstate_bid,ccheckstate_bid)",
										"cdefectprocessid->getColValue(qc_checkstate_b,cdefectprocessid,ccheckstate_bid,ccheckstate_bid)",
										"cdefectprocessname->getColValue(qc_defectprocess,cdefectprocessname,cdefectprocessid,cdefectprocessid)",
										"bqualified->getColValue(qc_checkstate_b,bqualified,ccheckstate_bid,ccheckstate_bid)" });
				dealAfter(iRowCount, index);
			}
		}
	}

	private void dealAfter(int iRowCount, int index) {
		int nReturn = isCheckStateDuplicated();

		if (nReturn == 1) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0003840")/*
                                                                                                               * @res
                                                                                                               * "质量等级"
                                                                                                               */, "存货+入库批次号对应的质量等级不唯一!"/*-=notranslate=-*/);
		} else if (nReturn == 2) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC000-0003840")/*
                                                                                                               * @res
                                                                                                               * "质量等级"
                                                                                                               */, "存货+来源单据行ID对应的质量等级重复!"/*-=notranslate=-*/);
		}

		if (nReturn > 0) {
			for (int i = 0; i < iRowCount; i++) {
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, i, "ccheckstate_bid");
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(false, i, "bqualified");
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, i, "cdefectprocessid");
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, i, "cdefectprocessname");
			}
			return;
		}

		// 数量,计算合格数量,不合格数量和合格率
		calculateNum();
		// 对于合格品，默认可以入库，不合格不可入库
		if (new UFBoolean(getBillCardPanel().getBodyValueAt(0, "bqualified").toString()).booleanValue()) {
			for (int i = 0; i < iRowCount; i++) {
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(true, i, "bcheckin");
				getBillCardPanel().getBodyItem("table" + String.valueOf(index), "vmemo").setEnabled(false);
			}
		} else {
			for (int i = 0; i < iRowCount; i++) {
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(false, i, "bcheckin");
				getBillCardPanel().getBillModel("table" + String.valueOf(index)).setValueAt(null, i, "vmemo");
				getBillCardPanel().getBodyItem("table" + String.valueOf(index), "vmemo").setEnabled(true);
			}
		}

	}

	/**
   * 此处插入方法说明。 功能描述:文件管理 输入参数: 返回值: 异常处理: 日期:2003/10/31
   */
	private void onListDocument() {
		Integer nSelected[] = null;
		if (m_nUIState == 1) {
			Vector v = new Vector();
			int nRow = getBillListPanel().getHeadBillModel().getRowCount();
			for (int i = 0; i < nRow; i++) {
				int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
				if (nStatus == BillModel.SELECTED)
					v.addElement(new Integer(i));
			}
			nSelected = new Integer[v.size()];
			v.copyInto(nSelected);
		} else if (m_nUIState == 0 || m_nUIState == 4) {
			nSelected = new Integer[1];
			nSelected[0] = new Integer(m_nBillRecord);
		}

		if (nSelected == null || nSelected.length == 0)
			return;

		String sBillID[] = new String[nSelected.length];
		String sBillCode[] = new String[nSelected.length];

		for (int i = 0; i < sBillID.length; i++) {
			int j = nSelected[i].intValue();
			if (j >= 0 && j < m_checkBillVOs.length) {
				sBillID[i] = ((CheckbillHeaderVO) m_checkBillVOs[j].getParentVO()).getCcheckbillid();
				sBillCode[i] = ((CheckbillHeaderVO) m_checkBillVOs[j].getParentVO()).getVcheckbillcode();
			}
		}

		if (sBillID != null && sBillID.length > 0) {
			nc.ui.scm.file.DocumentManager.showDM(this, "WH", sBillID);
		}
	}

	/**
   * 此处插入方法说明。 功能描述:信息"增加" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoAdd() {
		if (!m_bBillEdit) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000081")/*
                                                                                                                     * @res
                                                                                                                     * "增加检验信息"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000082")/*
                                                                                         * @res
                                                                                         * "检验单处于非编辑状态，不可增加检验信息！"
                                                                                         */);
			return;
		}

		getBillCardPanelInfo().addNew();
		getBillCardPanelInfo().setEnabled(true);
		m_bInfoAdd = true;
		m_bInfoEdit = true;

		// 运用公式获得检验方式的属性
		UIRefPane o = (UIRefPane) getBillCardPanel().getHeadItem("ccheckmodeid").getComponent();
		String pk = o.getRefPK();

		FormulaParse f = new FormulaParse();
		String sExpress1 = "getColValue(qc_checkmode,bsample,ccheckmodeid,ccheckmodeid)";
		String sExpress2 = "getColValue(qc_checkmode,bratiosample,ccheckmodeid,ccheckmodeid)";
		String sExpress3 = "getColValue(qc_checkmode,nsamplenum,ccheckmodeid,ccheckmodeid)";
		String sExpress4 = "getColValue(qc_checkmode,nsamplevolume,ccheckmodeid,ccheckmodeid)";
		String sExpress5 = "getColValue(qc_checkmode,nsampleratio,ccheckmodeid,ccheckmodeid)";
		f.setExpressArray(new String[] { sExpress1, sExpress2, sExpress3, sExpress4, sExpress5 });
		VarryVO varrys[] = f.getVarryArray();

		Hashtable table = new Hashtable();
		table.put(varrys[0].getVarry()[0], new String[] { StringUtil.toString(pk) });

		f.setDataSArray(new Hashtable[] { table, table, table, table, table });
		String s[][] = f.getValueSArray();

		getBillCardPanel().getHeadItem("bsample").setValue(s[0][0]);
		getBillCardPanel().getHeadItem("bratiosample").setValue(s[1][0]);
		getBillCardPanel().getHeadItem("nsamplenum").setValue(s[2][0]);
		getBillCardPanel().getHeadItem("nsamplevolume").setValue(s[3][0]);
		getBillCardPanel().getHeadItem("nsampleratio").setValue(s[4][0]);

		// 设置检验数量
		UIRefPane refPane1 = (UIRefPane) getBillCardPanelInfo().getHeadItem("nnum").getComponent();// 检验数量
		UIRefPane refPane2 = (UIRefPane) getBillCardPanel().getHeadItem("nchecknum").getComponent();// 报检数量
		UFDouble nCheckNum = new UFDouble(0);
		String text = refPane2.getUITextField().getText();
		if (text != null && text.trim().length() > 0)
			nCheckNum = new UFDouble(text.trim());

		boolean bSample = false;
		boolean bRatioSample = false;
		UFDouble nSampleNum = new UFDouble(0);
		UFDouble nSampleVolume = new UFDouble(0);
		UFDouble nSampleRatio = new UFDouble(0);
		text = (String) getBillCardPanel().getHeadItem("bsample").getValueObject();
		if (text != null && text.trim().length() > 0)
			bSample = new UFBoolean(text.trim()).booleanValue();
		text = (String) getBillCardPanel().getHeadItem("bratiosample").getValueObject();
		if (text != null && text.trim().length() > 0)
			bRatioSample = new UFBoolean(text.trim()).booleanValue();
		text = (String) getBillCardPanel().getHeadItem("nsamplenum").getValueObject();
		if (text != null && text.trim().length() > 0)
			nSampleNum = new UFDouble(text.trim());
		// text = (String) getBillCardPanel().getHeadItem("nsamplevolume")
		// .getValueObject();
		text = s[3][0];// 2009/10/22 田锋涛 - 解决检验数量显示问题
		if (text != null && text.trim().length() > 0)
			nSampleVolume = new UFDouble(text.trim());
		text = (String) getBillCardPanel().getHeadItem("nsampleratio").getValueObject();
		if (text != null && text.trim().length() > 0) {
			// nSampleRatio = new UFDouble(text.trim());
			nSampleRatio = getSampleRatioByNum(nCheckNum, text.trim());
		}

		double d = 0;
		if (!bSample) {
			// 全检
			d = nCheckNum.doubleValue();
		} else {
			// 抽检
			if (bRatioSample) {
				// 按比例抽样
				d = nCheckNum.doubleValue() * nSampleRatio.doubleValue() * 0.01;
			} else {
				// 抽样
				d = nSampleVolume.doubleValue();
				// 校验样本*样本容量是否小于检验数量
				if (d * nSampleNum.doubleValue() > nCheckNum.doubleValue()) {
					String sMsg;
					sMsg = nc.ui.ml.NCLangRes.getInstance().getStrByID("C00104", "UPPC00104-000019")/*
                                                                                           * @res
                                                                                           * "要求记录检验信息,且按样本数记录检验信息,"
                                                                                           */;
					sMsg += nc.ui.ml.NCLangRes.getInstance().getStrByID("C00104", "UPPC00104-000020")/*
                                                                                             * @res
                                                                                             * "存货或报检存货+工序设置了检验方式,"
                                                                                             */;
					sMsg += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0pub", "UPPc0pub-000331")/*
                                                                                           * @res
                                                                                           * "但设置好的样本数：("
                                                                                           */
							+ CommonConstant.BEGIN_MARK + nSampleNum.doubleValue() + CommonConstant.END_MARK
							+ nc.ui.ml.NCLangRes.getInstance().getStrByID("c0pub", "UPPc0pub-000332")/*
                                                                                         * @res
                                                                                         * ")*样本容量：("
                                                                                         */;
					sMsg += CommonConstant.BEGIN_MARK + d + CommonConstant.END_MARK
							+ nc.ui.ml.NCLangRes.getInstance().getStrByID("c0pub", "UPPc0pub-000333")/*
                                                                                         * @res
                                                                                         * ")大于报检数量：("
                                                                                         */;
					sMsg += CommonConstant.BEGIN_MARK + nCheckNum + CommonConstant.END_MARK + ")!";
					this.showErrorMessage(sMsg);
					return;
				}
			}
		}
		refPane1.setValue(new UFDouble(d).toString());

		// 设置序号
		refPane2 = (UIRefPane) getBillCardPanelInfo().getHeadItem("vsamplecode").getComponent();
		int n = 1;
		if (m_grandVOs != null && m_grandVOs.length > 0) {
			CheckbillB2HeaderVO headVO = (CheckbillB2HeaderVO) m_grandVOs[m_grandVOs.length - 1].getParentVO();
			String nCode = headVO.getVsamplecode();
			if (nCode != null && nCode.trim().length() > 0) {
				Integer nn = new Integer(nCode);
				n = nn.intValue() + 1;
			} else {
				n = m_grandVOs.length + 1;
			}
		}
		refPane2.setValue(Integer.toString(n));

		// 根据检验标准自动生成检验项目及相关信息
		if (sCheckStandards != null && sCheckStandards.length > 0) {
			try {
				String[][] ccheckstandardid = new String[1][sCheckStandards.length];
				for (int k = 0; k < sCheckStandards.length; k++) {
					ccheckstandardid[0][k] = sCheckStandards[k];
				}
				ArrayList list = CheckbillHelper.getInfoByStandardBatchV55(ccheckstandardid);
				CheckbillB2ItemVO bodyVO[] = (CheckbillB2ItemVO[]) list.get(0);
				if (bodyVO != null && bodyVO.length > 0) {

					// add by yye begin 2005-03-12
					if (m_dialog.getCheckbilltypecode().trim().equals("A4")) {
						// 如果为完工报检，且有产出序号，则找到匹配的成分报检单，自动取对应itemid的值
						boolean lbol_compcheck = true;
						if (getBillCardPanel().getHeadItem("bcompcheck") != null
								&& getBillCardPanel().getHeadItem("bcompcheck").getValueObject() != null
								&& getBillCardPanel().getHeadItem("bcompcheck").getValueObject().toString().trim().length() > 0) {
							lbol_compcheck = new UFBoolean(getBillCardPanel().getHeadItem("bcompcheck").getValueObject().toString()
									.trim()).booleanValue();
						}

						String lstr_vprodserialnum = "";
						if (!lbol_compcheck && getBillCardPanel().getHeadItem("vprodserialnum") != null
								&& getBillCardPanel().getHeadItem("vprodserialnum").getValueObject() != null
								&& getBillCardPanel().getHeadItem("vprodserialnum").getValueObject().toString().trim().length() > 0) {
							lstr_vprodserialnum = getBillCardPanel().getHeadItem("vprodserialnum").getValueObject().toString().trim();
						}

						Hashtable lhtb_compcheckinfo = CheckbillHelper.getCompCheckInfoByProdserialnum(lstr_vprodserialnum);

						if (lhtb_compcheckinfo != null) {
							for (int i = 0; i < bodyVO.length; i++) {
								String lStr_checkitemid = bodyVO[i].getCcheckitemid();
								if (lStr_checkitemid != null && lStr_checkitemid.trim().length() > 0) {
									Object lobj_CompResult = lhtb_compcheckinfo.get(lStr_checkitemid);

									if (lobj_CompResult != null && lobj_CompResult.toString().trim().length() > 0) {
										bodyVO[i].setCresult(lobj_CompResult.toString().toString());
									}
								}
							}
						}
					}
					Vector vecData = new Vector();
					for (int i = 0; i < bodyVO.length; i++) {
						if (hPowerCheckitemID.size() <= 0 && strSubSqlPower == null) {
							vecData.add(bodyVO[i]);
						} else {
							if (hPowerCheckitemID.containsKey(bodyVO[i].getCcheckitemid())) {
								bodyVO[i].setBdefault(new UFBoolean(true));
								vecData.add(bodyVO[i]);
							}
						}
					}
					if (vecData.size() > 0) {
						bodyVO = new CheckbillB2ItemVO[vecData.size()];
						vecData.copyInto(bodyVO);

						if (s[0][0] != null && s[0][0].equals("Y")) {
							// 2009/10/22 田锋涛 对s[2][0]样本数进行处理，防止转换成int值的时候抛异常
							String sIntValue;
							if (s[2][0].indexOf(".") > 0)
								sIntValue = s[2][0].substring(0, s[2][0].indexOf("."));
							else
								sIntValue = s[2][0];
							CheckbillB2HeaderVO[] header = new CheckbillB2HeaderVO[s[2][0] == "" ? 1 : Integer.parseInt(sIntValue)];
							for (int i = 0; i < header.length; i++) {
								header[i] = new CheckbillB2HeaderVO();
								header[i].setNnum(s[2][0] == "" ? new UFDouble(d) : new UFDouble(Double.parseDouble(s[3][0])));
								header[i].setVsamplecode(String.valueOf(i + 1));
							}
							CheckbillB2VO[] vos = new CheckbillB2VO[header.length];
							for (int i = 0; i < vos.length; i++) {
								vos[i] = new CheckbillB2VO();
								vos[i].setParentVO(header[i]);
								CheckbillB2ItemVO[] cloneBody = (CheckbillB2ItemVO[]) ObjectUtils.serializableClone(bodyVO);
								for (int j = 0; j < cloneBody.length; j++) {
									cloneBody[j].setNnum(header[i].getNnum());
									cloneBody[j].setDr(0);
									cloneBody[j].setVsamplecode(header[i].getVsamplecode());
								}
								vos[i].setChildrenVO(cloneBody);
							}
							m_grandVOs = vos;
							getBillCardPanelInfo().getBillModel().setBodyDataVO(vos[0].getChildrenVO());
							getBillCardPanelInfo().getBillModel().execLoadFormula();
						} else {
							getBillCardPanelInfo().getBillModel().setBodyDataVO(bodyVO);
							getBillCardPanelInfo().getBillModel().execLoadFormula();
						}

						/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */
						BillModel infoBM = getBillCardPanelInfo().getBillModel();
						for (int i = 0, len = infoBM.getRowCount(); i < len; i++) {
							if (infoBM.getValueAt(i, "ccheckunitname") == null)
								infoBM.setValueAt(bodyVO[i].getCcheckunitid(), i, "ccheckunitname");
						}
						/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */

						getBillCardPanelInfo().updateValue();
						getBillCardPanelInfo().updateUI();
						for (int i = 0; i < bodyVO.length; i++)
							getBillCardPanelInfo().getBillModel().setRowState(i, BillModel.ADD);
					} else {
						MessageDialog.showHintDlg(this, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000013")/*提示*/, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000014")/*请检查检验项目的权限分配*/);
						getBillCardPanelInfo().getBillData().clearViewData();
						getBillCardPanelInfo().setEnabled(false);
						buttonManager.setButtonsStateCardInfo();
						return;
					}

				}
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000081")/*
                                                                                                                       * @res
                                                                                                                       * "增加检验信息"
                                                                                                                       */, e.getMessage());
			}
		}
		reBuildGroupColumn();

		// 设置部分项目不可编辑
		setInfoEditState();
		buttonManager.setButtonsStateCardInfoModify();

	}

	/**
   * 此处插入方法说明。 功能描述:信息"增行" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoAddLine() {
		getBillCardPanelInfo().addLine();
		// getInfoCardPanel().setEnabled(true);
		m_bInfoEdit = true;

		int nRow = getBillCardPanelInfo().getRowCount();

		BillItem bodyItem = getBillCardPanelInfo().getBodyItem("dcheckdate");
		if (bodyItem.isShow()) {
			UFDateTime dateTime = ClientEnvironment.getServerTime();
			getBillCardPanelInfo().setBodyValueAt(dateTime.toString(), nRow - 1, "dcheckdate");
		}

		// 设置是否达标默认为true
		bodyItem = getBillCardPanelInfo().getBodyItem("bmeet");
		if (bodyItem.isShow()) {
			// UFDateTime dateTime = getClientEnvironment().getServerTime();
			getBillCardPanelInfo().setBodyValueAt(new UFBoolean(true), nRow - 1, "bmeet");
		}

		// 新增的检验项目可编辑
		getBillCardPanelInfo().setCellEditable(nRow - 1, "ccheckitemname", true);

		// // 设置保存，行操作，取消，打印按钮为正常，其他为灰
		// for (int i = 0; i < 11; i++) {
		// if (i == 5 || i == 6)
		// m_hButtonState.put(new Integer(i),
		// new UFBoolean[] { new UFBoolean(true) });// 保存,取消
		// else if (i == 3 || i == 4)
		// m_hButtonState.put(new Integer(i),
		// new UFBoolean[] { new UFBoolean(true) });// 行操作
		// else
		// m_hButtonState.put(new Integer(i),
		// new UFBoolean[] { new UFBoolean(false) });
		// }
		//
		// changeButtonState();
	}

	/**
   * 此处插入方法说明。 功能描述:信息"取消" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoCancel() {
		// add by yye begin 2005-03-16
		// 如果是修订取消
		if (m_bInfoEmend) {
			m_bInfoEmend = false;
			getBillCardPanelInfo().setEnabled(false);

			getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
			getBillCardPanelInfo().getBillModel().execLoadFormula();
			getBillCardPanelInfo().updateValue();
			getBillCardPanelInfo().updateUI();

			displayCheckResultForInfoCard();// 自动判断检验结果并显示

			//
			buttonManager.setButtonsStateCardInfo();
			//
			return;

		}
		// add by yye end 2005-03-16

		if (m_bInfoAdd) {
			m_grandVOs = null;
		}
		// 如果不是修订，正常取消
		m_bInfoAdd = false;
		m_bInfoEdit = false;
		getBillCardPanelInfo().setEnabled(false);

		if (m_grandVOs == null || m_grandVOs.length == 0) {
			getBillCardPanelInfo().getBillData().clearViewData();
			getBillCardPanelInfo().updateValue();
			getBillCardPanelInfo().updateUI();

			// // 除增加外，其余为灰。
			// for (int i = 0; i < 11; i++) {
			// if (i == 0)
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(true) });
			// else
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(false) });
			// }

		} else {
			getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
			getBillCardPanelInfo().getBillModel().execLoadFormula();
			getBillCardPanelInfo().updateValue();
			getBillCardPanelInfo().updateUI();

			//
			refreshInfoUnitName();
			displayCheckResultForInfoCard();// 自动判断检验结果并显示

		}

		//
		buttonManager.setButtonsStateCardInfo();
		// changeButtonState();
	}

	/**
   * 此处插入方法说明。 功能描述:信息"删行" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoDelLine() {
		// 关键检验项目不可删除
		int nRow = getBillCardPanelInfo().getBillTable().getSelectedRow();
		if (nRow >= 0) {
			Object s = getBillCardPanelInfo().getBodyValueAt(nRow, "bcritical");
			Object pk = getBillCardPanelInfo().getBodyValueAt(nRow, "ccheckbill_b2id");
			if (s != null && s.toString().trim().length() > 0 && pk != null && pk.toString().trim().length() > 0) {
				UFBoolean b = new UFBoolean(s.toString());
				if (b.booleanValue()) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000013")/*
                                                                                                                 * @res
                                                                                                                 * "删行"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000083")/*
                                                                                             * @res
                                                                                             * "不能删除关键项目！"
                                                                                             */);
					return;
				}
			}
		}

		if (!getBillCardPanelInfo().delLine()) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000013")/*
                                                                                                             * @res
                                                                                                             * "删行"
                                                                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000067")/*
                                                                                         * @res
                                                                                         * "没有选中表体行！"
                                                                                         */);
			return;
		}

		getBillCardPanelInfo().setEnabled(true);

		if (!m_bInfoEmend) {
			m_bInfoEdit = true;

			// 设置部分项目不可编辑
			setInfoEditState();
		}

		// // 设置保存，行操作，取消按钮为正常，其他为灰
		// for (int i = 0; i < 11; i++) {
		// if (i == 5 || i == 6)
		// m_hButtonState.put(new Integer(i),
		// new UFBoolean[] { new UFBoolean(true) });// 保存,取消
		// else if (i == 3 || i == 4)
		// m_hButtonState.put(new Integer(i),
		// new UFBoolean[] { new UFBoolean(true) });// 行操作
		// else
		// m_hButtonState.put(new Integer(i),
		// new UFBoolean[] { new UFBoolean(false) });
		// }
		//
		// changeButtonState();
	}

	/**
   * 此处插入方法说明。 功能描述:信息"作废" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoDiscard() {
		if (!m_bBillEdit) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000084")/*
                                                                                                                     * @res
                                                                                                                     * "作废检验信息"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000085")/*
                                                                                         * @res
                                                                                         * "检验单处于浏览状态，不可作废检验信息！"
                                                                                         */);
			return;
		}

		if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
			// 如果缓存中无记录，不可作废检验信息，因为此时检验信息和检验单无关联
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000084")/*
                                                                                                                     * @res
                                                                                                                     * "作废检验信息"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000086")/*
                                                                                         * @res
                                                                                         * "检验信息和检验单无关联，不可作废检验信息！"
                                                                                         */);
			return;
		}

		if (MessageDialog.showYesNoDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000084")/*
                                                                                                                       * @res
                                                                                                                       * "作废检验信息"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH002")/*
                                                                         * @res
                                                                         * "确定要作废检验信息？"
                                                                         */) != MessageDialog.ID_YES)
			return;

		try {
			mbol_CancelFromDisCardInfo = true;
			m_grandVOs2 = (CheckbillB2VO[]) ObjectUtils.serializableClone(m_grandVOs);
		} catch (Exception e) {
			return;
		}

		// 从缓存中作废检验信息
		CheckbillB2ItemVO bodyVO[] = (CheckbillB2ItemVO[]) m_grandVOs[m_nInfoRecord].getChildrenVO();
		if (bodyVO != null && bodyVO.length > 0) {
			for (int i = 0; i < bodyVO.length; i++)
				bodyVO[i].setEditStatus(3);
		}

		this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH006")/*
                                                                                         * @res
                                                                                         * "作废检验信息成功！"
                                                                                         */);
		m_bInfoDel = true;

		// 作废成功，从缓存清除相应的记录
		Vector vTemp = new Vector();
		for (int i = 0; i < m_grandVOs.length; i++) {
			if (i != m_nInfoRecord) {
				vTemp.addElement(m_grandVOs[i]);
			}
		}

		// 更改缓存记录指针
		if (m_grandVOs.length == 1) {
			// 缓存仅一条记录
			m_nInfoRecord = 0;
		} else {
			// 缓存多条记录
			if (m_nInfoRecord == m_grandVOs.length - 1)
				m_nInfoRecord = 0;
		}

		if (vTemp.size() > 0) {
			// 缓存尚有记录
			m_grandVOs = new CheckbillB2VO[vTemp.size()];
			vTemp.copyInto(m_grandVOs);

			// 刷新界面
			getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
			getBillCardPanelInfo().getBillModel().execLoadFormula();
			getBillCardPanelInfo().updateValue();
			getBillCardPanelInfo().updateUI();

			displayCheckResultForInfoCard();// 自动判断检验结果并显示

			// // 设置浏览键的状态
			// if (m_grandVOs.length == 1) {
			// // 四个按钮均为灰
			// for (int i = 7; i <= 10; i++)
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(false) });
			// } else if (m_nInfoRecord == 0) {
			// // 首张、上张为灰
			// for (int i = 7; i <= 8; i++)
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(false) });
			// // 下张、末张正常
			// for (int i = 9; i <= 10; i++)
			// m_hButtonState.put(new Integer(i),
			// new UFBoolean[] { new UFBoolean(true) });
			// }

		} else {
			// 缓存无记录
			m_grandVOs = null;

			getBillCardPanelInfo().getBillData().clearViewData();
			getBillCardPanelInfo().updateValue();
			getBillCardPanelInfo().updateUI();

		}

		//
		buttonManager.setButtonsStateCardInfo();
		//
		// changeButtonState();
	}

	/**
   * 此处插入方法说明。 功能描述:信息"首张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoFirst() {
		m_nInfoRecord = 0;

		// 刷新界面
		getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
		getBillCardPanelInfo().getBillModel().execLoadFormula();
		setUnit();
		getBillCardPanelInfo().updateValue();
		getBillCardPanelInfo().updateUI();

		displayCheckResultForInfoCard();// 自动判断检验结果并显示

		//
		buttonManager.setButtonsStateCardInfo();
		//
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:信息"修订" 输入参数: 返回值: 异常处理: 日期: 2005-03-16 袁野
   */
	private void onInfoEmend() {

		getBillCardPanelInfo().setEnabled(true);
		m_bInfoEmend = true;

		// 设置部分项目不可编辑
		setInfoEmendState();

		buttonManager.setButtonsStateCardInfoModify();

		// 修改时，控制标准值不可编辑
		if (m_grandVOs != null && m_grandVOs.length > 0) {
			CheckbillB2ItemVO tempVOs[] = (CheckbillB2ItemVO[]) m_grandVOs[m_nInfoRecord].getChildrenVO();
			if (tempVOs != null && tempVOs.length > 0) {
				for (int i = 0; i < tempVOs.length; i++) {
					Object oTemp = getBillCardPanelInfo().getBodyValueAt(i, "cstandardvalue");
					if (oTemp != null && oTemp.toString().trim().length() > 0)
						tempVOs[i].setCstandardvalue(oTemp.toString());
				}
			}
		}
	}

	/**
   * 此处插入方法说明。 功能描述: 联查主料质量 输入参数: 返回值: 异常处理: 日期: 2005-03-16 袁野
   */
	private void onCardMainMaterialQuery() {

		try {
			//
			CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();

			if (headVO.getCbaseid() == null || headVO.getCbaseid().trim().length() == 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000089")/*
                                                                                                                       * @res
                                                                                                                       * "主料联查"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000090")/*
                                                                                           * @res
                                                                                           * "该质检单上没有存货信息不全，无法进行本操作!"
                                                                                           */);
				return;
			}

			if (headVO.getCmangid() == null || headVO.getCmangid().trim().length() == 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000089")/*
                                                                                                                       * @res
                                                                                                                       * "主料联查"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000090")/*
                                                                                           * @res
                                                                                           * "该质检单上没有存货信息不全，无法进行本操作!"
                                                                                           */);
				return;
			}

			if (headVO.getVinvbatchcode() == null || headVO.getVinvbatchcode().trim().length() == 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000089")/*
                                                                                                                       * @res
                                                                                                                       * "主料联查"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000091")/*
                                                                                           * @res
                                                                                           * "该质检单上存货批次信息不全，无法进行本操作!"
                                                                                           */);
				return;
			}

			m_checkBillVOsForMMQuery = CheckbillHelper.queryCheckbillsForMainMaterialQuery(m_checkBillVOs[m_nBillRecord]);

			if (m_checkBillVOsForMMQuery == null || m_checkBillVOsForMMQuery.length == 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000089")/*
                                                                                                                       * @res
                                                                                                                       * "主料联查"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000092")/*
                                                                                           * @res
                                                                                           * "没有符合条件的主料信息，无法进行本操作!"
                                                                                           */);
				return;
			}

			m_tabbedPane.setVisible(false);
			m_bMMQuery = true;
			getTabbedPaneForMMQuery().setVisible(true);
			getTabbedPaneForMMQuery().setSelectedIndex(0);
			this.setButtons(buttonManager.m_btnListMMQuery);
			// updateButtons();
			buttonManager.setButtonsStateListMMQuery();
			// 设置按钮
			// this.setButtons(m_btnList);

			// 隐藏pk_corp,cpraycorp,cchecktypeid，cpraydeptid，cvendormangid，ccheckstateid,ccheckmodeid,ccheckdeptid
			// cprayerid,creporterid,cauditpsn,ccheckstandardid,cworkshopid,cproducerid
			// cstoreorganization,cpurorganization,cprojectid,cemployeeid,cwarehouseid
			getBillListPanelMMQuery().hideHeadTableCol("pk_corp");
			getBillListPanelMMQuery().hideHeadTableCol("cpraycorp");
			getBillListPanelMMQuery().hideHeadTableCol("cchecktypeid");
			getBillListPanelMMQuery().hideHeadTableCol("cpraydeptid");
			getBillListPanelMMQuery().hideHeadTableCol("cvendormangid");
			getBillListPanelMMQuery().hideHeadTableCol("ccheckstateid");
			getBillListPanelMMQuery().hideHeadTableCol("ccheckmodeid");
			getBillListPanelMMQuery().hideHeadTableCol("ccheckstandardid");
			getBillListPanelMMQuery().hideHeadTableCol("ccheckdeptid");
			getBillListPanelMMQuery().hideHeadTableCol("cprayerid");
			getBillListPanelMMQuery().hideHeadTableCol("creporterid");
			getBillListPanelMMQuery().hideHeadTableCol("cauditpsn");
			getBillListPanelMMQuery().hideHeadTableCol("cworkshopid");
			getBillListPanelMMQuery().hideHeadTableCol("cproducerid");
			getBillListPanelMMQuery().hideHeadTableCol("cstoreorganization");
			getBillListPanelMMQuery().hideHeadTableCol("cpurorganization");
			getBillListPanelMMQuery().hideHeadTableCol("cprojectid");
			getBillListPanelMMQuery().hideHeadTableCol("cemployeeid");
			getBillListPanelMMQuery().hideHeadTableCol("cwarehouseid");
			getBillListPanelMMQuery().hideHeadTableCol("cassistunitid");

			// 加载页签
			setLayout(new java.awt.BorderLayout());
			add(getTabbedPaneForMMQuery(), java.awt.BorderLayout.CENTER);

			// add by yy begin取得单据行号信息
			Vector lvec_SourceBillTypeCode = new Vector();
			Vector lvec_SourceBillHID = new Vector();
			Vector lvec_SourceBillBID = new Vector();

			CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOsForMMQuery[0].getChildrenVO();
			if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
				for (int j = 0; j < l_CheckbillItemVO.length; j++) {
					if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null && l_CheckbillItemVO[j].getCsourcebillid() != null
							&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
						lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
						lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
						lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
					}
				}
			}

			String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
			String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
			String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

			lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
			lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
			lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

			SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
			m_HTSourcebillInfoForMMQuery = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode,
					lstrary_SourceBillHID, lstrary_SourceBillBID);
			// add by yy end

			m_bBodyQueryForMMQuery = new UFBoolean[m_checkBillVOsForMMQuery.length];

			m_grandVOsForMMQuery = (CheckbillB2VO[]) m_checkBillVOsForMMQuery[0].getGrandVO();

			for (int i = 0; i < m_bBodyQueryForMMQuery.length; i++) {
				m_bBodyQueryForMMQuery[i] = new UFBoolean(true);
			}

			//
			// 刷新界面
			Vector vTemp = new Vector();
			for (int i = 0; i < m_checkBillVOsForMMQuery.length; i++)
				vTemp.addElement(m_checkBillVOsForMMQuery[i].getParentVO());
			CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp.size()];
			vTemp.copyInto(headVOs);
			// 加载自由项
			nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
			freeVOParse.setFreeVO(headVOs, "vfreeitem", "vfree", null, "cmangid", false);
			//
			CheckbillItemVO bodyVOs[] = (CheckbillItemVO[]) m_checkBillVOsForMMQuery[0].getChildrenVO();

			m_nBillRecordForMMQuery = 0;

			getBillListPanelMMQuery().getHeadBillModel().setBodyDataVO(headVOs);
			getBillListPanelMMQuery().getHeadBillModel().execLoadFormula();
			getBillListPanelMMQuery().getHeadBillModel().updateValue();

			getBillListPanelMMQuery().getBodyBillModel().setBodyDataVO(bodyVOs);
			getBillListPanelMMQuery().getBodyBillModel().execLoadFormula();
			getBillListPanelMMQuery().getBodyBillModel().updateValue();

			getBillListPanelMMQuery().updateUI();

			// //计算合格数量等(改为全部从后台处理)
			// for (int i = 0; i <
			// getBillListPanelForMMQuery().getHeadBillModel().getRowCount();
			// i++) {
			// calculateNumForMMQuery(i);
			// }

			// add by yy begin
			setListSourceBillInfo();

			// add by yy end

		} catch (Exception e) {
			m_tabbedPane.setVisible(true);
			getTabbedPaneForMMQuery().setVisible(false);
			m_bMMQuery = false;
			// this.setButtons(m_btnCardDisplay);
			this.setButtons(buttonManager.getBtnTree().getButtonArray());
			updateButtons();
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000089")/*
                                                                                                                     * @res
                                                                                                                     * "主料联查"
                                                                                                                     */, e.getMessage());
			return;
		}

	}

	/**
   * 此处插入方法说明。 功能描述:信息"末张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoLast() {
		m_nInfoRecord = m_grandVOs.length - 1;

		// 刷新界面
		getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
		getBillCardPanelInfo().getBillModel().execLoadFormula();
		setUnit();
		getBillCardPanelInfo().updateValue();
		getBillCardPanelInfo().updateUI();

		displayCheckResultForInfoCard();// 自动判断检验结果并显示

		buttonManager.setButtonsStateCardInfo();
		//
		return;
	}
	
	// lxt 2013-07 gzcg
	private void onInfoAddSample() {
		if (!m_bBillEdit) {
			MessageDialog.showHintDlg(this, "增加样本", "检验单处于浏览状态，不可增加样本信息！");
			return;
		}
		CheckbillB2VO lastGrandVo = m_grandVOs[m_grandVOs.length - 1];
		CheckbillB2VO newGrandVo = new CheckbillB2VO();
		Vector<CheckbillB2ItemVO> lvec_AllBodyVO = new Vector<CheckbillB2ItemVO>();
		Date now = new Date();
		String samplecode = new UFDate(now).toString();
		samplecode += String.valueOf(now.getHours()*60+now.getMinutes());
		
		for (int i = 0; i < lastGrandVo.getChildrenVO().length; i++) {
			CheckbillB2ItemVO tempVO = new CheckbillB2ItemVO();
			for (String attr : ((CheckbillB2ItemVO)lastGrandVo.getChildrenVO()[i]).getAttributeNames())
				tempVO.setAttributeValue(attr, ((CheckbillB2ItemVO)lastGrandVo.getChildrenVO()[i]).getAttributeValue(attr));
			tempVO.setCcheckbill_b2id(null);
			tempVO.setVsamplecode(samplecode);
			tempVO.setCresult(null);
			tempVO.setTs(null);
			lvec_AllBodyVO.addElement(tempVO);
		}

		CheckbillB2ItemVO[] lary_AllBodyVOs = new CheckbillB2ItemVO[lvec_AllBodyVO.size()];
		//CheckbillB2ItemVO[] bodyVOs= null;
		if (lvec_AllBodyVO.size() > 0) {
			lvec_AllBodyVO.copyInto(lary_AllBodyVOs);
			try {
				//bodyVOs = CheckbillHelper.doSaveForInfoEmend(lary_AllBodyVOs);
				// 检验信息保存到缓存中
				CheckbillB2HeaderVO headVO = new CheckbillB2HeaderVO();
				for (String attr : ((CheckbillB2HeaderVO)lastGrandVo.getParentVO()).getAttributeNames())
					headVO.setAttributeValue(attr, ((CheckbillB2HeaderVO)lastGrandVo.getParentVO()).getAttributeValue(attr));

				headVO.setVsamplecode(samplecode);
				newGrandVo.setParentVO(headVO);
				newGrandVo.setChildrenVO(lary_AllBodyVOs);
				
				Vector<CheckbillB2VO> lvec_AllVO = new Vector<CheckbillB2VO>();
				for(int i=0;i<m_grandVOs.length;i++) lvec_AllVO.add(m_grandVOs[i]);
				lvec_AllVO.add(newGrandVo);
				
				m_grandVOs = lvec_AllVO.toArray(new CheckbillB2VO[]{});
				
				onInfoLast();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	// lxt 2013-07 gzcg
	
	/**
   * 此处插入方法说明。 功能描述:信息"修改" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoModify() {
		if (!m_bBillEdit) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000093")/*
                                                                                                                     * @res
                                                                                                                     * "修改检验信息"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000094")/*
                                                                                         * @res
                                                                                         * "检验单处于非编辑状态，不可修改检验信息！"
                                                                                         */);
			return;
		}

		getBillCardPanelInfo().setEnabled(true);
		String strType = getBillCardPanelInfo().getBodyItem("icheckstandard").getValue();
		if (strType.equals("0")) {

			UIRefPane ref = (UIRefPane) getBillCardPanelInfo().getBodyItem("cresult").getComponent();
			getBillCardPanelInfo().getBodyItem("cresult").setDecimalDigits(m_numDecimal);
			ref.setTextType(UITextType.TextDbl);
			ref.setMaxLength(20);
			Double d = new Double(99999999);
			if (d != null)
				ref.setMaxValue(d.doubleValue());
			d = 0.0;
			if (d != null)
				ref.setMinValue(d.doubleValue());
			ref.setNumPoint(m_numDecimal);

		}
		m_bInfoEdit = true;

		// 设置部分项目不可编辑
		setInfoEditState();

		buttonManager.setButtonsStateCardInfoModify();

		// 修改时，控制标准值不可编辑
		if (m_grandVOs != null && m_grandVOs.length > 0) {
			CheckbillB2ItemVO tempVOs[] = (CheckbillB2ItemVO[]) m_grandVOs[m_nInfoRecord].getChildrenVO();
			if (tempVOs != null && tempVOs.length > 0) {
				for (int i = 0; i < tempVOs.length; i++) {
					Object oTemp = getBillCardPanelInfo().getBodyValueAt(i, "cstandardvalue");
					if (oTemp != null && oTemp.toString().trim().length() > 0)
						tempVOs[i].setCstandardvalue(oTemp.toString());
				}
			}
		}
	}

	/**
   * 此处插入方法说明。 功能描述:信息"下张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoNext() {
		m_nInfoRecord++;
		if (m_nInfoRecord > m_grandVOs.length - 1)
			m_nInfoRecord = m_grandVOs.length - 1;

		// 刷新界面
		getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
		getBillCardPanelInfo().getBillModel().execLoadFormula();
		setUnit();
		getBillCardPanelInfo().updateValue();
		getBillCardPanelInfo().updateUI();

		displayCheckResultForInfoCard();// 自动判断检验结果并显示

		buttonManager.setButtonsStateCardInfo();
		//
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:信息"上张" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoPrevious() {
		m_nInfoRecord--;
		if (m_nInfoRecord < 0)
			m_nInfoRecord = 0;

		// 刷新界面
		getBillCardPanelInfo().setBillValueVO(m_grandVOs[m_nInfoRecord]);
		getBillCardPanelInfo().getBillModel().execLoadFormula();
		setUnit();
		getBillCardPanelInfo().updateValue();
		getBillCardPanelInfo().updateUI();

		displayCheckResultForInfoCard();// 自动判断检验结果并显示

		//
		buttonManager.setButtonsStateCardInfo();
		//
		return;
	}

	private void setUnit() {
		for (int i = 0; i < m_grandVOs[m_nInfoRecord].getChildrenVO().length; i++) {
			getBillCardPanelInfo().getBillModel().setValueAt(
					((CheckbillB2ItemVO) m_grandVOs[m_nInfoRecord].getChildrenVO()[i]).getCcheckunitid(), i, "ccheckunitname");
		}
	}

	/**
   * 此处插入方法说明。 功能描述:信息"保存" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onInfoSave() {

		// 增加对校验公式的支持,错误显示由UAP处理
		if (!getBillCardPanelInfo().getBillData().execValidateFormulas()) {
			return;
		}

		// 获得界面数据
		int nRow = getBillCardPanelInfo().getRowCount();
		CheckbillB2VO grandVO = new CheckbillB2VO(nRow);
		getBillCardPanelInfo().getBillValueVO(grandVO);

		// 保存前检查
		if (nRow == 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000095")/*
                                                                                                                     * @res
                                                                                                                     * "检验信息保存"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000075")/*
                                                                                         * @res
                                                                                         * "行不能为空!"
                                                                                         */);
			return;
		}

		// 设置行编辑状态
		CheckbillB2ItemVO bodyVOs[] = (CheckbillB2ItemVO[]) grandVO.getChildrenVO();
		CheckbillB2HeaderVO headVO = (CheckbillB2HeaderVO) grandVO.getParentVO();
		for (int i = 0; i < bodyVOs.length; i++) {
			String sTemp = bodyVOs[i].getCcheckbill_b2id();
			if (sTemp != null && sTemp.trim().length() > 0) {
				// 修改或删除
				int nStatus = getBillCardPanelInfo().getBillModel().getRowState(i);
				if (nStatus != 3 && (!m_bInfoAdd) && m_bInfoEdit)
					bodyVOs[i].setEditStatus(2);// 修改
				else
					bodyVOs[i].setEditStatus(3);// 删除
			} else {
				// 增加
				bodyVOs[i].setEditStatus(1);
			}
			bodyVOs[i].setVsamplecode(headVO.getVsamplecode());
			bodyVOs[i].setNnum(headVO.getNnum());
			bodyVOs[i].setPk_corp(m_sUnitCode);
			bodyVOs[i].setDr(new Integer(0));
		}

		// 删除的检验信息
		Vector vDelData = getBillCardPanelInfo().getBillModel().getDeleteRow();
		// 2010-09-06 tianft 得到模板上的位置，避免vTemp.elementAt(0)这样的形式
		int tsPos = getBillCardPanelInfo().getBodyColByKey("ts");
		int ccheckbill_b2idPos = getBillCardPanelInfo().getBodyColByKey("ccheckbill_b2id");
		int ccheckbillidPos = getBillCardPanelInfo().getBodyColByKey("ccheckbillid");
		if (vDelData != null && vDelData.size() > 0) {
			for (int i = 0; i < vDelData.size(); i++) {
				Vector vTemp = (Vector) vDelData.elementAt(i);
				CheckbillB2ItemVO tempVO = new CheckbillB2ItemVO();
				tempVO.setCcheckbill_b2id((String) vTemp.elementAt(ccheckbill_b2idPos));
				tempVO.setCcheckbillid((String) vTemp.elementAt(ccheckbillidPos));
				tempVO.setTs((String) vTemp.elementAt(tsPos));
				tempVO.setEditStatus(3); // 删除状态
				tempVO.setVsamplecode(headVO.getVsamplecode());

				if (m_hDelInfo.get(tempVO.getCcheckbillid()) != null) {
					CheckbillB2ItemVO tempVOs[] = (CheckbillB2ItemVO[]) m_hDelInfo.get(tempVO.getCcheckbillid());
					Vector vvTemp = new Vector();
					for (int j = 0; j < tempVOs.length; j++)
						vvTemp.addElement(tempVOs[j]);
					vvTemp.addElement(tempVO);
					tempVOs = new CheckbillB2ItemVO[vvTemp.size()];
					vvTemp.copyInto(tempVOs);
					m_hDelInfo.put(tempVO.getCcheckbillid(), tempVOs);
				} else {
					m_hDelInfo.put(tempVO.getCcheckbillid(), new CheckbillB2ItemVO[] { tempVO });
				}
			}
		}

		// 保存前检查
		int nError = -1;
		try {
			headVO.validate();
			for (nError = 0; nError < bodyVOs.length; nError++)
				bodyVOs[nError].validate();
		} catch (Exception e) {
			SCMEnv.out(e);
			String sMsg = "";
			if (nError < 0) {
				sMsg += e.getMessage();
			} else {
				sMsg += e.getMessage() + nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000122")/*
                                                                                                               * @res
                                                                                                               * "出现问题的信息行为："
                                                                                                               */
						+ (nError + 1);
			}
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000095")/*
                                                                                                                     * @res
                                                                                                                     * "检验信息保存"
                                                                                                                     */, sMsg);
			return;
		}

		// add by yye begin 2005-03-16
		try {

			if (m_bInfoEmend) {

				Vector lvec_AllBodyVO = new Vector();
				String lStr_Checkbillid = (String) getBillCardPanel().getHeadItem("ccheckbillid").getValueObject();
				for (int i = 0; i < bodyVOs.length; i++) {
					String sTemp = bodyVOs[i].getCcheckbill_b2id();
					if (sTemp != null && sTemp.trim().length() > 0) {
						// 修改或删除
						int nStatus = getBillCardPanelInfo().getBillModel().getRowState(i);
						if (nStatus == 3) {
							bodyVOs[i].setEditStatus(3);// 删除
						} else {
							bodyVOs[i].setEditStatus(2);// 修改
						}
					} else {
						// 增加
						bodyVOs[i].setCcheckbillid(lStr_Checkbillid);// 插入前设置主键
						bodyVOs[i].setEditStatus(1);

					}

					lvec_AllBodyVO.addElement(bodyVOs[i]);

				}

				if (vDelData != null && vDelData.size() > 0) {
					for (int i = 0; i < vDelData.size(); i++) {
						Vector vTemp = (Vector) vDelData.elementAt(i);
						CheckbillB2ItemVO tempVO = new CheckbillB2ItemVO();
						tempVO.setCcheckbill_b2id((String) vTemp.elementAt(ccheckbill_b2idPos));
						tempVO.setCcheckbillid((String) vTemp.elementAt(ccheckbillidPos));
						tempVO.setTs((String) vTemp.elementAt(tsPos));
						tempVO.setEditStatus(3); // 删除状态
						tempVO.setVsamplecode(headVO.getVsamplecode());
						lvec_AllBodyVO.addElement(tempVO);
					}
				}

				CheckbillB2ItemVO[] lary_AllBodyVOs = new CheckbillB2ItemVO[lvec_AllBodyVO.size()];

				if (lvec_AllBodyVO.size() > 0) {
					lvec_AllBodyVO.copyInto(lary_AllBodyVOs);
					bodyVOs = CheckbillHelper.doSaveForInfoEmend(lary_AllBodyVOs);
				}

				// 检验信息保存到缓存中
				grandVO.setChildrenVO(bodyVOs);
				m_grandVOs[m_nInfoRecord] = grandVO;
				m_checkBillVOs[m_nBillRecord].setGrandVO(m_grandVOs);

				m_bInfoEmend = false;
				// 2010-09-06 tianft 编辑态置为false，解决修订时“检验信息页签”已经保存了，转到“检验单”页签还提示保存的问题
				m_bInfoEdit = false;

				getBillCardPanelInfo().setEnabled(false);
				getBillCardPanelInfo().setBillValueVO(grandVO);
				getBillCardPanelInfo().getBillModel().execLoadFormula();
				getBillCardPanelInfo().updateValue();
				getBillCardPanelInfo().updateUI();

				// displayCheckResultForInfoCard();// 界面上已经判断，无需保存后无需再自动判断检验结果并显示

				this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH005")/*
                                                                                             * @res
                                                                                             * "检验信息保存成功！"
                                                                                             */);

				//
				buttonManager.setButtonsStateCardInfo();
				//
				return;
			}

		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000001")/*
                                                                                                               * @res
                                                                                                               * "保存"
                                                                                                               */, e.getMessage());
			return;
		}

		// add by yye begin 2005-03-16

		// 检验信息的样本号重复性检验
		if (m_grandVOs != null && m_grandVOs.length > 0) {
			String sMsg = "";
			int j = 0;
			String s1 = ((CheckbillB2HeaderVO) grandVO.getParentVO()).getVsamplecode().trim();
			for (int i = 1; i < m_grandVOs.length; i++) {
				String s2 = ((CheckbillB2HeaderVO) m_grandVOs[i].getParentVO()).getVsamplecode().trim();
				if (s1.equals(s2))
					j++;
			}
			if (j > 1)
				sMsg += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000123")/*
                                                                                               * @res
                                                                                               * "样本号重复："
                                                                                               */
						+ s1 + "\n";
			else if (j == 1 && m_bInfoAdd)
				sMsg += nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000123")/*
                                                                                               * @res
                                                                                               * "样本号重复："
                                                                                               */
						+ s1 + "\n";

			if (sMsg.length() > 0) {
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000095")/*
                                                                                                                       * @res
                                                                                                                       * "检验信息保存"
                                                                                                                       */, sMsg);
				return;
			}
		}

		// 保存
		// 检验信息保存到缓存中
		if (m_bInfoAdd) {
			// 增加信息
			if (m_grandVOs != null && m_grandVOs.length > 0) {
				if (((CheckbillB2ItemVO) m_grandVOs[0].getChildrenVO()[0]).getCcheckbill_b2id() != null) {
					// 缓存中已存在信息
					Vector vTemp = new Vector();
					for (int i = 0; i < m_grandVOs.length; i++)
						vTemp.addElement(m_grandVOs[i]);
					// vTemp.addElement(grandVO);

					m_grandVOs = new CheckbillB2VO[vTemp.size()];
					vTemp.copyInto(m_grandVOs);

					m_nInfoRecord = 0;

				} else {
					m_grandVOs[m_nInfoRecord] = grandVO;
				}
			} else {
				// 缓存中不存在信息
				m_grandVOs = new CheckbillB2VO[1];
				m_grandVOs[0] = grandVO;
				m_nInfoRecord = 0;
			}
		} else {
			// 修改信息
			// 缓存中已存在信息
			m_grandVOs[m_nInfoRecord] = grandVO;
		}

		// 改变编辑状态
		m_bInfoAdd = false;
		m_bInfoEdit = false;
		m_bInfoEmend = false;
		getBillCardPanelInfo().setEnabled(false);
		getBillCardPanelInfo().setBillValueVO(grandVO);
		getBillCardPanelInfo().getBillModel().execLoadFormula();
		getBillCardPanelInfo().updateValue();
		getBillCardPanelInfo().updateUI();

		/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */
		BillModel bodyBM = getBillCardPanelInfo().getBillModel();
		if (grandVO != null) {
			CheckbillB2ItemVO[] cb2bVOs = (CheckbillB2ItemVO[]) grandVO.getChildrenVO();
			for (int i = 0, len = cb2bVOs.length; i < len; i++)
				if (bodyBM.getValueAt(i, "ccheckunitname") == null)
					bodyBM.setValueAt(cb2bVOs[i].getCcheckunitid(), i, "ccheckunitname");
		}
		/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */

		displayCheckResultForInfoCard();// 自动判断检验结果并显示

		this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000096")/*
                                                                                                       * @res
                                                                                                       * "检验信息保存成功！"
                                                                                                       */);

		//
		buttonManager.setButtonsStateCardInfo();
		//
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:列表"作废" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListDiscard() {
		if (MessageDialog.showYesNoDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000039")/*
                                                                                                                 * @res
                                                                                                                 * "作废"
                                                                                                                 */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH002")/*
                                                                         * @res
                                                                         * "确定要作废检验单？"
                                                                         */) != MessageDialog.ID_YES)
			return;
		//
		Vector v1 = new Vector();
		Vector v2 = new Vector();
		for (int i = 0; i < m_checkBillVOs.length; i++) {
			int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
			if (!m_bBodyQuery[i].booleanValue() && nStatus == BillModel.SELECTED) {
				CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
				v1.addElement(headVO.getCcheckbillid());
				v2.addElement(headVO.getTs());
			}
		}
		String headKey[] = new String[v1.size()];
		String ts[] = new String[v2.size()];
		v1.copyInto(headKey);
		v2.copyInto(ts);
		if (headKey != null && headKey.length > 0) {
			try {
				ArrayList list = CheckbillHelper.queryCheckbillItemAndInfoBatch(headKey, ts);
				if (list == null || list.size() == 0)
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                     * @res
                                                                                                                     * "并发操作，请刷新！"
                                                                                                                     */);
				int j = 0;
				for (int i = 0; i < m_checkBillVOs.length; i++) {
					int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
					if (!m_bBodyQuery[i].booleanValue() && nStatus == BillModel.SELECTED) {
						CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
						ArrayList list0 = (ArrayList) list.get(j);
						if (list0 == null || list0.size() == 0)
							throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作，请刷新！"
                                                                                                                         */);
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list0.get(0);
						CheckbillB2VO grandVO[] = null;
						// String sTemp[] = null;
						// Object oTemp = list0.get(2);
						// if(oTemp != null) sTemp = (String[]) oTemp;
						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());

							// 设置自由项
							headVO.setVfree1(bodyVO[0].getVfree1());
							headVO.setVfree2(bodyVO[0].getVfree2());
							headVO.setVfree3(bodyVO[0].getVfree3());
							headVO.setVfree4(bodyVO[0].getVfree4());
							headVO.setVfree5(bodyVO[0].getVfree5());
							// if(sTemp != null && sTemp.length > 0)
							// headVO.setVfreeitem(sTemp[j]);
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
						}

						Object o = list0.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
						}

						m_checkBillVOs[i].setChildrenVO(bodyVO);
						m_checkBillVOs[i].setGrandVO(grandVO);

						m_bBodyQuery[i] = new UFBoolean(true);

						// 计算合格数量等
						calculateNum();
						j++;
					}
				}
			} catch (BusinessException e) {
				SCMEnv.out(e);
				MessageDialog.showHintDlg(this, "", e.getMessage());
				return;
			} catch (Exception e) {
				SCMEnv.out(e);
				return;
			}
		}
		//

		// 获得选择的记录
		Vector vTemp1 = new Vector();// 选择的记录
		Vector vTemp2 = new Vector();// 未选择的记录
		Vector vTemp3 = new Vector();
		int nRow = getBillListPanel().getHeadBillModel().getRowCount();
		for (int i = 0; i < nRow; i++) {
			int nStatus = getBillListPanel().getHeadBillModel().getRowState(i);
			if (nStatus == BillModel.SELECTED) {
				vTemp1.addElement(m_checkBillVOs[i]);
			} else {
				vTemp2.addElement(m_checkBillVOs[i]);
				vTemp3.addElement(m_bBodyQuery[i]);
			}
		}

		if (vTemp1.size() == 0) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000039")/*
                                                                                                               * @res
                                                                                                               * "作废"
                                                                                                               */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000033")/*
                                                                                         * @res
                                                                                         * "未选中单据！"
                                                                                         */);
			return;
		}

		// 作废
		try {
			CheckbillVO tempVOs[] = new CheckbillVO[vTemp1.size()];
			vTemp1.copyInto(tempVOs);
			CheckbillHelper.discard(tempVOs, getClientEnvironment().getUser().getPrimaryKey());
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000005")/*
                                                                                                               * @res
                                                                                                               * "作废"
                                                                                                               */, e.getMessage());
			return;
		}

		this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH006")/*
                                                                                         * @res
                                                                                         * "作废成功！"
                                                                                         */);

		// 作废成功，从缓存清除相应的记录
		if (vTemp2.size() > 0) {
			// 缓存中尚有记录
			m_checkBillVOs = new CheckbillVO[vTemp2.size()];
			vTemp2.copyInto(m_checkBillVOs);
			m_bBodyQuery = new UFBoolean[vTemp3.size()];
			vTemp3.copyInto(m_bBodyQuery);

			m_nBillRecord = 0;

			// 若表体未查询，则查询表体
			if (!m_bBodyQuery[m_nBillRecord].booleanValue()) {
				try {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs());
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);

					if (list != null && list.size() > 0) {
						// 有返回结果
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
						CheckbillB2VO grandVO[] = null;
						Object o = list.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
							m_grandVOs = grandVO;
						} else {
							m_grandVOs = null;
						}

						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, null, "cmangid", false);
						}

						m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
						m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);

						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);

						// 计算合格数量等
						calculateNum();

					} else {
						// 无返回结果
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000015")/*
                                       * @res "查询表体"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                               * @res
                                                                                               * "表体不存在！"
                                                                                               */);
						return;
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作"
                                                                                                                         */, e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/* @res "查询表体" */, e.getMessage());
					return;
				}
			} else {
				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();
			}
			HashMap hStandard = new HashMap();
			Vector vecBody = new Vector();
			// 刷新界面
			vTemp1 = new Vector();
			for (int i = 0; i < m_checkBillVOs.length; i++)
				vTemp1.addElement(m_checkBillVOs[i].getParentVO());
			for (int j = m_checkBillVOs[0].getBodyVos().length, i = 0; i < j; i++) {
				String sCheckStandard = m_checkBillVOs[0].getBodyVos()[i].getCcheckstandardid();
				if (!hStandard.containsKey(sCheckStandard)) {
					hStandard.put(sCheckStandard, sCheckStandard);
				}
			}
			sCheckStandards = (String[]) hStandard.values().toArray(new String[hStandard.size()]);
			for (int i = 0; i < m_checkBillVOs[0].getBodyVos().length; i++) {
				if (m_checkBillVOs[0].getBodyVos()[i].getCcheckstandardid().equals(sCheckStandards[0])) {
					vecBody.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				}
			}

			CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp1.size()];
			vTemp1.copyInto(headVOs);
			CheckbillItemVO[] bodyVOs = new CheckbillItemVO[vecBody.size()];
			vecBody.copyInto(bodyVOs);

			getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);
			getBillListPanel().getHeadBillModel().execLoadFormula();
			getBillListPanel().getHeadBillModel().updateValue();

			getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
			getBillListPanel().getBodyBillModel().execLoadFormula();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();
		} else {
			// 缓存中无记录
			m_bBodyQuery = null;
			m_checkBillVOs = null;
			getBillListPanel().getHeadBillModel().clearBodyData();
			getBillListPanel().getHeadBillModel().updateValue();
			getBillListPanel().getBodyBillModel().clearBodyData();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();

		}
		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateList();
	}

	/**
   * 此处插入方法说明。 功能描述:列表"修改" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListModify() {

		this.remove(getBillListPanel());
		m_tabbedPane.setVisible(true);
		getBillCardPanel().setEnabled(true);
		m_bBillEdit = true;
		// this.setButtons(m_btnCard);

		// since v53, 按钮逻辑重构, by Czp
		// this.setButtons(m_btnCardDisplay);
		this.setButtons(buttonManager.getBtnTree().getButtonArray());
		initMultiPanel(m_checkBillVOs[m_nBillRecord]);
		// since v53, 按钮逻辑重构, by Czp
		// m_hButtonState = new Hashtable();
		strCheckbillCode = m_checkBillVOs[m_nBillRecord].getHeadVo().getVcheckbillcode();
		// 刷新界面
		// 缓存中有数据
		getBillCardPanel().getBillData().setHeaderValueVO(m_checkBillVOs[m_nBillRecord].getHeadVo());
		HashMap hVO = new HashMap();
		for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
			if (!hVO.containsKey(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid())) {
				Vector vecVO = new Vector();
				vecVO.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				hVO.put(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid(), vecVO);
			} else {
				((Vector) hVO.get(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid()))
						.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
			}
		}
		for (int i = 0; i < sCheckStandards.length; i++) {
			Vector vecTemp = (Vector) hVO.get(sCheckStandards[i]);
			CheckbillItemVO[] voBody = new CheckbillItemVO[vecTemp.size()];
			vecTemp.copyInto(voBody);
			getBillCardPanel().getBillModel("table" + String.valueOf(i)).setBodyDataVO(voBody);
		}
		if (m_checkBillVOs[m_nBillRecord].getHeadVo().getCprayerid().equalsIgnoreCase("system")) {
			UIRefPane refPane = (UIRefPane) getBillCardPanel().getTailItem("cprayerid").getComponent();
			refPane.setText("system");
		}
		for (int i = 0; i < sCheckStandards.length; i++) {
			getBillCardPanel().getBillModel("table" + String.valueOf(i)).execLoadFormula();
		}
		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();

		// 检验信息页签可用
		m_tabbedPane.setEnabledAt(1, true);

		// 设置编辑状态
		setModifyEditState();

		m_nUIState = 0;
		// changeButtonState();

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCardModify();

		// add by yy begin
		getRefreshedBillSourceInfo();
		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			if (getBillCardPanel().getBodyValueAt(i, "csourcebillrowid") != null
					&& getBillCardPanel().getBodyValueAt(i, "csourcebillrowid").toString().trim().length() > 0) {
				String ls_csourcebillrowid = getBillCardPanel().getBodyValueAt(i, "csourcebillrowid").toString().trim();
				if (m_HTSourcebillInfo != null && m_HTSourcebillInfo.containsKey(ls_csourcebillrowid)) {
					String[] l_strary = (String[]) m_HTSourcebillInfo.get(ls_csourcebillrowid);
					getBillCardPanel().setBodyValueAt(l_strary[0], i, "csourcebilltypename");
					getBillCardPanel().setBodyValueAt(l_strary[2], i, "csourcebillrowno");
				}
			}
		}

		// add by yy end

		// 设置焦点
		getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);

		UIRefPane refPane;
		UITextField text;
		String s;

		refPane = (UIRefPane) getBillCardPanel().getHeadItem("vproductordercode").getComponent();
		s = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getVproductordercode();
		text = refPane.getUITextField();
		text.setText(s);
		onCardModify();
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:列表"打印" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListPrint() {
		// 确保所有表体已查询
		if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {

			int nSelected = getBillListPanel().getHeadTable().getSelectedRow();

			if (nSelected < 0 || nSelected > m_checkBillVOs.length - 1) {
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000007")/*
                                                                                                               * @res
                                                                                                               * "打印"
                                                                                                               */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH003")/*
                                                                             * @res
                                                                             * "请选择要处理的数据！"
                                                                             */);
				return;
			}
			//
			Vector v1 = new Vector();
			Vector v2 = new Vector();
			for (int i = 0; i < m_checkBillVOs.length; i++) {
				if (!m_bBodyQuery[i].booleanValue()) {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
					v1.addElement(headVO.getCcheckbillid());
					v2.addElement(headVO.getTs());
				}
			}
			String headKey[] = new String[v1.size()];
			String ts[] = new String[v2.size()];
			v1.copyInto(headKey);
			v2.copyInto(ts);
			if (headKey != null && headKey.length > 0) {
				try {
					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfoBatch(headKey, ts);
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);
					int j = 0;
					for (int i = 0; i < m_checkBillVOs.length; i++) {
						if (!m_bBodyQuery[i].booleanValue()) {
							CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
							ArrayList list0 = (ArrayList) list.get(j);
							if (list0 == null || list0.size() == 0)
								throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
										"UPPC0020101-000014")/*
                                           * @res "并发操作，请刷新！"
                                           */);
							CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list0.get(0);
							CheckbillB2VO grandVO[] = null;
							// 设置来源信息
							if (bodyVO != null && bodyVO.length > 0) {
								headVO.setCsource(bodyVO[0].getCsource());
								// headVO.setCsourcebillcode(bodyVO[0].getCsourcebillcode());
								// headVO.setCsourcebilltypecode(bodyVO[0].getCsourcebilltypecode());
								// headVO.setCsourcebillid(bodyVO[0].getCsourcebillid());
								// headVO.setCsourcebillrowid(bodyVO[0].getCsourcebillrowid());
								// 设置检验单头的工序信息
								// headVO.setCprocedureid(bodyVO[0]
								// .getCprocedureid());
							}

							Object o = list0.get(1);
							if (o != null) {
								grandVO = (CheckbillB2VO[]) o;
							}

							m_checkBillVOs[i].setChildrenVO(bodyVO);
							m_checkBillVOs[i].setGrandVO(grandVO);

							m_bBodyQuery[i] = new UFBoolean(true);

							// 计算合格数量等
							calculateNum();
							j++;
						}
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, "", e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					return;
				}
			}
			//
		} else {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000007")/*
                                                                                                             * @res
                                                                                                             * "打印"
                                                                                                             */, nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH003")/*
                                                                           * @res
                                                                           * "请选择要处理的数据！"
                                                                           */);
			return;
		}

		// add by yye begin 解决打印中的单据来源类型和来源行号问题
		// for (int i = 0; i < m_checkBillVOs.length; i++) {
		// CheckbillHeaderVO headVO = (CheckbillHeaderVO)
		// m_checkBillVOs[i].getParentVO();
		//
		// if (m_HTSourcebillInfo.containsKey(headVO.getCsourcebillrowid())) {
		// String[] l_strary = (String[])
		// m_HTSourcebillInfo.get(headVO.getCsourcebillrowid());
		// headVO.setCsourcebilltypename(l_strary[0]);
		// headVO.setCsourcebillrowno(l_strary[2]);
		//
		// }
		//
		// }
		// add by yye end
		Vector vecAgg = new Vector();
		Vector vecBody = new Vector();
		CheckbillVO[] vo = new CheckbillVO[m_checkBillVOs.length];
		for (int i = 0; i < vo.length; i++) {
			CheckbillVO voa = new CheckbillVO();
			voa.setParentVO(m_checkBillVOs[i].getHeadVo());
			for (int j = 0; j < m_checkBillVOs[i].getBodyVos().length; j++) {
				if (j == 0) {
					vecBody.add(m_checkBillVOs[i].getBodyVos()[j]);
				} else {
					if (m_checkBillVOs[i].getBodyVos()[j].getCcheckstandardid().equals(
							m_checkBillVOs[i].getBodyVos()[0].getCcheckstandardid())) {
						vecBody.add(m_checkBillVOs[i].getBodyVos()[j]);
					}
				}
			}
			CheckbillItemVO[] voBody = new CheckbillItemVO[vecBody.size()];
			vecBody.copyInto(voBody);
			voa.setChildrenVO(voBody);
			vecBody.clear();
			vecAgg.add(voa);
		}
		vecAgg.copyInto(vo);
		nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(this, null);

		print.setDataSource(new ListPrintDS(m_listBill, vo));
		print.setTemplateID(m_sUnitCode, "C0020102", getClientEnvironment().getUser().getPrimaryKey(), null);
		int ret = print.selectTemplate();
		if (ret > 0) {
			print.preview();
		}

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH041")/*
                                                                                     * @res
                                                                                     * "打印成功"
                                                                                     */);
	}

	/**
   * 此处插入方法说明。 功能描述:列表"查询" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListQuery() {

		// 效率优化，将initQuery()改为由按钮触发，且只触发一次。
		// 这样已提高打开结点时的速度。合理分配用时。
		// yye 2005-06-17
		if (!mbol_hasInitQuery) {
			initQuery();
			mbol_hasInitQuery = true;
		}

		// /////////////////
		m_condClient.hideUnitButton();
		m_condClient.showModal();

		if (m_condClient.isCloseOK()) {
			m_bQueried = true;
			// 获取查询条件
			String sCorpID[] = m_condClient.getSelectedCorpIDs();
			if (sCorpID == null || sCorpID.length == 0) {
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                       * @res
                                                                                                                       * "查询检验单"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                           * @res
                                                                                           * "没有符合条件的检验单！"
                                                                                           */);
				return;
			}

			m_conditionVOs = m_condClient.getConditionVO();
			if (m_rbFree.isSelected())
				m_nAddCondition = 0;
			else if (m_rbGoing.isSelected())
				m_nAddCondition = 2;
			else if (m_rbPass.isSelected())
				m_nAddCondition = 3;
			else if (m_rbNoPass.isSelected())
				m_nAddCondition = 4;
			else
				m_nAddCondition = -1;
			String chooseDataPower = null;
			chooseDataPower = getCheckItemDataPower();
			try {
				//
				m_checkBillVOs = CheckbillHelper.queryCheckbill(sCorpID, m_conditionVOs, new Integer(m_nAddCondition), m_dialog
						.getChecktypeid(), m_condClient.getSourceSQL(), chooseDataPower);

				// 无查询结果
				if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
					m_bBodyQuery = null;
					m_grandVOs = null;
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                         * @res
                                                                                                                         * "查询检验单"
                                                                                                                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                             * @res
                                                                                             * "没有符合条件的检验单！"
                                                                                             */);
					getBillListPanel().getHeadBillModel().clearBodyData();
					getBillListPanel().getHeadBillModel().updateValue();
					getBillListPanel().getBodyBillModel().clearBodyData();
					getBillListPanel().getBodyBillModel().updateValue();

					getBillListPanel().updateUI();
					this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000071")/*
                                                                                                             * @res
                                                                                                             * "不存在符合条件的记录！"
                                                                                                             */);
					return;
				}

				// 有查询结果
				// add by yy begin取得单据行号信息
				Vector lvec_SourceBillTypeCode = new Vector();
				Vector lvec_SourceBillHID = new Vector();
				Vector lvec_SourceBillBID = new Vector();

				CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[0].getChildrenVO();
				Vector orderData = new Vector(1000);
				if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
					for (int j = 0; j < l_CheckbillItemVO.length; j++) {
						if (l_CheckbillItemVO[j].getVstobatchcode() != null) {
							if (!vContain.contains(l_CheckbillItemVO[j].getVstobatchcode())) {
								vContain.put(l_CheckbillItemVO[j].getCmangid() + l_CheckbillItemVO[j].getCcheckstate_bid(),
										l_CheckbillItemVO[j].getVstobatchcode());
							}
						}
						if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null
								&& l_CheckbillItemVO[j].getCsourcebillid() != null
								&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
							lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
							lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
							lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
						}
						if (l_CheckbillItemVO[j].getInorder().intValue() == 0) {
							orderData.add(0, l_CheckbillItemVO[j]);
						} else {
							// 重新过滤主检验标准
							orderData.add(l_CheckbillItemVO[j]);
						}
					}
					CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
					orderData.copyInto(vos);
					m_checkBillVOs[0].setChildrenVO(vos);
					orderData.clear();
				}

				String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
				String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
				String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

				lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
				lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
				lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

				SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
				m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode,
						lstrary_SourceBillHID, lstrary_SourceBillBID);
				// add by yy end

				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();
				m_bBodyQuery = new UFBoolean[m_checkBillVOs.length];
				for (int i = 0; i < m_bBodyQuery.length; i++) {
					if (i == 0)
						m_bBodyQuery[i] = new UFBoolean(true);
					else
						m_bBodyQuery[i] = new UFBoolean(false);
				}

				m_nBillRecord = 0;

				// 刷新界面
				Vector vTemp = new Vector();
				for (int i = 0; i < m_checkBillVOs.length; i++)
					vTemp.addElement(m_checkBillVOs[i].getParentVO());
				CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp.size()];
				vTemp.copyInto(headVOs);

				// 设置自由项
				nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
				freeVOParse.setFreeVO(headVOs, "vfreeitem", "vfree", null, "cmangid", false);
				//

				CheckbillItemVO bodyVOs[] = (CheckbillItemVO[]) m_checkBillVOs[0].getChildrenVO();
				// 置入批次档案信息
				QcTool.execFormulaForBatchCode(bodyVOs, getCorpPrimaryKey());
				if (bodyVOs != null && bodyVOs.length > 0) {
					for (int j = 0; j < bodyVOs.length; j++) {
						Object[] objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
								"qualitymanflag", m_checkBillVOs[0].getHeadVo().getCmangid());
						objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
								"qualitydaynum", m_checkBillVOs[0].getHeadVo().getCmangid());
						if (objStr[0] != null) {
							int iQualityDayNum = Integer.parseInt(objStr[0].toString());
							objStr = (Object[]) nc.ui.scm.pub.cache.CacheTool.getCellValue("bd_invmandoc", "pk_invmandoc",
									"qualityperiodunit", m_checkBillVOs[0].getHeadVo().getCmangid());
							if (objStr[0] != null && (Integer) objStr[0] == 0) {
								iQualityDayNum = iQualityDayNum * 365;
							} else if (objStr[0] != null && (Integer) objStr[0] == 1) {
								iQualityDayNum = iQualityDayNum * 30;
							}

							Object strDate = l_CheckbillItemVO[j].getDproducedate();
							if (strDate != null) {
								UFDate dvalidate = new UFDate(strDate.toString()).getDateAfter(iQualityDayNum);
								l_CheckbillItemVO[j].setAttributeValue("cb_dvalidate", dvalidate);
							}
						}
					}
				}
				Vector vecBody = new Vector();
				HashMap<String, String> hStandard = new HashMap<String, String>();
				for (int j = m_checkBillVOs[m_nBillRecord].getBodyVos().length, i = 0; i < j; i++) {
					String sCheckStandard = m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid();
					if (!hStandard.containsKey(sCheckStandard)) {
						hStandard.put(sCheckStandard, sCheckStandard);
					}
				}
				sCheckStandards = hStandard.values().toArray(new String[hStandard.size()]);
				for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
					if (m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid().equals(sCheckStandards[0])) {
						vecBody.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
					}
				}
				bodyVOs = new CheckbillItemVO[vecBody.size()];
				vecBody.copyInto(bodyVOs);
				getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);

				getBillListPanel().getHeadBillModel().execLoadFormula();
				getBillListPanel().getHeadBillModel().updateValue();

				getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
				for (int i = 0; i < headVOs.length; i++) {
					if (headVOs[i].getCprayerid() != null && headVOs[i].getCprayerid().equalsIgnoreCase("system")) {
						getBillListPanel().getHeadBillModel().setValueAt("system", i, "cprayername");
					}
				}
				getBillListPanel().getBodyBillModel().execLoadFormula();
				getBillListPanel().getBodyBillModel().updateValue();

				getBillListPanel().updateUI();

				this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000120")/*
                                                                                                           * @res
                                                                                                           * "符合条件的记录数："
                                                                                                           */
						+ m_checkBillVOs.length);

				// 计算合格数量等
				calculateNum();

				// 设置存货编码
				setInventoryCode(headVOs);

				// add by yy begin
				setListSourceBillInfo();

				// add by yy end
				buttonManager.setButtonsStateList();
			} catch (Exception e) {
				SCMEnv.out(e);
				MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                       * @res
                                                                                                                       * "查询检验单"
                                                                                                                       */, e.getMessage());
			}
		}
	}

	/**
   * 获取检验项目权限sql-where语句
   * 
   * @return
   */
	private String getCheckItemDataPower() {
		String chooseDataPower = null;
		if (m_conditionVOs != null) {
			for (int i = 0; i < m_conditionVOs.length; i++) {
				if (m_conditionVOs[i].getFieldCode().equals("ccheckitemid")) {
					chooseDataPower = "qc_checkbill_b2.ccheckitemid in ('" + m_conditionVOs[i].getRefResult().getRefPK() + "')";
					break;
				}
			}
		}
		if (strSubSqlPower == null && chooseDataPower != null)
			return chooseDataPower;

		if (strSubSqlPower != null && chooseDataPower == null)
			return strSubSqlPower;

		if (strSubSqlPower != null && chooseDataPower != null)
			return strSubSqlPower + " and " + chooseDataPower;

		return null;

	}

	/**
   * 此处插入方法说明。 功能描述:列表"刷新" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListRefresh() {
		String sCorpID[] = m_condClient.getSelectedCorpIDs();
		if (sCorpID == null || sCorpID.length == 0) {
			MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                     * @res
                                                                                                                     * "查询检验单"
                                                                                                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                         * @res
                                                                                         * "没有符合条件的检验单！"
                                                                                         */);
			return;
		}
		for (int i = 0; i < m_conditionVOs.length; i++) {
			if (m_conditionVOs[i].getFieldCode().equals("ccheckitemid")) {
				strSubSqlPower = "qc_checkbill_b2.ccheckitemid in ('" + m_conditionVOs[i].getRefResult().getRefPK() + "')";
				break;
			}
		}
		try {
			//
			m_checkBillVOs = CheckbillHelper.queryCheckbill(sCorpID, m_conditionVOs, new Integer(m_nAddCondition), m_dialog
					.getChecktypeid(), m_condClient.getSourceSQL(), strSubSqlPower);

			// 无查询结果
			if (m_checkBillVOs == null || m_checkBillVOs.length == 0) {
				m_bBodyQuery = null;
				m_grandVOs = null;
				MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                       * @res
                                                                                                                       * "查询检验单"
                                                                                                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000041")/*
                                                                                           * @res
                                                                                           * "没有符合条件的检验单！"
                                                                                           */);
				getBillListPanel().getHeadBillModel().clearBodyData();
				getBillListPanel().getHeadBillModel().updateValue();
				getBillListPanel().getBodyBillModel().clearBodyData();
				getBillListPanel().getBodyBillModel().updateValue();

				getBillListPanel().updateUI();
				this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000071")/*
                                                                                                           * @res
                                                                                                           * "不存在符合条件的记录！"
                                                                                                           */);
				return;
			}

			// 有查询结果
			// add by yy begin取得单据行号信息
			Vector lvec_SourceBillTypeCode = new Vector();
			Vector lvec_SourceBillHID = new Vector();
			Vector lvec_SourceBillBID = new Vector();

			CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[0].getChildrenVO();
			if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {
				for (int j = 0; j < l_CheckbillItemVO.length; j++) {
					if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null && l_CheckbillItemVO[j].getCsourcebillid() != null
							&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
						lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
						lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
						lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
					}
				}
			}

			String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
			String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
			String[] lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

			lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
			lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
			lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

			SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
			m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode, lstrary_SourceBillHID,
					lstrary_SourceBillBID);
			// add by yy end

			m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[0].getGrandVO();
			m_bBodyQuery = new UFBoolean[m_checkBillVOs.length];
			for (int i = 0; i < m_bBodyQuery.length; i++) {
				if (i == 0)
					m_bBodyQuery[i] = new UFBoolean(true);
				else
					m_bBodyQuery[i] = new UFBoolean(false);
			}

			m_nBillRecord = 0;

			// 刷新界面
			Vector vTemp = new Vector();
			for (int i = 0; i < m_checkBillVOs.length; i++)
				vTemp.addElement(m_checkBillVOs[i].getParentVO());
			CheckbillHeaderVO headVOs[] = new CheckbillHeaderVO[vTemp.size()];
			vTemp.copyInto(headVOs);
			Vector vecBody = new Vector();
			HashMap<String, String> hStandard = new HashMap<String, String>();
			for (int j = m_checkBillVOs[m_nBillRecord].getBodyVos().length, i = 0; i < j; i++) {
				String sCheckStandard = m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid();
				if (!hStandard.containsKey(sCheckStandard)) {
					hStandard.put(sCheckStandard, sCheckStandard);
				}
			}
			sCheckStandards = hStandard.values().toArray(new String[hStandard.size()]);
			for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
				if (m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid().equals(sCheckStandards[0])) {
					vecBody.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				}
			}
			CheckbillItemVO[] bodyVOs = new CheckbillItemVO[vecBody.size()];
			vecBody.copyInto(bodyVOs);
			getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);

			getBillListPanel().getHeadBillModel().execLoadFormula();
			getBillListPanel().getHeadBillModel().updateValue();

			getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
			// 设置自由项
			nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
			freeVOParse.setFreeVO(headVOs, "vfreeitem", "vfree", null, "cmangid", false);
			//
			//
			// CheckbillItemVO bodyVOs[] = (CheckbillItemVO[]) m_checkBillVOs[0]
			// .getChildrenVO();

			QcTool.execFormulaForBatchCode(bodyVOs, getCorpPrimaryKey());
			//
			// getBillListPanel().getHeadBillModel().setBodyDataVO(headVOs);
			// getBillListPanel().getHeadBillModel().execLoadFormula();
			// getBillListPanel().getHeadBillModel().updateValue();
			//
			// getBillListPanel().getBodyBillModel().setBodyDataVO(bodyVOs);
			// getBillListPanel().getBodyBillModel().execLoadFormula();
			getBillListPanel().getBodyBillModel().updateValue();

			getBillListPanel().updateUI();

			this.showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("c0020101", "UPPc0020101-000120")/*
                                                                                                         * @res
                                                                                                         * "符合条件的记录数："
                                                                                                         */
					+ m_checkBillVOs.length);

			// add by yy begin
			setListSourceBillInfo();

			// add by yy end

			// 计算合格数量等
			calculateNum();

			// 设置存货编码
			setInventoryCode(headVOs);

		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000040")/*
                                                                                                                     * @res
                                                                                                                     * "查询检验单"
                                                                                                                     */, e.getMessage());
		}

	}

	/**
   * 此处插入方法说明。 功能描述:列表"联查" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListRelateQuery() {
		CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
		if (headVO == null) {
			return;
		}
		nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(this, "WH", headVO
				.getCcheckbillid(), null, getClientEnvironment().getUser().getPrimaryKey(), headVO.getVcheckbillcode());
		soureDlg.showModal();

	}

	/**
   * 此处插入方法说明。 功能描述:列表"切换" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListSwitch() {
		// 列表可能已排序，获得排序前的缓存记录指针
		if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {
			m_nBillRecord = nc.ui.qc.pub.QCTool.getIndexBeforeSort(m_listBill, m_nBillRecord);
		} else {
			m_nBillRecord = -1;
		}

		this.remove(getBillListPanel());
		m_tabbedPane.setVisible(true);

		this.setButtons(buttonManager.getBtnTree().getButtonArray());

		m_nUIState = 0;

		// 刷新界面
		if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {
			// 若表体未查询，则查询表体
			if (!m_bBodyQuery[m_nBillRecord].booleanValue()) {
				try {
					CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();
					strCheckbillCode = headVO.getVcheckbillcode();
					String chooseDataPower = null;
					chooseDataPower = getCheckItemDataPower();
					ArrayList list = CheckbillHelper.queryCheckbillItemAndInfo(headVO.getCcheckbillid(), headVO.getTs(),
							chooseDataPower);
					if (list == null || list.size() == 0)
						throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000014")/*
                                                                                                                       * @res
                                                                                                                       * "并发操作，请刷新！"
                                                                                                                       */);

					if (list != null && list.size() > 0) {
						// 有返回结果
						CheckbillItemVO bodyVO[] = (CheckbillItemVO[]) list.get(0);
						Vector orderData = new Vector();
						for (int i = 0; i < bodyVO.length; i++) {
							if (bodyVO[i].getInorder() != null && bodyVO[i].getInorder().intValue() == 0) {
								orderData.add(0, bodyVO[i]);
							} else {
								// 重新过滤主检验标准
								orderData.add(bodyVO[i]);
							}
						}
						CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
						orderData.copyInto(vos);
						m_checkBillVOs[m_nBillRecord].setChildrenVO(vos);
						orderData.clear();
						CheckbillB2VO grandVO[] = null;

						// 设置来源信息
						if (bodyVO != null && bodyVO.length > 0) {
							headVO.setCsource(bodyVO[0].getCsource());
							// headVO.setCsourcebillcode(bodyVO[0].getCsourcebillcode());
							// headVO.setCsourcebilltypecode(bodyVO[0].getCsourcebilltypecode());
							// headVO.setCsourcebillid(bodyVO[0].getCsourcebillid());
							// headVO.setCsourcebillrowid(bodyVO[0].getCsourcebillrowid());
							// 设置检验单头的工序信息
							// headVO.setCprocedureid(bodyVO[0].getCprocedureid());
							// 设置自由项
							headVO.setVfree1(bodyVO[0].getVfree1());
							headVO.setVfree2(bodyVO[0].getVfree2());
							headVO.setVfree3(bodyVO[0].getVfree3());
							headVO.setVfree4(bodyVO[0].getVfree4());
							headVO.setVfree5(bodyVO[0].getVfree5());
							// Object oTemp = list.get(2);
							// if (oTemp != null)
							// headVO.setVfreeitem(oTemp.toString());
							nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
							freeVOParse.setFreeVO(new CheckbillHeaderVO[] { headVO }, "vfreeitem", "vfree", null, "cmangid", false);
						}

						Object o = list.get(1);
						if (o != null) {
							grandVO = (CheckbillB2VO[]) o;
							m_grandVOs = grandVO;
						} else {
							m_grandVOs = null;
						}

						m_checkBillVOs[m_nBillRecord].setChildrenVO(bodyVO);
						m_checkBillVOs[m_nBillRecord].setGrandVO(grandVO);

						m_bBodyQuery[m_nBillRecord] = new UFBoolean(true);

						// 多公司查询，改变检验单表头参照
						String pk_corp = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getPk_corp();
						if (pk_corp != null && pk_corp.trim().length() > 0) {
							// changeBillReference(pk_corp);
							m_sUnitCode = pk_corp;
						}
					} else {
						// 无返回结果
						MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
								"UPPC0020101-000015")/*
                                       * @res "查询表体"
                                       */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000016")/*
                                                                                               * @res
                                                                                               * "表体不存在！"
                                                                                               */);
						return;
					}
				} catch (BusinessException e) {
					SCMEnv.out(e);
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000017")/*
                                                                                                                         * @res
                                                                                                                         * "并发操作"
                                                                                                                         */, e.getMessage());
					return;
				} catch (Exception e) {
					SCMEnv.out(e);
					MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("C0020101", "UPPC0020101-000015")/* @res "查询表体" */, e.getMessage());
					return;
				}
			} else {
				m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();
			}
			Vector orderData = new Vector();
			for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getChildrenVO().length; i++) {
				if (((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]).getInorder() != null
						&& ((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]).getInorder().intValue() == 0) {
					orderData.add(0, ((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]));
				} else {
					// 重新过滤主检验标准
					orderData.add(((CheckbillItemVO) m_checkBillVOs[m_nBillRecord].getChildrenVO()[i]));
				}
			}
			CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
			orderData.copyInto(vos);
			m_checkBillVOs[m_nBillRecord].setChildrenVO(vos);
			orderData.clear();
			// 置入批次档案信息
			QcTool.execFormulaForBatchCode(m_checkBillVOs[m_nBillRecord].getChildrenVO(), getCorpPrimaryKey());
			initMultiPanel(m_checkBillVOs[m_nBillRecord]);
			// 缓存中有数据

			if (m_checkBillVOs[m_nBillRecord].getHeadVo().getCprayerid() != null
					&& m_checkBillVOs[m_nBillRecord].getHeadVo().getCprayerid().equalsIgnoreCase("system")) {
				UIRefPane refPane = (UIRefPane) getBillCardPanel().getTailItem("cprayerid").getComponent();
				refPane.setText("system");
			}

			QcTool.setValue(getBillCardPanel(), m_checkBillVOs[m_nBillRecord]);
			// 计算合格数量等
			calculateNum();
			setValues();
			strCheckbillCode = m_checkBillVOs[m_nBillRecord].getHeadVo().getVcheckbillcode();
			// 检验信息页签可用
			m_tabbedPane.setEnabledAt(1, true);

			// add by yy begin
			getRefreshedBillSourceInfo();
			setCardSourceBillInfo();
			// add by yy end

		} else {
			// 缓存无数据
			BillTempletHeadVO initHeadVO = tmpletvo.getHeadVO();
			BillTempletBodyVO[] initBodyVO = tmpletvo.getBodyVO();
			BillTempletVO initVO = new BillTempletVO(initHeadVO, initBodyVO);
			BillData newbd = new BillData(initVO);
			m_cardBill.setBillData(newbd);
			m_cardBill.setAutoExecHeadEditFormula(true);
			m_cardBill.setEnabled(false);
			getBillCardPanel().getBillData().clearViewData();
			getBillCardPanel().updateValue();
			getBillCardPanel().updateUI();
			sCheckStandards = null;
			// 检验信息页签不可用
			m_tabbedPane.setEnabledAt(1, false);
		}
		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();
		// add by hanbin 2010-10-20 原因：解决显示公式不执行的问题
		getBillCardPanel().execHeadLoadFormulas();
		getBillCardPanel().getBillModel().execLoadFormula();
		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();

		return;
	}

	/**
   * 此处插入方法说明。 功能描述:卡片"定位" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onCardLocate() {
		if (m_dlgLocate == null) {
			m_dlgLocate = new UIDialog(this);
			m_dlgLocate.setTitle(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPT10081802-000015")/*
                                                                                                         * @res
                                                                                                         * "定位"
                                                                                                         */);

			UILabel label = new UILabel(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000097")/*
                                                                                                                 * @res
                                                                                                                 * "选择检验单号："
                                                                                                                 */);
			label.setBounds(100, 50, 100, 25);

			m_comLocate = new UIComboBox();
			m_comLocate.setBounds(200, 50, 100, 25);
			m_comLocate.setEditable(false);

			m_btnLocateOK = new UIButton();
			m_btnLocateOK.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000044")/*
                                                                                                   * @res
                                                                                                   * "确定"
                                                                                                   */);
			m_btnLocateOK.addActionListener(this);
			m_btnLocateCancel = new UIButton();
			m_btnLocateCancel.setText(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UC001-0000008")/*
                                                                                                       * @res
                                                                                                       * "取消"
                                                                                                       */);
			m_btnLocateCancel.addActionListener(this);
			m_btnLocateOK.setBounds(80, 150, 70, 30);
			m_btnLocateCancel.setBounds(250, 150, 70, 30);

			m_dlgLocate.getContentPane().setLayout(null);
			m_dlgLocate.getContentPane().add(label);
			m_dlgLocate.getContentPane().add(m_comLocate);
			m_dlgLocate.getContentPane().add(m_btnLocateOK);
			m_dlgLocate.getContentPane().add(m_btnLocateCancel);
		}

		if (m_checkBillVOs != null && m_checkBillVOs.length > 0) {
			m_comLocate.setEditable(true);
			m_comLocate.removeAllItems();
			for (int i = 0; i < m_checkBillVOs.length; i++)
				m_comLocate.addItem(((CheckbillHeaderVO) m_checkBillVOs[i].getParentVO()).getVcheckbillcode());
		}

		m_dlgLocate.showModal();
	}

	/**
   * 此处插入方法说明。 功能描述:列表"全选" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListSelectAll() {

		getBillListPanel().getHeadTable().setRowSelectionInterval(0,
				getBillListPanel().getHeadBillModel().getRowCount() - 1);

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateList();

	}

	/**
   * 此处插入方法说明。 功能描述:列表"全消" 输入参数: 返回值: 异常处理: 日期:
   */
	private void onListSelectNo() {
		getBillListPanel().getHeadTable().removeRowSelectionInterval(0,
				getBillListPanel().getHeadBillModel().getRowCount() - 1);

		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateList();

	}

	/**
   * 此处插入方法说明。 功能描述:弃审 输入参数: 返回值: 异常处理: 日期:2003/04/02
   */
	private void onCardUnAudit() {
		try {

			CheckbillHeaderVO headVO = (CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO();

			// 是否允许本人审批\弃审
			if (!m_bAllowSelf) {
				String lstr_Oper = getClientEnvironment().getUser().getPrimaryKey();
				String lstr_Reporter = headVO.getCauditpsn();
				if (lstr_Oper.trim().equals(lstr_Reporter.trim())) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000059")/*
                                     * @res "错误"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000034")/*
                                                                                             * @res
                                                                                             * "不允许本人审批/弃审自己的单据！"
                                                                                             */);
					return;
				}
			}
			// 是否允许他人弃审单据
			if (!m_bAllow) {
				String lstr_Oper = getClientEnvironment().getUser().getPrimaryKey();
				String lstr_Reporter = headVO.getCauditpsn();
				if (!lstr_Oper.trim().equals(lstr_Reporter.trim())) {
					MessageDialog.showHintDlg(this, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000007")/*非本人审核单据不允许弃审*/, NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000007")/*非本人审核单据不允许弃审*/);
					return;
				}
			}

			// 给当前检验单赋操作员
			headVO.setCauditpsn(getClientEnvironment().getUser().getPrimaryKey());
			Object[] userObj = { getClientEnvironment().getUser().getPrimaryKey(), getClientEnvironment().getDate(),
					headVO.getPk_corp() };
			Object[] userObjAry = new Object[1];
			userObjAry[0] = userObj;
			nc.ui.pub.pf.PfUtilClient.processBatch("UNAPPROVE", strBilltemplet, getClientEnvironment().getDate().toString(),
					new CheckbillVO[] { m_checkBillVOs[m_nBillRecord] }, userObjAry);
			if (!nc.ui.pub.pf.PfUtilClient.isSuccess())
				return;

			// 弃审成功后刷新数据
			// 2009/10/24 田锋涛 改为不带billstatus参数的调用
			CheckbillVO VO = CheckbillHelper.queryCheckbillByHeadKey(headVO.getCcheckbillid());

			if (VO != null) {
				// 设置自由项
				nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
				freeVOParse.setFreeVO(new CheckbillHeaderVO[] { (CheckbillHeaderVO) VO.getParentVO() }, null, "cmangid", false);
				//
				m_checkBillVOs[m_nBillRecord] = VO;
				// VO.getHeadVo().setVinvbatchcode(VO.getBodyVos()[0].getVstobatchcode());
			}

			headVO.setCauditpsn(null);
			headVO.setDauditdate(null);
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000043")/*
                                                                                                                     * @res
                                                                                                                     * "检验单弃审"
                                                                                                                     */, e.getMessage());
			SCMEnv.out(e);
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common", "UCH011")/*
                                                                                     * @res
                                                                                     * "弃审检验单成功"
                                                                                     */);

		// 弃审后置入批次档案的各个属性
		CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[m_nBillRecord].getChildrenVO();
		QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());
		// 审批成功后刷新界面
		getBillCardPanel().getBillData().setHeaderValueVO(m_checkBillVOs[m_nBillRecord].getHeadVo());
		HashMap hVO = new HashMap();
		for (int i = 0; i < m_checkBillVOs[m_nBillRecord].getBodyVos().length; i++) {
			if (!hVO.containsKey(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid())) {
				Vector vecVO = new Vector();
				vecVO.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
				hVO.put(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid(), vecVO);
			} else {
				((Vector) hVO.get(m_checkBillVOs[m_nBillRecord].getBodyVos()[i].getCcheckstandardid()))
						.add(m_checkBillVOs[m_nBillRecord].getBodyVos()[i]);
			}
		}
		for (int i = 0; i < sCheckStandards.length; i++) {
			Vector vecTemp = (Vector) hVO.get(sCheckStandards[i]);
			CheckbillItemVO[] voBody = new CheckbillItemVO[vecTemp.size()];
			vecTemp.copyInto(voBody);
			getBillCardPanel().getBillModel("table" + String.valueOf(i));
		}

		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();

		setValues();

		// add by yy begin
		getRefreshedBillSourceInfo();
		setCardSourceBillInfo();

		// add by yy end
		calculateNum();
		// since v53, 按钮逻辑重构, by Czp
		buttonManager.setButtonsStateCard();
	}

	/**
   * 此处插入方法说明。 功能描述:增加检验单时设置编辑控制 输入参数: 返回值: 异常处理: 日期:2002/05/09
   */
	private void setAddEditState() {
		// 设置检验类型
		getBillCardPanel().getHeadItem("cchecktypeid").setValue(m_dialog.getChecktypeid());
		UIRefPane nRefPanel = (UIRefPane) getBillCardPanel().getHeadItem("cchecktypeid").getComponent();
		nRefPanel.getUITextField().setText(m_dialog.getChecktypename());
		nRefPanel.setEnabled(false);

		// 设置存货名称、规格、型号、主计量、公司不可编辑
		BillItem headItem = getBillCardPanel().getHeadItem("cinventoryname");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventoryspec");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventorytype");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventoryunit");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("pk_corp");
		if (headItem.isShow()) {
			headItem.setValue(m_sUnitCode);
			headItem.setEnabled(false);
		}

		if (!m_dialog.getCheckbilltypecode().trim().equals("WH")) {
			headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
			if (headItem.isShow())
				headItem.setEnabled(false);
		} else {
			headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
			if (headItem.isShow())
				headItem.setEnabled(true);
		}

		// /表头合格辅数量、不合格辅数量、改判数量、改判辅数量、改判率字段应控制不可编辑
		headItem = getBillCardPanel().getHeadItem("nqualifiedassinum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nunqualifiedassinum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nchangenum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nchangeassinum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nchangerate");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 新增初始状态下不可编辑副计量\辅数量\换算率等信息
		headItem = getBillCardPanel().getHeadItem("nassistchecknum");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cassistunitid");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("nexchangerate");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 设置报告人、报告日期、审批人、审批日期、合格数量、不合格数量、合格率不可编辑
		headItem = getBillCardPanel().getHeadItem("nqualifiednum");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("nunqualifiednum");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("nqualifiedrate");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 是否合格品不可编辑
		headItem = getBillCardPanel().getBodyItem("bqualified");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 不合格项目不可编辑
		headItem = getBillCardPanel().getBodyItem("ccheckitemid");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// /自由项
		headItem = getBillCardPanel().getHeadItem("vfreeitem");
		if (headItem.isShow())
			headItem.setEnabled(false);

		BillItem tailItem = getBillCardPanel().getTailItem("cauditpsn");
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("dauditdate");
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("creporterid");
		tailItem.setValue(getClientEnvironment().getUser().getPrimaryKey());
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("cprayerid");
		tailItem.setValue(getClientEnvironment().getUser().getPrimaryKey());
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("dreportdate");
		tailItem.setValue(ClientEnvironment.getInstance().getDate().toString() + " "
				+ new UFDateTime(System.currentTimeMillis()).getTime());
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("taudittime");
		tailItem.setEnabled(false);

		// 设置报检日期
		tailItem = getBillCardPanel().getTailItem("dpraydate");

		// add by yye begin
		UFDate lufd_BusinessDate = getClientEnvironment().getDate();// 登陆日期
		UFDateTime ldt_ServerTime = ClientEnvironment.getServerTime();// 时间
		UFDateTime ldt_prayDateTime = new UFDateTime(lufd_BusinessDate, new UFTime(ldt_ServerTime.getTime()));
		// add by yye end
		tailItem.setValue(ldt_prayDateTime.toString());

		// 非手工报检，下列项不可编辑
		if (!m_dialog.getCheckbilltypecode().trim().equals("WH")) {
			// 检验批次
			// headItem = getBillCardPanel().getHeadItem("vbatchcode");
			// if(headItem.isShow()) headItem.setEnabled(false);
			// 检验单号
			headItem = getBillCardPanel().getHeadItem("vcheckbillcode");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 公司
			headItem = getBillCardPanel().getHeadItem("pk_corp");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 报检部门
			headItem = getBillCardPanel().getHeadItem("cpraydeptid");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 存货编码
			headItem = getBillCardPanel().getHeadItem("cinventorycode");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 工序
			// headItem = getBillCardPanel().getHeadItem("cprocedureid");
			// if (headItem.isShow())
			// headItem.setEnabled(false);
			// 报检数量
			headItem = getBillCardPanel().getHeadItem("nchecknum");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 供应商
			headItem = getBillCardPanel().getHeadItem("cvendormangid");
			if (headItem.isShow())
				headItem.setEnabled(false);
		} else {
			// 报检公司
			headItem = getBillCardPanel().getHeadItem("cpraycorp");
			if (headItem.isShow())
				headItem.setValue(m_sUnitCode);

			headItem = getBillCardPanel().getHeadItem("ccheckmodeid");
			if (headItem.isShow())
				headItem.setEnabled(false);

		}

		// 来源信息不可编辑
		headItem = getBillCardPanel().getHeadItem("csource");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getBodyItem("csourcebilltypecode");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getBodyItem("csourcebillcode");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getBodyItem("csourcebillid");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getBodyItem("csourcebillrowid");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getBodyItem("csourcebillrowno");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getBodyItem("csourcebilltypename");
		if (headItem.isShow())
			headItem.setEnabled(false);

		getBillCardPanel().getBillModel().execLoadFormula();
		getBillCardPanel().updateValue();
		getBillCardPanel().updateUI();

		// 检验信息页签可用
		m_tabbedPane.setEnabledAt(1, true);
		return;
	}

	/**
   * 此处插入方法说明。 功能描述:检验信息设置编辑控制 输入参数: 返回值: 异常处理: 日期:2002/05/20
   */
	private void setInfoEditState() {
		// 设置检验项目、检验值单位、是否关键项目、标准值类型、是否必须达到不可编辑
		BillItem bodyItem = getBillCardPanelInfo().getBodyItem("ccheckitemname");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("ccheckunitname");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("bcritical");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("icheckstandard");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("bmust");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		// UIRefPane nRefPane =
		// (UIRefPane)getInfoCardPanel().getBodyItem("cstandardvalue").getComponent();
		// nRefPane.setEditable(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("cstandardvalue");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		// bodyItem = getBillCardPanelInfo().getBodyItem("ccheckresult");
		// if (bodyItem.isShow())
		// bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("bmeet");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		return;
	}

	/**
   * 此处插入方法说明。 功能描述:检验信息设置编辑控制 输入参数: 返回值: 异常处理: 日期:2002/05/20
   */
	private void setInfoEmendState() {

		// 修订时，表头不可编辑
		BillItem headItem = getBillCardPanelInfo().getHeadItem("vsamplecode");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanelInfo().getHeadItem("nnum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 设置检验项目、检验值单位、是否关键项目、标准值类型、是否必须达到不可编辑
		BillItem bodyItem = getBillCardPanelInfo().getBodyItem("ccheckitemname");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("ccheckunitname");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("bcritical");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("icheckstandard");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("bmust");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		// UIRefPane nRefPane =
		// (UIRefPane)getInfoCardPanel().getBodyItem("cstandardvalue").getComponent();
		// nRefPane.setEditable(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("cstandardvalue");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		bodyItem = getBillCardPanelInfo().getBodyItem("ccheckresult");
		if (bodyItem.isShow())
			bodyItem.setEnabled(false);

		// 修订时,不能修改关键检验项目检验记录行
		for (int i = 0; i < getBillCardPanelInfo().getRowCount(); i++) {
			if (getBillCardPanelInfo().getBodyValueAt(i, "bcritical") != null
					&& new UFBoolean(getBillCardPanelInfo().getBodyValueAt(i, "bcritical").toString().trim()).booleanValue()) {
				getBillCardPanelInfo().setCellEditable(i, "cresult", false);
				getBillCardPanelInfo().setCellEditable(i, "vmemo", false);
				getBillCardPanelInfo().setCellEditable(i, "ccheckername", false);
				getBillCardPanelInfo().setCellEditable(i, "dcheckdate", false);
				getBillCardPanelInfo().setCellEditable(i, "bmeet", false);
			}

		}

		return;
	}

	/**
   * 此处插入方法说明。 功能描述:获得存货编码（针对列表） 输入参数: 返回值: 异常处理: 日期:
   * 
   * @return java.lang.String[]
   * @param headVO
   *          nc.vo.qc.bill.CheckbillHeaderVO[]
   */
	private void setInventoryCode(CheckbillHeaderVO[] headVO) {
		FormulaParse f = new FormulaParse();
		String sExpress = "getColValue(bd_invbasdoc,invcode,pk_invbasdoc,cbaseid)";
		f.setExpress(sExpress);
		VarryVO varry = f.getVarry();

		Hashtable table = new Hashtable();
		String param[] = new String[headVO.length];
		for (int i = 0; i < headVO.length; i++)
			param[i] = nc.vo.pub.formulaset.util.StringUtil.toString(headVO[i].getCbaseid());

		table.put(varry.getVarry()[0], param);

		f.setDataS(table);
		String s[] = f.getValueS();

		if (s != null && s.length > 0) {
			for (int i = 0; i < headVO.length; i++) {
				getBillListPanel().getHeadBillModel().setValueAt(s[i], i, "cinventorycode");
			}
		}

		return;
	}

	/**
   * 此处插入方法说明。 功能描述:修改检验单时设置编辑控制 输入参数: 返回值: 异常处理: 日期:2002/05/22
   */
	private void setModifyEditState() {
		// 批次档案检验时间不可编辑

		// 设置检验类型、存货编码、名称、规格、型号、主计量、公司、工序不可编辑
		BillItem headItem = getBillCardPanel().getHeadItem("cchecktypeid");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventorycode");
		if (headItem.isShow())
			headItem.setEnabled(false);
		// headItem = getBillCardPanel().getHeadItem("vcheckbillcode");
		// if (headItem.isShow())
		// headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventoryname");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventoryspec");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventorytype");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("cinventoryunit");
		if (headItem.isShow())
			headItem.setEnabled(false);
		// headItem = getBillCardPanel().getHeadItem("cprocedureid");
		// if (headItem.isShow())
		// headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("pk_corp");
		if (headItem.isShow()) {
			headItem.setValue(m_sUnitCode);
			headItem.setEnabled(false);
		}

		headItem = getBillCardPanel().getHeadItem("vinvbatchcode");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 表头合格辅数量、不合格辅数量、改判数量、改判辅数量、改判率字段应控制不可编辑
		headItem = getBillCardPanel().getHeadItem("nqualifiedassinum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nunqualifiedassinum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nchangenum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nchangeassinum");
		if (headItem.isShow())
			headItem.setEnabled(false);

		headItem = getBillCardPanel().getHeadItem("nchangerate");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 设置报告人、报告日期、审批人、审批日期、合格数量、不合格数量、合格率、来源信息等不可编辑
		headItem = getBillCardPanel().getHeadItem("nqualifiednum");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("nunqualifiednum");
		if (headItem.isShow())
			headItem.setEnabled(false);
		headItem = getBillCardPanel().getHeadItem("nqualifiedrate");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 来源信息不可编辑
		headItem = getBillCardPanel().getHeadItem("csource");
		if (headItem.isShow())
			headItem.setEnabled(false);

		// 自由项
		headItem = getBillCardPanel().getHeadItem("vfreeitem");
		if (headItem.isShow())
			headItem.setEnabled(false);

		BillItem tailItem = getBillCardPanel().getTailItem("cauditpsn");
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("dauditdate");
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("taudittime");
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("creporterid");
		tailItem.setValue(getClientEnvironment().getUser().getPrimaryKey());
		tailItem.setEnabled(false);
		tailItem = getBillCardPanel().getTailItem("dreportdate");
		tailItem.setValue(ClientEnvironment.getInstance().getDate().toString() + " "
				+ new UFDateTime(System.currentTimeMillis()).getTime());
		tailItem.setEnabled(false);

		// 非手工报检，下列项不可编辑
		if (!isFromQC()) {
			// 报检公司
			headItem = getBillCardPanel().getHeadItem("cpraycorp");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 完工报告号
			headItem = getBillCardPanel().getHeadItem("vproductbatchcode");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 检验单号
			// headItem = getBillCardPanel().getHeadItem("vcheckbillcode");
			// if (headItem.isShow())
			// headItem.setEnabled(false);
			// 公司
			headItem = getBillCardPanel().getHeadItem("pk_corp");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 报检部门
			headItem = getBillCardPanel().getHeadItem("cpraydeptid");
			if (headItem.isShow()) {
				if ((isFromPU() || isFromIC()) && headItem.getValue() == null) {
					headItem.setEnabled(true);
				} else {
					headItem.setEnabled(false);
				}
			}
			// 存货编码
			headItem = getBillCardPanel().getHeadItem("cinventorycode");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 工序
			// headItem = getBillCardPanel().getHeadItem("cprocedureid");
			// if (headItem.isShow())
			// headItem.setEnabled(false);
			//
			// 生产订单号
			headItem = getBillCardPanel().getHeadItem("vproductordercode");
			if (headItem.isShow())
				headItem.setEnabled(false);

			// 报检数量
			headItem = getBillCardPanel().getHeadItem("nchecknum");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 报检辅数量
			headItem = getBillCardPanel().getHeadItem("nassistchecknum");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 辅单位
			headItem = getBillCardPanel().getHeadItem("cassistunitid");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 换算率
			headItem = getBillCardPanel().getHeadItem("nexchangerate");
			if (headItem.isShow())
				headItem.setEnabled(false);

			// 报检人
			headItem = getBillCardPanel().getTailItem("cprayerid");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 报检日期
			headItem = getBillCardPanel().getTailItem("dpraydate");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 供应商
			headItem = getBillCardPanel().getHeadItem("cvendormangid");
			if (headItem.isShow())
				headItem.setEnabled(false);
			// 如果是完工报检，则是否成分检验和产出序号不能编辑
			if (isFromMM()) {
				headItem = getBillCardPanel().getHeadItem("bcompcheck");
				if (headItem.isShow())
					headItem.setEnabled(false);

				headItem = getBillCardPanel().getHeadItem("vprodserialnum");
				if (headItem.isShow())
					headItem.setEnabled(false);

			}

			headItem = getBillCardPanel().getHeadItem("ccheckmodeid");
			if (headItem.isShow())
				headItem.setEnabled(false);
		} else {
			headItem = getBillCardPanel().getHeadItem("ccheckmodeid");
			if (headItem.isShow())
				headItem.setEnabled(false);

			// 存货编码
			headItem = getBillCardPanel().getHeadItem("cinventorycode");
			if (headItem.isShow())
				headItem.setEnabled(true);
		}

		// 表头的换算率是否可编辑
		String s1 = (String) getBillCardPanel().getHeadItem("cassistunitid").getValueObject();
		String s2 = (String) getBillCardPanel().getHeadItem("cbaseid").getValueObject();

		if (s1 != null) { // 辅计量管理

			// 是否固定换算率
			Object temp[][] = nc.ui.scm.pub.CacheTool.getAnyValue2("bd_convert", "fixedflag", "pk_measdoc='" + s1
					+ "' and pk_invbasdoc='" + s2 + "'");
			if (temp != null && temp.length > 0 && temp[0] != null && temp[0].length > 0 && temp[0][0] != null) {
				getBillCardPanel().getHeadItem("bfixed").setValue(temp[0][0]);
			} else {
				getBillCardPanel().getHeadItem("bfixed").setValue("Y");
			}

			// 手工报检时，且为辅计量管理时，需要判断表头辅数量、换算率是否可编辑
			if (isFromQC()) {
				getBillCardPanel().getHeadItem("bassunitmanaged").setValue("Y");
				headItem = getBillCardPanel().getHeadItem("nassistchecknum");
				if (headItem.isShow())
					headItem.setEnabled(true);

				boolean lb_headfix = (new UFBoolean(getBillCardPanel().getHeadItem("bfixed").getValue().toString().trim()))
						.booleanValue();
				headItem = getBillCardPanel().getHeadItem("nexchangerate");
				if (!lb_headfix) {
					if (headItem.isShow())
						headItem.setEnabled(true);
				} else {
					if (headItem.isShow())
						headItem.setEnabled(false);
				}
			}
		} else { // 非辅计量管理
			getBillCardPanel().getHeadItem("bfixed").setValue("Y"); // 设为“固定”，避免出现null
			if (isFromPU()) {
				// 报检辅数量
				headItem = getBillCardPanel().getHeadItem("nassistchecknum");
				if (headItem.isShow())
					headItem.setEnabled(false);
				// 辅单位
				headItem = getBillCardPanel().getHeadItem("cassistunitid");
				if (headItem.isShow())
					headItem.setEnabled(false);
				// 换算率
				headItem = getBillCardPanel().getHeadItem("nexchangerate");
				if (headItem.isShow())
					headItem.setEnabled(false);
			}
		}

		Object lobj_bheadfix = (String) getBillCardPanel().getHeadItem("bfixed").getValueObject();
		// 表体的换算率和辅数量
		for (int j = 0; j < sCheckStandards.length; j++) {
			for (int i = 0; i < getBillCardPanel().getRowCount("table" + String.valueOf(j)); i++) {
				Object lobj_bchange = getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i, "bchange");
				if (lobj_bchange == null || lobj_bchange.toString().trim().length() == 0) {
					lobj_bchange = new UFBoolean(false);
				}
				// getBillCardPanel().setCellEditable(i, "ccheckstatename",
				// false, "table" + String.valueOf(j));
				getBillCardPanel().setCellEditable(i, "ccheckstandardname", false, "table" + String.valueOf(j));
				getBillCardPanel().setCellEditable(i, "ccheckstateid", false, "table" + String.valueOf(j));
				getBillCardPanel().setCellEditable(i, "ccheckstandardid", false, "table" + String.valueOf(j));
				boolean lb_bchange = (new UFBoolean(lobj_bchange.toString())).booleanValue();

				if (lb_bchange) { // 选中是否改判{

					// 设置副数量和辅计量是否可编辑
					Object lobj_bassunitmanagedb = getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i,
							"bassunitmanagedb");

					if (lobj_bassunitmanagedb == null || lobj_bassunitmanagedb.toString().trim().length() == 0) {
						lobj_bassunitmanagedb = new UFBoolean(false);
					}

					boolean lb_bassunitmanagedb = (new UFBoolean(lobj_bassunitmanagedb.toString())).booleanValue();

					if (lb_bassunitmanagedb) {
						getBillCardPanel().setCellEditable(i, "nassistnum", true, "table" + String.valueOf(j));
						getBillCardPanel().setCellEditable(i, "cchgassistunitname", true, "table" + String.valueOf(j));
					} else {
						getBillCardPanel().setCellEditable(i, "nassistnum", false, "table" + String.valueOf(j));
						getBillCardPanel().setCellEditable(i, "cchgassistunitname", false, "table" + String.valueOf(j));
					}

					// 设置是否固定换算率和换算率是否可编辑
					Object lobj_cchgassistunitid = getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i,
							"cchgassistunitid");
					Object lobj_cchgbaseid = getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i,
							"cchgbaseid");
					if (lobj_cchgassistunitid == null || lobj_cchgassistunitid.toString().trim().length() == 0
							|| lobj_cchgbaseid == null || lobj_cchgbaseid.toString().trim().length() == 0) {
						// 如果存在lobj_cchgassistunitid 或 lobj_cchgbaseid
						// 为空的情况，则“固定换算率”且换算率不可编辑
						getBillCardPanel().getBillModel("table" + String.valueOf(j)).setValueAt(new UFBoolean(true), i, "bfixedb");
						getBillCardPanel().setCellEditable(i, "nexchangerateb", false, "table" + String.valueOf(j));
					} else {

						String lstr_cchgassistunitid = lobj_cchgassistunitid.toString().trim();
						String lstr_cchgbaseid = lobj_cchgbaseid.toString().trim();
						Object temp[][] = nc.ui.scm.pub.CacheTool.getAnyValue2("bd_convert", "fixedflag", "pk_measdoc='"
								+ lstr_cchgassistunitid + "' and pk_invbasdoc='" + lstr_cchgbaseid + "'");
						if (temp != null && temp.length > 0 && temp[0] != null && temp[0].length > 0 && temp[0][0] != null) {
							getBillCardPanel().getBillModel("table" + String.valueOf(j)).setValueAt(temp[0][0], i, "bfixedb");

							boolean lb_bodyfix = (new UFBoolean(temp[0][0].toString().trim())).booleanValue();
							if (lb_bodyfix) {
								getBillCardPanel().setCellEditable(i, "nexchangerateb", false, "table" + String.valueOf(j));
							} else {
								getBillCardPanel().setCellEditable(i, "nexchangerateb", true, "table" + String.valueOf(j));
							}
						} else {
							getBillCardPanel().getBillModel("table" + String.valueOf(j))
									.setValueAt(new UFBoolean(true), i, "bfixedb");
							getBillCardPanel().setCellEditable(i, "nexchangerateb", false, "table" + String.valueOf(j));
						}

					}

					getBillCardPanel().setCellEditable(i, "cchginventorycode", true, "table" + String.valueOf(j));

				} else { // 没有改判的是时候根据表头来决定
					getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i, "bfixedb");

					// 换算率是否可编辑
					if ((new UFBoolean(lobj_bheadfix.toString().trim())).booleanValue()) {
						getBillCardPanel().setCellEditable(i, "nexchangerateb", false, "table" + String.valueOf(j));
					} else {
						getBillCardPanel().setCellEditable(i, "nexchangerateb", true, "table" + String.valueOf(j));
					}

					// 辅数量是否可编辑
					if (s1 != null) { // 表头为辅计量管理
						getBillCardPanel().setCellEditable(i, "nassistnum", true, "table" + String.valueOf(j));
					} else {
						getBillCardPanel().setCellEditable(i, "nassistnum", false, "table" + String.valueOf(j));
					}

					getBillCardPanel().setCellEditable(i, "cchginventorycode", false, "table" + String.valueOf(j));
				}

				getBillCardPanel().setCellEditable(i, "vstobatchcode", true, "table" + String.valueOf(j));
			}
		}
		if ((m_grandVOs == null || m_grandVOs.length == 0) && m_checkBillVOs != null) {
			m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();

			for (int j = 0; j < sCheckStandards.length; j++) {
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckstateid");
				if (headItem.isShow())
					headItem.setEnabled(false);
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckstandardid");
				if (headItem.isShow())
					headItem.setEnabled(false);
				UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckstateid")
						.getComponent();
				String sID = nRefPane.getRefPK();
				nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckstatename")
						.getComponent();
				String sWhere = null;
				if (sID != null && sID.trim().length() > 0) {
					sWhere = " ccheckstateid = '" + sID + "' ";
					nRefPane.getRefModel().setWherePart(sWhere);
				} else {
					sWhere = " pk_corp = '" + m_sUnitCode + "' and dr = 0 ";
					nRefPane.getRefModel().setWherePart(sWhere);
				} // 根据报检部门，设置报检人参照

				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "csourcebilltypecode");
				if (headItem.isShow())
					headItem.setEnabled(false);
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "csourcebillcode");
				if (headItem.isShow())
					headItem.setEnabled(false);
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "csourcebillid");
				if (headItem.isShow())
					headItem.setEnabled(false);
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "csourcebillrowid");
				if (headItem.isShow())
					headItem.setEnabled(false);
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "csourcebillrowno");
				if (headItem.isShow())
					headItem.setEnabled(false);
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "csourcebilltypename");
				if (headItem.isShow())
					headItem.setEnabled(false);

				// 是否合格品不可编辑
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "bqualified");
				if (headItem.isShow())
					headItem.setEnabled(true);

				// 不合格项目不可编辑
				headItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckitemid");
				if (headItem.isShow())
					headItem.setEnabled(false);
				// 检验时间
				BillItem bodyItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "cb_tchecktime");
				if (bodyItem.isShow()) {
					bodyItem.setEdit(false);
				}
				// 批次号档案自定义项
				for (int i = 1; i < 21; i++) {
					bodyItem = getBillCardPanel().getBodyItem("table" + String.valueOf(j), "cb_vuserdef" + i);
					if (bodyItem.isShow()) {
						bodyItem.setEdit(true);
					}
				}
				UIRefPane nRefPane1 = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckstateid")
						.getComponent();
				String sid = nRefPane1.getRefPK();
				nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(j), "ccheckstatename")
						.getComponent();
				String strWhere = null;
				if (sid != null && sid.trim().length() > 0) {
					sWhere = " ccheckstateid = '" + sid + "' ";
					nRefPane.getRefModel().setWherePart(strWhere);
				} else {
					sWhere = " pk_corp = '" + m_sUnitCode + "' and dr = 0 ";
					nRefPane.getRefModel().setWherePart(strWhere);
				}
				getBillCardPanel().getBillModel("table" + String.valueOf(j)).execLoadFormula();
			}
			headItem = getBillCardPanel().getHeadItem("ccheckstandardid");
			if (headItem != null) {
				headItem.setEnabled(false);
			}
			headItem = getBillCardPanel().getHeadItem("ccheckstandardname");
			if (headItem != null) {
				headItem.setEnabled(false);
			}
			getBillCardPanel().updateValue();
			getBillCardPanel().updateUI();
			return;
		}
	}

	/**
   * 此处插入方法说明。 功能描述:页签切换时，调用该方法 输入参数: 返回值: 异常处理: 日期:2002/04/18
   */
	public void stateChanged(javax.swing.event.ChangeEvent event) {

		if (m_bMMQuery) {

			if ((UITabbedPane) event.getSource() == getTabbedPaneForMMQuery()) {
				if (getTabbedPaneForMMQuery().getSelectedIndex() == 0) {
					// 检验单
					this.setButtons(buttonManager.m_btnListMMQuery);
					m_nUIStateMMQuery = 0;
				} else if (getTabbedPaneForMMQuery().getSelectedIndex() == 1) {

					// 检验信息
					this.setButtons(buttonManager.m_btnInfoMMQuery);
					m_nUIStateMMQuery = 2;

					m_nInfoRecordForMMQuery = 0;

					getBillCardPanelMMQuery().setEnabled(false);

					// 显示检验信息
					m_grandVOsForMMQuery = (CheckbillB2VO[]) m_checkBillVOsForMMQuery[m_nBillRecordForMMQuery].getGrandVO();
					if (m_grandVOsForMMQuery != null && m_grandVOsForMMQuery.length > 0) {
						getBillCardPanelMMQuery().setBillValueVO(m_grandVOsForMMQuery[0]);
						getBillCardPanelMMQuery().getBillModel().execLoadFormula();
						displayCheckResultForInfoCardMMQuery();
					} else {
						getBillCardPanelMMQuery().getBillData().clearViewData();
					}

					getBillCardPanelMMQuery().getBillModel().updateValue();
					getBillCardPanelMMQuery().updateUI();

					if (m_grandVOsForMMQuery == null || m_grandVOsForMMQuery.length == 0) {
						// 除返回外，其余为灰
						buttonManager.m_btnReturnMMQuery.setEnabled(true);
						buttonManager.m_btnInfoPreviousMMQuery.setEnabled(false);
						buttonManager.m_btnInfoNextMMQuery.setEnabled(false);

					} else if (m_grandVOsForMMQuery.length == 1) {
						// 除返回外，其余为灰
						buttonManager.m_btnReturnMMQuery.setEnabled(true);
						buttonManager.m_btnInfoPreviousMMQuery.setEnabled(false);
						buttonManager.m_btnInfoNextMMQuery.setEnabled(false);

					} else {
						// 上张为灰,其余正常
						buttonManager.m_btnReturnMMQuery.setEnabled(true);
						buttonManager.m_btnInfoPreviousMMQuery.setEnabled(false);
						buttonManager.m_btnInfoNextMMQuery.setEnabled(true);
					}
					updateButtons();

				}

			}

			return;
		}
		if ((UITabbedPane) event.getSource() == m_tabbedPane) {
			// 检验单页签*****************
			if (m_tabbedPane.getSelectedIndex() == 0) {
				// 检验单

				if (m_bInfoEmend) {
					if (MessageDialog.showYesNoDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
							"UPTC00102-000010")/*
                                   * @res "检验信息"
                                   */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000098")/*
                                                                                             * @res
                                                                                             * "检验信息已改变！是否保存"
                                                                                             */) == MessageDialog.ID_YES)
						onInfoSave();
					else {
						m_bInfoAdd = false;
						m_bInfoEdit = false;
					}
				}

				if (m_bInfoEdit) {
					if (MessageDialog.showYesNoDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101",
							"UPTC00102-000010")/*
                                   * @res "检验信息"
                                   */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000098")/*
                                                                                             * @res
                                                                                             * "检验信息已改变！是否保存"
                                                                                             */) == MessageDialog.ID_YES)
						onInfoSave();
					else {
						m_bInfoAdd = false;
						m_bInfoEdit = false;
					}
				}

				// this.setButtons(m_btnCard);
				if (buttonManager.m_btnFlow != null) {
					this.setButtons(buttonManager.m_btnFlow);
					m_nUIState = 4;
					buttonManager.setButtonsStateCardWorkFlow();
				} else {
					// this.setButtons(m_btnCardDisplay);
					this.setButtons(buttonManager.getBtnTree().getButtonArray());
					m_nUIState = 0;
					displayCheckResultForInfoCard();
					getBillCardPanelInfo().getBillTable().getTableHeader().revalidate();
					getBillCardPanelInfo().getBillTable().getTableHeader().repaint();

					buttonManager.setButtonsStateCardModify();

				}

				// m_hButtonState = new Hashtable();

				// // 恢复检验单的按钮状态
				// if (m_hOldState != null) {
				// m_hButtonState = m_hOldState;
				// changeButtonState();
				// }

			} else if (m_tabbedPane.getSelectedIndex() == 1) {
				// 检验信息
				// 保存检验单的按钮状态
				// m_hOldState = m_hButtonState;

				// 如果检验方式，检验标准，检验状态组之一为空，则不能进入检验信息页签
				UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem("ccheckmodeid").getComponent();
				String pk = refPane.getRefPK();
				if (pk == null || pk.trim().length() == 0) {
					MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000099")/*
                                                                                                                         * @res
                                                                                                                         * "报检单"
                                                                                                                         */, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-000100")/*
                                                                                             * @res
                                                                                             * "检验方式不能为空！"
                                                                                             */);
					m_tabbedPane.setSelectedIndex(0);
					//
					return;
				}

				// this.setButtons(m_btnInfo);
				// this.setButtons(m_btnInfoDisplay);
				this.setButtons(buttonManager.m_btnInfo);
				m_nUIState = 2;
				// m_hButtonState = new Hashtable();

				// 显示检验信息
				if (m_bBillAdd && (!m_bInfoDel)) {
					// 检验单增加，检验信息无删除操作
					if (m_grandVOs != null && m_grandVOs.length > 0) {
						getBillCardPanelInfo().setBillValueVO(m_grandVOs[0]);
						m_nInfoRecord = 0;
						getBillCardPanelInfo().getBillModel().execLoadFormula();
						displayCheckResultForInfoCard();// 自动判断检验结果并显示
						// displayCheckInfo();
					} else {
						reBuildGroupColumn();
						getBillCardPanelInfo().getBillTable().getTableHeader().revalidate();
						getBillCardPanelInfo().getBillTable().getTableHeader().repaint();
						getBillCardPanelInfo().getBillTable().revalidate();
						getBillCardPanelInfo().getBillTable().repaint();
						// getBillCardPanelInfo().repaint();
						getBillCardPanelInfo().updateUI();
						getBillCardPanelInfo().getBillData().clearViewData();
					}

				} else if (m_bBillEdit && (!m_bInfoDel)) {
					// 检验单修改，检验信息无删除操作
					if (m_grandVOs == null || m_grandVOs.length == 0) {
						m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();
					}

					if (m_grandVOs != null && m_grandVOs.length > 0) {
						reBuildGroupColumn();
						getBillCardPanelInfo().setBillValueVO(m_grandVOs[0]);
						m_nInfoRecord = 0;
						getBillCardPanelInfo().getBillModel().execLoadFormula();
						displayCheckResultForInfoCard();// 自动判断检验结果并显示
					} else {
						getBillCardPanelInfo().getBillData().clearViewData();
					}

				} else if (!m_bInfoDel) {
					// 检验单浏览，检验信息无删除操作
					if (m_grandVOs == null || m_grandVOs.length == 0) {
						m_grandVOs = (CheckbillB2VO[]) m_checkBillVOs[m_nBillRecord].getGrandVO();
					}
					reBuildGroupColumn();
					if (m_grandVOs != null && m_grandVOs.length > 0) {
						getBillCardPanelInfo().setEnabled(false);
						getBillCardPanelInfo().setBillValueVO(m_grandVOs[0]);
						// getBillCardPanelInfo().getBodyValueAt(0, "icheckstandard2");
						m_nInfoRecord = 0;
						getBillCardPanelInfo().getBillModel().execLoadFormula();
						displayCheckResultForInfoCard();// 自动判断检验结果并显示
					} else {
						getBillCardPanelInfo().getBillData().clearViewData();
					}
					getBillCardPanelInfo().getBillTable().getTableHeader().revalidate();
					getBillCardPanelInfo().getBillTable().getTableHeader().repaint();
					getBillCardPanelInfo().getBillTable().revalidate();
					getBillCardPanelInfo().getBillTable().repaint();
					// getBillCardPanelInfo().repaint();
					getBillCardPanelInfo().updateUI();
				} else if (m_bInfoDel) {
					// 检验信息有删除操作
					if (m_grandVOs != null && m_grandVOs.length > 0) {
						getBillCardPanelInfo().setBillValueVO(m_grandVOs[0]);
						m_nInfoRecord = 0;
						getBillCardPanelInfo().getBillModel().execLoadFormula();
						displayCheckResultForInfoCard();// 自动判断检验结果并显示
					} else {
						getBillCardPanelInfo().getBillData().clearViewData();
					}
				}

				refreshInfoUnitName();

				getBillCardPanelInfo().getBillModel().updateValue();
				getBillCardPanelInfo().updateUI();

				// 按钮状态
				if (!m_bInfoEdit) {

					buttonManager.setButtonsStateCardInfo();

					// add by yye end 2005-03-15
				}

				// 检验单页签*****************

			} else if (m_condClient != null) {
				// 查询对话框页签*****************
				if (m_condClient.getUITabbedPane().getSelectedIndex() == 1) {
					// 获得多公司编码
					String sUnitCodes[] = m_condClient.getSelectedCorpIDs();
					String s = null;
					if (sUnitCodes == null || sUnitCodes.length == 0) {
						s = m_sUnitCode;
					} else {
						for (int i = 0; i < sUnitCodes.length; i++) {
							s = sUnitCodes[i];
							break;
						}
					}

					// 改变部门参照过滤
					m_deptDef.setPk_corp(s, true);

					// 改变存货参照过滤
					m_invDef.setPk_corp(s, true);

					// 改变操作员参照过滤
					m_operatorDef.setPk_corp(s, true);

					// 改变检验方式参照过滤
					m_modeDef.setWherePart(s);

					// 改变检验标准参照过滤
					m_standardDef.setWherePart(s);
				}

				// 查询对话框页签*****************
			}
		}
	}

	/**
   * 解决检验单位自动清空 原因：数据库里的Unitid存的是名称
   * 
   */
	private void refreshInfoUnitName() {
		/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */
		BillModel infoBM = getBillCardPanelInfo().getBillModel();
		if (m_grandVOs != null && m_grandVOs.length > 0) {
			CheckbillB2ItemVO[] bodyVO = (CheckbillB2ItemVO[]) m_grandVOs[m_nInfoRecord].getChildrenVO();
			for (int i = 0, len = infoBM.getRowCount(); i < len; i++)
				if (infoBM.getValueAt(i, "ccheckunitname") == null)
					infoBM.setValueAt(bodyVO[i].getCcheckunitid(), i, "ccheckunitname");
		}
		/** ****20051128-广东德美-增加检验项目单位自选值-zhongwei********** */
	}

	private void reBuildGroupColumn() {
		// 组织多表头
		Vector<ColumnGroup> vheader = new Vector<ColumnGroup>();
		String[] strStandardNames = new String[sCheckStandards.length];
		// 单标准，就不建多表头
		for (int k = 0; k < sCheckStandards.length && sCheckStandards.length > 1; k++) {
			Object[] obName = null;
			try {
				obName = (Object[]) CacheTool.getCellValue("qc_checkstandard", "ccheckstandardid", "ccheckstandardname",
						sCheckStandards[k]);
			} catch (Exception e) {
				SCMEnv.out(e);
				QcTool.outException(this, e);
			}
			strStandardNames[k] = obName[0].toString();
			ColumnGroup cg1 = new ColumnGroup("检验标准:" + strStandardNames[k]/*-=notranslate=-*/);
			if (k == 0) {
				getBillCardPanelInfo().showBodyTableCol("cstandardvalue");
				getBillCardPanelInfo().showBodyTableCol("ccheckresult");
				getBillCardPanelInfo().showBodyTableCol("icheckstandard");
				TableColumn col = getBillCardPanelInfo().getBodyPanel().getShowCol("cstandardvalue");
				cg1.add(col);
				col = getBillCardPanelInfo().getBodyPanel().getShowCol("ccheckresult");
				cg1.add(col);
				col = getBillCardPanelInfo().getBodyPanel().getShowCol("icheckstandard");
				cg1.add(col);
				vheader.add(cg1);
			} else {
				getBillCardPanelInfo().showBodyTableCol("cstandardvalue" + String.valueOf(k + 1));
				getBillCardPanelInfo().showBodyTableCol("ccheckresult" + String.valueOf(k + 1));
				getBillCardPanelInfo().showBodyTableCol("icheckstandard" + String.valueOf(k + 1));
				getBillCardPanelInfo().getBodyItem("cstandardvalue" + String.valueOf(k + 1)).setEnabled(false);
				getBillCardPanelInfo().getBodyItem("icheckstandard" + String.valueOf(k + 1)).setEnabled(false);
				TableColumn col = getBillCardPanelInfo().getBodyPanel().getShowCol("cstandardvalue" + String.valueOf(k + 1));
				cg1.add(col);
				col = getBillCardPanelInfo().getBodyPanel().getShowCol("ccheckresult" + String.valueOf(k + 1));
				cg1.add(col);
				col = getBillCardPanelInfo().getBodyPanel().getShowCol("icheckstandard" + String.valueOf(k + 1));
				cg1.add(col);
				vheader.add(cg1);
			}
		}
		for (int j = 10; j > sCheckStandards.length; j--) {
			getBillCardPanelInfo().hideBodyTableCol("cstandardvalue" + String.valueOf(j));
			getBillCardPanelInfo().hideBodyTableCol("ccheckresult" + String.valueOf(j));
			getBillCardPanelInfo().hideBodyTableCol("icheckstandard" + String.valueOf(j));
		}
		// 先清除以前设置的多表头
		((nc.ui.pub.beans.table.GroupableTableHeader) getBillCardPanelInfo().getBillTable().getTableHeader())
				.clearColumnGroups();
		getBillCardPanelInfo().getBillTable().getTableHeader().revalidate();
		getBillCardPanelInfo().getBillTable().getTableHeader().repaint();
		// 重新加载多表头
		for (int i = 0; i < vheader.size(); i++) {
			((nc.ui.pub.beans.table.GroupableTableHeader) getBillCardPanelInfo().getBillTable().getTableHeader())
					.addColumnGroup((ColumnGroup) vheader.get(i));
		}
	}

	/**
   * 此处插入方法说明。 功能描述:处理列表选择 输入参数: 返回值: 异常处理: 日期:2002/09/26
   */
	public void valueChanged(javax.swing.event.ListSelectionEvent event) {
		if ((ListSelectionModel) event.getSource() == getBillListPanel().getHeadTable().getSelectionModel()) {
			int nRow = getBillListPanel().getHeadBillModel().getRowCount();

			// 表头所有行恢复正常（不选择）
			for (int i = 0; i < nRow; i++) {
				getBillListPanel().getHeadBillModel().setRowState(i, BillModel.NORMAL);
			}

			// 获得表头选择行数
			int nSelected[] = getBillListPanel().getHeadTable().getSelectedRows();
			if (nSelected != null && nSelected.length > 0) {
				for (int i = 0; i < nSelected.length; i++) {
					int j = nSelected[i];
					if (j < nRow) {
						getBillListPanel().getHeadBillModel().setRowState(j, BillModel.SELECTED);
					}
				}
			}

			// 根据选择数目,改变按钮状态
			int n = 0;
			for (int i = 0; i < getBillListPanel().getHeadBillModel().getRowCount(); i++) {
				if (getBillListPanel().getHeadBillModel().getRowState(i) == BillModel.SELECTED) {
					n++;
				}
			}
			// since v53, 按钮逻辑重构, by Czp
			buttonManager.setButtonsStateList();

		}
	}

	/** 为二次开发提供接口 ********************************** */

	/**
   * 创建日期： 2005-9-20 功能描述： 获取扩展按钮数组（只提供卡片界面按钮）
   */
	public ButtonObject[] getExtendBtns() {
		return null;
	}

	/**
   * 创建日期： 2005-9-20 功能描述： 控制扩展按钮的事件
   */
	public void onExtendBtnsClick() {//
	}

	public Object[] getRelaSortObjectArray() {
		return m_checkBillVOs;
	}

	/**
   * 父类方法重写
   * 
   * @see nc.ui.pub.bill.BillEditListener2#beforeEdit(nc.ui.pub.bill.BillEditEvent)
   */
	public boolean beforeEdit(BillEditEvent e) {
		int index = getBillCardPanel().getBodyTabbedPane().getSelectedIndex();
		if (e.getKey().equals("ccheckstatename")) {
			UIRefPane nRefPane = (UIRefPane) getBillCardPanel().getBodyItem("table" + String.valueOf(index),
					"ccheckstatename").getComponent();
			String sid = (String) getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(e.getRow(),
					"ccheckstateid");
			String sWhere = " ccheckstateid = '" + sid + "' ";
			nRefPane.getRefModel().setWherePart(sWhere);
		}
		if (e.getKey().trim().equals("vstobatchcode")) {
			return beforeBacthEdit(e);

		}
		if (e.getKey().trim().equalsIgnoreCase("cresult")) {
			beforeCresultEdit(e);
		}
		if (e.getKey().trim().equalsIgnoreCase("bchange") && isFromIC()) {
			return false;
		}
		if (e.getKey().trim().equalsIgnoreCase("nexchangerateb")) {
			if (getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(e.getRow(), "nexchangerateb") != null) {
				iNexchangrateb = (UFDouble) getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(
						e.getRow(), "nexchangerateb");
			}
		}
		if (e.getKey().trim().equalsIgnoreCase("cdefecttypename")) {
			boolean bQualified = (Boolean) getBillCardPanel().getBillModel("table" + String.valueOf(index)).getValueAt(
					e.getRow(), "bqualified");
			if (bQualified) {
				getBillCardPanel().getBodyItem("table" + String.valueOf(index), "cdefecttypename").setEnabled(false);
			}
		}
		if ("inorder".equals(e.getKey())) {
			return false;
		}
		// since v53, 库存检验单表体可编辑逻辑走此处控制(其它报检点可编辑逻辑仍走：setModifyEditState())
		if (!isFromIC()) {
			return true;
		}
		// 库存报检，表体不支持修改内容(特别){数量、辅数量、入库批次号根据批次现存量自动带入}
		if ("nnum".equals(e.getKey()) || "nassistnum".equals(e.getKey()) || "nexchangerateb".equals(e.getKey())
				|| "vstobatchcode".equals(e.getKey()) || "cchgassistunitname".equals(e.getKey())
				|| "inorder".equals(e.getKey())) {
			return false;
		}

		return true;
	}

	/**
   * 检验信息，检验实际值编辑前事件
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param event
   *          <p>
   * @author liyc
   * @time 2007-9-20 上午10:35:18
   */
	private void beforeCresultEdit(BillEditEvent event) {

		((UIRefPane) getBillCardPanelInfo().getBodyItem("cresult").getComponent()).getUITextField().setTextType(
				UITextType.TextStr);
		((UITextDocument) ((UIRefPane) getBillCardPanelInfo().getBodyItem("cresult").getComponent()).getUITextField()
				.getDocument()).setChange(UITextType.TextStr, 10, 8, "", 0, Double.parseDouble("1000000"), Double
				.parseDouble("1000000"), true, true, true, true, 0);

	}

	/**
   * 传入存货和仓库需要的参数
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param e
   *          事件元素
   * @return
   *          <p>
   * @author liyc
   * @time 2007-8-17 上午11:21:14
   */
	private boolean beforeBacthEdit(BillEditEvent e) {
		if (isFromIC()) {
			return false;
		}
		String cmangid = (String) getBillCardPanel().getHeadItem("cmangid").getValueObject();
		Object lobj_bchange = null;
		if (cmangid != null && cmangid.trim().length() > 0) {
			boolean lb_isHeadBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(cmangid);

			lobj_bchange = getBillCardPanel().getBodyValueAt(e.getRow(), "bchange");
			boolean lb_bchange = false;// 是否改判了
			if (lobj_bchange != null && lobj_bchange.toString().trim().length() > 0) {
				lb_bchange = (new UFBoolean(lobj_bchange.toString())).booleanValue();
			}

			boolean lbol_RowBatchManaged = false;// 综合判断的该行是否批次管理
			if (lb_bchange) {// 如果改判了
				Object lobj_cchgmangid = getBillCardPanel().getBodyValueAt(e.getRow(), "cchgmangid");
				if (lobj_cchgmangid != null) {
					lbol_RowBatchManaged = nc.ui.scm.inv.InvTool.isBatchManaged(lobj_cchgmangid.toString().trim());
				}
			} else {
				lbol_RowBatchManaged = lb_isHeadBatchManaged;
			}

			if (lbol_RowBatchManaged) {// 如果为批次管理存货行
				getBillCardPanel().setCellEditable(e.getRow(), "vstobatchcode", true);
			} else {
				getBillCardPanel().setCellEditable(e.getRow(), "vstobatchcode", false);
				return false;
			}

		}

		// //判断一存货是否批次管理
		// String cmangid = (String) getBillCardPanel().getHeadItem(
		// "cmangid").getValueObject();
		// if (!PuTool.isBatchManaged(cmangid)){
		// return ;
		// }
		int iRow = e.getRow();
		// 组织需要查询的数据对应的itemkey
		ParaVOForBatch vo = new ParaVOForBatch();
		// 传入FieldName
		// 存货管理档案ID
		if (lobj_bchange != null && ((Boolean) lobj_bchange).booleanValue()) {
			vo.setMangIdField("cchgmangid");
			vo.setInvCodeField("cchginventorycode");
			vo.setInvNameField("cchginventoryname");
		} else {
			vo.setMangIdField("cmangid");
			vo.setInvCodeField("cinventorycode");
			vo.setInvNameField("cinventoryname");
		}
		vo.setSpecificationField("cinventoryspec");
		vo.setInvTypeField("cinventorytype");
		vo.setMainMeasureNameField("cchgmeasdocname");
		vo.setAssistUnitIDField("cassistunit");
		vo.setIsAstMg(new UFBoolean(QcTool.isAssUnitManaged((String) getBillCardPanel().getBodyValueAt(iRow, "cbaseid"))));
		// 仓库ID
		vo.setWarehouseIDField("cwarehouseid");
		vo.setFreePrefix("vfree");
		// 设置卡片模板,公司等
		vo.setCardPanel(getBillCardPanel());
		vo.setPk_corp(getCorpPrimaryKey());
		vo.setEvent(e);

		try {
			// 组织得到数据VO
			CircularlyAccessibleValueObject[] voaParaLot = get2VOsForLotNumRefPane(vo);

			LotNumbRefPane refPane = (LotNumbRefPane) vo.getCardPanel().getBodyItem("vstobatchcode").getComponent();
			refPane.setIsMutiSel(true);
			// 置入批次参照VO
			refPane.setParameter((WhVO) voaParaLot[0], (InvVO) voaParaLot[1]);

		} catch (Exception exp) {
			QcTool.outException(this, exp);
		}

		return true;

	}

	private String getBillTemplet(String strBilltemplet) {
		if (strBilltemplet.equalsIgnoreCase("C0010101000000000001")) {
			return "WR";
		} else if (strBilltemplet.equalsIgnoreCase("C0010101000000000002")) {
			return "WH";
		} else if (strBilltemplet.equalsIgnoreCase("C0010101000000000003")) {
			return "WQ";
		} else if (strBilltemplet.equalsIgnoreCase("C0010101000000000004")) {
			return "WP";
		} else if (strBilltemplet.equalsIgnoreCase("C0010101000000000005")) {
			return "WS";
		}
		return null;
	}

	/**
   * 组织库存批次档案参照需要的存货和仓库参数。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * 
   * @param ParaVOForBatch
   *          para 需要从模板上取到的数据值
   * @return
   * @throws ValidationException
   *           <p>
   * @author liyc
   * @time 2007-8-17 上午11:21:47
   */
	private CircularlyAccessibleValueObject[] get2VOsForLotNumRefPane(ParaVOForBatch para) throws ValidationException {
		// 合法性校验；
		try {
			para.validate();
		} catch (ValidationException e) {
			throw e;
		}
		// 取得需要处理数据的表体行
		BillCardPanel panel = para.getCardPanel();
		int row = para.getEvent().getRow();
		String cmangid = null;
		if (panel.getBodyValueAt(row, "bchange") != null && ((Boolean) panel.getBodyValueAt(row, "bchange")).booleanValue()) {
			cmangid = (String) panel.getBodyValueAt(row, "cchgmangid");
		}
		// 管理ID
		else {
			cmangid = (String) panel.getHeadItem(para.getMangIDField()).getValueObject();
		}
		if (cmangid == null || cmangid.length() <= 0)
			return null;
		InvVO invVO = new InvVO();

		// 组织仓库信息
		String cwhid = (String) panel.getHeadItem(para.getWarehouseIDField()).getValueObject();

		String strcstoreorganization = (String) getBillCardPanel().getHeadItem("cstoreorganization").getValueObject();
		WhVO whvo = new WhVO();
		if (cwhid != null || strcstoreorganization != null) {
			UIRefPane refPane = new UIRefPane();
			refPane.setRefNodeName("仓库档案"/*-=notranslate=-*/);
			refPane.setValue(cwhid);
			refPane.setPK(cwhid);
			// refPane.getRefModel().reloadData();
			whvo.setCwarehouseid(cwhid);
			whvo.setCwarehousecode(refPane.getRefCode());
			whvo.setCwarehousename(refPane.getRefName());
			whvo.setIsWasteWh(new Integer(0));
			whvo.setPk_corp(para.getPk_corp());
			whvo.setIsWasteWh(new Integer(0));
			whvo.setPk_corp(para.getPk_corp());
			// }
			whvo.setPk_calbody(strcstoreorganization);
			BillItem item = panel.getHeadItem("cstoreorganization");
			if (item != null && item.getComponent() != null)
				whvo.setVcalbodyname(((UIRefPane) item.getComponent()).getRefModel().getRefNameValue());
		}
		// 组织存货信息
		ArrayList listI = new ArrayList();
		listI.add(cmangid);
		ArrayList retList = null;
		try {
			ArrayList allList = new ArrayList();
			allList.add(listI);
			// 根据存货管理ID自由项属性
			retList = DefHelper.queryFreeVOByInvIDsGroupByBills(allList);
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(NCLangRes.getInstance().getStrByID("c0qcpub", "UPPC0PUB-000015", null, new String[]{e.getMessage()})/*错误信息:{0}*/);
		}

		if (retList != null) {
			ArrayList freeList = (ArrayList) (retList.get(0));
			FreeVO freeVO = (FreeVO) freeList.get(0);

			// 是否自由项管理
			if (freeVO == null
					|| ((freeVO.getVfreename1() == null || freeVO.getVfreename1().length() < 1)
							&& (freeVO.getVfreename2() == null || freeVO.getVfreename2().length() < 1)
							&& (freeVO.getVfreename3() == null || freeVO.getVfreename3().length() < 1)
							&& (freeVO.getVfreename4() == null || freeVO.getVfreename4().length() < 1) && (freeVO.getVfreename5() == null || freeVO
							.getVfreename5().length() < 1))) {

				// 非自由项管理
				invVO.setIsFreeItemMgt(new Integer(0));
			} else {
				// 自由项管理
				invVO.setIsFreeItemMgt(new Integer(1));
				// 自由项
				freeVO.setCinventoryid(cmangid);
				for (int i = 1; i < 6; i++) {
					Object oTemp = panel.getBodyValueAt(row, para.getFreePrefix() + i);
					freeVO.setAttributeValue("vfree" + i, oTemp == null ? null : oTemp.toString());
				}
				// 设置FreeVO
				invVO.setFreeItemVO(freeVO);

			}
		} else {
			// 非自由项管理
			invVO.setIsFreeItemMgt(new Integer(0));
		}

		// 存货管理ID
		invVO.setCinventoryid(cmangid);

		// 存货编码
		// Object strTemp = panel.getHeadItem(
		// para.getInvCodeField()).getValueObject();
		// invVO.setCinventorycode(strTemp == null ? null : strTemp.toString());
		Object strTemp = null;
		if (panel.getBodyValueAt(row, "bchange") != null && ((Boolean) panel.getBodyValueAt(row, "bchange")).booleanValue()) {
			// 改判存货名称
			invVO.setInvname(panel.getBodyValueAt(row, "cchginventoryname").toString());
			invVO.setCinventorycode(panel.getBodyValueAt(row, "cchginventorycode").toString());
		} else {
			// 存货名称
			strTemp = panel.getHeadItem(para.getInvNameField()).getValueObject();
			invVO.setInvname(strTemp == null ? null : strTemp.toString());
		}
		// 规格
		strTemp = panel.getHeadItem(para.getSpecificationField()).getValueObject();
		invVO.setInvspec(strTemp == null ? null : strTemp.toString());
		// 型号
		strTemp = panel.getHeadItem(para.getInvTypeField()).getValueObject();
		invVO.setInvtype(strTemp == null ? null : strTemp.toString());
		// 主计量
		strTemp = panel.getBodyValueAt(row, para.getMainMeasureNameField());
		invVO.setMeasdocname(strTemp == null ? null : strTemp.toString());
		// 是否辅计量管理
		UFBoolean flag = para.isAstMg();
		if (flag == null)
			flag = new UFBoolean(false);
		strTemp = (flag.booleanValue() ? new Integer(1) : new Integer(0));
		// 组织存货信息
		invVO.setIsAstUOMmgt((Integer) strTemp);
		invVO
				.setCastunitid((String) panel.getBillModel().getValueAt(para.getEvent().getRow(), para.getAssistUnitIDField()));

		return new CircularlyAccessibleValueObject[] { whvo, invVO };
	}

	/** 为二次开发提供接口 ********************************** */

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == getBillListPanel().getHeadTable() && e.getClickCount() == m_doubleClick) {
			onListSwitch();
		}

	}

	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	public void mouseExited(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	public void mousePressed(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成方法存根

	}

	/**
   * 置入信息
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author liyc
   * @time 2007-9-24 上午11:21:52
   */
	private void setValues() {
		// 设置存货编码
		UIRefPane refPane = (UIRefPane) getBillCardPanel().getHeadItem("cinventorycode").getComponent();
		String s = refPane.getRefCode();
		UITextField text = refPane.getUITextField();
		text.setText(s);

		refPane = (UIRefPane) getBillCardPanel().getHeadItem("vproductordercode").getComponent();
		s = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getVproductordercode();
		text = refPane.getUITextField();
		text.setText(s);

		// 设置备注
		refPane = (UIRefPane) getBillCardPanel().getHeadItem("vmemo").getComponent();
		s = ((CheckbillHeaderVO) m_checkBillVOs[m_nBillRecord].getParentVO()).getVmemo();
		text = refPane.getUITextField();
		text.setText(s);
	}

	/**
   * 卡片界面置入来源单据信息
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author liyc
   * @time 2007-9-24 上午11:21:30
   */
	private void setCardSourceBillInfo() {
		if (sCheckStandards != null && sCheckStandards.length > 0) {
			for (int j = 0; j < sCheckStandards.length; j++) {
				for (int i = 0; i < getBillCardPanel().getRowCount("table" + String.valueOf(j)); i++) {
					if (getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i, "csourcebillrowid") != null
							&& getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i, "csourcebillrowid")
									.toString().trim().length() > 0) {
						String ls_csourcebillrowid = getBillCardPanel().getBillModel("table" + String.valueOf(j)).getValueAt(i,
								"csourcebillrowid").toString().trim();
						if (m_HTSourcebillInfo != null) {
							String[] strSourceinfo = (String[]) m_HTSourcebillInfo.get(ls_csourcebillrowid);
							if (ls_csourcebillrowid != null && ls_csourcebillrowid.length() > 0 && strSourceinfo != null) {
								String strSourceBillTypeCode = strSourceinfo[0];
								String strSourceBillHID = strSourceinfo[1];
								String strSourceBillBID = strSourceinfo[2];
								getBillCardPanel().getBillModel("table" + String.valueOf(j)).setValueAt(strSourceBillTypeCode, i,
										"csourcebilltypename");
								getBillCardPanel().getBillModel("table" + String.valueOf(j)).setValueAt(strSourceBillHID, i,
										"csourcebillno");
								if (strSourceBillTypeCode.equalsIgnoreCase("库存解冻/冻结"/*-=notranslate=-*/)) {
									getBillCardPanel().getBillModel("table" + String.valueOf(j)).setValueAt(null, i, "csourcebillrowno");
								} else {
									getBillCardPanel().getBillModel("table" + String.valueOf(j)).setValueAt(strSourceBillBID, i,
											"csourcebillrowno");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
   * 列表界面置入来源单据信息
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author liyc
   * @time 2007-9-24 上午11:20:59
   */
	private void setListSourceBillInfo() {
		for (int i = 0; i < getBillListPanel().getBodyBillModel().getRowCount(); i++) {
			if (getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid") != null
					&& !getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid").toString().trim().equals("")) {
				String ls_csourcebillrowid = getBillListPanel().getBodyBillModel().getValueAt(i, "csourcebillrowid").toString()
						.trim();
				if (m_HTSourcebillInfo != null && m_HTSourcebillInfo.containsKey(ls_csourcebillrowid)) {
					String[] strSourceinfo = (String[]) m_HTSourcebillInfo.get(ls_csourcebillrowid);
					String strSourceBillTypeCode = strSourceinfo[0];
					String strSourceBillHID = strSourceinfo[1];
					String strSourceBillBID = strSourceinfo[2];
					getBillListPanel().getBodyBillModel().setValueAt(strSourceBillTypeCode, i, "csourcebilltypename");
					getBillListPanel().getBodyBillModel().setValueAt(strSourceBillHID, i, "csourcebillno");
					if (strSourceBillTypeCode.equalsIgnoreCase("库存解冻/冻结"/*-=notranslate=-*/)) {
						getBillListPanel().getBodyBillModel().setValueAt(null, i, "csourcebillrowno");
					} else {
						getBillListPanel().getBodyBillModel().setValueAt(strSourceBillBID, i, "csourcebillrowno");
					}
				}
			}
		}
	}

	public void doMaintainAction(ILinkMaintainData maintaindata) {
		if (maintaindata == null) {
			return;
		}

		String billID = maintaindata.getBillID();

		initDataAndPanel(billID);
	}

	public void doQueryAction(ILinkQueryData querydata) {
		if (querydata == null)
			return;

		m_bWorkFlow = true;

		try {
			String billType = querydata.getBillType();

			if (StringUtils.isEmpty(billType)) {
				throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001133")/*
                                                                                                                   * @res
                                                                                                                   * "来源单据类型为空！"
                                                                                                                   */);
			}

			String checkType = "";
			if ("WR".equals(billType)) {
				checkType = "WH";
			} else if ("WH".equals(billType)) {
				checkType = "23";
			} else if ("WQ".equals(billType)) {
				checkType = "A4";
			} else if ("WP".equals(billType)) {
				checkType = "3V";
			} else if ("WS".equals(billType)) {
				checkType = "4Z";
			}

			if (m_dialog == null) {
				m_dialog = new nc.ui.qc.type.ChecktypeDlg(getClientEnvironment().getDesktopApplet(), checkType);
			}

			try {
				ChecktypeVO checkTypeVO = CheckbillHelper.queryCheckType(checkType);
				if (checkTypeVO == null) {
					throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPPC0020101-001134")/*
                                                                                                                     * @res
                                                                                                                     * "来源单据类型为空！"
                                                                                                                     */);

				}
				m_dialog.setCheckbilltypecode(checkType);
				m_dialog.setCheckbillid(checkTypeVO.getPk_billtemplet_bill());
				m_dialog.setCheckinfoid(checkTypeVO.getPk_billtemplet_info());
				m_dialog.setChecktypeid(checkTypeVO.getCchecktypeid());
				m_dialog.setChecktypename(checkTypeVO.getCchecktypename());
				m_dialog.setSaveAudit(false);
			} catch (Exception e) {
				SCMEnv.out(e);
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC00103-000005")/*
                                                                                                     * @res
                                                                                                     * "联查"
                                                                                                     */+ e.getMessage());
			}

		} catch (BusinessException be) {
			SCMEnv.out(be);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance().getStrByID("C0020101", "UPTC00103-000005")/*
                                                                                                                   * @res
                                                                                                                   * "联查"
                                                                                                                   */, be.getMessage());

		}

		String billID = querydata.getBillID();

		initDataAndPanel(billID);
	}

	public void onMenuItemClick(ActionEvent e) {
		// 表体菜单项处理
		UIMenuItem menuItem = (UIMenuItem) e.getSource();
		if (menuItem.equals(getBillCardPanel().getAddLineMenuItem()))
			if (menuItem.equals(getBillCardPanel().getCopyLineMenuItem())) {
				onCardCopyLine();
			}

		if (menuItem.equals(getBillCardPanel().getDelLineMenuItem()))
			onCardDelLine();

		if (menuItem.equals(getBillCardPanel().getInsertLineMenuItem()))
			onCardInsertLine();

		if (menuItem.equals(getBillCardPanel().getPasteLineMenuItem()))
			onCardPasteLine();
		;
	}

	private void initDataAndPanel(String billID) {
		CheckbillVO VO = null;
		String[] lstrary_SourceBillBID = null;
		try {
			VO = CheckbillHelper.queryCheckbillByHeadKey(billID);

			if (VO != null) {
				strBilltemplate = getBillTemplet(VO.getHeadVo().getCchecktypeid());
				strBilltemplet = strBilltemplate;
				buttonManager.initButtons();
				initWorkflow();
				m_tabbedPane.setEnabledAt(1, true);
				m_checkBillVOs = new CheckbillVO[1];
				m_checkBillVOs[0] = VO;
				// add by yy begin取得单据行号信息
				Vector lvec_SourceBillTypeCode = new Vector();
				Vector lvec_SourceBillHID = new Vector();
				Vector lvec_SourceBillBID = new Vector();
				Vector orderData = new Vector(1000);
				for (int i = 0; i < m_checkBillVOs.length; i++) {
					CheckbillHeaderVO l_CheckbillHeaderVO = (CheckbillHeaderVO) m_checkBillVOs[i].getParentVO();
					if (i == 0) {
						// 设置自由项
						nc.ui.scm.pub.FreeVOParse freeVOParse = new nc.ui.scm.pub.FreeVOParse();
						freeVOParse.setFreeVO(new CheckbillHeaderVO[] { l_CheckbillHeaderVO }, "vfreeitem", "vfree", null,
								"cmangid", false);
					}
					CheckbillItemVO l_CheckbillItemVO[] = (CheckbillItemVO[]) m_checkBillVOs[i].getChildrenVO();
					if (l_CheckbillItemVO != null && l_CheckbillItemVO.length > 0) {

						for (int j = 0; j < l_CheckbillItemVO.length; j++) {
							l_CheckbillItemVO[j].setCinventoryid(l_CheckbillHeaderVO.getCinventorycode());
							if (l_CheckbillItemVO[j].getCsourcebilltypecode() != null
									&& l_CheckbillItemVO[j].getCsourcebillid() != null
									&& l_CheckbillItemVO[j].getCsourcebillrowid() != null) {
								lvec_SourceBillTypeCode.addElement(l_CheckbillItemVO[j].getCsourcebilltypecode());
								lvec_SourceBillHID.addElement(l_CheckbillItemVO[j].getCsourcebillid());
								lvec_SourceBillBID.addElement(l_CheckbillItemVO[j].getCsourcebillrowid());
							}
							if (l_CheckbillItemVO[j].getInorder().intValue() == 0) {
								orderData.add(0, l_CheckbillItemVO[j]);
							} else {
								// 重新过滤主检验标准
								orderData.add(l_CheckbillItemVO[j]);
							}
						}
						QcTool.execFormulaForBatchCode(l_CheckbillItemVO, getCorpPrimaryKey());
					}
					CheckbillItemVO[] vos = new CheckbillItemVO[orderData.size()];
					orderData.copyInto(vos);
					m_checkBillVOs[i].setChildrenVO(vos);
					orderData.clear();
				}
				String[] lstrary_SourceBillTypeCode = new String[lvec_SourceBillTypeCode.size()];
				String[] lstrary_SourceBillHID = new String[lvec_SourceBillHID.size()];
				lstrary_SourceBillBID = new String[lvec_SourceBillBID.size()];

				lvec_SourceBillTypeCode.copyInto(lstrary_SourceBillTypeCode);
				lvec_SourceBillHID.copyInto(lstrary_SourceBillHID);
				lvec_SourceBillBID.copyInto(lstrary_SourceBillBID);

				SourceBillInfoFinder l_SourceBillInfoFinder = new SourceBillInfoFinder();
				m_HTSourcebillInfo = l_SourceBillInfoFinder.getSourceBillInfo(lstrary_SourceBillTypeCode,
						lstrary_SourceBillHID, lstrary_SourceBillBID);
			} else {
				m_checkBillVOs = null;
				m_tabbedPane.setEnabledAt(1, false);
			}
			initDataPower();
		} catch (Exception e) {
			SCMEnv.out(e);
			return;
		}

		m_nBillRecord = 0;
		m_bBodyQuery = new UFBoolean[m_checkBillVOs.length];
		for (int i = 0; i < m_bBodyQuery.length; i++) {
			if (i == 0)
				m_bBodyQuery[i] = new UFBoolean(true);
			else
				m_bBodyQuery[i] = new UFBoolean(false);
		}
		/** V55有查询结果根据表体的检验标准加载多页签 */
		initMultiPanel(VO);
		QcTool.setValue(getBillCardPanel(), VO);
		bIsLinked = true;
		buttonManager.setButtonsStateCard();
		bIsLinked = false;
		setCardSourceBillInfo();
	}
	
	// begin ncm caosl
	private UFDate calcQualityDate(UFDate d, Integer qualityperiodunit, Integer qualitydaynum) {
		if (d == null || qualityperiodunit == null || qualitydaynum == null)
			return null;
		if (qualitydaynum.intValue() == 0)
			return d;
		if (InvVO.quality_day == qualityperiodunit.intValue()) {
			return d.getDateAfter(qualitydaynum.intValue());
		} else if (InvVO.quality_month == qualityperiodunit.intValue()) {
			int[] yearday = getAfterMonth(d.getYear(), d.getMonth(), qualitydaynum.intValue());
			if (yearday == null)
				return null;
			return getUFDate(yearday[0], yearday[1], d.getDay());
		} else if (InvVO.quality_year == qualityperiodunit.intValue()) {
			return getUFDate(d.getYear() + qualitydaynum.intValue(), d.getMonth(), d.getDay());
		}
		return null;
	}

	private int[] getAfterMonth(int year, int month, int interval) {
		if (interval <= 0)
			return null;
		int m = interval / 12;
		int n = interval % 12;

		int yearX = year + m;
		int monthX = month + n;
		if (monthX > 12) {
			yearX = yearX + 1;
			monthX = monthX - 12;
		}

		int[] iyearmonth = new int[2];
		iyearmonth[0] = yearX;
		iyearmonth[1] = monthX;

		return iyearmonth;
	}

	private UFDate getUFDate(int year, int month, int day) {
		if (year <= 0 || month <= 0 || day <= 0)
			return null;
		String syear = year + "";
		String smonth = month + "";
		if (month < 10)
			smonth = "0" + month;
		int days = UFDate.getDaysMonth(year, month);
		if (day > days)
			day = days;
		String sday = day + "";
		if (day < 10)
			sday = "0" + day;
		return new UFDate(syear + "-" + smonth + "-" + sday);
	}
	
	/**
	 * 创建人：刘家清 创建时间：2008-10-8 下午04:09:05 创建原因： 根据失效日期算生产日期。
	 */
	private UFDate calcQualityScrqDate(UFDate d,int qualityperiodunit,int qualitydaynum) {
		  if(d==null )
		    return null;
		  if(qualitydaynum==0)
		    return d;
		  if(InvVO.quality_day==qualityperiodunit){
		    return d.getDateBefore(qualitydaynum);
		  }else if(InvVO.quality_month==qualityperiodunit){
		    int[] yearday = getBeforeMonth(d.getYear(),d.getMonth(),qualitydaynum);
		    if(yearday==null)
		      return null;
		    return getUFDate(yearday[0],yearday[1],d.getDay());
		  }else if(InvVO.quality_year==qualityperiodunit){
		    return getUFDate(d.getYear()-qualitydaynum,d.getMonth(),d.getDay());
		  }
		  return null;
		}

	private int[] getBeforeMonth(int year, int month, int interval){
	    if (interval<=0) return null;
	    int m = interval/12;
	    int n = interval%12;
	    
	    int yearX = year-m;
	    int monthX = month-n;
	    if (monthX>12){
	        yearX = yearX+1;
	        monthX = monthX-12;
	    }
	    
	    int[] iyearmonth = new int[2];
	    iyearmonth[0] = yearX;
	    iyearmonth[1] = monthX;
	    
	    return iyearmonth;
	}
	// end ncm caosl
}