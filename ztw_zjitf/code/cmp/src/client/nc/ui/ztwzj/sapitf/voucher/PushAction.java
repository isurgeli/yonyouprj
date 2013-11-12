package nc.ui.ztwzj.sapitf.voucher;

import java.awt.event.ActionEvent;

import nc.ui.uif2.NCAction;
import nc.ui.uif2.ShowStatusBarMsgUtil;
import nc.ui.uif2.UIState;
import nc.ui.uif2.editor.IEditor;
import nc.ui.uif2.model.AbstractAppModel;
import nc.uif2.annoations.MethodType;
import nc.uif2.annoations.ModelMethod;
import nc.uif2.annoations.ModelType;
import nc.uif2.annoations.ViewMethod;
import nc.uif2.annoations.ViewType;

public class PushAction extends NCAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected AbstractAppModel model = null;
	private IEditor editor;

	public PushAction() {
		super();
		setBtnName("确认上传");//  按钮的显示名称，多语言在此设置
		setCode("Push");//  按钮编码 
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
	
	@ViewMethod(viewType=ViewType.IEditor,methodType=MethodType.SETTER)
	public void setEditor(IEditor editor) {
		this.editor = editor;
	}
	
	@ViewMethod(viewType=ViewType.IEditor,methodType=MethodType.GETTER)
	public IEditor getEditor() {
		return editor;
	}
	
	@Override
	protected boolean isActionEnable() {
		return model.getUiState()== UIState.NOT_EDIT;
	}
	
	@Override
	public void doAction(ActionEvent e) throws Exception {
		//VoucherQryInfo[] value = (VoucherQryInfo[])editor.getValue();
		//((TableService)((BillTableModel)model).getService()).setBillFlag(value);
		
		ShowStatusBarMsgUtil.showStatusBarMsg("设置成功。", getModel().getContext());
	}

}
