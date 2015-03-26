package com.wafersystems.mrbs.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.SystemContext;

/**
* Session过期检查Filter
*
* @author   Moon
*/
public class SessionTimeOutFilter implements Filter
{
	/**
	 * Logger for this class
	 */
	private final static Logger logger = LoggerFactory.getLogger(SessionTimeOutFilter.class);

	/**
	 * 过滤器配置对象
	 */
	protected FilterConfig filterConfig = null;

	/**
	 * 注销filter服务
	 */
	public void destroy(){}

	/**
	 * 检查当前请求的session是否过期
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
		throws IOException, ServletException 
	{
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse)resp;
		//license 未注册或其他方式跳过License检测
		if(!GlobalParam.LICENSE_STATE){
			logger.warn("当前系统未注册，重定向至未注册提醒页面！");
			request.getRequestDispatcher("/license/").forward(request, response);
			return;
		}
		String url = request.getRequestURI();
		logger.debug("请求的URL为:" + url);	
		String root = request.getContextPath();//root = /uc
		logger.debug("项目发布名称:" + root);

		//去掉发布目录名称/uc
		url = url.substring(root.length());
		if(!"/index.jsp".equals(url) && !"/".equals(url) && ! url.startsWith("/login/") && !url.startsWith("/resources/")){
			HttpSession session = request.getSession();
			if (null == session	|| null == session.getAttribute(GlobalConstent.USER_LOGIN_SESSION)) {
				logger.warn("当前连接已超时，重定向至登陆页面！");
				session.setAttribute("message", "system.session_expire");
				response.sendRedirect(request.getContextPath() + "/");
			} else {
				logger.debug("Session没有过期，继续执行。");
				// 系统应用名称
				request.getSession().setAttribute("root", root);
				SystemContext.set_session(session);
				chain.doFilter(request, response);
			}
		}else{
			logger.debug("登陆首页，继续执行。");
			chain.doFilter(request, response);
		}
	}

	/**
	 * 初始化Filter参数
	 * @param filterConfig The filter configuration object
	 */
	public void init(FilterConfig filterConfig) throws ServletException
	{
		this.filterConfig = filterConfig;
	}

}