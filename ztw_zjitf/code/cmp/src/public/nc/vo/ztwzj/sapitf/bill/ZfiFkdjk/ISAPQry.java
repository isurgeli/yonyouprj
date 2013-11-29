package nc.vo.ztwzj.sapitf.bill.ZfiFkdjk;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import nc.vo.pub.BusinessException;

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
 *         &lt;element name="item" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Sign" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Option" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Low" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="High" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "item"
})
public class ISAPQry {

    protected List<ISAPQry.Item> item;

    /**
     * Gets the value of the item property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ZfiFkdjk.IBukrs.Item }
     * 
     * 
     */
    public List<ISAPQry.Item> getItem() {
        if (item == null) {
            item = new ArrayList<ISAPQry.Item>();
        }
        return this.item;
    }
    
    public static ISAPQry getQryByCondition(String _low, String _high) throws BusinessException {
    	ISAPQry ret = new ISAPQry();
    	Item item = new Item(); 
    	item.setByCondition(_low, _high);
    	ret.getItem().add(item);
    	
    	return ret;
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
     *         &lt;element name="Sign" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Option" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Low" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="High" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
        "sign",
        "option",
        "low",
        "high"
    })
    public static class Item {

        @XmlElement(name = "Sign", required = true)
        protected String sign;
        @XmlElement(name = "Option", required = true)
        protected String option;
        @XmlElement(name = "Low", required = true)
        protected String low;
        @XmlElement(name = "High", required = true)
        protected String high;

        /**
         * ��ȡsign���Ե�ֵ��
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
         * ����sign���Ե�ֵ��
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
         * ��ȡoption���Ե�ֵ��
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
         * ����option���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setOption(String value) {
            this.option = value;
        }

        /**
         * ��ȡlow���Ե�ֵ��
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
         * ����low���Ե�ֵ��
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
         * ��ȡhigh���Ե�ֵ��
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
         * ����high���Ե�ֵ��
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHigh(String value) {
            this.high = value;
        }
        
        public void setByCondition(String _low, String _high) throws BusinessException {
	        if (_low == null || _low.length()==0)
	    		throw new BusinessException("��������");
	    	sign = "I";
	    	if (_high == null || _high.length()==0) {
	    		option = "EQ";
	    		low = _low;
	    		high = "";
	    	}else {
	    		option = "BT";
	    		low = _low;
	    		high = _high;
	    	}
        }
    }

}