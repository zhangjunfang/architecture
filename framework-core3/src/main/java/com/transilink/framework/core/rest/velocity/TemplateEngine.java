package com.transilink.framework.core.rest.velocity;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.transilink.framework.core.utils.springUtils.SpringConext;

/**
 *
 * <p>
 * 模版视图数据合成处理工具类
 * </p>
 * <p>
 * 暂只支持Velocity模版视图
 * </p>
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class TemplateEngine {
	private static final Log log = LogFactory.getLog(TemplateEngine.class);

	public static String parse(String templatePath, Context context)
			throws Exception {
		// 获取spring中velocity环境配置
		VelocityConfigurer vc = (VelocityConfigurer) SpringConext
				.getApplicationContext().getBean("velocityConfig");
		/* Velocity.getTemplate(templatePath, "utf-8"); */
		Template t = vc.getVelocityEngine().getTemplate(templatePath, "utf-8");
		// context.put("paging", "common/includePagination.vm");
		StringWriter sw = new StringWriter();
		t.merge(context, sw);
		return sw.toString();
	}

	static {
		// String templatePath = PathUtil.getRootPath();
		// Velocity.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, templatePath
		// + ConfigUtil.getItem("file.resource.loader.path"));
		// Velocity.setProperty("directive.set.null.allowed", "true");
		Velocity.setProperty(Velocity.SET_NULL_ALLOWED, "true");
		Velocity.setProperty("runtime.log.logsystem.class",
				"org.apache.velocity.runtime.log.NullLogSystem");
		// log.info("templatePath:" + templatePath);
		try {
			Velocity.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}