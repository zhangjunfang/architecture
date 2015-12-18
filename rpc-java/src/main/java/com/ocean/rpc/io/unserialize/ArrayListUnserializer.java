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
import java.util.ArrayList;

import com.ocean.rpc.util.ClassUtil;

final class ArrayListUnserializer implements RpcUnserializer {

	public final static ArrayListUnserializer instance = new ArrayListUnserializer();

	@SuppressWarnings({ "unchecked" })
	private static <T> ArrayList<T> readArrayList(RpcReader reader,
			ByteBuffer buffer, Class<?> cls, Class<T> componentClass,
			Type componentType) throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(buffer, TagOpenbrace);
			ArrayList<T> a = new ArrayList<T>(count);
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
			return (ArrayList<T>) reader.readRef(buffer);
		default:
			throw ValueReader.castError(reader.tagToString(tag), cls);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private static <T> ArrayList<T> readArrayList(RpcReader reader,
			InputStream stream, Class<?> cls, Class<T> componentClass,
			Type componentType) throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(stream, TagOpenbrace);
			ArrayList<T> a = new ArrayList<T>(count);
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
			return (ArrayList<T>) reader.readRef(stream);
		default:
			throw ValueReader.castError(reader.tagToString(tag), cls);
		}
	}

	@SuppressWarnings("rawtypes")
	final static ArrayList readArrayList(RpcReader reader, ByteBuffer buffer,
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
		return readArrayList(reader, buffer, cls, componentClass, componentType);
	}

	@SuppressWarnings("rawtypes")
	final static ArrayList readArrayList(RpcReader reader, InputStream stream,
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
		return readArrayList(reader, stream, cls, componentClass, componentType);
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		return readArrayList(reader, buffer, cls, type);
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		return readArrayList(reader, stream, cls, type);
	}
}
