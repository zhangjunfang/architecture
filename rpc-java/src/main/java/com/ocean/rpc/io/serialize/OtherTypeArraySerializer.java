package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Array;

@SuppressWarnings("rawtypes")
final class OtherTypeArraySerializer implements RpcSerializer {

	public final static OtherTypeArraySerializer instance = new OtherTypeArraySerializer();

	public final static void write(RpcWriter writer, OutputStream stream,
			WriterRefer refer, Object array) throws IOException {
		if (refer != null)
			refer.set(array);
		int length = Array.getLength(array);
		stream.write(TagList);
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagOpenbrace);
		for (int i = 0; i < length; ++i) {
			writer.serialize(Array.get(array, i));
		}
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
