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
//lxt 2013-10 gzcg���������۷�����ʱ�����Ʒ�������ѯ��
@SuppressWarnings("restriction")
public class ReceiveTo4KRedunSourceCtrl extends AbstractSORedunSourceCtrl implements ISourceQuery{
	
	public UIDialog soTo4CDLG;//���淢����to���ⵥת����ѯģ��
	
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
	 * @return Where��������
	 */
	private String prepareQuery(Map userObj){
		// 1.��ʾ��ѯ�Ի���
		if (getSoTo4CDLG().showModal() == UIDialog.ID_CANCEL)
			return null;

		// 2.��ѯ����
		INormalQuery query = (INormalQuery) getSoTo4CDLG();
		String strWhere = query.getWhereSql();			
		
		// ��ӹ�������:��ԴΪ���۶��������δ�رյı���˾������
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
	 * ������������
	 * @param result
	 */
	private void setVOToUI(Object[] result){
		try {
		// ������������
		redunSourcePanel.clearSourceBill();
		if(result!=null && result[0]!=null && ((Object[])result[0]).length>0){
			if (result[2]!=null && !result[2].toString().equals(""))
				redunSourcePanel.showHintMessage(result[3].toString());
			
			//vo��ʼ������
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
	 * ��VO�������ǰ����ʼ��vo����
	 * @param srcHeadVOs
	 * @param srcBodyVOs
	 * @throws Exception 
	 */
	private void initVOs(CircularlyAccessibleValueObject[] srcHeadVOs,
			CircularlyAccessibleValueObject[] srcBodyVOs) throws Exception{
		
		//�����Ƿ�̶�������
		SoVoTools.execFormulas(getReceiveUITools().fixedflagFormulas, srcBodyVOs);
		
		//����������
		super.setFreeItem(srcBodyVOs);
	}
	
	/**
	 * @return ��ԴΪ���۶����Ĺ�������
	 */
	protected String getWhereSubSql(){
		return " (so_salereceive_b.vsourcetype = '30')";
	}
	
	/**
	 * Object[0]----��������ͷCircularlyAccessibleValueObject[]
	 * Object[1]----����������CircularlyAccessibleValueObject[]
	 * @param queryVos
	 * @return
	 */
	private Object[] getSplitVO(AggregatedValueObject[] queryVos){
       
		ArrayList<CircularlyAccessibleValueObject> h_list = new ArrayList<CircularlyAccessibleValueObject>();
		ArrayList<CircularlyAccessibleValueObject> b_list = new ArrayList<CircularlyAccessibleValueObject>();
		for (int i = 0; i < queryVos.length; i++) {
			// ��ӱ�ͷ
			h_list.add(queryVos[i].getParentVO());
			// ��ӱ���
			for (int j = 0; j < queryVos[i].getChildrenVO().length; j++)
				b_list.add(queryVos[i].getChildrenVO()[j]);
		}
		
        // ��������ͷ
		CircularlyAccessibleValueObject[] heads = new CircularlyAccessibleValueObject[queryVos.length];
		heads = h_list.toArray(heads);
		
        // ����������
		CircularlyAccessibleValueObject[] bodys = new CircularlyAccessibleValueObject[b_list.size()];
		bodys = b_list.toArray(bodys);
		
		//���ؽ��
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
	 * @return ��������ѯ�Ի����ṩ�����ת����
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
		// TODO �Զ����ɷ������
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