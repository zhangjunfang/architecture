package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class ShortSerializer implements RpcSerializer<Short> {

	public final static ShortSerializer instance = new ShortSerializer();

	@Override
	public final void write(RpcWriter writer, Short obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
