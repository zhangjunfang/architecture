package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagSemicolon;
import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;

final class LocalDateSerializer implements RpcSerializer<LocalDate> {

	public final static LocalDateSerializer instance = new LocalDateSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			LocalDate date) throws IOException {
		if (refer != null)
			refer.set(date);
		int year = date.getYear();
		if (year > 9999 || year < 1) {
			stream.write(TagString);
			ValueWriter.write(stream, date.toString());
		} else {
			ValueWriter.writeDate(stream, year, date.getMonthValue(),
					date.getDayOfMonth());
			stream.write(TagSemicolon);
		}
	}

	@Override
	public final void write(RpcWriter writer, LocalDate obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
