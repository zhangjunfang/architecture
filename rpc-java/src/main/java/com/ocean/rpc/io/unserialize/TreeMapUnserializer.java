package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.TreeMap;

final class TreeMapUnserializer implements RpcUnserializer {

	public final static TreeMapUnserializer instance = new TreeMapUnserializer();

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return MapUnserializer.readMap(reader, buffer, TreeMap.class, type);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return MapUnserializer.readMap(reader, stream, TreeMap.class, type);
	}

}
