package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.Role;
import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色业务接口
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public interface RoleService   extends BaseService<Role> {
	
	/***
	 * 保存或更新
	 * 
	 * @param entity
	 */
	public void saveOrUpdate(Role entity);

	/**
	 * 获取角色列表
	 * @param params
	 * @param orderby
	 * @return
	 */

	public Page queryRoles(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
	
	
	/**
	 * 获取角色信息
	 * @param name
	 * @return
	 */
	public Role findRoleByName(String name);
	
	/**
	 * 删去角色
	 * @param ids id集合数组
	 */
	public void deleteRole(String[] ids);
	
	/**
	 * 赋值资源给权限
	 * @param role
	 * @param resoruceids
	 */
	public void setRoleResource(String role,String[] resoruceids);
	
	/**
	 * 赋值角色给部门（一些）
	 * @param roleid
	 * @param departmentids
	 */
	public void setRoleDepartments(String roleid,String[] departmentids);
	
	/**
	 * 删去资源
	 * @param roleid
	 */
	public void deleteResourceByRoleId(String roleid);
	
	/**

	 * 获取部门下的角色信息
	 * @param roleid
	 * @return
	 */
	public List queryRoleByDepartmentId(String roleid);
	
	/**
	 * 获取用户角色下的资源
	 * @param usreid
	 * @return
	 */
	public List<Map<String, Object>> queryPrivilesByUserId(String usreid);
	
	/**
	 * 系统初始化
	 */
	public void SystemInit();
	
	
	
}
