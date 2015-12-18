package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

final class URIUnserializer implements RpcUnserializer {

	public final static URIUnserializer instance = new URIUnserializer();

	private static URI toURI(String s) throws IOException {
		try {
			return new URI(s);
		} catch (URISyntaxException e) {
			throw ValueReader.castError("String: '" + s + "'", URI.class);
		}
	}

	final static URI readURI(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		URI u = toURI(ValueReader.readString(buffer));
		reader.refer.set(u);
		return u;
	}

	final static URI readURI(RpcReader reader, InputStream stream)
			throws IOException {
		URI u = toURI(ValueReader.readString(stream));
		reader.refer.set(u);
		return u;
	}

	private static URI toURI(Object obj) throws IOException {
		if (obj instanceof URI) {
			return (URI) obj;
		}
		if (obj instanceof char[]) {
			return toURI(new String((char[]) obj));
		}
		return toURI(obj.toString());
	}

	final static URI read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readURI(reader, buffer);
		case TagRef:
			return toURI(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag), URI.class);
		}
	}

	final static URI read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readURI(reader, stream);
		case TagRef:
			return toURI(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag), URI.class);
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
