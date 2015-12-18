package com.ocean.rpc.io.serialize;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@SuppressWarnings("rawtypes")
final class AtomicReferenceSerializer implements RpcSerializer<AtomicReference> {

	public final static AtomicReferenceSerializer instance = new AtomicReferenceSerializer();

	@Override
	public final void write(RpcWriter writer, AtomicReference obj)
			throws IOException {
		writer.serialize(obj.get());
	}
}
