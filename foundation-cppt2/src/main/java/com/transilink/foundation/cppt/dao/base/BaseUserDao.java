package com.transilink.foundation.cppt.dao.base;

import java.io.Serializable;

import com.transilink.foundation.cppt.model.User;
import com.transilink.framework.core.dao.hibernate.HibernateEntityDao;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public abstract class BaseUserDao extends HibernateEntityDao {

	@Override
	protected Class getReferenceClass() {
		return User.class;
	}

	public User get(Serializable key) {
		return (User) get(getReferenceClass(), key);
	}
}
