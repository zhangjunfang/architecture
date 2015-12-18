package com.ocean.rpc.io.serialize;

import java.io.IOException;

@SuppressWarnings("rawtypes")
final class EnumSerializer implements RpcSerializer<Enum> {

	public final static EnumSerializer instance = new EnumSerializer();

	@Override
	public final void write(RpcWriter writer, Enum obj) throws IOException {
		ValueWriter.write(writer.stream, obj.ordinal());
	}
}
