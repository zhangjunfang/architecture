package cn.newcapec.foundation.privilege.biz;

import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.framework.core.biz.BaseService;
import cn.newcapec.framework.core.utils.pagesUtils.Page;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * 菜单业务接口类
 * @author andy.li
 *
 */
@SuppressWarnings("all")
public  interface MenuService extends BaseService<Menu> {

	/**
	 * 获取资源列表
	 *
	 * @param params
	 * @param orderby
	 * @return
	 */

	public  Page queryMenu(Map<String, Object> params, LinkedHashMap<String, String> orderby);

	/**
	 * 查询资源单条记录By主键
	 *
	 * @param id
	 * @return
	 */
	public  Menu queryMenuById(String id);

	/**
	 * 删除角色
	 * @param ids id集合数组
	 */
	public  void deleteMenu(String[] ids);



	/**
	 * 获得级联菜单
	 * 有资源的菜单才能显示，否不显示
	 * @param ids
	 * @param orderby
	 * @return
	 */
	public  List<Menu> queryCascadeMenus(Object[] ids, LinkedHashMap<String, String> orderby);


	/**
	 * 获取角色信息
	 * @param name
	 * @return
	 */
	public  Menu findMenuByName(String name);

	/**
	 * 保存菜单列表
	 * @param menus
	 */
	public  void saves(List<Menu> menus);
}
