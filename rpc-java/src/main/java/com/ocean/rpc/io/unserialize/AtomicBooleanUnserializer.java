package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicBoolean;

final class AtomicBooleanUnserializer implements RpcUnserializer {

	public final static AtomicBooleanUnserializer instance = new AtomicBooleanUnserializer();

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return new AtomicBoolean(BooleanUnserializer.read(reader, buffer));
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return new AtomicBoolean(BooleanUnserializer.read(reader, stream));
	}

}
