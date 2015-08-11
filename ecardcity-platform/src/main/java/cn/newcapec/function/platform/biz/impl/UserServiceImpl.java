package cn.newcapec.function.platform.biz.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.biz.UserService;
import cn.newcapec.function.platform.dao.UserDAO;
import cn.newcapec.function.platform.model.User;
import cn.newcapec.function.platform.utils.Constant;

/**
 * 用户业务接口实现类
 * 
 * @author chongfeigao
 * 
 */
@Service("userService")
@Transactional(rollbackFor = Exception.class)
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {

	/* 用户接口类 */
	@Autowired
	private UserDAO userDAO;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public User get(String id) {
		return userDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {
		userDAO.delete(id);
		// userDAO.delUserRole(id);
	}

	@Override
	public void saveOrUpdate(User entity) {
		// 查询索引信息表empid
		String empid = userDAO.sequenceNo(Constant.EMPID,
				entity.getCustomerunitcode());
		// 插入用户表
		entity.setEmpid(empid);
		userDAO.saveOrUpdate(entity);
		// 更新索引信息表字段值加1
		userDAO.updateSeqNo(Constant.EMPID, entity.getCustomerunitcode());
	}

	/**
	 * 查询所有用户 --
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryUsers(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page page = userDAO.queryUsers(params, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public User findUserByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据用户名和密码查询用户 --
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public User findUser(String accountName, String password) {
		return userDAO.findUser(accountName, password);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryUserByRoleId(String id,
			LinkedHashMap<String, String> orderby) {
		Page page = userDAO.queryUserByRoleId(id, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryUserByRoleGroupId(String id,
			LinkedHashMap<String, String> orderby) {
		Page page = userDAO.queryUserByRoleGroupId(id, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}

	/**
	 * 更新用户信息 --
	 */
	@Override
	public void updateUser(User user) {
		userDAO.updateUser(user);

	}

	/**
	 * 删除用户（修改标志位）
	 */
	public void removeUser(String uuid) {
		userDAO.deleteUser(uuid);
	}
}
