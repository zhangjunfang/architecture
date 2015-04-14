package com.transilink.framework.core.utils.httpUtils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.restlet.data.Request;

import com.noelios.restlet.ext.servlet.ServletCall;
import com.noelios.restlet.http.HttpCall;
import com.noelios.restlet.http.HttpRequest;
import com.transilink.framework.core.logs.LogEnabled;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class WebUtils implements LogEnabled {

	/**
	 *
	 */
	private static final long serialVersionUID = -7121096516187376689L;

	/**
	 * 获取系统的Session对象
	 *
	 * @return 当前的Session
	 */
	public static final HttpSession getSession(Request request) {
		HttpSession session = null;

		if (request instanceof HttpRequest) {
			final HttpCall httpCall = ((HttpRequest) request).getHttpCall();

			if (httpCall instanceof ServletCall) {
				session = ((ServletCall) httpCall).getRequest().getSession();
			}
		}
		return session;
	}

	/**
	 * 获取系统的reqeust对象 绝大多数情况下都不需要调用此方法
	 *
	 * @return 当前的request
	 */
	public static final HttpServletRequest getRequests(Request request) {
		HttpServletRequest result = null;

		if (request instanceof HttpRequest) {
			final HttpCall httpCall = ((HttpRequest) request).getHttpCall();

			if (httpCall instanceof ServletCall) {
				result = ((ServletCall) httpCall).getRequest();
			}
		}

		return result;
	}

	/**
	 * 获取response对象 绝大多数情况下都不需要调用此方法
	 *
	 * @return 当前的response
	 */
	public static HttpServletResponse getResponse(Request request) {
		HttpServletResponse result = null;

		if (request instanceof HttpRequest) {
			final HttpCall httpCall = ((HttpRequest) request).getHttpCall();

			if (httpCall instanceof ServletCall) {
				result = ((ServletCall) httpCall).getResponse();
			}
		}

		return result;
	}

	/**
	 * 获取图片扩展名
	 *
	 * @return
	 */
	public static String getExt(String fileName) {

		return fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();
	}

	/***
	 * 获取URI的路径,如路径为http://www.baidu.com/action/post.htm?method=add,
	 * 得到的值为"/action/post.htm"
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestURI(HttpServletRequest request) {

		return request.getRequestURI();
	}

	/**
	 * 获取完整请求路径(含内容路径及请求参数)
	 *
	 * @param request
	 * @return
	 */
	public static String getRequestURIWithParam(HttpServletRequest request) {

		return getRequestURI(request)
				+ (request.getQueryString() == null ? "" : "?"
						+ request.getQueryString());
	}

	/**
	 * 处理中文问题,实现编码转换
	 *
	 * @param str
	 * @return
	 */
	public static String toChinese(String str) {

		String strResult = "";
		try {
			if (str != null) {
				strResult = new String(str.getBytes("ISO8859_1"), "UTF-8");
				// strResult = str;
			} else {
				strResult = "";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strResult;
	}

	/**
	 * 根据字符串的标记将字符串拆分成Integer数组
	 *
	 * @param str
	 *            字符
	 * @param flg
	 *            标记
	 * @return 整形数组
	 */
	public static Object[] toObjectByStringFlg(String str, String flg) {

		if (str == null || flg == null) {
			return null;
		}
		String[] strArray = str.split(flg);
		Object[] nArray = new Object[strArray.length];
		for (int i = 0; i < strArray.length; i++) {
			try {
				Object temp = strArray[i];
				nArray[i] = temp;
			} catch (Exception e) {
				System.out.println("类型转换出错 StringUtil.class");
			}
		}
		return nArray;
	}

	/**
	 * 去除html代码
	 *
	 * @param inputString
	 * @return
	 */
	public static String HtmltoText(String inputString) {

		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		java.util.regex.Pattern p_ba;
		java.util.regex.Matcher m_ba;

		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
																										// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
																									// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
			String patternStr = "\\s+";

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签

			p_ba = Pattern.compile(patternStr, Pattern.CASE_INSENSITIVE);
			m_ba = p_ba.matcher(htmlStr);
			htmlStr = m_ba.replaceAll(""); // 过滤空格

			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}
		return textStr;// 返回文本字符串
	}

	/**
	 * 金额格式化
	 *
	 * @param s
	 *            指数
	 * @param len
	 *            小数位数
	 * @return
	 */
	public static String formory(Double s, int len) {

		if (s == null) {
			return "";
		}
		NumberFormat formater = null;
		// double num = Double.parseDouble(s);
		if (len == 0) {
			formater = new DecimalFormat("###,###");

		} else {
			StringBuffer buff = new StringBuffer();
			buff.append("###,###.");
			for (int i = 0; i < len; i++) {
				buff.append("#");
			}
			formater = new DecimalFormat(buff.toString());
		}
		return formater.format(s);
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
	 * 添加cookie
	 *
	 * @param response
	 * @param name
	 *            cookie的名称
	 * @param value
	 *            cookie的值
	 * @param maxAge
	 *            cookie存放的时间(以秒为单位,假如存放三天,即3*24*60*60; 如果值为0,cookie将随浏览器关闭而清除)
	 */
	public static void addCookie(HttpServletResponse response, String name,
			String value, int maxAge) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		if (maxAge > 0)
			cookie.setMaxAge(maxAge);
		response.addCookie(cookie);
	}

	/**
	 * 获取cookie的值
	 *
	 * @param request
	 * @param name
	 *            cookie的名称
	 * @return
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		Map<String, Cookie> cookieMap = WebUtils.readCookieMap(request);
		if (cookieMap.containsKey(name)) {
			Cookie cookie = (Cookie) cookieMap.get(name);
			return cookie.getValue();
		} else {
			return null;
		}
	}

	protected static Map<String, Cookie> readCookieMap(
			HttpServletRequest request) {
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		Cookie[] cookies = request.getCookies();
		if (null != cookies) {
			for (int i = 0; i < cookies.length; i++) {
				cookieMap.put(cookies[i].getName(), cookies[i]);
			}
		}
		return cookieMap;
	}

	/**
	 *
	 * 获取应用动态域名地址
	 */
	public static String getDynmicName(HttpServletRequest httpRequest) {

		String dyName = "http://" + httpRequest.getLocalName() + ":"
				+ httpRequest.getLocalPort() + httpRequest.getContextPath();

		return dyName;
	}

	/**
	 *
	 * 格式化手机
	 */
	public static String formatPhone(String phone) {

		if (null != phone && !"".equals(phone)) {
			StringBuffer sb = new StringBuffer(phone);
			return sb.replace(3, 7, "****").toString();
		}

		return "";
	}

	/**
	 *
	 * 格式化邮箱
	 */
	public static String formatEmail(String email) {

		if (null != email && !"".equals(email)) {
			StringBuffer sb = new StringBuffer(email);
			return sb.replace(sb.indexOf("@") - 2, sb.indexOf("@"), "**")
					.toString();
		}

		return "";
	}
}
