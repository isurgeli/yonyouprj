package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;
/**
 * ����30TO4C��VO�Ķ�̬ת���ࡣ
 *
 * �������ڣ�(2004-11-18)
 * @author��ƽ̨�ű�����
 */
public class CHG4331TO4C extends nc.bs.pf.change.VOConversion {
/**
 * CHG30TO4C ������ע�⡣
 */
public CHG4331TO4C() {
  super();
}
/**
* ��ú������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getAfterClassName() {
  return "nc.bs.ic.pub.pfconv.HardLockChgVO";
}
/**
* �����һ���������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getOtherClassName() {
  return "nc.ui.ic.pub.pfconv.HardLockChgVO";
}

/**
 * ���ؽ�������ö��ConversionPolicyEnum��Ĭ��Ϊ������Ŀ-������Ŀ
 * @return ConversionPolicyEnum
 * @since 5.5
 */
public ConversionPolicyEnum getConversionPolicy() {
	return ConversionPolicyEnum.BILLITEM_METADATA;
}

/**
* ����ֶζ�Ӧ��
* @return java.lang.String[]
*/
public String[] getField() {
  return new String[] {
    //"H_ccustomerid->csalereceiveid_b.ccustomerid",
    "H_ccustomerid->csalereceiveid_b.ccustmandocid",
    "H_pk_cubasdocC->csalereceiveid_b.ccustbasdocid",
    //"H_cdptid->cdeptid",
    //"H_cbizid->cemployeeid",
    "H_cbiztype->csalereceiveid_b.cbiztype",
    //"H_vdiliveraddress->csalereceiveid_b.vsendaddress",
    "H_cdilivertypeid->ctransporttypeid",
    "H_vnote->vnote",
    "H_pk_corp->csalereceiveid_b.pk_sendcorp",
    "H_pk_calbody->csalereceiveid_b.csendcalbodyid",
    "H_cwarehouseid->csalereceiveid_b.csendwareid",
    "H_ctrancustid->csalereceiveid_b.ctranscustid",
    //"H_cwhsmanagerid->csalereceiveid_b.cstoreadmin",
    //"H_freplenishflag->bretinvflag",
    
    "H_ts->ts",
    
    
    "H_vuserdef9->vdef9",
    "H_vuserdef8->vdef8",
    "H_vuserdef7->vdef7",
    "H_vuserdef6->vdef6",
    "H_vuserdef5->vdef5",
    "H_vuserdef4->vdef4",
    "H_vuserdef3->vdef3",
    "H_vuserdef2->vdef2",
    "H_vuserdef1->vdef1",
    "H_vuserdef10->vdef10",
    "H_vuserdef11->vdef11",
    "H_vuserdef12->vdef12",
    "H_vuserdef13->vdef13",
    "H_vuserdef14->vdef14",
    "H_vuserdef15->vdef15",
    "H_vuserdef16->vdef16",
    "H_vuserdef17->vdef17",
    "H_vuserdef18->vdef18",
    "H_vuserdef19->vdef19",
    "H_vuserdef20->vdef20",
    
    "H_pk_defdoc1->pk_defdoc1",
    "H_pk_defdoc2->pk_defdoc2",
    "H_pk_defdoc3->pk_defdoc3",
    "H_pk_defdoc4->pk_defdoc4",
    "H_pk_defdoc5->pk_defdoc5",
    "H_pk_defdoc6->pk_defdoc6",
    "H_pk_defdoc7->pk_defdoc7",
    "H_pk_defdoc8->pk_defdoc8",
    "H_pk_defdoc9->pk_defdoc9",
    "H_pk_defdoc10->pk_defdoc10",
    "H_pk_defdoc11->pk_defdoc11",
    "H_pk_defdoc12->pk_defdoc12",
    "H_pk_defdoc13->pk_defdoc13",
    "H_pk_defdoc14->pk_defdoc14",
    "H_pk_defdoc15->pk_defdoc15",
    "H_pk_defdoc16->pk_defdoc16",
    "H_pk_defdoc17->pk_defdoc17",
    "H_pk_defdoc18->pk_defdoc18",
    "H_pk_defdoc19->pk_defdoc19",
    "H_pk_defdoc20->pk_defdoc20",
    
    //"B_vdiliveraddress->csalereceiveid_b.vsendaddress",
    "B_pk_corp->csalereceiveid_b.pk_sendcorp",
    "B_pk_calbody->csalereceiveid_b.csendcalbodyid",
    "B_cwarehouseid->csalereceiveid_b.csendwareid",
    "B_cbiztype->csalereceiveid_b.cbiztype",
    "B_ccustomerid->csalereceiveid_b.ccustmandocid",
    "B_pk_cubasdocC->csalereceiveid_b.ccustbasdocid",
    "B_ctrancustid->csalereceiveid_b.ctranscustid",
    
    
    //"B_cquotecurrency->csalereceiveid_b.ccurrencytypeid",
    //"B_nquoteprice->csalereceiveid_b.noriginalcurtaxnetprice",
    
    //"B_breturnprofit->csalereceiveid_b.breturnprofit",
    //"B_bsafeprice->csalereceiveid_b.bsafeprice",
    //"B_nquoteunitrate->csalereceiveid_b.scalefactor",
    
    "B_castunitid->csalereceiveid_b.castunitid",
    "B_cfreezeid->csalereceiveid_b.cfreezeid",
    "B_cinventoryid->csalereceiveid_b.cinvmandocid",
    "B_cinvbasid->csalereceiveid_b.cinvbasdocid",
    "B_cinvmanid->csalereceiveid_b.cinvbasdocid",
    //"B_cprojectid->csalereceiveid_b.cprojectid",
    //"B_cprojectphaseid->csalereceiveid_b.cprojectphaseid",
    //"B_flargess->csalereceiveid_b.blargessflag",
    //"B_nsaleprice->csalereceiveid_b.nnetprice",
    //"B_ntaxprice->csalereceiveid_b.ntaxnetprice",
    //"B_cquoteunitid->csalereceiveid_b.cquoteunitid",   
    "B_creceieveid->csalereceiveid_b.creceivecustid",
    "B_vbatchcode->csalereceiveid_b.vbatchcode",
    "B_vnotebody->csalereceiveid_b.vrownote",
    
    "B_vfree1->csalereceiveid_b.vfree1",
    "B_vfree2->csalereceiveid_b.vfree2",
    "B_vfree3->csalereceiveid_b.vfree3",
    "B_vfree4->csalereceiveid_b.vfree4",
    "B_vfree5->csalereceiveid_b.vfree5", 
    
    
    "B_csourcebillbid->csalereceiveid_b.csalereceiveid_bid",
    "B_csourcebillhid->csalereceiveid",
    "B_vsourcebillcode->vreceivecode",
    "B_vsourcerowno->csalereceiveid_b.crowno",
    
    "B_cfirstbillbid->csalereceiveid_b.csourcebillbodyid",
    "B_cfirstbillhid->csalereceiveid_b.csourcebillid",
    "B_cfirsttype->csalereceiveid_b.vsourcetype",
    "B_vfirstbillcode->csalereceiveid_b.vsourcereceivecode",
    "B_vfirstrowno->csalereceiveid_b.vsourcerowno",
    
    "B_csourcebodyts->csalereceiveid_b.ts",
    "B_csourceheadts->ts",
      
    "B_vuserdef1->csalereceiveid_b.vdef1",
    "B_vuserdef10->csalereceiveid_b.vdef10",
    "B_vuserdef11->csalereceiveid_b.vdef11",
    "B_vuserdef12->csalereceiveid_b.vdef12",
    "B_vuserdef13->csalereceiveid_b.vdef13",
    "B_vuserdef14->csalereceiveid_b.vdef14",
    "B_vuserdef15->csalereceiveid_b.vdef15",
    "B_vuserdef16->csalereceiveid_b.vdef16",
    "B_vuserdef17->csalereceiveid_b.vdef17",
    "B_vuserdef18->csalereceiveid_b.vdef18",
    "B_vuserdef19->csalereceiveid_b.vdef19",
    "B_vuserdef2->csalereceiveid_b.vdef2",
    //"B_vuserdef20->csalereceiveid_b.vdef20",
    "B_vuserdef3->csalereceiveid_b.vdef3",
    "B_vuserdef4->csalereceiveid_b.vdef4",
    "B_vuserdef5->csalereceiveid_b.vdef5",
    "B_vuserdef6->csalereceiveid_b.vdef6",
    "B_vuserdef7->csalereceiveid_b.vdef7",
    "B_vuserdef8->csalereceiveid_b.vdef8",
    "B_vuserdef9->csalereceiveid_b.vdef9",
    
    "B_pk_defdoc1->csalereceiveid_b.pk_defdoc1",
    "B_pk_defdoc2->csalereceiveid_b.pk_defdoc2",
    "B_pk_defdoc3->csalereceiveid_b.pk_defdoc3",
    "B_pk_defdoc4->csalereceiveid_b.pk_defdoc4",
    "B_pk_defdoc5->csalereceiveid_b.pk_defdoc5",
    "B_pk_defdoc6->csalereceiveid_b.pk_defdoc6",
    "B_pk_defdoc7->csalereceiveid_b.pk_defdoc7",
    "B_pk_defdoc8->csalereceiveid_b.pk_defdoc8",
    "B_pk_defdoc9->csalereceiveid_b.pk_defdoc9",
    "B_pk_defdoc10->csalereceiveid_b.pk_defdoc10",
    "B_pk_defdoc11->csalereceiveid_b.pk_defdoc11",
    "B_pk_defdoc12->csalereceiveid_b.pk_defdoc12",
    "B_pk_defdoc13->csalereceiveid_b.pk_defdoc13",
    "B_pk_defdoc14->csalereceiveid_b.pk_defdoc14",
    "B_pk_defdoc15->csalereceiveid_b.pk_defdoc15",
    "B_pk_defdoc16->csalereceiveid_b.pk_defdoc16",
    "B_pk_defdoc17->csalereceiveid_b.pk_defdoc17",
    "B_pk_defdoc18->csalereceiveid_b.pk_defdoc18",
    "B_pk_defdoc19->csalereceiveid_b.pk_defdoc19",
    "B_pk_defdoc20->csalereceiveid_b.pk_defdoc20",
    
    "B_ddeliverdate->csalereceiveid_b.ddeliverdate",
    "B_creceiveareaid->csalereceiveid_b.creceiveareaid",
    "B_vreceiveaddress->csalereceiveid_b.vreceiveaddress",
    "B_creceivepointid->csalereceiveid_b.vreceivepointid",
    
    
  };
}
/**
* ��ù�ʽ��
* @return java.lang.String[]
*/
public String[] getFormulas() {
  return new String[] {
      "H_clogdatenow->SYSDATE",
      "H_coperatorid->SYSOPERATOR",
      "H_dbilldate->SYSDATE",
      "H_coperatoridnow->SYSOPERATOR",
    //"H_pk_cubasdocC->getColValue(bd_cumandoc,pk_cubasdoc,pk_cumandoc,H_ccustomerid)",
    "H_cbilltypecode->\"4C\"",
    //"B_cinvbasid->getColValue(bd_invmandoc,pk_invbasdoc,pk_invmandoc,csalereceiveid_b.cinventoryid)",
    //"B_cinvmanid->getColValue(bd_invmandoc,pk_invbasdoc,pk_invmandoc,csalereceiveid_b.cinventoryid)",
    "B_nshouldoutnum->csalereceiveid_b.nnumber - iif(csalereceiveid_b.ntotaloutinvnum==null,0,csalereceiveid_b.ntotaloutinvnum) ",
    "B_hsl->iif(csalereceiveid_b.scalefactor==null,csalereceiveid_b.nnumber/csalereceiveid_b.npacknumber,csalereceiveid_b.scalefactor)",
    "B_csourcetype->\"4331\"",
    "B_nshouldoutassistnum->csalereceiveid_b.nastnumber - iif(csalereceiveid_b.scalefactor==null || csalereceiveid_b.scalefactor==0,0,iif(csalereceiveid_b.ntotaloutinvnum==null,0,csalereceiveid_b.ntotaloutinvnum)/csalereceiveid_b.scalefactor) "
  };
}
/**
* �����û��Զ��庯����
*/
public UserDefineFunction[] getUserDefineFunction() {
  return null;
}
}
