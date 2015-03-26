package com.wafersystems.mrbs.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.exception.LoginFailedException;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Controller
@RequestMapping(value="/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

	private UserService userService;

	@RequestMapping(value="/", method=RequestMethod.POST)
	public String login(@RequestParam String LoginId, @RequestParam String Password, HttpSession session) {
		try{
			UserInfo user = userService.checkUserLogin(LoginId.toLowerCase(), Password);

			user.setUserLanguage(GlobalParam.language);
			session.setAttribute(GlobalConstent.USER_LOGIN_SESSION, user);

			switch(user.getUserType().getValue()){
				//超级管理员
				case GlobalConstent.USER_TYPE_SUPER_ADMIN : return "redirect:/index/";
				case GlobalConstent.USER_TYPE_MEETING_ADMIN : return "redirect:/index/";
				case GlobalConstent.USER_TYPE_DEPT_ADMIN : return "redirect:/index.jsp";
				case GlobalConstent.USER_TYPE_PROFESSIONAL : return "redirect:/index/";
				case GlobalConstent.USER_TYPE_UNION : return "redirect:/index/";
				default : return "redirect:/index.jsp";
			}

		}catch(LoginFailedException e){
			logger.debug("用户[" + LoginId + "]登录失败");
			session.setAttribute("message", MessageTag.getMessage("system.logonFailure"));
		}
		return "redirect:/index.jsp";
	}

	@RequestMapping(value = "/exit", method = RequestMethod.GET)
	public String logout(HttpSession session) {
		session.invalidate();//销毁当前session
		return "redirect:/";
	}

	/**
	 *  此方法为用户从邮箱点击URL进入时拦截器匹配的URL，无实际作用
	 */
	@RequestMapping(value="notice",method =RequestMethod.GET)
	public String noticeLogin(){
		return "redirect:/";
	}

	@Resource(type = UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}