/**********************************************************\
|                                                          |
|                          hprose                          |
|                                                          |
| Official WebSite: http://www.hprose.com/                 |
|                   http://www.hprose.org/                 |
|                                                          |
\**********************************************************/
/**********************************************************\
 *                                                        *
 * SafeFieldAccessor.java                                 *
 *                                                        *
 * SafeFieldAccessor class for Java.                      *
 *                                                        *
 * LastModified: Apr 27, 2015                             *
 * Author: Ma Bingyao <andot@hprose.com>                  *
 *                                                        *
 \**********************************************************/
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

public final class SafeFieldAccessor implements MemberAccessor {
	private final Field accessor;
	private final Class<?> cls;
	private final Type type;
	@SuppressWarnings("rawtypes")
	private final RpcSerializer serializer;
	private final RpcUnserializer unserializer;

	public SafeFieldAccessor(Field field) {
		field.setAccessible(true);
		accessor = field;
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
			value = accessor.get(obj);
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
			accessor.set(obj, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}

	@Override
	public void unserialize(RpcReader reader, InputStream stream, Object obj)
			throws IOException {
		Object value = unserializer.read(reader, stream, cls, type);
		try {
			accessor.set(obj, value);
		} catch (Exception e) {
			throw new RpcException(e.getMessage());
		}
	}
}