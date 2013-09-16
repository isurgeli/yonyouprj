package nc.vo.sc.order;

import nc.vo.mm.pub.MMCircularlyAccessibleValueObject;
import nc.vo.pub.lang.*;

/**
 * 标准分解结果;//中和了计划BOM，配置BOM，生产BOM
 * 创建日期：(2001-6-30 22:54:09)
 * @author：谢志华
 */
public class CGDisassembleVO extends MMCircularlyAccessibleValueObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String pk_corp; //公司ID
	public String gcbm; //工厂ID
	public String pk; //主键
	public String wlbmid; //物料ID(集团）;;//可以是计划BOM的分类ID

	public UFDouble sl; //标准数量
	public UFDouble shxs; //损耗系数
	public String flckid; //发料仓库ID
	public UFBoolean sfth; //是否可替换
	public Integer zxlx; //子项类型;计划BOM下为分类和物料两种
	public Integer kzbz; //控制标志
	
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
 * DisassembleVO 构造子注解。
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
 * getEntityName 方法注解。
 */
public String getEntityName() {
	return null;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getFlckid() {
	return flckid;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getGcbm() {
	return gcbm;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-03 16:14:33)
 * @return java.lang.Integer
 */
public java.lang.Integer getKzbz() {
	return kzbz;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getPk() {
	return pk;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getPk_corp() {
	return pk_corp;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfth() {
	return sfth;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getShxs() {
	return shxs;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getSl() {
	return sl;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return java.lang.String
 */
public java.lang.String getWlbmid() {
	return wlbmid;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @return java.lang.Integer
 */
public java.lang.Integer getZxlx() {
	return zxlx;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newFlckid java.lang.String
 */
public void setFlckid(java.lang.String newFlckid) {
	flckid = newFlckid;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newGcbm java.lang.String
 */
public void setGcbm(java.lang.String newGcbm) {
	gcbm = newGcbm;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-03 16:14:33)
 * @param newKzbz java.lang.Integer
 */
public void setKzbz(java.lang.Integer newKzbz) {
	kzbz = newKzbz;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newPk java.lang.String
 */
public void setPk(java.lang.String newPk) {
	pk = newPk;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newPk_corp java.lang.String
 */
public void setPk_corp(java.lang.String newPk_corp) {
	pk_corp = newPk_corp;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newSfth nc.vo.pub.lang.UFBoolean
 */
public void setSfth(nc.vo.pub.lang.UFBoolean newSfth) {
	sfth = newSfth;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newShxs nc.vo.pub.lang.UFDouble
 */
public void setShxs(nc.vo.pub.lang.UFDouble newShxs) {
	shxs = newShxs;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newSl nc.vo.pub.lang.UFDouble
 */
public void setSl(nc.vo.pub.lang.UFDouble newSl) {
	sl = newSl;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newWlbmid java.lang.String
 */
public void setWlbmid(java.lang.String newWlbmid) {
	wlbmid = newWlbmid;
}
/**
 * 此处插入方法说明。
 * 创建日期：(2001-6-30 23:19:16)
 * @param newZxlx java.lang.Integer
 */
public void setZxlx(java.lang.Integer newZxlx) {
	zxlx = newZxlx;
}
/**
 * validate 方法注解。
 */
public void validate() throws nc.vo.pub.ValidationException {}

	public String bz;//备注，配置BOM的提示信息
	public Integer cm; //层码
	public String edate; //失效日期

	public String hh; //行号
	public String jldwid; //计量单位ID	
	public String pk_produce;//物料工厂PK
	public UFDouble pzbl; //配置比率;计划BOM中为百分比
	public UFDouble pztqq; //偏置提前期	
	public String sdate; //生效日期
	public UFBoolean sfkx; //是否可选件
	public UFBoolean sfqs;//是否缺省配置
	public UFBoolean sfslkx; //是否数量可选
	public UFBoolean sfwwfl; //是否委外发料
	public UFDouble slsx; //数量上限
	public UFDouble slxx; //数量下限
	public String wlglid; //物料管理ID
	public String zzgx; //组装工序

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 14:02:27)
 * @return java.lang.String
 */
public java.lang.String getBz() {
	return bz;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-04 16:55:51)
 * @return java.lang.Integer
 */
public java.lang.Integer getCm() {
	return cm;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-3 18:43:27)
 * @return java.lang.String
 */
public java.lang.String getEdate() {
	return edate;
}



/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return java.lang.String
 */
public java.lang.String getHh() {
	return hh;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-03 16:14:33)
 * @return java.lang.String
 */
public java.lang.String getJldwid() {
	return jldwid;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2003-4-16 11:01:12)
 * @return java.lang.String
 */
public java.lang.String getPk_produce() {
	return pk_produce;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getPzbl() {
	return pzbl;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-10-01 13:22:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getPztqq() {
	return pztqq;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-3 18:43:27)
 * @return java.lang.String
 */
public java.lang.String getSdate() {
	return sdate;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfkx() {
	return sfkx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 14:02:27)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfqs() {
	return sfqs;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfslkx() {
	return sfslkx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-10-01 13:45:52)
 * @return nc.vo.pub.lang.UFBoolean
 */
public nc.vo.pub.lang.UFBoolean getSfwwfl() {
	return sfwwfl;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getSlsx() {
	return slsx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return nc.vo.pub.lang.UFDouble
 */
public nc.vo.pub.lang.UFDouble getSlxx() {
	return slxx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @return java.lang.String
 */
public java.lang.String getWlglid() {
	return wlglid;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-02 19:06:40)
 * @return java.lang.String
 */
public java.lang.String getZzgx() {
	return zzgx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 14:02:27)
 * @param newBz java.lang.String
 */
public void setBz(java.lang.String newBz) {
	bz = newBz;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-04 16:55:51)
 * @param newCm java.lang.Integer
 */
public void setCm(java.lang.Integer newCm) {
	cm = newCm;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-3 18:43:27)
 * @param newEdate java.lang.String
 */
public void setEdate(java.lang.String newEdate) {
	edate = newEdate;
}



/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newHh java.lang.String
 */
public void setHh(java.lang.String newHh) {
	hh = newHh;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-03 16:14:33)
 * @param newJldwid java.lang.String
 */
public void setJldwid(java.lang.String newJldwid) {
	jldwid = newJldwid;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2003-4-16 11:01:12)
 * @param newPk_produce java.lang.String
 */
public void setPk_produce(java.lang.String newPk_produce) {
	pk_produce = newPk_produce;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newPzbl nc.vo.pub.lang.UFDouble
 */
public void setPzbl(nc.vo.pub.lang.UFDouble newPzbl) {
	pzbl = newPzbl;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-10-01 13:22:31)
 * @param newPztqq nc.vo.pub.lang.UFDouble
 */
public void setPztqq(nc.vo.pub.lang.UFDouble newPztqq) {
	pztqq = newPztqq;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-3 18:43:27)
 * @param newSdate java.lang.String
 */
public void setSdate(java.lang.String newSdate) {
	sdate = newSdate;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newSfkx nc.vo.pub.lang.UFBoolean
 */
public void setSfkx(nc.vo.pub.lang.UFBoolean newSfkx) {
	sfkx = newSfkx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 14:02:27)
 * @param newSfqs nc.vo.pub.lang.UFBoolean
 */
public void setSfqs(nc.vo.pub.lang.UFBoolean newSfqs) {
	sfqs = newSfqs;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newSfslkx nc.vo.pub.lang.UFBoolean
 */
public void setSfslkx(nc.vo.pub.lang.UFBoolean newSfslkx) {
	sfslkx = newSfslkx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-10-01 13:45:52)
 * @param newSfwwfl nc.vo.pub.lang.UFBoolean
 */
public void setSfwwfl(nc.vo.pub.lang.UFBoolean newSfwwfl) {
	sfwwfl = newSfwwfl;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newSlsx nc.vo.pub.lang.UFDouble
 */
public void setSlsx(nc.vo.pub.lang.UFDouble newSlsx) {
	slsx = newSlsx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newSlxx nc.vo.pub.lang.UFDouble
 */
public void setSlxx(nc.vo.pub.lang.UFDouble newSlxx) {
	slxx = newSlxx;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2002-4-5 13:04:31)
 * @param newWlglid java.lang.String
 */
public void setWlglid(java.lang.String newWlglid) {
	wlglid = newWlglid;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-02 19:06:40)
 * @param newZzgx java.lang.String
 */
public void setZzgx(java.lang.String newZzgx) {
	zzgx = newZzgx;
}

	//2003-09-16 del ZhangJ instead gyfs
	//public Integer gylx; //供应类型
	public Integer gyfs ;

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-03 16:14:33)
 * @return java.lang.Integer
 */
public java.lang.Integer getGyfs() {
	return gyfs;
}

/**
 * 此处插入方法说明。
 * 创建日期：(2001-07-03 16:14:33)
 * @param newGylx java.lang.Integer
 */
public void setGyfs(java.lang.Integer newGyfs) {
	gyfs = newGyfs;
}
}