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
import java.util.Calendar;

final class CalendarArrayUnserializer implements RpcUnserializer {

	public final static CalendarArrayUnserializer instance = new CalendarArrayUnserializer();

	final static Calendar[] read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(buffer, TagOpenbrace);
			Calendar[] a = new Calendar[count];
			reader.refer.set(a);
			for (int i = 0; i < count; ++i) {
				a[i] = CalendarUnserializer.read(reader, buffer);
			}
			buffer.get();
			return a;
		}
		case TagRef:
			return (Calendar[]) reader.readRef(buffer);
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array.class);
		}
	}

	final static Calendar[] read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(stream, TagOpenbrace);
			Calendar[] a = new Calendar[count];
			reader.refer.set(a);
			for (int i = 0; i < count; ++i) {
				a[i] = CalendarUnserializer.read(reader, stream);
			}
			stream.read();
			return a;
		}
		case TagRef:
			return (Calendar[]) reader.readRef(stream);
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
