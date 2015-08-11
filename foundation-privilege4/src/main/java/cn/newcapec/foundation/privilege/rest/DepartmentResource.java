package cn.newcapec.foundation.privilege.rest;

import cn.newcapec.foundation.privilege.biz.DepartmentService;
import cn.newcapec.foundation.privilege.biz.UserDepartmentService;
import cn.newcapec.foundation.privilege.model.Department;
import cn.newcapec.framework.core.exception.BaseException;
import cn.newcapec.framework.core.rest.BaseRequest;
import cn.newcapec.framework.core.rest.BaseResponse;
import cn.newcapec.framework.core.rest.Msg;
import cn.newcapec.framework.core.utils.jsonUtils.JSONTools;
import cn.newcapec.framework.core.utils.pagesUtils.Page;
import cn.newcapec.framework.core.utils.pagesUtils.PageView;
import cn.newcapec.framework.core.utils.pagesUtils.PageContext;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 部门视图资源类
 * 
 * @author andy.li
 * 
 */
@SuppressWarnings("all")
@Component
@Scope("prototype")
public class DepartmentResource extends PrivilegeResource {

	/* 部门业务类 */
	@Autowired
	private DepartmentService departmentService;

	/**
	 * 部门人员业务类
	 * ***/
	@Autowired
	private UserDepartmentService userDepartmentService;
	// 接受参数类
	private Map<String, Object> parms = new HashMap<String, Object>();
	// 排序参数
	private LinkedHashMap<String, String> orderby = new LinkedHashMap<String, String>();

	/**
	 * 获取部门列表UI
	 * 
	 * @param request
	 * @param response
	 */
	public void departmentListUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/department/pagelet/v1.0/department_list.html";

		departmentListGrid(request, response);
		response.toView(url, getNewcapectViewContext());
	}


	/**
	 * departmentTree Item
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void departmentTree(BaseRequest request,
			BaseResponse response) {
		String data = "-1";
		if (null != getJsonObject()) {
			data = JSONTools.getString(getJsonObject(), "id");
			data = data.equals("") ? "-1" : data;
		}

		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("parent_id", data);
		PageContext.setPagesize(Integer.MAX_VALUE);
		Page page = departmentService.queryDepartments(queryMap, orderby);

		List<Map<String, Object>> departments = page.getItems();

		Msg msg = new Msg();
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		for (Map<String, Object> item : departments) {
			Map<String, Object> dpt = item;
			map = new HashMap<String, String>();
			map.put("pId", dpt.get("PARENT_ID").toString());
			map.put("id", dpt.get("ID").toString());
			map.put("name", dpt.get("NAME").toString());
			if("-1".equals( dpt.get("PARENT_ID"))){
				map.put("isParent","true");
			}else{
			map.put("isParent","false");
			}
			list.add(map);
		}
		msg.setMsg("dempartment Item");
		msg.setData(list);
		response.print(msg.toJSONObjectPresention());
	}

	/**
	 * 获取所有的部门节点
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void departmentItemAll(BaseRequest request, BaseResponse response) {

		Map<String, Object> queryMap = new HashMap<String, Object>();
		/***
		 * 设置上下文对象的pagesize属性，可以展示每页显示数据条数【当值为Integer.MAX_VALUE时，认为是不分页显示数据的】
		 * 
		 * @date 2013-09-24
		 * */
		PageContext.setPagesize(Integer.MAX_VALUE);
		Page page = departmentService.queryDepartments(queryMap, orderby);

		List<Map<String, Object>> departments = page.getItems();

		Msg msg = new Msg();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Map<String, Object> item : departments) {
			Map<String, Object> dpt = item;
			map = new HashMap<String, Object>(departments.size());
			if(null!=map && map.size()>0){
				map.put("pId", dpt.get("parent_id")!=null?dpt.get("parent_id").toString():"");
				map.put("id", dpt.get("id")==null?"":dpt.get("id").toString());
				map.put("name", dpt.get("name").toString());
				map.put("isParent", dpt.get("isParent").toString());
				list.add(map);
			}
		}
		msg.setMsg("dempartment Item");
		msg.setData(list);
		msg.setSuccess(true);
		response.print(msg.toJSONObjectPresention());
	}

	/**
	 * 获取部门列表信息
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void departmentListGrid(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/department/pagelet/v1.0/department_list_grid.html";
		/* 查询列表 */
		Page page = departmentService
				.queryDepartments(getJsonObject(), orderby);
		// 部门列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadDepartmentList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toView(url, getNewcapectViewContext());
	}



	/**
	 * 删去部门信息
	 * 
	 * @param request
	 * @param response
	 */
	public void deleteDepartment(BaseRequest request, BaseResponse response) {
		Msg msg = new Msg();
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				if (data != null) {
					String ids = JSONTools.getString(data, "departmentIds");
					ids = ids.replaceAll("'on',", "").replaceAll("'", "");
					if (StringUtils.isNotBlank(ids)) {
						String[] idss = ids.split(",");
						if (userDepartmentService
								.findDepartmentsbyIdsExist(idss)) {
							throw new BaseException("当前删除部门下存在用户 ，请删除用户后再操作!");
						}
						departmentService.deleteDepartmentsbyIds(idss);
						msg.setSuccess(true);
						msg.setMsg("删去部门成功！");
						response.print(msg.toJSONObjectPresention());
					} else {
						msg.setSuccess(false);
						msg.setMsg("删去部门失败！");
						response.print(msg.toJSONObjectPresention());

					}
				}
			}
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("删去部门失败！", e);
			}
		}
	}

	/**
	 * 添加部门UI
	 * 
	 * @param request
	 * @param response
	 */
	public void addDepartmentUI(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/department/pagelet/v1.0/add_department_ui.html";
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 添加部门信息
	 * 
	 * @param request
	 * @param response
	 */
	public void addDepartment(BaseRequest request, BaseResponse response) {

		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				if (null != data) {
					Department department = (Department) JSONObject.toBean(
							data, Department.class);
					department.setCreateDatetime(new Date());
//					department.setIsParent("false");
					/**
					 * 方案一： 全局部门名称是否重名
					 * 
					 * */
					// if (StringUtils.isEmpty(department.getId())
					// && null != departmentService
					// .findDepartmentByName(department.getName())) {
					// throw new BaseException("部门名字已经存在，请重新输入！");
					// }
					/**
					 * 方案二： 同级部门名称是否重名
					 * 
					 * */
					boolean bool = departmentService.findSameLevelDepartmentExistingName(department);
					System.out.println(bool);
					if (StringUtils.isEmpty(department.getId())
							&& departmentService.findSameLevelDepartmentExistingName(department)) {
						throw new BaseException("部门名字已经存在，请重新输入！");
					}
					if("-1".equals(department.getParentId()) || "0".equals(department.getParentId())){
						department.setParentId("-1");
					}
					
					departmentService.saveOrUpdate(department);
					Msg msg = new Msg();
					msg.setSuccess(true);
					response.print(msg.toJSONObjectPresention());
				} else {
					Msg msg = new Msg();
					msg.setSuccess(false);
					msg.setMsg("添加部门失败！");
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
            log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("添加部门失败！", e);
			}
		}
	}

	/**
	 * 修改部门信息
	 * 
	 * @param request
	 * @param response
	 */
	/**
	 * 查找部门信息
	 * 
	 * @param request
	 * @param response
	 */
	public void findDepartment(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/department/pagelet/v1.0/update_department_ui.html";
		try {
			String id = request.getString("departmentid");
			Department department = null;
			if (id != null) {
				department = departmentService.get(id);
			}
			getNewcapectViewContext().put("department", department);
			response.toView(url, getNewcapectViewContext());
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("查找部门失败！", e);
			}
		}
	}

	/**
	 * 更新部门信息
	 * 
	 * @param request
	 * @param response
	 */
	public void updateDepartment(BaseRequest request, BaseResponse response) {
		try {
			if (null != getJsonObject()) {
				JSONObject data = JSONTools.getJSONObject(getJsonObject(),
						"data");
				if (null != data) {
					Department department = (Department) JSONObject.toBean(
							data, Department.class);
					department.setCreateDatetime(new Date());
					// 判断部门名称是否存在
					Department oldDepartment=departmentService.get(department.getId());
					if (oldDepartment.getName().equals(department.getName())) {
						departmentService.saveOrUpdate(department);
					} else {
						if (StringUtils.isEmpty(department.getId())
								&& null != departmentService
										.findDepartmentByName(department.getName())) {
							throw new BaseException("部门名字已经存在，请重新输入！");
						}
						departmentService.saveOrUpdate(department);
					}
					Msg msg = new Msg();
					msg.setSuccess(true);
					response.print(msg.toJSONObjectPresention());
				} else {
					Msg msg = new Msg();
					msg.setSuccess(false);
					msg.setMsg("更新部门失败！");
					response.print(msg.toJSONObjectPresention());
				}
			}
		} catch (Exception e) {
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("更新部门失败！", e);
			}
		}
	}

	/**
	 * 部门信息模糊查询
	 * 
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("rawtypes")
	public void queryDepartment(BaseRequest request, BaseResponse response) {
		String url = "/foundation/privilege/department/pagelet/v1.0/department_list_grid.html";

		parms.put("name", "'%" + request.getString("name") + "%'");

		parms.put("status", request.getString("status"));
		/* 查询列表 */
		Page page = departmentService.findDepartmentsbyParams(parms, orderby);
		// 部门列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadDepartmentList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toView(url, getNewcapectViewContext());
	}

	/**
	 * 根据选中的节点，动态加载右侧的部门数据
	 * 
	 * */
	public void dynamicLoadDepartmentList(BaseRequest request,
			BaseResponse response) {
		String url = "/foundation/privilege/department/pagelet/v1.0/department_list_grid.html";
		if (null != getJsonObject()) {
			String parentId = JSONTools.getString(getJsonObject(), "parent_id");
			parms.put("parent_id", parentId);
		}
		PageContext.setPagesize(Integer.MAX_VALUE);
		/* 查询列表 */
		Page page = departmentService.querySubDepartments(parms, orderby);
		// 部门列表视图
		PageView<Map<String, Object>> pageView = new PageView<Map<String, Object>>(
				10, PageContext.getOffset());
		pageView.setQueryResult(page);
		pageView.setJsMethod("reloadDepartmentList");
		getNewcapectViewContext().put("pageView", pageView);
		response.toView(url, getNewcapectViewContext());
	}
}
