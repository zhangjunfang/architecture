package cn.newcapec.foundation.privilege.rest;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import cn.newcapec.foundation.privilege.biz.ResourceService;
import cn.newcapec.foundation.privilege.model.Resource;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;

/**
 * 授权资源视图资源类
 *
 * @author andy.li
 *
 */
@Component
@Scope("prototype")
@SuppressWarnings("all")
public class ResourceResource extends PrivilegeResource {
	
	private String resourceListUI;
	

	/* 资源业务类 */
	@Autowired
	private ResourceService resourceService;


	// 接受参数类
	private Map<String, Object> parms = new HashMap<String, Object>();
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();

	/**
	 * 获取资源列表UI
	 *
	 * @param request
	 * @param response
	 */
	public void resourceListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/resource/pagelet/v1.0/resource_list.html";
//		resourceListGrid(request, response);
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 获取资源列表信息
	 *
	 * @param request
	 * @param response
	 */
	public void resourceListGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/resource/pagelet/v1.0/resource_list_grid.html";
		/* 查询列表 */
		orderby.put("resources.create_date", "desc");
		Page page = resourceService.queryResource(getJsonObject(), orderby);
		// 角色列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(10, PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadResourceList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 添加资源UI
	 *
	 * @param request
	 * @param response
	 */
	public void addResourceUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/resource/pagelet/v1.0/addResourceUI.html";
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 编辑资源UI
	 *
	 * @param request
	 * @param response
	 */
	public void editResourceUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/resource/pagelet/v1.0/editResourceUI.html";
		response.toView(url, getNewcapectViewContext());
	}


	/**
	 * 获取某资源
	 *
	 * @param request
	 * @param response
	 */
	public void findResourceById(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				Resource resource = resourceService.queryResourceById(JSONTools.getString(getJsonObject(), "id"));
				Msg msg = new Msg();
				msg.setData(resource);
				msg.setSuccess(true);
				response.print(msg.toJSONObjectPresention());
			}
		} catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("获取资源失败！", e);
			}
		}
	}

	/**
	 * 添加资源信息
	 *
	 * @param request
	 * @param response
	 */
	public void add(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				Resource resource = new Resource();
				resource.setResourceName(JSONTools.getString(getJsonObject(), "name"));
				resource.setResourcetypeid(JSONTools.getString(getJsonObject(), "resourcetype"));
				resource.setUrl(JSONTools.getString(getJsonObject(), "url"));
				resource.setStatus(JSONTools.getString(getJsonObject(), "status"));
				resource.setParentid(JSONTools.getString(getJsonObject(), "parentid"));
				resource.setMenuid(JSONTools.getString(getJsonObject(), "menu_id"));
				resource.setCreateDate(new Date());

				if (null != resource) {
					resourceService.saveOrUpdate(resource);
					Msg msg = new Msg();
					msg.setSuccess(true);
					response.print(msg.toJSONObjectPresention());
				} else {
					Msg msg = new Msg();
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("新增资源失败！", e);
			}
		}
	}

	/**
	 * 删除资源
	 *
	 * @param request
	 * @param response
	 */
	public void delete(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
					String ids = JSONTools.getString(getJsonObject(), "resourceids");
					if (StringUtils.isNotBlank(ids)) {
						String[] idss = ids.split(",");
						Msg msg = new Msg();
						msg.setMsg("删除功能成功！");
						msg.setSuccess(true);
						resourceService.deleteResource(idss);
						response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("删除功能失败！", e);
			}
		}
	}

	/**
	 * 修改功能信息
	 *
	 * @param request
	 * @param response
	 */
	public void edit(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				Resource resource = resourceService.get(JSONTools.getString(getJsonObject(), "id"));
				resource.setResourceName(JSONTools.getString(getJsonObject(), "name"));
				resource.setResourcetypeid(JSONTools.getString(getJsonObject(), "resourcetype"));
				resource.setUrl(JSONTools.getString(getJsonObject(), "url"));
				resource.setStatus(JSONTools.getString(getJsonObject(), "status"));
				resource.setParentid(JSONTools.getString(getJsonObject(), "parentid"));
				// Resource resource = (Resource) getJsonObject().toBean(data,
				// Resource.class);
				resource.setId(JSONTools.getString(getJsonObject(), "id"));

				if (null != resource) {
					resourceService.saveOrUpdate(resource);
					Msg msg = new Msg();
					msg.setSuccess(true);
					response.print(msg.toJSONObjectPresention());
				} else {
					Msg msg = new Msg();
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
            if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("修改功能失败！", e);
			}
		}
	}
	
}
