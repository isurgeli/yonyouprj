package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.ISQLDefine;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.ORDER;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;
import nc.itf.lxt.pub.sqltool.SQLOrderbyClause;
import nc.itf.lxt.pub.sqltool.SQLTable;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;

public abstract class CmpBaseTableDef implements ISQLDefine {

	@Override
	public SQLTable[] getSQLTables() {
		return new SQLTable[] {
				new SQLTable("bd_bankdoc_h",	"bd_bankdoc"),
				new SQLTable("bd_bankdoc_b",	"bd_bankdoc"),
				new SQLTable("bd_bankaccsub_h",	"bd_bankaccsub"),
				new SQLTable("bd_bankaccsub_b",	"bd_bankaccsub"),
				new SQLTable("bd_bankaccbas_h",	"bd_bankaccbas"),
				new SQLTable("bd_bankaccbas_b",	"bd_bankaccbas"),
				new SQLTable("org_orgs_h",		"org_orgs"),
				new SQLTable("org_orgs_b",		"org_orgs")
		};
	}

	@Override
	public SQLField[] getSQLFields() {
		return new SQLField[] {
				new SQLField("BUKRSLEVEL",	null,					DELIMITER.getParaExp("BUKRSLEVEL")),
				new SQLField("BUSITYPE",	null,					DELIMITER.getParaExp("BUSITYPE")),
				new SQLField("DOCUMENTSTYPE",	"bd_billtype",		"billtypename"),
				new SQLField("OUTBANK",	null,					"''"),
				new SQLField("OUTBANKNUM",	null,				"''"),
				new SQLField("INBANK",	null,					"''"),
				new SQLField("INBANKNUM",null,					"''"),
				new SQLField("pk_fatherorg","org_orgs_b",			"pk_fatherorg")
		};
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		return new SQLJoinClause[]{
			new SQLJoinClause("bd_bankaccbas_h",	"pk_bankaccbas","bd_bankaccsub_h",	"pk_bankaccbas"),
			new SQLJoinClause("bd_bankaccbas_b",	"pk_bankaccbas","bd_bankaccsub_b",	"pk_bankaccbas"),
			new SQLJoinClause("bd_bankdoc_h",		"pk_bankdoc",	"bd_bankaccbas_h",	"pk_bankdoc"),
			new SQLJoinClause("bd_bankdoc_b",		"pk_bankdoc",	"bd_bankaccbas_b",	"pk_bankdoc")
		};
	}

	@Override
	public SQLOrderbyClause[] getOrderbys() {
		return new SQLOrderbyClause[] {
			new SQLOrderbyClause("BUDAT", ORDER.ASC)
		};
	}

	@Override
	public SQLWhereClause[] getFixWheres() {
		return new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "paystatus", OPERATOR.EQ, "2"),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "pk_fatherorg", OPERATOR.EQ, "pk_org")
		};
	}
}
