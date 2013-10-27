package nc.ui.bd.supplier.baseinfo.action;

import java.awt.event.ActionEvent;

import nc.pubitf.para.SysInitQuery;
import nc.ui.uif2.actions.AddAction;
import nc.vo.bd.pub.IBDSysInitCodeConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

/**
 * 供应商基本信息添加Action.
 *
 * @author guohuia
 * @created on 2009.2.24.
 * @since NC60
 *
 */
public class SupBaseInfoAddAction extends AddAction {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void doAction(ActionEvent e) throws Exception {
		throw new BusinessException("请通过MDM接口增加供应商！");
		
//		UFBoolean isCanAdd = SysInitQuery.getParaBoolean(getModel()
//				.getContext().getPk_group(), IBDSysInitCodeConst.SUPPLIER_ADD);
//		if (!isCanAdd.booleanValue()) {
//			throw new BusinessException(nc.vo.ml.NCLangRes4VoTransl.getNCLangRes().getStrByID("10140sub","010140sub0011")/*@res "供应商只能通过申请审批增加，不能在节点直接录入！"*/);
//		}
//		super.doAction(e);
	}
}