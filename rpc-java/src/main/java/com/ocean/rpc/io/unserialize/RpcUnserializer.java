package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public interface RpcUnserializer {
	Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls, Type type)
			throws IOException;

	Object read(RpcReader reader, InputStream stream, Class<?> cls, Type type)
			throws IOException;
}
