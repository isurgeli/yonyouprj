package nc.impl.ztwzj.mdmitf;

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
			if (mdtype.equals("Customer")){
				String code = getXMLValue(data, "custNo");
				String name = getXMLValue(data, "custName");
				boolean isFinancial = getXMLValue(data, "isFinancial").toUpperCase().equals("TRUE");
				
				checkCodeName(code, name);
				
				billcode = code;
				CustomerProcessor custP = new CustomerProcessor();
				custP.processCustomer(code, name, isFinancial);
			} else if (mdtype.equals("Vendor")){
				String code = getXMLValue(data, "vendNo");
				String name = getXMLValue(data, "vendName");
				boolean isFinancial = getXMLValue(data, "isFinancial").toUpperCase().equals("TRUE");
				
				checkCodeName(code, name);
				
				billcode = code;
				SupplierProcessor suppP = new SupplierProcessor();
				suppP.processSupplier(code, name, isFinancial);
			} else {
				throw new BusinessException("C01", "δ֪�Ŀ�������");
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
			throw new BusinessException("C02", "����Ϊ��");
		if (name == null || name.length()==0)
			throw new BusinessException("C03", "����Ϊ��");
	}

	private String getXMLValue(String xml, String sec){
		int s = xml.indexOf("<"+sec+">");
		if (s==-1) return "";
		s+=sec.length()+2;
		int e = xml.indexOf("<", s);
		if (e==-1) return "";
		
		return xml.substring(s, e);
	}
}
