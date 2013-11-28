package nc.impl.ztwzj.sapitf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import javax.xml.bind.JAXBException;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.impl.ztwzj.sapitf.bill.tabledef.CmpRecTableDef;
import nc.itf.lxt.pub.jxabtool.JaxbTools;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLBuilderTool;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;
import nc.itf.ztwzj.sapitf.IBillService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.ztwzj.sapitf.bill.RecvBillInfo.RecvBillQryRet;
import nc.vo.ztwzj.sapitf.bill.RecvBillInfo.RecvBillQryRet.SystemInfo;
import nc.vo.ztwzj.sapitf.bill.RecvBillQry.RecvBillQryPara;
import nc.vo.ztwzj.sapitf.bill.RecvBillQry.RecvBillQryPara.SAPRangePara;

public class BillService implements IBillService {

	@Override
	public String qryRecvBillInfo(String para) throws BusinessException {
		try {
			RecvBillQryPara paraObj = JaxbTools.getObjectFromString(RecvBillQryPara.class, para);
			
			String sql = getQryRecvBillSQl(paraObj);
			
			BaseDAO dao = new BaseDAO();
			@SuppressWarnings("unchecked")
			ArrayList<RecvBillQryRet.RecvBillList.RecvBillInfo> infos = 
				(ArrayList<RecvBillQryRet.RecvBillList.RecvBillInfo>)dao.executeQuery(sql, 
				new BeanListProcessor(RecvBillQryRet.RecvBillList.RecvBillInfo.class));
			RecvBillQryRet infoList = new RecvBillQryRet();
			infoList.setSystemInfo(new SystemInfo());
			infoList.getSystemInfo().setSuccess("1");
			infoList.getSystemInfo().setErrcode("");
			infoList.getSystemInfo().setMessage("");
			infoList.setRecvBillList(new RecvBillQryRet.RecvBillList());
			for(RecvBillQryRet.RecvBillList.RecvBillInfo info : infos)
				infoList.getRecvBillList().getRecvBillInfo().add(info);
			
			String ret = JaxbTools.getStringFromObject(infoList);
			
			return ret;
		} catch (JAXBException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	private String getQryRecvBillSQl(RecvBillQryPara paraObj) throws BusinessException {
		SQLBuilderTool st = null;
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		st = new SQLBuilderTool(new CmpRecTableDef());
		
		ArrayList<SQLWhereClause> flexWheres = new ArrayList<SQLWhereClause>();
		if (paraObj.getBUKRS() == null || paraObj.getBUKRS().length() == 0)
			throw new BusinessException("参数错误 没有公司编码", "S1001");
		flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "BUKRS", OPERATOR.EQ, DELIMITER.getParaExp("BUKRS")));
		paras.put("BUKRS", DELIMITER.getStringParaValue(paraObj.getBUKRS()));
		
		if (paraObj.getZSKRQS() != null &&  paraObj.getZSKRQS().getZSKRQ().size() > 0) {
			flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.LEFT, "1", OPERATOR.EQ, "1"));
			for (SAPRangePara sapPara : paraObj.getZSKRQS().getZSKRQ()) {
				//sapPara.transferDateValue();
				flexWheres.addAll(sapPara.getSQLWhere("ZSKRQ"));
			}
			flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.RIGHT, "1", OPERATOR.EQ, "1"));
		}
		
		if (paraObj.getRECEIVENUMS() != null &&  paraObj.getRECEIVENUMS().getRECEIVENUM().size() > 0) {
			flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.LEFT, "1", OPERATOR.EQ, "1"));
			for (SAPRangePara sapPara : paraObj.getRECEIVENUMS().getRECEIVENUM()) {
				flexWheres.addAll(sapPara.getSQLWhere("RECEIVE_NUM"));
			}
			flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.RIGHT, "1", OPERATOR.EQ, "1"));
		}
		
		if (paraObj.getKUNNRS() != null &&  paraObj.getKUNNRS().getKUNNR().size() > 0) {
			flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.LEFT, "1", OPERATOR.EQ, "1"));
			for (SAPRangePara sapPara : paraObj.getKUNNRS().getKUNNR()) {
				flexWheres.addAll(sapPara.getSQLWhere("KUNNR"));
			}
			flexWheres.add(new SQLWhereClause(OPERATOR.AND, BRACKET.RIGHT, "1", OPERATOR.EQ, "1"));
		}
		
		
		ArrayList<String> fields = new ArrayList<String>();
		fields.addAll(Arrays.asList(new String[] { "BUKRS",	"ZYEAR","ZDJLX","ZSKFS","ZLSCH","ZSKRQ","BLDAT","WAERS","ZSKJE","KUNNR","STATU","ZKS_BANKN",
				"ZKS_BANMS","BKTXT","NUMPG","REMARK","RECEIVE_NUM", "RECBID"}));
		
		return st.buildSQL(fields.toArray(new String[] {}), flexWheres.toArray(new SQLWhereClause[] {}), paras);
	}

	@Override
	public String setRecvBillSHFlag(String info) {
		return null;
	}

	@Override
	public String qryBankReceiptInfo(String para) {
		return null;
	}

}
