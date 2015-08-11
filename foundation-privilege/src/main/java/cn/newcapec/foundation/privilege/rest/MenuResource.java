package cn.newcapec.foundation.privilege.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;

@Component
@Scope("prototype")
@SuppressWarnings("all")
public class MenuResource extends PrivilegeResource {

	/* 菜单业务类 */
	@Autowired
	private MenuService menuService;

	// 接受参数类
	private Map<String, Object> parms = new HashMap<String, Object>();
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();

	/**
	 * 获取菜单列表UI
	 * 
	 * @param request
	 * @param response
	 */
	public void menuListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/menu/pagelet/v1.0/menu_list.html";
		// 获得菜单列表
		menuListGrid(request, response);
		// 获得菜单树

		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 获取菜单列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void menuListGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/menu/pagelet/v1.0/menu_list_grid.html";
		/* 查询列表 */
		orderby.put("sortby", "asc");
		Page page = menuService.queryMenu(getJsonObject(), orderby);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadMenuList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 部门树视图展示类
	 * 
	 * @param request
	 * @param response
	 */
	public void menuTree(BaseRequest request, BaseResponse response) {
		try {
			String ifmenu = JSONTools.getString(getJsonObject(), "menutree");
			Map<String, Object> queryMap = new HashMap<String, Object>();
			PageContext.setPagesize(Integer.MAX_VALUE);
			orderby.put("sortby", "asc");
			Msg msg = new Msg();
			String data = "-1";
			if (null != getJsonObject()) {
				data = JSONTools.getString(getJsonObject(), "id");
				data = data.equals("") ? "-1" : data;
			}
			if ("menutree".equals(ifmenu)) {
				queryMap.put("parent_id", "'" + data + "'");
			}
			// 排序
			Page page = menuService.queryMenu(queryMap, orderby);
			if (null != page && null != page.getItems()
					&& page.getItems().size() > 0) {
				List<Map<String, String>> menus = page.getItems();
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (Map<String, String> map : menus) {
					Map map2 = new HashMap();
					if ("-1".equals(JSONTools.getString(getJsonObject(), "id"))) {
						if ("-1".equals(map.get("PARENT_ID"))) {
							map2.put("pId", map.get("PARENT_ID").toString());
							map2.put("id", map.get("ID").toString());
							map2.put("name", map.get("NAME").toString());
							if("menutree".equals(ifmenu)){
								map2.put("isParent", "false");
							}else{
								map2.put("isParent", "true");
							}
							list.add(map2);

						} 
					}else {
						if (!"-1".equals(map.get("PARENT_ID"))) {
							map2.put("pId", map.get("PARENT_ID").toString());
							map2.put("id", map.get("ID").toString());
							map2.put("name", map.get("NAME").toString());
							map2.put("isParent", "false");
							list.add(map2);
						}
					}
				}
				msg.setMsg("dempartment Item");
				msg.setData(list);
			}
			response.print(msg.toJSONObjectPresention());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
		}
	}

	/**
	 * 添加菜单UI
	 * 
	 * @param request
	 * @param response
	 */
	public void addMenuUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/menu/pagelet/v1.0/addMenuUI.html";
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 编辑菜单UI
	 * 
	 * @param request
	 * @param response
	 */
	public void editMenuUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/menu/pagelet/v1.0/editMenuUI.html";
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 添加菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	public void add(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				Menu menu = new Menu();
				menu.setName(JSONTools.getString(getJsonObject(), "name")
						.trim());
				menu.setSortby(JSONTools.getString(getJsonObject(), "sortby"));
				menu.setUrl(JSONTools.getString(getJsonObject(), "url"));
				menu.setIcon(JSONTools.getString(getJsonObject(), "icon"));
				menu.setIsParent(JSONTools.getString(getJsonObject(),
						"isParent"));
				menu.setParentId(JSONTools.getString(getJsonObject(),
						"parentid"));
				menu.setVisible("1");
				if (null != menu) {

					// 判断角色名称是否存在
					Menu oldRole = menuService.findMenuByName(menu.getName()
							.trim());
					if (null != oldRole
							&& !oldRole.getId().equals(menu.getId())) {
						throw new BaseException("菜单名字已经存在，请重新输入！");
					}

					menuService.saveOrUpdate(menu);
					Msg msg = new Msg();
					msg.setSuccess(true);
					response.print(msg.toJSONObjectPresention());
				} else {
					Msg msg = new Msg();
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			ThrowsException(e, "新增菜单失败！");
		}
	}

	/**
	 * 修改菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	public void edit(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				Menu resource = menuService.get(JSONTools.getString(
						getJsonObject(), "id"));

				resource.setId(JSONTools.getString(getJsonObject(), "id"));
				resource.setName(JSONTools.getString(getJsonObject(), "name"));
				resource.setSortby(JSONTools.getString(getJsonObject(),
						"sortby"));
				resource.setUrl(JSONTools.getString(getJsonObject(), "url"));
				resource.setIcon(JSONTools.getString(getJsonObject(), "icon"));
				resource.setIsParent(JSONTools.getString(getJsonObject(),
						"isParent"));

				if (null != resource) {
					menuService.saveOrUpdate(resource);
					Msg msg = new Msg();
					msg.setSuccess(true);
					response.print(msg.toJSONObjectPresention());
				} else {
					Msg msg = new Msg();
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			ThrowsException(e, "修改菜单失败！");
		}
	}

	/**
	 * 删除菜单信息
	 * 
	 * @param request
	 * @param response
	 */
	public void delete(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				String ids = JSONTools.getString(getJsonObject(), "menuids");
				if (StringUtils.isNotBlank(ids)) {
					String[] idss = ids.split(",");
					Msg msg = new Msg();
					msg.setMsg("删除菜单成功！");
					msg.setSuccess(true);
					menuService.deleteMenu(idss);
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			ThrowsException(e, "删去菜单失败！");
		}
	}

	/**
	 * 通过主键获取单条记录
	 * 
	 * @param request
	 * @param response
	 */
	public void selectById(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				Menu menu = menuService.queryMenuById(JSONTools.getString(
						getJsonObject(), "id"));
				Msg msg = new Msg();
				msg.setData(menu);
				msg.setSuccess(true);
				response.print(msg.toJSONObjectPresention());
			}
		} catch (Exception e) {
			ThrowsException(e, "获取菜单失败！");
		}
	}

}
