package com.ocean.rpc.io;

import java.io.OutputStream;

@Deprecated
public final class RpcWriter extends com.ocean.rpc.io.serialize.RpcWriter {

	public RpcWriter(OutputStream stream) {
		super(stream);
	}

	public RpcWriter(OutputStream stream, boolean simple) {
		super(stream, simple);
	}

	public RpcWriter(OutputStream stream, RpcMode mode) {
		super(stream, mode);
	}

	public RpcWriter(OutputStream stream, RpcMode mode, boolean simple) {
		super(stream, mode, simple);
	}
}
