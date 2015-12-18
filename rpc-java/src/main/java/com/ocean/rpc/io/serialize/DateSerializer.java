package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Calendar;

import com.ocean.rpc.util.DateTime;

final class DateSerializer implements RpcSerializer<Date> {

	public final static DateSerializer instance = new DateSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			Date date) throws IOException {
		if (refer != null)
			refer.set(date);
		Calendar calendar = DateTime.toCalendar(date);
		ValueWriter.writeDateOfCalendar(stream, calendar);
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
