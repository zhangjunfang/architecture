
package org.springframework.ocean.remoting.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class HelloWorld implements IHelloWorld
{

	@Override
	public String hello(String name)
	{
		System.out.println(Thread.currentThread().getName() + ":" + name);
		return "你好" + name;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public boolean sendBigData(Map map)
	{
		System.out.println(map);
		return true;
	}
	@Override
	public Map<String, Object> reciveBigData(String id)
	{
		System.out.println(id);
		Map<String, Object> map = new LinkedHashMap<String, Object>();
		for (int i = 0; i < 100; i++)
		{
			map.put(String.valueOf(i), i);
		}
		return map;
	}
	@Override
	public boolean upload(File file)
	{
		System.out.println("收到文件：" + file.getAbsolutePath());
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			System.out.println("内容：" + reader.readLine());
			reader.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return file != null;
	}
	@Override
	public String introduce(Person p)
	{
		System.out.println("新来的朋友：" + p);
		return "欢迎你：" + p.getName();
	}
	@Override
	public void sendMsg(String msg)
	{
		System.out.println("收到客户端发来的消息：" + msg);
	}
}
