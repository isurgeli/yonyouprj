package nc.itf.ztwzj.sapitf;

import nc.vo.pub.BusinessException;
import nc.vo.ztwzj.sapitf.voucher.VoucherQryInfo;

public interface IPubService {
	void setTMVoucherBillFlag(VoucherQryInfo[] value) throws BusinessException; 
}
