package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalTime;

final class LocalTimeSerializer implements RpcSerializer<LocalTime> {

	public final static LocalTimeSerializer instance = new LocalTimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			LocalTime time) throws IOException {
		if (refer != null)
			refer.set(time);
		ValueWriter.writeTime(stream, time.getHour(), time.getMinute(),
				time.getSecond(), 0, false, true);
		ValueWriter.writeNano(stream, time.getNano());
		stream.write(TagSemicolon);
	}

	@Override
	public final void write(RpcWriter writer, LocalTime obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
