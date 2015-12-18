package com.ocean.rpc.io.serialize;

import java.io.IOException;
import java.math.BigInteger;

final class BigIntegerSerializer implements RpcSerializer<BigInteger> {

	public final static BigIntegerSerializer instance = new BigIntegerSerializer();

	@Override
	public final void write(RpcWriter writer, BigInteger obj)
			throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
