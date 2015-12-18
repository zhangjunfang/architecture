package com.ocean.rpc.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.serialize.ValueWriter;
import com.ocean.rpc.io.unserialize.RpcReader;

public final class RpcFormatter {

	final static class Cache {
		final Object obj;
		final RpcMode mode;
		final boolean simple;
		final byte[] buffer;

		public Cache(Object o, RpcMode m, boolean s, byte[] b) {
			obj = o;
			mode = m;
			simple = s;
			buffer = b;
		}
	}

	private static final ThreadLocal<Cache> cache = new ThreadLocal<Cache>();

	private RpcFormatter() {
	}

	public final static OutputStream serialize(byte b, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, b);
		return stream;
	}

	public final static OutputStream serialize(short s, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, s);
		return stream;
	}

	public final static OutputStream serialize(int i, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, i);
		return stream;
	}

	public final static OutputStream serialize(long l, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, l);
		return stream;

	}

	public final static OutputStream serialize(float f, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, f);
		return stream;

	}

	public final static OutputStream serialize(double d, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, d);
		return stream;

	}

	public final static OutputStream serialize(boolean b, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, b);
		return stream;

	}

	public final static OutputStream serialize(char c, OutputStream stream)
			throws IOException {
		ValueWriter.write(stream, c);
		return stream;
	}

	public final static OutputStream serialize(BigInteger bi,
			OutputStream stream) throws IOException {
		ValueWriter.write(stream, bi);
		return stream;
	}

	public final static OutputStream serialize(BigDecimal bd,
			OutputStream stream) throws IOException {
		ValueWriter.write(stream, bd);
		return stream;
	}

	public final static OutputStream serialize(Object obj, OutputStream stream)
			throws IOException {
		return serialize(obj, stream, RpcMode.MemberMode, false);
	}

	public final static OutputStream serialize(Object obj, OutputStream stream,
			boolean simple) throws IOException {
		return serialize(obj, stream, RpcMode.MemberMode, simple);
	}

	public final static OutputStream serialize(Object obj, OutputStream stream,
			RpcMode mode) throws IOException {
		return serialize(obj, stream, mode, false);
	}

	public final static OutputStream serialize(Object obj, OutputStream stream,
			RpcMode mode, boolean simple) throws IOException {
		RpcWriter writer = new RpcWriter(stream, mode, simple);
		writer.serialize(obj);
		return stream;
	}

	public final static ByteBufferStream serialize(byte b) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(8);
		serialize(b, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(short s) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(8);
		serialize(s, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(int i) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(16);
		serialize(i, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(long l) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(32);
		serialize(l, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(float f) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(32);
		serialize(f, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(double d) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(32);
		serialize(d, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(boolean b)
			throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(1);
		serialize(b, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(char c) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream(4);
		serialize(c, bufstream.getOutputStream());
		bufstream.flip();
		return bufstream;
	}

	public final static ByteBufferStream serialize(Object obj)
			throws IOException {
		return serialize(obj, RpcMode.MemberMode, false);
	}

	public final static ByteBufferStream serialize(Object obj, RpcMode mode)
			throws IOException {
		return serialize(obj, mode, false);
	}

	public final static ByteBufferStream serialize(Object obj, boolean simple)
			throws IOException {
		return serialize(obj, RpcMode.MemberMode, simple);
	}

	public final static ByteBufferStream serialize(Object obj, RpcMode mode,
			boolean simple) throws IOException {
		ByteBufferStream bufstream = new ByteBufferStream();
		Cache c = cache.get();
		if ((c != null) && (obj == c.obj) && (mode == c.mode)
				&& (simple == c.simple)) {
			bufstream.write(c.buffer);
			return bufstream;
		} else {
			serialize(obj, bufstream.getOutputStream(), mode, simple);
			cache.set(new Cache(obj, mode, simple, bufstream.toArray()));
			bufstream.flip();
			return bufstream;
		}
	}

	public final static Object unserialize(ByteBufferStream stream)
			throws IOException {
		RpcReader reader = new RpcReader(stream.buffer);
		return reader.unserialize();
	}

	public final static Object unserialize(ByteBufferStream stream, RpcMode mode)
			throws IOException {
		RpcReader reader = new RpcReader(stream.buffer, mode);
		return reader.unserialize();
	}

	public final static Object unserialize(ByteBufferStream stream,
			boolean simple) throws IOException {
		RpcReader reader = new RpcReader(stream.buffer, simple);
		return reader.unserialize();
	}

	public final static Object unserialize(ByteBufferStream stream,
			RpcMode mode, boolean simple) throws IOException {
		RpcReader reader = new RpcReader(stream.buffer, mode, simple);
		return reader.unserialize();
	}

	public final static <T> T unserialize(ByteBufferStream stream, Class<T> type)
			throws IOException {
		RpcReader reader = new RpcReader(stream.buffer);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(ByteBufferStream stream,
			RpcMode mode, Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(stream.buffer, mode);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(ByteBufferStream stream,
			boolean simple, Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(stream.buffer, simple);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(ByteBufferStream stream,
			RpcMode mode, boolean simple, Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(stream.buffer, mode, simple);
		return reader.unserialize(type);
	}

	public final static Object unserialize(ByteBuffer data) throws IOException {
		RpcReader reader = new RpcReader(data);
		return reader.unserialize();
	}

	public final static Object unserialize(ByteBuffer data, RpcMode mode)
			throws IOException {
		RpcReader reader = new RpcReader(data, mode);
		return reader.unserialize();
	}

	public final static Object unserialize(ByteBuffer data, boolean simple)
			throws IOException {
		RpcReader reader = new RpcReader(data, simple);
		return reader.unserialize();
	}

	public final static Object unserialize(ByteBuffer data, RpcMode mode,
			boolean simple) throws IOException {
		RpcReader reader = new RpcReader(data, mode, simple);
		return reader.unserialize();
	}

	public final static <T> T unserialize(ByteBuffer data, Class<T> type)
			throws IOException {
		RpcReader reader = new RpcReader(data);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(ByteBuffer data, RpcMode mode,
			Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(data, mode);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(ByteBuffer data, boolean simple,
			Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(data, simple);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(ByteBuffer data, RpcMode mode,
			boolean simple, Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(data, mode, simple);
		return reader.unserialize(type);
	}

	public final static Object unserialize(byte[] data) throws IOException {
		RpcReader reader = new RpcReader(data);
		return reader.unserialize();
	}

	public final static Object unserialize(byte[] data, RpcMode mode)
			throws IOException {
		RpcReader reader = new RpcReader(data, mode);
		return reader.unserialize();
	}

	public final static Object unserialize(byte[] data, boolean simple)
			throws IOException {
		RpcReader reader = new RpcReader(data, simple);
		return reader.unserialize();
	}

	public final static Object unserialize(byte[] data, RpcMode mode,
			boolean simple) throws IOException {
		RpcReader reader = new RpcReader(data, mode, simple);
		return reader.unserialize();
	}

	public final static <T> T unserialize(byte[] data, Class<T> type)
			throws IOException {
		RpcReader reader = new RpcReader(data);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(byte[] data, RpcMode mode,
			Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(data, mode);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(byte[] data, boolean simple,
			Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(data, simple);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(byte[] data, RpcMode mode,
			boolean simple, Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(data, mode, simple);
		return reader.unserialize(type);
	}

	public final static Object unserialize(InputStream stream)
			throws IOException {
		RpcReader reader = new RpcReader(stream);
		return reader.unserialize();
	}

	public final static Object unserialize(InputStream stream, RpcMode mode)
			throws IOException {
		RpcReader reader = new RpcReader(stream, mode);
		return reader.unserialize();
	}

	public final static Object unserialize(InputStream stream, boolean simple)
			throws IOException {
		RpcReader reader = new RpcReader(stream, simple);
		return reader.unserialize();
	}

	public final static Object unserialize(InputStream stream, RpcMode mode,
			boolean simple) throws IOException {
		RpcReader reader = new RpcReader(stream, mode, simple);
		return reader.unserialize();
	}

	public final static <T> T unserialize(InputStream stream, Class<T> type)
			throws IOException {
		RpcReader reader = new RpcReader(stream);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(InputStream stream, RpcMode mode,
			Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(stream, mode);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(InputStream stream, boolean simple,
			Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(stream, simple);
		return reader.unserialize(type);
	}

	public final static <T> T unserialize(InputStream stream, RpcMode mode,
			boolean simple, Class<T> type) throws IOException {
		RpcReader reader = new RpcReader(stream, mode, simple);
		return reader.unserialize(type);
	}
}
