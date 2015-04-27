package com.transilink.framework.core.model.dbmeta;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public abstract interface IErrorHandler {
	public abstract void onError(String paramString, Throwable paramThrowable);
}