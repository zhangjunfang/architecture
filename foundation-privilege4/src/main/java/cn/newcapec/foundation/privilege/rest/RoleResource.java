package cn.newcapec.foundation.privilege.rest;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.MenuService;
import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.model.Role;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;

/**
 * 角色视图资源类
 * 
 * @author andy.li
 * 
 */
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class RoleResource extends PrivilegeResource {

	/* 角色业务类 */
	@Autowired
	private RoleService roleService;
	/* 菜单业务类 */
	@Autowired
	private MenuService menuService;
	/* 资源功能业务类 */
	@Autowired
	private ResourceService resourceService;
	/* 部门功能业务类 */
	@Autowired
	private DepartmentService departmentService;

	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();
	/* 参数类 */
	Map<String, Object> params = new HashMap<String, Object>();
	Map<String, Object> subparams = new HashMap<String, Object>();

	/**
	 * 查找角色信息
	 * 
	 * @param request
	 * @param response
	 */
	public void findRole(BaseRequest request, BaseResponse response) {

		String url = "/foundation/privilege/role/pagelet/v1.0/updateRoleUI.html";
		try {
			if (null != getJsonObject()) {
				String roleid = JSONTools.getString(getJsonObject(), "roleid");
				if (StringUtils.isNotBlank(roleid)) {
					Role role = roleService.get(roleid);
					if (null != role) {
                        getNewcapectViewContext().put("role", role);
						response.toView(url, getNewcapectViewContext());
					} else {
						throw new BaseException("没有相应的角色信息");
					}
				}
			}
		} catch (Exception e) {
			log.debug(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查找角色失败！", e);
			}
		}
	}

	/**
	 * 获取角色列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void roleListUI(BaseRequest request, BaseResponse response)  {
        try {
            String url = "/foundation/privilege/role/pagelet/v1.0/role_list.html";
            getNewcapectViewContext().put("menuName", "角色管理");
            roleListGrid(request, response);

            response.toView(url, getNewcapectViewContext());
        } catch (Exception e) {
        	log.error(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("系统出错！", e);
            }
        }
    }

	/**
	 * 获取角色列表信息
	 * 
	 * @param request
	 * @param response
	 */
	public void roleListGrid(BaseRequest request, BaseResponse response){
        try {
		String url = "/foundation/privilege/role/pagelet/v1.0/role_list_grid.html";
		/* 查询列表 */
		Page page = roleService.queryRoles(getJsonObject(), orderby);
		// 角色列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadRoleList");
        getNewcapectViewContext().put("pageView", pageView);

            response.toView(url, getNewcapectViewContext());
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("系统出错！", e);
            }
        }
    }

	/**
	 * 删去角色信息
	 * 
	 * @param request
	 * @param response
	 */
	public void delete(BaseRequest request, BaseResponse response) {
		Msg msg = new Msg();
		try {
			if (null != getJsonObject()) {
					String ids = JSONTools.getString(getJsonObject(), "roleids");
					if (StringUtils.isNotBlank(ids)) {
						String[] idss = ids.split(",");
						msg.setSuccess(true);
						msg.setMsg("删去角色成功！");
						msg.setSuccess(true);
						roleService.deleteRole(idss);
						clearCache();
						response.print(msg.toJSONObjectPresention());
					} else {
						msg.setMsg("删去角色失败！");
						msg.setSuccess(true);
						response.print(msg.toJSONObjectPresention());
					}

			}
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("删去角色失败！", e);
			}
		}
	}

	/**
	 * 添加角色UI
	 * 
	 * @param request
	 * @param response
	 */
	public void addRoleUI(BaseRequest request, BaseResponse response) throws Exception {
		String url = "/foundation/privilege/role/pagelet/v1.0/addRoleUI.html";
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 添加角色信息
	 * 
	 * @param request
	 * @param response
	 */
	public void add(BaseRequest request, BaseResponse response) {
//		MultiDatasourceContextHelper.setDatasource(MultiDatasourceContext.bizDataSourceCardMysql);
		try {
			Msg msg = new Msg();
			if (null != getJsonObject()) {
				JSONObject data = getJsonObject();
				Role role = JSONTools.JSONToBean(data, Role.class);
				if (null != role) {
					// 判断角色名称是否存在
					Role oldRole = roleService.findRoleByName(role
							.getRoleName());
					if (null != oldRole
							&& !oldRole.getId().equals(role.getId())) {
						throw new BaseException("角色名字已经存在，请重新输入！");
					}
					msg.setSuccess(true);
					msg.setMsg("添加角色成功！");
					roleService.saveOrUpdate(role);
					response.print(msg.toJSONObjectPresention());
				} else {
					msg.setMsg("添加角色失败！");
					response.print(msg.toJSONObjectPresention());

				}
			}
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("新增角色失败！", e);
			}
		}
	}

	/**
	 * 打开分配资源界面
	 * 
	 * @param request
	 * @param response
	 */
	public void openDistributionUI(BaseRequest request, BaseResponse response) {
		/* 参数类 */
		Map<String, Object> params = new HashMap<String, Object>();
		try {
			String url = "/foundation/privilege/role/pagelet/v1.0/distributionResourceUI.html";
			// 获取界面的reloid
			String roleid = JSONTools.getString(getJsonObject(), "roleid");
			PageContext.setPagesize(Integer.MAX_VALUE);
            getNewcapectViewContext().put("roleid", roleid);
			// 所有菜单
            getNewcapectViewContext().put("menus", menuService.queryMenu(params, orderby));
			// newcapectViewContext
            getNewcapectViewContext().put("functions", resourceService
                    .queryResource(params, null));
			// 获取已经授权的资源
            getNewcapectViewContext().put("functionselected", resourceService
                    .queryResourcesByRoleId(roleid));
			response.toView(url, getNewcapectViewContext());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("打开资源失败！", e);
			}
		}
	}

	/**
	 * 分配资源
	 * 
	 * @param request
	 * @param response
	 */
	public void distributionResource(BaseRequest request, BaseResponse response) {
		Msg msg = new Msg();
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				if (null != data) {
					// 获取角色标记
					String roleid = JSONTools.getString(data, "roleid");
					if (StringUtils.isNotBlank(roleid)) {
						String str = JSONTools.getString(data, "resourceids");
						if (StringUtils.isNotBlank(str)) {
							String[] resourceids = str.split(",");
							roleService.setRoleResource(roleid, resourceids);
							clearCache();
							msg.setSuccess(true);
							response.print(msg.toJSONObjectPresention());
						}
					}

				} else {
					msg.setMsg("分配资源失败！");
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			log.debug(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("分配资源失败！", e);
			}
		}
	}

	/**
	 * 打开部门授权界面
	 * 
	 * @param request
	 * @param response
	 */
	public void openDepartmentAuthorizeUI(BaseRequest request,
			BaseResponse response) {
		try {
			String url = "/foundation/privilege/role/pagelet/v1.0/departmentdAuthorizeUI.html";
			// 获取界面的角色编号reloid
			String roleid = JSONTools.getString(getJsonObject(), "roleid");
            getNewcapectViewContext().put("roleid", roleid);
			PageContext.setPagesize(Integer.MAX_VALUE);
			params.put("parent_id", "-1");
			// 获取顶层部门
            getNewcapectViewContext().put("department", departmentService.queryDepartments(
                    params, orderby));
			// 获取所有部门
			// subparams.put("subDep", "subDep");
            getNewcapectViewContext().put("departments", departmentService.queryDepartments(
                    subparams, orderby));
			// 获取部门下的所有权限
            getNewcapectViewContext().put("departmented", departmentService
                    .queryDepartmentByRoleId(roleid));
			response.toView(url, getNewcapectViewContext());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("打开授权部门失败！", e);
			}
		}
	}

	/**
	 * 部门授权
	 * 
	 * @param request
	 * @param response
	 */
	public void openDepartmentAuthorize(BaseRequest request,
			BaseResponse response) {
		Msg msg = new Msg();
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				if (null != data) {
					// 获取角色标记
					String roleid = JSONTools.getString(data, "roleid");
					if (StringUtils.isNotBlank(roleid)) {
						String str = JSONTools.getString(data, "departmentids");
						String[] departmentids = str.split(",");
						roleService.setRoleDepartments(roleid, departmentids);
						clearCache();
						msg.setSuccess(true);
						response.print(msg.toJSONObjectPresention());
					}

				} else {
					msg.setMsg("部门授权失败！");
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			System.out.println(ExceptionUtils.getFullStackTrace(e));
			log.debug(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("部门授权失败！", e);
			}
		}
	}

	/**
	 * 
	 * 请求资源初始化前置操作,目前不需要实现
	 * 
	 * @param context
	 * @param request
	 * @param response
	 */
	public void beforeHandle(Context context, Request request, Response response) {
		log.info("before......初始化资源之前");
	}

	/**
	 * 请求资源初始化后置操作,目前不需要实现
	 * 
	 * @param context
	 * @param request
	 * @param response
	 */
	public void afterHandle(Context context, Request request, Response response) {
		log.info("after......初始化资源之后");
	}

}
