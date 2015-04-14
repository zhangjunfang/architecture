package com.transilink.framework.core.exception;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class RedisAccessException extends SysException {
	private Collection exceptions = new ArrayList();
	private String messageKey = null;
	private Object[] messageArgs = null;

	public RedisAccessException() {
	}

	public RedisAccessException(String msg) {
		super(msg);
	}

	public RedisAccessException(Throwable cause) {
		this.rootCause = cause;
	}

	public RedisAccessException(String msg, Throwable cause) {
		super(msg);
		this.rootCause = cause;
	}

	public Collection getCollection() {
		return this.exceptions;
	}

	public void addException(RedisAccessException ex) {
		this.exceptions.add(ex);
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