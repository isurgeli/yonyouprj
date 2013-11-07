//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.7 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2013.11.01 时间 10:08:18 AM CST 
//


package nc.vo.ztwzj.sapitf.voucher;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the nc.vo.ztwzj.sapitf.voucher package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: nc.vo.ztwzj.sapitf.voucher
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Voucher }
     * 
     */
    public Voucher createVoucher() {
        return new Voucher();
    }

    /**
     * Create an instance of {@link Voucher.Entries }
     * 
     */
    public Voucher.Entries createVoucherEntries() {
        return new Voucher.Entries();
    }

    /**
     * Create an instance of {@link Voucher.Entries.Entry }
     * 
     */
    public Voucher.Entries.Entry createVoucherEntriesEntry() {
        return new Voucher.Entries.Entry();
    }

}
