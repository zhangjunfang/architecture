/**  
 * @Title: RoleGroupController.java
 * @Package cn.newcapec.function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei 
 * @date 2014-3-28 下午03:03:03
 * @version V1.0  
 */
package cn.newcapec.function.platform.controller;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

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
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.function.platform.biz.RoleGroupService;
import cn.newcapec.function.platform.biz.RoleService;
import cn.newcapec.function.platform.biz.UserService;
import cn.newcapec.function.platform.common.SubjectEnum;
import cn.newcapec.function.platform.model.LegalPerson;
import cn.newcapec.function.platform.model.RoleGroup;

/**
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/roleGroup")
@SuppressWarnings("all")
public class RoleGroupController extends MultiViewResource {
	@Autowired
	private RoleGroupService roleGroupService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private WebRequest request;

	/**
	 * 
	 * @Title: userListGrid
	 * @Description: 角色组列表
	 * @param @param modelMap
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(value = "roleGroupList")
	public ModelAndView roleGroupList(ModelMap modelMap) {
		JSONObject jsonObject = new JSONObject();
		setJsonObject(jsonObject);
		LegalPerson legalPerson =   (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		List<Map<String, Object>> page = roleGroupService.queryRoleGroups(
				getJsonObject(), null);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setTotalrecord(page.size());
		pageView.setRecords(page);
		pageView.setJsMethod("reloadRoleGroupList");
		modelMap.put("pageView", pageView);
		return toView(getUrl("platform.roleGroupUI"), modelMap);
	}

	/**
	 * 
	 * @Description: 角色组列表
	 * @param @param modelMap
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "roleGroupListGrid")
	public ModelAndView roleGroupListGrid(ModelMap modelMap) {
		LegalPerson legalPerson =   (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		List<Map<String, Object>> page = roleGroupService.queryRoleGroups(
				getJsonObject(), null);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setTotalrecord(page.size());
		pageView.setRecords(page);
		pageView.setJsMethod("reloadRoleGroupList");
		modelMap.put("pageView", pageView);
		return toView(getUrl("platform.roleGrouplist"), modelMap);
	}

	/**
	 * 添加界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addRoleGroupUI")
	public String addRoleGroupUI(ModelMap modelMap) {
		LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		// 获取角色
		Page page = roleService.queryRoleByCustomerCode(getJsonObject(), null);
		modelMap.put("page", page);
		return getUrl("platform.addroleGroupUI");
	}

	/**
	 * 
	 * @Description: 新增角色组
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "addRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg add() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
				//(LegalPerson) request.getSession()
					//	.getAttribute(Constant.SESSIONCUSTOMER);
				getJsonObject().put("customerunitcode",
						legalPerson.getCustomerunitcode());
				RoleGroup rolegroup = JSONTools.JSONToBean(getJsonObject(),
						RoleGroup.class);
				roleGroupService.saveOrUpdate(rolegroup);
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
	@RequestMapping(value = "editRoleGroupUI")
	public String editRoleGroupUI(ModelMap modelMap) {
		LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		// 获取所有角色
		Page page = roleService.queryRoleByCustomerCode(getJsonObject(), null);
		modelMap.put("page", page);
		String uuid = getJsonObject().getString("uuid");
		RoleGroup rolegroup = roleGroupService.get(uuid);
		modelMap.put("roleGroup", rolegroup);
		String[] roles = rolegroup.getRoleids().split(",");
		modelMap.put("roleArry", roles);
		Page page2 = userService.queryUserByRoleGroupId(uuid, null);
		modelMap.put("userpage", page2);
		return getUrl("platform.editroleGroupUI");
	}

	/**
	 * 
	 * @Description: 更新角色组
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "editRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg editRoleGroup() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
				//(LegalPerson) request.getSession()
					//	.getAttribute(Constant.SESSIONCUSTOMER);
				getJsonObject().put("customerunitcode",
						legalPerson.getCustomerunitcode());
				RoleGroup rolegroup = JSONTools.JSONToBean(getJsonObject(),
						RoleGroup.class);
				roleGroupService.saveOrUpdate(rolegroup);
				msg.setMsg("更新成功！");
			}
		});
	}

	/**
	 * 
	 * @Description:删除
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "deleteRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteRoleGroup() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				// 判断参数是否为空
				Assert.isTrue(null != getJsonObject(), "删除失败！");
				String uuid = getJsonObject().getString("uuid");
				// 查询是否有用户在用
				Assert.isTrue(
						null != userService.queryUserByRoleGroupId(uuid, null),
						"有用户在使用此角色组！");
				// 删除用户组
				roleGroupService.removeUnused(uuid);
				msg.setMsg("删除成功！");
			}
		});
	}

}
