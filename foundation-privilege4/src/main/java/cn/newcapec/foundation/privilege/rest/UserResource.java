package cn.newcapec.foundation.privilege.rest;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.RoleService;
import cn.newcapec.foundation.privilege.biz.UserRoleService;
import cn.newcapec.foundation.privilege.biz.UserService;
import cn.newcapec.foundation.privilege.model.User;
import cn.newcapec.foundation.privilege.utils.UserTool;
import cn.newcapec.framework.core.context.HttpNewcapecContextFactory;
import cn.newcapec.framework.core.context.Keys;
import cn.newcapec.framework.core.context.NewcapecContext;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.httpUtils.WebUtils;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;

/**
 * 用户视图资源类
 *
 * @author andy.li
 */
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class UserResource extends PrivilegeResource {
	

    /* 角色业务类 */
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private DepartmentService departmentService;

    // 接受参数类
    private Map<String, Object> parms = new HashMap<String, Object>();
    // 排序参数
    private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();

    /**
     * 用户登录UI
     *
     * @param request
     * @param response
     */
    public void loginUI(BaseRequest request, BaseResponse response) {
            String url = "/foundation/privilege/user/pagelet/v1.0/loginUI.html";
            response.toView(url, getNewcapectViewContext());
    }


    /**
     * 用户登录
     *
     * @param request
     * @param response
     */
    public void login(BaseRequest request, BaseResponse response) {
        try {
            Msg msg = new Msg();
//            //企业编号
//            String tenantCode = JSONTools.getString(getJsonObject(), "tenantCode");
            // 名称
            String name = JSONTools.getString(getJsonObject(), "name");
            // 密码
            String password = JSONTools.getString(getJsonObject(), "password");
            HttpSession session = WebUtils.getSession(request.getOrginRequest());
            //获得验证码
//	        String strEnsure = (String)session.getAttribute("validateCode");
//	        if(!strEnsure.equals( JSONTools.getString(getJsonObject(), "validateCode"))){
//	        	throw new BaseException("验证码不正确！");
//	        }

            if (StringUtils.isNotBlank(name)) {
                User user = userService.findUserByName(name);
                if (StringUtils.isNotBlank(password)) {
                    if (user != null) {
                        if (user.getPassword().equals(password)) {
                            //将登陆用户放到上下文里面
                            session.setAttribute(Keys.USER, UserTool.Uer2LoginUser(user));
                            NewcapecContext context = HttpNewcapecContextFactory.getContext(WebUtils.getRequests(request.getOrginRequest()));
                            NewcapecContext.registerContext(context);
                            WebUtils.getResponse(request.getOrginRequest()).sendRedirect("/restful/portalProxyService/tradition/index");
                            return;

                        } else {
                            throw new BaseException("用户密码不正确！");
                        }
                    } else {
                        throw new BaseException("用户名称不存在！");
                    }

                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("用户登录失败！", e);
            }
        }

    }


    /**
     * 获取角色列表信息
     *
     * @param request
     * @param response
     */
    public void userListUI(BaseRequest request, BaseResponse response) {
            String url = "foundation/privilege/user/pagelet/v1.0/user_list.html";
            userListGrid(request, response);
            response.toView(url, getNewcapectViewContext());
       
    }

    /**
     * 获取用户列表信息
     *
     * @param request
     * @param response
     */
    public void userListGrid(BaseRequest request, BaseResponse response) {
            String url = "/foundation/privilege/user/pagelet/v1.0/user_list_grid.html";
        /* 查询列表 */
            Page page = userService.queryUsers(getJsonObject(), orderby);
            // 角色列表视图
            PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
                    10, PageContext.getOffset());
            pageView.setQueryResult(page);
            pageView.setJsMethod("reloadUserList");
            getNewcapectViewContext().put("pageView", pageView);
            response.toView(url, getNewcapectViewContext());
    }

    /**
     * 添加用户UI
     *
     * @param request
     * @param response
     */
    public void addUserUI(BaseRequest request, BaseResponse response) {
        String url = "/foundation/privilege/user/pagelet/v1.0/addUserUI.html";
        PageContext.setPagesize(Integer.MAX_VALUE);
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> subparams = new HashMap<String, Object>();
        params.put("parent_id", "-1");
        // 获取顶层部门
        getNewcapectViewContext().put("department", departmentService.queryDepartments(params,
                orderby));
        // 获取所有部门
        getNewcapectViewContext().put("departments", departmentService.queryDepartments(
                subparams, orderby));
        response.toView(url, getNewcapectViewContext());
    }

    /**
     * 添加用户
     *
     * @param request
     * @param response
     */
    public void addUser(BaseRequest request, BaseResponse response) {
            if (null != getJsonObject()) {
                String departmentids = JSONTools.getString(getJsonObject(),
                        "departmentids");
                if (StringUtils.isNotBlank(departmentids)) {
                    String[] departments = departmentids.split(",");
                    if (null != departments && departments.length > 0) {

                        JSONObject data = getJsonObject();
                        User user = JSONTools.JSONToBean(getJsonObject(), User.class);
                        if (null != user) {
                            // 判断用户账号是否存在
                            if (null != userService.findUserByName(user
                                    .getAccountName())) {
                                throw new BaseException("用户账号已经存在，请重新输入！");
                            } else {
                                Msg msg = new Msg();
                                msg.setSuccess(true);
                                user.setCreateDate(new Date());
                                userService.saveCascadeUser(user, departments);
                                response.print(msg.toJSONObjectPresention());
                            }
                        }

                    }

                } else {
                    throw new BaseException("请选择部门！");
                }
            }
    }

    /**
     * 删去人员信息
     *
     * @param request
     * @param response
     */
    public void delete(BaseRequest request, BaseResponse response) {
        try {
            if (null != getJsonObject()) {
                    String ids = JSONTools.getString(getJsonObject(), "userids");
                    if (StringUtils.isNotBlank(ids)) {
                        String[] idss = ids.split(",");
                        Msg msg = new Msg();
                        msg.setMsg("删去人员成功！");
                        msg.setSuccess(true);
                        userService.deleteCascadeUser(idss);
                        response.print(msg.toJSONObjectPresention());
                    }
            }
        } catch (Exception e) {
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("删去人员失败！", e);
            }
        }
    }

    /**
     * 更新用户UI
     *
     * @param request
     * @param response
     */
    public void updateUserUI(BaseRequest request, BaseResponse response) {
        String url = "/foundation/privilege/user/pagelet/v1.0/updateUserUI.html";
        try {
            if (null != getJsonObject()) {
                String userId = JSONTools.getString(getJsonObject(), "userid");
                if (StringUtils.isNotBlank(userId)) {
                    User user = userService.get(userId);
                    if (null != user) {
                        getNewcapectViewContext().put("user", user);
                        PageContext.setPagesize(Integer.MAX_VALUE);
                        Map<String, Object> params = new HashMap<String, Object>();
                        Map<String, Object> subparams = new HashMap<String, Object>();
                        params.put("parent_id", "-1");
                        // 获取顶层部门
                        getNewcapectViewContext().put("department", departmentService
                                .queryDepartments(params, orderby));
                        // 获取所有部门
                        getNewcapectViewContext().put("departments", departmentService
                                .queryDepartments(subparams, orderby));
                        // 获取人员的全部部门
                        getNewcapectViewContext().put("departmented", departmentService
                                .queryDepartmentByUserId(userId));
                        response.toView(url, getNewcapectViewContext());
                    } else {
                        throw new BaseException("没有相应的人员信息");
                    }
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("查找人员失败！", e);
            }
        }
    }

    /**
     * 更新用户
     *
     * @param request
     * @param response
     */
    public void updateUser(BaseRequest request, BaseResponse response) {
        try {
            if (null != getJsonObject()) {
                String id = JSONTools.getString(getJsonObject(), "id");
//				User user = userService.get(id);
                User user = JSONTools.JSONToBean(getJsonObject(), User.class);
                if (null != user) {
                    // 判断用户账号是否存在
                    Msg msg = new Msg();
                    msg.setSuccess(true);
                    // user.setCreateDate(new Date());
                    userService.saveOrUpdate(user);
                    response.print(msg.toJSONObjectPresention());
                }
            }
        } catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("新增用户失败！", e);
            }
        }
    }

    /**
     * 打开分配角色界面
     *
     * @param request
     * @param response
     */
    public void openDistributionUI(BaseRequest request, BaseResponse response) {
		/* 参数类 */
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> userParams = new HashMap<String, Object>();
        String url = "/foundation/privilege/user/pagelet/v1.0/distributionUserRoleUI.html";
        // 获取界面的reloid
        String userid = JSONTools.getString(getJsonObject(), "userid");
        getNewcapectViewContext().put("userid", userid);
        PageContext.setPagesize(Integer.MAX_VALUE); // 不分页
        getNewcapectViewContext().put("roles", roleService.queryRoles(params, orderby));
        userParams.put("userid", userid);
        getNewcapectViewContext().put("hasRoles", userRoleService.queryUserRoles(userParams,
                orderby));
        response.toView(url, getNewcapectViewContext());
    }

    /**
     * 分配角色
     *
     * @param request
     * @param response
     */
    public void distributionResource(BaseRequest request, BaseResponse response) {
        Msg msg = new Msg();
        try {
            if (null != getJsonObject())  {
                    // 获取角色标记
                    String userid = JSONTools.getString(getJsonObject(), "userid");
                    if (StringUtils.isNotBlank(userid)) {
                        String str = JSONTools.getString(getJsonObject(), "roleids");
                        if (StringUtils.isNotBlank(str)) {
                            String[] roleids = str.split(",");
                            userService.setUsersAuthorize(roleids, userid);
                            clearCache();
                            msg.setSuccess(true);
                            response.print(msg.toJSONObjectPresention());
                        }
                    }

                } else {
                    msg.setMsg("分配角色失败！");
                    response.print(msg.toJSONObjectPresention());
                }
        } catch (Exception e) {
            log.debug(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
                throw (BaseException) e;
            } else {
                throw new BaseException("分配角色失败！", e);
            }
        }
    }


    /**
     * 退出
     *
     * @param request
     * @param response
     */
    public void logonOut(BaseRequest request, BaseResponse response) {
        NewcapecContext context = HttpNewcapecContextFactory.getContext(WebUtils.getRequests(request.getOrginRequest()));
//		User user =(User)context.getAttribute(5, Keys.USER);
//		clearCache();		
        context.removeAttribute(5, Keys.USER);
        NewcapecContext.unregisterContext();
        PrintWriter printWriter;
        try {
            printWriter = WebUtils.getResponse(request.getOrginRequest()).getWriter();
            String contextPath  = this.getContextPath();
            printWriter.write("<script type='text/javascript'>window.parent.location='"+contextPath+"/restful/privilegeProxyService/user/loginUI'</script>");
            printWriter.close();
        } catch (Exception e) {
            ThrowsException(e, "退出失败！");
        }
    }


}
