package nc.ui.ic.pub.bill;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Vector;

import javax.swing.JFileChooser;

import nc.bs.framework.common.NCLocator;
import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.ic.ic001.BatchCodeDefSetTool;
import nc.ui.ic.ic001.BatchcodeHelper;
import nc.ui.ic.ic009.PackCheckBusDialog;
import nc.ui.ic.pub.BarcodeValidateDialog;
import nc.ui.ic.pub.BillFormulaContainer;
import nc.ui.ic.pub.ICCommonBusi;
import nc.ui.ic.pub.InvOnHandDialog;
import nc.ui.ic.pub.LongTimeTask;
import nc.ui.ic.pub.PageCtrlBtn;
import nc.ui.ic.pub.QueryInfo;
import nc.ui.ic.pub.QueryOnHandInfoPanel;
import nc.ui.ic.pub.bc.BarCodeDlg;
import nc.ui.ic.pub.bill.cell.FreeItemCellRender;
import nc.ui.ic.pub.bill.cell.LotItemRefCellRender;
import nc.ui.ic.pub.bill.initref.RefFilter;
import nc.ui.ic.pub.bill.query.QueryConditionDlgForBill;
import nc.ui.ic.pub.corbillref.ICCorBillRefUI;
import nc.ui.ic.pub.locatorref.LocatorRefPane;
import nc.ui.ic.pub.print.PrintDataInterface;
import nc.ui.ic.pub.vmi.OutDetailDlg;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.FramePanel;
import nc.ui.pub.ToftPanel;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIFileChooser;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.bill.BillCardBeforeEditListener;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillListData;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillModelCellEditableController;
import nc.ui.pub.bill.BillSortListener2;
import nc.ui.pub.bill.BillUIUtil;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.pub.linkoperate.ILinkAdd;
import nc.ui.pub.linkoperate.ILinkAddData;
import nc.ui.pub.linkoperate.ILinkApprove;
import nc.ui.pub.linkoperate.ILinkApproveData;
import nc.ui.pub.linkoperate.ILinkMaintain;
import nc.ui.pub.linkoperate.ILinkMaintainData;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.scm.ic.exp.GeneralMethod;
import nc.ui.scm.ic.exp.SetColor;
import nc.ui.scm.ic.freeitem.FreeItemRefPane;
import nc.ui.scm.ic.measurerate.InvMeasRate;
import nc.ui.scm.ic.setpart.SetPartDlg;
import nc.ui.scm.plugin.InvokeEventProxy;
import nc.ui.scm.pub.AccreditLoginDialog;
import nc.ui.scm.pub.bill.IBillExtendFun;
import nc.ui.scm.pub.def.DefSetTool;
import nc.ui.scm.pub.print.DefaultFormulaJudge;
import nc.vo.bd.b06.PsndocVO;
import nc.vo.bd.def.DefVO;
import nc.vo.bill.pub.BillUtil;
import nc.vo.ic.ic001.BatchcodeVO;
import nc.vo.ic.ic700.ICDataSet;
import nc.vo.ic.pub.BillTypeConst;
import nc.vo.ic.pub.GenMethod;
import nc.vo.ic.pub.ICConst;
import nc.vo.ic.pub.ICSourceBillPara;
import nc.vo.ic.pub.ScaleKey;
import nc.vo.ic.pub.ScaleValue;
import nc.vo.ic.pub.SmartVOUtilExt;
import nc.vo.ic.pub.barcodeparse.BarCodeGroupHeadVO;
import nc.vo.ic.pub.barcodeparse.BarCodeGroupVO;
import nc.vo.ic.pub.barcodeparse.BarCodeParseVO;
import nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl;
import nc.vo.ic.pub.bill.BillStatus;
import nc.vo.ic.pub.bill.GeneralBillHeaderVO;
import nc.vo.ic.pub.bill.GeneralBillItemVO;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.bill.IICParaConst;
import nc.vo.ic.pub.bill.IItemKey;
import nc.vo.ic.pub.bill.QryConditionVO;
import nc.vo.ic.pub.bill.QryInfoConst;
import nc.vo.ic.pub.billtype.BillTypeFactory;
import nc.vo.ic.pub.billtype.IBillType;
import nc.vo.ic.pub.billtype.ModuleCode;
import nc.vo.ic.pub.check.CheckTools;
import nc.vo.ic.pub.check.VOCheck;
import nc.vo.ic.pub.check.VoCombine;
import nc.vo.ic.pub.exp.ICBusinessException;
import nc.vo.ic.pub.exp.RightcheckException;
import nc.vo.ic.pub.lang.ResBase;
import nc.vo.ic.pub.locator.LocatorVO;
import nc.vo.ic.pub.smallbill.SMGeneralBillVO;
import nc.vo.ic.pub.sn.SerialVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.VOStatus;
import nc.vo.pub.ValidationException;
import nc.vo.pub.bill.BillTempletVO;
import nc.vo.pub.formulaset.util.StringUtil;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.lang.UFTime;
import nc.vo.pub.query.ConditionVO;
import nc.vo.scm.bd.SmartVODataUtils;
import nc.vo.scm.constant.ScmConst;
import nc.vo.scm.constant.ic.BillMode;
import nc.vo.scm.constant.ic.CONST;
import nc.vo.scm.constant.ic.InOutFlag;
import nc.vo.scm.ic.bill.FreeVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.bill.WhVO;
import nc.vo.scm.ic.exp.ATPNotEnoughException;
import nc.vo.scm.ic.exp.HeaderRenderer;
import nc.vo.scm.ic.exp.ICDateException;
import nc.vo.scm.ic.exp.ICHeaderNullFieldException;
import nc.vo.scm.ic.exp.ICLocatorException;
import nc.vo.scm.ic.exp.ICNullFieldException;
import nc.vo.scm.ic.exp.ICNumException;
import nc.vo.scm.ic.exp.ICPriceException;
import nc.vo.scm.ic.exp.ICRepeatException;
import nc.vo.scm.ic.exp.ICSNException;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.smart.ObjectUtils;
import nc.vo.scm.pub.smart.SmartFieldMeta;
import nc.vo.so.so120.CreditNotEnoughException;
import nc.vo.so.so120.PeriodNotEnoughException;
import nc.vo.to.pub.BillVO;

public abstract class GeneralBillClientUI extends ToftPanel implements
    javax.swing.event.TableModelListener, nc.ui.pub.bill.BillEditListener,
    nc.ui.pub.bill.BillEditListener2,
    nc.ui.pub.bill.BillTableMouseListener,
    nc.ui.pub.bill.BillBodyMenuListener, nc.ui.pub.bill.BillTotalListener,
    nc.ui.ic.pub.barcode.BarCodeInputListenerNew,
    BillModelCellEditableController, BillCardBeforeEditListener,
    IBillExtendFun, ILinkAdd, ILinkMaintain, ILinkApprove, ILinkQuery,
    BillSortListener2, ActionListener, IGeneralBill {

  //protected String m_sBnoutnumnull = null; to QueryDlgHelp

  public static final int CANNOTSIGN = -1; // ����ǩ��

  public static final int NOTSIGNED = 0; // δǩ��//����ǩ��״̬

  public static final int SIGNED = 1; // ��ǩ��
  public static final String qualitylevelName=null;//�����ȼ�

  private Environment m_environment;

  // �������ͱ���
  protected String m_sTitle = null; // ����

  protected final int m_iInitRowCount = 1; // ������ʼ״̬�µ�����

  protected String m_sCurBillOID = null; // ��ǰ�ĵ���id.

  private int m_iMode = BillMode.Browse; // ��ǰ�ĵ��ݱ༭״̬.

  private boolean m_bOnhandShowHidden = false; // �Ƿ���ʾ��������

  public String m_sMultiMode = MultiCardMode.CARD_PURE;// �࿨Ƭ״̬

  private ToftLayoutManager m_layoutManager = new ToftLayoutManager(this);// ���ֹ�����

  // protected AfterEditCtrl m_afterEditCtrl = new AfterEditCtrl(this);

  protected EditCtrl m_editCtrl = new EditCtrl(this);

  private int m_iCurPanel = BillMode.Card; // ��ǰ��ʾ��panel.

  // �б���ʽ����
  protected int m_iBillQty = 0; // ��ǰ�б���ʽ�µĵ�������

  private ArrayList<GeneralBillVO> m_alListData = null; // �б�����
  //�޸��ˣ������� �޸�ʱ�䣺2008-7-17 ����07:09:51 �޸�ԭ��
  private ArrayList<GeneralBillVO> m_alRefBillsBak = null; // �б�����

  private int m_iLastSelListHeadRow = -1; // ���ѡ�е��б��ͷ�С�

  protected int m_iCurDispBillNum = -1;

  // ����ʽ�µ�ǰ��ʾ�ĵ�����ţ����б�---�����л�ʱ��δ��ѡ�������ݣ���������������ݣ������Ч�ʡ�
  // vo�������ڶ�ȡ�޸�����ʱ

  // ������VO
  private GeneralBillVO m_voBill = null;

  // ���浱ǰ�������޸ĵĵ��ݵĻ�λ��������...Ҫ��Ҫ�����б���ʽ�����е����ݣ�
  private ArrayList m_alLocatorData = null;

  protected ArrayList m_alLocatorDataBackup = null;

  // ���ݱ��ݣ�������ʱ���� m_alLocatorData��ȡ��ʱ�ָ�m_alLocatorData.
  // ���浱ǰ�������޸ĵĵ��ݵ����кŷ�������...Ҫ��Ҫ�����б���ʽ�����е����ݣ�
  private ArrayList m_alSerialData = null;

  protected ArrayList m_alSerialDataBackup = null;

  // ���кŶԻ���
  protected nc.ui.ic.pub.sn.SerialAllocationDlg m_dlgSerialAllocation = null;

  // ��λ�Ի���
  protected nc.ui.ic.pub.locator.SpaceAllocationDlg m_dlgSpaceAllocation = null;

  // ��Ƭ
  protected nc.ui.pub.bill.BillCardPanel ivjBillCardPanel = null;

  // �б�
  protected nc.ui.pub.bill.BillListPanel ivjBillListPanel = null;

  // ���������
  protected FreeItemRefPane ivjFreeItemRefPane = null;

  // ���β���
  protected nc.ui.ic.pub.lot.LotNumbRefPane ivjLotNumbRefPane = null;

  // ���β���
  protected nc.ui.ic.pub.tools.VehicleRefPanel ivjVehicleRefPane = null;

  // �и���VO
  protected GeneralBillItemVO[] m_voaBillItem = null;

  // ״̬��
  protected javax.swing.JTextField m_tfHintBar = null;

  // ��Ӧ��ⵥ�Ĳ���//��Ӧ���ݲ���
  protected nc.ui.ic.pub.corbillref.ICCorBillRefPane m_aICCorBillRef = null;

  protected InvOnHandDialog m_iohdDlg = null;

  // �������������ʡ�
  protected InvMeasRate m_voInvMeas = new InvMeasRate();

  boolean m_isWhInvRef = false;

  // ��λ����
  private LocatorRefPane ivjLocatorRefPane = null;

  // ��ѯ�Ի���
  //protected QueryConditionDlgForBill ivjQueryConditionDlg = null; to QueryDlgHelp

  // added by zhx �������Ƿ�ʹ�ù�ʽ�ı�־;
  protected boolean m_bIsByFormula = true;

  // ���ݺ��Ƿ���������
  protected boolean m_bIsEditBillCode = false;

  // �Ƿ��ڳ�����,ȱʡ���ǡ�
  protected boolean m_bIsInitBill = false;

  // �Ƿ���Ҫ���ݲ���¼��˵���
  protected boolean m_bNeedBillRef = true;

  // �Ƿ���ϵͳtoftpanelȱʡ�Ĵ�����ʾ�Ի���
  protected boolean m_bUserDefaultErrDlg = true;

  // ��ʼ����ӡ�ӿ�
  protected PrintDataInterface m_dataSource = null;

  // ������������ࡣ
  protected nc.ui.ic.pub.device.DevInputCtrl m_dictrl = null;

  /** ��λ�Ի��� */
  protected nc.ui.ic.pub.orient.OrientDialog m_dlgOrient = null;
  
  protected HashMap<String,WhVO> hm_whid_vo = new HashMap<String, WhVO>();

  // ����ʱ��
//  protected nc.vo.pub.lang.UFDateTime m_dTime = null;

  public Hashtable m_htBItemEditFlag = null;

  // add by zhx
  // ��ʼ��ʱ,���浥��ģ���ж���ı�ͷ,�����������Ƿ�ɱ༭, ����ҵ���������������Ƿ�ɱ༭���ж�.
  public Hashtable m_htHItemEditFlag = null;

  // С�����ȶ���--->
  // ����С��λ 2
  // ����������С��λ 2
  // ������ 2
  // ����ɱ�����С��λ 2
  protected ScaleValue m_ScaleValue = new ScaleValue();

  protected ScaleKey m_ScaleKey = new ScaleKey();
  
  protected String m_sIC026 = null;
  
  protected String m_sIC040 = null;

  // /////////////////////////////////////////
  // ��ʽ������Ҫ�����ȫ�ֱ��� by hanwei 2003-06-26
  private InvoInfoBYFormula m_InvoInfoBYFormula;

  // ///////////////////////////////////////

  protected nc.ui.pub.print.PrintEntry m_print = null;

  // ��Ŀ����
  protected nc.ui.pub.beans.UIRefPane m_refJob = null;

  // ��Ŀ�׶β���
  protected nc.ui.pub.beans.UIRefPane m_refJobPhase = null;

  protected nc.ui.bd.b39.PhaseRefModel m_refJobPhaseModel = null;

  // ģ����������
  protected String m_sStartDate = null;

  // ��Ʒ��
  /* ��־�õ����Ƿ�Ϊ����¼�뵥�ݣ�Ĭ��Ϊ���Ƶ��ݣ�* */
  // boolean bIsRefBill = false;
  /* ������Դ���ݲ������ɵĵ���VO* */
  protected GeneralBillVO m_voBillRefInput = null;

  // ���һ�εĲ�ѯ������������ˢ�¡��������б���ʽ�µĴ�ӡ��
  //protected QryConditionVO m_voLastQryCond = null; to QueryDlgHelp

  // ֧�ֶ��ο����Ĺ�����չ
  private nc.ui.scm.extend.IFuncExtend m_funcExtend = null;

  // �������ʸı�ʱ���ǹ̶������ʣ�Ĭ���Ǹ����������������䡣�����෴��
  protected boolean m_bAstCalMain = false;

  // ����ǻ����ʴ���afterAstNumEdit����afterNumEdit,��ô��afterNumEdit�оͲ���Ҫ��ȥ����afterAstNumEdit
  protected boolean m_isNeedNumEdit = true;

  // �Ƿ�������ѯ���ı�ʶ
  //protected boolean m_bEverQry = false; to QueryDlgHelp

  // �ǽ��в�ѯ���ǽ���ˢ�£�Ϊ�˼�����ǰ�İ汾�������Ӹñ���������ʶ���������ͣ�
  protected boolean m_bQuery = true;

  private String[] m_sItemField = null;// ���幫ʽ

  private ClientUISortCtl m_listHeadSortCtl;// �б��ͷ�������

  private ClientUISortCtl m_listBodySortCtl;// �б�����������

  private ClientUISortCtl m_cardBodySortCtl;// ��Ƭ�����������

  // �û���������У��UI
  protected nc.ui.scm.pub.AccreditLoginDialog m_AccreditLoginDialog;

  // �Ƿ����������� add by hanwei 2004-03-01
  protected boolean m_bAddBarCodeField = true;

  // ����༭���������
  protected nc.ui.ic.pub.bill.BarcodeCtrl m_BarcodeCtrl = null;

  // ���벻�����Ƿ񱣴�
  protected boolean m_bBadBarcodeSave = false;

  // �����Ƿ񱣴�
  // protected boolean m_bBarcodeSave = false;

  // �����������������ӣ��磺�ɹ������˿�
  protected boolean m_bFixBarcodeNegative = false;

  // ���ݹ�ʽ���� hanwei 2003-07-23
  BillFormulaContainer m_billFormulaContain;

  // �Ƿ��е������ hanwei 2003-12-17
  private boolean m_bIsImportData = false;

  // added by zhx ����༭����
  protected BarCodeDlg m_dlgBarCodeEdit = null;

  // ָ����λ
  protected LocSelDlg m_dlgLocSel = null;

  protected GeneralBillUICtl m_GenBillUICtl;

  // ����༭�����ɫ�У�ÿX+1�е���ɫ��Ҫ����
  protected int m_iBarcodeUIColorRow = 20;

  // �ļ��򿪶Ի���
  private nc.ui.ic.pub.tools.FileChooserImpBarcode m_InFileDialog = null;

  // ������λ��Ϣ
  protected boolean m_isLocated = false;

  // ��ҳ����
  protected PageCtrlBtn m_pageBtn;

  // ����״̬
  protected String m_sBillStatus = nc.vo.ic.pub.bill.BillStatus.FREE;

  protected String m_sColorRow = null;

  // ���׼��Ի���
  protected SetPartDlg m_SetpartDlg = null;

  // ��������
  String m_sLastKey = null;

  //����е���ɫ
  protected Color m_cNormalColor = null;
  //��������ʾ����
  protected boolean m_bExchangeColor = false;
  //���϶�λ��ʾ����
  protected boolean m_bLocateErrorColor = true;
  //�й��϶�λ��ʾ����
  protected boolean m_bRowLocateErrorColor = true;
  
  // ʱ����ʾ
  protected nc.vo.ic.pub.bill.Timer m_timer = new nc.vo.ic.pub.bill.Timer();

  private nc.ui.ic.pub.barcode.UIBarCodeTextFieldNew m_utfBarCode = null;

  protected static final UFDouble UFDNEGATIVE = new UFDouble(-1.00); // ����-1.00

  protected static final nc.vo.pub.lang.UFDouble UFDZERO = new nc.vo.pub.lang.UFDouble(
      0.0);

  protected QueryOnHandInfoPanel m_pnlQueryOnHand;// �ִ���Panel

  protected OnHandRefCtrl m_onHandRefDeal;// �ִ������յĴ�������

  protected UIPanel m_pnlBarCode;

  protected BarcodeValidateDialog barcodeValidateDialog;

  protected PackCheckBusDialog packCheckBusDialog;

  protected UIMenuItem miAddNewRowNo = new UIMenuItem(nc.ui.ml.NCLangRes
      .getInstance().getStrByID("SCMCommon", "UPP4008bill-000551")/*
                                     * @res
                                     * "�����к�"
                                     */);
  
  protected UIMenuItem miLineCardEdit = new UIMenuItem(nc.ui.ml.NCLangRes
      .getInstance().getStrByID("common", "SCMCOMMONIC55YB002")/*
                                     * @res
                                     * "��Ƭ�༭"
                                     */);
  
//  protected UIMenuItem miLineBatchEdit = new UIMenuItem(nc.ui.ml.NCLangRes
//      .getInstance().getStrByID("common", "SCMCOMMONIC55YB003")/*
//                                     * @res
//                                     * "����"
//                                     */);

  protected int m_Menu_AddNewRowNO_Index = -1;

  // ��ť�������
  private IButtonManager m_buttonManager;
  
  protected QueryDlgHelp m_qryDlgHelp;
  
  private boolean isLineCardEdit ;
//�ʲ������ڷ��ʲ��ֳ���ʱ�Ƿ���Բ��������к�
  protected boolean m_bCheckAssetInv = false;
  
  // Ϊ���������������ظ�������������������
  private String tempHeaderID = null;
  
//���ο�����չ
  private InvokeEventProxy pluginproxy;
  
  public InvokeEventProxy getPluginProxy() {
    if(this.pluginproxy==null)
      this.pluginproxy = new InvokeEventProxy(ICConst.MODULE_IC,getBillTypeCode(),new ICPluginUI(this));
    return this.pluginproxy;
  }
  
//�м�����
  private long rowCount=1;
  
  protected void setUIVORowPosWhenNewRow(int row,GeneralBillItemVO bodyvo){
    if(row<0)
      return ;
    this.rowCount++;
    String rowpos=String.valueOf(this.rowCount);
    getBillCardPanel().getBillModel().setValueAt(rowpos, row, GeneralBillItemVO.RowPos);
    if(bodyvo==null){
      throw new RuntimeException(NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000000")/*��ʾ����ģ�������в�һ�£������²�����*/);
    }
    bodyvo.setRowpos(rowpos);
  }
  
  protected void synUIVORowPos(){
    //if(getM_iMode()==BillMode.New || getM_iMode()==BillMode.Update){
      if(getM_voBill()==null || getM_voBill().getItemVOs()==null || getM_voBill().getItemVOs().length<=0 ||
         getBillCardPanel().getRowCount()<=0 || getM_voBill().getItemVOs().length!=getBillCardPanel().getRowCount())
        return;
      String uicgeneralhid = (String)getBillCardPanel().getHeadItem("cgeneralhid").getValueObject();
      String vocgeneralhid = getM_voBill().getHeaderVO().getCgeneralhid();
      if(!GenMethod.isEqualObject(uicgeneralhid, vocgeneralhid))
        return;
      for(int i=0,loop=getBillCardPanel().getRowCount();i<loop;i++){
        if(getM_voBill().getItemVOs()[i]==null || getM_voBill().getItemVOs()[i].getCgeneralbid()!=null)
          continue;
        setUIVORowPosWhenNewRow(i,getM_voBill().getItemVOs()[i]);
      }
    //S}
  }

  /**
   * ClientUI ������ע�⡣
   */
  public GeneralBillClientUI() {
    super();
  }

  /**
   * GeneralBillClientUI ������ע�⡣ add by lizuy 2007-12-18 ���ݽڵ�ó�ʼ������ģ��
   */
  public GeneralBillClientUI(FramePanel fp) {
    super();
    setFrame(fp);
  }

  @Override
  public void setFrame(FramePanel fp) {
    super.setFrame(fp);
  }

  /**
   * �����ߣ����˾� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void addRowNums(int rownums) {
    if (BillMode.New == getM_iMode()) {
      for (int i = 1; i <= rownums; i++) {
        getBillCardPanel().addLine();
        // zhx added rowno process 030626
        nc.ui.scm.pub.report.BillRowNo.addNewRowNo(getBillCardPanel(),
            getBillType(), IItemKey.CROWNO);
      }
    }

  }

  /**
   * �����ߣ����˾� ���ܣ����ݱ༭�¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  public void afterEdit(nc.ui.pub.bill.BillEditEvent e) {

    nc.vo.scm.pub.ctrl.GenMsgCtrl.printHint("afterEdit:" + e.getKey());
    if (getM_iMode() == BillMode.Browse)
      return;

    // getBillCardPanel().rememberFocusComponent();

    // �У�ѡ�б�ͷ�ֶ�ʱΪ-1
    int row = e.getRow();
    // �ֶ�itemkey
    String sItemKey = e.getKey();
    // �ֶΣ�λ�� 0: head 1:table
    int pos = e.getPos();

    if (pos == nc.ui.pub.bill.BillItem.BODY && row < 0 || sItemKey == null
        || sItemKey.length() == 0)
      return;

    // ljun
    // ������ë��Ƥ��
    getEditCtrl().afterEdit(e);

    if (sItemKey.equals("vbillcode")) {
      // ���ݺ�

      getEditCtrl().afterBillCodeEdit(e);
    } else if (sItemKey.equals("cdispatcherid")) {
      // �շ����
      getEditCtrl().afterDispatcherEdit(e);
    } else if (sItemKey.equals("cinventoryid")) {//�ӹ�Ʒ
      getEditCtrl().afterCinventoryidEdit(e);
    } else if (sItemKey.equals(IItemKey.WAREHOUSE))
      // �ֿ�
      afterWhEdit(e);
    else if (sItemKey.equals(IItemKey.CALBODY))
      // �����֯
      afterCalbodyEdit(e);

    else if (sItemKey.equals("cwhsmanagerid")) {
      getEditCtrl().afterWhsmanagerEdit(e);
    } else if (sItemKey.equals("cdptid")) {
      getEditCtrl().afterCDptIDEdit(e);
    } else if (sItemKey.equals("cbizid")) {
      // ҵ��Ա
      getEditCtrl().afterCBizidEdit(e);
    } else if (sItemKey.equals("cproviderid")) {
      // ��Ӧ��
      getEditCtrl().afterProviderEdit(e);
    } else if (sItemKey.equals("ccustomerid")) {
      getEditCtrl().afterCustomerEdit(e);

    } else if (sItemKey.equals("cbiztype")) {
      getEditCtrl().afterBiztypeEdit(e);
    } else if (sItemKey.equals("cdilivertypeid")) {
      getEditCtrl().afterDilivertypeEdit(e);
    } else if (sItemKey.equals("vdiliveraddress")) {
      // ���˵�ַ

      getEditCtrl().afterVdiliveraddress(e);

    } else if (sItemKey.equals("vreceiveaddress")) {
      // ���˵�ַ

      getEditCtrl().afterVreceiveaddress(e);

    }
    
    
//      2008.01.23 cy ����Ҫ���ͷ���������޸ĺ󣬱���ҵ���������ͷ��������һ�� begin
    else if (sItemKey.equals("dbilldate"))
    {
      //��������
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("dbilldate").getComponent()).getRefCode();
      
      for (int i=0;i<getBillCardPanel().getBillModel().getRowCount();i++)
      {
        getBillCardPanel().getBillModel().setValueAt(sName, i, "dbizdate");
        
        if (BillModel.NORMAL == getBillCardPanel().getBillModel().getRowState(i))
          getBillCardPanel().getBillModel().setRowState(i, BillModel.MODIFICATION);
      }
    }
      //2008.01.23 cy ����Ҫ���ͷ���������޸ĺ󣬱���ҵ���������ͷ��������һ�� end

    else if (sItemKey.equals("cotherwhid")) {
      getEditCtrl().afterOtherWHEdit(e);
    }
    // �������ı�
    else if (sItemKey.equals("cinventorycode")) {
      afterInvMutiEdit(e);
    } else if (sItemKey.equals("vfree0"))
      afterFreeItemEdit(e);
    else if (sItemKey.equals("castunitname"))
      getEditCtrl().afterAstUOMEdit(e);
    else if (sItemKey.equals(getEnvironment().getNumItemKey())) {
      getEditCtrl().afterNumEdit(e);

    } else if (sItemKey.equals(getEnvironment().getAssistNumItemKey())) {
      getEditCtrl().afterAstNumEdit(e);
    } else if (sItemKey.equals(getEnvironment().getShouldNumItemKey()))
      getEditCtrl().afterShouldNumEdit(e);
    else if (sItemKey.equals(getEnvironment().getShouldAssistNumItemKey()))
      getEditCtrl().afterShouldAstNumEdit(e);
    else if (sItemKey.equals("ncountnum")) {

      getEditCtrl().afterCountNumEdit(e);

    } else // ��Ӧ���ݸı�
    if (sItemKey.equals(IItemKey.CORRESCODE))
      getEditCtrl().afterCorBillEdit(e);
    else if (sItemKey.equals("vspacename"))
      afterSpaceEdit(e);

    // ��������
    else if (sItemKey.equals("scrq")) {
      getEditCtrl().afterScrqEdit(e);
    }
    // ʧЧ����
    else if (sItemKey.equals("dvalidate")) {
      getEditCtrl().afterDvalidateEdit(e);
    } else if (sItemKey.equals("nmny")) {
      getEditCtrl().afterNmnyEdit(e);

    } else if (sItemKey.equals("vbatchcode")) {
      getEditCtrl().afterLotEdit(e);
      // �޸��ˣ������� �޸����ڣ�2007-11-20����09:52:42 �޸�ԭ�򣺻ָ�����ѡ���ʼ���ݡ�
      getLotNumbRefPane().setClicked(false);
      getLotNumbRefPane().getLotNumbDlg().setSelVO(null);
    }
    // ��Ŀ
    else if (sItemKey.equals("cprojectname")) {
      getEditCtrl().afterProjectNameEdit(e);

    }
    // ��Ŀ�׶�
    else if (sItemKey.equals("cprojectphasename")) {
      getEditCtrl().afterProjectPhaseNameEdit(e);
    } // ���幩Ӧ��
    else if (sItemKey.equals("vvendorname")) {
      getEditCtrl().afterVendorNameEdit(e);

    } else // �ɱ�����
    if (sItemKey.equals("ccostobjectname")) {
      getEditCtrl().afterCostObjectNameEdit(e);
    } else // �ջ���λ
    if (sItemKey.equals("creceieveid")) {
      getEditCtrl().afterReceieveEdit(e);
    }

    /** �����ʱ༭�� */
    else if (sItemKey.equals("hsl"))
      getEditCtrl().afterHslEdit(e);

    // ��ע
    else if (sItemKey.equals("vnotebody"))
      getM_voBill().setItemValue(e.getRow(), "vnotebody", e.getValue());
    // ��Ʒ
    else if (sItemKey.equals("flargess")) {
      getEditCtrl().afterFlargessEdit(e);
    }
    // ��;
    else if (sItemKey.equals("bonroadflag")) {
      getEditCtrl().afterOnRoadEdit(e);
    } else if (e.getKey().equals(getEnvironment().getNumItemKey())
        || e.getKey().equals(getEnvironment().getAssistNumItemKey())
        || e.getKey().equals("hsl")
        || e.getKey().equals("castunitname")) {
      getEditCtrl().resetSpace(row);
    } else if (e.getKey().equals(IItemKey.CROWNO)) {// zhx added for bill
      getEditCtrl().afterCrownoEdit(e);

    } else if ((getM_iMode() == BillMode.New || getM_iMode() == BillMode.Update)
        && sItemKey.startsWith("vuserdef")) {// �Զ������zhy
      getEditCtrl().afterVuserDefEdit(e);
    } else if (sItemKey.startsWith(IItemKey.VBCUSER)) {// �������κŵ�����ص��Զ�����
      getEditCtrl().afterVbcuserEdit(e);
    } else if (sItemKey.equals(IItemKey.CQUALITYLEVELNAME)) {
      getEditCtrl().afterQualityLevelNameEdit(e);
    } else { // default set id col name:ȱʡ�����ã��༭�˲�����id�е�ֵ
      getEditCtrl().afterElseDefaultEdit(e);
    }

    // �����Ӧ�л�λ�����к�����
    // zhy2005-08-25��������ⵥ����Ҳ�����˹�Ӧ�̣�����ڴ˴������λ���к�ʱ��Ҫ�ж���������
     //�޸��ˣ������� �޸�ʱ�䣺2008-6-16 ����10:49:05 �޸�ԭ���޸�������������ʱ��������к�
    if (!((sItemKey.equals("vvendorname") && getM_voBill().getItemVOs()[row]
        .getInOutFlag() != InOutFlag.OUT)  || sItemKey.equals(getEnvironment().getNumItemKey()) || sItemKey.equals(getEnvironment().getAssistNumItemKey())))
      getEditCtrl().clearLocSnData(row, sItemKey);

    //
    if (e.getKey().equals(getEnvironment().getNumItemKey())
        || e.getKey().equals(getEnvironment().getAssistNumItemKey())
        || e.getKey().equals("hsl")
        || e.getKey().equals("castunitname")
        || e.getKey().equals("vbatchcode")
        || e.getKey().equals(getEnvironment().getGrossNumItemKey())
        || e.getKey().equals("ntarenum")) {
      getEditCtrl().resetSpace(row);
    }

    if (e.getKey().equals("ngrossnum")) {
      getM_voBill().setItemValue(row,
          getEnvironment().getGrossNumItemKey(),
          getBillCardPanel().getBodyValueAt(row, "ngrossnum"));
    }

    // zhx added for bill row no, after edit process.
    if (e.getKey().equals(IItemKey.CROWNO)) {
      nc.ui.scm.pub.report.BillRowNo.afterEditWhenRowNo(
          getBillCardPanel(), e, getBillType());
      // ͬ����VO
      getM_voBill().setItemValue(row, IItemKey.CROWNO,
          getBillCardPanel().getBodyValueAt(row, IItemKey.CROWNO));

    }
    GeneralBillUICtl.afterBcloseorditemEdit(getBillCardPanel(), e,
        getM_voBill());
    // �����෽��
    afterBillEdit(e);
    // getBillCardPanel().restoreFocusComponent();
    
    //  ���ο�����չ
    getPluginProxy().afterEdit(e);
  }

  /**
   * �����ߣ����˾� ���ܣ�������ı��¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void afterFreeItemEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterFreeItemEdit(e);

  }

  /**
   * �����ߣ����˾� ���ܣ��ֿ�ı��¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void afterWhEdit(nc.ui.pub.bill.BillEditEvent e) {
    // �ֿ�
    getEditCtrl().afterWhEdit(e, null, null);

  }

  protected void setLastHeadRow(int row) {
    setM_iLastSelListHeadRow(row);
  }

  /**
   * �����ߣ����˾� ���ܣ������塢�б��ϱ�༭�¼����� ������e ���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void bodyRowChange(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().bodyRowChange(e);
    
    //���ο�����չ
    getPluginProxy().bodyRowChange(e);

  }

  private FreeItemCellRender m_freeCellRender = null;

  protected FreeItemCellRender getFreeItemCellRender() {
    if (m_freeCellRender == null)
      m_freeCellRender = new FreeItemCellRender(getBillCardPanel());
    return m_freeCellRender;
  }

  private LotItemRefCellRender m_lotCellRender = null;

  protected LotItemRefCellRender getLotRefCellRender() {
    if (m_lotCellRender == null)
      m_lotCellRender = new LotItemRefCellRender(getBillCardPanel());
    return m_lotCellRender;
  }

  /**
   * �����ߣ����˾� ���ܣ����ָ���е����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void clearRowData(int row) {
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    int iRowCount = bmBill.getRowCount();
    BillItem[] items = getBillCardPanel().getBodyItems();
    if (items == null || row >= iRowCount) {
      SCMEnv.out("row too big.");
      return;
    }

    // ɾ����������
    // ɾ����������
    String sColKey = null;
    int iColCount = items.length;

    for (int col = 0; col < iColCount; col++) {

      sColKey = items[col].getKey();
      if (!IItemKey.NAME_HEADID.equals(sColKey)
          && !IItemKey.NAME_BODYID.equals(sColKey)
          && !"crowno".equals(sColKey))
        bmBill.setValueAt(null, row, col);
    }
    // ͬ��vo
    getM_voBill().clearItem(row);

  }

  /**
   * �����ߣ����˾� ���ܣ����ָ���С�ָ���е����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void clearRowData(int row, String[] saColKey) {
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    int iRowCount = bmBill.getRowCount();
    if (row >= iRowCount || saColKey == null || saColKey.length == 0) {
      SCMEnv.out("row too big or needn't clear.");
      return;
    }
    // ɾ����������
    nc.ui.pub.bill.BillItem biaBody[] = bmBill.getBodyItems();
    Hashtable<String, String> htBodyItem = new Hashtable<String, String>();
    // ���зŵ�hash��
    for (int col = 0; col < biaBody.length; col++)
      htBodyItem.put(biaBody[col].getKey(), "OK");

    for (int col = 0; col < saColKey.length; col++)
      if (saColKey[col] != null
          && getBillCardPanel().getBodyItem(saColKey[col]) != null) {
        try {
          // SCMEnv.out("clear "+saColKey[col]);
          try {
            // ����У����֮
            if (htBodyItem.containsKey(saColKey[col]))
              bmBill.setValueAt(null, row, saColKey[col]);
          } catch (Exception e3) {
          }

          // ͬ��vo
          getM_voBill().setItemValue(row, saColKey[col], null);
          // �����������Ļ���ͬʱ��vfree1--->vfree10
          if (saColKey[col].equals("vfree0")) {
            for (int i = 1; i <= 10; i++) {
              // ����У����֮
              if (htBodyItem.contains(saColKey[col]))
                bmBill.setValueAt(null, row, "vfree" + i);
              // ͬ��vo
              getM_voBill().setItemValue(row, "vfree" + i, null);
            }
          }

        } catch (Exception e) {
          // nc.vo.scm.pub.SCMEnv.error(e);
          SCMEnv
              .out("nc.ui.ic.pub.bill.GeneralBillClientUI.clearRowData(int, String [])��set value ERR.--->"
                  + saColKey[col]);/*-=notranslate=-*/
        } finally {

        }
      }

  }

  /**
   * �����ߣ����˾� ���ܣ���ȥ����ʽ�µĿ��У���ѯ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� 2001/10/29,wnj,���Ч��
   * 
   * 
   * 
   */
  public void filterNullLine() {

    // �����ֵ�ݴ�
    Object oTempValue = null;
    // ����model
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    // ����кţ�Ч�ʸ�һЩ��
    int iInvCol = bmBill.getBodyColByKey(IItemKey.INVID);

    // �����д����
    if (bmBill != null && iInvCol >= 0 && iInvCol < bmBill.getColumnCount()) {
      // ����
      int iRowCount = getBillCardPanel().getRowCount();
      // �Ӻ���ǰɾ
      for (int line = iRowCount - 1; line >= 0; line--) {
        // ���δ��
        oTempValue = bmBill.getValueAt(line, IItemKey.INVID);
        if (oTempValue == null
            || oTempValue.toString().trim().length() == 0)
          // ɾ��
          getBillCardPanel().getBillModel().delLine(
              new int[] { line });
      }
    }
  }
  private BillTempletVO billTempletVO = null;
  public BillTempletVO getDefaultTemplet() {
    if (null == billTempletVO) {
      if (null == getFrame())
        billTempletVO = BillUIUtil.getDefaultTempletStatic(
            getBillType(), null, getEnvironment().getUserID(),
            getEnvironment().getCorpID(), null, null);
      else
        billTempletVO = BillUIUtil.getDefaultTempletStatic((getFrame()
            .getModuleCode() != null && !getFrame().getModuleCode()
            .equals(getFunctionNode())) ? getFrame()
            .getModuleCode() : getBillType(), null,
            getEnvironment().getUserID(), getEnvironment()
                .getCorpID(), null, null);
    }
    return billTempletVO;

  }

  /**
   * ���� BillCardPanel1 ����ֵ��
   * 
   * @return nc.ui.pub.bill.BillCardPanel 1.
   *         �½�һ��billcardpanel,�õ�templetData�����ݸ�BillData 2.
   *         ������������κŲ��տؼ�����BillData���滻ԭComponet������Ŀ����Ҳ�� 3. ���˱��幩Ӧ�� 4.
   *         ���ñ���m_sTitle���Զ����� 5.
   *         ��BillData������billCardPanel,���������кţ�����billCardPanel
   */
  /* ���棺�˷������������ɡ� */
  protected nc.ui.pub.bill.BillCardPanel getBillCardPanel() {
    if (ivjBillCardPanel == null) {
      try {
        ivjBillCardPanel = new nc.ui.pub.bill.BillCardPanel();
        ivjBillCardPanel.setName("BillCardPanel");

        // modified by liuzy 2007-12-18 ���ݽڵ�ų�ʼ������ģ��
        BillData bd = null;
        if (null == getFrame())
          bd = new BillData(getDefaultTemplet());
        else
          bd = new BillData(getDefaultTemplet());

        if (bd == null) {
          SCMEnv.out("--> billdata null.");
          return ivjBillCardPanel;
        }
        // ���������
        if (bd.getBodyItem("vfree0") != null)
          getFreeItemRefPane().setMaxLength(
              bd.getBodyItem("vfree0").getLength());

        // zhy 2005-04-13 ��ͷ���β���
        if (bd.getHeadItem("vbatchcode") != null) {
          bd.getHeadItem("vbatchcode").setComponent(
              getLotNumbRefPane());
        }
        // zhy 2005-04-13 ��ͷ������
        if (bd.getHeadItem("vfree0") != null) {
          bd.getHeadItem("vfree0").setComponent(getFreeItemRefPane());
        }

        // ���ڳ��������κŲ���
        // zhy2006-04-18 ���ڼ������κŵ���,�ʲ����ڳ��������⴦��
        // if (!m_bIsInitBill) {
        if (bd.getBodyItem("vbatchcode") != null)
          getLotNumbRefPane().setMaxLength(
              bd.getBodyItem("vbatchcode").getLength());
        if (bd.getBodyItem("vbatchcode") != null)
          bd.getBodyItem("vbatchcode").setComponent(
              getLotNumbRefPane());
        // }
        if (bd.getBodyItem("vvehiclecode") != null)
          bd.getBodyItem("vvehiclecode").setComponent(
              getVehicleRefPane());
        
/*        if (bd.getBodyItem(IItemKey.CORRESCODE) != null) {
          bd.getBodyItem(IItemKey.CORRESCODE).setComponent(getICCorBillRef());
        }*/

        // ����������ռ��뵥��ģ�����
        if (bd.getBodyItem("vfree0") != null)
          bd.getBodyItem("vfree0").setComponent(getFreeItemRefPane()); // ����������ռ��뵥��ģ�����
        // ��Ŀ����
        m_refJob = new nc.ui.pub.beans.UIRefPane();
        m_refJob.setRefNodeName("��Ŀ������");/*-=notranslate=-*/
        m_refJob.getRefModel().setPk_corp(getEnvironment().getCorpID());

        if (bd.getBodyItem("cprojectname") != null)
          bd.getBodyItem("cprojectname").setComponent(m_refJob);

        // ��Ŀ�׶β���
        m_refJobPhase = new nc.ui.pub.beans.UIRefPane();
        m_refJobPhase.setIsCustomDefined(true);
        try {
          m_refJobPhaseModel = new nc.ui.bd.b39.PhaseRefModel();
        } catch (Exception e) {

        }
        m_refJobPhase.setRefModel(m_refJobPhaseModel);
        if (bd.getBodyItem("cprojectphasename") != null)
          bd.getBodyItem("cprojectphasename").setComponent(
              m_refJobPhase);

        // ���ⵥ���幩Ӧ��
        if (bd.getBodyItem("vvendorname") != null)
          RefFilter.filtProvider(bd.getBodyItem("vvendorname"),
              getEnvironment().getCorpID(), null);
        if (bd.getBodyItem("vspacename") != null)
          bd.getBodyItem("vspacename").setComponent(
              getLocatorRefPane()); //
      

        // �޸��Զ�����
        bd = changeBillDataByUserDef(getDefHeadVO(), getDefBodyVO(), bd);
        bd = BatchCodeDefSetTool.changeBillDataByBCUserDef(getCorpPrimaryKey(),bd);
        
        BillItem rowpos = new BillItem();
        rowpos.setKey(GeneralBillItemVO.RowPos);
        rowpos.setName(GeneralBillItemVO.RowPos);
        rowpos.setDataType(BillItem.STRING);
        rowpos.setShow(false);
        rowpos.setShowOrder(10000);
        rowpos.setEdit(false);
        bd.addBillItem(BillItem.BODY, BillData.DEFAULT_BODY_TABLECODE, rowpos);
        ivjBillCardPanel.setBillData(bd);
        

        // �����ȼ���������
        BillItem item = null;
        try {
          UIRefPane uiRefPanel = null;
          item = bd.getBodyItem("cqualitylevelname");
          if (item != null){
            uiRefPanel = new UIRefPane();
            uiRefPanel.setRefNodeName("�Զ������");/*-=notranslate=-*/
            uiRefPanel.setIsCustomDefined(true);
            uiRefPanel.setRefModel((AbstractRefModel) Class
                .forName("nc.ui.qc.pub.CheckstateDef")
                .newInstance());
            uiRefPanel.getRefModel().setPk_corp(
                getEnvironment().getCorpID());
//          addied by liuzy 2008-05-09
            // �����Ǽǲ���û������AbstractRefModel.setPk_corp����
            // ���²��ճ�������˾����������ȼ�
            String newWherePart = " pk_corp = '"
              + getEnvironment().getCorpID() + "' and dr = 0";
            uiRefPanel.getRefModel().setWherePart(newWherePart);
            uiRefPanel.setReturnCode(false);
            item.setComponent(uiRefPanel);
          }
        } catch (java.lang.Throwable e) {
          try{
            if(item!=null){
              bd.removeBillItem(BillItem.BODY, BillData.DEFAULT_BODY_TABLECODE, item.getKey());
              //getBillCardPanel().hideBodyTableCol(item.getKey());
            }
          }catch (java.lang.Throwable ee){}
        }
        try {
          item = bd
              .getBodyItem("cmeaswarename");
          if (item != null) {
            AbstractRefModel refModel = (AbstractRefModel) Class
                .forName("nc.ui.mm.pub.pub1010.JlcRefModel")
                .newInstance();
            UIRefPane ref = new UIRefPane();
            ref.setRefNodeName("�Զ������");/*-=notranslate=-*/
            ref.setIsCustomDefined(true);
            ref.setRefModel(refModel);
            item.setComponent(ref);
          }
        } catch (java.lang.Throwable e) {
            try{
              if(item!=null){
                //getBillCardPanel().hideBodyTableCol(item.getKey());
                bd.removeBillItem(BillItem.BODY, BillData.DEFAULT_BODY_TABLECODE, item.getKey());
              }
            }catch (java.lang.Throwable ee){}
        }
        BillItem itemhead = null;
        try {
          itemhead = bd
              .getHeadItem("pk_measware");
          if (itemhead != null) {
            AbstractRefModel refModel = (AbstractRefModel) Class
                .forName("nc.ui.mm.pub.pub1010.JlcRefModel")
                .newInstance();
            UIRefPane ref = new UIRefPane();
            ref.setRefNodeName("�Զ������");/*-=notranslate=-*/
            ref.setIsCustomDefined(true);
            ref.setRefModel(refModel);
            itemhead.setComponent(ref);
          }
          // �޸��ˣ������� �޸����ڣ�2007-11-27����11:11:40
          // �޸�ԭ�򣺴����ͷ�ļ����������ע�ᵽ����ģ������û��nc.ui.mm.pub.pub1010.JlcRefModel�࣬�ͻ���ز���ģ�壬����治�������������졣
          /*
           * BillItem itemhead =
           * ivjBillCardPanel.getHeadItem("cmeaswarename");
           * if(itemhead!=null){ AbstractRefModel refModel =
           * (AbstractRefModel)
           * Class.forName("nc.ui.mm.pub.pub1010.JlcRefModel").newInstance();
           * UIRefPane ref = new UIRefPane();
           * ref.setRefNodeName("�Զ������");
           * ref.setIsCustomDefined(true); ref.setRefModel(refModel);
           * itemhead.setDataType(5);
           * itemhead.setIDColName("pk_measware");
           * itemhead.setEditFormula("cmeaswarename->getColValue(mm_jldoc,mc,pk_jldocid,pk_measware)");
           * itemhead.setComponent(ref); }
           */

        } catch (java.lang.Throwable e) {
          try{
            if(itemhead!=null)
              bd.removeBillItem(BillItem.HEAD,null ,itemhead.getKey());
          }catch (java.lang.Throwable ee){}
          
        }
        
        ivjBillCardPanel.setBillData(bd);

        if (bd.getHeadItem("cbilltypecode") != null)
          m_sTitle = bd.getHeadItem("cbilltypecode").getName();
        if (ivjBillCardPanel.getTitle() != null
            && ivjBillCardPanel.getTitle().trim().length() > 0)
          m_sTitle = ivjBillCardPanel.getTitle();
        // zhx new add billrowno
        nc.ui.scm.pub.report.BillRowNo.loadRowNoItem(ivjBillCardPanel,
            IItemKey.CROWNO);
        // ��ԭ����ģ��ı���������!
        ivjBillCardPanel.getBodyPanel().setRowNOShow(
            nc.ui.ic.pub.bill.Setup.bShowBillRowNo);
        
        //����ģ�����޸�����
        GeneralBillUICtl.setBillCardPaneFillEnable(ivjBillCardPanel);
        
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return ivjBillCardPanel;
  }

  /**
   * ���� BillListPanel1 ����ֵ��
   * 
   * @return nc.ui.pub.bill.BillListPanel 1.�½�billListPanel������ģ������
   *         2.�õ�ģ������BillListData���޸��Զ������������ 3.���ñ�ͷѡ��ģʽ��������listid��ͷ�ı�ͷ��
   *         4.��ʾ���б����У�����billListPanel
   */
  /* ���棺�˷������������ɡ� */
  protected nc.ui.pub.bill.BillListPanel getBillListPanel() {
    if (ivjBillListPanel == null) {
      try {
        ivjBillListPanel = new nc.ui.pub.bill.BillListPanel();
        ivjBillListPanel.setName("BillListPanel");
        // user code begin {1}
        // ivjBillListPanel.loadTemplet(m_sTempletID);
        // �����б�ģ��
        /*ivjBillListPanel.loadTemplet(getBillType(), null,
            getEnvironment().getUserID(), getEnvironment()
                .getCorpID());
*/
        //BillListData bd = ivjBillListPanel.getBillListData();
        BillListData bd = new BillListData(getDefaultTemplet());

        // �޸��Զ�����
        bd = changeBillListDataByUserDef(getDefHeadVO(),
            getDefBodyVO(), bd);
        
        bd = BatchCodeDefSetTool.changeBillListDataByBCUserDef(getCorpPrimaryKey(),bd);

        ivjBillListPanel.setListData(bd);

        // ����С������
        // setScaleOfListPanel();

        // �˵�listid����
        nc.ui.pub.bill.BillItem biListItem[] = ivjBillListPanel
            .getHeadBillModel().getBodyItems();
        String[] hidkeys = new String[1];
        if (biListItem != null)
          for (int col = biListItem.length - 1; col >= 0; col--)
            if (biListItem[col].getName() != null
                && biListItem[col].getName().startsWith(
                    "listid")) {
              try {
                hidkeys[0] = biListItem[col].getKey();
                GeneralBillUICtl.showItem(ivjBillListPanel.getChildListPanel(), false, hidkeys);
                //hideListTableHeadCol(ivjBillListPanel
                //    .getParentListPanel(), col);
                // SCMEnv.out("hide
                // "+biListItem[col].getName());
              } catch (Exception e) {
              }
            }
        // user code end
        ivjBillListPanel.getChildListPanel().setTotalRowShow(true);
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return ivjBillListPanel;
  }

  /**
   * ���� FreeItemRefPane1 ����ֵ��
   * 
   * @return nc.ui.ic.pub.freeitem.FreeItemRefPane
   */
  /* ���棺�˷������������ɡ� */
  protected FreeItemRefPane getFreeItemRefPane() {
    if (ivjFreeItemRefPane == null) {
      try {
        ivjFreeItemRefPane = new FreeItemRefPane();
        ivjFreeItemRefPane.setName("FreeItemRefPane");
        ivjFreeItemRefPane.setLocation(209, 4);
        // user code begin {1}
        // user code end
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return ivjFreeItemRefPane;
  }

  /**
   * ���� LotNumbRefPane1 ����ֵ��
   * 
   * @return nc.ui.ic.pub.lot.LotNumbRefPane
   */
  /* ���棺�˷������������ɡ� */
  protected nc.ui.ic.pub.lot.LotNumbRefPane getLotNumbRefPane() {
    if (ivjLotNumbRefPane == null) {
      try {
        ivjLotNumbRefPane = new nc.ui.ic.pub.lot.LotNumbRefPane();
        ivjLotNumbRefPane.setName("LotNumbRefPane");
        ivjLotNumbRefPane.setLocation(38, 1);
        ivjLotNumbRefPane.setIsMutiSel(true);
        ivjLotNumbRefPane.setClientUI(this);
        // user code begin {1}
        // user code end
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return ivjLotNumbRefPane;
  }

  /**
   * ���� SerialAllocationDlg1 ����ֵ��
   * 
   * @return nc.ui.ic.pub.sn.SerialAllocationDlg
   */
  /* ���棺�˷������������ɡ� */
  protected nc.ui.ic.pub.sn.SerialAllocationDlg getSerialAllocationDlg() {
    if (m_dlgSerialAllocation == null) {
      try {
        m_dlgSerialAllocation = new nc.ui.ic.pub.sn.SerialAllocationDlg(
            this);
        m_dlgSerialAllocation.setName("SerialAllocationDlg");
        // m_dlgSerialAllocation.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        // user code begin {1}
        // m_dlgSerialAllocation.setParent(this);
        // С����������
        m_dlgSerialAllocation.setScale(m_ScaleValue
            .getScaleValueArray());
        // user code end
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return m_dlgSerialAllocation;
  }

  /**
   * ���� SpaceAllocationDlg1 ����ֵ��
   * 
   * @return nc.ui.ic.pub.locator.SpaceAllocationDlg
   */
  /* ���棺�˷������������ɡ� */
  protected nc.ui.ic.pub.locator.SpaceAllocationDlg getSpaceAllocationDlg() {
    if (m_dlgSpaceAllocation == null) {
      try {
        m_dlgSpaceAllocation = new nc.ui.ic.pub.locator.SpaceAllocationDlg(
            this);
        m_dlgSpaceAllocation.setName("SpaceAllocationDlg");
        // m_dlgSpaceAllocation.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        // user code begin {1}
        // С����������
        m_dlgSpaceAllocation
            .setScale(m_ScaleValue.getScaleValueArray());
        // user code end
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return m_dlgSpaceAllocation;
  }

  /**
   * ����ʵ�ָ÷���������ҵ�����ı��⡣
   * 
   * @version (00-6-6 13:33:25)
   * 
   * @return java.lang.String
   */
  public String getTitle() {
    return m_sTitle;
  }

  /**
   * �����ߣ����˾� ���ܣ����زֿ�����ֻ�����ڳ���ⵥ�����ⵥҪ���ֳ�/��ֿ�itemkey ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected String getWhName() {
    if (getM_voBill() != null
        && getM_voBill().getHeaderValue("cwarehousename") != null)
      return getM_voBill().getHeaderValue("cwarehousename").toString();
    else
      return null;
  }

  /**
   * ÿ�������׳��쳣ʱ������
   * 
   * @param exception
   *            java.lang.Throwable
   */
  protected void handleException(java.lang.Throwable exception) {

    /* ��ȥ���и��е�ע�ͣ��Խ�δ��׽�����쳣��ӡ�� stdout�� */
    SCMEnv.out("--------- δ��׽�����쳣 ---------");/*-=notranslate=-*/
    nc.vo.scm.pub.SCMEnv.error(exception);
  }

  /**
   * 
   * ��ÿͻ��˻�������
   * <p>
   * 
   * @return �ͻ��˻�������
   *         <p>
   * @author duy
   * @time 2008-2-22 ����04:00:35
   */
  public Environment getEnvironment() {
    if (m_environment == null) {
      m_environment = new Environment(this);
    }
    return m_environment;
  }

  /**
   * ��ʼ���ࡣ
   */
  /* ���棺�˷������������ɡ� */
  public void initialize() {
    m_timer.start("��ʼ���࿪ʼ��");/*-=notranslate=-*/
    try {
      m_timer.showExecuteTime("��ʼ����ʼ��");/*-=notranslate=-*/
      // initButtons();
      m_timer.showExecuteTime("initButtons��");/*-=notranslate=-*/
      // ydy 04-05-12 �򻯴��룬����
      initialize(null);
    } catch (java.lang.Throwable ivjExc) {
      handleException(ivjExc);
    }
    // user code begin {2}

  }

  /**
   * ɾ���б��µ�һ�ŵ���
   * 
   * @author ljun
   * @since v5 ����ʱ��һ�� ���ն��ŵ�������ʱ����Ƭ�����±�����Զ�ת���б����ʱ��ɾ������ĵ���
   * 
   */
  protected void delBillOfList(int iSel) {
    if (iSel < 0)
      return;
    if (getM_alListData() == null)
      return;
    // ���ɾ����m_alListData.size()==0 , ��m_iLastSelListHeadRowΪ-1
    getM_alListData().remove(iSel);

    // m_iCurDispBillNum��ɾ����Ϊ��һ���б���
    if (BillMode.Card == getM_iCurPanel())
      m_iCurDispBillNum = -1;
    else
      m_iCurDispBillNum = 0;

    if (getM_alListData().size() == 0)
      setLastHeadRow(-1);
    else
      setLastHeadRow(0);

    m_iBillQty = getM_alListData().size();

    // ˢ�½�����ʾ
    GeneralBillHeaderVO voaBillHeader[] = new GeneralBillHeaderVO[getM_alListData()
        .size()];
    for (int i = 0; i < getM_alListData().size(); i++) {
      if (getM_alListData().get(i) != null)
        voaBillHeader[i] = ((GeneralBillVO) getM_alListData().get(i))
            .getHeaderVO();
      else {
        SCMEnv.out("list data error!-->" + i);
        return;
      }

    }
    if (getM_alListData().size() <= 0) {
      getBillListPanel().setHeaderValueVO(null);
      getBillListPanel().setBodyValueVO(null);
    } else {
      setListHeadData(voaBillHeader);
      // ѡ���б��� ����ˢ�±�����ʾ
      selectListBill(getM_iLastSelListHeadRow());
    }

  }

  /**
   * �����ߣ����˾� ���ܣ��ѱ���ʽ�µĵ��ݲ��뵽�б���������������¼�롢���Ʊ����
   */
  private void insertBillToList(GeneralBillVO voBill) {
    if (voBill == null || voBill.getParentVO() == null
        || voBill.getChildrenVO() == null) {
      SCMEnv.out("Bill is null !");
      return;
    }

    // ��ǰû�е���
    if (getM_alListData() == null)
      setM_alListData(new ArrayList<GeneralBillVO>());
    // ��һ�������һ����׷�ӵ���
    // if (m_iLastSelListHeadRow < 0 || m_iLastSelListHeadRow == m_iBillQty
    // - 1)
    // ..................ע��clone()...........................

    // �������㻻����
    voBill.setHaveCalConvRate(true);

    // ͨ�����ݹ�ʽ������ִ���йع�ʽ�����ķ���
    // execFormulaAtBillVO(voBill);
    execHeadFormulaAtVOs(new GeneralBillHeaderVO[] { voBill.getHeaderVO() });

    GeneralBillVO billvo = (GeneralBillVO) voBill.clone();
    // ------------------
    getM_alListData().add(billvo);
    // else //����
    // m_alListData.add(m_iLastSelListHeadRow + 1, voBill.clone());

    m_iBillQty = getM_alListData().size();

    // ѡ�е��������С�
    setLastHeadRow(m_iBillQty - 1);

    // ����ʽ�µ�ǰ��ʾ�ĵ�����ţ����б�---�����л�ʱ��δ��ѡ�������ݣ���������������ݣ������Ч�ʡ�
    if (BillMode.Card == getM_iCurPanel())
      m_iCurDispBillNum = getM_iLastSelListHeadRow();
    else
      m_iCurDispBillNum = 0;
    // ˢ�½�����ʾ
    getBillListPanel().getHeadBillModel().setSortColumn(null);
    getBillListPanel().getHeadBillModel().addLine();
    getBillListPanel().getHeadBillModel().setBodyRowVO(
        billvo.getParentVO(), getM_iLastSelListHeadRow());

    getBillListPanel().getHeadBillModel().setRowState(
        getM_iLastSelListHeadRow(), VOStatus.UNCHANGED);
    // ѡ�б�ͷ��
    // getBillListPanel().getHeadTable().changeSelection(sn, 0, false,
    // false);
    if (getM_iLastSelListHeadRow() > -1
        && getM_iLastSelListHeadRow() < getBillListPanel()
            .getHeadBillModel().getRowCount())
      getBillListPanel().getHeadTable().setRowSelectionInterval(
          getM_iLastSelListHeadRow(), getM_iLastSelListHeadRow());

  }

  /**
   * �����ߣ�yb ���ܣ�ǩ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public Object[] onBatchProcessInThread(GeneralBillVO[] voaBills,
      String sAction) throws Exception {
    if (voaBills == null || voaBills.length <= 0)
      return null;

    StringBuffer sb = new StringBuffer("");
    String stemp = null;
    int errcount = 0;
    final int isleep = 200;
    Object[] oret = new Object[2];
    ArrayList<String> pksecusslist = new ArrayList<String>();
    UFTime ufdPre1 = new UFTime(System.currentTimeMillis());// ϵͳ��ǰʱ��
    UFDateTime ufdPre = new UFDateTime(getEnvironment().getLogDate() + " "
        + ufdPre1.toString());
    for (int i = 0; i < voaBills.length; i++) {
      stemp = null;
      try {
        if (ICConst.SIGN.equals(sAction)) {
          stemp = checkBillForAudit(voaBills[i]);
          if (stemp != null) {
            sb.append(stemp + "\n");
            LongTimeTask.showHintMsg(stemp);
            Thread.currentThread().sleep(isleep);
            continue;
          }
          voaBills[i] = getAuditVO(voaBills[i], ufdPre);
          // modify by liuzy 2007-04-28 reason:multi-language
          // LongTimeTask.showHintMsg("����"+voaBills[i].getHeaderVO().getVbillcode()+"��ʼǩ��...");
          LongTimeTask.showHintMsg(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID(
                  "4008bill",
                  "UPP4008bill-000513",
                  null,
                  new String[] { voaBills[i].getHeaderVO()
                      .getVbillcode() }));
          onAuditKernel(voaBills[i]);
          stemp = nc.ui.ml.NCLangRes.getInstance().getStrByID(
              "4008bill",
              "UPP4008bill-000514",
              null,
              new String[] { voaBills[i].getHeaderVO()
                  .getVbillcode() });
          LongTimeTask.showHintMsg(stemp);
        } else if (ICConst.CANCELSIGN.equals(sAction)) {
          stemp = checkBillForCancelAudit(voaBills[i]);
          if (stemp != null) {
            sb.append(stemp + "\n");
            LongTimeTask.showHintMsg(stemp);
            Thread.currentThread().sleep(isleep);
            continue;
          }
          voaBills[i] = getCancelAuditVO(voaBills[i]);

          // add by liuzy 2007-11-02 10:16 ѹ������
          ObjectUtils.objectReference(voaBills[i]);

          LongTimeTask.showHintMsg(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID(
                  "4008bill",
                  "UPP4008bill-000515",
                  null,
                  new String[] { voaBills[i].getHeaderVO()
                      .getVbillcode() }));
          nc.ui.pub.pf.PfUtilClient.processAction(ICConst.CANCELSIGN,
              getBillType(), getEnvironment().getLogDate(),
              voaBills[i]);
          stemp = nc.ui.ml.NCLangRes.getInstance().getStrByID(
              "4008bill",
              "UPP4008bill-000516",
              null,
              new String[] { voaBills[i].getHeaderVO()
                  .getVbillcode() });
          LongTimeTask.showHintMsg(stemp);
        }
        if (stemp != null)
          sb.append(stemp + "\n");
        pksecusslist.add(voaBills[i].getHeaderVO().getCgeneralhid());
        Thread.currentThread().sleep(isleep);
      } catch (Exception e) {
        e = nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
        if (ICConst.SIGN.equals(sAction))
          stemp = nc.ui.ml.NCLangRes.getInstance().getStrByID(
              "4008bill",
              "UPP4008bill-000517",
              null,
              new String[] { voaBills[i].getHeaderVO()
                  .getVbillcode() });
        else if (ICConst.CANCELSIGN.equals(sAction))
          stemp = nc.ui.ml.NCLangRes.getInstance().getStrByID(
              "4008bill",
              "UPP4008bill-000518",
              null,
              new String[] { voaBills[i].getHeaderVO()
                  .getVbillcode() });
        
        if (e != null && e.getClass() == nc.vo.ic.pub.exp.OtherOut4MException.class)
          stemp = nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi", "UPP4008busi-000401")/** @res "��ж�����ɵ�������ⵥ���µ�����ʵ�������Ӽ�����������ʵ�ʳ����׼�������ֶ�Ӧ���Ӽ�����"*/ + e.getMessage();
        
        LongTimeTask.showHintMsg(stemp);
        sb.append(stemp + "[" + e.getMessage() + "]");
        sb.append("\n");
        errcount++;
        try {
          Thread.currentThread().sleep(isleep);
        } catch (Exception ee) {
          SCMEnv.out(ee.getMessage());
        }
      }
    }

    if (pksecusslist.size() > 0) {
      try {
        LongTimeTask.showHintMsg(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000519"));
        if (getM_alListData() == null)
          setM_alListData(new ArrayList<GeneralBillVO>());
        GeneralBillUICtl.refreshBillVOsByPks(getM_alListData(),
            pksecusslist.toArray(new String[pksecusslist.size()]));
        setDataOnList(getM_alListData(), true);
      } catch (Exception e) {
        throw e;
      }
    }
    oret[0] = new Integer(errcount);
    // if(errcount>0){
    oret[1] = sb.toString();
    // }
    return oret;
  }

  /**
   * �����ߣ�yb ���ܣ�ǩ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void onBatchAction(String sAction) {

    ArrayList alvo = null;
    try {
      alvo = (ArrayList) LongTimeTask.procclongTime(this,
          nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
              "UPP4008bill-000522"), 1000, 3, this.getClass()
              .getName(), this, "getSelectedBills", null, null);
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this,
          nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
              "UPP4008bill-000523"), e);
      this.updateUI();
      return;
    }
    // getSelectedBills();
    if (alvo == null || alvo.size() <= 0) {
      this.updateUI();
      return;
    }
    GeneralBillVO voaBills[] = new GeneralBillVO[alvo.size()];

    try {

      for (int i = 0; i < alvo.size(); i++) {
        voaBills[i] = (GeneralBillVO) alvo.get(i);
        voaBills[i] = (GeneralBillVO) voaBills[i].clone();
      }

      String shint = null;
      if (ICConst.SIGN.equals(sAction))
        shint = nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000520");
      else if (ICConst.CANCELSIGN.equals(sAction))
        shint = nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000521");

//    ���ο�����չ
      getPluginProxy().beforeAction(ICConst.CANCELSIGN.equals(sAction)? 
          nc.vo.scm.plugin.Action.UNAUDIT:nc.vo.scm.plugin.Action.AUDIT, voaBills);
      
      Object[] oret = (Object[]) LongTimeTask.procclongTime(this, shint,
          10, 3, this.getClass().getName(), this,
          "onBatchProcessInThread", new Class[] {
              GeneralBillVO[].class, String.class },
          new Object[] { voaBills, sAction });
      if (oret != null && oret[1] != null)
        showWarningMessage(oret[1].toString());
      // if(((Integer)oret[0]).intValue()!=voaBills.length){

      // m_bQuery = false;
      // LongTimeTask.procclongTime(this,"����ˢ�½���...",0,
      // 3,this.getClass().getName(),this,"onQuery",
      // null,null);
      // }
      
//    ���ο�����չ
      getPluginProxy().afterAction(ICConst.CANCELSIGN.equals(sAction)? 
          nc.vo.scm.plugin.Action.UNAUDIT:nc.vo.scm.plugin.Action.AUDIT, voaBills);
      
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this, null, e);
    }
    this.updateUI();
  }

  /**
   * �����ߣ�yb ���ܣ�ɾ������ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public String checkBillForAudit(GeneralBillVO vobill) {
    if (vobill == null)
      return null;
    if (vobill.getHeaderVO().getFbillflag() != null
        && vobill.getHeaderVO().getFbillflag().intValue() != BillStatus.IFREE)
      return NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000001", null, new String[]{vobill.getHeaderVO().getVbillcode()})/*����[{0}]��������״̬������ǩ�֣�*/;
    return null;
  }

  /**
   * �����ߣ����˾� ���ܣ�ǩ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� �޸��ˣ������� �޸����ڣ�2007-11-14����03:51:17 �޸�ԭ��ǩ��ʱ���ɺ�̨����
   */
  public GeneralBillVO getAuditVO(GeneralBillVO voAudit,
      UFDateTime sysdatetime) {

    // ��������״̬δû�б��޸�
    GeneralBillVO.setBillBCVOStatus(voAudit, nc.vo.pub.VOStatus.UNCHANGED);
    // ֧��ƽ̨��cloneһ�����Ա����Ժ�Ĵ���ͬʱ��ֹ�޸���m_voBill
    GeneralBillHeaderVO voHead = voAudit.getHeaderVO();

    // ǩ����
    voHead.setCregister(getEnvironment().getUserID());
    // ���Բ��ǵ�ǰ��¼��λ�ĵ��ݣ����Բ���Ҫ�޸ĵ��ݡ�
    // voHead.setPk_corp(getEnvironment().getCorpID());
    voHead.setDaccountdate(new nc.vo.pub.lang.UFDate(getEnvironment()
        .getLogDate()));
    // voHead.setAttributeValue("taccounttime", sysdatetime.toString()); //
    // ǩ��ʱ��//zhy2005-06-15ǩ��ʱ��=��½����+ϵͳʱ��

    // vo����Ҫ����ƽ̨������Ҫ���ɺ�ǩ�ֺ�ĵ���
    // voHead.setFbillflag(new
    // Integer(nc.vo.ic.pub.bill.BillStatus.SIGNED));
    voHead.setCoperatoridnow(getEnvironment().getUserID()); // ��ǰ����Ա2002-04-10.wnj
    voHead.setAttributeValue("clogdatenow", getEnvironment().getLogDate()); // ��ǰ��¼����

    voAudit.setParentVO(voHead);

    // ���ݲֿ������òֿ��Ƿ����������� add by hanwei 2004-4-30
    //getGenBillUICtl().setBillIscalculatedinvcost(voAudit);

    // ƽ̨����Ҫ�������ͷPK
    GeneralBillItemVO voaItem[] = voAudit.getItemVOs();
    int iRowCount = voAudit.getItemCount();
    for (int i = 0; i < iRowCount; i++) {
      // ��ͷPK
      voaItem[i].setCgeneralhid(voHead.getPrimaryKey());
      // set delete flag------- obligatory for ts test.
    }
    voAudit.setChildrenVO(voaItem);

    voAudit.setStatus(nc.vo.pub.VOStatus.UNCHANGED);
    GeneralBillVO.setBillBCVOStatus((GeneralBillVO)voAudit, nc.vo.pub.VOStatus.UNCHANGED);

    voAudit.setIsCheckCredit(true);
    voAudit.setIsCheckPeriod(true);
    voAudit.setIsCheckAtp(true);

    nc.vo.sm.log.OperatelogVO log = getNormalOperateLog();
    voAudit.setAccreditUserID(getEnvironment().getUserID());
    voAudit.setOperatelogVO(log);

    // ���ڡ�������Ϣ
    voAudit.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_OTHER;
    voAudit.m_sActionCode = "SIGN";

    return voAudit;

  }

  /**
   * ����ʵ�ָ÷�������Ӧ��ť�¼���
   * 
   * @version (00-6-1 10:32:59)
   * 
   * @param bo
   *            ButtonObject
   */
  public void onButtonClicked(nc.ui.pub.ButtonObject bo) {
    
    try{
//    ���ο�����չ
      getPluginProxy().beforeButtonClicked(bo);
      
      getButtonManager().onButtonClicked(bo);
      
      getPluginProxy().afterButtonClicked(bo);
      
    }catch(Exception e){
      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
      return;
    }
    

  }

  /**
   * �����ߣ����˾� ���ܣ��������� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public void onAdd(boolean bUpataBotton, GeneralBillVO voBill) {

    // ��ǰ���б���ʽʱ�������л�������ʽ,[v5]����ǲ��ն������ɣ����л����л��ڵ���onSwitchʱִ��
    if (BillMode.List == getM_iCurPanel() && !m_bRefBills)
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_SWITCH));
    // onSwitch();

    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
        "UCH028")/* @res "��������" */);
    // ����
    try {

      if (voBill == null) {
        setM_voBill(new GeneralBillVO());
        getBillCardPanel().updateValue();
        getBillCardPanel().addNew();
        getBillCardPanel().getBillModel().clearBodyData();
      }
      // �����������ݵĳ�ʼ���ݣ������ڣ��Ƶ��˵ȡ�
      setNewBillInitData();
      getBillCardPanel().setEnabled(true);
      setM_iMode(BillMode.New);

      if (bUpataBotton && voBill == null)
        setButtonStatus(true);

      // long lTime = System.currentTimeMillis();

      // ��������λ����
      m_alLocatorDataBackup = m_alLocatorData;

      m_alLocatorData = null;
      // ����������к�����
      m_alSerialDataBackup = m_alSerialData;
      m_alSerialData = null;

      // v5
      if (voBill == null)
        addRowNums(m_iInitRowCount);
      //deleted by lirr  �޸�ԭ�������Ƿ���Ȩ�޵��ж� �����˺���
      /*// ��ʾ�����Ҽ���ť�������á�
      if (getBillCardPanel().getBodyMenuItems() != null)
        for (int i = 0; i < getBillCardPanel().getBodyMenuItems().length; i++){
            getBillCardPanel().getBodyMenuItems()[i].setEnabled(true);
        }
          */

      // 20050519 dw ��;���Ҽ�����ά������Ӧ��� getBillTypeCode() !="40080620"
      if (getBillType() != "40080620") {
        getBillCardPanel().setBodyMenuShow(true);
//         ��ʾ�����Ҽ���ť�������á�
        if (getBillCardPanel().getBodyMenuItems() != null)
          for (int i = 0; i < getBillCardPanel().getBodyMenuItems().length; i++){
            //added by lirr 2009-02-23 �޸�ԭ�������Ƿ���Ȩ�޵��ж� ���� �ͻ�������ϰ�ťû��Ȩ�޵Ļ� ������ť��Ȩ��Ϊtrue
            String sButtonName = getBillCardPanel().getBodyMenuItems()[i].getText().toString();
            if(getButtonManager().getButton(sButtonName).getParent().isPower()
               && getButtonManager().getButton(sButtonName).isPower())
              getBillCardPanel().getBodyMenuItems()[i].setEnabled(true);
            else 
              getBillCardPanel().getBodyMenuItems()[i].setEnabled(false);
          }//end added
      } else {
        getBillCardPanel().setBodyMenuShow(false);
      }

      // ���õ��ݺ��Ƿ�ɱ༭
      if (getBillCardPanel().getHeadItem("vbillcode") != null)
        getBillCardPanel().getHeadItem("vbillcode").setEnabled(
            m_bIsEditBillCode);
      getBillCardPanel().setTailItem("iprintcount", new Integer(0));

      // ��Ҫ��ʼ�����չ���, Ϊ���ȡ�������������. 20021225
      filterRef(getEnvironment().getCorpID());

      // zhx add �������û�����������к�.
      if (voBill == null
          && getBillCardPanel().getBillModel().getRowCount() != 0)
        nc.ui.scm.pub.report.BillRowNo.addNewRowNo(getBillCardPanel(),
            getBillType(), IItemKey.CROWNO);

      // Ĭ������£��˿�״̬�������� add by hanwei 2003-10-19 //v5 ������Ҫ�޸��˿����lj
      nc.ui.ic.pub.bill.GeneralBillUICtl
          .setSendBackBillState(this, false);

      // Ĭ�ϲ��ǵ������� add by hanwei 2003-10-30
      setIsImportData(false);

      // ���õ�ǰ������������ ���� 2004-04-05
      if (m_utfBarCode != null)
        m_utfBarCode.setCurBillItem(null);

      // �޸��ˣ������� �޸����ڣ�2007-8-31����04:31:35
      // �޸�ԭ��4F_ί��ӹ����ϵ���ԴΪA3_���ϼƻ�ʱ���޸������������Ѽӹ���λ״̬���û�ȥ
      if (getBillType().equals(nc.vo.ic.pub.BillTypeConst.m_consignMachiningOut)) {
        /** �ñ�ͷ�ɱ༭�� */
        String[] saNotEditableHeadKey = {
            "cbiztype", // wnj:2002-10-23.seg01
            // "cdptid",
            // //"cbizid",
            "ccustomerid", "cproviderid", "alloctypename",
            "freplenishflag", IItemKey.boutretflag };

        for (int i = 0; i < saNotEditableHeadKey.length; i++) {
          if (getBillCardPanel().getBillData().getHeadItem(
              saNotEditableHeadKey[i]) != null)
            getBillCardPanel().getBillData().getHeadItem(
                saNotEditableHeadKey[i]).setEdit(true);

        }
      }

    } catch (Exception e) {
      handleException(e);
    }
  }

  /**
   * �����ߣ�����еĺ��ķ��� ���ܣ�ȷ�ϣ����棩���� �������� ���⣺ ���ڣ�(2004-4-1 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� 2004-4-1 ����
   */
  protected void onAuditKernel(GeneralBillVO voBill) throws Exception {

    // add by liuzy 2007-11-02 10:16 ѹ������
    // ObjectUtils.objectReference(voBill);
    // voBill = (GeneralBillVO)voBill.clone();
    GeneralBillItemVO[] itemvos = voBill.getItemVOs();
    try {
      voBill.setTransNotFullVo();
      /*nc.ui.pub.pf.PfUtilClient.processBatch("SIGN", getBillType(),
          getEnvironment().getLogDate(),
          new GeneralBillVO[] { voBill });*/
      nc.ui.pub.pf.PfUtilClient.runBatch(this,"SIGN", getBillType(),
          getEnvironment().getLogDate(),
          new GeneralBillVO[] { voBill },null,null,null);
    }
    finally {
      voBill.setChildrenVO(itemvos);
    }
  }

  /**
   * �����ߣ�yb ���ܣ�ɾ������ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * �޸��ˣ������� �޸����ڣ�2007-9-12����10:16:33 �޸�ԭ��
   * 
   */
  public String checkBillForCancelAudit(GeneralBillVO vobill) {
    if (vobill == null)
      return null;
    if (vobill.getHeaderVO().getFbillflag() != null
        && vobill.getHeaderVO().getFbillflag().intValue() != BillStatus.ISIGNED) {
      if (vobill.getHeaderVO().getFbillflag() != null
          && vobill.getHeaderVO().getFbillflag().intValue() == BillStatus.IAUDITED)
        return nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000539")/*
                         * @res "����["
                         */
            + vobill.getHeaderVO().getVbillcode()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000541")/*
                                   * @res
                                   * "]�Ǵ��ϵͳ���״̬������ȡ��ǩ�֣�"
                                   */;
      else
        return nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000539")/*
                         * @res "����["
                         */
            + vobill.getHeaderVO().getVbillcode()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000540")/*
                                   * @res
                                   * "]����ǩ��״̬������ȡ��ǩ�֣�"
                                   */;
    }
      
      // �̶��ʲ�����ؼ��
      if (vobill.getHeaderVO().getBassetcard().booleanValue()) {
          return vobill.getHeaderVO().getVbillcode()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
                "UPP4008ASSE-000005"); /* @res �Ѿ������ʲ���Ƭ������ȡ��ǩ�� */
      }

    // �����������ǵ������ⵥʱ�������Ѿ��·���U8���ۣ�������ȡ��ǩ��
    if (vobill.getHeaderVO().getCbilltypecode().equals(BillTypeConst.m_allocationOut)) {
      for (GeneralBillItemVO item : vobill.getItemVOs()) {
        if (item.getBtou8rm().booleanValue()) {
          return nc.ui.ml.NCLangRes.getInstance()
              .getStrByID(
                  "4008bill",
                  "UPP4008U8RM-000003",
                  null,
                  new String[] { vobill.getHeaderVO()
                      .getVbillcode() });
          /*
           * @res "���ݺ���{0}�ĵ����Ѿ��·���U8����ϵͳ������ִ�иò�����"
           */
        }
      }
    }

    return null;
  }

  /**
   * �����ߣ����˾� ���ܣ�ǩ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public GeneralBillVO getCancelAuditVO(GeneralBillVO voAudit) {

    GeneralBillHeaderVO voHead = voAudit.getHeaderVO();
    // ǩ����
    // voHead.setCregister(getEnvironment().getUserID());
    // ���Բ��ǵ�ǰ��¼��λ�ĵ��ݣ����Բ���Ҫ�޸ĵ��ݡ�
    // voHead.setPk_corp(getEnvironment().getCorpID());
    // ����������֮�����ɿ���½�ȡ��ǩ��δ�ۼ�����
    // voHead.setDaccountdate(new
    // nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
    // vo����Ҫ����ƽ̨������Ҫ����ǩ�ֺ�ĵ���
    voHead.setFbillflag(new Integer(nc.vo.ic.pub.bill.BillStatus.SIGNED));
    voHead.setCoperatoridnow(getEnvironment().getUserID()); // ��ǰ����Ա2002-04-10.wnj
    voHead.setAttributeValue("clogdatenow", getEnvironment().getLogDate()); // ��ǰ��¼����2003-01-05
    voAudit.setParentVO(voHead);

    // ƽ̨����Ҫ�������ͷPK
    GeneralBillItemVO voaItem[] = voAudit.getItemVOs();
    // ��������
    int iRowCount = voAudit.getItemCount();

    for (int i = 0; i < iRowCount; i++) {
      // ��ͷPK
      voaItem[i].setCgeneralhid(voHead.getPrimaryKey());

    }
    voAudit.setChildrenVO(voaItem);

    voAudit.setStatus(nc.vo.pub.VOStatus.UNCHANGED);

    voAudit.setIsCheckCredit(true);
    voAudit.setIsCheckPeriod(true);
    voAudit.setIsCheckAtp(true);

    voAudit.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_OTHER;
    voAudit.m_sActionCode = "CANCELSIGN";

    return voAudit;

  }

  /**
   * �������ݵ��б�,��ִ��ǰ20�����ݵĹ�ʽ,����Ĭ��ѡ�����õ���һ��������,���ò˵�״̬
   * 
   * @param voa
   */
  public void setDataOnList(AggregatedValueObject[] voa) {
    ArrayList al = new ArrayList();
    if (voa == null)
      return;

    for (int i = 0; i < voa.length; i++) {
      if (voa[i] == null)
        continue;
      al.add(voa[i]);

    }

    setDataOnList(al, false);
  }

  /**
   * �������ݵ��б�,��ִ��ǰ20�����ݵĹ�ʽ,����Ĭ��ѡ�����õ���һ��������,���ò˵�״̬
   * 
   * @since v5
   * @author ljun
   * @param alData
   *            ���ݼ���
   * @param bQuery
   *            �Ƿ��ѯ����, �ǲ�ѯ����Ϊtrue, ����Ϊfalse
   */
  public void setDataOnList(ArrayList<GeneralBillVO> alData, boolean bQuery) {

    m_timer.start();
    setAlistDataByFormula(-1, alData);// GeneralBillVO.QRY_FIRST_ITEM_NUM,
    // alData);
    m_timer.showExecuteTime("@@setAlistDataByFormula��ʽ����ʱ�䣺");/*-=notranslate=-*/

    // ִ����չ��ʽ.Ŀǰֻ�����۳��ⵥUI����.
    execExtendFormula(alData);

    if (alData != null && alData.size() > 0) {
      if (bQuery)
       setScaleOfListData(alData);
      
      setM_alListData(alData);
      setListHeadData();
      // ���õ�ǰ�ĵ�������/��ţ����ڰ�ť����
      setLastHeadRow(0);
      // ��ǰ�Ǳ���ʽʱ�������л����б���ʽ
      if (BillMode.Card == getM_iCurPanel())
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_SWITCH));
      // onSwitch();

      // ȱʡ��ͷָ���һ�ŵ���
      selectListBill(0);
      //deleted  by lirr 2009-06-15
      //ȱʡ��ͷָ���һ�ŵ��� added by lirr 2009-05-21
      /*getBillListPanel().getHeadTable().changeSelection(0, 0, false, false);
      getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);*/

      // ��ʼ����ǰ������ţ��л�ʱ�õ������������������ñ������ݡ�
      m_iCurDispBillNum = -1;
      // ��ǰ������
      m_iBillQty = getM_alListData().size();

      if (bQuery) {
        String[] args = new String[1];
        args[0] = String.valueOf(m_iBillQty);
        String message = nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000339", null, args);

        /* @res "���鵽{0}�ŵ��ݣ�" */

        if (m_iBillQty > 0)
          showHintMessage(message);
        else
          showHintMessage(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000013")/*
                                       * @res
                                       * "δ�鵽���������ĵ��ݡ�"
                                       */);

      }
      // ��������Դ���ݵİ�ť���˵��ȵ�״̬��
      ctrlSourceBillUI(false);

    } else {
      dealNoData();
    }

    setButtonStatus(true);
  }
  
  /**
 * ����������������Ҫ�����������Ĺ��ܡ� removeBillsOfList(iaDelLines,false)���Ӳ��� �Ƿ�Ҫ����б����� ת��ʱ�ж��ŵ��ݲ���
 * <p>
 * <b>examples:</b>
 * <p>
 * ʹ��ʾ��
 * <p>
 * <b>����˵��</b>
 * @param iaDelLines
 * <p>
 * @author lirr
 * @time 2009-11-30 ����11:01:50
 */
protected void removeBillsOfList(int[] iaDelLines) {
      removeBillsOfList(iaDelLines,false);
  }

  /**
   * �����ߣ����˾� ���ܣ�ɾ�������б���洦�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void removeBillsOfList(int[] iaDelLines,boolean isRefAdd) {

    if (iaDelLines != null && iaDelLines.length > 0) {
      // ɾ�������ϵ�����
      getBillListPanel().getHeadBillModel().delLine(iaDelLines);
      for (int i = iaDelLines.length - 1; i >= 0; i--)
        // ��m_alListData��ɾ��
        if (getM_alListData() != null
            && getM_alListData().size() > iaDelLines[i])
          getM_alListData().remove(iaDelLines[i]);

      // ��������
      if (getM_alListData() != null)
        m_iBillQty = getM_alListData().size();
      else {
        setM_alListData(new ArrayList<GeneralBillVO>());
        m_iBillQty = 0;
      }
      // ���ɾ�������һ�ţ�����ͬʱɾ�����ţ���ָ���һ�ţ�
      // ���ֻɾ��������һ�ţ���ָ����һ��,��Ӧ����m_iLastSelListHeadRow
      if (m_iBillQty > 0) {
        if (getM_iLastSelListHeadRow() >= m_iBillQty
            || iaDelLines.length > 1)
          setLastHeadRow(0);

        // ѡ���б���
        selectListBill(getM_iLastSelListHeadRow());
      } else { // ȫɾ���ˣ������
        setM_alListData(new ArrayList<GeneralBillVO>());
        setLastHeadRow(-1);
        m_iCurDispBillNum = -1;
        m_iBillQty = 0;
        // ����б�
        if (!isRefAdd){
          getBillListPanel().getHeadBillModel().clearBodyData();
          getBillListPanel().getBodyBillModel().clearBodyData();
          // ��ձ�
          getBillCardPanel().getBillData().clearViewData();
        }
      }

    }
  }

  /**
   * �����ߣ����˾� ���ܣ����ñ��帨��--��λ/���к� ������ iBillNum��������� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void resetBodyAssistData(int iBillNum) {
    // ������еĻ�λ��������
    // δ��ѡ�������ݣ��������������ݣ������Ч�ʡ�
    if (iBillNum >= 0 && getM_alListData() != null
        && getM_alListData().size() > iBillNum
        && getM_alListData().get(iBillNum) != null) { // ���õ�ǰ�Ļ�λ����
      m_alLocatorData = new ArrayList();
      // ���õ�ǰ�����к�����
      m_alSerialData = new ArrayList();
      // ����
      GeneralBillVO hvo = (GeneralBillVO) getM_alListData().get(iBillNum);
      if (hvo != null) { // ������
        GeneralBillItemVO ivo[] = (GeneralBillItemVO[]) hvo
            .getChildrenVO();
        // ��λ����
        LocatorVO[] lvo = null;
        // ���к�����
        SerialVO[] svo = null;
        if (ivo != null)
          for (int line = 0; line < ivo.length; line++) {
            lvo = ivo[line].getLocatorClone();
            m_alLocatorData.add(lvo);
            svo = ivo[line].getSerial();
            m_alSerialData.add(svo);
          }
      }

    }

  }

  /**
   * �����ߣ����˾� ���ܣ�ѡ���б���ʽ�µĵ�sn�ŵ��� ������sn �������
   * 
   * ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void selectListBill(int sn) {
    nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
    timer.start("@@����selectListBill��ʼ");/*-=notranslate=-*/
    if (sn < 0 || getM_alListData() == null
        || sn >= getM_alListData().size()
        || getM_alListData().get(sn) == null
        || getBillListPanel().getHeadTable().getRowCount() <= 0) {
      SCMEnv.out("sn error,or list null");
      return;
    }
    //deleted by lirr 2009-04-16 ����ѡ��2���ٴӵ�һ�ж�ѡ��ֻ��ѡ�е�һ������
    // ѡ�б�ͷ��
    //�򿪴˴� 2009-06-15
    // modified by lirr 2009-10-14����02:03:12 ����ѡ��2���ٴӵ�һ�ж�ѡ��ֻ��ѡ�е�һ������
    if(getBillListPanel().getHeadTable().getSelectedRowCount()<=1){
      getBillListPanel().getHeadTable().changeSelection(sn, getBillListPanel().getHeadTable().getSelectedColumn(), false, false);
      getBillListPanel().getHeadTable().setRowSelectionInterval(sn, sn);
    }
    // ��Ӧ�ı�������
    GeneralBillVO voBill = (GeneralBillVO) getM_alListData().get(sn);

    GeneralBillItemVO voi[] = voBill.getItemVOs();
    // if null,query the items.
    if (voi == null || voi.length == 0) {
      qryItems(new int[] { sn }, new String[] { voBill.getPrimaryKey() });
    }
    // re-get
    voBill = (GeneralBillVO) getM_alListData().get(sn);
    getBillListPanel().getHeadBillModel().setBodyRowVO(
        voBill.getParentVO(), sn);
    // zhy �����㻻���ʺ���������
    // // ��Ҫ�Ļ�������ǹ̶�������
    // voBill.calConvRate();
    // // ��Ҫ�Ļ���������������
    // voBill.calPrdDate();

    voi = voBill.getItemVOs();
    // ����Ƿ��б��壬���û����ʾ���ݿ��ܱ�ɾ����,���������ء�
    if (voi == null || voi.length <= 0) {
      showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000024")/*
                             * @res
                             * "δ�ҵ�������Ϣ�����ܵ����ѱ�ɾ����"
                             */);
    }
    // ִ�����ε�����ʽ
    // �޸��ˣ������� �޸����ڣ�2007-10-30����03:59:09
    // �޸�ԭ���ڵ���������״̬ʱ����Ҫȥˢ��������Ϣ����Ϊ���ⵥ���ܻ�����������֮ǰˢ�µ��ݽ���
    if (nc.vo.pub.VOStatus.NEW != voBill.getStatus())
      BatchCodeDefSetTool.execFormulaForBatchCode(voi);
    // ------------
    setListBodyData(voi);
    // abstract method.
    // ��ʾ��λ
    dispSpace(sn);
    selectBillOnListPanel(sn);
    // ִ�й�ʽ
    // getBillListPanel().getBodyBillModel().execLoadFormula();
    //
    // ���û�λ��ť
    setBtnStatusSpace(false);

    setLastHeadRow(sn);

    // ���󷽷�����
    setButtonsStatus(getM_iMode());
    setExtendBtnsStat(getM_iMode());
    // ����ǩ�ְ�ť�Ƿ���á�
    setBtnStatusSign(false);

    // �����Դ���ݲ�Ϊ�գ�����ҵ�����͵Ȳ�����
    ctrlSourceBillButtons(true);

    updateButtons();

    // ���������ĵ�ǰ��������VO add by hanwei ���ڳ�ʼ�������ΨһУ������
    if (m_utfBarCode != null)
      m_utfBarCode.setCurBillItem(voi);

    timer.showExecuteTime("@@����selectListBillʱ��");/*-=notranslate=-*/

  }

  /**
   * 
   * ���������������ݹ��޸�ĳ����ť�������¼���ť��״̬��
   * <p>
   * <b>����˵��</b>
   * 
   * @param btn
   *            Ҫ�޸ĵİ�ť
   * @param enabled
   *            �޸ĺ��״̬
   *            <p>
   * @author duy
   * @time 2007-3-27 ����03:49:02
   */
  private void setButtonStatusCascade(ButtonObject btn, boolean enabled) {
    if (btn == null)
      return;

    ButtonObject[] children = btn.getChildButtonGroup();
    for (int i = 0; i < children.length; i++) {
      setButtonStatusCascade(children[i], enabled);
      children[i].setEnabled(enabled);
    }
  }

  /**
   * ֧�ֲ������ɶ��ŵ��ݵİ�Ŧ���� ���۳�,����,�ɹ�����ʹ��,ֻ���޸ĺ�ȡ������
   * 
   * @since v5
   * @author ljun
   * 
   */
  public void setRefBtnStatus() {
    // �������ɶ��ţ��ҵ�ǰ���б����ƺܶఴť�����ã�ֻ��ȡ�����޸Ŀ��ã�ͬʱ˫����ͷ���൱���л�����
    if (m_bRefBills == true && getM_iCurPanel() == BillMode.List) {
      // ���Ʋ��ն��ŵ���,�ҵ�ǰ���б�״̬ʱ�İ�ť״̬
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ADD).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL).setEnabled(
          true);
      setButtonStatusCascade(getButtonManager().getButton(
          ICButtonConst.BTN_BILL), false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_CANCEL)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_QUERY).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_QUERY)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_EXPORT_IMPORT)
          .setEnabled(false);
      // added by lirr 2009-11-18����01:51:21
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_MANUAL_RETURN)
           .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_PO_RETURN)
           .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_WW_RETURN)
      .setEnabled(false);

    }
    // ��Ƭ�����ǲ������ɣ��л���ť����
    if (m_bRefBills == true && getM_iCurPanel() == BillMode.Card) {
      // ���ఴť��������ʱ�İ�ť����
      setM_iMode(BillMode.New);

      // setButtonStatus(true);
      //
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          true);
      // updateButton(getButtonManager().getButton(ICButtonConst.BTN_SWITCH));

      // ������setButtonStatus(true)�����Ĳ��ִ���
      getButtonManager().getButton(ICButtonConst.BTN_ADD).setEnabled(
          false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_SAVE).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_SEL_LOC).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_CANCEL)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_QUERY).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE_LOCATE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          false);
//          �����б༭��ť ������ 2009-09-30
//      getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
//          .setEnabled(true);
//      getButtonManager().getButton(ICButtonConst.BTN_LINE_DELETE)
//          .setEnabled(true);
//      getButtonManager().getButton(ICButtonConst.BTN_LINE_COPY)
//          .setEnabled(true);
//      getButtonManager().getButton(ICButtonConst.BTN_LINE_PASTE)
//          .setEnabled(true);
//      getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
//          .setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO)
          .setEnabled(true);

      // getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE).setEnabled(true);
      // getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL).setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);

      getButtonManager().getButton(ICButtonConst.BTN_LINE).setEnabled(
          true);

      getButtonManager().getButton(ICButtonConst.BTN_PRINT).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_PRINT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_PREVIEW)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_SPACE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_SUM)
          .setEnabled(false);

      getButtonManager().getButton(ICButtonConst.BTN_BROWSE).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_QUERY)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_EXPORT_IMPORT)
          .setEnabled(true);

      // ��������֧��
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_REFER_IN)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(false);
      // ���Ʒ�ҳ��ť��״̬��
      m_pageBtn.setPageBtnVisible(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);

      // ����������º��޸�����������Ա༭
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(true);

    }

  }

  /**
   * �����ߣ����˾� ���ܣ����ñ�����ʾ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBodyInvValue(int row, InvVO voInv) {
    if (voInv == null)
      return;
    try {
      getBillCardPanel().setBodyValueAt((Object) voInv, row, "invvo");

      // if (getBillCardPanel().getBodyItem(IItemKey.INVID) != null)
      getBillCardPanel().setBodyValueAt(voInv.getCinventoryid(), row,
          IItemKey.INVID);

      getBillCardPanel().setBodyValueAt(voInv.getCinventorycode(), row,
          IItemKey.INVCODE);

      // if (getBillCardPanel().getBodyItem("invname") != null)
      getBillCardPanel().setBodyValueAt(voInv.getInvname(), row,
          "invname");

      // if (getBillCardPanel().getBodyItem("invspec") != null)
      getBillCardPanel().setBodyValueAt(voInv.getInvspec(), row,
          "invspec");
      // if (getBillCardPanel().getBodyItem("invtype") != null)
      getBillCardPanel().setBodyValueAt(voInv.getInvtype(), row,
          "invtype");
      // if (getBillCardPanel().getBodyItem("measdocname") != null)
      getBillCardPanel().setBodyValueAt(voInv.getMeasdocname(), row,
          "measdocname");
      // if (getBillCardPanel().getBodyItem("castunitid") != null)
      getBillCardPanel().setBodyValueAt(voInv.getCastunitid(), row,
          "castunitid");

      if (getBillCardPanel().getBodyItem("npacknum") != null) {
        getBillCardPanel().setBodyValueAt(voInv.getNPacknum(), row,
            "npacknum");
        // m_voBill.setItemValue(row,"npacknum",voInv.getNPacknum());
      }
      if (getBillCardPanel().getBodyItem("vpacktypename") != null) {
        getBillCardPanel().setBodyValueAt(voInv.getVpacktypename(),
            row, "vpacktypename");
        // m_voBill.setItemValue(row,"vpacktypename",voInv.getVpacktypename());
      }
      if (getBillCardPanel().getBodyItem("pk_packsort") != null) {
        getBillCardPanel().setBodyValueAt(voInv.getPk_packsort(), row,
            "pk_packsort");
        // m_voBill.setItemValue(row,"pk_packsort",voInv.getPk_packsort());
      }

      // ȱʡ����������
      // if (getBillCardPanel().getBodyItem("castunitname") != null)
      getBillCardPanel().setBodyValueAt(voInv.getCastunitname(), row,
          "castunitname");
      getBillCardPanel().setBodyValueAt(voInv.getCinvmanid(), row,
          "cinvbasid");

      if (getBillCardPanel().getBodyItem("unitweight") != null)
        getBillCardPanel().setBodyValueAt(voInv.getNunitweight(), row,
            "unitweight");

      if (getBillCardPanel().getBodyItem("unitvolume") != null)
        getBillCardPanel().setBodyValueAt(voInv.getNunitvolume(), row,
            "unitvolume");

      if (voInv.getHsl() != null) {
        getBillCardPanel().setBodyValueAt(voInv.getHsl(), row, "hsl");
        // ������
        getM_voBill().setItemValue(row, "hsl", voInv.getHsl());
      }
      // �Ƿ�̶�����
      getM_voBill().setItemValue(row, "isSolidConvRate",
          voInv.getIsSolidConvRate());
      // �ƻ���
      if (voInv.getNplannedprice() != null)
        getBillCardPanel().setBodyValueAt(voInv.getNplannedprice(),
            row, IItemKey.NPLANNEDPRICE);
      // �ƻ����
      Object oTempNum = getBillCardPanel().getBodyValueAt(row,
          getEnvironment().getNumItemKey());
      UFDouble dNum = null;
      UFDouble dMny = null;

      // ͬʱ�������͵���ʱ���ż���
      if (oTempNum != null && voInv.getNplannedprice() != null) {
        dNum = (UFDouble) oTempNum;
        dMny = dNum.multiply((UFDouble) voInv.getNplannedprice());
      } else
        dMny = null;

      if (dMny != null)
        getBillCardPanel().setBodyValueAt(dMny, row,
            IItemKey.NPLANNEDMNY);

      // �������������ʾ��
      String sVfree0 = voInv.getFreeItemVO().getVfree0();
      if (sVfree0 != null && sVfree0.trim().length() > 0)
        getBillCardPanel().setBodyValueAt(sVfree0, row, "vfree0");
      else
        getBillCardPanel().setBodyValueAt("", row, "vfree0");

      if (voInv.getVbatchcode() != null)
        getBillCardPanel().setBodyValueAt(voInv.getVbatchcode(), row,
            "vbatchcode");

      execEditFomulas(row, IItemKey.INVCODE);
      execEditFomulas(row, IItemKey.INVID);

      // ���ü�����
      BillItem bi = getBillCardPanel().getBodyItem("cmeaswarename");
      if(bi!=null){
        nc.ui.pub.beans.UIRefPane refMeasware = ((nc.ui.pub.beans.UIRefPane) bi.getComponent());
        if (refMeasware != null) {
          if (getInOutFlag() == InOutFlag.IN)
            refMeasware.setPK(voInv.getCrkjlc());
          else
            refMeasware.setPK(voInv.getCckjlc());
          /** ǿ��ִ�б����У������еĹ�ʽ */
          if (refMeasware.getRefModel() != null
              && refMeasware.getRefModel().getClass().getName()
                  .equals("nc.ui.mm.pub.pub1010.JlcRefModel"))
            execEditFomulas(row, "cmeaswarename");
        }
      }
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }

  }
  
  String m_Used=nc.ui.ml.NCLangRes.getInstance().getStrByID("4008other","UPP4008other-000431")/*@res "����"*/;

  /**
   * �����ˣ������� ����ʱ�䣺2008-7-24 ����03:55:23 ����ԭ�� ����IC026"����λ���յ�����˳��"����ѡ��"�ϴ�����λ"��
   * �������ѡ���ѡ���������ⵥʱ�Զ������ֿ�+��������ϴ�����λ�Զ�����"��λ"��Ŀ�������ֹ��޸ġ��ϴ�����λȡ��������������µ���ⵥ������λ��
   * @param row
   * @param voInv
   */
  protected void setBodyInSpace(int row, InvVO voInv) {
    try{
      if (getBillCardPanel().getBodyItem("vspacename") != null
          && (voInv.getInOutFlag() == InOutFlag.IN||voInv.getInOutFlag() == InOutFlag.SPECIAL)
          && null != m_sIC040 && "N".equals(m_sIC040)
          && null != m_sIC026 && "�ϴ�����λ"/*-=notranslate=-*/.equals(m_sIC026)) {
        filterSpace(row);
        nc.ui.pub.beans.UIRefPane refSpace = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem("vspacename").getComponent());
        //������ 2010-09-02 ���߻��� ֱ�Ӳ�ѯ��̨
        refSpace.getRefModel().setCacheEnabled(false);
        java.util.Vector refdata = refSpace.getRefModel().getData();
        refSpace.getRefModel().setCacheEnabled(true);//�ָ����� ������ 2010-09-02        
        if (refdata != null && refdata.size() > 0){
          java.util.Vector newrefdata = new java.util.Vector();
          Vector vv = (Vector)refdata.get(0);
          if (null != vv && 4 < vv.size() && null != vv.get(3)
              && null != m_Used 
              && m_Used.equals((String)vv.get(3))){
            newrefdata.add(refdata.get(0));
          }
          if (0 < newrefdata.size()){
            refSpace.setSelectedData(newrefdata);
            refSpace.getRefModel().setSelectedData(newrefdata);
            String cspaceid = refSpace.getRefPK();
            String csname = refSpace.getRefName();
            getEditCtrl().setRowSpaceData(row, cspaceid, csname);
          }
        }
        
      }
    
    
  
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }
  /**
   * �����ߣ����˾� ���ܣ������޸ĺ����������е�PK,��ͬʱˢ�´����VO. Ҫ��֤VO��Item��˳��ͽ�������һ�¡� ������ ���أ� ���⣺
   * ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBodyPkAfterUpdate(ArrayList alBodyPK) {
    nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();
    if (bmTemp == null) {
      SCMEnv.out("bm null ERROR!");
      return;
    }
    if (alBodyPK == null || alBodyPK.size() == 0) {
      SCMEnv.out("no row add.");
      return;
    }
    int rowCount = getBillCardPanel().getRowCount();
    // int length = 0;
    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    int pk_count = 0;

    getBillCardPanel().getBillModel().setNeedCalculate(false);

    for (int row = 0; row < rowCount; row++) {
      iRowStatus = bmTemp.getRowState(row);
      if (nc.ui.pub.bill.BillModel.ADD == iRowStatus) {
        if ((pk_count + 1) >= alBodyPK.size()) {
          SCMEnv.out("return PK size err.");
          return;
        }
        // ��0���Ǳ�ͷPK,
        getBillCardPanel().setBodyValueAt(alBodyPK.get(pk_count + 1),
            row, IItemKey.NAME_BODYID);
        pk_count++;
      }
    }
    getBillCardPanel().getBillModel().setNeedCalculate(true);
    getBillCardPanel().getBillModel().reCalcurateAll();

  }

  /**
   * �����ߣ����˾� ���ܣ����ð�ť״̬�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  protected void setButtonStatus() {
    setButtonStatus(true);
    
    //modified by lirr 2009-02-13 �޸�ԭ�����setButtonStatus(true)��
    /*//  ���ο�����չ
    getPluginProxy().setButtonStatus();*/
  }

  /**
   * �����ߣ����˾� ���ܣ������������ݵĳ�ʼ���ݣ������ڣ��Ƶ��˵ȡ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * �޸��ˣ������� �޸����ڣ�2007-11-14����01:53:20 �޸�ԭ��ʱ��������Ƶ���̨��
   * 
   * 
   */
  protected void setNewBillInitData() {
    try {
      // ----------------------------- tail values
      // -----------------------------
      if (getBillCardPanel().getTailItem("coperatorid") != null)
        getBillCardPanel().setTailItem("coperatorid",
            getEnvironment().getUserID());
      if (getBillCardPanel().getTailItem("coperatorname") != null)
        getBillCardPanel().setTailItem("coperatorname",
            getEnvironment().getUserName());

      if (getBillCardPanel().getTailItem("clastmodiid") != null)
        getBillCardPanel().setTailItem("clastmodiid",
            getEnvironment().getUserID());
      if (getBillCardPanel().getTailItem("clastmodiname") != null)
        getBillCardPanel().setTailItem("clastmodiname",
            getEnvironment().getUserName());

      if (getM_voBill() != null) {
        getM_voBill().setHeaderValue("coperatorid",
            getEnvironment().getUserID());
        getM_voBill().setHeaderValue("coperatorname",
            getEnvironment().getUserName());
        getM_voBill().setHeaderValue("clastmodiid",
            getEnvironment().getUserID());
        getM_voBill().setHeaderValue("clastmodiname",
            getEnvironment().getUserName());

      }
      // ------------------ head values --------------------------------
      if (getBillCardPanel().getHeadItem("dbilldate") != null)
        getBillCardPanel().setHeadItem("dbilldate",
            getEnvironment().getLogDate());
      if (getBillCardPanel().getHeadItem("pk_corp") != null)
        getBillCardPanel().setHeadItem("pk_corp",
            getEnvironment().getCorpID());
      if (!m_bIsEditBillCode
          && getBillCardPanel().getHeadItem("vbillcode") != null)
        getBillCardPanel().setHeadItem("vbillcode",
            getEnvironment().getCorpID());
      if (getM_voBill() != null) {
        getM_voBill().setHeaderValue("dbilldate",
            getEnvironment().getLogDate());
        getM_voBill().setHeaderValue("pk_corp",
            getEnvironment().getCorpID());
        getM_voBill().setHeaderValue("vbillcode", null);
      }

      GeneralBillUICtl.setWHSManagerAndDept(getBillCardPanel(),
          getM_voBill());

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);

    }

  }

  /**
   * �����ߣ�cqw ���ܣ������޸ĵ��ݵĳ�ʼ���� ������ ���أ� ���⣺ ���ڣ�(2005-04-04 19:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * �޸��ˣ������� �޸����ڣ�2007-11-14����01:53:20 �޸�ԭ��ʱ��������Ƶ���̨��
   * 
   * 
   */
  protected void setUpdateBillInitData() {
    try {
      // ----------------------------- tail values
      // -----------------------------

      if (getBillCardPanel().getTailItem("clastmodiid") != null)
        getBillCardPanel().setTailItem("clastmodiid",
            getEnvironment().getUserID());
      if (getBillCardPanel().getTailItem("clastmodiname") != null)
        getBillCardPanel().setTailItem("clastmodiname",
            getEnvironment().getUserName());

      if (getM_voBill() != null) {
        getM_voBill().setHeaderValue("clastmodiid",
            getEnvironment().getUserID());
        getM_voBill().setHeaderValue("clastmodiname",
            getEnvironment().getUserName());
        // getM_voBill().setHeaderValue("tlastmoditime",
        // ufdPre.toString());
      }

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);

    }

  }

  /**
   * �����ߣ�yudaying ���ܣ����ñ�β��ʾ����,��m_voBill��ȡ�������״̬��Ҫ�ض��ִ��� ������ ���أ� ���⣺
   * ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setTailValue(int row) {
    if (getM_voBill() == null || row < 0
        || row >= getM_voBill().getItemCount()) {
      SCMEnv.out("no vobill.no taildata");
      return;
    }
    // �޸� by hanwei 2003-11-13
    if (getM_voBill().getItemInv(row) == null
        || getM_voBill().getItemInv(row).getCinventoryid() == null) {
      setTailValue(null);
      return;
    }

    InvVO voInv = getM_voBill().getItemInv(row);
    // ��ͷ�Ŀ����֯�Ͳֿ�
    BillItem biCalbody = getBillCardPanel().getHeadItem(IItemKey.CALBODY);
    BillItem biWarehouse = getBillCardPanel().getHeadItem(
        IItemKey.WAREHOUSE);

    String pk_calbody = null;
    String sWhID = null;
    if (biCalbody != null)
      pk_calbody = (String) biCalbody.getValueObject();
    if (biWarehouse != null)
      sWhID = (String) biWarehouse.getValueObject();
    String invid = voInv.getCinventoryid();

    // if (getEnvironment().getCorpID() == null || sWhID == null || invid ==
    // null) {

    // return;
    // }

    // Object oWhQty = null;
    // Object oTotalQty = null;
    Object nmaxstocknum = null;
    Object nminstocknum = null;
    Object norderpointnum = null;
    Object nsafestocknum = null;
    Object nchzl = null;
    // ȡ��ǰvo�е�����

    // ���״̬��Ҫˢ���ִ���������Ѿ��������򲻱��ض���
    // �ڱ༭--������л�ʱ���л��������ʱҪ��տ������
    // �޸�״̬�£�ѡ����ԭ�е���ҲҪ���ִ�����
    BillItem biMax = getBillCardPanel().getTailItem("nmaxstocknum");
    BillItem biMin = getBillCardPanel().getTailItem("nminstocknum");
    BillItem biOpt = getBillCardPanel().getTailItem("norderpointnum");
    BillItem biSafe = getBillCardPanel().getTailItem("nsafestocknum");
    BillItem biWhQty = getBillCardPanel().getTailItem("bkxcl");
    BillItem biBdQty = getBillCardPanel().getTailItem("xczl");

    int iFlag = 0;
    if (((biMax != null && biMax.isShow())
        || (biMin != null && biMin.isShow())
        || (biOpt != null && biOpt.isShow()) || (biSafe != null && biSafe
        .isShow()))
        && getEnvironment().getCorpID() != null
        && sWhID != null
        && invid != null) {

      // ��ѯ���ƴ���
      nmaxstocknum = voInv.getNmaxstocknum();
      nminstocknum = voInv.getNminstocknum();
      norderpointnum = voInv.getNorderpointnum();
      nsafestocknum = voInv.getNsafestocknum();
      nchzl = getM_voBill().getItemInv(row).getChzl();
      if (nmaxstocknum == null && nminstocknum == null
          && norderpointnum == null && nsafestocknum == null
          && voInv.getBkxcl() == null && voInv.getXczl() == null
          && nchzl == null) {

        iFlag += 1;
      }

    }
    if (((biWhQty != null && biWhQty.isShow()) || (biBdQty != null && biBdQty
        .isShow()))
        && getEnvironment().getCorpID() != null
        && sWhID != null
        && invid != null) {
      iFlag += 2;
    }
    switch (iFlag) {
    case 0:
      break;
    case 1:
      iFlag = QryInfoConst.QTY_CTRL;
      break;
    case 2:
      iFlag = QryInfoConst.QTY_ONHAND;
      break;
    case 3:
      iFlag = QryInfoConst.QTY_ALL;
      break;
    }
    if (iFlag > 0) {
      ArrayList alIDs = new ArrayList();
      alIDs.add(sWhID);
      alIDs.add(invid);
      alIDs.add(pk_calbody);
      alIDs.add(getEnvironment().getCorpID());
      // ��ѯ�����Ϣ
      try {
        SCMEnv.out("setTailValue1(int) ,iflag= " + iFlag);
        InvVO voInvTmp = (InvVO) GeneralBillHelper.queryInfo(
            new Integer(iFlag), alIDs);
        if (voInvTmp != null) {
          if (iFlag == QryInfoConst.QTY_CTRL
              || iFlag == QryInfoConst.QTY_ALL) {
            voInv.setNmaxstocknum(voInvTmp.getNmaxstocknum());
            voInv.setNminstocknum(voInvTmp.getNminstocknum());
            voInv.setNorderpointnum(voInvTmp.getNorderpointnum());
            voInv.setNsafestocknum(voInvTmp.getNsafestocknum());
            voInv.setNplannedprice(voInvTmp.getNplannedprice());
          }
          if (iFlag == QryInfoConst.QTY_ONHAND
              || iFlag == QryInfoConst.QTY_ALL) {
            voInv.setBkxcl(voInvTmp.getBkxcl());
            voInv.setXczl(voInvTmp.getXczl());
          }
          voInv.setChzl(new UFDouble(0));
          getM_voBill().setItemInv(row, voInv);
          // m_voBill.getItemInv(row).setChzl(new UFDouble(0));
        }
      } catch (Exception e) {
        nc.vo.scm.pub.SCMEnv.error(e);
      }

    }

    // ���ý�����ʾ
    if (biMax != null) {
      biMax.setValue(voInv.getNmaxstocknum());
    }
    if (biMin != null)
      biMin.setValue(voInv.getNminstocknum());
    if (biOpt != null)
      biOpt.setValue(voInv.getNorderpointnum());
    if (biSafe != null)
      biSafe.setValue(voInv.getNsafestocknum());
    if (biWhQty != null)
      biWhQty.setValue(voInv.getBkxcl());
    if (biBdQty != null)
      biBdQty.setValue(voInv.getXczl());

  }

  /**
   * �����ߣ����˾� ���ܣ����ñ�β��ʾ����,����null����ա� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setTailValue(InvVO voInv) {
    // �����ִ���
    nc.ui.pub.bill.BillItem biTail = null;
    biTail = getBillCardPanel().getTailItem("bkxcl");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getBkxcl());
      else
        biTail.setValue(null);
    // �ִ�����
    biTail = getBillCardPanel().getTailItem("xczl");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getBkxcl());
      else
        biTail.setValue(null);
    // ��߿��
    biTail = getBillCardPanel().getTailItem("nmaxstocknum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNmaxstocknum());
      else
        biTail.setValue(null);
    // ��Ϳ��
    biTail = getBillCardPanel().getTailItem("nminstocknum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNminstocknum());
      else
        biTail.setValue(null);
    // ��ȫ���
    biTail = getBillCardPanel().getTailItem("nsafestocknum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNsafestocknum());
      else
        biTail.setValue(null);
    // ��������
    biTail = getBillCardPanel().getTailItem("norderpointnum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNorderpointnum());
      else
        biTail.setValue(null);

  }

  /**
   * �����ߣ����˾� ���ܣ�ͬ�������ݣ����λ�����к� ������ int iFirstLine,iLastLine �кţ�start from 0 int
   * iCol �� start from 0 int type 1: add 0: update -1:delete
   * 
   * ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� 2001-06-13. ͬ��VO
   * 
   * 
   * 
   */
  protected void synchLineData(int iFirstLine, int iLastLine, int iCol,
      int iType) {
    if (iFirstLine < 0 || iLastLine < 0)
      return;
    // �޸��ˣ������� �޸����ڣ�2007-10-16����04:56:44 �޸�ԭ�򣺲����б仯��ֱ�ӷ���
    if (iType != javax.swing.event.TableModelEvent.INSERT
        && iType != javax.swing.event.TableModelEvent.UPDATE
        && iType != javax.swing.event.TableModelEvent.DELETE
        && getBillCardPanel().getRowCount() <= getM_voBill()
            .getItemCount())
      return;

    // SCMEnv.out("synch line "+iType);
    if (m_alLocatorData == null) {
      SCMEnv.out("init Locator data.");
      // m_alLocatorData=new ArrayList();
      // ��ʼ�������ݣ������ڸ��Ƶ���ʱ��m_alLocatorData==null ������������Ϊ0��
      m_alLocatorData = new ArrayList(getBillCardPanel().getBillModel()
          .getRowCount());
      for(int i = 0 ;i < getBillCardPanel().getBillModel().getRowCount() ;i++)
        m_alLocatorData.add(i,null);
    }
    if (m_alSerialData == null) {
      SCMEnv.out("init serial data.");
      // m_alSerialData=new ArrayList();
      // ��ʼ�������ݣ������ڸ��Ƶ���ʱ��m_alSerialData==null ������������Ϊ0��
      m_alSerialData = new ArrayList(getBillCardPanel().getBillModel()
          .getRowCount());
      for(int i = 0 ;i < getBillCardPanel().getBillModel().getRowCount() ;i++)
        m_alSerialData.add(i,null);
    }
    if (getM_voBill() == null)
      setM_voBill(new GeneralBillVO());

    switch (iType) {
    case javax.swing.event.TableModelEvent.INSERT:// ���У��塢׷�ӡ�ճ��
      m_alLocatorData.add(iFirstLine, null);
      m_alSerialData.add(iFirstLine, null);
      GeneralBillItemVO bodyvo = getM_voBill().insertItem(iFirstLine);
      setUIVORowPosWhenNewRow(iFirstLine, bodyvo);
      break;
    case javax.swing.event.TableModelEvent.UPDATE:
      while (getBillCardPanel().getRowCount() > getM_voBill()
          .getItemCount()) {
        // �޸��ˣ������� �޸����ڣ�2007-10-16����04:56:44 �޸�ԭ�򣺴����iFirstLine�����⣬������һ��
        iFirstLine = getBillCardPanel().getRowCount() - 1;
        if (iFirstLine < 0)
          iFirstLine = 0;
        m_alLocatorData.add(iFirstLine, null);
        m_alSerialData.add(iFirstLine, null);
        getM_voBill().insertItem(iFirstLine);
        iFirstLine++;
      }
      break;
    case javax.swing.event.TableModelEvent.DELETE:
      if (iFirstLine >= 0) {
        if (m_alLocatorData.size() > iFirstLine)
          m_alLocatorData.remove(iFirstLine);
        if (m_alSerialData.size() > iFirstLine)
          m_alSerialData.remove(iFirstLine);
        if (getM_voBill().getItemCount() > iFirstLine)
          getM_voBill().removeItem(iFirstLine);
      }
      break;
    }
  }

  /**
   * This fine grain notification tells listeners the exact range of cells,
   * rows, or columns that changed.
   */
  public void tableChanged(javax.swing.event.TableModelEvent e) {

    // try��Ŀ���Ǳ�֤addListener��ִ�С�
    try {
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      if (// e.getType() != javax.swing.event.TableModelEvent.UPDATE &&
      e.getSource() == getBillCardPanel().getBillModel())
        synchLineData(e.getFirstRow(), e.getLastRow(), e.getColumn(), e
            .getType());

    } catch (Exception eeee) {
      SCMEnv.out(eeee.getMessage());
    } finally {
      getBillCardPanel().getBillModel().addTableModelListener(this);
    }
  }

  /**
   * �����ߣ����˾� ���ܣ��ñ���ʽ�µĵ���ˢ���б����ݣ������޸ı���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void updateBillToList(GeneralBillVO bvo) {
    if (getM_iLastSelListHeadRow() < 0 || bvo == null
        || bvo.getParentVO() == null || bvo.getChildrenVO() == null) {
      SCMEnv.out("Bill is null !");
      return;
    }

    // ͨ�����ݹ�ʽ������ִ���йع�ʽ�����ķ���
    // execFormulaAtBillVO(bvo);
    execHeadFormulaAtVOs(new GeneralBillHeaderVO[] { bvo.getHeaderVO() });
    // ˢ������
    GeneralBillVO billvo = (GeneralBillVO) bvo.clone();
    getM_alListData().set(getM_iLastSelListHeadRow(), billvo);
    getBillListPanel().getHeadBillModel().setSortColumn(null);
    getBillListPanel().getHeadBillModel().setBodyRowVO(
        billvo.getParentVO(), getM_iLastSelListHeadRow());

    if (getM_iLastSelListHeadRow() > -1
        && getM_iLastSelListHeadRow() < getBillListPanel()
            .getHeadBillModel().getRowCount())
      getBillListPanel().getHeadTable().setRowSelectionInterval(
          getM_iLastSelListHeadRow(), getM_iLastSelListHeadRow());

  }

  /**
   * �����ˣ������� �������ڣ�2007-10-24����01:28:23 ����ԭ���ñ���ʽ�µĵ���ˢ���б����ݣ�ֻ�������޵ı�ͷ�ͱ����ֶΡ�
   * 
   * 
   * 
   * 
   */
  protected void updateBillToListByItemKeys(GeneralBillVO bvo,
      String[] headItemKeys, String[] bodyItemKeys) {
    if (getM_iLastSelListHeadRow() < 0 || bvo == null
        || bvo.getParentVO() == null || bvo.getChildrenVO() == null) {
      SCMEnv.out("Bill is null !");
      return;
    }

    SmartVOUtilExt.copyAggVOByAggVO((GeneralBillVO) getM_alListData().get(
        getM_iLastSelListHeadRow()), headItemKeys, bodyItemKeys,
        "cgeneralbid", bvo, headItemKeys, bodyItemKeys, "cgeneralbid");

    // ˢ���б������ʾ
    GeneralBillHeaderVO voh[] = new GeneralBillHeaderVO[getM_alListData()
        .size()];
    for (int i = 0; i < getM_alListData().size(); i++) {
      if (getM_alListData().get(i) != null)
        voh[i] = (GeneralBillHeaderVO) ((GeneralBillVO) getM_alListData()
            .get(i)).getParentVO();
      else
        SCMEnv.out("list data error!-->" + i);

    }

    setListHeadData(voh);
    // ѡ���б��ݣ���ˢ�±�����ʾ
    selectListBill(getM_iLastSelListHeadRow());

  }

  /**
   * �����ߣ����˾� ���ܣ�ȡ��ǩ�ֳɹ����� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void freshAfterCancelSignedOK() {
    try {

      // refreshSelBill(getM_iLastSelListHeadRow() );
      GeneralBillVO voBill = null;
      // ����m_voBill,�Զ�ȡ�������ݡ�
      if (m_iLastSelListHeadRow >= 0 && m_alListData != null
          && m_alListData.size() > m_iLastSelListHeadRow
          && m_alListData.get(m_iLastSelListHeadRow) != null) {
        // ���ﲻ��clone(),�ı���m_voBillͬʱ�ı�m_alListData
        voBill = (GeneralBillVO) m_alListData
            .get(m_iLastSelListHeadRow);
        // �ѵ�ǰ��¼�����õ�vo
        if (voBill != null && voBill.getHeaderVO() != null) {
          GeneralBillHeaderVO voHeader = voBill.getHeaderVO();
          voHeader.setCregister(null);
          voHeader.setCregistername(null);
          voHeader.setDaccountdate(null);
          voHeader.setAttributeValue("taccounttime", null);

          voHeader.setCauditorid(null);
          voHeader.setCauditorname(null);
          voHeader.setDauditdate(null);

          voHeader.setFbillflag(new Integer(BillStatus.FREE));
          voBill.setParentVO(voHeader);
        }
        // �����б����
        m_alListData.set(m_iLastSelListHeadRow, voBill);
        // ˢ���б���ʽ
        getBillListPanel().getHeadBillModel().setBodyRowVO(
            voBill.getParentVO(), getM_iLastSelListHeadRow());
      }
      // �ѵ�ǰ��¼���ÿ�
      // m_voBillˢ��
      if (m_voBill != null && m_voBill.getHeaderVO() != null) {
        GeneralBillHeaderVO voHeader = m_voBill.getHeaderVO();
        voHeader.setCregister(null);
        voHeader.setCregistername(null);
        voHeader.setDaccountdate(null);
        voHeader.setAttributeValue("taccounttime", null);

        voHeader.setCauditorid(null);
        voHeader.setCauditorname(null);
        voHeader.setDauditdate(null);

        voHeader.setFbillflag(new Integer(BillStatus.FREE));
        m_voBill.setParentVO(voHeader);
      }

      // �ѽ���ǩ���������ÿ�
      if (getBillCardPanel().getTailItem("cregister") != null)
        getBillCardPanel().getTailItem("cregister").setValue(null);
      if (getBillCardPanel().getTailItem("cregistername") != null)
        getBillCardPanel().getTailItem("cregistername").setValue(null);
      if (getBillCardPanel().getTailItem("daccountdate") != null)
        getBillCardPanel().getTailItem("daccountdate").setValue(null);
      if (getBillCardPanel().getTailItem("taccounttime") != null)
        getBillCardPanel().getTailItem("taccounttime").setValue(null);

      if (getBillCardPanel().getTailItem("cauditorid") != null)
        getBillCardPanel().getTailItem("cauditorid").setValue(null);
      if (getBillCardPanel().getTailItem("cauditorname") != null)
        getBillCardPanel().getTailItem("cauditorname").setValue(null);
      if (getBillCardPanel().getTailItem("dauditdate") != null)
        getBillCardPanel().getTailItem("dauditdate").setValue(null);

      if (getBillCardPanel().getHeadItem("fbillflag") != null)
        getBillCardPanel().getHeadItem("fbillflag").setValue(
            BillStatus.FREE);

      // ˢ���б���ʽ
      // setListHeadData();
      // selectListBill(m_iLastSelListHeadRow);

      // ���ð�ť״̬,ǩ�ֿ��ã�ȡ��ǩ�ֲ�����
      // ��Ӧ��һ���жϵ�ǰ�ĵ����Ƿ���ǩ��
      // δǩ�֣��������ð�ť״̬,ǩ�ֿ��ã�ȡ��ǩ�ֲ�����
      setButtonStatus(false);
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      // ��ɾ����
      if (isCurrentTypeBill()) {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(true);
      } else {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(false);
      }
      updateButtons();

    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
    }

  }

  /**
   * �����ߣ����˾� ���ܣ����ݵ�ǰ���ݵĴ���״̬����ǩ��/ȡ��ǩ���Ǹ����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSign() {
    // ֻ�����״̬�²��ҽ������е���ʱ����
    setBtnStatusSign(true);
  }

  /**
   * �����ߣ����˾� ���ܣ����ݵ�ǰ��������Ծ������кŷ����Ƿ���� ������ row�к� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSN(int row) {
    setBtnStatusSN(row, true);
  }

  /**
   * �����ߣ����˾� ���ܣ����ݵ�ǰ�ֿ��״̬������λ�����Ƿ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSpace() {
    setBtnStatusSpace(true);
  }

  /**
   * �����ߣ����˾� ���ܣ�ˢ���б���ʽ��ͷ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 2003-02-27 ���� ��ӹ�ʽ�����ȡ�ֿ�ͷ�Ʒ�ֿ���Ϣ
   */
  public void setListHeadData() {
    if (getM_alListData() != null && getM_alListData().size() > 0) {
      // ˢ���б���ʽ��ͷ����
      GeneralBillHeaderVO voh[] = new GeneralBillHeaderVO[getM_alListData()
          .size()];
      for (int i = 0; i < getM_alListData().size(); i++) {
        if (getM_alListData().get(i) != null)
          voh[i] = (GeneralBillHeaderVO) ((GeneralBillVO) getM_alListData()
              .get(i)).getParentVO();
        else
          SCMEnv.out("list data error!-->" + i);

      }
      setListHeadData(voh);
    }

  }

  /**
   * �����ߣ����Ӣ ���ܣ��õ���ǰ����vo,������λ/���к�,����ɾ�����У�δ�޸ĵ���ֻ��PK. ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  protected GeneralBillVO getBillChangedVO() {
    GeneralBillVO billVO = new GeneralBillVO();
    getBillCardPanel().getBillData().getHeaderValueVO(billVO.getParentVO());
    billVO.setChildrenVO(getBodyChangedVOs());
    return billVO;
  }

  /**
   * ���۳���Գ����� v52�� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-04-18 10:43:46)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected GeneralBillVO getCurVO() {

    GeneralBillVO billvo = null;
    try {

      if (BillMode.List == getM_iCurPanel()) {
        int selrow = getBillListPanel().getHeadTable().getSelectedRow();
        if (selrow < 0) {
          // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          // "scmcommon", "UPPSCMCommon-000167")/* @res "û��ѡ�񵥾�" */ );

          return null;
        }
        billvo = getVOFromListDataAt(selrow);

      } else {
        billvo = getM_voBill();
      }
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this, null, e);
    }

    return billvo;
  }

  /**
   * �����ߣ����Ӣ ���ܣ��õ���ǰ����vo,������λ/���кźͽ��������е�����,������ɾ������ ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  public GeneralBillVO getBillVO() {
    GeneralBillVO billVO = new GeneralBillVO();
    getBillCardPanel().getBillData().getHeaderValueVO(billVO.getParentVO());
    billVO.setChildrenVO(getBodyVOs());
    if (getM_voBill() != null) {
      getM_voBill().setIDItems(billVO);
      return getM_voBill();
    } else
      return billVO;
  }

  /**
   * �����ߣ����˾� ���ܣ��õ������ĵ��ݱ���VO������ɾ�����С� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected GeneralBillItemVO[] getBodyChangedVOs() {
    nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();
    if (bmTemp == null) {
      SCMEnv.out("bm null ERROR!");
      return null;
    }
    Vector vBodyData = bmTemp.getDataVector();
    if (vBodyData == null || vBodyData.size() == 0) {
      SCMEnv.out("bd null ERROR!");
      return null;
    }
    // Ϊ�˵õ������
    GeneralBillItemVO[] voaItemForFree = getM_voBill().getItemVOs();
    // ɾ������

    // vo����ĳ���==��ǰ��ʾ������+ɾ����������
    int rowCount = vBodyData.size();
    int length = 0;
    Vector vDeleteRow = bmTemp.getDeleteRow();
    if (vDeleteRow != null)
      length = rowCount + vDeleteRow.size();
    else
      length = rowCount;
    // ��ʼ�����ص�vo
    GeneralBillItemVO[] voaBody = new GeneralBillItemVO[length];

    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    // ����ǰ��������ʾ���У�����ԭ�С��޸ĺ���С��������С�
    // ����������
    for (int i = 0; i < vBodyData.size(); i++) {
      voaBody[i] = new GeneralBillItemVO();
      //�����б�ʶ
      voaBody[i].setRowpos((String)bmTemp.getValueAt(i, GeneralBillItemVO.RowPos));
      iRowStatus = bmTemp.getRowState(i);
      for (int j = 0; j < bmTemp.getBodyItems().length; j++) {
        nc.ui.pub.bill.BillItem item = bmTemp.getBodyItems()[j];

        Object aValue = bmTemp.getValueAt(i, item.getKey());

        // SCMEnv.out(item.getKey()+aValue);
        voaBody[i].setAttributeValue(item.getKey(), aValue);
      }
      // ����״̬
      switch (iRowStatus) {
      case nc.ui.pub.bill.BillModel.ADD: // ��������
        voaBody[i].setStatus(nc.vo.pub.VOStatus.NEW);
        break;
      case nc.ui.pub.bill.BillModel.MODIFICATION: // �޸ĺ����
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UPDATED);
        break;
      case nc.ui.pub.bill.BillModel.NORMAL: // ԭ��
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UNCHANGED);
        break;
      }
      // ��λ��������
      if (m_alLocatorData != null && m_alLocatorData.size() > i)
        voaBody[i].setLocator((LocatorVO[]) m_alLocatorData.get(i));
      // ���к�����
      if (m_alSerialData != null && m_alSerialData.size() > i)
        voaBody[i].setSerial((SerialVO[]) m_alSerialData.get(i));
      // ������
      voaBody[i].setFreeItemVO(voaItemForFree[i].getFreeItemVO());

    }
    // ɾ�����д���2003-02-09 wnj:�ô�ԭ�еĵ����п����������λ�����кŶ�û���ˡ�
    if (vDeleteRow != null && vDeleteRow.size() > 0) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
        // =========
        int col = bmTemp.getBodyColByKey(IItemKey.NAME_BODYID); // ����PK
        Vector rowVector = null;
        String sItemPK = null;
        GeneralBillVO voOriginalBill = (GeneralBillVO) getM_alListData()
            .get(getM_iLastSelListHeadRow());
        GeneralBillItemVO[] voaOriginalItem = voOriginalBill
            .getItemVOs();
        // ��ѯɾ�����е�pk,��ԭ�����в���֮��
        // ��������н϶࣬�����Ż�Ϊhastable.
        for (int del = 0; del < vDeleteRow.size(); del++) {
          rowVector = (Vector) vDeleteRow.elementAt(del);
          sItemPK = (String) rowVector.elementAt(col);
          // ��ԭ�����в���֮��
          if (sItemPK != null)
            for (int item = 0; item < voaOriginalItem.length; item++)
              if (sItemPK.equals(voaOriginalItem[item]
                  .getPrimaryKey()))
                voaBody[del + rowCount] = (GeneralBillItemVO) voaOriginalItem[item]
                    .clone();
          // ����״̬
          voaBody[del + rowCount]
              .setStatus(nc.vo.pub.VOStatus.DELETED);
        }
      } else
        SCMEnv.out("update err,cannot dup del rows.");

    }
    return voaBody;
  }

  /**
   * �����ߣ����˾� ���ܣ��õ��޸ĺ��vo,�����޸ĺ�ı��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected GeneralBillItemVO[] getBodyVOs() {
    nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();
    if (bmTemp == null) {
      SCMEnv.out("bm null ERROR!");
      return null;
    }
    Vector vBodyData = bmTemp.getDataVector();
    if (vBodyData == null || vBodyData.size() == 0) {
      SCMEnv.out("bd null ERROR!");
      return null;
    }
    // Ϊ�˵õ������
    GeneralBillItemVO[] voaItemForFree = getM_voBill().getItemVOs();

    // vo����ĳ���==��ǰ��ʾ������
    int rowCount = vBodyData.size();
    // ��ʼ�����ص�vo
    GeneralBillItemVO[] voaBody = new GeneralBillItemVO[rowCount];
    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    // ����ǰ��������ʾ���У�����ԭ�С��޸ĺ���С��������С�
    for (int i = 0; i < rowCount; i++) {
      voaBody[i] = new GeneralBillItemVO();
      iRowStatus = bmTemp.getRowState(i);
      
      //�����б�ʶ
      voaBody[i].setRowpos((String)bmTemp.getValueAt(i, GeneralBillItemVO.RowPos));
      
      for (int j = 0; j < bmTemp.getBodyItems().length; j++) {

        nc.ui.pub.bill.BillItem item = bmTemp.getBodyItems()[j];
        Object aValue = bmTemp.getValueAt(i, item.getKey());

        // SCMEnv.out(item.getKey()+" "+aValue);
        voaBody[i].setAttributeValue(item.getKey(), aValue);
      }
      // ����״̬
      switch (iRowStatus) {
      case nc.ui.pub.bill.BillModel.ADD: // ��������
        voaBody[i].setStatus(nc.vo.pub.VOStatus.NEW);
        break;
      case nc.ui.pub.bill.BillModel.MODIFICATION: // �޸ĺ����
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UPDATED);
        break;
      case nc.ui.pub.bill.BillModel.NORMAL: // ԭ��
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UNCHANGED);
        break;
      }
      // ��λ��������
      if (m_alLocatorData != null && m_alLocatorData.size() > i)
        voaBody[i].setLocator((LocatorVO[]) m_alLocatorData.get(i));

      // ���к�����
      if (m_alSerialData != null && m_alSerialData.size() > i) {
        SerialVO[] serialVOs = (SerialVO[]) m_alSerialData.get(i);
        // �������к�����
        voaBody[i].updateSerialDate(serialVOs);
        // �������к�
        voaBody[i].setSerial(serialVOs);
      }

      // ������
      voaBody[i].setFreeItemVO(voaItemForFree[i].getFreeItemVO());

      // ɾ�����в���
    }
    return voaBody;
  }

  /**
   * �����ߣ����˾� ���ܣ��õ���ǰ�����Ƿ���ǩ�� ������ ���أ� ��ǩ�� 1 δǩ�� 0 ���ܲ��� -1 ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public int isSigned() {
    GeneralBillVO voBill = null;
    // ����voBill,�Զ�ȡ�������ݡ�

    if (getM_iCurPanel() == BillMode.List
        && getM_iLastSelListHeadRow() >= 0 && getM_alListData() != null
        && getM_alListData().size() > getM_iLastSelListHeadRow()
        && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
      voBill = (GeneralBillVO) getM_alListData().get(
          getM_iLastSelListHeadRow());
    else
      voBill = getM_voBill();

    if (voBill != null) {
      
//    �Ƿ���ǩ����
      Integer fbillflag = ((GeneralBillHeaderVO) voBill.getHeaderVO())
          .getFbillflag();
      if (fbillflag != null && (fbillflag.intValue()==3 || fbillflag.intValue()==4))
        return SIGNED;
      
//     �Ƿ���ǩ����
      String sSignerID = ((GeneralBillHeaderVO) voBill.getHeaderVO())
          .getCregister();
      if (sSignerID != null && sSignerID.trim().length() > 0)
        return SIGNED;
      
      if (null == voBill.getHeaderVO().getCgeneralhid())
        return CANNOTSIGN;
      
      int iCount = voBill.getItemCount();
      int i = 0;
      GeneralBillItemVO voaItem[] = voBill.getItemVOs();
      for (i = 0; i < iCount; i++) {
        // ����ʵ��/������
        if ((voaItem[i].getNinnum() == null || voaItem[i].getNinnum()
            .toString().length() == 0)
            && (voaItem[i].getNoutnum() == null || voaItem[i]
                .getNoutnum().toString().length() == 0))
          break;

      }

      if (i < iCount) // ������
        return CANNOTSIGN;

      // �Ƿ���ǩ����
//      String sSignerID = ((GeneralBillHeaderVO) voBill.getHeaderVO())
//          .getCregister();
//      if (sSignerID != null && sSignerID.trim().length() > 0)
//        return SIGNED;
//      else
        return NOTSIGNED;
    } else
      return CANNOTSIGN;
  }

  /**
   * �����ߣ����˾� ���ܣ��ڱ�������ʾVO ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setBillVO(GeneralBillVO bvo) {
    setBillVO(bvo, true, true);
  }

  /**
   * �����ߣ������� ���ܣ������� ������ ���أ� ���⣺ ���ڣ�(2001-5-24 ���� 5:17) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public boolean checkVO() {
    try {
      String sAllErrorMessage = "";
      ArrayList alRowNum = new ArrayList();
      Color cExceptionColor = null;

      // ִ�����¼�飬�������еļ��ע��------------------------------------------------
      // ------------------------------------------------------------------------------
      // VO���ڼ��
      VOCheck.checkNullVO(getM_voBill());
      // ------------------------------------------------------------------------------
      // Ӧ���������,Ҫ����ǰ��
      // ���ڵ�ʹ��=====================
      // ��������ķ����ҵ�����Ƿ�һ��.�˻��˿������ҪΪ��,���˿��˻�����Ϊ��
      boolean isRight = VOCheck.isNumDirectionRight(getM_voBill());
      if (!isRight) {
        sAllErrorMessage = nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008check", "UPP4008check-000213")/*
                               * @res
                               * "�����ķ����ҵ����һ�£�"
                               */;
        showErrorMessage(sAllErrorMessage);
        return false;
      }
      // ��ֵ����ȫ���Լ�� v5 ֧�ֻ���
      // VOCheck.checkNumInput(m_voBill.getChildrenVO(),
      // getEnvironment().getNumItemKey());

      // ���ڵ�ʹ��=====================
      // ��ͷ����ǿռ��
      try {
        VOCheck.validate(getM_voBill(), GeneralMethod
            .getHeaderCanotNullString(getBillCardPanel()),
            GeneralMethod
                .getBodyCanotNullString(getBillCardPanel()));
      } catch (ICNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
      } catch (ICHeaderNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getHeaderErrorMessage(
            getBillCardPanel(), e.getErrorRowNums(), e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        
      }

      /*
       * // �ɹ������˿��ͷVO��ҵ�����͵ķǿ����� try {
       * VOCheck.validatePO_RETURN(m_voBill); } catch
       * (ICHeaderNullFieldException e) { // ��ʾ��ʾ String sErrorMessage =
       * GeneralMethod.getHeaderErrorMessage( getBillCardPanel(),
       * e.getErrorRowNums(), e.getHint()); sAllErrorMessage =
       * sAllErrorMessage + sErrorMessage + "\n"; }
       */

      // �е�������ĵ�������У��
      if (m_bIsImportData)
        sAllErrorMessage = sAllErrorMessage
            + nc.ui.ic.pub.bill.GeneralBillUICtl
                .checkImportBodyVO(getM_voBill()
                    .getChildrenVO());
      // ------------------------------------------------------------------------------
      // ҵ������

      // ������
      try {
        VOCheck.checkFreeItemInput(getM_voBill(), getEnvironment()
            .getNumItemKey());

      } catch (ICNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);

      }
      // ������
      try {
        VOCheck.checkAssistUnitInputByID(getM_voBill(),
            getEnvironment().getNumItemKey(), getEnvironment()
                .getAssistNumItemKey());
      } catch (ICNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
      }

      try {
        VOCheck.checkAssistUnitInputByID(getM_voBill(),
            getEnvironment().getAssistNumItemKey(),
            getEnvironment().getNumItemKey());
      } catch (ICNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
      }

      // �������
      try {
        if (getBillCardPanel().getBodyItem("dbizdate") != null)
          VOCheck.checkdbizdate(getM_voBill(), getEnvironment()
              .getNumItemKey());
      } catch (ICNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        if(!sAllErrorMessage.contains(NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000002")/*��������*/)&&!sAllErrorMessage.contains(NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000003")/*�������*/)){
          sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";     
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
        }
      }
      // ���ڳ������ҵ�����������ϵͳ��������
      if (!m_bIsInitBill) {
        if (getBillCardPanel().getBodyItem("dbizdate") != null) {
          String sBizdateColName = getBillCardPanel().getBodyItem(
              "dbizdate").getName();
          try {
            VOCheck.checkStartDateAfter(getM_voBill(), "dbizdate",
                sBizdateColName, m_sStartDate);
          } catch (NullFieldException e) {
            String sErrorMessage = e.getHint();
            sAllErrorMessage = sAllErrorMessage + sErrorMessage
                + "\n";
          }
        }
      }
      // �۸�>0���
      try {
        VOCheck.checkGreaterThanZeroInput(
            getM_voBill().getChildrenVO(), "nprice",
            nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
                "UC000-0000741")/* @res "����" */);
      } catch (ICPriceException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
      }
      // ���кż��
      try {
         /*   
              VOCheck.checkSNInput(getM_voBill().getChildrenVO(),
                  getEnvironment().getNumItemKey());*/
            /*modified by  lirr 2009-02-25 
            @�޸�ԭ�� v56���� �ʲ������ڷ��ʲ��ֳ���ʱ�Ƿ���Բ��������кŲ���
            @param isChechAssetInv �ʲ������ڷ��ʲ��ֳ���ʱ�Ƿ���Բ��������кŲ���*/
    	  if(getM_voBill().getWh()==null) {
    		  showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec","UPP4008spec-000169")/*@res "������ֿ⣡"*/);
    		  return false;
    	  }

    	  boolean isCapitalStor = getM_voBill().getWh().getIsCapitalStor()==null ? false:getM_voBill().getWh().getIsCapitalStor().booleanValue();
		  String cbilltypecode = getM_voBill().getCbilltypecode();
		  if ((cbilltypecode.equals(BillTypeConst.m_purchaseIn) || 
				  cbilltypecode.equals(BillTypeConst.m_allocationIn)) && 
				  isCapitalStor ) {
				  // �ɹ��롢�����뵥�ݣ������ʲ��֣���������кţ�V57����
				  // DO NOTHING
		  }
		  else {
	          VOCheck.checkSNInput(getM_voBill().getChildrenVO(),
	                  getEnvironment().getNumItemKey(),isCheckAssetInv(),
	                  isCapitalStor,getM_voBill().getBizTypeid());
		  }
      } catch (ICSNException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
      } catch (NullFieldException e) {
        String sErrorMessage = e.getHint();
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
      }

      
      // ����Ӧ���ݺ�
      //�޸��ˣ������� �޸����ڣ�2008-5-16����01:33:32 �޸�ԭ�򣺽��ת����ҵ��ʱ������������������Ҫ¼���Ӧ���ݺš�
      String busiVerifyrule = null;
      if (null != getM_voBill().getHeaderVO().getCbiztypeid()
          && !"".equals(getM_voBill().getHeaderVO().getCbiztypeid()))
        busiVerifyrule = getBusiVerifyrule(getM_voBill().getHeaderVO().getCbiztypeid());
      if (null == busiVerifyrule || !"C".equals(busiVerifyrule)){
        ArrayList alCheckString = new ArrayList();
        for (int i = 0; i < getM_voBill().getItemVOs().length; i++) {
          boolean bCanAdded = false;
          if (getInOutFlag() == InOutFlag.IN) {
            // ��ⵥ
            if (getIsInvTrackedBill(getM_voBill().getItemInv(i))
                && getM_voBill().getItemValue(i,
                    getEnvironment().getNumItemKey()) != null
                && ((UFDouble) getM_voBill().getItemValue(i,
                    getEnvironment().getNumItemKey()))
                    //modified by liuzy 2008-05-22 �ѵ���ȥ�������ʵ�տ���Ϊ0
                    .doubleValue() < 0) {
              // ����<0
              bCanAdded = true;
            }
          } else {
            // ���ⵥ
            if (getIsInvTrackedBill(getM_voBill().getItemInv(i))
                && (getM_voBill().getItemValue(i,
                    getEnvironment().getNumItemKey()) == null || ((UFDouble) getM_voBill()
                    .getItemValue(i,
                        getEnvironment().getNumItemKey()))
                    .doubleValue() >= 0)) {
              // ����>=0
              bCanAdded = true;
            }
          }
          if (bCanAdded) {
            ArrayList alCheckddd = new ArrayList();
            alCheckddd.add(0, new Integer(i));
            alCheckddd.add(1, getEnvironment().getNumItemKey());
            if (getBillCardPanel().getBodyItem(IItemKey.CORRESCODE) != null) {
              alCheckddd.add(2, IItemKey.CORRESCODE); // ��Ӧ���ݺ��ֶ�1
              alCheckddd.add(3, "ccorrespondbid"); // ��Ӧ���ݺ��ֶ�2
            }
            alCheckString.add(alCheckddd);
          }
        }
        try {
          VOCheck.validateBody(getM_voBill().getItemVOs(), alCheckString);
        } catch (ICNullFieldException e) {
          // ��ʾ��ʾ
          String sErrorMessage = GeneralMethod.getBodyErrorMessage(
              getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
              e.getHint());
          sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
          alRowNum.addAll(e.getErrorRowNums());
          cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
        }
      // }
      }

      // �Զ�У��ǰ���б����˳�
      if (sAllErrorMessage.trim().length() != 0) {
        showErrorMessage(sAllErrorMessage);
        // ������ɫ
/*        SetColor.SetTableColor(
            getBillCardPanel().getBillModel(),
            getBillCardPanel().getBillTable(),
            getBillCardPanel(),
            alRowNum,
            m_cNormalColor,
            cExceptionColor,
            m_bExchangeColor,
            m_bLocateErrorColor,
            "",m_bRowLocateErrorColor);*/
        //SetColor.setErrRowColor(getBillCardPanel().getBillTable(), alRowNum);
        nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), alRowNum);
        return false;
      }

      // У�������������ܴ���ʵ���շ�����
      GeneralBillItemVO[] voaItemtemp = (GeneralBillItemVO[]) getM_voBill()
          .getChildrenVO();
      String sMsg = BarcodeparseCtrl
          .checkNumWithBarNum(voaItemtemp, true);
      if (sMsg != null) {
        showErrorMessage(sMsg);
        return false;
      }

      // ����ʱ��������ʹ����������Լ�飬���������ʱ��ǿ�Ʋ�ͨ��
      sMsg = BarcodeparseCtrl.checkBarcodesubIntegrality(voaItemtemp);
      if (sMsg != null) {
        showErrorMessage(sMsg);
        return false;
      }

      // ���ҵ��������W����ֿ��������Ĳ�.

      try {
        checkVMIWh(getM_voBill());

      } catch (Exception ex1) {
        showErrorMessage(ex1.getMessage());

        return false;

      }

      // �����Ϊ��ë�ع�����ı�������ë��
      try {
        VOCheck.checkGrossNumInput(getM_voBill().getItemVOs(),
            getEnvironment().getGrossNumItemKey(), getEnvironment()
                .getNumItemKey());
      } catch (ICNullFieldException e) {
        // ��ʾ��ʾ
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        // ������ɫ
        nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);
      }

      return true;

    } catch (ICDateException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      // showHintMessage(e.getHint());
      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);
      
      return false;
    } catch (ICNullFieldException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());

      showErrorMessage(sErrorMessage);
      
      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICNumException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICPriceException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICSNException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICLocatorException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICRepeatException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);

      // ������ɫ
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);
      
      return false;
    } catch (ICHeaderNullFieldException e) {
      // ��ʾ��ʾ
      String sErrorMessage = GeneralMethod.getHeaderErrorMessage(
          getBillCardPanel(), e.getErrorRowNums(), e.getHint());
      // String sErrorMessage= getHeaderErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      // showHintMessage(e.getHint());
      // showErrorMessage( e.getMessage());
      
      return false;
    } catch (NullFieldException e) {
      showErrorMessage(e.getHint());
      // fullScreen(getBillCardPanel().getBillModel(), m_iFirstAddRows);
      return false;
    } catch (ValidationException e) {
      SCMEnv.out("У���쳣������δ֪����...");/*-=notranslate=-*/
      handleException(e);
      return false;
    }
  }

  /**
   * �����ߣ����˾� ���ܣ��õ��޸ĺ��vo,�����޸ĺ�ı��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(java.awt.event.ActionEvent e) {

    if (e.getSource() == getAddNewRowNoItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_ADD_NEWROWNO));
      // onAddNewRowNo();
    }else if (e.getSource() == getMiLineCardEdit()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_CARD_EDIT));
    }
//    else if (e.getSource() == getMiLineBatchEdit()) {
//      onButtonClicked(getButtonManager().getButton(
//          ICButtonConst.BTN_BATCH_EDIT));
//    }
  }

  /**
   * �����ߣ����˾� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  /**
   * �����ߣ����˾� ���ܣ��õ���ǰ��ʾ��panel ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public int getCurPanel() {
    return getM_iCurPanel();
  }

  /**
   * �����ߣ����˾� ���ܣ����ݱ����Ҽ��˵����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void onMenuItemClick(java.awt.event.ActionEvent e) {
    // Դ
    UIMenuItem item = (UIMenuItem) e.getSource();
    // ����
    if (item == getBillCardPanel().getCopyLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_COPY));
      // onCopyLine();
    } else // ճ��
    if (item == getBillCardPanel().getPasteLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_PASTE));
      // onPasteLine();
    }
    // ճ������βʱ,�����к�
    else if (item == getBillCardPanel().getPasteLineToTailMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_PASTE_TAIL));

    } else // ����
    if (item == getBillCardPanel().getAddLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_ADD));
      // onAddLine();

    } else // ɾ��
    if (item == getBillCardPanel().getDelLineMenuItem())
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_DELETE));
    // onDeleteRow();
    else // ������
    if (item == getBillCardPanel().getInsertLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_INSERT));
      // onInsertLine();
    }
    
//  ���ο�����չ
    getPluginProxy().onMenuItemClick(e);

  }

  /**
   * �����ߣ������� ���ܣ������� ������ ���أ� ���⣺ ���ڣ�(2001-6-26 ���� 9:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  protected void voBillCopyLine() {
    int[] row = getBillCardPanel().getBillTable().getSelectedRows();
    if (row == null || row.length == 0) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "common", "UCH004")/* @res "��ѡ��Ҫ�����������" */);
      return;
    }
    m_voaBillItem = new GeneralBillItemVO[row.length];
    GeneralBillItemVO uicopyvo = null;
    for (int i = 0; i < row.length; i++) {
      getM_voBill().getItemVOs()[row[i]]
          .setLocator((LocatorVO[]) m_alLocatorData.get(row[i]));
      m_voaBillItem[i] = (GeneralBillItemVO) getM_voBill().getItemVOs()[row[i]]
          .clone();
      uicopyvo = (GeneralBillItemVO) getBillCardPanel().getBillModel()
          .getBodyValueRowVO(row[i],
              GeneralBillItemVO.class.getName());
      uicopyvo = (GeneralBillItemVO) uicopyvo.clone();
      String[] keys = uicopyvo.getAttributeNames();
      // �޸��ˣ������� �޸����ڣ�2007-12-27����07:38:49
      // �޸�ԭ�򣺶���invvo��freevo����ʵ���Ķ��󣬲��ܸ��ơ�
      SmartVOUtilExt.copyVOByVO(m_voaBillItem[i], keys, uicopyvo, keys,
          new String[] { "invvo", "freevo" });
      // �����λ�����кţ���Щ�����ǲ����Ƶ�,�� m_alLoctorData,m_alSerialData����һ��
      // ydy 2004-07-02 ��λ����
      try {
        if (m_alLocatorData.get(row[i]) != null)
          m_voaBillItem[i].setLocator((LocatorVO[]) ObjectUtils
              .serializableClone(m_alLocatorData.get(row[i])));
      } catch (Exception e) {
        nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
      }
      // m_voaBillItem[i].setLocator((LocatorVO[])m_alLocatorData.get(row[i]));
      m_voaBillItem[i].setSerial(null);
      m_voaBillItem[i].setAttributeValue("cparentid", null);
      m_voaBillItem[i].setAttributeValue("ncorrespondnum", null);
      m_voaBillItem[i].setAttributeValue("ncorrespondastnum", null);

      // ����������� add by hanwei 2004-04-07
      m_voaBillItem[i].setBarCodeVOs(null);
      m_voaBillItem[i].setAttributeValue(IItemKey.NBARCODENUM,
          new UFDouble(0.0));

      m_voaBillItem[i].setBarcodeClose(new nc.vo.pub.lang.UFBoolean('N'));

      m_voaBillItem[i].setAttributeValue(IItemKey.NKDNUM, null);
    }

  }

  /**
   * �����ߣ������� ���ܣ�ճ���� ������ ���أ� ���⣺ ���ڣ�(2001-6-26 ���� 9:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void voBillPastLine() {
    // Ҫ�����Ѿ������к�ִ��
    if (m_voaBillItem != null) {
      int row = getBillCardPanel().getBillTable().getSelectedRow()
          - m_voaBillItem.length;
      voBillPastLine(row);
    }
  }

  /**
   * �����ߣ����˾� ���ܣ��õ��������� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public int getBillCount() {
    return m_iBillQty;
  }

  /**
   * �����ߣ����˾� ���ܣ��õ�ָ���е�VO ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected GeneralBillItemVO getBodyVO(int iLine) {
    nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();
    if (bmTemp == null) {
      SCMEnv.out("bm null E!");
      return null;
    }
    Vector vBodyData = bmTemp.getDataVector();
    if (vBodyData == null || vBodyData.size() == 0
        || iLine >= vBodyData.size()) {
      SCMEnv.out("bd null or line big E!");
      return null;
    }
    // Ϊ�˵õ������
    GeneralBillItemVO voItemForFree = getM_voBill().getItemVOs()[iLine];

    // vo����ĳ���==��ǰ��ʾ������
    // int rowCount = vBodyData.size();
    // ��ʼ�����ص�vo
    GeneralBillItemVO voBody = new GeneralBillItemVO();

    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    // ����ǰ��������ʾ���У�����ԭ�С��޸ĺ���С��������С�
    iRowStatus = bmTemp.getRowState(iLine);
    for (int j = 0; j < bmTemp.getBodyItems().length; j++) {
      nc.ui.pub.bill.BillItem item = bmTemp.getBodyItems()[j];
      Object aValue = bmTemp.getValueAt(iLine, item.getKey());
      voBody.setAttributeValue(item.getKey(), aValue);
    }
    
    //�����б�ʶ
    voBody.setRowpos((String)bmTemp.getValueAt(iLine, GeneralBillItemVO.RowPos));
    
    // ����״̬
    switch (iRowStatus) {
    case nc.ui.pub.bill.BillModel.ADD: // ��������
      voBody.setStatus(nc.vo.pub.VOStatus.NEW);
      break;
    case nc.ui.pub.bill.BillModel.MODIFICATION: // �޸ĺ����
      voBody.setStatus(nc.vo.pub.VOStatus.UPDATED);
      break;
    case nc.ui.pub.bill.BillModel.NORMAL: // ԭ��
      voBody.setStatus(nc.vo.pub.VOStatus.UNCHANGED);
      break;
    }
    // ��λ��������
    if (m_alLocatorData != null && m_alLocatorData.size() > iLine)
      voBody.setLocator((LocatorVO[]) m_alLocatorData.get(iLine));
    // ���к�����
    if (m_alSerialData != null && m_alSerialData.size() > iLine)
      voBody.setSerial((SerialVO[]) m_alSerialData.get(iLine));
    // ������
    voBody.setFreeItemVO(voItemForFree.getFreeItemVO());

    // ɾ�����в���
    return voBody;
  }

  /**
   * �����ߣ����˾� ���ܣ���ǰѡ�����Ƿ������кŷ��䣬Ҫ�����б�/���µ�ѡ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public boolean isSNmgt() {
    // ��ǰѡ�е���
    int iCurSelBodyLine = -1;
    if (BillMode.Card == getM_iCurPanel()) {
      iCurSelBodyLine = getBillCardPanel().getBillTable()
          .getSelectedRow();
      if (iCurSelBodyLine < 0) {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000016")/*
                               * @res
                               * "��ѡ��Ҫ�������кŷ�����С�"
                               */);
        return false;
      }
    } else {
      iCurSelBodyLine = getBillListPanel().getBodyTable()
          .getSelectedRow();
      if (iCurSelBodyLine < 0) {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000017")/*
                               * @res
                               * "��ѡ��Ҫ�鿴���кŵ��С�"
                               */);
        return false;
      }
    }
    InvVO voInv = null;
    // ������vo,�����б��»��Ǳ���
    // ����ʽ��
    if (BillMode.Card == getM_iCurPanel()) {
      if (getM_voBill() == null) {
        SCMEnv.out("bill null E.");
        return false;
      }
      voInv = getM_voBill().getItemInv(iCurSelBodyLine);
    } else // �б���ʽ��
    if (getM_iLastSelListHeadRow() >= 0
        && getM_iLastSelListHeadRow() < getM_alListData().size()) {
      GeneralBillVO bvo = (GeneralBillVO) getM_alListData().get(
          getM_iLastSelListHeadRow());
      if (bvo == null) {
        SCMEnv.out("bill null E.");
        return false;
      }
      voInv = bvo.getItemInv(iCurSelBodyLine);
    }

    if (voInv != null && voInv.getIsSerialMgt() != null
        && voInv.getIsSerialMgt().intValue() == 1)
      return true;
    else
      return false;
  }

  /**
   * �����ߣ����˾� ���ܣ�����״̬����ȱʡ����²������á� ��Ҫ�Ļ����԰���ʾ��Ϣ�ض���ָ����TextField ������ ���أ� ���⣺
   * ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setHintBar(javax.swing.JTextField tfHint) {
    m_tfHintBar = tfHint;
  }

  /**
   * �����ߣ����˾� ���ܣ����ص���ʾ��ʾ��Ϣ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void showHintMessage(String sMsg) {
    if (m_tfHintBar != null)
      m_tfHintBar.setText(sMsg);
    else
      super.showHintMessage(sMsg);
  }

  /**
   * �����ߣ����˾� ���ܣ��õ���ǰѡ�еĵ��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public int getCurSelBill() {
    return getM_iLastSelListHeadRow();
  }

  /**
   * �����ߣ����˾� ���ܣ��Ƿ��ǻ�λ����ֻ�����ڳ���ⵥ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public boolean isLocatorMgt() {
    if (getM_voBill() != null && BillMode.Card == getM_iCurPanel()) {
      WhVO voWh = getM_voBill().getWh();
      // ��λ����Ĳֿ���Ҫ����λ����
      if (voWh != null && voWh.getIsLocatorMgt() != null
          && voWh.getIsLocatorMgt().intValue() == 1)
        return true;
      else
        return false;

    }
    if (getM_alListData() == null || getM_iLastSelListHeadRow() < 0
        || getM_iLastSelListHeadRow() >= getM_alListData().size())
      return false;
    GeneralBillVO vob = (GeneralBillVO) getM_alListData().get(
        getM_iLastSelListHeadRow());
    if (vob != null) {
      WhVO voWh = vob.getWh();
      // ��λ����Ĳֿ���Ҫ����λ����
      if (voWh != null && voWh.getIsLocatorMgt() != null
          && voWh.getIsLocatorMgt().intValue() == 1)
        return true;
      else
        return false;
    } else
      return false;
  }

  /**
   * �����ߣ����˾� ���ܣ����ָ���С�ָ���е����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void clearRowData(int iBillFlag, int row, String sItemKey) {
    // �õ��������itemkey
    String[] saColKey = GeneralBillUICtl.getClearIDs(iBillFlag, sItemKey,
        this);
    if (saColKey != null && saColKey.length > 0)
      clearRowData(row, saColKey);

  }

  /**
   * �����ߣ����Ӣ ���ܣ����ָ���С�ָ���е����� ���������˸����� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void filterMeas(int row) {

    nc.vo.scm.ic.bill.InvVO voInv = getM_voBill().getItemInv(row);
    if (voInv.getIsAstUOMmgt() == null
        || voInv.getIsAstUOMmgt().intValue() == 0)
      return;

    nc.ui.pub.beans.UIRefPane refCast = (nc.ui.pub.beans.UIRefPane) getBillCardPanel()
        .getBodyItem("castunitname").getComponent();

    m_voInvMeas.filterMeas(getEnvironment().getCorpID(), voInv
        .getCinventoryid(), refCast);

  }

  /**
   * ���˵��ݲ��� �����ߣ����� ���ܣ���ʼ�����չ��� ������ ���أ� ���⣺ ���ڣ�(2001-7-17 10:33:20)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void filterRef(String sCorpID) {
    try {
      // ���˲ֿ����
      nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
          IItemKey.WAREHOUSE);
      RefFilter.filtWh(bi, sCorpID, getFilterWhString(null));

      if (!(getBillType().equals("4Y") || getBillType().equals("4E"))) {
        bi = getBillCardPanel().getHeadItem("cotherwhid");
        RefFilter.filtWh(bi, sCorpID, null);
      }
      // ���˿����֯ add by hanwei 2004-05-09
      bi = getBillCardPanel().getHeadItem("pk_calbody");
      RefFilter.filtCalbody(bi, sCorpID, null);

      // ���˴������
      bi = getBillCardPanel().getBodyItem("cinventorycode");
      nc.ui.pub.beans.UIRefPane invRef = (nc.ui.pub.beans.UIRefPane) bi
          .getComponent();
      invRef.setTreeGridNodeMultiSelected(true);
      invRef.setMultiSelectedEnabled(true);
      // invRef.getRefModel().setIsDynamicCol(true);
      // invRef.getRefModel().setDynamicColClassName("nc.ui.scm.pub.RefDynamic");

      RefFilter.filtInv(bi, sCorpID, null);

      // ���˱�ͷ�������
      bi = getBillCardPanel().getHeadItem("cinventoryid");
      RefFilter.filtInv(bi, sCorpID, null);
      // ���˱�ͷ�ڶ����ֿ����
      bi = getBillCardPanel().getHeadItem(IItemKey.WASTEWAREHOUSE);
      if (bi != null && bi.getDataType() == BillItem.UFREF)
        RefFilter.filtWasteWh(bi, sCorpID, null);
      // ���˿ͻ�����
      bi = getBillCardPanel().getHeadItem("ccustomerid");
      RefFilter.filtCust(bi, sCorpID, null);

      // ����ҵ��Ա���� 2003-11-20
      bi = getBillCardPanel().getHeadItem("cbizid");
      RefFilter.filterPsnByDept(bi, sCorpID, new String[] { null });

      // ���˹�Ӧ�̲���
      bi = getBillCardPanel().getHeadItem("cproviderid");
      RefFilter.filtProvider(bi, sCorpID, null);
      // �����շ����Ͳ��գ�����ⲻһ����
      bi = getBillCardPanel().getHeadItem("cdispatcherid");
      if (getInOutFlag() == InOutFlag.IN)
        RefFilter.filtDispatch(bi, sCorpID, 0, null);
      else
        RefFilter.filtDispatch(bi, sCorpID, 1, null);
      // ��ͷ���˵�ַ:�����Զ���飬�������ơ�
      if (getBillCardPanel().getBodyItem("vreceiveaddress") != null
          && getBillCardPanel().getBodyItem("vreceiveaddress")
              .getComponent() != null) {
        nc.ui.pub.beans.UIRefPane vdlvr = (nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem("vreceiveaddress").getComponent();
        vdlvr.setAutoCheck(false);

        filterVdiliveraddressRef(true, -1);

      }
      // ���˳ɱ��������
      if (getBillCardPanel().getBodyItem(IItemKey.COSTOBJECTNAME) != null
          && getBillCardPanel().getBodyItem(IItemKey.COSTOBJECTNAME)
              .getComponent() != null) {
        if (!getBillType().equals("4F")) {
          bi = getBillCardPanel()
              .getBodyItem(IItemKey.COSTOBJECTNAME);
          nc.ui.ic.pub.bill.initref.RefFilter.filterCostObject(bi);
          // filterCostObject();
        }
      }
      // // ���˼�������
      // nc.ui.pub.bill.BillItem bi2 = getBillCardPanel().getHeadItem(
      // "pk_measware");
      // // UIRefPane refCalbody =
      // //
      // (UIRefPane)getBillCardPanel().getHeadItem("pk_calbody").getComponent();
      // // String pk_calbody = refCalbody.getRefPK();
      // // String[] s = new String[1];
      // // s[0] = " and mm_jldoc.gcbm='" + pk_calbody + "'";
      // RefFilter.filtMeasware(bi2, sCorpID, null);

      
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }

  /**
   * �˴����뷽��˵���� ���ܣ��õ���Ӧ������ ������ ���أ� ���⣺ ���ڣ�(2001-7-18 10:54:47)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.ui.ic.pub.corbillref.ICCorBillRef
   */
  public nc.ui.ic.pub.corbillref.ICCorBillRefPane getICCorBillRef() {
    if (m_aICCorBillRef == null) {
      m_aICCorBillRef = new nc.ui.ic.pub.corbillref.ICCorBillRefPane(this);
      m_aICCorBillRef.setReturnCode(true);
      m_aICCorBillRef.setMultiSelectedEnabled(true);
      //�޸��ˣ������� �޸�ʱ�䣺2008-6-2 ����02:15:48 �޸�ԭ�򣺶�Ӧ��ⵥ���ս���Ҳ�ý���С��λ�ȴ���
      if (null != m_aICCorBillRef.getRefUI() && m_aICCorBillRef.getRefUI() instanceof ICCorBillRefUI)
        setScaleOfCardPanel(((ICCorBillRefUI)m_aICCorBillRef.getRefUI()).getBillCardPanel());

    }
    return m_aICCorBillRef;
  }

  protected String[] getBodyFormula() {
    if (m_sItemField == null) {
      BillItem[] bodyItems = getBillCardPanel().getBillData()
          .getBodyItems();
      m_sItemField = BillUtil.getFormulas(bodyItems, IBillItem.LOAD);
    }
    return m_sItemField;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2001-7-11 ���� 11:19)
   * 
   * @return nc.ui.ic.pub.InvOnHandDialog
   */
  protected nc.ui.ic.pub.InvOnHandDialog getIohdDlg() {
    if (null == m_iohdDlg) {
      m_iohdDlg = new InvOnHandDialog(this);
    }
    return m_iohdDlg;
  }

  /**
   * ����û��ֹ��޸����κţ����⣬��ȷ����ʧЧ���ڼ���Ӧ���ݺţ�����ȷ����ա� �����ߣ����� ���ܣ� ������ ���أ� ���⣺
   * ���ڣ�(2001-6-14 10:25:33) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void getLotRefbyHand(String ColName) {
    int iSelrow = getBillCardPanel().getBillTable().getSelectedRow();
    String strColName = ColName;
    if (strColName == null) {
      return;
    }
    String sbatchcode = null;

    sbatchcode = (String) getBillCardPanel().getBodyValueAt(iSelrow,
        "vbatchcode");
    if (sbatchcode == null || sbatchcode.trim().length() <= 0
        || getLotNumbRefPane().isClicked()) {
      return;
    }

    getLotNumbRefPane().checkData();

  }

  /**
   * �Ƿ�̶�������
   */
  protected boolean isFixFlag(int row) {

    boolean isFixFlag = false;
    if (row >= 0 && getM_voBill().getItemVOs()[row] != null
        && getM_voBill().getItemVOs()[row].getIsSolidConvRate() != null)
      isFixFlag = getM_voBill().getItemVOs()[row].getIsSolidConvRate()
          .intValue() == 1 ? true : false;

    return isFixFlag;

  }

  /**
   * ���û�ѡ�����κź��Զ�������ʧЧ�������Ӧ����ţ��������͡� �����ߣ����� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-6-13
   * 17:38:31) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void pickupLotRef(String colname) {
    String s = colname;
    // ���κŲ��մ���ʧЧ���ںͶ�Ӧ���ݺż���Ӧ��������

    String sbatchcode = null;
    int iSelrow = getBillCardPanel().getBillTable().getSelectedRow();
    if (s == null) {
      return;
    }
    if (s.equals("vbatchcode")) {
      // �жϵ����κŲ���Ϊ��ʱ,Ӧ�÷���;
      // if(arytemp[0]==null||arytemp[3]==null){
      // return;
      // }

      sbatchcode = (String) getBillCardPanel().getBodyValueAt(iSelrow,
          "vbatchcode");

      if (sbatchcode != null && sbatchcode.trim().length() > 0) {
        nc.vo.scm.ic.bill.InvVO voInv = getM_voBill().getItemInv(
            iSelrow);
        // /����λ
        try {
          if (voInv.getIsAstUOMmgt().intValue() == 1) {
            nc.ui.pub.beans.UIRefPane refCastunit = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
                .getBodyItem("castunitname").getComponent());
            Object oldvalue = getBillCardPanel().getBodyValueAt(
                iSelrow, "castunitid");
            getM_voBill().setItemValue(
                iSelrow,
                "cselastunitid",
                getLotNumbRefPane().getLotNumbRefVO()
                    .getCastunitid());
            nc.vo.ic.pub.DesassemblyVO voDesa = (nc.vo.ic.pub.DesassemblyVO) getM_voBill()
                .getItemValue(iSelrow, "desainfo");
            if (voDesa.getMeasInfo() == null) {
              nc.vo.bd.b15.MeasureRateVO[] voMeasInfo = m_voInvMeas
                  .getMeasInfo(getEnvironment().getCorpID(),
                      voInv.getCinventoryid());
              voDesa.setMeasInfo(voMeasInfo);
            }
            getM_voBill().setItemValue(iSelrow, "idesatype",
                new Integer(voDesa.getDesaType()));
            getBillCardPanel().setBodyValueAt(
                new Integer(voDesa.getDesaType()), iSelrow,
                "idesatype");
            getBillCardPanel().setBodyValueAt(
                getLotNumbRefPane().getLotNumbRefVO()
                    .getCastunitid(), iSelrow,
                "cselastunitid");
            if (voDesa.getDesaType() == nc.vo.ic.pub.DesassemblyVO.TYPE_NO) {
              refCastunit.setPK(getLotNumbRefPane()
                  .getLotNumbDlg().getAssUnit());
              if (getLotNumbRefPane().getLotNumbDlg()
                  .getAssUnit() == null)
                refCastunit.setName(null);

              Object source = getBillCardPanel().getBodyItem(
                  "castunitname");

              Object newvalue = getLotNumbRefPane()
                  .getRefTableBodyID();
              nc.ui.pub.bill.BillEditEvent even = new nc.ui.pub.bill.BillEditEvent(
                  source, newvalue, oldvalue, "castunitname",
                  iSelrow, BillItem.BODY);
              getEditCtrl().afterAstUOMEdit(even);
            }
          }
        } catch (Exception e) {
        }
        try {
          // �����ε�ʧЧ����
          getBillCardPanel()
              .setBodyValueAt(
                  getLotNumbRefPane().getRefInvalideDate() == null ? ""
                      : getLotNumbRefPane()
                          .getRefInvalideDate()
                          .toString(), iSelrow,
                  "dvalidate");
        } catch (Exception e) {
        }
        if (getLotNumbRefPane().getRefBillCode() != null) {
          try {
            // ��Ӧ���ݺ�
            getBillCardPanel()
                .setBodyValueAt(
                    getLotNumbRefPane().getRefBillCode() == null ? ""
                        : getLotNumbRefPane()
                            .getRefBillCode(),
                    iSelrow, IItemKey.CORRESCODE);
          } catch (Exception e) {
          }
          try {
            // ��Ӧ��������
            getBillCardPanel()
                .setBodyValueAt(
                    getLotNumbRefPane().getRefBillType() == null ? ""
                        : getLotNumbRefPane()
                            .getRefBillType(),
                    iSelrow, "ccorrespondtype");
          } catch (Exception e) {
          }
          try {
            // ��Ӧ���ݱ�ͷOID
            // ����ģ����б���λ����������ʾ��ccorrespondhid,ccorrespondbid,�Ա�������Ķ�Ӧ��ͷ������OID
            getBillCardPanel()
                .setBodyValueAt(
                    getLotNumbRefPane()
                        .getRefTableHeaderID() == null ? ""
                        : getLotNumbRefPane()
                            .getRefTableHeaderID(),
                    iSelrow, "ccorrespondhid");
          } catch (Exception e) {
          }
          try {
            // ��Ӧ���ݱ���OID
            getBillCardPanel()
                .setBodyValueAt(
                    getLotNumbRefPane().getRefTableBodyID() == null ? ""
                        : getLotNumbRefPane()
                            .getRefTableBodyID(),
                    iSelrow, "ccorrespondbid");

          } catch (Exception e) {
          }
        }

        try {
          // //��������
          if (voInv.getIsValidateMgt().intValue() == 1) {
            nc.vo.pub.lang.UFDate dvalidate = getLotNumbRefPane()
                .getRefInvalideDate();
            if (dvalidate != null) {
              getBillCardPanel().setBodyValueAt(
                  dvalidate.getDateBefore(
                      voInv.getQualityDay().intValue())
                      .toString(), iSelrow, "scrq");
            }

          }
        } catch (Exception e) {
        }

        // /������
        try {
          if (voInv.getIsFreeItemMgt().intValue() == 1) {
            FreeVO freevo = getLotNumbRefPane().getLotNumbDlg()
                .getFreeVO();
            if (freevo != null && freevo.getVfree0() != null) {
              InvVO invvo = getM_voBill().getItemInv(iSelrow);
              if (invvo != null)
                invvo.setFreeItemVO(freevo);
              getFreeItemRefPane().setFreeItemParam(invvo);
              getBillCardPanel().setBodyValueAt(
                  freevo.getVfree0(), iSelrow, "vfree0");
              for (int i = 1; i <= FreeVO.FREE_ITEM_NUM; i++) {
                if (getBillCardPanel().getBodyItem("vfree" + i) != null)

                  getBillCardPanel().setBodyValueAt(
                      freevo.getAttributeValue("vfree"
                          + i), iSelrow, "vfree" + i);
                else
                  getBillCardPanel().setBodyValueAt(null,
                      iSelrow, "vfree" + i);
              }
            }
            getM_voBill().setItemFreeVO(iSelrow, freevo);
          }
        } catch (Exception e) {

        }

        // ͬ���ı�m_voBill
        getM_voBill().setItemValue(iSelrow, "vbatchcode",
            getLotNumbRefPane().getRefLotNumb());
        getM_voBill().setItemValue(iSelrow, "dvalidate",
            getLotNumbRefPane().getRefInvalideDate());
        if (getLotNumbRefPane().getRefBillCode() != null) {
          getM_voBill().setItemValue(iSelrow, "ccorrespondbid",
              getLotNumbRefPane().getRefTableBodyID());
          getM_voBill().setItemValue(iSelrow, "ccorrespondhid",
              getLotNumbRefPane().getRefTableHeaderID());
          getM_voBill().setItemValue(iSelrow, IItemKey.CORRESCODE,
              getLotNumbRefPane().getRefBillCode());
          getM_voBill().setItemValue(iSelrow, "ccorrespondtype",
              getLotNumbRefPane().getRefBillType());
        }

      } else {
        // getBillCardPanel().setBodyValueAt("", iSelrow, "dvalidate");
        // //��ձ�������ʧЧ����
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // IItemKey.CORRESCODE);
        // //��ձ������ж�Ӧ���ݺ�
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // "ccorrespondtype");
        // //��ձ������ж�Ӧ��������
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // "ccorrespondhid");
        // //��ձ������ж�Ӧ���ݱ�ͷOID
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // "ccorrespondbid");
        // //��ձ������ж�Ӧ���ݱ���OID
        // getBillCardPanel().setBodyValueAt("", iSelrow, "scrq");
        // //��ձ�������ʧЧ����
        // //ͬ���ı�m_voBill
        // m_voBill.setItemValue(iSelrow, "vbatchcode", null);
        // m_voBill.setItemValue(iSelrow, "dvalidate", null);
        // m_voBill.setItemValue(iSelrow, "ccorrespondbid", null);
        // m_voBill.setItemValue(iSelrow, "ccorrespondhid", null);
        // m_voBill.setItemValue(iSelrow, IItemKey.CORRESCODE, null);
        // m_voBill.setItemValue(iSelrow, "ccorrespondtype", null);

      }
    }
  }

  /**
   * �����ߣ����˾� ���ܣ����ݱ༭���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  abstract protected void afterBillEdit(nc.ui.pub.bill.BillEditEvent e);

  /**
   * �����ߣ����˾� ���ܣ���������ѡ��ı� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  abstract protected void afterBillItemSelChg(int iRow, int iCol);

  /**
   * �����ߣ����˾� ���ܣ������֯�ı��¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void afterCalbodyEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterCalbodyEdit(e);

  }

  /**
   * �����ߣ����Ӣ ���ܣ���λ�޸��¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void afterSpaceEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterSpaceEdit(e);

  }

  /**
   * �����ߣ����˾� ���ܣ����ݱ���༭�¼�ǰ�������� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  abstract public boolean beforeBillItemEdit(nc.ui.pub.bill.BillEditEvent e);

  /**
   * �����ߣ����˾� ���ܣ���������ѡ��ı� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  abstract protected void beforeBillItemSelChg(int iRow, int iCol);
  
  /**
   * 
   * ��ô�����յĹ����ַ���
   * <p>
   * @return ������չ����ַ���
   * <p>
   * @author duy
   * @time 2008-7-17 ����03:02:11
   */
  public StringBuilder getFilterInvString() {
    nc.ui.pub.bill.BillItem biCol = getBillCardPanel().getBodyItem(
        IItemKey.INVCODE);
    nc.ui.pub.bill.BillItem biWh = getBillCardPanel().getHeadItem(
        IItemKey.WAREHOUSE);
    nc.ui.pub.bill.BillItem biBody = getBillCardPanel().getHeadItem(
        IItemKey.CALBODY);
    StringBuilder swhere = new StringBuilder();
    if (m_isWhInvRef && biCol != null && biWh != null) {
      String cwhid = (String) biWh.getValueObject();
      if (cwhid != null) {
        swhere
            .append(" pk_invmandoc in (select cinventoryid from ic_numctl where cwarehouseid='"
                + cwhid + "' )");
      }
    }
    else if (biCol != null && biBody != null) {
      String ccalbodyid = (String) biBody.getValueObject();
      if (ccalbodyid != null) {
        swhere
            .append(" pk_invmandoc in (select pk_invmandoc from bd_produce where pk_calbody='"
                + ccalbodyid + "' and isused='Y')");
      }
    }
    if (getM_voBill() != null && getM_voBill().getWh() != null) {
      if (getM_voBill().getWh().getIsCapitalStor().booleanValue())
        swhere.append(" and bd_invbasdoc.asset = 'Y' ");
    }
    return swhere;
  }
  
  /**
   * 
   * ��òֿ���յĹ�������
   * <b>����˵��</b>
   * @param pk_calbody �����֯��PK
   * @return �ֿ��������������
   * <p>
   * @author duy
   * @time 2008-8-19 ����04:48:46
   */
  public String[] getFilterWhString(String pk_calbody) {
    // ����ⵥ�Ĳֿ�����У����˵�ֱ�˲֣�����ϵͳ�����ģ�ֱ�˲ֲ���ʾҲû�����⡣
    //�޸��ˣ������� �޸�ʱ�䣺2008-8-15 ����11:05:57 �޸�ԭ��ֱ��ҵ�����͵ĵ��ݲֿ�ֻ��ʾֱ�˲֡�
    if (null != getBillType() 
        && (ScmConst.m_purchaseIn.equals(getBillType()) || ScmConst.m_saleOut.equals(getBillType()) || ScmConst.m_allocationIn.equals(getBillType()))
        && null != getM_voBill() && null != getM_voBill().getHeaderVO().getBdirecttranflag()
        && getM_voBill().getHeaderVO().getBdirecttranflag().booleanValue()) {
      /*if (pk_calbody == null || pk_calbody.length() == 0)
        return new String[] { "and isdirectstore = 'Y'" };
      else
        return new String[] { "and isdirectstore = 'Y'", "AND pk_calbody='" + pk_calbody + "'" };*/
      if (pk_calbody == null || pk_calbody.length() == 0)
        return null ;
      else
        return new String[] { "AND pk_calbody='" + pk_calbody + "'" };
    }
    else {
      if (null != getBillType() && "45".equals(getBillType())){
        if (pk_calbody == null || pk_calbody.length() == 0)
          return null;
        else
          return new String[] { "AND pk_calbody='" + pk_calbody + "'" };
      }else{
        if (pk_calbody == null || pk_calbody.length() == 0)
          return new String[] { "and isdirectstore = 'N'" };
        else
          return new String[] { "and isdirectstore = 'N'", "AND pk_calbody='" + pk_calbody + "'" };
      }
    }
  }

  /**
   * UAP�ṩ�ı༭ǰ����
   * 
   * @param value
   * @param row
   * @param itemkey
   * @return
   * 
   */
  public boolean isCellEditable(
      boolean value/* BillModel��isCellEditable�ķ���ֵ */,
      int row/* ��������� */, String itemkey/* ��ǰ�е�itemkey */) {

    return getEditCtrl().isCellEditable(value, row, itemkey);

  }

  /**
   * beforeEdit ����ע�⡣[�����ͷ�༭ǰ�¼�]
   */
  public boolean beforeEdit(nc.ui.pub.bill.BillItemEvent e) {
    if(!getPluginProxy().beforeEdit(e))
      return false;
    
    return getEditCtrl().beforeEdit(e);

  }

  /**
   * �����ߣ����˾� ���ܣ����ݱ༭�¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public boolean beforeEdit(nc.ui.pub.bill.BillEditEvent e) {
    boolean bret = getEditCtrl().beforeEdit(e);
    if(isLineCardEdit()){
      bret = isCellEditable(bret, e.getRow(), e.getKey());
    }
    
    //���ο�����չ
    if(!getPluginProxy().beforeEdit(e))
      bret = false;
    
    return bret;

  }

  /**
   * ����ϼơ� �������ڣ�(2001-10-24 16:33:58)
   * 
   * @return nc.vo.pub.lang.UFDouble
   * @param sItemKey
   *            java.lang.String
   */
  public nc.vo.pub.lang.UFDouble calcurateTotal(java.lang.String sItemKey) {
    UFDouble dTotal = new UFDouble(0.0);

    for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
      //�޸��ˣ������� �޸�ʱ�䣺2009-2-16 ����05:00:35 �޸�ԭ����Ʒ��������ϼơ�
          UFBoolean blargessflag = SmartVODataUtils.getUFBoolean(getBillCardPanel().getBodyValueAt(
                  i, "flargess"));
              if (("nmny".equals(sItemKey) || "nprice".equals(sItemKey))&&(blargessflag != null && blargessflag.booleanValue()))
                continue;
              
      Object oValue = getBillCardPanel().getBodyValueAt(i, sItemKey);
      String sValue = (oValue == null || oValue.equals("")) ? "0"
          : oValue.toString();
      dTotal = dTotal.add(new UFDouble(sValue));
    }

    return dTotal;
  }

  /**
   * ���ݻ����ʼ���ë�غ����ء� �������ڣ�(2005-04-05 16:33:58)
   * 
   * @return void
   * @param hsl
   *            UFDouble
   */
  public void calOtherNumByHsl(String sMainNum, String sAstNum, int rownum,
      UFDouble hsl) {
    if (hsl == null)
      return;

    // ������Ƥ��ë��
    UFDouble nMain = null;
    UFDouble nAst = null;

    Object oMain = getBillCardPanel().getBodyValueAt(rownum, sMainNum);
    Object oAst = getBillCardPanel().getBodyValueAt(rownum, sAstNum);

    if (oMain != null)
      nMain = new UFDouble(oMain.toString().trim());
    if (oAst != null)
      nAst = new UFDouble(oAst.toString().trim());

    /* �������������ʣ����۹̶����䶯�����ʣ����������������������¼��������� */
    if (m_bAstCalMain) {
      if (nAst != null) {
        nMain = nAst.multiply(hsl);
        getBillCardPanel().setBodyValueAt(nMain, rownum, sMainNum);
        getM_voBill().setItemValue(rownum, sMainNum, nMain);

      } else {
        getBillCardPanel().setBodyValueAt(null, rownum, sMainNum);
        getM_voBill().setItemValue(rownum, sMainNum, null);
      }
    } else {
      if (nMain != null) {
        nAst = nMain.div(hsl);
        getBillCardPanel().setBodyValueAt(nAst, rownum, sAstNum);
        getM_voBill().setItemValue(rownum, sAstNum, nAst);
      } else {
        getBillCardPanel().setBodyValueAt(null, rownum, sAstNum);
        getM_voBill().setItemValue(rownum, sAstNum, null);
      }
    }

  }

  /**
   * �����ߣ����˾� ���ܣ����󷽷�������ǰ��VO��� �����������浥�� ���أ� ���⣺ ���ڣ�(2001-5-24 ���� 5:17)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  abstract protected boolean checkVO(GeneralBillVO voBill);

  /**
   * �����ߣ����˾� ���ܣ�����б�ͱ����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void clearUi() {
    try {
      // clear card panel()
      GeneralBillVO voNullBill = new GeneralBillVO();
      voNullBill.setParentVO(new GeneralBillHeaderVO());
      voNullBill
          .setChildrenVO(new GeneralBillItemVO[] { new GeneralBillItemVO() });
      getBillCardPanel().setBillValueVO(voNullBill);
      getBillCardPanel().getBillModel().clearBodyData();
      // clear list panel()
      getBillListPanel().getHeadBillModel().clearBodyData();
      getBillListPanel().getBodyBillModel().clearBodyData();

    } catch (Exception e) {

    }

  }

  /**
   * ��Դ������ת�ⵥʱ�Ľ�����Ʒ��� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-19 09:43:22)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void ctrlSourceBillButtons() {
    ctrlSourceBillButtons(true);
  }

  /**
   * ��Դ������ת�ⵥʱ�Ľ�����Ʒ��� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-19 09:43:22)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void ctrlSourceBillUI() {
    ctrlSourceBillUI(true);
  }

  /**
   * �˴����뷽��˵���� ���ߣ����Ӣ �������ڣ�(2001-6-21 15:11:22)
   * 
   * @param int
   *            bill
   */
  protected void dispSpace(int bill) {
    // ��ѯ��ǰ���ı����λ
    // ydy
    if (getM_alListData() == null)
      return;

    if (getBillCardPanel().getBodyItem("vspacename") == null
        || !(getBillCardPanel().getBodyItem("vspacename").isShow())) {
      if (getBillListPanel().getBodyTable().getRowCount() > 0)
//        deleted by lirr 2009-04-16 ����ѡ��2���ٴӵ�һ�ж�ѡ��ֻ��ѡ�е�һ������
        //getBillListPanel().getBodyTable().setRowSelectionInterval(0, 0);
      return;
    }

    GeneralBillVO voBill = (GeneralBillVO) getM_alListData().get(bill);
    appendLocator(voBill);

    setListBodyData(voBill.getItemVOs());

    // ѡ�б����һ��
    // ���岻����Ϊ��
//    deleted by lirr 2009-04-16 ����ѡ��2���ٴӵ�һ�ж�ѡ��ֻ��ѡ�е�һ������
    /*if (getBillListPanel().getBodyTable().getRowCount() > 0)
      getBillListPanel().getBodyTable().setRowSelectionInterval(0, 0);*/
    // end ydy

  }

  protected void appendLocator(GeneralBillVO voBill) {
    GeneralBillItemVO[] voItems = (GeneralBillItemVO[]) voBill
        .getChildrenVO();
    boolean isQueried = false;

    if (voItems != null) {
      SCMEnv.out("body rows=" + voItems.length);
      for (int i = 0; i < voItems.length; i++) {
        LocatorVO[] lvos = ((LocatorVO[]) voItems[i].getLocator());
        if (lvos == null && !isQueried) {
          qryLocSN(voBill, QryInfoConst.LOC);
          lvos = ((LocatorVO[]) voItems[i].getLocator());
          isQueried = true;
        }
        if (lvos != null && lvos.length == 1) {
          voItems[i].setCspaceid(lvos[0].getCspaceid());
          voItems[i].setVspacename(lvos[0].getVspacename());
        } else {
          voItems[i].setCspaceid(null);
          voItems[i].setVspacename(null);
        }
      }
    }
  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-12-5 16:27:59) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param row
   *            int
   * @param formulas
   *            java.lang.String[]
   */
  protected void execEditFomulas(int row, String itemKey) {
    /** ǿ��ִ�б����У������еĹ�ʽ */
    nc.ui.pub.bill.BillItem bi = getBillCardPanel().getBodyItem(itemKey);
    if (bi != null) {
      String[] formulas = bi.getEditFormulas();
      getBillCardPanel().execBodyFormulas(row, formulas);
    }
  }

  /**
   * ����˵����
   * 
   * ִ�б�ͷ����β�Ĺ�ʽ�ǡ�
   * 
   * �������ڣ�(2002-11-6 13:23:02) ���ߣ� �޸����ڣ� �޸��ˣ� �޸�ԭ�� �㷨˵����
   */
  public void execHeadTailFormulas() {

    getBillCardPanel().execHeadTailLoadFormulas();

  }

  /**
   * �����ߣ����˾� ���ܣ��˵�����Ҫ�������ֶΣ�Ȼ�󷵻�֮ ������ ���أ� ���⣺ ���ڣ�(2001-8-17 13:13:51)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public void filterCondVO2(nc.vo.pub.query.ConditionVO[] voaCond,
      String[] saItemKey) {
    if (voaCond == null || saItemKey == null)
      return;
    // �ݴ���
    int j = 0;
    // ���鳤
    int len = saItemKey.length;
    for (int i = 0; i < voaCond.length; i++)
      // ��
      if (voaCond[i] != null)
        for (j = 0; j < len; j++) {
          if (saItemKey[j] != null
              && voaCond[i].getFieldCode() != null
              && saItemKey[j].trim().equals(
                  voaCond[i].getFieldCode().trim())) {
            // ���϶������ı���
            voaCond[i].setFieldCode("1");
            voaCond[i].setOperaCode("=");
            voaCond[i].setDataType(1);
            voaCond[i].setValue("1");
          }
        }
  }

  /**
   * ���˵��ݲ��� �����ߣ����� ���ܣ����˳ɱ����� ������ ���أ� ���⣺ ���ڣ�(2001-7-17 10:33:20)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void filterCostObject() {
    try {
      // ���ݿ����֯����
      String sCalbodyID = null;
      if (getBillCardPanel().getHeadItem("pk_calbody") != null
          && getBillCardPanel().getBodyItem(IItemKey.COSTOBJECTNAME) != null) {
        sCalbodyID = (String) getBillCardPanel().getHeadItem(
            "pk_calbody").getValueObject();
        UIRefPane ref = (nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem(IItemKey.COSTOBJECTNAME).getComponent();
        // �޸��ˣ������� �޸����ڣ�2008-1-25����11:26:58 �޸�ԭ�򣺲��ϳ��ⵥ�ɱ��������Ч������
        String swhere = " bd_produce.pk_calbody='"
            + sCalbodyID
            + "' "
            + " and bd_invmandoc.pk_corp= '"
            + getEnvironment().getCorpID()
            + "' and bd_produce.pk_invbasdoc = bd_invbasdoc.pk_invbasdoc "
            + " and bd_produce.pk_invmandoc = bd_invmandoc.pk_invmandoc and bd_produce.sfcbdx = 'Y' ";
        if (ref.getRefModel().getWherePart() == null
            || (ref.getRefModel().getWherePart() != null && !swhere
                .trim()
                .equals(ref.getRefModel().getWherePart().trim()))) {

          ref.getRefModel().setWherePart(swhere);
          // �޸��ˣ������� �޸����ڣ�2008-1-25����11:26:58
          // �޸�ԭ�򣺲��ϳ��ⵥ�ɱ��������Ч�����⣬��Ϊ�򿪲���ʱ����������Ѿ���ֵ�Ļ���ƽ̨�����ƥ�䣨���Σ���Ĭ����ֻ��PK����WherePart������ҵ�����¼ӵı�ʱ���ᵼ��SQLЧ���ر�ף�����Ҫ����WherePartʹ�õ�PK��
          ref.getRefModel().setMatchPkWithWherePart(true);
        }
      }
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }

  /**
   * ����ǹ̶���λ�Ĵ�������˳�����Ĺ̶���λ ���ߣ����Ӣ �������ڣ�(2001-7-6 16:53:38)
   */
  protected void filterSpace(int row) {
    if (getM_voBill() == null)
      return;
    nc.vo.scm.ic.bill.WhVO voWh = getM_voBill().getWh();
    if (voWh == null || voWh.getIsLocatorMgt() == null
        || voWh.getIsLocatorMgt().intValue() == 0) {
      getBillCardPanel().getBillData().getBodyItem("vspacename")
          .setEnabled(false);
      return;
    }
    getBillCardPanel().getBillData().getBodyItem("vspacename").setEnabled(
        true);

    String sName = (String) getBillCardPanel().getBodyValueAt(row,
        "vspacename");
    String spk = (String) getBillCardPanel()
        .getBodyValueAt(row, "cspaceid");

    if (GenMethod.isNull(spk)) {
      getBillCardPanel().setBodyValueAt(null, row, "cspaceid");

    }

    getLocatorRefPane().setOldValue(sName, null, spk);

    nc.vo.scm.ic.bill.InvVO invvo = getM_voBill().getItemInv(row);
    String selastunitid = (String) getM_voBill().getItemValue(row,
        "cselastunitid");
    invvo.setCselastunitid(selastunitid);

    getLocatorRefPane().setParam(voWh, invvo);
    
    return;

  }

  /**
   * �����ߣ����˾� ���ܣ�ǩ�ֳɹ����� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * 
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void freshAfterSignedOK(String sBillStatus) {
    try {
      GeneralBillVO voBill = null;
      // ˢ���б���ʽ
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
        // ���ﲻ��clone(),�ı���m_voBillͬʱ�ı�m_alListData???
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());

        // �ѵ�ǰ��¼�����õ�vo
        if (voBill != null && voBill.getHeaderVO() != null) {
          GeneralBillHeaderVO voHeader = voBill.getHeaderVO();
          voHeader.setCregister(getEnvironment().getUserID());
          voHeader.setCregistername(getEnvironment().getUserName());
          voHeader.setDaccountdate(new nc.vo.pub.lang.UFDate(
              getEnvironment().getLogDate()));

          if (sBillStatus != null
              && sBillStatus.equals(BillStatus.AUDITED)) {
            voHeader.setCauditorid(getEnvironment().getUserID());
            voHeader
                .setCauditorname(getEnvironment().getUserName());
            voHeader.setDauditdate(new nc.vo.pub.lang.UFDate(
                getEnvironment().getLogDate()));
          }

          voHeader.setFbillflag(new Integer(sBillStatus));
          voBill.setParentVO(voHeader);
        }
        // �����б����
        getM_alListData().set(getM_iLastSelListHeadRow(), voBill);

        // ˢ���б���ʽ
        getBillListPanel().getHeadBillModel().setBodyRowVO(
            voBill.getParentVO(), getM_iLastSelListHeadRow());
      }
      // m_voBillˢ��
      // �ѵ�ǰ��¼�����õ�vo
      if (getM_voBill() != null && getM_voBill().getHeaderVO() != null) {
        GeneralBillHeaderVO voHeader = getM_voBill().getHeaderVO();
        voHeader.setCregister(getEnvironment().getUserID());
        voHeader.setCregistername(getEnvironment().getUserName());
        voHeader.setDaccountdate(new nc.vo.pub.lang.UFDate(
            getEnvironment().getLogDate()));

        if (sBillStatus != null
            && sBillStatus.equals(BillStatus.AUDITED)) {
          voHeader.setCauditorid(getEnvironment().getUserID());
          voHeader.setCauditorname(getEnvironment().getUserName());
          voHeader.setDauditdate(new nc.vo.pub.lang.UFDate(
              getEnvironment().getLogDate()));
        }
        voHeader.setFbillflag(new Integer(sBillStatus));
        getM_voBill().setParentVO(voHeader);
      }

      // �ѵ�ǰ��¼��������ʾ������
      if (getBillCardPanel().getTailItem("cregister") != null)
        getBillCardPanel().getTailItem("cregister").setValue(
            getEnvironment().getUserID());
      if (getBillCardPanel().getTailItem("cregistername") != null)
        getBillCardPanel().getTailItem("cregistername").setValue(
            getEnvironment().getUserName());
      if (getBillCardPanel().getTailItem("daccountdate") != null)
        getBillCardPanel().getTailItem("daccountdate").setValue(
            getEnvironment().getLogDate());
      if (getBillCardPanel().getTailItem("taccounttime") != null)
        getBillCardPanel().getTailItem("taccounttime").setValue(
            getM_voBill().getHeaderValue("taccounttime"));

      if (sBillStatus != null && sBillStatus.equals(BillStatus.AUDITED)) {
        if (getBillCardPanel().getTailItem("cauditorid") != null)
          getBillCardPanel().getTailItem("cauditorid").setValue(
              getEnvironment().getUserID());
        if (getBillCardPanel().getTailItem("cauditorname") != null)
          getBillCardPanel().getTailItem("cauditorname").setValue(
              getEnvironment().getUserName());
        if (getBillCardPanel().getTailItem("dauditdate") != null)
          getBillCardPanel().getTailItem("dauditdate").setValue(
              getEnvironment().getLogDate());
      }
      if (getBillCardPanel().getHeadItem("fbillflag") != null)
        getBillCardPanel().getHeadItem("fbillflag").setValue(
            sBillStatus);
      // ˢ���б���ʽ
      // setListHeadData();
      // selectListBill(getM_iLastSelListHeadRow());
      // ���ð�ť״̬,ǩ�ֲ����ã�ȡ��ǩ�ֿ���
      // ��ǩ�֣��������ð�ť״̬,ǩ�ֲ����ã�ȡ��ǩ�ֿ���
      setButtonStatus(false);

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(true);
      // ����ɾ����
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);

      updateButtons();

    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
    }

  }

  /**
   * �����ߣ����˾� ���ܣ�ˢ�¼ƻ��� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void freshPlanprice(ArrayList alNewPlanprice) {
    try {
      // ����
      int iRowCount = getBillCardPanel().getRowCount();
      if (alNewPlanprice == null || alNewPlanprice.size() < iRowCount
          || getM_voBill() == null) {
        SCMEnv.out("alallinv nvl");
        return;
      }
      // ����ȡ�������Ƿ�Ϊ��
      nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
      // ���������ڼ�����
      Object oTempNum = null;
      UFDouble dNum = null;
      UFDouble dMny = null;
      GeneralBillItemVO[] voaBillItem = getM_voBill().getItemVOs();
      for (int row = 0; row < iRowCount; row++) {
        bmBill.setValueAt(alNewPlanprice.get(row), row,
            IItemKey.NPLANNEDPRICE);
        // ��Ҫͬ��m_voBill
        if (alNewPlanprice.get(row) != null)
          voaBillItem[row].setNplannedprice((UFDouble) alNewPlanprice
              .get(row));
        else
          voaBillItem[row].setNplannedprice(null);
        oTempNum = bmBill.getValueAt(row, getEnvironment()
            .getNumItemKey());
        // ͬʱ�������͵���ʱ���ż���
        if (oTempNum != null && alNewPlanprice.get(row) != null) {
          dNum = (UFDouble) oTempNum;
          dMny = dNum.multiply((UFDouble) alNewPlanprice.get(row));
        } else
          dMny = null;

        bmBill.setValueAt(dMny, row, IItemKey.NPLANNEDMNY);
        // ��Ҫͬ��m_voBill
        voaBillItem[row].setNplannedmny(dMny);

      }

    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
    }
  }

  /**
   * �����ߣ����˾� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:05:45) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return java.lang.String
   */
  public java.lang.String getBillTypeCode() {
    return getBillType();
  }

  /**
   * �����ߣ����˾� ���ܣ��õ���ѯ�Ի��� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
//  protected QueryConditionDlgForBill getConditionDlg() {
//    if (ivjQueryConditionDlg == null) {
//      ivjQueryConditionDlg = new QueryConditionDlgForBill(this);
//      ivjQueryConditionDlg.setTempletID(getEnvironment().getCorpID(),
//          getFunctionNode(), getEnvironment().getUserID(), null);
//
//      // ����Ϊ�Թ�˾���յĳ�ʼ��
//      ArrayList alCorpIDs = new ArrayList();
//      alCorpIDs.add(getEnvironment().getCorpID());
//      ivjQueryConditionDlg.initCorpRef("head.pk_corp", getEnvironment()
//          .getCorpID(), alCorpIDs);
//      // ����Ϊ�Բ��յĳ�ʼ��
//      ivjQueryConditionDlg.initQueryDlgRef();
//
//      // ���س�������
//      ivjQueryConditionDlg.hideNormal();
//      // �����Ƿ�رղ�ѯ����body.bbarcodeclose
//      ivjQueryConditionDlg.setCombox("body.bbarcodeclose",
//          new String[][] {
//              { " ", " " },
//              {
//                  "N",
//                  nc.ui.ml.NCLangRes.getInstance()
//                      .getStrByID("SCMCOMMON",
//                          "UPPSCMCommon-000108") /*
//                                       * @res
//                                       * "��"
//                                       */},
//              {
//                  "Y",
//                  nc.ui.ml.NCLangRes.getInstance()
//                      .getStrByID("SCMCOMMON",
//                          "UPPSCMCommon-000244") /*
//                                       * @res
//                                       * "��"
//                                       */} });
//
//      // ������������ʾ
//      ivjQueryConditionDlg.setCombox("qbillstatus", new String[][] {
//          {
//              "2",
//              nc.ui.ml.NCLangRes.getInstance().getStrByID(
//                  "4008bill", "UPP4008bill-000313") /*
//                                     * @res
//                                     * "�Ƶ�"
//                                     */},
//          {
//              "3",
//              nc.ui.ml.NCLangRes.getInstance().getStrByID(
//                  "40080402", "UPT40080402-000013") /*
//                                     * @res
//                                     * "ǩ��"
//                                     */},
//          {
//              "A",
//              nc.ui.ml.NCLangRes.getInstance().getStrByID(
//                  "SCMCOMMON", "UPPSCMCommon-000217") /*
//                                     * @res
//                                     * "ȫ��"
//                                     */} });
//      // set default logon date into query condiotn dlg
//      // modified by liuzy 2008-03-28 5.03���󣬵��ݲ�ѯ������ֹ����
//      ivjQueryConditionDlg.setInitDate("head.dbilldate.from",
//          getEnvironment().getLogDate());
//      ivjQueryConditionDlg.setInitDate("head.dbilldate.end",
//          getEnvironment().getLogDate());
//      ivjQueryConditionDlg.setInitDate("dbilldate.from", getEnvironment()
//          .getLogDate());
//      ivjQueryConditionDlg.setInitDate("dbilldate.end", getEnvironment()
//          .getLogDate());
//      ivjQueryConditionDlg.setInOutFlag(getInOutFlag());
//
//      // ��ѯ�Ի�����ʾ��ӡ����ҳǩ��
//      ivjQueryConditionDlg.setShowPrintStatusPanel(true);
//
//      // �޸��Զ�����Ŀ add by hanwei 2003-12-09
//      DefSetTool.updateQueryConditionClientUserDef(ivjQueryConditionDlg,
//          getEnvironment().getCorpID(), ICConst.BILLTYPE_IC,
//          "head.vuserdef", "body.vuserdef");
//      getConDlginitself(ivjQueryConditionDlg);
//
//      // ���˿����֯���ֿ�,��Ʒ��,�ͻ�,��Ӧ�̵�����Ȩ�ޣ����ţ�ҵ��Ա
//      // zhy2005-06-10 �ͻ��͹�Ӧ�̲���Ҫ����ͨ���Ϲ��ˣ����ͻ������۳��ⵥ�Ϲ��ˣ���Ӧ���ڲɹ���ⵥ�Ϲ��ˣ�
//      // zhy2007-02-12 V51������:3��
//      // ���̡��������ࡢ�����֯����Ŀ������Ȩ�޿��ƣ����š��ֿ⡢������ࡢ������Ѷ���Ŀ��Աƥ���¼�Ŀ��ƣ�
//      /**
//       * ���Ա:head.cwhsmanagerid ����:head.cproviderid �����֯:head.pk_calbody
//       * �ֿ�:head.cwarehouseid,head.cwastewarehouseid ��Ŀ:body.cprojectid
//       * ����:head.cdptid �������:invcl.invclasscode ���:inv.invcode
//       */
//      // ivjQueryConditionDlg.setCorpRefs("head.pk_corp", new String[] {
//      // "head.cproviderid","head.pk_calbody", "head.cwarehouseid",
//      // "head.cwastewarehouseid","body.cprojectid"
//      // //, "head.cdptid", "head.cbizid"
//      // });
//      // ivjQueryConditionDlg.setDataPower(true,
//      // getEnvironment().getCorpID());
//      if (BillTypeConst.m_allocationIn.equals(getBillTypeCode())
//          || BillTypeConst.m_allocationOut.equals(getBillTypeCode()))
//        ivjQueryConditionDlg.setCorpRefs("head.pk_corp",
//            nc.ui.ic.pub.tools.GenMethod
//                .getDataPowerFieldFromDlg(ivjQueryConditionDlg,
//                    false, new String[] {
//                        "head.cothercorpid",
//                        "head.coutcorpid",
//                        "body.creceieveid",
//                        "head.cothercalbodyid",
//                        "head.cotherwhid",
//                        "head.coutcalbodyid" }));
//      else
//        ivjQueryConditionDlg
//            .setCorpRefs(
//                "head.pk_corp",
//                nc.ui.ic.pub.tools.GenMethod
//                    .getDataPowerFieldFromDlgNotByProp(ivjQueryConditionDlg));
//
//      // zhy205-05-19 �ӿɻ�����������
//      // �����
//      ivjQueryConditionDlg
//          .setCombox(
//              "coalesce(body.noutnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
//              new Integer[][] { { new Integer(0), new Integer(0) } });
//      // ���뵥
//      ivjQueryConditionDlg
//          .setCombox(
//              "coalesce(body.ninnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
//              new Integer[][] { { new Integer(0), new Integer(0) } });
//
//    }
//    return ivjQueryConditionDlg;
//  }

  /**
   * �����ߣ����Ӣ ���ܣ��õ���ǰ��¼��Ĵ��ID ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  public ArrayList getCurInvID() {
    // ���ID
    ArrayList alAllInv = new ArrayList();
    // ����
    int iRowCount = getBillCardPanel().getRowCount();
    // ����ȡ�������Ƿ�Ϊ��
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    for (int row = 0; row < iRowCount; row++)
      alAllInv.add(bmBill.getValueAt(row, IItemKey.INVID));
    return alAllInv;
  }

  /**
   * �����ߣ����Ӣ ���ܣ��õ���ǰ��¼��Ĵ��ID ������ //���ID ���أ��Ƿ��д�� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  public boolean getCurInvID(ArrayList alAllInv) {
    if (alAllInv == null) {
      SCMEnv.out("alallinv nvl");
      return false;
    }
    boolean bHaveInv = false;
    // ����
    int iRowCount = getBillCardPanel().getRowCount();
    // ����ȡ�������Ƿ�Ϊ��
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    Object oTempInvID = null;
    for (int row = 0; row < iRowCount; row++) {
      oTempInvID = bmBill.getValueAt(row, IItemKey.INVID);
      alAllInv.add(oTempInvID);
      if (bHaveInv == false && oTempInvID != null
          && oTempInvID.toString().trim().length() > 0)
        bHaveInv = true;
    }
    return bHaveInv;
  }

  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-30 15:06:35) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected PrintDataInterface getDataSource() {
    if (null == m_dataSource) {
      m_dataSource = new PrintDataInterface();
      BillData bd = getBillCardPanel().getBillData();
      m_dataSource.setBillData(bd);
      m_dataSource.setModuleName(getFunctionNode());
      m_dataSource.setTotalLinesInOnePage(getPrintEntry().getBreakPos());
      m_dataSource.setFormulaJudge(new DefaultFormulaJudge(getFunctionNode(), getEnvironment().getCorpID()));
    }
    return m_dataSource;
  }

  /**
   * �����ߣ����˾� ���ܣ������������������ ������ ���أ� ���⣺ ���ڣ�(2001-11-24 12:15:42)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public nc.ui.ic.pub.device.DevInputCtrl getDevInputCtrl() throws Exception {

    if (m_dictrl == null) {
      m_dictrl = new nc.ui.ic.pub.device.DevInputCtrl();
      m_dictrl.setPk_corp(getEnvironment().getCorpID());
      m_dictrl.setBillTypeCode(getBillType());
      m_dictrl.setCard(getBillCardPanel());
      m_dictrl.setTp(this);
    }
    m_dictrl.setup();
    return m_dictrl;
  }

//  /**
//   * �����ߣ����˾� ���ܣ��õ��û�����Ķ����ѯ���� ������//��ѯ�������� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
//   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
//   * 
//   * 
//   * 
//   * 
//   */
//  public String getExtendQryCond(nc.vo.pub.query.ConditionVO[] voaCond) {
//    // ����״̬����,ȱʡ��
//    String sBillStatusSql = " (1=1) ";
//    try {
//      // -------- ��ѯ�����ֶ� itemkey ---------
//      String sFieldCode = null;
//      // �������в��������С����
//      // ����״̬
//      String sBillStatus = "A";
//      String sFreplenishflag = null;
//      if (voaCond != null) {
//        for (int i = 0; i < voaCond.length; i++) {
//          if (voaCond[i] != null && voaCond[i].getFieldCode() != null) {
//            sFieldCode = voaCond[i].getFieldCode().trim();
//            if ("qbillstatus".equals(voaCond[i].getFieldCode()
//                .trim())) {
//              if (voaCond[i].getValue() != null
//                  && voaCond[i].getRefResult() != null)
//                sBillStatus = voaCond[i].getRefResult()
//                    .getRefPK();
//            } else if ("boutnumnull".equals(sFieldCode)) {
//
//              voaCond[i].setFieldCode("body.noutnum");
//              voaCond[i].setOperaCode(" is ");
//
//              voaCond[i].setDataType(ConditionVO.INTEGER);
//
//              if (voaCond[i].getValue() != null
//                  && "Y".equals(voaCond[i].getValue())) {
//
//                voaCond[i].setValue(" not null ");
//                m_sBnoutnumnull = "Y";
//              } else {
//
//                voaCond[i].setValue("  null ");
//                m_sBnoutnumnull = "N";
//
//              }
//            }
//
//            if ("freplenishflag".equals(voaCond[i].getFieldCode()
//                .trim())) {
//              if (voaCond[i].getValue() != null
//                  && voaCond[i].getRefResult() != null)
//                sFreplenishflag = voaCond[i].getRefResult()
//                    .getRefPK();
//            }
//
//            if ("like".equals(voaCond[i].getOperaCode().trim())
//                && voaCond[i].getFieldCode() != null) {
//              // String sFeildCode = voaCond[i].getFieldCode()
//              // .trim();
//              if (sFieldCode.equals("invcl.invclasscode")
//                  && voaCond[i].getValue() != null) {
//                voaCond[i]
//                    .setValue(voaCond[i].getValue() + "%");
//              } else if (sFieldCode.equals("dept.deptcode")
//                  && voaCond[i].getValue() != null) {
//                voaCond[i]
//                    .setValue(voaCond[i].getValue() + "%");
//              } else if (voaCond[i].getValue() != null)
//                voaCond[i].setValue("%" + voaCond[i].getValue()
//                    + "%");
//            }
//          }
//        }
//      }
//      // ȱʡ��A
//      if ("2".equals(sBillStatus)) // ����
//        sBillStatusSql = " fbillflag="
//            + nc.vo.ic.pub.bill.BillStatus.FREE;
//      else if ("3".equals(sBillStatus)) // ǩ�ֵ�
//        sBillStatusSql = " ( fbillflag="
//            + nc.vo.ic.pub.bill.BillStatus.SIGNED
//            + " OR fbillflag="
//            + nc.vo.ic.pub.bill.BillStatus.AUDITED + ") ";
//
//      // �˿��ѯ add by hanwei 2003-10-10
//      if (nc.vo.ic.pub.BillTypeConst.BILLNORMAL
//          .equalsIgnoreCase(sFreplenishflag)) {
//        sBillStatusSql += " AND ( COALESCE(freplenishflag,'N') = 'N' and COALESCE(boutretflag,'N') = 'N' )";
//      } else if (nc.vo.ic.pub.BillTypeConst.BILLSENDBACK
//          .equalsIgnoreCase(sFreplenishflag)) {
//        sBillStatusSql += " AND ( freplenishflag='Y' or boutretflag = 'Y' )";
//      } else if (nc.vo.ic.pub.BillTypeConst.BILLALL
//          .equalsIgnoreCase(sFreplenishflag)) {
//        sBillStatusSql += "  ";
//      }
//
//      // ȥ��freplenishflag �Ƿ��˿�
//      String saItemKey[] = new String[] { "qbillstatus", "freplenishflag" };
//      filterCondVO2(voaCond, saItemKey);
//      // ��������
//      String sOtherCond = getConditionDlg().getWhereSQL(voaCond);
//      if (sOtherCond != null)
//        sBillStatusSql += " AND ( " + sOtherCond + " )";
//    } catch (Exception e) {
//      handleException(e);
//    }
//
//    return sBillStatusSql;
//  }

  /**
   * �������ڣ�(2003-3-4 17:13:59) ���ߣ����� �޸����ڣ� �޸��ˣ� �޸�ԭ�� ����˵����
   * 
   * @return nc.ui.ic.pub.bill.InvoInfoBYFormula
   */
  public InvoInfoBYFormula getInvoInfoBYFormula() {
    if (m_InvoInfoBYFormula == null)
      m_InvoInfoBYFormula = new InvoInfoBYFormula(getCorpPrimaryKey());
    return m_InvoInfoBYFormula;
  }

  /**
   * ȥ�����������������Ƿ�������Ĳ��� ���ܣ� ���������VO ���أ�boolean ���⣺ ���ڣ�(2002-05-20 19:55:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected boolean getIsInvTrackedBill(InvVO invvo) {
    if (invvo != null && invvo.getOuttrackin() != null) {
      return invvo.getOuttrackin().booleanValue();

    } else {
      return false;
    }
  }

  /**
   * ���� FreeItemRefPane1 ����ֵ��
   * 
   * @return nc.ui.ic.pub.freeitem.FreeItemRefPane
   */
  /* ���棺�˷������������ɡ� */
  protected LocatorRefPane getLocatorRefPane() {
    if (ivjLocatorRefPane == null) {
      try {
        ivjLocatorRefPane = new LocatorRefPane(getInOutFlag());
        ivjLocatorRefPane.setName("LocatorRefPane");
        ivjLocatorRefPane.setLocation(209, 4);
        // user code begin {1}
        //ivjLocatorRefPane.setInOutFlag(InOutFlag.IN);

        // user code end
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return ivjLocatorRefPane;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2001-9-19 16:02:07)
   * 
   * @return nc.ui.ic.ic101.OrientDialog
   * @author:���Ӣ
   */
  public nc.ui.ic.pub.orient.OrientDialog getOrientDlg() {
    if (m_dlgOrient == null) {
      m_dlgOrient = new nc.ui.ic.pub.orient.OrientDialog(this);

      // ivjQueryConditionDlg.setPKCorp(getEnvironment().getCorpID());

    }
    return m_dlgOrient;
  }

  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-30 15:06:35) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected nc.ui.pub.print.PrintEntry getPrintEntry() {
    if (null == m_print) {
      //m_print = new nc.ui.pub.print.PrintEntry(null, null);
      //modified by lirr 2009-05-22 �޸�ԭ�������ӡ ����ֱ�ӹر�����
      m_print = new nc.ui.pub.print.PrintEntry(this, null);
      m_print.setTemplateID(getEnvironment().getCorpID(),
          getFunctionNode(), getEnvironment().getUserID(), null);
    }
    return m_print;
  }

  protected nc.ui.pub.print.PrintEntry getPrintEntryNew() {
//    nc.ui.pub.print.PrintEntry pe = new nc.ui.pub.print.PrintEntry(null,
//        null);
//    modified by lirr 2009-05-22 �޸�ԭ�������ӡ ����ֱ�ӹر�����
    
    nc.ui.pub.print.PrintEntry pe = new nc.ui.pub.print.PrintEntry(this,
        null);
    pe.setTemplateID(getEnvironment().getCorpID(), getFunctionNode(),
        getEnvironment().getUserID(), null);
    return pe;
  }

  /**
   * �����ߣ�zhx ���ܣ������û�ѡ���ҵ������ ������ ���أ� ���⣺ ���ڣ�(2002-12-10 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected String getSelBusiType() {
    if (getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE) != null) {
      ButtonObject[] boaAll = getButtonManager().getButton(
          ICButtonConst.BTN_BUSINESS_TYPE).getChildButtonGroup();
      if (boaAll != null) {
        for (int i = 0; i < boaAll.length; i++)
          if (boaAll[i].isSelected()) {
            return boaAll[i].getTag();
          }
      }
    }
    return null;
  }

  /**
   * ����˵���� �������ڣ�(2002-11-18 15:49:54) ���ߣ����Ӣ �޸����ڣ� �޸��ˣ� �޸�ԭ�� �㷨˵����
   * 
   * @return java.util.ArrayList
   */
  public ArrayList getSelectedBills() {

    ArrayList albill = new ArrayList();
    int iSelListHeadRowCount = getBillListPanel().getHeadTable()
        .getSelectedRowCount();
    if (iSelListHeadRowCount <= 0) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "common", "UCH003")/* @res "��ѡ��Ҫ��������ݣ�" */);
      return null;
    }
    int[] arySelListHeadRows = new int[iSelListHeadRowCount];
    arySelListHeadRows = getBillListPanel().getHeadTable()
        .getSelectedRows();

    GeneralBillVO voaBill[] = new GeneralBillVO[iSelListHeadRowCount];
    Vector vHeadPK = new Vector();
    Vector vIndex = new Vector();

    for (int i = 0; i < iSelListHeadRowCount; i++) {
      if (getM_alListData() != null
          && getM_alListData().size() > arySelListHeadRows[i]) {

        voaBill[i] = (GeneralBillVO) getM_alListData().get(
            arySelListHeadRows[i]);
        if (voaBill[i].getChildrenVO() == null
            || voaBill[i].getChildrenVO().length == 0) {

          vHeadPK.addElement(((GeneralBillHeaderVO) voaBill[i]
              .getParentVO()).getCgeneralhid());
          vIndex.addElement(new Integer(arySelListHeadRows[i]));
        }

      }
    }

    // ��ѯ��������
    if (vIndex.size() > 0) {
      String[] saPK = new String[vHeadPK.size()];
      int[] indexs = new int[vIndex.size()];
      vHeadPK.copyInto(saPK);

      for (int i = 0; i < vIndex.size(); i++) {
        indexs[i] = ((Integer) vIndex.get(i)).intValue();
      }
      qryItems(indexs, saPK);
    }
    for (int i = 0; i < arySelListHeadRows.length; i++) {
      if (getM_alListData() != null
          && getM_alListData().size() > arySelListHeadRows[i]) {

        albill.add((GeneralBillVO) getM_alListData().get(
            arySelListHeadRows[i]));
      }
    }

    return albill;
  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-10-23 10:31:53) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param voSNs
   *            nc.vo.ic.pub.sn.SerialVO[]
   */
  protected String getSNString(SerialVO[] voSNs, String cspaceid) {
    if (voSNs == null || voSNs.length == 0)
      return null;
    StringBuffer str = new StringBuffer();

    for (int i = 0; i < voSNs.length; i++) {
      if (cspaceid != null) {
        if (cspaceid.equals(voSNs[i].getCspaceid()))
          str.append(voSNs[i].getVsn() + ";");
      } else
        str.append(voSNs[i].getVsn() + ";");
    }
    return str.toString();

  }

  /**
   * �õ�m_voBill�еĵ������ͱ��� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-12 13:18:06)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected String getSourBillTypeCode() {
    GeneralBillVO voBill = null;
    // �б���ʽ�ͱ���ʽ��ͬ
    if (getM_iCurPanel() == BillMode.List
        && getM_iLastSelListHeadRow() >= 0 && getM_alListData() != null
        && getM_alListData().size() > getM_iLastSelListHeadRow()
        && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
      // �ȶ�����
      voBill = (GeneralBillVO) getM_alListData().get(
          getM_iLastSelListHeadRow());
    else
      // ֱ����m_vo
      voBill = getM_voBill();

    if (voBill != null && voBill.getItemCount() > 0)
      return (String) voBill.getItemValue(0, "csourcetype");
    else
      return null;
  }

  /**
   * �����ߣ����˾� ���ܣ���ָ����ŵ��ݵĻ�λ/���к�����,���ڴ�ӡ��λ���к���ϸ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected GeneralBillVO getWholeBill(int iBillNum) {
    if (getM_alListData() == null || getM_alListData().size() < iBillNum) {
      SCMEnv.out("list ERR.");
      return null;
    }
    qryLocSNSort(iBillNum, QryInfoConst.LOC_SN);
    // Modified by zss for PrintLocSN
    GeneralBillVO voMyBill = null;
    // �޸��ˣ������� �޸����ڣ�2007-10-29����03:12:03
    // �޸�ԭ��ֻ���ڿ�Ƭ״̬�£����ÿ��ǵ�������ӡ˳�����⣬��ȡgetM_voBill()
    if (m_sLastKey != null && getM_voBill() != null
        && getM_iMode() == BillMode.Browse
        && getM_iCurPanel() == BillMode.Card)
      voMyBill = getM_voBill();
    else
      voMyBill = (GeneralBillVO) getM_alListData().get(iBillNum);

    return voMyBill;
  }


  public IButtonManager getButtonManager() {
    if (m_buttonManager == null) {
      try {
        m_buttonManager = new GeneralButtonManager(this);
      } catch (BusinessException e) {
        // ��־�쳣
        nc.vo.scm.pub.SCMEnv.error(e);
        showErrorMessage(e.getMessage());
      }
    }
    return m_buttonManager;
  }

  /**
   * 
   * ����������������ť�ĳ�ʼ����
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @deprecated
   * @author duy
   * @time 2007-2-5 ����02:56:08
   */
  protected void initButtons() {
  }

  /**
   * 
   * /** �����ߣ����˾� ���ܣ���ʼ����ť�� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� ˵�� by hanwei 2003-10-10
   * m_vTopMenu.insertElementAt(m_vboSendback, 0); ����������ָ���Ĳ˵�λ��
   * ���˵�����initButtonsData()����������Ի� �������������
   * nc.ui.ic.ic201.ClientUI.setButtonsStatus(int) super.initButtonsData();
   * Ҫȥ��super. ����setButtonsStatus�ظ�����initButtonsData()��
   * ���Ը��˵���������ڱ�������������m_boSendback = new ButtonObject("�˻�", "�˻�", 0);
   * ����ᵼ���Ӳ˵��ظ����ӡ�
   * <p>
   * <b>examples:</b>
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author duy
   * @deprecated
   * @time 2007-2-2 ����11:57:14
   */
  protected void initButtonsData() {
  }

  /**
   * �˴����뷽��˵���� ���ܣ���ʼ�� ����¼�� ��ť�� ������ ���أ� ���⣺ ���ڣ�(2002-9-28 10:19:23)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @deprecated
   */
  protected void initDevInputButtons() {
  }

  /**
   * �����ߣ����˾� ���ܣ���ʼ��ϵͳ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  abstract protected void initPanel();

  /**
   * �����ߣ����˾� ���ܣ���ϵͳ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void initSysParam() {
    // m_sTrackedBillFlag = "N";
    // m_sSaveAndSign = "N";
    try {
      // ��������IC028:����ʱ�Ƿ�ָ����ⵥ;���β��ո����Ƿ񵽵��ݺ�.
      // IC010 �Ƿ�ʹ��ɾ��������
      // IC060 �Ƿ���������Ƶ��ˡ�
      // IC030 ���ݺ��Ƿ������ֹ�����

      // �������� ���� ȱʡֵ
      // BD501 ����С��λ 2
      // BD502 ����������С��λ 2
      // BD503 ������ 2
      // BD504 ����ɱ�����С��λ 2
      // BD505 �ɹ�/���۵���С��λ 2

      String[] saParam = new String[] { // "IC028", "IC010",
      "BD501", "BD502", "BD503", "BD504", "BD301",// "IC060",
          "IC030",
          // "IC062",
          "IC0621", "IC0641", "IC0642", "IC050", "BD505" ,"IC026","IC040"// modify by
      // liuzy
      // 2007-04-10
      //ADDED by lirr 2009-02-25    
       ,"IC104"

      };

      // ����Ĳ���
      ArrayList alAllParam = new ArrayList();
      // ������ı�������
      ArrayList alParam = new ArrayList();
      alParam.add(getEnvironment().getCorpID()); // ��һ���ǹ�˾
      alParam.add(saParam); // ����Ĳ���
      alAllParam.add(alParam);
      // ���û���Ӧ��˾�ı������
      alAllParam.add(getEnvironment().getUserID());

      /*ArrayList alRetData = null;
      alRetData = (ArrayList) ICReportHelper.queryInfo(new Integer(
          QryInfoConst.INIT_PARAM), alAllParam);*/
      String[] saParamValue = nc.ui.ic.pub.tools.GenMethod.getSysParams(getEnvironment().getCorpID(), saParam);
      // Ŀǰ��������
      if (saParamValue == null || saParamValue.length <= 0) {
        showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000045")/* @res "��ʼ����������" */);
        return;
      }
      // ���صĲ���ֵ
      //String[] saParamValue = (String[]) alRetData.get(0);
      
      // ׷�ٵ����ݲ���,Ĭ������Ϊ"N"
      if (saParamValue != null
          && saParamValue.length >= alAllParam.size()) {
        // if (saParamValue[0] != null)
        // m_sTrackedBillFlag =
        // saParamValue[0].toUpperCase().trim();
        // �Ƿ񱣴漴ǩ�֡�Ĭ������Ϊ"N"
        // if (saParamValue[0] != null)
        // m_sSaveAndSign = saParamValue[0].toUpperCase().trim();
        // BD501 ����С��λ 2
        if (saParamValue[0] != null)
          m_ScaleValue.setNumScale(Integer.parseInt(saParamValue[0]));
        // BD502 ����������С��λ 2
        if (saParamValue[1] != null)
          m_ScaleValue.setAssistNumScale(Integer
              .parseInt(saParamValue[1]));
        // BD503 ������ 2
        if (saParamValue[2] != null)
          m_ScaleValue.setHslScale(Integer.parseInt(saParamValue[2]));
        // BD504 ����ɱ�����С��λ 2
        if (saParamValue[3] != null)
          m_ScaleValue.setPriceScale(Integer
              .parseInt(saParamValue[3]));
        // BD301 ����С��λ
        if (saParamValue[4] != null)
          m_ScaleValue.setMnyScale(Integer.parseInt(saParamValue[4]));
        // IC060 �Ƿ�������Ƶ��� 'N' 'Y'
        // if (saParamValue[7] != null)
        // m_sRemainOperator = saParamValue[7].toUpperCase().trim();
        // IC030 �Ƿ�����༭���ݺ� 'N' 'Y'
        if (saParamValue[5] != null
            && "Y".equalsIgnoreCase(saParamValue[5].trim()))
          m_bIsEditBillCode = true;

        /*
         * // IC062 �Ƿ񱣴����� if (saParamValue[6] != null &&
         * "Y".equalsIgnoreCase(saParamValue[6].trim())) m_bBarcodeSave =
         * true; else m_bBarcodeSave = false;
         */

        // m_bBarcodeSave = true;
        // IC063 ���벻�����Ƿ񱣴�����
        if (saParamValue[6] != null
            && "Y".equalsIgnoreCase(saParamValue[6].trim()))
          m_bBadBarcodeSave = true;
        else
          m_bBadBarcodeSave = false;

        // IC0641 ���������ʾ������
        if (saParamValue[7] != null
            && saParamValue[7].trim().length() > 0)
          m_iBarcodeUIColorRow = Integer.parseInt(saParamValue[7]
              .trim());

        // IC0642 ���������ʾ����ɫ
        if (saParamValue[8] != null
            && saParamValue[8].trim().length() > 0)
          m_sColorRow = saParamValue[8].trim();

        // IC050 ��������Ƿ��ղֿ����
        if (saParamValue[9] != null
            && NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000004")/*�ֿ�*/.equalsIgnoreCase(saParamValue[9].trim()))
          m_isWhInvRef = true;
        else
          m_isWhInvRef = false;

        // BD505 �ɹ�/���۵���С��λ 2
        // add by liuzy 2007-04-10
        if (saParamValue[10] != null) {
          nc.ui.pub.bill.BillItem billItem = null;

          String[] saColumns = { "nsaleprice", "ntaxprice",
              "npprice", "nquoteprice" ,"nquotentprice"};
          int scalepriceso = Integer.parseInt(saParamValue[10]);
          for (int i = 0, j = saColumns.length; i < j; i++) {
            billItem = getBillCardPanel().getBodyItem(saColumns[i]); 
            if (billItem != null)
              billItem.setDecimalDigits(scalepriceso);
            // modify by yangb 2007-11-08
            billItem = getBillListPanel().getBodyItem(saColumns[i]);
            if (billItem != null)
              billItem.setDecimalDigits(scalepriceso);
          }
        }
        //IC026
        if (saParamValue[11] != null) {
          m_sIC026 = saParamValue[11];
        }
        if (saParamValue[12] != null) {
          m_sIC040 = saParamValue[12];
        }
         if (saParamValue[13] != null
                      && "Y".equalsIgnoreCase(saParamValue[13].trim()))
                m_bCheckAssetInv = true;
              else
                m_bCheckAssetInv = false;


        SCMEnv.out("-------------- <mny>=========="
            + m_ScaleValue.getMnyScale());
      }

      m_sStartDate = nc.ui.ic.pub.tools.GenMethod.getICStartDate(getEnvironment().getCorpID());
      // ϵͳ��������
      if (null == m_sStartDate)
        m_sStartDate = getEnvironment().getLogDate();

/*      if (alRetData.size() > 2) {
        m_sStartDate = (String) alRetData.get(2);

      }*/

      //

      // �޸��ˣ������� �޸����ڣ�2007-9-5����10:22:36
      // �޸�ԭ������'�ɿ�Ʊ����','�ۼ�;������','�ۼƿ�Ʊ����','�ۼƶԳ�����','�ɶԳ�����'
      // modified by liuzy 2007-11-28 ���ӱ�β�����ֶ�
      // modified by chennn 2009-07-14 ����'�ۼƳ���ǩ������'
      m_ScaleKey.setNumKeys(new String[] { "nshouldinnum",
          "nshouldoutnum", "nsignnum", "ntranoutnum", "nretnum",
          "noutnum", "nleftnum", "ninnum", "neconomicnum",
          "nsafestocknum", "norderpointnum", "nmaxstocknum",
          "nminstocknum", "nsettlenum1", "naccountnum2", "nsignnum",
          "ntoaccountnum", "weight", "volume", "npacknum",
          "ncorrespondnum", "naccumwastnum", "ningrossnum",
          "noutgrossnum", "nleftgrsnum", "ntarenum", "nkdnum",
          "nreplenishednum", "naccinvoicenum", "navlinvoicenum",
          "naccumwastnum", "nrushnum", "ncanrushnum", "naccountnum1",
          "naccoutnum", "naccumoutbacknum", IItemKey.nordcanoutnum,
          "nquoteunitnum", "bkxcl", "xczl", "nmaxstocknum",
          "nminstocknum", "nsafestocknum" ,"nonhandnum",
          "nadjustnum","nlasterr","ncurerr","nerr","nadjustgrossnum",IItemKey.naccumoutsignnum
          });

      m_ScaleKey
          .setAssistNumKeys(new String[] { "ninassistnum",
              "nleftastnum", "nneedinassistnum", "noutassistnum",
              "nretastnum", "nshouldoutassistnum",
              "ntranoutastnum", "naccountassistnum2",
              "naccountassistnum1", "nsignassistnum",
              "ncorrespondastnum", "nreplenishedastnum" ,"nonhandastnum","nadjustastnum"});
      m_ScaleKey.setPriceKeys(new String[] { "nprice", "nplannedprice",/*
                                          "nsaleprice",
                                          "ntaxprice",
                                          "npprice",
                                          "nquoteprice",
                                          "nquotentprice"*/
                                         });
      m_ScaleKey
          .setMnyKeys(new String[] { "nmny", "nplannedmny",
              "nsalemny", "ntaxmny", "ndiscountmny", "nnetmny",
              "npmoney", "nsettlemny1", "nmaterialmoney",
              "ntoaccountmny" });
      m_ScaleKey.setHslKeys(new String[] { "hsl", "nquoteunitrate" });

    } catch (Exception e) {
      SCMEnv.out("can not get para" + e.getMessage());
      if (e instanceof nc.vo.pub.BusinessException)
        showErrorMessage(e.getMessage());
    }
  }

  /**
   * �����ߣ����˾� ���ܣ������ǰѡ�еĵ��ݲ��Ǻͱ��ڵ���ͬ�ĵ�������(����뵥�ϲ�����ڳ���)������ɾ��.
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected boolean isCurrentTypeBill() {
    // ������Ǻͱ��ڵ���ͬ�ĵ�������(����뵥�ϲ�����ڳ���)������ɾ��.
    try {
      // ��ǰѡ�еĵ���
      GeneralBillVO voBill = null;
      if (getM_iCurPanel() == BillMode.List)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
      else
        voBill = getM_voBill();
      return getBillType()
          .equals(voBill.getHeaderVO().getCbilltypecode());
    } catch (Exception e) {
      SCMEnv.out(e.getMessage());
    }
    return false;
  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-06-03 11:32:25) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected boolean isDispensedBill(GeneralBillVO gvo) {
    if (gvo == null) {

      // �б���ʽ�ͱ���ʽ��ͬ
      if (getM_iCurPanel() == BillMode.List
          && getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        // �ȶ�����
        gvo = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
      else
        // ֱ����m_vo
        gvo = getM_voBill();
    }

    if (gvo != null && gvo.getItemCount() > 0) {
      boolean frowflag = false;
      for (int i = 0; i < gvo.getItemCount(); i++) {

        Integer fbillrowflag = gvo.getItemVOs()[i].getFbillrowflag() == null ? null
            : gvo.getItemVOs()[i].getFbillrowflag();
        if (fbillrowflag != null
            && fbillrowflag.intValue() == nc.vo.ic.pub.BillRowType.afterConvert) {
          frowflag = true;
          break;
        }

      }
      return frowflag;
    } else
      return false;

  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-06-03 11:32:25) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected boolean isDispensedBill(GeneralBillVO gvo, int rownum) {
    if (gvo == null) {

      // �б���ʽ�ͱ���ʽ��ͬ
      if (getM_iCurPanel() == BillMode.List
          && getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        // �ȶ�����
        gvo = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
      else
        // ֱ����m_vo
        gvo = getM_voBill();
    }

    if (gvo != null && gvo.getItemCount() > 0) {
      boolean frowflag = false;

      Integer fbillrowflag = gvo.getItemVOs()[rownum].getFbillrowflag() == null ? null
          : gvo.getItemVOs()[rownum].getFbillrowflag();
      if (fbillrowflag != null
          && fbillrowflag.intValue() == nc.vo.ic.pub.BillRowType.afterConvert) {
        frowflag = true;

      }

      return frowflag;
    } else
      return false;

  }

  /**
   * �����ߣ����˾� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-24 12:14:35) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return boolean
   */
  public boolean isNeedBillRef() {
    if (m_bNeedBillRef && isNeedBillRefWithBillType())
      return true;
    else
      return false;
  }
  //�޸��ˣ������� �޸�ʱ�䣺2009-1-8 ����10:07:02 �޸�ԭ��ֻ��һЩ���ݲſ���ʹ�ô˷�����
  public boolean isNeedBillRefWithBillType() {
    if (null != getBillTypeCode() &&
        (ScmConst.m_saleOut.equals(getBillTypeCode()) || ScmConst.m_Pickup.equals(getBillTypeCode())
            ||ScmConst.m_purchaseIn.equals(getBillTypeCode()) ||ScmConst.m_consignMachiningIn.equals(getBillTypeCode())))
      return true;
    else
      return false;
  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-06-11 9:22:38) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected boolean isSetInv(GeneralBillVO gvo, int rownum) {
    if (gvo != null && rownum != -1
        && (rownum <= gvo.getItemCount() - 1 && rownum >= 0))
      if (gvo.getItemInv(rownum).getIsSet() != null
          && gvo.getItemInv(rownum).getIsSet().intValue() == 1)
        return true;
      else
        return false;
    else
      return false;

  }

  /**
   * �����ߣ����� ���ܣ���ǰѡ�����Ƿ������кŷ��䣬Ҫ�����б�/���µ�ѡ�� ������//��ǰѡ�е��� ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public boolean isSNmgt(int iCurSelBodyLine) {

    if (iCurSelBodyLine >= 0) {
      InvVO voInv = null;
      // ������vo,�����б��»��Ǳ���
      // ����ʽ��
      if (BillMode.Card == getM_iCurPanel()) {
        if (getM_voBill() == null) {
          SCMEnv.out("bill null E.");
          return false;
        }
        voInv = getM_voBill().getItemInv(iCurSelBodyLine);
      } else // �б���ʽ��
      if (getM_iLastSelListHeadRow() >= 0
          && getM_iLastSelListHeadRow() < getM_alListData().size()) {
        GeneralBillVO bvo = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
        if (bvo == null) {
          SCMEnv.out("bill null E.");
          return false;
        }
        voInv = bvo.getItemInv(iCurSelBodyLine);
      }

      if (voInv != null && voInv.getIsSerialMgt() != null
          && voInv.getIsSerialMgt().intValue() == 1)
        return true;
      else
        return false;
    } else
      return false;
  }

  protected boolean m_bRefBills = false;// �Ƿ�������ɶ��ŵ���

  private String m_sBizTypeRef = null;

  protected String getBizTypeRef() {
    return m_sBizTypeRef;
  }

  protected void setBizTypeRef(String bizTypeRef) {
    m_sBizTypeRef = bizTypeRef;
  }

  public void setBillRefMultiVOs(String sBizType, GeneralBillVO[] vos)
      throws Exception {
    if (vos == null || vos.length <= 0)
      return;

    if (vos != null && vos.length == 1) {
      setRefBillsFlag(false);
      setBillRefResultVO(sBizType, vos);// �ϵ�
      return;
    }
    m_sBizTypeRef = sBizType;
    
    m_alRefBillsBak = getM_alListData();

    // �����к�
    nc.ui.scm.pub.report.BillRowNo.setVOsRowNoByRule(vos, getBillType(),
        IItemKey.CROWNO);
    // �������ݵ��б�
    setDataOnList(vos);

  }

  protected void setRefBillsFlag(boolean bRefBills) {
    m_bRefBills = bRefBills;
  }

  /**
   * �����ߣ����˾� ���ܣ���ѯ���ݵı��壬���ѽ���õ�arraylist ������ int iaIndex[],������alAlldata�е�������
   * String saBillPK[]����pk���� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void qryItems(int iaIndex[], String saBillPK[]) {
    if (iaIndex == null || saBillPK == null
        || iaIndex.length != saBillPK.length) {
      SCMEnv.out("param value ERR.");
      return;
    }
    try {
      QryConditionVO voCond = new QryConditionVO();

      // ʹ�ù�ʽ��ѯʱ,����������PK
      voCond.setIntParam(0, GeneralBillVO.QRY_ITEM_ONLY_PURE);

      voCond.setParam(0, saBillPK);
      // ���ý�����
      // getPrgBar(PB_QRY).start();
      // long lTime = System.currentTimeMillis();
      ArrayList alRetData = (ArrayList) GeneralBillHelper.queryBills(
          getBillType(), voCond);
      if (alRetData == null || alRetData.size() == 0
          || iaIndex.length != alRetData.size()) {
        SCMEnv.out("ret item value ERR.");
        return;
      }

      setAlistDataByFormula(-1, alRetData);
      SCMEnv.out("1�����ʽ�����ɹ���");/*-=notranslate=-*/

      // --------------------------------------------
      GeneralBillVO voBill = null;
      for (int i = 0; i < alRetData.size(); i++) {
        // index
        voBill = (GeneralBillVO) getM_alListData().get(iaIndex[i]);
        // set value
        if (alRetData.get(i) != null && voBill != null)
          voBill.setChildrenVO(((GeneralBillVO) alRetData.get(i))
              .getChildrenVO());

      }
    } catch (Exception e) {
      showErrorMessage(e.getMessage());
    }
  }

  /**
   * �����ߣ����˾� ���ܣ��б���ʽ�´�ӡǰ�� ��Ҫ�Ļ���ʣ��ĵ��ݱ��塣 ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void queryLeftItem(ArrayList alListData) throws Exception {
    // -------------
    if (alListData == null || alListData.size() == 0)
      return;
    int iIntegralBillNum = 0; // �������ݵ�����
    GeneralBillVO voBill = null;
    GeneralBillItemVO[] voaItem = null;
    for (int bill = 0; bill < alListData.size(); bill++)
      if (alListData.get(bill) != null) {
        voBill = (GeneralBillVO) getM_alListData().get(bill);
        voaItem = voBill.getItemVOs();
        if (voaItem != null && voaItem.length > 0)
          iIntegralBillNum++;
      }
    // ����û�б���ĵ���
    if (iIntegralBillNum < alListData.size()) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000054")/*
                             * @res
                             * "����׼����ӡ���ݣ����Ժ�..."
                             */);
      if ((double) iIntegralBillNum / alListData.size() < 0.60 && getQryDlgHelp().getVoLastQryCond()!=null) {
        // ������ڷ�ֵ�ĵ���δ�������ɴ����²�ѯ
        QryConditionVO voCond = (QryConditionVO) getQryDlgHelp().getVoLastQryCond()
            .clone();
        // ��ѯ��������
        voCond.setIntParam(0, GeneralBillVO.QRY_FULL_BILL);
        SCMEnv.out("���²��������ݣ�׼����ӡ������");/*-=notranslate=-*/
        setM_alListData((ArrayList<GeneralBillVO>) GeneralBillHelper.queryBills(
            getBillType(), voCond));
      } else { // ����ֻ��ʣ�µı��弴�ɡ�

        // ������pk�����ڲ�ѯ������ǰ���ѭ������
        // be sure size >0
        int[] iaIndex = new int[alListData.size() - iIntegralBillNum];
        String[] saPk = new String[alListData.size() - iIntegralBillNum];
        int count = 0;
        for (int bill = 0; bill < alListData.size(); bill++)
          if (alListData.get(bill) != null) {
            voBill = (GeneralBillVO) getM_alListData().get(bill);
            voaItem = voBill.getItemVOs();
            if (voaItem == null || voaItem.length == 0) {
              saPk[count] = voBill.getHeaderVO().getPrimaryKey();
              iaIndex[count] = bill;
              count++;
            }
          }
        qryItems(iaIndex, saPk);
      }

    }

  }

  /**
   * �����ߣ����˾� ���ܣ��������ô��ID,��������������ݣ��������Ρ�������������������� �������кţ����ID ���أ� ���⣺
   * ���ڣ�(2001-5-8 19:08:05) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * (1)��ñ������billItemVO (2)����������� (3)�����渳������� (4)����ؼ������ǲ��ǿ��Բ���������
   * 
   * 
   */
  public void resetAllInvInfo(GeneralBillVO voBill) {
    try {
      long lTime = System.currentTimeMillis();
      if (voBill == null || voBill.getItemVOs() == null
          || voBill.getItemVOs().length == 0) {
        SCMEnv.out("---- no item ");
        return;
      }

      getBillCardPanel().getBillModel().setNeedCalculate(false);

      GeneralBillItemVO[] voaItem = voBill.getItemVOs();

      // ����ÿһ�еĴ��ID,������
      ArrayList alBillItems = new ArrayList();
      for (int i = 0; i < voBill.getItemVOs().length; i++) {
        alBillItems.add(voaItem[i]);
      }
      SCMEnv.showTime(lTime, "resetAllInvInfo:getItems");
      lTime = System.currentTimeMillis();
      // �������������
      getFormulaBillContainer().formulaBodys(getFormulaItemBody(),
          alBillItems);

      // �ƻ��۸��ѯ add by hanwei 2004-6-30
      if (alBillItems != null && voaItem.length > 0) {
        int iLen = voaItem.length;
        InvVO[] invVOs = new InvVO[iLen];
        ArrayList<InvVO> astsoilvos = new ArrayList<InvVO>(
            invVOs.length);
        for (int i = 0; i < voaItem.length; i++) {
          // ���GeneralBillItemVO��invVO����
          invVOs[i] = voaItem[i].getInv();
          if (invVOs[i] != null && invVOs[i].getIsAstUOMmgt() != null
              && invVOs[i].getIsAstUOMmgt().intValue() == 1
              && invVOs[i].getIsSolidConvRate() != null
              && invVOs[i].getIsSolidConvRate().intValue() == 1)
            astsoilvos.add(invVOs[i]);
        }

        long ITime = System.currentTimeMillis();
        String sCalID = null;
        String sWhID = null;

        if (voBill.getHeaderValue(IItemKey.CALBODY) != null)
          sCalID = (String) voBill.getHeaderValue(IItemKey.CALBODY);

        if (voBill.getHeaderValue(IItemKey.WAREHOUSE) != null)
          sWhID = (String) voBill.getHeaderValue(IItemKey.WAREHOUSE);

        // DUYONG �˴���Ҫͬʱ���ݿ����֯�Ͳֿ��ID��Ϊ�˴ӳɱ�������֯�ж�ȡ�ƻ��۸�
        getInvoInfoBYFormula().getProductPrice(invVOs, sCalID, sWhID);
        IBillType billType = BillTypeFactory.getInstance().getBillType(voaItem[0].getCsourcetype());
        if (astsoilvos.size() > 0
            && voaItem[0].getCsourcetype() != null
            && billType.typeOf(ModuleCode.PO))
          getInvoInfoBYFormula().getInVoOfHSLByHashCach(
              astsoilvos.toArray(new InvVO[astsoilvos.size()]));
        SCMEnv.showTime(ITime, "getProductPrice:" + invVOs.length
            + NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000005")/*����*/);
        for (int i = 0; i < voaItem.length; i++) {
          // ���GeneralBillItemVO��invVO����
          voaItem[i].setInv(invVOs[i]);
        }
      }

      SCMEnv.showTime(lTime, "resetAllInvInfo:formulaBodys");

      lTime = System.currentTimeMillis();
      GeneralBillItemVO[] voBillItems = new GeneralBillItemVO[alBillItems
          .size()];
      alBillItems.toArray(voBillItems);

      // voBill.calConvRate(); //���㻻����

      SCMEnv.showTime(lTime, "resetAllInvInfo:hsl");
      // ���ý�������
      // �޸� by hanwei 2003-11-18 hw
      lTime = System.currentTimeMillis();
      // ��������
      for (int i = 0; i < voBillItems.length; i++) {
        // Object oBatchcode = voBillItems[i].getVbatchcode();
        InvVO voInv = voBillItems[i].getInv();
        if (voInv != null && voInv.getIsAstUOMmgt()!=null&&voInv.getIsAstUOMmgt().intValue() != 1) {
          voBillItems[i].setHsl(null);
          voBillItems[i].setNshouldoutassistnum(null);
          voBillItems[i].setNneedinassistnum(null);
          voBillItems[i].setNinassistnum(null);
          voBillItems[i].setNoutassistnum(null);

        } else {
          if (voInv != null
              && voInv.getIsSolidConvRate() != null
              && voInv.getIsSolidConvRate().intValue() == 1
              && voInv.getHsl() != null
              && (voBillItems[i].getHsl() == null || voInv
                  .getHsl()
                  .compareTo(voBillItems[i].getHsl()) != 0)) {
            voBillItems[i].setHsl(voInv.getHsl());
          }
          if(voInv != null && voInv.getIsAstUOMmgt() != null
              && voInv.getIsAstUOMmgt().intValue() == 1 
              && voBillItems[i].getHsl()==null){
            voBillItems[i].setHsl(SmartVOUtilExt.div((UFDouble)voBillItems[i].getAttributeValue(getEnvironment().getShouldNumItemKey()), 
                (UFDouble)voBillItems[i].getAttributeValue(getEnvironment().getShouldAssistNumItemKey())));
          }
            
          // �޸��ˣ������� �޸����ڣ�2008-5-12����01:31:53
          // �޸�ԭ�򣺵��л�����ʱ����������������Ҹ�����Ϊ��ʱ���ͼ��㸨������
          if (null != voBillItems[i].getHsl()
              && GenMethod.ZERO
                  .compareTo(voBillItems[i].getHsl()) != 0){
            
            if(null != voBillItems[i].getNoutnum()
              && GenMethod.isEQZeroOrNull(voBillItems[i].getNoutassistnum())) {
            
              voBillItems[i].setNoutassistnum(voBillItems[i]
                  .getNoutnum().div(voBillItems[i].getHsl()));
              if (null != voBillItems[i].getLocator())
                for (LocatorVO locVO : voBillItems[i].getLocator())
                  if (null != locVO
                      && null != locVO.getNoutspacenum()
                      && null == locVO
                          .getNoutspaceassistnum())
                    locVO.setNoutspaceassistnum(locVO
                        .getNoutspacenum().div(
                            voBillItems[i].getHsl()));
            }
            
            if(null != voBillItems[i].getNinnum()
                && GenMethod.isEQZeroOrNull(voBillItems[i].getNinassistnum())) {
              
                voBillItems[i].setNinassistnum(voBillItems[i]
                    .getNinnum().div(voBillItems[i].getHsl()));
                if (null != voBillItems[i].getLocator())
                  for (LocatorVO locVO : voBillItems[i].getLocator())
                    if (null != locVO
                        && null != locVO.getNinspacenum()
                        && null == locVO
                            .getNinspaceassistnum())
                      locVO.setNinspaceassistnum(locVO
                          .getNinspacenum().div(
                              voBillItems[i].getHsl()));
            }
            
            
            if(voBillItems[i].getAttributeValue(getEnvironment().getShouldNumItemKey())!=null
                && GenMethod.isEQZeroOrNull((UFDouble)voBillItems[i].getAttributeValue(getEnvironment().getShouldAssistNumItemKey())) ) {
              voBillItems[i].setAttributeValue(getEnvironment().getShouldAssistNumItemKey(),
              SmartVOUtilExt.div((UFDouble)voBillItems[i].getAttributeValue(getEnvironment().getShouldNumItemKey()), 
                      voBillItems[i].getHsl() ) );
            }

          }
        }

      }
      // added by lirr 2009-11-14����02:42:24 �ʲ����ʲ�����Ҫִ���Զ����ʽ
      if(getBillTypeCode().equals("4401") || getBillTypeCode().equals("4451")){
          setBillVO(voBill, false, true);
      }
      else{
          setBillVO(voBill, false, false);
      }
      
      SCMEnv.showTime(lTime, "resetAllInvInfo:setBillVO");

      lTime = System.currentTimeMillis();
      for (int i = 0; i < voBillItems.length; i++) {
        // Object oBatchcode = voBillItems[i].getVbatchcode();
        InvVO voInv = voBillItems[i].getInv();
        if (voInv != null && voInv.getIsAstUOMmgt()!=null&&voInv.getIsAstUOMmgt().intValue() != 1) {
          voBillItems[i].setHsl(null);
          voBillItems[i].setNshouldoutassistnum(null);
          voBillItems[i].setNneedinassistnum(null);
          voBillItems[i].setNinassistnum(null);
          voBillItems[i].setNoutassistnum(null);

        }

      }
      SCMEnv.showTime(lTime, "���κż�������ʱ��");/*-=notranslate=-*/

      // modified by liuzy 2009-9-21 ����10:16:48 �÷�����resetAllInvInfo��ֻ��ת���ǵ��ã����ת����ͳһ����ϼ�
//      getBillCardPanel().getBillModel().setNeedCalculate(true);
//      getBillCardPanel().getBillModel().reCalcurateAll();
    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
    }
  }

  /**
   * �����ߣ����˾� ���ܣ������趨�İ�ť��ʼ���˵��� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * �޸��ˣ������� �޸�ʱ�䣺2008-12-31 ����11:29:12 �޸�ԭ��ɾ���˷�����
   * @deprecated
   */
/*  protected void resetButtons() {
    try {
      initButtonsData();
      initDevInputButtons();
      setButtons();
    } catch (Exception e) {
      handleException(e);
    }

  }*/

  
  
  /**
   * �����ߣ����˾� ���ܣ����ֿ��������ݣ��ŵ�m_voBill ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void resetWhInfo(GeneralBillVO voBill) {
    try {
      if (voBill == null
          || voBill.getHeaderValue(IItemKey.WAREHOUSE) == null) {
        SCMEnv.out("-->W:no wh");
        return;
      } // ��ѯ��Ҫ�ֿ�ID
      String sWhID = voBill.getHeaderValue(IItemKey.WAREHOUSE).toString()
          .trim();
      // ���ֿ�����
      // �飬����WhVO
      WhVO voWh;
      if(!hm_whid_vo.containsKey(sWhID)){
        voWh = (WhVO) GeneralBillHelper.queryInfo(new Integer(
          QryInfoConst.WH), sWhID);
        hm_whid_vo.put(sWhID, voWh);
      }
      voWh = hm_whid_vo.get(sWhID);

      if (getM_voBill() != null) { // ���òֿ�����
        getM_voBill().setWh(voWh);
        voBill.setWh(voWh);// v5
        // ���û�λ��ť������
        setBtnStatusSpace(false);
      }

    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
      showErrorMessage(e2.getMessage());
    }
  }

  /**
   * ����ǰ�Ե�������ĳЩ�̶�ֵ��
   * 
   * @since v5
   * @author ljun ֻ��ί��ӹ���ⵥ���أ����ü��ɹ�Ĭ��ֵ�����ڲɹ�����
   */
  // v5
  protected void beforeSave(GeneralBillVO voBill) {

  }
  
  /**
   * 
   * ����ύ�����棩�ĵ��ݶ�������
   * <p>
   * <p>
   * <b>����˵��</b>
   * @return �ύ�����棩�ĵ��ݶ�������
   * <p>
   * @author duy
   * @time 2008-7-17 ����09:42:34
   */
  protected String getCommitActionName() {
    return "WRITE";
  }
  
  private void setOID( String pk_corp ) throws Exception {
    if ( tempHeaderID == null ) { 
      tempHeaderID = (String)nc.ui.ic.pub.tools.GenMethod.callICEJBService(
        "nc.bs.ic.pub.bill.GeneralBillDMO", 
        "getOID", 
        new Class[]{
            String.class
        },
        new Object[]{
            pk_corp
          }
      ); 
    }
  }
  
  private String getOID() { 
    return tempHeaderID;
  }
  
  public void clearOID() {
    tempHeaderID = null;
  }
  
  /**
   * �����ߣ����˾� ���ܣ����������ĵ��� �д�����Ҫ���쳣�׳���Ӱ��������
   * 
   * �������������� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void saveNewBill(GeneralBillVO voNewBill) throws Exception {

    try {
      // �õ����ݴ��󣬳��� ------------ EXIT -------------------
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@saveNewBill��ʼ");/*-=notranslate=-*/
      if (voNewBill == null || voNewBill.getParentVO() == null
          || voNewBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000055")/*
                                     * @res
                                     * "����Ϊ�գ�"
                                     */);
      }
      // ִ�б���...
      // ͨ��ƽ̨ʵ�ֱ��漴ǩ�֣�����ǩ�����ֶΣ��ڳ���ⵥ����ʱ���֮��
      // ֧��ƽ̨��cloneһ�����Ա����Ժ�Ĵ���ͬʱ��ֹ�޸���m_voBill
      GeneralBillVO voTempBill = (GeneralBillVO) voNewBill.clone();
      GeneralBillHeaderVO voHead = voTempBill.getHeaderVO();
      voHead.setPk_corp(getEnvironment().getCorpID());

      // voHead.setDaccountdate(new
      // nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
      // �����ĵ������PK
      voHead.setPrimaryKey(null);
      // ����PK
      GeneralBillItemVO[] voaItem = voTempBill.getItemVOs();
      for (int row = 0; row < voaItem.length; row++)
        voaItem[row].setPrimaryKey(null);

      // ��������������������
      // nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.beforSaveBillVOBarcode(
      // m_bBarcodeSave, voNewBill);

      voHead.setCoperatoridnow(getEnvironment().getUserID()); // ��ǰ����Ա2002-04-10.wnj
      voHead.setAttributeValue("clogdatenow", getEnvironment()
          .getLogDate()); // ��ǰ��¼����2003-01-05

      voTempBill.setParentVO(voHead);
      voTempBill.setChildrenVO(voaItem);
      timer.showExecuteTime("@@���ñ�ͷ�ͱ��壺");/*-=notranslate=-*/
      // --------- save -------------
      // ���뵥�ݺ�,������ݺ�==sCorpID�������ݺ��ÿգ���̨���Զ���ȡ��
      if (getEnvironment().getCorpID().equals(voHead.getVbillcode())) {
        voHead.setVbillcode(null);
      }
      ArrayList alPK = null;

      // ����У�����־ add by hanwei 2004-04-01
      voTempBill.setAccreditUserID(voNewBill.getAccreditUserID());
      voTempBill.setOperatelogVO(voNewBill.getOperatelogVO());
      // IP��ַ����
      // ���ݲ����Ƿ񱣴����룬�������

      // �Ƿ񱣴�����
      voTempBill.setSaveBadBarcode(m_bBadBarcodeSave);
      // �Ƿ񱣴�������һ�µ�����
      // voTempBill.setSaveBarcode(m_bBarcodeSave);
      // ���ڡ�������Ϣ
      voTempBill.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_ADD;
      voTempBill.m_sActionCode = "SAVEBASE";

      beforeSave(voTempBill);

      // ��������������������
      nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
          .beforSaveBillVOBarcode(voTempBill);

      // GeneralBillVO billvo_to_bs = (GeneralBillVO)voTempBill.clone();
      GeneralBillItemVO[] bakbvos = voTempBill.getItemVOs();

      try {
        // add by liuzy 2007-11-02 10:16 ѹ������
        // ObjectUtils.objectReference(voTempBill);
        voTempBill.compressBodyWhenSave();
        
        // songhy, 2009-11-16��start���������ʱ���ε����ߵ��������ظ�������
        if ( getOID() == null ) {
          setOID( getEnvironment().getCorpID() );
        }
        voTempBill.getHeaderVO().setAttributeValue("cgeneralhid_temp", getOID());
        // songhy, 2009-11-16��end.
        
        alPK = (ArrayList) nc.ui.pub.pf.PfUtilClient.processAction(
            getCommitActionName(), getBillType(), getEnvironment().getLogDate(),
            voTempBill);
        
        // songhy, 2009-11-16��start���������ʱ���ε����ߵ��������ظ�������
        clearOID();
        // songhy, 2009-11-16��end.

      }
      finally {
        voTempBill.setChildrenVO(bakbvos);
      }

      SCMEnv.out("ret..");
      timer.showExecuteTime("@@��ƽ̨����ʱ�䣺");/*-=notranslate=-*/

      // [[��ʾ��Ϣ][PK_Head,PK_body1,PK_body2,....]]
      // ���浱ǰ���ݵ�OID
      // �������ݴ��� ------------------- EXIT --------------------------
      if (alPK == null || alPK.size() < 3 || alPK.get(1) == null
          || alPK.get(2) == null) {
        SCMEnv.out("return data error. al pk" + alPK);
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000056")/*
                               * @res
                               * "�����Ѿ����棬������ֵ���������²�ѯ���ݡ�"
                               */);
      } else {

        // ��ʾ��ʾ��Ϣ
        if (alPK.get(0) != null
            && alPK.get(0).toString().trim().length() > 0)
          showWarningMessage((String) alPK.get(0));

        // ����
        int iRowCount = voNewBill.getItemCount();
        ArrayList alMyPK = (ArrayList) alPK.get(1);
        if (alMyPK == null || alMyPK.size() < (iRowCount + 1)
            || alMyPK.get(0) == null || alMyPK.get(1) == null) {
          SCMEnv.out("return data error. my pk " + alMyPK);
          showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000057")/*
                                       * @res
                                       * "���淵��ֵ����"
                                       */);
        } else {
          // ��ͷ��OID
          m_sCurBillOID = (String) alMyPK.get(0);
          // ���ý���ĵ���ID
          if (getBillCardPanel().getHeadItem(IItemKey.NAME_HEADID) != null)
            getBillCardPanel().setHeadItem(IItemKey.NAME_HEADID,
                m_sCurBillOID);
          //
          voNewBill.getParentVO().setPrimaryKey(m_sCurBillOID);
          // �����OID
          // ###################################################
          // �������õ���vo����VOPK����ͷts,billcode,����
          // ��ͷ��cgeneralhid,fbillfalg,vbillcode,ts
          // ���壺cgeneralbid,crowno,vbatchcode,vfirstbillcode,ninnum,ninassistnum,noutnum,noutassistnum,ts
          SMGeneralBillVO smbillvo = null;
          smbillvo = (SMGeneralBillVO) alPK.get(2);
          m_sBillStatus = (smbillvo.getHeaderVO().getFbillflag())
              .toString();

          getGenBillUICtl().refreshSaveData(smbillvo,
              getBillCardPanel(), voNewBill, getM_voBill());
          getGenBillUICtl().refreshLocFromSMVO(smbillvo,
              getBillCardPanel(), m_alLocatorData, getM_voBill());
          /*getGenBillUICtl().refreshBatchcodeAfterSave(
              getBillCardPanel(), getM_voBill());*/

          timer.showExecuteTime("@@���ñ�ͷ�ͱ���PKʱ�䣺");/*-=notranslate=-*/
          getM_voBill().clearAccreditBarcodeUserID();
          // ��д��m_alListData
          if (!m_bRefBills)
            insertBillToList(getM_voBill());

          timer.showExecuteTime("@@insertBillToList(m_voBill)��");/*-=notranslate=-*/
        }
      }
      SCMEnv.out("insertok..");

      // v5 lj ֧�ֲ������ɶ��ŵ���
      if (m_bRefBills == true) {
        // removeBillsOfList(new int[] { m_iLastSelListHeadRow });

        setButtonStatus(false);
        ctrlSourceBillUI(true);
      }

    } catch (Exception e) {
      // �쳣�����׳����������̴�����Ϊ��Ӱ�������̡�
      // ###################################################
      // ���������쳣����¼��̨��־ add by hanwei 2004-6-8
      // ###################################################

      throw e;

    }
  }

  /**
   * �����ߣ����˾� ���ܣ������޸ĵĵ���
   * 
   * �д�����Ҫ���쳣�׳���Ӱ��������
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void saveUpdatedBill(GeneralBillVO voUpdatedBill)
      throws Exception {
    try {
      // �õ����ݴ��󣬳��� ------------ EXIT -------------------
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@�޸ı��浥�ݿ�ʼ��");/*-=notranslate=-*/
      if (voUpdatedBill == null || voUpdatedBill.getParentVO() == null
          || voUpdatedBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000055")/*
                                     * @res
                                     * "����Ϊ�գ�"
                                     */);
      }

      // ��������������������
      // nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.beforSaveBillVOBarcode(
      // m_bBarcodeSave, voUpdatedBill);

      // ���õ�������
      voUpdatedBill.setHeaderValue("cbilltypecode", getBillType());
      // 05/07�����Ƶ���Ϊ��ǰ����Ա
      // remark by zhx onSave() set coperatorid into VO
      // voUpdatedBill.setHeaderValue("coperatorid",
      // getEnvironment().getUserID());
      timer.showExecuteTime("@@���õ������ͣ�");/*-=notranslate=-*/
      GeneralBillVO voBill = (GeneralBillVO) getM_voBill().clone();
      timer.showExecuteTime("@@m_voBill.clone()��");/*-=notranslate=-*/
      voBill.setIDItems(voUpdatedBill);
      int iDelRowCount = voUpdatedBill.getItemCount()
          - voBill.getItemCount();
      if (iDelRowCount > 0) {
        // ����У�׷��ɾ����
        GeneralBillItemVO voaItems[] = new GeneralBillItemVO[voUpdatedBill
            .getItemCount()];
        // ԭ��
        for (int org = 0; org < voBill.getItemCount(); org++)
          voaItems[org] = voBill.getItemVOs()[org];

        // ����ɾ����
        for (int del = 0; del < iDelRowCount; del++)
          voaItems[voBill.getItemCount() + del] = voUpdatedBill
              .getItemVOs()[voBill.getItemCount() + del];

        voBill.setChildrenVO(voaItems);
        timer.showExecuteTime("@@voBill.setChildrenVO(voaItems)��");/*-=notranslate=-*/
      }
      GeneralBillHeaderVO voHead = voBill.getHeaderVO();
      // --------------------------------------------���Բ��ǵ�ǰ��¼��λ�ĵ��ݣ����Բ���Ҫ�޸ĵ�λ��
      voHead.setPk_corp(getEnvironment().getCorpID());
      // ��Ϊ��¼���ں͵��������ǿ��Բ�ͬ�ģ����Ա���Ҫ��¼���ڡ�
      // voHead.setDaccountdate(new
      // nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
      // vo����Ҫ����ƽ̨������Ҫ���ɺ�ǩ�ֺ�ĵ���
      voHead.setCoperatoridnow(getEnvironment().getUserID()); // ��ǰ����Ա2002-04-10.wnj
      voHead.setAttributeValue("clogdatenow", getEnvironment()
          .getLogDate()); // ��ǰ��¼����2003-01-05

      // update by zhx on 0926 ���Ӹ��ݿ�����IC060 �ж��Ƿ���������Ƶ��ˡ�
      // IC060 ����N�� �����������޸ĵ���ʱ�����Ƶ��ˡ����򣬲����á�
      // if (m_sRemainOperator != null
      // && m_sRemainOperator.equalsIgnoreCase("N"))
      // voHead.setCoperatorid(getEnvironment().getUserID());

      // �޸�ǰ�ĵ���
      GeneralBillVO voOriginalBill = null;
      // ���Ե����б�
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
        voOriginalBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
        voOriginalBill.getHeaderVO().setCoperatoridnow(
            getEnvironment().getUserID());
        voOriginalBill.getHeaderVO().setPk_corp(
            getEnvironment().getCorpID());
      } else {
        SCMEnv.out("original null,maybe error.");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000058")/*
                                     * @res
                                     * "δ�ҵ�ԭʼ���ݣ����ѯ�����ԡ�"
                                     */);
      }
      timer.showExecuteTime("@@���ñ�ͷ���ݣ�");/*-=notranslate=-*/
      // 2003-06-13.02 wnj:���û�λ�����к��޸�״̬��
      voBill.setLocStatus(voOriginalBill);
      timer.showExecuteTime("@@���û�λ�����к��޸�״̬��");/*-=notranslate=-*/
      // ----
      // add by hanwei 2004-04
      voBill.setAccreditUserID(voUpdatedBill.getAccreditUserID());
      voBill.setOperatelogVO(voUpdatedBill.getOperatelogVO());

      // �Ƿ񱣴�����
      voBill.setSaveBadBarcode(m_bBadBarcodeSave);
      // �Ƿ񱣴�������һ�µ�����
      // voBill.setSaveBarcode(m_bBarcodeSave);

      // ��������������������
      nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
          .beforSaveBillVOBarcode(voBill);
      ArrayList alRetData = null;

      // GeneralBillVO curvo_to_bs = (GeneralBillVO)voBill.clone();
      // GeneralBillVO oldvo_to_bs =
      // (GeneralBillVO)voOriginalBill.clone();
      GeneralBillItemVO[] bakcurbodyvos = voBill.getItemVOs();
      GeneralBillItemVO[] bakoldbodyvos = voOriginalBill.getItemVOs();
      
//     ���ڡ�������Ϣ
      voBill.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_MODIFY;
      voBill.m_sActionCode = "SAVEBASE";
      voBill.m_voOld = voOriginalBill;

      try {

        // add by liuzy 2007-11-02 10:16 ѹ������
        // ObjectUtils.objectReference(new GeneralBillVO[]{
        // voBill,voOriginalBill});
        // ObjectUtils.objectReference(voOriginalBill);
        //�޸��ˣ������� �޸�ʱ�䣺2008-8-15 ����02:27:03 �޸�ԭ�򣺺�̨�жϵ��ݺ���û���޸��á�
        voBill.getHeaderVO().setAttributeValue("oldVBillCode", voOriginalBill.getVBillCode());
        
        voBill.compressBodyWhenSave();
        voOriginalBill.setTransNotFullVo();

        
        alRetData = (ArrayList) nc.ui.pub.pf.PfUtilClient
            .processAction(getCommitActionName(), getBillType(), getEnvironment()
                .getLogDate(), voBill, voOriginalBill);
      } finally {
        voBill.setChildrenVO(bakcurbodyvos);
        voOriginalBill.setChildrenVO(bakoldbodyvos);
      }

      timer.showExecuteTime("@@��ƽ̨���棺��");/*-=notranslate=-*/
      // new GeneralBillVO[]{m_voBill});
      // [[��ʾ��Ϣ][new_PK_body1,new_PK_body2,....]]
      // ���浱ǰ���ݵ�OID
      // �������ݴ��� ------------------- EXIT --------------------------
      if (alRetData == null || alRetData.size() < 3
          || alRetData.get(1) == null || alRetData.get(2) == null) {
        SCMEnv.out("return data error.");
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000056")/*
                               * @res
                               * "�����Ѿ����棬������ֵ���������²�ѯ���ݡ�"
                               */);
      } else {
        // 0 ---- ��ʾ��ʾ��Ϣ
        if (alRetData.get(0) != null
            && alRetData.get(0).toString().trim().length() > 0)
          showWarningMessage((String) alRetData.get(0));
        // 1 ---- ���ص�PK
        ArrayList alMyPK = (ArrayList) alRetData.get(1);

        // ���������е�PK ͨ��smallvo���ü���
        //setBodyPkAfterUpdate(alMyPK);
        //timer.showExecuteTime("@@���������е�PK����");
        
        SMGeneralBillVO smbillvo = null;
        smbillvo = (SMGeneralBillVO) alRetData.get(2);
        timer.showExecuteTime("@@getBillCardPanel().updateValue()����");/*-=notranslate=-*/
        voUpdatedBill = getBillVO();
        // ���õ�������
        voUpdatedBill.setHeaderValue("cbilltypecode", getBillType());

        // ###################################################
        // �������õ���vo����VOPK����ͷts,billcode,����
        // ��ͷ��cgeneralhid,fbillfalg,vbillcode,ts
        // ���壺cgeneralbid,crowno,vbatchcode,vfirstbillcode,ninnum,ninassistnum,noutnum,noutassistnum,ts
        
        
        m_sBillStatus = (smbillvo.getHeaderVO().getFbillflag())
            .toString();
        getGenBillUICtl().refreshSaveData(smbillvo, getBillCardPanel(),
            voUpdatedBill, getM_voBill());
        getGenBillUICtl().refreshLocFromSMVO(smbillvo,
            getBillCardPanel(), m_alLocatorData, getM_voBill());
        /*getGenBillUICtl().refreshBatchcodeAfterSave(getBillCardPanel(),
            getM_voBill());*/

        // ���by hanwei 2004-9-23
        GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
        // ################################################### 

        getBillCardPanel().updateValue();

        timer.showExecuteTime("@@m_voBill.setIDItems(voUpdatedBill)��");/*-=notranslate=-*/

        getM_voBill().clearAccreditBarcodeUserID();
        // ˢ���б�����
        updateBillToList(getM_voBill());
        timer.showExecuteTime("@@ˢ���б�����updateBillToList(m_voBill)��");/*-=notranslate=-*/
      }

    } catch (Exception e) {
      // �쳣�����׳����������̴�����Ϊ��Ӱ�������̡�
      throw e;

    }
  }

  /**
   * �����ߣ����˾� ���ܣ����б�ʽ��ѡ��һ�ŵ��� ������ ������alListData�е����� ���أ��� ���⣺ ���ڣ�(2001-11-23
   * 18:11:18) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  abstract protected void selectBillOnListPanel(int iBillIndex);

  /**
   * �������ڣ�(2003-3-6 11:33:06) ���ߣ����� �޸����ڣ� �޸��ˣ� �޸�ԭ�� ����˵����
   * ȡ�õ��ݲ�ѯ���ݣ���ָ��������Χ�ĵ��ݵı��� �ô����ʽ��������������й���������
   * 
   * @param iTopnum
   *            int
   * @param lListData
   *            java.util.ArrayList
   */
  public void setAlistDataByFormula(int iTopnum, ArrayList lListData) {
    if (lListData == null || lListData.size() == 0)
      return;
    // ��Ҫ�����ʽ�����ĵ�����Ŀ
    int iFormulaNum = 0;
    // ���iTopnum=-1 ��ʾȡ���еĵ���
    // ���lListData.size
    // ���� iTopnum ��lListData.sizeΪ��׼
    if (lListData.size() < iTopnum || iTopnum < 0)
      iFormulaNum = lListData.size();
    else
      iFormulaNum = iTopnum;

    GeneralBillVO billVo = null;
    nc.vo.ic.pub.bill.GeneralBillItemVO[] itemVos = null;
    java.util.ArrayList alItemVos = new ArrayList();
    int iItemLen = 0;
    for (int i = 0; i < iFormulaNum; i++) {
      billVo = (GeneralBillVO) lListData.get(i);
      itemVos = (GeneralBillItemVO[]) billVo.getChildrenVO();
      if (itemVos != null && itemVos.length > 0) {
        iItemLen = itemVos.length;
        for (int j = 0; j < iItemLen; j++) {
          // ���item
          alItemVos.add(itemVos[j]);
        }
      }

    }

    if (alItemVos != null && alItemVos.size() > 0) {
      // ͨ�����ݹ�ʽ������ִ���йع�ʽ�����ķ���
      getFormulaBillContainer().formulaBodys(getFormulaItemBody(),
          alItemVos);

    }

  }

  /**
   * ����˵���� �������ڣ�(2002-12-23 11:59:38) ���ߣ����˾� �޸����ڣ� �޸��ˣ� �޸�ԭ�� �㷨˵����
   * 
   * @param row
   *            int
   * 
   */
  protected void setBodySpace(int row) {

    if (getBillCardPanel().getBodyItem("vspacename") == null)
      return;
    getBillCardPanel().setBodyValueAt(null, row, "vspacename");
    getBillCardPanel().setBodyValueAt(null, row, "cspaceid");
    getBillCardPanel().setBodyValueAt(null, row, "vserialcode");

    // д�����λ
    if (row < 0 || m_alLocatorData == null
        || m_alLocatorData.size() < row + 1)
      return;

    LocatorVO[] voaLoc = (LocatorVO[]) m_alLocatorData.get(row);
    SerialVO[] sns = (SerialVO[])m_alSerialData.get(row);

    if (voaLoc != null && voaLoc.length == 1 && voaLoc[0] != null) {
      getBillCardPanel().setBodyValueAt(voaLoc[0].getVspacename(), row,
          "vspacename");
      getBillCardPanel().setBodyValueAt(voaLoc[0].getCspaceid(), row,
          "cspaceid");
    }
    
    if (sns != null && sns.length == 1 && sns[0] != null) {
      getBillCardPanel().setBodyValueAt(sns[0].getVserialcode(), row, "vserialcode");
    }
  }
  
  //boolean isSetButtons = false;

  /**
   * �����ߣ����˾� ���ܣ������趨�İ�ť��ʼ���˵��� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  protected void setButtons() {
/*    if (isSetButtons){
      SCMEnv.out("��������Ը��٣��Ѿ����ù��ˣ��趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getBillTypeCode()+" ʧ��");
      return;
    }
    isSetButtons = true;*/
    //�޸��ˣ������� �޸�ʱ�䣺2008-12-24 ����04:30:20 �޸�ԭ��Ϊ�˲�׽��ťû�е����������ʱ����־�����
    //int inum = 0;
    ButtonObject[] buttonArray = getButtonManager().getButtonTree().getButtonArray();
/*    if (null != getBillTypeCode() && ("4D".equals(getBillTypeCode()) || "4401".equals(getBillTypeCode()))){
      SCMEnv.out("�趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getBillTypeCode());
      for (ButtonObject bo : buttonArray){
        SCMEnv.out("��ť��"+bo.getName());
        inum = inum +1;
        if (null != bo.getChildren() && 0 < bo.getChildren().size())
          for (int i = 0 ; i<bo.getChildren().size() ;i++){
            ButtonObject bb = (ButtonObject)bo.getChildren().get(i);
            SCMEnv.out("��ť��"+bo.getName() + "���Ӱ�ť��"+bb.getName());
            inum = inum +1;
          }
      }
    }
    
    SCMEnv.out("��ѯ���ڵ�"+getBillTypeCode()+"�İ�ť����: "+inum);*/
    
    setButtons(buttonArray);

  }

  /**
   * �����ߣ����˾� ���ܣ����󷽷������ð�ť״̬����setButtonStatus�е��á� ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  abstract protected void setButtonsStatus(int iBillMode);

  /**
   * �����ߣ����˾� ���ܣ����ñ�������ɫ�� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void setColor() {
    try {
      // ���ı���ɫ��ı�����Header��ɫ��
      javax.swing.table.DefaultTableCellRenderer tcrold = (javax.swing.table.DefaultTableCellRenderer) getBillCardPanel()
          .getBillTable().getColumnModel().getColumn(1)
          .getHeaderRenderer();
      HeaderRenderer tcr = new HeaderRenderer(tcrold);

      // �ֱ�õ���ͷ�ͱ�������������ʾ���ֶ�
      ArrayList alHeaderColChangeColorString = GeneralMethod
          .getHeaderCanotNullString(getBillCardPanel());
      ArrayList alBodyColChangeColorString = GeneralMethod
          .getBodyCanotNullString(getBillCardPanel());

      // �޸ı��еı�ͷ��ɫ
      SetColor.SetBillCardHeaderColor(getBillCardPanel(),
          alHeaderColChangeColorString);
      // SetBillCardHeaderColor(alHeaderColChangeColorString);

      // ���������ɫ���ڱ���Header��
      nc.ui.scm.ic.exp.SetColor.SetTableHeaderColor(getBillCardPanel()
          .getBillModel(), getBillCardPanel().getBillTable(),
          alBodyColChangeColorString, tcr);
      SetColor.SetTableHeaderColor(getBillListPanel().getHeadBillModel(),
          getBillListPanel().getHeadTable(),
          alHeaderColChangeColorString, tcr);
      SetColor.SetTableHeaderColor(getBillListPanel().getBodyBillModel(),
          getBillListPanel().getBodyTable(),
          alBodyColChangeColorString, tcr);
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }
  

  /**
   * �����ߣ����˾� ���ܣ��ڱ�������ʾVO,�����½���״̬updateValue() ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setImportBillVO(GeneralBillVO bvo) throws Exception {
    setImportBillVO(bvo, true);
  }

  /**
   * �����ߣ����˾� ���ܣ�ѡ���б���ʽ�µĵ�sn�ŵ��� ������sn �������
   * 
   * ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setListBodyData(GeneralBillItemVO voi[]) {
    
    getPluginProxy().beforeSetBillVOsToListBody(voi);
    
    if (voi != null) {
      try {
        getBillListPanel().getBodyTable().getModel()
            .removeTableModelListener(this);
        getBillListPanel().setBodyValueVO(voi);
        // �����Ѿ���setAlistDataBYFormula��ִ���˱��幫ʽ��
        // ���Բ������ظ������������д��� by hanwei 2003-06-24
        // getBillListPanel().getBodyBillModel().execLoadFormula();
      } catch (Exception e) {
        SCMEnv.out(e.getMessage());
      } finally {
        getBillListPanel().getBodyTable().getModel()
            .addTableModelListener(this);
      }
    }
    
    getPluginProxy().afterSetBillVOsToListBody(voi);
    
  }

  /**
   * �����ߣ����˾� ���ܣ�ˢ���б���ʽ��ͷ����Ϊָ�������� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void execHeadFormulaAtVOs(GeneralBillHeaderVO[] voh) {
    if (voh == null || voh.length <= 0)
      return;
    if (m_bIsByFormula) {
      // ��ӹ�ʽ�����ȡ�ֿ�ͷ�Ʒ�ֿ���Ϣ by hw 2003-02-27
      getInvoInfoBYFormula().setBillHeaderWH(voh);
    }

    // ͨ�����ݹ�ʽ������ִ���йع�ʽ�����ķ���
    getFormulaBillContainer().formulaHeaders(getFormulaItemHeader(), voh);
  }

  /**
   * �����ߣ����˾� ���ܣ�ˢ���б���ʽ��ͷ����Ϊָ�������� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void execFormulaAtBillVO(GeneralBillVO billvo) {
    if (billvo == null)
      return;
    execHeadFormulaAtVOs(new GeneralBillHeaderVO[] { billvo.getHeaderVO() });
    if (billvo.getItemVOs() == null || billvo.getItemVOs().length <= 0)
      return;
    ArrayList<GeneralBillItemVO> listitem = new ArrayList<GeneralBillItemVO>(
        billvo.getItemVOs().length);
    for (GeneralBillItemVO itemvo : billvo.getItemVOs())
      listitem.add(itemvo);
    getFormulaBillContainer().formulaBodys(getFormulaItemBody(), listitem);

  }

  /**
   * �����ߣ����˾� ���ܣ�ˢ���б���ʽ��ͷ����Ϊָ�������� ������ ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setListHeadData(GeneralBillHeaderVO voh[]) {
    
    getPluginProxy().beforeSetBillVOsToListHead(voh);
    
    if (voh != null && voh.length > 0) {
      try {
        getBillListPanel().getHeadBillModel().setSortColumn(null);

        execHeadFormulaAtVOs(voh);

        // �����������
        getBillListPanel().setHeaderValueVO(voh);
        // �����Ե���������룬�Ѿ�������ִ���˱�ͷ��ʽ
        // getBillListPanel().getHeadBillModel().execLoadFormula();

      } catch (Exception e2) {
        nc.vo.scm.pub.SCMEnv.error(e2);
      }
    }
    
    getPluginProxy().afterSetBillVOsToListHead(voh);
  }

  /**
   * �����ߣ����˾� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-24 12:14:35) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param newNeedBillRef
   *            boolean
   */
  public void setNeedBillRef(boolean newNeedBillRef) {
    m_bNeedBillRef = newNeedBillRef;
  }

  /**
   * �����ߣ����˾� ���ܣ��б���ʽ�´�ӡǰ�� ��С������ ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public void setScaleOfListData(ArrayList alListData) {
    if (alListData != null) {
      GeneralBillItemVO[] voaItem = null;
      for (int bill = 0; bill < alListData.size(); bill++)
        if (alListData.get(bill) != null) {
          voaItem = ((GeneralBillVO) alListData.get(bill))
              .getItemVOs();

          GenMethod.setScale(voaItem, m_ScaleKey, m_ScaleValue);

        }
    } else
      SCMEnv.out("ld null.");

  }

  /**
   * �����ߣ����˾� ���ܣ����ñ���/��β��С��λ�� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void setScaleOfListPanel() {
    nc.ui.ic.pub.scale.ScaleInit si = new nc.ui.ic.pub.scale.ScaleInit(
        getEnvironment().getUserID(), getEnvironment().getCorpID(),
        m_ScaleValue);

    try {
      si.setScale(getBillListPanel(), m_ScaleKey);
    } catch (Exception e) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000060")/* @res "��������ʧ�ܣ�" */
          + e.getMessage());
    }

  }

  /**
   * �����ߣ����˾� ���ܣ��ڱ�������ʾVO,�����½���״̬updateValue() ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setTempBillVO(GeneralBillVO bvo) throws Exception {
    if (bvo == null)
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000061")/* @res "����ĵ���Ϊ�գ�" */);
    // ��������
    int iRowCount = bvo.getItemCount();

    try {
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      getBillCardPanel().removeBillEditListenerHeadTail();
      // ����һ��clone()
      setM_voBill((GeneralBillVO) bvo.clone());
      // ��������
      getBillCardPanel().setBillValueVO(bvo);
      // ִ�й�ʽ
      getBillCardPanel().getBillModel().execLoadFormula();
      execHeadTailFormulas();
      // ����״̬ ---delete it to support CANCEL
      // getBillCardPanel().updateValue();
      // �����ִ�������
      bvo.clearInvQtyInfo();
      // �б����У�ѡ�е�һ��
      if (iRowCount > 0) {
        // ѡ�е�һ��
        getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
        // �������к��Ƿ����
        setBtnStatusSN(0, true);
        // ˢ���ִ�����ʾ
        // setTailValue(0);
        // ������������
        nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel()
            .getBillModel();
        m_alLocatorDataBackup = m_alLocatorData;
        m_alSerialDataBackup = m_alSerialData;
        m_alLocatorData = new ArrayList();
        m_alSerialData = new ArrayList();

        for (int row = 0; row < iRowCount; row++) {
          // ������״̬Ϊ����
          if (bmTemp != null)
            bmTemp.setRowState(row, nc.ui.pub.bill.BillModel.ADD);
          m_alLocatorData.add(null);
          m_alSerialData.add(null);
        }
      }
    } catch (Exception e) {
      SCMEnv.out(e.getMessage());
    } finally {
      getBillCardPanel().getBillModel().addTableModelListener(this);
      getBillCardPanel().addBillEditListenerHeadTail(this);
    }
  }

  /**
   * �����ߣ����˾� ���ܣ����ñ���ĺϼ��� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void setTotalCol() {
    getBillCardPanel().setTatolRowShow(true);
    // getBillListPanel().set
    // ���ǵ�����ģ���ڲ���Ч�ʣ������ó���Ч�ʸ���Щ����Ϊ���������ϴ��ù�ϣ��ʵ��.
    nc.ui.pub.bill.BillItem[] biaCardBody = getBillCardPanel()
        .getBodyItems();
    // nc.ui.pub.bill.BillItem[] biaListBody =
    // getBillCardPanel().getBodyItems();
    // [itemkey,i]
    Hashtable htCardBody = new Hashtable();
    for (int i = 0; i < biaCardBody.length; i++)
      htCardBody.put(biaCardBody[i].getKey(), new Integer(i));
    // //[itemkey,i]
    // Hashtable htListBody = new Hashtable();
    // for (int i = 0; i < biaCardBody.length; i++)
    // htListBody.put(biaListBody[i].getKey(), new Integer(i));

    // ������
    String[] saBodyTotalItemKey = { "nmny", "nplannedmny", "nshouldinnum",
        "nshouldoutnum", "ntranoutnum", "nretnum", "noutnum",
        "nleftnum", "ninnum", "ninassistnum", "nleftastnum",
        "nneedinassistnum", "noutassistnum", "nretastnum",
        "nshouldoutassistnum", "ntranoutastnum", "volume", "weight" };
    for (int k = 0; k < saBodyTotalItemKey.length; k++) {
      // ����Ǵ���
      if (htCardBody.containsKey(saBodyTotalItemKey[k]))
        biaCardBody[Integer.valueOf(
            htCardBody.get(saBodyTotalItemKey[k]).toString())
            .intValue()].setTatol(true);
      // //����Ǵ���
      // if (htListBody.containsKey(saBodyTotalItemKey[k]))
      // biaListBody[Integer
      // .valueOf(htListBody.get(saBodyTotalItemKey[k]).toString())
      // .intValue()]
      // .setTatol(true);
    }

  }

  /**
   * �����ߣ����˾� ���ܣ����ص���ʾ��ʾ��Ϣ�Ի����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void showErrorMessage(String sMsg) {
    if (m_bUserDefaultErrDlg)
      super.showErrorMessage(sMsg);
    else
      nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("SCMCOMMON",
              "UPPSCMCommon-000270")/* @res "��ʾ" */, sMsg);

  }

  /**
   * �����ߣ����˾� ���ܣ����ص���ʾ��ʾ��Ϣ�Ի����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void showWarningMessage(String sMsg) {
    if (m_bUserDefaultErrDlg){
      // added by lirr 2009-11-5����03:35:29
      showHintMessage(sMsg);
      super.showWarningMessage(sMsg);

    }
    else
      nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("SCMCOMMON",
              "UPPSCMCommon-000270")/* @res "��ʾ" */, sMsg);

  }

  /**
   * �����ߣ����� ���ܣ�����ָ����ҵ������Ϊѡ��ʽ�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void showSelBizType(ButtonObject bo) {
    ButtonObject[] boaAll = getButtonManager().getButton(
        ICButtonConst.BTN_BUSINESS_TYPE).getChildButtonGroup();
    if (boaAll != null) {
      for (int i = 0; i < boaAll.length; i++)
        if (bo.equals(boaAll[i])) {
          boaAll[i].setSelected(true);
          break;
        }
    }

  }

  /**
   * �����ߣ����˾� ���ܣ���ʾ���ĵ�ʱ�� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void showTime(long lStartTime, String sTaskHint) {
    long lTime = System.currentTimeMillis() - lStartTime;
    SCMEnv.out("ִ��<"/*-=notranslate=-*/ + sTaskHint + ">���ĵ�ʱ��Ϊ��"/*-=notranslate=-*/ + (lTime / 60000) + "��"/*-=notranslate=-*/
        + ((lTime / 1000) % 60) + "��"/*-=notranslate=-*/ + (lTime % 1000) + "����");/*-=notranslate=-*/

  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-4-27 13:21:10) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param time
   *            long
   */
  public void writeTimeLog(long timebegin, java.util.Date dbegin, String sHint) {
    // time
    long end = System.currentTimeMillis();
    java.util.Date dend = new java.util.Date(end);
    SCMEnv.out(end - timebegin);
    java.io.FileOutputStream out = null;
    try {
      out = new java.io.FileOutputStream("c://time.log", true);

      out
          .write(("\n" + sHint + dbegin.toString() + "-"
              + dend.toString() + new String(new Long(end
              - timebegin).toString())).getBytes());
      out.close();
    } catch (Exception e) {
      SCMEnv.out(e.getMessage());
    }
    // end time
  }

  /**
   * ClientUI ������ע�⡣ nc 2.2 �ṩ�ĵ������鹦�ܹ����ӡ�
   * 
   */
  public GeneralBillClientUI(String pk_corp, String billType,
      String businessType, String operator, String billID) {
    super();
    // �鵥��
    GeneralBillVO voBill = qryBill(pk_corp, billType, businessType,
        operator, billID, null);
    // ��ʼ������
    setM_alListData(new ArrayList<GeneralBillVO>());

    getM_alListData().add(voBill);

    if (voBill == null)
      nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("SCMCOMMON",
              "UPPSCMCommon-000270")/* @res "��ʾ" */,
          nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
              "UPP4008bill-000062")/* @res "û�з��ϲ�ѯ�����ĵ��ݣ�" */);
    else {
      getEnvironment().setCorpID(voBill.getHeaderVO().getPk_corp());
      getEnvironment().setUserID(operator);
      getEnvironment().setUserName("jc");
      getEnvironment().setGroupID("0001");
      getEnvironment().setLogDate("2003-04-17");

      initialize(businessType);

      // ͨ�����ݹ�ʽ������ִ���йع�ʽ�����ķ���
      setListHeadData(new GeneralBillHeaderVO[] { voBill.getHeaderVO() });

      appendLocator(voBill);

      // ��ʾ����
      setBillVO(voBill);
    }

  }

  /**
   * �����ִ�������Panel
   * 
   * @param iRow
   *            �޸��ˣ������� �޸����ڣ�2007-10-23����03:07:03
   *            �޸�ԭ�򣺻�ȡ�ִ�������Panelʱ�������һ������༭������ʾ������
   */
  public QueryOnHandInfoPanel getPnlQueryOnHandPnl() {

    if (m_pnlQueryOnHand == null) {
      /*
       * m_pnlQueryOnHand = new
       * QueryOnHandInfoPanel(getEnvironment().getUserID(),
       * getEnvironment().getCorpID(), getOnHandRefDeal(), true,
       * getOnHandRefDeal(), true, true);
       */
      m_pnlQueryOnHand = new QueryOnHandInfoPanel(getEnvironment()
          .getUserID(), getEnvironment().getCorpID(),
          getOnHandRefDeal(), true, getOnHandRefDeal(), true, true,
          m_utfBarCode);
    } else {
      m_pnlQueryOnHand.getPnlBarCode().add(
          m_pnlQueryOnHand.getBarCodeInputTxt());
    }

    return m_pnlQueryOnHand;
  }

  public OnHandRefCtrl getOnHandRefDeal() {
    if (m_onHandRefDeal == null) {
      m_onHandRefDeal = new OnHandRefCtrl(this);
    }
    return m_onHandRefDeal;
  }

  /**
   * ����ɨ��Panel,������m_bAddBarcodeFieldΪtrueʱ��ʾ �޸��ˣ������� �޸����ڣ�2007-10-23����03:07:50
   * �޸�ԭ���ڻ�ȡ����ɨ��Panelʱ�������һ������༭������ʾ
   * 
   * @return
   */
  protected UIPanel getPnlBc() {
    if (m_pnlBarCode == null) {
      m_pnlBarCode = new UIPanel();
      try {

        UILabel lbName = new UILabel(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000063")/*
                                     * @res
                                     * "������������: "
                                     */);
        lbName.setPreferredSize(new java.awt.Dimension(80, 22));

        m_pnlBarCode.setLayout(new java.awt.FlowLayout());
        ((java.awt.FlowLayout) m_pnlBarCode.getLayout()).setHgap(20);
        ((java.awt.FlowLayout) m_pnlBarCode.getLayout())
            .setAlignment(java.awt.FlowLayout.LEFT);

        m_utfBarCode = new nc.ui.ic.pub.barcode.UIBarCodeTextFieldNew();

        m_utfBarCode.addBarCodeInputListener(this, getEnvironment()
            .getCorpID());
        m_utfBarCode.setPreferredSize(new java.awt.Dimension(300, 22));
        m_utfBarCode.setMaxLength(100);
        m_utfBarCode.setMaxLength(300);

        m_pnlBarCode.add(lbName);
        m_pnlBarCode.add(m_utfBarCode);

      } catch (java.lang.Throwable ivjExc) {
        handleException(ivjExc);
      }
    } else
      m_pnlBarCode.add(m_utfBarCode);
    if (m_bAddBarCodeField == false) {
      m_pnlBarCode.setVisible(false);
    }
    return m_pnlBarCode;
  }

  /**
   * � ���ܣ����ⵥΪ��Դ���ݵ��������뵥�������ɱ༭�����������ܴ���Ӧ�շ����� ������ ���أ� /* 1.�����༭�����������༭�� 11
   * �õ�Ӧ�շ�������NULL��Ϊ0 12 �õ�ʵ���շ��������Ƚ��������������ʵ��������ʵ�ʸ��������� ���ʵ�ʸ���������Ӧ�շ��������� ��ʾ
   * 
   * ���⣺ ���ڣ�(2005-1-28 14:27:22) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void afterNumEditFromSpe(nc.ui.pub.bill.BillEditEvent e) {

    try {
      int iRow = e.getRow();
      // ��Դ���ݿ��ƣ�
      String csourcetype = (String) getM_voBill().getItemValue(iRow,
          "csourcetype");

      if (csourcetype != null
          && (csourcetype.equals(BillTypeConst.m_assembly)
              || csourcetype.equals(BillTypeConst.m_disassembly)
              || csourcetype.equals(BillTypeConst.m_transform) || csourcetype
              .equals(BillTypeConst.m_check))) {

        UFDouble dbNum = null;
        UFDouble dbShouldNum = null;
        if (e.getKey().equals(getEnvironment().getNumItemKey())) {
          if (e.getValue() == null)
            return;
          dbNum = new UFDouble(e.getValue().toString());
          dbShouldNum = (UFDouble) getM_voBill().getItemValue(iRow,
              getEnvironment().getShouldNumItemKey());
          if (dbShouldNum == null)
            return;
          if (dbNum.toDouble().doubleValue() > dbShouldNum.toDouble()
              .doubleValue()) {
            showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
                .getStrByID("4008bill", "UPP4008bill-000064")/*
                                         * @res
                                         * "ʵ���������ܴ���Ӧ�շ�������"
                                         */);
            getBillCardPanel().setBodyValueAt(null, iRow,
                getEnvironment().getNumItemKey());
            getBillCardPanel().setBodyValueAt(null, iRow,
                getEnvironment().getAssistNumItemKey());
            return;
          }

        }

        if (e.getKey().equals(getEnvironment().getAssistNumItemKey())) {
          if (e.getValue() == null)
            return;
          dbNum = new UFDouble(e.getValue().toString());
          dbShouldNum = (UFDouble) getM_voBill().getItemValue(iRow,
              getEnvironment().getShouldAssistNumItemKey());
          if (dbShouldNum == null)
            return;
          if (dbNum.toDouble().doubleValue() > dbShouldNum.toDouble()
              .doubleValue()) {
            showErrorMessage(nc.ui.ml.NCLangRes.getInstance()
                .getStrByID("4008bill", "UPP4008bill-000064")/*
                                         * @res
                                         * "ʵ���������ܴ���Ӧ�շ�������"
                                         */);
            getBillCardPanel().setBodyValueAt(null, iRow,
                getEnvironment().getAssistNumItemKey());
            getBillCardPanel().setBodyValueAt(null, iRow,
                getEnvironment().getAssistNumItemKey());
            return;
          }
        }
      }
    } catch (Exception ex) {
      nc.vo.scm.pub.SCMEnv.error(ex);

    }

  }

  /**
   * �����ߣ����˾� ���ܣ�����¼����� ������e���ݱ༭�¼� ���أ� ���⣺ ���ڣ�(2001-5-8 19:08:05)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void afterInvMutiEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterInvMutiEdit(e);

  }

  /**
   * ����¼��: v5֧�ּ��вɹ�, �˷������ɹ���⸲��
   * 
   * @param istartrow
   *            ���п�ʼ�ֶ�
   * @param count
   *            ��������
   */
  public void setBodyDefaultData(int istartrow, int count) {

  }

  /**
   * 
   */
  public void setCardMode() {
    // ����ִ�����ʾ��������ʾ״̬����ǰ��Ƭ״̬����ΪMultiCardMode.CARD_TAB
    if (m_bOnhandShowHidden) {
      m_sMultiMode = MultiCardMode.CARD_TAB;
    } else {
      m_sMultiMode = MultiCardMode.CARD_PURE;
    }
  }

  /**
   * ��ʾ�ִ������յ�����
   * 
   * @param iRow
   */
  protected void showOnHandPnlInfo(int iRow) {
    if (m_pnlQueryOnHand == null || iRow < 0)
      return;

    m_pnlQueryOnHand.setVORefresh(getOnHandRefDeal()
        .getSelectedItemHandInfo(iRow));

    m_pnlQueryOnHand.showData();

  }

  /**
   * 
   * ��������������<br>
   * ���ҵ��ݼ����еĴ�U8RM�д���ĵ��ݺ��ַ�����<br>
   * ������ڴ�U8RM�д���ĵ��ݣ����᷵�ص��ݺ��б����򣬷��ؿ��ַ���
   * <p>
   * 
   * @return ���ݺ��б��ַ���
   *         <p>
   * @author duy
   * @time 2007-4-18 ����10:14:51
   * 
   */
  protected String getU8RMBillCodes(java.util.ArrayList<GeneralBillVO> bills) {
    StringBuffer billCodes = new StringBuffer();
    for (GeneralBillVO bill : bills) {
      // �޸��ˣ������� �޸����ڣ�2007-11-29����10:11:23 �޸�ԭ��
      if (null == bill)
        continue;
      GeneralBillItemVO[] itemVOs = bill.getItemVOs();
      for (GeneralBillItemVO itemVO : itemVOs)
        if (itemVO.getVbilltypeu8rm() != null
            && itemVO.getVbilltypeu8rm().length() > 0) {
          billCodes.append("\n");
          billCodes.append(bill.getVBillCode());
          break;
        }
    }
    String billCodesStr = billCodes.toString();
    if (billCodesStr != null && billCodesStr.length() > 0) {
      billCodesStr = billCodesStr.substring(1);
    }
    return billCodesStr;
  }

  /**
   * �����ߣ����˾� ���ܣ�ɾ������ ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public String checkBillsCanDeleted(ArrayList alBills) {
    String sError = null;
    if (alBills != null) {
      {
        // ��ѯ���ݼ����еĴ�U8�����д���ĵ��ݱ�����ַ���
        // ������ڴ�U8�����д���ĵ��ݣ�������ɾ��
        String billCodes = getU8RMBillCodes(alBills);
        if (billCodes != null && billCodes.length() > 0) {
          String message1 = nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008U8RM-000001", null,
                  new String[] { billCodes });
          /*
           * @res "���ݺ���{0}�ĵ�������U8�����ϴ����Զ����ɵģ�����ִ�иò�����"
           */
          sError = "(" + message1 + ")";
          return sError;
        }

        GeneralBillVO gvo = null;
        String sSourceBillType = null;
        for (int i = 0; i < alBills.size(); i++) {
          gvo = (GeneralBillVO) alBills.get(i);
          sSourceBillType = (String) gvo.getItemValue(0,
              "csourcetype");
          /** �жϵ��������Ƿ�Ϊ�ⲿ���� */
          // û����Դ���ݣ��������״̬�£����ƿ���
          IBillType billType = BillTypeFactory.getInstance().getBillType(sSourceBillType);
          if (sSourceBillType != null
              && billType.typeOf(ModuleCode.IC)) {
            // �ڲ����ɵ���
            /** �жϵ������ͷ�Ϊת�ⵥ */
            if (sSourceBillType != null
                && (sSourceBillType
                    .equals(BillTypeConst.m_transfer)
                    || sSourceBillType
                        .equals(BillTypeConst.m_assembly)
                    || sSourceBillType
                        .equals(BillTypeConst.m_disassembly)
                    || sSourceBillType
                        .equals(BillTypeConst.m_transform)
                    || sSourceBillType
                        .equals(BillTypeConst.m_check) || sSourceBillType
                    .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {

              /**
               * �����������״̬ʱ�����ư�ť������ ����װ����ж����̬ת�����̵����ⵥ�����ɵ������롢
               * ����������ֱ��ɾ������ɾ��ʱ��ʾ�û�ͨ���������ⵥʵ�֡����ԣ���ɾ����ť�ûҡ�
               */

              String[] args = new String[1];
              args[0] = gvo.getHeaderValue("vbillcode")
                  .toString();
              String message = nc.ui.ml.NCLangRes.getInstance()
                  .getStrByID("4008bill",
                      "UPP4008bill-000340", null, args);
              /*
               * @res
               * "���ݺ��ǣ�{0}�ĵ���������װ����ж����̬ת�����̵����ⵥ�����ɵ�������,����������ֱ��ɾ����ͨ���������ⵥʵ��!"
               */

              if (!(sSourceBillType
                  .equals(nc.vo.ic.pub.BillTypeConst.m_transfer) || sSourceBillType
                  .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {
                sError += "(" + message + ")";
              }

            } else if (sSourceBillType != null
                && (sSourceBillType
                    .equals(BillTypeConst.m_saleOut) || sSourceBillType
                    .equals(BillTypeConst.m_purchaseIn))) {
              // ���۳��ⵥ���ɹ���ⵥ�������ɵ���������������ⵥ�ݲ���ɾ���޸ĵ��ݣ�����
              String[] args1 = new String[1];
              args1[0] = gvo.getHeaderValue("vbillcode")
                  .toString();
              String message1 = nc.ui.ml.NCLangRes.getInstance()
                  .getStrByID("4008bill",
                      "UPP4008bill-000341", null, args1);
              /*
               * @res
               * "���ݺ��ǣ�{0}�ĵ����������۳��ⵥ���ɹ���ⵥ�������ɵ���������������ⵥ�ݲ���ɾ���޸ĵ���!"
               */
              sError += "(" + message1 + ")";
            }

          }
        }
      }

    }
    return sError;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2003-10-09 16:34:45)
   */
  protected void checkVMIWh(GeneralBillVO voBill) throws Exception {

    VOCheck.checkVMIWh(new QueryInfo(), voBill);

  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2003-11-7 15:13:21)
   * 
   * @param row
   *            int
   */
  public void clearRow(int row) {
    clearRowData(row);
    // ��Ӧ����
    // ͬ��vo
    if (getM_voBill() != null) {
      getM_voBill().setItemValue(row,
          getEnvironment().getShouldNumItemKey(), null);
      getM_voBill().setItemValue(row,
          getEnvironment().getShouldAssistNumItemKey(), null);
    }
    if (getBillCardPanel().getBodyItem(
        getEnvironment().getShouldNumItemKey()) != null)
      getBillCardPanel().setBodyValueAt(null, row,
          getEnvironment().getShouldNumItemKey());
    if (getBillCardPanel().getBodyItem(
        getEnvironment().getShouldAssistNumItemKey()) != null)
      getBillCardPanel().setBodyValueAt(null, row,
          getEnvironment().getShouldAssistNumItemKey());
    // ��β
    setTailValue(null);
  }

  /**
   * ��Դ������ת�ⵥʱ�Ľ�����Ʒ��� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-19 09:43:22)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void ctrlSourceBillButtons(boolean bUpdateButtons) {
    String sSourceBillType = getSourBillTypeCode();
    IBillType billType = BillTypeFactory.getInstance().getBillType(sSourceBillType);
    /** �жϵ��������Ƿ�Ϊ�ⲿ���� */
    // û����Դ���ݣ��������״̬�£����ƿ���
    if ((sSourceBillType == null || sSourceBillType.trim().length() == 0)) {
      if (getM_iMode() == BillMode.Browse && m_iBillQty > 0
          && getM_iLastSelListHeadRow() >= 0) {
        // ���״̬�¿��Ը��Ƶ���
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(true);
      }
    } else if (!billType.typeOf(ModuleCode.IC)) {
      // �ⲿ���ɵĵ���,���Ѿ��������״���

      // �޸ġ�����״̬�¡�
      if (getM_iMode() == BillMode.Update || getM_iMode() == BillMode.New) {

        /** �õ����У������Ҽ���ť,���У�������Ϊ������,���ƣ�ճ������ */

        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
            .setEnabled(false);

        // getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO).setEnabled(false);
        // �����Ҽ�������
        getBillCardPanel().getBodyMenuItems()[0].setEnabled(false);
        // �����Ҽ�������
        getBillCardPanel().getBodyMenuItems()[2].setEnabled(false);
        // �����Ҽ�ɾ���У�ǿ����Ϊtrue; ��Bug�����Ѳ������ûң�����ǰ���ɾ����Ҳ���ûҡ�
        getBillCardPanel().getBodyMenuItems()[1].setEnabled(true);

        // modified by 2008-01-07 ΪʲôҪ���ó�false��ע֮��
        // if (null !=
        // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index])
        // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index].setEnabled(false);

      } else{
        // ���״̬��ֻ�ǲ��ø��Ƶ���
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false); 
        
        // �޸��ˣ������� ����10:53:36_2009-10-17 �޸�ԭ�� ת���˿ⲻ���ò�����ת�����ⵥ
        if(m_bRefBills){
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_MANUAL_RETURN)
              .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_PO_RETURN)
              .setEnabled(false);
        }

      
      }
      // boolean bisdispensebill = isDispensedBill(null);
      // if (bisdispensebill) {
      // getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE).setEnabled(false);
      // getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY).setEnabled(false);
      // getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT).setEnabled(false);

      // }
    } else {
      // �ڲ����ɵ���
      /** �жϵ������ͷ�Ϊת�ⵥ */
      if (sSourceBillType != null
          && (sSourceBillType.equals(BillTypeConst.m_transfer)
              || sSourceBillType.equals(BillTypeConst.m_assembly)
              || sSourceBillType
                  .equals(BillTypeConst.m_disassembly)
              || sSourceBillType
                  .equals(BillTypeConst.m_transform)
              || sSourceBillType.equals(BillTypeConst.m_check) || sSourceBillType
              .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {
        if (getM_iMode() == BillMode.Update
            || getM_iMode() == BillMode.New) {
          /** ���������������޸�״̬ʱ�Ľ�����Ϊ���ơ� */
          /** �õ����У������Ҽ���ť,ɾ�У����У�������Ϊ������,���ƣ�ճ������ */

          /** �ò˵���ť�Ŀ���״̬ */
          getButtonManager().getButton(ICButtonConst.BTN_LINE)
              .setEnabled(false);
          /** �ñ����Ҽ��˵���ť�Ŀ���״̬ */
          getBillCardPanel().setBodyMenuShow(false);

        }
        /**
         * �����������״̬ʱ�����ư�ť������ ����װ����ж����̬ת�����̵����ⵥ�����ɵ������롢
         * ����������ֱ��ɾ������ɾ��ʱ��ʾ�û�ͨ���������ⵥʵ�֡����ԣ���ɾ����ť�ûҡ�
         */
        else {
          if (!(sSourceBillType
              .equals(nc.vo.ic.pub.BillTypeConst.m_transfer) || sSourceBillType
              .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {
            getButtonManager().getButton(
                ICButtonConst.BTN_BILL_DELETE)
                .setEnabled(false);
          } else {
            // ǩ�ֵĵ��ݲ���ɾ����
            if (CANNOTSIGN == isSigned() || NOTSIGNED == isSigned())
              getButtonManager().getButton(
                  ICButtonConst.BTN_BILL_DELETE).setEnabled(
                  true);
          }
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
        }

      } else if (sSourceBillType != null
          && (sSourceBillType.equals(BillTypeConst.m_saleOut) || sSourceBillType
              .equals(BillTypeConst.m_purchaseIn))) {
        // ���۳��ⵥ���ɹ���ⵥ�������ɵ���������������ⵥ�ݲ���ɾ���޸ĵ��ݣ�����
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(false);

      }
      // �����������������Ĳ�������ǡ�ϵͳ�������ģ���ô���ܸ��ƣ�ɾ�����޸�
      Object oDesatype = null;
      if (BillMode.List == getM_iCurPanel()) {
        if (getM_alListData() != null
            && getM_iLastSelListHeadRow() >= 0) {
          GeneralBillVO voBill = (GeneralBillVO) getM_alListData()
              .get(getM_iLastSelListHeadRow());
          oDesatype = voBill.getItemValue(0, "idesatype");
        }
      } else if (BillMode.Card == getM_iCurPanel()) {
        if (getM_voBill() != null && getM_voBill().getItemVOs() != null
            && getM_voBill().getItemVOs().length > 0) {
          oDesatype = getM_voBill().getItemValue(0, "idesatype");
        }

      }
      if (oDesatype != null) {
        Integer idesatype = new Integer(oDesatype.toString().trim());
        if (idesatype.intValue() == nc.vo.ic.pub.DesassemblyVO.TYPE_SYS) {
          getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_SIGN)
              .setEnabled(false);
        }
      }

    }
    // //�Ѿ��������׵ĵ��ݲ��ܱ�ɾ�����޸ĺ͸��ơ�
    boolean bisdispensebill = isDispensedBill(null);
    if (bisdispensebill) {
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);

    }

    // ###########################
    // ���� add by hanwei 2004-05-14
    // 1��������ⵥ�����ֱ�ӵ�ת��־����Ҫ�ڽ������ �޸ġ�ɾ�������ƵȰ�ť�����ã�
    // 2��������ݵĲֿ���ֱ�˲֣�Ӧ�ÿ����޸ġ�ɾ�������ƵȰ�ť�����ã�
    // ����true:ֻ����false������
    setBtnStatusTranflag(true);
    // #############################

    // ������õ�ˢ�½���Ĳ˵���ť
    if (bUpdateButtons)
      updateButtons();

    // v5 lj
    if (m_bRefBills == true) {
      setRefBtnStatus();
    }
  }

  /**
   * ��Դ������ת�ⵥʱ�Ľ�����Ʒ��� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-19 09:43:22)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��@
   * 
   */
  protected void ctrlSourceBillUI(boolean bUpdateButtons) {
    try {
      String sSourceBillType = getSourBillTypeCode();
      IBillType billType = BillTypeFactory.getInstance().getBillType(sSourceBillType);
      /** �жϵ��������Ƿ�Ϊ�ⲿ���� */
      // û����Դ���ݣ��������״̬�£����ƿ���
      if ((sSourceBillType == null || sSourceBillType.trim().length() == 0)) {
        if (getM_iMode() == BillMode.Browse&& m_iBillQty > 0
            && getM_iLastSelListHeadRow() >= 0) {
          // ���״̬�¿��Ը��Ƶ���
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(true);
        }
      } else if (!billType.typeOf(ModuleCode.IC)) {
        // �ⲿ���ɵĵ���

        // �޸ġ�����״̬�¡�
        if (getM_iMode() == BillMode.Update
            || getM_iMode() == BillMode.New) {

          /** �ñ�ͷ���ɱ༭�� */
          String[] saNotEditableHeadKey = {
              "cbiztype", // wnj:2002-10-23.seg01
              // "cdptid",
              // //"cbizid",
              "ccustomerid", "cproviderid", "alloctypename",
              "freplenishflag", IItemKey.boutretflag };

          for (int i = 0; i < saNotEditableHeadKey.length; i++) {
            if (getBillCardPanel().getBillData().getHeadItem(
                saNotEditableHeadKey[i]) != null)
              getBillCardPanel().getBillData().getHeadItem(
                  saNotEditableHeadKey[i]).setEdit(false);

          }

          /** �õ����У������Ҽ���ť,���У�������Ϊ������,���ƣ�ճ������ */

          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
              .setEnabled(false);

          // getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO).setEnabled(false);
          // �����Ҽ�������
          getBillCardPanel().getBodyMenuItems()[0].setEnabled(false);
          // �����Ҽ�������
          getBillCardPanel().getBodyMenuItems()[2].setEnabled(false);
          // �����Ҽ�ɾ���У�ǿ����Ϊtrue; ��Bug�����Ѳ������ûң�����ǰ���ɾ����Ҳ���ûҡ�
          getBillCardPanel().getBodyMenuItems()[1].setEnabled(true);

          // modified by 2008-01-07 ΪʲôҪ���ó�false��ע֮��
          // if (null !=
          // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index])
          // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index].setEnabled(false);
        } else {

          // ���״̬��ֻ�ǲ��ø��Ƶ���
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
        }

      }
      else {
        // �ڲ����ɵ���,���ⵥ�ݣ����۳��⣬�ɹ������������ɵ������룬����
        /** �жϵ������ͷ�Ϊת�ⵥ */
        if (sSourceBillType != null
            && (sSourceBillType.equals(BillTypeConst.m_transfer)
                || sSourceBillType
                    .equals(BillTypeConst.m_assembly)
                || sSourceBillType
                    .equals(BillTypeConst.m_disassembly)
                || sSourceBillType
                    .equals(BillTypeConst.m_transform)
                || sSourceBillType
                    .equals(BillTypeConst.m_check)
                || sSourceBillType
                    .equals(BillTypeConst.m_saleOut) || sSourceBillType
                .equals(BillTypeConst.m_purchaseIn))) {
          if (getM_iMode() == BillMode.Update
              || getM_iMode() == BillMode.New) {
            /** ���������������޸�״̬ʱ�Ľ�����Ϊ���ơ� */
            /** �õ����У������Ҽ���ť,ɾ�У����У�������Ϊ������,���ƣ�ճ������ */

            /** �ò˵���ť�Ŀ���״̬ */
            getButtonManager().getButton(ICButtonConst.BTN_LINE)
                .setEnabled(false);
            /** �ñ����Ҽ��˵���ť�Ŀ���״̬ */
            getBillCardPanel().setBodyMenuShow(false);
            String sHeadItemKey = null;
            // �������۳��ⵥ���ɵ��������ⵥ����ͷ�ͻ����ɱ༭
            if (sSourceBillType.equals(BillTypeConst.m_saleOut)
                && getBillType().equals(
                    BillTypeConst.m_otherOut)) {
              sHeadItemKey = "ccustomerid";
            }
            // ���ײɹ���ⵥ���ɵ�������ⵥ����ͷ�ͻ����ɱ༭
            else if (sSourceBillType
                .equals(BillTypeConst.m_purchaseIn)
                && getBillType()
                    .equals(BillTypeConst.m_otherIn)) {
              sHeadItemKey = "cproviderid";
            }

            /** �ñ�ͷ���ɱ༭�� */
            String[] saNotEditableHeadKey2 = null;
            if (sHeadItemKey == null)
              saNotEditableHeadKey2 = new String[] {
                  IItemKey.WAREHOUSE,IItemKey.COTHERWHID, IItemKey.CALBODY,
                  IItemKey.WASTEWAREHOUSE,
                  IItemKey.WASTECALBODY };
            else
              saNotEditableHeadKey2 = new String[] {
                  IItemKey.WAREHOUSE,IItemKey.COTHERWHID, IItemKey.CALBODY,
                  IItemKey.WASTEWAREHOUSE,
                  IItemKey.WASTECALBODY, sHeadItemKey };

            for (int i = 0; i < saNotEditableHeadKey2.length; i++) {
              if (getBillCardPanel().getBillData().getHeadItem(
                  saNotEditableHeadKey2[i]) != null)
                getBillCardPanel().getBillData().getHeadItem(
                    saNotEditableHeadKey2[i]).setEnabled(
                    false);

            }

          }
          /**
           * �����������״̬ʱ�����ư�ť������ ����װ����ж����̬ת�����̵����ⵥ�����ɵ������롢
           * ����������ֱ��ɾ������ɾ��ʱ��ʾ�û�ͨ���������ⵥʵ�֡����ԣ���ɾ����ť�ûҡ�
           */
          else {
            if (!(sSourceBillType
                .equals(nc.vo.ic.pub.BillTypeConst.m_transfer) || sSourceBillType
                .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {
              getButtonManager().getButton(
                  ICButtonConst.BTN_BILL_DELETE).setEnabled(
                  false);
            } else {
              // ǩ�ֵĵ��ݲ���ɾ����
              int iIsSigned = isSigned();
              if (iIsSigned != SIGNED)
                getButtonManager().getButton(
                    ICButtonConst.BTN_BILL_DELETE)
                    .setEnabled(true);

            }
            getButtonManager().getButton(
                ICButtonConst.BTN_BILL_COPY).setEnabled(false);
          }
        } else if (sSourceBillType != null
            && (sSourceBillType.equals(BillTypeConst.m_saleOut) || sSourceBillType
                .equals(BillTypeConst.m_purchaseIn))) {
          // ���۳��ⵥ,�ɹ���⵫�������ɵ���������������ⵥ�ݲ���ɾ���޸ĵ��ݣ�����
          getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
              .setEnabled(false);

        } else if (sSourceBillType != null
            && (sSourceBillType
                .equals(BillTypeConst.m_AllocationOrder))) {
          // ��Դ�����ǵ��������Ľ�����ƣ�

          /** ���������������޸�״̬ʱ�Ľ�����Ϊ���ơ� */
          /** �õ����У������Ҽ���ť,ɾ�У����У�������Ϊ������,���ƣ�ճ������ */

          /** �ò˵���ť�Ŀ���״̬ */
          getButtonManager().getButton(ICButtonConst.BTN_LINE)
              .setEnabled(false);
          /** �ñ����Ҽ��˵���ť�Ŀ���״̬ */
          getBillCardPanel().setBodyMenuShow(false);

          /** �ñ�ͷ�����֯���ɱ༭�� */
          String saNotEditableHeadKey2 = IItemKey.CALBODY;

          if (getBillCardPanel().getBillData().getHeadItem(
              saNotEditableHeadKey2) != null)
            getBillCardPanel().getBillData().getHeadItem(
                saNotEditableHeadKey2).setEnabled(false);

        }

      } // ������õ�ˢ�½���Ĳ˵���ť

      // ###########################
      // ���� add by hanwei 2004-05-14
      // 1��������ⵥ�����ֱ�ӵ�ת��־����Ҫ�ڽ������ �޸ġ�ɾ�������ƵȰ�ť�����ã�
      // 2��������ݵĲֿ���ֱ�˲֣�Ӧ�ÿ����޸ġ�ɾ�������ƵȰ�ť�����ã�
      // ����true:ֻ����false������
      setBtnStatusTranflag(true);
      // #############################

      if (bUpdateButtons)
        updateButtons();

      // v5 lj
      if (m_bRefBills == true) {
        setRefBtnStatus();
      }

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }

  /**
   * �˴����뷽��˵���� ��������:�����ѯʱû�в鵽���ݵ�����½���ġ� ���ߣ������� �������: ����ֵ: �쳣����: ����:(2003-6-9
   * 15:57:49)
   */
  protected void dealNoData() {
    // ���õ�ǰ�ĵ�������/��ţ����ڰ�ť����
    setLastHeadRow(-1);
    // ��ʼ����ǰ������ţ��л�ʱ�õ���
    m_iCurDispBillNum = -1;
    // ��ǰ������
    m_iBillQty = 0;
    setM_alListData(null);
    clearUi();
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
        "UPP4008bill-000013")/* @res "δ�鵽���������ĵ��ݡ�" */);
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2002-11-27 10:41:27)
   * 
   * @param error
   *            java.lang.String
   */
  public void errormessageshow(java.lang.String error) {
    // ��ʾ������Ϣ
    java.awt.Toolkit.getDefaultToolkit().beep();
    showErrorMessage(error);
    // ���ر༭��
    m_utfBarCode.requestFocus();
  }

  /**
   * ���ù�ʽ ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-12 16:47:04) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  private String execFormular(String formula, String value) {
    nc.ui.pub.formulaparse.FormulaParse f = new nc.ui.pub.formulaparse.FormulaParse();

    if (formula != null && !formula.equals("")) {
      // ���ñ��ʽ
      f.setExpress(formula);
      // ��ñ���
      nc.vo.pub.formulaset.VarryVO varry = f.getVarry();
      // ��������ֵ
      Hashtable h = new Hashtable();
      for (int j = 0; j < varry.getVarry().length; j++) {
        String key = varry.getVarry()[j];

        String[] vs = new String[1];
        vs[0] = value;
        h.put(key, StringUtil.toString(vs));
      }

      f.setDataS(h);
      // ���ý��
      if (varry.getFormulaName() != null
          && !varry.getFormulaName().trim().equals(""))
        return f.getValueS()[0];
      else
        return f.getValueS()[0];

    } else {
      return null;
    }
  }

  /**
   * �˴����뷽��˵���� ����������ر�����,ʵ�ַ�Ʒ����ճ�ʼ�� RefFilter.filtWasteWh(billItem, sCorpID,
   * null);
   * 
   * �������ڣ�(2004-3-19 17:41:50)
   */
  public void filterRefofWareshouse(nc.ui.pub.bill.BillItem billItem,
      String sCorpID) {

    RefFilter.filtWh(billItem, sCorpID, null);

  }

  /**
   * �˴����뷽��˵���� ���Ȩ����֤UI �������ڣ�(2004-4-19 14:11:06)
   * 
   * @return nc.ui.scm.pub.AccreditLoginDialog
   */
  public nc.ui.scm.pub.AccreditLoginDialog getAccreditLoginDialog() {
    if (m_AccreditLoginDialog == null)
      m_AccreditLoginDialog = new AccreditLoginDialog();
    return m_AccreditLoginDialog;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2004-5-7 14:18:22)
   * 
   * @return nc.ui.ic.pub.bill.BarcodeCtrl
   */
  public BarcodeCtrl getBarcodeCtrl() {
    if (m_BarcodeCtrl == null) {
      m_BarcodeCtrl = new BarcodeCtrl();
      m_BarcodeCtrl.m_sCorpID = getEnvironment().getCorpID();
    }
    return m_BarcodeCtrl;
  }

  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-1-24 11:35:23) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return boolean
   */
  /**
   * ���� BillCardPanel1 ����ֵ��
   * 
   * @return nc.ui.pub.bill.BillCardPanel
   */
  /* ���棺�˷������������ɡ� */
  public BarCodeDlg getBarCodeDlg() {
    m_dlgBarCodeEdit = new BarCodeDlg(this, getEnvironment().getCorpID());
    return m_dlgBarCodeEdit;
  }

  /**
   * �˴����뷽��˵���� ���������Condition�ĸ��Ի��޸����� �����ط��� �������ڣ�(2003-11-25 20:58:54)
   */
  protected void getConDlginitself(QueryConditionDlgForBill queryCondition) {
  }

  /**
   * �˴����뷽��˵���� ���ܣ��õ���׼�ļ��Ի��� ��������׼�ļ��Ի��� ���أ��� ���⣺ ���ڣ�(2002-9-24 15:47:21)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public UIFileChooser getFileChooseDlg() {
    try {
      // m_InFileDialog = null;
      if (m_InFileDialog == null) {
        m_InFileDialog = new nc.ui.ic.pub.tools.FileChooserImpBarcode();
        m_InFileDialog.setDialogType(UIFileChooser.OPEN_DIALOG);
        // m_InFileDialog.setFileSelectionMode(UIFileChooser.FILES_ONLY);
        m_InFileDialog.removeChoosableFileFilter(m_InFileDialog
            .getFileFilter());
        // ��ȥ��ǰ���ļ�������
        // m_InFileDialog.addChoosableFileFilter(new
        // nc.ui.pf.export.SuffixFilter());
        // ����ļ�ѡ�������

        m_InFileDialog.setCurrentDirectory(new java.io.File("D:\\"));
        m_InFileDialog
            .setFileFilter(new javax.swing.filechooser.FileFilter() {
              public boolean accept(java.io.File f) {
                return f.isDirectory()
                    || f.getName().toLowerCase().endsWith(
                        ".xls");
              }

              public String getDescription() {
                return nc.ui.ml.NCLangRes.getInstance()
                    .getStrByID("4008bill",
                        "UPP4008bill-000495")/*
                                     * @res
                                     * "Excel�ļ�"
                                     */;
              }
            });
      }
    } catch (Exception ex) {
      nc.vo.scm.pub.SCMEnv.error(ex);
    }
    return m_InFileDialog;
  }

  /**
   * �˴����뷽��˵���� ��������:��� BillFormulaContainer ���ߣ����� �������: ����ֵ: �쳣����:
   * ����:(2003-7-2 9:48:12)
   * 
   * @return nc.ui.ic.pub.BillFormulaContainer
   */
  public BillFormulaContainer getFormulaBillContainer() {
    if (m_billFormulaContain == null) {
      m_billFormulaContain = new BillFormulaContainer(getBillListPanel());
    }
    return m_billFormulaContain;
  }

  /**
   * �˴����뷽��˵���� ��������: ���ߣ����˾� �������: ����ֵ: �쳣����: ����:(2003-6-25 20:43:17)
   * 
   * @return java.util.ArrayList
   */
  protected ArrayList getFormulaItemBody() {
    String sCusterNameFields = "custname";
    // if (m_bShowCusterShortName)
    // sCusterNameFields="custshortname";
    // else
    // sCusterNameFields="custname";

    ArrayList arylistItemField = new ArrayList();

    // ��Ŀ��������ID
    String[] aryItemField11 = new String[] { "pk_jobbasfil",
        "pk_jobbasfil", "cprojectid" };
    arylistItemField.add(aryItemField11);

    // ��Ŀ����
    String[] aryItemField12 = new String[] { "jobname", "cprojectname",
        "pk_jobbasfil" };
    arylistItemField.add(aryItemField12);

    // ��Ŀ�׶λ�������ID
    String[] aryItemField13 = new String[] { "pk_jobphase", "pk_jobphase",
        "cprojectphaseid" };
    arylistItemField.add(aryItemField13);

    // ��Ŀ�׶�����
    String[] aryItemField14 = new String[] { "jobphasename",
        "cprojectphasename", "pk_jobphase" };
    arylistItemField.add(aryItemField14);

    // ����������λ
    String[] aryItemField31 = new String[] { "measname", "castunitname",
        "castunitid" };
    arylistItemField.add(aryItemField31);

    // ���̻������� for ��Ӧ��
    String[] aryItemField7 = new String[] { "pk_cubasdoc", "pk_cubasdoc",
        "cvendorid" };
    arylistItemField.add(aryItemField7);

    // �������� for ��Ӧ��
    String[] aryItemField8 = new String[] { sCusterNameFields,
        "vvendorname", "pk_cubasdoc" };
    arylistItemField.add(aryItemField8);

    // zhy2005-09-16�������� for ��Ӧ��
    String[] aryItemField88 = new String[] { sCusterNameFields,
        "cvendorname", "pk_cubasdoc" };
    arylistItemField.add(aryItemField88);

    // ���̻������� for �ͻ�||�ջ���λ
    String[] aryItemField17 = new String[] { "pk_cubasdoc",
        "pk_cubasdocrev", "creceieveid" };
    arylistItemField.add(aryItemField17);

    // �������� for �ͻ�||�ջ���λ
    String[] aryItemField18 = new String[] { sCusterNameFields,
        "vrevcustname", "pk_cubasdocrev" };
    arylistItemField.add(aryItemField18);

//    // ��Դ��������, Ϊ������������ȥ���˴���ʽ���أ��ŵ���̨��ɣ�songhy
//    String[] aryItemField9 = new String[] { "billtypename",
//        "csourcetypename", "csourcetype" };
//    arylistItemField.add(aryItemField9);

    // �ɱ����� ��������
    // ccostobjectbasid
    String[] aryItemField34 = new String[] { "pk_invbasdoc",
        "ccostobjectbasid", "ccostobject" };
    arylistItemField.add(aryItemField34);
    // �ɱ�����
    String[] aryItemField33 = new String[] { "invname", "ccostobjectname",
        "ccostobjectbasid" };
    arylistItemField.add(aryItemField33);

//    // Դͷ���ݺ�, Ϊ������������ȥ���˴���ʽ���أ��ŵ���̨��ɣ�songhy
//    String[] aryItemField10 = new String[] { "billtypename",
//        "cfirsttypename", "cfirsttype" };
//    arylistItemField.add(aryItemField10);
    
    // //����
    String[] aryItemField19 = new String[] { "deptname", "vdeptname",
        "cdptid" };
    arylistItemField.add(aryItemField19);

    // ��λ
    String[] aryItemField20 = new String[] { "csname", "vspacename",
        "cspaceid" };
    arylistItemField.add(aryItemField20);

    // ��Ӧ��ⵥ���� ccorrespondtype
    // String[] aryItemField20 =
    // new String[] { "billtypename", "cfirsttypeName", "cfirsttype" };
    // arylistItemField.add(aryItemField20);
    return arylistItemField;
  }

  /**
   * �˴����뷽��˵���� ��������: ���ߣ����˾� �������: ����ֵ: �쳣����: ����:(2003-6-25 20:43:17)
   * 
   * @return java.util.ArrayList
   */
  protected ArrayList getFormulaItemHeader() {
    ArrayList<String[]> arylistItemField = new ArrayList<String[]>();
    // ԭ�еĹ�ʽ
    // �����֯ 1
    String[] aryItemField40 = new String[] { "bodyname", "vcalbodyname",
        "pk_calbody" };
    arylistItemField.add(aryItemField40);

    // �ⷿ����Ա 2
    String[] aryItemField3 = new String[] { "psnname", "cwhsmanagername",
        "cwhsmanagerid" };
    arylistItemField.add(aryItemField3);

    // �ֿ� 3
    String[] aryItemField15 = new String[] { "storname", "cwarehousename",
        "cwarehouseid" };
    arylistItemField.add(aryItemField15);

    // //�ֿ��Ƿ��Ƿ�ֱ�˲ֿ�
    String[] aryItemField41 = new String[] { "isdirectstore",
        "isdirectstore", "cwarehouseid" };
    arylistItemField.add(aryItemField41);

    // �ֿ��Ƿ��Ƿ��ʲ��ֿ�
    String[] aryItemField43 = new String[] { "iscapitalstor",
        "iscapitalstor", "cwarehouseid" };
    arylistItemField.add(aryItemField43);

    // �ֿ� 3
    String[] aryItemField25 = new String[] { "storname",
        "cwastewarehousename", "cwastewarehouseid" };
    arylistItemField.add(aryItemField25);

    // ������ 4
    String[] aryItemField2 = new String[] { "user_name", "cregistername",
        "cregister" };
    arylistItemField.add(aryItemField2);

    // //������ 5
    String[] aryItemField12 = new String[] { "user_name", "cauditorname",
        "cauditorid" };
    arylistItemField.add(aryItemField12);

    // //����Ա 6
    String[] aryItemField1 = new String[] { "user_name", "coperatorname",
        "coperatorid" };
    arylistItemField.add(aryItemField1);

    // ���� 7
    String[] aryItemField19 = new String[] { "deptname", "cdptname",
        "cdptid" };
    arylistItemField.add(aryItemField19);

    // ҵ��Ա 8
    String[] aryItemField13 = new String[] { "psnname", "cbizname",
        "cbizid" };
    arylistItemField.add(aryItemField13);

    // ���̻������� for ��Ӧ�� 9
    String[] aryItemField7 = new String[] { "pk_cubasdoc", "pk_cubasdoc",
        "cproviderid" };
    arylistItemField.add(aryItemField7);

    // �������� for ��Ӧ�� 9
    String[] aryItemField8 = new String[] { "custname", "cprovidername",
        "pk_cubasdoc" };
    arylistItemField.add(aryItemField8);
    // �������� for ��Ӧ�̼��
    String[] aryItemField81 = new String[] { "custshortname",
        "cprovidershortname", "pk_cubasdoc" };
    arylistItemField.add(aryItemField81);

    // ���̻������� for �ͻ� 10
    String[] aryItemField5 = new String[] { "pk_cubasdoc", "pk_cubasdocC",
        "ccustomerid" };
    arylistItemField.add(aryItemField5);

    // �������� for �ͻ� 10
    String[] aryItemField6 = new String[] { "custname", "ccustomername",
        "pk_cubasdocC" };
    arylistItemField.add(aryItemField6);
    // �������� for �ͻ����
    String[] aryItemField61 = new String[] { "custshortname",
        "ccustomershortname", "pk_cubasdocC" };
    arylistItemField.add(aryItemField61);

    // //�շ����� 11
    String[] aryItemField18 = new String[] { "rdname", "cdispatchername",
        "cdispatcherid" };
    arylistItemField.add(aryItemField18);

    // ҵ������ 12
    String[] aryItemField17 = new String[] { "businame", "cbiztypename",
        "cbiztype" };
    arylistItemField.add(aryItemField17);

    // �����ӵĹ�ʽ
    // //���˷�ʽ 13
    String[] aryItemField42 = new String[] { "sendname",
        "cdilivertypename", "cdilivertypeid" };
    arylistItemField.add(aryItemField42);

    // for ���ϼӹ���ⵥ���ӹ�Ʒ
    // ��������
    // pk_invbasdoc 14
    String[] aryItemField20 = new String[] { "pk_invbasdoc",
        "pk_invbasdoc", "cinventoryid" };
    arylistItemField.add(aryItemField20);

    // ���� 14
    String[] aryItemField21 = new String[] { "invname", "cinventoryname",
        "pk_invbasdoc" };
    arylistItemField.add(aryItemField21);

    return arylistItemField;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2004-4-8 11:13:07)
   * 
   * @return nc.ui.ic.pub.bill.GeneralBillUICtl
   */
  public GeneralBillUICtl getGenBillUICtl() {
    if (m_GenBillUICtl == null)
      m_GenBillUICtl = new GeneralBillUICtl();
    return m_GenBillUICtl;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2001-7-11 ���� 11:19)
   * 
   * @return nc.ui.ic.pub.InvOnHandDialog
   */
  protected LocSelDlg getLocSelDlg() {
    if (null == m_dlgLocSel) {
      m_dlgLocSel = new LocSelDlg(this);
      m_dlgLocSel.setCorpID(getEnvironment().getCorpID());
    }
    return m_dlgLocSel;
  }

  /**
   * �˴����뷽��˵���� ��������: ���ݲ�ѯ��������; ���롢������ݿ������¹���÷���,����������onquery���� �������: ����ֵ:
   * �쳣����: ����:
   * 
   * @return nc.vo.ic.pub.bill.QryConditionVO
   */
//  protected QryConditionVO getQryConditionVO() {
//    // ��Ӳ�ѯ
//    nc.vo.pub.query.ConditionVO[] voaCond = getConditionDlg()
//        .getConditionVO();
//    // ����繫˾����ҵ��Ա����
//    voaCond = nc.ui.ic.pub.tools.GenMethod.procMultCorpDeptBizDP(voaCond,
//        getBillTypeCode(), getCorpPrimaryKey());
//    // ����nullΪ is null �� is not null add by hanwei 2004-03-31.01
//    nc.ui.ic.pub.report.IcBaseReportComm.fixContionVONullsql(voaCond);
//    QryConditionVO voCond = new QryConditionVO(" head.cbilltypecode='"
//        + getBillType() + "' AND " + getExtendQryCond(voaCond));
//
//    // addied by liuzy 2008-03-28 V5.03���󣺵��ݲ�ѯ������ֹ����
//    for (int i = 0; i < voaCond.length; i++) {
//      if (null != voaCond[i]
//          && (voaCond[i].getFieldCode().equals("head.dbilldate.from") || voaCond[i]
//              .getFieldCode().equals("head.dbilldate.end"))) {
//        voaCond[i].setFieldCode("head.dbilldate");
//      }
//    }
//
//    return voCond;
//  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2004-3-12 21:25:15)
   * 
   * @return nc.ui.ic.pub.setpart.SetPartDlg
   */
  protected SetPartDlg getSetpartDlg() {
    if (m_SetpartDlg == null) {
      m_SetpartDlg = new SetPartDlg(this);
    }
    return m_SetpartDlg;
  }

  /**
   * �򵥳�ʼ���ࡣ����������������������õĲ���Ա����˾�ȡ�
   */
  /* ���棺�˷������������ɡ� */
  protected void initialize(String sBiztypeid) {

    try {
      // ���������
      m_layoutManager = new ToftLayoutManager(this);
      // user code begin {1}
      // ���󷽷�����ʼ������
      initPanel();
      getButtonManager().getButton(ICButtonConst.BTN_LINE_BARCODE)
          .setEnabled(false);
      // ���·�ҳ�Ŀ���
      m_pageBtn = new PageCtrlBtn(this);
      // ��ʼ����ť
      // initButtons();
      // ��ϵͳ����
      initSysParam();
      // ��ʼ��ȱʡ�˵���
      // initButtonsData();



      // user code end
      setName("ClientUI");

      // ------------- ���þ��� -----------
      setScaleOfCardPanel(getBillCardPanel());
      setScaleOfListPanel();
      // ���õ��ۡ������ʵ�>0
      getGenBillUICtl().setValueRange(
          getBillCardPanel(),
          new String[] { "nprice", "hsl", "nsaleprice", "ntaxprice",
              "nquoteunitrate" }, 0,
          nc.vo.scm.pub.bill.SCMDoubleScale.MAXVALUE);
      
      //SCMEnv.out("��������Ը��٣������趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getBillTypeCode());
      // ���ò˵�
      setButtons();

      // ��ʼ���༭ǰ������
      getBillCardPanel().getBillModel().setCellEditableController(this);
      
//      �õ���ǰ�ı�����ϵͳĬ�ϵı���ɫ
      m_cNormalColor = getBillCardPanel().getBillTable().getBackground();
      
      
      // addied by liuzy 2010-7-1 ����08:00:37 �����Զ�������յĿɱ༭��      
      for (int i = 1; i <= 20; i++) {
        nc.ui.pub.bill.BillItem itembody = getBillCardPanel().getBodyItem(
            "vuserdef" + i);
        nc.ui.pub.bill.BillItem itemhead = getBillCardPanel().getHeadItem(
            "vuserdef" + i);
        if (itemhead != null
            && itemhead.getComponent() != null
            && itemhead.getComponent().getClass().getName().equals(
                "nc.ui.pub.beans.UIRefPane"))
          ((nc.ui.pub.beans.UIRefPane) itemhead.getComponent()).setEditable(itemhead
              .isEdit());
        if (itembody != null
            && itembody.getComponent() != null
            && itembody.getComponent().getClass().getName().equals(
                "nc.ui.pub.beans.UIRefPane"))
          ((nc.ui.pub.beans.UIRefPane) itembody.getComponent()).setEditable(itembody
              .isEdit());
      }

    } catch (java.lang.Throwable ivjExc) {
      handleException(ivjExc);
    }
    // user code begin {2}

    // ��ʼ��Ϊ���ɱ༭��
    getBillCardPanel().setEnabled(false);
    setM_iMode(BillMode.Browse);
    // ��ʼ����ʾ����ʽ��
    setM_iCurPanel(BillMode.Card);

    getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setHint(
        nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000068")/* @res "�л����б���ʽ" */);

    getBillListPanel().addEditListener(this);
    getBillListPanel().getChildListPanel().addEditListener(this);
    getBillCardPanel().addEditListener(this);

    // ��ʱ���ӣ��������ȫ�ɱ༭��
    getBillCardPanel().addBodyEditListener2(this);

    getBillCardPanel().setBillBeforeEditListenerHeadTail(this);

    getBillCardPanel().getBillModel().addTableModelListener(this);

    // ���� ������ͷ�ͱ�ͷ������
    m_listHeadSortCtl = new ClientUISortCtl(this, false, BillItem.HEAD);
    getBillListPanel().getHeadBillModel().addBillSortListener2(this);
    m_listBodySortCtl = new ClientUISortCtl(this, false, BillItem.BODY);

    getBillCardPanel().getBillTable().addSortListener();
    m_cardBodySortCtl = new ClientUISortCtl(this, true, BillItem.BODY);

    getBillCardPanel().setAutoExecHeadEditFormula(true);

    // �ϼ���
    getBillCardPanel().getBillModel().addTotalListener(this);

    // getBillListPanel().getHead().addTableModelListener(this);
    getBillListPanel().getBodyTable().getModel()
        .addTableModelListener(this);

    getBillListPanel().addMouseListener(this);

    getBillCardPanel().addBodyMenuListener(this);
    // ���˲�����ʾ
    filterRef(getEnvironment().getCorpID());
    // ���û�����
    setTotalCol();
    // ��retBusinessBtnǰ���á�
    setButtonStatus(true);

    GeneralBillUICtl.initHideItem(this);

    GeneralBillUICtl.setCardItemNotEdit(getBillCardPanel());

    getEditCtrl().saveCardEditFlag(getBillCardPanel());

    GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);

    // ����е��ݲ���
    if (m_bNeedBillRef && isNeedBillRefWithBillType()) {
      // ��ȡҵ������
      nc.ui.pub.pf.PfUtilClient.retBusinessBtn(getButtonManager()
          .getButton(ICButtonConst.BTN_BUSINESS_TYPE),
          getEnvironment().getCorpID(), getBillType());
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .setCheckboxGroup(true);

    }

    if (isNeedBillRefWithBillType() && getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE) != null
        && getButtonManager()
            .getButton(ICButtonConst.BTN_BUSINESS_TYPE)
            .getChildButtonGroup() != null
        && getButtonManager()
            .getButton(ICButtonConst.BTN_BUSINESS_TYPE)
            .getChildButtonGroup().length > 0
        && getButtonManager()
            .getButton(ICButtonConst.BTN_BUSINESS_TYPE)
            .getChildButtonGroup()[0] != null) {
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .getChildButtonGroup()[0].setSelected(true);
      //SCMEnv.out("��������Ը��٣������趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getBillTypeCode());
      //SCMEnv.out("��������Ը��٣������趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE).getCode() + ":"+getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE).getName()+":"+getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE).getTag());
      onJointAdd(getButtonManager().getButton(
          ICButtonConst.BTN_BUSINESS_TYPE).getChildButtonGroup()[0]);
    }

    // �޸��ˣ������� �޸����ڣ�2007-12-26����11:05:02 �޸�ԭ���Ҽ�����"�����к�"
    UIMenuItem[] oldUIMenuItems = getBillCardPanel().getBodyMenuItems();
    if (oldUIMenuItems.length > 0) {
      ArrayList<UIMenuItem> newMenuList = new ArrayList<UIMenuItem>();
      for (UIMenuItem oldUIMenuItem : oldUIMenuItems)
        newMenuList.add(oldUIMenuItem);
      newMenuList.add(getAddNewRowNoItem());
      newMenuList.add(getMiLineCardEdit());
      //newMenuList.add(getMiLineBatchEdit());
      getAddNewRowNoItem().removeActionListener(this);
      getAddNewRowNoItem().addActionListener(this);
      getMiLineCardEdit().removeActionListener(this);
      getMiLineCardEdit().addActionListener(this);
      //getMiLineBatchEdit().removeActionListener(this);
      //getMiLineBatchEdit().addActionListener(this);
      UIMenuItem[] newUIMenuItems = new UIMenuItem[newMenuList.size()];
      m_Menu_AddNewRowNO_Index = newMenuList.size() - 1;
      newMenuList.toArray(newUIMenuItems);
      // getBillCardPanel().setBodyMenu(newUIMenuItems);
      getBillCardPanel().getBodyPanel().setMiBody(newUIMenuItems);
      getBillCardPanel().getBodyPanel().setBBodyMenuShow(true);
      getBillCardPanel().getBodyPanel().addTableBodyMenu();

    }
    
    GeneralBillUICtl.setBillCardPaneSelectMode(getBillCardPanel());
    GeneralBillUICtl.setBillListPaneSelectMode(getBillListPanel());
    showBtnSwitch();
    m_layoutManager.show();

  }


  protected void setIsImportData(boolean isImportData) {
    m_bIsImportData = isImportData;
  }

  /**
   * �����ߣ�� ���ܣ���������˺󣩱��浼������
   */
  public void onImportSignedBillBarcode(GeneralBillVO voUpdatedBill)
      throws Exception {

    // �Ƿ�������������ʵ��������true
    onImportSignedBillBarcode(voUpdatedBill, true);

  }

  /**
   * �����ߣ�� ���ܣ�����δǩ�����״̬�µ�������
   * 
   */
  public ArrayList onImportSignedBillBarcodeKernel(GeneralBillVO voBill,
      GeneralBillVO voUpdatedBill) throws Exception {
    voBill.setAccreditUserID(voUpdatedBill.getAccreditUserID());
    voBill.setOperatelogVO(voUpdatedBill.getOperatelogVO());
    // ��������������������
    // nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.beforSaveBillVOBarcode(
    // m_bBarcodeSave, voBill);
    // �Ƿ񱣴�������һ�µ�����
    voBill.setSaveBadBarcode(m_bBadBarcodeSave);
    // �Ƿ񱣴�����
    // voBill.setSaveBarcode(true);
    // �޸��ˣ������� �޸����ڣ�2007-9-3����03:29:47 �޸�ԭ����������������������
    nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
        .beforSaveBillVOBarcode(voBill);

    // add by liuzy 2007-11-02 10:16 ѹ������
    ObjectUtils.objectReference(voBill);

    ArrayList alRetData = (ArrayList) nc.ui.pub.pf.PfUtilClient
        .processAction("IMPORTBARCODE", getBillType(), getEnvironment()
            .getLogDate(), voBill, null);
    // ����������������������ָ������Ŀ¼�µ�Excel�����ļ���ɾ��
    OffLineCtrl ctrl = new OffLineCtrl(this);
    ctrl.directSaveDelete(voBill);

    return alRetData;
  }

  /**
   * �����ߣ����� ���ܣ�����¼�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void onJointAdd(ButtonObject bo) {
  
    if (isNeedBillRefWithBillType()){
      // ��ǰ���б���ʽʱ�������л�������ʽ
      if (BillMode.List == getM_iCurPanel())
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_SWITCH));
      // onSwitch();
      nc.ui.pub.pf.PfUtilClient.retAddBtn(getButtonManager().getButton(
          ICButtonConst.BTN_ADD), getEnvironment().getCorpID(),
          getBillType(), bo);
      showSelBizType(bo);
      // updateButtons();
      //SCMEnv.out("��������Ը��٣������趨�İ�ť��ʼ���˵�ʱ�İ�ť: "+getBillTypeCode());
      //isSetButtons = false;
      setButtons();
    }

  }

  /**
   * �����ߣ����˾� ���ܣ�ȷ�ϣ����棩���� �������� ���أ� true: �ɹ� false: ʧ��
   * 
   * ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 2001/10/29,wnj,��ֳ���������/�����޸�����������ʹ�ø��淶��
   * 
   * 
   * 
   */
  public boolean onSave() {
    long lStartTime = System.currentTimeMillis();
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
        "UCH044")/* @res "���ڱ���" */);

//    if(!getBillCardPanel().getBillData().execBodyValidateFormulas())
//      return false;
    //֧����֤��ʽ ������ 090909
    if(!getBillCardPanel().getBillData().execValidateFormulas())
             return false;


    boolean bSave = false;
    bSave = onSaveBase();
    // �����б����Ķ���
    if (bSave) {
      
      GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
      
      GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);
      
      getM_utfBarCode().setText(null);
      
    }
    if (m_bOnhandShowHidden) {
      m_pnlQueryOnHand.clearCache();
      m_pnlQueryOnHand.fresh();
    }
    // v5 ����ǲ����������ŵ��ݣ�����ɹ���Ҫɾ���б��µĶ�Ӧ����
    if (m_bRefBills && bSave) {
      // ɾ���б��µĶ�Ӧ����
      // delBillOfList(m_iLastSelListHeadRow);
      
      if (null == m_alRefBillsBak)
        m_alRefBillsBak = new ArrayList();
      m_alRefBillsBak.add(getM_voBill());
      
      if (getM_iLastSelListHeadRow() >= 0)
        removeBillsOfList(new int[] { getM_iLastSelListHeadRow() },true);
      updateHeadTsWhenMutiBillSave(getM_voBill());
      
      if ((getM_alListData() == null || getM_alListData().size() == 0)
          && null != m_alRefBillsBak && 0< m_alRefBillsBak.size()){
        m_bRefBills = false;
        setM_iMode(BillMode.Browse);
        setDataOnList(m_alRefBillsBak,false);
        m_alRefBillsBak = null;
        

      }
      else{
        for (GeneralBillVO billVO : getM_alListData())
          updateHeadTsWhenMutiBillSave(billVO);
        onButtonClicked(getButtonManager().getButton(
        ICButtonConst.BTN_SWITCH));
      }
      // onSwitch();
    }
    nc.vo.scm.pub.SCMEnv.showTime(lStartTime, "Bill Save");

    return bSave;
  }

  /**
   * �����ߣ�yangbo
   * 
   * ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * ��ʽ���ɶ൥ʱ������Դ������ͬ�ĵ��ݣ���Ҫˢ����ص��ݵ���Դ���ݱ�ͷts
   * 
   * 
   * 
   */
  private void updateHeadTsWhenMutiBillSave(GeneralBillVO billvo) {
    if (billvo == null || billvo.getItemVOs() == null)
      return;
    HashSet<String> idlist = new HashSet<String>(50);
    for (int i = 0; i < billvo.getItemVOs().length; i++) {
      String srcbilltype = billvo.getItemVOs()[i].getCsourcetype();
      String srcbillid = billvo.getItemVOs()[i].getCsourcebillhid();
      if (srcbilltype == null || srcbillid == null)
        continue;
      if (idlist.contains(srcbilltype + srcbillid))
        continue;
      updateHeadTsWhenMutiBillSave(srcbilltype, srcbillid);
      idlist.add(srcbilltype + srcbillid);
    }
  }

  /**
   * �����ߣ�yangbo
   * 
   * ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * ��ʽ���ɶ൥ʱ������Դ������ͬ�ĵ��ݣ���Ҫˢ����ص��ݵ���Դ���ݱ�ͷts
   * 
   * 
   * 
   */
  private void updateHeadTsWhenMutiBillSave(String srcbilltype, String srchid) {
    if (srcbilltype == null || srcbilltype.trim().length() <= 0
        || srchid == null || srchid.trim().length() <= 0)
      return;
    if (getM_alListData() == null || getM_alListData().size() <= 0)
      return;

    ArrayList<GeneralBillHeaderVO> vohlist = new ArrayList<GeneralBillHeaderVO>(
        10);
    ArrayList<GeneralBillItemVO> voblist = new ArrayList<GeneralBillItemVO>(
        20);
    for (int i = 0; i < getM_alListData().size(); i++) {
      GeneralBillVO billvo = (GeneralBillVO) getM_alListData().get(i);
      GeneralBillItemVO[] bodyvos = billvo.getItemVOs();
      GeneralBillHeaderVO headvo = billvo.getHeaderVO();
      if (bodyvos == null || bodyvos.length <= 0)
        continue;
      for (int j = 0; j < bodyvos.length; j++)
        if (srcbilltype.equals(bodyvos[j].getCsourcetype())
            && srchid.equals(bodyvos[j].getCsourcebillhid())) {
          if (!voblist.contains(bodyvos[j]))
            voblist.add(bodyvos[j]);
          if (!vohlist.contains(headvo))
            vohlist.add(headvo);
        }
    }

    if (vohlist.size() <= 0 || voblist.size() <= 0)
      return;

    String stable = ICSourceBillPara.getHeadTable(srcbilltype);
    String pkfieldname = ICSourceBillPara.getHeadPkField(srcbilltype);
    if (stable == null || stable.trim().length() <= 0
        || pkfieldname == null || pkfieldname.trim().length() <= 0)
      return;
    ICDataSet set = nc.ui.ic.pub.tools.GenMethod.queryData(stable,
        pkfieldname, new String[] { "ts" },
        new int[] { SmartFieldMeta.JAVATYPE_UFDATETIME },
        new String[] { srchid }, " dr=0 ");
    String ts = null;
    if (set != null && set.getRowCount() > 0
        && set.getValueAt(0, 0) != null) {
      ts = set.getValueAt(0, 0).toString();
      if (ts == null || ts.trim().length() <= 0)
        return;
      for (int i = 0; i < voblist.size(); i++)
        voblist.get(i).setCsourceheadts(ts);
      for (int i = 0; i < vohlist.size(); i++)
        vohlist.get(i).setTs(ts);

    }
  }

  /**
   * 
   * ������������������ĳЩ�ֶεĿ�ֵ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * 
   * @author duy
   * @time 2008-4-28 ����03:51:47
   * @deprecated
   */
/*  private void fillItemNullValue() {
    GeneralBillHeaderVO header = getM_voBill().getHeaderVO();
    GeneralBillItemVO[] items = getM_voBill().getItemVOs();

    // �ֿ�
    if (header.getCwarehouseid() == null
        || header.getCwarehouseid().length() == 0)
      header.setCwarehouseid(GenMethod.STRING_NULL);
    // ����
    if (header.getCdptid() == null || header.getCdptid().length() == 0)
      header.setCdptid(GenMethod.STRING_NULL);
    // �շ����
    if (header.getCdispatcherid() == null
        || header.getCdispatcherid().length() == 0)
      header.setCdispatcherid(GenMethod.STRING_NULL);
    // �����֯
    if (header.getPk_calbody() == null
        || header.getPk_calbody().length() == 0)
      header.setPk_calbody(GenMethod.STRING_NULL);
    // ���Ա
    if (header.getCwhsmanagerid() == null
        || header.getCwhsmanagerid().length() == 0)
      header.setCwhsmanagerid(GenMethod.STRING_NULL);

    for (GeneralBillItemVO item : items) {
      item.setAttributeValue(IItemKey.PKCORP, header.getPk_corp());
      item.setCbodywarehouseid(header.getCwarehouseid());
      item.setPk_bodycalbody(header.getPk_calbody());
      item.setPk_calbody(header.getPk_calbody());
      item.setCbodybilltypecode(header.getCbilltypecode());
    }
  }*/

  /**
   * �����ߣ����˾� ���ܣ�ȷ�ϣ����棩���� �������� ���أ� true: �ɹ� false: ʧ��
   * 
   * ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 2001/10/29,wnj,��ֳ���������/�����޸�����������ʹ�ø��淶��
   * 
   * 
   * 
   */
  public boolean onSaveBase() {
    try {
      nc.vo.ic.pub.bill.Timer t = new nc.vo.ic.pub.bill.Timer();
      m_timer.start("���濪ʼ");/*-=notranslate=-*/
      t.start();
      // ��ȥ����ʽ�µĿ���
      filterNullLine();

      m_timer.showExecuteTime("filterNullLine");
      // �ޱ����� ------------ EXIT -------------------
      if (getBillCardPanel().getRowCount() <= 0) {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000072")/* @res "�����������!" */);
        // ��������� add by hanwei 2004-06-08 ,��������ⵥ��Щ����²�������
        return false;
      }
      // added by zhx 030626 ����кŵĺϷ���; �÷���Ӧ���ڹ��˿��еĺ��档
      if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
          getBillCardPanel(), IItemKey.CROWNO)) {
        return false;
      }
      // ��ǰ�ı�������
      int iRowCount = getBillCardPanel().getRowCount();
      // ����ĵ�������
      GeneralBillVO voInputBill = null;
      // �ӽ����л����Ҫ������
      voInputBill = getBillVO();
      // �õ����ݴ��󣬳��� ------------ EXIT -------------------
      if (voInputBill == null || voInputBill.getParentVO() == null
          || voInputBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        return false;
      }
      // ����ı�����
      GeneralBillItemVO voInputBillItem[] = voInputBill.getItemVOs();
      // �õ�������
      int iVORowCount = voInputBillItem.length;
      // �õ������кͽ���������һ�£����� ------------ EXIT -------------------
      if (iVORowCount != iRowCount) {
        SCMEnv.out("data error." + iVORowCount + "<>" + iRowCount);
        return false;
      }
      m_timer.showExecuteTime("From fliterNullLine Before setIDItems");
      // VOУ��׼������
      getM_voBill().setIDItems(voInputBill);
      // ���õ�������
      getM_voBill().setHeaderValue("cbilltypecode", getBillType());

      m_timer.showExecuteTime("setIDItems");

      // ���õ����к�zhx 0630:
      if (iRowCount > 0 && getM_voBill().getChildrenVO() != null) {
        if (getBillCardPanel().getBodyItem(IItemKey.CROWNO) != null)
          for (int i = 0; i < iRowCount; i++) {
            getM_voBill().setItemValue(
                i,
                IItemKey.CROWNO,
                getBillCardPanel().getBodyValueAt(i,
                    IItemKey.CROWNO));

          }
      }
      // VOУ�� ------------ EXIT -------------------
      if (!checkVO(getM_voBill())) {

        return false;
      }
      m_timer.showExecuteTime("VOУ��");/*-=notranslate=-*/

      // ���û�е������ڣ���дΪ��ǰ��¼����
      if (getBillCardPanel().getHeadItem("dbilldate") == null
          || getBillCardPanel().getHeadItem("dbilldate")
              .getValueObject() == null
          || getBillCardPanel().getHeadItem("dbilldate")
              .getValueObject().toString().trim().length() == 0) {
        SCMEnv.out("-->no bill date.");
        getM_voBill().setHeaderValue("dbilldate",
            getEnvironment().getLogDate());
      }
      m_timer.showExecuteTime("���õ������ͺ͵�������");/*-=notranslate=-*/

      // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
      // "4008bill", "UPP4008bill-000102")/* @res "���ڱ��棬���Ժ�..." */);

      // ����ĺ��ķ������ add by hanwei 2004-04

      // Ĭ�ϵ���״̬ add by hanwei
      m_sBillStatus = nc.vo.ic.pub.bill.BillStatus.FREE;
      // ʵ��m_sBillStatus�ĸ�ֵ��onSaveBaseKernel�еģ�saveUpdateBill,saveNewBill

      getM_voBill().setIsCheckCredit(true);
      getM_voBill().setIsCheckPeriod(true);
      getM_voBill().setIsCheckAtp(true);
      getM_voBill().setGetPlanPriceAtBs(false);
      getM_voBill().setIsRwtPuUserConfirmFlag(false);

      // �����ֵ
      //fillItemNullValue();

      while (true) {
        try {

          onSaveBaseKernel(getM_voBill(), getEnvironment()
              .getUserID());
          break;

        }
        catch ( nc.bs.framework.exception.ConnectorSocketException e ) {
          SCMEnv.out(e.getMessage());
          showErrorMessage( nc.ui.ml.NCLangRes.getInstance().getStrByID(
              "4008busi","UPP4008busi-000403")/* @res "��ǰ�����쳣���������磡" */ );
        }
        catch (Exception ee1) {

          BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
              .handleException(null, null, ee1);
          if (realbe != null
              && realbe.getClass() == nc.vo.scm.pub.excp.RwtIcToPoException.class) {
            // ������Ϣ��ʾ����ѯ���û����Ƿ��������
            int iFlag = showYesNoMessage(realbe.getMessage());
            // ����û�ѡ�����
            if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
              getM_voBill().setIsRwtPuUserConfirmFlag(true);
              continue;
            } else
              return false;
          } else if (realbe != null
              && realbe.getClass() == CreditNotEnoughException.class) {
            // ������Ϣ��ʾ����ѯ���û����Ƿ��������
            // �Ƿ������ ��Ϊ������ʽ modify by qinchao  20081225 ʥ���ڣ���3���滻
            int iFlag = showYesNoMessage(realbe.getMessage()
                + " \r\n" + 
                nc.ui.ml.NCLangRes.getInstance().getStrByID("40080802","ClientUI-000001")/* @res "�Ƿ����" */);
            // ����û�ѡ�����
            if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
              getM_voBill().setIsCheckCredit(false);
              continue;
            } else
              return false;
          } else if (realbe != null
              && realbe.getClass() == PeriodNotEnoughException.class) {
            // ������Ϣ��ʾ����ѯ���û����Ƿ��������
            int iFlag = showYesNoMessage(realbe.getMessage()
                + " \r\n" + 
                nc.ui.ml.NCLangRes.getInstance().getStrByID("40080802","ClientUI-000001")/* @res "�Ƿ����" */);
            // ����û�ѡ�����
            if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
              getM_voBill().setIsCheckPeriod(false);
              continue;
            } else
              return false;
          } else if (realbe != null
              && realbe.getClass() == ATPNotEnoughException.class) {
            ATPNotEnoughException atpe = (ATPNotEnoughException) realbe;
            if (atpe.getHint() == null) {
              showErrorMessage(atpe.getMessage());
              return false;
            } else {
              // ������Ϣ��ʾ����ѯ���û����Ƿ��������
              int iFlag = showYesNoMessage(atpe.getMessage()
                  + " \r\n" + 
                  nc.ui.ml.NCLangRes.getInstance().getStrByID("40080802","ClientUI-000001")/* @res "�Ƿ����" */);
              // ����û�ѡ�����
              if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
                getM_voBill().setIsCheckAtp(false);
                continue;
              } else {
                return false;
              }
            }
          } else {
            if (realbe != null)
              throw realbe;
            else
              throw ee1;
          }
        }
      }
      
      

      // ����ͨ���������޸�
      if (BillMode.New == getM_iMode() || BillMode.Update == getM_iMode()) {
        // necessary��//ˢ�µ���״̬
        getBillCardPanel().updateValue();
        m_timer.showExecuteTime("updateValue");
        // coperatorid
        setM_iMode(BillMode.Browse);
        
        getEditCtrl().resetCardEditFlag(getBillCardPanel());
        // ���ɱ༭
        getBillCardPanel().setEnabled(false);
        // ���谴ť״̬
        setButtonStatus(false);
        m_timer.showExecuteTime("setButtonStatus");

        // ����ִ���
        // ���� by hanwei 2003-11-13 ���Ᵽ������ѡ����ִ���Ϊ��
        // m_voBill.clearInvQtyInfo();
        // ѡ�е�һ��
        //getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
        // �������к��Ƿ����
        setBtnStatusSN(0, false);
        // ˢ�µ�һ���ִ�����ʾ
        //setTailValue(0);
        m_timer.showExecuteTime("ˢ�µ�һ���ִ�����ʾ");/*-=notranslate=-*/
      }

//      if (m_sBillStatus != null && !m_sBillStatus.equals(BillStatus.FREE)
//          && !m_sBillStatus.equals(BillStatus.DELETED)) {
//        SCMEnv.out("**** saved and signed ***");
//        freshAfterSignedOK(m_sBillStatus);
//        m_timer.showExecuteTime("freshAfterSignedOK");
//      }
      
      m_sBillStatus = getM_voBill().getHeaderVO().getFbillflag().toString();
      
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "common", "UCH005")/* @res "����ɹ�" */);

      // ��������Դ�ĵ��ݽ��в�ͬ�Ľ�����ƣ�zhx 1130
      ctrlSourceBillUI(true);
      m_timer.showExecuteTime("��Դ���ݽ������");/*-=notranslate=-*/
      t.stopAndShow("����ϼ�");/*-=notranslate=-*/

      // save the barcodes to excel file according to param IC***
      m_timer.showExecuteTime("��ʼִ�б��������ļ�");/*-=notranslate=-*/
      OffLineCtrl ctrl = new OffLineCtrl(this);
      ctrl.saveExcelFile(getM_voBill(), getCorpPrimaryKey());
      m_timer.showExecuteTime("ִ�б��������ļ�����");/*-=notranslate=-*/
      nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel());
      return true;

    } catch (java.net.ConnectException ex1) {
      SCMEnv.out(ex1.getMessage());
      if (showYesNoMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000104")/*
                             * @res
                             * "��ǰ�����жϣ��Ƿ񽫵�����Ϣ���浽Ĭ��Ŀ¼��"
                             */
      ) == MessageDialog.ID_YES) {
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_EXPORT_TO_DIRECTORY));
        // onBillExcel(1);// ���浥����Ϣ��Ĭ��Ŀ¼
      }
    }
    catch (Exception e) {

      if (e instanceof nc.vo.ic.ic009.PackCheckBusException) {

        handleException(e);
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000105")/* @res "�������" */);
        String se = e.getMessage();
        if (se != null) {
          int index = se.indexOf("$$ZZZ$$");
          if (index >= 0)
            se = se.substring(index + 7);
        }
        // packCheckBusDialog = null;
        getpackCheckBusDialog().setText(se);

        getpackCheckBusDialog().showModal();

      } 
      // added by lirr 20092009-7-2����01:31:53

      else if (e instanceof nc.vo.scm.ic.exp.ICSNException){
        String sErrorMessage = ((ICSNException)e).getHint();
            showErrorMessage(sErrorMessage);
      }
      else {

        handleException(e);
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000105")/* @res "�������" */);
        String se = e.getMessage();
        if (se != null) {
          int index = se.indexOf("$$ZZZ$$");
          if (index >= 0)
            se = se.substring(index + 7);
        }
        showErrorMessage(se);
      }
      
      if (e instanceof ICBusinessException){
        ICBusinessException ee = (ICBusinessException) e;
        // ������ɫ
        nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(),ee);
      }

    }
    return false;
  }

  /**
   * �����ߣ�������������еĺ��ķ��� ���ܣ�ȷ�ϣ����棩���� �������� ���⣺ ���ڣ�(2004-4-1 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� 2004-4-1 ����
   * 
   */
  public void onSaveBaseKernel(GeneralBillVO voBill, String sAccreditUserID)
      throws Exception {

    try {
      nc.vo.sm.log.OperatelogVO log = getNormalOperateLog();


      if (BillMode.New == getM_iMode()) {
        voBill.setStatus(nc.vo.pub.VOStatus.NEW);
        voBill.setHeaderValue("coperatorid", getEnvironment()
            .getUserID());
//        voBill.setHeaderValue("time", m_dTime);
        voBill.setAccreditUserID(sAccreditUserID);
        voBill.setOperatelogVO(log);
            
        //���ο�����չ
        getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voBill});
        saveNewBill(voBill);
        //���ο�����չ
            getPluginProxy().afterAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voBill});
      } else // �޸�
      if (BillMode.Update == getM_iMode()) {
        // �õ��޸ĺ�ĵ���VO
        GeneralBillVO voUpdatedBill = getBillChangedVO();
        voUpdatedBill.setAccreditUserID(sAccreditUserID);
        voUpdatedBill.setTs(voBill);
        voUpdatedBill.setOperatelogVO(log);
        // ִ���޸ı���...�д����׳��쳣
        // ִ���޸ı���
            
        //���ο�����չ
        getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voUpdatedBill});
        saveUpdatedBill(voUpdatedBill);
        //���ο�����չ
            getPluginProxy().afterAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voUpdatedBill});
      } else {
        SCMEnv.out("status invalid...");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000106")/*
                                     * @res
                                     * "״̬���� "
                                     */);
      }
      
  
      
    } catch (RightcheckException e) {
      showErrorMessage(e.getMessage()
          + nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
              "UPP4008bill-000069")/* @res ".\nȨ��У�鲻ͨ��,����ʧ��! " */);
      getAccreditLoginDialog().setCorpID(getEnvironment().getCorpID());
      getAccreditLoginDialog().clearPassWord();
      if (getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
        String sUserID = getAccreditLoginDialog().getUserID();
        if (sUserID == null) {
          throw new Exception(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000070")/*
                                       * @res
                                       * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                       */);
        } else {
          /*
           * voBill.setAccreditUserID(sUserID);
           * onSaveBaseKernel(voBill, sUserID);
           */
          voBill.setAccreditBarcodeUserID(
              e.getFunCodeForRightCheck(), sUserID);
          onSaveBaseKernel(voBill, getEnvironment().getUserID());

        }
      } else {
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000070")/*
                                     * @res
                                     * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                     */);

      }

    } catch (Exception e) {
      // �޸��ˣ������� �޸����ڣ�2007-10-31����04:46:03
      // �޸�ԭ����Ϊ����RightcheckException�ᱻ��һ�㣬���������´�����ദ�����㡣
      Exception eCause = null;
      if (null != e && null != e.getCause())
        eCause = (Exception) e.getCause();
      if (null != eCause
          && eCause.getClass() != RightcheckException.class
          && null != eCause.getCause())
        eCause = (Exception) eCause.getCause();
      if (null != eCause
          && eCause.getClass() != RightcheckException.class
          && null != eCause.getCause())
        eCause = (Exception) eCause.getCause();
      if (null != eCause
          && eCause.getClass() == RightcheckException.class) {

        showErrorMessage(eCause.getMessage()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000069")/*
                                   * @res
                                   * ".\nȨ��У�鲻ͨ��,����ʧ��! "
                                   */);
        getAccreditLoginDialog()
            .setCorpID(getEnvironment().getCorpID());
        getAccreditLoginDialog().clearPassWord();
        if (getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
          String sUserID = getAccreditLoginDialog().getUserID();
          if (sUserID == null) {
            throw new Exception(nc.ui.ml.NCLangRes.getInstance()
                .getStrByID("4008bill", "UPP4008bill-000070")/*
                                         * @res
                                         * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                         */);
          } else {
            /*
             * voBill.setAccreditUserID(sUserID);
             * onSaveBaseKernel(voBill, sUserID);
             */
            voBill.setAccreditBarcodeUserID(
                ((RightcheckException) eCause)
                    .getFunCodeForRightCheck(), sUserID);
            onSaveBaseKernel(voBill, getEnvironment().getUserID());
          }
        } else {
          throw new Exception(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000070")/*
                                       * @res
                                       * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                       */);

        }

      } else
        throw e;
    }

  }

  /**
   * �����ߣ����˾� ���ܣ���λָ��
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2003-7-2 19:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void onSelLoc() {
    // warehouse id
    String sNewWhID = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
        .getHeadItem(IItemKey.WAREHOUSE).getComponent()).getRefPK();
    if (sNewWhID == null || sNewWhID.trim().length() == 0) {
      showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000107")/* @res "����ѡ��ֿ�" */);
    } else {
      getLocSelDlg().setWhID(sNewWhID);
      if (getLocSelDlg().showModal() == LocSelDlg.ID_OK) {

        String cspaceid = getLocSelDlg().getLocID();
        String csname = getLocSelDlg().getLocName();
        // �����ֵ�ݴ�
        Object oTempValue = null;
        // ����model
        nc.ui.pub.bill.BillModel bmBill = getBillCardPanel()
            .getBillModel();
        // ����кţ�Ч�ʸ�һЩ��
        int iInvCol = bmBill.getBodyColByKey(IItemKey.INVID);

        // �����д����
        if (bmBill != null && iInvCol >= 0
            && iInvCol < bmBill.getColumnCount()) {
          // ����
          int iRowCount = getBillCardPanel().getRowCount();
          // �Ӻ���ǰɾ
          for (int line = 0; line < iRowCount; line++) {
            // �������
            oTempValue = bmBill.getValueAt(line, IItemKey.INVID);
            if (oTempValue != null
                && oTempValue.toString().trim().length() > 0)
              getEditCtrl().setRowSpaceData(line, cspaceid,
                  csname);
          }
        }

      }
    }

  }

  /**
   * �����ߣ����˾� ���ܣ���ѯָ���ĵ��ݡ� ������ billType, ��ǰ�������� billID, ��ǰ����ID businessType,
   * ��ǰҵ������ operator, ��ǰ�û�ID pk_corp, ��ǰ��˾ID
   * 
   * ���� ������vo ���� �� ���� �� (2001 - 5 - 9 9 : 23 : 32) �޸����� �� �޸��� �� �޸�ԭ�� �� ע�ͱ�־ ��
   * 
   * 
   * 
   * 
   */
  protected GeneralBillVO qryBill(String pk_corp, String billType,
      String businessType, String operator, String billID,
      ConditionVO[] convos) {

    if (billID == null || billType == null || pk_corp == null) {
      SCMEnv.out("no bill param");
      return null;
    }
    GeneralBillVO voRet = null;
    try {
      String sqrywhere = "  head.cbilltypecode='" + billType
          + "' AND head.cgeneralhid='" + billID + "' ";
      QryConditionVO voCond = new QryConditionVO(sqrywhere);

      voCond.setIntParam(0, GeneralBillVO.QRY_HEAD_ONLY_PURE);
      if (convos != null && convos.length > 0) {
        voCond.setParam(QryConditionVO.QRY_CONDITIONVO, convos);
        String swhere = convos[0].getWhereSQL(convos);
        if (swhere != null && swhere.trim().length() > 0)
          voCond.setQryCond(sqrywhere + " and " + swhere);
      }

      // ���ý�����
      // getPrgBar(PB_QRY).start();
      long lTime = System.currentTimeMillis();
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000012")/* @res "���ڲ�ѯ�����Ժ�..." */);
      ArrayList<GeneralBillVO> alListData = (ArrayList) GeneralBillHelper.queryBills(
          getBillType(), voCond);

      showTime(lTime, "��ѯ");/*-=notranslate=-*/
      // ִ����չ��ʽ.Ŀǰֻ�����۳��ⵥUI����.
      //       
      // execExtendFormula(alListData);
      // //��ʽ��� ��һ�������¼��ʽ��ѯ�������� �޸� hanwei 2003-03-05
      if (alListData != null && alListData.size() > 0) {
        //
        setAlistDataByFormula(GeneralBillVO.QRY_FIRST_ITEM_NUM,
            alListData);
        SCMEnv.out("0�����ʽ�����ɹ���");/*-=notranslate=-*/
        //
        setM_alListData(alListData);
        // //�����
        // // ���� by hanwei 2003-06-17 ,�����ѯ
        // //qryItems(new int[] { 0 }, new String[] { billID });
        // //��ͷִ�й�ʽ
        voRet = (GeneralBillVO) alListData.get(0);

      }

    } catch (Exception e) {
      handleException(e);
      showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000015")/* @res "��ѯ����" */
          + e.getMessage());
    }
    return voRet;
  }

  /**
   * �����ߣ����˾� ���ܣ���ָ����ŵ��ݵĻ�λ/���к�����,ֻ�������״̬�¡� ������ָ����ѯģʽ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void qryLocSN(int iBillNum, int iMode) {
    GeneralBillVO voMyBill = null;
    // arraylist �еĻ�������,û�л�,�����µ�.
    if (getM_alListData() != null && getM_alListData().size() > iBillNum
        && iBillNum >= 0)
      voMyBill = (GeneralBillVO) getM_alListData().get(iBillNum);
    qryLocSN(voMyBill, iMode);
  }

  protected void qryLocSN(GeneralBillVO voMyBill, int iMode) {
    try {
      if (!needToQryLocSN(voMyBill)) {
    	  // ����״̬�ȷ����̬����Ҫ��ѯ��λ���к�
    	  return;
      }

      // �����Ƿ��Ѿ�������Щ�����ˡ�
      boolean hasLoc = this.hasQryLoc(voMyBill);
      boolean hasSN = this.hasQrySN(voMyBill);
      // �Ѿ���������,�����ݷŵ���Ա�����У���ͬ��vo(needless now )2003-08-07
      if (hasLoc)
        m_alLocatorData = voMyBill.getLocators();
      if (hasSN)
        m_alSerialData = voMyBill.getSNs();
      if (hasLoc && hasSN)
        return;

      // =============================================================
      // ��ʼ������ if necessary
	  int i = 0, iRowCount = voMyBill.getItemCount();
      if (m_alLocatorData == null) {
        m_alLocatorData = new ArrayList();
        for (i = 0; i < iRowCount; i++)
          m_alLocatorData.add(null);
      }
      if (m_alSerialData == null) {
        m_alSerialData = new ArrayList();
        for (i = 0; i < iRowCount; i++)
          m_alSerialData.add(null);
      }

      for (i = 0; i < iRowCount; i++)
        // ����ʵ��/������
        if (voMyBill.getItemValue(i, "ninnum") != null
            && voMyBill.getItemValue(i, "ninnum").toString()
                .length() > 0
            || voMyBill.getItemValue(i, "noutnum") != null
            && voMyBill.getItemValue(i, "noutnum").toString()
                .length() > 0)
          break;

      if (i >= iRowCount) // ������
        return; // =============================================================

      // ����ջ�λ�����к�����
      Integer iSearchMode = null;
      // ��Ҫ���λ
      if (!hasLoc
          && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.LOC)) {
        iSearchMode = new Integer(iMode);
      }
      // ��Ҫ�����к�
      if (!hasSN
          && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.SN)) {
        iSearchMode = new Integer(iMode);
      }
      if (iSearchMode == null)
        return;

      // ////////////////////////////iMode); //���λ & ���к� 3 or ���к� 4
      ArrayList alAllData = (ArrayList) GeneralBillHelper.queryInfo(
          iSearchMode, voMyBill.getPrimaryKey());
      // ����ջ�λ�����к�����
      ArrayList alTempLocatorData = null;
      ArrayList alTempSerialData = null;

      if (iMode == QryInfoConst.LOC_SN) {
        if (alAllData != null && alAllData.size() >= 2) {
          alTempLocatorData = (ArrayList) alAllData.get(0);
          alTempSerialData = (ArrayList) alAllData.get(1);
        } // else
      } else if (iMode == QryInfoConst.SN) { // === SN only
        if (alAllData != null && alAllData.size() >= 1)
          alTempSerialData = (ArrayList) alAllData.get(0);
        // else
      } else if (iMode == QryInfoConst.LOC) { // === LOC only
        if (alAllData != null && alAllData.size() >= 1)
          alTempLocatorData = (ArrayList) alAllData.get(0);
      } // else

      // ����еĻ��û�λ����
      if (alTempLocatorData != null) {
        // �ŵ�vo�У����ݱ���idִ������ƥ�䡣
        voMyBill.setLocators(alTempLocatorData);
        // getLocators������ by hanwei 2004-01-06
        m_alLocatorData = voMyBill.getLocators();
      }
      // ����еĻ������к�����
      if (alTempSerialData != null) {
        // �ŵ�vo�У����ݱ���idִ������ƥ�䡣
        voMyBill.setSNs(alTempSerialData);
        // getSNs������ by hanwei 2004-01-06
        m_alSerialData = voMyBill.getSNs();
      }
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }

  }
  
  /**
   * ����Ƿ���Ҫ��ѯ��λ���к���Ϣ
   * @return
   */
  private boolean needToQryLocSN(GeneralBillVO voMyBill) {
      // ERRRR,needless to read,���������ݵ������
      if (voMyBill == null || voMyBill.getPrimaryKey() == null) {
        int iFaceRowCount = getBillCardPanel().getRowCount();
        // ��ʼ������ if necessary
        if (m_alLocatorData == null) {
          m_alLocatorData = new ArrayList();
          for (int i = 0; i < iFaceRowCount; i++)
            m_alLocatorData.add(null);
        }
        if (m_alSerialData == null) {
          m_alSerialData = new ArrayList();
          for (int i = 0; i < iFaceRowCount; i++)
            m_alSerialData.add(null);
        }
        SCMEnv.out("null bill,init loc ,sn");
        return false;
      }
      
      if (getM_iMode() != BillMode.Browse) {
    	// �����̬������Ҫ��ѯ��λ���к�
    	return false;
      }
      
      return true;
  }
  
  protected boolean hasQryLoc(GeneralBillVO voMyBill) {
	  int i = 0, iRowCount = voMyBill.getItemCount();
      boolean hasLoc = true;
      WhVO voWh = voMyBill.getWh();
      if (voWh != null) {
        if (voWh.getIsLocatorMgt() != null
            && voWh.getIsLocatorMgt().intValue() != 0) {
          for (i = 0; i < iRowCount; i++) {
            if (voMyBill.getItemValue(i, "locator") == null) {
              hasLoc = false;
              break;
            }
          }
          if (hasLoc) {
            try {
              VOCheck.checkSpaceInput(voMyBill, new Integer(
                  getInOutFlag()));
            } catch (Exception e) {
              nc.vo.scm.pub.SCMEnv.error(e);
              hasLoc = false;
            }
          }
        }
      }
      
      return hasLoc;
  }
  
  protected boolean hasQrySN(GeneralBillVO voMyBill) {
	  int i = 0, iRowCount = voMyBill.getItemCount();
      InvVO voInv = null;
      boolean hasSN = true;
      for (i = 0; i < iRowCount; i++) {
        voInv = voMyBill.getItemInv(i);
        // �����кŹ�����е����л�û�����кš�
        if (voInv != null && voInv.getIsSerialMgt() != null
            && voInv.getIsSerialMgt().intValue() != 0
            && voMyBill.getItemValue(i, "serial") == null) {
          hasSN = false;
          break;
        }
      }
      
      return hasSN;
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2004-2-10 9:11:33)
   * 
   * @param iBillNum
   *            int
   * @param iMode
   *            int ר�����б��±������������ݴ�ӡ��λ�Ĳ���
   */
  public void qryLocSNSort(int iBillNum, int iMode) {
    try {
      GeneralBillVO voMyBill = null;
      // arraylist �еĻ�������,û�л�,�����µ�.
      // ��������Ź���������������ݷ���ȡȫ�ֱ����е�����
      // if (m_sLastKey != null && getM_voBill() != null)
      // �޸��ˣ������� �޸����ڣ�2007-10-29����03:12:03
      // �޸�ԭ��ֻ���ڿ�Ƭ״̬�£����ÿ��ǵ�������ӡ˳�����⣬��ȡgetM_voBill()
      if (m_sLastKey != null && getM_voBill() != null
          && getM_iMode() == BillMode.Browse
          && getM_iCurPanel() == BillMode.Card)
        voMyBill = getM_voBill();
      else if (getM_alListData() != null
          && getM_alListData().size() > iBillNum && iBillNum >= 0)
        voMyBill = (GeneralBillVO) getM_alListData().get(iBillNum);

      // ERRRR,needless to read,���������ݵ������
      if (voMyBill == null || voMyBill.getPrimaryKey() == null) {
        int iFaceRowCount = getBillCardPanel().getRowCount();
        // ��ʼ������ if necessary
        if (m_alLocatorData == null) {
          m_alLocatorData = new ArrayList();
          for (int i = 0; i < iFaceRowCount; i++)
            m_alLocatorData.add(null);
        }
        if (m_alSerialData == null) {
          m_alSerialData = new ArrayList();
          for (int i = 0; i < iFaceRowCount; i++)
            m_alSerialData.add(null);
        }
        SCMEnv.out("null bill,init loc ,sn");
        return;
      }
      // ֻ�����״̬�£��Ż���Ҫ��ֵ�Ͳ�ѯ�������ȡ���������޸ġ�
      if (getM_iMode() == BillMode.Browse) {
        // ���Դ˵����Ƿ���������������������Ҫ����ִ�У����򵥾ݱ�����û����Щ���ݣ����ö���
        int i = 0, iRowCount = voMyBill.getItemCount();
        // �����Ƿ��Ѿ�������Щ�����ˡ�

        boolean hasLoc = true;
        WhVO voWh = voMyBill.getWh();
        if (voWh != null) {
          for (i = 0; i < iRowCount; i++) {
            if (voWh.getIsLocatorMgt() != null
                && voWh.getIsLocatorMgt().intValue() != 0
                && voMyBill.getItemValue(i, "locator") == null) {
              hasLoc = false;
              break;
            }
          }
        }
        InvVO voInv = null;
        boolean hasSN = true;
        for (i = 0; i < iRowCount; i++) {
          voInv = voMyBill.getItemInv(i);
          // �����кŹ�����е����л�û�����кš�
          if (voInv != null && voInv.getIsSerialMgt() != null
              && voInv.getIsSerialMgt().intValue() != 0
              && voMyBill.getItemValue(i, "serial") == null) {
            hasSN = false;
            break;
          }
        }
        // �Ѿ���������,�����ݷŵ���Ա�����У���ͬ��vo(needless now )2003-08-07
        if (hasLoc)
          m_alLocatorData = voMyBill.getLocators();
        if (hasSN)
          m_alSerialData = voMyBill.getSNs();
        if (hasLoc && hasSN)
          return;

        // =============================================================
        // ��ʼ������ if necessary
        if (m_alLocatorData == null) {
          m_alLocatorData = new ArrayList();
          for (i = 0; i < iRowCount; i++)
            m_alLocatorData.add(null);
        }
        if (m_alSerialData == null) {
          m_alSerialData = new ArrayList();
          for (i = 0; i < iRowCount; i++)
            m_alSerialData.add(null);
        }

        for (i = 0; i < iRowCount; i++)
          // ����ʵ��/������
          if (voMyBill.getItemValue(i, "ninnum") != null
              && voMyBill.getItemValue(i, "ninnum").toString()
                  .length() > 0
              || voMyBill.getItemValue(i, "noutnum") != null
              && voMyBill.getItemValue(i, "noutnum").toString()
                  .length() > 0)
            break;

        if (i >= iRowCount) // ������
          return; // =============================================================

        // ����ջ�λ�����к�����
        Integer iSearchMode = null;
        // ��Ҫ���λ
        if (!hasLoc
            && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.LOC)) {
          iSearchMode = new Integer(iMode);
        }
        // ��Ҫ�����к�
        if (!hasSN
            && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.SN)) {
          iSearchMode = new Integer(iMode);
        }
        if (iSearchMode == null)
          return;
        // WhVO voWh = voMyBill.getWh();
        // ��λ����Ĳֿ⣬���һ�û�л�λ����Ҫ����λ���ݡ����к�

        // iMode = 3;
        // Integer iSearchMode = new Integer(iMode);

        // ////////////////////////////iMode); //���λ & ���к� 3 or ���к� 4
        ArrayList alAllData = (ArrayList) GeneralBillHelper.queryInfo(
            iSearchMode, voMyBill.getPrimaryKey());
        // ����ջ�λ�����к�����
        ArrayList alTempLocatorData = null;
        ArrayList alTempSerialData = null;

        if (iMode == QryInfoConst.LOC_SN) {
          if (alAllData != null && alAllData.size() >= 2) {
            alTempLocatorData = (ArrayList) alAllData.get(0);
            alTempSerialData = (ArrayList) alAllData.get(1);
          } // else
        } else if (iMode == QryInfoConst.SN) { // === SN only
          if (alAllData != null && alAllData.size() >= 1)
            alTempSerialData = (ArrayList) alAllData.get(0);
          // else
        } else if (iMode == QryInfoConst.LOC) { // === LOC only
          if (alAllData != null && alAllData.size() >= 1)
            alTempLocatorData = (ArrayList) alAllData.get(0);
        } // else

        // --------------------------------------------------------

        // ����еĻ��û�λ����
        if (alTempLocatorData != null) {
          // �ŵ�vo�У����ݱ���idִ������ƥ�䡣
          voMyBill.setLocators(alTempLocatorData);
          // getLocators������ by hanwei 2004-01-06
          m_alLocatorData = voMyBill.getLocators();
        }
        // ����еĻ������к�����
        if (alTempSerialData != null) {
          // �ŵ�vo�У����ݱ���idִ������ƥ�䡣
          voMyBill.setSNs(alTempSerialData);
          // getSNs������ by hanwei 2004-01-06
          m_alSerialData = voMyBill.getSNs();

        }

      }

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }

  }

  /**
   * ������Դ������ʽ���ɵĿ�浥����Ҫ���ñ�ͷItem, �Ա㽫name��ʾ���б����ʹ�ӡ�еõ�item������. ��������: �������: ����ֵ:
   * �쳣����: ����:
   */
  public void resetAllHeaderRefItem() {
    if (getBillCardPanel().getHeadItem("cdispatcherid") != null) {
      // �շ����
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cdispatcherid").getComponent()).getRefName();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cdispatchername", sName);
    }
    if (getBillCardPanel().getHeadItem("cinventoryid") != null) {
      // �ӹ�Ʒ
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cinventoryid").getComponent()).getRefName();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cinventoryname", sName);
    }
    // ����¼��ʱ, ���ÿ����֯afterEdit������ڲֿ��ǰ��
    String sNewWhID = null;
    String sNewWhName = null;
    if (getM_voBill().getHeaderValue(IItemKey.WAREHOUSE) != null) {

      sNewWhName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem(IItemKey.WAREHOUSE).getComponent())
          .getRefName();
      sNewWhID = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem(IItemKey.WAREHOUSE).getComponent()).getRefPK();

    }

    if (getBillCardPanel().getHeadItem(IItemKey.CALBODY) != null) {
      // �����֯
      if (getM_voBill().getHeaderValue(IItemKey.CALBODY) != null) {
        nc.ui.pub.bill.BillEditEvent beEvent = new nc.ui.pub.bill.BillEditEvent(
            getBillCardPanel(), getM_voBill().getHeaderValue(
                IItemKey.CALBODY), IItemKey.CALBODY);
        afterCalbodyEdit(beEvent);
      }
    }

    if (getM_voBill().getHeaderValue(IItemKey.WAREHOUSE) != null) {
      if (getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE) != null) {
        nc.ui.pub.bill.BillEditEvent beEvent = new nc.ui.pub.bill.BillEditEvent(
            getBillCardPanel(), getM_voBill().getHeaderValue(
                IItemKey.WAREHOUSE), IItemKey.WAREHOUSE);
        getEditCtrl().afterWhEdit(beEvent, sNewWhName, sNewWhID);
      }

    }

    if (getBillCardPanel().getHeadItem("cwhsmanagerid") != null) {
      // ���Ա

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cwhsmanagerid").getComponent()).getRefName();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cwhsmanagername", sName);
    }
    if (getBillCardPanel().getHeadItem("cdptid") != null) {
      // ����
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cdptid").getComponent()).getRefName();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cdptname", sName);
    }
    if (getBillCardPanel().getHeadItem("cbizid") != null) {
      // ҵ��Ա

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cbizid").getComponent()).getRefName();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cbizname", sName);
    }
    if (getBillCardPanel().getHeadItem("cproviderid") != null) {
      // ��Ӧ��

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cproviderid").getComponent()).getRefName();
      String sRefPK = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cproviderid").getComponent()).getRefPK();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cprovidername", sName);
    }
    if (getBillCardPanel().getHeadItem("ccustomerid") != null) {
      // �ͻ�

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("ccustomerid").getComponent()).getRefName();
      String sRefPK = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("ccustomerid").getComponent()).getRefPK();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("ccustomername", sName);
      // ���ݿͻ���Ӧ�̹��˷��˵�ַ�Ĳ���

      filterVdiliveraddressRef(true, -1);

    }
    if (getBillCardPanel().getHeadItem("cbiztype") != null) {
      // ҵ������

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cbiztype").getComponent()).getRefName();
      // String sPK = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
      // .getHeadItem("cbiztype").getComponent()).getRefPK();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cbiztypename", sName);
    }
    if (getBillCardPanel().getHeadItem("cdilivertypeid") != null) {
      // ���˷�ʽ

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cdilivertypeid").getComponent()).getRefName();
      // �������������б���ʽ����ʾ��
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cdilivertypename", sName);
    }
    
  }

  /**
   * �˴����뷽��˵���� ���µ��� �������ڣ�(2002-11-27 10:32:34)
   * 
   * @return int
   * @param vo
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO
   */
  public void scanAddBarcodeline(nc.vo.ic.pub.barcodeparse.BarCodeParseVO vo)
      throws Exception {

    if (vo == null)
      return;

    String sRowPrimaryKey = getBarcodeCtrl().getBarcodeRowPrimaryKey(
        getEnvironment().getCorpID(), vo);

    // ͨ����������ж���Ĺؼ�����
    String[] sPrimaryKeyItems = vo.getMatchPrimaryKeyItems();

    BarCodeParseVO[] barCodeParseVOs = new BarCodeParseVO[] { vo };
    boolean bBox = false;
    scanadd(sRowPrimaryKey, barCodeParseVOs, bBox, sPrimaryKeyItems);
    // ��������ɨ���
    m_utfBarCode.requestFocus();
    return;
  }

  /**
   * �˴����뷽��˵���� ������ɨ����������� �������ڣ�(2004-3-12 15:57:22)
   * 
   * @param vo
   *            nc.vo.ic.pub.barcodeparse.BarCodeGroupVO
   */
  public void scanAddBoxline(BarCodeGroupVO barCodeGroupVO) throws Exception {

    BarCodeGroupHeadVO barCodeGroupHeadVO = (BarCodeGroupHeadVO) barCodeGroupVO
        .getParentVO();

    BarCodeParseVO[] barCodeParseVOs = (BarCodeParseVO[]) barCodeGroupVO
        .getChildrenVO();
    if (barCodeParseVOs == null || barCodeParseVOs.length == 0) {
      return;
    }

    // ��Ҫͨ��barCodeGroupHeadVO��ùؼ�����
    // Ŀǰ����װ��û�����ô�������������
    // �������óɴ������
    String[] sPrimaryKeyItems = new String[] { nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.InvManKey };

    String sRowPrimaryKey = getBarcodeCtrl().getBarGroupHeadRowPrimaryKey(
        getEnvironment().getCorpID(), barCodeGroupHeadVO,
        sPrimaryKeyItems);
    boolean bBox = true;
    scanadd(sRowPrimaryKey, barCodeParseVOs, bBox, sPrimaryKeyItems);
    // ��������ɨ���
    m_utfBarCode.requestFocus();
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2004-3-12 22:26:14)
   * 
   * @param sRowPrimaryKey
   *            java.lang.String
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]
   * 
   * �޸��ˣ������� �޸����ڣ�2007-7-2����06:29:22 �޸�ԭ��V53
   */
  protected void scanadd(String sRowPrimaryKey,
      BarCodeParseVO[] barCodeParseVOs, boolean bBox,
      String[] sPrimaryKeyItems) throws Exception {
    try {
      
      Boolean isNeedAppend = false;
      if (sPrimaryKeyItems != null)
        for (BarCodeParseVO barCodeParseVO : barCodeParseVOs){
          for (String sPrimaryKeyItem : sPrimaryKeyItems){
            if (null == barCodeParseVO
                .getAttributeValue(sPrimaryKeyItem))
              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000537")/*
                                   * @res
                                   * "�������ʧ�ܣ������������Ϲؼ������޶�Ӧ����"
                                   */);       
          }
          
        }
      // ȡ��ǰ��
      int iRow = getBillCardPanel().getBillTable().getSelectedRow();
       
      
      
      
      
      if (sRowPrimaryKey != null && barCodeParseVOs != null
          && sRowPrimaryKey.length() > 4
          && !sRowPrimaryKey.startsWith("NULL")) // �������д��ڴ����Ϣ
      {
        
        
        String checkInvManId = null;
        String checkInvcastunitid = null;
        if (sPrimaryKeyItems != null)
          for (BarCodeParseVO barCodeParseVO : barCodeParseVOs){
            for (String sPrimaryKeyItem : sPrimaryKeyItems){
              if (BarcodeparseCtrl.InvManKey.equals(sPrimaryKeyItem))
                checkInvManId = (String)barCodeParseVO.getAttributeValue(sPrimaryKeyItem);
              if (BarcodeparseCtrl.InvcastunitidKey.equals(sPrimaryKeyItem))
                checkInvcastunitid = (String)barCodeParseVO.getAttributeValue(sPrimaryKeyItem); 
              
  
            }
            
            if (null != checkInvManId && !"".equals(checkInvManId) 
                && null != checkInvcastunitid && !"".equals(checkInvcastunitid)){
              nc.vo.bd.b15.MeasureRateVO measureVO = m_voInvMeas.getMeasureRateDirect(getEnvironment().getCorpID(), checkInvManId, checkInvcastunitid);
              if (null == measureVO)
                throw new BusinessException(nc.ui.ml.NCLangRes
                    .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000557")/*
                                 * @res
                                 * "�����Ӧ�ĸ���λ���Ϸ�"
                                 */);
              
            }
            
          }

        // �������в����з�����������arraylist ,���ȴ���ѡ����
        ArrayList alResultTemp = getBarcodeCtrl().scanBillCardItem(
            sRowPrimaryKey, getM_voBill(), iRow, sPrimaryKeyItems);
        
        if (!bBox && (alResultTemp == null || alResultTemp.size() == 0) && null != getBillType() && ("45".equals(getBillType())||"4C".equals(getBillType())||"4Y".equals(getBillType()))){
          
          alResultTemp = getBarcodeCtrl().scanBillCardItem(
              sRowPrimaryKey, getM_voBill(), iRow, sPrimaryKeyItems,barCodeParseVOs);
          if (null != alResultTemp && 0 < alResultTemp.size())
            isNeedAppend = true;
        }

        ArrayList alResult = new ArrayList();

        // �������룬ֻ���õ�һ�У�����ʵ����������У��
        // �����޷�������������������쳣�ع�
        if (bBox && alResultTemp != null && alResultTemp.size() > 0) {
          alResult.add(alResultTemp.get(0));
        } else
          alResult = alResultTemp;

        // ���arraylistΪ�գ���len==0����ʾû�ж�Ӧ�������
        if ((alResult == null || alResult.size() == 0)) {

          if (barCodeParseVOs.length > 0 && !bBox)
            if (barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null
                && barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000524")/*
                                   * @res
                                   * "����ɨ��¼����������"
                                   */);

            }

          // (1)�Ե�������ⵥ�������Զ������е������
          // (2)��������������������ϣ�Ҳ������ɨ���Զ�������
          if (getButtonManager()
              .getButton(ICButtonConst.BTN_LINE_ADD) != null
              && getButtonManager().getButton(
                  ICButtonConst.BTN_LINE_ADD).isEnabled()
              && getBarcodeCtrl().isAddNewInvLine()) {
            // û�ж�Ӧ�Ĵ�����Զ�����һ�д��
            java.util.ArrayList alInvID = new java.util.ArrayList();
            alInvID.add(sRowPrimaryKey);

            boolean needCastunitname = false;
            if (((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
                && barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && barCodeParseVOs[0]
                .getBasstaddflag().booleanValue())
                || (barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null
                    && barCodeParseVOs[0]
                        .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
                    .getBasstaddflagsub().booleanValue()) || (barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
                && barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
                .getBasstaddflagsub().booleanValue()))) {
              needCastunitname = true;

            }

            int iCurFixLine = getBarcodeCtrl().fixBlankLineWithInv(
                this, getM_voBill(), alInvID, IItemKey.INVID,
                IItemKey.WAREHOUSE, IItemKey.CALBODY,
                getBillType(), IItemKey.CROWNO,
                sPrimaryKeyItems, needCastunitname);
            // �ûع��
            m_utfBarCode.requestFocus();

            // int rowNow = 0;
            boolean bAllforFix = true;
            int iNumUsed = 0;
            scanfixline(barCodeParseVOs, iCurFixLine, iNumUsed,
                bAllforFix);
            int icurline = getBillCardPanel().getBillTable()
                .getSelectedRow();
            if (icurline >= 0) {
              String vbatchcode = (String) getBillCardPanel()
                  .getBodyValueAt(icurline,
                      IItemKey.VBATCHCODE);
              if (vbatchcode != null
                  && vbatchcode.trim().length() > 0) {
                BillEditEvent ev = new BillEditEvent(
                    getBillCardPanel().getBodyItem(
                        IItemKey.VBATCHCODE), null,
                    vbatchcode, IItemKey.VBATCHCODE,
                    icurline, BillItem.BODY);
                getEditCtrl().afterLotEdit(ev);
              }

            }
          } else {

            /*
             * errormessageshow(nc.ui.ml.NCLangRes.getInstance()
             * .getStrByID("4008bill", "UPP4008bill-000127") @res
             * "ɨ��ʶ����µĴ�����룬����ǰ���ݽ��治���������Ӵ���У�" );
             */
            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000127")
            /*
             * @res "ɨ��ʶ����µĴ�����룬����ǰ���ݽ��治���������Ӵ���У�"
             */
            );

          }
        } else {
          int icurline = Integer.parseInt(alResult.get(0).toString());

          if (barCodeParseVOs.length > 0) {
            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)
                && (barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null)
                && (icurline != iRow)) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000524")/*
                                   * @res
                                   * "����ɨ��¼����������"
                                   */);

            }
            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)
                && (!getM_voBill().getItemVOs()[icurline]
                    .getIssecondarybarcode().booleanValue())) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000527")/*
                                   * @res
                                   * "�Ǵ������������֧��ɨ��¼¼�������"
                                   */);

            }

            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
                && barCodeParseVOs[0].getCbarcoderuleid() != null
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs() != null
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs().length > 0
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs()[getM_voBill()
                    .getItemVOs()[icurline].getBarCodeVOs().length - 1]
                    .getCbarcoderuleid() != null && (!getM_voBill()
                .getItemVOs()[icurline].getBarCodeVOs()[getM_voBill()
                .getItemVOs()[icurline].getBarCodeVOs().length - 1]
                .getCbarcoderuleid().equals(
                    barCodeParseVOs[0].getCbarcoderuleid())))) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000532")/*
                                   * @res
                                   * "ͬһ�����ͬһ�У�ֻ��ɨ��¼��ͬһ��������������"
                                   */);

            }

            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null
                && barCodeParseVOs[0].getCbarcoderuleidsub() != null
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs() != null
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs().length > 1
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs()[getM_voBill()
                    .getItemVOs()[icurline].getBarCodeVOs().length - 2]
                    .getCbarcoderuleidsub() != null && (!getM_voBill()
                .getItemVOs()[icurline].getBarCodeVOs()[getM_voBill()
                .getItemVOs()[icurline].getBarCodeVOs().length - 2]
                .getCbarcoderuleidsub().equals(
                    barCodeParseVOs[0]
                        .getCbarcoderuleidsub())))) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000533")/*
                                   * @res
                                   * "ͬһ�����ͬһ�У�ֻ��ɨ��¼��ͬһ�������Ĵ�����"
                                   */);

            }

            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)
                && (barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null)
                && (getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs() != null)
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs().length > 0
                && (getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs()[getM_voBill()
                    .getItemVOs()[icurline].getBarCodeVOs().length - 1]
                    .getBsingletype().booleanValue())
                && (getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs()[getM_voBill()
                    .getItemVOs()[icurline].getBarCodeVOs().length - 1]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000524")/*
                                   * @res
                                   * "����ɨ��¼����������"
                                   */);

            }
            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
                && (!getM_voBill().getItemVOs()[icurline]
                    .getIsprimarybarcode().booleanValue())) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000526")/*
                                   * @res
                                   * "���������������֧��ɨ��¼¼��������"
                                   */);

            }
            if ((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
                && (getM_voBill().getItemVOs()[icurline]
                    .getIsprimarybarcode().booleanValue())
                && (getM_voBill().getItemVOs()[icurline]
                    .getIssecondarybarcode().booleanValue())
                && (getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs() != null)
                && getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs().length > 0
                && (getM_voBill().getItemVOs()[icurline]
                    .getBarCodeVOs()[getM_voBill()
                    .getItemVOs()[icurline].getBarCodeVOs().length - 1]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null)) {
              if (icurline != iRow)
                getBillCardPanel().getBillTable()
                    .getSelectionModel()
                    .setSelectionInterval(icurline,
                        icurline);
              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000525")/*
                                   * @res
                                   * "����ɨ��¼����������"
                                   */);

            }

            if (((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
                && barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && barCodeParseVOs[0]
                .getBasstaddflag().booleanValue())
                || (barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null
                    && barCodeParseVOs[0]
                        .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
                    .getBasstaddflagsub().booleanValue()) || (barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
                && barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
                .getBasstaddflagsub().booleanValue()))
                && (getM_voBill().getItemVOs()[icurline]
                    .getCastunitname() == null
                    || (null != getM_voBill().getItemVOs()[icurline]
                        .getCastunitname() && ""
                        .equals(getM_voBill()
                            .getItemVOs()[icurline]
                            .getCastunitname()))
                    || null == getBillCardPanel()
                        .getBodyValueAt(icurline,
                            "castunitname") || (null != getBillCardPanel()
                    .getBodyValueAt(icurline,
                        "castunitname") && ""
                    .equals(getBillCardPanel()
                        .getBodyValueAt(icurline,
                            "castunitname")
                        .toString().trim())))) {

              throw new BusinessException(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000531")/*
                                   * @res
                                   * "�Ǹ�������������֧��ɨ��¼¼�밴���������ӵ�����"
                                   */);

            }
          }

          getBillCardPanel().getBillTable().getSelectionModel()
              .setSelectionInterval(icurline, icurline);
          scanUpdateLine(barCodeParseVOs, alResult);
          
          if (!bBox && isNeedAppend && null != getBillType() && ("45".equals(getBillType())||"4C".equals(getBillType())||"4Y".equals(getBillType()))){
            if (getBillCardPanel().getBillModel().getRowState(icurline) == BillModel.NORMAL)
              getBillCardPanel().getBillModel().setRowState(icurline,
                  BillModel.MODIFICATION);
            for(String key:sPrimaryKeyItems){
              if (null ==getBillCardPanel().getBodyValueAt(icurline, key) ){

                
                nc.ui.pub.bill.BillEditEvent event = null;
                if (key.startsWith("vfree")){
                  // ������
                  /*if (0 == getFreeItemRefPane().getFreeItemValueAll().size())
                    isCellEditable(true, icurline, "vfree0");*/
                  
    /*              InvVO invvo = getM_voBill().getItemInv(icurline);
                  if (null!= invvo && invvo.getIsFreeItemMgt() != null
                      && invvo.getIsFreeItemMgt().intValue() == 1) {
                    FreeVO freevo = getM_voBill().getItemVOs()[icurline].getFreeItemVO();
                    if (freevo != null && freevo.getVfree0() != null) {
                      
                       * if (freevo != null && freevo.getVfree0() != null &&
                       * freevo.getVfree0() != null && !"".equals(freevo.getVfree0())) {
                       
                      
                      if (invvo != null)
                        invvo.setFreeItemVO(freevo);
                      //getFreeItemRefPane().setFreeItemParam(ivoVO)
                      getBillCardPanel().setBodyValueAt(freevo.getVfree0(), icurline,
                          "vfree0");
                      for (int i= 1; i <= 10; i++) {
                        if (("vfree" + Integer.toString(i).trim()).equals(key)){
                          if (0 == getFreeItemRefPane().getFreeItemValueAll().size()){
                            InvVO voInv = getM_voBill().getItemInv(icurline);
                            for(int j = 1 ;j<=10;j++)
                              voInv.setFreeItemValue("vfree" + Integer.toString(j).trim(), null);
                            getFreeItemRefPane().setFreeItemParam(voInv);
                            for(int j = 1 ;j<=10;j++)
                              getFreeItemRefPane().getFreeItemValueAll().add(null);
                          }
                          getFreeItemRefPane().getFreeItemValueAll().set(i-1,barCodeParseVOs[0].getAttributeValue(key));
                        }
                            
                      }
                      
                    
                      
                      for (int i = 1; i <= FreeVO.FREE_ITEM_NUM; i++) {
                        if (getBillCardPanel().getBodyItem("vfree" + i) != null)

                          getBillCardPanel().setBodyValueAt(
                              freevo.getAttributeValue("vfree" + i), icurline,
                              "vfree" + i);
                        else
                          getBillCardPanel().setBodyValueAt(null, icurline,
                              "vfree" + i);
                      }
                      // �޸��ˣ������� �޸����ڣ�2007-12-28����04:49:54 �޸�ԭ��ͬ������VO�ͽ����ϵ�invvo
                      getBillCardPanel().setBodyValueAt(invvo, irow, "invvo");

                    }
                    
                    //getM_voBill().setItemFreeVO(irow, freevo);
                  }*/
                  isCellEditable(true, icurline, "vfree0");
                  getBillCardPanel().setBodyValueAt(barCodeParseVOs[0].getAttributeValue(key), icurline, key);
                  getM_voBill().getItemVOs()[icurline].setAttributeValue(key, barCodeParseVOs[0].getAttributeValue(key));
                  for (int i= 1; i <= 10; i++) {
                    if (("vfree" + Integer.toString(i).trim()).equals(key)){
                      getFreeItemRefPane().getFreeItemValueAll().set(i-1,barCodeParseVOs[0].getAttributeValue(key));
                    }
                        
                  }
                  FreeVO freevo = getFreeItemRefPane().getFreeVO();
                  getBillCardPanel().setBodyValueAt(freevo.getVfree0(), icurline,
                  "vfree0");

                  event = new nc.ui.pub.bill.BillEditEvent(
                      getBillCardPanel().getBodyItem("vfree0"),
                      barCodeParseVOs[0].getAttributeValue(key), "vfree0",icurline);
                }
                else{
                  getBillCardPanel().setBodyValueAt(barCodeParseVOs[0].getAttributeValue(key), icurline, key);
                  getM_voBill().getItemVOs()[icurline].setAttributeValue(key, barCodeParseVOs[0].getAttributeValue(key));
                  event = new nc.ui.pub.bill.BillEditEvent(
                      getBillCardPanel().getBodyItem(key),
                      barCodeParseVOs[0].getAttributeValue(key), key,icurline);
                }
                afterEdit(event);
              }
            }
          }
        }
      } else // �����в����ڴ����Ϣ
      {
        // ��Ҫ�ҵ�ǰ�����У�����ɨ�账��
        if (barCodeParseVOs.length > 0) {

          if (iRow < 0 && getBillCardPanel().getRowCount() == 1) {
            iRow = 0;
            getBillCardPanel().getBillTable().getSelectionModel()
                .setSelectionInterval(0, 0);
          }

          if (getM_voBill().getItemVOs() == null
              || iRow < 0
              || getM_voBill().getItemVOs().length < (iRow + 1)
              || getM_voBill().getItemVOs()[iRow] == null
              || getM_voBill().getItemVOs()[iRow]
                  .getCinventorycode() == null
                  || getM_voBill().getItemVOs()[iRow]
                                  .getCinventoryid() == null) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000534")/*
                                 * @res
                                 * "����ɨ��¼����ڴ����Ϣ��������"
                                 */);

          }
          
          
          String checkInvManId = null;
          String checkInvcastunitid = null;
          if (sPrimaryKeyItems != null)
            for (BarCodeParseVO barCodeParseVO : barCodeParseVOs){
              checkInvManId = getM_voBill().getItemVOs()[iRow].getCinventoryid();
              for (String sPrimaryKeyItem : sPrimaryKeyItems){

                if (BarcodeparseCtrl.InvcastunitidKey.equals(sPrimaryKeyItem))
                  checkInvcastunitid = (String)barCodeParseVO.getAttributeValue(sPrimaryKeyItem); 
                
    
              }
              
              if (null != checkInvManId && !"".equals(checkInvManId) 
                  && null != checkInvcastunitid && !"".equals(checkInvcastunitid)){
                nc.vo.bd.b15.MeasureRateVO measureVO = m_voInvMeas.getMeasureRateDirect(getEnvironment().getCorpID(), checkInvManId, checkInvcastunitid);
                if (null == measureVO)
                  throw new BusinessException(nc.ui.ml.NCLangRes
                      .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000557")/*
                                   * @res
                                   * "�����Ӧ�ĸ���λ���Ϸ�"
                                   */);
                
              }
              
            }
          

          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)
              && (getM_voBill().getItemVOs()[iRow]
                  .getCinventorycode() == null || getM_voBill()
                  .getItemVOs()[iRow].getBarCodeVOs() == null)) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000524")/*
                                 * @res
                                 * "����ɨ��¼����������"
                                 */);

          }

          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)
              && (!getM_voBill().getItemVOs()[iRow]
                  .getIssecondarybarcode().booleanValue())) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000527")/*
                                 * @res
                                 * "�Ǵ������������֧��ɨ��¼¼�������"
                                 */);

          }

          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
              && barCodeParseVOs[0].getCbarcoderuleid() != null
              && getM_voBill().getItemVOs()[iRow].getBarCodeVOs() != null
              && getM_voBill().getItemVOs()[iRow].getBarCodeVOs()[getM_voBill()
                  .getItemVOs()[iRow].getBarCodeVOs().length - 1]
                  .getCbarcoderuleid() != null && (!getM_voBill()
              .getItemVOs()[iRow].getBarCodeVOs()[getM_voBill()
              .getItemVOs()[iRow].getBarCodeVOs().length - 1]
              .getCbarcoderuleid().equals(
                  barCodeParseVOs[0].getCbarcoderuleid())))) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000532")/*
                                 * @res
                                 * "ͬһ�����ͬһ�У�ֻ��ɨ��¼��ͬһ��������������"
                                 */);

          }

          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null
              && barCodeParseVOs[0].getCbarcoderuleidsub() != null
              && getM_voBill().getItemVOs()[iRow].getBarCodeVOs() != null
              && getM_voBill().getItemVOs()[iRow].getBarCodeVOs().length > 1
              && getM_voBill().getItemVOs()[iRow].getBarCodeVOs()[getM_voBill()
                  .getItemVOs()[iRow].getBarCodeVOs().length - 2]
                  .getCbarcoderuleidsub() != null && (!getM_voBill()
              .getItemVOs()[iRow].getBarCodeVOs()[getM_voBill()
              .getItemVOs()[iRow].getBarCodeVOs().length - 2]
              .getCbarcoderuleidsub().equals(
                  barCodeParseVOs[0].getCbarcoderuleidsub())))) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000533")/*
                                 * @res
                                 * "ͬһ�����ͬһ�У�ֻ��ɨ��¼��ͬһ�������Ĵ�����"
                                 */);

          }

          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)
              && (barCodeParseVOs[0]
                  .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null)
              && (getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs() != null)
              && (getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs()[getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs().length - 1]
                  .getBsingletype().booleanValue())
              && (getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs()[getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs().length - 1]
                  .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null)) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000524")/*
                                 * @res
                                 * "����ɨ��¼����������"
                                 */);

          }
          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
              && (!getM_voBill().getItemVOs()[iRow]
                  .getIsprimarybarcode().booleanValue())) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000526")/*
                                 * @res
                                 * "���������������֧��ɨ��¼¼��������"
                                 */);

          }
          if ((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
              && (getM_voBill().getItemVOs()[iRow]
                  .getIsprimarybarcode().booleanValue())
              && (getM_voBill().getItemVOs()[iRow]
                  .getIssecondarybarcode().booleanValue())
              && (getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs() != null)
              && (getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs()[getM_voBill().getItemVOs()[iRow]
                  .getBarCodeVOs().length - 1]
                  .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null)) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000525")/*
                                 * @res
                                 * "����ɨ��¼����������"
                                 */);

          }

          if (((barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
              && barCodeParseVOs[0]
                  .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && barCodeParseVOs[0]
              .getBasstaddflag().booleanValue())
              || (barCodeParseVOs[0]
                  .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null
                  && barCodeParseVOs[0]
                      .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
                  .getBasstaddflagsub().booleanValue()) || (barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
              && barCodeParseVOs[0]
                  .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
              .getBasstaddflagsub().booleanValue()))
              && (getM_voBill().getItemVOs()[iRow]
                  .getCastunitname() == null
                  || (null != getM_voBill().getItemVOs()[iRow]
                      .getCastunitname() && ""
                      .equals(getM_voBill().getItemVOs()[iRow]
                          .getCastunitname()))
                  || null == getBillCardPanel()
                      .getBodyValueAt(iRow,
                          "castunitname") || (null != getBillCardPanel()
                  .getBodyValueAt(iRow, "castunitname") && ""
                  .equals(getBillCardPanel().getBodyValueAt(
                      iRow, "castunitname").toString()
                      .trim())))) {

            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000531")/*
                                 * @res
                                 * "�Ǹ�������������֧��ɨ��¼¼�밴���������ӵ�����"
                                 */);

          }
        }

        scanUpdateLineSelect(barCodeParseVOs);
      }
    } catch (Exception e) {
      throw nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
    }
  }

  /**
   * ���˷��˵�ַ���� �����ߣ����� ���ܣ���ʼ�����չ��� ������ ���أ� ���⣺ ���ڣ�(2001-7-17 10:33:20)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  public void filterVdiliveraddressRef(boolean ishead, int row) {
    try {
      // ���˱�ͷ���˵�ַ
      BillItem bicu = getBillCardPanel().getHeadItem("ccustomerid");
      if (bicu == null)
        return;
      String ccustomerid = (String) bicu.getValueObject();

      
      BillItem bmaddr = getBillCardPanel().getBodyItem(
          "vreceiveaddress");
      if (bmaddr != null) {
        nc.ui.pub.beans.UIRefPane vdlvr = (nc.ui.pub.beans.UIRefPane) bmaddr
            .getComponent();
        if (vdlvr != null) {
          if (ccustomerid == null
              || ccustomerid.trim().length() <= 0) {
             ((nc.ui.scm.ref.prm.CustAddrRefModel)
             vdlvr.getRefModel()).setCustId(null);
          } else {
             ((nc.ui.scm.ref.prm.CustAddrRefModel)
             vdlvr.getRefModel()).setCustId(ccustomerid);
          }

        }
      }

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }

  /**
   * �˴����뷽��˵���� ������ʹ�ã���������༭�޸�������Ӧ��������������ϵ��ҵ���߼� �������ڣ�(2004-4-29 9:08:49)
   * 
   * @param event1
   *            nc.ui.pub.bill.BillEditEvent
   */
  public void scanCheckNumEdit(nc.ui.pub.bill.BillEditEvent event1)
      throws Exception {

  }

  /**
   * �˴����뷽��˵���� ������VO������䵽���� �������ڣ�(2004-3-12 20:15:07)
   * 
   * @return int
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]���������� VO
   * 
   * @param iCurFixLine
   *            int����ǰ����,
   * 
   * @param iNumUsed
   *            int���Ѿ����ʹ�ù�����������
   * 
   * @param bAllforFix
   *            java.lang.Boolean ���Ƿ�Ҫ��ʣ������붼����
   * 
   * ����ֻ��һ�з��������Ĵ����������һ������£�Ҫ��ʣ������붼����
   * 
   * ���ر���������������
   */
  public int scanfixline(BarCodeParseVO[] barCodeParseVOs, int iCurFixLine,
      int iNumUsed, boolean bAllforFix) throws Exception {

    if (barCodeParseVOs == null || barCodeParseVOs.length == 0
        || iCurFixLine < 0) {
      return 0;
    }
    String sInvID = (String) getBillCardPanel().getBodyValueAt(iCurFixLine,
        IItemKey.INVID);
    if (sInvID == null || sInvID.length() == 0) {
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000111")/* @res "��ѡ���д�����ݵ��У�" */);
    }
    // �������
    int iNumforUse = scanfixline_fix(barCodeParseVOs, iCurFixLine,
        iNumUsed, bAllforFix); // ����������������

    // ��������
    getBarcodeCtrl().scanfixline_save(barCodeParseVOs, iCurFixLine,
        iNumUsed, iNumforUse, getM_voBill().getItemVOs(),true); // ����������������

    return iNumforUse;
  }

  /**
   * �˴����뷽��˵���� ������VO������䵽���� �������ڣ�(2004-3-12 20:15:07)
   * 
   * @return int
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]���������� VO
   * 
   * @param iCurFixLine
   *            int����ǰ����,
   * 
   * @param iNumUsed
   *            int���Ѿ����ʹ�ù�����������
   * 
   * @param bAllforFix
   *            java.lang.Boolean ���Ƿ�Ҫ��ʣ������붼����
   * 
   * ����ֻ��һ�з��������Ĵ����������һ������£�Ҫ��ʣ������붼����
   * 
   * ���ر���������������
   * 
   */
  protected int scanfixline_fix(BarCodeParseVO[] barCodeParseVOs,
      int iCurFixLine, int iNumUsed, boolean bAllforFix) throws Exception {

    if (barCodeParseVOs == null || barCodeParseVOs.length == 0
        || iCurFixLine < 0) {
      return 0;
    }

    // ����"�Ƿ񰴸���λ��������"���ԣ�������������Զ���һ�������������Զ���һ��
    String m_sMyItemKey = null;
    String m_sMyShouldItemKey = null;

    if (((barCodeParseVOs[0].getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
        && barCodeParseVOs[0]
            .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null && barCodeParseVOs[0]
        .getBasstaddflag().booleanValue())
        || (barCodeParseVOs[0]
            .getAttributeValue(BarcodeparseCtrl.VBARCODE) == null
            && barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
            .getBasstaddflagsub().booleanValue()) || (barCodeParseVOs[0]
        .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null
        && barCodeParseVOs[0]
            .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) != null && barCodeParseVOs[0]
        .getBasstaddflagsub().booleanValue()))
        && getBillCardPanel().getBodyValueAt(iCurFixLine,
            "castunitname") != null) {
      m_sMyItemKey = getEnvironment().getAssistNumItemKey();
      m_sMyShouldItemKey = getEnvironment().getShouldAssistNumItemKey();
    } else {
      m_sMyItemKey = getEnvironment().getNumItemKey();
      m_sMyShouldItemKey = getEnvironment().getShouldNumItemKey();
    }

    int iNumforUse = 0; // ����������������
    if (getBillCardPanel().getBodyItem("cinventorycode") != null) {
      // ʵ�ʷ�����
      UFDouble nFactNum = null;
      // Ӧ������
      UFDouble nShouldNum = null;
      UFDouble nFactBarCodeNum = null; // ʵ�ʷ���ʵ������������

      nc.vo.ic.pub.bc.BarCodeVO[] oldBarcodevos = getM_voBill()
          .getItemVOs()[iCurFixLine].getBarCodeVOs();

      if (oldBarcodevos == null || oldBarcodevos.length == 0)
        nFactBarCodeNum = UFDZERO;
      else {
        nFactBarCodeNum = UFDZERO;
        for (int i = 0; i < oldBarcodevos.length; i++) {
          // �޸��ˣ������� �޸����ڣ�2007-11-5����11:25:59 �޸�ԭ����ɾ�����벻��ͳ�ƽ���
          if (oldBarcodevos[i] != null
              && oldBarcodevos[i].getNnumber() != null
              && oldBarcodevos[i].getStatus() != nc.vo.pub.VOStatus.DELETED)
            nFactBarCodeNum = nFactBarCodeNum.add(oldBarcodevos[i]
                .getNnumber());
        }
      }

      // ʵ�ʷ�����
      Object oNum = getBillCardPanel().getBodyValueAt(iCurFixLine,
          m_sMyItemKey);
      if (oNum == null || oNum.toString().trim().length() == 0) {
        nFactNum = null;
        // ���û��Ӧ�������������ȫ������
      } else
        nFactNum = (UFDouble) oNum;

      // Ӧ������

      try {
        oNum = getBillCardPanel().getBodyValueAt(iCurFixLine,
            m_sMyShouldItemKey);
      } catch (Exception e) {
        oNum = null;
        nc.vo.scm.pub.SCMEnv.error(e);
      }
      if (oNum == null || oNum.toString().trim().length() == 0) {
        nShouldNum = null;
        // ���û��Ӧ�������������ȫ������
      } else
        nShouldNum = (UFDouble) oNum;

      boolean bNegative = false; // �Ƿ���
      if ((nFactNum != null && nFactNum.doubleValue() < 0)
          || (nShouldNum != null && nShouldNum.doubleValue() < 0)) {
        bNegative = true;
      }

      // �����������ݵ����ƥ���е��㷨
      iNumforUse = getBarcodeCtrl().scanfixlinenum(barCodeParseVOs,
          oldBarcodevos, iCurFixLine, iNumUsed, bAllforFix, nFactNum,
          nShouldNum);

      nFactBarCodeNum = nFactBarCodeNum.add(iNumforUse);

      // add by hanwei 2004-6-2
      // ������������Ӧ������,�������̵㵥���ɵ�����������ϼ
      // ���ܳ���Ӧ�������������޸ĵ�ʵ����������Ӧ������
      if (nShouldNum != null && nFactBarCodeNum != null
          && nFactBarCodeNum.doubleValue() > nShouldNum.doubleValue()
          && !getBarcodeCtrl().isOverShouldNum()) {
        nFactBarCodeNum = nShouldNum.abs();
      }

      if (nFactNum == null)
        nFactNum = UFDZERO;
      // �޸��ˣ������� �޸����ڣ�2007-9-10����02:15:22
      // �޸�ԭ�򣺶��ڵ�����������Ҳ���������ģ�ÿ�ΰ����������Ǹ�������
      if ((barCodeParseVOs[0]
          .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
          && (barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null)
          && (m_voBill.getItemVOs()[iCurFixLine]
              .getIsprimarybarcode().booleanValue())
          && (!m_voBill.getItemVOs()[iCurFixLine]
              .getIssecondarybarcode().booleanValue())
          && !barCodeParseVOs[0].getBsavebarcode().booleanValue()) {

        // ͬ��ʵ������
        if (bNegative || m_bFixBarcodeNegative)
          nFactNum = nFactNum.sub(iNumforUse);
        else
          nFactNum = nFactNum.add(iNumforUse);

        getBillCardPanel().setBodyValueAt(nFactNum, iCurFixLine,
            m_sMyItemKey);

        if (getBillCardPanel().getBodyItem("nbarcodenum") != null)
          getBillCardPanel().setBodyValueAt(nFactNum, iCurFixLine,
              "nbarcodenum");

        if (getBillCardPanel().getBodyItem("IItemKey.NBARCODENUM") != null)
          getBillCardPanel().setBodyValueAt(nFactNum, iCurFixLine,
              IItemKey.NBARCODENUM);

        // Ӧ����������ͬ��
        nc.ui.pub.bill.BillEditEvent event1 = new nc.ui.pub.bill.BillEditEvent(
            getBillCardPanel().getBodyItem(m_sMyItemKey), nFactNum,
            m_sMyItemKey, iCurFixLine);
        // ��������༭ҵ���߼�
        scanCheckNumEdit(event1);
        afterEdit(event1);
        // ִ��ģ�湫ʽ
        getGenBillUICtl().execEditFormula(getBillCardPanel(),
            iCurFixLine, m_sMyItemKey);

        // ����������״̬Ϊ�޸�
        if (getBillCardPanel().getBodyValueAt(iCurFixLine,
            IItemKey.NAME_BODYID) != null)
          getBillCardPanel().getBillModel().setRowState(iCurFixLine,
              BillMode.Update);

      } else {

        // ʵ������С��������������ȥ�޸Ľ����ϵ�ʵ������
        if (nFactNum.doubleValue() < nFactBarCodeNum.doubleValue()
            && !((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
                && (barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null)
                && (m_voBill.getItemVOs()[iCurFixLine]
                    .getIsprimarybarcode().booleanValue()) && (m_voBill
                .getItemVOs()[iCurFixLine]
                .getIssecondarybarcode().booleanValue()))) {

          // ͬ��ʵ������
          if (bNegative || m_bFixBarcodeNegative)
            nFactBarCodeNum = nFactBarCodeNum.multiply(UFDNEGATIVE);

          getBillCardPanel().setBodyValueAt(nFactBarCodeNum,
              iCurFixLine, m_sMyItemKey);

          if (getBillCardPanel().getBodyItem("nbarcodenum") != null)
            getBillCardPanel().setBodyValueAt(nFactBarCodeNum,
                iCurFixLine, "nbarcodenum");

          if (getBillCardPanel().getBodyItem("IItemKey.NBARCODENUM") != null)
            getBillCardPanel().setBodyValueAt(nFactBarCodeNum,
                iCurFixLine, IItemKey.NBARCODENUM);

          // Ӧ����������ͬ��
          nc.ui.pub.bill.BillEditEvent event1 = new nc.ui.pub.bill.BillEditEvent(
              getBillCardPanel().getBodyItem(m_sMyItemKey),
              nFactBarCodeNum, m_sMyItemKey, iCurFixLine);
          // ��������༭ҵ���߼�
          scanCheckNumEdit(event1);
          afterEdit(event1);
          // ִ��ģ�湫ʽ
          getGenBillUICtl().execEditFormula(getBillCardPanel(),
              iCurFixLine, m_sMyItemKey);

          // ����������״̬Ϊ�޸�
          if (getBillCardPanel().getBodyValueAt(iCurFixLine,
              IItemKey.NAME_BODYID) != null)
            getBillCardPanel().getBillModel().setRowState(
                iCurFixLine, BillMode.Update);
        }
      }

    }

    return iNumforUse;
  }

  /**
   * �˴����뷽��˵���� ���и����������ݣ� �Գ���Ӧ�������������������Ƶ���һ�и���
   * 
   * barCodeParseVOs:��������VO[] alFixRowNO��ArrayList��ÿ�д��д���������ݣ�String ���� int
   * ������
   * 
   * �������ڣ�(2004-3-12 19:23:05)
   * 
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]
   * @param alFixRowNO
   *            java.util.ArrayList
   */
  protected void scanUpdateLine(BarCodeParseVO[] barCodeParseVOs,
      ArrayList alFixRowNO) throws Exception {

    if (barCodeParseVOs == null || barCodeParseVOs.length == 0
        || alFixRowNO == null || alFixRowNO.size() == 0) {
      return;
    }

    int iNumAll = barCodeParseVOs.length;
    int iNumUsed = 0; // �ۼ�ͳ������
    int ifixRows = alFixRowNO.size();
    int iCurFixLine = 0; // ���µ�ǰ��
    int ifixSingleLineNum = 0;

    for (int i = 0; i < ifixRows; i++) {
      iCurFixLine = Integer.parseInt((String) alFixRowNO.get(i));

      if (ifixRows == 1) {
        // ֻ��һ�У�ȫ����䵱ǰ��
        ifixSingleLineNum = scanfixline(barCodeParseVOs, iCurFixLine,
            0, true);
        break;
      } else {
        if (i == ifixRows - 1) // ���һ�У�������е�����
        {
          ifixSingleLineNum = scanfixline(barCodeParseVOs,
              iCurFixLine, iNumUsed, true);
          break;
        } else // �м������Ӧ��������
        {
          ifixSingleLineNum = scanfixline(barCodeParseVOs,
              iCurFixLine, iNumUsed, false);
        }
        iNumUsed = iNumUsed + ifixSingleLineNum;
        if (iNumUsed == iNumAll) {
          // ��������
          break;
        }
      }

    }
  }

  /**
   * �˴����뷽��˵���� ������VO������䵽����Ľ����� �������ڣ�(2004-3-12 20:15:07)
   * 
   * @return int
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]���������� VO
   */
  protected void scanUpdateLineSelect(BarCodeParseVO[] barCodeParseVOs)
      throws Exception {
    // ȡ��ǰ��
    int iCurFixLine = 0;
    int rowNow = getBillCardPanel().getBillTable().getSelectedRow();
    if (rowNow < 0) {
      // ��ʾ������Ϣ
      throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
          .getInstance().getStrByID("4008bill", "UPP4008bill-000112")/*
                                         * @res
                                         * "��ѡ���Ӧ����Ĵ���У�"
                                         */);
    } else {
      iCurFixLine = rowNow;
    }
    boolean bAllforFix = true;
    int iNumUsed = 0;

    if (getM_voBill() != null && getM_voBill().getItemVOs() != null
        && getM_voBill().getItemVOs().length > iCurFixLine
        && getM_voBill().getItemVOs()[iCurFixLine] != null) {
      if (getM_voBill().getItemVOs()[iCurFixLine].getBarcodeManagerflag()
          .booleanValue()
          && getM_voBill().getItemVOs()[iCurFixLine]
              .getBarcodeClose().booleanValue() == false) {
        scanfixline(barCodeParseVOs, iCurFixLine, iNumUsed, bAllforFix);
      } else {
        throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
            .getInstance().getStrByID("4008bill",
                "UPP4008bill-000113")/*
                             * @res
                             * "��ǰ�з������������������ѹرգ���"
                             */);
      }
    }
  }

  /**
   * �˴����뷽��˵���� ��������:����ҳ�� ���ߣ������� �������:��ǰҳ�롣 ����ֵ: �쳣����: ����:(2003-7-5 13:14:52)
   * 
   * @param iSelect
   *            int
   */
  protected void scrollBill(int iCur) {
    if (getM_alListData() != null && getM_alListData().size() > 0) {
      setM_voBill((GeneralBillVO) getM_alListData().get(iCur));
      GeneralBillItemVO voitem[] = getM_voBill().getItemVOs();
      if (voitem == null || voitem.length == 0)
        qryItems(new int[] { iCur }, new String[] { getM_voBill()
            .getPrimaryKey() });
      // re-get
      setM_voBill((GeneralBillVO) getM_alListData().get(iCur));
      selectListBill(iCur);
      setBillVO(getM_voBill());
      setLastHeadRow(iCur);
      m_iCurDispBillNum = getM_iLastSelListHeadRow();
    }
  }

  /**
   * �����ߣ����˾� ���ܣ������޸ĺ����������е�PK,��ͬʱˢ�´����VO. Ҫ��֤VO��Item��˳��ͽ�������һ�¡� ������ ���أ� ���⣺
   * ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBarcodePkAfterImp(ArrayList alBodyPK) {
    nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel().getBillModel();
    if (bmTemp == null) {
      SCMEnv.out("bm null ERROR!");
      return;
    }
    if (alBodyPK == null || alBodyPK.size() == 0) {
      SCMEnv.out("no row add.");
      return;
    }
    int rowCount = getBillCardPanel().getRowCount();
    int pk_count = 0;
    for (int row = 0; row < rowCount; row++) {

      // ����ñ�����������,������ID����BarCodeVO��
      if (getM_voBill().getItemVOs()[row].getBarCodeVOs() != null) {
        for (int j = 1; j <= getM_voBill().getItemVOs()[row]
            .getBarCodeVOs().length; j++) {
          if (getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1] != null
              && getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1]
                  .getStatus() == nc.vo.pub.VOStatus.NEW) {
            // ������������
            getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1]
                .setPrimaryKey(alBodyPK.get(pk_count + 1)
                    .toString().trim());
            // ���ñ�������
            getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1]
                .setCgeneralbid(getM_voBill().getItemVOs()[row]
                    .getCgeneralbid());
          }
        }

      }
    }
  }
  
  
  protected void transBillDataDeal(GeneralBillVO vo) throws BusinessException {

    if (null == vo || null == vo.getItemVOs() || vo.getItemVOs().length == 0)
      return;

    HashMap<String, ArrayList> hm_paras = new HashMap<String, ArrayList>();

    // ��ȫ������Ϣ
    String cbilltypecode = vo.getHeaderVO().getCbilltypecode();
    String cfirsttype = null;
    if (BillTypeConst.m_saleOut.equals(cbilltypecode)
        || BillTypeConst.m_allocationOut.equals(cbilltypecode)) {
      cfirsttype = vo.getItemVOs()[0].getCfirsttype();
      if (cfirsttype != null) {
        IBillType billType = BillTypeFactory.getInstance().getBillType(
            cfirsttype);
        if (cfirsttype.equals(BillTypeConst.SO_Order)
            || cfirsttype.equals(BillTypeConst.SO5_ReturnApp)
            || billType.typeOf(ModuleCode.TO)) {
          ArrayList<String> al_para = new ArrayList<String>();
          al_para.add(0, cfirsttype);
          String bid = null, firstbid = null;
          int i = 1;
          for (GeneralBillItemVO item : vo.getItemVOs()) {
            firstbid = item.getCfirstbillbid();
            if (null == firstbid || "".equals(firstbid.trim()))
              continue;
            al_para.add(i, firstbid);
            i++;
          }
          if (al_para.size() > 1)
            hm_paras.put(IICParaConst.GetOrderInfoPara, al_para);
        }
      }
    }

    // ������ε���������Ҫ�Ĳ���
    ArrayList<String> al_qBat = BatchCodeDefSetTool.getQueryParas(vo
        .getItemVOs());
    if (null != al_qBat && al_qBat.size() > 0
        && !hm_paras.containsKey(IICParaConst.BatchCodePara))
      hm_paras.put(IICParaConst.BatchCodePara, al_qBat);

    // ��ø��ݲ���Ա�������š�ҵ��Ա��Ҫ�Ĳ���
    ArrayList<String> al_qDptBizer = new ArrayList<String>();
    al_qDptBizer.add(0, ClientEnvironment.getInstance().getCorporation()
        .getPrimaryKey());// ��˾PK
    al_qDptBizer.add(1, ClientEnvironment.getInstance().getUser()
        .getPrimaryKey());// ����ԱID
    hm_paras.put(IICParaConst.DptBizerPara, al_qDptBizer);

    // �������ֿ���Ϣ�Ĳ���
    if (vo.getHeaderValue(IItemKey.WAREHOUSE) != null) {
      ArrayList<String> al_qWhInfo = new ArrayList<String>();
      String sWhID = vo.getHeaderValue(IItemKey.WAREHOUSE).toString().trim();
      al_qWhInfo.add(sWhID);
      hm_paras.put(IICParaConst.ReSetWHInfoPara, al_qWhInfo);
    }

    // ���ݿ����֯���ֿ�ȡ�ɱ���
    String sCalID = null;
    String sWhID = null;
    String sCorpID = null;
    if (vo.getHeaderValue(IItemKey.CALBODY) != null
        || vo.getHeaderValue(IItemKey.WAREHOUSE) != null) {
      sCalID = (String) vo.getHeaderValue(IItemKey.CALBODY);
      sWhID = (String) vo.getHeaderValue(IItemKey.WAREHOUSE);
      sCorpID = (String) vo.getHeaderValue(IItemKey.PKCORP);
      if (null == sCorpID)
        sCorpID = ClientEnvironment.getInstance().getCorporation()
          .getPrimaryKey();
      ArrayList<String> al_qCostCal = new ArrayList<String>();
      al_qCostCal.add(0, sCalID);
      al_qCostCal.add(1, sWhID);
      al_qCostCal.add(2, sCorpID);
      hm_paras.put(IICParaConst.CostLandPara, al_qCostCal);
    }

    // ���ݴ���������Ϳ����֯ȡ�ƻ��ۡ�����XX
    ArrayList<String> al_qJHJ = new ArrayList<String>();
    for (GeneralBillItemVO item : vo.getItemVOs()) {
      if (!al_qJHJ.contains(item.getCinventoryid()))
        al_qJHJ.add(item.getCinventoryid());
    }
    if (al_qJHJ.size() > 0)
      hm_paras.put(IICParaConst.ProduceJHJPara, al_qJHJ);

    try {
      HashMap<String, Object> hmRet = GeneralBillHelper
          .transBillDataDeal(hm_paras);
      // ��������Ϣ
      if (hmRet.containsKey(IICParaConst.GetOrderInfoPara)) {
        String[] itemkeys = (String[]) hmRet
            .get(IICParaConst.ICBillOrderItemsPara);
        ICDataSet ds = (ICDataSet) hmRet.get(IICParaConst.GetOrderInfoPara);
        String cfirstbillbid = null;
        if (null != ds) {
          GeneralBillItemVO[] voi = vo.getItemVOs();
          for (GeneralBillItemVO item : voi) {
            cfirstbillbid = item.getCfirstbillbid();
            ds.fillVOByPk(new String[] {
              cfirstbillbid
            }, itemkeys, itemkeys, item);
          }

          UFDouble d100 = new UFDouble(100);
          UFDouble nrate = null;
          if (cfirsttype.equals(BillTypeConst.SO_Order)
              || cfirsttype.equals(BillTypeConst.SO5_ReturnApp)) {
            UFDouble SO29 = CheckTools
                .toUFDouble(nc.ui.pub.para.SysInitBO_Client.getParaString(
                    ClientEnvironment.getInstance().getCorporation()
                        .getPrimaryKey(), "SO29"));
            if (nc.vo.ic.pub.GenMethod.isEQZeroOrNull(SO29))
              SO29 = null;
            else
              SO29 = SO29.div(d100);
            for (int i = 0; i < voi.length; i++) {
              nrate = CheckTools.toUFDouble(voi[i]
                  .getAttributeValue(IItemKey.nordcloserate));
              if (nrate == null)
                voi[i].setAttributeValue(IItemKey.nordcloserate, SO29);
              else
                voi[i].setAttributeValue(IItemKey.nordcloserate, nrate
                    .div(d100));
              voi[i].setAttributeValue(IItemKey.bcloseord, voi[i]
                  .getAttributeValue(IItemKey.bcloseyetord));
            }
          }
          else {
            for (int i = 0; i < voi.length; i++) {
              nrate = CheckTools.toUFDouble(voi[i]
                  .getAttributeValue(IItemKey.nordcloserate));
              if (!nc.vo.ic.pub.GenMethod.isEQZeroOrNull(nrate))
                voi[i].setAttributeValue(IItemKey.nordcloserate, nrate
                    .div(d100));
              voi[i].setAttributeValue(IItemKey.bcloseord, voi[i]
                  .getAttributeValue(IItemKey.bcloseyetord));
            }
          }
        }
      }

      // ȡ���κŽ��
      if (hmRet.containsKey(IICParaConst.BatchCodePara)) {
        ICDataSet ds = (ICDataSet) hmRet.get(IICParaConst.BatchCodePara);
        String sPk_invbasdoc = null;
        String sVbatchCode = null;
        for (GeneralBillItemVO item : vo.getItemVOs()) {
          sPk_invbasdoc = (String) item.getAttributeValue("pk_invbasdoc");
          sVbatchCode = (String) item.getAttributeValue("vbatchcode");
          if (null == sPk_invbasdoc || "".equals(sPk_invbasdoc.trim())
              || null == sVbatchCode || "".equals(sVbatchCode.trim()))
            continue;
          ds.fillVOByPk(new String[] {
              sPk_invbasdoc, sVbatchCode
          }, IItemKey.batchcodefielsAtBillBody, IItemKey.batchcodefielsAtBillBody, item);
          //���ý���VO�������ȼ�����
          String qualitylevelName=(String)ds.getValuesByPk(new String[]{sPk_invbasdoc, sVbatchCode},53);
          item.setAttributeValue("cqualitylevelname", qualitylevelName);
        }
      }
      // ȡ���š�ҵ��Ա���
      if (hmRet.containsKey(IICParaConst.DptBizerPara))
        ICCommonBusi.getHm_userid_psndocvo().put(
            ClientEnvironment.getInstance().getCorporation().getPrimaryKey()
                + ClientEnvironment.getInstance().getUser().getPrimaryKey(),
            (PsndocVO) hmRet.get(IICParaConst.DptBizerPara));
      // ȡ����ֿ���
      if (hmRet.containsKey(IICParaConst.ReSetWHInfoPara)) {
        WhVO whvo = (WhVO) hmRet.get(IICParaConst.ReSetWHInfoPara);
        hm_whid_vo.put(whvo.getCwarehouseid(), whvo);
      }
      // ȡ�ɱ���
      if (hmRet.containsKey(IICParaConst.CostLandPara)) {
        String sCostLand = (String) hmRet.get(IICParaConst.CostLandPara);
        getInvoInfoBYFormula().getHm_calwhid_costid().put(sCalID + sWhID,
            sCostLand);
      }
      // ȡ����ƻ��ۡ�����XX
      if (hmRet.containsKey(IICParaConst.ProduceJHJPara)) {
        HashMap<String, Object> hm_qJHJ = (HashMap<String, Object>) hmRet
            .get(IICParaConst.ProduceJHJPara);
        String key = null;
        for (Map.Entry<String, Object> entry : hm_qJHJ.entrySet()) {
          key = entry.getKey();
          if (!getInvoInfoBYFormula().getHm_invmanCal_obj().containsKey(key))
            getInvoInfoBYFormula().getHm_invmanCal_obj().put(key,
                entry.getValue());
        }
      }
     
    }
    catch (Exception e) {
      // ��־�쳣
      nc.vo.scm.pub.SCMEnv.out(e);
      throw GenMethod.handleException(e.getMessage(), e);
    }

  }
  
  

  /**
   * �˴����뷽��˵���� ����ʽ�����ϵ����ݼ��ص���浥�ݽ����� BusiTypeID��ҵ������ID,���û��Ϊnull
   * vos:���ݵ�AggregatedValueObject[]��ʵ������VO����ͨ���ݵ�VO Ŀǰ�÷�������ͨ���ݻ�����Ͳɹ���⡢ί��ӹ����������
   * �������ڣ�(2003-10-14 14:29:30)
   * 
   * @param BusiTypeID
   *          java.lang.String
   * @param vos
   *          nc.vo.pub.AggregatedValueObject[]
   */
  protected void setBillRefResultVO(String sBusiTypeID,
      nc.vo.pub.AggregatedValueObject[] vos) throws Exception {

    if (vos == null || vos.length == 0)
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
          .getStrByID("4008bill", "UCH003")/* @res "��ѡ��Ҫ��������ݣ�" */);

    if (vos != null && !(vos instanceof GeneralBillVO[])) {
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000114")/*
                             * @res
                             * "��������ת��������鿴����̨����ϸ��ʾ��Ϣ��"
                             */);
    }

    // v5 ��������VO��������֤VO����ȷ�ԣ����ĳЩ�ֶ�ֵ
    GeneralBillVO voRet = VoCombine.combinVo(vos);

    if (voRet != null && voRet.getItemVOs() != null
        && voRet.getItemVOs().length > 0) {
      for (int i = 0, loop = voRet.getItemVOs().length; i < loop; i++)
        voRet.getItemVOs()[i].procLocNumDigit(m_ScaleValue
            .getNumScale(), m_ScaleValue.getAssistNumScale());

      // set user selected ҵ������ 20021209
      voRet.setHeaderValue("cbiztype", sBusiTypeID);
      // ͨ�����ݹ�ʽ������ִ���йع�ʽ�����ķ���
      getFormulaBillContainer().formulaHeaders(getFormulaItemHeader(),
          voRet.getHeaderVO());
      transBillDataDeal(voRet);
//      BatchCodeDefSetTool.execFormulaForBatchCode(voRet.getItemVOs());

      // ���洫��ĵ���VO�����滻�����մ���Ĵ��IDʼ���Ǹõ���VO�д��ID��
      m_voBillRefInput = voRet;
      // lTime = System.currentTimeMillis();
      // ��������1
      onAdd(false, null);

      // �����˿�״̬��ֻ�����븺�� add by hanwei 2004-05-08
      if (voRet.getHeaderVO() != null
          && voRet.getHeaderVO().getFreplenishflag().booleanValue()) {
        nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(this,
            true);
      } else
        nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(this,
            false);

      // ��յ��ݺ�
      voRet.getHeaderVO().setPrimaryKey(null);
      voRet.getHeaderVO().setVbillcode(null);

      // ���ӵ����кţ�zhx added on 20030630 support for incoming bill
      nc.ui.scm.pub.report.BillRowNo.setVORowNoByRule(voRet,
          getBillType(), IItemKey.CROWNO);

      // ����ֿ�����
      resetWhInfo(voRet);

      // �������д������
      resetAllInvInfo(voRet);
      
      for(int i = 0 ;i < voRet.getItemVOs().length ; i++){
        setBodyInSpace(i, voRet.getItemVOs()[i].getInv());
      }

      // �����������ݳ�ʼ���ݣ���ΪsetTempBillVO����������ˡ�
      int iOriginalItemCount = voRet.getItemCount();
      // �˵�������С�
      GeneralBillItemVO[] itemvo = getM_voBill().filterItem();
      if (itemvo == null || itemvo.length == 0) {
        setM_voBill(null);
        clearUi();
        m_voBillRefInput = null;
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000115")/*
                                     * @res
                                     * "���յ����д������Ϊ�ۿۻ��������ԵĴ�������޸Ĵ�����ٹ���¼�롣"
                                     */);

      } else if (iOriginalItemCount > itemvo.length) { // ����˵�����
        getM_voBill().setChildrenVO(itemvo);
        setImportBillVO(getM_voBill(), false);
        m_voBillRefInput.setChildrenVO(itemvo);

      }
      
      // ������������������״̬ �޸���:������ �޸�����:2007-04-04
      if (itemvo != null) {

        for (int i = 0; i < itemvo.length; i++) {
          if (itemvo[i].getBarCodeVOs() != null)
            nc.vo.ic.pub.SmartVOUtilExt.modifiVOStatus(itemvo[i]
                .getBarCodeVOs(), nc.vo.pub.VOStatus.NEW);
        }
      }
      // �޸��ˣ������� �޸����ڣ�2007-10-19����10:14:29 �޸�ԭ��ˢ���������
      nc.vo.ic.pub.GenMethod.fillGeneralBillVOByBarcode(getM_voBill());

      setNewBillInitData();

      GeneralBillUICtl.calcNordcanoutnumAfterRefAdd(getM_voBill(),
          getBillCardPanel(), getBillType());

      setButtonStatus(true);

      ctrlSourceBillUI(true);

      // set user selected ҵ������ 20021209
      if (getBillCardPanel().getHeadItem("cbiztype") != null
          && sBusiTypeID != null) {
        getBillCardPanel().setHeadItem("cbiztype", sBusiTypeID);

        nc.ui.pub.bill.BillEditEvent event = new nc.ui.pub.bill.BillEditEvent(
            getBillCardPanel().getHeadItem("cbiztype"),
            sBusiTypeID, "cbiztype");
        afterEdit(event);
      }
      for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
        if (getBillCardPanel().getBodyValueAt(i,
            getEnvironment().getNumItemKey()) != null)
          getBillCardPanel().getBillModel().execEditFormulaByKey(i,
              getEnvironment().getNumItemKey());
        if (getBillCardPanel().getBodyValueAt(i,"vfirstbillcode") == null)
          getBillCardPanel().setBodyValueAt(getBillCardPanel().getBodyValueAt(i,"vsourcebillcode"), i, "vfirstbillcode");
      }
      // end set user selected ҵ������

      // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
      // "SCMCOMMON", "UPPSCMCommon-000133")/* @res "����" */);

    } else {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000116")/*
                             * @res
                             * "��˫��ѡ�����¼�뵥�ݵı�ͷ�������У�"
                             */);
    }
    
    // modified by liuzy 2009-9-21 ����10:15:09 ת����ͳһ����ϼ�
    getBillCardPanel().getBillModel().setNeedCalculate(true);
    getBillCardPanel().getBillModel().reCalcurateAll();

  }

  /*
   * ��Ե��ݣ���������ⵥ �������ã� 1 ���õ���ֿ�-4Y /�����ֿ�-4E ���չ��� 2 �Ե������ⵥ������ջ���λ����������
   * 
   * �۱� on Jun 13, 2005
   */
  protected void setBillRefIn4Eand4Y(GeneralBillVO voBill) {
    nc.ui.pub.bill.BillItem bi = null;

    // ����ֿ�-4Y /�����ֿ�-4E ����

    bi = getBillCardPanel().getHeadItem("cotherwhid");

    if (bi != null) {
      nc.ui.pub.beans.UIRefPane ref = (nc.ui.pub.beans.UIRefPane) bi
          .getComponent();
      if (ref != null) {
        ref.getRefModel().setPk_corp(
            (String) voBill.getHeaderValue("cothercorpid"));

        int fallocflag = CONST.IC_ALLOCINSTORE; // ������
        String isDirectStor = "N";

        if (voBill.getHeaderValue("fallocflag") != null)
          fallocflag = voBill.getHeaderVO().getFallocflag()
              .intValue();

        if (fallocflag == CONST.IC_ALLOCDIRECT) // ��Ϊֱ�˵���
          isDirectStor = "Y";

        ref.setWhereString(" pk_calbody ='"
            + (String) voBill.getHeaderValue("cothercalbodyid")
            + "' and isdirectstore='" + isDirectStor + "'");
      }
    }
    Object coutcorpid = voBill.getHeaderVO()
        .getAttributeValue("coutcorpid");

    bi = getBillCardPanel().getHeadItem("cproviderid");

    if (bi != null)
      ((nc.ui.pub.beans.UIRefPane) bi.getComponent()).getRefModel()
          .setPk_corp(coutcorpid.toString());

    bi = getBillCardPanel().getHeadItem("ccustomerid");

    if (bi != null)
      ((nc.ui.pub.beans.UIRefPane) bi.getComponent()).getRefModel()
          .setPk_corp(coutcorpid.toString());

    // �Ե������ⵥ������ջ���λ���������ˡ�
    if (!getBillType().equals("4Y")) // �ǵ������ⵥ
      return;
    // �ջ���λ
    bi = getBillCardPanel().getBodyItem("vrevcustname");
    if (bi != null) {
      nc.ui.pub.beans.UIRefPane ref = (nc.ui.pub.beans.UIRefPane) bi
          .getComponent();
      if (ref != null)
        ref.getRefModel().setPk_corp(
            (String) voBill.getHeaderValue("cothercorpid"));
    }
  }

  /**
   * �����ߣ����˾� ���ܣ��ڱ�������ʾVO ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setBillVO(GeneralBillVO bvo, boolean bUpdateBotton,
      boolean bExeFormule) {
    // ����ǿգ������ʾ
    if (bvo == null) {
      getBillCardPanel().getBillData().clearViewData();
      getBillCardPanel().updateValue();
      return;
    }
    
//  ���ο�����չ
    getPluginProxy().beforeSetBillVOToCard(bvo);
    
    try {
      long lTime = System.currentTimeMillis();
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      getBillCardPanel().removeBillEditListenerHeadTail();

      // ����һ��clone()
      setM_voBill((GeneralBillVO) bvo.clone());

      // ��������ⵥ�������������⴦��
      // ��Ӧ���ڻ��෽���С�
      // shawbing on Jun 13, 2005
      if (getBillType().equals("4Y") || getBillType().equals("4E"))
        setBillRefIn4Eand4Y(getM_voBill());

      // ʹ�ù�ʽ��ѯ������Ҫ�ֹ�������������
      // ��Ҫ�Ļ���������������
      if (m_bIsByFormula)
        bvo.calPrdDate();
      SCMEnv.showTime(lTime, "setBillVO:bvo.clone()");
      // �޸��ˣ������� �޸����ڣ�2007-9-6����09:21:35
      // �޸�ԭ�򣺽��Ҫ���ڿ�Ƭ�£���һ����ʾ����ʱ����ִ�����ε�����ʽ�������������ں�ʧЧ���ڵ�����
      // ִ�����ε�����ʽ
      // �޸��ˣ������� �޸����ڣ�2007-10-30����03:59:09
      // �޸�ԭ���ڵ���������״̬ʱ����Ҫȥˢ��������Ϣ����Ϊ���ⵥ���ܻ�����������֮ǰˢ�µ��ݽ���
      if (null != bvo.getItemVOs()
          && nc.vo.pub.VOStatus.NEW != bvo.getStatus())
        BatchCodeDefSetTool.execFormulaForBatchCode(bvo.getItemVOs());

      // ��������
      lTime = System.currentTimeMillis();
      // modified by liuzy 2009-11-5 ����04:38:21 Ӧ�÷��������Ϊ���ת��ǰ���澭��ĳ��������
      //��ôӦ�������û�λ�����кŵĻ������ݣ����������������ݵ�ʱ����ܸ���bodyvoһͬ����������
    // �����λ���ݣ����кš�
//      lTime = System.currentTimeMillis();
      int iRowCount = bvo.getItemCount();
      m_alLocatorDataBackup = m_alLocatorData;
      m_alSerialDataBackup = m_alSerialData;
      if (iRowCount > 0) {
        m_alLocatorData = new ArrayList();
        m_alSerialData = new ArrayList();
        for (int i = 0; i < iRowCount; i++) {
          m_alLocatorData.add(bvo.getItemValue(i, "locator"));
          m_alSerialData.add(bvo.getItemValue(i, "serial"));
          // m_alLocatorData.add(null);
          // m_alSerialData.add(null);
        }
      } else
        SCMEnv.out("--->W:row is null");
      SCMEnv.showTime(lTime, "setBillVO:4");
      getBillCardPanel().setBillValueVO(bvo);
      
      synUIVORowPos();
      
      for(int i = 1; i <= 20; i++)
      {
          String key = "vuserdef" + i;
          BillItem item = getBillCardPanel().getHeadItem(key);
          if(item != null && item.getDataType() == 7)
          {
              String pk = (String)m_voBill.getHeaderValue("pk_defdoc" + i);
              if(pk != null && pk.length() > 0)
                  ((UIRefPane)item.getComponent()).setPK(bvo.getHeaderValue("pk_defdoc" + i));
          }
      }

      SCMEnv.showTime(lTime, "setBillVO:setBillValueVO");
      // ִ�й�ʽ
      /** ydy 2005-06-21 */
      if (bExeFormule) {
        getBillCardPanel().getBillModel().execLoadFormula();
        execHeadTailFormulas();
      }
      // ����״̬
      lTime = System.currentTimeMillis();
      getBillCardPanel().updateValue();
      // �����ִ�������
      bvo.clearInvQtyInfo();
      // ѡ�е�һ�У�����Ƶ�����
      // modified by liuzy 2009-8-18 ����05:00:51 ȡ��ѡ�е�һ�У�Ϊ�˽�������
//      getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
      // modified by liuzy 2009-8-18 ����05:02:02 ȡ��ѡ�е�һ�У�Ϊ�˽�������
//      getBillCardPanel().transferFocusTo(1);
      // �������к��Ƿ����
      setBtnStatusSN(0, false);

      SCMEnv.showTime(lTime, "setBillVO:3");
      // ˢ���ִ�����ʾ
      // modified by liuzy 2009-8-18 ����05:02:15 ȡ��ѡ�е�һ�У�Ϊ�˽�������
//      setTailValue(0);
      
      
  
      
    } catch (Exception e) {
      showErrorMessage(e.getMessage());
      SCMEnv.out(e.getMessage());
    } finally {
      long lTime = System.currentTimeMillis();
      getBillCardPanel().getBillModel().addTableModelListener(this);
      getBillCardPanel().addBillEditListenerHeadTail(this);
      SCMEnv.showTime(lTime, "setBillVO:addTableModelListener");
    }
    /** �����ݵ���Դ����Ϊת�ⵥʱ, ���н������ */
    long lTime = System.currentTimeMillis();
    ctrlSourceBillUI(bUpdateBotton);
    
//  ���ο�����չ
    getPluginProxy().afterSetBillVOToCard(bvo);
    
    SCMEnv.showTime(lTime, "setBillVO:ctrlSourceBillUI");

  }

  /**
   * �����ߣ����� ���ܣ����ݵ�ǰ������BarCodeVO[]�Ƿ���ֵ,����༭��ť�Ƿ���� ������ row�к� ���أ� ���⣺
   * ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * �޸��ˣ������� �޸����ڣ�2007-10-17����11:31:19 �޸�ԭ��
   * 
   * 
   */
  protected void setBtnStatusBC(int row) {
    GeneralBillVO voBill = null;
    // ����voBill,�Զ�ȡ�������ݡ�
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // �������޸ķ�ʽ����m_voBill
      voBill = getM_voBill();

    if (BillMode.List == getM_iCurPanel()) {
      getButtonManager().getButton(ICButtonConst.BTN_LINE_BARCODE)
          .setEnabled(false);
      getButtonManager().getButton(
          ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE).setEnabled(false);
      getButtonManager()
          .getButton(ICButtonConst.BTN_EXECUTE_BARCODE_OPEN)
          .setEnabled(false);
    } else {
      if (voBill != null && voBill.getItemVOs() != null
          && voBill.getItemVOs().length > 0
          && row < voBill.getItemVOs().length) {
        GeneralBillItemVO voItem = null;
        voItem = voBill.getItemVOs()[row];
        if (voItem != null
            && voItem.getBarcodeManagerflag().booleanValue()
            &&isSigned() != SIGNED) {
          // ��������༭
          getButtonManager()
              .getButton(ICButtonConst.BTN_LINE_BARCODE)
              .setEnabled(true);
          getButtonManager().getButton(
              ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE)
              .setEnabled(true);
          getButtonManager().getButton(
              ICButtonConst.BTN_EXECUTE_BARCODE_OPEN).setEnabled(
              true);
          // if (m_utfBarCode!=null)
          // m_utfBarCode.setEnabled(true);
        } else {
          getButtonManager()
              .getButton(ICButtonConst.BTN_LINE_BARCODE)
              .setEnabled(false);
          getButtonManager().getButton(
              ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE)
              .setEnabled(false);
          getButtonManager().getButton(
              ICButtonConst.BTN_EXECUTE_BARCODE_OPEN).setEnabled(
              false);
          // if (m_utfBarCode!=null)
          // m_utfBarCode.setEnabled(false);
        }

      }
    }

    // �������⣬ydy 04-06-29
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_LINE_BARCODE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_EXECUTE_BARCODE_OPEN));

  }

  /**
   * �����ߣ�� ���ܣ����ݵ�ǰ������BarCodeVO[],���õ�������˵�״̬ ������ row�к� ���أ� ���⣺
   * 
   */
  protected void setBtnStatusImportBarcode(int row) {
    if (row < 0)
      return;
    GeneralBillVO voBill = null;
    if (BillMode.List == getM_iCurPanel()) {
      setBarcodeButtonStatus(false);
      return;// ������������������벻����ֱ�ӵ���
    }
    // ����voBill,�Զ�ȡ�������ݡ�
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // �������޸ķ�ʽ����m_voBill
      voBill = getM_voBill();

    if (voBill != null && voBill.getItemVOs() != null
        && voBill.getItemVOs().length > 0
        && voBill.getItemVOs().length > row) {
      GeneralBillItemVO voItem = null;
      voItem = voBill.getItemVOs()[row];
      if (voItem != null && voItem.getBarcodeManagerflag().booleanValue()) {
        boolean bPri = voItem.getInv().getIsprimarybarcode()
            .booleanValue();
        boolean bSub = voItem.getInv().getIssecondarybarcode()
            .booleanValue();
        if (bPri == false && bSub == false)
          setBarcodeButtonStatus(false);
        if (bPri == true && bSub == false) {
          setBarcodeButtonStatus(true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_1ST_BARCODE).setEnabled(
              true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_2ND_BARCODE).setEnabled(
              false);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_BOTH_BARCODE).setEnabled(
              false);
        }
        if (bPri == false && bSub == true) {
          setBarcodeButtonStatus(true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_1ST_BARCODE).setEnabled(
              false);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_2ND_BARCODE).setEnabled(
              true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_BOTH_BARCODE).setEnabled(
              false);
        }
        if (bPri == true && bSub == true) {
          setBarcodeButtonStatus(true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_1ST_BARCODE).setEnabled(
              true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_2ND_BARCODE).setEnabled(
              true);
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_BOTH_BARCODE).setEnabled(
              true);
        }
      } else
        setBarcodeButtonStatus(false);

    } else
      setBarcodeButtonStatus(false);

  }

  /**
   * 
   * ���������������������뵼����ص��ĸ���ť��״̬��<br>
   * V50�У�ʹ����һ��������ť���������롱����Ϊ���ĸ���ť��һ����ť�����ﵽ�������ĸ���ť״̬��Ŀ��
   * V51�ع������еİ�ť��ʹ��ButtonTree�����м��أ���������ð�ťע��Ĺ��ܣ����ĸ���ť�鲢��������/���롱��ť�£������Ҫ����������״̬
   * <p>
   * <p>
   * <b>����˵��</b>
   * 
   * @param enabled
   *            ����״̬Ϊtrue��������״̬Ϊfalse
   *            <p>
   * @author duy
   * @time 2007-2-5 ����02:16:46
   */
  private void setBarcodeButtonStatus(boolean enabled) {
    getButtonManager().getButton(ICButtonConst.BTN_IMPORT_1ST_BARCODE)
        .setEnabled(enabled);
    getButtonManager().getButton(ICButtonConst.BTN_IMPORT_2ND_BARCODE)
        .setEnabled(enabled);
    getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BOTH_BARCODE)
        .setEnabled(enabled);
    getButtonManager().getButton(ICButtonConst.BTN_IMPORT_SOURCE_BARCODE)
        .setEnabled(enabled);
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_IMPORT_1ST_BARCODE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_IMPORT_2ND_BARCODE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_IMPORT_BOTH_BARCODE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_IMPORT_SOURCE_BARCODE));
  }

  /**
   * �����ߣ����˾� ���ܣ����ݵ�ǰ���ݵĴ���״̬����ǩ��/ȡ��ǩ���Ǹ����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSign(boolean bUpdateButtons) {

    // ֻ�����״̬�²��ҽ������е���ʱ����
    if (BillMode.Browse != getM_iMode() || getM_iLastSelListHeadRow() < 0
        || m_iBillQty <= 0) {
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      return;
    }

    int iSignFlag = isSigned();
    if (SIGNED == iSignFlag) {
      // ��ǩ�֣��������ð�ť״̬,ǩ�ֲ����ã�ȡ��ǩ�ֿ���
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(true);
      // ����ɾ����
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SETTLE_PATH).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_CANCEL_SETTLE_PATH).setEnabled(
          false);

    } else if (NOTSIGNED == iSignFlag) {
      // δǩ�֣��������ð�ť״̬,ǩ�ֿ��ã�ȡ��ǩ�ֲ�����
      // �ж��Ƿ���������������Ϊ�����������ģ�����ֻҪ����һ�о����ˡ�

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      // ��ɾ����
      if (isCurrentTypeBill()) {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(true);
      } else {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(false);
      }
    } else { // ����ǩ�ֲ���
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      // ��ɾ����
      if (isCurrentTypeBill()) {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(true);
      } else {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(false);
      }
    }
    // ʹ������Ч ���� by hanwei 2003-11-17 for Ч��
    if (bUpdateButtons)
      updateButtons();

  }

  /**
   * �����ߣ����˾� ���ܣ����ݵ�ǰ��������Ծ������кŷ����Ƿ���� ������ row�к� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־�� bUpdateButtons:�Ƿ���°�Ŧ,true: ����, false������
   * 
   * 
   * 
   */
  protected void setBtnStatusSN(int row, boolean bUpdateButtons) {
    GeneralBillVO voBill = null;
    // ����voBill,�Զ�ȡ�������ݡ�
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // �������޸ķ�ʽ����m_voBill
      voBill = getM_voBill();

    if (voBill != null) {
      InvVO voInv = null;
      voInv = voBill.getItemInv(row);
      if (voInv != null && voInv.getIsSerialMgt() != null
          && voInv.getIsSerialMgt().intValue() != 0)
        // �����кŹ�����������
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
            .setEnabled(true);
      else
        // �������кŹ�������������
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
            .setEnabled(false);
    } else
      getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
          .setEnabled(false);

    // �������밴ť״̬���Ʒ���by hanwei 2003-11-18
    setBtnStatusBC(row);

    // ���õ������밴ť״̬���Ʒ��� add by ljun
    setBtnStatusImportBarcode(row);

    // ʹ������Ч ���� by hanwei 2003-11-17 for Ч��
    if (bUpdateButtons)
      // updateButtons();
      // �������⣬ydy 04-06-29
      updateButton(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_SERIAL));

  }

  /**
   * �����ߣ����˾� ���ܣ����ݵ�ǰ�ֿ��״̬������λ�����Ƿ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSpace(boolean bUpdateButtons) {
    GeneralBillVO voBill = null;
    // ����voBill,�Զ�ȡ�������ݡ�
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // �������޸ķ�ʽ����m_voBill
      voBill = getM_voBill();

    // ȱʡ���ǻ�λ����ֿ⣬������
    getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE).setEnabled(
        false);
    // m_boSelectLocator.setEnabled(false);

    if (voBill != null) {
      WhVO voWh = null;
      voWh = voBill.getWh();
      // �ǻ�λ����ֿ⣬����
      if (voWh != null && voWh.getIsLocatorMgt() != null
          && voWh.getIsLocatorMgt().intValue() != 0) {
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE)
            .setEnabled(true);
      }

      // ###########################
      // ���� add by hanwei 2004-05-14
      // 1��������ⵥ�����ֱ�ӵ�ת��־����Ҫ�ڽ������ �޸ġ�ɾ�������ƵȰ�ť�����ã�
      // 2��������ݵĲֿ���ֱ�˲֣�Ӧ�ÿ����޸ġ�ɾ�������ƵȰ�ť�����ã�
      setBtnStatusTranflag();
      // #############################
    }

    // ʹ������Ч by hanwei 2003-11-17 for Ч��
    if (bUpdateButtons)
      updateButton(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_SPACE));

  }

  /**
   * �����ߣ����� ���ܣ� bd_stordoc �Ƿ�ֱ�˲ֿ⣺isdirectstore =Y �� ic_general_h
   * �Ƿ�ֱ�ӵ�����bdirecttranflag =Y 1��������ⵥ�����ֱ�ӵ�ת��־����Ҫ�ڽ������ �޸ġ�ɾ�������ƵȰ�ť�����ã�
   * 2���ֿ���һ�����ԣ��Ƿ�ֱ�˲ֿ⣬��Ҫ���� WHVO�С�������ݵĲֿ���ֱ�˲֣�Ӧ�ÿ����޸ġ�ɾ�������ƵȰ�ť�����ã�
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  protected void setBtnStatusTranflag() {
    setBtnStatusTranflag(false);
  }

  /**
   * �����ߣ����� ���ܣ� bd_stordoc �Ƿ�ֱ�˲ֿ⣺isdirectstore =Y �� ic_general_h
   * �Ƿ�ֱ�ӵ�����bdirecttranflag =Y 1��������ⵥ�����ֱ�ӵ�ת��־����Ҫ�ڽ������ �޸ġ�ɾ�������ƵȰ�ť�����ã�
   * 2���ֿ���һ�����ԣ��Ƿ�ֱ�˲ֿ⣬��Ҫ���� WHVO�С�������ݵĲֿ���ֱ�˲֣�Ӧ�ÿ����޸ġ�ɾ�������ƵȰ�ť�����ã�
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * bOnlyFalse:ֻ����setEnabledΪfalse�����������
   * 
   */
  protected void setBtnStatusTranflag(boolean bOnlyFalse) {

    GeneralBillVO voBill = null;

    // ����voBill,�Զ�ȡ�������ݡ�
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      return;

    boolean bUseable = true;
    // ֱ��
    if (voBill != null) {
/*      WhVO voWh = null;
      voWh = voBill.getWh();
      if (voWh != null && voWh.getIsdirectstore() != null
          && voWh.getIsdirectstore().booleanValue()) {
        bUseable = false;
      }
      if (voBill.getHeaderVO() != null
          && voBill.getHeaderVO().getBdirecttranflag() != null
          && voBill.getHeaderVO().getBdirecttranflag().booleanValue()) {
        bUseable = false;
      }*/

      if (bOnlyFalse) {
        //�޸��ˣ������� �޸�ʱ�䣺2008-8-25 ����04:39:09 �޸�ԭ��ֱ�˲ֿ⡢ֱ�ӵ���,�ɱ༭
/*        if (!bUseable) {
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
              .setEnabled(false);
        }*/
      } else {
        if (getM_iMode() != BillMode.New) {
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(bUseable);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
              .setEnabled(bUseable);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
              .setEnabled(bUseable);
        }
      }

    }

  }

  /**
   * �����ߣ����˾� ���ܣ����ð�ť״̬�� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */

  protected void setButtonStatus(boolean bUpdateButtons) {

    long lTime = System.currentTimeMillis();
    switch (getM_iMode()) {
    case BillMode.New:
      getButtonManager().getButton(ICButtonConst.BTN_ADD).setEnabled(
          false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_SAVE).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_SEL_LOC).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_CANCEL)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_QUERY).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE_REFRESH)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE_LOCATE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          false);

      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_QUERY)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_EXPORT_IMPORT)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE).setEnabled(
          true);

      getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_DELETE)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_COPY)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_PASTE)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
          .setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO)
          .setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE).setEnabled(true);
      // getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL).setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);

      getButtonManager().getButton(ICButtonConst.BTN_LINE).setEnabled(
          true);

      getButtonManager().getButton(ICButtonConst.BTN_PRINT).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_PRINT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_PREVIEW)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_SPACE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_SUM)
          .setEnabled(false);

      // ��������֧��
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SCAN)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(false);
      // ���Ʒ�ҳ��ť��״̬��
      m_pageBtn.setPageBtnVisible(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);
      // ����������״̬�²�����
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_QUERY_RELATED)
          .setEnabled(false);
      getButtonManager()
      .getButton(ICButtonConst.BTN_ASSIST_QUERY_RELATED)
      .setEnabled(false);
      
      if(getButtonManager().getButton(ICButtonConst.BTN_DMStateQry)!=null)
        getButtonManager().getButton(ICButtonConst.BTN_DMStateQry).setEnabled(false);
      if(null != getButtonManager().getButton(ICButtonConst.BTN_ASSIST_COOP_45))
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_COOP_45).setEnabled(false);

      // ����������º��޸�����������Ա༭
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(true);

      // ��������
      if (BillMode.List == getM_iCurPanel()) {
        setBarcodeButtonStatus(false);
      } else {
        setBtnStatusImportBarcode(0);
      }
      if (getButtonManager().getButton(
          ICButtonConst.BTN_IMPORT_SOURCE_BARCODE) != null) {
        getButtonManager().getButton(
            ICButtonConst.BTN_IMPORT_SOURCE_BARCODE).setEnabled(
            true);
      }
      getButtonManager().getButton(ICButtonConst.BTN_OUT_RETURN)
      .setEnabled(false);
  getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_ASSEMBLY)
      .setEnabled(false);
  getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
      .setEnabled(false);
  
  getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_MANUAL_RETURN)
  .setEnabled(false);
getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_PO_RETURN)
  .setEnabled(false);
  
      break;
    case BillMode.Update:
      getButtonManager().getButton(ICButtonConst.BTN_ADD).setEnabled(
          false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_SAVE).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_SEL_LOC).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_CANCEL)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_QUERY).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE_REFRESH)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE_LOCATE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          false);
      
      getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE).setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_QUERY)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_EXPORT_IMPORT)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_BROWSE).setEnabled(
          true);

      getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_DELETE)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_COPY)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_PASTE)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
          .setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO)
          .setEnabled(true);

      // getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE).setEnabled(true);
      // getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL).setEnabled(true);

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);

      getButtonManager().getButton(ICButtonConst.BTN_LINE).setEnabled(
          true);

      getButtonManager().getButton(ICButtonConst.BTN_PRINT).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_PRINT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_PREVIEW)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_SPACE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_SUM)
          .setEnabled(false);

      // ��������֧��
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SCAN)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(false);
      // ���Ʒ�ҳ��ť��״̬��
      m_pageBtn.setPageBtnVisible(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);
      // �������޸�״̬�²�����
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_QUERY_RELATED)
          .setEnabled(false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_SETTLE_PATH)
          .setEnabled(false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_CANCEL_SETTLE_PATH).setEnabled(
          false);
      if(getButtonManager().getButton(ICButtonConst.BTN_DMStateQry)!=null)
        getButtonManager().getButton(ICButtonConst.BTN_DMStateQry).setEnabled(false);
      if(null != getButtonManager().getButton(ICButtonConst.BTN_ASSIST_COOP_45))
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_COOP_45).setEnabled(false);

      // ����������º��޸�����������Ա༭
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(true);

      if (getButtonManager().getButton(
          ICButtonConst.BTN_IMPORT_SOURCE_BARCODE) != null
          && BillMode.Card == getM_iCurPanel()
          && getM_voBill() != null
          && getM_voBill().getHeaderVO() != null) {
        String sBillTypecode = getM_voBill().getHeaderVO()
            .getCbilltypecode();
        if (sBillTypecode != null
            && (sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_otherIn)
                || sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_otherOut)
                || sBillTypecode.equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_allocationOut) || sBillTypecode
                .equalsIgnoreCase(nc.vo.ic.pub.BillTypeConst.m_allocationIn))) {
          // ���Ϲ�����������ⵥ����������ⵥ�����Ե��뵥������
          // ��Ƭ״̬���������ͣ�
          getButtonManager().getButton(
              ICButtonConst.BTN_IMPORT_SOURCE_BARCODE)
              .setEnabled(true);
        }

      }

      if (BillMode.List == getM_iCurPanel()) {
        setBarcodeButtonStatus(false);
      } else {
        setBtnStatusImportBarcode(0);
      }
      getButtonManager().getButton(ICButtonConst.BTN_OUT_RETURN)
      .setEnabled(false);
  getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_ASSEMBLY)
      .setEnabled(false);
  getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
      .setEnabled(false);
  getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_MANUAL_RETURN)
  .setEnabled(false);
getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_PO_RETURN)
  .setEnabled(false);
      break;
    case BillMode.Browse: // ��������������򲻿��Ա༭
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(false);
      // ���б�����£����밴Ŧ
      if (BillMode.List == getM_iCurPanel()) {
        getButtonManager().getButton(ICButtonConst.BTN_LINE_BARCODE)
            .setEnabled(false);
        // added by lirr 2009-10-31����10:38:32 ��ѯ���б��£�����رա��򿪲�����
        getButtonManager().getButton(
            ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE).setEnabled(false);
        getButtonManager()
            .getButton(ICButtonConst.BTN_EXECUTE_BARCODE_OPEN)
            .setEnabled(false);
      } else {
        setBtnStatusBC(0);
        // add by ljun
        setBtnStatusImportBarcode(0);
      }

      getButtonManager().getButton(ICButtonConst.BTN_ADD)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE)
          .setEnabled(true);

      // �е���
      if (m_iBillQty > 0 && getM_iLastSelListHeadRow() >= 0) {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_BROWSE_LOCATE)
            .setEnabled(true);

        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_QUERY)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_EXPORT_IMPORT)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_BROWSE)
            .setEnabled(true);
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_QUERY_RELATED)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_OUT_RETURN)
        .setEnabled(true);
    getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_ASSEMBLY)
        .setEnabled(true);
    getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
        .setEnabled(true);
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_FUNC_SETTLE_PATH).setEnabled(
            true);
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_CANCEL_SETTLE_PATH).setEnabled(
            true);
        if(null != getButtonManager().getButton(ICButtonConst.BTN_ASSIST_COOP_45))
          getButtonManager().getButton(ICButtonConst.BTN_ASSIST_COOP_45).setEnabled(true);
        
        // ������Ǻͱ��ڵ���ͬ�ĵ�������(����뵥�ϲ�����ڳ���)������ɾ��.
        try {
          // ��ǰѡ�еĵ���
          GeneralBillVO voBill = null;
          if (getM_iCurPanel() == BillMode.List)
            voBill = (GeneralBillVO) getM_alListData().get(
                getM_iLastSelListHeadRow());
          else
            voBill = getM_voBill();
          if (getBillType().equals(
              voBill.getHeaderVO().getCbilltypecode())) {
            getButtonManager().getButton(
                ICButtonConst.BTN_BILL_EDIT).setEnabled(true);
            getButtonManager().getButton(
                ICButtonConst.BTN_BILL_DELETE).setEnabled(true);
          }
        } catch (Exception e) {
          SCMEnv.out(e.getMessage());
        }
      } else {
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BROWSE_LOCATE)
            .setEnabled(false);
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_QUERY_RELATED).setEnabled(
            false);
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_FUNC_SETTLE_PATH).setEnabled(
            false);
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_CANCEL_SETTLE_PATH).setEnabled(
            false);
        getButtonManager().getButton(ICButtonConst.BTN_OUT_RETURN)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_ASSEMBLY)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
            .setEnabled(false);
        
      }

      getButtonManager().getButton(ICButtonConst.BTN_SAVE).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_SEL_LOC).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_CANCEL)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_QUERY).setEnabled(
          true);

      if (getQryDlgHelp().isBEverQry())
        getButtonManager().getButton(ICButtonConst.BTN_BROWSE_REFRESH)
            .setEnabled(true);
      else
        getButtonManager().getButton(ICButtonConst.BTN_BROWSE_REFRESH)
            .setEnabled(false);

      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_COPY)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_PASTE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
          .setEnabled(false);
      // modified by liuzy 2008-01-03 ���״̬�²�����
      getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO)
          .setEnabled(false);
      // �е��ݿ��Դ�ӡ��
      if (m_iBillQty > 0) {
        getButtonManager().getButton(ICButtonConst.BTN_PRINT)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_PRINT)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_PREVIEW)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_SPACE)
            .setEnabled(true);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_SUM)
            .setEnabled(true);
        
        if(getButtonManager().getButton(ICButtonConst.BTN_DMStateQry)!=null)
          getButtonManager().getButton(ICButtonConst.BTN_DMStateQry).setEnabled(true);
        
      } else {
        getButtonManager().getButton(ICButtonConst.BTN_PRINT)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_PRINT)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_PREVIEW)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_SPACE)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_SUM)
            .setEnabled(false);
        
        if(getButtonManager().getButton(ICButtonConst.BTN_DMStateQry)!=null)
          getButtonManager().getButton(ICButtonConst.BTN_DMStateQry).setEnabled(false);
        
      } // ��Ӧ��һ���жϵ�ǰ�ĵ����Ƿ���ǩ��
      // ͬʱ�ж��޸ġ�ɾ���Ƿ���ã�����Ӧ�������ǵĺ��档
      getButtonManager().getButton(ICButtonConst.BTN_LINE).setEnabled(
          false);
      // ��������֧��
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SCAN)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
/*      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(true);*/
      // ��������
      if (getButtonManager().getButton(
          ICButtonConst.BTN_IMPORT_SOURCE_BARCODE) != null)
        getButtonManager().getButton(
            ICButtonConst.BTN_IMPORT_SOURCE_BARCODE).setEnabled(
            true);
      // ���Ʒ�ҳ��ť��״̬��
      if (getM_iCurPanel() == BillMode.List) {
        m_pageBtn.setPageBtnVisible(false);
      } else {
        m_pageBtn.setPageBtnVisible(true);
        m_pageBtn.setPageBtnStatus(m_iBillQty,
            getM_iLastSelListHeadRow());
      }
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO)
          .setEnabled(false);

      if (BillMode.List == getM_iCurPanel()) {
        getButtonManager().getButton(
            ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(
            false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_BARCODE)
            .setEnabled(false);
      }
      
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_MANUAL_RETURN)
      .setEnabled(true);
    getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_PO_RETURN)
      .setEnabled(true);

      break;
    }
    
    getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE)
    .setEnabled(false);
    SCMEnv.showTime(lTime, "setEnable:");
    // ��ǰѡ����
    lTime = System.currentTimeMillis();
    int rownow = getBillCardPanel().getBillTable().getSelectedRow();
    // �ж��Ƿ���Ҫ���кŷ���,����״̬
    if (rownow >= 0)
      setBtnStatusSN(rownow, false);
    else
      getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
          .setEnabled(false);
    // ���󷽷�����
    SCMEnv.showTime(lTime, "setBtnStatusSN)");
    lTime = System.currentTimeMillis();
    setButtonsStatus(getM_iMode());
    setExtendBtnsStat(getM_iMode());
    SCMEnv.showTime(lTime, "setButtonsStatus(m_iMode)");
    // �������û�λ�����Ƿ����
    lTime = System.currentTimeMillis();
    setBtnStatusSpace(false);
    SCMEnv.showTime(lTime, "setBtnStatusSpace();:");
    setBtnStatusSign(false);
    // ������Դ���ݿ��ư�ť
    lTime = System.currentTimeMillis();
    ctrlSourceBillButtons(false);
    SCMEnv.showTime(lTime, "������Դ���ݿ��ư�ť");/*-=notranslate=-*/

    // ���ݵ������Ϳ��ư�ť
    lTime = System.currentTimeMillis();
    ctrlBillTypeButtons(true);
    SCMEnv.showTime(lTime, "���ݵ������Ϳ��ư�ť");/*-=notranslate=-*/

    // �ϲ���ʾ
    if (getM_iCurPanel() == BillMode.List) {
      getButtonManager().getButton(ICButtonConst.BTN_PRINT_DISTINCT)
          .setEnabled(false);
    } else {
      if (getM_iMode() == BillMode.Browse)
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_DISTINCT)
            .setEnabled(true);
      else
        getButtonManager().getButton(ICButtonConst.BTN_PRINT_DISTINCT)
            .setEnabled(false);
    }

    // �б�״̬�£�������ʾ/���صİ�ť������
    if (BillMode.Card == getM_iCurPanel()) {
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_ONHAND)
          .setEnabled(true);
    } else if (BillMode.List == getM_iCurPanel()) {
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_ONHAND)
          .setEnabled(false);
    }

    // v5 lj
    if (m_bRefBills == true) {
      setRefBtnStatus();
    }

    // ʹ������Ч
    lTime = System.currentTimeMillis();
    if (bUpdateButtons)
      updateButtons();
    SCMEnv.showTime(lTime, "updateButtons();");
    
    //added by lirr 2009-02-13    ���ο�����չ
      getPluginProxy().setButtonStatus();
  }

  /**
   * �˴����뷽��˵���� �������ڣ�(2004-4-8 11:13:07)
   * 
   * @param newGenBillUICtl
   *            nc.ui.ic.pub.bill.GeneralBillUICtl
   */
  public void setGenBillUICtl(GeneralBillUICtl newGenBillUICtl) {
    m_GenBillUICtl = newGenBillUICtl;
  }

  /**
   * �����ߣ����˾� ���ܣ��ڱ�������ʾVO,�����½���״̬updateValue() ������ ���أ� ���⣺ ���ڣ�(2001-5-9
   * 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void setImportBillVO(GeneralBillVO bvo, boolean bExeFormula)
      throws Exception {
    if (bvo == null)
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000061")/* @res "����ĵ���Ϊ�գ�" */);
    // ��������
    int iRowCount = bvo.getItemCount();

    try {
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      getBillCardPanel().removeBillEditListenerHeadTail();
      // ����һ��clone()
      setM_voBill((GeneralBillVO) bvo.clone());
      // �����ֿ�Ĺ�˾�ǵ�½��˾

      // ��������ⵥ�������������⴦��
      // ��Ӧ���ڻ��෽���С�
      // shawbing on Jun 13, 2005
      if (getBillType().equals("4Y") || getBillType().equals("4E"))
        setBillRefIn4Eand4Y(getM_voBill());

      // �����ĵ������PK
      getM_voBill().getHeaderVO().setPrimaryKey(null);
      GeneralBillItemVO[] voaItem = getM_voBill().getItemVOs();
      for (int row = 0; row < iRowCount; row++) {
        voaItem[row].setPrimaryKey(null);
        voaItem[row].calculateMny();
      }
      // ��������
      getBillCardPanel().setBillValueVO(getM_voBill());
      // ִ�й�ʽ
      if (bExeFormula) {
        getBillCardPanel().getBillModel().execLoadFormula();
        execHeadTailFormulas();
      }

      // ����״̬ ---delete it to support CANCEL
      // getBillCardPanel().updateValue();
      // �����ִ�������
      bvo.clearInvQtyInfo();
      // �б����У�ѡ�е�һ��
      if (iRowCount > 0) {
        // ѡ�е�һ��
        getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
        // �������к��Ƿ����
        setBtnStatusSN(0, false);

        // ˢ���ִ�����ʾ
        // setTailValue(0);
        // ������������
        nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel()
            .getBillModel();
        m_alLocatorDataBackup = m_alLocatorData;
        m_alSerialDataBackup = m_alSerialData;
        m_alLocatorData = new ArrayList();
        m_alSerialData = new ArrayList();

        for (int row = 0; row < iRowCount; row++) {
          // ������״̬Ϊ����
          if (bmTemp != null)
            bmTemp.setRowState(row, nc.ui.pub.bill.BillModel.ADD);
          m_alLocatorData.add(null);
          m_alSerialData.add(null);
        }
      }
    } catch (Exception e) {
      SCMEnv.out(e.getMessage());
    } finally {
      getBillCardPanel().getBillModel().addTableModelListener(this);
      getBillCardPanel().addBillEditListenerHeadTail(this);
    }
  }

  /**
   * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2005-1-11 18:53:03) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param voLot
   *            nc.vo.ic.pub.lot.LotNumbRefVO
   * @param irow
   *            int
   */
  protected void synlot(nc.vo.ic.pub.lot.LotNumbRefVO voLot, int irow) {

    if (getBillCardPanel().getBillModel().getRowState(irow) == BillModel.NORMAL)
      getBillCardPanel().getBillModel().setRowState(irow,
          BillModel.MODIFICATION);
    InvVO voInv = getM_voBill().getItemInv(irow);
    if (voLot == null) {
      clearRowData(getInOutFlag(), irow, "vbatchcode");

    }

    // ����λ
    if (voInv.getIsAstUOMmgt() != null
        && voInv.getIsAstUOMmgt().intValue() == 1) {

      String oldvalue = (String) getBillCardPanel().getBodyValueAt(irow,
          "castunitid");// refCastunit.getRefPK();
      //������ �����������Զ����BUG 2009-07-08
      if(voLot.getCastunitid()!=null){
          getM_voBill().setItemValue(irow, "cselastunitid",
          voLot.getCastunitid());
          
      }
      else{
        voLot.setCastunitid(oldvalue);
//          getM_voBill().setItemValue(irow, "cselastunitid",
//              oldvalue);
        
      }
      nc.vo.ic.pub.DesassemblyVO voDesa = (nc.vo.ic.pub.DesassemblyVO) getM_voBill()
          .getItemValue(irow, "desainfo");

      if (voDesa.getMeasInfo() == null) {
        nc.vo.bd.b15.MeasureRateVO[] voMeasInfo = m_voInvMeas
            .getMeasInfo(getEnvironment().getCorpID(), voInv
                .getCinventoryid());
        voDesa.setMeasInfo(voMeasInfo);
      }
      getM_voBill().setItemValue(irow, "idesatype",
          new Integer(voDesa.getDesaType()));
      getBillCardPanel().setBodyValueAt(
          new Integer(voDesa.getDesaType()), irow, "idesatype");
      //������ �����������Զ����BUG 2009-07-08
      if(voLot.getCastunitid()!=null){
      getBillCardPanel().setBodyValueAt(voLot.getCastunitid(), irow,
          "cselastunitid");
      }
      // ������ǲ�⣬��ôִ����ǰ�Ĵ��롣
      if (voDesa.getDesaType() == nc.vo.ic.pub.DesassemblyVO.TYPE_NO
          && !GenMethod
              .isEqualObject(voLot.getCastunitid(), oldvalue)) {// !voLot.getCastunitid().equals(oldvalue))
        // {
        nc.ui.pub.beans.UIRefPane refCastunit = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem("castunitname").getComponent());

        filterMeas(irow);
        refCastunit.setPK(voLot.getCastunitid());
        refCastunit.setName(voLot.getCastunitname());

        Object source = getBillCardPanel().getBodyItem("castunitname");
        Object newvalue = voLot.getCastunitid();
        nc.ui.pub.bill.BillEditEvent even = new nc.ui.pub.bill.BillEditEvent(
            source, newvalue, oldvalue, "castunitname", irow,
            BillItem.BODY);
        getBillCardPanel().setBodyValueAt(voLot.getCastunitname(),
            irow, "castunitname");
        
          getEditCtrl().afterAstUOMEdit(even);
      }
   // addied by liuzy 2010-12-9 ����11:27:03 ����ǻ����ʼǽ�棬��Ҫ�ѻ����ʴ��뵥��
      boolean isStoreByConvert = null == voInv.getIsStoreByConvert()
      || voInv.getIsStoreByConvert() == 0 ? false : true;
      if(isStoreByConvert){
        UFDouble hsl = voLot.getHsl();
        if(null != hsl){
          Object srcItem = getBillCardPanel().getBodyItem("hsl");
          Object oldHsl = getBillCardPanel().getBodyValueAt(irow, "hsl");
          BillEditEvent e = new BillEditEvent(srcItem,hsl,oldHsl,"hsl",irow,BillItem.BODY);
          getBillCardPanel().setBodyValueAt(hsl, irow, "hsl");
          getEditCtrl().afterHslEdit(e);              
        }
      }
    }
    getBillCardPanel().setBodyValueAt(voLot.getVbatchcode(), irow,
        "vbatchcode");
    getBillCardPanel().setBodyValueAt(voLot.getDvalidate(), irow,
        "dvalidate");
    if (GeneralBillVO.getBillInOutFlag(getBillType()) == InOutFlag.OUT) {
      if (voLot.getOnhandnumType() != null
          && voLot.getOnhandnumType().intValue() == 0) {
        getBillCardPanel().setBodyValueAt(new UFBoolean("N"), irow,
            "bonroadflag");
        getM_voBill().setItemValue(irow, "bonroadflag",
            new UFBoolean("N"));
      } else {
        getBillCardPanel().setBodyValueAt(new UFBoolean("Y"), irow,
            "bonroadflag");
        getM_voBill().setItemValue(irow, "bonroadflag",
            new UFBoolean("Y"));
      }
    }
    // �޸��ˣ������� �޸����ڣ�2007-9-19����11:01:19 �޸�ԭ��������������ھͲ�������
    if (voInv.getIsValidateMgt().intValue() == 1
        && (null == getBillCardPanel().getBodyValueAt(irow, "scrq") || (null != getBillCardPanel()
            .getBodyValueAt(irow, "scrq") && ""
            .equals(getBillCardPanel().getBodyValueAt(irow, "scrq")
                .toString())))) {
      nc.vo.pub.lang.UFDate dvalidate = voLot.getDvalidate();
      if (dvalidate != null) {
        getBillCardPanel().setBodyValueAt(
            dvalidate.getDateBefore(
                voInv.getQualityDay().intValue()).toString(),
            irow, "scrq");
      }
    }
    
    // ������
    if (voInv.getIsFreeItemMgt() != null
        && voInv.getIsFreeItemMgt().intValue() == 1) {
      FreeVO freevo = voLot.getFreeVO();
      if (freevo != null && freevo.getVfree0() != null) {
        /*
         * if (freevo != null && freevo.getVfree0() != null &&
         * freevo.getVfree0() != null && !"".equals(freevo.getVfree0())) {
         */
        InvVO invvo = getM_voBill().getItemInv(irow);
        if (invvo != null)
          invvo.setFreeItemVO(freevo);
        getFreeItemRefPane().setFreeItemParam(invvo);
        getBillCardPanel().setBodyValueAt(freevo.getVfree0(), irow,
            "vfree0");
        for (int i = 1; i <= FreeVO.FREE_ITEM_NUM; i++) {
          if (getBillCardPanel().getBodyItem("vfree" + i) != null)

            getBillCardPanel().setBodyValueAt(
                freevo.getAttributeValue("vfree" + i), irow,
                "vfree" + i);
          else
            getBillCardPanel().setBodyValueAt(null, irow,
                "vfree" + i);
        }
        // �޸��ˣ������� �޸����ڣ�2007-12-28����04:49:54 �޸�ԭ��ͬ������VO�ͽ����ϵ�invvo
        getBillCardPanel().setBodyValueAt(invvo, irow, "invvo");

      }
      getM_voBill().setItemFreeVO(irow, freevo);
    }
    // ͬ���ı�m_voBill
    getM_voBill().setItemValue(irow, "vbatchcode", voLot.getVbatchcode());
    getM_voBill().setItemValue(irow, "dvalidate", voLot.getDvalidate());
    getM_voBill().setItemValue(irow, "cqualitylevelid", voLot.getCqualitylevelid());
    getM_voBill().setItemValue(irow, "cqualitylevelname", voLot.getCqualitylevelname());

  }

  /**
   * �����ߣ����˾� ���ܣ����ñ���/��β��С��λ�� ������ ���أ� ���⣺ ���ڣ�(2001-11-23 18:11:18)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void setScaleOfCardPanel(nc.ui.pub.bill.BillCardPanel bill) {

    // ����

    nc.ui.ic.pub.scale.ScaleInit si = new nc.ui.ic.pub.scale.ScaleInit(
        getEnvironment().getUserID(), getEnvironment().getCorpID(),
        m_ScaleValue);

    try {
      si.setScale(bill, m_ScaleKey);
    } catch (Exception e) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000060")/* @res "��������ʧ�ܣ�" */
          + e.getMessage());
    }

  }

  /**
   * �����ߣ����˾� ���ܣ����ص���ʾ��ʾ��Ϣ�Ի����� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public void showErrorMessage(String sMsg, boolean bWarnSound) {
    // ��ʾ����
    if (bWarnSound)
      java.awt.Toolkit.getDefaultToolkit().beep();

    showErrorMessage(sMsg);

  }

  /**
   * 
   * ���ܣ� �����ļ���ͬ��״̬vo. ������ ���أ� ���⣺ ���ڣ�(2002-04-18 10:43:46) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public void synVO(nc.vo.pub.CircularlyAccessibleValueObject[] voaDi,
      String sWarehouseid, ArrayList m_alLocatorData) throws Exception {
    // ͬ��vo.
    if (getM_voBill() != null) {
      GeneralBillItemVO voaItem[] = getM_voBill().getItemVOs();
      int start = getDevInputCtrl().getStartItem();
      if (start >= 0 && voaDi.length > 0
          && voaItem.length >= (start + voaDi.length)) {
        for (int line = 0; line < voaDi.length; line++) {
          voaItem[start + line] = (GeneralBillItemVO) voaDi[line];
        }
      } else {
        SCMEnv.out("date error.");
      }
      // �����λ add by hanwei 2003-12-17
      nc.ui.ic.pub.bill.GeneralBillUICtl.setLocatorVO(voaItem,
          sWarehouseid, m_alLocatorData);
    }
  }
  
  /**
   * �����ߣ�yangb ���ܣ�add insert line ������ ���أ� ���⣺ ���ڣ�(2001-6-26 ���� 9:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void voBillAddLine(int row) {
    if(row>=0 && row<getBillCardPanel().getRowCount()){
      getM_voBill().setItemValue(row, "crowno", getBillCardPanel().getBodyValueAt(row, "crowno"));
      setUIVORowPosWhenNewRow(row, getM_voBill().getItemVOs()[row]);
    }
  }

  /**
   * �����ߣ������� ���ܣ�ճ���� ������ ���أ� ���⣺ ���ڣ�(2001-6-26 ���� 9:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void voBillPastLine(int row) {
    // Ҫ�����Ѿ������к�ִ��

    for (int i = 0; i < m_voaBillItem.length; i++) {
      getM_voBill().getChildrenVO()[row + i] = (GeneralBillItemVO) m_voaBillItem[i]
          .clone();
      
      setUIVORowPosWhenNewRow(row+i, getM_voBill().getItemVOs()[row+i]);
      
      getM_voBill().getChildrenVO()[row + i].setAttributeValue("crowno",
          getBillCardPanel().getBodyValueAt(row + i, "crowno"));
      getM_voBill().getItemVOs()[row + i].setCgeneralbid(null);
      getM_voBill().getItemVOs()[row + i].setCgeneralbb3(null);
      getM_voBill().getItemVOs()[row + i].setAttributeValue(
          "ncorrespondnum", null);
      getM_voBill().getItemVOs()[row + i].setAttributeValue(
          "ncorrespondastnum", null);
      m_alLocatorData.set(row + i, ((GeneralBillItemVO) (getM_voBill()
          .getChildrenVO()[row + i])).getLocator());
      getBillCardPanel().getBillModel().setValueAt(
          getM_voBill().getItemVOs()[row + i].getInv(), row + i,
          "invvo");
      getBillCardPanel().getBillModel().setValueAt(UFDZERO, row + i,
          IItemKey.NBARCODENUM);
      getBillCardPanel().getBillModel().setValueAt(UFBoolean.FALSE,
          row + i, "bbarcodeclose");
      getBillCardPanel().getBillModel().setValueAt(null, row + i,
          IItemKey.NKDNUM);

      getBillCardPanel().getBillModel().setValueAt(null, row + i,
          "cgeneralbid");
      getBillCardPanel().getBillModel().setValueAt(null, row + i,
          "ncorrespondnum");
      getBillCardPanel().getBillModel().setValueAt(null, row + i,
          "ncorrespondastnum");

      // //ճ���е���Ʒ���Ա༭
      // if (getBillCardPanel().getBodyItem("flargess")!=null)
      // {
      // getBillCardPanel().getBodyItem("flargess").setEnabled(true);
      // getBillCardPanel().getBodyItem("flargess").setEdit(true);
      // }
    }
    // �����Ƿ�����кŷ���
    //
    // voBillPastLineSetAttribe(row,m_voBill);
    setBtnStatusSN(row, true);
  }

  /**
   * �����ߣ������� ���ܣ�ճ���� ������ ���أ� ���⣺ ���ڣ�(2001-6-26 ���� 9:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected void voBillPastTailLine() {
    // Ҫ�����Ѿ������к�ִ��
    if (m_voaBillItem != null) {
      int row = getBillCardPanel().getBillTable().getRowCount()
          - m_voaBillItem.length;
      voBillPastLine(row);
    }
    GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
        getBillCardPanel(), getBillType());

  }

  /**
   * hw ���ܣ������ͨ��ҵ����־VO ������ ���أ� ���⣺ ���ڣ�(2004-6-8 20:42:43) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.vo.sm.log.OperatelogVO
   */
  public nc.vo.sm.log.OperatelogVO getNormalOperateLog() {
    nc.ui.pub.ClientEnvironment ce = getClientEnvironment();
    nc.vo.sm.log.OperatelogVO log = new nc.vo.sm.log.OperatelogVO();
    log.setCompanyname(ce.getCorporation().getUnitname());
    //�޸��ˣ������� ���ڣ�2009-04-20 ���ܣ�ȡ���жϣ�д��IP      
//    if (!nc.ui.pub.ClientEnvironment.getInstance().isInDebug()){
      log.setEnterip(nc.ui.sm.cmenu.Desktop.getApplet().getParameter(
          "USER_IP"));
      
//      }
        
    log.setPKCorp(getEnvironment().getCorpID());


    return log;
  }

  /**
   * ���ܣ����˳�ʵ�������ǿշ���ı����У���ӡ�á� ������ ���أ� ���⣺ ���ڣ�(2005-2-21 21:31:48)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.vo.ic.pub.bill.GeneralBillVO
   * @param voBill
   *            nc.vo.ic.pub.bill.GeneralBillVO
   */
  private GeneralBillVO getNoZeroNumVO(GeneralBillVO voBill) {
    if (voBill != null) {
      ArrayList alItems = new ArrayList();
      GeneralBillItemVO[] voaItem = voBill.getItemVOs();
      int size = voaItem.length;
      UFDouble ufdnum = null;
      UFDouble ufd0 = new UFDouble(0);
      int inotnull = 0;
      for (int i = 0; i < size; i++) {
        ufdnum = (UFDouble) voaItem[i]
            .getAttributeValue(getEnvironment().getNumItemKey());
        if (ufdnum != null && ufdnum.compareTo(ufd0) != 0) {
          alItems.add(voaItem[i]);
        }
      }
      inotnull = alItems.size();
      if (size > inotnull && inotnull > 0) {
        GeneralBillItemVO[] voNewItems = new GeneralBillItemVO[inotnull];
        alItems.toArray(voNewItems);
        GeneralBillVO voNewBill = (GeneralBillVO) voBill.clone();
        voNewBill.setChildrenVO(voNewItems);
        return voNewBill;
      } else if (inotnull == 0) {
        return null;
      } else if (size == inotnull) {
        return voBill;
      }
    }
    return null;
  }

  /**
   * ���� LotNumbRefPane1 ����ֵ��
   * 
   * @return nc.ui.ic.pub.lot.LotNumbRefPane
   */
  /* ���棺�˷������������ɡ� */
  public nc.ui.ic.pub.tools.VehicleRefPanel getVehicleRefPane() {
    if (ivjVehicleRefPane == null) {
      try {
        ivjVehicleRefPane = new nc.ui.ic.pub.tools.VehicleRefPanel();
        ivjVehicleRefPane.setName("ivjVehicleRefPane");
        ivjVehicleRefPane.setLocation(38, 1);
        ivjVehicleRefPane.setReturnCode(true);
        // ivjVehicleRefPane.setIsMutiSel(true);
        // user code begin {1}
        // user code end
      } catch (java.lang.Throwable ivjExc) {
        // user code begin {2}
        // user code end
        handleException(ivjExc);
      }
    }
    return ivjVehicleRefPane;
  }

  /**
   * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2005-1-11 18:53:03) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param voLot
   *            nc.vo.ic.pub.lot.LotNumbRefVO
   * @param irow
   *            int
   * 
   */
  protected void synline(GeneralBillItemVO vo, int iSelrow,
      boolean isFirstLine) {
    if (vo == null)
      return;

    if (getBillCardPanel().getBillModel().getRowState(iSelrow) == BillModel.NORMAL)
      getBillCardPanel().getBillModel().setRowState(iSelrow,
          BillModel.MODIFICATION);

    if (!isFirstLine
        || vo.getCinventoryid() != null
        && !vo.getCinventoryid().equals(
            getBillCardPanel().getBodyValueAt(iSelrow,
                "cinventoryid"))) {
      getBillCardPanel().setBodyValueAt(vo.getCinventoryid(), iSelrow,
          "cinventoryid");
      nc.ui.pub.beans.UIRefPane refCastunit = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getBodyItem("cinventorycode").getComponent());
      refCastunit.setPK(vo.getCinventoryid());

      getBillCardPanel().setBodyValueAt(refCastunit.getRefCode(),
          iSelrow, "cinventorycode");
      afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null, refCastunit
          .getRefCode(), "cinventorycode", iSelrow, BillItem.BODY));

    }
    if (getBillCardPanel().getBodyItem("castunitid") != null) {
      String oldvalue = (String) getBillCardPanel().getBodyValueAt(
          iSelrow, "castunitid");// refCastunit.getRefPK();
      getM_voBill().setItemValue(iSelrow, "cselastunitid",
          vo.getCastunitid());
      nc.vo.ic.pub.DesassemblyVO voDesa = (nc.vo.ic.pub.DesassemblyVO) getM_voBill()
          .getItemValue(iSelrow, "desainfo");

      if (voDesa.getMeasInfo() == null) {
        nc.vo.bd.b15.MeasureRateVO[] voMeasInfo = m_voInvMeas
            .getMeasInfo(getEnvironment().getCorpID(), vo
                .getCinventoryid());
        voDesa.setMeasInfo(voMeasInfo);
      }
      getM_voBill().setItemValue(iSelrow, "idesatype",
          new Integer(voDesa.getDesaType()));
      getBillCardPanel().setBodyValueAt(
          new Integer(voDesa.getDesaType()), iSelrow, "idesatype");
      getBillCardPanel().setBodyValueAt(vo.getCastunitid(), iSelrow,
          "cselastunitid");

      if (!isFirstLine
          || (voDesa.getDesaType() == nc.vo.ic.pub.DesassemblyVO.TYPE_NO && !GenMethod
              .isStringEqual(vo.getCastunitid(), oldvalue))) {

        getBillCardPanel().setBodyValueAt(vo.getCastunitid(), iSelrow,
            "castunitid");
        nc.ui.pub.beans.UIRefPane refCastunit = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem("castunitname").getComponent());
        refCastunit.setPK(vo.getCastunitid());
        getBillCardPanel().setBodyValueAt(vo.getCastunitname(),
            iSelrow, "castunitname");

        nc.vo.bd.b15.MeasureRateVO voMeas = m_voInvMeas.getMeasureRate(
            getM_voBill().getItemInv(iSelrow).getCinventoryid(), vo
                .getCastunitid());
        if (voMeas != null) {

          UFDouble hsl = voMeas.getMainmeasrate();
          getBillCardPanel().setBodyValueAt(hsl, iSelrow, "hsl");
          hsl = (UFDouble) getBillCardPanel().getBodyValueAt(iSelrow,
              "hsl");
          getM_voBill().setItemValue(iSelrow, "hsl", hsl);
          getM_voBill().setItemValue(iSelrow, "isSolidConvRate",
              voMeas.getFixedflag());

        }

        getM_voBill().setItemValue(iSelrow, "castunitid",
            vo.getCastunitid());

        if (getM_voBill().getItemValue(iSelrow, "isSolidConvRate") != null
            && ((Integer) getM_voBill().getItemValue(iSelrow,
                "isSolidConvRate")).intValue() == 0) {
          getBillCardPanel().setBodyValueAt(
              vo.getAttributeValue("hsl"), iSelrow, "hsl");
          getM_voBill().setItemValue(iSelrow, "hsl",
              vo.getAttributeValue("hsl"));
        }

        afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null, vo
            .getCastunitname(), "castunitname", iSelrow,
            BillItem.BODY));

      }
      // ��һ�У�������������λhsl��ͬ�����滻hsl
      if (getM_voBill().getItemValue(iSelrow, "isSolidConvRate") != null
          && ((Integer) getM_voBill().getItemValue(iSelrow,
              "isSolidConvRate")).intValue() == 0) {
        getBillCardPanel().setBodyValueAt(vo.getAttributeValue("hsl"),
            iSelrow, "hsl");
        getM_voBill().setItemValue(iSelrow, "hsl",
            vo.getAttributeValue("hsl"));
        afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null, vo
            .getAttributeValue("hsl"), "hsl", iSelrow,
            BillItem.BODY));
      }
    }
    //
    if (getBillCardPanel().getBodyItem("vbatchcode") != null) {
      // if
      // (!isFirstLine||!GenMethod.isStringEqual(vo.getVbatchcode(),(String)m_voBill.getItemValue(iSelrow,
      // "vbatchcode"))){
      if (!isFirstLine
          || !GenMethod.isStringEqual(vo.getVbatchcode(),
              (String) getBillCardPanel().getBodyValueAt(iSelrow,
                  "vbatchcode"))) {
        getBillCardPanel().setBodyValueAt(vo.getVbatchcode(), iSelrow,
            "vbatchcode");
        BatchCodeDefSetTool.setBatchCodeInfo(getBillCardPanel(),
            iSelrow, vo.getCinventoryid(), vo.getVbatchcode(),
            getEnvironment().getCorpID());

        getM_voBill().setItemValue(iSelrow, "vbatchcode",
            vo.getVbatchcode());

        getM_voBill().setItemValue(iSelrow, "scrq", vo.getScrq());
        getM_voBill().setItemValue(iSelrow, "dvalidate",
            vo.getDvalidate());
      }
    }
    if (getBillCardPanel().getBodyItem("cvendorid") != null) {
      // if
      // (!isFirstLine||!GenMethod.isStringEqual(vo.getCvendorid(),(String)m_voBill.getItemValue(iSelrow,
      // "cvendorid"))){
      if (!isFirstLine
          || !GenMethod.isStringEqual(vo.getCvendorid(),
              (String) getBillCardPanel().getBodyValueAt(iSelrow,
                  "cvendorid"))) {

        getBillCardPanel().setBodyValueAt(vo.getCvendorid(), iSelrow,
            "cvendorid");
        getBillCardPanel().setBodyValueAt(vo.getVvendorname(), iSelrow,
            "vvendorname");
        UIRefPane ref = (UIRefPane) getBillCardPanel().getBodyItem(
            "vvendorname").getComponent();
        ref.setPK(vo.getCvendorid());
        getM_voBill().setItemValue(iSelrow, "cvendorid",
            vo.getCvendorid());
        getM_voBill().setItemValue(iSelrow, "vvendorname",
            vo.getVvendorname());
      }
    }
    if (getBillCardPanel().getBodyItem("cprojectid") != null) {
      String cprojectid = (String) vo.getAttributeValue("cprojectid");
      if (!isFirstLine
          || !GenMethod.isStringEqual(cprojectid,
              (String) getBillCardPanel().getBodyValueAt(iSelrow,
                  "cprojectid"))) {

        getBillCardPanel().setBodyValueAt(cprojectid, iSelrow,
            "cprojectid");
        getBillCardPanel().setBodyValueAt(
            vo.getAttributeValue("cprojectname"), iSelrow,
            "cprojectname");
        UIRefPane ref = (UIRefPane) getBillCardPanel().getBodyItem(
            "cprojectname").getComponent();
        ref.setPK(cprojectid);
        getM_voBill().setItemValue(iSelrow, "cprojectid", cprojectid);
        getM_voBill().setItemValue(iSelrow, "cprojectname",
            vo.getAttributeValue("cprojectname"));
      }
    }

    if (getBillCardPanel().getBodyItem("vtransfercode") != null
        && vo.getAttributeValue("vtransfercode") != null) {
      if (!isFirstLine
          || !GenMethod.isStringEqual((String) vo
              .getAttributeValue("vtransfercode"),
              (String) getBillCardPanel().getBodyValueAt(iSelrow,
                  "vtransfercode"))) {

        getBillCardPanel().setBodyValueAt(
            vo.getAttributeValue("vtransfercode"), iSelrow,
            "vtransfercode");

        getM_voBill().setItemValue(iSelrow, "vtransfercode",
            (String) vo.getAttributeValue("vtransfercode"));
      }
    }

    String userdef = null;
    for (int i = 1; i <= 20; i++) {
      userdef = "vuserdef" + String.valueOf(i);
      if (vo.getAttributeValue(userdef) == null
          && getBillCardPanel().getBodyValueAt(iSelrow, userdef) != null)
        continue;

      getBillCardPanel().setBodyValueAt(vo.getAttributeValue(userdef),
          iSelrow, userdef);
      getM_voBill().setItemValue(iSelrow, userdef,
          vo.getAttributeValue(userdef));
      userdef = "pk_defdoc" + String.valueOf(i);
      getBillCardPanel().setBodyValueAt(vo.getAttributeValue(userdef),
          iSelrow, userdef);
      getM_voBill().setItemValue(iSelrow, userdef,
          vo.getAttributeValue(userdef));

    }
    if (getBillCardPanel().getBodyItem("vfree0") != null) {
      FreeVO voFree = getM_voBill().getItemVOs()[iSelrow].getFreeItemVO();

      for (int i = 1; i <= 5; i++) {
        userdef = "vfree" + String.valueOf(i);
        getBillCardPanel().setBodyValueAt(
            vo.getAttributeValue(userdef), iSelrow, userdef);
        getM_voBill().setItemValue(iSelrow, userdef,
            vo.getAttributeValue(userdef));
        voFree
            .setAttributeValue(userdef, vo
                .getAttributeValue(userdef));

      }
      getBillCardPanel().setBodyValueAt(voFree.getVfree0(), iSelrow,
          "vfree0");
    }

    if (((null != getFunctionNode() && "40080602".equals(getFunctionNode())) ||
        GeneralBillUICtl.isChangePrice()
        )&& getBillCardPanel().getBodyItem("nprice") != null) {
      UFDouble uinprice = CheckTools.toUFDouble(getBillCardPanel()
          .getBodyValueAt(iSelrow, "nprice"));
      UFDouble vonprice = vo.getNprice();
      if (null == uinprice && null != vonprice) {
        getBillCardPanel().setBodyValueAt(vonprice, iSelrow, "nprice");
        getBillCardPanel().getBillModel().execEditFormulaByKey(iSelrow,
            "nprice");
        afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null,
            vonprice, "nprice", iSelrow, BillItem.BODY));
      }
    }
    if (getBillCardPanel().getBodyItem(getEnvironment().getNumItemKey()) != null) {
      UFDouble num = SmartVOUtilExt.mult(new UFDouble((-1)
          * (getInOutFlag())), vo.getNinnum());
      UFDouble uinum = CheckTools.toUFDouble(getBillCardPanel()
          .getBodyValueAt(iSelrow, getEnvironment().getNumItemKey()));
		if(getBillTypeCode().equals(ScmConst.m_otherOut)){//add by linsf
			if (!isFirstLine || (/*uinum == null && linsf*/num != null)) {
				// || !GenMethod.isUFDoubleEqual(num, uinum)) {
				// num = new UFDouble((-1) * (getInOutFlag())).multiply(vo
				// .getNinnum());
				getBillCardPanel().setBodyValueAt(num, iSelrow,
						getEnvironment().getNumItemKey());
				afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null, num,
						getEnvironment().getNumItemKey(), iSelrow,
						BillItem.BODY));
			}
		}
		else{
			if (!isFirstLine || (uinum == null && num != null)) {
				// || !GenMethod.isUFDoubleEqual(num, uinum)) {
				// num = new UFDouble((-1) * (getInOutFlag())).multiply(vo
				// .getNinnum());
				getBillCardPanel().setBodyValueAt(num, iSelrow,
						getEnvironment().getNumItemKey());
				afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null, num,
						getEnvironment().getNumItemKey(), iSelrow,
						BillItem.BODY));
			}
		}
      
      num = SmartVOUtilExt.mult(new UFDouble((-1) * (getInOutFlag())), vo
          .getNinassistnum());
      uinum = CheckTools.toUFDouble(getBillCardPanel().getBodyValueAt(
          iSelrow, getEnvironment().getAssistNumItemKey()));
      if (vo.getCastunitid() != null
          && (!isFirstLine || (uinum == null && num != null))) {
        // num = new UFDouble((-1) * (getInOutFlag())).multiply(vo
        // .getNinassistnum());
        getBillCardPanel().setBodyValueAt(num, iSelrow,
            getEnvironment().getAssistNumItemKey());
        afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null, num,
            getEnvironment().getAssistNumItemKey(), iSelrow,
            BillItem.BODY));

      }

    }

    if (getBillCardPanel().getBodyItem(
        getEnvironment().getGrossNumItemKey()) != null) {
      InvVO voInvo = getM_voBill().getItemInv(iSelrow);
      if (voInvo.getIsmngstockbygrswt() != null
          && voInvo.getIsmngstockbygrswt().intValue() == 1) {
        UFDouble numgross = SmartVOUtilExt.mult(new UFDouble((-1)
            * (getInOutFlag())), vo.getNingrossnum());
        UFDouble uinumgross = CheckTools.toUFDouble(getBillCardPanel()
            .getBodyValueAt(iSelrow,
                getEnvironment().getGrossNumItemKey()));
        if (!isFirstLine || (uinumgross == null && numgross != null)) {
          // || !GenMethod.isUFDoubleEqual(numgross, uinumgross)) {
          // numgross=vo.getNingrossnum();
          // if(numgross!=null)
          // numgross = new UFDouble((-1) *
          // (getInOutFlag())).multiply(numgross);

          getBillCardPanel().setBodyValueAt(numgross, iSelrow,
              getEnvironment().getGrossNumItemKey());
          afterEdit(new nc.ui.pub.bill.BillEditEvent(this, null,
              numgross, getEnvironment().getGrossNumItemKey(),
              iSelrow, BillItem.BODY));

        }
      }

    }

    // ��Ӧ���ݺ�
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondcode(), iSelrow,
        IItemKey.CORRESCODE);
    // ��Ӧ��������
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondtype(), iSelrow,
        "ccorrespondtype");
    // ��Ӧ���ݱ�ͷOID
    // ����ģ����б���λ����������ʾ��ccorrespondhid,ccorrespondbid,�Ա�������Ķ�Ӧ��ͷ������OID
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondhid(), iSelrow,
        "ccorrespondhid");
    // ��Ӧ���ݱ���OID
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondbid(), iSelrow,
        "ccorrespondbid");

    // ��;���
    if (getBillCardPanel().getBodyItem("bonroadflag") != null) {
      getBillCardPanel()
          .setBodyValueAt(vo.getAttributeValue("bonroadflag"),
              iSelrow, "bonroadflag");
    }
    getM_voBill().setItemValue(iSelrow, "bonroadflag",
        vo.getAttributeValue("bonroadflag"));

    getM_voBill().setItemValue(iSelrow, "ccorrespondbid",
        vo.getCcorrespondbid());
    // getICCorBillRef().getCorBillBid());
    getM_voBill().setItemValue(iSelrow, "ccorrespondhid",
        vo.getCcorrespondhid());
    // getICCorBillRef().getCorBillHid());
    getM_voBill().setItemValue(iSelrow, IItemKey.CORRESCODE,
        vo.getCcorrespondcode());
    // getICCorBillRef().getCorBillCode());
    getM_voBill().setItemValue(iSelrow, "ccorrespondtype",
        vo.getCcorrespondtype());
    // getICCorBillRef().getCorBillType());

    /*
     * if (null != getFunctionNode() &&
     * "40080602".equals(getFunctionNode()))
     * getM_voBill().setItemValue(iSelrow, "nprice",vo.getNprice());
     */
    //�޸��ˣ������� �޸�ʱ�䣺2008-7-24 ����03:32:53 �޸�ԭ�򣺳���������ʱ�������ⵥ��λֻ��һ��������ⵥ�������Ӧ��ⵥ�ź���ⵥ"��λ"�Զ�������ⵥ��"��λ"�У�
    if (getBillCardPanel().getBodyItem("vspacename") != null
        && null != getM_voBill().getItemInv(iSelrow).getOuttrackin()
        && getM_voBill().getItemInv(iSelrow).getOuttrackin().booleanValue()
        && getM_voBill().getItemInv(iSelrow).getInOutFlag() != InOutFlag.IN) {
      filterSpace(iSelrow);
      nc.ui.pub.beans.UIRefPane refSpace = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getBodyItem("vspacename").getComponent());
      if(refSpace.getRefModel()!=null){
        java.util.Vector refdata = refSpace.getRefModel().getData();
        if (refdata != null && refdata.size() == 1){
          refSpace.setSelectedData(refdata);
          refSpace.getRefModel().setSelectedData(refdata);
          String cspaceid = refSpace.getRefPK();
          String csname = refSpace.getRefName();
          getEditCtrl().setRowSpaceData(iSelrow, cspaceid, csname);
        }
      }
      
    }

  }

  protected javax.swing.JFileChooser m_chooser = null;

  DefVO[] m_defBody = null;

  DefVO[] m_defHead = null;

  String m_sDir = null;

  // ���ݵ����˵�
  protected Vector m_vBillExcel = null;

  /**
   * 
   * �����������������ݿ��̹�������ÿ��̵Ļ���������
   * <p>
   * <b>����˵��</b>
   * 
   * @param pk_cumangid
   *            ���̹�������ID
   * @return ���̻�������ID
   *         <p>
   * @author duy
   * @time 2007-3-20 ����10:50:26
   */
  private String getPk_cubasdoc(String pk_cumandoc) {
    if (pk_cumandoc == null)
      return null;
    try {
      Object[] pks = (Object[]) nc.ui.scm.pub.CacheTool.getColumnValue(
          "bd_cumandoc", "pk_cumandoc", "pk_cubasdoc",
          new String[] { pk_cumandoc });
      if (pks != null)
        return (String) pks[0];
    } catch (BusinessException e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this, null, e);
    }
    return null;
  }

  /**
   * �����ˣ������� �������ڣ�2008-5-16����11:31:47 ����ԭ��ȡ��ҵ�����͵ĺ������
   * 
   * @param pk_busitype
   * @return
   */
  private String getBusiVerifyrule(String pk_busitype) {
    if (pk_busitype == null)
      return null;
    try {
      Object[] pks = (Object[]) nc.ui.scm.pub.CacheTool.getColumnValue(
          "bd_busitype", "pk_busitype", "verifyrule",
          new String[] { pk_busitype });
      if (pks != null)
        return (String) pks[0];
    } catch (BusinessException e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this, null, e);
    }
    return null;
  }

  /**
   * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-8-27 10:54:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param voNew
   *            nc.vo.ic.pub.bill.GeneralBillVO
   * 
   * �޸����⣺�ɵ����������ɵ������ⵥʱ����ʽ&��ʽ������λû�б����ϡ�
   * �޸�ԭ���������λ����ʱ��ʹ��locator�б�������ݣ����Ǳ�������ʾ��space��
   * ��space�޸ĺ�afterEdit�����afterSpaceEdit�������Զ�ͬ��space��locator��
   * �����ƻ������ε���ʱ��space�����Զ�ͬ��locator���������治�ϡ� �޸�ʱ�䣺2005-11-24 �޸��ߣ�ShawBing
   * 
   */
  protected void calcSpace(GeneralBillVO voNew) {
    if (voNew != null && voNew.getItemVOs() != null) {
      GeneralBillItemVO[] voItems = voNew.getItemVOs();
      // ��¼��δ��
      boolean isLocator = false;
      // ��δ����ID
      String[] aryItemField11 = new String[] {
          "vspacename->getColValue(bd_cargdoc,csname,pk_cargdoc,cspaceid)",
          "vspacecode->getColValue(bd_cargdoc,cscode,pk_cargdoc,cspaceid)" };

      // boolean isSN = false;

      for (int i = 0; i < voItems.length; i++) {

        if (voItems[i].getCspaceid() != null) {
          isLocator = true;

          /** 1-1 ���沿��Ϊ2005-11-25 Shaw �޸����ݣ��޸�˵��������ע��* */
          // ���ƻ���ʽ���ɵ���ʱ��space��ֵ����locaorûֵ�����»�λ�޷�����
          // ͬ��locator
          if (voItems[i].getLocator() == null
              || voItems[i].getLocator().length == 0) {

            LocatorVO[] voLocators = new LocatorVO[1];
            voLocators[0] = new LocatorVO();
            voLocators[0].setCspaceid(voItems[i].getCspaceid());

            voItems[i].setLocator(voLocators);
            m_alLocatorData.remove(i);
            m_alLocatorData.add(i, voLocators);

            getEditCtrl().resetSpace(i);
          }
          /** 1-1 �޸Ľ���* */
        }

        if (voItems[i].getLocator() != null
            && voItems[i].getLocator().length > 0) {
          nc.vo.pub.SuperVOUtil.execFormulaWithVOs(voItems[i]
              .getLocator(), aryItemField11, null);
          isLocator = true;

        }
        if (voItems[i].getSerial() != null
            && voItems[i].getSerial().length > 0) {
          nc.vo.pub.SuperVOUtil.execFormulaWithVOs(voItems[i]
              .getSerial(), aryItemField11, null);
          // isSN = true;
        }
      }

      if (isLocator) {
        nc.vo.pub.SuperVOUtil.execFormulaWithVOs(voItems,
            aryItemField11, null);

      }

    }
    return;
  }

  /**
   * �����λ��ɫ
   */
  public void clearOrientColor() {
    if (m_isLocated) {
      if (getM_iCurPanel() == BillMode.List)
        nc.ui.scm.pub.report.OrientDialog
            .clearOrientColor(getBillListPanel().getHeadTable());
      else
        nc.ui.scm.pub.report.OrientDialog
            .clearOrientColor(getBillCardPanel().getBillTable());
      m_isLocated = false;
    }

  }

  /**
   * ������ ���ܣ�ִ�����⹫ʽ. Ŀǰֻ�����۳��ⵥ���ش˷���. ������ ���أ� ���⣺ ���ڣ�(2004-7-20 17:19:12)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   */
  protected void execExtendFormula(ArrayList alListData) {
  }

  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-1-24 11:35:23) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return boolean
   */
  /**
   * ���� BillCardPanel1 ����ֵ��
   * 
   * @return nc.ui.pub.bill.BillCardPanel bEditable:�Ƿ���Ա༭�� bSaveable:�Ƿ����ֱ�ӱ���
   * 
   * ����༭����״̬�� ֻ����������ʾ �޸ģ������޸ģ�����Ҫͨ�����ݲ��ܱ��� ���棺����ֱ�ӱ��棬ֻ�����״̬�ſ��ԣ�
   * 
   */
  /* ���棺�˷������������ɡ� */
  public BarCodeDlg getBarCodeDlg(boolean bEditable, boolean bSaveable) {
    m_dlgBarCodeEdit = new BarCodeDlg(this, this, getEnvironment()
        .getCorpID(), bEditable, bSaveable);
    return m_dlgBarCodeEdit;
  }

  /**
   * �˴����뷽��˵���� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2002-7-26 11:51:17) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return javax.swing.JFileChooser
   */
  protected javax.swing.JFileChooser getChooser() {

    if (m_chooser == null) {
      m_chooser = new JFileChooser();
      m_chooser.setDialogType(JFileChooser.SAVE_DIALOG);

      // m_chooser.setFileSelectionMode(UIFileChooser.DIRECTORIES_ONLY);
    }
    return m_chooser;

  }

  /**
   * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-8-31 15:56:03) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return java.lang.String
   */
  public String getDefaultDir() {

    String sDir = null;
    try {
      nc.ui.ic.pub.property.PropertyCtrl ctrl = new nc.ui.ic.pub.property.PropertyCtrl();
      sDir = ctrl
          .getItemValueByKey(nc.ui.ic.pub.property.IPropertyFile.ExcelBarcode_FileName_Param2);
      if (sDir == null) {
        java.io.File file = new java.io.File(
            nc.ui.ic.pub.property.IPropertyFile.ExcelBarcode_FileName_Param2_Value);
        if (!file.exists())
          file.mkdir();
        sDir = file.getAbsolutePath();
      }
    } catch (Exception ex) {
      nc.vo.scm.pub.SCMEnv.error(ex);
    }
    return sDir;

  }

  /**
   * �����ߣ�� ���ܣ���������˺󣩱��浼������
   */
  public int onImportSignedBillBarcode(GeneralBillVO voUpdatedBill,
      boolean bCheckNum) throws Exception {

    try {
      // �õ����ݴ��󣬳��� ------------ EXIT -------------------
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@�޸ı��浥�ݿ�ʼ��");/*-=notranslate=-*/
      if (voUpdatedBill == null || voUpdatedBill.getParentVO() == null
          || voUpdatedBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000055")/*
                                     * @res
                                     * "����Ϊ�գ�"
                                     */);
      }

      // ����ʱ��������ʹ����������Լ�飬���������ʱ��ǿ�Ʋ�ͨ��
      String sMsg = BarcodeparseCtrl
          .checkBarcodesubIntegrality(voUpdatedBill.getItemVOs());
      if (sMsg != null) {
        MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes
            .getInstance().getStrByID("4008bill",
                "UPPSCMCommon-000059")/* @res "����" */, sMsg);
        return 0;
      }

      if (bCheckNum) {
        sMsg = BarcodeparseCtrl.checkNumWithBarNum(voUpdatedBill
            .getItemVOs(), true);
        if (sMsg != null) {
          MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes
              .getInstance().getStrByID("4008bill",
                  "UPPSCMCommon-000059")/* @res "����" */,
              sMsg);
          return 0;
        }

      }

      // ���õ�������
      voUpdatedBill.setHeaderValue("cbilltypecode", getBillType());
      // 05/07�����Ƶ���Ϊ��ǰ����Ա
      // remark by zhx onSave() set coperatorid into VO
      // voUpdatedBill.setHeaderValue("coperatorid",
      // getEnvironment().getUserID());
      timer.showExecuteTime("@@���õ������ͣ�");/*-=notranslate=-*/
      GeneralBillVO voBill = (GeneralBillVO) getM_voBill().clone();
      timer.showExecuteTime("@@m_voBill.clone()��");/*-=notranslate=-*/
      // ydy 0826
      // ��voUpdatedBill�ϵ�����VO�ŵ�voBill��
      setBarCodeOnUI(voBill, (GeneralBillItemVO[]) voUpdatedBill
          .getChildrenVO());
      // voBill.setIDItems(voUpdatedBill);

      GeneralBillHeaderVO voHead = voBill.getHeaderVO();
      // ǩ����
      //voHead.setCregister(getEnvironment().getUserID());
      // --------------------------------------------���Բ��ǵ�ǰ��¼��λ�ĵ��ݣ����Բ���Ҫ�޸ĵ�λ��
      voHead.setPk_corp(getEnvironment().getCorpID());
      // ��Ϊ��¼���ں͵��������ǿ��Բ�ͬ�ģ����Ա���Ҫ��¼���ڡ�
      voHead.setDaccountdate(new nc.vo.pub.lang.UFDate(getEnvironment()
          .getLogDate()));
      // vo����Ҫ����ƽ̨������Ҫ���ɺ�ǩ�ֺ�ĵ���
      //voHead.setFbillflag(new Integer(nc.vo.ic.pub.bill.BillStatus.SIGNED));
      voHead.setCoperatoridnow(getEnvironment().getUserID()); // ��ǰ����Ա2002-04-10.wnj
      voHead.setAttributeValue("clogdatenow", getEnvironment()
          .getLogDate()); // ��ǰ��¼����2003-01-05
      // clear audit info
      voHead.setCauditorid(null);
      voHead.setDauditdate(null);

      voUpdatedBill.setAccreditUserID(getEnvironment().getUserID());

      ArrayList alRetData = new ArrayList();
      try {
        alRetData = onImportSignedBillBarcodeKernel(voBill,
            voUpdatedBill);
      } catch (RightcheckException e) {
        showErrorMessage(e.getMessage()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000069")/*
                                   * @res
                                   * ".\nȨ��У�鲻ͨ��,����ʧ��! "
                                   */);
        getAccreditLoginDialog()
            .setCorpID(getEnvironment().getCorpID());
        getAccreditLoginDialog().clearPassWord();
        if (getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
          String sUserID = getAccreditLoginDialog().getUserID();
          if (sUserID == null) {
            throw new Exception(nc.ui.ml.NCLangRes.getInstance()
                .getStrByID("4008bill", "UPP4008bill-000070")/*
                                         * @res
                                         * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                         */);
          } else {
            // voUpdatedBill.setAccreditUserID(sUserID);
            voBill.setAccreditBarcodeUserID(e
                .getFunCodeForRightCheck(), sUserID);
            alRetData = onImportSignedBillBarcodeKernel(voBill,
                voUpdatedBill);
          }
        } else {
          throw new Exception(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000070")/*
                                       * @res
                                       * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                       */);

        }
      } catch (Exception e) {
        // �޸��ˣ������� �޸����ڣ�2007-10-31����04:46:03
        // �޸�ԭ����Ϊ����RightcheckException�ᱻ��һ�㣬���������´�����ദ�����㡣
        Exception eCause = null;
        if (null != e && null != e.getCause())
          eCause = (Exception) e.getCause();
        if (null != eCause
            && eCause.getClass() != RightcheckException.class
            && null != eCause.getCause())
          eCause = (Exception) eCause.getCause();
        if (null != eCause
            && eCause.getClass() != RightcheckException.class
            && null != eCause.getCause())
          eCause = (Exception) eCause.getCause();
        if (null != eCause
            && eCause.getClass() == RightcheckException.class) {

          showErrorMessage(e.getMessage()
              + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                  "4008bill", "UPP4008bill-000069")/*
                                     * @res
                                     * ".\nȨ��У�鲻ͨ��,����ʧ��! "
                                     */);
          getAccreditLoginDialog().setCorpID(
              getEnvironment().getCorpID());
          getAccreditLoginDialog().clearPassWord();
          if (getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
            String sUserID = getAccreditLoginDialog().getUserID();
            if (sUserID == null) {
              throw new Exception(nc.ui.ml.NCLangRes
                  .getInstance().getStrByID("4008bill",
                      "UPP4008bill-000070")/*
                                   * @res
                                   * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                   */);
            } else {
              // voUpdatedBill.setAccreditUserID(sUserID);
              voBill
                  .setAccreditBarcodeUserID(
                      ((RightcheckException) eCause)
                          .getFunCodeForRightCheck(),
                      sUserID);
              alRetData = onImportSignedBillBarcodeKernel(voBill,
                  voUpdatedBill);
            }
          } else {
            throw new Exception(nc.ui.ml.NCLangRes.getInstance()
                .getStrByID("4008bill", "UPP4008bill-000070")/*
                                         * @res
                                         * "Ȩ��У�鲻ͨ��,����ʧ��. "
                                         */);

          }

        } else
          throw e;
      }

      if (alRetData == null || alRetData.size() < 0) {
        SCMEnv.out("return data error.");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000056")/*
                                     * @res
                                     * "�����Ѿ����棬������ֵ���������²�ѯ���ݡ�"
                                     */);
      }
      // 0 ---- ��ʾ��ʾ��Ϣ
      if (alRetData.get(0) != null
          && alRetData.get(0).toString().trim().length() > 0)
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000122")/* @res "���뱣��ɹ���" */
            + (String) alRetData.get(0));
      else
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000122")/* @res "���뱣��ɹ���" */);

      // ###################################################
      // �������õ��ݱ�ͷts
      SMGeneralBillVO smbillvo = null;
      smbillvo = (SMGeneralBillVO) alRetData.get(2);
      String sHeaderTs = smbillvo.getHeaderVO().getTs();
      String sTsKey = "ts";
      nc.ui.pub.bill.BillItem billItem = getBillCardPanel().getHeadItem(
          sTsKey);
      if (billItem != null) {
        getBillCardPanel().setHeadItem(sTsKey, sHeaderTs);
      }
      getM_voBill().getHeaderVO().setTs(sHeaderTs);

      getM_voBill().setSmallBillVO(smbillvo);
      voUpdatedBill.setSmallBillVO(smbillvo);
      // ###################################################
      // ��������״̬
      GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
      // hanwei 2004-0916
      GeneralBillVO.setBillBCVOStatus(voUpdatedBill, nc.vo.pub.VOStatus.UNCHANGED);

      // ��Ӵ˷�������������VOΪ�պ�û�����m_voBill��Ӧ������VO
      getM_voBill().setIDClearBarcodeItems(voUpdatedBill);

      getM_voBill().clearAccreditBarcodeUserID();

      // ���½���ʱ��ts
      getGenBillUICtl()
          .setBillCardPanelData(getBillCardPanel(), smbillvo);

      timer.showExecuteTime(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000123")/*
                             * @res
                             * "@@m_voBill.setIDItems(voUpdatedBill)��"
                             */);

      // ˢ���б�����
      updateBillToList(getM_voBill());
      timer.showExecuteTime(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000124")/*
                             * @res
                             * "@@ˢ���б�����updateBillToList(m_voBill)��"
                             */);

    } catch (java.net.ConnectException ex1) {
      SCMEnv.out(ex1.getMessage());
      if (showYesNoMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000104")/*
                             * @res
                             * "��ǰ�����жϣ��Ƿ񽫵�����Ϣ���浽Ĭ��Ŀ¼��"
                             */
      ) == MessageDialog.ID_YES) {
        // ���浥����Ϣ��Ĭ��Ŀ¼
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_EXPORT_TO_DIRECTORY));
        // onBillExcel(1);
      }
    } catch (Exception e) {
      // �쳣�����׳����������̴�����Ϊ��Ӱ�������̡�
      throw e;

    }
    return 1;
  }

  /**
   * ?user> ���ܣ����������ĵ����е��������õ�BillVO�� ������ ���أ� ���⣺ ���ڣ�(2004-8-24 14:02:09)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @param billVO
   *            nc.vo.ic.pub.bill.GeneralBillVO
   * @param voaBarcodes
   *            nc.vo.ic.pub.bill.GeneralBillItemVO[]
   */
  public void setBarCodeOnUI(GeneralBillVO billVO,
      GeneralBillItemVO[] voaItems) {

    if (voaItems.length <= 0)
      return;
    Hashtable htbItemBarcodeVos = getBarcodeCtrl().getHtbItemBarcodeVos(
        voaItems);

    GeneralBillItemVO[] billItemsAll = (GeneralBillItemVO[]) billVO
        .getChildrenVO();
    if (billItemsAll.length <= 0)
      return;

    if (htbItemBarcodeVos != null && htbItemBarcodeVos.size() > 0) {

      GeneralBillItemVO billItemTemp = null;
      if (htbItemBarcodeVos != null) {

        String sRowNo = null;
        for (int i = 0; i < billItemsAll.length; i++) {
          sRowNo = billItemsAll[i].getCrowno();
          if (sRowNo != null && htbItemBarcodeVos.containsKey(sRowNo)) {
            billItemTemp = (GeneralBillItemVO) htbItemBarcodeVos
                .get(sRowNo);
            billItemsAll[i].setBarCodeVOs(billItemTemp
                .getBarCodeVOs());
          }

        }

      }

    }

  }

  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-10-30 15:06:35) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected PrintDataInterface getDataSourceNew() {
    // if (null == m_dataSource) {
    PrintDataInterface ds = new PrintDataInterface();
    BillData bd = getBillCardPanel().getBillData();
    ds.setBillData(bd);
    ds.setModuleName(getFunctionNode());
    ds.setTotalLinesInOnePage(getPrintEntry().getBreakPos());
    ds.setFormulaJudge(new DefaultFormulaJudge(getFunctionNode(), getEnvironment().getCorpID()));
    // }
    return ds;
  }

  /**
   * ���˫���¼� �������ڣ�(2001-6-20 17:19:03)
   */
  public void mouse_doubleclick(nc.ui.pub.bill.BillMouseEnent e) {
    if (e.getPos() == BillItem.HEAD) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_SWITCH));
      // onSwitch();
    }
    
//  ���ο�����չ
    getPluginProxy().mouse_doubleclick(e);

  }

  /*
   * ���� Javadoc��
   * 
   * @see nc.ui.scm.pub.bill.IBillExtendFun#getExtendBtns()
   */
  public ButtonObject[] getExtendBtns() {
    return null;
  }

  /*
   * ���� Javadoc��
   * 
   * @see nc.ui.scm.pub.bill.IBillExtendFun#onExtendBtnsClick(nc.ui.pub.ButtonObject)
   */
  public void onExtendBtnsClick(ButtonObject bo) {

  }

  /*
   * ���� Javadoc��
   * 
   * @see nc.ui.scm.pub.bill.IBillExtendFun#setExtendBtnsStat(int)
   *      BillMode.New �������� BillMode.Browse ��� BillMode.Update �޸�
   * 
   * 
   */
  public void setExtendBtnsStat(int iState) {

  }

  private boolean isExistInBatch(String pk_invmandoc, String vbatchcode) {
    BatchcodeVO vos = getBCVO(pk_invmandoc, vbatchcode);
    if (vos == null)
      return false;
    else
      return true;
  }

  private BatchcodeVO getBCVO(String pk_invmandoc, String vbatchcode) {
    ConditionVO[] voCons = new ConditionVO[2];
    voCons[0] = new ConditionVO();
    voCons[0].setFieldCode("pk_invbasdoc");
    voCons[0].setValue(pk_invmandoc);
    voCons[0].setLogic(true);
    voCons[0].setOperaCode("=");

    voCons[1] = new ConditionVO();
    voCons[1].setFieldCode("vbatchcode");
    voCons[1].setValue(vbatchcode);
    voCons[1].setLogic(true);
    voCons[1].setOperaCode("=");
    BatchcodeVO[] vos = null;
    try {
      vos = BatchcodeHelper.queryBatchcode(voCons, getEnvironment()
          .getCorpID());
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
    return (vos == null || vos.length == 0) ? null : vos[0];
  }

  /**
   * get �������ڣ�(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  protected ClientUISortCtl getListHeadSortCtl() {
    return m_listHeadSortCtl;
  }

  /**
   * get �������ڣ�(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  protected ClientUISortCtl getListBodySortCtl() {
    return m_listBodySortCtl;
  }

  /**
   * get �������ڣ�(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  protected ClientUISortCtl getCardBodySortCtl() {
    return m_cardBodySortCtl;
  }

  /**
   * ����󴥷��� �������ڣ�(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  public void afterSortEvent(boolean iscard, boolean ishead, String key) {
    if (ishead) {
      setM_alListData((ArrayList<GeneralBillVO>) getListHeadSortCtl().getRelaSortData(0));
    } else {
      if (iscard) {
        if (getM_voBill() != null) {
          GeneralBillItemVO[] itemvos = (GeneralBillItemVO[]) getCardBodySortCtl()
              .getRelaSortDataAsArray(0);
          getM_voBill().setChildrenVO(itemvos);
          if (getM_iMode() != BillMode.New
              && getM_iMode() != BillMode.QuickNew
              && getM_iMode() != BillMode.Update
              && getM_alListData() != null
              && getM_alListData().size() > getM_iLastSelListHeadRow()) {
            if (getM_voBill().getHeaderVO().getCgeneralhid() != null
                && getM_voBill()
                    .getHeaderVO()
                    .getCgeneralhid()
                    .equals(
                        ((GeneralBillVO) getM_alListData()
                            .get(
                                getM_iLastSelListHeadRow()))
                            .getHeaderVO()
                            .getCgeneralhid()))
              try {
                ((GeneralBillVO) getM_alListData().get(
                    getM_iLastSelListHeadRow()))
                    .setChildrenVO((GeneralBillItemVO[]) ObjectUtils
                        .serializableClone(itemvos));
              } catch (Exception e) {
                nc.vo.scm.pub.SCMEnv.error(e);
              }
          }
        }
        if (m_alLocatorData != null && m_alLocatorData.size() > 0)
          m_alLocatorData = (ArrayList) getCardBodySortCtl()
              .getRelaSortData(1);
        if (m_alSerialData != null && m_alSerialData.size() > 0)
          m_alSerialData = (ArrayList) getCardBodySortCtl()
              .getRelaSortData(2);
        if (m_alLocatorDataBackup != null
            && m_alLocatorDataBackup.size() > 0)
          m_alLocatorDataBackup = (ArrayList) getCardBodySortCtl()
              .getRelaSortData(3);
        if (m_alSerialDataBackup != null
            && m_alSerialDataBackup.size() > 0)
          m_alSerialDataBackup = (ArrayList) getCardBodySortCtl()
              .getRelaSortData(4);
      } else {
        if (getM_alListData() != null && getM_alListData().size() > 0) {
          GeneralBillItemVO[] itemvos = (GeneralBillItemVO[]) getListBodySortCtl()
              .getRelaSortDataAsArray(0);
          ((GeneralBillVO) getM_alListData().get(
              getM_iLastSelListHeadRow())).setChildrenVO(itemvos);
          if (getM_voBill() != null
              && getM_voBill().getHeaderVO().getCgeneralhid() != null
              && getM_voBill()
                  .getHeaderVO()
                  .getCgeneralhid()
                  .equals(
                      ((GeneralBillVO) getM_alListData()
                          .get(
                              getM_iLastSelListHeadRow()))
                          .getHeaderVO()
                          .getCgeneralhid())) {
            try {
              getM_voBill().setChildrenVO(
                  (GeneralBillItemVO[]) ObjectUtils
                      .serializableClone(itemvos));
            } catch (Exception e) {
              nc.vo.scm.pub.SCMEnv.error(e);
            }
          }
        }

        if (m_alLocatorData != null && m_alLocatorData.size() > 0)
          m_alLocatorData = (ArrayList) getListBodySortCtl()
              .getRelaSortData(1);
        if (m_alSerialData != null && m_alSerialData.size() > 0)
          m_alSerialData = (ArrayList) getListBodySortCtl()
              .getRelaSortData(2);
        if (m_alLocatorDataBackup != null
            && m_alLocatorDataBackup.size() > 0)
          m_alLocatorDataBackup = (ArrayList) getListBodySortCtl()
              .getRelaSortData(3);
        if (m_alSerialDataBackup != null
            && m_alSerialDataBackup.size() > 0)
          m_alSerialDataBackup = (ArrayList) getListBodySortCtl()
              .getRelaSortData(4);
      }
    }
    m_sLastKey = key;
  }

  /**
   * ����ǰ������ �������ڣ�(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  public void beforeSortEvent(boolean iscard, boolean ishead, String key) {

    clearOrientColor();
    // ����Ǳ�ͷ����
    if (ishead) {
      SCMEnv.out("��ͷ����");/*-=notranslate=-*/
      if (getM_alListData() == null || getM_alListData().size() <= 0) {
        // ˵��û������ı�Ҫ
        return;
      }
      getListHeadSortCtl().addRelaSortData(getM_alListData());

    } else {
      SCMEnv.out("��������");/*-=notranslate=-*/

      if (iscard) {
        if (getM_voBill() != null)
          getCardBodySortCtl().addRelaSortData(
              getM_voBill().getItemVOs());
        if (m_alLocatorData != null && m_alLocatorData.size() > 0)
          getCardBodySortCtl().addRelaSortData(m_alLocatorData);
        if (m_alSerialData != null && m_alSerialData.size() > 0)
          getCardBodySortCtl().addRelaSortData(m_alSerialData);
        if (m_alLocatorDataBackup != null
            && m_alLocatorDataBackup.size() > 0)
          getCardBodySortCtl().addRelaSortData(m_alLocatorDataBackup);
        if (m_alSerialDataBackup != null
            && m_alSerialDataBackup.size() > 0)
          getCardBodySortCtl().addRelaSortData(m_alSerialDataBackup);
      } else {
        if (getM_alListData() != null && getM_alListData().size() > 0)
          getListBodySortCtl().addRelaSortData(
              ((GeneralBillVO) getM_alListData().get(
                  getM_iLastSelListHeadRow())).getItemVOs());
        if (m_alLocatorData != null && m_alLocatorData.size() > 0)
          getListBodySortCtl().addRelaSortData(m_alLocatorData);
        if (m_alSerialData != null && m_alSerialData.size() > 0)
          getListBodySortCtl().addRelaSortData(m_alSerialData);
        if (m_alLocatorDataBackup != null
            && m_alLocatorDataBackup.size() > 0)
          getListBodySortCtl().addRelaSortData(m_alLocatorDataBackup);
        if (m_alSerialDataBackup != null
            && m_alSerialDataBackup.size() > 0)
          getListBodySortCtl().addRelaSortData(m_alSerialDataBackup);
      }
    }
  }

  /**
   * �б��ͷ����󴥷�,��ǰ�б仯 �������ڣ�(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  public void currentRowChange(int newrow) {

    if (newrow >= 0) {
      if (getM_iLastSelListHeadRow() != newrow) {
        setLastHeadRow(newrow);
        selectListBill(getM_iLastSelListHeadRow()); // ����
        setButtonStatus(true);
      }
    } else {
      if (getM_iLastSelListHeadRow() < 0
          || getM_iLastSelListHeadRow() >= getBillListPanel()
              .getHeadBillModel().getRowCount())
        setM_iLastSelListHeadRow(0);
      selectListBill(getM_iLastSelListHeadRow()); // ����
      setButtonStatus(true);
    }
  }

  /**
   * ��������:�˳�ϵͳ
   */
  public boolean onClosing() {
    // ���ڱ༭����ʱ�˳���ʾ
    boolean bret = true;
    if (getM_iMode() != BillMode.Browse) {

      int iret = MessageDialog.showYesNoCancelDlg(this, null,
          nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
              "UCH001")/* @res "�Ƿ񱣴����޸ĵ����ݣ�" */);
      if (iret == MessageDialog.ID_YES) {
        try {
          boolean isok = onSave();
          if (!isok) {

            bret = false;
          }
        } catch (Exception e) {

          bret = false;
        }
      } else if (iret == MessageDialog.ID_NO) {
        bret = true;
      } else
        bret = false;
    }
    getEditCtrl().clearData();
    return bret;
  }

  /*
   * (non-Javadoc)
   * 
   * @see nc.ui.pub.linkoperate.ILinkQuery#doQueryAction(nc.ui.pub.linkoperate.ILinkQueryData)
   */
  public void doQueryAction(ILinkQueryData querydata) {
    if(querydata instanceof OutDetailDlg.QueryData){
      String swhere = querydata.getBillID();
      ConditionVO[] convos = getPowerCons(querydata.getPkOrg(), querydata.getBillType());
   // addied by liuzy 2010-9-7 ����03:22:24 ԭ����ѯ�ǿ��ǵ�Ȩ�ޣ�����û�п��ǵ�������
      getQryDlgHelp().setLinkQryConds(convos);     
      // modified by liuzy 2010-9-7 ����03:23:15 ԭ����ѯ�ǿ��ǵ�Ȩ�ޣ�����û�п��ǵ�������
//      if(convos!=null)
//        swhere += " and " + convos[0].getWhereSQL(convos);
      try {
        ArrayList listvo = getQryDlgHelp().queryData(swhere);
        setDataOnList(listvo, true);
      } catch (BusinessException e) {
        SCMEnv.out(e.getMessage());
        nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
            .getInstance().getStrByID("SCMCOMMON",
                "UPPSCMCommon-000270")/* @res "��ʾ" */,
            nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
                "UPP4008bill-000062")/* @res "û�з��ϲ�ѯ�����ĵ��ݣ�" */);
      }
      
    }else{
      queryForLinkOper(querydata.getPkOrg(), querydata.getBillType(),
          querydata.getBillID());
    }
  }

  public ConditionVO[] getPowerCons(String pk_corp, String billtype) {
    QueryConditionDlgForBill qrydlg = new QueryConditionDlgForBill(this);
    qrydlg.setTempletID(pk_corp, getFunctionNode(),
        getEnvironment().getUserID(), null);
    String[] refcodes = null;
    if (BillTypeConst.m_allocationOut.equals(billtype)
        || BillTypeConst.m_allocationIn.equals(billtype))
      refcodes = nc.ui.ic.pub.tools.GenMethod
          .getDataPowerFieldFromDlg(qrydlg, false, new String[] {
              "head.cothercorpid", "head.coutcorpid",
              "body.creceieveid", "head.cothercalbodyid",
              "head.cotherwhid", "head.coutcalbodyid" });
    else
      refcodes = nc.ui.ic.pub.tools.GenMethod
          .getDataPowerFieldFromDlg(qrydlg, false, null);

    qrydlg.setCorpRefs("head.pk_corp", refcodes);
    ConditionVO[] convos = null;
    if (getClientEnvironment().getCorporation().getPrimaryKey().equals(
        pk_corp)) {
      convos = ICCommonBusi.getDataPowerConsFromDlg(qrydlg,
          getFunctionNode(), pk_corp, getEnvironment()
              .getUserID(), refcodes);
      // ����繫˾����ҵ��Ա����
      convos = nc.ui.ic.pub.tools.GenMethod.procMultCorpDeptBizDP(
          convos, billtype, pk_corp);
    }
    return convos;
  }

  /**
   * (non-Javadoc)
   * 
   * @see nc.ui.pub.linkoperate.ILinkQuery#doQueryAction(nc.ui.pub.linkoperate.ILinkQueryData)
   */
  public void queryForLinkOper(String PkOrg, String billtype, String billid) {
    GeneralBillVO voBill = null;
    HashMap<String,Object> retHM = null;
    ArrayList<GeneralBillVO> alListData = null;
    try {
      retHM = getBillVOForLinkOper(PkOrg, billtype, billid);
      if(null == retHM)
        return;
      alListData = (ArrayList<GeneralBillVO>)retHM.get(IICParaConst.LinkQryBillPara);
      if (alListData != null && alListData.size() > 0) {
        this.setM_alListData(alListData);
        this.checkFirstBillNotNullInList(alListData);
      }
    }
    catch (Exception e) {
      // ��־�쳣
      nc.vo.scm.pub.SCMEnv.out(e);
      showErrorMessage(e.getMessage());
    }
    
    // ��ʾ���鵥��ǰ�Ĵ���
    processBeforeShowLinkBill((String)retHM.get(IICParaConst.LinkBillCorpPara));

      // ��ʼ������
    showLinkBills(alListData);
      
    //�����б�ģʽ�µ� ��һ��ѡ��  qinchao  2009-04-29
    getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);
  }
  
  protected HashMap<String,Object> getBillVOForLinkOper(
		  String PkOrg, String billtype, String billid) throws Exception {
	  return GeneralBillHelper.getBillVOByLinkOpen(PkOrg, billtype, billid,
	          getEnvironment().getUserID(), getFunctionNode(), getClientEnvironment()
	              .getCorporation().getPrimaryKey());
  }
  
  protected void checkFirstBillNotNullInList(ArrayList<GeneralBillVO> alListData) {
      if (alListData.get(0) == null) {
          nc.ui.pub.beans.MessageDialog.showHintDlg(this,
              nc.ui.ml.NCLangRes.getInstance().getStrByID(
                  "SCMCOMMON", "UPPSCMCommon-000270")/*
                                     * @res "��ʾ"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
                  "4008bill", "UPP4008bill-000062")/*
                                     * @res
                                     * "û�з��ϲ�ѯ�����ĵ��ݣ�"
                                     */);
          return;
       }
  }
  
  /**
   * ��ʼ���������
   * @param alListData
   */
  protected void showLinkBills(ArrayList<GeneralBillVO> alListData) {
      setM_alListData(new ArrayList<GeneralBillVO>());
      getM_alListData().add((GeneralBillVO) alListData.get(0));

      ArrayList alListDataNew = new ArrayList();
      alListDataNew.add((GeneralBillVO) alListData.get(0));
      setDataOnList(alListDataNew, true);
  }
  
  /**
   * ��ʾ���鵥��ǰ�Ĵ���
   * @param cbillpkcorp
   */
  protected void processBeforeShowLinkBill(String cbillpkcorp) {
      if (!getClientEnvironment().getCorporation().getPrimaryKey()
          .equals(cbillpkcorp)) {
        setButtons(new ButtonObject[] {});
        // �޸��ˣ������� �޸����ڣ�2007-9-24����10:49:59 �޸�ԭ������ʱ���͵��ݹ�˾��filter�������
        filterRef(cbillpkcorp);

        // �޸��ˣ������� �޸����ڣ�2007-9-25����10:17:35 �޸�ԭ�򣺹��˲ֿ����
        nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
            IItemKey.WAREHOUSE);
        // ����ⵥ�Ĳֿ�����У������˵�ֱ�˲֣����ڿ繫˾�ģ�ֱ�˲�ҲҪ��ʾ��
        RefFilter.filtWh(bi, cbillpkcorp, null);
        
        //�޸��ˣ������� �޸�ʱ�䣺2008-12-29 ����03:31:59 �޸�ԭ������ʱ���ݵ��ݹ�˾����ҵ�����͵�����
        bi = getBillCardPanel().getHeadItem("cbiztype");
        if (null != bi && null != bi.getComponent() && bi.getComponent() instanceof UIRefPane){
          UIRefPane ref = (UIRefPane) bi.getComponent();
          if (null != ref.getRefModel()){
            if (ref.getRefModel().getGroupCode().equals(cbillpkcorp)){
              ref.getRefModel().setWherePart(" pk_corp='@@@@' " );
            }else{
              ref.getRefModel().setWherePart("(pk_corp='" + cbillpkcorp + "' or pk_corp='@@@@') " );
            }   
          }
        }
        
        bi = getBillCardPanel().getHeadItem("cbiztypename");
        if (null != bi && null != bi.getComponent() && bi.getComponent() instanceof UIRefPane){
          UIRefPane ref = (UIRefPane) bi.getComponent();
          if (null != ref.getRefModel()){
            if (ref.getRefModel().getGroupCode().equals(cbillpkcorp)){
              ref.getRefModel().setWherePart(" pk_corp='@@@@' " );
            }else{
              ref.getRefModel().setWherePart("(pk_corp='" + cbillpkcorp + "' or pk_corp='@@@@') " );
            } 
          }
            
        }
      }else{
        setButtons();
      }
  }

  /**
   * UI��������-����
   * 
   * @author leijun 2006-5-24
   */
  public void doAddAction(ILinkAddData adddata) {
    if (adddata == null)
      return;
    GeneralBillVO[] billvos = getBillVOs(adddata);
    if (billvos == null || billvos.length <= 0)
      return;

    try {
      // v5 lj ֧�ֶ��ŵ��ݲ�������
      if (billvos.length == 1) {
        setRefBillsFlag(false);
        setBillRefResultVO(billvos[0].getHeaderVO().getCbiztypeid(),
            billvos);
      } else {
        setRefBillsFlag(true);
        setBillRefMultiVOs(billvos[0].getHeaderVO().getCbiztypeid(),
            billvos);
      }
      // end v5
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
    }
  }

  /**
   * UI��������-����--��ȡԴ����
   * 
   * @author leijun 2006-5-24
   */
  protected GeneralBillVO[] getBillVOs(ILinkAddData adddata) {
    if (adddata == null)
      return null;
    String billtype = adddata.getSourceBillType();
    AggregatedValueObject[] srcvos = null;

    try {
      if (ScmConst.SO_Order.equals(billtype.trim())) {
        nc.itf.scm.so.so001.ISaleOrderQuery soquery = (nc.itf.scm.so.so001.ISaleOrderQuery) NCLocator
            .getInstance().lookup(
                nc.itf.scm.so.so001.ISaleOrderQuery.class
                    .getName());
        AggregatedValueObject srcvo = soquery
            .querySourceBillVOForLinkAdd(adddata.getSourceBillID(),
                billtype, ClientEnvironment.getInstance()
                    .getCorporation().getPrimaryKey(), null);
        if (srcvo != null) {
          srcvos = (AggregatedValueObject[]) Array.newInstance(srcvo
              .getClass(), 1);
          srcvos[0] = srcvo;
        }
      } else if (ScmConst.SO_Invoice.equals(billtype.trim())) {
        nc.itf.scm.so.so002.ISaleinvoiceQuery soquery = (nc.itf.scm.so.so002.ISaleinvoiceQuery) NCLocator
            .getInstance().lookup(
                nc.itf.scm.so.so002.ISaleinvoiceQuery.class
                    .getName());
        AggregatedValueObject srcvo = soquery.queryBillDataByID(adddata
            .getSourceBillID());
        if (srcvo != null) {
          srcvos = (AggregatedValueObject[]) Array.newInstance(srcvo
              .getClass(), 1);
          srcvos[0] = srcvo;
        }

      } else if (ScmConst.PO_Order.equals(billtype.trim())) {
        nc.itf.po.outer.IQueryForIc qrypo = (nc.itf.po.outer.IQueryForIc) NCLocator
            .getInstance().lookup(
                nc.itf.po.outer.IQueryForIc.class.getName());
        srcvos = qrypo.querySourceBillVOForLinkAdd(adddata
            .getSourcePkOrg(), adddata.getSourceBillID(), billtype,
            ClientEnvironment.getInstance().getCorporation()
                .getPrimaryKey(), null);

      } else if (ScmConst.PO_Arrive.equals(billtype.trim())) {
        /**add by qinchao 2009-02-20  
         **�޸Ĳ��֣� else if{}��   
         **���ã�    ����PO�ɹ�������  ***/
        
        boolean m_bQcEnabled = false; 
          //��������ģ������
        nc.itf.uap.sf.ICreateCorpQueryService tt = (nc.itf.uap.sf.ICreateCorpQueryService) NCLocator
              .getInstance().lookup(nc.itf.uap.sf.ICreateCorpQueryService.class.getName());
         
        m_bQcEnabled = tt.isEnabled(getCorpPrimaryKey(), nc.vo.pub.ProductCode.PROD_QC);
        
        nc.itf.po.outer.IQueryForIc qrypo = (nc.itf.po.outer.IQueryForIc) NCLocator
        .getInstance().lookup(
            nc.itf.po.outer.IQueryForIc.class.getName());
          srcvos = qrypo.queryArriveBillVOForLinkAdd(adddata
        .getSourcePkOrg(), adddata.getSourceBillID(), billtype,
        ClientEnvironment.getInstance().getCorporation()
            .getPrimaryKey(), null,m_bQcEnabled);

          } else if (BillTypeConst.TO_ORDER3.equals(billtype.trim())
          || BillTypeConst.TO_ORDER2.equals(billtype.trim())
          || BillTypeConst.TO_ORDER1.equals(billtype.trim())
          || BillTypeConst.TO_ORDER4.equals(billtype.trim())
          || "5X".equals(billtype.trim())) {
        BillVO billvo = (BillVO) LongTimeTask
            .callMethod(3, "nc.ui.to.outer.QryOrder4ICLinkAdd",
                null, "querySourceBillVOForLinkAdd",
                new Class[] { String.class, String.class,
                    String.class, String.class,
                    Object.class }, new Object[] {
                    adddata.getSourceBillID(),
                    billtype,
                    adddata.getSourcePkOrg(),
                    ClientEnvironment.getInstance()
                        .getCorporation()
                        .getPrimaryKey(), null });
        if (billvo == null)
          return null;
        srcvos = new BillVO[] { billvo };

      } else {
        Object obj = adddata.getUserObject();
        if(obj!=null && obj instanceof AggregatedValueObject[])
          srcvos = (AggregatedValueObject[])obj;
        else
          return null;
      }
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this, null, e);
      return null;
    }

    if (srcvos == null || srcvos.length <= 0) {
      showErrorMessage(ResBase.getLinkAddQueryErr());
      return null;
    }

    // ����Դ���ݷֵ�
    srcvos = GenMethod.splitSourceVOs(srcvos, adddata.getSourceBillType()
        .trim(), getBillTypeCode());

    GeneralBillVO[] retbillvos = null;
    try {
      retbillvos = (GeneralBillVO[]) PfChangeBO_Client
          .pfChangeBillToBillArray(srcvos, adddata
              .getSourceBillType().trim(), getBillTypeCode());
      retbillvos = GenMethod.splitGeneralBillVOs(retbillvos,
          getBillTypeCode(), getBillListPanel().getHeadBillModel()
              .getFormulaParse());
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
    }

    return retbillvos;
  }

  /**
   * UI��������-����
   * 
   * @author cch 2006-5-9-11:04:16
   */
  public void doApproveAction(ILinkApproveData approvedata) {
    queryForLinkOper(approvedata.getPkOrg(), approvedata.getBillType(),
        approvedata.getBillID());
  }

  /**
   * UI��������-ά��
   * 
   * @author leijun 2006-5-24
   */
  public void doMaintainAction(ILinkMaintainData maintaindata) {
    queryForLinkOper(getClientEnvironment().getCorporation()
        .getPrimaryKey(), getBillTypeCode(), maintaindata.getBillID());
//    if(getM_voBill()!=null && getM_voBill().getHeaderVO().getFbillflag()!=null && getM_voBill().getHeaderVO().getFbillflag().intValue()==BillStatus.IFREE)
      // modified by liuzy 2009-1-19 ����06:10:46 ΪʲôҪ�ѱ༭״̬���أ�����������״̬�ģ��û��Լ���
      //�޸İ�ť���Ϳ����ˣ�����������SES��ѯ�����ĵ��ݾ��Ǳ༭״̬�ġ����в������SES�򿪽ڵ�ʱΪʲôҪ��ILinkType.LINK_TYPE_MAINTAIN��ʽ�򿪣�
      //��ʱע���������������ôֻ����SES�޸Ĵ򿪵��ݵķ�ʽ��
//      onButtonClicked(getButtonManager().getButton(
//          ICButtonConst.BTN_BILL_EDIT));
  }

  /**
   * showBtnSwitch ���Ͻ���淶
   * 
   * @author leijun 2006-5-24
   */
  public void showBtnSwitch() {
    if (getM_iCurPanel() == BillMode.Card)
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setName(
          nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
              "UCH022")/* @res "�б���ʾ" */);
    else
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setName(
          nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
              "UCH021")/* @res "��Ƭ��ʾ" */);
    updateButton(getButtonManager().getButton(ICButtonConst.BTN_SWITCH));

  }

  protected void refreshSelBill(int selrow) {

    GeneralBillVO voBill = (GeneralBillVO) getM_alListData().get(selrow);

    QryConditionVO voCond = new QryConditionVO(" head.cgeneralhid='"
        + voBill.getHeaderVO().getCgeneralhid() + "'");

    try {
      ArrayList alListData = (ArrayList) GeneralBillHelper.queryBills(
          getBillType(), voCond);
      getM_alListData().set(selrow, (GeneralBillVO)alListData.get(0));
      setDataOnList(getM_alListData(), true);
      selectListBill(selrow);

    } catch (Exception e) {
      showErrorMessage(e.getMessage());

    }

  }

  /**
   * �����ߣ����˾� ���ܣ��Ƿ��ת����
   * 
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2001-11-24 12:15:42) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  protected boolean isBrwLendBiztype() {
    return false;

  }

  /**
   * �����ߣ������� ���ܣ����ݵ������Ϳ��ư�ť
   * 
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2007-04-05 17:00:00) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */

  protected void ctrlBillTypeButtons(boolean willDo) {

    if (willDo) {

    }

  }

  /**
   * �����ߣ������� ���ܣ����ݵ������Ϳ��ư�ť
   * 
   * 
   * ������ ���أ� ���⣺ ���ڣ�(2007-04-06 10:26:00) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */

  public String getBusiTypeItemKey() {

    if (getM_voBill() != null) {
      if (getM_voBill().getBizTypeid() != null) {

        return getM_voBill().getBizTypeid();

      }
      return "";
    } else {
      return "";
    }

  }

  /**
   * �����ˣ������� �������ڣ�2007-7-11����01:41:03 ����ԭ�򣺷�����֤ʧ��ʱ��֤�������ʾ����
   * 
   * ICGenVO[] gVOs = new ICGenVO[2]; gVOs[0] = new ICGenVO();
   * gVOs[0].setAttributeValue("ssss", "������"); gVOs[1] = new ICGenVO();
   * gVOs[1].setAttributeValue("dddd", "������"); barcodeValidateDialog = null;
   * getbarcodeValidateDialog().setVOData(gVOs);
   * getbarcodeValidateDialog().showModal();
   */

  public BarcodeValidateDialog getbarcodeValidateDialog() {

    if (barcodeValidateDialog == null) {

      barcodeValidateDialog = new BarcodeValidateDialog(this);

    }

    return barcodeValidateDialog;

  }

  /**
   * �����ˣ������� �������ڣ�2007-7-11����01:41:03 ����ԭ�򣺷�����֤ʧ��ʱ��֤�������ʾ����
   * 
   */

  public PackCheckBusDialog getpackCheckBusDialog() {
    if (packCheckBusDialog == null) {
      packCheckBusDialog = new PackCheckBusDialog(this);
    }
    return packCheckBusDialog;

  }

  /*
   * m_voBill��������
   */
  protected void setM_voBill(GeneralBillVO m_voBill) {
    this.m_voBill = m_voBill;
    // ��Ҫ��ʼ�����չ���, Ϊ���ȡ�������������. 20021225
    //filterRef(getEnvironment().getCorpID());
  }

  /*
   * m_voBill�Ļ�ȡ�������Ժ�һ��ʹ�÷��������ʳ�Աm_voBill
   */
  public GeneralBillVO getM_voBill() {
//    if(null == m_voBill)
//      return m_voBill;
//    System.out
//    .println("***********************LIUZY-BEGIN**************************");
//    System.out
//    .println("����VO��������=" + m_voBill.getItemCount());
//    printCallInfo("getM_voBill");
    return m_voBill;
  }

  /*
   * m_iLastSelListHeadRow��������
   */
  protected void setM_iLastSelListHeadRow(int m_iLastSelListHeadRow) {
    this.m_iLastSelListHeadRow = m_iLastSelListHeadRow;
  }

  /*
   * m_iLastSelListHeadRow�Ļ�ȡ�������Ժ�һ��ʹ�÷��������ʳ�Աm_iLastSelListHeadRow
   */
  public int getM_iLastSelListHeadRow() {
    return m_iLastSelListHeadRow;
  }

  /*
   * m_iCurPanel��������
   */
  protected void setM_iCurPanel(int m_iCurPanel) {
    this.m_iCurPanel = m_iCurPanel;
  }

  /*
   * m_iCurPanel�Ļ�ȡ�������Ժ�һ��ʹ�÷��������ʳ�Աm_iCurPanel
   */
  public int getM_iCurPanel() {
    return m_iCurPanel;
  }

  /*
   * m_iMode��������
   */
  public void setM_iMode(int m_iMode) {
    this.m_iMode = m_iMode;
  }

  /*
   * m_iMode�Ļ�ȡ�������Ժ�һ��ʹ�÷��������ʳ�Աm_iMode
   */
  public int getM_iMode() {
    return m_iMode;
  }

  /*
   * m_alListData��������
   */
  protected void setM_alListData(ArrayList<GeneralBillVO> m_alListData) {
    this.m_alListData = m_alListData;
  }

  /*
   * m_alListData�Ļ�ȡ�������Ժ�һ��ʹ�÷��������ʳ�Աm_alListData
   */
  public ArrayList<GeneralBillVO> getM_alListData() {
    return m_alListData;
  }

  /*
   * ��m_alListData��ȡ����VO,���ù���m_alListData�����Ƿ�Ϊ�գ����Ƿ�����Խ��
   */
  protected GeneralBillVO getVOFromListDataAt(int row) {
    if (getM_alListData() == null)
      return null;
    if (row >= 0 && row < getM_alListData().size())
      return (GeneralBillVO) getM_alListData().get(row);
    return null;

  }

  public boolean getbBadBarcodeSave() {
    return m_bBadBarcodeSave;
  }

  protected UIMenuItem getAddNewRowNoItem() {
    return miAddNewRowNo;
  }
  
  protected UIMenuItem getMiLineCardEdit() {
    return miLineCardEdit;
  }
//  protected UIMenuItem getMiLineBatchEdit() {
//    return miLineBatchEdit;
//  }

  public EditCtrl getEditCtrl() {
    if (null == m_editCtrl) {
      m_editCtrl = new EditCtrl(this);
    }
    return m_editCtrl;
  }

  protected void setM_alLocatorData(ArrayList locatorData) {
    m_alLocatorData = locatorData;
  }

  protected void setM_alSerialData(ArrayList serialData) {
    m_alSerialData = serialData;
  }
  
  protected void setM_alLocatorDataBackup(ArrayList locatorDataBackup) {
    m_alLocatorDataBackup = locatorDataBackup;
  }

  protected void setM_alSerialDataBackup(ArrayList serialDataBackup) {
    m_alSerialDataBackup = serialDataBackup;
  }

  public nc.ui.ic.pub.barcode.UIBarCodeTextFieldNew getM_utfBarCode() {
    return m_utfBarCode;
  }

  public void setM_utfBarCode(
      nc.ui.ic.pub.barcode.UIBarCodeTextFieldNew barCode) {
    m_utfBarCode = barCode;
  }

  public boolean isM_bOnhandShowHidden() {
    return m_bOnhandShowHidden;
  }

  public void setM_bOnhandShowHidden(boolean onhandShowHidden) {
    m_bOnhandShowHidden = onhandShowHidden;
  }

  public ToftLayoutManager getM_layoutManager() {
    return m_layoutManager;
  }

  public void setM_layoutManager(ToftLayoutManager manager) {
    m_layoutManager = manager;
  }

  public ArrayList getM_alSerialData() {
    return m_alSerialData;
  }

  public void initM_alSerialData() {
    m_alSerialData = new ArrayList();
  }

  public ArrayList getM_alLocatorData() {
    return m_alLocatorData;
  }

  public void initM_alLocatorData() {
    m_alLocatorData = new ArrayList();
  }

  public void initM_alLocatorData(Integer isize) {
    m_alLocatorData = new ArrayList(isize);
  }

  public nc.ui.scm.extend.IFuncExtend getM_funcExtend() {
    return m_funcExtend;
  }
  
  public void setSortEnable(boolean isenable) {
    if(!isenable){
      getBillCardPanel().getBillTable().setSortEnabled(false);
      getBillCardPanel().getBillTable().removeSortListener();
    }else{
      getBillCardPanel().getBillTable().setSortEnabled(true);
      getBillCardPanel().getBillTable().addSortListener();
    }
  }
  
  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-8 19:47:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.ui.pub.bill.BillData
   * @param oldBillData
   *            nc.ui.pub.bill.BillData
   */
  protected BillData changeBillDataByUserDef(DefVO[] defHead,
      DefVO[] defBody, BillData oldBillData) {
    try {
        String codeColumn;
        String nameColumn;
      // �����Զ��������
      if (defHead != null) {
        oldBillData.updateItemByDef(defHead, "vuserdef", true);
        for (int i = 1; i <= 20; i++) {
                        
          nc.ui.pub.bill.BillItem item = oldBillData
              .getHeadItem("vuserdef" + i);
          
         
          if (item != null
              && item.getDataType() == nc.ui.pub.bill.BillItem.USERDEF) {
            ((nc.ui.pub.beans.UIRefPane) item.getComponent())
                .setAutoCheck(true);
          }
        }
      }

      // ����
      if ((defBody != null)) {
        oldBillData.updateItemByDef(defBody, "vuserdef", false);
        if (ScmConst.m_assetOut.equals(getBillType()) || ScmConst.m_assetIn.equals(getBillType())){
          for (int i = 1; i <= 20; i++) {
            nc.ui.pub.bill.BillItem item = oldBillData
                .getBodyItem("vuserdef" + i);
            nc.ui.pub.bill.BillItem itemPK = oldBillData
            .getBodyItem("pk_defdoc" + i);
            
            codeColumn = "vuserdef" + i;
            String formular = null;
            String [] ss = null;
            if(defBody[i-1] != null && defBody[i-1].getDefdef()!= null && defBody[i-1].getDefdef().getPk_bdinfo() != null){
                formular= nc.ui.ic.pub.tools.GenMethod.getCodeFieldEditFormular(defBody[i-1].getDefdef().getPk_bdinfo(),itemPK.getKey(),null,codeColumn);
                ss = formular.split(";");  
            }
                   
            if (item != null
                && item.getDataType() == nc.ui.pub.bill.BillItem.USERDEF) {
              ((nc.ui.pub.beans.UIRefPane) item.getComponent())
                  .setAutoCheck(true);
            }          
            if (null != itemPK && ss!= null){
                itemPK.setEditFormula(ss);
                itemPK.setLoadFormula(ss);
            }
            
            if(item!=null && item.getComponent()!=null)
              ((nc.ui.pub.beans.UIRefPane) item.getComponent()).setEditable(item.isEdit());
          }
        }
      }
    } catch (Exception e) {

        nc.vo.scm.pub.SCMEnv.error(e);
    }

    return oldBillData;
  }

  /**
   * �����ߣ������� ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2001-11-8 19:47:29) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.ui.pub.bill.BillData
   * @param oldBillData
   *            nc.ui.pub.bill.BillData
   */
  protected BillListData changeBillListDataByUserDef(DefVO[] defHead,
      DefVO[] defBody, BillListData oldBillData) {
    try {
      if (defHead != null)

        oldBillData.updateItemByDef(defHead, "vuserdef", true);

      if (defBody != null){
        oldBillData.updateItemByDef(defBody, "vuserdef", false);
        if (ScmConst.m_assetOut.equals(getBillType()) || ScmConst.m_assetIn.equals(getBillType())){
          // added by lirr 2009-11-17����02:12:13 �ʲ����ʲ�����Ҫִ���Զ����ʽ
          String codeColumn ;
          for (int i = 1; i <= 20; i++) {
              nc.ui.pub.bill.BillItem item = oldBillData
                  .getBodyItem("vuserdef" + i);
              nc.ui.pub.bill.BillItem itemPK = oldBillData
              .getBodyItem("pk_defdoc" + i);
              
              codeColumn = "vuserdef" + i;
              String formular = null;
              String [] ss = null;
              if(defBody[i-1] != null && defBody[i-1].getDefdef()!= null && defBody[i-1].getDefdef().getPk_bdinfo() != null){
                  formular= nc.ui.ic.pub.tools.GenMethod.getCodeFieldEditFormular(defBody[i-1].getDefdef().getPk_bdinfo(),itemPK.getKey(),null,codeColumn);
                  ss = formular.split(";");  
              }
                     
              if (item != null
                  && item.getDataType() == nc.ui.pub.bill.BillItem.USERDEF) {
                ((nc.ui.pub.beans.UIRefPane) item.getComponent())
                    .setAutoCheck(true);
              }          
              if (null != itemPK && ss!= null){
                  itemPK.setEditFormula(ss);
                  itemPK.setLoadFormula(ss);
              }
              
              if(item!=null && item.getComponent()!=null)
                ((nc.ui.pub.beans.UIRefPane) item.getComponent()).setEditable(item.isEdit());
            }
        }
      }

      return oldBillData;
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
    return oldBillData;
  }

  /**
   * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-6-30 17:57:26) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.vo.bd.def.DefVO[]
   * @param pk_corp
   *            java.lang.String
   * @param isHead
   *            boolean
   */
  public DefVO[] getDefHeadVO() {
    if (m_defHead == null) {
      try {
        m_defHead = DefSetTool.getDefHead(getCorpPrimaryKey(), ICConst.BILLTYPE_IC);
      } catch (Exception e) {
        nc.vo.scm.pub.SCMEnv.error(e);
      }
    }
    return m_defHead;
  }


  /**
   * ?user> ���ܣ� ������ ���أ� ���⣺ ���ڣ�(2004-6-30 17:57:26) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * @return nc.vo.bd.def.DefVO[]
   * @param pk_corp
   *            java.lang.String
   * @param isHead
   *            boolean
   */
  public DefVO[] getDefBodyVO() {

    if (m_defBody == null) {
      try {
        m_defBody = DefSetTool.getDefBody(getCorpPrimaryKey(), ICConst.BILLTYPE_IC);
      } catch (Exception e) {
        nc.vo.scm.pub.SCMEnv.error(e);
      }
    }
    return m_defBody;
  }
  
  public QueryDlgHelp getQryDlgHelp() {
    if(m_qryDlgHelp==null)
      m_qryDlgHelp = new QueryDlgHelp(this);
    return m_qryDlgHelp;
  }
  
  public boolean isBQuery() {
    return m_bQuery;
  }
  
  public boolean isLineCardEdit() {
    return isLineCardEdit;
  }

  public void setLineCardEdit(boolean isLineCardEdit) {
    this.isLineCardEdit = isLineCardEdit;
  }
  
  /**
   * �����ߣ����˾� ���ܣ����Ƶ�ǰ���� ������ ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32) �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  public String afterAuditFrushData(GeneralBillVO billvo,boolean isSaveAfter) {
    if(billvo==null)
      return null;
    String sBillStatus = billvo.getHeaderVO().getFbillflag()+"";
    if(isSaveAfter){
      GeneralBillUICtl.fillHeadVOFromHead(billvo.getHeaderVO(), new String[]{IItemKey.fbillflag});
      sBillStatus = billvo.getHeaderVO().getFbillflag()+"";
      if (sBillStatus.equals(BillStatus.FREE) || sBillStatus.equals(BillStatus.DELETED)) 
        return sBillStatus;
    }
    //showTime(lTime, "ǩ��");
    sBillStatus = GeneralBillUICtl.updateDataAfterAudit(
        this, billvo);

    if (sBillStatus != null && !sBillStatus.equals(BillStatus.FREE)
        && !sBillStatus.equals(BillStatus.DELETED)) {
      freshAfterSignedOK(sBillStatus);
    } 
    return sBillStatus;
    
  }
 /**
  * �����ˣ������� ����ʱ�䣺2008-7-16 ����03:43:33 ����ԭ�� ������Ϣ��ʾ������漰��ĳЩ�е����ݣ��б���ɫ��Ϊ��ɫ
  * @param e
  */ 
/*  public void setRowColorWhenException(ICException e){
    // ������ɫ
    SetColor.SetTableColor(
        getBillCardPanel().getBillModel(),
        getBillCardPanel().getBillTable(),
        getBillCardPanel(),
        e.getErrorRowNums(),
        m_cNormalColor,
        e.getExceptionColor(m_bRowLocateErrorColor),
        m_bExchangeColor,
        m_bLocateErrorColor,
        e.getHint(),m_bRowLocateErrorColor);
    SetColor.setErrRowColor(getBillCardPanel().getBillTable(), e.getErrorRowNums());
  }*/
  /**
   * �����ˣ������� ����ʱ�䣺2008-7-16 ����03:43:33 ����ԭ�� ������Ϣ��ʾ������漰��ĳЩ�е����ݣ��б���ɫ��Ϊ��ɫ
   * @param e
   */  
/*  public void setRowColorWhenException(ICBusinessException e){
    // ������ɫ
    SetColor.SetTableColor(
        getBillCardPanel().getBillModel(),
        getBillCardPanel().getBillTable(),
        getBillCardPanel(),
        e.getErrorRowNums(),
        m_cNormalColor,
        e.getExceptionColor(m_bRowLocateErrorColor),
        m_bExchangeColor,
        m_bLocateErrorColor,
        e.getMessage(),m_bRowLocateErrorColor);
    
    ArrayList<Integer> errRowNums = new ArrayList<Integer>();
    Boolean isRight = false;
    ArrayList alerr = e.getErrorRowNums();
    if (alerr != null && alerr.size() > 0) {
      int iLen = alerr.size();
      for (int i = 0; i < iLen; i++) {
        if (alerr.get(i) instanceof ArrayList
            && (((ArrayList) alerr.get(i)).get(0)) instanceof Integer){
          errRowNums.add(((Integer)(((ArrayList) alerr.get(i)).get(0)))-1);
          isRight = true;
        }
      }
    }
    if (isRight)
      SetColor.setErrRowColor(getBillCardPanel().getBillTable(), errRowNums);
    else
      SetColor.setErrRowColor(getBillCardPanel().getBillTable(), e.getErrorRowNums());
    
  }*/
  /**
   * �����ˣ������� ����ʱ�䣺2008-7-16 ����03:43:33 ����ԭ�� �ָ����е������б���ɫΪĬ��ɫ
   * @param e
   */ 
/*  public void reSetRowColorWhenNOException(){
    // ������ɫ
    ArrayList alRowNum = new ArrayList();
    SetColor.SetTableColor(
        getBillCardPanel().getBillModel(),
        getBillCardPanel().getBillTable(),
        getBillCardPanel(),
        alRowNum,
        m_cNormalColor,
        m_cNormalColor,
        m_bExchangeColor,
        m_bLocateErrorColor,
        "",m_bRowLocateErrorColor);
    SetColor.resetColor(getBillCardPanel().getBillTable());
}*/

public ArrayList getM_alRefBillsBak() {
  return m_alRefBillsBak;
}

public void setM_alRefBillsBak(ArrayList refBillsBak) {
  m_alRefBillsBak = refBillsBak;
}
  

/**
 * �����ˣ������� ����ʱ�䣺2008-10-30 ����08:16:52 ����ԭ�������������ʵ������ʵ��������������༭�ֿ⡣ 
 *
 */
public void freshWHEditable(){
  boolean isEditable = true;
  if (0 < getBillCardPanel().getRowCount()){
    for(int i = 0 ;i< getBillCardPanel().getRowCount();i++)
      if (null != getBillCardPanel().getBodyValueAt(i, "noutnum") || null != getBillCardPanel().getBodyValueAt(i, "ninnum"))
        isEditable = false;
  }
  if (BillMode.Update == getM_iMode() && getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE) != null
      && null != getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE).getValue())
    getBillCardPanel().getHeadItem(IItemKey.WAREHOUSE).setEnabled(isEditable);
  /*if (getBillCardPanel().getHeadItem(m_sWasteWhItemKey) != null)
    getBillCardPanel().getHeadItem(m_sWasteWhItemKey).setEnabled(isEditable);*/
}
//added by lirr 2009-02-25
public boolean isCheckAssetInv() {
    return m_bCheckAssetInv;
  }


//  public void printCallInfo(String methodName) {
//
//    String fullClassName = methodName;
//
//    StackTraceElement stack[] = (new Throwable()).getStackTrace();
//
//    int ix = 0;
//    while (ix < stack.length) {
//      StackTraceElement frame = stack[ix];
//      String cname = frame.getMethodName();
//      if (cname.equals(fullClassName)) {
//        break;
//      }
//      ix++;
//    }
//
//    while (ix < stack.length) {
//      StackTraceElement frame = stack[ix];
//      String cname = frame.getMethodName();
//      if (!cname.equals(fullClassName)) {
//        System.out.println("���ô˷���������������������" + frame.getClassName() + ":"
//            + frame.getMethodName());
//      }
//      ix++;
//    }
//
//    System.out
//        .println("***********************LIUZY-END**************************");
//  }

}
