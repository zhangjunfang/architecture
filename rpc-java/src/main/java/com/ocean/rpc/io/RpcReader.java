package com.ocean.rpc.io;

import java.io.InputStream;
import java.nio.ByteBuffer;

@Deprecated
public final class RpcReader extends com.ocean.rpc.io.unserialize.RpcReader {

	public RpcReader(InputStream stream) {
		super(stream);
	}

	public RpcReader(InputStream stream, boolean simple) {
		super(stream, simple);
	}

	public RpcReader(InputStream stream, RpcMode mode) {
		super(stream, mode);
	}

	public RpcReader(InputStream stream, RpcMode mode, boolean simple) {
		super(stream, mode, simple);
	}

	public RpcReader(ByteBuffer buffer) {
		super(buffer);
	}

	public RpcReader(ByteBuffer buffer, boolean simple) {
		super(buffer, simple);
	}

	public RpcReader(ByteBuffer buffer, RpcMode mode) {
		super(buffer, mode);
	}

	public RpcReader(ByteBuffer buffer, RpcMode mode, boolean simple) {
		super(buffer, mode, simple);
	}

	public RpcReader(byte[] bytes) {
		super(bytes);
	}

	public RpcReader(byte[] bytes, boolean simple) {
		super(bytes, simple);
	}

	public RpcReader(byte[] bytes, RpcMode mode) {
		super(bytes, mode);
	}

	public RpcReader(byte[] bytes, RpcMode mode, boolean simple) {
		super(bytes, mode, simple);
	}

}