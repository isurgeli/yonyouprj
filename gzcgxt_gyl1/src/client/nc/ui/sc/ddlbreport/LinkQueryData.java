package nc.ui.sc.ddlbreport;

import nc.ui.pub.linkoperate.ILinkQueryData;

public class LinkQueryData implements ILinkQueryData
{
	private String billID = null;
	private String billType = null;
	private String pkOrg = null;
	private Object userObject = null;
	public String getBillID() {
		// TODO 自动生成方法存根
		return billID;
	}

	public String getBillType() {
		// TODO 自动生成方法存根
		return billType;
	}

	public String getPkOrg() {
		// TODO 自动生成方法存根
		return pkOrg;
	}

	public Object getUserObject() {
		// TODO 自动生成方法存根
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
