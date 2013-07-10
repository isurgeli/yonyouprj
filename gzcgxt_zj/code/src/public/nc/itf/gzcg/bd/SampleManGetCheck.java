package nc.itf.gzcg.bd;

import java.io.Serializable;

import nc.bs.framework.server.util.NewObjectService;
import nc.bs.gzcg.bd.SampleManCheckBack;
import nc.bs.trade.business.IBDBusiCheck;
import nc.bs.trade.comsave.IQueryAfterSave;
import nc.ui.gzcg.bd.SampleManCheckBefore;
import nc.vo.gzcg.bd.samplevo;
import nc.vo.trade.pub.IBDGetCheckClass2;
import nc.vo.trade.pub.IRetCurrentDataAfterSave;
import nc.vo.trade.pub.IServerSideFactory;

public class SampleManGetCheck implements IBDGetCheckClass2,IServerSideFactory,IRetCurrentDataAfterSave,Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6107551126134734515L;
	
	private String queryAfterSave_SqlWhere;
	/**
	 * @param queryAfterSave_SqlWhere
	 */
	public SampleManGetCheck(String queryAfterSave_SqlWhere)
	{
		super();
		this.queryAfterSave_SqlWhere = queryAfterSave_SqlWhere;
	}
	/* （非 Javadoc）
	 * @see nc.vo.trade.pub.IBDGetCheckClass2#getUICheckClass()
	 */
	public String getUICheckClass() {
		return SampleManCheckBefore.class.getName();
	}

	/* （非 Javadoc）
	 * @see nc.vo.trade.pub.IBDGetCheckClass#getCheckClass()
	 */
	public String getCheckClass() {
		
		return SampleManCheckBack.class.getName();
	}
	/* （非 Javadoc）
	 * @see nc.vo.trade.pub.IServerSideFactory#getQueryAfterSaveInstance()
	 */
	public IQueryAfterSave getQueryAfterSaveInstance()
	{
//		return new SingleTableQueryAfterSave(new CurrtypeVO(), queryAfterSave_SqlWhere);
		return (IQueryAfterSave) NewObjectService.newInstance("qc","nc.bs.trade.comsave.SingleTableQueryAfterSave",new Object[] {new samplevo(), queryAfterSave_SqlWhere});
	}
	/* （非 Javadoc）
	 * @see nc.vo.trade.pub.IServerSideFactory#getBDBusiCheckInstance()
	 */
	public IBDBusiCheck getBDBusiCheckInstance()
	{
		return (IBDBusiCheck)NewObjectService.newInstance("qc",SampleManCheckBack.class.getName());
	}

}
