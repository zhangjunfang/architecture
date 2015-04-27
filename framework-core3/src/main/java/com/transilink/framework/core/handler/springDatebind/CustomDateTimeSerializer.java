package com.transilink.framework.core.handler.springDatebind;

import java.io.IOException;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class CustomDateTimeSerializer extends JsonSerializer<Date> {
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		jgen.writeString(DateFormatUtils.format(value, "yyyy-MM-dd HH:mm:ss"));
	}
}