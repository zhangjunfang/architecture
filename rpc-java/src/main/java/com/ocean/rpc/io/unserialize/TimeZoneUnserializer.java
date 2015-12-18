package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.TimeZone;

final class TimeZoneUnserializer implements RpcUnserializer {

	public final static TimeZoneUnserializer instance = new TimeZoneUnserializer();

	final static TimeZone readTimeZone(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		TimeZone tz = TimeZone.getTimeZone(ValueReader.readString(buffer));
		reader.refer.set(tz);
		return tz;
	}

	final static TimeZone readTimeZone(RpcReader reader, InputStream stream)
			throws IOException {
		TimeZone tz = TimeZone.getTimeZone(ValueReader.readString(stream));
		reader.refer.set(tz);
		return tz;
	}

	private static TimeZone toTimeZone(Object obj) throws IOException {
		if (obj instanceof TimeZone) {
			return (TimeZone) obj;
		}
		if (obj instanceof char[]) {
			return TimeZone.getTimeZone(new String((char[]) obj));
		}
		return TimeZone.getTimeZone(obj.toString());
	}

	final static TimeZone read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readTimeZone(reader, buffer);
		case TagRef:
			return toTimeZone(reader.readRef(buffer));
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), TimeZone.class);
		}
	}

	final static TimeZone read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readTimeZone(reader, stream);
		case TagRef:
			return toTimeZone(reader.readRef(stream));
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), TimeZone.class);
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
