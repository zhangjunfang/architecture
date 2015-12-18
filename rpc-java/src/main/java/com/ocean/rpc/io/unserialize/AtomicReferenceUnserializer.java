package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReference;

final class AtomicReferenceUnserializer implements RpcUnserializer {

	public final static AtomicReferenceUnserializer instance = new AtomicReferenceUnserializer();

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		if (type instanceof ParameterizedType) {
			return new AtomicReference(reader.unserialize(buffer,
					((ParameterizedType) type).getActualTypeArguments()[0]));
		} else {
			return new AtomicReference(reader.unserialize(buffer, Object.class));
		}
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		if (type instanceof ParameterizedType) {
			return new AtomicReference(reader.unserialize(stream,
					((ParameterizedType) type).getActualTypeArguments()[0]));
		} else {
			return new AtomicReference(reader.unserialize(stream, Object.class));
		}
	}

}
