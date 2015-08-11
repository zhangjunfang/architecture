package cn.newcapec.foundation.privilege.servlet;

import cn.newcapec.framework.core.logs.LogEnabled;
import org.apache.commons.lang.exception.ExceptionUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * <p>生成验证码</p>
 * @author andy.li
 */
public class CharServlet extends HttpServlet implements LogEnabled {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
		int width = 60;
		int height = 20;
		char mapTable[] = { 'a', 'b', 'c', 'd', 'e', 'h', 'j', 'k', 'm', 'n', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '2', '3', '4', '5', '6', '7', '8', '9' };

		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// 获取图形上下文
		Graphics g = image.getGraphics();
		// 设定背景颜色
		g.setColor(new Color(0xDCDCDC));
		g.fillRect(0, 0, width, height);

		// 画边框
		g.setColor(Color.black);
		g.drawRect(0, 0, width - 1, height - 1);
		// 随机产生的验证码
		String strEnsure = "";
		// 4代表4位验证码，如果要生成等多位的验证码 ，则加大数值

		for (int i = 0; i < 4; i++) {
			strEnsure += mapTable[(int) (mapTable.length * Math.random())];
		}
		// 将验证码显示在图像中，如果要生成更多位的验证码，增加drawString语句
		g.setColor(Color.black);
		g.setFont(new Font("Atlantic Inline", Font.PLAIN, 18));
		String str = strEnsure.substring(0, 1);
		g.drawString(str, 8, 17);
		str = strEnsure.substring(1, 2);
		g.drawString(str, 20, 15);
		str = strEnsure.substring(2, 3);
		g.drawString(str, 35, 18);
		str = strEnsure.substring(3, 4);
		g.drawString(str, 45, 15);
		// 随机产生10个干扰点

		Random random = new Random();
		for (int i = 0; i < 10; i++) {
			int x = random.nextInt(width);
			int y = random.nextInt(height);
			g.drawOval(x, y, 1, 1);
		}
		// 释放图形上下文
		g.dispose();
		// 将四位数字的验证码保存到Session中。
		HttpSession session = request.getSession();
		session.removeAttribute("validateCode");
		session.setAttribute("validateCode", strEnsure);
		// 禁止图像缓存。
		response.setContentType("image/jpeg");
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
	
			ImageIO.write(image, "jpeg", response.getOutputStream());
		} catch (Exception e) {
			log.error(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
	}
}
