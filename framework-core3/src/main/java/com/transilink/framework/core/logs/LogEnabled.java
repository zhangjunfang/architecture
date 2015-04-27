package com.transilink.framework.core.logs;

import org.apache.log4j.Logger;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public abstract interface LogEnabled {
	public static final Logger log = Logger.getRootLogger();
}