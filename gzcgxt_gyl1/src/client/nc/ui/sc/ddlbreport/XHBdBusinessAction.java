package nc.ui.sc.ddlbreport;

import java.util.ArrayList;

import nc.ui.pub.ClientEnvironment;
import nc.ui.trade.base.AbstractBillUI;
import nc.ui.trade.businessaction.BdBusinessAction;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;

public class XHBdBusinessAction extends BdBusinessAction{

	 public XHBdBusinessAction(AbstractBillUI ui) {
	        super();
	  }
	@Override
	public AggregatedValueObject save(AggregatedValueObject billVO, String billType, String billDate, Object userObj, AggregatedValueObject checkVo) throws Exception {
		// TODO 自动生成方法存根
		AggregatedValueObject returnVO = super.save(billVO, billType, billDate, userObj, checkVo);
		if(returnVO != null
				&& returnVO.getChildrenVO() != null 
				&& returnVO.getChildrenVO().length > 0){
			ArrayList<CircularlyAccessibleValueObject> bodyVOs = new ArrayList<CircularlyAccessibleValueObject>();
			for(int i = 0 ; i < returnVO.getChildrenVO().length ; i++){
				if(returnVO.getChildrenVO()[i].getAttributeValue("pk_corp") != null
						&& returnVO.getChildrenVO()[i].getAttributeValue("pk_corp").equals(ClientEnvironment.getInstance().getCorporation().getPrimaryKey()))
					bodyVOs.add(returnVO.getChildrenVO()[i]);
			}
			returnVO.setChildrenVO((SuperVO[])bodyVOs.toArray( new SuperVO[0]));
		}
			return returnVO;
	}
}
