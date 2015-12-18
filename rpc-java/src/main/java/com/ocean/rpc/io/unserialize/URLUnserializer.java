package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;

final class URLUnserializer implements RpcUnserializer {

	public final static URLUnserializer instance = new URLUnserializer();

	private static URL toURL(String s) throws IOException {
		try {
			return new URL(s);
		} catch (MalformedURLException e) {
			throw ValueReader.castError("String: '" + s + "'", URL.class);
		}
	}

	final static URL readURL(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		URL u = toURL(ValueReader.readString(buffer));
		reader.refer.set(u);
		return u;
	}

	final static URL readURL(RpcReader reader, InputStream stream)
			throws IOException {
		URL u = toURL(ValueReader.readString(stream));
		reader.refer.set(u);
		return u;
	}

	private static URL toURL(Object obj) throws IOException {
		if (obj instanceof URL) {
			return (URL) obj;
		}
		if (obj instanceof char[]) {
			return toURL(new String((char[]) obj));
		}
		return toURL(obj.toString());
	}

	final static URL read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readURL(reader, buffer);
		case TagRef:
			return toURL(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag), URL.class);
		}
	}

	final static URL read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readURL(reader, stream);
		case TagRef:
			return toURL(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag), URL.class);
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
