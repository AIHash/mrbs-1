package com.wafersystems.mrbs.tag.tree;

import javax.servlet.jsp.JspException;

import com.wafersystems.mrbs.tag.BaseTag;


/**
 * 输入框（复选、单选框）Tag
 */
public class InputBoxTag extends BaseTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1132432418043066422L;
	/**
	 * 输入框类型：
	 * 			checkBox－复选框
	 * 			radioBox－单选框
	 */
	private String type;
	/**
	 * 输入框名称
	 */
	private String name;
	/**
	 * 默认值
	 */
	private String defaultValue = "";
	/**
	 * 值样式：
	 * 		idOnly－只放置系统编号
	 * 		combination－使用组合值（id、nodeName、nodeNo、remark属性顺序，“|”为间隔符号的字符串）
	 */
	private String valueStyle = "idOnly";
	/**
	 * 单选、复选框的显示方式：
	 * 						all－所有节点都显示
	 * 						leafNodeOnly－仅仅叶子节点
	 */
	private String showStyle = "all";
	/**
	 * 是否根节点也显示
	 */
	private String showInRootNode = "false";
	
	/**
	 * Tag标记结束事件
	 */
	public int doEndTag() throws JspException
	{
		TreeTag tree = (TreeTag) findAncestorWithClass(this, TreeTag.class);
		tree.setInputBox(this);
		
		return EVAL_PAGE;
	}
	
	/**
	 * @return
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return
	 */
	public String getShowInRootNode()
	{
		return showInRootNode;
	}

	/**
	 * @return
	 */
	public String getShowStyle()
	{
		return showStyle;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @return
	 */
	public String getValueStyle()
	{
		return valueStyle;
	}

	/**
	 * @param string
	 */
	public void setName(String string)
	{
		name = string;
	}

	/**
	 * @param string
	 */
	public void setShowInRootNode(String string)
	{
		showInRootNode = string;
	}

	/**
	 * @param string
	 */
	public void setShowStyle(String string)
	{
		showStyle = string;
	}

	/**
	 * @param string
	 */
	public void setType(String string)
	{
		type = string;
	}

	/**
	 * @param string
	 */
	public void setValueStyle(String string)
	{
		valueStyle = string;
	}

	/**
	 * @return Returns the defaultValue.
	 */
	public String getDefaultValue()
	{
		return defaultValue;
	}

	/**
	 * @param defaultValue The defaultValue to set.
	 */
	public void setDefaultValue(String defaultValue)
	{
		this.defaultValue = defaultValue;
	}

}
