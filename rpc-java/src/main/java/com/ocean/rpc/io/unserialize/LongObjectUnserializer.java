package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagLong;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class LongObjectUnserializer implements RpcUnserializer {

	public final static LongObjectUnserializer instance = new LongObjectUnserializer();

	final static Long read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (long) (tag - '0');
		if (tag == TagInteger || tag == TagLong)
			return ValueReader.readLong(buffer, TagSemicolon);
		if (tag == TagNull)
			return null;
		return LongUnserializer.read(reader, buffer, tag);
	}

	final static Long read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (long) (tag - '0');
		if (tag == TagInteger || tag == TagLong)
			return ValueReader.readLong(stream, TagSemicolon);
		if (tag == TagNull)
			return null;
		return LongUnserializer.read(reader, stream, tag);
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
