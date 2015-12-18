package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class ByteSerializer implements RpcSerializer<Byte> {

	public final static ByteSerializer instance = new ByteSerializer();

	@Override
	public final void write(RpcWriter writer, Byte obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
