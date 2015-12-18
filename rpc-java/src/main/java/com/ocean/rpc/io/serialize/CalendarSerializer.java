package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;
import static com.ocean.rpc.io.RpcTags.TagUTC;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.TimeZone;

import com.ocean.rpc.util.TimeZoneUtil;

final class CalendarSerializer implements RpcSerializer<Calendar> {

	public final static CalendarSerializer instance = new CalendarSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			Calendar calendar) throws IOException {
		if (refer != null)
			refer.set(calendar);
		TimeZone tz = calendar.getTimeZone();
		if (!(tz.hasSameRules(TimeZoneUtil.DefaultTZ) || tz
				.hasSameRules(TimeZoneUtil.UTC))) {
			tz = TimeZoneUtil.UTC;
			Calendar c = (Calendar) calendar.clone();
			c.setTimeZone(tz);
			calendar = c;
		}
		ValueWriter.writeDateOfCalendar(stream, calendar);
		ValueWriter.writeTimeOfCalendar(stream, calendar, true, false);
		stream.write(tz.hasSameRules(TimeZoneUtil.UTC) ? TagUTC : TagSemicolon);
	}

	@Override
	public final void write(RpcWriter writer, Calendar obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
