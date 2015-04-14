package com.transilink.framework.core.biz;

import com.transilink.framework.core.logs.LogEnabled;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 * @param <T>
 */
public abstract interface BaseService<T> extends LogEnabled {
	public abstract void removeUnused(String paramString);

	public abstract T get(String paramString);

	public abstract void saveOrUpdate(T paramT);
}