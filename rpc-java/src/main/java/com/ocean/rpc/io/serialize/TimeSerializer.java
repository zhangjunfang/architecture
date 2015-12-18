package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Time;
import java.util.Calendar;

import com.ocean.rpc.util.DateTime;

final class TimeSerializer implements RpcSerializer<Time> {

	public final static TimeSerializer instance = new TimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			Time time) throws IOException {
		if (refer != null)
			refer.set(time);
		Calendar calendar = DateTime.toCalendar(time);
		ValueWriter.writeTimeOfCalendar(stream, calendar, false, false);
		stream.write(TagSemicolon);
	}

	@Override
	public final void write(RpcWriter writer, Time obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
