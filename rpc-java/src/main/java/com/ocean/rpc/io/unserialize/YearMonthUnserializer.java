package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.time.YearMonth;

import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class YearMonthUnserializer implements RpcUnserializer, RpcTags {

	public final static YearMonthUnserializer instance = new YearMonthUnserializer();

	private static YearMonth toYearMonth(DateTime dt) {
		return YearMonth.of(dt.year, dt.month);
	}

	private static YearMonth toYearMonth(Object obj) {
		if (obj instanceof DateTime) {
			return toYearMonth((DateTime) obj);
		}
		if (obj instanceof char[]) {
			return YearMonth.parse(new String((char[]) obj));
		}
		return YearMonth.parse(obj.toString());
	}

	final static YearMonth read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagString:
			return YearMonth.parse(StringUnserializer
					.readString(reader, buffer));
		case TagDate:
			return toYearMonth(DefaultUnserializer.readDateTime(reader, buffer));
		case TagTime:
			return toYearMonth(DefaultUnserializer.readTime(reader, buffer));
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toYearMonth(reader.readRef(buffer));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					YearMonth.class);
		}
	}

	final static YearMonth read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagString:
			return YearMonth.parse(StringUnserializer
					.readString(reader, stream));
		case TagDate:
			return toYearMonth(DefaultUnserializer.readDateTime(reader, stream));
		case TagTime:
			return toYearMonth(DefaultUnserializer.readTime(reader, stream));
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toYearMonth(reader.readRef(stream));
		default:
			throw ValueReader.castError(reader.tagToString(tag),
					YearMonth.class);
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
