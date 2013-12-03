package nc.impl.ztwzj.sapitf;

import javax.xml.bind.JAXBException;

import nc.bs.framework.common.InvocationInfoProxy;
import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.lxt.pub.jxabtool.JaxbTools;
import nc.itf.ztwzj.sapitf.IBillService;
import nc.itf.ztwzj.sapitf.IWS_NC_SAPBill;
import nc.vo.pfxx.util.PfxxUtils;
import nc.vo.pfxx.xlog.XlogVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.ztwzj.sapitf.bill.BankReceiptInfo.BankReceiptQryRet;
import nc.vo.ztwzj.sapitf.bill.RecvBillInfo.RecvBillQryRet;
import nc.vo.ztwzj.sapitf.bill.RecvBillInfo.RecvBillQryRet.SystemInfo;
import nc.vo.ztwzj.sapitf.voucher.ZtwVoucherConstant;

public class WS_NC_SAPBill implements IWS_NC_SAPBill {

	@Override
	public String qryRecvBillInfo(String para) {
		IBillService bp = NCLocator.getInstance().lookup(IBillService.class);
		String ret = null;
		UFBoolean correct = null;
		try {
			if (para == null || para.length() == 0) {
				para = "null";
				throw new BusinessException("参数为空", "S01");
			}
			InvocationInfoProxy.getInstance().setUserDataSource(ZtwVoucherConstant.getDataSource());
			ret = bp.qryRecvBillInfo(para);
			correct  = new UFBoolean(true);
		} catch (BusinessException e) {
			RecvBillQryRet infoList = new RecvBillQryRet();
			infoList.setSystemInfo(new SystemInfo());
			infoList.getSystemInfo().setSuccess("0");
			infoList.getSystemInfo().setErrcode(e.getErrorCodeString());
			infoList.getSystemInfo().setMessage(e.getMessage());
			
			try {
				ret = JaxbTools.getStringFromObject(infoList);
			} catch (JAXBException e1) {
			}
			correct = new UFBoolean(false);
		} finally {
			XlogVO xlog = ZtwVoucherConstant.getBaseXlogVO("RECBILLQRY", "", correct, para, ret);
			try {
				PfxxUtils.lookUpPFxxEJBService().writeLogs_RequiresNew(new XlogVO[]{xlog});
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
		
		return ret;
	}

	@Override
	public String setRecvBillSHFlag(String info) {
		IBillService bp = NCLocator.getInstance().lookup(IBillService.class);
		String ret = null;
		UFBoolean correct = null;
		try {
			if (info == null || info.length() == 0) {
				info = "null";
				throw new BusinessException("参数为空", "S01");
			}
			InvocationInfoProxy.getInstance().setUserDataSource(ZtwVoucherConstant.getDataSource());
			bp.setRecvBillSHFlag(info);
			correct  = new UFBoolean(true);
			RecvBillQryRet infoList = new RecvBillQryRet();
			infoList.setSystemInfo(new SystemInfo());
			infoList.getSystemInfo().setSuccess("1");
			infoList.getSystemInfo().setErrcode("");
			infoList.getSystemInfo().setMessage("");
	
			try {
				ret = JaxbTools.getStringFromObject(infoList);
			} catch (JAXBException e1) {
			}
		} catch (BusinessException e) {
			RecvBillQryRet infoList = new RecvBillQryRet();
			infoList.setSystemInfo(new SystemInfo());
			infoList.getSystemInfo().setSuccess("0");
			infoList.getSystemInfo().setErrcode(e.getErrorCodeString());
			infoList.getSystemInfo().setMessage(e.getMessage());
			
			try {
				ret = JaxbTools.getStringFromObject(infoList);
			} catch (JAXBException e1) {
			}
			correct = new UFBoolean(false);
		} finally {
			XlogVO xlog = ZtwVoucherConstant.getBaseXlogVO("SETRECBILLFLAG", "", correct, info, ret);
			try {
				PfxxUtils.lookUpPFxxEJBService().writeLogs_RequiresNew(new XlogVO[]{xlog});
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
		
		return ret;
	}

	@Override
	public String qryBankReceiptInfo(String para) {
		IBillService bp = NCLocator.getInstance().lookup(IBillService.class);
		String ret = null;
		UFBoolean correct = null;
		try {
			if (para == null || para.length() == 0) {
				para = "null";
				throw new BusinessException("参数为空", "S01");
			}
			InvocationInfoProxy.getInstance().setUserDataSource(ZtwVoucherConstant.getDataSource());
			ret = bp.qryBankReceiptInfo(para);
			correct  = new UFBoolean(true);
		} catch (BusinessException e) {
			BankReceiptQryRet infoList = new BankReceiptQryRet();
			infoList.setSystemInfo(new BankReceiptQryRet.SystemInfo());
			infoList.getSystemInfo().setSuccess("0");
			infoList.getSystemInfo().setErrcode(e.getErrorCodeString());
			infoList.getSystemInfo().setMessage(e.getMessage());
			
			try {
				ret = JaxbTools.getStringFromObject(infoList);
			} catch (JAXBException e1) {
			}
			correct = new UFBoolean(false);
		} finally {
			XlogVO xlog = ZtwVoucherConstant.getBaseXlogVO("BANKRECEIPTQRY", "", correct, para, ret);
			try {
				PfxxUtils.lookUpPFxxEJBService().writeLogs_RequiresNew(new XlogVO[]{xlog});
			} catch (BusinessException e) {
				Logger.error(e.getMessage(),e);
			}
		}
		
		return ret;
	}
}
