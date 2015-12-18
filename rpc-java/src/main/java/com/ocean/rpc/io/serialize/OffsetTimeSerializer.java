package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagString;
import static com.ocean.rpc.io.RpcTags.TagUTC;

import java.io.IOException;
import java.io.OutputStream;
import java.time.OffsetTime;
import java.time.ZoneOffset;

final class OffsetTimeSerializer implements RpcSerializer<OffsetTime> {

	public final static OffsetTimeSerializer instance = new OffsetTimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			OffsetTime time) throws IOException {
		if (refer != null)
			refer.set(time);
		if (!(time.getOffset().equals(ZoneOffset.UTC))) {
			stream.write(TagString);
			ValueWriter.write(stream, time.toString());
		} else {
			ValueWriter.writeTime(stream, time.getHour(), time.getMinute(),
					time.getSecond(), 0, false, true);
			ValueWriter.writeNano(stream, time.getNano());
			stream.write(TagUTC);
		}
	}

	@Override
	public final void write(RpcWriter writer, OffsetTime obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
