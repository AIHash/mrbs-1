package com.wafersystems.mrbs.tag.tree;

import java.util.List;

/**
 * 动态树数据获取接口
 * 
 * @author AnDong
 */
public interface DynamicTree
{
	/**
	 * 获取指定树型节点编号的直接字节点列表
	 * 
	 * @param nodeNo	树型节点编号
	 * 
	 * @return List(TreeNode)
	 */
	public List<TreeNode> getSonListOfTreeNode(String nodeNo);
}