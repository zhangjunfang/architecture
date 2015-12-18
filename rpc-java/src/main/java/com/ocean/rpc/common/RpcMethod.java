package com.ocean.rpc.common;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public final class RpcMethod {
	public Object obj;
	public Method method;
	public Type[] paramTypes;
	public RpcResultMode mode;
	public boolean simple;

	public RpcMethod(Method method, Object obj, RpcResultMode mode,
			boolean simple) {
		this.obj = obj;
		this.method = method;
		this.paramTypes = method.getGenericParameterTypes();
		this.mode = mode;
		this.simple = simple;
	}

	public RpcMethod(Method method, Object obj, RpcResultMode mode) {
		this(method, obj, mode, false);
	}

	public RpcMethod(Method method, Object obj, boolean simple) {
		this(method, obj, RpcResultMode.Normal, simple);
	}

	public RpcMethod(Method method, Object obj) {
		this(method, obj, RpcResultMode.Normal, false);
	}

	public RpcMethod(Method method) {
		this(method, null, RpcResultMode.Normal, false);
	}

	public RpcMethod(String methodName, Class<?> type, Class<?>[] paramTypes,
			RpcResultMode mode, boolean simple) throws NoSuchMethodException {
		this.obj = null;
		this.method = type.getMethod(methodName, paramTypes);
		if (!Modifier.isStatic(this.method.getModifiers())) {
			throw new NoSuchMethodException();
		}
		this.paramTypes = method.getGenericParameterTypes();
		this.mode = mode;
		this.simple = simple;
	}

	public RpcMethod(String methodName, Class<?> type, Class<?>[] paramTypes,
			RpcResultMode mode) throws NoSuchMethodException {
		this(methodName, type, paramTypes, mode, false);
	}

	public RpcMethod(String methodName, Class<?> type, Class<?>[] paramTypes,
			boolean simple) throws NoSuchMethodException {
		this(methodName, type, paramTypes, RpcResultMode.Normal, simple);
	}

	public RpcMethod(String methodName, Class<?> type, Class<?>[] paramTypes)
			throws NoSuchMethodException {
		this(methodName, type, paramTypes, RpcResultMode.Normal, false);
	}

	public RpcMethod(String methodName, Object obj, Class<?>[] paramTypes,
			RpcResultMode mode, boolean simple) throws NoSuchMethodException {
		this.obj = obj;
		this.method = obj.getClass().getMethod(methodName, paramTypes);
		if (Modifier.isStatic(this.method.getModifiers())) {
			throw new NoSuchMethodException();
		}
		this.paramTypes = method.getGenericParameterTypes();
		this.mode = mode;
		this.simple = simple;
	}

	public RpcMethod(String methodName, Object obj, Class<?>[] paramTypes,
			RpcResultMode mode) throws NoSuchMethodException {
		this(methodName, obj, paramTypes, mode, false);
	}

	public RpcMethod(String methodName, Object obj, Class<?>[] paramTypes,
			boolean simple) throws NoSuchMethodException {
		this(methodName, obj, paramTypes, RpcResultMode.Normal, simple);
	}

	public RpcMethod(String methodName, Object obj, Class<?>[] paramTypes)
			throws NoSuchMethodException {
		this(methodName, obj, paramTypes, RpcResultMode.Normal, false);
	}
}