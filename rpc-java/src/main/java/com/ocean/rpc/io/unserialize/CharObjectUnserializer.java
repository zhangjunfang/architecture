package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagUTF8Char;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class CharObjectUnserializer implements RpcUnserializer {

	public final static CharObjectUnserializer instance = new CharObjectUnserializer();

	final static Character read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagUTF8Char)
			return ValueReader.readChar(buffer);
		if (tag == TagNull)
			return null;
		return CharUnserializer.read(reader, buffer, tag);
	}

	final static Character read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagUTF8Char)
			return ValueReader.readChar(stream);
		if (tag == TagNull)
			return null;
		return CharUnserializer.read(reader, stream, tag);
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
