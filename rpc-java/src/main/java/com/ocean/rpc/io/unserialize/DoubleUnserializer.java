package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

public final class DoubleUnserializer implements RpcUnserializer, RpcTags {

	public final static DoubleUnserializer instance = new DoubleUnserializer();

	final static double read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return ValueReader.readLongAsDouble(buffer);
		case TagEmpty:
			return 0.0;
		case TagTrue:
			return 1.0;
		case TagFalse:
			return 0.0;
		case TagNaN:
			return Double.NaN;
		case TagInfinity:
			return ValueReader.readInfinity(buffer);
		case TagUTF8Char:
			return ValueReader.parseDouble(ValueReader.readUTF8Char(buffer));
		case TagString:
			return ValueReader.parseDouble(StringUnserializer.readString(
					reader, buffer));
		case TagRef:
			return ValueReader
					.parseDouble(reader.readRef(buffer, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), double.class);
		}
	}

	final static double read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case TagLong:
			return ValueReader.readLongAsDouble(stream);
		case TagEmpty:
			return 0.0;
		case TagTrue:
			return 1.0;
		case TagFalse:
			return 0.0;
		case TagNaN:
			return Double.NaN;
		case TagInfinity:
			return ValueReader.readInfinity(stream);
		case TagUTF8Char:
			return ValueReader.parseDouble(ValueReader.readUTF8Char(stream));
		case TagString:
			return ValueReader.parseDouble(StringUnserializer.readString(
					reader, stream));
		case TagRef:
			return ValueReader
					.parseDouble(reader.readRef(stream, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), double.class);
		}
	}

	public final static double read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagDouble)
			return ValueReader.readDouble(buffer);
		if (tag >= '0' && tag <= '9')
			return tag - '0';
		if (tag == TagInteger)
			return ValueReader.readInt(buffer, TagSemicolon);
		if (tag == TagNull)
			return 0.0;
		return read(reader, buffer, tag);
	}

	public final static double read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagDouble)
			return ValueReader.readDouble(stream);
		if (tag >= '0' && tag <= '9')
			return tag - '0';
		if (tag == TagInteger)
			return ValueReader.readInt(stream, TagSemicolon);
		if (tag == TagNull)
			return 0.0;
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
