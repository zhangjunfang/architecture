package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicLongArray;

final class AtomicLongArraySerializer implements RpcSerializer<AtomicLongArray> {

	public final static AtomicLongArraySerializer instance = new AtomicLongArraySerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			AtomicLongArray array) throws IOException {
		if (refer != null)
			refer.set(array);
		int length = array.length();
		stream.write(TagList);
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagOpenbrace);
		for (int i = 0; i < length; ++i) {
			ValueWriter.write(stream, array.get(i));
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, AtomicLongArray obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}

}
