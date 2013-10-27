package nc.ui.bd.cust.baseinfo.action;

import java.awt.event.ActionEvent;

import nc.pubitf.para.SysInitQuery;
import nc.ui.uif2.actions.AddAction;
import nc.vo.bd.pub.IBDSysInitCodeConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

/**
 * 客户新增Action
 *
 * @author jiangjuna
 * @since NC6.0
 *
 */
@SuppressWarnings("serial")
public class CustBaseInfoAddAction extends AddAction {

	private UFBoolean canDirectAdd = null;

	public void doAction(ActionEvent e) throws Exception {
		throw new BusinessException("请通过MDM接口增加客户！");
		//checkCanAdd();
		//super.doAction(e);
	}

	private void checkCanAdd() throws BusinessException {
		if (!canDirectAdd()) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("10140cub","010140cub0008")/*@res "客户只能通过申请审批增加，不能在节点直接录入."*/);
		}
	}

	private boolean canDirectAdd() throws BusinessException {
		if (canDirectAdd == null) {
			canDirectAdd = SysInitQuery.getParaBoolean(getModel().getContext()
					.getPk_group(), IBDSysInitCodeConst.CUSTOMER_ADD);
		}
		return canDirectAdd == null ? true : canDirectAdd.booleanValue();
	}
}