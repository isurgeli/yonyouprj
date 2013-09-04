package nc.itf.gzcg.pub;

public enum GZCGReportAnalysisConst implements ISQLSection{
	CUSTOMER		("CUSTOMER",
					"��Ӧ��",
					new String[]{"vcustcode", "vcustname"},
					new String[]{"bd_cubasdoc.custcode", "bd_cubasdoc.custname"},
					new String[]{"bd_cubasdoc", "bd_cumandoc"},
					new String[]{" and gzcg_qcrp_checkbill_v.cvendormangid=bd_cumandoc.pk_cumandoc and bd_cumandoc.pk_cubasdoc = bd_cubasdoc.pk_cubasdoc "}),
	INVCL			("INVCL",
					"�������",
					new String[]{"vinvclcode", "vinvclname"},
					new String[]{"bd_invcl.invclasscode", "bd_invcl.invclassname"},
					new String[]{"bd_invcl"},
					new String[]{" and gzcg_qcrp_checkbill_v.pk_invcl = bd_invcl.pk_invcl "}),
	INVDOC("INVDOC",
					"���",
					new String[]{"vinvdoccode", "vinvdocname", "vinvspec", "vinvunit"},
					new String[]{"bd_invbasdoc.invcode", "bd_invbasdoc.invname", "nvl(bd_invbasdoc.invspec,'-')", "nvl(bd_measdoc.measname,'-')"},
					new String[]{"bd_invbasdoc", "bd_invmandoc", "bd_measdoc"},
					new String[]{" and gzcg_qcrp_checkbill_v.cmangid = bd_invmandoc.pk_invmandoc and bd_invmandoc.pk_invbasdoc = bd_invbasdoc.pk_invbasdoc and bd_invbasdoc.pk_measdoc=bd_measdoc.pk_measdoc(+)"}),
	MONTH			("MONTH",
					"�¶�",
					new String[]{"vmonth"},
					new String[]{"substr(gzcg_qcrp_checkbill_v.dpraydate,0,7)"},
					new String[]{},
					new String[]{}),
	INAMOUNT		("INAMOUNT",
					"��������",
					new String[]{"ninamount"},
					new String[]{"sum(gzcg_qcrp_checkbill_v.nchecknum) ninamount"},
					new String[]{},
					new String[]{}),
	INCOUNT			("INCOUNT",
					"���ϴ���",
					new String[]{"nincount"},
					new String[]{"count(gzcg_qcrp_checkbill_v.nchecknum) nincount"},
					new String[]{},
					new String[]{}),
	SAMPLECOUNT		("SAMPLECOUNT",
					"ȡ������",
					new String[]{"nsamplecount"},
					new String[]{"sum(gzcg_qcrp_checkbill_v.nsamplecount) nsamplecount"},
					new String[]{},
					new String[]{}),
	INPASSAMOUNT	("INPASSAMOUNT",
					"���Ϻϸ�����",
					new String[]{"ninpassamount"},
					new String[]{"sum(gzcg_qcrp_checkbill_v.nchecknum*decode(gzcg_qcrp_checkbill_v.bqualified,'Y',1,0)) ninpassamount"},
					new String[]{},
					new String[]{}),
	INPASSCOUNT		("INPASSCOUNT",
					"���Ϻϸ����",
					new String[]{"ninpasscount"},
					new String[]{"sum(decode(gzcg_qcrp_checkbill_v.bqualified,'Y',1,0)) ninpasscount "},
					new String[]{},
					new String[]{}),
	AMOUNTPASSRATIO	("AMOUNTPASSRATIO",	
					"�����ϸ���", 		
					new String[]{"namountpassratio"},			
					new String[]{"sum(gzcg_qcrp_checkbill_v.nchecknum*decode(gzcg_qcrp_checkbill_v.bqualified,'Y',1,0))/sum(gzcg_qcrp_checkbill_v.nchecknum) namountpassratio"},
					new String[]{},
					new String[]{}),
	COUNTPASSRATIO	("COUNTPASSRATIO", 	
					"�����ϸ���", 		
					new String[]{"ncountpassratio"},			
					new String[]{"sum(decode(gzcg_qcrp_checkbill_v.bqualified,'Y',1,0))/count(gzcg_qcrp_checkbill_v.nchecknum) ncountpassratio "},
					new String[]{},
					new String[]{}),
	AMOUNTPASSORDER	("AMOUNTPASSORDER", 
					"�����ϸ�������",	
					new String[]{"namountorder"},				
					new String[]{"RANK () OVER (#PLACEHOLD# ORDER BY sum(gzcg_qcrp_checkbill_v.nchecknum*decode(gzcg_qcrp_checkbill_v.bqualified,'Y',1,0))/sum(gzcg_qcrp_checkbill_v.nchecknum) desc) namountorder"},
					new String[]{},
					new String[]{}),
	COUNTPASSORDER	("COUNTPASSORDER", 	
					"�����ϸ�������", 	
					new String[]{"ncountorder"},				
					new String[]{"RANK () OVER (#PLACEHOLD# ORDER BY sum(decode(gzcg_qcrp_checkbill_v.bqualified,'Y',1,0))/count(gzcg_qcrp_checkbill_v.nchecknum) desc) ncountorder"},
					new String[]{},
					new String[]{}),
	PROCESSTYPE		("PROCESSTYPE", 	
					"����ʽ", 		
					new String[]{},								
					new String[]{"nvl(qc_defectprocess.cdefectprocessname, '����')"},
					new String[]{"qc_defectprocess"},
					new String[]{" and gzcg_qcrp_checkbill_v.cdefectprocessid=qc_defectprocess.cdefectprocessid(+) "});
	
	public static final int customerDimentionStart = 0;
	public static final int customerDimentionEnd = 3;
	public static final int customerMeasureStart = 4;
	public static final int customerMeasureEnd = 12;
	public static final int customerCrossStart = 13;
	public static final int customerCrossEnd = 13;
	
	private final String 	value;
	private final String 	key;
	private final String[]	cols;
	private final String[]	queryItems;
	private final String[]	tables;
	private final String[]	joins;
	 
	private GZCGReportAnalysisConst(String key, String value, String[] cols, String[] queryItems, String[] tables, String[] joins) {
		this.key = key;
		this.value = value;
		this.cols = cols;
		this.queryItems = queryItems;
		this.tables = tables;
		this.joins = joins;
	}
	
	public String[] getTables() {
		return tables;
	}

	public String[] getJoins() {
		return joins;
	}

	public String getValue() {
	     return value;
	}
	
	public String getKey() {
	     return key;
	}
	
	public String[] getCols() {
	     return cols;
	}
	
	public String[] getQueryItems() {
	     return queryItems;
	}
}
