package com.ocean.rpc.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.ocean.rpc.util.ClassUtil;

public class RpcInvocationHandler implements InvocationHandler {
	private final RpcInvoker client;
	private final String ns;

	public RpcInvocationHandler(RpcInvoker client, String ns) {
		this.client = client;
		this.ns = (ns == null) ? "" : ns + "_";
	}

	@Override
	@SuppressWarnings("rawtypes")
	public Object invoke(Object proxy, Method method, final Object[] arguments)
			throws Throwable {
		MethodName methodName = method.getAnnotation(MethodName.class);
		final String functionName = ns
				+ ((methodName == null) ? method.getName() : methodName.value());
		ResultMode rm = method.getAnnotation(ResultMode.class);
		final RpcResultMode resultMode = (rm == null) ? RpcResultMode.Normal
				: rm.value();
		SimpleMode sm = method.getAnnotation(SimpleMode.class);
		final boolean simple = (sm == null) ? false : sm.value();
		ByRef byref = method.getAnnotation(ByRef.class);
		final boolean byRef = (byref == null) ? false : byref.value();
		Type[] paramTypes = method.getGenericParameterTypes();
		Type returnType = method.getGenericReturnType();
		if (void.class.equals(returnType) || Void.class.equals(returnType)) {
			returnType = null;
		}
		int n = paramTypes.length;
		Object result = null;
		if ((n > 0)
				&& ClassUtil.toClass(paramTypes[n - 1]).equals(
						RpcCallback1.class)) {
			if (paramTypes[n - 1] instanceof ParameterizedType) {
				returnType = ((ParameterizedType) paramTypes[n - 1])
						.getActualTypeArguments()[0];
			}
			RpcCallback1 callback = (RpcCallback1) arguments[n - 1];
			Object[] tmpargs = new Object[n - 1];
			System.arraycopy(arguments, 0, tmpargs, 0, n - 1);
			client.invoke(functionName, tmpargs, callback, null, returnType,
					resultMode, simple);
		} else if ((n > 0)
				&& ClassUtil.toClass(paramTypes[n - 1]).equals(
						RpcCallback.class)) {
			if (paramTypes[n - 1] instanceof ParameterizedType) {
				returnType = ((ParameterizedType) paramTypes[n - 1])
						.getActualTypeArguments()[0];
			}
			RpcCallback callback = (RpcCallback) arguments[n - 1];
			Object[] tmpargs = new Object[n - 1];
			System.arraycopy(arguments, 0, tmpargs, 0, n - 1);
			client.invoke(functionName, tmpargs, callback, null, returnType,
					byRef, resultMode, simple);
		} else if ((n > 1)
				&& ClassUtil.toClass(paramTypes[n - 2]).equals(
						RpcCallback1.class)
				&& ClassUtil.toClass(paramTypes[n - 1]).equals(
						RpcErrorEvent.class)) {
			if (paramTypes[n - 2] instanceof ParameterizedType) {
				returnType = ((ParameterizedType) paramTypes[n - 2])
						.getActualTypeArguments()[0];
			}
			RpcCallback1 callback = (RpcCallback1) arguments[n - 2];
			RpcErrorEvent errorEvent = (RpcErrorEvent) arguments[n - 1];
			Object[] tmpargs = new Object[n - 2];
			System.arraycopy(arguments, 0, tmpargs, 0, n - 2);
			client.invoke(functionName, tmpargs, callback, errorEvent,
					returnType, resultMode, simple);
		} else if ((n > 1)
				&& ClassUtil.toClass(paramTypes[n - 2]).equals(
						RpcCallback.class)
				&& ClassUtil.toClass(paramTypes[n - 1]).equals(
						RpcErrorEvent.class)) {
			if (paramTypes[n - 2] instanceof ParameterizedType) {
				returnType = ((ParameterizedType) paramTypes[n - 2])
						.getActualTypeArguments()[0];
			}
			RpcCallback callback = (RpcCallback) arguments[n - 2];
			RpcErrorEvent errorEvent = (RpcErrorEvent) arguments[n - 1];
			Object[] tmpargs = new Object[n - 2];
			System.arraycopy(arguments, 0, tmpargs, 0, n - 2);
			client.invoke(functionName, tmpargs, callback, errorEvent,
					returnType, byRef, resultMode, simple);
		} else {
			result = client.invoke(functionName, arguments, returnType, byRef,
					resultMode, simple);
		}
		return result;
	}
}
