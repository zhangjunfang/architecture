package com.ocean.rpc.io;

import java.util.concurrent.ConcurrentHashMap;

public final class RpcClassManager {
	private final static ConcurrentHashMap<Class<?>, String> classCache1 = new ConcurrentHashMap<Class<?>, String>();
	private final static ConcurrentHashMap<String, Class<?>> classCache2 = new ConcurrentHashMap<String, Class<?>>();

	private RpcClassManager() {
	}

	public final static void register(Class<?> type, String alias) {
		classCache1.put(type, alias);
		classCache2.put(alias, type);
	}

	public final static String getClassAlias(Class<?> type) {
		return classCache1.get(type);
	}

	public final static Class<?> getClass(String alias) {
		return classCache2.get(alias);
	}

	public final static boolean containsClass(String alias) {
		return classCache2.containsKey(alias);
	}
}