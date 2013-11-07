package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoQryService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.ztwzj.pub.Cust_SuppVO;

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
	
	protected void updateCustomer(String pk_cust, Cust_SuppVO vo) throws BusinessException{
		ICustBaseInfoQueryService custQry = NCLocator.getInstance().lookup(ICustBaseInfoQueryService.class);
		ICustBaseInfoService custDao = NCLocator.getInstance().lookup(ICustBaseInfoService.class);
		try {
			CustomerVO cust = custQry.queryDataByPkSet(new String[]{pk_cust})[0];
			cust.setName(vo.name);
			cust.setShortname(vo.shortName);
			cust.setPk_custclass(getPk_custclass(vo.code, vo.financial));
			
			custDao.updateCustomerVO(cust, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	protected String getPk_custclass(String code, boolean financial) {
		if (financial)
			return NCDataConstant.PK_FINANCIALCUSTCLASS.getValue();
		else{
			if (code.charAt(0)=='2')
				return NCDataConstant.PK_DOMESTICCUSTCLASS.getValue();
		}
		
		return NCDataConstant.PK_FOREIGNCUSTCLASS.getValue();
	}
	
	protected void updateSupplier(String pk_supp, Cust_SuppVO vo) throws BusinessException{
		ISupplierBaseInfoQryService suppQry = NCLocator.getInstance().lookup(ISupplierBaseInfoQryService.class);
		ISupplierBaseInfoService suppDao = NCLocator.getInstance().lookup(ISupplierBaseInfoService.class);
		try {
			SupplierVO supp = suppQry.querySupBaseInfoByPks(null, new String[]{pk_supp})[0];
			supp.setName(vo.name);
			supp.setShortname(vo.shortName);
			supp.setPk_supplierclass(getPk_suppclass(vo.code, vo.financial));
			
			suppDao.updateSupplierVO(supp, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	protected String getPk_suppclass(String code, boolean financial) {
		if (financial)
			return NCDataConstant.PK_FINANCIALSUPPCLASS.getValue();
		else{
			if (code.charAt(0)=='2')
				return NCDataConstant.PK_DOMESTICSUPPCLASS.getValue();
		}
		
		return NCDataConstant.PK_FOREIGNSUPPCLASS.getValue();
	}
}