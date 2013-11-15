package nc.ui.ztwzj.sapitf.voucher;

import java.util.HashMap;

import nc.bs.framework.common.NCLocator;
import nc.itf.ztwzj.sapitf.IVoucherService;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.pub.BusinessException;
import nc.vo.pub.SuperVO;
import nc.vo.pub.lang.UFDateTime;
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
		if (context.getPk_org() == null) {
			throw new BusinessException("请选择组织。");
		}
		@SuppressWarnings("unchecked")
		HashMap<String, QueryCondition> conditions = (HashMap<String, QueryCondition>)queryScheme.get("all_condition");
		if (conditions.get("busitype") == null || conditions.get("busitype").getValues().length == 0) {
			throw new BusinessException("请选择至少一种业务类型。");
		}
		
		String dates[] = new String[] {"2013-01-01", new UFDateTime().getDateTimeAfter(1).toString()};
		if (conditions.get("budat") != null) {
			String[] cdates = conditions.get("budat").getValues();
			
			if (cdates[0]!=null && cdates[0].length()>0)
				dates[0] = cdates[0];
			if (cdates[1]!=null && cdates[1].length()>0)
				dates[1] = cdates[1];
		}
		
		IVoucherService bp = NCLocator.getInstance().lookup(IVoucherService.class);
		SuperVO[] ret = bp.qryTMVoucherBillInfoVo(context.getPk_org(), conditions.get("busitype").getValues(), 
				dates[0].replaceAll("-", "").substring(0, 8), dates[1].replaceAll("-", "").substring(0, 8));
		
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
