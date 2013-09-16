package nc.vo.sc.order;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class OrderReportVO extends SuperVO {

	

//	private String d_cprocessmangid;// 加工品管理档案ID
//	private String d_cprocessbaseid;// 加工品基本档案ID
	private String d_cmangid;// 材料管理档案ID
	private String d_cbaseid;// 材料基本档案ID
	private UFDouble d_nmeasurenum;// 标准用量
	private UFDouble d_nnum;// 订单可发料数
	private UFDouble d_nprice;// 单价
	private UFDouble d_nmny;// 金额
	private String d_vproducenum;// 批次
	private UFDouble d_nwastrate;// 损耗率
	private UFDouble d_ntaxrate;// 税率
	private String d_cunitid;// 计量单位ID
	private UFDouble d_nunitrate;// 计量单位换算率
	private String d_bisww;// 是否委外发料
	private UFDouble d_naccumsendnum;// 累计发料数量
	private UFDouble d_naccumwritenum;// 累计核销数量
	private UFDouble d_nordernum;// 委外数量
	private UFDouble d_naccumwastnum;// 累计途耗数量
	private UFDouble d_forderrowstatus;// 单据行状态
	private String d_cordersource;// 来源单据类型
	private String d_csourcebillid;// 来源单据ID
	private String d_csourcebillrow;// 来源单据行ID
	private String d_cupsourcebilltype;// 上层单据类型
	private String d_cupsourcebillid;// 上层来源单据ID
	private String d_cupsourcebillrowid;// 上层来源单据行ID
	private String d_vmemo;// 备注
	private String d_crowno;// 行号
	private String d_vfree1;// 自由项1
	private String d_vfree2;// 自由项2
	private String d_vfree3;// 自由项3
	private String d_vfree4;// 自由项4
	private String d_vfree5;// 自由项5
	private String d_vfree0;// 自由项0
//	private Integer dr;
//	private UFDateTime ts;

	private String b_cmangid;// 材料管理档案ID
	private String b_cbaseid;// 材料基本档案ID
	private UFDouble b_naccumstorenum;
	private UFDouble b_naccumarrvnum;
	private UFDouble b_naccuminvoicenum;
	private UFDouble b_measrate;
	private UFDouble b_noriginalcurmny;// 金额
	private UFDouble b_noriginalnetprice;// 净单价
	private UFDouble b_noriginalcurprice;// 单价
	private String b_vfree1;// 自由项1
	private String b_vfree2;// 自由项2
	private String b_vfree3;// 自由项3
	private String b_vfree4;// 自由项4
	private String b_vfree5;// 自由项5
	private String b_vfree0;// 自由项0
	
	private UFDouble b_nordernum;// 委外数量
	private String corderid;// 委外订单ID
	private String b_corder_bid;// 委外订单行ID
	private String cvendorid;// 供应商ID
	private String cvendormangid;// 供应商管理ID
	public String vordercode;
	public UFDate dorderdate;
	private String d_corder_lb_id;// 委外订单料比行ID
	public String cwareid;
	public String caccountbankid;
	public String cdeptid;
	public String cemployeeid;
	public String creciever;
	public String cgiveinvoicevendor;
	public String ctransmodeid;
	public String ctermProtocolid;
	public Integer ibillstatus;
	public String caccountyear;
	public String coperator;
	private String pk_corp;// 公司主键
	
	public UFDouble getB_nordernum() {
		return b_nordernum;
	}

	public void setB_nordernum(UFDouble b_nordernum) {
		this.b_nordernum = b_nordernum;
	}

	/**
	 * 净含税单价
	 */
	public UFDouble b_norgnettaxprice ;

	/**
	 * 含税单价
	 */
	public UFDouble b_norgtaxprice ;

//	/**
//	 * 扣税类别
//	 */
//	public UFDouble b_idiscounttaxtype ;

	/**
	 * 价税合计
	 */
	public UFDouble b_noriginalsummny ;

	/**
	 * 币种汇率
	 */
	public UFDouble b_nexchangeotobrate;

	/**
	 * 本币金额
	 */
	public UFDouble b_nmoney;

	/**
	 * 本币税额
	 */
	public UFDouble b_ntaxmny;

	/**
	 * 本币价税合计
	 */
	public UFDouble b_nsummny;

	/**
	 * 存货管理档案CODE
	 */
	public String b_cinventorycode;

	/**
	 * 存货名称
	 */
	public String b_cinventoryname;

	/**
	 * 存货规格
	 */
	public String b_invspec;

	/**
	 * 存货型号
	 */
	public String b_invtype;

	/**
	 * 主计量单位NAME
	 */
	public String b_cmeasdocname;

	/**
	 * 辅计量单位pk
	 */
	public String b_cassistunit;

	/**
	 * 辅计量单位名称
	 */
	public String b_cassistunitname;

	/**
	 * 是否辅计量管理
	 */
	public String b_isassist;

	/**
	 * 批次号
	 */
	public String b_vproducenum;

	/**
	 * 币种ID
	 */
	public String b_ccurrencytypeid;
	/**
	 * 主计量单位pk
	 */
	public String b_pk_measdoc;

	public String cpurorganization;

	public String getD_corder_lb_id() {
		return d_corder_lb_id;
	}

	public void setD_corder_lb_id(String d_corder_lb_id) {
		this.d_corder_lb_id = d_corder_lb_id;
	}

//	public String getD_cprocessmangid() {
//		return d_cprocessmangid;
//	}
//
//	public void setD_cprocessmangid(String d_cprocessmangid) {
//		this.d_cprocessmangid = d_cprocessmangid;
//	}
//
//	public String getD_cprocessbaseid() {
//		return d_cprocessbaseid;
//	}
//
//	public void setD_cprocessbaseid(String d_cprocessbaseid) {
//		this.d_cprocessbaseid = d_cprocessbaseid;
//	}

	public String getD_cmangid() {
		return d_cmangid;
	}

	public void setD_cmangid(String d_cmangid) {
		this.d_cmangid = d_cmangid;
	}

	public String getD_cbaseid() {
		return d_cbaseid;
	}

	public void setD_cbaseid(String d_cbaseid) {
		this.d_cbaseid = d_cbaseid;
	}

	public String getCvendorid() {
		return cvendorid;
	}

	public void setCvendorid(String cvendorid) {
		this.cvendorid = cvendorid;
	}

	public String getCvendormangid() {
		return cvendormangid;
	}

	public void setCvendormangid(String cvendormangid) {
		this.cvendormangid = cvendormangid;
	}

	public UFDouble getD_nmeasurenum() {
		return d_nmeasurenum;
	}

	public void setD_nmeasurenum(UFDouble d_nmeasurenum) {
		this.d_nmeasurenum = d_nmeasurenum;
	}

	public UFDouble getD_nnum() {
		return d_nnum;
	}

	public void setD_nnum(UFDouble d_nnum) {
		this.d_nnum = d_nnum;
	}

	public UFDouble getD_nprice() {
		return d_nprice;
	}

	public void setD_nprice(UFDouble d_nprice) {
		this.d_nprice = d_nprice;
	}

	public UFDouble getD_nmny() {
		return d_nmny;
	}

	public void setD_nmny(UFDouble d_nmny) {
		this.d_nmny = d_nmny;
	}

	public String getD_vproducenum() {
		return d_vproducenum;
	}

	public void setD_vproducenum(String d_vproducenum) {
		this.d_vproducenum = d_vproducenum;
	}

	public UFDouble getD_nwastrate() {
		return d_nwastrate;
	}

	public void setD_nwastrate(UFDouble d_nwastrate) {
		this.d_nwastrate = d_nwastrate;
	}

	public UFDouble getD_ntaxrate() {
		return d_ntaxrate;
	}

	public void setD_ntaxrate(UFDouble d_ntaxrate) {
		this.d_ntaxrate = d_ntaxrate;
	}

	public String getD_cunitid() {
		return d_cunitid;
	}

	public void setD_cunitid(String d_cunitid) {
		this.d_cunitid = d_cunitid;
	}

	public UFDouble getD_nunitrate() {
		return d_nunitrate;
	}

	public void setD_nunitrate(UFDouble d_nunitrate) {
		this.d_nunitrate = d_nunitrate;
	}

	public String getD_bisww() {
		return d_bisww;
	}

	public void setD_bisww(String d_bisww) {
		this.d_bisww = d_bisww;
	}

	public UFDouble getD_naccumsendnum() {
		return d_naccumsendnum;
	}

	public void setD_naccumsendnum(UFDouble d_naccumsendnum) {
		this.d_naccumsendnum = d_naccumsendnum;
	}

	public UFDouble getD_naccumwritenum() {
		return d_naccumwritenum;
	}

	public void setD_naccumwritenum(UFDouble d_naccumwritenum) {
		this.d_naccumwritenum = d_naccumwritenum;
	}

	public UFDouble getD_nordernum() {
		return d_nordernum;
	}

	public void setD_nordernum(UFDouble d_nordernum) {
		this.d_nordernum = d_nordernum;
	}

	public UFDouble getD_naccumwastnum() {
		return d_naccumwastnum;
	}

	public void setD_naccumwastnum(UFDouble d_naccumwastnum) {
		this.d_naccumwastnum = d_naccumwastnum;
	}

	public UFDouble getD_forderrowstatus() {
		return d_forderrowstatus;
	}

	public void setD_forderrowstatus(UFDouble d_forderrowstatus) {
		this.d_forderrowstatus = d_forderrowstatus;
	}

	public String getD_cordersource() {
		return d_cordersource;
	}

	public void setD_cordersource(String d_cordersource) {
		this.d_cordersource = d_cordersource;
	}

	public String getD_csourcebillid() {
		return d_csourcebillid;
	}

	public void setD_csourcebillid(String d_csourcebillid) {
		this.d_csourcebillid = d_csourcebillid;
	}

	public String getD_csourcebillrow() {
		return d_csourcebillrow;
	}

	public void setD_csourcebillrow(String d_csourcebillrow) {
		this.d_csourcebillrow = d_csourcebillrow;
	}

	public String getD_cupsourcebilltype() {
		return d_cupsourcebilltype;
	}

	public void setD_cupsourcebilltype(String d_cupsourcebilltype) {
		this.d_cupsourcebilltype = d_cupsourcebilltype;
	}

	public String getD_cupsourcebillid() {
		return d_cupsourcebillid;
	}

	public void setD_cupsourcebillid(String d_cupsourcebillid) {
		this.d_cupsourcebillid = d_cupsourcebillid;
	}

	public String getD_cupsourcebillrowid() {
		return d_cupsourcebillrowid;
	}

	public void setD_cupsourcebillrowid(String d_cupsourcebillrowid) {
		this.d_cupsourcebillrowid = d_cupsourcebillrowid;
	}

	public String getD_vmemo() {
		return d_vmemo;
	}

	public void setD_vmemo(String d_vmemo) {
		this.d_vmemo = d_vmemo;
	}

	public String getD_crowno() {
		return d_crowno;
	}

	public void setD_crowno(String d_crowno) {
		this.d_crowno = d_crowno;
	}

	public String getD_vfree1() {
		return d_vfree1;
	}

	public void setD_vfree1(String d_vfree1) {
		this.d_vfree1 = d_vfree1;
	}

	public String getD_vfree2() {
		return d_vfree2;
	}

	public void setD_vfree2(String d_vfree2) {
		this.d_vfree2 = d_vfree2;
	}

	public String getD_vfree3() {
		return d_vfree3;
	}

	public void setD_vfree3(String d_vfree3) {
		this.d_vfree3 = d_vfree3;
	}

	public String getD_vfree4() {
		return d_vfree4;
	}

	public void setD_vfree4(String d_vfree4) {
		this.d_vfree4 = d_vfree4;
	}

	public String getD_vfree5() {
		return d_vfree5;
	}

	public void setD_vfree5(String d_vfree5) {
		this.d_vfree5 = d_vfree5;
	}

	public String getD_vfree0() {
		return d_vfree0;
	}

	public void setD_vfree0(String d_vfree0) {
		this.d_vfree0 = d_vfree0;
	}

	public UFDouble getB_naccumstorenum() {
		return b_naccumstorenum;
	}

	public void setB_naccumstorenum(UFDouble b_naccumstorenum) {
		this.b_naccumstorenum = b_naccumstorenum;
	}

	public UFDouble getB_naccumarrvnum() {
		return b_naccumarrvnum;
	}

	public void setB_naccumarrvnum(UFDouble b_naccumarrvnum) {
		this.b_naccumarrvnum = b_naccumarrvnum;
	}

	public UFDouble getB_naccuminvoicenum() {
		return b_naccuminvoicenum;
	}

	public void setB_naccuminvoicenum(UFDouble b_naccuminvoicenum) {
		this.b_naccuminvoicenum = b_naccuminvoicenum;
	}

	public UFDouble getB_measrate() {
		return b_measrate;
	}

	public void setB_measrate(UFDouble b_measrate) {
		this.b_measrate = b_measrate;
	}

	public UFDouble getB_noriginalcurmny() {
		return b_noriginalcurmny;
	}

	public void setB_noriginalcurmny(UFDouble b_noriginalcurmny) {
		this.b_noriginalcurmny = b_noriginalcurmny;
	}

	public UFDouble getB_noriginalnetprice() {
		return b_noriginalnetprice;
	}

	public void setB_noriginalnetprice(UFDouble b_noriginalnetprice) {
		this.b_noriginalnetprice = b_noriginalnetprice;
	}

	public UFDouble getB_noriginalcurprice() {
		return b_noriginalcurprice;
	}

	public void setB_noriginalcurprice(UFDouble b_noriginalcurprice) {
		this.b_noriginalcurprice = b_noriginalcurprice;
	}

	public String getB_vfree1() {
		return b_vfree1;
	}

	public void setB_vfree1(String b_vfree1) {
		this.b_vfree1 = b_vfree1;
	}

	public String getB_vfree2() {
		return b_vfree2;
	}

	public void setB_vfree2(String b_vfree2) {
		this.b_vfree2 = b_vfree2;
	}

	public String getB_vfree3() {
		return b_vfree3;
	}

	public void setB_vfree3(String b_vfree3) {
		this.b_vfree3 = b_vfree3;
	}

	public String getB_vfree4() {
		return b_vfree4;
	}

	public void setB_vfree4(String b_vfree4) {
		this.b_vfree4 = b_vfree4;
	}

	public String getB_vfree5() {
		return b_vfree5;
	}

	public void setB_vfree5(String b_vfree5) {
		this.b_vfree5 = b_vfree5;
	}

	public String getB_vfree0() {
		return b_vfree0;
	}

	public void setB_vfree0(String b_vfree0) {
		this.b_vfree0 = b_vfree0;
	}

	public UFDouble getB_norgnettaxprice() {
		return b_norgnettaxprice;
	}

	public void setB_norgnettaxprice(UFDouble b_norgnettaxprice) {
		this.b_norgnettaxprice = b_norgnettaxprice;
	}

	public UFDouble getB_norgtaxprice() {
		return b_norgtaxprice;
	}

	public void setB_norgtaxprice(UFDouble b_norgtaxprice) {
		this.b_norgtaxprice = b_norgtaxprice;
	}

	public UFDouble getB_noriginalsummny() {
		return b_noriginalsummny;
	}

	public void setB_noriginalsummny(UFDouble b_noriginalsummny) {
		this.b_noriginalsummny = b_noriginalsummny;
	}

	public UFDouble getB_nexchangeotobrate() {
		return b_nexchangeotobrate;
	}

	public void setB_nexchangeotobrate(UFDouble b_nexchangeotobrate) {
		this.b_nexchangeotobrate = b_nexchangeotobrate;
	}

	public UFDouble getB_nmoney() {
		return b_nmoney;
	}

	public void setB_nmoney(UFDouble b_nmoney) {
		this.b_nmoney = b_nmoney;
	}

	public UFDouble getB_ntaxmny() {
		return b_ntaxmny;
	}

	public void setB_ntaxmny(UFDouble b_ntaxmny) {
		this.b_ntaxmny = b_ntaxmny;
	}

	public UFDouble getB_nsummny() {
		return b_nsummny;
	}

	public void setB_nsummny(UFDouble b_nsummny) {
		this.b_nsummny = b_nsummny;
	}

	public String getB_cbaseid() {
		return b_cbaseid;
	}

	public void setB_cbaseid(String b_cbaseid) {
		this.b_cbaseid = b_cbaseid;
	}

	public String getB_cmangid() {
		return b_cmangid;
	}

	public void setB_cmangid(String b_cmangid) {
		this.b_cmangid = b_cmangid;
	}

	public String getB_cinventorycode() {
		return b_cinventorycode;
	}

	public void setB_cinventorycode(String b_cinventorycode) {
		this.b_cinventorycode = b_cinventorycode;
	}

	public String getB_cinventoryname() {
		return b_cinventoryname;
	}

	public void setB_cinventoryname(String b_cinventoryname) {
		this.b_cinventoryname = b_cinventoryname;
	}

	public String getB_invspec() {
		return b_invspec;
	}

	public void setB_invspec(String b_invspec) {
		this.b_invspec = b_invspec;
	}

	public String getB_invtype() {
		return b_invtype;
	}

	public void setB_invtype(String b_invtype) {
		this.b_invtype = b_invtype;
	}

	public String getB_cmeasdocname() {
		return b_cmeasdocname;
	}

	public void setB_cmeasdocname(String b_cmeasdocname) {
		this.b_cmeasdocname = b_cmeasdocname;
	}

	public String getB_cassistunit() {
		return b_cassistunit;
	}

	public void setB_cassistunit(String b_cassistunit) {
		this.b_cassistunit = b_cassistunit;
	}

	public String getB_cassistunitname() {
		return b_cassistunitname;
	}

	public void setB_cassistunitname(String b_cassistunitname) {
		this.b_cassistunitname = b_cassistunitname;
	}

	public String getB_isassist() {
		return b_isassist;
	}

	public void setB_isassist(String b_isassist) {
		this.b_isassist = b_isassist;
	}

	public String getB_vproducenum() {
		return b_vproducenum;
	}

	public void setB_vproducenum(String b_vproducenum) {
		this.b_vproducenum = b_vproducenum;
	}

	public String getB_ccurrencytypeid() {
		return b_ccurrencytypeid;
	}

	public void setB_ccurrencytypeid(String b_ccurrencytypeid) {
		this.b_ccurrencytypeid = b_ccurrencytypeid;
	}

	public String getB_pk_measdoc() {
		return b_pk_measdoc;
	}

	public void setB_pk_measdoc(String b_pk_measdoc) {
		this.b_pk_measdoc = b_pk_measdoc;
	}

	public String getVordercode() {
		return vordercode;
	}

	public void setVordercode(String vordercode) {
		this.vordercode = vordercode;
	}

	public String getCpurorganization() {
		return cpurorganization;
	}

	public void setCpurorganization(String cpurorganization) {
		this.cpurorganization = cpurorganization;
	}

	public String getCwareid() {
		return cwareid;
	}

	public void setCwareid(String cwareid) {
		this.cwareid = cwareid;
	}

	public UFDate getDorderdate() {
		return dorderdate;
	}

	public void setDorderdate(UFDate dorderdate) {
		this.dorderdate = dorderdate;
	}

	public String getCaccountbankid() {
		return caccountbankid;
	}

	public void setCaccountbankid(String caccountbankid) {
		this.caccountbankid = caccountbankid;
	}

	public String getCdeptid() {
		return cdeptid;
	}

	public void setCdeptid(String cdeptid) {
		this.cdeptid = cdeptid;
	}

	public String getCemployeeid() {
		return cemployeeid;
	}

	public void setCemployeeid(String cemployeeid) {
		this.cemployeeid = cemployeeid;
	}

	public String getCreciever() {
		return creciever;
	}

	public void setCreciever(String creciever) {
		this.creciever = creciever;
	}

	public String getCgiveinvoicevendor() {
		return cgiveinvoicevendor;
	}

	public void setCgiveinvoicevendor(String cgiveinvoicevendor) {
		this.cgiveinvoicevendor = cgiveinvoicevendor;
	}

	public String getCtransmodeid() {
		return ctransmodeid;
	}

	public void setCtransmodeid(String ctransmodeid) {
		this.ctransmodeid = ctransmodeid;
	}

	public String getCtermProtocolid() {
		return ctermProtocolid;
	}

	public void setCtermProtocolid(String ctermProtocolid) {
		this.ctermProtocolid = ctermProtocolid;
	}

	public Integer getIbillstatus() {
		return ibillstatus;
	}

	public void setIbillstatus(Integer ibillstatus) {
		this.ibillstatus = ibillstatus;
	}

	public String getCaccountyear() {
		return caccountyear;
	}

	public void setCaccountyear(String caccountyear) {
		this.caccountyear = caccountyear;
	}

	public String getCoperator() {
		return coperator;
	}

	public void setCoperator(String coperator) {
		this.coperator = coperator;
	}

	public String getCorderid() {
		return corderid;
	}

	public void setCorderid(String corderid) {
		this.corderid = corderid;
	}

	public String getPk_corp() {
		return pk_corp;
	}

	public void setPk_corp(String pk_corp) {
		this.pk_corp = pk_corp;
	}
//
//	public Integer getDr() {
//		return dr;
//	}
//
//	public void setDr(Integer dr) {
//		this.dr = dr;
//	}

//	public UFDateTime getTs() {
//		return ts;
//	}
//
//	public void setTs(UFDateTime ts) {
//		this.ts = ts;
//	}

	@Override
	public String getPKFieldName() {
		// TODO Auto-generated method stub
		return "corder_lb_id";
	}

	@Override
	public String getParentPKFieldName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return "sc_order_ddlb";
	}

	public String getB_corder_bid() {
		return b_corder_bid;
	}

	public void setB_corder_bid(String b_corder_bid) {
		this.b_corder_bid = b_corder_bid;
	}
}
