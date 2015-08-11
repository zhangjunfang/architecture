package cn.newcapce.foundation.privilege;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.model.Department;
import cn.newcapec.framework.core.utils.pagesUtils.Page;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:config/spring-conf/*.xml","classpath*:config/restlet-conf/restletContext.xml","classpath*:config/**/restlet-conf/*.xml"})
@TransactionConfiguration(defaultRollback = true)
public class DepartmentTest {

	@Autowired
	DepartmentService departmentService;
	@Autowired
	RoleService roleService;
	@Autowired
	ResourceService resourceService;
	
	

	/**
	 *  添加部门数据
	 */
	@Test
	public void addDepartment(){
		
		Department department = new  Department();
		department.setParentId("1");
		department.setCreateDatetime(new Date());
		department.setUpdateTime(new Date());
		department.setName("oceanTest");
//		department.setIsParent("false");
		departmentService.saveOrUpdate(department);
	}
	
}
