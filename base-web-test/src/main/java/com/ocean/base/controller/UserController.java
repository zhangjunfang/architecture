package com.ocean.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ocean.base.pojo.User;
import com.ocean.base.service.IUserService;
/**
 * 描述：
 * 
 * @author ocean
 * 2015年12月12日
 * email：zhangjunfang0505@163.com
 */
@Controller
public class UserController {
	@Resource
	private IUserService userService;
	
	@RequestMapping(value="/showUser",method={RequestMethod.GET})
	public String toIndex(HttpServletRequest request,Model model){
		int userId = Integer.parseInt(request.getParameter("id"));
		User user = this.userService.getUserById(userId);
		model.addAttribute("user", user);
		return "showUser";
	}
}
