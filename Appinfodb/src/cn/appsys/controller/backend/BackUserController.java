package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.BackendUserService;
import cn.appsys.tools.Constants;

@Controller
@RequestMapping(value="/manager")
public class BackUserController {
	
	@Resource
	private BackendUserService backUserService;

	@RequestMapping(value="/login.html")
	public String backend() {
		return "/backendlogin";
	}
	
	@RequestMapping(value="/dologin")
		public String  main(HttpServletRequest res,@RequestParam(value="userCode",required=false)String name,@RequestParam(value="userPassword",required=false)String pwd) {
		BackendUser backensUser = backUserService.backLogin(name, pwd);
		if(backensUser != null) {
			res.getSession().setAttribute(Constants.USER_SESSION, backensUser);
			return "backend/main";
		}else {
			return "backend/login";
		}
	}
	@RequestMapping(value="/logout")
	public String logout(HttpServletRequest res) {
		res.getSession().removeAttribute(Constants.USER_SESSION);
		return "redirect:/index.jsp";
	}
}
