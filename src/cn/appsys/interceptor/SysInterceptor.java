package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;
import cn.appsys.tools.Constants;



/*拦截类*/
public class SysInterceptor extends HandlerInterceptorAdapter {
	private Logger logger=Logger.getLogger(SysInterceptor.class);
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response,
			Object handler)throws Exception{
		HttpSession session=request.getSession();
		BackendUser duser=(BackendUser) session.getAttribute(Constants.USER_SESSION);
		DevUser buser=(DevUser) session.getAttribute(Constants.DEV_USER_SESSION);
		if(null!=duser){
			return true;
		}else if(null!=buser){
			return true;
		}else{
			response.sendRedirect(request.getContextPath()+"/403.jsp");
			return false;
		}
		
	}

}
