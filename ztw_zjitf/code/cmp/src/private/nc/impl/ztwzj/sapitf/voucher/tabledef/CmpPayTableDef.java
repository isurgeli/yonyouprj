package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;

public abstract class CmpPayTableDef extends CmpBaseTableDef {

	@Override
	public String getMianTable() {
		return "cmp_paybill";
	}

	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("VOUCHERID",	"cmp_paybilldetail",	"pk_paybill_detail"),
				new SQLField("DOCUMENTSNO",	"cmp_paybill",			"bill_no"),
				new SQLField("BUDAT",		"cmp_paybill",			"REPLACE(substr(cmp_paybill.paydate,1,10),'-','')"),
				new SQLField("WRBTR",		"cmp_paybilldetail",	"pay_primal"),
				new SQLField("paystatus",	"cmp_paybill",			"paystatus"),
				new SQLField("pk_org",		"cmp_paybill",			"pk_org"),
				new SQLField("trade_type",	"cmp_paybill",			"trade_type")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), cur);
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		SQLJoinClause[] cur = new SQLJoinClause[]{
			new SQLJoinClause("cmp_paybilldetail",	"pk_paybill",	"cmp_paybill",		"pk_paybill"),
			new SQLJoinClause("bd_bankaccsub_h",	"pk_bankaccsub","cmp_paybilldetail","pk_account"),
			new SQLJoinClause("bd_bankaccsub_b",	"pk_bankaccsub","cmp_paybilldetail","pk_oppaccount"),
			new SQLJoinClause("bd_supplier",		"pk_supplier",	"cmp_paybilldetail","pk_supplier"),
			new SQLJoinClause("org_orgs_b",			"pk_org",		"bd_supplier",		"pk_financeorg"),
			new SQLJoinClause("org_orgs_h",			"pk_org",		"cmp_paybill",		"pk_org"),
			new SQLJoinClause("bd_billtype",		"pk_billtypeid","cmp_paybill",		"pk_billtypeid")
		};
		
		return SetUtils.concatAll(super.getTableJoins(), cur);
	}
	
	@Override
	public SQLWhereClause[] getFixWheres() {
		SQLWhereClause[] cur = new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "trade_type", OPERATOR.IN, "('F5-Cxx-01', 'F5-Cxx-04')")
		};
		
		return SetUtils.concatAll(super.getFixWheres(), cur);
	}
}
