package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagDouble;
import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class FloatObjectUnserializer implements RpcUnserializer {

	public final static FloatObjectUnserializer instance = new FloatObjectUnserializer();

	final static Float read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagDouble)
			return ValueReader.readFloat(buffer);
		if (tag >= '0' && tag <= '9')
			return (float) (tag - '0');
		if (tag == TagInteger)
			return (float) ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return null;
		return FloatUnserializer.read(reader, buffer, tag);
	}

	final static Float read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagDouble)
			return ValueReader.readFloat(stream);
		if (tag >= '0' && tag <= '9')
			return (float) (tag - '0');
		if (tag == TagInteger)
			return (float) ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return null;
		return FloatUnserializer.read(reader, stream, tag);
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
