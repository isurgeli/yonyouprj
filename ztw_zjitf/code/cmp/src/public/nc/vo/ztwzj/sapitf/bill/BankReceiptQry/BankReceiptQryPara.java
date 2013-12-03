//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.12.02 ʱ�� 10:23:08 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.BankReceiptQry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nc.vo.ztwzj.sapitf.bill.RecvBillQry.RecvBillQryPara;


/**
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ZHB_BANKN" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BUDAT">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bukrs",
    "zhbbankn",
    "budat"
})
@XmlRootElement(name = "BankReceiptQryPara")
public class BankReceiptQryPara {

    @XmlElement(name = "BUKRS")
    protected String bukrs;
    @XmlElement(name = "ZHB_BANKN")
    protected String zhbbankn;
    @XmlElement(name = "BUDAT", required = true)
    protected RecvBillQryPara.SAPRangePara budat;

    /**
     * ��ȡbukrs���Ե�ֵ��
     * 
     */
    public String getBUKRS() {
        return bukrs;
    }

    /**
     * ����bukrs���Ե�ֵ��
     * 
     */
    public void setBUKRS(String value) {
        this.bukrs = value;
    }

    /**
     * ��ȡzhbbankn���Ե�ֵ��
     * 
     */
    public String getZHBBANKN() {
        return zhbbankn;
    }

    /**
     * ����zhbbankn���Ե�ֵ��
     * 
     */
    public void setZHBBANKN(String value) {
        this.zhbbankn = value;
    }

    /**
     * ��ȡbudat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link BankReceiptQryPara.BUDAT }
     *     
     */
    public RecvBillQryPara.SAPRangePara getBUDAT() {
        return budat;
    }

    /**
     * ����budat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link BankReceiptQryPara.BUDAT }
     *     
     */
    public void setBUDAT(RecvBillQryPara.SAPRangePara value) {
        this.budat = value;
    }
}
