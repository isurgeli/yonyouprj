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

  public static final int CANNOTSIGN = -1; // 不能签字

  public static final int NOTSIGNED = 0; // 未签字//单据签字状态

  public static final int SIGNED = 1; // 已签字
  public static final String qualitylevelName=null;//质量等级

  private Environment m_environment;

  // 单据类型编码
  protected String m_sTitle = null; // 标题

  protected final int m_iInitRowCount = 1; // 新增初始状态下的行数

  protected String m_sCurBillOID = null; // 当前的单据id.

  private int m_iMode = BillMode.Browse; // 当前的单据编辑状态.

  private boolean m_bOnhandShowHidden = false; // 是否显示存量参照

  public String m_sMultiMode = MultiCardMode.CARD_PURE;// 多卡片状态

  private ToftLayoutManager m_layoutManager = new ToftLayoutManager(this);// 布局管理器

  // protected AfterEditCtrl m_afterEditCtrl = new AfterEditCtrl(this);

  protected EditCtrl m_editCtrl = new EditCtrl(this);

  private int m_iCurPanel = BillMode.Card; // 当前显示的panel.

  // 列表形式处理
  protected int m_iBillQty = 0; // 当前列表形式下的单据数量

  private ArrayList<GeneralBillVO> m_alListData = null; // 列表数据
  //修改人：刘家清 修改时间：2008-7-17 下午07:09:51 修改原因：
  private ArrayList<GeneralBillVO> m_alRefBillsBak = null; // 列表数据

  private int m_iLastSelListHeadRow = -1; // 最后选中的列表表头行。

  protected int m_iCurDispBillNum = -1;

  // 表单形式下当前显示的单据序号，当列表---〉表单切换时，未改选其它单据，则无需重设表单数据，以提高效率。
  // vo名，用于读取修改数据时

  // 表单数据VO
  private GeneralBillVO m_voBill = null;

  // 保存当前新增或修改的单据的货位分配数据...要不要保存列表形式下所有的数据？
  private ArrayList m_alLocatorData = null;

  protected ArrayList m_alLocatorDataBackup = null;

  // 数据备份，在新增时保存 m_alLocatorData，取消时恢复m_alLocatorData.
  // 保存当前新增或修改的单据的序列号分配数据...要不要保存列表形式下所有的数据？
  private ArrayList m_alSerialData = null;

  protected ArrayList m_alSerialDataBackup = null;

  // 序列号对话框
  protected nc.ui.ic.pub.sn.SerialAllocationDlg m_dlgSerialAllocation = null;

  // 货位对话框
  protected nc.ui.ic.pub.locator.SpaceAllocationDlg m_dlgSpaceAllocation = null;

  // 卡片
  protected nc.ui.pub.bill.BillCardPanel ivjBillCardPanel = null;

  // 列表
  protected nc.ui.pub.bill.BillListPanel ivjBillListPanel = null;

  // 自由项参照
  protected FreeItemRefPane ivjFreeItemRefPane = null;

  // 批次参照
  protected nc.ui.ic.pub.lot.LotNumbRefPane ivjLotNumbRefPane = null;

  // 车次参照
  protected nc.ui.ic.pub.tools.VehicleRefPanel ivjVehicleRefPane = null;

  // 行复制VO
  protected GeneralBillItemVO[] m_voaBillItem = null;

  // 状态条
  protected javax.swing.JTextField m_tfHintBar = null;

  // 对应入库单的参照//对应单据参照
  protected nc.ui.ic.pub.corbillref.ICCorBillRefPane m_aICCorBillRef = null;

  protected InvOnHandDialog m_iohdDlg = null;

  // 辅计量及换算率。
  protected InvMeasRate m_voInvMeas = new InvMeasRate();

  boolean m_isWhInvRef = false;

  // 货位参照
  private LocatorRefPane ivjLocatorRefPane = null;

  // 查询对话框
  //protected QueryConditionDlgForBill ivjQueryConditionDlg = null; to QueryDlgHelp

  // added by zhx 单据中是否使用公式的标志;
  protected boolean m_bIsByFormula = true;

  // 单据号是否允许手输
  protected boolean m_bIsEditBillCode = false;

  // 是否期初单据,缺省不是。
  protected boolean m_bIsInitBill = false;

  // 是否需要单据参照录入菜单。
  protected boolean m_bNeedBillRef = true;

  // 是否用系统toftpanel缺省的错误显示对话框
  protected boolean m_bUserDefaultErrDlg = true;

  // 初始化打印接口
  protected PrintDataInterface m_dataSource = null;

  // 外设输入控制类。
  protected nc.ui.ic.pub.device.DevInputCtrl m_dictrl = null;

  /** 定位对话框 */
  protected nc.ui.ic.pub.orient.OrientDialog m_dlgOrient = null;
  
  protected HashMap<String,WhVO> hm_whid_vo = new HashMap<String, WhVO>();

  // 新增时间
//  protected nc.vo.pub.lang.UFDateTime m_dTime = null;

  public Hashtable m_htBItemEditFlag = null;

  // add by zhx
  // 初始化时,保存单据模板中定义的表头,表体数据项是否可编辑, 用于业务规则定义的数据项是否可编辑的判断.
  public Hashtable m_htHItemEditFlag = null;

  // 小数精度定义--->
  // 数量小数位 2
  // 辅计量数量小数位 2
  // 换算率 2
  // 存货成本单价小数位 2
  protected ScaleValue m_ScaleValue = new ScaleValue();

  protected ScaleKey m_ScaleKey = new ScaleKey();
  
  protected String m_sIC026 = null;
  
  protected String m_sIC040 = null;

  // /////////////////////////////////////////
  // 公式解析需要的相关全局变量 by hanwei 2003-06-26
  private InvoInfoBYFormula m_InvoInfoBYFormula;

  // ///////////////////////////////////////

  protected nc.ui.pub.print.PrintEntry m_print = null;

  // 项目参照
  protected nc.ui.pub.beans.UIRefPane m_refJob = null;

  // 项目阶段参照
  protected nc.ui.pub.beans.UIRefPane m_refJobPhase = null;

  protected nc.ui.bd.b39.PhaseRefModel m_refJobPhaseModel = null;

  // 模块启用日期
  protected String m_sStartDate = null;

  // 废品库
  /* 标志该单据是否为参照录入单据（默认为自制单据）* */
  // boolean bIsRefBill = false;
  /* 保存来源单据参照生成的单据VO* */
  protected GeneralBillVO m_voBillRefInput = null;

  // 最近一次的查询条件，可用于刷新。现用于列表形式下的打印。
  //protected QryConditionVO m_voLastQryCond = null; to QueryDlgHelp

  // 支持二次开发的功能扩展
  private nc.ui.scm.extend.IFuncExtend m_funcExtend = null;

  // 当换算率改变时，非固定换算率，默认是辅数量不变主数量变。否则相反。
  protected boolean m_bAstCalMain = false;

  // 如果是换算率触发afterAstNumEdit触发afterNumEdit,那么在afterNumEdit中就不需要再去触发afterAstNumEdit
  protected boolean m_isNeedNumEdit = true;

  // 是否曾经查询过的标识
  //protected boolean m_bEverQry = false; to QueryDlgHelp

  // 是进行查询还是进行刷新（为了兼容以前的版本，特增加该变量用来标识操作的类型）
  protected boolean m_bQuery = true;

  private String[] m_sItemField = null;// 表体公式

  private ClientUISortCtl m_listHeadSortCtl;// 列表表头排序控制

  private ClientUISortCtl m_listBodySortCtl;// 列表表体排序控制

  private ClientUISortCtl m_cardBodySortCtl;// 卡片表体排序控制

  // 用户名、密码校验UI
  protected nc.ui.scm.pub.AccreditLoginDialog m_AccreditLoginDialog;

  // 是否添加条码解析 add by hanwei 2004-03-01
  protected boolean m_bAddBarCodeField = true;

  // 条码编辑界面控制类
  protected nc.ui.ic.pub.bill.BarcodeCtrl m_BarcodeCtrl = null;

  // 条码不完整是否保存
  protected boolean m_bBadBarcodeSave = false;

  // 条码是否保存
  // protected boolean m_bBarcodeSave = false;

  // 条码数量按负数增加，如：采购入库的退库
  protected boolean m_bFixBarcodeNegative = false;

  // 单据公式容器 hanwei 2003-07-23
  BillFormulaContainer m_billFormulaContain;

  // 是否有导入操作 hanwei 2003-12-17
  private boolean m_bIsImportData = false;

  // added by zhx 条码编辑界面
  protected BarCodeDlg m_dlgBarCodeEdit = null;

  // 指定货位
  protected LocSelDlg m_dlgLocSel = null;

  protected GeneralBillUICtl m_GenBillUICtl;

  // 条码编辑框的颜色列：每X+1行的颜色需要设置
  protected int m_iBarcodeUIColorRow = 20;

  // 文件打开对话框
  private nc.ui.ic.pub.tools.FileChooserImpBarcode m_InFileDialog = null;

  // 表明定位信息
  protected boolean m_isLocated = false;

  // 翻页功能
  protected PageCtrlBtn m_pageBtn;

  // 单据状态
  protected String m_sBillStatus = nc.vo.ic.pub.bill.BillStatus.FREE;

  protected String m_sColorRow = null;

  // 成套件对话框
  protected SetPartDlg m_SetpartDlg = null;

  // 排序主键
  String m_sLastKey = null;

  //表格中的颜色
  protected Color m_cNormalColor = null;
  //交错行显示开关
  protected boolean m_bExchangeColor = false;
  //故障定位显示开关
  protected boolean m_bLocateErrorColor = true;
  //行故障定位显示开关
  protected boolean m_bRowLocateErrorColor = true;
  
  // 时间显示
  protected nc.vo.ic.pub.bill.Timer m_timer = new nc.vo.ic.pub.bill.Timer();

  private nc.ui.ic.pub.barcode.UIBarCodeTextFieldNew m_utfBarCode = null;

  protected static final UFDouble UFDNEGATIVE = new UFDouble(-1.00); // 负数-1.00

  protected static final nc.vo.pub.lang.UFDouble UFDZERO = new nc.vo.pub.lang.UFDouble(
      0.0);

  protected QueryOnHandInfoPanel m_pnlQueryOnHand;// 现存量Panel

  protected OnHandRefCtrl m_onHandRefDeal;// 现存量参照的代码容器

  protected UIPanel m_pnlBarCode;

  protected BarcodeValidateDialog barcodeValidateDialog;

  protected PackCheckBusDialog packCheckBusDialog;

  protected UIMenuItem miAddNewRowNo = new UIMenuItem(nc.ui.ml.NCLangRes
      .getInstance().getStrByID("SCMCommon", "UPP4008bill-000551")/*
                                     * @res
                                     * "重排行号"
                                     */);
  
  protected UIMenuItem miLineCardEdit = new UIMenuItem(nc.ui.ml.NCLangRes
      .getInstance().getStrByID("common", "SCMCOMMONIC55YB002")/*
                                     * @res
                                     * "卡片编辑"
                                     */);
  
//  protected UIMenuItem miLineBatchEdit = new UIMenuItem(nc.ui.ml.NCLangRes
//      .getInstance().getStrByID("common", "SCMCOMMONIC55YB003")/*
//                                     * @res
//                                     * "批改"
//                                     */);

  protected int m_Menu_AddNewRowNO_Index = -1;

  // 按钮管理对象
  private IButtonManager m_buttonManager;
  
  protected QueryDlgHelp m_qryDlgHelp;
  
  private boolean isLineCardEdit ;
//资产类存货在非资产仓出入时是否可以不输入序列号
  protected boolean m_bCheckAssetInv = false;
  
  // 为解决拔网线引起的重复保存问题而引入的属性
  private String tempHeaderID = null;
  
//二次开发扩展
  private InvokeEventProxy pluginproxy;
  
  public InvokeEventProxy getPluginProxy() {
    if(this.pluginproxy==null)
      this.pluginproxy = new InvokeEventProxy(ICConst.MODULE_IC,getBillTypeCode(),new ICPluginUI(this));
    return this.pluginproxy;
  }
  
//行计数器
  private long rowCount=1;
  
  protected void setUIVORowPosWhenNewRow(int row,GeneralBillItemVO bodyvo){
    if(row<0)
      return ;
    this.rowCount++;
    String rowpos=String.valueOf(this.rowCount);
    getBillCardPanel().getBillModel().setValueAt(rowpos, row, GeneralBillItemVO.RowPos);
    if(bodyvo==null){
      throw new RuntimeException(NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000000")/*显示行与模型数据行不一致，请重新操作！*/);
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
   * ClientUI 构造子注解。
   */
  public GeneralBillClientUI() {
    super();
  }

  /**
   * GeneralBillClientUI 构造子注解。 add by lizuy 2007-12-18 根据节点好初始化单据模板
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
   * 创建者：王乃军 功能： 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：单据编辑事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
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

    // 行，选中表头字段时为-1
    int row = e.getRow();
    // 字段itemkey
    String sItemKey = e.getKey();
    // 字段，位置 0: head 1:table
    int pos = e.getPos();

    if (pos == nc.ui.pub.bill.BillItem.BODY && row < 0 || sItemKey == null
        || sItemKey.length() == 0)
      return;

    // ljun
    // 处理了毛重皮重
    getEditCtrl().afterEdit(e);

    if (sItemKey.equals("vbillcode")) {
      // 单据号

      getEditCtrl().afterBillCodeEdit(e);
    } else if (sItemKey.equals("cdispatcherid")) {
      // 收发类别
      getEditCtrl().afterDispatcherEdit(e);
    } else if (sItemKey.equals("cinventoryid")) {//加工品
      getEditCtrl().afterCinventoryidEdit(e);
    } else if (sItemKey.equals(IItemKey.WAREHOUSE))
      // 仓库
      afterWhEdit(e);
    else if (sItemKey.equals(IItemKey.CALBODY))
      // 库存组织
      afterCalbodyEdit(e);

    else if (sItemKey.equals("cwhsmanagerid")) {
      getEditCtrl().afterWhsmanagerEdit(e);
    } else if (sItemKey.equals("cdptid")) {
      getEditCtrl().afterCDptIDEdit(e);
    } else if (sItemKey.equals("cbizid")) {
      // 业务员
      getEditCtrl().afterCBizidEdit(e);
    } else if (sItemKey.equals("cproviderid")) {
      // 供应商
      getEditCtrl().afterProviderEdit(e);
    } else if (sItemKey.equals("ccustomerid")) {
      getEditCtrl().afterCustomerEdit(e);

    } else if (sItemKey.equals("cbiztype")) {
      getEditCtrl().afterBiztypeEdit(e);
    } else if (sItemKey.equals("cdilivertypeid")) {
      getEditCtrl().afterDilivertypeEdit(e);
    } else if (sItemKey.equals("vdiliveraddress")) {
      // 发运地址

      getEditCtrl().afterVdiliveraddress(e);

    } else if (sItemKey.equals("vreceiveaddress")) {
      // 发运地址

      getEditCtrl().afterVreceiveaddress(e);

    }
    
    
//      2008.01.23 cy 雨润要求表头单据日期修改后，表体业务日期与表头单据日期一致 begin
    else if (sItemKey.equals("dbilldate"))
    {
      //单据日期
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("dbilldate").getComponent()).getRefCode();
      
      for (int i=0;i<getBillCardPanel().getBillModel().getRowCount();i++)
      {
        getBillCardPanel().getBillModel().setValueAt(sName, i, "dbizdate");
        
        if (BillModel.NORMAL == getBillCardPanel().getBillModel().getRowState(i))
          getBillCardPanel().getBillModel().setRowState(i, BillModel.MODIFICATION);
      }
    }
      //2008.01.23 cy 雨润要求表头单据日期修改后，表体业务日期与表头单据日期一致 end

    else if (sItemKey.equals("cotherwhid")) {
      getEditCtrl().afterOtherWHEdit(e);
    }
    // 存货编码改变
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

    } else // 对应单据改变
    if (sItemKey.equals(IItemKey.CORRESCODE))
      getEditCtrl().afterCorBillEdit(e);
    else if (sItemKey.equals("vspacename"))
      afterSpaceEdit(e);

    // 生产日期
    else if (sItemKey.equals("scrq")) {
      getEditCtrl().afterScrqEdit(e);
    }
    // 失效日期
    else if (sItemKey.equals("dvalidate")) {
      getEditCtrl().afterDvalidateEdit(e);
    } else if (sItemKey.equals("nmny")) {
      getEditCtrl().afterNmnyEdit(e);

    } else if (sItemKey.equals("vbatchcode")) {
      getEditCtrl().afterLotEdit(e);
      // 修改人：刘家清 修改日期：2007-11-20上午09:52:42 修改原因：恢复参照选择初始数据。
      getLotNumbRefPane().setClicked(false);
      getLotNumbRefPane().getLotNumbDlg().setSelVO(null);
    }
    // 项目
    else if (sItemKey.equals("cprojectname")) {
      getEditCtrl().afterProjectNameEdit(e);

    }
    // 项目阶段
    else if (sItemKey.equals("cprojectphasename")) {
      getEditCtrl().afterProjectPhaseNameEdit(e);
    } // 表体供应商
    else if (sItemKey.equals("vvendorname")) {
      getEditCtrl().afterVendorNameEdit(e);

    } else // 成本对象
    if (sItemKey.equals("ccostobjectname")) {
      getEditCtrl().afterCostObjectNameEdit(e);
    } else // 收货单位
    if (sItemKey.equals("creceieveid")) {
      getEditCtrl().afterReceieveEdit(e);
    }

    /** 换算率编辑后 */
    else if (sItemKey.equals("hsl"))
      getEditCtrl().afterHslEdit(e);

    // 备注
    else if (sItemKey.equals("vnotebody"))
      getM_voBill().setItemValue(e.getRow(), "vnotebody", e.getValue());
    // 赠品
    else if (sItemKey.equals("flargess")) {
      getEditCtrl().afterFlargessEdit(e);
    }
    // 在途
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
        && sItemKey.startsWith("vuserdef")) {// 自定义项处理zhy
      getEditCtrl().afterVuserDefEdit(e);
    } else if (sItemKey.startsWith(IItemKey.VBCUSER)) {// 处理批次号档案相关的自定义项
      getEditCtrl().afterVbcuserEdit(e);
    } else if (sItemKey.equals(IItemKey.CQUALITYLEVELNAME)) {
      getEditCtrl().afterQualityLevelNameEdit(e);
    } else { // default set id col name:缺省的设置，编辑了参照置id列的值
      getEditCtrl().afterElseDefaultEdit(e);
    }

    // 清除对应行货位、序列号数据
    // zhy2005-08-25由于在入库单表体也增加了供应商，因此在此处清除货位序列号时需要判断下列条件
     //修改人：刘家清 修改时间：2008-6-16 上午10:49:05 修改原因：修改数量、辅数量时不清除序列号
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
      // 同步化VO
      getM_voBill().setItemValue(row, IItemKey.CROWNO,
          getBillCardPanel().getBodyValueAt(row, IItemKey.CROWNO));

    }
    GeneralBillUICtl.afterBcloseorditemEdit(getBillCardPanel(), e,
        getM_voBill());
    // 抽象类方法
    afterBillEdit(e);
    // getBillCardPanel().restoreFocusComponent();
    
    //  二次开发扩展
    getPluginProxy().afterEdit(e);
  }

  /**
   * 创建者：王乃军 功能：自由项改变事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void afterFreeItemEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterFreeItemEdit(e);

  }

  /**
   * 创建者：王乃军 功能：仓库改变事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void afterWhEdit(nc.ui.pub.bill.BillEditEvent e) {
    // 仓库
    getEditCtrl().afterWhEdit(e, null, null);

  }

  protected void setLastHeadRow(int row) {
    setM_iLastSelListHeadRow(row);
  }

  /**
   * 创建者：王乃军 功能：单据体、列表上表编辑事件处理 参数：e 单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void bodyRowChange(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().bodyRowChange(e);
    
    //二次开发扩展
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
   * 创建者：王乃军 功能：清除指定行的数据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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

    // 删除界面数据
    // 删除界面数据
    String sColKey = null;
    int iColCount = items.length;

    for (int col = 0; col < iColCount; col++) {

      sColKey = items[col].getKey();
      if (!IItemKey.NAME_HEADID.equals(sColKey)
          && !IItemKey.NAME_BODYID.equals(sColKey)
          && !"crowno".equals(sColKey))
        bmBill.setValueAt(null, row, col);
    }
    // 同步vo
    getM_voBill().clearItem(row);

  }

  /**
   * 创建者：王乃军 功能：清除指定行、指定列的数据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
    // 删除界面数据
    nc.ui.pub.bill.BillItem biaBody[] = bmBill.getBodyItems();
    Hashtable<String, String> htBodyItem = new Hashtable<String, String>();
    // 把列放到hash中
    for (int col = 0; col < biaBody.length; col++)
      htBodyItem.put(biaBody[col].getKey(), "OK");

    for (int col = 0; col < saColKey.length; col++)
      if (saColKey[col] != null
          && getBillCardPanel().getBodyItem(saColKey[col]) != null) {
        try {
          // SCMEnv.out("clear "+saColKey[col]);
          try {
            // 如果有，清除之
            if (htBodyItem.containsKey(saColKey[col]))
              bmBill.setValueAt(null, row, saColKey[col]);
          } catch (Exception e3) {
          }

          // 同步vo
          getM_voBill().setItemValue(row, saColKey[col], null);
          // 如果是自由项的话需同时清vfree1--->vfree10
          if (saColKey[col].equals("vfree0")) {
            for (int i = 1; i <= 10; i++) {
              // 如果有，清除之
              if (htBodyItem.contains(saColKey[col]))
                bmBill.setValueAt(null, row, "vfree" + i);
              // 同步vo
              getM_voBill().setItemValue(row, "vfree" + i, null);
            }
          }

        } catch (Exception e) {
          // nc.vo.scm.pub.SCMEnv.error(e);
          SCMEnv
              .out("nc.ui.ic.pub.bill.GeneralBillClientUI.clearRowData(int, String [])：set value ERR.--->"
                  + saColKey[col]);/*-=notranslate=-*/
        } finally {

        }
      }

  }

  /**
   * 创建者：王乃军 功能：滤去表单形式下的空行，查询 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志： 2001/10/29,wnj,提高效率
   * 
   * 
   * 
   */
  public void filterNullLine() {

    // 存货列值暂存
    Object oTempValue = null;
    // 表体model
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    // 存货列号，效率高一些。
    int iInvCol = bmBill.getBodyColByKey(IItemKey.INVID);

    // 必须有存货列
    if (bmBill != null && iInvCol >= 0 && iInvCol < bmBill.getColumnCount()) {
      // 行数
      int iRowCount = getBillCardPanel().getRowCount();
      // 从后向前删
      for (int line = iRowCount - 1; line >= 0; line--) {
        // 存货未填
        oTempValue = bmBill.getValueAt(line, IItemKey.INVID);
        if (oTempValue == null
            || oTempValue.toString().trim().length() == 0)
          // 删行
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
   * 返回 BillCardPanel1 特性值。
   * 
   * @return nc.ui.pub.bill.BillCardPanel 1.
   *         新建一个billcardpanel,得到templetData并传递给BillData 2.
   *         将自由项和批次号参照控件置入BillData（替换原Componet），项目参照也是 3. 过滤表体供应商 4.
   *         设置表体m_sTitle和自定义项 5.
   *         将BillData设置入billCardPanel,重新设置行号，返回billCardPanel
   */
  /* 警告：此方法将重新生成。 */
  protected nc.ui.pub.bill.BillCardPanel getBillCardPanel() {
    if (ivjBillCardPanel == null) {
      try {
        ivjBillCardPanel = new nc.ui.pub.bill.BillCardPanel();
        ivjBillCardPanel.setName("BillCardPanel");

        // modified by liuzy 2007-12-18 根据节点号初始化单据模版
        BillData bd = null;
        if (null == getFrame())
          bd = new BillData(getDefaultTemplet());
        else
          bd = new BillData(getDefaultTemplet());

        if (bd == null) {
          SCMEnv.out("--> billdata null.");
          return ivjBillCardPanel;
        }
        // 自由项参照
        if (bd.getBodyItem("vfree0") != null)
          getFreeItemRefPane().setMaxLength(
              bd.getBodyItem("vfree0").getLength());

        // zhy 2005-04-13 表头批次参照
        if (bd.getHeadItem("vbatchcode") != null) {
          bd.getHeadItem("vbatchcode").setComponent(
              getLotNumbRefPane());
        }
        // zhy 2005-04-13 表头自由项
        if (bd.getHeadItem("vfree0") != null) {
          bd.getHeadItem("vfree0").setComponent(getFreeItemRefPane());
        }

        // 非期初单加批次号参照
        // zhy2006-04-18 由于加了批次号档案,故不对期初单据特殊处理
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

        // 将自由项参照加入单据模板表体
        if (bd.getBodyItem("vfree0") != null)
          bd.getBodyItem("vfree0").setComponent(getFreeItemRefPane()); // 将自由项参照加入单据模板表体
        // 项目参照
        m_refJob = new nc.ui.pub.beans.UIRefPane();
        m_refJob.setRefNodeName("项目管理档案");/*-=notranslate=-*/
        m_refJob.getRefModel().setPk_corp(getEnvironment().getCorpID());

        if (bd.getBodyItem("cprojectname") != null)
          bd.getBodyItem("cprojectname").setComponent(m_refJob);

        // 项目阶段参照
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

        // 出库单表体供应商
        if (bd.getBodyItem("vvendorname") != null)
          RefFilter.filtProvider(bd.getBodyItem("vvendorname"),
              getEnvironment().getCorpID(), null);
        if (bd.getBodyItem("vspacename") != null)
          bd.getBodyItem("vspacename").setComponent(
              getLocatorRefPane()); //
      

        // 修改自定义项
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
        

        // 质量等级参照设置
        BillItem item = null;
        try {
          UIRefPane uiRefPanel = null;
          item = bd.getBodyItem("cqualitylevelname");
          if (item != null){
            uiRefPanel = new UIRefPane();
            uiRefPanel.setRefNodeName("自定义参照");/*-=notranslate=-*/
            uiRefPanel.setIsCustomDefined(true);
            uiRefPanel.setRefModel((AbstractRefModel) Class
                .forName("nc.ui.qc.pub.CheckstateDef")
                .newInstance());
            uiRefPanel.getRefModel().setPk_corp(
                getEnvironment().getCorpID());
//          addied by liuzy 2008-05-09
            // 质量登记参照没有重载AbstractRefModel.setPk_corp方法
            // 导致参照出其它公司定义的质量等级
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
            ref.setRefNodeName("自定义参照");/*-=notranslate=-*/
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
            ref.setRefNodeName("自定义参照");/*-=notranslate=-*/
            ref.setIsCustomDefined(true);
            ref.setRefModel(refModel);
            itemhead.setComponent(ref);
          }
          // 修改人：刘家清 修改日期：2007-11-27上午11:11:40
          // 修改原因：处理表头的计量器皿，如果注册到单据模板里，如果没有nc.ui.mm.pub.pub1010.JlcRefModel类，就会加载不了模板，而库存不依赖于生产制造。
          /*
           * BillItem itemhead =
           * ivjBillCardPanel.getHeadItem("cmeaswarename");
           * if(itemhead!=null){ AbstractRefModel refModel =
           * (AbstractRefModel)
           * Class.forName("nc.ui.mm.pub.pub1010.JlcRefModel").newInstance();
           * UIRefPane ref = new UIRefPane();
           * ref.setRefNodeName("自定义参照");
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
        // 将原单据模板的表体行隐藏!
        ivjBillCardPanel.getBodyPanel().setRowNOShow(
            nc.ui.ic.pub.bill.Setup.bShowBillRowNo);
        
        //设置模版批修改属性
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
   * 返回 BillListPanel1 特性值。
   * 
   * @return nc.ui.pub.bill.BillListPanel 1.新疆billListPanel，加载模板数据
   *         2.得到模板数据BillListData，修改自定义项，重新设入 3.设置表头选择模式，隐藏以listid开头的表头列
   *         4.显示所有表体列，返回billListPanel
   */
  /* 警告：此方法将重新生成。 */
  protected nc.ui.pub.bill.BillListPanel getBillListPanel() {
    if (ivjBillListPanel == null) {
      try {
        ivjBillListPanel = new nc.ui.pub.bill.BillListPanel();
        ivjBillListPanel.setName("BillListPanel");
        // user code begin {1}
        // ivjBillListPanel.loadTemplet(m_sTempletID);
        // 加载列表模版
        /*ivjBillListPanel.loadTemplet(getBillType(), null,
            getEnvironment().getUserID(), getEnvironment()
                .getCorpID());
*/
        //BillListData bd = ivjBillListPanel.getBillListData();
        BillListData bd = new BillListData(getDefaultTemplet());

        // 修改自定义项
        bd = changeBillListDataByUserDef(getDefHeadVO(),
            getDefBodyVO(), bd);
        
        bd = BatchCodeDefSetTool.changeBillListDataByBCUserDef(getCorpPrimaryKey(),bd);

        ivjBillListPanel.setListData(bd);

        // 设置小数精度
        // setScaleOfListPanel();

        // 滤掉listid的列
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
   * 返回 FreeItemRefPane1 特性值。
   * 
   * @return nc.ui.ic.pub.freeitem.FreeItemRefPane
   */
  /* 警告：此方法将重新生成。 */
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
   * 返回 LotNumbRefPane1 特性值。
   * 
   * @return nc.ui.ic.pub.lot.LotNumbRefPane
   */
  /* 警告：此方法将重新生成。 */
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
   * 返回 SerialAllocationDlg1 特性值。
   * 
   * @return nc.ui.ic.pub.sn.SerialAllocationDlg
   */
  /* 警告：此方法将重新生成。 */
  protected nc.ui.ic.pub.sn.SerialAllocationDlg getSerialAllocationDlg() {
    if (m_dlgSerialAllocation == null) {
      try {
        m_dlgSerialAllocation = new nc.ui.ic.pub.sn.SerialAllocationDlg(
            this);
        m_dlgSerialAllocation.setName("SerialAllocationDlg");
        // m_dlgSerialAllocation.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        // user code begin {1}
        // m_dlgSerialAllocation.setParent(this);
        // 小数精度设置
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
   * 返回 SpaceAllocationDlg1 特性值。
   * 
   * @return nc.ui.ic.pub.locator.SpaceAllocationDlg
   */
  /* 警告：此方法将重新生成。 */
  protected nc.ui.ic.pub.locator.SpaceAllocationDlg getSpaceAllocationDlg() {
    if (m_dlgSpaceAllocation == null) {
      try {
        m_dlgSpaceAllocation = new nc.ui.ic.pub.locator.SpaceAllocationDlg(
            this);
        m_dlgSpaceAllocation.setName("SpaceAllocationDlg");
        // m_dlgSpaceAllocation.setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);
        // user code begin {1}
        // 小数精度设置
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
   * 子类实现该方法，返回业务界面的标题。
   * 
   * @version (00-6-6 13:33:25)
   * 
   * @return java.lang.String
   */
  public String getTitle() {
    return m_sTitle;
  }

  /**
   * 创建者：王乃军 功能：返回仓库名，只适用于出入库单。特殊单要区分出/入仓库itemkey 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
   * 每当部件抛出异常时被调用
   * 
   * @param exception
   *            java.lang.Throwable
   */
  protected void handleException(java.lang.Throwable exception) {

    /* 除去下列各行的注释，以将未捕捉到的异常打印至 stdout。 */
    SCMEnv.out("--------- 未捕捉到的异常 ---------");/*-=notranslate=-*/
    nc.vo.scm.pub.SCMEnv.error(exception);
  }

  /**
   * 
   * 获得客户端环境对象。
   * <p>
   * 
   * @return 客户端环境对象
   *         <p>
   * @author duy
   * @time 2008-2-22 下午04:00:35
   */
  public Environment getEnvironment() {
    if (m_environment == null) {
      m_environment = new Environment(this);
    }
    return m_environment;
  }

  /**
   * 初始化类。
   */
  /* 警告：此方法将重新生成。 */
  public void initialize() {
    m_timer.start("初始化类开始：");/*-=notranslate=-*/
    try {
      m_timer.showExecuteTime("初始化开始：");/*-=notranslate=-*/
      // initButtons();
      m_timer.showExecuteTime("initButtons：");/*-=notranslate=-*/
      // ydy 04-05-12 简化代码，重用
      initialize(null);
    } catch (java.lang.Throwable ivjExc) {
      handleException(ivjExc);
    }
    // user code begin {2}

  }

  /**
   * 删除列表下的一张单据
   * 
   * @author ljun
   * @since v5 调用时机一： 参照多张单据生成时，卡片界面下保存后自动转入列表界面时，删除保存的单据
   * 
   */
  protected void delBillOfList(int iSel) {
    if (iSel < 0)
      return;
    if (getM_alListData() == null)
      return;
    // 如果删除后m_alListData.size()==0 , 则m_iLastSelListHeadRow为-1
    getM_alListData().remove(iSel);

    // m_iCurDispBillNum在删除后为第一个列表单据
    if (BillMode.Card == getM_iCurPanel())
      m_iCurDispBillNum = -1;
    else
      m_iCurDispBillNum = 0;

    if (getM_alListData().size() == 0)
      setLastHeadRow(-1);
    else
      setLastHeadRow(0);

    m_iBillQty = getM_alListData().size();

    // 刷新界面显示
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
      // 选中列表单据 ，以刷新表体显示
      selectListBill(getM_iLastSelListHeadRow());
    }

  }

  /**
   * 创建者：王乃军 功能：把表单形式下的单据插入到列表，用于新增、关联录入、复制保存后。
   */
  private void insertBillToList(GeneralBillVO voBill) {
    if (voBill == null || voBill.getParentVO() == null
        || voBill.getChildrenVO() == null) {
      SCMEnv.out("Bill is null !");
      return;
    }

    // 当前没有单据
    if (getM_alListData() == null)
      setM_alListData(new ArrayList<GeneralBillVO>());
    // 第一个或最后一个，追加单据
    // if (m_iLastSelListHeadRow < 0 || m_iLastSelListHeadRow == m_iBillQty
    // - 1)
    // ..................注意clone()...........................

    // 无需重算换算率
    voBill.setHaveCalConvRate(true);

    // 通过单据公式容器类执行有关公式解析的方法
    // execFormulaAtBillVO(voBill);
    execHeadFormulaAtVOs(new GeneralBillHeaderVO[] { voBill.getHeaderVO() });

    GeneralBillVO billvo = (GeneralBillVO) voBill.clone();
    // ------------------
    getM_alListData().add(billvo);
    // else //插入
    // m_alListData.add(m_iLastSelListHeadRow + 1, voBill.clone());

    m_iBillQty = getM_alListData().size();

    // 选中的新增的行。
    setLastHeadRow(m_iBillQty - 1);

    // 表单形式下当前显示的单据序号，当列表---〉表单切换时，未改选其它单据，则无需重设表单数据，以提高效率。
    if (BillMode.Card == getM_iCurPanel())
      m_iCurDispBillNum = getM_iLastSelListHeadRow();
    else
      m_iCurDispBillNum = 0;
    // 刷新界面显示
    getBillListPanel().getHeadBillModel().setSortColumn(null);
    getBillListPanel().getHeadBillModel().addLine();
    getBillListPanel().getHeadBillModel().setBodyRowVO(
        billvo.getParentVO(), getM_iLastSelListHeadRow());

    getBillListPanel().getHeadBillModel().setRowState(
        getM_iLastSelListHeadRow(), VOStatus.UNCHANGED);
    // 选中表头行
    // getBillListPanel().getHeadTable().changeSelection(sn, 0, false,
    // false);
    if (getM_iLastSelListHeadRow() > -1
        && getM_iLastSelListHeadRow() < getBillListPanel()
            .getHeadBillModel().getRowCount())
      getBillListPanel().getHeadTable().setRowSelectionInterval(
          getM_iLastSelListHeadRow(), getM_iLastSelListHeadRow());

  }

  /**
   * 创建者：yb 功能：签字 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 
   * 修改日期，修改人，修改原因，注释标志：
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
    UFTime ufdPre1 = new UFTime(System.currentTimeMillis());// 系统当前时间
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
          // LongTimeTask.showHintMsg("单据"+voaBills[i].getHeaderVO().getVbillcode()+"开始签字...");
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

          // add by liuzy 2007-11-02 10:16 压缩数据
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
          stemp = nc.ui.ml.NCLangRes.getInstance().getStrByID("4008busi", "UPP4008busi-000401")/** @res "拆卸单生成的其他入库单以下单据行实际入库的子件数量超过按实际出库套件数量拆分对应的子件数量"*/ + e.getMessage();
        
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
   * 创建者：yb 功能：签字 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 
   * 修改日期，修改人，修改原因，注释标志：
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

//    二次开发扩展
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
      // LongTimeTask.procclongTime(this,"正在刷新界面...",0,
      // 3,this.getClass().getName(),this,"onQuery",
      // null,null);
      // }
      
//    二次开发扩展
      getPluginProxy().afterAction(ICConst.CANCELSIGN.equals(sAction)? 
          nc.vo.scm.plugin.Action.UNAUDIT:nc.vo.scm.plugin.Action.AUDIT, voaBills);
      
    } catch (Exception e) {
      nc.ui.ic.pub.tools.GenMethod.handleException(this, null, e);
    }
    this.updateUI();
  }

  /**
   * 创建者：yb 功能：删除处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
      return NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000001", null, new String[]{vobill.getHeaderVO().getVbillcode()})/*单据[{0}]不是自由状态，不能签字！*/;
    return null;
  }

  /**
   * 创建者：王乃军 功能：签字 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 
   * 修改日期，修改人，修改原因，注释标志： 修改人：刘家清 修改日期：2007-11-14下午03:51:17 修改原因：签字时间由后台处理。
   */
  public GeneralBillVO getAuditVO(GeneralBillVO voAudit,
      UFDateTime sysdatetime) {

    // 设置条码状态未没有被修改
    GeneralBillVO.setBillBCVOStatus(voAudit, nc.vo.pub.VOStatus.UNCHANGED);
    // 支持平台，clone一个，以便于以后的处理，同时防止修改了m_voBill
    GeneralBillHeaderVO voHead = voAudit.getHeaderVO();

    // 签字人
    voHead.setCregister(getEnvironment().getUserID());
    // 可以不是当前登录单位的单据，所以不需要修改单据。
    // voHead.setPk_corp(getEnvironment().getCorpID());
    voHead.setDaccountdate(new nc.vo.pub.lang.UFDate(getEnvironment()
        .getLogDate()));
    // voHead.setAttributeValue("taccounttime", sysdatetime.toString()); //
    // 签字时间//zhy2005-06-15签字时间=登陆日期+系统时间

    // vo可能要传给平台，所以要做成和签字后的单据
    // voHead.setFbillflag(new
    // Integer(nc.vo.ic.pub.bill.BillStatus.SIGNED));
    voHead.setCoperatoridnow(getEnvironment().getUserID()); // 当前操作员2002-04-10.wnj
    voHead.setAttributeValue("clogdatenow", getEnvironment().getLogDate()); // 当前登录日期

    voAudit.setParentVO(voHead);

    // 根据仓库解析获得仓库是否存货核算属性 add by hanwei 2004-4-30
    //getGenBillUICtl().setBillIscalculatedinvcost(voAudit);

    // 平台：需要表体带表头PK
    GeneralBillItemVO voaItem[] = voAudit.getItemVOs();
    int iRowCount = voAudit.getItemCount();
    for (int i = 0; i < iRowCount; i++) {
      // 表头PK
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

    // 帐期、信用信息
    voAudit.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_OTHER;
    voAudit.m_sActionCode = "SIGN";

    return voAudit;

  }

  /**
   * 子类实现该方法，响应按钮事件。
   * 
   * @version (00-6-1 10:32:59)
   * 
   * @param bo
   *            ButtonObject
   */
  public void onButtonClicked(nc.ui.pub.ButtonObject bo) {
    
    try{
//    二次开发扩展
      getPluginProxy().beforeButtonClicked(bo);
      
      getButtonManager().onButtonClicked(bo);
      
      getPluginProxy().afterButtonClicked(bo);
      
    }catch(Exception e){
      nc.ui.ic.pub.tools.GenMethod.handleException(null, null, e);
      return;
    }
    

  }

  /**
   * 创建者：王乃军 功能：新增处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   */
  public void onAdd(boolean bUpataBotton, GeneralBillVO voBill) {

    // 当前是列表形式时，首先切换到表单形式,[v5]如果是参照多张生成，不切换，切换在调用onSwitch时执行
    if (BillMode.List == getM_iCurPanel() && !m_bRefBills)
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_SWITCH));
    // onSwitch();

    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
        "UCH028")/* @res "正在增加" */);
    // 新增
    try {

      if (voBill == null) {
        setM_voBill(new GeneralBillVO());
        getBillCardPanel().updateValue();
        getBillCardPanel().addNew();
        getBillCardPanel().getBillModel().clearBodyData();
      }
      // 设置新增单据的初始数据，如日期，制单人等。
      setNewBillInitData();
      getBillCardPanel().setEnabled(true);
      setM_iMode(BillMode.New);

      if (bUpataBotton && voBill == null)
        setButtonStatus(true);

      // long lTime = System.currentTimeMillis();

      // 保存后清货位数据
      m_alLocatorDataBackup = m_alLocatorData;

      m_alLocatorData = null;
      // 保存后清序列号数据
      m_alSerialDataBackup = m_alSerialData;
      m_alSerialData = null;

      // v5
      if (voBill == null)
        addRowNums(m_iInitRowCount);
      //deleted by lirr  修改原因：增加是否有权限的判断 放在了后面
      /*// 显示表体右键按钮，并可用。
      if (getBillCardPanel().getBodyMenuItems() != null)
        for (int i = 0; i < getBillCardPanel().getBodyMenuItems().length; i++){
            getBillCardPanel().getBodyMenuItems()[i].setEnabled(true);
        }
          */

      // 20050519 dw 在途单右键的行维护功能应封掉 getBillTypeCode() !="40080620"
      if (getBillType() != "40080620") {
        getBillCardPanel().setBodyMenuShow(true);
//         显示表体右键按钮，并可用。
        if (getBillCardPanel().getBodyMenuItems() != null)
          for (int i = 0; i < getBillCardPanel().getBodyMenuItems().length; i++){
            //added by lirr 2009-02-23 修改原因：增加是否有权限的判断 并且 客户化如果上按钮没有权限的话 本级按钮的权限为true
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

      // 设置单据号是否可编辑
      if (getBillCardPanel().getHeadItem("vbillcode") != null)
        getBillCardPanel().getHeadItem("vbillcode").setEnabled(
            m_bIsEditBillCode);
      getBillCardPanel().setTailItem("iprintcount", new Integer(0));

      // 需要初始化参照过滤, 为点击取消后的新增操作. 20021225
      filterRef(getEnvironment().getCorpID());

      // zhx add 如果表体没有行则不设置行号.
      if (voBill == null
          && getBillCardPanel().getBillModel().getRowCount() != 0)
        nc.ui.scm.pub.report.BillRowNo.addNewRowNo(getBillCardPanel(),
            getBillType(), IItemKey.CROWNO);

      // 默认情况下，退库状态不可以用 add by hanwei 2003-10-19 //v5 可能需要修改退库控制lj
      nc.ui.ic.pub.bill.GeneralBillUICtl
          .setSendBackBillState(this, false);

      // 默认不是导入数据 add by hanwei 2003-10-30
      setIsImportData(false);

      // 设置当前的条码框的条码 韩卫 2004-04-05
      if (m_utfBarCode != null)
        m_utfBarCode.setCurBillItem(null);

      // 修改人：刘家清 修改日期：2007-8-31下午04:31:35
      // 修改原因：4F_委外加工发料单来源为A3_备料计划时，修改完后如果新增把加工单位状态设置回去
      if (getBillType().equals(nc.vo.ic.pub.BillTypeConst.m_consignMachiningOut)) {
        /** 置表头可编辑项 */
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
   * 创建者：审核中的核心方法 功能：确认（保存）处理 参数：无 例外： 日期：(2004-4-1 9:23:32)
   * 修改日期，修改人，修改原因，注释标志： 2004-4-1 韩卫
   */
  protected void onAuditKernel(GeneralBillVO voBill) throws Exception {

    // add by liuzy 2007-11-02 10:16 压缩数据
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
   * 创建者：yb 功能：删除处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 修改人：刘家清 修改日期：2007-9-12上午10:16:33 修改原因：
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
                         * @res "单据["
                         */
            + vobill.getHeaderVO().getVbillcode()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000541")/*
                                   * @res
                                   * "]是存货系统审核状态，不能取消签字！"
                                   */;
      else
        return nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000539")/*
                         * @res "单据["
                         */
            + vobill.getHeaderVO().getVbillcode()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000540")/*
                                   * @res
                                   * "]不是签字状态，不能取消签字！"
                                   */;
    }
      
      // 固定资产的相关检查
      if (vobill.getHeaderVO().getBassetcard().booleanValue()) {
          return vobill.getHeaderVO().getVbillcode()
            + nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
                "UPP4008ASSE-000005"); /* @res 已经生成资产卡片，不能取消签字 */
      }

    // 当单据类型是调拨出库单时，并且已经下发到U8零售，则不允许取消签字
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
           * @res "单据号是{0}的单据已经下发到U8零售系统，不能执行该操作！"
           */
        }
      }
    }

    return null;
  }

  /**
   * 创建者：王乃军 功能：签字 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public GeneralBillVO getCancelAuditVO(GeneralBillVO voAudit) {

    GeneralBillHeaderVO voHead = voAudit.getHeaderVO();
    // 签字人
    // voHead.setCregister(getEnvironment().getUserID());
    // 可以不是当前登录单位的单据，所以不需要修改单据。
    // voHead.setPk_corp(getEnvironment().getCorpID());
    // 该日期设置之后会造成库存月结取消签字未扣减数量
    // voHead.setDaccountdate(new
    // nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
    // vo可能要传给平台，所以要做成签字后的单据
    voHead.setFbillflag(new Integer(nc.vo.ic.pub.bill.BillStatus.SIGNED));
    voHead.setCoperatoridnow(getEnvironment().getUserID()); // 当前操作员2002-04-10.wnj
    voHead.setAttributeValue("clogdatenow", getEnvironment().getLogDate()); // 当前登录日期2003-01-05
    voAudit.setParentVO(voHead);

    // 平台：需要表体带表头PK
    GeneralBillItemVO voaItem[] = voAudit.getItemVOs();
    // 表体行数
    int iRowCount = voAudit.getItemCount();

    for (int i = 0; i < iRowCount; i++) {
      // 表头PK
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
   * 设置数据到列表,并执行前20条单据的公式,并将默认选择设置到第一个单据上,设置菜单状态
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
   * 设置数据到列表,并执行前20条单据的公式,并将默认选择设置到第一个单据上,设置菜单状态
   * 
   * @since v5
   * @author ljun
   * @param alData
   *            单据集合
   * @param bQuery
   *            是否查询调用, 是查询调用为true, 不是为false
   */
  public void setDataOnList(ArrayList<GeneralBillVO> alData, boolean bQuery) {

    m_timer.start();
    setAlistDataByFormula(-1, alData);// GeneralBillVO.QRY_FIRST_ITEM_NUM,
    // alData);
    m_timer.showExecuteTime("@@setAlistDataByFormula公式解析时间：");/*-=notranslate=-*/

    // 执行扩展公式.目前只被销售出库单UI重载.
    execExtendFormula(alData);

    if (alData != null && alData.size() > 0) {
      if (bQuery)
       setScaleOfListData(alData);
      
      setM_alListData(alData);
      setListHeadData();
      // 设置当前的单据数量/序号，用于按钮控制
      setLastHeadRow(0);
      // 当前是表单形式时，首先切换到列表形式
      if (BillMode.Card == getM_iCurPanel())
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_SWITCH));
      // onSwitch();

      // 缺省表头指向第一张单据
      selectListBill(0);
      //deleted  by lirr 2009-06-15
      //缺省表头指向第一张单据 added by lirr 2009-05-21
      /*getBillListPanel().getHeadTable().changeSelection(0, 0, false, false);
      getBillListPanel().getHeadTable().setRowSelectionInterval(0, 0);*/

      // 初始化当前单据序号，切换时用到！！！不宜主动设置表单的数据。
      m_iCurDispBillNum = -1;
      // 当前单据数
      m_iBillQty = getM_alListData().size();

      if (bQuery) {
        String[] args = new String[1];
        args[0] = String.valueOf(m_iBillQty);
        String message = nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000339", null, args);

        /* @res "共查到{0}张单据！" */

        if (m_iBillQty > 0)
          showHintMessage(message);
        else
          showHintMessage(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000013")/*
                                       * @res
                                       * "未查到符合条件的单据。"
                                       */);

      }
      // 控制有来源单据的按钮、菜单等的状态。
      ctrlSourceBillUI(false);

    } else {
      dealNoData();
    }

    setButtonStatus(true);
  }
  
  /**
 * 方法功能描述：简要描述本方法的功能。 removeBillsOfList(iaDelLines,false)增加参数 是否要清除列表数据 转单时有多张单据不清
 * <p>
 * <b>examples:</b>
 * <p>
 * 使用示例
 * <p>
 * <b>参数说明</b>
 * @param iaDelLines
 * <p>
 * @author lirr
 * @time 2009-11-30 上午11:01:50
 */
protected void removeBillsOfList(int[] iaDelLines) {
      removeBillsOfList(iaDelLines,false);
  }

  /**
   * 创建者：王乃军 功能：删除后续列表界面处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void removeBillsOfList(int[] iaDelLines,boolean isRefAdd) {

    if (iaDelLines != null && iaDelLines.length > 0) {
      // 删除界面上的数据
      getBillListPanel().getHeadBillModel().delLine(iaDelLines);
      for (int i = iaDelLines.length - 1; i >= 0; i--)
        // 从m_alListData中删除
        if (getM_alListData() != null
            && getM_alListData().size() > iaDelLines[i])
          getM_alListData().remove(iaDelLines[i]);

      // 重置数量
      if (getM_alListData() != null)
        m_iBillQty = getM_alListData().size();
      else {
        setM_alListData(new ArrayList<GeneralBillVO>());
        m_iBillQty = 0;
      }
      // 如果删除了最后一张，或者同时删除多张，则指向第一张；
      // 如果只删除了其中一张，则指向下一张,还应该是m_iLastSelListHeadRow
      if (m_iBillQty > 0) {
        if (getM_iLastSelListHeadRow() >= m_iBillQty
            || iaDelLines.length > 1)
          setLastHeadRow(0);

        // 选中列表单据
        selectListBill(getM_iLastSelListHeadRow());
      } else { // 全删除了，清界面
        setM_alListData(new ArrayList<GeneralBillVO>());
        setLastHeadRow(-1);
        m_iCurDispBillNum = -1;
        m_iBillQty = 0;
        // 清空列表
        if (!isRefAdd){
          getBillListPanel().getHeadBillModel().clearBodyData();
          getBillListPanel().getBodyBillModel().clearBodyData();
          // 清空表单
          getBillCardPanel().getBillData().clearViewData();
        }
      }

    }
  }

  /**
   * 创建者：王乃军 功能：重置表体辅助--货位/序列号 参数： iBillNum：单据序号 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void resetBodyAssistData(int iBillNum) {
    // 置入各行的货位分配数据
    // 未改选其它单据，则无需重设数据，以提高效率。
    if (iBillNum >= 0 && getM_alListData() != null
        && getM_alListData().size() > iBillNum
        && getM_alListData().get(iBillNum) != null) { // 重置当前的货位数据
      m_alLocatorData = new ArrayList();
      // 重置当前的序列号数据
      m_alSerialData = new ArrayList();
      // 单据
      GeneralBillVO hvo = (GeneralBillVO) getM_alListData().get(iBillNum);
      if (hvo != null) { // 单据体
        GeneralBillItemVO ivo[] = (GeneralBillItemVO[]) hvo
            .getChildrenVO();
        // 货位数据
        LocatorVO[] lvo = null;
        // 序列号数据
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
   * 创建者：王乃军 功能：选中列表形式下的第sn张单据 参数：sn 单据序号
   * 
   * 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void selectListBill(int sn) {
    nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
    timer.start("@@方法selectListBill开始");/*-=notranslate=-*/
    if (sn < 0 || getM_alListData() == null
        || sn >= getM_alListData().size()
        || getM_alListData().get(sn) == null
        || getBillListPanel().getHeadTable().getRowCount() <= 0) {
      SCMEnv.out("sn error,or list null");
      return;
    }
    //deleted by lirr 2009-04-16 若先选第2行再从第一行多选则只能选中第一行问题
    // 选中表头行
    //打开此处 2009-06-15
    // modified by lirr 2009-10-14下午02:03:12 若先选第2行再从第一行多选则只能选中第一行问题
    if(getBillListPanel().getHeadTable().getSelectedRowCount()<=1){
      getBillListPanel().getHeadTable().changeSelection(sn, getBillListPanel().getHeadTable().getSelectedColumn(), false, false);
      getBillListPanel().getHeadTable().setRowSelectionInterval(sn, sn);
    }
    // 对应的表体数据
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
    // zhy 不重算换算率和生产日期
    // // 需要的话，重算非固定换算率
    // voBill.calConvRate();
    // // 需要的话，计算生产日期
    // voBill.calPrdDate();

    voi = voBill.getItemVOs();
    // 检查是否有表体，如果没有提示单据可能被删除了,但并不返回。
    if (voi == null || voi.length <= 0) {
      showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000024")/*
                             * @res
                             * "未找到表体信息，可能单据已被删除。"
                             */);
    }
    // 执行批次档案公式
    // 修改人：刘家清 修改日期：2007-10-30下午03:59:09
    // 修改原因：在单据是新增状态时，不要去刷新批次信息，因为特殊单可能会在新增保存之前刷新单据界面
    if (nc.vo.pub.VOStatus.NEW != voBill.getStatus())
      BatchCodeDefSetTool.execFormulaForBatchCode(voi);
    // ------------
    setListBodyData(voi);
    // abstract method.
    // 显示货位
    dispSpace(sn);
    selectBillOnListPanel(sn);
    // 执行公式
    // getBillListPanel().getBodyBillModel().execLoadFormula();
    //
    // 设置货位按钮
    setBtnStatusSpace(false);

    setLastHeadRow(sn);

    // 抽象方法调用
    setButtonsStatus(getM_iMode());
    setExtendBtnsStat(getM_iMode());
    // 设置签字按钮是否可用。
    setBtnStatusSign(false);

    // 如果来源单据不为空，设置业务类型等不可用
    ctrlSourceBillButtons(true);

    updateButtons();

    // 设置条码框的当前条码数据VO add by hanwei 用于初始化条码框唯一校验数据
    if (m_utfBarCode != null)
      m_utfBarCode.setCurBillItem(voi);

    timer.showExecuteTime("@@方法selectListBill时间");/*-=notranslate=-*/

  }

  /**
   * 
   * 方法功能描述：递归修改某个按钮的所有下级按钮的状态。
   * <p>
   * <b>参数说明</b>
   * 
   * @param btn
   *            要修改的按钮
   * @param enabled
   *            修改后的状态
   *            <p>
   * @author duy
   * @time 2007-3-27 下午03:49:02
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
   * 支持参照生成多张单据的按纽控制 销售出,调拨,采购单据使用,只有修改和取消可用
   * 
   * @since v5
   * @author ljun
   * 
   */
  public void setRefBtnStatus() {
    // 参照生成多张，且当前是列表，控制很多按钮不能用，只有取消和修改可用，同时双击表头列相当于切换操作
    if (m_bRefBills == true && getM_iCurPanel() == BillMode.List) {
      // 控制参照多张单据,且当前是列表状态时的按钮状态
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
      // added by lirr 2009-11-18下午01:51:21
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_MANUAL_RETURN)
           .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_PO_RETURN)
           .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_WW_RETURN)
      .setEnabled(false);

    }
    // 卡片下且是参照生成，切换按钮可用
    if (m_bRefBills == true && getM_iCurPanel() == BillMode.Card) {
      // 其余按钮类似新增时的按钮处理
      setM_iMode(BillMode.New);

      // setButtonStatus(true);
      //
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setEnabled(
          true);
      // updateButton(getButtonManager().getButton(ICButtonConst.BTN_SWITCH));

      // 拷贝了setButtonStatus(true)方法的部分代码
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
//          禁用行编辑按钮 陈倪娜 2009-09-30
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

      // 外设输入支持
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
      // 控制翻页按钮的状态：
      m_pageBtn.setPageBtnVisible(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);

      // 在新增情况下和修改情况条码框可以编辑
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(true);

    }

  }

  /**
   * 创建者：王乃军 功能：设置表体显示的 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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

      // 缺省辅计量名称
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
        // 换算率
        getM_voBill().setItemValue(row, "hsl", voInv.getHsl());
      }
      // 是否固定换算
      getM_voBill().setItemValue(row, "isSolidConvRate",
          voInv.getIsSolidConvRate());
      // 计划价
      if (voInv.getNplannedprice() != null)
        getBillCardPanel().setBodyValueAt(voInv.getNplannedprice(),
            row, IItemKey.NPLANNEDPRICE);
      // 计划金额
      Object oTempNum = getBillCardPanel().getBodyValueAt(row,
          getEnvironment().getNumItemKey());
      UFDouble dNum = null;
      UFDouble dMny = null;

      // 同时有数量和单价时，才计算
      if (oTempNum != null && voInv.getNplannedprice() != null) {
        dNum = (UFDouble) oTempNum;
        dMny = dNum.multiply((UFDouble) voInv.getNplannedprice());
      } else
        dMny = null;

      if (dMny != null)
        getBillCardPanel().setBodyValueAt(dMny, row,
            IItemKey.NPLANNEDMNY);

      // 清界面自由项显示。
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

      // 设置计量称
      BillItem bi = getBillCardPanel().getBodyItem("cmeaswarename");
      if(bi!=null){
        nc.ui.pub.beans.UIRefPane refMeasware = ((nc.ui.pub.beans.UIRefPane) bi.getComponent());
        if (refMeasware != null) {
          if (getInOutFlag() == InOutFlag.IN)
            refMeasware.setPK(voInv.getCrkjlc());
          else
            refMeasware.setPK(voInv.getCckjlc());
          /** 强制执行表体行，数量列的公式 */
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
  
  String m_Used=nc.ui.ml.NCLangRes.getInstance().getStrByID("4008other","UPP4008other-000431")/*@res "存量"*/;

  /**
   * 创建人：刘家清 创建时间：2008-7-24 下午03:55:23 创建原因： 参数IC026"入库货位参照的优先顺序"增加选项"上次入库货位"。
   * 如果参数选择此选项，则生成入库单时自动将本仓库+本存货的上次入库货位自动带入"货位"栏目，可以手工修改。上次入库货位取表体入库日期最新的入库单的入库货位。
   * @param row
   * @param voInv
   */
  protected void setBodyInSpace(int row, InvVO voInv) {
    try{
      if (getBillCardPanel().getBodyItem("vspacename") != null
          && (voInv.getInOutFlag() == InOutFlag.IN||voInv.getInOutFlag() == InOutFlag.SPECIAL)
          && null != m_sIC040 && "N".equals(m_sIC040)
          && null != m_sIC026 && "上次入库货位"/*-=notranslate=-*/.equals(m_sIC026)) {
        filterSpace(row);
        nc.ui.pub.beans.UIRefPane refSpace = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem("vspacename").getComponent());
        //陈倪娜 2010-09-02 不走缓存 直接查询后台
        refSpace.getRefModel().setCacheEnabled(false);
        java.util.Vector refdata = refSpace.getRefModel().getData();
        refSpace.getRefModel().setCacheEnabled(true);//恢复缓存 陈倪娜 2010-09-02        
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
   * 创建者：王乃军 功能：用于修改后设置新增行的PK,并同时刷新传入的VO. 要保证VO中Item的顺序和界面数据一致。 参数： 返回： 例外：
   * 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
        // 第0个是表头PK,
        getBillCardPanel().setBodyValueAt(alBodyPK.get(pk_count + 1),
            row, IItemKey.NAME_BODYID);
        pk_count++;
      }
    }
    getBillCardPanel().getBillModel().setNeedCalculate(true);
    getBillCardPanel().getBillModel().reCalcurateAll();

  }

  /**
   * 创建者：王乃军 功能：设置按钮状态。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */

  protected void setButtonStatus() {
    setButtonStatus(true);
    
    //modified by lirr 2009-02-13 修改原因放在setButtonStatus(true)中
    /*//  二次开发扩展
    getPluginProxy().setButtonStatus();*/
  }

  /**
   * 创建者：王乃军 功能：设置新增单据的初始数据，如日期，制单人等。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 修改人：刘家清 修改日期：2007-11-14下午01:53:20 修改原因：时间的设置移到后台。
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
   * 创建者：cqw 功能：设置修改单据的初始数据 参数： 返回： 例外： 日期：(2005-04-04 19:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 修改人：刘家清 修改日期：2007-11-14下午01:53:20 修改原因：时间的设置移到后台。
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
   * 创建者：yudaying 功能：设置表尾显示数据,从m_voBill中取数。浏览状态下要重读现存量 参数： 返回： 例外：
   * 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
    // 修改 by hanwei 2003-11-13
    if (getM_voBill().getItemInv(row) == null
        || getM_voBill().getItemInv(row).getCinventoryid() == null) {
      setTailValue(null);
      return;
    }

    InvVO voInv = getM_voBill().getItemInv(row);
    // 表头的库存组织和仓库
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
    // 取当前vo中的数据

    // 浏览状态下要刷新现存量，如果已经读过，则不必重读。
    // 在编辑--〉浏览切换时，切换浏览单据时要清空库存量。
    // 修改状态下，选中了原有的行也要读现存量。
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

      // 查询控制存量
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
      // 查询存货信息
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

    // 设置界面显示
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
   * 创建者：王乃军 功能：设置表尾显示数据,传入null则清空。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void setTailValue(InvVO voInv) {
    // 本库现存量
    nc.ui.pub.bill.BillItem biTail = null;
    biTail = getBillCardPanel().getTailItem("bkxcl");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getBkxcl());
      else
        biTail.setValue(null);
    // 现存总量
    biTail = getBillCardPanel().getTailItem("xczl");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getBkxcl());
      else
        biTail.setValue(null);
    // 最高库存
    biTail = getBillCardPanel().getTailItem("nmaxstocknum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNmaxstocknum());
      else
        biTail.setValue(null);
    // 最低库存
    biTail = getBillCardPanel().getTailItem("nminstocknum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNminstocknum());
      else
        biTail.setValue(null);
    // 安全库存
    biTail = getBillCardPanel().getTailItem("nsafestocknum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNsafestocknum());
      else
        biTail.setValue(null);
    // 订货点量
    biTail = getBillCardPanel().getTailItem("norderpointnum");
    if (biTail != null)
      if (voInv != null)
        biTail.setValue(voInv.getNorderpointnum());
      else
        biTail.setValue(null);

  }

  /**
   * 创建者：王乃军 功能：同步行数据，如货位、序列号 参数： int iFirstLine,iLastLine 行号，start from 0 int
   * iCol 列 start from 0 int type 1: add 0: update -1:delete
   * 
   * 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志： 2001-06-13. 同步VO
   * 
   * 
   * 
   */
  protected void synchLineData(int iFirstLine, int iLastLine, int iCol,
      int iType) {
    if (iFirstLine < 0 || iLastLine < 0)
      return;
    // 修改人：刘家清 修改日期：2007-10-16下午04:56:44 修改原因：不是行变化就直接返回
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
      // 初始化行数据，比如在复制单据时，m_alLocatorData==null 但单据行数不为0。
      m_alLocatorData = new ArrayList(getBillCardPanel().getBillModel()
          .getRowCount());
      for(int i = 0 ;i < getBillCardPanel().getBillModel().getRowCount() ;i++)
        m_alLocatorData.add(i,null);
    }
    if (m_alSerialData == null) {
      SCMEnv.out("init serial data.");
      // m_alSerialData=new ArrayList();
      // 初始化行数据，比如在复制单据时，m_alSerialData==null 但单据行数不为0。
      m_alSerialData = new ArrayList(getBillCardPanel().getBillModel()
          .getRowCount());
      for(int i = 0 ;i < getBillCardPanel().getBillModel().getRowCount() ;i++)
        m_alSerialData.add(i,null);
    }
    if (getM_voBill() == null)
      setM_voBill(new GeneralBillVO());

    switch (iType) {
    case javax.swing.event.TableModelEvent.INSERT:// 增行：插、追加、粘贴
      m_alLocatorData.add(iFirstLine, null);
      m_alSerialData.add(iFirstLine, null);
      GeneralBillItemVO bodyvo = getM_voBill().insertItem(iFirstLine);
      setUIVORowPosWhenNewRow(iFirstLine, bodyvo);
      break;
    case javax.swing.event.TableModelEvent.UPDATE:
      while (getBillCardPanel().getRowCount() > getM_voBill()
          .getItemCount()) {
        // 修改人：刘家清 修改日期：2007-10-16下午04:56:44 修改原因：传入的iFirstLine有问题，更正了一下
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

    // try的目的是保证addListener被执行。
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
   * 创建者：王乃军 功能：用表单形式下的单据刷新列表数据，用于修改保存后。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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

    // 通过单据公式容器类执行有关公式解析的方法
    // execFormulaAtBillVO(bvo);
    execHeadFormulaAtVOs(new GeneralBillHeaderVO[] { bvo.getHeaderVO() });
    // 刷新数据
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
   * 创建人：刘家清 创建日期：2007-10-24下午01:28:23 创建原因：用表单形式下的单据刷新列表数据，只更新有限的表头和表体字段。
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

    // 刷新列表界面显示
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
    // 选中列表单据，以刷新表体显示
    selectListBill(getM_iLastSelListHeadRow());

  }

  /**
   * 创建者：王乃军 功能：取消签字成功后处理 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void freshAfterCancelSignedOK() {
    try {

      // refreshSelBill(getM_iLastSelListHeadRow() );
      GeneralBillVO voBill = null;
      // 设置m_voBill,以读取控制数据。
      if (m_iLastSelListHeadRow >= 0 && m_alListData != null
          && m_alListData.size() > m_iLastSelListHeadRow
          && m_alListData.get(m_iLastSelListHeadRow) != null) {
        // 这里不能clone(),改变了m_voBill同时改变m_alListData
        voBill = (GeneralBillVO) m_alListData
            .get(m_iLastSelListHeadRow);
        // 把当前登录人设置到vo
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
        // 重置列表界面
        m_alListData.set(m_iLastSelListHeadRow, voBill);
        // 刷新列表形式
        getBillListPanel().getHeadBillModel().setBodyRowVO(
            voBill.getParentVO(), getM_iLastSelListHeadRow());
      }
      // 把当前登录人置空
      // m_voBill刷新
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

      // 把界面签字人名称置空
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

      // 刷新列表形式
      // setListHeadData();
      // selectListBill(m_iLastSelListHeadRow);

      // 设置按钮状态,签字可用，取消签字不可用
      // 还应进一步判断当前的单据是否已签字
      // 未签字，所以设置按钮状态,签字可用，取消签字不可用
      setButtonStatus(false);
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      // 可删、改
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
   * 创建者：王乃军 功能：根据当前单据的待审状态决定签字/取消签字那个可用 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSign() {
    // 只在浏览状态下并且界面上有单据时控制
    setBtnStatusSign(true);
  }

  /**
   * 创建者：王乃军 功能：根据当前存货的属性决定序列号分配是否可用 参数： row行号 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSN(int row) {
    setBtnStatusSN(row, true);
  }

  /**
   * 创建者：王乃军 功能：根据当前仓库的状态决定货位分配是否可用 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSpace() {
    setBtnStatusSpace(true);
  }

  /**
   * 创建者：王乃军 功能：刷新列表形式表头数据 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 2003-02-27 韩卫 添加公式处理获取仓库和废品仓库信息
   */
  public void setListHeadData() {
    if (getM_alListData() != null && getM_alListData().size() > 0) {
      // 刷新列表形式表头数据
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
   * 创建者：余大英 功能：得到当前单据vo,包含货位/序列号,包括删除的行，未修改的行只传PK. 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
   * 销售出库对冲联查 v52。 功能： 参数： 返回： 例外： 日期：(2002-04-18 10:43:46)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected GeneralBillVO getCurVO() {

    GeneralBillVO billvo = null;
    try {

      if (BillMode.List == getM_iCurPanel()) {
        int selrow = getBillListPanel().getHeadTable().getSelectedRow();
        if (selrow < 0) {
          // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          // "scmcommon", "UPPSCMCommon-000167")/* @res "没有选择单据" */ );

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
   * 创建者：余大英 功能：得到当前单据vo,包含货位/序列号和界面上所有的数据,不包括删除的行 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：得到完整的单据表体VO，包括删除的行。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
    // 为了得到自由项。
    GeneralBillItemVO[] voaItemForFree = getM_voBill().getItemVOs();
    // 删除的行

    // vo数组的长度==当前显示的行数+删除的总行数
    int rowCount = vBodyData.size();
    int length = 0;
    Vector vDeleteRow = bmTemp.getDeleteRow();
    if (vDeleteRow != null)
      length = rowCount + vDeleteRow.size();
    else
      length = rowCount;
    // 初始化返回的vo
    GeneralBillItemVO[] voaBody = new GeneralBillItemVO[length];

    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    // 整理当前界面上显示的行，包括原行、修改后的行、新增的行。
    // 传所有数据
    for (int i = 0; i < vBodyData.size(); i++) {
      voaBody[i] = new GeneralBillItemVO();
      //设置行标识
      voaBody[i].setRowpos((String)bmTemp.getValueAt(i, GeneralBillItemVO.RowPos));
      iRowStatus = bmTemp.getRowState(i);
      for (int j = 0; j < bmTemp.getBodyItems().length; j++) {
        nc.ui.pub.bill.BillItem item = bmTemp.getBodyItems()[j];

        Object aValue = bmTemp.getValueAt(i, item.getKey());

        // SCMEnv.out(item.getKey()+aValue);
        voaBody[i].setAttributeValue(item.getKey(), aValue);
      }
      // 设置状态
      switch (iRowStatus) {
      case nc.ui.pub.bill.BillModel.ADD: // 新增的行
        voaBody[i].setStatus(nc.vo.pub.VOStatus.NEW);
        break;
      case nc.ui.pub.bill.BillModel.MODIFICATION: // 修改后的行
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UPDATED);
        break;
      case nc.ui.pub.bill.BillModel.NORMAL: // 原行
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UNCHANGED);
        break;
      }
      // 货位分配数据
      if (m_alLocatorData != null && m_alLocatorData.size() > i)
        voaBody[i].setLocator((LocatorVO[]) m_alLocatorData.get(i));
      // 序列号数据
      if (m_alSerialData != null && m_alSerialData.size() > i)
        voaBody[i].setSerial((SerialVO[]) m_alSerialData.get(i));
      // 自由项
      voaBody[i].setFreeItemVO(voaItemForFree[i].getFreeItemVO());

    }
    // 删除的行处理：2003-02-09 wnj:得从原有的单据中拷贝，否则货位和序列号都没有了。
    if (vDeleteRow != null && vDeleteRow.size() > 0) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
        // =========
        int col = bmTemp.getBodyColByKey(IItemKey.NAME_BODYID); // 表体PK
        Vector rowVector = null;
        String sItemPK = null;
        GeneralBillVO voOriginalBill = (GeneralBillVO) getM_alListData()
            .get(getM_iLastSelListHeadRow());
        GeneralBillItemVO[] voaOriginalItem = voOriginalBill
            .getItemVOs();
        // 查询删除的行的pk,在原单据中查找之。
        // 如果单据行较多，可以优化为hastable.
        for (int del = 0; del < vDeleteRow.size(); del++) {
          rowVector = (Vector) vDeleteRow.elementAt(del);
          sItemPK = (String) rowVector.elementAt(col);
          // 在原单据中查找之。
          if (sItemPK != null)
            for (int item = 0; item < voaOriginalItem.length; item++)
              if (sItemPK.equals(voaOriginalItem[item]
                  .getPrimaryKey()))
                voaBody[del + rowCount] = (GeneralBillItemVO) voaOriginalItem[item]
                    .clone();
          // 设置状态
          voaBody[del + rowCount]
              .setStatus(nc.vo.pub.VOStatus.DELETED);
        }
      } else
        SCMEnv.out("update err,cannot dup del rows.");

    }
    return voaBody;
  }

  /**
   * 创建者：王乃军 功能：得到修改后的vo,用于修改后的保存 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
    // 为了得到自由项。
    GeneralBillItemVO[] voaItemForFree = getM_voBill().getItemVOs();

    // vo数组的长度==当前显示的行数
    int rowCount = vBodyData.size();
    // 初始化返回的vo
    GeneralBillItemVO[] voaBody = new GeneralBillItemVO[rowCount];
    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    // 整理当前界面上显示的行，包括原行、修改后的行、新增的行。
    for (int i = 0; i < rowCount; i++) {
      voaBody[i] = new GeneralBillItemVO();
      iRowStatus = bmTemp.getRowState(i);
      
      //设置行标识
      voaBody[i].setRowpos((String)bmTemp.getValueAt(i, GeneralBillItemVO.RowPos));
      
      for (int j = 0; j < bmTemp.getBodyItems().length; j++) {

        nc.ui.pub.bill.BillItem item = bmTemp.getBodyItems()[j];
        Object aValue = bmTemp.getValueAt(i, item.getKey());

        // SCMEnv.out(item.getKey()+" "+aValue);
        voaBody[i].setAttributeValue(item.getKey(), aValue);
      }
      // 设置状态
      switch (iRowStatus) {
      case nc.ui.pub.bill.BillModel.ADD: // 新增的行
        voaBody[i].setStatus(nc.vo.pub.VOStatus.NEW);
        break;
      case nc.ui.pub.bill.BillModel.MODIFICATION: // 修改后的行
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UPDATED);
        break;
      case nc.ui.pub.bill.BillModel.NORMAL: // 原行
        voaBody[i].setStatus(nc.vo.pub.VOStatus.UNCHANGED);
        break;
      }
      // 货位分配数据
      if (m_alLocatorData != null && m_alLocatorData.size() > i)
        voaBody[i].setLocator((LocatorVO[]) m_alLocatorData.get(i));

      // 序列号数据
      if (m_alSerialData != null && m_alSerialData.size() > i) {
        SerialVO[] serialVOs = (SerialVO[]) m_alSerialData.get(i);
        // 设置序列号日期
        voaBody[i].updateSerialDate(serialVOs);
        // 设置序列号
        voaBody[i].setSerial(serialVOs);
      }

      // 自由项
      voaBody[i].setFreeItemVO(voaItemForFree[i].getFreeItemVO());

      // 删除的行不传
    }
    return voaBody;
  }

  /**
   * 创建者：王乃军 功能：得到当前单据是否已签字 参数： 返回： 已签字 1 未签字 0 不能操作 -1 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public int isSigned() {
    GeneralBillVO voBill = null;
    // 设置voBill,以读取控制数据。

    if (getM_iCurPanel() == BillMode.List
        && getM_iLastSelListHeadRow() >= 0 && getM_alListData() != null
        && getM_alListData().size() > getM_iLastSelListHeadRow()
        && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
      voBill = (GeneralBillVO) getM_alListData().get(
          getM_iLastSelListHeadRow());
    else
      voBill = getM_voBill();

    if (voBill != null) {
      
//    是否有签字人
      Integer fbillflag = ((GeneralBillHeaderVO) voBill.getHeaderVO())
          .getFbillflag();
      if (fbillflag != null && (fbillflag.intValue()==3 || fbillflag.intValue()==4))
        return SIGNED;
      
//     是否有签字人
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
        // 测试实出/入数量
        if ((voaItem[i].getNinnum() == null || voaItem[i].getNinnum()
            .toString().length() == 0)
            && (voaItem[i].getNoutnum() == null || voaItem[i]
                .getNoutnum().toString().length() == 0))
          break;

      }

      if (i < iCount) // 无数量
        return CANNOTSIGN;

      // 是否有签字人
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
   * 创建者：王乃军 功能：在表单设置显示VO 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void setBillVO(GeneralBillVO bvo) {
    setBillVO(bvo, true, true);
  }

  /**
   * 创建者：仲瑞庆 功能：保存检查 参数： 返回： 例外： 日期：(2001-5-24 下午 5:17) 修改日期，修改人，修改原因，注释标志：
   */
  public boolean checkVO() {
    try {
      String sAllErrorMessage = "";
      ArrayList alRowNum = new ArrayList();
      Color cExceptionColor = null;

      // 执行以下检查，将不具有的检查注释------------------------------------------------
      // ------------------------------------------------------------------------------
      // VO存在检查
      VOCheck.checkNullVO(getM_voBill());
      // ------------------------------------------------------------------------------
      // 应发数量检查,要放在前面
      // 本节点使用=====================
      // 检查数量的方向和业务方向是否一致.退货退库的数量要为负,非退库退货数量为正
      boolean isRight = VOCheck.isNumDirectionRight(getM_voBill());
      if (!isRight) {
        sAllErrorMessage = nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008check", "UPP4008check-000213")/*
                               * @res
                               * "数量的方向和业务方向不一致！"
                               */;
        showErrorMessage(sAllErrorMessage);
        return false;
      }
      // 数值输入全部性检查 v5 支持混填
      // VOCheck.checkNumInput(m_voBill.getChildrenVO(),
      // getEnvironment().getNumItemKey());

      // 本节点使用=====================
      // 表头表体非空检查
      try {
        VOCheck.validate(getM_voBill(), GeneralMethod
            .getHeaderCanotNullString(getBillCardPanel()),
            GeneralMethod
                .getBodyCanotNullString(getBillCardPanel()));
      } catch (ICNullFieldException e) {
        // 显示提示
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
      } catch (ICHeaderNullFieldException e) {
        // 显示提示
        String sErrorMessage = GeneralMethod.getHeaderErrorMessage(
            getBillCardPanel(), e.getErrorRowNums(), e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        
      }

      /*
       * // 采购订单退库表头VO里业务类型的非空项检查 try {
       * VOCheck.validatePO_RETURN(m_voBill); } catch
       * (ICHeaderNullFieldException e) { // 显示提示 String sErrorMessage =
       * GeneralMethod.getHeaderErrorMessage( getBillCardPanel(),
       * e.getErrorRowNums(), e.getHint()); sAllErrorMessage =
       * sAllErrorMessage + sErrorMessage + "\n"; }
       */

      // 有导入操作的导入数据校验
      if (m_bIsImportData)
        sAllErrorMessage = sAllErrorMessage
            + nc.ui.ic.pub.bill.GeneralBillUICtl
                .checkImportBodyVO(getM_voBill()
                    .getChildrenVO());
      // ------------------------------------------------------------------------------
      // 业务项检查

      // 自由项
      try {
        VOCheck.checkFreeItemInput(getM_voBill(), getEnvironment()
            .getNumItemKey());

      } catch (ICNullFieldException e) {
        // 显示提示
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);

      }
      // 辅计量
      try {
        VOCheck.checkAssistUnitInputByID(getM_voBill(),
            getEnvironment().getNumItemKey(), getEnvironment()
                .getAssistNumItemKey());
      } catch (ICNullFieldException e) {
        // 显示提示
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
        // 显示提示
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
      }

      // 入库日期
      try {
        if (getBillCardPanel().getBodyItem("dbizdate") != null)
          VOCheck.checkdbizdate(getM_voBill(), getEnvironment()
              .getNumItemKey());
      } catch (ICNullFieldException e) {
        // 显示提示
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        if(!sAllErrorMessage.contains(NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000002")/*出库日期*/)&&!sAllErrorMessage.contains(NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000003")/*入库日期*/)){
          sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";     
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
        }
      }
      // 非期初单检查业务日期需大于系统启用日期
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
      // 价格>0检查
      try {
        VOCheck.checkGreaterThanZeroInput(
            getM_voBill().getChildrenVO(), "nprice",
            nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
                "UC000-0000741")/* @res "单价" */);
      } catch (ICPriceException e) {
        // 显示提示
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        alRowNum.addAll(e.getErrorRowNums());
        cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
      }
      // 序列号检查
      try {
         /*   
              VOCheck.checkSNInput(getM_voBill().getChildrenVO(),
                  getEnvironment().getNumItemKey());*/
            /*modified by  lirr 2009-02-25 
            @修改原因： v56增加 资产类存货在非资产仓出入时是否可以不输入序列号参数
            @param isChechAssetInv 资产类存货在非资产仓出入时是否可以不输入序列号参数*/
    	  if(getM_voBill().getWh()==null) {
    		  showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008spec","UPP4008spec-000169")/*@res "请输入仓库！"*/);
    		  return false;
    	  }

    	  boolean isCapitalStor = getM_voBill().getWh().getIsCapitalStor()==null ? false:getM_voBill().getWh().getIsCapitalStor().booleanValue();
		  String cbilltypecode = getM_voBill().getCbilltypecode();
		  if ((cbilltypecode.equals(BillTypeConst.m_purchaseIn) || 
				  cbilltypecode.equals(BillTypeConst.m_allocationIn)) && 
				  isCapitalStor ) {
				  // 采购入、调拨入单据，对于资产仓，不检查序列号，V57需求
				  // DO NOTHING
		  }
		  else {
	          VOCheck.checkSNInput(getM_voBill().getChildrenVO(),
	                  getEnvironment().getNumItemKey(),isCheckAssetInv(),
	                  isCapitalStor,getM_voBill().getBizTypeid());
		  }
      } catch (ICSNException e) {
        // 显示提示
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

      
      // 检查对应单据号
      //修改人：刘家清 修改日期：2008-5-16下午01:33:32 修改原因：借出转销售业务时，出库跟踪入库存货不需要录入对应单据号。
      String busiVerifyrule = null;
      if (null != getM_voBill().getHeaderVO().getCbiztypeid()
          && !"".equals(getM_voBill().getHeaderVO().getCbiztypeid()))
        busiVerifyrule = getBusiVerifyrule(getM_voBill().getHeaderVO().getCbiztypeid());
      if (null == busiVerifyrule || !"C".equals(busiVerifyrule)){
        ArrayList alCheckString = new ArrayList();
        for (int i = 0; i < getM_voBill().getItemVOs().length; i++) {
          boolean bCanAdded = false;
          if (getInOutFlag() == InOutFlag.IN) {
            // 入库单
            if (getIsInvTrackedBill(getM_voBill().getItemInv(i))
                && getM_voBill().getItemValue(i,
                    getEnvironment().getNumItemKey()) != null
                && ((UFDouble) getM_voBill().getItemValue(i,
                    getEnvironment().getNumItemKey()))
                    //modified by liuzy 2008-05-22 把等于去掉，入库实收可以为0
                    .doubleValue() < 0) {
              // 数量<0
              bCanAdded = true;
            }
          } else {
            // 出库单
            if (getIsInvTrackedBill(getM_voBill().getItemInv(i))
                && (getM_voBill().getItemValue(i,
                    getEnvironment().getNumItemKey()) == null || ((UFDouble) getM_voBill()
                    .getItemValue(i,
                        getEnvironment().getNumItemKey()))
                    .doubleValue() >= 0)) {
              // 数量>=0
              bCanAdded = true;
            }
          }
          if (bCanAdded) {
            ArrayList alCheckddd = new ArrayList();
            alCheckddd.add(0, new Integer(i));
            alCheckddd.add(1, getEnvironment().getNumItemKey());
            if (getBillCardPanel().getBodyItem(IItemKey.CORRESCODE) != null) {
              alCheckddd.add(2, IItemKey.CORRESCODE); // 对应单据号字段1
              alCheckddd.add(3, "ccorrespondbid"); // 对应单据号字段2
            }
            alCheckString.add(alCheckddd);
          }
        }
        try {
          VOCheck.validateBody(getM_voBill().getItemVOs(), alCheckString);
        } catch (ICNullFieldException e) {
          // 显示提示
          String sErrorMessage = GeneralMethod.getBodyErrorMessage(
              getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
              e.getHint());
          sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
          alRowNum.addAll(e.getErrorRowNums());
          cExceptionColor = e.getExceptionColor(m_bRowLocateErrorColor);
        }
      // }
      }

      // 自定校验前先行报错，退出
      if (sAllErrorMessage.trim().length() != 0) {
        showErrorMessage(sAllErrorMessage);
        // 更改颜色
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

      // 校验条码数量不能大于实际收发数量
      GeneralBillItemVO[] voaItemtemp = (GeneralBillItemVO[]) getM_voBill()
          .getChildrenVO();
      String sMsg = BarcodeparseCtrl
          .checkNumWithBarNum(voaItemtemp, true);
      if (sMsg != null) {
        showErrorMessage(sMsg);
        return false;
      }

      // 保存时，主条码和次条码完整性检查，如果不完整时，强制不通过
      sMsg = BarcodeparseCtrl.checkBarcodesubIntegrality(voaItemtemp);
      if (sMsg != null) {
        showErrorMessage(sMsg);
        return false;
      }

      // 检查业务类型是W的则仓库必须入外寄仓.

      try {
        checkVMIWh(getM_voBill());

      } catch (Exception ex1) {
        showErrorMessage(ex1.getMessage());

        return false;

      }

      // 检查存货为按毛重管理库存的必须输入毛重
      try {
        VOCheck.checkGrossNumInput(getM_voBill().getItemVOs(),
            getEnvironment().getGrossNumItemKey(), getEnvironment()
                .getNumItemKey());
      } catch (ICNullFieldException e) {
        // 显示提示
        String sErrorMessage = GeneralMethod.getBodyErrorMessage(
            getBillCardPanel(), getM_voBill(), e.getErrorRowNums(),
            e.getHint());
        sAllErrorMessage = sAllErrorMessage + sErrorMessage + "\n";
        // 更改颜色
        nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);
      }

      return true;

    } catch (ICDateException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      // showHintMessage(e.getHint());
      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);
      
      return false;
    } catch (ICNullFieldException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());

      showErrorMessage(sErrorMessage);
      
      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICNumException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICPriceException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICSNException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICLocatorException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);
      
      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);

      return false;
    } catch (ICRepeatException e) {
      // 显示提示
      String sErrorMessage = GeneralMethod.getBodyErrorMessage(
          getBillCardPanel(), getM_voBill(), e.getErrorRowNums(), e
              .getHint());
      // String sErrorMessage= getBodyErrorMessage(e.getErrorRowNums(),
      // e.getHint());
      showErrorMessage(sErrorMessage);

      // 更改颜色
      nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(), e);
      
      return false;
    } catch (ICHeaderNullFieldException e) {
      // 显示提示
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
      SCMEnv.out("校验异常！其他未知故障...");/*-=notranslate=-*/
      handleException(e);
      return false;
    }
  }

  /**
   * 创建者：王乃军 功能：得到修改后的vo,用于修改后的保存 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能： 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  /**
   * 创建者：王乃军 功能：得到当前显示的panel 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public int getCurPanel() {
    return getM_iCurPanel();
  }

  /**
   * 创建者：王乃军 功能：单据表体右键菜单处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void onMenuItemClick(java.awt.event.ActionEvent e) {
    // 源
    UIMenuItem item = (UIMenuItem) e.getSource();
    // 复制
    if (item == getBillCardPanel().getCopyLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_COPY));
      // onCopyLine();
    } else // 粘贴
    if (item == getBillCardPanel().getPasteLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_PASTE));
      // onPasteLine();
    }
    // 粘贴到表尾时,设置行号
    else if (item == getBillCardPanel().getPasteLineToTailMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_PASTE_TAIL));

    } else // 增行
    if (item == getBillCardPanel().getAddLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_ADD));
      // onAddLine();

    } else // 删行
    if (item == getBillCardPanel().getDelLineMenuItem())
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_DELETE));
    // onDeleteRow();
    else // 插入行
    if (item == getBillCardPanel().getInsertLineMenuItem()) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_INSERT));
      // onInsertLine();
    }
    
//  二次开发扩展
    getPluginProxy().onMenuItemClick(e);

  }

  /**
   * 创建者：仲瑞庆 功能：复制行 参数： 返回： 例外： 日期：(2001-6-26 下午 9:32) 修改日期，修改人，修改原因，注释标志：
   * 
   */
  protected void voBillCopyLine() {
    int[] row = getBillCardPanel().getBillTable().getSelectedRows();
    if (row == null || row.length == 0) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "common", "UCH004")/* @res "请选择要处理的数据行" */);
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
      // 修改人：刘家清 修改日期：2007-12-27下午07:38:49
      // 修改原因：对于invvo、freevo等有实例的对象，不能复制。
      SmartVOUtilExt.copyVOByVO(m_voaBillItem[i], keys, uicopyvo, keys,
          new String[] { "invvo", "freevo" });
      // 清除货位、序列号，这些数据是不复制的,和 m_alLoctorData,m_alSerialData保持一致
      // ydy 2004-07-02 货位复制
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

      // 清除条码数据 add by hanwei 2004-04-07
      m_voaBillItem[i].setBarCodeVOs(null);
      m_voaBillItem[i].setAttributeValue(IItemKey.NBARCODENUM,
          new UFDouble(0.0));

      m_voaBillItem[i].setBarcodeClose(new nc.vo.pub.lang.UFBoolean('N'));

      m_voaBillItem[i].setAttributeValue(IItemKey.NKDNUM, null);
    }

  }

  /**
   * 创建者：仲瑞庆 功能：粘贴行 参数： 返回： 例外： 日期：(2001-6-26 下午 9:32) 修改日期，修改人，修改原因，注释标志：
   */
  protected void voBillPastLine() {
    // 要求在已经增完行后执行
    if (m_voaBillItem != null) {
      int row = getBillCardPanel().getBillTable().getSelectedRow()
          - m_voaBillItem.length;
      voBillPastLine(row);
    }
  }

  /**
   * 创建者：王乃军 功能：得到单据数量 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public int getBillCount() {
    return m_iBillQty;
  }

  /**
   * 创建者：王乃军 功能：得到指定行的VO 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
    // 为了得到自由项。
    GeneralBillItemVO voItemForFree = getM_voBill().getItemVOs()[iLine];

    // vo数组的长度==当前显示的行数
    // int rowCount = vBodyData.size();
    // 初始化返回的vo
    GeneralBillItemVO voBody = new GeneralBillItemVO();

    int iRowStatus = nc.ui.pub.bill.BillModel.ADD;

    // 整理当前界面上显示的行，包括原行、修改后的行、新增的行。
    iRowStatus = bmTemp.getRowState(iLine);
    for (int j = 0; j < bmTemp.getBodyItems().length; j++) {
      nc.ui.pub.bill.BillItem item = bmTemp.getBodyItems()[j];
      Object aValue = bmTemp.getValueAt(iLine, item.getKey());
      voBody.setAttributeValue(item.getKey(), aValue);
    }
    
    //设置行标识
    voBody.setRowpos((String)bmTemp.getValueAt(iLine, GeneralBillItemVO.RowPos));
    
    // 设置状态
    switch (iRowStatus) {
    case nc.ui.pub.bill.BillModel.ADD: // 新增的行
      voBody.setStatus(nc.vo.pub.VOStatus.NEW);
      break;
    case nc.ui.pub.bill.BillModel.MODIFICATION: // 修改后的行
      voBody.setStatus(nc.vo.pub.VOStatus.UPDATED);
      break;
    case nc.ui.pub.bill.BillModel.NORMAL: // 原行
      voBody.setStatus(nc.vo.pub.VOStatus.UNCHANGED);
      break;
    }
    // 货位分配数据
    if (m_alLocatorData != null && m_alLocatorData.size() > iLine)
      voBody.setLocator((LocatorVO[]) m_alLocatorData.get(iLine));
    // 序列号数据
    if (m_alSerialData != null && m_alSerialData.size() > iLine)
      voBody.setSerial((SerialVO[]) m_alSerialData.get(iLine));
    // 自由项
    voBody.setFreeItemVO(voItemForFree.getFreeItemVO());

    // 删除的行不传
    return voBody;
  }

  /**
   * 创建者：王乃军 功能：当前选中行是否能序列号分配，要考虑列表/表单下的选中 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public boolean isSNmgt() {
    // 当前选中的行
    int iCurSelBodyLine = -1;
    if (BillMode.Card == getM_iCurPanel()) {
      iCurSelBodyLine = getBillCardPanel().getBillTable()
          .getSelectedRow();
      if (iCurSelBodyLine < 0) {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000016")/*
                               * @res
                               * "请选中要进行序列号分配的行。"
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
                               * "请选中要查看序列号的行。"
                               */);
        return false;
      }
    }
    InvVO voInv = null;
    // 读表体vo,区分列表下还是表单下
    // 表单形式下
    if (BillMode.Card == getM_iCurPanel()) {
      if (getM_voBill() == null) {
        SCMEnv.out("bill null E.");
        return false;
      }
      voInv = getM_voBill().getItemInv(iCurSelBodyLine);
    } else // 列表形式下
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
   * 创建者：王乃军 功能：设置状态条，缺省情况下不用设置。 需要的话可以把提示信息重定向到指定的TextField 参数： 返回： 例外：
   * 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void setHintBar(javax.swing.JTextField tfHint) {
    m_tfHintBar = tfHint;
  }

  /**
   * 创建者：王乃军 功能：重载的显示提示信息功能 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：得到当前选中的单据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public int getCurSelBill() {
    return getM_iLastSelListHeadRow();
  }

  /**
   * 创建者：王乃军 功能：是否是货位管理，只适用于出入库单。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public boolean isLocatorMgt() {
    if (getM_voBill() != null && BillMode.Card == getM_iCurPanel()) {
      WhVO voWh = getM_voBill().getWh();
      // 货位管理的仓库需要读货位数据
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
      // 货位管理的仓库需要读货位数据
      if (voWh != null && voWh.getIsLocatorMgt() != null
          && voWh.getIsLocatorMgt().intValue() == 1)
        return true;
      else
        return false;
    } else
      return false;
  }

  /**
   * 创建者：王乃军 功能：清除指定行、指定列的数据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void clearRowData(int iBillFlag, int row, String sItemKey) {
    // 得到需清除的itemkey
    String[] saColKey = GeneralBillUICtl.getClearIDs(iBillFlag, sItemKey,
        this);
    if (saColKey != null && saColKey.length > 0)
      clearRowData(row, saColKey);

  }

  /**
   * 创建者：余大英 功能：清除指定行、指定列的数据 参数：过滤辅计量 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 过滤单据参照 创建者：张欣 功能：初始化参照过滤 参数： 返回： 例外： 日期：(2001-7-17 10:33:20)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void filterRef(String sCorpID) {
    try {
      // 过滤仓库参照
      nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
          IItemKey.WAREHOUSE);
      RefFilter.filtWh(bi, sCorpID, getFilterWhString(null));

      if (!(getBillType().equals("4Y") || getBillType().equals("4E"))) {
        bi = getBillCardPanel().getHeadItem("cotherwhid");
        RefFilter.filtWh(bi, sCorpID, null);
      }
      // 过滤库存组织 add by hanwei 2004-05-09
      bi = getBillCardPanel().getHeadItem("pk_calbody");
      RefFilter.filtCalbody(bi, sCorpID, null);

      // 过滤存货编码
      bi = getBillCardPanel().getBodyItem("cinventorycode");
      nc.ui.pub.beans.UIRefPane invRef = (nc.ui.pub.beans.UIRefPane) bi
          .getComponent();
      invRef.setTreeGridNodeMultiSelected(true);
      invRef.setMultiSelectedEnabled(true);
      // invRef.getRefModel().setIsDynamicCol(true);
      // invRef.getRefModel().setDynamicColClassName("nc.ui.scm.pub.RefDynamic");

      RefFilter.filtInv(bi, sCorpID, null);

      // 过滤表头存货编码
      bi = getBillCardPanel().getHeadItem("cinventoryid");
      RefFilter.filtInv(bi, sCorpID, null);
      // 过滤表头第二个仓库参照
      bi = getBillCardPanel().getHeadItem(IItemKey.WASTEWAREHOUSE);
      if (bi != null && bi.getDataType() == BillItem.UFREF)
        RefFilter.filtWasteWh(bi, sCorpID, null);
      // 过滤客户参照
      bi = getBillCardPanel().getHeadItem("ccustomerid");
      RefFilter.filtCust(bi, sCorpID, null);

      // 过滤业务员参照 2003-11-20
      bi = getBillCardPanel().getHeadItem("cbizid");
      RefFilter.filterPsnByDept(bi, sCorpID, new String[] { null });

      // 过滤供应商参照
      bi = getBillCardPanel().getHeadItem("cproviderid");
      RefFilter.filtProvider(bi, sCorpID, null);
      // 过滤收发类型参照（出入库不一样）
      bi = getBillCardPanel().getHeadItem("cdispatcherid");
      if (getInOutFlag() == InOutFlag.IN)
        RefFilter.filtDispatch(bi, sCorpID, 0, null);
      else
        RefFilter.filtDispatch(bi, sCorpID, 1, null);
      // 表头发运地址:不用自动检查，返回名称。
      if (getBillCardPanel().getBodyItem("vreceiveaddress") != null
          && getBillCardPanel().getBodyItem("vreceiveaddress")
              .getComponent() != null) {
        nc.ui.pub.beans.UIRefPane vdlvr = (nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem("vreceiveaddress").getComponent();
        vdlvr.setAutoCheck(false);

        filterVdiliveraddressRef(true, -1);

      }
      // 过滤成本对象参照
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
      // // 过滤计量器具
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
   * 此处插入方法说明。 功能：得到对应单参照 参数： 返回： 例外： 日期：(2001-7-18 10:54:47)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * @return nc.ui.ic.pub.corbillref.ICCorBillRef
   */
  public nc.ui.ic.pub.corbillref.ICCorBillRefPane getICCorBillRef() {
    if (m_aICCorBillRef == null) {
      m_aICCorBillRef = new nc.ui.ic.pub.corbillref.ICCorBillRefPane(this);
      m_aICCorBillRef.setReturnCode(true);
      m_aICCorBillRef.setMultiSelectedEnabled(true);
      //修改人：刘家清 修改时间：2008-6-2 下午02:15:48 修改原因：对应入库单参照界面也得进行小数位等处理。
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
   * 此处插入方法说明。 创建日期：(2001-7-11 下午 11:19)
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
   * 如果用户手工修改批次号，则查库，正确带出失效日期及对应单据号，不正确，清空。 创建者：张欣 功能： 参数： 返回： 例外：
   * 日期：(2001-6-14 10:25:33) 修改日期，修改人，修改原因，注释标志：
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
   * 是否固定换算率
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
   * 当用户选择批次号后，自动带出，失效日期与对应单句号，单据类型。 创建者：张欣 功能： 参数： 返回： 例外： 日期：(2001-6-13
   * 17:38:31) 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void pickupLotRef(String colname) {
    String s = colname;
    // 批次号参照带出失效日期和对应单据号及对应单据类型

    String sbatchcode = null;
    int iSelrow = getBillCardPanel().getBillTable().getSelectedRow();
    if (s == null) {
      return;
    }
    if (s.equals("vbatchcode")) {
      // 判断当批次号参数为空时,应该返回;
      // if(arytemp[0]==null||arytemp[3]==null){
      // return;
      // }

      sbatchcode = (String) getBillCardPanel().getBodyValueAt(iSelrow,
          "vbatchcode");

      if (sbatchcode != null && sbatchcode.trim().length() > 0) {
        nc.vo.scm.ic.bill.InvVO voInv = getM_voBill().getItemInv(
            iSelrow);
        // /辅单位
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
          // 该批次的失效日期
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
            // 对应单据号
            getBillCardPanel()
                .setBodyValueAt(
                    getLotNumbRefPane().getRefBillCode() == null ? ""
                        : getLotNumbRefPane()
                            .getRefBillCode(),
                    iSelrow, IItemKey.CORRESCODE);
          } catch (Exception e) {
          }
          try {
            // 对应单据类型
            getBillCardPanel()
                .setBodyValueAt(
                    getLotNumbRefPane().getRefBillType() == null ? ""
                        : getLotNumbRefPane()
                            .getRefBillType(),
                    iSelrow, "ccorrespondtype");
          } catch (Exception e) {
          }
          try {
            // 对应单据表头OID
            // 单据模板库中表体位置两个不显示列ccorrespondhid,ccorrespondbid,以保存带出的对应表头，表体OID
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
            // 对应单据表体OID
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
          // //生产日期
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

        // /自由项
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

        // 同步改变m_voBill
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
        // //清空表体所有失效日期
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // IItemKey.CORRESCODE);
        // //清空表体所有对应单据号
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // "ccorrespondtype");
        // //清空表体所有对应单据类型
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // "ccorrespondhid");
        // //清空表体所有对应单据表头OID
        // getBillCardPanel().setBodyValueAt("", iSelrow,
        // "ccorrespondbid");
        // //清空表体所有对应单据表体OID
        // getBillCardPanel().setBodyValueAt("", iSelrow, "scrq");
        // //清空表体所有失效日期
        // //同步改变m_voBill
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
   * 创建者：王乃军 功能：单据编辑后处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  abstract protected void afterBillEdit(nc.ui.pub.bill.BillEditEvent e);

  /**
   * 创建者：王乃军 功能：表体行列选择改变 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  abstract protected void afterBillItemSelChg(int iRow, int iCol);

  /**
   * 创建者：王乃军 功能：库存组织改变事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void afterCalbodyEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterCalbodyEdit(e);

  }

  /**
   * 创建者：余大英 功能：货位修改事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void afterSpaceEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterSpaceEdit(e);

  }

  /**
   * 创建者：王乃军 功能：单据表体编辑事件前触发处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */

  abstract public boolean beforeBillItemEdit(nc.ui.pub.bill.BillEditEvent e);

  /**
   * 创建者：王乃军 功能：表体行列选择改变 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  abstract protected void beforeBillItemSelChg(int iRow, int iCol);
  
  /**
   * 
   * 获得存货参照的过滤字符串
   * <p>
   * @return 存货参照过滤字符串
   * <p>
   * @author duy
   * @time 2008-7-17 下午03:02:11
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
   * 获得仓库参照的过滤条件
   * <b>参数说明</b>
   * @param pk_calbody 库存组织的PK
   * @return 仓库过滤条件的数组
   * <p>
   * @author duy
   * @time 2008-8-19 下午04:48:46
   */
  public String[] getFilterWhString(String pk_calbody) {
    // 出入库单的仓库参照中，过滤掉直运仓，对于系统补单的，直运仓不显示也没有问题。
    //修改人：刘家清 修改时间：2008-8-15 上午11:05:57 修改原因：直运业务类型的单据仓库只显示直运仓。
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
   * UAP提供的编辑前控制
   * 
   * @param value
   * @param row
   * @param itemkey
   * @return
   * 
   */
  public boolean isCellEditable(
      boolean value/* BillModel的isCellEditable的返回值 */,
      int row/* 界面行序号 */, String itemkey/* 当前列的itemkey */) {

    return getEditCtrl().isCellEditable(value, row, itemkey);

  }

  /**
   * beforeEdit 方法注解。[处理表头编辑前事件]
   */
  public boolean beforeEdit(nc.ui.pub.bill.BillItemEvent e) {
    if(!getPluginProxy().beforeEdit(e))
      return false;
    
    return getEditCtrl().beforeEdit(e);

  }

  /**
   * 创建者：王乃军 功能：单据编辑事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   */
  public boolean beforeEdit(nc.ui.pub.bill.BillEditEvent e) {
    boolean bret = getEditCtrl().beforeEdit(e);
    if(isLineCardEdit()){
      bret = isCellEditable(bret, e.getRow(), e.getKey());
    }
    
    //二次开发扩展
    if(!getPluginProxy().beforeEdit(e))
      bret = false;
    
    return bret;

  }

  /**
   * 计算合计。 创建日期：(2001-10-24 16:33:58)
   * 
   * @return nc.vo.pub.lang.UFDouble
   * @param sItemKey
   *            java.lang.String
   */
  public nc.vo.pub.lang.UFDouble calcurateTotal(java.lang.String sItemKey) {
    UFDouble dTotal = new UFDouble(0.0);

    for (int i = 0; i < getBillCardPanel().getRowCount(); i++) {
      //修改人：刘家清 修改时间：2009-2-16 下午05:00:35 修改原因：赠品不参与金额合计。
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
   * 根据换算率计算毛重和批重。 创建日期：(2005-04-05 16:33:58)
   * 
   * @return void
   * @param hsl
   *            UFDouble
   */
  public void calOtherNumByHsl(String sMainNum, String sAstNum, int rownum,
      UFDouble hsl) {
    if (hsl == null)
      return;

    // 以下是皮重毛重
    UFDouble nMain = null;
    UFDouble nAst = null;

    Object oMain = getBillCardPanel().getBodyValueAt(rownum, sMainNum);
    Object oAst = getBillCardPanel().getBodyValueAt(rownum, sAstNum);

    if (oMain != null)
      nMain = new UFDouble(oMain.toString().trim());
    if (oAst != null)
      nAst = new UFDouble(oAst.toString().trim());

    /* 辅计量：换算率；无论固定，变动换算率，按辅数量＊换算率来重新计算主数量 */
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
   * 创建者：王乃军 功能：抽象方法：保存前的VO检查 参数：待保存单据 返回： 例外： 日期：(2001-5-24 下午 5:17)
   * 修改日期，修改人，修改原因，注释标志：
   */
  abstract protected boolean checkVO(GeneralBillVO voBill);

  /**
   * 创建者：王乃军 功能：清空列表和表单界面 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 来源单据是转库单时的界面控制方法 功能： 参数： 返回： 例外： 日期：(2001-10-19 09:43:22)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void ctrlSourceBillButtons() {
    ctrlSourceBillButtons(true);
  }

  /**
   * 来源单据是转库单时的界面控制方法 功能： 参数： 返回： 例外： 日期：(2001-10-19 09:43:22)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void ctrlSourceBillUI() {
    ctrlSourceBillUI(true);
  }

  /**
   * 此处插入方法说明。 作者：余大英 创建日期：(2001-6-21 15:11:22)
   * 
   * @param int
   *            bill
   */
  protected void dispSpace(int bill) {
    // 查询当前表单的表体货位
    // ydy
    if (getM_alListData() == null)
      return;

    if (getBillCardPanel().getBodyItem("vspacename") == null
        || !(getBillCardPanel().getBodyItem("vspacename").isShow())) {
      if (getBillListPanel().getBodyTable().getRowCount() > 0)
//        deleted by lirr 2009-04-16 若先选第2行再从第一行多选则只能选中第一行问题
        //getBillListPanel().getBodyTable().setRowSelectionInterval(0, 0);
      return;
    }

    GeneralBillVO voBill = (GeneralBillVO) getM_alListData().get(bill);
    appendLocator(voBill);

    setListBodyData(voBill.getItemVOs());

    // 选中表体第一行
    // 表体不可能为空
//    deleted by lirr 2009-04-16 若先选第2行再从第一行多选则只能选中第一行问题
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
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2001-12-5 16:27:59) 修改日期，修改人，修改原因，注释标志：
   * 
   * @param row
   *            int
   * @param formulas
   *            java.lang.String[]
   */
  protected void execEditFomulas(int row, String itemKey) {
    /** 强制执行表体行，数量列的公式 */
    nc.ui.pub.bill.BillItem bi = getBillCardPanel().getBodyItem(itemKey);
    if (bi != null) {
      String[] formulas = bi.getEditFormulas();
      getBillCardPanel().execBodyFormulas(row, formulas);
    }
  }

  /**
   * 类型说明：
   * 
   * 执行表头、表尾的公式们。
   * 
   * 创建日期：(2002-11-6 13:23:02) 作者： 修改日期： 修改人： 修改原因： 算法说明：
   */
  public void execHeadTailFormulas() {

    getBillCardPanel().execHeadTailLoadFormulas();

  }

  /**
   * 创建者：王乃军 功能：滤掉不需要的条件字段，然后返回之 参数： 返回： 例外： 日期：(2001-8-17 13:13:51)
   * 修改日期，修改人，修改原因，注释标志：
   */
  public void filterCondVO2(nc.vo.pub.query.ConditionVO[] voaCond,
      String[] saItemKey) {
    if (voaCond == null || saItemKey == null)
      return;
    // 暂存器
    int j = 0;
    // 数组长
    int len = saItemKey.length;
    for (int i = 0; i < voaCond.length; i++)
      // 找
      if (voaCond[i] != null)
        for (j = 0; j < len; j++) {
          if (saItemKey[j] != null
              && voaCond[i].getFieldCode() != null
              && saItemKey[j].trim().equals(
                  voaCond[i].getFieldCode().trim())) {
            // 补上对括弧的保留
            voaCond[i].setFieldCode("1");
            voaCond[i].setOperaCode("=");
            voaCond[i].setDataType(1);
            voaCond[i].setValue("1");
          }
        }
  }

  /**
   * 过滤单据参照 创建者：张欣 功能：过滤成本对象 参数： 返回： 例外： 日期：(2001-7-17 10:33:20)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void filterCostObject() {
    try {
      // 根据库存组织过滤
      String sCalbodyID = null;
      if (getBillCardPanel().getHeadItem("pk_calbody") != null
          && getBillCardPanel().getBodyItem(IItemKey.COSTOBJECTNAME) != null) {
        sCalbodyID = (String) getBillCardPanel().getHeadItem(
            "pk_calbody").getValueObject();
        UIRefPane ref = (nc.ui.pub.beans.UIRefPane) getBillCardPanel()
            .getBodyItem(IItemKey.COSTOBJECTNAME).getComponent();
        // 修改人：刘家清 修改日期：2008-1-25上午11:26:58 修改原因：材料出库单成本对象参照效率问题
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
          // 修改人：刘家清 修改日期：2008-1-25上午11:26:58
          // 修改原因：材料出库单成本对象参照效率问题，因为打开参照时，如果参照已经有值的话，平台会调用匹配（两次），默认是只有PK不加WherePart，当有业务部门新加的表时，会导致SQL效率特别底，所以要加上WherePart使用到PK。
          ref.getRefModel().setMatchPkWithWherePart(true);
        }
      }
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }
  }

  /**
   * 如果是固定货位的存货，过滤出存货的固定货位 作者：余大英 创建日期：(2001-7-6 16:53:38)
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
   * 创建者：王乃军 功能：签字成功后处理 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void freshAfterSignedOK(String sBillStatus) {
    try {
      GeneralBillVO voBill = null;
      // 刷新列表形式
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null) {
        // 这里不能clone(),改变了m_voBill同时改变m_alListData???
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());

        // 把当前登录人设置到vo
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
        // 重置列表界面
        getM_alListData().set(getM_iLastSelListHeadRow(), voBill);

        // 刷新列表形式
        getBillListPanel().getHeadBillModel().setBodyRowVO(
            voBill.getParentVO(), getM_iLastSelListHeadRow());
      }
      // m_voBill刷新
      // 把当前登录人设置到vo
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

      // 把当前登录人名称显示到界面
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
      // 刷新列表形式
      // setListHeadData();
      // selectListBill(getM_iLastSelListHeadRow());
      // 设置按钮状态,签字不可用，取消签字可用
      // 已签字，所以设置按钮状态,签字不可用，取消签字可用
      setButtonStatus(false);

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(true);
      // 不可删、改
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
   * 创建者：王乃军 功能：刷新计划价 参数： 返回： 例外： 日期：(2001-5-8 19:08:05) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void freshPlanprice(ArrayList alNewPlanprice) {
    try {
      // 行数
      int iRowCount = getBillCardPanel().getRowCount();
      if (alNewPlanprice == null || alNewPlanprice.size() < iRowCount
          || getM_voBill() == null) {
        SCMEnv.out("alallinv nvl");
        return;
      }
      // 按行取，不管是否为空
      nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
      // 数量，用于计算金额
      Object oTempNum = null;
      UFDouble dNum = null;
      UFDouble dMny = null;
      GeneralBillItemVO[] voaBillItem = getM_voBill().getItemVOs();
      for (int row = 0; row < iRowCount; row++) {
        bmBill.setValueAt(alNewPlanprice.get(row), row,
            IItemKey.NPLANNEDPRICE);
        // 需要同步m_voBill
        if (alNewPlanprice.get(row) != null)
          voaBillItem[row].setNplannedprice((UFDouble) alNewPlanprice
              .get(row));
        else
          voaBillItem[row].setNplannedprice(null);
        oTempNum = bmBill.getValueAt(row, getEnvironment()
            .getNumItemKey());
        // 同时有数量和单价时，才计算
        if (oTempNum != null && alNewPlanprice.get(row) != null) {
          dNum = (UFDouble) oTempNum;
          dMny = dNum.multiply((UFDouble) alNewPlanprice.get(row));
        } else
          dMny = null;

        bmBill.setValueAt(dMny, row, IItemKey.NPLANNEDMNY);
        // 需要同步m_voBill
        voaBillItem[row].setNplannedmny(dMny);

      }

    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
    }
  }

  /**
   * 创建者：王乃军 功能： 参数： 返回： 例外： 日期：(2001-11-23 18:05:45) 修改日期，修改人，修改原因，注释标志：
   * 
   * @return java.lang.String
   */
  public java.lang.String getBillTypeCode() {
    return getBillType();
  }

  /**
   * 创建者：王乃军 功能：得到查询对话框 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
//      // 以下为对公司参照的初始化
//      ArrayList alCorpIDs = new ArrayList();
//      alCorpIDs.add(getEnvironment().getCorpID());
//      ivjQueryConditionDlg.initCorpRef("head.pk_corp", getEnvironment()
//          .getCorpID(), alCorpIDs);
//      // 以下为对参照的初始化
//      ivjQueryConditionDlg.initQueryDlgRef();
//
//      // 隐藏常用条件
//      ivjQueryConditionDlg.hideNormal();
//      // 条码是否关闭查询条件body.bbarcodeclose
//      ivjQueryConditionDlg.setCombox("body.bbarcodeclose",
//          new String[][] {
//              { " ", " " },
//              {
//                  "N",
//                  nc.ui.ml.NCLangRes.getInstance()
//                      .getStrByID("SCMCOMMON",
//                          "UPPSCMCommon-000108") /*
//                                       * @res
//                                       * "否"
//                                       */},
//              {
//                  "Y",
//                  nc.ui.ml.NCLangRes.getInstance()
//                      .getStrByID("SCMCOMMON",
//                          "UPPSCMCommon-000244") /*
//                                       * @res
//                                       * "是"
//                                       */} });
//
//      // 设置下拉框显示
//      ivjQueryConditionDlg.setCombox("qbillstatus", new String[][] {
//          {
//              "2",
//              nc.ui.ml.NCLangRes.getInstance().getStrByID(
//                  "4008bill", "UPP4008bill-000313") /*
//                                     * @res
//                                     * "制单"
//                                     */},
//          {
//              "3",
//              nc.ui.ml.NCLangRes.getInstance().getStrByID(
//                  "40080402", "UPT40080402-000013") /*
//                                     * @res
//                                     * "签字"
//                                     */},
//          {
//              "A",
//              nc.ui.ml.NCLangRes.getInstance().getStrByID(
//                  "SCMCOMMON", "UPPSCMCommon-000217") /*
//                                     * @res
//                                     * "全部"
//                                     */} });
//      // set default logon date into query condiotn dlg
//      // modified by liuzy 2008-03-28 5.03需求，单据查询增加起止日期
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
//      // 查询对话框显示打印次数页签。
//      ivjQueryConditionDlg.setShowPrintStatusPanel(true);
//
//      // 修改自定义项目 add by hanwei 2003-12-09
//      DefSetTool.updateQueryConditionClientUserDef(ivjQueryConditionDlg,
//          getEnvironment().getCorpID(), ICConst.BILLTYPE_IC,
//          "head.vuserdef", "body.vuserdef");
//      getConDlginitself(ivjQueryConditionDlg);
//
//      // 过滤库存组织，仓库,废品库,客户,供应商的数据权限，部门，业务员
//      // zhy2005-06-10 客户和供应商不需要在普通单上过滤，（客户在销售出库单上过滤，供应商在采购入库单上过滤）
//      // zhy2007-02-12 V51新需求:3、
//      // 客商、地区分类、库存组织、项目受数据权限控制，部门、仓库、存货分类、存货受已定义的库管员匹配纪录的控制；
//      /**
//       * 库管员:head.cwhsmanagerid 客商:head.cproviderid 库存组织:head.pk_calbody
//       * 仓库:head.cwarehouseid,head.cwastewarehouseid 项目:body.cprojectid
//       * 部门:head.cdptid 存货分类:invcl.invclasscode 存货:inv.invcode
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
//      // zhy205-05-19 加可还回数量条件
//      // 借出单
//      ivjQueryConditionDlg
//          .setCombox(
//              "coalesce(body.noutnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
//              new Integer[][] { { new Integer(0), new Integer(0) } });
//      // 借入单
//      ivjQueryConditionDlg
//          .setCombox(
//              "coalesce(body.ninnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
//              new Integer[][] { { new Integer(0), new Integer(0) } });
//
//    }
//    return ivjQueryConditionDlg;
//  }

  /**
   * 创建者：余大英 功能：得到当前已录入的存货ID 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */

  public ArrayList getCurInvID() {
    // 存货ID
    ArrayList alAllInv = new ArrayList();
    // 行数
    int iRowCount = getBillCardPanel().getRowCount();
    // 按行取，不管是否为空
    nc.ui.pub.bill.BillModel bmBill = getBillCardPanel().getBillModel();
    for (int row = 0; row < iRowCount; row++)
      alAllInv.add(bmBill.getValueAt(row, IItemKey.INVID));
    return alAllInv;
  }

  /**
   * 创建者：余大英 功能：得到当前已录入的存货ID 参数： //存货ID 返回：是否有存货 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
    // 行数
    int iRowCount = getBillCardPanel().getRowCount();
    // 按行取，不管是否为空
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
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-10-30 15:06:35) 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：构造外设输入控制类 参数： 返回： 例外： 日期：(2001-11-24 12:15:42)
   * 修改日期，修改人，修改原因，注释标志：
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
//   * 创建者：王乃军 功能：得到用户输入的额外查询条件 参数：//查询条件数组 返回： 例外： 日期：(2001-5-9 9:23:32)
//   * 修改日期，修改人，修改原因，注释标志：
//   * 
//   * 
//   * 
//   * 
//   */
//  public String getExtendQryCond(nc.vo.pub.query.ConditionVO[] voaCond) {
//    // 单据状态条件,缺省无
//    String sBillStatusSql = " (1=1) ";
//    try {
//      // -------- 查询条件字段 itemkey ---------
//      String sFieldCode = null;
//      // 从条件中查找最大最小日期
//      // 单据状态
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
//      // 缺省是A
//      if ("2".equals(sBillStatus)) // 自由
//        sBillStatusSql = " fbillflag="
//            + nc.vo.ic.pub.bill.BillStatus.FREE;
//      else if ("3".equals(sBillStatus)) // 签字的
//        sBillStatusSql = " ( fbillflag="
//            + nc.vo.ic.pub.bill.BillStatus.SIGNED
//            + " OR fbillflag="
//            + nc.vo.ic.pub.bill.BillStatus.AUDITED + ") ";
//
//      // 退库查询 add by hanwei 2003-10-10
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
//      // 去掉freplenishflag 是否退库
//      String saItemKey[] = new String[] { "qbillstatus", "freplenishflag" };
//      filterCondVO2(voaCond, saItemKey);
//      // 其他条件
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
   * 创建日期：(2003-3-4 17:13:59) 作者：韩卫 修改日期： 修改人： 修改原因： 方法说明：
   * 
   * @return nc.ui.ic.pub.bill.InvoInfoBYFormula
   */
  public InvoInfoBYFormula getInvoInfoBYFormula() {
    if (m_InvoInfoBYFormula == null)
      m_InvoInfoBYFormula = new InvoInfoBYFormula(getCorpPrimaryKey());
    return m_InvoInfoBYFormula;
  }

  /**
   * 去存货管理档案定义出库是否跟踪入库的参数 功能： 参数：存货VO 返回：boolean 例外： 日期：(2002-05-20 19:55:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected boolean getIsInvTrackedBill(InvVO invvo) {
    if (invvo != null && invvo.getOuttrackin() != null) {
      return invvo.getOuttrackin().booleanValue();

    } else {
      return false;
    }
  }

  /**
   * 返回 FreeItemRefPane1 特性值。
   * 
   * @return nc.ui.ic.pub.freeitem.FreeItemRefPane
   */
  /* 警告：此方法将重新生成。 */
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
   * 此处插入方法说明。 创建日期：(2001-9-19 16:02:07)
   * 
   * @return nc.ui.ic.ic101.OrientDialog
   * @author:余大英
   */
  public nc.ui.ic.pub.orient.OrientDialog getOrientDlg() {
    if (m_dlgOrient == null) {
      m_dlgOrient = new nc.ui.ic.pub.orient.OrientDialog(this);

      // ivjQueryConditionDlg.setPKCorp(getEnvironment().getCorpID());

    }
    return m_dlgOrient;
  }

  /**
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-10-30 15:06:35) 修改日期，修改人，修改原因，注释标志：
   */
  protected nc.ui.pub.print.PrintEntry getPrintEntry() {
    if (null == m_print) {
      //m_print = new nc.ui.pub.print.PrintEntry(null, null);
      //modified by lirr 2009-05-22 修改原因：联查打印 不能直接关闭问题
      m_print = new nc.ui.pub.print.PrintEntry(this, null);
      m_print.setTemplateID(getEnvironment().getCorpID(),
          getFunctionNode(), getEnvironment().getUserID(), null);
    }
    return m_print;
  }

  protected nc.ui.pub.print.PrintEntry getPrintEntryNew() {
//    nc.ui.pub.print.PrintEntry pe = new nc.ui.pub.print.PrintEntry(null,
//        null);
//    modified by lirr 2009-05-22 修改原因：联查打印 不能直接关闭问题
    
    nc.ui.pub.print.PrintEntry pe = new nc.ui.pub.print.PrintEntry(this,
        null);
    pe.setTemplateID(getEnvironment().getCorpID(), getFunctionNode(),
        getEnvironment().getUserID(), null);
    return pe;
  }

  /**
   * 创建者：zhx 功能：返回用户选择的业务类型 参数： 返回： 例外： 日期：(2002-12-10 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 类型说明： 创建日期：(2002-11-18 15:49:54) 作者：余大英 修改日期： 修改人： 修改原因： 算法说明：
   * 
   * @return java.util.ArrayList
   */
  public ArrayList getSelectedBills() {

    ArrayList albill = new ArrayList();
    int iSelListHeadRowCount = getBillListPanel().getHeadTable()
        .getSelectedRowCount();
    if (iSelListHeadRowCount <= 0) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "common", "UCH003")/* @res "请选择要处理的数据！" */);
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

    // 查询表体数据
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
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-10-23 10:31:53) 修改日期，修改人，修改原因，注释标志：
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
   * 得到m_voBill中的单据类型编码 功能： 参数： 返回： 例外： 日期：(2001-10-12 13:18:06)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected String getSourBillTypeCode() {
    GeneralBillVO voBill = null;
    // 列表形式和表单形式不同
    if (getM_iCurPanel() == BillMode.List
        && getM_iLastSelListHeadRow() >= 0 && getM_alListData() != null
        && getM_alListData().size() > getM_iLastSelListHeadRow()
        && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
      // 先读过来
      voBill = (GeneralBillVO) getM_alListData().get(
          getM_iLastSelListHeadRow());
    else
      // 直接用m_vo
      voBill = getM_voBill();

    if (voBill != null && voBill.getItemCount() > 0)
      return (String) voBill.getItemValue(0, "csourcetype");
    else
      return null;
  }

  /**
   * 创建者：王乃军 功能：查指定序号单据的货位/序列号数据,用于打印货位序列号名细 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
    // 修改人：刘家清 修改日期：2007-10-29下午03:12:03
    // 修改原因：只有在卡片状态下，才用考虑到排序后打印顺序问题，来取getM_voBill()
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
        // 日志异常
        nc.vo.scm.pub.SCMEnv.error(e);
        showErrorMessage(e.getMessage());
      }
    }
    return m_buttonManager;
  }

  /**
   * 
   * 方法功能描述：按钮的初始化。
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @deprecated
   * @author duy
   * @time 2007-2-5 下午02:56:08
   */
  protected void initButtons() {
  }

  /**
   * 
   * /** 创建者：王乃军 功能：初始化按钮。 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志： 说明 by hanwei 2003-10-10
   * m_vTopMenu.insertElementAt(m_vboSendback, 0); 是用来查入指定的菜单位置
   * 父菜单是在initButtonsData()方法里面初试化 子类里面如果有
   * nc.ui.ic.ic201.ClientUI.setButtonsStatus(int) super.initButtonsData();
   * 要去掉super. 由于setButtonsStatus重复调用initButtonsData()，
   * 所以父菜单对象必须在本方法中声明：m_boSendback = new ButtonObject("退货", "退货", 0);
   * 否则会导致子菜单重复增加。
   * <p>
   * <b>examples:</b>
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author duy
   * @deprecated
   * @time 2007-2-2 上午11:57:14
   */
  protected void initButtonsData() {
  }

  /**
   * 此处插入方法说明。 功能：初始化 外设录入 按钮。 参数： 返回： 例外： 日期：(2002-9-28 10:19:23)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * @deprecated
   */
  protected void initDevInputButtons() {
  }

  /**
   * 创建者：王乃军 功能：初始化系统参数 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  abstract protected void initPanel();

  /**
   * 创建者：王乃军 功能：读系统参数 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void initSysParam() {
    // m_sTrackedBillFlag = "N";
    // m_sSaveAndSign = "N";
    try {
      // 库存参数表IC028:出库时是否指定入库单;批次参照跟踪是否到单据号.
      // IC010 是否使用删除方案。
      // IC060 是否保留最初的制单人。
      // IC030 单据号是否允许手工输入

      // 参数编码 含义 缺省值
      // BD501 数量小数位 2
      // BD502 辅计量数量小数位 2
      // BD503 换算率 2
      // BD504 存货成本单价小数位 2
      // BD505 采购/销售单价小数位 2

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

      // 传入的参数
      ArrayList alAllParam = new ArrayList();
      // 查参数的必须数据
      ArrayList alParam = new ArrayList();
      alParam.add(getEnvironment().getCorpID()); // 第一个是公司
      alParam.add(saParam); // 待查的参数
      alAllParam.add(alParam);
      // 查用户对应公司的必须参数
      alAllParam.add(getEnvironment().getUserID());

      /*ArrayList alRetData = null;
      alRetData = (ArrayList) ICReportHelper.queryInfo(new Integer(
          QryInfoConst.INIT_PARAM), alAllParam);*/
      String[] saParamValue = nc.ui.ic.pub.tools.GenMethod.getSysParams(getEnvironment().getCorpID(), saParam);
      // 目前读两个。
      if (saParamValue == null || saParamValue.length <= 0) {
        showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000045")/* @res "初始化参数错误！" */);
        return;
      }
      // 读回的参数值
      //String[] saParamValue = (String[]) alRetData.get(0);
      
      // 追踪到单据参数,默认设置为"N"
      if (saParamValue != null
          && saParamValue.length >= alAllParam.size()) {
        // if (saParamValue[0] != null)
        // m_sTrackedBillFlag =
        // saParamValue[0].toUpperCase().trim();
        // 是否保存即签字。默认设置为"N"
        // if (saParamValue[0] != null)
        // m_sSaveAndSign = saParamValue[0].toUpperCase().trim();
        // BD501 数量小数位 2
        if (saParamValue[0] != null)
          m_ScaleValue.setNumScale(Integer.parseInt(saParamValue[0]));
        // BD502 辅计量数量小数位 2
        if (saParamValue[1] != null)
          m_ScaleValue.setAssistNumScale(Integer
              .parseInt(saParamValue[1]));
        // BD503 换算率 2
        if (saParamValue[2] != null)
          m_ScaleValue.setHslScale(Integer.parseInt(saParamValue[2]));
        // BD504 存货成本单价小数位 2
        if (saParamValue[3] != null)
          m_ScaleValue.setPriceScale(Integer
              .parseInt(saParamValue[3]));
        // BD301 本币小数位
        if (saParamValue[4] != null)
          m_ScaleValue.setMnyScale(Integer.parseInt(saParamValue[4]));
        // IC060 是否保留最初制单人 'N' 'Y'
        // if (saParamValue[7] != null)
        // m_sRemainOperator = saParamValue[7].toUpperCase().trim();
        // IC030 是否允许编辑单据号 'N' 'Y'
        if (saParamValue[5] != null
            && "Y".equalsIgnoreCase(saParamValue[5].trim()))
          m_bIsEditBillCode = true;

        /*
         * // IC062 是否保存条码 if (saParamValue[6] != null &&
         * "Y".equalsIgnoreCase(saParamValue[6].trim())) m_bBarcodeSave =
         * true; else m_bBarcodeSave = false;
         */

        // m_bBarcodeSave = true;
        // IC063 条码不完整是否保存条码
        if (saParamValue[6] != null
            && "Y".equalsIgnoreCase(saParamValue[6].trim()))
          m_bBadBarcodeSave = true;
        else
          m_bBadBarcodeSave = false;

        // IC0641 条码分组显示的行数
        if (saParamValue[7] != null
            && saParamValue[7].trim().length() > 0)
          m_iBarcodeUIColorRow = Integer.parseInt(saParamValue[7]
              .trim());

        // IC0642 条码分组显示的颜色
        if (saParamValue[8] != null
            && saParamValue[8].trim().length() > 0)
          m_sColorRow = saParamValue[8].trim();

        // IC050 存货参照是否按照仓库过滤
        if (saParamValue[9] != null
            && NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000004")/*仓库*/.equalsIgnoreCase(saParamValue[9].trim()))
          m_isWhInvRef = true;
        else
          m_isWhInvRef = false;

        // BD505 采购/销售单价小数位 2
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
      // 系统起用日期
      if (null == m_sStartDate)
        m_sStartDate = getEnvironment().getLogDate();

/*      if (alRetData.size() > 2) {
        m_sStartDate = (String) alRetData.get(2);

      }*/

      //

      // 修改人：刘家清 修改日期：2007-9-5上午10:22:36
      // 修改原因：增加'可开票数量','累计途损数量','累计开票数量','累计对冲数量','可对冲数量'
      // modified by liuzy 2007-11-28 增加表尾数量字段
      // modified by chennn 2009-07-14 增加'累计出库签收数量'
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
   * 创建者：王乃军 功能：如果当前选中的单据不是和本节点相同的单据类型(如借入单上查出的期初单)，不能删除.
   * 
   * 参数： 返回： 例外： 日期：(2001-11-23 18:11:18) 修改日期，修改人，修改原因，注释标志：
   */
  protected boolean isCurrentTypeBill() {
    // 如果不是和本节点相同的单据类型(如借入单上查出的期初单)，不能删除.
    try {
      // 当前选中的单据
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
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-06-03 11:32:25) 修改日期，修改人，修改原因，注释标志：
   */
  protected boolean isDispensedBill(GeneralBillVO gvo) {
    if (gvo == null) {

      // 列表形式和表单形式不同
      if (getM_iCurPanel() == BillMode.List
          && getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        // 先读过来
        gvo = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
      else
        // 直接用m_vo
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
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-06-03 11:32:25) 修改日期，修改人，修改原因，注释标志：
   */
  protected boolean isDispensedBill(GeneralBillVO gvo, int rownum) {
    if (gvo == null) {

      // 列表形式和表单形式不同
      if (getM_iCurPanel() == BillMode.List
          && getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        // 先读过来
        gvo = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
      else
        // 直接用m_vo
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
   * 创建者：王乃军 功能： 参数： 返回： 例外： 日期：(2001-11-24 12:14:35) 修改日期，修改人，修改原因，注释标志：
   * 
   * @return boolean
   */
  public boolean isNeedBillRef() {
    if (m_bNeedBillRef && isNeedBillRefWithBillType())
      return true;
    else
      return false;
  }
  //修改人：刘家清 修改时间：2009-1-8 上午10:07:02 修改原因：只有一些单据才可以使用此方法。
  public boolean isNeedBillRefWithBillType() {
    if (null != getBillTypeCode() &&
        (ScmConst.m_saleOut.equals(getBillTypeCode()) || ScmConst.m_Pickup.equals(getBillTypeCode())
            ||ScmConst.m_purchaseIn.equals(getBillTypeCode()) ||ScmConst.m_consignMachiningIn.equals(getBillTypeCode())))
      return true;
    else
      return false;
  }

  /**
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-06-11 9:22:38) 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：张欣 功能：当前选中行是否能序列号分配，要考虑列表/表单下的选中 参数：//当前选中的行 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public boolean isSNmgt(int iCurSelBodyLine) {

    if (iCurSelBodyLine >= 0) {
      InvVO voInv = null;
      // 读表体vo,区分列表下还是表单下
      // 表单形式下
      if (BillMode.Card == getM_iCurPanel()) {
        if (getM_voBill() == null) {
          SCMEnv.out("bill null E.");
          return false;
        }
        voInv = getM_voBill().getItemInv(iCurSelBodyLine);
      } else // 列表形式下
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

  protected boolean m_bRefBills = false;// 是否参照生成多张单据

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
      setBillRefResultVO(sBizType, vos);// 老的
      return;
    }
    m_sBizTypeRef = sBizType;
    
    m_alRefBillsBak = getM_alListData();

    // 处理行号
    nc.ui.scm.pub.report.BillRowNo.setVOsRowNoByRule(vos, getBillType(),
        IItemKey.CROWNO);
    // 设置数据到列表
    setDataOnList(vos);

  }

  protected void setRefBillsFlag(boolean bRefBills) {
    m_bRefBills = bRefBills;
  }

  /**
   * 创建者：王乃军 功能：查询单据的表体，并把结果置到arraylist 参数： int iaIndex[],单据在alAlldata中的索引。
   * String saBillPK[]单据pk数组 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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

      // 使用公式查询时,仅查出相关列PK
      voCond.setIntParam(0, GeneralBillVO.QRY_ITEM_ONLY_PURE);

      voCond.setParam(0, saBillPK);
      // 启用进度条
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
      SCMEnv.out("1存货公式解析成功！");/*-=notranslate=-*/

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
   * 创建者：王乃军 功能：列表形式下打印前， 需要的化读剩余的单据表体。 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void queryLeftItem(ArrayList alListData) throws Exception {
    // -------------
    if (alListData == null || alListData.size() == 0)
      return;
    int iIntegralBillNum = 0; // 完整单据的数量
    GeneralBillVO voBill = null;
    GeneralBillItemVO[] voaItem = null;
    for (int bill = 0; bill < alListData.size(); bill++)
      if (alListData.get(bill) != null) {
        voBill = (GeneralBillVO) getM_alListData().get(bill);
        voaItem = voBill.getItemVOs();
        if (voaItem != null && voaItem.length > 0)
          iIntegralBillNum++;
      }
    // 还有没有表体的单据
    if (iIntegralBillNum < alListData.size()) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000054")/*
                             * @res
                             * "正在准备打印数据，请稍候..."
                             */);
      if ((double) iIntegralBillNum / alListData.size() < 0.60 && getQryDlgHelp().getVoLastQryCond()!=null) {
        // 如果大于阀值的单据未读出，干脆重新查询
        QryConditionVO voCond = (QryConditionVO) getQryDlgHelp().getVoLastQryCond()
            .clone();
        // 查询完整单据
        voCond.setIntParam(0, GeneralBillVO.QRY_FULL_BILL);
        SCMEnv.out("重新查所有数据，准备打印。。。");/*-=notranslate=-*/
        setM_alListData((ArrayList<GeneralBillVO>) GeneralBillHelper.queryBills(
            getBillType(), voCond));
      } else { // 否则只读剩下的表体即可。

        // 读单据pk，用于查询，不在前面的循环读。
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
   * 创建者：王乃军 功能：重新设置存货ID,带出存货其它数据，不清批次、自由项、数量等其它数据 参数：行号，存货ID 返回： 例外：
   * 日期：(2001-5-8 19:08:05) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * (1)获得表体的行billItemVO (2)解析表体的行 (3)给界面赋予表体行 (4)特殊控件处理（是不是可以不做处理？）
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

      // 重设每一行的存货ID,辅计量
      ArrayList alBillItems = new ArrayList();
      for (int i = 0; i < voBill.getItemVOs().length; i++) {
        alBillItems.add(voaItem[i]);
      }
      SCMEnv.showTime(lTime, "resetAllInvInfo:getItems");
      lTime = System.currentTimeMillis();
      // 读存货数据条件
      getFormulaBillContainer().formulaBodys(getFormulaItemBody(),
          alBillItems);

      // 计划价格查询 add by hanwei 2004-6-30
      if (alBillItems != null && voaItem.length > 0) {
        int iLen = voaItem.length;
        InvVO[] invVOs = new InvVO[iLen];
        ArrayList<InvVO> astsoilvos = new ArrayList<InvVO>(
            invVOs.length);
        for (int i = 0; i < voaItem.length; i++) {
          // 获得GeneralBillItemVO的invVO对象
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

        // DUYONG 此处需要同时传递库存组织和仓库的ID（为了从成本域库存组织中读取计划价格）
        getInvoInfoBYFormula().getProductPrice(invVOs, sCalID, sWhID);
        IBillType billType = BillTypeFactory.getInstance().getBillType(voaItem[0].getCsourcetype());
        if (astsoilvos.size() > 0
            && voaItem[0].getCsourcetype() != null
            && billType.typeOf(ModuleCode.PO))
          getInvoInfoBYFormula().getInVoOfHSLByHashCach(
              astsoilvos.toArray(new InvVO[astsoilvos.size()]));
        SCMEnv.showTime(ITime, "getProductPrice:" + invVOs.length
            + NCLangRes.getInstance().getStrByID("generalbillclientui", "GeneralBillClientUI-000005")/*条：*/);
        for (int i = 0; i < voaItem.length; i++) {
          // 获得GeneralBillItemVO的invVO对象
          voaItem[i].setInv(invVOs[i]);
        }
      }

      SCMEnv.showTime(lTime, "resetAllInvInfo:formulaBodys");

      lTime = System.currentTimeMillis();
      GeneralBillItemVO[] voBillItems = new GeneralBillItemVO[alBillItems
          .size()];
      alBillItems.toArray(voBillItems);

      // voBill.calConvRate(); //计算换算率

      SCMEnv.showTime(lTime, "resetAllInvInfo:hsl");
      // 设置界面数据
      // 修改 by hanwei 2003-11-18 hw
      lTime = System.currentTimeMillis();
      // 处理换算率
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
            
          // 修改人：刘家清 修改日期：2008-5-12下午01:31:53
          // 修改原因：当有换算率时，如果有主数量并且辅数量为空时，就计算辅数量。
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
      // added by lirr 2009-11-14下午02:42:24 资产入资产出需要执行自定义项公式
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
      SCMEnv.showTime(lTime, "批次号加载数据时间");/*-=notranslate=-*/

      // modified by liuzy 2009-9-21 上午10:16:48 该方法（resetAllInvInfo）只在转但是调用，因此转单后统一计算合计
//      getBillCardPanel().getBillModel().setNeedCalculate(true);
//      getBillCardPanel().getBillModel().reCalcurateAll();
    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
    }
  }

  /**
   * 创建者：王乃军 功能：根据设定的按钮初始化菜单。 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   * 修改人：刘家清 修改时间：2008-12-31 上午11:29:12 修改原因：删除此方法。
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
   * 创建者：王乃军 功能：读仓库属性数据，放到m_voBill 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
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
      } // 查询需要仓库ID
      String sWhID = voBill.getHeaderValue(IItemKey.WAREHOUSE).toString()
          .trim();
      // 读仓库数据
      // 查，返回WhVO
      WhVO voWh;
      if(!hm_whid_vo.containsKey(sWhID)){
        voWh = (WhVO) GeneralBillHelper.queryInfo(new Integer(
          QryInfoConst.WH), sWhID);
        hm_whid_vo.put(sWhID, voWh);
      }
      voWh = hm_whid_vo.get(sWhID);

      if (getM_voBill() != null) { // 设置仓库属性
        getM_voBill().setWh(voWh);
        voBill.setWh(voWh);// v5
        // 设置货位按钮可用性
        setBtnStatusSpace(false);
      }

    } catch (Exception e2) {
      nc.vo.scm.pub.SCMEnv.error(e2);
      showErrorMessage(e2.getMessage());
    }
  }

  /**
   * 保存前对单据设置某些固定值。
   * 
   * @since v5
   * @author ljun 只被委外加工入库单重载，设置集采购默认值，便于采购结算
   */
  // v5
  protected void beforeSave(GeneralBillVO voBill) {

  }
  
  /**
   * 
   * 获得提交（保存）的单据动作名称
   * <p>
   * <p>
   * <b>参数说明</b>
   * @return 提交（保存）的单据动作名称
   * <p>
   * @author duy
   * @time 2008-7-17 上午09:42:34
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
   * 创建者：王乃军 功能：保存新增的单据 有错误需要以异常抛出，影响主流程
   * 
   * 参数：完整单据 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   */
  protected void saveNewBill(GeneralBillVO voNewBill) throws Exception {

    try {
      // 得到数据错误，出错 ------------ EXIT -------------------
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@saveNewBill开始");/*-=notranslate=-*/
      if (voNewBill == null || voNewBill.getParentVO() == null
          || voNewBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000055")/*
                                     * @res
                                     * "单据为空！"
                                     */);
      }
      // 执行保存...
      // 通过平台实现保存即签字，置入签字人字段，在出入库单保存时清除之。
      // 支持平台，clone一个，以便于以后的处理，同时防止修改了m_voBill
      GeneralBillVO voTempBill = (GeneralBillVO) voNewBill.clone();
      GeneralBillHeaderVO voHead = voTempBill.getHeaderVO();
      voHead.setPk_corp(getEnvironment().getCorpID());

      // voHead.setDaccountdate(new
      // nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
      // 新增的单据清除PK
      voHead.setPrimaryKey(null);
      // 表体PK
      GeneralBillItemVO[] voaItem = voTempBill.getItemVOs();
      for (int row = 0; row < voaItem.length; row++)
        voaItem[row].setPrimaryKey(null);

      // 如果不保存条码清空条码
      // nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.beforSaveBillVOBarcode(
      // m_bBarcodeSave, voNewBill);

      voHead.setCoperatoridnow(getEnvironment().getUserID()); // 当前操作员2002-04-10.wnj
      voHead.setAttributeValue("clogdatenow", getEnvironment()
          .getLogDate()); // 当前登录日期2003-01-05

      voTempBill.setParentVO(voHead);
      voTempBill.setChildrenVO(voaItem);
      timer.showExecuteTime("@@设置表头和表体：");/*-=notranslate=-*/
      // --------- save -------------
      // 加入单据号,如果单据号==sCorpID，将单据号置空，后台会自动获取。
      if (getEnvironment().getCorpID().equals(voHead.getVbillcode())) {
        voHead.setVbillcode(null);
      }
      ArrayList alPK = null;

      // 保存校验和日志 add by hanwei 2004-04-01
      voTempBill.setAccreditUserID(voNewBill.getAccreditUserID());
      voTempBill.setOperatelogVO(voNewBill.getOperatelogVO());
      // IP地址传入
      // 根据参数是否保存条码，清空条码

      // 是否保存条码
      voTempBill.setSaveBadBarcode(m_bBadBarcodeSave);
      // 是否保存数量不一致的条码
      // voTempBill.setSaveBarcode(m_bBarcodeSave);
      // 帐期、信用信息
      voTempBill.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_ADD;
      voTempBill.m_sActionCode = "SAVEBASE";

      beforeSave(voTempBill);

      // 如果不保存条码清空条码
      nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
          .beforSaveBillVOBarcode(voTempBill);

      // GeneralBillVO billvo_to_bs = (GeneralBillVO)voTempBill.clone();
      GeneralBillItemVO[] bakbvos = voTempBill.getItemVOs();

      try {
        // add by liuzy 2007-11-02 10:16 压缩数据
        // ObjectUtils.objectReference(voTempBill);
        voTempBill.compressBodyWhenSave();
        
        // songhy, 2009-11-16，start，解决保存时，拔掉网线导致数据重复的问题
        if ( getOID() == null ) {
          setOID( getEnvironment().getCorpID() );
        }
        voTempBill.getHeaderVO().setAttributeValue("cgeneralhid_temp", getOID());
        // songhy, 2009-11-16，end.
        
        alPK = (ArrayList) nc.ui.pub.pf.PfUtilClient.processAction(
            getCommitActionName(), getBillType(), getEnvironment().getLogDate(),
            voTempBill);
        
        // songhy, 2009-11-16，start，解决保存时，拔掉网线导致数据重复的问题
        clearOID();
        // songhy, 2009-11-16，end.

      }
      finally {
        voTempBill.setChildrenVO(bakbvos);
      }

      SCMEnv.out("ret..");
      timer.showExecuteTime("@@走平台保存时间：");/*-=notranslate=-*/

      // [[提示信息][PK_Head,PK_body1,PK_body2,....]]
      // 保存当前单据的OID
      // 返回数据错误 ------------------- EXIT --------------------------
      if (alPK == null || alPK.size() < 3 || alPK.get(1) == null
          || alPK.get(2) == null) {
        SCMEnv.out("return data error. al pk" + alPK);
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000056")/*
                               * @res
                               * "单据已经保存，但返回值错误，请重新查询单据。"
                               */);
      } else {

        // 显示提示信息
        if (alPK.get(0) != null
            && alPK.get(0).toString().trim().length() > 0)
          showWarningMessage((String) alPK.get(0));

        // 行数
        int iRowCount = voNewBill.getItemCount();
        ArrayList alMyPK = (ArrayList) alPK.get(1);
        if (alMyPK == null || alMyPK.size() < (iRowCount + 1)
            || alMyPK.get(0) == null || alMyPK.get(1) == null) {
          SCMEnv.out("return data error. my pk " + alMyPK);
          showWarningMessage(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000057")/*
                                       * @res
                                       * "保存返回值错误！"
                                       */);
        } else {
          // 表头的OID
          m_sCurBillOID = (String) alMyPK.get(0);
          // 设置界面的单据ID
          if (getBillCardPanel().getHeadItem(IItemKey.NAME_HEADID) != null)
            getBillCardPanel().setHeadItem(IItemKey.NAME_HEADID,
                m_sCurBillOID);
          //
          voNewBill.getParentVO().setPrimaryKey(m_sCurBillOID);
          // 表体的OID
          // ###################################################
          // 重新设置单据vo条码VOPK、表头ts,billcode,表体
          // 表头：cgeneralhid,fbillfalg,vbillcode,ts
          // 表体：cgeneralbid,crowno,vbatchcode,vfirstbillcode,ninnum,ninassistnum,noutnum,noutassistnum,ts
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

          timer.showExecuteTime("@@设置表头和表体PK时间：");/*-=notranslate=-*/
          getM_voBill().clearAccreditBarcodeUserID();
          // 回写到m_alListData
          if (!m_bRefBills)
            insertBillToList(getM_voBill());

          timer.showExecuteTime("@@insertBillToList(m_voBill)：");/*-=notranslate=-*/
        }
      }
      SCMEnv.out("insertok..");

      // v5 lj 支持参照生成多张单据
      if (m_bRefBills == true) {
        // removeBillsOfList(new int[] { m_iLastSelListHeadRow });

        setButtonStatus(false);
        ctrlSourceBillUI(true);
      }

    } catch (Exception e) {
      // 异常必须抛出，由主流程处理。因为它影响主流程。
      // ###################################################
      // 新增保存异常不记录后台日志 add by hanwei 2004-6-8
      // ###################################################

      throw e;

    }
  }

  /**
   * 创建者：王乃军 功能：保存修改的单据
   * 
   * 有错误需要以异常抛出，影响主流程
   * 
   * 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void saveUpdatedBill(GeneralBillVO voUpdatedBill)
      throws Exception {
    try {
      // 得到数据错误，出错 ------------ EXIT -------------------
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@修改保存单据开始：");/*-=notranslate=-*/
      if (voUpdatedBill == null || voUpdatedBill.getParentVO() == null
          || voUpdatedBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000055")/*
                                     * @res
                                     * "单据为空！"
                                     */);
      }

      // 如果不保存条码清空条码
      // nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.beforSaveBillVOBarcode(
      // m_bBarcodeSave, voUpdatedBill);

      // 设置单据类型
      voUpdatedBill.setHeaderValue("cbilltypecode", getBillType());
      // 05/07设置制单人为当前操作员
      // remark by zhx onSave() set coperatorid into VO
      // voUpdatedBill.setHeaderValue("coperatorid",
      // getEnvironment().getUserID());
      timer.showExecuteTime("@@设置单据类型：");/*-=notranslate=-*/
      GeneralBillVO voBill = (GeneralBillVO) getM_voBill().clone();
      timer.showExecuteTime("@@m_voBill.clone()：");/*-=notranslate=-*/
      voBill.setIDItems(voUpdatedBill);
      int iDelRowCount = voUpdatedBill.getItemCount()
          - voBill.getItemCount();
      if (iDelRowCount > 0) {
        // 如果有，追加删除行
        GeneralBillItemVO voaItems[] = new GeneralBillItemVO[voUpdatedBill
            .getItemCount()];
        // 原行
        for (int org = 0; org < voBill.getItemCount(); org++)
          voaItems[org] = voBill.getItemVOs()[org];

        // 附上删除行
        for (int del = 0; del < iDelRowCount; del++)
          voaItems[voBill.getItemCount() + del] = voUpdatedBill
              .getItemVOs()[voBill.getItemCount() + del];

        voBill.setChildrenVO(voaItems);
        timer.showExecuteTime("@@voBill.setChildrenVO(voaItems)：");/*-=notranslate=-*/
      }
      GeneralBillHeaderVO voHead = voBill.getHeaderVO();
      // --------------------------------------------可以不是当前登录单位的单据，所以不需要修改单位。
      voHead.setPk_corp(getEnvironment().getCorpID());
      // 因为登录日期和单据日期是可以不同的，所以必须要登录日期。
      // voHead.setDaccountdate(new
      // nc.vo.pub.lang.UFDate(getEnvironment().getLogDate()));
      // vo可能要传给平台，所以要做成和签字后的单据
      voHead.setCoperatoridnow(getEnvironment().getUserID()); // 当前操作员2002-04-10.wnj
      voHead.setAttributeValue("clogdatenow", getEnvironment()
          .getLogDate()); // 当前登录日期2003-01-05

      // update by zhx on 0926 增加根据库存参数IC060 判断是否保留最初的制单人。
      // IC060 ＝‘N’ 不保留，则修改单据时重置制单人。否则，不重置。
      // if (m_sRemainOperator != null
      // && m_sRemainOperator.equalsIgnoreCase("N"))
      // voHead.setCoperatorid(getEnvironment().getUserID());

      // 修改前的单据
      GeneralBillVO voOriginalBill = null;
      // 来自单据列表
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
                                     * "未找到原始单据，请查询后重试。"
                                     */);
      }
      timer.showExecuteTime("@@设置表头数据：");/*-=notranslate=-*/
      // 2003-06-13.02 wnj:设置货位、序列号修改状态。
      voBill.setLocStatus(voOriginalBill);
      timer.showExecuteTime("@@设置货位、序列号修改状态：");/*-=notranslate=-*/
      // ----
      // add by hanwei 2004-04
      voBill.setAccreditUserID(voUpdatedBill.getAccreditUserID());
      voBill.setOperatelogVO(voUpdatedBill.getOperatelogVO());

      // 是否保存条码
      voBill.setSaveBadBarcode(m_bBadBarcodeSave);
      // 是否保存数量不一致的条码
      // voBill.setSaveBarcode(m_bBarcodeSave);

      // 如果不保存条码清空条码
      nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
          .beforSaveBillVOBarcode(voBill);
      ArrayList alRetData = null;

      // GeneralBillVO curvo_to_bs = (GeneralBillVO)voBill.clone();
      // GeneralBillVO oldvo_to_bs =
      // (GeneralBillVO)voOriginalBill.clone();
      GeneralBillItemVO[] bakcurbodyvos = voBill.getItemVOs();
      GeneralBillItemVO[] bakoldbodyvos = voOriginalBill.getItemVOs();
      
//     帐期、信用信息
      voBill.m_iActionInt = nc.vo.scm.pub.bill.CreditConst.ICREDIT_ACT_MODIFY;
      voBill.m_sActionCode = "SAVEBASE";
      voBill.m_voOld = voOriginalBill;

      try {

        // add by liuzy 2007-11-02 10:16 压缩数据
        // ObjectUtils.objectReference(new GeneralBillVO[]{
        // voBill,voOriginalBill});
        // ObjectUtils.objectReference(voOriginalBill);
        //修改人：刘家清 修改时间：2008-8-15 下午02:27:03 修改原因：后台判断单据号有没有修改用。
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

      timer.showExecuteTime("@@走平台保存：：");/*-=notranslate=-*/
      // new GeneralBillVO[]{m_voBill});
      // [[提示信息][new_PK_body1,new_PK_body2,....]]
      // 保存当前单据的OID
      // 返回数据错误 ------------------- EXIT --------------------------
      if (alRetData == null || alRetData.size() < 3
          || alRetData.get(1) == null || alRetData.get(2) == null) {
        SCMEnv.out("return data error.");
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000056")/*
                               * @res
                               * "单据已经保存，但返回值错误，请重新查询单据。"
                               */);
      } else {
        // 0 ---- 显示提示信息
        if (alRetData.get(0) != null
            && alRetData.get(0).toString().trim().length() > 0)
          showWarningMessage((String) alRetData.get(0));
        // 1 ---- 返回的PK
        ArrayList alMyPK = (ArrayList) alRetData.get(1);

        // 设置新增行的PK 通过smallvo设置即可
        //setBodyPkAfterUpdate(alMyPK);
        //timer.showExecuteTime("@@设置新增行的PK：：");
        
        SMGeneralBillVO smbillvo = null;
        smbillvo = (SMGeneralBillVO) alRetData.get(2);
        timer.showExecuteTime("@@getBillCardPanel().updateValue()：：");/*-=notranslate=-*/
        voUpdatedBill = getBillVO();
        // 设置单据类型
        voUpdatedBill.setHeaderValue("cbilltypecode", getBillType());

        // ###################################################
        // 重新设置单据vo条码VOPK、表头ts,billcode,表体
        // 表头：cgeneralhid,fbillfalg,vbillcode,ts
        // 表体：cgeneralbid,crowno,vbatchcode,vfirstbillcode,ninnum,ninassistnum,noutnum,noutassistnum,ts
        
        
        m_sBillStatus = (smbillvo.getHeaderVO().getFbillflag())
            .toString();
        getGenBillUICtl().refreshSaveData(smbillvo, getBillCardPanel(),
            voUpdatedBill, getM_voBill());
        getGenBillUICtl().refreshLocFromSMVO(smbillvo,
            getBillCardPanel(), m_alLocatorData, getM_voBill());
        /*getGenBillUICtl().refreshBatchcodeAfterSave(getBillCardPanel(),
            getM_voBill());*/

        // 添加by hanwei 2004-9-23
        GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
        // ################################################### 

        getBillCardPanel().updateValue();

        timer.showExecuteTime("@@m_voBill.setIDItems(voUpdatedBill)：");/*-=notranslate=-*/

        getM_voBill().clearAccreditBarcodeUserID();
        // 刷新列表数据
        updateBillToList(getM_voBill());
        timer.showExecuteTime("@@刷新列表数据updateBillToList(m_voBill)：");/*-=notranslate=-*/
      }

    } catch (Exception e) {
      // 异常必须抛出，由主流程处理。因为它影响主流程。
      throw e;

    }
  }

  /**
   * 创建者：王乃军 功能：在列表方式下选择一张单据 参数： 单据在alListData中的索引 返回：无 例外： 日期：(2001-11-23
   * 18:11:18) 修改日期，修改人，修改原因，注释标志：
   */
  abstract protected void selectBillOnListPanel(int iBillIndex);

  /**
   * 创建日期：(2003-3-6 11:33:06) 作者：韩卫 修改日期： 修改人： 修改原因： 方法说明：
   * 取得单据查询数据，把指定行数范围的单据的表体 用存货公式解析，解析存货有关属性数据
   * 
   * @param iTopnum
   *            int
   * @param lListData
   *            java.util.ArrayList
   */
  public void setAlistDataByFormula(int iTopnum, ArrayList lListData) {
    if (lListData == null || lListData.size() == 0)
      return;
    // 需要存货公式解析的单据数目
    int iFormulaNum = 0;
    // 如果iTopnum=-1 表示取所有的单据
    // 如果lListData.size
    // 少于 iTopnum 以lListData.size为标准
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
          // 获得item
          alItemVos.add(itemVos[j]);
        }
      }

    }

    if (alItemVos != null && alItemVos.size() > 0) {
      // 通过单据公式容器类执行有关公式解析的方法
      getFormulaBillContainer().formulaBodys(getFormulaItemBody(),
          alItemVos);

    }

  }

  /**
   * 类型说明： 创建日期：(2002-12-23 11:59:38) 作者：王乃军 修改日期： 修改人： 修改原因： 算法说明：
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

    // 写界面货位
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
   * 创建者：王乃军 功能：根据设定的按钮初始化菜单。 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  protected void setButtons() {
/*    if (isSetButtons){
      SCMEnv.out("刘家清测试跟踪，已经设置过了，设定的按钮初始化菜单时的按钮: "+getBillTypeCode()+" 失败");
      return;
    }
    isSetButtons = true;*/
    //修改人：刘家清 修改时间：2008-12-24 下午04:30:20 修改原因：为了捕捉按钮没有的问题而做暂时的日志输出。
    //int inum = 0;
    ButtonObject[] buttonArray = getButtonManager().getButtonTree().getButtonArray();
/*    if (null != getBillTypeCode() && ("4D".equals(getBillTypeCode()) || "4401".equals(getBillTypeCode()))){
      SCMEnv.out("设定的按钮初始化菜单时的按钮: "+getBillTypeCode());
      for (ButtonObject bo : buttonArray){
        SCMEnv.out("按钮："+bo.getName());
        inum = inum +1;
        if (null != bo.getChildren() && 0 < bo.getChildren().size())
          for (int i = 0 ; i<bo.getChildren().size() ;i++){
            ButtonObject bb = (ButtonObject)bo.getChildren().get(i);
            SCMEnv.out("按钮："+bo.getName() + "的子按钮："+bb.getName());
            inum = inum +1;
          }
      }
    }
    
    SCMEnv.out("查询出节点"+getBillTypeCode()+"的按钮总数: "+inum);*/
    
    setButtons(buttonArray);

  }

  /**
   * 创建者：王乃军 功能：抽象方法：设置按钮状态，在setButtonStatus中调用。 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */

  abstract protected void setButtonsStatus(int iBillMode);

  /**
   * 创建者：王乃军 功能：设置必输项颜色。 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void setColor() {
    try {
      // 更改背景色后的表体中Header着色器
      javax.swing.table.DefaultTableCellRenderer tcrold = (javax.swing.table.DefaultTableCellRenderer) getBillCardPanel()
          .getBillTable().getColumnModel().getColumn(1)
          .getHeaderRenderer();
      HeaderRenderer tcr = new HeaderRenderer(tcrold);

      // 分别得到表头和表体中需着重显示的字段
      ArrayList alHeaderColChangeColorString = GeneralMethod
          .getHeaderCanotNullString(getBillCardPanel());
      ArrayList alBodyColChangeColorString = GeneralMethod
          .getBodyCanotNullString(getBillCardPanel());

      // 修改表单中的表头颜色
      SetColor.SetBillCardHeaderColor(getBillCardPanel(),
          alHeaderColChangeColorString);
      // SetBillCardHeaderColor(alHeaderColChangeColorString);

      // 置入各个着色器于表格的Header中
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
   * 创建者：王乃军 功能：在表单设置显示VO,不更新界面状态updateValue() 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void setImportBillVO(GeneralBillVO bvo) throws Exception {
    setImportBillVO(bvo, true);
  }

  /**
   * 创建者：王乃军 功能：选中列表形式下的第sn张单据 参数：sn 单据序号
   * 
   * 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
        // 由于已经在setAlistDataBYFormula中执行了表体公式，
        // 所以不可以重复出现下面这行代码 by hanwei 2003-06-24
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
   * 创建者：王乃军 功能：刷新列表形式表头数据为指定的数据 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void execHeadFormulaAtVOs(GeneralBillHeaderVO[] voh) {
    if (voh == null || voh.length <= 0)
      return;
    if (m_bIsByFormula) {
      // 添加公式处理获取仓库和废品仓库信息 by hw 2003-02-27
      getInvoInfoBYFormula().setBillHeaderWH(voh);
    }

    // 通过单据公式容器类执行有关公式解析的方法
    getFormulaBillContainer().formulaHeaders(getFormulaItemHeader(), voh);
  }

  /**
   * 创建者：王乃军 功能：刷新列表形式表头数据为指定的数据 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：刷新列表形式表头数据为指定的数据 参数： 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
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

        // 赋予表体数据
        getBillListPanel().setHeaderValueVO(voh);
        // 不可以调用下面代码，已经在上面执行了表头公式
        // getBillListPanel().getHeadBillModel().execLoadFormula();

      } catch (Exception e2) {
        nc.vo.scm.pub.SCMEnv.error(e2);
      }
    }
    
    getPluginProxy().afterSetBillVOsToListHead(voh);
  }

  /**
   * 创建者：王乃军 功能： 参数： 返回： 例外： 日期：(2001-11-24 12:14:35) 修改日期，修改人，修改原因，注释标志：
   * 
   * @param newNeedBillRef
   *            boolean
   */
  public void setNeedBillRef(boolean newNeedBillRef) {
    m_bNeedBillRef = newNeedBillRef;
  }

  /**
   * 创建者：王乃军 功能：列表形式下打印前， 置小数精度 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：设置表体/表尾的小数位数 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void setScaleOfListPanel() {
    nc.ui.ic.pub.scale.ScaleInit si = new nc.ui.ic.pub.scale.ScaleInit(
        getEnvironment().getUserID(), getEnvironment().getCorpID(),
        m_ScaleValue);

    try {
      si.setScale(getBillListPanel(), m_ScaleKey);
    } catch (Exception e) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000060")/* @res "精度设置失败：" */
          + e.getMessage());
    }

  }

  /**
   * 创建者：王乃军 功能：在表单设置显示VO,不更新界面状态updateValue() 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void setTempBillVO(GeneralBillVO bvo) throws Exception {
    if (bvo == null)
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000061")/* @res "传入的单据为空！" */);
    // 表体行数
    int iRowCount = bvo.getItemCount();

    try {
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      getBillCardPanel().removeBillEditListenerHeadTail();
      // 保存一个clone()
      setM_voBill((GeneralBillVO) bvo.clone());
      // 设置数据
      getBillCardPanel().setBillValueVO(bvo);
      // 执行公式
      getBillCardPanel().getBillModel().execLoadFormula();
      execHeadTailFormulas();
      // 更新状态 ---delete it to support CANCEL
      // getBillCardPanel().updateValue();
      // 清存货现存量数据
      bvo.clearInvQtyInfo();
      // 有表体行，选中第一行
      if (iRowCount > 0) {
        // 选中第一行
        getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
        // 重置序列号是否可用
        setBtnStatusSN(0, true);
        // 刷新现存量显示
        // setTailValue(0);
        // 重置其它数据
        nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel()
            .getBillModel();
        m_alLocatorDataBackup = m_alLocatorData;
        m_alSerialDataBackup = m_alSerialData;
        m_alLocatorData = new ArrayList();
        m_alSerialData = new ArrayList();

        for (int row = 0; row < iRowCount; row++) {
          // 设置行状态为新增
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
   * 创建者：王乃军 功能：设置表体的合计列 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void setTotalCol() {
    getBillCardPanel().setTatolRowShow(true);
    // getBillListPanel().set
    // 考虑到单据模版内部的效率，单独拿出来效率更高些。因为表体数量较大，用哈希表实现.
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

    // 表体列
    String[] saBodyTotalItemKey = { "nmny", "nplannedmny", "nshouldinnum",
        "nshouldoutnum", "ntranoutnum", "nretnum", "noutnum",
        "nleftnum", "ninnum", "ninassistnum", "nleftastnum",
        "nneedinassistnum", "noutassistnum", "nretastnum",
        "nshouldoutassistnum", "ntranoutastnum", "volume", "weight" };
    for (int k = 0; k < saBodyTotalItemKey.length; k++) {
      // 如果是此列
      if (htCardBody.containsKey(saBodyTotalItemKey[k]))
        biaCardBody[Integer.valueOf(
            htCardBody.get(saBodyTotalItemKey[k]).toString())
            .intValue()].setTatol(true);
      // //如果是此列
      // if (htListBody.containsKey(saBodyTotalItemKey[k]))
      // biaListBody[Integer
      // .valueOf(htListBody.get(saBodyTotalItemKey[k]).toString())
      // .intValue()]
      // .setTatol(true);
    }

  }

  /**
   * 创建者：王乃军 功能：重载的显示提示信息对话框功能 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
              "UPPSCMCommon-000270")/* @res "提示" */, sMsg);

  }

  /**
   * 创建者：王乃军 功能：重载的显示提示信息对话框功能 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void showWarningMessage(String sMsg) {
    if (m_bUserDefaultErrDlg){
      // added by lirr 2009-11-5下午03:35:29
      showHintMessage(sMsg);
      super.showWarningMessage(sMsg);

    }
    else
      nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("SCMCOMMON",
              "UPPSCMCommon-000270")/* @res "提示" */, sMsg);

  }

  /**
   * 创建者：张欣 功能：设置指定的业务类型为选择方式。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：显示消耗的时间 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void showTime(long lStartTime, String sTaskHint) {
    long lTime = System.currentTimeMillis() - lStartTime;
    SCMEnv.out("执行<"/*-=notranslate=-*/ + sTaskHint + ">消耗的时间为："/*-=notranslate=-*/ + (lTime / 60000) + "分"/*-=notranslate=-*/
        + ((lTime / 1000) % 60) + "秒"/*-=notranslate=-*/ + (lTime % 1000) + "毫秒");/*-=notranslate=-*/

  }

  /**
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-4-27 13:21:10) 修改日期，修改人，修改原因，注释标志：
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
   * ClientUI 构造子注解。 nc 2.2 提供的单据联查功能构造子。
   * 
   */
  public GeneralBillClientUI(String pk_corp, String billType,
      String businessType, String operator, String billID) {
    super();
    // 查单据
    GeneralBillVO voBill = qryBill(pk_corp, billType, businessType,
        operator, billID, null);
    // 初始化界面
    setM_alListData(new ArrayList<GeneralBillVO>());

    getM_alListData().add(voBill);

    if (voBill == null)
      nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("SCMCOMMON",
              "UPPSCMCommon-000270")/* @res "提示" */,
          nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
              "UPP4008bill-000062")/* @res "没有符合查询条件的单据！" */);
    else {
      getEnvironment().setCorpID(voBill.getHeaderVO().getPk_corp());
      getEnvironment().setUserID(operator);
      getEnvironment().setUserName("jc");
      getEnvironment().setGroupID("0001");
      getEnvironment().setLogDate("2003-04-17");

      initialize(businessType);

      // 通过单据公式容器类执行有关公式解析的方法
      setListHeadData(new GeneralBillHeaderVO[] { voBill.getHeaderVO() });

      appendLocator(voBill);

      // 显示单据
      setBillVO(voBill);
    }

  }

  /**
   * 创建现存量参照Panel
   * 
   * @param iRow
   *            修改人：刘家清 修改日期：2007-10-23下午03:07:03
   *            修改原因：获取现存量参照Panel时，得添加一下条码编辑框来显示出来。
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
   * 条码扫描Panel,当开关m_bAddBarcodeField为true时显示 修改人：刘家清 修改日期：2007-10-23下午03:07:50
   * 修改原因：在获取条码扫描Panel时，得添加一下条码编辑框来显示
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
                                     * "请输入条形码: "
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
   * 李俊 功能：特殊单为来源单据的其它出入单，数量可编辑；且数量不能大于应收发数量 参数： 返回： /* 1.数量编辑，辅助数量编辑， 11
   * 得到应收发数量，NULL则为0 12 得到实际收发数量，比较两者数量，如果实际数量》实际辅助数量， 如果实际辅助数量》应收发辅助数量 提示
   * 
   * 例外： 日期：(2005-1-28 14:27:22) 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void afterNumEditFromSpe(nc.ui.pub.bill.BillEditEvent e) {

    try {
      int iRow = e.getRow();
      // 来源单据控制：
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
                                         * "实际数量不能大于应收发数量！"
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
                                         * "实际数量不能大于应收发数量！"
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
   * 创建者：王乃军 功能：存货事件处理 参数：e单据编辑事件 返回： 例外： 日期：(2001-5-8 19:08:05)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void afterInvMutiEdit(nc.ui.pub.bill.BillEditEvent e) {
    getEditCtrl().afterInvMutiEdit(e);

  }

  /**
   * 快速录入: v5支持集中采购, 此方法被采购入库覆盖
   * 
   * @param istartrow
   *            增行开始字段
   * @param count
   *            增行数量
   */
  public void setBodyDefaultData(int istartrow, int count) {

  }

  /**
   * 
   */
  public void setCardMode() {
    // 如果现存量显示隐藏是显示状态，则当前卡片状态必须为MultiCardMode.CARD_TAB
    if (m_bOnhandShowHidden) {
      m_sMultiMode = MultiCardMode.CARD_TAB;
    } else {
      m_sMultiMode = MultiCardMode.CARD_PURE;
    }
  }

  /**
   * 显示现存量参照的数据
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
   * 方法功能描述：<br>
   * 查找单据集合中的从U8RM中传入的单据号字符串。<br>
   * 如果存在从U8RM中传入的单据，将会返回单据号列表；否则，返回空字符串
   * <p>
   * 
   * @return 单据号列表字符串
   *         <p>
   * @author duy
   * @time 2007-4-18 上午10:14:51
   * 
   */
  protected String getU8RMBillCodes(java.util.ArrayList<GeneralBillVO> bills) {
    StringBuffer billCodes = new StringBuffer();
    for (GeneralBillVO bill : bills) {
      // 修改人：刘家清 修改日期：2007-11-29上午10:11:23 修改原因：
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
   * 创建者：王乃军 功能：删除处理 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public String checkBillsCanDeleted(ArrayList alBills) {
    String sError = null;
    if (alBills != null) {
      {
        // 查询单据集合中的从U8零售中传入的单据编码的字符串
        // 如果存在从U8零售中传入的单据，则不允许删除
        String billCodes = getU8RMBillCodes(alBills);
        if (billCodes != null && billCodes.length() > 0) {
          String message1 = nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008U8RM-000001", null,
                  new String[] { billCodes });
          /*
           * @res "单据号是{0}的单据是由U8零售上传并自动生成的，不能执行该操作！"
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
          /** 判断单据类型是否为外部单据 */
          // 没有来源单据，并且浏览状态下，复制可用
          IBillType billType = BillTypeFactory.getInstance().getBillType(sSourceBillType);
          if (sSourceBillType != null
              && billType.typeOf(ModuleCode.IC)) {
            // 内部生成单据
            /** 判断单据类型否为转库单 */
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
               * 当单据是浏览状态时，复制按钮不可用 。组装、拆卸、形态转换、盘点特殊单据生成的其他入、
               * 其他出不能直接删除，在删除时提示用户通过弃审特殊单实现。所以，将删除按钮置灰。
               */

              String[] args = new String[1];
              args[0] = gvo.getHeaderValue("vbillcode")
                  .toString();
              String message = nc.ui.ml.NCLangRes.getInstance()
                  .getStrByID("4008bill",
                      "UPP4008bill-000340", null, args);
              /*
               * @res
               * "单据号是：{0}的单据是由组装、拆卸、形态转换、盘点特殊单据生成的其他入,其他出不能直接删除，通过弃审特殊单实现!"
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
              // 销售出库单，采购入库单配套生成的其它出，其它入库单据不能删除修改单据，复制
              String[] args1 = new String[1];
              args1[0] = gvo.getHeaderValue("vbillcode")
                  .toString();
              String message1 = nc.ui.ml.NCLangRes.getInstance()
                  .getStrByID("4008bill",
                      "UPP4008bill-000341", null, args1);
              /*
               * @res
               * "单据号是：{0}的单据是由销售出库单，采购入库单配套生成的其它出，其它入库单据不能删除修改单据!"
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
   * 此处插入方法说明。 创建日期：(2003-10-09 16:34:45)
   */
  protected void checkVMIWh(GeneralBillVO voBill) throws Exception {

    VOCheck.checkVMIWh(new QueryInfo(), voBill);

  }

  /**
   * 此处插入方法说明。 创建日期：(2003-11-7 15:13:21)
   * 
   * @param row
   *            int
   */
  public void clearRow(int row) {
    clearRowData(row);
    // 清应数量
    // 同步vo
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
    // 表尾
    setTailValue(null);
  }

  /**
   * 来源单据是转库单时的界面控制方法 功能： 参数： 返回： 例外： 日期：(2001-10-19 09:43:22)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void ctrlSourceBillButtons(boolean bUpdateButtons) {
    String sSourceBillType = getSourBillTypeCode();
    IBillType billType = BillTypeFactory.getInstance().getBillType(sSourceBillType);
    /** 判断单据类型是否为外部单据 */
    // 没有来源单据，并且浏览状态下，复制可用
    if ((sSourceBillType == null || sSourceBillType.trim().length() == 0)) {
      if (getM_iMode() == BillMode.Browse && m_iBillQty > 0
          && getM_iLastSelListHeadRow() >= 0) {
        // 浏览状态下可以复制单据
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(true);
      }
    } else if (!billType.typeOf(ModuleCode.IC)) {
      // 外部生成的单据,且已经作乐配套处理

      // 修改、新增状态下。
      if (getM_iMode() == BillMode.Update || getM_iMode() == BillMode.New) {

        /** 置单据行，表体右键按钮,增行，插入行为不可用,复制，粘贴可用 */

        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
            .setEnabled(false);

        // getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO).setEnabled(false);
        // 表体右键增加行
        getBillCardPanel().getBodyMenuItems()[0].setEnabled(false);
        // 表体右键插入行
        getBillCardPanel().getBodyMenuItems()[2].setEnabled(false);
        // 表体右键删除行，强行置为true; 有Bug，若把插入行置灰，则它前面的删除行也被置灰。
        getBillCardPanel().getBodyMenuItems()[1].setEnabled(true);

        // modified by 2008-01-07 为什么要设置成false？注之！
        // if (null !=
        // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index])
        // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index].setEnabled(false);

      } else{
        // 浏览状态下只是不让复制单据
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false); 
        
        // 修改人：陈倪娜 上午10:53:36_2009-10-17 修改原因 转单退库不可用并且是转多个入库单
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
      // 内部生成单据
      /** 判断单据类型否为转库单 */
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
          /** 当单据是新增或修改状态时的界面行为控制。 */
          /** 置单据行，表体右键按钮,删行，增行，插入行为不可用,复制，粘贴可用 */

          /** 置菜单按钮的可用状态 */
          getButtonManager().getButton(ICButtonConst.BTN_LINE)
              .setEnabled(false);
          /** 置表体右键菜单按钮的可用状态 */
          getBillCardPanel().setBodyMenuShow(false);

        }
        /**
         * 当单据是浏览状态时，复制按钮不可用 。组装、拆卸、形态转换、盘点特殊单据生成的其他入、
         * 其他出不能直接删除，在删除时提示用户通过弃审特殊单实现。所以，将删除按钮置灰。
         */
        else {
          if (!(sSourceBillType
              .equals(nc.vo.ic.pub.BillTypeConst.m_transfer) || sSourceBillType
              .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {
            getButtonManager().getButton(
                ICButtonConst.BTN_BILL_DELETE)
                .setEnabled(false);
          } else {
            // 签字的单据不能删除！
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
        // 销售出库单，采购入库单配套生成的其它出，其它入库单据不能删除修改单据，复制
        getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
            .setEnabled(false);
        getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
            .setEnabled(false);

      }
      // 如果其他入和其他出的拆解类型是“系统产生“的，那么不能复制，删除，修改
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
    // //已经生成配套的单据不能被删除，修改和复制。
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
    // 设置 add by hanwei 2004-05-14
    // 1、库存出入库单如果有直接调转标志，需要在界面控制 修改、删除、复制等按钮不可用；
    // 2、如果单据的仓库是直运仓，应该控制修改、删除、复制等按钮不可用；
    // 参数true:只设置false的属性
    setBtnStatusTranflag(true);
    // #############################

    // 必须调用的刷新界面的菜单按钮
    if (bUpdateButtons)
      updateButtons();

    // v5 lj
    if (m_bRefBills == true) {
      setRefBtnStatus();
    }
  }

  /**
   * 来源单据是转库单时的界面控制方法 功能： 参数： 返回： 例外： 日期：(2001-10-19 09:43:22)
   * 修改日期，修改人，修改原因，注释标志：@
   * 
   */
  protected void ctrlSourceBillUI(boolean bUpdateButtons) {
    try {
      String sSourceBillType = getSourBillTypeCode();
      IBillType billType = BillTypeFactory.getInstance().getBillType(sSourceBillType);
      /** 判断单据类型是否为外部单据 */
      // 没有来源单据，并且浏览状态下，复制可用
      if ((sSourceBillType == null || sSourceBillType.trim().length() == 0)) {
        if (getM_iMode() == BillMode.Browse&& m_iBillQty > 0
            && getM_iLastSelListHeadRow() >= 0) {
          // 浏览状态下可以复制单据
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(true);
        }
      } else if (!billType.typeOf(ModuleCode.IC)) {
        // 外部生成的单据

        // 修改、新增状态下。
        if (getM_iMode() == BillMode.Update
            || getM_iMode() == BillMode.New) {

          /** 置表头不可编辑项 */
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

          /** 置单据行，表体右键按钮,增行，插入行为不可用,复制，粘贴可用 */

          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_LINE_ADD)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_LINE_INSERT)
              .setEnabled(false);

          // getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO).setEnabled(false);
          // 表体右键增加行
          getBillCardPanel().getBodyMenuItems()[0].setEnabled(false);
          // 表体右键插入行
          getBillCardPanel().getBodyMenuItems()[2].setEnabled(false);
          // 表体右键删除行，强行置为true; 有Bug，若把插入行置灰，则它前面的删除行也被置灰。
          getBillCardPanel().getBodyMenuItems()[1].setEnabled(true);

          // modified by 2008-01-07 为什么要设置成false？注之！
          // if (null !=
          // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index])
          // getBillCardPanel().getBodyMenuItems()[m_Menu_AddNewRowNO_Index].setEnabled(false);
        } else {

          // 浏览状态下只是不让复制单据
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
        }

      }
      else {
        // 内部生成单据,特殊单据，销售出库，采购入库的配套生成的其它入，出。
        /** 判断单据类型否为转库单 */
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
            /** 当单据是新增或修改状态时的界面行为控制。 */
            /** 置单据行，表体右键按钮,删行，增行，插入行为不可用,复制，粘贴可用 */

            /** 置菜单按钮的可用状态 */
            getButtonManager().getButton(ICButtonConst.BTN_LINE)
                .setEnabled(false);
            /** 置表体右键菜单按钮的可用状态 */
            getBillCardPanel().setBodyMenuShow(false);
            String sHeadItemKey = null;
            // 配套销售出库单生成的其它出库单，表头客户不可编辑
            if (sSourceBillType.equals(BillTypeConst.m_saleOut)
                && getBillType().equals(
                    BillTypeConst.m_otherOut)) {
              sHeadItemKey = "ccustomerid";
            }
            // 配套采购入库单生成的其它入库单，表头客户不可编辑
            else if (sSourceBillType
                .equals(BillTypeConst.m_purchaseIn)
                && getBillType()
                    .equals(BillTypeConst.m_otherIn)) {
              sHeadItemKey = "cproviderid";
            }

            /** 置表头不可编辑项 */
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
           * 当单据是浏览状态时，复制按钮不可用 。组装、拆卸、形态转换、盘点特殊单据生成的其他入、
           * 其他出不能直接删除，在删除时提示用户通过弃审特殊单实现。所以，将删除按钮置灰。
           */
          else {
            if (!(sSourceBillType
                .equals(nc.vo.ic.pub.BillTypeConst.m_transfer) || sSourceBillType
                .equals(nc.vo.ic.pub.BillTypeConst.m_AllocationOrder))) {
              getButtonManager().getButton(
                  ICButtonConst.BTN_BILL_DELETE).setEnabled(
                  false);
            } else {
              // 签字的单据不能删除！
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
          // 销售出库单,采购入库但配套生成的其它出，其它入库单据不能删除修改单据，复制
          getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_COPY)
              .setEnabled(false);
          getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
              .setEnabled(false);

        } else if (sSourceBillType != null
            && (sSourceBillType
                .equals(BillTypeConst.m_AllocationOrder))) {
          // 来源单据是调拨订单的界面控制：

          /** 当单据是新增或修改状态时的界面行为控制。 */
          /** 置单据行，表体右键按钮,删行，增行，插入行为不可用,复制，粘贴可用 */

          /** 置菜单按钮的可用状态 */
          getButtonManager().getButton(ICButtonConst.BTN_LINE)
              .setEnabled(false);
          /** 置表体右键菜单按钮的可用状态 */
          getBillCardPanel().setBodyMenuShow(false);

          /** 置表头库存组织不可编辑项 */
          String saNotEditableHeadKey2 = IItemKey.CALBODY;

          if (getBillCardPanel().getBillData().getHeadItem(
              saNotEditableHeadKey2) != null)
            getBillCardPanel().getBillData().getHeadItem(
                saNotEditableHeadKey2).setEnabled(false);

        }

      } // 必须调用的刷新界面的菜单按钮

      // ###########################
      // 设置 add by hanwei 2004-05-14
      // 1、库存出入库单如果有直接调转标志，需要在界面控制 修改、删除、复制等按钮不可用；
      // 2、如果单据的仓库是直运仓，应该控制修改、删除、复制等按钮不可用；
      // 参数true:只设置false的属性
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
   * 此处插入方法说明。 功能描述:处理查询时没有查到数据的情况下界面的。 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-6-9
   * 15:57:49)
   */
  protected void dealNoData() {
    // 设置当前的单据数量/序号，用于按钮控制
    setLastHeadRow(-1);
    // 初始化当前单据序号，切换时用到。
    m_iCurDispBillNum = -1;
    // 当前单据数
    m_iBillQty = 0;
    setM_alListData(null);
    clearUi();
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
        "UPP4008bill-000013")/* @res "未查到符合条件的单据。" */);
  }

  /**
   * 此处插入方法说明。 创建日期：(2002-11-27 10:41:27)
   * 
   * @param error
   *            java.lang.String
   */
  public void errormessageshow(java.lang.String error) {
    // 提示警告信息
    java.awt.Toolkit.getDefaultToolkit().beep();
    showErrorMessage(error);
    // 光标回编辑框
    m_utfBarCode.requestFocus();
  }

  /**
   * 调用公式 功能： 参数： 返回： 例外： 日期：(2001-11-12 16:47:04) 修改日期，修改人，修改原因，注释标志：
   */
  private String execFormular(String formula, String value) {
    nc.ui.pub.formulaparse.FormulaParse f = new nc.ui.pub.formulaparse.FormulaParse();

    if (formula != null && !formula.equals("")) {
      // 设置表达式
      f.setExpress(formula);
      // 获得变量
      nc.vo.pub.formulaset.VarryVO varry = f.getVarry();
      // 给变量付值
      Hashtable h = new Hashtable();
      for (int j = 0; j < varry.getVarry().length; j++) {
        String key = varry.getVarry()[j];

        String[] vs = new String[1];
        vs[0] = value;
        h.put(key, StringUtil.toString(vs));
      }

      f.setDataS(h);
      // 设置结果
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
   * 此处插入方法说明。 子类可以重载本方法,实现废品库参照初始化 RefFilter.filtWasteWh(billItem, sCorpID,
   * null);
   * 
   * 创建日期：(2004-3-19 17:41:50)
   */
  public void filterRefofWareshouse(nc.ui.pub.bill.BillItem billItem,
      String sCorpID) {

    RefFilter.filtWh(billItem, sCorpID, null);

  }

  /**
   * 此处插入方法说明。 获得权限认证UI 创建日期：(2004-4-19 14:11:06)
   * 
   * @return nc.ui.scm.pub.AccreditLoginDialog
   */
  public nc.ui.scm.pub.AccreditLoginDialog getAccreditLoginDialog() {
    if (m_AccreditLoginDialog == null)
      m_AccreditLoginDialog = new AccreditLoginDialog();
    return m_AccreditLoginDialog;
  }

  /**
   * 此处插入方法说明。 创建日期：(2004-5-7 14:18:22)
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
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2002-1-24 11:35:23) 修改日期，修改人，修改原因，注释标志：
   * 
   * @return boolean
   */
  /**
   * 返回 BillCardPanel1 特性值。
   * 
   * @return nc.ui.pub.bill.BillCardPanel
   */
  /* 警告：此方法将重新生成。 */
  public BarCodeDlg getBarCodeDlg() {
    m_dlgBarCodeEdit = new BarCodeDlg(this, getEnvironment().getCorpID());
    return m_dlgBarCodeEdit;
  }

  /**
   * 此处插入方法说明。 用于子类对Condition的个性化修改设置 的重载方法 创建日期：(2003-11-25 20:58:54)
   */
  protected void getConDlginitself(QueryConditionDlgForBill queryCondition) {
  }

  /**
   * 此处插入方法说明。 功能：得到标准文件对话框 参数：标准文件对话框 返回：无 例外： 日期：(2002-9-24 15:47:21)
   * 修改日期，修改人，修改原因，注释标志：
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
        // 移去当前的文件过滤器
        // m_InFileDialog.addChoosableFileFilter(new
        // nc.ui.pf.export.SuffixFilter());
        // 添加文件选择过滤器

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
                                     * "Excel文件"
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
   * 此处插入方法说明。 功能描述:获得 BillFormulaContainer 作者：韩卫 输入参数: 返回值: 异常处理:
   * 日期:(2003-7-2 9:48:12)
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
   * 此处插入方法说明。 功能描述: 作者：王乃军 输入参数: 返回值: 异常处理: 日期:(2003-6-25 20:43:17)
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

    // 项目基本档案ID
    String[] aryItemField11 = new String[] { "pk_jobbasfil",
        "pk_jobbasfil", "cprojectid" };
    arylistItemField.add(aryItemField11);

    // 项目名称
    String[] aryItemField12 = new String[] { "jobname", "cprojectname",
        "pk_jobbasfil" };
    arylistItemField.add(aryItemField12);

    // 项目阶段基本档案ID
    String[] aryItemField13 = new String[] { "pk_jobphase", "pk_jobphase",
        "cprojectphaseid" };
    arylistItemField.add(aryItemField13);

    // 项目阶段名称
    String[] aryItemField14 = new String[] { "jobphasename",
        "cprojectphasename", "pk_jobphase" };
    arylistItemField.add(aryItemField14);

    // 辅助计量单位
    String[] aryItemField31 = new String[] { "measname", "castunitname",
        "castunitid" };
    arylistItemField.add(aryItemField31);

    // 客商基本档案 for 供应商
    String[] aryItemField7 = new String[] { "pk_cubasdoc", "pk_cubasdoc",
        "cvendorid" };
    arylistItemField.add(aryItemField7);

    // 客商名称 for 供应商
    String[] aryItemField8 = new String[] { sCusterNameFields,
        "vvendorname", "pk_cubasdoc" };
    arylistItemField.add(aryItemField8);

    // zhy2005-09-16客商名称 for 供应商
    String[] aryItemField88 = new String[] { sCusterNameFields,
        "cvendorname", "pk_cubasdoc" };
    arylistItemField.add(aryItemField88);

    // 客商基本档案 for 客户||收货单位
    String[] aryItemField17 = new String[] { "pk_cubasdoc",
        "pk_cubasdocrev", "creceieveid" };
    arylistItemField.add(aryItemField17);

    // 客商名称 for 客户||收货单位
    String[] aryItemField18 = new String[] { sCusterNameFields,
        "vrevcustname", "pk_cubasdocrev" };
    arylistItemField.add(aryItemField18);

//    // 来源单据类型, 为减少链接数，去掉此处公式加载，放到后台完成，songhy
//    String[] aryItemField9 = new String[] { "billtypename",
//        "csourcetypename", "csourcetype" };
//    arylistItemField.add(aryItemField9);

    // 成本对象 基本档案
    // ccostobjectbasid
    String[] aryItemField34 = new String[] { "pk_invbasdoc",
        "ccostobjectbasid", "ccostobject" };
    arylistItemField.add(aryItemField34);
    // 成本对象
    String[] aryItemField33 = new String[] { "invname", "ccostobjectname",
        "ccostobjectbasid" };
    arylistItemField.add(aryItemField33);

//    // 源头单据号, 为减少链接数，去掉此处公式加载，放到后台完成，songhy
//    String[] aryItemField10 = new String[] { "billtypename",
//        "cfirsttypename", "cfirsttype" };
//    arylistItemField.add(aryItemField10);
    
    // //部门
    String[] aryItemField19 = new String[] { "deptname", "vdeptname",
        "cdptid" };
    arylistItemField.add(aryItemField19);

    // 货位
    String[] aryItemField20 = new String[] { "csname", "vspacename",
        "cspaceid" };
    arylistItemField.add(aryItemField20);

    // 对应入库单类型 ccorrespondtype
    // String[] aryItemField20 =
    // new String[] { "billtypename", "cfirsttypeName", "cfirsttype" };
    // arylistItemField.add(aryItemField20);
    return arylistItemField;
  }

  /**
   * 此处插入方法说明。 功能描述: 作者：王乃军 输入参数: 返回值: 异常处理: 日期:(2003-6-25 20:43:17)
   * 
   * @return java.util.ArrayList
   */
  protected ArrayList getFormulaItemHeader() {
    ArrayList<String[]> arylistItemField = new ArrayList<String[]>();
    // 原有的公式
    // 库存组织 1
    String[] aryItemField40 = new String[] { "bodyname", "vcalbodyname",
        "pk_calbody" };
    arylistItemField.add(aryItemField40);

    // 库房管理员 2
    String[] aryItemField3 = new String[] { "psnname", "cwhsmanagername",
        "cwhsmanagerid" };
    arylistItemField.add(aryItemField3);

    // 仓库 3
    String[] aryItemField15 = new String[] { "storname", "cwarehousename",
        "cwarehouseid" };
    arylistItemField.add(aryItemField15);

    // //仓库是否是否直运仓库
    String[] aryItemField41 = new String[] { "isdirectstore",
        "isdirectstore", "cwarehouseid" };
    arylistItemField.add(aryItemField41);

    // 仓库是否是否资产仓库
    String[] aryItemField43 = new String[] { "iscapitalstor",
        "iscapitalstor", "cwarehouseid" };
    arylistItemField.add(aryItemField43);

    // 仓库 3
    String[] aryItemField25 = new String[] { "storname",
        "cwastewarehousename", "cwastewarehouseid" };
    arylistItemField.add(aryItemField25);

    // 记账人 4
    String[] aryItemField2 = new String[] { "user_name", "cregistername",
        "cregister" };
    arylistItemField.add(aryItemField2);

    // //审批人 5
    String[] aryItemField12 = new String[] { "user_name", "cauditorname",
        "cauditorid" };
    arylistItemField.add(aryItemField12);

    // //操作员 6
    String[] aryItemField1 = new String[] { "user_name", "coperatorname",
        "coperatorid" };
    arylistItemField.add(aryItemField1);

    // 部门 7
    String[] aryItemField19 = new String[] { "deptname", "cdptname",
        "cdptid" };
    arylistItemField.add(aryItemField19);

    // 业务员 8
    String[] aryItemField13 = new String[] { "psnname", "cbizname",
        "cbizid" };
    arylistItemField.add(aryItemField13);

    // 客商基本档案 for 供应商 9
    String[] aryItemField7 = new String[] { "pk_cubasdoc", "pk_cubasdoc",
        "cproviderid" };
    arylistItemField.add(aryItemField7);

    // 客商名称 for 供应商 9
    String[] aryItemField8 = new String[] { "custname", "cprovidername",
        "pk_cubasdoc" };
    arylistItemField.add(aryItemField8);
    // 客商名称 for 供应商简称
    String[] aryItemField81 = new String[] { "custshortname",
        "cprovidershortname", "pk_cubasdoc" };
    arylistItemField.add(aryItemField81);

    // 客商基本档案 for 客户 10
    String[] aryItemField5 = new String[] { "pk_cubasdoc", "pk_cubasdocC",
        "ccustomerid" };
    arylistItemField.add(aryItemField5);

    // 客商名称 for 客户 10
    String[] aryItemField6 = new String[] { "custname", "ccustomername",
        "pk_cubasdocC" };
    arylistItemField.add(aryItemField6);
    // 客商名称 for 客户简称
    String[] aryItemField61 = new String[] { "custshortname",
        "ccustomershortname", "pk_cubasdocC" };
    arylistItemField.add(aryItemField61);

    // //收发类型 11
    String[] aryItemField18 = new String[] { "rdname", "cdispatchername",
        "cdispatcherid" };
    arylistItemField.add(aryItemField18);

    // 业务类型 12
    String[] aryItemField17 = new String[] { "businame", "cbiztypename",
        "cbiztype" };
    arylistItemField.add(aryItemField17);

    // 新增加的公式
    // //发运方式 13
    String[] aryItemField42 = new String[] { "sendname",
        "cdilivertypename", "cdilivertypeid" };
    arylistItemField.add(aryItemField42);

    // for 来料加工入库单：加工品
    // 基本档案
    // pk_invbasdoc 14
    String[] aryItemField20 = new String[] { "pk_invbasdoc",
        "pk_invbasdoc", "cinventoryid" };
    arylistItemField.add(aryItemField20);

    // 名称 14
    String[] aryItemField21 = new String[] { "invname", "cinventoryname",
        "pk_invbasdoc" };
    arylistItemField.add(aryItemField21);

    return arylistItemField;
  }

  /**
   * 此处插入方法说明。 创建日期：(2004-4-8 11:13:07)
   * 
   * @return nc.ui.ic.pub.bill.GeneralBillUICtl
   */
  public GeneralBillUICtl getGenBillUICtl() {
    if (m_GenBillUICtl == null)
      m_GenBillUICtl = new GeneralBillUICtl();
    return m_GenBillUICtl;
  }

  /**
   * 此处插入方法说明。 创建日期：(2001-7-11 下午 11:19)
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
   * 此处插入方法说明。 功能描述: 单据查询条件构造; 借入、借出单据可以重新构造该方法,而不必重载onquery方法 输入参数: 返回值:
   * 异常处理: 日期:
   * 
   * @return nc.vo.ic.pub.bill.QryConditionVO
   */
//  protected QryConditionVO getQryConditionVO() {
//    // 添加查询
//    nc.vo.pub.query.ConditionVO[] voaCond = getConditionDlg()
//        .getConditionVO();
//    // 处理跨公司部门业务员条件
//    voaCond = nc.ui.ic.pub.tools.GenMethod.procMultCorpDeptBizDP(voaCond,
//        getBillTypeCode(), getCorpPrimaryKey());
//    // 过滤null为 is null 或 is not null add by hanwei 2004-03-31.01
//    nc.ui.ic.pub.report.IcBaseReportComm.fixContionVONullsql(voaCond);
//    QryConditionVO voCond = new QryConditionVO(" head.cbilltypecode='"
//        + getBillType() + "' AND " + getExtendQryCond(voaCond));
//
//    // addied by liuzy 2008-03-28 V5.03需求：单据查询增加起止日期
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
   * 此处插入方法说明。 创建日期：(2004-3-12 21:25:15)
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
   * 简单初始化类。按传入参数，不读环境设置的操作员，公司等。
   */
  /* 警告：此方法将重新生成。 */
  protected void initialize(String sBiztypeid) {

    try {
      // 界面管理器
      m_layoutManager = new ToftLayoutManager(this);
      // user code begin {1}
      // 抽象方法，初始化参数
      initPanel();
      getButtonManager().getButton(ICButtonConst.BTN_LINE_BARCODE)
          .setEnabled(false);
      // 上下翻页的控制
      m_pageBtn = new PageCtrlBtn(this);
      // 初始化按钮
      // initButtons();
      // 读系统参数
      initSysParam();
      // 初始化缺省菜单。
      // initButtonsData();



      // user code end
      setName("ClientUI");

      // ------------- 设置精度 -----------
      setScaleOfCardPanel(getBillCardPanel());
      setScaleOfListPanel();
      // 设置单价、换算率等>0
      getGenBillUICtl().setValueRange(
          getBillCardPanel(),
          new String[] { "nprice", "hsl", "nsaleprice", "ntaxprice",
              "nquoteunitrate" }, 0,
          nc.vo.scm.pub.bill.SCMDoubleScale.MAXVALUE);
      
      //SCMEnv.out("刘家清测试跟踪，重新设定的按钮初始化菜单时的按钮: "+getBillTypeCode());
      // 设置菜单
      setButtons();

      // 初始化编辑前控制器
      getBillCardPanel().getBillModel().setCellEditableController(this);
      
//      得到当前的表体中系统默认的背景色
      m_cNormalColor = getBillCardPanel().getBillTable().getBackground();
      
      
      // addied by liuzy 2010-7-1 下午08:00:37 处理自定义项参照的可编辑性      
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

    // 初始化为不可编辑。
    getBillCardPanel().setEnabled(false);
    setM_iMode(BillMode.Browse);
    // 初始化显示表单形式。
    setM_iCurPanel(BillMode.Card);

    getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setHint(
        nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
            "UPP4008bill-000068")/* @res "切换到列表形式" */);

    getBillListPanel().addEditListener(this);
    getBillListPanel().getChildListPanel().addEditListener(this);
    getBillCardPanel().addEditListener(this);

    // 暂时不加，否则表体全可编辑。
    getBillCardPanel().addBodyEditListener2(this);

    getBillCardPanel().setBillBeforeEditListenerHeadTail(this);

    getBillCardPanel().getBillModel().addTableModelListener(this);

    // 排序 监听表头和表头的排序
    m_listHeadSortCtl = new ClientUISortCtl(this, false, BillItem.HEAD);
    getBillListPanel().getHeadBillModel().addBillSortListener2(this);
    m_listBodySortCtl = new ClientUISortCtl(this, false, BillItem.BODY);

    getBillCardPanel().getBillTable().addSortListener();
    m_cardBodySortCtl = new ClientUISortCtl(this, true, BillItem.BODY);

    getBillCardPanel().setAutoExecHeadEditFormula(true);

    // 合计列
    getBillCardPanel().getBillModel().addTotalListener(this);

    // getBillListPanel().getHead().addTableModelListener(this);
    getBillListPanel().getBodyTable().getModel()
        .addTableModelListener(this);

    getBillListPanel().addMouseListener(this);

    getBillCardPanel().addBodyMenuListener(this);
    // 过滤参照显示
    filterRef(getEnvironment().getCorpID());
    // 设置汇总列
    setTotalCol();
    // 在retBusinessBtn前调用。
    setButtonStatus(true);

    GeneralBillUICtl.initHideItem(this);

    GeneralBillUICtl.setCardItemNotEdit(getBillCardPanel());

    getEditCtrl().saveCardEditFlag(getBillCardPanel());

    GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);

    // 如果有单据参照
    if (m_bNeedBillRef && isNeedBillRefWithBillType()) {
      // 读取业务类型
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
      //SCMEnv.out("刘家清测试跟踪，重新设定的按钮初始化菜单时的按钮: "+getBillTypeCode());
      //SCMEnv.out("刘家清测试跟踪，重新设定的按钮初始化菜单时的按钮: "+getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE).getCode() + ":"+getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE).getName()+":"+getButtonManager().getButton(ICButtonConst.BTN_BUSINESS_TYPE).getTag());
      onJointAdd(getButtonManager().getButton(
          ICButtonConst.BTN_BUSINESS_TYPE).getChildButtonGroup()[0]);
    }

    // 修改人：刘家清 修改日期：2007-12-26上午11:05:02 修改原因：右键增加"重排行号"
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
   * 创建者：李俊 功能：（单据审核后）保存导入条码
   */
  public void onImportSignedBillBarcode(GeneralBillVO voUpdatedBill)
      throws Exception {

    // 是否检查条码数量和实际数量：true
    onImportSignedBillBarcode(voUpdatedBill, true);

  }

  /**
   * 创建者：李俊 功能：单据未签字浏览状态下导入条码
   * 
   */
  public ArrayList onImportSignedBillBarcodeKernel(GeneralBillVO voBill,
      GeneralBillVO voUpdatedBill) throws Exception {
    voBill.setAccreditUserID(voUpdatedBill.getAccreditUserID());
    voBill.setOperatelogVO(voUpdatedBill.getOperatelogVO());
    // 如果不保存条码清空条码
    // nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.beforSaveBillVOBarcode(
    // m_bBarcodeSave, voBill);
    // 是否保存数量不一致的条码
    voBill.setSaveBadBarcode(m_bBadBarcodeSave);
    // 是否保存条码
    // voBill.setSaveBarcode(true);
    // 修改人：刘家清 修改日期：2007-9-3下午03:29:47 修改原因：如果不保存条码清空条码
    nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl
        .beforSaveBillVOBarcode(voBill);

    // add by liuzy 2007-11-02 10:16 压缩数据
    ObjectUtils.objectReference(voBill);

    ArrayList alRetData = (ArrayList) nc.ui.pub.pf.PfUtilClient
        .processAction("IMPORTBARCODE", getBillType(), getEnvironment()
            .getLogDate(), voBill, null);
    // 检查条码完整，完整则查找指定参数目录下的Excel条码文件并删除
    OffLineCtrl ctrl = new OffLineCtrl(this);
    ctrl.directSaveDelete(voBill);

    return alRetData;
  }

  /**
   * 创建者：张欣 功能：关联录入 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void onJointAdd(ButtonObject bo) {
  
    if (isNeedBillRefWithBillType()){
      // 当前是列表形式时，首先切换到表单形式
      if (BillMode.List == getM_iCurPanel())
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_SWITCH));
      // onSwitch();
      nc.ui.pub.pf.PfUtilClient.retAddBtn(getButtonManager().getButton(
          ICButtonConst.BTN_ADD), getEnvironment().getCorpID(),
          getBillType(), bo);
      showSelBizType(bo);
      // updateButtons();
      //SCMEnv.out("刘家清测试跟踪，重新设定的按钮初始化菜单时的按钮: "+getBillTypeCode());
      //isSetButtons = false;
      setButtons();
    }

  }

  /**
   * 创建者：王乃军 功能：确认（保存）处理 参数：无 返回： true: 成功 false: 失败
   * 
   * 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 2001/10/29,wnj,拆分出保存新增/保存修改两个方法，使得更规范。
   * 
   * 
   * 
   */
  public boolean onSave() {
    long lStartTime = System.currentTimeMillis();
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
        "UCH044")/* @res "正在保存" */);

//    if(!getBillCardPanel().getBillData().execBodyValidateFormulas())
//      return false;
    //支持验证公式 陈倪娜 090909
    if(!getBillCardPanel().getBillData().execValidateFormulas())
             return false;


    boolean bSave = false;
    bSave = onSaveBase();
    // 基类中保存后的动作
    if (bSave) {
      
      GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
      
      GeneralBillUICtl.processOrdItem(getBillCardPanel(), false);
      
      getM_utfBarCode().setText(null);
      
    }
    if (m_bOnhandShowHidden) {
      m_pnlQueryOnHand.clearCache();
      m_pnlQueryOnHand.fresh();
    }
    // v5 如果是参照生长多张单据，保存成功后要删除列表下的对应单据
    if (m_bRefBills && bSave) {
      // 删除列表下的对应单据
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
   * 创建者：yangbo
   * 
   * 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 拉式生成多单时保存来源单据相同的单据，需要刷新相关单据的来源单据表头ts
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
   * 创建者：yangbo
   * 
   * 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 拉式生成多单时保存来源单据相同的单据，需要刷新相关单据的来源单据表头ts
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
   * 方法功能描述：补充某些字段的空值。
   * <p>
   * <b>参数说明</b>
   * <p>
   * 
   * @author duy
   * @time 2008-4-28 下午03:51:47
   * @deprecated
   */
/*  private void fillItemNullValue() {
    GeneralBillHeaderVO header = getM_voBill().getHeaderVO();
    GeneralBillItemVO[] items = getM_voBill().getItemVOs();

    // 仓库
    if (header.getCwarehouseid() == null
        || header.getCwarehouseid().length() == 0)
      header.setCwarehouseid(GenMethod.STRING_NULL);
    // 部门
    if (header.getCdptid() == null || header.getCdptid().length() == 0)
      header.setCdptid(GenMethod.STRING_NULL);
    // 收发类别
    if (header.getCdispatcherid() == null
        || header.getCdispatcherid().length() == 0)
      header.setCdispatcherid(GenMethod.STRING_NULL);
    // 库存组织
    if (header.getPk_calbody() == null
        || header.getPk_calbody().length() == 0)
      header.setPk_calbody(GenMethod.STRING_NULL);
    // 库管员
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
   * 创建者：王乃军 功能：确认（保存）处理 参数：无 返回： true: 成功 false: 失败
   * 
   * 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 2001/10/29,wnj,拆分出保存新增/保存修改两个方法，使得更规范。
   * 
   * 
   * 
   */
  public boolean onSaveBase() {
    try {
      nc.vo.ic.pub.bill.Timer t = new nc.vo.ic.pub.bill.Timer();
      m_timer.start("保存开始");/*-=notranslate=-*/
      t.start();
      // 滤去表单形式下的空行
      filterNullLine();

      m_timer.showExecuteTime("filterNullLine");
      // 无表体行 ------------ EXIT -------------------
      if (getBillCardPanel().getRowCount() <= 0) {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000072")/* @res "请输入表体行!" */);
        // 不添加新行 add by hanwei 2004-06-08 ,调拨出入库单有些情况下不能自制
        return false;
      }
      // added by zhx 030626 检查行号的合法性; 该方法应放在过滤空行的后面。
      if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(
          getBillCardPanel(), IItemKey.CROWNO)) {
        return false;
      }
      // 当前的表体行数
      int iRowCount = getBillCardPanel().getRowCount();
      // 界面的单据数据
      GeneralBillVO voInputBill = null;
      // 从界面中获得需要的数据
      voInputBill = getBillVO();
      // 得到数据错误，出错 ------------ EXIT -------------------
      if (voInputBill == null || voInputBill.getParentVO() == null
          || voInputBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        return false;
      }
      // 输入的表体行
      GeneralBillItemVO voInputBillItem[] = voInputBill.getItemVOs();
      // 得到数据行
      int iVORowCount = voInputBillItem.length;
      // 得到数据行和界面行数不一致，出错 ------------ EXIT -------------------
      if (iVORowCount != iRowCount) {
        SCMEnv.out("data error." + iVORowCount + "<>" + iRowCount);
        return false;
      }
      m_timer.showExecuteTime("From fliterNullLine Before setIDItems");
      // VO校验准备数据
      getM_voBill().setIDItems(voInputBill);
      // 设置单据类型
      getM_voBill().setHeaderValue("cbilltypecode", getBillType());

      m_timer.showExecuteTime("setIDItems");

      // 重置单据行号zhx 0630:
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
      // VO校验 ------------ EXIT -------------------
      if (!checkVO(getM_voBill())) {

        return false;
      }
      m_timer.showExecuteTime("VO校验");/*-=notranslate=-*/

      // 如果没有单据日期，填写为当前登录日期
      if (getBillCardPanel().getHeadItem("dbilldate") == null
          || getBillCardPanel().getHeadItem("dbilldate")
              .getValueObject() == null
          || getBillCardPanel().getHeadItem("dbilldate")
              .getValueObject().toString().trim().length() == 0) {
        SCMEnv.out("-->no bill date.");
        getM_voBill().setHeaderValue("dbilldate",
            getEnvironment().getLogDate());
      }
      m_timer.showExecuteTime("设置单据类型和单据日期");/*-=notranslate=-*/

      // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
      // "4008bill", "UPP4008bill-000102")/* @res "正在保存，请稍候..." */);

      // 保存的核心方法入口 add by hanwei 2004-04

      // 默认单据状态 add by hanwei
      m_sBillStatus = nc.vo.ic.pub.bill.BillStatus.FREE;
      // 实际m_sBillStatus的赋值在onSaveBaseKernel中的：saveUpdateBill,saveNewBill

      getM_voBill().setIsCheckCredit(true);
      getM_voBill().setIsCheckPeriod(true);
      getM_voBill().setIsCheckAtp(true);
      getM_voBill().setGetPlanPriceAtBs(false);
      getM_voBill().setIsRwtPuUserConfirmFlag(false);

      // 补充空值
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
              "4008busi","UPP4008busi-000403")/* @res "当前网络异常，请检查网络！" */ );
        }
        catch (Exception ee1) {

          BusinessException realbe = nc.ui.ic.pub.tools.GenMethod
              .handleException(null, null, ee1);
          if (realbe != null
              && realbe.getClass() == nc.vo.scm.pub.excp.RwtIcToPoException.class) {
            // 错误信息显示，并询问用户“是否继续？”
            int iFlag = showYesNoMessage(realbe.getMessage());
            // 如果用户选择继续
            if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
              getM_voBill().setIsRwtPuUserConfirmFlag(true);
              continue;
            } else
              return false;
          } else if (realbe != null
              && realbe.getClass() == CreditNotEnoughException.class) {
            // 错误信息显示，并询问用户“是否继续？”
            // 是否继续？ 改为多语形式 modify by qinchao  20081225 圣诞节，共3处替换
            int iFlag = showYesNoMessage(realbe.getMessage()
                + " \r\n" + 
                nc.ui.ml.NCLangRes.getInstance().getStrByID("40080802","ClientUI-000001")/* @res "是否继续" */);
            // 如果用户选择继续
            if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
              getM_voBill().setIsCheckCredit(false);
              continue;
            } else
              return false;
          } else if (realbe != null
              && realbe.getClass() == PeriodNotEnoughException.class) {
            // 错误信息显示，并询问用户“是否继续？”
            int iFlag = showYesNoMessage(realbe.getMessage()
                + " \r\n" + 
                nc.ui.ml.NCLangRes.getInstance().getStrByID("40080802","ClientUI-000001")/* @res "是否继续" */);
            // 如果用户选择继续
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
              // 错误信息显示，并询问用户“是否继续？”
              int iFlag = showYesNoMessage(atpe.getMessage()
                  + " \r\n" + 
                  nc.ui.ml.NCLangRes.getInstance().getStrByID("40080802","ClientUI-000001")/* @res "是否继续" */);
              // 如果用户选择继续
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
      
      

      // 是普通新增、或修改
      if (BillMode.New == getM_iMode() || BillMode.Update == getM_iMode()) {
        // necessary！//刷新单据状态
        getBillCardPanel().updateValue();
        m_timer.showExecuteTime("updateValue");
        // coperatorid
        setM_iMode(BillMode.Browse);
        
        getEditCtrl().resetCardEditFlag(getBillCardPanel());
        // 不可编辑
        getBillCardPanel().setEnabled(false);
        // 重设按钮状态
        setButtonStatus(false);
        m_timer.showExecuteTime("setButtonStatus");

        // 清空现存量
        // 屏蔽 by hanwei 2003-11-13 避免保存后界面选择出现存量为空
        // m_voBill.clearInvQtyInfo();
        // 选中第一行
        //getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
        // 重置序列号是否可用
        setBtnStatusSN(0, false);
        // 刷新第一行现存量显示
        //setTailValue(0);
        m_timer.showExecuteTime("刷新第一行现存量显示");/*-=notranslate=-*/
      }

//      if (m_sBillStatus != null && !m_sBillStatus.equals(BillStatus.FREE)
//          && !m_sBillStatus.equals(BillStatus.DELETED)) {
//        SCMEnv.out("**** saved and signed ***");
//        freshAfterSignedOK(m_sBillStatus);
//        m_timer.showExecuteTime("freshAfterSignedOK");
//      }
      
      m_sBillStatus = getM_voBill().getHeaderVO().getFbillflag().toString();
      
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "common", "UCH005")/* @res "保存成功" */);

      // 对于有来源的单据进行不同的界面控制；zhx 1130
      ctrlSourceBillUI(true);
      m_timer.showExecuteTime("来源单据界面控制");/*-=notranslate=-*/
      t.stopAndShow("保存合计");/*-=notranslate=-*/

      // save the barcodes to excel file according to param IC***
      m_timer.showExecuteTime("开始执行保存条码文件");/*-=notranslate=-*/
      OffLineCtrl ctrl = new OffLineCtrl(this);
      ctrl.saveExcelFile(getM_voBill(), getCorpPrimaryKey());
      m_timer.showExecuteTime("执行保存条码文件结束");/*-=notranslate=-*/
      nc.ui.ic.pub.tools.GenMethod.reSetRowColorWhenNOException(getBillCardPanel());
      return true;

    } catch (java.net.ConnectException ex1) {
      SCMEnv.out(ex1.getMessage());
      if (showYesNoMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000104")/*
                             * @res
                             * "当前网络中断，是否将单据信息保存到默认目录："
                             */
      ) == MessageDialog.ID_YES) {
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_EXPORT_TO_DIRECTORY));
        // onBillExcel(1);// 保存单据信息到默认目录
      }
    }
    catch (Exception e) {

      if (e instanceof nc.vo.ic.ic009.PackCheckBusException) {

        handleException(e);
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000105")/* @res "保存出错。" */);
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
      // added by lirr 20092009-7-2下午01:31:53

      else if (e instanceof nc.vo.scm.ic.exp.ICSNException){
        String sErrorMessage = ((ICSNException)e).getHint();
            showErrorMessage(sErrorMessage);
      }
      else {

        handleException(e);
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000105")/* @res "保存出错。" */);
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
        // 更改颜色
        nc.ui.ic.pub.tools.GenMethod.setRowColorWhenException(getBillCardPanel(),ee);
      }

    }
    return false;
  }

  /**
   * 创建者：保存基本方法中的核心方法 功能：确认（保存）处理 参数：无 例外： 日期：(2004-4-1 9:23:32)
   * 修改日期，修改人，修改原因，注释标志： 2004-4-1 韩卫
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
            
        //二次开发扩展
        getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voBill});
        saveNewBill(voBill);
        //二次开发扩展
            getPluginProxy().afterAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voBill});
      } else // 修改
      if (BillMode.Update == getM_iMode()) {
        // 得到修改后的单据VO
        GeneralBillVO voUpdatedBill = getBillChangedVO();
        voUpdatedBill.setAccreditUserID(sAccreditUserID);
        voUpdatedBill.setTs(voBill);
        voUpdatedBill.setOperatelogVO(log);
        // 执行修改保存...有错误抛出异常
        // 执行修改保存
            
        //二次开发扩展
        getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voUpdatedBill});
        saveUpdatedBill(voUpdatedBill);
        //二次开发扩展
            getPluginProxy().afterAction(nc.vo.scm.plugin.Action.SAVE, new GeneralBillVO[]{voUpdatedBill});
      } else {
        SCMEnv.out("status invalid...");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000106")/*
                                     * @res
                                     * "状态错误。 "
                                     */);
      }
      
  
      
    } catch (RightcheckException e) {
      showErrorMessage(e.getMessage()
          + nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
              "UPP4008bill-000069")/* @res ".\n权限校验不通过,保存失败! " */);
      getAccreditLoginDialog().setCorpID(getEnvironment().getCorpID());
      getAccreditLoginDialog().clearPassWord();
      if (getAccreditLoginDialog().showModal() == nc.ui.pub.beans.UIDialog.ID_OK) {
        String sUserID = getAccreditLoginDialog().getUserID();
        if (sUserID == null) {
          throw new Exception(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("4008bill", "UPP4008bill-000070")/*
                                       * @res
                                       * "权限校验不通过,保存失败. "
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
                                     * "权限校验不通过,保存失败. "
                                     */);

      }

    } catch (Exception e) {
      // 修改人：刘家清 修改日期：2007-10-31下午04:46:03
      // 修改原因：因为可能RightcheckException会被包一层，所以再做下处理，最多处理三层。
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
                                   * ".\n权限校验不通过,保存失败! "
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
                                         * "权限校验不通过,保存失败. "
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
                                       * "权限校验不通过,保存失败. "
                                       */);

        }

      } else
        throw e;
    }

  }

  /**
   * 创建者：王乃军 功能：货位指定
   * 
   * 参数： 返回： 例外： 日期：(2003-7-2 19:23:32) 修改日期，修改人，修改原因，注释标志：
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
          "4008bill", "UPP4008bill-000107")/* @res "请先选择仓库" */);
    } else {
      getLocSelDlg().setWhID(sNewWhID);
      if (getLocSelDlg().showModal() == LocSelDlg.ID_OK) {

        String cspaceid = getLocSelDlg().getLocID();
        String csname = getLocSelDlg().getLocName();
        // 存货列值暂存
        Object oTempValue = null;
        // 表体model
        nc.ui.pub.bill.BillModel bmBill = getBillCardPanel()
            .getBillModel();
        // 存货列号，效率高一些。
        int iInvCol = bmBill.getBodyColByKey(IItemKey.INVID);

        // 必须有存货列
        if (bmBill != null && iInvCol >= 0
            && iInvCol < bmBill.getColumnCount()) {
          // 行数
          int iRowCount = getBillCardPanel().getRowCount();
          // 从后向前删
          for (int line = 0; line < iRowCount; line++) {
            // 存货填了
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
   * 创建者：王乃军 功能：查询指定的单据。 参数： billType, 当前单据类型 billID, 当前单据ID businessType,
   * 当前业务类型 operator, 当前用户ID pk_corp, 当前公司ID
   * 
   * 返回 ：单据vo 例外 ： 日期 ： (2001 - 5 - 9 9 : 23 : 32) 修改日期 ， 修改人 ， 修改原因 ， 注释标志 ：
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

      // 启用进度条
      // getPrgBar(PB_QRY).start();
      long lTime = System.currentTimeMillis();
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000012")/* @res "正在查询，请稍候..." */);
      ArrayList<GeneralBillVO> alListData = (ArrayList) GeneralBillHelper.queryBills(
          getBillType(), voCond);

      showTime(lTime, "查询");/*-=notranslate=-*/
      // 执行扩展公式.目前只被销售出库单UI重载.
      //       
      // execExtendFormula(alListData);
      // //公式情况 第一条表体记录公式查询补充数据 修改 hanwei 2003-03-05
      if (alListData != null && alListData.size() > 0) {
        //
        setAlistDataByFormula(GeneralBillVO.QRY_FIRST_ITEM_NUM,
            alListData);
        SCMEnv.out("0存货公式解析成功！");/*-=notranslate=-*/
        //
        setM_alListData(alListData);
        // //查表体
        // // 屏蔽 by hanwei 2003-06-17 ,多余查询
        // //qryItems(new int[] { 0 }, new String[] { billID });
        // //表头执行公式
        voRet = (GeneralBillVO) alListData.get(0);

      }

    } catch (Exception e) {
      handleException(e);
      showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000015")/* @res "查询出错：" */
          + e.getMessage());
    }
    return voRet;
  }

  /**
   * 创建者：王乃军 功能：查指定序号单据的货位/序列号数据,只用于浏览状态下。 参数：指定查询模式 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void qryLocSN(int iBillNum, int iMode) {
    GeneralBillVO voMyBill = null;
    // arraylist 有的话用他的,没有话,就是新的.
    if (getM_alListData() != null && getM_alListData().size() > iBillNum
        && iBillNum >= 0)
      voMyBill = (GeneralBillVO) getM_alListData().get(iBillNum);
    qryLocSN(voMyBill, iMode);
  }

  protected void qryLocSN(GeneralBillVO voMyBill, int iMode) {
    try {
      if (!needToQryLocSN(voMyBill)) {
    	  // 新增状态等非浏览态不需要查询货位序列号
    	  return;
      }

      // 测试是否已经读过这些数据了。
      boolean hasLoc = this.hasQryLoc(voMyBill);
      boolean hasSN = this.hasQrySN(voMyBill);
      // 已经读过数据,把数据放到成员变量中，并同步vo(needless now )2003-08-07
      if (hasLoc)
        m_alLocatorData = voMyBill.getLocators();
      if (hasSN)
        m_alSerialData = voMyBill.getSNs();
      if (hasLoc && hasSN)
        return;

      // =============================================================
      // 初始化数组 if necessary
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
        // 测试实出/入数量
        if (voMyBill.getItemValue(i, "ninnum") != null
            && voMyBill.getItemValue(i, "ninnum").toString()
                .length() > 0
            || voMyBill.getItemValue(i, "noutnum") != null
            && voMyBill.getItemValue(i, "noutnum").toString()
                .length() > 0)
          break;

      if (i >= iRowCount) // 无数量
        return; // =============================================================

      // 先清空货位、序列号数据
      Integer iSearchMode = null;
      // 需要查货位
      if (!hasLoc
          && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.LOC)) {
        iSearchMode = new Integer(iMode);
      }
      // 需要查序列号
      if (!hasSN
          && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.SN)) {
        iSearchMode = new Integer(iMode);
      }
      if (iSearchMode == null)
        return;

      // ////////////////////////////iMode); //查货位 & 序列号 3 or 序列号 4
      ArrayList alAllData = (ArrayList) GeneralBillHelper.queryInfo(
          iSearchMode, voMyBill.getPrimaryKey());
      // 先清空货位、序列号数据
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

      // 如果有的话置货位数据
      if (alTempLocatorData != null) {
        // 放到vo中，根据表体id执行数据匹配。
        voMyBill.setLocators(alTempLocatorData);
        // getLocators处理后的 by hanwei 2004-01-06
        m_alLocatorData = voMyBill.getLocators();
      }
      // 如果有的话置序列号数据
      if (alTempSerialData != null) {
        // 放到vo中，根据表体id执行数据匹配。
        voMyBill.setSNs(alTempSerialData);
        // getSNs处理后的 by hanwei 2004-01-06
        m_alSerialData = voMyBill.getSNs();
      }
    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }

  }
  
  /**
   * 检查是否需要查询货位序列号信息
   * @return
   */
  private boolean needToQryLocSN(GeneralBillVO voMyBill) {
      // ERRRR,needless to read,如新增单据的情况，
      if (voMyBill == null || voMyBill.getPrimaryKey() == null) {
        int iFaceRowCount = getBillCardPanel().getRowCount();
        // 初始化数组 if necessary
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
    	// 非浏览态，不需要查询货位序列号
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
        // 有序列号管理的行但此行还没有序列号。
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
   * 此处插入方法说明。 创建日期：(2004-2-10 9:11:33)
   * 
   * @param iBillNum
   *            int
   * @param iMode
   *            int 专用于列表下表体排序后的数据打印货位的操作
   */
  public void qryLocSNSort(int iBillNum, int iMode) {
    try {
      GeneralBillVO voMyBill = null;
      // arraylist 有的话用他的,没有话,就是新的.
      // 如果表体排过序就用排序后的数据否则取全局变量中的数据
      // if (m_sLastKey != null && getM_voBill() != null)
      // 修改人：刘家清 修改日期：2007-10-29下午03:12:03
      // 修改原因：只有在卡片状态下，才用考虑到排序后打印顺序问题，来取getM_voBill()
      if (m_sLastKey != null && getM_voBill() != null
          && getM_iMode() == BillMode.Browse
          && getM_iCurPanel() == BillMode.Card)
        voMyBill = getM_voBill();
      else if (getM_alListData() != null
          && getM_alListData().size() > iBillNum && iBillNum >= 0)
        voMyBill = (GeneralBillVO) getM_alListData().get(iBillNum);

      // ERRRR,needless to read,如新增单据的情况，
      if (voMyBill == null || voMyBill.getPrimaryKey() == null) {
        int iFaceRowCount = getBillCardPanel().getRowCount();
        // 初始化数组 if necessary
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
      // 只在浏览状态下，才会需要赋值和查询，否则会取消已做的修改。
      if (getM_iMode() == BillMode.Browse) {
        // 测试此单据是否填了数量，如填了则需要继续执行，否则单据本来就没有这些数据，不用读了
        int i = 0, iRowCount = voMyBill.getItemCount();
        // 测试是否已经读过这些数据了。

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
          // 有序列号管理的行但此行还没有序列号。
          if (voInv != null && voInv.getIsSerialMgt() != null
              && voInv.getIsSerialMgt().intValue() != 0
              && voMyBill.getItemValue(i, "serial") == null) {
            hasSN = false;
            break;
          }
        }
        // 已经读过数据,把数据放到成员变量中，并同步vo(needless now )2003-08-07
        if (hasLoc)
          m_alLocatorData = voMyBill.getLocators();
        if (hasSN)
          m_alSerialData = voMyBill.getSNs();
        if (hasLoc && hasSN)
          return;

        // =============================================================
        // 初始化数组 if necessary
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
          // 测试实出/入数量
          if (voMyBill.getItemValue(i, "ninnum") != null
              && voMyBill.getItemValue(i, "ninnum").toString()
                  .length() > 0
              || voMyBill.getItemValue(i, "noutnum") != null
              && voMyBill.getItemValue(i, "noutnum").toString()
                  .length() > 0)
            break;

        if (i >= iRowCount) // 无数量
          return; // =============================================================

        // 先清空货位、序列号数据
        Integer iSearchMode = null;
        // 需要查货位
        if (!hasLoc
            && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.LOC)) {
          iSearchMode = new Integer(iMode);
        }
        // 需要查序列号
        if (!hasSN
            && (iMode == QryInfoConst.LOC_SN || iMode == QryInfoConst.SN)) {
          iSearchMode = new Integer(iMode);
        }
        if (iSearchMode == null)
          return;
        // WhVO voWh = voMyBill.getWh();
        // 货位管理的仓库，并且还没有货位，需要读货位数据、序列号

        // iMode = 3;
        // Integer iSearchMode = new Integer(iMode);

        // ////////////////////////////iMode); //查货位 & 序列号 3 or 序列号 4
        ArrayList alAllData = (ArrayList) GeneralBillHelper.queryInfo(
            iSearchMode, voMyBill.getPrimaryKey());
        // 先清空货位、序列号数据
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

        // 如果有的话置货位数据
        if (alTempLocatorData != null) {
          // 放到vo中，根据表体id执行数据匹配。
          voMyBill.setLocators(alTempLocatorData);
          // getLocators处理后的 by hanwei 2004-01-06
          m_alLocatorData = voMyBill.getLocators();
        }
        // 如果有的话置序列号数据
        if (alTempSerialData != null) {
          // 放到vo中，根据表体id执行数据匹配。
          voMyBill.setSNs(alTempSerialData);
          // getSNs处理后的 by hanwei 2004-01-06
          m_alSerialData = voMyBill.getSNs();

        }

      }

    } catch (Exception e) {
      nc.vo.scm.pub.SCMEnv.error(e);
    }

  }

  /**
   * 根据来源单据拉式生成的库存单据需要重置表头Item, 以便将name显示在列表界面和打印中得到item的名称. 功能描述: 输入参数: 返回值:
   * 异常处理: 日期:
   */
  public void resetAllHeaderRefItem() {
    if (getBillCardPanel().getHeadItem("cdispatcherid") != null) {
      // 收发类别
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cdispatcherid").getComponent()).getRefName();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cdispatchername", sName);
    }
    if (getBillCardPanel().getHeadItem("cinventoryid") != null) {
      // 加工品
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cinventoryid").getComponent()).getRefName();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cinventoryname", sName);
    }
    // 关联录入时, 调用库存组织afterEdit必须放在仓库的前面
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
      // 库存组织
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
      // 库管员

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cwhsmanagerid").getComponent()).getRefName();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cwhsmanagername", sName);
    }
    if (getBillCardPanel().getHeadItem("cdptid") != null) {
      // 部门
      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cdptid").getComponent()).getRefName();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cdptname", sName);
    }
    if (getBillCardPanel().getHeadItem("cbizid") != null) {
      // 业务员

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cbizid").getComponent()).getRefName();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cbizname", sName);
    }
    if (getBillCardPanel().getHeadItem("cproviderid") != null) {
      // 供应商

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cproviderid").getComponent()).getRefName();
      String sRefPK = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cproviderid").getComponent()).getRefPK();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cprovidername", sName);
    }
    if (getBillCardPanel().getHeadItem("ccustomerid") != null) {
      // 客户

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("ccustomerid").getComponent()).getRefName();
      String sRefPK = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("ccustomerid").getComponent()).getRefPK();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("ccustomername", sName);
      // 根据客户或供应商过滤发运地址的参照

      filterVdiliveraddressRef(true, -1);

    }
    if (getBillCardPanel().getHeadItem("cbiztype") != null) {
      // 业务类型

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cbiztype").getComponent()).getRefName();
      // String sPK = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
      // .getHeadItem("cbiztype").getComponent()).getRefPK();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cbiztypename", sName);
    }
    if (getBillCardPanel().getHeadItem("cdilivertypeid") != null) {
      // 发运方式

      String sName = ((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
          .getHeadItem("cdilivertypeid").getComponent()).getRefName();
      // 保存名称以在列表形式下显示。
      if (getM_voBill() != null)
        getM_voBill().setHeaderValue("cdilivertypename", sName);
    }
    
  }

  /**
   * 此处插入方法说明。 更新单行 创建日期：(2002-11-27 10:32:34)
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

    // 通过条码规则中定义的关键字列
    String[] sPrimaryKeyItems = vo.getMatchPrimaryKeyItems();

    BarCodeParseVO[] barCodeParseVOs = new BarCodeParseVO[] { vo };
    boolean bBox = false;
    scanadd(sRowPrimaryKey, barCodeParseVOs, bBox, sPrimaryKeyItems);
    // 光标回条码扫描框
    m_utfBarCode.requestFocus();
    return;
  }

  /**
   * 此处插入方法说明。 箱条码扫描解析到表体 创建日期：(2004-3-12 15:57:22)
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

    // 需要通过barCodeGroupHeadVO获得关键字列
    // 目前条码装箱没有设置存货自由项等属性
    // 所以设置成存货主键
    String[] sPrimaryKeyItems = new String[] { nc.vo.ic.pub.barcodeparse.BarcodeparseCtrl.InvManKey };

    String sRowPrimaryKey = getBarcodeCtrl().getBarGroupHeadRowPrimaryKey(
        getEnvironment().getCorpID(), barCodeGroupHeadVO,
        sPrimaryKeyItems);
    boolean bBox = true;
    scanadd(sRowPrimaryKey, barCodeParseVOs, bBox, sPrimaryKeyItems);
    // 光标回条码扫描框
    m_utfBarCode.requestFocus();
  }

  /**
   * 此处插入方法说明。 创建日期：(2004-3-12 22:26:14)
   * 
   * @param sRowPrimaryKey
   *            java.lang.String
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]
   * 
   * 修改人：刘家清 修改日期：2007-7-2下午06:29:22 修改原因：V53
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
                                   * "条码解析失败，请检查条码联合关键字有无对应档案"
                                   */);       
          }
          
        }
      // 取当前行
      int iRow = getBillCardPanel().getBillTable().getSelectedRow();
       
      
      
      
      
      if (sRowPrimaryKey != null && barCodeParseVOs != null
          && sRowPrimaryKey.length() > 4
          && !sRowPrimaryKey.startsWith("NULL")) // 箱条码中存在存货信息
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
                                 * "条码对应的辅单位不合法"
                                 */);
              
            }
            
          }

        // 到单据中查找行符合条件的行arraylist ,优先处理选中行
        ArrayList alResultTemp = getBarcodeCtrl().scanBillCardItem(
            sRowPrimaryKey, getM_voBill(), iRow, sPrimaryKeyItems);
        
        if (!bBox && (alResultTemp == null || alResultTemp.size() == 0) && null != getBillType() && ("45".equals(getBillType())||"4C".equals(getBillType())||"4Y".equals(getBillType()))){
          
          alResultTemp = getBarcodeCtrl().scanBillCardItem(
              sRowPrimaryKey, getM_voBill(), iRow, sPrimaryKeyItems,barCodeParseVOs);
          if (null != alResultTemp && 0 < alResultTemp.size())
            isNeedAppend = true;
        }

        ArrayList alResult = new ArrayList();

        // 对箱条码，只适用第一行，处于实际数量填充的校验
        // 否则无法处理这种情况的数据异常回滚
        if (bBox && alResultTemp != null && alResultTemp.size() > 0) {
          alResult.add(alResultTemp.get(0));
        } else
          alResult = alResultTemp;

        // 如果arraylist为空，或len==0，提示没有对应存货数据
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
                                   * "请先扫描录入存货主条码"
                                   */);

            }

          // (1)对调拨出入库单不允许自动增加行的情况下
          // (2)对其他入和其他出界面上，也不允许扫描自动增加行
          if (getButtonManager()
              .getButton(ICButtonConst.BTN_LINE_ADD) != null
              && getButtonManager().getButton(
                  ICButtonConst.BTN_LINE_ADD).isEnabled()
              && getBarcodeCtrl().isAddNewInvLine()) {
            // 没有对应的存货，自动增加一行存货
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
            // 置回光标
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
             * "扫描识别出新的存货条码，但当前单据界面不允许新增加存货行！" );
             */
            throw new BusinessException(nc.ui.ml.NCLangRes
                .getInstance().getStrByID("4008bill",
                    "UPP4008bill-000127")
            /*
             * @res "扫描识别出新的存货条码，但当前单据界面不允许新增加存货行！"
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
                                   * "请先扫描录入存货主条码"
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
                                   * "非次条码管理存货不支持扫描录录入次条码"
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
                                   * "同一存货，同一行，只能扫描录入同一条码规则的主条码"
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
                                   * "同一存货，同一行，只能扫描录入同一条码规则的次条码"
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
                                   * "请先扫描录入存货主条码"
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
                                   * "非主条码管理存货不支持扫描录录入主条码"
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
                                   * "请先扫描录入存货次条码"
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
                                   * "非辅计量管理存货不支持扫描录录入按辅数量增加的条码"
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
                  // 自由项
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
                      // 修改人：刘家清 修改日期：2007-12-28下午04:49:54 修改原因：同步备份VO和界面上的invvo
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
      } else // 条码中不存在存货信息
      {
        // 需要找当前焦点行，进行扫描处理
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
                                 * "请先扫描录入存在存货信息的主条码"
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
                                   * "条码对应的辅单位不合法"
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
                                 * "请先扫描录入存货主条码"
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
                                 * "非次条码管理存货不支持扫描录录入次条码"
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
                                 * "同一存货，同一行，只能扫描录入同一条码规则的主条码"
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
                                 * "同一存货，同一行，只能扫描录入同一条码规则的次条码"
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
                                 * "请先扫描录入存货主条码"
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
                                 * "非主条码管理存货不支持扫描录录入主条码"
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
                                 * "请先扫描录入存货次条码"
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
                                 * "非辅计量管理存货不支持扫描录录入按辅数量增加的条码"
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
   * 过滤发运地址参照 创建者：张欣 功能：初始化参照过滤 参数： 返回： 例外： 日期：(2001-7-17 10:33:20)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  public void filterVdiliveraddressRef(boolean ishead, int row) {
    try {
      // 过滤表头发运地址
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
   * 此处插入方法说明。 供子类使用，检验条码编辑修改数量和应发数量等数量关系的业务逻辑 创建日期：(2004-4-29 9:08:49)
   * 
   * @param event1
   *            nc.ui.pub.bill.BillEditEvent
   */
  public void scanCheckNumEdit(nc.ui.pub.bill.BillEditEvent event1)
      throws Exception {

  }

  /**
   * 此处插入方法说明。 把条码VO数据填充到表体 创建日期：(2004-3-12 20:15:07)
   * 
   * @return int
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]：条码数据 VO
   * 
   * @param iCurFixLine
   *            int：当前的行,
   * 
   * @param iNumUsed
   *            int：已经填充使用过的条码数量
   * 
   * @param bAllforFix
   *            java.lang.Boolean ：是否要把剩余的条码都填上
   * 
   * 对于只有一行符合条件的存货情况和最后一行情况下，要把剩余的条码都填上
   * 
   * 返回本次填充条码的数量
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
          "4008bill", "UPP4008bill-000111")/* @res "请选择有存货数据的行！" */);
    }
    // 填充数量
    int iNumforUse = scanfixline_fix(barCodeParseVOs, iCurFixLine,
        iNumUsed, bAllforFix); // 本次填充条码的数量

    // 保存条码
    getBarcodeCtrl().scanfixline_save(barCodeParseVOs, iCurFixLine,
        iNumUsed, iNumforUse, getM_voBill().getItemVOs(),true); // 本次填充条码的数量

    return iNumforUse;
  }

  /**
   * 此处插入方法说明。 把条码VO数据填充到表体 创建日期：(2004-3-12 20:15:07)
   * 
   * @return int
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]：条码数据 VO
   * 
   * @param iCurFixLine
   *            int：当前的行,
   * 
   * @param iNumUsed
   *            int：已经填充使用过的条码数量
   * 
   * @param bAllforFix
   *            java.lang.Boolean ：是否要把剩余的条码都填上
   * 
   * 对于只有一行符合条件的存货情况和最后一行情况下，要把剩余的条码都填上
   * 
   * 返回本次填充条码的数量
   * 
   */
  protected int scanfixline_fix(BarCodeParseVO[] barCodeParseVOs,
      int iCurFixLine, int iNumUsed, boolean bAllforFix) throws Exception {

    if (barCodeParseVOs == null || barCodeParseVOs.length == 0
        || iCurFixLine < 0) {
      return 0;
    }

    // 根据"是否按辅单位增加数量"属性，如果是则辅数量自动加一；否则主数量自动加一。
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

    int iNumforUse = 0; // 本次填充条码的数量
    if (getBillCardPanel().getBodyItem("cinventorycode") != null) {
      // 实际发数量
      UFDouble nFactNum = null;
      // 应发数量
      UFDouble nShouldNum = null;
      UFDouble nFactBarCodeNum = null; // 实际发，实际入条码数量

      nc.vo.ic.pub.bc.BarCodeVO[] oldBarcodevos = getM_voBill()
          .getItemVOs()[iCurFixLine].getBarCodeVOs();

      if (oldBarcodevos == null || oldBarcodevos.length == 0)
        nFactBarCodeNum = UFDZERO;
      else {
        nFactBarCodeNum = UFDZERO;
        for (int i = 0; i < oldBarcodevos.length; i++) {
          // 修改人：刘家清 修改日期：2007-11-5上午11:25:59 修改原因：已删除条码不能统计进来
          if (oldBarcodevos[i] != null
              && oldBarcodevos[i].getNnumber() != null
              && oldBarcodevos[i].getStatus() != nc.vo.pub.VOStatus.DELETED)
            nFactBarCodeNum = nFactBarCodeNum.add(oldBarcodevos[i]
                .getNnumber());
        }
      }

      // 实际发数量
      Object oNum = getBillCardPanel().getBodyValueAt(iCurFixLine,
          m_sMyItemKey);
      if (oNum == null || oNum.toString().trim().length() == 0) {
        nFactNum = null;
        // 如果没有应发数量，则填充全部数量
      } else
        nFactNum = (UFDouble) oNum;

      // 应发数量

      try {
        oNum = getBillCardPanel().getBodyValueAt(iCurFixLine,
            m_sMyShouldItemKey);
      } catch (Exception e) {
        oNum = null;
        nc.vo.scm.pub.SCMEnv.error(e);
      }
      if (oNum == null || oNum.toString().trim().length() == 0) {
        nShouldNum = null;
        // 如果没有应发数量，则填充全部数量
      } else
        nShouldNum = (UFDouble) oNum;

      boolean bNegative = false; // 是否负数
      if ((nFactNum != null && nFactNum.doubleValue() < 0)
          || (nShouldNum != null && nShouldNum.doubleValue() < 0)) {
        bNegative = true;
      }

      // 分配条码数据到多个匹配行的算法
      iNumforUse = getBarcodeCtrl().scanfixlinenum(barCodeParseVOs,
          oldBarcodevos, iCurFixLine, iNumUsed, bAllforFix, nFactNum,
          nShouldNum);

      nFactBarCodeNum = nFactBarCodeNum.add(iNumforUse);

      // add by hanwei 2004-6-2
      // 条码数量大于应发数量,并且在盘点单生成的其他入出情况霞
      // 不能超过应发数量，这样修改的实发数量等于应发数量
      if (nShouldNum != null && nFactBarCodeNum != null
          && nFactBarCodeNum.doubleValue() > nShouldNum.doubleValue()
          && !getBarcodeCtrl().isOverShouldNum()) {
        nFactBarCodeNum = nShouldNum.abs();
      }

      if (nFactNum == null)
        nFactNum = UFDZERO;
      // 修改人：刘家清 修改日期：2007-9-10下午02:15:22
      // 修改原因：对于单条码管理，并且不保存条码的，每次按条码数量是更新数量
      if ((barCodeParseVOs[0]
          .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
          && (barCodeParseVOs[0]
              .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null)
          && (m_voBill.getItemVOs()[iCurFixLine]
              .getIsprimarybarcode().booleanValue())
          && (!m_voBill.getItemVOs()[iCurFixLine]
              .getIssecondarybarcode().booleanValue())
          && !barCodeParseVOs[0].getBsavebarcode().booleanValue()) {

        // 同步实发数量
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

        // 应发数量不用同步
        nc.ui.pub.bill.BillEditEvent event1 = new nc.ui.pub.bill.BillEditEvent(
            getBillCardPanel().getBodyItem(m_sMyItemKey), nFactNum,
            m_sMyItemKey, iCurFixLine);
        // 检查数量编辑业务逻辑
        scanCheckNumEdit(event1);
        afterEdit(event1);
        // 执行模版公式
        getGenBillUICtl().execEditFormula(getBillCardPanel(),
            iCurFixLine, m_sMyItemKey);

        // 触发单据行状态为修改
        if (getBillCardPanel().getBodyValueAt(iCurFixLine,
            IItemKey.NAME_BODYID) != null)
          getBillCardPanel().getBillModel().setRowState(iCurFixLine,
              BillMode.Update);

      } else {

        // 实发数量小于条码数量，才去修改界面上的实发数量
        if (nFactNum.doubleValue() < nFactBarCodeNum.doubleValue()
            && !((barCodeParseVOs[0]
                .getAttributeValue(BarcodeparseCtrl.VBARCODE) != null)
                && (barCodeParseVOs[0]
                    .getAttributeValue(BarcodeparseCtrl.VBARCODESUB) == null)
                && (m_voBill.getItemVOs()[iCurFixLine]
                    .getIsprimarybarcode().booleanValue()) && (m_voBill
                .getItemVOs()[iCurFixLine]
                .getIssecondarybarcode().booleanValue()))) {

          // 同步实发数量
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

          // 应发数量不用同步
          nc.ui.pub.bill.BillEditEvent event1 = new nc.ui.pub.bill.BillEditEvent(
              getBillCardPanel().getBodyItem(m_sMyItemKey),
              nFactBarCodeNum, m_sMyItemKey, iCurFixLine);
          // 检查数量编辑业务逻辑
          scanCheckNumEdit(event1);
          afterEdit(event1);
          // 执行模版公式
          getGenBillUICtl().execEditFormula(getBillCardPanel(),
              iCurFixLine, m_sMyItemKey);

          // 触发单据行状态为修改
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
   * 此处插入方法说明。 逐行更新条码数据， 对超过应发数量的条码数量，移到下一行更新
   * 
   * barCodeParseVOs:条码数据VO[] alFixRowNO：ArrayList，每行存有存货行行数据，String 类型 int
   * 的数据
   * 
   * 创建日期：(2004-3-12 19:23:05)
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
    int iNumUsed = 0; // 累计统计行数
    int ifixRows = alFixRowNO.size();
    int iCurFixLine = 0; // 更新当前行
    int ifixSingleLineNum = 0;

    for (int i = 0; i < ifixRows; i++) {
      iCurFixLine = Integer.parseInt((String) alFixRowNO.get(i));

      if (ifixRows == 1) {
        // 只有一行，全部填充当前行
        ifixSingleLineNum = scanfixline(barCodeParseVOs, iCurFixLine,
            0, true);
        break;
      } else {
        if (i == ifixRows - 1) // 最后一行，填充所有的数量
        {
          ifixSingleLineNum = scanfixline(barCodeParseVOs,
              iCurFixLine, iNumUsed, true);
          break;
        } else // 中间行填充应发的数量
        {
          ifixSingleLineNum = scanfixline(barCodeParseVOs,
              iCurFixLine, iNumUsed, false);
        }
        iNumUsed = iNumUsed + ifixSingleLineNum;
        if (iNumUsed == iNumAll) {
          // 填充完毕了
          break;
        }
      }

    }
  }

  /**
   * 此处插入方法说明。 把条码VO数据填充到表体的焦点行 创建日期：(2004-3-12 20:15:07)
   * 
   * @return int
   * @param barCodeParseVOs
   *            nc.vo.ic.pub.barcodeparse.BarCodeParseVO[]：条码数据 VO
   */
  protected void scanUpdateLineSelect(BarCodeParseVO[] barCodeParseVOs)
      throws Exception {
    // 取当前行
    int iCurFixLine = 0;
    int rowNow = getBillCardPanel().getBillTable().getSelectedRow();
    if (rowNow < 0) {
      // 提示错误信息
      throw new nc.vo.pub.BusinessException(nc.ui.ml.NCLangRes
          .getInstance().getStrByID("4008bill", "UPP4008bill-000112")/*
                                         * @res
                                         * "请选择对应条码的存货行！"
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
                             * "当前行非条码管理或者行条码已关闭！！"
                             */);
      }
    }
  }

  /**
   * 此处插入方法说明。 功能描述:处理翻页。 作者：程起伍 输入参数:当前页码。 返回值: 异常处理: 日期:(2003-7-5 13:14:52)
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
   * 创建者：王乃军 功能：用于修改后设置新增行的PK,并同时刷新传入的VO. 要保证VO中Item的顺序和界面数据一致。 参数： 返回： 例外：
   * 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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

      // 如果该表体行有条码,则将条码ID置入BarCodeVO中
      if (getM_voBill().getItemVOs()[row].getBarCodeVOs() != null) {
        for (int j = 1; j <= getM_voBill().getItemVOs()[row]
            .getBarCodeVOs().length; j++) {
          if (getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1] != null
              && getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1]
                  .getStatus() == nc.vo.pub.VOStatus.NEW) {
            // 回置条码主键
            getM_voBill().getItemVOs()[row].getBarCodeVOs()[j - 1]
                .setPrimaryKey(alBodyPK.get(pk_count + 1)
                    .toString().trim());
            // 回置表体主键
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

    // 补全订单信息
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

    // 获得批次档案处理需要的参数
    ArrayList<String> al_qBat = BatchCodeDefSetTool.getQueryParas(vo
        .getItemVOs());
    if (null != al_qBat && al_qBat.size() > 0
        && !hm_paras.containsKey(IICParaConst.BatchCodePara))
      hm_paras.put(IICParaConst.BatchCodePara, al_qBat);

    // 获得根据操作员带出部门、业务员需要的参数
    ArrayList<String> al_qDptBizer = new ArrayList<String>();
    al_qDptBizer.add(0, ClientEnvironment.getInstance().getCorporation()
        .getPrimaryKey());// 公司PK
    al_qDptBizer.add(1, ClientEnvironment.getInstance().getUser()
        .getPrimaryKey());// 操作员ID
    hm_paras.put(IICParaConst.DptBizerPara, al_qDptBizer);

    // 获得重设仓库信息的参数
    if (vo.getHeaderValue(IItemKey.WAREHOUSE) != null) {
      ArrayList<String> al_qWhInfo = new ArrayList<String>();
      String sWhID = vo.getHeaderValue(IItemKey.WAREHOUSE).toString().trim();
      al_qWhInfo.add(sWhID);
      hm_paras.put(IICParaConst.ReSetWHInfoPara, al_qWhInfo);
    }

    // 根据库存组织、仓库取成本域
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

    // 根据存货管理档案和库存组织取计划价、计量XX
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
      // 处理订单信息
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

      // 取批次号结果
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
          //设置界面VO的质量等级名称
          String qualitylevelName=(String)ds.getValuesByPk(new String[]{sPk_invbasdoc, sVbatchCode},53);
          item.setAttributeValue("cqualitylevelname", qualitylevelName);
        }
      }
      // 取部门、业务员结果
      if (hmRet.containsKey(IICParaConst.DptBizerPara))
        ICCommonBusi.getHm_userid_psndocvo().put(
            ClientEnvironment.getInstance().getCorporation().getPrimaryKey()
                + ClientEnvironment.getInstance().getUser().getPrimaryKey(),
            (PsndocVO) hmRet.get(IICParaConst.DptBizerPara));
      // 取重设仓库结果
      if (hmRet.containsKey(IICParaConst.ReSetWHInfoPara)) {
        WhVO whvo = (WhVO) hmRet.get(IICParaConst.ReSetWHInfoPara);
        hm_whid_vo.put(whvo.getCwarehouseid(), whvo);
      }
      // 取成本域
      if (hmRet.containsKey(IICParaConst.CostLandPara)) {
        String sCostLand = (String) hmRet.get(IICParaConst.CostLandPara);
        getInvoInfoBYFormula().getHm_calwhid_costid().put(sCalID + sWhID,
            sCostLand);
      }
      // 取存货计划价、计量XX
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
      // 日志异常
      nc.vo.scm.pub.SCMEnv.out(e);
      throw GenMethod.handleException(e.getMessage(), e);
    }

  }
  
  

  /**
   * 此处插入方法说明。 把拉式订单上的数据加载到库存单据界面上 BusiTypeID：业务类型ID,如果没有为null
   * vos:单据的AggregatedValueObject[]，实际数据VO是普通单据的VO 目前该方法被普通单据基本类和采购入库、委外加工入库所引用
   * 创建日期：(2003-10-14 14:29:30)
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
          .getStrByID("4008bill", "UCH003")/* @res "请选择要处理的数据！" */);

    if (vos != null && !(vos instanceof GeneralBillVO[])) {
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000114")/*
                             * @res
                             * "单据类型转换错误！请查看控制台的详细提示信息。"
                             */);
    }

    // v5 不是整合VO，而是验证VO的正确性，清空某些字段值
    GeneralBillVO voRet = VoCombine.combinVo(vos);

    if (voRet != null && voRet.getItemVOs() != null
        && voRet.getItemVOs().length > 0) {
      for (int i = 0, loop = voRet.getItemVOs().length; i < loop; i++)
        voRet.getItemVOs()[i].procLocNumDigit(m_ScaleValue
            .getNumScale(), m_ScaleValue.getAssistNumScale());

      // set user selected 业务类型 20021209
      voRet.setHeaderValue("cbiztype", sBusiTypeID);
      // 通过单据公式容器类执行有关公式解析的方法
      getFormulaBillContainer().formulaHeaders(getFormulaItemHeader(),
          voRet.getHeaderVO());
      transBillDataDeal(voRet);
//      BatchCodeDefSetTool.execFormulaForBatchCode(voRet.getItemVOs());

      // 保存传入的单据VO，向替换件参照传入的存货ID始终是该单据VO中存货ID。
      m_voBillRefInput = voRet;
      // lTime = System.currentTimeMillis();
      // 新增单据1
      onAdd(false, null);

      // 设置退库状态和只能输入负数 add by hanwei 2004-05-08
      if (voRet.getHeaderVO() != null
          && voRet.getHeaderVO().getFreplenishflag().booleanValue()) {
        nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(this,
            true);
      } else
        nc.ui.ic.pub.bill.GeneralBillUICtl.setSendBackBillState(this,
            false);

      // 清空单据号
      voRet.getHeaderVO().setPrimaryKey(null);
      voRet.getHeaderVO().setVbillcode(null);

      // 增加单据行号：zhx added on 20030630 support for incoming bill
      nc.ui.scm.pub.report.BillRowNo.setVORowNoByRule(voRet,
          getBillType(), IItemKey.CROWNO);

      // 重设仓库数据
      resetWhInfo(voRet);

      // 重设所有存货数据
      resetAllInvInfo(voRet);
      
      for(int i = 0 ;i < voRet.getItemVOs().length ; i++){
        setBodyInSpace(i, voRet.getItemVOs()[i].getInv());
      }

      // 重设新增单据初始数据，因为setTempBillVO把他们清掉了。
      int iOriginalItemCount = voRet.getItemCount();
      // 滤掉多余的行。
      GeneralBillItemVO[] itemvo = getM_voBill().filterItem();
      if (itemvo == null || itemvo.length == 0) {
        setM_voBill(null);
        clearUi();
        m_voBillRefInput = null;
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000115")/*
                                     * @res
                                     * "参照单据中存货不能为折扣或劳务属性的存货，请修改存货后再关联录入。"
                                     */);

      } else if (iOriginalItemCount > itemvo.length) { // 真的滤掉了行
        getM_voBill().setChildrenVO(itemvo);
        setImportBillVO(getM_voBill(), false);
        m_voBillRefInput.setChildrenVO(itemvo);

      }
      
      // 重设所有所有条形码状态 修改人:刘家清 修改日期:2007-04-04
      if (itemvo != null) {

        for (int i = 0; i < itemvo.length; i++) {
          if (itemvo[i].getBarCodeVOs() != null)
            nc.vo.ic.pub.SmartVOUtilExt.modifiVOStatus(itemvo[i]
                .getBarCodeVOs(), nc.vo.pub.VOStatus.NEW);
        }
      }
      // 修改人：刘家清 修改日期：2007-10-19上午10:14:29 修改原因：刷新条码规则
      nc.vo.ic.pub.GenMethod.fillGeneralBillVOByBarcode(getM_voBill());

      setNewBillInitData();

      GeneralBillUICtl.calcNordcanoutnumAfterRefAdd(getM_voBill(),
          getBillCardPanel(), getBillType());

      setButtonStatus(true);

      ctrlSourceBillUI(true);

      // set user selected 业务类型 20021209
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
      // end set user selected 业务类型

      // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
      // "SCMCOMMON", "UPPSCMCommon-000133")/* @res "就绪" */);

    } else {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000116")/*
                             * @res
                             * "请双击选择参照录入单据的表头，表体行！"
                             */);
    }
    
    // modified by liuzy 2009-9-21 上午10:15:09 转单后统一计算合计
    getBillCardPanel().getBillModel().setNeedCalculate(true);
    getBillCardPanel().getBillModel().reCalcurateAll();

  }

  /*
   * 针对单据：调拨出入库单 两个作用： 1 设置调入仓库-4Y /出货仓库-4E 参照过滤 2 对调拨出库单表体的收货单位参照作过滤
   * 
   * 邵兵 on Jun 13, 2005
   */
  protected void setBillRefIn4Eand4Y(GeneralBillVO voBill) {
    nc.ui.pub.bill.BillItem bi = null;

    // 调入仓库-4Y /出货仓库-4E 参照

    bi = getBillCardPanel().getHeadItem("cotherwhid");

    if (bi != null) {
      nc.ui.pub.beans.UIRefPane ref = (nc.ui.pub.beans.UIRefPane) bi
          .getComponent();
      if (ref != null) {
        ref.getRefModel().setPk_corp(
            (String) voBill.getHeaderValue("cothercorpid"));

        int fallocflag = CONST.IC_ALLOCINSTORE; // 入库调拨
        String isDirectStor = "N";

        if (voBill.getHeaderValue("fallocflag") != null)
          fallocflag = voBill.getHeaderVO().getFallocflag()
              .intValue();

        if (fallocflag == CONST.IC_ALLOCDIRECT) // 若为直运调拨
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

    // 对调拨出库单表体的收货单位参照作过滤。
    if (!getBillType().equals("4Y")) // 非调拨出库单
      return;
    // 收货单位
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
   * 创建者：王乃军 功能：在表单设置显示VO 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void setBillVO(GeneralBillVO bvo, boolean bUpdateBotton,
      boolean bExeFormule) {
    // 如果是空，清空显示
    if (bvo == null) {
      getBillCardPanel().getBillData().clearViewData();
      getBillCardPanel().updateValue();
      return;
    }
    
//  二次开发扩展
    getPluginProxy().beforeSetBillVOToCard(bvo);
    
    try {
      long lTime = System.currentTimeMillis();
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      getBillCardPanel().removeBillEditListenerHeadTail();

      // 保存一个clone()
      setM_voBill((GeneralBillVO) bvo.clone());

      // 调拨出入库单的两个参照特殊处理
      // 不应放在基类方法中。
      // shawbing on Jun 13, 2005
      if (getBillType().equals("4Y") || getBillType().equals("4E"))
        setBillRefIn4Eand4Y(getM_voBill());

      // 使用公式查询单据需要手工计算生产日期
      // 需要的话，计算生产日期
      if (m_bIsByFormula)
        bvo.calPrdDate();
      SCMEnv.showTime(lTime, "setBillVO:bvo.clone()");
      // 修改人：刘家清 修改日期：2007-9-6上午09:21:35
      // 修改原因：解决要是在卡片下，第一次显示单据时，不执行批次档案公式来更新生产日期和失效日期的问题
      // 执行批次档案公式
      // 修改人：刘家清 修改日期：2007-10-30下午03:59:09
      // 修改原因：在单据是新增状态时，不要去刷新批次信息，因为特殊单可能会在新增保存之前刷新单据界面
      if (null != bvo.getItemVOs()
          && nc.vo.pub.VOStatus.NEW != bvo.getStatus())
        BatchCodeDefSetTool.execFormulaForBatchCode(bvo.getItemVOs());

      // 设置数据
      lTime = System.currentTimeMillis();
      // modified by liuzy 2009-11-5 下午04:38:21 应该放在这里，因为如果转单前界面经过某列排序了
      //那么应该先设置货位、序列号的缓存数据，这样向界面放置数据的时候才能跟着bodyvo一同进行排序处理
    // 整理货位数据，序列号。
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
      // 执行公式
      /** ydy 2005-06-21 */
      if (bExeFormule) {
        getBillCardPanel().getBillModel().execLoadFormula();
        execHeadTailFormulas();
      }
      // 更新状态
      lTime = System.currentTimeMillis();
      getBillCardPanel().updateValue();
      // 清存货现存量数据
      bvo.clearInvQtyInfo();
      // 选中第一行，光标移到表体
      // modified by liuzy 2009-8-18 下午05:00:51 取消选中第一行，为了降连接数
//      getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
      // modified by liuzy 2009-8-18 下午05:02:02 取消选中第一行，为了降连接数
//      getBillCardPanel().transferFocusTo(1);
      // 重置序列号是否可用
      setBtnStatusSN(0, false);

      SCMEnv.showTime(lTime, "setBillVO:3");
      // 刷新现存量显示
      // modified by liuzy 2009-8-18 下午05:02:15 取消选中第一行，为了降连接数
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
    /** 当单据的来源单据为转库单时, 进行界面控制 */
    long lTime = System.currentTimeMillis();
    ctrlSourceBillUI(bUpdateBotton);
    
//  二次开发扩展
    getPluginProxy().afterSetBillVOToCard(bvo);
    
    SCMEnv.showTime(lTime, "setBillVO:ctrlSourceBillUI");

  }

  /**
   * 创建者：张欣 功能：根据当前表体行BarCodeVO[]是否有值,条码编辑按钮是否可用 参数： row行号 返回： 例外：
   * 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 修改人：刘家清 修改日期：2007-10-17上午11:31:19 修改原因：
   * 
   * 
   */
  protected void setBtnStatusBC(int row) {
    GeneralBillVO voBill = null;
    // 设置voBill,以读取控制数据。
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // 新增、修改方式下用m_voBill
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
          // 可以条码编辑
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

    // 焦点问题，ydy 04-06-29
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_LINE_BARCODE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_EXECUTE_BARCODE_CLOSE));
    updateButton(getButtonManager().getButton(
        ICButtonConst.BTN_EXECUTE_BARCODE_OPEN));

  }

  /**
   * 创建者：李俊 功能：根据当前表体行BarCodeVO[],设置导入条码菜单状态 参数： row行号 返回： 例外：
   * 
   */
  protected void setBtnStatusImportBarcode(int row) {
    if (row < 0)
      return;
    GeneralBillVO voBill = null;
    if (BillMode.List == getM_iCurPanel()) {
      setBarcodeButtonStatus(false);
      return;// 浏览情况，非是审核条码不可以直接导入
    }
    // 设置voBill,以读取控制数据。
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // 新增、修改方式下用m_voBill
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
   * 方法功能描述：更新条码导入相关的四个按钮的状态。<br>
   * V50中，使用了一个顶级按钮“导入条码”，作为这四个按钮的一级按钮，来达到控制这四个按钮状态的目的
   * V51重构，所有的按钮都使用ButtonTree来进行加载，并充分利用按钮注册的功能，这四个按钮归并到“导出/导入”按钮下，因此需要单独控制其状态
   * <p>
   * <p>
   * <b>参数说明</b>
   * 
   * @param enabled
   *            可用状态为true，不可用状态为false
   *            <p>
   * @author duy
   * @time 2007-2-5 下午02:16:46
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
   * 创建者：王乃军 功能：根据当前单据的待审状态决定签字/取消签字那个可用 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSign(boolean bUpdateButtons) {

    // 只在浏览状态下并且界面上有单据时控制
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
      // 已签字，所以设置按钮状态,签字不可用，取消签字可用
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(true);
      // 不可删、改
      getButtonManager().getButton(ICButtonConst.BTN_BILL_EDIT)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_BILL_DELETE)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SETTLE_PATH).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_CANCEL_SETTLE_PATH).setEnabled(
          false);

    } else if (NOTSIGNED == iSignFlag) {
      // 未签字，所以设置按钮状态,签字可用，取消签字不可用
      // 判断是否已填了数量，因为数量是完整的，所以只要检查第一行就行了。

      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          true);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      // 可删、改
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
    } else { // 不可签字操作
      getButtonManager().getButton(ICButtonConst.BTN_SIGN).setEnabled(
          false);
      getButtonManager().getButton(ICButtonConst.BTN_EXECUTE_SIGN_CANCEL)
          .setEnabled(false);
      // 可删、改
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
    // 使设置生效 屏蔽 by hanwei 2003-11-17 for 效率
    if (bUpdateButtons)
      updateButtons();

  }

  /**
   * 创建者：王乃军 功能：根据当前存货的属性决定序列号分配是否可用 参数： row行号 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志： bUpdateButtons:是否更新按纽,true: 更新, false不更新
   * 
   * 
   * 
   */
  protected void setBtnStatusSN(int row, boolean bUpdateButtons) {
    GeneralBillVO voBill = null;
    // 设置voBill,以读取控制数据。
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // 新增、修改方式下用m_voBill
      voBill = getM_voBill();

    if (voBill != null) {
      InvVO voInv = null;
      voInv = voBill.getItemInv(row);
      if (voInv != null && voInv.getIsSerialMgt() != null
          && voInv.getIsSerialMgt().intValue() != 0)
        // 是序列号管理存货，可用
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
            .setEnabled(true);
      else
        // 不是序列号管理存货，不可用
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
            .setEnabled(false);
    } else
      getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
          .setEnabled(false);

    // 调用条码按钮状态控制方法by hanwei 2003-11-18
    setBtnStatusBC(row);

    // 调用导入条码按钮状态控制方法 add by ljun
    setBtnStatusImportBarcode(row);

    // 使设置生效 屏蔽 by hanwei 2003-11-17 for 效率
    if (bUpdateButtons)
      // updateButtons();
      // 焦点问题，ydy 04-06-29
      updateButton(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_SERIAL));

  }

  /**
   * 创建者：王乃军 功能：根据当前仓库的状态决定货位分配是否可用 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected void setBtnStatusSpace(boolean bUpdateButtons) {
    GeneralBillVO voBill = null;
    // 设置voBill,以读取控制数据。
    if (BillMode.Browse == getM_iMode()) {
      if (getM_iLastSelListHeadRow() >= 0
          && getM_alListData() != null
          && getM_alListData().size() > getM_iLastSelListHeadRow()
          && getM_alListData().get(getM_iLastSelListHeadRow()) != null)
        voBill = (GeneralBillVO) getM_alListData().get(
            getM_iLastSelListHeadRow());
    } else
      // 新增、修改方式下用m_voBill
      voBill = getM_voBill();

    // 缺省不是货位管理仓库，不可用
    getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE).setEnabled(
        false);
    // m_boSelectLocator.setEnabled(false);

    if (voBill != null) {
      WhVO voWh = null;
      voWh = voBill.getWh();
      // 是货位管理仓库，可用
      if (voWh != null && voWh.getIsLocatorMgt() != null
          && voWh.getIsLocatorMgt().intValue() != 0) {
        getButtonManager().getButton(ICButtonConst.BTN_LINE_SPACE)
            .setEnabled(true);
      }

      // ###########################
      // 设置 add by hanwei 2004-05-14
      // 1、库存出入库单如果有直接调转标志，需要在界面控制 修改、删除、复制等按钮不可用；
      // 2、如果单据的仓库是直运仓，应该控制修改、删除、复制等按钮不可用；
      setBtnStatusTranflag();
      // #############################
    }

    // 使设置生效 by hanwei 2003-11-17 for 效率
    if (bUpdateButtons)
      updateButton(getButtonManager().getButton(
          ICButtonConst.BTN_LINE_SPACE));

  }

  /**
   * 创建者：韩卫 功能： bd_stordoc 是否直运仓库：isdirectstore =Y 或 ic_general_h
   * 是否直接调拨：bdirecttranflag =Y 1、库存出入库单如果有直接调转标志，需要在界面控制 修改、删除、复制等按钮不可用；
   * 2、仓库有一个属性，是否直运仓库，需要加在 WHVO中。如果单据的仓库是直运仓，应该控制修改、删除、复制等按钮不可用；
   * 
   * 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   */
  protected void setBtnStatusTranflag() {
    setBtnStatusTranflag(false);
  }

  /**
   * 创建者：韩卫 功能： bd_stordoc 是否直运仓库：isdirectstore =Y 或 ic_general_h
   * 是否直接调拨：bdirecttranflag =Y 1、库存出入库单如果有直接调转标志，需要在界面控制 修改、删除、复制等按钮不可用；
   * 2、仓库有一个属性，是否直运仓库，需要加在 WHVO中。如果单据的仓库是直运仓，应该控制修改、删除、复制等按钮不可用；
   * 
   * 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * bOnlyFalse:只设置setEnabled为false的情况的属性
   * 
   */
  protected void setBtnStatusTranflag(boolean bOnlyFalse) {

    GeneralBillVO voBill = null;

    // 设置voBill,以读取控制数据。
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
    // 直运
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
        //修改人：刘家清 修改时间：2008-8-25 下午04:39:09 修改原因：直运仓库、直接调拨,可编辑
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
   * 创建者：王乃军 功能：设置按钮状态。 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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

      // 外设输入支持
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SCAN)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(false);
      // 控制翻页按钮的状态：
      m_pageBtn.setPageBtnVisible(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);
      // 联查在新增状态下不可用
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

      // 在新增情况下和修改情况条码框可以编辑
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(true);

      // 导入条码
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

      // 外设输入支持
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SCAN)
          .setEnabled(true);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(false);
      // 控制翻页按钮的状态：
      m_pageBtn.setPageBtnVisible(false);
      getButtonManager().getButton(ICButtonConst.BTN_LINE_AUTO_FILL)
          .setEnabled(true);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_PICKUP_AUTO).setEnabled(true);
      // 联查在修改状态下不可用
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

      // 在新增情况下和修改情况条码框可以编辑
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
          // 符合规则：其他出入库单，调拨出入库单，可以导入单据条码
          // 卡片状态，单据类型，
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
    case BillMode.Browse: // 在浏览情况下条码框不可以编辑
      if (m_utfBarCode != null)
        m_utfBarCode.setEnabled(false);
      // 在列表情况下，条码按纽
      if (BillMode.List == getM_iCurPanel()) {
        getButtonManager().getButton(ICButtonConst.BTN_LINE_BARCODE)
            .setEnabled(false);
        // added by lirr 2009-10-31上午10:38:32 查询后列表下，条码关闭、打开不可用
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

      // 有单据
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
        
        // 如果不是和本节点相同的单据类型(如借入单上查出的期初单)，不能删除.
        try {
          // 当前选中的单据
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
      // modified by liuzy 2008-01-03 浏览状态下不可用
      getButtonManager().getButton(ICButtonConst.BTN_ADD_NEWROWNO)
          .setEnabled(false);
      // 有单据可以打印。
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
        
      } // 还应进一步判断当前的单据是否已签字
      // 同时判断修改、删除是否可用，所以应放在它们的后面。
      getButtonManager().getButton(ICButtonConst.BTN_LINE).setEnabled(
          false);
      // 外设输入支持
      getButtonManager().getButton(ICButtonConst.BTN_ASSIST_FUNC_SCAN)
          .setEnabled(false);
      getButtonManager().getButton(ICButtonConst.BTN_IMPORT_BILL)
          .setEnabled(false);
      getButtonManager().getButton(
          ICButtonConst.BTN_ASSIST_FUNC_INV_CHECK).setEnabled(true);
/*      getButtonManager()
          .getButton(ICButtonConst.BTN_ASSIST_FUNC_DOCUMENT)
          .setEnabled(true);*/
      // 导入条码
      if (getButtonManager().getButton(
          ICButtonConst.BTN_IMPORT_SOURCE_BARCODE) != null)
        getButtonManager().getButton(
            ICButtonConst.BTN_IMPORT_SOURCE_BARCODE).setEnabled(
            true);
      // 控制翻页按钮的状态：
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
    // 当前选中行
    lTime = System.currentTimeMillis();
    int rownow = getBillCardPanel().getBillTable().getSelectedRow();
    // 判断是否需要序列号分配,设置状态
    if (rownow >= 0)
      setBtnStatusSN(rownow, false);
    else
      getButtonManager().getButton(ICButtonConst.BTN_LINE_SERIAL)
          .setEnabled(false);
    // 抽象方法调用
    SCMEnv.showTime(lTime, "setBtnStatusSN)");
    lTime = System.currentTimeMillis();
    setButtonsStatus(getM_iMode());
    setExtendBtnsStat(getM_iMode());
    SCMEnv.showTime(lTime, "setButtonsStatus(m_iMode)");
    // 重新设置货位分配是否可用
    lTime = System.currentTimeMillis();
    setBtnStatusSpace(false);
    SCMEnv.showTime(lTime, "setBtnStatusSpace();:");
    setBtnStatusSign(false);
    // 根据来源单据控制按钮
    lTime = System.currentTimeMillis();
    ctrlSourceBillButtons(false);
    SCMEnv.showTime(lTime, "根据来源单据控制按钮");/*-=notranslate=-*/

    // 根据单据类型控制按钮
    lTime = System.currentTimeMillis();
    ctrlBillTypeButtons(true);
    SCMEnv.showTime(lTime, "根据单据类型控制按钮");/*-=notranslate=-*/

    // 合并显示
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

    // 列表状态下，存量显示/隐藏的按钮不可用
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

    // 使设置生效
    lTime = System.currentTimeMillis();
    if (bUpdateButtons)
      updateButtons();
    SCMEnv.showTime(lTime, "updateButtons();");
    
    //added by lirr 2009-02-13    二次开发扩展
      getPluginProxy().setButtonStatus();
  }

  /**
   * 此处插入方法说明。 创建日期：(2004-4-8 11:13:07)
   * 
   * @param newGenBillUICtl
   *            nc.ui.ic.pub.bill.GeneralBillUICtl
   */
  public void setGenBillUICtl(GeneralBillUICtl newGenBillUICtl) {
    m_GenBillUICtl = newGenBillUICtl;
  }

  /**
   * 创建者：王乃军 功能：在表单设置显示VO,不更新界面状态updateValue() 参数： 返回： 例外： 日期：(2001-5-9
   * 9:23:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void setImportBillVO(GeneralBillVO bvo, boolean bExeFormula)
      throws Exception {
    if (bvo == null)
      throw new Exception(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000061")/* @res "传入的单据为空！" */);
    // 表体行数
    int iRowCount = bvo.getItemCount();

    try {
      getBillCardPanel().getBillModel().removeTableModelListener(this);
      getBillCardPanel().removeBillEditListenerHeadTail();
      // 保存一个clone()
      setM_voBill((GeneralBillVO) bvo.clone());
      // 其他仓库的公司非登陆公司

      // 调拨出入库单的两个参照特殊处理
      // 不应放在基类方法中。
      // shawbing on Jun 13, 2005
      if (getBillType().equals("4Y") || getBillType().equals("4E"))
        setBillRefIn4Eand4Y(getM_voBill());

      // 新增的单据清除PK
      getM_voBill().getHeaderVO().setPrimaryKey(null);
      GeneralBillItemVO[] voaItem = getM_voBill().getItemVOs();
      for (int row = 0; row < iRowCount; row++) {
        voaItem[row].setPrimaryKey(null);
        voaItem[row].calculateMny();
      }
      // 设置数据
      getBillCardPanel().setBillValueVO(getM_voBill());
      // 执行公式
      if (bExeFormula) {
        getBillCardPanel().getBillModel().execLoadFormula();
        execHeadTailFormulas();
      }

      // 更新状态 ---delete it to support CANCEL
      // getBillCardPanel().updateValue();
      // 清存货现存量数据
      bvo.clearInvQtyInfo();
      // 有表体行，选中第一行
      if (iRowCount > 0) {
        // 选中第一行
        getBillCardPanel().getBillTable().setRowSelectionInterval(0, 0);
        // 重置序列号是否可用
        setBtnStatusSN(0, false);

        // 刷新现存量显示
        // setTailValue(0);
        // 重置其它数据
        nc.ui.pub.bill.BillModel bmTemp = getBillCardPanel()
            .getBillModel();
        m_alLocatorDataBackup = m_alLocatorData;
        m_alSerialDataBackup = m_alSerialData;
        m_alLocatorData = new ArrayList();
        m_alSerialData = new ArrayList();

        for (int row = 0; row < iRowCount; row++) {
          // 设置行状态为新增
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
   * ?user> 功能： 参数： 返回： 例外： 日期：(2005-1-11 18:53:03) 修改日期，修改人，修改原因，注释标志：
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

    // 辅单位
    if (voInv.getIsAstUOMmgt() != null
        && voInv.getIsAstUOMmgt().intValue() == 1) {

      String oldvalue = (String) getBillCardPanel().getBodyValueAt(irow,
          "castunitid");// refCastunit.getRefPK();
      //陈倪娜 修正辅数量自动清空BUG 2009-07-08
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
      //陈倪娜 修正辅数量自动清空BUG 2009-07-08
      if(voLot.getCastunitid()!=null){
      getBillCardPanel().setBodyValueAt(voLot.getCastunitid(), irow,
          "cselastunitid");
      }
      // 如果不是拆解，那么执行以前的代码。
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
   // addied by liuzy 2010-12-9 上午11:27:03 如果是换算率记结存，需要把换算率带入单据
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
    // 修改人：刘家清 修改日期：2007-9-19上午11:01:19 修改原因：如果有生产日期就不重算了
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
    
    // 自由项
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
        // 修改人：刘家清 修改日期：2007-12-28下午04:49:54 修改原因：同步备份VO和界面上的invvo
        getBillCardPanel().setBodyValueAt(invvo, irow, "invvo");

      }
      getM_voBill().setItemFreeVO(irow, freevo);
    }
    // 同步改变m_voBill
    getM_voBill().setItemValue(irow, "vbatchcode", voLot.getVbatchcode());
    getM_voBill().setItemValue(irow, "dvalidate", voLot.getDvalidate());
    getM_voBill().setItemValue(irow, "cqualitylevelid", voLot.getCqualitylevelid());
    getM_voBill().setItemValue(irow, "cqualitylevelname", voLot.getCqualitylevelname());

  }

  /**
   * 创建者：王乃军 功能：设置表体/表尾的小数位数 参数： 返回： 例外： 日期：(2001-11-23 18:11:18)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void setScaleOfCardPanel(nc.ui.pub.bill.BillCardPanel bill) {

    // 精度

    nc.ui.ic.pub.scale.ScaleInit si = new nc.ui.ic.pub.scale.ScaleInit(
        getEnvironment().getUserID(), getEnvironment().getCorpID(),
        m_ScaleValue);

    try {
      si.setScale(bill, m_ScaleKey);
    } catch (Exception e) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000060")/* @res "精度设置失败：" */
          + e.getMessage());
    }

  }

  /**
   * 创建者：王乃军 功能：重载的显示提示信息对话框功能 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  public void showErrorMessage(String sMsg, boolean bWarnSound) {
    // 提示声音
    if (bWarnSound)
      java.awt.Toolkit.getDefaultToolkit().beep();

    showErrorMessage(sMsg);

  }

  /**
   * 
   * 功能： 导入文件后同步状态vo. 参数： 返回： 例外： 日期：(2002-04-18 10:43:46) 修改日期，修改人，修改原因，注释标志：
   */
  public void synVO(nc.vo.pub.CircularlyAccessibleValueObject[] voaDi,
      String sWarehouseid, ArrayList m_alLocatorData) throws Exception {
    // 同步vo.
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
      // 处理货位 add by hanwei 2003-12-17
      nc.ui.ic.pub.bill.GeneralBillUICtl.setLocatorVO(voaItem,
          sWarehouseid, m_alLocatorData);
    }
  }
  
  /**
   * 创建者：yangb 功能：add insert line 参数： 返回： 例外： 日期：(2001-6-26 下午 9:32) 修改日期，修改人，修改原因，注释标志：
   */
  protected void voBillAddLine(int row) {
    if(row>=0 && row<getBillCardPanel().getRowCount()){
      getM_voBill().setItemValue(row, "crowno", getBillCardPanel().getBodyValueAt(row, "crowno"));
      setUIVORowPosWhenNewRow(row, getM_voBill().getItemVOs()[row]);
    }
  }

  /**
   * 创建者：仲瑞庆 功能：粘贴行 参数： 返回： 例外： 日期：(2001-6-26 下午 9:32) 修改日期，修改人，修改原因，注释标志：
   */
  protected void voBillPastLine(int row) {
    // 要求在已经增完行后执行

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

      // //粘贴行的赠品可以编辑
      // if (getBillCardPanel().getBodyItem("flargess")!=null)
      // {
      // getBillCardPanel().getBodyItem("flargess").setEnabled(true);
      // getBillCardPanel().getBodyItem("flargess").setEdit(true);
      // }
    }
    // 设置是否可序列号分配
    //
    // voBillPastLineSetAttribe(row,m_voBill);
    setBtnStatusSN(row, true);
  }

  /**
   * 创建者：仲瑞庆 功能：粘贴行 参数： 返回： 例外： 日期：(2001-6-26 下午 9:32) 修改日期，修改人，修改原因，注释标志：
   */
  protected void voBillPastTailLine() {
    // 要求在已经增完行后执行
    if (m_voaBillItem != null) {
      int row = getBillCardPanel().getBillTable().getRowCount()
          - m_voaBillItem.length;
      voBillPastLine(row);
    }
    GeneralBillUICtl.calcNordcanoutnumAfterRowChange(getM_voBill(),
        getBillCardPanel(), getBillType());

  }

  /**
   * hw 功能：获得普通的业务日志VO 参数： 返回： 例外： 日期：(2004-6-8 20:42:43) 修改日期，修改人，修改原因，注释标志：
   * 
   * @return nc.vo.sm.log.OperatelogVO
   */
  public nc.vo.sm.log.OperatelogVO getNormalOperateLog() {
    nc.ui.pub.ClientEnvironment ce = getClientEnvironment();
    nc.vo.sm.log.OperatelogVO log = new nc.vo.sm.log.OperatelogVO();
    log.setCompanyname(ce.getCorporation().getUnitname());
    //修改人：陈倪娜 日期：2009-04-20 功能：取消判断，写入IP      
//    if (!nc.ui.pub.ClientEnvironment.getInstance().isInDebug()){
      log.setEnterip(nc.ui.sm.cmenu.Desktop.getApplet().getParameter(
          "USER_IP"));
      
//      }
        
    log.setPKCorp(getEnvironment().getCorpID());


    return log;
  }

  /**
   * 功能：过滤出实发数量非空非零的表体行，打印用。 参数： 返回： 例外： 日期：(2005-2-21 21:31:48)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 返回 LotNumbRefPane1 特性值。
   * 
   * @return nc.ui.ic.pub.lot.LotNumbRefPane
   */
  /* 警告：此方法将重新生成。 */
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
   * ?user> 功能： 参数： 返回： 例外： 日期：(2005-1-11 18:53:03) 修改日期，修改人，修改原因，注释标志：
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
      // 第一行，且两个辅助单位hsl相同，则替换hsl
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

    // 对应单据号
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondcode(), iSelrow,
        IItemKey.CORRESCODE);
    // 对应单据类型
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondtype(), iSelrow,
        "ccorrespondtype");
    // 对应单据表头OID
    // 单据模板库中表体位置两个不显示列ccorrespondhid,ccorrespondbid,以保存带出的对应表头，表体OID
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondhid(), iSelrow,
        "ccorrespondhid");
    // 对应单据表体OID
    getBillCardPanel().setBodyValueAt(vo.getCcorrespondbid(), iSelrow,
        "ccorrespondbid");

    // 在途标记
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
    //修改人：刘家清 修改时间：2008-7-24 下午03:32:53 修改原因：出库跟踪入库时，如果入库单货位只有一个，则出库单中输入对应入库单号后将入库单"货位"自动带入出库单的"货位"中；
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

  // 单据导出菜单
  protected Vector m_vBillExcel = null;

  /**
   * 
   * 方法功能描述：根据客商管理档案获得客商的基本档案。
   * <p>
   * <b>参数说明</b>
   * 
   * @param pk_cumangid
   *            客商管理档案的ID
   * @return 客商基本档案ID
   *         <p>
   * @author duy
   * @time 2007-3-20 上午10:50:26
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
   * 创建人：刘家清 创建日期：2008-5-16上午11:31:47 创建原因：取得业务类型的核算规则
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
   * ?user> 功能： 参数： 返回： 例外： 日期：(2004-8-27 10:54:32) 修改日期，修改人，修改原因，注释标志：
   * 
   * @param voNew
   *            nc.vo.ic.pub.bill.GeneralBillVO
   * 
   * 修改问题：由调拨订单生成调拨出库单时（推式&拉式），货位没有保存上。
   * 修改原因分析：货位保存时，使用locator中保存的数据，而非表体中显示的space；
   * 在space修改后，afterEdit会调用afterSpaceEdit方法，自动同步space与locator；
   * 但在推或拉上游单据时，space不会自动同步locator，以至保存不上。 修改时间：2005-11-24 修改者：ShawBing
   * 
   */
  protected void calcSpace(GeneralBillVO voNew) {
    if (voNew != null && voNew.getItemVOs() != null) {
      GeneralBillItemVO[] voItems = voNew.getItemVOs();
      // 纪录货未行
      boolean isLocator = false;
      // 货未档案ID
      String[] aryItemField11 = new String[] {
          "vspacename->getColValue(bd_cargdoc,csname,pk_cargdoc,cspaceid)",
          "vspacecode->getColValue(bd_cargdoc,cscode,pk_cargdoc,cspaceid)" };

      // boolean isSN = false;

      for (int i = 0; i < voItems.length; i++) {

        if (voItems[i].getCspaceid() != null) {
          isLocator = true;

          /** 1-1 下面部分为2005-11-25 Shaw 修改内容，修改说明见方法注释* */
          // 在推或拉式生成单据时，space有值，而locaor没值，导致货位无法保存
          // 同步locator
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
          /** 1-1 修改结束* */
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
   * 清除定位颜色
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
   * 程起伍 功能：执行特殊公式. 目前只有销售出库单重载此方法. 参数： 返回： 例外： 日期：(2004-7-20 17:19:12)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   */
  protected void execExtendFormula(ArrayList alListData) {
  }

  /**
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2002-1-24 11:35:23) 修改日期，修改人，修改原因，注释标志：
   * 
   * @return boolean
   */
  /**
   * 返回 BillCardPanel1 特性值。
   * 
   * @return nc.ui.pub.bill.BillCardPanel bEditable:是否可以编辑： bSaveable:是否可以直接保存
   * 
   * 条码编辑界面状态： 只读，用来显示 修改：可以修改，但需要通过单据才能保存 保存：可以直接保存，只在浏览状态才可以？
   * 
   */
  /* 警告：此方法将重新生成。 */
  public BarCodeDlg getBarCodeDlg(boolean bEditable, boolean bSaveable) {
    m_dlgBarCodeEdit = new BarCodeDlg(this, this, getEnvironment()
        .getCorpID(), bEditable, bSaveable);
    return m_dlgBarCodeEdit;
  }

  /**
   * 此处插入方法说明。 功能： 参数： 返回： 例外： 日期：(2002-7-26 11:51:17) 修改日期，修改人，修改原因，注释标志：
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
   * ?user> 功能： 参数： 返回： 例外： 日期：(2004-8-31 15:56:03) 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：李俊 功能：（单据审核后）保存导入条码
   */
  public int onImportSignedBillBarcode(GeneralBillVO voUpdatedBill,
      boolean bCheckNum) throws Exception {

    try {
      // 得到数据错误，出错 ------------ EXIT -------------------
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@修改保存单据开始：");/*-=notranslate=-*/
      if (voUpdatedBill == null || voUpdatedBill.getParentVO() == null
          || voUpdatedBill.getChildrenVO() == null) {
        SCMEnv.out("Bill is null !");
        throw new Exception(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("4008bill", "UPP4008bill-000055")/*
                                     * @res
                                     * "单据为空！"
                                     */);
      }

      // 保存时，主条码和次条码完整性检查，如果不完整时，强制不通过
      String sMsg = BarcodeparseCtrl
          .checkBarcodesubIntegrality(voUpdatedBill.getItemVOs());
      if (sMsg != null) {
        MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes
            .getInstance().getStrByID("4008bill",
                "UPPSCMCommon-000059")/* @res "错误" */, sMsg);
        return 0;
      }

      if (bCheckNum) {
        sMsg = BarcodeparseCtrl.checkNumWithBarNum(voUpdatedBill
            .getItemVOs(), true);
        if (sMsg != null) {
          MessageDialog.showErrorDlg(this, nc.ui.ml.NCLangRes
              .getInstance().getStrByID("4008bill",
                  "UPPSCMCommon-000059")/* @res "错误" */,
              sMsg);
          return 0;
        }

      }

      // 设置单据类型
      voUpdatedBill.setHeaderValue("cbilltypecode", getBillType());
      // 05/07设置制单人为当前操作员
      // remark by zhx onSave() set coperatorid into VO
      // voUpdatedBill.setHeaderValue("coperatorid",
      // getEnvironment().getUserID());
      timer.showExecuteTime("@@设置单据类型：");/*-=notranslate=-*/
      GeneralBillVO voBill = (GeneralBillVO) getM_voBill().clone();
      timer.showExecuteTime("@@m_voBill.clone()：");/*-=notranslate=-*/
      // ydy 0826
      // 把voUpdatedBill上的条码VO放到voBill中
      setBarCodeOnUI(voBill, (GeneralBillItemVO[]) voUpdatedBill
          .getChildrenVO());
      // voBill.setIDItems(voUpdatedBill);

      GeneralBillHeaderVO voHead = voBill.getHeaderVO();
      // 签字人
      //voHead.setCregister(getEnvironment().getUserID());
      // --------------------------------------------可以不是当前登录单位的单据，所以不需要修改单位。
      voHead.setPk_corp(getEnvironment().getCorpID());
      // 因为登录日期和单据日期是可以不同的，所以必须要登录日期。
      voHead.setDaccountdate(new nc.vo.pub.lang.UFDate(getEnvironment()
          .getLogDate()));
      // vo可能要传给平台，所以要做成和签字后的单据
      //voHead.setFbillflag(new Integer(nc.vo.ic.pub.bill.BillStatus.SIGNED));
      voHead.setCoperatoridnow(getEnvironment().getUserID()); // 当前操作员2002-04-10.wnj
      voHead.setAttributeValue("clogdatenow", getEnvironment()
          .getLogDate()); // 当前登录日期2003-01-05
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
                                   * ".\n权限校验不通过,保存失败! "
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
                                         * "权限校验不通过,保存失败. "
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
                                       * "权限校验不通过,保存失败. "
                                       */);

        }
      } catch (Exception e) {
        // 修改人：刘家清 修改日期：2007-10-31下午04:46:03
        // 修改原因：因为可能RightcheckException会被包一层，所以再做下处理，最多处理三层。
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
                                     * ".\n权限校验不通过,保存失败! "
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
                                   * "权限校验不通过,保存失败. "
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
                                         * "权限校验不通过,保存失败. "
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
                                     * "单据已经保存，但返回值错误，请重新查询单据。"
                                     */);
      }
      // 0 ---- 显示提示信息
      if (alRetData.get(0) != null
          && alRetData.get(0).toString().trim().length() > 0)
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000122")/* @res "条码保存成功。" */
            + (String) alRetData.get(0));
      else
        showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "4008bill", "UPP4008bill-000122")/* @res "条码保存成功。" */);

      // ###################################################
      // 重新设置单据表头ts
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
      // 更新条码状态
      GeneralBillVO.setBillBCVOStatus(getM_voBill(), nc.vo.pub.VOStatus.UNCHANGED);
      // hanwei 2004-0916
      GeneralBillVO.setBillBCVOStatus(voUpdatedBill, nc.vo.pub.VOStatus.UNCHANGED);

      // 添加此方法，避免条码VO为空后，没有清空m_voBill对应的条码VO
      getM_voBill().setIDClearBarcodeItems(voUpdatedBill);

      getM_voBill().clearAccreditBarcodeUserID();

      // 更新界面时间ts
      getGenBillUICtl()
          .setBillCardPanelData(getBillCardPanel(), smbillvo);

      timer.showExecuteTime(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000123")/*
                             * @res
                             * "@@m_voBill.setIDItems(voUpdatedBill)："
                             */);

      // 刷新列表数据
      updateBillToList(getM_voBill());
      timer.showExecuteTime(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000124")/*
                             * @res
                             * "@@刷新列表数据updateBillToList(m_voBill)："
                             */);

    } catch (java.net.ConnectException ex1) {
      SCMEnv.out(ex1.getMessage());
      if (showYesNoMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "4008bill", "UPP4008bill-000104")/*
                             * @res
                             * "当前网络中断，是否将单据信息保存到默认目录："
                             */
      ) == MessageDialog.ID_YES) {
        // 保存单据信息到默认目录
        onButtonClicked(getButtonManager().getButton(
            ICButtonConst.BTN_EXPORT_TO_DIRECTORY));
        // onBillExcel(1);
      }
    } catch (Exception e) {
      // 异常必须抛出，由主流程处理。因为它影响主流程。
      throw e;

    }
    return 1;
  }

  /**
   * ?user> 功能：将条码管理的单据行的条码设置到BillVO上 参数： 返回： 例外： 日期：(2004-8-24 14:02:09)
   * 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-10-30 15:06:35) 修改日期，修改人，修改原因，注释标志：
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
   * 鼠标双击事件 创建日期：(2001-6-20 17:19:03)
   */
  public void mouse_doubleclick(nc.ui.pub.bill.BillMouseEnent e) {
    if (e.getPos() == BillItem.HEAD) {
      onButtonClicked(getButtonManager().getButton(
          ICButtonConst.BTN_SWITCH));
      // onSwitch();
    }
    
//  二次开发扩展
    getPluginProxy().mouse_doubleclick(e);

  }

  /*
   * （非 Javadoc）
   * 
   * @see nc.ui.scm.pub.bill.IBillExtendFun#getExtendBtns()
   */
  public ButtonObject[] getExtendBtns() {
    return null;
  }

  /*
   * （非 Javadoc）
   * 
   * @see nc.ui.scm.pub.bill.IBillExtendFun#onExtendBtnsClick(nc.ui.pub.ButtonObject)
   */
  public void onExtendBtnsClick(ButtonObject bo) {

  }

  /*
   * （非 Javadoc）
   * 
   * @see nc.ui.scm.pub.bill.IBillExtendFun#setExtendBtnsStat(int)
   *      BillMode.New 单据新增 BillMode.Browse 浏览 BillMode.Update 修改
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
   * get 创建日期：(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  protected ClientUISortCtl getListHeadSortCtl() {
    return m_listHeadSortCtl;
  }

  /**
   * get 创建日期：(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  protected ClientUISortCtl getListBodySortCtl() {
    return m_listBodySortCtl;
  }

  /**
   * get 创建日期：(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  protected ClientUISortCtl getCardBodySortCtl() {
    return m_cardBodySortCtl;
  }

  /**
   * 排序后触发。 创建日期：(2001-10-26 14:31:14)
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
   * 排序前触发。 创建日期：(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  public void beforeSortEvent(boolean iscard, boolean ishead, String key) {

    clearOrientColor();
    // 如果是表头排序
    if (ishead) {
      SCMEnv.out("表头排序");/*-=notranslate=-*/
      if (getM_alListData() == null || getM_alListData().size() <= 0) {
        // 说明没有排序的必要
        return;
      }
      getListHeadSortCtl().addRelaSortData(getM_alListData());

    } else {
      SCMEnv.out("表体排序");/*-=notranslate=-*/

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
   * 列表表头排序后触发,当前行变化 创建日期：(2001-10-26 14:31:14)
   * 
   * @param key
   *            java.lang.String
   */
  public void currentRowChange(int newrow) {

    if (newrow >= 0) {
      if (getM_iLastSelListHeadRow() != newrow) {
        setLastHeadRow(newrow);
        selectListBill(getM_iLastSelListHeadRow()); // 表体
        setButtonStatus(true);
      }
    } else {
      if (getM_iLastSelListHeadRow() < 0
          || getM_iLastSelListHeadRow() >= getBillListPanel()
              .getHeadBillModel().getRowCount())
        setM_iLastSelListHeadRow(0);
      selectListBill(getM_iLastSelListHeadRow()); // 表体
      setButtonStatus(true);
    }
  }

  /**
   * 功能描述:退出系统
   */
  public boolean onClosing() {
    // 正在编辑单据时退出提示
    boolean bret = true;
    if (getM_iMode() != BillMode.Browse) {

      int iret = MessageDialog.showYesNoCancelDlg(this, null,
          nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
              "UCH001")/* @res "是否保存已修改的数据？" */);
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
   // addied by liuzy 2010-9-7 下午03:22:24 原来查询是考虑的权限，但是没有考虑到表连接
      getQryDlgHelp().setLinkQryConds(convos);     
      // modified by liuzy 2010-9-7 下午03:23:15 原来查询是考虑的权限，但是没有考虑到表连接
//      if(convos!=null)
//        swhere += " and " + convos[0].getWhereSQL(convos);
      try {
        ArrayList listvo = getQryDlgHelp().queryData(swhere);
        setDataOnList(listvo, true);
      } catch (BusinessException e) {
        SCMEnv.out(e.getMessage());
        nc.ui.pub.beans.MessageDialog.showHintDlg(this, nc.ui.ml.NCLangRes
            .getInstance().getStrByID("SCMCOMMON",
                "UPPSCMCommon-000270")/* @res "提示" */,
            nc.ui.ml.NCLangRes.getInstance().getStrByID("4008bill",
                "UPP4008bill-000062")/* @res "没有符合查询条件的单据！" */);
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
      // 处理跨公司部门业务员条件
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
      // 日志异常
      nc.vo.scm.pub.SCMEnv.out(e);
      showErrorMessage(e.getMessage());
    }
    
    // 显示联查单据前的处理
    processBeforeShowLinkBill((String)retHM.get(IICParaConst.LinkBillCorpPara));

      // 初始化界面
    showLinkBills(alListData);
      
    //设置列表模式下的 第一条选中  qinchao  2009-04-29
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
                                     * @res "提示"
                                     */, nc.ui.ml.NCLangRes.getInstance().getStrByID(
                  "4008bill", "UPP4008bill-000062")/*
                                     * @res
                                     * "没有符合查询条件的单据！"
                                     */);
          return;
       }
  }
  
  /**
   * 初始化联查界面
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
   * 显示联查单据前的处理
   * @param cbillpkcorp
   */
  protected void processBeforeShowLinkBill(String cbillpkcorp) {
      if (!getClientEnvironment().getCorporation().getPrimaryKey()
          .equals(cbillpkcorp)) {
        setButtons(new ButtonObject[] {});
        // 修改人：刘家清 修改日期：2007-9-24上午10:49:59 修改原因：联查时，就单据公司来filter界面参照
        filterRef(cbillpkcorp);

        // 修改人：刘家清 修改日期：2007-9-25上午10:17:35 修改原因：过滤仓库参照
        nc.ui.pub.bill.BillItem bi = getBillCardPanel().getHeadItem(
            IItemKey.WAREHOUSE);
        // 出入库单的仓库参照中，不过滤掉直运仓，对于跨公司的，直运仓也要显示。
        RefFilter.filtWh(bi, cbillpkcorp, null);
        
        //修改人：刘家清 修改时间：2008-12-29 下午03:31:59 修改原因：联查时根据单据公司处理业务类型档案。
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
   * UI关联操作-新增
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
      // v5 lj 支持多张单据参照生成
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
   * UI关联操作-新增--获取源数据
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
         **修改部分： else if{}块   
         **作用：    联查PO采购到货单  ***/
        
        boolean m_bQcEnabled = false; 
          //质量管理模块启用
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

    // 根据源单据分单
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
   * UI关联操作-审批
   * 
   * @author cch 2006-5-9-11:04:16
   */
  public void doApproveAction(ILinkApproveData approvedata) {
    queryForLinkOper(approvedata.getPkOrg(), approvedata.getBillType(),
        approvedata.getBillID());
  }

  /**
   * UI关联操作-维护
   * 
   * @author leijun 2006-5-24
   */
  public void doMaintainAction(ILinkMaintainData maintaindata) {
    queryForLinkOper(getClientEnvironment().getCorporation()
        .getPrimaryKey(), getBillTypeCode(), maintaindata.getBillID());
//    if(getM_voBill()!=null && getM_voBill().getHeaderVO().getFbillflag()!=null && getM_voBill().getHeaderVO().getFbillflag().intValue()==BillStatus.IFREE)
      // modified by liuzy 2009-1-19 下午06:10:46 为什么要已编辑状态打开呢？单据是自由状态的，用户自己点
      //修改按钮不就可以了？！这样导致SES查询出来的单据就是编辑状态的。还有不解的是SES打开节点时为什么要以ILinkType.LINK_TYPE_MAINTAIN方式打开？
      //暂时注掉，如果有问题那么只能让SES修改打开单据的方式了
//      onButtonClicked(getButtonManager().getButton(
//          ICButtonConst.BTN_BILL_EDIT));
  }

  /**
   * showBtnSwitch 符合界面规范
   * 
   * @author leijun 2006-5-24
   */
  public void showBtnSwitch() {
    if (getM_iCurPanel() == BillMode.Card)
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setName(
          nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
              "UCH022")/* @res "列表显示" */);
    else
      getButtonManager().getButton(ICButtonConst.BTN_SWITCH).setName(
          nc.ui.ml.NCLangRes.getInstance().getStrByID("common",
              "UCH021")/* @res "卡片显示" */);
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
   * 创建者：王乃军 功能：是否借转类型
   * 
   * 
   * 参数： 返回： 例外： 日期：(2001-11-24 12:15:42) 修改日期，修改人，修改原因，注释标志：
   */
  protected boolean isBrwLendBiztype() {
    return false;

  }

  /**
   * 创建者：刘家清 功能：根据单据类型控制按钮
   * 
   * 
   * 参数： 返回： 例外： 日期：(2007-04-05 17:00:00) 修改日期，修改人，修改原因，注释标志：
   */

  protected void ctrlBillTypeButtons(boolean willDo) {

    if (willDo) {

    }

  }

  /**
   * 创建者：刘家清 功能：根据单据类型控制按钮
   * 
   * 
   * 参数： 返回： 例外： 日期：(2007-04-06 10:26:00) 修改日期，修改人，修改原因，注释标志：
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
   * 创建人：刘家清 创建日期：2007-7-11下午01:41:03 创建原因：返回验证失败时验证结果的显示器框
   * 
   * ICGenVO[] gVOs = new ICGenVO[2]; gVOs[0] = new ICGenVO();
   * gVOs[0].setAttributeValue("ssss", "刘家清"); gVOs[1] = new ICGenVO();
   * gVOs[1].setAttributeValue("dddd", "刘家清"); barcodeValidateDialog = null;
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
   * 创建人：刘家清 创建日期：2007-7-11下午01:41:03 创建原因：返回验证失败时验证结果的显示器框
   * 
   */

  public PackCheckBusDialog getpackCheckBusDialog() {
    if (packCheckBusDialog == null) {
      packCheckBusDialog = new PackCheckBusDialog(this);
    }
    return packCheckBusDialog;

  }

  /*
   * m_voBill的设置器
   */
  protected void setM_voBill(GeneralBillVO m_voBill) {
    this.m_voBill = m_voBill;
    // 需要初始化参照过滤, 为点击取消后的新增操作. 20021225
    //filterRef(getEnvironment().getCorpID());
  }

  /*
   * m_voBill的获取器，请以后一定使用访问器访问成员m_voBill
   */
  public GeneralBillVO getM_voBill() {
//    if(null == m_voBill)
//      return m_voBill;
//    System.out
//    .println("***********************LIUZY-BEGIN**************************");
//    System.out
//    .println("缓存VO表体行数=" + m_voBill.getItemCount());
//    printCallInfo("getM_voBill");
    return m_voBill;
  }

  /*
   * m_iLastSelListHeadRow的设置器
   */
  protected void setM_iLastSelListHeadRow(int m_iLastSelListHeadRow) {
    this.m_iLastSelListHeadRow = m_iLastSelListHeadRow;
  }

  /*
   * m_iLastSelListHeadRow的获取器，请以后一定使用访问器访问成员m_iLastSelListHeadRow
   */
  public int getM_iLastSelListHeadRow() {
    return m_iLastSelListHeadRow;
  }

  /*
   * m_iCurPanel的设置器
   */
  protected void setM_iCurPanel(int m_iCurPanel) {
    this.m_iCurPanel = m_iCurPanel;
  }

  /*
   * m_iCurPanel的获取器，请以后一定使用访问器访问成员m_iCurPanel
   */
  public int getM_iCurPanel() {
    return m_iCurPanel;
  }

  /*
   * m_iMode的设置器
   */
  public void setM_iMode(int m_iMode) {
    this.m_iMode = m_iMode;
  }

  /*
   * m_iMode的获取器，请以后一定使用访问器访问成员m_iMode
   */
  public int getM_iMode() {
    return m_iMode;
  }

  /*
   * m_alListData的设置器
   */
  protected void setM_alListData(ArrayList<GeneralBillVO> m_alListData) {
    this.m_alListData = m_alListData;
  }

  /*
   * m_alListData的获取器，请以后一定使用访问器访问成员m_alListData
   */
  public ArrayList<GeneralBillVO> getM_alListData() {
    return m_alListData;
  }

  /*
   * 从m_alListData获取单据VO,不用关心m_alListData本身是否为空，或是否数组越界
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
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-11-8 19:47:29) 修改日期，修改人，修改原因，注释标志：
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
      // 进行自定义项定义用
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

      // 表体
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
   * 创建者：仲瑞庆 功能： 参数： 返回： 例外： 日期：(2001-11-8 19:47:29) 修改日期，修改人，修改原因，注释标志：
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
          // added by lirr 2009-11-17下午02:12:13 资产入资产出需要执行自定义项公式
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
   * ?user> 功能： 参数： 返回： 例外： 日期：(2004-6-30 17:57:26) 修改日期，修改人，修改原因，注释标志：
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
   * ?user> 功能： 参数： 返回： 例外： 日期：(2004-6-30 17:57:26) 修改日期，修改人，修改原因，注释标志：
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
   * 创建者：王乃军 功能：复制当前单据 参数： 返回： 例外： 日期：(2001-5-9 9:23:32) 修改日期，修改人，修改原因，注释标志：
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
    //showTime(lTime, "签字");
    sBillStatus = GeneralBillUICtl.updateDataAfterAudit(
        this, billvo);

    if (sBillStatus != null && !sBillStatus.equals(BillStatus.FREE)
        && !sBillStatus.equals(BillStatus.DELETED)) {
      freshAfterSignedOK(sBillStatus);
    } 
    return sBillStatus;
    
  }
 /**
  * 创建人：刘家清 创建时间：2008-7-16 下午03:43:33 创建原因： 错误信息提示，如果涉及到某些行的数据，行背景色变为黄色
  * @param e
  */ 
/*  public void setRowColorWhenException(ICException e){
    // 更改颜色
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
   * 创建人：刘家清 创建时间：2008-7-16 下午03:43:33 创建原因： 错误信息提示，如果涉及到某些行的数据，行背景色变为黄色
   * @param e
   */  
/*  public void setRowColorWhenException(ICBusinessException e){
    // 更改颜色
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
   * 创建人：刘家清 创建时间：2008-7-16 下午03:43:33 创建原因： 恢复所有的数据行背景色为默认色
   * @param e
   */ 
/*  public void reSetRowColorWhenNOException(){
    // 更改颜色
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
 * 创建人：刘家清 创建时间：2008-10-30 下午08:16:52 创建原因：如果单据上有实发或者实收数量，则不允许编辑仓库。 
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
//        System.out.println("调用此方法的类名、及方法名：" + frame.getClassName() + ":"
//            + frame.getMethodName());
//      }
//      ix++;
//    }
//
//    System.out
//        .println("***********************LIUZY-END**************************");
//  }

}
