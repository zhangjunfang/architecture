package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.Duration;

import com.ocean.rpc.io.RpcTags;

final class DurationUnserializer implements RpcUnserializer, RpcTags {

	public final static DurationUnserializer instance = new DurationUnserializer();

	private static Duration toDuration(Object obj) {
		if (obj instanceof char[]) {
			return Duration.parse(new String((char[]) obj));
		}
		return Duration.parse(obj.toString());
	}

	final static Duration read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag >= '0' && tag <= '9')
			return Duration.ofNanos(tag - '0');
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toDuration(reader.readRef(buffer));
		case TagInteger:
		case TagLong:
			return Duration.ofNanos(ValueReader.readLong(buffer));
		case TagDouble:
			return Duration.ofNanos(Double.valueOf(
					ValueReader.readDouble(buffer)).longValue());
		case TagString:
			return Duration
					.parse(StringUnserializer.readString(reader, buffer));
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), Duration.class);
		}
	}

	final static Duration read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag >= '0' && tag <= '9')
			return Duration.ofNanos(tag - '0');
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toDuration(reader.readRef(stream));
		case TagInteger:
		case TagLong:
			return Duration.ofNanos(ValueReader.readLong(stream));
		case TagDouble:
			return Duration.ofNanos(Double.valueOf(
					ValueReader.readDouble(stream)).longValue());
		case TagString:
			return Duration
					.parse(StringUnserializer.readString(reader, stream));
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), Duration.class);
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
