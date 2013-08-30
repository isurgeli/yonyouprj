package nc.ui.gzcg.pub;

import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class CheckBoxRenderer extends JCheckBox implements TableCellRenderer {
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
