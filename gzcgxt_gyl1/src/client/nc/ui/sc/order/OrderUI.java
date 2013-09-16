package nc.ui.sc.order;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.table.TableColumn;

import nc.bs.framework.common.NCLocator;
import nc.itf.gl.accbook.IBillModel;
import nc.itf.pu.pub.IGetSysBillCode;
import nc.itf.sc.IDataPowerForSC;
import nc.itf.scm.cenpur.service.CentrPurchaseUtil;
import nc.itf.scm.cenpur.service.ChgDataUtil;
import nc.itf.uap.rbac.IUserManageQuery;
import nc.ui.bd.b21.CurrParamQuery;
import nc.ui.bd.ref.busi.InvmandocDefaultRefModel;
import nc.ui.ct.ref.ValiCtRefModel;
import nc.ui.ic.pub.lot.LotNumbRefPane;
import nc.ui.ml.NCLangRes;
import nc.ui.po.pub.PoPublicUIClass;
import nc.ui.pu.pub.POPubSetUI2;
import nc.ui.pu.pub.PuGetUIValueTool;
import nc.ui.pu.pub.PuTool;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.bill.BillActionListener;
import nc.ui.pub.bill.BillBodyMenuListener;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillCellEditor;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillEditListener2;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pub.bill.BillTabbedPane;
import nc.ui.pub.bill.BillTabbedPaneTabChangeEvent;
import nc.ui.pub.bill.BillTabbedPaneTabChangeListener;
import nc.ui.pub.bill.BillTableMouseListener;
import nc.ui.pub.bill.BillUIUtil;
import nc.ui.pub.bill.IBillRelaSortListener;
import nc.ui.pub.billcodemanage.BillcodeRuleBO_Client;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.pub.formulaparse.FormulaParse;
import nc.ui.pub.linkoperate.ILinkAdd;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.pub.linkoperate.ILinkApprove;
import nc.ui.pub.linkoperate.ILinkApproveData;
import nc.ui.pub.linkoperate.ILinkMaintain;
import nc.ui.pub.linkoperate.ILinkMaintainData;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.sc.ct.ScFromCtHelper;
import nc.ui.sc.pub.BillEdit;
import nc.ui.sc.pub.FreeItemCellRenderer;
import nc.ui.sc.pub.OtherRefModel;
import nc.ui.sc.pub.PublicHelper;
import nc.ui.sc.pub.ScBizTypeRefModel;
import nc.ui.sc.pub.ScDeptRefModel;
import nc.ui.sc.pub.ScPsnRefModel;
import nc.ui.sc.pub.ScTool;
import nc.ui.sc.pub.tool.Precision;
import nc.ui.scm.ic.freeitem.FreeItemRefPane;
import nc.ui.scm.ic.measurerate.InvMeasRate;
import nc.ui.scm.inv.InvTool;
import nc.ui.scm.pu.SCMPuTool;
import nc.ui.scm.pub.BillTools;
import nc.ui.scm.pub.CacheTool;
import nc.ui.scm.pub.CollectSettingDlg;
import nc.ui.scm.pub.bill.ButtonTree;
import nc.ui.scm.pub.bill.IBillExtendFun;
import nc.ui.scm.pub.bill.ScmButtonConst;
import nc.ui.scm.pub.ct.CntSelDlg;
import nc.ui.scm.pub.def.DefSetTool;
import nc.ui.scm.pub.panel.BillPanelTool;
import nc.ui.scm.pub.panel.RelationsCal;
import nc.ui.scm.pub.print.ISetBillVO;
import nc.ui.scm.pub.query.SCMQueryConditionDlg;
import nc.ui.scm.pub.report.BillRowNo;
import nc.ui.scm.pub.sourceref.SourceRefDlg;
import nc.ui.scm.ref.WarehouseRefModel;
import nc.ui.scm.sourcebill.SourceBillFlowDlg;
import nc.vo.bd.b06.PsndocVO;
import nc.vo.jcom.lang.StringUtil;
import nc.vo.po.pub.OrderPubVO;
import nc.vo.pp.ask.PriceauditHeaderVO;
import nc.vo.pp.ask.PriceauditItemMergeVO;
import nc.vo.pp.ask.PriceauditMergeVO;
import nc.vo.pu.exception.RwtScToPrException;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.ProductCode;
import nc.vo.pub.VOStatus;
import nc.vo.pub.ValidationException;
import nc.vo.pub.bill.BillTempletVO;
import nc.vo.pub.billcodemanage.BillCodeObjValueVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.sc.order.BillBodyItemKeys;
import nc.vo.sc.order.BillHeaderItemKeys;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.sc.order.OrderHeaderVO;
import nc.vo.sc.order.OrderItemVO;
import nc.vo.sc.order.OrderVO;
import nc.vo.sc.order.SCMessageVO;
import nc.vo.sc.pub.BD_ConvertVO;
import nc.vo.sc.pub.CustDefaultVO;
import nc.vo.sc.pub.RetScVrmAndParaPriceVO;
import nc.vo.sc.pub.SCPubVO;
import nc.vo.sc.pub.ScConstants;
import nc.vo.sc.pub.ScUtils;
import nc.vo.scm.cenpur.service.ChgDocPkVO;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.ctpo.RetCtToPoQueryVO;
import nc.vo.scm.datapower.BtnPowerVO;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.pu.BillStatus;
import nc.vo.scm.pu.BillTypeConst;
import nc.vo.scm.pu.PuPubVO;
import nc.vo.scm.pu.RelationsCalVO;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.session.ClientLink;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.scm.pub.vosplit.SplitBillVOs;
import nc.vo.scm.service.ServcallVO;
import nc.vo.trade.checkrule.VOChecker;

/**
 * 委外订单维护ClientUI
 * <p>
 * <b>变更历史：</b>
 * <p>
 * <hr>
 * <p>
 * 修改日期 2008.8.13
 * <p>
 * 修改人 zhaoyha
 * <p>
 * 版本 5.5
 * <p>
 * 说明：
 * <ul>
 * <li>支持卡片编辑
 * <li>行操作的规范(重排行号、卡片编辑等，权限控制）
 * <li>选中模式调整
 * </ul>
 * 
 * @author:xjl
 * @version:04-19-01
 * 
 */
/**
 * 增加页签
 * 1.getBillCardPanel().getBillModel(TAB1).getItemByKey替换为getBillCardPanel()
 * .getBillModel(TAB1).getItemByKey 2.getBillModel(TAB1)替换为getBillModel(TAB1)
 * 3.getBillCardPanel().getBillTable(tab) 4.getBillCardPanel().setBodyValueAt
 * 5.afterEditCinventorycode 6.存货自由项
 * 6.
 * 
 * @author gc
 * 
 */
public class OrderUI extends nc.ui.pub.ToftPanel implements BillEditListener,// 表头、表体的编辑后、行改变事件
		BillEditListener2,// 表体的编辑前事件
		BillTableMouseListener, BillBodyMenuListener,// 表体弹出式菜单
		ISetBillVO, IBillRelaSortListener, IBillExtendFun,// 支持产业链功能扩展::getExtendBtns()/onExtendBtnsClick()/setExtendBtnsStat()
		ILinkMaintain,// 关联修改
		ILinkAdd,// 关联新增
		ILinkApprove,// 审批流
		ILinkQuery,// 逐级联查
		BillActionListener ,BillTabbedPaneTabChangeListener{

	// gc定义表体tab编码
	private String TAB1 = "table";
	private String TAB2 = "lby";

	private static final long serialVersionUID = 1L;

	private int iMaxMnyDigit = 8;

	private POPubSetUI2 m_cardPoPubSetUI2 = null;

	// 记录是否查询过，查询过才启用“刷新”功能
	private boolean m_bQueried = false;

	// 按钮树实例,since v51
	private ButtonTree m_btnTree = null;

	// 业务类型组按钮
	private ButtonObject boBusitype = null;

	// 增加组按钮
	private ButtonObject boAdd = null;

	// 保存组按钮
	private ButtonObject boSave = null;

	// 维护组按钮
	private ButtonObject boEdit = null;// 修改

	private ButtonObject boCancel = null;// 取消

	private ButtonObject boDel = null;// 作废

	private ButtonObject boCopy = null;// 复制

	private ButtonObject boLineOperator = null;// 行操作组按钮

	private ButtonObject boAddLine = null;// 增行

	private ButtonObject boDelLine = null;// 删行

	private ButtonObject boInsertLine = null;// 插入行

	private ButtonObject boCopyLine = null;// 复制行

	private ButtonObject boPasteLine = null;// 粘贴行

	private ButtonObject boPasteLineToTail = null;// 粘贴行到表尾

	private ButtonObject boReOrderRowNo = null;// 重排行号

	private ButtonObject boCardEdit = null;// 卡片编辑

	// 行操作右键菜单
	private UIMenuItem miReOrderRowNo = null;// 重排行号

	private UIMenuItem miCardEdit = null;// 卡片编辑

	// 执行组按钮
	private ButtonObject boAction = null;

	private ButtonObject boSendAudit = null;// 送审

	private ButtonObject boAudit = null;// 审批

	private ButtonObject boUnaudit = null;// 弃审

	private ButtonObject boCancelOut = null;// 放弃转单

	// 查询组按钮
	private ButtonObject boQuery = null;

	// 浏览组按钮
	private ButtonObject boFrash = null;// 刷新

	private ButtonObject boLocate = null;// 定位

	private ButtonObject boFirst = null;// 首页

	private ButtonObject boPre = null;// 上一页

	private ButtonObject boNext = null;// 下一页

	private ButtonObject boLast = null;// 末页

	private ButtonObject boSelectAll = null;// 全选

	private ButtonObject boSelectNo = null;// 全消

	// 卡片显示/列表显示(切换)
	private ButtonObject boReturn = null;

	// 打印管理组按钮
	private ButtonObject m_btnPrints = null;

	private ButtonObject m_btnPrintPreview = null;// 预览

	private ButtonObject boPrint = null;// 打印

	private ButtonObject btnBillCombin = null;;// 合并显示

	// 辅助查询组按钮
	private ButtonObject m_btnOthersQueries = null;

	private ButtonObject boLinkQuery = null;// 联查

	private ButtonObject boQueryForAudit = null;// 审批流状态(状态查询)

	// 辅助功能组按钮
	private ButtonObject m_btnOthersFuncs = null;

	private ButtonObject boDocument = null;;// 文档管理

	private java.awt.CardLayout cardLayout = null;

	/** 转入单据 */
	private ArrayList comeVOs = null;

	/** 表单 */
	private BillCardPanel m_BillCardPanel;

	/** 单据状态 */
	private int m_billState = ScConstants.STATE_CARD;

	/** 当前VO */
	private OrderVO m_curOrderVO;

	//
	private nc.bs.bd.b21.CurrencyRateUtil m_CurrArith;

	/**
	 * 价格优先策略 包含两种值：无税价格优先；含税价格优先 默认值：无税价格优先
	 */
	private int m_iPricePolicy = RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE;

	// 查询框
	private OrderUIQueryDlg m_query;

	// 查询条件
	private String m_queryCon = null;

	/** 列表 */
	private OrderListPanel m_ScOrderListPanel;

	// 当前表头备注
	private String m_sHeadVmemo;

	// 默认币种
	String m_sPKCurrencyType = "";

	int m_intCurrencyDecimal = 2;

	// 辅币种
	String m_sPKCurrencyTypeAssit = "";

	int m_intCurrencyDecimalAssit = 2;

	// 数量精度
	int[] mintary_precisions = new int[] { 2, 2, 2, 2 };

	//
	int mint_localmnyPrecision = 2;

	boolean mbol_HasGetDecimal = false;// 用于提高效率

	// 合同是否启用
	private boolean m_bCTStartUp = false;

	// 判断参数mint_localmnyPrecision,mbol_HasGetDecimal,m_bCTStartUp是否从后台加载过
	private boolean parasHaveLoaded = false;

	/* 批量打印工具 */
	private nc.ui.scm.pub.print.ScmPrintTool printList = null; // 类变量

	private CntSelDlg m_CntSelDlg = null;// 关联合同时,选择窗口

	/**
	 * 记录是否当前操作为增行,以便行增加后,作相应处理
	 */
	private boolean isAddedLine = false;

	// 卡片列表单据模版VO，为降低远程调用的连接，songhy
	private BillTempletVO billTempletVO;

	// 缓存供应商VO
	private Map<String, CustDefaultVO> custDefaultVOMap;

	// 设置界面数据精度的工具类
	private Precision precisionUtil;

	// 前台的数据模型
	private OrderModel orderModel;

	/**
	 * V51重构需要的匹配,按钮实例变量化。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author lxd
	 * @time 2007-3-15 下午02:23:07
	 */
	private void createBtnInstances() {
		// 业务类型组按钮
		boBusitype = getBtnTree().getButton(ScmButtonConst.BTN_BUSINESS_TYPE);
		// 增加组按钮
		boAdd = getBtnTree().getButton(ScmButtonConst.BTN_ADD);
		// 保存组按钮
		boSave = getBtnTree().getButton(ScmButtonConst.BTN_SAVE);
		// 维护组按钮
		boEdit = getBtnTree().getButton(ScmButtonConst.BTN_BILL_EDIT);// 修改
		boCancel = getBtnTree().getButton(ScmButtonConst.BTN_BILL_CANCEL);// 取消
		boDel = getBtnTree().getButton(ScmButtonConst.BTN_BILL_DELETE);// 删除
		boCopy = getBtnTree().getButton(ScmButtonConst.BTN_BILL_COPY);// 复制
		// 行操作组按钮
		boLineOperator = getBtnTree().getButton(ScmButtonConst.BTN_LINE);
		boAddLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_ADD);// 增行
		boDelLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_DELETE);// 删行
		boInsertLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_INSERT);// 插入行
		boCopyLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_COPY);// 复制行
		boPasteLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_PASTE);// 粘贴行
		boPasteLineToTail = getBtnTree().getButton(
				ScmButtonConst.BTN_LINE_PASTE_TAIL);// 粘贴行到表尾
		boReOrderRowNo = getBtnTree()
				.getButton(ScmButtonConst.BTN_ADD_NEWROWNO);// 重排行号
		boCardEdit = getBtnTree().getButton(ScmButtonConst.BTN_CARD_EDIT);// 卡片编辑

		// 执行组按钮
		boAction = getBtnTree().getButton(ScmButtonConst.BTN_EXECUTE);
		boSendAudit = getBtnTree().getButton(ScmButtonConst.BTN_EXECUTE_AUDIT);// 送审
		boAudit = getBtnTree().getButton(ScmButtonConst.BTN_AUDIT);// 审批
		boUnaudit = getBtnTree().getButton(
				ScmButtonConst.BTN_EXECUTE_AUDIT_CANCEL);// 弃审
		boCancelOut = getBtnTree().getButton(ScmButtonConst.BTN_REF_CANCEL);// 放弃转单

		// 查询组按钮
		boQuery = getBtnTree().getButton(ScmButtonConst.BTN_QUERY);

		// 浏览组按钮
		boFrash = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_REFRESH);// 刷新
		boLocate = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_LOCATE);// 定位
		boFirst = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_TOP);// 首页
		boPre = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_PREVIOUS);// 上一页
		boNext = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_NEXT);// 下一页
		boLast = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_BOTTOM);// 末页
		boSelectAll = getBtnTree().getButton(
				ScmButtonConst.BTN_BROWSE_SELECT_ALL);// 全选
		boSelectNo = getBtnTree().getButton(
				ScmButtonConst.BTN_BROWSE_SELECT_NONE);// 全消

		// 卡片显示/列表显示(切换)
		boReturn = getBtnTree().getButton(ScmButtonConst.BTN_SWITCH);

		// 打印管理组按钮
		m_btnPrints = getBtnTree().getButton(ScmButtonConst.BTN_PRINT);
		m_btnPrintPreview = getBtnTree().getButton(
				ScmButtonConst.BTN_PRINT_PREVIEW);// 预览
		boPrint = getBtnTree().getButton(ScmButtonConst.BTN_PRINT_PRINT);// 打印
		btnBillCombin = getBtnTree().getButton(
				ScmButtonConst.BTN_PRINT_DISTINCT);// 合并显示

		// 辅助查询组按钮
		m_btnOthersQueries = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_QUERY);
		boLinkQuery = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_QUERY_RELATED);// 联查
		boQueryForAudit = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_QUERY_WORKFLOW);// 审批流状态(状态查询)

		// 辅助功能组按钮
		m_btnOthersFuncs = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_FUNC);
		boDocument = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_FUNC_DOCUMENT);// 文档管理
	}

	/**
	 * 获取按钮树，类唯一实例。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @return <p>
	 * @author lxd
	 * @time 2007-3-15 下午05:04:22
	 */
	private ButtonTree getBtnTree() {
		if (m_btnTree == null) {
			try {
				m_btnTree = new ButtonTree(getModuleCode());
			} catch (BusinessException be) {
				showHintMessage(be.getMessage());
				return null;
			}
		}
		return m_btnTree;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-4-19 18:19:11)
	 */
	public OrderUI() {
		super();
		init();
	}

	/**
	 * BillBodyMenuListener的接口方法，对本类实现没有用
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// do nothing, BillBodyMenuListener
	}

	/**
	 * 表头供应商参照编辑后的处理
	 */
	private void afterEditCvendormangid() {// gc 不需要处理
		int rowCount = getBillCardPanel().getRowCount();
		if (rowCount <= 0) {
			// 表体行数为0，返回
			return;
		}

		// 若币种为空，则给出供应商默认币种
		String cvendormangid = (String) getCardHeadItemValue(BillHeaderItemKeys.CVENDORMANGID);
		if (StringUtil.isEmpty(cvendormangid)) {
			// 编辑后供应商Id为空，返回
			return;
		}

		// 供应商Id不为空,获取供应商默认交易币种
		String currentyTypeId = getCurrencyTypeId(cvendormangid);
		if (StringUtil.isEmpty(currentyTypeId)) {
			// 供应商默认交易币种为空，则取本位币
			currentyTypeId = m_sPKCurrencyType;
		}

		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			if (StringUtil.isEmpty((String) getCardBodyValueAt(rowIndex,
					BillBodyItemKeys.CCURRENCYTYPEID,TAB1))) {
				// 表体行币种为空，才设置币种
				getBillCardPanel().setBodyValueAt(currentyTypeId, rowIndex,
						BillBodyItemKeys.CCURRENCYTYPEID,TAB1);
				setCurrencyExchangeRateEnable(rowIndex);
				setCurrencyExchangeRate(rowIndex);
			}
		}
	}

	/**
	 * 表头编辑后事件的处理
	 * 
	 * @param e
	 */
	private void afterEditItemInCardHead(BillEditEvent e) {
		// 供应商带出默认项
		if (e.getKey().equals(BillHeaderItemKeys.CVENDORMANGID)
				|| e.getKey().equals("caccountbankid")) {
			BillEdit.editCust(getBillCardPanel(), e.getKey());
			loadCustBank();
			afterEditCvendormangid();
			// 关联合同和自动取价处理
			setRelateCntAndDefaultPriceAllRow(false);
			// 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
			setNotEditableWhenRelateCntAllRow();
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			setPrecision();
		}

		// 部门、业务员关系
		if (e.getKey().equals("cdeptid") || e.getKey().equals("cemployeeid")) {
			BillEdit.editDeptAndEmployee(getBillCardPanel(), e.getKey());
		}

		// 收货方带出到货地址
		if (e.getKey().equals("creciever")) {
			setReceiveAddress(0, e.getKey());
		}

		// 库存组织计算计划到货日期
		if (e.getKey().equals("cwareid")) {
			for (int i = 0; i < getBillCardPanel().getRowCount(); i++)
				BillEdit.editArrDate(getBillCardPanel(), i, e.getKey(),
						nc.ui.pub.ClientEnvironment.getInstance().getDate());

			// Czp : V31 增加仓库与库存组织匹配处理
			String strWareId = (String) getCardHeadItemValue(BillBodyItemKeys.CWAREID);
			// 库存组织变化后，仓库参照变化:清空表体仓库ID(cwarehouseid)和表体库存组织(warehouseorg);
			afterEditStoreOrgToWarehouse(getBillCardPanel(), e,
					getCorpPrimaryKey(), strWareId,
					BillBodyItemKeys.CWAREHOUSENAME, new String[] {
							BillBodyItemKeys.CWAREHOUSEID,
							BillHeaderItemKeys.WAREHOUSEORG });
		}

		// 自定义项PK处理
		setHeadDefPK(e);

		if (e.getKey().equals(BillHeaderItemKeys.DORDERDATE)) {
			// 关联合同和自动取价处理
			setRelateCntAndDefaultPriceAllRow(false);
			// 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
			setNotEditableWhenRelateCntAllRow();
		}

		if (e.getKey().equals(BillBodyItemKeys.CWAREID)) {
			setRelateCntAndDefaultPriceAllRow(true);
		}

		if (e.getKey().indexOf(BillHeaderItemKeys.DEF_PREFIX) >= 0)
			getBillCardPanel().execHeadEditFormulas();
	}

	/**
	 * 转化Object数组为字符串数组
	 * 
	 * @param objArray
	 *            Object数组
	 * @return 字符串数组
	 */
	private String[] convertToStringArray(Object[] objArray) {
		if ((objArray == null) || (objArray.length <= 0)) {
			return new String[0];
		}

		String[] resultStrArray = new String[objArray.length];
		for (int i = 0; i < objArray.length; i++) {
			resultStrArray[i] = (String) objArray[i];
		}

		return resultStrArray;
	}

	/**
	 * 多选存货后的事件处理
	 * 
	 * @param e
	 */
	private void afterEditCinventorycode(BillEditEvent e) throws Exception {
		String tab = getSelectTab();//gc
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBillModel(tab)
				.getItemByKey(BillBodyItemKeys.CINVENTORYCODE).getComponent();

		if (refpane.getRefPKs() == null) {
			// 没有选择存货，不做处理，直接返回
			return;
		}

		// 存货管理档案ID
		Object[] saMangId = ((Object[]) refpane
				.getRefValues("bd_invmandoc.pk_invmandoc"));
		// 存货基本档案ID
		Object[] saBaseId = ((Object[]) refpane
				.getRefValues("bd_invmandoc.pk_invbasdoc"));
		// 存货编码
		Object[] saCode = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invcode"));
		// 存货名称
		Object[] saName = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invname"));
		// 存货规格
		Object[] saSpec = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invspec"));
		// 存货型号
		Object[] saType = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invtype"));

		// 存货主计量单位ID
		String[] saBaseIdTemp = convertToStringArray(saBaseId);
		Object[] saMeasUnit = (Object[]) CacheTool.getColumnValue(
				"bd_invbasdoc", "pk_invbasdoc", "pk_measdoc", saBaseIdTemp);

		String[] saMeasUnitTemp = convertToStringArray(saMeasUnit);
		Object[] saUnitNameTemp = (Object[]) CacheTool.getColumnValue(
				"bd_measdoc", "pk_measdoc", "measname", saMeasUnitTemp);
		// 存货主计量单位名称
		String[] saUnitName = convertToStringArray(saUnitNameTemp);

		Object[] sAssisUnitTemp = null;
		String[] sAssisUnit = null;
		Object[] sIsAssisUnitTemp = null;
		String[] sAssisUnitName = null;
		String[] sIsAssisUnit = null;
//		if(tab.equals(TAB1)){
			// 辅计量主键
			sAssisUnitTemp = (Object[]) CacheTool.getColumnValue(
					"bd_invbasdoc", "pk_invbasdoc", "pk_measdoc2", saBaseIdTemp);
			sAssisUnit = convertToStringArray(sAssisUnitTemp);
	
			// 是否辅计量管理，考虑效率，先批量加载
			InvTool.loadBatchAssistManaged(saBaseIdTemp);
			sIsAssisUnitTemp = (Object[]) CacheTool.getColumnValue(
					"bd_invbasdoc", "pk_invbasdoc", "assistunit", saBaseIdTemp);
			sIsAssisUnit = convertToStringArray(sIsAssisUnitTemp);
	
			// 辅计量名称
			Object[] sAssisUnitNameTemp = (Object[]) CacheTool.getColumnValue(
					"bd_measdoc", "pk_measdoc", "measname", sAssisUnit);
			sAssisUnitName = convertToStringArray(sAssisUnitNameTemp);
//		}
		// 插入多行
		int iInsertLen = (saMangId == null) ? 0 : (saMangId.length - 1);
		int iBeginRow = 0, iEndRow = 0;
		iBeginRow = e.getRow();
		if (iBeginRow == getBillCardPanel().getRowCount() - 1) {
			// 选中的行已是最后一行则增行
			for (int i = 0; i < iInsertLen; i++) {
				getBillCardPanel().addLine();
			}
		} else {
			// 插行
			onInsertLines(iBeginRow, iBeginRow + 1, iInsertLen);
		}
		iEndRow = iBeginRow + iInsertLen;

		String[] saMangIdTemp = convertToStringArray(saMangId);
		// 考虑效率，在循环处理每个存货的批次号之前，先批量加载批次号
		InvTool.loadBatchProdNumMngt(saMangIdTemp);
		// 考虑效率，在循环处理每个存货的自由项之前，先批量加载自由项
		InvTool.loadBatchFreeVO(saMangIdTemp);
		// 给多行赋值
		int iPkIndex = 0;
		for (int i = iBeginRow; i <= iEndRow; i++) {
			iPkIndex = i - iBeginRow;

			// 管理ID
			setCardBodyValueAt(saMangId[iPkIndex], i, BillBodyItemKeys.CMANGID,null);
			// 基本ID
			setCardBodyValueAt(saBaseId[iPkIndex], i, BillBodyItemKeys.CBASEID,null);
			// 编码
			setCardBodyValueAt(saCode[iPkIndex], i,
					BillBodyItemKeys.CINVENTORYCODE,null);
			// 名称
			setCardBodyValueAt(saName[iPkIndex], i,
					BillBodyItemKeys.CINVENTORYNAME,null);
			// 规格
			setCardBodyValueAt(saSpec[iPkIndex], i, BillBodyItemKeys.INVSPEC,null);
			// 型号
			setCardBodyValueAt(saType[iPkIndex], i, BillBodyItemKeys.INVTYPE,null);
			// 主计量单位pk
			setCardBodyValueAt(saMeasUnit[iPkIndex], i,
					BillBodyItemKeys.PK_MEASDOC,null);
			// 主计量单位NAME
			setCardBodyValueAt(saUnitName[iPkIndex], i,
					BillBodyItemKeys.CMEASDOCNAME,null);
//			if(tab.equals(TAB1)){
				// 辅计量单位pk
				setCardBodyValueAt(sAssisUnit[iPkIndex], i,
						BillBodyItemKeys.CASSISTUNIT,null);
				// 辅计量单位name
				setCardBodyValueAt(sAssisUnitName[iPkIndex], i,
						BillBodyItemKeys.CASSISTUNITNAME,null);
				// 是否辅计量管理
				setCardBodyValueAt(sIsAssisUnit[iPkIndex], i,
						BillBodyItemKeys.ISASSIST,null);
				if (getBillCardPanel().getBillModel(tab).getRowState(i) != BillModel.MODIFICATION) {
					getBillCardPanel().getBillModel(tab).setRowState(i,
							BillModel.ADD);
				}
//			}
			// 批次号处理，开始
			// 批次号的可编辑性
			getBillCardPanel().getBillModel(tab).setCellEditable(i,
					BillBodyItemKeys.VPRODUCENUM,
					InvTool.isBatchManaged(saMangIdTemp[iPkIndex]));
			// 批次号清空
			setCardBodyValueAt(null, i, BillBodyItemKeys.VPRODUCENUM,null);
			// 批次号处理，完毕

			// 自由项处理，开始
			InvVO invVO = new InvVO();
			FreeVO freeVO = InvTool.getInvFreeVO((String) saMangId[iPkIndex]);
			if (freeVO == null) {
				getBillCardPanel().setCellEditable(i, "vfree0", false,tab);
				invVO.setIsFreeItemMgt(new Integer(0));
			} else {
				getBillCardPanel().setCellEditable(i, "vfree0", true,tab);
				invVO.setIsFreeItemMgt(new Integer(1));

				// add by hanbin 2009-11-18 原因：去除自由项的默认值。
				// 针对bug(NCdp201081223)：自由项存货，新增单据录入自由项，界面显示上次录入的数据。
				freeVO.setVfree1(null);
				freeVO.setVfree2(null);
				freeVO.setVfree3(null);
				freeVO.setVfree4(null);
				freeVO.setVfree5(null);
			}

			invVO.setFreeItemVO(freeVO);
			invVO.setCinvmanid((String) saBaseId[iPkIndex]);
			invVO.setCinventoryid((String) saMangId[iPkIndex]);
			invVO.setCinventorycode((String) saCode[iPkIndex]);
			invVO.setInvname((String) saName[iPkIndex]);
			invVO.setInvspec((String) saSpec[iPkIndex]);
			invVO.setInvtype((String) saType[iPkIndex]);
			FreeItemRefPane freeRef = (FreeItemRefPane) getBillCardPanel()
					.getBillModel(tab).getItemByKey("vfree0").getComponent();
			freeRef.setFreeItemParam(invVO);

			getBillCardPanel().setBodyValueAt(invVO, i, "invvo");
			getBillCardPanel().setBodyValueAt(null, i, "vfree0");
			// 自由项处理，完毕
		}

		// 多选存货后的辅计量单位处理
		InvTool.loadBatchInvConvRateInfo(saBaseIdTemp, sAssisUnit);
		BillEdit.editAssistUnitForMultiSelected(getBillCardPanel(), iBeginRow,
				iEndRow, e.getKey());

		// 设置多选存货的默认税率
		setRelated_Taxrate(iBeginRow, iEndRow);

		// 2009-9-11 renzhonghai 关联合同设置
		int lengthLine = iEndRow - iBeginRow + 1;
		int[] lintary_rows = new int[lengthLine];
		for (int i = iBeginRow, j = 0; i <= iEndRow; i++, j++) {
			lintary_rows[j] = i;
		}
		if(tab.equals(TAB1)){
			// 自动取价
			setRelateCntAndDefaultPrice(lintary_rows, false);
			// 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
			setNotEditableWhenRelateCntAllRow();
		}
		// 编辑后执行公式
		getBillCardPanel().getBillModel(tab).execLoadFormula();
	}

	/**
	 * 获取价格优先策略 包含两种值： 无税价格优先 RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE； 含税价格优先
	 * RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE 默认值：无税价格优先
	 * 
	 * @return
	 */
	private int getPricePolicy() {
		return m_iPricePolicy;
	}

	/**
	 * 表体编辑后事件的处理
	 * 
	 * @param e
	 */
	private void afterEditItemInCardBody(BillEditEvent e) throws Exception {
		int rowindex = e.getRow();
		// 扣率(0---100)
		if (e.getKey().equals(BillBodyItemKeys.NDISCOUNTRATE)) {// gc 不需要处理
			Object odiscountrate = e.getValue();
			if (odiscountrate == null || odiscountrate.equals(""))
				getBillCardPanel().setBodyValueAt("100", rowindex,
						BillBodyItemKeys.NDISCOUNTRATE);
			else {
				double discount = new UFDouble(odiscountrate.toString())
						.doubleValue();
				if (discount <= 0 || discount > 100) {
					showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("401201", "UPP401201-000035")/*
																	 * @res
																	 * "扣率应在0---100之间，请重新录入"
																	 */);
					getBillCardPanel().setBodyValueAt("100", rowindex,
							BillBodyItemKeys.NDISCOUNTRATE);
					getBillCardPanel().execBodyFormula(rowindex,
							BillBodyItemKeys.NDISCOUNTRATE);
				}
			}
		} else if (e.getKey().equals(BillBodyItemKeys.CINVENTORYCODE)) {
			// 存货多选
			afterEditCinventorycode(e);
		} else if (e.getKey().equals("vfree0")) {
			String tab = getSelectTab();//gc
			BillEdit.editFreeItem(getBillCardPanel(),tab, rowindex, e.getKey(),
						"cbaseid", "cmangid");
		}
		// 仓库－－－表体到货地址
		else if (e.getKey().equals("cwarehousename")) {
			setReceiveAddress(rowindex, e.getKey());
		} else if ("cprojectname".equals(e.getKey())) {// gc 只有第一个页才有，不用修改
			/***** 项目修改后，处理项目阶段字段是否可以编辑、清除原来项目阶段的值 *****/
			int n = e.getRow();
			BillCardPanel bcp = this.getBillCardPanel();
			Object oTemp = bcp.getBodyValueAt(n, "cprojectname");
			if (oTemp == null || oTemp.toString().length() == 0) {
				bcp.getBillModel(TAB1).setCellEditable(n, "cprojectphasename",
						false);
			} else {
				bcp.getBillModel(TAB1).setCellEditable(
						n,
						"cprojectphasename",
						bcp.getBillModel(TAB1)
								.getItemByKey("cprojectphasename").isEdit());
				oTemp = bcp.getBodyValueAt(n, "cprojectid");
				if (oTemp != null && oTemp.toString().length() > 0) {
					UIRefPane nRefPanel = (UIRefPane) bcp.getBodyItem(
							"cprojectphasename").getComponent();
					nRefPanel.setIsCustomDefined(true);
					nRefPanel.setRefModel(new nc.ui.sc.pub.ProjectPhase(
							(String) oTemp));
				}
			}
			bcp.setBodyValueAt(null, n, "cprojectphasename");
			bcp.setBodyValueAt(null, n, "cprojectphaseid");
		}
		// 项目－－－项目阶段
		else if (e.getKey().equals("cprojectname")) {// gc 只有第一个页才有，不用修改
			Object cprojectid = getBillCardPanel().getBodyValueAt(rowindex,
					"cprojectid");
			if (cprojectid != null && !cprojectid.toString().trim().equals("")) {
				UIRefPane ref = (UIRefPane) getBillCardPanel()
						.getBillModel(TAB1).getItemByKey("cprojectphasename")
						.getComponent();
				ref.setRefModel(new nc.ui.sc.pub.ProjectPhase(cprojectid
						.toString()));
				getBillCardPanel().setCellEditable(rowindex,
						"cprojectphasename", true);
			} else {
				getBillCardPanel().setBodyValueAt("", rowindex,
						"cprojectphasename");
				getBillCardPanel().setBodyValueAt("", rowindex,
						"cprojectphaseid");
				getBillCardPanel().setCellEditable(rowindex,
						"cprojectphasename", false);
				getBillCardPanel().updateUI();
			}
		}

		// 表体数量关系连动
		int[] descriptions = new int[] { RelationsCal.DISCOUNT_TAX_TYPE_NAME,
				RelationsCal.DISCOUNT_TAX_TYPE_KEY, RelationsCal.NUM,
				RelationsCal.TAXPRICE_ORIGINAL, RelationsCal.PRICE_ORIGINAL,
				RelationsCal.NET_TAXPRICE_ORIGINAL,
				RelationsCal.NET_PRICE_ORIGINAL, RelationsCal.DISCOUNT_RATE,
				RelationsCal.TAXRATE, RelationsCal.MONEY_ORIGINAL,
				RelationsCal.TAX_ORIGINAL, RelationsCal.SUMMNY_ORIGINAL,
				RelationsCal.PK_CORP };

		String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// 应税外加;
		Object oTemp = getBillCardPanel().getBodyValueAt(e.getRow(),
				"idiscounttaxtype");// gc 只有第一个页才有，不用修改
		if (oTemp != null) {
			lStr_discounttaxtype = oTemp.toString();
		}

		String[] keys = new String[] { lStr_discounttaxtype,
				"idiscounttaxtype",// 扣税类别
				"nordernum",// 订货数量
				"norgtaxprice",// 含税单价
				"noriginalcurprice",// 单价
				"norgnettaxprice",// 净含税单价
				"noriginalnetprice",// 净单价
				"ndiscountrate",// 扣率
				"ntaxrate",// 税率
				"noriginalcurmny",// 金额
				"noriginaltaxmny",// 税额
				"noriginalsummny",// 价税合计
				"pk_corp"// 公司
		};

		if (e.getKey().equals("nordernum")&&getSelectTab().equals(TAB1)) {
			// 修改订单数量时
			if (getBillCardPanel().getBillModel(TAB1).getValueAt(rowindex,
					"ntaxrate") == null)// 税率为空时设置为0,公共算法要求,since
				// v501，如：修改辅单位，价税合计为空，但金额有值。
				getBillCardPanel().getBillModel(TAB1).setValueAt(
						new UFDouble(0), rowindex, "ntaxrate");
			RelationsCal.calculate(getBillCardPanel(), e, getBillCardPanel()
					.getBillModel(TAB1), new int[] { getPricePolicy() },
					descriptions, keys, OrderItemVO.class.getName());

			BillEdit.editAssistUnit(getBillCardPanel(), rowindex, e.getKey());
		}

		if (e.getKey().equals("idiscounttaxtype")
				|| e.getKey().equals("ndiscountrate")
				|| e.getKey().equals("noriginalnetprice")
				|| e.getKey().equals("noriginalcurprice")
				|| e.getKey().equals("ntaxrate")
				|| e.getKey().equals("noriginalcurmny")
				|| e.getKey().equals("noriginaltaxmny")
				|| e.getKey().equals("noriginalsummny")
				|| e.getKey().equals("norgtaxprice")
				|| e.getKey().equals("norgnettaxprice")) {
			if (getBillCardPanel().getBillModel(TAB1).getValueAt(rowindex,
					"ntaxrate") == null)// 税率为空时设置为0,公共算法要求,since
				// v501，如：修改辅单位，价税合计为空，但金额有值。
				getBillCardPanel().getBillModel(TAB1).setValueAt(
						new UFDouble(0), rowindex, "ntaxrate");
			RelationsCal.calculate(getBillCardPanel(), e, getBillCardPanel()
					.getBillModel(TAB1), new int[] { getPricePolicy() },
					descriptions, keys, OrderItemVO.class.getName());
		}

		if (e.getKey().equals("cassistunitname")
				|| e.getKey().equals("nassistnum")
				|| e.getKey().equals("measrate")) {// 辅单位、订货数量、辅数量、换算率
			BillEdit.editAssistUnit(getBillCardPanel(), rowindex, e.getKey());
			BillEditEvent t = new BillEditEvent(e.getSource(), e.getValue(),
					"nordernum", e.getRow(), e.getPos());

			RelationsCal.calculate(getBillCardPanel(), t, getBillCardPanel()
					.getBillModel(TAB1), new int[] { getPricePolicy() },
					descriptions, keys, OrderItemVO.class.getName());
		}

		// 币种发生变化，重新计算
		if (e.getKey().equals("ccurrencytype")) {
			setMaxMnyDigit(iMaxMnyDigit);
			setExchangeRateBody(rowindex, true);
			// 设置汇率、金额等的精度
			setPrecision();
			getPrecisionUtil().bodyRowChange(
					e,
					new String[] { "nexchangeotobrate", "noriginalcurmny",
							"noriginaltaxmny", "noriginalsummny", "nmoney",
							"ntaxmny", "nsummny" });
			RelationsCal.calculate(getBillCardPanel(), e, getBillCardPanel()
					.getBillModel(TAB1), new int[] { getPricePolicy() },
					"nordernum", descriptions, keys, OrderItemVO.class
							.getName());
			// 修改币种时,应该跳过合同相关运算,所以用true
			setRelateCntAndDefaultPrice(new int[] { e.getRow() }, false);
		} else if (e.getKey().equals("nexchangeotobrate")) {
			// 修改折本折辅汇率时,应该跳过合同相关运算,所以用true
			setRelateCntAndDefaultPrice(new int[] { e.getRow() }, true);
		}
		// 合同号
		else if (e.getKey().equals("ccontractrcode")) {
			afterEditWhenBodyCntCode(e);// 修改合同号后的相应变化
		}

		// 自定义项PK处理
		setBodyDefPK(e);
	}

	/*
	 * 编辑后事件处理
	 */
	public void afterEdit(BillEditEvent e) {
		try {
			if (e.getPos() == BillItem.HEAD) {
				afterEditItemInCardHead(e);
			}

			if (e.getPos() == BillItem.BODY) {
				afterEditItemInCardBody(e);
			}

			// 刷新界面
			getBillCardPanel().updateUI();
		} catch (Exception ex) {
			SCMEnv.out("error!  afterEdit(BillEditEvent e)" + ex.getMessage());
			this.showErrorMessage(ex.getMessage());
		}
	}

	/**
	 * 设置界面数据精度
	 */
	private void setPrecision() {
		getPrecisionUtil().setExchangeRatePercision(getCorpPrimaryKey(),
				BillBodyItemKeys.CCURRENCYTYPEID,
				BillBodyItemKeys.NEXCHANGEOTOBRATE);

		String[] localMnyItems = new String[] { BillBodyItemKeys.NMONEY,
				BillBodyItemKeys.NTAXMNY, BillBodyItemKeys.NSUMMNY };
		String[] currMnyItems = new String[] {
				BillBodyItemKeys.NORIGINALCURMNY,
				BillBodyItemKeys.NORIGINALTAXMNY,
				BillBodyItemKeys.NORIGINALSUMMNY };
		getPrecisionUtil().setBusinessPrecision(
				BillBodyItemKeys.CCURRENCYTYPEID, localMnyItems, currMnyItems);
	}

	/**
	 * 获取界面精度设置工具
	 * 
	 * @return 精度设置工具
	 */
	private Precision getPrecisionUtil() {
		if (precisionUtil == null) {
			precisionUtil = new Precision(
					getBillCardPanel().getBillModel(TAB1), getBillCardPanel()
							.getBillTable(TAB1));
		}
		return precisionUtil;
	}

	/**
	 * 作者：yye 功能：修改合同号后的相应变化 参数：BillEditEvent e 捕捉到的BillEditEvent事件 返回：无 例外：无
	 */
	private void afterEditWhenBodyCntCode(BillEditEvent e) {// gc 只有第一个页才有，不用修改
		try {
			int lint_rowindex = e.getRow();
			if (e.getValue() == null
					|| e.getValue().toString().trim().length() < 1) {
				// 是否可编辑
				getBillCardPanel().setCellEditable(
						lint_rowindex,
						"ccontractrcode",
						getBillCardPanel().getBillModel(TAB1).getItemByKey(
								"ccontractrcode") != null
								&& getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("ccontractrcode")
										.isEdit());
				getBillCardPanel()
						.setCellEditable(
								lint_rowindex,
								"ccurrencytype",
								getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("ccurrencytype") != null
										&& getBillCardPanel()
												.getBillModel(TAB1)
												.getItemByKey("ccurrencytype")
												.isEdit());
				getBillCardPanel().setCellEditable(
						lint_rowindex,
						"noriginalcurprice",
						getBillCardPanel().getBillModel(TAB1).getItemByKey(
								"noriginalcurprice") != null
								&& getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("noriginalcurprice")
										.isEdit());
				getBillCardPanel().setCellEditable(
						lint_rowindex,
						"norgtaxprice",
						getBillCardPanel().getBillModel(TAB1).getItemByKey(
								"norgtaxprice") != null
								&& getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("norgtaxprice").isEdit());
				getBillCardPanel().setCellEditable(
						lint_rowindex,
						"noriginalnetprice",
						getBillCardPanel().getBillModel(TAB1).getItemByKey(
								"noriginalnetprice") != null
								&& getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("noriginalnetprice")
										.isEdit());
				getBillCardPanel().setCellEditable(
						lint_rowindex,
						"noriginalcurmny",
						getBillCardPanel().getBillModel(TAB1).getItemByKey(
								"noriginalcurmny") != null
								&& getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("noriginalcurmny")
										.isEdit());
				// 合同ID 合同行ID 合同号 币种
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						"ccontractid");
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						"ccontractrowid");
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						"ccontractrcode");
				getBillCardPanel().setBodyValueAt(new UFDouble(17.00),
						lint_rowindex, "ntaxrate");

				BillEditEvent t = new BillEditEvent(e.getSource(),
						new UFDouble(17.00), "ntaxrate", e.getRow(), e.getPos());
				afterEditItemInCardBody(t);
			} else {
				nc.ui.ct.ref.ValiCtRefModel refmodelCt = (nc.ui.ct.ref.ValiCtRefModel) ((UIRefPane) getBillCardPanel()
						.getBodyItem("ccontractrcode").getComponent())
						.getRefModel();
				Object[] oRet = new Object[5];
				oRet[0] = (String) refmodelCt.getValue("ct_b.pk_ct_manage");
				oRet[1] = (String) refmodelCt.getValue("ct_b.pk_ct_manage_b");
				oRet[2] = (String) refmodelCt.getValue("ct.ct_code");
				oRet[3] = (String) refmodelCt.getValue("ct.currid");
				// oRet[4] = (BigDecimal)
				// refmodelCt.getValue("ct_b.oriprice");若编辑表体改变合同，则oriprice(UFDouble类型)转换为BigDecimal错误；因无其它地方引用，暂注掉。
				// 设置值
				getBillCardPanel().setBodyValueAt(oRet[0], lint_rowindex,
						"ccontractid");// 合同ID
				getBillCardPanel().setBodyValueAt(oRet[1], lint_rowindex,
						"ccontractrowid");// 合同行ID
				getBillCardPanel().setBodyValueAt(oRet[2], lint_rowindex,
						"ccontractrcode");// 合同号
				RetCtToPoQueryVO[] lary_CtRetVO = null;

				// 设置界面其他项及可编辑性
				if (oRet[1] != null && oRet[1].toString().trim().length() > 0) {
					String lStr_CtBid = oRet[1].toString().trim();
					lary_CtRetVO = ScFromCtHelper
							.queryCntByct_b(new String[] { lStr_CtBid });

					if (lary_CtRetVO != null && lary_CtRetVO.length > 0
							&& lary_CtRetVO.length == 1) {
						// 修改订单列的itemkey
						String lStr_ChangedKey = ScUtils
								.getPriceFieldByPricePolicy(getPricePolicy());

						UFDouble lUFD_Price = null;
						lUFD_Price = SCPubVO.getPriceValueByPricePolicy(
								lary_CtRetVO[0], getPricePolicy());

						if (lUFD_Price != null) {

							UFDouble lUFD_OldPrice = SCPubVO
									.getUFDouble_ValueAsValue(getBillCardPanel()
											.getBodyValueAt(lint_rowindex,
													lStr_ChangedKey));

							getBillCardPanel().setBodyValueAt(
									lary_CtRetVO[0].getCContractID(),
									lint_rowindex, "ccontractid");
							getBillCardPanel().setBodyValueAt(
									lary_CtRetVO[0].getCContractRowId(),
									lint_rowindex, "ccontractrowid");
							getBillCardPanel().setBodyValueAt(
									lary_CtRetVO[0].getCContractCode(),
									lint_rowindex, "ccontractrcode");

							// /与原有值不同才重新计算
							if (lUFD_OldPrice == null
									|| lUFD_Price.compareTo(lUFD_OldPrice) != 0) {
								getBillCardPanel().setBodyValueAt(lUFD_Price,
										lint_rowindex, lStr_ChangedKey);
								getBillCardPanel().setBodyValueAt(
										lary_CtRetVO[0].getTaxration(),
										lint_rowindex, "ntaxrate");

								// 重新计算数量关系
								// 表体数量关系连动
								int[] descriptions = new int[] {
										RelationsCal.DISCOUNT_TAX_TYPE_NAME,
										RelationsCal.DISCOUNT_TAX_TYPE_KEY,
										RelationsCal.NUM,
										RelationsCal.TAXPRICE_ORIGINAL,
										RelationsCal.PRICE_ORIGINAL,
										RelationsCal.NET_TAXPRICE_ORIGINAL,
										RelationsCal.NET_PRICE_ORIGINAL,
										RelationsCal.DISCOUNT_RATE,
										RelationsCal.TAXRATE,
										RelationsCal.MONEY_ORIGINAL,
										RelationsCal.TAX_ORIGINAL,
										RelationsCal.SUMMNY_ORIGINAL };

								Object oTemp = getBillCardPanel()
										.getBodyValueAt(lint_rowindex,
												"idiscounttaxtype");
								String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// 应税外加
								if (oTemp != null) {
									lStr_discounttaxtype = oTemp.toString();
								}

								String[] keys = new String[] {
										lStr_discounttaxtype,
										"idiscounttaxtype", "nordernum",
										"norgtaxprice", "noriginalcurprice",
										"norgnettaxprice", "noriginalnetprice",
										"ndiscountrate", "ntaxrate",
										"noriginalcurmny", "noriginaltaxmny",
										"noriginalsummny" };
								BillEditEvent l_BillEditEventTemp = new BillEditEvent(
										getBillCardPanel().getBodyItem(
												lStr_ChangedKey).getComponent(),
										getBillCardPanel().getBodyValueAt(
												lint_rowindex, lStr_ChangedKey),
										lStr_ChangedKey, lint_rowindex);
								RelationsCal.calculate(getBillCardPanel(),
										l_BillEditEventTemp, getBillCardPanel()
												.getBillModel(TAB1),
										new int[] { getPricePolicy() },
										descriptions, keys, OrderItemVO.class
												.getName());

							}

						}
					}
				}

				if (lary_CtRetVO != null) {
					getBillCardPanel().setBodyValueAt(
							lary_CtRetVO[0].getCCurrencyId(), lint_rowindex,
							"ccurrencytypeid");
					String formula = "ccurrencytype->getColValue(bd_currtype,currtypename,pk_currtype,ccurrencytypeid)";
					getBillCardPanel().getBillModel(TAB1).execFormula(
							lint_rowindex, new String[] { formula });

					setCurrencyExchangeRateEnable(lint_rowindex);
					setCurrencyExchangeRate(lint_rowindex);

					// 设置汇率、金额等的精度
					setExchangeRateBody(lint_rowindex, true);

					getBillCardPanel().updateUI();
				}

				setRelateCntAndDefaultPrice(new int[] { lint_rowindex }, true);
				setNotEditableWhenRelateCnt(new int[] { lint_rowindex });
			}
			// 该段代码防止双击合同号参照的TEXT区域时，合同号清空的问题
			UIRefPane pane = ((UIRefPane) getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("ccontractrcode").getComponent());
			pane.setPK(getBillCardPanel().getBodyValueAt(lint_rowindex,
					"ccontractrowid"));

		} catch (Exception l_Exception) {

			showErrorMessage("" + l_Exception.getMessage());
			SCMEnv.out(l_Exception.getMessage());
			return;
		}
	}

	/**
	 * 编辑前处理。 创建日期：(2001-3-23 2:02:27)
	 * 
	 * @param e
	 *            ufbill.BillEditEvent
	 */
	public boolean beforeEdit(nc.ui.pub.bill.BillEditEvent e) {
		if (e.getKey().equals("vproducenum")) {

			nc.ui.scm.pu.ParaVOForBatch vo = new nc.ui.scm.pu.ParaVOForBatch();
			int iRow = e.getRow();
			BillCardPanel bcPanel = getBillCardPanel();

			// 传入FieldName
			vo.setMangIdField("cmangid");
			vo.setInvCodeField("cinventorycode");
			vo.setInvNameField("cinventoryname");
			vo.setSpecificationField("invspec");
			vo.setInvTypeField("invtype");
			vo.setMainMeasureNameField("cassistunitname");
			vo.setAssistUnitIDField("cassistunit");
			vo.setIsAstMg(new UFBoolean(InvTool
					.isAssUnitManaged((String) bcPanel.getBodyValueAt(iRow,
							"cbaseid"))));

			vo.setWarehouseIDField("cwarehouseid");
			vo.setFreePrefix("vfree");

			// 设置卡片模板,公司等
			vo.setCardPanel(getBillCardPanel());
			vo.setPk_corp(getCorpPrimaryKey());
			vo.setEvent(e);

			try {
				nc.ui.sc.pub.ScTool.beforeEditWhenBodyBatch(vo);
			} catch (Exception ex) {
				SCMEnv.out(ex.getMessage());
			}
		}
		// 仓库
		else if (e.getKey().equals("cwarehousename")) {
			((UIRefPane) getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("cwarehousename").getComponent())
					.setPk_corp(getCorpPrimaryKey());
			BillPanelTool.restrictWarehouseRefByStoreOrg(getBillCardPanel(),
					getCorpPrimaryKey(), (String) getBillCardPanel()
							.getHeadItem("cwareid").getValueObject(),
					"cwarehousename");
		}

		// 存货
		else if (e.getKey().equals("cinventorycode")) {

			// 停止编辑!!!
			getBillCardPanel().stopEditing();

			int iRow = e.getRow();
			String sClassId = null;
			String sCntRowId = null;
			// 上层来源为合同
			String sUpSourceType = (String) getBillCardPanel().getBodyValueAt(
					iRow, "cupsourcebilltype");
			if (sUpSourceType != null
					&& (sUpSourceType.equals(BillTypeConst.CT_BEFOREDATE) || sUpSourceType
							.equals(BillTypeConst.CT_ORDINARY))) {
				sCntRowId = (String) getBillCardPanel().getBodyValueAt(iRow,
						"cupsourcebillrowid");

			} else {
				// 2009-9-30 renzhonghai 如果有关联合同，则存货选择应按照合同存货分类过滤。
				sCntRowId = (String) getBillCardPanel().getBodyValueAt(iRow,
						"ccontractrowid");
			}
			if (sCntRowId != null) {
				RetCtToPoQueryVO voCntInfo = PoPublicUIClass
						.getCntInfo(sCntRowId);
				if (voCntInfo != null
						&& voCntInfo.getIinvCtl() == RetCtToPoQueryVO.INVCLASSCTL) {
					// 存货大类合同的存货只能在该类中
					sClassId = voCntInfo.getCInvClass();
				}
			}

			String tab = getSelectTab();
			UIRefPane ref = (UIRefPane) getBillCardPanel().getBillModel(tab)
					.getItemByKey("cinventorycode").getComponent();
			ref.setPk_corp(getPk_corp());
			if (sClassId == null) {
				ref.getRefModel().setChangeTableSeq(true);
				// 如果存货分PK为空，则显示所有存货
				((InvmandocDefaultRefModel) ref.getRefModel())
						.setClassWherePart("");
			} else {
				// 关闭“常用条件”页签
				ref.getRefModel().setChangeTableSeq(false);
				// 如果存货分PK不为空，则按存货分类PK过滤参照
				Object[] obj = null;
				try {
					obj = (Object[]) CacheTool.getColumnValue("bd_invcl",
							"pk_invcl", "invclasscode",
							new String[] { sClassId });
				} catch (BusinessException ex) {
					// 日志异常
					nc.vo.scm.pub.SCMEnv.out(ex);
					showErrorMessage(ex.getMessage());
				}
				String invclasscode = obj != null && obj.length == 1
						&& obj[0] != null ? "'" + obj[0].toString() + "%'"
						: "'%'";
				String invClassWherePart = " bd_invcl.invclasscode like "
						+ invclasscode;
				((InvmandocDefaultRefModel) ref.getRefModel())
						.setClassWherePart(invClassWherePart);
			}
		}
		// 合同号
		else if (e.getKey().equals(ScConstants.SC_ORDER_BODY_CT_CODE)) {
			int iRow = e.getRow();
			// 设置公司ID、供应商ID、存货ID、日期设置合同号参照
			UIRefPane pane = ((UIRefPane) getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("ccontractrcode").getComponent());
			if (pane.getRefModel() != null) {
				pane.getRefModel().clearData();
			}
			pane.setRefModel(new nc.ui.ct.ref.ValiCtRefModel(
					getBillCardPanel().getHeadItem("pk_corp").getValue(),
					getBillCardPanel().getHeadItem("cvendorid").getValue(),
					(String) getBillCardPanel().getBodyValueAt(iRow, "cbaseid"),
					new UFDate(getBillCardPanel().getHeadItem("dorderdate")
							.getValue()), new Boolean(true)));
		} else if (e.getKey().equals("cprojectphasename")) {
			/******* 修改项目阶段时，根据项目是否有值，来确定是否可以编辑 ************/
			Object oTemp = getBillCardPanel().getBodyValueAt(e.getRow(),
					"cprojectid");
			if (oTemp != null) {
				UIRefPane refPane = ((UIRefPane) getBillCardPanel()
						.getBillModel(TAB1).getItemByKey("cprojectphasename")
						.getComponent());
				refPane.setRefModel(new nc.ui.sc.pub.ProjectPhase(oTemp
						.toString()));
				getBillCardPanel()
						.getBillData()
						.getBillModel(TAB1)
						.setCellEditable(
								e.getRow(),
								"cprojectphasename",
								getBillCardPanel().getBillData()
										.getBillModel(TAB1)
										.getItemByKey("cprojectphasename")
										.isEdit());
			} else {
				getBillCardPanel()
						.getBillData()
						.getBillModel(TAB1)
						.setCellEditable(e.getRow(), "cprojectphasename", false);
			}
		}
		return true;
	}

	/*
	 * 功能：换行操作：新增、修改、单据转入
	 */
	public void bodyRowChange(BillEditEvent e) {
		// 如果为增行操作,则作出相应的处理
		if (isAddedLine) {
			afterAppendLine();
			isAddedLine = false;
			return;
		}
		try {
			String tab = getSelectTab();// gc
			if (tab.equals(TAB1)) {
				// modify by hanbin 2009-11-16
				// 原因：控制精度代码原本放在方法尾部，但是中间有return语句，导致可能不能被执行
				// 控制精度
				Precision precision = new Precision(getBillCardPanel()
						.getBillModel(TAB1), getBillCardPanel().getBillTable(
						TAB1));
				precision.bodyRowChange(e, new String[] { "nexchangeotobrate",
						"noriginalcurmny", "noriginaltaxmny",
						"noriginalsummny", "nmoney", "ntaxmny", "nsummny" });
			}

			if (m_billState != ScConstants.STATE_ADD
					&& m_billState != ScConstants.STATE_MODIFY
					&& m_billState != ScConstants.STATE_OTHER)
				return;

			int rowindex = e.getRow();
			if (rowindex < 0) {
				return;
			}

			if (tab.equals(TAB1)) {
				// 项目阶段
				Object cprojectid = getBillCardPanel().getBodyValueAt(rowindex,
						"cprojectid");
				if (cprojectid == null
						|| cprojectid.toString().trim().equals(""))
					getBillCardPanel().setCellEditable(rowindex,
							"cprojectphasename", false);
				else {
					UIRefPane ref = (UIRefPane) getBillCardPanel()
							.getBillModel(TAB1)
							.getItemByKey("cprojectphasename").getComponent();
					// ref.setRefModel(new
					// CTIPhaseRefModel(cprojectid.toString()));
					ref.setRefModel(new nc.ui.sc.pub.ProjectPhase(cprojectid
							.toString()));
					getBillCardPanel().setCellEditable(rowindex,
							"cprojectphasename", true);

				}

				// 设置折本是否可编辑
				setCurrencyExchangeRateEnable(rowindex);

				// 存货是否可编辑（参照请购单则不可编辑）
				Object cupsourcebillrowid = getBillCardPanel().getBodyValueAt(
						rowindex, "cupsourcebillrowid");
				if (cupsourcebillrowid != null
						&& !cupsourcebillrowid.toString().trim().equals("")) {

					// 上层来源为合同
					String sUpSourceType = (String) getBillCardPanel()
							.getBodyValueAt(rowindex, "cupsourcebilltype");
					if (sUpSourceType != null
							&& (sUpSourceType
									.equals(BillTypeConst.CT_BEFOREDATE) || sUpSourceType
									.equals(BillTypeConst.CT_ORDINARY))) {

						getBillCardPanel().setCellEditable(e.getRow(),
								"cinventorycode", false);
						String sCntRowId = (String) getBillCardPanel()
								.getBodyValueAt(rowindex, "cupsourcebillrowid");

						RetCtToPoQueryVO voCntInfo = PoPublicUIClass
								.getCntInfo(sCntRowId);
						if (voCntInfo != null) {

							if (voCntInfo.getIinvCtl() == RetCtToPoQueryVO.INVCLASSCTL
									|| voCntInfo.getIinvCtl() == RetCtToPoQueryVO.INVNULLCTL) {
								// 存货大类合同的存货可以编辑
								getBillCardPanel().setCellEditable(e.getRow(),
										"cinventorycode", true);
							}

						}
					}
				} else {
					getBillCardPanel().setCellEditable(e.getRow(),
							"cinventorycode", true);
				}
			}
			// 以下是存货的相关项
			Object pk_invbasdoc = getBillCardPanel().getBillModel(tab).getValueAt(rowindex,
					"cbaseid"); // 基本档案ID
			if (pk_invbasdoc == null) {
				getBillCardPanel().setCellEditable(e.getRow(),
						"cassistunitname", false,tab);
				getBillCardPanel().setCellEditable(e.getRow(), "measrate",
						false,tab);
				getBillCardPanel().setCellEditable(e.getRow(), "vfree0", false,tab);
				return;
			}

			getBillCardPanel().setCellEditable(e.getRow(), "cassistunitname",
					true,tab);
			getBillCardPanel().setCellEditable(e.getRow(), "measrate", true,tab);
			getBillCardPanel().setCellEditable(e.getRow(), "vfree0", true,tab);
			if (tab.equals(TAB1)) {
				// 本行辅计量单位参照
				Object bisAssist = getBillCardPanel().getBodyValueAt(rowindex,
						"isassist");// 是否辅计量管理
				if (bisAssist == null || bisAssist.equals("N")) {
					getBillCardPanel().setCellEditable(rowindex, "cassistunitname",
							false);
					getBillCardPanel().setCellEditable(e.getRow(), "measrate",
							false);
				} else {// 若为辅计量管理
					getBillCardPanel().setCellEditable(rowindex, "cassistunitname",
							true);
					getBillCardPanel()
							.setCellEditable(e.getRow(), "measrate", true);
					String cmangid = (String) getBillCardPanel().getBodyValueAt(
							rowindex, "cmangid"); // 当前存货管理档案ID
					UIRefPane measRef = (UIRefPane) getBillCardPanel()
							.getBillModel(TAB1).getItemByKey("cassistunitname")
							.getComponent();
					measRef.setText("");
					new InvMeasRate().filterMeas(getPk_corp(), cmangid, measRef);
				}
	
				// 换算率是否可编辑
				Object pk_assmeasdoc = getBillCardPanel().getBodyValueAt(rowindex,
						"cassistunit"); // 辅单位
				if (pk_assmeasdoc == null
						|| pk_assmeasdoc.toString().trim().equals("")) {
					getBillCardPanel().setCellEditable(e.getRow(), "measrate",
							false);
				} else {
					// 获得是否固定换算率、与主计量换算率、
					BD_ConvertVO convertVO[] = null;
					if (pk_invbasdoc != null
							&& pk_invbasdoc.toString().trim().length() > 0
							&& pk_assmeasdoc != null
							&& pk_assmeasdoc.toString().trim().length() > 0) {
						convertVO = OrderHelper.findBd_Converts(
								new String[] { (String) pk_invbasdoc },
								new String[] { (String) pk_assmeasdoc });
					}
	
					if (convertVO != null && convertVO.length > 0) {
	
						UFBoolean fixedflag = convertVO[0].getBfixedflag();
						if (fixedflag == null) {
							getBillCardPanel().setCellEditable(e.getRow(),
									"measrate", false);
						} else {
							if (fixedflag.booleanValue())
								getBillCardPanel().setCellEditable(e.getRow(),
										"measrate", false);
							else
								getBillCardPanel().setCellEditable(e.getRow(),
										"measrate", true);
						}
					} else {
						getBillCardPanel().setCellEditable(e.getRow(), "measrate",
								false);
					}
				}
			}
			// 显示自由项
			nc.vo.scm.ic.bill.InvVO invVO = new nc.vo.scm.ic.bill.InvVO();
			invVO = (nc.vo.scm.ic.bill.InvVO) getBillCardPanel()
					.getBillModel(tab).getValueAt(rowindex, "invvo");//
			if (invVO == null || invVO.getFreeItemVO() == null
					|| !BillEdit.definedFreeItem(invVO.getFreeItemVO())) {
				getBillCardPanel().setCellEditable(rowindex, "vfree0", false,tab);
			} else {
				getBillCardPanel().setCellEditable(rowindex, "vfree0", true,tab);
				FreeItemRefPane freeRef = (FreeItemRefPane) getBillCardPanel()
						.getBillModel(tab).getItemByKey("vfree0")
						.getComponent();
				freeRef.setFreeItemParam(invVO);
			}
		} catch (Exception t) {
			SCMEnv.out(t.getMessage());
		}
	}

	/**
	 * 计算主辅币（折本、折辅、本币金额、辅币金额）refer invoice
	 */
	private boolean calNativeAndAssistCurrValue(OrderVO orderVO) {

		// 计算汇率：取订单日期
		String strRateDate = ((OrderHeaderVO) orderVO.getParentVO())
				.getDorderdate().toString();

		UFDouble nmoney, nsummny;

		String strLocalCurrId = null;
		//
		try {
			strLocalCurrId = getCurrParamQuery().getLocalCurrPK(getPk_corp());
			//
			if (strLocalCurrId == null) {
				MessageDialog.showErrorDlg(
						this,
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "错误"
																	 */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
								"UPP401201-000036")/*
													 * @res "未指定本位币，币种折算错误"
													 */);
				return false;
			}
			OrderItemVO[] itemVOs = (OrderItemVO[]) orderVO.getChildrenVO();
			for (int i = 0; i < itemVOs.length; i++) {
				OrderItemVO itemVO = itemVOs[i];

				// =============本币金额(nmoney):根据源币种主键、目的币种主键、源币种金额、用户指定的新汇率、用于查询汇率的日期得到本币金额；注：这里取的是"币种"里的"财务系统精度"(ICurrRateConst.CURRDIGIT_FINANCE)
				nmoney = getCurrencyRateUtil().getAmountByOpp(
						itemVO.getCcurrencytypeid(), strLocalCurrId,
						itemVO.getNoriginalcurmny(),
						itemVO.getNexchangeotobrate(), strRateDate);

				// =============原币价税合计(noriginalsummny)
				nsummny = null;
				if (itemVO.getNoriginalsummny() != null) {
					nsummny = getCurrencyRateUtil().getAmountByOpp(
							itemVO.getCcurrencytypeid(), strLocalCurrId,
							itemVO.getNoriginalsummny(),
							itemVO.getNexchangeotobrate(), strRateDate);
				}
				// 设值
				itemVOs[i].setNmoney(nmoney);
				itemVOs[i].setNsummny(nsummny);
				itemVOs[i].setNtaxmny((nsummny == null ? new UFDouble(0)
						: nsummny).sub(nmoney == null ? new UFDouble(0)
						: nmoney));
			}
			// 赋值
			orderVO.setChildrenVO(itemVOs);
			m_curOrderVO = orderVO;

		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "错误"
																	 */, e
					.getMessage());
			return false;
		}

		return true;

	}

	/**
	 * 清空列表表体 创建日期：(2001-9-24 18:20:09)
	 */
	private void clearList() {
		getBillListPanel().getBodyBillModel().clearBodyData();
		getBillListPanel().getParentListPanel().clearSelect();
		getBillListPanel().getHeadTable().clearSelection();
		//
		boEdit.setEnabled(false);
		boDel.setEnabled(false);
		boReturn.setEnabled(true);
		setGroupButtonsState(UFBoolean.FALSE);

		int rowCount = getBillListPanel().getHeadTable().getSelectedRowCount();
		if (rowCount > 0) {
			boDocument.setEnabled(true);
			m_btnOthersFuncs.setEnabled(true);
		}
		if (rowCount == 1) {
			boEdit.setEnabled(true);
			boDel.setEnabled(true);
			setGroupButtonsState(UFBoolean.TRUE);
		}

		updateButtons();
	}

	/**
	 * 作者：yye 功能：填充表头公式数据，避免不可参照出的数据 参数： 返回： 例外： 日期：(2004-5-20 14:18:41)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @param vo
	 *            nc.vo.pr.pray.PraybillVO
	 */
	public void execHeadTailFormula(OrderVO vo) {
		if (vo == null) {
			return;
		}
		// 单个公式
		OrderHeaderVO voHead = (OrderHeaderVO) vo.getParentVO();
		UIRefPane refpane = null;

		FormulaParse forParse = new FormulaParse();
		forParse.setExpress("getColValue(bd_calbody,bodyname,pk_calbody,cwareid)");
		forParse.addVariable("cwareid", voHead.getCwareid());
		String saRet = forParse.getValue();

		refpane = (UIRefPane) getBillCardPanel().getHeadItem("cwareid")
				.getComponent();

		if (PuPubVO.getString_TrimZeroLenAsNull(refpane.getUITextField()
				.getText()) == null) {
			if (saRet != null)
				refpane.getUITextField().setText(saRet);
		}
	}

	/**
	 * 获得按钮动作。 创建日期：(2001-11-15 8:56:16)
	 * 
	 * @return java.lang.String
	 * @param bo
	 *            nc.ui.pub.ButtonObject
	 */
	public String getBillButtonAction(ButtonObject bo) {
		String strAction = null;
		if (bo == boAction)
			strAction = "scorder001";
		return strAction;
	}

	/**
	 * 此处插入方法说明。 功能描述:返回状态组 输入参数: 返回值: 异常处理: 日期:
	 * 
	 * @return java.lang.String
	 */
	public String getBillButtonState() {
		return "scorder001";
	}

	/**
	 * 单据模板卡片Panel
	 */
	private BillCardPanel getBillCardPanel() {
		if (m_BillCardPanel == null) {
			try {

				m_BillCardPanel = new BillCardPanel();
				m_BillCardPanel.setName("CardPanel");
				// 设置卡片模板VO
				m_BillCardPanel.setBillData(new BillData(getBillTempletVO()));

				// 事件监听
				m_BillCardPanel.addEditListener(this);
				m_BillCardPanel.addBodyMouseListener(this);
				m_BillCardPanel.addBodyEditListener2(this);
				m_BillCardPanel.addBodyMenuListener(this);
				
				m_BillCardPanel.addEditListener(TAB2, this);//gc
				m_BillCardPanel.addBodyMouseListener(TAB2,this);//gc
				m_BillCardPanel.addBodyEditListener2(TAB2,this);//gc
				m_BillCardPanel.addBodyMenuListener(TAB2,this);//gc

				// 单据动作事件监听
				m_BillCardPanel.addActionListener(this);
				m_BillCardPanel.addActionListener(TAB2,this);//gc


				//
				setNormalPrecision2();
				initComboBox();
				initRefer();

				// 处理自定义项
				ScTool.changeBillDataByUserDef(m_BillCardPanel, getPk_corp(),
						ScmConst.SC_Order, getOperatorID());

				// 初始化行号
				nc.ui.scm.pub.report.BillRowNo.loadRowNoItem(m_BillCardPanel,
						"crowno");

				// 显示供应商的简称
				BillItem l_billitem = m_BillCardPanel
						.getHeadItem("cvendormangid");
				UIRefPane pane = (UIRefPane) l_billitem.getComponent();
				pane.getRef().getRefModel()
						.setRefNameField("bd_cubasdoc.custshortname");

				// V55，支持整行选中
				SCMPuTool.setLineSelected(m_BillCardPanel);

				// user code end
			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.toString());
				handleException(ivjExc);
			}
		}
		return m_BillCardPanel;
	}

	/**
	 * 通过单据号管理获得当前单据号（只有在保存前生成） 创建日期：(2001-9-19 11:06:43)
	 * 
	 * @return java.lang.String
	 */
	private String getBillCode() throws Exception {

		try {
			String vordercode = getBillCardPanel().getHeadItem("vordercode")
					.getValue();
			if (vordercode == null || vordercode.toString().trim().equals(""))
				vordercode = null;

			String cwareid = getBillCardPanel().getHeadItem("cwareid")
					.getValue();
			String cdeptid = getBillCardPanel().getHeadItem("cdeptid")
					.getValue();
			String cpurid = getBillCardPanel().getHeadItem("cpurorganization")
					.getValue();
			String cbiztype = getBillCardPanel().getBusiType();

			BillCodeObjValueVO objVO = new BillCodeObjValueVO();
			objVO.setAttributeValue("公司", getPk_corp());/* -=notranslate=- */
			objVO.setAttributeValue("采购组织", cpurid);/* -=notranslate=- */
			objVO.setAttributeValue("库存组织", cwareid);/* -=notranslate=- */
			objVO.setAttributeValue("部门", cdeptid);/* -=notranslate=- */
			objVO.setAttributeValue("业务类型", cbiztype);/* -=notranslate=- */

			String billcode = BillcodeRuleBO_Client.getBillCode(
					ScmConst.SC_Order, getPk_corp(), vordercode, objVO);
			getBillCardPanel().getHeadItem("vordercode").setValue(vordercode);
			getBillCardPanel().updateUI();
			return billcode;

		} catch (Exception e) {
			SCMEnv.out(e);
			throw new Exception(e.getMessage());
		}

	}

	/**
	 * 返回列表。
	 * 
	 * @return nc.ui.pub.bill.BillCardPanel
	 */
	/* 警告：此方法将重新生成。 */
	private OrderListPanel getBillListPanel() {
		if (m_ScOrderListPanel == null) {
			try {
				m_ScOrderListPanel = new OrderListPanel(this);
				m_ScOrderListPanel.setName("ListPanel");

				// 设置列表模板VO
				m_ScOrderListPanel.setListData(new BillListData(
						getBillTempletVO()));

				// initIDs();
				m_ScOrderListPanel.hideTableCol();
				m_ScOrderListPanel.updateUI();

				m_ScOrderListPanel.setNormalPrecision();
				// 处理自定义项
				ScTool.changeListDataByUserDef(m_ScOrderListPanel,
						getPk_corp(), ScmConst.SC_Order, getOperatorID());

				m_ScOrderListPanel.addMouseListener(this);
				m_ScOrderListPanel.getHeadTable().getSelectionModel()
						.addListSelectionListener(m_ScOrderListPanel);

				// 设置为多行选择模式
				m_ScOrderListPanel
						.getHeadTable()
						.setSelectionMode(
								javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

				// 设置列表合计
				m_ScOrderListPanel.getChildListPanel().setTotalRowShow(true);

				m_ScOrderListPanel.getHeadBillModel()
						.addSortRelaObjectListener(this);

				UIComboBox discountTaxType = (UIComboBox) m_ScOrderListPanel
						.getBodyItem("idiscounttaxtype").getComponent(); //
				discountTaxType.addItem(ScConstants.TaxType_Including);// 应税内含
				discountTaxType.addItem(ScConstants.TaxType_Not_Including);// 应税外加
				discountTaxType.addItem(ScConstants.TaxType_No);// 不计税
				m_ScOrderListPanel.getBodyItem("idiscounttaxtype")
						.setWithIndex(true);

			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return m_ScOrderListPanel;
	}

	/**
	 * 获取币种汇率工具对象
	 * 
	 * @return nc.ui.bd.b21.CurrencyRateUtil 币种汇率工具
	 */
	private nc.bs.bd.b21.CurrencyRateUtil getCurrencyRateUtil() {
		if (m_CurrArith == null) {
			m_CurrArith = new nc.bs.bd.b21.CurrencyRateUtil(getPk_corp());
		}
		return m_CurrArith;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-8-2 13:24:19)
	 * 
	 * @return java.lang.String
	 */
	public String getModuleCode() {
		return "401201";
		// return getModuleCode();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-8-2 13:21:20)
	 * 
	 * @return java.lang.String
	 */
	private String getOperatorID() {
		return nc.ui.pub.ClientEnvironment.getInstance().getUser()
				.getPrimaryKey();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-8-2 12:42:41)
	 * 
	 * @return java.lang.String
	 */
	private String getPk_corp() {
		return nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
				.getPk_corp();
	}

	/**
	 * 查询条件
	 */
	private OrderUIQueryDlg getQryCondition() {
		return getQryCondition(null);
	}

	/**
	 * 查询条件
	 */
	private OrderUIQueryDlg getQryCondition(String strPkCorp) {
		if (m_query == null) {
			try {
				if (strPkCorp != null && !strPkCorp.trim().equals("")) {// 指定单据公司
					m_query = new OrderUIQueryDlg(this, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000002")/* @res "订单" */,
							getOperatorID(), strPkCorp, getModuleCode());
				} else {// 指定公司为空，则取当前登陆公司
					m_query = new OrderUIQueryDlg(this, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000002")/* @res "订单" */,
							getOperatorID(), getPk_corp(), getModuleCode());
				}

				// 加载自定义项名称
				nc.ui.scm.pub.def.DefSetTool.updateQueryConditionClientUserDef(
						m_query, getPk_corp(), ScmConst.SC_Order,
						"sc_order.vdef", // 单据模板中单据头的自定义项前缀
						"sc_order_b.vdef" // 单据模板中单据体的自定义项前缀
				);

			} catch (java.lang.Throwable ivjExc) {
				SCMEnv.out(ivjExc.toString());
				handleException(ivjExc);
			}
		}

		return m_query;
	}

	private ArrayList getSelectedBills() {

		java.util.ArrayList<OrderVO> arrOrderVO = new java.util.ArrayList<OrderVO>();

		if (m_billState == ScConstants.STATE_CARD) {// 卡片状态下，设置BillListPanel的当前单据为选中状态
			arrOrderVO.add(getSelectedVO());
			return arrOrderVO;
		}

		try {
			OrderHeaderVO[] l_SelOrderHeaderVOs = (OrderHeaderVO[]) getBillListPanel()
					.getHeadBillModel().getBodySelectedVOs(
							"nc.vo.sc.order.OrderHeaderVO");

			if (l_SelOrderHeaderVOs == null || l_SelOrderHeaderVOs.length <= 0) {
				return null;
			} else if (l_SelOrderHeaderVOs.length == 1) { // 列表界面只选择一条记录
				arrOrderVO.add(getSelectedVO());
				return arrOrderVO;
			}
			for (int i = 0; i < l_SelOrderHeaderVOs.length; i++) {
				OrderItemVO[] l_OrderItemVOs = getModel().getOrderItems(
						l_SelOrderHeaderVOs[i].getCorderid());
				OrderVO order = new OrderVO();
				order.setParentVO(l_SelOrderHeaderVOs[i]);
				order.setChildrenVO(l_OrderItemVOs);
				arrOrderVO.add(order);

				// gc
				OrderDdlbVO[] orderddlbvos = getModel().getOrderDdlbs(
						l_SelOrderHeaderVOs[i].getCorderid());
				order.setDdlbvos(orderddlbvos);
				arrOrderVO.add(order);
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000039")/* @res "发现未知错误" */);
		}
		return arrOrderVO;

	}

	/**
	 * 界面排序按decimal的规则排序。则BillListPanel需实现如下监听nc.ui.pub.bill.
	 * IbillModelSortPrepareListener。 作者：yye 功能：接口IBillModelSortPrepareListener
	 * 的实现方法 参数：String sItemKey ITEMKEY 返回：无 例外：无 日期：(2004-03-24 11:39:21)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public int getSortTypeByBillItemKey(String sItemKey) {

		if ("crowno".equals(sItemKey)) {
			return BillItem.DECIMAL;
		}

		String tab = getSelectTab();// gc
		return getBillCardPanel().getBillModel(tab).getItemByKey(sItemKey)
				.getDataType();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-1-22 15:20:11)
	 * 
	 * @return java.lang.String
	 * @param cstorhouseid
	 *            java.lang.String
	 */
	private String getStorAdd(String cstorhouseid) {
		String addr = null;
		try {
			addr = PublicHelper.getStorAddr(cstorhouseid);
		} catch (Exception e) {
		}
		return addr;
	}

	/**
	 * 获得当前模版标题
	 * 
	 * @return String
	 * @exception
	 * @roseuid 3ADE500100C4
	 */
	public String getTitle() {
		String title = nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
				"UPP401201-000040")/* @res "委外加工订单" */;
		if (m_BillCardPanel != null)
			title = m_BillCardPanel.getTitle();
		return title;

	}

	/**
	 * 每当部件抛出异常时被调用
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	public void handleException(java.lang.Throwable exception) {

		/* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
		// SCMEnv.out("--------- 未捕捉到的异常 ---------");
	}

	/**
	 * 初始化 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010254
	 */
	private void init() {

		setName("ScOrder");

		initpara();
		cardLayout = new java.awt.CardLayout();
		setLayout(cardLayout);
		add(getBillCardPanel(), getBillCardPanel().getName());
		add(getBillListPanel(), getBillListPanel().getName());

		// V51重构需要的匹配,按钮实例变量化
		createBtnInstances();

		// 增加重排行号右键菜单
		UIMenuItem newItem = BillTools.addMultiTableBodyMenuItem(
				getBillCardPanel(), boReOrderRowNo, null).get(
				getBillCardPanel().getCurrentBodyTableCode());
		if (newItem != null) {
			newItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					onReOrderRowNo();
				}
			});
			miReOrderRowNo = newItem;
		}

		// 增加行操作右键菜单项"卡片编辑"
		newItem = BillTools.addMultiTableBodyMenuItem(getBillCardPanel(),
				boCardEdit, null).get(
				getBillCardPanel().getCurrentBodyTableCode());
		if (newItem != null) {
			newItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					onBoCardEdit();
				}
			});
			miCardEdit = newItem;
		}

		//gc
		getBillCardPanel().addTabbedPaneTabChangeListener(this,BillItem.BODY);
		initButtons();
		initState();
		getBillCardPanel().setBillData(getBillCardPanel().getBillData());

		updateUI();

		// 业务类型处理
		nc.ui.pub.pf.PfUtilClient.retBusiAddBtn(boBusitype, boAdd,
				getPk_corp(), ScmConst.SC_Order);
		if (boBusitype.getChildButtonGroup() != null
				&& boBusitype.getChildButtonGroup().length != 0) {
			boBusitype.setCheckboxGroup(true);
			boBusitype.getChildButtonGroup()[0].setSelected(true);
		}

		for (int i = 0; i < boAction.getChildButtonGroup().length; i++) {
			if (boAction.getChildButtonGroup()[i] != null) {
				if (boAction.getChildButtonGroup()[i].getTag() != null) {
					if (boAction.getChildButtonGroup()[i].getTag().equals(
							"APPROVE"))
						boAudit = boAction.getChildButtonGroup()[i];
					if (boAction.getChildButtonGroup()[i].getTag().equals(
							"UNAPPROVE"))
						boUnaudit = boAction.getChildButtonGroup()[i];
				}
			}
		}

		getPoPubSetUi2();
		setMaxMnyDigit(iMaxMnyDigit);
		getBillCardPanel().setBodyMenuShow(true);

		// 初始化自由项着色器
		initFreeItemCellRenderer("vfree0", "invvo");
	}

	private void initFreeItemCellRenderer(String freeItemKey,
			String invvoItemKey) {
		BillItem bt = getBillCardPanel().getBillModel(TAB1).getItemByKey(
				freeItemKey);
		if (!bt.isShow())
			return;
		String title = bt.getName();
		UITable table = getBillCardPanel().getBillTable(TAB1);// gc
		TableColumn column = table.getColumn(title);
		column.setCellRenderer(new FreeItemCellRenderer(getBillCardPanel(),
				invvoItemKey));

		// gc复制一份
		BillItem bt1 = getBillCardPanel().getBillModel(TAB2).getItemByKey(
				freeItemKey);
		if (!bt1.isShow())
			return;
		String title1 = bt1.getName();
		UITable table1 = getBillCardPanel().getBillTable(TAB2);
		TableColumn column1 = table1.getColumn(title1);
		column1.setCellRenderer(new FreeItemCellRenderer(getBillCardPanel(),
				invvoItemKey));
	}

	/**
	 * 为了减少初始化时前后台交互的次数，一次性获取多个系统参数 作者:袁野 日期：2005-04-21
	 * 
	 */
	private void initpara() {
		try {
			Hashtable t = nc.ui.pub.para.SysInitBO_Client.queryBatchParaValues(
					getPk_corp(), new String[] { "SC04", "BD301" });
			if (t != null && t.size() > 0) {
				Object temp = null;
				if (t.get("SC04") != null) {
					// 获取价格优先策略
					temp = t.get("SC04");
					if (temp.toString().trim().equals("无税价格优先")) {
						m_iPricePolicy = RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE;
					} else {
						m_iPricePolicy = RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE;
					}
				}

				if (t.get("BD301") != null) {
					// 取得默认币种
					temp = t.get("BD301");
					m_sPKCurrencyType = temp.toString();
				}
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040502", "UPP40040502-000052")/*
																 * @res
																 * "获取系统初始化参数出错"
																 */, e
					.getMessage());
		}
	}

	/**
	 * 按钮初始化
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500102CD
	 */
	private void initButtons() {

		// 执行组按钮
		if (boAction.getChildCount() == 0) {
			boAudit.setTag("APPROVE");
			boUnaudit.setTag("UNAPPROVE");
			// boAction.addChildButton(boAudit);
			// boAction.addChildButton(boUnaudit);
		}

		if (m_billState == ScConstants.STATE_LIST) {
			setButtons(m_btnTree.getButtonArray());// 设置列表组按钮
			boReturn.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH021")/* @res "卡片显示" */);
		} else {
			setButtons(m_btnTree.getButtonArray());// 设置卡片组按钮
			boReturn.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH022")/* @res "列表显示" */);
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-8-1 13:05:32)
	 */
	private void initComboBox() {
		try {
			UIComboBox discountTaxType = (UIComboBox) getBillCardPanel()
					.getBillModel(TAB1).getItemByKey("idiscounttaxtype")
					.getComponent(); //
			// discountTaxType.addItem("应税外加");
			// discountTaxType.addItem("应税内含");
			// discountTaxType.addItem("不计税");
			discountTaxType.addItem(ScConstants.TaxType_Including);// 应税内含
			discountTaxType.addItem(ScConstants.TaxType_Not_Including);// 应税外加
			discountTaxType.addItem(ScConstants.TaxType_No);// 不计税
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("idiscounttaxtype").setWithIndex(true);
		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * 由价格审批单生成时调用的界面
	 */
	private void processAfterChange(String sUpBillType,
			AggregatedValueObject[] voaSourceVO) {

		if (sUpBillType.equals(ScmConst.PO_PriceAudit)) {

			/** **************1、得到转换后的OrderVO****************** */
			OrderVO[] voaRet = null;// 转换后的OrderVO
			// -------------询价单转订单
			// 按表体业务类型+供应商分单
			PriceauditMergeVO[] voaAskBill = (PriceauditMergeVO[]) SplitBillVOs
					.getSplitVOs(PriceauditMergeVO.class.getName(),
							PriceauditHeaderVO.class.getName(),
							PriceauditItemMergeVO.class.getName(),
							(AggregatedValueObject[]) voaSourceVO, null,
							new String[] { "cbiztype", "cvendormangid" });
			// 设置订单VO数组：转换，其中进行表体业务类型到表头业务类型的转换
			try {
				voaRet = (OrderVO[]) PfChangeBO_Client.pfChangeBillToBillArray(
						voaAskBill, ScmConst.PO_PriceAudit, ScmConst.SC_Order);
			} catch (Exception e) {
				PuTool.outException(this, e);
				voaRet = null;
			}

			if (voaRet == null) {
				return;
			}

			/** **************2、处理界面逻辑****************** */

			onAddFromRef(voaRet);

			nc.ui.scm.sourcebill.SourceBillTool.loadSourceInfoAll(
					getBillListPanel().getBodyBillModel(), ScmConst.SC_Order);

		}
	}

	/**
	 * 函数说明：初始化参照 创建日期：(2001-10-25 16:05:08)
	 */
	private void initRefer() {

		// 供应商条件
		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("cvendorid")
				.getComponent();
		ref.setWhereString(" bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' and bd_cumandoc.frozenflag='N' and (bd_cumandoc.custflag='1' or bd_cumandoc.custflag='3' )");

		// 收货方
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("creciever")
				.getComponent();
		ref.setWhereString(" bd_cumandoc.frozenflag='N' and  bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' AND (bd_cumandoc.custflag='0' OR bd_cumandoc.custflag='2') ");

		// 发票方
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("cgiveinvoicevendor")
				.getComponent();
		ref.setWhereString("  bd_cumandoc.frozenflag='N' and bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' AND (bd_cumandoc.custflag='1' OR bd_cumandoc.custflag='3') ");

		// 采购组织
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("cpurorganization")
				.getComponent();
		ref.getRefModel().addWherePart(
				" and bd_purorg.ownercorp='" + getPk_corp() + "' ");

		// 加工品
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cinventorycode").getComponent();
		ref.setWhereString(" bd_invmandoc.pk_corp='" + getPk_corp()
				+ "' and bd_invmandoc.sealflag='N' ");
		ref.setTreeGridNodeMultiSelected(true);
		ref.setMultiSelectedEnabled(true);
		// 仓库
		// ref = (UIRefPane)
		// getBillCardPanel().getBillModel(TAB1).getItemByKey("cwarehousename").getComponent();
		// ref.setReturnCode(false);
		// ref.setRefInputType(1);
		// ref.setWhereString(" bd_stordoc.gubflag='N' and
		// bd_stordoc.sealflag='N' and bd_stordoc.pk_corp='" + getPk_corp() + "'
		// ");

		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cwarehousename").getComponent();
		ref.setRefModel(new WarehouseRefModel(getCorpPrimaryKey()));
		ref.getRefModel()
				.addWherePart(
						" and UPPER(bd_stordoc.gubflag) <> 'Y' and UPPER(bd_stordoc.sealflag) <> 'Y' ");
		//gc 材料
		// 加工品
				ref = (UIRefPane) getBillCardPanel().getBillModel(TAB2)
						.getItemByKey("cinventorycode").getComponent();
				ref.setWhereString(" bd_invmandoc.pk_corp='" + getPk_corp()
						+ "' and bd_invmandoc.sealflag='N' ");
				ref.setTreeGridNodeMultiSelected(true);
				ref.setMultiSelectedEnabled(true);
				// 仓库
				// ref = (UIRefPane)
				// getBillCardPanel().getBillModel(TAB1).getItemByKey("cwarehousename").getComponent();
				// ref.setReturnCode(false);
				// ref.setRefInputType(1);
				// ref.setWhereString(" bd_stordoc.gubflag='N' and
				// bd_stordoc.sealflag='N' and bd_stordoc.pk_corp='" + getPk_corp() + "'
				// ");

//				ref = (UIRefPane) getBillCardPanel().getBillModel(TAB2)
//						.getItemByKey("cwarehousename").getComponent();
//				ref.setRefModel(new WarehouseRefModel(getCorpPrimaryKey()));
//				ref.getRefModel()
//						.addWherePart(
//								" and UPPER(bd_stordoc.gubflag) <> 'Y' and UPPER(bd_stordoc.sealflag) <> 'Y' ");
		//gc end

		// 项目
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cprojectname").getComponent();
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		// ref.setWhereString(" bd_jobmngfil.sealflag='N' and
		// bd_jobmngfil.pk_corp ='" + getPk_corp() + "' ");
		ref.setWhereString(" bd_jobmngfil.pk_corp ='" + getPk_corp() + "' ");
		// 币种
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("ccurrencytype").getComponent();
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		// 辅计量单位
		ref = new UIRefPane();
		ref.setIsCustomDefined(true);
		ref.setRefModel(new OtherRefModel(OtherRefModel.REF_ASSMEAS));
		ref.setCacheEnabled(false);
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		getBillCardPanel().getBillModel(TAB1).getItemByKey("cassistunitname")
				.setComponent(ref);
		
		//gc 
		ref = new UIRefPane();
		ref.setIsCustomDefined(true);
		ref.setRefModel(new OtherRefModel(OtherRefModel.REF_ASSMEAS));
		ref.setCacheEnabled(false);
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		getBillCardPanel().getBillModel(TAB2).getItemByKey("cassistunitname")
				.setComponent(ref);
		//gc-end
		// 项目阶段
		ref = new UIRefPane();
		ref.setIsCustomDefined(true);
		// ref.setRefModel(new CTIPhaseRefModel());
		ref.setRefModel(new nc.ui.sc.pub.ProjectPhase());
		ref.setCacheEnabled(false);
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		getBillCardPanel().getBillModel(TAB1).getItemByKey("cprojectphasename")
				.setComponent(ref);
		// //客商开户银行
		// ref = new UIRefPane();
		// ref.setIsCustomDefined(true);
		// ref.setRefModel(new
		// nc.ui.bd.ref.busi.BankAccCustDefaultRefModel("客商银行账户"));
		// ref.setCacheEnabled(false);
		// ref.setReturnCode(false);
		// ref.setRefInputType(0);
		//
		// String cvendorid =
		// getBillCardPanel().getHeadItem("cvendorid").getValue();
		//
		// // 取和帐户基本档案关联的ID
		// ref.getRefModel().addWherePart(
		// " and bd_bankaccbas.pk_bankaccbas in (select k.pk_accbank from
		// bd_custbank k,bd_cumandoc m where"
		// + " m.pk_corp='"+getCorpPrimaryKey()+"'"// 指定公司的客商
		// + " and k.pk_cubasdoc=m.pk_cubasdoc"// 客商的开户帐户
		// + " and k.pk_cubasdoc='"+cvendorid+"')");
		//
		// getBillCardPanel().getHeadItem("caccountbankid").setComponent(ref);

		// 表头、表体备注（不进行自动检查）
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo").getComponent();
		ref.setAutoCheck(false);
		ref.setReturnCode(false);

		ref = (UIRefPane) m_BillCardPanel.getBodyItem("vmemo").getComponent();
		ref.setTable(getBillCardPanel().getBillTable(TAB1));// gc 新加页不处理
		ref.getRefModel().setRefCodeField(ref.getRefModel().getRefNameField());
		ref.getRefModel().setBlurFields(
				new String[] { ref.getRefModel().getRefNameField() });
		ref.setAutoCheck(false);

		// 表头库存组织
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("cwareid").getComponent();
		ref.setWhereString(" property <> 2 and pk_corp = '"
				+ getCorpPrimaryKey() + "'");

		BillData billData = m_BillCardPanel.getBillData();
		// 自由项
		FreeItemRefPane freeRef = new FreeItemRefPane();
		freeRef.setMaxLength(billData.getBodyItem(TAB1,"vfree0").getLength());
		billData.getBodyItem(TAB1,"vfree0").setComponent(freeRef);
		//gc
		FreeItemRefPane freeRef1 = new FreeItemRefPane();
		freeRef1.setMaxLength(billData.getBodyItem(TAB2,"vfree0").getLength());
		billData.getBodyItem(TAB2,"vfree0").setComponent(freeRef1);//gc
		//gc end
		// 初始化合同的参照
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey(ScConstants.SC_ORDER_BODY_CT_CODE).getComponent();
		ref.setRefModel(new ValiCtRefModel(null, null, null, null, true));

		// 业务流程
		ref = (UIRefPane) getBillCardPanel().getHeadItem("cbiztype")
				.getComponent();
		ref.setRefModel(new ScBizTypeRefModel(
				PoPublicUIClass.getLoginPk_corp(), BillTypeConst.SC_ORDER,
				false));

		// 部门
		ref = (UIRefPane) getBillCardPanel().getHeadItem("cdeptid")
				.getComponent();
		ScDeptRefModel refDeptModel = new ScDeptRefModel(getPk_corp());
		ref.setRefModel(refDeptModel);

		// 业务员(采购部门的)
		ref = (UIRefPane) getBillCardPanel().getHeadItem("cemployeeid")
				.getComponent();
		ref.setRefModel(new ScPsnRefModel(getPk_corp(), getBillCardPanel()
				.getHeadItem("cdeptid").getValue()));

		LotNumbRefPane lotRef = new LotNumbRefPane();
		lotRef.setMaxLength(billData.getBodyItem("vproducenum").getLength());
		billData.getBodyItem(TAB1,"vproducenum").setComponent(lotRef);
		
		//gc
		LotNumbRefPane lotRef1 = new LotNumbRefPane();
		lotRef1.setMaxLength(billData.getBodyItem("vproducenum").getLength());
		billData.getBodyItem(TAB2,"vproducenum").setComponent(lotRef1);
		//gcned
		m_BillCardPanel.setBillData(billData);
	}

	private void initState() {
		// 不使用合计行
		getBillCardPanel().setTatolRowShow(true);
		// 设置单据控件为不可编辑
		getBillCardPanel().setEnabled(false);

		cardLayout.first(this);

		setButtonsState();
		updateButtons();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-1-25 12:32:59)
	 */
	private void loadCustBank() {
		getBillCardPanel()
				.execHeadFormula(
						"cvendorid->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,cvendormangid)");
		String cvendorid = getBillCardPanel().getHeadItem("cvendorid")
				.getValue();
		String pkcorp = getBillCardPanel().getCorp();
		String sWherePart = " and bd_bankaccbas.pk_bankaccbas in (select  k.pk_accbank from bd_custbank k,bd_cumandoc m   where   m.pk_corp ='"
				+ pkcorp + "'  and k.pk_cubasdoc=m.pk_cubasdoc ";
		if (nc.vo.scm.pu.PuPubVO.getString_TrimZeroLenAsNull(cvendorid) != null) {
			sWherePart += "and k.pk_cubasdoc='" + cvendorid + "'";
		}
		sWherePart += ")";
		UIRefPane ref = (UIRefPane) getBillCardPanel().getHeadItem(
				"caccountbankid").getComponent();
		// 修改人：renzhonghai 修改内容：参照不存在要判断
		if (ref.getRefModel() != null) {
			ref.getRefModel().addWherePart(sWherePart);
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-4-23 9:17:37)
	 */
	private void loadData(String billID) {
		try {
			setMaxMnyDigit(iMaxMnyDigit);
			if (billID != null && !"onAudit".equals(billID)) {
				m_curOrderVO = getModel().getOrder(billID);
				getModel().add((OrderHeaderVO) m_curOrderVO.getParentVO());
			} else if ("onAudit".equals(billID)) {
				OrderVO vo = (OrderVO) getBillCardPanel().getBillValueChangeVO(
						OrderVO.class.getName(), OrderHeaderVO.class.getName(),
						OrderItemVO.class.getName());
				m_curOrderVO.setChildrenVO(vo.getChildrenVO());
				if(vo.getDdlbvos() == null)
					m_curOrderVO.setDdlbvos(vo.getDdlbvos());//gc
			} else if (getModel().size() > 0) {
				m_curOrderVO = getModel().getCurrentOrder();
			}

			if (m_curOrderVO == null) {
				getBillCardPanel().getBillData().clearViewData();
				return;
			}

			// 计算含税单价
			loadCustBank();

			// 处理自定义项
			OrderHeaderVO voHead = (OrderHeaderVO) m_curOrderVO.getParentVO();
			String[] saKey = new String[] { "vdef1", "vdef2", "vdef3", "vdef4",
					"vdef5", "vdef6", "vdef7", "vdef8", "vdef9", "vdef10" };
			int iLen = saKey.length;
			for (int i = 0; i < iLen; i++) {
				JComponent component = getBillCardPanel().getHeadItem(saKey[i])
						.getComponent();
				if (component instanceof UIRefPane) {
					voHead.setAttributeValue(saKey[i],
							((UIRefPane) component).getText());
				}
			}

//			getBillCardPanel().setBillValueVO(m_curOrderVO);//gc-old
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(m_curOrderVO);
			exBillVO.setParentVO((OrderHeaderVO)m_curOrderVO.getParentVO());
			exBillVO.addTableVO(TAB1,"", m_curOrderVO.getChildrenVO());
			exBillVO.addTableVO(TAB2,"", m_curOrderVO.getDdlbvos());
			getBillCardPanel().setBillValueVO(exBillVO);
			//gc-end
			
			loadFreeItems();

			m_sHeadVmemo = ((OrderHeaderVO) m_curOrderVO.getParentVO())
					.getVmemo();
			UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
					.getComponent();
			ref.setText(m_sHeadVmemo);

			long s1 = System.currentTimeMillis();

			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().getBillModel(TAB2).execLoadFormula();//gc
			SCMEnv.out("执行公式[共用时" + (System.currentTimeMillis() - s1) + "]");/*
																			 * -=
																			 * notranslate
																			 * =
																			 * -
																			 */

			// 显示扣税类别
			OrderItemVO[] orderItemVO = (OrderItemVO[]) m_curOrderVO
					.getChildrenVO();
			for (int i = 0; i < orderItemVO.length; i++) {
				Integer idiscounttaxtype = orderItemVO[i].getIdiscounttaxtype();

				getBillCardPanel().getBillModel(TAB1).setValueAt(
						idiscounttaxtype, i, "idiscounttaxtype");

			}

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000253")/* @res "数据加载成功" */);

			// 维护界面状态（作废、修改）
			int ibillstate = ((OrderHeaderVO) m_curOrderVO.getParentVO())
					.getIbillstatus().intValue();

			for (int i = 0; i < boAction.getChildButtonGroup().length; i++) {
				if (boAction.getChildButtonGroup()[i] != null) {
					if (boAction.getChildButtonGroup()[i].getTag() != null) {
						if (boAction.getChildButtonGroup()[i].getTag().equals(
								"APPROVE"))
							boAudit = boAction.getChildButtonGroup()[i];
						if (boAction.getChildButtonGroup()[i].getTag().equals(
								"UNAPPROVE"))
							boUnaudit = boAction.getChildButtonGroup()[i];
					}
				}
			}
			setOperateState(ibillstate);

			// 加载上层和源头单据信息
			nc.ui.scm.sourcebill.SourceBillTool.loadSourceInfoAll(
					getBillCardPanel().getBillModel(TAB1), ScmConst.SC_Order);
			// 正常显示已封存得库存组织
			execHeadTailFormula(m_curOrderVO);

		} catch (Exception e) {
			SCMEnv.out("数据加载失败");
			SCMEnv.out(e);
		}

	}

	/**
	 * 加载自由项。 创建日期：(2001-9-28 11:09:44)
	 */
	private void loadFreeItems() {
		// 加载自由项
		
		try {
			String []tabs = new String[]{TAB1,TAB2};
				for (int j = 0; j < tabs.length; j++) {
				// 判断是否需要执行公式,条件：如果存货id不为null，而存货编码为null，则执行加载公式 add by hanbin
				// 2009-9-11
				if (getCardBodyValueAt(0, "cmangid",tabs[j]) != null
						&& getCardBodyValueAt(0, "cinventorycode",tabs[j]) == null)
					this.getBillCardPanel().getBillModel(tabs[j]).execLoadFormula();
	
				String[] invMangIdArray = this.getInvMangIdArrayFromView(tabs[j]);
				InvTool.loadBatchFreeVO(invMangIdArray);
	
				nc.vo.scm.ic.bill.InvVO invVO = new nc.vo.scm.ic.bill.InvVO();
				for (int i = 0, len = getBillCardPanel().getRowCount(); i < len; i++) {
					String invMangId = (String) getBillCardPanel().getBillModel(tabs[j]).getValueAt(
							i, "cmangid");
					FreeVO freeVO = InvTool.getInvFreeVO(invMangId);
	
					if ((freeVO == null) || (freeVO.getCinventoryid() == null)) {
						continue;
					}
	
					// modify by hanbin 2009-9-11 原因:当自由项只有一个内容时，编辑时，内容显示错误
					freeVO.setVfree1((String) getCardBodyValueAt(i, "vfree1",tabs[j]));
					freeVO.setVfree2((String) getCardBodyValueAt(i, "vfree2",tabs[j]));
					freeVO.setVfree3((String) getCardBodyValueAt(i, "vfree3",tabs[j]));
					freeVO.setVfree4((String) getCardBodyValueAt(i, "vfree4",tabs[j]));
					freeVO.setVfree5((String) getCardBodyValueAt(i, "vfree5",tabs[j]));
	
					String pk_invmangdoc = (String) getCardBodyValueAt(i, "cmangid",tabs[j]);
					String pk_invbasedoc = (String) getCardBodyValueAt(i, "cbaseid",tabs[j]);
					String cinventorycode = (String) getCardBodyValueAt(i,
							"cinventorycode",tabs[j]);
					String cinventoryname = (String) getCardBodyValueAt(i,
							"cinventoryname",tabs[j]);
					String invspec = (String) getCardBodyValueAt(i, "invspec",tabs[j]);
					String invtype = (String) getCardBodyValueAt(i, "invtype",tabs[j]);
	
					invVO = new nc.vo.scm.ic.bill.InvVO();
					invVO.setFreeItemVO(freeVO);
					invVO.setCinvmanid(pk_invbasedoc);
					invVO.setCinventoryid(pk_invmangdoc);
	
					invVO.setIsFreeItemMgt(new Integer(1));
					invVO.setCinventorycode(cinventorycode);
					invVO.setInvname(cinventoryname);
					invVO.setInvspec(invspec);
					invVO.setInvtype(invtype);
	
					getBillCardPanel().getBillModel(tabs[j]).setValueAt(freeVO.getVfree0(), i,
							"vfree0");
					getBillCardPanel().getBillModel(tabs[j]).setValueAt(invVO, i, "invvo");
				}
			}
				
		} catch (Exception e) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000022")/* @res "加载自由项失败" */);
			SCMEnv.out(e);
		}

	}

	/**
	 * 表体逐行显示换算率 创建日期：(2001-9-17 17:30:05)
	 */
	private void loadMeasRate() {// gc 不需要处理
		try {
			String[] saBaseId = (String[]) PuGetUIValueTool.getArray(
					getBillCardPanel().getBillModel(TAB1), "cbaseid",
					String.class, 0, getBillCardPanel().getRowCount());
			String[] saAssistUnit = (String[]) PuGetUIValueTool.getArray(
					getBillCardPanel().getBillModel(TAB1), "cassistunit",
					String.class, 0, getBillCardPanel().getRowCount());

			// 批量加载
			PuTool.loadBatchInvConvRateInfo(saBaseId, saAssistUnit);

			int j = 0;
			int rowcount = getBillCardPanel().getRowCount();
			for (int i = 0; i < rowcount; i++) {
				Object pk_invbasdoc = getBillCardPanel().getBodyValueAt(i,
						"cbaseid");
				Object pk_measdoc = getBillCardPanel().getBodyValueAt(i,
						"cassistunit");
				Object nordernum = getBillCardPanel().getBodyValueAt(i,
						"nordernum");
				Object nassistnum = getBillCardPanel().getBodyValueAt(i,
						"nassistnum");
				Object pk_mainbeasdoc = getBillCardPanel().getBodyValueAt(i,
						"pk_measdoc");

				if (pk_invbasdoc != null
						&& pk_invbasdoc.toString().trim().length() > 0
						&& pk_measdoc != null
						&& pk_measdoc.toString().trim().length() > 0
						&& pk_mainbeasdoc != null
						&& pk_mainbeasdoc.toString().trim().length() > 0) {
					if (pk_measdoc.toString().trim()
							.equals(pk_mainbeasdoc.toString().trim())) {
						// 如果主副计量相同，换算率显示1
						getBillCardPanel().setBodyValueAt("1", i, "measrate");
					}
					j++;
				}

				if (nordernum != null && nassistnum != null
						&& nordernum.toString().trim().length() != 0
						&& nassistnum.toString().trim().length() != 0) {
					UFDouble rate;
					// 如果是固定换算率，则取固定换算率；否则 换算率 = 主数量 / 辅数量
					if (PuTool.isFixedConvertRate(
							PuPubVO.getString_TrimZeroLenAsNull(pk_invbasdoc),
							PuPubVO.getString_TrimZeroLenAsNull(pk_measdoc))) {
						rate = InvTool
								.getInvConvRateValue(
										PuPubVO.getString_TrimZeroLenAsNull(pk_invbasdoc),
										PuPubVO.getString_TrimZeroLenAsNull(pk_measdoc));
					} else {
						UFDouble x = new UFDouble(nordernum.toString());
						UFDouble y = new UFDouble(nassistnum.toString());
						rate = x.div(y);
					}
					getBillCardPanel().setBodyValueAt(rate, i, "measrate");
					continue;
				}

				if (PuPubVO.getString_TrimZeroLenAsNull(pk_measdoc) == null) {
					getBillCardPanel().setBodyValueAt("", i, "measrate");
					continue;
				}
			}
		} catch (Exception t) {
			SCMEnv.out(t.getMessage());
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2002-1-21 14:09:17)
	 * 
	 * @param oneVO
	 *            nc.vo.sc.order.OrderVO
	 */
	private void loadReferListByCust(OrderVO[] VOs,
			Map<String, List<Object>> filledInfos) {
		for (OrderVO orderVO : VOs) {
			OrderHeaderVO headVO = (OrderHeaderVO) orderVO.getParentVO();
			OrderItemVO[] itemVOs = (OrderItemVO[]) orderVO.getChildrenVO();
			String cvendormangid = headVO.getCvendormangid();
			if (cvendormangid == null || cvendormangid.trim().equals(""))
				return;

			try {
				List<Object> filledInfo = filledInfos.get(cvendormangid);

				CustDefaultVO custVO = (CustDefaultVO) filledInfo.get(0);
				if (PuPubVO.getString_TrimZeroLenAsNull(custVO.getCdeptid()) == null) {// 若供应商主管部门为空，则取转单过来的默认部门
					headVO.setCdeptid(((OrderHeaderVO) orderVO.getParentVO())
							.getCdeptid());
				} else {
					headVO.setCdeptid(custVO.getCdeptid());
				}
				// headVO.setCemployeeid(custVO.getCpsnid());//comment by yye
				// 2005-03-23 当由外部单据转入时，不能自动定义业务员，用来源单据的业务员
				headVO.setCgiveinvoicevendor(custVO.getCcumandoc2());
				headVO.setCtermProtocolid(custVO.getCpayterm());
				headVO.setCtransmodeid(custVO.getCsendtype());
				headVO.setCvendorid(custVO.getCvendorid());
				String curencytype = custVO.getCcurrtype();
				for (int i = 0; i < itemVOs.length; i++) {
					String ccurencytype = itemVOs[i].getCcurrencytypeid();
					if (ccurencytype == null || ccurencytype.trim().equals(""))
						itemVOs[i].setCcurrencytypeid(curencytype);
				}

				String cbankid = (String) filledInfo.get(1);
				headVO.setCaccountbankid(cbankid);

				getBillCardPanel().updateUI();
			} catch (Exception t) {
				SCMEnv.out(t.getMessage());
			}
		}
	}

	public void mouse_doubleclick(nc.ui.pub.bill.BillMouseEnent e) {
		// 单据转入状态
		if (e.getPos() == 0 && m_billState == ScConstants.STATE_OTHER) {
			getBillCardPanel().stopEditing();
			setMaxMnyDigit(iMaxMnyDigit);
			onModify();
			getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);// 置光标到表头第一个可编辑项目
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000072")/* @res "编辑单据" */);
			return;
		}
		// 普通列表状态
		if (e.getPos() == BillItem.HEAD) {
			onDoubleClick();

			// 设置精度
			setPrecision();
		} else if (m_billState == ScConstants.STATE_ADD
				|| m_billState == ScConstants.STATE_MODIFY) {
		}
	}

	/**
	 * 增加新单据(自制单据) *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001016E
	 */
	private void onAdd() {
		getBillCardPanel().addNew();
		getBillCardPanel().setEnabled(true);
		getBillCardPanel().setTatolRowShow(true);

		// 表头默认项（订单日期、制单人、公司、业务类型、会计年度）
		try {
			getBillCardPanel().getHeadItem("dorderdate").setValue(
					nc.ui.pub.ClientEnvironment.getInstance().getDate()
							.toString());
			getBillCardPanel().getHeadItem("pk_corp").setValue(getPk_corp());
			if (getBillCardPanel().getBusiType() != null
					&& getBillCardPanel().getBusiType().trim().length() > 0) {
				getBillCardPanel().getHeadItem("cbiztype").setValue(
						getBillCardPanel().getBusiType());
			} else {
				getBillCardPanel().getHeadItem("cbiztype").setValue(
						boBusitype.getSelectedChildButton()[0].getTag());
			}

			getBillCardPanel().getHeadItem("caccountyear").setValue(
					nc.ui.pub.ClientEnvironment.getInstance().getAccountYear());

			getBillCardPanel().getTailItem("coperator").setValue(
					getOperatorID());

			// since v51, 设置业务员默认值 根据操作员带出业务员
			setDefaultValueByUser();

		} catch (Exception e) {
			SCMEnv.out(e);
		}
		//
		m_billState = ScConstants.STATE_ADD;
		setButtonsState();

		rightButtonRightControl();
		onAppendLine();
		cardLayout.first(this);
		setCntRelatedItemEditableInCardHead();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
				"UPP401201-000071")/* @res "增加、编辑单据" */);
	}

	/**
	 * 单据转入增加新单据 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001016E
	 */
	private void onAddFromRef(OrderVO[] VOs) {

		if (VOs == null || VOs.length == 0)
			return;

		// 转到列表界面
		// 行号处理
		nc.ui.scm.pub.report.BillRowNo.setVOsRowNoByRule(VOs,
				ScmConst.SC_Order, "crowno");

		m_billState = ScConstants.STATE_OTHER;
		cardLayout.last(this);
		setButtons(m_btnTree.getButtonArray());// 设置列表组按钮
		updateUI();
		boEdit.setEnabled(true);
		setButtonsState();
		getBillListPanel().setState(m_billState);

		loadCustInfos(VOs);
		loadInvTaxRates(VOs);

		// 表头、表体默认值（订单日期、制单人、会计年度、扣税类别、行状态）
		for (int i = 0; i < VOs.length; i++) {
			OrderVO oneVO = VOs[i];
			OrderHeaderVO header = ((OrderHeaderVO) oneVO.getParentVO());
			header.setDorderdate(ClientEnvironment.getInstance().getDate());
			header.setCoperator(getOperatorID());
			header.setCaccountyear(ClientEnvironment.getInstance()
					.getAccountYear());

			OrderItemVO[] itemVO = (OrderItemVO[]) oneVO.getChildrenVO();
			String recieve = header.getCreciever();
			for (int j = 0; j < itemVO.length; j++) {
				// 设置是否辅计量
				if (itemVO[j].getCassistunit() != null) {
					itemVO[j].setAttributeValue("isassist", "Y");
				}

				// 设置主辅计量换算率
				UFDouble nordernum = itemVO[j].getNordernum();
				UFDouble nassistnum = itemVO[j].getNassistnum();
				if ((nordernum != null) && (nassistnum != null)) {
					itemVO[j].setAttributeValue("measrate",
							nordernum.div(nassistnum));
				}

				itemVO[j].setIdiscounttaxtype(new Integer(1));
				itemVO[j].setBisactive(UFBoolean.TRUE);
				// String cmangid = itemVO[j].getCmangid();
				// 存货默认项（税率）
				// 处理到货地址(收获方为空，取仓库默认地址)
				if (recieve == null) {
					String warehouseid = itemVO[j].getCwarehouseid();
					if (warehouseid == null || warehouseid.trim().equals(""))
						continue;

					String storaddr = itemVO[j].getCreceiveaddress();
					if (storaddr == null || storaddr.trim().equals("")) {
						String storadd = getStorAdd(warehouseid);
						itemVO[j].setCreceiveaddress(storadd);
					}
				}
			}
		}

		int num = VOs.length;
		ArrayList vecVO = new ArrayList();
		for (int i = 0; i < num; i++) {
			vecVO.add(VOs[i]);
		}
		comeVOs = vecVO;
		getBillListPanel().setComeVO(vecVO);
		getBillListPanel().loadHeadData();
	}

	/**
	 * 从后台加载客商信息
	 * 
	 * @param VOs
	 */
	private void loadCustInfos(OrderVO[] VOs) {
		List<String> cvendormangidList = new ArrayList<String>();
		for (OrderVO orderVO : VOs) {
			OrderHeaderVO headVO = (OrderHeaderVO) orderVO.getParentVO();
			String cvendormangid = headVO.getCvendormangid();
			if ((cvendormangid != null) && (cvendormangid.length() > 0)) {
				cvendormangidList.add(cvendormangid);
			}
		}

		Map<String, List<Object>> retInfos = null;
		try {
			retInfos = OrderBHelper.getMapByCustIds(cvendormangidList
					.toArray(new String[cvendormangidList.size()]));
		} catch (Exception t) {
			SCMEnv.out(t.getMessage());
		}

		loadReferListByCust(VOs, retInfos);
	}

	/**
	 * 从后台加载存货税率
	 * 
	 * @param VOs
	 */
	private void loadInvTaxRates(OrderVO[] VOs) {
		List<String> itemList = new ArrayList<String>();
		for (OrderVO orderVO : VOs) {
			OrderItemVO[] itemVO = (OrderItemVO[]) orderVO.getChildrenVO();
			// 存货默认项（税率 批次处理）
			for (int i = 0; i < itemVO.length; i++) {
				if (itemVO[i].getNtaxrate() == null) {
					itemList.add(itemVO[i].getCbaseid());
				}
			}
		}

		Map<String, UFDouble> taxRateMap = null;
		try {
			// 远程调用查询存货的税率
			taxRateMap = PublicHelper.getTaxRates(itemList
					.toArray(new String[itemList.size()]));
		} catch (Exception t) {
			SCMEnv.out(t.getMessage());
		}

		if (taxRateMap != null && taxRateMap.size() > 0) {
			for (OrderVO orderVO : VOs) {
				OrderItemVO[] itemVOs = (OrderItemVO[]) orderVO.getChildrenVO();
				for (OrderItemVO itemVO : itemVOs) {
					if (itemVO.getNtaxrate() == null) {
						itemVO.setNtaxrate(taxRateMap.get(itemVO.getCbaseid()));
					}
				}
			}
		}
	}

	/**
	 * 增行
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101BE
	 */
	private void onAppendLine() {
		getBillCardPanel().addLine();

		String tab = getSelectTab();
		// getBillCardPanel().getBillModel(TAB1).execLoadFormula();//gc-old
		getBillCardPanel().getBillModel(tab).execLoadFormula();// gc
		if (tab.equals(TAB1))// gc
			setPrecision();
		getBillCardPanel().updateUI();

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH036")/* @res "增行成功" */);
	}

	/**
	 * 方法功能描述：当用增行后,做一些值的预置处理。 <br>
	 * 从原来的onAppendLine方法中抽出
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author zhaoyha
	 * @time 2008-8-14 下午04:14:14
	 */
	private void afterAppendLine() {
		// 行号处理
		nc.ui.scm.pub.report.BillRowNo.addLineRowNo(getBillCardPanel(),
				ScmConst.SC_Order, "crowno");
		int row = getBillCardPanel().getRowCount() - 1;
		// 表体默认项（扣率、扣税类别、行状态）
		String tab = getSelectTab();
		if(tab.equals(TAB1)){
			getBillCardPanel().setBodyValueAt(new Integer(1), row,
					"idiscounttaxtype");
			getBillCardPanel().setBodyValueAt("100", row, "ndiscountrate");
			getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "bisactive");
		}

		try {
			// 默认到货地址
			String sReceiver = (String) getCardHeadItemValue("creciever");
			if (!StringUtil.isEmptyWithTrim(sReceiver)) {
				String cvendorbaseid = PublicHelper.getCvendorbaseid(sReceiver
						.toString());
				String defaddr = PublicHelper.getVdefaddr(cvendorbaseid);
				getBillCardPanel().setBodyValueAt(defaddr, row,
						"creceiveaddress");
			}

			fillCurrencyTypeIdInCardBody(row);
			setCurrencyExchangeRateEnable(row);
			setCurrencyExchangeRate(row);

			// add by hanbin 2009-11-16 原因:卡片编辑中，点击增行，币种字段显示pk，不显示名称
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().updateUI();
		} catch (Exception e) {
			SCMEnv.out(e);
			this.showErrorMessage(e.toString());
		}
	}

	/**
	 * 填充第 rowIndex 行的币种ID。
	 * 
	 * 注： 经谢阳，唐江峰，讨论后，按照下面的算法设置币种ID 填充卡片界面表体币种ID的算法 1，当表头供应商为空时，不填充第 rowIndex
	 * 行的币种ID，用户可以自行更改币种。 2，当表头供应商不为空时，带出供应商默认的币种ID； 若供应商没有设置默认币种时，带出本位币。
	 * 
	 * @param rowIndex
	 */
	private void fillCurrencyTypeIdInCardBody(int rowIndex) {// gc 不需要处理
		try {
			// 若币种为空，则给出供应商默认币种
			String cvendormangid = (String) getCardHeadItemValue("cvendormangid");
			// 如果供应商不为空，才进行币种设置
			if (!StringUtil.isEmpty(cvendormangid)) {
				// 获取供应商默认交易币种
				String currentyTypeId = getCurrencyTypeId(cvendormangid);
				if (StringUtil.isEmpty(currentyTypeId)) {
					// 供应商默认交易币种为空，则取本位币
					getBillCardPanel().setBodyValueAt(m_sPKCurrencyType,
							rowIndex, "ccurrencytypeid");
				} else {
					// 设置供应商默认交易币种
					getBillCardPanel().setBodyValueAt(currentyTypeId, rowIndex,
							"ccurrencytypeid");
				}
			}

			// 设置币种汇率单元格的可编辑性
			setCurrencyExchangeRateEnable(rowIndex);

			// 设置币种汇率
			setCurrencyExchangeRate(rowIndex);
		} catch (Exception e) {
			SCMEnv.out(e);
			this.showErrorMessage(e.toString());
		}
	}

	/**
	 * 获取供应商对应的币种ID
	 * 
	 * @param cvendormangid
	 *            供应商ID
	 * @return 供应商对应的币种ID
	 */
	private String getCurrencyTypeId(String cvendormangid) {
		if (getCustDefaultVOMap().get(cvendormangid) == null) {
			try {
				CustDefaultVO vo = OrderBHelper.getDefaultByCust(cvendormangid);
				getCustDefaultVOMap().put(cvendormangid, vo);
			} catch (Exception e) {
				SCMEnv.out(e);
			}
		}

		return getCustDefaultVOMap().get(cvendormangid).getCcurrtype();
	}

	/**
	 * 获取供应商VO缓存，以Map形式存放，其中Map的主键为供应商管理档案ID
	 * 
	 * @return 供应商VO缓存
	 */
	private Map<String, CustDefaultVO> getCustDefaultVOMap() {
		if (custDefaultVOMap == null) {
			custDefaultVOMap = new HashMap<String, CustDefaultVO>();
		}
		return custDefaultVOMap;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-7-26 12:50:18)
	 */
	public void onAudit() {
		try {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH048")/* @res "正在审核" */);

			OrderVO vo = (OrderVO) getBillCardPanel().getBillValueVO(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());

			// V31SP1:增加审批日期不能小于制单日期限制
			String strErr = PuTool.getAuditLessThanMakeMsg(
					new OrderVO[] { vo }, "dorderdate", "vordercode",
					nc.ui.pub.ClientEnvironment.getInstance().getDate());
			if (strErr != null) {
				throw new BusinessException(strErr);
			}

			((OrderHeaderVO) vo.getParentVO())
					.setDauditdate(nc.ui.pub.ClientEnvironment.getInstance()
							.getBusinessDate());
			((OrderHeaderVO) vo.getParentVO()).setCauditpsn(getOperatorID());
			((OrderHeaderVO) vo.getParentVO()).setCcuroperator(getOperatorID());
			((OrderHeaderVO) vo.getParentVO()).setTaudittime((new UFDateTime(
					new Date())).toString());

			Object[] retCellValue = (Object[]) CacheTool.getCellValue(
					"bd_busitype", "pk_busitype", "verifyrule",
					((OrderHeaderVO) vo.getParentVO()).getCbiztype());
			if (("N").equalsIgnoreCase((String) retCellValue[0])) {
				throw new BusinessException("业务类型核算规则为 N存货不处理类");
			}

			// 审批
			Object[] retVOs = PfUtilClient.runBatch(this, "APPROVE",
					ScmConst.SC_Order, nc.ui.pub.ClientEnvironment
							.getInstance().getDate().toString(),
					new OrderVO[] { vo }, null, null, null);

			if ((!PfUtilClient.isSuccess()) || (retVOs == null)
					|| (retVOs.length > 1)) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000043")/*
													 * @res "委外订单审批失败"
													 */);
				return;
			} else {
				m_curOrderVO = (OrderVO) retVOs[0];
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000234")/* "审批成功" */);
			}

			getModel().setHeaderAt(getModel().getCurrentIndex(),
					(OrderHeaderVO) m_curOrderVO.getParentVO());

			refreshBillCardPanel();

			setButtonsState();
		} catch (Exception e) {
			showErrorMessage(e.getMessage());
			SCMEnv.out(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000043")/* @res "委外订单审批失败" */);
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000234")/* "审批成功" */);
	}

	/**
	 * 列表下批审
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-4-13 上午11:08:08
	 */
	public void onAuditList() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000403")/* "正在审批单据" */);

		try {

			Vector vTmp = new Vector();
			BillModel bm = getBillListPanel().getHeadBillModel();
			for (int i = 0; i < bm.getRowCount(); i++) {
				if (BillModel.SELECTED == bm.getRowState(i)) {
					vTmp.addElement(new Integer(BillPanelTool
							.getIndexBeforeSort(getBillListPanel()
									.getHeadBillModel(), i)));
				}
			}
			if (vTmp.size() <= 0) {
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000006")/* @res "请选择要审批的单据" */);
				return;
			}
			Integer[] selectRowsInteger = null;
			selectRowsInteger = new Integer[vTmp.size()];
			vTmp.copyInto(selectRowsInteger);
			int[] selectRows = null;
			selectRows = new int[vTmp.size()];
			for (int i = 0; i < selectRowsInteger.length; i++) {
				selectRows[i] = selectRowsInteger[i].intValue();
			}
			//
			OrderVO[] VOs = (OrderVO[]) getBillListPanel().getMultiSelectedVOs(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());

			// V31SP1:增加审批日期不能小于制单日期限制
			String strErr = PuTool.getAuditLessThanMakeMsg(VOs, "dorderdate",
					"vordercode", nc.ui.pub.ClientEnvironment.getInstance()
							.getDate());
			if (strErr != null) {
				throw new BusinessException(strErr);
			}

			// 传入当前操作员，加业务锁用
			for (int i = 0; i < selectRows.length; i++) {
				OrderHeaderVO headVO = (OrderHeaderVO) VOs[i].getParentVO();
				headVO.setCcuroperator(getOperatorID());
				headVO.setCauditpsn(getOperatorID());
				headVO.setTaudittime((new UFDateTime(new Date())).toString());
				VOs[i].setChildrenVO(getModel().getOrderItems(
						headVO.getCorderid()));

			}

			PfUtilClient.processBatchFlow(null, "APPROVE" + getOperatorID(),
					"61", nc.ui.pub.ClientEnvironment.getInstance().getDate()
							.toString(), VOs, null);

			if (!PfUtilClient.isSuccess()) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000235")/* "审核失败" */);
				return;
			}

			refreshSelectedRows(selectRows, VOs);
			setButtonsState();

			getBillListPanel().getHeadBillModel().execLoadFormula();

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000234")/* "审批成功" */);
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000235")/*
																	 * @res
																	 * "审核失败"
																	 */, e
					.getMessage());
			getBillListPanel().getParentListPanel().clearSelect();
			SCMEnv.out(e);
		}

	}

	private void refreshSelectedRows(int[] selectedRows, OrderVO[] selectedVOs)
			throws Exception {
		StringBuilder sqlWhereBuilder = new StringBuilder();
		sqlWhereBuilder.append(" sc_order.corderid in (");
		for (int i = 0; i < selectedRows.length; i++) {
			sqlWhereBuilder.append("'");
			OrderHeaderVO headVO = (OrderHeaderVO) selectedVOs[i].getParentVO();
			sqlWhereBuilder.append(headVO.getPrimaryKey());
			sqlWhereBuilder.append("'");
			sqlWhereBuilder.append(",");
		}
		int length = sqlWhereBuilder.length();
		sqlWhereBuilder.deleteCharAt(length - 1);
		sqlWhereBuilder.append(") ");

		OrderHeaderVO[] headerVOs = OrderHelper.queryAllHead("",
				sqlWhereBuilder.toString(), new ClientLink(
						nc.ui.pub.ClientEnvironment.getInstance()), false);
		Map<String, OrderHeaderVO> headerMap = new HashMap<String, OrderHeaderVO>();
		for (OrderHeaderVO item : headerVOs) {
			headerMap.put(item.getPrimaryKey(), item);
		}

		for (int i = selectedRows.length - 1; i >= 0; i--) {
			OrderHeaderVO headerVO = getModel().getHeaderAt(selectedRows[i]);
			getModel().setHeaderAt(selectedRows[i],
					headerMap.get(headerVO.getPrimaryKey()));
		}

		getBillListPanel().setHeaderValueVO(getModel().getHeaderArray());
		getBillListPanel().getHeadTable().setRowSelectionInterval(
				selectedRows[selectedRows.length - 1],
				selectedRows[selectedRows.length - 1]);
	}

	/**
	 * 列表下委外订单弃审(批量)
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-4-13 上午11:24:52
	 */
	public void onUnAuditList() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000407")/*
													 * @res "正在弃审单据"
													 */);

		try {

			Vector vTmp = new Vector();
			BillModel bm = getBillListPanel().getHeadBillModel();
			for (int i = 0; i < bm.getRowCount(); i++) {
				if (BillModel.SELECTED == bm.getRowState(i)) {
					vTmp.addElement(new Integer(BillPanelTool
							.getIndexBeforeSort(getBillListPanel()
									.getHeadBillModel(), i)));
				}
			}
			if (vTmp.size() <= 0) {
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000011")/* @res "请选择要弃审的单据" */);
				return;
			}

			Integer[] selectRowsInteger = null;
			selectRowsInteger = new Integer[vTmp.size()];
			vTmp.copyInto(selectRowsInteger);
			int[] selectRows = null;
			selectRows = new int[vTmp.size()];
			for (int i = 0; i < selectRowsInteger.length; i++) {
				selectRows[i] = selectRowsInteger[i].intValue();
			}
			/*
			 * if (selectRows.length == 0) { showWarningMessage("请选择要弃审的单据！");
			 * return; }
			 */
			//
			OrderVO[] VOs = (OrderVO[]) getBillListPanel().getMultiSelectedVOs(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());

			// 传入当前操作员，加业务锁用
			for (int i = 0; i < selectRows.length; i++) {
				OrderHeaderVO headVO = (OrderHeaderVO) VOs[i].getParentVO();
				headVO.setCcuroperator(getOperatorID());
				headVO.setCauditpsn(getOperatorID());
				headVO.setTaudittime(null);
			}

			PfUtilClient.processBatch("UNAPPROVE" + getOperatorID(), "61",
					nc.ui.pub.ClientEnvironment.getInstance().getDate()
							.toString(), VOs);

			if (!PfUtilClient.isSuccess()) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000409")/*
															 * @res "弃审未成功"
															 */);
				return;
			}

			refreshSelectedRows(selectRows, VOs);
			setButtonsState();
			getBillListPanel().getHeadBillModel().execLoadFormula();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH011")/* @res "弃审成功" */);
		} catch (Exception e) {
			SCMEnv.out(e);
			getBillListPanel().getParentListPanel().clearSelect();
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000185")/*
																	 * @res
																	 * "弃审失败"
																	 */, e
					.getMessage());
		}

	}

	/**
	 * 单据联查 功能描述: 输入参数: 返回值: 异常处理: 日期:
	 */
	public void onBillLinkQuery() {

		String billID = getModel().getCurrentHeaderId();
		String bizType = (String) getBillCardPanel().getHeadItem("cbiztype")
				.getValueObject();
		String userID = getOperatorID();
		String billCode = getModel().getCurrentHeader().getVordercode();
		String billType = ScConstants.BILL_TYPE_SCORDER;

		nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new SourceBillFlowDlg(
				this, billType, billID, bizType, userID, billCode);

		soureDlg.showModal();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000019")/* @res "联查成功" */);

	}

	/**
	 * 相应按钮事件
	 * 
	 * @param btn
	 * @return void
	 * @exception
	 * @roseuid 3ADE500100EC
	 */
	public void onButtonClicked(ButtonObject btn) {

		getBillCardPanel().stopEditing();

		setMaxMnyDigit(iMaxMnyDigit);

		if (btn == boEdit) {
			onModify();
			getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);// 置光标到表头第一个可编辑项目
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000072")/* @res "编辑单据" */);
		} else if (btn.getParent() == boBusitype) {
			PfUtilClient.retAddBtn(boAdd, getPk_corp(), ScmConst.SC_Order, btn);
			setButtons(m_btnTree.getButtonArray());// 设置卡片组按钮
			getBillCardPanel().setBusiType(btn.getTag());
			boAdd.setEnabled(true);
			// 设置按钮
			showSelBizType(btn);
			updateButtons();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000065", null,
					new String[] { btn.getName() })/* @res "当前操作业务类型：" */);
		} else if (btn.getParent() == boAdd) {
			showHintMessage("正在增加...");
			String tag = btn.getTag();
			int index = tag.indexOf(":");
			String billType = tag.substring(0, index);
			String cBizTypeId = tag.substring(index + 1, tag.length());

			// 设置环境变量业务类型，UI端的VO交换可用用到
			ClientEnvironment.getInstance().putValue(OrderPubVO.ENV_BIZTYPEID,
					tag.substring(index + 1, tag.length()));
			if (billType.equals("20")) {
				SourceRefDlg.childButtonClicked(btn, getPk_corp(),
						getModuleCode(), getOperatorID(), ScmConst.SC_Order,
						this);// 请购单
			} else {
				PfUtilClient.childButtonClicked(btn, getPk_corp(),
						getModuleCode(), getOperatorID(), ScmConst.SC_Order,
						this);
			}

			if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
				onAdd();
				getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);// 置光标到表头第一个可编辑项目
			} else {
				boolean isCloseOK = false;
				if (billType.equals("20")) {
					isCloseOK = SourceRefDlg.isCloseOK();
				} else {
					isCloseOK = PfUtilClient.isCloseOK();
				}
				if (isCloseOK) {
					OrderVO[] VO = null;
					if (billType.equals("20")) {
						VO = (OrderVO[]) SourceRefDlg.getRetsVos();
					} else {
						VO = (OrderVO[]) PfUtilClient.getRetVos();
					}
					try {
						chgDataForOrderCorp(getBillCardPanel().getBusiType(),
								VO, billType);// 参照跨公司合同 情况下的公司间档案ID转换
					} catch (BusinessException e) {
						MessageDialog
								.showHintDlg(
										this,
										nc.ui.ml.NCLangRes.getInstance()
												.getStrByID("SCMCOMMON",
														"UPPSCMCommon-000270")/*
																			 * @res
																			 * "提示"
																			 */,
										e.getMessage());
						showHintMessage(e.getMessage());
						return;
					}

					// 设置参照上游VO生成VO的业务类型ID（即业务流程ID）
					setCbizTypeId(VO, cBizTypeId);
					onAddFromRef(VO);
				}
			}
			initButtons();
		} else if (btn == boCopy) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000350")/* "编辑单据..." */);
			onCopy();
		} else if (btn == boDel) {
			onDiscard();
		} else if (btn == boSave) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000134")/* @res "开始保存" */);
			onSave();
		} else if (btn == boCancel) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000205")/* @res "取消操作" */);
			onCancel();
		} else if (btn == boAddLine) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000465")/* @res "正在增加行" */);
			onAppendLine();
		} else if (btn == boDelLine) {
			onDelLine();
		} else if (btn == boCopyLine) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH059")/* @res "正在复制行" */);
			onCopyLine();
		} else if (btn == boInsertLine) {
			String tab = getSelectTab();// gc
			int row = getBillCardPanel().getBillTable(tab).getSelectedRow();
			if (row > -1) {
				onInsertLine();
				getBillCardPanel().getBillModel(tab).execLoadFormula();
				getBillCardPanel().updateUI();
			} else {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000066")/* @res "请选择行" */);
			}
		} else if (btn == boPasteLine) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH060")/* @res "正在粘贴行" */);
			getBillCardPanel().removeEditListener();
			onPasteLine();
			getBillCardPanel().addEditListener(this);
		} else if (btn == boPre) {
			onPrePage();
		} else if (btn == boFirst) {
			onFirstPage();
		} else if (btn == boNext) {
			onNextPage();
		} else if (btn == boLast) {
			onLastPage();
		} else if (btn == boPrint) {
			onPrint();
		} else if (btn == btnBillCombin) {
			onBillCombin();
		} else if (btn == boQueryForAudit) {
			onQueryForAudit();
		} else if (btn == boSendAudit) {
			onSendAudit();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH023")/* @res "已送审" */);
		} else if (btn == m_btnPrintPreview) {
			onPrintPreview();
		} else if (btn == boQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000360")/* "开始查询..." */);
			onQuery();
		} else if (btn == boReturn) {
			if (m_billState == ScConstants.STATE_CARD) {
				onList();
			} else if (m_billState == ScConstants.STATE_LIST
					|| m_billState == ScConstants.STATE_OTHER) {
				onCard();
			}
		} else if (btn == boSelectAll) {
			onSelectAll();
		} else if (btn == boSelectNo) {
			onSelectNo();
		} else if (btn == boFrash) {
			onFresh();
		} else if (btn == boDocument) {
			onDocument();
		} else if (btn == boAudit || btn.getCode().equals("审批")) {/*
																 * -=notranslate=
																 * -
																 */
			if (m_billState == ScConstants.STATE_LIST) {
				onAuditList();
			}
			if (m_billState == ScConstants.STATE_CARD) {
				onAudit();
			}
		} else if (btn == boUnaudit) {
			if (m_billState == ScConstants.STATE_LIST) {
				onUnAuditList();
			}
			if (m_billState == ScConstants.STATE_CARD) {
				onUnAudit();
			}
		} else if (btn == boCancelOut) {
			onCancelOut();
		} else if (btn == boLocate) {
			onLocate();
		} else if (btn == boLinkQuery) {
			onBillLinkQuery();
		} else if (btn == boPasteLineToTail) {
			onPasteLineToTail();
		} else if (btn == boCardEdit) {
			onBoCardEdit();
		} else if (btn == boReOrderRowNo) {
			onReOrderRowNo();
		}
		setPrecision();
		if (btn == boPasteLine) {
			String tab = getSelectTab();// gc,不处理按行控制精度的换行事件
			if(tab.equals(TAB1)){
				getPrecisionUtil().bodyRowChange(
						new BillEditEvent(btn, 0, getBillCardPanel().getBillTable(
								tab).getSelectedRow()),
						new String[] { "nexchangeotobrate", "noriginalcurmny",
								"noriginaltaxmny", "noriginalsummny", "nmoney",
								"ntaxmny", "nsummny" });
			}else{
//				getPrecisionUtil().bodyRowChange(
//						new BillEditEvent(btn, 0, getBillCardPanel().getBillTable(
//								tab).getSelectedRow()),
//						new String[] { "nexchangeotobrate", "noriginalcurmny",
//								"noriginaltaxmny", "noriginalsummny", "nmoney",
//								"ntaxmny", "nsummny" });
			}
		}
	}

	/**
	 * 设置业务流程ID <b>参数说明</b>
	 * 
	 * @param vos
	 * @param cBizTypeId
	 * @author songhy
	 * @time 2009-7-17 下午12:26:00
	 */
	private void setCbizTypeId(OrderVO[] vos, String cBizTypeId) {
		if (!VOChecker.isEmpty(vos)) {
			for (OrderVO item : vos) {
				((OrderHeaderVO) item.getParentVO()).setCbiztype(cBizTypeId);
			}
		}
	}

	/**
	 * 恢复存货参照的数据，因为取消前可能做了参照存货分类合同的采购订单
	 */
	private void resetInvmandocRefModel() {
		UIRefPane ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cinventorycode").getComponent();
		ref.setPk_corp(getPk_corp());
		((InvmandocDefaultRefModel) ref.getRefModel()).addWherePart(null);
	}

	/**
	 * 取消操作 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	public void onCancel() {
		try {
			// 处理表头备注
			getBillCardPanel().resumeValue();
			((UIRefPane) getBillCardPanel().getHeadItem("vmemo").getComponent())
					.setText(m_sHeadVmemo);

			resetInvmandocRefModel();
			getBillCardPanel().setEnabled(false);

			// 2009-9-28 renzhonghai 参照过来的单据如果没有业务流程则不能继续进行以后的操作。恢复初始状态
			getBillCardPanel().getHeadItem("cbiztype").setEdit(false);
			getBillCardPanel().getHeadItem("cbiztype").setNull(false);
			setCntRelatedItemEditableInCardHead();

			if (comeVOs == null || comeVOs.size() == 0)
				m_billState = ScConstants.STATE_CARD;

			boEdit.setEnabled(true);
			// 设置按钮状态
			setButtonsState();
			getBillListPanel().setState(m_billState);

			// 单据转入取消,若列表有数据，回到列表界面
			if (m_billState == ScConstants.STATE_OTHER) {
				cardLayout.last(this);
				setButtons(m_btnTree.getButtonArray());// 设置列表组按钮
				updateUI();
				setButtonsState();
				getBillListPanel().loadBodyData(0);
				// add by hanbin 原因：转单时，点击取消按钮后，默认选中列表下的第一条记录
				getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);
				return;
			}

			loadData(null);
			if (getModel().size() <= 0) {
				getBillCardPanel().getBillData().clearViewData();
				updateUI();
			}
		} catch (Exception e) {
			SCMEnv.out(e);
		}

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH008")/* "取消成功" */);

	}

	/**
	 * 取消操作 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	public void onCancelOut() {

		try {
			getBillListPanel().getComeVO().clear();

			getBillListPanel().loadHeadData();
			// getBillList
			m_billState = ScConstants.STATE_CARD;
			setButtonsState();

			getBillListPanel().setState(m_billState);

			cardLayout.first(this);
			initButtons();

			setPgUpDownButtonsState(getModel().getCurrentIndex());
			loadData(null);
		} catch (Exception e) {
			SCMEnv.out(e);
		}

	}

	/**
	 * 切换至卡片界面 创建日期：(2001-4-25 10:31:02)
	 */
	private void onCard() {
		// 单据转入
		if (m_billState == ScConstants.STATE_OTHER) {
			int rowindex = getBillListPanel().getHeadTable().getSelectedRow();
			if (rowindex == -1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000044")/* @res "请选中要修改的单据" */);
				return;
			}

			refLoadData(rowindex);
			return;
		}

		if (getBillListPanel().getHeadTable().getRowCount() > 0) {
			int rowSelected = getBillListPanel().getHeadTable()
					.getSelectedRowCount();
			if (rowSelected == 0 || rowSelected > 1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000045")/* @res "请选中一张单据" */);
				return;
			}
		} else {
			m_curOrderVO = null;
			getBillCardPanel().getBillData().clearViewData();
		}

		setPgUpDownButtonsState(getModel().getCurrentIndex());

		loadData(null);

		m_billState = ScConstants.STATE_CARD;
		cardLayout.first(this);
		initButtons();
		setButtonsState();
		updateUI();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH021")/* @res "卡片显示" */);

	}

	/**
	 * 此处插入方法说明。 功能描述:复制订单 输入参数: 返回值: 异常处理:
	 */
	private void onCopy() {
		if (getModel().size() == 0) {//gc
			return;
		}

		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
				.getComponent();
		String headVmemo = ref.getUITextField().getText();
		try {
			//
			OrderVO orderVO = getModel().getCurrentOrder();
			orderVO = (OrderVO) ObjectUtils.serializableClone(orderVO);
			((OrderHeaderVO) orderVO.getParentVO())
					.setDorderdate(nc.ui.pub.ClientEnvironment.getInstance()
							.getDate());
			((OrderHeaderVO) orderVO.getParentVO()).setVordercode(null);
			((OrderHeaderVO) orderVO.getParentVO()).setCorderid(null);
			((OrderHeaderVO) orderVO.getParentVO())
					.setCoperator(getOperatorID());
			((OrderHeaderVO) orderVO.getParentVO())
					.setIbillstatus(BillStatus.FREE);
			((OrderHeaderVO) orderVO.getParentVO()).setVmemo(headVmemo);
			// czp/2003/09/12/add begin
			((OrderHeaderVO) orderVO.getParentVO()).setCauditpsn(null);
			((OrderHeaderVO) orderVO.getParentVO()).setDauditdate(null);
			// czp/2003/09/12/add begin

			OrderItemVO[] itemVO = (OrderItemVO[]) orderVO.getChildrenVO();
			Vector vTemp = new Vector();// 换算率
			for (int i = 0; i < itemVO.length; i++) {
				itemVO[i].setCorderid(null);
				itemVO[i].setCorder_bid(null);
				itemVO[i].setCupsourcebillid(null);
				itemVO[i].setCupsourcebillrowid(null);
				itemVO[i].setCupsourcebilltype(null);
				itemVO[i].setCsourcebillid(null);
				itemVO[i].setCsourcebillrow(null);
				itemVO[i].setCordersource(null);
				// 清空累计回写信息
				itemVO[i].setNaccumarrvnum(null);
				itemVO[i].setNaccuminvoicenum(null);
				itemVO[i].setNaccumstorenum(null);
				itemVO[i].setNaccumwastnum(null);
				// 清空关联信息
				itemVO[i].setCcontractid(null);
				itemVO[i].setCcontractrcode(null);
				itemVO[i].setCcontractrowid(null);

				vTemp.addElement(getBillCardPanel().getBillModel(TAB1).getValueAt(i,
						"measrate"));
			}
			orderVO.setChildrenVO(itemVO);
			
			//gc 料比页
			OrderDdlbVO[] ddlbVO = (OrderDdlbVO[]) orderVO.getDdlbvos();
//			Vector vTemp_ddlb = new Vector();// 换算率
			for (int i = 0; i < ddlbVO.length; i++) {
				ddlbVO[i].setCorderid(null);
				ddlbVO[i].setCorder_bid(null);
				ddlbVO[i].setCorder_lb_id(null);
				ddlbVO[i].setCupsourcebillid(null);
				ddlbVO[i].setCupsourcebillrowid(null);
				ddlbVO[i].setCupsourcebilltype(null);
				ddlbVO[i].setCsourcebillid(null);
				ddlbVO[i].setCsourcebillrow(null);
				ddlbVO[i].setCordersource(null);
				// 清空累计回写信息
				ddlbVO[i].setNaccumwritenum(null);
				ddlbVO[i].setNaccumsendnum(null);
				ddlbVO[i].setNaccumwastnum(null);

//				vTemp_ddlb.addElement(getBillCardPanel().getBillModel(TAB2).getValueAt(i,
//						"measrate"));
			}
			orderVO.setDdlbvos(ddlbVO);
			//gc---end
			//
			getBillCardPanel().addNew();
			// 开户银行
			loadCustBank();

//			getBillCardPanel().setBillValueVO(orderVO);//gc-end
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(m_curOrderVO);
			exBillVO.setParentVO((OrderHeaderVO)orderVO.getParentVO());
			exBillVO.addTableVO(TAB1,"", orderVO.getChildrenVO());
			exBillVO.addTableVO(TAB2,"", orderVO.getDdlbvos());
			getBillCardPanel().setBillValueVO(exBillVO);
			//gc-end
			//
			ref.setText(headVmemo);

			// 执行公式
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().getBillModel(TAB2).execLoadFormula();

			// 处理扣税类别显示
			for (int i = 0; i < itemVO.length; i++) {
				Integer idiscounttaxtype = itemVO[i].getIdiscounttaxtype();

				getBillCardPanel().getBillModel(TAB1).setValueAt(idiscounttaxtype, i,
						"idiscounttaxtype");
				//
				// 辅计量单位
				String formula = "isassist->getColValue(bd_invbasdoc,assistunit,pk_invbasdoc,cbaseid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(i,
						new String[] { formula });
				// 自由项
				BillEdit.editFreeItem(getBillCardPanel(),TAB1, i, "cinventorycode",
						"cbaseid", "cmangid");

				// 换算率
				getBillCardPanel().setBodyValueAt(vTemp.elementAt(i), i,
						"measrate",TAB1);
			}
			for (int j = 0; ddlbVO != null && j < ddlbVO.length; j++) {
				// 自由项
				BillEdit.editFreeItem(getBillCardPanel(),TAB2, j, "cinventorycode",
						"cbaseid", "cmangid");
			}
			// 2009-9-21 renzhonghai 为了提供效率，把自由项提到外循环统一处理。
			loadFreeItems();
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}
		m_billState = ScConstants.STATE_ADD;
		initButtons();
		setButtonsState();
		setRelateCntAndDefaultPriceAllRow(false);
		cardLayout.first(this);
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH029")/* @res "已复制" */);
	}

	/**
	 * gc料比页 此处插入方法说明。 功能描述:复制订单 输入参数: 返回值: 异常处理:
	 */
	private void onCopyForLB() {// gc???????????
		if (getModel().size() == 0) {
			return;
		}

		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
				.getComponent();
		String headVmemo = ref.getUITextField().getText();
		try {
			//
			OrderVO orderVO = getModel().getCurrentOrder();
			orderVO = (OrderVO) ObjectUtils.serializableClone(orderVO);
			((OrderHeaderVO) orderVO.getParentVO())
					.setDorderdate(nc.ui.pub.ClientEnvironment.getInstance()
							.getDate());
			((OrderHeaderVO) orderVO.getParentVO()).setVordercode(null);
			((OrderHeaderVO) orderVO.getParentVO()).setCorderid(null);
			((OrderHeaderVO) orderVO.getParentVO())
					.setCoperator(getOperatorID());
			((OrderHeaderVO) orderVO.getParentVO())
					.setIbillstatus(BillStatus.FREE);
			((OrderHeaderVO) orderVO.getParentVO()).setVmemo(headVmemo);
			// czp/2003/09/12/add begin
			((OrderHeaderVO) orderVO.getParentVO()).setCauditpsn(null);
			((OrderHeaderVO) orderVO.getParentVO()).setDauditdate(null);
			// czp/2003/09/12/add begin

			OrderItemVO[] itemVO = (OrderItemVO[]) orderVO.getChildrenVO();
			Vector vTemp = new Vector();// 换算率
			for (int i = 0; i < itemVO.length; i++) {
				itemVO[i].setCorderid(null);
				itemVO[i].setCorder_bid(null);
				itemVO[i].setCupsourcebillid(null);
				itemVO[i].setCupsourcebillrowid(null);
				itemVO[i].setCupsourcebilltype(null);
				itemVO[i].setCsourcebillid(null);
				itemVO[i].setCsourcebillrow(null);
				itemVO[i].setCordersource(null);
				// 清空累计回写信息
				itemVO[i].setNaccumarrvnum(null);
				itemVO[i].setNaccuminvoicenum(null);
				itemVO[i].setNaccumstorenum(null);
				itemVO[i].setNaccumwastnum(null);
				// 清空关联信息
				itemVO[i].setCcontractid(null);
				itemVO[i].setCcontractrcode(null);
				itemVO[i].setCcontractrowid(null);

				vTemp.addElement(getBillCardPanel().getBodyValueAt(i,
						"measrate"));
			}
			orderVO.setChildrenVO(itemVO);
			//
			getBillCardPanel().addNew();
//			getBillCardPanel().setBillValueVO(orderVO);//gc-end
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(orderVO);
			exBillVO.setParentVO((OrderHeaderVO)orderVO.getParentVO());
			exBillVO.addTableVO(TAB1,"", orderVO.getChildrenVO());
			exBillVO.addTableVO(TAB2,"", orderVO.getDdlbvos());
			getBillCardPanel().setBillValueVO(exBillVO);
			//gc-end
			// 开户银行
			loadCustBank();

//			getBillCardPanel().setBillValueVO(orderVO);////gc-old
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO1 = new nc.vo.pub.ExAggregatedVO(orderVO);
			exBillVO1.setParentVO((OrderHeaderVO)orderVO.getParentVO());
			exBillVO1.addTableVO(TAB1,"", orderVO.getChildrenVO());
			exBillVO1.addTableVO(TAB2,"", orderVO.getDdlbvos());
			getBillCardPanel().setBillValueVO(exBillVO1);
			//gc-end
			
			//
			ref.setText(headVmemo);

			// 执行公式
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();

			// 处理扣税类别显示
			for (int i = 0; i < itemVO.length; i++) {
				Integer idiscounttaxtype = itemVO[i].getIdiscounttaxtype();

				getBillCardPanel().setBodyValueAt(idiscounttaxtype, i,
						"idiscounttaxtype");
				//
				// 辅计量单位
				String formula = "isassist->getColValue(bd_invbasdoc,assistunit,pk_invbasdoc,cbaseid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(i,
						new String[] { formula });
				// 自由项
				BillEdit.editFreeItem(getBillCardPanel(), i, "cinventorycode",
						"cbaseid", "cmangid");

				// 换算率
				getBillCardPanel().setBodyValueAt(vTemp.elementAt(i), i,
						"measrate");
			}
			// 2009-9-21 renzhonghai 为了提供效率，把自由项提到外循环统一处理。
			loadFreeItems();
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}
		m_billState = ScConstants.STATE_ADD;
		initButtons();
		setButtonsState();
		setRelateCntAndDefaultPriceAllRow(false);
		cardLayout.first(this);
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH029")/* @res "已复制" */);
	}

	/**
	 * 复制单据 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010178
	 */
	private void onCopyLine() {
		String tab = getSelectTab();// gc
		int row[] = getBillCardPanel().getBillTable(tab).getSelectedRows();
		if (row.length == 0)
			return;

		getBillCardPanel().copyLine();//gc不处理本身就是按选中行复制
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH039")/* @res "复制行成功" */);
	}

	/**
	 * 删行 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001022C
	 */
	private void onDelLine() {
		String tab = getSelectTab();// gc
		// 如果没有行则直接返回V55
		if (getBillCardPanel().getRowCount() == 0)
			return;

		// 如果只有一行则不允许删除V55
		if (getBillCardPanel().getRowCount() == getBillCardPanel()
				.getBillTable(tab).getSelectedRows().length) {
			showErrorMessage(NCLangRes.getInstance().getStrByID("scmcommon",
					"UPPSCMCommon-000489")
			/* 不能删除表体中的全部行！ */
			);
			return;
		}

		int allDelRows[] = getBillCardPanel().getBillTable(tab)
				.getSelectedRows();
		if (allDelRows.length == 0) {
			MessageDialog.showHintDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000013")/* "删行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
							"UPP401201-000067")/* "没有选中订单表体行！" */);
			return;
		}

		getBillCardPanel().getBillModel(tab).delLine(allDelRows);
		// int lint_rowcount = getBillCardPanel().getRowCount();//gc old
		int lint_rowcount = getBillCardPanel().getBillModel(tab).getRowCount();// gc

		if (lint_rowcount > 0) {
			getBillCardPanel().getBillTable(tab).setRowSelectionInterval(
					lint_rowcount - 1, lint_rowcount - 1);
			// getBillCardPanel().getBillModel(TAB1).setRowState(lint_rowcount-1,
			// BillModel.SELECTED);
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH037")/* @res "删行成功" */);

	}

	/**
	 * 作废单据 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101A0
	 */
	private void onDiscard() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
				"UPP401201-000068")/* "作废单据" */);

		if (m_billState == ScConstants.STATE_LIST) {
			int lint_CurBillIndex = getBillListPanel().getHeadTable()
					.getSelectedRow();
			getModel().setCurrentIndex(
					nc.ui.pu.pub.PuTool.getIndexBeforeSort(getBillListPanel(),
							lint_CurBillIndex));
			if (getModel().getCurrentIndex() == -1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000047")/* @res "请选中要作废的单据" */);
				return;
			}

			int lint_RowCount = getBillListPanel().getHeadTable()
					.getSelectedRowCount();
			if (lint_RowCount > 1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000045")/* @res "请选中一张单据" */);
				return;
			}
		}

		int ret = MessageDialog.showYesNoDlg(
				this,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000219")/* @res "确定" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000224")/* @res "确定要作废该记录吗" */,
				UIDialog.ID_NO);
		if (ret != MessageDialog.ID_YES) {
			return;
		}

		try {
			OrderVO vo = getSelectedVO();

			int i = ((OrderHeaderVO) vo.getParentVO()).getIbillstatus()
					.intValue();
			if (i == BillStatus.AUDITED || i == BillStatus.AUDITING
					|| i == BillStatus.AUDITFAIL) {
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000048")/*
													 * @res "该单据已经被审批，请刷新界面"
													 */);
				return;
			} else if (i == BillStatus.DELETED) {
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000042")/*
													 * @res "该单据已经被删除，请刷新界面"
													 */);
				return;
			}
			((OrderHeaderVO) vo.getParentVO()).setCuroperator(getOperatorID());
			PfUtilClient.processBatch("DISCARD", ScmConst.SC_Order,
					nc.ui.pub.ClientEnvironment.getInstance().getDate()
							.toString(), new OrderVO[] { vo });
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
			return;
		}

		// 列表界面清除该单据
		if (m_billState == ScConstants.STATE_LIST) {
			String curpk = getModel().getCurrentHeaderId();
			int listnum = getBillListPanel().getHeadTable().getRowCount();
			for (int i = 0; i < listnum; i++) {
				String billpk = getBillListPanel().getHeadBillModel()
						.getValueAt(i, "corderid").toString();
				if (billpk.equals(curpk)) {
					// 当前单据集合中清除该单据
					getModel().deleteCurrentHeader();
					getBillListPanel().getHeadBillModel().delLine(
							new int[] { i });
					getBillListPanel().getBillListData().clearCopyData();

					int curRow = (i == listnum - 1) ? i = listnum - 2 : i;
					if (curRow == -1) { // 界面中没有数据，所有数据都已经删除
						clearList(); // 清除列表界面数据
						boReturn.setEnabled(true);
						updateButtons();
						this.m_curOrderVO = null; // 清除当前单据
						break;
					}

					getBillListPanel().getHeadTable().setRowSelectionInterval(
							curRow, curRow);
					getBillListPanel().getHeadBillModel().setRowState(curRow,
							BillModel.SELECTED);

					String billstatus = getBillListPanel().getHeadBillModel()
							.getValueAt(i, "ibillstatus").toString();
					setOperateState(new Integer(billstatus).intValue());

					break;
				}
			}
			updateUI();
		} else if (m_billState == ScConstants.STATE_CARD) { // 卡片状态清除该单据
			// 当前单据集合中清除该单据
			getModel().deleteCurrentHeader();

			if (getModel().getCurrentIndex() < 0) {
				getBillCardPanel().getBillData().clearViewData();

				// 刷新按钮
				setButtonsState();
				m_curOrderVO = null; // 清除当前单据
				return;
			}
			onList();
			setPgUpDownButtonsState(getModel().getCurrentIndex());
		}

		// 刷新按钮
		setButtonsState();

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000068")/* @res "作废成功" */);
	}

	/**
	 * 获取被选择的VO
	 * 
	 * @return
	 * @author songhy
	 */
	private OrderVO getSelectedVO() {
		if (m_billState == ScConstants.STATE_CARD) {
			// 卡片状态下的当前VO
			m_curOrderVO = (OrderVO) getBillCardPanel().getBillValueVO(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());
		} else {
			// 列表状态下的当前VO，如果选择多条时，只选择第一条
			m_curOrderVO = (OrderVO) getBillListPanel().getSelectedVO(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());
			m_curOrderVO.setChildrenVO(getBillListPanel().getBodyBillModel()
					.getBodyValueVOs(OrderItemVO.class.getName()));
		}

		return m_curOrderVO;
	}

	/**
	 * 功能 ：文档管理 适用单据状态：“卡片浏览”、“列表浏览” 作者：李金巧 日期：2003-03-19
	 */
	private void onDocument() {
		String[] strPks = null;
		String[] strCodes = null;
		HashMap mapBtnPowerVo = new HashMap();
		Integer iBillStatus = null;

		try {
			// 卡片
			if (m_billState == ScConstants.STATE_CARD) {
				if (m_curOrderVO == null)
					return;
				strPks = new String[] { m_curOrderVO.getParentVO()
						.getPrimaryKey() };
				strCodes = new String[] { ((OrderHeaderVO) m_curOrderVO
						.getParentVO()).getVordercode() };

				// 处理文档管理框删除按钮是否可用
				BtnPowerVO pVo = new BtnPowerVO(strCodes[0]);
				iBillStatus = ((OrderHeaderVO) m_curOrderVO.getParentVO())
						.getIbillstatus();
				if (iBillStatus.intValue() == BillStatus.DELETED
						|| iBillStatus.intValue() == BillStatus.AUDITING
						|| iBillStatus.intValue() == BillStatus.AUDITED) {
					pVo.setFileDelEnable("false");
				}
				mapBtnPowerVo.put(strCodes[0], pVo);

				if (strPks == null || strPks.length <= 0)
					return;
				// 调用文档管理对话框
				nc.ui.scm.file.DocumentManager.showDM(this, ScmConst.SC_Order,
						strPks, mapBtnPowerVo);

				return;

			}

			// 列表
			if (m_billState == ScConstants.STATE_LIST) {
				int rowCount = getBillListPanel().getHeadTable().getRowCount();
				Vector tempV = new Vector();
				for (int i = 0; i < rowCount; i++) {
					if (getBillListPanel().getHeadBillModel().getRowState(i) == BillModel.SELECTED)
						tempV.addElement(new Integer(i));
				}
				if (tempV.size() > 0) {
					// int[] rows =
					// getBillListPanel().getHeadTable().getSelectedRows();
					strPks = new String[tempV.size()];
					strCodes = new String[tempV.size()];
					for (int i = 0; i < tempV.size(); i++) {
						int index = ((Integer) tempV.get(i)).intValue();
						strPks[i] = (String) getBillListPanel()
								.getHeadBillModel().getValueAt(index,
										"corderid");
						strCodes[i] = (String) getBillListPanel()
								.getHeadBillModel().getValueAt(index,
										"vordercode");
					}
				}
			}

			if (strPks == null || strPks.length <= 0)
				return;
			// 调用文档管理对话框
			nc.ui.scm.file.DocumentManager.showDM(this, ScmConst.SC_Order,
					strPks);
		} catch (Exception e) {
			SCMEnv.out(e);
			if (e instanceof BusinessException)
				MessageDialog.showErrorDlg(
						this,
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "错误"
																	 */,
						e.getMessage());
			else
				MessageDialog.showErrorDlg(
						this,
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "错误"
																	 */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
								"UPP401201-000007")/*
													 * @res "文档管理出错"
													 */);
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000025")/* @res "文档管理成功" */);
	}

	/**
	 * 普通列表状态双击列表行 创建日期：(2001-4-25 10:31:02)
	 */
	public void onDoubleClick() {

		m_billState = ScConstants.STATE_CARD;
		cardLayout.first(this);
		initButtons();
		int lint_CurBillIndex = getBillListPanel().getHeadTable()
				.getSelectedRow();
		getModel().setCurrentIndex(
				nc.ui.pu.pub.PuTool.getIndexBeforeSort(getBillListPanel(),
						lint_CurBillIndex));
		setButtonsState();
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		loadData(null);
		updateUI();
	}

	/**
	 * 首张 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010150
	 */
	private void onFirstPage() {
		getModel().setCurrentIndex(0);

		if (getModel().size() == 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000009")/* @res "没有满足条件的数据" */);
			getBillCardPanel().getBillData().clearViewData();
			return;
		}
		loadData(null);
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000026")/* @res "成功显示首页" */);
	}

	/**
	 * 刷新列表 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001018C
	 */
	private void onFresh() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000360")/* "开始查询..." */);

		try {

			OrderHeaderVO[] orderHeaderVO = OrderHelper.queryAllHead("",
					m_queryCon,
					new ClientLink(nc.ui.pub.ClientEnvironment.getInstance()),
					getQryCondition().getIswaitaudit());

			// modify by hanbin 2009-10-16
			getModel().clear();
			getModel().addAll(orderHeaderVO);

			getBillListPanel().setHeaderValueVO(orderHeaderVO);
			getBillListPanel().getHeadBillModel().execLoadFormula();

			getBillListPanel().hideTableCol();
			if (getModel().size() > 0) {
				getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);
				getBillListPanel().getHeadBillModel().setRowState(0,
						BillModel.SELECTED);

				if (m_billState != ScConstants.STATE_LIST) {
					onFirstPage();
				}

				String[] value = new String[] { String.valueOf(getModel()
						.size()) };
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000070", null, value)/*
																 * "查询完毕，返回 "
																 * +iCnt + "张单据"
																 */);
			} else if (m_billState == ScConstants.STATE_LIST) {
				clearList();
				boReturn.setEnabled(true);
				updateButtons();
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000361")/* "查询完毕，没有查到数据" */);
			}

		} catch (Exception e) {
			SCMEnv.out("列表表头数据加载失败");
			SCMEnv.out(e);
		}
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH007")/* @res "刷新成功" */);
	}

	/**
	 * 
	 * 方法功能：判断某个字段是否属于自定义项
	 * <p>
	 * 
	 * @param itemKey
	 *            字段key
	 * @param prefix
	 *            前缀字符串
	 * @return 如果是，返回true，否则，返回false
	 *         <p>
	 * @author duy
	 * @time 2009-7-9 下午02:45:14
	 */
	private boolean isUserDefinedField(String itemKey, String prefix) {
		if (!itemKey.startsWith(prefix))
			return false;
		String no = itemKey.substring(prefix.length());
		char[] chs = no.toCharArray();
		boolean flag = true;
		for (char ch : chs) {
			if (!Character.isDigit(ch)) {
				flag = false;
				break;
			}
		}
		return flag;
	}

	/**
	 * 功能描述:自定义项保存PK(表体)
	 */
	private void setBodyDefPK(BillEditEvent event) {
		String tab = getSelectTab();
		if(tab.equals(TAB1)){
			String itemKey = event.getKey();
			int row = event.getRow();
			if (isUserDefinedField(itemKey, "vdef")) {
				DefSetTool.afterEditBody(getBillCardPanel().getBillModel(TAB1),
						row, itemKey, "pk_defdoc" + itemKey.substring(4));
			}
		}
	}

	/**
	 * 功能描述:自定义项保存PK(表头)
	 */
	private void setHeadDefPK(BillEditEvent event) {
		String itemKey = event.getKey();
		if (isUserDefinedField(itemKey, "vdef")) {
			DefSetTool.afterEditHead(getBillCardPanel().getBillData(), itemKey,
					"pk_defdoc" + itemKey.substring(4));
		}
	}

	/**
	 * 复制单据 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010178
	 */
	private void onInsertLine() {
		getBillCardPanel().insertLine();
		// 行号处理
		nc.ui.scm.pub.report.BillRowNo.insertLineRowNo(getBillCardPanel(),
				ScmConst.SC_Order, "crowno");
		String tab = getSelectTab();// gc
		int row = getBillCardPanel().getBillTable(tab).getSelectedRow();
		if (row < 0) {
			MessageDialog.showHintDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000016")/* "插入行" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
							"UPP401201-000066")/* @res "请选择行" */);
			return;
		}
		// gc
		if (tab.equals(TAB1)) {
			// 表体默认项（扣率、扣税类别）
			getBillCardPanel().setBodyValueAt(new Integer(1), row,
					"idiscounttaxtype");
			getBillCardPanel().setBodyValueAt("100", row, "ndiscountrate");
			getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "bisactive");

			// 带出上行默认项（币种、汇率、税率）
			if (row > 0) {
				Object ccurrtype = getBillCardPanel().getBodyValueAt(row - 1,
						"ccurrencytypeid");
				Object localRate = getBillCardPanel().getBodyValueAt(row - 1,
						"nexchangeotobrate");
				Object taxrate = getBillCardPanel().getBodyValueAt(row - 1,
						"ntaxrate");

				getBillCardPanel().setBodyValueAt(ccurrtype, row,
						"ccurrencytypeid");
				getBillCardPanel().setBodyValueAt(localRate, row,
						"nexchangeotobrate");
				getBillCardPanel().setBodyValueAt(taxrate, row, "ntaxrate");

			}

			// 默认到货地址
			try {
				String sReceiver = (String) getBillCardPanel().getHeadItem(
						"creciever").getValueObject();

				if (!StringUtil.isEmptyWithTrim(sReceiver)) {
					// 基础ID
					String cvendorbaseid = null;
					String defaddr = null;

					cvendorbaseid = PublicHelper.getCvendorbaseid(sReceiver
							.toString());
					defaddr = PublicHelper.getVdefaddr(cvendorbaseid);
					getBillCardPanel().setBodyValueAt(defaddr, row,
							"creceiveaddress");
				}
			} catch (Exception e) {
				SCMEnv.out(e);
			}
		}
		// 填充第 row 行的币种ID
		fillCurrencyTypeIdInCardBody(row);

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH038")/* @res "插入行成功" */);
	}

	/**
	 * 复制单据 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010178
	 */
	private void onInsertLines(int iBeginRow, int iEndRow, int iInsertCount) {
		String tab = getSelectTab();// gc
		if (getBillCardPanel().getBillTable(tab).getSelectedRowCount() <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000049")/* @res "插行前请先选择表体行" */);
			return;
		}

		// 插入前先取得到货地址和默认币种
		// 默认到货地址
		String defaddr = null;
		String defcurr = null;
		try {
			String sReceiver = (String) getBillCardPanel().getHeadItem(
					"creciever").getValueObject();
			if (StringUtil.isEmpty(sReceiver)) {
				// 基础ID
				String cvendorbaseid = null;
				cvendorbaseid = PublicHelper.getCvendorbaseid(sReceiver
						.toString());
				defaddr = PublicHelper.getVdefaddr(cvendorbaseid);
			}
		} catch (Exception e) {
			SCMEnv.out(e);
		}
		if(tab.equals(TAB1)){
			int iCurRow = 0;
			for (int i = 0; i < iInsertCount; i++) {
				iCurRow = iBeginRow + i;
				getBillCardPanel().getBillModel(TAB1).insertRow(iCurRow + 1);
				// 设置新插行的默认值
				getBillCardPanel().setBodyValueAt(new Integer(1), iCurRow + 1,
						"idiscounttaxtype");
				getBillCardPanel().setBodyValueAt("100", iCurRow + 1,
						"ndiscountrate");
				getBillCardPanel().setBodyValueAt(defaddr, iCurRow + 1,
						"creceiveaddress");
				getBillCardPanel().setBodyValueAt(defcurr, iCurRow + 1,
						"ccurrencytypeid");
				getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, iCurRow + 1,
						"bisactive");
	
				fillCurrencyTypeIdInCardBody(iCurRow + 1);
			}
		}
		int iFinalEndRow = iBeginRow + iInsertCount + 1;
		// 设置行号
		nc.ui.scm.pub.report.BillRowNo.insertLineRowNos(getBillCardPanel(),
				ScmConst.SC_Order, "crowno", iFinalEndRow, iInsertCount);
	}

	/**
	 * 末张 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001015A
	 */
	private void onLastPage() {
		getModel().setCurrentIndex(getModel().size() - 1);

		loadData(null);
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000029")/* @res "成功显示末页" */);

	}

	private void onList() {
		m_billState = ScConstants.STATE_LIST;
		cardLayout.last(this);
		initButtons();
		getBillListPanel().setState(m_billState);

		// add by yye
		OrderHeaderVO[] orderHeaderVO = getModel().getHeaderArray();
		getBillListPanel().setHeaderValueVO(orderHeaderVO);
		getBillListPanel().getHeadBillModel().execLoadFormula();
		// add by yye

		// 定位
		int rowcount = getBillListPanel().getHeadBillModel().getRowCount();
		if (rowcount == 0) {
			clearList();
			boReturn.setEnabled(true);
			setButtonsState();
			updateButtons();
			return;
		}
		onSelectNo();
		String curpk = getModel().getCurrentHeaderId();
		for (int i = 0; i < rowcount; i++) {
			Object corderid = getBillListPanel().getHeadBillModel().getValueAt(
					i, "corderid");
			if (corderid != null && corderid.toString().equals(curpk)) {
				getBillListPanel().getHeadTable().setRowSelectionInterval(i, i);
				String billstatus = getBillListPanel().getHeadBillModel()
						.getValueAt(i, "ibillstatus").toString();
				setOperateState(new Integer(billstatus).intValue());

				break;
			}
		}

		setButtonsState();
		updateUI();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH022")/* @res "列表显示" */);
	}

	/**
	 * 定位 * 只列表下才能定位
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010114
	 */
	private void onLocate() {
		// 实例定位对话框
		nc.ui.scm.pub.report.OrientDialog dlgOrient = new nc.ui.scm.pub.report.OrientDialog(
				this, getBillListPanel().getBodyBillModel(), getBillListPanel()
						.getBillListData().getBodyItems(), getBillListPanel()
						.getBodyTable());
		//
		dlgOrient.showModal();
	}

	/**
	 * 作者： 功能：处理表体浮动菜单 参数：无 返回：无 例外：无 日期：(2001-4-18 13:24:16)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public void onMenuItemClick(java.awt.event.ActionEvent event) {
		setMaxMnyDigit(iMaxMnyDigit);
		getBillCardPanel().removeEditListener();
		// 得到浮动菜单
		UIMenuItem menuItem = (UIMenuItem) event.getSource();

		if (menuItem.equals(getBillCardPanel().getAddLineMenuItem())) {
			onAppendLine();
		} else if (menuItem.equals(getBillCardPanel().getCopyLineMenuItem())) {
			onCopyLine();
		} else if (menuItem.equals(getBillCardPanel().getDelLineMenuItem())) {
			onDelLine();
		} else if (menuItem.equals(getBillCardPanel().getInsertLineMenuItem())) {
			onInsertLine();

			// add by hanbin 2009-11-16 原因:右键选择插入，币种字段显示pk，不显示名称
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().updateUI();
		} else if (menuItem.equals(getBillCardPanel().getPasteLineMenuItem())) {
			onPasteLine();
		} else if (menuItem.equals(getBillCardPanel()
				.getPasteLineToTailMenuItem())) {
			onPasteLineToTail();
		}
		getBillCardPanel().addEditListener(this);
		setPrecision();
		String tab = getSelectTab();//gc
		if(tab.equals(TAB1)){
			getPrecisionUtil().bodyRowChange(
					new BillEditEvent(event.getSource(), 0, getBillCardPanel()
							.getBillTable().getSelectedRow()),
					new String[] { "nexchangeotobrate", "noriginalcurmny",
							"noriginaltaxmny", "noriginalsummny", "nmoney",
							"ntaxmny", "nsummny" });
		}
	}

	/**
	 * 修改单据 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001018C
	 */
	private void onModify() {
		try {
			// 单据转入修改
			if (m_billState == ScConstants.STATE_OTHER) {
				int rowindex = getBillListPanel().getHeadTable()
						.getSelectedRow();
				if (rowindex == -1) {
					showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("401201", "UPP401201-000044")/*
																	 * @res
																	 * "请选中要修改的单据"
																	 */);
					return;
				}

				refLoadData(rowindex);

				String []tabs = new String[]{TAB1,TAB2};
				for (int j = 0; j < tabs.length; j++) {
					// 获取存货管理档案ID
					String[] invMangIdArray = getInvMangIdArrayFromView(tabs[j]);
					// 批量加载批次号，提高效率
					InvTool.loadBatchProdNumMngt(invMangIdArray);
					for (int i = 0; i < getBillCardPanel().getBillModel(tabs[j]).getRowCount(); i++) {
						// 批次号的可编辑性
						String oMangId = (String) getCardBodyValueAt(i, "cmangid",null);
						if (StringUtil.isEmptyWithTrim(oMangId)) {
							getBillCardPanel().setCellEditable(
									i, "vproducenum", false,tabs[j]);
						} else {
							getBillCardPanel().setCellEditable(
									i, "vproducenum",
									InvTool.isBatchManaged(oMangId),tabs[j]);
						}
					}
				}

				// since v51, 设置业务员默认值 根据操作员带出业务员
				setDefaultValueByUser();

				/*
				 * 转单时的自动取价和关联合同处理.
				 * 注意:不同于采购订单,委外订单转单时的自动取价和关联合同处理.放在了修改时,认为这样更合理一些.
				 * 采购订单的相关处理放在了修改前的列表状态下完成
				 * ,可参考其代码:nc.ui.po.pub.PoChangeUI.setVOCntRelated()
				 */
				setRelateCntAndDefaultPriceAllRow(false);

				// 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
				setNotEditableWhenRelateCntAllRow();
				setCntRelatedItemEditableInCardHead();
				setPrecision();

				return;
			}
			// 普通单据修改
			else if (m_billState == ScConstants.STATE_LIST) {
				// m_iCurBillIndex =
				// getBillListPanel().getHeadTable().getSelectedRow();
				int lint_CurBillIndex = getBillListPanel().getHeadTable()
						.getSelectedRow();
				getModel().setCurrentIndex(
						nc.ui.pu.pub.PuTool.getIndexBeforeSort(
								getBillListPanel(), lint_CurBillIndex));
				if (getModel().getCurrentIndex() == -1) {
					showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("401201", "UPP401201-000044")/*
																	 * @res
																	 * "请选中要修改的单据"
																	 */);
					return;
				}

				cardLayout.first(this);
				setButtons(m_btnTree.getButtonArray());// 设置卡片组按钮

				loadData(null);
				String []tabs = new String[]{TAB1,TAB2};
				for (int j = 0; j < tabs.length; j++) {
					for (int i = 0; i < getBillCardPanel().getBillModel(tabs[j]).getRowCount(); i++) {
						// 批次号的可编辑性
						String sMangId = getBillCardPanel().getBillModel(tabs[j]).getValueAt(i,
								"cmangid").toString();
						if (sMangId == null || sMangId.trim().length() == 0) {
							getBillCardPanel().getBillModel(tabs[j]).setCellEditable(
									i, "vproducenum", false);
						} else {
							getBillCardPanel().getBillModel(tabs[j]).setCellEditable(
									i, "vproducenum",
									InvTool.isBatchManaged(sMangId));
						}
						// 批次号清空
						getBillCardPanel().getBillModel(tabs[j]).setValueAt(null, i, "vproducenum");
					}
				}

			}

			// 浮动菜单右键功能权限控制
			rightButtonRightControl();
			updateButtons();
		} catch (Exception e) {
			SCMEnv.out(e);
		}
		setAuditInfo();

		m_billState = ScConstants.STATE_MODIFY;
		initButtons();
		setButtonsState();

		String tab = getSelectTab();// gc
		int rowindex = getBillCardPanel().getBillTable(tab).getSelectedRow();
		getBillCardPanel().getBillTable(tab).clearSelection();
		if (rowindex > -1) {
			int colindex = getBillCardPanel().getBillTable(tab)
					.getSelectedColumn();
			if (colindex > -1) {
				getBillCardPanel().getBillTable(tab)
						.setColumnSelectionInterval(colindex, colindex);
				getBillCardPanel().getBillTable(tab).setRowSelectionInterval(
						rowindex, rowindex);
			}
		}

		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			// 批次号的可编辑性
			String sMangId = getBillCardPanel().getBodyValueAt(i, "cmangid")
					.toString();
			if (sMangId == null || sMangId.trim().length() == 0) {
				getBillCardPanel().getBillModel(TAB1).setCellEditable(i,
						"vproducenum", false);
			} else {
				getBillCardPanel().getBillModel(TAB1).setCellEditable(i,
						"vproducenum", InvTool.isBatchManaged(sMangId));
			}
		}

		// 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
		setNotEditableWhenRelateCntAllRow();
		setCntRelatedItemEditableInCardHead();

		getBillCardPanel().updateValue();
		cardLayout.first(this);
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000030")/* @res "正在修改" */);

	}

	/**
	 * 下一张 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001013C
	 */
	private void onNextPage() {
		getModel().next();

		loadData(null);
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000028")/* @res "成功显示下一页" */);
	}

	/**
	 * 功能：处理粘贴行
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001022C
	 */
	private void onPasteLine() {
		String tab = getSelectTab();// gc
		int row = getBillCardPanel().getBillTable(tab).getSelectedRow();
		// 如果未选中行,则粘贴到行尾
		// V55 赵玉行 2008.8.28
		if (row < 0) {
			onPasteLineToTail();
			return;
		}
		// 行号处理
		int oldRow = getBillCardPanel().getBillTable(tab).getRowCount();

		getBillCardPanel().pasteLine();

		int newRow = getBillCardPanel().getBillTable(tab).getRowCount();
		if (newRow >= oldRow) {
			nc.ui.scm.pub.report.BillRowNo.pasteLineRowNo(getBillCardPanel(),
					ScmConst.SC_Order, "crowno", newRow - oldRow);
		}

		// 将粘贴行的corder_bid和来源信息置空
		row = getBillCardPanel().getBillTable(tab).getSelectedRow();
		int iBeginSetRow, iEndSetRow;
		iBeginSetRow = row - (newRow - oldRow);
		iEndSetRow = row - 1;
		for (int i = iBeginSetRow; i <= iEndSetRow; i++) {
			// getBillCardPanel().setBodyValueAt("", i,
			// BillBodyItemKeys.CORDER_BID);//gc -old
			getBillCardPanel().getBillModel(tab).setValueAt("", i,
					BillBodyItemKeys.CORDER_BID);// gc
			clearSourceInfoInCardBody(i);
			setNotEditableWhenRelateCnt(new int[] { i });
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH040")/* @res "粘贴行成功" */);
	}

	/**
	 * 清除卡片界面表体第 rowIndex 行的来源及源头信息
	 * 
	 * @param rowIndex
	 *            行标
	 */
	private void clearSourceInfoInCardBody(int rowIndex) {// gc
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CORDERSOURCE); // 来源单据类型
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLID); // 来源单据ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLROW); // 来源单据行ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CUPSOURCEBILLTYPE); // 上层单据类型
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CUPSOURCEBILLID); // 上层来源单据ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CUPSOURCEBILLROWID); // 上层来源单据行ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLNAME); // 来源单据名称
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLCODE); // 来源单据号
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLROWNO); // 来源单据行号
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CANCESTORBILLNAME); // 源头单据名称
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CANCESTORBILLCODE); // 源头单据号
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CANCESTORBILLROWNO); // 源头单据行号
	}

	/**
	 * 功能：处理粘贴行到表尾
	 * 
	 */
	public void onPasteLineToTail() {
		// 粘贴前的行数
		int iOrgRowCount = getBillCardPanel().getRowCount();
		getBillCardPanel().pasteLineToTail();
		// 增加的行数
		int iPastedRowCount = getBillCardPanel().getRowCount() - iOrgRowCount;
		// 设置行号
		nc.ui.scm.pub.report.BillRowNo.addLineRowNos(getBillCardPanel(),
				ScmConst.SC_Order, "crowno", iPastedRowCount);

		for (int i = iOrgRowCount; i <= iOrgRowCount + iPastedRowCount - 1; i++) {
			getBillCardPanel().setBodyValueAt("", i,
					BillBodyItemKeys.CORDER_BID);
			if(getSelectTab().equals(TAB2)){
				getBillCardPanel().setBodyValueAt("", i,"corder_lb_id");//gc
			}
			clearSourceInfoInCardBody(i);
			setNotEditableWhenRelateCnt(new int[] { i });
		}
	}

	/**
	 * 上一张 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010132
	 */
	private void onPrePage() {
		getModel().previous();
		loadData(null);
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000027")/* @res "成功显示上一页" */);
	}

	/**
	 * 方法功能描述：打印单据
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-3-19 下午03:40:12
	 */
	private void onPrint() {

		if (m_billState == ScConstants.STATE_CARD) {
			OrderVO orderVO4Print = getSelectedVO();
			ArrayList<OrderVO> aryRslt = new ArrayList<OrderVO>();
			aryRslt.add(orderVO4Print);

			nc.ui.scm.pub.print.ScmPrintTool printCard = null;
			printCard = new nc.ui.scm.pub.print.ScmPrintTool(this,
					getBillCardPanel(), aryRslt, getModuleCode());
			try {
				printCard.setData(aryRslt);
			} catch (Exception e) {
				SCMEnv.out(e);
			}

			try {
				// printCard.onCardPrintPreview(getBillCardPanel(),
				// getBillListPanel(), "61");
				printCard.onCardPrint(getBillCardPanel(), getBillListPanel(),
						ScmConst.SC_Order);
				if (null != printCard.getPrintMessage()
						&& printCard.getPrintMessage().trim().length() > 0) {
					MessageDialog.showHintDlg(
							this,
							nc.ui.ml.NCLangRes.getInstance().getStrByID(
									"SCMCOMMON", "UPPSCMCommon-000270")/*
																		 * @res
																		 * "提示"
																		 */,
							printCard.getPrintMessage());
				}
			} catch (BusinessException e) {
				SCMEnv.out(e);
			}
		} else if (m_billState == ScConstants.STATE_LIST
				|| m_billState == ScConstants.STATE_OTHER) {
			if (printList == null) {
				printList = new nc.ui.scm.pub.print.ScmPrintTool(this,
						getBillCardPanel(), getSelectedBills(), getModuleCode());
			} else
				try {
					printList.setData(getSelectedBills());
				} catch (Exception e) {

				}
			try {
				printList.onBatchPrint(getBillListPanel(), getBillCardPanel(),
						ScmConst.SC_Order);
				if (null != printList.getPrintMessage()
						&& printList.getPrintMessage().trim().length() > 0) {
					// MessageDialog.showHintDlg(this,
					// nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
					// "UPPSCMCommon-000270")/* @res "提示" */,
					// printList.getPrintMessage());
					showHintMessage(printList.getPrintMessage());
				}
			} catch (BusinessException e) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000061")/* "打印出错" */);
				SCMEnv.out(e);
			}
		}
	}

	/**
	 * 方法功能描述：打印预览
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-3-19 下午03:39:51
	 */
	private void onPrintPreview() {
		// 2009-9-22 renzhonghai 改进效率和第一次打开要得到选中的单据数据
		try {
			if (printList == null) {
				printList = new nc.ui.scm.pub.print.ScmPrintTool(this,
						getBillCardPanel(), getSelectedBills(), getModuleCode());
			} else
				printList.setData(getSelectedBills());
			printList.onBatchPrintPreview(getBillListPanel(),
					getBillCardPanel(), ScmConst.SC_Order);
		} catch (BusinessException e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * 查询 *
	 * 
	 * @return AggregatedValueObject[]
	 * @exception
	 * @roseuid 3ADE500100B0
	 */
	private void onQuery() {
		// 单据转入
		if (m_billState == ScConstants.STATE_OTHER) {
			return;
		}
		// 普通查询
		try {
			getQryCondition().showModal();
			if (!getQryCondition().isCloseOK())
				return;

			// 设置数据权限:放在查询对话框的showModal()之后。因为此时的数据权限要取决于多公司页签所选择的公司. lixiaodong
			// , v51
			m_query.setRefsDataPowerConVOs(nc.ui.pub.ClientEnvironment
					.getInstance().getUser().getPrimaryKey(),
					new String[] { nc.ui.pub.ClientEnvironment.getInstance()
							.getCorporation().getPrimaryKey() }, new String[] {
							"部门档案", "采购组织", "供应商档案", "供应商档案", "客户档案", "库存组织",
							"人员档案", "存货档案", "存货分类" }, new String[] {
							"bd_deptdoc.deptcode", "sc_order.cpurorganization",
							"bd_cubasdoc.custcode",
							"sc_order.cgiveinvoicevendor",
							"sc_order.creciever", "sc_order.cwareid",
							"bd_psndoc.psncode", "bd_invbasdoc.invcode",
							"bd_invcl.invclasscode" }, new int[] { 0, 2, 0, 2,
							2, 2, 0, 0, 0 });

			String whereSQL = getQryCondition().getWhereSQL();
			if (whereSQL == null || whereSQL.trim().equals(""))
				whereSQL = " sc_order.pk_corp='" + getPk_corp()
						+ "' and sc_order.dr=0 ";
			else
				whereSQL = " sc_order.pk_corp='" + getPk_corp()
						+ "' and sc_order.dr=0 and " + whereSQL;

			String lStr_temp = m_query.getUICheckBoxSQL();

			if (lStr_temp != null && lStr_temp.trim().length() > 0) {
				whereSQL = whereSQL + " and ( " + lStr_temp + " ) ";
			}

			// 查询条件vo
			ConditionVO[] condvo = m_query.getConditionVO();
			ArrayList listRet = ScTool.dealCondVosForPower(condvo);

			String strDataPowerSql = (String) listRet.get(1);

			if (strDataPowerSql != null && strDataPowerSql.trim().length() > 0) {
				if (whereSQL != null && whereSQL.trim().length() > 0) {
					whereSQL += " and " + strDataPowerSql + " ";
				} else {
					whereSQL = strDataPowerSql + " ";
				}
			}

			m_queryCon = whereSQL;

			OrderHeaderVO[] orderHeaderVO = OrderHelper.queryAllHead("",
					m_queryCon,
					new ClientLink(nc.ui.pub.ClientEnvironment.getInstance()),
					getQryCondition().getIswaitaudit());

			getModel().clear();
			getModel().addAll(orderHeaderVO);

			if (getModel().size() == 0) {
				m_bQueried = false;
				getBillCardPanel().getBillData().clearViewData();
				getBillListPanel().getHeadBillModel().clearBodyData();
				clearList();
				// 设置按钮状态
				setButtonsState();
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401200", "UPP401200-000012")/* @res "没有符合条件的数据" */);
				return;
			}

			// add by hanbin 2009-10-22 原因：控制刷新按钮的状态
			// 把“已经查询过”设为true
			m_bQueried = true;

			if (m_billState == ScConstants.STATE_CARD) {
				onList();
			} else {
				getBillListPanel().setHeaderValueVO(orderHeaderVO);
				getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);

				// 删除销售订单推成的委外订单表体行，因精度导致oldnum和nordernum不一致，故回写数量错误，导致销售订单弃审失败
				getBillCardPanel()
						.getBillModel(TAB1)
						.getItemByKey("oldnum")
						.setDecimalDigits(
								getBillCardPanel().getBillModel(TAB1)
										.getItemByKey("nordernum")
										.getDecimalDigits());
				setButtonsState();
			}

			getQryCondition().destroy();

		} catch (Exception e) {
			m_bQueried = false;
			// 设置按钮状态
			setButtonsState();
			SCMEnv.out("数据加载失败");
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000038")/* @res "查询失败" */);
			SCMEnv.out(e);
		}

		m_bQueried = true;
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH009")/* @res "查询完成" */);
	}

	/**
	 * 对当前单据进行合并显示，并可打印 修改日期，修改人，修改原因，注释标志：
	 */
	private void onBillCombin() {
		CollectSettingDlg dlg = new CollectSettingDlg(this, nc.ui.ml.NCLangRes
				.getInstance().getStrByID("401201", "UPT401201-000033")/*
																		 * @res
																		 * "合并显示"
																		 */,
				ScmConst.SC_Order, "401201", getPk_corp(), getOperatorID(),
				OrderVO.class.getName(), OrderHeaderVO.class.getName(),
				OrderItemVO.class.getName());

		// 把表头的“备注”字段修改为字符串类型，否则合并显示时会出现“备注”字段为空 add by hanbin 2009-11-16
		JComponent tempCom = null;
		if (getBillCardPanel().getHeadItem("vmemo").getComponent() instanceof UIRefPane) {
			tempCom = getBillCardPanel().getHeadItem("vmemo").getComponent();
			getBillCardPanel().getHeadItem("vmemo")
					.setDataType(BillItem.STRING);
		}

		if (m_billState == ScConstants.STATE_LIST) {
//			getBillCardPanel().setBillValueVO(this.getSelectedVO());//gc-end
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(m_curOrderVO);
			exBillVO.setParentVO((OrderHeaderVO)getSelectedVO().getParentVO());
			exBillVO.addTableVO(TAB1,"", getSelectedVO().getChildrenVO());
			exBillVO.addTableVO(TAB2,"", getSelectedVO().getDdlbvos());
			getBillCardPanel().setBillValueVO(exBillVO);
			//gc-end
		}

		setPrecision();
		dlg.initData(getBillCardPanel(), new String[] { "cinventorycode",
				"cinventoryname", "invspec", "invtype", "ccurrencytype" }, // 固定分组列
				null, // 缺省分组列
				new String[] { "noriginalcurmny", "noriginaltaxmny",
						"nordernum", "noriginalsummny" }, // 求和列
				null, // 求平均列
				new String[] { "noriginalcurprice", "noriginalnetprice" }, // 求加权平均列
				"nordernum" // 数量列
		);
		dlg.showModal();

		// 合并显示完后，把表头的“备注”字段恢复为参照类型 add by hanbin 2009-11-16
		if (tempCom != null) {
			getBillCardPanel().getHeadItem("vmemo").setDataType(BillItem.UFREF);
			getBillCardPanel().getHeadItem("vmemo").setComponent(tempCom);
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000039")/* @res "合并显示完成" */);
	}

	/**
	 * 查询当前单据审批状态
	 */

	private void onQueryForAudit() {
		if (getModel().size() <= 0 || getModel().getCurrentIndex() == -1) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000010")/* @res "请先选择要查询状态的单据" */);
			return;
		}

		String corderid = getModel().getCurrentHeaderId();
		nc.ui.pub.workflownote.FlowStateDlg approvestatedlg = new nc.ui.pub.workflownote.FlowStateDlg(
				this, ScmConst.SC_Order, corderid);
		approvestatedlg.showModal();
	}

	/**
	 * 保存 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010240 修改人：李金巧 修改内容：增加单据模板设置非空项检查 修改日期：2003-04-14
	 */
	private boolean onSave() {
		
		//gc 设置第一页为选中页，要不然改的类太多
		BillTabbedPane tp = getBillCardPanel().getBodyTabbedPane();
		tp.setSelectedIndex(0);//.setTabVisible(0, true);
		// 增加对校验公式的支持,错误显示由UAP处理 since v501
		if (!getBillCardPanel().getBillData().execValidateFormulas()) {
			return false;
		}

		int rowCount = getBillCardPanel().getBillModel(TAB1).getRowCount();
		// 删除材料编码为空的行
		if (m_billState != ScConstants.STATE_OTHER) {// 有可能是合同大类转入，此时不能删除材料编码为空的行
			for (int i = rowCount - 1; i >= 0; i--) {
				Object tempObj = getBillCardPanel().getBillModel(TAB1)
						.getValueAt(i, "cinventorycode");
				if (tempObj == null || tempObj.toString().trim().equals("")) {
					getBillCardPanel().getBillModel(TAB1).delLine(
							new int[] { i });
				}
			}

			// 表体为空时，不能保存
			if (getBillCardPanel().getBillModel(TAB1).getRowCount() <= 0) {
				this.showErrorMessage(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("40120003",
								"UPP40120003-000004")/* @res "表体不能为空，请输入" */);
				return false;
			}
		}
		// gc-材料页为空不可保存
		int row_cly = getBillCardPanel().getBillModel(TAB2).getRowCount();
		for (int i = row_cly - 1; i >= 0; i--) {
			Object tempObj = getBillCardPanel().getBillModel("lby").getValueAt(
					i, "cinventorycode");
			if (tempObj == null || tempObj.toString().trim().equals("")) {
				getBillCardPanel().getBillModel("lby").delLine(new int[] { i });
			}
		}
		if(!checkDDLB()){
			this.showErrorMessage("材料页【材料编码】+【自由项】不唯一，请修改");
			return false;
		}
		// 表体为空时，不能保存
		if (getBillCardPanel().getBillModel(TAB2).getRowCount() <= 0) {
			this.showErrorMessage("材料页不能为空，请输入");
			return false;
		}

		// gc-end//
		//gc 把第一个页的第一行存货，放到料比页
		row_cly = getBillCardPanel().getBillModel(TAB2).getRowCount();
		Object cmangid = getBillCardPanel().getBillModel(TAB1)
				.getValueAt(0, "cmangid");
		Object cbaseid = getBillCardPanel().getBillModel(TAB1)
				.getValueAt(0, "cbaseid");
		for (int i = 0; i < row_cly; i++) {
			getBillCardPanel().getBillModel("lby").setValueAt(cmangid, i, "cprocessmangid");
			getBillCardPanel().getBillModel("lby").setValueAt(cmangid, i, "cprocessbaseid");
		}
		
		// 2005-02-24 修改从合同生成时，业务类型不能保存
		String lstr_biztype = (String) getBillCardPanel().getHeadItem(
				"cbiztype").getValueObject();
		if (lstr_biztype == null || lstr_biztype.trim().length() == 0) {
			getBillCardPanel().getHeadItem("cbiztype").setValue(
					getBillCardPanel().getBusiType());
		}
		// 单据非空项检查
		try {
			ScTool.validateNotNullField(getBillCardPanel());
		} catch (Exception e) {
			SCMEnv.out(e);
			if (e instanceof BusinessException)
				showErrorMessage(e.getMessage());
			return false;
		}

		try {
			if (m_billState == ScConstants.STATE_ADD
					|| (m_billState == ScConstants.STATE_OTHER)) {

				// getBillCardPanel().getBillModel(TAB1).getItemByKey("nexchangeotobrate")
				// .setDecimalDigits(8);
				// 获得VO
				
				m_curOrderVO = (OrderVO) getBillCardPanel()
						.getBillValueChangeVO(OrderVO.class.getName(),
								OrderHeaderVO.class.getName(),
								OrderItemVO.class.getName());
				//gc 加入新页
				m_curOrderVO.setDdlbvos(getSaveDdlbVO());
				//gc---end,OrderDdlbVO.class.getName()
				m_curOrderVO.setPk_corp(getPk_corp());
				// 审核标志
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setDauditdate(null);
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setCauditpsn(null);

				// 表头备注
				UIRefPane ref = (UIRefPane) m_BillCardPanel
						.getHeadItem("vmemo").getComponent();
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setVmemo(ref
						.getUITextField().getText());

				// 单据状态
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setIbillstatus(BillStatus.FREE);

				// 当前操作员
				String tTime = (new UFDateTime(new Date())).toString();
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setCcuroperator(getOperatorID());// 后台出现curoperator丢失，通过脚本去重写，此处不管
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTmaketime(tTime);
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTaudittime(null);
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTlastmaketime(tTime);

				// 合法性校验
				m_curOrderVO.validate();

				// 计算主辅币
				if (!calNativeAndAssistCurrValue(m_curOrderVO))
					return false;

				// 行号检查
				if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
						getBillCardPanel(), "crowno"))
					return false;

				// 仓库和库存组织的匹配检查
				String ls_HeadWareHouseOrg = ((UIRefPane) getBillCardPanel()
						.getHeadItem("cwareid").getComponent()).getRefPK()
						.trim();

				for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
					Object l_ob = getBillCardPanel().getBillModel(TAB1)
							.getValueAt(i, "warehouseorg");
					if (l_ob != null && !l_ob.toString().trim().equals("")) {
						if (!ls_HeadWareHouseOrg.equals(l_ob.toString().trim())) {
							throw new ValidationException(nc.ui.ml.NCLangRes
									.getInstance().getStrByID("401201",
											"UPP401201-000051")/*
																 * @res
																 * "仓库与库存组织不匹配"
																 */);
						}
					}
				}

				String curPK = null;

				ArrayList userObj = new ArrayList();
				userObj.add(getOperatorID()); // userObjOld是原来的用户自定义对象参数
				userObj.add(new Integer(0)); // 0，供应商在单据表头；1，供应商在单据表体
				userObj.add("cvendormangid");

				java.util.ArrayList aryRet = null;
				OrderItemVO tempVO[] = (OrderItemVO[]) m_curOrderVO
						.getChildrenVO();
				if (!checkBeforeSave()) {
					return false;
				}

				if (tempVO[0].getCupsourcebilltype() != null
						&& tempVO[0].getCupsourcebilltype().equals(
								ScmConst.PO_Pray)) {
					boolean bConfirmed = false;
					m_curOrderVO.setUserConfirmFlag(UFBoolean.FALSE);
					while (!m_curOrderVO.getUserConfirmFlag().booleanValue()) {
						try {
							if (bConfirmed)
								m_curOrderVO.setUserConfirmFlag(UFBoolean.TRUE);
							aryRet = (java.util.ArrayList) PfUtilClient
									.processActionNoSendMessage(this,
											"SAVEBASE", ScmConst.SC_Order,
											nc.ui.pub.ClientEnvironment
													.getInstance().getDate()
													.toString(), m_curOrderVO,
											userObj, null, null);
						} catch (RwtScToPrException ex) {
							SCMEnv.out(ex.getMessage());
							if (MessageDialog
									.showYesNoDlg(
											this,
											nc.ui.ml.NCLangRes
													.getInstance()
													.getStrByID("SCMCOMMON",
															"UPPSCMCommon-000270")/*
																				 * @
																				 * res
																				 * "提示"
																				 */,
											ex.getMessage()
													+ nc.ui.ml.NCLangRes
															.getInstance()
															.getStrByID(
																	"401201",
																	"UPP401201-000064")/*
																						 * @
																						 * res
																						 * "是否继续?"
																						 */) == MessageDialog.ID_YES) {
								// 继续保存
								bConfirmed = true;
							} else {
								return false;
							}
						}
						if (!bConfirmed)
							break;// 未抛异常,跳出
					}
				} else {
					aryRet = (java.util.ArrayList) PfUtilClient
							.processActionNoSendMessage(this, "SAVEBASE",
									ScmConst.SC_Order,
									nc.ui.pub.ClientEnvironment.getInstance()
											.getDate().toString(),
									m_curOrderVO, userObj, null, null);
				}
				m_curOrderVO = (OrderVO) aryRet.get(1);
				if (m_curOrderVO != null) {
					curPK = ((OrderHeaderVO) m_curOrderVO.getParentVO())
							.getCorderid();
				}

				if (curPK == null)
					return false;

				// 同步oldNum
				updateOldNum();

				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setCoperator(getOperatorID());

				getModel().add((OrderHeaderVO) m_curOrderVO.getParentVO());
				getModel().last();

				// 若非转入单据，列表增加单据
				if (m_billState == ScConstants.STATE_ADD) {

					OrderHeaderVO[] headVOs = (OrderHeaderVO[]) getBillListPanel()
							.getHeadBillModel().getBodyValueVOs(
									OrderHeaderVO.class.getName());
					int num = headVOs.length;
					OrderHeaderVO[] newBillHVO = new OrderHeaderVO[num + 1];
					for (int i = 0; i < num; i++)
						newBillHVO[i] = headVOs[i];
					newBillHVO[num] = (OrderHeaderVO) m_curOrderVO
							.getParentVO();
					newBillHVO[num].setCorderid(curPK);

					getBillListPanel().setHeaderValueVO(newBillHVO);
					getBillListPanel().getHeadBillModel().execLoadFormula();
				}
			} else if (m_billState == ScConstants.STATE_MODIFY) {
				String key = getModel().getCurrentHeaderId();
				for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
					getBillCardPanel().setBodyValueAt(key, i, "corderid");//
				}

				// 单据状态
				// 单据在修改时候，原单据如果是审批未通过状态，则修改成自由态
				Object obj = getBillCardPanel().getHeadItem("ibillstatus")
						.getValueObject();
				if (obj != null
						&& Integer.valueOf(obj.toString()).equals(
								BillStatus.AUDITFAIL)) {
					getBillCardPanel().getHeadItem("ibillstatus").setValue(
							BillStatus.FREE);
					for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
						getBillCardPanel().getBillModel(TAB1).setRowState(i,
								BillModel.MODIFICATION);
					}
				}

				// 获得VO
				m_curOrderVO = (OrderVO) getBillCardPanel()
						.getBillValueChangeVO(OrderVO.class.getName(),
								OrderHeaderVO.class.getName(),
								OrderItemVO.class.getName());
				//gc 加入新页
				m_curOrderVO.setDdlbvos(getSaveDdlbVO());
				//gc---end,OrderDdlbVO.class.getName()
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setCauditpsn(null);
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setDauditdate(null);

				OrderItemVO[] items = (OrderItemVO[]) m_curOrderVO
						.getChildrenVO();
				for (int i = 0; i < items.length; i++) {
					items[i].setPk_corp(getPk_corp());
					if (items[i].getCorder_bid() != null
							&& !items[i].getCorder_bid().toString().trim()
									.equals("")) {
						if (items[i].getStatus() == VOStatus.DELETED) {
							items[i].setNoldnum(items[i].getNordernum());
						} else {
							items[i].setStatus(VOStatus.UPDATED);
						}
					}
				}
				//gc
				OrderDdlbVO[] ddlbs = (OrderDdlbVO[]) m_curOrderVO
						.getDdlbvos();
				for (int i = 0; i < ddlbs.length; i++) {
					ddlbs[i].setCorderid(key);
					ddlbs[i].setPk_corp(getPk_corp());
					if (ddlbs[i].getCorder_lb_id() != null
							&& !ddlbs[i].getCorder_lb_id().toString().trim()
									.equals("")) {
						if (ddlbs[i].getStatus() == VOStatus.DELETED) {
//							ddlbs[i].setNoldnum(items[i].getNordernum());
						} else {
							ddlbs[i].setStatus(VOStatus.UPDATED);
						}
					}
				}
				//gc-end
				// 表头备注
				UIRefPane ref = (UIRefPane) m_BillCardPanel
						.getHeadItem("vmemo").getComponent();
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setVmemo(ref
						.getUITextField().getText());

				// 当前操作员
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setCcuroperator(getOperatorID());

				// 合法性校验
				m_curOrderVO.validate();

				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTlastmaketime((new UFDateTime(new Date()))
								.toString());
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTaudittime(null);

				// 计算主辅币
				if (!calNativeAndAssistCurrValue(m_curOrderVO))
					return false;

				// 行号检查
				if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
						getBillCardPanel(), "crowno"))
					return false;

				// 仓库和库存组织的匹配检查，检查方法：比较表头库存组织cwareid和表体库存组织warehouseorg(注：表体库存组织根据表体的仓库ID带出)
				String ls_HeadWareHouseOrg = ((UIRefPane) getBillCardPanel()
						.getHeadItem("cwareid").getComponent()).getRefPK()
						.trim();

				for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
					Object l_ob = getBillCardPanel().getBillModel(TAB1)
							.getValueAt(i, "warehouseorg");
					if (l_ob != null && !l_ob.toString().trim().equals("")) {
						if (!ls_HeadWareHouseOrg.equals(l_ob.toString().trim())) {
							throw new ValidationException(nc.ui.ml.NCLangRes
									.getInstance().getStrByID("401201",
											"UPP401201-000051")/*
																 * @res
																 * "仓库与库存组织不匹配"
																 */);
						}
					}

				}
				//

				// OrderBO_Client.update(m_curOrderVO);
				ArrayList userObj = new ArrayList();
				userObj.add(getOperatorID()); // userObjOld是原来的用户自定义对象参数
				userObj.add(new Integer(0)); // 0，供应商在单据表头；1，供应商在单据表体
				userObj.add("cvendormangid");

				java.util.ArrayList aryRet = null;
				OrderItemVO tempVO[] = (OrderItemVO[]) m_curOrderVO
						.getChildrenVO();
				if (!checkBeforeSave()) {
					return false;
				}

				if (tempVO != null
						&& tempVO.length > 0
						&& tempVO[0].getCupsourcebilltype() != null
						&& tempVO[0].getCupsourcebilltype().equals(
								ScmConst.PO_Pray)) {
					boolean bConfirmed = false;
					m_curOrderVO.setUserConfirmFlag(UFBoolean.FALSE);
					while (!m_curOrderVO.getUserConfirmFlag().booleanValue()) {
						try {
							if (bConfirmed)
								m_curOrderVO.setUserConfirmFlag(UFBoolean.TRUE);
							aryRet = (java.util.ArrayList) PfUtilClient
									.processAction("SAVEBASE",
											ScmConst.SC_Order,
											nc.ui.pub.ClientEnvironment
													.getInstance().getDate()
													.toString(), m_curOrderVO,
											userObj);
						} catch (RwtScToPrException ex) {
							SCMEnv.out(ex.getMessage());
							if (MessageDialog
									.showYesNoDlg(
											this,
											nc.ui.ml.NCLangRes
													.getInstance()
													.getStrByID("SCMCOMMON",
															"UPPSCMCommon-000270")/*
																				 * @
																				 * res
																				 * "提示"
																				 */,
											ex.getMessage()
													+ nc.ui.ml.NCLangRes
															.getInstance()
															.getStrByID(
																	"401201",
																	"UPP401201-000064")/*
																						 * @
																						 * res
																						 * "是否继续?"
																						 */) == MessageDialog.ID_YES) {
								// 继续保存
								bConfirmed = true;
							} else {
								return false;
							}
						}
						if (!bConfirmed)
							break;// 未抛异常,跳出
					}
				} else {
					if (!checkBeforeSave()) {
						return false;
					}
					aryRet = (java.util.ArrayList) PfUtilClient.processAction(
							"SAVEBASE", ScmConst.SC_Order,
							nc.ui.pub.ClientEnvironment.getInstance().getDate()
									.toString(), m_curOrderVO, userObj);
				}

				m_curOrderVO = (OrderVO) aryRet.get(1);

				getModel().setHeaderAt(getModel().getCurrentIndex(),
						(OrderHeaderVO) m_curOrderVO.getParentVO());

				if (!PfUtilClient.isSuccess())
					return false;

				// 同步oldNum
				updateOldNum();

			}

			resetInvmandocRefModel();
			getBillCardPanel().updateValue();

			if (m_billState == ScConstants.STATE_OTHER
					&& getBillListPanel().getHeadBillModel().getRowCount() != 0) {
				// 单据转入的保存,回写请购单累计订货数量，回到列表界面，列表显示第一张单据表体
				cardLayout.last(this);
				setButtons(m_btnTree.getButtonArray());// 设置列表组按钮
				setButtonsState();

				if (m_billState == ScConstants.STATE_OTHER && comeVOs != null
						&& comeVOs.size() > 0 && comeVOs.get(0) != null) {
					boEdit.setEnabled(true);
					boCancelOut.setEnabled(true);
					boCancel.setEnabled(false);
					boLineOperator.setEnabled(false);
					boSave.setEnabled(false);
					updateButtons();
				}

				updateUI();
				return false;
			}
		} catch (ValidationException e) {
			showErrorMessage(e.getMessage());
			return false;
		} catch (Exception e) {
			// 回退单据号
			SCMEnv.out("回退单据号开始...");/* -=notranslate=- */
			try {
				if ((m_billState == ScConstants.STATE_OTHER)
						&& ((OrderHeaderVO) m_curOrderVO.getParentVO())
								.getVordercode() != null) { // 新增时不回退单据号
					IGetSysBillCode getSysBillCode = (IGetSysBillCode) NCLocator
							.getInstance().lookup(
									IGetSysBillCode.class.getName());
					getSysBillCode.returnBillNo(m_curOrderVO);
				}
			} catch (Exception ex) {
				SCMEnv.out("回退单据号异常结束");/* -=notranslate=- */
			}
			SCMEnv.out("回退单据号正常结束");/* -=notranslate=- */

			showErrorMessage("" + e.getMessage());
			SCMEnv.out(e);
			return false;
		}

		m_billState = ScConstants.STATE_CARD;
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		refreshData();
		setButtonsState();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH005")/* @res "保存成功" */);

		return true;
	}

	/**
	 * 
	 * 方法功能：使用订单数量更新oldNum，以便回写来源单据时使用。<br>
	 * 该方法一般都是在修改保存或新增保存后使用<br>
	 * <p>
	 * <p>
	 * 
	 * @author duy
	 * @time 2009-10-30 上午10:59:43
	 */
	private void updateOldNum() {// gc??????????
		if (m_curOrderVO != null) {
			OrderItemVO[] items = (OrderItemVO[]) m_curOrderVO.getChildrenVO();
			for (int i = 0; i < items.length; i++) {
				items[i].setNoldnum(items[i].getNordernum());
				getBillCardPanel().setBodyValueAt(items[i].getNoldnum(), i,
						BillBodyItemKeys.NOLDNUM);
			}
		}
	}

	/**
	 * 全选操作 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	private void onSelectAll() {
		int listHeadNum = getBillListPanel().getHeadBillModel().getRowCount();
		if (listHeadNum <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000069")/* @res "无数据选中" */);
			return;
		}
		for (int i = 0; i < listHeadNum; i++)
			getBillListPanel().getHeadBillModel().setRowState(i,
					BillModel.SELECTED);

		if (listHeadNum > 0)
			getBillListPanel().getHeadTable().setRowSelectionInterval(0,
					listHeadNum - 1);

		getBillListPanel().getHeadTable().updateUI();

		// clearList();

		getBillListPanel().getBodyBillModel().clearBodyData();
		//
		boEdit.setEnabled(false);
		boDel.setEnabled(false);
		boDocument.setEnabled(false);
		boReturn.setEnabled(true);

		int rowCount = getBillListPanel().getHeadTable().getSelectedRowCount();
		if (rowCount > 0) {
			boDocument.setEnabled(true);
			m_btnOthersFuncs.setEnabled(true);
		}
		if (rowCount == 1) {
			boEdit.setEnabled(true);
		}

		updateButtons();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000033")/* @res "全部选中成功" */);

	}

	/**
	 * 取消操作 *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	private void onSelectNo() {
		int listHeadNum = getBillListPanel().getHeadBillModel().getRowCount();
		if (listHeadNum <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000069")/* @res "无数据选中" */);
			return;
		}
		getBillListPanel().getHeadTable().clearSelection();
		for (int i = 0; i < listHeadNum; i++)
			getBillListPanel().getHeadBillModel().setRowState(i,
					BillModel.UNSTATE);

		clearList();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000034")/* @res "全部取消成功" */);
	}

	/**
	 * 此处插入方法说明。 创建日期：(2003-10-28 11:54:46)
	 */
	private void onSendAudit() {
		OrderVO vo = getSelectedVO();

		try {
			// 送审
			java.util.ArrayList<Object> retObj = (java.util.ArrayList<Object>) PfUtilClient
					.processAction("SAVE", ScmConst.SC_Order, ClientEnvironment
							.getInstance().getDate().toString(), vo);

			if (retObj != null && retObj.size() == 3) {
				OrderHeaderVO header = getModel().getCurrentHeader();
				header.setTs((String) retObj.get(1));
				header.setIbillstatus((Integer) retObj.get(2));

				getBillCardPanel().setHeadItem("ts", (String) retObj.get(1));
				getBillCardPanel().setHeadItem("ibillstatus",
						(Integer) retObj.get(2));

				getBillListPanel().getHeadBillModel().setValueAt(
						(String) retObj.get(1), getModel().getCurrentIndex(),
						"ts");
				getBillListPanel().getHeadBillModel().setValueAt(
						(Integer) retObj.get(2), getModel().getCurrentIndex(),
						"ibillstatus");
			}
			// 送审按钮细化控制-begin
			// updateButtons();
			// 送审按钮细化控制-end
			setButtonsState();

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000266")/* @res "送审成功" */);
		} catch (Exception e) {
			showErrorMessage(e.getMessage());
			SCMEnv.out(e);
			return;
		}
	}

	/**
	 * 委外订单弃审 创建日期：(2001-7-26 12:50:18)
	 */
	public void onUnAudit() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH049")/* @res "正在弃审" */);

		try {
			OrderVO vo = (OrderVO) this.getBillCardPanel()
					.getBillValueChangeVO(OrderVO.class.getName(),
							OrderHeaderVO.class.getName(),
							OrderItemVO.class.getName());
			//gc 加入新页
			OrderDdlbVO[] ddlbvo = getSaveDdlbVO();
			m_curOrderVO.setDdlbvos(ddlbvo);
			//gc-end
			if (vo != null) {
				((OrderHeaderVO) vo.getParentVO()).setDauditdate(null);
				((OrderHeaderVO) vo.getParentVO()).setCauditpsn(null);
				((OrderHeaderVO) vo.getParentVO())
						.setCcuroperator(getOperatorID());
				((OrderHeaderVO) vo.getParentVO()).setTaudittime(null);

				// 弃审
				Object retObj = PfUtilClient.processBatchFlow(null, "UNAPPROVE"
						+ getClientEnvironment().getUser().getPrimaryKey(),
						ScmConst.SC_Order, getClientEnvironment().getDate()
								.toString(), new OrderVO[] { vo }, null);

				if (!PfUtilClient.isSuccess()) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("SCMCOMMON", "UPPSCMCommon-000185")/*
																			 * @res
																			 * "弃审失败"
																			 */);
					return;
				}

				if ((retObj != null) || (retObj instanceof OrderVO[])
						|| ((((OrderVO[]) retObj)).length == 1)) {
					m_curOrderVO = (((OrderVO[]) retObj))[0];
				}
				if(m_curOrderVO.getDdlbvos() == null){
					m_curOrderVO.setDdlbvos(ddlbvo);
				}
				getModel().setHeaderAt(getModel().getCurrentIndex(),
						(OrderHeaderVO) m_curOrderVO.getParentVO());
				refreshBillCardPanel();

				setButtonsState();
			}
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH011")/* @res "弃审成功" */);
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000185")/* @res "弃审失败" */);
			return;
		}
	}

	private String[] getInvMangIdArrayFromView(String tab) {
		List<String> mangIdList = new ArrayList<String>();
		for (int i = 0, len = getBillCardPanel().getRowCount(); i < len; i++) {
			mangIdList.add((String) getBillCardPanel().getBillModel(tab).getValueAt(i,
					"cmangid"));
		}

		return new String[mangIdList.size()];
	}

	/**
	 * 判断卡片界面上的单据VO是否来源于CT
	 * 
	 * @return
	 */
	private boolean isFromCtInCard() {
		boolean isFromCT = false;
		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			String sourceType = (String) getBillCardPanel().getBodyValueAt(i,
					BillBodyItemKeys.CORDERSOURCE);
			if (sourceType != null
					&& (sourceType.toString().trim()
							.equals(BillTypeConst.CT_BEFOREDATE) || sourceType
							.toString().trim()
							.equals(BillTypeConst.CT_ORDINARY))) {
				isFromCT = true;
				break;
			}
		}

		return isFromCT;
	}

	/**
	 * 设置卡片面板表头与合同相关项的可编辑性
	 * 
	 * @param isEditable
	 */
	private void setCntRelatedItemEditableInCardHead() {
		if (isFromCtInCard()) {
			// 参照合同生成的单据，表头供应商、订单日期不可修改
			getBillCardPanel().getHeadItem(BillHeaderItemKeys.CVENDORMANGID)
					.setEdit(false);
			getBillCardPanel().getHeadItem(BillHeaderItemKeys.DORDERDATE)
					.setEdit(false);
		} else {
			// 非参照合同生成的单据，表头供应商、订单日期不可修改
			getBillCardPanel().getHeadItem(BillHeaderItemKeys.CVENDORMANGID)
					.setEdit(true);

			if (m_billState == ScConstants.STATE_MODIFY) {
				// 修改状态：订单日期不可编辑
				getBillCardPanel().getHeadItem(BillHeaderItemKeys.DORDERDATE)
						.setEdit(false);
			} else {
				getBillCardPanel().getHeadItem(BillHeaderItemKeys.DORDERDATE)
						.setEdit(true);
			}
		}
	}

	/**
	 * 卡片状态 加载转入单据数据 创建日期：(2001-9-6 9:54:14)
	 */
	private void refLoadData(int listRow) {// gc???????????
		try {
			// 修正界面显示状态
			cardLayout.first(this);
			setButtons(m_btnTree.getButtonArray());// 设置卡片组按钮

			// 加载转入单据数据
			OrderVO vo = (OrderVO) comeVOs.get(listRow);
//			getBillCardPanel().setBillValueVO(vo);//gc-old
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(vo);
			exBillVO.setParentVO((OrderHeaderVO)vo.getParentVO());
			exBillVO.addTableVO(TAB1,"", vo.getChildrenVO());
			exBillVO.addTableVO(TAB2,"", vo.getDdlbvos());
			getBillCardPanel().setBillValueVO(vo);
			//gc-end
			

			// 处理备注
			// 表头
			m_sHeadVmemo = ((OrderHeaderVO) vo.getParentVO()).getVmemo();
			UIRefPane ref = (UIRefPane) getBillCardPanel().getHeadItem("vmemo")
					.getComponent();
			ref.setText(m_sHeadVmemo);

			// 2009-9-28 renzhonghai 参照过来的单据如果没有业务流程则不能继续进行以后的操作。
			if (getBillCardPanel().getHeadItem("cbiztype").getValueObject() == null) {
				getBillCardPanel().getHeadItem("cbiztype").setEdit(true);
				getBillCardPanel().getHeadItem("cbiztype").setNull(true);
			}

			// 2009-9-10 renzhonghai 修改效率慢
			// 表体备注、默认项(扣率、扣税类别、计划到货日期)
			for (int i = 0, len = vo.getChildrenVO().length; i < len; i++) {
				// 默认项(扣率、扣税类别、计划到货日期)
				getBillCardPanel().setBodyValueAt("100", i, "ndiscountrate");
				getBillCardPanel().setBodyValueAt(new Integer(1), i,
						"idiscounttaxtype");

				// 若无计划到货日期，则给出默认
				Object arrDate = getBillCardPanel().getBodyValueAt(i,
						"dplanarrvdate");
				if (arrDate != null && !arrDate.toString().trim().equals(""))
					continue;
				int advancedDays = BillEdit.getAdvanceDays(getBillCardPanel(),
						i);
				getBillCardPanel()
						.setBodyValueAt(
								nc.ui.pub.ClientEnvironment.getInstance()
										.getDate().getDateAfter(advancedDays),
								i, "dplanarrvdate");
			}
			getBillCardPanel().updateUI();

			// 自由项
			loadFreeItems();
			// 供应商默认项
			BillEdit.editCust(getBillCardPanel(),
					BillHeaderItemKeys.CVENDORMANGID);

			// 2009-9-10 renzhonghai 效率慢，优化
			int[] descriptions = new int[] {
					RelationsCal.DISCOUNT_TAX_TYPE_NAME,
					RelationsCal.DISCOUNT_TAX_TYPE_KEY, RelationsCal.NUM,
					RelationsCal.TAXPRICE_ORIGINAL,
					RelationsCal.PRICE_ORIGINAL,
					RelationsCal.NET_TAXPRICE_ORIGINAL,
					RelationsCal.NET_PRICE_ORIGINAL,
					RelationsCal.DISCOUNT_RATE, RelationsCal.TAXRATE,
					RelationsCal.MONEY_ORIGINAL, RelationsCal.TAX_ORIGINAL,
					RelationsCal.SUMMNY_ORIGINAL };

			// 循环之前关闭合计行的计算，提供效率
			getBillCardPanel().getBillModel(TAB1).setNeedCalculate(false);
			// 数量关系联动、主辅计量换算、币种
			for (int i = 0, len = vo.getChildrenVO().length; i < len; i++) {

				Object oTemp = getBillCardPanel().getBodyValueAt(i,
						"idiscounttaxtype");
				String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// 应税外加
				if (oTemp != null) {
					lStr_discounttaxtype = oTemp.toString();
				}

				String[] keys = new String[] { lStr_discounttaxtype,
						"idiscounttaxtype", "nordernum", "norgtaxprice",
						"noriginalcurprice", "norgnettaxprice",
						"noriginalnetprice", "ndiscountrate", "ntaxrate",
						"noriginalcurmny", "noriginaltaxmny", "noriginalsummny" };

				// 辅单位、辅数量
				Object nordernum = getBillCardPanel().getBodyValueAt(i,
						"nordernum");
				getBillCardPanel().setBodyValueAt(nordernum, i, "nordernum");
				getBillCardPanel().setCellEditable(i, "nordernum", true);
				Object measrate = getBillCardPanel().getBodyValueAt(i,
						"measrate");
				if (measrate != null && !measrate.toString().equals("")
						&& nordernum != null
						&& !nordernum.toString().equals("")) {
					UFDouble uOrdernum = new UFDouble(nordernum.toString());
					UFDouble uMeasrate = new UFDouble(measrate.toString());
					getBillCardPanel().setBodyValueAt(uOrdernum.div(uMeasrate),
							i, "nassistnum");
				} else {
					if (m_billState == ScConstants.STATE_OTHER) {
						Object mr = getBillListPanel().getBodyBillModel()
								.getValueAt(i, "measrate");
						Object an = getBillListPanel().getBodyBillModel()
								.getValueAt(i, "nassistnum");
						getBillCardPanel().setBodyValueAt(mr, i, "measrate");
						getBillCardPanel().setBodyValueAt(an, i, "nassistnum");
					}
				}

				BillEdit.editFreeItem(getBillCardPanel(), i, "cinventorycode",
						"cbaseid", "cmangid");
				// 数量
				nordernum = getBillCardPanel().getBodyValueAt(i, "nordernum");
				BillItem item = getBillCardPanel().getBillModel(TAB1)
						.getItemByKey("nordernum");
				BillEditEvent e = new BillEditEvent(new BillCellEditor(
						(UIRefPane) item.getComponent()), nordernum,
						"nordernum", i);
				RelationsCal.calculate(getBillCardPanel(), e,
						getBillCardPanel().getBillModel(TAB1),
						new int[] { getPricePolicy() }, descriptions, keys,
						OrderItemVO.class.getName());
				// 单价
				item = getBillCardPanel().getBillModel(TAB1).getItemByKey(
						"noriginalcurprice");
				Object noriginalcurprice = getBillCardPanel().getBodyValueAt(i,
						"noriginalcurprice");
				BillEditEvent e1 = new BillEditEvent(new BillCellEditor(
						(UIRefPane) item.getComponent()), null,
						noriginalcurprice, "noriginalcurprice", i, 1);
				RelationsCal.calculate(getBillCardPanel(), e1,
						getBillCardPanel().getBillModel(TAB1),
						new int[] { getPricePolicy() }, descriptions, keys,
						OrderItemVO.class.getName());
				// 到货地址
				String creciever = ((OrderHeaderVO) vo.getParentVO())
						.getCreciever();
				if (creciever != null && !creciever.trim().equals("")) {
					setReceiveAddress(i, "creciever");
				}

				// //若币种为空，则给出供应商默认币种、税率
				try {
					Object curCurrtype = getBillCardPanel().getBodyValueAt(i,
							BillBodyItemKeys.CCURRENCYTYPEID);
					if (curCurrtype == null
							|| curCurrtype.toString().equals("")) {
						String cvendormangid = (String) getCardHeadItemValue(BillHeaderItemKeys.CVENDORMANGID);
						if (cvendormangid != null
								&& !cvendormangid.toString().equals("")) {

							CustDefaultVO custVO = OrderBHelper
									.getDefaultByCust(cvendormangid.toString());
							getBillCardPanel().setBodyValueAt(
									custVO.getCcurrtype(), i,
									BillBodyItemKeys.CCURRENCYTYPEID);
						}
					}

					setCurrencyExchangeRateEnable(i);
					setCurrencyExchangeRate(i);
				} catch (Exception t) {
					SCMEnv.out(t.getMessage());
					showErrorMessage(t.getMessage());
				}

				// 委外订单参照请购单，录入净单价税率后，含税单价为0，其它单价和金额都不为0。
				UFDouble norgtaxprice = (UFDouble) getBillCardPanel()
						.getBodyValueAt(i, BillBodyItemKeys.NORGTAXPRICE);
				if (norgtaxprice != null && norgtaxprice.doubleValue() == 0) {
					getBillCardPanel().setBodyValueAt(null, i,
							BillBodyItemKeys.NORGTAXPRICE);
				}
			}
			// 循环之后打开合计行的计算，songhy
			getBillCardPanel().getBillModel(TAB1).setNeedCalculate(true);

			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().updateUI();
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}

		// 加载上层和源头单据信息
		nc.ui.scm.sourcebill.SourceBillTool.loadSourceInfoAll(
				getBillCardPanel().getBillModel(TAB1), ScmConst.SC_Order);
		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			Object sourceType = getBillCardPanel().getBodyValueAt(i,
					BillBodyItemKeys.CORDERSOURCE);
			if (sourceType != null
					&& (sourceType.toString().trim()
							.equals(BillTypeConst.CT_BEFOREDATE) || sourceType
							.toString().trim()
							.equals(BillTypeConst.CT_ORDINARY))) {
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORIGINALCURPRICE, false);
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORIGINALNETPRICE, false);
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORGTAXPRICE, false);
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORGNETTAXPRICE, false);
			} else {
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORIGINALCURPRICE, true);
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORIGINALNETPRICE, true);
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORGTAXPRICE, true);
				getBillCardPanel().setCellEditable(i,
						BillBodyItemKeys.NORGNETTAXPRICE, true);
			}
		}

		// getBillCardPanel().getBillTable(tab).clearSelection();
		// 修正列表数据
		getBillListPanel().getComeVO().remove(listRow);
		getBillListPanel().loadHeadData();

		boEdit.setEnabled(false);
		setButtonsState();
		updateButtons();
	}

	/**
	 * 创建者：许京黎 功能： 参数： 返回： 例外： 日期：(2003-6-14 12:45:56) 修改日期，修改人，修改原因，注释标志：
	 */
	private void refreshData() {
		long tTime = System.currentTimeMillis();

		// 计算含税单价
		refreshBillCardPanel();
		loadCustBank();

		m_sHeadVmemo = ((OrderHeaderVO) m_curOrderVO.getParentVO()).getVmemo();
		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
				.getComponent();
		ref.setText(m_sHeadVmemo);

		// 处理自定义项
		OrderHeaderVO voHead = (OrderHeaderVO) m_curOrderVO.getParentVO();
		String[] saKey = new String[] { "vdef1", "vdef2", "vdef3", "vdef4",
				"vdef5", "vdef6", "vdef7", "vdef8", "vdef9", "vdef10" };
		int iLen = saKey.length;
		for (int i = 0; i < iLen; i++) {
			JComponent component = getBillCardPanel().getHeadItem(saKey[i])
					.getComponent();
			if (component instanceof UIRefPane) {
				voHead.setAttributeValue(saKey[i],
						((UIRefPane) component).getText());
			}
		}

		getBillCardPanel().getBillModel(TAB1).execLoadFormula();

		loadMeasRate();
		getBillCardPanel().updateValue();
		loadFreeItems();

		// 显示扣税类别
		OrderItemVO[] orderItemVO = (OrderItemVO[]) m_curOrderVO
				.getChildrenVO();
		for (int i = 0; i < orderItemVO.length; i++) {
			Integer idiscounttaxtype = orderItemVO[i].getIdiscounttaxtype();

			getBillCardPanel().getBillModel(TAB1).setValueAt(idiscounttaxtype,
					i, "idiscounttaxtype");
			// 2009-10-29 renzhonghai 显示批次号
			String vproduceNum = orderItemVO[i].getVproducenum();
			getBillCardPanel().getBillModel(TAB1).setValueAt(vproduceNum, i,
					"vproducenum");

		}

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000253")/* @res "数据加载成功" */);
		SCMEnv.out("数据加载：[共用时" + (System.currentTimeMillis() - tTime) / 1000
				+ "秒]");/* -=notranslate=- */

		// 维护界面状态（作废、修改）
		int ibillstate = ((OrderHeaderVO) m_curOrderVO.getParentVO())
				.getIbillstatus().intValue();

		for (int i = 0; i < boAction.getChildButtonGroup().length; i++) {
			if (boAction.getChildButtonGroup()[i] != null) {
				if (boAction.getChildButtonGroup()[i].getTag() != null) {
					if (boAction.getChildButtonGroup()[i].getTag().equals(
							"APPROVE"))
						boAudit = boAction.getChildButtonGroup()[i];
					if (boAction.getChildButtonGroup()[i].getTag().equals(
							"UNAPPROVE"))
						boUnaudit = boAction.getChildButtonGroup()[i];
				}
			}
		}

		setOperateState(ibillstate);
	}

	/**
	 * 刷新界面上卡片界面表头和表体的部分数据
	 * 
	 * @author songhy
	 */
	private void refreshBillCardPanel() {
		// 刷新卡片“表头PK”、“单据号”、“TS”和“单据状态”等信息
		OrderHeaderVO header = ((OrderHeaderVO) m_curOrderVO.getParentVO());
		getBillCardPanel().setHeadItem("corderid", header.getCorderid());
		getBillCardPanel().setHeadItem("vordercode", header.getVordercode());
		getBillCardPanel().setHeadItem("ts", header.getTs());
		getBillCardPanel().setHeadItem("ibillstatus", header.getIbillstatus());
		getBillCardPanel().setTailItem("coperator", header.getCoperator());
		getBillCardPanel().setTailItem("cauditpsn", header.getCauditpsn());
		getBillCardPanel().setTailItem("dauditdate", header.getDauditdate());
		getBillCardPanel().setTailItem("taudittime", header.getTaudittime());
		getBillCardPanel().setTailItem("tmaketime", header.getTmaketime());
		getBillCardPanel().setTailItem("tlastmaketime",
				header.getTlastmaketime());

		// 刷新卡片表体PK和TS
		OrderItemVO[] orderItems = (OrderItemVO[]) m_curOrderVO.getChildrenVO();
		int len = getBillCardPanel().getBillModel(TAB1).getRowCount();
		for (int i = 0; i < len; i++) {
			getBillCardPanel().setBodyValueAt(orderItems[i].getCorderid(), i,
					"corderid",TAB1);
			getBillCardPanel().setBodyValueAt(orderItems[i].getCorder_bid(), i,
					"corder_bid",TAB1);
			getBillCardPanel().setBodyValueAt(orderItems[i].getTs(), i, "ts",TAB1);
		}
		//gc
		OrderDdlbVO[] ddlbvos = m_curOrderVO.getDdlbvos();
		int len2 = getBillCardPanel().getBillModel(TAB2).getRowCount();
		for (int i = 0; i < len2; i++) {
			getBillCardPanel().setBodyValueAt(ddlbvos[i].getCorderid(), i,
					"corderid",TAB2);
			getBillCardPanel().setBodyValueAt(ddlbvos[i].getCorder_lb_id(), i,
					"corder_lb_id",TAB2);
			getBillCardPanel().setBodyValueAt(ddlbvos[i].getTs(), i, "ts",TAB2);
		}
	}

	/**
	 * 功能描述:浮动菜单右键功能权限控制
	 * <p>
	 * <b>变更历史：</b>
	 * <p>
	 * <hr>
	 * <p>
	 * 修改日期 2008.8.28
	 * <p>
	 * 修改人 zhaoyha
	 * <p>
	 * 版本 5.5
	 * <p>
	 * 说明：
	 * <ul>
	 * <li>设定更多的右键菜单控制权限
	 * </ul>
	 */
	private void rightButtonRightControl() {
		// 没有分配行操作权限
		if (boLineOperator == null || boLineOperator.getChildCount() == 0) {
			getBillCardPanel().getAddLineMenuItem().setEnabled(false);
			getBillCardPanel().getInsertLineMenuItem().setEnabled(false);
			getBillCardPanel().getCopyLineMenuItem().setEnabled(false);
			getBillCardPanel().getDelLineMenuItem().setEnabled(false);
			getBillCardPanel().getPasteLineMenuItem().setEnabled(false);
			getBillCardPanel().getPasteLineToTailMenuItem().setEnabled(false);
			getReOrderRowNoMenuItem().setEnabled(false);
			getCardEditMenuItem().setEnabled(false);
		}
		// 分配行操作权限
		else {
			getBillCardPanel().getAddLineMenuItem().setEnabled(
					boAddLine.isPower());
			getBillCardPanel().getInsertLineMenuItem().setEnabled(
					boInsertLine.isPower());
			getBillCardPanel().getCopyLineMenuItem().setEnabled(
					boCopyLine.isPower());
			getBillCardPanel().getDelLineMenuItem().setEnabled(
					boDelLine.isPower());
			getBillCardPanel().getPasteLineMenuItem().setEnabled(
					boPasteLine.isPower());
			// 粘贴到行尾与粘贴可用性逻辑相同
			getBillCardPanel().getPasteLineToTailMenuItem().setEnabled(
					boPasteLineToTail.isPower());
			getReOrderRowNoMenuItem().setEnabled(boReOrderRowNo.isPower());
			getCardEditMenuItem().setEnabled(boCardEdit.isPower());
		}
	}

	/**
	 * 向单据模板设置VO 在列表显示下，点击打印时，会调用此方法
	 * 
	 * 创建日期：(2003-11-28 9:13:10)
	 * 
	 * @param vo
	 *            nc.vo.pub.AggregatedValueObject
	 */
	public void setBillVO(nc.vo.pub.AggregatedValueObject vo) {
		if (getModel().size() == 0) {
			return;
		}

		m_curOrderVO = (OrderVO) vo;

		// 计算含税单价
//		getBillCardPanel().setBillValueVO(m_curOrderVO);//gc-old
		//gc
		nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(m_curOrderVO);
		exBillVO.setParentVO((OrderHeaderVO)m_curOrderVO.getParentVO());
		exBillVO.addTableVO(TAB1,"", m_curOrderVO.getChildrenVO());
		exBillVO.addTableVO(TAB2,"", m_curOrderVO.getDdlbvos());
		getBillCardPanel().setBillValueVO(exBillVO);
		//gc-end
		loadCustBank();
		getBillCardPanel().setBillValueVO(m_curOrderVO);//gc
		nc.vo.pub.ExAggregatedVO exBillVO1 = new nc.vo.pub.ExAggregatedVO(m_curOrderVO);
		exBillVO1.setParentVO((OrderHeaderVO)m_curOrderVO.getParentVO());
		exBillVO1.addTableVO(TAB1,"", m_curOrderVO.getChildrenVO());
		exBillVO1.addTableVO(TAB2,"", m_curOrderVO.getDdlbvos());
		getBillCardPanel().setBillValueVO(exBillVO1);
		//gc-end

		// add by hanbin 2009-11-24 原因：如果不加上，打印预览时金额精度显示不正确
		this.setPrecision();

		m_sHeadVmemo = ((OrderHeaderVO) m_curOrderVO.getParentVO()).getVmemo();
		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
				.getComponent();
		ref.setText(m_sHeadVmemo);

		// 处理自定义项
		OrderHeaderVO voHead = (OrderHeaderVO) m_curOrderVO.getParentVO();
		String[] saKey = new String[] { "vdef1", "vdef2", "vdef3", "vdef4",
				"vdef5", "vdef6", "vdef7", "vdef8", "vdef9", "vdef10" };
		int iLen = saKey.length;
		for (int i = 0; i < iLen; i++) {
			JComponent component = getBillCardPanel().getHeadItem(saKey[i])
					.getComponent();
			if (component instanceof UIRefPane) {
				voHead.setAttributeValue(saKey[i],
						((UIRefPane) component).getText());
			}
		}

		long s1 = System.currentTimeMillis();

		getBillCardPanel().getBillModel(TAB1).execLoadFormula();
		SCMEnv.out("执行公式[共用时" + (System.currentTimeMillis() - s1) + "]");/*
																		 * -=
																		 * notranslate
																		 * =-
																		 */

		loadMeasRate();
		getBillCardPanel().updateValue();
		loadFreeItems();

		// 显示扣税类别
		OrderItemVO[] orderItemVO = (OrderItemVO[]) m_curOrderVO
				.getChildrenVO();
		for (int i = 0; i < orderItemVO.length; i++) {
			Integer idiscounttaxtype = orderItemVO[i].getIdiscounttaxtype();

			getBillCardPanel().getBillModel(TAB1).setValueAt(idiscounttaxtype,
					i, "idiscounttaxtype");
		}
		//
		nc.ui.scm.sourcebill.SourceBillTool.loadSourceInfoAll(
				getBillCardPanel().getBillModel(TAB1), ScmConst.SC_Order);
	}

	/**
	 * 设置按钮状态。 创建日期：(2001-3-17 9:00:09)
	 */
	private void setButtonsState() {
		boFrash.setEnabled(m_bQueried);// 查询过才有效
		boCancelOut.setEnabled(false);

		if (m_billState == ScConstants.STATE_OTHER) {
			boAudit.setEnabled(false);
			boCancel.setEnabled(!boEdit.isEnabled());
			boSave.setEnabled(!boEdit.isEnabled());
			boLineOperator.setEnabled(!boEdit.isEnabled());

			// 单据转入状态
			boAdd.setEnabled(false);
			boDel.setEnabled(false);
			boQuery.setEnabled(false);
			boBusitype.setEnabled(false);

			boAddLine.setEnabled(true);
			boDelLine.setEnabled(true);

			// modify by yye
			boCopyLine.setEnabled(true);
			boInsertLine.setEnabled(true);
			boPasteLine.setEnabled(true);
			//

			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			boFirst.setEnabled(false);

			boReturn.setEnabled(false);

			boSelectAll.setEnabled(false);
			boSelectNo.setEnabled(false);

			boCopy.setEnabled(false);

			boAction.setEnabled(false);
			boCancelOut.setEnabled(getBillListPanel().isShowing());
			boLocate.setEnabled(false);

			boFrash.setEnabled(false);// 刷新
			setGroupButtonsState(UFBoolean.FALSE);
			getBillCardPanel().setEnabled(true);

		} else if (m_billState == ScConstants.STATE_LIST) {
			boAdd.setEnabled(false);
			boBusitype.setEnabled(false);
			boLocate.setEnabled(true);
			boQuery.setEnabled(true);

			boSelectAll.setEnabled(true);
			boSelectNo.setEnabled(true);
			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			boFirst.setEnabled(false);
			boCopy.setEnabled(false);

			if (getBillListPanel().getHeadTable().getSelectedRowCount() == 1) {
				setGroupButtonsState(UFBoolean.TRUE);
			} else {
				setGroupButtonsState(UFBoolean.FALSE);
			}
			if (getModel().size() > 0) {
				boLocate.setEnabled(true);
				boAction.setEnabled(true);
				Integer status = ((OrderHeaderVO) getSelectedVO().getParentVO())
						.getIbillstatus();
				String auditpsn = ((OrderHeaderVO) getSelectedVO()
						.getParentVO()).getCauditpsn();
				if (auditpsn == null || auditpsn.length() == 0)
					boSendAudit.setEnabled(true);
				else
					boSendAudit.setEnabled(false);
				if (status != null && status.equals(BillStatus.AUDITED)) {
					boAudit.setEnabled(false);
					boUnaudit.setEnabled(true);
					boEdit.setEnabled(false);
					boDel.setEnabled(false);
				}
				// 2009-10-27 renzhonghai 送审状态下不能删除单据
				else if (status != null && status.equals(BillStatus.AUDITING)) {
					boAudit.setEnabled(true);
					boUnaudit.setEnabled(true);
					if (auditpsn == null || auditpsn.length() == 0)
						boEdit.setEnabled(true);
					else
						boEdit.setEnabled(false);
					boDel.setEnabled(false);
				} else {
					boAudit.setEnabled(true);
					boUnaudit.setEnabled(false);
					boEdit.setEnabled(true);
					boDel.setEnabled(true);
				}
			} else {
				boLocate.setEnabled(false);
				boSelectAll.setEnabled(false);
				boSelectNo.setEnabled(false);
				boEdit.setEnabled(false);
				boDel.setEnabled(false);
			}
		} else if (m_billState == ScConstants.STATE_CARD) {
			boLocate.setEnabled(false);// 定位
			boBusitype.setEnabled(true);
			boAdd.setEnabled(true);
			boEdit.setEnabled(false);
			boSelectAll.setEnabled(false);
			boSelectNo.setEnabled(false);
			boSave.setEnabled(false);
			boCancel.setEnabled(false);
			boDel.setEnabled(false);
			boQuery.setEnabled(true);

			boLineOperator.setEnabled(false);
			boAddLine.setEnabled(true);
			boDelLine.setEnabled(true);
			boCopyLine.setEnabled(true);
			boInsertLine.setEnabled(true);
			boPasteLine.setEnabled(true);

			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			boFirst.setEnabled(false);

			boReturn.setEnabled(true);

			if (getModel().size() == 0) {
				boCopy.setEnabled(true);
				boAction.setEnabled(false);
				boAudit.setEnabled(false);
				setGroupButtonsState(UFBoolean.FALSE);
			} else {
				boCopy.setEnabled(true);
				boAction.setEnabled(true);
				Integer status = ((OrderHeaderVO) getSelectedVO().getParentVO())
						.getIbillstatus();
				String auditpsn = ((OrderHeaderVO) getSelectedVO()
						.getParentVO()).getCauditpsn();
				if (auditpsn == null || auditpsn.length() == 0)
					boSendAudit.setEnabled(true);
				else
					boSendAudit.setEnabled(false);
				if (status != null && status.equals(BillStatus.FREE)) {
					boAudit.setEnabled(true);
					boUnaudit.setEnabled(false);
					boEdit.setEnabled(true);
					boDel.setEnabled(true);
				}
				// 2009-10-27 renzhonghai 送审状态下不能删除单据
				else if (status != null && status.equals(BillStatus.AUDITING)) {
					boAudit.setEnabled(true);
					boUnaudit.setEnabled(true);
					if (auditpsn == null || auditpsn.length() == 0)
						boEdit.setEnabled(true);
					else
						boEdit.setEnabled(false);
					boDel.setEnabled(false);
				} else {
					boAudit.setEnabled(false);
					boUnaudit.setEnabled(true);
					boEdit.setEnabled(false);
					boDel.setEnabled(false);
				}
				setPgUpDownButtonsState(getModel().getCurrentIndex());
				setGroupButtonsState(UFBoolean.TRUE);
			}

			getBillCardPanel().setEnabled(false);
		} else if (m_billState == ScConstants.STATE_ADD) {
			boAdd.setEnabled(false);
			boEdit.setEnabled(false);
			boCancel.setEnabled(true);
			boSave.setEnabled(true);
			boDel.setEnabled(false);
			boQuery.setEnabled(false);
			boBusitype.setEnabled(false);

			boLineOperator.setEnabled(true);
			boAddLine.setEnabled(true);
			boDelLine.setEnabled(true);
			boCopyLine.setEnabled(true);
			boInsertLine.setEnabled(true);
			boPasteLine.setEnabled(true);

			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			boFirst.setEnabled(false);

			boSelectAll.setEnabled(false);// 全选
			boSelectNo.setEnabled(false);// 全消
			boFrash.setEnabled(false);// 刷新
			boLocate.setEnabled(false);// 定位
			boReturn.setEnabled(false);
			boCopy.setEnabled(false);
			boAction.setEnabled(false);

			// 界面新增状态“审批”按钮不可用，songhy, 2009-06-01, start
			boAudit.setEnabled(false);
			// songhy, 2009-06-01, end

			setGroupButtonsState(UFBoolean.FALSE);
			getBillCardPanel().setEnabled(true);
		} else if (m_billState == ScConstants.STATE_MODIFY) {
			boAdd.setEnabled(false);
			boEdit.setEnabled(false);
			boAudit.setEnabled(false);
			boCancel.setEnabled(true);
			boSave.setEnabled(true);
			boDel.setEnabled(false);
			boQuery.setEnabled(false);
			boBusitype.setEnabled(false);

			boLineOperator.setEnabled(true);
			boAddLine.setEnabled(true);
			boDelLine.setEnabled(true);
			boCopyLine.setEnabled(true);
			boInsertLine.setEnabled(true);
			boPasteLine.setEnabled(true);

			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			boFirst.setEnabled(false);

			boSelectAll.setEnabled(false);// 全选
			boSelectNo.setEnabled(false);// 全消
			boFrash.setEnabled(false);// 刷新
			boLocate.setEnabled(false);// 定位
			boReturn.setEnabled(false);

			boCopy.setEnabled(false);
			boAction.setEnabled(false);
			setGroupButtonsState(UFBoolean.FALSE);
			getBillCardPanel().setEnabled(true);
		}

		updateButtons();
	}

	/**
	 * 设置打印管理组、辅助查询组、辅助功能组 的按钮状态。 创建日期：(2001-3-17 9:00:09)
	 */
	private void setGroupButtonsState(UFBoolean state) {
		// 打印管理组按钮
		m_btnPrints.setEnabled(state.booleanValue());
		m_btnPrintPreview.setEnabled(state.booleanValue());
		boPrint.setEnabled(state.booleanValue());
		btnBillCombin.setEnabled(state.booleanValue());

		// 辅助查询组按钮
		m_btnOthersQueries.setEnabled(state.booleanValue());
		boLinkQuery.setEnabled(state.booleanValue());// 联查
		boQueryForAudit.setEnabled(state.booleanValue());// 审批流状态

		// 辅助功能组按钮
		m_btnOthersFuncs.setEnabled(state.booleanValue());// 辅助功能
		boDocument.setEnabled(state.booleanValue());// 文档管理
	}

	/**
	 * 如果中间有的行的存货为空，则剔除掉 由价格审批单推式生成委外订单也剔除掉
	 * 
	 * @param selectedRows
	 *            界面中选择的行数组
	 * @return 过滤后的行数组
	 */
	private int[] filterCtRelatedRows(int[] rows) {
		List<Integer> filteredRows = new ArrayList<Integer>();
		for (int i = 0; i < rows.length; i++) {
			// 由价格审批单推式生成委外订单也剔除掉,不再询价
			if (SCPubVO.getString_TrimZeroLenAsNull(getBillCardPanel()
					.getBodyValueAt(i, "cpriceauditid")) == null) {
				if (getBillCardPanel().getBodyValueAt(rows[i], "cmangid") != null
						&& getBillCardPanel()
								.getBodyValueAt(rows[i], "cmangid").toString()
								.length() > 0) {
					filteredRows.add(rows[i]);
				}
			}
		}

		int rowCount = filteredRows.size();
		if (rowCount > 0) {
			int[] retRowsArray = new int[rowCount];
			for (int i = 0; i < rowCount; i++) {
				retRowsArray[i] = filteredRows.get(i);
			}
			return retRowsArray;
		} else {
			return null;
		}
	}

	/**
	 * 1，清空合同相关信息：先将合同ID 合同行ID 合同号。 2，设置行状态：新增BillModel.ADD 或
	 * 修改BillModel.MODIFICATION
	 * 
	 * @param aintary_rows
	 *            行号数组
	 */
	private void clearContractInfo(int[] aintary_rows) {
		// 若查询出关联合同为空。先将合同ID 合同行ID 合同号 设置为空
		for (int i = 0, len = aintary_rows.length; i < len; i++) {
			int lint_rowindex = aintary_rows[i];// 行序号
			if (!isFromCT(lint_rowindex)) {// 如果来源不是合同
				setCardBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTID,TAB1);
				setCardBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTROWID,TAB1);
				setCardBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTRCODE,TAB1);

				String corder_bid = (String) getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.CORDER_BID,TAB1);
				if (StringUtil.isEmptyWithTrim(corder_bid)) {
					getBillCardPanel().getBillModel(TAB1).setRowState(
							lint_rowindex, BillModel.ADD);
				} else {
					getBillCardPanel().getBillModel(TAB1).setRowState(
							lint_rowindex, BillModel.MODIFICATION);
				}
			}
		}
	}

	/**
	 * 判断第 rowIndex 行是否来源与合同
	 * 
	 * @param rowIndex
	 * @return
	 */
	private boolean isFromCT(int rowIndex) {
		// 判断第 rowIndex 行是否来源与合同
		String sUpSourceType = (String) getBillCardPanel().getBodyValueAt(
				rowIndex, BillBodyItemKeys.CUPSOURCEBILLTYPE);
		return (sUpSourceType != null)
				&& (sUpSourceType.equals(BillTypeConst.CT_BEFOREDATE) || sUpSourceType
						.equals(BillTypeConst.CT_ORDINARY));
	}

	/**
	 * 判断是否需要关联合同
	 * 
	 * @param aintary_rows
	 *            行号数组
	 * @return
	 */
	private boolean needRelateCT(int[] aintary_rows) {
		for (int i = 0; i < aintary_rows.length; i++) {
			int lint_rowindex = aintary_rows[i];// 行序号
			if (!isFromCT(lint_rowindex)) {// 如果来源不是合同
				int iRet = showYesNoMessage(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("40040200", "UPP40040200-000034")/*
																	 * @res
																	 * "寻找到合同，是否关联？"
																	 */
				);

				if (iRet == MessageDialog.ID_YES) {
					return true;
				}

				break;
			}
		}

		return false;
	}

	private void setCardBodyCellEditable(String itemKey, int rowIndex) {
		BillItem billBodyItem = getBillCardPanel().getBillModel(TAB1)
				.getItemByKey(itemKey);
		// 没有关联上合同,则币种可编辑
		getBillCardPanel().setCellEditable(rowIndex, itemKey,
				(billBodyItem != null) && billBodyItem.isEdit());
	}

	/**
	 * 关联合同信息: 合同价，合同号
	 * 
	 * @param aintary_rows
	 *            行标识数组
	 * @return 为询到合同价的行号数组
	 * 
	 * @throws Exception
	 */
	private int[] relateContractInfo(int[] aintary_rows) throws Exception {
		if (!isCtStartUp()) {
			// 如果合同没有启用，返回所有行标识数组
			return aintary_rows;
		}

		// 清空选择行的合同相关信息
		clearContractInfo(aintary_rows);

		// 表头-供应商基础id
		String lStr_vendorbasid = (String) getBillCardPanel().getHeadItem(
				BillBodyItemKeys.CVENDORID).getValueObject();

		// 表头-订单日期
		String lStr_orderdate = (String) getBillCardPanel().getHeadItem(
				BillHeaderItemKeys.DORDERDATE).getValueObject();

		// 记录行号，标识是否还需要进一步去询默认价格
		List<Integer> rowsForPriceAgain = new ArrayList<Integer>();

		int li_rowcount = aintary_rows.length;
		String[] lStrary_baseid = new String[li_rowcount]; // 存货基本档案Id数组
		String[] currencyTypeIds = new String[li_rowcount]; // 当前默认币种Id数组
		String[] rowNumbers = new String[li_rowcount]; // 行号数组

		for (int i = 0; i < li_rowcount; i++) {
			int lint_rowindex = aintary_rows[i];// 行序号
			lStrary_baseid[i] = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CBASEID,TAB1);
			currencyTypeIds[i] = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CCURRENCYTYPEID,TAB1);
			rowNumbers[i] = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CROWNO,TAB1);
		}

		// 为提高效率，一次性查询合同相关信息
		RetCtToPoQueryVO[] lary_CtRetVO = ScFromCtHelper.queryForCntAll(
				rowNumbers, getPk_corp(), lStrary_baseid, lStr_vendorbasid,
				currencyTypeIds, new UFDate(lStr_orderdate));

		if ((lary_CtRetVO == null) || (lary_CtRetVO.length <= 0)
				|| (!needRelateCT(aintary_rows))) {
			// 如果查询结果为空，或者用户选择不关联合同，返回所有行标识数组
			return aintary_rows;
		}

		// add by hanbin 2009-11-5
		// 用于标识：所选择的加工品和所关联的合同是不是一一对应，如果是直接显示对应的合同，不需要在弹出对话框
		boolean isOne2One = true;

		List<String> tempList = new ArrayList<String>();
		for (RetCtToPoQueryVO ctVO : lary_CtRetVO) {
			// 根据行号是否存在重复，来进行判断
			if (tempList.contains(ctVO.getM_cRowno())) {
				isOne2One = false;
				break;
			}
			tempList.add(ctVO.getM_cRowno());
		}

		// 用于存储所选择的合同
		RetCtToPoQueryVO[] voArraySelected = null;
		if (isOne2One) {
			voArraySelected = lary_CtRetVO;// 如果是直接显示对应的合同，不需要在弹出对话框
		} else {
			if (m_CntSelDlg == null) {
				m_CntSelDlg = new CntSelDlg(this);
			}
			m_CntSelDlg.setOnOK(false);
			m_CntSelDlg.setCntDatas(lary_CtRetVO);
			m_CntSelDlg.showModal();

			if (m_CntSelDlg.isON_OK()) {
				voArraySelected = m_CntSelDlg.getRetVOs();
			}
		}
		if ((voArraySelected == null) || (voArraySelected.length == 0)) {// 没有关联上合同
			for (int i = 0; i < li_rowcount; i++) {
				int lint_rowindex = aintary_rows[i];// 行序号

				rowsForPriceAgain.add(lint_rowindex);

				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTID);
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTROWID);
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTRCODE);

				// 没有关联上合同,则币种可编辑
				setCardBodyCellEditable(BillBodyItemKeys.CCURRENCYTYPE,
						lint_rowindex);

				// 没有关联上合同,则价格可编辑
				setCardBodyCellEditable(BillBodyItemKeys.NORIGINALCURPRICE,
						lint_rowindex);

				// 没有关联上合同,则含税单价可编辑
				setCardBodyCellEditable(BillBodyItemKeys.NORGTAXPRICE,
						lint_rowindex);

				String corder_bid = (String) getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.CORDER_BID,TAB1);
				if (StringUtil.isEmptyWithTrim(corder_bid)) {
					getBillCardPanel().getBillModel(TAB1).setRowState(
							lint_rowindex, BillModel.ADD);
				} else {
					getBillCardPanel().getBillModel(TAB1).setRowState(
							lint_rowindex, BillModel.MODIFICATION);
				}
			}
		} else {// 关联上了合同
			Map<String, RetCtToPoQueryVO> voMap = new HashMap<String, RetCtToPoQueryVO>();
			for (RetCtToPoQueryVO item : voArraySelected) {
				voMap.put((String) item
						.getAttributeValue(BillBodyItemKeys.CROWNO), item);
			}

			for (int i = 0; i < li_rowcount; i++) {
				int lint_rowindex = aintary_rows[i];// 行序号
				String crowno = (String) getBillCardPanel().getBodyValueAt(
						lint_rowindex, BillBodyItemKeys.CROWNO);
				RetCtToPoQueryVO lCtRetVO_Selected = voMap.get(crowno);

				if (lCtRetVO_Selected == null) {
					// 表体行上没有关联的合同号，此行不做处理，继续往后查找关联的合同号
					continue;
				}

				// 合同ID 合同号
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCContractID(), lint_rowindex,
						BillBodyItemKeys.CCONTRACTID);
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCContractRowId(), lint_rowindex,
						BillBodyItemKeys.CCONTRACTROWID);
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCContractCode(), lint_rowindex,
						BillBodyItemKeys.CCONTRACTRCODE);

				// 关联上合同,则使用合同币种,且币种不可编辑
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCCurrencyId(), lint_rowindex,
						BillBodyItemKeys.CCURRENCYTYPEID);

				if (getBillCardPanel().getBodyValueAt(lint_rowindex,
						"corder_bid") == null
						|| getBillCardPanel()
								.getBodyValueAt(lint_rowindex, "corder_bid")
								.toString().trim().length() == 0) {
					getBillCardPanel().getBillModel(TAB1).setRowState(
							lint_rowindex, BillModel.ADD);
				} else {
					getBillCardPanel().getBillModel(TAB1).setRowState(
							lint_rowindex, BillModel.MODIFICATION);
				}

				// //理论上,考虑效率因素,可以放到循环外执行,但实际上没有必要
				String formula = "ccurrencytype->getColValue(bd_currtype,currtypename,pk_currtype,ccurrencytypeid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(i,
						new String[] { formula });
				getBillCardPanel().setCellEditable(lint_rowindex,
						"ccurrencytype", false);
				setCurrencyExchangeRateEnable(lint_rowindex);
				setCurrencyExchangeRate(lint_rowindex);
				getBillCardPanel().updateUI();

				// 价格
				UFDouble lUFD_Price = null;
				lUFD_Price = SCPubVO.getPriceValueByPricePolicy(
						lCtRetVO_Selected, getPricePolicy());

				if (lUFD_Price != null) {
					String priceItemKey = ScUtils
							.getPriceFieldByPricePolicy(getPricePolicy());
					Object valueObject = getBillCardPanel().getBodyValueAt(
							lint_rowindex, priceItemKey);
					UFDouble lUFD_OldPrice = SCPubVO
							.getUFDouble_ValueAsValue(valueObject);

					setCardBodyValueAt(lCtRetVO_Selected.getCContractID(),
							lint_rowindex, BillBodyItemKeys.CCONTRACTID,TAB1);
					setCardBodyValueAt(lCtRetVO_Selected.getCContractRowId(),
							lint_rowindex, BillBodyItemKeys.CCONTRACTROWID,TAB1);
					setCardBodyValueAt(lCtRetVO_Selected.getCContractCode(),
							lint_rowindex, BillBodyItemKeys.CCONTRACTRCODE,TAB1);

					if (getBillCardPanel().getBodyValueAt(lint_rowindex,
							"corder_bid") == null
							|| getBillCardPanel()
									.getBodyValueAt(lint_rowindex, "corder_bid")
									.toString().trim().length() == 0) {
						getBillCardPanel().getBillModel(TAB1).setRowState(
								lint_rowindex, BillModel.ADD);
					} else {
						getBillCardPanel().getBillModel(TAB1).setRowState(
								lint_rowindex, BillModel.MODIFICATION);
					}

					// /与原有值不同才重新计算
					if (lUFD_OldPrice == null
							|| lUFD_Price.compareTo(lUFD_OldPrice) != 0) {
						setCardBodyValueAt(lUFD_Price, lint_rowindex,
								priceItemKey,TAB1);
						getBillCardPanel().setBodyValueAt(
								lCtRetVO_Selected.getTaxration(),
								lint_rowindex, BillBodyItemKeys.NTAXRATE);

						// 关联上合同,且找到对应的合同价,则价格不能编辑
						getBillCardPanel().setCellEditable(lint_rowindex,
								BillBodyItemKeys.NORIGINALCURPRICE, false);
						getBillCardPanel().setCellEditable(lint_rowindex,
								BillBodyItemKeys.NORGTAXPRICE, false);

						// 重新计算数量关系
						// 表体数量关系连动
						int[] descriptions = new int[] {
								RelationsCal.DISCOUNT_TAX_TYPE_NAME,
								RelationsCal.DISCOUNT_TAX_TYPE_KEY,
								RelationsCal.NUM,
								RelationsCal.TAXPRICE_ORIGINAL,
								RelationsCal.PRICE_ORIGINAL,
								RelationsCal.NET_TAXPRICE_ORIGINAL,
								RelationsCal.NET_PRICE_ORIGINAL,
								RelationsCal.DISCOUNT_RATE,
								RelationsCal.TAXRATE,
								RelationsCal.MONEY_ORIGINAL,
								RelationsCal.TAX_ORIGINAL,
								RelationsCal.SUMMNY_ORIGINAL };
						Object oTemp = getBillCardPanel().getBodyValueAt(
								lint_rowindex, "idiscounttaxtype");// 扣税类别
						String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// 应税外加
						if (oTemp != null) {
							lStr_discounttaxtype = oTemp.toString();
						}

						String[] keys = new String[] { lStr_discounttaxtype,
								"idiscounttaxtype", "nordernum",
								"norgtaxprice", "noriginalcurprice",
								"norgnettaxprice", "noriginalnetprice",
								"ndiscountrate", "ntaxrate", "noriginalcurmny",
								"noriginaltaxmny", "noriginalsummny" };
						BillEditEvent l_BillEditEventTemp = new BillEditEvent(
								getBillCardPanel()
										.getBillModel(TAB1)
										.getItemByKey(
												ScUtils.getPriceFieldByPricePolicy(getPricePolicy()))
										.getComponent(),
								getBillCardPanel()
										.getBodyValueAt(
												lint_rowindex,
												ScUtils.getPriceFieldByPricePolicy(getPricePolicy())),
								ScUtils.getPriceFieldByPricePolicy(getPricePolicy()),
								lint_rowindex);
						RelationsCal.calculate(getBillCardPanel(),
								l_BillEditEventTemp, getBillCardPanel()
										.getBillModel(TAB1),
								new int[] { getPricePolicy() }, descriptions,
								keys, OrderItemVO.class.getName());
					}
				} else {
					rowsForPriceAgain.add(lint_rowindex);
				}
			}
		}

		int[] retIntArray = new int[rowsForPriceAgain.size()];
		for (int index = 0, len = rowsForPriceAgain.size(); index < len; index++) {
			retIntArray[index] = rowsForPriceAgain.get(index);
		}
		return retIntArray;
	}

	/**
	 * 关联合同和自动取价 参数:abol_SkipCT boolean 跳过合同的取价和关联运算,直接进入取供应商价格和默认参数价格.
	 */
	private void setRelateCntAndDefaultPrice(int[] aintary_rows,
			boolean abol_SkipCT) {
		try {
			aintary_rows = filterCtRelatedRows(aintary_rows);//???
			if ((aintary_rows == null) || (aintary_rows.length == 0)) {
				return;
			}

			// 如果不跳过合同运算,则首先进行合同相关运算
			int[] noCtPriceRows = null;
			if (!abol_SkipCT) {
				noCtPriceRows = relateContractInfo(aintary_rows);
			}

			if ((noCtPriceRows == null) || (noCtPriceRows.length <= 0)) {
				// 没有需要继续询价的行，返回
				return;
			}

			// 取进一步计算所需要的值
			// 表体数据
			String[] lStrary_mangid2 = new String[noCtPriceRows.length];
			String[] lStrary_currencytypeid2 = new String[noCtPriceRows.length];
			UFDouble[] lUFDary_BRate2 = new UFDouble[noCtPriceRows.length];

			for (int i = 0; i < noCtPriceRows.length; i++) {
				getBillCardPanel().setCellEditable(noCtPriceRows[i],
						"noriginalcurprice", true,TAB1);
				getBillCardPanel().setCellEditable(noCtPriceRows[i],
						"norgtaxprice", true,TAB1);
				getBillCardPanel().setCellEditable(noCtPriceRows[i],
						"ccurrencytype", true,TAB1);

				lStrary_mangid2[i] = SCPubVO
						.getString_TrimZeroLenAsNull((String) getBillCardPanel()
								.getBillModel(TAB1).getValueAt(noCtPriceRows[i], "cmangid"));
				lStrary_currencytypeid2[i] = SCPubVO
						.getString_TrimZeroLenAsNull((String) getBillCardPanel()
								.getBillModel(TAB1).getValueAt(noCtPriceRows[i],
										"ccurrencytypeid"));// 币种id
				lUFDary_BRate2[i] = SCPubVO
						.getUFDouble_ValueAsValue(getBillCardPanel()
								.getBillModel(TAB1).getValueAt(noCtPriceRows[i],
										"nexchangeotobrate"));// 折本汇率
			}

			// 表头-供应商mangid
			String lStr_vendormangid = (String) getBillCardPanel().getHeadItem(
					BillBodyItemKeys.CVENDORMANGID).getValueObject();

			// 表头-订单日期
			String lStr_orderdate = (String) getBillCardPanel().getHeadItem(
					BillHeaderItemKeys.DORDERDATE).getValueObject();

			// /表头-库存组织id
			String lStr_wareid = (String) getBillCardPanel().getHeadItem(
					BillHeaderItemKeys.CWAREID).getValueObject();

			// 存货 币种 折本汇率 折辅汇率
			// 取默认价格
			RetScVrmAndParaPriceVO l_voPara = new RetScVrmAndParaPriceVO(1);
			l_voPara.setPk_corp(getPk_corp());
			l_voPara.setStoOrgId(lStr_wareid);
			l_voPara.setVendMangId(lStr_vendormangid);
			l_voPara.setSaInvMangId(lStrary_mangid2);
			l_voPara.setSaCurrId(lStrary_currencytypeid2);
			l_voPara.setDaBRate(lUFDary_BRate2);
			l_voPara.setDOrderDate(lStr_orderdate == null ? null : new UFDate(
					lStr_orderdate));
			l_voPara.setClientLink(new ClientLink(nc.ui.pub.ClientEnvironment
					.getInstance()));

			// 价格返回
			RetScVrmAndParaPriceVO l_voRetPrice = OrderHelper
					.queryVrmAndParaPrices(l_voPara);

			for (int i = 0; i < noCtPriceRows.length; i++) {
				UFDouble lUFD_Price = l_voRetPrice.getPriceAt(i);
				if (lUFD_Price == null) {
					continue;
				}

				String lStr_ChangedKey = null;
				if (l_voRetPrice.isSetPriceNoTaxAt(i)) {// 如果为“参考成本”或"计划价"，则永远看为“无税优先”
					lStr_ChangedKey = "noriginalcurprice";
				}

				// 与原有值不同才重新计算
				UFDouble lUFD_OldPrice = SCPubVO
						.getUFDouble_ValueAsValue(getBillCardPanel()
								.getBillModel(TAB1).getValueAt(noCtPriceRows.length,
										lStr_ChangedKey));

				if (lUFD_OldPrice == null
						|| lUFD_Price.compareTo(lUFD_OldPrice) != 0) {
					getBillCardPanel().setBodyValueAt(lUFD_Price,
							noCtPriceRows.length, lStr_ChangedKey,TAB1);
					getBillCardPanel().setCellEditable(noCtPriceRows.length,
							"noriginalcurprice", true,TAB1);
					getBillCardPanel().setCellEditable(noCtPriceRows.length,
							"norgtaxprice", true,TAB1);
					getBillCardPanel().setCellEditable(noCtPriceRows.length,
							"ccurrencytype", true,TAB1);
					// 重新计算数量关系
					// 表体数量关系连动
					int[] descriptions = new int[] {
							RelationsCal.DISCOUNT_TAX_TYPE_NAME,
							RelationsCal.DISCOUNT_TAX_TYPE_KEY,
							RelationsCal.NUM, RelationsCal.TAXPRICE_ORIGINAL,
							RelationsCal.PRICE_ORIGINAL,
							RelationsCal.NET_TAXPRICE_ORIGINAL,
							RelationsCal.NET_PRICE_ORIGINAL,
							RelationsCal.DISCOUNT_RATE, RelationsCal.TAXRATE,
							RelationsCal.MONEY_ORIGINAL,
							RelationsCal.TAX_ORIGINAL,
							RelationsCal.SUMMNY_ORIGINAL };
					Object oTemp = getBillCardPanel().getBillModel(TAB1).getValueAt(
							noCtPriceRows.length, "idiscounttaxtype");
					String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// 应税外加
					if (oTemp != null) {
						lStr_discounttaxtype = oTemp.toString();
					}

					String[] keys = new String[] { lStr_discounttaxtype,
							"idiscounttaxtype", "nordernum", "norgtaxprice",
							"noriginalcurprice", "norgnettaxprice",
							"noriginalnetprice", "ndiscountrate", "ntaxrate",
							"noriginalcurmny", "noriginaltaxmny",
							"noriginalsummny" };
					BillEditEvent l_BillEditEventTemp = new BillEditEvent(
							getBillCardPanel().getBillModel(TAB1)
									.getItemByKey(lStr_ChangedKey)
									.getComponent(), getBillCardPanel()
									.getBodyValueAt(noCtPriceRows.length,
											lStr_ChangedKey), lStr_ChangedKey,
							noCtPriceRows.length);
					RelationsCal.calculate(getBillCardPanel(),
							l_BillEditEventTemp, getBillCardPanel()
									.getBillModel(TAB1),
							new int[] { getPricePolicy() }, descriptions, keys,
							OrderItemVO.class.getName());
				}
			}
		} catch (Exception e) {
			showErrorMessage("" + e.getMessage());
			SCMEnv.out(e);
		}
	}

	/**
	 * 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
	 */
	private void setNotEditableWhenRelateCnt(int[] aintary_rows) {
		List<String> contractIdList = new ArrayList<String>();
		for (int i = 0; i < aintary_rows.length; i++) {
			String ccontractrowid = (String) getCardBodyValueAt(
					aintary_rows[i], BillBodyItemKeys.CCONTRACTROWID,TAB1);
			contractIdList.add(ccontractrowid);
		}

		RetCtToPoQueryVO[] voCntInfos = PoPublicUIClass
				.getCntInfoArray(contractIdList
						.toArray(new String[contractIdList.size()]));

		for (int i = 0; i < aintary_rows.length; i++) {
			int lint_rowindex = aintary_rows[i];// 行序号
			String ccontractrcode = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CCONTRACTRCODE,TAB1);
			if (!StringUtil.isEmptyWithTrim(ccontractrcode)) {
				Object ccurrencytype = getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.CCURRENCYTYPE,TAB1);
				Object noriginalcurprice = getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.NORIGINALCURPRICE,TAB1);
				Object norgtaxprice = getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.NORGTAXPRICE,TAB1);
				Object cupsourcebilltype = getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.CUPSOURCEBILLTYPE,TAB1);
				Object noriginalnetprice = getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.NORIGINALNETPRICE,TAB1);
				Object norgnettaxprice = getCardBodyValueAt(lint_rowindex,
						BillBodyItemKeys.NORGNETTAXPRICE,TAB1);

				if ((voCntInfos[i] == null)
						|| (voCntInfos[i].getDOrgPrice() == null)) {
					continue;
				}

				if (ccurrencytype != null
						&& ccurrencytype.toString().trim().length() > 0) {
					getBillCardPanel().setCellEditable(lint_rowindex,
							BillBodyItemKeys.CCURRENCYTYPE, false);
				}
				if (noriginalcurprice != null
						&& noriginalcurprice.toString().trim().length() > 0) {
					getBillCardPanel().setCellEditable(lint_rowindex,
							BillBodyItemKeys.NORIGINALCURPRICE, false);
				}
				if (norgtaxprice != null
						&& norgtaxprice.toString().trim().length() > 0) {
					getBillCardPanel().setCellEditable(lint_rowindex,
							BillBodyItemKeys.NORGTAXPRICE, false);
				}
				if (cupsourcebilltype != null
						&& (cupsourcebilltype.toString().trim()
								.equals(BillTypeConst.CT_ORDINARY) || cupsourcebilltype
								.toString().trim()
								.equals(BillTypeConst.CT_ORDINARY))) {
					getBillCardPanel().setCellEditable(lint_rowindex,
							BillBodyItemKeys.CCONTRACTRCODE, false);
				}
				if (noriginalnetprice != null
						&& noriginalnetprice.toString().trim().length() > 0) {
					getBillCardPanel().setCellEditable(lint_rowindex,
							BillBodyItemKeys.NORIGINALNETPRICE, false);
				}
				if (norgnettaxprice != null
						&& norgnettaxprice.toString().trim().length() > 0) {
					getBillCardPanel().setCellEditable(lint_rowindex,
							BillBodyItemKeys.NORGNETTAXPRICE, false);
				}
			}
		}
	}

	/**
	 * 关联合同的行以下字段不可改：币种、原币无税单价、原币含税单价。
	 */
	private void setNotEditableWhenRelateCntAllRow() {// gc 不处理
		int li_rowcount = getBillCardPanel().getRowCount();
		if (li_rowcount > 0) {
			int[] lintary_all = new int[li_rowcount];
			for (int i = 0; i < li_rowcount; i++) {
				lintary_all[i] = i;
			}
			setNotEditableWhenRelateCnt(lintary_all);
		}
	}

	/**
	 * 自动取价
	 */
	private void setRelateCntAndDefaultPriceAllRow(boolean abol_SkipCT) {
		int li_rowcount = getBillCardPanel().getBillModel(TAB1).getRowCount();
		if (li_rowcount > 0) {
			int[] lintary_all = new int[li_rowcount];
			for (int i = 0; i < li_rowcount; i++) {
				lintary_all[i] = i;
			}
			setRelateCntAndDefaultPrice(lintary_all, abol_SkipCT);
		}
	}

	/**
	 * 作者：yye 功能：得到根据采购价格优先规格的单价FIELD 参数：int iPolicy 参见类RelationsCalVO 返回：String
	 * "noriginalcurprice"无税优先;"norgtaxprice"含税优先 例外：无 日期：(2003-7-1 12:42:34)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public static String getPriceFieldByPricePolicy(int iPolicy) {
		if (iPolicy == RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE) {
			// 含税优先
			return "norgtaxprice";
		} else if (iPolicy == RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE) {
			// 无税优先
			return "noriginalcurprice";
		}

		return null;
	}

	/**
	 * 作者：yye 功能：得到根据采购价格优先规格的单价 参数：int iPolicy 参见类RelationsCalVO 返回：String
	 * "noriginalcurprice"无税优先;"norgtaxprice"含税优先 例外：无 日期：(2003-7-1 12:42:34)
	 * 修改日期，修改人，修改原因，注释标志：
	 */
	public static UFDouble getPriceValueByPricePolicy(
			nc.vo.scm.ctpo.RetCtToPoQueryVO voCntInfo, int iPolicy) {

		// 参数正确性检查
		if (voCntInfo == null) {
			return null;
		}

		if (iPolicy == RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE) {
			// 含税优先
			return voCntInfo.getDOrgTaxPrice();
		} else if (iPolicy == RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE) {
			// 无税优先
			return voCntInfo.getDOrgPrice();
		}

		return null;
	}

	/**
	 * 设置币种汇率单元格的可编辑性
	 * 
	 * @param rowIndex
	 *            行号
	 */
	private void setCurrencyExchangeRateEnable(int rowIndex) {
		try {
			// 获取本币
			String ccurrency = (String) getBillCardPanel().getBodyValueAt(
					rowIndex, "ccurrencytypeid");
			if (StringUtil.isEmptyWithTrim(ccurrency)) {
				// 币种为空，设置汇率单元格不可编辑
				getBillCardPanel().setCellEditable(rowIndex,
						"nexchangeotobrate", false);
			} else if (getCurrParamQuery().isLocalCurrType(getPk_corp(),
					ccurrency)) {
				// 币种为本位币，设置汇率单元格不可编辑
				getBillCardPanel().setCellEditable(rowIndex,
						"nexchangeotobrate", false);
			} else {
				// 币种为非本位币，设置汇率单元格可编辑
				getBillCardPanel().setCellEditable(rowIndex,
						"nexchangeotobrate", true);
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}
	}

	/**
	 * 设置币种汇率
	 */
	private void setCurrencyExchangeRate(int rowIndex) {// gc 不处理
		try {
			// 获取本币
			String ccurrency = (String) getBillCardPanel().getBodyValueAt(
					rowIndex, "ccurrencytypeid");
			if (StringUtil.isEmpty(ccurrency)) {
				// 币种为空，返回
				return;
			}

			if (getCurrParamQuery().isLocalCurrType(getPk_corp(), ccurrency)) {
				getBillCardPanel().setBodyValueAt("1", rowIndex,
						"nexchangeotobrate");
			} else {
				// 本币
				String localCur = getCurrParamQuery().getLocalCurrPK(
						getPk_corp());
				// 取订单日期
				String strRateDate = (String) getCardHeadItemValue("dorderdate");
				// 计算汇率
				UFDouble localRate = getCurrencyRateUtil().getRate(ccurrency,
						localCur, strRateDate);
				getBillCardPanel().setBodyValueAt(localRate, rowIndex,
						"nexchangeotobrate");
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}
	}

	/**
	 * 获取币种参数查询工具, 用于查询与汇率相关的一些参数
	 * 
	 * @return 参数查询工具
	 */
	private CurrParamQuery getCurrParamQuery() {
		return CurrParamQuery.getInstance();
	}

	/**
	 * 此处插入方法说明。 功能描述:设置精度（数量、单价、金额） 输入参数: 返回值: 异常处理: 2003/02/13 李金巧修改
	 * 2003/03/15 增加含税单价，处理精度
	 */
	private void setNormalPrecision2() {// gc 不处理

		try {
			int moneyPrecision = 4;

			// 数量：
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nordernum")
					.setDecimalDigits(getNumPrecision()); // 订单数量
			getBillCardPanel().getBillModel(TAB2).getItemByKey("nnum")
			.setDecimalDigits(getNumPrecision()); // 订单数量//gc
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nbackarrvnum")
					.setDecimalDigits(getNumPrecision());// 退货数量
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nbackstorenum")
					.setDecimalDigits(getNumPrecision());// 退库数量
			// 辅数量
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nassistnum")
					.setDecimalDigits(getAssNumPrecision());
			// 换算率
			getBillCardPanel().getBillModel(TAB1).getItemByKey("measrate")
					.setDecimalDigits(getRatePrecision());
			// 单价:
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginalcurprice")
					.setDecimalDigits(getPricePrecision());
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginalnetprice")
					.setDecimalDigits(getPricePrecision());
			getBillCardPanel().getBillModel(TAB1).getItemByKey("norgtaxprice")
					.setDecimalDigits(getPricePrecision());
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("norgnettaxprice")
					.setDecimalDigits(getPricePrecision());
			// 金额：
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginalcurmny")
					.setDecimalDigits(moneyPrecision);
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginaltaxmny")
					.setDecimalDigits(moneyPrecision);
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginalsummny")
					.setDecimalDigits(moneyPrecision);
			// 本币金额
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nmoney")
					.setDecimalDigits(getLocalMnyPrecision());
			getBillCardPanel().getBillModel(TAB1).getItemByKey("ntaxmny")
					.setDecimalDigits(getLocalMnyPrecision());
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nsummny")
					.setDecimalDigits(getLocalMnyPrecision());
		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * 维护操作按钮状态：修改、作废 创建日期：(2001-8-30 12:50:47)
	 * 
	 * @param state
	 *            int
	 */
	public void setOperateState(int ibillstate) {

		if (comeVOs != null && comeVOs.size() > 0 && ibillstate == 10) {
			// modify by hanbin 2009-11-18 原因:添加判断，如果当前是处于编辑状态，控制按钮状态
			if (this.getBillCardPanel().isVisible()
					&& this.getBillCardPanel().isEnabled()) {
				boAudit.setEnabled(false);
				boEdit.setEnabled(false);
				boCancelOut.setEnabled(false);
				boCancel.setEnabled(!boEdit.isEnabled());
				boSave.setEnabled(!boEdit.isEnabled());
				boLineOperator.setEnabled(!boEdit.isEnabled());

				// 单据转入状态
				boAdd.setEnabled(false);
				boDel.setEnabled(false);
				boQuery.setEnabled(false);
				boBusitype.setEnabled(false);

				boAddLine.setEnabled(true);
				boDelLine.setEnabled(true);

				// modify by yye
				boCopyLine.setEnabled(true);
				boInsertLine.setEnabled(true);
				boPasteLine.setEnabled(true);
				//

				boPre.setEnabled(false);
				boNext.setEnabled(false);
				boLast.setEnabled(false);
				boFirst.setEnabled(false);

				boReturn.setEnabled(false);
			} else {
				boAudit.setEnabled(false);
				boEdit.setEnabled(true);
				boCancelOut.setEnabled(true);
				boCancel.setEnabled(false);
				boLineOperator.setEnabled(false);
				boSave.setEnabled(false);
			}
			updateButtons();
			return;
		}

		if (getModel().size() == 0) {
			boEdit.setEnabled(false);
			boDel.setEnabled(false);
			setGroupButtonsState(UFBoolean.FALSE);
			return;
		}

		if (ibillstate == 0 || ibillstate == 4) { // 自由、审批未通过
			boEdit.setEnabled(true);
			boDel.setEnabled(true);
			boAction.setEnabled(true);
			boAudit.setEnabled(true);
			boUnaudit.setEnabled(false);
		} else if (ibillstate == 2) { // 审批中
			String auditpsn = ((OrderHeaderVO) getSelectedVO().getParentVO())
					.getCauditpsn();
			if (auditpsn == null || auditpsn.length() == 0)
				boEdit.setEnabled(true);
			else
				boEdit.setEnabled(false);
			boDel.setEnabled(false);
			boAction.setEnabled(true);
			boAudit.setEnabled(true);
			boUnaudit.setEnabled(true);
		} else { // 已审批
			boEdit.setEnabled(false);
			boDel.setEnabled(false);
			boAction.setEnabled(true);
			boAudit.setEnabled(false);
			boUnaudit.setEnabled(true);
		}
		boReturn.setEnabled(true);

		setGroupButtonsState(UFBoolean.TRUE);
		updateButtons();
	}

	/**
	 * 功能：设置翻页按钮的状态，包括：首页、上页、下页、末页
	 * 
	 * 创建日期：(2001-3-17 9:00:09)
	 */
	private void setPgUpDownButtonsState(int curPageIndex) {

		// setButtonsState();
		if (curPageIndex < 0) {
			boFirst.setEnabled(false);
			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			updateButtons();
			return;
		}
		if (getModel().size() - 1 == 0) {
			boFirst.setEnabled(false);
			boPre.setEnabled(false);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			updateButtons();
			return;
		}
		if (curPageIndex == 0) {
			if (getModel().size() == 0) {
				boFirst.setEnabled(false);
				boPre.setEnabled(false);
				boNext.setEnabled(false);
				boLast.setEnabled(false);
			} else {
				boFirst.setEnabled(false);
				boPre.setEnabled(false);
				boNext.setEnabled(true);
				boLast.setEnabled(true);
			}
			updateButtons();
			return;
		}
		if (curPageIndex == getModel().size() - 1) {
			boFirst.setEnabled(true);
			boPre.setEnabled(true);
			boNext.setEnabled(false);
			boLast.setEnabled(false);
			updateButtons();
			return;
		}

		boFirst.setEnabled(true);
		boPre.setEnabled(true);
		boNext.setEnabled(true);
		boLast.setEnabled(true);

		updateButtons();
	}

	/**
	 * 表体到货地址：可为空。当表头的收货方不为空时，参照表头收货方的到货地址。 如表头的收货方为空，则默认为收货仓库的地址，可编辑。
	 * 创建日期：(2001-8-1 12:53:21)
	 */
	private void setReceiveAddress(int rowIndex, String key) {// gc 不处理

		//
		Object sReceiver = getBillCardPanel().getHeadItem("creciever")
				.getValue();

		if (key.equals("creciever")) {

			// add by hanbin 2009-10-28
			// 原因：getBillCardPanel().getBillTable(tab).getColumn("到货地址")报异常IllegalArgumentException
			// 判断“到货地址”是否显示，如果没有显示，直接返回
			if (!getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("creceiveaddress").isShow())
				return;

			if (sReceiver != null && !sReceiver.toString().trim().equals("")) {
				// 基础ID
				String cvendorbaseid = null;
				String defaddr = null;
				try {
					cvendorbaseid = PublicHelper.getCvendorbaseid(sReceiver
							.toString());
					defaddr = PublicHelper.getVdefaddr(cvendorbaseid);

				} catch (Exception e) {
				}
				UIRefPane adressRef = new UIRefPane();
				adressRef.setRefNodeName("客商发货地址");/* -=notranslate=- */
				adressRef.setReturnCode(true);
				adressRef.setWhereString(" where pk_cubasdoc='" + cvendorbaseid
						+ "' ");

				getBillCardPanel()
						.getBillTable()
						.getColumn(
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"common", "UC000-0000642")/* @res "到货地址" */)
						.setCellEditor(new BillCellEditor(adressRef));

				// 清空现有表体到货地址
				for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
					getBillCardPanel().setBodyValueAt(defaddr, i,
							"creceiveaddress");
				}

			} else {
				getBillCardPanel()
						.getBillTable()
						.getColumn(
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"common", "UC000-0000642")/* @res "到货地址" */)
						.setCellEditor(new BillCellEditor(new UITextField()));
				// 根据仓库填写表体行默认到货地址
				String formula = "creceiveaddress->getColValue(bd_stordoc,storaddr,pk_stordoc,cwarehouseid)";
				for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
					Object address = getBillCardPanel().getBodyValueAt(
							rowIndex, "creceiveaddress");
					if (address == null || address.toString().trim().equals("")) {
						getBillCardPanel().getBillModel(TAB1).execFormula(i,
								new String[] { formula });
					}
				}
			}
			String tab = getSelectTab();
			getBillCardPanel().getBillTable(tab).editingStopped(null);
			getBillCardPanel().updateUI();
			return;

		}
		//
		if (key.equals("cwarehousename")
				&& (sReceiver == null || sReceiver.toString().trim().equals(""))) {
			Object address = getBillCardPanel().getBodyValueAt(rowIndex,
					"creceiveaddress");
			if (address == null || address.toString().trim().equals("")) {
				String formula = "creceiveaddress->getColValue(bd_stordoc,storaddr,pk_stordoc,cwarehouseid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(rowIndex,
						new String[] { formula });
			}
		}
	}

	private void setRelated_Taxrate(int iBeginRow, int iEndRow) {
		List<String> baseIdList = new ArrayList<String>();
		List<String> mangIdList = new ArrayList<String>();
		Vector<Integer> vecRow = new Vector<Integer>();
		for (int i = iBeginRow; i <= iEndRow; i++) {
			Object obj = getBillCardPanel().getBodyValueAt(i, "ntaxrate");
			if (obj == null || obj.equals(UFDouble.ZERO_DBL)) {
				String sBaseId = nc.vo.scm.pu.PuPubVO
						.getString_TrimZeroLenAsNull((String) getBillCardPanel()
								.getBodyValueAt(i, "cbaseid"));
				String sMangId = nc.vo.scm.pu.PuPubVO
						.getString_TrimZeroLenAsNull((String) getBillCardPanel()
								.getBodyValueAt(i, "cmangid"));
				if (sBaseId != null) {
					baseIdList.add(sBaseId);
					vecRow.add(new Integer(i));
				}
				if (sMangId != null) {
					mangIdList.add(sMangId);
				}
			}
		}

		if (baseIdList.size() == 0) {
			return;
		}

		// 批量加载税率
		InvTool.loadBatchTaxrate(baseIdList.toArray(new String[baseIdList
				.size()]));
		InvTool.loadBatchTaxrateForMangIDs(mangIdList
				.toArray(new String[mangIdList.size()]));

		// 重置
		for (Integer rowIndex : vecRow) {
			String sBaseId = (String) getBillCardPanel().getBodyValueAt(
					rowIndex, "cbaseid");
			String sMangId = (String) getBillCardPanel().getBodyValueAt(
					rowIndex, "cmangid");

			UFDouble dCurTaxRate = InvTool.getInvTaxRateFromMangID(sMangId);
			if (dCurTaxRate == null)
				dCurTaxRate = InvTool.getInvTaxRate(sBaseId);
			if (dCurTaxRate == null)
				dCurTaxRate = new UFDouble(0);
			getBillCardPanel()
					.setBodyValueAt(dCurTaxRate, rowIndex, "ntaxrate");
		}
	}

	/**
	 * 此处插入方法说明。 功能描述:选择不同业务类型时，打勾 输入参数: 返回值: 异常处理: 日期:2003/05/06
	 */
	private void showSelBizType(ButtonObject bo) {
		ButtonObject[] boaAll = boBusitype.getChildButtonGroup();
		if (boaAll != null) {
			for (int i = 0; i < boaAll.length; i++)
				if (bo.equals(boaAll[i])) {

					boaAll[i].setSelected(true);
					break;
				}
		}

	}

	/**
	 * 功能描述:退出系统
	 */
	public boolean onClosing() {
		// 正在编辑单据时退出提示
		if (m_billState == ScConstants.STATE_ADD
				|| m_billState == ScConstants.STATE_MODIFY
				|| (m_billState == ScConstants.STATE_OTHER && getBillCardPanel()
						.isVisible())) {
			int nReturn = MessageDialog.showYesNoCancelDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UCH001")/* @res "是否保存已修改的数据？" */);
			// 保存成功后才退出
			if (nReturn == MessageDialog.ID_YES) {
				return onSave();
			}
			// 退出
			else if (nReturn == MessageDialog.ID_NO) {
				return true;
			}
			// 取消关闭
			else {
				return false;
			}
		}
		return true;
	}

	/**
	 * 二次开发功能扩展按钮，要求二次开发子类给出具体实现
	 * 
	 * @see nc.ui.scm.pub.bill.IBillExtendFun#getExtendBtns()
	 * 
	 */
	public ButtonObject[] getExtendBtns() {
		return null;
	}

	/**
	 * 点击二次开发按钮后的响应处理，要求二次开发子类给出具体实现
	 * 
	 * @see nc.ui.scm.pub.bill.IBillExtendFun#onExtendBtnsClick(nc.ui.pub.ButtonObject)
	 * 
	 */
	public void onExtendBtnsClick(ButtonObject bo) {

	}

	/**
	 * 二次开发状态与原有界面状态处理邦定，要求二次开发子类给出具体实现
	 * 
	 * @see nc.ui.scm.pub.bill.IBillExtendFun#setExtendBtnsStat(int)
	 * 
	 *      状态数值对照表：
	 * 
	 *      0：初始化 1：浏览卡片 2：修改 3：浏览列表 4：转入列表
	 */
	public void setExtendBtnsStat(int iState) {

	}

	/**
	 * 界面关联接口方法实现 -- 维护
	 */
	public void doMaintainAction(ILinkMaintainData maintaindata) {
		SCMEnv.out("进入委外订单接口...");

		if (maintaindata == null)
			return;

		String billID = maintaindata.getBillID();

		init();
		// 没有符合条件的订单
		try {
			getModel().setCurrentIndex(0);
			loadData(billID);
			setPrecision();
		} catch (Exception e) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000041")/* @res "加载数据出错" */);
			// 设置按钮状态
			boAudit.setEnabled(false);
			btnBillCombin.setEnabled(false);
			boQueryForAudit.setEnabled(false);
			boDocument.setEnabled(false);
			updateButtons();

			return;
		}

		getBillCardPanel().setEnabled(false);
		// 设置按钮组
		setButtons(m_btnTree.getButtonArray());

		// 设置按钮状态
		setButtonsState();
		// 维护界面状态（作废、修改）
		int ibillstate = ((OrderHeaderVO) m_curOrderVO.getParentVO())
				.getIbillstatus().intValue();
		if (((OrderHeaderVO) m_curOrderVO.getParentVO()).getPrimaryKey()
				.equals(billID))
			setOperateState(ibillstate);
		updateButtons();

	}

	/**
	 * 界面关联接口方法实现 -- 新增
	 */
	public void doAddAction(ILinkAddData adddata) {
		if (adddata.getUserObject() == null)
			return;

		SCMessageVO message = (SCMessageVO) adddata.getUserObject();
		/* 由价格审批单生成时调用的界面 */
		processAfterChange(ScmConst.PO_PriceAudit,
				new nc.vo.pub.AggregatedValueObject[] { message.getAskvo() });
	}

	/**
	 * 界面关联接口方法实现 -- 审批
	 */
	public void doApproveAction(ILinkApproveData approvedata) {
		if (approvedata == null
				|| StringUtil.isEmptyWithTrim(approvedata.getBillID()))
			return;
		PFOpenOperation(approvedata.getPkOrg(), approvedata.getBillID());

	}

	/**
	 * 界面关联接口方法实现 -- 逐级联查
	 */
	public void doQueryAction(ILinkQueryData querydata) {
		if (querydata == null
				|| StringUtil.isEmptyWithTrim(querydata.getBillID()))
			return;
		PFOpenOperation(querydata.getPkOrg(), querydata.getBillID());
	}

	private void PFOpenOperation(String pk_corp, String billid) {
		init();

		String billID = billid;

		OrderVO vo = null;
		// 没有符合条件的订单
		try {
			getModel().setCurrentIndex(0);

			// 先按照单据PK查询单据所属的公司corpvalue
			vo = OrderHelper.findByPrimaryKey(billID);
			if (vo == null) {
				MessageDialog.showHintDlg(
						this,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000270")/* "提示" */,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000397")/* "没有符合条件的数据" */);
				return;
			}
			String strPkCorp = vo.getPk_corp();

			// v5.1数据权限：加载查询模版(注意去掉模板默认值)；查询模板中没有公司时，要设置虚拟公司
			SCMQueryConditionDlg queryDlg = new SCMQueryConditionDlg(this);
			if (queryDlg.getAllTempletDatas() == null
					|| queryDlg.getAllTempletDatas().length <= 0) {
				if (pk_corp == null) {
					// 指定公司为空，则取当前登陆公司
					queryDlg.setTempletID(getPk_corp(), getModuleCode(),
							getOperatorID(), null);
				} else {
					queryDlg.setTempletID(pk_corp, getModuleCode(),
							getOperatorID(), null);
				}
			}

			ArrayList<String> alcorp = new ArrayList<String>();
			alcorp.add(ClientEnvironment.getInstance().getCorporation()
					.getPrimaryKey());
			queryDlg.initCorpRef(IDataPowerForSC.CORPKEYFORSC,
					ClientEnvironment.getInstance().getCorporation()
							.getPrimaryKey(), alcorp);
			queryDlg.setCorpRefs(IDataPowerForSC.CORPKEYFORSC,
					IDataPowerForSC.REFKEYSFORSC);
			// 调用公共方法获取该公司中控制权限的档案条件VO数组
			ConditionVO[] voaCond = queryDlg.getDataPowerConVOs(strPkCorp,
					IDataPowerForSC.REFKEYSFORSC);

			// 组织第二次查询单据，按照权限和单据PK过滤
			String strDataPowerSql = new ConditionVO().getWhereSQL(voaCond);
			String whereSQL = "sc_order.corderid = '" + billID + "' ";
			if (strDataPowerSql != null && strDataPowerSql.trim().length() > 0) {
				if (whereSQL != null && whereSQL.trim().length() > 0) {
					whereSQL += " and " + strDataPowerSql + " ";
				} else {
					whereSQL = strDataPowerSql + " ";
				}
			}

			OrderHeaderVO[] orderHeaderVO = OrderHelper.queryAllHead("",
					whereSQL,
					new ClientLink(nc.ui.pub.ClientEnvironment.getInstance()),
					false);
			if (orderHeaderVO == null || orderHeaderVO.length <= 0
					|| orderHeaderVO[0] == null) {
				MessageDialog.showHintDlg(
						this,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000270")/* "提示" */,
						NCLangRes.getInstance().getStrByID("common",
								"SCMCOMMON000000161")/* "没有察看单据的权限" */);
				return;
			}
			billID = ((OrderHeaderVO) orderHeaderVO[0]).getCorderid();// 只查一条单据
			getModel().add((OrderHeaderVO) orderHeaderVO[0]);

			loadData(null);
			setPrecision();
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showHintDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "提示" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
							"UPP401201-000034")/* @res "查询委外订单出错" */);
		}

		getBillCardPanel().setEnabled(false);
	}

	/**
	 * VO缓存管理实现IbillRelaSortListener接口 -- VO缓存和界面排序缓存的同步
	 */
	public List getRelaSortObject() {
		return getModel().getHeaderList();
	}

	/**
	 * <p>
	 * 委外订单参照跨公司合同业务下，源公司和目标公司间客商、存货、项目ID的转换
	 * 
	 * @author lxd
	 * 
	 * @date 2006-10-18
	 */
	public void chgDataForOrderCorp(String sBiztypeId, OrderVO[] vos,
			String sUpBillType) throws BusinessException {

		if (vos == null || vos.length == 0 || vos[0] == null) {
			return;
		}
		if (sBiztypeId == null || sBiztypeId.trim().length() == 0) {
			SCMEnv.out("订单档案转换的前提是业务类型非空!");/* -=notranslate=- */
			return;
		}

		// 订单是否来源于合同
		boolean bFromCt = sUpBillType != null
				&& (sUpBillType
						.equals(nc.vo.scm.pu.BillTypeConst.CT_BEFOREDATE) || sUpBillType
						.equals(nc.vo.scm.pu.BillTypeConst.CT_ORDINARY));

		// 下面两种情况需要处理：1、业务类型是集团;2、如果业务类型为公司并且订单来源是合同。否则返回
		if (!CentrPurchaseUtil.isGroupBusiType(sBiztypeId) && !bFromCt) {
			SCMEnv.out("订单档案转换的前提是业务类型为集团业务类型!");/* -=notranslate=- */
			return;
		}

		// 获取要转换的档案VO
		ArrayList listVendorId = new ArrayList();
		ArrayList listProjectId = new ArrayList();
		ArrayList listInventoryId = new ArrayList();
		ChgDocPkVO chgDocVo = null;
		String strCntCorpId = null;// 来源公司ID
		String strPurCorpId = null;// 目标公司ID
		int iLen = vos.length;
		int jLen = 0;
		OrderItemVO[] items = null;
		OrderHeaderVO head = null;
		for (int i = 0; i < iLen; i++) {
			if (vos[i].getChildrenVO() == null
					|| vos[i].getChildrenVO().length == 0
					|| vos[i].getChildrenVO()[0] == null) {
				continue;
			}
			head = (OrderHeaderVO) vos[i].getParentVO();
			;

			// strPurCorpId = head.getPk_corp();
			strPurCorpId = getCorpPrimaryKey();// 设置目标公司ID 为当前登录公司

			items = (OrderItemVO[]) vos[i].getChildrenVO();
			// 供应商
			chgDocVo = new ChgDocPkVO();
			chgDocVo.setSrcCorpId(items[0].getPk_corp());// 表体上层来源公司ID是默认分单
			chgDocVo.setDstCorpId(strPurCorpId);
			chgDocVo.setSrcBasId(head.getCvendorid());// 供应商基本档案ID
			listVendorId.add(chgDocVo);

			jLen = items.length;
			for (int j = 0; j < jLen; j++) {
				if (items[j] == null) {
					continue;
				}
				strCntCorpId = items[j].getPk_corp();

				// 项目
				if (PuPubVO.getString_TrimZeroLenAsNull(items[j]
						.getCprojectid()) != null) {
					chgDocVo = new ChgDocPkVO();
					chgDocVo.setSrcCorpId(strCntCorpId);
					chgDocVo.setDstCorpId(strPurCorpId);
					chgDocVo.setSrcManId(items[j].getCprojectid());// 项目管理ID
					listProjectId.add(chgDocVo);
				}
				// 存货
				if (PuPubVO.getString_TrimZeroLenAsNull(items[j].getCbaseid()) != null) {
					chgDocVo = new ChgDocPkVO();
					chgDocVo.setSrcCorpId(strCntCorpId);
					chgDocVo.setDstCorpId(strPurCorpId);
					chgDocVo.setSrcBasId(items[j].getCbaseid());// 存货基本ID
					listInventoryId.add(chgDocVo);
				}
			}
		}
		// 转换
		int iSize = listVendorId.size();
		if (iSize == 0) {
			return;
		}
		// 客商转换
		ChgDocPkVO[] vendorVos = null;
		vendorVos = (ChgDocPkVO[]) listVendorId.toArray(new ChgDocPkVO[iLen]);
		vendorVos = ChgDataUtil.chgPkCuByCorpBase(vendorVos);

		// 项目转换
		ChgDocPkVO[] projectVos = null;
		projectVos = (ChgDocPkVO[]) listProjectId
				.toArray(new ChgDocPkVO[listProjectId.size()]);
		projectVos = ChgDataUtil.chgPkjobByCorp(projectVos);

		// 存货转换
		ChgDocPkVO[] inventoryVos = null;
		inventoryVos = (ChgDocPkVO[]) listInventoryId
				.toArray(new ChgDocPkVO[listInventoryId.size()]);
		inventoryVos = ChgDataUtil.chgPkInvByCorpBase(inventoryVos);

		// 设置转换后的数据到vos
		int iPosProj = 0;
		int iPosBody = 0;
		for (int i = 0; i < iLen; i++) {
			if (vos[i].getChildrenVO() == null
					|| vos[i].getChildrenVO().length == 0
					|| vos[i].getChildrenVO()[0] == null) {
				continue;
			}
			// 供应商设置
			head.setCvendormangid(vendorVos[i].getDstManId());
			// 发票方
			// head.setCgiveinvoicevendor(vendorVos[i].getDstManId());
			//
			items = (OrderItemVO[]) vos[i].getChildrenVO();
			jLen = items.length;
			for (int j = 0; j < jLen; j++) {
				if (items[j] == null) {
					continue;
				}
				// 项目设置
				if (PuPubVO.getString_TrimZeroLenAsNull(items[j]
						.getCprojectid()) != null) {
					items[j].setCprojectid(projectVos[iPosProj].getDstManId());
					iPosProj++;
				}
				// 存货设置
				if (nc.vo.scm.pu.PuPubVO.getString_TrimZeroLenAsNull(items[j]
						.getCbaseid()) != null) {
					if (inventoryVos[iPosBody].getDstCorpId() != null
							&& !inventoryVos[iPosBody].getDstCorpId()
									.equalsIgnoreCase(
											inventoryVos[iPosBody]
													.getSrcCorpId())) {
						items[j].setCmangid(inventoryVos[iPosBody]
								.getDstManId());
					}
					iPosBody++;
				}
			}
		}
	}

	/**
	 * 此方法已存在<code>nc.ui.scm.pub.panelBillPanelTool</code>中
	 * <p>
	 * <strong>功能：</strong>仓库与库存组织的关系： 库存组织变化后，仓库参照变化；
	 * 库存组织清空后，仓库不清空；库存组织改变，仓库清空，并相关仓库字段清空
	 * <p>
	 * <strong>修改描述：</strong>修改委外订单保存时检查，若库存组织发生变化，则检查表体仓库，若表体仓库变化，则设置变化行状态为
	 * "BillModel.MODIFICATION"
	 * (注：如果由表头变化而引起表体变化的情况,getBillValueChangeVO()方法不能得到变化的表体)
	 * 
	 * @author lxd
	 * @date 2006-11-20
	 * @param BillCardPanel
	 *            pnlBill 当前单据模板 不可为空
	 * @param BillEditEvent
	 *            e 捕捉到的BillEditEvent事件，不可为空
	 * @param String
	 *            pk_corp 公司ID，不为空
	 * @param String
	 *            sCalBodyId 库存组织ID，可为空
	 * @param String
	 *            sKeyBodyWareRef 表体仓库的模板KEY，不可为空 String[]
	 * @param String
	 *            [] saKeyBodyWarehouse 其他与仓库相关的表体KEY，长度由用户决定，可为空 [i]
	 * @return void
	 * @throws 无
	 * @since V5.0
	 * @see
	 */
	private void afterEditStoreOrgToWarehouse(BillCardPanel pnlBill,
			BillEditEvent e, String pk_corp, String sCalBodyId,
			String sKeyBodyWareRef, String[] saKeyBodyWarehouse) {// gc 不处理

		// 参数正确性检查
		if (// 单据模板、公司、库存组织ID、仓库参照、其他与仓库相关的表体KEY，长度由用户决定，可为空 [i]
		pnlBill == null || e == null || pk_corp == null
				|| pk_corp.trim().length() == 0 || sKeyBodyWareRef == null
				|| sKeyBodyWareRef.trim().length() == 0) {
			SCMEnv.out("方法nc.ui.sc.order.OrderUI.afterEditStoreOrgToWarehouse(BillListPanel, BillEditEvent, String, String, String, String[])传入参数错误！");
			return;
		}

		// 库存组织ID
		if (sCalBodyId != null && sCalBodyId.trim().length() != 0) {
			// 清空所有仓库
			int iRowCount = pnlBill.getRowCount();
			for (int i = 0; i < iRowCount; i++) {
				// 仓库参照所在KEY清空
				pnlBill.setBodyValueAt(null, i, sKeyBodyWareRef);
				// 其他仓库字段清空
				if (saKeyBodyWarehouse != null) {
					int iWareColLen = saKeyBodyWarehouse.length;
					for (int j = 0; j < iWareColLen; j++) {
						if (saKeyBodyWarehouse[j] != null
								&& saKeyBodyWarehouse[j].trim().length() > 0) {
							pnlBill.setBodyValueAt(null, i,
									saKeyBodyWarehouse[j]);
							if (m_billState == ScConstants.STATE_MODIFY) {// 单据只有修改状态下才修改行属性
								pnlBill.getBillModel(TAB1).setRowState(i,
										BillModel.MODIFICATION);
							}
						}
					}
				}
			}
		}

		// 参照条件
		BillPanelTool.restrictWarehouseRefByStoreOrg(pnlBill, pk_corp,
				sCalBodyId, sKeyBodyWareRef);
	}

	/**
	 * 方法功能描述：设置业务员默认值 根据操作员带出业务员。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-3-29 上午10:42:05
	 */
	private void setDefaultValueByUser() {
		// 设置业务员默认值 根据操作员带出业务员
		UIRefPane cemployeeid = (UIRefPane) getBillCardPanel().getHeadItem(
				"cemployeeid").getComponent();
		if (cemployeeid != null && cemployeeid.getRefPK() == null) {
			IUserManageQuery qrySrv = (IUserManageQuery) NCLocator
					.getInstance().lookup(IUserManageQuery.class.getName());
			PsndocVO voPsnDoc = null;
			try {
				voPsnDoc = qrySrv.getPsndocByUserid(nc.ui.pub.ClientEnvironment
						.getInstance().getCorporation().getPrimaryKey(),
						getOperatorID());
			} catch (BusinessException be) {
				SCMEnv.out(be);
			}
			if (voPsnDoc != null) {
				cemployeeid.setPK(voPsnDoc.getPk_psndoc());

				UIRefPane cdeptid = (UIRefPane) getBillCardPanel().getHeadItem(
						"cdeptid").getComponent();// 带出部门
				cdeptid.setPK(voPsnDoc.getPk_deptdoc());
			}
		}
	}

	/**
	 * 
	 * 方法功能描述：重新排序各行的行号。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author zhaoyha
	 * @time 2008-8-15 下午06:44:50
	 */
	private void onReOrderRowNo() {
		getBillCardPanel().stopEditing();
		BillRowNo.addNewRowNo(getBillCardPanel(), ScmConst.SC_Order,
				ScConstants.SC_ORDER_BODY_ROWNUM);
	}

	/**
	 * 
	 * 方法功能描述：启动行的卡片编辑。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * <p>
	 * 
	 * @author zhaoyha
	 * @time 2008-8-14 上午10:21:04
	 */
	protected void onBoCardEdit() {
		// 处理“备注字段”，先把类型设为字符串
		boolean isRef = false;
		int dataType = -1;
		if (getBillCardPanel().getBillModel(TAB1).getItemByKey("vmemo")
				.getComponent() instanceof UIRefPane) {
			isRef = true;
			dataType = getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("vmemo").getDataType();
			getBillCardPanel().getBillModel(TAB1).getItemByKey("vmemo")
					.setDataType(BillItem.STRING);
		}
		// 卡片编辑
		getBillCardPanel().startRowCardEdit();
		if (isRef)
			getBillCardPanel().getBillModel(TAB1).getItemByKey("vmemo")
					.setDataType(dataType);// 卡片编辑完后，恢复类型
	}

	/**
	 * 
	 * 父类方法重写
	 * 
	 * @see nc.ui.pub.bill.BillActionListener#onEditAction(int)
	 */
	public boolean onEditAction(int action) {
		if (action == BillScrollPane.ADDLINE) {
			// 设置行增加标志,以便行增加后可被BillEditListener的
			// bodyRowChange()捕获
			// 当前这个标志只用于bodyRowChange()方法,其它方法请不要使用
			isAddedLine = true;

		} else if (action == BillScrollPane.DELLINE) {
			// 如果只有一行则不允许删除V55
			if (getBillCardPanel().getRowCount() == 1) {
				showErrorMessage(NCLangRes.getInstance().getStrByID(
						"scmcommon", "UPPSCMCommon-000489")
				/* 不能删除表体中的全部行！ */
				);
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * 方法功能描述：返回重排行号菜单项。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @return 如果未初始化菜单项，则返回null
	 *         <p>
	 * @author zhaoyha
	 * @time 2008-8-28 下午06:09:07
	 */
	public UIMenuItem getReOrderRowNoMenuItem() {
		return miReOrderRowNo;
	}

	/**
	 * 
	 * 方法功能描述：返回卡片编辑菜单项。
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * 使用示例
	 * <p>
	 * <b>参数说明</b>
	 * 
	 * @return 如果未初始化菜单项，则返回null
	 *         <p>
	 * @author zhaoyha
	 * @time 2008-8-28 下午06:12:57
	 */
	public UIMenuItem getCardEditMenuItem() {
		return miCardEdit;
	}

	/**
	 * 卡片界面编辑时设置表体第 rowIndex 行的折本汇率精度。 rowIndex 从零开始计数
	 * 
	 * @param rowIndex
	 *            行标
	 */
	// private void setExchangeRatePrecision(int rowIndex) {
	// // 第 rowIndex 行的币种ID
	// String sCurrId = (String)getBillCardPanel()
	// .getBodyValueAt(rowIndex, "ccurrencytypeid");
	//
	// // 设置显示精度
	// int[] iaExchRateDigit = m_cardPoPubSetUI2.getBothExchRateDigit(
	// getPk_corp(), sCurrId);
	// if ( (iaExchRateDigit != null) && (iaExchRateDigit.length > 0) ) {
	// getBillCardPanel().getBillModel(TAB1).getItemByKey("nexchangeotobrate")
	// .setDecimalDigits( iaExchRateDigit[0] );
	// }
	// }
	/**
	 * 获取汇率精度设置工具
	 */
	public POPubSetUI2 getPoPubSetUi2() {
		if (m_cardPoPubSetUI2 == null) {
			m_cardPoPubSetUI2 = new POPubSetUI2();
		}
		return m_cardPoPubSetUI2;
	}

	/**
	 * 根据ID得到显示的状态
	 * 
	 * @param
	 * @return
	 * @exception 异常描述
	 * @see 需要参见的其它内容
	 * @since 从类的那一个版本，此方法被添加进来。（可选）
	 * 
	 */
	private void setMaxMnyDigit(int iMaxDigit) {
		// 金额相关
		getBillCardPanel().getBillModel(TAB1).getItemByKey("nexchangeotobrate")
				.setDecimalDigits(iMaxDigit);
		getBillCardPanel().getBillModel(TAB1).getItemByKey("noriginalcurmny")
				.setDecimalDigits(iMaxDigit);
		getBillCardPanel().getBillModel(TAB1).getItemByKey("noriginaltaxmny")
				.setDecimalDigits(iMaxDigit);
		getBillCardPanel().getBillModel(TAB1).getItemByKey("noriginalsummny")
				.setDecimalDigits(iMaxDigit);
	}

	/**
	 * 作者：王印芬 功能：设置表体折本及折辅汇率的值并设置其可编辑性 参数： int iRow boolean bResetExchValue
	 * 是否需要重新设置表体行的汇率值 返回：无 例外：无 日期：(2002-5-13 11:39:21) 修改日期，修改人，修改原因，注释标志：
	 */
	private void setExchangeRateBody(int iRow, boolean bResetExchValue) {// gc
																			// 不处理
		String dOrderDate = (String) getBillCardPanel().getHeadItem(
				"dorderdate").getValueObject();
		String sCurrId = (String) getBillCardPanel().getBodyValueAt(iRow,
				"ccurrencytypeid");
		// 值
		if (bResetExchValue && dOrderDate != null
				&& dOrderDate.trim().length() > 0) {
			String strCurrDate = dOrderDate;
			if (strCurrDate == null || strCurrDate.trim().length() == 0) {
				strCurrDate = PoPublicUIClass.getLoginDate() + "";
			}
			UFDouble[] daRate = m_cardPoPubSetUI2.getBothExchRateValue(
					getPk_corp(), sCurrId, new UFDate(dOrderDate));
			getBillCardPanel().setBodyValueAt(daRate[0], iRow,
					"nexchangeotobrate");
		}

		// 可编辑性
		boolean[] baEditable = m_cardPoPubSetUI2.getBothExchRateEditable(
				getPk_corp(), sCurrId);
		boolean bEditUserDef0 = getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("nexchangeotobrate").isEdit();
		getBillCardPanel().getBillModel(TAB1).setCellEditable(iRow,
				"nexchangeotobrate", baEditable[0] && bEditUserDef0);

		// 设置修改标志
		getBillCardPanel().getBillModel(TAB1).setRowState(iRow,
				BillModel.MODIFICATION);
	}

	private boolean checkBeforeSave() {
		StringBuffer errorLins = new StringBuffer();

		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			Object l_ob = getBillCardPanel().getBillModel(TAB1).getValueAt(i,
					"nexchangeotobrate");
			if (PuPubVO.getUFDouble_NullAsZero(l_ob).doubleValue() == 0.0) {
				errorLins.append("表体行"
						+ getBillCardPanel().getBillModel(TAB1).getValueAt(i,
								"crowno") + ":折本汇率不能为空" + "\n");
			}
		}

		if (errorLins.length() > 0) {
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040401", "UPPSCMCommon-000270")/*
																 * @res "提示"
																 */, errorLins
					.toString());
			return false;
		}
		return true;
	}

	/**
	 * 弃审返回到制单人时，需要在此清空审批人、审批日期、审批时间
	 */
	private void setAuditInfo() {
		Integer iVoStatus = BillStatus.FREE;
		if (getBillCardPanel().getHeadItem("ibillstatus") != null) {
			iVoStatus = PuPubVO.getInteger_NullAs(getBillCardPanel()
					.getHeadItem("ibillstatus").getValueObject(),
					BillStatus.FREE);
		} else if (getBillCardPanel().getTailItem("ibillstatus") != null) {
			iVoStatus = PuPubVO.getInteger_NullAs(getBillCardPanel()
					.getTailItem("ibillstatus").getValueObject(),
					BillStatus.FREE);
		}
		if (BillStatus.AUDITFAIL.intValue() == iVoStatus.intValue()) {
			if (getBillCardPanel().getTailItem("cauditpsn") != null) {
				getBillCardPanel().getTailItem("cauditpsn").setValue(null);
			} else if (getBillCardPanel().getHeadItem("cauditpsn") != null) {
				getBillCardPanel().getHeadItem("cauditpsn").setValue(null);
			}
			if (getBillCardPanel().getTailItem("dauditdate") != null) {
				getBillCardPanel().getTailItem("dauditdate").setValue(null);
			} else if (getBillCardPanel().getHeadItem("dauditdate") != null) {
				getBillCardPanel().getHeadItem("dauditdate").setValue(null);
			}
			if (getBillCardPanel().getTailItem("taudittime") != null) {
				getBillCardPanel().getTailItem("taudittime").setValue(null);
			} else if (getBillCardPanel().getHeadItem("taudittime") != null) {
				getBillCardPanel().getHeadItem("taudittime").setValue(null);
			}
		}
	}

	/**
	 * 获得委外订单的卡片列表模版VO
	 * 
	 * @return 卡片列表模版VO
	 */
	private BillTempletVO getBillTempletVO() {
		if (billTempletVO == null) {
			billTempletVO = BillUIUtil.getDefaultTempletStatic(
					ScmConst.SC_Order, null, getOperatorID(), getPk_corp(),
					null, null);
		}

		return billTempletVO;
	}

	/**
	 * 获取合同是否启用
	 * 
	 * @return 合同启用 true; 合同不启用 false;
	 */
	private boolean isCtStartUp() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return m_bCTStartUp;
	}

	/**
	 * 获取本币金额精度
	 * 
	 * @return
	 */
	private int getLocalMnyPrecision() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return mint_localmnyPrecision;
	}

	/**
	 * 获取数量精度
	 * 
	 * @return
	 */
	private int getNumPrecision() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return mintary_precisions[0];
	}

	/**
	 * 获取辅数量精度
	 * 
	 * @return
	 */
	private int getAssNumPrecision() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return mintary_precisions[1];
	}

	/**
	 * 获取转换率精度
	 * 
	 * @return
	 */
	private int getRatePrecision() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return mintary_precisions[2];
	}

	/**
	 * 获取价格精度
	 * 
	 * @return
	 */
	private int getPricePrecision() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return mintary_precisions[3];
	}

	/**
	 * 从后台加载参数：合同是否启用，本币金额精度，数量精度，辅数量精度，转换率精度，价格精度
	 */
	private void loadParas() {
		try {
			/** ********************************* */
			Object[] objs = null;
			ServcallVO[] scds = new ServcallVO[3];

			// 合同是否启用
			scds[0] = new ServcallVO();
			scds[0].setBeanName("nc.itf.uap.sf.ICreateCorpQueryService");
			scds[0].setMethodName("isEnabled");
			scds[0].setParameter(new Object[] { getPk_corp(),
					ProductCode.PROD_CT });
			scds[0].setParameterTypes(new Class[] { String.class, String.class });

			// 数量精度
			scds[1] = new ServcallVO();
			scds[1].setBeanName("nc.itf.sc.IPublic");
			scds[1].setMethodName("getDigitBatch");
			scds[1].setParameter(new Object[] { getPk_corp(),
					new String[] { "BD501", "BD502", "BD503", "BD505" } });
			scds[1].setParameterTypes(new Class[] { String.class,
					String[].class });

			scds[2] = new ServcallVO();
			scds[2].setBeanName("nc.itf.sc.IPublic");
			scds[2].setMethodName("getCurrDigitByPKCorp");
			scds[2].setParameter(new Object[] { getPk_corp() });
			scds[2].setParameterTypes(new Class[] { String.class });

			objs = nc.ui.scm.service.LocalCallService.callService(scds);

			// 合同是否启用
			m_bCTStartUp = ((Boolean) objs[0]).booleanValue();// 合同是否启用

			int[] lInt_ary = (int[]) objs[1];
			if (lInt_ary != null && lInt_ary.length == 4) {
				mintary_precisions[0] = lInt_ary[0];
				mintary_precisions[1] = lInt_ary[1];
				mintary_precisions[2] = lInt_ary[2];
				mintary_precisions[3] = lInt_ary[3];
			}

			String s = (String) objs[2];
			if (s != null && s.length() > 0)
				mint_localmnyPrecision = Integer.parseInt(s.trim());

			parasHaveLoaded = true;
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040502", "UPP40040502-000052")/*
																 * @res
																 * "获取系统初始化参数出错"
																 */, e
					.getMessage());
		}
	}

	/**
	 * 获取卡片界面表头项对应的值
	 * 
	 * @param itemKey
	 * @return
	 */
	private Object getCardHeadItemValue(String itemKey) {
		return getBillCardPanel().getHeadItem(itemKey).getValueObject();
	}

	/**
	 * 获取卡片界面表体项对应的值
	 * 
	 * @param rowIndex
	 *            表体行标识，从0开始计数
	 * @param itemKey
	 * @return
	 */
	private Object getCardBodyValueAt(int row, String itemKey,String tab) {
		// return getBillCardPanel().getBodyValueAt(row, itemKey);//gc
		if(tab == null)// gc
			tab = getSelectTab();
		return getBillCardPanel().getBillModel(tab).getValueAt(row, itemKey);// gc
	}

	/**
	 * 设置卡片界面表体项对应的值
	 * 
	 * @param rowIndex
	 *            表体行标识，从0开始计数
	 * @param itemKey
	 * @return
	 */
	private void setCardBodyValueAt(Object aValue, int row, String strKey,String tab) {

		// getBillCardPanel().setBodyValueAt(aValue, row, strKey);//gc
		if(tab == null)// gc
			tab = getSelectTab();
		getBillCardPanel().getBillModel(tab).setValueAt(aValue, row, strKey);// gc
	}

	/**
	 * 获取委外订单的前台的DataModel
	 * 
	 */
	OrderModel getModel() {
		// if (orderModel == null) {
		// orderModel = new OrderModel();
		// }

		// modify by hanbin 原因：把缓存模型对象转移到ListPanel上，订单审批也引用订单的列表面板
		orderModel = this.getBillListPanel().getModel();
		return orderModel;
	}

	/**
	 * 得到选择的页
	 */
	private String getSelectTab() {
		return getBillCardPanel().getCurrentBodyTableCode();
	}
	private OrderDdlbVO [] getSaveDdlbVO(){
		OrderDdlbVO [] changeVO = (OrderDdlbVO[]) getBillCardPanel().getBillModel(TAB2).getBodyValueChangeVOs(OrderDdlbVO.class.getName());
		return changeVO;
	}

	public void afterTabChanged(BillTabbedPaneTabChangeEvent e) {
		// TODO Auto-generated method stub
		if(e.getBtvo().getTabcode().equals(TAB2)){
			
		}
	}
	
	private boolean checkDDLB(){
		OrderDdlbVO [] changeVO = (OrderDdlbVO[]) getBillCardPanel().getBillModel(TAB2).getBodyValueVOs(OrderDdlbVO.class.getName());
		if(changeVO != null){
			List<String> list = new ArrayList<String>();
			for (int i = 0; i < changeVO.length; i++) {
				String key =changeVO[i].getCmangid()+changeVO[i].getVfree1()
						+changeVO[i].getVfree2()+changeVO[i].getVfree3()
						+changeVO[i].getVfree4()+changeVO[i].getVfree5();
				if(list.contains(key)){
					return false;
				}else{
					list.add(key);
				}
			}
		}
		return true;
	}
}