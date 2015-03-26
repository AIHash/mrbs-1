package com.wafersystems.mrbs.tag.tree;

import java.io.Serializable;

/**
 * ���ͽڵ�ֵ����
 */
public class TreeNode implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2432867830856164430L;
	/**
	 * ���
	 */
	private String id;
	/**
	 * �ڵ�����
	 */
	private String nodeName;
	/**
	 * �ڵ���
	 */
	private String nodeNo;
	/**
	 * ���ڵ���
	 */
	private String parentNodeNo;
	/**
	 * ��ע
	 */
	private String remark;
	/**
	 * �Ƿ��ȡ���ӽڵ㣨Ӧ����ֱ�ӵ�2���ӽڵ���ر�־��
	 */
	private boolean isLoadedSonList = false;
//	/**
//	 * �ڵ㼶������ڸ��ڵ㣩
//	 */
//	private int level;
	/**
	 * �ڵ�״̬��1��չ����2���ϲ�;
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
