package com.ocean.rpc.io.accessor;

import static com.ocean.rpc.io.RpcTags.TagNull;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

import com.ocean.rpc.common.RpcException;
import com.ocean.rpc.io.serialize.RpcSerializer;
import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.serialize.SerializerFactory;
import com.ocean.rpc.io.unserialize.RpcReader;
import com.ocean.rpc.io.unserialize.RpcUnserializer;
import com.ocean.rpc.io.unserialize.UnserializerFactory;
import com.ocean.rpc.util.ClassUtil;

public final class PropertyAccessor implements MemberAccessor {
	private final static Object[] nullArgs = new Object[0];
	private final Method getter;
	private final Method setter;
	private final Class<?> cls;
	private final Type type;
	@SuppressWarnings("rawtypes")
	private final RpcSerializer serializer;
	private final RpcUnserializer unserializer;

	public PropertyAccessor(Method getter, Method setter) {
		getter.setAccessible(true);
		setter.setAccessible(true);
		this.getter = getter;
		this.setter = setter;
		this.type = getter.getGenericReturnType();
		this.cls = ClassUtil.toClass(type);
		this.serializer = SerializerFactory.get(cls);
		this.unserializer = UnserializerFactory.get(cls);
	}

	@Override
	@SuppressWarnings({ "unchecked" })
	public void serialize(RpcWriter writer, Object obj) throws IOException {
		Object value;
		try {
			value = getter.invoke(obj, nullArgs);
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
			setter.invoke(obj, new Object[] { value });
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public void unserialize(RpcReader reader, InputStream stream, Object obj)
			throws IOException {
		Object value = unserializer.read(reader, stream, cls, type);
		try {
			setter.invoke(obj, new Object[] { value });
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

}