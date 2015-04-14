package com.transilink.framework.core.exception.asserts;

import com.transilink.framework.core.exception.BaseException;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class Assert {
	public static void isTrue(boolean exp, String message) {
		if (!exp)
			throw new BaseException(message);
	}
}