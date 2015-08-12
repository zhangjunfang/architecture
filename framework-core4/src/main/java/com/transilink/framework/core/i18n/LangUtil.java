package com.transilink.framework.core.i18n;

import com.transilink.framework.core.context.Context;
import com.transilink.framework.core.utils.stringUtils.GlobalVariant;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class LangUtil {
	public static String getLang(Context context) {
		String lang = null;

		lang = context.getParameter("lang");
		lang = StringUtil.trim(lang);
		if (lang.length() > 0) {
			return lang;
		}

		lang = (String) context.getAttribute("lang");
		lang = StringUtil.trim(lang);
		if (lang.length() > 0) {
			return lang;
		}

		lang = GlobalVariant.getVariant("lang");
		lang = StringUtil.trim(lang);
		if (lang.length() > 0) {
			return "zh";
		}

		return "zh";
	}
}