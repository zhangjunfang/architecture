package com.transilink.framework.core.model.dbmeta;

import com.transilink.framework.core.logs.LogEnabled;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class DefaultErrorHandler implements IErrorHandler, LogEnabled {
	public void onError(String s, Throwable throwable) {
		log.error(throwable);
	}
}