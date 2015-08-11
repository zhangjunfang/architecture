
package cn.newcapec.function.platform.u;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author ocean
 * @date : 2014-4-18 下午04:44:33
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
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
 *         &lt;element name="VerifyIdentityResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="serrinfo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "verifyIdentityResult",
    "serrinfo"
})
@XmlRootElement(name = "VerifyIdentityResponse")
public class VerifyIdentityResponse {

    @XmlElement(name = "VerifyIdentityResult")
    protected boolean verifyIdentityResult;
    protected String serrinfo;

    /**
     * Gets the value of the verifyIdentityResult property.
     *
     */
    public boolean isVerifyIdentityResult() {
        return verifyIdentityResult;
    }

    /**
     * Sets the value of the verifyIdentityResult property.
     *
     */
    public void setVerifyIdentityResult(boolean value) {
        this.verifyIdentityResult = value;
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
