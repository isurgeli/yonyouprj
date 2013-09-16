/**
 * 
 */
package nc.ui.sc.order;

import java.awt.Container;

import nc.ui.pub.query.QueryConditionClient;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.scm.pub.query.SCMQueryConditionDlg;
import nc.ui.scm.pub.sourceref.IBillReferQueryProxy;
import nc.vo.querytemplate.TemplateInfo;

/**
 * @author gc
 *
 */
public class BillQueryDLG extends IBillReferQueryProxy{
	nc.ui.sc.order.ScOrderRefICQueryTemplet dlg = null;
	public BillQueryDLG() {
	}

	public BillQueryDLG(Container parent, TemplateInfo ti) {
		super(parent, ti);
	}

	public BillQueryDLG(Container parent) {
		super(parent);
	}
	@Override
	public QueryConditionDLG createNewQryDlg() {
		return null;
	}

	@Override
	public QueryConditionClient createOldQryDlg() {
		if(dlg == null){
			dlg = new nc.ui.sc.order.ScOrderRefICQueryTemplet(getContainer(),"Î¯Íâ¶©µ¥"/*-=notranslate=-*/, getPkCorp(),
					getOperator(), getFunNode(), getBusinessType(),
					getCurrentBillType(), getSourceBilltype(), getUserObj());
			dlg.setBillRefModeSelPanel(true);
		}
		return dlg;
	}

	@Override
	public boolean isNewQryDlg() {
		return false;
	}

	public boolean isShowDoubleTableRef() {
		return ((SCMQueryConditionDlg) createOldQryDlg())
				.isShowDoubleTableRef();
	}

	@Override
	public void setUserRefShowMode(boolean isShowDoubleTableRef) {
		((SCMQueryConditionDlg) createOldQryDlg())
				.setBillRefShowMode(isShowDoubleTableRef);
	}

}
