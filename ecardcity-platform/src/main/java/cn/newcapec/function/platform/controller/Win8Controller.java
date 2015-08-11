package cn.newcapec.function.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.function.platform.biz.Win8UIService;
import cn.newcapec.function.platform.common.SubjectEnum;
import cn.newcapec.function.platform.model.Module;
import cn.newcapec.function.platform.model.User;

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
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(value = "/win8")
public class Win8Controller extends MultiViewResource {

	@Autowired
	private Win8UIService service;

	@RequestMapping(value = "indexUI")
	public ModelAndView indexUI(ModelMap modelMap) {

		// modelMap.put("tree", service.findAllMenu());

		return toView(getUrl("platform.win8.indexUI"), modelMap);
	}

	/**
	 * 用于测试主页菜单加载数据
	 *
	 * @author ocean 2014-04-22
	 *
	 * @return json格式数据对象
	 */
	@RequestMapping(value = "testUI")
	@ResponseBody
	public List<Module> testUI(WebRequest request) {
		// return service.showTree();
		return service.findAllModule((User) request.getAttribute(
				SubjectEnum.USER.getAlias(), RequestAttributes.SCOPE_SESSION));

	}

}
