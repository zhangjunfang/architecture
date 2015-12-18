package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.LocalTime;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class LocalTimeUnserializer implements RpcUnserializer, RpcTags {

	public final static LocalTimeUnserializer instance = new LocalTimeUnserializer();

	private static LocalTime toLocalTime(DateTime dt) {
		return LocalTime.of(dt.hour, dt.minute, dt.second, dt.nanosecond);
	}

	private static LocalTime toLocalTime(Object obj) {
		if (obj instanceof DateTime) {
			return toLocalTime((DateTime) obj);
		}
		if (obj instanceof char[]) {
			return LocalTime.parse(new String((char[]) obj));
		}
		return LocalTime.parse(obj.toString());
	}

	final static LocalTime read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return toLocalTime(DefaultUnserializer.readDateTime(reader, buffer));
		case TagTime:
			return toLocalTime(DefaultUnserializer.readTime(reader, buffer));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return LocalTime.parse(StringUnserializer
					.readString(reader, buffer));
		case TagRef:
			return toLocalTime(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return LocalTime.ofNanoOfDay(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return LocalTime.ofNanoOfDay(ValueReader.readLong(buffer));
		case TagDouble:
			return LocalTime.ofNanoOfDay(Double.valueOf(
					ValueReader.readDouble(buffer)).longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					LocalTime.class);
		}
	}

	final static LocalTime read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return toLocalTime(DefaultUnserializer.readDateTime(reader, stream));
		case TagTime:
			return toLocalTime(DefaultUnserializer.readTime(reader, stream));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return LocalTime.parse(StringUnserializer
					.readString(reader, stream));
		case TagRef:
			return toLocalTime(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return LocalTime.ofNanoOfDay(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return LocalTime.ofNanoOfDay(ValueReader.readLong(stream));
		case TagDouble:
			return LocalTime.ofNanoOfDay(Double.valueOf(
					ValueReader.readDouble(stream)).longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					LocalTime.class);
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
