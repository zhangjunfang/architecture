package com.ocean.rpc.io.serialize;

import java.io.IOException;

import com.ocean.rpc.common.RpcException;

@SuppressWarnings("rawtypes")
final class ObjectSerializer implements RpcSerializer {

	public final static ObjectSerializer instance = new ObjectSerializer();

	@Override
	public final void write(RpcWriter writer, Object obj) throws IOException {
		if (obj != null) {
			Class<?> cls = obj.getClass();
			if (Object.class.equals(cls)) {
				throw new RpcException(
						"Can't serialize an object of the Object class.");
			}
		}
		writer.serialize(obj);
	}
}
