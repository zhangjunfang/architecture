package com.transilink.framework.core.logs;

import org.apache.log4j.Logger;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public abstract interface LogEnabled {
	public static final Logger log = Logger.getRootLogger();
}