package com.ocean.rpc.io.serialize;

import java.io.IOException;

final class CharSerializer implements RpcSerializer<Character> {

	public final static CharSerializer instance = new CharSerializer();

	@Override
	public final void write(RpcWriter writer, Character obj) throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
