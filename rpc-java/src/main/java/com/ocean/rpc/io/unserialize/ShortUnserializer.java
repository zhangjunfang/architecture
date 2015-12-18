package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

public final class ShortUnserializer implements RpcUnserializer, RpcTags {

	public final static ShortUnserializer instance = new ShortUnserializer();

	final static short read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return (short) ValueReader.readLong(buffer, TagSemicolon);
		case TagDouble:
			return Double.valueOf(ValueReader.readDouble(buffer)).shortValue();
		case TagEmpty:
			return 0;
		case TagTrue:
			return 1;
		case TagFalse:
			return 0;
		case TagUTF8Char:
			return Short.parseShort(ValueReader.readUTF8Char(buffer));
		case TagString:
			return Short.parseShort(StringUnserializer.readString(reader,
					buffer));
		case TagRef:
			return Short.parseShort(reader.readRef(buffer, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), short.class);
		}
	}

	final static short read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return (short) ValueReader.readLong(stream, TagSemicolon);
		case TagDouble:
			return Double.valueOf(ValueReader.readDouble(stream)).shortValue();
		case TagEmpty:
			return 0;
		case TagTrue:
			return 1;
		case TagFalse:
			return 0;
		case TagUTF8Char:
			return Short.parseShort(ValueReader.readUTF8Char(stream));
		case TagString:
			return Short.parseShort(StringUnserializer.readString(reader,
					stream));
		case TagRef:
			return Short.parseShort(reader.readRef(stream, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), short.class);
		}
	}

	public final static short read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (short) (tag - '0');
		if (tag == TagInteger)
			return (short) ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return 0;
		return read(reader, buffer, tag);
	}

	public final static short read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (short) (tag - '0');
		if (tag == TagInteger)
			return (short) ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return 0;
		return read(reader, stream, tag);
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
