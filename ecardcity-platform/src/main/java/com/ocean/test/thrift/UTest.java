/**
 *
 */
package com.ocean.test.thrift;

//import cn.newcapec.function.platform.services.SecretKey;
//import cn.newcapec.function.platform.services.SecretKeySoap;
import cn.newcapec.function.platform.u.CertAuthBillService;
import cn.newcapec.function.platform.u.CertAuthBillServiceSoap;

/**
 * @author ocean
 * @date : 2014-4-18 下午04:55:13
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
public class UTest {


	public  static  void  testU(){
		CertAuthBillService authBillService = new CertAuthBillService();

		CertAuthBillServiceSoap soap = authBillService
				.getCertAuthBillServiceSoap();

		System.err.println(soap.getCurrentVer());

		System.err.println(soap.testConnection());

		// System.err.println(soap.verifyIdentity(strSigData, strSigValue,
		// strUserCert, serrinfo, verifyIdentityResult));

		// System.err.println(soap.verifySign(strSigData,
		// strSigValue,strUserCert, serrinfo, verifySignResult));

	}
	public static void main(String[] args) {
//     SecretKey  key=new SecretKey();
//     SecretKeySoap  soap=key.getSecretKeySoap();
//     soap.getClass();
    // soap.retrieveSysInfo(sessionID, customerInfo, retrieveSysInfoResult)
	}

}
