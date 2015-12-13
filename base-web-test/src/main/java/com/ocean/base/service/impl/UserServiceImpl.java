package com.ocean.base.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ocean.base.dao.IUserDao;
import com.ocean.base.pojo.User;
import com.ocean.base.service.IUserService;
/**
 * 描述：
 * 
 * @author ocean
 * 2015年12月12日
 * email：zhangjunfang0505@163.com
 */
@Service("userService")
public class UserServiceImpl implements IUserService {
	@Resource
	private IUserDao userDao;
	@Override
	public User getUserById(int userId) {
		return this.userDao.selectByPrimaryKey(userId);
	}

}
