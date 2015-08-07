package com.transilink.framework.core.exception.asserts;

import com.transilink.framework.core.exception.BaseException;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public abstract interface ErrorPost {
	public abstract Object doInstancePost() throws BaseException;
}