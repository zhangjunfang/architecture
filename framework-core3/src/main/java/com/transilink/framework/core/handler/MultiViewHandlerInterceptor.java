package com.transilink.framework.core.handler;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.transilink.framework.core.exception.SysException;
import com.transilink.framework.core.logs.LogEnabled;
import com.transilink.framework.core.model.FileAttach;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("all")
public class MultiViewHandlerInterceptor extends HandlerInterceptorAdapter
		implements LogEnabled {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		MultiViewResource resource = null;

		if (handler instanceof MultiViewResource) {
			resource = (MultiViewResource) handler;
		}

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			if (handlerMethod.getBean() instanceof MultiViewResource) {
				resource = (MultiViewResource) handlerMethod.getBean();
			}
		}
		if (resource != null) {

			JSONObject jsonObject = null;

			List<FileAttach> fileAttachs = null;

			List<FileItem> fileItems = null;

			String method = request.getMethod();
			if ("GET".equals(method)) {
				jsonObject = getParameter(request);

			} else if ("POST".equals(method)) {
				String contentType = request.getContentType();
				// 有上传文件
				if (ServletFileUpload.isMultipartContent(request)) {
					DiskFileItemFactory fileFactory = new DiskFileItemFactory();
					fileFactory.setSizeThreshold(4194304);

					ServletFileUpload uploadFile = new ServletFileUpload(
							fileFactory);
					uploadFile.setHeaderEncoding("utf-8");
					uploadFile.setFileSizeMax(20971520L);
					try {
						fileItems = uploadFile.parseRequest(request);
						if (fileItems != null) {
							jsonObject = new JSONObject();
							fileAttachs = new ArrayList<FileAttach>();
							for (FileItem fileItem : fileItems) {
								if (fileItem.isFormField()) {
									jsonObject.put(fileItem.getFieldName(),
											fileItem.getString("utf-8"));
								} else {
									String profileName = fileItem
											.getFieldName();
									FileAttach file = new FileAttach();
									file.setFileName(fileItem.getName());
									file.setFileSize(fileItem.getSize());
									file.setFileType(fileItem.getContentType());
									file.setProName(profileName);
									file.setInputStream(fileItem
											.getInputStream());
									fileAttachs.add(file);
								}
							}
						}
					} catch (Exception e) {
						this.log.error("文件上传失败", e);
						throw new SysException("文件上传失败", e);
					}

				}
				if (contentType != null
						&& contentType.toLowerCase()
								.indexOf("application/json") != -1) {
					String encoding = request.getCharacterEncoding();
					if (encoding == null) {
						encoding = "UTF-8";
					}
					InputStream in = null;
					String jsonData = "";
					StringWriter writer = null;
					InputStreamReader reader = null;
					try {
						in = request.getInputStream();
						reader = new InputStreamReader(in, encoding);
						writer = new StringWriter();
						IOUtils.copy(reader, writer);
						jsonData = writer.toString();
					} catch (IOException e1) {
						this.log.error("出现解析IO异常!", e1);
						throw new SysException("出现解析IO异常!", e1);
					} finally {
						// 关闭流
						if (writer != null) {
							try {
								writer.close();
							} catch (IOException e) {
								log.error(null, e);
							}
						}
						if (reader != null) {
							try {
								reader.close();
							} catch (IOException e) {
								log.error(null, e);
							}
						}
						if (in != null) {
							try {
								in.close();
							} catch (IOException e) {
								log.error(null, e);
							}
						}
					}

					if (StringUtil.isEmpty(jsonData)) {
						this.log.error("客户端提交的Json数据为空");
						throw new SysException("客户端提交的Json数据为空！");
					}
					if ((!jsonData.startsWith("{"))
							|| (!jsonData.endsWith("}"))) {
						throw new SysException("客户端拼装的Json格式不对!");
					}
					try {
						jsonObject = JSONObject.fromObject(jsonData);
					} catch (JSONException e) {
						this.log.error("客户端拼装的Json串格式不对!");
						throw new SysException("客户端拼装的Json串格式不对!", e);
					}

				}
				if (contentType != null
						&& contentType.toLowerCase().indexOf(
								"application/x-www-form-urlencoded") != -1) {
					jsonObject = getParameter(request);
				}
			}

			resource.setJsonObject(jsonObject);
			resource.setfiles(fileAttachs);
		}

		return true;

	}

	/**
	 * @author 景明超 2013-12-6 下午1:25:43
	 * @param request
	 * @param jsonObject
	 * @return
	 * @throws JSONException
	 */
	private JSONObject getParameter(HttpServletRequest request)
			throws JSONException {
		JSONObject jsonObject = null;
		Map<String, Object> paramap = request.getParameterMap();
		if (!paramap.isEmpty()) {
			jsonObject = new JSONObject();
		}
		for (String key : paramap.keySet()) {
			jsonObject.put(key, request.getParameter(key));
		}
		return jsonObject;
	}
}
