package com.transilink.framework.core.rest;

import java.sql.Timestamp;
import java.util.Date;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.log4j.Logger;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

import com.transilink.framework.core.utils.stringUtils.JsonDateValueProcessor;

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月14日
 *  email：zhangjunfang0505@163.com
 */
public class Msg {
	protected Logger log = Logger.getLogger(getClass());

	private boolean success = false;
	private String msg;
	private String code;
	private Object data;

	public Msg() {
		// TODO
	}

	public Msg(String msg) {
		this(true, msg);
	}

	public Msg(boolean success, String msg) {
		this(success, msg, null);
	}

	public Msg(String msg, Object data) {
		this(true, msg, null);
	}

	public Msg(boolean success, String msg, Object data) {
		this.msg = msg;
		this.success = success;
		if (null == data || "null".equals(data))
			this.data = "";
		else
			this.data = data;
	}

	public Object getData() {
		return this.data;
	}

	public Msg setData(Object data) {
		this.data = data;
		return this;
	}

	public String getMsg() {
		return this.msg;
	}

	public Msg setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public boolean isSuccess() {
		return this.success;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Msg setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public JsonConfig getJsonConfig(String dateFormat) {
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,
				new JsonDateValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Timestamp.class,
				new JsonDateValueProcessor(dateFormat));
		return config;
	}

	public JSONObject toJSONObject() {
		String format = "yyyy-MM-dd HH:mm:ss";
		JSONObject jsonObject = new JSONObject();
		String newMsg = this.msg == null ? "" : this.msg;
		setMsg(newMsg);
		jsonObject = JSONObject.fromObject(this, getJsonConfig(format));
		return jsonObject;
	}

	public JSONObject toJSONObject(String dataFormat) {
		JSONObject jsonObject = new JSONObject();
		String newMsg = this.msg == null ? "" : this.msg;
		setMsg(newMsg);
		jsonObject = JSONObject.fromObject(this, getJsonConfig(dataFormat));
		return jsonObject;
	}

	public Representation toJSONObjectPresention() {
		return new StringRepresentation(toJSONObject().toString().replaceAll(
				"null", "\"\""), MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public Representation toJSONObjectPresention(String dataFormat) {
		return new StringRepresentation(toJSONObject(dataFormat).toString()
				.replaceAll("null", "\"\""), MediaType.TEXT_PLAIN, null,
				CharacterSet.UTF_8);
	}
}