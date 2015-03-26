package com.wafersystems.mrbs.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.user.UserInfo;

public class Out2SystemInteceptor extends HandlerInterceptorAdapter {

	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	//	String direct_temp = request.getParameter("direct");
	//	if(StringUtils.isNotBlank(direct_temp)){
			String username = request.getParameter("uid");
			String pwd = request.getParameter("pwd");
//			String direct = URLDecoder.decode(direct_temp, "utf-8");

			UserInfo user = userService.checkUserLogin(username, pwd);
			user.setUserLanguage(GlobalParam.language);
			request.getSession().setAttribute(GlobalConstent.USER_LOGIN_SESSION, user);

		//	request.getRequestDispatcher(direct).forward(request, response);
			request.getRequestDispatcher("/index/").forward(request, response);//转到用户登录主页
			return false;
	//	}
//		return true;//不执行后续
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}