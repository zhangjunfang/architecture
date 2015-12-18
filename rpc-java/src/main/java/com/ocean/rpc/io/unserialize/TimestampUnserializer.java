package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.sql.Timestamp;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class TimestampUnserializer implements RpcUnserializer, RpcTags {

	public final static TimestampUnserializer instance = new TimestampUnserializer();

	private static Timestamp toTimestamp(Object obj) {
		if (obj instanceof DateTime) {
			return ((DateTime) obj).toTimestamp();
		}
		if (obj instanceof char[]) {
			return Timestamp.valueOf(new String((char[]) obj));
		}
		return Timestamp.valueOf(obj.toString());
	}

	final static Timestamp read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, buffer)
					.toTimestamp();
		case TagTime:
			return DefaultUnserializer.readTime(reader, buffer).toTimestamp();
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return Timestamp.valueOf(StringUnserializer.readString(reader,
					buffer));
		case TagRef:
			return toTimestamp(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return new Timestamp(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return new Timestamp(ValueReader.readLong(buffer));
		case TagDouble:
			return new Timestamp(Double.valueOf(ValueReader.readDouble(buffer))
					.longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					Timestamp.class);
		}
	}

	final static Timestamp read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, stream)
					.toTimestamp();
		case TagTime:
			return DefaultUnserializer.readTime(reader, stream).toTimestamp();
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return Timestamp.valueOf(StringUnserializer.readString(reader,
					stream));
		case TagRef:
			return toTimestamp(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return new Timestamp(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return new Timestamp(ValueReader.readLong(stream));
		case TagDouble:
			return new Timestamp(Double.valueOf(ValueReader.readDouble(stream))
					.longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					Timestamp.class);
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
