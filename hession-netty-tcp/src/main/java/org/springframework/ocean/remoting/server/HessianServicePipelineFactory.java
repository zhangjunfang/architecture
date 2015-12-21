
package org.springframework.ocean.remoting.server;

import java.util.concurrent.Executor;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.springframework.ocean.remoting.channelhandler.HessianServiceInovker;
import org.springframework.ocean.remoting.channelhandler.InputStreamDecoder;
import org.springframework.ocean.remoting.channelhandler.OutputStreamEncoder;

import com.caucho.hessian.server.HessianSkeleton;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：
 */
public class HessianServicePipelineFactory implements ChannelPipelineFactory
{

	private final HessianSkeleton hessianSkeleton;
	private final Executor executor;

	/**
	 * @param hessianSkeleton
	 */
	public HessianServicePipelineFactory(HessianSkeleton hessianSkeleton, Executor executor)
	{
		this.hessianSkeleton = hessianSkeleton;
		this.executor = executor;
	}

	
	@Override
	public ChannelPipeline getPipeline() throws Exception
	{
		ChannelPipeline pipeline = Channels.pipeline();
		pipeline.addLast("InputStreamDecoder", new InputStreamDecoder(executor));// 将Channel转化为Stream使用
		pipeline.addLast("OutputStreamEncoder", new OutputStreamEncoder());// 将Channel转化为Stream使用
		// pipeline.addLast("", new ExecutionHandler(new
		// OrderedMemoryAwareThreadPoolExecutor(16, 1048576, 1048576)));
		pipeline.addLast("invokerhandler", new HessianServiceInovker());
		// 将hessianSkeleton注入到HessianServiceInovker中执行
		pipeline.getContext(HessianServiceInovker.class).setAttachment(hessianSkeleton);
		return pipeline;
	}
}
