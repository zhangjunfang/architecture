/**
 *
 */
package cn.newcapec.function.platform.biz.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.newcapec.framework.core.dao.db.SqlDataset;
import cn.newcapec.function.platform.biz.Win8UIService;
import cn.newcapec.function.platform.common.Constant;
import cn.newcapec.function.platform.model.BusinessAppredit;
import cn.newcapec.function.platform.model.Menu;
import cn.newcapec.function.platform.model.Module;
import cn.newcapec.function.platform.model.Role;
import cn.newcapec.function.platform.model.RoleGroup;
import cn.newcapec.function.platform.model.RoleMenu;
import cn.newcapec.function.platform.model.User;
import cn.newcapec.function.platform.tree.model.AssistTree;
import cn.newcapec.function.platform.tree.model.Tree;

/**
 * @author ocean
 * @date : 2014-4-22 上午11:14:57
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Service()
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional()
public class Win8UIServiceImpl implements Win8UIService, Serializable {

	private static final long serialVersionUID = 681398034096834898L;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Module> findAllModule(User user) {
		// 1.菜单
		// 2.应用模块
		// 33.模块菜单
		// 3.查询 角色菜单
		// 4.角色
		// 5.角色组
		// 5:
		if (null==user) {
			return null;
		}
		List<RoleGroup> groups = findRoleByUser(user);
		// 4:
		List<Role> roles = findRolebyRole(groups, user.getCustomerunitcode());
		// 3:
		List<RoleMenu> roleMenus = findMenuByRole(roles,
				user.getCustomerunitcode());
		// 33:
		List<Menu> menus = findMenuByRoleMenu(roleMenus);
		// 2:
		List<Module> modules = findModuleByMenu(menus);

		return modules;

	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> findAllMenu(User user) {
		// 1.菜单
		// 2.应用模块
		// 33.模块菜单
		// 3.查询 角色菜单
		// 4.角色
		// 5.角色组
		// 5:
		List<RoleGroup> groups = findRoleByUser(user);
		// 4:
		List<Role> roles = findRolebyRole(groups, user.getCustomerunitcode());
		// 3:
		List<RoleMenu> roleMenus = findMenuByRole(roles,
				user.getCustomerunitcode());
		// 33:
		List<Menu> menus = findMenuByRoleMenu(roleMenus);
		// 2:
		// List<Module> modules = findModuleByMenu(menus);
		return menus;

	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Module> findAllModule() {
		StringBuffer buffer = new StringBuffer(100);
		buffer.append(" select * from base_app_module ");
		SqlDataset dataset = new SqlDataset();
		dataset.setSql(buffer.toString());
		dataset.setClazz(Module.class);
		dataset.loadData();
		return dataset.getData();

	}

	/**
	 * @param menus
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Module> findModuleByMenu(List<Menu> menus) {
		if (null == menus || 0 == menus.size()) {
			return null;
		} else {
			StringBuffer buffer = new StringBuffer(100);
			buffer.append(" select * from base_app_module  where moduleid  in (");
			int i = 0;
			for (Menu menu : menus) {
				buffer.append("'");
				buffer.append(menu.getSubsystemid());
				buffer.append("'");
				if (i != menus.size() - 1) {
					buffer.append(",");
				}
				i++;
			}
			buffer.append(")");
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(Module.class);
			dataset.loadData();
			return dataset.getData();
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> findAllMenu() {
		SqlDataset dataset = new SqlDataset();
		dataset.setSql("  select * from base_menu   ");
		dataset.setClazz(Menu.class);
		dataset.loadData();
		return dataset.getData();
	}

	// 查询角色组
	@SuppressWarnings("unchecked")
	private List<RoleGroup> findRoleByUser(User user) {
		if (null == user || StringUtils.isEmpty(user.getId())) {
			return null;
		} else {
			SqlDataset dataset = new SqlDataset();
			StringBuffer buffer = new StringBuffer(60);
			buffer.append(" select  g.* from  base_role_group  g    where g.id=");
			buffer.append("'");
			buffer.append(user.getRoleid());
			buffer.append("'");
			dataset.setSql(buffer.toString());
			dataset.setClazz(RoleGroup.class);
			dataset.loadData();
			return dataset.getData();
		}
	}

	// 查询角色
	@SuppressWarnings("unchecked")
	private List<Role> findRolebyRole(List<RoleGroup> groups,
			String customerunitcode) {

		if (null == groups || 0 == groups.size()) {
			return null;
		} else {
			StringBuffer buffer = new StringBuffer(60);
			buffer.append(" select  r.* from  base_role r where r.id in (");
			int i = 0;
			for (RoleGroup roleGroup : groups) {
				String[] temp = roleGroup.getRoleids().split(",");
				for (int j = 0; j < temp.length; j++) {
					buffer.append("'");
					buffer.append(temp[j]);
					buffer.append("'");
					if (j != temp.length - 1) {
						buffer.append(",");
					}
				}
				if (groups.size()>1&& i!=groups.size()-1) {
					buffer.append(",");
				}
				i++;
			}
			buffer.append(")");
			buffer.append("  and r.customerunitcode=");
			buffer.append("'");
			buffer.append(customerunitcode);
			buffer.append("'");
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(Role.class);
			dataset.loadData();
			return dataset.getData();

		}
	}

	// 查询 角色菜单
	@SuppressWarnings("unchecked")
	private List<RoleMenu> findMenuByRole(List<Role> roles,
			String customerunitcode) {
		if (null == roles || 0 == roles.size()) {
			return null;
		} else {
			StringBuffer buffer = new StringBuffer(60);
			buffer.append(" select  r.* from  base_role_menu r where r.roleid in (");
			int i = 0;
			for (Role role : roles) {
				buffer.append("'");
				buffer.append(role.getId());
				buffer.append("'");
				if (i != roles.size() - 1) {
					buffer.append(",");
				}
				i++;
			}
			buffer.append(")");
			buffer.append("  and r.customerunitcode=");
			buffer.append("'");
			buffer.append(customerunitcode);
			buffer.append("'");
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(RoleMenu.class);
			dataset.loadData();
			return dataset.getData();

		}
	}

	// 查询 菜单模块
	@SuppressWarnings("unchecked")
	private List<Menu> findMenuByRoleMenu(List<RoleMenu> roleMenus) {
		if (null == roleMenus || 0 == roleMenus.size()) {
			return null;
		} else {

			StringBuffer buffer = new StringBuffer(120);
			buffer.append(" select * from base_menu where menuid in(");
			int i = 0;
			for (RoleMenu roleMenu : roleMenus) {
				buffer.append("'");
				buffer.append(roleMenu.getMenuid());
				buffer.append("'");
				if (i != roleMenus.size() - 1) {
					buffer.append(",");
				}
				i++;
			}
			buffer.append(")");
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(Menu.class);
			dataset.loadData();
			return dataset.getData();

		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Object> showTree() {
		List<Object> objects = new ArrayList<Object>(800);// 管理这个树节点
		List<Menu> menus = this.findAllMenu();
		List<Module> modules = this.findAllModule();
		AssistTree assistTree = null;// 根节点
		for (Module module : modules) {
			assistTree = new AssistTree();
			// assistTree.setParent(null);//设置一级菜单的父节点是 null
			assistTree.setpModuleId("0");
			copyModuleToAssistTree(module, assistTree);
			AssistTree temp = null;
			for (Menu menu : menus) {
				if (menu.getSubsystemid().equals(module.getModuleid())) {
					temp = new AssistTree();
					copyMenuToAssistTree(menu, temp);// 添加二级菜单
					List<Object> list = new ArrayList<Object>(20);
					list.add(temp);
					if (Constant.TREE_ROOT_SIGN.equals(menu.getParentmenuid())) {
						// temp.setParent(assistTree);//设置二级菜单的父节点是 上级菜单
						temp.setpModuleId(assistTree.getpModuleId());// 设置二级菜单的父节点是
						assistTree.getChildren().add(temp);// 添加二级菜单 // 上级菜单
					} else {
						Tree tree = new Tree();
						copyMenuToTree(tree, menu);
						temp.getChildren().add(tree);// 添加三级节点
						assistTree.getChildren().add(temp);// 添加二级菜单[添加三级菜单的父菜单]
					}
				}
			}
			objects.add(assistTree);// 一级菜单
		}
		return objects;
	}

	private void copyModuleToAssistTree(Module module, AssistTree assistTree) {
		if (module == null) {
			return;
		}
		assistTree.setModuleId(module.getModuleid());
		assistTree.setModuleName(module.getModulename());
	}

	private void copyMenuToAssistTree(Menu menu, AssistTree assistTree) {
		if (menu == null) {
			return;
		}
		assistTree.setModuleId(menu.getMenuid());
		assistTree.setModuleName(menu.getMenuname());
	}

	private void copyMenuToTree(Tree tree, Menu menu) {
		if (menu == null) {
			return;
		}
		tree.setFile(menu.getNavlink());
		tree.setId(menu.getMenuid());
		tree.setName(menu.getMenuname());
		tree.setpId(menu.getParentmenuid());
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Tree> showCusTree() {

		List<Tree> trees = new ArrayList<Tree>(200);
		List<BusinessAppredit> appredits = findStandardBusinessAppredit();
		changBusinessAppreditToTree(appredits, trees);
		List<Module> modules = findModuleByStandard(appredits);
		changModuleToTree(modules, trees);
		List<Menu> menus = findMenuByStandard(modules);
		changMenuToTree(menus, trees);
		return trees;
	}

	/**
	 * @param menus
	 * @param trees
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private void changMenuToTree(List<Menu> menus, List<Tree> trees) {
		if (null == menus || menus.size() == 0) {
			return;
		} else {
			Tree subTree = null;
			for (Menu menu : menus) {
				subTree = new Tree(menu.getMenuid(), menu.getSubsystemid(),
						menu.getMenuname(), "", null);
				trees.add(subTree);
			}
		}

	}

	/**
	 * @param modules
	 * @param trees
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private void changModuleToTree(List<Module> modules, List<Tree> trees) {
		if (null == modules || modules.size() == 0) {
			return;
		} else {

			Tree subTree = null;
			for (Module module : modules) {
				subTree = new Tree(module.getModuleid(), module.getAppid(),
						module.getModulename(), "", null);
				trees.add(subTree);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<BusinessAppredit> findStandardBusinessAppredit() {
		StringBuffer buffer = new StringBuffer(60);
		buffer.append("  select * from base_app_redit  where apptype=");
		buffer.append("'");
		buffer.append(Constant.APPTYPE_APP);
		buffer.append("'");
		SqlDataset dataset = new SqlDataset();
		dataset.setSql(buffer.toString());
		dataset.setClazz(BusinessAppredit.class);
		dataset.loadData();
		return dataset.getData();
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private List<Module> findModuleByStandard(List<BusinessAppredit> appredits) {

		if (null == appredits || appredits.size() == 0) {
			return null;
		} else {
			StringBuffer buffer = new StringBuffer(200);
			buffer.append("  select rel.appid,rel.moduleid,mm.id,mm.modulecode,mm.reditedcode,mm.modulename,mm.limitnum,mm.limitdt,mm.reditdt,mm.reditasn,mm.description,mm.apptype,mm.opdt,mm.syscode,mm.sortid from  base_appmodule  rel  right join  base_app_module  mm  on  rel.moduleid=mm.moduleid  ");
			int i = 0;
			buffer.append(" where rel.appid in ( ");
			for (BusinessAppredit businessAppredit : appredits) {
				buffer.append("'");
				buffer.append(businessAppredit.getAppid());
				buffer.append("'");
				if (i != appredits.size() - 1) {
					buffer.append(" , ");
				}
				i++;
			}
			buffer.append(" ) ");
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(Module.class);
			dataset.loadData();
			return dataset.getData();
		}
	}

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private List<Menu> findMenuByStandard(List<Module> modules) {
		if (null == modules || modules.size() == 0) {
			return null;
		} else {
			StringBuffer buffer = new StringBuffer(200);
			buffer.append("   SELECT * FROM base_menu mm  ");
			int i = 0;
			buffer.append(" where mm.subsystemid in ( ");
			for (Module module : modules) {
				buffer.append(" ' ");
				buffer.append(module.getModuleid());
				buffer.append(" ' ");
				if (i != modules.size() - 1) {
					buffer.append(" , ");
				}
				i++;
			}
			buffer.append(" ) ");
			SqlDataset dataset = new SqlDataset();
			dataset.setSql(buffer.toString());
			dataset.setClazz(Menu.class);
			dataset.loadData();
			return dataset.getData();
		}

	}

	/**
	 * @param findStandardBusinessAppredit
	 * @param trees
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private void changBusinessAppreditToTree(
			List<BusinessAppredit> findStandardBusinessAppredit,
			List<Tree> trees) {
		if (null == findStandardBusinessAppredit
				|| findStandardBusinessAppredit.size() == 0) {
			return;
		} else {
			// Tree tree = new Tree("QQQQQQQQ", Constant.TREE_ROOT_SIGN,
			// "应用包信息列表", "", null);
			// trees.add(tree);
			Tree subTree = null;
			for (BusinessAppredit appredit : findStandardBusinessAppredit) {
				// subTree = new Tree(appredit.getAppid(),
				// tree.getId(),appredit.getAppname(), "", null);
				subTree = new Tree(appredit.getAppid(),
						Constant.TREE_ROOT_SIGN, appredit.getAppname(), "",
						null);
				trees.add(subTree);
			}
		}
	}
}
