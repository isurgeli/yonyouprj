//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.28 时间 11:04:39 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.RecvBillInfo;

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
 *         &lt;element name="RecvBillList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RecvBillInfo" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZYEAR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZDJLX" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZSKFS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZLSCH" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZSKRQ" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="WAERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZSKJE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="STATU" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZKS_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="ZKS_BANMS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="BKTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="NUMPG" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="REMARK" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RECEIVE_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                             &lt;element name="RECBID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "recvBillList"
})
@XmlRootElement(name = "RecvBillQryRet")
public class RecvBillQryRet {

    @XmlElement(name = "SystemInfo", required = true)
    protected RecvBillQryRet.SystemInfo systemInfo;
    @XmlElement(name = "RecvBillList", required = true)
    protected RecvBillQryRet.RecvBillList recvBillList;

    /**
     * 获取systemInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RecvBillQryRet.SystemInfo }
     *     
     */
    public RecvBillQryRet.SystemInfo getSystemInfo() {
        return systemInfo;
    }

    /**
     * 设置systemInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RecvBillQryRet.SystemInfo }
     *     
     */
    public void setSystemInfo(RecvBillQryRet.SystemInfo value) {
        this.systemInfo = value;
    }

    /**
     * 获取recvBillList属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RecvBillQryRet.RecvBillList }
     *     
     */
    public RecvBillQryRet.RecvBillList getRecvBillList() {
        return recvBillList;
    }

    /**
     * 设置recvBillList属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RecvBillQryRet.RecvBillList }
     *     
     */
    public void setRecvBillList(RecvBillQryRet.RecvBillList value) {
        this.recvBillList = value;
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
     *         &lt;element name="RecvBillInfo" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZYEAR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZDJLX" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZSKFS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZLSCH" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZSKRQ" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="WAERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZSKJE" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="STATU" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZKS_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="ZKS_BANMS" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="BKTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="NUMPG" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="REMARK" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="RECEIVE_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                   &lt;element name="RECBID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "recvBillInfo"
    })
    public static class RecvBillList {

        @XmlElement(name = "RecvBillInfo")
        protected List<RecvBillQryRet.RecvBillList.RecvBillInfo> recvBillInfo;

        /**
         * Gets the value of the recvBillInfo property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the recvBillInfo property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRecvBillInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RecvBillQryRet.RecvBillList.RecvBillInfo }
         * 
         * 
         */
        public List<RecvBillQryRet.RecvBillList.RecvBillInfo> getRecvBillInfo() {
            if (recvBillInfo == null) {
                recvBillInfo = new ArrayList<RecvBillQryRet.RecvBillList.RecvBillInfo>();
            }
            return this.recvBillInfo;
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
         *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZYEAR" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZDJLX" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZSKFS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZLSCH" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZSKRQ" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BLDAT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="WAERS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZSKJE" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="KUNNR" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="STATU" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZKS_BANKN" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="ZKS_BANMS" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="BKTXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="NUMPG" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="REMARK" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="RECEIVE_NUM" type="{http://www.w3.org/2001/XMLSchema}string"/>
         *         &lt;element name="RECBID" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "zyear",
            "zdjlx",
            "zskfs",
            "zlsch",
            "zskrq",
            "bldat",
            "waers",
            "zskje",
            "kunnr",
            "statu",
            "zksbankn",
            "zksbanms",
            "bktxt",
            "numpg",
            "remark",
            "receive_num",
            "recbid"
        })
        public static class RecvBillInfo {

            @XmlElement(name = "BUKRS", required = true)
            protected String bukrs;
            @XmlElement(name = "ZYEAR", required = true)
            protected String zyear;
            @XmlElement(name = "ZDJLX", required = true)
            protected String zdjlx;
            @XmlElement(name = "ZSKFS", required = true)
            protected String zskfs;
            @XmlElement(name = "ZLSCH", required = true)
            protected String zlsch;
            @XmlElement(name = "ZSKRQ", required = true)
            protected String zskrq;
            @XmlElement(name = "BLDAT", required = true)
            protected String bldat;
            @XmlElement(name = "WAERS", required = true)
            protected String waers;
            @XmlElement(name = "ZSKJE", required = true)
            protected String zskje;
            @XmlElement(name = "KUNNR", required = true)
            protected String kunnr;
            @XmlElement(name = "STATU", required = true)
            protected String statu;
            @XmlElement(name = "ZKS_BANKN", required = true)
            protected String zksbankn;
            @XmlElement(name = "ZKS_BANMS", required = true)
            protected String zksbanms;
            @XmlElement(name = "BKTXT", required = true)
            protected String bktxt;
            @XmlElement(name = "NUMPG", required = true)
            protected String numpg;
            @XmlElement(name = "REMARK", required = true)
            protected String remark;
            @XmlElement(name = "RECEIVE_NUM", required = true)
            protected String receive_num;
            @XmlElement(name = "RECBID", required = true)
            protected String recbid;

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
             * 获取zyear属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZYEAR() {
                return zyear;
            }

            /**
             * 设置zyear属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZYEAR(String value) {
                this.zyear = value;
            }

            /**
             * 获取zdjlx属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZDJLX() {
                return zdjlx;
            }

            /**
             * 设置zdjlx属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZDJLX(String value) {
                this.zdjlx = value;
            }

            /**
             * 获取zskfs属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZSKFS() {
                return zskfs;
            }

            /**
             * 设置zskfs属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZSKFS(String value) {
                this.zskfs = value;
            }

            /**
             * 获取zlsch属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZLSCH() {
                return zlsch;
            }

            /**
             * 设置zlsch属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZLSCH(String value) {
                this.zlsch = value;
            }

            /**
             * 获取zskrq属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZSKRQ() {
                return zskrq;
            }

            /**
             * 设置zskrq属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZSKRQ(String value) {
                this.zskrq = value;
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
             * 获取zskje属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZSKJE() {
                return zskje;
            }

            /**
             * 设置zskje属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZSKJE(String value) {
                this.zskje = value;
            }

            /**
             * 获取kunnr属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getKUNNR() {
                return kunnr;
            }

            /**
             * 设置kunnr属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setKUNNR(String value) {
                this.kunnr = value;
            }

            /**
             * 获取statu属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getSTATU() {
                return statu;
            }

            /**
             * 设置statu属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setSTATU(String value) {
                this.statu = value;
            }

            /**
             * 获取zksbankn属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZKSBANKN() {
                return zksbankn;
            }

            /**
             * 设置zksbankn属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZKSBANKN(String value) {
                this.zksbankn = value;
            }

            /**
             * 获取zksbanms属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getZKSBANMS() {
                return zksbanms;
            }

            /**
             * 设置zksbanms属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setZKSBANMS(String value) {
                this.zksbanms = value;
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
             * 获取numpg属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNUMPG() {
                return numpg;
            }

            /**
             * 设置numpg属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNUMPG(String value) {
                this.numpg = value;
            }

            /**
             * 获取remark属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getREMARK() {
                return remark;
            }

            /**
             * 设置remark属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setREMARK(String value) {
                this.remark = value;
            }

            /**
             * 获取receivenum属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRECEIVE_NUM() {
                return receive_num;
            }

            /**
             * 设置receivenum属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRECEIVE_NUM(String value) {
                this.receive_num = value;
            }

            /**
             * 获取recbid属性的值。
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getRECBID() {
                return recbid;
            }

            /**
             * 设置recbid属性的值。
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setRECBID(String value) {
                this.recbid = value;
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
