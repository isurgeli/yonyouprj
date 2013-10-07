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
		JButton btn = new JButton("��ʼ����");
		
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
		sb.append("<custName>����̩�������������޹�˾2</custName>");
		sb.append("<isFinancial>true</isFinancial>");
		sb.append("<countryNo>CN</countryNo>");
		sb.append("<countryName>�й�</countryName>");
		sb.append("<areaNo>H5</areaNo>");
		sb.append("<areaName>����</areaName>");
		sb.append("<provinceNo>250</provinceNo>");
		sb.append("<provinceName>����</provinceName>");
		sb.append("<cityName>������</cityName>");
		sb.append("<search>̩������</search>");
		sb.append("<tax>610723694932223</tax>");
		sb.append("<address></address>");
		sb.append("<postNo></postNo>");
		sb.append("<fax></fax>");
		sb.append("<email></email>");
		sb.append("<vatTel>0916-8220866</vatTel>");
		sb.append("<vatAddress>������·102��</vatAddress>");
		sb.append("<eqpCustTypeNo>FZ</eqpCustTypeNo>");
		sb.append("<eqpCustTypeName>����</eqpCustTypeName>");
		sb.append("<eqpCustProNo>GZ</eqpCustProNo>");
		sb.append("<eqpCustProName>����</eqpCustProName>");
		sb.append("<custTypeNo>AZ</custTypeNo>");
		sb.append("<custTypeName>����</custTypeName>");
		sb.append("<firmProNo>CZ</firmProNo>");
		sb.append("<firmProName>����</firmProName>");
		sb.append("<custActGrpNo>Z001</custActGrpNo>");
		sb.append("<custActGrpName>����ͻ�</custActGrpName>");
		sb.append("<railInOutNo>E2</railInOutNo>");
		sb.append("<railInOutName>·��</railInOutName>");
		sb.append("<bankNo>2606051529200074572</bankNo>");
		sb.append("<bankName>��������֧��</bankName>");
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
		sb.append("<vendName>������ɷ����޹�˾</vendName>");
		sb.append("<isFinancial>false</isFinancial>");
		sb.append("<countryNo>TW</countryNo>");
		sb.append("<countryName>̨��</countryName>");
		sb.append("<areaNo>HZ</areaNo>");
		sb.append("<areaName>����</areaName>");
		sb.append("<provinceNo></provinceNo>");
		sb.append("<provinceName></provinceName>");
		sb.append("<cityName></cityName>");
		sb.append("<search>����</search>");
		sb.append("<tax></tax>");
		sb.append("<tel>1</tel>");
		sb.append("<address>1</address>");
		sb.append("<postNo></postNo>");
		sb.append("<fax>1</fax>");
		sb.append("<email>1@1.com</email>");
		sb.append("<mobTel>1</mobTel>");
		sb.append("<eqpVendTypeNo>FZ</eqpVendTypeNo>");
		sb.append("<eqpVendTypeName>����</eqpVendTypeName>");
		sb.append("<firmScalNo>DZ</firmScalNo>");
		sb.append("<firmScalName>����</firmScalName>");
		sb.append("<vendTypeNo>B1</vendTypeNo>");
		sb.append("<vendTypeName>������</vendTypeName>");
		sb.append("<firmProNo>C3</firmProNo>");
		sb.append("<firmProName>����</firmProName>");
		sb.append("<vendClassNo>Z001</vendClassNo>");
		sb.append("<vendClassName>���⹩Ӧ��</vendClassName>");
		sb.append("<railInOutNo>E2</railInOutNo>");
		sb.append("<railInOutName>·��</railInOutName>");
		sb.append("<purchTypeNo>Z040</purchTypeNo>");
		sb.append("<purchTypeName>�Բ�</purchTypeName>");
		sb.append("<bankNo>123123</bankNo>");
		sb.append("<bankName>���н���֧��</bankName>");
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