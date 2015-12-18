package com.ocean.rpc.io.serialize;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

final class AtomicBooleanSerializer implements RpcSerializer<AtomicBoolean> {

	public final static AtomicBooleanSerializer instance = new AtomicBooleanSerializer();

	@Override
	public final void write(RpcWriter writer, AtomicBoolean obj)
			throws IOException {
		ValueWriter.write(writer.stream, obj.get());
	}
}
