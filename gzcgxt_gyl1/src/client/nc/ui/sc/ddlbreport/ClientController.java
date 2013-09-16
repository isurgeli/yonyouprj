package nc.ui.sc.ddlbreport;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.bd.CorpVO;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.trade.pub.HYBillVO;

/**
 */
public class ClientController implements ICardController, ISingleController {

	/**
	 * ˵����
	 * 
	 */
	public ClientController() {
		// TODO �Զ����ɹ��캯�����
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.ICardController#getCardBodyHideCol()
	 */
	public String[] getCardBodyHideCol() {
		// TODO �Զ����ɷ������
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.ICardController#getCardButtonAry()
	 */
	public int[] getCardButtonAry() {
		// TODO �Զ����ɷ������
			
//		if(ClientEnvironment.getInstance().getCorporation().getPrimaryKey().equals("0001")){
			return new int[]{
					IBillButton.Query,
//					IBillButton.Edit,
//					IBillButton.Save,
//					IBillButton.Line,
//					IBillButton.Cancel,
//					IBillButton.Delete,
//					IBillButton.Refresh,
					1000
					
		};
//		}else{
//			return new int[]{IBillButton.Refresh};
//		}
	}
	public int[] getListButtonAry() {
		// TODO �Զ����ɷ������
			
//		if(ClientEnvironment.getInstance().getCorporation().getPrimaryKey().equals("0001")){
			return new int[]{
//					IBillButton.Add,
					IBillButton.Query,
//					IBillButton.Edit,
//					IBillButton.Save,
//					IBillButton.Line,
//					IBillButton.Cancel,
//					IBillButton.Delete,
//					IBillButton.Refresh,
					1000
					
		};
//		}else{
//			return new int[]{IBillButton.Refresh};
//		}
	}
	

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		// TODO �Զ����ɷ������
		return true;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		// TODO �Զ����ɷ������
		return false;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		// TODO �Զ����ɷ������
		return "CG99";
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		// TODO �Զ����ɷ������
		return new String[]{HYBillVO.class.getName(),OrderDdlbVO.class.getName(),OrderDdlbVO.class.getName()};
	}

	/*
	 * ���� Javadoc��
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBodyCondition()
	 */
	public String getBodyCondition() {
//		String pk_corp = ClientEnvironment.getInstance().getCorporation()
//				.getPk_corp();
//		if (pk_corp != null && "0001".equals(pk_corp)) {
//			// ����Ǽ�������ʾ���й�˾��֪ͨ��
//			return " vbillcode||vovertype in( " +
//					     "select t.vbillcode||max(t.vovertype) from chamc_deal_overtime t group by t.vbillcode)";
//		} else {
//			CorpVO corpvo = ClientEnvironment.getInstance().getCorporation();
//			return "pk_corp in (select pk_corp from bd_corp where unitcode like '"+corpvo.getUnitcode()+"%') and " +
//					"vbillcode||vovertype in(select t.vbillcode||max(t.vovertype)   from chamc_deal_overtime t group by t.vbillcode)";
//		}
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getBodyZYXKey()
	 */
	public String getBodyZYXKey() {
		// TODO �Զ����ɷ������
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getBusinessActionType()
	 */
	public int getBusinessActionType() {
		// TODO �Զ����ɷ������
		return IBusinessActionType.BD;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getChildPkField()
	 */
	public String getChildPkField() {
		// TODO �Զ����ɷ������
		return "pk_eam_ncfa_person";
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getHeadZYXKey()
	 */
	public String getHeadZYXKey() {
		// TODO �Զ����ɷ������
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#getPkField()
	 */
	public String getPkField() {
		// TODO �Զ����ɷ������
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#isEditInGoing()
	 */
	public Boolean isEditInGoing() throws Exception {
		// TODO �Զ����ɷ������
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#isExistBillStatus()
	 */
	public boolean isExistBillStatus() {
		// TODO �Զ����ɷ������
		return false;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.controller.IControllerBase#isLoadCardFormula()
	 */
	public boolean isLoadCardFormula() {
		// TODO �Զ����ɷ������
		return true;
	}

	/* ���� Javadoc��
	 * @see nc.ui.trade.bill.ISingleController#isSingleDetail()
	 */
	public boolean isSingleDetail() {
		// TODO �Զ����ɷ������
		return true;
	}

}
