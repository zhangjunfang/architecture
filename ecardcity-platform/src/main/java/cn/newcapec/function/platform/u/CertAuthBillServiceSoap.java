
package cn.newcapec.function.platform.u;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.ws.Holder;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-4-25 上午11:40:36
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@WebService(name = "CertAuthBillServiceSoap", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface CertAuthBillServiceSoap {


    /**
     *测试连接
     *
     * @return
     *     returns boolean
     */
    @WebMethod(operationName = "TestConnection", action = "http://NewCapCert.com/CapCertAuthWebService/TestConnection")
    @WebResult(name = "TestConnectionResult", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
    @RequestWrapper(localName = "TestConnection", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.TestConnection")
    @ResponseWrapper(localName = "TestConnectionResponse", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.TestConnectionResponse")
    public boolean testConnection();

    /**
     *显示本证书前置Web服务的版本信息
     *
     * @return
     *     returns java.lang.String
     */
    @WebMethod(operationName = "GetCurrentVer", action = "http://NewCapCert.com/CapCertAuthWebService/GetCurrentVer")
    @WebResult(name = "GetCurrentVerResult", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
    @RequestWrapper(localName = "GetCurrentVer", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.GetCurrentVer")
    @ResponseWrapper(localName = "GetCurrentVerResponse", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.GetCurrentVerResponse")
    public String getCurrentVer();

    /**
     * 获取服务器时间ID
     *
     * @param serrinfo
     * @param getServerDtResult
     * @param serverDt
     */
    @WebMethod(operationName = "GetServerDt", action = "http://NewCapCert.com/CapCertAuthWebService/GetServerDt")
    @RequestWrapper(localName = "GetServerDt", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.GetServerDt")
    @ResponseWrapper(localName = "GetServerDtResponse", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.GetServerDtResponse")
    public void getServerDt(
        @WebParam(name = "ServerDt", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.INOUT)
        Holder<XMLGregorianCalendar> serverDt,
        @WebParam(name = "serrinfo", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.INOUT)
        Holder<String> serrinfo,
        @WebParam(name = "GetServerDtResult", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.OUT)
        Holder<Boolean> getServerDtResult);

    /**
     * 验签
     *
     * @param serrinfo
     * @param verifySignResult
     * @param strSigValue
     * @param strUserCert
     * @param strSigData
     */
    @WebMethod(operationName = "VerifySign", action = "http://NewCapCert.com/CapCertAuthWebService/VerifySign")
    @RequestWrapper(localName = "VerifySign", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.VerifySign")
    @ResponseWrapper(localName = "VerifySignResponse", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.VerifySignResponse")
    public void verifySign(
        @WebParam(name = "strSigData", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
        String strSigData,
        @WebParam(name = "strSigValue", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
        String strSigValue,
        @WebParam(name = "strUserCert", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
        String strUserCert,
        @WebParam(name = "serrinfo", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.INOUT)
        Holder<String> serrinfo,
        @WebParam(name = "VerifySignResult", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.OUT)
        Holder<Boolean> verifySignResult);

    /**
     * 身份验证
     *
     * @param serrinfo
     * @param strSigValue
     * @param verifyIdentityResult
     * @param strUserCert
     * @param strSigData
     */
    @WebMethod(operationName = "VerifyIdentity", action = "http://NewCapCert.com/CapCertAuthWebService/VerifyIdentity")
    @RequestWrapper(localName = "VerifyIdentity", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.VerifyIdentity")
    @ResponseWrapper(localName = "VerifyIdentityResponse", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", className = "cn.newcapec.function.platform.u.VerifyIdentityResponse")
    public void verifyIdentity(
        @WebParam(name = "strSigData", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
        String strSigData,
        @WebParam(name = "strSigValue", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
        String strSigValue,
        @WebParam(name = "strUserCert", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/")
        String strUserCert,
        @WebParam(name = "serrinfo", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.INOUT)
        Holder<String> serrinfo,
        @WebParam(name = "VerifyIdentityResult", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", mode = WebParam.Mode.OUT)
        Holder<Boolean> verifyIdentityResult);

}
