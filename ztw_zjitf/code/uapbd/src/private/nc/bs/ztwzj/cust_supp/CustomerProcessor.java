package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoQueryService;
import nc.itf.bd.cust.baseinfo.ICustBaseInfoService;
import nc.itf.bd.cust.baseinfo.ICustSupplierService;
import nc.itf.bd.supplier.baseinfo.ISupplierBaseInfoQryService;
import nc.itf.ztwzj.mdmitf.NCDataConstant;
import nc.vo.bd.cust.CustCountrytaxesVO;
import nc.vo.bd.cust.CustbankVO;
import nc.vo.bd.cust.CustlinkmanVO;
import nc.vo.bd.cust.CustomerVO;
import nc.vo.bd.cust.CustvatVO;
import nc.vo.bd.supplier.SupplierVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;

public class CustomerProcessor extends CustSuppBaseProcessor {
	public void processCustomer(String code, String name, boolean financial) throws BusinessException{
		String pk_cust = isCustomerExist(code);
		if (pk_cust != null){
			updateCustomer(pk_cust, code, name, financial);
			return;
		}
		
		String pk_supp = isSupplierExist(code);
		if (pk_supp != null){
			addCustomerFromSupplier(pk_supp, code, name, financial);
			return;
		}
		
		addCustomer(code, name, financial);
	}
	
	private void addCustomer(String code, String name, boolean financial) throws BusinessException{
		CustomerVO cust = getCustomerVO(code, name, financial);
		
		ICustBaseInfoService custDao = NCLocator.getInstance().lookup(ICustBaseInfoService.class);
		try {
			custDao.insertCustomerVO(cust, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}

	private void updateCustomer(String pk_cust, String code, String name, boolean financial) throws BusinessException{
		ICustBaseInfoQueryService custQry = NCLocator.getInstance().lookup(ICustBaseInfoQueryService.class);
		ICustBaseInfoService custDao = NCLocator.getInstance().lookup(ICustBaseInfoService.class);
		try {
			CustomerVO cust = custQry.queryDataByPkSet(new String[]{pk_cust})[0];
			cust.setName(name);
			cust.setPk_custclass(getPk_custclass(code, financial));
			
			custDao.updateCustomerVO(cust, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	private void addCustomerFromSupplier(String pk_supplier, String code, String name, boolean financial) throws BusinessException{
		ISupplierBaseInfoQryService suppQry = NCLocator.getInstance().lookup(ISupplierBaseInfoQryService.class);
		ICustSupplierService cust_supp = NCLocator.getInstance().lookup(ICustSupplierService.class);
		
		try {
			SupplierVO supp = suppQry.querySupBaseInfoByPks(null, new String[]{pk_supplier})[0];
			CustomerVO cust = getCustomerVO(code, name, financial);
			
			cust_supp.insertCustAndRelaToSup(cust, supp);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	protected CustomerVO getCustomerVO(String code, String name, boolean financial) {
		CustomerVO cust = new CustomerVO();
		cust.setIsvat(new UFBoolean(false));
		cust.setFrozenflag(new UFBoolean(false));
		cust.setCusttaxtypes(new CustCountrytaxesVO[]{});
		cust.setCustcontacts(new CustlinkmanVO[]{});
		cust.setIssupplier(new UFBoolean(false));
		cust.setCode(code);
		cust.setIsfreecust(new UFBoolean(false));
		cust.setCustvat(new CustvatVO[]{});
		cust.setPk_custclass(getPk_custclass(code, financial));
		cust.setEnablestate(2);
		cust.setPk_group(NCDataConstant.PK_GROUP.getValue());
		cust.setCustprop(0);
		cust.setDataoriginflag(0);
		cust.setPk_org(NCDataConstant.PK_GROUP.getValue());
		cust.setName(name);
		cust.setShortname(name);
		cust.setCustbanks(new CustbankVO[]{});
		cust.setPk_format(NCDataConstant.PK_FORMAT.getValue());
		cust.setDr(0);
		cust.setPk_country(NCDataConstant.PK_COUNTRY.getValue());
		cust.setIsretailstore(new UFBoolean(false));
		cust.setCuststate(1);
		cust.setPk_timezone(NCDataConstant.PK_TIMEZONE.getValue());
		return cust;
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
}
