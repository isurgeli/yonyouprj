package nc.ui.sc.ddlbreport;

import nc.ui.pub.linkoperate.ILinkQueryData;

public class LinkQueryData implements ILinkQueryData
{
	private String billID = null;
	private String billType = null;
	private String pkOrg = null;
	private Object userObject = null;
	public String getBillID() {
		// TODO �Զ����ɷ������
		return billID;
	}

	public String getBillType() {
		// TODO �Զ����ɷ������
		return billType;
	}

	public String getPkOrg() {
		// TODO �Զ����ɷ������
		return pkOrg;
	}

	public Object getUserObject() {
		// TODO �Զ����ɷ������
		return userObject;
	}

	public void setBillID(String billID) {
		this.billID = billID;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public void setPkOrg(String pkOrg) {
		this.pkOrg = pkOrg;
	}

	public void setUserObject(Object userObject) {
		this.userObject = userObject;
	}
}
