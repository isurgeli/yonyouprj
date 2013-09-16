package nc.vo.sc.order;

import nc.vo.mm.pub.MMCircularlyAccessibleValueObject;
import nc.vo.pub.lang.*;

/**
 * ��׼�ֽ���;//�к��˼ƻ�BOM������BOM������BOM
 * �������ڣ�(2001-6-30 22:54:09)
 * @author��л־��
 */
public class CGDisassembleVO extends MMCircularlyAccessibleValueObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String pk_corp; //��˾ID
	public String gcbm; //����ID
	public String pk; //����
	public String wlbmid; //����ID(���ţ�;;//�����Ǽƻ�BOM�ķ���ID

	public UFDouble sl; //��׼����
	public UFDouble shxs; //���ϵ��
	public String flckid; //���ϲֿ�ID
	public UFBoolean sfth; //�Ƿ���滻
	public Integer zxlx; //��������;�ƻ�BOM��Ϊ�������������
	public Integer kzbz; //���Ʊ�־
	
	public nc.vo.scm.ic.bill.InvVO invVO;
	public UFDouble nprice;
	public String vbatch;
	public String getVbatch() {
			return vbatch;
		}
		public void setVbatch(String vbatch) {
			this.vbatch = vbatch;
		}
/**
 * DisassembleVO ������ע�⡣
 */
public CGDisassembleVO() {
	super();
}
public nc.vo.scm.ic.bill.InvVO getInvVO() {
	return invVO;
}
public void setInvVO(nc.vo.scm.ic.bill.InvVO invVO) {
	this.invVO = invVO;
}
public UFDouble getNprice() {
	return nprice;
}
public void setNprice(UFDouble nprice) {
	this.nprice = nprice;
}
/**
 * getEntityName ����ע�⡣
 */
public String getEntityName() {
	return null;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getFlckid() {
	return flckid;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getGcbm() {
	return gcbm;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-03 16:14:33)
 * @return java.lang.Integer
 */
public java.lang.Integer getKzbz() {
	return kzbz;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getPk() {
	return pk;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getPk_corp() {
	return pk_corp;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfth() {
	return sfth;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getShxs() {
	return shxs;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getSl() {
	return sl;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getWlbmid() {
	return wlbmid;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @return java.lang.Integer
 */
public java.lang.Integer getZxlx() {
	return zxlx;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newFlckid java.lang.String
 */
public void setFlckid(java.lang.String newFlckid) {
	flckid = newFlckid;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newGcbm java.lang.String
 */
public void setGcbm(java.lang.String newGcbm) {
	gcbm = newGcbm;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-03 16:14:33)
 * @param newKzbz java.lang.Integer
 */
public void setKzbz(java.lang.Integer newKzbz) {
	kzbz = newKzbz;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newPk java.lang.String
 */
public void setPk(java.lang.String newPk) {
	pk = newPk;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newPk_corp java.lang.String
 */
public void setPk_corp(java.lang.String newPk_corp) {
	pk_corp = newPk_corp;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newSfth nc.vo.pub.lang.UFBoolean
 */
public void setSfth(nc.vo.pub.lang.UFBoolean newSfth) {
	sfth = newSfth;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newShxs nc.vo.pub.lang.UFDouble
 */
public void setShxs(nc.vo.pub.lang.UFDouble newShxs) {
	shxs = newShxs;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newSl nc.vo.pub.lang.UFDouble
 */
public void setSl(nc.vo.pub.lang.UFDouble newSl) {
	sl = newSl;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newWlbmid java.lang.String
 */
public void setWlbmid(java.lang.String newWlbmid) {
	wlbmid = newWlbmid;
}
/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-6-30 23:19:16)
 * @param newZxlx java.lang.Integer
 */
public void setZxlx(java.lang.Integer newZxlx) {
	zxlx = newZxlx;
}
/**
 * validate ����ע�⡣
 */
public void validate() throws nc.vo.pub.ValidationException {}

	public String bz;//��ע������BOM����ʾ��Ϣ
	public Integer cm; //����
	public String edate; //ʧЧ����

	public String hh; //�к�
	public String jldwid; //������λID	
	public String pk_produce;//���Ϲ���PK
	public UFDouble pzbl; //���ñ���;�ƻ�BOM��Ϊ�ٷֱ�
	public UFDouble pztqq; //ƫ����ǰ��	
	public String sdate; //��Ч����
	public UFBoolean sfkx; //�Ƿ��ѡ��
	public UFBoolean sfqs;//�Ƿ�ȱʡ����
	public UFBoolean sfslkx; //�Ƿ�������ѡ
	public UFBoolean sfwwfl; //�Ƿ�ί�ⷢ��
	public UFDouble slsx; //��������
	public UFDouble slxx; //��������
	public String wlglid; //���Ϲ���ID
	public String zzgx; //��װ����

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 14:02:27)
 * @return java.lang.String
 */
public java.lang.String getBz() {
	return bz;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-04 16:55:51)
 * @return java.lang.Integer
 */
public java.lang.Integer getCm() {
	return cm;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-3 18:43:27)
 * @return java.lang.String
 */
public java.lang.String getEdate() {
	return edate;
}



/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return java.lang.String
 */
public java.lang.String getHh() {
	return hh;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-03 16:14:33)
 * @return java.lang.String
 */
public java.lang.String getJldwid() {
	return jldwid;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2003-4-16 11:01:12)
 * @return java.lang.String
 */
public java.lang.String getPk_produce() {
	return pk_produce;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getPzbl() {
	return pzbl;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-10-01 13:22:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getPztqq() {
	return pztqq;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-3 18:43:27)
 * @return java.lang.String
 */
public java.lang.String getSdate() {
	return sdate;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfkx() {
	return sfkx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 14:02:27)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfqs() {
	return sfqs;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfslkx() {
	return sfslkx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-10-01 13:45:52)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfwwfl() {
	return sfwwfl;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getSlsx() {
	return slsx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getSlxx() {
	return slxx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @return java.lang.String
 */
public java.lang.String getWlglid() {
	return wlglid;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-02 19:06:40)
 * @return java.lang.String
 */
public java.lang.String getZzgx() {
	return zzgx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 14:02:27)
 * @param newBz java.lang.String
 */
public void setBz(java.lang.String newBz) {
	bz = newBz;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-04 16:55:51)
 * @param newCm java.lang.Integer
 */
public void setCm(java.lang.Integer newCm) {
	cm = newCm;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-3 18:43:27)
 * @param newEdate java.lang.String
 */
public void setEdate(java.lang.String newEdate) {
	edate = newEdate;
}



/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newHh java.lang.String
 */
public void setHh(java.lang.String newHh) {
	hh = newHh;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-03 16:14:33)
 * @param newJldwid java.lang.String
 */
public void setJldwid(java.lang.String newJldwid) {
	jldwid = newJldwid;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2003-4-16 11:01:12)
 * @param newPk_produce java.lang.String
 */
public void setPk_produce(java.lang.String newPk_produce) {
	pk_produce = newPk_produce;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newPzbl nc.vo.pub.lang.UFDouble
 */
public void setPzbl(nc.vo.pub.lang.UFDouble newPzbl) {
	pzbl = newPzbl;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-10-01 13:22:31)
 * @param newPztqq nc.vo.pub.lang.UFDouble
 */
public void setPztqq(nc.vo.pub.lang.UFDouble newPztqq) {
	pztqq = newPztqq;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-3 18:43:27)
 * @param newSdate java.lang.String
 */
public void setSdate(java.lang.String newSdate) {
	sdate = newSdate;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newSfkx nc.vo.pub.lang.UFBoolean
 */
public void setSfkx(nc.vo.pub.lang.UFBoolean newSfkx) {
	sfkx = newSfkx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 14:02:27)
 * @param newSfqs nc.vo.pub.lang.UFBoolean
 */
public void setSfqs(nc.vo.pub.lang.UFBoolean newSfqs) {
	sfqs = newSfqs;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newSfslkx nc.vo.pub.lang.UFBoolean
 */
public void setSfslkx(nc.vo.pub.lang.UFBoolean newSfslkx) {
	sfslkx = newSfslkx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-10-01 13:45:52)
 * @param newSfwwfl nc.vo.pub.lang.UFBoolean
 */
public void setSfwwfl(nc.vo.pub.lang.UFBoolean newSfwwfl) {
	sfwwfl = newSfwwfl;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newSlsx nc.vo.pub.lang.UFDouble
 */
public void setSlsx(nc.vo.pub.lang.UFDouble newSlsx) {
	slsx = newSlsx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newSlxx nc.vo.pub.lang.UFDouble
 */
public void setSlxx(nc.vo.pub.lang.UFDouble newSlxx) {
	slxx = newSlxx;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2002-4-5 13:04:31)
 * @param newWlglid java.lang.String
 */
public void setWlglid(java.lang.String newWlglid) {
	wlglid = newWlglid;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-02 19:06:40)
 * @param newZzgx java.lang.String
 */
public void setZzgx(java.lang.String newZzgx) {
	zzgx = newZzgx;
}

	//2003-09-16 del ZhangJ instead gyfs
	//public Integer gylx; //��Ӧ����
	public Integer gyfs ;

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-03 16:14:33)
 * @return java.lang.Integer
 */
public java.lang.Integer getGyfs() {
	return gyfs;
}

/**
 * �˴����뷽��˵����
 * �������ڣ�(2001-07-03 16:14:33)
 * @param newGylx java.lang.Integer
 */
public void setGyfs(java.lang.Integer newGyfs) {
	gyfs = newGyfs;
}
}