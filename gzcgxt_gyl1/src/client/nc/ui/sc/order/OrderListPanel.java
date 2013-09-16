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
 * 委外订单、订单审批都会使用此列表面板 创建日期：(01-6-7 8:50:59)
 * <p>
 * <b>变更历史：</b>
 * <p>
 * <hr>
 * <p>修改日期 2008.8.21
 * <p>修改人 zhaoyha
 * <p>版本 5.5
 * <p>说明：
 * <ul>
 * <li>选中模式进行了调整
 * </ul>
 * 
 * @author：xjl
 */
public class OrderListPanel extends nc.ui.pub.bill.BillListPanel implements
		BillEditListener, ListSelectionListener {
	// gc定义表体tab编码
	private String TAB1 = "table";
	private String TAB2 = "lby";
	private static final long serialVersionUID = 1L;

	private int ibillstatus;

	private OrderUI m_orderUI;

	private int iState = ScConstants.STATE_LIST;

	private ArrayList m_listComeVO = null;

	private nc.bs.bd.b21.CurrencyRateUtil m_CurrArith = null;

  // 前台的数据模型，委外订单、订单审批都会使用
  private OrderModel orderModel;
  
	/**
	 * ScAdjustListPanel 构造子注解。
	 */
	public OrderListPanel() {
		super();
		init();
	}

	/**
	 * ScAdjustListPanel 构造子注解。
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
			// 加载表体
			if (getHeadBillModel().getValueAt(e.getRow(), "corderid") != null) {
				setBodyModelDataCopy(e.getOldRow());
				loadBodyData(e.getRow());
				
				if (e.getRow() == 0 && getBodyBillModel().getRowCount() == 0)
					loadBodyData(0);
			}

			if (m_orderUI != null) {
				// 单据卡片上按钮状态（修改、作废）
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
	 * 计算换算率 创建日期：(2001-9-17 17:30:05)
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
			
			//装载自由项
			InvTool.loadBatchInvConvRateInfo(
					listBaseId.toArray(new String[listBaseId.size()]), 
					listAssitUnitId.toArray(new String[listAssitUnitId.size()]));
			for (int i = 0; i < rowcount; i++) {
				// 返回长度为2的数组：retValues[0]:换算率, retValues[1]:是否固定换算率
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
	 * 单据转入VO 创建日期：(2001-9-5 15:28:14)
	 * 
	 * @return java.util.Vector
	 */
	public ArrayList getComeVO() {
		return m_listComeVO;
	}

	/**
	 * 此处插入方法说明。 功能描述: 输入参数: 返回值: 异常处理:
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
	 * 此处插入方法说明。 创建日期：(2001-8-30 12:19:49)
	 * 
	 * @return int
	 */
	public int getIbillStatus() {
		return ibillstatus;
	}

	/**
	 * 此处插入方法说明。 功能描述: 输入参数: 返回值: 异常处理:
	 * 
	 * @return java.lang.String
	 */
	public String getPk_corp() {
		return nc.ui.pub.ClientEnvironment.getInstance().getCorporation()
				.getPrimaryKey();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-6-1 15:06:15)
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
	 * 此处插入方法说明。 创建日期：(2001-5-12 11:21:34)
	 * <p>
	 * <b>变更历史：</b>
	 * <p>
	 * <hr>
	 * <p>修改日期 2008.8.21
	 * <p>修改人 zhaoyha
	 * <p>版本 5.5
	 * <p>说明：
	 * <ul>
	 * <li>使用UAP55选中模式进行了调整
	 * </ul>
	 */
	protected void init() {

		super.init();
		this.addEditListener(this);
		getHeadTable().getSelectionModel().addListSelectionListener(this);
		//V55中增加选定整行
		SCMPuTool.setLineSelectedList(this);

	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-4-23 9:17:37)
	 */
	public void loadBodyData(int row) {
		String tab = getSelectTab();
		//gc 设置第一页为选中页，要不然改的类太多
				BillTabbedPane tp = getBodyTabbedPane();
				tp.setSelectedIndex(0);//.setTabVisible(0, true);
//		if (tab.equals(TAB1)) {
			try {
				OrderItemVO[] orderItemVO = null;

				// 单据转入状态
				if (iState == ScConstants.STATE_OTHER) {
					orderItemVO = (OrderItemVO[]) ((OrderVO) m_listComeVO
							.get(row)).getChildrenVO();
				}
				// 普通列表状态
				else {
					orderItemVO = (OrderItemVO[]) this.getModel()
							.getOrderAt(row).getChildrenVO();
				}

				// 先设置为最大８位，后面会通过cellrender来控制具体精度
				getBodyItem("noriginalcurmny").setDecimalDigits(8);
				getBodyItem("noriginaltaxmny").setDecimalDigits(8);
				getBodyItem("noriginalsummny").setDecimalDigits(8);
				getBodyItem("nexchangeotobrate").setDecimalDigits(8);

//				setBodyValueVO(orderItemVO);
				setBodyValueVO(TAB1, orderItemVO);

				// 扣税类别
				String sRaxType = null;
				for (int i = 0; i < orderItemVO.length; i++) {
					Integer idiscounttaxtype = orderItemVO[i]
							.getIdiscounttaxtype();

					if (idiscounttaxtype.intValue() == 0)
						sRaxType = ScConstants.TaxType_Including;// 0 应税内含
					else if (idiscounttaxtype.intValue() == 1)
						sRaxType = ScConstants.TaxType_Not_Including;// 1 应税外加
					else if (idiscounttaxtype.intValue() == 2)
						sRaxType = ScConstants.TaxType_No;// 2 不计税
					else
						sRaxType = "";

					getBodyBillModel().setValueAt(sRaxType, i,
							"idiscounttaxtype");
				}

				// 精度/必须通过两次设置精度来保证精度，缺一不可，切记。
				nc.ui.sc.pub.tool.Precision p = new nc.ui.sc.pub.tool.Precision(
						this.getBodyBillModel(), this.getBodyTable());
				p.setBusinessPrecision("ccurrencytypeid", new String[] {
						"nmoney", "ntaxmny", "nsummny" },
						new String[] { "noriginalcurmny", "noriginaltaxmny",
								"noriginalsummny" });

				// 设置汇率精度
				p.setExchangeRatePercision(nc.ui.pub.ClientEnvironment
						.getInstance().getCorporation().getPrimaryKey(),
						"ccurrencytypeid", "nexchangeotobrate");

				// 加载自由项
				loadFreeItems(orderItemVO);

				// 处理存货固定换算率问题
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
				SCMEnv.out("列表表体数据加载失败");
				SCMEnv.out(e);
			}
//		} else {
			try {
				tp.setSelectedIndex(1);
				OrderDdlbVO[] ddlbvos = null;
				// 单据转入状态
				if (iState == ScConstants.STATE_OTHER) {
					ddlbvos = (OrderDdlbVO[]) ((OrderVO) m_listComeVO.get(row))
							.getDdlbvos();
				}
				// 普通列表状态
				else {
					ddlbvos = (OrderDdlbVO[]) this.getModel().getOrderAt(row)
							.getDdlbvos();
				}

				// 先设置为最大８位，后面会通过cellrender来控制具体精度
				// getBodyItem("noriginalcurmny").setDecimalDigits(8);
				// getBodyItem("noriginaltaxmny").setDecimalDigits(8);
				// getBodyItem("noriginalsummny").setDecimalDigits(8);
				// getBodyItem("nexchangeotobrate").setDecimalDigits(8);

				setBodyValueVO(TAB2, ddlbvos);

				// // 扣税类别
				// String sRaxType = null;
				// for (int i = 0; i < orderItemVO.length; i++) {
				// Integer idiscounttaxtype =
				// orderItemVO[i].getIdiscounttaxtype();
				//
				// if (idiscounttaxtype.intValue() == 0)
				// sRaxType = ScConstants.TaxType_Including;//0 应税内含
				// else if (idiscounttaxtype.intValue() == 1)
				// sRaxType = ScConstants.TaxType_Not_Including;//1 应税外加
				// else if (idiscounttaxtype.intValue() == 2)
				// sRaxType = ScConstants.TaxType_No;//2 不计税
				// else
				// sRaxType = "";
				//
				// getBodyBillModel().setValueAt(sRaxType, i,
				// "idiscounttaxtype");
				// }

				// // 精度/必须通过两次设置精度来保证精度，缺一不可，切记。
				// nc.ui.sc.pub.tool.Precision p = new
				// nc.ui.sc.pub.tool.Precision(this
				// .getBodyBillModel(), this.getBodyTable());
				// p.setBusinessPrecision("ccurrencytypeid", new String[] {
				// "nmoney", "ntaxmny", "nsummny"}, new String[] {
				// "noriginalcurmny", "noriginaltaxmny", "noriginalsummny"});
				//
				// // 设置汇率精度
				// p.setExchangeRatePercision(nc.ui.pub.ClientEnvironment.getInstance()
				// .getCorporation().getPrimaryKey(), "ccurrencytypeid",
				// "nexchangeotobrate");

				// 加载自由项
				loadFreeItems(ddlbvos);

				// 处理存货固定换算率问题
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
				SCMEnv.out("列表表体数据加载失败");
				SCMEnv.out(e);
			}
//		}

	}

	private String getSelectTab() {
		// TODO Auto-generated method stub
		return getBodyTabbedPane().getSelectedTableCode();
	}

	/**
	 * 加载自由项。 创建日期：(2001-9-28 11:09:44)
	 */
	private void loadFreeItems(OrderItemVO[] orderItemVOs) {
		if (orderItemVOs == null || orderItemVOs.length == 0)
			return;
		// 加载自由项
		try {
			int num = orderItemVOs.length;
			java.util.ArrayList<String> listMangId = new java.util.ArrayList<String>();
			for (int i = 0; i < num; i++) {
				listMangId.add(orderItemVOs[i].getCmangid());
			}

			if (listMangId == null || listMangId.size() <= 0)
				return;

			//来源于合同时，存货管理id可能为空
			if(PuPubVO.getString_TrimZeroLenAsNull(listMangId.get(0))==null)
				return;
			
			//装载自由项
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
							"UC000-0003327")/* @res "自由项" */, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000022")/* @res "加载自由项失败" */);
			SCMEnv.out(e);
		}
	}
	/**
	 * 加载自由项。 创建日期：(2001-9-28 11:09:44)
	 */
	private void loadFreeItems(OrderDdlbVO[] orderItemVOs) {
		if (orderItemVOs == null || orderItemVOs.length == 0)
			return;
		// 加载自由项
		try {
			int num = orderItemVOs.length;
			java.util.ArrayList<String> listMangId = new java.util.ArrayList<String>();
			for (int i = 0; i < num; i++) {
				listMangId.add(orderItemVOs[i].getCmangid());
			}

			if (listMangId == null || listMangId.size() <= 0)
				return;

			//来源于合同时，存货管理id可能为空
			if(PuPubVO.getString_TrimZeroLenAsNull(listMangId.get(0))==null)
				return;
			
			//装载自由项
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
							"UC000-0003327")/* @res "自由项" */, nc.ui.ml.NCLangRes
							.getInstance().getStrByID("401201",
									"UPP401201-000022")/* @res "加载自由项失败" */);
			SCMEnv.out(e);
		}
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-4-23 9:17:37)
	 */
	public void loadHeadData() {
		// 单据转入状态
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
			// 普通列表状态
			try {
				OrderHeaderVO[] orderHeaderVO = OrderHelper.queryAllHead("");

				setHeaderValueVO(orderHeaderVO);
				getHeadBillModel().execLoadFormula();

			} catch (Exception e) {
				SCMEnv.out("列表表头数据加载失败");
				SCMEnv.out(e);
			}
		}
		
		SCMEnv.out("列表表头数据加载成功");
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-9-5 15:27:41)
	 * 
	 * @param vec
	 *            java.util.Vector
	 */
	public void setComeVO(ArrayList vec) {
		m_listComeVO = vec;
	}

	/**
	 * 此处插入方法说明。 功能描述:设置精度（数量、单价、金额） 输入参数: 返回值: 异常处理:
	 */
	public void setNormalPrecision() {
		try {
			int numPrecision = 2;
			int assnumPrecision = 2;
			int ratePrecision = 2;
			int pricePrecision = 2;

			// 数量
			try {
				numPrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD501").intValue();
			} catch (Exception e) {
				numPrecision = 2;
			}
			// 辅数量
			try {
				assnumPrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD502").intValue();
			} catch (Exception e) {
				assnumPrecision = 2;
			}
			// 换算率
			try {
				ratePrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD503").intValue();
			} catch (Exception e) {
				ratePrecision = 2;
			}
			// 单价
			try {
				pricePrecision = SysInitBO_Client.getParaInt(getPk_corp(),
						"BD505").intValue();
			} catch (Exception e) {
				pricePrecision = 2;
			}

			//数量：
			getBodyItem("nordernum").setDecimalDigits(numPrecision);
			getBodyItem("nbackarrvnum").setDecimalDigits(numPrecision);//退货数量
			getBodyItem("nbackstorenum").setDecimalDigits(numPrecision);//退库数量
			//辅数量
			getBodyItem("nassistnum").setDecimalDigits(assnumPrecision);
			//换算率
			getBodyItem("measrate").setDecimalDigits(ratePrecision);
			//单价:
			getBodyItem("noriginalcurprice").setDecimalDigits(pricePrecision);
			getBodyItem("noriginalnetprice").setDecimalDigits(pricePrecision);
			getBodyItem("norgtaxprice").setDecimalDigits(pricePrecision);
			getBodyItem("norgnettaxprice").setDecimalDigits(pricePrecision);

		} catch (Exception e) {
			SCMEnv.out(e);
		}
	}

	/**
	 * 此处插入方法说明。
	 * 创建日期：(2001-9-5 10:21:12)
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

		//得到被选中的行
		int[] selectRows = getHeadTable().getSelectedRows();
		if (selectRows != null) {
			iCount = selectRows.length;

			if ( (iCount >= 1) && 
				 (m_orderUI != null) && 
				 (iState != ScConstants.STATE_OTHER) ) {
				// 选中的第一行为当前行
				this.getModel().setCurrentIndex( selectRows[0] );
			}

			// 选中的行表示为打＊号
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