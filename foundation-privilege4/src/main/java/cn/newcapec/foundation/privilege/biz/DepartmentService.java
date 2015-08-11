package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.Department;
import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

import java.util.*;

/**
 * 部门业务接口类
 * 
 * @author andy.li
 * 
 */
@SuppressWarnings("all")
public interface DepartmentService extends BaseService<Department> {

	/**
	 * 获取部门列表
	 * 
	 * @param params
	 * @param orderby
	 * @return
	 */

	public Page<Department> queryDepartments(Map<String, Object> params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 删除部门
	 * 
	 * @param ids
	 * @return
	 */
	public void deleteDepartmentsbyIds(String[] ids);

	/**
	 * 获取部门通过 部门名称 或者 状态
	 * 
	 * @param name
	 * @param status
	 * @return Department
	 */
	public Page<Department> findDepartmentsbyParams(Map<String, Object> params,
			LinkedHashMap<String, String> orderby);

	/**
	 * 通过部门名称 查找 当前部门名称是否存在 ，不存在 返回null
	 * 
	 * @param name
	 * @return
	 */
	public Department findDepartmentByName(String name);

	/**
	 * 获取某一个部门下，所有列表数据
	 * 
	 * @param params
	 * @param orderby
	 * @return
	 * */
	public Page<HashMap<String, Object>> querySubDepartments(
			Map<String, Object> params, LinkedHashMap<String, String> orderby);

	/**
	 * 获取权限下的部门信息
	 * 
	 * @param roleid
	 * @return
	 */
	public List queryDepartmentByRoleId(String roleid);

	/**
	 * 获取人员的所有部门
	 * 
	 * @param userid
	 * @return
	 */
	public List queryDepartmentByUserId(String userid);

	/**
	 * 查询子部门数据
	 * 
	 * @param set
	 * @return
	 */
	public Page<Map<String, Object>> querySubDepartmentData(Set<String> set);

	/**
	 * 查询子部门所有的id
	 * 
	 * @param id
	 * @return
	 */
	public Set<String> findSubIds(String id);

	/**
	 * 同级部门 部门名称不能重名，不同层次 可以重名判断
	 * 
	 * *
	 ***/
	public boolean findSameLevelDepartmentExistingName(Department department);

	/**
	 * 保存部门列表
	 * 
	 * @param entitys
	 */
	public void saves(List<Department> entitys);
}
