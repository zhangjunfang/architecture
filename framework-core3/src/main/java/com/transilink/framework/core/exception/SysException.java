package com.transilink.framework.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class SysException extends RuntimeException {
	private static final long serialVersionUID = -5542169322764723764L;
	protected Throwable rootCause = null;
	private String messageKey = null;
	private Object[] messageArgs = null;

	public SysException() {
	}

	public SysException(String msg) {
		super(msg);
	}

	public SysException(Throwable cause) {
		this.rootCause = cause;
	}

	public SysException(String msg, Throwable cause) {
		super(msg);
		this.rootCause = cause;
	}

	public void setMessageKey(String key) {
		this.messageKey = key;
	}

	public String getMessageKey() {
		return this.messageKey;
	}

	public void setMessageArgs(Object[] args) {
		this.messageArgs = args;
	}

	public Object[] getMessageArgs() {
		return this.messageArgs;
	}

	public void setRootCause(Throwable anException) {
		this.rootCause = anException;
	}

	public Throwable getRootCause() {
		return this.rootCause;
	}

	public void printStackTrace() {
		printStackTrace(System.err);
	}

	public void printStackTrace(PrintStream outStream) {
		printStackTrace(new PrintWriter(outStream));
	}

	public void printStackTrace(PrintWriter writer) {
		super.printStackTrace(writer);

		if (getRootCause() != null) {
			getRootCause().printStackTrace(writer);
		}
		writer.flush();
	}
}