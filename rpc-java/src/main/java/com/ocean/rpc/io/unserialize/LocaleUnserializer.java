package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Locale;

final class LocaleUnserializer implements RpcUnserializer {

	public final static LocaleUnserializer instance = new LocaleUnserializer();

	private static Locale toLocale(String s) {
		String[] items = s.split("_");
		if (items.length == 1) {
			return new Locale(items[0]);
		}
		if (items.length == 2) {
			return new Locale(items[0], items[1]);
		}
		return new Locale(items[0], items[1], items[2]);
	}

	final static Locale readLocale(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		Locale locale = toLocale(ValueReader.readString(buffer));
		reader.refer.set(locale);
		return locale;
	}

	final static Locale readLocale(RpcReader reader, InputStream stream)
			throws IOException {
		Locale locale = toLocale(ValueReader.readString(stream));
		reader.refer.set(locale);
		return locale;
	}

	private static Locale toLocale(Object obj) {
		if (obj instanceof Locale) {
			return (Locale) obj;
		}
		if (obj instanceof char[]) {
			return toLocale(new String((char[]) obj));
		}
		return toLocale(obj.toString());
	}

	final static Locale read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readLocale(reader, buffer);
		case TagRef:
			return toLocale(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag), Locale.class);
		}
	}

	final static Locale read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readLocale(reader, stream);
		case TagRef:
			return toLocale(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag), Locale.class);
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
