/**
 * @Title:
 * @Package cn.newcapec.function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
package cn.newcapec.function.platform.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.exception.asserts.AssertObject;
import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.function.platform.biz.LegalPersonService;
import cn.newcapec.function.platform.biz.RoleService;
import cn.newcapec.function.platform.biz.UserService;
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
@RequestMapping(value = "/homepage")
@SuppressWarnings("all")
// @SessionAttributes(value={"sessionCustomer"},types=LegalPerson.class)
public class HomePageController extends MultiViewResource {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;
	@Autowired
	private LegalPersonService legalPersonService;
	@Autowired
	private HttpServletRequest request;

	/**
	 * 登陆页面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loginUI")
	public ModelAndView login(ModelMap modelMap) {
		return toView(getUrl("platform.loginUI"), modelMap);
	}

	/**
	 * 用户登陆
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public Msg userLogin(ModelMap modelMap) {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				String customerunitcode = getJsonObject().getString(
						"customerunitcode");
				// 根据customerid 查询客户信息 并放入session
				LegalPerson legalPerson = legalPersonService
						.queryByCustomerunitcode(customerunitcode);
				request.getSession().setAttribute(Constant.SESSIONCUSTOMER,
						legalPerson);
				// 查询用户信息 并放入session
				User user = userService.findUser(
						getJsonObject().getString("empCode"), getJsonObject()
								.getString("empPwd"));
				if (user == null) {
					msg.setMsg("用户名或密码错误！");
				} else if (!"0".equals(user.getLoglimit())) { // 后续去登陆限制表查询
																// Constant.getloginlimitType()
					msg.setMsg("用户名登陆限制！");
				} else {
					//request.getSession().setAttribute(Constant.SESSIONUSER, user);
					msg.setMsg("登陆成功！");
				}
			}
		});
	}

}
