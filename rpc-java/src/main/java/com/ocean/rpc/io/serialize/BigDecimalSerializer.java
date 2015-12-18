package com.ocean.rpc.io.serialize;

import java.io.IOException;
import java.math.BigDecimal;

final class BigDecimalSerializer implements RpcSerializer<BigDecimal> {

	public final static BigDecimalSerializer instance = new BigDecimalSerializer();

	@Override
	public final void write(RpcWriter writer, BigDecimal obj)
			throws IOException {
		ValueWriter.write(writer.stream, obj);
	}
}
