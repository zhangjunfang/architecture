package com.ocean.rpc.io.accessor;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.serialize.ValueWriter;
import com.ocean.rpc.io.unserialize.BooleanUnserializer;
import com.ocean.rpc.io.unserialize.RpcReader;
@SuppressWarnings("restriction")
public final class BoolFieldAccessor implements MemberAccessor {
	private final long offset;

	public BoolFieldAccessor(Field accessor) {
		accessor.setAccessible(true);
		offset = Accessors.unsafe.objectFieldOffset(accessor);
	}

	@Override
	public final void serialize(RpcWriter writer, Object obj)
			throws IOException {
		boolean value;
		try {
			value = Accessors.unsafe.getBoolean(obj, offset);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
		ValueWriter.write(writer.stream, value);
	}

	@Override
	public final void unserialize(RpcReader reader, ByteBuffer buffer,
			Object obj) throws IOException {
		boolean value = BooleanUnserializer.read(reader, buffer);
		try {
			Accessors.unsafe.putBoolean(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public final void unserialize(RpcReader reader, InputStream stream,
			Object obj) throws IOException {
		boolean value = BooleanUnserializer.read(reader, stream);
		try {
			Accessors.unsafe.putBoolean(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}
}