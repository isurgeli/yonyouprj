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
		//custP.processCustomer("21111111", "测试客户1", false);
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
		sb.append("		<custName>陕西泰安金属构件有限公司</custName>");
		sb.append("		<isFinancial>true</isFinancial>");
		sb.append("		<countryNo>CN</countryNo>");
		sb.append("		<countryName>中国</countryName>");
		sb.append("		<areaNo>H5</areaNo>");
		sb.append("		<areaName>西北</areaName>");
		sb.append("		<provinceNo>250</provinceNo>");
		sb.append("		<provinceName>陕西</provinceName>");
		sb.append("		<cityName>南宁市</cityName>");
		sb.append("		<search>泰安金属</search>");
		sb.append("		<tax>610723694932223</tax>");
		sb.append("		<address></address>");
		sb.append("		<postNo></postNo>");
		sb.append("		<fax></fax>");
		sb.append("		<email></email>");
		sb.append("		<vatTel>0916-8220866</vatTel>");
		sb.append("		<vatAddress>洋县洋华路102号</vatAddress>");
		sb.append("		<eqpCustTypeNo>FZ</eqpCustTypeNo>");
		sb.append("		<eqpCustTypeName>其他</eqpCustTypeName>");
		sb.append("		<eqpCustProNo>GZ</eqpCustProNo>");
		sb.append("		<eqpCustProName>其他</eqpCustProName>");
		sb.append("		<custTypeNo>AZ</custTypeNo>");
		sb.append("		<custTypeName>其他</custTypeName>");
		sb.append("		<firmProNo>CZ</firmProNo>");
		sb.append("		<firmProName>其他</firmProName>");
		sb.append("		<custActGrpNo>Z001</custActGrpNo>");
		sb.append("		<custActGrpName>国外客户</custActGrpName>");
		sb.append("		<railInOutNo>E2</railInOutNo>");
		sb.append("		<railInOutName>路外</railInOutName>");
		sb.append("		<bankNo>2606051529200074572</bankNo>");
		sb.append("		<bankName>工行洋县支行</bankName>");
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