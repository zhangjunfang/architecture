package com.transilink.framework.core.utils.encryptUtils;

import java.security.MessageDigest;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

/**
 * MD5加密工具类
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class MD5 {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	/**
	 * 转换字节数组为16进制字串
	 *
	 * @param b
	 *            字节数组
	 * @return 16进制字串
	 */

	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * String sign = MD5.MD5Encode(userid + map2Str(urlKeyValueMap) +
	 * String.valueOf(t) + secret); // 签名串
	 *
	 * @param params
	 * @return
	 */
	public static String map2Str(TreeMap<String, String> params) {
		String ret = "";
		if (params == null || params.isEmpty())
			return ret;
		Set<String> keySet = params.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = params.get(key);
			ret += key + value;
		}

		return ret;
	}

	/**
	 * 生成MD5
	 *
	 * @param string
	 * @return
	 */
	public static String MD5Encode(String string) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
				'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] bytes = string.getBytes("UTF-8");
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(bytes);
			byte[] updateBytes = messageDigest.digest();
			int len = updateBytes.length;
			char myChar[] = new char[len * 2];
			int k = 0;
			for (int i = 0; i < len; i++) {
				byte byte0 = updateBytes[i];
				myChar[k++] = hexDigits[byte0 >>> 4 & 0x0f];
				myChar[k++] = hexDigits[byte0 & 0x0f];
			}
			return new String(myChar);
		} catch (Exception e) {

		}

		return "";
	}

}