package com.wafersystems.mrbs.tag;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 页面Header标签
 *
 * @author AnDong
 */
public class PageHeaderTag extends BaseTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 206353941288100328L;
	/**
	 * Logger for this class
	 */
	private final static Logger logger = LoggerFactory.getLogger(PageHeaderTag.class);
	/**
	 * 是否包含Ecside列表信息,默认包含列表信息
	 */
	private String hasEcList = "true";
	
	public int doEndTag() throws JspException
	{		
		try
		{
			page.println("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
			page.println("  <script language=\"JavaScript\" src=\"" + request.getContextPath() + "/resources/js/w3c_to_ie.js\"></script>");
			page.println("  <script language=\"JavaScript\" src=\"" + request.getContextPath() + "/resources/js/validate.js\"></script>");
			page.println("  <script language=\"JavaScript\" src=\"" + request.getContextPath() + "/resources/js/comm.jsp\"></script>");
			
			page.println("	<link href=\"" + request.getContextPath() + "/resources/skin/style/common.css\" rel=\"StyleSheet\" type=\"text/css\">");
			
			if(hasEcList.equals("true"))
			{	
				page.println("	<script language=\"JavaScript\" src=\"" + request.getContextPath() + "/resources/js/prototypeajax.js\"></script>");
				page.println("	<script language=\"JavaScript\" src=\"" + request.getContextPath() + "/resources/js/ecside.jsp\"></script>");
			//	page.println("	<link href=\"" + request.getContextPath() + "/resources/style/td_style_ec.css\" rel=\"StyleSheet\" type=\"text/css\">");
				page.println("  <script language=\"javaScript\" type=\"text/javascript\">");
				page.println("   function init(){");
				page.println("      var ecside1=new ECSide(\"ec\");");
				//不使用预读取技术
				page.println("      ecside1.doPrep=false;");
				//不预读取“前一页”
				page.println("      ecside1.doPrepPrev=false;");
				page.println("      ecside1.init();");
				page.println("   }");
				page.println(" </script>");
			}
		}
		catch (Exception e)
		{
			logger.error("自定义Tag错误:", e);
		}
		
		return EVAL_PAGE;
	}

	/**
	 * @param hasEcList the hasEcList to set
	 */
	public void setHasEcList(String hasEcList)
	{
		this.hasEcList = hasEcList;
	}
	
}
