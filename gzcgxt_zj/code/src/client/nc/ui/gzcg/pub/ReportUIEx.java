package nc.ui.gzcg.pub;

import nc.ui.scm.pub.report.ReportUI;

@SuppressWarnings("restriction")
public abstract class ReportUIEx extends ReportUI{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void setButtonStatus(String status) {
		if (status.equals("INIT")) {
			bnQuery.setEnabled(true);
			bnPrint.setEnabled(false);
			bnModelPrint.setEnabled(false);
			bnPreview.setEnabled(false);
			bnOut.setEnabled(false);
			bnShow.setEnabled(true);
			bnFilter.setEnabled(false);
			bnSubtotal.setEnabled(false);
			bnMultSort.setEnabled(false);
			bnLocate.setEnabled(false);
			bnRefresh.setEnabled(false);
			bnOrderQuery.setEnabled(false);
		} else if (status.equals("QUERY")) {
			bnQuery.setEnabled(true);
			bnPrint.setEnabled(true);
			bnModelPrint.setEnabled(true);
			bnPreview.setEnabled(true);
			bnOut.setEnabled(true);
			bnShow.setEnabled(true);
			bnFilter.setEnabled(true);
			bnSubtotal.setEnabled(true);
			bnMultSort.setEnabled(true);
			bnLocate.setEnabled(true);
			bnRefresh.setEnabled(true);
			bnOrderQuery.setEnabled(true);
		}
		updateButtons();
	}
	
	@Override
	public void onOut() {
		getReportPanel().exportExcelFile();
	}
}
