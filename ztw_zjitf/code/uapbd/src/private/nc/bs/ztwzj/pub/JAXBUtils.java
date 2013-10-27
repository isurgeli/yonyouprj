package nc.bs.ztwzj.pub;

import java.io.InputStream;  
import java.io.OutputStream;  
  
import javax.xml.bind.JAXBContext;  
import javax.xml.bind.JAXBException;  
import javax.xml.bind.Marshaller;  
import javax.xml.bind.Unmarshaller;  
  
public class JAXBUtils {  
  
    private JAXBUtils() {  
  
        throw new UnsupportedOperationException();  
    }  
  
    public static Object unmarshal(String contextPath, InputStream xmlStream)  
            throws JAXBException {  
  
        JAXBContext jc = JAXBContext.newInstance(contextPath);  
        Unmarshaller u = jc.createUnmarshaller();  
  
        return u.unmarshal(xmlStream);  
    }  
  
    public static void marshal(String contextPath, Object obj,  
            OutputStream stream) throws JAXBException {  
  
        JAXBContext jc = JAXBContext.newInstance(contextPath);  
        Marshaller m = jc.createMarshaller();  
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
        m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");  
  
        m.marshal(obj, stream);  
    }  
}  