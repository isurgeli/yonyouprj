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
	 * 说明：
	 * 
	 */
	public ClientController() {
		// TODO 自动生成构造函数存根
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#getCardBodyHideCol()
	 */
	public String[] getCardBodyHideCol() {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#getCardButtonAry()
	 */
	public int[] getCardButtonAry() {
		// TODO 自动生成方法存根
			
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
		// TODO 自动生成方法存根
			
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
	

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		// TODO 自动生成方法存根
		return true;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		// TODO 自动生成方法存根
		return false;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		// TODO 自动生成方法存根
		return "CG99";
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		// TODO 自动生成方法存根
		return new String[]{HYBillVO.class.getName(),OrderDdlbVO.class.getName(),OrderDdlbVO.class.getName()};
	}

	/*
	 * （非 Javadoc）
	 * 
	 * @see nc.ui.trade.controller.IControllerBase#getBodyCondition()
	 */
	public String getBodyCondition() {
//		String pk_corp = ClientEnvironment.getInstance().getCorporation()
//				.getPk_corp();
//		if (pk_corp != null && "0001".equals(pk_corp)) {
//			// 如果是集团这显示所有公司的通知单
//			return " vbillcode||vovertype in( " +
//					     "select t.vbillcode||max(t.vovertype) from chamc_deal_overtime t group by t.vbillcode)";
//		} else {
//			CorpVO corpvo = ClientEnvironment.getInstance().getCorporation();
//			return "pk_corp in (select pk_corp from bd_corp where unitcode like '"+corpvo.getUnitcode()+"%') and " +
//					"vbillcode||vovertype in(select t.vbillcode||max(t.vovertype)   from chamc_deal_overtime t group by t.vbillcode)";
//		}
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBodyZYXKey()
	 */
	public String getBodyZYXKey() {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBusinessActionType()
	 */
	public int getBusinessActionType() {
		// TODO 自动生成方法存根
		return IBusinessActionType.BD;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getChildPkField()
	 */
	public String getChildPkField() {
		// TODO 自动生成方法存根
		return "pk_eam_ncfa_person";
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getHeadZYXKey()
	 */
	public String getHeadZYXKey() {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getPkField()
	 */
	public String getPkField() {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#isEditInGoing()
	 */
	public Boolean isEditInGoing() throws Exception {
		// TODO 自动生成方法存根
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#isExistBillStatus()
	 */
	public boolean isExistBillStatus() {
		// TODO 自动生成方法存根
		return false;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#isLoadCardFormula()
	 */
	public boolean isLoadCardFormula() {
		// TODO 自动生成方法存根
		return true;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ISingleController#isSingleDetail()
	 */
	public boolean isSingleDetail() {
		// TODO 自动生成方法存根
		return true;
	}

}
