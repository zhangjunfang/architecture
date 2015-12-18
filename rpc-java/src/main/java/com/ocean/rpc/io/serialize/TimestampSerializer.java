package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Calendar;

import com.ocean.rpc.util.DateTime;

final class TimestampSerializer implements RpcSerializer<Timestamp> {

	public final static TimestampSerializer instance = new TimestampSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			Timestamp time) throws IOException {
		if (refer != null)
			refer.set(time);
		Calendar calendar = DateTime.toCalendar(time);
		ValueWriter.writeDateOfCalendar(stream, calendar);
		ValueWriter.writeTimeOfCalendar(stream, calendar, false, true);
		ValueWriter.writeNano(stream, time.getNanos());
		stream.write(TagSemicolon);
	}

	@Override
	public final void write(RpcWriter writer, Timestamp obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}

}
