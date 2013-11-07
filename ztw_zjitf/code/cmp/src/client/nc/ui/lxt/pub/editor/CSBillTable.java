package nc.ui.lxt.pub.editor;

import nc.ui.uif2.editor.BillTable;
import nc.ui.uif2.editor.value.BillCardPanelBodyVOValueAdapter;
import nc.ui.uif2.editor.value.BillCardPanelMetaDataValueAdapter;

@SuppressWarnings("deprecation")
/**
 * 在BillTable的选择列上，设置全选全消功能。同时设置多选功能
 * 通过属性SelectCol设置选择列名。
 * @author 李歆涛
 *
 */
public class CSBillTable extends BillTable {

	private static final long serialVersionUID = 1L;

	protected String selectCol;

	protected BillColumnHelper colHelper;
	
	public String getSelectCol() {
		return selectCol;
	}

	public void setSelectCol(String selectCol) {
		this.selectCol = selectCol;
	}
	
	@Override
	public void initUI() {
		super.initUI();
		
		//设置值处理策略
		processValueManager();
				
		if (selectCol != null) {
			colHelper = new BillColumnHelper(billCardPanel, selectCol);
			colHelper.setSelectColumnEditable();
			colHelper.setSelectColumnHeader();
			colHelper.setSelectColumnMultiSelect();
		}
	}
	
	private void processValueManager() {
		
		if(componentValueManager == null)
		{
			if(billCardPanel.getBillData().isMeataDataTemplate())
				componentValueManager = new BillCardPanelMetaDataValueAdapter();
			else
				componentValueManager = new BillCardPanelBodyVOValueAdapter();
		}
		
		if(componentValueManager != null)
			componentValueManager.setComponent(billCardPanel);
	} 
}
