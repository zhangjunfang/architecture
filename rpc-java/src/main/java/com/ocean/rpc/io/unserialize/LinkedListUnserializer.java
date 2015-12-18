package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.LinkedList;

final class LinkedListUnserializer implements RpcUnserializer {

	public final static LinkedListUnserializer instance = new LinkedListUnserializer();

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return CollectionUnserializer.readCollection(reader, buffer,
				LinkedList.class, type);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return CollectionUnserializer.readCollection(reader, stream,
				LinkedList.class, type);
	}

}
