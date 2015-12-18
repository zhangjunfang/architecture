package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

public final class IntUnserializer implements RpcUnserializer, RpcTags {

	public final static IntUnserializer instance = new IntUnserializer();

	final static int read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return ValueReader.readInt(buffer, TagSemicolon);
		case TagDouble:
			return Double.valueOf(ValueReader.readDouble(buffer)).intValue();
		case TagEmpty:
			return 0;
		case TagTrue:
			return 1;
		case TagFalse:
			return 0;
		case TagUTF8Char:
			return Integer.parseInt(ValueReader.readUTF8Char(buffer));
		case TagString:
			return Integer.parseInt(StringUnserializer.readString(reader,
					buffer));
		case TagRef:
			return Integer.parseInt(reader.readRef(buffer, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), int.class);
		}
	}

	final static int read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return ValueReader.readInt(stream, TagSemicolon);
		case TagDouble:
			return Double.valueOf(ValueReader.readDouble(stream)).intValue();
		case TagEmpty:
			return 0;
		case TagTrue:
			return 1;
		case TagFalse:
			return 0;
		case TagUTF8Char:
			return Integer.parseInt(ValueReader.readUTF8Char(stream));
		case TagString:
			return Integer.parseInt(StringUnserializer.readString(reader,
					stream));
		case TagRef:
			return Integer.parseInt(reader.readRef(stream, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), int.class);
		}
	}

	public final static int read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return (tag - '0');
		if (tag == TagInteger)
			return ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return 0;
		return read(reader, buffer, tag);
	}

	public final static int read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return (tag - '0');
		if (tag == TagInteger)
			return ValueReader.readInt(stream, TagSemicolon);
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
