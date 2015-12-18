package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.OutputStream;

@SuppressWarnings("rawtypes")
final class ToStringSerializer implements RpcSerializer {

	public final static ToStringSerializer instance = new ToStringSerializer();

	@Override
	public final void write(RpcWriter writer, Object obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			if (refer != null)
				refer.set(obj);
			stream.write(TagString);
			ValueWriter.write(stream, obj.toString());
		}
	}
}
