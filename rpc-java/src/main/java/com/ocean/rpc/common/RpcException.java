package com.ocean.rpc.common;

import java.io.IOException;

public class RpcException extends IOException {

	private final static long serialVersionUID = -6146544906159301857L;

	public RpcException() {
		super();
	}

	public RpcException(String msg) {
		super(msg);
	}
}
