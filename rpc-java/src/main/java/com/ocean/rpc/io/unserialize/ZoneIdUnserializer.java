package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.ZoneId;

final class ZoneIdUnserializer implements RpcUnserializer {

	public final static ZoneIdUnserializer instance = new ZoneIdUnserializer();

	final static ZoneId readZoneId(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		ZoneId tz = ZoneId.of(ValueReader.readString(buffer));
		reader.refer.set(tz);
		return tz;
	}

	final static ZoneId readZoneId(RpcReader reader, InputStream stream)
			throws IOException {
		ZoneId tz = ZoneId.of(ValueReader.readString(stream));
		reader.refer.set(tz);
		return tz;
	}

	private static ZoneId toZoneId(Object obj) throws IOException {
		if (obj instanceof ZoneId) {
			return (ZoneId) obj;
		}
		if (obj instanceof char[]) {
			return ZoneId.of(new String((char[]) obj));
		}
		return ZoneId.of(obj.toString());
	}

	final static ZoneId read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readZoneId(reader, buffer);
		case TagRef:
			return toZoneId(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag), ZoneId.class);
		}
	}

	final static ZoneId read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readZoneId(reader, stream);
		case TagRef:
			return toZoneId(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag), ZoneId.class);
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
