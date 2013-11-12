package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;


public abstract class CmpRecTableDef extends CmpBaseTableDef {

	@Override
	public String getMianTable() {
		return "cmp_recbill";
	}

	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("VOUCHERID",	"cmp_recbilldetail",	"pk_recbill_detail"),
				new SQLField("DOCUMENTSNO",	"cmp_recbill",			"bill_no"),
				new SQLField("BUDAT",		"cmp_recbill",			"paydate"),
				new SQLField("WRBTR",		"cmp_recbilldetail",	"rec_primal"),
				new SQLField("paystatus",	"cmp_recbill",			"paystatus"),
				new SQLField("pk_org",		"cmp_recbill",			"pk_org")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), cur);
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		SQLJoinClause[] cur = new SQLJoinClause[]{
			new SQLJoinClause("cmp_recbilldetail",	"pk_recbill",	"cmp_recbill",		"pk_recbill"),
			new SQLJoinClause("bd_bankaccsub_h",	"pk_bankaccsub","cmp_recbilldetail","pk_account"),
			new SQLJoinClause("bd_bankaccsub_b",	"pk_bankaccsub","cmp_recbilldetail","pk_oppaccount"),
			new SQLJoinClause("bd_customer",		"pk_customer",	"cmp_recbilldetail","pk_customer"),
			new SQLJoinClause("org_orgs_b",			"pk_org",		"bd_customer",		"pk_financeorg"),
			new SQLJoinClause("org_orgs_h",			"pk_org",		"cmp_recbill",		"pk_org"),
			new SQLJoinClause("bd_billtype",		"pk_billtypeid","cmp_recbill",		"pk_billtypeid")
		};
		
		return SetUtils.concatAll(super.getTableJoins(), cur);
	}
}
