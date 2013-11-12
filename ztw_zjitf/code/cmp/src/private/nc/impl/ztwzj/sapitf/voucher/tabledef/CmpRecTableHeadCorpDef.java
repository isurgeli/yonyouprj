package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;

public class CmpRecTableHeadCorpDef extends CmpRecTableDef {
	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("voucherflag",	"cmp_recbilldetail",	"def20"),
				new SQLField("pk_qryorg",	"cmp_recbill",			"pk_org")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), new OrgFieldDef().getHeadSQLFields(), cur);
	}
}
