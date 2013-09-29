package nc.ui.gzcg.report;

import java.util.Hashtable;
import nc.itf.gzcg.pub.ISQLSection;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UICheckBox;
import nc.vo.pub.query.ConditionVO;

public class SQLBuildUtil {
	private ReportConigParam reportConfig;
	private IAdditionSQLProcess sqlProcesser;
	private ISQLSection[] sqlSections;
	
	public SQLBuildUtil(ISQLSection[] _sqlSections, ReportConigParam _reportConfig, IAdditionSQLProcess _sqlProcesser){
		reportConfig = _reportConfig;
		sqlProcesser = _sqlProcesser;
		sqlSections = _sqlSections;
	}
	
	private String getAssembleSQL(StringBuffer sqlQuery, StringBuffer sqlFrom,
			StringBuffer sqlWhere, StringBuffer sqlGroup) {
		sqlProcesser.additionSQLQueryClause(sqlQuery);
		sqlProcesser.additionSQLFromClause(sqlFrom);
		sqlProcesser.additionSQLWhereClause(sqlWhere);
		sqlProcesser.additionSQLGroupClause(sqlGroup);
		
		StringBuffer sql = new StringBuffer();
		sql.append(sqlQuery);
		sql.append(sqlFrom);
		sql.append(sqlWhere);
		sql.append(sqlGroup);
		
		return sql.toString();
	}
	
	public String getSQLForReportStatistics(Hashtable<ISQLSection, UICheckBox> conditionCtrls, ConditionVO[] voCondition, String where){
		StringBuffer sqlQuery = getSQLQueryClauseCross(conditionCtrls);
		StringBuffer sqlFrom = getSQLFromClause(conditionCtrls);
		StringBuffer sqlWhere = getSQLWhereClause(conditionCtrls, voCondition);
		sqlWhere.append(where);
		StringBuffer sqlGroup = new StringBuffer();
		
		return getAssembleSQL(sqlQuery, sqlFrom, sqlWhere, sqlGroup);
	}
	
	public String getSQLForReportAnalysis(Hashtable<ISQLSection, UICheckBox> conditionCtrls, ConditionVO[] voCondition){
		StringBuffer sqlQuery = getSQLQueryClause(conditionCtrls);
		StringBuffer sqlFrom = getSQLFromClause(conditionCtrls);
		StringBuffer sqlWhere = getSQLWhereClause(conditionCtrls, voCondition);
		StringBuffer sqlGroup = getSQLGroupClause(conditionCtrls);
		
		return getAssembleSQL(sqlQuery, sqlFrom, sqlWhere, sqlGroup);
	}
	
	public String getSQLForReportAnalysisCross(Hashtable<ISQLSection, UICheckBox> conditionCtrls, ConditionVO[] voCondition){
		StringBuffer sqlQuery = getSQLQueryClauseCross(conditionCtrls);
		StringBuffer sqlFrom = getSQLFromClauseCross(conditionCtrls);
		StringBuffer sqlWhere = getSQLWhereClauseCross(conditionCtrls, voCondition);
		StringBuffer sqlGroup = getSQLGroupClauseCross(conditionCtrls);
		
		return getAssembleSQL(sqlQuery, sqlFrom, sqlWhere, sqlGroup);
	}

	private void queryItemClauseHelper(Hashtable<ISQLSection, UICheckBox> conditionCtrls, StringBuffer sql, int[] idxs) {
		for (int idx : idxs){
			if (conditionCtrls.get(sqlSections[idx]).isSelected()){
				for(String queryItem : sqlSections[idx].getQueryItems()){
					sql.append(queryItem);
					sql.append(", ");
				}
			}
		}
	}
	
	private StringBuffer getSQLGroupClause(Hashtable<ISQLSection, UICheckBox> conditionCtrls){
		StringBuffer sqlGroup = new StringBuffer();
		sqlGroup.append("group by ");
		queryItemClauseHelper(conditionCtrls, sqlGroup, reportConfig.dimention);
		sqlGroup.delete(sqlGroup.length()-2, sqlGroup.length()-1);
		return sqlGroup;
	}
	
	private StringBuffer getSQLGroupClauseCross(Hashtable<ISQLSection, UICheckBox> conditionCtrls){
		StringBuffer sqlGroup = new StringBuffer();
		sqlGroup.append("group by ");
		queryItemClauseHelper(conditionCtrls, sqlGroup, reportConfig.dimention);
		queryItemClauseHelper(conditionCtrls, sqlGroup, reportConfig.cross);
		sqlGroup.delete(sqlGroup.length()-2, sqlGroup.length()-1);
		return sqlGroup; 
	}
	
	private void joinClauseHelper(Hashtable<ISQLSection, UICheckBox> conditionCtrls, StringBuffer sqlGroup, int[] idxs) {
		for (int idx : idxs){
			if (conditionCtrls.get(sqlSections[idx]).isSelected()){
				for(String join : sqlSections[idx].getJoins()){
					sqlGroup.append(join);
				}
			}
		}
	}
	
	private StringBuffer getSQLWhereClause(Hashtable<ISQLSection, UICheckBox> conditionCtrls, ConditionVO[] voCondition){
		StringBuffer sqlWhere = new StringBuffer();
		sqlWhere.append(" where 1=1");
		joinClauseHelper(conditionCtrls, sqlWhere, reportConfig.dimention);
		
		sqlWhere.append(" and gzcg_qcrp_checkbill_v.pk_corp='"+ClientEnvironment.getInstance().getCorporation().getPrimaryKey()+"' ");
		if(voCondition!=null && voCondition.length>0)
			sqlWhere.append(" and "+voCondition[0].getSQLStr(voCondition));
		
		return sqlWhere;
	}
	
	private StringBuffer getSQLWhereClauseCross(Hashtable<ISQLSection, UICheckBox> conditionCtrls, ConditionVO[] voCondition){
		StringBuffer sqlWhere = getSQLWhereClause(conditionCtrls, voCondition);
		joinClauseHelper(conditionCtrls, sqlWhere, reportConfig.cross);
		return sqlWhere; 
	}
	
	private void fromClauseHelper(Hashtable<ISQLSection, UICheckBox> conditionCtrls, StringBuffer sqlGroup, int[] idxs) {
		for (int idx : idxs){
			if (conditionCtrls.get(sqlSections[idx]).isSelected()){
				for(String table : sqlSections[idx].getTables()){
					sqlGroup.append(", ");
					sqlGroup.append(table);
				}
			}
		}
	}
	
	private StringBuffer getSQLFromClause(Hashtable<ISQLSection, UICheckBox> conditionCtrls){
		StringBuffer sqlFrom = new StringBuffer();
		sqlFrom.append(" from gzcg_qcrp_checkbill_v");
		fromClauseHelper(conditionCtrls, sqlFrom, reportConfig.dimention);
		return sqlFrom;
	}
	
	private StringBuffer getSQLFromClauseCross(Hashtable<ISQLSection, UICheckBox> conditionCtrls){
		StringBuffer sqlFrom = getSQLFromClause(conditionCtrls);
		fromClauseHelper(conditionCtrls, sqlFrom, reportConfig.cross);
		return sqlFrom;
	}

	private StringBuffer getSQLQueryClause(Hashtable<ISQLSection, UICheckBox> conditionCtrls) {
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("select ");
		queryItemClauseHelper(conditionCtrls, sqlQuery, reportConfig.dimention);
		queryItemClauseHelper(conditionCtrls, sqlQuery, reportConfig.measure);
		
		String placehold = "";
		if (conditionCtrls.containsKey(sqlSections[reportConfig.idxINVCL]) && conditionCtrls.get(sqlSections[reportConfig.idxINVCL]).isSelected()){
			placehold = "partition by invclasscode";
		}
		if (conditionCtrls.containsKey(sqlSections[reportConfig.idxINVDOC]) && conditionCtrls.get(sqlSections[reportConfig.idxINVDOC]).isSelected()){
			placehold = "partition by invcode";
		}
		
		int idx=0;
		while((idx = sqlQuery.indexOf("#PLACEHOLD#")) != -1){
			sqlQuery.replace(idx, idx+"#PLACEHOLD#".length(), placehold);
		}
		sqlQuery.delete(sqlQuery.length()-2, sqlQuery.length()-1);
		
		return sqlQuery;
	}
	
	private StringBuffer getSQLQueryClauseCross(Hashtable<ISQLSection, UICheckBox> conditionCtrls) {
		StringBuffer sqlQuery = new StringBuffer();
		sqlQuery.append("select ");
		queryItemClauseHelper(conditionCtrls, sqlQuery, reportConfig.dimention);
		queryItemClauseHelper(conditionCtrls, sqlQuery, reportConfig.cross);
		
		if (reportConfig.crossMeasure.length!=0 && conditionCtrls.get(sqlSections[reportConfig.crossMeasure[0]]).isSelected()){
			for(String queryItem : sqlSections[reportConfig.crossMeasure[0]].getQueryItems()){
				sqlQuery.append(queryItem);
				sqlQuery.append(", ");
			}
		} else if(reportConfig.crossMeasure.length!=0) {
			for(String queryItem : sqlSections[reportConfig.crossMeasure[1]].getQueryItems()){
				sqlQuery.append(queryItem);
				sqlQuery.append(", ");
			}
		}
		sqlQuery.delete(sqlQuery.length()-2, sqlQuery.length()-1);

		return sqlQuery;
	}
}
