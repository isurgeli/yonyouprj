package nc.ui.gzcg.report;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportAnalysisConst;
import nc.ui.scm.pub.report.ReportPanel;

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
}
