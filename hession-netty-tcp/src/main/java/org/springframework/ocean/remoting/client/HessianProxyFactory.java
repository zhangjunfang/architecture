
package org.springframework.ocean.remoting.client;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.ocean.remoting.service.IHelloWorld;

import com.caucho.hessian.io.HessianRemoteObject;

/**
 * 
 * @author： ocean
 * @创建时间：2015年12月21日
 * @mail：zhangjunfang0505@163.com
 * @描述：Hessian代理工厂
 */
@SuppressWarnings("unchecked")
public class HessianProxyFactory<T>
{

	private final Class<T> interfaceClazz;

	public HessianProxyFactory(Class<T> interfaceClass)
	{
		this.interfaceClazz = interfaceClass;
	}

	/**
	 * 创建Hessian远程代理
	 * 
	 * @param address
	 * @param port
	 * @param globalHeaders
	 * @return
	 */
	public T create(String address, int port, Map<Object, Object> globalHeaders)
	{
		HessianProxy handler = new HessianProxy(address, port, globalHeaders);
		return (T) Proxy.newProxyInstance(new ClassLoader(interfaceClazz.getClassLoader())
		{

			@Override
			public Class<?> loadClass(String name) throws ClassNotFoundException
			{
				try
				{
					return super.loadClass(name);
				}
				catch (ClassNotFoundException e)
				{
					return HessianRemoteObject.class.getClassLoader().loadClass(name);
				}
			}
		}, new Class[] { interfaceClazz, HessianRemoteObject.class }, handler);
	}

	/**
	 * 采用线程池编写的demo
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		final HessianProxyFactory<IHelloWorld> factory = new HessianProxyFactory<IHelloWorld>(IHelloWorld.class);
		int count = 4;
		final AtomicInteger sucessnum = new AtomicInteger(0);
		final AtomicInteger executedNum = new AtomicInteger(0);
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(100, 100, 10, TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>())
		{

			/**
			 * 统计任务执行之后执行成功数、执行总数
			 * 
			 * @param r
			 *            运行的任务
			 * @param t
			 *            不为空执行有一场，为空执行成功
			 */
			@Override
			protected void afterExecute(Runnable r, Throwable t)
			{
				executedNum.incrementAndGet();
				if (t == null)
					sucessnum.incrementAndGet();
			}
		};
//		long sTime = System.currentTimeMillis();
		for (int j = 0; j < count; j++)
		{
			final int i = j;
			threadPool.execute(new Runnable()
			{

				@Override
				public void run()
				{
					IHelloWorld cs = factory.create("localhost", 8080, null);
					// System.out.println(cs.hello("君枫"));
					/**
					 * 2
					 * 
					 * <pre>
					 * Map&lt;String, Object&gt; map = new LinkedHashMap&lt;String, Object&gt;();
					 * for (int i = 0; i &lt; 100; i++)
					 * {
					 * 	map.put(String.valueOf(i), i);
					 * }
					 * System.out.println(cs.sendBigData(map));
					 */
					// System.out.println(cs.reciveBigData(String.valueOf(Math.random())));
					// System.out.println(cs.upload(new
					// File("d://1.txt")));//2台机子有问题，同台机子无问题
					/*
					 * Person p = new Person(); p.setName("新人" + Math.random());
					 * p.setAge(new Random().nextInt(100));
					 * System.out.println(cs.introduce(p));
					 */
					cs.sendMsg("这是第" + (i + 1) + "个消息");
				}
			});
		}
		while (executedNum.get() != count)
		{
			try
			{
				Thread.sleep(10);
			}
			catch (InterruptedException e)
			{
			}
		}
		//System.out.println("成功执行数：" + sucessnum.get() + ";耗时：" + (System.currentTimeMillis() - sTime) + "毫秒");
		threadPool.shutdown();
	}
}
