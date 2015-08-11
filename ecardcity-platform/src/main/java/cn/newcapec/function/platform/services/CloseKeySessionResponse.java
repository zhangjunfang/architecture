
package cn.newcapec.function.platform.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
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
 *         &lt;element name="CloseKeySessionResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
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
    "closeKeySessionResult"
})
@XmlRootElement(name = "CloseKeySessionResponse")
public class CloseKeySessionResponse {

    @XmlElement(name = "CloseKeySessionResult")
    protected Result closeKeySessionResult;

    /**
     * Gets the value of the closeKeySessionResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getCloseKeySessionResult() {
        return closeKeySessionResult;
    }

    /**
     * Sets the value of the closeKeySessionResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setCloseKeySessionResult(Result value) {
        this.closeKeySessionResult = value;
    }

}
