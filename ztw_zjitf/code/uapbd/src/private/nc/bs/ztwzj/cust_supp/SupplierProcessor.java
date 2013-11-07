package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.cust.baseinfo.ICustSupplierService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.ztwzj.pub.Cust_SuppVO;

public class SupplierProcessor extends CustSuppBaseProcessor {
	public String processSupplier(Cust_SuppVO vo) throws BusinessException{
		String pk_supp = isSupplierExist(vo.code);
		if (pk_supp != null){
			updateSupplier(pk_supp, vo);
			String pk_cust = isCustomerExist(vo.code);
			if (pk_cust != null){
				updateCustomer(pk_cust, vo);
			}
			return "更新";
		}
		
		String pk_cust = isCustomerExist(vo.code);
		if (pk_cust != null){
			addSupplierFromCustomer(pk_cust, vo);
			return "关联客户";
		}
		
		addSupplier(vo);
		
		return "增加";
	}
	
	private void addSupplier(Cust_SuppVO vo) throws BusinessException{
		SupplierVO supp = getSupplierVO(vo);
		
		ISupplierBaseInfoService suppDao = NCLocator.getInstance().lookup(ISupplierBaseInfoService.class);
		try {
			suppDao.insertSupplierVO(supp, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private void addSupplierFromCustomer(String pk_customer, Cust_SuppVO vo) throws BusinessException{
		ICustBaseInfoQueryService custQry = NCLocator.getInstance().lookup(ICustBaseInfoQueryService.class);
		ICustSupplierService cust_supp = NCLocator.getInstance().lookup(ICustSupplierService.class);
		
		try {
			CustomerVO cust = custQry.queryDataByPkSet(new String[]{pk_customer})[0];
			SupplierVO supp = getSupplierVO(vo);
			
			cust_supp.insertSupAndRelaToCust(supp, cust);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	protected SupplierVO getSupplierVO(Cust_SuppVO vo) {
		SupplierVO supp = new SupplierVO();
		supp.setIsvat(new UFBoolean(false));
		supp.setSupstate(1);
		supp.setPk_oldsupplier("~");
		supp.setCode(vo.code);
		supp.setIsfreecust(new UFBoolean(false));
		supp.setEnablestate(2);
		supp.setPk_group(NCDataConstant.PK_GROUP.getValue());
		supp.setSupbankacc(new nc.vo.bd.cust.CustbankVO[]{});
		supp.setDataoriginflag(0);
		supp.setIscustomer(new UFBoolean(false));
		supp.setPk_org(NCDataConstant.PK_GROUP.getValue());
		supp.setName(vo.name);
		supp.setShortname(vo.shortName);
		supp.setSupcountrytaxes(new nc.vo.bd.supplier.SupCountryTaxesVO[]{});
		supp.setPk_supplierclass(getPk_suppclass(vo.code, vo.financial));
		supp.setPk_format(NCDataConstant.PK_FORMAT.getValue());
		supp.setDr(0);
		supp.setIsoutcheck(new UFBoolean(false));
		supp.setPk_country(NCDataConstant.PK_COUNTRY.getValue());
		supp.setSupprop(0);
		supp.setSuplinkman(new nc.vo.bd.supplier.SupLinkmanVO[]{});
		supp.setPk_timezone(NCDataConstant.PK_TIMEZONE.getValue());
		return supp;
	}
}
