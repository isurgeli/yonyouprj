package nc.ui.ztwzj.pub.test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import nc.bs.framework.common.NCLocator;
import nc.funcnode.ui.AbstractFunclet;
import nc.itf.ztwzj.mdmitf.IWS_NC_mdcsRecPushService;

public class TestClientUI extends AbstractFunclet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init() {
		JButton btn = new JButton("开始测试");
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				IWS_NC_mdcsRecPushService service = NCLocator.getInstance().lookup(IWS_NC_mdcsRecPushService.class);
				service.recvPushMdcsInfo("Vendor", getSuppXMLSample());
			}
		});
		
		add(btn);
	}
	
	private String getCustXMLSample(){
		StringBuffer sb = new StringBuffer();

		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<root>");
		sb.append("<mdtype>Customer</mdtype>");
		sb.append("<opttype>add</opttype>");
		sb.append("<data>");
		sb.append("<CustomerInfo>");
		sb.append("<custNo>10000090</custNo>");
		sb.append("<custName>陕西泰安金属构件有限公司2</custName>");
		sb.append("<isFinancial>true</isFinancial>");
		sb.append("<countryNo>CN</countryNo>");
		sb.append("<countryName>中国</countryName>");
		sb.append("<areaNo>H5</areaNo>");
		sb.append("<areaName>西北</areaName>");
		sb.append("<provinceNo>250</provinceNo>");
		sb.append("<provinceName>陕西</provinceName>");
		sb.append("<cityName>南宁市</cityName>");
		sb.append("<search>泰安金属</search>");
		sb.append("<tax>610723694932223</tax>");
		sb.append("<address></address>");
		sb.append("<postNo></postNo>");
		sb.append("<fax></fax>");
		sb.append("<email></email>");
		sb.append("<vatTel>0916-8220866</vatTel>");
		sb.append("<vatAddress>洋县洋华路102号</vatAddress>");
		sb.append("<eqpCustTypeNo>FZ</eqpCustTypeNo>");
		sb.append("<eqpCustTypeName>其他</eqpCustTypeName>");
		sb.append("<eqpCustProNo>GZ</eqpCustProNo>");
		sb.append("<eqpCustProName>其他</eqpCustProName>");
		sb.append("<custTypeNo>AZ</custTypeNo>");
		sb.append("<custTypeName>其他</custTypeName>");
		sb.append("<firmProNo>CZ</firmProNo>");
		sb.append("<firmProName>其他</firmProName>");
		sb.append("<custActGrpNo>Z001</custActGrpNo>");
		sb.append("<custActGrpName>国外客户</custActGrpName>");
		sb.append("<railInOutNo>E2</railInOutNo>");
		sb.append("<railInOutName>路外</railInOutName>");
		sb.append("<bankNo>2606051529200074572</bankNo>");
		sb.append("<bankName>工行洋县支行</bankName>");
		sb.append("</CustomerInfo>");
		sb.append("<CustAuthos>");
		sb.append("<CustAutho>");
		sb.append("<interUnitNo>2050</interUnitNo>");
		sb.append("<interUnitName>2050</interUnitName>");
		sb.append("</CustAutho>");
		sb.append("<CustAutho>");
		sb.append("<interUnitNo>2051</interUnitNo>");
		sb.append("<interUnitName>2051</interUnitName>");
		sb.append("</CustAutho>");
		sb.append("</CustAuthos>");
		sb.append("</data>");
		sb.append("</root>");
		
		return sb.toString();
	}
	
	private String getSuppXMLSample(){
		StringBuffer sb = new StringBuffer();
		
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<root>");
		sb.append("<mdtype>Vendor</mdtype>");
		sb.append("<opttype>add</opttype>");
		sb.append("<data>");
		sb.append("<VendorInfo>");
		sb.append("<vendNo>10000088</vendNo>");
		sb.append("<vendName>晶达光电股份有限公司</vendName>");
		sb.append("<isFinancial>false</isFinancial>");
		sb.append("<countryNo>TW</countryNo>");
		sb.append("<countryName>台湾</countryName>");
		sb.append("<areaNo>HZ</areaNo>");
		sb.append("<areaName>其他</areaName>");
		sb.append("<provinceNo></provinceNo>");
		sb.append("<provinceName></provinceName>");
		sb.append("<cityName></cityName>");
		sb.append("<search>晶达</search>");
		sb.append("<tax></tax>");
		sb.append("<tel>1</tel>");
		sb.append("<address>1</address>");
		sb.append("<postNo></postNo>");
		sb.append("<fax>1</fax>");
		sb.append("<email>1@1.com</email>");
		sb.append("<mobTel>1</mobTel>");
		sb.append("<eqpVendTypeNo>FZ</eqpVendTypeNo>");
		sb.append("<eqpVendTypeName>其他</eqpVendTypeName>");
		sb.append("<firmScalNo>DZ</firmScalNo>");
		sb.append("<firmScalName>其他</firmScalName>");
		sb.append("<vendTypeNo>B1</vendTypeNo>");
		sb.append("<vendTypeName>生产商</vendTypeName>");
		sb.append("<firmProNo>C3</firmProNo>");
		sb.append("<firmProName>外企</firmProName>");
		sb.append("<vendClassNo>Z001</vendClassNo>");
		sb.append("<vendClassName>国外供应商</vendClassName>");
		sb.append("<railInOutNo>E2</railInOutNo>");
		sb.append("<railInOutName>路外</railInOutName>");
		sb.append("<purchTypeNo>Z040</purchTypeNo>");
		sb.append("<purchTypeName>自采</purchTypeName>");
		sb.append("<bankNo>123123</bankNo>");
		sb.append("<bankName>工行金新支行</bankName>");
		sb.append("</VendorInfo>");
		sb.append("<VendAuthos>");
		sb.append("<VendAutho>");
		sb.append("<interUnitNo>2000</interUnitNo>");
		sb.append("<interUnitName>2000</interUnitName>");
		sb.append("</VendAutho>");
		sb.append("<VendAutho>");
		sb.append("</VendAutho>");
		sb.append("</VendAuthos>");
		sb.append("</data>");
		sb.append("</root>");
		
		return sb.toString();
	}
}