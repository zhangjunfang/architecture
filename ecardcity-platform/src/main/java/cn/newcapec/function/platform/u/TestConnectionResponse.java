
package cn.newcapec.function.platform.u;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 *
 * @author ocean
 * @date : 2014-4-18 下午04:43:43
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
 *         &lt;element name="TestConnectionResult" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "testConnectionResult"
})
@XmlRootElement(name = "TestConnectionResponse")
public class TestConnectionResponse {

    @XmlElement(name = "TestConnectionResult")
    protected boolean testConnectionResult;

    /**
     * Gets the value of the testConnectionResult property.
     *
     */
    public boolean isTestConnectionResult() {
        return testConnectionResult;
    }

    /**
     * Sets the value of the testConnectionResult property.
     *
     */
    public void setTestConnectionResult(boolean value) {
        this.testConnectionResult = value;
    }

}
