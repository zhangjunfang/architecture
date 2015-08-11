/**
 * @Title:
 * @Package cn.newcapec.function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
package cn.newcapec.function.platform.controller;

import java.util.Date;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.velocity.tools.generic.DateTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.exception.asserts.Assert;
import cn.newcapec.framework.core.exception.asserts.AssertObject;
import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.dataUtils.DateUtil;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.function.platform.biz.RoleGroupService;
import cn.newcapec.function.platform.biz.RoleService;
import cn.newcapec.function.platform.biz.UserService;
import cn.newcapec.function.platform.common.SubjectEnum;
import cn.newcapec.function.platform.model.LegalPerson;
import cn.newcapec.function.platform.model.User;
import cn.newcapec.function.platform.utils.Constant;

/**
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/user")
@SuppressWarnings("all")
public class UserController extends MultiViewResource {

	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleGroupService roleGroupService;
	@Autowired
	private UserService userService;
	@Autowired
	private WebRequest request;

	/**
	 * 用户
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "userList")
	public ModelAndView userList(ModelMap modelMap) {
		// session中获取客户代码
		 LegalPerson legalPerson =
		// (LegalPerson)request.getSession().getAttribute("sessionCustomer");
			 (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		 JSONObject jsonObject= new JSONObject();
		 setJsonObject(jsonObject);
		 getJsonObject().put("customerunitcode",
		 legalPerson.getCustomerunitcode());
		// //获取所有用户
		// Page page = userService.queryUsers(getJsonObject(), null);
		 Page RoleGroup = roleGroupService.queryRoleGroupByCustomerCode(getJsonObject(),
		 null);
		//
		// PageView<Map<String, Object>> empUser = new PageView<Map<String,
		// Object>>(
		// PageContext.getPagesize(), PageContext.getOffset());
		// empUser.setQueryResult(page);
		// empUser.setJsMethod("reloadUserList");
		 modelMap.put("pageView", RoleGroup);
		// modelMap.put("pageView2", empRole);
		// DateTool dateTool = new DateTool();
		// modelMap.put("dateTool", dateTool);
		// modelMap.put("date_format_short", DateUtil.DATE_FORMAT);
		return toView(getUrl("platform.userUI"), modelMap);
	}

	/**
	 * 用户展示
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "userListGrid")
	public ModelAndView userListGrid(ModelMap modelMap) {
		LegalPerson legalPerson =  (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		Page page = userService.queryUsers(getJsonObject(), null);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadUserList");
		modelMap.put("pageView", pageView);
		DateTool dateTool = new DateTool();
		modelMap.put("dateTool", dateTool);
		modelMap.put("date_format_short", DateUtil.DATE_FORMAT);
		return toView(getUrl("platform.userlist"), modelMap);
	}

	/**
	 * 添加界面
	 *
	 * @return
	 */
	@RequestMapping(value = "addUserUI")
	public String addUserUI(ModelMap modelMap) {
		LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		 JSONObject jsonObject= new JSONObject();
		 setJsonObject(jsonObject);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		Page RoleGroup = roleGroupService.queryRoleGroupByCustomerCode(getJsonObject(),
				 null);
		modelMap.put("loginlimitlist", Constant.getloginlimitType());
		modelMap.put("customerunitcode", legalPerson.getCustomerunitcode());
		modelMap.put("pageView", RoleGroup);
		return getUrl("platform.adduserUI");
	}

	/**
	 *
	 * @Description: 获取用户角色
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "getRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg getRole() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
				//(LegalPerson) request.getSession()
					//	.getAttribute(Constant.SESSIONCUSTOMER);
				JSONObject jsonObject = new JSONObject();
				setJsonObject(jsonObject);
				getJsonObject().put("customerunitcode",
						legalPerson.getCustomerunitcode());
				// 获取角色
				Page empRole = roleService.queryRoleByCustomerCode(
						getJsonObject(), null);
				msg.setData(empRole);
			}
		});
	}

	/**
	 *
	 * @Description: 获取用户角色组
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "getRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg getRoleGroup() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
				//(LegalPerson) request.getSession()
					//	.getAttribute(Constant.SESSIONCUSTOMER);
				JSONObject jsonObject = new JSONObject();
				setJsonObject(jsonObject);
				getJsonObject().put("customerunitcode",
						legalPerson.getCustomerunitcode());
				// 获取角色组
				Page roleGroup = roleGroupService.queryRoleGroupByCustomerCode(
						getJsonObject(), null);
				msg.setData(roleGroup);
			}
		});
	}

	/**
	 * 保存信息
	 *
	 * @param
	 * @return
	 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	@ResponseBody
	public Msg add() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				User user = JSONTools.JSONToBean(getJsonObject(), User.class);
				// 表字段 否删除状态
				user.setIsdelete("0");
				user.setOpdt(new Date());
				userService.saveOrUpdate(user);
				msg.setMsg("新增成功！");
			}
		});
	}

	/**
	 * 更新界面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editUserUI")
	public String editUserUI(ModelMap modelMap) {
		LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
		//.getAttribute(Constant.SESSIONCUSTOMER);
        getJsonObject().put("customerunitcode",
		legalPerson.getCustomerunitcode());
		String empuuid = getJsonObject().getString("uuid");
		User user = userService.get(empuuid);
		modelMap.put("loginlimitlist", Constant.getloginlimitType());
		modelMap.put("eduser", user);
//		if("1".equals(user.getRoletype())){ //角色组
		Page page = roleGroupService.queryRoleGroupByCustomerCode(getJsonObject(), null);
//		}else{
//			page = roleService.queryRoleByCustomerCode(getJsonObject(), null);
//		}
		modelMap.put("pageView", page);
		return getUrl("platform.edituserUI");
	}

	/**
	 * 更新信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Msg updateUser(final User user) {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				user.setOpdt(new Date());
				userService.updateUser(user);
				msg.setMsg("更新成功！");
			}
		});
	}

	/**
	 * 删去信息
	 *
	 * @return
	 */
	@RequestMapping(value = "deleteUser", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteUser() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				// 判断参数是否为空
				Assert.isTrue(null != getJsonObject(), "删除失败！");
				String userid = getJsonObject().getString("uuid");
				// 删除用户
				userService.removeUser(userid);
				msg.setMsg("删除成功！");
			}
		});
	}

}
