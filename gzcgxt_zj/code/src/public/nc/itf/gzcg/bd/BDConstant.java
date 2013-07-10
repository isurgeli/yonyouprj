package nc.itf.gzcg.bd;

public enum BDConstant {
	FUNCODE("C0010115"),
	VOUCHERMAKE("20021005");
		
	private final String value;
	 
	private BDConstant(String value) {
	     this.value = value;
	}
	
	public String getValue() {
	     return value;
	}
}
