/**
 * 
 */
package cn.newcapec.foundation.portal.controller;

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

import cn.newcapec.foundation.portal.biz.StudentService;
import cn.newcapec.foundation.portal.model.Student;
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
 * 学生视图展示类
 * @author Administrator
 *
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/student")
@SuppressWarnings("all")
public class StudentController extends MultiViewResource {

	@Autowired
	private StudentService studentService;

	

	/**
	 * 学生列表信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "stuListUI")
	public ModelAndView stuListUI(ModelMap modelMap) {
		Page page = studentService.querys(getJsonObject());
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadStuList");
		modelMap.put("pageView", pageView);
		return  toView(getUrl("student.stuListUI"), modelMap);
	}

	/**
	 * 学生表格展示
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "stuListGrid")
	public ModelAndView stuListGrid(ModelMap modelMap) {
		Page page = studentService.querys(getJsonObject());
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				PageContext.getPagesize(), PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadStuList");
		modelMap.put("pageView", pageView);
		return toView(getUrl("student.stuListGrid"), modelMap);
	}
	
	/**
	 * 学生添加界面
	 * @return
	 */
	@RequestMapping(value = "addStuUI")
	public String addStuUI() {
		return getUrl("student.addStuUI");
	}

	
	/**
	 * 保存学生信息
	 * @param student
	 * @return
	 */
	@RequestMapping(value = "add",method=RequestMethod.POST)
	@ResponseBody
	public Msg add(final HttpServletRequest request) {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				Student student = JSONTools.JSONToBean(getJsonObject(), Student.class);
				List<FileAttach> fileList = getfiles();
				String newfilename=null;
				if (fileList!=null && fileList.size() > 0) {
					FileAttach fileItem = fileList.get(0);
					if ("photo".equals(fileItem.getProName())) {
						String filename = fileItem.getFileName();
						String uploadpath = request.getSession().getServletContext().getRealPath("/") + File.separator + "upload";
						File descfile = new File(uploadpath);
						newfilename = UUID.randomUUID().toString() + "." + WebUtils.getExt(filename);
						IoUtil.createFile(new File(descfile, newfilename), fileItem.getInputStream());
					}
				}
				student.setPhoto(newfilename);
				studentService.saveOrUpdate(student);
				msg.setMsg("保存成功！");
			}
		});
	}
	
	
	/**
	 * 更新学生界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editStuUI")
	public String editStuUI() {
		return getUrl("student.editStuUI");
	}

	/**
	 * 更新学生信息
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "edit",method=RequestMethod.POST)
	@ResponseBody
	public Msg edit(final Student student) {
		 return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg Msg) {
				studentService.saveOrUpdate(student);
			}
		});
	}
	
	/**
	 * 打开查询界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "selectById",method=RequestMethod.POST)
	@ResponseBody
	public Msg selectById() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				//判断传递参数是否为null
				Assert.isTrue(null != getJsonObject(),"打开查询界面失败！");
				
				Student student = studentService.findById(getJsonObject().getString("id"));
				msg.setData(student);
			}
		});
	}

	
	/**
	 * 删去学生信息
	 * @return
	 */
	@RequestMapping(value = "delete",method=RequestMethod.POST)
	@ResponseBody
	public Msg delete() {
		return doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				
				//判断参数是否为空
				Assert.isTrue(null != getJsonObject(),"删除失败！");
				
					String ids = getJsonObject().getString("ids");
					String[] idArray = ids.split(",");
					studentService.delete(idArray);
					msg.setMsg("删除成功！");
			}
		});
	}

	
}
