package com.transilink.framework.core.exception;

import java.sql.SQLException;

import org.apache.commons.lang.exception.ExceptionUtils;

import com.transilink.framework.core.context.TransilinkContext;
import com.transilink.framework.core.i18n.LangUtil;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("unused")
public class ExceptionUtil implements LogEnabled {
	private static final String ERROR_CODE = "err_code";

	public static void extractException(Throwable t, String lang,
			Object[] messageArgs) throws RuntimeException {
		extractException(t, lang, messageArgs, null);
	}

	public static void extractException(Throwable t, String lang,
			Object[] messageArgs, Object entityObject) throws RuntimeException {
		Throwable t1 = null;

		if ((t instanceof SysException)) {
			t1 = t;
		} else {
			t1 = getTargetAppException(t);
		}
		if (t1 == null) {
			t1 = ExceptionUtils.getRootCause(t);
		}
		if (t1 == null) {
			t1 = t;
		}

		String messageCode = StringUtil.trim(t1.getMessage());
		String errorMessage = null;
		if ((t1 instanceof SysException)) {
			if (messageCode.indexOf("err_code") != -1) {
				errorMessage = Message.getInfo(messageCode, lang, messageArgs,
						null, entityObject);
				if (StringUtil.isValid(errorMessage)) {
					throw new SysException(errorMessage, t1);
				}
			}
			throw ((SysException) t1);
		}
		if ((t1 instanceof SysException)) {
			if (messageCode.indexOf("err_code") != -1) {
				errorMessage = Message.getInfo(messageCode, lang, messageArgs,
						null, entityObject);
				if (StringUtil.isValid(errorMessage)) {
					throw new SysException(errorMessage, t1);
				}
			}
			throw ((SysException) t);
		}
		if ((t1 instanceof SQLException)) {
			SQLExceptionUtil.translateException(t);
		} else
			throw new SysException(t1.getMessage(), t1);
	}

	private static Throwable getTargetAppException(Throwable t) {
		Throwable t1 = null;
		do {
			t1 = ExceptionUtils.getCause(t);
			if ((t1 instanceof SysException)) {
				return t1;
			}
			t = t1;
		} while (t1 != null);

		return null;
	}

	public static void extractException(Throwable t, String lang)
			throws RuntimeException {
		extractException(t, lang, null, null);
	}

	public static void extractException(Throwable t) throws RuntimeException {
		String lang = LangUtil.getLang(TransilinkContext.getContext());
		extractException(t, lang);
	}

	public static void extractException(Throwable t, Object[] messageArgs)
			throws RuntimeException {
		String lang = LangUtil.getLang(TransilinkContext.getContext());
		extractException(t, lang, messageArgs, null);
	}

	public static void extractException(Throwable t, Object[] args,
			Object entityObject) {
		String lang = LangUtil.getLang(TransilinkContext.getContext());
		extractException(t, lang, args, entityObject);
	}
}