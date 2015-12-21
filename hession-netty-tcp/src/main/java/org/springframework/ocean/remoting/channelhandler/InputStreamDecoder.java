
package org.springframework.ocean.remoting.channelhandler;

import static org.jboss.netty.channel.Channels.fireChannelDisconnected;
import static org.jboss.netty.channel.Channels.fireMessageReceived;

import java.net.SocketAddress;
import java.util.concurrent.Executor;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelUpstreamHandler;
import org.springframework.ocean.remoting.io.InputStreamBuffer;


/**
 * Encodes bytes read by a {@link Channel} into an {@link InputStream}. <br>
 * Once created the InputStream is passed as a message to the next handler within a
 * separate thread. From there on, no further messages are passed through the pipeline. <br>
 * A typical setup for a serialization protocol in a TCP/IP socket would be:
 * 
 * <pre>
 * {@link ChannelPipeline} pipeline = ...;
 * 
 * // Encoder
 * pipeline.addLast(&quot;outputStream&quot;, new {@link handler.io.OutputStream}());
 * pipeline.addLast(&quot;outputHandler&quot;, new MyOutputHandler());
 * 
 * // Decoder
 * pipeline.addLast(&quot;inputStream&quot;, new {@link handler.io.InputStream}());
 * pipeline.addLast(&quot;inputHandler&quot;, new MyInputHandler());
 * </pre>
 * 
 * and then, within the handler you can use a {@link java.io.InputStream} or
 * {@link java.io.OutputStream} instead of a {@link ChannelBuffer} as a message: <br>
 * Writing to OutputStream:
 * 
 * <pre>
 * 
 * 
 * // synchronized for multithreaded environment to avoid messages mixing
 * synchronized public void writeRequested(ChannelHandlerContext ctx, MessageEvent e) throws Exception
 * {
 * 	byte[] message = (byte[]) e.getMessage();
 * 	OutputStream out = OutputStreamEncoder.getOutputStream(ctx);
 * 	out.write(message);
 * 	// if this is the last chunk of bytes we should flush the output
 * 	out.flush();
 * 	// netty seems to require this, so that the boss thread may read input from the
 * 	// channel
 * 	Thread.yield();
 * }
 * 
 * </pre>
 * 
 * <br>
 * Reading from InputStream:
 * 
 * <pre>
 * 
 * 
 * void messageReceived(ChannelHandlerContext ctx, MessageEvent e)
 * {
 * 	// message received is called only once to deliver the input stream
 * 	// it is called in a separate thread and not in the netty worker thread.
 * 	// incoming bytes are consumed in this method.
 * 	// the stream is closed once the channel is disconnected
 * 	InputStream in = (InputStream) evt.getMessage();
 * 	while (ctx.getChannel().isConnected())
 * 	{
 * 		// parse the incoming stream and forward the result to the next handler
 * 		Channels.fireMessageReceived(ctx, parseReply(in));
 * 	}
 * }
 * </pre>
 */
/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：
 */
public class InputStreamDecoder extends SimpleChannelUpstreamHandler
{

	/** Thread pool for getting a thread for calling the next handler */
	private Executor executor;

	/**
	 * Instantiates a new input stream decoder.
	 * 
	 * @param executor
	 *            the thread pool
	 */
	public InputStreamDecoder(Executor executor)
	{
		this.executor = executor;
	}

	public InputStreamDecoder()
	{
	}

	
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception
	{
		InputStreamBuffer in = (InputStreamBuffer) ctx.getAttachment();
		boolean newIn = false;
		if (in == null || in.isClosed())
		{
			in = new InputStreamBuffer();
			ctx.setAttachment(in);
			newIn = true;
		}
		((InputStreamBuffer) in).write((ChannelBuffer) e.getMessage());
		if (newIn)
		{
			// fireMessageReceived(ctx, in, e.getRemoteAddress());
			/**
			 * <pre>
			 * final ChannelHandlerContext c = ctx;
			 * 			final Object message = in;
			 * 			final SocketAddress addr = e.getRemoteAddress();
			 * 			_executor.execute(new Runnable()
			 * 			{
			 * 
			 * 				public void run()
			 * 				{
			 * 					fireMessageReceived(c, message, addr);
			 * 				}
			 * });
			 */
			final ChannelHandlerContext c = ctx;
			final Object message = in;
			final SocketAddress addr = e.getRemoteAddress();
			executor.execute(new Runnable()
			{

				@Override
				public void run()
				{
					fireMessageReceived(c, message, addr);
				}
			});
		}
	}

	
	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception
	{
		InputStreamBuffer in = (InputStreamBuffer) ctx.getAttachment();
		if (in != null)
			in.close();
		fireChannelDisconnected(ctx);
	}
}
