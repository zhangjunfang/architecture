
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
 *         &lt;element name="VerifyServiceURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="VerifyID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="VerifyCode" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="totolSec" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="purseSector" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cardsnr" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="cardMac" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
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
    "verifyServiceURL",
    "verifyID",
    "verifyCode",
    "totolSec",
    "purseSector",
    "cardsnr",
    "cardMac",
    "encKey"
})
@XmlRootElement(name = "GetKeyForPublish")
public class GetKeyForPublish {

    @XmlElement(name = "VerifyServiceURL")
    protected String verifyServiceURL;
    @XmlElement(name = "VerifyID")
    protected int verifyID;
    @XmlElement(name = "VerifyCode")
    protected StringBuilder verifyCode;
    protected int totolSec;
    protected int purseSector;
    protected StringBuilder cardsnr;
    protected StringBuilder cardMac;
    protected String encKey;

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
     * Gets the value of the totolSec property.
     * 
     */
    public int getTotolSec() {
        return totolSec;
    }

    /**
     * Sets the value of the totolSec property.
     * 
     */
    public void setTotolSec(int value) {
        this.totolSec = value;
    }

    /**
     * Gets the value of the purseSector property.
     * 
     */
    public int getPurseSector() {
        return purseSector;
    }

    /**
     * Sets the value of the purseSector property.
     * 
     */
    public void setPurseSector(int value) {
        this.purseSector = value;
    }

    /**
     * Gets the value of the cardsnr property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getCardsnr() {
        return cardsnr;
    }

    /**
     * Sets the value of the cardsnr property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setCardsnr(StringBuilder value) {
        this.cardsnr = value;
    }

    /**
     * Gets the value of the cardMac property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getCardMac() {
        return cardMac;
    }

    /**
     * Sets the value of the cardMac property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setCardMac(StringBuilder value) {
        this.cardMac = value;
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
