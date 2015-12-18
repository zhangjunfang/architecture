package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;
import static com.ocean.rpc.io.RpcTags.TagRef;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class ObjectArrayUnserializer implements RpcUnserializer {

	public final static ObjectArrayUnserializer instance = new ObjectArrayUnserializer();

	final static Object[] readArray(RpcReader reader, ByteBuffer buffer,
			int count) throws IOException {
		Object[] a = new Object[count];
		reader.refer.set(a);
		for (int i = 0; i < count; ++i) {
			a[i] = DefaultUnserializer.read(reader, buffer);
		}
		buffer.get();
		return a;
	}

	final static Object[] readArray(RpcReader reader, InputStream stream,
			int count) throws IOException {
		Object[] a = new Object[count];
		reader.refer.set(a);
		for (int i = 0; i < count; ++i) {
			a[i] = DefaultUnserializer.read(reader, stream);
		}
		stream.read();
		return a;
	}

	final static Object[] read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
			return null;
		case TagList:
			return readArray(reader, buffer,
					ValueReader.readInt(buffer, TagOpenbrace));
		case TagRef:
			return (Object[]) reader.readRef(buffer);
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array.class);
		}
	}

	final static Object[] read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
			return null;
		case TagList:
			return ObjectArrayUnserializer.readArray(reader, stream,
					ValueReader.readInt(stream, TagOpenbrace));
		case TagRef:
			return (Object[]) reader.readRef(stream);
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
