package com.transilink.framework.core.rest;

import java.io.IOException;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.restlet.data.CharacterSet;
import org.restlet.data.MediaType;
import org.restlet.resource.Representation;
import org.restlet.resource.StringRepresentation;

import com.transilink.framework.core.model.variant.Variant;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class BaseRepresention extends StringRepresentation {
	public BaseRepresention(Msg message) {
		super(message.toJSONObject().toString(), MediaType.TEXT_PLAIN, null,
				CharacterSet.UTF_8);
	}

	public BaseRepresention(boolean flag, String msg) {
		super(new Msg(flag, msg).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public BaseRepresention(boolean flag, String msg, Object data) {
		super(new Msg(flag, msg, data).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public BaseRepresention(String msg, Object data) {
		super(new Msg(true, msg).setData(data).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public BaseRepresention(org.json.JSONObject jsonObject) {
		this(jsonObject.toString());
	}

	public BaseRepresention(Map<Object, Object> map) {
		super(new Msg().setData(map).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public BaseRepresention(Variant variant) {
		super(new Msg().setData(variant).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public BaseRepresention(Object data) {
		super(new Msg().setData(data).toJSONObject().toString(),
				MediaType.TEXT_PLAIN, null, CharacterSet.UTF_8);
	}

	public BaseRepresention(Representation jsonRepresentation)
			throws IOException {
		this(jsonRepresentation.getText());
	}

	public JSONArray toJsonArray() throws Exception {
		return new JSONArray(getText());
	}

	public org.json.JSONObject toJsonObject() throws JSONException {
		return new org.json.JSONObject(getText());
	}
}