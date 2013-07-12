package nc.ui.gzcg.report;

import java.util.Hashtable;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportStatisticsConst;
import nc.itf.gzcg.pub.ISQLSection;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.scm.pub.report.ReportPanel;

@SuppressWarnings("restriction")
public class MaterialStatisticsUI extends ReportStatisticsUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBusitype() {
		return null;
	}

	@Override
	public String getNodeCode() {
		return GZCGConstant.MATERIALSTATISTICSUIFUNCODE.getValue();
	}

	@Override
	protected void hideReportPanel(ReportPanel reportpanel) {
	}

	@Override
	protected void setReportStatisticsConfig() {
		sqlCtrls = new Hashtable<ISQLSection, UICheckBox>();
		sqlCtrls.put(GZCGReportStatisticsConst.PUBLIC, new UICheckBox());
		sqlCtrls.put(GZCGReportStatisticsConst.MATERIAL, new UICheckBox());
		sqlCtrls.put(GZCGReportStatisticsConst.PROJECT, new UICheckBox());
		sqlCtrls.get(GZCGReportStatisticsConst.PUBLIC).setSelected(true);
		sqlCtrls.get(GZCGReportStatisticsConst.MATERIAL).setSelected(true);
		sqlCtrls.get(GZCGReportStatisticsConst.PROJECT).setSelected(true);
		
		if (reportConfig==null){
			reportConfig = new ReportConigParam();
			reportConfig.dimentionStart = 0;
			reportConfig.dimentionEnd = 1;
			reportConfig.measureStart = -1;
			reportConfig.measureEnd = -2;
			reportConfig.crossStart = 2;
			reportConfig.crossEnd = 2;
			reportConfig.mustSelect = new int[]{};
			reportConfig.crossMeasure = new int[]{};
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

}
