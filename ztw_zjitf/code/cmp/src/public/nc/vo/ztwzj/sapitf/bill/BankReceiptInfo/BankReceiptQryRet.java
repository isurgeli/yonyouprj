//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.28 ʱ�� 08:57:55 AM CST 
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
 * <p>anonymous complex type�� Java �ࡣ
 * 
 * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
     * ��ȡsystemInfo���Ե�ֵ��
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
     * ����systemInfo���Ե�ֵ��
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
     * ��ȡbankReceiptList���Ե�ֵ��
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
     * ����bankReceiptList���Ե�ֵ��
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
     * <p>anonymous complex type�� Java �ࡣ
     * 
     * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
         * ��ȡcount���Ե�ֵ��
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
         * ����count���Ե�ֵ��
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
         * ��ȡtotalDebit���Ե�ֵ��
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
         * ����totalDebit���Ե�ֵ��
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
         * ��ȡtotalCredit���Ե�ֵ��
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
         * ����totalCredit���Ե�ֵ��
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
         * <p>anonymous complex type�� Java �ࡣ
         * 
         * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
             * ��ȡzhbbanms���Ե�ֵ��
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
             * ����zhbbanms���Ե�ֵ��
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
             * ��ȡunitn���Ե�ֵ��
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
             * ����unitn���Ե�ֵ��
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
             * ��ȡexnum���Ե�ֵ��
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
             * ����exnum���Ե�ֵ��
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
             * ��ȡuseage���Ե�ֵ��
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
             * ����useage���Ե�ֵ��
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

    }

}
