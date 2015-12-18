package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;
import static com.ocean.rpc.io.RpcTags.TagUTF8Char;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class CharArrayUnserializer implements RpcUnserializer {

	public final static CharArrayUnserializer instance = new CharArrayUnserializer();

	final static char[] readChars(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		char[] chars = ValueReader.readChars(buffer);
		reader.refer.set(chars);
		return chars;
	}

	final static char[] readChars(RpcReader reader, InputStream stream)
			throws IOException {
		char[] chars = ValueReader.readChars(stream);
		reader.refer.set(chars);
		return chars;
	}

	final static char[] read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
			return null;
		case TagUTF8Char:
			return new char[] { ValueReader.readChar(buffer) };
		case TagString:
			return readChars(reader, buffer);
		case TagList: {
			int count = ValueReader.readInt(buffer, TagOpenbrace);
			char[] a = new char[count];
			reader.refer.set(a);
			for (int i = 0; i < count; ++i) {
				a[i] = CharUnserializer.read(reader, buffer);
			}
			buffer.get();
			return a;
		}
		case TagRef: {
			Object obj = reader.readRef(buffer);
			if (obj instanceof char[]) {
				return (char[]) obj;
			}
			if (obj instanceof String) {
				return ((String) obj).toCharArray();
			}
			throw ValueReader.castError(obj, Array.class);
		}
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array.class);
		}
	}

	final static char[] read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
			return null;
		case TagUTF8Char:
			return new char[] { ValueReader.readChar(stream) };
		case TagString:
			return readChars(reader, stream);
		case TagList: {
			int count = ValueReader.readInt(stream, TagOpenbrace);
			char[] a = new char[count];
			reader.refer.set(a);
			for (int i = 0; i < count; ++i) {
				a[i] = CharUnserializer.read(reader, stream);
			}
			stream.read();
			return a;
		}
		case TagRef: {
			Object obj = reader.readRef(stream);
			if (obj instanceof char[]) {
				return (char[]) obj;
			}
			if (obj instanceof String) {
				return ((String) obj).toCharArray();
			}
			throw ValueReader.castError(obj, Array.class);
		}
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array.class);
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
