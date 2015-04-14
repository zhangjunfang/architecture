package com.transilink.foundation.cppt.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.transilink.foundation.cppt.dao.base.BaseRoleDao;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@Repository
@SuppressWarnings("all")
public class RoleDao extends BaseRoleDao {

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	public Page findAll(Map<String, Object> paramMap) {
		List<Object> param = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select * from s_role t where 1=1");
		if (null != paramMap) {
			if (paramMap.containsKey("role_name")
					&& StringUtils.isNotBlank(paramMap.get("role_name")
							.toString())) {
				sql.append(" and t.role_name like ?");
				param.add("%" + paramMap.get("role_name") + "%");
			}
		}
		return this.sqlQueryForPage(sql.toString(), param.toArray(), null);
	}
}
