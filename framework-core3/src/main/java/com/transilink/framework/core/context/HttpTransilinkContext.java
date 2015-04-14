package com.transilink.framework.core.context;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author huangxin (3203317@qq.com)
 *
 */
public class HttpTransilinkContext extends AbstractTransilinkContext {
	protected Stack reqeustStack;

	HttpTransilinkContext() {
		this.reqeustStack = new Stack();
	}

	public HttpServletRequest getRequest() {
		HttpServletRequest httpservletrequest = null;

		while (httpservletrequest == null) {
			this.reqeustStack.peek();
			Reference reference = null;
			if ((reference = (Reference) this.reqeustStack.peek()) == null) {
				break;
			}
			if ((httpservletrequest = (HttpServletRequest) reference.get()) == null) {
				this.reqeustStack.pop();
			}
		}
		return httpservletrequest;
	}

	public void setRequest(HttpServletRequest httpservletrequest) {
		boolean flag = false;
		int i = this.reqeustStack.size();
		int j = 0;

		while (j < i) {
			Reference reference;
			if (((reference = (Reference) this.reqeustStack.get(j)) != null)
					&& (reference.get() == httpservletrequest)) {
				flag = true;
				for (int k = i - 1; k > j; k--) {
					this.reqeustStack.remove(k);
				}
				break;
			}
			j++;
		}
		if (!flag)
			this.reqeustStack.push(new WeakReference(httpservletrequest));
	}

	public String getParameter(String s) {
		return getRequest().getParameter(s);
	}

	public String[] getParameters(String s) {
		return getRequest().getParameterValues(s);
	}

	public Object getAttribute(int i, String s) {
		HttpServletRequest httpservletrequest = getRequest();
		switch (i) {
		case 1:
			return httpservletrequest.getAttribute(s);
		case 5:
			return httpservletrequest.getSession().getAttribute(s);
		case 9:
			return httpservletrequest.getSession().getServletContext()
					.getAttribute(s);
		case 2:
		case 3:
		case 4:
		case 6:
		case 7:
		case 8:
		}

		return null;
	}

	public void setAttribute(int i, String s, Object obj) {
		HttpServletRequest httpservletrequest = getRequest();
		switch (i) {
		case 1:
			httpservletrequest.setAttribute(s, obj);
			return;
		case 5:
			httpservletrequest.getSession().setAttribute(s, obj);
			return;
		case 9:
			httpservletrequest.getSession().getServletContext()
					.setAttribute(s, obj);
		case 2:
		case 3:
		case 4:
		case 6:
		case 7:
		case 8:
		}
	}

	public void removeAttribute(int i, String s) {
		HttpServletRequest httpservletrequest = getRequest();
		switch (i) {
		case 1:
			httpservletrequest.removeAttribute(s);
			return;
		case 5:
			httpservletrequest.getSession().removeAttribute(s);
			return;
		case 9:
			httpservletrequest.getSession().getServletContext()
					.removeAttribute(s);
		}
	}
}