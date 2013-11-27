package nc.ui.gzcg.pub;

import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;

import nc.ui.pub.bill.BillCardPanel;

public class BillColumnHelper {
	private BillCardPanel rp;
	private String selectColKey;
	private CheckBoxRenderer stockChecker;
	private int offset;
	
	public BillColumnHelper(BillCardPanel _rp, String _selectColKey, int _offset){
		rp = _rp;
		selectColKey = _selectColKey;
		stockChecker = new CheckBoxRenderer();
		stockChecker.setSelected(true);
		offset = _offset;
	}
	
	public void setSelectColumnHeader(){
		String colName = getColumnName(rp, selectColKey);
		
		rp.getBillTable().getColumn(colName).setMaxWidth(20);
		rp.getBillTable().getColumn(colName).setHeaderRenderer(stockChecker);
		
		rp.getBillTable().getTableHeader().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				if(rp.getBillTable().getColumnModel().getColumnIndexAtX(e.getX())+offset==rp.getBillModel().getBodyColByKey(selectColKey)){//���������ǵ�0�У���checkbox��һ��
					boolean b = !stockChecker.isSelected();
					stockChecker.setSelected(b);
					rp.getBillTable().getTableHeader().repaint();
					for(int i=0;i<rp.getBillTable().getRowCount();i++){
						rp.getBillModel().setValueAt(b, i, selectColKey);//����һ�ж���ɺͱ�ͷһ��
					}
				}
			}
		});
	}
	
	public void setSelectColumnMultiSelect(){
		rp.getBillTable().addMouseListener(new MouseAdapter() {
			private int startRow = -1;
			private long startTime = 0;
			
			@Override
			public void mousePressed(MouseEvent e) {
				 int LEFT_CTRL_MASK = InputEvent.BUTTON1_MASK + InputEvent.CTRL_MASK;
				 if ((e.getModifiers() & LEFT_CTRL_MASK) == LEFT_CTRL_MASK) {
					 int col = rp.getBillTable().columnAtPoint(e.getPoint())+offset;
				     int row = rp.getBillTable().rowAtPoint(e.getPoint());
				     if (col==rp.getBillModel().getBodyColByKey(selectColKey) && row>=0 && row<rp.getBillTable().getRowCount()){
				    	 startRow = row;
				    	 startTime = new Date().getTime();
				     }
				 }
			}
			
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	int LEFT_CTRL_MASK = InputEvent.BUTTON1_MASK + InputEvent.CTRL_MASK;
				if ((e.getModifiers() & LEFT_CTRL_MASK) == LEFT_CTRL_MASK) {
				     int row = rp.getBillTable().rowAtPoint(e.getPoint());
				     if (startRow >= 0 && row>=0 && row<rp.getBillTable().getRowCount() && new Date().getTime()-startTime < 3000){
				    	 int step=startRow<row?1:-1;
				    	 for(int i=startRow;step>0?i<=row:i>=row;i+=step){
				    		 boolean b = !(rp.getBillModel().getValueAt(i, selectColKey)==null?false:rp.getBillModel().getValueAt(i, selectColKey).equals(true));
				    		 rp.getBillModel().setValueAt(b, i, selectColKey);
				    	 }
				     }
				     
				     startRow = -1;
			    	 startTime = 0;
				 }
		    }
		});
	}
	
	public static String getColumnName(BillCardPanel rp, String colKey){
		return rp.getBillModel().getItemByKey(colKey).getName();
	}
}
