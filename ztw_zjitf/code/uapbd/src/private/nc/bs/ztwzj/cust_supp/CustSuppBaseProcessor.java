package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoQryService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.pub.BusinessException;

public class CustSuppBaseProcessor {
	protected String isCustomerExist(String code) {
		ICustBaseInfoQueryService custQry = NCLocator.getInstance().lookup(ICustBaseInfoQueryService.class);
		try {
			String[] pks = custQry.queryCustomerPKByCondition(new String[]{NCDataConstant.PK_GROUP.getValue()}, "code='"+code+"'");
			if (pks.length>0)
				return pks[0];
		} catch (BusinessException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}

	protected String isSupplierExist(String code) {
		ISupplierBaseInfoQryService suppQry = NCLocator.getInstance().lookup(ISupplierBaseInfoQryService.class);
		try {
			String[] pks = suppQry.querySupplierPKsByCondition(new String[]{NCDataConstant.PK_GROUP.getValue()}, "code='"+code+"'");
			if (pks.length>0)
				return pks[0];
		} catch (BusinessException e) {
			e.printStackTrace();
			return null;
		}
		return null;
	}
}