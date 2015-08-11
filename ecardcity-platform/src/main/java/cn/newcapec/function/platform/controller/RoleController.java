/**  
 * @Title: RoleController.java
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
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.function.platform.biz.RoleService;
import cn.newcapec.function.platform.biz.UserService;
import cn.newcapec.function.platform.common.SubjectEnum;
import cn.newcapec.function.platform.model.LegalPerson;
import cn.newcapec.function.platform.model.Role;

/**
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/role")
@SuppressWarnings("all")
public class RoleController extends MultiViewResource {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private WebRequest request;

	/**
	 * 
	 * @Title: userListGrid
	 * @Description: 角色列表
	 * @param @param modelMap
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(value = "roleList")
	public ModelAndView roleList(ModelMap modelMap) {
		JSONObject jsonObject = new JSONObject();
		setJsonObject(jsonObject);
		LegalPerson legalPerson =  (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		List<Map<String, Object>> page = roleService.queryRoles(
				getJsonObject(), null);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setTotalrecord(page.size());
		pageView.setRecords(page);
		pageView.setJsMethod("reloadRoleList");
		modelMap.put("pageView", pageView);
		return toView(getUrl("platform.roleUI"), modelMap);
	}

	/**
	 * 
	 * @Description: 角色列表
	 * @param @param modelMap
	 * @param @return
	 * @return ModelAndView
	 * @throws
	 */
	@RequestMapping(value = "roleListGrid")
	public ModelAndView roleListGrid(ModelMap modelMap) {
		LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
		//(LegalPerson) request.getSession()
			//	.getAttribute(Constant.SESSIONCUSTOMER);
		getJsonObject().put("customerunitcode",
				legalPerson.getCustomerunitcode());
		List<Map<String, Object>> page = roleService.queryRoles(
				getJsonObject(), null);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setTotalrecord(page.size());
		pageView.setRecords(page);
		pageView.setJsMethod("reloadRoleList");
		modelMap.put("pageView", pageView);
		return toView(getUrl("platform.rolelist"), modelMap);
	}

	/**
	 * 
	 * @Description: 添加界面
	 * @param @param modelMap
	 * @param @return
	 * @return String
	 * @throws
	 */
	@RequestMapping(value = "addRoleUI")
	public String addUserUI(ModelMap modelMap) {
		// 获取授权菜单

		return getUrl("platform.addroleUI");
	}

	/**
	 * 
	 * @Description: 新增角色
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg add() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				LegalPerson legalPerson =  (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
				//(LegalPerson) request.getSession()
					//	.getAttribute(Constant.SESSIONCUSTOMER);
				getJsonObject().put("customerunitcode",
						legalPerson.getCustomerunitcode());
				Role role = JSONTools.JSONToBean(getJsonObject(), Role.class);
//				roleService.saveOrUpdate(role);
				// getJsonObject() 中需要封装放入个 menuid 数组 appid数组 供新增时候用
				roleService.addRole(role,getJsonObject());
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
	@RequestMapping(value = "editRoleUI")
	public String editRoleUI(ModelMap modelMap) {
		String uuid = getJsonObject().getString("uuid");
		Role role = roleService.get(uuid);
		modelMap.put("role", role);
//		Page page2 = userService.queryUserByRoleId(uuid, null);
//		modelMap.put("userpage", page2);
		// 获取授权菜单
		 
		return getUrl("platform.editroleUI");
	}

	/**
	 * 
	 * @Description: 编辑角色
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "editRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg editRole() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				LegalPerson legalPerson = (LegalPerson)request.getAttribute(SubjectEnum.LEGALPERSON.getAlias(),RequestAttributes.SCOPE_SESSION);
				//(LegalPerson) request.getSession()
					//	.getAttribute(Constant.SESSIONCUSTOMER);
				getJsonObject().put("customerunitcode",
						legalPerson.getCustomerunitcode());
				Role role = JSONTools.JSONToBean(getJsonObject(), Role.class);
				// getJsonObject() 中需要封装放入个 menuid 数组 , appid数组 供修改时候用
				roleService.editRole(role,getJsonObject());
				msg.setMsg("更新成功！");
			}
		});
	}

	/**
	 * 
	 * @Description: 删除角色
	 * @param @return
	 * @return Msg
	 * @throws
	 */
	@RequestMapping(value = "deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteRole() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				// 判断参数是否为空
				Assert.isTrue(null != getJsonObject(), "删除失败！");
				String uuid = getJsonObject().getString("uuid");
				// 查询是否有用户在用
				Assert.isTrue(
						null != roleService.queryMenuByRoleId(uuid, null),
						"有菜单在使用此角色！");
				// 删除用户
				roleService.removeUnused(uuid);
				msg.setMsg("删除成功！");
			}
		});
	}

}
