package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagNull;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;

final class CalendarArraySerializer implements RpcSerializer<Calendar[]> {

	public final static CalendarArraySerializer instance = new CalendarArraySerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			Calendar[] array) throws IOException {
		if (refer != null)
			refer.set(array);
		int length = array.length;
		stream.write(TagList);
		if (length > 0) {
			ValueWriter.writeInt(stream, length);
		}
		stream.write(TagOpenbrace);
		for (int i = 0; i < length; ++i) {
			Calendar e = array[i];
			if (e == null) {
				stream.write(TagNull);
			} else if (refer == null || !refer.write(stream, e)) {
				CalendarSerializer.write(stream, refer, e);
			}
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, Calendar[] obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
