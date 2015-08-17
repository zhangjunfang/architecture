package com.xxx.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * 描述： 下载处理Servlet
 * 
 * @author ocean 2015年8月16日 email：zhangjunfang0505@163.com
 */
public class DownloadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String DEFAULT_ENCODING = "UTF-8";

	private static final int DEFAULT_BUFFER_SIZE = 4096;

	private String encoding = DEFAULT_ENCODING;

	private int bufferSize = DEFAULT_BUFFER_SIZE;

	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);

		String paramEncoding = sc.getInitParameter("encoding");
		if (paramEncoding != null && paramEncoding.trim().length() > 0) {
			encoding = paramEncoding.trim();
		}

		String paramSize = sc.getInitParameter("bufferSize");
		if (paramSize != null && paramSize.matches("^[0-9]+$")) {
			bufferSize = Integer.parseInt(paramSize);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 查找文件
		File file = new File(getServletContext().getRealPath("/")
				+ request.getServletPath());
		if (!file.exists()) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		// 设置response
		response.setContentType("application/x-msdownload; charset=" + encoding);
		response.addHeader("content-type", "application/x-msdownload;");
		response.addHeader("content-disposition", "attachment; filename="
				+ response.encodeURL(file.getName()));
		response.setContentLength((int) file.length());

		// 输出
		InputStream in = new FileInputStream(file);
		OutputStream out = response.getOutputStream();
		try {
			int length = 0;
			byte[] buffer = new byte[bufferSize];
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			out.flush();
		} finally {
			if (in != null) {
				in.close();
			}
		}
	}

}
