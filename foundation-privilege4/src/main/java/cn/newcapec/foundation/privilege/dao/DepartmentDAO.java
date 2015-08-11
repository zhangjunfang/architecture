package cn.newcapec.foundation.privilege.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import cn.newcapec.foundation.privilege.dao.base.BaseDepartmentDAO;
import cn.newcapec.foundation.privilege.model.Department;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.stringUtils.StringUtil;

/**
 * 部门接口类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
@Repository("departmentDAO")
public class DepartmentDAO extends BaseDepartmentDAO {

	
	
//	/**
//	 * 获取部门列表
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//
//	public Page queryDepartments(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
//	
//	
//	/**
//	 * 获取部门列表   通过 name 和 status 
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//
//	public Page findDepartmentsbyParams(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
//
//	/**
//	 * 获取部门列表   通过 name
//	 * 
//	 */
//	public Department findDepartmentByName(String name);
//	
//	/**
//	 * 获取某一个部门下，所有列表数据
//	 * @param params
//	 * @param orderby
//	 * @return 
//	 * */
//	public Page querySubDepartments(Map<String, Object> params, LinkedHashMap<String, String> orderby) ;
//
//	
//	/**
//	 * 获取权限下的部门信息
//	 * @param roleid
//	 * @return
//	 */
//	public List queryDepartmentByRoleId(String roleid);
//	
//	/**
//	 * 获取人员的所有部门
//	 * @param userid
//	 * @return
//	 */
//	public List queryDepartmentByUserId(String userid);
//   
//
//	/**
//	 * 查询子部门数据
//	 * @param set
//	 * @return
//	 */
//	public  Page<Map<String, Object>> querySubDepartmentData(Set<String> set);
//	/**
//	 * 查询子部门所有的id
//	 * @param id
//	 * @return
//	 */
//	 public  Set<String>	findSubIds(String id);
//
//	   /** 
//		 *  同级部门 部门不能重名，不同层次 可以重名判断
//		 * 
//		 * ****/
//	 public boolean findSameLevelDepartmentExistingName(Department department);
	
	public Page<Map<String, Object>> queryDepartments(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select * from T_DEPARTMENT t where 1=1 ");
		/* 部门名称 */
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and t.NAME like '%"
					+ params.get("name").toString().trim() + "%'");
		}
		/* 部门状态 */
		if (params.containsKey("status")
				&& StringUtils.isNotBlank(params.get("status").toString())) {
			sb.append(" and t.STATUS=? ");
			param.add(params.get("status"));

		}
		if (params.containsKey("parent_id")
				&& StringUtils.isNotBlank(params.get("parent_id").toString())) {
			sb.append(" and t.PARENT_ID=? ");
			param.add(params.get("parent_id"));
		}

		if (params.containsKey("subDep")
				&& StringUtils.isNotBlank(params.get("subDep").toString())) {
			sb.append(" and t.PARENT_ID !=-1");
		}

		orderby.put("t.CREATE_DATETIME", "desc");

		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	public Page<Map<String, Object>> findDepartmentsbyParams(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		return queryDepartments(params, orderby);
	}

	public Department findDepartmentByName(String name) {

		/**
		 * 方案一:限制整个系统部门名称不能重名
		 * ***/
		String hql = "select r from Department r where r.name=?";
		Department department = (Department) this.findForObject(
				hql.toString(), new Object[] { name });
		return department;
	}

	/**
	 * 方案二 同级部门 部门名称不能重名，不同层次 可以重名
	 * 
	 * ****/
	public boolean findSameLevelDepartmentExistingName(Department department) {
			int count = 0;
			String hql="select count(*)  from Department d where  d.name=? ";
			String[] params = new String[] { department.getName() };
			List list = find(hql, params);
			if (list != null && list.size() > 0)
				count = ((Long)list.get(0)).intValue();
			return count > 0;
	}

	public Page<HashMap<String, Object>> querySubDepartments(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select * from t_department t where 1=1 ");
		if (params.containsKey("parent_id")
				&& StringUtils.isNotBlank(params.get("parent_id").toString())) {
			sb.append(" and t.parent_id=? ");
			param.add(params.get("parent_id"));
		}
		if (orderby != null) {
			orderby.put("t.create_datetime", "desc");
		}
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	public List queryDepartmentByRoleId(String roleid) {
		StringBuffer sb = new StringBuffer(
				"select department.*  from  t_department_role  dr ,t_role role,t_department department where "
						+ "  dr.ROLE_ID=role.ID and dr.DEPARTMENT_ID=department.ID and dr.role_ID=?");
		return sqlQueryForList(sb.toString(), new Object[] { roleid }, null);
	}

	public List queryDepartmentByUserId(String userid) {
		StringBuffer sb = new StringBuffer(
				"select distinct d.* from t_department_user du,t_department d,t_user u  "
						+ " where du.USER_ID = u.ID and du.DEPATEMENT_ID = d.ID and du.USER_ID=?");
		return sqlQueryForList(sb.toString(), new Object[] { userid }, null);
	}

	public Page<HashMap<String, Object>> querySubDepartments2(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select * from t_department t where 1=1 ");
		if (params.containsKey("parent_id")
				&& StringUtils.isNotBlank(params.get("parent_id").toString())) {
			sb.append(" and t.parent_id=? ");
			param.add(params.get("parent_id"));
		}
		if (orderby != null) {
			orderby.put("t.create_datetime", "desc");
		}
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}

	public Set<String> findSubIds(String id) {
		Set<String> set = new HashSet<String>(50);// 存储所有的子节点的id
		Page<Map<String, Object>> page = null;
		String parentId = id;
		boolean bool = true;
		int endCondition = 0;
		while (true) {
			// 接受参数类
			Map<String, Object> parms = new HashMap<String, Object>();
			// 排序参数
			LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
			parms.put("parent_id", parentId);
			page = queryDepartments2(parms, orderby);

			List<Map<String, Object>> lists = page.getItems();
			int listSize = lists.size();

			// 接受本次查询出来的所有id的集合
			Set<String> children = new HashSet<String>(listSize);

			for (int i = 0; i < listSize; i++) {
				Map<String, Object> map = lists.get(i);
				String tempId = (String) map.get("id");
				if (!StringUtil.isEmpty(tempId)) {
					children.add(tempId);
				}
			}
			if (!children.isEmpty()) {
				String tempChildren = Arrays.deepToString(children.toArray());
				parentId = tempChildren.replaceFirst("\\[", "").replaceAll(
						"\\]", "");
				set.add(parentId);
			}
			// 终止循环条件
			if (endCondition == set.size()) {
				break;
			}
			endCondition = set.size();
		}
		return set;
	}

	private Page<Map<String, Object>> queryDepartments2(
			Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer(
				"select * from t_department t where 1=1 ");
		if (params.containsKey("parent_id")
				&& StringUtils.isNotBlank(params.get("parent_id").toString())) {
			sb.append(" and t.parent_id  in (?) ");
			param.add(params.get("parent_id"));
		}
		if (orderby != null) {
			orderby.put("t.create_datetime", "desc");
		}
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}
//自己测试使用 ，但是现在项目中没有使用到
	public List<Department> findIds(String[] ids) {
		StringBuffer buffer = new StringBuffer(400);
		for (int i = 0; i < ids.length; i++) {
			String temp = ids[i];
			if (!StringUtil.isEmpty(temp)) {
				buffer.append("'");
				buffer.append(temp);
				buffer.append("'");
			}
			if (!(ids.length - 1 == i)) {
				System.out.println(i);
				buffer.append(" , ");
			}
		}
		List<Department> departments = getSession()
				.createQuery(" from  Department  where parentId in (:parentId)  ")
				.setString("parentId", buffer.toString()).list();

	return departments;
	}

	public Page<Map<String, Object>> querySubDepartmentData(Set<String> set) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
		StringBuffer sb = new StringBuffer(
				"select * from t_department t where 1=1 ");
		String temp = Arrays.deepToString(set.toArray());
		temp = temp.replaceFirst("\\[", "'");
		temp = temp.replaceAll("\\]", "'");
		temp = temp.replaceAll("\\s", "");
		temp = temp.replaceAll(",", "' , '");
		sb.append(" and t.parent_id  in (" + temp + ") ");
		//使用这个方式 需要自己写 相关的分页数据格式以及统计
		//List<Department> departments = getSession().createQuery("  from  Department where parentId in (:parentId)  ").setString("parentId", temp).list();
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}
}
