package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagDouble;
import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class DoubleObjectUnserializer implements RpcUnserializer {

	public final static DoubleObjectUnserializer instance = new DoubleObjectUnserializer();

	final static Double read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagDouble)
			return ValueReader.readDouble(buffer);
		if (tag >= '0' && tag <= '9')
			return (double) (tag - '0');
		if (tag == TagInteger)
			return (double) ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return null;
		return DoubleUnserializer.read(reader, buffer, tag);
	}

	final static Double read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagDouble)
			return ValueReader.readDouble(stream);
		if (tag >= '0' && tag <= '9')
			return (double) (tag - '0');
		if (tag == TagInteger)
			return (double) ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return null;
		return DoubleUnserializer.read(reader, stream, tag);
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
