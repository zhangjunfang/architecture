/**
 *
 */
package cn.newcapec.function.platform.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.newcapec.framework.core.handler.MultiViewResource;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.function.platform.biz.BusinessAppreditService;
import cn.newcapec.function.platform.biz.ModuleService;
import cn.newcapec.function.platform.biz.UploadFile;
import cn.newcapec.function.platform.biz.Win8UIService;
import cn.newcapec.function.platform.model.BusinessAppredit;
import cn.newcapec.function.platform.model.Module;
import cn.newcapec.function.platform.tree.model.Tree;

/**
 *
 * @author ocean
 * @date : 2014-4-8 下午03:07:23
 * @email : zhangjunfang0505@163.com
 * @Copyright : newcapec zhengzhou
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@RequestMapping(value = "/app")
public class AppMangerController extends MultiViewResource {

	@Autowired
	private UploadFile uploadFile;
	@Autowired
	private BusinessAppreditService service;
	@Autowired
	private ModuleService moduleService;
	@Autowired
	private Win8UIService win8uiService;

	/**
	 * 应用注册视图
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "appUI", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView appUI(ModelMap modelMap) {
		List<BusinessAppredit> businessAppredits = service
				.getBusinessAppredit();
		modelMap.addAttribute("businessAppreditList", businessAppredits);
		modelMap.addAttribute("moduleList",
				moduleService.findByBusinessAppredit(businessAppredits));
		return toView(getUrl("platform.appManger.app"), modelMap);
	}

	/**
	 * 文件上传控制
	 *
	 * @param request
	 *            建议使用paths以及nio操作文件 但是 悲哀事情发生了
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	@ResponseBody
	public Msg uploadFile() {
		return uploadFile.uploadFile(getfiles(),
				getUrl("platform.appManger.file"));

	}

	/**
	 * 自定义应用包业务控制器--视图
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customAppUI", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView customApp(ModelMap modelMap) {
		List<BusinessAppredit> appredits = service.getCusAppredit();
		modelMap.put("app", appredits);
		modelMap.addAttribute("moduleList",
				moduleService.findByBusinessAppredit(appredits));
		return toView(getUrl("platform.appManger.customAppUI"), modelMap);
	}

	/**
	 * 自定义应用包--编辑
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customAppEdit", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public BusinessAppredit customAppEdit() {
		// 获取编辑数据的id
		return service.queryCustomApp(getJsonObject().getString("id"));

	}

	/**
	 * 自定义应用包--保存或者更新
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "merge", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public boolean merge() {
		return service.SaveOrUpdate(getJsonObject());
		// return redirect("customAppUI");
	}

	/**
	 * 自定义应用包--删除
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customAppDelete", method = { RequestMethod.POST,
			RequestMethod.GET })
	public String customAppDelete(ModelMap modelMap) {
		String id = getJsonObject().getString("id");
		Assert.isTrue(null != id, "删除操作有误！！！");
		service.delete(id);
		return redirect("customAppUI");

	}

	/**
	 * 自定义应用包--树形数据加载
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customTree", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public List<Tree> customTree() {
		return win8uiService.showCusTree();

	}

	/**
	 * 自定义应用包--查找包对应的模块
	 *
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "findModule", method = { RequestMethod.POST,
			RequestMethod.GET })
	@ResponseBody
	public List<Module> findModule() {
		return moduleService.findByBusinessAppredit(service.get((getJsonObject().getString("id"))).getAppid());

	}

}
