package nc.ui.pf.changedir;

import nc.vo.pf.change.UserDefineFunction;
/**
 * ����61TO4F��VO�Ķ�̬ת���ࡣ
 *
 * �������ڣ�(2004-11-18)
 * @author��ƽ̨�ű�����
 */
public class CHG61CGTO4F extends nc.bs.pf.change.VOConversion {
/**
 * CHG61TO4F ������ע�⡣
 */
public CHG61CGTO4F() {
	super();
}
/**
* ��ú������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getAfterClassName() {
	return null;
}
/**
* �����һ���������ȫ¼�����ơ�
* @return java.lang.String[]
*/
public String getOtherClassName() {
	return null;
}
/**
* ����ֶζ�Ӧ��
* @return java.lang.String[]
*/
public String[] getField() {
	return new String[] {
		"H_pk_corp->H_pk_corp",
		"H_dauditdate->H_dauditdate",
		"H_dbilldate->SYSDATE",
		"H_coperatorid->SYSOPERATOR",
		"H_cbizid->H_cemployeeid",
		"H_cbiztype->H_cbiztype",

		"H_cproviderid->H_cgiveinvoicevendor",
		"H_ccustomerid->H_ccvendormangid",
//		"H_pk_cubasdocc->H_cvendorid",
		 
//		"H_->H_cvendorid",
//		"H_cproviderid->H_cvendorid",
		
		"B_vfree5->B_vfree5",
		"B_vfree4->B_vfree4",
		"B_vfree3->B_vfree3",
		"B_vfree2->B_vfree2",
		"B_vfree1->B_vfree1",
		
		"H_vuserdef1->H_vdef1",
		"H_vuserdef2->H_vdef2",
		"H_vuserdef3->H_vdef3",
		"H_vuserdef4->H_vdef4",
		"H_vuserdef5->H_vdef5",
		"H_vuserdef6->H_vdef6",
		"H_vuserdef7->H_vdef7",
		"H_vuserdef8->H_vdef8",
		"H_vuserdef9->H_vdef9",
		"H_vuserdef10->H_vdef10",
		"H_vuserdef11->H_vdef11",
		"H_vuserdef12->H_vdef12",
		"H_vuserdef13->H_vdef13",
		"H_vuserdef14->H_vdef14",
		"H_vuserdef15->H_vdef15",
		"H_vuserdef16->H_vdef16",
		"H_vuserdef17->H_vdef17",
		"H_vuserdef18->H_vdef18",
		"H_vuserdef19->H_vdef19",
		"H_vuserdef20->H_vdef20",
//		"H_ts->H_ts",
		"H_vnote->H_vmemo",
		"B_vfree1->B_vfree1",
		"B_vfree2->B_vfree2",
		"B_vfree3->B_vfree3",
		"B_vfree4->B_vfree4",
		"B_vfree5->B_vfree5",
		
		"B_castunitid->B_cassistunit",//������
		"B_cinventoryid->B_cmangid", //���������ID
		"B_ccostobject->B_cprocessmangid",
		
		"B_csourcebillbid->B_carriveorder_bid",
		"B_csourcebillhid->B_carriveorderid",
		"B_dvalidate->B_dvaliddate",
		"B_hsl->B_convertrate",
		"B_vbatchcode->B_vproducenum",

		"B_nprice->B_nprice",        //����
		"B_vsourcebillcode->H_vordercode",
		"B_csourcebillhid->B_corderid",
		"B_csourcebillbid->B_corder_lb_id",
//		"B_ccheckstateid->B_squalitylevel", //����״̬
//		"B_flargess->B_blargess",  //�Ƿ���Ʒ
//		"B_ts->B_ts",
		"B_vuserdef1->B_vdef1",
		"B_vuserdef2->B_vdef2",
		"B_vuserdef3->B_vdef3",
		"B_vuserdef4->B_vdef4",
		"B_vuserdef5->B_vdef5",
		"B_vuserdef6->B_vdef6",
		"B_vuserdef7->B_vdef7",
		"B_vuserdef8->B_vdef8",
		"B_vuserdef9->B_vdef9",
		"B_vuserdef10->B_vdef10",
		"B_vuserdef11->B_vdef11",
		"B_vuserdef12->B_vdef12",
		"B_vuserdef13->B_vdef13",
		"B_vuserdef14->B_vdef14",
		"B_vuserdef15->B_vdef15",
		"B_vuserdef16->B_vdef16",
		"B_vuserdef17->B_vdef17",
		"B_vuserdef18->B_vdef18",
		"B_vuserdef19->B_vdef19",
		"B_vuserdef20->B_vdef20",
		"B_csourceheadts->H_ts",
		"B_csourcebodyts->B_ts",
		"B_scrq->B_dproducedate",  
//		"B_cprojectid->B_cprojectid", //��Ŀ
//		"B_cprojectphaseid->B_cprojectphaseid",
		"B_vnotebody->B_vmemo",
//		"B_corder_bb1id->B_corder_bb1id",
		"B_vsourcerowno->B_crowno",
		"B_cinvbasid->B_cbaseid",  //��������ID
		"B_cinvmanid->B_cmangid",  //..
//		"B_bsourcelargess->B_blargess",
		
		"H_pk_defdoc1->H_pk_defdoc1",
		"H_pk_defdoc2->H_pk_defdoc2",
		"H_pk_defdoc3->H_pk_defdoc3",
		"H_pk_defdoc4->H_pk_defdoc4",
		"H_pk_defdoc5->H_pk_defdoc5",
		"H_pk_defdoc6->H_pk_defdoc6",
		"H_pk_defdoc7->H_pk_defdoc7",
		"H_pk_defdoc8->H_pk_defdoc8",
		"H_pk_defdoc9->H_pk_defdoc9",
		"H_pk_defdoc10->H_pk_defdoc10",
		"H_pk_defdoc11->H_pk_defdoc11",
		"H_pk_defdoc12->H_pk_defdoc12",
		"H_pk_defdoc13->H_pk_defdoc13",
		"H_pk_defdoc14->H_pk_defdoc14",
		"H_pk_defdoc15->H_pk_defdoc15",
		"H_pk_defdoc16->H_pk_defdoc16",
		"H_pk_defdoc17->H_pk_defdoc17",
		"H_pk_defdoc18->H_pk_defdoc18",
		"H_pk_defdoc19->H_pk_defdoc19",
		"H_pk_defdoc20->H_pk_defdoc20",
		
		"B_pk_defdoc1->B_pk_defdoc1",
		"B_pk_defdoc2->B_pk_defdoc2",
		"B_pk_defdoc3->B_pk_defdoc3",
		"B_pk_defdoc4->B_pk_defdoc4",
		"B_pk_defdoc5->B_pk_defdoc5",
		"B_pk_defdoc6->B_pk_defdoc6",
		"B_pk_defdoc7->B_pk_defdoc7",
		"B_pk_defdoc8->B_pk_defdoc8",
		"B_pk_defdoc9->B_pk_defdoc9",
		"B_pk_defdoc10->B_pk_defdoc10",
		"B_pk_defdoc11->B_pk_defdoc11",
		"B_pk_defdoc12->B_pk_defdoc12",
		"B_pk_defdoc13->B_pk_defdoc13",
		"B_pk_defdoc14->B_pk_defdoc14",
		"B_pk_defdoc15->B_pk_defdoc15",
		"B_pk_defdoc16->B_pk_defdoc16",
		"B_pk_defdoc17->B_pk_defdoc17",
		"B_pk_defdoc18->B_pk_defdoc18",
		"B_pk_defdoc19->B_pk_defdoc19",
		"B_pk_defdoc20->B_pk_defdoc20",
	};
}
/**
* ��ù�ʽ��
* @return java.lang.String[]
*/
public String[] getFormulas() {
	return new String[] {
		"H_cbilltypecode->\"4F\"", 
		"B_csourcetype->\"61\"",
		"B_nshouldoutnum->B_nnum-B_naccumsendnum"
	};
}
/**
* �����û��Զ��庯����
*/
public UserDefineFunction[] getUserDefineFunction() {
	return null;
}
}
