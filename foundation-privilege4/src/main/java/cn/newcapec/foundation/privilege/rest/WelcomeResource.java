package cn.newcapec.foundation.privilege.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.biz.UserRoleService;
import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.foundation.privilege.dao.RoleResourceDAO;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResponse;

/**
 * 欢迎资源类
 * 
 * @author andy.li
 * 
 */
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class WelcomeResource extends PrivilegeResource {

	@Autowired
	MenuService menuService;
	@Autowired
	ResourceService resourceService;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	RoleService roleService;
	@Autowired
	UserService userService;
	@Autowired
	RoleResourceDAO roleResourceDAO;
	@Autowired
	UserRoleService userRoleService;

	/**
	 * 欢迎界面
	 * 
	 * @param request
	 * @param response
	 */
	public void welcome(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/welcome/pagelet/v1.0/welcome.html";
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 系统皮肤切换
	 * @param request
	 * @param response
	 */
	public void SysSwitch(BaseRequest request, BaseResponse response) {
		String url = "/foundation/portal/switch/pagelet/switch.html";
		response.toView(url, getNewcapectViewContext());
	}
	
	/**
	 * 常用功能
	 * @param request
	 * @param response
	 */
	public void commonFunction(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/welcome/pagelet/v1.0/commonFunction.html";
		response.toView(url, getNewcapectViewContext());
	}
	
	/**
	 * 系统初始化
	 * @param request
	 * @param response
	 */
	
	public void SystemInit(BaseRequest request, BaseResponse response) {
		roleService.SystemInit();
	}
	 
//		Msg msg = new Msg();
//		msg.setSuccess(true);
//	    //初始化权限菜单
//		Menu privilegesMenu =new Menu("权限模块管理", "", "-1","../../../styles/css/portal/office/frame/images/menu_person.png","1");
//		privilegesMenu.setSortby("1");
//		menuService.saveOrUpdate(privilegesMenu);
//	    //用户管理菜单
//		Menu userMenu = new Menu("人员管理", "/restful/privilegeProxyService/user/userListUI", privilegesMenu.getId(),"1");
//		menuService.saveOrUpdate(userMenu);
//		List<Resource> userResourceItem = new ArrayList<Resource>();
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/userListUI", "用户列表", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/userListGrid", "用户查询", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/addUserUI", "添加用户", "1",userMenu.getId()));
////		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/addUser", "添加用户", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/updateUserUI", "修改用户", "1",userMenu.getId()));
////		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/updateUser", "修改用户", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/delete", "删去用户", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/distributionResource", "分配角色", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/openDistributionUI", "分配角色界面", "1",userMenu.getId()));
//		userResourceItem.add(new Resource("/restful/privilegeProxyService/user/logonOut", "用户退出", "1",userMenu.getId()));
//		resourceService.saves(userResourceItem);
//		
//		//部门管理菜单
//		Menu departmentMenu =new Menu("部门管理", "/restful/privilegeProxyService/department/departmentListUI", privilegesMenu.getId(),"1");
//		menuService.saveOrUpdate(departmentMenu);
//		List<Resource> departmentResourceItem = new ArrayList<Resource>();
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/departmentListUI", "部门列表界面", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/departmentListGrid", "部门列表", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/departmentTree", "查询部门树", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/addDepartmentUI", "添加部门", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/findDepartment", "更新部门界面", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/updateDepartment", "更新部门", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/updateDepartmentUI","修改部门", "1",departmentMenu.getId()));
//		departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/deleteDepartment", "删去部门", "1",departmentMenu.getId()));
//		resourceService.saves(departmentResourceItem);
//		
//		//菜单管理
//		Menu menus =new Menu("菜单管理", "/restful/privilegeProxyService/menu/menuListUI", privilegesMenu.getId(),"1");
//		menuService.saveOrUpdate(menus);
//		List<Resource> menusResourceItem = new ArrayList<Resource>();
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/menuListUI", "菜单列表界面", "1",menus.getId()));
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/menuListGrid", "菜单列表", "1",menus.getId()));
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/menuTree", "菜单树列表", "1",menus.getId()));
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/selectById", "选择菜单", "1",menus.getId()));
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/addMenuUI", "添加菜单", "1",menus.getId()));
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/editMenuUI","修改菜单", "1",menus.getId()));
//		menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/delete", "删去菜单", "1",menus.getId()));
//		resourceService.saves(menusResourceItem);
//		
//		//功能菜单
//		Menu functionMenus =new Menu("功能管理", "/restful/privilegeProxyService/resource/resourceListUI", privilegesMenu.getId(),"1");
//		menuService.saveOrUpdate(functionMenus);
//		List<Resource> functionResourceItem = new ArrayList<Resource>();
//		functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/resourceListUI", "功能列表界面", "1",functionMenus.getId()));
//		functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/resourceListGrid", "功能列表", "1",functionMenus.getId()));
//		functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/findResourceById", "功能详细信息", "1",functionMenus.getId()));
//		functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/addResourceUI", "添加功能", "1",functionMenus.getId()));
//		functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/editResourceUI","修改功能", "1",functionMenus.getId()));
//		functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/delete", "删去功能", "1",functionMenus.getId()));
//		resourceService.saves(functionResourceItem);
//		
//		//角色菜单
//		Menu roleMenus =new Menu("角色管理", "/restful/privilegeProxyService/role/roleListUI", privilegesMenu.getId(),"1");
//		menuService.saveOrUpdate(roleMenus);
//		List<Resource> roleResourceItem = new ArrayList<Resource>();
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/roleListUI", "角色列表", "1",roleMenus.getId()));
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/roleListGrid", "查询角色", "1",roleMenus.getId()));
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/addRoleUI", "添加角色", "1",roleMenus.getId()));
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/findRole", "添加修改界面", "1",roleMenus.getId()));
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/openDistributionUI", "分配角色", "1",roleMenus.getId()));
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/openDepartmentAuthorizeUI", "部门授权", "1",roleMenus.getId()));
//		
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/distributionResource", "分配资源", "1",roleMenus.getId()));
//		roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/delete", "删去角色", "1",roleMenus.getId()));
//		resourceService.saves(roleResourceItem);
//		
//		/**
//		 * 门户菜单
//		 */
//		Menu portalMenus =new Menu("门户管理", "", privilegesMenu.getId(),"","0");
//		menuService.saveOrUpdate(portalMenus);
//		List<Resource> portalResourceItem = new ArrayList<Resource>();
//		portalResourceItem.add(new Resource("/restful/portalProxyService/tradition/index", "传统模式", "1",portalMenus.getId()));
//		portalResourceItem.add(new Resource("/restful/portalProxyService/office/index", "office模式", "1",portalMenus.getId()));
//		portalResourceItem.add(new Resource("/restful/portalProxyService/win8/index", "win8模式", "1",portalMenus.getId()));
//		portalResourceItem.add(new Resource("/restful/privilegeProxyService/control/welcome", "返回首页", "1",portalMenus.getId()));
//		portalResourceItem.add(new Resource("/restful/privilegeProxyService/control/commonFunction", "常用功能", "1",portalMenus.getId()));
//		portalResourceItem.add(new Resource("/restful/privilegeProxyService/control/SysSwitch", "皮肤设置", "1",portalMenus.getId()));
//		resourceService.saves(portalResourceItem);
//		
//		/**
//		 * 初始化角色信息
//		 */
//		Role role = new Role();
//		role.setRoleName("超级管理员");
//		role.setCreateDate(new Date());
//		roleService.saveOrUpdate(role);
//		
//		/**
//		 * 初始化用户
//		 */
//		User user = new User();
//		user.setUserName("超级管理员");
//		user.setAccountName("admin");
//		user.setPassword("admin");
//		user.setCreateDate(new Date());
//		userService.saveOrUpdate(user);
//		
//		UserRole userRole = new UserRole();
//		userRole.setUserId(user.getId());
//		userRole.setRoleId(role.getId());
//		userRoleService.saveOrUpdate(userRole);
//		
//		// 接受参数类
//		 Map<String, Object> parms = new HashMap<String, Object>();
//		// 排序参数
//		 LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
//		SystemContext.setPagesize(Integer.MAX_VALUE);
//		List list= resourceService.queryResource(parms, orderby).getItems();
//		
//		for(int i=0;i<list.size();i++){
//			Map map = (Map)list.get(i);
//			RoleResource roleResource = new RoleResource();
//			roleResource.setResourceId(map.get("id").toString());
//			roleResource.setRoleId(role.getId());
//			roleResourceDAO.save(roleResource);
//		}
//		
//		
//		
//		
//		
////		
////		
////		privilegemenuItem.add(new Menu("功能管理", "/restful/privilegeProxyService/resource/resourceListUI", privilegesMenu.getId()));
////		
////		privilegemenuItem.add(new Menu("角色管理", "/restful/privilegeProxyService/role/roleListUI", privilegesMenu.getId()));
////		menuService.saves(privilegemenuItem);
////		//初始化报表菜单模块
////		Menu reprotMenu =new Menu("报表模块管理", "", "-1","../../../styles/css/portal/office/frame/images/menu_clock.png");
////		reprotMenu.setSortby("2");
////		menuService.saveOrUpdate(reprotMenu);
////		List<Menu> reprotMenuItem = new ArrayList<Menu>();
////		reprotMenuItem.add(new Menu("数据源管理", "/restful/reportProxyService/ds/index", reprotMenu.getId()));
////		reprotMenuItem.add(new Menu("数据集管理", "/restful/reportProxyService/dataset/index", reprotMenu.getId()));
////		reprotMenuItem.add(new Menu("控件管理", "/restful/reportProxyService/widget/index", reprotMenu.getId()));
////		reprotMenuItem.add(new Menu("报表设计", "/restful/reportProxyService/design/index", reprotMenu.getId()));
////		reprotMenuItem.add(new Menu("报表预览", "/restful/reportProxyService/preview/index", reprotMenu.getId()));
////		menuService.saves(reprotMenuItem);
//		msg.setData(roleResourceItem);
//		response.print(msg.toJSONObjectPresention());
//		
//		
//	}
	
}
