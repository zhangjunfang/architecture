package com.ocean.rpc.server;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ocean.rpc.common.RpcFilter;
import com.ocean.rpc.common.RpcMethods;
import com.ocean.rpc.io.RpcClassManager;
import com.ocean.rpc.io.RpcMode;
import com.ocean.rpc.util.StrUtil;

/**
 * 
 * @author： ocean 创建时间：2015年12月18日 mail：zhangjunfang0505@163.com 描述：
 */
public class RpcServlet extends HttpServlet {

	private final static long serialVersionUID = 1716958719284073368L;
	private final RpcHttpService service = new RpcHttpService();

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String param = config.getInitParameter("mode");
		if (param != null) {
			param = param.toLowerCase();
			if (param.equals("propertymode")) {
				service.setMode(RpcMode.PropertyMode);
			} else if (param.equals("fieldmode")) {
				service.setMode(RpcMode.FieldMode);
			} else if (param.equals("membermode")) {
				service.setMode(RpcMode.MemberMode);
			}
		}
		param = config.getInitParameter("debug");
		if (param != null) {
			param = param.toLowerCase();
			if (param.equals("true")) {
				service.setDebugEnabled(true);
			}
		}
		param = config.getInitParameter("crossDomain");
		if (param != null) {
			param = param.toLowerCase();
			if (param.equals("true")) {
				service.setCrossDomainEnabled(true);
			}
		}
		param = config.getInitParameter("p3p");
		if (param != null) {
			param = param.toLowerCase();
			if (param.equals("true")) {
				service.setP3pEnabled(true);
			}
		}
		param = config.getInitParameter("get");
		if (param != null) {
			param = param.toLowerCase();
			if (param.equals("false")) {
				service.setGetEnabled(false);
			}
		}
		param = config.getInitParameter("event");
		if (param != null) {
			try {
				Class<?> type = Class.forName(param);
				if (RpcServiceEvent.class.isAssignableFrom(type)) {
					service.setEvent((RpcServiceEvent) type.newInstance());
				}
			} catch (Exception ex) {
				throw new ServletException(ex);
			}
		}
		param = config.getInitParameter("filter");
		if (param != null) {
			try {
				Class<?> type = Class.forName(param);
				if (RpcFilter.class.isAssignableFrom(type)) {
					service.setFilter((RpcFilter) type.newInstance());
				}
			} catch (Exception ex) {
				throw new ServletException(ex);
			}
		}
		RpcMethods methods = service.getGlobalMethods();
		param = config.getInitParameter("class");
		if (param != null) {
			try {
				String[] classNames = StrUtil.split(param, ',', 0);
				for (int i = 0, n = classNames.length; i < n; ++i) {
					String[] name = StrUtil.split(classNames[i], '|', 3);
					Class<?> type = Class.forName(name[0]);
					Object obj = type.newInstance();
					Class<?> ancestorType;
					if (name.length == 1) {
						methods.addInstanceMethods(obj, type);
					} else if (name.length == 2) {
						for (ancestorType = Class.forName(name[1]); ancestorType
								.isAssignableFrom(type); type = type
								.getSuperclass()) {
							methods.addInstanceMethods(obj, type);
						}
					} else if (name.length == 3) {
						if (name[1].equals("")) {
							methods.addInstanceMethods(obj, type, name[2]);
						} else {
							for (ancestorType = Class.forName(name[1]); ancestorType
									.isAssignableFrom(type); type = type
									.getSuperclass()) {
								methods.addInstanceMethods(obj, type, name[2]);
							}
						}
					}
				}
			} catch (Exception ex) {
				throw new ServletException(ex);
			}
		}
		param = config.getInitParameter("staticClass");
		if (param != null) {
			try {
				String[] classNames = StrUtil.split(param, ',', 0);
				for (int i = 0, n = classNames.length; i < n; ++i) {
					String[] name = StrUtil.split(classNames[i], '|', 2);
					Class<?> type = Class.forName(name[0]);
					if (name.length == 1) {
						methods.addStaticMethods(type);
					} else {
						methods.addStaticMethods(type, name[1]);
					}
				}
			} catch (ClassNotFoundException ex) {
				throw new ServletException(ex);
			}
		}
		param = config.getInitParameter("type");
		if (param != null) {
			try {
				String[] classNames = StrUtil.split(param, ',', 0);
				for (int i = 0, n = classNames.length; i < n; ++i) {
					String[] name = StrUtil.split(classNames[i], '|', 2);
					RpcClassManager.register(Class.forName(name[0]), name[1]);
				}
			} catch (ClassNotFoundException ex) {
				throw new ServletException(ex);
			}
		}
		setGlobalMethods(methods);
	}

	protected void setGlobalMethods(RpcMethods methods) {
	}

	protected void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		service.handle(new HttpContext(request, response, this
				.getServletConfig(), this.getServletContext()));
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	protected void doOptions(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	@Override
	public String getServletInfo() {
		return "Hprose Servlet 1.4";
	}
}
