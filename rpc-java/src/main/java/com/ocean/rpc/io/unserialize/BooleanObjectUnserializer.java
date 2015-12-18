package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagFalse;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagTrue;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class BooleanObjectUnserializer implements RpcUnserializer {

	public final static BooleanObjectUnserializer instance = new BooleanObjectUnserializer();

	final static Boolean read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagTrue:
			return true;
		case TagFalse:
			return false;
		case TagNull:
			return null;
		default:
			return BooleanUnserializer.read(reader, buffer, tag);
		}
	}

	final static Boolean read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagTrue:
			return true;
		case TagFalse:
			return false;
		case TagNull:
			return null;
		default:
			return BooleanUnserializer.read(reader, stream, tag);
		}
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return read(reader, buffer);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return read(reader, stream);
	}

}
