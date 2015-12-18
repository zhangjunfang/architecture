package com.ocean.rpc.io.serialize;

import java.io.IOException;

public interface RpcSerializer<T> {
	void write(RpcWriter writer, T obj) throws IOException;
}
