package nc.bs.pub.action;

import nc.vo.pub.pf.PfUtilActionVO;
import nc.bs.pub.compiler.*;
import nc.vo.pub.compiler.PfParameterVO;
import java.math.*;
import java.util.*;
import nc.vo.pub.lang.*;
import nc.bs.pub.pf.PfUtilTools;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.uap.pf.PFBusinessException;
/**
 * 备注：库存委外加工发料单的保存(基本)
单据动作执行中的动态执行类的动态执行类。
 *
 * 创建日期：(2009-1-5)
 * @author 平台脚本生成
 */
public class N_4F_SAVEBASE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_4F_SAVEBASE 构造子注解。
 */
public N_4F_SAVEBASE() {
	super();
}
/*
* 备注：平台编写规则类
* 接口执行类
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####
//*************从平台取得由该动作传入的入口参数。本方法取得需要保存的VO。***********
Object inCurObject=getVo();
Object inPreObject=getUserObj();
StringBuffer sErr=new StringBuffer();
Object retObj=null;
Object retObjPk=null;
//1,首先检查传入参数类型是否合法，是否为空。
if(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO)) throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("错误：您希望保存的库存委外加工发料类型不匹配"));
if(inCurObject == null)  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("错误：您希望保存的库存委外加工发料没有数据"));
//2,数据合法，把数据转换为库存委外加工发料。
nc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;
nc.vo.ic.pub.bill.GeneralBillVO inPreVO=null;
if(inCurObject !=null)
 inCurVO=(nc.vo.ic.pub.bill.GeneralBillVO)inCurObject;
if(inPreObject !=null) inPreVO=(nc.vo.ic.pub.bill.GeneralBillVO)inPreObject;
inCurObject=null;
inPreObject=null;
//获取平台传入的参数
setParameter("INCURVO",inCurVO);
setParameter("INPREVO",inPreVO);
//#################################################
//方法说明:将锁定存货及序列号解锁
setParameter("userID",inCurVO.getParentVO().getAttributeValue("coperatoridnow"));
setParameter("sDate",getUserDate().toString().substring(0,10));
ArrayList alRet=new ArrayList();
String sBillCode=null;
nc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;
setParameter("IBC",ibc);
try{
//####重要说明：生成的业务组件方法尽量不要进行修改####
//方法说明:检查是否关帐。<可配置>
//目前是根据单据表体业务日期检查。如果根据登录日期检查，请将checkAccountStatus改为checkAccountStatus1
runClass("nc.bs.ic.ic2a3.AccountctrlDMO","checkAccountStatus","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//#############################################################
//方法说明:出库单保存后将锁定存货及序列号解锁
runClass("nc.bs.ic.pub.freeze.FreezeDMO","unLockInv","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&userID:STRING,&sDate:STRING",vo,m_keyHas,m_methodReturnHas);
//##################################################
//该方法<不可配置>
//方法说明:检查是否生成了拣货单
runClass( "nc.bs.ic.pub.check.CheckDMO", "isPicked", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查库存单据时间戳
runClass("nc.bs.ic.pub.check.CheckDMO","checkTimeStamp","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//####重要说明：生成的业务组件方法尽量不要进行修改####
ArrayList listbefore = null;
if(inCurVO.isHaveSourceBill()){
  //方法说明:修改可用量前获取更新信息
  listbefore = (ArrayList)runClass("nc.bs.ic.pub.bill.ICATP","modifyATPBefore","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//方法说明:检查vmi存货时，表头的供应商必填
runClass( "nc.bs.ic.pub.check.CheckInvVendorDMO", "checkVmiVendorInput", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查是否有单据号，如果没有，系统自动产生。
sBillCode=(String)runClass("nc.bs.ic.pub.check.CheckDMO","setBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
//####重要说明：生成的业务组件方法尽量不要进行修改####
//##################################################
//该方法<不可配置>
//方法说明:检查存货批次的失效日期是否一致
runClass("nc.bs.ic.pub.check.CheckDMO","checkInvalidateDate","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//方法说明:检查存货出库是否跟踪入库
runClass("nc.bs.ic.pub.check.CheckBusiDMO","checkRelativeRespondBill","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查库存单据号是否重复
runClass("nc.bs.ic.pub.check.CheckDMO","checkBillCodeFore","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//##################################################
//#################
//校验条码数量和存货数量 该方法可以配置
String sBarcodeCheckErr=(String)runClass("nc.bs.ic.pub.check.CheckBarcodeDMO","checkBarcodeAbsent","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if (sBarcodeCheckErr!=null )
sErr.append(sBarcodeCheckErr);
//#################
//处理单据单据辅数量和库存单位之间的转换，如果没有这种业务，请实施人员注释掉。
runClass("nc.bs.ic.pub.bill.DesassemblyBO","setMeasRateVO","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//单据保存前动作统一处理
runClass("nc.bs.ic.pub.BillActionBase","beforeSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//方法说明:单据保存
retObjPk=runClass("nc.bs.ic.ic213.GeneralHBO","saveBill","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//该方法<可配置>
//方法说明:数量倒挤
Object s2=runClass("nc.bs.ic.pub.bill.GeneralBillBO","makeBothToZeroOnly","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s2!=null)
  sErr.append((String)s2);
//处理拆箱，如果没有这种业务，请实施人员注释掉。
runClass("nc.bs.ic.pub.bill.DesassemblyBO","exeDesassembly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//回写累计出库数量
runClass("nc.bs.ic.pub.RewriteDMO","reWriteCorNum","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//gc回写累计出库数量
runClass("nc.bs.ic.pub.RewriteScDMO","reWriteCorNum","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);


//该方法<不可配置>
//方法说明:检查存货是否已经分配到库存组织
runClass("nc.bs.ic.pub.check.CheckDMO","checkCalBodyInv_New","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查存货主辅数量方向一致,批次负结存,存货负结存
runClass("nc.bs.ic.pub.check.CheckDMO","checkDBL_New","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<可配置>
//方法说明:检查供应商管理存货负结存
//runClass("nc.bs.ic.pub.check.CheckInvVendorDMO","checkInvQtyNewVendor_New","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查存货出库是否跟踪入库负结存
runClass("nc.bs.ic.pub.check.CheckDMO","checkInOutTrace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<可配置>
//方法说明:检查货位容量是否超出
//runClass("nc.bs.ic.pub.check.CheckDMO","checkCargoVolumeOut","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查存货固定货位
runClass("nc.bs.ic.pub.check.CheckDMO","checkFixSpace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<不可配置>
//方法说明:检查存货单独存放
runClass("nc.bs.ic.pub.check.CheckDMO","checkPlaceAlone","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//该方法<可配置>
//方法说明:检查最高库存、最低库存、安全库存、再订购点
Object s1=runClass("nc.bs.ic.pub.check.CheckDMO","checkParam_new","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s1!=null)
 sErr.append((String)s1);
//方法说明:材料出库单回写生产单据出库数量
runClass("nc.bs.ic.pub.RewriteDMO","reWriteMMNewBatch","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//回写生产制造累计报出库数量、辅累计报出库数量
runClass("nc.bs.ic.pub.RewriteDMO","reWriteMMljyfslBatch","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//############################
//插入业务日志，该方法可以配置
setParameter("ERR",sErr.toString());
setParameter("FUN","保存(基本)");
runClass("nc.bs.ic.pub.check.CheckDMO","insertBusinesslog","&INCURVO:nc.vo.pub.AggregatedValueObject,&ERR:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
if(inCurVO.isHaveSourceBill()){
//方法说明:可用量及时检查,可配置
        runClass("nc.bs.ic.pub.bill.ICATP","checkAtpInstantly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//结果返回前必须检查类型是否匹配
if(retObjPk != null && !(retObjPk instanceof ArrayList))  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("错误：保存动作的返回值类型错误。"));
if(sErr.toString().trim().length()==0)
 alRet.add(null);
else
  alRet.add(sErr.toString());
alRet.add(retObjPk);
//单据保存后动作统一处理
runClass("nc.bs.ic.pub.BillActionBase","afterSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//单据号修改后退号处理
runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCodeWhenUpdate","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//####重要说明：生成的业务组件方法尽量不要进行修改####
if(inCurVO.isHaveSourceBill()){
  //设置可用量参数
  if(listbefore!=null)
     setParameter("LISTBEFORE",listbefore);
  //方法说明:修改可用量
  runClass("nc.bs.ic.pub.bill.ICATP","modifyATPAfter","&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList",vo,m_keyHas,m_methodReturnHas);
}
//添加小型单据VO向前台传递 
nc.vo.ic.pub.smallbill.SMGeneralBillVO smbillvo = inCurVO.getSmallBillVO();
alRet.add(smbillvo);
inCurVO=null;
inPreVO=null;
}catch(Exception e){
//############################
//插入业务日志，该方法可以配置
setParameter("EXC",e.getMessage());
setParameter("FUN","保存基本");
runClass("nc.bs.ic.pub.check.CheckBO","insertBusinessExceptionlog","&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
if(sBillCode!=null){
  if(e instanceof nc.vo.pub.BusinessException){
   if(((nc.vo.pub.BusinessException)e).getCause()== null ||(((nc.vo.pub.BusinessException)e).getCause()!= null && !(((nc.vo.pub.BusinessException)e).getCause() instanceof nc.vo.ic.pub.exp.BillCodeNotUnique)))
               runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
 }else{
    if(!(e instanceof nc.vo.ic.pub.exp.BillCodeNotUnique))
      runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
 }
}
      if (e instanceof nc.vo.pub.BusinessException)
     throw (nc.vo.pub.BusinessException) e;
    else
      throw new nc.vo.pub.BusinessException("Remote Call", e);
}
return alRet;
//************************************************************************
} catch (Exception ex) {
	if (ex instanceof BusinessException)
		throw (BusinessException) ex;
	else 
    throw new PFBusinessException(ex.getMessage(), ex);
}
}
/*
* 备注：平台编写原始脚本
*/
public String getCodeRemark(){
	return "	//####本脚本必须含有返回值,返回DLG和PNL的组件不允许有返回值####\n//*************从平台取得由该动作传入的入口参数。本方法取得需要保存的VO。***********\nObject inCurObject=getVo();\nObject inPreObject=getUserObj();\nStringBuffer sErr=new StringBuffer();\nObject retObj=null;\nObject retObjPk=null;\n//1,首先检查传入参数类型是否合法，是否为空。\nif(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO)) throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"错误：您希望保存的库存委外加工发料类型不匹配\"));\nif(inCurObject == null)  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"错误：您希望保存的库存委外加工发料没有数据\"));\n//2,数据合法，把数据转换为库存委外加工发料。\nnc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;\nnc.vo.ic.pub.bill.GeneralBillVO inPreVO=null;\nif(inCurObject !=null)\n inCurVO=(nc.vo.ic.pub.bill.GeneralBillVO)inCurObject;\nif(inPreObject !=null) inPreVO=(nc.vo.ic.pub.bill.GeneralBillVO)inPreObject;\ninCurObject=null;\ninPreObject=null;\n//获取平台传入的参数\nsetParameter(\"INCURVO\",inCurVO);\nsetParameter(\"INPREVO\",inPreVO);\n//#################################################\n//方法说明:将锁定存货及序列号解锁\nsetParameter(\"userID\",inCurVO.getParentVO().getAttributeValue(\"coperatoridnow\"));\nsetParameter(\"sDate\",getUserDate().toString().substring(0,10));\nArrayList alRet=new ArrayList();\nString sBillCode=null;\nnc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;\nsetParameter(\"IBC\",ibc);\ntry{\n//####重要说明：生成的业务组件方法尽量不要进行修改####\n//方法说明:检查是否关帐。<可配置>\n//目前是根据单据表体业务日期检查。如果根据登录日期检查，请将checkAccountStatus改为checkAccountStatus1\nrunClassCom@\"nc.bs.ic.ic2a3.AccountctrlDMO\",\"checkAccountStatus\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//#############################################################\n//方法说明:出库单保存后将锁定存货及序列号解锁\nrunClassCom@\"nc.bs.ic.pub.freeze.FreezeDMO\",\"unLockInv\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&userID:STRING,&sDate:STRING\"@;\n//##################################################\n//该方法<不可配置>\n//方法说明:检查是否生成了拣货单\nrunClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"isPicked\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<不可配置>\n//方法说明:检查库存单据时间戳\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkTimeStamp\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//####重要说明：生成的业务组件方法尽量不要进行修改####\nArrayList listbefore = null;\nif(inCurVO.isHaveSourceBill()){\n  //方法说明:修改可用量前获取更新信息\n  listbefore = (ArrayList)runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATPBefore\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//方法说明:检查vmi存货时，表头的供应商必填\nrunClassCom@ \"nc.bs.ic.pub.check.CheckInvVendorDMO\", \"checkVmiVendorInput\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<不可配置>\n//方法说明:检查是否有单据号，如果没有，系统自动产生。\nsBillCode=(String)runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"setBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n//####重要说明：生成的业务组件方法尽量不要进行修改####\n//##################################################\n//该方法<不可配置>\n//方法说明:检查存货批次的失效日期是否一致\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInvalidateDate\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//方法说明:检查存货出库是否跟踪入库\nrunClassCom@\"nc.bs.ic.pub.check.CheckBusiDMO\",\"checkRelativeRespondBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<不可配置>\n//方法说明:检查库存单据号是否重复\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkBillCodeFore\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//##################################################\n//#################\n//校验条码数量和存货数量 该方法可以配置\nString sBarcodeCheckErr=(String)runClassCom@\"nc.bs.ic.pub.check.CheckBarcodeDMO\",\"checkBarcodeAbsent\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif (sBarcodeCheckErr!=null )\nsErr.append(sBarcodeCheckErr);\n//#################\n//处理单据单据辅数量和库存单位之间的转换，如果没有这种业务，请实施人员注释掉。\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"setMeasRateVO\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//单据保存前动作统一处理\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"beforeSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n" +
			"//方法说明:单据保存\nretObjPk=runClassCom@\"nc.bs.ic.ic213.GeneralHBO\",\"saveBill\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//该方法<可配置>\n//方法说明:数量倒挤\nObject s2=runClassCom@\"nc.bs.ic.pub.bill.GeneralBillBO\",\"makeBothToZeroOnly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s2!=null)\n  sErr.append((String)s2);\n//处理拆箱，如果没有这种业务，请实施人员注释掉。\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"exeDesassembly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//回写累计出库数量\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteCorNum\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//该方法<不可配置>\n//方法说明:检查存货是否已经分配到库存组织\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCalBodyInv_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<不可配置>\n//方法说明:检查存货主辅数量方向一致,批次负结存,存货负结存\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkDBL_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<可配置>\n//方法说明:检查供应商管理存货负结存\n//runClassCom@\"nc.bs.ic.pub.check.CheckInvVendorDMO\",\"checkInvQtyNewVendor_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<不可配置>\n//方法说明:检查存货出库是否跟踪入库负结存\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInOutTrace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<可配置>\n//方法说明:检查货位容量是否超出\n//runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCargoVolumeOut\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//该方法<不可配置>\n//方法说明:检查存货固定货位\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkFixSpace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<不可配置>\n//方法说明:检查存货单独存放\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkPlaceAlone\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//该方法<可配置>\n//方法说明:检查最高库存、最低库存、安全库存、再订购点\nObject s1=runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkParam_new\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s1!=null)\n sErr.append((String)s1);\n//方法说明:材料出库单回写生产单据出库数量\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteMMNewBatch\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//回写生产制造累计报出库数量、辅累计报出库数量\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteMMljyfslBatch\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//############################\n//插入业务日志，该方法可以配置\nsetParameter(\"ERR\",sErr.toString());\nsetParameter(\"FUN\",\"保存(基本)\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"insertBusinesslog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&ERR:String,&FUN:String\"@;\n//############################\nif(inCurVO.isHaveSourceBill()){\n//方法说明:可用量及时检查,可配置\n        runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"checkAtpInstantly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//结果返回前必须检查类型是否匹配\nif(retObjPk != null && !(retObjPk instanceof ArrayList))  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"错误：保存动作的返回值类型错误。\"));\nif(sErr.toString().trim().length()==0)\n alRet.add(null);\nelse\n  alRet.add(sErr.toString());\nalRet.add(retObjPk);\n//单据保存后动作统一处理\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"afterSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//单据号修改后退号处理\nrunClasscom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCodeWhenUpdate\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//####重要说明：生成的业务组件方法尽量不要进行修改####\nif(inCurVO.isHaveSourceBill()){\n  //设置可用量参数\n  if(listbefore!=null)\n     setParameter(\"LISTBEFORE\",listbefore);\n  //方法说明:修改可用量\n  runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATPAfter\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList\"@;\n}\n//添加小型单据VO向前台传递 \nnc.vo.ic.pub.smallbill.SMGeneralBillVO smbillvo = inCurVO.getSmallBillVO();\nalRet.add(smbillvo);\ninCurVO=null;\ninPreVO=null;\n}catch(Exception e){\n//############################\n//插入业务日志，该方法可以配置\nsetParameter(\"EXC\",e.getMessage());\nsetParameter(\"FUN\",\"保存基本\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckBO\",\"insertBusinessExceptionlog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String\"@;\n//############################\nif(sBillCode!=null){\n  if(e instanceof nc.vo.pub.BusinessException){\n   if(((nc.vo.pub.BusinessException)e).getCause()== null ||(((nc.vo.pub.BusinessException)e).getCause()!= null && !(((nc.vo.pub.BusinessException)e).getCause() instanceof nc.vo.ic.pub.exp.BillCodeNotUnique)))\n               runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n }else{\n    if(!(e instanceof nc.vo.ic.pub.exp.BillCodeNotUnique))\n      runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n }\n}\n      if (e instanceof nc.vo.pub.BusinessException)\n     throw (nc.vo.pub.BusinessException) e;\n    else\n      throw new nc.vo.pub.BusinessException(\"Remote Call\", e);\n}\nreturn alRet;\n//************************************************************************\n";}
/*
* 备注：设置脚本变量的HAS
*/
private void setParameter(String key,Object val)	{
	if (m_keyHas==null){
		m_keyHas=new Hashtable();
	}
	if (val!=null)	{
		m_keyHas.put(key,val);
	}
}
}
