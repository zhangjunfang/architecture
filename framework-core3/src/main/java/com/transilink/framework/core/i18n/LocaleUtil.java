package com.transilink.framework.core.i18n;

import java.util.Locale;

import com.transilink.framework.core.utils.stringUtils.GlobalVariant;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public final class LocaleUtil {
	private static Locale loc = new Locale("zh", "", "");
	private static boolean inited = false;

	public static Locale getLocale() {
		return loc;
	}

	public static Locale getLocale(String lang) {
		return new Locale(lang, "", "");
	}

	private static void init() {
		if (!inited) {
			String lang = GlobalVariant.getVariant("lang");

			if (lang != null) {
				loc = new Locale(lang, "", "");
			}
		}
		inited = true;
	}

	static {
		init();
	}
}