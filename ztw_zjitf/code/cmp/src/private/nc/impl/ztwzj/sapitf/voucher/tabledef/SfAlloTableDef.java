package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;

public abstract class SfAlloTableDef extends SfBaseTableDef {

	@Override
	public String getMianTable() {
		return "sf_allocate_h"; //ÏÂ²¦µ¥
	}

	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("VOUCHERID",	"sf_allocate_b",			"pk_allocate_b"),
				new SQLField("DOCUMENTSNO",	"sf_allocate_h",			"vbillno"),
				new SQLField("BUDAT",		"sf_allocate_h",			"dapprovedate"),
				new SQLField("WRBTR",		"sf_allocate_b",			"amount"),
				new SQLField("paystatus",	"sf_allocate_h",			"billstatus")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), cur);
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		SQLJoinClause[] cur = new SQLJoinClause[]{
			new SQLJoinClause("sf_allocate_b",		"pk_allocate_h","sf_allocate_h",	"pk_allocate_h"),
			new SQLJoinClause("bd_bankaccsub_h",	"pk_bankaccsub","sf_allocate_b",	"pk_bankacc_p"),
			new SQLJoinClause("bd_bankaccsub_b",	"pk_bankaccsub","sf_allocate_b",	"pk_bankacc_r"),
			new SQLJoinClause("org_orgs_b",			"pk_org",		"sf_allocate_b",	"pk_org_r"),
			new SQLJoinClause("org_orgs_h",			"pk_org",		"sf_allocate_h",	"pk_org"),
			new SQLJoinClause("bd_billtype",		"pk_billtypeid","sf_allocate_h",	"pk_billtypeid"),
			new SQLJoinClause("sf_allocatereceipt",	"pk_srcbill_b",	"sf_allocate_b",	"pk_allocate_b")
		};
		
		return SetUtils.concatAll(super.getTableJoins(), cur);
	}
	
	@Override
	public SQLWhereClause[] getFixWheres() {
		return new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "paystatus", OPERATOR.EQ, "5"),
		};
	}
}
