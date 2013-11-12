package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;

public class SfDeliTableBodyCorpDef extends SfDeliTableDef {
	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("voucherflag",	"sf_deliveryreceipt",	"vuserdef9"),
				new SQLField("pk_qryorg",	"sf_delivery_b",		"pk_org_p")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), new OrgFieldDef().getBodySQLFields(), cur);
	}
}
