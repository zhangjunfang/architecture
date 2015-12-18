package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagDouble;
import static com.ocean.rpc.io.RpcTags.TagInteger;
import static com.ocean.rpc.io.RpcTags.TagLong;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;
import static com.ocean.rpc.io.RpcTags.TagUTF8Char;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

public final class CharUnserializer implements RpcUnserializer {

	public final static CharUnserializer instance = new CharUnserializer();

	final static char read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return (char) tag;
		case TagInteger:
			return (char) ValueReader.readInt(buffer);
		case TagLong:
			return (char) ValueReader.readLong(buffer);
		case TagDouble:
			return (char) Double.valueOf(ValueReader.readDouble(buffer))
					.intValue();
		case TagString:
			return StringUnserializer.readString(reader, buffer).charAt(0);
		case TagRef:
			return reader.readRef(buffer, String.class).charAt(0);
		default:
			throw ValueReader.castError(reader.tagToString(tag), char.class);
		}
	}

	final static char read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case '0':
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return (char) tag;
		case TagInteger:
			return (char) ValueReader.readInt(stream);
		case TagLong:
			return (char) ValueReader.readLong(stream);
		case TagDouble:
			return (char) Double.valueOf(ValueReader.readDouble(stream))
					.intValue();
		case TagUTF8Char:
			return ValueReader.readChar(stream);
		case TagString:
			return StringUnserializer.readString(reader, stream).charAt(0);
		case TagRef:
			return reader.readRef(stream, String.class).charAt(0);
		default:
			throw ValueReader.castError(reader.tagToString(tag), char.class);
		}
	}

	public final static char read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagUTF8Char)
			return ValueReader.readChar(buffer);
		if (tag == TagNull)
			return (char) 0;
		return read(reader, buffer, tag);
	}

	public final static char read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagUTF8Char)
			return ValueReader.readChar(stream);
		if (tag == TagNull)
			return (char) 0;
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
