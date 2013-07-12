package nc.ui.gzcg.report;

public interface IAdditionSQLProcess {
	void additionSQLQueryClause(StringBuffer sql);
	void additionSQLFromClause(StringBuffer sql);
	void additionSQLWhereClause(StringBuffer sql);
	void additionSQLGroupClause(StringBuffer sql);
}
