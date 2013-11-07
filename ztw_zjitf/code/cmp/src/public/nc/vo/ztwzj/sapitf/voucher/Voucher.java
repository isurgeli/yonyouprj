//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.01 时间 10:08:18 AM CST 
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
 * <p>anonymous complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
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
     * 获取blart属性的值。
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
     * 设置blart属性的值。
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
     * 获取bldat属性的值。
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
     * 设置bldat属性的值。
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
     * 获取waers属性的值。
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
     * 设置waers属性的值。
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
     * 获取bktxt属性的值。
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
     * 设置bktxt属性的值。
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
     * 获取xblnr属性的值。
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
     * 设置xblnr属性的值。
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
     * 获取numpg属性的值。
     * 
     */
    public int getNUMPG() {
        return numpg;
    }

    /**
     * 设置numpg属性的值。
     * 
     */
    public void setNUMPG(int value) {
        this.numpg = value;
    }

    /**
     * 获取usnam属性的值。
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
     * 设置usnam属性的值。
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
     * 获取entries属性的值。
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
     * 设置entries属性的值。
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
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
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
         * <p>anonymous complex type的 Java 类。
         * 
         * <p>以下模式片段指定包含在此类中的预期内容。
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
             * 获取shkzg属性的值。
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
             * 设置shkzg属性的值。
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
             * 获取koart属性的值。
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
             * 设置koart属性的值。
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
             * 获取hkont属性的值。
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
             * 设置hkont属性的值。
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
             * 获取wrbtr属性的值。
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
             * 设置wrbtr属性的值。
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
             * 获取zuonr属性的值。
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
             * 设置zuonr属性的值。
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
             * 获取sgtxt属性的值。
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
             * 设置sgtxt属性的值。
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
             * 获取rstgr属性的值。
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
             * 设置rstgr属性的值。
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
             * 获取zfbdt属性的值。
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
             * 设置zfbdt属性的值。
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
             * 获取vbund属性的值。
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
             * 设置vbund属性的值。
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
             * 获取valut属性的值。
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
             * 设置valut属性的值。
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
             * 获取bankaccount属性的值。
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
             * 设置bankaccount属性的值。
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
             * 获取detailid属性的值。
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
             * 设置detailid属性的值。
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
