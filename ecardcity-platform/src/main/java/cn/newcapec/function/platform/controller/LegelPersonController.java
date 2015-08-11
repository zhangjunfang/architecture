package cn.newcapec.function.platform.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.function.platform.biz.AppPackageService;
import cn.newcapec.function.platform.biz.CodeBankService;
import cn.newcapec.function.platform.biz.LegelPersonService;

/**
 * <p>
 * Title:
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Copyright: Copyright(c) 2011 郑州新开普电子股份有限公司
 * </p>
 *
 * @author 黄鑫 (huangxin)
 * @version
 * @data 创建日期：2011-11-11 修改日期： 修改人： 复审人：
 * @generated
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/legelPerson")
@SuppressWarnings("all")
public class LegelPersonController extends MultiViewResource {

	@Autowired
	private LegelPersonService legelPersonService;

	@Autowired
	private CodeBankService codeBankService;

	@Autowired
	private AppPackageService appPackageService;

	@RequestMapping(value = "indexUI")
	public ModelAndView indexUI(ModelMap modelMap) {
		PageContext.setPagesize(Integer.MAX_VALUE);
		Page page = legelPersonService.query(getJsonObject(), null);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		modelMap.put("pageView", pageView);
		return toView(getUrl("platform.legelPerson.indexUI"), modelMap);
	}

	/**
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "manageAndPermitUI")
	public ModelAndView manageAndPermitUI(ModelMap modelMap) {
		PageContext.setPagesize(Integer.MAX_VALUE);
		Page page = legelPersonService.query(getJsonObject(), null);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		modelMap.put("pageView", pageView);

		Page page2 = codeBankService.query(getJsonObject(), null);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView2 = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView2.setQueryResult(page2);
		modelMap.put("banks", pageView2);

		Page page3 = appPackageService.findAll(getJsonObject(), null);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView3 = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView3.setQueryResult(page3);
		modelMap.put("appPackage", pageView3);
		return toView(getUrl("platform.legelPerson.manageAndPermitUI"),
				modelMap);
	}

	/**
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "manageAndPermitAddUI")
	public ModelAndView manageAndPermitAddUI(ModelMap modelMap) {
		return toView(getUrl("platform.legelPerson.manageAndPermitAddUI"),
				modelMap);
	}
}