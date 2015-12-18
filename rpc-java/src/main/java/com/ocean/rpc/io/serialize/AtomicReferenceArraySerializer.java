package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicReferenceArray;

@SuppressWarnings("rawtypes")
final class AtomicReferenceArraySerializer implements
		RpcSerializer<AtomicReferenceArray> {

	public final static AtomicReferenceArraySerializer instance = new AtomicReferenceArraySerializer();

	public final static void write(RpcWriter writer, OutputStream stream,
			WriterRefer refer, AtomicReferenceArray array) throws IOException {
		if (refer != null)
			refer.set(array);
		int length = array.length();
		stream.write(TagList);
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagOpenbrace);
		for (int i = 0; i < length; ++i) {
			writer.serialize(array.get(i));
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, AtomicReferenceArray obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(writer, stream, refer, obj);
		}
	}
}
