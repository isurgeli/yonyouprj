package nc.vo.ztwzj.pub;

import nc.vo.pub.BusinessException;

public class ZtwBusinessException extends BusinessException {
	private static final long serialVersionUID = 1L;
	
	private static String SUCCESS = "1";
	private static String ERROR = "0";
	private static String CUSTSUPPTYPE = "RecvPushMdInfoRtn";
		
	public static String getCustSuppErrorXMLString(BusinessException e){
		StringBuffer sb = getRetXMLString(CUSTSUPPTYPE, ERROR, e.getErrorCodeString(), e.getMessage());
		return sb.toString();
	}
	
	public static String getCustSuppErrorXMLString(String code , String msg){
		StringBuffer sb = getRetXMLString(CUSTSUPPTYPE, ERROR, code, msg);
		return sb.toString();
	}
	
	public static String getCustSuppSuccessXMLString(){
		StringBuffer sb = getRetXMLString(CUSTSUPPTYPE, SUCCESS, "", "");
		return sb.toString();
	}
	
	private static StringBuffer getRetXMLString(String type, String ret, String code, String msg){
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<"+type+">");
		sb.append("<success>"+ret+"</success>");
		sb.append("<errcode>"+code+"</errcode>");
		sb.append("<message>"+msg+"</message>");
		sb.append("</"+type+">");
		
		return sb;
	}
}
