package com.wafersystems.mrbs.tag.tree;

import javax.servlet.jsp.JspException;

import com.wafersystems.mrbs.tag.BaseTag;



public class RootTag  extends BaseTag{

	private static final long serialVersionUID = 5234502190512589976L;

	/**
	 * 根节点系统编号
	 */
	private String id = "1";
	/**
	 * 根节点名称
	 */
	private String name = "Root";
	/**
	 * 节点编号
	 */	
	private String nodeNo = "001";
	/**
	 * 是否显示根节点链接
	 */
	private String showHref = "true";
	/**
	 * 备注
	 */
	private String remark;

	/**
	 * Tag标记结束事件
	 */
	public int doEndTag() throws JspException
	{
		TreeTag tree = (TreeTag) findAncestorWithClass(this, TreeTag.class);
		tree.setRoot(this);
		return EVAL_PAGE;
	}
	
	/**
	 * @return
	 */
	public String getId()
	{
		return id;
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
	public String getNodeNo()
	{
		return nodeNo;
	}

	/**
	 * @return
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @param string
	 */
	public void setId(String string)
	{
		id = string;
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
	public void setNodeNo(String string)
	{
		nodeNo = string;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string)
	{
		remark = string;
	}

	/**
	 * @return
	 */
	public String getShowHref()
	{
		return showHref;
	}

	/**
	 * @param string
	 */
	public void setShowHref(String string)
	{
		showHref = string;
	}
	
}
