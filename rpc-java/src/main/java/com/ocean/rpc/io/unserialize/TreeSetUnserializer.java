package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.TreeSet;

final class TreeSetUnserializer implements RpcUnserializer {

	public final static TreeSetUnserializer instance = new TreeSetUnserializer();

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return CollectionUnserializer.readCollection(reader, buffer,
				TreeSet.class, type);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return CollectionUnserializer.readCollection(reader, stream,
				TreeSet.class, type);
	}

}
