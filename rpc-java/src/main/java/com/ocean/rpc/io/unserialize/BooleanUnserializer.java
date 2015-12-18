package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

public final class BooleanUnserializer implements RpcUnserializer, RpcTags {

	public final static BooleanUnserializer instance = new BooleanUnserializer();

	final static boolean read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case '0':
			return false;
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return true;
		case TagInteger:
			return ValueReader.readInt(buffer) != 0;
		case TagLong:
			return !(BigInteger.ZERO.equals(ValueReader.readBigInteger(buffer)));
		case TagDouble:
			return ValueReader.readDouble(buffer) != 0.0;
		case TagEmpty:
			return false;
		case TagNaN:
			return true;
		case TagInfinity:
			buffer.get();
			return true;
		case TagUTF8Char:
			return "\00".indexOf(ValueReader.readChar(buffer)) == -1;
		case TagString:
			return Boolean.parseBoolean(StringUnserializer.readString(reader,
					buffer));
		case TagRef:
			return Boolean.parseBoolean(reader.readRef(buffer, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), boolean.class);
		}
	}

	final static boolean read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case '0':
			return false;
		case '1':
		case '2':
		case '3':
		case '4':
		case '5':
		case '6':
		case '7':
		case '8':
		case '9':
			return true;
		case TagInteger:
			return ValueReader.readInt(stream) != 0;
		case TagLong:
			return !(BigInteger.ZERO.equals(ValueReader.readBigInteger(stream)));
		case TagDouble:
			return ValueReader.readDouble(stream) != 0.0;
		case TagEmpty:
			return false;
		case TagNaN:
			return true;
		case TagInfinity:
			stream.read();
			return true;
		case TagUTF8Char:
			return "\00".indexOf(ValueReader.readChar(stream)) == -1;
		case TagString:
			return Boolean.parseBoolean(StringUnserializer.readString(reader,
					stream));
		case TagRef:
			return Boolean.parseBoolean(reader.readRef(stream, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag), boolean.class);
		}
	}

	public final static boolean read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagTrue:
			return true;
		case TagFalse:
		case TagNull:
			return false;
		default:
			return read(reader, buffer, tag);
		}
	}

	public final static boolean read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagTrue:
			return true;
		case TagFalse:
		case TagNull:
			return false;
		default:
			return read(reader, stream, tag);
		}
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return BooleanUnserializer.read(reader, buffer);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return BooleanUnserializer.read(reader, stream);
	}

}
