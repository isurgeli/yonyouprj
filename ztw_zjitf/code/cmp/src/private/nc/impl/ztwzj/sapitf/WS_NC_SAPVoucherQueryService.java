package nc.impl.ztwzj.sapitf;

import javax.xml.bind.JAXBException;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.lxt.pub.jxabtool.JaxbTools;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.itf.ztwzj.sapitf.IVoucherService;
import nc.itf.ztwzj.sapitf.IWS_NC_SAPVoucherQueryService;
import nc.vo.pfxx.util.PfxxUtils;
import nc.vo.pfxx.xlog.XlogVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.ztwzj.sapitf.voucher.BillInfo.BillInfoList;

public class WS_NC_SAPVoucherQueryService implements
		IWS_NC_SAPVoucherQueryService {

	@Override
	public String qryVoucherBillInfo(String para) {
		IVoucherService bp = NCLocator.getInstance().lookup(IVoucherService.class);
		String ret = null;
		UFBoolean correct = null;
		try {
			InvocationInfoProxy.getInstance().setUserDataSource(NCDataConstant.getDataSource());
			ret = bp.qryTMVoucherBillInfoXml(para);
			correct  = new UFBoolean(true);
		} catch (BusinessException e) {
			BillInfoList infoList = new BillInfoList();
			infoList.setSuccess("1");
			infoList.setErrcode(e.getErrorCodeString());
			infoList.setMessage(e.getMessage());
			
			try {
				ret = JaxbTools.getStringFromObject(infoList);
			} catch (JAXBException e1) {
			}
			correct = new UFBoolean(false);
		} finally {
			XlogVO xlog = NCDataConstant.getBaseXlogVO("VOUBILLQRY", "", correct, para, ret);
			try {
				PfxxUtils.lookUpPFxxEJBService().writeLogs_RequiresNew(new XlogVO[]{xlog});
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
		
		return ret;
	}

	@Override
	public String pushVoucherResultInfo(String info) {
		return "Hello one";
	}

}
