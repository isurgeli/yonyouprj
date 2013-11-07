package nc.ui.ztwzj.sapitf.voucher;

import java.awt.event.ActionEvent;

import nc.ui.uif2.UIState;
import nc.ui.uif2.model.AbstractAppModel;
import nc.uif2.annoations.MethodType;
import nc.uif2.annoations.ModelMethod;
import nc.uif2.annoations.ModelType;

public class ResetAction extends nc.ui.uif2.NCAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AbstractAppModel model = null;
	
	public ResetAction() {
		super();
		setBtnName("重置标志");//  按钮的显示名称，多语言在此设置
		setCode("Reset");//  按钮编码
	}
	
	@ModelMethod(modelType=ModelType.AbstractAppModel,methodType=MethodType.GETTER)
	public AbstractAppModel getModel() {
		return model;
	}
	
	@ModelMethod(modelType=ModelType.AbstractAppModel,methodType=MethodType.SETTER)
	public void setModel(AbstractAppModel model) {
		this.model = model;
		model.addAppEventListener(this);
	}
	
	@Override
	protected boolean isActionEnable() {
		return model.getUiState()== UIState.NOT_EDIT;
	}
	
	@Override
	public void doAction(ActionEvent e) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
