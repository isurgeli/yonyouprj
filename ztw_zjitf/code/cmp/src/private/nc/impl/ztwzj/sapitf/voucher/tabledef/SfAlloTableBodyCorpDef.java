package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;

public class SfAlloTableBodyCorpDef extends SfAlloTableDef{
	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("voucherflag",	"sf_allocatereceipt",	"vueserdef9"),
				new SQLField("pk_qryorg",	"sf_allocate_b",		"pk_org_r")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), new OrgFieldDef().getBodySQLFields(), cur);
	}
}

