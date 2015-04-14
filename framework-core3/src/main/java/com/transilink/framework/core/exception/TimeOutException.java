package com.transilink.framework.core.exception;

/**
 *
 * @author huangxin (3203317@qq.com)
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