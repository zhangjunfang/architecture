package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;
import static com.ocean.rpc.io.RpcTags.TagRef;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.HashSet;

import com.ocean.rpc.util.ClassUtil;

final class HashSetUnserializer implements RpcUnserializer {

	public final static HashSetUnserializer instance = new HashSetUnserializer();

	@SuppressWarnings({ "unchecked" })
	private static <T> HashSet<T> readHashSet(RpcReader reader,
			ByteBuffer buffer, Class<?> cls, Class<T> componentClass,
			Type componentType) throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(buffer, TagOpenbrace);
			HashSet<T> a = new HashSet<T>(count);
			reader.refer.set(a);
			RpcUnserializer unserializer = UnserializerFactory
					.get(componentClass);
			for (int i = 0; i < count; ++i) {
				a.add((T) unserializer.read(reader, buffer, componentClass,
						componentType));
			}
			buffer.get();
			return a;
		}
		case TagRef:
			return (HashSet<T>) reader.readRef(buffer);
		default:
			throw ValueReader.castError(reader.tagToString(tag), cls);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private static <T> HashSet<T> readHashSet(RpcReader reader,
			InputStream stream, Class<?> cls, Class<T> componentClass,
			Type componentType) throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(stream, TagOpenbrace);
			HashSet<T> a = new HashSet<T>(count);
			reader.refer.set(a);
			RpcUnserializer unserializer = UnserializerFactory
					.get(componentClass);
			for (int i = 0; i < count; ++i) {
				a.add((T) unserializer.read(reader, stream, componentClass,
						componentType));
			}
			stream.read();
			return a;
		}
		case TagRef:
			return (HashSet<T>) reader.readRef(stream);
		default:
			throw ValueReader.castError(reader.tagToString(tag), cls);
		}
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		Type componentType;
		Class<?> componentClass;
		if (type instanceof ParameterizedType) {
			componentType = ((ParameterizedType) type).getActualTypeArguments()[0];
			componentClass = ClassUtil.toClass(componentType);
		} else {
			componentType = Object.class;
			componentClass = Object.class;
		}
		return readHashSet(reader, buffer, cls, componentClass, componentType);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		Type componentType;
		Class<?> componentClass;
		if (type instanceof ParameterizedType) {
			componentType = ((ParameterizedType) type).getActualTypeArguments()[0];
			componentClass = ClassUtil.toClass(componentType);
		} else {
			componentType = Object.class;
			componentClass = Object.class;
		}
		return readHashSet(reader, stream, cls, componentClass, componentType);
	}

}
