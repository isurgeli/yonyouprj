//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.28 ʱ�� 08:57:55 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.BankReceiptInfo;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nc.vo.ztwzj.sapitf.bill.BankReceiptInfo package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nc.vo.ztwzj.sapitf.bill.BankReceiptInfo
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BankReceiptQryRet }
     * 
     */
    public BankReceiptQryRet createBankReceiptQryRet() {
        return new BankReceiptQryRet();
    }

    /**
     * Create an instance of {@link BankReceiptQryRet.BankReceiptList }
     * 
     */
    public BankReceiptQryRet.BankReceiptList createBankReceiptQryRetBankReceiptList() {
        return new BankReceiptQryRet.BankReceiptList();
    }

    /**
     * Create an instance of {@link BankReceiptQryRet.SystemInfo }
     * 
     */
    public BankReceiptQryRet.SystemInfo createBankReceiptQryRetSystemInfo() {
        return new BankReceiptQryRet.SystemInfo();
    }

    /**
     * Create an instance of {@link BankReceiptQryRet.BankReceiptList.BankReceiptInfo }
     * 
     */
    public BankReceiptQryRet.BankReceiptList.BankReceiptInfo createBankReceiptQryRetBankReceiptListBankReceiptInfo() {
        return new BankReceiptQryRet.BankReceiptList.BankReceiptInfo();
    }

}
