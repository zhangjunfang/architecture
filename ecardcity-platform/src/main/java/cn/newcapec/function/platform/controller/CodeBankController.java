package cn.newcapec.function.platform.controller;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.function.platform.biz.CodeBankService;
import cn.newcapec.function.platform.model.CodeBank;
import cn.newcapec.framework.core.exception.asserts.Assert;
import cn.newcapec.framework.core.exception.asserts.AssertObject;
import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.model.FileAttach;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.fileUtils.IoUtil;
import cn.newcapec.framework.core.utils.httpUtils.WebUtils;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;

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
@RequestMapping(value = "/codeBank")
@SuppressWarnings("all")
public class CodeBankController extends MultiViewResource {

	@Autowired
	private CodeBankService codeBankService;


	@RequestMapping(value = "indexUI")
	public ModelAndView indexUI(ModelMap modelMap) {
		PageContext.setPagesize(Integer.MAX_VALUE);
		Page page = codeBankService.query(getJsonObject(), null);
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(PageContext.getPagesize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		modelMap.put("pageView", pageView);
		return toView(getUrl("platform.codeBank.indexUI"), modelMap);
	}
}