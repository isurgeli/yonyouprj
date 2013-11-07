package nc.ui.lxt.pub.editor;

import nc.ui.uif2.editor.BillTable;
import nc.ui.uif2.editor.value.BillCardPanelBodyVOValueAdapter;
import nc.ui.uif2.editor.value.BillCardPanelMetaDataValueAdapter;

@SuppressWarnings("deprecation")
/**
 * ��BillTable��ѡ�����ϣ�����ȫѡȫ�����ܡ�ͬʱ���ö�ѡ����
 * ͨ������SelectCol����ѡ��������
 * @author �����
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
		
		//����ֵ�������
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
