package com.ocean.rpc.common;

public interface RpcCallback<T> {
	void handler(T result, Object[] arguments);
}
