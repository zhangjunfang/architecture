package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class ShortObjectUnserializer implements RpcUnserializer {

	public final static ShortObjectUnserializer instance = new ShortObjectUnserializer();

	public final static Short read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (short) (tag - '0');
		if (tag == TagInteger)
			return (short) ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return null;
		return ShortUnserializer.read(reader, buffer, tag);
	}

	public final static Short read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (short) (tag - '0');
		if (tag == TagInteger)
			return (short) ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return null;
		return ShortUnserializer.read(reader, stream, tag);
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
