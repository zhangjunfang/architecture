
package cn.newcapec.function.platform.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CheckMACResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
 *         &lt;element name="pMAC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
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
    "checkMACResult",
    "pmac"
})
@XmlRootElement(name = "CheckMACResponse")
public class CheckMACResponse {

    @XmlElement(name = "CheckMACResult")
    protected Result checkMACResult;
    @XmlElement(name = "pMAC")
    @XmlSchemaType(name = "unsignedInt")
    protected long pmac;

    /**
     * Gets the value of the checkMACResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getCheckMACResult() {
        return checkMACResult;
    }

    /**
     * Sets the value of the checkMACResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setCheckMACResult(Result value) {
        this.checkMACResult = value;
    }

    /**
     * Gets the value of the pmac property.
     * 
     */
    public long getPMAC() {
        return pmac;
    }

    /**
     * Sets the value of the pmac property.
     * 
     */
    public void setPMAC(long value) {
        this.pmac = value;
    }

}
