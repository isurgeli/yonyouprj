//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.01 ʱ�� 10:08:18 AM CST 
//


package nc.vo.ztwzj.sapitf.voucher;

import java.math.BigDecimal;
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
 *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BLART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="WAERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BKTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="XBLNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NUMPG" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="USNAM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Entries">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Entry" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="KOART" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="HKONT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *                             &lt;element name="ZUONR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SGTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RSTGR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZFBDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="VBUND" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="VALUT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BANKACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="DETAILID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
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
    "bukrs",
    "blart",
    "budat",
    "bldat",
    "waers",
    "bktxt",
    "xblnr",
    "numpg",
    "usnam",
    "voucherid",
    "entries"
})
@XmlRootElement(name = "Voucher")
public class Voucher {

    @XmlElement(name = "BUKRS", required = true)
    protected String bukrs;
    @XmlElement(name = "BLART", required = true)
    protected String blart;
    @XmlElement(name = "BUDAT", required = true)
    protected String budat;
    @XmlElement(name = "BLDAT", required = true)
    protected String bldat;
    @XmlElement(name = "WAERS", required = true)
    protected String waers;
    @XmlElement(name = "BKTXT", required = true)
    protected String bktxt;
    @XmlElement(name = "XBLNR", required = true)
    protected String xblnr;
    @XmlElement(name = "NUMPG")
    protected int numpg;
    @XmlElement(name = "USNAM", required = true)
    protected String usnam;
    @XmlElement(name = "VOUCHERID", required = true)
    protected String voucherid;
    @XmlElement(name = "Entries", required = true)
    protected Voucher.Entries entries;

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
     * ��ȡblart���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLART() {
        return blart;
    }

    /**
     * ����blart���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLART(String value) {
        this.blart = value;
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
     * ��ȡbldat���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBLDAT() {
        return bldat;
    }

    /**
     * ����bldat���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBLDAT(String value) {
        this.bldat = value;
    }

    /**
     * ��ȡwaers���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWAERS() {
        return waers;
    }

    /**
     * ����waers���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWAERS(String value) {
        this.waers = value;
    }

    /**
     * ��ȡbktxt���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBKTXT() {
        return bktxt;
    }

    /**
     * ����bktxt���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBKTXT(String value) {
        this.bktxt = value;
    }

    /**
     * ��ȡxblnr���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXBLNR() {
        return xblnr;
    }

    /**
     * ����xblnr���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXBLNR(String value) {
        this.xblnr = value;
    }

    /**
     * ��ȡnumpg���Ե�ֵ��
     * 
     */
    public int getNUMPG() {
        return numpg;
    }

    /**
     * ����numpg���Ե�ֵ��
     * 
     */
    public void setNUMPG(int value) {
        this.numpg = value;
    }

    /**
     * ��ȡusnam���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUSNAM() {
        return usnam;
    }

    /**
     * ����usnam���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUSNAM(String value) {
        this.usnam = value;
    }

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
     * ��ȡentries���Ե�ֵ��
     * 
     * @return
     *     possible object is
     *     {@link Voucher.Entries }
     *     
     */
    public Voucher.Entries getEntries() {
        return entries;
    }

    /**
     * ����entries���Ե�ֵ��
     * 
     * @param value
     *     allowed object is
     *     {@link Voucher.Entries }
     *     
     */
    public void setEntries(Voucher.Entries value) {
        this.entries = value;
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
     *         &lt;element name="Entry" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="KOART" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="HKONT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
     *                   &lt;element name="ZUONR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="SGTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="RSTGR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZFBDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="VBUND" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="VALUT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BANKACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="DETAILID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "entry"
    })
    public static class Entries {

        @XmlElement(name = "Entry")
        protected List<Voucher.Entries.Entry> entry;

        /**
         * Gets the value of the entry property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the entry property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getEntry().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Voucher.Entries.Entry }
         * 
         * 
         */
        public List<Voucher.Entries.Entry> getEntry() {
            if (entry == null) {
                entry = new ArrayList<Voucher.Entries.Entry>();
            }
            return this.entry;
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
         *         &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="KOART" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="HKONT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
         *         &lt;element name="ZUONR" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="SGTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="RSTGR" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZFBDT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="VBUND" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="VALUT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BANKACCOUNT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="VOUCHERID" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="DETAILID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "shkzg",
            "koart",
            "hkont",
            "wrbtr",
            "zuonr",
            "sgtxt",
            "rstgr",
            "zfbdt",
            "vbund",
            "valut",
            "bankaccount",
            "voucherid",
            "detailid"
        })
        public static class Entry {

            @XmlElement(name = "SHKZG", required = true)
            protected String shkzg;
            @XmlElement(name = "KOART", required = true)
            protected String koart;
            @XmlElement(name = "HKONT", required = true)
            protected String hkont;
            @XmlElement(name = "WRBTR", required = true)
            protected BigDecimal wrbtr;
            @XmlElement(name = "ZUONR", required = true)
            protected String zuonr;
            @XmlElement(name = "SGTXT", required = true)
            protected String sgtxt;
            @XmlElement(name = "RSTGR", required = true)
            protected String rstgr;
            @XmlElement(name = "ZFBDT", required = true)
            protected String zfbdt;
            @XmlElement(name = "VBUND", required = true)
            protected String vbund;
            @XmlElement(name = "VALUT", required = true)
            protected String valut;
            @XmlElement(name = "BANKACCOUNT", required = true)
            protected String bankaccount;
            @XmlElement(name = "VOUCHERID", required = true)
            protected String voucherid;
            @XmlElement(name = "DETAILID", required = true)
            protected String detailid;

            /**
             * ��ȡshkzg���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSHKZG() {
                return shkzg;
            }

            /**
             * ����shkzg���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSHKZG(String value) {
                this.shkzg = value;
            }

            /**
             * ��ȡkoart���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKOART() {
                return koart;
            }

            /**
             * ����koart���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKOART(String value) {
                this.koart = value;
            }

            /**
             * ��ȡhkont���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getHKONT() {
                return hkont;
            }

            /**
             * ����hkont���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setHKONT(String value) {
                this.hkont = value;
            }

            /**
             * ��ȡwrbtr���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link BigDecimal }
             *     
             */
            public BigDecimal getWRBTR() {
                return wrbtr;
            }

            /**
             * ����wrbtr���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link BigDecimal }
             *     
             */
            public void setWRBTR(BigDecimal value) {
                this.wrbtr = value;
            }

            /**
             * ��ȡzuonr���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZUONR() {
                return zuonr;
            }

            /**
             * ����zuonr���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZUONR(String value) {
                this.zuonr = value;
            }

            /**
             * ��ȡsgtxt���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSGTXT() {
                return sgtxt;
            }

            /**
             * ����sgtxt���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSGTXT(String value) {
                this.sgtxt = value;
            }

            /**
             * ��ȡrstgr���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRSTGR() {
                return rstgr;
            }

            /**
             * ����rstgr���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRSTGR(String value) {
                this.rstgr = value;
            }

            /**
             * ��ȡzfbdt���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZFBDT() {
                return zfbdt;
            }

            /**
             * ����zfbdt���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZFBDT(String value) {
                this.zfbdt = value;
            }

            /**
             * ��ȡvbund���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVBUND() {
                return vbund;
            }

            /**
             * ����vbund���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVBUND(String value) {
                this.vbund = value;
            }

            /**
             * ��ȡvalut���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getVALUT() {
                return valut;
            }

            /**
             * ����valut���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setVALUT(String value) {
                this.valut = value;
            }

            /**
             * ��ȡbankaccount���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getBANKACCOUNT() {
                return bankaccount;
            }

            /**
             * ����bankaccount���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setBANKACCOUNT(String value) {
                this.bankaccount = value;
            }

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
             * ��ȡdetailid���Ե�ֵ��
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getDETAILID() {
                return detailid;
            }

            /**
             * ����detailid���Ե�ֵ��
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setDETAILID(String value) {
                this.detailid = value;
            }

        }

    }

}
