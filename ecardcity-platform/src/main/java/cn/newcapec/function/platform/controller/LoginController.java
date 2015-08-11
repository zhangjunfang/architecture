
/**
 *
 */
package cn.newcapec.function.platform.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.function.platform.biz.LegalPersonService;
import cn.newcapec.function.platform.biz.UserService;
import cn.newcapec.function.platform.biz.Win8UIService;
import cn.newcapec.function.platform.common.SubjectEnum;
import cn.newcapec.function.platform.model.LegalPerson;
import cn.newcapec.function.platform.model.Menu;
import cn.newcapec.function.platform.model.User;
import cn.newcapec.function.platform.utils.ValidateCode;

/**
 *
 * @author ocean date : 2014-4-16 下午04:18:17 email : zhangjunfang0505@163.com
 *         Copyright : newcapec zhengzhou
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/login")
public class LoginController extends MultiViewResource {

	@Autowired
	private UserService userService;
	@Autowired
	private LegalPersonService legalPersonService;
//	@Autowired
//	private RoleGroupService groupService;
//
//	@Autowired
//	private MenuService menuService;
//
//	@Autowired
//	private RoleMenService roleMenService;

	@Autowired
	private Win8UIService  win8uiService;

	/**
	 * 注册提交业务控制器
	 *
	 * @param request
	 * @param response
	 * @return
	 */

	/**
	 * 业务功能说明：
	 *
	 * 判断当前 用户是否 支持u盾 其次判断一下信息
	 *
	 * 通过用户表 【客户法人，登录密码，登录用户名称，以及是否存在u盾】
	 *
	 *
	 */
	@RequestMapping(value = "/login", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView loginUI(ModelMap modelMap, WebRequest request) {


		if (null==getJsonObject()) {
			return toView("index", modelMap);
		}

		String validateCode = (String) request.getAttribute("validateCode",
				RequestAttributes.SCOPE_SESSION);
		String code = getJsonObject().getString("code");
		if (null == validateCode || "".equals(validateCode.trim())
				|| validateCode.length() == 0 || !validateCode.equals(code)) {
			modelMap.put("msg", new Msg(false, "验证码填写有误!"));
			return toView("index", modelMap);
			// throw new BaseException("验证码填写有误!");
		}
		String pwd = getJsonObject().getString("pwd");
		String name = getJsonObject().getString("name");
		String customId = getJsonObject().getString("customId");
		User user = userService.findUser(name, pwd);
		Assert.isTrue(null != user, "身份信息不合法");
		LegalPerson legalPerson = legalPersonService
				.queryByCustomerunitcode(customId);
		Assert.isTrue(null != legalPerson, "身份信息不合法");
		//系统开通注册--》转到对应视图--->转到登录界面
		// 判断是需要U盾识别
		if (user.getLoglimit().equals("5")) {
			System.err
					.println("--------------------------------------------------");
		}
//		List<Menu> menus = menuService.findMenubyRole(roleMenService
//				.findRoleMenuById(groupService.get(user.getRoleid())
//						.getRoleids()));
		List<Menu> menus = win8uiService.findAllMenu(user);
		if (null == menus || menus.size() == 0) {
			modelMap.put("msg", new Msg(false, "没有资源可以操作!"));
			return toView("index", modelMap);
		}
		request.setAttribute(SubjectEnum.USER.getAlias(), user,
				RequestAttributes.SCOPE_SESSION);
		request.setAttribute(SubjectEnum.LEGALPERSON.getAlias(), legalPerson,
				RequestAttributes.SCOPE_SESSION);
		request.setAttribute(SubjectEnum.AUTHORITY.getAlias(), menus,
				RequestAttributes.SCOPE_SESSION);
		return toView(getUrl("platform.win8.indexUI"), modelMap);
	}

	@RequestMapping(value = "/img", method = { RequestMethod.POST,
			RequestMethod.GET })
	public void img(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		ValidateCode.outputImg(request, response);
	}
    /**
     * ajax获取
     * @return
     */
	@RequestMapping(value = "/loginValidate", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public User loginValidateUI() {
		String pwd = getJsonObject().getString("pwd");
		String name = getJsonObject().getString("name");
		User user = userService.findUser(name, pwd);
		return user;
	}


}
