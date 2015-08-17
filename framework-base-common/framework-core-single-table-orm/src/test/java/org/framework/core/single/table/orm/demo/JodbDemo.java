/**
 * Copyright (c) 2011-2015, @author ocean(zhangjufang0505@163.com).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.framework.core.single.table.orm.demo;

import java.util.Map;

import org.framework.core.single.table.orm.core.Jodb;
import org.framework.core.single.table.orm.core.JodbConfig;
import org.framework.core.single.table.orm.core.ORM;
import org.framework.core.single.table.orm.core.Record;
import org.framework.core.single.table.orm.dsImpl.Db;
import org.framework.core.single.table.orm.util.DuplexMap;

/**
 * This is a simple demo to use some great features of Jodb.
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class JodbDemo {

	public static Db ds = new Db("db.properties");
	//public static C3p0 ds = new C3p0("c3p0.properties");
	
	public static void main(String[] args) {
		test_Jodb_Record();
//		test_Jodb_Model();
//		test_Jodb_Bean();
//		test_Jodb_tx();
//		test_ORM();
//		test_DuplexMap();
	}
	
	public static void test_Jodb_Record() {
		System.out.println("---------------------------test_Jodb_Record-----------------------------");
		
		Jodb jodb = new Jodb(ds).open();
		jodb.getSession().setReportSql(true);
		
		Record user = new Record("user").set("id", 10).set("username", "mobangjack1").set("password", "mobangjacklove331");
		System.out.println("insert user '"+user+"':"+jodb.insert(user));
		
		System.out.println("select user '"+user+"':"+jodb.selectFirst(user));
		
		System.out.println("validate user '"+user+"':"+jodb.validate(user));
		
		System.out.println("count user '"+user+"':"+jodb.count(user));
		
		Record user_replacement = new Record("user").set("id", 10).set("username", "mobangjack").set("password", "mobangjacklove33");
		System.out.println("update user '"+user+"' using user '"+user_replacement+"':"+jodb.update(user,user_replacement));
		
		System.out.println("select user '"+user_replacement+"':"+jodb.selectFirst(user_replacement));
		
		System.out.println("validate user '"+user_replacement+"':"+jodb.validate(user_replacement));
		
		System.out.println("count user '"+user_replacement+"':"+jodb.count(user_replacement));
		
		System.out.println("delete user '"+user_replacement+"':"+jodb.delete(user_replacement));
		
		jodb.close();
	}
	
	public static void test_Jodb_Model() {
		System.out.println("---------------------------test_Jodb_Model-----------------------------");
		
		Jodb jodb = new Jodb(ds).open();
		jodb.getSession().setReportSql(true);
		
		/*User继承自Model类，Model类首先从TableMapping里配置表映射，如果无TableMapping或其值为空，则使用类名驼峰转下划线作为表映射*/
		User user = User.dao.set("id", 10).set("username", "mobangjack1").set("password", "mobangjacklove331");
		System.out.println("insert user '"+user+"':"+jodb.insert(user));
		
		System.out.println("select user '"+user+"':"+jodb.selectFirst(user));
		
		System.out.println("validate user '"+user+"':"+jodb.validate(user));
		
		System.out.println("count user '"+user+"':"+jodb.count(user));
		
		User user_replacement = new User().set("id", 10).set("username", "mobangjack").set("password", "mobangjacklove33");
		System.out.println("update user '"+user+"' using user '"+user_replacement+"':"+jodb.update(user,user_replacement));
		
		System.out.println("select user '"+user_replacement+"':"+jodb.selectFirst(user_replacement));
		
		System.out.println("validate user '"+user_replacement+"':"+jodb.validate(user_replacement));
		
		System.out.println("count user '"+user_replacement+"':"+jodb.count(user_replacement));
		
		System.out.println("delete user '"+user_replacement+"':"+jodb.delete(user_replacement));
		
		jodb.close();
	}
	
	public static void test_Jodb_Bean() {
		System.out.println("---------------------------test_Jodb_Bean-----------------------------");
		
		Jodb jodb = new Jodb(ds).open();
		jodb.getSession().setReportSql(true);
		
		UserBean user = new UserBean();
		user.setId(10);
		//user.setUsername("username");
		//user.setPassword("password");
		//System.out.println("insert user '"+user+"':"+jodb.insert(user));
		
		System.out.println("select user '"+user+"':"+jodb.selectFirst(user));
		
		System.out.println("validate user '"+user+"':"+jodb.validate(user));
		
		System.out.println("count user '"+user+"':"+jodb.count(user));
		
		UserBean user_replacement = new UserBean();
		user.setId(10);
		user.setUsername("username2");
		user.setPassword("password2");
		System.out.println("update user '"+user+"' using user '"+user_replacement+"':"+jodb.update(user,user_replacement));
		
		System.out.println("select user '"+user_replacement+"':"+jodb.selectFirst(user_replacement));
		
		System.out.println("validate user '"+user_replacement+"':"+jodb.validate(user_replacement));
		
		System.out.println("count user '"+user_replacement+"':"+jodb.count(user_replacement));
		
		System.out.println("delete user '"+user_replacement+"':"+jodb.delete(user_replacement));
		
		jodb.close();
	}
	
	public static void test_Jodb_tx() {
		System.out.println("---------------------------test_Jodb_tx-----------------------------");
		
		Jodb jodb = new Jodb(ds).open();
		jodb.getSession().setReportSql(true);
		jodb.getSession().setAutoCommit(false);
		
		/*User继承自Model类，Model类首先从TableMapping里配置表映射，如果无TableMapping或其值为空，则使用类名驼峰转下划线作为表映射*/
		User user = User.dao.set("id", 10).set("username", "mobangjack1").set("password", "mobangjacklove331");
		boolean flag = true;
		/*故意插入重复记录*/
		System.out.println("insert user '"+user+"':"+(flag&=jodb.insert(user)));
		System.out.println("insert user '"+user+"':"+(flag&=jodb.insert(user)));
		System.out.println("flag="+flag);
				
		System.out.println("select user '"+user+"':"+jodb.selectFirst(user));
		
		System.out.println("validate user '"+user+"':"+jodb.validate(user));
		
		System.out.println("count user '"+user+"':"+jodb.count(user));
		
		User user_replacement = new User().set("id", 10).set("username", "mobangjack").set("password", "mobangjacklove33");
		System.out.println("update user '"+user+"' using user '"+user_replacement+"':"+(flag&=jodb.update(user,user_replacement)));
		
		System.out.println("select user '"+user_replacement+"':"+jodb.selectFirst(user_replacement));
		
		System.out.println("validate user '"+user_replacement+"':"+jodb.validate(user_replacement));
		
		System.out.println("count user '"+user_replacement+"':"+jodb.count(user_replacement));
		
		System.out.println("delete user '"+user_replacement+"':"+(flag&=jodb.delete(user_replacement)));
		
		if(!flag){
			jodb.getSession().rollBack();
			System.out.println("tx rolled back!");
		}
		
		jodb.close();
	}
	
	public static void test_ORM() {
		System.out.println("---------------------------test_ORM-----------------------------");
		
		UserBean user = new UserBean();
		user.setId(10);
		user.setUsername("username");
		user.setPassword("password");
		
		System.out.println(user);
		
		System.out.println(JodbConfig.getColumnFieldDuplexMap(UserBean.class));
		
		Map<String, Object> map = ORM.toMap(user, JodbConfig.getColumnFieldDuplexMap(UserBean.class).invert());
		System.out.println(map);
		
		System.out.println(ORM.toBean(UserBean.class, map, JodbConfig.getColumnFieldDuplexMap(UserBean.class)));
	}
	
	public static void test_DuplexMap() {
		System.out.println("---------------------------test_DuplexMap-----------------------------");
		
		DuplexMap<String,String> map = new DuplexMap<String, String>();
		map.put("k1", "v1");
		map.put("k2", "v2");
		map.put("k3", "v3");
		System.out.println(map);
		System.out.println(map.invert());
	}
}
