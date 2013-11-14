package nc.ui.ztwzj.sapitf.voucher;

import java.util.HashMap;

import nc.bs.framework.common.NCLocator;
import nc.itf.ztwzj.sapitf.IVoucherService;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDate;
import nc.vo.pubapp.query2.sql.process.QueryCondition;
import nc.vo.uif2.LoginContext;

public class TableService implements IQueryService, IAppModelService {
	LoginContext context;
	
	public LoginContext getContext() {
		return context;
	}

	public void setContext(LoginContext context) {
		this.context = context;
	}

	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme) throws Exception {
		if (context.getPk_org() == null)
			throw new BusinessException("ÇëÑ¡Ôñ×éÖ¯¡£");
		@SuppressWarnings("unchecked")
		HashMap<String, QueryCondition> conditions = (HashMap<String, QueryCondition>)queryScheme.get("all_condition");
		String[] date = conditions.get("budat").getValues();
		if (date[0]==null || date[0].length()==0)
			date[0] = "2013-01-01";
		if (date[1]==null || date[1].length()==0)
			date[1] = new UFDate().toString();
		IVoucherService bp = NCLocator.getInstance().lookup(IVoucherService.class);
		SuperVO[] ret = bp.qryTMVoucherBillInfoVo(context.getPk_org(), conditions.get("busitype").getValues(), 
				date[0].replaceAll("-", "").substring(0, 8), date[1].replaceAll("-", "").substring(0, 8));
		
		return ret;
	}

	@Override
	public Object insert(Object object) throws Exception {
		return null;
	}

	@Override
	public Object update(Object object) throws Exception {
		return null;
	}

	@Override
	public void delete(Object object) throws Exception {
	}

	@Override
	public Object[] queryByDataVisibilitySetting(LoginContext context)
			throws Exception {
		return null;
	}
	
}
