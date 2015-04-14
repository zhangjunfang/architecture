package com.transilink.framework.core.exception;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class KeyNotExistException extends BaseException {
	public KeyNotExistException(Object key) {
		super("Key \"" + key + "\" does not exist!");
	}
}