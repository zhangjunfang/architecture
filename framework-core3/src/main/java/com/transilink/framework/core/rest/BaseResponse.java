package com.transilink.framework.core.rest;

import java.util.Map;

import org.apache.velocity.context.Context;
import org.json.JSONObject;
import org.restlet.data.Response;
import org.restlet.resource.Representation;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract interface BaseResponse {
	public abstract void toMultiView(String paramString, Map paramMap);

	public abstract void print(Representation paramRepresentation);

	public abstract void toView(String paramString, Context paramContext);

	public abstract void print(String paramString);

	public abstract void print(JSONObject paramJSONObject);

	public abstract Response getOrignResponse();
}