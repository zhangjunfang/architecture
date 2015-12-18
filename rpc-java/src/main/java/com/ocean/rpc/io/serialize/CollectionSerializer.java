package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

@SuppressWarnings("rawtypes")
final class CollectionSerializer implements RpcSerializer<Collection> {

	public final static CollectionSerializer instance = new CollectionSerializer();

	public final static void write(RpcWriter writer, OutputStream stream,
			WriterRefer refer, Collection collection) throws IOException {
		if (refer != null)
			refer.set(collection);
		int count = collection.size();
		stream.write(TagList);
		if (count > 0) {
			ValueWriter.writeInt(stream, count);
		}
		stream.write(TagOpenbrace);
		for (Iterator i = collection.iterator(); i.hasNext();) {
			writer.serialize(i.next());
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, Collection obj)
			throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(writer, stream, refer, obj);
		}
	}
}
