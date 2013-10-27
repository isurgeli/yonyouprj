package nc.itf.ztwzj.mdmitf;

import nc.vo.pfxx.xlog.XlogVO;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public enum NCDataConstant {
	PK_GROUP("0001A4100000000003FD"),
	PK_FORMAT("FMT0Z000000000000000"),
	PK_COUNTRY("0001Z010000000079UJJ"),
	PK_TIMEZONE("0001Z010000000079U2P"),

	PK_DOMESTICCUSTCLASS("1002A410000000000XU0"),
	PK_FOREIGNCUSTCLASS("1002A410000000000XU1"),
	PK_FINANCIALCUSTCLASS("1002A410000000000XU2"),
	PK_DOMESTICSUPPCLASS("1002A410000000000XU8"),
	PK_FOREIGNSUPPCLASS("1002A410000000000XU8"),
	PK_FINANCIALSUPPCLASS("1002A410000000000XUA");
	
	private String value;
	
	private NCDataConstant(String v){
		value = v;
	}
	
	public String getValue(){
		return value;
	}
	
	public static XlogVO getBaseXlogVO(String type, String billcode, UFBoolean correct, String data, String retmsg){
		XlogVO xlog = new XlogVO();
		xlog.setDoc_type(type);
		xlog.setDoc_id(billcode);			
		xlog.setCorrect(correct);
			
		xlog.setDoc_source("NCESB");
		xlog.setDoc_destination("ZTWZJITF");
		xlog.setDoc_time(new UFDateTime());
		data = data.trim();
		if (data.length()>500) 
			data=data.substring(0, 500);
		xlog.setDoc_dscpt(data);
		xlog.setBusibill(retmsg);
		
		return xlog;
	}
}
