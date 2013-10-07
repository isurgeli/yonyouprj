package nc.ui.gzcg.report;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.BoxLayout;

import nc.bs.framework.common.NCLocator;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportAnalysisConst;
import nc.itf.gzcg.pub.ISQLSection;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.ReportLinkQueryData;
import nc.ui.gzcg.pub.ReportUIEx;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.report.ReportItem;
import nc.ui.scm.pub.report.ReportPanel;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.cquery.FldgroupVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;

@SuppressWarnings("restriction")
public abstract class ReportAnalysisUI extends ReportUIEx{
	protected Hashtable<ISQLSection, UICheckBox> conditionCtrls;
	private ReportItem[] initBodyItems;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ReportConigParam reportConfig;
	protected IAdditionSQLProcess sqlProcesser;
	
	protected abstract void setReportAnalysisConfig();
	
	protected abstract String getMainViewName();

	@Override
	public ButtonObject[] getButtons() {
		return new ButtonObject[]{bnQuery, bnPrint, bnModelPrint, bnPreview, bnOut, bnShow,
				bnFilter, bnSubtotal, bnMultSort, bnLocate, bnRefresh, bnOrderQuery};
	}
	
	public ReportPanel getSuperReportPanel() {
		if (reportpanel == null) {
			try {
				reportpanel = new ReportPanel();
				reportpanel
						.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"scmpub", "UPPscmpub-000778")/* @res "报表基类" */);
				reportpanel.setTempletID(getCorpPrimaryKey(), GZCGConstant.CUSTOMERANALYSISUIFUNCODE.getValue(),
						getClientEnvironment().getUser().getPrimaryKey(),
						getBusitype());
				// reportpanel.setTempletID("40060906000000000000");
				reportpanel.setBodyMenuShow(false);
				reportpanel.setRowNOShow(true);
				reportpanel.getBillTable().getTableHeader().addMouseListener(
						reportpanel);
				// 获取原始行高
				// originalRowHeight=reportpanel.getBillTable().getRowHeight();
				// 设置绘制器
				// setCellRenderer();
			} catch (java.lang.Throwable ivjExc) {
				// user code begin {2}
				// user code end
				handleException(ivjExc);
			}
		}
		return reportpanel;

	}

	@Override
	public ReportPanel getReportPanel() {
		if (reportpanel == null) {
			getSuperReportPanel();
			hideReportPanel(reportpanel);
			initBodyItems = reportpanel.getBody_Items();
		}
		return reportpanel;
	}
	
	protected abstract void hideReportPanel(ReportPanel reportpanel);
	
	protected abstract void afterHideReportPanel(ReportPanel reportpanel);
	
	private ArrayList<String> getColsForIndex(int[] idxs){
		ArrayList<String> cols = new ArrayList<String>();
		for (int idx : idxs){
			cols.addAll(Arrays.asList(GZCGReportAnalysisConst.values()[idx].getCols()));
		}
		return cols;
	}
	
	private ArrayList<String> getSelectColsForIndex(int[] idxs){
		ArrayList<String> cols = new ArrayList<String>();
		for (int idx : idxs){
			if (conditionCtrls.get(GZCGReportAnalysisConst.values()[idx]).isSelected())
				cols.addAll(Arrays.asList(GZCGReportAnalysisConst.values()[idx].getCols()));
		}
		return cols;
	}

	@Override
	public void setReportData(ConditionVO[] voCondition) {
		getReportPanel().setBody_Items(initBodyItems);
		ArrayList<String> allcols = new ArrayList<String>();
		allcols.addAll(getColsForIndex(reportConfig.dimention));
		allcols.addAll(getColsForIndex(reportConfig.measure));
		getReportPanel().hideColumn(allcols.toArray(new String[]{}));
		
		ArrayList<String> allshowcols = new ArrayList<String>();
		allshowcols.addAll(getSelectColsForIndex(reportConfig.dimention));
		allshowcols.addAll(getSelectColsForIndex(reportConfig.measure));
		getReportPanel().showHiddenColumn(allshowcols.toArray(new String[]{}));
		
		String sql = new SQLBuildUtil(GZCGReportAnalysisConst.values(), reportConfig, sqlProcesser).getSQLForReportAnalysis(conditionCtrls, voCondition);
		
		if (!getMainViewName().equals(GZCGConstant.MATERIALMAINVIEW.getValue()))
			sql = sql.replaceAll(GZCGConstant.MATERIALMAINVIEW.getValue(), getMainViewName());
		
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		int dimensionCount = getDimensionCount();
		Hashtable<String, AnalysisReportVO> reportSet = new Hashtable<String, AnalysisReportVO>();
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql, new VectorProcessor());
			if (data!=null && data.size()>0){
				AnalysisReportVO[] reportData = new AnalysisReportVO[data.size()];
				for(int rowIdx=0;rowIdx<data.size();rowIdx++){
					reportData[rowIdx] = new AnalysisReportVO();
					for(int colIdx=0;colIdx<allshowcols.size();colIdx++){
						reportData[rowIdx].setAttributeValue(allshowcols.get(colIdx), data.get(rowIdx).get(colIdx));
					}
					if (isCrossSelected()){
						StringBuffer key = new StringBuffer();
						for(int colIdx=0;colIdx<dimensionCount;colIdx++) key.append(data.get(rowIdx).get(colIdx).toString());
						reportSet.put(key.toString(), reportData[rowIdx]);
					}
				}
				if (isCrossSelected()){
					String crossSql = new SQLBuildUtil(GZCGReportAnalysisConst.values(), reportConfig, sqlProcesser).getSQLForReportAnalysisCross(conditionCtrls, voCondition);
					if (!getMainViewName().equals(GZCGConstant.MATERIALMAINVIEW.getValue()))
						crossSql = crossSql.replaceAll(GZCGConstant.MATERIALMAINVIEW.getValue(), getMainViewName());
					@SuppressWarnings("unchecked")
					Vector<Vector<Object>> crossData = (Vector<Vector<Object>>)dao.executeQuery(crossSql, new VectorProcessor());
					if (crossData!=null && crossData.size()>0){
						processCrossData(reportSet, crossData);
					}
				}
				afterHideReportPanel(getReportPanel());
				setData(reportData);
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private boolean isCrossSelected()	{
		for(int i : reportConfig.cross){
			if (conditionCtrls.get(GZCGReportAnalysisConst.values()[i]).isSelected())
				return true;
		}
		
		return false;
	}
	
	private void processCrossData(Hashtable<String, AnalysisReportVO> reportSet, Vector<Vector<Object>> data){
		int crossColIdx = getDimensionCount();
		
		Hashtable<String, Integer> crossValues = new Hashtable<String, Integer>();
		for(int i=0;i<data.size();i++){
			String crossValue = data.get(i).get(crossColIdx).toString();
			if (!crossValues.containsKey(crossValue))
				crossValues.put(crossValue, crossValues.size());
		}
		
		Hashtable<String, UFDouble[]> crossTable = new Hashtable<String, UFDouble[]>();
		
		for(int i=0;i<data.size();i++){
			StringBuffer key = new StringBuffer();
			for(int j=0;j<crossColIdx;j++) key.append(data.get(i).get(j).toString());
			
			if (!crossTable.containsKey(key.toString())) 
				crossTable.put(key.toString(), new UFDouble[crossValues.size()]);
			UFDouble[] crosscol = crossTable.get(key.toString());
			crosscol[crossValues.get(data.get(i).get(crossColIdx).toString())] = new UFDouble(data.get(i).get(crossColIdx+1).toString());
		}
		
		for(String key : crossTable.keySet()){
			AnalysisReportVO reportVO = reportSet.get(key);
			UFDouble[] crosscol = crossTable.get(key);
			
			for(int i=0;i<crosscol.length;i++){
				UFDouble crosscolitem = crosscol[i];
				if (crosscolitem==null) crosscolitem = new UFDouble(0);
				reportVO.setAttributeValue("_CROSS"+i, crosscolitem);
			}
		}
		
		processTableLayout(crossValues);
	}

	private void processTableLayout(Hashtable<String, Integer> crossValues) {
		ReportItem[] reportItems = new ReportItem[crossValues.size()];
		for(String key : crossValues.keySet()){
			ReportItem item = new ReportItem();
			item.setWidth(80);
			item.setKey("_CROSS"+crossValues.get(key));
			item.setName(key);
			if (conditionCtrls.get(GZCGReportAnalysisConst.values()[reportConfig.crossMeasure[0]]).isSelected()){
				item.setDataType(getReportPanel().getBody_Item(GZCGReportAnalysisConst.values()[reportConfig.crossMeasure[0]].getCols()[0]).getDataType());
			} else {
				item.setDataType(getReportPanel().getBody_Item(GZCGReportAnalysisConst.values()[reportConfig.crossMeasure[1]].getCols()[0]).getDataType());
			}
			reportItems[crossValues.get(key)] = item;
		}
		ArrayList<ReportItem> finalBodyItems = new ArrayList<ReportItem>();
		finalBodyItems.addAll(Arrays.asList(getReportPanel().getBody_Items()));
		finalBodyItems.addAll(Arrays.asList(reportItems));
		
		Vector<FldgroupVO> groupVOs = new Vector<FldgroupVO>();
		for(int i=0;i<reportItems.length-1;i++){
			FldgroupVO groupVO = new FldgroupVO();
			groupVO.setGroupid(i + 1);
			groupVO.setGroupname("处理方式");
			if(i==0) 
				groupVO.setGrouptype("0");
			else
				groupVO.setGrouptype("2");
			if(i==0)
				groupVO.setItem1(String.valueOf(finalBodyItems.size()-reportItems.length+i));
			else
				groupVO.setItem1("处理方式");
			groupVO.setItem2(String.valueOf(finalBodyItems.size()-reportItems.length+i+1));
			if (i == reportItems.length - 2)
				groupVO.setToplevelflag("Y");
			else
				groupVO.setToplevelflag("N");
			
			groupVOs.add(groupVO);
		}
		
		getReportPanel().setFieldGroup(groupVOs.toArray(new FldgroupVO[]{}));
		getReportPanel().setBody_Items(finalBodyItems.toArray(new ReportItem[]{}));
	}

	private int getDimensionCount() {
		int ret = 0;
		for (int idx : reportConfig.dimention){
			if (conditionCtrls.get(GZCGReportAnalysisConst.values()[idx]).isSelected())
				ret+=GZCGReportAnalysisConst.values()[idx].getCols().length;
		}
		
		return ret;
	}
	
	@Override
	public boolean isConPanelShow() {
		return true;
	}
	
	@Override
	protected UIPanel getConditionPanel() {
		if (conditionpanel==null){
			setReportAnalysisConfig();
			conditionCtrls = new Hashtable<ISQLSection, UICheckBox>();
			
			UIPanel conditionPanel = super.getConditionPanel();
			conditionPanel.setMaximumSize(new Dimension(550, 60));
			conditionPanel.setPreferredSize(new Dimension(550, 60));
			conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
			UIPanel panel1 = new UIPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
			UIPanel panel2 = new UIPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
			
			initCheckboxCtrl(panel1, panel2, reportConfig.dimention, true);
			initCheckboxCtrl(panel1, panel2, reportConfig.measure, false);
			initCheckboxCtrl(panel1, panel2, reportConfig.cross, false);
			
			conditionPanel.add(panel1);
			conditionPanel.add(panel2);
			
			for(int i=0;i<reportConfig.mustSelect.length;i++){
				conditionCtrls.get(GZCGReportAnalysisConst.values()[reportConfig.mustSelect[i]]).setEnabled(false);
				conditionCtrls.get(GZCGReportAnalysisConst.values()[reportConfig.mustSelect[i]]).setSelected(true);
			}
			
			afterConditionPanelInit();
		}
		
		return conditionpanel;
	}
	
	protected abstract void afterConditionPanelInit();

	private void initCheckboxCtrl(UIPanel panel1, UIPanel panel2, int[] idxs, boolean first) {
		for (int idx : idxs){
			UICheckBox ctrl = new UICheckBox(GZCGReportAnalysisConst.values()[idx].getValue());
			conditionCtrls.put(GZCGReportAnalysisConst.values()[idx], ctrl);
			if (first){
				panel1.add(ctrl); 
			} else {
				panel2.add(ctrl);
				ctrl.setSelected(true);
			}
		}
	}
	
	@Override
	public void onRefresh() {
		setReportData(getQueryPanel().getConditionVO());
	}
	
	@Override
	public void onOrderQuery() {
		ReportLinkQueryData queryLinkData = new ReportLinkQueryData();
		String targetFunCode = setReportLinkData(queryLinkData);
		
		SFClientUtil.openLinkedQueryDialog(targetFunCode, this, queryLinkData);
	}

	protected String setReportLinkData(ReportLinkQueryData queryLinkData) {
		String targetFunCode = null;
		int[] rows = getReportPanel().getBillTable().getSelectedRows();
		
		if (rows==null || rows.length==0){
			MessageDialog.showWarningDlg(this, "提示", "请先选择一行数据再联查质检数据。");
			return null;
		}
		Object vmanagecode = getReportPanel().getBillModel().getValueAt(rows[0], "vinvdoccode");
		if (vmanagecode==null || vmanagecode.toString().length()==0){
			MessageDialog.showWarningDlg(this, "提示", "请先勾选存货分析项查询后再联查质检数据。");
			return null;
		}
		
		if (getNodeCode().equals(GZCGConstant.SEMIPRODUCTANALYSISUIFUNCODE.getValue())){
			targetFunCode = GZCGConstant.SEMIPRODUCTSTATISTICSUIFUNCODE.getValue();
			queryLinkData.setCmanageid(getReportPanel().getBillModel().getValueAt(rows[0], "vinvdocname").toString());
		}else{
			IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			StringBuffer sql = new StringBuffer();
			sql.append("select bd_invmandoc.pk_invmandoc, nvl(bd_invmandoc.def3,'-') from bd_invbasdoc, bd_invmandoc where bd_invbasdoc.pk_invbasdoc=bd_invmandoc.pk_invbasdoc and bd_invmandoc.pk_corp='");
			sql.append(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
			sql.append("' and bd_invbasdoc.invcode='");
			sql.append(vmanagecode);
			sql.append("'");
			
			try {
				@SuppressWarnings("unchecked")
				Vector<Vector<Object>> cmanagepkdata = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
				if (cmanagepkdata!=null && cmanagepkdata.size()>0){
					queryLinkData.setCmanageid(cmanagepkdata.get(0).get(0).toString());
					if (cmanagepkdata.get(0).get(1).toString().equals(GZCGConstant.DEFDOCMATERAILPK.getValue()))
						targetFunCode = GZCGConstant.MATERIALSTATISTICSUIFUNCODE.getValue();
					else if (cmanagepkdata.get(0).get(1).toString().equals(GZCGConstant.DEFDOCSUBMATERAILPK.getValue()))
						targetFunCode = GZCGConstant.ASSISTMATERIALSTATISTICSUIFUNCODE.getValue();
					else if (cmanagepkdata.get(0).get(1).toString().equals(GZCGConstant.DEFDOCSEMIPRODUCTPK.getValue()))
						targetFunCode = GZCGConstant.SEMIPRODUCTSTATISTICSUIFUNCODE.getValue();
					else if (cmanagepkdata.get(0).get(1).toString().equals(GZCGConstant.DEFDOCPODUCTPK.getValue()))
						targetFunCode = GZCGConstant.PRODUCTSTATISTICSUIFUNCODE.getValue();
				}
			} catch (BusinessException e) {
			}
		}
		queryLinkData.setVoCondition(getQueryPanel().getConditionVO());
		return targetFunCode;
	}
}
