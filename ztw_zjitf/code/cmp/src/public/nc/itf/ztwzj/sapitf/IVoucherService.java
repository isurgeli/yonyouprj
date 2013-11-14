package nc.itf.ztwzj.sapitf;

import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

public interface IVoucherService {
	void setTMVoucherBillFlag(String retInfo) throws BusinessException;
	String qryTMVoucherBillInfoXml(String para) throws BusinessException;
	SuperVO[] qryTMVoucherBillInfoVo(String pk_org, String[] busitypes, String sd, String ed) throws BusinessException;
}
