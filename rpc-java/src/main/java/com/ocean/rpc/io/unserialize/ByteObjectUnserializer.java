package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class ByteObjectUnserializer implements RpcUnserializer {

	public final static ByteObjectUnserializer instance = new ByteObjectUnserializer();

	final static Byte read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (byte) (tag - '0');
		if (tag == TagInteger)
			return (byte) ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return null;
		return ByteUnserializer.read(reader, buffer, tag);
	}

	final static Byte read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (byte) (tag - '0');
		if (tag == TagInteger)
			return (byte) ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return null;
		return ByteUnserializer.read(reader, stream, tag);
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
