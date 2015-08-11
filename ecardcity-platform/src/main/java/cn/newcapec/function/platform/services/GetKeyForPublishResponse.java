
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
 *         &lt;element name="GetKeyForPublishResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
 *         &lt;element name="encKey" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "getKeyForPublishResult",
    "encKey"
})
@XmlRootElement(name = "GetKeyForPublishResponse")
public class GetKeyForPublishResponse {

    @XmlElement(name = "GetKeyForPublishResult")
    protected Result getKeyForPublishResult;
    protected String encKey;

    /**
     * Gets the value of the getKeyForPublishResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getGetKeyForPublishResult() {
        return getKeyForPublishResult;
    }

    /**
     * Sets the value of the getKeyForPublishResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setGetKeyForPublishResult(Result value) {
        this.getKeyForPublishResult = value;
    }

    /**
     * Gets the value of the encKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEncKey() {
        return encKey;
    }

    /**
     * Sets the value of the encKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEncKey(String value) {
        this.encKey = value;
    }

}
