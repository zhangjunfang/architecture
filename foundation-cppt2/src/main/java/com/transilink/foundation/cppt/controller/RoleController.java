package com.transilink.foundation.cppt.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.transilink.foundation.cppt.biz.RoleService;
import com.transilink.framework.core.exception.asserts.AssertObject;
import com.transilink.framework.core.handler.MultiViewResource;
import com.transilink.framework.core.rest.Msg;
import com.transilink.framework.core.utils.fileUtils.SysConfigUtil;
import com.transilink.framework.core.utils.pagesUtils.Page;
import com.transilink.framework.core.utils.pagesUtils.PageContext;
import com.transilink.framework.core.utils.pagesUtils.PageView;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/role")
@SuppressWarnings("all")
public class RoleController extends MultiViewResource {

	@Autowired
	private RoleService roleService;

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView indexUI(ModelMap modelMap) {
		modelMap.put("cdn", SysConfigUtil.get("html.cdn"));
		modelMap.put("virtualPath", SysConfigUtil.get("html.virtualPath"));

		Page page = roleService.findList(null);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPageSize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		modelMap.put("pageView", pageView);

		return toView(getUrl("role.indexUI"), modelMap);
	}

	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public Msg list(final HttpServletRequest request) {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				Page page = roleService.findList(getJsonObject());
				PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
						PageContext.getPageSize(), PageContext.getOffset());
				pageView.setQueryResult(page);

				Map<String, Object> modelMap = new HashMap<String, Object>();
				modelMap.put("pageView", pageView);

				msg.setData(toHtml(getUrl("role.indexUI.listUI"), modelMap));
				msg.setSuccess(true);
			}
		});
	}
}
