package nc.ui.gzcg.bd;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.button.IBillButton;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.trade.pub.HYBillVO;
import nc.vo.trade.pub.IBillStatus;

public class SampleManController implements ICardController,ISingleController {
	private String bodyCondition = "";
	
	public SampleManController(String _bodyCondition){
		bodyCondition = _bodyCondition;
		bodyCondition += "and vdef1||vsampleno not in (select distinct QC_CGHZBG_B.Ypname||QC_CGHZBG_B.Jcpici from QC_CGHZBG_H, QC_CGHZBG_B where QC_CGHZBG_H.Pk_Cghzbg_h=QC_CGHZBG_B.Pk_Cghzbg_h and QC_CGHZBG_H.VBILLSTATUS=";
		bodyCondition += IBillStatus.CHECKPASS+")";
	}
	
	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#getCardBodyHideCol()
	 */
	public String[] getCardBodyHideCol() {
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#getCardButtonAry()
	 */
	public int[] getCardButtonAry() {
		return new int[]{
				IBillButton.Add,
				IBillButton.Edit,
				IBillButton.Delete,
				IBillButton.Line,
				IBillButton.Save,
				IBillButton.Cancel,
				IBillButton.Query
		};
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#isShowCardRowNo()
	 */
	public boolean isShowCardRowNo() {
		return true;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ICardController#isShowCardTotal()
	 */
	public boolean isShowCardTotal() {
		return false;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBillType()
	 */
	public String getBillType() {
		return GZCGConstant.SAMPLEUIFUNCODE.getValue();
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBillVoName()
	 */
	public String[] getBillVoName() {
		return new String[]{
			HYBillVO.class.getName(),
			samplevo.class.getName(),
			samplevo.class.getName()
		};
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBodyCondition()
	 */
	public String getBodyCondition() {
		return bodyCondition;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBodyZYXKey()
	 */
	public String getBodyZYXKey() {
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getBusinessActionType()
	 */
	public int getBusinessActionType() {
		return IBusinessActionType.BD;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getChildPkField()
	 */
	public String getChildPkField() {
		return samplevo.PK_SAMPLE;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getHeadZYXKey()
	 */
	public String getHeadZYXKey() {
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#getPkField()
	 */
	public String getPkField() {
		return samplevo.PK_SAMPLE;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#isEditInGoing()
	 */
	public Boolean isEditInGoing() throws Exception {
		return null;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#isExistBillStatus()
	 */
	public boolean isExistBillStatus() {
		return false;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.controller.IControllerBase#isLoadCardFormula()
	 */
	public boolean isLoadCardFormula() {
		return false;
	}

	/* （非 Javadoc）
	 * @see nc.ui.trade.bill.ISingleController#isSingleDetail()
	 */
	public boolean isSingleDetail() {
		//单表体
		return true;
	}

	/* （非 Javadoc）
	 * @see nc.ui.bd.pub.IBDCardControll#isDeleteReference()
	 */
	public boolean isDeleteReference() {
		//检查删除引用
		return true;
	}

	/* （非 Javadoc）
	 * @see nc.ui.bd.pub.IBDCardControll#isUpdateReferenct()
	 */
	public boolean isUpdateReferenct() {
		//不检查更新引用
		return false;
	}
}
