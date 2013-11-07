package nc.bs.ztwzj.cust_supp;

import nc.bs.framework.common.NCLocator;
import nc.bs.logging.Logger;
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
import nc.vo.ztwzj.pub.Cust_SuppVO;

public class CustomerProcessor extends CustSuppBaseProcessor {
	public String processCustomer(Cust_SuppVO vo) throws BusinessException{
		String pk_cust = isCustomerExist(vo.code);
		if (pk_cust != null){
			updateCustomer(pk_cust, vo);
			String pk_supp = isSupplierExist(vo.code);
			if (pk_supp != null){
				updateSupplier(pk_supp, vo);
			}
			return "更新";
		}
		
		String pk_supp = isSupplierExist(vo.code);
		if (pk_supp != null){
			addCustomerFromSupplier(pk_supp, vo);
			return "关联供应商";
		}
		
		addCustomer(vo);
		
		return "增加";
	}
	
	private void addCustomer(Cust_SuppVO vo) throws BusinessException{
		CustomerVO cust = getCustomerVO(vo);
		
		ICustBaseInfoService custDao = NCLocator.getInstance().lookup(ICustBaseInfoService.class);
		try {
			custDao.insertCustomerVO(cust, false);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}

	
	private void addCustomerFromSupplier(String pk_supplier, Cust_SuppVO vo) throws BusinessException{
		ISupplierBaseInfoQryService suppQry = NCLocator.getInstance().lookup(ISupplierBaseInfoQryService.class);
		ICustSupplierService cust_supp = NCLocator.getInstance().lookup(ICustSupplierService.class);
		
		try {
			SupplierVO supp = suppQry.querySupBaseInfoByPks(null, new String[]{pk_supplier})[0];
			CustomerVO cust = getCustomerVO(vo);
			
			cust_supp.insertCustAndRelaToSup(cust, supp);
		} catch (BusinessException e) {
			Logger.error(e.getMessage(),e);
			throw e;
		}
	}
	
	protected CustomerVO getCustomerVO(Cust_SuppVO vo) {
		CustomerVO cust = new CustomerVO();
		cust.setIsvat(new UFBoolean(false));
		cust.setFrozenflag(new UFBoolean(false));
		cust.setCusttaxtypes(new CustCountrytaxesVO[]{});
		cust.setCustcontacts(new CustlinkmanVO[]{});
		cust.setIssupplier(new UFBoolean(false));
		cust.setCode(vo.code);
		cust.setIsfreecust(new UFBoolean(false));
		cust.setCustvat(new CustvatVO[]{});
		cust.setPk_custclass(getPk_custclass(vo.code, vo.financial));
		cust.setEnablestate(2);
		cust.setPk_group(NCDataConstant.PK_GROUP.getValue());
		cust.setCustprop(0);
		cust.setDataoriginflag(0);
		cust.setPk_org(NCDataConstant.PK_GROUP.getValue());
		cust.setName(vo.name);
		cust.setShortname(vo.shortName);
		cust.setCustbanks(new CustbankVO[]{});
		cust.setPk_format(NCDataConstant.PK_FORMAT.getValue());
		cust.setDr(0);
		cust.setPk_country(NCDataConstant.PK_COUNTRY.getValue());
		cust.setIsretailstore(new UFBoolean(false));
		cust.setCuststate(1);
		cust.setPk_timezone(NCDataConstant.PK_TIMEZONE.getValue());
		return cust;
	}
}
