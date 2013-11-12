package nc.impl.ztwzj.sapitf.voucher.tabledef.test;

import java.util.Hashtable;

import nc.impl.ztwzj.sapitf.voucher.tabledef.CmpPayTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.CmpRecTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfAlloTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfAlloTableHeadCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfDeliTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfDeliTableHeadCorpDef;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLBuilderTool;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;
import nc.vo.pub.BusinessException;
import junit.framework.TestCase;

public class SQLDefTest extends TestCase {
	public SQLDefTest(String name) {
		super(name);
	}
	
	public void testCmpRecTableDefST() throws BusinessException{
		SQLBuilderTool tool = new SQLBuilderTool(new CmpRecTableBodyCorpDef());
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		paras.put("BUKRSLEVEL", "'2'");
		paras.put("BUSITYPE", "'资金上收-收款公司'");
		paras.put("ABSTRACTS", "'收['");
		paras.put("ABSTRACTE", "']资金上划款'");
		SQLWhereClause[] flexWheres = new SQLWhereClause[] {
				new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "pk_qryorg", OPERATOR.EQ, DELIMITER.getParaExp("CORP")),
				new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "BUDAT", OPERATOR.EQ, DELIMITER.getParaExp("BUDATS")),
				new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "BUDAT", OPERATOR.EQ, DELIMITER.getParaExp("BUDATE")),
			};
		String ret = tool.buildSQL(new String[] { "VOUCHERID","BUKRS","BUKRSLEVEL","BUSITYPE","DOCUMENTSTYPE","DOCUMENTSNO",
				"ABSTRACT","BUDAT","CUSTCODE","CUSTNAME","WRBTR","EXBANK","EXBANKNUM","OPPEXBANK","OPPEXBANKNUM","OUTBANK",
				"OUTBANKNUM","INBANK","INBANKNUM" }, 
				flexWheres, paras);
		
		System.out.println(ret);
	}
	
	public void testCmpPayTableDefST() throws BusinessException{
		SQLBuilderTool tool = new SQLBuilderTool(new CmpPayTableBodyCorpDef());
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		paras.put("BUKRSLEVEL", "'2'");
		paras.put("BUSITYPE", "'资金下拨-收款公司'");
		paras.put("ABSTRACTS", "'支付['");
		paras.put("ABSTRACTE", "']下拨资金款'");

		String ret = tool.buildSQL(new String[] { "VOUCHERID","BUKRS","BUKRSLEVEL","BUSITYPE","DOCUMENTSTYPE","DOCUMENTSNO",
				"ABSTRACT","BUDAT","CUSTCODE","CUSTNAME","WRBTR","EXBANK","EXBANKNUM","OPPEXBANK","OPPEXBANKNUM","OUTBANK",
				"OUTBANKNUM","INBANK","INBANKNUM" }, 
				null, paras);
		
		System.out.println(ret);
	}
	
	public void testSfAlloTableDefST() throws BusinessException{
		SQLBuilderTool tool = new SQLBuilderTool(new SfAlloTableBodyCorpDef());
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		paras.put("BUKRSLEVEL", "'1'");
		paras.put("BUSITYPE", "'资金上收-收款公司'");
		paras.put("ABSTRACTS", "'收['");
		paras.put("ABSTRACTE", "']资金上划款'");

		String ret = tool.buildSQL(new String[] { "VOUCHERID","BUKRS","BUKRSLEVEL","BUSITYPE","DOCUMENTSTYPE","DOCUMENTSNO",
				"ABSTRACT","BUDAT","CUSTCODE","CUSTNAME","WRBTR","EXBANK","EXBANKNUM","OPPEXBANK","OPPEXBANKNUM","OUTBANK",
				"OUTBANKNUM","INBANK","INBANKNUM" }, 
				null, paras);
		
		System.out.println(ret);
	}
	
	public void testSfDeliTableDefST() throws BusinessException{
		SQLBuilderTool tool = new SQLBuilderTool(new SfDeliTableHeadCorpDef());
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		paras.put("BUKRSLEVEL", "'1'");
		paras.put("BUSITYPE", "'资金下拨-收款公司'");
		paras.put("ABSTRACTS", "'支付['");
		paras.put("ABSTRACTE", "']下拨资金款'");

		String ret = tool.buildSQL(new String[] { "VOUCHERID","BUKRS","BUKRSLEVEL","BUSITYPE","DOCUMENTSTYPE","DOCUMENTSNO",
				"ABSTRACT","BUDAT","CUSTCODE","CUSTNAME","WRBTR","EXBANK","EXBANKNUM","OPPEXBANK","OPPEXBANKNUM","OUTBANK",
				"OUTBANKNUM","INBANK","INBANKNUM" }, 
				null, paras);
		
		System.out.println(ret);
	}
}
	