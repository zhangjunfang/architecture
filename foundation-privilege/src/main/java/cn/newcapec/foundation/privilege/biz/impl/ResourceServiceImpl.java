package cn.newcapec.foundation.privilege.biz.impl;

import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.dao.ResourceDAO;
import cn.newcapec.foundation.privilege.dao.RoleDAO;
import cn.newcapec.foundation.privilege.model.Resource;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 资源业务接口实现类
 * 
 * @author andy.li
 * 
 */
@Service("resourceService")
@Transactional
@SuppressWarnings("all")
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceDAO resourceDAO;
	@Autowired
	private RoleDAO roleDAO;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Resource get(String id) {
		return resourceDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {
		resourceDAO.delete(id);
	}

	@Override
	public void saveOrUpdate(Resource entity) {
		resourceDAO.saveOrUpdate(entity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryResource(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page page = resourceDAO.queryResource(params, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Resource queryResourceById(String id) {
		Resource resource = resourceDAO.queryResourceById(id);
		return resource;
	}

	

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Map<String, Object>> queryResourcesByRoleId(String role) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = resourceDAO.queryResourcesByRoleId(role);
		return list;
	}

	@Override
	public void deleteResource(String[] ids) {
		if (null != ids && ids.length > 0) {
			resourceDAO.deleteResource(ids);
		}
	}

	/**
	 * 获取用户的所有资源信息
	 * 
	 * @param userid
	 * @return
	 */
	public List queryResorucesByUserid(String userid) {
		// 获取某员工所在部门下的所有角色信息
		List departmentroleids = roleDAO.queryRolesDepartmentByUserid(userid);
		List roleids = roleDAO.queryRolesByUserid(userid);
		Set set = new HashSet();
		if (null != departmentroleids && departmentroleids.size() > 0) {
			set.addAll(departmentroleids);
		}
		if (null != roleids && roleids.size() > 0) {
			set.addAll(roleids);
		}

		if (!set.isEmpty()) {
			List list = new ArrayList();
			for (int i = 0; i < set.size(); i++) {
				Iterator it = set.iterator();
				while (it.hasNext()) {
					Map map = (Map) it.next();
					for (Iterator<String> keys = map.keySet().iterator(); keys
							.hasNext();) {
						String key = (String) keys.next();
						String value = map.get(key).toString();
						list.add("'" + value + "'");

					}

				}

			}
			List resources = resourceDAO
					.queryResorucesByRoleids(list.toArray());
			return resources;

		}
		return null;

	}

	@Override
	public void saves(List<Resource> resources) {
		resourceDAO.saveOrUpdateAll(resources);
	}
}
