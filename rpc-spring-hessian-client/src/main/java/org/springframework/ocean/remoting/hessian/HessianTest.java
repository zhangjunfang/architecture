/**
 * 2015年12月19日
 */
package org.springframework.ocean.remoting.hessian;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author： ocean 创建时间：2015年12月19日 mail：zhangjunfang0505@163.com 描述：
 */
public class HessianTest {

	public static void main(String[] args) {

		ClassPathXmlApplicationContext contex = new ClassPathXmlApplicationContext(
				"remoting-servlet.xml");
		// 获得客户端的Hessian代理工厂bean
		Isay i = (Isay) contex.getBean("clientSpring");
		System.out.println(i.sayHello("zhangboyu", "-----waiting"));
		contex.close();
	}

}
