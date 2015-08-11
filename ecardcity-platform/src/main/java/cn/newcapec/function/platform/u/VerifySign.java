
package cn.newcapec.function.platform.u;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author ocean
 * @date : 2014-4-18 下午04:44:58
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 *
 *
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="strSigData" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strSigValue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strUserCert" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="serrinfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "strSigData",
    "strSigValue",
    "strUserCert",
    "serrinfo"
})
@XmlRootElement(name = "VerifySign")
public class VerifySign {

    protected String strSigData;
    protected String strSigValue;
    protected String strUserCert;
    protected String serrinfo;

    /**
     * Gets the value of the strSigData property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStrSigData() {
        return strSigData;
    }

    /**
     * Sets the value of the strSigData property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStrSigData(String value) {
        this.strSigData = value;
    }

    /**
     * Gets the value of the strSigValue property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStrSigValue() {
        return strSigValue;
    }

    /**
     * Sets the value of the strSigValue property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStrSigValue(String value) {
        this.strSigValue = value;
    }

    /**
     * Gets the value of the strUserCert property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getStrUserCert() {
        return strUserCert;
    }

    /**
     * Sets the value of the strUserCert property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setStrUserCert(String value) {
        this.strUserCert = value;
    }

    /**
     * Gets the value of the serrinfo property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSerrinfo() {
        return serrinfo;
    }

    /**
     * Sets the value of the serrinfo property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSerrinfo(String value) {
        this.serrinfo = value;
    }

}
