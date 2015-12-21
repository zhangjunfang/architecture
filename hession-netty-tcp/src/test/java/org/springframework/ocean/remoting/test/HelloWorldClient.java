package org.springframework.ocean.remoting.test;

import org.springframework.ocean.remoting.client.HessianProxyFactory;
import org.springframework.ocean.remoting.service.IHelloWorld;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class HelloWorldClient {

    public static void main(String[] args) {
        HessianProxyFactory<IHelloWorld> hessianProxyFactory = new HessianProxyFactory<IHelloWorld>(IHelloWorld.class);
        IHelloWorld s = hessianProxyFactory.create("localhost", 1234, null);
        System.out.println(s.hello("访客" + System.currentTimeMillis()));
        System.out.println(s.reciveBigData(System.currentTimeMillis()+""));
    }
}
