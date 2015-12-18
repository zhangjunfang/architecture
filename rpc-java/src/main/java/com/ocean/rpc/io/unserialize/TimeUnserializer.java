package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.sql.Time;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class TimeUnserializer implements RpcUnserializer, RpcTags {

	public final static TimeUnserializer instance = new TimeUnserializer();

	private static Time toTime(Object obj) throws RpcException {
		if (obj instanceof DateTime) {
			return ((DateTime) obj).toTime();
		}
		if (obj instanceof char[]) {
			return Time.valueOf(new String((char[]) obj));
		}
		return Time.valueOf(obj.toString());
	}

	final static Time read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, buffer).toTime();
		case TagTime:
			return DefaultUnserializer.readTime(reader, buffer).toTime();
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return Time.valueOf(StringUnserializer.readString(reader, buffer));
		case TagRef:
			return toTime(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return new Time(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return new Time(ValueReader.readLong(buffer));
		case TagDouble:
			return new Time(Double.valueOf(ValueReader.readDouble(buffer))
					.longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag), Time.class);
		}
	}

	final static Time read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, stream).toTime();
		case TagTime:
			return DefaultUnserializer.readTime(reader, stream).toTime();
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return Time.valueOf(StringUnserializer.readString(reader, stream));
		case TagRef:
			return toTime(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return new Time(tag - '0');
		switch (tag) {
		case TagInteger:
		case TagLong:
			return new Time(ValueReader.readLong(stream));
		case TagDouble:
			return new Time(Double.valueOf(ValueReader.readDouble(stream))
					.longValue());
		default:
			throw ValueReader.castError(reader.tagToString(tag), Time.class);
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
