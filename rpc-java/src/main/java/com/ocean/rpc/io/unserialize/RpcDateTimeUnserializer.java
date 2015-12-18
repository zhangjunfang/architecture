package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Date;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class RpcDateTimeUnserializer implements RpcUnserializer, RpcTags {

	public final static RpcDateTimeUnserializer instance = new RpcDateTimeUnserializer();

	@SuppressWarnings({ "deprecation" })
	private static DateTime toDate(Object obj) {
		if (obj instanceof DateTime) {
			return (DateTime) obj;
		}
		if (obj instanceof char[]) {
			return new DateTime(new Date(new String((char[]) obj)));
		}
		return new DateTime(new Date(obj.toString()));
	}

	@SuppressWarnings({ "deprecation" })
	final static DateTime read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, buffer);
		case TagTime:
			return DefaultUnserializer.readTime(reader, buffer);
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return new DateTime(new Date(StringUnserializer.readString(reader,
					buffer)));
		case TagRef:
			return toDate(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return new DateTime(new Date(tag - '0'));
		switch (tag) {
		case TagInteger:
		case TagLong:
			return new DateTime(new Date(ValueReader.readLong(buffer)));
		case TagDouble:
			return new DateTime(new Date(Double.valueOf(
					ValueReader.readDouble(buffer)).longValue()));
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), DateTime.class);
		}
	}

	@SuppressWarnings({ "deprecation" })
	final static DateTime read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, stream);
		case TagTime:
			return DefaultUnserializer.readTime(reader, stream);
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return new DateTime(new Date(StringUnserializer.readString(reader,
					stream)));
		case TagRef:
			return toDate(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return new DateTime(new Date(tag - '0'));
		switch (tag) {
		case TagInteger:
		case TagLong:
			return new DateTime(new Date(ValueReader.readLong(stream)));
		case TagDouble:
			return new DateTime(new Date(Double.valueOf(
					ValueReader.readDouble(stream)).longValue()));
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), DateTime.class);
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
