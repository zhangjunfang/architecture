package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicIntegerArray;

final class AtomicIntegerArrayUnserializer implements RpcUnserializer {

	public final static AtomicIntegerArrayUnserializer instance = new AtomicIntegerArrayUnserializer();

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return new AtomicIntegerArray(IntArrayUnserializer.read(reader, buffer));
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return new AtomicIntegerArray(IntArrayUnserializer.read(reader, stream));
	}

}
