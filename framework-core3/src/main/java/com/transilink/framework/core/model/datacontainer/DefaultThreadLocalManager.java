package com.transilink.framework.core.model.datacontainer;

import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class DefaultThreadLocalManager implements ThreadLocalManager {
	private static ConcurrentHashMap<String, ThreadLocalContainer> allContainers = new ConcurrentHashMap<String, ThreadLocalContainer>();
	private static ThreadLocalContainer appContainer = new ThreadLocalContainer();
	private static ThreadLocalContainer appFrameworkContainer = new ThreadLocalContainer();
	private static ThreadLocalContainer frameworkContainer = new ThreadLocalContainer();

	public ThreadLocalContainer getAppContainer() {
		return appContainer;
	}

	public ThreadLocalContainer getAppFrameworkContainer() {
		return appFrameworkContainer;
	}

	public ThreadLocalContainer getFrameworkContainer() {
		return frameworkContainer;
	}

	public void registerContainer(String name, ThreadLocalContainer container) {
		if (allContainers.containsKey(name)) {
			throw new RuntimeException("注册失败,线程变量容器已存在!");
		}
		allContainers.put(name, container);
	}

	public void removeContainer(String name) {
		allContainers.remove(name);
	}
}