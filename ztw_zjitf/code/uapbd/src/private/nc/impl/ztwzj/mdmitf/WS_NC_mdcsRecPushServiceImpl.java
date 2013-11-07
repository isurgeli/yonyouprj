package nc.impl.ztwzj.mdmitf;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.logging.Logger;
import nc.bs.ztwzj.cust_supp.CustomerProcessor;
import nc.bs.ztwzj.cust_supp.SupplierProcessor;
import nc.itf.ztwzj.mdmitf.IWS_NC_mdcsRecPushService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.pfxx.util.PfxxUtils;
import nc.vo.pfxx.xlog.XlogVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.ztwzj.pub.ZtwBusinessException;

public class WS_NC_mdcsRecPushServiceImpl implements IWS_NC_mdcsRecPushService {

	@Override
	public String recvPushMdcsInfo(String mdtype, String data) {
		String retmsg = null;
		String billcode = null;
		UFBoolean correct = null;
		
		try	{
			InvocationInfoProxy.getInstance().setUserDataSource(NCDataConstant.getDataSource());
			InvocationInfoProxy.getInstance().setGroupId(NCDataConstant.PK_GROUP.getValue());
			InvocationInfoProxy.getInstance().setUserId(NCDataConstant.PK_SYSUSER.getValue());
			
			if (mdtype.equals("Customer")){
				String code = getXMLValue(data, "custNo"); 
				String name = getXMLValue(data, "custName");
				boolean isFinancial = getXMLValue(data, "isFinancial").toUpperCase().equals("TRUE");
				
				checkCodeName(code, name);
				
				billcode = "客户"+code;
				CustomerProcessor custP = new CustomerProcessor();
				String flag = custP.processCustomer(code, name, isFinancial);
				billcode += flag;
			} else if (mdtype.equals("Vendor")){
				String code = getXMLValue(data, "vendNo");
				String name = getXMLValue(data, "vendName");
				boolean isFinancial = getXMLValue(data, "isFinancial").toUpperCase().equals("TRUE");
				
				checkCodeName(code, name);
				
				billcode = "供应商"+code;
				SupplierProcessor suppP = new SupplierProcessor();
				String flag = suppP.processSupplier(code, name, isFinancial);
				billcode += flag;
			} else {
				throw new BusinessException("未知的客商类型", "C01");
			}
			
			retmsg = ZtwBusinessException.getCustSuppSuccessXMLString();
			correct = new UFBoolean(true);
		}catch (BusinessException e) {
			retmsg = ZtwBusinessException.getCustSuppErrorXMLString(e);
			correct = new UFBoolean(false);
		}finally {
			XlogVO xlog = NCDataConstant.getBaseXlogVO("CUSTSUPP", billcode, correct, data, retmsg);
			try {
				PfxxUtils.lookUpPFxxEJBService().writeLogs_RequiresNew(new XlogVO[]{xlog});
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
			        
		return retmsg;
	}

	private void checkCodeName(String code, String name)
			throws BusinessException {
		if (code == null || code.length()==0)
			throw new BusinessException("编码为空", "C02");
		if (name == null || name.length()==0)
			throw new BusinessException("名称为空", "C03");
	}

	private String getXMLValue(String xml, String sec){
		int s = xml.indexOf("<"+sec+">");
		if (s==-1) return "";
		s+=sec.length()+2;
		int e = xml.indexOf("</"+sec+">", s);
		if (e==-1) return "";
		
		String ret = xml.substring(s, e);
		if (ret.toUpperCase().indexOf("<![CDATA[") != -1){
			ret = ret.substring(9, ret.length()-3);
		}
		
		return ret;
	}
}
