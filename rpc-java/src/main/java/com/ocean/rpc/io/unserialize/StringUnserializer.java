package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

final class StringUnserializer implements RpcUnserializer, RpcTags {

	public final static StringUnserializer instance = new StringUnserializer();

	final static String readString(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		String str = ValueReader.readString(buffer);
		reader.refer.set(str);
		return str;
	}

	final static String readString(RpcReader reader, InputStream stream)
			throws IOException {
		String str = ValueReader.readString(stream);
		reader.refer.set(str);
		return str;
	}

	private static String toString(Object obj) {
		if (obj instanceof char[]) {
			return new String((char[]) obj);
		}
		return obj.toString();
	}

	final static String read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagEmpty:
			return "";
		case TagNull:
			return null;
		case TagString:
			return readString(reader, buffer);
		case TagUTF8Char:
			return ValueReader.readUTF8Char(buffer);
		case TagInteger:
			return ValueReader.readUntil(buffer, TagSemicolon).toString();
		case TagLong:
			return ValueReader.readUntil(buffer, TagSemicolon).toString();
		case TagDouble:
			return ValueReader.readUntil(buffer, TagSemicolon).toString();
		case TagRef:
			return toString(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return String.valueOf((char) tag);
		switch (tag) {
		case TagTrue:
			return "true";
		case TagFalse:
			return "false";
		case TagNaN:
			return "NaN";
		case TagInfinity:
			return (buffer.get() == TagPos) ? "Infinity" : "-Infinity";
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, buffer).toString();
		case TagTime:
			return DefaultUnserializer.readTime(reader, buffer).toString();
		case TagGuid:
			return UUIDUnserializer.readUUID(reader, buffer).toString();
		default:
			throw ValueReader.castError(reader.tagToString(tag), String.class);
		}
	}

	final static String read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagEmpty:
			return "";
		case TagNull:
			return null;
		case TagString:
			return readString(reader, stream);
		case TagUTF8Char:
			return ValueReader.readUTF8Char(stream);
		case TagInteger:
			return ValueReader.readUntil(stream, TagSemicolon).toString();
		case TagLong:
			return ValueReader.readUntil(stream, TagSemicolon).toString();
		case TagDouble:
			return ValueReader.readUntil(stream, TagSemicolon).toString();
		case TagRef:
			return toString(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return String.valueOf((char) tag);
		switch (tag) {
		case TagTrue:
			return "true";
		case TagFalse:
			return "false";
		case TagNaN:
			return "NaN";
		case TagInfinity:
			return (stream.read() == TagPos) ? "Infinity" : "-Infinity";
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, stream).toString();
		case TagTime:
			return DefaultUnserializer.readTime(reader, stream).toString();
		case TagGuid:
			return UUIDUnserializer.readUUID(reader, stream).toString();
		default:
			throw ValueReader.castError(reader.tagToString(tag), String.class);
		}
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
