
package cn.newcapec.function.platform.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="sysCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keyUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keyUserPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sessionID" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="reserved" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
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
    "sysCode",
    "keyUser",
    "keyUserPwd",
    "sessionID",
    "reserved"
})
@XmlRootElement(name = "CreateKeySession")
public class CreateKeySession {

    protected String sysCode;
    protected String keyUser;
    protected String keyUserPwd;
    protected StringBuilder sessionID;
    protected StringBuilder reserved;

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

    /**
     * Gets the value of the keyUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyUser() {
        return keyUser;
    }

    /**
     * Sets the value of the keyUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyUser(String value) {
        this.keyUser = value;
    }

    /**
     * Gets the value of the keyUserPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKeyUserPwd() {
        return keyUserPwd;
    }

    /**
     * Sets the value of the keyUserPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKeyUserPwd(String value) {
        this.keyUserPwd = value;
    }

    /**
     * Gets the value of the sessionID property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getSessionID() {
        return sessionID;
    }

    /**
     * Sets the value of the sessionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setSessionID(StringBuilder value) {
        this.sessionID = value;
    }

    /**
     * Gets the value of the reserved property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getReserved() {
        return reserved;
    }

    /**
     * Sets the value of the reserved property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setReserved(StringBuilder value) {
        this.reserved = value;
    }

}
