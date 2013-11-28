package nc.itf.ztwzj.sapitf;

import nc.vo.pub.BusinessException;

public interface IBillService {
	String qryRecvBillInfo(String para) throws BusinessException;
	String setRecvBillSHFlag(String info) throws BusinessException;
	
	String qryBankReceiptInfo(String para) throws BusinessException;
}
