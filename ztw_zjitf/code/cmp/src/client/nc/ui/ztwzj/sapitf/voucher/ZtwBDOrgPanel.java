package nc.ui.ztwzj.sapitf.voucher;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import nc.ui.bd.pub.BDOrgPanel;
import nc.ui.pub.beans.UIComboBox;
import nc.ui.pub.beans.UILabel;
import nc.vo.ztwzj.sapitf.voucher.IZtwConstant;

public class ZtwBDOrgPanel extends BDOrgPanel implements ItemListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UILabel busiTypeLabel;
	private UIComboBox comBox;

	@Override
	public void initUI() {
		super.initUI();
		
		add(getBusiTypeLabel());
		add(getComboBox());
		
		setContextPara(getComboBox().getSelectdItemValue().toString());
	}
	
	protected UILabel getBusiTypeLabel() {
		if (busiTypeLabel == null) {
			busiTypeLabel = new UILabel();
			busiTypeLabel.setText("业务类型");/* @res "业务单元" */
		}
		return busiTypeLabel;
	}

	public UIComboBox getComboBox() {
		if (comBox == null) {
			comBox = new UIComboBox();
			comBox.addItem(IZtwConstant.BUSITYPE_REVFDOWN);
			comBox.addItem(IZtwConstant.BUSITYPE_PAYTDOWN);
			comBox.addItem(IZtwConstant.BUSITYPE_PAYTUP);
			comBox.addItem(IZtwConstant.BUSITYPE_REVFUP);
			comBox.addItem(IZtwConstant.BUSITYPE_ADJUST);
			comBox.addItemListener(this);
		}
		return comBox;
	}

	@Override
	public void itemStateChanged(ItemEvent event) {
		if (event.getStateChange() == ItemEvent.SELECTED) {
			String item = event.getItem().toString();
			setContextPara(item);
			if (item != null) {
				getDataManager().initModel();
			}
	    }
	}

	private void setContextPara(String item) {
		if (getModel().getContext().getStatusRegistery().getAppStatusMemento().getAppStatusMap()!=null &&
				getModel().getContext().getStatusRegistery().getAppStatusMemento().getAppStatusMap().containsKey(IZtwConstant.BUSITYPE))
			getModel().getContext().getStatusRegistery().getAppStatusMemento().getAppStatusMap().remove(IZtwConstant.BUSITYPE);
		
		getModel().getContext().getStatusRegistery().getAppStatusMemento().addAppStatusInfo(IZtwConstant.BUSITYPE, item);
	}
}
