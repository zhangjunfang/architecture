
package cn.newcapec.function.platform.u;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author ocean
 * @date : 2014-4-18 下午04:41:40
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetCurrentVerResult" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getCurrentVerResult"
})
@XmlRootElement(name = "GetCurrentVerResponse")
public class GetCurrentVerResponse {

    @XmlElement(name = "GetCurrentVerResult")
    protected String getCurrentVerResult;

    /**
     * Gets the value of the getCurrentVerResult property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getGetCurrentVerResult() {
        return getCurrentVerResult;
    }

    /**
     * Sets the value of the getCurrentVerResult property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setGetCurrentVerResult(String value) {
        this.getCurrentVerResult = value;
    }

}
