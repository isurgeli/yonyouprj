package nc.vo.ztwzj.sapitf.voucher;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import nc.bs.framework.common.RuntimeEnv;
import nc.bs.logging.Logger;
import nc.vo.pfxx.xlog.XlogVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDateTime;

public enum ZtwVoucherConstant {
	PK_MONEYCENTER("0001A510000000000KW6"),
	BT_DELIHEADCORP("�ʽ�����-�տ˾"), //һ�� "sf_delivery_b"."vuserdef9" ���� "cmp_recbilldetail"."def20"
	BT_DELIBODYCORP("�ʽ�����-�ɿ˾"), //һ�� "sf_deliveryreceipt"."vueserdef9" ���� "cmp_recbilldetail"."def19"
	BT_ALLOHEADCORP("�ʽ��²�-���˾"), //һ�� "sf_allocate_b"."vuserdef9" ���� "cmp_paybilldetail"."def20"
	BT_ALLOBODYCORP("�ʽ��²�-�տ˾"), //һ�� "sf_allocatereceipt"."vueserdef9" ���� "cmp_paybilldetail"."def19"
	BT_ADJU("���е���"),
	BTID("BUSITYPE");
	
	private String value;
	
	private ZtwVoucherConstant(String v){
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
	
	public static String getDataSource() throws BusinessException{
		return getProperty("datasrc");
	}
	
	public static String getSAPPayBillWSUrl() throws BusinessException{
		return getProperty("pbwsurl");
	}
	
	public static String getSAPPayBillWSUser() throws BusinessException{
		return getProperty("pbwsuser");
	}
	
	public static String getSAPPayBillWSPass() throws BusinessException{
		return getProperty("pbwspass");
	}
	
	public static String getProperty(String key) throws BusinessException{
		Properties prop = new Properties();
		 
    	try {
    		String path = RuntimeEnv.getInstance().getNCHome()+File.separatorChar+"resources"+File.separatorChar+"ztwitf.properties";
    		prop.load(new FileInputStream(path));
    		if (prop.getProperty(key) == null)
    			return null;
    		String property = prop.getProperty(key).toString();
    		if (property != null && property.length()==0)
    			throw new BusinessException("����["+key+"]���ò���ȷ", "S01");
    		
    		return property;
    	} catch (IOException ex) {
    		Logger.error(ex.getMessage(),ex);
			throw new BusinessException("����["+key+"]���ò���ȷ", "S01");
        }
	}
}
