package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagMap;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

@SuppressWarnings("rawtypes")
final class MapSerializer implements RpcSerializer<Map> {

	public final static MapSerializer instance = new MapSerializer();

	public final static void write(RpcWriter writer, OutputStream stream,
			WriterRefer refer, Map<?, ?> map) throws IOException {
		if (refer != null)
			refer.set(map);
		int count = map.size();
		stream.write(TagMap);
		if (count > 0) {
			ValueWriter.writeInt(stream, count);
		}
		stream.write(TagOpenbrace);
		for (Map.Entry<?, ?> entry : map.entrySet()) {
			writer.serialize(entry.getKey());
			writer.serialize(entry.getValue());
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, Map obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(writer, stream, refer, obj);
		}
	}
}
