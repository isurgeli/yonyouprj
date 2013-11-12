//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.11 时间 04:14:52 PM CST 
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
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
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
         * 获取voucherid属性的值。
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
         * 设置voucherid属性的值。
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
         * 获取bukrs属性的值。
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
         * 设置bukrs属性的值。
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
         * 获取busitype属性的值。
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
         * 设置busitype属性的值。
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
         * 获取documentstype属性的值。
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
         * 设置documentstype属性的值。
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
         * 获取documentsno属性的值。
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
         * 设置documentsno属性的值。
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
         * 获取type属性的值。
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
         * 设置type属性的值。
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
