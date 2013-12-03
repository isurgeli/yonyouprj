package nc.impl.ztwzj.sapitf.bill.tabledef;

import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.ISQLDefine;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.ORDER;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;
import nc.itf.lxt.pub.sqltool.SQLOrderbyClause;
import nc.itf.lxt.pub.sqltool.SQLTable;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;

public class BankReceipTableDef implements ISQLDefine {

	@Override
	public String getMianTable() {
		return "ebank_dzd";
	}

	@Override
	public SQLTable[] getSQLTables() {
		return new SQLTable[] {};
	}

	@Override
	public SQLField[] getSQLFields() {
		return new SQLField[] {
			new SQLField("BUDAT","ebank_dzd","REPLACE(substr(ebank_dzd.trans_date,1,10),'-','')"),
			new SQLField("ZHB_BANMS","bd_banktype","bd_banktype.name"),
			new SQLField("ZHB_BANKN","ebank_dzd","curacc"),
			new SQLField("BUKRS","org_orgs","decode(substr(org_orgs.code,5),'100099','0001',substr(org_orgs.code,5))"),
			new SQLField("SHKZG","ebank_dzd","CASE WHEN ebank_dzd.dbtacc=ebank_dzd.curacc THEN 'H' ELSE 'S' END"),
			new SQLField("WRBTR","ebank_dzd","ebank_dzd.trsamt"),
			new SQLField("UNITN","ebank_dzd","ebank_dzd.oppname"),
			new SQLField("EXNUM","ebank_dzd","ebank_dzd.pk_ebank_dzd"),
			new SQLField("USEAGE","ebank_dzd","ebank_dzd.trans_abstr"),
			new SQLField("time","ebank_dzd","ebank_dzd.trans_time"),
			new SQLField("SAPFLAG","ebank_dzd","ebank_dzd.obmdef5")
		};
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		return new SQLJoinClause[] {
			new SQLJoinClause("bd_banktype","code","ebank_dzd","banktypecode"),
			new SQLJoinClause("org_orgs","pk_org","ebank_dzd","pk_org")
		};
	}

	@Override
	public SQLOrderbyClause[] getOrderbys() {
		return new SQLOrderbyClause[] {
			new SQLOrderbyClause("BUDAT", ORDER.ASC),
			new SQLOrderbyClause("time", ORDER.ASC)
		};
	}

	@Override
	public SQLWhereClause[] getFixWheres() {
		return new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "SAPFLAG", OPERATOR.EQ, "'~'")
		};
	}

}
