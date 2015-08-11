
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
 *         &lt;element name="RetrieveSysInfoResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
 *         &lt;element name="customerInfo" type="{http://tempuri.org/}AppCustomerInfo"/>
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
    "retrieveSysInfoResult",
    "customerInfo"
})
@XmlRootElement(name = "RetrieveSysInfoResponse")
public class RetrieveSysInfoResponse {

    @XmlElement(name = "RetrieveSysInfoResult")
    protected Result retrieveSysInfoResult;
    @XmlElement(required = true)
    protected AppCustomerInfo customerInfo;

    /**
     * Gets the value of the retrieveSysInfoResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getRetrieveSysInfoResult() {
        return retrieveSysInfoResult;
    }

    /**
     * Sets the value of the retrieveSysInfoResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setRetrieveSysInfoResult(Result value) {
        this.retrieveSysInfoResult = value;
    }

    /**
     * Gets the value of the customerInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AppCustomerInfo }
     *     
     */
    public AppCustomerInfo getCustomerInfo() {
        return customerInfo;
    }

    /**
     * Sets the value of the customerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AppCustomerInfo }
     *     
     */
    public void setCustomerInfo(AppCustomerInfo value) {
        this.customerInfo = value;
    }

}
