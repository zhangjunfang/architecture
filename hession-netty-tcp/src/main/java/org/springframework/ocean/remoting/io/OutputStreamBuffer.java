package org.springframework.ocean.remoting.io;

import static org.jboss.netty.buffer.ChannelBuffers.dynamicBuffer;

import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.DownstreamMessageEvent;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：基于Channel封装的输出流
 */
public class OutputStreamBuffer extends OutputStream {

	/** The context of the channel on which to send the bytes downstream */
	private final ChannelHandlerContext _ctx;
	/** Indicates if the stream has been closed */
	private boolean _closed = false;
	private final Lock _lock = new ReentrantLock();
	/** If written bytes > watermark, the bytes are sent downstream */
	int _watermark = 1024;
	int _initialBuffSize = 2048;
	/**
	 * The buffer for storing outgoing bytes. Once the bytes have been sent
	 * downstream a new buffer is created
	 */
	private ChannelBuffer _buf = dynamicBuffer();

	/**
	 * Instantiates a new output stream buffer.
	 * 
	 * @param ctx
	 *            the context in which bytes are sent downstream
	 */
	public OutputStreamBuffer(ChannelHandlerContext ctx) {
		super();
		_ctx = ctx;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(int)
	 */
	@Override
	public void write(int b) throws IOException {
		// System.out.println("write 1");
		if (_closed)
			throw new IOException("stream closed");
		_lock.lock();
		try {
			_buf.writeByte((byte) b);
			if (_buf.writerIndex() >= _watermark)
				sendDownstream();
		} finally {
			_lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#write(byte[], int, int)
	 */
	@Override
	public void write(byte b[], int off, int len) throws IOException {
		// System.out.println("write "+len);
		if (_closed)
			throw new IOException("stream closed");
		_lock.lock();
		try {
			_buf.writeBytes(b, off, len);
			if (_buf.writerIndex() >= _watermark)
				sendDownstream();
		} finally {
			_lock.unlock();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#flush()
	 */
	@Override
	public void flush() {
		// System.out.println("flush");
		_lock.lock();
		try {
			sendDownstream();
		} finally {
			_lock.unlock();
		}
	}

	private void sendDownstream() {
		ChannelFuture future = Channels.future(_ctx.getChannel());
		_ctx.sendDownstream(new DownstreamMessageEvent(_ctx.getChannel(),
				future, _buf, _ctx.getChannel().getRemoteAddress()));
		_buf = dynamicBuffer();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.io.OutputStream#close()
	 */
	@Override
	public void close() throws IOException {
		_closed = true;
		super.close();
	}
}
