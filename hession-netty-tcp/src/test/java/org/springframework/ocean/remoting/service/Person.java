
package org.springframework.ocean.remoting.service;

import java.io.Serializable;

/**
 * 
 * @author： ocean
 * 创建时间：2015年12月21日
 * mail：zhangjunfang0505@163.com
 * 描述：
 */
public class Person implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9018732275371337252L;
	private String name;
	private int age;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the age
	 */
	public int getAge()
	{
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age)
	{
		this.age = age;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Person [name=" + name + ", age=" + age + "]";
	}
}
