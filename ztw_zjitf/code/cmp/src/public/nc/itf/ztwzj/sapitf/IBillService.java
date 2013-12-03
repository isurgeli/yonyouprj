package nc.itf.ztwzj.sapitf;

import java.util.List;

import nc.vo.pub.BusinessException;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse.OTab;

public interface IBillService {
	String qryRecvBillInfo(String para) throws BusinessException;
	void setRecvBillSHFlag(String info) throws BusinessException;
	
	String qryBankReceiptInfo(String para) throws BusinessException;
	
	List<OTab.Item> qryPayBillInfo(String para) throws BusinessException;
	
	void setPayBillNCFlag(String statu, List<String> payNos) throws BusinessException;
}
