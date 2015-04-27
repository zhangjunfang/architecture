package com.transilink.framework.core.exception.asserts;

import com.transilink.framework.core.exception.BaseException;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class Assert {
	public static void isTrue(boolean exp, String message) {
		if (!exp)
			throw new BaseException(message);
	}
}