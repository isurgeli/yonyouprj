package nc.ui.pf.changedir;

import nc.bs.pf.change.ConversionPolicyEnum;
import nc.vo.pf.change.UserDefineFunction;

public class CHG4331TO4K extends nc.ui.pf.change.VOConversionUI {
	/**
	 * CHG30TO4K 构造子注解。
	 */
	public CHG4331TO4K() {
		super();
	}

	/**
	 * 获得后续类的全录经名称。
	 * 
	 * @return java.lang.String[]
	 */
	public String getAfterClassName() {
		//return "nc.ui.ic.pub.pfconv.HardLockChgVO";
		return null;
	}

	/**
	 * 获得另一个后续类的全录径名称。
	 * 
	 * @return java.lang.String[]
	 */
	public String getOtherClassName() {
		//return "nc.bs.ic.pub.pfconv.HardLockChgVO";
		return null;
	}

	/**
	 * 返回交换类型枚举ConversionPolicyEnum，默认为单据项目-单据项目
	 * 
	 * @return ConversionPolicyEnum
	 * @since 5.5
	 */
	public ConversionPolicyEnum getConversionPolicy() {
		return ConversionPolicyEnum.BILLITEM_METADATA;
	}

	/**
	 * 获得字段对应。
	 * 
	 * @return java.lang.String[]
	 */
	public String[] getField() {
		return new String[] {
				"H_vuserdef9->csalereceiveid_b.ccustmandocid",
				"H_cbiztype->csalereceiveid_b.cbiztype",
				"H_cshlddiliverdate->csalereceiveid_b.dsenddate",
				"H_vnote->vnote",
				"H_pk_corp->csalereceiveid_b.pk_sendcorp",
				"H_pk_calbody_out->csalereceiveid_b.csendcalbodyid",
				"H_coutwarehouseid->csalereceiveid_b.csendwareid",
				"H_coutdeptid->cdeptid",
				//"H_vuserdef9->vdef9",
				//"H_vuserdef8->vdef8",
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
				
				"B_csourcebillbid->csalereceiveid_b.csalereceiveid_bid",
				"B_csourcebillhid->csalereceiveid",
				"B_vsourcebillcode->vreceivecode",
				"B_cfirstbillbid->csalereceiveid_b.csourcebillbodyid",
				"B_cfirstbillhid->csalereceiveid_b.csourcebillid",
				"B_cfirsttype->csalereceiveid_b.vsourcetype",
				"B_vfirstbillcode->csalereceiveid_b.vsourcereceivecode",
				"B_crowno->csalereceiveid_b.vsourcerowno",
				
				"B_castunitid->csalereceiveid_b.castunitid",
				"B_cfreezeid->csalereceiveid_b.cfreezeid",
				"B_cinventoryid->csalereceiveid_b.cinvmandocid",
				"B_pk_corp->csalereceiveid_b.pk_sendcorp",

				"B_creceieveid->csalereceiveid_b.creceivecustid",
				"B_vbatchcode->csalereceiveid_b.vbatchcode",
				"B_vnote->csalereceiveid_b.vrownote",

				"B_vfree1->csalereceiveid_b.vfree1",
				"B_vfree2->csalereceiveid_b.vfree2",
				"B_vfree3->csalereceiveid_b.vfree3",
				"B_vfree4->csalereceiveid_b.vfree4",
				"B_vfree5->csalereceiveid_b.vfree5",

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
				"B_vuserdef20->csalereceiveid_b.vdef20",
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

		};
	}

	/**
	 * 获得公式。
	 * 
	 * @return java.lang.String[]
	 */
	public String[] getFormulas() {
		return new String[] {
				"H_coperatorid->SYSOPERATOR", 
				"H_clastmodiid->SYSOPERATOR",
				"H_tmaketime->SYSTIME",
				"H_tlastmoditime ->SYSTIME",
				"H_dbilldate->SYSDATE",
				"H_vuserdef8->getColValue(so_sale,vreceiptcode,csaleid,csalereceiveid_b.csourcebillid)",
				"H_cbilltypecode->\"4K\"",
				"H_iprintcount->0",
				
				"B_dshldtransnum->csalereceiveid_b.nnumber - iif(csalereceiveid_b.vdef20==null,0,tonumber(csalereceiveid_b.vdef20)) ",
				"B_hsl->iif(csalereceiveid_b.scalefactor==null,csalereceiveid_b.nnumber/csalereceiveid_b.npacknumber,csalereceiveid_b.scalefactor)",
				"B_csourcetype->\"4331\"",
				"B_nshldtransastnum->csalereceiveid_b.nastnumber - iif(csalereceiveid_b.scalefactor==null || csalereceiveid_b.scalefactor==0,0,iif(csalereceiveid_b.vdef20==null,0,tonumber(csalereceiveid_b.vdef20))/csalereceiveid_b.scalefactor) ",
				"B_nmny->getColValue(so_saleorder_b,nmny,corder_bid,csalereceiveid_b.csourcebillbodyid)",
				"B_nprice->getColValue(so_saleorder_b,nnetprice,corder_bid,csalereceiveid_b.csourcebillbodyid)"
		};
	}

	/**
	 * 返回用户自定义函数。
	 */
	public UserDefineFunction[] getUserDefineFunction() {
		return null;
	}
}
