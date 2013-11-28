//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.28 时间 08:57:55 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.BankReceiptInfo;

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
 *         &lt;element name="SystemInfo">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="errcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="BankReceiptList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TotalDebit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="TotalCredit" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="BankReceiptInfo" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZHB_BANMS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZHB_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="UNITN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="EXNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="USEAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "systemInfo",
    "bankReceiptList"
})
@XmlRootElement(name = "BankReceiptQryRet")
public class BankReceiptQryRet {

    @XmlElement(name = "SystemInfo", required = true)
    protected BankReceiptQryRet.SystemInfo systemInfo;
    @XmlElement(name = "BankReceiptList", required = true)
    protected BankReceiptQryRet.BankReceiptList bankReceiptList;

    /**
     * 获取systemInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BankReceiptQryRet.SystemInfo }
     *     
     */
    public BankReceiptQryRet.SystemInfo getSystemInfo() {
        return systemInfo;
    }

    /**
     * 设置systemInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BankReceiptQryRet.SystemInfo }
     *     
     */
    public void setSystemInfo(BankReceiptQryRet.SystemInfo value) {
        this.systemInfo = value;
    }

    /**
     * 获取bankReceiptList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BankReceiptQryRet.BankReceiptList }
     *     
     */
    public BankReceiptQryRet.BankReceiptList getBankReceiptList() {
        return bankReceiptList;
    }

    /**
     * 设置bankReceiptList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BankReceiptQryRet.BankReceiptList }
     *     
     */
    public void setBankReceiptList(BankReceiptQryRet.BankReceiptList value) {
        this.bankReceiptList = value;
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
     *         &lt;element name="Count" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="TotalDebit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="TotalCredit" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="BankReceiptInfo" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZHB_BANMS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZHB_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="UNITN" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="EXNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="USEAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "count",
        "totalDebit",
        "totalCredit",
        "bankReceiptInfo"
    })
    public static class BankReceiptList {

        @XmlElement(name = "Count", required = true)
        protected String count;
        @XmlElement(name = "TotalDebit", required = true)
        protected String totalDebit;
        @XmlElement(name = "TotalCredit", required = true)
        protected String totalCredit;
        @XmlElement(name = "BankReceiptInfo")
        protected List<BankReceiptQryRet.BankReceiptList.BankReceiptInfo> bankReceiptInfo;

        /**
         * 获取count属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCount() {
            return count;
        }

        /**
         * 设置count属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCount(String value) {
            this.count = value;
        }

        /**
         * 获取totalDebit属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTotalDebit() {
            return totalDebit;
        }

        /**
         * 设置totalDebit属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTotalDebit(String value) {
            this.totalDebit = value;
        }

        /**
         * 获取totalCredit属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTotalCredit() {
            return totalCredit;
        }

        /**
         * 设置totalCredit属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTotalCredit(String value) {
            this.totalCredit = value;
        }

        /**
         * Gets the value of the bankReceiptInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the bankReceiptInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBankReceiptInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link BankReceiptQryRet.BankReceiptList.BankReceiptInfo }
         * 
         * 
         */
        public List<BankReceiptQryRet.BankReceiptList.BankReceiptInfo> getBankReceiptInfo() {
            if (bankReceiptInfo == null) {
                bankReceiptInfo = new ArrayList<BankReceiptQryRet.BankReceiptList.BankReceiptInfo>();
            }
            return this.bankReceiptInfo;
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
         *         &lt;element name="BUDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZHB_BANMS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZHB_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="SHKZG" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="WRBTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="UNITN" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="EXNUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="USEAGE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "budat",
            "zhbbanms",
            "zhbbankn",
            "bukrs",
            "shkzg",
            "wrbtr",
            "unitn",
            "exnum",
            "useage"
        })
        public static class BankReceiptInfo {

            @XmlElement(name = "BUDAT", required = true)
            protected String budat;
            @XmlElement(name = "ZHB_BANMS", required = true)
            protected String zhbbanms;
            @XmlElement(name = "ZHB_BANKN", required = true)
            protected String zhbbankn;
            @XmlElement(name = "BUKRS", required = true)
            protected String bukrs;
            @XmlElement(name = "SHKZG", required = true)
            protected String shkzg;
            @XmlElement(name = "WRBTR", required = true)
            protected String wrbtr;
            @XmlElement(name = "UNITN", required = true)
            protected String unitn;
            @XmlElement(name = "EXNUM", required = true)
            protected String exnum;
            @XmlElement(name = "USEAGE", required = true)
            protected String useage;

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
             * 获取zhbbanms属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZHBBANMS() {
                return zhbbanms;
            }

            /**
             * 设置zhbbanms属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZHBBANMS(String value) {
                this.zhbbanms = value;
            }

            /**
             * 获取zhbbankn属性的值。
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
             * 设置zhbbankn属性的值。
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
             * 获取unitn属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUNITN() {
                return unitn;
            }

            /**
             * 设置unitn属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUNITN(String value) {
                this.unitn = value;
            }

            /**
             * 获取exnum属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getEXNUM() {
                return exnum;
            }

            /**
             * 设置exnum属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setEXNUM(String value) {
                this.exnum = value;
            }

            /**
             * 获取useage属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getUSEAGE() {
                return useage;
            }

            /**
             * 设置useage属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setUSEAGE(String value) {
                this.useage = value;
            }

        }

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
     *         &lt;element name="success" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="errcode" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="message" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "message"
    })
    public static class SystemInfo {

        @XmlElement(required = true)
        protected String success;
        @XmlElement(required = true)
        protected String errcode;
        @XmlElement(required = true)
        protected String message;

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

    }

}
