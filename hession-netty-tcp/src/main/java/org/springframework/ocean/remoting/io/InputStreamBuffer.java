
package org.springframework.ocean.remoting.io;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.jboss.netty.buffer.ChannelBuffer;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：基于Channel读取字节封装的输入流
 */
public class InputStreamBuffer extends InputStream
{

	/** Buffer for storing incoming bytes */
	LinkedList<ChannelBuffer> bufs = new LinkedList<ChannelBuffer>();
	/** Indicates if the stream has been closed */
	private volatile boolean closed = false;
	private final Lock lock = new ReentrantLock();
	/** Sync condition indicating that buffer is not empty. */
	private volatile Condition notEmpty = lock.newCondition();
	private volatile int available = 0;

	
	@Override
	public int read() throws IOException
	{
		int result = -1;
		if (closed)
			return -1;
		lock.lock();
		try
		{
			while (!closed && available() == 0)
				notEmpty.await();
			if (!closed)
			{
				result = bufs.getFirst().readByte() & 0xFF;
				available--;
				checkBufs();
			}
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
		finally
		{
			lock.unlock();
		}
		return result;
	}

	private void checkBufs()
	{
		while (!bufs.isEmpty() && bufs.getFirst().readableBytes() == 0)
			bufs.removeFirst();
	}

	@Override
	public void close() throws IOException
	{
		lock.lock();
		closed = true;
		notEmpty.signal();
		lock.unlock();
		super.close();
	}

	/**
	 * Insert bytes to the input stream
	 * 
	 * @param buf
	 *            bytes received from previous upstream handler
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public void write(ChannelBuffer buf) throws IOException
	{
		if (closed)
			throw new IOException("stream closed");
		lock.lock();
		try
		{
			if (bufs.isEmpty() || buf != bufs.getLast())
			{
				bufs.addLast(buf);
				available += buf.readableBytes();
				notEmpty.signal();
			}
		}
		finally
		{
			lock.unlock();
		}
	}

	@Override
	public int available() throws IOException
	{
		if (closed)
			throw new IOException("stream closed");
		return available;
	}

	@Override
	public int read(byte[] b, int off, int len) throws IOException
	{
		int result = -1;
		if (closed)
			return -1;
		lock.lock();
		try
		{
			while (!closed && available() == 0)
			{
				notEmpty.await();
			}
			if (!closed)
			{
				int length = Math.min(len, bufs.getFirst().readableBytes());
				bufs.getFirst().readBytes(b, off, length);
				result = length;
				available -= length;
				checkBufs();
			}
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
		finally
		{
			lock.unlock();
		}
		return result;
	}

	/**
	 * Checks if the stream is closed.
	 * 
	 * @return true, if is closed
	 */
	public boolean isClosed()
	{
		return closed;
	}
}
