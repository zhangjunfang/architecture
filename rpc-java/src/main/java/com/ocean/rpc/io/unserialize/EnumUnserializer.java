package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.common.RpcException;

final class EnumUnserializer implements RpcUnserializer {

	public final static EnumUnserializer instance = new EnumUnserializer();

	final static <T> T read(RpcReader reader, ByteBuffer buffer, Class<T> type)
			throws RpcException {
		try {
			return type.getEnumConstants()[IntUnserializer.read(reader, buffer)];
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	final static <T> T read(RpcReader reader, InputStream stream, Class<T> type)
			throws RpcException {
		try {
			return type.getEnumConstants()[IntUnserializer.read(reader, stream)];
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return read(reader, buffer, cls);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return read(reader, stream, cls);
	}

}
