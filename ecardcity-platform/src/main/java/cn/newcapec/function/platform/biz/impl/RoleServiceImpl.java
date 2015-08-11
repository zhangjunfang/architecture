package cn.newcapec.function.platform.biz.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.biz.RoleService;
import cn.newcapec.function.platform.dao.RoleDAO;
import cn.newcapec.function.platform.dao.RoleGroupDAO;
import cn.newcapec.function.platform.dao.RoleMenuDAO;
import cn.newcapec.function.platform.model.Role;

/**
 * 角色接口业务实现类
 * 
 * @author chongfeigao
 * 
 */

@Service("roleService")
@Transactional
@SuppressWarnings("all")
public class RoleServiceImpl implements RoleService {

	/* 角色接口类 */
	@Autowired
	private RoleDAO roleDAO;
	@Autowired
	private RoleMenuDAO roleMenuDAO;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Role get(String id) {
		return roleDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {
		roleDAO.delete(id);
	}

	@Override
	public void saveOrUpdate(Role entity) {
		 roleDAO.saveOrUpdate(entity);
	}

	/**
	 * 查询所有角色 返回list为包含人数统计 --
	 */
	@Override
	public List<Map<String, Object>> queryRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		return roleDAO.queryRoles(params, orderby);
	}

	@Override
	public Role findRoleByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteRole(String[] ids) {
		roleDAO.deleteRole(ids);
	}

	/**
	 * 根据角色组id 查询 角色 --
	 */
	@Override
	public List<Map<String, Object>> queryByRoleGroupidId(String rolegroupid) {
		return roleDAO.queryByRoleGroupidId(rolegroupid);
	}

	/**
	 * 查询角色 根据客户code
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryRoleByCustomerCode(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page page = roleDAO.queryRoleByCustomerCode(params, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}
	/**
	 * @Description: 新增角色 及其关联的菜单权限
	 * @param @param role
	 * @param @param jsonObject   
	 * @return void    
	 * @throws
	 */ 
	@Override
	public void addRole(Role entity, Map<String, Object> params){
		// 新增角色
		roleDAO.saveOrUpdate(entity);
		Role role = roleDAO.merge(entity);
		String role_uid = role.getId();
		params.put("roleid", role_uid);
		// 新增角色对应权限菜单 
		roleMenuDAO.addRoleMenu(params);
	}
	/**
	 * @Description: 更新角色 及其关联的菜单权限
	 * @param @param role
	 * @param @param jsonObject   
	 * @return void    
	 * @throws
	 */ 
	@Override
	public void editRole(Role entity, Map<String, Object> params){
		// 更新角色
		roleDAO.saveOrUpdate(entity);
		Role role = roleDAO.merge(entity);
		String role_uid = role.getId();
		params.put("roleid", role_uid);
		// 先删除
		roleMenuDAO.delRoleMenu(params);
		// 新增角色对应权限菜单 
		roleMenuDAO.addRoleMenu(params);
	}

	/* (non-Javadoc) 根据roleid 查询 是否存在 角色菜单关联 
	 * @see cn.newcapec.function.platform.biz.RoleService#queryMenuByRoleId(java.lang.String, java.lang.Object)
	 */
	@Override
	public Page queryMenuByRoleId(String uuid, LinkedHashMap<String, String> orderby) {
		Page page = roleMenuDAO.queryMenuByRoleId(uuid, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}
}
