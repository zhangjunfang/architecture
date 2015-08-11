package cn.newcapec.foundation.privilege.dao;

import cn.newcapec.foundation.privilege.dao.base.BaseMenuDAO;
import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户接口类
 *
 * @author andy.li
 *
 */
@SuppressWarnings("all")
@Repository("menuDAO")
public class MenuDAO extends  BaseMenuDAO {
//	/**
//	 * 获取菜单列表
//	 *
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//
//	public Page queryMenu(Map<String, Object> params, LinkedHashMap<String, String> orderby);
//
//	/**
//	 * 获取资源信息
//	 *
//	 * @param id
//	 * @return
//	 */
//	public Menu queryMenuById(String id);
//
//	/**
//	 * 删除功能
//	 * @param ids id集合数组
//	 */
//	public void deleteMenu(String[] ids);
//
//	/**
//	 * 获取二级菜单
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//	public Page queryMenuBySecond(Map<String, Object> params, LinkedHashMap<String, String> orderby);
//
//	/**
//	 * 获得级联菜单
//	 * 有资源的菜单才能显示，否不显示
//	 * @param params
//	 * @param orderby
//	 * @return
//	 */
//	public Page queryCascadeMenus(Map<String, Object> params, LinkedHashMap<String, String> orderby);
//	/**
//	 * 判断角色是否重复
//	 * @param name
//	 * @return
//	 */
//	public Menu findMenuByName(String name);
//	
//	/**
//	 *根据资源查询菜单
//	 **/
//	public Page findMenuByResources(List mapList,Map<String, Object> params,LinkedHashMap<String, String> orderby);
	
	/**
	 * 获取菜单列表
	 *
	 * @param params
	 * @param orderby
	 * @return
	 */
	public Page queryMenu(Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("select menu.* from t_menu menu where 1=1 ");
		/* 资源名称 */
		if (params.containsKey("name") && StringUtils.isNotBlank(params.get("name").toString())) {
			sb.append(" and menu.NAME like '%" + params.get("name").toString().trim() + "%'");
		}
		/* 父ID */
		if (params.containsKey("parent_id") && StringUtils.isNotBlank(params.get("parent_id").toString())) {
			sb.append(" and menu.PARENT_ID= " + params.get("parent_id").toString().trim());
		}

		return this.sqlQueryForPage(sb.toString(), null, orderby);
	}

	/**
	 * 获取菜单信息
	 *
	 * @param id
	 * @return
	 */
	public Menu findMenuById(String id) {
		String hql = "select r from Menu r where r.id=?";
		Menu menu = (Menu) this.findForObject(hql.toString(), new Object[] { id });
		return menu;
	}
	

	/**
	 * 删除功能
	 * @param ids id集合数组
	 */
	public void deleteMenu(String[] ids) {
		if (null != ids && ids.length > 0) {
			StringBuffer sb = new StringBuffer("delete from t_menu where  1=1");
			StringBuffer menuids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				menuids.append(ids[i]).append(",");
			}
			menuids.deleteCharAt(menuids.length() - 1);
			sb.append(" and id in (" + menuids.toString() + ")");
			getSession().createSQLQuery(sb.toString()).executeUpdate();
		}
	}
	
	
	/**
	 * 获取菜单
	 * @param ids id集合
	 * @param orderby
	 * @return
	 */
	public List  queryMenusByIds(Object[] ids,LinkedHashMap<String, String> orderby) {
		if(ids!=null && ids.length>0){
			StringBuffer sb = new StringBuffer("select  m from Menu m where 1=1 and m.visible=1");
			StringBuffer menuids = new StringBuffer();
			for (int i = 0; i < ids.length; i++) {
				menuids.append(ids[i]).append(",");
			}
			menuids.deleteCharAt(menuids.length() - 1);
			sb.append(" and m.id in (" + menuids.toString() + ")");
			Page page = this.queryForpage(sb.toString(), null, orderby);
			if(page.getItems()!=null && page.getItems().size()>0){
				return page.getItems();
			}
			
		}
		return null;
	}
	

	public Page queryMenuBySecond(Map<String, Object> params, LinkedHashMap<String, String> orderby) {
		/* 参数集合类 */
		List<Object> param = new ArrayList<Object>();
		StringBuffer sb = new StringBuffer("SELECT * FROM t_menu where parent_id in (select t.id from t_menu t where t.parent_id=-1)");
		return this.sqlQueryForPage(sb.toString(), null, orderby);
	}


	public Menu findMenuByName(String name) {
		String hql = "select r from Menu r where r.name=?";
		Menu menu =  (Menu)this.findForObject(hql.toString(), new Object []{name});
		return menu;
	}

	public Page findMenuByResources(List mapList,Map<String, Object> params,LinkedHashMap<String, String> orderby) {
		if(null != mapList && mapList.size() > 0){
			StringBuffer hql = new StringBuffer("select menu.* from t_menu menu where menu.id in ( ");
			List<Object> values = new ArrayList<Object>();
			for (int i = 0; i < mapList.size(); i++) {
				if(i != mapList.size()-1){
					hql.append("?,");
				}else{
					hql.append("?");
				}
				Map<String, Object> map = (Map<String, Object>)mapList.get(i);
				values.add(map.get("menu_id"));
			}
			hql.append(" ) ");
			/**
			 *
			 **/
			if (params.containsKey("parent_id") && StringUtils.isNotBlank(params.get("parent_id").toString())) {
				hql.append(" and menu.parent_id = ? ");
				values.add(params.get("parent_id").toString().trim());
			}
			/**
			 * 查找二级菜单
			 * */
			if (params.containsKey("second_id") && StringUtils.isNotBlank(params.get("second_id").toString())) {
				hql.append(" and menu.parent_id in (select t.id from t_menu t where t.parent_id=-1) ");

			}
			orderby.put("sortby", "asc");
			return this.sqlQueryForPage(hql.toString(), values.toArray(), orderby);
		}
		return null;
	}
}
