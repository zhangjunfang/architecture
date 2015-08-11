
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
 *         &lt;element name="ValidKeyPropResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
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
    "validKeyPropResult"
})
@XmlRootElement(name = "ValidKeyPropResponse")
public class ValidKeyPropResponse {

    @XmlElement(name = "ValidKeyPropResult")
    protected Result validKeyPropResult;

    /**
     * Gets the value of the validKeyPropResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getValidKeyPropResult() {
        return validKeyPropResult;
    }

    /**
     * Sets the value of the validKeyPropResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setValidKeyPropResult(Result value) {
        this.validKeyPropResult = value;
    }

}
