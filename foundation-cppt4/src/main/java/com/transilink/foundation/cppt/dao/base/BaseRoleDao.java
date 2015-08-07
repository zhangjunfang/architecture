package com.transilink.foundation.cppt.dao.base;

import java.io.Serializable;

import com.transilink.foundation.cppt.model.Role;
import com.transilink.framework.core.dao.hibernate.HibernateEntityDao;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public abstract class BaseRoleDao extends HibernateEntityDao {

	@Override
	protected Class getReferenceClass() {
		return Role.class;
	}

	public Role get(Serializable key) {
		return (Role) get(getReferenceClass(), key);
	}
}
