package com.transilink.framework.core.handler.springDatebind;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;

import com.transilink.framework.core.logs.LogEnabled;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class JackJson implements LogEnabled {
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	public static <T> T fromJsonToObject(String json,
			TypeReference<T> typeReference) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		} catch (JsonMappingException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		} catch (IOException e) {
			log.error(ExceptionUtils.getStackTrace(e));
		}
		return null;
	}

	public static <T> T fromJsonToObject(String json, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			log.error("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectToJson(Object object) {
		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			log.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}

	public static String printFromObjectToJson(Object object) {
		if (!log.isDebugEnabled()) {
			return null;
		}

		if (object == null) {
			return null;
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			String obj = mapper.writeValueAsString(object);
			log.debug("\n" + obj);
			return obj;
		} catch (JsonGenerationException e) {
			log.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectToJson(Object object, String filterName,
			Set<String> properties) {
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider().addFilter(
				filterName,
				SimpleBeanPropertyFilter.serializeAllExcept(properties));
		try {
			return mapper.filteredWriter(filters).writeValueAsString(object);
		} catch (JsonGenerationException e) {
			log.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectToJson(Object object, String filterName,
			String property) {
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider().addFilter(
				filterName, SimpleBeanPropertyFilter
						.serializeAllExcept(new String[] { property }));
		try {
			return mapper.filteredWriter(filters).writeValueAsString(object);
		} catch (JsonGenerationException e) {
			log.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectHasDateToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withDateFormat(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			log.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectHasDateToJson(Object object,
			String dateTimeFormatString) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withDateFormat(
				new SimpleDateFormat(dateTimeFormatString));
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			log.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			log.error("JsonMappingException: ", e);
		} catch (IOException e) {
			log.error("IOException: ", e);
		}
		return null;
	}
}