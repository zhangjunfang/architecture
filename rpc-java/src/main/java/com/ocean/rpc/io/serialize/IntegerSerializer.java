package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class IntegerSerializer implements RpcSerializer<Integer> {

	public final static IntegerSerializer instance = new IntegerSerializer();

	@Override
	public final void write(RpcWriter writer, Integer obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
