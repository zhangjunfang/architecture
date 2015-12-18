package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class DoubleSerializer implements RpcSerializer<Double> {

	public final static DoubleSerializer instance = new DoubleSerializer();

	@Override
	public final void write(RpcWriter writer, Double obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
