package cn.appsys.controller.dev;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.dev.DevUserService;
import cn.appsys.tools.Constants;

@Controller
public class DevUserLoginController {
	@Resource
	private DevUserService devUserService;
	
	@RequestMapping(value="/devlogin",method=RequestMethod.GET)
	public String login(){
		return "dlogin";
	}
	
	/*登录界面*/
	@RequestMapping(value="/devWelcome",method=RequestMethod.POST)
	public String welcome(String devCode,String devPassword,HttpSession session,HttpServletRequest request){
		System.out.println("进入开发登录页面判断=============用户编码："+devCode+"用户密码："+devPassword);
	
		DevUser devUser =devUserService.login(devCode, devPassword);
		
		if(devUser !=null){
			
			session.setAttribute(Constants.DEV_USER_SESSION,devUser );
			return "redirect:/dev/dwelcome";
		}else{
			request.setAttribute("error", "用户名或密码不正确！");
		}
		return "dlogin";
	}
	
	/*注销界面*/
	@RequestMapping(value="/dev/logout",method=RequestMethod.GET)
	public String logOut(HttpSession session){
		session.removeAttribute(Constants.DEV_USER_SESSION);
		
		return "index";
	}
	
	
}
