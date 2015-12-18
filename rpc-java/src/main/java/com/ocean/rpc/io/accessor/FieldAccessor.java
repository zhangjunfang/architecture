package com.ocean.rpc.io.accessor;

import static com.ocean.rpc.io.RpcTags.TagNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.serialize.RpcSerializer;
import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.serialize.SerializerFactory;
import com.ocean.rpc.io.unserialize.RpcReader;
import com.ocean.rpc.io.unserialize.RpcUnserializer;
import com.ocean.rpc.io.unserialize.UnserializerFactory;
@SuppressWarnings("restriction")
public final class FieldAccessor implements MemberAccessor {
	private final long offset;
	private final Class<?> cls;
	private final Type type;
	@SuppressWarnings("rawtypes")
	private final RpcSerializer serializer;
	private final RpcUnserializer unserializer;

	public FieldAccessor(Field accessor) {
		accessor.setAccessible(true);
		offset = Accessors.unsafe.objectFieldOffset(accessor);
		type = accessor.getGenericType();
		cls = accessor.getType();
		serializer = SerializerFactory.get(cls);
		unserializer = UnserializerFactory.get(cls);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public void serialize(RpcWriter writer, Object obj) throws IOException {
		Object value;
		try {
			value = Accessors.unsafe.getObject(obj, offset);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
		if (value == null) {
			writer.stream.write(TagNull);
		} else {
			serializer.write(writer, value);
		}
	}

	@Override
	public void unserialize(RpcReader reader, ByteBuffer buffer, Object obj)
			throws IOException {
		Object value = unserializer.read(reader, buffer, cls, type);
		try {
			Accessors.unsafe.putObject(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public void unserialize(RpcReader reader, InputStream stream, Object obj)
			throws IOException {
		Object value = unserializer.read(reader, stream, cls, type);
		try {
			Accessors.unsafe.putObject(obj, offset, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}
}