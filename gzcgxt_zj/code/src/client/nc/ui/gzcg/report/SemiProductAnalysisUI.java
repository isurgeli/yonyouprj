package nc.ui.gzcg.report;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.ui.scm.pub.report.ReportPanel;

@SuppressWarnings("restriction")
public class SemiProductAnalysisUI extends ReportAnalysisUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getNodeCode() {
		return GZCGConstant.SEMIPRODUCTANALYSISUIFUNCODE.getValue();
	}

	@Override
	protected void setReportAnalysisConfig() {
		if (reportConfig==null){
			reportConfig = new ReportConigParam();
			reportConfig.dimention = new int[]{14};
			reportConfig.measure = new int[]{4, 5, 6};
			reportConfig.cross = new int[]{};
			reportConfig.mustSelect = new int[]{14};
			reportConfig.crossMeasure = new int[]{4,5};
		}
		
		if (sqlProcesser==null){
			sqlProcesser = new IAdditionSQLProcess() {	
				public void additionSQLWhereClause(StringBuffer sql) {
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
		reportpanel.hideColumn(new String[]{"vcustcode", "vcustname", "vinvclcode", "vinvclname", "namountorder", "ncountorder"
				, "vmonth", "ninpassamount", "ninpasscount", "namountpassratio", "ncountpassratio", "namountorder", "ncountorder"});
	}
	
	@Override
	protected String getMainViewName() {
		return GZCGConstant.SEMIPRODUCTMAINVIEW.getValue();
	}

	@Override
	protected void afterHideReportPanel(ReportPanel reportpanel) {
		reportpanel.hideColumn(new String[]{"vinvdoccode", "vinvspec", "vinvunit"});
	}
	
	@Override
	protected void afterConditionPanelInit() {	
	}
}

