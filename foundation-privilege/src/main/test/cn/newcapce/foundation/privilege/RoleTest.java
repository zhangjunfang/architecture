package cn.newcapce.foundation.privilege;



import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.foundation.privilege.model.Role;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.framework.core.exception.RedisAccessException;
import cn.newcapec.framework.core.utils.pagesUtils.Page;




@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-conf/*.xml"})
@TransactionConfiguration(defaultRollback = true)
public class RoleTest {
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;

	@Autowired
	DepartmentService resourceService;
	@Autowired
	MenuService menuService ;
	@Autowired
	ResourceService resourceServices;
	
	
	@Test
	public void menus(){
		Map params= new HashMap<String, Object>();
		Page page = menuService.queryMenu(params, null);
		System.out.println(page.getTotal());
	}
	
	
	@Test
	public void function(){
		Map params= new HashMap<String, Object>();
		Page page = resourceServices.queryResource(params, null);
		System.out.println(page.getTotal());
	}
	
	/**
	 * 添加角色
	 */
	@Test
	public void addrole(){
		for(int i=0;i<27;i++){
			Role role = new Role();
			role.setRoleName("普通员工"+i);
			role.setMemo("超级管理员");
			role.setCreateDatetime(new Date());
			roleService.saveOrUpdate(role);
			
		}
	}
	
	/**
	 * 添加人员
	 */
	@Test
	public void addUser(){
	String json = "{'openDate':'2013-09-24','accountName':'fdfdfd88'}";//{"data":{"certificateCode":"fd","birthday":"2013-09-24","sex":"1","certificateType":"身份证","nation":"fd","userType":"fdfd","accountName":"fdsf","email":"fd","userName":"fd","departId":"软件技术研究部","invaildDate":"2013-09-18","openDate":"2013-09-10","mobile":""}}';
	JSONObject js = JSONObject.fromObject(json);
//	User user  = JSONTools.JSONToBean(js, User.class);
	JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] { "yyyy-MM-dd","yyyy-MM-dd HH:mm:ss" })); 
//	User user  = JSONTools.JsonToBean(User.class, js);
	User user = (User) JSONObject.toBean(js, User.class);
	System.out.println(user.getOpenDate());
	System.out.println(user.getAccountName());
			userService.saveOrUpdate(user);
	}
	
	
	
	/**
	 *  查询角色
	 */
	@Test
	public void updaterole(){
		String id ="402881cd412f88ac01412f88b4d70007";
		Role role =  roleService.get(id);
		System.out.println(role.getMemo());
	}
}
