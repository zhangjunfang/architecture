package com.transilink.framework.core.exception;

import org.apache.commons.lang.exception.ExceptionUtils;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class SQLExceptionUtil {
	public static void translateException(Throwable t) {
		Throwable t1 = null;
		Throwable t2 = null;

		while (t != null) {
			t2 = t1;
			t1 = t;
			t = ExceptionUtils.getCause(t);
		}

		throw new SysException(t2.getMessage(), t2);
	}
}