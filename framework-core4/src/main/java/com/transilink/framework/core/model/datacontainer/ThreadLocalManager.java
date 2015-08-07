package com.transilink.framework.core.model.datacontainer;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
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