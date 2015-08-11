package cn.newcapec.foundation.privilege.dao;

import cn.newcapec.foundation.privilege.constant.privilegeConstant;
import cn.newcapec.foundation.privilege.dao.base.BaseRoleDAO;
import cn.newcapec.foundation.privilege.model.Role;
import cn.newcapec.foundation.privilege.model.RoleResource;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * 角色接口类
 * @author andy.li
 */
@SuppressWarnings("all")
@Repository("roleDAO")
public class RoleDAO  extends BaseRoleDAO{


	@Autowired
	RoleResourceDAO roleResourceDAO;
	
	public Page queryRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select * from t_role t  where 1=1");
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and t.name like ? ");
			param.add("%"+params.get("name")+"%");
		}
		orderby.put("t.create_datetime", "desc");
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}
	
	public Page queryUserRoles(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select t1.name role_name ,t1.id role_id, from t_role t1,t_user_role t2  where 1=1 and t1.id=t2.role_id and t2.user_id = t3.id ");
		if (params.containsKey("name")
				&& StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and t.name=:name ");
			param.add(params.get("name"));
		}
		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
	}
	
	

	public Role findRoleByName(String name) {
		String hql = "select r from Role r where r.roleName=?";
		Role role =  (Role)this.findForObject(hql.toString(), new Object []{name});
		return role;
	}

	public void deleteRole(String[] ids) {
		if(null!=ids && ids.length>0){
			StringBuffer sb = new StringBuffer("delete from t_role where  1=1");
			 StringBuffer roleids = new StringBuffer();
			  for(int i = 0; i < ids.length; i ++) {
				  roleids.append(ids[i]).append(",");
			  }
			  roleids.deleteCharAt(roleids.length()-1); 
			sb.append(" and id in ("+roleids.toString()+")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}

	public void setRoleResource(String role, String[] resoruceids) {
		if(StringUtils.isNotBlank(role)){
			if(null!=resoruceids && resoruceids.length>0){
				for(int i=0;i<resoruceids.length;i++){
					RoleResource rr = new RoleResource();
					rr.setCreateDatetime(new Date());
					rr.setRoleId(role);
					rr.setResourceId(resoruceids[i]);
					//TODO
					roleResourceDAO.save(rr);
				}
			}
		}
	}

	public void deleteResourceByRoleId(String roleid) {
		String sql ="delete  from  t_role_resource  where role_id=?";
		getSession().createSQLQuery(sql).setParameter(0, roleid).executeUpdate();

	}

	public List queryRoleByDepartmentId(String departmentid) {
		StringBuffer sb = new StringBuffer("select role from  t_department_role  dr ,t_role role,t_department department where " +
				"  dr.role_id=role.id and dr.department_id=department.id and dr.department_id=?");
		return sqlQueryForList(sb.toString(), new Object[]{departmentid}, null);
	}

	public List<Map<String, Object>> queryPriviles(String usreid) {
		//查询人员下的所有资源
		String sql="select s.* from t_resource s where s.id in( select distinct role_resource.resource_id from t_role_resource role_resource ,t_role role " +
				" where role_resource.role_id=role.id and role.status = ? and role_resource.role_id in(" +
				"  select distinct department_role.role_id from t_department_role department_role where department_role.department_id in " +
				"( select dep.id from t_department_user dep,t_user user where dep.user_id=dep.id and dep.user_id=? )))";
		return sqlQueryForList(sql, new Object[]{privilegeConstant.roleNormal,usreid}, null);
	}

	public List<Map<String, Object>> queryPrivilesByUserId(String usreid) {
		//查询人员下的所有资源
		String sql="select s.* from t_resource s where s.id in( select distinct role_resource.resource_id from t_role_resource role_resource,t_role role " +
				" where  role_resource.role_id = role.id and role.status = ? and role_resource.role_id in(" +
				"  select distinct ur.role_id from t_user_role ur where ur.user_id = ? ))" ;
		return sqlQueryForList(sql, new Object[]{privilegeConstant.roleNormal,usreid}, null);
	}
	
	/**
	 * 查询员工所在部门所有的角色信息
	 * @return
	 */
	public List queryRolesDepartmentByUserid(String userid){
		String sql="select distinct dr.role_id from t_department_role dr where dr.department_id in  " +
				"(select du.depatement_id from t_department_user du,t_user u ,t_department d  " +
				" where du.depatement_id=d.id and du.user_id=u.id and du.user_id=?) ";
		return sqlQueryForList(sql,new Object[]{userid},null);
		
	}
	
	/**
	 * 获取员工的角色信息
	 * @param userid
	 * @return
	 */
	public List queryRolesByUserid(String userid){
		String sql="select distinct ur.role_id from t_user_role ur where ur.user_id=?";
		return sqlQueryForList(sql,new Object[]{userid},null);
		
	}

}
