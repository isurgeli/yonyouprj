//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.12.02 时间 10:23:08 AM CST 
//


package nc.vo.ztwzj.sapitf.bill.BankReceiptQry;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import nc.vo.ztwzj.sapitf.bill.RecvBillQry.RecvBillQryPara;


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
 *         &lt;element name="BUKRS" type="{http://www.w3.org/2001/XMLSchema}short"/>
 *         &lt;element name="ZHB_BANKN" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="BUDAT">
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
    "bukrs",
    "zhbbankn",
    "budat"
})
@XmlRootElement(name = "BankReceiptQryPara")
public class BankReceiptQryPara {

    @XmlElement(name = "BUKRS")
    protected String bukrs;
    @XmlElement(name = "ZHB_BANKN")
    protected String zhbbankn;
    @XmlElement(name = "BUDAT", required = true)
    protected RecvBillQryPara.SAPRangePara budat;

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
     * 获取zhbbankn属性的值。
     * 
     */
    public String getZHBBANKN() {
        return zhbbankn;
    }

    /**
     * 设置zhbbankn属性的值。
     * 
     */
    public void setZHBBANKN(String value) {
        this.zhbbankn = value;
    }

    /**
     * 获取budat属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BankReceiptQryPara.BUDAT }
     *     
     */
    public RecvBillQryPara.SAPRangePara getBUDAT() {
        return budat;
    }

    /**
     * 设置budat属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BankReceiptQryPara.BUDAT }
     *     
     */
    public void setBUDAT(RecvBillQryPara.SAPRangePara value) {
        this.budat = value;
    }
}
