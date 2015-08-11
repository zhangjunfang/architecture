package cn.newcapec.foundation.privilege.biz.impl;

import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.dao.MenuDAO;
import cn.newcapec.foundation.privilege.dao.ResourceDAO;
import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 菜单业务接口实现类
 * 
 * @author Administrator
 * 
 */
@Service("menuService")
@Transactional
@SuppressWarnings("all")
public class MenuServiceImpl implements LogEnabled, MenuService {

	@Autowired
	private MenuDAO menuDAO;

	@Autowired
	private ResourceDAO resourceDAO;

	@Override
	public Menu get(String id) {
		return menuDAO.get(id);
	}

	@Override
	public void removeUnused(String id) {

	}

	@Override
	public void saveOrUpdate(Menu entity) {
		menuDAO.saveOrUpdate(entity);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Page queryMenu(Map<String, Object> params,
			LinkedHashMap<String, String> orderby) {
		Page page = menuDAO.queryMenu(params, orderby);
		if (null != page) {
			return page;
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Menu queryMenuById(String id) {
		Menu menu = menuDAO.findMenuById(id);
		return menu;
	}

	@Override
	public void deleteMenu(String[] ids) {
		if (null != ids && ids.length > 0) {
			menuDAO.deleteMenu(ids);
		}
	}


	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> queryCascadeMenus(Object[] ids,
			LinkedHashMap<String, String> orderby) {
		List list = menuDAO.queryMenusByIds(ids, orderby);
		if(null!= list && list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				Menu menu = (Menu) list.get(i);
				if(null!=menu){
					if(null!=menu.getParentId()){
						Menu parentMenu = menuDAO.findMenuById(menu.getParentId());
						if (null != parentMenu) {
							list.add(parentMenu);
						}
					}
				}
			}
			if (!list.isEmpty()) {
				return list;
			}
		}
		return null;
	}

	@Override
	public Menu findMenuByName(String name) {
		Menu role = menuDAO.findMenuByName(name);
		if (null != role) {
			return role;
		}
		return null;
	}


	// @Override
	// public void install() {
	// // Menu menu = null;
	//
	// // /* 添加根节点 */
	// // menu = new Menu();
	// // menu.setName("菜单树");
	// // menu.setParentId("0");
	// // menu.setIsParent("true");
	// // this.saveOrUpdate(menu);
	// // String rootId = menu.getId();
	// // /* end */
	//
	// this.install_Step2("0");
	//
	// // System.out.println(menu.getId());
	// }
	//
	// /**
	// * 创建第二级菜单
	// *
	// * @param parentId
	// */
	// private void install_Step2(String parentId) {
	// Menu menu = null;
	//
	// menu = new Menu();
	// menu.setName("权限模块管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("true");
	// menu.setSortby("1");
	// menu.setIcon("../../../styles/css/portal/office/frame/images/menu_person.png");
	// this.saveOrUpdate(menu);
	// this.install_Step3(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("报表模块管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("true");
	// menu.setSortby("2");
	// menu.setIcon("../../../styles/css/portal/office/frame/images/menu_clock.png");
	// this.saveOrUpdate(menu);
	// this.install_Step3(menu.getId(), menu.getName());
	//
	// // menu = new Menu();
	// // menu.setName("扩展菜单");
	// // menu.setParentId(parentId);
	// // menu.setIsParent("true");
	// // menu.setSortby("3");
	// //
	// menu.setIcon("../../../styles/css/portal/office/frame/images/menu_total.png");
	// // this.saveOrUpdate(menu);
	// // this.install_Step3(menu.getId(), menu.getName());
	// //
	// // menu = new Menu();
	// // menu.setName("其他菜单");
	// // menu.setParentId(parentId);
	// // menu.setIsParent("true");
	// // menu.setSortby("4");
	// //
	// menu.setIcon("../../../styles/css/portal/office/frame/images/menu_clock.png");
	// // this.saveOrUpdate(menu);
	// // this.install_Step3(menu.getId(), menu.getName());
	// }
	//
	// /**
	// * 创建第三级菜单
	// *
	// * @param parentId
	// */
	// private void install_Step3(String parentId, String nodeName) {
	// Menu menu = null;
	//
	// if ("权限模块管理".equals(nodeName)) {
	// menu = new Menu();
	// menu.setName("人员管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("1");
	// menu.setUrl("/restful/privilegeProxyService/user/userListUI");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("部门管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("2");
	// menu.setUrl("/restful/privilegeProxyService/department/departmentListUI");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("菜单管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("3");
	// menu.setUrl("/restful/privilegeProxyService/menu/menuListUI");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("功能管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("4");
	// menu.setUrl("/restful/privilegeProxyService/resource/resourceListUI");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("角色管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("5");
	// menu.setUrl("/restful/privilegeProxyService/role/roleListUI");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// // menu = new Menu();
	// // menu.setName("授权管理");
	// // menu.setParentId(parentId);
	// // menu.setIsParent("false");
	// // menu.setSortby("6");
	// // menu.setUrl("/restful/privilegeProxyService/userRole/userRoleListUI");
	// // this.saveOrUpdate(menu);
	// // this.install_Step4(menu.getId(), menu.getName());
	// //
	// // menu = new Menu();
	// // menu.setName("日志管理");
	// // menu.setParentId(parentId);
	// // menu.setIsParent("false");
	// // menu.setSortby("7");
	// // menu.setUrl("main6.html");
	// // this.saveOrUpdate(menu);
	// // this.install_Step4(menu.getId(), menu.getName());
	//
	// } else if ("扩展菜单".equals(nodeName)) {
	//
	// } else if ("报表模块管理".equals(nodeName)) {
	// menu = new Menu();
	// menu.setName("数据源管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("1");
	// menu.setUrl("/restful/reportProxyService/ds/index");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("数据集管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("2");
	// menu.setUrl("/restful/reportProxyService/dataset/index");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("控件管理");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("3");
	// menu.setUrl("/restful/reportProxyService/widget/index");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("报表设计");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("4");
	// menu.setUrl("/restful/reportProxyService/design/index");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// menu = new Menu();
	// menu.setName("报表预览");
	// menu.setParentId(parentId);
	// menu.setIsParent("false");
	// menu.setSortby("5");
	// menu.setUrl("/restful/reportProxyService/preview/index");
	// this.saveOrUpdate(menu);
	// this.install_Step4(menu.getId(), menu.getName());
	//
	// } else {
	//
	// }
	// }
	//
	// /**
	// * 为菜单添加动作
	// *
	// * @param menuId
	// * @param menuName
	// */
	// private void install_Step4(String menuId, String menuName) {
	// Resource resource = null;
	// if ("人员管理".equals(menuName)) {
	// resource = new Resource();
	// resource.setResourceName("添加");
	// resource.setMenuid(menuId);
	// resourceDAO.save(resource);
	//
	// resource = new Resource();
	// resource.setResourceName("修改");
	// resource.setMenuid(menuId);
	// resourceDAO.save(resource);
	//
	// resource = new Resource();
	// resource.setResourceName("删除");
	// resource.setMenuid(menuId);
	// resourceDAO.save(resource);
	//
	// resource = new Resource();
	// resource.setResourceName("查询");
	// resource.setMenuid(menuId);
	// resourceDAO.save(resource);
	// }
	// }

	@Override
	public void saves(List<Menu> menus) {
		menuDAO.saveOrUpdateAll(menus);
	}


}
