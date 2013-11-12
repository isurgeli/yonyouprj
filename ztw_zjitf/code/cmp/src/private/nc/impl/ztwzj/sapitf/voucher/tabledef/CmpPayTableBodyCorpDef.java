package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;

public class CmpPayTableBodyCorpDef extends CmpPayTableDef {
	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("voucherflag",	"cmp_paybilldetail",	"def19"),
				new SQLField("pk_qryorg",	"bd_supplier",			"pk_financeorg")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), new OrgFieldDef().getBodySQLFields(), cur);
	}
}