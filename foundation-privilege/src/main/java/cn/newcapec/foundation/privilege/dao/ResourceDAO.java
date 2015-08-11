package cn.newcapec.foundation.privilege.dao;

import cn.newcapec.foundation.privilege.dao.base.BaseResourceDAO;
import cn.newcapec.foundation.privilege.model.Resource;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源接口类
 *
 * @author andy.li
 *
 */
@SuppressWarnings("all")
@Repository("resourceDAO")
public class ResourceDAO extends BaseResourceDAO{
//	/**
//	 * 获取角色列表
//	 *
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//
//	public Page queryResource(Map<String, Object> params, LinkedHashMap<String, String> orderby);
//
//	/**
//	 * 获取资源单条记录By主键
//	 *
//	 * @param id
//	 * @return
//	 */
//	public Resource queryResourceById(String id);
//
//	/**
//	 * 获得菜单下的所有功能
//	 * @param menuid
//	 * @return
//	 */
//	public List<Map<String,Object >>  queryResourcesByMenuId(String menuid);
//
//
//	/**
//	 * 获得角色下的所有功能
//	 * @param menuid
//	 * @return
//	 */
//	public List<Map<String,Object >>  queryResourcesByRoleId(String role);
//
//	/**
//	 * 删除功能
//	 * @param ids id集合数组
//	 */
//	public void deleteResource(String[] ids);
//	
//	
//	/**
//	 * 查询角色下所有的资源信息
//	 * @param userid
//	 * @return
//	 */
//	public List queryResorucesByRoleids(Object[] roleid);
	
	public Page queryResource(Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select * from t_resource resources where 1=1 ");
		/* 资源名称 */
		if (params.containsKey("name") && StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and resources.NAME like '%" + params.get("name").toString().trim() + "%'");
			// param.add("'%"+params.get("name")+"%'");
			// System.out.println("'%"+params.get("name")+"%'");
		}
//		/* 资源类型 */
//		if (params.containsKey("typeid") && StringUtils.isNotBlank(params.get("typeid").toString())) {
//			sb.append(" and type.id= " + params.get("typeid").toString().trim());
//			// param.add(params.get("typeid").toString().trim());
//		}
		if (params.containsKey("status") && StringUtils.isNotBlank(params.get("status").toString())) {
//			sb.append(" and resources.STATUS= " + params.get("status").toString().trim());
			// param.add(params.get("status").toString().trim());
			// System.out.println(params.get("status").toString().trim());
		}
		/* 父ID */
		if (params.containsKey("parent_id") && StringUtils.isNotBlank(params.get("parent_id").toString())) {
			sb.append(" and resources.PARENT_ID= " + params.get("parent_id").toString().trim());
			// param.add(params.get("parent_id").toString().trim());
		}
		/* 父ID */
		if (params.containsKey("menu_id") && StringUtils.isNotBlank(params.get("menu_id").toString())) {
			sb.append(" and resources.MENU_ID= " + params.get("menu_id").toString().trim());
			// param.add(params.get("parent_id").toString().trim());
		}
		// orderby.put("resources.create_datetime", "desc");
		return this.sqlQueryForPage(sb.toString(), null, orderby);
	}

	public Resource queryResourceById(String id) {
		String hql = "select r from Resource r where r.id=?";
		Resource resource = (Resource) this.findForObject(hql.toString(), new Object[] { id });
		return resource;
	}

	public List<Map<String, Object>> queryResourcesByMenuId(String menuid) {
		String sql ="select r.*  from  t_resource r ,t_menu m  where m.ID = m.MENU_ID";
		return sqlQueryForList(sql, null, null);
	}

	public List<Map<String, Object>> queryResourcesByRoleId(String role) {
		String sql="select  resources.*  from t_role_resource rr,t_resource resources,t_role role where rr.ROLE_ID=role.ID " +
				" and rr.RESOURCE_ID=resources.ID and  rr.ROLE_ID=?";
		return sqlQueryForList(sql, new Object[]{role}, null);
	}

	public void deleteResource(String[] ids) {
		if (null != ids && ids.length > 0) {
			StringBuffer sb = new StringBuffer("delete from t_resource where  1=1");
			StringBuffer resourceids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				resourceids.append(ids[i]).append(",");
			}
			resourceids.deleteCharAt(resourceids.length() - 1);
			sb.append(" and id in (" + resourceids.toString() + ")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}
	
	/**
	 * 查询角色下所有的资源信息
	 * @param userid
	 * @return
	 */
	public List queryResorucesByRoleids(Object[] roleid){
		if(null!=roleid && roleid.length>0){
			StringBuffer sb = new StringBuffer("select distinct r.* ,t.url as menuUrl, t.name as menuname , t.parent_id as menuparent,t.id as menuid " +
					" from  t_role_resource rr , t_resource r,t_menu t  " +
					" where r.id=rr.resource_id  and t.id=r.menu_id ") ;
			StringBuffer resourceids = new StringBuffer();
			for (int i = 0; i < roleid.length; i++) {
				resourceids.append(roleid[i]).append(",");
			}
			resourceids.deleteCharAt(resourceids.length() - 1);
			sb.append(" and rr.role_id in (" + resourceids.toString() + ")");
			return this.sqlQueryForList(sb.toString(), null, null);
		}
		return null;
		
	}
}
