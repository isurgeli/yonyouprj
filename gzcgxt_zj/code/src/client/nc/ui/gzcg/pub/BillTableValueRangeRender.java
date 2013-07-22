package nc.ui.gzcg.pub;

import nc.ui.pub.bill.BillItem;
import nc.ui.pub.bill.BillTableCellRenderer;
import nc.vo.pub.lang.UFDouble;

public class BillTableValueRangeRender extends BillTableCellRenderer{

	private static final long serialVersionUID = 1L;
	
	private double min,max;

	public BillTableValueRangeRender(BillItem item, double min, double max) {
		super(item);
		this.min = min;
		this.max = max;
	}
	
	@Override
	protected void setValue(Object value) {
		super.setValue(value);
		
		if (value!=null && value.toString().length()>0){
			double dou = 0;
			String v = value.toString();
			try {
				dou = Double.valueOf(v);
				if (dou < min || dou > max)
					setForeground(java.awt.Color.red);
			} catch (Exception e) {
			}
		}
	}
}
