package nc.ui.gzcg.pub;

import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.vo.pub.query.ConditionVO;

public class ReportLinkQueryData implements ILinkQueryData{

	public String getBillID() {
		return null;
	}

	public String getBillType() {
		return null;
	}

	public String getPkOrg() {
		return null;
	}

	public Object getUserObject() {
		return null;
	}

	private String cmanageid;
	private String cvendormangid;
	private ConditionVO[] voCondition;
	public String getCmanageid() {
		return cmanageid;
	}

	public void setCmanageid(String cmanageid) {
		this.cmanageid = cmanageid;
	}
	
	public String getCvendormangid() {
		return cvendormangid;
	}

	public void setCvendormangid(String cvendormangid) {
		this.cvendormangid = cvendormangid;
	}

	public ConditionVO[] getVoCondition() {
		return voCondition;
	}

	public void setVoCondition(ConditionVO[] voCondition) {
		this.voCondition = voCondition;
	}
}
