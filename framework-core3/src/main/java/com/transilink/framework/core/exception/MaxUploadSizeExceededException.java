package com.transilink.framework.core.exception;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
@SuppressWarnings("unused")
public class MaxUploadSizeExceededException extends BaseException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 896441315393160267L;
	private long maxUploadSize;
	private String message;
	private String code;

	public MaxUploadSizeExceededException() {
	}

	public MaxUploadSizeExceededException(long maxUploadSize, String message) {
		this.maxUploadSize = maxUploadSize;
		this.message = message;
	}
}