package com.transilink.framework.core.rest;

import java.util.Map;

import org.apache.velocity.context.Context;
import org.json.JSONObject;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月14日
 *  email：zhangjunfang0505@163.com
 */
public abstract interface BaseResponse {
	@SuppressWarnings("rawtypes")
	public abstract void toMultiView(String paramString, Map paramMap);

	public abstract void print(Representation paramRepresentation);

	public abstract void toView(String paramString, Context paramContext);

	public abstract void print(String paramString);

	public abstract void print(JSONObject paramJSONObject);

	public abstract Response getOrignResponse();
}