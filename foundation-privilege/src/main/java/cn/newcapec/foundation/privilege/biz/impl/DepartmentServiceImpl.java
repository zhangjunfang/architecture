package cn.newcapec.foundation.privilege.biz.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.dao.DepartmentDAO;
import cn.newcapec.foundation.privilege.model.Department;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.stringUtils.StringUtil;

@SuppressWarnings({ "all" })
@Service("departmentService")
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

	@Autowired
	private DepartmentDAO departmentDAO;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Department get(String id) {
		return (Department)departmentDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {
		departmentDAO.delete(id);
	}

	@Override
	public void saveOrUpdate(Department entity) {
		departmentDAO.saveOrUpdate(entity);

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryDepartments(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page<Map<String, Object>> page = departmentDAO.queryDepartments(params, orderby);
		if (null != page) {
			return page;
		}else {
			return null;
		}
		
	}

	@Override
	public void deleteDepartmentsbyIds(String[] ids) {
		if (null != ids && ids.length > 0) {
		
			for (int i = 0; i < ids.length; i++) {
				Map<String, Object>params=new HashMap<String, Object>();
				params.put("parent_id", ids[i]);
				Page<HashMap<String, Object>>page=departmentDAO.querySubDepartments(params, null);
				if(page.getTotal()>0){
					throw new BaseException(departmentDAO.get(ids[i]).getName()+"下有二级部门，请先删除二级部门！");
				}
				departmentDAO.delete(ids[i]);
			}
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page findDepartmentsbyParams(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page<Map<String, Object>> page = departmentDAO.queryDepartments(params, null);
		if (null != page) {
			return page;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Department findDepartmentByName(String name) {
		Department department = departmentDAO.findDepartmentByName(name);
		if (null != department) {
			return department;
		}
		return null;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page querySubDepartments(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		
		return departmentDAO.querySubDepartments(params, orderby);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List queryDepartmentByRoleId(String roleid) {
		List list = new ArrayList();
		list=departmentDAO.queryDepartmentByRoleId(roleid);
		return list;
	}
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List queryDepartmentByUserId(String userid) {
		List list = new ArrayList();
		list=departmentDAO.queryDepartmentByUserId(userid);
		return list;
	}

	@Override
	public Page<Map<String, Object>> querySubDepartmentData(Set<String> set) {
		
		if(null==set||set.isEmpty()){
			
			return null;
		}
		return departmentDAO.querySubDepartmentData(set);
	}

	@Override
	public Set<String> findSubIds(String id) {
		
		if(StringUtil.isEmpty(id)){
			return null;
		}
		return departmentDAO.findSubIds(id);
	}
	@Override
	public boolean findSameLevelDepartmentExistingName(Department department) {
		return departmentDAO.findSameLevelDepartmentExistingName(department);
	}

	@Override
	public void saves(List<Department> entitys) {
		departmentDAO.saveOrUpdateAll(entitys);
	}
}
