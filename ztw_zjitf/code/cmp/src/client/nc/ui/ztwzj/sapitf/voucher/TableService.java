package nc.ui.ztwzj.sapitf.voucher;

import java.util.ArrayList;

import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.itf.ztwzj.sapitf.IPubService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.ui.uif2.model.MD2AppModelService;
import nc.vo.logging.Debug;
import nc.vo.pub.BusinessException;
import nc.vo.uif2.LoginContext;
import nc.vo.ztwzj.sapitf.voucher.IZtwConstant;
import nc.vo.ztwzj.sapitf.voucher.VoucherQryInfo;

public class TableService extends MD2AppModelService {
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		
		String pk_org = context.getPk_org();
		String busiType = context.getStatusRegistery().getAppStatusObject(IZtwConstant.BUSITYPE).toString();
		ArrayList<VoucherQryInfo> ret = new ArrayList<VoucherQryInfo>();
		
		if (busiType.equals(IZtwConstant.BUSITYPE_REVFDOWN)){
			ret.addAll(query2TO1F1(pk_org));
			ret.addAll(query3TO2F2(pk_org));
		}
		else if (busiType.equals(IZtwConstant.BUSITYPE_PAYTDOWN)){
			ret.addAll(query1TO2F1(pk_org));
			ret.addAll(query2TO3F2(pk_org));
		}
		else if (busiType.equals(IZtwConstant.BUSITYPE_PAYTUP)){
			ret.addAll(query2TO1F2(pk_org));
			ret.addAll(query3TO2F3(pk_org));
		}
		else if (busiType.equals(IZtwConstant.BUSITYPE_REVFUP)){
			ret.addAll(query1TO2F2(pk_org));
			ret.addAll(query2TO3F3(pk_org));
		}
		else if (busiType.equals(IZtwConstant.BUSITYPE_ADJUST)){
			ret.addAll(queryAdjustData(pk_org));
		}
		else
			throw new BusinessException("业务类型错误。");
		
		return ret.toArray(new VoucherQryInfo[0]);
	}
	
	ArrayList<VoucherQryInfo> query2TO1F1(String pk_org) throws BusinessException{ //二级单位向一级单位付款一级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select sf_delivery_h.pk_group,");
		sql.append("       sf_delivery_h.pk_org,");
		sql.append("       '上收下级单位款' vbusitype,");
		sql.append("       sf_delivery_h.vbillno vbillcode,");
		sql.append("       sf_delivery_h.dapprovedate ddate,");
		sql.append("       bd_bankdoc_r.name vbank,");
		sql.append("       bd_bankaccsub_r.accnum vbkaccount,");
		sql.append("       sf_delivery_b.pk_org_p pk_opporg,");
		sql.append("       bd_bankdoc_p.name voppbank,");
		sql.append("       bd_bankaccsub_p.accnum voppbkacc,");
		sql.append("       sf_delivery_b.amount nmny,");
		sql.append("       'sf_delivery_b' udef1,");
		sql.append("       'vuserdef8' udef2,");
		sql.append("       'pk_delivery_b' udef3,");
		sql.append("       sf_delivery_b.pk_delivery_b pk_busibill");
		sql.append("  from sf_delivery_h, sf_delivery_b, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append(" where sf_delivery_h.pk_delivery_h = sf_delivery_b.pk_delivery_h");
		sql.append("   and sf_delivery_b.bankname_r = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and sf_delivery_b.bankname_p = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and sf_delivery_b.pk_bankacc_r = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and sf_delivery_b.pk_bankacc_p = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and sf_delivery_h.billstatus = 4");
		sql.append("   and sf_delivery_h.pk_org = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and sf_delivery_b.vuserdef8 <> 'Y'");
		sql.append("   order by sf_delivery_h.dapprovedate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query2TO1F2(String pk_org) throws BusinessException{ //二级单位向一级单位付款二级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select sf_delivery_h.pk_group,");
		sql.append("       sf_delivery_b.pk_org_p pk_org,");
		sql.append("       '上缴款到上级单位' vbusitype,");
		sql.append("       sf_delivery_h.vbillno vbillcode,");
		sql.append("       sf_delivery_h.dapprovedate ddate,");
		sql.append("       bd_bankdoc_p.name vbank,");
		sql.append("       bd_bankaccsub_p.accnum vbkaccount,");
		sql.append("       sf_delivery_h.pk_org pk_opporg,");
		sql.append("       bd_bankdoc_r.name voppbank,");
		sql.append("       bd_bankaccsub_r.accnum voppbkacc,");
		sql.append("       sf_delivery_b.amount nmny,");
		sql.append("       'sf_delivery_b' udef1,");
		sql.append("       'vuserdef9' udef2,");
		sql.append("       'pk_delivery_b' udef3,");
		sql.append("       sf_delivery_b.pk_delivery_b pk_busibill");
		sql.append("  from sf_delivery_h, sf_delivery_b, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append(" where sf_delivery_h.pk_delivery_h = sf_delivery_b.pk_delivery_h");
		sql.append("   and sf_delivery_b.bankname_r = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and sf_delivery_b.bankname_p = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and sf_delivery_b.pk_bankacc_r = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and sf_delivery_b.pk_bankacc_p = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and sf_delivery_h.billstatus = 4");
		sql.append("   and sf_delivery_b.pk_org_p = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and sf_delivery_b.vuserdef9 <> 'Y'");
		sql.append("   order by sf_delivery_h.dapprovedate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query1TO2F1(String pk_org) throws BusinessException{ //一级单位向二级单位付款一级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select sf_allocate_h.pk_group,");
		sql.append("       sf_allocate_h.pk_org pk_org,");
		sql.append("       '下拨款到下级单位' vbusitype,");
		sql.append("       sf_allocate_h.vbillno vbillcode,");
		sql.append("       sf_allocate_h.dapprovedate ddate,");
		sql.append("       bd_bankdoc_p.name vbank,");
		sql.append("       bd_bankaccsub_p.accnum vbkaccount,");
		sql.append("       sf_allocate_b.pk_org_r pk_opporg,");
		sql.append("       bd_bankdoc_r.name voppbank,");
		sql.append("       bd_bankaccsub_r.accnum voppbkacc,");
		sql.append("       sf_allocate_b.amount nmny,");
		sql.append("       'sf_allocate_b' udef1,");
		sql.append("       'vuserdef8' udef2,");
		sql.append("       'pk_allocate_b' udef3,");
		sql.append("       sf_allocate_b.pk_allocate_b pk_busibill");
		sql.append("  from sf_allocate_h, sf_allocate_b, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append(" where sf_allocate_h.pk_allocate_h = sf_allocate_b.pk_allocate_h");
		sql.append("   and sf_allocate_b.bankname_r = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and sf_allocate_b.bankname_p = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and sf_allocate_b.pk_bankacc_r = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and sf_allocate_b.pk_bankacc_p = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and sf_allocate_h.billstatus = 5");
		sql.append("   and sf_allocate_h.pk_org = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and sf_allocate_b.vuserdef8 <> 'Y'");
		sql.append("   order by sf_allocate_h.dapprovedate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query1TO2F2(String pk_org) throws BusinessException{ //一级单位向二级单位付款二级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select sf_allocate_h.pk_group,");
		sql.append("       sf_allocate_b.pk_org_r pk_org,");
		sql.append("       '接收上级单位拨款' vbusitype,");
		sql.append("       sf_allocate_h.vbillno vbillcode,");
		sql.append("       sf_allocate_h.dapprovedate ddate,");
		sql.append("       bd_bankdoc_r.name vbank,");
		sql.append("       bd_bankaccsub_r.accnum vbkaccount,");
		sql.append("       sf_allocate_h.pk_org pk_opporg,");
		sql.append("       bd_bankdoc_p.name voppbank,");
		sql.append("       bd_bankaccsub_p.accnum voppbkacc,");
		sql.append("       sf_allocate_b.amount nmny,");
		sql.append("       'sf_allocate_b' udef1,");
		sql.append("       'vuserdef9' udef2,");
		sql.append("       'pk_allocate_b' udef3,");
		sql.append("       sf_allocate_b.pk_allocate_b pk_busibill");
		sql.append("  from sf_allocate_h, sf_allocate_b, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append(" where sf_allocate_h.pk_allocate_h = sf_allocate_b.pk_allocate_h");
		sql.append("   and sf_allocate_b.bankname_r = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and sf_allocate_b.bankname_p = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and sf_allocate_b.pk_bankacc_r = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and sf_allocate_b.pk_bankacc_p = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and sf_allocate_h.billstatus = 5");
		sql.append("   and sf_allocate_b.pk_org_r = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and sf_allocate_b.vuserdef9 <> 'Y'");
		sql.append("   order by sf_allocate_h.dapprovedate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query3TO2F2(String pk_org) throws BusinessException{ //三级单位向二级单位付款二级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select cmp_recbill.pk_group,");
		sql.append("       cmp_recbill.pk_org pk_org,");
		sql.append("       '上收下级单位款' vbusitype,");
		sql.append("       cmp_recbill.bill_no vbillcode,");
		sql.append("       cmp_recbill.paydate ddate,");
		sql.append("       bd_bankdoc_p.name vbank,");
		sql.append("       bd_bankaccsub_p.accnum vbkaccount,");
		sql.append("       bd_customer.pk_financeorg pk_opporg,");
		sql.append("       bd_bankdoc_r.name voppbank,");
		sql.append("       bd_bankaccsub_r.accnum voppbkacc,");
		sql.append("       'cmp_recbilldetail' udef1,");
		sql.append("       'def20' udef2,");
		sql.append("       'pk_recbill_detail' udef3,");
		sql.append("       cmp_recbilldetail.rec_primal nmny,");
		sql.append("       cmp_recbilldetail.pk_recbill_detail pk_busibill");
		sql.append("  from cmp_recbill, cmp_recbilldetail, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append("  , bd_bankaccbas bd_bankaccbas_r, bd_bankaccbas bd_bankaccbas_p, bd_customer, org_orgs");
		sql.append(" where cmp_recbill.pk_recbill = cmp_recbilldetail.pk_recbill");
		sql.append("   and cmp_recbilldetail.pk_account = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and cmp_recbilldetail.pk_oppaccount = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and bd_bankaccsub_r.pk_bankaccbas = bd_bankaccbas_r.pk_bankaccbas");
		sql.append("   and bd_bankaccsub_p.pk_bankaccbas = bd_bankaccbas_p.pk_bankaccbas");
		sql.append("   and bd_bankaccbas_r.pk_bankdoc = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and bd_bankaccbas_p.pk_bankdoc = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and cmp_recbilldetail.pk_customer = bd_customer.pk_customer");
		sql.append("   and bd_customer.pk_financeorg = org_orgs.pk_org");
		sql.append("   and org_orgs.pk_fatherorg = cmp_recbill.pk_org");
		sql.append("   and cmp_recbill.paystatus = 2");
		sql.append("   and cmp_recbill.pk_org = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and cmp_recbilldetail.def20 <> 'Y'");
		sql.append("   order by cmp_recbill.paydate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query3TO2F3(String pk_org) throws BusinessException{ //三级单位向二级单位付款三级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select cmp_paybill.pk_group,");
		sql.append("       cmp_paybill.pk_org pk_org,");
		sql.append("       '上缴款到上级单位' vbusitype,");
		sql.append("       cmp_paybill.bill_no vbillcode,");
		sql.append("       cmp_paybill.paydate ddate,");
		sql.append("       bd_bankdoc_p.name vbank,");
		sql.append("       bd_bankaccsub_p.accnum vbkaccount,");
		sql.append("       bd_supplier.pk_financeorg pk_opporg,");
		sql.append("       bd_bankdoc_r.name voppbank,");
		sql.append("       bd_bankaccsub_r.accnum voppbkacc,");
		sql.append("       cmp_paybilldetail.pay_primal nmny,");
		sql.append("       'cmp_paybilldetail' udef1,");
		sql.append("       'def20' udef2,");
		sql.append("       'pk_paybill_detail' udef3,");
		sql.append("       cmp_paybilldetail.pk_paybill_detail pk_busibill");
		sql.append("  from cmp_paybill, cmp_paybilldetail, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append("  , bd_bankaccbas bd_bankaccbas_r, bd_bankaccbas bd_bankaccbas_p, bd_supplier, org_orgs");
		sql.append(" where cmp_paybill.pk_paybill = cmp_paybilldetail.pk_paybill");
		sql.append("   and cmp_paybilldetail.pk_account = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and cmp_paybilldetail.pk_oppaccount = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and bd_bankaccsub_r.pk_bankaccbas = bd_bankaccbas_r.pk_bankaccbas");
		sql.append("   and bd_bankaccsub_p.pk_bankaccbas = bd_bankaccbas_p.pk_bankaccbas");
		sql.append("   and bd_bankaccbas_r.pk_bankdoc = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and bd_bankaccbas_p.pk_bankdoc = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and cmp_paybilldetail.pk_supplier = bd_supplier.pk_supplier");
		sql.append("   and cmp_paybill.pk_org = org_orgs.pk_org");
		sql.append("   and org_orgs.pk_fatherorg = bd_supplier.pk_financeorg");
		sql.append("   and cmp_paybill.paystatus = 2");
		sql.append("   and cmp_paybill.pk_org = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and cmp_paybilldetail.def20 <> 'Y'");
		sql.append("   order by cmp_paybill.paydate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query2TO3F2(String pk_org) throws BusinessException{ //二级单位向三级单位付款二级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select cmp_paybill.pk_group,");
		sql.append("       cmp_paybill.pk_org pk_org,");
		sql.append("       '下拨款到下级单位' vbusitype,");
		sql.append("       cmp_paybill.bill_no vbillcode,");
		sql.append("       cmp_paybill.paydate ddate,");
		sql.append("       bd_bankdoc_p.name vbank,");
		sql.append("       bd_bankaccsub_p.accnum vbkaccount,");
		sql.append("       bd_supplier.pk_financeorg pk_opporg,");
		sql.append("       bd_bankdoc_r.name voppbank,");
		sql.append("       bd_bankaccsub_r.accnum voppbkacc,");
		sql.append("       cmp_paybilldetail.pay_primal nmny,");
		sql.append("       'cmp_paybilldetail' udef1,");
		sql.append("       'def20' udef2,");
		sql.append("       'pk_paybill_detail' udef3,");
		sql.append("       cmp_paybilldetail.pk_paybill_detail pk_busibill");
		sql.append("  from cmp_paybill, cmp_paybilldetail, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append("  , bd_bankaccbas bd_bankaccbas_r, bd_bankaccbas bd_bankaccbas_p, bd_supplier, org_orgs");
		sql.append(" where cmp_paybill.pk_paybill = cmp_paybilldetail.pk_paybill");
		sql.append("   and cmp_paybilldetail.pk_account = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and cmp_paybilldetail.pk_oppaccount = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and bd_bankaccsub_r.pk_bankaccbas = bd_bankaccbas_r.pk_bankaccbas");
		sql.append("   and bd_bankaccsub_p.pk_bankaccbas = bd_bankaccbas_p.pk_bankaccbas");
		sql.append("   and bd_bankaccbas_r.pk_bankdoc = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and bd_bankaccbas_p.pk_bankdoc = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and cmp_paybilldetail.pk_supplier = bd_supplier.pk_supplier");
		sql.append("   and bd_supplier.pk_financeorg = org_orgs.pk_org");
		sql.append("   and org_orgs.pk_fatherorg = cmp_paybill.pk_org");
		sql.append("   and cmp_paybill.paystatus = 2");
		sql.append("   and cmp_paybill.pk_org = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and cmp_paybilldetail.def20 <> 'Y'");
		sql.append("   order by cmp_paybill.paydate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> query2TO3F3(String pk_org) throws BusinessException{ //二级单位向三级单位付款三级信息
		StringBuilder sql = new StringBuilder();
		sql.append("select cmp_recbill.pk_group,");
		sql.append("       cmp_recbill.pk_org pk_org,");
		sql.append("       '接收上级单位拨款' vbusitype,");
		sql.append("       cmp_recbill.bill_no vbillcode,");
		sql.append("       cmp_recbill.paydate ddate,");
		sql.append("       bd_bankdoc_p.name vbank,");
		sql.append("       bd_bankaccsub_p.accnum vbkaccount,");
		sql.append("       bd_customer.pk_financeorg pk_opporg,");
		sql.append("       bd_bankdoc_r.name voppbank,");
		sql.append("       bd_bankaccsub_r.accnum voppbkacc,");
		sql.append("       cmp_recbilldetail.rec_primal nmny,");
		sql.append("       'cmp_recbilldetail' udef1,");
		sql.append("       'def20' udef2,");
		sql.append("       'pk_recbill_detail' udef3,");
		sql.append("       cmp_recbilldetail.pk_recbill_detail pk_busibill");
		sql.append("  from cmp_recbill, cmp_recbilldetail, bd_bankdoc bd_bankdoc_r, bd_bankdoc bd_bankdoc_p, bd_bankaccsub bd_bankaccsub_r, bd_bankaccsub bd_bankaccsub_p");
		sql.append("  , bd_bankaccbas bd_bankaccbas_r, bd_bankaccbas bd_bankaccbas_p, bd_customer, org_orgs");
		sql.append(" where cmp_recbill.pk_recbill = cmp_recbilldetail.pk_recbill");
		sql.append("   and cmp_recbilldetail.pk_account = bd_bankaccsub_r.pk_bankaccsub");
		sql.append("   and cmp_recbilldetail.pk_oppaccount = bd_bankaccsub_p.pk_bankaccsub");
		sql.append("   and bd_bankaccsub_r.pk_bankaccbas = bd_bankaccbas_r.pk_bankaccbas");
		sql.append("   and bd_bankaccsub_p.pk_bankaccbas = bd_bankaccbas_p.pk_bankaccbas");
		sql.append("   and bd_bankaccbas_r.pk_bankdoc = bd_bankdoc_r.pk_bankdoc");
		sql.append("   and bd_bankaccbas_p.pk_bankdoc = bd_bankdoc_p.pk_bankdoc");
		sql.append("   and cmp_recbilldetail.pk_customer = bd_customer.pk_customer");
		sql.append("   and cmp_recbill.pk_org = org_orgs.pk_org");
		sql.append("   and org_orgs.pk_fatherorg = bd_customer.pk_financeorg");
		sql.append("   and cmp_recbill.paystatus = 2");
		sql.append("   and cmp_recbill.pk_org = '");
		sql.append(pk_org);
		sql.append("'");
		sql.append("   and cmp_recbilldetail.def20 <> 'Y'");
		sql.append("   order by cmp_recbill.paydate");
		
		return queryDataBySQL(sql);
	}
	
	ArrayList<VoucherQryInfo> queryAdjustData(String pk_org) throws BusinessException{ 
		StringBuilder sql = new StringBuilder();
		
		return queryDataBySQL(sql);
	}
	
	private ArrayList<VoucherQryInfo> queryDataBySQL(StringBuilder sql)
			throws BusinessException {
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		try {
			ArrayList<VoucherQryInfo> ret = (ArrayList<VoucherQryInfo>)dao.executeQuery(sql.toString(), new BeanListProcessor(VoucherQryInfo.class));
			return ret;
		} catch (BusinessException e) {
			Debug.error(e.getMessage(), e);
			throw new BusinessException("数据查询错误", e);
		}
	}
	
	public void setBillFlag(VoucherQryInfo[] value) throws BusinessException{
		IPubService bs = NCLocator.getInstance().lookup(IPubService.class);
		bs.setTMVoucherBillFlag(value);
	}
}
