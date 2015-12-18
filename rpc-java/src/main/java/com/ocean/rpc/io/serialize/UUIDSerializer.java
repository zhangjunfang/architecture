package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagGuid;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

final class UUIDSerializer implements RpcSerializer<UUID> {

	public final static UUIDSerializer instance = new UUIDSerializer();

	public final static void write(OutputStream stream, WriterRefer refer,
			UUID uuid) throws IOException {
		if (refer != null)
			refer.set(uuid);
		stream.write(TagGuid);
		stream.write(TagOpenbrace);
		stream.write(ValueWriter.getAscii(uuid.toString()));
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, UUID obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(stream, refer, obj);
		}
	}
}
