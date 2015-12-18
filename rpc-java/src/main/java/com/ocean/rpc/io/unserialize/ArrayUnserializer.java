package com.ocean.rpc.io.unserialize;

import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;
import static com.ocean.rpc.io.RpcTags.TagRef;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.nio.ByteBuffer;

final class ArrayUnserializer implements RpcUnserializer {

	public final static ArrayUnserializer instance = new ArrayUnserializer();

	@SuppressWarnings({ "unchecked" })
	final static <T> T[] readArray(RpcReader reader, ByteBuffer buffer,
			Class<T> componentClass, Type componentType) throws IOException {
		int tag = buffer.get();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(buffer, TagOpenbrace);
			T[] a = (T[]) Array.newInstance(componentClass, count);
			reader.refer.set(a);
			RpcUnserializer unserializer = UnserializerFactory
					.get(componentClass);
			for (int i = 0; i < count; ++i) {
				a[i] = (T) unserializer.read(reader, buffer, componentClass,
						componentType);
			}
			buffer.get();
			return a;
		}
		case TagRef:
			return (T[]) reader.readRef(buffer);
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array
					.newInstance(componentClass, 0).getClass());
		}
	}

	@SuppressWarnings({ "unchecked" })
	final static <T> T[] readArray(RpcReader reader, InputStream stream,
			Class<T> componentClass, Type componentType) throws IOException {
		int tag = stream.read();
		switch (tag) {
		case TagNull:
			return null;
		case TagList: {
			int count = ValueReader.readInt(stream, TagOpenbrace);
			T[] a = (T[]) Array.newInstance(componentClass, count);
			reader.refer.set(a);
			RpcUnserializer unserializer = UnserializerFactory
					.get(componentClass);
			for (int i = 0; i < count; ++i) {
				a[i] = (T) unserializer.read(reader, stream, componentClass,
						componentType);
			}
			stream.read();
			return a;
		}
		case TagRef:
			return (T[]) reader.readRef(stream);
		default:
			throw ValueReader.castError(reader.tagToString(tag), Array
					.newInstance(componentClass, 0).getClass());
		}
	}

	@Override
	public final Object read(RpcReader reader, ByteBuffer buffer, Class<?> cls,
			Type type) throws IOException {
		Class<?> componentClass = cls.getComponentType();
		if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type)
					.getGenericComponentType();
			return readArray(reader, buffer, componentClass, componentType);
		} else {
			return readArray(reader, buffer, componentClass, componentClass);
		}
	}

	@Override
	public final Object read(RpcReader reader, InputStream stream,
			Class<?> cls, Type type) throws IOException {
		Class<?> componentClass = cls.getComponentType();
		if (type instanceof GenericArrayType) {
			Type componentType = ((GenericArrayType) type)
					.getGenericComponentType();
			return readArray(reader, stream, componentClass, componentType);
		} else {
			return readArray(reader, stream, componentClass, componentClass);
		}
	}

}
