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

public class CmpRecTableDef implements ISQLDefine {

	@Override
	public String getMianTable() {
		return "cmp_recbill";
	}

	@Override
	public SQLTable[] getSQLTables() {
		return new SQLTable[] {};
	}

	@Override
	public SQLField[] getSQLFields() {
		return new SQLField[] {
			new SQLField("BUKRS","org_orgs","decode(substr(org_orgs.code,5),'100099','1000',substr(org_orgs.code,5))"),
			new SQLField("ZYEAR","cmp_recbill","substr(cmp_recbill.paydate,1,4)"),
			new SQLField("ZDJLX",null,"'S'"),
			new SQLField("ZSKFS","cmp_recbilldetail","decode(cmp_recbilldetail.pk_balatype,'0001Z0100000000000Y5','4','0001Z0100000000000Y6','3','2')"),
			new SQLField("ZLSCH","cmp_recbilldetail","decode(cmp_recbilldetail.pk_balatype,'0001Z0100000000000Y5','B','0001Z0100000000000Y6','A','D')"),
			new SQLField("ZSKRQ","cmp_recbill","REPLACE(substr(cmp_recbill.paydate,1,10),'-','')"),
			new SQLField("BLDAT","cmp_recbill","REPLACE(substr(cmp_recbill.paydate,1,10),'-','')"),
			new SQLField("WAERS","bd_currtype","bd_currtype.code"),
			new SQLField("ZSKJE","cmp_recbilldetail","cmp_recbilldetail.rec_primal"),
			new SQLField("KUNNR","bd_customer","'00'||bd_customer.code"),
			new SQLField("STATU",null,"'A'"),
			new SQLField("ZKS_BANKN","bd_bankaccsub","bd_bankaccsub.accnum"),
			new SQLField("ZKS_BANMS","bd_bankdoc","bd_bankdoc.name"),
			new SQLField("BKTXT","bd_customer","'ÊÕ'||bd_customer.name||'»õ¿î'"),
			new SQLField("NUMPG",null,"'001'"),
			new SQLField("REMARK","cmp_recbilldetail","cmp_recbilldetail.memo"),
			new SQLField("RECEIVE_NUM","cmp_recbill","cmp_recbill.bill_no"),
			new SQLField("RECBID","cmp_recbilldetail","cmp_recbilldetail.pk_recbill_detail"),
			new SQLField("trade_type","cmp_recbill","trade_type"),
			new SQLField("sapflag",	"cmp_recbilldetail","def20"),
		};
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		return new SQLJoinClause[] {
			new SQLJoinClause("cmp_recbilldetail","pk_recbill","cmp_recbill","pk_recbill"),
			new SQLJoinClause("bd_bankaccsub","pk_bankaccsub","cmp_recbilldetail","pk_oppaccount"),
			new SQLJoinClause("bd_bankaccbas","pk_bankaccbas","bd_bankaccsub","pk_bankaccbas"),
			new SQLJoinClause("bd_bankdoc","pk_bankdoc","bd_bankaccbas","pk_bankdoc"),
			new SQLJoinClause("bd_customer","pk_customer","cmp_recbilldetail","pk_customer"),
			new SQLJoinClause("org_orgs","pk_org","cmp_recbill","pk_org"),
			new SQLJoinClause("bd_currtype","pk_currtype","cmp_recbilldetail","pk_currtype"), 	
		};
	}

	@Override
	public SQLOrderbyClause[] getOrderbys() {
		return new SQLOrderbyClause[] {
			new SQLOrderbyClause("ZSKRQ", ORDER.ASC)
		};
	}

	@Override
	public SQLWhereClause[] getFixWheres() {
		return new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "trade_type", OPERATOR.IN, "('D4','F4-Cxx-03')"),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "sapflag", OPERATOR.IN, "('~','D')")
		};
	}
}
