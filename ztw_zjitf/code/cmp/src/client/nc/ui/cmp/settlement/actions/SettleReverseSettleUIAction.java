package nc.ui.cmp.settlement.actions;

import java.awt.event.ActionEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Vector;

import javax.swing.Action;

import nc.bs.framework.common.NCLocator;
import nc.cmp.utils.CmpUtils;
import nc.cmp.utils.SettleUtils;
import nc.funcnode.ui.action.INCAction;
import nc.itf.cmp.pub.CmpSelfDefButtonNameConst;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractUIAppModel;
import nc.vo.cmp.BusiStatus;
import nc.vo.cmp.SettleStatus;
import nc.vo.cmp.exception.ExceptionHandler;
import nc.vo.cmp.settlement.SettleContext;
import nc.vo.cmp.settlement.SettleEnumCollection.Direction;
import nc.vo.cmp.settlement.SettlementAggVO;
import nc.vo.cmp.settlement.SettlementBodyVO;
import nc.vo.cmp.settlement.SettlementHeadVO;
import nc.vo.cmp.util.StringUtils;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;

public class SettleReverseSettleUIAction extends SettleDefaultAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public SettleReverseSettleUIAction() {
		setBtnName(CmpSelfDefButtonNameConst.getInstance().getReversesettleName());// ������
		// setBtnChinaName("������");
		putValue(Action.SHORT_DESCRIPTION, getBtnName());
		putValue(INCAction.CODE, "ȡ������");/*-=notranslate=-*/
	}

	@Override
	public void doAction(ActionEvent e) throws Exception {
		checkRecBillData();
		checkData();
		List<SettlementAggVO> beanList = SettleUtils.filterSettleInfo4HandSettleFlag4Settle(this.getSelectedAggVOs());
		if(beanList.size()==0){
			ExceptionHandler.cteateandthrowException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607set_0","03607set-0852")/*@res "û�пɲ����ı����м�¼"*/);
		}
		SettlementAggVO[] selectedAggVOs = new SettlementAggVO[beanList.size()];
		beanList.toArray(selectedAggVOs);
		validate(selectedAggVOs);
		
		
		checkState(selectedAggVOs);
		SettleContext context = new SettleContext();
		context.setBeanList(CmpUtils.covertArraysToList(selectedAggVOs));
		context = service.handleCancelSettle(context);
		getEdit().setValue(context.getBeanList().get(0));

		this.getEdit().setLoadBean(context.getBeanList().get(0));
		getModel().directlyUpdate(CmpUtils.covertListToArrays(context.getBeanList(), SettlementAggVO.class));
		showAuditInfo(this.getModel(), null);

		getEdit().loadNotenumber();
		getListView().loadNotenumber();
		if(context.getErrMap()!=null){
			StringBuilder sbMsg = new StringBuilder();

			for (String msgStr : context.getErrMap().keySet()) {
				sbMsg.append(msgStr);
			}
			ShowStatusBarMsgUtil.showErrorMsg( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607set_0","03607set-0022")/*@res "�����ɹ�"*/,sbMsg.toString(), this.getModel().getContext());
//			throw new BusinessException(sbMsg.toString());
		}
	}

	private void checkRecBillData() throws BusinessException {
		List<SettlementAggVO> beanList = SettleUtils.filterSettleInfo4HandSettleFlag4Settle(this.getSelectedAggVOs());
		StringWriter errMsg = new StringWriter();
		PrintWriter errPrt = new PrintWriter(errMsg);
		for (SettlementAggVO bean : beanList) {
			SettlementHeadVO head= (SettlementHeadVO)bean.getParentVO();
			if (head.getTradertypecode().equals("D4")) { //�Դ��տ���㵥
				StringBuffer sql = new StringBuffer();
				sql.append("select count(cmp_recbilldetail.def20) from cmp_recbilldetail where (cmp_recbilldetail.def20 <> '~' or  cmp_recbilldetail.def20 <> 'D') ");
				sql.append("and cmp_recbilldetail.pk_recbill='");
				sql.append(head.getPk_busibill());
				sql.append("'");
				
				IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
				@SuppressWarnings("unchecked")
				Vector<Vector<Object>> count = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
				if (!count.get(0).get(0).equals(0)) {
					errPrt.println("���ݺ�Ϊ["+head.getBillcode()+"]�ĵ����ѱ�SAPϵͳ�����޷�ȡ�����㡣");
				}
			}
		}
		if (errMsg.toString().length() > 0)
			throw new BusinessException(errMsg.toString());
	}

	/**
	 *
	 *
	 * @throws BusinessException
	 *
	 */
	public void showAuditInfo(AbstractUIAppModel model, AggregatedValueObject[] bills) throws BusinessException {
		ShowStatusBarMsgUtil.showStatusBarMsg( nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607set_0","03607set-0022")/*@res "�����ɹ�"*/, model.getContext());
	}

	private void checkState(SettlementAggVO... selectedAggVOs) throws BusinessException {
		StringBuffer sbmsg = new StringBuffer();
		for (SettlementAggVO settlementAggVO : selectedAggVOs) {
			SettlementHeadVO headvo = (SettlementHeadVO) settlementAggVO.getParentVO();

		}
		if (!sbmsg.toString().equals("")) {
			throw new BusinessException(sbmsg.toString());
		}

	}

	private void validate(SettlementAggVO[] selectedAggVOs) throws BusinessException {
		
		
		for (SettlementAggVO settlementAggVO :selectedAggVOs) {
			SettlementBodyVO[] bodyvos = (SettlementBodyVO[]) settlementAggVO.getChildrenVO();
			for (SettlementBodyVO body : bodyvos) {
				if (body.getPk_Inneraccount() != null) {
					String billcode = ((SettlementHeadVO) settlementAggVO.getParentVO()).getBillcode();
					String errMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607set_0","03607set-0017")/*@res "���ڲ��˻��ĵ��ݣ����ܽ��㣬������ѡ��"*/;
					if (StringUtils.isNotNullWithTrim(billcode)) {
						errMsg = nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("3607set_0", "03607set-0864", null, new String[] {billcode})/*"���ݺ�Ϊ{0}�ĵ��� ���ڲ��˻������ܽ��㣬������ѡ��\n*/;
					}
					ExceptionHandler.cteateandthrowException(errMsg);
				}
			}
		}
		
		
	}

	/**
	 * ����vo����
	 *
	 * @param selectedAggVOs
	 * @throws BusinessException
	 */
	private void proceccAttribute(SettlementAggVO... selectedAggVOs) throws BusinessException {
		for (SettlementAggVO settlementAggVO : selectedAggVOs) {
			SettlementHeadVO headvo = (SettlementHeadVO) settlementAggVO.getParentVO();
			headvo.setSettlestatus(SettleStatus.NONESETTLE.getStatus());
		}
	}

	@Override
	public boolean isActionEnable() {
		if(UIState.ADD==this.getModel().getUiState()||UIState.EDIT==this.getModel().getUiState()){
			return false;
		}
		SettlementAggVO[] selectedAggVOs = getSelectedAggVOs();
		if (CmpUtils.isListNull(selectedAggVOs)) {
			return false;
		}
		
		for (SettlementAggVO settlementAggVO : selectedAggVOs) {
			SettlementHeadVO headvo = (SettlementHeadVO) settlementAggVO.getParentVO();
			if(!headvo.getDirection().equals(Direction.PAY.VALUE)&&(headvo.getSettlestatus()==null || !headvo.getSettlestatus().equals(SettleStatus.SUCCESSSETTLE.getStatus()))){
				// �Ǹ������Ҳ��ǽ�����ɵĲ���������
				return false;
			}
			
			if(SettleUtils.isExistInnerAccount(settlementAggVO)){
				// ���ڲ��˻��Ĳ����ֹ�ȡ������
				return false;
			}
			
			
			
			if(headvo.getPk_tradetype()==null){
				return false;
			}
			for (SettlementBodyVO body : (SettlementBodyVO[])settlementAggVO.getChildrenVO()) {
				if(body.getSettlestatus()!=null && body.getSettlestatus().equals(SettleStatus.SETTLERESET.getStatus())){
					// ���н��������Ϣ�����ܷ�����
					return false;
				}
			}
			if (headvo.getBusistatus() == null || !headvo.getBusistatus().equals(BusiStatus.Sign.getBillStatusKind())) {
				// ��ǩ��̬��
				return false;
			}
		}
		return super.isActionEnable();
	}

}