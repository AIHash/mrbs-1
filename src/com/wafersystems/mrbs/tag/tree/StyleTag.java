package com.wafersystems.mrbs.tag.tree;

import javax.servlet.jsp.JspException;

import com.wafersystems.mrbs.tag.BaseTag;


/**
 * 样式Tag
 * 
 * @author AnDong
 */
public class StyleTag extends BaseTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2234123881197274232L;
	/**
	 * 图片路径
	 */
	private String imagesDir = "/";
	/**
	 * 是否显示节点虚线
	 */
	private String showLine = "false";
	/**
	 * 节点名称尾部是否显示字节点个数
	 */
	private String showSonNodeCount = "false";
	
	/**
	 * Tag标记结束事件
	 */
	public int doEndTag() throws JspException
	{
		TreeTag tree = (TreeTag) findAncestorWithClass(this, TreeTag.class);
		tree.setStyle(this);
		
		return EVAL_PAGE;
	}
	
	/**
	 * @return
	 */
	public String getImagesDir()
	{
		return imagesDir;
	}

	/**
	 * @return
	 */
	public String getShowLine()
	{
		return showLine;
	}

	/**
	 * @return
	 */
	public String getShowSonNodeCount()
	{
		return showSonNodeCount;
	}

	/**
	 * @param string
	 */
	public void setImagesDir(String string)
	{
		imagesDir =  string;
	}

	/**
	 * @param string
	 */
	public void setShowLine(String string)
	{
		showLine = string;
	}

	/**
	 * @param string
	 */
	public void setShowSonNodeCount(String string)
	{
		showSonNodeCount = string;
	}

}
