package cn.newcapec.function.platform.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.function.platform.biz.AppPackageService;

@Controller
@Scope("prototype")
@RequestMapping(value = "/menuManage")
@SuppressWarnings("all")
public class MenuManageController extends MultiViewResource {

	@Autowired
	private AppPackageService appPackageService;

	@RequestMapping(value = "indexUI")
	public ModelAndView indexUI(ModelMap modelMap) {
		Page page1 = appPackageService.queryAll(null, null);
		PageView<Map<String, Object>> pageView1 = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView1.setQueryResult(page1);
		modelMap.put("pageView1", pageView1);

		Page page2 = appPackageService.queryCustomAppPackage(null, null);
		PageView<Map<String, Object>> pageView2 = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView2.setQueryResult(page2);
		modelMap.put("pageView2", pageView2);
		return toView(getUrl("platform.menuManage.indexUI"), modelMap);
	}

	@RequestMapping(value = "AppMenuTree")
	@ResponseBody
	public String AppMenuTree(ModelMap modelMap) {
		String appid = getJsonObject().getString("appid");
		String str = appPackageService.queryAppMenuByAppId(appid);
		return str;
	}

	@RequestMapping(value = "CustMenuTree")
	@ResponseBody
	public String CustMenuTree(ModelMap modelMap) {
		String custid = getJsonObject().getString("custid");
		String str = appPackageService.queryAppMenuByCustId(custid);
		return str;
	}
}
