//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.12 ʱ�� 01:48:26 PM CST 
//


package nc.vo.ztwzj.sapitf.voucher.BillInfo;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nc.vo.ztwzj.sapitf.voucher.BillInfo package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nc.vo.ztwzj.sapitf.voucher.BillInfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BillInfoList }
     * 
     */
    public BillInfoList createBillInfoList() {
        return new BillInfoList();
    }

    /**
     * Create an instance of {@link BillInfoList.BillInfo }
     * 
     */
    public BillInfoList.BillInfo createBillInfoListBillInfo() {
        return new BillInfoList.BillInfo();
    }

}
