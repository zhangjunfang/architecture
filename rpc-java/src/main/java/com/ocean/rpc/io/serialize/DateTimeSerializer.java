package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.Date;

import com.ocean.rpc.util.DateTime;

final class DateTimeSerializer implements RpcSerializer<Date> {

	public final static DateTimeSerializer instance = new DateTimeSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			Date date) throws IOException {
		if (refer != null)
			refer.set(date);
		Calendar calendar = DateTime.toCalendar(date);
		ValueWriter.writeDateOfCalendar(stream, calendar);
		ValueWriter.writeTimeOfCalendar(stream, calendar, true, false);
		stream.write(TagSemicolon);
	}

	@Override
	public final void write(RpcWriter writer, Date obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
