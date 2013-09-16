package nc.ui.sourceref.sc;

import java.awt.Container;

import nc.ui.ml.NCLangRes;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.query.QueryConditionClient;
import nc.ui.querytemplate.QueryConditionDLG;
import nc.ui.scm.pub.query.SCMQueryConditionDlg;
import nc.ui.scm.pub.sourceref.IBillReferQueryProxy;
import nc.vo.querytemplate.TemplateInfo;

@SuppressWarnings("restriction")
public class CGBillReferQPSCToStore extends IBillReferQueryProxy{
	QueryConditionClient dlg = null;

	public CGBillReferQPSCToStore(Container parent) {
		super(parent);
	
	}
	public CGBillReferQPSCToStore(Container parent ,nc.vo.querytemplate.TemplateInfo templateinfo) {
		super(parent);
//		pk_corp=ClientEnvironment.getInstance().getCorporation().getPrimaryKey();
//		setTempletID(null);
	}
//	@Override
//	public QueryConditionDLG createNewQryDlg() {
//		return null;
//	}

//	@Override
//	public void setTempletID(String newID) {
//		// TODO Auto-generated method stub
//		super.setTempletID("401201000000000000GC");
//	}

	@Override
	public boolean isNewQryDlg() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public QueryConditionDLG createNewQryDlg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isShowDoubleTableRef() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setUserRefShowMode(boolean isShowDoubleTableRef) {
		// TODO Auto-generated method stub
//		setUserRefShowMode(false); 
	}

	@Override
	public QueryConditionClient createOldQryDlg() {
		if(dlg == null){
			dlg = //new QueryConditionClient(getContainer(),"委外订单");
			 new nc.ui.sc.order.CGScOrderRefICQueryTemplet(getContainer(),"委外订单"/*-=notranslate=-*/);//, getPkCorp(),
//					getOperator(), "4FREF61"/*getFunNode()*/, getBusinessType(),
//					getCurrentBillType(), getSourceBilltype(), getUserObj());
			dlg.setTempletID("401201000000000000GC");
//			dlg.setBillRefModeSelPanel(true);
			dlg.setNormalShow(false);
		
		}
		return dlg;
	}

//	@Override
//	public boolean isNewQryDlg() {
//		return false;
//	}
//
//	public boolean isShowDoubleTableRef() {
//		return false;//((SCMQueryConditionDlg) createOldQryDlg())
//				//.isShowDoubleTableRef();
//	}
//

}
