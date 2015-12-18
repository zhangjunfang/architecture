package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;
import com.ocean.rpc.util.TimeZoneUtil;

final class ZonedDateTimeUnserializer implements RpcUnserializer, RpcTags {

	public final static ZonedDateTimeUnserializer instance = new ZonedDateTimeUnserializer();

	private static ZonedDateTime toZonedDateTime(DateTime dt) {
		return ZonedDateTime.of(
				dt.year,
				dt.month,
				dt.day,
				dt.hour,
				dt.minute,
				dt.second,
				dt.nanosecond,
				dt.utc ? ZoneOffset.UTC : ZoneOffset.of(TimeZoneUtil.DefaultTZ
						.getID()));
	}

	private static ZonedDateTime toZonedDateTime(Object obj) {
		if (obj instanceof DateTime) {
			return toZonedDateTime((DateTime) obj);
		}
		if (obj instanceof char[]) {
			return ZonedDateTime.parse(new String((char[]) obj));
		}
		return ZonedDateTime.parse(obj.toString());
	}

	final static ZonedDateTime read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return toZonedDateTime(DefaultUnserializer.readDateTime(reader,
					buffer));
		case TagTime:
			return toZonedDateTime(DefaultUnserializer.readTime(reader, buffer));
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toZonedDateTime(reader.readRef(buffer));
		case TagString:
			return ZonedDateTime.parse(StringUnserializer.readString(reader,
					buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					ZonedDateTime.class);
		}
	}

	final static ZonedDateTime read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return toZonedDateTime(DefaultUnserializer.readDateTime(reader,
					stream));
		case TagTime:
			return toZonedDateTime(DefaultUnserializer.readTime(reader, stream));
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toZonedDateTime(reader.readRef(stream));
		case TagString:
			return ZonedDateTime.parse(StringUnserializer.readString(reader,
					stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					ZonedDateTime.class);
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
