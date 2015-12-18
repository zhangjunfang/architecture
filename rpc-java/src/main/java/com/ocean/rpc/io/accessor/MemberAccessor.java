package com.ocean.rpc.io.accessor;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import com.ocean.rpc.io.serialize.RpcWriter;
import com.ocean.rpc.io.unserialize.RpcReader;

public interface MemberAccessor {
	void serialize(RpcWriter writer, Object obj) throws IOException;

	void unserialize(RpcReader reader, ByteBuffer buffer, Object obj)
			throws IOException;

	void unserialize(RpcReader reader, InputStream stream, Object obj)
			throws IOException;
}