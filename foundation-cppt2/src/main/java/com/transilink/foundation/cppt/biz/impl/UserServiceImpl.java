package com.transilink.foundation.cppt.biz.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.transilink.foundation.cppt.biz.UserService;
import com.transilink.foundation.cppt.dao.UserDao;
import com.transilink.foundation.cppt.model.User;
import com.transilink.framework.core.rest.Msg;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Service("userService")
@Transactional
@SuppressWarnings("all")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public User get(String id) {
		return userDao.get(id);
	}

	@Override
	public void removeUnused(String id) {
		userDao.delete(id);
	}

	@Override
	public void saveOrUpdate(User entity) {
		userDao.saveOrUpdate(entity);
	}

	@Override
	public Msg loginValidate(String userName, String password) {
		Msg msg = new Msg();
		User user = userDao.findByName(userName);
		if (null == user) {
			msg.setMsg("不存在该用户");
			return msg;
		}
		if (!user.getPassword().equals(password)) {
			msg.setMsg("用户名或密码输入错误");
			return msg;
		}
		msg.setSuccess(true);
		return msg;
	}

	@Override
	public Page findList(Map<String, Object> paramMap) {
		Page page = userDao.findAll(paramMap);
		return page;
	}
}
