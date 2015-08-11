package cn.newcapec.foundation.privilege.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.newcapec.foundation.privilege.biz.DepartmentRoleService;
import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.biz.UserRoleService;
import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.foundation.privilege.dao.RoleDAO;
import cn.newcapec.foundation.privilege.dao.RoleResourceDAO;
import cn.newcapec.foundation.privilege.model.DepartmentRole;
import cn.newcapec.foundation.privilege.model.Menu;
import cn.newcapec.foundation.privilege.model.Resource;
import cn.newcapec.foundation.privilege.model.Role;
import cn.newcapec.foundation.privilege.model.RoleResource;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.foundation.privilege.model.UserRole;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.logs.LogEnabled;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;;

/**
 * 角色接口业务实现类
 *
 * @author andy.li
 */

@Service("roleService")
@Transactional
@SuppressWarnings("all")
public class RoleServiceImpl implements RoleService, LogEnabled {

    /* 角色接口类 */
    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    DepartmentRoleService departmentRoleService;

    @Autowired
    MenuService menuService;
    @Autowired
    ResourceService resourceService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    UserService userService;
    @Autowired
    RoleResourceDAO roleResourceDAO;
    @Autowired
    UserRoleService userRoleService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Role get(String id) {
        return roleDAO.get(id);
    }

    @Override
    public void removeUnused(String id) {
        roleDAO.delete(id);
    }


    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Page queryRoles(Map<String, Object> params,
                           LinkedHashMap<String, String> orderby) {
        Page page = roleDAO.queryRoles(params, orderby);
        if (null != page) {
            return page;
        }
        return null;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public Role findRoleByName(String name) {
        Role role = roleDAO.findRoleByName(name);
        if (null != role) {
            return role;
        }
        return null;
    }

    @Override
    public void deleteRole(String[] ids) {
        if (null != ids && ids.length > 0) {
            /* 同时删去关联的资源 */
            for (int i = 0; i < ids.length; i++) {
                roleDAO.deleteResourceByRoleId(ids[i]);
            }
            roleDAO.deleteRole(ids);
        }
    }

    @Override
    public void setRoleResource(String role, String[] resoruceids) {
        if (StringUtils.isNotBlank(role) && null != resoruceids
                && resoruceids.length > 0) {
            /* 赋值新的资源，首先要删去原来资源 */
            roleDAO.deleteResourceByRoleId(role);
            roleDAO.setRoleResource(role, resoruceids);
        }
    }

    @Override
    public void deleteResourceByRoleId(String roleid) {
        if (StringUtils.isNotBlank(roleid)) {
            roleDAO.deleteResourceByRoleId(roleid);
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public List queryRoleByDepartmentId(String departmentid) {
        List list = roleDAO.queryRoleByDepartmentId(departmentid);
        if (null != list && list.size() > 0) {
            return list;
        }
        return null;
    }

    @Override
    public void setRoleDepartments(String roleid, String[] departmentids) {
        if (StringUtils.isNotBlank(roleid)) {
            // 同时删去关联资源
            departmentRoleService.deleteDepartmentRoleByRoleId(roleid);
            if (null != departmentids && departmentids.length > 0) {
                for (int i = 0; i < departmentids.length; i++) {
                    DepartmentRole dt = new DepartmentRole();
                    dt.setCreateDate(new Date());
                    dt.setDepartmentid(departmentids[i]);
                    dt.setRoleid(roleid);
                    departmentRoleService.saveOrUpdate(dt);
                }
            }

        }

    }

    @Override
    public List<Map<String, Object>> queryPrivilesByUserId(String usreid) {
        return roleDAO.queryPrivilesByUserId(usreid);
    }

    @Override
    public void SystemInit() {
        try {

            //初始化权限菜单
            Menu privilegesMenu = new Menu("权限模块管理", "", "-1", "../../../styles/css/portal/office/frame/images/menu_person.png", "1");
            privilegesMenu.setSortby("1");
            menuService.saveOrUpdate(privilegesMenu);
            //用户管理菜单
            Menu userMenu = new Menu("人员管理", "/restful/privilegeProxyService/user/userListUI", privilegesMenu.getId(), "1");
            menuService.saveOrUpdate(userMenu);
            List<Resource> userResourceItem = new ArrayList<Resource>();
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/userListUI", "用户列表", "1", userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/userListGrid", "用户查询", "1", userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/addUserUI", "添加用户", "1", userMenu.getId()));
//			userResourceItem.add(new Resource("/restful/privilegeProxyService/user/addUser", "添加用户", "1",userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/updateUserUI", "修改用户", "1", userMenu.getId()));
//			userResourceItem.add(new Resource("/restful/privilegeProxyService/user/updateUser", "修改用户", "1",userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/delete", "删去用户", "1", userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/distributionResource", "分配角色", "1", userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/openDistributionUI", "分配角色界面", "1", userMenu.getId()));
            userResourceItem.add(new Resource("/restful/privilegeProxyService/user/logonOut", "用户退出", "1", userMenu.getId()));
            resourceService.saves(userResourceItem);

            //部门管理菜单
            Menu departmentMenu = new Menu("部门管理", "/restful/privilegeProxyService/department/departmentListUI", privilegesMenu.getId(), "1");
            menuService.saveOrUpdate(departmentMenu);
            List<Resource> departmentResourceItem = new ArrayList<Resource>();
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/departmentListUI", "部门列表界面", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/departmentListGrid", "部门列表", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/departmentTree", "查询部门树", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/addDepartmentUI", "添加部门", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/findDepartment", "更新部门界面", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/updateDepartment", "更新部门", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/updateDepartmentUI", "修改部门", "1", departmentMenu.getId()));
            departmentResourceItem.add(new Resource("/restful/privilegeProxyService/department/deleteDepartment", "删去部门", "1", departmentMenu.getId()));
            resourceService.saves(departmentResourceItem);

            //菜单管理
            Menu menus = new Menu("菜单管理", "/restful/privilegeProxyService/menu/menuListUI", privilegesMenu.getId(), "1");
            menuService.saveOrUpdate(menus);
            List<Resource> menusResourceItem = new ArrayList<Resource>();
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/menuListUI", "菜单列表界面", "1", menus.getId()));
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/menuListGrid", "菜单列表", "1", menus.getId()));
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/menuTree", "菜单树列表", "1", menus.getId()));
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/selectById", "选择菜单", "1", menus.getId()));
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/addMenuUI", "添加菜单", "1", menus.getId()));
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/editMenuUI", "修改菜单", "1", menus.getId()));
            menusResourceItem.add(new Resource("/restful/privilegeProxyService/menu/delete", "删去菜单", "1", menus.getId()));
            resourceService.saves(menusResourceItem);

            //功能菜单
            Menu functionMenus = new Menu("功能管理", "/restful/privilegeProxyService/resource/resourceListUI", privilegesMenu.getId(), "1");
            menuService.saveOrUpdate(functionMenus);
            List<Resource> functionResourceItem = new ArrayList<Resource>();
            functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/resourceListUI", "功能列表界面", "1", functionMenus.getId()));
            functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/resourceListGrid", "功能列表", "1", functionMenus.getId()));
            functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/findResourceById", "功能详细信息", "1", functionMenus.getId()));
            functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/addResourceUI", "添加功能", "1", functionMenus.getId()));
            functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/editResourceUI", "修改功能", "1", functionMenus.getId()));
            functionResourceItem.add(new Resource("/restful/privilegeProxyService/resource/delete", "删去功能", "1", functionMenus.getId()));
            resourceService.saves(functionResourceItem);

            //角色菜单
            Menu roleMenus = new Menu("角色管理", "/restful/privilegeProxyService/role/roleListUI", privilegesMenu.getId(), "1");
            menuService.saveOrUpdate(roleMenus);
            List<Resource> roleResourceItem = new ArrayList<Resource>();
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/roleListUI", "角色列表", "1", roleMenus.getId()));
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/roleListGrid", "查询角色", "1", roleMenus.getId()));
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/addRoleUI", "添加角色", "1", roleMenus.getId()));
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/findRole", "添加修改界面", "1", roleMenus.getId()));
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/openDistributionUI", "分配角色", "1", roleMenus.getId()));
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/openDepartmentAuthorizeUI", "部门授权", "1", roleMenus.getId()));

            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/distributionResource", "分配资源", "1", roleMenus.getId()));
            roleResourceItem.add(new Resource("/restful/privilegeProxyService/role/delete", "删去角色", "1", roleMenus.getId()));
            resourceService.saves(roleResourceItem);

            /**
             * 门户菜单
             */
            Menu portalMenus = new Menu("门户管理", "", privilegesMenu.getId(), "", "0");
            menuService.saveOrUpdate(portalMenus);
            List<Resource> portalResourceItem = new ArrayList<Resource>();
            portalResourceItem.add(new Resource("/restful/portalProxyService/tradition/index", "传统模式", "1", portalMenus.getId()));
            portalResourceItem.add(new Resource("/restful/portalProxyService/office/index", "office模式", "1", portalMenus.getId()));
            portalResourceItem.add(new Resource("/restful/portalProxyService/win8/index", "win8模式", "1", portalMenus.getId()));
            portalResourceItem.add(new Resource("/restful/privilegeProxyService/control/welcome", "返回首页", "1", portalMenus.getId()));
            portalResourceItem.add(new Resource("/restful/privilegeProxyService/control/commonFunction", "常用功能", "1", portalMenus.getId()));
            portalResourceItem.add(new Resource("/restful/privilegeProxyService/control/SysSwitch", "皮肤设置", "1", portalMenus.getId()));
            resourceService.saves(portalResourceItem);

            /**
             * 初始化角色信息
             */
            Role role = new Role();
            role.setRoleName("超级管理员");
            role.setCreateDate(new Date());
            roleDAO.saveOrUpdate(role);

            /**
             * 初始化用户
             */
            User user = new User();
            user.setUserName("超级管理员");
            user.setAccountName("admin");
            user.setPassword("admin");
            user.setCreateDate(new Date());
            userService.saveOrUpdate(user);

            UserRole userRole = new UserRole();
            userRole.setUserId(user.getId());
            userRole.setRoleId(role.getId());
            userRoleService.saveOrUpdate(userRole);

            // 接受参数类
            Map<String, Object> parms = new HashMap<String, Object>();
            // 排序参数
            LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
            PageContext.setPagesize(Integer.MAX_VALUE);
            List list = resourceService.queryResource(parms, orderby).getItems();

            for (int i = 0; i < list.size(); i++) {
                Map map = (Map) list.get(i);
                RoleResource roleResource = new RoleResource();
                roleResource.setResourceId(map.get("ID").toString());
                roleResource.setRoleId(role.getId());
                roleResourceDAO.save(roleResource);
            }


//			
//			
//			privilegemenuItem.add(new Menu("功能管理", "/restful/privilegeProxyService/resource/resourceListUI", privilegesMenu.getId()));
//			
//			privilegemenuItem.add(new Menu("角色管理", "/restful/privilegeProxyService/role/roleListUI", privilegesMenu.getId()));
//			menuService.saves(privilegemenuItem);
//			//初始化报表菜单模块
//			Menu reprotMenu =new Menu("报表模块管理", "", "-1","../../../styles/css/portal/office/frame/images/menu_clock.png");
//			reprotMenu.setSortby("2");
//			menuService.saveOrUpdate(reprotMenu);
//			List<Menu> reprotMenuItem = new ArrayList<Menu>();
//			reprotMenuItem.add(new Menu("数据源管理", "/restful/reportProxyService/ds/index", reprotMenu.getId()));
//			reprotMenuItem.add(new Menu("数据集管理", "/restful/reportProxyService/dataset/index", reprotMenu.getId()));
//			reprotMenuItem.add(new Menu("控件管理", "/restful/reportProxyService/widget/index", reprotMenu.getId()));
//			reprotMenuItem.add(new Menu("报表设计", "/restful/reportProxyService/design/index", reprotMenu.getId()));
//			reprotMenuItem.add(new Menu("报表预览", "/restful/reportProxyService/preview/index", reprotMenu.getId()));
//			menuService.saves(reprotMenuItem);

        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("用户登录失败！", e);
            }
        }
    }

    @Override
    public void saveOrUpdate(Role entity) {
        // TODO Auto-generated method stub
        roleDAO.saveOrUpdate(entity);

    }

}
