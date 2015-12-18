package com.ocean.rpc.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

public class RpcMethods {

	protected ConcurrentHashMap<String, ConcurrentHashMap<Integer, RpcMethod>> remoteMethods = new ConcurrentHashMap<String, ConcurrentHashMap<Integer, RpcMethod>>();
	protected ConcurrentHashMap<String, String> methodNames = new ConcurrentHashMap<String, String>();

	public RpcMethods() {
	}

	public RpcMethod get(String aliasName, int paramCount) {
		ConcurrentHashMap<Integer, RpcMethod> methods = remoteMethods
				.get(aliasName);
		if (methods == null) {
			return null;
		}
		return methods.get(paramCount);
	}

	public Collection<String> getAllNames() {
		return methodNames.values();
	}

	public int getCount() {
		return remoteMethods.size();
	}

	protected int getCount(Type[] paramTypes) {
		int i = paramTypes.length;
		if ((i > 0) && (paramTypes[i - 1] instanceof Class<?>)) {
			Class<?> paramType = (Class<?>) paramTypes[i - 1];
			if (paramType.equals(RpcContext.class)) {
				--i;
			}
		}
		return i;
	}

	void addMethod(String aliasName, RpcMethod method) {
		ConcurrentHashMap<Integer, RpcMethod> methods;
		String name = aliasName.toLowerCase();
		if (remoteMethods.containsKey(name)) {
			methods = remoteMethods.get(name);
		} else {
			methods = new ConcurrentHashMap<Integer, RpcMethod>();
			methodNames.put(name, aliasName);
		}
		if (aliasName.equals("*")
				&& (!((method.paramTypes.length == 2)
						&& method.paramTypes[0].equals(String.class) && method.paramTypes[1]
							.equals(Object[].class)))) {
			return;
		}
		int i = getCount(method.paramTypes);
		methods.put(i, method);
		remoteMethods.put(name, methods);
	}

	public void addMethod(Method method, Object obj, String aliasName) {
		addMethod(aliasName, new RpcMethod(method, obj));
	}

	public void addMethod(Method method, Object obj, String aliasName,
			RpcResultMode mode) {
		addMethod(aliasName, new RpcMethod(method, obj, mode));
	}

	public void addMethod(Method method, Object obj, String aliasName,
			boolean simple) {
		addMethod(aliasName, new RpcMethod(method, obj, simple));
	}

	public void addMethod(Method method, Object obj, String aliasName,
			RpcResultMode mode, boolean simple) {
		addMethod(aliasName, new RpcMethod(method, obj, mode, simple));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			String aliasName) throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, obj, paramTypes));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			String aliasName, RpcResultMode mode) throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, obj, paramTypes, mode));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			String aliasName, boolean simple) throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, obj, paramTypes, simple));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			String aliasName, RpcResultMode mode, boolean simple)
			throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, obj, paramTypes, mode,
				simple));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, String aliasName)
			throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, type, paramTypes));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, String aliasName, RpcResultMode mode)
			throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, type, paramTypes, mode));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, String aliasName, boolean simple)
			throws NoSuchMethodException {
		addMethod(aliasName,
				new RpcMethod(methodName, type, paramTypes, simple));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, String aliasName, RpcResultMode mode,
			boolean simple) throws NoSuchMethodException {
		addMethod(aliasName, new RpcMethod(methodName, type, paramTypes, mode,
				simple));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes)
			throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, obj, paramTypes));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			RpcResultMode mode) throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, obj, paramTypes, mode));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			boolean simple) throws NoSuchMethodException {
		addMethod(methodName,
				new RpcMethod(methodName, obj, paramTypes, simple));
	}

	public void addMethod(String methodName, Object obj, Class<?>[] paramTypes,
			RpcResultMode mode, boolean simple) throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, obj, paramTypes, mode,
				simple));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes) throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, type, paramTypes));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, RpcResultMode mode)
			throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, type, paramTypes, mode));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, boolean simple) throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, type, paramTypes,
				simple));
	}

	public void addMethod(String methodName, Class<?> type,
			Class<?>[] paramTypes, RpcResultMode mode, boolean simple)
			throws NoSuchMethodException {
		addMethod(methodName, new RpcMethod(methodName, type, paramTypes, mode,
				simple));
	}

	private void addMethod(String methodName, Object obj, Class<?> type,
			String aliasName, RpcResultMode mode, boolean simple) {
		Method[] methods = type.getMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName())
					&& ((obj == null) == Modifier.isStatic(method
							.getModifiers()))) {
				addMethod(aliasName, new RpcMethod(method, obj, mode, simple));
			}
		}
	}

	private void addMethod(String methodName, Object obj, Class<?> type,
			String aliasName) {
		addMethod(methodName, obj, type, aliasName, RpcResultMode.Normal, false);
	}

	public void addMethod(String methodName, Object obj, String aliasName) {
		addMethod(methodName, obj, obj.getClass(), aliasName);
	}

	public void addMethod(String methodName, Object obj, String aliasName,
			RpcResultMode mode) {
		addMethod(methodName, obj, obj.getClass(), aliasName, mode, false);
	}

	public void addMethod(String methodName, Object obj, String aliasName,
			boolean simple) {
		addMethod(methodName, obj, obj.getClass(), aliasName,
				RpcResultMode.Normal, simple);
	}

	public void addMethod(String methodName, Object obj, String aliasName,
			RpcResultMode mode, boolean simple) {
		addMethod(methodName, obj, obj.getClass(), aliasName, mode, simple);
	}

	public void addMethod(String methodName, Class<?> type, String aliasName) {
		addMethod(methodName, null, type, aliasName);
	}

	public void addMethod(String methodName, Class<?> type, String aliasName,
			RpcResultMode mode) {
		addMethod(methodName, null, type, aliasName, mode, false);
	}

	public void addMethod(String methodName, Class<?> type, String aliasName,
			boolean simple) {
		addMethod(methodName, null, type, aliasName, RpcResultMode.Normal,
				simple);
	}

	public void addMethod(String methodName, Class<?> type, String aliasName,
			RpcResultMode mode, boolean simple) {
		addMethod(methodName, null, type, aliasName, mode, simple);
	}

	public void addMethod(String methodName, Object obj) {
		addMethod(methodName, obj, methodName);
	}

	public void addMethod(String methodName, Object obj, RpcResultMode mode) {
		addMethod(methodName, obj, methodName, mode, false);
	}

	public void addMethod(String methodName, Object obj, boolean simple) {
		addMethod(methodName, obj, methodName, RpcResultMode.Normal, simple);
	}

	public void addMethod(String methodName, Object obj, RpcResultMode mode,
			boolean simple) {
		addMethod(methodName, obj, methodName, mode, simple);
	}

	public void addMethod(String methodName, Class<?> type) {
		addMethod(methodName, type, methodName);
	}

	public void addMethod(String methodName, Class<?> type, RpcResultMode mode) {
		addMethod(methodName, type, methodName, mode, false);
	}

	public void addMethod(String methodName, Class<?> type, boolean simple) {
		addMethod(methodName, type, methodName, RpcResultMode.Normal, simple);
	}

	public void addMethod(String methodName, Class<?> type, RpcResultMode mode,
			boolean simple) {
		addMethod(methodName, type, methodName, mode, simple);
	}

	private void addMethods(String[] methodNames, Object obj, Class<?> type,
			String[] aliasNames, RpcResultMode mode, boolean simple) {
		Method[] methods = type.getMethods();
		for (int i = 0; i < methodNames.length; ++i) {
			String methodName = methodNames[i];
			String aliasName = aliasNames[i];
			for (Method method : methods) {
				if (methodName.equals(method.getName())
						&& ((obj == null) == Modifier.isStatic(method
								.getModifiers()))) {
					addMethod(aliasName, new RpcMethod(method, obj, mode,
							simple));
				}
			}
		}
	}

	private void addMethods(String[] methodNames, Object obj, Class<?> type,
			String[] aliasNames) {
		addMethods(methodNames, obj, type, aliasNames, RpcResultMode.Normal,
				false);
	}

	private void addMethods(String[] methodNames, Object obj, Class<?> type,
			String aliasPrefix, RpcResultMode mode, boolean simple) {
		String[] aliasNames = new String[methodNames.length];
		for (int i = 0; i < methodNames.length; ++i) {
			aliasNames[i] = aliasPrefix + "_" + methodNames[i];
		}
		addMethods(methodNames, obj, type, aliasNames, mode, simple);
	}

	private void addMethods(String[] methodNames, Object obj, Class<?> type,
			String aliasPrefix) {
		addMethods(methodNames, obj, type, aliasPrefix, RpcResultMode.Normal,
				false);
	}

	private void addMethods(String[] methodNames, Object obj, Class<?> type,
			RpcResultMode mode, boolean simple) {
		addMethods(methodNames, obj, type, methodNames, mode, simple);
	}

	private void addMethods(String[] methodNames, Object obj, Class<?> type) {
		addMethods(methodNames, obj, type, methodNames, RpcResultMode.Normal,
				false);
	}

	public void addMethods(String[] methodNames, Object obj, String[] aliasNames) {
		addMethods(methodNames, obj, obj.getClass(), aliasNames);
	}

	public void addMethods(String[] methodNames, Object obj,
			String[] aliasNames, RpcResultMode mode) {
		addMethods(methodNames, obj, obj.getClass(), aliasNames, mode, false);
	}

	public void addMethods(String[] methodNames, Object obj,
			String[] aliasNames, boolean simple) {
		addMethods(methodNames, obj, obj.getClass(), aliasNames,
				RpcResultMode.Normal, simple);
	}

	public void addMethods(String[] methodNames, Object obj,
			String[] aliasNames, RpcResultMode mode, boolean simple) {
		addMethods(methodNames, obj, obj.getClass(), aliasNames, mode, simple);
	}

	public void addMethods(String[] methodNames, Object obj, String aliasPrefix) {
		addMethods(methodNames, obj, obj.getClass(), aliasPrefix);
	}

	public void addMethods(String[] methodNames, Object obj,
			String aliasPrefix, RpcResultMode mode) {
		addMethods(methodNames, obj, obj.getClass(), aliasPrefix, mode, false);
	}

	public void addMethods(String[] methodNames, Object obj,
			String aliasPrefix, boolean simple) {
		addMethods(methodNames, obj, obj.getClass(), aliasPrefix,
				RpcResultMode.Normal, simple);
	}

	public void addMethods(String[] methodNames, Object obj,
			String aliasPrefix, RpcResultMode mode, boolean simple) {
		addMethods(methodNames, obj, obj.getClass(), aliasPrefix, mode, simple);
	}

	public void addMethods(String[] methodNames, Object obj) {
		addMethods(methodNames, obj, obj.getClass());
	}

	public void addMethods(String[] methodNames, Object obj, RpcResultMode mode) {
		addMethods(methodNames, obj, obj.getClass(), mode, false);
	}

	public void addMethods(String[] methodNames, Object obj, boolean simple) {
		addMethods(methodNames, obj, obj.getClass(), RpcResultMode.Normal,
				simple);
	}

	public void addMethods(String[] methodNames, Object obj,
			RpcResultMode mode, boolean simple) {
		addMethods(methodNames, obj, obj.getClass(), mode, simple);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String[] aliasNames) {
		addMethods(methodNames, null, type, aliasNames);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String[] aliasNames, RpcResultMode mode) {
		addMethods(methodNames, null, type, aliasNames, mode, false);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String[] aliasNames, boolean simple) {
		addMethods(methodNames, null, type, aliasNames, RpcResultMode.Normal,
				simple);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String[] aliasNames, RpcResultMode mode, boolean simple) {
		addMethods(methodNames, null, type, aliasNames, mode, simple);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String aliasPrefix) {
		addMethods(methodNames, null, type, aliasPrefix);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String aliasPrefix, RpcResultMode mode) {
		addMethods(methodNames, null, type, aliasPrefix, mode, false);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String aliasPrefix, boolean simple) {
		addMethods(methodNames, null, type, aliasPrefix, RpcResultMode.Normal,
				simple);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			String aliasPrefix, RpcResultMode mode, boolean simple) {
		addMethods(methodNames, null, type, aliasPrefix, mode, simple);
	}

	public void addMethods(String[] methodNames, Class<?> type) {
		addMethods(methodNames, null, type);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			RpcResultMode mode) {
		addMethods(methodNames, null, type, mode, false);
	}

	public void addMethods(String[] methodNames, Class<?> type, boolean simple) {
		addMethods(methodNames, null, type, RpcResultMode.Normal, simple);
	}

	public void addMethods(String[] methodNames, Class<?> type,
			RpcResultMode mode, boolean simple) {
		addMethods(methodNames, null, type, mode, simple);
	}

	public void addInstanceMethods(Object obj, Class<?> type,
			String aliasPrefix, RpcResultMode mode, boolean simple) {
		if (obj != null) {
			Method[] methods = type.getDeclaredMethods();
			for (Method method : methods) {
				int mod = method.getModifiers();
				if (Modifier.isPublic(mod) && !Modifier.isStatic(mod)) {
					addMethod(method, obj,
							aliasPrefix + "_" + method.getName(), mode, simple);
				}
			}
		}
	}

	public void addInstanceMethods(Object obj, Class<?> type,
			String aliasPrefix, boolean simple) {
		addInstanceMethods(obj, type, aliasPrefix, RpcResultMode.Normal, simple);
	}

	public void addInstanceMethods(Object obj, Class<?> type,
			String aliasPrefix, RpcResultMode mode) {
		addInstanceMethods(obj, type, aliasPrefix, mode, false);
	}

	public void addInstanceMethods(Object obj, Class<?> type, String aliasPrefix) {
		addInstanceMethods(obj, type, aliasPrefix, RpcResultMode.Normal, false);
	}

	public void addInstanceMethods(Object obj, Class<?> type,
			RpcResultMode mode, boolean simple) {
		if (obj != null) {
			Method[] methods = type.getDeclaredMethods();
			for (Method method : methods) {
				int mod = method.getModifiers();
				if (Modifier.isPublic(mod) && !Modifier.isStatic(mod)) {
					addMethod(method, obj, method.getName(), mode, simple);
				}
			}
		}
	}

	public void addInstanceMethods(Object obj, Class<?> type, boolean simple) {
		addInstanceMethods(obj, type, RpcResultMode.Normal, simple);
	}

	public void addInstanceMethods(Object obj, Class<?> type, RpcResultMode mode) {
		addInstanceMethods(obj, type, mode, false);
	}

	public void addInstanceMethods(Object obj, Class<?> type) {
		addInstanceMethods(obj, type, RpcResultMode.Normal, false);
	}

	public void addInstanceMethods(Object obj, String aliasPrefix) {
		addInstanceMethods(obj, obj.getClass(), aliasPrefix);
	}

	public void addInstanceMethods(Object obj, String aliasPrefix,
			RpcResultMode mode) {
		addInstanceMethods(obj, obj.getClass(), aliasPrefix, mode);
	}

	public void addInstanceMethods(Object obj, String aliasPrefix,
			boolean simple) {
		addInstanceMethods(obj, obj.getClass(), aliasPrefix, simple);
	}

	public void addInstanceMethods(Object obj, String aliasPrefix,
			RpcResultMode mode, boolean simple) {
		addInstanceMethods(obj, obj.getClass(), aliasPrefix, mode, simple);
	}

	public void addInstanceMethods(Object obj) {
		addInstanceMethods(obj, obj.getClass());
	}

	public void addInstanceMethods(Object obj, RpcResultMode mode) {
		addInstanceMethods(obj, obj.getClass(), mode);
	}

	public void addInstanceMethods(Object obj, boolean simple) {
		addInstanceMethods(obj, obj.getClass(), simple);
	}

	public void addInstanceMethods(Object obj, RpcResultMode mode,
			boolean simple) {
		addInstanceMethods(obj, obj.getClass(), mode, simple);
	}

	public void addStaticMethods(Class<?> type, String aliasPrefix,
			RpcResultMode mode, boolean simple) {
		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
			int mod = method.getModifiers();
			if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
				addMethod(method, null, aliasPrefix + "_" + method.getName(),
						mode, simple);
			}
		}
	}

	public void addStaticMethods(Class<?> type, String aliasPrefix,
			boolean simple) {
		addStaticMethods(type, aliasPrefix, RpcResultMode.Normal, simple);
	}

	public void addStaticMethods(Class<?> type, String aliasPrefix,
			RpcResultMode mode) {
		addStaticMethods(type, aliasPrefix, mode, false);
	}

	public void addStaticMethods(Class<?> type, String aliasPrefix) {
		addStaticMethods(type, aliasPrefix, RpcResultMode.Normal, false);
	}

	public void addStaticMethods(Class<?> type, RpcResultMode mode,
			boolean simple) {
		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
			int mod = method.getModifiers();
			if (Modifier.isPublic(mod) && Modifier.isStatic(mod)) {
				addMethod(method, null, method.getName(), mode, simple);
			}
		}
	}

	public void addStaticMethods(Class<?> type, boolean simple) {
		addStaticMethods(type, RpcResultMode.Normal, simple);
	}

	public void addStaticMethods(Class<?> type, RpcResultMode mode) {
		addStaticMethods(type, mode, false);
	}

	public void addStaticMethods(Class<?> type) {
		addStaticMethods(type, RpcResultMode.Normal, false);
	}

	public void addMissingMethod(String methodName, Object obj)
			throws NoSuchMethodException {
		addMethod(methodName, obj, new Class<?>[] { String.class,
				Object[].class }, "*");
	}

	public void addMissingMethod(String methodName, Object obj,
			RpcResultMode mode) throws NoSuchMethodException {
		addMethod(methodName, obj, new Class<?>[] { String.class,
				Object[].class }, "*", mode);
	}

	public void addMissingMethod(String methodName, Object obj, boolean simple)
			throws NoSuchMethodException {
		addMethod(methodName, obj, new Class<?>[] { String.class,
				Object[].class }, "*", simple);
	}

	public void addMissingMethod(String methodName, Object obj,
			RpcResultMode mode, boolean simple) throws NoSuchMethodException {
		addMethod(methodName, obj, new Class<?>[] { String.class,
				Object[].class }, "*", mode, simple);
	}

	public void addMissingMethod(String methodName, Class<?> type)
			throws NoSuchMethodException {
		addMethod(methodName, type, new Class<?>[] { String.class,
				Object[].class }, "*");
	}

	public void addMissingMethod(String methodName, Class<?> type,
			RpcResultMode mode) throws NoSuchMethodException {
		addMethod(methodName, type, new Class<?>[] { String.class,
				Object[].class }, "*", mode);
	}

	public void addMissingMethod(String methodName, Class<?> type,
			boolean simple) throws NoSuchMethodException {
		addMethod(methodName, type, new Class<?>[] { String.class,
				Object[].class }, "*", simple);
	}

	public void addMissingMethod(String methodName, Class<?> type,
			RpcResultMode mode, boolean simple) throws NoSuchMethodException {
		addMethod(methodName, type, new Class<?>[] { String.class,
				Object[].class }, "*", mode, simple);
	}

}
