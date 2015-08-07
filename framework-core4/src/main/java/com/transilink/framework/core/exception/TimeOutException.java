package com.transilink.framework.core.exception;

/**
 *
 * @author ocean(zhangjufang0505@163.com)
 *
 */
public class TimeOutException extends BaseException {
	private static final long serialVersionUID = 3957340248111739698L;

	public TimeOutException() {
	}

	public TimeOutException(String message) {
		super(message);
	}
}