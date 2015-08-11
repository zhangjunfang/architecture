
package cn.newcapec.function.platform.u;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-4-25 上午11:40:22
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@WebServiceClient(name = "CertAuthBillService", targetNamespace = "http://NewCapCert.com/CapCertAuthWebService/", wsdlLocation = "http://192.168.60.22/CertAuthBillPreServiceZhengShi/CertAuthBillWebService.asmx?wsdl")
public class CertAuthBillService
    extends Service
{

    private final static URL CERTAUTHBILLSERVICE_WSDL_LOCATION;
    private final static WebServiceException CERTAUTHBILLSERVICE_EXCEPTION;
    private final static QName CERTAUTHBILLSERVICE_QNAME = new QName("http://NewCapCert.com/CapCertAuthWebService/", "CertAuthBillService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://192.168.60.22/CertAuthBillPreServiceZhengShi/CertAuthBillWebService.asmx?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CERTAUTHBILLSERVICE_WSDL_LOCATION = url;
        CERTAUTHBILLSERVICE_EXCEPTION = e;
    }

    public CertAuthBillService() {
        super(__getWsdlLocation(), CERTAUTHBILLSERVICE_QNAME);
    }

    public CertAuthBillService(WebServiceFeature... features) {
        super(__getWsdlLocation(), CERTAUTHBILLSERVICE_QNAME, features);
    }

    public CertAuthBillService(URL wsdlLocation) {
        super(wsdlLocation, CERTAUTHBILLSERVICE_QNAME);
    }

    public CertAuthBillService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CERTAUTHBILLSERVICE_QNAME, features);
    }

    public CertAuthBillService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CertAuthBillService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns CertAuthBillServiceSoap
     */
    @WebEndpoint(name = "CertAuthBillServiceSoap")
    public CertAuthBillServiceSoap getCertAuthBillServiceSoap() {
        return super.getPort(new QName("http://NewCapCert.com/CapCertAuthWebService/", "CertAuthBillServiceSoap"), CertAuthBillServiceSoap.class);
    }

    /**
     *
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CertAuthBillServiceSoap
     */
    @WebEndpoint(name = "CertAuthBillServiceSoap")
    public CertAuthBillServiceSoap getCertAuthBillServiceSoap(WebServiceFeature... features) {
        return super.getPort(new QName("http://NewCapCert.com/CapCertAuthWebService/", "CertAuthBillServiceSoap"), CertAuthBillServiceSoap.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CERTAUTHBILLSERVICE_EXCEPTION!= null) {
            throw CERTAUTHBILLSERVICE_EXCEPTION;
        }
        return CERTAUTHBILLSERVICE_WSDL_LOCATION;
    }

}
