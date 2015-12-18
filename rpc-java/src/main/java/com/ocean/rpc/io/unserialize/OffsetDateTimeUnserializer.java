package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;
import com.ocean.rpc.util.TimeZoneUtil;

final class OffsetDateTimeUnserializer implements RpcUnserializer, RpcTags {

	public final static OffsetDateTimeUnserializer instance = new OffsetDateTimeUnserializer();

	private static OffsetDateTime toOffsetDateTime(DateTime dt) {
		return OffsetDateTime.of(
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

	private static OffsetDateTime toOffsetDateTime(Object obj) {
		if (obj instanceof DateTime) {
			return toOffsetDateTime((DateTime) obj);
		}
		if (obj instanceof char[]) {
			return OffsetDateTime.parse(new String((char[]) obj));
		}
		return OffsetDateTime.parse(obj.toString());
	}

	final static OffsetDateTime read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return toOffsetDateTime(DefaultUnserializer.readDateTime(reader,
					buffer));
		case TagTime:
			return toOffsetDateTime(DefaultUnserializer
					.readTime(reader, buffer));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return OffsetDateTime.parse(StringUnserializer.readString(reader,
					buffer));
		case TagRef:
			return toOffsetDateTime(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					OffsetDateTime.class);
		}
	}

	final static OffsetDateTime read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return toOffsetDateTime(DefaultUnserializer.readDateTime(reader,
					stream));
		case TagTime:
			return toOffsetDateTime(DefaultUnserializer
					.readTime(reader, stream));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return OffsetDateTime.parse(StringUnserializer.readString(reader,
					stream));
		case TagRef:
			return toOffsetDateTime(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					OffsetDateTime.class);
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
