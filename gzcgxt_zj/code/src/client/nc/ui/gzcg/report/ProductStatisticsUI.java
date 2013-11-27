package nc.ui.gzcg.report;

import java.util.Hashtable;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportStatisticsConst;
import nc.itf.gzcg.pub.ISQLSection;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.scm.pub.report.ReportPanel;

@SuppressWarnings("restriction")
public class ProductStatisticsUI extends ReportStatisticsUI{

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
	protected String[] getHideReportCol() {
		return new String[]{"vorderbillcode", "vcustname"};
	}

	@Override
	protected void setReportStatisticsConfig() {
		sqlCtrls = new Hashtable<ISQLSection, UICheckBox>();
		sqlCtrls.put(GZCGReportStatisticsConst.PUBLIC, new UICheckBox());
		sqlCtrls.put(GZCGReportStatisticsConst.MATERIAL, new UICheckBox());
		sqlCtrls.put(GZCGReportStatisticsConst.PROJECT, new UICheckBox());
		sqlCtrls.get(GZCGReportStatisticsConst.PUBLIC).setSelected(true);
		sqlCtrls.get(GZCGReportStatisticsConst.MATERIAL).setSelected(false);
		sqlCtrls.get(GZCGReportStatisticsConst.PROJECT).setSelected(true);
		
		if (reportConfig==null){
			reportConfig = new ReportConigParam();
			reportConfig.dimention = new int[]{0,1};
			reportConfig.measure = new int[]{};
			reportConfig.cross = new int[]{2};
			reportConfig.mustSelect = new int[]{};
			reportConfig.crossMeasure = new int[]{};
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
	protected String getMainViewName() {
		return GZCGConstant.PRODUCTMAINVIEW.getValue();
	}
	
	@Override
	protected boolean isSemiProduct() {
		return false;
	}
	
	@Override
	protected int getLockCol() {
		return 6;
	}
}
