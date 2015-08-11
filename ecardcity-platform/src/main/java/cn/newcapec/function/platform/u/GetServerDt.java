
package cn.newcapec.function.platform.u;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 *
 * @author ocean
 * @date : 2014-4-18 下午04:42:05
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
  * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ServerDt" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="serrinfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "serverDt",
    "serrinfo"
})
@XmlRootElement(name = "GetServerDt")
public class GetServerDt {

    @XmlElement(name = "ServerDt", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar serverDt;
    protected String serrinfo;

    /**
     * Gets the value of the serverDt property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getServerDt() {
        return serverDt;
    }

    /**
     * Sets the value of the serverDt property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setServerDt(XMLGregorianCalendar value) {
        this.serverDt = value;
    }

    /**
     * Gets the value of the serrinfo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSerrinfo() {
        return serrinfo;
    }

    /**
     * Sets the value of the serrinfo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSerrinfo(String value) {
        this.serrinfo = value;
    }

}
