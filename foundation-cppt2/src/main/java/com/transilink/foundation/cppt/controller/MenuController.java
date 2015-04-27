package com.transilink.foundation.cppt.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.transilink.foundation.cppt.biz.MenuService;
import com.transilink.framework.core.exception.asserts.AssertObject;
import com.transilink.framework.core.handler.MultiViewResource;
import com.transilink.framework.core.rest.Msg;
import com.transilink.framework.core.utils.fileUtils.SysConfigUtil;
import com.transilink.framework.core.utils.pagesUtils.Page;
import com.transilink.framework.core.utils.pagesUtils.PageContext;
import com.transilink.framework.core.utils.pagesUtils.PageView;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/menu")
@SuppressWarnings("all")
public class MenuController extends MultiViewResource {

	@Autowired
	private MenuService menuService;

	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();

	@RequestMapping(value = "index", method = RequestMethod.GET)
	public ModelAndView indexUI(ModelMap modelMap) {
		modelMap.put("cdn", SysConfigUtil.get("html.cdn"));
		modelMap.put("virtualPath", SysConfigUtil.get("html.virtualPath"));

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("pid", "0");
		PageContext.setPageSize(Integer.MAX_VALUE);

		orderby.put("sort", "asc");
		Page page = menuService.findList(paramMap, orderby);
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPageSize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		modelMap.put("pageView", pageView);
		/* tree */
		modelMap.put("treeView", pageView);

		return toView(getUrl("menu.indexUI"), modelMap);
	}

	@RequestMapping(value = "list.do", method = RequestMethod.GET)
	public Msg list(final HttpServletRequest request) {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				orderby.put("sort", "asc");
				Page page = menuService.findList(getJsonObject(), orderby);
				PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
						PageContext.getPageSize(), PageContext.getOffset());
				pageView.setQueryResult(page);

				Map<String, Object> modelMap = new HashMap<String, Object>();
				modelMap.put("pageView", pageView);

				if (getJsonObject().containsKey("type")) {
					msg.setData(page);
				} else {
					msg.setData(toHtml(getUrl("menu.indexUI.listUI"), modelMap));
				}
				msg.setSuccess(true);
			}
		});
	}
}
