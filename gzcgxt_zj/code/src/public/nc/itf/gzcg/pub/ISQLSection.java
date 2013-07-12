package nc.itf.gzcg.pub;

public interface ISQLSection {
	String[] getTables();
	String[] getJoins();
	String getValue();
	String getKey();
	String[] getCols();
	String[] getQueryItems();
}
