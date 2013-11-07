/***************************************************************\
 *     The skeleton of this class is generated by an automatic *
 * code generator for NC product. It is based on Velocity.     *
\***************************************************************/
package nc.vo.ztwzj.sapitf.voucher;
	
import nc.vo.pub.*;

/**
 * <b> �ڴ˴���Ҫ��������Ĺ��� </b>
 * <p>
 *     �ڴ˴����Ӵ����������Ϣ
 * </p>
 * ��������:
 * @author 
 * @version NCPrj ??
 */
@SuppressWarnings("serial")
public class VoucherQryInfo extends SuperVO {
	private java.lang.String pk_group;
	private java.lang.String pk_org;
	private java.lang.String vbusitype;
	private java.lang.String vbillcode;
	private nc.vo.pub.lang.UFDate ddate;
	private java.lang.String vbank;
	private java.lang.String vbkaccount;
	private java.lang.String pk_opporg;
	private java.lang.String voppbank;
	private java.lang.String voppbkacc;
	private nc.vo.pub.lang.UFDouble nmny;
	private java.lang.String udef1;
	private java.lang.String udef2;
	private java.lang.String udef3;
	private java.lang.String udef4;
	private java.lang.String udef5;
	private java.lang.String udef6;
	private java.lang.String udef7;
	private java.lang.String udef8;
	private java.lang.String udef9;
	private java.lang.String udef10;
	private java.lang.String pk_busibill;
	private nc.vo.pub.lang.UFBoolean bselect;
	private java.lang.Integer dr = 0;
	private nc.vo.pub.lang.UFDateTime ts;

	public static final String PK_GROUP = "pk_group";
	public static final String PK_ORG = "pk_org";
	public static final String VBUSITYPE = "vbusitype";
	public static final String VBILLCODE = "vbillcode";
	public static final String DDATE = "ddate";
	public static final String VBANK = "vbank";
	public static final String VBKACCOUNT = "vbkaccount";
	public static final String PK_OPPORG = "pk_opporg";
	public static final String VOPPBANK = "voppbank";
	public static final String VOPPBKACC = "voppbkacc";
	public static final String NMNY = "nmny";
	public static final String UDEF1 = "udef1";
	public static final String UDEF2 = "udef2";
	public static final String UDEF3 = "udef3";
	public static final String UDEF4 = "udef4";
	public static final String UDEF5 = "udef5";
	public static final String UDEF6 = "udef6";
	public static final String UDEF7 = "udef7";
	public static final String UDEF8 = "udef8";
	public static final String UDEF9 = "udef9";
	public static final String UDEF10 = "udef10";
	public static final String PK_BUSIBILL = "pk_busibill";
	public static final String BSELECT = "bselect";
			
	/**
	 * ����pk_group��Getter����.������������
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_group () {
		return pk_group;
	}   
	/**
	 * ����pk_group��Setter����.������������
	 * ��������:
	 * @param newPk_group java.lang.String
	 */
	public void setPk_group (java.lang.String newPk_group ) {
	 	this.pk_group = newPk_group;
	} 	  
	/**
	 * ����pk_org��Getter����.����������˾
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_org () {
		return pk_org;
	}   
	/**
	 * ����pk_org��Setter����.����������˾
	 * ��������:
	 * @param newPk_org java.lang.String
	 */
	public void setPk_org (java.lang.String newPk_org ) {
	 	this.pk_org = newPk_org;
	} 	  
	/**
	 * ����vbusitype��Getter����.��������ҵ������
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getVbusitype () {
		return vbusitype;
	}   
	/**
	 * ����vbusitype��Setter����.��������ҵ������
	 * ��������:
	 * @param newVbusitype java.lang.String
	 */
	public void setVbusitype (java.lang.String newVbusitype ) {
	 	this.vbusitype = newVbusitype;
	} 	  
	/**
	 * ����vbillcode��Getter����.�����������ݺ�
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getVbillcode () {
		return vbillcode;
	}   
	/**
	 * ����vbillcode��Setter����.�����������ݺ�
	 * ��������:
	 * @param newVbillcode java.lang.String
	 */
	public void setVbillcode (java.lang.String newVbillcode ) {
	 	this.vbillcode = newVbillcode;
	} 	  
	/**
	 * ����ddate��Getter����.������������
	 * ��������:
	 * @return nc.vo.pub.lang.UFDate
	 */
	public nc.vo.pub.lang.UFDate getDdate () {
		return ddate;
	}   
	/**
	 * ����ddate��Setter����.������������
	 * ��������:
	 * @param newDdate nc.vo.pub.lang.UFDate
	 */
	public void setDdate (nc.vo.pub.lang.UFDate newDdate ) {
	 	this.ddate = newDdate;
	} 	  
	/**
	 * ����vbank��Getter����.��������������
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getVbank () {
		return vbank;
	}   
	/**
	 * ����vbank��Setter����.��������������
	 * ��������:
	 * @param newVbank java.lang.String
	 */
	public void setVbank (java.lang.String newVbank ) {
	 	this.vbank = newVbank;
	} 	  
	/**
	 * ����vbkaccount��Getter����.���������ʺ�
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getVbkaccount () {
		return vbkaccount;
	}   
	/**
	 * ����vbkaccount��Setter����.���������ʺ�
	 * ��������:
	 * @param newVbkaccount java.lang.String
	 */
	public void setVbkaccount (java.lang.String newVbkaccount ) {
	 	this.vbkaccount = newVbkaccount;
	} 	  
	/**
	 * ����pk_opporg��Getter����.����������(��)�λPK
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_opporg () {
		return pk_opporg;
	}   
	/**
	 * ����pk_opporg��Setter����.����������(��)�λPK
	 * ��������:
	 * @param newPk_opporg java.lang.String
	 */
	public void setPk_opporg (java.lang.String newPk_opporg ) {
	 	this.pk_opporg = newPk_opporg;
	} 	  
	/**
	 * ����voppbank��Getter����.����������(��)�λ����
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getVoppbank () {
		return voppbank;
	}   
	/**
	 * ����voppbank��Setter����.����������(��)�λ����
	 * ��������:
	 * @param newVoppbank java.lang.String
	 */
	public void setVoppbank (java.lang.String newVoppbank ) {
	 	this.voppbank = newVoppbank;
	} 	  
	/**
	 * ����voppbkacc��Getter����.����������(��)�λ�˺�
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getVoppbkacc () {
		return voppbkacc;
	}   
	/**
	 * ����voppbkacc��Setter����.����������(��)�λ�˺�
	 * ��������:
	 * @param newVoppbkacc java.lang.String
	 */
	public void setVoppbkacc (java.lang.String newVoppbkacc ) {
	 	this.voppbkacc = newVoppbkacc;
	} 	  
	/**
	 * ����nmny��Getter����.�����������
	 * ��������:
	 * @return nc.vo.pub.lang.UFDouble
	 */
	public nc.vo.pub.lang.UFDouble getNmny () {
		return nmny;
	}   
	/**
	 * ����nmny��Setter����.�����������
	 * ��������:
	 * @param newNmny nc.vo.pub.lang.UFDouble
	 */
	public void setNmny (nc.vo.pub.lang.UFDouble newNmny ) {
	 	this.nmny = newNmny;
	} 	  
	/**
	 * ����udef1��Getter����.���������Զ���1
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef1 () {
		return udef1;
	}   
	/**
	 * ����udef1��Setter����.���������Զ���1
	 * ��������:
	 * @param newUdef1 java.lang.String
	 */
	public void setUdef1 (java.lang.String newUdef1 ) {
	 	this.udef1 = newUdef1;
	} 	  
	/**
	 * ����udef2��Getter����.���������Զ���2
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef2 () {
		return udef2;
	}   
	/**
	 * ����udef2��Setter����.���������Զ���2
	 * ��������:
	 * @param newUdef2 java.lang.String
	 */
	public void setUdef2 (java.lang.String newUdef2 ) {
	 	this.udef2 = newUdef2;
	} 	  
	/**
	 * ����udef3��Getter����.���������Զ���3
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef3 () {
		return udef3;
	}   
	/**
	 * ����udef3��Setter����.���������Զ���3
	 * ��������:
	 * @param newUdef3 java.lang.String
	 */
	public void setUdef3 (java.lang.String newUdef3 ) {
	 	this.udef3 = newUdef3;
	} 	  
	/**
	 * ����udef4��Getter����.���������Զ���4
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef4 () {
		return udef4;
	}   
	/**
	 * ����udef4��Setter����.���������Զ���4
	 * ��������:
	 * @param newUdef4 java.lang.String
	 */
	public void setUdef4 (java.lang.String newUdef4 ) {
	 	this.udef4 = newUdef4;
	} 	  
	/**
	 * ����udef5��Getter����.���������Զ���5
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef5 () {
		return udef5;
	}   
	/**
	 * ����udef5��Setter����.���������Զ���5
	 * ��������:
	 * @param newUdef5 java.lang.String
	 */
	public void setUdef5 (java.lang.String newUdef5 ) {
	 	this.udef5 = newUdef5;
	} 	  
	/**
	 * ����udef6��Getter����.���������Զ���6
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef6 () {
		return udef6;
	}   
	/**
	 * ����udef6��Setter����.���������Զ���6
	 * ��������:
	 * @param newUdef6 java.lang.String
	 */
	public void setUdef6 (java.lang.String newUdef6 ) {
	 	this.udef6 = newUdef6;
	} 	  
	/**
	 * ����udef7��Getter����.���������Զ���7
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef7 () {
		return udef7;
	}   
	/**
	 * ����udef7��Setter����.���������Զ���7
	 * ��������:
	 * @param newUdef7 java.lang.String
	 */
	public void setUdef7 (java.lang.String newUdef7 ) {
	 	this.udef7 = newUdef7;
	} 	  
	/**
	 * ����udef8��Getter����.���������Զ���8
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef8 () {
		return udef8;
	}   
	/**
	 * ����udef8��Setter����.���������Զ���8
	 * ��������:
	 * @param newUdef8 java.lang.String
	 */
	public void setUdef8 (java.lang.String newUdef8 ) {
	 	this.udef8 = newUdef8;
	} 	  
	/**
	 * ����udef9��Getter����.���������Զ���9
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef9 () {
		return udef9;
	}   
	/**
	 * ����udef9��Setter����.���������Զ���9
	 * ��������:
	 * @param newUdef9 java.lang.String
	 */
	public void setUdef9 (java.lang.String newUdef9 ) {
	 	this.udef9 = newUdef9;
	} 	  
	/**
	 * ����udef10��Getter����.���������Զ���10
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getUdef10 () {
		return udef10;
	}   
	/**
	 * ����udef10��Setter����.���������Զ���10
	 * ��������:
	 * @param newUdef10 java.lang.String
	 */
	public void setUdef10 (java.lang.String newUdef10 ) {
	 	this.udef10 = newUdef10;
	} 	  
	/**
	 * ����pk_busibill��Getter����.������������
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getPk_busibill () {
		return pk_busibill;
	}   
	/**
	 * ����pk_busibill��Setter����.������������
	 * ��������:
	 * @param newPk_busibill java.lang.String
	 */
	public void setPk_busibill (java.lang.String newPk_busibill ) {
	 	this.pk_busibill = newPk_busibill;
	} 	  
	/**
	 * ����bselect��Getter����.��������ѡ��
	 * ��������:
	 * @return nc.vo.pub.lang.UFBoolean
	 */
	public nc.vo.pub.lang.UFBoolean getBselect () {
		return bselect;
	}   
	/**
	 * ����bselect��Setter����.��������ѡ��
	 * ��������:
	 * @param newBselect nc.vo.pub.lang.UFBoolean
	 */
	public void setBselect (nc.vo.pub.lang.UFBoolean newBselect ) {
	 	this.bselect = newBselect;
	} 	  
	/**
	 * ����dr��Getter����.��������dr
	 * ��������:
	 * @return java.lang.Integer
	 */
	public java.lang.Integer getDr () {
		return dr;
	}   
	/**
	 * ����dr��Setter����.��������dr
	 * ��������:
	 * @param newDr java.lang.Integer
	 */
	public void setDr (java.lang.Integer newDr ) {
	 	this.dr = newDr;
	} 	  
	/**
	 * ����ts��Getter����.��������ts
	 * ��������:
	 * @return nc.vo.pub.lang.UFDateTime
	 */
	public nc.vo.pub.lang.UFDateTime getTs () {
		return ts;
	}   
	/**
	 * ����ts��Setter����.��������ts
	 * ��������:
	 * @param newTs nc.vo.pub.lang.UFDateTime
	 */
	public void setTs (nc.vo.pub.lang.UFDateTime newTs ) {
	 	this.ts = newTs;
	} 	  
 
	/**
	  * <p>ȡ�ø�VO�����ֶ�.
	  * <p>
	  * ��������:
	  * @return java.lang.String
	  */
	public java.lang.String getParentPKFieldName() {
	    return null;
	}   
    
	/**
	  * <p>ȡ�ñ�����.
	  * <p>
	  * ��������:
	  * @return java.lang.String
	  */
	public java.lang.String getPKFieldName() {
	  return "pk_busibill";
	}
    
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public java.lang.String getTableName() {
		return "cmp_ztw_voucheriftinfo";
	}    
	
	/**
	 * <p>���ر�����.
	 * <p>
	 * ��������:
	 * @return java.lang.String
	 */
	public static java.lang.String getDefaultTableName() {
		return "cmp_ztw_voucheriftinfo";
	}    
    
    /**
	  * ����Ĭ�Ϸ�ʽ����������.
	  *
	  * ��������:
	  */
     public VoucherQryInfo() {
		super();	
	}    
	
	@nc.vo.annotation.MDEntityInfo(beanFullclassName =  "nc.vo.ztwzj.sapitf.voucher.VoucherQryInfo" )
	public IVOMeta getMetaData() {
   		return null;
  	}
} 

