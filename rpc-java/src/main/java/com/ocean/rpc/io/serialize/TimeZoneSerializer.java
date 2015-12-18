package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagString;

import java.io.IOException;
import java.io.OutputStream;
import java.util.TimeZone;

final class TimeZoneSerializer implements RpcSerializer<TimeZone> {

	public final static TimeZoneSerializer instance = new TimeZoneSerializer();

	@Override
	public final void write(RpcWriter writer, TimeZone obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			if (refer != null)
				refer.set(obj);
			stream.write(TagString);
			ValueWriter.write(stream, obj.getID());
		}
	}
}
