package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicReferenceArray;

import com.ocean.rpc.util.ClassUtil;

final class AtomicReferenceArrayUnserializer implements RpcUnserializer {

	public final static AtomicReferenceArrayUnserializer instance = new AtomicReferenceArrayUnserializer();

	private <T> AtomicReferenceArray<T> readAtomicReferenceArray(
			RpcReader reader, ByteBuffer buffer, Class<T> componentClass,
			Type componentType) throws IOException {
		return new AtomicReferenceArray<T>(ArrayUnserializer.readArray(reader,
				buffer, componentClass, componentType));
	}

	private <T> AtomicReferenceArray<T> readAtomicReferenceArray(
			RpcReader reader, InputStream stream, Class<T> componentClass,
			Type componentType) throws IOException {
		return new AtomicReferenceArray<T>(ArrayUnserializer.readArray(reader,
				stream, componentClass, componentType));
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getActualTypeArguments()[0];
			cls = ClassUtil.toClass(type);
			return readAtomicReferenceArray(reader, buffer, cls, type);
		} else {
			return readAtomicReferenceArray(reader, buffer, Object.class,
					Object.class);
		}
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		if (type instanceof ParameterizedType) {
			type = ((ParameterizedType) type).getActualTypeArguments()[0];
			cls = ClassUtil.toClass(type);
			return readAtomicReferenceArray(reader, stream, cls, type);
		} else {
			return readAtomicReferenceArray(reader, stream, Object.class,
					Object.class);
		}
	}

}
