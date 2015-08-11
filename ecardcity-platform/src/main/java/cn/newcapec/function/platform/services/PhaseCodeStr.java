
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
 *         &lt;element name="grantCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="keyOPCmd" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="keyUser" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="oldKeyPwd" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="newKeyPwd" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
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
    "grantCode",
    "keyOPCmd",
    "keyUser",
    "oldKeyPwd",
    "newKeyPwd"
})
@XmlRootElement(name = "PhaseCodeStr")
public class PhaseCodeStr {

    protected String sysCode;
    protected String grantCode;
    protected int keyOPCmd;
    protected StringBuilder keyUser;
    protected StringBuilder oldKeyPwd;
    protected StringBuilder newKeyPwd;

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
     * Gets the value of the grantCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrantCode() {
        return grantCode;
    }

    /**
     * Sets the value of the grantCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrantCode(String value) {
        this.grantCode = value;
    }

    /**
     * Gets the value of the keyOPCmd property.
     * 
     */
    public int getKeyOPCmd() {
        return keyOPCmd;
    }

    /**
     * Sets the value of the keyOPCmd property.
     * 
     */
    public void setKeyOPCmd(int value) {
        this.keyOPCmd = value;
    }

    /**
     * Gets the value of the keyUser property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getKeyUser() {
        return keyUser;
    }

    /**
     * Sets the value of the keyUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setKeyUser(StringBuilder value) {
        this.keyUser = value;
    }

    /**
     * Gets the value of the oldKeyPwd property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getOldKeyPwd() {
        return oldKeyPwd;
    }

    /**
     * Sets the value of the oldKeyPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setOldKeyPwd(StringBuilder value) {
        this.oldKeyPwd = value;
    }

    /**
     * Gets the value of the newKeyPwd property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getNewKeyPwd() {
        return newKeyPwd;
    }

    /**
     * Sets the value of the newKeyPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setNewKeyPwd(StringBuilder value) {
        this.newKeyPwd = value;
    }

}
