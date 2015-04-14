package com.transilink.framework.core.handler.springDatebind;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class CustomDateTimeDeserializer extends JsonDeserializer<Date> {
	Logger logger = Logger.getLogger(CustomDateTimeDeserializer.class);
	private static final String[] DATE_TIME = { "yyyy-MM-dd HH:mm:ss" };

	public Date deserialize(JsonParser parser, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		try {
			return DateUtils.parseDate(parser.getText(), DATE_TIME);
		} catch (ParseException e) {
			this.logger.error("ParseException: ", e);
		}
		return null;
	}
}