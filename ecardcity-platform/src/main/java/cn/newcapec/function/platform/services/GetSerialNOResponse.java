
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
 *         &lt;element name="GetSerialNOResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
 *         &lt;element name="sysCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getSerialNOResult",
    "sysCode"
})
@XmlRootElement(name = "GetSerialNOResponse")
public class GetSerialNOResponse {

    @XmlElement(name = "GetSerialNOResult")
    protected Result getSerialNOResult;
    protected String sysCode;

    /**
     * Gets the value of the getSerialNOResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getGetSerialNOResult() {
        return getSerialNOResult;
    }

    /**
     * Sets the value of the getSerialNOResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setGetSerialNOResult(Result value) {
        this.getSerialNOResult = value;
    }

    /**
     * Gets the value of the sysCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * Sets the value of the sysCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysCode(String value) {
        this.sysCode = value;
    }

}
