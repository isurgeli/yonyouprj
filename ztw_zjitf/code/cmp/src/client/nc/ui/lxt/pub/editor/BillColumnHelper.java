package nc.ui.lxt.pub.editor;

import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillModelCellEditableController;

public class BillColumnHelper {
	private BillCardPanel cardPanel;
	private String selectColKey;
	private CheckBoxRenderer stockChecker;
	private int startRow = -1;
	private long startTime = 0;
	private int selectColIdx;

	public BillColumnHelper(BillCardPanel _rp, String _selectColKey) {
		cardPanel = _rp;
		selectColKey = _selectColKey;
		
		stockChecker = new CheckBoxRenderer();
		stockChecker.setSelected(false);
		
		selectColIdx = -1;
		
		for(int i=0;i<cardPanel.getBillTable().getColumnCount();i++){
			if (cardPanel.getBillTable().getColumnName(i).equals(getColumnName(cardPanel, selectColKey))){
				selectColIdx = i;
				break;
			}
		}
	}

	public void setSelectColumnEditable() {
		((nc.ui.pub.bill.BillModel) cardPanel.getBillTable().getModel()).setCellEditableController(new BillModelCellEditableController() {
			public boolean isCellEditable(boolean value, int row, String itemkey) {
				if (itemkey.equals(selectColKey))
					return true;
				else
					return false;
			}
		});
	}

	public void setSelectColumnHeader() {
		String colName = getColumnName(cardPanel, selectColKey);

		cardPanel.getBillTable().getColumn(colName).setMaxWidth(20);
		cardPanel.getBillTable().getColumn(colName).setHeaderRenderer(stockChecker);

		cardPanel.getBillTable().getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (cardPanel.getBillTable().getColumnModel().getColumnIndexAtX(e.getX()) == selectColIdx) {// 如果点击的是第0列，即checkbox这一列
					boolean b = !stockChecker.isSelected();
					stockChecker.setSelected(b);
					cardPanel.getBillTable().getTableHeader().repaint();
					for (int i = 0; i < cardPanel.getBillTable().getRowCount(); i++) {
						cardPanel.getBillModel().setValueAt(b, i, selectColKey);// 把这一列都设成和表头一样
					}
				}
			}
		});
	}

	public void setSelectColumnMultiSelect() {
		cardPanel.getBillTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int LEFT_SHITF_MASK = InputEvent.BUTTON1_MASK + InputEvent.SHIFT_MASK;
				if ((e.getModifiers() & LEFT_SHITF_MASK) == LEFT_SHITF_MASK) {
					if (startTime > 0){
						int row = cardPanel.getBillTable().rowAtPoint(e.getPoint());
						if (startRow >= 0 && row >= 0 && row < cardPanel.getBillTable().getRowCount() && new Date().getTime() - startTime < 3000) {
							int step = startRow < row ? 1 : -1;
							for (int i = startRow+step; step > 0 ? i < row : i > row; i += step) {
								boolean b = !(cardPanel.getBillModel().getValueAt(i, selectColKey) == null ? false
										: cardPanel.getBillModel().getValueAt(i, selectColKey).equals(true));
								cardPanel.getBillModel().setValueAt(b, i, selectColKey);
							}
						}
	
						startRow = -1;
						startTime = 0;
					}else{
						int col = cardPanel.getBillTable().columnAtPoint(e.getPoint());
						int row = cardPanel.getBillTable().rowAtPoint(e.getPoint());
						if (col == selectColIdx && row >= 0 && row < cardPanel.getBillTable().getRowCount()) {
							startRow = row;
							startTime = new Date().getTime();
						}
					}
				}
			}
		});
		
		cardPanel.getBillTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int SHITF_MASK = InputEvent.SHIFT_MASK;
				if ((e.getModifiers() & SHITF_MASK) == SHITF_MASK) {
					startRow = -1;
					startTime = 0;
				}
			}
		});
	}

	public static String getColumnName(BillCardPanel cp, String colKey) {
		return cp.getBillModel().getItemByKey(colKey).getName();
	}
}
