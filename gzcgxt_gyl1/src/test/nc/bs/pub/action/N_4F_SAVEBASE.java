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
 * ��ע�����ί��ӹ����ϵ��ı���(����)
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2009-1-5)
 * @author ƽ̨�ű�����
 */
public class N_4F_SAVEBASE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_4F_SAVEBASE ������ע�⡣
 */
public N_4F_SAVEBASE() {
	super();
}
/*
* ��ע��ƽ̨��д������
* �ӿ�ִ����
*/
public Object runComClass(PfParameterVO vo) throws BusinessException {
try{
	super.m_tmpVo=vo;
	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####
//*************��ƽ̨ȡ���ɸö����������ڲ�����������ȡ����Ҫ�����VO��***********
Object inCurObject=getVo();
Object inPreObject=getUserObj();
StringBuffer sErr=new StringBuffer();
Object retObj=null;
Object retObjPk=null;
//1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�
if(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO)) throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("������ϣ������Ŀ��ί��ӹ��������Ͳ�ƥ��"));
if(inCurObject == null)  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("������ϣ������Ŀ��ί��ӹ�����û������"));
//2,���ݺϷ���������ת��Ϊ���ί��ӹ����ϡ�
nc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;
nc.vo.ic.pub.bill.GeneralBillVO inPreVO=null;
if(inCurObject !=null)
 inCurVO=(nc.vo.ic.pub.bill.GeneralBillVO)inCurObject;
if(inPreObject !=null) inPreVO=(nc.vo.ic.pub.bill.GeneralBillVO)inPreObject;
inCurObject=null;
inPreObject=null;
//��ȡƽ̨����Ĳ���
setParameter("INCURVO",inCurVO);
setParameter("INPREVO",inPreVO);
//#################################################
//����˵��:��������������кŽ���
setParameter("userID",inCurVO.getParentVO().getAttributeValue("coperatoridnow"));
setParameter("sDate",getUserDate().toString().substring(0,10));
ArrayList alRet=new ArrayList();
String sBillCode=null;
nc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;
setParameter("IBC",ibc);
try{
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:����Ƿ���ʡ�<������>
//Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1
runClass("nc.bs.ic.ic2a3.AccountctrlDMO","checkAccountStatus","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//#############################################################
//����˵��:���ⵥ�����������������кŽ���
runClass("nc.bs.ic.pub.freeze.FreezeDMO","unLockInv","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&userID:STRING,&sDate:STRING",vo,m_keyHas,m_methodReturnHas);
//##################################################
//�÷���<��������>
//����˵��:����Ƿ������˼����
runClass( "nc.bs.ic.pub.check.CheckDMO", "isPicked", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����浥��ʱ���
runClass("nc.bs.ic.pub.check.CheckDMO","checkTimeStamp","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
ArrayList listbefore = null;
if(inCurVO.isHaveSourceBill()){
  //����˵��:�޸Ŀ�����ǰ��ȡ������Ϣ
  listbefore = (ArrayList)runClass("nc.bs.ic.pub.bill.ICATP","modifyATPBefore","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//����˵��:���vmi���ʱ����ͷ�Ĺ�Ӧ�̱���
runClass( "nc.bs.ic.pub.check.CheckInvVendorDMO", "checkVmiVendorInput", "&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����Ƿ��е��ݺţ����û�У�ϵͳ�Զ�������
sBillCode=(String)runClass("nc.bs.ic.pub.check.CheckDMO","setBillCode","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//##################################################
//�÷���<��������>
//����˵��:��������ε�ʧЧ�����Ƿ�һ��
runClass("nc.bs.ic.pub.check.CheckDMO","checkInvalidateDate","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//����˵��:����������Ƿ�������
runClass("nc.bs.ic.pub.check.CheckBusiDMO","checkRelativeRespondBill","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����浥�ݺ��Ƿ��ظ�
runClass("nc.bs.ic.pub.check.CheckDMO","checkBillCodeFore","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//##################################################
//#################
//У�����������ʹ������ �÷�����������
String sBarcodeCheckErr=(String)runClass("nc.bs.ic.pub.check.CheckBarcodeDMO","checkBarcodeAbsent","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if (sBarcodeCheckErr!=null )
sErr.append(sBarcodeCheckErr);
//#################
//�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���
runClass("nc.bs.ic.pub.bill.DesassemblyBO","setMeasRateVO","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//���ݱ���ǰ����ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","beforeSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//����˵��:���ݱ���
retObjPk=runClass("nc.bs.ic.ic213.GeneralHBO","saveBill","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:��������
Object s2=runClass("nc.bs.ic.pub.bill.GeneralBillBO","makeBothToZeroOnly","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s2!=null)
  sErr.append((String)s2);
//������䣬���û������ҵ����ʵʩ��Աע�͵���
runClass("nc.bs.ic.pub.bill.DesassemblyBO","exeDesassembly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//��д�ۼƳ�������
runClass("nc.bs.ic.pub.RewriteDMO","reWriteCorNum","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//gc��д�ۼƳ�������
runClass("nc.bs.ic.pub.RewriteScDMO","reWriteCorNum","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);


//�÷���<��������>
//����˵��:������Ƿ��Ѿ����䵽�����֯
runClass("nc.bs.ic.pub.check.CheckDMO","checkCalBodyInv_New","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:�����������������һ��,���θ����,��������
runClass("nc.bs.ic.pub.check.CheckDMO","checkDBL_New","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:��鹩Ӧ�̹����������
//runClass("nc.bs.ic.pub.check.CheckInvVendorDMO","checkInvQtyNewVendor_New","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����������Ƿ������⸺���
runClass("nc.bs.ic.pub.check.CheckDMO","checkInOutTrace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:����λ�����Ƿ񳬳�
//runClass("nc.bs.ic.pub.check.CheckDMO","checkCargoVolumeOut","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:������̶���λ
runClass("nc.bs.ic.pub.check.CheckDMO","checkFixSpace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:������������
runClass("nc.bs.ic.pub.check.CheckDMO","checkPlaceAlone","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����
Object s1=runClass("nc.bs.ic.pub.check.CheckDMO","checkParam_new","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
if(s1!=null)
 sErr.append((String)s1);
//����˵��:���ϳ��ⵥ��д�������ݳ�������
runClass("nc.bs.ic.pub.RewriteDMO","reWriteMMNewBatch","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//��д���������ۼƱ��������������ۼƱ���������
runClass("nc.bs.ic.pub.RewriteDMO","reWriteMMljyfslBatch","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//############################
//����ҵ����־���÷�����������
setParameter("ERR",sErr.toString());
setParameter("FUN","����(����)");
runClass("nc.bs.ic.pub.check.CheckDMO","insertBusinesslog","&INCURVO:nc.vo.pub.AggregatedValueObject,&ERR:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
if(inCurVO.isHaveSourceBill()){
//����˵��:��������ʱ���,������
        runClass("nc.bs.ic.pub.bill.ICATP","checkAtpInstantly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//�������ǰ�����������Ƿ�ƥ��
if(retObjPk != null && !(retObjPk instanceof ArrayList))  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("���󣺱��涯���ķ���ֵ���ʹ���"));
if(sErr.toString().trim().length()==0)
 alRet.add(null);
else
  alRet.add(sErr.toString());
alRet.add(retObjPk);
//���ݱ������ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","afterSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//���ݺ��޸ĺ��˺Ŵ���
runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCodeWhenUpdate","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
if(inCurVO.isHaveSourceBill()){
  //���ÿ���������
  if(listbefore!=null)
     setParameter("LISTBEFORE",listbefore);
  //����˵��:�޸Ŀ�����
  runClass("nc.bs.ic.pub.bill.ICATP","modifyATPAfter","&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList",vo,m_keyHas,m_methodReturnHas);
}
//���С�͵���VO��ǰ̨���� 
nc.vo.ic.pub.smallbill.SMGeneralBillVO smbillvo = inCurVO.getSmallBillVO();
alRet.add(smbillvo);
inCurVO=null;
inPreVO=null;
}catch(Exception e){
//############################
//����ҵ����־���÷�����������
setParameter("EXC",e.getMessage());
setParameter("FUN","�������");
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
* ��ע��ƽ̨��дԭʼ�ű�
*/
public String getCodeRemark(){
	return "	//####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n//*************��ƽ̨ȡ���ɸö����������ڲ�����������ȡ����Ҫ�����VO��***********\nObject inCurObject=getVo();\nObject inPreObject=getUserObj();\nStringBuffer sErr=new StringBuffer();\nObject retObj=null;\nObject retObjPk=null;\n//1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�\nif(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO)) throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"������ϣ������Ŀ��ί��ӹ��������Ͳ�ƥ��\"));\nif(inCurObject == null)  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"������ϣ������Ŀ��ί��ӹ�����û������\"));\n//2,���ݺϷ���������ת��Ϊ���ί��ӹ����ϡ�\nnc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;\nnc.vo.ic.pub.bill.GeneralBillVO inPreVO=null;\nif(inCurObject !=null)\n inCurVO=(nc.vo.ic.pub.bill.GeneralBillVO)inCurObject;\nif(inPreObject !=null) inPreVO=(nc.vo.ic.pub.bill.GeneralBillVO)inPreObject;\ninCurObject=null;\ninPreObject=null;\n//��ȡƽ̨����Ĳ���\nsetParameter(\"INCURVO\",inCurVO);\nsetParameter(\"INPREVO\",inPreVO);\n//#################################################\n//����˵��:��������������кŽ���\nsetParameter(\"userID\",inCurVO.getParentVO().getAttributeValue(\"coperatoridnow\"));\nsetParameter(\"sDate\",getUserDate().toString().substring(0,10));\nArrayList alRet=new ArrayList();\nString sBillCode=null;\nnc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;\nsetParameter(\"IBC\",ibc);\ntry{\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:����Ƿ���ʡ�<������>\n//Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1\nrunClassCom@\"nc.bs.ic.ic2a3.AccountctrlDMO\",\"checkAccountStatus\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//#############################################################\n//����˵��:���ⵥ�����������������кŽ���\nrunClassCom@\"nc.bs.ic.pub.freeze.FreezeDMO\",\"unLockInv\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&userID:STRING,&sDate:STRING\"@;\n//##################################################\n//�÷���<��������>\n//����˵��:����Ƿ������˼����\nrunClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"isPicked\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����浥��ʱ���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkTimeStamp\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\nArrayList listbefore = null;\nif(inCurVO.isHaveSourceBill()){\n  //����˵��:�޸Ŀ�����ǰ��ȡ������Ϣ\n  listbefore = (ArrayList)runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATPBefore\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//����˵��:���vmi���ʱ����ͷ�Ĺ�Ӧ�̱���\nrunClassCom@ \"nc.bs.ic.pub.check.CheckInvVendorDMO\", \"checkVmiVendorInput\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����Ƿ��е��ݺţ����û�У�ϵͳ�Զ�������\nsBillCode=(String)runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"setBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//##################################################\n//�÷���<��������>\n//����˵��:��������ε�ʧЧ�����Ƿ�һ��\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInvalidateDate\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//����˵��:����������Ƿ�������\nrunClassCom@\"nc.bs.ic.pub.check.CheckBusiDMO\",\"checkRelativeRespondBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����浥�ݺ��Ƿ��ظ�\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkBillCodeFore\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//##################################################\n//#################\n//У�����������ʹ������ �÷�����������\nString sBarcodeCheckErr=(String)runClassCom@\"nc.bs.ic.pub.check.CheckBarcodeDMO\",\"checkBarcodeAbsent\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif (sBarcodeCheckErr!=null )\nsErr.append(sBarcodeCheckErr);\n//#################\n//�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"setMeasRateVO\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//���ݱ���ǰ����ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"beforeSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n" +
			"//����˵��:���ݱ���\nretObjPk=runClassCom@\"nc.bs.ic.ic213.GeneralHBO\",\"saveBill\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<������>\n//����˵��:��������\nObject s2=runClassCom@\"nc.bs.ic.pub.bill.GeneralBillBO\",\"makeBothToZeroOnly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s2!=null)\n  sErr.append((String)s2);\n//������䣬���û������ҵ����ʵʩ��Աע�͵���\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"exeDesassembly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//��д�ۼƳ�������\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteCorNum\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<��������>\n//����˵��:������Ƿ��Ѿ����䵽�����֯\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCalBodyInv_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:�����������������һ��,���θ����,��������\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkDBL_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:��鹩Ӧ�̹����������\n//runClassCom@\"nc.bs.ic.pub.check.CheckInvVendorDMO\",\"checkInvQtyNewVendor_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����������Ƿ������⸺���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInOutTrace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:����λ�����Ƿ񳬳�\n//runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCargoVolumeOut\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<��������>\n//����˵��:������̶���λ\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkFixSpace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:������������\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkPlaceAlone\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����\nObject s1=runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkParam_new\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\nif(s1!=null)\n sErr.append((String)s1);\n//����˵��:���ϳ��ⵥ��д�������ݳ�������\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteMMNewBatch\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//��д���������ۼƱ��������������ۼƱ���������\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteMMljyfslBatch\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"ERR\",sErr.toString());\nsetParameter(\"FUN\",\"����(����)\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"insertBusinesslog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&ERR:String,&FUN:String\"@;\n//############################\nif(inCurVO.isHaveSourceBill()){\n//����˵��:��������ʱ���,������\n        runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"checkAtpInstantly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//�������ǰ�����������Ƿ�ƥ��\nif(retObjPk != null && !(retObjPk instanceof ArrayList))  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"���󣺱��涯���ķ���ֵ���ʹ���\"));\nif(sErr.toString().trim().length()==0)\n alRet.add(null);\nelse\n  alRet.add(sErr.toString());\nalRet.add(retObjPk);\n//���ݱ������ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"afterSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//���ݺ��޸ĺ��˺Ŵ���\nrunClasscom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCodeWhenUpdate\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\nif(inCurVO.isHaveSourceBill()){\n  //���ÿ���������\n  if(listbefore!=null)\n     setParameter(\"LISTBEFORE\",listbefore);\n  //����˵��:�޸Ŀ�����\n  runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATPAfter\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList\"@;\n}\n//���С�͵���VO��ǰ̨���� \nnc.vo.ic.pub.smallbill.SMGeneralBillVO smbillvo = inCurVO.getSmallBillVO();\nalRet.add(smbillvo);\ninCurVO=null;\ninPreVO=null;\n}catch(Exception e){\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"EXC\",e.getMessage());\nsetParameter(\"FUN\",\"�������\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckBO\",\"insertBusinessExceptionlog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String\"@;\n//############################\nif(sBillCode!=null){\n  if(e instanceof nc.vo.pub.BusinessException){\n   if(((nc.vo.pub.BusinessException)e).getCause()== null ||(((nc.vo.pub.BusinessException)e).getCause()!= null && !(((nc.vo.pub.BusinessException)e).getCause() instanceof nc.vo.ic.pub.exp.BillCodeNotUnique)))\n               runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n }else{\n    if(!(e instanceof nc.vo.ic.pub.exp.BillCodeNotUnique))\n      runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCode\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\n }\n}\n      if (e instanceof nc.vo.pub.BusinessException)\n     throw (nc.vo.pub.BusinessException) e;\n    else\n      throw new nc.vo.pub.BusinessException(\"Remote Call\", e);\n}\nreturn alRet;\n//************************************************************************\n";}
/*
* ��ע�����ýű�������HAS
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
