package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.RpcTags;

final class BigDecimalUnserializer implements RpcUnserializer, RpcTags {

	public final static BigDecimalUnserializer instance = new BigDecimalUnserializer();

	final static BigDecimal read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		if (tag == TagDouble)
			return new BigDecimal(ValueReader.readUntil(buffer, TagSemicolon)
					.toString());
		if (tag == TagNull)
			return null;
		if (tag == TagLong)
			return new BigDecimal(ValueReader.readLong(buffer));
		if (tag == TagInteger)
			return new BigDecimal(ValueReader.readInt(buffer));
		if (tag >= '0' && tag <= '9')
			return BigDecimal.valueOf(tag - '0');
		switch (tag) {
		case TagEmpty:
			return BigDecimal.ZERO;
		case TagTrue:
			return BigDecimal.ONE;
		case TagFalse:
			return BigDecimal.ZERO;
		case TagUTF8Char:
			return new BigDecimal(ValueReader.readUTF8Char(buffer));
		case TagString:
			return new BigDecimal(StringUnserializer.readString(reader, buffer));
		case TagRef:
			return new BigDecimal(reader.readRef(buffer, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					BigDecimal.class);
		}
	}

	final static BigDecimal read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		if (tag == TagDouble)
			return new BigDecimal(ValueReader.readUntil(stream, TagSemicolon)
					.toString());
		if (tag == TagNull)
			return null;
		if (tag == TagLong)
			return new BigDecimal(ValueReader.readLong(stream));
		if (tag == TagInteger)
			return new BigDecimal(ValueReader.readInt(stream));
		if (tag >= '0' && tag <= '9')
			return BigDecimal.valueOf(tag - '0');
		switch (tag) {
		case TagEmpty:
			return BigDecimal.ZERO;
		case TagTrue:
			return BigDecimal.ONE;
		case TagFalse:
			return BigDecimal.ZERO;
		case TagUTF8Char:
			return new BigDecimal(ValueReader.readUTF8Char(stream));
		case TagString:
			return new BigDecimal(StringUnserializer.readString(reader, stream));
		case TagRef:
			return new BigDecimal(reader.readRef(stream, String.class));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					BigDecimal.class);
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
