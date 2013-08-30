package nc.ui.gzcg.mixturetool;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import nc.bs.framework.common.NCLocator;
import nc.itf.gzcg.pub.GZCGConstant;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.gzcg.pub.BillColumnHelper;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.bill.BillModelCellEditableController;
import nc.ui.pub.report.ReportItem;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.lang.UFDouble;

@SuppressWarnings("restriction")
public class StockSelectDlg extends UIDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private UIButton btnOk;
	private UIButton btnCacnel;
	private ReportPanel reportPanel;

	private String invpk;
	private String[] checkIds;
	private String[] checkNames;
	private ReportItem[] initBodyItems;
	private MixtureToolUI parentUI;

	private BillColumnHelper colHelper;
	
	private CircularlyAccessibleValueObject[] reportData;
	public CircularlyAccessibleValueObject[] retData;
	
	public StockSelectDlg(Container parent, String _invpk, String[] _checkIds, String[] _checkNames) {
		super(parent, "混料库存选择");
		
		parentUI = (MixtureToolUI)parent;
		invpk = _invpk;
		checkIds = _checkIds;
		checkNames = _checkNames;
		
		initUI();
		initData();
	}

	private void initData() {
		getReportPanel().setBody_Items(initBodyItems);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select bd_invbasdoc.invname, bd_stordoc.storname, ic_onhandnum.vlot, sum(ic_onhandnum.nonhandnum), ic_onhandnum.cwarehouseid from ic_onhandnum, bd_invbasdoc, bd_stordoc ");
		sql.append("where ic_onhandnum.cwarehouseid=bd_stordoc.pk_stordoc and ic_onhandnum.cinvbasid=bd_invbasdoc.pk_invbasdoc and ic_onhandnum.cinventoryid='");
		sql.append(invpk+"' and ic_onhandnum.nonhandnum > 0 group by bd_invbasdoc.invname, bd_stordoc.storname, ic_onhandnum.vlot, ic_onhandnum.cwarehouseid order by ic_onhandnum.vlot");
		
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
				uiStocks[i].setAttributeValue("nmaxusenum", new UFDouble(stock.get(i).get(3).toString()));
				
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
	}

	private void getCheckValue(Hashtable<String, AnalysisReportVO> uiStockSet, String[] warehouses, String[] stockbatchs) {
		String[] checkitemids = checkIds;
		
		Hashtable<String, Integer> checkItemidNo = new Hashtable<String, Integer>();
		for(int i=0;i<checkitemids.length;i++)
			checkItemidNo.put(checkitemids[i], i);
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ic_general_h_v.CWAREHOUSEID, ic_general_b_v.VBATCHCODE, qc_checkbill_b2_v.CCHECKITEMID, qc_checkbill_b2_v.CRESULT, ic_general_h_v.daccountdate, bd_cubasdoc.custname");
		sql.append(" from qc_checkbill, qc_checkbill_b1, qc_checkbill_b2_v, ic_general_b_v, ic_general_h_v, bd_cumandoc, bd_cubasdoc");
		sql.append(" where qc_checkbill.ccheckbillid = qc_checkbill_b1.ccheckbillid");
		sql.append(" and qc_checkbill.ccheckbillid = qc_checkbill_b2_v.ccheckbillid");
		sql.append(" and qc_checkbill_b1.csourcebillrowid = ic_general_b_v.csourcebillbid(+)");
		sql.append(" and ic_general_b_v.cgeneralhid = ic_general_h_v.cgeneralhid(+)");
		sql.append(" and qc_checkbill_b1.csourcebilltypecode = '23' and qc_checkbill_b1.norder = 0");
		sql.append(" and ic_general_h_v.cproviderid = bd_cumandoc.pk_cumandoc");
		sql.append(" and bd_cumandoc.pk_cubasdoc = bd_cubasdoc.pk_cubasdoc");
		sql.append(" and nvl(qc_checkbill.dr, 0) = 0 and nvl(qc_checkbill_b1.dr, 0) = 0");
		sql.append(" and ic_general_b_v.VBATCHCODE in "+getInsqlClause(stockbatchs));
		sql.append(" and ic_general_b_v.CINVENTORYID='"+invpk+"' ");
		sql.append(" and ic_general_h_v.CWAREHOUSEID in "+getInsqlClause(warehouses));
		sql.append(" and qc_checkbill_b2_v.CCHECKITEMID in "+getInsqlClause(checkitemids));
		sql.append(" order by qc_checkbill_b2_v.VSAMPLECODE, ic_general_h_v.daccountdate");
		
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> checkValue = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			for(int i=0;i<checkValue.size();i++){
				String cwarehouseid = checkValue.get(i).get(0).toString();
				String vstockbatch = checkValue.get(i).get(1).toString();
				String checkitemid = checkValue.get(i).get(2).toString();
				UFDouble checkvalue = new UFDouble(checkValue.get(i).get(3).toString());
				String indate = checkValue.get(i).get(4).toString();
				String custname = checkValue.get(i).get(5).toString();
				
				String key=cwarehouseid+vstockbatch;
			
				if (uiStockSet.containsKey(key)){
					AnalysisReportVO reportVO = uiStockSet.get(key);
					if(!reportVO.haveKey("_CROSS"+checkItemidNo.get(checkitemid)))
						reportVO.setAttributeValue("_CROSS"+checkItemidNo.get(checkitemid), checkvalue);
					
					if(!reportVO.haveKey("dindate"))
						reportVO.setAttributeValue("dindate", indate);
					
					if(!reportVO.haveKey("vcustname"))
						reportVO.setAttributeValue("vcustname", custname);
				}
			}
			parentUI.processTableLayout(getReportPanel(), initBodyItems, checkNames, checkitemids);
			
			setReportPanelHeader();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private void setReportPanelHeader() {		
		colHelper.setSelectColumnHeader();
		colHelper.setSelectColumnMultiSelect();
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

	private void initUI() {
		setSize(1024, 600);
		setLayout(new BorderLayout());

		add(getReportPanel(), BorderLayout.NORTH);


		UIPanel buttonPanel = new UIPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		buttonPanel.add(getBtnOk());
		buttonPanel.add(getBtnCacnel());
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	public ReportPanel getReportPanel() {
		if (reportPanel ==null) {
			try {
				reportPanel = new ReportPanel();
				reportPanel.setMaximumSize(new Dimension(1000, 530));
				reportPanel.setPreferredSize(new Dimension(1000, 530));
				reportPanel
						.setName(nc.ui.ml.NCLangRes.getInstance().getStrByID(
								"scmpub", "UPPscmpub-000778")/* @res "报表基类" */);
				reportPanel.setTempletID(ClientEnvironment.getInstance().getCorporation().getPrimaryKey(), 
						GZCGConstant.MIXTURETOOLFUNCODE.getValue(),
						ClientEnvironment.getInstance().getUser().getPrimaryKey(),
						null);
				
				reportPanel.setBodyMenuShow(false);
				reportPanel.setRowNOShow(true);
				reportPanel.getBillTable().getTableHeader().addMouseListener(
						reportPanel);
				
				colHelper = new BillColumnHelper(reportPanel, "bselect");
				
				initBodyItems = reportPanel.getBody_Items();
				ArrayList<ReportItem> rps = new ArrayList<ReportItem>();
				for(int i=0;i<7;i++) rps.add(initBodyItems[i]);
				initBodyItems = rps.toArray(new ReportItem[]{});
				
				((nc.ui.pub.bill.BillModel)getReportPanel().getBillTable().getModel()).setCellEditableController(new BillModelCellEditableController() {
					public boolean isCellEditable(boolean value, int row, String itemkey) {
						if (itemkey.equals("bselect")) 
							return true;
						else
							return false;
					}
				});
			} catch (java.lang.Throwable ivjExc) {
			}
		}
		return reportPanel;
	}

	private UIButton getBtnCacnel() {
		if (btnCacnel == null) {
			btnCacnel = new UIButton("取消");
			btnCacnel.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					closeCancel();
				}
			});
		}
		return btnCacnel;
	}

	private UIButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new UIButton("确定");
			btnOk.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					ArrayList<CircularlyAccessibleValueObject> retList = new ArrayList<CircularlyAccessibleValueObject>();
					if (reportData != null){
						for (int row=0;row<reportPanel.getBillModel().getRowCount();row++ ){
							if (reportPanel.getBillModel().getValueAt(row, "bselect").equals(true)){
								retList.add(reportData[row]);
							}
						}
					}
					retData = retList.toArray(new CircularlyAccessibleValueObject[]{});
					closeOK();
				}
			});
		}
		return btnOk;
	}

	public void setData(CircularlyAccessibleValueObject[] report) {
		// //设置报表数据和显示界面
		reportData = report;
		if (report == null) {
			getReportPanel().getBillModel().clearBodyData();
			return;
		}

		getReportPanel().setTatolRowShow(false);
		getReportPanel().setBodyDataVO(report);
		getReportPanel().getBillModel().execLoadFormula();
		getReportPanel().updateValue();
	}
}
