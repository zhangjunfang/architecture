package org.springframework.ocean.remoting.hessian.mock;
/**
 * 
 * @author： ocean
 * 创建时间：2015年12月19日
 * mail：zhangjunfang0505@163.com
 * 描述：接口实现
 */     
public class IsayImpl implements Isay {

	public String sayHello(String arg1, String arg2) {
		return "Hello:" + arg1 + arg2;
	}
}