package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;
import static com.ocean.rpc.io.RpcTags.TagUTC;

import java.io.IOException;
import java.io.OutputStream;

import com.ocean.rpc.util.DateTime;

final class RpcDateTimeSerializer implements RpcSerializer<DateTime> {

	public final static RpcDateTimeSerializer instance = new RpcDateTimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			DateTime dt) throws IOException {
		if (refer != null)
			refer.set(dt);
		if (dt.year == 1970 && dt.month == 1 && dt.day == 1) {
			ValueWriter.writeTime(stream, dt.hour, dt.minute, dt.second, 0,
					false, true);
			ValueWriter.writeNano(stream, dt.nanosecond);
		} else {
			ValueWriter.writeDate(stream, dt.year, dt.month, dt.day);
			if (dt.nanosecond == 0) {
				ValueWriter.writeTime(stream, dt.hour, dt.minute, dt.second, 0,
						true, true);
			} else {
				ValueWriter.writeTime(stream, dt.hour, dt.minute, dt.second, 0,
						false, true);
				ValueWriter.writeNano(stream, dt.nanosecond);
			}
		}
		stream.write(dt.utc ? TagUTC : TagSemicolon);
	}

	@Override
	public final void write(RpcWriter writer, DateTime obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
