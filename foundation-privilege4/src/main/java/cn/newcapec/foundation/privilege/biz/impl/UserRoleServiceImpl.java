package cn.newcapec.foundation.privilege.biz.impl;

import cn.newcapec.foundation.privilege.biz.UserRoleService;
import cn.newcapec.foundation.privilege.dao.UserRoleDAO;
import cn.newcapec.foundation.privilege.model.UserRole;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;
@Service("userRoleService")
@Transactional
@SuppressWarnings("all")
public class UserRoleServiceImpl implements UserRoleService {
	@Autowired
	private UserRoleDAO userRoleDAO;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryUserRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		return userRoleDAO.queryUserRoles(params, orderby);
	}

	@Override
	public UserRole get(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeUnused(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveOrUpdate(UserRole arg0) {
		userRoleDAO.saveOrUpdate(arg0);
	}

}
