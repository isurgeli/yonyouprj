package nc.itf.gzcg.pub;

public enum GZCGReportStatisticsConst implements ISQLSection{
	PUBLIC ("PUBLIC",
			"公共",
			new String[]{"vstockbatch", "vinvdoccode", "vinvdocname", "ninnum", "vprocessname", "dcheck", "vsamplecode", "vchargepsn"},
			new String[]{"gzcg_qcrp_checkbill_v.vbatchcode", "bd_invbasdoc.invcode", 
			"bd_invbasdoc.invname", "gzcg_qcrp_checkbill_v.nchecknum", "nvl(qc_defectprocess.cdefectprocessname, '其它')", "gzcg_qcrp_checkbill_v.dpraydate", "qc_checkbill_b2.vsamplecode", 
			"sm_user.user_name"},
			new String[]{"qc_checkbill_b2", "bd_invmandoc", "bd_invbasdoc", "sm_user", "qc_defectprocess"},
			new String[]{" and gzcg_qcrp_checkbill_v.ccheckbillid = qc_checkbill_b2.ccheckbillid "
			+"and gzcg_qcrp_checkbill_v.cmangid = bd_invmandoc.pk_invmandoc "
			+"and bd_invmandoc.pk_invbasdoc = bd_invbasdoc.pk_invbasdoc "
			+"and gzcg_qcrp_checkbill_v.creporterid = sm_user.cuserid "
			+"and gzcg_qcrp_checkbill_v.cdefectprocessid = qc_defectprocess.cdefectprocessid(+)"
			+"and nvl(qc_checkbill_b2.dr, 0)=0 and qc_checkbill_b2.cresult is not null "}),
	MATERIAL("MATERIAL",
			"原料",
			new String[]{"vorderbillcode", "vcustcode", "vcustname"},
			new String[]{"gzcg_qcrp_checkbill_v.vordercode", "bd_cubasdoc.custcode", "bd_cubasdoc.custname"},
			new String[]{"bd_cumandoc", "bd_cubasdoc"},
			new String[]{" and gzcg_qcrp_checkbill_v.cvendormangid = bd_cumandoc.pk_cumandoc "
			+"and bd_cumandoc.pk_cubasdoc = bd_cubasdoc.pk_cubasdoc "}),
	PROJECT	("PROJECT",
			"投影",
			new String[]{},
			new String[]{"qc_checkbill_b2.ccheckitemid", "qc_checkbill_b2.cresult"},
			new String[]{},
			new String[]{}),
	SEMIPRODUCTPUBLIC ("PUBLIC",
			"公共",
			new String[]{"vinvdocname", "dcheck", "vsamplecode", "vchargepsn"},
			new String[]{"qc_cghzbg_b.ypname", "gzcg_qcrp_checkbill_v.dpraydate", "qc_cghzbg_b.jcpici", "'-'"},
			new String[]{"qc_cghzbg_h", "qc_cghzbg_b", "sm_user"},
			new String[]{" and gzcg_qcrp_checkbill_v.ccheckbillid = qc_cghzbg_b.jcpici "}),
	SEMIPRODUCTPROJECT	("PROJECT",
			"投影",
			new String[]{},
			new String[]{"qc_cghzbg_b.checkitem", "qc_cghzbg_b.checkvalue"},
			new String[]{},
			new String[]{});
	
	private final String 	value;
	private final String 	key;
	private final String[]	cols;
	private final String[]	queryItems;
	private final String[]	tables;
	private final String[]	joins;
	 
	private GZCGReportStatisticsConst(String key, String value, String[] cols, String[] queryItems, String[] tables, String[] joins) {
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
