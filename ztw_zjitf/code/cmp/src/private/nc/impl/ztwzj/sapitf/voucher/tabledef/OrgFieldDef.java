package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.SQLField;

public class OrgFieldDef {

	public SQLField[] getHeadSQLFields() {
		return new SQLField[] {
				new SQLField("BUKRS",		"org_orgs_h",			"code"),
				new SQLField("ABSTRACT",	"org_orgs_b",			DELIMITER.getParaExp("ABSTRACTS")+"||org_orgs_b.name||"+DELIMITER.getParaExp("ABSTRACTE")),
				new SQLField("CUSTCODE",	"org_orgs_b",			"code"),
				new SQLField("CUSTNAME",	"org_orgs_b",			"name"),
				new SQLField("EXBANK",		"bd_bankdoc_h",			"name"),
				new SQLField("EXBANKNUM",	"bd_bankaccsub_h",		"accnum"),
				new SQLField("OPPEXBANK",	"bd_bankdoc_b",			"name"),
				new SQLField("OPPEXBANKNUM","bd_bankaccsub_b",		"accnum")
		};
	}
	
	public SQLField[] getBodySQLFields() {
		return new SQLField[] {
				new SQLField("BUKRS",		"org_orgs_b",			"code"),
				new SQLField("ABSTRACT",	"org_orgs_h",			DELIMITER.getParaExp("ABSTRACTS")+"||org_orgs_h.name||"+DELIMITER.getParaExp("ABSTRACTE")),
				new SQLField("CUSTCODE",	"org_orgs_h",			"code"),
				new SQLField("CUSTNAME",	"org_orgs_h",			"name"),
				new SQLField("EXBANK",		"bd_bankdoc_b",			"name"),
				new SQLField("EXBANKNUM",	"bd_bankaccsub_b",		"accnum"),
				new SQLField("OPPEXBANK",	"bd_bankdoc_h",			"name"),
				new SQLField("OPPEXBANKNUM","bd_bankaccsub_h",		"accnum")
		};
	}
}
