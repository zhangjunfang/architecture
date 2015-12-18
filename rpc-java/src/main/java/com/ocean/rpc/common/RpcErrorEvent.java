package com.ocean.rpc.common;

public interface RpcErrorEvent {
	void handler(String name, Throwable error);
}
