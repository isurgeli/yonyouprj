//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.12 ʱ�� 01:48:26 PM CST 
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
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡsuccess���Ե�ֵ��
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
     * ����success���Ե�ֵ��
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
     * ��ȡerrcode���Ե�ֵ��
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
     * ����errcode���Ե�ֵ��
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
     * ��ȡmessage���Ե�ֵ��
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
     * ����message���Ե�ֵ��
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
         * ��ȡbukrslevel���Ե�ֵ��
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
         * ����bukrslevel���Ե�ֵ��
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
         * ��ȡabstract���Ե�ֵ��
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
         * ����abstract���Ե�ֵ��
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
         * ��ȡbudat���Ե�ֵ��
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
         * ����budat���Ե�ֵ��
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
         * ��ȡcustcode���Ե�ֵ��
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
         * ����custcode���Ե�ֵ��
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
         * ��ȡcustname���Ե�ֵ��
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
         * ����custname���Ե�ֵ��
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
         * ��ȡwrbtr���Ե�ֵ��
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
         * ����wrbtr���Ե�ֵ��
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
         * ��ȡexbank���Ե�ֵ��
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
         * ����exbank���Ե�ֵ��
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
         * ��ȡexbanknum���Ե�ֵ��
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
         * ����exbanknum���Ե�ֵ��
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
         * ��ȡoppexbank���Ե�ֵ��
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
         * ����oppexbank���Ե�ֵ��
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
         * ��ȡoppexbanknum���Ե�ֵ��
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
         * ����oppexbanknum���Ե�ֵ��
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
         * ��ȡoutbank���Ե�ֵ��
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
         * ����outbank���Ե�ֵ��
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
         * ��ȡoutbanknum���Ե�ֵ��
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
         * ����outbanknum���Ե�ֵ��
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
         * ��ȡinbank���Ե�ֵ��
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
         * ����inbank���Ե�ֵ��
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
         * ��ȡinbanknum���Ե�ֵ��
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
         * ����inbanknum���Ե�ֵ��
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
