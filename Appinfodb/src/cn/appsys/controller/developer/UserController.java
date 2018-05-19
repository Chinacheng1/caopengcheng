package cn.appsys.controller.developer;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping("/userdve")
public class UserController {
	@Resource
	private DevUserService devUserService;
	@RequestMapping(value="/dev.html")
	public String devLogin() {
		return "devlogin";
	}
	/**
	 * 登录方法
	 * @param devCode
	 * @param devPassword
	 * @param res
	 * @return
	 */
	@RequestMapping(value="/login.html")
	public String login(@RequestParam(value="devCode",required=false) String devCode,@RequestParam(value="devPassword",required=false) String devPassword,HttpServletRequest res) {
		DevUser user =  devUserService.login(devCode, devPassword);
		if(user != null) {
			res.getSession().setAttribute(Constants.DEV_USER_SESSION, user);
			return "developer/main";
		}
		return "devlogin";
	}
	
	@RequestMapping(value="/logout")
	public String Cancellation(HttpServletRequest res) {
		
		res.getSession().removeAttribute(Constants.DEV_USER_SESSION);
		return "redirect:/index.jsp";
	}
}
