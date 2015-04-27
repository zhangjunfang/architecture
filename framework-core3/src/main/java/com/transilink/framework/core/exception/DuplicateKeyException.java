package com.transilink.framework.core.exception;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class DuplicateKeyException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5945155582932210993L;

	public DuplicateKeyException(Object key) {
		super("Can not add a object with the duplicate key \"" + key + "\"!");
	}
}