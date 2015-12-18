package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

final class BigIntegerArraySerializer implements RpcSerializer<BigInteger[]> {

	public final static BigIntegerArraySerializer instance = new BigIntegerArraySerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			BigInteger[] array) throws IOException {
		if (refer != null)
			refer.set(array);
		int length = array.length;
		stream.write(TagList);
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagOpenbrace);
		for (int i = 0; i < length; ++i) {
			BigInteger e = array[i];
			if (e == null) {
				stream.write(TagNull);
			} else {
				ValueWriter.write(stream, e);
			}
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, BigInteger[] obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
