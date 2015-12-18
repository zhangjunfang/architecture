package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;

final class FloatArraySerializer implements RpcSerializer<float[]> {

	public final static FloatArraySerializer instance = new FloatArraySerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			float[] array) throws IOException {
		if (refer != null)
			refer.set(array);
		int length = array.length;
		stream.write(TagList);
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagOpenbrace);
		for (int i = 0; i < length; ++i) {
			ValueWriter.write(stream, array[i]);
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, float[] obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
