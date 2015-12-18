package com.ocean.rpc.io.serialize;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

final class AtomicLongSerializer implements RpcSerializer<AtomicLong> {

	public final static AtomicLongSerializer instance = new AtomicLongSerializer();

	@Override
	public final void write(RpcWriter writer, AtomicLong obj)
			throws IOException {
		ValueWriter.write(writer.stream, obj.get());
	}
}
