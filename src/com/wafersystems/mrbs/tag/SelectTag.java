package com.wafersystems.mrbs.tag;

import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.apache.log4j.Logger;
import org.apache.commons.beanutils.BeanUtils;

import com.wafersystems.util.StrUtil;

/**
 * 下拉列表Tag
 * 
 * @author AnDong
 */
public class SelectTag extends BaseTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7890837694387405395L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(SelectTag.class);
	/**
	 * 下拉列表名称
	 */
	private String name;
	/**
	 * 数据源的缓冲健值
	 */
	private String data;
	/**
	 * 数据的编号属性名称
	 */
	private String idName;
	/**
	 * 数据的值属性名称
	 */
	private String valueName;
	/**
	 * 列表选项的value属性值样式：id－只保留id属性，mixed-id,value样式
	 */
	private String valueStyle = "id";	
	/**
	 * 是否产生头部的空白选项
	 */
	private String hasBlankOption = "false";
	/**
	 * 其它html属性
	 */
	private String htmls = "";
	
	/**
	 * 数据源
	 */
	private List<Object> datas = null;
	
	/**
	 * 标签结束事件
	 */
	@SuppressWarnings("unchecked")
	public int doEndTag() throws JspException
	{	
		try
		{
			datas = (List<Object>) request.getAttribute(data);
			if (null == datas)
				datas = (List<Object>) session.getAttribute(data);
			
			page.print(this.getSelect());
		}
		catch (Exception e)
		{
			logger.error("下拉列表tag错误：", e);
		}

		return EVAL_PAGE;
	}
	
	/**
	 * 生成下拉列表的html代码
	 */
	private String getSelect()
	{
		StringBuffer htmlCode = new StringBuffer();
		htmlCode.append("<Select name='")
				.append(name)
				.append("' ")
				.append(htmls)
				.append(">\n");
		
		if (null != datas)
		{
			try
			{
				//从当前请求参数中获取默认值
				String defaultValue = request.getParameter(name);
				if (null == defaultValue)
					defaultValue = (String) request.getAttribute(name);
				logger.debug("默认值:" + defaultValue);
				
				if ("true".equals(hasBlankOption))
					htmlCode.append("<option></option>\n");
				
				Object obj = null;
				for (Iterator<Object> itr = datas.iterator(); itr.hasNext(); )
				{
					obj = (Object) itr.next();
					
					htmlCode.append("  <option value='")
							.append(BeanUtils.getProperty(obj, idName));
				
					if ("mixed".equals(valueStyle))
						htmlCode.append(",")
								.append(BeanUtils.getProperty(obj, valueName));
					
					htmlCode.append("'");
					
					
					if (!StrUtil.isEmptyStr(defaultValue))
					{
						if ("mixed".equals(valueStyle) && defaultValue.equals(BeanUtils.getProperty(obj, idName) + 
								"," + BeanUtils.getProperty(obj, valueName)))
							htmlCode.append(" selected");
						else
							if (defaultValue.equals(BeanUtils.getProperty(obj, idName)))
								htmlCode.append(" selected");
					}
					
					htmlCode.append(">")
							.append(BeanUtils.getProperty(obj, valueName))
							.append("</option>\n");
				}
			}
			catch (Exception e)
			{
				htmlCode.append("<option>非法的数据源</option>\n");
				logger.error("下拉列表Tag错误:", e);
			}
		}
		
		htmlCode.append("</Select>\n");
				
		return htmlCode.toString();
	}

	/**
	 * @param data The data to set.
	 */
	public void setData(String data)
	{
		this.data = data;
	}

	/**
	 * @param hasBlankOption The hasBlankOption to set.
	 */
	public void setHasBlankOption(String hasBlankOption)
	{
		this.hasBlankOption = hasBlankOption;
	}

	/**
	 * @param idName The idName to set.
	 */
	public void setIdName(String idName)
	{
		this.idName = idName;
	}

	/**
	 * @param name The name to set.
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param valueName The valueName to set.
	 */
	public void setValueName(String valueName)
	{
		this.valueName = valueName;
	}

	/**
	 * @param valueStyle The valueStyle to set.
	 */
	public void setValueStyle(String valueStyle)
	{
		this.valueStyle = valueStyle;
	}

	/**
	 * @param htmls The htmls to set.
	 */
	public void setHtmls(String htmls)
	{
		this.htmls = htmls;
	}
}
