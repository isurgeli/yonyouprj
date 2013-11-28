//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.28 时间 08:59:58 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.RecvBillQry;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import nc.itf.lxt.pub.sqltool.BRACKET;
import nc.itf.lxt.pub.sqltool.DELIMITER;
import nc.itf.lxt.pub.sqltool.OPERATOR;
import nc.itf.lxt.pub.sqltool.SQLWhereClause;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;


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
 *         &lt;element name="ZSKRQS">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ZSKRQ" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="RECEIVE_NUMS">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="RECEIVE_NUM" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="KUNNRS">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="KUNNR" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;simpleContent>
 *                         &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                           &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                           &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                         &lt;/extension>
 *                       &lt;/simpleContent>
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
    "zskrqs",
    "receivenums",
    "kunnrs"
})
@XmlRootElement(name = "RecvBillQryPara")
public class RecvBillQryPara {

    @XmlElement(name = "BUKRS")
    protected String bukrs;
    @XmlElement(name = "ZSKRQS", required = true)
    protected RecvBillQryPara.ZSKRQS zskrqs;
    @XmlElement(name = "RECEIVE_NUMS", required = true)
    protected RecvBillQryPara.RECEIVENUMS receivenums;
    @XmlElement(name = "KUNNRS", required = true)
    protected RecvBillQryPara.KUNNRS kunnrs;

    /**
     * 获取bukrs属性的值。
     * 
     */
    public String getBUKRS() {
        return bukrs;
    }

    /**
     * 设置bukrs属性的值。
     * 
     */
    public void setBUKRS(String value) {
        this.bukrs = value;
    }

    /**
     * 获取zskrqs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RecvBillQryPara.ZSKRQS }
     *     
     */
    public RecvBillQryPara.ZSKRQS getZSKRQS() {
        return zskrqs;
    }

    /**
     * 设置zskrqs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RecvBillQryPara.ZSKRQS }
     *     
     */
    public void setZSKRQS(RecvBillQryPara.ZSKRQS value) {
        this.zskrqs = value;
    }

    /**
     * 获取receivenums属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RecvBillQryPara.RECEIVENUMS }
     *     
     */
    public RecvBillQryPara.RECEIVENUMS getRECEIVENUMS() {
        return receivenums;
    }

    /**
     * 设置receivenums属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RecvBillQryPara.RECEIVENUMS }
     *     
     */
    public void setRECEIVENUMS(RecvBillQryPara.RECEIVENUMS value) {
        this.receivenums = value;
    }

    /**
     * 获取kunnrs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RecvBillQryPara.KUNNRS }
     *     
     */
    public RecvBillQryPara.KUNNRS getKUNNRS() {
        return kunnrs;
    }

    /**
     * 设置kunnrs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RecvBillQryPara.KUNNRS }
     *     
     */
    public void setKUNNRS(RecvBillQryPara.KUNNRS value) {
        this.kunnrs = value;
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
     *         &lt;element name="KUNNR" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "kunnr"
    })
    public static class KUNNRS {

        @XmlElement(name = "KUNNR")
        protected List<RecvBillQryPara.SAPRangePara> kunnr;

        /**
         * Gets the value of the kunnr property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the kunnr property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getKUNNR().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RecvBillQryPara.KUNNRS.KUNNR }
         * 
         * 
         */
        public List<RecvBillQryPara.SAPRangePara> getKUNNR() {
            if (kunnr == null) {
                kunnr = new ArrayList<RecvBillQryPara.SAPRangePara>();
            }
            return this.kunnr;
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
     *         &lt;element name="RECEIVE_NUM" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "receivenum"
    })
    public static class RECEIVENUMS {

        @XmlElement(name = "RECEIVE_NUM")
        protected List<RecvBillQryPara.SAPRangePara> receivenum;

        /**
         * Gets the value of the receivenum property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the receivenum property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRECEIVENUM().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RecvBillQryPara.RECEIVENUMS.RECEIVENUM }
         * 
         * 
         */
        public List<RecvBillQryPara.SAPRangePara> getRECEIVENUM() {
            if (receivenum == null) {
                receivenum = new ArrayList<RecvBillQryPara.SAPRangePara>();
            }
            return this.receivenum;
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
     *         &lt;element name="ZSKRQ" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;simpleContent>
     *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *                 &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
     *                 &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
     *               &lt;/extension>
     *             &lt;/simpleContent>
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
        "zskrq"
    })
    public static class ZSKRQS {

        @XmlElement(name = "ZSKRQ")
        protected List<RecvBillQryPara.SAPRangePara> zskrq;

        /**
         * Gets the value of the zskrq property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the zskrq property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getZSKRQ().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link RecvBillQryPara.ZSKRQS.ZSKRQ }
         * 
         * 
         */
        public List<RecvBillQryPara.SAPRangePara> getZSKRQ() {
            if (zskrq == null) {
                zskrq = new ArrayList<RecvBillQryPara.SAPRangePara>();
            }
            return this.zskrq;
        }


        
    }
    
    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="low" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="high" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="sign" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="option" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class SAPRangePara {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "low")
        protected String low;
        @XmlAttribute(name = "high")
        protected String high;
        @XmlAttribute(name = "sign")
        protected String sign;
        @XmlAttribute(name = "option")
        protected String option;

        /**
         * 获取value属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * 设置value属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 获取low属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getLow() {
            return low;
        }

        /**
         * 设置low属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setLow(String value) {
            this.low = value;
        }

        /**
         * 获取high属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHigh() {
            return high;
        }

        /**
         * 设置high属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHigh(String value) {
            this.high = value;
        }

        /**
         * 获取sign属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSign() {
            return sign;
        }

        /**
         * 设置sign属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSign(String value) {
            this.sign = value;
        }

        /**
         * 获取option属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getOption() {
            return option;
        }

        /**
         * 设置option属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOption(String value) {
            this.option = value;
        }
        
        public void transferDateValue() throws BusinessException {
        	SimpleDateFormat sFormat = new SimpleDateFormat("yyyyMMdd");
    		try {
    			if (low != null && low.length() > 0)
    				low = UFDate.getDate(sFormat.parse(low)).toString();
    			if (high != null && high.length() > 0)
    				high = UFDate.getDate(sFormat.parse(high)).toString();
    		} catch (ParseException e) {
    			throw new BusinessException(e.getMessage() ,e);
    		}
        }
        
        public List<SQLWhereClause> getSQLWhere(String key) throws BusinessException {
        	if (low==null || low.length()==0) 
        		throw new BusinessException("查询参数错误 low", "S1001");
        	if (!option.equals("EQ") && (high==null || high.length()==0)) 
        		throw new BusinessException("查询参数错误 high", "S1001");
        	
        	ArrayList<SQLWhereClause> ret = new ArrayList<SQLWhereClause>();
        	
        	if (sign.equals("I") && option.equals("BT")) {
        		ret.add(new SQLWhereClause(OPERATOR.OR, BRACKET.LEFT, key, OPERATOR.GTE, DELIMITER.getStringParaValue(low)));
        		ret.add(new SQLWhereClause(OPERATOR.AND, BRACKET.RIGHT, key, OPERATOR.LTE, DELIMITER.getStringParaValue(high)));
        	} else if (sign.equals("I") && option.equals("EQ")) {
        		ret.add(new SQLWhereClause(OPERATOR.OR, BRACKET.NONE, key, OPERATOR.EQ, DELIMITER.getStringParaValue(low)));
        	} else if (sign.equals("E") && option.equals("BT")) {
        		ret.add(new SQLWhereClause(OPERATOR.OR, BRACKET.LEFT, key, OPERATOR.LT, DELIMITER.getStringParaValue(low)));
        		ret.add(new SQLWhereClause(OPERATOR.OR, BRACKET.RIGHT, key, OPERATOR.GT, DELIMITER.getStringParaValue(high)));
        	} else if (sign.equals("E") && option.equals("EQ")) {
        		ret.add(new SQLWhereClause(OPERATOR.OR, BRACKET.NONE, key, OPERATOR.NEQ, DELIMITER.getStringParaValue(low)));
        	} else {
        		throw new BusinessException("查询参数错误 sign 或 option", "S1001");
        	}
        	
        	return ret;
        }
    }

}
