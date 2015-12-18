package com.ocean.rpc.io.serialize;

import static com.ocean.rpc.io.RpcTags.TagClosebrace;
import static com.ocean.rpc.io.RpcTags.TagList;
import static com.ocean.rpc.io.RpcTags.TagOpenbrace;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.RandomAccess;

@SuppressWarnings("rawtypes")
final class ListSerializer implements RpcSerializer<List> {

	public final static ListSerializer instance = new ListSerializer();

	public final static void write(RpcWriter writer, OutputStream stream,
			WriterRefer refer, List list) throws IOException {
		if (refer != null)
			refer.set(list);
		int count = list.size();
		stream.write(TagList);
		if (count > 0) {
			ValueWriter.writeInt(stream, count);
		}
		stream.write(TagOpenbrace);
		if (list instanceof RandomAccess) {
			for (int i = 0; i < count; ++i) {
				writer.serialize(list.get(i));
			}
		} else {
			for (Iterator i = list.iterator(); i.hasNext();) {
				writer.serialize(i.next());
			}
		}
		stream.write(TagClosebrace);
	}

	@Override
	public final void write(RpcWriter writer, List obj) throws IOException {
		OutputStream stream = writer.stream;
		WriterRefer refer = writer.refer;
		if (refer == null || !refer.write(stream, obj)) {
			write(writer, stream, refer, obj);
		}
	}
}
