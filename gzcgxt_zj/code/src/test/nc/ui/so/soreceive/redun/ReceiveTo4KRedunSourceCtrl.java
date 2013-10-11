package nc.ui.so.soreceive.redun;

import java.util.ArrayList;
import java.util.Map;

import nc.ui.pub.beans.UIDialog;
import nc.ui.scm.pub.redunmulti.DisplayMode;
import nc.ui.scm.pub.redunmulti.ISourceQuery;
import nc.ui.scm.so.RedunVOTool;
import nc.ui.so.pub.redun.AbstractSORedunSourceCtrl;
import nc.ui.so.pub.redun.AbstractSORedunSourcePanel;
import nc.ui.so.soreceive.ReceiveServiceBO_Client;
import nc.ui.so.soreceive.pub.ReceiveUITools;
import nc.ui.trade.query.HYQueryConditionDLG;
import nc.ui.trade.query.INormalQuery;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.querytemplate.TemplateInfo;
import nc.vo.so.so016.SoVoTools;
import nc.vo.trade.pub.IBillStatus;
//lxt 2013-10 gzcg：关联销售发货单时，控制发货单查询。
@SuppressWarnings("restriction")
public class ReceiveTo4KRedunSourceCtrl extends AbstractSORedunSourceCtrl implements ISourceQuery{
	
	public UIDialog soTo4CDLG;//缓存发货单to出库单转单查询模板
	
	private ReceiveUITools rtools = null;

	@Override
	protected AbstractSORedunSourcePanel createAbstractSORedunSourcePanel() {
		return new ReceiveToICRedunSourcePanel();
	}

	@Override
	public ISourceQuery getISourceQuery() {
		return this;
	}
	
	public boolean query(Map userObj) {
		
		String where = prepareQuery(userObj);
		if (where == null)
			return false;

		processQuery(where);
		
		return false;
	}

	@Override
	public Object queryAllBillDatas() throws BusinessException {
		return query(null);
	}
	
	/**
	 * @return Where过滤条件
	 */
	private String prepareQuery(Map userObj){
		// 1.显示查询对话框
		if (getSoTo4CDLG().showModal() == UIDialog.ID_CANCEL)
			return null;

		// 2.查询数据
		INormalQuery query = (INormalQuery) getSoTo4CDLG();
		String strWhere = query.getWhereSql();			
		
		// 添加过滤条件:来源为销售订单的审核未关闭的本公司发货单
		if (strWhere == null)
			strWhere = "(SO_SALERECEIVE.fstatus=" + IBillStatus.CHECKPASS
					+ ") and " + getWhereSubSql()
					+ " and (SO_SALERECEIVE.pk_corp = '" + getCorp() + "')";
		else
			strWhere = "(" + strWhere + ") and (SO_SALERECEIVE.fstatus="
					+ IBillStatus.CHECKPASS + ") and " + getWhereSubSql()
					+ " and (SO_SALERECEIVE.pk_corp = '" + getCorp() + "')";
		
		strWhere += " and (SO_SALERECEIVE.dr=0 and so_salereceive_b.dr=0) and (so_salereceive_b.bifrowclose = 'N' or so_salereceive_b.bifrowclose is null)";
		// LXT
		strWhere += " and so_salereceive_b.nnumber-nvl(to_number(so_salereceive_b.vdef20),0)>0 and nvl(so_salereceive_b.ntotaloutinvnum,0)=0 "; 
		if (userObj!=null){
			strWhere += " and SO_SALERECEIVE.cbiztype='"+userObj.get("cbiztype").toString()+"'";
		}
		
		return strWhere;
	}
	
	private void processQuery(String where){
		try {	
			Object[] result = ReceiveServiceBO_Client.queryAllBillDatas(where);
			setVOToUI(result);
		} catch (Exception e) {
			redunSourcePanel.handleException(e);
		}
	}
	
	/**
	 * 将结果置入界面
	 * @param result
	 */
	private void setVOToUI(Object[] result){
		try {
		// 将结果置入界面
		redunSourcePanel.clearSourceBill();
		if(result!=null && result[0]!=null && ((Object[])result[0]).length>0){
			if (result[2]!=null && !result[2].toString().equals(""))
				redunSourcePanel.showHintMessage(result[3].toString());
			
			//vo初始化数据
			initVOs((CircularlyAccessibleValueObject[]) result[0], (CircularlyAccessibleValueObject[]) result[1]);
			
			AggregatedValueObject[] tempVos = RedunVOTool.getBillVos(redunSourcePanel.getSourceVoClassName(), 
					redunSourcePanel.getSourcevoPkname(), 
					(CircularlyAccessibleValueObject[]) result[0], 
					(CircularlyAccessibleValueObject[]) result[1]);
			
			redunSourcePanel.setSourceVOToUI(
					(CircularlyAccessibleValueObject[]) result[0],
					(CircularlyAccessibleValueObject[]) result[1]);
		}
		} catch (Exception e) {
			redunSourcePanel.handleException(e);
		}
	}
	
	/**
	 * 将VO置入界面前，初始化vo数据
	 * @param srcHeadVOs
	 * @param srcBodyVOs
	 * @throws Exception 
	 */
	private void initVOs(CircularlyAccessibleValueObject[] srcHeadVOs,
			CircularlyAccessibleValueObject[] srcBodyVOs) throws Exception{
		
		//设置是否固定换算率
		SoVoTools.execFormulas(getReceiveUITools().fixedflagFormulas, srcBodyVOs);
		
		//设置自由项
		super.setFreeItem(srcBodyVOs);
	}
	
	/**
	 * @return 来源为销售订单的过滤条件
	 */
	protected String getWhereSubSql(){
		return " (so_salereceive_b.vsourcetype = '30')";
	}
	
	/**
	 * Object[0]----发货单表头CircularlyAccessibleValueObject[]
	 * Object[1]----发货单表体CircularlyAccessibleValueObject[]
	 * @param queryVos
	 * @return
	 */
	private Object[] getSplitVO(AggregatedValueObject[] queryVos){
       
		ArrayList<CircularlyAccessibleValueObject> h_list = new ArrayList<CircularlyAccessibleValueObject>();
		ArrayList<CircularlyAccessibleValueObject> b_list = new ArrayList<CircularlyAccessibleValueObject>();
		for (int i = 0; i < queryVos.length; i++) {
			// 添加表头
			h_list.add(queryVos[i].getParentVO());
			// 添加表体
			for (int j = 0; j < queryVos[i].getChildrenVO().length; j++)
				b_list.add(queryVos[i].getChildrenVO()[j]);
		}
		
        // 发货单表头
		CircularlyAccessibleValueObject[] heads = new CircularlyAccessibleValueObject[queryVos.length];
		heads = h_list.toArray(heads);
		
        // 发货单表体
		CircularlyAccessibleValueObject[] bodys = new CircularlyAccessibleValueObject[b_list.size()];
		bodys = b_list.toArray(bodys);
		
		//返回结果
		Object[] ret = new Object[2];
		ret[0] = heads;
		ret[1] = bodys;
		return ret;
	}

	@Override
	public void switchDisplayMode(DisplayMode mode) {
		
	}

	public UIDialog getSoTo4CDLG() {
		if (soTo4CDLG == null){
			soTo4CDLG = createQueryDLG();
		}
		return soTo4CDLG;
	}
	
	/**
	 * @return 发货单查询对话框（提供给库存转单）
	 */
	protected UIDialog createQueryDLG(){
		TemplateInfo tempinfo = new TemplateInfo();
		tempinfo.setPk_Org(redunSourcePanel.getCorp());
		tempinfo.setCurrentCorpPk(redunSourcePanel.getCorp());
		tempinfo.setFunNode("40060401");
		tempinfo.setUserid(redunSourcePanel.getOperator());
		tempinfo.setBusiType(null);
		tempinfo.setNodekey("4331_IC");

		return new HYQueryConditionDLG(redunSourcePanel, null, tempinfo);
	}

	public AggregatedValueObject getSelectedRowVO() {
		// TODO 自动生成方法存根
		return null;
	}
	
	public ReceiveUITools getReceiveUITools(){
		if (rtools == null){
			rtools = new ReceiveUITools();
		}
		return rtools;
	}

	/* (non-Javadoc)
	 * @see nc.ui.scm.pub.redunmulti.ISourceQuery#getBills()
	 */
	public AggregatedValueObject[] getBills() {
		// TODO Auto-generated method stub
		return null;
	}
		
}