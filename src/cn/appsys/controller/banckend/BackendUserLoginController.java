package cn.appsys.controller.banckend;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import cn.appsys.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



import cn.appsys.pojo.BackendUser;
import cn.appsys.service.backend.backendUserService.BackendUserService;

@Controller

public class BackendUserLoginController {
	@Resource
	private BackendUserService backendUserService;
	@RequestMapping(value="/backlogin",method=RequestMethod.GET)
	public String login(){
		return "blogin";
	}
	
	@RequestMapping(value="/bWelcome",method=RequestMethod.POST)
	public String welcome(String userCode,
			String userPassword,
			HttpSession session,
			HttpServletRequest request){
		//调用service方法，进行用户匹配
		BackendUser bUserbackend=backendUserService.login(userCode, userPassword);
		
		if(bUserbackend !=null){
			session.setAttribute(Constants.USER_SESSION,bUserbackend );
			return "redirect:/back/backlogin";
		}else{
			request.setAttribute("error", "用户名或密码不正确！");
		}
		return "blogin";
	}
	/*注销界面*/
	@RequestMapping(value="/back/logout",method=RequestMethod.GET)
	public String logOut(HttpSession session){
		session.removeAttribute(Constants.USER_SESSION);
		
		return "index";
	}
	
	
}
