package com.wafersystems.mrbs.tag.tree;

import java.io.Serializable;

/**
 * 树型节点值对象
 */
public class TreeNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2432867830856164430L;
	/**
	 * 编号
	 */
	private String id;
	/**
	 * 节点名称
	 */
	private String nodeName;
	/**
	 * 节点编号
	 */
	private String nodeNo;
	/**
	 * 父节点编号
	 */
	private String parentNodeNo;
	/**
	 * 备注
	 */
	private String remark;
	/**
	 * 是否获取过子节点（应该是直接的2级子节点加载标志）
	 */
	private boolean isLoadedSonList = false;
//	/**
//	 * 节点级别（相对于根节点）
//	 */
//	private int level;
	/**
	 * 节点状态：1－展开；2－合并;
	 */
	private int state = 2;
	
	/**
	 * @return
	 */
	public boolean isLoadedSonList()
	{
		return isLoadedSonList;
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
	public String getNodeName()
	{
		return nodeName;
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
	public String getParentNodeNo()
	{
		return parentNodeNo;
	}

	/**
	 * @return
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @return
	 */
	public int getState()
	{
		return state;
	}

	/**
	 * @param b
	 */
	public void setIsLoadedSonList(boolean b)
	{
		isLoadedSonList = b;
	}

	/**
	 * @param l
	 */
	public void setId(String l)
	{
		id = l;
	}

	/**
	 * @param string
	 */
	public void setNodeName(String string)
	{
		nodeName = string;
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
	public void setParentNodeNo(String string)
	{
		parentNodeNo = string;
	}

	/**
	 * @param string
	 */
	public void setRemark(String string)
	{
		remark = string;
	}

	/**
	 * @param i
	 */
	public void setState(int i)
	{
		state = i;
	}
}
