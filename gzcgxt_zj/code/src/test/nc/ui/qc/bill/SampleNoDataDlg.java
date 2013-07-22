package nc.ui.qc.bill;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Vector;
import nc.bs.framework.common.NCLocator;
import nc.itf.uap.IUAPQueryBS;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIButton;
import nc.ui.pub.beans.UIDialog;
import nc.ui.pub.beans.UIPanel;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.report.ReportItem;
import nc.ui.scm.pub.report.ReportPanel;
import nc.vo.gzcg.report.AnalysisReportVO;
import nc.vo.pub.BusinessException;

@SuppressWarnings("restriction")
public class SampleNoDataDlg extends UIDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UIButton btnCacnel;
	private UIButton btnOk;
	private ReportPanel reportpanel;
	public int selectedIdx;

	public SampleNoDataDlg(Container parent, ArrayList<Hashtable<String, String>> data) {
		super(parent, "质检数据");
		initUI();
		initData(data);
	}
	
	
	private void initData(ArrayList<Hashtable<String, String>> data) {
		ArrayList<String> checkItemIds = new ArrayList<String>();
		ArrayList<String> checkItemNames = new ArrayList<String>();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select qc_checkitem.ccheckitemid, qc_checkitem.ccheckitemname from qc_invrelate, qc_checkstandard_b, qc_checkitem, gzcg_bd_sampledoc");
		sql.append(" where qc_invrelate.ccheckstandardid=qc_checkstandard_b.ccheckstandardid and qc_checkitem.ccheckitemid = qc_checkstandard_b.ccheckitemid ");
		sql.append(" and qc_invrelate.bdefault='Y' and qc_invrelate.cmangid=gzcg_bd_sampledoc.pk_invmandoc and gzcg_bd_sampledoc.vsampleno='");
		sql.append(data.get(0).get("vsampleno")+"'");
		
		IUAPQueryBS dao = (IUAPQueryBS)NCLocator.getInstance().lookup(IUAPQueryBS.class.getName());
		try {
			@SuppressWarnings("unchecked")
			Vector<Vector<Object>> checkItemsData = (Vector<Vector<Object>>)dao.executeQuery(sql.toString(), new VectorProcessor());
			if (checkItemsData!=null && checkItemsData.size()>0){
				for(int i=0;i<checkItemsData.size();i++){
					checkItemIds.add(checkItemsData.get(i).get(0).toString());
					checkItemNames.add(checkItemsData.get(i).get(1).toString());
				}
			}
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AnalysisReportVO[] reportData = new AnalysisReportVO[data.size()];
		for(int row=0;row<data.size();row++){
			reportData[row] = new AnalysisReportVO();
			reportData[row].setAttributeValue("vsampleno", data.get(row).get("vsampleno"));
			for(int col=0;col<checkItemIds.size();col++){
				if (data.get(row).containsKey(checkItemIds.get(col))){
					reportData[row].setAttributeValue("_CHECKTITEM"+col, data.get(row).get(checkItemIds.get(col)));
				}
			}
		}
		
		processTableLayout(checkItemIds.toArray(new String[]{}), checkItemNames.toArray(new String[]{}));
		
		getReportPanel().setBodyDataVO(reportData);
	}
	
	private void processTableLayout(String[] checkItemIds, String[] checkItemNames) {
		ReportItem[] reportItems = new ReportItem[checkItemIds.length];
		for(int i=0;i<checkItemIds.length;i++){
			ReportItem item = new ReportItem();
			item.setWidth(80);
			item.setKey("_CHECKTITEM"+i);
			item.setName(checkItemNames[i]);
			item.setDataType(IBillItem.DECIMAL);
			item.setDecimalDigits(4);
			reportItems[i] = item;
		}
		ArrayList<ReportItem> finalBodyItems = new ArrayList<ReportItem>();
		finalBodyItems.addAll(Arrays.asList(getReportPanel().getBody_Items()));
		finalBodyItems.addAll(Arrays.asList(reportItems));
		
		getReportPanel().setBody_Items(finalBodyItems.toArray(new ReportItem[]{}));
	}


	private void initUI() {
		setSize(850, 300);
		setLayout(new BorderLayout());
		UIPanel centerPanel = getReportPanel();
		add(centerPanel, BorderLayout.NORTH);

	
		UIPanel buttonPanel = new UIPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
		buttonPanel.add(getBtnOk());
		buttonPanel.add(getBtnCacnel());
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	private ReportPanel getReportPanel() {
		if (reportpanel == null) {
			try {
				reportpanel = new ReportPanel();
				reportpanel.setMaximumSize(new Dimension(800, 230));
				reportpanel.setPreferredSize(new Dimension(800, 230));
				reportpanel.setName("报表基类");
				reportpanel.setTempletID("0001ZF1000000001ODM5");
				reportpanel.setBodyMenuShow(false);
				reportpanel.setRowNOShow(true);
				reportpanel.getBillTable().getTableHeader().addMouseListener(reportpanel);
			} catch (java.lang.Throwable ivjExc) {
				
			}
		}
		return reportpanel;
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
			btnOk.addActionListener(this);
		}
		return btnOk;
	}
	
	public void actionPerformed(ActionEvent arg0) { 
		int[] rows = getReportPanel().getBillTable().getSelectedRows();
		if (rows==null || rows.length==0){
			MessageDialog.showWarningDlg(this, "提示", "请先选择一行数据。");
			return;
		}
		selectedIdx = rows[0];
		
		closeOK();
	}
}
