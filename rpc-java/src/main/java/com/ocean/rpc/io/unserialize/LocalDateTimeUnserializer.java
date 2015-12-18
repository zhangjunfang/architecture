package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class LocalDateTimeUnserializer implements RpcUnserializer, RpcTags {

	public final static LocalDateTimeUnserializer instance = new LocalDateTimeUnserializer();

	private static LocalDateTime toLocalDateTime(DateTime dt) {
		return LocalDateTime.of(dt.year, dt.month, dt.day, dt.hour, dt.minute,
				dt.second, dt.nanosecond);
	}

	private static LocalDateTime toLocalDateTime(Object obj) {
		if (obj instanceof DateTime) {
			return toLocalDateTime((DateTime) obj);
		}
		if (obj instanceof char[]) {
			return LocalDateTime.parse(new String((char[]) obj));
		}
		return LocalDateTime.parse(obj.toString());
	}

	final static LocalDateTime read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return toLocalDateTime(DefaultUnserializer.readDateTime(reader,
					buffer));
		case TagTime:
			return toLocalDateTime(DefaultUnserializer.readTime(reader, buffer));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return LocalDateTime.parse(StringUnserializer.readString(reader,
					buffer));
		case TagRef:
			return toLocalDateTime(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					LocalDateTime.class);
		}
	}

	final static LocalDateTime read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return toLocalDateTime(DefaultUnserializer.readDateTime(reader,
					stream));
		case TagTime:
			return toLocalDateTime(DefaultUnserializer.readTime(reader, stream));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return LocalDateTime.parse(StringUnserializer.readString(reader,
					stream));
		case TagRef:
			return toLocalDateTime(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					LocalDateTime.class);
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
