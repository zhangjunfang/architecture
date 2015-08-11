package cn.newcapec.function.platform.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import cn.newcapec.framework.core.dao.hibernate.HibernateEntityDao;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.function.platform.model.RoleMenu;

/**
 * 
 * @Description: 角色菜单实体类
 * @author gaochongfei
 * @date 2014-4-4 下午04:00:50
 * @version V1.0
 */
@SuppressWarnings("all")
@Repository("RoleMenuDAO")
public class RoleMenuDAO extends HibernateEntityDao {

	@Override
	protected Class getReferenceClass() {
		return RoleMenu.class;
	}
    /**
     * 
     * @Description: 查询
     * @param @param params
     * @param @param orderby
     * @param @return   
     * @return Page    
     * @throws
     */
	public Page queryRoleMenu(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
//		StringBuffer sb = new StringBuffer(
//				"select t1.name role_name ,t1.id role_id, "
//						+ "from t_role t1,t_user_role t2  where 1=1 and t1.id=t2.role_id and t2.user_id = t3.id ");
//		if (params.containsKey("name")
//				&& StringUtils.isNotBlank(params.get("name").toString())) {
//			sb.append(" and t.name=:name ");
//			param.add(params.get("name"));
//		}
//		return this.sqlQueryForPage(sb.toString(), param.toArray(), orderby);
		return null;
	}
	/**
	 * @Description: 新增
	 * @param @param params   
	 * @return void    
	 * @throws
	 */ 
	public void addRoleMenu(Map<String, Object> params) {
		//List<Object> param = new ArrayList<Object>();
		
//		StringBuffer sql = new StringBuffer(
//				"insert into base_role_menu value (:name,:dfs,:sdd)");
//		//for
////		{
//			SQLQuery query=	getSession().createSQLQuery(sql.toString());
//			query.setParameterList("", new Object[]{} );
////			query.setString("name", params.get(""));
//			query.executeUpdate();
////		}
		
	}
	/**
	 * @Description:删除
	 * @param @param role_uid   
	 * @return void    
	 * @throws
	 */ 
	public void delRoleMenu(Map<String, Object> params) {
//		List<Object> param = new ArrayList<Object>();
//		StringBuffer sql = new StringBuffer(
//				"delete from BASE_ROLE_MENU where 1=1");
//		if (params.containsKey("roleid")&& StringUtils.isNotBlank(params.get("roleid").toString())) {
//			sql.append(" and roleid=:roleid ");
//			param.add(params.get("roleid"));
//		}else{
//			return;
//		}
//		if (params.containsKey("appid")&& StringUtils.isNotBlank(params.get("appid").toString())) {
//			sql.append(" and appid=:appid ");
//			param.add(params.get("appid"));
//		}else{
//			return;
//		}
//		if (params.containsKey("customerunitcode")&& StringUtils.isNotBlank(params.get("customerunitcode").toString())) {
//	    sql.append(" and customerunitcode=:customerunitcode ");
//	    param.add(params.get("customerunitcode"));
//        }else{
//			return;
//		}
//		
//		getSession().createSQLQuery(sql.toString()).executeUpdate();
	}
	/**
	 * @Description: TODO
	 * @param @param uuid
	 * @param @param object
	 * @param @return   
	 * @return Object    
	 * @throws
	 */ 
	public Page queryMenuByRoleId(String uuid, LinkedHashMap<String, String> orderby) {
			String hql = "select u from RoleMenu u where u.roleid =?";
			Page roleMenu = this.queryForpage(hql, new Object[] { uuid }, orderby);
			return roleMenu;
	}
}
