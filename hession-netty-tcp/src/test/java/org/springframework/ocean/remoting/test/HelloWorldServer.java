
package org.springframework.ocean.remoting.test;

import org.springframework.ocean.remoting.server.AbstractHessianServiceExporter;
import org.springframework.ocean.remoting.server.HessianServiceExporter;
import org.springframework.ocean.remoting.service.HelloWorld;
import org.springframework.ocean.remoting.service.IHelloWorld;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class HelloWorldServer
{

	public static void main(String[] args)
	{
		AbstractHessianServiceExporter serviceExporter = new HessianServiceExporter();
		serviceExporter.setPort(1234);
		serviceExporter.setServiceInterface(IHelloWorld.class);
		serviceExporter.setService(new HelloWorld());
		serviceExporter.startUp();
	}
}
