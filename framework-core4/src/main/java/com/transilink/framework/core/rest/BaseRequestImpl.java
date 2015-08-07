package com.transilink.framework.core.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.restlet.Request;
import org.restlet.data.CharacterSet;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Method;
import org.restlet.data.Parameter;
import org.restlet.engine.adapter.HttpRequest;
import org.restlet.engine.adapter.ServerCall;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.ext.servlet.internal.ServletCall;
import org.restlet.representation.Representation;

import com.transilink.framework.core.exception.SysException;
import com.transilink.framework.core.filter.PagerFilter;
import com.transilink.framework.core.model.FileAttach;
import com.transilink.framework.core.model.variant.VariantSet;
import com.transilink.framework.core.model.variant.VariantUtil;
import com.transilink.framework.core.utils.clazzUtils.BeanUtils;
import com.transilink.framework.core.utils.stringUtils.StringUtil;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
@SuppressWarnings("all")
public class BaseRequestImpl implements BaseRequest {
	private Logger log = Logger.getLogger(getClass());

	/* 获取系统上下文路径 */
	private String contextPath;

	/** Rest框架封装后的Request */
	Request request = null;

	/** Rest框架封装后的资源对象类型 */
	private Representation representation = null;

	/** Rest框架封装后的Form对象类型 */
	Form form = null;

	/** 提交数据格式类型 */
	private MediaType mediaType;

	/** 封装的Json格式数据 */
	JSONObject jsonObject = null;
	/* 封装附件集合类 */
	private List<FileAttach> fileAttachs = null;

	/** 封装的上传文件数据 */
	List<FileItem> fileItems;

	HttpServletRequest httpServletRequest = null;
	HttpServletResponse httpServletResponse = null;

	public BaseRequestImpl(Request request) {
		this.request = request;
		this.representation = request.getEntity();
		this.mediaType = this.representation.getMediaType();
		// 初始化数据格式
		init();

		if (this.form != null && this.form.size() == 0) {
			ServerCall httpCall = ((HttpRequest) this.request).getHttpCall();
			HttpServletRequest httpServletRequest = ((ServletCall) httpCall).getRequest();
			try {
				httpServletRequest.setCharacterEncoding("utf-8");
				Enumeration paramNames = httpServletRequest.getParameterNames();
				while (paramNames.hasMoreElements()) {
					String name = (String) paramNames.nextElement();
					String value = httpServletRequest.getParameter(name);
					this.form.add(new Parameter(name, value));
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @return the representation
	 */
	public Representation getRepresentation() {
		return representation;
	}
	public void init() {
		ServerCall httpCall = ((HttpRequest) this.request).getHttpCall();
		httpServletRequest = ((ServletCall) httpCall).getRequest();
		httpServletResponse = ((ServletCall) httpCall).getResponse();
		this.representation.setCharacterSet(CharacterSet.UTF_8);
		if (this.request.getMethod() == Method.GET) {
			// Form
			this.form = this.request.getResourceRef().getQueryAsForm();
		} else if (this.request.getMethod() == Method.POST) {
			// UploadFile
			if (this.representation != null
					&& MediaType.MULTIPART_FORM_DATA.equals(mediaType, true)) {
				DiskFileItemFactory fileFactory = new DiskFileItemFactory();
				fileFactory.setSizeThreshold(4 * 1024 * 1024);

				RestletFileUpload uploadFile = new RestletFileUpload(
						fileFactory);
				uploadFile.setHeaderEncoding("utf-8");
				uploadFile.setFileSizeMax(20 * 1024 * 1024);
				try {
					this.fileItems = uploadFile.parseRequest(this.request);
					if (null != this.fileItems) {
						this.jsonObject = new JSONObject();
						this.fileAttachs = new ArrayList<FileAttach>();
						for (FileItem fileItem : fileItems) {
							// 获取基本属性
							if (fileItem.isFormField())
								jsonObject.put(fileItem.getFieldName(),
										fileItem.getString("utf-8"));
							// 获取附件属性
							else {
								String profileName = fileItem.getFieldName();
								FileAttach file = new FileAttach();
								file.setFileName(fileItem.getName());
								file.setFileSize(fileItem.getSize());
								file.setFileType(fileItem.getContentType());
								file.setProName(profileName);
								file.setInputStream(fileItem.getInputStream());
								fileAttachs.add(file);
							}
						}
					}

				} catch (Exception e) {
					log.error("文件上传失败", e);
					throw new SysException("文件上传失败", e);
				}
				return;
			}

			// JSON
			if (this.representation != null
					&& MediaType.APPLICATION_JSON.equals(mediaType, true)) {
				try {
					this.representation.setCharacterSet(CharacterSet.UTF_8);
					String jsonData = null;
					jsonData = this.representation.getText();
					if (StringUtil.isEmpty(jsonData)) {
						log.error("客户端提交的Json数据为空");
						throw new SysException("客户端提交的Json数据为空！");
					}
					if (!jsonData.startsWith("{") || !jsonData.endsWith("}")) {
						throw new SysException("客户端拼装的Json格式不对!");
					}
					this.jsonObject = new JSONObject(jsonData);
				} catch (IOException e) {
					log.error("出现解析IO异常!");
					throw new SysException("出现解析IO异常!");
				} catch (JSONException e) {
					log.error("客户端拼装的Json串格式不对!");
					throw new SysException("客户端拼装的Json串格式不对!");
				}
				return;
			}

			if (this.representation != null
					&& MediaType.APPLICATION_WWW_FORM.equals(mediaType, true)) {
				this.representation.setCharacterSet(CharacterSet.UTF_8);
				this.form = new Form(this.representation);
			}
			return;
		}
		if (this.representation != null
				&& MediaType.TEXT_XML.equals(mediaType, true)) {
			try {
			} catch (Exception e) {
				log.error("IO异常!");
				throw new SysException("IO异常!");
			}
			return;
		}
		if (this.representation != null
				&& MediaType.TEXT_PLAIN.equals(mediaType, true)) {
			this.representation.setCharacterSet(CharacterSet.UTF_8);
			setJsonObjectValues();
			this.form = new Form(this.representation);
			return;
		}
		if (this.representation != null
				&& MediaType.TEXT_HTML.equals(mediaType, true)) {
			this.representation.setCharacterSet(CharacterSet.UTF_8);
			setJsonObjectValues();
			this.form = new Form(this.representation);
			return;
		}
	}

	/**
	 * jsonOjbect赋值
	 */
	private void setJsonObjectValues() {
		Map map = httpServletRequest.getParameterMap();
		if (!map.isEmpty()) {
			Set<String> keySet = map.keySet();
			for (String key : keySet) {
				try {
					if (StringUtils.isNotBlank(key)) {
						this.jsonObject.put(key,
								httpServletRequest.getParameter(key));
					}
				} catch (JSONException e) {
					log.error("客户端拼装的Json串格式不对!");
					throw new SysException("客户端拼装的Json串格式不对!");
				}
			}

		}
	}

	// }

	public JSONObject getJSONObject() {
		return this.jsonObject;
	}

	public String getParameter(String paramName) {
		return this.form.getValues(paramName);
	}

	public String[] getParameters(String paramName) {
		return this.form.getValuesArray(paramName);
	}

	public List<FileItem> getUploadFileItems() {
		return this.fileItems;
	}

	/**
	 * 获取参数名称
	 */
	public String[] getParamNames() {
		String[] names = null;
		Object[] params = this.form.getNames().toArray();
		if (params == null || params.length == 0) {
			return new String[] {};
		}
		names = new String[params.length];
		for (int i = 0, len = params.length; i < len; i++) {
			names[i] = params[i].toString();
		}
		return names;
	}

	public Map<String, String> getParamValueMap() {
		if (form == null) {
			return null;
		}
		return this.form.getValuesMap();
	}

	/**
	 * 获取参数，此方法总是返回一个有效地String, 不会返回null.
	 *
	 * @param name
	 * @return
	 */
	public String getString(String name) {
		String str = getParameter(name);
		if (str == null) {
			str = "";
		}
		return str;
	}

	/**
	 * 获取参数，此方法总是返回一个有效地String, 不会返回null
	 *
	 * @param name
	 *            参数名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public String getString(String name, String defaultValue) {
		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return str;
	}

	/**
	 * bigdecimal型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public BigDecimal getBigdecimal(String name) {

		return VariantUtil.parseBigDecimal(getParameter(name));

	}

	/**
	 * 按bigdecimal型获取参数值
	 *
	 * @param name
	 *            参数名称
	 * @param defaultValue
	 *            默认值
	 * @return
	 */
	public BigDecimal getBigdecimal(String name, BigDecimal defaultValue) {
		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return VariantUtil.parseBigDecimal(str);
	}

	/**
	 * 按int型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public int getInt(String name) {
		return VariantUtil.parseInt(getParameter(name));
	}

	/**
	 * 按int型获取参数值
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public int getInt(String name, int defaultValue) {
		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return VariantUtil.parseInt(str);

	}

	/**
	 * 按long型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public long getLong(String name) {
		return VariantUtil.parseLong(getParameter(name));
	}

	/**
	 * 按long型获取参数值
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public long getLong(String name, long defaultValue) {
		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return VariantUtil.parseLong(str);

	}

	/**
	 * 按float型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public float getFloat(String name) {
		return VariantUtil.parseFloat(getParameter(name));
	}

	/**
	 * 按float型获取参数值
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public float getFloat(String name, float defaultValue) {

		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return VariantUtil.parseFloat(str);
	}

	/**
	 * 按double型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public double getDouble(String name) {
		return VariantUtil.parseDouble(getParameter(name));
	}

	/**
	 * 按double型获取参数值
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public double getDouble(String name, double defaultValue) {
		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return VariantUtil.parseDouble(str);
	}

	/**
	 * 按boolean型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public boolean getBoolean(String name) {
		return VariantUtil.parseBoolean(getParameter(name));
	}

	/**
	 * 按boolean型获取参数值
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public boolean getBoolean(String name, boolean defaultValue) {
		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;
		return VariantUtil.parseBoolean(str);
	}

	/**
	 * 按date型获取参数值
	 *
	 * @param name
	 * @return
	 */
	public Date getDate(String name) {
		return VariantUtil.parseDate(getParameter(name));
	}

	/**
	 * 按date型获取参数值
	 *
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public Date getDate(String name, Date defaultValue) {

		String str;
		if ((str = getParameter(name)) == null)
			return defaultValue;

		return VariantUtil.parseDate(str);

	}

	/**
	 * 返回参数值数组.该方法返回的每个参数值始终不为NULL
	 *
	 * @param name
	 * @return
	 */
	public String[] getStringValues(String name) {
		String[] values = this.getParameters(name);
		int len = 0;
		if (values != null && ((len = values.length) != 0)) {
			for (int i = 0; i < len; i++) {
				if (values[i] == null) {
					values[i] = "";
				}
			}
		}
		return values;
	}

	/**
	 * 将当前Request中包含的参数(Parameter)设置到给定的对象当中.
	 *
	 * @param object
	 * @throws Exception
	 */
	public void parameter(Object object) {
		if (object instanceof Map) {
			parametersToMap((Map) object);
			return;
		}
		if (object instanceof VariantSet) {
			parametersToVariantSet((VariantSet) object);
			return;
		}
		parametersToBean(object);
	}

	/**
	 * 将当前Request中包含的参数(Parameter)设置到Map当中.
	 *
	 * @return
	 */
	public Map parametersToMap() {
		HashMap hashMap = new HashMap();
		parametersToMap(hashMap);
		return hashMap;
	}

	/**
	 * 将当前Request中包含的参数(Parameter)设置到Bean当中.
	 *
	 * @param object
	 * @throws Exception
	 */
	protected void parametersToBean(Object object) {
		String[] paramNames = this.getParamNames();
		for (int i = 0, len = paramNames.length; i < len; i++) {
			String name = paramNames[i];
			String value = this.getParameter(name);
			BeanUtils.copyProperty(object, name, value);
		}

	}

	/**
	 * 将当前Request中包含的参数(Parameter)设置到Map当中.
	 *
	 * @param map
	 */
	@SuppressWarnings("unchecked")
	protected void parametersToMap(Map map) {
		String[] paramNames = this.getParamNames();
		for (int i = 0, len = paramNames.length; i < len; i++) {
			String name = paramNames[i];
			String value = this.getParameter(name);
			map.put(name, value);
		}
	}

	/**
	 * 当前Request中包含的参数(Parameter)设置到VariantSet当中.
	 *
	 * @param variantSet
	 */
	protected void parametersToVariantSet(VariantSet variantSet) {
		String[] paramNames = this.getParamNames();
		for (int i = 0, len = paramNames.length; i < len; i++) {
			String name = paramNames[i];
			variantSet.setString(name, this.getParameter(name));
		}
	}

	/**
	 * 将当前Request中包含的属性(Attribute)设置到给定的对象当中.
	 *
	 * @param dest
	 * @throws Exception
	 */
	public void attributies(Object dest) {
		if (dest instanceof Map) {
			attributiesToMap((Map) dest);
			return;
		}
		if (dest instanceof VariantSet) {
			attributiesToVariantSet((VariantSet) dest);
			return;
		}
		attributiesToBean(dest);
	}

	/**
	 * 当前Request中包含的属性(Attribute)设置到Map对象当中
	 *
	 * @return
	 */
	public Map attributiesToMap() {
		HashMap map = new HashMap();
		attributiesToMap(map);
		return map;
	}

	/**
	 * 当前Request中包含的属性(Attribute)设置到Bean对象当中
	 *
	 * @return
	 */
	protected void attributiesToBean(Object dest) {
		Map attributeNames = this.request.getAttributes();
		for (Iterator iterator = attributeNames.entrySet().iterator(); iterator
				.hasNext();) {
			Entry entry = (Entry) iterator.next();
			BeanUtils.copyProperty(dest, (String) entry.getKey(),
					entry.getValue());
		}
	}

	/**
	 * 当前Request中包含的属性(Attribute)设置到Map对象当中
	 *
	 * @return
	 */
	protected void attributiesToMap(Map map) {
		Map attributeNames = this.request.getAttributes();
		for (Iterator iterator = attributeNames.entrySet().iterator(); iterator
				.hasNext();) {
			Entry entry = (Entry) iterator.next();
			map.put(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * 当前Request中包含的属性(Attribute)设置到VariantSet对象当中
	 *
	 * @param variant
	 */
	protected void attributiesToVariantSet(VariantSet variant) {

		Map attributeNames = this.request.getAttributes();
		for (Iterator iterator = attributeNames.entrySet().iterator(); iterator
				.hasNext();) {
			Entry entry = (Entry) iterator.next();
			variant.setValue((String) entry.getKey(), entry.getValue());
		}
	}

	public String getContextPath() {
		return PagerFilter.getRootPath();
	}

	/**
	 * 获取原始request
	 */
	public Request getOrginRequest() {
		//  Auto-generated method stub
		return this.request;
	}

	@Override
	public List<FileAttach> getfiles() {
		return fileAttachs;
	}
}
