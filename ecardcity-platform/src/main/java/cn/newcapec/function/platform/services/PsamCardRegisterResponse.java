
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
 *         &lt;element name="PsamCardRegisterResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
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
    "psamCardRegisterResult"
})
@XmlRootElement(name = "PsamCardRegisterResponse")
public class PsamCardRegisterResponse {

    @XmlElement(name = "PsamCardRegisterResult")
    protected Result psamCardRegisterResult;

    /**
     * Gets the value of the psamCardRegisterResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getPsamCardRegisterResult() {
        return psamCardRegisterResult;
    }

    /**
     * Sets the value of the psamCardRegisterResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setPsamCardRegisterResult(Result value) {
        this.psamCardRegisterResult = value;
    }

}
