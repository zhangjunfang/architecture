package com.ocean.rpc.io.accessor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.serialize.ValueWriter;
import com.ocean.rpc.io.unserialize.FloatUnserializer;
import com.ocean.rpc.io.unserialize.RpcReader;
@SuppressWarnings("restriction")
public final class FloatFieldAccessor implements MemberAccessor {
	private final long offset;

	public FloatFieldAccessor(Field accessor) {
		accessor.setAccessible(true);
		offset = Accessors.unsafe.objectFieldOffset(accessor);
	}

	@Override
	public void serialize(RpcWriter writer, Object obj) throws IOException {
		float value;
		try {
			value = Accessors.unsafe.getFloat(obj, offset);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
		ValueWriter.write(writer.stream, value);
	}

	@Override
	public void unserialize(RpcReader reader, ByteBuffer buffer, Object obj)
			throws IOException {
		float value = FloatUnserializer.read(reader, buffer);
		try {
			Accessors.unsafe.putFloat(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public void unserialize(RpcReader reader, InputStream stream, Object obj)
			throws IOException {
		float value = FloatUnserializer.read(reader, stream);
		try {
			Accessors.unsafe.putFloat(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}
}