//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.28 ʱ�� 08:58:29 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.BankReceiptQry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


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
 *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZHB_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUMON" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "bumon"
})
@XmlRootElement(name = "BankReceiptQryPara")
public class BankReceiptQryPara {

    @XmlElement(name = "BUKRS", required = true)
    protected String bukrs;
    @XmlElement(name = "ZHB_BANKN", required = true)
    protected String zhbbankn;
    @XmlElement(name = "BUMON", required = true)
    protected String bumon;

    /**
     * ��ȡbukrs���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUKRS() {
        return bukrs;
    }

    /**
     * ����bukrs���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUKRS(String value) {
        this.bukrs = value;
    }

    /**
     * ��ȡzhbbankn���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZHBBANKN() {
        return zhbbankn;
    }

    /**
     * ����zhbbankn���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZHBBANKN(String value) {
        this.zhbbankn = value;
    }

    /**
     * ��ȡbumon���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUMON() {
        return bumon;
    }

    /**
     * ����bumon���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUMON(String value) {
        this.bumon = value;
    }

}
