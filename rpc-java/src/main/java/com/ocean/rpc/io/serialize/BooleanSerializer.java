package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class BooleanSerializer implements RpcSerializer<Boolean> {

	public final static BooleanSerializer instance = new BooleanSerializer();

	@Override
	public final void write(RpcWriter writer, Boolean obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
