package nc.ui.gzcg.mixturetool;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.linear.LinearConstraint;
import org.apache.commons.math3.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optimization.linear.Relationship;
import org.apache.commons.math3.optimization.linear.SimplexSolver;

import nc.bs.framework.common.NCLocator;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.BillTableValueRangeRender;
import nc.ui.gzcg.pub.ReportUIEx;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
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
import nc.ui.pub.report.ReportItem;
import nc.ui.qc.standard.CheckstandardDef;
import nc.ui.qc.standard.CheckstandardHelper;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.cquery.FldgroupVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.qc.query.CheckstandardItemVO;

@SuppressWarnings({ "restriction", "deprecation" })
public class MixtureToolUI extends ReportUIEx{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final double epsilon = 1e-4;
	private UIRefPane invDocRef;
	private UIRefPane checkStandardRef;
	private UITextField mixtureAmountText;
	private UIPanel leftgridpanel;
	private BillCardPanel m_billCardPanel;
	private CheckBoxRenderer checkItemheadChecker;
	
	protected ButtonObject bnStock; 
	protected ButtonObject bnCompute;
	private CheckBoxRenderer stockChecker;
	private ReportItem[] initBodyItems;
	private UITextField invSpec; 

	@Override
	public String getBusitype() {
		return null;
	}

	@Override
	public ButtonObject[] getButtons() {
		if (bnStock==null && bnCompute==null) {
			bnStock = new ButtonObject("获取库存", "获取库存", 2, "获取库存"); 
			bnCompute = new ButtonObject("混料计算", "混料计算", 2, "混料计算");
		}
		return new ButtonObject[]{bnPrint, bnModelPrint, bnPreview, bnOut, bnStock, bnCompute};
	}
	
	@Override
	public void onButtonClicked(ButtonObject bo) {
		super.onButtonClicked(bo);
		
		if (bo == bnStock)
			onGetStock();
		else if (bo == bnCompute)
			onMixtureCompute();
	}

	private void onMixtureCompute() {
		if (mixtureAmountText.getText()==null || mixtureAmountText.getText().length()==0){
			MessageDialog.showWarningDlg(this, "错误", "请先设置混料数量。");
			return;
		}
		UFDouble mixAmount=null;
		try{
			mixAmount = new UFDouble(mixtureAmountText.getText());
		} catch (Exception ex) {
			MessageDialog.showWarningDlg(this, "错误", "请设置正确的混料数量。");
			return;
		}
		
		Hashtable<String, double[]> standardValue = getStandardValue();
		Hashtable<String, String> needComputeCheckItemId = new Hashtable<String, String>();
		String[] checkitemids = getCheckItemId();
		Hashtable<String, Integer> checkItemidNo = new Hashtable<String, Integer>();
		for(int i=0;i<checkitemids.length;i++)
			checkItemidNo.put(checkitemids[i], i);
		ArrayList<Integer> joinStockRowNo = new ArrayList<Integer>();
		
		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (reportpanel.getBillModel().getValueAt(row, "bselect").equals(true) && haveCheckValue(row, checkitemids, checkItemidNo)){
				joinStockRowNo.add(row);
				for(int col=0;col<checkitemids.length;col++){
					double checkValue = Double.parseDouble(reportpanel.getBillModel().getValueAt
							(row, "_CROSS"+checkItemidNo.get(checkitemids[col])).toString());
					if (checkValue>=standardValue.get(checkitemids[col])[0] && checkValue<=standardValue.get(checkitemids[col])[1])
						;
					else
						if (!needComputeCheckItemId.containsKey(checkitemids[col]))
							needComputeCheckItemId.put(checkitemids[col], checkitemids[col]);
				}
			}
		}
		
		double[] ret = doComputeWork(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, mixAmount);
		for (int i=0;i<joinStockRowNo.size();i++){
        	reportpanel.getBillModel().setValueAt(mixAmount.multiply(ret[i]), joinStockRowNo.get(i), "nusenum");
        }
		
		setComputeCheckValue(ret, joinStockRowNo, checkItemidNo);
	}
	
	private void setComputeCheckValue(double[] ret, ArrayList<Integer> joinStockRowNo, Hashtable<String, Integer> checkItemidNo){
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			if (m_billCardPanel.getBillModel().getValueAt(i, "bselect").equals(true)){
				String checkItemid = m_billCardPanel.getBillModel().getValueAt(i, "ccheckitemid").toString();
				double finalCheckValue = 0;
				for(int j=0;j<ret.length;j++){
					double realCheckValue=Double.parseDouble(reportpanel.getBillModel().getValueAt
							(joinStockRowNo.get(j), "_CROSS"+checkItemidNo.get(checkItemid)).toString());
					finalCheckValue+=ret[j]*realCheckValue;
				}
				m_billCardPanel.getBillModel().setValueAt(new UFDouble(finalCheckValue), i, "vcomputevalue");
			}
		}
	}
	
	private double[] doComputeWork(ArrayList<Integer> joinStockRowNo, Hashtable<String, String> needComputeCheckItemId,
			Hashtable<String, double[]> standardValue, Hashtable<String, Integer> checkItemidNo, UFDouble mixAmount){
		double[] objectiveFunctionCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
		objectiveFunctionCoefficients[0] = 1.0;
		LinearObjectiveFunction f = new LinearObjectiveFunction(objectiveFunctionCoefficients, 0.0);
        
        ArrayList <LinearConstraint>constraints = new ArrayList<LinearConstraint>();
        for(String checkItemId : needComputeCheckItemId.keySet()){
        	double[] itemStandardValue = standardValue.get(checkItemId);
        	double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
    		for (int i=0;i<joinStockRowNo.size();i++){
    			constraintCoefficients[i]=Double.parseDouble(reportpanel.getBillModel().getValueAt
						(joinStockRowNo.get(i), "_CROSS"+checkItemidNo.get(checkItemId)).toString());
    		}
        	if (itemStandardValue[0]>epsilon){
        		constraints.add(new LinearConstraint(constraintCoefficients.clone(), Relationship.GEQ, itemStandardValue[0]));
        	}
        	if (itemStandardValue[0]<1-epsilon){
        		constraints.add(new LinearConstraint(constraintCoefficients.clone(), Relationship.LEQ, itemStandardValue[1]));
        	}
        }
        for (int i=0;i<joinStockRowNo.size();i++){
        	double stockAmount = Double.parseDouble(reportpanel.getBillModel().getValueAt(joinStockRowNo.get(i), "nstocknum").toString());
        	if (stockAmount < mixAmount.doubleValue()){
        		double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
        		constraintCoefficients[i] = 1.0;
        		constraints.add(new LinearConstraint(constraintCoefficients, Relationship.LEQ, stockAmount/mixAmount.doubleValue()));
        	}
        }
        {
        	double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 1.0);
        	constraints.add(new LinearConstraint(constraintCoefficients, Relationship.EQ, 1));
        }
        
        PointValuePair solution = new SimplexSolver().optimize(f, constraints, GoalType.MAXIMIZE, true);
        return solution.getPoint();
	}
	
	private double[] getDoubleArray(int count, double value){
		double[] ret = new double[count];
		for(int i=0;i<count;i++)
			ret[i] = value;
		return ret;
	}

	private boolean haveCheckValue(int row, String[] checkitemids, Hashtable<String, Integer> checkItemidNo) {
		for(int i=0;i<checkitemids.length;i++){
			Object checkValueObj = reportpanel.getBillModel().getValueAt(row, "_CROSS"+checkItemidNo.get(checkitemids[i]));
			if (checkValueObj == null || checkValueObj.toString().length()==0)
				return false;
		}
		return true;
	}

	private Hashtable<String, double[]> getStandardValue() {
		Hashtable<String, double[]> standardValue = new Hashtable<String, double[]>();
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			if (m_billCardPanel.getBillModel().getValueAt(i, "bselect").equals(true)){
				String checkItems = m_billCardPanel.getBillModel().getValueAt(i, "ccheckitemid").toString();
				String requirevalue = m_billCardPanel.getBillModel().getValueAt(i, "vrequirevalue").toString();
				
				String[] sec = requirevalue.trim().split(",");
				if (sec.length!=2){
					MessageDialog.showWarningDlg(this, "错误", "请设置正确的检测标准值。");
					return null;
				}
				double[] value = new double[2];
				try {
					value[0]=Double.parseDouble(sec[0].substring(1));
					value[1]=Double.parseDouble(sec[1].substring(0,sec[1].length()-1));
				} catch (NumberFormatException ex) {
					MessageDialog.showWarningDlg(this, "错误", "请设置正确的检测标准值。");
					return null;
				}

				if (sec[0].charAt(0) == '(') value[0]+=epsilon;
				if (sec[1].charAt(sec[1].length()-1) == ')') value[0]-=epsilon;
				
				standardValue.put(checkItems, value);
			}
		}
		
		return standardValue;
	}

	private void onGetStock() {
		getReportPanel().setBody_Items(initBodyItems);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select bd_invbasdoc.invname, bd_stordoc.storname, ic_onhandnum.vlot, sum(ic_onhandnum.nonhandnum), ic_onhandnum.cwarehouseid from ic_onhandnum, bd_invbasdoc, bd_stordoc ");
		sql.append("where ic_onhandnum.cwarehouseid=bd_stordoc.pk_stordoc and ic_onhandnum.cinvbasid=bd_invbasdoc.pk_invbasdoc and ic_onhandnum.cinventoryid='");
		sql.append(invDocRef.getRefPK()+"' and ic_onhandnum.nonhandnum > 0 group by bd_invbasdoc.invname, bd_stordoc.storname, ic_onhandnum.vlot, ic_onhandnum.cwarehouseid order by ic_onhandnum.vlot");
		
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> stock = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			AnalysisReportVO[] uiStocks = new AnalysisReportVO[stock.size()];
			Hashtable<String, AnalysisReportVO> uiStockSet = new Hashtable<String, AnalysisReportVO>();
			Hashtable<String, String> warehousePKSet = new Hashtable<String, String>();
			Hashtable<String, String> stockBatchSet = new Hashtable<String, String>();
			for(int i=0;i<stock.size();i++){
				uiStocks[i] = new AnalysisReportVO();
				
				String cwarehouseid = stock.get(i).get(4).toString();
				String vstockbatch = stock.get(i).get(2).toString();
				
				uiStocks[i].setAttributeValue("bselect", true);
				uiStocks[i].setAttributeValue("vinvdocname", stock.get(i).get(0).toString());
				uiStocks[i].setAttributeValue("vstock", stock.get(i).get(1).toString());
				uiStocks[i].setAttributeValue("vbatchcode", vstockbatch);
				uiStocks[i].setAttributeValue("nstocknum", new UFDouble(stock.get(i).get(3).toString()));
				
				String key=cwarehouseid+vstockbatch;
				uiStockSet.put(key, uiStocks[i]);
				
				if (!warehousePKSet.containsKey(cwarehouseid)) warehousePKSet.put(cwarehouseid, cwarehouseid);
				if (!stockBatchSet.containsKey(vstockbatch)) stockBatchSet.put(vstockbatch, vstockbatch);
			}
			
			getCheckValue(uiStockSet, warehousePKSet.keySet().toArray(new String[]{}), stockBatchSet.keySet().toArray(new String[]{}));
			
			setData(uiStocks);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setButtonStatusEx();
	}

	

	private void getCheckValue(Hashtable<String, AnalysisReportVO> uiStockSet, String[] warehouses, String[] stockbatchs) {
		String[] checkitemids = getCheckItemId();
		
		Hashtable<String, Integer> checkItemidNo = new Hashtable<String, Integer>();
		for(int i=0;i<checkitemids.length;i++)
			checkItemidNo.put(checkitemids[i], i);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ic_general_h_v.CWAREHOUSEID, ic_general_b_v.VBATCHCODE, qc_checkbill_b2_v.CCHECKITEMID, qc_checkbill_b2_v.CRESULT");
		sql.append(" from qc_checkbill, qc_checkbill_b1, qc_checkbill_b2_v, ic_general_b_v, ic_general_h_v");
		sql.append(" where qc_checkbill.ccheckbillid = qc_checkbill_b1.ccheckbillid");
		sql.append(" and qc_checkbill.ccheckbillid = qc_checkbill_b2_v.ccheckbillid");
		sql.append(" and qc_checkbill_b1.csourcebillrowid = ic_general_b_v.csourcebillbid(+)");
		sql.append(" and ic_general_b_v.cgeneralhid = ic_general_h_v.cgeneralhid(+)");
		sql.append(" and qc_checkbill_b1.csourcebilltypecode = '23' and qc_checkbill_b1.norder = 0");
		sql.append(" and nvl(qc_checkbill.dr, 0) = 0 and nvl(qc_checkbill_b1.dr, 0) = 0");
		sql.append(" and ic_general_b_v.VBATCHCODE in "+getInsqlClause(stockbatchs));
		sql.append(" and ic_general_b_v.CINVENTORYID='"+invDocRef.getRefPK()+"' ");
		sql.append(" and ic_general_h_v.CWAREHOUSEID in "+getInsqlClause(warehouses));
		sql.append(" and qc_checkbill_b2_v.CCHECKITEMID in "+getInsqlClause(checkitemids));
		sql.append(" order by qc_checkbill_b2_v.VSAMPLECODE");
		
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> checkValue = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			for(int i=0;i<checkValue.size();i++){
				String cwarehouseid = checkValue.get(i).get(0).toString();
				String vstockbatch = checkValue.get(i).get(1).toString();
				String checkitemid = checkValue.get(i).get(2).toString();
				UFDouble checkvalue = new UFDouble(checkValue.get(i).get(3).toString());
				
				String key=cwarehouseid+vstockbatch;
			
				if (uiStockSet.containsKey(key)){
					AnalysisReportVO reportVO = uiStockSet.get(key);
					if(!reportVO.haveKey("_CROSS"+checkItemidNo.get(checkitemid)))
						reportVO.setAttributeValue("_CROSS"+checkItemidNo.get(checkitemid), checkvalue);
				}
			}
			processTableLayout(getCheckItemName(), checkitemids);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void processTableLayout(String[] checkitemnames, String[] checkitemids) {
		ReportItem[] reportItems = new ReportItem[checkitemnames.length];
		for(int i=0;i<checkitemnames.length;i++){
			ReportItem item = new ReportItem();
			item.setWidth(80);
			item.setKey("_CROSS"+i);
			item.setName(checkitemnames[i]);
			item.setDataType(IBillItem.DECIMAL);
			item.setDecimalDigits(4);
			reportItems[i] = item;
		}
		ArrayList<ReportItem> finalBodyItems = new ArrayList<ReportItem>();
		finalBodyItems.addAll(Arrays.asList(getReportPanel().getBody_Items()));
		finalBodyItems.addAll(Arrays.asList(reportItems));
		
		Vector<FldgroupVO> groupVOs = new Vector<FldgroupVO>();
		for(int i=0;i<reportItems.length-1;i++){
			FldgroupVO groupVO = new FldgroupVO();
			groupVO.setGroupid(i + 1);
			groupVO.setGroupname("检验项目");
			if(i==0) 
				groupVO.setGrouptype("0");
			else
				groupVO.setGrouptype("2");
			if(i==0)
				groupVO.setItem1(String.valueOf(finalBodyItems.size()-reportItems.length+i));
			else
				groupVO.setItem1("检验项目");
			groupVO.setItem2(String.valueOf(finalBodyItems.size()-reportItems.length+i+1));
			if (i == reportItems.length - 2)
				groupVO.setToplevelflag("Y");
			else
				groupVO.setToplevelflag("N");
			
			groupVOs.add(groupVO);
		}
		
		getReportPanel().setFieldGroup(groupVOs.toArray(new FldgroupVO[]{}));
		getReportPanel().setBody_Items(finalBodyItems.toArray(new ReportItem[]{}));
		
		setReportPanelCellRender(checkitemnames, checkitemids);
		setReportPanelHeader();
	}
	
	private void setReportPanelCellRender(String[] checkitemnames, String[] checkitemids) {
		Hashtable<String, double[]> standardValue = getStandardValue();
		for(int i=0;i<checkitemnames.length;i++) {
			double[] itemStandardValue = standardValue.get(checkitemids[i]);
			BillItem billItem = reportpanel.getBodyItem("_CROSS"+i);
			BillTableValueRangeRender render = new BillTableValueRangeRender(billItem, itemStandardValue[0], itemStandardValue[1]);
			reportpanel.getBillTable().getColumn(checkitemnames[i]).setCellRenderer(render);
		}
	}

	private String getInsqlClause(String[] pks){
		StringBuffer sql = new StringBuffer();
		sql.append("(");
		for(int i=0;i<pks.length;i++)
			sql.append("'"+pks[i]+"',");
		sql.delete(sql.length()-1, sql.length());
		sql.append(")");
		
		return sql.toString();
	}
	

	private String[] getCheckItemId() {
		ArrayList<String> checkItems = new ArrayList<String>();
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			if (m_billCardPanel.getBillModel().getValueAt(i, "bselect").equals(true)){
				checkItems.add(m_billCardPanel.getBillModel().getValueAt(i, "ccheckitemid").toString());
			}
		}
		
		return checkItems.toArray(new String[]{});
	}
	
	private String[] getCheckItemName() {
		ArrayList<String> checkItems = new ArrayList<String>();
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			if (m_billCardPanel.getBillModel().getValueAt(i, "bselect").equals(true)){
				checkItems.add(m_billCardPanel.getBillModel().getValueAt(i, "vcheckitem").toString());
			}
		}
		
		return checkItems.toArray(new String[]{});
	}

	@Override
	public String getNodeCode() {
		return GZCGConstant.MIXTURETOOLFUNCODE.getValue();
	}

	@Override
	public void setReportData(ConditionVO[] voCondition) {
		
	}

	@Override
	public void setTotalRowShow() {
	}
	
	@Override
	public boolean isConPanelShow() {
		return true;
	}
	
	@Override
	protected UIPanel getConditionPanel() {
		if (conditionpanel==null){			
			UIPanel conditionPanel = super.getConditionPanel();
			conditionPanel.setMaximumSize(null);
			conditionPanel.setPreferredSize(null);
			conditionPanel.setLayout(new BoxLayout(conditionPanel, BoxLayout.Y_AXIS));
			UIPanel panel1 = new UIPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
			
			invDocRef = new UIRefPane("存货档案");
			panel1.add(new UILabel("存货档案"));
			panel1.add(invDocRef);
			
			invDocRef.addValueChangedListener(new ValueChangedListener() {	
				public void valueChanged(ValueChangedEvent event) {
					updateCheckStandardCtrl();
				}
			});
			
			invSpec = new UITextField();
			panel1.add(new UILabel("规格"));
			panel1.add(invSpec);
			invSpec.setEditable(false);
			invSpec.setColumns(10);
			
			checkStandardRef = new UIRefPane();
			checkStandardRef.setRefModel(new CheckstandardDef(ClientEnvironment.getInstance().getCorporation().getPrimaryKey()));
			panel1.add(new UILabel("检测标准"));
			panel1.add(checkStandardRef);
			
			checkStandardRef.addValueChangedListener(new ValueChangedListener() {	
				public void valueChanged(ValueChangedEvent event) {
					updateCheckStandardGrid();
				}
			});
			
			mixtureAmountText = new UITextField(10);
			mixtureAmountText.setAllowAlphabetic(false);
			panel1.add(new UILabel("混料数量"));
			panel1.add(mixtureAmountText);
			
			conditionpanel.add(panel1);
		}
		
		return conditionpanel;
	}
	
	private void updateCheckStandardCtrl() {
		reportpanel.getBillModel().clearBodyData();
		setButtonStatusEx();
		
		String pk_invmandoc = invDocRef.getRefPK();
		if (pk_invmandoc==null || pk_invmandoc.length()!=20) {
			checkStandardRef.setPK(null);
			m_billCardPanel.getBillModel().clearBodyData();
			
			return;
		}

		StringBuffer sql = new StringBuffer();
		sql.append("select qc_invrelate.ccheckstandardid, nvl(bd_invbasdoc.invspec,'-') from qc_invrelate, bd_invbasdoc, bd_invmandoc where bd_invbasdoc.pk_invbasdoc=bd_invmandoc.pk_invbasdoc and bd_invmandoc.pk_invmandoc=qc_invrelate.cmangid and qc_invrelate.bdefault='Y' and qc_invrelate.cmangid='");
		sql.append(invDocRef.getRefPK()+"'");
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> checkStandard = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			if (checkStandard!=null && checkStandard.size()>0) {
				invSpec.setText(checkStandard.get(0).get(1).toString());
				
				checkStandardRef.setPK(checkStandard.get(0).get(0).toString());
				updateCheckStandardGrid();
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void updateCheckStandardGrid() {
		reportpanel.getBillModel().clearBodyData();
		setButtonStatusEx();
		
		String checkStandardid = checkStandardRef.getRefPK();
		if (checkStandardid==null || checkStandardid.length()!=20) {
			m_billCardPanel.getBillModel().clearBodyData();
			return;
		}
		
		try {
			CheckstandardItemVO[] standardItems = CheckstandardHelper.queryCheckstandardItems(checkStandardid);
			AnalysisReportVO[] uiCheckItems = new AnalysisReportVO[standardItems.length];
			for(int i=0;i<standardItems.length;i++){
				uiCheckItems[i] = new AnalysisReportVO();
				uiCheckItems[i].setAttributeValue("bselect", true);
				uiCheckItems[i].setAttributeValue("ccheckitemid", standardItems[i].getCcheckitemid());
				String standardValue = standardItems[i].getCstandardvalue();
				int start = standardValue.indexOf("合格:")+3;
				int end = standardValue.indexOf("}", start);
				standardValue = standardValue.substring(start, end);
				uiCheckItems[i].setAttributeValue("vstandardvalue", standardValue);
				uiCheckItems[i].setAttributeValue("vrequirevalue", standardValue);
			}
			m_billCardPanel.getBillModel().setBodyDataVO(uiCheckItems);
			m_billCardPanel.getBillModel().execLoadFormula();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	protected void initialize() {
		super.initialize();
		add(getLefrGridPanel(), "West");
		setButtonStatusEx();
	}

	private UIPanel getLefrGridPanel() {
		if (leftgridpanel==null){
			leftgridpanel = new UIPanel();
			leftgridpanel.setName("UIPanel");
			leftgridpanel.setLayout(new java.awt.BorderLayout(5, 5));
			leftgridpanel.setMaximumSize(new Dimension(350, 400));
			leftgridpanel.setPreferredSize(new Dimension(350, 400));
			
			m_billCardPanel = new BillCardPanel();
			m_billCardPanel.setName("混料标准");
			m_billCardPanel.setEnabled(true);
			m_billCardPanel.loadTemplet(getNodeCode(), getBusitype(), ClientEnvironment.getInstance().getUser().getPrimaryKey()
					, ClientEnvironment.getInstance().getCorporation().getPrimaryKey());
			
			checkItemheadChecker = new CheckBoxRenderer();
			m_billCardPanel.getBillTable().getColumn("选择").setMaxWidth(20);
			m_billCardPanel.getBillTable().getColumn("选择").setHeaderRenderer(checkItemheadChecker);
			
			m_billCardPanel.getBillTable().getTableHeader().addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e){
					if(m_billCardPanel.getBillTable().getColumnModel().getColumnIndexAtX(e.getX())==0){//如果点击的是第0列，即checkbox这一列
						boolean b = !checkItemheadChecker.isSelected();
						checkItemheadChecker.setSelected(b);
						m_billCardPanel.getBillTable().getTableHeader().repaint();
						for(int i=0;i<m_billCardPanel.getBillTable().getRowCount();i++){
							m_billCardPanel.getBillTable().getModel().setValueAt(b, i, 0);//把这一列都设成和表头一样
						}
					}
				}
			});
			
			leftgridpanel.add(m_billCardPanel);
		}
		
		return leftgridpanel;
	}
	
	class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
		private static final long serialVersionUID = 1L;

		public CheckBoxRenderer() {
			this.setBorderPainted(false);
		}

		public Component getTableCellRendererComponent(JTable arg0,
				Object arg1, boolean arg2, boolean arg3, int arg4, int arg5) {
			setHorizontalAlignment(JLabel.CENTER);
			return this;
		}
	}
	
	private void setButtonStatusEx(){
		if (invDocRef.getRefPK()!=null && invDocRef.getRefPK().length()==20)
			bnStock.setEnabled(true);
		else
			bnStock.setEnabled(false);
		
		if (checkStandardRef.getRefPK()!=null && checkStandardRef.getRefPK().length()==20 
				&& getReportPanel().getBillModel().getRowCount()>0)
			bnCompute.setEnabled(true);
		else
			bnCompute.setEnabled(false);
		
		updateButton(bnStock);
		updateButton(bnCompute);
		
		if (reportpanel.getBillModel().getRowCount()!=0)
			setButtonStatus("QUERY");
		else
			setButtonStatus("INIT");
		updateUI();
	}
	
	@Override
	public ReportPanel getReportPanel() {
		if (reportpanel ==null) {
			super.getReportPanel();
			stockChecker = new CheckBoxRenderer();
			setReportPanelHeader();
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
		reportpanel.getBillTable().getColumn("选择").setMaxWidth(20);
		reportpanel.getBillTable().getColumn("选择").setHeaderRenderer(stockChecker);
		
		reportpanel.getBillTable().getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				if(reportpanel.getBillTable().getColumnModel().getColumnIndexAtX(e.getX())==0){//如果点击的是第0列，即checkbox这一列
					boolean b = !stockChecker.isSelected();
					stockChecker.setSelected(b);
					reportpanel.getBillTable().getTableHeader().repaint();
					for(int i=0;i<reportpanel.getBillTable().getRowCount();i++){
						reportpanel.getBillTable().getModel().setValueAt(b, i, 0);//把这一列都设成和表头一样
					}
				}
			}
		});
	}
}
