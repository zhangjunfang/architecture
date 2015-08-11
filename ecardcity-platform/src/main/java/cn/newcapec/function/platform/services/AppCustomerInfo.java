
package cn.newcapec.function.platform.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AppCustomerInfo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AppCustomerInfo">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CustomerName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrantSerialNO" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="CustomerCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrantDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PwdVer" type="{http://www.w3.org/2001/XMLSchema}base64Binary" minOccurs="0"/>
 *         &lt;element name="Market" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="GrantRights" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SysID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="GrantPwd" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}unsignedByte"/>
 *         &lt;element name="InvalidDt" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="SysRegCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AppCustomerInfo", propOrder = {
    "customerName",
    "grantSerialNO",
    "customerCode",
    "grantDt",
    "pwdVer",
    "market",
    "grantRights",
    "sysID",
    "grantPwd",
    "status",
    "invalidDt",
    "sysRegCode"
})
public class AppCustomerInfo {

    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "GrantSerialNO")
    protected int grantSerialNO;
    @XmlElement(name = "CustomerCode")
    protected String customerCode;
    @XmlElement(name = "GrantDt")
    protected String grantDt;
    @XmlElement(name = "PwdVer")
    protected byte[] pwdVer;
    @XmlElement(name = "Market")
    protected String market;
    @XmlElement(name = "GrantRights")
    protected String grantRights;
    @XmlElement(name = "SysID")
    protected int sysID;
    @XmlElement(name = "GrantPwd")
    protected String grantPwd;
    @XmlElement(name = "Status")
    @XmlSchemaType(name = "unsignedByte")
    protected short status;
    @XmlElement(name = "InvalidDt")
    protected String invalidDt;
    @XmlElement(name = "SysRegCode")
    protected String sysRegCode;

    /**
     * Gets the value of the customerName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the value of the customerName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerName(String value) {
        this.customerName = value;
    }

    /**
     * Gets the value of the grantSerialNO property.
     * 
     */
    public int getGrantSerialNO() {
        return grantSerialNO;
    }

    /**
     * Sets the value of the grantSerialNO property.
     * 
     */
    public void setGrantSerialNO(int value) {
        this.grantSerialNO = value;
    }

    /**
     * Gets the value of the customerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCustomerCode() {
        return customerCode;
    }

    /**
     * Sets the value of the customerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    /**
     * Gets the value of the grantDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrantDt() {
        return grantDt;
    }

    /**
     * Sets the value of the grantDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrantDt(String value) {
        this.grantDt = value;
    }

    /**
     * Gets the value of the pwdVer property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getPwdVer() {
        return pwdVer;
    }

    /**
     * Sets the value of the pwdVer property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setPwdVer(byte[] value) {
        this.pwdVer = value;
    }

    /**
     * Gets the value of the market property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarket() {
        return market;
    }

    /**
     * Sets the value of the market property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarket(String value) {
        this.market = value;
    }

    /**
     * Gets the value of the grantRights property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrantRights() {
        return grantRights;
    }

    /**
     * Sets the value of the grantRights property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrantRights(String value) {
        this.grantRights = value;
    }

    /**
     * Gets the value of the sysID property.
     * 
     */
    public int getSysID() {
        return sysID;
    }

    /**
     * Sets the value of the sysID property.
     * 
     */
    public void setSysID(int value) {
        this.sysID = value;
    }

    /**
     * Gets the value of the grantPwd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGrantPwd() {
        return grantPwd;
    }

    /**
     * Sets the value of the grantPwd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGrantPwd(String value) {
        this.grantPwd = value;
    }

    /**
     * Gets the value of the status property.
     * 
     */
    public short getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     */
    public void setStatus(short value) {
        this.status = value;
    }

    /**
     * Gets the value of the invalidDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvalidDt() {
        return invalidDt;
    }

    /**
     * Sets the value of the invalidDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvalidDt(String value) {
        this.invalidDt = value;
    }

    /**
     * Gets the value of the sysRegCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSysRegCode() {
        return sysRegCode;
    }

    /**
     * Sets the value of the sysRegCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSysRegCode(String value) {
        this.sysRegCode = value;
    }

}
