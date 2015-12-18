package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.Calendar;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class CalendarUnserializer implements RpcUnserializer, RpcTags {

	public final static CalendarUnserializer instance = new CalendarUnserializer();

	private static Calendar toCalendar(Object obj) throws RpcException {
		if (obj instanceof DateTime) {
			return ((DateTime) obj).toCalendar();
		}
		throw ValueReader.castError(obj, Calendar.class);
	}

	private static Calendar toCalendar(int tag) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(tag - '0');
		return calendar;
	}

	final static Calendar read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, buffer)
					.toCalendar();
		case TagTime:
			return DefaultUnserializer.readTime(reader, buffer).toCalendar();
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toCalendar(reader.readRef(buffer));
		}
		if (tag >= '0' && tag <= '9')
			return toCalendar(tag);
		switch (tag) {
		case TagInteger:
		case TagLong: {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(ValueReader.readLong(buffer));
			return calendar;
		}
		case TagDouble: {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Double.valueOf(
					ValueReader.readDouble(buffer)).longValue());
			return calendar;
		}
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), Calendar.class);
		}
	}

	final static Calendar read(RpcReader reader, InputStream stream)
			throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagDate:
			return DefaultUnserializer.readDateTime(reader, stream)
					.toCalendar();
		case TagTime:
			return DefaultUnserializer.readTime(reader, stream).toCalendar();
		case TagNull:
		case TagEmpty:
			return null;
		case TagRef:
			return toCalendar(reader.readRef(stream));
		}
		if (tag >= '0' && tag <= '9')
			return toCalendar(tag);
		switch (tag) {
		case TagInteger:
		case TagLong: {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(ValueReader.readLong(stream));
			return calendar;
		}
		case TagDouble: {
			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(Double.valueOf(
					ValueReader.readDouble(stream)).longValue());
			return calendar;
		}
		default:
			throw ValueReader
					.castError(reader.tagToString(tag), Calendar.class);
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
