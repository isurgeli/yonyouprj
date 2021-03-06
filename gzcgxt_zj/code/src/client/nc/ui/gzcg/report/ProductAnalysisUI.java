package nc.ui.gzcg.report;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.ui.scm.pub.report.ReportPanel;

@SuppressWarnings("restriction")
public class ProductAnalysisUI extends ReportAnalysisUI{
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
			reportConfig.dimention = new int[]{2,3};
			reportConfig.measure = new int[]{4, 5, 6, 7, 8, 9, 10};
			reportConfig.cross = new int[]{13};
			reportConfig.mustSelect = new int[]{2};
			reportConfig.crossMeasure = new int[]{4,5};
		}
		
		if (sqlProcesser==null){
			sqlProcesser = new IAdditionSQLProcess() {	
				public void additionSQLWhereClause(StringBuffer sql) {
					sql.append(" and gzcg_qcrp_checkbill_v.cmangid in (select pk_invmandoc from bd_invmandoc where bd_invmandoc.def3='"+
							GZCGConstant.DEFDOCPODUCTPK.getValue()+"')");
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
		reportpanel.hideColumn(new String[]{"vcustcode", "vcustname", "vinvclcode", "vinvclname", "namountorder", "ncountorder"});
	}
	
	@Override
	protected String getMainViewName() {
		return GZCGConstant.PRODUCTMAINVIEW.getValue();
	}
	
	@Override
	protected void afterHideReportPanel(ReportPanel reportpanel) {
		reportpanel.hideColumn(new String[]{"vinvdoccode"});
	}
	
	@Override
	protected void afterConditionPanelInit() {	
	}
}

