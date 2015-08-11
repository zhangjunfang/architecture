package cn.newcapec.foundation.privilege.biz.impl;

import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.foundation.privilege.dao.UserDAO;
import cn.newcapec.foundation.privilege.dao.UserDepartmentDAO;
import cn.newcapec.foundation.privilege.dao.UserRoleDAO;
import cn.newcapec.foundation.privilege.model.DepartmentUser;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.foundation.privilege.model.UserRole;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 用户业务接口实现类
 * @author andy.li
 *
 */
@Service("userService")
@Transactional
@SuppressWarnings("all")
public class UserServiceImpl  implements UserService {

	/*用户接口类*/
	@Autowired
	private UserDAO userDAO;
	/*用户部门接口类*/
	@Autowired
	private UserDepartmentDAO  userDepartmentDAO;
	/*角色用户接口类*/
	@Autowired
	private UserRoleDAO userRoleDAO;
	
	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public User get(String id) {
		return userDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {
		userDAO.delete(id);
	}

	@Override
	public void saveOrUpdate(User entity) {
		userDAO.saveOrUpdate(entity);
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public Page queryUsers(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page page = userDAO.queryUsers(params, orderby);
		if(null!=page){
			return page;
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true,propagation=Propagation.NOT_SUPPORTED)
	public User findUserByName(String name) {
		User user = userDAO.findUserByName(name);
//		if(null!=user){
			return user;
//		}
//		return null;
	}

	@Override
	public void saveCascadeUser(User user, String[] departmentids) {
		userDAO.save(user);
		//添加用户和部门关联关系
		if(null!=departmentids && departmentids.length>0){
			for(int i=0;i<departmentids.length;i++){
				DepartmentUser du = new DepartmentUser();
				du.setUserId(user.getId());
				du.setDepatementId(String.valueOf(departmentids[i]));
				du.setCreateDate(new Date());
				userDepartmentDAO.save(du);
			}
		}
	}

	@Override
	public void deleteCascadeUser(String[] userids) {
		userDAO.deleteUser(userids);
		//同时删去部门员工关联关系
		userDepartmentDAO.deleteDepartmentByUserid(userids);
		//同时删除角色人员关联关系
		userRoleDAO.deleteUserRolesByUserId(userids);
	}
	
	
	@Override
	public void setUsersAuthorize(String roleid, String[] userids) {
		if(StringUtils.isNotBlank(roleid) && userids!=null && userids.length>0){
			for(int i=0;i<userids.length;i++){
				UserRole ur = new UserRole();
				ur.setUserId(userids[i]);
				ur.setRoleId(roleid);
				ur.setCreateDatetime(new Date());
				userRoleDAO.save(ur);
			}
		}
	}

	@Override
	public void setUsersAuthorize(String[] roleids, String userid) {
		if(StringUtils.isNotBlank(userid)){
			/**根据用户id先删除原来的角色信息*/
			userRoleDAO.deleteUserRolesByUserId(new String[]{"'"+userid+"'"});
			if(null!=roleids && roleids.length>0){
				for(int i=0;i<roleids.length;i++){
					UserRole ur = new UserRole();
					ur.setUserId(userid);
					ur.setRoleId(roleids[i]);
					ur.setCreateDatetime(new Date());
					userRoleDAO.save(ur);
				}
			}
		}
	}

	@Override
	public User findUser(String accountName, String password) {
		User user= userDAO.findUser(accountName, password);
		if(null!=user){
			return user;
		}
		return null;
	}


	
}
