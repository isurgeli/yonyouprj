//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.11 ʱ�� 04:14:52 PM CST 
//


package nc.vo.ztwzj.sapitf.voucher.VoucherResult;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="VoucherResultInfo" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BUSITYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DOCUMENTSTYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DOCUMENTSNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
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
    "voucherResultInfo"
})
@XmlRootElement(name = "VoucherResultInfoList")
public class VoucherResultInfoList {

    @XmlElement(name = "VoucherResultInfo")
    protected List<VoucherResultInfoList.VoucherResultInfo> voucherResultInfo;

    /**
     * Gets the value of the voucherResultInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the voucherResultInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVoucherResultInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VoucherResultInfoList.VoucherResultInfo }
     * 
     * 
     */
    public List<VoucherResultInfoList.VoucherResultInfo> getVoucherResultInfo() {
        if (voucherResultInfo == null) {
            voucherResultInfo = new ArrayList<VoucherResultInfoList.VoucherResultInfo>();
        }
        return this.voucherResultInfo;
    }


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
     *         &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BUSITYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DOCUMENTSTYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DOCUMENTSNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="TYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "voucherid",
        "bukrs",
        "busitype",
        "documentstype",
        "documentsno",
        "type"
    })
    public static class VoucherResultInfo {

        @XmlElement(name = "VOUCHERID", required = true)
        protected String voucherid;
        @XmlElement(name = "BUKRS", required = true)
        protected String bukrs;
        @XmlElement(name = "BUSITYPE", required = true)
        protected String busitype;
        @XmlElement(name = "DOCUMENTSTYPE", required = true)
        protected String documentstype;
        @XmlElement(name = "DOCUMENTSNO", required = true)
        protected String documentsno;
        @XmlElement(name = "TYPE", required = true)
        protected String type;

        /**
         * ��ȡvoucherid���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getVOUCHERID() {
            return voucherid;
        }

        /**
         * ����voucherid���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setVOUCHERID(String value) {
            this.voucherid = value;
        }

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
         * ��ȡbusitype���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBUSITYPE() {
            return busitype;
        }

        /**
         * ����busitype���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBUSITYPE(String value) {
            this.busitype = value;
        }

        /**
         * ��ȡdocumentstype���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDOCUMENTSTYPE() {
            return documentstype;
        }

        /**
         * ����documentstype���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDOCUMENTSTYPE(String value) {
            this.documentstype = value;
        }

        /**
         * ��ȡdocumentsno���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDOCUMENTSNO() {
            return documentsno;
        }

        /**
         * ����documentsno���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDOCUMENTSNO(String value) {
            this.documentsno = value;
        }

        /**
         * ��ȡtype���Ե�ֵ��
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTYPE() {
            return type;
        }

        /**
         * ����type���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTYPE(String value) {
            this.type = value;
        }

    }

}
