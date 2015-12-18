package com.ocean.rpc.io.accessor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.serialize.ValueWriter;
import com.ocean.rpc.io.unserialize.RpcReader;
import com.ocean.rpc.io.unserialize.IntUnserializer;
@SuppressWarnings("restriction")
public final class IntFieldAccessor implements MemberAccessor {
	private final long offset;

	public IntFieldAccessor(Field accessor) {
		accessor.setAccessible(true);
		offset = Accessors.unsafe.objectFieldOffset(accessor);
	}

	@Override
	public void serialize(RpcWriter writer, Object obj) throws IOException {
		int value;
		try {
			value = Accessors.unsafe.getInt(obj, offset);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
		ValueWriter.write(writer.stream, value);
	}

	@Override
	public void unserialize(RpcReader reader, ByteBuffer buffer, Object obj)
			throws IOException {
		int value = IntUnserializer.read(reader, buffer);
		try {
			Accessors.unsafe.putInt(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public void unserialize(RpcReader reader, InputStream stream, Object obj)
			throws IOException {
		int value = IntUnserializer.read(reader, stream);
		try {
			Accessors.unsafe.putInt(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}
}