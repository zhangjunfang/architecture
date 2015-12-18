package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagBytes;
import static com.ocean.rpc.io.RpcTags.TagEmpty;
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

final class ByteArrayUnserializer implements RpcUnserializer {

	public final static ByteArrayUnserializer instance = new ByteArrayUnserializer();

	final static byte[] readBytes(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		byte[] b = ValueReader.readBytes(buffer);
		reader.refer.set(b);
		return b;
	}

	final static byte[] readBytes(RpcReader reader, InputStream stream)
			throws IOException {
		byte[] b = ValueReader.readBytes(stream);
		reader.refer.set(b);
		return b;
	}

	final static byte[] read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagBytes)
			return readBytes(reader, buffer);
		switch (tag) {
		case TagNull:
			return null;
		case TagEmpty:
			return new byte[0];
		case TagUTF8Char:
			return ValueReader.readUTF8Char(buffer).getBytes("UTF-8");
		case TagString:
			return StringUnserializer.readString(reader, buffer).getBytes(
					"UTF-8");
		case TagList: {
			int count = ValueReader.readInt(buffer, TagOpenbrace);
			byte[] a = new byte[count];
			reader.refer.set(a);
			for (int i = 0; i < count; ++i) {
				a[i] = ByteUnserializer.read(reader, buffer);
			}
			buffer.get();
			return a;
		}
		case TagRef: {
			Object obj = reader.readRef(buffer);
			if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			if (obj instanceof String) {
				return ((String) obj).getBytes("UTF-8");
			}
			throw ValueReader.castError(obj, Array.class);
		}
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array.class);
		}
	}

	final static byte[] read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagBytes)
			return readBytes(reader, stream);
		switch (tag) {
		case TagNull:
			return null;
		case TagEmpty:
			return new byte[0];
		case TagUTF8Char:
			return ValueReader.readUTF8Char(stream).getBytes("UTF-8");
		case TagString:
			return StringUnserializer.readString(reader, stream).getBytes(
					"UTF-8");
		case TagList: {
			int count = ValueReader.readInt(stream, TagOpenbrace);
			byte[] a = new byte[count];
			reader.refer.set(a);
			for (int i = 0; i < count; ++i) {
				a[i] = ByteUnserializer.read(reader, stream);
			}
			stream.read();
			return a;
		}
		case TagRef: {
			Object obj = reader.readRef(stream);
			if (obj instanceof byte[]) {
				return (byte[]) obj;
			}
			if (obj instanceof String) {
				return ((String) obj).getBytes("UTF-8");
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
