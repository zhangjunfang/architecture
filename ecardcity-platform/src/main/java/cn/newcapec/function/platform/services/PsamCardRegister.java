
package cn.newcapec.function.platform.services;

import java.math.BigInteger;
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
 *         &lt;element name="VerifyServiceURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerifyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VerifyCode" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="PsamCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PrintCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sCardSNR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PosCode" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="BeginDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="EndDT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "verifyServiceURL",
    "verifyID",
    "verifyCode",
    "psamCode",
    "printCode",
    "sCardSNR",
    "posCode",
    "beginDT",
    "endDT"
})
@XmlRootElement(name = "PsamCardRegister")
public class PsamCardRegister {

    @XmlElement(name = "VerifyServiceURL")
    protected String verifyServiceURL;
    @XmlElement(name = "VerifyID")
    protected int verifyID;
    @XmlElement(name = "VerifyCode")
    protected StringBuilder verifyCode;
    @XmlElement(name = "PsamCode")
    protected String psamCode;
    @XmlElement(name = "PrintCode")
    protected String printCode;
    protected String sCardSNR;
    @XmlElement(name = "PosCode", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger posCode;
    @XmlElement(name = "BeginDT")
    protected String beginDT;
    @XmlElement(name = "EndDT")
    protected String endDT;

    /**
     * Gets the value of the verifyServiceURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVerifyServiceURL() {
        return verifyServiceURL;
    }

    /**
     * Sets the value of the verifyServiceURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVerifyServiceURL(String value) {
        this.verifyServiceURL = value;
    }

    /**
     * Gets the value of the verifyID property.
     * 
     */
    public int getVerifyID() {
        return verifyID;
    }

    /**
     * Sets the value of the verifyID property.
     * 
     */
    public void setVerifyID(int value) {
        this.verifyID = value;
    }

    /**
     * Gets the value of the verifyCode property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getVerifyCode() {
        return verifyCode;
    }

    /**
     * Sets the value of the verifyCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setVerifyCode(StringBuilder value) {
        this.verifyCode = value;
    }

    /**
     * Gets the value of the psamCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPsamCode() {
        return psamCode;
    }

    /**
     * Sets the value of the psamCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPsamCode(String value) {
        this.psamCode = value;
    }

    /**
     * Gets the value of the printCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrintCode() {
        return printCode;
    }

    /**
     * Sets the value of the printCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrintCode(String value) {
        this.printCode = value;
    }

    /**
     * Gets the value of the sCardSNR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSCardSNR() {
        return sCardSNR;
    }

    /**
     * Sets the value of the sCardSNR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSCardSNR(String value) {
        this.sCardSNR = value;
    }

    /**
     * Gets the value of the posCode property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getPosCode() {
        return posCode;
    }

    /**
     * Sets the value of the posCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setPosCode(BigInteger value) {
        this.posCode = value;
    }

    /**
     * Gets the value of the beginDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBeginDT() {
        return beginDT;
    }

    /**
     * Sets the value of the beginDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBeginDT(String value) {
        this.beginDT = value;
    }

    /**
     * Gets the value of the endDT property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDT() {
        return endDT;
    }

    /**
     * Sets the value of the endDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDT(String value) {
        this.endDT = value;
    }

}
