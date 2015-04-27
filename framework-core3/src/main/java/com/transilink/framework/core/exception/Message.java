package com.transilink.framework.core.exception;

import java.text.MessageFormat;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;

/**
 * 国际化消息查找. 国际化消息查找.可支持从数据库或资源文件查找.
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public class Message implements LogEnabled {

	private Message() {
	}

	/**
	 * 从数据库查找国际化消息,未找到返回NULL.
	 *
	 * @param infoCode
	 *            消息代码.
	 * @param lang
	 *            语种.
	 * @return 国际化消息,未找到返回NULL.
	 */
	public static String getInfo(String infoCode, String lang) {
		return getInfo(infoCode, lang, null, null, null);
	}

	/**
	 * 从数据库查找国际化消息,未找到返回NULL.
	 *
	 * @param infoCode
	 *            消息代码.
	 * @param lang
	 *            语种.
	 * @param parms
	 *            消息参数.
	 * @return 国际化消息,未找到返回NULL.
	 */
	public static String getInfo(String infoCode, String lang, String[] parms) {
		return getInfo(infoCode, lang, parms, null, null);
	}

	/**
	 * 从资源文件查找国际化消息,未找到返回NULL.
	 *
	 * @param infoCode
	 *            消息代码.
	 * @param lang
	 *            语种.
	 * @return 国际化消息,未找到返回NULL.
	 */
	public static String getInfo(String infoCode, String lang, String resName) {
		return getInfo(infoCode, lang, null, resName, null);
	}

	/**
	 *
	 * 从平台 后台数据库表获取国际化信息
	 *
	 * @param infoCode
	 *            语言代码
	 * @param lang
	 *            语种
	 * @return
	 */
	private static String getInfoFromDB(String infoCode, String lang) {

		return null;
	}

	private static String getInfoFromRes(String infoCode, String lang,
			String resName) {

		return null;
	}

	/**
	 * 从资源文件查找国际化消息,未找到返回NULL.
	 *
	 * @param infoCode
	 *            消息代码.
	 * @param lang
	 *            语种.
	 * @param parms
	 *            消息参数.
	 * @return 国际化消息,未找到返回NULL.
	 */
	public static String getInfo(String infoCode, String lang, Object[] parms,
			String resName, Object entityObject) {
		String s = null;
		infoCode = StringUtils.trim(infoCode);
		resName = StringUtils.trim(resName);

		if (infoCode.length() < 1) {
			return s;
		}

		if (resName.length() > 0) { // from resoucebundle
			s = getInfoFromRes(infoCode, lang, resName);
		} else { // from DB
			s = getInfoFromDB(infoCode, lang);
		}

		if (s == null || parms == null || parms.length < 1) {
			return s;
		} else {
			try {
				if (entityObject != null) {
					Map params = BeanUtils.describe(entityObject);
					if (params != null && !params.isEmpty()) {
						// 
					}
				}
				String msg = MessageFormat.format(s, parms);

				return msg;

			} catch (Exception e) {
				return s;
			}
		}

	}

	/**
	 * 按语种默认载入国际化消息.
	 */
	private static void load(String lang) {

		try {

			String sql = "select msg_id,msg_value from ctl_langmsg where lang_code='"
					+ lang + "' order by msg_id";

		} catch (Exception e) {
			log.error("获取国际资源出错!!", e);
		} finally {

		}

	}

	// static {
	// load(GlobalVariant.getVariant(Keys.LANG));
	// }
}