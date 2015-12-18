package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagRef;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.regex.Pattern;

final class PatternUnserializer implements RpcUnserializer {

	public final static PatternUnserializer instance = new PatternUnserializer();

	final static Pattern readPattern(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		Pattern pattern = Pattern.compile(ValueReader.readString(buffer));
		reader.refer.set(pattern);
		return pattern;
	}

	final static Pattern readPattern(RpcReader reader, InputStream stream)
			throws IOException {
		Pattern pattern = Pattern.compile(ValueReader.readString(stream));
		reader.refer.set(pattern);
		return pattern;
	}

	private static Pattern toPattern(Object obj) {
		if (obj instanceof Pattern) {
			return (Pattern) obj;
		}
		if (obj instanceof char[]) {
			return Pattern.compile(new String((char[]) obj));
		}
		return Pattern.compile(obj.toString());
	}

	final static Pattern read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readPattern(reader, buffer);
		case TagRef:
			return toPattern(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag), Pattern.class);
		}
	}

	final static Pattern read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
		case TagEmpty:
			return null;
		case TagString:
			return readPattern(reader, stream);
		case TagRef:
			return toPattern(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag), Pattern.class);
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
