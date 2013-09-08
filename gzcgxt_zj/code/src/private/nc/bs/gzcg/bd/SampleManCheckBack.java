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
	 *  ���ش�����ֶ�
	 */
	public ArrayList getFieldArray() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{samplevo.VDEF1, samplevo.VSAMPLENO});
		return list;
	}
/*
 *  ���� Javadoc��
 * @see nc.bs.bd.pub.AbstractBDBackCheck#isEditRef(nc.vo.pub.SuperVO, nc.bs.bd.pub.BDReferenceImp)
 */
	protected boolean isEditRef(SuperVO vo, IReferenceCheck imp)
			throws BusinessException {
		return false;
	}
	/* ���� Javadoc��
	 * @see nc.bs.bd.pub.AbstractBDBackCheck#needChkRefWhenThisFiledsIsChanged()
	 */
	protected String[] needChkRefWhenThisFiledsIsChanged() {
		return new samplevo().getAttributeNames();
	}
	/*
	 * �����ֶ���
	 */
	public ArrayList getNameArray() {
		ArrayList<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"���", "��������"});
		return list;
	}

	/*
	 * ���� true
	 */
	public boolean isDetail() {

		return true;
	}

	/*
	 * ������ true
	 */
	public boolean isSingleTable() {

		return true;
	}

	/* ���� Javadoc��
	 * @see nc.bs.bd.pub.AbstractBDBackCheck#getKey()
	 */
	public String[] getKey() {
		return new String[] {samplevo.VDEF1, samplevo.VSAMPLENO};
	}
	/* ���� Javadoc��
	 * @see nc.bs.bd.pub.AbstractBDBackCheck#getFun_code()
	 */
	public String getFun_code() {
		return GZCGConstant.SAMPLEUIFUNCODE.getValue();
	}

}