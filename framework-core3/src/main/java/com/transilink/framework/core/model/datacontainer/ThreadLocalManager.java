package com.transilink.framework.core.model.datacontainer;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract interface ThreadLocalManager {
	public abstract ThreadLocalContainer getFrameworkContainer();

	public abstract ThreadLocalContainer getAppFrameworkContainer();

	public abstract ThreadLocalContainer getAppContainer();

	public abstract void registerContainer(String paramString,
			ThreadLocalContainer paramThreadLocalContainer);

	public abstract void removeContainer(String paramString);
}