package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;
import com.ocean.rpc.util.TimeZoneUtil;

final class InstantUnserializer implements RpcUnserializer, RpcTags {

	public final static InstantUnserializer instance = new InstantUnserializer();

	private static Instant toInstant(DateTime dt) {
		return OffsetDateTime.of(
				dt.year,
				dt.month,
				dt.day,
				dt.hour,
				dt.minute,
				dt.second,
				dt.nanosecond,
				dt.utc ? ZoneOffset.UTC : ZoneOffset.of(TimeZoneUtil.DefaultTZ
						.getID())).toInstant();
	}

	private static Instant toInstant(Object obj) {
		if (obj instanceof DateTime) {
			return toInstant((DateTime) obj);
		}
		if (obj instanceof char[]) {
			return Instant.parse(new String((char[]) obj));
		}
		return Instant.parse(obj.toString());
	}

	final static Instant read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return toInstant(DefaultUnserializer.readDateTime(reader, buffer));
		case TagTime:
			return toInstant(DefaultUnserializer.readTime(reader, buffer));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return Instant.parse(StringUnserializer.readString(reader, buffer));
		case TagRef:
			return toInstant(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return Instant.ofEpochMilli(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return Instant.ofEpochMilli(ValueReader.readLong(buffer));
		case TagDouble:
			return Instant.ofEpochMilli(Double.valueOf(
					ValueReader.readDouble(buffer)).longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag), Instant.class);
		}
	}

	final static Instant read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return toInstant(DefaultUnserializer.readDateTime(reader, stream));
		case TagTime:
			return toInstant(DefaultUnserializer.readTime(reader, stream));
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return Instant.parse(StringUnserializer.readString(reader, stream));
		case TagRef:
			return toInstant(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return Instant.ofEpochMilli(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return Instant.ofEpochMilli(ValueReader.readLong(stream));
		case TagDouble:
			return Instant.ofEpochMilli(Double.valueOf(
					ValueReader.readDouble(stream)).longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag), Instant.class);
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
