package nc.ui.bd.cust.baseinfo.action;

import java.awt.event.ActionEvent;

import nc.pubitf.para.SysInitQuery;
import nc.ui.uif2.actions.AddAction;
import nc.vo.bd.pub.IBDSysInitCodeConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

/**
 * �ͻ�����Action
 *
 * @author jiangjuna
 * @since NC6.0
 *
 */
@SuppressWarnings("serial")
public class CustBaseInfoAddAction extends AddAction {

	private UFBoolean canDirectAdd = null;

	public void doAction(ActionEvent e) throws Exception {
		throw new BusinessException("��ͨ��MDM�ӿ����ӿͻ���");
		//checkCanAdd();
		//super.doAction(e);
	}

	private void checkCanAdd() throws BusinessException {
		if (!canDirectAdd()) {
			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("10140cub","010140cub0008")/*@res "�ͻ�ֻ��ͨ�������������ӣ������ڽڵ�ֱ��¼��."*/);
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