//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.28 ʱ�� 08:59:58 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.RecvBillQry;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nc.vo.ztwzj.sapitf.bill.RecvBillQry package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nc.vo.ztwzj.sapitf.bill.RecvBillQry
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RecvBillQryPara }
     * 
     */
    public RecvBillQryPara createRecvBillQryPara() {
        return new RecvBillQryPara();
    }

    /**
     * Create an instance of {@link RecvBillQryPara.KUNNRS }
     * 
     */
    public RecvBillQryPara.KUNNRS createRecvBillQryParaKUNNRS() {
        return new RecvBillQryPara.KUNNRS();
    }

    /**
     * Create an instance of {@link RecvBillQryPara.RECEIVENUMS }
     * 
     */
    public RecvBillQryPara.RECEIVENUMS createRecvBillQryParaRECEIVENUMS() {
        return new RecvBillQryPara.RECEIVENUMS();
    }

    /**
     * Create an instance of {@link RecvBillQryPara.ZSKRQS }
     * 
     */
    public RecvBillQryPara.ZSKRQS createRecvBillQryParaZSKRQS() {
        return new RecvBillQryPara.ZSKRQS();
    }

    /**
     * Create an instance of {@link RecvBillQryPara.KUNNRS.KUNNR }
     * 
     */
    public RecvBillQryPara.SAPRangePara createRecvBillQryParaKUNNRSKUNNR() {
        return new RecvBillQryPara.SAPRangePara();
    }

    /**
     * Create an instance of {@link RecvBillQryPara.RECEIVENUMS.RECEIVENUM }
     * 
     */
    public RecvBillQryPara.SAPRangePara createRecvBillQryParaRECEIVENUMSRECEIVENUM() {
        return new RecvBillQryPara.SAPRangePara();
    }

    /**
     * Create an instance of {@link RecvBillQryPara.ZSKRQS.ZSKRQ }
     * 
     */
    public RecvBillQryPara.SAPRangePara createRecvBillQryParaZSKRQSZSKRQ() {
        return new RecvBillQryPara.SAPRangePara();
    }

}
