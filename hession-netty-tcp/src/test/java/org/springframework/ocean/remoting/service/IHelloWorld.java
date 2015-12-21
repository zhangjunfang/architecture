
package org.springframework.ocean.remoting.service;

import java.io.File;
import java.util.Map;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public interface IHelloWorld
{

	String hello(String name);

	@SuppressWarnings("rawtypes")
	boolean sendBigData(Map map);

	Map<String, Object> reciveBigData(String id);

	boolean upload(File file);

	String introduce(Person p);

	void sendMsg(String msg);
}
