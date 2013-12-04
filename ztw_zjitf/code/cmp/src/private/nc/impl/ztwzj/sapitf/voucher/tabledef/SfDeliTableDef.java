package nc.impl.ztwzj.sapitf.voucher.tabledef;

import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLField;
import nc.itf.lxt.pub.sqltool.SQLJoinClause;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;

public abstract class SfDeliTableDef extends SfBaseTableDef {

	@Override
	public String getMianTable() {
		return "sf_delivery_h"; //…œ ’µ•
	}

	@Override
	public SQLField[] getSQLFields() {
		SQLField[] cur = new SQLField[] {
				new SQLField("VOUCHERID",	"sf_delivery_b",			"pk_delivery_b"),
				new SQLField("DOCUMENTSNO",	"sf_delivery_h",			"vbillno"),
				new SQLField("BUDAT",		"sf_delivery_h",			"REPLACE(substr(sf_delivery_h.dapprovedate,1,10),'-','')"),
				new SQLField("WRBTR",		"sf_delivery_b",			"amount"),
				new SQLField("paystatus",	"sf_delivery_h",			"billstatus")
		};
		
		return SetUtils.concatAll(super.getSQLFields(), cur);
	}

	@Override
	public SQLJoinClause[] getTableJoins() {
		SQLJoinClause[] cur = new SQLJoinClause[]{
			new SQLJoinClause("sf_delivery_b",		"pk_delivery_h","sf_delivery_h",	"pk_delivery_h"),
			new SQLJoinClause("bd_bankaccsub_h",	"pk_bankaccsub","sf_delivery_b",	"pk_bankacc_r"),
			new SQLJoinClause("bd_bankaccsub_b",	"pk_bankaccsub","sf_delivery_b",	"pk_bankacc_p"),
			new SQLJoinClause("org_orgs_b",			"pk_org",		"sf_delivery_b",	"pk_org_p"),
			new SQLJoinClause("org_orgs_h",			"pk_org",		"sf_delivery_h",	"pk_org"),
			new SQLJoinClause("bd_billtype",		"pk_billtypeid","sf_delivery_h",	"pk_billtypeid"),
			new SQLJoinClause("sf_deliveryreceipt",	"pk_srcbill_b",	"sf_delivery_b",	"pk_delivery_b")
		};
		
		return SetUtils.concatAll(super.getTableJoins(), cur);
	}
	
	@Override
	public SQLWhereClause[] getFixWheres() {
		return new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "paystatus", OPERATOR.EQ, "4"),
		};
	}
}
