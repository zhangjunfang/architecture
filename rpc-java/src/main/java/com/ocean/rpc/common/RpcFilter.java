package com.ocean.rpc.common;

import java.nio.ByteBuffer;

public interface RpcFilter {
	ByteBuffer inputFilter(ByteBuffer istream, RpcContext context);

	ByteBuffer outputFilter(ByteBuffer ostream, RpcContext context);
}