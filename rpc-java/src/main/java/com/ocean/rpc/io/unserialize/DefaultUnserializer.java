package com.ocean.rpc.io.unserialize;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.RpcTags;
import com.ocean.rpc.util.DateTime;

final class DefaultUnserializer implements RpcUnserializer, RpcTags {

	public final static DefaultUnserializer instance = new DefaultUnserializer();

	final static DateTime readDateTime(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		DateTime datetime = ValueReader.readDateTime(buffer);
		reader.refer.set(datetime);
		return datetime;
	}

	final static DateTime readDateTime(RpcReader reader, InputStream stream)
			throws IOException {
		DateTime datetime = ValueReader.readDateTime(stream);
		reader.refer.set(datetime);
		return datetime;
	}

	final static DateTime readTime(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		DateTime datetime = ValueReader.readTime(buffer);
		reader.refer.set(datetime);
		return datetime;
	}

	final static DateTime readTime(RpcReader reader, InputStream stream)
			throws IOException {
		DateTime datetime = ValueReader.readTime(stream);
		reader.refer.set(datetime);
		return datetime;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final static ArrayList readList(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int count = ValueReader.readInt(buffer, TagOpenbrace);
		ArrayList a = new ArrayList(count);
		reader.refer.set(a);
		for (int i = 0; i < count; ++i) {
			a.add(read(reader, buffer));
		}
		buffer.get();
		return a;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final static ArrayList readList(RpcReader reader, InputStream stream)
			throws IOException {
		int count = ValueReader.readInt(stream, TagOpenbrace);
		ArrayList a = new ArrayList(count);
		reader.refer.set(a);
		for (int i = 0; i < count; ++i) {
			a.add(read(reader, stream));
		}
		stream.read();
		return a;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final static HashMap readMap(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		int count = ValueReader.readInt(buffer, TagOpenbrace);
		HashMap map = new HashMap(count);
		reader.refer.set(map);
		for (int i = 0; i < count; ++i) {
			Object key = read(reader, buffer);
			Object value = read(reader, buffer);
			map.put(key, value);
		}
		buffer.get();
		return map;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	final static HashMap readMap(RpcReader reader, InputStream stream)
			throws IOException {
		int count = ValueReader.readInt(stream, TagOpenbrace);
		HashMap map = new HashMap(count);
		reader.refer.set(map);
		for (int i = 0; i < count; ++i) {
			Object key = read(reader, stream);
			Object value = read(reader, stream);
			map.put(key, value);
		}
		stream.read();
		return map;
	}

	final static Object read(RpcReader reader, ByteBuffer buffer, int tag)
			throws IOException {
		switch (tag) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case TagInteger:
			return ValueReader.readInt(buffer);
		case TagLong:
			return ValueReader.readBigInteger(buffer);
		case TagDouble:
			return ValueReader.readDouble(buffer);
		case TagNull:
			return null;
		case TagEmpty:
			return "";
		case TagTrue:
			return true;
		case TagFalse:
			return false;
		case TagNaN:
			return Double.NaN;
		case TagInfinity:
			return ValueReader.readInfinity(buffer);
		case TagDate:
			return readDateTime(reader, buffer).toCalendar();
		case TagTime:
			return readTime(reader, buffer).toCalendar();
		case TagBytes:
			return ByteArrayUnserializer.readBytes(reader, buffer);
		case TagUTF8Char:
			return ValueReader.readUTF8Char(buffer);
		case TagString:
			return StringUnserializer.readString(reader, buffer);
		case TagGuid:
			return UUIDUnserializer.readUUID(reader, buffer);
		case TagList:
			return readList(reader, buffer);
		case TagMap:
			return readMap(reader, buffer);
		case TagClass:
			ObjectUnserializer.readClass(reader, buffer);
			return ObjectUnserializer.read(reader, buffer, null);
		case TagObject:
			return ObjectUnserializer.readObject(reader, buffer, null);
		case TagRef:
			return reader.readRef(buffer);
		case TagError:
			throw new RpcException(StringUnserializer.read(reader, buffer));
		default:
			throw reader.unexpectedTag(tag);
		}
	}

	final static Object read(RpcReader reader, InputStream stream, int tag)
			throws IOException {
		switch (tag) {
		case '0':
			return 0;
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case TagInteger:
			return ValueReader.readInt(stream);
		case TagLong:
			return ValueReader.readBigInteger(stream);
		case TagDouble:
			return ValueReader.readDouble(stream);
		case TagNull:
			return null;
		case TagEmpty:
			return "";
		case TagTrue:
			return true;
		case TagFalse:
			return false;
		case TagNaN:
			return Double.NaN;
		case TagInfinity:
			return ValueReader.readInfinity(stream);
		case TagDate:
			return readDateTime(reader, stream).toCalendar();
		case TagTime:
			return readTime(reader, stream).toCalendar();
		case TagBytes:
			return ByteArrayUnserializer.readBytes(reader, stream);
		case TagUTF8Char:
			return ValueReader.readUTF8Char(stream);
		case TagString:
			return StringUnserializer.readString(reader, stream);
		case TagGuid:
			return UUIDUnserializer.readUUID(reader, stream);
		case TagList:
			return readList(reader, stream);
		case TagMap:
			return readMap(reader, stream);
		case TagClass:
			ObjectUnserializer.readClass(reader, stream);
			return ObjectUnserializer.read(reader, stream, null);
		case TagObject:
			return ObjectUnserializer.readObject(reader, stream, null);
		case TagRef:
			return reader.readRef(stream);
		case TagError:
			throw new RpcException(StringUnserializer.read(reader, stream));
		default:
			throw reader.unexpectedTag(tag);
		}
	}

	final static Object read(RpcReader reader, ByteBuffer buffer)
			throws IOException {
		return read(reader, buffer, buffer.get());
	}

	final static Object read(RpcReader reader, InputStream stream)
			throws IOException {
		return read(reader, stream, stream.read());
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
