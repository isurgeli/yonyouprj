package nc.ui.sc.ddlbreport;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.sc.order.ISCForCGOrderService;
import nc.ui.pf.change.PfUtilUITools;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.trade.bill.ICardController;
import nc.ui.trade.businessaction.BusinessAction;
import nc.ui.trade.businessaction.IBusinessActionType;
import nc.ui.trade.businessaction.IBusinessController;
import nc.ui.trade.card.BillCardUI;
import nc.ui.trade.card.CardEventHandler;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.sc.order.OrderDdlbVO;
import nc.vo.sc.order.OrderHeaderVO;
import nc.vo.sc.order.OrderItemVO;
import nc.vo.sc.order.OrderReportVO;
import nc.vo.sc.order.OrderVO;

/**
 */
public class ClientEventHandler extends CardEventHandler {

	/**
	 * 说明：
	 * 
	 */
	public ClientEventHandler(BillCardUI billUI, ICardController control) {
		super(billUI, control);
		// TODO 自动生成构造函数存根
	}
	
	@Override
	protected void onBoElse(int intBtn) throws Exception {
		if(intBtn == 1000){
			onLinkquery();
		}
		super.onBoElse(intBtn);
	}
	
	@Override
	protected void onBoRefresh() throws Exception {
		((ClientUI)getBillUI()).readData();
	}
	
	public void onLinkquery(){
		int selectRow = getBillCardPanelWrapper().getBillCardPanel().getBillTable().getSelectedRow();
		if (selectRow < 0) {
			MessageDialog.showHintDlg(getBillUI(), "提示", "请选中要联查的行");
			return;
		}
		OrderDdlbVO vo;
		try {
			vo = (OrderDdlbVO) getBillCardPanelWrapper().getBillCardPanel().getBillModel().getBodyValueRowVO(selectRow, getUICardController().getBillVoName()[1]);
			LinkQueryData billQuery = new LinkQueryData();
			billQuery.setBillID(vo.getCorderid());
			billQuery.setBillType((String) vo.getAttributeValue("pk_billtype"));
			billQuery.setPkOrg((String) vo.getAttributeValue("pk_corp"));
			SFClientUtil.openLinkedQueryDialog(getFuncode((String) vo.getAttributeValue("pk_billtype"),billQuery),getBillUI(),billQuery);

		} catch (Exception e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(e.getMessage());
		} 
	}
	
	private String getFuncode(String pk_billType,ILinkQueryData lqd) throws BusinessException{
		return PfUtilUITools.findNodecodeOfBilltype(pk_billType, lqd);
	}
	@Override
	protected IBusinessController createBusinessAction() {
		// TODO 自动生成方法存根
		switch (getUIController().getBusinessActionType()) {
		case IBusinessActionType.PLATFORM:
			return new BusinessAction(getBillUI());
		case IBusinessActionType.BD:
			return new XHBdBusinessAction(getBillUI());
		default:
			return new BusinessAction(getBillUI());
		}
	}
	
	private OrderReportVO[] query(String condition){
		ISCForCGOrderService service = NCLocator.getInstance().lookup(ISCForCGOrderService.class);
		OrderVO[] billvo = null;
		try {
			billvo = service.queryBillVO(condition);
		} catch (SQLException e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(e.getMessage());
		} catch (BusinessException e) {
			e.printStackTrace();
			getBillUI().showErrorMessage(e.getMessage());
		}
		if(billvo==null) return null;
		
		List<OrderReportVO> list = new ArrayList<OrderReportVO>();
		for (int i = 0; i < billvo.length; i++) {
			OrderHeaderVO headvo = (OrderHeaderVO) billvo[i].getParentVO();
			
			//按加工品和材料组合，来订行数
			OrderItemVO[] items = (OrderItemVO[]) billvo[i].getChildrenVO();
			OrderDdlbVO[] ddlbs = (OrderDdlbVO[]) billvo[i].getDdlbvos();
			for (int j = 0; j < items.length; j++) {
				for (int j2 = 0; j2 < ddlbs.length; j2++) {
					OrderReportVO value = new OrderReportVO();
					String[] names = value.getAttributeNames();
					for (int n = 0; n < names.length; n++) {
						if(names[n].startsWith("b_")){
							value.setAttributeValue(names[n], items[j].getAttributeValue(names[n].replace("b_", "")));
						}else if(names[n].startsWith("d_")){
							value.setAttributeValue(names[n], ddlbs[j2].getAttributeValue(names[n].replace("d_", "")));
						}else{
							value.setAttributeValue(names[n], headvo.getAttributeValue(names[n]));
						}
					}
					list.add(value);
				}
			}
		}
		OrderReportVO []valueVO = list.toArray( new OrderReportVO[0]);
		if(valueVO == null)
			return null;
		//自由项显示
		for (int i = 0; i < valueVO.length; i++) {
			String b_free = "";
			String d_free = "";
			for (int j = 0; j < 5; j++) {
				String temp = (String) valueVO[i].getAttributeValue("b_vfree"+(j+1));
				temp = temp == null ? "" : temp;
				b_free += temp;
				
				temp = (String) valueVO[i].getAttributeValue("d_vfree"+(j+1));
				temp = temp == null ? "" : temp;
				d_free += temp;
			}
			valueVO[i].setB_vfree0(b_free);
			valueVO[i].setD_vfree0(d_free);
		}
//		//查询核销明细
//		try {
//			MaterialledgerVO paramvo =  new MaterialledgerVO();
//			paramvo.setPk_corp(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
//			Vector all_mater = MaterialledgerHelper.queryByVOs(new MaterialledgerVO[]{paramvo}, new Boolean [] {true});
//			if(all_mater != null){
//				MaterialledgerVO[] materivos = (MaterialledgerVO[]) all_mater.get(0);
//				for (int i = 0; i < materivos.length; i++) {
//					MaterialledgerVO materialledgerVO = materivos[i];
//					for (int j = 0; j < valueVO.length; j++) {
//						OrderReportVO orderreportvo = valueVO[j];
//						if(orderreportvo.getB_corder_bid().equals(materialledgerVO.getCorder_bid())
////								&&orderreportvo.getB_cmangid().equals(materialledgerVO.getCprocessmangid())
//								&& orderreportvo.getD_cmangid().equals(materialledgerVO.getCmaterialmangid())){
//							UFDouble update = materialledgerVO.getNnum();
//							UFDouble old = orderreportvo.getD_naccumwritenum();
//							old = old == null ? UFDouble.ZERO_DBL : old;
//							update = update == null ? UFDouble.ZERO_DBL : update;
//							orderreportvo.setD_naccumwritenum(old.add(update));
//						}
//						
//					}
//				}
//			}
//		} catch (BusinessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		return valueVO;
	}
	/**
	 * @param strWhere
	 * @throws Exception
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	protected void doBodyQuery(String strWhere) throws Exception,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		strWhere = strWhere.replace("(1=1) and (isnull(dr,0)=0)" ,"");
		strWhere = strWhere.replace("(isnull(dr,0)=0)" ,"1=1");
		
		if(strWhere.trim().length() > 0){
			strWhere = " and " + strWhere;
		}
		OrderReportVO[] queryVos = query(strWhere+" and sc_order.pk_corp='"+_getCorp().getPrimaryKey()+"'");
		getBufferData().clear();

		AggregatedValueObject vo = (AggregatedValueObject) Class.forName(
				getUIController().getBillVoName()[0]).newInstance();
		vo.setChildrenVO(queryVos);
		getBufferData().addVOToBuffer(vo);

		updateBuffer();
	}

}
