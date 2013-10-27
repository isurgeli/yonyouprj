package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.cust.baseinfo.ICustSupplierService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoQryService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class SupplierProcessor extends CustSuppBaseProcessor {
	public void processSupplier(String code, String name, boolean financial) throws BusinessException{
		String pk_supp = isSupplierExist(code);
		if (pk_supp != null){
			updateSupplier(pk_supp, code, name, financial);
			return;
		}
		
		String pk_cust = isCustomerExist(code);
		if (pk_cust != null){
			addSupplierFromCustomer(pk_cust, code, name, financial);
			return;
		}
		
		addSupplier(code, name, financial);
	}
	
	private void addSupplier(String code, String name, boolean financial) throws BusinessException{
		SupplierVO supp = getSupplierVO(code, name, financial);
		
		ISupplierBaseInfoService suppDao = NCLocator.getInstance().lookup(ISupplierBaseInfoService.class);
		try {
			suppDao.insertSupplierVO(supp, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}

	private void updateSupplier(String pk_supp, String code, String name, boolean financial) throws BusinessException{
		ISupplierBaseInfoQryService suppQry = NCLocator.getInstance().lookup(ISupplierBaseInfoQryService.class);
		ISupplierBaseInfoService suppDao = NCLocator.getInstance().lookup(ISupplierBaseInfoService.class);
		try {
			SupplierVO supp = suppQry.querySupBaseInfoByPks(null, new String[]{pk_supp})[0];
			supp.setName(name);
			supp.setPk_supplierclass(getPk_suppclass(code, financial));
			
			suppDao.updateSupplierVO(supp, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private void addSupplierFromCustomer(String pk_customer, String code, String name, boolean financial) throws BusinessException{
		ICustBaseInfoQueryService custQry = NCLocator.getInstance().lookup(ICustBaseInfoQueryService.class);
		ICustSupplierService cust_supp = NCLocator.getInstance().lookup(ICustSupplierService.class);
		
		try {
			CustomerVO cust = custQry.queryDataByPkSet(new String[]{pk_customer})[0];
			SupplierVO supp = getSupplierVO(code, name, financial);
			
			cust_supp.insertSupAndRelaToCust(supp, cust);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	protected SupplierVO getSupplierVO(String code, String name, boolean financial) {
		SupplierVO supp = new SupplierVO();
		supp.setIsvat(new UFBoolean(false));
		supp.setSupstate(1);
		supp.setPk_oldsupplier("~");
		supp.setCode(code);
		supp.setIsfreecust(new UFBoolean(false));
		supp.setEnablestate(2);
		supp.setPk_group(NCDataConstant.PK_GROUP.getValue());
		supp.setSupbankacc(new nc.vo.bd.cust.CustbankVO[]{});
		supp.setDataoriginflag(0);
		supp.setIscustomer(new UFBoolean(false));
		supp.setPk_org(NCDataConstant.PK_GROUP.getValue());
		supp.setName(name);
		supp.setShortname(name);
		supp.setSupcountrytaxes(new nc.vo.bd.supplier.SupCountryTaxesVO[]{});
		supp.setPk_supplierclass(getPk_suppclass(code, financial));
		supp.setPk_format(NCDataConstant.PK_FORMAT.getValue());
		supp.setDr(0);
		supp.setIsoutcheck(new UFBoolean(false));
		supp.setPk_country(NCDataConstant.PK_COUNTRY.getValue());
		supp.setSupprop(0);
		supp.setSuplinkman(new nc.vo.bd.supplier.SupLinkmanVO[]{});
		supp.setPk_timezone(NCDataConstant.PK_TIMEZONE.getValue());
		return supp;
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
