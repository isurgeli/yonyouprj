package nc.itf.ztwzj.sapitf;

public interface IWS_NC_SAPBill {
	String qryRecvBillInfo(String para);
	String setRecvBillSHFlag(String info);
	
	String qryBankReceiptInfo(String para);
}
