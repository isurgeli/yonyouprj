package nc.vo.sc.order;

import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;

public class OrderDdlbVO extends SuperVO{

private String corder_lb_id;//委外订单料比行ID
private String corderid;//委外订单ID
private String corder_bid;//委外订单行ID
private String pk_corp;//公司主键
private String cprocessmangid;//加工品管理档案ID
private String cprocessbaseid;//加工品基本档案ID
private String cmangid;//材料管理档案ID
private String cbaseid;//材料基本档案ID
private String cvendorid;//供应商ID
private String cvendormangid;//供应商管理ID
private UFDouble nmeasurenum;//标准用量
private UFDouble nnum;//订单可发料数
private UFDouble nprice;//单价
private UFDouble nmny;//金额
private String vproducenum;//批次
private UFDouble nwastrate;//损耗率
private UFDouble ntaxrate;//税率
private String pk_measdoc;//计量单位ID
private UFDouble nunitrate;//计量单位换算率
private String bisww;//是否委外发料
private UFDouble naccumsendnum;//累计发料数量
private UFDouble naccumwritenum;//累计核销数量
private UFDouble nordernum;//委外数量
private UFDouble naccumwastnum;//累计途耗数量
private UFDouble forderrowstatus;//单据行状态
private String cordersource;//来源单据类型
private String csourcebillid;//来源单据ID
private String csourcebillrow;//来源单据行ID
private String cupsourcebilltype;//上层单据类型
private String cupsourcebillid;//上层来源单据ID
private String cupsourcebillrowid;//上层来源单据行ID
private String vmemo;//备注
private String crowno;//行号
private String vfree0;//自由项0
private String vfree1;//自由项1
private String vfree2;//自由项2
private String vfree3;//自由项3
private String vfree4;//自由项4
private String vfree5;//自由项5
private String vdef1;//自定义项1
private String vdef2;//自定义项2
private String vdef3;//自定义项3
private String vdef4;//自定义项4
private String vdef5;//自定义项5
private String vdef6;//自定义项6
private String vdef7;//自定义项7
private String vdef8;//自定义项8
private String vdef9;//自定义项9
private String vdef10;//自定义项10
private String vdef11;//自定义项11
private String vdef12;//自定义项12
private String vdef13;//自定义项13
private String vdef14;//自定义项14
private String vdef15;//自定义项15
private String vdef16;//自定义项16
private String vdef17;//自定义项17
private String vdef18;//自定义项18
private String vdef19;//自定义项19
private String vdef20;//自定义项20
private String pk_defdoc1;//自定义项主键1
private String pk_defdoc2;//自定义项主键2
private String pk_defdoc3;//自定义项主键3
private String pk_defdoc4;//自定义项主键4
private String pk_defdoc5;//自定义项主键5
private String pk_defdoc6;//自定义项主键6
private String pk_defdoc7;//自定义项主键7
private String pk_defdoc8;//自定义项主键8
private String pk_defdoc9;//自定义项主键9
private String pk_defdoc10;//自定义项主键10
private String pk_defdoc11;//自定义项主键11
private String pk_defdoc12;//自定义项主键12
private String pk_defdoc13;//自定义项主键13
private String pk_defdoc14;//自定义项主键14
private String pk_defdoc15;//自定义项主键15
private String pk_defdoc16;//自定义项主键16
private String pk_defdoc17;//自定义项主键17
private String pk_defdoc18;//自定义项主键18
private String pk_defdoc19;//自定义项主键19
private String pk_defdoc20;//自定义项主键20
private Integer dr;
private UFDateTime ts;
public String getCorder_lb_id() {
	return corder_lb_id;
}
public void setCorder_lb_id(String corder_lb_id) {
	this.corder_lb_id = corder_lb_id;
}
public String getCorderid() {
	return corderid;
}
public void setCorderid(String corderid) {
	this.corderid = corderid;
}
public String getCorder_bid() {
	return corder_bid;
}
public void setCorder_bid(String corder_bid) {
	this.corder_bid = corder_bid;
}
public String getPk_corp() {
	return pk_corp;
}
public void setPk_corp(String pk_corp) {
	this.pk_corp = pk_corp;
}
public String getCprocessmangid() {
	return cprocessmangid;
}
public void setCprocessmangid(String cprocessmangid) {
	this.cprocessmangid = cprocessmangid;
}
public String getCprocessbaseid() {
	return cprocessbaseid;
}
public void setCprocessbaseid(String cprocessbaseid) {
	this.cprocessbaseid = cprocessbaseid;
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
public UFDouble getNmeasurenum() {
	return nmeasurenum;
}
public void setNmeasurenum(UFDouble nmeasurenum) {
	this.nmeasurenum = nmeasurenum;
}
public UFDouble getNnum() {
	return nnum;
}
public void setNnum(UFDouble nnum) {
	this.nnum = nnum;
}
public UFDouble getNprice() {
	return nprice;
}
public void setNprice(UFDouble nprice) {
	this.nprice = nprice;
}
public UFDouble getNmny() {
	return nmny;
}
public void setNmny(UFDouble nmny) {
	this.nmny = nmny;
}
public String getVproducenum() {
	return vproducenum;
}
public void setVproducenum(String vproducenum) {
	this.vproducenum = vproducenum;
}
public UFDouble getNwastrate() {
	return nwastrate;
}
public void setNwastrate(UFDouble nwastrate) {
	this.nwastrate = nwastrate;
}
public UFDouble getNtaxrate() {
	return ntaxrate;
}
public void setNtaxrate(UFDouble ntaxrate) {
	this.ntaxrate = ntaxrate;
}
public String getPk_measdoc() {
	return pk_measdoc;
}
public void setPk_measdoc(String pk_measdoc) {
	this.pk_measdoc = pk_measdoc;
}
public UFDouble getNunitrate() {
	return nunitrate;
}
public void setNunitrate(UFDouble nunitrate) {
	this.nunitrate = nunitrate;
}
public String getBisww() {
	return bisww;
}
public void setBisww(String bisww) {
	this.bisww = bisww;
}
public UFDouble getNaccumsendnum() {
	return naccumsendnum;
}
public void setNaccumsendnum(UFDouble naccumsendnum) {
	this.naccumsendnum = naccumsendnum;
}
public UFDouble getNaccumwritenum() {
	return naccumwritenum;
}
public void setNaccumwritenum(UFDouble naccumwritenum) {
	this.naccumwritenum = naccumwritenum;
}
public UFDouble getNordernum() {
	return nordernum;
}
public void setNordernum(UFDouble nordernum) {
	this.nordernum = nordernum;
}
public UFDouble getNaccumwastnum() {
	return naccumwastnum;
}
public void setNaccumwastnum(UFDouble naccumwastnum) {
	this.naccumwastnum = naccumwastnum;
}
public UFDouble getForderrowstatus() {
	return forderrowstatus;
}
public void setForderrowstatus(UFDouble forderrowstatus) {
	this.forderrowstatus = forderrowstatus;
}
public String getCordersource() {
	return cordersource;
}
public void setCordersource(String cordersource) {
	this.cordersource = cordersource;
}
public String getCsourcebillid() {
	return csourcebillid;
}
public void setCsourcebillid(String csourcebillid) {
	this.csourcebillid = csourcebillid;
}
public String getCsourcebillrow() {
	return csourcebillrow;
}
public void setCsourcebillrow(String csourcebillrow) {
	this.csourcebillrow = csourcebillrow;
}
public String getCupsourcebilltype() {
	return cupsourcebilltype;
}
public void setCupsourcebilltype(String cupsourcebilltype) {
	this.cupsourcebilltype = cupsourcebilltype;
}
public String getCupsourcebillid() {
	return cupsourcebillid;
}
public void setCupsourcebillid(String cupsourcebillid) {
	this.cupsourcebillid = cupsourcebillid;
}
public String getCupsourcebillrowid() {
	return cupsourcebillrowid;
}
public void setCupsourcebillrowid(String cupsourcebillrowid) {
	this.cupsourcebillrowid = cupsourcebillrowid;
}
public String getVmemo() {
	return vmemo;
}
public void setVmemo(String vmemo) {
	this.vmemo = vmemo;
}
public String getCrowno() {
	return crowno;
}
public void setCrowno(String crowno) {
	this.crowno = crowno;
}
public String getVfree1() {
	return vfree1;
}
public void setVfree1(String vfree1) {
	this.vfree1 = vfree1;
}
public String getVfree2() {
	return vfree2;
}
public void setVfree2(String vfree2) {
	this.vfree2 = vfree2;
}
public String getVfree3() {
	return vfree3;
}
public void setVfree3(String vfree3) {
	this.vfree3 = vfree3;
}
public String getVfree4() {
	return vfree4;
}
public void setVfree4(String vfree4) {
	this.vfree4 = vfree4;
}
public String getVfree5() {
	return vfree5;
}
public void setVfree5(String vfree5) {
	this.vfree5 = vfree5;
}
public String getVdef1() {
	return vdef1;
}
public void setVdef1(String vdef1) {
	this.vdef1 = vdef1;
}
public String getVdef2() {
	return vdef2;
}
public void setVdef2(String vdef2) {
	this.vdef2 = vdef2;
}
public String getVdef3() {
	return vdef3;
}
public void setVdef3(String vdef3) {
	this.vdef3 = vdef3;
}
public String getVdef4() {
	return vdef4;
}
public void setVdef4(String vdef4) {
	this.vdef4 = vdef4;
}
public String getVdef5() {
	return vdef5;
}
public void setVdef5(String vdef5) {
	this.vdef5 = vdef5;
}
public String getVdef6() {
	return vdef6;
}
public void setVdef6(String vdef6) {
	this.vdef6 = vdef6;
}
public String getVdef7() {
	return vdef7;
}
public void setVdef7(String vdef7) {
	this.vdef7 = vdef7;
}
public String getVdef8() {
	return vdef8;
}
public void setVdef8(String vdef8) {
	this.vdef8 = vdef8;
}
public String getVdef9() {
	return vdef9;
}
public void setVdef9(String vdef9) {
	this.vdef9 = vdef9;
}
public String getVdef10() {
	return vdef10;
}
public void setVdef10(String vdef10) {
	this.vdef10 = vdef10;
}
public String getVdef11() {
	return vdef11;
}
public void setVdef11(String vdef11) {
	this.vdef11 = vdef11;
}
public String getVdef12() {
	return vdef12;
}
public void setVdef12(String vdef12) {
	this.vdef12 = vdef12;
}
public String getVdef13() {
	return vdef13;
}
public void setVdef13(String vdef13) {
	this.vdef13 = vdef13;
}
public String getVdef14() {
	return vdef14;
}
public void setVdef14(String vdef14) {
	this.vdef14 = vdef14;
}
public String getVdef15() {
	return vdef15;
}
public void setVdef15(String vdef15) {
	this.vdef15 = vdef15;
}
public String getVdef16() {
	return vdef16;
}
public void setVdef16(String vdef16) {
	this.vdef16 = vdef16;
}
public String getVdef17() {
	return vdef17;
}
public void setVdef17(String vdef17) {
	this.vdef17 = vdef17;
}
public String getVdef18() {
	return vdef18;
}
public void setVdef18(String vdef18) {
	this.vdef18 = vdef18;
}
public String getVdef19() {
	return vdef19;
}
public void setVdef19(String vdef19) {
	this.vdef19 = vdef19;
}
public String getVdef20() {
	return vdef20;
}
public void setVdef20(String vdef20) {
	this.vdef20 = vdef20;
}
public String getPk_defdoc1() {
	return pk_defdoc1;
}
public void setPk_defdoc1(String pk_defdoc1) {
	this.pk_defdoc1 = pk_defdoc1;
}
public String getPk_defdoc2() {
	return pk_defdoc2;
}
public void setPk_defdoc2(String pk_defdoc2) {
	this.pk_defdoc2 = pk_defdoc2;
}
public String getPk_defdoc3() {
	return pk_defdoc3;
}
public void setPk_defdoc3(String pk_defdoc3) {
	this.pk_defdoc3 = pk_defdoc3;
}
public String getPk_defdoc4() {
	return pk_defdoc4;
}
public void setPk_defdoc4(String pk_defdoc4) {
	this.pk_defdoc4 = pk_defdoc4;
}
public String getPk_defdoc5() {
	return pk_defdoc5;
}
public void setPk_defdoc5(String pk_defdoc5) {
	this.pk_defdoc5 = pk_defdoc5;
}
public String getPk_defdoc6() {
	return pk_defdoc6;
}
public void setPk_defdoc6(String pk_defdoc6) {
	this.pk_defdoc6 = pk_defdoc6;
}
public String getPk_defdoc7() {
	return pk_defdoc7;
}
public void setPk_defdoc7(String pk_defdoc7) {
	this.pk_defdoc7 = pk_defdoc7;
}
public String getPk_defdoc8() {
	return pk_defdoc8;
}
public void setPk_defdoc8(String pk_defdoc8) {
	this.pk_defdoc8 = pk_defdoc8;
}
public String getPk_defdoc9() {
	return pk_defdoc9;
}
public void setPk_defdoc9(String pk_defdoc9) {
	this.pk_defdoc9 = pk_defdoc9;
}
public String getPk_defdoc10() {
	return pk_defdoc10;
}
public void setPk_defdoc10(String pk_defdoc10) {
	this.pk_defdoc10 = pk_defdoc10;
}
public String getPk_defdoc11() {
	return pk_defdoc11;
}
public void setPk_defdoc11(String pk_defdoc11) {
	this.pk_defdoc11 = pk_defdoc11;
}
public String getPk_defdoc12() {
	return pk_defdoc12;
}
public void setPk_defdoc12(String pk_defdoc12) {
	this.pk_defdoc12 = pk_defdoc12;
}
public String getPk_defdoc13() {
	return pk_defdoc13;
}
public void setPk_defdoc13(String pk_defdoc13) {
	this.pk_defdoc13 = pk_defdoc13;
}
public String getPk_defdoc14() {
	return pk_defdoc14;
}
public void setPk_defdoc14(String pk_defdoc14) {
	this.pk_defdoc14 = pk_defdoc14;
}
public String getPk_defdoc15() {
	return pk_defdoc15;
}
public void setPk_defdoc15(String pk_defdoc15) {
	this.pk_defdoc15 = pk_defdoc15;
}
public String getPk_defdoc16() {
	return pk_defdoc16;
}
public void setPk_defdoc16(String pk_defdoc16) {
	this.pk_defdoc16 = pk_defdoc16;
}
public String getPk_defdoc17() {
	return pk_defdoc17;
}
public void setPk_defdoc17(String pk_defdoc17) {
	this.pk_defdoc17 = pk_defdoc17;
}
public String getPk_defdoc18() {
	return pk_defdoc18;
}
public void setPk_defdoc18(String pk_defdoc18) {
	this.pk_defdoc18 = pk_defdoc18;
}
public String getPk_defdoc19() {
	return pk_defdoc19;
}
public void setPk_defdoc19(String pk_defdoc19) {
	this.pk_defdoc19 = pk_defdoc19;
}
public String getPk_defdoc20() {
	return pk_defdoc20;
}
public void setPk_defdoc20(String pk_defdoc20) {
	this.pk_defdoc20 = pk_defdoc20;
}
public Integer getDr() {
	return dr;
}
public void setDr(Integer dr) {
	this.dr = dr;
}
public UFDateTime getTs() {
	return ts;
}
public void setTs(UFDateTime ts) {
	this.ts = ts;
}
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
public String getCmangid() {
	return cmangid;
}
public void setCmangid(String cmangid) {
	this.cmangid = cmangid;
}
public String getCbaseid() {
	return cbaseid;
}
public void setCbaseid(String cbaseid) {
	this.cbaseid = cbaseid;
}
public String getVfree0() {
	return vfree0;
}
public void setVfree0(String vfree0) {
	this.vfree0 = vfree0;
}

}
