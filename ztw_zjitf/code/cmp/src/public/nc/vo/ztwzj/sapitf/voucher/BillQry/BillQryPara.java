//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.11 时间 04:14:15 PM CST 
//


package nc.vo.ztwzj.sapitf.voucher.BillQry;

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
 *         &lt;element name="BUSITYPE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUDATS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BUDATE" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "busitype",
    "bukrs",
    "budats",
    "budate"
})
@XmlRootElement(name = "BillQryPara")
public class BillQryPara {

    @XmlElement(name = "BUSITYPE", required = true)
    protected String busitype;
    @XmlElement(name = "BUKRS", required = true)
    protected String bukrs;
    @XmlElement(name = "BUDATS", required = true)
    protected String budats;
    @XmlElement(name = "BUDATE", required = true)
    protected String budate;

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
     * 获取budats属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDATS() {
        return budats;
    }

    /**
     * 设置budats属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDATS(String value) {
        this.budats = value;
    }

    /**
     * 获取budate属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBUDATE() {
        return budate;
    }

    /**
     * 设置budate属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBUDATE(String value) {
        this.budate = value;
    }

}
