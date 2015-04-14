package com.transilink.framework.core.model.dbmeta;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract interface IErrorHandler {
	public abstract void onError(String paramString, Throwable paramThrowable);
}