
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
 *         &lt;element name="PhaseCodeStrResult" type="{http://tempuri.org/}Result" minOccurs="0"/>
 *         &lt;element name="keyOPCmd" type="{http://www.w3.org/2001/XMLSchema}int"/>
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
    "phaseCodeStrResult",
    "keyOPCmd"
})
@XmlRootElement(name = "PhaseCodeStrResponse")
public class PhaseCodeStrResponse {

    @XmlElement(name = "PhaseCodeStrResult")
    protected Result phaseCodeStrResult;
    protected int keyOPCmd;

    /**
     * Gets the value of the phaseCodeStrResult property.
     * 
     * @return
     *     possible object is
     *     {@link Result }
     *     
     */
    public Result getPhaseCodeStrResult() {
        return phaseCodeStrResult;
    }

    /**
     * Sets the value of the phaseCodeStrResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Result }
     *     
     */
    public void setPhaseCodeStrResult(Result value) {
        this.phaseCodeStrResult = value;
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

}
