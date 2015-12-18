package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class IntObjectUnserializer implements RpcUnserializer {

	public final static IntObjectUnserializer instance = new IntObjectUnserializer();

	final static Integer read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (tag - '0');
		if (tag == TagInteger)
			return ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return null;
		return IntUnserializer.read(reader, buffer, tag);
	}

	final static Integer read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (tag - '0');
		if (tag == TagInteger)
			return ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return null;
		return IntUnserializer.read(reader, stream, tag);
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
