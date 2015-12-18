package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class FloatSerializer implements RpcSerializer<Float> {

	public final static FloatSerializer instance = new FloatSerializer();

	@Override
	public final void write(RpcWriter writer, Float obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
