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
 * ��ע�����ί��ӹ����ϵ���ɾ��
���ݶ���ִ���еĶ�ִ̬����Ķ�ִ̬���ࡣ
 *
 * �������ڣ�(2008-6-28)
 * @author ƽ̨�ű�����
 */
public class N_4F_DELETE extends AbstractCompiler2 {
private java.util.Hashtable m_methodReturnHas=new java.util.Hashtable();
private Hashtable m_keyHas=null;
/**
 * N_4F_DELETE ������ע�⡣
 */
public N_4F_DELETE() {
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
Object inCurObject=getVos();
Object retObj=null;
StringBuffer sErr=new StringBuffer();
//1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�
if(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO[])) throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("������ϣ������Ŀ��ί��ӹ��������Ͳ�ƥ��"));
if(inCurObject == null)  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("������ϣ������Ŀ��ί��ӹ�����û������"));
//2,���ݺϷ���������ת��Ϊ���ί��ӹ����⡣
nc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;
nc.vo.ic.pub.bill.GeneralBillVO[] inCurVOs=(nc.vo.ic.pub.bill.GeneralBillVO[])inCurObject;
inCurObject=null;
for(int i=0;i<inCurVOs.length;i++){
   inCurVO=inCurVOs[i];
 if(inCurVO!=null&&inCurVO.getHeaderVO()!=null)
    inCurVO.getHeaderVO().setStatus(nc.vo.pub.VOStatus.DELETED);
//��ȡƽ̨����Ĳ���
setParameter("INCURVO",inCurVO);
Object alLockedPK=null;
try{
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:����Ƿ���ʡ�<������>
//Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1
runClass("nc.bs.ic.ic2a3.AccountctrlDMO","checkAccountStatus","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//#############################################################
//����˵��:������ⵥ�ݼ�ҵ����
alLockedPK=runClass("nc.bs.ic.pub.bill.ICLockBO","lockBill","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
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
//����˵��:����������Ƿ�������
runClass("nc.bs.ic.pub.check.CheckBusiDMO","checkRelativeRespondBill","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:��鹩Ӧ�̹����������
//setParameter("INPREVO",null);
//runClass("nc.bs.ic.pub.check.CheckInvVendorDMO","checkInvQtyNegativeNewVendor","&INPREVO:nc.vo.pub.AggregatedValueObject,&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//##################################################
//�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���
runClass("nc.bs.ic.pub.bill.DesassemblyBO","setMeasRateVO","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//���ݱ���ǰ����ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","beforeSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//����˵��:����ɾ��
runClass("nc.bs.ic.ic213.GeneralHBO","deleteBill","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//������䣬���û������ҵ����ʵʩ��Աע�͵���
runClass("nc.bs.ic.pub.bill.DesassemblyBO","exeDesassembly","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//����˵������д�ۼƳ�������
setParameter("CURVO",null);
setParameter("PREVO",inCurVO);
runClass("nc.bs.ic.pub.RewriteDMO","reWriteCorNum","&CURVO:nc.vo.ic.pub.bill.GeneralBillVO,&PREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//gc��д�ۼƳ�������
runClass("nc.bs.ic.pub.RewriteScDMO","reWriteCorNumForDelete","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);

//����˵��:ɾ���������
setParameter("hid",inCurVO.getHeaderVO().getPrimaryKey());
runClass( "nc.bs.ic.ic2a1.PickBillDMO", "deleteItemsByOuthid", "&hid:String",vo,m_keyHas,m_methodReturnHas);
setParameter("RWINPREVO",null);
//ע�����˳����Ϊ��ɾ�����������Խ���ǰ��VO��Ϊoldvo����
runClass("nc.bs.ic.pub.RewriteDMO","reWriteMMNewBatch","&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO,&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//ע�����˳����Ϊ��ɾ�����������Խ���ǰ��VO��Ϊoldvo����
runClass("nc.bs.ic.pub.RewriteDMO","reWriteMMljyfslBatch","&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO,&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:�����������������һ��,���θ����,��������
runClass("nc.bs.ic.pub.check.CheckDMO","checkDBL_New","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<��������>
//����˵��:����������Ƿ������⸺���
runClass("nc.bs.ic.pub.check.CheckDMO","checkInOutTrace","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:����λ�����Ƿ񳬳�
//runClass("nc.bs.ic.pub.check.CheckDMO","checkCargoVolumeOut","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//�÷���<������>
//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����
Object s1=runClass("nc.bs.ic.pub.check.CheckDMO","checkParam_new","&INCURVO:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
  if(s1!=null)
    sErr.append((String)s1);
//�������ǰ�����������Ƿ�ƥ��
//if(retObj != null && !(retObj instanceof ArrayList))  throw new nc.vo.pub.BusinessException("Remote Call",new nc.vo.pub.BusinessException("����ɾ�������ķ���ֵ���ʹ���"));
//�÷���<��������>
//����˵��:����ɾ��ʱ�˻ص��ݺ�
nc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;
setParameter("IBC",ibc);
runClass("nc.bs.ic.pub.check.CheckDMO","returnBillCodeWhenDelete","&IBC:nc.vo.scm.pub.IBillCode",vo,m_keyHas,m_methodReturnHas);
if(inCurVO.isHaveSourceBill()){
//����˵��:��������ʱ���,������
setParameter("INPREVOATP",null);
runClass("nc.bs.ic.pub.bill.ICATP","checkAtpInstantly","&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVOATP:nc.vo.pub.AggregatedValueObject",vo,m_keyHas,m_methodReturnHas);
}
//���ݱ������ͳһ����
runClass("nc.bs.ic.pub.BillActionBase","afterSave","&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO",vo,m_keyHas,m_methodReturnHas);
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
if(inCurVO.isHaveSourceBill()){
  //���ÿ���������
  if(listbefore!=null)
     setParameter("LISTBEFORE",listbefore);
  //����˵��:�޸Ŀ�����
  runClass("nc.bs.ic.pub.bill.ICATP","modifyATPAfter","&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList",vo,m_keyHas,m_methodReturnHas);
}
}catch(Exception e){
//############################
//����ҵ����־���÷�����������
setParameter("EXC",e.getMessage());
setParameter("FUN","ɾ��");
runClass("nc.bs.ic.pub.check.CheckBO","insertBusinessExceptionlog","&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//###########################
     if (e instanceof nc.vo.pub.BusinessException)
     throw (nc.vo.pub.BusinessException) e;
    else
      throw new nc.vo.pub.BusinessException("Remote Call", e);
}
finally{
//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####
//����˵��:������ⵥ�ݽ�ҵ����
setParameter("ALLPK",(ArrayList)alLockedPK);
if(alLockedPK!=null)
runClass("nc.bs.ic.pub.bill.ICLockBO","unlockBill","&INCURVO:nc.vo.pub.AggregatedValueObject,&ALLPK:ArrayList",vo,m_keyHas,m_methodReturnHas);
//##################################################
}
}
//############################
//����ҵ����־���÷�����������
setParameter("INCURVOS",inCurVOs);
setParameter("ERR",sErr.toString());
setParameter("FUN","ɾ��");
runClass("nc.bs.ic.pub.check.CheckDMO","insertBusinesslog","&INCURVOS:nc.vo.ic.pub.bill.GeneralBillVO[],&ERR:String,&FUN:String",vo,m_keyHas,m_methodReturnHas);
//############################
inCurVO=null;
ArrayList alRet=new ArrayList();
if(sErr.toString().trim().length()==0)
 alRet.add(null);
else
  alRet.add(sErr.toString());
//alRet.add(retObj);
return retObj;
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
  return "  //####���ű����뺬�з���ֵ,����DLG��PNL������������з���ֵ####\n//*************��ƽ̨ȡ���ɸö����������ڲ�����������ȡ����Ҫ�����VO��***********\nObject inCurObject=getVos();\nObject retObj=null;\nStringBuffer sErr=new StringBuffer();\n//1,���ȼ�鴫����������Ƿ�Ϸ����Ƿ�Ϊ�ա�\nif(!(inCurObject instanceof  nc.vo.ic.pub.bill.GeneralBillVO[])) throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"������ϣ������Ŀ��ί��ӹ��������Ͳ�ƥ��\"));\nif(inCurObject == null)  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"������ϣ������Ŀ��ί��ӹ�����û������\"));\n//2,���ݺϷ���������ת��Ϊ���ί��ӹ����⡣\nnc.vo.ic.pub.bill.GeneralBillVO inCurVO=null;\nnc.vo.ic.pub.bill.GeneralBillVO[] inCurVOs=(nc.vo.ic.pub.bill.GeneralBillVO[])inCurObject;\ninCurObject=null;\nfor(int i=0;i<inCurVOs.length;i++){\n   inCurVO=inCurVOs[i];\n if(inCurVO!=null&&inCurVO.getHeaderVO()!=null)\n    inCurVO.getHeaderVO().setStatus(nc.vo.pub.VOStatus.DELETED);\n//��ȡƽ̨����Ĳ���\nsetParameter(\"INCURVO\",inCurVO);\nObject alLockedPK=null;\ntry{\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:����Ƿ���ʡ�<������>\n//Ŀǰ�Ǹ��ݵ��ݱ���ҵ�����ڼ�顣������ݵ�¼���ڼ�飬�뽫checkAccountStatus��ΪcheckAccountStatus1\nrunClassCom@\"nc.bs.ic.ic2a3.AccountctrlDMO\",\"checkAccountStatus\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//#############################################################\n//����˵��:������ⵥ�ݼ�ҵ����\nalLockedPK=runClassCom@\"nc.bs.ic.pub.bill.ICLockBO\",\"lockBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//##################################################\n//�÷���<��������>\n//����˵��:����Ƿ������˼����\nrunClassCom@ \"nc.bs.ic.pub.check.CheckDMO\", \"isPicked\", \"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����浥��ʱ���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkTimeStamp\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\nArrayList listbefore = null;\nif(inCurVO.isHaveSourceBill()){\n  //����˵��:�޸Ŀ�����ǰ��ȡ������Ϣ\n  listbefore = (ArrayList)runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATPBefore\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n}\n//����˵��:����������Ƿ�������\nrunClassCom@\"nc.bs.ic.pub.check.CheckBusiDMO\",\"checkRelativeRespondBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:��鹩Ӧ�̹����������\n//setParameter(\"INPREVO\",null);\n//runClassCom@\"nc.bs.ic.pub.check.CheckInvVendorDMO\",\"checkInvQtyNegativeNewVendor\",\"&INPREVO:nc.vo.pub.AggregatedValueObject,&INCURVO:nc.vo.pub.AggregatedValueObject\"@;" +
      "\n//##################################################\n//�����ݵ��ݸ������Ϳ�浥λ֮���ת�������û������ҵ����ʵʩ��Աע�͵���\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"setMeasRateVO\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//���ݱ���ǰ����ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"beforeSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//����˵��:����ɾ��\nrunClassCom@\"nc.bs.ic.ic213.GeneralHBO\",\"deleteBill\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//������䣬���û������ҵ����ʵʩ��Աע�͵���\nrunClassCom@\"nc.bs.ic.pub.bill.DesassemblyBO\",\"exeDesassembly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//����˵������д�ۼƳ�������\nsetParameter(\"CURVO\",null);\nsetParameter(\"PREVO\",inCurVO);\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteCorNum\",\"&CURVO:nc.vo.ic.pub.bill.GeneralBillVO,&PREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//����˵��:ɾ���������\nsetParameter(\"hid\",inCurVO.getHeaderVO().getPrimaryKey());\nrunClassCom@ \"nc.bs.ic.ic2a1.PickBillDMO\", \"deleteItemsByOuthid\", \"&hid:String\"@;\nsetParameter(\"RWINPREVO\",null);\n//ע�����˳����Ϊ��ɾ�����������Խ���ǰ��VO��Ϊoldvo����\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteMMNewBatch\",\"&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO,&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//ע�����˳����Ϊ��ɾ�����������Խ���ǰ��VO��Ϊoldvo����\nrunClassCom@\"nc.bs.ic.pub.RewriteDMO\",\"reWriteMMljyfslBatch\",\"&RWINPREVO:nc.vo.ic.pub.bill.GeneralBillVO,&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<��������>\n//����˵��:�����������������һ��,���θ����,��������\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkDBL_New\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<��������>\n//����˵��:����������Ƿ������⸺���\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkInOutTrace\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n//�÷���<������>\n//����˵��:����λ�����Ƿ񳬳�\n//runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkCargoVolumeOut\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//�÷���<������>\n//����˵��:�����߿�桢��Ϳ�桢��ȫ��桢�ٶ�����\nObject s1=runClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"checkParam_new\",\"&INCURVO:nc.vo.pub.AggregatedValueObject\"@;\n  if(s1!=null)\n    sErr.append((String)s1);\n//�������ǰ�����������Ƿ�ƥ��\n//if(retObj != null && !(retObj instanceof ArrayList))  throw new nc.vo.pub.BusinessException(\"Remote Call\",new nc.vo.pub.BusinessException(\"����ɾ�������ķ���ֵ���ʹ���\"));\n//�÷���<��������>\n//����˵��:����ɾ��ʱ�˻ص��ݺ�\nnc.vo.scm.pub.IBillCode ibc=(nc.vo.scm.pub.IBillCode)inCurVO;\nsetParameter(\"IBC\",ibc);\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"returnBillCodeWhenDelete\",\"&IBC:nc.vo.scm.pub.IBillCode\"@;\nif(inCurVO.isHaveSourceBill()){" +
      "\n//����˵��:��������ʱ���,������\nsetParameter(\"INPREVOATP\",null);\nrunClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"checkAtpInstantly\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&INPREVOATP:nc.vo.pub.AggregatedValueObject\"@;\n}\n//���ݱ������ͳһ����\nrunClasscom@\"nc.bs.ic.pub.BillActionBase\",\"afterSave\",\"&INCURVO:nc.vo.ic.pub.bill.GeneralBillVO,&INPREVO:nc.vo.ic.pub.bill.GeneralBillVO\"@;\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\nif(inCurVO.isHaveSourceBill()){\n  //���ÿ���������\n  if(listbefore!=null)\n     setParameter(\"LISTBEFORE\",listbefore);\n  //����˵��:�޸Ŀ�����\n  runClassCom@\"nc.bs.ic.pub.bill.ICATP\",\"modifyATPAfter\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&LISTBEFORE:java.util.ArrayList\"@;\n}\n}catch(Exception e){\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"EXC\",e.getMessage());\nsetParameter(\"FUN\",\"ɾ��\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckBO\",\"insertBusinessExceptionlog\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&EXC:String,&FUN:String\"@;\n//###########################\n     if (e instanceof nc.vo.pub.BusinessException)\n     throw (nc.vo.pub.BusinessException) e;\n    else\n      throw new nc.vo.pub.BusinessException(\"Remote Call\", e);\n}\nfinally{\n//####��Ҫ˵�������ɵ�ҵ���������������Ҫ�����޸�####\n//����˵��:������ⵥ�ݽ�ҵ����\nsetParameter(\"ALLPK\",(ArrayList)alLockedPK);\nif(alLockedPK!=null)\nrunClassCom@\"nc.bs.ic.pub.bill.ICLockBO\",\"unlockBill\",\"&INCURVO:nc.vo.pub.AggregatedValueObject,&ALLPK:ArrayList\"@;\n//##################################################\n}\n}\n//############################\n//����ҵ����־���÷�����������\nsetParameter(\"INCURVOS\",inCurVOs);\nsetParameter(\"ERR\",sErr.toString());\nsetParameter(\"FUN\",\"ɾ��\");\nrunClassCom@\"nc.bs.ic.pub.check.CheckDMO\",\"insertBusinesslog\",\"&INCURVOS:nc.vo.ic.pub.bill.GeneralBillVO[],&ERR:String,&FUN:String\"@;\n//############################\ninCurVO=null;\nArrayList alRet=new ArrayList();\nif(sErr.toString().trim().length()==0)\n alRet.add(null);\nelse\n  alRet.add(sErr.toString());\n//alRet.add(retObj);\nreturn retObj;\n//************************************************************************\n";}
/*
* ��ע�����ýű�������HAS
*/
private void setParameter(String key,Object val)  {
  if (m_keyHas==null){
    m_keyHas=new Hashtable();
  }
  if (val!=null)  {
    m_keyHas.put(key,val);
  }
}
}
