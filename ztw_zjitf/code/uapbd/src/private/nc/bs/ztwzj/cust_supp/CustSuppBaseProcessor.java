package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoQryService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.pub.BusinessException;

public class CustSuppBaseProcessor {
	protected String isCustomerExist(String code) throws BusinessException {
		ICustBaseInfoQueryService custQry = NCLocator.getInstance().lookup(ICustBaseInfoQueryService.class);
		try {
			String[] pks = custQry.queryCustomerPKByCondition(new String[]{NCDataConstant.PK_GROUP.getValue()}, "code='"+code+"'");
			if (pks.length>0)
				return pks[0];
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
		return null;
	}

	protected String isSupplierExist(String code) throws BusinessException {
		ISupplierBaseInfoQryService suppQry = NCLocator.getInstance().lookup(ISupplierBaseInfoQryService.class);
		try {
			String[] pks = suppQry.querySupplierPKsByCondition(new String[]{NCDataConstant.PK_GROUP.getValue()}, "code='"+code+"'");
			if (pks.length>0)
				return pks[0];
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
		return null;
	}
}