package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;

public class CmpPayTableHeadCorpDef extends CmpPayTableDef {
	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("voucherflag",	"cmp_paybilldetail",	"def20"),
				new SQLField("pk_qryorg",	"cmp_paybill",			"pk_org")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), new OrgFieldDef().getHeadSQLFields(), cur);
	}
}
