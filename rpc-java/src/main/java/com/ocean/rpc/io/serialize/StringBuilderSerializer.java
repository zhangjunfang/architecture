package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagEmpty;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.OutputStream;

final class StringBuilderSerializer implements RpcSerializer<StringBuilder> {

	public final static StringBuilderSerializer instance = new StringBuilderSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			StringBuilder s) throws IOException {
		if (refer != null)
			refer.set(s);
		stream.write(TagString);
		ValueWriter.write(stream, s.toString());
	}

	@Override
	public final void write(RpcWriter writer, StringBuilder obj)
			throws IOException {
		OutputStream stream = writer.stream;
		switch (obj.length()) {
		case 0:
			stream.write(TagEmpty);
			break;
		case 1:
			ValueWriter.write(stream, obj.charAt(0));
			break;
		default:
			WriterRefer refer = writer.refer;
			if (refer == null || !refer.write(stream, obj)) {
				write(stream, refer, obj);
			}
			break;
		}
	}
}
