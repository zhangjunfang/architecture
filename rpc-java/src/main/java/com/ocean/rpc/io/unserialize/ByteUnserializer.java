package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

public final class ByteUnserializer implements RpcUnserializer, RpcTags {

	public final static ByteUnserializer instance = new ByteUnserializer();

	final static byte read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return (byte) ValueReader.readLong(buffer, TagSemicolon);
		case TagDouble:
			return Double.valueOf(ValueReader.readDouble(buffer)).byteValue();
		case TagEmpty:
			return 0;
		case TagTrue:
			return 1;
		case TagFalse:
			return 0;
		case TagUTF8Char:
			return Byte.parseByte(ValueReader.readUTF8Char(buffer));
		case TagString:
			return Byte
					.parseByte(StringUnserializer.readString(reader, buffer));
		case TagRef:
			return Byte.parseByte(reader.readRef(buffer, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), byte.class);
		}
	}

	final static byte read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return (byte) ValueReader.readLong(stream, TagSemicolon);
		case TagDouble:
			return Double.valueOf(ValueReader.readDouble(stream)).byteValue();
		case TagEmpty:
			return 0;
		case TagTrue:
			return 1;
		case TagFalse:
			return 0;
		case TagUTF8Char:
			return Byte.parseByte(ValueReader.readUTF8Char(stream));
		case TagString:
			return Byte
					.parseByte(StringUnserializer.readString(reader, stream));
		case TagRef:
			return Byte.parseByte(reader.readRef(stream, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), byte.class);
		}
	}

	public final static byte read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (byte) (tag - '0');
		if (tag == TagInteger)
			return (byte) ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return 0;
		return read(reader, buffer, tag);
	}

	public final static byte read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (byte) (tag - '0');
		if (tag == TagInteger)
			return (byte) ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return 0;
		return ByteUnserializer.read(reader, stream, tag);
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return ByteUnserializer.read(reader, buffer);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return ByteUnserializer.read(reader, stream);
	}

}
