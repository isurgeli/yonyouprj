//
// ���ļ����� JavaTM Architecture for XML Binding (JAXB) ����ʵ�� v2.2.7 ���ɵ�
// ����� <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// �����±���Դģʽʱ, �Դ��ļ��������޸Ķ�����ʧ��
// ����ʱ��: 2013.11.28 ʱ�� 11:04:39 AM CST 
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
     * ��ȡsystemInfo���Ե�ֵ��
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
     * ����systemInfo���Ե�ֵ��
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
     * ��ȡrecvBillList���Ե�ֵ��
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
     * ����recvBillList���Ե�ֵ��
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
     * <p>anonymous complex type�� Java �ࡣ
     * 
     * <p>����ģʽƬ��ָ�������ڴ����е�Ԥ�����ݡ�
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
             * ��ȡzyear���Ե�ֵ��
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
             * ����zyear���Ե�ֵ��
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
             * ��ȡzdjlx���Ե�ֵ��
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
             * ����zdjlx���Ե�ֵ��
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
             * ��ȡzskfs���Ե�ֵ��
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
             * ����zskfs���Ե�ֵ��
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
             * ��ȡzlsch���Ե�ֵ��
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
             * ����zlsch���Ե�ֵ��
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
             * ��ȡzskrq���Ե�ֵ��
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
             * ����zskrq���Ե�ֵ��
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
             * ��ȡzskje���Ե�ֵ��
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
             * ����zskje���Ե�ֵ��
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
             * ��ȡkunnr���Ե�ֵ��
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
             * ����kunnr���Ե�ֵ��
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
             * ��ȡstatu���Ե�ֵ��
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
             * ����statu���Ե�ֵ��
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
             * ��ȡzksbankn���Ե�ֵ��
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
             * ����zksbankn���Ե�ֵ��
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
             * ��ȡzksbanms���Ե�ֵ��
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
             * ����zksbanms���Ե�ֵ��
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
             * ��ȡnumpg���Ե�ֵ��
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
             * ����numpg���Ե�ֵ��
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
             * ��ȡremark���Ե�ֵ��
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
             * ����remark���Ե�ֵ��
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
             * ��ȡreceivenum���Ե�ֵ��
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
             * ����receivenum���Ե�ֵ��
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
             * ��ȡrecbid���Ե�ֵ��
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
             * ����recbid���Ե�ֵ��
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
