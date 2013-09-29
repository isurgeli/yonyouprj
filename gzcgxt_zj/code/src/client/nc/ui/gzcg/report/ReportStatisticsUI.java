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
import nc.itf.gzcg.pub.GZCGReportStatisticsConst;
import nc.itf.gzcg.pub.ISQLSection;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.BillColumnHelper;
import nc.ui.gzcg.pub.BillTableValueRangeRender;
import nc.ui.gzcg.pub.ReportLinkQueryData;
import nc.ui.gzcg.pub.ReportUIEx;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UICheckBox;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModelCellEditableController;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.linkoperate.ILinkQuery;
import nc.ui.pub.linkoperate.ILinkQueryData;
import nc.ui.pub.report.ReportItem;
import nc.ui.qc.standard.CheckstandardHelper;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.cquery.FldgroupVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.qc.query.CheckstandardItemVO;

@SuppressWarnings("restriction")
public abstract class ReportStatisticsUI extends ReportUIEx implements ILinkQuery{
	protected Hashtable<ISQLSection, UICheckBox> sqlCtrls;
	private Hashtable<String, String> checkItemNames;
	private Hashtable<String, String> checkItemStandards;
	private ReportItem[] initBodyItems;
	protected ReportConigParam reportConfig;
	protected IAdditionSQLProcess sqlProcesser;
	
	private UIRefPane invDocRef;
	private UITextField invSpec;
	private UITextField invUnit;
	private ConditionVO[] linkConditions;
	
	private UIPanel leftgridpanel;
	private BillCardPanel m_leftBillCard;
	private BillCardPanel m_topBillCard;
	private BillColumnHelper leftColHelper;
	private UIPanel panelSumGrid;
	
	private ArrayList<AnalysisReportVO> reportDataCache;
	
	protected ButtonObject bnfilter;
	private ConditionVO[] conditionCache;
	private BillColumnHelper reportColHelper; 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double epsilon = 1e-4;

	@Override
	public ButtonObject[] getButtons() {
		if (bnfilter==null) {
			bnfilter = new ButtonObject("标准过滤", "标准过滤", 2, "标准过滤"); 
		}
		
		return new ButtonObject[]{bnQuery, bnPrint, bnModelPrint, bnPreview, bnOut, bnShow,
				bnFilter, bnSubtotal, bnMultSort, bnLocate, bnRefresh, bnfilter};
	}
	
	private ArrayList<String> getSelectColsForIndex(int[] idxs){
		ArrayList<String> cols = new ArrayList<String>();
		for (int idx : idxs){
			if (sqlCtrls.get(GZCGReportStatisticsConst.values()[idx]).isSelected())
				cols.addAll(Arrays.asList(GZCGReportStatisticsConst.values()[idx].getCols()));
		}
		return cols;
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		add(getLefrGridPanel(), "West");
	}

	private UIPanel getLefrGridPanel() {
		if (leftgridpanel==null){
			leftgridpanel = new UIPanel();
			leftgridpanel.setName("UIPanel");
			leftgridpanel.setLayout(new java.awt.BorderLayout(5, 5));
			leftgridpanel.setMaximumSize(new Dimension(300, 400));
			leftgridpanel.setPreferredSize(new Dimension(300, 400));
			
			m_leftBillCard = new BillCardPanel();
			m_leftBillCard.setName("标准过滤");
			m_leftBillCard.setEnabled(true);
			m_leftBillCard.loadTemplet(GZCGConstant.MIXTURETOOLFUNCODE.getValue(), getBusitype(), ClientEnvironment.getInstance().getUser().getPrimaryKey()
					, ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
			
			m_leftBillCard.getBillTable().getColumnModel().getColumn(3).setHeaderValue("筛选标准");
			m_leftBillCard.getBillTable().getColumnModel().getColumn(4).setMinWidth(0);
			m_leftBillCard.getBillTable().getColumnModel().getColumn(4).setMaxWidth(0);
			m_leftBillCard.getBillTable().getColumnModel().getColumn(4).setPreferredWidth(0);
			
			leftColHelper = new BillColumnHelper(m_leftBillCard, "bselect");
			leftColHelper.setSelectColumnHeader();
			
			leftgridpanel.add(m_leftBillCard);
		}
		
		return leftgridpanel;
	}

	@Override
	public void setReportData(ConditionVO[] voCondition) {
		conditionCache = voCondition;
		
		getReportPanel().setBody_Items(initBodyItems);
		reportDataCache = null;
		
		ArrayList<String> allshowcols = new ArrayList<String>();
		allshowcols.addAll(getSelectColsForIndex(reportConfig.dimention));
		
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
						reportData.setAttributeValue("bselect", true);
						for(int colIdx=0;colIdx<dimensionCount;colIdx++){
							if (data.get(rowIdx).get(colIdx)!=null)
								reportData.setAttributeValue(allshowcols.get(colIdx), data.get(rowIdx).get(colIdx));
						}
						reportSet.put(key.toString(), reportData);
						reportArray.add(reportData);
					}
				}

				processCrossData(reportSet, data);
				
				getStockNum(reportArray);
				
				reportDataCache = reportArray;
				
				onFilterCheckValue();
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getStockNum(ArrayList<AnalysisReportVO> reportArray) {
		ArrayList<String> vstockBatchNo = new ArrayList<String>();
		Hashtable<String, ArrayList<AnalysisReportVO>> voMap = new Hashtable<String, ArrayList<AnalysisReportVO>>();
		for(int i=0;i<reportArray.size();i++){
			
			if (reportArray.get(i).getAttributeValue("vstockbatch") != null){
				String batch = reportArray.get(i).getAttributeValue("vstockbatch").toString();
				vstockBatchNo.add(batch);
				if (voMap.containsKey(batch)){
					voMap.get(batch).add(reportArray.get(i));
				}else{
					voMap.put(batch, new ArrayList<AnalysisReportVO>());
					voMap.get(batch).add(reportArray.get(i));
				}
			}
			reportArray.get(i).setAttributeValue("nstocknum", 0);
		}
		
		if (vstockBatchNo.size()==0) return;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ic_onhandnum.vlot, sum(ic_onhandnum.nonhandnum) from ic_onhandnum where ic_onhandnum.cinventoryid='");
		sql.append(invDocRef.getRefPK());
		sql.append("' and ic_onhandnum.pk_corp='");
		sql.append(ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
		sql.append("' and ic_onhandnum.vlot in ");
		sql.append(getInSql(vstockBatchNo.toArray(new String[]{})));
		sql.append(" group by ic_onhandnum.vlot");
		
		IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			for (int i=0;i<data.size();i++){
				for(int j=0;j<voMap.get(data.get(i).get(0).toString()).size();j++)
					voMap.get(data.get(i).get(0).toString()).get(j).setAttributeValue("nstocknum", data.get(i).get(1));
			}
		}catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	
	private String getInSql(String[] pks){
		StringBuffer sql = new StringBuffer();
		sql.append("(");
		for(String key : pks){
			sql.append("'"+key+"',");
		}
		sql.replace(sql.length()-1, sql.length(), ")");
		
		return sql.toString();
	}

	private ArrayList<AnalysisReportVO> filterByCheckStandard() {
		if (reportDataCache==null) 
			return null;
		
		ArrayList<Double[]> conditionValues = new ArrayList<Double[]>();
		for(int i=0;i<m_leftBillCard.getBillTable().getRowCount();i++){
			conditionValues.add(parseCheckStandard(m_leftBillCard.getBillModel().getValueAt(i, "vrequirevalue").toString()));
		}
		ArrayList<AnalysisReportVO> ret = new ArrayList<AnalysisReportVO>();
		for(int i=0;i<reportDataCache.size();i++){
			boolean ok = true;
			for(int j=0;j<conditionValues.size();j++){
				Object objValue = reportDataCache.get(i).getAttributeValue("_CHECKTITEM"+j);
				if (objValue==null) continue;
				UFDouble value = null;
				try{
					value = new UFDouble(objValue.toString());
					if (value.doubleValue()<conditionValues.get(j)[0] || value.doubleValue()>conditionValues.get(j)[1]){
						ok = false;
						break;
					}
				}catch(NumberFormatException nex){
					value = new UFDouble(0);
				}
			}
			if (ok) 
				ret.add(reportDataCache.get(i));
		}
		return ret;
	}
	
	private ArrayList<AnalysisReportVO> computeSumData(ArrayList<AnalysisReportVO> showData) {
		
		String starttime = "";
		String endtime = "";
		String cust = "";
		String custclass = "";
		UFDouble inNum = new UFDouble(0);
		UFDouble stockNum = new UFDouble(0);
		
		for(int i=0;i<conditionCache.length;i++){
			if (conditionCache[i].getFieldName().indexOf("时间")>-1){
				if (conditionCache[i].getOperaCode().equals(">") || conditionCache[i].getOperaCode().equals(">="))
					starttime = conditionCache[i].getValue();
				else if (conditionCache[i].getOperaCode().equals("<") || conditionCache[i].getOperaCode().equals("<="))
					endtime = conditionCache[i].getValue();
				else if (conditionCache[i].getOperaCode().equals("="))
					starttime = endtime = conditionCache[i].getValue();
			}else if (conditionCache[i].getFieldName().equals("供应商")){
				cust = conditionCache[i].getValue();
			}else if (conditionCache[i].getFieldName().equals("供应商分类")){
				custclass = conditionCache[i].getValue();
			}
		}
		
		Hashtable<String, String> computed = new Hashtable<String, String>();
		for(int i=0;i<showData.size();i++){
			Object batchCode = showData.get(i).getAttributeValue("vstockbatch");
			if (batchCode==null) continue;
			if (!computed.containsKey(batchCode.toString())){
				inNum = inNum.add(new UFDouble(showData.get(i).getAttributeValue("ninnum").toString()));
				stockNum = stockNum.add(new UFDouble(showData.get(i).getAttributeValue("nstocknum").toString()));
				computed.put(batchCode.toString(), batchCode.toString());
			}
		}
		
		AnalysisReportVO vo = new AnalysisReportVO();
		if (starttime.equals(endtime))
			vo.setAttributeValue("vtimesec", starttime);
		else
			vo.setAttributeValue("vtimesec", starttime+"至"+endtime);
		
		if (cust.length()>0){
			String sql = "select bd_cubasdoc.custname from bd_cumandoc, bd_cubasdoc where bd_cumandoc.pk_cubasdoc=bd_cubasdoc.pk_cubasdoc and bd_cumandoc.pk_cumandoc='";
			sql+=cust;
			sql+="' ";
			IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			try {
				@SuppressWarnings("unchecked")
				Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql, new VectorProcessor());
				vo.setAttributeValue("vcustdesc", data.get(0).get(0));
			}catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (custclass.length()>0){
			String sql = "select bd_areacl.areaclname from bd_areacl where bd_areacl.pk_areacl='";
			sql+=custclass;
			sql+="' ";
			IUAPQueryBS dao = NCLocator.getInstance().lookup(IUAPQueryBS.class);
			try {
				@SuppressWarnings("unchecked")
				Vector<Vector<Object>> data = (Vector<Vector<Object>>)dao.executeQuery(sql, new VectorProcessor());
				vo.setAttributeValue("vcustdesc", data.get(0).get(0));
			}catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			vo.setAttributeValue("vcustdesc", "全部供应商");
		
		vo.setAttributeValue("vinvname", invDocRef.getRefName());
		vo.setAttributeValue("ninnum", inNum);
		vo.setAttributeValue("nstocknum", stockNum);
		
		ArrayList<AnalysisReportVO> ret = new ArrayList<AnalysisReportVO>();
		ret.add(vo);
		
		return ret;
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
		
		processTableLayout(getReportPanel(), checkItems);
	}

	private void processTableLayout(ReportPanel rp, String[] checkItems) {
		ReportItem[] reportItems = new ReportItem[checkItems.length];
		for(int i=0;i<checkItems.length;i++){
			ReportItem item = new ReportItem();
			item.setWidth(80);
			item.setKey("_CHECKTITEM"+i);
			item.setName(checkItemNames.get(checkItems[i]));//(checkItemStandards.get(checkItems[i]))
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
		
		rp.setFieldGroup(groupVOs.toArray(new FldgroupVO[]{}));
		rp.setBody_Items(finalBodyItems.toArray(new ReportItem[]{}));
		
		setReportPanelCellRender(rp, checkItems);
	}
	
	private void setReportPanelCellRender(ReportPanel rp, String[] checkitemids) {
		for(int i=0;i<checkitemids.length;i++) {
			String standard = checkItemStandards.get(checkitemids[i]);
			Double[] itemStandardValue = parseCheckStandard(standard);

			BillItem billItem = rp.getBodyItem("_CHECKTITEM"+i);
			BillTableValueRangeRender render = new BillTableValueRangeRender(billItem, itemStandardValue[0], itemStandardValue[1]);
			rp.getBillTable().getColumn(checkItemNames.get(checkitemids[i])).setCellRenderer(render);
		}
	}

	private Double[] parseCheckStandard(String standard) {
		Double[] itemStandardValue = new Double[]{new Double(0),new Double(100)};
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
		return itemStandardValue;
	}

	private int getDimensionCount() {
		int ret = 0;
		for (int idx : reportConfig.dimention){
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
		for(int i=0;i<m_leftBillCard.getBillTable().getRowCount();i++){
			if (m_leftBillCard.getBillModel().getValueAt(i, "bselect").equals(true))
				checkItems.add(m_leftBillCard.getBillModel().getValueAt(i, "ccheckitemid").toString());
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
			reportColHelper = new BillColumnHelper(reportpanel, "bselect");
			//setReportPanelHeader();
			reportpanel.getBillModel().getItemByKey("ninnum").setDecimalDigits(2);
			reportpanel.getBillModel().getItemByKey("nstocknum").setDecimalDigits(2);
			initBodyItems = reportpanel.getBody_Items();
			
			((nc.ui.pub.bill.BillModel)getReportPanel().getBillTable().getModel()).setCellEditableController(new BillModelCellEditableController() {
				public boolean isCellEditable(boolean value, int row, String itemkey) {
					if (itemkey.equals("bselect")) 
						return true;
					else
						return false;
				}
			});
		}
		return reportpanel;
	}
	
	private void setReportPanelHeader() {
		reportColHelper.setSelectColumnHeader();
		reportColHelper.setSelectColumnMultiSelect();
	}
	
	@Override
	protected UIPanel getConditionPanel() {
		if (conditionpanel==null){
			setReportStatisticsConfig();
			checkItemStandards = new Hashtable<String, String>();
			
			UIPanel conditionPanel = super.getConditionPanel();
			conditionPanel.setMaximumSize(null);
			conditionPanel.setPreferredSize(null);
			conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
			UIPanel panel1 = new UIPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
			panelSumGrid = new UIPanel();
			panelSumGrid.setLayout(new java.awt.BorderLayout(2, 2));
			
			m_topBillCard = new BillCardPanel();
			m_topBillCard.setName("汇总");
			m_topBillCard.setEnabled(true);
			m_topBillCard.loadTemplet(getNodeCode(), getBusitype(), ClientEnvironment.getInstance().getUser().getPrimaryKey()
					, ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
			m_topBillCard.setMaximumSize(new Dimension(600, 70));
			m_topBillCard.setPreferredSize(new Dimension(600, 70));
			//m_topBillCard.getBillModel().getItemByKey("ninnum").setDecimalDigits(2);
			//m_topBillCard.getBillModel().getItemByKey("nstocknum").setDecimalDigits(2);
			panelSumGrid.add(m_topBillCard);
			
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
			
			invUnit = new UITextField();
			panel1.add(new UILabel("单位"));
			panel1.add(invUnit);
			invUnit.setEditable(false);
			invUnit.setColumns(10);
			
			conditionPanel.add(panel1);
			conditionPanel.add(panelSumGrid);			
		}
		
		return conditionpanel;
	}
	
	private void updateCheckItemCtrl() {
		reportpanel.getBillModel().clearBodyData();
		String checkStandardid = null;
		
		StringBuffer sql = new StringBuffer();
		sql.append("select qc_invrelate.ccheckstandardid, nvl(bd_invbasdoc.invspec,'-'), nvl(bd_measdoc.measname,'-') from qc_invrelate, bd_invbasdoc, bd_invmandoc, bd_measdoc where bd_invbasdoc.pk_invbasdoc=bd_invmandoc.pk_invbasdoc and bd_invmandoc.pk_invmandoc=qc_invrelate.cmangid and bd_invbasdoc.pk_measdoc=bd_measdoc.pk_measdoc(+) and qc_invrelate.bdefault='Y' and qc_invrelate.cmangid='");
		sql.append(invDocRef.getRefPK()+"'");
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> checkStandard = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			if (checkStandard!=null && checkStandard.size()>0) {
				invSpec.setText(checkStandard.get(0).get(1).toString());
				invUnit.setText(checkStandard.get(0).get(2).toString());
				
				checkStandardid=checkStandard.get(0).get(0).toString();
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		if (checkStandardid==null || checkStandardid.length()!=20) {
			m_leftBillCard.getBillModel().clearBodyData();
			return;
		}
		
		try {
			CheckstandardItemVO[] standardItems = CheckstandardHelper.queryCheckstandardItems(checkStandardid);
			AnalysisReportVO[] uiCheckItems = new AnalysisReportVO[standardItems.length];
			checkItemNames = new Hashtable<String, String>();

			for(int i=0;i<standardItems.length;i++){
				uiCheckItems[i] = new AnalysisReportVO();
				uiCheckItems[i].setAttributeValue("bselect", true);
				uiCheckItems[i].setAttributeValue("ccheckitemid", standardItems[i].getCcheckitemid());
				String standardValue = standardItems[i].getCstandardvalue();
				int start = standardValue.indexOf("合格:")+3;
				int end = standardValue.indexOf("}", start);		
				standardValue = standardValue.substring(start, end);
				checkItemStandards.put(standardItems[i].getCcheckitemid(), standardValue);
				uiCheckItems[i].setAttributeValue("vstandardvalue", standardValue);
				uiCheckItems[i].setAttributeValue("vrequirevalue", "[0,100]");
			}
			m_leftBillCard.getBillModel().setBodyDataVO(uiCheckItems);
			m_leftBillCard.getBillModel().execLoadFormula();
			
			for(int i=0;i<m_leftBillCard.getBillTable().getRowCount();i++){
				checkItemNames.put(m_leftBillCard.getBillModel().getValueAt(i, "ccheckitemid").toString()
						, m_leftBillCard.getBillModel().getValueAt(i, "vcheckitem").toString());
			}
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
				cvo.setFieldName("供应商");
				cvo.setFieldCode("gzcg_qcrp_checkbill_v.cvendormangid");
				cvo.setOperaCode("=");
				cvo.setValue(((ReportLinkQueryData) querydata).getCvendormangid());
				newCondition.add(cvo);
			}
			//getQueryPanel().setShowConditionVOs(newCondition.toArray(new ConditionVO[]{}));
			linkConditions = newCondition.toArray(new ConditionVO[]{});
			conditionCache = linkConditions;
			setReportData(linkConditions);
			
			if (reportpanel.getBillModel().getRowCount()!=0)
				setButtonStatus("QUERY");
			else
				setButtonStatus("INIT");
			updateUI();
		}
	}
	
	@Override
	public void onButtonClicked(ButtonObject bo) {
		super.onButtonClicked(bo);
		
		if (bo == bnfilter)
			onFilterCheckValue();
	}

	private void onFilterCheckValue() {
		ArrayList<AnalysisReportVO> showData = filterByCheckStandard();
		
		ArrayList<AnalysisReportVO> sumData = computeSumData(showData);

		if (showData!=null) setData(showData.toArray(new AnalysisReportVO[]{}));
		if (sumData!=null) m_topBillCard.getBillModel().setBodyDataVO(sumData.toArray(new AnalysisReportVO[]{}));
		
		setReportPanelHeader();
	}
	
	@Override
	public void onOut() {
		//super.onOut();
		//return;
		
		ArrayList<CircularlyAccessibleValueObject> data = new ArrayList<CircularlyAccessibleValueObject>();
		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (reportpanel.getBillModel().getValueAt(row, "bselect").equals(true)){
				data.add(report[row]);
			}
		}
		ReportPanel rp = new ReportPanel();
		rp.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID("scmpub", "UPPscmpub-000778")/* @res "报表基类" */);
		try {
			rp.setTempletID(getCorpPrimaryKey(), getNodeCode(),
					getClientEnvironment().getUser().getPrimaryKey(),
					getBusitype());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String[] checkItems = getSelectedCheckItem();
		processTableLayout(rp, checkItems);
		rp.setTatolRowShow(false);
		rp.setBodyDataVO(data.toArray(new CircularlyAccessibleValueObject[]{}));
		rp.getBillModel().execLoadFormula();
		rp.updateValue();
		
		rp.exportExcelFile();
	}
}

