package com.transilink.framework.core.utils.httpUtils;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * 用于编码和解码字符串中的不安全字符，使之可以在各种网络环境中安全的传输。
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public final class EscapeUtil {
	/**
	 * @param c
	 * @return
	 */
	private static String toHex(char c) {
		return Integer.toHexString(c).toUpperCase();
	}

	/**
	 * 原始字符串进行编码，返回安全字符串.
	 *
	 * @param plainText
	 *            原始字符串。
	 * @return 编码后的安全字符串。
	 */
	public static final String escape(String plainText) {
		if (plainText == null)
			return "";
		int i = plainText.length();
		StringBuffer result = new StringBuffer(i);
		for (int j = 0; j < i; ++j) {
			char c;
			if ((c = plainText.charAt(j)) > 4095)
				result.append("%u" + toHex(c));
			else if (c > 255)
				result.append("%u0" + toHex(c));
			else if (c > '')
				result.append("%u00" + toHex(c));
			else if (((c >= '0') && (c <= '9')) || ((c >= 'A') && (c <= 'Z'))
					|| ((c >= 'a') && (c <= 'z')))
				result.append(c);
			else if (c > '\15')
				result.append("%" + toHex(c));
			else
				result.append("%0" + toHex(c));
		}
		return result.toString();
	}

	public static String UrlEncode(String str) {
		return UrlEncode(str, "UTF-8");
	}

	/**
	 * @see java.net.URLDecoder#(java.lang.String, java.lang.String)
	 */
	public static String UrlEncode(String str, String type) {
		try {
			return java.net.URLEncoder.encode(str, type);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("字符串编码错误", ex);
		}
	}

	public static String UrlDecode(String str) {
		return UrlDecode(str);
	}

	/**
	 * @see java.net.URLDecoder#decode(java.lang.String, java.lang.String)
	 */
	public static String UrlDecode(String str, String type) {
		try {
			return java.net.URLDecoder.decode(str, type);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("字符串解码错误", ex);
		}
	}

	/**
	 * 对安全字符串进行解码，返回原始字符串
	 *
	 * @param encodedText
	 *            编码后安全字符串。
	 * @return 原始字符串。
	 */
	public static final String unescape(String encodedText) {
		if (encodedText == null)
			return "";
		StringBuffer temp = new StringBuffer(4);
		StringBuffer result = new StringBuffer();
		int len = encodedText.length();
		int j = 0;
		int k = 0;
		int l = 0;
		char c1 = 0;
		for (int i = 0; i < len; ++i) {
			char c = encodedText.charAt(i);
			if (k != 0) {
				if (j != 0) {
					j = 0;
					if (c == 'u') {
						l = 1;
						k = 1;
						continue;
					}
				}
				temp.append(c);
				if (temp.length() != ((l != 0) ? 4 : 2)) {
					k = 1;
					continue;
				}
				try {
					int i2 = Integer.parseInt(temp.toString(), 16);
					result.append((char) i2);
					temp.setLength(0);
					k = 0;
					l = 0;
					continue;
				} catch (NumberFormatException ex) {
					throw new IllegalArgumentException(
							"Unable to parse unicode value: " + temp);
				}
			}
			if ((c == '%') || (c == '$')) {
				j = 1;
				c1 = c;
				k = 1;

			} else {
				result.append(c);
			}
		}
		if (j != 0)
			result.append(c1);
		return result.toString();
	}

	/**
	 *
	 * 解析xml
	 *
	 * @param xml
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static final String escapeXml(String xml)
			throws UnsupportedEncodingException {
		return StringEscapeUtils.escapeXml(xml);
	}

	@SuppressWarnings("unchecked")
	public static void requestConvertPostUrlEncode(HttpServletRequest request,
			String FromCodeName, String ToCodeName, String qs) {
		String[] paramValues;
		int i;
		String paramName = "";

		Enumeration<String> paramNames = (Enumeration<String>) request
				.getParameterNames();
		while (true) {
			do {
				if (!(paramNames.hasMoreElements()))
					return;
				paramName = (String) paramNames.nextElement();
			} while (qs.indexOf(paramName + "=") <= -1);

			paramValues = request.getParameterValues(paramName);
			for (i = 0; i < paramValues.length; ++i)
				paramValues[i] = convertCharacterSet(paramValues[i],
						FromCodeName, ToCodeName);
		}
	}

	public static final String convertCharacterSet(String str,
			String formCharacterSet, String toCharacterSet) {
		String convertedStr = null;
		try {
			convertedStr = new String(str.getBytes(formCharacterSet),
					toCharacterSet);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedStr;
	}

	@SuppressWarnings("unchecked")
	public static void requestConvertEncode(HttpServletRequest request,
			String fromCodeName, String toCodeName) {
		String[] paramValues;
		int i;
		String paramName = "";

		Enumeration<String> paramNames = request.getParameterNames();
		while (paramNames.hasMoreElements()) {
			paramName = (String) paramNames.nextElement();

			paramValues = request.getParameterValues(paramName);
			for (i = 0; i < paramValues.length; ++i)
				paramValues[i] = convertCharacterSet(paramValues[i],
						fromCodeName, toCodeName);
		}
	}

	/**
	 * 获取图片扩展名
	 *
	 * @param
	 * @return
	 */
	public static String getExt(String fileName) {

		return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
	}

}