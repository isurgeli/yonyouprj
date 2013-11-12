package nc.bs.ztwzj.mdmitf.test;

import junit.framework.TestCase;
import nc.bs.framework.common.RuntimeEnv;
import nc.bs.ztwzj.cust_supp.CustomerProcessor;
import nc.impl.ztwzj.mdmitf.WS_NC_mdcsRecPushServiceImpl;

import org.antlr.stringtemplate.StringTemplate;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

public class CustomerSupplierTest extends TestCase {
	public CustomerSupplierTest(String name) {
		super(name);
	}
	
	public void testST(){
		StringTemplate hello = new StringTemplate("Hello, $name$", DefaultTemplateLexer.class);
		hello.setAttribute("name", "World $two$");
		StringTemplate hello1 = new StringTemplate(hello.toString(), DefaultTemplateLexer.class);
		hello1.setAttribute("two", "OK");
		System.out.println(hello1.toString());
	}
	
	public void testCustomer(){
		RuntimeEnv.getInstance().setProperty("SERVICELOOKUP_URL", "http://127.0.0.1:80/ServiceLookuperServlet");
		RuntimeEnv.getInstance().setProperty("CLIENT_COMMUNICATOR", "nc.bs.framework.comn.cli.JavaURLCommunicator");
		RuntimeEnv.getInstance().setProperty("SERVICEDISPATCH_URL", "http://127.0.0.1:80/ServiceDispatcherServlet");
		
		CustomerProcessor custP = new CustomerProcessor();
		//custP.processCustomer("21111111", "���Կͻ�1", false);
	}

	public void testJaxbCustomer() {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<root>");
		sb.append("<mdtype>Customer</mdtype>");
		sb.append("<opttype>add</opttype>");
		sb.append("<data>");
		sb.append("	<CustomerInfo>");
		sb.append("		<custNo>10000088</custNo>");
		sb.append("		<custName>����̩�������������޹�˾</custName>");
		sb.append("		<isFinancial>true</isFinancial>");
		sb.append("		<countryNo>CN</countryNo>");
		sb.append("		<countryName>�й�</countryName>");
		sb.append("		<areaNo>H5</areaNo>");
		sb.append("		<areaName>����</areaName>");
		sb.append("		<provinceNo>250</provinceNo>");
		sb.append("		<provinceName>����</provinceName>");
		sb.append("		<cityName>������</cityName>");
		sb.append("		<search>̩������</search>");
		sb.append("		<tax>610723694932223</tax>");
		sb.append("		<address></address>");
		sb.append("		<postNo></postNo>");
		sb.append("		<fax></fax>");
		sb.append("		<email></email>");
		sb.append("		<vatTel>0916-8220866</vatTel>");
		sb.append("		<vatAddress>������·102��</vatAddress>");
		sb.append("		<eqpCustTypeNo>FZ</eqpCustTypeNo>");
		sb.append("		<eqpCustTypeName>����</eqpCustTypeName>");
		sb.append("		<eqpCustProNo>GZ</eqpCustProNo>");
		sb.append("		<eqpCustProName>����</eqpCustProName>");
		sb.append("		<custTypeNo>AZ</custTypeNo>");
		sb.append("		<custTypeName>����</custTypeName>");
		sb.append("		<firmProNo>CZ</firmProNo>");
		sb.append("		<firmProName>����</firmProName>");
		sb.append("		<custActGrpNo>Z001</custActGrpNo>");
		sb.append("		<custActGrpName>����ͻ�</custActGrpName>");
		sb.append("		<railInOutNo>E2</railInOutNo>");
		sb.append("		<railInOutName>·��</railInOutName>");
		sb.append("		<bankNo>2606051529200074572</bankNo>");
		sb.append("		<bankName>��������֧��</bankName>");
		sb.append("	</CustomerInfo>");
		sb.append("	<CustAuthos>");
		sb.append("		<CustAutho>");
		sb.append("			<interUnitNo>2050</interUnitNo>");
		sb.append("			<interUnitName>2050</interUnitName>");
		sb.append("		</CustAutho>");
		sb.append("		<CustAutho>");
		sb.append("			<interUnitNo>2051</interUnitNo>");
		sb.append("			<interUnitName>2051</interUnitName>");
		sb.append("		</CustAutho>");
		sb.append("	</CustAuthos>");
		sb.append("</data>");
		sb.append("</root>");

		WS_NC_mdcsRecPushServiceImpl ws = new WS_NC_mdcsRecPushServiceImpl();
		ws.recvPushMdcsInfo("Customer", sb.toString());
	}
}