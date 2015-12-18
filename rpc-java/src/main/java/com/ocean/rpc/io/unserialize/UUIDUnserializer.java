package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.UUID;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.RpcTags;

final class UUIDUnserializer implements RpcUnserializer, RpcTags {

	public final static UUIDUnserializer instance = new UUIDUnserializer();

	final static UUID readUUID(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		UUID uuid = ValueReader.readUUID(buffer);
		reader.refer.set(uuid);
		return uuid;
	}

	final static UUID readUUID(RpcReader reader, InputStream stream)
			throws IOException {
		UUID uuid = ValueReader.readUUID(stream);
		reader.refer.set(uuid);
		return uuid;
	}

	private static UUID toUUID(Object obj) throws RpcException {
		if (obj instanceof UUID) {
			return (UUID) obj;
		}
		if (obj instanceof byte[]) {
			return UUID.nameUUIDFromBytes((byte[]) obj);
		}
		if (obj instanceof String) {
			return UUID.fromString((String) obj);
		}
		if (obj instanceof char[]) {
			return UUID.fromString(new String((char[]) obj));
		}
		throw ValueReader.castError(obj, UUID.class);
	}

	final static UUID read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagGuid:
			return readUUID(reader, buffer);
		case TagBytes:
			return UUID.nameUUIDFromBytes(ByteArrayUnserializer.readBytes(
					reader, buffer));
		case TagString:
			return UUID.fromString(StringUnserializer
					.readString(reader, buffer));
		case TagRef:
			return toUUID(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag), UUID.class);
		}
	}

	final static UUID read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagGuid:
			return readUUID(reader, stream);
		case TagBytes:
			return UUID.nameUUIDFromBytes(ByteArrayUnserializer.readBytes(
					reader, stream));
		case TagString:
			return UUID.fromString(StringUnserializer
					.readString(reader, stream));
		case TagRef:
			return toUUID(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag), UUID.class);
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
