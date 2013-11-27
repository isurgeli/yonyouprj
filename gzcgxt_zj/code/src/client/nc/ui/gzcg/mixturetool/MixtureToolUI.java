package nc.ui.gzcg.mixturetool;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.BoxLayout;

import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.optimization.PointValuePair;
import org.apache.commons.math3.optimization.linear.LinearConstraint;
import org.apache.commons.math3.optimization.linear.LinearObjectiveFunction;
import org.apache.commons.math3.optimization.linear.NoFeasibleSolutionException;
import org.apache.commons.math3.optimization.linear.Relationship;
import org.apache.commons.math3.optimization.linear.SimplexSolver;

import nc.bs.framework.common.NCLocator;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.BillTableValueRangeRender;
import nc.ui.gzcg.pub.BillColumnHelper;
import nc.ui.gzcg.pub.PrintDataSource;
import nc.ui.gzcg.pub.ReportUIEx;
import nc.ui.gzcg.pub.TableCellValueRangeRender;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UILabel;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.beans.UIRefPane;
import nc.ui.pub.beans.UITextField;
import nc.ui.pub.beans.ValueChangedEvent;
import nc.ui.pub.beans.ValueChangedListener;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillEditListener;
import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillModelCellEditableController;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.print.IDataSource;
import nc.ui.pub.print.PrintEntry;
import nc.ui.pub.report.ReportItem;
import nc.ui.qc.standard.CheckstandardDef;
import nc.ui.qc.standard.CheckstandardHelper;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.cquery.FldgroupVO;
import nc.vo.pub.lang.UFDouble;
import nc.vo.pub.query.ConditionVO;
import nc.vo.qc.query.CheckstandardItemVO;

@SuppressWarnings({ "restriction", "deprecation" })
public class MixtureToolUI extends ReportUIEx implements BillEditListener{

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
	private BillColumnHelper leftColHelper;
	
	protected ButtonObject bnStock; 
	protected ButtonObject bnClearStock; 
	protected ButtonObject bnCompute;
	protected ButtonObject bnTry;
	private BillColumnHelper reportColHelper;
	private ReportItem[] initBodyItems;
	private UITextField invSpec;
	private UITextField invUnit;
	private UIRefPane srcInvDocRef;
	private UITextField mixtureUnitText; 
	
	private ArrayList<String> m_checkItemNames;
	private ArrayList<String> m_checkItemIds;
	private Hashtable<String, Integer> m_checkItemIdNo;
	
	TableCellValueRangeRender leftGridCellRender;
	private UITextField newBatchNoCtrl;

	@Override
	public String getBusitype() {
		return null;
	}

	@Override
	public ButtonObject[] getButtons() {
		if (bnStock==null && bnCompute==null && bnTry==null && bnClearStock==null) {
			bnStock = new ButtonObject("获取库存", "获取库存", 2, "获取库存"); 
			bnCompute = new ButtonObject("标准约束计算", "标准约束计算", 2, "标准约束计算");
			bnTry = new ButtonObject("全部使用计算", "全部使用计算", 2, "全部使用计算");
			bnClearStock = new ButtonObject("清除库存", "清除库存", 2, "清除库存");
		}
		return new ButtonObject[]{bnPrint, bnModelPrint, bnPreview, bnOut, bnStock, bnClearStock, bnTry, bnCompute};
	}
	
	@Override
	public void onButtonClicked(ButtonObject bo) {
		super.onButtonClicked(bo);
		try{
			if (bo == bnStock)
				onGetStock();
			else if (bo == bnCompute)
				onMixtureCompute();
			else if (bo == bnTry)
				onTryCompute();
			else if (bo == bnClearStock)
				onClearStock();
		}catch(BusinessException ex){
			MessageDialog.showWarningDlg(this, "错误", ex.getMessage());
		}
	}

	private void onClearStock() {
		ArrayList<CircularlyAccessibleValueObject> retList = new ArrayList<CircularlyAccessibleValueObject>();
		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (reportpanel.getBillModel().getValueAt(row, "bselect").equals(true)){
				retList.add(report[row]);
			}
		}
		setData(retList.toArray(new CircularlyAccessibleValueObject[]{}));
		setButtonStatusEx();
	}

	private void onManuCompute() throws BusinessException {
		String[] checkitemids = getCheckItemId();
		Hashtable<String, Integer> checkItemidNo = getCheckItemIdNo();
		
		ArrayList<Integer> joinStockRowNo = new ArrayList<Integer>();
	
		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (haveCheckValue(row, checkitemids, checkItemidNo)){
				joinStockRowNo.add(row);
			}
		}
		UFDouble totalUnitUse = UFDouble.ZERO_DBL;
    	for (int i=0;i<joinStockRowNo.size();i++){
			UFDouble unitUse = new UFDouble(reportpanel.getBillModel().getValueAt(joinStockRowNo.get(i), "nunitusenum").toString());
			totalUnitUse = totalUnitUse.add(unitUse);
		}
    	UFDouble unitCount = getUnitCount();
    	UFDouble[] ret = new UFDouble[joinStockRowNo.size()];
    	for (int i=0;i<joinStockRowNo.size();i++){
    		UFDouble stockAmount = new UFDouble(reportpanel.getBillModel().getValueAt(joinStockRowNo.get(i), "nstocknum").toString());
    		UFDouble unitUse = new UFDouble(reportpanel.getBillModel().getValueAt(joinStockRowNo.get(i), "nunitusenum").toString());
    		UFDouble remainNum = stockAmount.sub(unitCount.multiply(unitUse)).setScale(-2, UFDouble.ROUND_DOWN);
    		if (remainNum.abs().doubleValue() < 1){
    			if (reportpanel.getBillModel().getItemByKey("nusenum")!=null)
    				reportpanel.getBillModel().setValueAt(stockAmount, joinStockRowNo.get(i), "nusenum");
    			reportpanel.getBillModel().setValueAt(UFDouble.ZERO_DBL, joinStockRowNo.get(i), "nremainnum");
    		}else{
    			if (reportpanel.getBillModel().getItemByKey("nusenum")!=null)
    				reportpanel.getBillModel().setValueAt(unitCount.multiply(unitUse).setScale(-2, UFDouble.ROUND_DOWN), joinStockRowNo.get(i), "nusenum");
    			reportpanel.getBillModel().setValueAt(remainNum, joinStockRowNo.get(i), "nremainnum");
    		}
    		
			ret[i]=unitUse.div(totalUnitUse);
		}
    	mixtureAmountText.setText(totalUnitUse.setScale(-2, UFDouble.ROUND_UP).toString());
    	setComputeCheckValue(ret, joinStockRowNo, checkItemidNo);
	}
	
	private void onTryCompute() throws BusinessException{
		UFDouble unitCount=null;
		
		unitCount = getUnitCount();
		
		String[] checkitemids = getCheckItemId();
		Hashtable<String, Integer> checkItemidNo = getCheckItemIdNo();

		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (haveCheckValue(row, checkitemids, checkItemidNo)){
				UFDouble maxUse = new UFDouble(getMaxUseStockNum(row));
				if (reportpanel.getBillModel().getValueAt(row, "vinvdocname").toString().equals(invDocRef.getRefName()))
					reportpanel.getBillModel().setValueAt(maxUse.div(unitCount), row, "nunitusenum");
				else
					reportpanel.getBillModel().setValueAt(0, row, "nunitusenum");
			}
		}
				
		onManuCompute();
	}

	private void onGetStock() {
		StockSelectDlg dlg = new StockSelectDlg(this, srcInvDocRef.getRefPK(), getCheckItemId(), getCheckItemName(), getCheckItemIdNo());
		dlg.showModal();
		if (dlg.getResult()==UIDialog.ID_OK){
			processTableLayout(getReportPanel(), initBodyItems, getCheckItemName(), getCheckItemId());
			getCheckItemIdNo();
			setReportPanelHeader();
			ArrayList<CircularlyAccessibleValueObject> allData = new ArrayList<CircularlyAccessibleValueObject>();
			if (report!=null)
				allData.addAll(Arrays.asList(report));
			allData.addAll(Arrays.asList(dlg.retData));
			setData(allData.toArray(new CircularlyAccessibleValueObject[]{}));
			setButtonStatusEx();
		}
	}

	private void onMixtureCompute() throws BusinessException {
		boolean computeMax = false;
		
		UFDouble unitCount = getUnitCount();
		UFDouble unitAmount = getAmountCount();
		
		if (unitAmount.doubleValue()==0)
			computeMax = true;
		
		Hashtable<String, double[]> standardValue = getManuStandardValueRange();
		Hashtable<String, String> needComputeCheckItemId = new Hashtable<String, String>();
		String[] checkitemids = getCheckItemId();
		Hashtable<String, Integer> checkItemidNo = getCheckItemIdNo();
		
		ArrayList<Integer> joinStockRowNo = new ArrayList<Integer>();
		
		int firstRowNoIdx = -1;
		String timeValue = null;
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
				if (reportpanel.getBillModel().getValueAt(row, "vinvdocname").toString().equals(invDocRef.getRefName()) && (
						firstRowNoIdx==-1 || reportpanel.getBillModel().getValueAt(row, "vbatchcode").toString().compareTo(timeValue)==-1)){
					firstRowNoIdx = joinStockRowNo.size()-1;
					timeValue = reportpanel.getBillModel().getValueAt(row, "vbatchcode").toString(); //TODO 批次号或日期
				}
			}
		}
		ArrayList<UFDouble> retList = new ArrayList<UFDouble>();
		UFDouble retAmount = UFDouble.ZERO_DBL;
		if (computeMax){
			retAmount = doMaxComputeWork(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, unitCount, unitAmount, firstRowNoIdx, retList);
		}else{
			retAmount = doConstComputeWork(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, unitCount, unitAmount, firstRowNoIdx, retList);
		}
		if (retAmount.doubleValue() == 0){
			MessageDialog.showWarningDlg(this, "警告", "无法根据设定条件进行混料。");
		}else{
			UFDouble[] ret = retList.toArray(new UFDouble[]{});
			
			for (int i=0;i<joinStockRowNo.size();i++){
				UFDouble stockAmount = new UFDouble(reportpanel.getBillModel().getValueAt(joinStockRowNo.get(i), "nstocknum").toString());
				UFDouble remainNum = stockAmount.sub(unitCount.multiply(retAmount.multiply(ret[i]).setScale(-2, UFDouble.ROUND_DOWN)));
				
				if (remainNum.abs().doubleValue() < 1){
					reportpanel.getBillModel().setValueAt(stockAmount.div(unitCount).setScale(-2, UFDouble.ROUND_DOWN), joinStockRowNo.get(i), "nunitusenum");
					reportpanel.getBillModel().setValueAt(stockAmount, joinStockRowNo.get(i), "nusenum");
					reportpanel.getBillModel().setValueAt(UFDouble.ZERO_DBL, joinStockRowNo.get(i), "nremainnum");
				}else{
					reportpanel.getBillModel().setValueAt(retAmount.multiply(ret[i]).setScale(-2, UFDouble.ROUND_DOWN), joinStockRowNo.get(i), "nunitusenum");
					reportpanel.getBillModel().setValueAt(unitCount.multiply(retAmount.multiply(ret[i]).setScale(-2, UFDouble.ROUND_DOWN)), joinStockRowNo.get(i), "nusenum");
					reportpanel.getBillModel().setValueAt(remainNum, joinStockRowNo.get(i), "nremainnum");
				}
			}
			
			setComputeCheckValue(ret, joinStockRowNo, checkItemidNo);
			
			if (computeMax){
				mixtureAmountText.setText(retAmount.setScale(-2, UFDouble.ROUND_UP).toString());
			}
		}
	}

	private UFDouble getUnitCount() throws BusinessException {
		UFDouble unitCount;
		
		if (mixtureUnitText.getText()==null || mixtureUnitText.getText().length()==0){
			throw new BusinessException("请设置混料拌数。");
		}
		
		try{
			unitCount = new UFDouble(mixtureUnitText.getText());
		} catch (Exception ex) {
			throw new BusinessException("请设置正确的混料拌数。");
		}
		if (unitCount.doubleValue()==0){
			throw new BusinessException("请设置正确的混料拌数。");
		}
		return unitCount;
	}
	
	private UFDouble getAmountCount() throws BusinessException {
		UFDouble unitAmount;
		
		if (mixtureAmountText.getText()==null || mixtureAmountText.getText().length()==0){
			unitAmount = new UFDouble(0);
		}else{
			try{
				unitAmount = new UFDouble(mixtureAmountText.getText());
			} catch (Exception ex) {
				throw new BusinessException("请设置正确的单位数量。");
			}
			if (unitAmount.doubleValue()==0){
				throw new BusinessException("请设置正确的单位数量。");
			}
		}
		
		return unitAmount;
	}
	
	private void setComputeCheckValue(UFDouble[] ret, ArrayList<Integer> joinStockRowNo, Hashtable<String, Integer> checkItemidNo){
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			String checkItemid = m_billCardPanel.getBillModel().getValueAt(i, "ccheckitemid").toString();
			if (checkItemidNo.containsKey(checkItemid)){	
				UFDouble finalCheckValue = UFDouble.ZERO_DBL;
				for(int j=0;j<ret.length;j++){
					double realCheckValue=Double.parseDouble(reportpanel.getBillModel().getValueAt
							(joinStockRowNo.get(j), "_CROSS"+checkItemidNo.get(checkItemid)).toString());
					finalCheckValue = finalCheckValue.add(ret[j].multiply(realCheckValue));
				}
				m_billCardPanel.getBillModel().setValueAt(finalCheckValue, i, "vcomputevalue");
			}
		}
		m_billCardPanel.getBillTable().repaint();
	}
	
	private UFDouble doConstComputeWork(ArrayList<Integer> joinStockRowNo, Hashtable<String, String> needComputeCheckItemId,
			Hashtable<String, double[]> standardValue, Hashtable<String, Integer> checkItemidNo, UFDouble unitCount, 
			UFDouble unitAmount, int firstRowNoIdx, ArrayList<UFDouble> retList){
		return doComputeWork(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, unitCount, unitAmount, firstRowNoIdx, retList, false);
	}
	
	private void prepareComputeWork(ArrayList<Integer> joinStockRowNo,
			Hashtable<String, String> needComputeCheckItemId,
			Hashtable<String, double[]> standardValue,
			Hashtable<String, Integer> checkItemidNo,
			ArrayList<UFDouble> stockAmounts,
			ArrayList<LinearConstraint> constraints) {
		for (int i=0;i<joinStockRowNo.size();i++)
	      	stockAmounts.add(getMaxUseStockNum(joinStockRowNo.get(i)));
		
		ArrayList<LinearConstraint> checkConstraints = getCheckValueConstraints(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, stockAmounts);
		ArrayList<LinearConstraint> stockConstraints = getStockNumConstraints(joinStockRowNo);
		
		constraints.addAll(checkConstraints);
    	constraints.addAll(stockConstraints);
	}
	
	private UFDouble doComputeWork(ArrayList<Integer> joinStockRowNo, Hashtable<String, String> needComputeCheckItemId,
			Hashtable<String, double[]> standardValue, Hashtable<String, Integer> checkItemidNo, UFDouble unitCount, 
			UFDouble unitAmount, int firstRowNoIdx, ArrayList<UFDouble> retList, boolean max){
		
		ArrayList<UFDouble> stockAmounts = new ArrayList<UFDouble>();
		ArrayList<LinearConstraint> constraints = new ArrayList<LinearConstraint>();
		LinearObjectiveFunction f = null;
		
		prepareComputeWork(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, stockAmounts, constraints);
		if (!max){
			double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 1.0);
			for (int i=0;i<joinStockRowNo.size();i++)
				constraintCoefficients[i] = stockAmounts.get(i).div(unitCount).div(unitAmount).doubleValue();
			
			constraints.add(new LinearConstraint(constraintCoefficients, Relationship.EQ, 1));
			
			double[] objectiveFunctionCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
			objectiveFunctionCoefficients[firstRowNoIdx] = 1.0;
			f = new LinearObjectiveFunction(objectiveFunctionCoefficients, 0.0);
		}else{
			double[] objectiveFunctionCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
			for (int i=0;i<joinStockRowNo.size();i++)
				objectiveFunctionCoefficients[i] = stockAmounts.get(i).doubleValue();
			f = new LinearObjectiveFunction(objectiveFunctionCoefficients, 0.0);
		}
                
	    PointValuePair solution = null;
        try{
        	solution = new SimplexSolver().optimize(f, constraints, GoalType.MAXIMIZE, true);
        }
        catch(NoFeasibleSolutionException ex){
        	return UFDouble.ZERO_DBL;
        }

		if (max){
			unitAmount = UFDouble.ZERO_DBL;
			for (int i = 0; i < solution.getPoint().length; i++)
				unitAmount = unitAmount.add(stockAmounts.get(i).multiply(solution.getPoint()[i]));
			unitAmount = unitAmount.div(unitCount);
		}
		
		retList.clear();
		for (int i = 0; i < solution.getPoint().length; i++)
			retList.add(stockAmounts.get(i).multiply(solution.getPoint()[i]).div(unitAmount.multiply(unitCount)));
		return unitAmount;
	}
	
	private UFDouble doMaxComputeWork(ArrayList<Integer> joinStockRowNo, Hashtable<String, String> needComputeCheckItemId,
			Hashtable<String, double[]> standardValue, Hashtable<String, Integer> checkItemidNo, UFDouble unitCount, 
			UFDouble unitAmount, int firstRowNoIdx, ArrayList<UFDouble> retList){
		
		return doComputeWork(joinStockRowNo, needComputeCheckItemId, standardValue, checkItemidNo, unitCount, unitAmount, firstRowNoIdx, retList, true);
	}

	private ArrayList<LinearConstraint> getCheckValueConstraints(ArrayList<Integer> joinStockRowNo,
			Hashtable<String, String> needComputeCheckItemId,
			Hashtable<String, double[]> standardValue,
			Hashtable<String, Integer> checkItemidNo,
			ArrayList<UFDouble> stockAmounts) {
		ArrayList<LinearConstraint> regConstraints = new ArrayList<LinearConstraint>();
		
		for(String checkItemId : needComputeCheckItemId.keySet()){
        	double[] itemStandardValue = standardValue.get(checkItemId);
        	double[] downConstraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
        	double[] upConstraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
    		for (int i=0;i<joinStockRowNo.size();i++){
    			UFDouble checkValue = new UFDouble(reportpanel.getBillModel().getValueAt
						(joinStockRowNo.get(i), "_CROSS"+checkItemidNo.get(checkItemId)).toString());
    			downConstraintCoefficients[i]=checkValue.sub(itemStandardValue[0]).multiply(stockAmounts.get(i)).toDouble();
    			upConstraintCoefficients[i]=checkValue.sub(itemStandardValue[1]).multiply(stockAmounts.get(i)).toDouble();
    		}
        	if (itemStandardValue[0]>epsilon){
        		regConstraints.add(new LinearConstraint(downConstraintCoefficients, Relationship.GEQ, 0));
        	}
        	if (itemStandardValue[0]<1-epsilon){
        		regConstraints.add(new LinearConstraint(upConstraintCoefficients, Relationship.LEQ, 0));
        	}
        }
	    
	    return regConstraints;
	}
	
//	private ArrayList<LinearConstraint> getRegConstraints(ArrayList<Integer> joinStockRowNo,
//			Hashtable<String, String> needComputeCheckItemId,
//			Hashtable<String, double[]> standardValue,
//			Hashtable<String, Integer> checkItemidNo) {
//		ArrayList<LinearConstraint> regConstraints = new ArrayList<LinearConstraint>();
//		
//		for(String checkItemId : needComputeCheckItemId.keySet()){
//        	double[] itemStandardValue = standardValue.get(checkItemId);
//        	double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
//    		for (int i=0;i<joinStockRowNo.size();i++){
//    			constraintCoefficients[i]=Double.parseDouble(reportpanel.getBillModel().getValueAt
//						(joinStockRowNo.get(i), "_CROSS"+checkItemidNo.get(checkItemId)).toString());
//    		}
//        	if (itemStandardValue[0]>epsilon){
//        		regConstraints.add(new LinearConstraint(constraintCoefficients.clone(), Relationship.GEQ, itemStandardValue[0]));
//        	}
//        	if (itemStandardValue[0]<1-epsilon){
//        		regConstraints.add(new LinearConstraint(constraintCoefficients.clone(), Relationship.LEQ, itemStandardValue[1]));
//        	}
//        }
//	    
//	    {
//	       	double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 1.0);
//	       	regConstraints.add(new LinearConstraint(constraintCoefficients, Relationship.EQ, 1));
//	    }
//	    
//	    return regConstraints;
//	}

//	private ArrayList<LinearConstraint> getStockNumConstraints(ArrayList<Integer> joinStockRowNo, UFDouble mixAmount) {
//		ArrayList<LinearConstraint> chgConstraints = new ArrayList<LinearConstraint>();
//		for (int i=0;i<joinStockRowNo.size();i++){
//	      	UFDouble stockAmount = UFDouble.ZERO_DBL;
//	      	stockAmount = getMaxUseStockNum(joinStockRowNo.get(i));
//	       	if (stockAmount.compareTo(mixAmount) < 0){
//	       		double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
//	       		constraintCoefficients[i] = 1.0;
//	       		chgConstraints.add(new LinearConstraint(constraintCoefficients, Relationship.LEQ, stockAmount.div(mixAmount).doubleValue()));
//	       	}
//	    }
//		return chgConstraints;
//	}
	
	private ArrayList<LinearConstraint> getStockNumConstraints(ArrayList<Integer> joinStockRowNo) {
		ArrayList<LinearConstraint> chgConstraints = new ArrayList<LinearConstraint>();
		
		for (int i=0;i<joinStockRowNo.size();i++){
    		double[] constraintCoefficients = getDoubleArray(joinStockRowNo.size(), 0.0);
       		constraintCoefficients[i] = 1.0;
       		chgConstraints.add(new LinearConstraint(constraintCoefficients, Relationship.LEQ, 1));
		}
		
		return chgConstraints;
	}

	private UFDouble getMaxUseStockNum(int i) {
		UFDouble stockAmount;
		if (reportpanel.getBillModel().getItemByKey("nmaxusenum") != null)
			stockAmount = new UFDouble(reportpanel.getBillModel().getValueAt(i, "nmaxusenum").toString());
		else
			stockAmount = new UFDouble(reportpanel.getBillModel().getValueAt(i, "nstocknum").toString());
		
		return stockAmount;
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
	
	private Hashtable<String, double[]> getSysStandardValueRange() {
		return getStandardValueRange("vstandardvalue");
	}
	
	private Hashtable<String, double[]> getManuStandardValueRange() {
		return getStandardValueRange("vrequirevalue");
	}
	
	private Hashtable<String, double[]> getStandardValueRange(String vlauekey) {
		Hashtable<String, double[]> standardValue = new Hashtable<String, double[]>();
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			String checkItems = m_billCardPanel.getBillModel()
					.getValueAt(i, "ccheckitemid").toString();
			String requirevalue = m_billCardPanel.getBillModel()
					.getValueAt(i, vlauekey).toString();

			String[] sec = requirevalue.trim().split(",");
			if (sec.length != 2) {
				MessageDialog.showWarningDlg(this, "错误", "请设置正确的检测标准值。");
				return null;
			}
			double[] value = new double[2];
			try {
				value[0] = Double.parseDouble(sec[0].substring(1));
				value[1] = Double.parseDouble(sec[1].substring(0,
						sec[1].length() - 1));
			} catch (NumberFormatException ex) {
				MessageDialog.showWarningDlg(this, "错误", "请设置正确的检测标准值。");
				return null;
			}

			if (sec[0].charAt(0) == '(')
				value[0] += epsilon;
			if (sec[1].charAt(sec[1].length() - 1) == ')')
				value[0] -= epsilon;

			standardValue.put(checkItems, value);
		}
		
		return standardValue;
	}
	
	public void processTableLayout(ReportPanel rp, ReportItem[] baseItems, String[] checkitemnames, String[] checkitemids) {
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
		finalBodyItems.addAll(Arrays.asList(baseItems));
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
		
		rp.setFieldGroup(groupVOs.toArray(new FldgroupVO[]{}));
		rp.setBody_Items(finalBodyItems.toArray(new ReportItem[]{}));
		
		setReportPanelCellRender(rp, checkitemnames, checkitemids);
	}
	
	private void setReportPanelCellRender(ReportPanel rp, String[] checkitemnames, String[] checkitemids) {
		Hashtable<String, double[]> standardValue = getManuStandardValueRange();
		for(int i=0;i<checkitemnames.length;i++) {
			double[] itemStandardValue = standardValue.get(checkitemids[i]);
			BillItem billItem = rp.getBodyItem("_CROSS"+i);
			BillTableValueRangeRender render = new BillTableValueRangeRender(billItem, itemStandardValue[0], itemStandardValue[1]);
			rp.getBillTable().getColumn(checkitemnames[i]).setCellRenderer(render);
		}
	}
	
	private Hashtable<String, Integer> getCheckItemIdNo(){
		if (m_checkItemIdNo.size()==0){
			String[] checkitemids = getCheckItemId();
			
			for(int i=0;i<checkitemids.length;i++)
				m_checkItemIdNo.put(checkitemids[i], i);
		}
		
		return m_checkItemIdNo;
	}

	private String[] getCheckItemId() {
		if (m_checkItemIds.size()==0){
			for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
				if (m_billCardPanel.getBillModel().getValueAt(i, "bselect").equals(true)){
					m_checkItemIds.add(m_billCardPanel.getBillModel().getValueAt(i, "ccheckitemid").toString());
				}
			}
		}
		
		return m_checkItemIds.toArray(new String[]{});
	}
	
	private String[] getCheckItemName() {
		if (m_checkItemNames.size()==0){
			for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
				if (m_billCardPanel.getBillModel().getValueAt(i, "bselect").equals(true)){
					m_checkItemNames.add(m_billCardPanel.getBillModel().getValueAt(i, "vcheckitem").toString());
				}
			}
		}
		
		return m_checkItemNames.toArray(new String[]{});
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
			//UIPanel panel2 = new UIPanel(new FlowLayout(FlowLayout.LEFT, 5, 2));
			
			invDocRef = new UIRefPane("存货档案");
			panel1.add(new UILabel("混料存货"));
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
			
			invUnit = new UITextField();
			panel1.add(new UILabel("单位"));
			panel1.add(invUnit);
			invUnit.setEditable(false);
			invUnit.setColumns(10);
			
			checkStandardRef = new UIRefPane();
			checkStandardRef.setRefModel(new CheckstandardDef(ClientEnvironment.getInstance().getCorporation().getPrimaryKey()));
			panel1.add(new UILabel("检测标准"));
			panel1.add(checkStandardRef);
			
			checkStandardRef.addValueChangedListener(new ValueChangedListener() {	
				public void valueChanged(ValueChangedEvent event) {
					updateCheckStandardGrid();
				}
			});
			
			srcInvDocRef = new UIRefPane("存货档案");
			panel1.add(new UILabel("原料存货"));
			panel1.add(srcInvDocRef);
			
			mixtureUnitText = new UITextField(10);
			mixtureUnitText.setAllowAlphabetic(false);
			panel1.add(new UILabel("拌数"));
			panel1.add(mixtureUnitText);
			
			mixtureAmountText = new UITextField(10);
			mixtureAmountText.setAllowAlphabetic(false);
			panel1.add(new UILabel("单位数量"));
			panel1.add(mixtureAmountText);
			
			newBatchNoCtrl = new UITextField(10);
			panel1.add(new UILabel("混后批号"));
			panel1.add(newBatchNoCtrl);
			
			conditionpanel.add(panel1);
			//conditionpanel.add(panel2);
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
		srcInvDocRef.setPK(invDocRef.getRefPK());
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
		report = null;
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
			
			BillItem billItem = m_billCardPanel.getBodyItem("vcomputevalue");
			leftGridCellRender = new TableCellValueRangeRender(billItem, new double[0], new double[0]);
			m_billCardPanel.getBillTable().getColumn("混料结果").setCellRenderer(leftGridCellRender);
			setLeftGridCellRenderValue();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void setLeftGridCellRenderValue() {
		Hashtable<String, double[]> standardValue = getManuStandardValueRange();
		
		double[] min = new double[m_billCardPanel.getBillModel().getRowCount()];
		double[] max = new double[m_billCardPanel.getBillModel().getRowCount()];
		
		for (int i=0;i<m_billCardPanel.getBillModel().getRowCount();i++){
			String itemKey = m_billCardPanel.getBillModel().getValueAt(i, "ccheckitemid").toString();
			min[i] = standardValue.get(itemKey)[0];
			max[i] = standardValue.get(itemKey)[1];
		}
		
		leftGridCellRender.setMin(min);
		leftGridCellRender.setMax(max);
		
		m_billCardPanel.getBillTable().repaint();
	}

	@Override
	protected void initialize() {
		m_checkItemNames = new ArrayList<String>();
		m_checkItemIds = new ArrayList<String>();
		m_checkItemIdNo = new Hashtable<String, Integer>();
		
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
			
			m_billCardPanel.addEditListener(this);
			
			MouseListener[] listeners = m_billCardPanel.getBillTable().getTableHeader().getMouseListeners();
			for(MouseListener listener : listeners)
				m_billCardPanel.getBillTable().getTableHeader().removeMouseListener(listener);
			
			m_billCardPanel.getBillModel().getItemByKey("vcomputevalue").setDecimalDigits(4);
			//m_billCardPanel.getBillTable().getColumnModel().getColumn(4).setMinWidth(100);
			//m_billCardPanel.getBillTable().getColumnModel().getColumn(4).setMaxWidth(100);
			//m_billCardPanel.getBillTable().getColumnModel().getColumn(4).setPreferredWidth(100);
			
			leftColHelper = new BillColumnHelper(m_billCardPanel, "bselect", 0);
			leftColHelper.setSelectColumnHeader();
			
			leftgridpanel.add(m_billCardPanel);
		}
		
		return leftgridpanel;
	}
	
	private void setButtonStatusEx(){
		if (reportpanel.getBillModel().getRowCount()==0){
			m_checkItemIds.clear();
			m_checkItemNames.clear();
			m_checkItemIdNo.clear();
		}
		
		
		if (invDocRef.getRefPK()!=null && invDocRef.getRefPK().length()==20)
			bnStock.setEnabled(true);
		else
			bnStock.setEnabled(false);
		
		if (checkStandardRef.getRefPK()!=null && checkStandardRef.getRefPK().length()==20 
				&& getReportPanel().getBillModel().getRowCount()>0){
			bnCompute.setEnabled(true);
			bnTry.setEnabled(true);
		}
		else{
			bnCompute.setEnabled(false);
			bnTry.setEnabled(false);
		}
		
		updateButton(bnStock);
		updateButton(bnCompute);
		updateButton(bnTry);
		
		if (reportpanel.getBillModel().getRowCount()!=0){
			setButtonStatus("QUERY");
		}
		else
			setButtonStatus("INIT");
		updateUI();
	}
	
	@Override
	public ReportPanel getReportPanel() {
		if (reportpanel ==null) {
			super.getReportPanel();
			reportColHelper = new BillColumnHelper(reportpanel, "bselect", 0);
			initBodyItems = reportpanel.getBody_Items();
			((nc.ui.pub.bill.BillModel)getReportPanel().getBillTable().getModel()).setCellEditableController(new BillModelCellEditableController() {
				public boolean isCellEditable(boolean value, int row, String itemkey) {
					if (itemkey.equals("bselect") || itemkey.equals("nmaxusenum") || itemkey.equals("nunitusenum")) 
						return true;
					else
						return false;
				}
			});
			
			if (reportpanel.getBillModel().getItemByKey("nmaxusenum")!=null)
				reportpanel.getBillModel().getItemByKey("nmaxusenum").setDecimalDigits(2);
			reportpanel.getBillModel().getItemByKey("nunitusenum").setDecimalDigits(2);
			reportpanel.getBillModel().getItemByKey("nstocknum").setDecimalDigits(2);
			if (reportpanel.getBillModel().getItemByKey("nusenum")!=null)
				reportpanel.getBillModel().getItemByKey("nusenum").setDecimalDigits(2);
			reportpanel.getBillModel().getItemByKey("nremainnum").setDecimalDigits(2);
			
			reportpanel.addEditListener(this);
		}
		return reportpanel;
	}

	private void setReportPanelHeader() {
		reportColHelper.setSelectColumnHeader();
	}
	
	@Override
	public void onOut() {
		PrintEntry pe = new PrintEntry(this);
		// 设置打印模板ID的查询条件
		pe.setTemplateID(getCorpPrimaryKey(), getNodeCode(),
				getClientEnvironment().getUser().getPrimaryKey(),
				getBusitype());
		// 如果分配了多个打印模板，可选择一个模板
		pe.selectTemplate();
		// 添加打印数据，只有一个数据源
		pe.setDataSource(getPrintDataSource());
		//导出到Excel
		pe.exportExcelFile();
	}	
	
	private IDataSource getPrintDataSource(){
		PrintDataSource dataSrc = new PrintDataSource();
		
		ArrayList<String> custNames = new ArrayList<String>();
		ArrayList<String> stockBatchs = new ArrayList<String>();
		ArrayList<String> unitNumbers = new ArrayList<String>();
		ArrayList<String> newBatchNo = new ArrayList<String>();
		String mainWarehouse = null;
		
		Hashtable<String, Integer> wareHouseCountHash = new Hashtable<String, Integer>();
		
		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (reportpanel.getBillModel().getValueAt(row, "nunitusenum") != null && 
					new UFDouble(reportpanel.getBillModel().getValueAt(row, "nunitusenum").toString()).doubleValue()>0){
				String warehouse = reportpanel.getBillModel().getValueAt(row, "vstock").toString();
				if (!wareHouseCountHash.containsKey(warehouse))
					wareHouseCountHash.put(warehouse, 1);
				else
					wareHouseCountHash.put(warehouse, wareHouseCountHash.get(warehouse)+1);
			}
		}		
		
		for (String curWarehouse : wareHouseCountHash.keySet()){
			if (mainWarehouse==null || wareHouseCountHash.get(curWarehouse) > wareHouseCountHash.get(mainWarehouse))
				mainWarehouse = curWarehouse;
		}
		
		for (int row=0;row<reportpanel.getBillModel().getRowCount();row++ ){
			if (reportpanel.getBillModel().getValueAt(row, "nunitusenum") != null &&
					new UFDouble(reportpanel.getBillModel().getValueAt(row, "nunitusenum").toString()).doubleValue()>0){
				String warehouse = reportpanel.getBillModel().getValueAt(row, "vstock").toString();
				String invname = reportpanel.getBillModel().getValueAt(row, "vinvdocname").toString();
				String batchNo = reportpanel.getBillModel().getValueAt(row, "vbatchcode").toString();
				String unitNum = reportpanel.getBillModel().getValueAt(row, "nunitusenum").toString();
				
				String custname = reportpanel.getBillModel().getValueAt(row, "vcustname").toString();
				if (!warehouse.equals(mainWarehouse))
					custname = warehouse+"-"+custname;
				
				if (!invname.equals(invDocRef.getRefName()))
					custname = custname+"-"+invname;
				
				custNames.add(custname);
				stockBatchs.add(batchNo);
				unitNumbers.add(unitNum);
				newBatchNo.add(newBatchNoCtrl.getText());
			}
		}
		
		dataSrc.addItemValue("vmaininvl", new String[]{"现将（"+invDocRef.getRefName()});
		dataSrc.addItemValue("vmainware", new String[]{mainWarehouse});
		dataSrc.addItemValue("vbatchno", stockBatchs.toArray(new String[]{}));
		dataSrc.addItemValue("vcustname", custNames.toArray(new String[]{}));
		dataSrc.addItemValue("nusernum", unitNumbers.toArray(new String[]{}));
		dataSrc.addItemValue("vnewbatchno", newBatchNo.toArray(new String[]{}));
		
		return dataSrc;
	}
	
	public void onModelPrint() {
		nc.ui.pub.print.PrintEntry print = new nc.ui.pub.print.PrintEntry(null,
				null);
		print.setTemplateID(getCorpPrimaryKey(), getNodeCode(),
				getClientEnvironment().getUser().getPrimaryKey(), null);
		print.setDataSource(getPrintDataSource());
		if (print.selectTemplate() < 0)
			return;
		print.print(true);
	}

	public void afterEdit(BillEditEvent e) {
		if (e.getKey().equals("nunitusenum")){
			try {
				onManuCompute();
			} catch (BusinessException ex) {
				MessageDialog.showWarningDlg(this, "错误", ex.getMessage());
			}
		}else if (e.getKey().equals("vrequirevalue")){
			setLeftGridCellRenderValue();
		}
	}

	public void bodyRowChange(BillEditEvent e) {
	}
}
