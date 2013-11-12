package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;

public class SfDeliTableHeadCorpDef extends SfDeliTableDef {
	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("voucherflag",	"sf_delivery_b",		"vuserdef9"),
				new SQLField("pk_qryorg",	"sf_delivery_h",		"pk_org")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), new OrgFieldDef().getHeadSQLFields(), cur);
	}
}
