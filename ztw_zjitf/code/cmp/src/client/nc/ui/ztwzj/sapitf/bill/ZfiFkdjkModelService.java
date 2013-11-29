package nc.ui.ztwzj.sapitf.bill;

import java.util.HashMap;
import java.util.List;

import nc.bs.framework.common.NCLocator;
import nc.itf.lxt.pub.jxabtool.JaxbTools;
import nc.itf.ztwzj.sapitf.IBillService;
import nc.ui.lxt.pub.editor.LxtUITool;
import nc.ui.pubapp.uif2app.query2.model.IQueryService;
import nc.ui.querytemplate.querytree.IQueryScheme;
import nc.ui.uif2.model.IAppModelService;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pubapp.query2.sql.process.QueryCondition;
import nc.vo.uif2.LoginContext;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjk.ISAPQry;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjk.ZfiFkdjk;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse;
import nc.vo.ztwzj.sapitf.bill.ZfiFkdjkResponse.ZfiFkdjkResponse.OTab;

public class ZfiFkdjkModelService implements IAppModelService, IQueryService {
	LoginContext context;
	
	public LoginContext getContext() {
		return context;
	}

	public void setContext(LoginContext context) {
		this.context = context;
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

	@Override
	public Object[] queryByQueryScheme(IQueryScheme queryScheme) throws Exception {
		if (context.getPk_org() == null) {
			throw new BusinessException("ÇëÑ¡Ôñ×éÖ¯¡£");
		}
		@SuppressWarnings("unchecked")
		HashMap<String, QueryCondition> conditions = (HashMap<String, QueryCondition>)queryScheme.get("all_condition");
		
		String dates[] = LxtUITool.getQueryCondition(conditions, "aedat", 
				new String[] {"2013-01-01", new UFDateTime().getDateTimeAfter(1).toString()});
		dates[0] = dates[0].replaceAll("-", "").substring(0, 8);
		dates[1] = dates[1].replaceAll("-", "").substring(0, 8);
		
		String[] prepayQeqNums = LxtUITool.getQueryCondition(conditions, "prepay_req_num", 
				new String[] {null, null});
		
		String[] suppliers = LxtUITool.getQueryCondition(conditions, "pk_supplier", 
				new String[] {null, null});
		
		String[] formulas = new String[] {
			"orgcode->getColValue(org_orgs,code,pk_org,pk_org)",
			"suppliercode->getColValue(bd_supplier,code,pk_supplier,pk_supplier)"
		};
		HashMap<String, Object> vars = new HashMap<String, Object>();
		vars.put("pk_org", context.getPk_org());
		vars.put("pk_supplier", suppliers);
		Object[][] values = LxtUITool.getValueByFormula(formulas, vars);
		suppliers[0] = values[1][0].equals("")?null:"00"+(String)values[1][0];
		suppliers[1] = values[1].length==1 || values[1][1].equals("")?null:"00"+(String)values[1][1]; 
		
		String orgcode = values[0][0].toString().substring(4);
		if (orgcode.equals("100099")) orgcode="1000";
		
		ZfiFkdjk zfi = new ZfiFkdjk();
		zfi.setIBukrs(ISAPQry.getQryByCondition(orgcode, null));
		zfi.setIAedat(ISAPQry.getQryByCondition(dates[0], dates[1]));
		if (prepayQeqNums[0] != null)
			zfi.setIPrepayReqNum(ISAPQry.getQryByCondition(prepayQeqNums[0], prepayQeqNums[1]));
		if (suppliers[0] != null)
		zfi.setILifnr(ISAPQry.getQryByCondition(suppliers[0], suppliers[1]));
		
		String queryXml = JaxbTools.getStringFromObject(zfi);
				
		IBillService dao = NCLocator.getInstance().lookup(IBillService.class);
		List<OTab.Item> obj = dao.qryPayBillInfo(queryXml);
		return obj.toArray(new ZfiFkdjkResponse.OTab.Item[] {});
	}

}
