package com.wafersystems.mrbs.tag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 没有Body内容Tag基类
 * 
 * @author AnDong
 */
public abstract class BaseTag extends TagSupport
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3809929915058778792L;

	protected HttpServletRequest request = null;

	protected HttpServletResponse response = null;

	protected HttpSession session = null;

	protected PageContext pageContext = null;

	protected JspWriter page = null;

	/**
	 * 设置当前页面的
	 */
	public void setPageContext(PageContext pageContext)
	{
		super.setPageContext(pageContext);
		
		if(pageContext != null)
		{
			this.pageContext = pageContext;
			request  = (HttpServletRequest) pageContext.getRequest();
			response = (HttpServletResponse)pageContext.getResponse();
			page 	 = pageContext.getOut();
			session  = pageContext.getSession();
			
		}	
	}
}
