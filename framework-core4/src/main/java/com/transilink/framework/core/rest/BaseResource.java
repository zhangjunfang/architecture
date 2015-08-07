package com.transilink.framework.core.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.tools.generic.DateTool;
import org.restlet.Context;
import org.restlet.Request;
import org.restlet.Response;
import org.restlet.data.MediaType;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.engine.adapter.ServerCall;
import org.restlet.ext.servlet.internal.ServletCall;
import org.restlet.representation.Representation;
import org.restlet.representation.Variant;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;
import org.springframework.beans.factory.InitializingBean;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.exception.ExceptionUtil;
import com.transilink.framework.core.exception.SysException;
import com.transilink.framework.core.exception.asserts.AssertObject;
import com.transilink.framework.core.filter.PagerFilter;
import com.transilink.framework.core.handler.UrlMapping;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.utils.dataUtils.DateUtil;
import com.transilink.framework.core.utils.fileUtils.SysConfigUtil;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class BaseResource extends ServerResource implements InitializingBean,BaseResourceHandler, LogEnabled  {

	// JSON参数实体
	private net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
	// 接受参数实体
	private Map<String, String> params = new HashMap<String, String>();

	private org.apache.velocity.context.Context transilinkViewContext = BaseResource
			.getVelocityContext();

	private static VelocityContext getVelocityContext() {
		Map<String, String> map = new HashMap<String, String>();
		VelocityContext result = new VelocityContext();
		// 增加服务器路径
		DateTool dateTool = new DateTool();
		result.put("dateTool", dateTool);
		// 日期长短格式
		result.put("date_format_short", DateUtil.DATE_FORMAT);
		result.put("date_format_long", DateUtil.DATETIME_FORMAT);
		String ctx = PagerFilter.getRootPath();
		String envRoot = PagerFilter.getRootPath() + "/"
				+ SysConfigUtil.get("framework.springContext");
		map.put("ctx", ctx);
		map.put("envRoot", envRoot);
		result.put("_project", map);
		return result;
	}

	/** 系统接收的请求方式 */
	private static final String[] ALLOWED_METHODS = { "get", "post", "put",
			"delete" };

	private boolean canGet;
	private boolean canPost;
	private boolean canPut;
	private boolean canDelete;

	public BaseResource() {
		super();
	}

	public BaseResource(Context context, Request request, Response response) {
		this.init(context, request, response);
		this.getVariants().add(new Variant(MediaType.ALL));
		String callMethod = request.getMethod().getName();
		log.info("method[" + callMethod + "] call!");
	}

	/**
	 * 错误处理机制
	 *
	 * @param jsonPost
	 * @return
	 */
	public Msg doExpAssert(AssertObject assertObject) {
		Msg msg = new Msg();
		try {
			assertObject.AssertMethod(msg);
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("系统出错了!");
			log.error(ExceptionUtils.getFullStackTrace(e));
			if (e instanceof BaseException) {
				throw (BaseException) e;
			} else {
				throw new BaseException("系统出错了!", e);
			}
		}
		return msg;
	}

	/**
	 * 初始化
	 */
	public void init(Context context, Request request, Response response) {
		beforeHandle(context, request, response);
		super.init(context, request, response);
		this.getVariants().add(new Variant(MediaType.ALL));
		String callMethod = request.getMethod().getName();

		log.info("method[" + callMethod + "] call!");

		afterHandle(context, request, response);
	}

	/**
	 * <p>
	 * Title: afterPropertiesSet
	 * </p>
	 * <p>
	 * Description: 系统支持各种请求方式
	 * </p>
	 */
	public void afterPropertiesSet() throws Exception {
		for (String method : ALLOWED_METHODS) {
			if ("get".equals(method.toLowerCase())) {
				this.canGet = true;
				continue;
			}
			if ("put".equals(method.toLowerCase())) {
				this.canPut = false;
				continue;
			}
			if ("post".equals(method.toLowerCase())) {
				this.canPost = true;
				continue;
			}
			if ("delete".equals(method.toLowerCase())) {
				this.canDelete = false;
			}
		}
		log.debug("support methods:get=" + this.canGet + ";put=" + this.canPut
				+ ";post=" + this.canPost + ";delete=" + this.canDelete);
	}

	public final boolean allowGet() {
		return canGet;
	}

	public final boolean allowPost() {
		return canPost;
	}

	public final boolean allowPut() {
		return canPut;
	}

	public final boolean allowDelete() {
		return canDelete;
	}
	
	@Override
	public Representation doHandle() throws ResourceException {
		this.callMethod();
		return super.doHandle();
	}
	/**
	 * <p>
	 * Title: storeRepresentation
	 * </p>
	 * <p>
	 * Description: 重写put请求
	 * </p>
	 */
	public final void storeRepresentation(Representation entity)
			throws ResourceException {

		callMethod();
	}

	/**
	 * <p>
	 * Title: acceptRepresentation
	 * </p>
	 * <p>
	 * Description: 重写post请求
	 * </p>
	 */
	public final void acceptRepresentation(Representation entity)
			throws ResourceException {
		callMethod();
	}

	public final void removeRepresentations() throws ResourceException {
		callMethod();
	}

	/**
	 * 请求回调资源
	 *
	 * @return
	 */
	private void callMethod() {
		try {
			String methodName = String.valueOf(getRequest().getAttributes()
					.get("method"));
			if (StringUtil.isEmpty(methodName)) {
				log.error("URI拼写错误，请按正确的方式拼写：服务/操作");
				throw new SysException("URI拼写错误，请按正确的方式拼写：服务/操作");
			}

			try {
				java.lang.reflect.Method callMethod = getClass().getMethod(
						methodName,
						new Class[] { BaseRequest.class, BaseResponse.class });
				if (callMethod == null) {
					throw new SysException("未找到对应的方法[" + methodName + "]！");
				}
				// 获取参数
				BaseResponse baseResponse = new BaseResponseImpl(
						this.getResponse());
				BaseRequest baseRequest = new BaseRequestImpl(this.getRequest());
				if (baseRequest.getJSONObject() != null) {
					jsonObject = net.sf.json.JSONObject.fromObject(baseRequest
							.getJSONObject().toString());
					ServerCall httpCall = ((HttpRequest) this.getRequest()).getHttpCall();
					HttpServletRequest httpServletRequest = ((ServletCall) httpCall).getRequest();
					httpServletRequest.setCharacterEncoding("utf-8");
					//params = 
					Map<String, String[]>	map=	httpServletRequest.getParameterMap();
					for (String key : map.keySet()) {
						params.put(key, Arrays.deepToString(map.get(key)));
					}
					if (!params.isEmpty()) {
						Set<String> keySet = params.keySet();
						for (String key : keySet) {
							try {
								if (StringUtils.isNotBlank(key)) {
									this.jsonObject.put(key, httpServletRequest
											.getParameter(key));
								}
							} catch (Exception e) {
								log.error("客户端拼装的Json串格式不对!");
								throw new SysException("客户端拼装的Json串格式不对!");
							}
						}

					}

				} else {
					params = baseRequest.getParamValueMap();
					jsonObject = net.sf.json.JSONObject.fromObject(params);
				}
				callMethod.invoke(this, baseRequest, baseResponse);
			} catch (NoSuchMethodException e) {

				throw new SysException("未找到对应的方法[" + methodName + "]！");
			}
		} catch (Exception ex) {
			try {
				if (ex instanceof BaseException) {
					log.error(ExceptionUtils.getFullStackTrace(ex));
					// 转换异常
					ExceptionUtil.extractException(ex);

				} else if (ex instanceof SysException) {
					log.error(ExceptionUtils.getFullStackTrace(ex));
					ExceptionUtil.extractException(ex);
				} else {
					log.error(ExceptionUtils.getFullStackTrace(ex));
					ExceptionUtil.extractException(ex);
				}

			} catch (Exception e) {
				log.error(ExceptionUtils.getFullStackTrace(ex));
				String errMsg = e.getMessage();
				if (StringUtil.isValid(errMsg)) {
					this.getResponse().setEntity(
							new BaseRepresention(false, errMsg));
				} else {
					this.getResponse().setEntity(
							new BaseRepresention(false, "服务端处理发生错误，请尝试重新操作！"));
				}
			}

		}
	}

	public net.sf.json.JSONObject getJsonObject() {
		return jsonObject;
	}

	public org.apache.velocity.context.Context getTransilinkViewContext() {
		return transilinkViewContext;
	}

	public void beforeHandle(Context context, Request request, Response response) {

	}

	public void afterHandle(Context context, Request request, Response response) {

	}

	public String getContextPath() {
		return PagerFilter.getRootPath();
	}

	/**
	 * 获取访问地址
	 *
	 * @param key
	 * @return
	 */
	protected String getUrl(String key) {
		if (UrlMapping.loadUrlMap != null) {
			if (UrlMapping.loadUrlMap.containsKey(key)) {
				return UrlMapping.loadUrlMap.get(key);
			}
		}
		return "";
	}

}
