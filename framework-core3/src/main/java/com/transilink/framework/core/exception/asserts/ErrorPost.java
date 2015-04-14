package com.transilink.framework.core.exception.asserts;

import com.transilink.framework.core.exception.BaseException;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract interface ErrorPost {
	public abstract Object doInstancePost() throws BaseException;
}