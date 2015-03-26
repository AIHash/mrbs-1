package com.wafersystems.mrbs.tag;

import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.ResourcesCenter;

/**
 * 系统资源Tag
 * 
 * @author AnDong
 */
public class MessageTag extends BaseTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2627722721515818341L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(MessageTag.class);
	/**
	 * 资源内容参数分割符
	 */
	public static final String PARAMETERS_SPLIT_STR = "#@#";
	/**
	 * 键值
	 */
	private String key = null;
	/**
	 * 参数
	 */
	private String args = null;

	
	/**
	 * 获取资源信息
	 * 
	 * @param keyName		键值名称
	 * 
	 * @return 资源信息
	 */
	public static String getMessage(String keyName)
	{
//		键值采用小写.
		return ResourcesCenter.getMessage("", keyName);
	}
	
	/**
	 * 获取资源信息
	 * 
	 * @param keyName		键值名称
	 * @param parameters	参数列表（使用","符号分割）
	 * 
	 * @return 资源信息
	 */
	public static String getMessage(String keyname, String Parameters)
	{
		return ResourcesCenter.getMessage("", keyname, Parameters);
	}
	
	/**
	 * 通过session获取资源信息
	 * 
	 * @param keyName		键值名称
	 * 
	 * @return 资源信息
	 */
	public static String getMessage(HttpSession session, String keyName)
	{
		return getMessage(session, keyName, null);
	}
	
	/**
	 * 通过session获取资源信息
	 * 
	 * @param keyName		键值名称
	 * @param parameters	参数列表（使用","符号分割）
	 * 
	 * @return 资源信息
	 */
	public static String getMessage(HttpSession session, String keyname, String Parameters)
	{
//		键值采用小写.
		String language = GlobalParam.language;
		UserInfo user = (UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		if(user != null)
			language = user.getUserLanguage();
		return ResourcesCenter.getMessage(language,  keyname,  Parameters);
	}
	
	
	/**
	 * 标签结束事件
	 */
	public int doEndTag() throws JspException
	{
		try
		{
//			if (args!=null)
//				args.replaceAll(",", MessageTag.PARAMETERS_SPLIT_STR);
			page.write(getMessage(session, key, args));
		}
		catch (Exception e)
		{
			logger.error("系统资源提取错误", e);
		}
		
		return EVAL_PAGE;
	}
	
	public void setArgs(String args)
	{
		this.args = args;
	}

	public void setKey(String key)
	{
		this.key = key;
	}
	
	
}