package nc.ui.ztwzj.sapitf.voucher;

import nc.ui.uif2.model.DefaultAppModelDataManager;
import nc.ui.uif2.model.IAppModelDataManagerEx;

public class TableDataManager extends DefaultAppModelDataManager implements IAppModelDataManagerEx{

	@Override
	public void refresh() {
		initModel();
	}

	@Override
	public void initModelBySqlWhere(String sqlWhere) {
	}

	@Override
	public void setShowSealDataFlag(boolean showSealDataFlag) {
	}

}
