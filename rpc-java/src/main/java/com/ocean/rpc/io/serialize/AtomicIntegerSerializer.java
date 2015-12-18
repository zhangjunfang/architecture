package com.ocean.rpc.io.serialize;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

final class AtomicIntegerSerializer implements RpcSerializer<AtomicInteger> {

	public final static AtomicIntegerSerializer instance = new AtomicIntegerSerializer();

	@Override
	public final void write(RpcWriter writer, AtomicInteger obj)
			throws IOException {
		ValueWriter.write(writer.stream, obj.get());
	}
}
