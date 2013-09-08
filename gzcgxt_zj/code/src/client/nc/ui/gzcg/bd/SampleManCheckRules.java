package nc.ui.gzcg.bd;

import nc.vo.gzcg.bd.samplevo;
import nc.vo.trade.checkrule.CheckRule;
import nc.vo.trade.checkrule.ICheckRule;
import nc.vo.trade.checkrule.ICheckRules;
import nc.vo.trade.checkrule.ICompareRule;
import nc.vo.trade.checkrule.IUniqueRule;
import nc.vo.trade.checkrule.IUniqueRules;
import nc.vo.trade.checkrule.UniqueRule;

public class SampleManCheckRules implements ICheckRules,IUniqueRules {

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadCheckRules()
	 */
	public ICheckRule[] getHeadCheckRules() {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemCheckRules(java.lang.String)
	 */
	public ICheckRule[] getItemCheckRules(String tablecode) {
		return new CheckRule[]{ new CheckRule("��������", samplevo.VSAMPLENO, false, null, null) };
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadCompareRules()
	 */
	public ICompareRule[] getHeadCompareRules() {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadIntegerField()
	 */
	public String[] getHeadIntegerField() {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getHeadUFDoubleField()
	 */
	public String[] getHeadUFDoubleField() {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemCompareRules(java.lang.String)
	 */
	public ICompareRule[] getItemCompareRules(String tablecode) {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemIntegerField(java.lang.String)
	 */
	public String[] getItemIntegerField(String tablecode) {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.ICheckRules#getItemUFDoubleField(java.lang.String)
	 */
	public String[] getItemUFDoubleField(String tablecode) {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.IUniqueRules#getHeadUniqueRules()
	 */
	public IUniqueRule[] getHeadUniqueRules() {
		return null;
	}

	/* ���� Javadoc��
	 * @see nc.vo.trade.checkrule.IUniqueRules#getItemUniqueRules(java.lang.String)
	 */
	public IUniqueRule[] getItemUniqueRules(String tablecode) {
		return new IUniqueRule[]{
			new UniqueRule("���+���������ظ�",new String[]{samplevo.VDEF1, samplevo.VSAMPLENO})
		};
	}
}
