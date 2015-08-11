package cn.newcapec.function.platform.biz.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.biz.RoleGroupService;
import cn.newcapec.function.platform.dao.RoleGroupDAO;
import cn.newcapec.function.platform.model.RoleGroup;

/**
 * 角色接口业务实现类
 * 
 * @author chongfeigao
 * 
 */

@Service("roleGroupService")
@Transactional
@SuppressWarnings("all")
public class RoleGroupServiceImpl implements RoleGroupService {

	@Autowired
	private RoleGroupDAO roleGroupDAO;

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.newcapec.framework.core.biz.BaseService#get(java.lang.String)
	 */
	@Override
	public RoleGroup get(String arg0) {
		return roleGroupDAO.get(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.framework.core.biz.BaseService#removeUnused(java.lang.String)
	 */
	@Override
	public void removeUnused(String arg0) {
		roleGroupDAO.delete(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.framework.core.biz.BaseService#saveOrUpdate(java.lang.Object)
	 */
	@Override
	public void saveOrUpdate(RoleGroup arg0) {
		// 新增角色组
		roleGroupDAO.saveOrUpdate(arg0);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.function.platform.biz.RoleGroupService#deleteRole(java.lang
	 * .String[])
	 */
	@Override
	public void deleteRole(String[] ids) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.function.platform.biz.RoleGroupService#queryRoleByCustomerCode
	 * (java.util.Map, java.util.LinkedHashMap)
	 */
	@Override
	public Page queryRoleGroupByCustomerCode(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		return roleGroupDAO.queryRoleGroupByCustomerCode(params, orderby);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * cn.newcapec.function.platform.biz.RoleGroupService#queryRoleGroups(net
	 * .sf.json.JSONObject, java.lang.Object)
	 */
	@Override
	public List<Map<String, Object>> queryRoleGroups(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		return roleGroupDAO.queryRoleGroups(params, orderby);
	}
}
