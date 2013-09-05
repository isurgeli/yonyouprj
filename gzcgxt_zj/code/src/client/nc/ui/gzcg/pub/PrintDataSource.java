package nc.ui.gzcg.pub;

import java.util.Hashtable;

import nc.ui.pub.print.IDataSource;

public class PrintDataSource implements IDataSource {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String, String[]> datas = new Hashtable<String, String[]>();
	private Hashtable<String, String> numItems = new Hashtable<String, String>();
	
	public void addItemValue(String itemExpress, String[] values){
		datas.put(itemExpress, values);
	}
	
	public void addNumberItem(String itemExpress){
		numItems.put(itemExpress, itemExpress);
	}

	public String[] getItemValuesByExpress(String itemExpress) {
		return datas.get(itemExpress);
	}

	public boolean isNumber(String itemExpress) {
		if (numItems.containsKey(itemExpress))
			return true;
		else
			return false;
	}

	public String[] getDependentItemExpressByExpress(String itemExpress) {
		return null;
	}

	public String[] getAllDataItemExpress() {
		return null;
	}

	public String[] getAllDataItemNames() {
		return null;
	}

	public String getModuleName() {
		return null;
	}
}
