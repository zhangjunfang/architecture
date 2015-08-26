package com.atguigu.spring.annotation;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	
	public static void main(String[] args) {
		
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("beans-annotation.xml");
		
		UserAction userAction = ctx.getBean(UserAction.class);
		userAction.execute();
		ctx.close();
	}
	
}
