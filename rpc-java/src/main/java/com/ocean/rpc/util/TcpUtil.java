package com.ocean.rpc.util;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import com.ocean.rpc.io.ByteBufferStream;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public final class TcpUtil {
	public final static void sendDataOverTcp(SocketChannel channel,
			ByteBufferStream stream) throws IOException {
		int n = stream.available();
		int len = n > 1020 ? 2048 : n > 508 ? 1024 : 512;
		byte[] buf = new byte[len];
		buf[0] = (byte) ((n >> 24) & 0xff);
		buf[1] = (byte) ((n >> 16) & 0xff);
		buf[2] = (byte) ((n >> 8) & 0xff);
		buf[3] = (byte) (n & 0xff);
		int p = len - 4;
		if (n <= p) {
			stream.read(buf, 4, n);
			ByteBufferStream.wrap(buf, 0, n + 4).writeTo(channel);
		} else {
			stream.read(buf, 4, p);
			ByteBufferStream.wrap(buf, 0, len).writeTo(channel);
			stream.writeTo(channel);
		}
	}

	public final static ByteBufferStream receiveDataOverTcp(
			SocketChannel channel) throws IOException {
		ByteBufferStream buf = new ByteBufferStream();
		buf.readFrom(channel, 4);
		buf.rewind();
		int len = (buf.read() << 24) | (buf.read() << 16) | (buf.read() << 8)
				| buf.read();
		buf.close();
		ByteBufferStream data = new ByteBufferStream(len);
		data.readFrom(channel, len);
		return data;
	}
}
