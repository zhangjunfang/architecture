package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClass;
import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagObject;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ocean.rpc.io.RpcMode;
import com.ocean.rpc.io.accessor.Accessors;
import com.ocean.rpc.io.accessor.MemberAccessor;
import com.ocean.rpc.util.ClassUtil;

@SuppressWarnings("rawtypes")
final class OtherTypeSerializer implements RpcSerializer {

	public final static OtherTypeSerializer instance = new OtherTypeSerializer();

	private final static EnumMap<RpcMode, ConcurrentHashMap<Class<?>, SerializeCache>> memberCache = new EnumMap<RpcMode, ConcurrentHashMap<Class<?>, SerializeCache>>(
			RpcMode.class);

	static {
		memberCache.put(RpcMode.FieldMode,
				new ConcurrentHashMap<Class<?>, SerializeCache>());
		memberCache.put(RpcMode.PropertyMode,
				new ConcurrentHashMap<Class<?>, SerializeCache>());
		memberCache.put(RpcMode.MemberMode,
				new ConcurrentHashMap<Class<?>, SerializeCache>());
	}

	final static class SerializeCache {
		byte[] data;
		int refcount;
	}

	private static void writeObject(RpcWriter writer, Object object,
			Class<?> type) throws IOException {
		Map<String, MemberAccessor> members = Accessors.getMembers(type,
				writer.mode);
		for (Map.Entry<String, MemberAccessor> entry : members.entrySet()) {
			MemberAccessor member = entry.getValue();
			member.serialize(writer, object);
		}
	}

	private static int writeClass(RpcWriter writer, Class<?> type)
			throws IOException {
		SerializeCache cache = memberCache.get(writer.mode).get(type);
		if (cache == null) {
			cache = new SerializeCache();
			ByteArrayOutputStream cachestream = new ByteArrayOutputStream();
			Map<String, MemberAccessor> members = Accessors.getMembers(type,
					writer.mode);
			int count = members.size();
			cachestream.write(TagClass);
			ValueWriter.write(cachestream, ClassUtil.getClassAlias(type));
			if (count > 0) {
				ValueWriter.writeInt(cachestream, count);
			}
			cachestream.write(TagOpenbrace);
			for (Map.Entry<String, MemberAccessor> member : members.entrySet()) {
				cachestream.write(TagString);
				ValueWriter.write(cachestream, member.getKey());
				++cache.refcount;
			}
			cachestream.write(TagClosebrace);
			cache.data = cachestream.toByteArray();
			memberCache.get(writer.mode).put(type, cache);
		}
		writer.stream.write(cache.data);
		if (writer.refer != null) {
			writer.refer.addCount(cache.refcount);
		}
		int cr = writer.lastclassref++;
		writer.classref.put(type, cr);
		return cr;
	}

	public final static void write(RpcWriter writer, OutputStream stream,
			WriterRefer refer, Object object) throws IOException {
		Class<?> type = object.getClass();
		Integer cr = writer.classref.get(type);
		if (cr == null) {
			cr = writeClass(writer, type);
		}
		if (refer != null)
			refer.set(object);
		stream.write(TagObject);
		ValueWriter.write(stream, cr);
		stream.write(TagOpenbrace);
		writeObject(writer, object, type);
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, Object obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(writer, stream, refer, obj);
		}
	}
}
