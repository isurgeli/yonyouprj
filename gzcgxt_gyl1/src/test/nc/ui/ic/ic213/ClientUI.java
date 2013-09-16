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
 * ί�мӹ����ϵ�
 *
 *
 * �������ڣ�(2001-11-23 15:39:43)
 * @author������
 */
public class ClientUI extends nc.ui.ic.pub.bill.GeneralBillClientUI {
	
	
/**
 * ClientUI2 ������ע�⡣
 */
public ClientUI() {
	super();
	initialize();
}

/**
 * ClientUI ������ע�⡣
 * add by liuzy 2007-12-18 ���ݽڵ�����ʼ������ģ��
 */
public ClientUI(FramePanel fp) {
 super(fp);
 initialize();
}

/**
 * ClientUI ������ע�⡣
 * nc 2.2 �ṩ�ĵ������鹦�ܹ����ӡ�
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
 * �����ߣ����˾�
 * ���ܣ����ݱ༭����
 * ������	
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-5-9 9:23:32)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
				//���幤�����Ĳ���
			if (bd.getBodyItem("cworkcentername") != null) {
				nc.ui.pub.beans.UIRefPane ref1 = new nc.ui.pub.beans.UIRefPane();
				ref1.setIsCustomDefined(true);
//				�޸��ˣ������� �޸�ʱ�䣺2008-6-3 ����11:07:21 �޸�ԭ�򣺸��ݹ�˾���˹������ġ�
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
 * �����ߣ����˾�
 * ���ܣ���������ѡ��ı�
 * ������	
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-5-9 9:23:32)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * 
 * 
 * 
 */
protected void afterBillItemSelChg(int iRow, int iCol) {
	//nc.vo.scm.pub.SCMEnv.out("haha,sel chged!");

}
/**
 * �����ߣ����˾�
 * ���ܣ����ݱ༭�¼�����
 * ������e���ݱ༭�¼�
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-5-8 19:08:05)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * 
 * 
 * 
 */
public boolean beforeBillItemEdit(nc.ui.pub.bill.BillEditEvent e) {
	return true;
}
/**
 * �����ߣ����˾�
 * ���ܣ���������ѡ��ı�
 * ������	
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-5-9 9:23:32)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * 
 * 
 * 
 */
protected void beforeBillItemSelChg(int iRow, int iCol) {
	//nc.vo.scm.pub.SCMEnv.out("haha,before sel");

}
/**
  * �����ߣ����˾�
  * ���ܣ����󷽷�������ǰ��VO���
  * �����������浥�� 
  * ���أ�
  * ���⣺
  * ���ڣ�(2001-5-24 ���� 5:17)
  * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
  */
protected boolean checkVO(nc.vo.ic.pub.bill.GeneralBillVO voBill) {
	return super.checkVO();
}
/**
 * �����ߣ����Ӣ
 * ���ܣ��õ���ǰ����vo,������λ/���кźͽ��������е�����,������ɾ������
 * ������	
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-5-9 9:23:32)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
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
	if(bo.getName().equals("����ί�ⶩ��")){//gc
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
	    	getButtonManager().getButton("����ί�ⶩ��").setEnabled(false);
	    case BillMode.Update:
	      getButtonManager().getButton("����ί�ⶩ��").setEnabled(false);
	      break;
	      default:
	        getButtonManager().getButton("����ί�ⶩ��").setEnabled(true);
//	        if(getM_voBill()!=null&&(getM_voBill().getHeaderVO().getFbillflag().intValue()==3 //ǩ��״̬���豸��Ƭ��ť����
//	        		||getM_voBill().getHeaderVO().getFbillflag().intValue()==4)){
//	            getButtonManager().getButton("����ί�ⶩ��").setEnabled(true);
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
					  "4008busi","UPP4008busi-000297")/*@res "��������:"*/ + ex.getMessage());
	  }
}

/**
 * �����ߣ����˾�
 * ���ܣ���ʼ��ϵͳ����
 * ������	
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-5-9 9:23:32)
 * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 * 
 * 
 * 
 * 
 */
protected void initPanel() {
	//��Ҫ���ݲ���
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
 * �����ߣ����˾�
 * ���ܣ�
 * ������ 
 * ���أ�
 * ���⣺
 * ���ڣ�(2001-11-24 12:15:42)
 *  �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 */
void newMethod() {
}

/**
 * �����ߣ����˾�
 * ���ܣ����б�ʽ��ѡ��һ�ŵ���
 * ������ ������alListData�е�����
 * ���أ���
 * ���⣺
 * ���ڣ�(2001-11-23 18:11:18)
 *  �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
 */
protected void selectBillOnListPanel(int iBillIndex) {}

@Override
public EditCtrl getEditCtrl() {
  return new nc.ui.ic.ic213.EditCtrl(this);
}
}
