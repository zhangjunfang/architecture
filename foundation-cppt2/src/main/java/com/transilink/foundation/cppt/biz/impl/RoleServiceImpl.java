package com.transilink.foundation.cppt.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.transilink.foundation.cppt.biz.RoleService;
import com.transilink.foundation.cppt.dao.RoleDao;
import com.transilink.foundation.cppt.model.Role;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Service("roleService")
@Transactional
@SuppressWarnings("all")
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Role get(String id) {
		return roleDao.get(id);
	}

	@Override
	public void removeUnused(String id) {
		roleDao.delete(id);
	}

	@Override
	public void saveOrUpdate(Role entity) {
		roleDao.saveOrUpdate(entity);
	}

	@Override
	public Page findList(Map<String, Object> paramMap) {
		Page page = roleDao.findAll(paramMap);
		return page;
	}
}
