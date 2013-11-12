//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.12 时间 01:48:26 PM CST 
//


package nc.vo.ztwzj.sapitf.voucher.BillInfo;

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
 *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="errcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BillInfo" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BUKRSLEVEL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BUSITYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DOCUMENTSTYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="DOCUMENTSNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="ABSTRACT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CUSTCODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="CUSTNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="EXBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="EXBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="OPPEXBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="OPPEXBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="OUTBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="OUTBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="INBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="INBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "success",
    "errcode",
    "message",
    "billInfo"
})
@XmlRootElement(name = "BillInfoList")
public class BillInfoList {

    @XmlElement(required = true)
    protected String success;
    @XmlElement(required = true)
    protected String errcode;
    @XmlElement(required = true)
    protected String message;
    @XmlElement(name = "BillInfo")
    protected List<BillInfoList.BillInfo> billInfo;

    /**
     * 获取success属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSuccess() {
        return success;
    }

    /**
     * 设置success属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSuccess(String value) {
        this.success = value;
    }

    /**
     * 获取errcode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getErrcode() {
        return errcode;
    }

    /**
     * 设置errcode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setErrcode(String value) {
        this.errcode = value;
    }

    /**
     * 获取message属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置message属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMessage(String value) {
        this.message = value;
    }

    /**
     * Gets the value of the billInfo property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the billInfo property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBillInfo().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BillInfoList.BillInfo }
     * 
     * 
     */
    public List<BillInfoList.BillInfo> getBillInfo() {
        if (billInfo == null) {
            billInfo = new ArrayList<BillInfoList.BillInfo>();
        }
        return this.billInfo;
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
     *         &lt;element name="BUKRSLEVEL" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BUSITYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DOCUMENTSTYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="DOCUMENTSNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="ABSTRACT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CUSTCODE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="CUSTNAME" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="EXBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="EXBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="OPPEXBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="OPPEXBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="OUTBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="OUTBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="INBANK" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="INBANKNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "bukrslevel",
        "busitype",
        "documentstype",
        "documentsno",
        "_abstract",
        "budat",
        "custcode",
        "custname",
        "wrbtr",
        "exbank",
        "exbanknum",
        "oppexbank",
        "oppexbanknum",
        "outbank",
        "outbanknum",
        "inbank",
        "inbanknum"
    })
    public static class BillInfo {

        @XmlElement(name = "VOUCHERID", required = true)
        protected String voucherid;
        @XmlElement(name = "BUKRS", required = true)
        protected String bukrs;
        @XmlElement(name = "BUKRSLEVEL", required = true)
        protected String bukrslevel;
        @XmlElement(name = "BUSITYPE", required = true)
        protected String busitype;
        @XmlElement(name = "DOCUMENTSTYPE", required = true)
        protected String documentstype;
        @XmlElement(name = "DOCUMENTSNO", required = true)
        protected String documentsno;
        @XmlElement(name = "ABSTRACT", required = true)
        protected String _abstract;
        @XmlElement(name = "BUDAT", required = true)
        protected String budat;
        @XmlElement(name = "CUSTCODE", required = true)
        protected String custcode;
        @XmlElement(name = "CUSTNAME", required = true)
        protected String custname;
        @XmlElement(name = "WRBTR", required = true)
        protected String wrbtr;
        @XmlElement(name = "EXBANK", required = true)
        protected String exbank;
        @XmlElement(name = "EXBANKNUM", required = true)
        protected String exbanknum;
        @XmlElement(name = "OPPEXBANK", required = true)
        protected String oppexbank;
        @XmlElement(name = "OPPEXBANKNUM", required = true)
        protected String oppexbanknum;
        @XmlElement(name = "OUTBANK", required = true)
        protected String outbank;
        @XmlElement(name = "OUTBANKNUM", required = true)
        protected String outbanknum;
        @XmlElement(name = "INBANK", required = true)
        protected String inbank;
        @XmlElement(name = "INBANKNUM", required = true)
        protected String inbanknum;

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
         * 获取bukrslevel属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBUKRSLEVEL() {
            return bukrslevel;
        }

        /**
         * 设置bukrslevel属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBUKRSLEVEL(String value) {
            this.bukrslevel = value;
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
         * 获取abstract属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getABSTRACT() {
            return _abstract;
        }

        /**
         * 设置abstract属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setABSTRACT(String value) {
            this._abstract = value;
        }

        /**
         * 获取budat属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getBUDAT() {
            return budat;
        }

        /**
         * 设置budat属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setBUDAT(String value) {
            this.budat = value;
        }

        /**
         * 获取custcode属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCUSTCODE() {
            return custcode;
        }

        /**
         * 设置custcode属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCUSTCODE(String value) {
            this.custcode = value;
        }

        /**
         * 获取custname属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCUSTNAME() {
            return custname;
        }

        /**
         * 设置custname属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCUSTNAME(String value) {
            this.custname = value;
        }

        /**
         * 获取wrbtr属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getWRBTR() {
            return wrbtr;
        }

        /**
         * 设置wrbtr属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setWRBTR(String value) {
            this.wrbtr = value;
        }

        /**
         * 获取exbank属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEXBANK() {
            return exbank;
        }

        /**
         * 设置exbank属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEXBANK(String value) {
            this.exbank = value;
        }

        /**
         * 获取exbanknum属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getEXBANKNUM() {
            return exbanknum;
        }

        /**
         * 设置exbanknum属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setEXBANKNUM(String value) {
            this.exbanknum = value;
        }

        /**
         * 获取oppexbank属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOPPEXBANK() {
            return oppexbank;
        }

        /**
         * 设置oppexbank属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOPPEXBANK(String value) {
            this.oppexbank = value;
        }

        /**
         * 获取oppexbanknum属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOPPEXBANKNUM() {
            return oppexbanknum;
        }

        /**
         * 设置oppexbanknum属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOPPEXBANKNUM(String value) {
            this.oppexbanknum = value;
        }

        /**
         * 获取outbank属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOUTBANK() {
            return outbank;
        }

        /**
         * 设置outbank属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOUTBANK(String value) {
            this.outbank = value;
        }

        /**
         * 获取outbanknum属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOUTBANKNUM() {
            return outbanknum;
        }

        /**
         * 设置outbanknum属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOUTBANKNUM(String value) {
            this.outbanknum = value;
        }

        /**
         * 获取inbank属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getINBANK() {
            return inbank;
        }

        /**
         * 设置inbank属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setINBANK(String value) {
            this.inbank = value;
        }

        /**
         * 获取inbanknum属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getINBANKNUM() {
            return inbanknum;
        }

        /**
         * 设置inbanknum属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setINBANKNUM(String value) {
            this.inbanknum = value;
        }

    }

}
