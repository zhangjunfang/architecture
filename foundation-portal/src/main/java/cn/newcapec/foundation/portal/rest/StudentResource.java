/**
 * 
 */
package cn.newcapec.foundation.portal.rest;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.restlet.Context;
import org.restlet.data.Request;
import org.restlet.data.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.portal.biz.StudentService;
import cn.newcapec.foundation.portal.model.Student;
import cn.newcapec.framework.core.exception.error.AssertObject;
import cn.newcapec.framework.core.model.FileAttach;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResource;
import cn.newcapec.framework.core.rest.BaseResourceHandler;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.dataUtils.DateUtil;
import cn.newcapec.framework.core.utils.fileUtils.IoUtil;
import cn.newcapec.framework.core.utils.httpUtils.WebUtils;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.core.utils.pagesUtils.SystemContext;

/**
 * 学生视图展示
 * @author Administrator
 *
 */
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class StudentResource extends BaseResource implements BaseResourceHandler {

	@Autowired
	private StudentService studentService;


	/**
	 * 学生列表信息
	 * @param request
	 * @param response
	 * @return
	 */
	public void stuListUI(BaseRequest request, BaseResponse response){
		stuListGrid(request,response);
		response.toHtml(getUrl("students.stuListUI"), getNewcapectViewContext());
	}
	
	/**
	 * 学生表格展示
	 * @param request
	 * @param modelMap
	 * @return
	 */
	public void stuListGrid(BaseRequest request, BaseResponse response){
		Page page =studentService.querys(getJsonObject());
		// 菜单列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				SystemContext.getPagesize(), SystemContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadStuList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toHtml(getUrl("students.stuListGrid"), getNewcapectViewContext());
	}

	/**
	 * 学生添加界面
	 * @return
	 */
	public void addStuUI(BaseRequest request, BaseResponse response){
		response.toHtml(getUrl("students.addStuUI"), getNewcapectViewContext());
	}
	
	/**
	 * 保存学生信息
	 * @param student
	 * @return
	 */
	public void add(final BaseRequest request,final BaseResponse response){
		response.print(doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				
				Student student = JSONTools.JSONToBean(getJsonObject(), Student.class);
				
				List<FileAttach> fileList = request.getfiles();
				String newfilename=null;
				if (fileList!=null && fileList.size() > 0) {
					FileAttach fileItem = fileList.get(0);
					if ("photo".equals(fileItem.getProName())) {
						String filename = fileItem.getFileName();
						String uploadpath = WebUtils.getSession(request.getOrginRequest()).getServletContext().getRealPath("/") + File.separator + "upload";
						File descfile = new File(uploadpath);
						newfilename = UUID.randomUUID().toString() + "." + WebUtils.getExt(filename);
						IoUtil.createFile(new File(descfile, newfilename), fileItem.getInputStream());
					}
				}
				student.setPhoto(newfilename);
				studentService.saveOrUpdate(student);
				msg.setMsg("保存成功！");
			}
		}).toJSONObjectPresention());
	}
	
	/**
	 * 更新学生界面
	 * @param request
	 * @param response
	 * @return
	 */
	public void editStuUI(BaseRequest request, BaseResponse response){
		response.toHtml(getUrl("students.editStuUI"), getNewcapectViewContext());
	}

	/**
	 * 更新学生信息
	 * @param request
	 * @param response
	 * @return
	 */
	public void edit(BaseRequest request, BaseResponse response){
		response.print(super.doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				Student student = JSONTools.JSONToBean(getJsonObject(), Student.class);
				studentService.saveOrUpdate(student);
				msg.setMsg("保存成功！");
			}
		}).toJSONObjectPresention());
	}
	
	
	
	/**
	 * 打开查询界面
	 * @param request
	 * @param response
	 * @return
	 */
	public void selectById(BaseRequest request, BaseResponse response) {
		response.print(super.doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				if (null != getJsonObject()) {
					Student student = studentService.findById(JSONTools.getString(
							getJsonObject(), "id"));
					msg.setData(student);
				}
			}
		}).toJSONObjectPresention());
	}
	
	
	/**
	 * 删去学生信息
	 * @return
	 */
	public void delete(BaseRequest request, BaseResponse response){
		response.print(super.doExpAssert(new AssertObject() {
			@Override
			public void AssertMethod(Msg msg) {
				if(getJsonObject()!=null){
					String ids=getJsonObject().getString("ids");
					String[] idArray=ids.split(",");
					studentService.delete(idArray);
					msg.setMsg("删除成功！");
				}
			}
		}).toJSONObjectPresention());
	}

}
