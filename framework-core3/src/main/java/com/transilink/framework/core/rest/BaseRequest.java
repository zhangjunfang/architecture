package com.transilink.framework.core.rest;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONObject;
import org.restlet.data.Request;

import com.transilink.framework.core.model.FileAttach;

/**
 * 
 * 描述：
 * 
 * @author ocean
 * 2015年4月14日
 *  email：zhangjunfang0505@163.com
 */
@SuppressWarnings("rawtypes")
public abstract interface BaseRequest {
	public abstract JSONObject getJSONObject();

	public abstract String getParameter(String paramString);

	public abstract String[] getParameters(String paramString);

	public abstract List<FileItem> getUploadFileItems();

	public abstract List<FileAttach> getfiles();

	public abstract String[] getParamNames();

	public abstract Map<String, String> getParamValueMap();

	public abstract String getString(String paramString);

	public abstract String getString(String paramString1, String paramString2);

	public abstract int getInt(String paramString);

	public abstract int getInt(String paramString, int paramInt);

	public abstract long getLong(String paramString);

	public abstract long getLong(String paramString, long paramLong);

	public abstract float getFloat(String paramString);

	public abstract float getFloat(String paramString, float paramFloat);

	public abstract double getDouble(String paramString);

	public abstract double getDouble(String paramString, double paramDouble);

	public abstract boolean getBoolean(String paramString);

	public abstract boolean getBoolean(String paramString, boolean paramBoolean);

	public abstract Date getDate(String paramString);

	public abstract Date getDate(String paramString, Date paramDate);

	public abstract String[] getStringValues(String paramString);

	public abstract void parameterToDO(Object paramObject);

	public abstract Map parametersToMap();

	public abstract void attributiesToDO(Object paramObject);

	public abstract Map attributiesToMap();

	public abstract Request getOrginRequest();

	public abstract BigDecimal getBigdecimal(String paramString);

	public abstract BigDecimal getBigdecimal(String paramString,
			BigDecimal paramBigDecimal);
}