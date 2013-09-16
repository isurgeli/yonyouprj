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
 * ί�ⶩ��ά��ClientUI
 * <p>
 * <b>�����ʷ��</b>
 * <p>
 * <hr>
 * <p>
 * �޸����� 2008.8.13
 * <p>
 * �޸��� zhaoyha
 * <p>
 * �汾 5.5
 * <p>
 * ˵����
 * <ul>
 * <li>֧�ֿ�Ƭ�༭
 * <li>�в����Ĺ淶(�����кš���Ƭ�༭�ȣ�Ȩ�޿��ƣ�
 * <li>ѡ��ģʽ����
 * </ul>
 * 
 * @author:xjl
 * @version:04-19-01
 * 
 */
/**
 * ����ҳǩ
 * 1.getBillCardPanel().getBillModel(TAB1).getItemByKey�滻ΪgetBillCardPanel()
 * .getBillModel(TAB1).getItemByKey 2.getBillModel(TAB1)�滻ΪgetBillModel(TAB1)
 * 3.getBillCardPanel().getBillTable(tab) 4.getBillCardPanel().setBodyValueAt
 * 5.afterEditCinventorycode 6.���������
 * 6.
 * 
 * @author gc
 * 
 */
public class OrderUI extends nc.ui.pub.ToftPanel implements BillEditListener,// ��ͷ������ı༭���иı��¼�
		BillEditListener2,// ����ı༭ǰ�¼�
		BillTableMouseListener, BillBodyMenuListener,// ���嵯��ʽ�˵�
		ISetBillVO, IBillRelaSortListener, IBillExtendFun,// ֧�ֲ�ҵ��������չ::getExtendBtns()/onExtendBtnsClick()/setExtendBtnsStat()
		ILinkMaintain,// �����޸�
		ILinkAdd,// ��������
		ILinkApprove,// ������
		ILinkQuery,// ������
		BillActionListener ,BillTabbedPaneTabChangeListener{

	// gc�������tab����
	private String TAB1 = "table";
	private String TAB2 = "lby";

	private static final long serialVersionUID = 1L;

	private int iMaxMnyDigit = 8;

	private POPubSetUI2 m_cardPoPubSetUI2 = null;

	// ��¼�Ƿ��ѯ������ѯ�������á�ˢ�¡�����
	private boolean m_bQueried = false;

	// ��ť��ʵ��,since v51
	private ButtonTree m_btnTree = null;

	// ҵ�������鰴ť
	private ButtonObject boBusitype = null;

	// �����鰴ť
	private ButtonObject boAdd = null;

	// �����鰴ť
	private ButtonObject boSave = null;

	// ά���鰴ť
	private ButtonObject boEdit = null;// �޸�

	private ButtonObject boCancel = null;// ȡ��

	private ButtonObject boDel = null;// ����

	private ButtonObject boCopy = null;// ����

	private ButtonObject boLineOperator = null;// �в����鰴ť

	private ButtonObject boAddLine = null;// ����

	private ButtonObject boDelLine = null;// ɾ��

	private ButtonObject boInsertLine = null;// ������

	private ButtonObject boCopyLine = null;// ������

	private ButtonObject boPasteLine = null;// ճ����

	private ButtonObject boPasteLineToTail = null;// ճ���е���β

	private ButtonObject boReOrderRowNo = null;// �����к�

	private ButtonObject boCardEdit = null;// ��Ƭ�༭

	// �в����Ҽ��˵�
	private UIMenuItem miReOrderRowNo = null;// �����к�

	private UIMenuItem miCardEdit = null;// ��Ƭ�༭

	// ִ���鰴ť
	private ButtonObject boAction = null;

	private ButtonObject boSendAudit = null;// ����

	private ButtonObject boAudit = null;// ����

	private ButtonObject boUnaudit = null;// ����

	private ButtonObject boCancelOut = null;// ����ת��

	// ��ѯ�鰴ť
	private ButtonObject boQuery = null;

	// ����鰴ť
	private ButtonObject boFrash = null;// ˢ��

	private ButtonObject boLocate = null;// ��λ

	private ButtonObject boFirst = null;// ��ҳ

	private ButtonObject boPre = null;// ��һҳ

	private ButtonObject boNext = null;// ��һҳ

	private ButtonObject boLast = null;// ĩҳ

	private ButtonObject boSelectAll = null;// ȫѡ

	private ButtonObject boSelectNo = null;// ȫ��

	// ��Ƭ��ʾ/�б���ʾ(�л�)
	private ButtonObject boReturn = null;

	// ��ӡ�����鰴ť
	private ButtonObject m_btnPrints = null;

	private ButtonObject m_btnPrintPreview = null;// Ԥ��

	private ButtonObject boPrint = null;// ��ӡ

	private ButtonObject btnBillCombin = null;;// �ϲ���ʾ

	// ������ѯ�鰴ť
	private ButtonObject m_btnOthersQueries = null;

	private ButtonObject boLinkQuery = null;// ����

	private ButtonObject boQueryForAudit = null;// ������״̬(״̬��ѯ)

	// ���������鰴ť
	private ButtonObject m_btnOthersFuncs = null;

	private ButtonObject boDocument = null;;// �ĵ�����

	private java.awt.CardLayout cardLayout = null;

	/** ת�뵥�� */
	private ArrayList comeVOs = null;

	/** �� */
	private BillCardPanel m_BillCardPanel;

	/** ����״̬ */
	private int m_billState = ScConstants.STATE_CARD;

	/** ��ǰVO */
	private OrderVO m_curOrderVO;

	//
	private nc.bs.bd.b21.CurrencyRateUtil m_CurrArith;

	/**
	 * �۸����Ȳ��� ��������ֵ����˰�۸����ȣ���˰�۸����� Ĭ��ֵ����˰�۸�����
	 */
	private int m_iPricePolicy = RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE;

	// ��ѯ��
	private OrderUIQueryDlg m_query;

	// ��ѯ����
	private String m_queryCon = null;

	/** �б� */
	private OrderListPanel m_ScOrderListPanel;

	// ��ǰ��ͷ��ע
	private String m_sHeadVmemo;

	// Ĭ�ϱ���
	String m_sPKCurrencyType = "";

	int m_intCurrencyDecimal = 2;

	// ������
	String m_sPKCurrencyTypeAssit = "";

	int m_intCurrencyDecimalAssit = 2;

	// ��������
	int[] mintary_precisions = new int[] { 2, 2, 2, 2 };

	//
	int mint_localmnyPrecision = 2;

	boolean mbol_HasGetDecimal = false;// �������Ч��

	// ��ͬ�Ƿ�����
	private boolean m_bCTStartUp = false;

	// �жϲ���mint_localmnyPrecision,mbol_HasGetDecimal,m_bCTStartUp�Ƿ�Ӻ�̨���ع�
	private boolean parasHaveLoaded = false;

	/* ������ӡ���� */
	private nc.ui.scm.pub.print.ScmPrintTool printList = null; // �����

	private CntSelDlg m_CntSelDlg = null;// ������ͬʱ,ѡ�񴰿�

	/**
	 * ��¼�Ƿ�ǰ����Ϊ����,�Ա������Ӻ�,����Ӧ����
	 */
	private boolean isAddedLine = false;

	// ��Ƭ�б���ģ��VO��Ϊ����Զ�̵��õ����ӣ�songhy
	private BillTempletVO billTempletVO;

	// ���湩Ӧ��VO
	private Map<String, CustDefaultVO> custDefaultVOMap;

	// ���ý������ݾ��ȵĹ�����
	private Precision precisionUtil;

	// ǰ̨������ģ��
	private OrderModel orderModel;

	/**
	 * V51�ع���Ҫ��ƥ��,��ťʵ����������
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author lxd
	 * @time 2007-3-15 ����02:23:07
	 */
	private void createBtnInstances() {
		// ҵ�������鰴ť
		boBusitype = getBtnTree().getButton(ScmButtonConst.BTN_BUSINESS_TYPE);
		// �����鰴ť
		boAdd = getBtnTree().getButton(ScmButtonConst.BTN_ADD);
		// �����鰴ť
		boSave = getBtnTree().getButton(ScmButtonConst.BTN_SAVE);
		// ά���鰴ť
		boEdit = getBtnTree().getButton(ScmButtonConst.BTN_BILL_EDIT);// �޸�
		boCancel = getBtnTree().getButton(ScmButtonConst.BTN_BILL_CANCEL);// ȡ��
		boDel = getBtnTree().getButton(ScmButtonConst.BTN_BILL_DELETE);// ɾ��
		boCopy = getBtnTree().getButton(ScmButtonConst.BTN_BILL_COPY);// ����
		// �в����鰴ť
		boLineOperator = getBtnTree().getButton(ScmButtonConst.BTN_LINE);
		boAddLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_ADD);// ����
		boDelLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_DELETE);// ɾ��
		boInsertLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_INSERT);// ������
		boCopyLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_COPY);// ������
		boPasteLine = getBtnTree().getButton(ScmButtonConst.BTN_LINE_PASTE);// ճ����
		boPasteLineToTail = getBtnTree().getButton(
				ScmButtonConst.BTN_LINE_PASTE_TAIL);// ճ���е���β
		boReOrderRowNo = getBtnTree()
				.getButton(ScmButtonConst.BTN_ADD_NEWROWNO);// �����к�
		boCardEdit = getBtnTree().getButton(ScmButtonConst.BTN_CARD_EDIT);// ��Ƭ�༭

		// ִ���鰴ť
		boAction = getBtnTree().getButton(ScmButtonConst.BTN_EXECUTE);
		boSendAudit = getBtnTree().getButton(ScmButtonConst.BTN_EXECUTE_AUDIT);// ����
		boAudit = getBtnTree().getButton(ScmButtonConst.BTN_AUDIT);// ����
		boUnaudit = getBtnTree().getButton(
				ScmButtonConst.BTN_EXECUTE_AUDIT_CANCEL);// ����
		boCancelOut = getBtnTree().getButton(ScmButtonConst.BTN_REF_CANCEL);// ����ת��

		// ��ѯ�鰴ť
		boQuery = getBtnTree().getButton(ScmButtonConst.BTN_QUERY);

		// ����鰴ť
		boFrash = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_REFRESH);// ˢ��
		boLocate = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_LOCATE);// ��λ
		boFirst = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_TOP);// ��ҳ
		boPre = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_PREVIOUS);// ��һҳ
		boNext = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_NEXT);// ��һҳ
		boLast = getBtnTree().getButton(ScmButtonConst.BTN_BROWSE_BOTTOM);// ĩҳ
		boSelectAll = getBtnTree().getButton(
				ScmButtonConst.BTN_BROWSE_SELECT_ALL);// ȫѡ
		boSelectNo = getBtnTree().getButton(
				ScmButtonConst.BTN_BROWSE_SELECT_NONE);// ȫ��

		// ��Ƭ��ʾ/�б���ʾ(�л�)
		boReturn = getBtnTree().getButton(ScmButtonConst.BTN_SWITCH);

		// ��ӡ�����鰴ť
		m_btnPrints = getBtnTree().getButton(ScmButtonConst.BTN_PRINT);
		m_btnPrintPreview = getBtnTree().getButton(
				ScmButtonConst.BTN_PRINT_PREVIEW);// Ԥ��
		boPrint = getBtnTree().getButton(ScmButtonConst.BTN_PRINT_PRINT);// ��ӡ
		btnBillCombin = getBtnTree().getButton(
				ScmButtonConst.BTN_PRINT_DISTINCT);// �ϲ���ʾ

		// ������ѯ�鰴ť
		m_btnOthersQueries = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_QUERY);
		boLinkQuery = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_QUERY_RELATED);// ����
		boQueryForAudit = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_QUERY_WORKFLOW);// ������״̬(״̬��ѯ)

		// ���������鰴ť
		m_btnOthersFuncs = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_FUNC);
		boDocument = getBtnTree().getButton(
				ScmButtonConst.BTN_ASSIST_FUNC_DOCUMENT);// �ĵ�����
	}

	/**
	 * ��ȡ��ť������Ψһʵ����
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @return <p>
	 * @author lxd
	 * @time 2007-3-15 ����05:04:22
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
	 * �˴����뷽��˵���� �������ڣ�(2001-4-19 18:19:11)
	 */
	public OrderUI() {
		super();
		init();
	}

	/**
	 * BillBodyMenuListener�Ľӿڷ������Ա���ʵ��û����
	 */
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// do nothing, BillBodyMenuListener
	}

	/**
	 * ��ͷ��Ӧ�̲��ձ༭��Ĵ���
	 */
	private void afterEditCvendormangid() {// gc ����Ҫ����
		int rowCount = getBillCardPanel().getRowCount();
		if (rowCount <= 0) {
			// ��������Ϊ0������
			return;
		}

		// ������Ϊ�գ��������Ӧ��Ĭ�ϱ���
		String cvendormangid = (String) getCardHeadItemValue(BillHeaderItemKeys.CVENDORMANGID);
		if (StringUtil.isEmpty(cvendormangid)) {
			// �༭��Ӧ��IdΪ�գ�����
			return;
		}

		// ��Ӧ��Id��Ϊ��,��ȡ��Ӧ��Ĭ�Ͻ��ױ���
		String currentyTypeId = getCurrencyTypeId(cvendormangid);
		if (StringUtil.isEmpty(currentyTypeId)) {
			// ��Ӧ��Ĭ�Ͻ��ױ���Ϊ�գ���ȡ��λ��
			currentyTypeId = m_sPKCurrencyType;
		}

		for (int rowIndex = 0; rowIndex < rowCount; rowIndex++) {
			if (StringUtil.isEmpty((String) getCardBodyValueAt(rowIndex,
					BillBodyItemKeys.CCURRENCYTYPEID,TAB1))) {
				// �����б���Ϊ�գ������ñ���
				getBillCardPanel().setBodyValueAt(currentyTypeId, rowIndex,
						BillBodyItemKeys.CCURRENCYTYPEID,TAB1);
				setCurrencyExchangeRateEnable(rowIndex);
				setCurrencyExchangeRate(rowIndex);
			}
		}
	}

	/**
	 * ��ͷ�༭���¼��Ĵ���
	 * 
	 * @param e
	 */
	private void afterEditItemInCardHead(BillEditEvent e) {
		// ��Ӧ�̴���Ĭ����
		if (e.getKey().equals(BillHeaderItemKeys.CVENDORMANGID)
				|| e.getKey().equals("caccountbankid")) {
			BillEdit.editCust(getBillCardPanel(), e.getKey());
			loadCustBank();
			afterEditCvendormangid();
			// ������ͬ���Զ�ȡ�۴���
			setRelateCntAndDefaultPriceAllRow(false);
			// ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
			setNotEditableWhenRelateCntAllRow();
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			setPrecision();
		}

		// ���š�ҵ��Ա��ϵ
		if (e.getKey().equals("cdeptid") || e.getKey().equals("cemployeeid")) {
			BillEdit.editDeptAndEmployee(getBillCardPanel(), e.getKey());
		}

		// �ջ�������������ַ
		if (e.getKey().equals("creciever")) {
			setReceiveAddress(0, e.getKey());
		}

		// �����֯����ƻ���������
		if (e.getKey().equals("cwareid")) {
			for (int i = 0; i < getBillCardPanel().getRowCount(); i++)
				BillEdit.editArrDate(getBillCardPanel(), i, e.getKey(),
						nc.ui.pub.ClientEnvironment.getInstance().getDate());

			// Czp : V31 ���Ӳֿ�������֯ƥ�䴦��
			String strWareId = (String) getCardHeadItemValue(BillBodyItemKeys.CWAREID);
			// �����֯�仯�󣬲ֿ���ձ仯:��ձ���ֿ�ID(cwarehouseid)�ͱ�������֯(warehouseorg);
			afterEditStoreOrgToWarehouse(getBillCardPanel(), e,
					getCorpPrimaryKey(), strWareId,
					BillBodyItemKeys.CWAREHOUSENAME, new String[] {
							BillBodyItemKeys.CWAREHOUSEID,
							BillHeaderItemKeys.WAREHOUSEORG });
		}

		// �Զ�����PK����
		setHeadDefPK(e);

		if (e.getKey().equals(BillHeaderItemKeys.DORDERDATE)) {
			// ������ͬ���Զ�ȡ�۴���
			setRelateCntAndDefaultPriceAllRow(false);
			// ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
			setNotEditableWhenRelateCntAllRow();
		}

		if (e.getKey().equals(BillBodyItemKeys.CWAREID)) {
			setRelateCntAndDefaultPriceAllRow(true);
		}

		if (e.getKey().indexOf(BillHeaderItemKeys.DEF_PREFIX) >= 0)
			getBillCardPanel().execHeadEditFormulas();
	}

	/**
	 * ת��Object����Ϊ�ַ�������
	 * 
	 * @param objArray
	 *            Object����
	 * @return �ַ�������
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
	 * ��ѡ�������¼�����
	 * 
	 * @param e
	 */
	private void afterEditCinventorycode(BillEditEvent e) throws Exception {
		String tab = getSelectTab();//gc
		UIRefPane refpane = (UIRefPane) getBillCardPanel().getBillModel(tab)
				.getItemByKey(BillBodyItemKeys.CINVENTORYCODE).getComponent();

		if (refpane.getRefPKs() == null) {
			// û��ѡ��������������ֱ�ӷ���
			return;
		}

		// ���������ID
		Object[] saMangId = ((Object[]) refpane
				.getRefValues("bd_invmandoc.pk_invmandoc"));
		// �����������ID
		Object[] saBaseId = ((Object[]) refpane
				.getRefValues("bd_invmandoc.pk_invbasdoc"));
		// �������
		Object[] saCode = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invcode"));
		// �������
		Object[] saName = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invname"));
		// ������
		Object[] saSpec = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invspec"));
		// ����ͺ�
		Object[] saType = ((Object[]) refpane
				.getRefValues("bd_invbasdoc.invtype"));

		// �����������λID
		String[] saBaseIdTemp = convertToStringArray(saBaseId);
		Object[] saMeasUnit = (Object[]) CacheTool.getColumnValue(
				"bd_invbasdoc", "pk_invbasdoc", "pk_measdoc", saBaseIdTemp);

		String[] saMeasUnitTemp = convertToStringArray(saMeasUnit);
		Object[] saUnitNameTemp = (Object[]) CacheTool.getColumnValue(
				"bd_measdoc", "pk_measdoc", "measname", saMeasUnitTemp);
		// �����������λ����
		String[] saUnitName = convertToStringArray(saUnitNameTemp);

		Object[] sAssisUnitTemp = null;
		String[] sAssisUnit = null;
		Object[] sIsAssisUnitTemp = null;
		String[] sAssisUnitName = null;
		String[] sIsAssisUnit = null;
//		if(tab.equals(TAB1)){
			// ����������
			sAssisUnitTemp = (Object[]) CacheTool.getColumnValue(
					"bd_invbasdoc", "pk_invbasdoc", "pk_measdoc2", saBaseIdTemp);
			sAssisUnit = convertToStringArray(sAssisUnitTemp);
	
			// �Ƿ񸨼�����������Ч�ʣ�����������
			InvTool.loadBatchAssistManaged(saBaseIdTemp);
			sIsAssisUnitTemp = (Object[]) CacheTool.getColumnValue(
					"bd_invbasdoc", "pk_invbasdoc", "assistunit", saBaseIdTemp);
			sIsAssisUnit = convertToStringArray(sIsAssisUnitTemp);
	
			// ����������
			Object[] sAssisUnitNameTemp = (Object[]) CacheTool.getColumnValue(
					"bd_measdoc", "pk_measdoc", "measname", sAssisUnit);
			sAssisUnitName = convertToStringArray(sAssisUnitNameTemp);
//		}
		// �������
		int iInsertLen = (saMangId == null) ? 0 : (saMangId.length - 1);
		int iBeginRow = 0, iEndRow = 0;
		iBeginRow = e.getRow();
		if (iBeginRow == getBillCardPanel().getRowCount() - 1) {
			// ѡ�е����������һ��������
			for (int i = 0; i < iInsertLen; i++) {
				getBillCardPanel().addLine();
			}
		} else {
			// ����
			onInsertLines(iBeginRow, iBeginRow + 1, iInsertLen);
		}
		iEndRow = iBeginRow + iInsertLen;

		String[] saMangIdTemp = convertToStringArray(saMangId);
		// ����Ч�ʣ���ѭ������ÿ����������κ�֮ǰ���������������κ�
		InvTool.loadBatchProdNumMngt(saMangIdTemp);
		// ����Ч�ʣ���ѭ������ÿ�������������֮ǰ������������������
		InvTool.loadBatchFreeVO(saMangIdTemp);
		// �����и�ֵ
		int iPkIndex = 0;
		for (int i = iBeginRow; i <= iEndRow; i++) {
			iPkIndex = i - iBeginRow;

			// ����ID
			setCardBodyValueAt(saMangId[iPkIndex], i, BillBodyItemKeys.CMANGID,null);
			// ����ID
			setCardBodyValueAt(saBaseId[iPkIndex], i, BillBodyItemKeys.CBASEID,null);
			// ����
			setCardBodyValueAt(saCode[iPkIndex], i,
					BillBodyItemKeys.CINVENTORYCODE,null);
			// ����
			setCardBodyValueAt(saName[iPkIndex], i,
					BillBodyItemKeys.CINVENTORYNAME,null);
			// ���
			setCardBodyValueAt(saSpec[iPkIndex], i, BillBodyItemKeys.INVSPEC,null);
			// �ͺ�
			setCardBodyValueAt(saType[iPkIndex], i, BillBodyItemKeys.INVTYPE,null);
			// ��������λpk
			setCardBodyValueAt(saMeasUnit[iPkIndex], i,
					BillBodyItemKeys.PK_MEASDOC,null);
			// ��������λNAME
			setCardBodyValueAt(saUnitName[iPkIndex], i,
					BillBodyItemKeys.CMEASDOCNAME,null);
//			if(tab.equals(TAB1)){
				// ��������λpk
				setCardBodyValueAt(sAssisUnit[iPkIndex], i,
						BillBodyItemKeys.CASSISTUNIT,null);
				// ��������λname
				setCardBodyValueAt(sAssisUnitName[iPkIndex], i,
						BillBodyItemKeys.CASSISTUNITNAME,null);
				// �Ƿ񸨼�������
				setCardBodyValueAt(sIsAssisUnit[iPkIndex], i,
						BillBodyItemKeys.ISASSIST,null);
				if (getBillCardPanel().getBillModel(tab).getRowState(i) != BillModel.MODIFICATION) {
					getBillCardPanel().getBillModel(tab).setRowState(i,
							BillModel.ADD);
				}
//			}
			// ���κŴ�����ʼ
			// ���κŵĿɱ༭��
			getBillCardPanel().getBillModel(tab).setCellEditable(i,
					BillBodyItemKeys.VPRODUCENUM,
					InvTool.isBatchManaged(saMangIdTemp[iPkIndex]));
			// ���κ����
			setCardBodyValueAt(null, i, BillBodyItemKeys.VPRODUCENUM,null);
			// ���κŴ������

			// ���������ʼ
			InvVO invVO = new InvVO();
			FreeVO freeVO = InvTool.getInvFreeVO((String) saMangId[iPkIndex]);
			if (freeVO == null) {
				getBillCardPanel().setCellEditable(i, "vfree0", false,tab);
				invVO.setIsFreeItemMgt(new Integer(0));
			} else {
				getBillCardPanel().setCellEditable(i, "vfree0", true,tab);
				invVO.setIsFreeItemMgt(new Integer(1));

				// add by hanbin 2009-11-18 ԭ��ȥ���������Ĭ��ֵ��
				// ���bug(NCdp201081223)��������������������¼�������������ʾ�ϴ�¼������ݡ�
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
			// ����������
		}

		// ��ѡ�����ĸ�������λ����
		InvTool.loadBatchInvConvRateInfo(saBaseIdTemp, sAssisUnit);
		BillEdit.editAssistUnitForMultiSelected(getBillCardPanel(), iBeginRow,
				iEndRow, e.getKey());

		// ���ö�ѡ�����Ĭ��˰��
		setRelated_Taxrate(iBeginRow, iEndRow);

		// 2009-9-11 renzhonghai ������ͬ����
		int lengthLine = iEndRow - iBeginRow + 1;
		int[] lintary_rows = new int[lengthLine];
		for (int i = iBeginRow, j = 0; i <= iEndRow; i++, j++) {
			lintary_rows[j] = i;
		}
		if(tab.equals(TAB1)){
			// �Զ�ȡ��
			setRelateCntAndDefaultPrice(lintary_rows, false);
			// ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
			setNotEditableWhenRelateCntAllRow();
		}
		// �༭��ִ�й�ʽ
		getBillCardPanel().getBillModel(tab).execLoadFormula();
	}

	/**
	 * ��ȡ�۸����Ȳ��� ��������ֵ�� ��˰�۸����� RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE�� ��˰�۸�����
	 * RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE Ĭ��ֵ����˰�۸�����
	 * 
	 * @return
	 */
	private int getPricePolicy() {
		return m_iPricePolicy;
	}

	/**
	 * ����༭���¼��Ĵ���
	 * 
	 * @param e
	 */
	private void afterEditItemInCardBody(BillEditEvent e) throws Exception {
		int rowindex = e.getRow();
		// ����(0---100)
		if (e.getKey().equals(BillBodyItemKeys.NDISCOUNTRATE)) {// gc ����Ҫ����
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
																	 * "����Ӧ��0---100֮�䣬������¼��"
																	 */);
					getBillCardPanel().setBodyValueAt("100", rowindex,
							BillBodyItemKeys.NDISCOUNTRATE);
					getBillCardPanel().execBodyFormula(rowindex,
							BillBodyItemKeys.NDISCOUNTRATE);
				}
			}
		} else if (e.getKey().equals(BillBodyItemKeys.CINVENTORYCODE)) {
			// �����ѡ
			afterEditCinventorycode(e);
		} else if (e.getKey().equals("vfree0")) {
			String tab = getSelectTab();//gc
			BillEdit.editFreeItem(getBillCardPanel(),tab, rowindex, e.getKey(),
						"cbaseid", "cmangid");
		}
		// �ֿ⣭�������嵽����ַ
		else if (e.getKey().equals("cwarehousename")) {
			setReceiveAddress(rowindex, e.getKey());
		} else if ("cprojectname".equals(e.getKey())) {// gc ֻ�е�һ��ҳ���У������޸�
			/***** ��Ŀ�޸ĺ󣬴�����Ŀ�׶��ֶ��Ƿ���Ա༭�����ԭ����Ŀ�׶ε�ֵ *****/
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
		// ��Ŀ��������Ŀ�׶�
		else if (e.getKey().equals("cprojectname")) {// gc ֻ�е�һ��ҳ���У������޸�
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

		// ����������ϵ����
		int[] descriptions = new int[] { RelationsCal.DISCOUNT_TAX_TYPE_NAME,
				RelationsCal.DISCOUNT_TAX_TYPE_KEY, RelationsCal.NUM,
				RelationsCal.TAXPRICE_ORIGINAL, RelationsCal.PRICE_ORIGINAL,
				RelationsCal.NET_TAXPRICE_ORIGINAL,
				RelationsCal.NET_PRICE_ORIGINAL, RelationsCal.DISCOUNT_RATE,
				RelationsCal.TAXRATE, RelationsCal.MONEY_ORIGINAL,
				RelationsCal.TAX_ORIGINAL, RelationsCal.SUMMNY_ORIGINAL,
				RelationsCal.PK_CORP };

		String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// Ӧ˰���;
		Object oTemp = getBillCardPanel().getBodyValueAt(e.getRow(),
				"idiscounttaxtype");// gc ֻ�е�һ��ҳ���У������޸�
		if (oTemp != null) {
			lStr_discounttaxtype = oTemp.toString();
		}

		String[] keys = new String[] { lStr_discounttaxtype,
				"idiscounttaxtype",// ��˰���
				"nordernum",// ��������
				"norgtaxprice",// ��˰����
				"noriginalcurprice",// ����
				"norgnettaxprice",// ����˰����
				"noriginalnetprice",// ������
				"ndiscountrate",// ����
				"ntaxrate",// ˰��
				"noriginalcurmny",// ���
				"noriginaltaxmny",// ˰��
				"noriginalsummny",// ��˰�ϼ�
				"pk_corp"// ��˾
		};

		if (e.getKey().equals("nordernum")&&getSelectTab().equals(TAB1)) {
			// �޸Ķ�������ʱ
			if (getBillCardPanel().getBillModel(TAB1).getValueAt(rowindex,
					"ntaxrate") == null)// ˰��Ϊ��ʱ����Ϊ0,�����㷨Ҫ��,since
				// v501���磺�޸ĸ���λ����˰�ϼ�Ϊ�գ��������ֵ��
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
					"ntaxrate") == null)// ˰��Ϊ��ʱ����Ϊ0,�����㷨Ҫ��,since
				// v501���磺�޸ĸ���λ����˰�ϼ�Ϊ�գ��������ֵ��
				getBillCardPanel().getBillModel(TAB1).setValueAt(
						new UFDouble(0), rowindex, "ntaxrate");
			RelationsCal.calculate(getBillCardPanel(), e, getBillCardPanel()
					.getBillModel(TAB1), new int[] { getPricePolicy() },
					descriptions, keys, OrderItemVO.class.getName());
		}

		if (e.getKey().equals("cassistunitname")
				|| e.getKey().equals("nassistnum")
				|| e.getKey().equals("measrate")) {// ����λ��������������������������
			BillEdit.editAssistUnit(getBillCardPanel(), rowindex, e.getKey());
			BillEditEvent t = new BillEditEvent(e.getSource(), e.getValue(),
					"nordernum", e.getRow(), e.getPos());

			RelationsCal.calculate(getBillCardPanel(), t, getBillCardPanel()
					.getBillModel(TAB1), new int[] { getPricePolicy() },
					descriptions, keys, OrderItemVO.class.getName());
		}

		// ���ַ����仯�����¼���
		if (e.getKey().equals("ccurrencytype")) {
			setMaxMnyDigit(iMaxMnyDigit);
			setExchangeRateBody(rowindex, true);
			// ���û��ʡ����ȵľ���
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
			// �޸ı���ʱ,Ӧ��������ͬ�������,������true
			setRelateCntAndDefaultPrice(new int[] { e.getRow() }, false);
		} else if (e.getKey().equals("nexchangeotobrate")) {
			// �޸��۱��۸�����ʱ,Ӧ��������ͬ�������,������true
			setRelateCntAndDefaultPrice(new int[] { e.getRow() }, true);
		}
		// ��ͬ��
		else if (e.getKey().equals("ccontractrcode")) {
			afterEditWhenBodyCntCode(e);// �޸ĺ�ͬ�ź����Ӧ�仯
		}

		// �Զ�����PK����
		setBodyDefPK(e);
	}

	/*
	 * �༭���¼�����
	 */
	public void afterEdit(BillEditEvent e) {
		try {
			if (e.getPos() == BillItem.HEAD) {
				afterEditItemInCardHead(e);
			}

			if (e.getPos() == BillItem.BODY) {
				afterEditItemInCardBody(e);
			}

			// ˢ�½���
			getBillCardPanel().updateUI();
		} catch (Exception ex) {
			SCMEnv.out("error!  afterEdit(BillEditEvent e)" + ex.getMessage());
			this.showErrorMessage(ex.getMessage());
		}
	}

	/**
	 * ���ý������ݾ���
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
	 * ��ȡ���澫�����ù���
	 * 
	 * @return �������ù���
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
	 * ���ߣ�yye ���ܣ��޸ĺ�ͬ�ź����Ӧ�仯 ������BillEditEvent e ��׽����BillEditEvent�¼� ���أ��� ���⣺��
	 */
	private void afterEditWhenBodyCntCode(BillEditEvent e) {// gc ֻ�е�һ��ҳ���У������޸�
		try {
			int lint_rowindex = e.getRow();
			if (e.getValue() == null
					|| e.getValue().toString().trim().length() < 1) {
				// �Ƿ�ɱ༭
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
				// ��ͬID ��ͬ��ID ��ͬ�� ����
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
				// refmodelCt.getValue("ct_b.oriprice");���༭����ı��ͬ����oriprice(UFDouble����)ת��ΪBigDecimal�������������ط����ã���ע����
				// ����ֵ
				getBillCardPanel().setBodyValueAt(oRet[0], lint_rowindex,
						"ccontractid");// ��ͬID
				getBillCardPanel().setBodyValueAt(oRet[1], lint_rowindex,
						"ccontractrowid");// ��ͬ��ID
				getBillCardPanel().setBodyValueAt(oRet[2], lint_rowindex,
						"ccontractrcode");// ��ͬ��
				RetCtToPoQueryVO[] lary_CtRetVO = null;

				// ���ý���������ɱ༭��
				if (oRet[1] != null && oRet[1].toString().trim().length() > 0) {
					String lStr_CtBid = oRet[1].toString().trim();
					lary_CtRetVO = ScFromCtHelper
							.queryCntByct_b(new String[] { lStr_CtBid });

					if (lary_CtRetVO != null && lary_CtRetVO.length > 0
							&& lary_CtRetVO.length == 1) {
						// �޸Ķ����е�itemkey
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

							// /��ԭ��ֵ��ͬ�����¼���
							if (lUFD_OldPrice == null
									|| lUFD_Price.compareTo(lUFD_OldPrice) != 0) {
								getBillCardPanel().setBodyValueAt(lUFD_Price,
										lint_rowindex, lStr_ChangedKey);
								getBillCardPanel().setBodyValueAt(
										lary_CtRetVO[0].getTaxration(),
										lint_rowindex, "ntaxrate");

								// ���¼���������ϵ
								// ����������ϵ����
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
								String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// Ӧ˰���
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

					// ���û��ʡ����ȵľ���
					setExchangeRateBody(lint_rowindex, true);

					getBillCardPanel().updateUI();
				}

				setRelateCntAndDefaultPrice(new int[] { lint_rowindex }, true);
				setNotEditableWhenRelateCnt(new int[] { lint_rowindex });
			}
			// �öδ����ֹ˫����ͬ�Ų��յ�TEXT����ʱ����ͬ����յ�����
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
	 * �༭ǰ���� �������ڣ�(2001-3-23 2:02:27)
	 * 
	 * @param e
	 *            ufbill.BillEditEvent
	 */
	public boolean beforeEdit(nc.ui.pub.bill.BillEditEvent e) {
		if (e.getKey().equals("vproducenum")) {

			nc.ui.scm.pu.ParaVOForBatch vo = new nc.ui.scm.pu.ParaVOForBatch();
			int iRow = e.getRow();
			BillCardPanel bcPanel = getBillCardPanel();

			// ����FieldName
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

			// ���ÿ�Ƭģ��,��˾��
			vo.setCardPanel(getBillCardPanel());
			vo.setPk_corp(getCorpPrimaryKey());
			vo.setEvent(e);

			try {
				nc.ui.sc.pub.ScTool.beforeEditWhenBodyBatch(vo);
			} catch (Exception ex) {
				SCMEnv.out(ex.getMessage());
			}
		}
		// �ֿ�
		else if (e.getKey().equals("cwarehousename")) {
			((UIRefPane) getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("cwarehousename").getComponent())
					.setPk_corp(getCorpPrimaryKey());
			BillPanelTool.restrictWarehouseRefByStoreOrg(getBillCardPanel(),
					getCorpPrimaryKey(), (String) getBillCardPanel()
							.getHeadItem("cwareid").getValueObject(),
					"cwarehousename");
		}

		// ���
		else if (e.getKey().equals("cinventorycode")) {

			// ֹͣ�༭!!!
			getBillCardPanel().stopEditing();

			int iRow = e.getRow();
			String sClassId = null;
			String sCntRowId = null;
			// �ϲ���ԴΪ��ͬ
			String sUpSourceType = (String) getBillCardPanel().getBodyValueAt(
					iRow, "cupsourcebilltype");
			if (sUpSourceType != null
					&& (sUpSourceType.equals(BillTypeConst.CT_BEFOREDATE) || sUpSourceType
							.equals(BillTypeConst.CT_ORDINARY))) {
				sCntRowId = (String) getBillCardPanel().getBodyValueAt(iRow,
						"cupsourcebillrowid");

			} else {
				// 2009-9-30 renzhonghai ����й�����ͬ������ѡ��Ӧ���պ�ͬ���������ˡ�
				sCntRowId = (String) getBillCardPanel().getBodyValueAt(iRow,
						"ccontractrowid");
			}
			if (sCntRowId != null) {
				RetCtToPoQueryVO voCntInfo = PoPublicUIClass
						.getCntInfo(sCntRowId);
				if (voCntInfo != null
						&& voCntInfo.getIinvCtl() == RetCtToPoQueryVO.INVCLASSCTL) {
					// ��������ͬ�Ĵ��ֻ���ڸ�����
					sClassId = voCntInfo.getCInvClass();
				}
			}

			String tab = getSelectTab();
			UIRefPane ref = (UIRefPane) getBillCardPanel().getBillModel(tab)
					.getItemByKey("cinventorycode").getComponent();
			ref.setPk_corp(getPk_corp());
			if (sClassId == null) {
				ref.getRefModel().setChangeTableSeq(true);
				// ��������PKΪ�գ�����ʾ���д��
				((InvmandocDefaultRefModel) ref.getRefModel())
						.setClassWherePart("");
			} else {
				// �رա�����������ҳǩ
				ref.getRefModel().setChangeTableSeq(false);
				// ��������PK��Ϊ�գ��򰴴������PK���˲���
				Object[] obj = null;
				try {
					obj = (Object[]) CacheTool.getColumnValue("bd_invcl",
							"pk_invcl", "invclasscode",
							new String[] { sClassId });
				} catch (BusinessException ex) {
					// ��־�쳣
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
		// ��ͬ��
		else if (e.getKey().equals(ScConstants.SC_ORDER_BODY_CT_CODE)) {
			int iRow = e.getRow();
			// ���ù�˾ID����Ӧ��ID�����ID���������ú�ͬ�Ų���
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
			/******* �޸���Ŀ�׶�ʱ��������Ŀ�Ƿ���ֵ����ȷ���Ƿ���Ա༭ ************/
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
	 * ���ܣ����в������������޸ġ�����ת��
	 */
	public void bodyRowChange(BillEditEvent e) {
		// ���Ϊ���в���,��������Ӧ�Ĵ���
		if (isAddedLine) {
			afterAppendLine();
			isAddedLine = false;
			return;
		}
		try {
			String tab = getSelectTab();// gc
			if (tab.equals(TAB1)) {
				// modify by hanbin 2009-11-16
				// ԭ�򣺿��ƾ��ȴ���ԭ�����ڷ���β���������м���return��䣬���¿��ܲ��ܱ�ִ��
				// ���ƾ���
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
				// ��Ŀ�׶�
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

				// �����۱��Ƿ�ɱ༭
				setCurrencyExchangeRateEnable(rowindex);

				// ����Ƿ�ɱ༭�������빺���򲻿ɱ༭��
				Object cupsourcebillrowid = getBillCardPanel().getBodyValueAt(
						rowindex, "cupsourcebillrowid");
				if (cupsourcebillrowid != null
						&& !cupsourcebillrowid.toString().trim().equals("")) {

					// �ϲ���ԴΪ��ͬ
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
								// ��������ͬ�Ĵ�����Ա༭
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
			// �����Ǵ���������
			Object pk_invbasdoc = getBillCardPanel().getBillModel(tab).getValueAt(rowindex,
					"cbaseid"); // ��������ID
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
				// ���и�������λ����
				Object bisAssist = getBillCardPanel().getBodyValueAt(rowindex,
						"isassist");// �Ƿ񸨼�������
				if (bisAssist == null || bisAssist.equals("N")) {
					getBillCardPanel().setCellEditable(rowindex, "cassistunitname",
							false);
					getBillCardPanel().setCellEditable(e.getRow(), "measrate",
							false);
				} else {// ��Ϊ����������
					getBillCardPanel().setCellEditable(rowindex, "cassistunitname",
							true);
					getBillCardPanel()
							.setCellEditable(e.getRow(), "measrate", true);
					String cmangid = (String) getBillCardPanel().getBodyValueAt(
							rowindex, "cmangid"); // ��ǰ���������ID
					UIRefPane measRef = (UIRefPane) getBillCardPanel()
							.getBillModel(TAB1).getItemByKey("cassistunitname")
							.getComponent();
					measRef.setText("");
					new InvMeasRate().filterMeas(getPk_corp(), cmangid, measRef);
				}
	
				// �������Ƿ�ɱ༭
				Object pk_assmeasdoc = getBillCardPanel().getBodyValueAt(rowindex,
						"cassistunit"); // ����λ
				if (pk_assmeasdoc == null
						|| pk_assmeasdoc.toString().trim().equals("")) {
					getBillCardPanel().setCellEditable(e.getRow(), "measrate",
							false);
				} else {
					// ����Ƿ�̶������ʡ��������������ʡ�
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
			// ��ʾ������
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
	 * ���������ң��۱����۸������ҽ����ҽ�refer invoice
	 */
	private boolean calNativeAndAssistCurrValue(OrderVO orderVO) {

		// ������ʣ�ȡ��������
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
																	 * @res "����"
																	 */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
								"UPP401201-000036")/*
													 * @res "δָ����λ�ң������������"
													 */);
				return false;
			}
			OrderItemVO[] itemVOs = (OrderItemVO[]) orderVO.getChildrenVO();
			for (int i = 0; i < itemVOs.length; i++) {
				OrderItemVO itemVO = itemVOs[i];

				// =============���ҽ��(nmoney):����Դ����������Ŀ�ı���������Դ���ֽ��û�ָ�����»��ʡ����ڲ�ѯ���ʵ����ڵõ����ҽ�ע������ȡ����"����"���"����ϵͳ����"(ICurrRateConst.CURRDIGIT_FINANCE)
				nmoney = getCurrencyRateUtil().getAmountByOpp(
						itemVO.getCcurrencytypeid(), strLocalCurrId,
						itemVO.getNoriginalcurmny(),
						itemVO.getNexchangeotobrate(), strRateDate);

				// =============ԭ�Ҽ�˰�ϼ�(noriginalsummny)
				nsummny = null;
				if (itemVO.getNoriginalsummny() != null) {
					nsummny = getCurrencyRateUtil().getAmountByOpp(
							itemVO.getCcurrencytypeid(), strLocalCurrId,
							itemVO.getNoriginalsummny(),
							itemVO.getNexchangeotobrate(), strRateDate);
				}
				// ��ֵ
				itemVOs[i].setNmoney(nmoney);
				itemVOs[i].setNsummny(nsummny);
				itemVOs[i].setNtaxmny((nsummny == null ? new UFDouble(0)
						: nsummny).sub(nmoney == null ? new UFDouble(0)
						: nmoney));
			}
			// ��ֵ
			orderVO.setChildrenVO(itemVOs);
			m_curOrderVO = orderVO;

		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "����"
																	 */, e
					.getMessage());
			return false;
		}

		return true;

	}

	/**
	 * ����б���� �������ڣ�(2001-9-24 18:20:09)
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
	 * ���ߣ�yye ���ܣ�����ͷ��ʽ���ݣ����ⲻ�ɲ��ճ������� ������ ���أ� ���⣺ ���ڣ�(2004-5-20 14:18:41)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 * 
	 * @param vo
	 *            nc.vo.pr.pray.PraybillVO
	 */
	public void execHeadTailFormula(OrderVO vo) {
		if (vo == null) {
			return;
		}
		// ������ʽ
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
	 * ��ð�ť������ �������ڣ�(2001-11-15 8:56:16)
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
	 * �˴����뷽��˵���� ��������:����״̬�� �������: ����ֵ: �쳣����: ����:
	 * 
	 * @return java.lang.String
	 */
	public String getBillButtonState() {
		return "scorder001";
	}

	/**
	 * ����ģ�忨ƬPanel
	 */
	private BillCardPanel getBillCardPanel() {
		if (m_BillCardPanel == null) {
			try {

				m_BillCardPanel = new BillCardPanel();
				m_BillCardPanel.setName("CardPanel");
				// ���ÿ�Ƭģ��VO
				m_BillCardPanel.setBillData(new BillData(getBillTempletVO()));

				// �¼�����
				m_BillCardPanel.addEditListener(this);
				m_BillCardPanel.addBodyMouseListener(this);
				m_BillCardPanel.addBodyEditListener2(this);
				m_BillCardPanel.addBodyMenuListener(this);
				
				m_BillCardPanel.addEditListener(TAB2, this);//gc
				m_BillCardPanel.addBodyMouseListener(TAB2,this);//gc
				m_BillCardPanel.addBodyEditListener2(TAB2,this);//gc
				m_BillCardPanel.addBodyMenuListener(TAB2,this);//gc

				// ���ݶ����¼�����
				m_BillCardPanel.addActionListener(this);
				m_BillCardPanel.addActionListener(TAB2,this);//gc


				//
				setNormalPrecision2();
				initComboBox();
				initRefer();

				// �����Զ�����
				ScTool.changeBillDataByUserDef(m_BillCardPanel, getPk_corp(),
						ScmConst.SC_Order, getOperatorID());

				// ��ʼ���к�
				nc.ui.scm.pub.report.BillRowNo.loadRowNoItem(m_BillCardPanel,
						"crowno");

				// ��ʾ��Ӧ�̵ļ��
				BillItem l_billitem = m_BillCardPanel
						.getHeadItem("cvendormangid");
				UIRefPane pane = (UIRefPane) l_billitem.getComponent();
				pane.getRef().getRefModel()
						.setRefNameField("bd_cubasdoc.custshortname");

				// V55��֧������ѡ��
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
	 * ͨ�����ݺŹ����õ�ǰ���ݺţ�ֻ���ڱ���ǰ���ɣ� �������ڣ�(2001-9-19 11:06:43)
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
			objVO.setAttributeValue("��˾", getPk_corp());/* -=notranslate=- */
			objVO.setAttributeValue("�ɹ���֯", cpurid);/* -=notranslate=- */
			objVO.setAttributeValue("�����֯", cwareid);/* -=notranslate=- */
			objVO.setAttributeValue("����", cdeptid);/* -=notranslate=- */
			objVO.setAttributeValue("ҵ������", cbiztype);/* -=notranslate=- */

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
	 * �����б�
	 * 
	 * @return nc.ui.pub.bill.BillCardPanel
	 */
	/* ���棺�˷������������ɡ� */
	private OrderListPanel getBillListPanel() {
		if (m_ScOrderListPanel == null) {
			try {
				m_ScOrderListPanel = new OrderListPanel(this);
				m_ScOrderListPanel.setName("ListPanel");

				// �����б�ģ��VO
				m_ScOrderListPanel.setListData(new BillListData(
						getBillTempletVO()));

				// initIDs();
				m_ScOrderListPanel.hideTableCol();
				m_ScOrderListPanel.updateUI();

				m_ScOrderListPanel.setNormalPrecision();
				// �����Զ�����
				ScTool.changeListDataByUserDef(m_ScOrderListPanel,
						getPk_corp(), ScmConst.SC_Order, getOperatorID());

				m_ScOrderListPanel.addMouseListener(this);
				m_ScOrderListPanel.getHeadTable().getSelectionModel()
						.addListSelectionListener(m_ScOrderListPanel);

				// ����Ϊ����ѡ��ģʽ
				m_ScOrderListPanel
						.getHeadTable()
						.setSelectionMode(
								javax.swing.ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

				// �����б�ϼ�
				m_ScOrderListPanel.getChildListPanel().setTotalRowShow(true);

				m_ScOrderListPanel.getHeadBillModel()
						.addSortRelaObjectListener(this);

				UIComboBox discountTaxType = (UIComboBox) m_ScOrderListPanel
						.getBodyItem("idiscounttaxtype").getComponent(); //
				discountTaxType.addItem(ScConstants.TaxType_Including);// Ӧ˰�ں�
				discountTaxType.addItem(ScConstants.TaxType_Not_Including);// Ӧ˰���
				discountTaxType.addItem(ScConstants.TaxType_No);// ����˰
				m_ScOrderListPanel.getBodyItem("idiscounttaxtype")
						.setWithIndex(true);

			} catch (java.lang.Throwable ivjExc) {
				handleException(ivjExc);
			}
		}
		return m_ScOrderListPanel;
	}

	/**
	 * ��ȡ���ֻ��ʹ��߶���
	 * 
	 * @return nc.ui.bd.b21.CurrencyRateUtil ���ֻ��ʹ���
	 */
	private nc.bs.bd.b21.CurrencyRateUtil getCurrencyRateUtil() {
		if (m_CurrArith == null) {
			m_CurrArith = new nc.bs.bd.b21.CurrencyRateUtil(getPk_corp());
		}
		return m_CurrArith;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-2 13:24:19)
	 * 
	 * @return java.lang.String
	 */
	public String getModuleCode() {
		return "401201";
		// return getModuleCode();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-2 13:21:20)
	 * 
	 * @return java.lang.String
	 */
	private String getOperatorID() {
		return nc.ui.pub.ClientEnvironment.getInstance().getUser()
				.getPrimaryKey();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-2 12:42:41)
	 * 
	 * @return java.lang.String
	 */
	private String getPk_corp() {
		return nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
				.getPk_corp();
	}

	/**
	 * ��ѯ����
	 */
	private OrderUIQueryDlg getQryCondition() {
		return getQryCondition(null);
	}

	/**
	 * ��ѯ����
	 */
	private OrderUIQueryDlg getQryCondition(String strPkCorp) {
		if (m_query == null) {
			try {
				if (strPkCorp != null && !strPkCorp.trim().equals("")) {// ָ�����ݹ�˾
					m_query = new OrderUIQueryDlg(this, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000002")/* @res "����" */,
							getOperatorID(), strPkCorp, getModuleCode());
				} else {// ָ����˾Ϊ�գ���ȡ��ǰ��½��˾
					m_query = new OrderUIQueryDlg(this, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000002")/* @res "����" */,
							getOperatorID(), getPk_corp(), getModuleCode());
				}

				// �����Զ���������
				nc.ui.scm.pub.def.DefSetTool.updateQueryConditionClientUserDef(
						m_query, getPk_corp(), ScmConst.SC_Order,
						"sc_order.vdef", // ����ģ���е���ͷ���Զ�����ǰ׺
						"sc_order_b.vdef" // ����ģ���е�������Զ�����ǰ׺
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

		if (m_billState == ScConstants.STATE_CARD) {// ��Ƭ״̬�£�����BillListPanel�ĵ�ǰ����Ϊѡ��״̬
			arrOrderVO.add(getSelectedVO());
			return arrOrderVO;
		}

		try {
			OrderHeaderVO[] l_SelOrderHeaderVOs = (OrderHeaderVO[]) getBillListPanel()
					.getHeadBillModel().getBodySelectedVOs(
							"nc.vo.sc.order.OrderHeaderVO");

			if (l_SelOrderHeaderVOs == null || l_SelOrderHeaderVOs.length <= 0) {
				return null;
			} else if (l_SelOrderHeaderVOs.length == 1) { // �б����ֻѡ��һ����¼
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
					"401201", "UPP401201-000039")/* @res "����δ֪����" */);
		}
		return arrOrderVO;

	}

	/**
	 * ��������decimal�Ĺ���������BillListPanel��ʵ�����¼���nc.ui.pub.bill.
	 * IbillModelSortPrepareListener�� ���ߣ�yye ���ܣ��ӿ�IBillModelSortPrepareListener
	 * ��ʵ�ַ��� ������String sItemKey ITEMKEY ���أ��� ���⣺�� ���ڣ�(2004-03-24 11:39:21)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	 * �˴����뷽��˵���� �������ڣ�(2002-1-22 15:20:11)
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
	 * ��õ�ǰģ�����
	 * 
	 * @return String
	 * @exception
	 * @roseuid 3ADE500100C4
	 */
	public String getTitle() {
		String title = nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
				"UPP401201-000040")/* @res "ί��ӹ�����" */;
		if (m_BillCardPanel != null)
			title = m_BillCardPanel.getTitle();
		return title;

	}

	/**
	 * ÿ�������׳��쳣ʱ������
	 * 
	 * @param exception
	 *            java.lang.Throwable
	 */
	public void handleException(java.lang.Throwable exception) {

		/* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
		// SCMEnv.out("--------- δ��׽�����쳣 ---------");
	}

	/**
	 * ��ʼ�� *
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

		// V51�ع���Ҫ��ƥ��,��ťʵ��������
		createBtnInstances();

		// ���������к��Ҽ��˵�
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

		// �����в����Ҽ��˵���"��Ƭ�༭"
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

		// ҵ�����ʹ���
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

		// ��ʼ����������ɫ��
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

		// gc����һ��
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
	 * Ϊ�˼��ٳ�ʼ��ʱǰ��̨�����Ĵ�����һ���Ի�ȡ���ϵͳ���� ����:ԬҰ ���ڣ�2005-04-21
	 * 
	 */
	private void initpara() {
		try {
			Hashtable t = nc.ui.pub.para.SysInitBO_Client.queryBatchParaValues(
					getPk_corp(), new String[] { "SC04", "BD301" });
			if (t != null && t.size() > 0) {
				Object temp = null;
				if (t.get("SC04") != null) {
					// ��ȡ�۸����Ȳ���
					temp = t.get("SC04");
					if (temp.toString().trim().equals("��˰�۸�����")) {
						m_iPricePolicy = RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE;
					} else {
						m_iPricePolicy = RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE;
					}
				}

				if (t.get("BD301") != null) {
					// ȡ��Ĭ�ϱ���
					temp = t.get("BD301");
					m_sPKCurrencyType = temp.toString();
				}
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040502", "UPP40040502-000052")/*
																 * @res
																 * "��ȡϵͳ��ʼ����������"
																 */, e
					.getMessage());
		}
	}

	/**
	 * ��ť��ʼ��
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500102CD
	 */
	private void initButtons() {

		// ִ���鰴ť
		if (boAction.getChildCount() == 0) {
			boAudit.setTag("APPROVE");
			boUnaudit.setTag("UNAPPROVE");
			// boAction.addChildButton(boAudit);
			// boAction.addChildButton(boUnaudit);
		}

		if (m_billState == ScConstants.STATE_LIST) {
			setButtons(m_btnTree.getButtonArray());// �����б��鰴ť
			boReturn.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH021")/* @res "��Ƭ��ʾ" */);
		} else {
			setButtons(m_btnTree.getButtonArray());// ���ÿ�Ƭ�鰴ť
			boReturn.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH022")/* @res "�б���ʾ" */);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-8-1 13:05:32)
	 */
	private void initComboBox() {
		try {
			UIComboBox discountTaxType = (UIComboBox) getBillCardPanel()
					.getBillModel(TAB1).getItemByKey("idiscounttaxtype")
					.getComponent(); //
			// discountTaxType.addItem("Ӧ˰���");
			// discountTaxType.addItem("Ӧ˰�ں�");
			// discountTaxType.addItem("����˰");
			discountTaxType.addItem(ScConstants.TaxType_Including);// Ӧ˰�ں�
			discountTaxType.addItem(ScConstants.TaxType_Not_Including);// Ӧ˰���
			discountTaxType.addItem(ScConstants.TaxType_No);// ����˰
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("idiscounttaxtype").setWithIndex(true);
		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * �ɼ۸�����������ʱ���õĽ���
	 */
	private void processAfterChange(String sUpBillType,
			AggregatedValueObject[] voaSourceVO) {

		if (sUpBillType.equals(ScmConst.PO_PriceAudit)) {

			/** **************1���õ�ת�����OrderVO****************** */
			OrderVO[] voaRet = null;// ת�����OrderVO
			// -------------ѯ�۵�ת����
			// ������ҵ������+��Ӧ�̷ֵ�
			PriceauditMergeVO[] voaAskBill = (PriceauditMergeVO[]) SplitBillVOs
					.getSplitVOs(PriceauditMergeVO.class.getName(),
							PriceauditHeaderVO.class.getName(),
							PriceauditItemMergeVO.class.getName(),
							(AggregatedValueObject[]) voaSourceVO, null,
							new String[] { "cbiztype", "cvendormangid" });
			// ���ö���VO���飺ת�������н��б���ҵ�����͵���ͷҵ�����͵�ת��
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

			/** **************2����������߼�****************** */

			onAddFromRef(voaRet);

			nc.ui.scm.sourcebill.SourceBillTool.loadSourceInfoAll(
					getBillListPanel().getBodyBillModel(), ScmConst.SC_Order);

		}
	}

	/**
	 * ����˵������ʼ������ �������ڣ�(2001-10-25 16:05:08)
	 */
	private void initRefer() {

		// ��Ӧ������
		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("cvendorid")
				.getComponent();
		ref.setWhereString(" bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' and bd_cumandoc.frozenflag='N' and (bd_cumandoc.custflag='1' or bd_cumandoc.custflag='3' )");

		// �ջ���
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("creciever")
				.getComponent();
		ref.setWhereString(" bd_cumandoc.frozenflag='N' and  bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' AND (bd_cumandoc.custflag='0' OR bd_cumandoc.custflag='2') ");

		// ��Ʊ��
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("cgiveinvoicevendor")
				.getComponent();
		ref.setWhereString("  bd_cumandoc.frozenflag='N' and bd_cumandoc.pk_corp='"
				+ getPk_corp()
				+ "' AND (bd_cumandoc.custflag='1' OR bd_cumandoc.custflag='3') ");

		// �ɹ���֯
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("cpurorganization")
				.getComponent();
		ref.getRefModel().addWherePart(
				" and bd_purorg.ownercorp='" + getPk_corp() + "' ");

		// �ӹ�Ʒ
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cinventorycode").getComponent();
		ref.setWhereString(" bd_invmandoc.pk_corp='" + getPk_corp()
				+ "' and bd_invmandoc.sealflag='N' ");
		ref.setTreeGridNodeMultiSelected(true);
		ref.setMultiSelectedEnabled(true);
		// �ֿ�
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
		//gc ����
		// �ӹ�Ʒ
				ref = (UIRefPane) getBillCardPanel().getBillModel(TAB2)
						.getItemByKey("cinventorycode").getComponent();
				ref.setWhereString(" bd_invmandoc.pk_corp='" + getPk_corp()
						+ "' and bd_invmandoc.sealflag='N' ");
				ref.setTreeGridNodeMultiSelected(true);
				ref.setMultiSelectedEnabled(true);
				// �ֿ�
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

		// ��Ŀ
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cprojectname").getComponent();
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		// ref.setWhereString(" bd_jobmngfil.sealflag='N' and
		// bd_jobmngfil.pk_corp ='" + getPk_corp() + "' ");
		ref.setWhereString(" bd_jobmngfil.pk_corp ='" + getPk_corp() + "' ");
		// ����
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("ccurrencytype").getComponent();
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		// ��������λ
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
		// ��Ŀ�׶�
		ref = new UIRefPane();
		ref.setIsCustomDefined(true);
		// ref.setRefModel(new CTIPhaseRefModel());
		ref.setRefModel(new nc.ui.sc.pub.ProjectPhase());
		ref.setCacheEnabled(false);
		ref.setReturnCode(false);
		ref.setRefInputType(1);
		getBillCardPanel().getBillModel(TAB1).getItemByKey("cprojectphasename")
				.setComponent(ref);
		// //���̿�������
		// ref = new UIRefPane();
		// ref.setIsCustomDefined(true);
		// ref.setRefModel(new
		// nc.ui.bd.ref.busi.BankAccCustDefaultRefModel("���������˻�"));
		// ref.setCacheEnabled(false);
		// ref.setReturnCode(false);
		// ref.setRefInputType(0);
		//
		// String cvendorid =
		// getBillCardPanel().getHeadItem("cvendorid").getValue();
		//
		// // ȡ���ʻ���������������ID
		// ref.getRefModel().addWherePart(
		// " and bd_bankaccbas.pk_bankaccbas in (select k.pk_accbank from
		// bd_custbank k,bd_cumandoc m where"
		// + " m.pk_corp='"+getCorpPrimaryKey()+"'"// ָ����˾�Ŀ���
		// + " and k.pk_cubasdoc=m.pk_cubasdoc"// ���̵Ŀ����ʻ�
		// + " and k.pk_cubasdoc='"+cvendorid+"')");
		//
		// getBillCardPanel().getHeadItem("caccountbankid").setComponent(ref);

		// ��ͷ�����屸ע���������Զ���飩
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo").getComponent();
		ref.setAutoCheck(false);
		ref.setReturnCode(false);

		ref = (UIRefPane) m_BillCardPanel.getBodyItem("vmemo").getComponent();
		ref.setTable(getBillCardPanel().getBillTable(TAB1));// gc �¼�ҳ������
		ref.getRefModel().setRefCodeField(ref.getRefModel().getRefNameField());
		ref.getRefModel().setBlurFields(
				new String[] { ref.getRefModel().getRefNameField() });
		ref.setAutoCheck(false);

		// ��ͷ�����֯
		ref = (UIRefPane) m_BillCardPanel.getHeadItem("cwareid").getComponent();
		ref.setWhereString(" property <> 2 and pk_corp = '"
				+ getCorpPrimaryKey() + "'");

		BillData billData = m_BillCardPanel.getBillData();
		// ������
		FreeItemRefPane freeRef = new FreeItemRefPane();
		freeRef.setMaxLength(billData.getBodyItem(TAB1,"vfree0").getLength());
		billData.getBodyItem(TAB1,"vfree0").setComponent(freeRef);
		//gc
		FreeItemRefPane freeRef1 = new FreeItemRefPane();
		freeRef1.setMaxLength(billData.getBodyItem(TAB2,"vfree0").getLength());
		billData.getBodyItem(TAB2,"vfree0").setComponent(freeRef1);//gc
		//gc end
		// ��ʼ����ͬ�Ĳ���
		ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey(ScConstants.SC_ORDER_BODY_CT_CODE).getComponent();
		ref.setRefModel(new ValiCtRefModel(null, null, null, null, true));

		// ҵ������
		ref = (UIRefPane) getBillCardPanel().getHeadItem("cbiztype")
				.getComponent();
		ref.setRefModel(new ScBizTypeRefModel(
				PoPublicUIClass.getLoginPk_corp(), BillTypeConst.SC_ORDER,
				false));

		// ����
		ref = (UIRefPane) getBillCardPanel().getHeadItem("cdeptid")
				.getComponent();
		ScDeptRefModel refDeptModel = new ScDeptRefModel(getPk_corp());
		ref.setRefModel(refDeptModel);

		// ҵ��Ա(�ɹ����ŵ�)
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
		// ��ʹ�úϼ���
		getBillCardPanel().setTatolRowShow(true);
		// ���õ��ݿؼ�Ϊ���ɱ༭
		getBillCardPanel().setEnabled(false);

		cardLayout.first(this);

		setButtonsState();
		updateButtons();
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2002-1-25 12:32:59)
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
		// �޸��ˣ�renzhonghai �޸����ݣ����ղ�����Ҫ�ж�
		if (ref.getRefModel() != null) {
			ref.getRefModel().addWherePart(sWherePart);
		}
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-4-23 9:17:37)
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

			// ���㺬˰����
			loadCustBank();

			// �����Զ�����
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
			SCMEnv.out("ִ�й�ʽ[����ʱ" + (System.currentTimeMillis() - s1) + "]");/*
																			 * -=
																			 * notranslate
																			 * =
																			 * -
																			 */

			// ��ʾ��˰���
			OrderItemVO[] orderItemVO = (OrderItemVO[]) m_curOrderVO
					.getChildrenVO();
			for (int i = 0; i < orderItemVO.length; i++) {
				Integer idiscounttaxtype = orderItemVO[i].getIdiscounttaxtype();

				getBillCardPanel().getBillModel(TAB1).setValueAt(
						idiscounttaxtype, i, "idiscounttaxtype");

			}

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000253")/* @res "���ݼ��سɹ�" */);

			// ά������״̬�����ϡ��޸ģ�
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

			// �����ϲ��Դͷ������Ϣ
			nc.ui.scm.sourcebill.SourceBillTool.loadSourceInfoAll(
					getBillCardPanel().getBillModel(TAB1), ScmConst.SC_Order);
			// ������ʾ�ѷ��ÿ����֯
			execHeadTailFormula(m_curOrderVO);

		} catch (Exception e) {
			SCMEnv.out("���ݼ���ʧ��");
			SCMEnv.out(e);
		}

	}

	/**
	 * ��������� �������ڣ�(2001-9-28 11:09:44)
	 */
	private void loadFreeItems() {
		// ����������
		
		try {
			String []tabs = new String[]{TAB1,TAB2};
				for (int j = 0; j < tabs.length; j++) {
				// �ж��Ƿ���Ҫִ�й�ʽ,������������id��Ϊnull�����������Ϊnull����ִ�м��ع�ʽ add by hanbin
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
	
					// modify by hanbin 2009-9-11 ԭ��:��������ֻ��һ������ʱ���༭ʱ��������ʾ����
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
					"401201", "UPP401201-000022")/* @res "����������ʧ��" */);
			SCMEnv.out(e);
		}

	}

	/**
	 * ����������ʾ������ �������ڣ�(2001-9-17 17:30:05)
	 */
	private void loadMeasRate() {// gc ����Ҫ����
		try {
			String[] saBaseId = (String[]) PuGetUIValueTool.getArray(
					getBillCardPanel().getBillModel(TAB1), "cbaseid",
					String.class, 0, getBillCardPanel().getRowCount());
			String[] saAssistUnit = (String[]) PuGetUIValueTool.getArray(
					getBillCardPanel().getBillModel(TAB1), "cassistunit",
					String.class, 0, getBillCardPanel().getRowCount());

			// ��������
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
						// �������������ͬ����������ʾ1
						getBillCardPanel().setBodyValueAt("1", i, "measrate");
					}
					j++;
				}

				if (nordernum != null && nassistnum != null
						&& nordernum.toString().trim().length() != 0
						&& nassistnum.toString().trim().length() != 0) {
					UFDouble rate;
					// ����ǹ̶������ʣ���ȡ�̶������ʣ����� ������ = ������ / ������
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
	 * �˴����뷽��˵���� �������ڣ�(2002-1-21 14:09:17)
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
				if (PuPubVO.getString_TrimZeroLenAsNull(custVO.getCdeptid()) == null) {// ����Ӧ�����ܲ���Ϊ�գ���ȡת��������Ĭ�ϲ���
					headVO.setCdeptid(((OrderHeaderVO) orderVO.getParentVO())
							.getCdeptid());
				} else {
					headVO.setCdeptid(custVO.getCdeptid());
				}
				// headVO.setCemployeeid(custVO.getCpsnid());//comment by yye
				// 2005-03-23 �����ⲿ����ת��ʱ�������Զ�����ҵ��Ա������Դ���ݵ�ҵ��Ա
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
		// ����ת��״̬
		if (e.getPos() == 0 && m_billState == ScConstants.STATE_OTHER) {
			getBillCardPanel().stopEditing();
			setMaxMnyDigit(iMaxMnyDigit);
			onModify();
			getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);// �ù�굽��ͷ��һ���ɱ༭��Ŀ
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000072")/* @res "�༭����" */);
			return;
		}
		// ��ͨ�б�״̬
		if (e.getPos() == BillItem.HEAD) {
			onDoubleClick();

			// ���þ���
			setPrecision();
		} else if (m_billState == ScConstants.STATE_ADD
				|| m_billState == ScConstants.STATE_MODIFY) {
		}
	}

	/**
	 * �����µ���(���Ƶ���) *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001016E
	 */
	private void onAdd() {
		getBillCardPanel().addNew();
		getBillCardPanel().setEnabled(true);
		getBillCardPanel().setTatolRowShow(true);

		// ��ͷĬ����������ڡ��Ƶ��ˡ���˾��ҵ�����͡������ȣ�
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

			// since v51, ����ҵ��ԱĬ��ֵ ���ݲ���Ա����ҵ��Ա
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
				"UPP401201-000071")/* @res "���ӡ��༭����" */);
	}

	/**
	 * ����ת�������µ��� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001016E
	 */
	private void onAddFromRef(OrderVO[] VOs) {

		if (VOs == null || VOs.length == 0)
			return;

		// ת���б����
		// �кŴ���
		nc.ui.scm.pub.report.BillRowNo.setVOsRowNoByRule(VOs,
				ScmConst.SC_Order, "crowno");

		m_billState = ScConstants.STATE_OTHER;
		cardLayout.last(this);
		setButtons(m_btnTree.getButtonArray());// �����б��鰴ť
		updateUI();
		boEdit.setEnabled(true);
		setButtonsState();
		getBillListPanel().setState(m_billState);

		loadCustInfos(VOs);
		loadInvTaxRates(VOs);

		// ��ͷ������Ĭ��ֵ���������ڡ��Ƶ��ˡ������ȡ���˰�����״̬��
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
				// �����Ƿ񸨼���
				if (itemVO[j].getCassistunit() != null) {
					itemVO[j].setAttributeValue("isassist", "Y");
				}

				// ������������������
				UFDouble nordernum = itemVO[j].getNordernum();
				UFDouble nassistnum = itemVO[j].getNassistnum();
				if ((nordernum != null) && (nassistnum != null)) {
					itemVO[j].setAttributeValue("measrate",
							nordernum.div(nassistnum));
				}

				itemVO[j].setIdiscounttaxtype(new Integer(1));
				itemVO[j].setBisactive(UFBoolean.TRUE);
				// String cmangid = itemVO[j].getCmangid();
				// ���Ĭ���˰�ʣ�
				// ��������ַ(�ջ�Ϊ�գ�ȡ�ֿ�Ĭ�ϵ�ַ)
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
	 * �Ӻ�̨���ؿ�����Ϣ
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
	 * �Ӻ�̨���ش��˰��
	 * 
	 * @param VOs
	 */
	private void loadInvTaxRates(OrderVO[] VOs) {
		List<String> itemList = new ArrayList<String>();
		for (OrderVO orderVO : VOs) {
			OrderItemVO[] itemVO = (OrderItemVO[]) orderVO.getChildrenVO();
			// ���Ĭ���˰�� ���δ���
			for (int i = 0; i < itemVO.length; i++) {
				if (itemVO[i].getNtaxrate() == null) {
					itemList.add(itemVO[i].getCbaseid());
				}
			}
		}

		Map<String, UFDouble> taxRateMap = null;
		try {
			// Զ�̵��ò�ѯ�����˰��
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
	 * ����
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
				"UCH036")/* @res "���гɹ�" */);
	}

	/**
	 * ���������������������к�,��һЩֵ��Ԥ�ô��� <br>
	 * ��ԭ����onAppendLine�����г��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author zhaoyha
	 * @time 2008-8-14 ����04:14:14
	 */
	private void afterAppendLine() {
		// �кŴ���
		nc.ui.scm.pub.report.BillRowNo.addLineRowNo(getBillCardPanel(),
				ScmConst.SC_Order, "crowno");
		int row = getBillCardPanel().getRowCount() - 1;
		// ����Ĭ������ʡ���˰�����״̬��
		String tab = getSelectTab();
		if(tab.equals(TAB1)){
			getBillCardPanel().setBodyValueAt(new Integer(1), row,
					"idiscounttaxtype");
			getBillCardPanel().setBodyValueAt("100", row, "ndiscountrate");
			getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "bisactive");
		}

		try {
			// Ĭ�ϵ�����ַ
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

			// add by hanbin 2009-11-16 ԭ��:��Ƭ�༭�У�������У������ֶ���ʾpk������ʾ����
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().updateUI();
		} catch (Exception e) {
			SCMEnv.out(e);
			this.showErrorMessage(e.toString());
		}
	}

	/**
	 * ���� rowIndex �еı���ID��
	 * 
	 * ע�� ��л�����ƽ��壬���ۺ󣬰���������㷨���ñ���ID ��俨Ƭ����������ID���㷨 1������ͷ��Ӧ��Ϊ��ʱ�������� rowIndex
	 * �еı���ID���û��������и��ı��֡� 2������ͷ��Ӧ�̲�Ϊ��ʱ��������Ӧ��Ĭ�ϵı���ID�� ����Ӧ��û������Ĭ�ϱ���ʱ��������λ�ҡ�
	 * 
	 * @param rowIndex
	 */
	private void fillCurrencyTypeIdInCardBody(int rowIndex) {// gc ����Ҫ����
		try {
			// ������Ϊ�գ��������Ӧ��Ĭ�ϱ���
			String cvendormangid = (String) getCardHeadItemValue("cvendormangid");
			// �����Ӧ�̲�Ϊ�գ��Ž��б�������
			if (!StringUtil.isEmpty(cvendormangid)) {
				// ��ȡ��Ӧ��Ĭ�Ͻ��ױ���
				String currentyTypeId = getCurrencyTypeId(cvendormangid);
				if (StringUtil.isEmpty(currentyTypeId)) {
					// ��Ӧ��Ĭ�Ͻ��ױ���Ϊ�գ���ȡ��λ��
					getBillCardPanel().setBodyValueAt(m_sPKCurrencyType,
							rowIndex, "ccurrencytypeid");
				} else {
					// ���ù�Ӧ��Ĭ�Ͻ��ױ���
					getBillCardPanel().setBodyValueAt(currentyTypeId, rowIndex,
							"ccurrencytypeid");
				}
			}

			// ���ñ��ֻ��ʵ�Ԫ��Ŀɱ༭��
			setCurrencyExchangeRateEnable(rowIndex);

			// ���ñ��ֻ���
			setCurrencyExchangeRate(rowIndex);
		} catch (Exception e) {
			SCMEnv.out(e);
			this.showErrorMessage(e.toString());
		}
	}

	/**
	 * ��ȡ��Ӧ�̶�Ӧ�ı���ID
	 * 
	 * @param cvendormangid
	 *            ��Ӧ��ID
	 * @return ��Ӧ�̶�Ӧ�ı���ID
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
	 * ��ȡ��Ӧ��VO���棬��Map��ʽ��ţ�����Map������Ϊ��Ӧ�̹�����ID
	 * 
	 * @return ��Ӧ��VO����
	 */
	private Map<String, CustDefaultVO> getCustDefaultVOMap() {
		if (custDefaultVOMap == null) {
			custDefaultVOMap = new HashMap<String, CustDefaultVO>();
		}
		return custDefaultVOMap;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2001-7-26 12:50:18)
	 */
	public void onAudit() {
		try {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH048")/* @res "�������" */);

			OrderVO vo = (OrderVO) getBillCardPanel().getBillValueVO(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());

			// V31SP1:�����������ڲ���С���Ƶ���������
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
				throw new BusinessException("ҵ�����ͺ������Ϊ N�����������");
			}

			// ����
			Object[] retVOs = PfUtilClient.runBatch(this, "APPROVE",
					ScmConst.SC_Order, nc.ui.pub.ClientEnvironment
							.getInstance().getDate().toString(),
					new OrderVO[] { vo }, null, null, null);

			if ((!PfUtilClient.isSuccess()) || (retVOs == null)
					|| (retVOs.length > 1)) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000043")/*
													 * @res "ί�ⶩ������ʧ��"
													 */);
				return;
			} else {
				m_curOrderVO = (OrderVO) retVOs[0];
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000234")/* "�����ɹ�" */);
			}

			getModel().setHeaderAt(getModel().getCurrentIndex(),
					(OrderHeaderVO) m_curOrderVO.getParentVO());

			refreshBillCardPanel();

			setButtonsState();
		} catch (Exception e) {
			showErrorMessage(e.getMessage());
			SCMEnv.out(e);
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000043")/* @res "ί�ⶩ������ʧ��" */);
			return;
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000234")/* "�����ɹ�" */);
	}

	/**
	 * �б�������
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-4-13 ����11:08:08
	 */
	public void onAuditList() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000403")/* "������������" */);

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
						"401201", "UPP401201-000006")/* @res "��ѡ��Ҫ�����ĵ���" */);
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

			// V31SP1:�����������ڲ���С���Ƶ���������
			String strErr = PuTool.getAuditLessThanMakeMsg(VOs, "dorderdate",
					"vordercode", nc.ui.pub.ClientEnvironment.getInstance()
							.getDate());
			if (strErr != null) {
				throw new BusinessException(strErr);
			}

			// ���뵱ǰ����Ա����ҵ������
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
						"SCMCOMMON", "UPPSCMCommon-000235")/* "���ʧ��" */);
				return;
			}

			refreshSelectedRows(selectRows, VOs);
			setButtonsState();

			getBillListPanel().getHeadBillModel().execLoadFormula();

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000234")/* "�����ɹ�" */);
		} catch (Exception e) {
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000235")/*
																	 * @res
																	 * "���ʧ��"
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
	 * �б���ί�ⶩ������(����)
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-4-13 ����11:24:52
	 */
	public void onUnAuditList() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000407")/*
													 * @res "�������󵥾�"
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
						"401201", "UPP401201-000011")/* @res "��ѡ��Ҫ����ĵ���" */);
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
			 * if (selectRows.length == 0) { showWarningMessage("��ѡ��Ҫ����ĵ��ݣ�");
			 * return; }
			 */
			//
			OrderVO[] VOs = (OrderVO[]) getBillListPanel().getMultiSelectedVOs(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());

			// ���뵱ǰ����Ա����ҵ������
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
															 * @res "����δ�ɹ�"
															 */);
				return;
			}

			refreshSelectedRows(selectRows, VOs);
			setButtonsState();
			getBillListPanel().getHeadBillModel().execLoadFormula();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH011")/* @res "����ɹ�" */);
		} catch (Exception e) {
			SCMEnv.out(e);
			getBillListPanel().getParentListPanel().clearSelect();
			MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("SCMCOMMON", "UPPSCMCommon-000185")/*
																	 * @res
																	 * "����ʧ��"
																	 */, e
					.getMessage());
		}

	}

	/**
	 * �������� ��������: �������: ����ֵ: �쳣����: ����:
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
				"4004COMMON000000019")/* @res "����ɹ�" */);

	}

	/**
	 * ��Ӧ��ť�¼�
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
			getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);// �ù�굽��ͷ��һ���ɱ༭��Ŀ
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000072")/* @res "�༭����" */);
		} else if (btn.getParent() == boBusitype) {
			PfUtilClient.retAddBtn(boAdd, getPk_corp(), ScmConst.SC_Order, btn);
			setButtons(m_btnTree.getButtonArray());// ���ÿ�Ƭ�鰴ť
			getBillCardPanel().setBusiType(btn.getTag());
			boAdd.setEnabled(true);
			// ���ð�ť
			showSelBizType(btn);
			updateButtons();
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000065", null,
					new String[] { btn.getName() })/* @res "��ǰ����ҵ�����ͣ�" */);
		} else if (btn.getParent() == boAdd) {
			showHintMessage("��������...");
			String tag = btn.getTag();
			int index = tag.indexOf(":");
			String billType = tag.substring(0, index);
			String cBizTypeId = tag.substring(index + 1, tag.length());

			// ���û�������ҵ�����ͣ�UI�˵�VO���������õ�
			ClientEnvironment.getInstance().putValue(OrderPubVO.ENV_BIZTYPEID,
					tag.substring(index + 1, tag.length()));
			if (billType.equals("20")) {
				SourceRefDlg.childButtonClicked(btn, getPk_corp(),
						getModuleCode(), getOperatorID(), ScmConst.SC_Order,
						this);// �빺��
			} else {
				PfUtilClient.childButtonClicked(btn, getPk_corp(),
						getModuleCode(), getOperatorID(), ScmConst.SC_Order,
						this);
			}

			if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
				onAdd();
				getBillCardPanel().transferFocusTo(BillCardPanel.HEAD);// �ù�굽��ͷ��һ���ɱ༭��Ŀ
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
								VO, billType);// ���տ繫˾��ͬ ����µĹ�˾�䵵��IDת��
					} catch (BusinessException e) {
						MessageDialog
								.showHintDlg(
										this,
										nc.ui.ml.NCLangRes.getInstance()
												.getStrByID("SCMCOMMON",
														"UPPSCMCommon-000270")/*
																			 * @res
																			 * "��ʾ"
																			 */,
										e.getMessage());
						showHintMessage(e.getMessage());
						return;
					}

					// ���ò�������VO����VO��ҵ������ID����ҵ������ID��
					setCbizTypeId(VO, cBizTypeId);
					onAddFromRef(VO);
				}
			}
			initButtons();
		} else if (btn == boCopy) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000350")/* "�༭����..." */);
			onCopy();
		} else if (btn == boDel) {
			onDiscard();
		} else if (btn == boSave) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000134")/* @res "��ʼ����" */);
			onSave();
		} else if (btn == boCancel) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000205")/* @res "ȡ������" */);
			onCancel();
		} else if (btn == boAddLine) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000465")/* @res "����������" */);
			onAppendLine();
		} else if (btn == boDelLine) {
			onDelLine();
		} else if (btn == boCopyLine) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH059")/* @res "���ڸ�����" */);
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
						"401201", "UPP401201-000066")/* @res "��ѡ����" */);
			}
		} else if (btn == boPasteLine) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"common", "UCH060")/* @res "����ճ����" */);
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
					"common", "UCH023")/* @res "������" */);
		} else if (btn == m_btnPrintPreview) {
			onPrintPreview();
		} else if (btn == boQuery) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000360")/* "��ʼ��ѯ..." */);
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
		} else if (btn == boAudit || btn.getCode().equals("����")) {/*
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
			String tab = getSelectTab();// gc,�������п��ƾ��ȵĻ����¼�
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
	 * ����ҵ������ID <b>����˵��</b>
	 * 
	 * @param vos
	 * @param cBizTypeId
	 * @author songhy
	 * @time 2009-7-17 ����12:26:00
	 */
	private void setCbizTypeId(OrderVO[] vos, String cBizTypeId) {
		if (!VOChecker.isEmpty(vos)) {
			for (OrderVO item : vos) {
				((OrderHeaderVO) item.getParentVO()).setCbiztype(cBizTypeId);
			}
		}
	}

	/**
	 * �ָ�������յ����ݣ���Ϊȡ��ǰ�������˲��մ�������ͬ�Ĳɹ�����
	 */
	private void resetInvmandocRefModel() {
		UIRefPane ref = (UIRefPane) getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("cinventorycode").getComponent();
		ref.setPk_corp(getPk_corp());
		((InvmandocDefaultRefModel) ref.getRefModel()).addWherePart(null);
	}

	/**
	 * ȡ������ *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	public void onCancel() {
		try {
			// �����ͷ��ע
			getBillCardPanel().resumeValue();
			((UIRefPane) getBillCardPanel().getHeadItem("vmemo").getComponent())
					.setText(m_sHeadVmemo);

			resetInvmandocRefModel();
			getBillCardPanel().setEnabled(false);

			// 2009-9-28 renzhonghai ���չ����ĵ������û��ҵ���������ܼ��������Ժ�Ĳ������ָ���ʼ״̬
			getBillCardPanel().getHeadItem("cbiztype").setEdit(false);
			getBillCardPanel().getHeadItem("cbiztype").setNull(false);
			setCntRelatedItemEditableInCardHead();

			if (comeVOs == null || comeVOs.size() == 0)
				m_billState = ScConstants.STATE_CARD;

			boEdit.setEnabled(true);
			// ���ð�ť״̬
			setButtonsState();
			getBillListPanel().setState(m_billState);

			// ����ת��ȡ��,���б������ݣ��ص��б����
			if (m_billState == ScConstants.STATE_OTHER) {
				cardLayout.last(this);
				setButtons(m_btnTree.getButtonArray());// �����б��鰴ť
				updateUI();
				setButtonsState();
				getBillListPanel().loadBodyData(0);
				// add by hanbin ԭ��ת��ʱ�����ȡ����ť��Ĭ��ѡ���б��µĵ�һ����¼
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
				"UCH008")/* "ȡ���ɹ�" */);

	}

	/**
	 * ȡ������ *
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
	 * �л�����Ƭ���� �������ڣ�(2001-4-25 10:31:02)
	 */
	private void onCard() {
		// ����ת��
		if (m_billState == ScConstants.STATE_OTHER) {
			int rowindex = getBillListPanel().getHeadTable().getSelectedRow();
			if (rowindex == -1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000044")/* @res "��ѡ��Ҫ�޸ĵĵ���" */);
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
						"401201", "UPP401201-000045")/* @res "��ѡ��һ�ŵ���" */);
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
				"UCH021")/* @res "��Ƭ��ʾ" */);

	}

	/**
	 * �˴����뷽��˵���� ��������:���ƶ��� �������: ����ֵ: �쳣����:
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
			Vector vTemp = new Vector();// ������
			for (int i = 0; i < itemVO.length; i++) {
				itemVO[i].setCorderid(null);
				itemVO[i].setCorder_bid(null);
				itemVO[i].setCupsourcebillid(null);
				itemVO[i].setCupsourcebillrowid(null);
				itemVO[i].setCupsourcebilltype(null);
				itemVO[i].setCsourcebillid(null);
				itemVO[i].setCsourcebillrow(null);
				itemVO[i].setCordersource(null);
				// ����ۼƻ�д��Ϣ
				itemVO[i].setNaccumarrvnum(null);
				itemVO[i].setNaccuminvoicenum(null);
				itemVO[i].setNaccumstorenum(null);
				itemVO[i].setNaccumwastnum(null);
				// ��չ�����Ϣ
				itemVO[i].setCcontractid(null);
				itemVO[i].setCcontractrcode(null);
				itemVO[i].setCcontractrowid(null);

				vTemp.addElement(getBillCardPanel().getBillModel(TAB1).getValueAt(i,
						"measrate"));
			}
			orderVO.setChildrenVO(itemVO);
			
			//gc �ϱ�ҳ
			OrderDdlbVO[] ddlbVO = (OrderDdlbVO[]) orderVO.getDdlbvos();
//			Vector vTemp_ddlb = new Vector();// ������
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
				// ����ۼƻ�д��Ϣ
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
			// ��������
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

			// ִ�й�ʽ
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().getBillModel(TAB2).execLoadFormula();

			// �����˰�����ʾ
			for (int i = 0; i < itemVO.length; i++) {
				Integer idiscounttaxtype = itemVO[i].getIdiscounttaxtype();

				getBillCardPanel().getBillModel(TAB1).setValueAt(idiscounttaxtype, i,
						"idiscounttaxtype");
				//
				// ��������λ
				String formula = "isassist->getColValue(bd_invbasdoc,assistunit,pk_invbasdoc,cbaseid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(i,
						new String[] { formula });
				// ������
				BillEdit.editFreeItem(getBillCardPanel(),TAB1, i, "cinventorycode",
						"cbaseid", "cmangid");

				// ������
				getBillCardPanel().setBodyValueAt(vTemp.elementAt(i), i,
						"measrate",TAB1);
			}
			for (int j = 0; ddlbVO != null && j < ddlbVO.length; j++) {
				// ������
				BillEdit.editFreeItem(getBillCardPanel(),TAB2, j, "cinventorycode",
						"cbaseid", "cmangid");
			}
			// 2009-9-21 renzhonghai Ϊ���ṩЧ�ʣ����������ᵽ��ѭ��ͳһ����
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
				"UCH029")/* @res "�Ѹ���" */);
	}

	/**
	 * gc�ϱ�ҳ �˴����뷽��˵���� ��������:���ƶ��� �������: ����ֵ: �쳣����:
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
			Vector vTemp = new Vector();// ������
			for (int i = 0; i < itemVO.length; i++) {
				itemVO[i].setCorderid(null);
				itemVO[i].setCorder_bid(null);
				itemVO[i].setCupsourcebillid(null);
				itemVO[i].setCupsourcebillrowid(null);
				itemVO[i].setCupsourcebilltype(null);
				itemVO[i].setCsourcebillid(null);
				itemVO[i].setCsourcebillrow(null);
				itemVO[i].setCordersource(null);
				// ����ۼƻ�д��Ϣ
				itemVO[i].setNaccumarrvnum(null);
				itemVO[i].setNaccuminvoicenum(null);
				itemVO[i].setNaccumstorenum(null);
				itemVO[i].setNaccumwastnum(null);
				// ��չ�����Ϣ
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
			// ��������
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

			// ִ�й�ʽ
			getBillCardPanel().getBillModel(TAB1).execLoadFormula();

			// �����˰�����ʾ
			for (int i = 0; i < itemVO.length; i++) {
				Integer idiscounttaxtype = itemVO[i].getIdiscounttaxtype();

				getBillCardPanel().setBodyValueAt(idiscounttaxtype, i,
						"idiscounttaxtype");
				//
				// ��������λ
				String formula = "isassist->getColValue(bd_invbasdoc,assistunit,pk_invbasdoc,cbaseid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(i,
						new String[] { formula });
				// ������
				BillEdit.editFreeItem(getBillCardPanel(), i, "cinventorycode",
						"cbaseid", "cmangid");

				// ������
				getBillCardPanel().setBodyValueAt(vTemp.elementAt(i), i,
						"measrate");
			}
			// 2009-9-21 renzhonghai Ϊ���ṩЧ�ʣ����������ᵽ��ѭ��ͳһ����
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
				"UCH029")/* @res "�Ѹ���" */);
	}

	/**
	 * ���Ƶ��� *
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

		getBillCardPanel().copyLine();//gc����������ǰ�ѡ���и���
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH039")/* @res "�����гɹ�" */);
	}

	/**
	 * ɾ�� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001022C
	 */
	private void onDelLine() {
		String tab = getSelectTab();// gc
		// ���û������ֱ�ӷ���V55
		if (getBillCardPanel().getRowCount() == 0)
			return;

		// ���ֻ��һ��������ɾ��V55
		if (getBillCardPanel().getRowCount() == getBillCardPanel()
				.getBillTable(tab).getSelectedRows().length) {
			showErrorMessage(NCLangRes.getInstance().getStrByID("scmcommon",
					"UPPSCMCommon-000489")
			/* ����ɾ�������е�ȫ���У� */
			);
			return;
		}

		int allDelRows[] = getBillCardPanel().getBillTable(tab)
				.getSelectedRows();
		if (allDelRows.length == 0) {
			MessageDialog.showHintDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000013")/* "ɾ��" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
							"UPP401201-000067")/* "û��ѡ�ж��������У�" */);
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
				"UCH037")/* @res "ɾ�гɹ�" */);

	}

	/**
	 * ���ϵ��� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101A0
	 */
	private void onDiscard() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
				"UPP401201-000068")/* "���ϵ���" */);

		if (m_billState == ScConstants.STATE_LIST) {
			int lint_CurBillIndex = getBillListPanel().getHeadTable()
					.getSelectedRow();
			getModel().setCurrentIndex(
					nc.ui.pu.pub.PuTool.getIndexBeforeSort(getBillListPanel(),
							lint_CurBillIndex));
			if (getModel().getCurrentIndex() == -1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000047")/* @res "��ѡ��Ҫ���ϵĵ���" */);
				return;
			}

			int lint_RowCount = getBillListPanel().getHeadTable()
					.getSelectedRowCount();
			if (lint_RowCount > 1) {
				showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000045")/* @res "��ѡ��һ�ŵ���" */);
				return;
			}
		}

		int ret = MessageDialog.showYesNoDlg(
				this,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000219")/* @res "ȷ��" */,
				nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
						"UPPSCMCommon-000224")/* @res "ȷ��Ҫ���ϸü�¼��" */,
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
													 * @res "�õ����Ѿ�����������ˢ�½���"
													 */);
				return;
			} else if (i == BillStatus.DELETED) {
				showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401201", "UPP401201-000042")/*
													 * @res "�õ����Ѿ���ɾ������ˢ�½���"
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

		// �б��������õ���
		if (m_billState == ScConstants.STATE_LIST) {
			String curpk = getModel().getCurrentHeaderId();
			int listnum = getBillListPanel().getHeadTable().getRowCount();
			for (int i = 0; i < listnum; i++) {
				String billpk = getBillListPanel().getHeadBillModel()
						.getValueAt(i, "corderid").toString();
				if (billpk.equals(curpk)) {
					// ��ǰ���ݼ���������õ���
					getModel().deleteCurrentHeader();
					getBillListPanel().getHeadBillModel().delLine(
							new int[] { i });
					getBillListPanel().getBillListData().clearCopyData();

					int curRow = (i == listnum - 1) ? i = listnum - 2 : i;
					if (curRow == -1) { // ������û�����ݣ��������ݶ��Ѿ�ɾ��
						clearList(); // ����б��������
						boReturn.setEnabled(true);
						updateButtons();
						this.m_curOrderVO = null; // �����ǰ����
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
		} else if (m_billState == ScConstants.STATE_CARD) { // ��Ƭ״̬����õ���
			// ��ǰ���ݼ���������õ���
			getModel().deleteCurrentHeader();

			if (getModel().getCurrentIndex() < 0) {
				getBillCardPanel().getBillData().clearViewData();

				// ˢ�°�ť
				setButtonsState();
				m_curOrderVO = null; // �����ǰ����
				return;
			}
			onList();
			setPgUpDownButtonsState(getModel().getCurrentIndex());
		}

		// ˢ�°�ť
		setButtonsState();

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000068")/* @res "���ϳɹ�" */);
	}

	/**
	 * ��ȡ��ѡ���VO
	 * 
	 * @return
	 * @author songhy
	 */
	private OrderVO getSelectedVO() {
		if (m_billState == ScConstants.STATE_CARD) {
			// ��Ƭ״̬�µĵ�ǰVO
			m_curOrderVO = (OrderVO) getBillCardPanel().getBillValueVO(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());
		} else {
			// �б�״̬�µĵ�ǰVO�����ѡ�����ʱ��ֻѡ���һ��
			m_curOrderVO = (OrderVO) getBillListPanel().getSelectedVO(
					OrderVO.class.getName(), OrderHeaderVO.class.getName(),
					OrderItemVO.class.getName());
			m_curOrderVO.setChildrenVO(getBillListPanel().getBodyBillModel()
					.getBodyValueVOs(OrderItemVO.class.getName()));
		}

		return m_curOrderVO;
	}

	/**
	 * ���� ���ĵ����� ���õ���״̬������Ƭ����������б������ ���ߣ������ ���ڣ�2003-03-19
	 */
	private void onDocument() {
		String[] strPks = null;
		String[] strCodes = null;
		HashMap mapBtnPowerVo = new HashMap();
		Integer iBillStatus = null;

		try {
			// ��Ƭ
			if (m_billState == ScConstants.STATE_CARD) {
				if (m_curOrderVO == null)
					return;
				strPks = new String[] { m_curOrderVO.getParentVO()
						.getPrimaryKey() };
				strCodes = new String[] { ((OrderHeaderVO) m_curOrderVO
						.getParentVO()).getVordercode() };

				// �����ĵ������ɾ����ť�Ƿ����
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
				// �����ĵ�����Ի���
				nc.ui.scm.file.DocumentManager.showDM(this, ScmConst.SC_Order,
						strPks, mapBtnPowerVo);

				return;

			}

			// �б�
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
			// �����ĵ�����Ի���
			nc.ui.scm.file.DocumentManager.showDM(this, ScmConst.SC_Order,
					strPks);
		} catch (Exception e) {
			SCMEnv.out(e);
			if (e instanceof BusinessException)
				MessageDialog.showErrorDlg(
						this,
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "����"
																	 */,
						e.getMessage());
			else
				MessageDialog.showErrorDlg(
						this,
						nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"SCMCOMMON", "UPPSCMCommon-000059")/*
																	 * @res "����"
																	 */,
						nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
								"UPP401201-000007")/*
													 * @res "�ĵ��������"
													 */);
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000025")/* @res "�ĵ�����ɹ�" */);
	}

	/**
	 * ��ͨ�б�״̬˫���б��� �������ڣ�(2001-4-25 10:31:02)
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
	 * ���� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010150
	 */
	private void onFirstPage() {
		getModel().setCurrentIndex(0);

		if (getModel().size() == 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000009")/* @res "û����������������" */);
			getBillCardPanel().getBillData().clearViewData();
			return;
		}
		loadData(null);
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000026")/* @res "�ɹ���ʾ��ҳ" */);
	}

	/**
	 * ˢ���б� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001018C
	 */
	private void onFresh() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000360")/* "��ʼ��ѯ..." */);

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
																 * "��ѯ��ϣ����� "
																 * +iCnt + "�ŵ���"
																 */);
			} else if (m_billState == ScConstants.STATE_LIST) {
				clearList();
				boReturn.setEnabled(true);
				updateButtons();
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000361")/* "��ѯ��ϣ�û�в鵽����" */);
			}

		} catch (Exception e) {
			SCMEnv.out("�б��ͷ���ݼ���ʧ��");
			SCMEnv.out(e);
		}
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH007")/* @res "ˢ�³ɹ�" */);
	}

	/**
	 * 
	 * �������ܣ��ж�ĳ���ֶ��Ƿ������Զ�����
	 * <p>
	 * 
	 * @param itemKey
	 *            �ֶ�key
	 * @param prefix
	 *            ǰ׺�ַ���
	 * @return ����ǣ�����true�����򣬷���false
	 *         <p>
	 * @author duy
	 * @time 2009-7-9 ����02:45:14
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
	 * ��������:�Զ������PK(����)
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
	 * ��������:�Զ������PK(��ͷ)
	 */
	private void setHeadDefPK(BillEditEvent event) {
		String itemKey = event.getKey();
		if (isUserDefinedField(itemKey, "vdef")) {
			DefSetTool.afterEditHead(getBillCardPanel().getBillData(), itemKey,
					"pk_defdoc" + itemKey.substring(4));
		}
	}

	/**
	 * ���Ƶ��� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010178
	 */
	private void onInsertLine() {
		getBillCardPanel().insertLine();
		// �кŴ���
		nc.ui.scm.pub.report.BillRowNo.insertLineRowNo(getBillCardPanel(),
				ScmConst.SC_Order, "crowno");
		String tab = getSelectTab();// gc
		int row = getBillCardPanel().getBillTable(tab).getSelectedRow();
		if (row < 0) {
			MessageDialog.showHintDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UC001-0000016")/* "������" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
							"UPP401201-000066")/* @res "��ѡ����" */);
			return;
		}
		// gc
		if (tab.equals(TAB1)) {
			// ����Ĭ������ʡ���˰���
			getBillCardPanel().setBodyValueAt(new Integer(1), row,
					"idiscounttaxtype");
			getBillCardPanel().setBodyValueAt("100", row, "ndiscountrate");
			getBillCardPanel().setBodyValueAt(UFBoolean.TRUE, row, "bisactive");

			// ��������Ĭ������֡����ʡ�˰�ʣ�
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

			// Ĭ�ϵ�����ַ
			try {
				String sReceiver = (String) getBillCardPanel().getHeadItem(
						"creciever").getValueObject();

				if (!StringUtil.isEmptyWithTrim(sReceiver)) {
					// ����ID
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
		// ���� row �еı���ID
		fillCurrencyTypeIdInCardBody(row);

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH038")/* @res "�����гɹ�" */);
	}

	/**
	 * ���Ƶ��� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010178
	 */
	private void onInsertLines(int iBeginRow, int iEndRow, int iInsertCount) {
		String tab = getSelectTab();// gc
		if (getBillCardPanel().getBillTable(tab).getSelectedRowCount() <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000049")/* @res "����ǰ����ѡ�������" */);
			return;
		}

		// ����ǰ��ȡ�õ�����ַ��Ĭ�ϱ���
		// Ĭ�ϵ�����ַ
		String defaddr = null;
		String defcurr = null;
		try {
			String sReceiver = (String) getBillCardPanel().getHeadItem(
					"creciever").getValueObject();
			if (StringUtil.isEmpty(sReceiver)) {
				// ����ID
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
				// �����²��е�Ĭ��ֵ
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
		// �����к�
		nc.ui.scm.pub.report.BillRowNo.insertLineRowNos(getBillCardPanel(),
				ScmConst.SC_Order, "crowno", iFinalEndRow, iInsertCount);
	}

	/**
	 * ĩ�� *
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
				"4004COMMON000000029")/* @res "�ɹ���ʾĩҳ" */);

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

		// ��λ
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
				"UCH022")/* @res "�б���ʾ" */);
	}

	/**
	 * ��λ * ֻ�б��²��ܶ�λ
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010114
	 */
	private void onLocate() {
		// ʵ����λ�Ի���
		nc.ui.scm.pub.report.OrientDialog dlgOrient = new nc.ui.scm.pub.report.OrientDialog(
				this, getBillListPanel().getBodyBillModel(), getBillListPanel()
						.getBillListData().getBodyItems(), getBillListPanel()
						.getBodyTable());
		//
		dlgOrient.showModal();
	}

	/**
	 * ���ߣ� ���ܣ�������帡���˵� �������� ���أ��� ���⣺�� ���ڣ�(2001-4-18 13:24:16)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public void onMenuItemClick(java.awt.event.ActionEvent event) {
		setMaxMnyDigit(iMaxMnyDigit);
		getBillCardPanel().removeEditListener();
		// �õ������˵�
		UIMenuItem menuItem = (UIMenuItem) event.getSource();

		if (menuItem.equals(getBillCardPanel().getAddLineMenuItem())) {
			onAppendLine();
		} else if (menuItem.equals(getBillCardPanel().getCopyLineMenuItem())) {
			onCopyLine();
		} else if (menuItem.equals(getBillCardPanel().getDelLineMenuItem())) {
			onDelLine();
		} else if (menuItem.equals(getBillCardPanel().getInsertLineMenuItem())) {
			onInsertLine();

			// add by hanbin 2009-11-16 ԭ��:�Ҽ�ѡ����룬�����ֶ���ʾpk������ʾ����
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
	 * �޸ĵ��� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001018C
	 */
	private void onModify() {
		try {
			// ����ת���޸�
			if (m_billState == ScConstants.STATE_OTHER) {
				int rowindex = getBillListPanel().getHeadTable()
						.getSelectedRow();
				if (rowindex == -1) {
					showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("401201", "UPP401201-000044")/*
																	 * @res
																	 * "��ѡ��Ҫ�޸ĵĵ���"
																	 */);
					return;
				}

				refLoadData(rowindex);

				String []tabs = new String[]{TAB1,TAB2};
				for (int j = 0; j < tabs.length; j++) {
					// ��ȡ���������ID
					String[] invMangIdArray = getInvMangIdArrayFromView(tabs[j]);
					// �����������κţ����Ч��
					InvTool.loadBatchProdNumMngt(invMangIdArray);
					for (int i = 0; i < getBillCardPanel().getBillModel(tabs[j]).getRowCount(); i++) {
						// ���κŵĿɱ༭��
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

				// since v51, ����ҵ��ԱĬ��ֵ ���ݲ���Ա����ҵ��Ա
				setDefaultValueByUser();

				/*
				 * ת��ʱ���Զ�ȡ�ۺ͹�����ͬ����.
				 * ע��:��ͬ�ڲɹ�����,ί�ⶩ��ת��ʱ���Զ�ȡ�ۺ͹�����ͬ����.�������޸�ʱ,��Ϊ����������һЩ.
				 * �ɹ���������ش���������޸�ǰ���б�״̬�����
				 * ,�ɲο������:nc.ui.po.pub.PoChangeUI.setVOCntRelated()
				 */
				setRelateCntAndDefaultPriceAllRow(false);

				// ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
				setNotEditableWhenRelateCntAllRow();
				setCntRelatedItemEditableInCardHead();
				setPrecision();

				return;
			}
			// ��ͨ�����޸�
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
																	 * "��ѡ��Ҫ�޸ĵĵ���"
																	 */);
					return;
				}

				cardLayout.first(this);
				setButtons(m_btnTree.getButtonArray());// ���ÿ�Ƭ�鰴ť

				loadData(null);
				String []tabs = new String[]{TAB1,TAB2};
				for (int j = 0; j < tabs.length; j++) {
					for (int i = 0; i < getBillCardPanel().getBillModel(tabs[j]).getRowCount(); i++) {
						// ���κŵĿɱ༭��
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
						// ���κ����
						getBillCardPanel().getBillModel(tabs[j]).setValueAt(null, i, "vproducenum");
					}
				}

			}

			// �����˵��Ҽ�����Ȩ�޿���
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
			// ���κŵĿɱ༭��
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

		// ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
		setNotEditableWhenRelateCntAllRow();
		setCntRelatedItemEditableInCardHead();

		getBillCardPanel().updateValue();
		cardLayout.first(this);
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000030")/* @res "�����޸�" */);

	}

	/**
	 * ��һ�� *
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
				"4004COMMON000000028")/* @res "�ɹ���ʾ��һҳ" */);
	}

	/**
	 * ���ܣ�����ճ����
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE5001022C
	 */
	private void onPasteLine() {
		String tab = getSelectTab();// gc
		int row = getBillCardPanel().getBillTable(tab).getSelectedRow();
		// ���δѡ����,��ճ������β
		// V55 ������ 2008.8.28
		if (row < 0) {
			onPasteLineToTail();
			return;
		}
		// �кŴ���
		int oldRow = getBillCardPanel().getBillTable(tab).getRowCount();

		getBillCardPanel().pasteLine();

		int newRow = getBillCardPanel().getBillTable(tab).getRowCount();
		if (newRow >= oldRow) {
			nc.ui.scm.pub.report.BillRowNo.pasteLineRowNo(getBillCardPanel(),
					ScmConst.SC_Order, "crowno", newRow - oldRow);
		}

		// ��ճ���е�corder_bid����Դ��Ϣ�ÿ�
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
				"UCH040")/* @res "ճ���гɹ�" */);
	}

	/**
	 * �����Ƭ�������� rowIndex �е���Դ��Դͷ��Ϣ
	 * 
	 * @param rowIndex
	 *            �б�
	 */
	private void clearSourceInfoInCardBody(int rowIndex) {// gc
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CORDERSOURCE); // ��Դ��������
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLID); // ��Դ����ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLROW); // ��Դ������ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CUPSOURCEBILLTYPE); // �ϲ㵥������
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CUPSOURCEBILLID); // �ϲ���Դ����ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CUPSOURCEBILLROWID); // �ϲ���Դ������ID
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLNAME); // ��Դ��������
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLCODE); // ��Դ���ݺ�
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CSOURCEBILLROWNO); // ��Դ�����к�
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CANCESTORBILLNAME); // Դͷ��������
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CANCESTORBILLCODE); // Դͷ���ݺ�
		getBillCardPanel().setBodyValueAt("", rowIndex,
				BillBodyItemKeys.CANCESTORBILLROWNO); // Դͷ�����к�
	}

	/**
	 * ���ܣ�����ճ���е���β
	 * 
	 */
	public void onPasteLineToTail() {
		// ճ��ǰ������
		int iOrgRowCount = getBillCardPanel().getRowCount();
		getBillCardPanel().pasteLineToTail();
		// ���ӵ�����
		int iPastedRowCount = getBillCardPanel().getRowCount() - iOrgRowCount;
		// �����к�
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
	 * ��һ�� *
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
				"4004COMMON000000027")/* @res "�ɹ���ʾ��һҳ" */);
	}

	/**
	 * ����������������ӡ����
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-3-19 ����03:40:12
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
																		 * "��ʾ"
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
					// "UPPSCMCommon-000270")/* @res "��ʾ" */,
					// printList.getPrintMessage());
					showHintMessage(printList.getPrintMessage());
				}
			} catch (BusinessException e) {
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"SCMCOMMON", "UPPSCMCommon-000061")/* "��ӡ����" */);
				SCMEnv.out(e);
			}
		}
	}

	/**
	 * ����������������ӡԤ��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-3-19 ����03:39:51
	 */
	private void onPrintPreview() {
		// 2009-9-22 renzhonghai �Ľ�Ч�ʺ͵�һ�δ�Ҫ�õ�ѡ�еĵ�������
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
	 * ��ѯ *
	 * 
	 * @return AggregatedValueObject[]
	 * @exception
	 * @roseuid 3ADE500100B0
	 */
	private void onQuery() {
		// ����ת��
		if (m_billState == ScConstants.STATE_OTHER) {
			return;
		}
		// ��ͨ��ѯ
		try {
			getQryCondition().showModal();
			if (!getQryCondition().isCloseOK())
				return;

			// ��������Ȩ��:���ڲ�ѯ�Ի����showModal()֮����Ϊ��ʱ������Ȩ��Ҫȡ���ڶ๫˾ҳǩ��ѡ��Ĺ�˾. lixiaodong
			// , v51
			m_query.setRefsDataPowerConVOs(nc.ui.pub.ClientEnvironment
					.getInstance().getUser().getPrimaryKey(),
					new String[] { nc.ui.pub.ClientEnvironment.getInstance()
							.getCorporation().getPrimaryKey() }, new String[] {
							"���ŵ���", "�ɹ���֯", "��Ӧ�̵���", "��Ӧ�̵���", "�ͻ�����", "�����֯",
							"��Ա����", "�������", "�������" }, new String[] {
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

			// ��ѯ����vo
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
				// ���ð�ť״̬
				setButtonsState();
				showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
						"401200", "UPP401200-000012")/* @res "û�з�������������" */);
				return;
			}

			// add by hanbin 2009-10-22 ԭ�򣺿���ˢ�°�ť��״̬
			// �ѡ��Ѿ���ѯ������Ϊtrue
			m_bQueried = true;

			if (m_billState == ScConstants.STATE_CARD) {
				onList();
			} else {
				getBillListPanel().setHeaderValueVO(orderHeaderVO);
				getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);

				// ɾ�����۶����Ƴɵ�ί�ⶩ�������У��򾫶ȵ���oldnum��nordernum��һ�£��ʻ�д�������󣬵������۶�������ʧ��
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
			// ���ð�ť״̬
			setButtonsState();
			SCMEnv.out("���ݼ���ʧ��");
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000038")/* @res "��ѯʧ��" */);
			SCMEnv.out(e);
		}

		m_bQueried = true;
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH009")/* @res "��ѯ���" */);
	}

	/**
	 * �Ե�ǰ���ݽ��кϲ���ʾ�����ɴ�ӡ �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private void onBillCombin() {
		CollectSettingDlg dlg = new CollectSettingDlg(this, nc.ui.ml.NCLangRes
				.getInstance().getStrByID("401201", "UPT401201-000033")/*
																		 * @res
																		 * "�ϲ���ʾ"
																		 */,
				ScmConst.SC_Order, "401201", getPk_corp(), getOperatorID(),
				OrderVO.class.getName(), OrderHeaderVO.class.getName(),
				OrderItemVO.class.getName());

		// �ѱ�ͷ�ġ���ע���ֶ��޸�Ϊ�ַ������ͣ�����ϲ���ʾʱ����֡���ע���ֶ�Ϊ�� add by hanbin 2009-11-16
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
				"cinventoryname", "invspec", "invtype", "ccurrencytype" }, // �̶�������
				null, // ȱʡ������
				new String[] { "noriginalcurmny", "noriginaltaxmny",
						"nordernum", "noriginalsummny" }, // �����
				null, // ��ƽ����
				new String[] { "noriginalcurprice", "noriginalnetprice" }, // ���Ȩƽ����
				"nordernum" // ������
		);
		dlg.showModal();

		// �ϲ���ʾ��󣬰ѱ�ͷ�ġ���ע���ֶλָ�Ϊ�������� add by hanbin 2009-11-16
		if (tempCom != null) {
			getBillCardPanel().getHeadItem("vmemo").setDataType(BillItem.UFREF);
			getBillCardPanel().getHeadItem("vmemo").setComponent(tempCom);
		}
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000039")/* @res "�ϲ���ʾ���" */);
	}

	/**
	 * ��ѯ��ǰ��������״̬
	 */

	private void onQueryForAudit() {
		if (getModel().size() <= 0 || getModel().getCurrentIndex() == -1) {
			showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000010")/* @res "����ѡ��Ҫ��ѯ״̬�ĵ���" */);
			return;
		}

		String corderid = getModel().getCurrentHeaderId();
		nc.ui.pub.workflownote.FlowStateDlg approvestatedlg = new nc.ui.pub.workflownote.FlowStateDlg(
				this, ScmConst.SC_Order, corderid);
		approvestatedlg.showModal();
	}

	/**
	 * ���� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE50010240 �޸��ˣ������ �޸����ݣ����ӵ���ģ�����÷ǿ����� �޸����ڣ�2003-04-14
	 */
	private boolean onSave() {
		
		//gc ���õ�һҳΪѡ��ҳ��Ҫ��Ȼ�ĵ���̫��
		BillTabbedPane tp = getBillCardPanel().getBodyTabbedPane();
		tp.setSelectedIndex(0);//.setTabVisible(0, true);
		// ���Ӷ�У�鹫ʽ��֧��,������ʾ��UAP���� since v501
		if (!getBillCardPanel().getBillData().execValidateFormulas()) {
			return false;
		}

		int rowCount = getBillCardPanel().getBillModel(TAB1).getRowCount();
		// ɾ�����ϱ���Ϊ�յ���
		if (m_billState != ScConstants.STATE_OTHER) {// �п����Ǻ�ͬ����ת�룬��ʱ����ɾ�����ϱ���Ϊ�յ���
			for (int i = rowCount - 1; i >= 0; i--) {
				Object tempObj = getBillCardPanel().getBillModel(TAB1)
						.getValueAt(i, "cinventorycode");
				if (tempObj == null || tempObj.toString().trim().equals("")) {
					getBillCardPanel().getBillModel(TAB1).delLine(
							new int[] { i });
				}
			}

			// ����Ϊ��ʱ�����ܱ���
			if (getBillCardPanel().getBillModel(TAB1).getRowCount() <= 0) {
				this.showErrorMessage(nc.vo.ml.NCLangRes4VoTransl
						.getNCLangRes().getStrByID("40120003",
								"UPP40120003-000004")/* @res "���岻��Ϊ�գ�������" */);
				return false;
			}
		}
		// gc-����ҳΪ�ղ��ɱ���
		int row_cly = getBillCardPanel().getBillModel(TAB2).getRowCount();
		for (int i = row_cly - 1; i >= 0; i--) {
			Object tempObj = getBillCardPanel().getBillModel("lby").getValueAt(
					i, "cinventorycode");
			if (tempObj == null || tempObj.toString().trim().equals("")) {
				getBillCardPanel().getBillModel("lby").delLine(new int[] { i });
			}
		}
		if(!checkDDLB()){
			this.showErrorMessage("����ҳ�����ϱ��롿+���������Ψһ�����޸�");
			return false;
		}
		// ����Ϊ��ʱ�����ܱ���
		if (getBillCardPanel().getBillModel(TAB2).getRowCount() <= 0) {
			this.showErrorMessage("����ҳ����Ϊ�գ�������");
			return false;
		}

		// gc-end//
		//gc �ѵ�һ��ҳ�ĵ�һ�д�����ŵ��ϱ�ҳ
		row_cly = getBillCardPanel().getBillModel(TAB2).getRowCount();
		Object cmangid = getBillCardPanel().getBillModel(TAB1)
				.getValueAt(0, "cmangid");
		Object cbaseid = getBillCardPanel().getBillModel(TAB1)
				.getValueAt(0, "cbaseid");
		for (int i = 0; i < row_cly; i++) {
			getBillCardPanel().getBillModel("lby").setValueAt(cmangid, i, "cprocessmangid");
			getBillCardPanel().getBillModel("lby").setValueAt(cmangid, i, "cprocessbaseid");
		}
		
		// 2005-02-24 �޸ĴӺ�ͬ����ʱ��ҵ�����Ͳ��ܱ���
		String lstr_biztype = (String) getBillCardPanel().getHeadItem(
				"cbiztype").getValueObject();
		if (lstr_biztype == null || lstr_biztype.trim().length() == 0) {
			getBillCardPanel().getHeadItem("cbiztype").setValue(
					getBillCardPanel().getBusiType());
		}
		// ���ݷǿ�����
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
				// ���VO
				
				m_curOrderVO = (OrderVO) getBillCardPanel()
						.getBillValueChangeVO(OrderVO.class.getName(),
								OrderHeaderVO.class.getName(),
								OrderItemVO.class.getName());
				//gc ������ҳ
				m_curOrderVO.setDdlbvos(getSaveDdlbVO());
				//gc---end,OrderDdlbVO.class.getName()
				m_curOrderVO.setPk_corp(getPk_corp());
				// ��˱�־
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setDauditdate(null);
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setCauditpsn(null);

				// ��ͷ��ע
				UIRefPane ref = (UIRefPane) m_BillCardPanel
						.getHeadItem("vmemo").getComponent();
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setVmemo(ref
						.getUITextField().getText());

				// ����״̬
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setIbillstatus(BillStatus.FREE);

				// ��ǰ����Ա
				String tTime = (new UFDateTime(new Date())).toString();
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setCcuroperator(getOperatorID());// ��̨����curoperator��ʧ��ͨ���ű�ȥ��д���˴�����
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTmaketime(tTime);
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTaudittime(null);
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTlastmaketime(tTime);

				// �Ϸ���У��
				m_curOrderVO.validate();

				// ����������
				if (!calNativeAndAssistCurrValue(m_curOrderVO))
					return false;

				// �кż��
				if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
						getBillCardPanel(), "crowno"))
					return false;

				// �ֿ�Ϳ����֯��ƥ����
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
																 * "�ֿ�������֯��ƥ��"
																 */);
						}
					}
				}

				String curPK = null;

				ArrayList userObj = new ArrayList();
				userObj.add(getOperatorID()); // userObjOld��ԭ�����û��Զ���������
				userObj.add(new Integer(0)); // 0����Ӧ���ڵ��ݱ�ͷ��1����Ӧ���ڵ��ݱ���
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
																				 * "��ʾ"
																				 */,
											ex.getMessage()
													+ nc.ui.ml.NCLangRes
															.getInstance()
															.getStrByID(
																	"401201",
																	"UPP401201-000064")/*
																						 * @
																						 * res
																						 * "�Ƿ����?"
																						 */) == MessageDialog.ID_YES) {
								// ��������
								bConfirmed = true;
							} else {
								return false;
							}
						}
						if (!bConfirmed)
							break;// δ���쳣,����
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

				// ͬ��oldNum
				updateOldNum();

				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setCoperator(getOperatorID());

				getModel().add((OrderHeaderVO) m_curOrderVO.getParentVO());
				getModel().last();

				// ����ת�뵥�ݣ��б����ӵ���
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

				// ����״̬
				// �������޸�ʱ��ԭ�������������δͨ��״̬�����޸ĳ�����̬
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

				// ���VO
				m_curOrderVO = (OrderVO) getBillCardPanel()
						.getBillValueChangeVO(OrderVO.class.getName(),
								OrderHeaderVO.class.getName(),
								OrderItemVO.class.getName());
				//gc ������ҳ
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
				// ��ͷ��ע
				UIRefPane ref = (UIRefPane) m_BillCardPanel
						.getHeadItem("vmemo").getComponent();
				((OrderHeaderVO) m_curOrderVO.getParentVO()).setVmemo(ref
						.getUITextField().getText());

				// ��ǰ����Ա
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setCcuroperator(getOperatorID());

				// �Ϸ���У��
				m_curOrderVO.validate();

				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTlastmaketime((new UFDateTime(new Date()))
								.toString());
				((OrderHeaderVO) m_curOrderVO.getParentVO())
						.setTaudittime(null);

				// ����������
				if (!calNativeAndAssistCurrValue(m_curOrderVO))
					return false;

				// �кż��
				if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
						getBillCardPanel(), "crowno"))
					return false;

				// �ֿ�Ϳ����֯��ƥ���飬��鷽�����Ƚϱ�ͷ�����֯cwareid�ͱ�������֯warehouseorg(ע����������֯���ݱ���Ĳֿ�ID����)
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
																 * "�ֿ�������֯��ƥ��"
																 */);
						}
					}

				}
				//

				// OrderBO_Client.update(m_curOrderVO);
				ArrayList userObj = new ArrayList();
				userObj.add(getOperatorID()); // userObjOld��ԭ�����û��Զ���������
				userObj.add(new Integer(0)); // 0����Ӧ���ڵ��ݱ�ͷ��1����Ӧ���ڵ��ݱ���
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
																				 * "��ʾ"
																				 */,
											ex.getMessage()
													+ nc.ui.ml.NCLangRes
															.getInstance()
															.getStrByID(
																	"401201",
																	"UPP401201-000064")/*
																						 * @
																						 * res
																						 * "�Ƿ����?"
																						 */) == MessageDialog.ID_YES) {
								// ��������
								bConfirmed = true;
							} else {
								return false;
							}
						}
						if (!bConfirmed)
							break;// δ���쳣,����
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

				// ͬ��oldNum
				updateOldNum();

			}

			resetInvmandocRefModel();
			getBillCardPanel().updateValue();

			if (m_billState == ScConstants.STATE_OTHER
					&& getBillListPanel().getHeadBillModel().getRowCount() != 0) {
				// ����ת��ı���,��д�빺���ۼƶ����������ص��б���棬�б���ʾ��һ�ŵ��ݱ���
				cardLayout.last(this);
				setButtons(m_btnTree.getButtonArray());// �����б��鰴ť
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
			// ���˵��ݺ�
			SCMEnv.out("���˵��ݺſ�ʼ...");/* -=notranslate=- */
			try {
				if ((m_billState == ScConstants.STATE_OTHER)
						&& ((OrderHeaderVO) m_curOrderVO.getParentVO())
								.getVordercode() != null) { // ����ʱ�����˵��ݺ�
					IGetSysBillCode getSysBillCode = (IGetSysBillCode) NCLocator
							.getInstance().lookup(
									IGetSysBillCode.class.getName());
					getSysBillCode.returnBillNo(m_curOrderVO);
				}
			} catch (Exception ex) {
				SCMEnv.out("���˵��ݺ��쳣����");/* -=notranslate=- */
			}
			SCMEnv.out("���˵��ݺ���������");/* -=notranslate=- */

			showErrorMessage("" + e.getMessage());
			SCMEnv.out(e);
			return false;
		}

		m_billState = ScConstants.STATE_CARD;
		setPgUpDownButtonsState(getModel().getCurrentIndex());
		refreshData();
		setButtonsState();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH005")/* @res "����ɹ�" */);

		return true;
	}

	/**
	 * 
	 * �������ܣ�ʹ�ö�����������oldNum���Ա��д��Դ����ʱʹ�á�<br>
	 * �÷���һ�㶼�����޸ı�������������ʹ��<br>
	 * <p>
	 * <p>
	 * 
	 * @author duy
	 * @time 2009-10-30 ����10:59:43
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
	 * ȫѡ���� *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	private void onSelectAll() {
		int listHeadNum = getBillListPanel().getHeadBillModel().getRowCount();
		if (listHeadNum <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000069")/* @res "������ѡ��" */);
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
				"4004COMMON000000033")/* @res "ȫ��ѡ�гɹ�" */);

	}

	/**
	 * ȡ������ *
	 * 
	 * @return void
	 * @exception
	 * @roseuid 3ADE500101B4
	 */
	private void onSelectNo() {
		int listHeadNum = getBillListPanel().getHeadBillModel().getRowCount();
		if (listHeadNum <= 0) {
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000069")/* @res "������ѡ��" */);
			return;
		}
		getBillListPanel().getHeadTable().clearSelection();
		for (int i = 0; i < listHeadNum; i++)
			getBillListPanel().getHeadBillModel().setRowState(i,
					BillModel.UNSTATE);

		clearList();
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"4004COMMON000000034")/* @res "ȫ��ȡ���ɹ�" */);
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2003-10-28 11:54:46)
	 */
	private void onSendAudit() {
		OrderVO vo = getSelectedVO();

		try {
			// ����
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
			// ����ťϸ������-begin
			// updateButtons();
			// ����ťϸ������-end
			setButtonsState();

			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000266")/* @res "����ɹ�" */);
		} catch (Exception e) {
			showErrorMessage(e.getMessage());
			SCMEnv.out(e);
			return;
		}
	}

	/**
	 * ί�ⶩ������ �������ڣ�(2001-7-26 12:50:18)
	 */
	public void onUnAudit() {
		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
				"UCH049")/* @res "��������" */);

		try {
			OrderVO vo = (OrderVO) this.getBillCardPanel()
					.getBillValueChangeVO(OrderVO.class.getName(),
							OrderHeaderVO.class.getName(),
							OrderItemVO.class.getName());
			//gc ������ҳ
			OrderDdlbVO[] ddlbvo = getSaveDdlbVO();
			m_curOrderVO.setDdlbvos(ddlbvo);
			//gc-end
			if (vo != null) {
				((OrderHeaderVO) vo.getParentVO()).setDauditdate(null);
				((OrderHeaderVO) vo.getParentVO()).setCauditpsn(null);
				((OrderHeaderVO) vo.getParentVO())
						.setCcuroperator(getOperatorID());
				((OrderHeaderVO) vo.getParentVO()).setTaudittime(null);

				// ����
				Object retObj = PfUtilClient.processBatchFlow(null, "UNAPPROVE"
						+ getClientEnvironment().getUser().getPrimaryKey(),
						ScmConst.SC_Order, getClientEnvironment().getDate()
								.toString(), new OrderVO[] { vo }, null);

				if (!PfUtilClient.isSuccess()) {
					showHintMessage(nc.ui.ml.NCLangRes.getInstance()
							.getStrByID("SCMCOMMON", "UPPSCMCommon-000185")/*
																			 * @res
																			 * "����ʧ��"
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
					"common", "UCH011")/* @res "����ɹ�" */);
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
			showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"SCMCOMMON", "UPPSCMCommon-000185")/* @res "����ʧ��" */);
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
	 * �жϿ�Ƭ�����ϵĵ���VO�Ƿ���Դ��CT
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
	 * ���ÿ�Ƭ����ͷ���ͬ�����Ŀɱ༭��
	 * 
	 * @param isEditable
	 */
	private void setCntRelatedItemEditableInCardHead() {
		if (isFromCtInCard()) {
			// ���պ�ͬ���ɵĵ��ݣ���ͷ��Ӧ�̡��������ڲ����޸�
			getBillCardPanel().getHeadItem(BillHeaderItemKeys.CVENDORMANGID)
					.setEdit(false);
			getBillCardPanel().getHeadItem(BillHeaderItemKeys.DORDERDATE)
					.setEdit(false);
		} else {
			// �ǲ��պ�ͬ���ɵĵ��ݣ���ͷ��Ӧ�̡��������ڲ����޸�
			getBillCardPanel().getHeadItem(BillHeaderItemKeys.CVENDORMANGID)
					.setEdit(true);

			if (m_billState == ScConstants.STATE_MODIFY) {
				// �޸�״̬���������ڲ��ɱ༭
				getBillCardPanel().getHeadItem(BillHeaderItemKeys.DORDERDATE)
						.setEdit(false);
			} else {
				getBillCardPanel().getHeadItem(BillHeaderItemKeys.DORDERDATE)
						.setEdit(true);
			}
		}
	}

	/**
	 * ��Ƭ״̬ ����ת�뵥������ �������ڣ�(2001-9-6 9:54:14)
	 */
	private void refLoadData(int listRow) {// gc???????????
		try {
			// ����������ʾ״̬
			cardLayout.first(this);
			setButtons(m_btnTree.getButtonArray());// ���ÿ�Ƭ�鰴ť

			// ����ת�뵥������
			OrderVO vo = (OrderVO) comeVOs.get(listRow);
//			getBillCardPanel().setBillValueVO(vo);//gc-old
			//gc
			nc.vo.pub.ExAggregatedVO exBillVO = new nc.vo.pub.ExAggregatedVO(vo);
			exBillVO.setParentVO((OrderHeaderVO)vo.getParentVO());
			exBillVO.addTableVO(TAB1,"", vo.getChildrenVO());
			exBillVO.addTableVO(TAB2,"", vo.getDdlbvos());
			getBillCardPanel().setBillValueVO(vo);
			//gc-end
			

			// ����ע
			// ��ͷ
			m_sHeadVmemo = ((OrderHeaderVO) vo.getParentVO()).getVmemo();
			UIRefPane ref = (UIRefPane) getBillCardPanel().getHeadItem("vmemo")
					.getComponent();
			ref.setText(m_sHeadVmemo);

			// 2009-9-28 renzhonghai ���չ����ĵ������û��ҵ���������ܼ��������Ժ�Ĳ�����
			if (getBillCardPanel().getHeadItem("cbiztype").getValueObject() == null) {
				getBillCardPanel().getHeadItem("cbiztype").setEdit(true);
				getBillCardPanel().getHeadItem("cbiztype").setNull(true);
			}

			// 2009-9-10 renzhonghai �޸�Ч����
			// ���屸ע��Ĭ����(���ʡ���˰��𡢼ƻ���������)
			for (int i = 0, len = vo.getChildrenVO().length; i < len; i++) {
				// Ĭ����(���ʡ���˰��𡢼ƻ���������)
				getBillCardPanel().setBodyValueAt("100", i, "ndiscountrate");
				getBillCardPanel().setBodyValueAt(new Integer(1), i,
						"idiscounttaxtype");

				// ���޼ƻ��������ڣ������Ĭ��
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

			// ������
			loadFreeItems();
			// ��Ӧ��Ĭ����
			BillEdit.editCust(getBillCardPanel(),
					BillHeaderItemKeys.CVENDORMANGID);

			// 2009-9-10 renzhonghai Ч�������Ż�
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

			// ѭ��֮ǰ�رպϼ��еļ��㣬�ṩЧ��
			getBillCardPanel().getBillModel(TAB1).setNeedCalculate(false);
			// ������ϵ�����������������㡢����
			for (int i = 0, len = vo.getChildrenVO().length; i < len; i++) {

				Object oTemp = getBillCardPanel().getBodyValueAt(i,
						"idiscounttaxtype");
				String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// Ӧ˰���
				if (oTemp != null) {
					lStr_discounttaxtype = oTemp.toString();
				}

				String[] keys = new String[] { lStr_discounttaxtype,
						"idiscounttaxtype", "nordernum", "norgtaxprice",
						"noriginalcurprice", "norgnettaxprice",
						"noriginalnetprice", "ndiscountrate", "ntaxrate",
						"noriginalcurmny", "noriginaltaxmny", "noriginalsummny" };

				// ����λ��������
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
				// ����
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
				// ����
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
				// ������ַ
				String creciever = ((OrderHeaderVO) vo.getParentVO())
						.getCreciever();
				if (creciever != null && !creciever.trim().equals("")) {
					setReceiveAddress(i, "creciever");
				}

				// //������Ϊ�գ��������Ӧ��Ĭ�ϱ��֡�˰��
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

				// ί�ⶩ�������빺����¼�뾻����˰�ʺ󣬺�˰����Ϊ0���������ۺͽ���Ϊ0��
				UFDouble norgtaxprice = (UFDouble) getBillCardPanel()
						.getBodyValueAt(i, BillBodyItemKeys.NORGTAXPRICE);
				if (norgtaxprice != null && norgtaxprice.doubleValue() == 0) {
					getBillCardPanel().setBodyValueAt(null, i,
							BillBodyItemKeys.NORGTAXPRICE);
				}
			}
			// ѭ��֮��򿪺ϼ��еļ��㣬songhy
			getBillCardPanel().getBillModel(TAB1).setNeedCalculate(true);

			getBillCardPanel().getBillModel(TAB1).execLoadFormula();
			getBillCardPanel().updateUI();
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}

		// �����ϲ��Դͷ������Ϣ
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
		// �����б�����
		getBillListPanel().getComeVO().remove(listRow);
		getBillListPanel().loadHeadData();

		boEdit.setEnabled(false);
		setButtonsState();
		updateButtons();
	}

	/**
	 * �����ߣ����� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2003-6-14 12:45:56) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private void refreshData() {
		long tTime = System.currentTimeMillis();

		// ���㺬˰����
		refreshBillCardPanel();
		loadCustBank();

		m_sHeadVmemo = ((OrderHeaderVO) m_curOrderVO.getParentVO()).getVmemo();
		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
				.getComponent();
		ref.setText(m_sHeadVmemo);

		// �����Զ�����
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

		// ��ʾ��˰���
		OrderItemVO[] orderItemVO = (OrderItemVO[]) m_curOrderVO
				.getChildrenVO();
		for (int i = 0; i < orderItemVO.length; i++) {
			Integer idiscounttaxtype = orderItemVO[i].getIdiscounttaxtype();

			getBillCardPanel().getBillModel(TAB1).setValueAt(idiscounttaxtype,
					i, "idiscounttaxtype");
			// 2009-10-29 renzhonghai ��ʾ���κ�
			String vproduceNum = orderItemVO[i].getVproducenum();
			getBillCardPanel().getBillModel(TAB1).setValueAt(vproduceNum, i,
					"vproducenum");

		}

		showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
				"SCMCOMMON", "UPPSCMCommon-000253")/* @res "���ݼ��سɹ�" */);
		SCMEnv.out("���ݼ��أ�[����ʱ" + (System.currentTimeMillis() - tTime) / 1000
				+ "��]");/* -=notranslate=- */

		// ά������״̬�����ϡ��޸ģ�
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
	 * ˢ�½����Ͽ�Ƭ�����ͷ�ͱ���Ĳ�������
	 * 
	 * @author songhy
	 */
	private void refreshBillCardPanel() {
		// ˢ�¿�Ƭ����ͷPK���������ݺš�����TS���͡�����״̬������Ϣ
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

		// ˢ�¿�Ƭ����PK��TS
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
	 * ��������:�����˵��Ҽ�����Ȩ�޿���
	 * <p>
	 * <b>�����ʷ��</b>
	 * <p>
	 * <hr>
	 * <p>
	 * �޸����� 2008.8.28
	 * <p>
	 * �޸��� zhaoyha
	 * <p>
	 * �汾 5.5
	 * <p>
	 * ˵����
	 * <ul>
	 * <li>�趨������Ҽ��˵�����Ȩ��
	 * </ul>
	 */
	private void rightButtonRightControl() {
		// û�з����в���Ȩ��
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
		// �����в���Ȩ��
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
			// ճ������β��ճ���������߼���ͬ
			getBillCardPanel().getPasteLineToTailMenuItem().setEnabled(
					boPasteLineToTail.isPower());
			getReOrderRowNoMenuItem().setEnabled(boReOrderRowNo.isPower());
			getCardEditMenuItem().setEnabled(boCardEdit.isPower());
		}
	}

	/**
	 * �򵥾�ģ������VO ���б���ʾ�£������ӡʱ������ô˷���
	 * 
	 * �������ڣ�(2003-11-28 9:13:10)
	 * 
	 * @param vo
	 *            nc.vo.pub.AggregatedValueObject
	 */
	public void setBillVO(nc.vo.pub.AggregatedValueObject vo) {
		if (getModel().size() == 0) {
			return;
		}

		m_curOrderVO = (OrderVO) vo;

		// ���㺬˰����
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

		// add by hanbin 2009-11-24 ԭ����������ϣ���ӡԤ��ʱ������ʾ����ȷ
		this.setPrecision();

		m_sHeadVmemo = ((OrderHeaderVO) m_curOrderVO.getParentVO()).getVmemo();
		UIRefPane ref = (UIRefPane) m_BillCardPanel.getHeadItem("vmemo")
				.getComponent();
		ref.setText(m_sHeadVmemo);

		// �����Զ�����
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
		SCMEnv.out("ִ�й�ʽ[����ʱ" + (System.currentTimeMillis() - s1) + "]");/*
																		 * -=
																		 * notranslate
																		 * =-
																		 */

		loadMeasRate();
		getBillCardPanel().updateValue();
		loadFreeItems();

		// ��ʾ��˰���
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
	 * ���ð�ť״̬�� �������ڣ�(2001-3-17 9:00:09)
	 */
	private void setButtonsState() {
		boFrash.setEnabled(m_bQueried);// ��ѯ������Ч
		boCancelOut.setEnabled(false);

		if (m_billState == ScConstants.STATE_OTHER) {
			boAudit.setEnabled(false);
			boCancel.setEnabled(!boEdit.isEnabled());
			boSave.setEnabled(!boEdit.isEnabled());
			boLineOperator.setEnabled(!boEdit.isEnabled());

			// ����ת��״̬
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

			boFrash.setEnabled(false);// ˢ��
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
				// 2009-10-27 renzhonghai ����״̬�²���ɾ������
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
			boLocate.setEnabled(false);// ��λ
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
				// 2009-10-27 renzhonghai ����״̬�²���ɾ������
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

			boSelectAll.setEnabled(false);// ȫѡ
			boSelectNo.setEnabled(false);// ȫ��
			boFrash.setEnabled(false);// ˢ��
			boLocate.setEnabled(false);// ��λ
			boReturn.setEnabled(false);
			boCopy.setEnabled(false);
			boAction.setEnabled(false);

			// ��������״̬����������ť�����ã�songhy, 2009-06-01, start
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

			boSelectAll.setEnabled(false);// ȫѡ
			boSelectNo.setEnabled(false);// ȫ��
			boFrash.setEnabled(false);// ˢ��
			boLocate.setEnabled(false);// ��λ
			boReturn.setEnabled(false);

			boCopy.setEnabled(false);
			boAction.setEnabled(false);
			setGroupButtonsState(UFBoolean.FALSE);
			getBillCardPanel().setEnabled(true);
		}

		updateButtons();
	}

	/**
	 * ���ô�ӡ�����顢������ѯ�顢���������� �İ�ť״̬�� �������ڣ�(2001-3-17 9:00:09)
	 */
	private void setGroupButtonsState(UFBoolean state) {
		// ��ӡ�����鰴ť
		m_btnPrints.setEnabled(state.booleanValue());
		m_btnPrintPreview.setEnabled(state.booleanValue());
		boPrint.setEnabled(state.booleanValue());
		btnBillCombin.setEnabled(state.booleanValue());

		// ������ѯ�鰴ť
		m_btnOthersQueries.setEnabled(state.booleanValue());
		boLinkQuery.setEnabled(state.booleanValue());// ����
		boQueryForAudit.setEnabled(state.booleanValue());// ������״̬

		// ���������鰴ť
		m_btnOthersFuncs.setEnabled(state.booleanValue());// ��������
		boDocument.setEnabled(state.booleanValue());// �ĵ�����
	}

	/**
	 * ����м��е��еĴ��Ϊ�գ����޳��� �ɼ۸���������ʽ����ί�ⶩ��Ҳ�޳���
	 * 
	 * @param selectedRows
	 *            ������ѡ���������
	 * @return ���˺��������
	 */
	private int[] filterCtRelatedRows(int[] rows) {
		List<Integer> filteredRows = new ArrayList<Integer>();
		for (int i = 0; i < rows.length; i++) {
			// �ɼ۸���������ʽ����ί�ⶩ��Ҳ�޳���,����ѯ��
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
	 * 1����պ�ͬ�����Ϣ���Ƚ���ͬID ��ͬ��ID ��ͬ�š� 2��������״̬������BillModel.ADD ��
	 * �޸�BillModel.MODIFICATION
	 * 
	 * @param aintary_rows
	 *            �к�����
	 */
	private void clearContractInfo(int[] aintary_rows) {
		// ����ѯ��������ͬΪ�ա��Ƚ���ͬID ��ͬ��ID ��ͬ�� ����Ϊ��
		for (int i = 0, len = aintary_rows.length; i < len; i++) {
			int lint_rowindex = aintary_rows[i];// �����
			if (!isFromCT(lint_rowindex)) {// �����Դ���Ǻ�ͬ
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
	 * �жϵ� rowIndex ���Ƿ���Դ���ͬ
	 * 
	 * @param rowIndex
	 * @return
	 */
	private boolean isFromCT(int rowIndex) {
		// �жϵ� rowIndex ���Ƿ���Դ���ͬ
		String sUpSourceType = (String) getBillCardPanel().getBodyValueAt(
				rowIndex, BillBodyItemKeys.CUPSOURCEBILLTYPE);
		return (sUpSourceType != null)
				&& (sUpSourceType.equals(BillTypeConst.CT_BEFOREDATE) || sUpSourceType
						.equals(BillTypeConst.CT_ORDINARY));
	}

	/**
	 * �ж��Ƿ���Ҫ������ͬ
	 * 
	 * @param aintary_rows
	 *            �к�����
	 * @return
	 */
	private boolean needRelateCT(int[] aintary_rows) {
		for (int i = 0; i < aintary_rows.length; i++) {
			int lint_rowindex = aintary_rows[i];// �����
			if (!isFromCT(lint_rowindex)) {// �����Դ���Ǻ�ͬ
				int iRet = showYesNoMessage(nc.ui.ml.NCLangRes.getInstance()
						.getStrByID("40040200", "UPP40040200-000034")/*
																	 * @res
																	 * "Ѱ�ҵ���ͬ���Ƿ������"
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
		// û�й����Ϻ�ͬ,����ֿɱ༭
		getBillCardPanel().setCellEditable(rowIndex, itemKey,
				(billBodyItem != null) && billBodyItem.isEdit());
	}

	/**
	 * ������ͬ��Ϣ: ��ͬ�ۣ���ͬ��
	 * 
	 * @param aintary_rows
	 *            �б�ʶ����
	 * @return Ϊѯ����ͬ�۵��к�����
	 * 
	 * @throws Exception
	 */
	private int[] relateContractInfo(int[] aintary_rows) throws Exception {
		if (!isCtStartUp()) {
			// �����ͬû�����ã����������б�ʶ����
			return aintary_rows;
		}

		// ���ѡ���еĺ�ͬ�����Ϣ
		clearContractInfo(aintary_rows);

		// ��ͷ-��Ӧ�̻���id
		String lStr_vendorbasid = (String) getBillCardPanel().getHeadItem(
				BillBodyItemKeys.CVENDORID).getValueObject();

		// ��ͷ-��������
		String lStr_orderdate = (String) getBillCardPanel().getHeadItem(
				BillHeaderItemKeys.DORDERDATE).getValueObject();

		// ��¼�кţ���ʶ�Ƿ���Ҫ��һ��ȥѯĬ�ϼ۸�
		List<Integer> rowsForPriceAgain = new ArrayList<Integer>();

		int li_rowcount = aintary_rows.length;
		String[] lStrary_baseid = new String[li_rowcount]; // �����������Id����
		String[] currencyTypeIds = new String[li_rowcount]; // ��ǰĬ�ϱ���Id����
		String[] rowNumbers = new String[li_rowcount]; // �к�����

		for (int i = 0; i < li_rowcount; i++) {
			int lint_rowindex = aintary_rows[i];// �����
			lStrary_baseid[i] = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CBASEID,TAB1);
			currencyTypeIds[i] = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CCURRENCYTYPEID,TAB1);
			rowNumbers[i] = (String) getCardBodyValueAt(lint_rowindex,
					BillBodyItemKeys.CROWNO,TAB1);
		}

		// Ϊ���Ч�ʣ�һ���Բ�ѯ��ͬ�����Ϣ
		RetCtToPoQueryVO[] lary_CtRetVO = ScFromCtHelper.queryForCntAll(
				rowNumbers, getPk_corp(), lStrary_baseid, lStr_vendorbasid,
				currencyTypeIds, new UFDate(lStr_orderdate));

		if ((lary_CtRetVO == null) || (lary_CtRetVO.length <= 0)
				|| (!needRelateCT(aintary_rows))) {
			// �����ѯ���Ϊ�գ������û�ѡ�񲻹�����ͬ�����������б�ʶ����
			return aintary_rows;
		}

		// add by hanbin 2009-11-5
		// ���ڱ�ʶ����ѡ��ļӹ�Ʒ���������ĺ�ͬ�ǲ���һһ��Ӧ�������ֱ����ʾ��Ӧ�ĺ�ͬ������Ҫ�ڵ����Ի���
		boolean isOne2One = true;

		List<String> tempList = new ArrayList<String>();
		for (RetCtToPoQueryVO ctVO : lary_CtRetVO) {
			// �����к��Ƿ�����ظ����������ж�
			if (tempList.contains(ctVO.getM_cRowno())) {
				isOne2One = false;
				break;
			}
			tempList.add(ctVO.getM_cRowno());
		}

		// ���ڴ洢��ѡ��ĺ�ͬ
		RetCtToPoQueryVO[] voArraySelected = null;
		if (isOne2One) {
			voArraySelected = lary_CtRetVO;// �����ֱ����ʾ��Ӧ�ĺ�ͬ������Ҫ�ڵ����Ի���
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
		if ((voArraySelected == null) || (voArraySelected.length == 0)) {// û�й����Ϻ�ͬ
			for (int i = 0; i < li_rowcount; i++) {
				int lint_rowindex = aintary_rows[i];// �����

				rowsForPriceAgain.add(lint_rowindex);

				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTID);
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTROWID);
				getBillCardPanel().setBodyValueAt(null, lint_rowindex,
						BillBodyItemKeys.CCONTRACTRCODE);

				// û�й����Ϻ�ͬ,����ֿɱ༭
				setCardBodyCellEditable(BillBodyItemKeys.CCURRENCYTYPE,
						lint_rowindex);

				// û�й����Ϻ�ͬ,��۸�ɱ༭
				setCardBodyCellEditable(BillBodyItemKeys.NORIGINALCURPRICE,
						lint_rowindex);

				// û�й����Ϻ�ͬ,��˰���ۿɱ༭
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
		} else {// �������˺�ͬ
			Map<String, RetCtToPoQueryVO> voMap = new HashMap<String, RetCtToPoQueryVO>();
			for (RetCtToPoQueryVO item : voArraySelected) {
				voMap.put((String) item
						.getAttributeValue(BillBodyItemKeys.CROWNO), item);
			}

			for (int i = 0; i < li_rowcount; i++) {
				int lint_rowindex = aintary_rows[i];// �����
				String crowno = (String) getBillCardPanel().getBodyValueAt(
						lint_rowindex, BillBodyItemKeys.CROWNO);
				RetCtToPoQueryVO lCtRetVO_Selected = voMap.get(crowno);

				if (lCtRetVO_Selected == null) {
					// ��������û�й����ĺ�ͬ�ţ����в�����������������ҹ����ĺ�ͬ��
					continue;
				}

				// ��ͬID ��ͬ��
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCContractID(), lint_rowindex,
						BillBodyItemKeys.CCONTRACTID);
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCContractRowId(), lint_rowindex,
						BillBodyItemKeys.CCONTRACTROWID);
				getBillCardPanel().setBodyValueAt(
						lCtRetVO_Selected.getCContractCode(), lint_rowindex,
						BillBodyItemKeys.CCONTRACTRCODE);

				// �����Ϻ�ͬ,��ʹ�ú�ͬ����,�ұ��ֲ��ɱ༭
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

				// //������,����Ч������,���Էŵ�ѭ����ִ��,��ʵ����û�б�Ҫ
				String formula = "ccurrencytype->getColValue(bd_currtype,currtypename,pk_currtype,ccurrencytypeid)";
				getBillCardPanel().getBillModel(TAB1).execFormula(i,
						new String[] { formula });
				getBillCardPanel().setCellEditable(lint_rowindex,
						"ccurrencytype", false);
				setCurrencyExchangeRateEnable(lint_rowindex);
				setCurrencyExchangeRate(lint_rowindex);
				getBillCardPanel().updateUI();

				// �۸�
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

					// /��ԭ��ֵ��ͬ�����¼���
					if (lUFD_OldPrice == null
							|| lUFD_Price.compareTo(lUFD_OldPrice) != 0) {
						setCardBodyValueAt(lUFD_Price, lint_rowindex,
								priceItemKey,TAB1);
						getBillCardPanel().setBodyValueAt(
								lCtRetVO_Selected.getTaxration(),
								lint_rowindex, BillBodyItemKeys.NTAXRATE);

						// �����Ϻ�ͬ,���ҵ���Ӧ�ĺ�ͬ��,��۸��ܱ༭
						getBillCardPanel().setCellEditable(lint_rowindex,
								BillBodyItemKeys.NORIGINALCURPRICE, false);
						getBillCardPanel().setCellEditable(lint_rowindex,
								BillBodyItemKeys.NORGTAXPRICE, false);

						// ���¼���������ϵ
						// ����������ϵ����
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
								lint_rowindex, "idiscounttaxtype");// ��˰���
						String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// Ӧ˰���
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
	 * ������ͬ���Զ�ȡ�� ����:abol_SkipCT boolean ������ͬ��ȡ�ۺ͹�������,ֱ�ӽ���ȡ��Ӧ�̼۸��Ĭ�ϲ����۸�.
	 */
	private void setRelateCntAndDefaultPrice(int[] aintary_rows,
			boolean abol_SkipCT) {
		try {
			aintary_rows = filterCtRelatedRows(aintary_rows);//???
			if ((aintary_rows == null) || (aintary_rows.length == 0)) {
				return;
			}

			// �����������ͬ����,�����Ƚ��к�ͬ�������
			int[] noCtPriceRows = null;
			if (!abol_SkipCT) {
				noCtPriceRows = relateContractInfo(aintary_rows);
			}

			if ((noCtPriceRows == null) || (noCtPriceRows.length <= 0)) {
				// û����Ҫ����ѯ�۵��У�����
				return;
			}

			// ȡ��һ����������Ҫ��ֵ
			// ��������
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
										"ccurrencytypeid"));// ����id
				lUFDary_BRate2[i] = SCPubVO
						.getUFDouble_ValueAsValue(getBillCardPanel()
								.getBillModel(TAB1).getValueAt(noCtPriceRows[i],
										"nexchangeotobrate"));// �۱�����
			}

			// ��ͷ-��Ӧ��mangid
			String lStr_vendormangid = (String) getBillCardPanel().getHeadItem(
					BillBodyItemKeys.CVENDORMANGID).getValueObject();

			// ��ͷ-��������
			String lStr_orderdate = (String) getBillCardPanel().getHeadItem(
					BillHeaderItemKeys.DORDERDATE).getValueObject();

			// /��ͷ-�����֯id
			String lStr_wareid = (String) getBillCardPanel().getHeadItem(
					BillHeaderItemKeys.CWAREID).getValueObject();

			// ��� ���� �۱����� �۸�����
			// ȡĬ�ϼ۸�
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

			// �۸񷵻�
			RetScVrmAndParaPriceVO l_voRetPrice = OrderHelper
					.queryVrmAndParaPrices(l_voPara);

			for (int i = 0; i < noCtPriceRows.length; i++) {
				UFDouble lUFD_Price = l_voRetPrice.getPriceAt(i);
				if (lUFD_Price == null) {
					continue;
				}

				String lStr_ChangedKey = null;
				if (l_voRetPrice.isSetPriceNoTaxAt(i)) {// ���Ϊ���ο��ɱ�����"�ƻ���"������Զ��Ϊ����˰���ȡ�
					lStr_ChangedKey = "noriginalcurprice";
				}

				// ��ԭ��ֵ��ͬ�����¼���
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
					// ���¼���������ϵ
					// ����������ϵ����
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
					String lStr_discounttaxtype = ScConstants.TaxType_Not_Including;// Ӧ˰���
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
	 * ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
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
			int lint_rowindex = aintary_rows[i];// �����
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
	 * ������ͬ���������ֶβ��ɸģ����֡�ԭ����˰���ۡ�ԭ�Һ�˰���ۡ�
	 */
	private void setNotEditableWhenRelateCntAllRow() {// gc ������
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
	 * �Զ�ȡ��
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
	 * ���ߣ�yye ���ܣ��õ����ݲɹ��۸����ȹ��ĵ���FIELD ������int iPolicy �μ���RelationsCalVO ���أ�String
	 * "noriginalcurprice"��˰����;"norgtaxprice"��˰���� ���⣺�� ���ڣ�(2003-7-1 12:42:34)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static String getPriceFieldByPricePolicy(int iPolicy) {
		if (iPolicy == RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE) {
			// ��˰����
			return "norgtaxprice";
		} else if (iPolicy == RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE) {
			// ��˰����
			return "noriginalcurprice";
		}

		return null;
	}

	/**
	 * ���ߣ�yye ���ܣ��õ����ݲɹ��۸����ȹ��ĵ��� ������int iPolicy �μ���RelationsCalVO ���أ�String
	 * "noriginalcurprice"��˰����;"norgtaxprice"��˰���� ���⣺�� ���ڣ�(2003-7-1 12:42:34)
	 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	public static UFDouble getPriceValueByPricePolicy(
			nc.vo.scm.ctpo.RetCtToPoQueryVO voCntInfo, int iPolicy) {

		// ������ȷ�Լ��
		if (voCntInfo == null) {
			return null;
		}

		if (iPolicy == RelationsCalVO.TAXPRICE_PRIOR_TO_PRICE) {
			// ��˰����
			return voCntInfo.getDOrgTaxPrice();
		} else if (iPolicy == RelationsCalVO.PRICE_PRIOR_TO_TAXPRICE) {
			// ��˰����
			return voCntInfo.getDOrgPrice();
		}

		return null;
	}

	/**
	 * ���ñ��ֻ��ʵ�Ԫ��Ŀɱ༭��
	 * 
	 * @param rowIndex
	 *            �к�
	 */
	private void setCurrencyExchangeRateEnable(int rowIndex) {
		try {
			// ��ȡ����
			String ccurrency = (String) getBillCardPanel().getBodyValueAt(
					rowIndex, "ccurrencytypeid");
			if (StringUtil.isEmptyWithTrim(ccurrency)) {
				// ����Ϊ�գ����û��ʵ�Ԫ�񲻿ɱ༭
				getBillCardPanel().setCellEditable(rowIndex,
						"nexchangeotobrate", false);
			} else if (getCurrParamQuery().isLocalCurrType(getPk_corp(),
					ccurrency)) {
				// ����Ϊ��λ�ң����û��ʵ�Ԫ�񲻿ɱ༭
				getBillCardPanel().setCellEditable(rowIndex,
						"nexchangeotobrate", false);
			} else {
				// ����Ϊ�Ǳ�λ�ң����û��ʵ�Ԫ��ɱ༭
				getBillCardPanel().setCellEditable(rowIndex,
						"nexchangeotobrate", true);
			}
		} catch (Exception e) {
			SCMEnv.out(e);
			showErrorMessage(e.getMessage());
		}
	}

	/**
	 * ���ñ��ֻ���
	 */
	private void setCurrencyExchangeRate(int rowIndex) {// gc ������
		try {
			// ��ȡ����
			String ccurrency = (String) getBillCardPanel().getBodyValueAt(
					rowIndex, "ccurrencytypeid");
			if (StringUtil.isEmpty(ccurrency)) {
				// ����Ϊ�գ�����
				return;
			}

			if (getCurrParamQuery().isLocalCurrType(getPk_corp(), ccurrency)) {
				getBillCardPanel().setBodyValueAt("1", rowIndex,
						"nexchangeotobrate");
			} else {
				// ����
				String localCur = getCurrParamQuery().getLocalCurrPK(
						getPk_corp());
				// ȡ��������
				String strRateDate = (String) getCardHeadItemValue("dorderdate");
				// �������
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
	 * ��ȡ���ֲ�����ѯ����, ���ڲ�ѯ�������ص�һЩ����
	 * 
	 * @return ������ѯ����
	 */
	private CurrParamQuery getCurrParamQuery() {
		return CurrParamQuery.getInstance();
	}

	/**
	 * �˴����뷽��˵���� ��������:���þ��ȣ����������ۡ��� �������: ����ֵ: �쳣����: 2003/02/13 ������޸�
	 * 2003/03/15 ���Ӻ�˰���ۣ�������
	 */
	private void setNormalPrecision2() {// gc ������

		try {
			int moneyPrecision = 4;

			// ������
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nordernum")
					.setDecimalDigits(getNumPrecision()); // ��������
			getBillCardPanel().getBillModel(TAB2).getItemByKey("nnum")
			.setDecimalDigits(getNumPrecision()); // ��������//gc
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nbackarrvnum")
					.setDecimalDigits(getNumPrecision());// �˻�����
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nbackstorenum")
					.setDecimalDigits(getNumPrecision());// �˿�����
			// ������
			getBillCardPanel().getBillModel(TAB1).getItemByKey("nassistnum")
					.setDecimalDigits(getAssNumPrecision());
			// ������
			getBillCardPanel().getBillModel(TAB1).getItemByKey("measrate")
					.setDecimalDigits(getRatePrecision());
			// ����:
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
			// ��
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginalcurmny")
					.setDecimalDigits(moneyPrecision);
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginaltaxmny")
					.setDecimalDigits(moneyPrecision);
			getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("noriginalsummny")
					.setDecimalDigits(moneyPrecision);
			// ���ҽ��
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
	 * ά��������ť״̬���޸ġ����� �������ڣ�(2001-8-30 12:50:47)
	 * 
	 * @param state
	 *            int
	 */
	public void setOperateState(int ibillstate) {

		if (comeVOs != null && comeVOs.size() > 0 && ibillstate == 10) {
			// modify by hanbin 2009-11-18 ԭ��:����жϣ������ǰ�Ǵ��ڱ༭״̬�����ư�ť״̬
			if (this.getBillCardPanel().isVisible()
					&& this.getBillCardPanel().isEnabled()) {
				boAudit.setEnabled(false);
				boEdit.setEnabled(false);
				boCancelOut.setEnabled(false);
				boCancel.setEnabled(!boEdit.isEnabled());
				boSave.setEnabled(!boEdit.isEnabled());
				boLineOperator.setEnabled(!boEdit.isEnabled());

				// ����ת��״̬
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

		if (ibillstate == 0 || ibillstate == 4) { // ���ɡ�����δͨ��
			boEdit.setEnabled(true);
			boDel.setEnabled(true);
			boAction.setEnabled(true);
			boAudit.setEnabled(true);
			boUnaudit.setEnabled(false);
		} else if (ibillstate == 2) { // ������
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
		} else { // ������
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
	 * ���ܣ����÷�ҳ��ť��״̬����������ҳ����ҳ����ҳ��ĩҳ
	 * 
	 * �������ڣ�(2001-3-17 9:00:09)
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
	 * ���嵽����ַ����Ϊ�ա�����ͷ���ջ�����Ϊ��ʱ�����ձ�ͷ�ջ����ĵ�����ַ�� ���ͷ���ջ���Ϊ�գ���Ĭ��Ϊ�ջ��ֿ�ĵ�ַ���ɱ༭��
	 * �������ڣ�(2001-8-1 12:53:21)
	 */
	private void setReceiveAddress(int rowIndex, String key) {// gc ������

		//
		Object sReceiver = getBillCardPanel().getHeadItem("creciever")
				.getValue();

		if (key.equals("creciever")) {

			// add by hanbin 2009-10-28
			// ԭ��getBillCardPanel().getBillTable(tab).getColumn("������ַ")���쳣IllegalArgumentException
			// �жϡ�������ַ���Ƿ���ʾ�����û����ʾ��ֱ�ӷ���
			if (!getBillCardPanel().getBillModel(TAB1)
					.getItemByKey("creceiveaddress").isShow())
				return;

			if (sReceiver != null && !sReceiver.toString().trim().equals("")) {
				// ����ID
				String cvendorbaseid = null;
				String defaddr = null;
				try {
					cvendorbaseid = PublicHelper.getCvendorbaseid(sReceiver
							.toString());
					defaddr = PublicHelper.getVdefaddr(cvendorbaseid);

				} catch (Exception e) {
				}
				UIRefPane adressRef = new UIRefPane();
				adressRef.setRefNodeName("���̷�����ַ");/* -=notranslate=- */
				adressRef.setReturnCode(true);
				adressRef.setWhereString(" where pk_cubasdoc='" + cvendorbaseid
						+ "' ");

				getBillCardPanel()
						.getBillTable()
						.getColumn(
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"common", "UC000-0000642")/* @res "������ַ" */)
						.setCellEditor(new BillCellEditor(adressRef));

				// ������б��嵽����ַ
				for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
					getBillCardPanel().setBodyValueAt(defaddr, i,
							"creceiveaddress");
				}

			} else {
				getBillCardPanel()
						.getBillTable()
						.getColumn(
								nc.ui.ml.NCLangRes.getInstance().getStrByID(
										"common", "UC000-0000642")/* @res "������ַ" */)
						.setCellEditor(new BillCellEditor(new UITextField()));
				// ���ݲֿ���д������Ĭ�ϵ�����ַ
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

		// ��������˰��
		InvTool.loadBatchTaxrate(baseIdList.toArray(new String[baseIdList
				.size()]));
		InvTool.loadBatchTaxrateForMangIDs(mangIdList
				.toArray(new String[mangIdList.size()]));

		// ����
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
	 * �˴����뷽��˵���� ��������:ѡ��ͬҵ������ʱ���� �������: ����ֵ: �쳣����: ����:2003/05/06
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
	 * ��������:�˳�ϵͳ
	 */
	public boolean onClosing() {
		// ���ڱ༭����ʱ�˳���ʾ
		if (m_billState == ScConstants.STATE_ADD
				|| m_billState == ScConstants.STATE_MODIFY
				|| (m_billState == ScConstants.STATE_OTHER && getBillCardPanel()
						.isVisible())) {
			int nReturn = MessageDialog.showYesNoCancelDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "��ʾ" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
							"UCH001")/* @res "�Ƿ񱣴����޸ĵ����ݣ�" */);
			// ����ɹ�����˳�
			if (nReturn == MessageDialog.ID_YES) {
				return onSave();
			}
			// �˳�
			else if (nReturn == MessageDialog.ID_NO) {
				return true;
			}
			// ȡ���ر�
			else {
				return false;
			}
		}
		return true;
	}

	/**
	 * ���ο���������չ��ť��Ҫ����ο��������������ʵ��
	 * 
	 * @see nc.ui.scm.pub.bill.IBillExtendFun#getExtendBtns()
	 * 
	 */
	public ButtonObject[] getExtendBtns() {
		return null;
	}

	/**
	 * ������ο�����ť�����Ӧ����Ҫ����ο��������������ʵ��
	 * 
	 * @see nc.ui.scm.pub.bill.IBillExtendFun#onExtendBtnsClick(nc.ui.pub.ButtonObject)
	 * 
	 */
	public void onExtendBtnsClick(ButtonObject bo) {

	}

	/**
	 * ���ο���״̬��ԭ�н���״̬������Ҫ����ο��������������ʵ��
	 * 
	 * @see nc.ui.scm.pub.bill.IBillExtendFun#setExtendBtnsStat(int)
	 * 
	 *      ״̬��ֵ���ձ�
	 * 
	 *      0����ʼ�� 1�������Ƭ 2���޸� 3������б� 4��ת���б�
	 */
	public void setExtendBtnsStat(int iState) {

	}

	/**
	 * ��������ӿڷ���ʵ�� -- ά��
	 */
	public void doMaintainAction(ILinkMaintainData maintaindata) {
		SCMEnv.out("����ί�ⶩ���ӿ�...");

		if (maintaindata == null)
			return;

		String billID = maintaindata.getBillID();

		init();
		// û�з��������Ķ���
		try {
			getModel().setCurrentIndex(0);
			loadData(billID);
			setPrecision();
		} catch (Exception e) {
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					"401201", "UPP401201-000041")/* @res "�������ݳ���" */);
			// ���ð�ť״̬
			boAudit.setEnabled(false);
			btnBillCombin.setEnabled(false);
			boQueryForAudit.setEnabled(false);
			boDocument.setEnabled(false);
			updateButtons();

			return;
		}

		getBillCardPanel().setEnabled(false);
		// ���ð�ť��
		setButtons(m_btnTree.getButtonArray());

		// ���ð�ť״̬
		setButtonsState();
		// ά������״̬�����ϡ��޸ģ�
		int ibillstate = ((OrderHeaderVO) m_curOrderVO.getParentVO())
				.getIbillstatus().intValue();
		if (((OrderHeaderVO) m_curOrderVO.getParentVO()).getPrimaryKey()
				.equals(billID))
			setOperateState(ibillstate);
		updateButtons();

	}

	/**
	 * ��������ӿڷ���ʵ�� -- ����
	 */
	public void doAddAction(ILinkAddData adddata) {
		if (adddata.getUserObject() == null)
			return;

		SCMessageVO message = (SCMessageVO) adddata.getUserObject();
		/* �ɼ۸�����������ʱ���õĽ��� */
		processAfterChange(ScmConst.PO_PriceAudit,
				new nc.vo.pub.AggregatedValueObject[] { message.getAskvo() });
	}

	/**
	 * ��������ӿڷ���ʵ�� -- ����
	 */
	public void doApproveAction(ILinkApproveData approvedata) {
		if (approvedata == null
				|| StringUtil.isEmptyWithTrim(approvedata.getBillID()))
			return;
		PFOpenOperation(approvedata.getPkOrg(), approvedata.getBillID());

	}

	/**
	 * ��������ӿڷ���ʵ�� -- ������
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
		// û�з��������Ķ���
		try {
			getModel().setCurrentIndex(0);

			// �Ȱ��յ���PK��ѯ���������Ĺ�˾corpvalue
			vo = OrderHelper.findByPrimaryKey(billID);
			if (vo == null) {
				MessageDialog.showHintDlg(
						this,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000270")/* "��ʾ" */,
						NCLangRes.getInstance().getStrByID("SCMCOMMON",
								"UPPSCMCommon-000397")/* "û�з�������������" */);
				return;
			}
			String strPkCorp = vo.getPk_corp();

			// v5.1����Ȩ�ޣ����ز�ѯģ��(ע��ȥ��ģ��Ĭ��ֵ)����ѯģ����û�й�˾ʱ��Ҫ�������⹫˾
			SCMQueryConditionDlg queryDlg = new SCMQueryConditionDlg(this);
			if (queryDlg.getAllTempletDatas() == null
					|| queryDlg.getAllTempletDatas().length <= 0) {
				if (pk_corp == null) {
					// ָ����˾Ϊ�գ���ȡ��ǰ��½��˾
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
			// ���ù���������ȡ�ù�˾�п���Ȩ�޵ĵ�������VO����
			ConditionVO[] voaCond = queryDlg.getDataPowerConVOs(strPkCorp,
					IDataPowerForSC.REFKEYSFORSC);

			// ��֯�ڶ��β�ѯ���ݣ�����Ȩ�޺͵���PK����
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
								"UPPSCMCommon-000270")/* "��ʾ" */,
						NCLangRes.getInstance().getStrByID("common",
								"SCMCOMMON000000161")/* "û�в쿴���ݵ�Ȩ��" */);
				return;
			}
			billID = ((OrderHeaderVO) orderHeaderVO[0]).getCorderid();// ֻ��һ������
			getModel().add((OrderHeaderVO) orderHeaderVO[0]);

			loadData(null);
			setPrecision();
		} catch (Exception e) {
			SCMEnv.out(e);
			MessageDialog.showHintDlg(
					this,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
							"UPPSCMCommon-000270")/* @res "��ʾ" */,
					nc.ui.ml.NCLangRes.getInstance().getStrByID("401201",
							"UPP401201-000034")/* @res "��ѯί�ⶩ������" */);
		}

		getBillCardPanel().setEnabled(false);
	}

	/**
	 * VO�������ʵ��IbillRelaSortListener�ӿ� -- VO����ͽ������򻺴��ͬ��
	 */
	public List getRelaSortObject() {
		return getModel().getHeaderList();
	}

	/**
	 * <p>
	 * ί�ⶩ�����տ繫˾��ͬҵ���£�Դ��˾��Ŀ�깫˾����̡��������ĿID��ת��
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
			SCMEnv.out("��������ת����ǰ����ҵ�����ͷǿ�!");/* -=notranslate=- */
			return;
		}

		// �����Ƿ���Դ�ں�ͬ
		boolean bFromCt = sUpBillType != null
				&& (sUpBillType
						.equals(nc.vo.scm.pu.BillTypeConst.CT_BEFOREDATE) || sUpBillType
						.equals(nc.vo.scm.pu.BillTypeConst.CT_ORDINARY));

		// �������������Ҫ����1��ҵ�������Ǽ���;2�����ҵ������Ϊ��˾���Ҷ�����Դ�Ǻ�ͬ�����򷵻�
		if (!CentrPurchaseUtil.isGroupBusiType(sBiztypeId) && !bFromCt) {
			SCMEnv.out("��������ת����ǰ����ҵ������Ϊ����ҵ������!");/* -=notranslate=- */
			return;
		}

		// ��ȡҪת���ĵ���VO
		ArrayList listVendorId = new ArrayList();
		ArrayList listProjectId = new ArrayList();
		ArrayList listInventoryId = new ArrayList();
		ChgDocPkVO chgDocVo = null;
		String strCntCorpId = null;// ��Դ��˾ID
		String strPurCorpId = null;// Ŀ�깫˾ID
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
			strPurCorpId = getCorpPrimaryKey();// ����Ŀ�깫˾ID Ϊ��ǰ��¼��˾

			items = (OrderItemVO[]) vos[i].getChildrenVO();
			// ��Ӧ��
			chgDocVo = new ChgDocPkVO();
			chgDocVo.setSrcCorpId(items[0].getPk_corp());// �����ϲ���Դ��˾ID��Ĭ�Ϸֵ�
			chgDocVo.setDstCorpId(strPurCorpId);
			chgDocVo.setSrcBasId(head.getCvendorid());// ��Ӧ�̻�������ID
			listVendorId.add(chgDocVo);

			jLen = items.length;
			for (int j = 0; j < jLen; j++) {
				if (items[j] == null) {
					continue;
				}
				strCntCorpId = items[j].getPk_corp();

				// ��Ŀ
				if (PuPubVO.getString_TrimZeroLenAsNull(items[j]
						.getCprojectid()) != null) {
					chgDocVo = new ChgDocPkVO();
					chgDocVo.setSrcCorpId(strCntCorpId);
					chgDocVo.setDstCorpId(strPurCorpId);
					chgDocVo.setSrcManId(items[j].getCprojectid());// ��Ŀ����ID
					listProjectId.add(chgDocVo);
				}
				// ���
				if (PuPubVO.getString_TrimZeroLenAsNull(items[j].getCbaseid()) != null) {
					chgDocVo = new ChgDocPkVO();
					chgDocVo.setSrcCorpId(strCntCorpId);
					chgDocVo.setDstCorpId(strPurCorpId);
					chgDocVo.setSrcBasId(items[j].getCbaseid());// �������ID
					listInventoryId.add(chgDocVo);
				}
			}
		}
		// ת��
		int iSize = listVendorId.size();
		if (iSize == 0) {
			return;
		}
		// ����ת��
		ChgDocPkVO[] vendorVos = null;
		vendorVos = (ChgDocPkVO[]) listVendorId.toArray(new ChgDocPkVO[iLen]);
		vendorVos = ChgDataUtil.chgPkCuByCorpBase(vendorVos);

		// ��Ŀת��
		ChgDocPkVO[] projectVos = null;
		projectVos = (ChgDocPkVO[]) listProjectId
				.toArray(new ChgDocPkVO[listProjectId.size()]);
		projectVos = ChgDataUtil.chgPkjobByCorp(projectVos);

		// ���ת��
		ChgDocPkVO[] inventoryVos = null;
		inventoryVos = (ChgDocPkVO[]) listInventoryId
				.toArray(new ChgDocPkVO[listInventoryId.size()]);
		inventoryVos = ChgDataUtil.chgPkInvByCorpBase(inventoryVos);

		// ����ת��������ݵ�vos
		int iPosProj = 0;
		int iPosBody = 0;
		for (int i = 0; i < iLen; i++) {
			if (vos[i].getChildrenVO() == null
					|| vos[i].getChildrenVO().length == 0
					|| vos[i].getChildrenVO()[0] == null) {
				continue;
			}
			// ��Ӧ������
			head.setCvendormangid(vendorVos[i].getDstManId());
			// ��Ʊ��
			// head.setCgiveinvoicevendor(vendorVos[i].getDstManId());
			//
			items = (OrderItemVO[]) vos[i].getChildrenVO();
			jLen = items.length;
			for (int j = 0; j < jLen; j++) {
				if (items[j] == null) {
					continue;
				}
				// ��Ŀ����
				if (PuPubVO.getString_TrimZeroLenAsNull(items[j]
						.getCprojectid()) != null) {
					items[j].setCprojectid(projectVos[iPosProj].getDstManId());
					iPosProj++;
				}
				// �������
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
	 * �˷����Ѵ���<code>nc.ui.scm.pub.panelBillPanelTool</code>��
	 * <p>
	 * <strong>���ܣ�</strong>�ֿ�������֯�Ĺ�ϵ�� �����֯�仯�󣬲ֿ���ձ仯��
	 * �����֯��պ󣬲ֿⲻ��գ������֯�ı䣬�ֿ���գ�����زֿ��ֶ����
	 * <p>
	 * <strong>�޸�������</strong>�޸�ί�ⶩ������ʱ��飬�������֯�����仯���������ֿ⣬������ֿ�仯�������ñ仯��״̬Ϊ
	 * "BillModel.MODIFICATION"
	 * (ע������ɱ�ͷ�仯���������仯�����,getBillValueChangeVO()�������ܵõ��仯�ı���)
	 * 
	 * @author lxd
	 * @date 2006-11-20
	 * @param BillCardPanel
	 *            pnlBill ��ǰ����ģ�� ����Ϊ��
	 * @param BillEditEvent
	 *            e ��׽����BillEditEvent�¼�������Ϊ��
	 * @param String
	 *            pk_corp ��˾ID����Ϊ��
	 * @param String
	 *            sCalBodyId �����֯ID����Ϊ��
	 * @param String
	 *            sKeyBodyWareRef ����ֿ��ģ��KEY������Ϊ�� String[]
	 * @param String
	 *            [] saKeyBodyWarehouse ������ֿ���صı���KEY���������û���������Ϊ�� [i]
	 * @return void
	 * @throws ��
	 * @since V5.0
	 * @see
	 */
	private void afterEditStoreOrgToWarehouse(BillCardPanel pnlBill,
			BillEditEvent e, String pk_corp, String sCalBodyId,
			String sKeyBodyWareRef, String[] saKeyBodyWarehouse) {// gc ������

		// ������ȷ�Լ��
		if (// ����ģ�塢��˾�������֯ID���ֿ���ա�������ֿ���صı���KEY���������û���������Ϊ�� [i]
		pnlBill == null || e == null || pk_corp == null
				|| pk_corp.trim().length() == 0 || sKeyBodyWareRef == null
				|| sKeyBodyWareRef.trim().length() == 0) {
			SCMEnv.out("����nc.ui.sc.order.OrderUI.afterEditStoreOrgToWarehouse(BillListPanel, BillEditEvent, String, String, String, String[])�����������");
			return;
		}

		// �����֯ID
		if (sCalBodyId != null && sCalBodyId.trim().length() != 0) {
			// ������вֿ�
			int iRowCount = pnlBill.getRowCount();
			for (int i = 0; i < iRowCount; i++) {
				// �ֿ��������KEY���
				pnlBill.setBodyValueAt(null, i, sKeyBodyWareRef);
				// �����ֿ��ֶ����
				if (saKeyBodyWarehouse != null) {
					int iWareColLen = saKeyBodyWarehouse.length;
					for (int j = 0; j < iWareColLen; j++) {
						if (saKeyBodyWarehouse[j] != null
								&& saKeyBodyWarehouse[j].trim().length() > 0) {
							pnlBill.setBodyValueAt(null, i,
									saKeyBodyWarehouse[j]);
							if (m_billState == ScConstants.STATE_MODIFY) {// ����ֻ���޸�״̬�²��޸�������
								pnlBill.getBillModel(TAB1).setRowState(i,
										BillModel.MODIFICATION);
							}
						}
					}
				}
			}
		}

		// ��������
		BillPanelTool.restrictWarehouseRefByStoreOrg(pnlBill, pk_corp,
				sCalBodyId, sKeyBodyWareRef);
	}

	/**
	 * ������������������ҵ��ԱĬ��ֵ ���ݲ���Ա����ҵ��Ա��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author lixiaodong
	 * @time 2007-3-29 ����10:42:05
	 */
	private void setDefaultValueByUser() {
		// ����ҵ��ԱĬ��ֵ ���ݲ���Ա����ҵ��Ա
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
						"cdeptid").getComponent();// ��������
				cdeptid.setPK(voPsnDoc.getPk_deptdoc());
			}
		}
	}

	/**
	 * 
	 * ������������������������е��кš�
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author zhaoyha
	 * @time 2008-8-15 ����06:44:50
	 */
	private void onReOrderRowNo() {
		getBillCardPanel().stopEditing();
		BillRowNo.addNewRowNo(getBillCardPanel(), ScmConst.SC_Order,
				ScConstants.SC_ORDER_BODY_ROWNUM);
	}

	/**
	 * 
	 * �������������������еĿ�Ƭ�༭��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * <p>
	 * 
	 * @author zhaoyha
	 * @time 2008-8-14 ����10:21:04
	 */
	protected void onBoCardEdit() {
		// ������ע�ֶΡ����Ȱ�������Ϊ�ַ���
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
		// ��Ƭ�༭
		getBillCardPanel().startRowCardEdit();
		if (isRef)
			getBillCardPanel().getBillModel(TAB1).getItemByKey("vmemo")
					.setDataType(dataType);// ��Ƭ�༭��󣬻ָ�����
	}

	/**
	 * 
	 * ���෽����д
	 * 
	 * @see nc.ui.pub.bill.BillActionListener#onEditAction(int)
	 */
	public boolean onEditAction(int action) {
		if (action == BillScrollPane.ADDLINE) {
			// ���������ӱ�־,�Ա������Ӻ�ɱ�BillEditListener��
			// bodyRowChange()����
			// ��ǰ�����־ֻ����bodyRowChange()����,���������벻Ҫʹ��
			isAddedLine = true;

		} else if (action == BillScrollPane.DELLINE) {
			// ���ֻ��һ��������ɾ��V55
			if (getBillCardPanel().getRowCount() == 1) {
				showErrorMessage(NCLangRes.getInstance().getStrByID(
						"scmcommon", "UPPSCMCommon-000489")
				/* ����ɾ�������е�ȫ���У� */
				);
				return false;
			}
		}
		return true;
	}

	/**
	 * 
	 * �����������������������кŲ˵��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @return ���δ��ʼ���˵���򷵻�null
	 *         <p>
	 * @author zhaoyha
	 * @time 2008-8-28 ����06:09:07
	 */
	public UIMenuItem getReOrderRowNoMenuItem() {
		return miReOrderRowNo;
	}

	/**
	 * 
	 * �����������������ؿ�Ƭ�༭�˵��
	 * <p>
	 * <b>examples:</b>
	 * <p>
	 * ʹ��ʾ��
	 * <p>
	 * <b>����˵��</b>
	 * 
	 * @return ���δ��ʼ���˵���򷵻�null
	 *         <p>
	 * @author zhaoyha
	 * @time 2008-8-28 ����06:12:57
	 */
	public UIMenuItem getCardEditMenuItem() {
		return miCardEdit;
	}

	/**
	 * ��Ƭ����༭ʱ���ñ���� rowIndex �е��۱����ʾ��ȡ� rowIndex ���㿪ʼ����
	 * 
	 * @param rowIndex
	 *            �б�
	 */
	// private void setExchangeRatePrecision(int rowIndex) {
	// // �� rowIndex �еı���ID
	// String sCurrId = (String)getBillCardPanel()
	// .getBodyValueAt(rowIndex, "ccurrencytypeid");
	//
	// // ������ʾ����
	// int[] iaExchRateDigit = m_cardPoPubSetUI2.getBothExchRateDigit(
	// getPk_corp(), sCurrId);
	// if ( (iaExchRateDigit != null) && (iaExchRateDigit.length > 0) ) {
	// getBillCardPanel().getBillModel(TAB1).getItemByKey("nexchangeotobrate")
	// .setDecimalDigits( iaExchRateDigit[0] );
	// }
	// }
	/**
	 * ��ȡ���ʾ������ù���
	 */
	public POPubSetUI2 getPoPubSetUi2() {
		if (m_cardPoPubSetUI2 == null) {
			m_cardPoPubSetUI2 = new POPubSetUI2();
		}
		return m_cardPoPubSetUI2;
	}

	/**
	 * ����ID�õ���ʾ��״̬
	 * 
	 * @param
	 * @return
	 * @exception �쳣����
	 * @see ��Ҫ�μ�����������
	 * @since �������һ���汾���˷�������ӽ���������ѡ��
	 * 
	 */
	private void setMaxMnyDigit(int iMaxDigit) {
		// ������
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
	 * ���ߣ���ӡ�� ���ܣ����ñ����۱����۸����ʵ�ֵ��������ɱ༭�� ������ int iRow boolean bResetExchValue
	 * �Ƿ���Ҫ�������ñ����еĻ���ֵ ���أ��� ���⣺�� ���ڣ�(2002-5-13 11:39:21) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
	 */
	private void setExchangeRateBody(int iRow, boolean bResetExchValue) {// gc
																			// ������
		String dOrderDate = (String) getBillCardPanel().getHeadItem(
				"dorderdate").getValueObject();
		String sCurrId = (String) getBillCardPanel().getBodyValueAt(iRow,
				"ccurrencytypeid");
		// ֵ
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

		// �ɱ༭��
		boolean[] baEditable = m_cardPoPubSetUI2.getBothExchRateEditable(
				getPk_corp(), sCurrId);
		boolean bEditUserDef0 = getBillCardPanel().getBillModel(TAB1)
				.getItemByKey("nexchangeotobrate").isEdit();
		getBillCardPanel().getBillModel(TAB1).setCellEditable(iRow,
				"nexchangeotobrate", baEditable[0] && bEditUserDef0);

		// �����޸ı�־
		getBillCardPanel().getBillModel(TAB1).setRowState(iRow,
				BillModel.MODIFICATION);
	}

	private boolean checkBeforeSave() {
		StringBuffer errorLins = new StringBuffer();

		for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
			Object l_ob = getBillCardPanel().getBillModel(TAB1).getValueAt(i,
					"nexchangeotobrate");
			if (PuPubVO.getUFDouble_NullAsZero(l_ob).doubleValue() == 0.0) {
				errorLins.append("������"
						+ getBillCardPanel().getBillModel(TAB1).getValueAt(i,
								"crowno") + ":�۱����ʲ���Ϊ��" + "\n");
			}
		}

		if (errorLins.length() > 0) {
			MessageDialog.showWarningDlg(this, nc.ui.ml.NCLangRes.getInstance()
					.getStrByID("40040401", "UPPSCMCommon-000270")/*
																 * @res "��ʾ"
																 */, errorLins
					.toString());
			return false;
		}
		return true;
	}

	/**
	 * ���󷵻ص��Ƶ���ʱ����Ҫ�ڴ���������ˡ��������ڡ�����ʱ��
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
	 * ���ί�ⶩ���Ŀ�Ƭ�б�ģ��VO
	 * 
	 * @return ��Ƭ�б�ģ��VO
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
	 * ��ȡ��ͬ�Ƿ�����
	 * 
	 * @return ��ͬ���� true; ��ͬ������ false;
	 */
	private boolean isCtStartUp() {
		if (!parasHaveLoaded) {
			loadParas();
		}

		return m_bCTStartUp;
	}

	/**
	 * ��ȡ���ҽ���
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
	 * ��ȡ��������
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
	 * ��ȡ����������
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
	 * ��ȡת���ʾ���
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
	 * ��ȡ�۸񾫶�
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
	 * �Ӻ�̨���ز�������ͬ�Ƿ����ã����ҽ��ȣ��������ȣ����������ȣ�ת���ʾ��ȣ��۸񾫶�
	 */
	private void loadParas() {
		try {
			/** ********************************* */
			Object[] objs = null;
			ServcallVO[] scds = new ServcallVO[3];

			// ��ͬ�Ƿ�����
			scds[0] = new ServcallVO();
			scds[0].setBeanName("nc.itf.uap.sf.ICreateCorpQueryService");
			scds[0].setMethodName("isEnabled");
			scds[0].setParameter(new Object[] { getPk_corp(),
					ProductCode.PROD_CT });
			scds[0].setParameterTypes(new Class[] { String.class, String.class });

			// ��������
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

			// ��ͬ�Ƿ�����
			m_bCTStartUp = ((Boolean) objs[0]).booleanValue();// ��ͬ�Ƿ�����

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
																 * "��ȡϵͳ��ʼ����������"
																 */, e
					.getMessage());
		}
	}

	/**
	 * ��ȡ��Ƭ�����ͷ���Ӧ��ֵ
	 * 
	 * @param itemKey
	 * @return
	 */
	private Object getCardHeadItemValue(String itemKey) {
		return getBillCardPanel().getHeadItem(itemKey).getValueObject();
	}

	/**
	 * ��ȡ��Ƭ����������Ӧ��ֵ
	 * 
	 * @param rowIndex
	 *            �����б�ʶ����0��ʼ����
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
	 * ���ÿ�Ƭ����������Ӧ��ֵ
	 * 
	 * @param rowIndex
	 *            �����б�ʶ����0��ʼ����
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
	 * ��ȡί�ⶩ����ǰ̨��DataModel
	 * 
	 */
	OrderModel getModel() {
		// if (orderModel == null) {
		// orderModel = new OrderModel();
		// }

		// modify by hanbin ԭ�򣺰ѻ���ģ�Ͷ���ת�Ƶ�ListPanel�ϣ���������Ҳ���ö������б����
		orderModel = this.getBillListPanel().getModel();
		return orderModel;
	}

	/**
	 * �õ�ѡ���ҳ
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