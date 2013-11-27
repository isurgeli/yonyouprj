package nc.ui.gzcg.report;

import java.util.Hashtable;

import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportStatisticsConst;
import nc.itf.gzcg.pub.ISQLSection;
import nc.ui.pub.beans.UICheckBox;

@SuppressWarnings("restriction")
public class SemiProductStatisticsUI extends ReportStatisticsUI{

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
		return GZCGConstant.SEMIPRODUCTSTATISTICSUIFUNCODE.getValue();
	}

	@Override
	protected String[] getHideReportCol() {
		return new String[]{"vcheckbillcode", "bqualified", "vfree1", "vorderbillcode", "vstockbatch", 
				"vcustname", /*"vinvdocname",*/ "vprocessname", 
				"ninnum", "nstocknum"};
	}

	@Override
	protected void setReportStatisticsConfig() {
		sqlCtrls = new Hashtable<ISQLSection, UICheckBox>();
		sqlCtrls.put(GZCGReportStatisticsConst.SEMIPRODUCTPUBLIC, new UICheckBox());
		sqlCtrls.put(GZCGReportStatisticsConst.SEMIPRODUCTPROJECT, new UICheckBox());
		sqlCtrls.get(GZCGReportStatisticsConst.SEMIPRODUCTPUBLIC).setSelected(true);
		sqlCtrls.get(GZCGReportStatisticsConst.SEMIPRODUCTPROJECT).setSelected(true);
		
		if (reportConfig==null){
			reportConfig = new ReportConigParam();
			reportConfig.dimention = new int[]{3};
			reportConfig.measure = new int[]{};
			reportConfig.cross = new int[]{4};
			reportConfig.mustSelect = new int[]{};
			reportConfig.crossMeasure = new int[]{};
		}
		
		if (sqlProcesser==null){
			sqlProcesser = new IAdditionSQLProcess() {	
				public void additionSQLWhereClause(StringBuffer sql) {
					sql.append(" and qc_cghzbg_b.zdy1 = '无系统号' ");
					sql.append(" and qc_cghzbg_b.zdy2 = '通过' ");
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
		return GZCGConstant.SEMIPRODUCTMAINVIEWS.getValue();
	}
	
	@Override
	protected boolean isSemiProduct() {
		return true;
	}

	@Override
	protected int getLockCol() {
		return 3;
	}
}

