package nc.ui.gzcg.report;

import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import javax.swing.BoxLayout;
import nc.bs.framework.common.NCLocator;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.gzcg.pub.GZCGReportStatisticsConst;
import nc.itf.gzcg.pub.ISQLSection;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.BillTableValueRangeRender;
import nc.ui.gzcg.pub.ReportLinkQueryData;
import nc.ui.gzcg.pub.ReportUIEx;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.pub.report.ReportItem;
import nc.ui.qc.standard.CheckstandardHelper;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.cquery.FldgroupVO;
import nc.vo.pub.query.ConditionVO;
import nc.vo.qc.query.CheckstandardItemVO;

@SuppressWarnings("restriction")
public abstract class ReportStatisticsUI extends ReportUIEx implements ILinkQuery{
	protected Hashtable<ISQLSection, UICheckBox> sqlCtrls;
	private Hashtable<String, UICheckBox> checkItemCtrls;
	private Hashtable<String, String> checkItemStandards;
	private ArrayList<String> allCheckItems;
	private ReportItem[] initBodyItems;
	protected ReportConigParam reportConfig;
	protected IAdditionSQLProcess sqlProcesser;
	
	private UIRefPane invDocRef;
	private UIPanel panelCheckItem;
	private UITextField invSpec;
	private ConditionVO[] linkConditions;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double epsilon = 1e-4;

	@Override
	public ButtonObject[] getButtons() {
		return new ButtonObject[]{bnQuery, bnPrint, bnModelPrint, bnPreview, bnOut, bnShow,
				bnFilter, bnSubtotal, bnMultSort, bnLocate, bnRefresh};
	}
	
	private ArrayList<String> getSelectColsForIndex(int start, int end){
		ArrayList<String> cols = new ArrayList<String>();
		for (int idx=start;idx<=end;idx++){
			if (sqlCtrls.get(GZCGReportStatisticsConst.values()[idx]).isSelected())
				cols.addAll(Arrays.asList(GZCGReportStatisticsConst.values()[idx].getCols()));
		}
		return cols;
	}

	@Override
	public void setReportData(ConditionVO[] voCondition) {
		getReportPanel().setBody_Items(initBodyItems);
		
		ArrayList<String> allshowcols = new ArrayList<String>();
		allshowcols.addAll(getSelectColsForIndex(reportConfig.dimentionStart, reportConfig.dimentionEnd));
		
		String sql = new SQLBuildUtil(GZCGReportStatisticsConst.values(), reportConfig, sqlProcesser).
				getSQLForReportStatistics(sqlCtrls, voCondition, getInvCheckItemWhere());
		
		if (!getMainViewName().equals(GZCGConstant.MATERIALMAINVIEW.getValue()))
			sql = sql.replaceAll(GZCGConstant.MATERIALMAINVIEW.getValue(), getMainViewName());
		
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		int dimensionCount = getDimensionCount();
		Hashtable<String, AnalysisReportVO> reportSet = new Hashtable<String, AnalysisReportVO>();
		ArrayList<AnalysisReportVO> reportArray = new ArrayList<AnalysisReportVO>();
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql, new VectorProcessor());
			if (data!=null && data.size()>0){
				for(int rowIdx=0;rowIdx<data.size();rowIdx++){
					StringBuffer key = new StringBuffer();
					for(int colIdx=0;colIdx<dimensionCount;colIdx++) 
						if (data.get(rowIdx).get(colIdx)!=null)
							key.append(data.get(rowIdx).get(colIdx).toString());
					if (!reportSet.containsKey(key.toString())){
						AnalysisReportVO reportData = new AnalysisReportVO();
						for(int colIdx=0;colIdx<dimensionCount;colIdx++){
							if (data.get(rowIdx).get(colIdx)!=null)
								reportData.setAttributeValue(allshowcols.get(colIdx), data.get(rowIdx).get(colIdx));
						}
						reportSet.put(key.toString(), reportData);
						reportArray.add(reportData);
					}
				}

				processCrossData(reportSet, data);

				setData(reportArray.toArray(new AnalysisReportVO[]{}));
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void processCrossData(Hashtable<String, AnalysisReportVO> reportSet, Vector<Vector<Object>> data){
		int crossColIdx = getDimensionCount();
		String[] checkItems = getSelectedCheckItem();
		Hashtable<String, Integer> checkItemIdx = new Hashtable<String, Integer>();
		for(int i=0;i<checkItems.length;i++)
			checkItemIdx.put(checkItems[i], i);
		
		for(int i=0;i<data.size();i++){
			StringBuffer key = new StringBuffer();
			for(int j=0;j<crossColIdx;j++) 
				if (data.get(i).get(j)!=null)
					key.append(data.get(i).get(j).toString());
			
			reportSet.get(key.toString()).setAttributeValue("_CHECKTITEM"+checkItemIdx.get(data.get(i).get(crossColIdx).toString())
					, data.get(i).get(crossColIdx+1).toString());
		}
		
		processTableLayout(checkItems);
	}

	private void processTableLayout(String[] checkItems) {
		ReportItem[] reportItems = new ReportItem[checkItems.length];
		for(int i=0;i<checkItems.length;i++){
			ReportItem item = new ReportItem();
			item.setWidth(80);
			item.setKey("_CHECKTITEM"+i);
			item.setName(checkItemCtrls.get(checkItems[i]).getText());//(checkItemStandards.get(checkItems[i]))
			item.setDataType(IBillItem.STRING);
			//item.setDecimalDigits(4);
			reportItems[i] = item;
		}
		ArrayList<ReportItem> finalBodyItems = new ArrayList<ReportItem>();
		finalBodyItems.addAll(Arrays.asList(getReportPanel().getBody_Items()));
		finalBodyItems.addAll(Arrays.asList(reportItems));
		
		Vector<FldgroupVO> groupVOs = new Vector<FldgroupVO>();
		for(int i=0;i<reportItems.length;i++){
			FldgroupVO groupVO1 = new FldgroupVO();
			groupVO1.setGroupid(i+1);
			groupVO1.setGroupname(checkItemStandards.get(checkItems[i]));//(checkItemCtrls.get(checkItems[i]).getText());
			groupVO1.setGrouptype("0");
			groupVO1.setItem1(String.valueOf(finalBodyItems.size()-reportItems.length+i));
			groupVO1.setItem2(null);
			groupVO1.setToplevelflag("Y");
			groupVOs.add(groupVO1);
		}
//		for(int i=0;i<reportItems.length-1;i++){
//			FldgroupVO groupVO2 = new FldgroupVO();
//			groupVO2.setGroupid(i+1+reportItems.length);
//			groupVO2.setGroupname("检验项目");
//			groupVO2.setGrouptype("3");
//			if(i==0)
//				groupVO2.setItem1(checkItemCtrls.get(checkItems[i]).getText());
//			else
//				groupVO2.setItem1("检验项目");
//			groupVO2.setItem2(checkItemCtrls.get(checkItems[i+1]).getText());
//			if (i == reportItems.length-2)
//				groupVO2.setToplevelflag("Y");
//			else
//				groupVO2.setToplevelflag("N");
//			groupVOs.add(groupVO2);
//		}
		
		getReportPanel().setFieldGroup(groupVOs.toArray(new FldgroupVO[]{}));
		getReportPanel().setBody_Items(finalBodyItems.toArray(new ReportItem[]{}));
		
		setReportPanelCellRender(checkItems);
	}
	
	private void setReportPanelCellRender(String[] checkitemids) {
		for(int i=0;i<checkitemids.length;i++) {
			String standard = checkItemStandards.get(checkitemids[i]);
			double[] itemStandardValue = new double[]{0,100};
			String[] sec = standard.trim().split(",");
			if (sec.length==2){
				try {
					itemStandardValue[0]=Double.parseDouble(sec[0].substring(1));
					itemStandardValue[1]=Double.parseDouble(sec[1].substring(0,sec[1].length()-1));
				} catch (NumberFormatException ex) {
					ex.toString();
				}
				if (sec[0].charAt(0) == '(') itemStandardValue[0]+=epsilon;
				if (sec[1].charAt(sec[1].length()-1) == ')') itemStandardValue[0]-=epsilon;
			}

			BillItem billItem = reportpanel.getBodyItem("_CHECKTITEM"+i);
			BillTableValueRangeRender render = new BillTableValueRangeRender(billItem, itemStandardValue[0], itemStandardValue[1]);
			reportpanel.getBillTable().getColumn(checkItemCtrls.get(checkitemids[i]).getText()).setCellRenderer(render);
		}
	}

	private int getDimensionCount() {
		int ret = 0;
		for (int idx=reportConfig.dimentionStart;idx<=reportConfig.dimentionEnd;idx++){
			if (sqlCtrls.get(GZCGReportStatisticsConst.values()[idx]).isSelected())
				ret+=GZCGReportStatisticsConst.values()[idx].getCols().length;
		}
		
		return ret;
	}

	private String getInvCheckItemWhere() {
		StringBuffer sql = new StringBuffer();
		sql.append(" and gzcg_qcrp_checkbill_v.cmangid='"+invDocRef.getRefPK()+"' ");
		
		StringBuffer checkItemsSql = new StringBuffer();
		String[] checkItems = getSelectedCheckItem();
		
		for(int i=0;i<checkItems.length;i++)
			checkItemsSql.append("'"+checkItems[i]+"', ");
		checkItemsSql.delete(checkItemsSql.length()-2, checkItemsSql.length()-1);
		
		sql.append(" and qc_checkbill_b2.ccheckitemid in("+checkItemsSql.toString()+") ");
		
		return sql.toString();
	}
	
	private String[] getSelectedCheckItem(){
		ArrayList<String> checkItems = new ArrayList<String>();
		for(String checkItem : allCheckItems){
			if (checkItemCtrls.get(checkItem).isSelected())
				checkItems.add(checkItem);
		}
		
		return checkItems.toArray(new String[]{});
	}

	@Override
	public void setTotalRowShow() {
	}
	
	@Override
	public boolean isConPanelShow() {
		return true;
	}
	
	@Override
	public ReportPanel getReportPanel() {
		if (reportpanel == null) {
			super.getReportPanel();
			hideReportPanel(reportpanel);
			initBodyItems = reportpanel.getBody_Items();
		}
		return reportpanel;
	}
	
	@Override
	protected UIPanel getConditionPanel() {
		if (conditionpanel==null){
			setReportStatisticsConfig();
			checkItemCtrls = new Hashtable<String, UICheckBox>();
			allCheckItems = new ArrayList<String>();
			checkItemStandards = new Hashtable<String, String>();
			
			UIPanel conditionPanel = super.getConditionPanel();
			conditionPanel.setMaximumSize(null);
			conditionPanel.setPreferredSize(null);
			conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
			UIPanel panel1 = new UIPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
			panelCheckItem = new UIPanel(new java.awt.GridLayout(2, 12, 5, 2));
			
			invDocRef = new UIRefPane("存货档案");
			panel1.add(new UILabel("存货档案"));
			panel1.add(invDocRef);
			
			invDocRef.addValueChangedListener(new ValueChangedListener() {	
				public void valueChanged(ValueChangedEvent event) {
					updateCheckItemCtrl();
				}
			});
			
			invSpec = new UITextField();
			panel1.add(new UILabel("规格"));
			panel1.add(invSpec);
			invSpec.setEditable(false);
			invSpec.setColumns(10);
			
			conditionPanel.add(panel1);
			conditionPanel.add(panelCheckItem);			
		}
		
		return conditionpanel;
	}
	
	private void updateCheckItemCtrl() {
		panelCheckItem.removeAll();
		checkItemCtrls.clear();
		allCheckItems.clear();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select qc_checkitem.ccheckitemid, qc_checkitem.ccheckitemname, qc_checkstandard_b.ccheckstandardid, nvl(bd_invbasdoc.invspec,'-') from qc_invrelate, qc_checkstandard_b, qc_checkitem, bd_invmandoc, bd_invbasdoc");
		sql.append(" where qc_invrelate.ccheckstandardid=qc_checkstandard_b.ccheckstandardid and qc_checkitem.ccheckitemid = qc_checkstandard_b.ccheckitemid ");
		sql.append(" and bd_invbasdoc.pk_invbasdoc=bd_invmandoc.pk_invbasdoc and bd_invmandoc.pk_invmandoc=qc_invrelate.cmangid and qc_invrelate.bdefault='Y' and qc_invrelate.cmangid='");
		sql.append(invDocRef.getRefPK()+"'");
		
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> checkItems = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			if (checkItems!=null && checkItems.size()>0){
				invSpec.setText(checkItems.get(0).get(3).toString());
				
				String ccheckstandardid=null;
				for(int i=0;i<checkItems.size();i++){
					UICheckBox ctrl = new UICheckBox(checkItems.get(i).get(1).toString());
					ctrl.setSelected(true);
					checkItemCtrls.put(checkItems.get(i).get(0).toString(), ctrl);
					panelCheckItem.add(ctrl); 
					allCheckItems.add(checkItems.get(i).get(0).toString());
					ccheckstandardid = checkItems.get(i).get(2).toString();
				}
				CheckstandardItemVO[] standardItems = CheckstandardHelper.queryCheckstandardItems(ccheckstandardid);
				for(CheckstandardItemVO standardItem : standardItems){
					String standardValue = standardItem.getCstandardvalue();
					int start = standardValue.indexOf("合格:")+3;
					int end = standardValue.indexOf("}", start);
					standardValue = standardValue.substring(start, end);
					checkItemStandards.put(standardItem.getCcheckitemid(), standardValue);
				}
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onQuery() {
		if (invDocRef==null || invDocRef.getRefPK()==null || invDocRef.getRefPK().length()==0){
			MessageDialog.showWarningDlg(this, "错误", "请先设置存货。");
			return;
		}
		super.onQuery();
	}
	
	protected abstract void hideReportPanel(ReportPanel reportpanel);
	
	protected abstract void setReportStatisticsConfig();
	
	protected abstract String getMainViewName();
	
	@Override
	public void onRefresh() {
		if (getQueryPanel().getConditionVO()!=null && getQueryPanel().getConditionVO().length>0)
			setReportData(getQueryPanel().getConditionVO());
		else if (linkConditions!=null && linkConditions.length>0)
			setReportData(linkConditions);
	}
	
	public void doQueryAction(ILinkQueryData querydata) {
		if (querydata instanceof ReportLinkQueryData){
			invDocRef.setPK(((ReportLinkQueryData) querydata).getCmanageid());
			updateCheckItemCtrl();
			ConditionVO[] voCondition = ((ReportLinkQueryData) querydata).getVoCondition();
			ArrayList<ConditionVO> newCondition = new ArrayList<ConditionVO>();
			for(int i=0;i<voCondition.length;i++){
				if (!voCondition[i].getFieldCode().equals("gzcg_qcrp_checkbill_v.pk_invcl") &&
						!voCondition[i].getFieldCode().equals("gzcg_qcrp_checkbill_v.cmangid")){
					newCondition.add(voCondition[i]);
				}
			}
			if (((ReportLinkQueryData) querydata).getCvendormangid()!=null){
				ConditionVO cvo = new ConditionVO();
				cvo.setFieldCode("gzcg_qcrp_checkbill_v.cvendormangid");
				cvo.setOperaCode("=");
				cvo.setValue(((ReportLinkQueryData) querydata).getCvendormangid());
				newCondition.add(cvo);
			}
			//getQueryPanel().setShowConditionVOs(newCondition.toArray(new ConditionVO[]{}));
			linkConditions = newCondition.toArray(new ConditionVO[]{});
			setReportData(linkConditions);
			
			if (reportpanel.getBillModel().getRowCount()!=0)
				setButtonStatus("QUERY");
			else
				setButtonStatus("INIT");
			updateUI();
		}
	}
}

