package com.transilink.foundation.cppt.dao;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import com.transilink.foundation.cppt.dao.base.BaseMenuDao;
import com.transilink.framework.core.utils.pagesUtils.Page;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@Repository
@SuppressWarnings("all")
public class MenuDao extends BaseMenuDao {

	/**
	 *
	 * @param paramMap
	 * @return
	 */
	public Page findAll(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		List<Object> param = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select t.* from s_menu t where 1=1");
		if (null != paramMap) {
			if (paramMap.containsKey("menu_name")
					&& StringUtils.isNotBlank(paramMap.get("menu_name")
							.toString())) {
				sql.append(" and t.menu_name like ?");
				param.add("%" + paramMap.get("menu_name") + "%");
			}
			if (paramMap.containsKey("pid")
					&& StringUtils.isNotBlank(paramMap.get("pid").toString())) {
				sql.append(" and t.pid = ?");
				param.add(paramMap.get("pid"));
			}
		}
		return this.sqlQueryForPage(sql.toString(), param.toArray(), orderby);
	}

	public List findAllChildren(Map<String, Object> paramMap,
			LinkedHashMap<String, String> orderby) {
		List<Object> param = new ArrayList<Object>();
		StringBuilder sql = new StringBuilder(
				"select t.* from s_menu t where 1=1");
		if (null != paramMap) {
			if (paramMap.containsKey("menu_name")
					&& StringUtils.isNotBlank(paramMap.get("menu_name")
							.toString())) {
				sql.append(" and t.menu_name like ?");
				param.add("%" + paramMap.get("menu_name") + "%");
			}
			if (paramMap.containsKey("pid")
					&& StringUtils.isNotBlank(paramMap.get("pid").toString())) {
				sql.append(" and t.path like CONCAT((select path from s_menu where id=?), ',%')");
				param.add(paramMap.get("pid"));
			}
		}
		return this.sqlQueryForList(sql.toString(), param.toArray(), orderby);
	}
}
