/**
 *
 */
package com.ocean.test;



import com.ocean.test.CustGrantInfoStructure.CUSTGRANTINFO;
import com.sun.jna.Native;

/**
*
* @Description :
* @author : ocean
* @date : 2014-5-13 上午10:33:15
* @email : zhangjunfang0505@163.com
* @Copyright : newcapec zhengzhou
*/
public class ParseGrantFile {

	static {
		String path = Thread.currentThread().getContextClassLoader()
				.getResource("SoftGrant.dll").toString()
				.replaceFirst("file:/", "");
		System.load(path);
	}

	public static void main(String[] args) throws Exception {
		CustGrantInfoStructure infoStructure = (CustGrantInfoStructure) Native
				.loadLibrary("SoftGrant", CustGrantInfoStructure.class);
		// String b=new String();
		// int i=infoStructure.GetCharacterCode(b);
		//
		// System.out.println(i+"============="+b.length());
		CUSTGRANTINFO info = new CUSTGRANTINFO();
		Integer  integer=new Integer(0);
		infoStructure.ParseGrantFile(
				"12121212121212121121212121212121", DllInvoke.rtfPath, info, integer);

		System.out.println(integer);
		System.out.println(new String(info.CustomerName));
	}

}
