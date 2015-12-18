package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagBytes;
import static com.ocean.rpc.io.RpcTags.TagQuote;

import java.io.IOException;
import java.io.OutputStream;

final class ByteArraySerializer implements RpcSerializer<byte[]> {

	public final static ByteArraySerializer instance = new ByteArraySerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			byte[] bytes) throws IOException {
		if (refer != null)
			refer.set(bytes);
		stream.write(TagBytes);
		int length = bytes.length;
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagQuote);
		stream.write(bytes);
		stream.write(TagQuote);
	}

	@Override
	public final void write(RpcWriter writer, byte[] obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
