package nc.impl.ztwzj.sapitf;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Vector;

import javax.xml.bind.JAXBException;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.impl.ztwzj.sapitf.voucher.tabledef.CmpPayTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.CmpPayTableHeadCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.CmpRecTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.CmpRecTableHeadCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfAlloTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfAlloTableHeadCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfDeliTableBodyCorpDef;
import nc.impl.ztwzj.sapitf.voucher.tabledef.SfDeliTableHeadCorpDef;
import nc.itf.lxt.pub.jxabtool.JaxbTools;
import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLBuilderTool;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;
import nc.itf.lxt.pub.type.Pair;
import nc.itf.ztwzj.sapitf.IVoucherService;
import nc.jdbc.framework.processor.BeanListProcessor;
import nc.jdbc.framework.processor.VectorProcessor;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.ztwzj.sapitf.voucher.VoucherQryInfo;
import nc.vo.ztwzj.sapitf.voucher.ZtwVoucherConstant;
import nc.vo.ztwzj.sapitf.voucher.BillInfo.BillInfoList;
import nc.vo.ztwzj.sapitf.voucher.BillInfo.BillInfoList.BillInfo;
import nc.vo.ztwzj.sapitf.voucher.BillQry.BillQryPara;

public class VoucherService implements IVoucherService {

	@Override
	public void setTMVoucherBillFlag(String retInfo) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String qryTMVoucherBillInfoXml(String para) throws BusinessException {
		try {
			BillQryPara paraObj = JaxbTools.getObjectFromString(BillQryPara.class, para);
			Pair<String, String> orgInfo = getOrgPkLevel(paraObj.getBUKRS());
			String pk_org = orgInfo.first;
			String org_level = orgInfo.second;
			
			String sql = getQryTMVoucherBillSQl(pk_org, org_level, paraObj.getBUSITYPE(), paraObj.getBUDATS(), paraObj.getBUDATE());
			
			BaseDAO dao = new BaseDAO();
			@SuppressWarnings("unchecked")
			ArrayList<BillInfo> infos = (ArrayList<BillInfo>)dao.executeQuery(sql, new BeanListProcessor(BillInfo.class));
			BillInfoList infoList = new BillInfoList();
			infoList.setSuccess("1");
			infoList.setErrcode("");
			infoList.setMessage("");
			for(BillInfo info : infos)
				infoList.getBillInfo().add(info);
			
			String ret = JaxbTools.getStringFromObject(infoList);
			
			return ret;
		} catch (JAXBException e) {
			Logger.error(e.getMessage(), e);
			throw new BusinessException(e);
		}
	}

	private String getQryTMVoucherBillSQl(String pk_org, String org_level,
			String busitype, String budats, String budate) throws BusinessException {
		SQLBuilderTool st = null;
		Hashtable<String, Object> paras  = new Hashtable<String, Object>();
		if (org_level.equals("1") && busitype.equals(ZtwVoucherConstant.BT_DELIHEADCORP.getValue())) { //һ������-�տ˾
			st = new SQLBuilderTool(new SfDeliTableHeadCorpDef());
		} else if (org_level.equals("2") && busitype.equals(ZtwVoucherConstant.BT_DELIHEADCORP.getValue())) { //��������-�տ˾
			st = new SQLBuilderTool(new CmpRecTableHeadCorpDef());
		} else if (org_level.equals("2") && busitype.equals(ZtwVoucherConstant.BT_DELIBODYCORP.getValue())) { //һ������-�ɿ˾
			st = new SQLBuilderTool(new SfDeliTableBodyCorpDef());
		} else if (org_level.equals("3") && busitype.equals(ZtwVoucherConstant.BT_DELIBODYCORP.getValue())) { //��������-�ɿ˾
			st = new SQLBuilderTool(new CmpRecTableBodyCorpDef());
		} else if (org_level.equals("1") && busitype.equals(ZtwVoucherConstant.BT_ALLOHEADCORP.getValue())) { //һ���²�-���˾
			st = new SQLBuilderTool(new SfAlloTableHeadCorpDef());
		} else if (org_level.equals("2") && busitype.equals(ZtwVoucherConstant.BT_ALLOHEADCORP.getValue())) { //�����²�-���˾
			st = new SQLBuilderTool(new CmpPayTableHeadCorpDef());
		} else if (org_level.equals("2") && busitype.equals(ZtwVoucherConstant.BT_ALLOBODYCORP.getValue())) { //һ���²�-�տ˾
			st = new SQLBuilderTool(new SfAlloTableBodyCorpDef());
		} else if (org_level.equals("3") && busitype.equals(ZtwVoucherConstant.BT_ALLOBODYCORP.getValue())) { //�����²�-�տ˾
			st = new SQLBuilderTool(new CmpPayTableBodyCorpDef());
		} else {
			throw new BusinessException("["+org_level+"]��˾û��["+busitype+"]ҵ��");
		}
		SQLWhereClause[] flexWheres = new SQLWhereClause[] {
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "pk_qryorg", OPERATOR.EQ, DELIMITER.getParaExp("CORP")),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "BUDAT", OPERATOR.GTE, DELIMITER.getParaExp("BUDATS")),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "BUDAT", OPERATOR.LTE, DELIMITER.getParaExp("BUDATE")),
			new SQLWhereClause(OPERATOR.AND, BRACKET.NONE, "voucherflag", OPERATOR.NEQ, "'Y'")
		};
		paras.put("BUKRSLEVEL", DELIMITER.getStringParaValue(org_level));
		paras.put("BUSITYPE", DELIMITER.getStringParaValue(busitype));
		paras.put("CORP", DELIMITER.getStringParaValue(pk_org));
		SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
		try {
			paras.put("BUDATS", DELIMITER.getStringParaValue(UFDate.getDate(sFormat.parse(budats)).toString()));
			paras.put("BUDATE", DELIMITER.getStringParaValue(UFDate.getDate(sFormat.parse(budate)).toString()));
		} catch (ParseException e) {
			throw new BusinessException(e.getMessage() ,e);
		}
		
		
		if (busitype.equals(ZtwVoucherConstant.BT_DELIHEADCORP.getValue())) {
			paras.put("ABSTRACTS", "'��['");
			paras.put("ABSTRACTE", "']�ʽ��ϻ���'");
		} else if (busitype.equals(ZtwVoucherConstant.BT_DELIBODYCORP.getValue())) {
			paras.put("ABSTRACTS", "'�ʽ��ϻ���['");
			paras.put("ABSTRACTE", "']'");
		} else if (busitype.equals(ZtwVoucherConstant.BT_ALLOHEADCORP.getValue())) {
			paras.put("ABSTRACTS", "'֧��['");
			paras.put("ABSTRACTE", "']�²��ʽ��'");
		} else if (busitype.equals(ZtwVoucherConstant.BT_ALLOBODYCORP.getValue())) {
			paras.put("ABSTRACTS", "'��['");
			paras.put("ABSTRACTE", "']�²��ʽ��'");
		}
		
		return st.buildSQL(new String[] { "VOUCHERID","BUKRS","BUKRSLEVEL","BUSITYPE","DOCUMENTSTYPE","DOCUMENTSNO",
				"ABSTRACT","BUDAT","CUSTCODE","CUSTNAME","WRBTR","EXBANK","EXBANKNUM","OPPEXBANK","OPPEXBANKNUM","OUTBANK",
				"OUTBANKNUM","INBANK","INBANKNUM" }, flexWheres, paras);
	}

	private Pair<String, String> getOrgPkLevel(String bukrs) throws BusinessException {
		if (bukrs.equals("0001")) bukrs = "100099";
		bukrs = "9999"+bukrs;
		BaseDAO dao = new BaseDAO();
		@SuppressWarnings("unchecked")
		Vector<Vector<Object>> data = 
				(Vector<Vector<Object>>)dao.executeQuery("select org_orgs.pk_org, org_orgs.pk_fatherorg from org_orgs where org_orgs.code='"+bukrs+"'", 
				new VectorProcessor());
		if (data == null || data.size() != 1)
			throw new BusinessException("��֯�������");
		String level = null;
		if (bukrs.equals("9999100099"))
			level = "1";
		else if (data.get(0).get(1).equals(ZtwVoucherConstant.PK_MONEYCENTER.getValue()))
			level = "2";
		else
			level = "3";
		
		return Pair.create(data.get(0).get(0).toString(), level);
	}

	@Override
	public VoucherQryInfo[] qryTMVoucherBillInfoVo(String para)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

}
