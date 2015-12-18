package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;

final class LocalDateTimeSerializer implements RpcSerializer<LocalDateTime> {

	public final static LocalDateTimeSerializer instance = new LocalDateTimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			LocalDateTime datetime) throws IOException {
		if (refer != null)
			refer.set(datetime);
		int year = datetime.getYear();
		if (year > 9999 || year < 1) {
			stream.write(TagString);
			ValueWriter.write(stream, datetime.toString());
		} else {
			ValueWriter.writeDate(stream, year, datetime.getMonthValue(),
					datetime.getDayOfMonth());
			ValueWriter.writeTime(stream, datetime.getHour(),
					datetime.getMinute(), datetime.getSecond(), 0, false, true);
			ValueWriter.writeNano(stream, datetime.getNano());
			stream.write(TagSemicolon);
		}
	}

	@Override
	public final void write(RpcWriter writer, LocalDateTime obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
