package nc.ui.gzcg.pub;

import java.awt.Component;

import javax.swing.JTable;

import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillTableCellRenderer;

public class TableCellValueRangeRender extends BillTableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	private double[] min;
	private double[] max;

	public void setMin(double[] min) {
		this.min = min;
	}

	public void setMax(double[] max) {
		this.max = max;
	}

	public TableCellValueRangeRender(BillItem item, double[] min, double[] max) {
		super(item);
		this.min = min;
		this.max = max;
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Component ret =  super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);
		
		if (value!=null && value.toString().length()>0){
			double dou = 0;
			String v = value.toString();
			try {
				dou = Double.valueOf(v);
				if (min.length>row && max.length>row && dou < min[row] || dou > max[row])
					setForeground(java.awt.Color.red);
			} catch (Exception e) {
			}
		}
		
		return ret;
	}
}
