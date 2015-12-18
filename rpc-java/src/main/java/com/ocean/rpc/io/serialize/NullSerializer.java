package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagNull;

import java.io.IOException;

@SuppressWarnings("rawtypes")
final class NullSerializer implements RpcSerializer {

	public final static NullSerializer instance = new NullSerializer();

	@Override
	public void write(RpcWriter writer, Object obj) throws IOException {
		writer.stream.write(TagNull);
	}
}
