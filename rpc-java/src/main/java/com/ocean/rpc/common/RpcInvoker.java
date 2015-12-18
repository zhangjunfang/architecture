package com.ocean.rpc.common;

import java.io.IOException;
import java.lang.reflect.Type;

public interface RpcInvoker {
	void invoke(String functionName, RpcCallback1<?> callback);

	void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent);

	void invoke(String functionName, RpcCallback1<?> callback,
			RpcResultMode resultMode);

	void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent, RpcResultMode resultMode);

	void invoke(String functionName, RpcCallback1<?> callback, boolean simple);

	void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent, boolean simple);

	void invoke(String functionName, RpcCallback1<?> callback,
			RpcResultMode resultMode, boolean simple);

	void invoke(String functionName, RpcCallback1<?> callback,
			RpcErrorEvent errorEvent, RpcResultMode resultMode, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcResultMode resultMode);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcResultMode resultMode, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback1<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType, RpcResultMode resultMode);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType,
			RpcResultMode resultMode);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType, boolean simple);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType, boolean simple);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			Class<T> returnType, RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, RpcCallback1<T> callback,
			RpcErrorEvent errorEvent, Class<T> returnType,
			RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType,
			RpcResultMode resultMode);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, Class<T> returnType,
			RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback1<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode, boolean simple);

	@SuppressWarnings("rawtypes")
	void invoke(String functionName, Object[] arguments, RpcCallback1 callback,
			RpcErrorEvent errorEvent, Type returnType,
			RpcResultMode resultMode, boolean simple);

	void invoke(String functionName, Object[] arguments, RpcCallback<?> callback);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef,
			boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcResultMode resultMode);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef, RpcResultMode resultMode);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef,
			RpcResultMode resultMode);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcResultMode resultMode, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent,
			RpcResultMode resultMode, boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, boolean byRef, RpcResultMode resultMode,
			boolean simple);

	void invoke(String functionName, Object[] arguments,
			RpcCallback<?> callback, RpcErrorEvent errorEvent, boolean byRef,
			RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef,
			boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType,
			RpcResultMode resultMode);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef,
			RpcResultMode resultMode);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef, RpcResultMode resultMode);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType,
			RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, Class<T> returnType, boolean byRef,
			RpcResultMode resultMode, boolean simple);

	<T> void invoke(String functionName, Object[] arguments,
			RpcCallback<T> callback, RpcErrorEvent errorEvent,
			Class<T> returnType, boolean byRef, RpcResultMode resultMode,
			boolean simple);

	@SuppressWarnings("rawtypes")
	void invoke(String functionName, Object[] arguments, RpcCallback callback,
			RpcErrorEvent errorEvent, Type returnType, boolean byRef,
			RpcResultMode resultMode, boolean simple);

	Object invoke(String functionName) throws IOException;

	Object invoke(String functionName, Object[] arguments) throws IOException;

	Object invoke(String functionName, Object[] arguments, boolean byRef)
			throws IOException;

	Object invoke(String functionName, boolean simple) throws IOException;

	Object invoke(String functionName, Object[] arguments, boolean byRef,
			boolean simple) throws IOException;

	Object invoke(String functionName, RpcResultMode resultMode)
			throws IOException;

	Object invoke(String functionName, Object[] arguments,
			RpcResultMode resultMode) throws IOException;

	Object invoke(String functionName, Object[] arguments, boolean byRef,
			RpcResultMode resultMode) throws IOException;

	Object invoke(String functionName, RpcResultMode resultMode, boolean simple)
			throws IOException;

	Object invoke(String functionName, Object[] arguments,
			RpcResultMode resultMode, boolean simple) throws IOException;

	Object invoke(String functionName, Object[] arguments, boolean byRef,
			RpcResultMode resultMode, boolean simple) throws IOException;

	<T> T invoke(String functionName, Class<T> returnType) throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType)
			throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType,
			boolean byRef) throws IOException;

	<T> T invoke(String functionName, Class<T> returnType, boolean simple)
			throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType,
			boolean byRef, boolean simple) throws IOException;

	<T> T invoke(String functionName, Class<T> returnType,
			RpcResultMode resultMode) throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType,
			RpcResultMode resultMode) throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType,
			boolean byRef, RpcResultMode resultMode) throws IOException;

	<T> T invoke(String functionName, Class<T> returnType,
			RpcResultMode resultMode, boolean simple) throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType,
			RpcResultMode resultMode, boolean simple) throws IOException;

	<T> T invoke(String functionName, Object[] arguments, Class<T> returnType,
			boolean byRef, RpcResultMode resultMode, boolean simple)
			throws IOException;

	Object invoke(String functionName, Object[] arguments, Type returnType,
			boolean byRef, RpcResultMode resultMode, boolean simple)
			throws IOException;
}