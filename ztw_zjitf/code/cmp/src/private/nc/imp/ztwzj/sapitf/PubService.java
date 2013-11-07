package nc.imp.ztwzj.sapitf;

import nc.bs.dao.BaseDAO;
import nc.itf.ztwzj.sapitf.IPubService;
import nc.vo.pub.BusinessException;
import nc.vo.ztwzj.sapitf.voucher.VoucherQryInfo;

public class PubService implements IPubService {

	@Override
	public void setTMVoucherBillFlag(VoucherQryInfo[] value) throws BusinessException {
		BaseDAO dao = new BaseDAO();
		
		for (int i=0;i<value.length;i++){
			StringBuilder sql = new StringBuilder();
			sql.append("update ");
			sql.append(value[i].getUdef1());
			sql.append(" set ");
			sql.append(value[i].getUdef2());
			sql.append(" ='Y' where ");
			sql.append(value[i].getUdef3());
			sql.append(" ='");
			sql.append(value[i].getPk_busibill());
			sql.append("'");
			
			dao.executeUpdate(sql.toString());
		}
	}
}
