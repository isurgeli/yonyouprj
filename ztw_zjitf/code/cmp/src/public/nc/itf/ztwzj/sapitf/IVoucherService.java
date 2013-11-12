package nc.itf.ztwzj.sapitf;

import nc.vo.pub.BusinessException;
import nc.vo.ztwzj.sapitf.voucher.VoucherQryInfo;

public interface IVoucherService {
	void setTMVoucherBillFlag(String retInfo) throws BusinessException;
	String qryTMVoucherBillInfoXml(String para) throws BusinessException;
	VoucherQryInfo[] qryTMVoucherBillInfoVo(String para) throws BusinessException;
}
