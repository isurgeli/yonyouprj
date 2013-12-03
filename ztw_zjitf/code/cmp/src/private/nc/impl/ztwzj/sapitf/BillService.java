package nc.impl.ztwzj.sapitf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import javax.xml.bind.JAXBException;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.impl.ztwzj.sapitf.bill.tabledef.CmpRecTableDef;
import nc.itf.lxt.pub.jxabtool.JaxbTools;
import nc.itf.lxt.pub.set.SetUtils;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLBuilderTool;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;
import nc.itf.ztwzj.sapitf.IBillService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.vo.lxt.pub.WSTool;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.ztwzj.sapitf.bill.RecvBillInfo.RecvBillQryRet;
import nc.vo.ztwzj.sapitf.bill.RecvBillInfo.RecvBillQryRet.SystemInfo;
import nc.vo.ztwzj.sapitf.bill.RecvBillQry.RecvBillQryPara;
import nc.vo.ztwzj.sapitf.bill.RecvBillQry.RecvBillQryPara.SAPRangePara;
import nc.vo.ztwzj.sapitf.bill.RecvBillSPFlag.RecvBillSPFlag;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjk.ZfiFkdjk;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjk.ZfiFkdjk.ITab;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse.OTab;
import nc.vo.ztwzj.sapitf.voucher.ZtwVoucherConstant;

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
	public void setRecvBillSHFlag(String info) throws BusinessException {
		try {
			RecvBillSPFlag flagObj = JaxbTools.getObjectFromString(RecvBillSPFlag.class, info);
		
			for (int i=0;i<flagObj.getItem().getRECBID().size();i++)
				flagObj.getItem().getRECBID().set(i, DELIMITER.getStringParaValue(flagObj.getItem().getRECBID().get(i)));
			
			@SuppressWarnings("unchecked")
			String sql = "update cmp_recbilldetail set def20='"+flagObj.getSTATUS()+"' where pk_recbill_detail in ("+SetUtils.concatString(',', flagObj.getItem().getRECBID())+")";
			
			BaseDAO dao = new BaseDAO();
			dao.executeUpdate(sql);
		}catch(JAXBException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	@Override
	public String qryBankReceiptInfo(String para) {
		return null;
	}

	@Override
	public List<OTab.Item> qryPayBillInfo(String para) throws BusinessException {
		try {
			String xmlret = callSAPWS(para);
			ZfiFkdjkResponse ret = JaxbTools.getObjectFromString(ZfiFkdjkResponse.class, xmlret);
			if (!ret.getOResult().equals("S"))
				throw new BusinessException("WS请求返回错误。");
			SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
			for (OTab.Item item : ret.getOTab().getItem()) {
				String date = item.getAedat();
				item.setAedat(UFDate.getDate(sFormat.parse(date)).toString());
				item.setVstatus("未处理");
				item.setStatu("S");
			}
			return ret.getOTab().getItem();
		}catch(JAXBException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		} catch (ParseException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	private String callSAPWS(String xml) throws BusinessException {
		xml = xml.substring(xml.indexOf('<', 1), xml.length());
		xml = xml.replaceAll("ZfiFkdjk", "urn:ZfiFkdjk");
		StringBuffer soap = new StringBuffer();
		soap.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:sap-com:document:sap:soap:functions:mc-style\">");
		soap.append("   <soapenv:Header/>");
		soap.append("   <soapenv:Body>");
		soap.append(xml);
		soap.append("   </soapenv:Body>");
		soap.append("</soapenv:Envelope>");

		String url = ZtwVoucherConstant.getSAPPayBillWSUrl();
		String soapResponseData = WSTool.callByHttp(url, soap.toString());
		int sx = soapResponseData.indexOf("<urn:ZfiFkdjkResponse>");
		int ex = soapResponseData.indexOf("</urn:ZfiFkdjkResponse>") + "</urn:ZfiFkdjkResponse>".length();
		soapResponseData = soapResponseData.substring(sx, ex);
		soapResponseData = soapResponseData.replaceAll("urn:ZfiFkdjkResponse", "ZfiFkdjkResponse");
		return soapResponseData;
	}

	@Override
	public void setPayBillNCFlag(String statu, List<String> payNos)	throws BusinessException {
		ZfiFkdjk zfi = new ZfiFkdjk();
		zfi.setIStatus(statu);
		zfi.setITab(new ZfiFkdjk.ITab());
		for (String payNo : payNos) {
			ITab.Item item = new ITab.Item();
			item.setPrepayReqNum(payNo);
			zfi.getITab().getItem().add(item);
		}
		
		try {
			String setXml = JaxbTools.getStringFromObject(zfi);
			String xmlret = callSAPWS(setXml);
			ZfiFkdjkResponse ret = JaxbTools.getObjectFromString(ZfiFkdjkResponse.class, xmlret);
			if (!ret.getOResult().equals("S"))
				throw new BusinessException("WS请求返回错误。");
		} catch (JAXBException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}
}
