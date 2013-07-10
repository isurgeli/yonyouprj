package nc.ui.gzcg.bd;

import java.awt.Container;
import java.io.Serializable;

import nc.ui.trade.businessaction.IPFACTION;
import nc.ui.trade.check.BeforeActionCHK;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.trade.checkrule.VOChecker;

public class SampleManCheckBefore extends BeforeActionCHK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* £¨·Ç Javadoc£©
	 * @see nc.ui.pub.pf.IUIBeforeProcAction#runBatchClass(java.awt.Container, java.lang.String, java.lang.String, nc.vo.pub.AggregatedValueObject[], java.lang.Object[])
	 */
	public void runBatchClass(Container parent, String billType, String actionName, AggregatedValueObject[] vos, Object[] obj) throws Exception {
	}

	/* £¨·Ç Javadoc£©
	 * @see nc.ui.pub.pf.IUIBeforeProcAction#runClass(java.awt.Container, java.lang.String, java.lang.String, nc.vo.pub.AggregatedValueObject, java.lang.Object)
	 */
	public void runClass(Container parent, String billType, String actionName, AggregatedValueObject vo, Object obj) throws Exception {
		if(actionName.equals(IPFACTION.SAVE))
			if(!VOChecker.check(vo,new SampleManCheckRules()))
				throw new nc.vo.pub.BusinessException(VOChecker.getErrorMessage());
	}
}
