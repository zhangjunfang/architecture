package com.transilink.framework.core.exception;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class KeyNotExistException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4629911597603549139L;

	public KeyNotExistException(Object key) {
		super("Key \"" + key + "\" does not exist!");
	}
}