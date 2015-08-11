
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
 *         &lt;element name="ASN" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="Rand" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
 *         &lt;element name="NoteCase" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OddFare" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OpCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="OpFare" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="posCode" type="{http://www.w3.org/2001/XMLSchema}unsignedLong"/>
 *         &lt;element name="OpDT" type="{http://tempuri.org/}StringBuilder" minOccurs="0"/>
 *         &lt;element name="pMAC" type="{http://www.w3.org/2001/XMLSchema}unsignedInt"/>
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
    "asn",
    "rand",
    "noteCase",
    "oddFare",
    "opCount",
    "opFare",
    "posCode",
    "opDT",
    "pmac"
})
@XmlRootElement(name = "CheckMAC")
public class CheckMAC {

    @XmlElement(name = "VerifyServiceURL")
    protected String verifyServiceURL;
    @XmlElement(name = "VerifyID")
    protected int verifyID;
    @XmlElement(name = "VerifyCode")
    protected StringBuilder verifyCode;
    @XmlElement(name = "ASN", required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger asn;
    @XmlElement(name = "Rand")
    @XmlSchemaType(name = "unsignedInt")
    protected long rand;
    @XmlElement(name = "NoteCase")
    protected int noteCase;
    @XmlElement(name = "OddFare")
    protected int oddFare;
    @XmlElement(name = "OpCount")
    protected int opCount;
    @XmlElement(name = "OpFare")
    protected int opFare;
    @XmlElement(required = true)
    @XmlSchemaType(name = "unsignedLong")
    protected BigInteger posCode;
    @XmlElement(name = "OpDT")
    protected StringBuilder opDT;
    @XmlElement(name = "pMAC")
    @XmlSchemaType(name = "unsignedInt")
    protected long pmac;

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
     * Gets the value of the asn property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getASN() {
        return asn;
    }

    /**
     * Sets the value of the asn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setASN(BigInteger value) {
        this.asn = value;
    }

    /**
     * Gets the value of the rand property.
     * 
     */
    public long getRand() {
        return rand;
    }

    /**
     * Sets the value of the rand property.
     * 
     */
    public void setRand(long value) {
        this.rand = value;
    }

    /**
     * Gets the value of the noteCase property.
     * 
     */
    public int getNoteCase() {
        return noteCase;
    }

    /**
     * Sets the value of the noteCase property.
     * 
     */
    public void setNoteCase(int value) {
        this.noteCase = value;
    }

    /**
     * Gets the value of the oddFare property.
     * 
     */
    public int getOddFare() {
        return oddFare;
    }

    /**
     * Sets the value of the oddFare property.
     * 
     */
    public void setOddFare(int value) {
        this.oddFare = value;
    }

    /**
     * Gets the value of the opCount property.
     * 
     */
    public int getOpCount() {
        return opCount;
    }

    /**
     * Sets the value of the opCount property.
     * 
     */
    public void setOpCount(int value) {
        this.opCount = value;
    }

    /**
     * Gets the value of the opFare property.
     * 
     */
    public int getOpFare() {
        return opFare;
    }

    /**
     * Sets the value of the opFare property.
     * 
     */
    public void setOpFare(int value) {
        this.opFare = value;
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
     * Gets the value of the opDT property.
     * 
     * @return
     *     possible object is
     *     {@link StringBuilder }
     *     
     */
    public StringBuilder getOpDT() {
        return opDT;
    }

    /**
     * Sets the value of the opDT property.
     * 
     * @param value
     *     allowed object is
     *     {@link StringBuilder }
     *     
     */
    public void setOpDT(StringBuilder value) {
        this.opDT = value;
    }

    /**
     * Gets the value of the pmac property.
     * 
     */
    public long getPMAC() {
        return pmac;
    }

    /**
     * Sets the value of the pmac property.
     * 
     */
    public void setPMAC(long value) {
        this.pmac = value;
    }

}
