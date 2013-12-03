//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.12.02 ʱ�� 10:23:08 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.BankReceiptQry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


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
    protected short bukrs;
    @XmlElement(name = "ZHB_BANKN")
    protected int zhbbankn;
    @XmlElement(name = "BUDAT", required = true)
    protected BankReceiptQryPara.BUDAT budat;

    /**
     * ��ȡbukrs���Ե�ֵ��
     * 
     */
    public short getBUKRS() {
        return bukrs;
    }

    /**
     * ����bukrs���Ե�ֵ��
     * 
     */
    public void setBUKRS(short value) {
        this.bukrs = value;
    }

    /**
     * ��ȡzhbbankn���Ե�ֵ��
     * 
     */
    public int getZHBBANKN() {
        return zhbbankn;
    }

    /**
     * ����zhbbankn���Ե�ֵ��
     * 
     */
    public void setZHBBANKN(int value) {
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
    public BankReceiptQryPara.BUDAT getBUDAT() {
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
    public void setBUDAT(BankReceiptQryPara.BUDAT value) {
        this.budat = value;
    }


    /**
     * <p>anonymous complex type�� Java �ࡣ
     * 
     * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class BUDAT {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "low")
        protected String low;
        @XmlAttribute(name = "high")
        protected String high;
        @XmlAttribute(name = "sign")
        protected String sign;
        @XmlAttribute(name = "option")
        protected String option;

        /**
         * ��ȡvalue���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * ����value���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * ��ȡlow���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLow() {
            return low;
        }

        /**
         * ����low���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLow(String value) {
            this.low = value;
        }

        /**
         * ��ȡhigh���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHigh() {
            return high;
        }

        /**
         * ����high���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHigh(String value) {
            this.high = value;
        }

        /**
         * ��ȡsign���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSign() {
            return sign;
        }

        /**
         * ����sign���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSign(String value) {
            this.sign = value;
        }

        /**
         * ��ȡoption���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOption() {
            return option;
        }

        /**
         * ����option���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOption(String value) {
            this.option = value;
        }

    }

}
