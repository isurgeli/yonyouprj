package nc.ui.gzcg.report;


import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportAnalysisConst;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.ReportLinkQueryData;
import nc.ui.pub.ClientEnvironment;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.pub.BusinessException;

@SuppressWarnings("restriction")
public class CustomerAnalysisUI extends ReportAnalysisUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeCode() {
		return GZCGConstant.CUSTOMERANALYSISUIFUNCODE.getValue();
	}

	@Override
	protected void setReportAnalysisConfig() {
		if (reportConfig==null){
			reportConfig = new ReportConigParam();
			reportConfig.dimentionStart = GZCGReportAnalysisConst.customerDimentionStart;
			reportConfig.dimentionEnd = GZCGReportAnalysisConst.customerDimentionEnd;
			reportConfig.measureStart = GZCGReportAnalysisConst.customerMeasureStart;
			reportConfig.measureEnd = GZCGReportAnalysisConst.customerMeasureEnd;
			reportConfig.crossStart = GZCGReportAnalysisConst.customerCrossStart;
			reportConfig.crossEnd = GZCGReportAnalysisConst.customerCrossEnd;
			reportConfig.mustSelect = new int[]{0};
			reportConfig.crossMeasure = new int[]{4,5};
		}
		
		if (sqlProcesser==null){
			sqlProcesser = new IAdditionSQLProcess() {	
				public void additionSQLWhereClause(StringBuffer sql) {
					sql.append(" and gzcg_qcrp_checkbill_v.cchecktypeid='"+GZCGConstant.CHECKTYPEPURCHASE.getValue()+"' ");
				}				
				public void additionSQLQueryClause(StringBuffer sql) {
				}				
				public void additionSQLGroupClause(StringBuffer sql) {
				}				
				public void additionSQLFromClause(StringBuffer sql) {
				}
			};
		}
	}

	@Override
	public String getBusitype() {
		return null;
	}

	@Override
	public void setTotalRowShow() {
	}

	@Override
	protected void hideReportPanel(ReportPanel reportpanel) {
	}
	
	@Override
	protected String getMainViewName() {
		return GZCGConstant.MATERIALMAINVIEW.getValue();
	}
	
	@Override
	protected String setReportLinkData(ReportLinkQueryData queryLinkData) {
		String targetFunCode = super.setReportLinkData(queryLinkData);
		if (targetFunCode==null) return targetFunCode;
		
		int[] rows = getReportPanel().getBillTable().getSelectedRows();
		
		String vcustcode = getReportPanel().getBillModel().getValueAt(rows[0], "vcustcode").toString();
		
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		StringBuffer sql = new StringBuffer();
		sql.append("select bd_cumandoc.pk_cumandoc from bd_cubasdoc, bd_cumandoc where bd_cubasdoc.pk_cubasdoc=bd_cumandoc.pk_cubasdoc and bd_cumandoc.pk_corp='");
		sql.append(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		sql.append("' and bd_cubasdoc.custcode='");
		sql.append(vcustcode);
		sql.append("' and bd_cumandoc.custflag='3'");
		
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> cvendorpkdata = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			if (cvendorpkdata!=null && cvendorpkdata.size()>0)
				queryLinkData.setCvendormangid(cvendorpkdata.get(0).get(0).toString());
		} catch (BusinessException e) {
		}
		
		return targetFunCode;
	}
}
