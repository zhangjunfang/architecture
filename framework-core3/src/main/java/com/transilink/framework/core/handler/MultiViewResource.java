package com.transilink.framework.core.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.transilink.framework.core.exception.BaseException;
import com.transilink.framework.core.exception.asserts.AssertObject;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.model.FileAttach;
import com.transilink.framework.core.rest.Msg;
import com.transilink.framework.core.rest.velocity.TemplateEngine;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 * @修改时间
 * @<p> 2014-12-22 15:25 </p>
 *
 */
public abstract class MultiViewResource implements LogEnabled {

	/* 获取参数 */
	private JSONObject jsonObject;
	/* 附件实体类 */
	private List<FileAttach> fileAttachs = null;

	@ExceptionHandler({ Exception.class })
	public void exceptionHandler(Exception e, HttpServletResponse response) {
		log.error(ExceptionUtils.getFullStackTrace(e));
		try {
			Msg msg = new Msg();
			msg.setMsg("系统出现错误了！");
			response.getWriter().write(msg.toJSONObject().toString());
		} catch (Exception ex) {
			e.printStackTrace();
		}
	}

	public List<FileAttach> getfiles() {
		return this.fileAttachs;
	}

	public List<FileAttach> setfiles(List<FileAttach> fileAttachs) {
		return this.fileAttachs = fileAttachs;
	}

	public JSONObject getJsonObject() {
		return this.jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

	/**
	 * 跳转页面
	 *
	 * @param path
	 * @param model
	 * @return
	 */
	protected ModelAndView toView(String path, Map<String, Object> model) {
		ModelAndView result = new ModelAndView(path, model);
		result.addAllObjects(model);
		return result;
	}

	protected String toHtml(String path, Map<String, Object> model) {
		Context ctx = new VelocityContext();
		for (String key : model.keySet()) {
			ctx.put(key, model.get(key));
		}
		String str = null;
		try {
			str = TemplateEngine.parse(path + ".html", ctx);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str;
	}

	/**
	 * 跳转页面
	 *
	 * @param path
	 * @return
	 */
	protected String toView(String path) {
		return path;
	}

	/**
	 * 重定位
	 *
	 * @param path
	 * @return
	 */
	protected String redirect(String path) {
		return "redirect:" + path;
	}

	/**
	 * 跳转
	 *
	 * @param path
	 * @return
	 */
	protected String forward(String path) {
		return "forward:" + path;
	}

	/**
	 * 获取访问地址
	 *
	 * @param key
	 * @return
	 */
	protected String getUrl(String key) {
		if (null != UrlMapping.loadUrlMap
				&& UrlMapping.loadUrlMap.containsKey(key)) {
			return (String) UrlMapping.loadUrlMap.get(key);
		}
		return "";
	}

	public Msg doExpAssert(AssertObject assertObject) {
		Msg msg = new Msg();
		try {
			assertObject.AssertMethod(msg);
		} catch (Exception e) {
			msg.setMsg("系统出错了!");
			log.error(ExceptionUtils.getFullStackTrace(e));
			if ((e instanceof BaseException)) {
				throw ((BaseException) e);
			}
			throw new BaseException("系统出错了!", e);
		}
		return msg;
	}
}