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

public class CmpTransferTableDef implements ISQLDefine {

	@Override
	public String getMianTable() {
		return "cmp_transformbill";
	}

	@Override
	public SQLTable[] getSQLTables() {
		return new SQLTable[] {
				new SQLTable("bd_bankdoc_i",	"bd_bankdoc"),
				new SQLTable("bd_bankdoc_o",	"bd_bankdoc"),
				new SQLTable("bd_bankaccsub_i",	"bd_bankaccsub"),
				new SQLTable("bd_bankaccsub_o",	"bd_bankaccsub"),
				new SQLTable("bd_bankaccbas_i",	"bd_bankaccbas"),
				new SQLTable("bd_bankaccbas_o",	"bd_bankaccbas"),
		};
	}

	@Override
	public SQLField[] getSQLFields() {
		return new SQLField[] {
				new SQLField("BUKRSLEVEL",	null,					DELIMITER.getParaExp("BUKRSLEVEL")),
				new SQLField("BUSITYPE",	null,					DELIMITER.getParaExp("BUSITYPE")),
				new SQLField("DOCUMENTSTYPE",	"bd_billtype",		"billtypename"),
				new SQLField("OUTBANK",		"bd_bankdoc_o",			"name"),
				new SQLField("OUTBANKNUM",	"bd_bankaccsub_o",		"accnum"),
				new SQLField("INBANK",		"bd_bankdoc_i",			"name"),
				new SQLField("INBANKNUM",	"bd_bankaccsub_i",		"accnum"),
				new SQLField("BUKRS",		"org_orgs",				"decode(substr(org_orgs.code,5),'100099','0001',substr(org_orgs.code,5))"),
				new SQLField("ABSTRACT",	"org_orgs",				DELIMITER.getParaExp("ABSTRACTS")+"||org_orgs.name||"+DELIMITER.getParaExp("ABSTRACTE")),
				new SQLField("CUSTCODE",	"org_orgs",				"decode(substr(org_orgs.code,5),'100099','0001',substr(org_orgs.code,5))"),
				new SQLField("CUSTNAME",	"org_orgs",				"name"),
				new SQLField("EXBANK",		null,					"''"),
				new SQLField("EXBANKNUM",	null,					"''"),
				new SQLField("OPPEXBANK",	null,					"''"),
				new SQLField("OPPEXBANKNUM",null,					"''"),
				new SQLField("VOUCHERID",	"cmp_transformbill",	"pk_transformbill"),
				new SQLField("DOCUMENTSNO",	"cmp_transformbill",	"vbillno"),
				new SQLField("BUDAT",		"cmp_transformbill",	"settlementdate"),
				new SQLField("WRBTR",		"cmp_transformbill",	"amount"),
				new SQLField("paystatus",	"cmp_transformbill",	"settlesatus"),	
				new SQLField("voucherflag",	"cmp_transformbill",	"vdef20"),
				new SQLField("pk_qryorg",	"cmp_transformbill",	"pk_org")
		};
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		return new SQLJoinClause[]{
				new SQLJoinClause("bd_bankaccbas_i",	"pk_bankaccbas","bd_bankaccsub_i",	"pk_bankaccbas"),
				new SQLJoinClause("bd_bankaccbas_o",	"pk_bankaccbas","bd_bankaccsub_o",	"pk_bankaccbas"),
				new SQLJoinClause("bd_bankdoc_i",		"pk_bankdoc",	"bd_bankaccbas_i",	"pk_bankdoc"),
				new SQLJoinClause("bd_bankdoc_o",		"pk_bankdoc",	"bd_bankaccbas_o",	"pk_bankdoc"),
				new SQLJoinClause("bd_bankaccsub_i",	"pk_bankaccsub","cmp_transformbill","transforminaccount"),
				new SQLJoinClause("bd_bankaccsub_o",	"pk_bankaccsub","cmp_transformbill","transformoutaccount"),
				new SQLJoinClause("org_orgs",			"pk_org",		"cmp_transformbill","pk_org"),
				new SQLJoinClause("bd_billtype",		"pk_billtypeid","cmp_transformbill","pk_billtypeid")
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
				new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "paystatus", OPERATOR.EQ, "3"),
		};
	}

}
