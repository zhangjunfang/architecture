package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class LongSerializer implements RpcSerializer<Long> {

	public final static LongSerializer instance = new LongSerializer();

	@Override
	public final void write(RpcWriter writer, Long obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
