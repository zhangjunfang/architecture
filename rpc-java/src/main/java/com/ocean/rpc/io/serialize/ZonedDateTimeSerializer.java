package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagString;
import static com.ocean.rpc.io.RpcTags.TagUTC;

import java.io.IOException;
import java.io.OutputStream;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

final class ZonedDateTimeSerializer implements RpcSerializer<ZonedDateTime> {

	public final static ZonedDateTimeSerializer instance = new ZonedDateTimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			ZonedDateTime datetime) throws IOException {
		if (refer != null)
			refer.set(datetime);
		if (!(datetime.getOffset().equals(ZoneOffset.UTC))) {
			stream.write(TagString);
			ValueWriter.write(stream, datetime.toString());
		} else {
			int year = datetime.getYear();
			if (year > 9999 || year < 1) {
				stream.write(TagString);
				ValueWriter.write(stream, datetime.toString());
			} else {
				ValueWriter.writeDate(stream, year, datetime.getMonthValue(),
						datetime.getDayOfMonth());
				ValueWriter.writeTime(stream, datetime.getHour(),
						datetime.getMinute(), datetime.getSecond(), 0, false,
						true);
				ValueWriter.writeNano(stream, datetime.getNano());
				stream.write(TagUTC);
			}
		}
	}

	@Override
	public final void write(RpcWriter writer, ZonedDateTime obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}