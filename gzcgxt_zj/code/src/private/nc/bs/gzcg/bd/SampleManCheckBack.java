package nc.bs.gzcg.bd;

import java.util.ArrayList;

import nc.bs.bd.pub.AbstractBDBackCheck;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.uap.bd.refcheck.IReferenceCheck;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;

public class SampleManCheckBack extends AbstractBDBackCheck {
	/*
	 *  返回待检查字段
	 */
	public ArrayList getFieldArray() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{samplevo.VDEF1, samplevo.VSAMPLENO});
		return list;
	}
/*
 *  （非 Javadoc）
 * @see nc.bs.bd.pub.AbstractBDBackCheck#isEditRef(nc.vo.pub.SuperVO, nc.bs.bd.pub.BDReferenceImp)
 */
	protected boolean isEditRef(SuperVO vo, IReferenceCheck imp)
			throws BusinessException {
		return false;
	}
	/* （非 Javadoc）
	 * @see nc.bs.bd.pub.AbstractBDBackCheck#needChkRefWhenThisFiledsIsChanged()
	 */
	protected String[] needChkRefWhenThisFiledsIsChanged() {
		return new samplevo().getAttributeNames();
	}
	/*
	 * 返回字段名
	 */
	public ArrayList getNameArray() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"存货", "检验批次"});
		return list;
	}

	/*
	 * 表体 true
	 */
	public boolean isDetail() {

		return true;
	}

	/*
	 * 单表体 true
	 */
	public boolean isSingleTable() {

		return true;
	}

	/* （非 Javadoc）
	 * @see nc.bs.bd.pub.AbstractBDBackCheck#getKey()
	 */
	public String[] getKey() {
		return new String[] {samplevo.VDEF1, samplevo.VSAMPLENO};
	}
	/* （非 Javadoc）
	 * @see nc.bs.bd.pub.AbstractBDBackCheck#getFun_code()
	 */
	public String getFun_code() {
		return GZCGConstant.SAMPLEUIFUNCODE.getValue();
	}

}