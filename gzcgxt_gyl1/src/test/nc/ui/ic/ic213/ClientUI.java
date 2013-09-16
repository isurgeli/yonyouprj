package nc.ui.ic.ic213;

import nc.ui.ic.ic212.WkRefModel;
import nc.ui.ic.pub.bill.EditCtrl;
import nc.ui.ic.pub.bill.ICButtonConst;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.FramePanel;
import nc.ui.pub.bill.BillData;
import nc.ui.pub.pf.PfUtilClient;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.scm.constant.ic.BillMode;
import nc.vo.scm.constant.ic.InOutFlag;


/**
 * 委托加工发料单
 *
 *
 * 创建日期：(2001-11-23 15:39:43)
 * @author：张欣
 */
public class ClientUI extends nc.ui.ic.pub.bill.GeneralBillClientUI {
	
	
/**
 * ClientUI2 构造子注解。
 */
public ClientUI() {
	super();
	initialize();
}

/**
 * ClientUI 构造子注解。
 * add by liuzy 2007-12-18 根据节点编码初始化单据模版
 */
public ClientUI(FramePanel fp) {
 super(fp);
 initialize();
}

/**
 * ClientUI 构造子注解。
 * nc 2.2 提供的单据联查功能构造子。
 *
 */
public ClientUI(
	String pk_corp,
	String billType,
	String businessType,
	String operator,
	String billID) {
	super(pk_corp, billType, businessType, operator, billID);

}
/**
 * 创建者：王乃军
 * 功能：单据编辑后处理
 * 参数：	
 * 返回：
 * 例外：
 * 日期：(2001-5-9 9:23:32)
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * 
 * 
 * 
 */
protected void afterBillEdit(nc.ui.pub.bill.BillEditEvent e) {
//	nc.vo.scm.pub.SCMEnv.out("haha,bill edit/.");
}



protected nc.ui.pub.bill.BillCardPanel getBillCardPanel() {
	if (ivjBillCardPanel == null) {
		try {
			ivjBillCardPanel=super.getBillCardPanel();
			BillData bd=ivjBillCardPanel.getBillData();
				//表体工作中心参照
			if (bd.getBodyItem("cworkcentername") != null) {
				nc.ui.pub.beans.UIRefPane ref1 = new nc.ui.pub.beans.UIRefPane();
				ref1.setIsCustomDefined(true);
//				修改人：刘家清 修改时间：2008-6-3 上午11:07:21 修改原因：根据公司过滤工作中心。
				//WkRefModel model1 = new WkRefModel();
				WkRefModel model1 = new WkRefModel(getEnvironment().getCorpID());

				ref1.setRefModel(model1);


				bd.getBodyItem("cworkcentername").setComponent(ref1); //

			}
			
			ivjBillCardPanel.setBillData(bd);
		} catch (java.lang.Throwable ivjExc) {
			// user code begin {2}
			// user code end
			handleException(ivjExc);
		}
	}
	return ivjBillCardPanel;
}
/**
 * 创建者：王乃军
 * 功能：表体行列选择改变
 * 参数：	
 * 返回：
 * 例外：
 * 日期：(2001-5-9 9:23:32)
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * 
 * 
 * 
 */
protected void afterBillItemSelChg(int iRow, int iCol) {
	//nc.vo.scm.pub.SCMEnv.out("haha,sel chged!");

}
/**
 * 创建者：王乃军
 * 功能：单据编辑事件处理
 * 参数：e单据编辑事件
 * 返回：
 * 例外：
 * 日期：(2001-5-8 19:08:05)
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * 
 * 
 * 
 */
public boolean beforeBillItemEdit(nc.ui.pub.bill.BillEditEvent e) {
	return true;
}
/**
 * 创建者：王乃军
 * 功能：表体行列选择改变
 * 参数：	
 * 返回：
 * 例外：
 * 日期：(2001-5-9 9:23:32)
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * 
 * 
 * 
 */
protected void beforeBillItemSelChg(int iRow, int iCol) {
	//nc.vo.scm.pub.SCMEnv.out("haha,before sel");

}
/**
  * 创建者：王乃军
  * 功能：抽象方法：保存前的VO检查
  * 参数：待保存单据 
  * 返回：
  * 例外：
  * 日期：(2001-5-24 下午 5:17)
  * 修改日期，修改人，修改原因，注释标志：
  */
protected boolean checkVO(nc.vo.ic.pub.bill.GeneralBillVO voBill) {
	return super.checkVO();
}
/**
 * 创建者：余大英
 * 功能：得到当前单据vo,包含货位/序列号和界面上所有的数据,不包括删除的行
 * 参数：	
 * 返回：
 * 例外：
 * 日期：(2001-5-9 9:23:32)
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * 
 * 
 * 
 */

public GeneralBillVO getBillVO() {
	GeneralBillVO billVO = super.getBillVO();
	if (getBillCardPanel().getHeadItem("vdiliveraddress") != null)
		billVO.setHeaderValue(
			"vdiliveraddress",
			((nc.ui.pub.beans.UIRefPane) getBillCardPanel()
				.getHeadItem("vdiliveraddress")
				.getComponent())
				.getText());
	return billVO;

}

@Override
public void onButtonClicked(ButtonObject bo) {
	// TODO Auto-generated method stub
	if(bo.getName().equals("参照委外订单")){//gc
		bo.setTag("61:4F");
//		bo.setTag(getBizTypeRef()+":4F");
		PfUtilClient.childButtonClicked(bo,
				ClientEnvironment.getInstance().getCorporation()
						.getPrimaryKey(), getFunctionNode(),
						getEnvironment().getUserID(),
				"4F", this);
		if(PfUtilClient.isCloseOK()){
			onAddFrom61(PfUtilClient.getRetOldVos());
		}
	}else{
		super.onButtonClicked(bo);
	}
	
}
protected void setButtonsStatus(int iBillMode) {
	 switch(iBillMode){
	    case BillMode.New:
	    	getButtonManager().getButton("参照委外订单").setEnabled(false);
	    case BillMode.Update:
	      getButtonManager().getButton("参照委外订单").setEnabled(false);
	      break;
	      default:
	        getButtonManager().getButton("参照委外订单").setEnabled(true);
//	        if(getM_voBill()!=null&&(getM_voBill().getHeaderVO().getFbillflag().intValue()==3 //签字状态下设备卡片按钮可用
//	        		||getM_voBill().getHeaderVO().getFbillflag().intValue()==4)){
//	            getButtonManager().getButton("参照委外订单").setEnabled(true);
//	        }else{
//	            getButtonManager().getButton(ICButtonConst.BTN_ASSIST_CGEN_ASSET_CARD).setEnabled(false);
//	        }
	  }
}
/**
 * 
 * @param vos
 */
private void onAddFrom61(AggregatedValueObject[] vos) {
	try{
		 if(vos.length<=0)
			 return;
		 if(vos.length==1){
			 setRefBillsFlag(false);
			 setBillRefResultVO(null, vos);
		 }else{
			 setRefBillsFlag(true);
		     setBillRefMultiVOs(null,(GeneralBillVO[])vos);		 
		 }
	  }catch(Exception ex){
			showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
					  "4008busi","UPP4008busi-000297")/*@res "发生错误:"*/ + ex.getMessage());
	  }
}

/**
 * 创建者：王乃军
 * 功能：初始化系统参数
 * 参数：	
 * 返回：
 * 例外：
 * 日期：(2001-5-9 9:23:32)
 * 修改日期，修改人，修改原因，注释标志：
 * 
 * 
 * 
 * 
 */
protected void initPanel() {
	//需要单据参照
	super.setNeedBillRef(false);
}

public String getBillType() {
	return nc.vo.ic.pub.BillTypeConst.m_consignMachiningOut;
}

public String getFunctionNode() {
	return "40080806";
}

public int getInOutFlag() {
	return InOutFlag.OUT;
}

/**
 * 创建者：王乃军
 * 功能：
 * 参数： 
 * 返回：
 * 例外：
 * 日期：(2001-11-24 12:15:42)
 *  修改日期，修改人，修改原因，注释标志：
 */
void newMethod() {
}

/**
 * 创建者：王乃军
 * 功能：在列表方式下选择一张单据
 * 参数： 单据在alListData中的索引
 * 返回：无
 * 例外：
 * 日期：(2001-11-23 18:11:18)
 *  修改日期，修改人，修改原因，注释标志：
 */
protected void selectBillOnListPanel(int iBillIndex) {}

@Override
public EditCtrl getEditCtrl() {
  return new nc.ui.ic.ic213.EditCtrl(this);
}
}
