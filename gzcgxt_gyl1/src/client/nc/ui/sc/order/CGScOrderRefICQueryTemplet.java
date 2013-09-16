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
 * ��������:����ί�ⶩ�����ɿ��ί�мӹ���ⵥ,��ѯģ�� 
 * ����:lxd 
 * ��������:2006-11-22 
 * �汾��:5.0
 */
public class CGScOrderRefICQueryTemplet extends
		MultiCorpQueryClient implements IinitQueryData {

	private boolean iswaitaudit = false;

	public String pk_corp;

	public String funcode;

	public String operator;

	public String cbiztype;

	/**
	 * ScOrderRefICQueryTemplet ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Container
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Container parent) {
		super(parent);
	}

	/**
	 * ScOrderRefICQueryTemplet ������ע�⡣
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
	 * ScOrderRefICQueryTemplet ������ע�⡣
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
	 * ScOrderRefICQueryTemplet ������ע�⡣
	 * 
	 * @param parent
	 *            java.awt.Frame
	 */
	public CGScOrderRefICQueryTemplet(java.awt.Frame parent) {
		super(parent);
	}

	/**
	 * ScOrderRefICQueryTemplet ������ע�⡣
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
	 * �˴����뷽��˵���� ��������: �������: ����ֵ: �쳣����:
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
	 * �˴����뷽��˵���� ��������: �������: ����ֵ: �쳣����:
	 */
	public void initQueryTemplet() {

		//Ĭ����������
		setValueRef("dorderdate","����"/*-=notranslate=-*/);/*-=notranslate=-*/
		setDefaultValue("dorderdate", "dorderdate", nc.ui.pub.ClientEnvironment.getInstance().getDate().toString());

	}
  /**
   * ��ѯģ����û�й�˾ʱ��Ҫ�������⹫˾��
   * <p>
   * <b>examples:</b>
   * <p>
   * ʹ��ʾ��
   * <p>
   * <b>����˵��</b>
   * <p>
   * @author lxd
   * @time 2007-3-13 ����11:10:56
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

	  //���Ӵ�������ѯ�����Ĺ���
	  ArrayList alret = PuUtils.filterConditionByIsWaitAudit(getConditionVO());
	  ConditionVO[] cons = (ConditionVO[])alret.get(1);
	  iswaitaudit = (Boolean)alret.get(0);
	  String sConditionWhere = getWhereSQL(cons);
	  // ���⴦�������� ...

	  // ƴ���Զ�������
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
