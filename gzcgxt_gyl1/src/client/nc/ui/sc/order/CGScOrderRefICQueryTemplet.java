package nc.ui.sc.order;

import java.util.ArrayList;

import nc.itf.sc.IDataPowerForSC;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.pf.IinitQueryData;
import nc.ui.scm.pub.report.MultiCorpQueryClient;
import nc.vo.pu.util.PuUtils;
import nc.vo.pub.query.ConditionVO;

/**
 * 功能描述:根据委外订单生成库存委托加工入库单,查询模板 
 * 作者:lxd 
 * 创建日期:2006-11-22 
 * 版本号:5.0
 */
public class CGScOrderRefICQueryTemplet extends
		MultiCorpQueryClient implements IinitQueryData {

	private boolean iswaitaudit = false;

	public String pk_corp;

	public String funcode;

	public String operator;

	public String cbiztype;

	/**
	 * ScOrderRefICQueryTemplet 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Container parent) {
		super(parent);
	}

	/**
	 * ScOrderRefICQueryTemplet 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Container
	 * @param title
	 *            java.lang.String
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Container parent, String title) {
		super(parent, title);
	}

	/**
	 * ScOrderRefICQueryTemplet 构造子注解。
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Container parent, String title,
			String pkCorp, String operator, String funNode,
			String businessType, String currentBillType, String sourceBilltype,
			Object userObj) {
		super(parent, title);
		this.pk_corp = pkCorp;
		this.operator = operator;
		this.funcode = funNode;
		this.cbiztype = businessType;
		init();
	}

	/**
	 * ScOrderRefICQueryTemplet 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Frame
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Frame parent) {
		super(parent);
	}

	/**
	 * ScOrderRefICQueryTemplet 构造子注解。
	 * 
	 * @param parent
	 *            java.awt.Frame
	 * @param title
	 *            java.lang.String
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Frame parent, String title) {
		super(parent, title);
	}

	/**
	 * 此处插入方法说明。 功能描述: 输入参数: 返回值: 异常处理:
	 */
	public void init() {
		setTempletID(pk_corp, funcode, operator, cbiztype);
		initQueryTemplet();
		hideNormal();
		hideUnitButton();
	}

	public void initData(String pkCorp, String operator, String funNode,
			String businessType, String currentBillType, String sourceBilltype,
			Object userObj) throws Exception {
		this.pk_corp = pkCorp;
		this.operator = operator;
		this.funcode = funNode;
		this.cbiztype = businessType;
		init();
    initCorpRefs();
	}

	/**
	 * 此处插入方法说明。 功能描述: 输入参数: 返回值: 异常处理:
	 */
	public void initQueryTemplet() {

		//默认日期条件
		setValueRef("dorderdate","日历"/*-=notranslate=-*/);/*-=notranslate=-*/
		setDefaultValue("dorderdate", "dorderdate", nc.ui.pub.ClientEnvironment.getInstance().getDate().toString());

	}
  /**
   * 查询模板中没有公司时，要设置虚拟公司。
   * <p>
   * <b>examples:</b>
   * <p>
   * 使用示例
   * <p>
   * <b>参数说明</b>
   * <p>
   * @author lxd
   * @time 2007-3-13 上午11:10:56
   */
  public void initCorpRefs(){
    //
    ArrayList<String> alcorp=new ArrayList<String>();
    alcorp.add(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
    initCorpRef(IDataPowerForSC.CORPKEYFORSC,ClientEnvironment.getInstance().getCorporation().getPrimaryKey(),alcorp);   
    //
    setCorpRefs(IDataPowerForSC.CORPKEYFORSC, IDataPowerForSC.REFKEYSFORSC);
    //    
  }

  public String getWhereSQL() {
	  String sWhereSQL = "";

	  //增加待审批查询条件的过滤
	  ArrayList alret = PuUtils.filterConditionByIsWaitAudit(getConditionVO());
	  ConditionVO[] cons = (ConditionVO[])alret.get(1);
	  iswaitaudit = (Boolean)alret.get(0);
	  String sConditionWhere = getWhereSQL(cons);
	  // 特殊处理存货分类 ...

	  // 拼接自定义条件
	  if (sConditionWhere != null && sConditionWhere.trim().length() != 0)
		  sWhereSQL = sConditionWhere;
    
//    if (sWhereSQL != null && sWhereSQL.length() > 0)
//      sWhereSQL += " and sc_order.pk_corp = '" + pk_corp + "'"
//                + " and sc_order.cbiztype = '" + cbiztype + "'"
//                + " and isnull(sc_order_b.nordernum, 0) - isnull(sc_order_b.naccumstorenum, 0) + isnull(sc_order_b.nbackstorenum, 0) > 0";
//    else
//      sWhereSQL = " and sc_order.pk_corp = '" + pk_corp + "'"
//                + " and sc_order.cbiztype = '" + cbiztype + "'"
//                + " and and isnull(sc_order_b.nordernum, 0) - isnull(sc_order_b.naccumstorenum, 0) + isnull(sc_order_b.nbackstorenum, 0) > 0";

	  return sWhereSQL;
  }

	public boolean getIswaitaudit() {
		return iswaitaudit;
	}

	public void setIswaitaudit(boolean iswaitaudit) {
		this.iswaitaudit = iswaitaudit;
	}
	
}
