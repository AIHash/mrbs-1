package com.wafersystems.mrbs.tag.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.jsp.JspException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.tag.BaseBodyTag;
import com.wafersystems.util.StrUtil;


/**
 * 树型Tree标签类。
 * 
 * 使用DataSource标签来解决数据源的问题（根据type判断，如果是dymanic形式的树，需要实
 * 现DynamicTree接口获取指定节点的下级节点列表）。
 */
public final class TreeTag extends BaseBodyTag
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2425274566399987782L;
	/**
	 * Logger for this class
	 */
	private static final Logger logger = LoggerFactory.getLogger(TreeTag.class);
	/**
	 * 树名称健值:静态树－用来接收缓冲中树型节点数据的key值（request--->session）
	 * 			动态树－继承自DynamicTree接口的子类全路径
	 */
	private String treeName;
	/**
	 * 链接的target属性。
	 */
	private String target;
	/**
	 * URL链接。
	 */
	private String href;
	/**
	 * 是否显示根节点链接
	 */
	private String showHref = "true";
	/**
	 * 节点编号分级宽度。
	 */
	private String nodeNoWidth = "3";
	/**
	 * 树类型：
	 * 		static－静态树（直接在前台打印html页面代码）
	 * 		dynamic－动态树（调用DynamicTree接口逐级获取数据）
	 */
	private String type = "static";
	/**
	 * 刷新模式：
	 * 			never－从不刷新
	 * 			dynamic－动态刷新（每展开、合并一次节点就刷新一次相关范围内的数据）
	 */
	private String refreshMode = "never";
	/**
	 * 默认展开级别
	 */
	private String expandLevel = "1";
	
	//非标签属性
	/**
	 * 根节点信息
	 */
	private RootTag root;
	/**
	 * 显示样式信息
	 */
	private StyleTag style;
	/**
	 * 输入框信息 ,内容已经用";"分割,故此处会默认在末尾和开头各添加一个分号.对应的TreeTag中解析的时候也需要对";"进行特殊的判断.
	 *  2008-03-07 NGY 
	 */
	private InputBoxTag inputBox;
	/**
	 * 树型数据列表
	 */
	private List<TreeNode> treeData;
	/**
	 * 图片绝对路径
	 */
	private String realImageDir;
	/**
	 * 动态树数据获取类接口
	 */
	private DynamicTree dTree;
	/**
	 * 多个字符串值的分割字符串
	 */
	private String splitStr = ",";
	/**
	 * 标签结束事件
	 */
	public int doEndTag() throws JspException
	{
		try
		{
			Integer.parseInt(expandLevel);
		}
		catch(Exception e)
		{
			logger.warn("expandLevel属性设置错误，系统将使用默认值生成树");
			expandLevel = "1";
		}
		
		realImageDir = request.getContextPath();
		realImageDir+= null == style ? "" : style.getImagesDir() + "/";
		
		try
		{
			if (null == root)
				page.print("缺少<treeRoot />标签");
				
			if ("static".equalsIgnoreCase(type))
				page.print(this.getStaticTree());									//静态树
			else
				page.print(this.getDynamicTree());									//动态树
		}
		catch (Exception e)
		{
			logger.error("树Tag错误：", e);
		}
		
		return EVAL_PAGE;
	}

	//============================================================================
	//									静态树代码生成区
	//============================================================================
	/**
	 * 产生静态树的页面html代码
	 * 
	 * @return
	 */
	private String getStaticTree()
	{
		StringBuffer htmlCode = new StringBuffer();
		
		treeData = this.getStaticTreeData();
		htmlCode.append(this.getRootNode());
		htmlCode.append(this.getAllTreeNode());
		htmlCode.append(this.getJavaScript());
		
		return htmlCode.toString();
	}

	/**
	 * 产生层的显示、隐藏javascript脚本
	 */
	private String getJavaScript()
	{
		StringBuffer htmlCode = new StringBuffer();
		
		htmlCode.append("<Script Language='JavaScript'>\n");
		
		if ("static".equals(type))
		{
			htmlCode.append("function openOrCloseStaticTreeNode(divId, ");
			//要绘制节点线
			if (null != style && "true".equalsIgnoreCase(style.getShowLine()))
				htmlCode.append("lineId, ");

			htmlCode.append("imgName)\n")
					.append("{\n divId = document.getElementById(divId);")
					.append("\n lineId = document.getElementById(lineId); ")
					.append("\n imgName = document.getElementById(imgName);")
					.append("\n	if (divId.style.display == 'none')\n")
					.append("	{\n")
					.append("		divId.style.display = 'block'\n")
					.append("       imgName.src = '")
					.append(realImageDir)
					.append(TreeConst.NODE_OPEN_IMG)
					.append("'\n");
				
			//要绘制节点线
			if (null != style && "true".equalsIgnoreCase(style.getShowLine()))
				htmlCode.append("		if (-1 != lineId.src.indexOf('nodeLine.gif'))\n")
						.append("			lineId.src = '")
						.append(realImageDir)
						.append(TreeConst.NODE_OPEN_LINE)
						.append("'\n")
						.append("		else if (-1 != lineId.src.indexOf('lastNodeLine.gif'))\n")
						.append("				lineId.src = '")
						.append(realImageDir)
						.append(TreeConst.LAST_NODE_OPEN_LINE)
						.append("'\n");
					
			htmlCode.append("		}\n")
					.append("		else\n")
					.append("		{\n")
					.append("			divId.style.display = 'none'\n")
					.append("           imgName.src = '")
					.append(realImageDir)
					.append(TreeConst.NODE_IMG)
					.append("'\n");
						
			//要绘制节点线
			if (null != style && "true".equalsIgnoreCase(style.getShowLine()))
				htmlCode.append("		if (-1 != lineId.src.indexOf('nodeOpenLine.gif'))\n")
						.append("			lineId.src = '")
						.append(realImageDir)
						.append(TreeConst.NODE_LINE)
						.append("'\n")
						.append("		else if (-1 != lineId.src.indexOf('lastNodeOpenLine.gif'))\n")
						.append("				lineId.src = '")
						.append(realImageDir)
						.append(TreeConst.LAST_NODE_LINE)
						.append("'\n");
		
			htmlCode.append("	}\n")
					.append("}\n\n");
		}
		else
		{
			//request.getRequestURI()中不带请求参数，所有的请求参数字符串在request.getQueryString()中
			htmlCode.append("function openNode(nodeId)\n")
					.append("{\n")
					.append("	window.location='")
					.append(request.getRequestURI())
					.append("?");
			
			// URL请求参数处理（过滤当前使用的"action=open"和"nodeId="参数）
			if (!"".equals(StrUtil.formatStr(request.getQueryString())))
			{
				String[] params = request.getQueryString().split("&");
				for (int i = 0; i < params.length; i++)
				{
					if (!params[i].equals("action=open") && !params[i].equals("action=close") && !params[i].startsWith("nodeId="))
						htmlCode.append(params[i])
								.append("&");
				}
			}
			
			htmlCode.append("action=open&nodeId=' + nodeId;\n")
					.append("}\n\n");

			htmlCode.append("function closeNode(nodeId)\n")
					.append("{\n")
					.append("	window.location='")
					.append(request.getRequestURI())
					.append("?");
					
			// URL请求参数处理（过滤当前使用的"action=open"和"nodeId="参数）
			if (!"".equals(StrUtil.formatStr(request.getQueryString())))
			{
				String[] params = request.getQueryString().split("&");
				for (int i = 0; i < params.length; i++)
				{
					if (!params[i].equals("action=open") && !params[i].equals("action=close") && !params[i].startsWith("nodeId="))
						htmlCode.append(params[i])
								.append("&");
				}
			}
			
			htmlCode.append("action=close&nodeId=' + nodeId;\n")
					.append("}\n");
		}

		htmlCode.append("</Script>\n\n");

		return htmlCode.toString();
	}

	/**
	 * 产生根节点的html代码
	 * 
	 * @return
	 */
	private String getRootNode()
	{
		StringBuffer htmlCode = new StringBuffer();
		
		htmlCode.append("<div noWrap ><ul style='MARGIN-LEFT:5;MARGIN-TOP:0'><img align='absmiddle' src='")
				.append(realImageDir)
				.append(TreeConst.ROOT_NODE_IMG)
				.append("'>&nbsp;")
				.append(this.getRootInputBox());

		if (null != root && "true".equalsIgnoreCase(root.getShowHref()) && null != href)
		{
			htmlCode.append("<a href='")
//			.append(request.getAttribute("root"))
			.append(href);
			
			if (href.indexOf('?') == -1)
				htmlCode.append("?");
			else
				htmlCode.append("&");
			
			// 链接参数
			htmlCode.append("id=")
					.append(root.getId())
					.append("&nodeNo=")
					.append(root.getNodeNo())
					.append("&parentNodeNo=&remark=")
					.append(root.getRemark())
					.append("&nodeName=")
					.append(root.getName())
					.append("'");
			
			// target 属性
			if (null != target)
				htmlCode.append(" target='")
						.append(target)
						.append("'");
			
			htmlCode.append(">")
					.append(null == root ? "根节点" : root.getName())
					.append("</a>\n");
		}
		else
			htmlCode.append(null == root ? "根节点" : root.getName());
		
		htmlCode.append("\n")
				.append("	<div id=d style='display:block'>\n")
				.append("		<ul style='MARGIN-LEFT:");
				
		if (null == style || !"true".equalsIgnoreCase(style.getShowLine()))
			htmlCode.append("20");
		else
			htmlCode.append("0");

		htmlCode.append("'>\n");
		
		return htmlCode.toString();
	}

	/**
	 * 产生根节点的输入框（单选、复选按钮）
	 * 
	 * @return
	 */
	private String getRootInputBox()
	{
		if (null == inputBox || "false".equalsIgnoreCase(inputBox.getShowInRootNode()))
			return "";
			
		StringBuffer htmlCode = new StringBuffer();
		htmlCode.append("<input type='")
				.append(inputBox.getType())
				.append("' name='")
				.append(inputBox.getName())
				.append("' value='");
		
		if ("idOnly".equals(inputBox.getValueStyle()))
			htmlCode.append(null == root ? "-1" : root.getId());
		else
			htmlCode.append(null == root ? "||||" : root.getId() + splitStr + root.getName() + splitStr + root.getNodeNo() +
					splitStr + "-1" + splitStr + root.getRemark());
		htmlCode.append("'>");
		
		return htmlCode.toString();
	}
	
	/**
	 * 按照展开级别产生树的所有节点的html代码
	 */
	private String getAllTreeNode()
	{
		StringBuffer htmlCode = new StringBuffer();
		
		int maxLevel = Integer.parseInt(expandLevel);
		int currentLever = 0, layers = 0;
		
		TreeNode node = null, nextNode = null, parentNode = null;
		String parentNodeNo = "-";
		for (int i = 0; i < treeData.size(); i++)
		{
			try
			{
				node = (TreeNode) treeData.get(i);
			}
			catch (Exception e)
			{
				logger.error("数据源不是期望的TreeNode对象");
				break;
			}

			currentLever = (node.getNodeNo().length() - root.getNodeNo().length())/ Integer.parseInt(nodeNoWidth);
			//下一个节点对象
			nextNode = (i + 1) < treeData.size() ? (TreeNode) treeData.get(i + 1) : null;
			//上级节点对象
			parentNode = this.findNode(node.getNodeNo().substring(0, node.getNodeNo().length() - Integer.parseInt(nodeNoWidth)));

			if (!"static".equals(type) && null != parentNode && isParentNodeClosed(node.getNodeNo()))
			{
				//logger.debug("root node:" + parentNodeNo);
				//node.setState(2);
				if (-1 == parentNode.getNodeNo().indexOf(parentNodeNo))		//同级节点仅添加一个
				{
					htmlCode.append("	</ul>\n</div>\n");
					parentNodeNo = parentNode.getNodeNo();
				}

				continue;
			}
			
			//logger.info("processed node:" + node.getNodeNo() + "," + node.getNodeName());
			
			//最后一个节点
			if (null == nextNode)
			{
				//logger.info("last node!");
				htmlCode.append(this.getTreeNode(currentLever, maxLevel, layers, node, nextNode));
			}
			else
				if (node.getNodeNo().length() == nextNode.getNodeNo().length())	//同级别节点
				{
					//logger.info("same lever node!");
					htmlCode.append(this.getTreeNode(currentLever, maxLevel, layers, node, nextNode));
				}
				else
					if (node.getNodeNo().length() < nextNode.getNodeNo().length())	//当前节点下面还有直接节点
					{
						//logger.info("has child node!");
						htmlCode.append(this.getTreeNode(currentLever, maxLevel, layers, node, nextNode));
						
						htmlCode.append("	<div id=d")
								.append(layers)
								.append(" style='display:");
								
						if (currentLever < maxLevel || node.getState() == 1)
							htmlCode.append("block");
						else
							htmlCode.append("none");
						
						htmlCode.append("'>\n")
								.append("		<ul style='MARGIN-LEFT:");
				
						if (null == style || !"true".equalsIgnoreCase(style.getShowLine()))
							htmlCode.append("20");
						else
							htmlCode.append("0");
		
						htmlCode.append("'>\n");

						layers++;
					}
					else	//下个节点不属于当前分支
						if (node.getNodeNo().length() > nextNode.getNodeNo().length())
						{
							//logger.info("other tree node!");
							htmlCode.append(this.getTreeNode(currentLever, maxLevel, layers, node, nextNode));
							
							if ("static".equals(type))
								for (int j = 0; j < (node.getNodeNo().length() - nextNode.getNodeNo().length()) / Integer.parseInt(nodeNoWidth); j++)
									htmlCode.append("	</ul>\n</div>\n");
						}
		}
		
		htmlCode.append("	</ul>\n</div>\n");
		
		return htmlCode.toString();
	}
	
	/**
	 * @param string
	 * @return
	 */
	private boolean isParentNodeClosed(String nodeNo)
	{
		boolean flag = false;
		
		TreeNode node = null;
		for (int i = root.getNodeNo().length() + Integer.parseInt(nodeNoWidth); 
			 i < nodeNo.length(); i+= Integer.parseInt(nodeNoWidth))
		{
			node = this.findNode(nodeNo.substring(0, i));
			if (null != node && node.getState() == 2)
			{
				flag = true;
				break;
			}
		}
		//logger.debug("isParentNodeClosed:" + nodeNo + "," + flag);
		return flag;
	}

	/**
	 * 产生指定节点的页面html代码
	 * 
	 * @param node	当前节点
	 * @param i
	 * @return
	 */
	private String getTreeNode(int currLevel, int maxLever, int layers, TreeNode node, TreeNode nextNode)
	{
		StringBuffer htmlCode = new StringBuffer("<li>");

		//线型样式代码
		htmlCode.append(this.getLineImage(currLevel, maxLever, layers, node, nextNode));
		//节点图片代码
		htmlCode.append(this.getNodeImage(currLevel, maxLever, layers, node, nextNode));
				
		htmlCode.append("<span id=\"Child_node" + node.getId() + "\" onmousemove=\"mymousemove(this);\" onmouseout=\"mymouseout(this);\">")
				.append(this.getNodeInputBox(node, nextNode))
				.append(this.getNodeHref(node))
				.append("</li>\n");

		return htmlCode.toString();
	}

	/**
	 * @return
	 */
	private String getNodeImage(int currLevel, int maxLever, int layers, TreeNode node, TreeNode nextNode)
	{
		StringBuffer htmlCode = new StringBuffer();
		
		htmlCode.append("<img align='absmiddle' src='")
				.append(realImageDir);
		
		if (null == nextNode)
		{
			//logger.info("Lasted leaf!");
			//最后一个节点
			htmlCode.append(TreeConst.LEAF_NODE_IMG);
		}
		else
		{
			if (node.getNodeNo().equals(nextNode.getParentNodeNo()))
			{
				if ("static".equals(type))
				{
					if (currLevel < maxLever)
						htmlCode.append(TreeConst.NODE_OPEN_IMG);
					else
						htmlCode.append(TreeConst.NODE_IMG);
				}
				else
				{
					if (node.getState() == 1)
						htmlCode.append(TreeConst.NODE_OPEN_IMG);
					else
						htmlCode.append(TreeConst.NODE_IMG);
				}
				
				//节点图片链接信息
				htmlCode.append(this.getNodeAction("img", node, layers));
			}
			else
				htmlCode.append(TreeConst.LEAF_NODE_IMG);
		}
		
		htmlCode.append("'>");
		
		return htmlCode.toString();
	}

	/**
	 * @param layers
	 * @return
	 */
	private String getNodeAction(String imgName, TreeNode node, int layers)
	{
		StringBuffer htmlCode = new StringBuffer();
		
		if ("static".equals(type))
		{
			htmlCode.append("' id='")
					.append(imgName)
					.append(layers)
					.append("' onClick='javaScript:openOrCloseStaticTreeNode(\"").append("d")
					.append(layers).append("\"");
					
			if (null != style && "true".equalsIgnoreCase(style.getShowLine()))
				htmlCode.append(",").append("\"").append("line").append(layers).append("\"");

			htmlCode.append(",").append("\"").append("img").append(layers).append("\"")
					.append(")'  style='CURSOR: pointer;");
		}
		else
		{
			htmlCode.append("' onClick='javaScript:");
			
			if (node.getState() == 1)
				htmlCode.append("close");
			else
				htmlCode.append("open");

			htmlCode.append("Node(")
					.append(node.getId())
					.append(")' style='CURSOR: pointer;");
		}
		
		return htmlCode.toString();
	}

	/**
	 * 产生线条样式的树型代码
	 */
	private String getLineImage(int currLevel, int maxLever, int layers, TreeNode node, TreeNode nextNode)
	{
		StringBuffer htmlCode = new StringBuffer();
		
		if (null != style && "true".equalsIgnoreCase(style.getShowLine()))
		{
			//循环添加节点之间的竖线
			if (currLevel > 1)
			{
				for (int j = 0; j < currLevel - 1 ; j++)
				{
					htmlCode.append("<img src='")
							.append(realImageDir);
							
					if (this.hasNextBrother(node.getNodeNo().substring(0, (j + 1) * Integer.parseInt(nodeNoWidth) + root.getNodeNo().length())))
						htmlCode.append(TreeConst.VERTICAL_LINE);
					else
						htmlCode.append(TreeConst.BLANK_LINE);
					
					htmlCode.append("'>");
				}
			}

			if (null == nextNode)										//最后一个节点
			{
				htmlCode.append("<img src='")
						.append(realImageDir)
						.append(TreeConst.LAST_LEAF_LINE)
						.append("'>");
			}
			else
			{
				//判断节点类型，绘制相关节点线型
				if (node.getNodeNo().equals(nextNode.getParentNodeNo()))			//中间一般节点
				{				
					htmlCode.append("<img src='")
							.append(realImageDir);

					if (this.hasNextBrother(node.getNodeNo()))
					{
						if ("static".equals(type))
						{
							if (currLevel < maxLever)
								htmlCode.append(TreeConst.NODE_OPEN_LINE);
							else
								htmlCode.append(TreeConst.NODE_LINE);
						}
						else
						{
							if (node.getState() == 1)
								htmlCode.append(TreeConst.NODE_OPEN_LINE);
							else
								htmlCode.append(TreeConst.NODE_LINE);
						}
					}
					else
					{
						if ("static".equals(type))
						{
							if (currLevel < maxLever)
								htmlCode.append(TreeConst.LAST_NODE_OPEN_LINE);
							else
								htmlCode.append(TreeConst.LAST_NODE_LINE);
						}
						else
						{
							if (node.getState() == 1)
								htmlCode.append(TreeConst.LAST_NODE_OPEN_LINE);
							else
								htmlCode.append(TreeConst.LAST_NODE_LINE);
						}
					}
					
					//节点线条链接信息
					htmlCode.append(this.getNodeAction("line", node, layers));
				}		
				else	//叶子节点
				{
					htmlCode.append("<img src='")
							.append(realImageDir);
							
					if (node.getNodeNo().length() == nextNode.getNodeNo().length())
						htmlCode.append(TreeConst.MIDDLE_LEAF_LINE);						//同级叶子节点
					else
						htmlCode.append(TreeConst.LAST_LEAF_LINE);							//最后一个叶子节点
				}
				
				htmlCode.append("'>");
			}
		}
		
		return htmlCode.toString();
	}

	/**
	 * @param node
	 * @return
	 */
	private boolean hasNextBrother(String nodeNo)
	{
		boolean flag = false;
		
		int currentTail = Integer.parseInt(nodeNo.substring(nodeNo.length() - Integer.parseInt(nodeNoWidth)));
		int nextTail = 0;
		TreeNode nodeData = null;
		for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
		{
			nodeData = (TreeNode) itr.next();
			nextTail = Integer.parseInt(nodeData.getNodeNo().substring(nodeData.getNodeNo().length() - Integer.parseInt(nodeNoWidth)));
			if (nextTail > currentTail && nodeNo.substring(0, nodeNo.length() - Integer.parseInt(nodeNoWidth)).equals(nodeData.getParentNodeNo()))
			{
				flag = true;
				break;
			}
		}

		return flag;
	}

	/**
	 * 产生节点的链接html代码
	 * 
	 * @param node
	 * @return
	 */
	private String getNodeHref(TreeNode node)
	{
		StringBuffer htmlCode = new StringBuffer();
		
		if ("true".equals(showHref))	//显示超链接
		{
			if (StrUtil.isEmptyStr(href))
			{
				//如果href属性为空，则默认将href修改为调用js方法。
				htmlCode.append("<a href=\"javascript:treeNodeOnClick('")
						.append(node.getId())
						.append(",")
						.append(node.getNodeName())
						.append(",")
						.append(node.getNodeNo())
						.append(",")
						.append(node.getParentNodeNo())
						.append(",")
						.append(node.getRemark())
						.append("')\">")
						.append(node.getNodeName())
						.append("</a></span>");
			}
			else
			{
				htmlCode.append("<a href='")
				// .append(request.getAttribute("root"))
						.append(href);

				if (href.indexOf('?') == -1)
					htmlCode.append("?");
				else
					htmlCode.append("&");
				
				// 链接参数
				htmlCode.append("id=")
						.append(node.getId())
						.append("&nodeNo=")
						.append(node.getNodeNo())
						.append("&parentNodeNo=")
						.append(node.getParentNodeNo())
						.append("&remark=")
						.append(node.getRemark())
						.append("&nodeName=")
						.append(node.getNodeName())
						.append("'");
				
				//选中节点
				htmlCode.append(" onclick=\"changeIconMenu('" + node.getId() + "');\"");

				// target 属性
				if (null != target)
					htmlCode.append(" target='")
							.append(target)
							.append("'");

				htmlCode.append(">")
						.append(node.getNodeName())
						.append("</a></span>");
			}
		}
		else
		{
			//不显示超链接
			htmlCode.append(node.getNodeName())
					.append("</span>");
		}

		return htmlCode.toString();
	}

	/**
	 * 产生指定节点的输入框（单选、复选按钮）页面html代码
	 * 
	 * @return
	 */
	private String getNodeInputBox(TreeNode node, TreeNode nextNode)
	{
		if (null == inputBox)
			return "&nbsp;";
		
		StringBuffer htmlCode = new StringBuffer();
		
		if ("all".equalsIgnoreCase(inputBox.getShowStyle()))
		{
			htmlCode.append(this.getInputBox(node));
		}
		else
		{
			if (null == nextNode)
			{
				//logger.info("last node!");
				//最后一个节点
				htmlCode.append(this.getInputBox(node));
			}
			else
			{
				//logger.info("node' length:" + node.getNodeNo().length() + ",nextNode's length:" + nextNode.getNodeNo().length());
				if (node.getNodeNo().length() >= nextNode.getNodeNo().length())
					htmlCode.append(this.getInputBox(node));
			}
		}

		return htmlCode.toString();
	}
	
	/**
	 * 产生输入框的html代码
	 * 
	 * @param node
	 * @return
	 */
	private String getInputBox(TreeNode node)
	{
		StringBuffer htmlCode = new StringBuffer();
		
		htmlCode.append("<input type='")
				.append(inputBox.getType())
				.append("' name='")
				.append(inputBox.getName())
				.append("' value='");
					
		if ("idOnly".equals(inputBox.getValueStyle()))
			htmlCode.append(node.getId());
		else
			htmlCode.append(node.getId())
					.append(splitStr)
					.append(node.getNodeName())
					.append(splitStr)
					.append(node.getNodeNo())
					.append(splitStr)
					.append(node.getRemark());
		htmlCode.append("'");
		
		if (!"".equals(inputBox.getDefaultValue()))
		{
//			logger.info("选择框:"+inputBox.getDefaultValue()+";");
//			logger.info("节点号:"+node.getNodeNo()+";");
			if (inputBox.getDefaultValue().equals(node.getNodeNo()) ||
				-1 != ((";"+inputBox.getDefaultValue()+";").indexOf(";"+node.getNodeNo()+";")))
				htmlCode.append(" checked");
		}
		
		htmlCode.append(">");
		
		return htmlCode.toString();
	}
	
	/**
	 * 获取静态树的数据源
	 */
	private List<TreeNode> getStaticTreeData()
	{
		List<TreeNode> treeDatas = new ArrayList<TreeNode>();
		if (null != request.getAttribute(this.treeName))
		{
			try
			{
				treeDatas = (List<TreeNode>) request.getAttribute(this.treeName);
			}
			catch (Exception e)
			{
				logger.error(treeName + "健值的request缓冲中没有List对象。");
			}
		}
		else
			if (null != session.getAttribute(this.treeName))
			{
				try
				{
					treeDatas = (List<TreeNode>) session.getAttribute(this.treeName);
				}
				catch (Exception e)
				{
					logger.error(treeName + "健值的session缓冲中没有List对象。");
				}
			}
			else
				{
					logger.error("request、session缓冲中没有要显示的树型数据");
				}
		
		return treeDatas;
	}

	//============================================================================
	//									动态树代码生成区
	//============================================================================
	/**
	 * 产生动态树的页面html代码
	 * 
	 * @return
	 */
	private String getDynamicTree() throws Exception
	{
		StringBuffer htmlCode = new StringBuffer();
		
		this.getDynamicTreeData();
		if (null == dTree)
			htmlCode.append("指定的类没有实现DynamicTree接口");
		else
		{
			htmlCode.append(this.getRootNode());
			htmlCode.append(this.getAllTreeNode());
			htmlCode.append(this.getJavaScript());
		}
		
		return htmlCode.toString();
	}

	/**
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private void getDynamicTreeData() throws Exception
	{
//		try
//		{
			dTree = (DynamicTree) Class.forName(treeName).newInstance();
			
			TreeNode node = null;
			if (null == session.getAttribute(treeName))
			{
				//第一次加载该树，获取一级节点信息
				treeData = new ArrayList<TreeNode>();
				treeData.addAll(dTree.getSonListOfTreeNode(root.getNodeNo()));
//				展开第1级（根节点始终处于展开状态）
//				for (Iterator itr = treeData.iterator(); itr.hasNext();)
//				{
//					node = (TreeNode) itr.next();
//					//提前预制第一级节点状态（后续会逐个节点同步该状态）。
////					node.setIsLoadedSonList(true);
////					node.setState(1);
//				}
				
				//按照展开级别获取指定范围内的数据
				List<TreeNode> tempList = new ArrayList<TreeNode>();//, sonList = null;
				int currLever = 0;
				int maxLever = Integer.parseInt(expandLevel);
				for (int i = 1; i <= maxLever; i++)	//遍历用户设置的展开级别（从第1级到用户规定的级别）。
				{
					tempList.clear();
					for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
					{
						node = (TreeNode) itr.next();
						//按照顺序加载添加当前节点（不管当前节点是否参与本级展开比较）。
						tempList.add(node);
						
						currLever = (node.getNodeNo().length() - root.getNodeNo().length()) / Integer.parseInt(nodeNoWidth);
						//logger.debug("currLevel:" + currLever + ",maxLevel:" + maxLever);
						if (currLever == i)	//每一次循环只检查和当前级别相关的节点。
						{
							//logger.debug("i:" + i + ",currLevel:" + currLever);
//							if (currLever >= (maxLever - 2))
//							tempList.addAll(dTree.getSonListOfTreeNode(node.getNodeNo()));
//							else
//							{
								//加载直接子节点。
								tempList.addAll(dTree.getSonListOfTreeNode(node.getNodeNo()));
								//将当前节点的字节点加载标志改为已处理。
								node.setIsLoadedSonList(true);
								if (currLever != maxLever)	//最后一级结点不展开，只加载其字节点。
									node.setState(1);	//设置展开标志
								
//								for (Iterator itr1 = sonList.iterator(); itr1.hasNext();)
//								{
//									node = (TreeNode) itr1.next();
//									if (i != maxLever)	//非最后1级节点展开
//										node.setState(1);
//
//									tempList.add(node);
//								}
//							}
						}
					}
					
					treeData.clear();
					treeData.addAll(tempList);
				}
			}
			else
				treeData = (List<TreeNode>) session.getAttribute(treeName);

			if (null != request.getParameter("nodeId"))			//节点展开、合并操作
			{
				String action = request.getParameter("action");
				node = this.findNodeById(request.getParameter("nodeId"));

				if ("open".equals(action))
				{
					//展开当前节点
					node.setState(1);

//					//是否加载过子节点了？？？
//					if (node.isLoadedSonList())
//					{
//						if (!"never".equals(refreshMode))
//							this.loadSonList(node);
//					}
//					else
						this.loadSonList(node);	//每次展开都要重新加载子节点信息
					
//					node.setIsLoadedSonList(true);
					//logger.debug("new node number:" + treeData.size());
				}
				else
					node.setState(2);	//设置节点状态为合并
			}
			
			//缓冲当前数据
			session.setAttribute(treeName, treeData);
			
//			logger.debug("new tree data list=======================================");
//			for (Iterator itr = treeData.iterator(); itr.hasNext();)
//			{
//				node = (TreeNode) itr.next();
//				logger.info(node.getNodeNo() + "," + node.getNodeName() + "," + node.getState());
//			}
//			logger.debug("=========================================================");
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			logger.error("树Tag错误：", e);
//		}
	}

	/**
	 * 加载指定节点的下属节点数据（首先清除当前节点下的所有非同步数据，然后重新获取直接的2级节点数据）
	 * 
	 * @param string
	 */
	private void loadSonList(TreeNode currNode)
	{
		//清除该节点下所有的脏数据（非同步）。
		List<TreeNode> tempList = new ArrayList<TreeNode>();
		TreeNode node = null;
		for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
		{
			node = (TreeNode) itr.next();
//			if (node.getParentNodeNo().startsWith(currNode.getNodeNo()) && !node.getParentNodeNo().equals(currNode.getNodeNo()))
//				continue;
			if (!node.getParentNodeNo().startsWith(currNode.getNodeNo()))	//非currNode节点的数据
				tempList.add(node);
		}
		treeData.clear();
		treeData.addAll(tempList);	//已清理过的新列表数据。
		
		//获取第1级节点数据（默认不展开）
		tempList.clear();
		for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
		{
			node = (TreeNode) itr.next();
			tempList.add(node);
			if (node.getNodeNo().equals(currNode.getNodeNo()))	//目标节点
				tempList.addAll(dTree.getSonListOfTreeNode(node.getNodeNo()));
		}
		treeData.clear();
		treeData.addAll(tempList);
		
		//获取第2级节点数据（用于显示展开当前节点后所有直接子节点的父子状态）
		tempList.clear();
		for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
		{
			node = (TreeNode) itr.next();
			tempList.add(node);
			if (node.getParentNodeNo().equals(currNode.getNodeNo()))
				tempList.addAll(dTree.getSonListOfTreeNode(node.getNodeNo()));
		}

		treeData.clear();
		treeData.addAll(tempList);
		
		//当前节点的直接2级子节点已加载过了。
		node.setIsLoadedSonList(true);
	}

	/**
	 * @param nodeId
	 * @return
	 */
	private TreeNode findNodeById(String nodeId)
	{
		TreeNode tempNode = null;
		
		for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
		{
			tempNode = (TreeNode) itr.next();

			if (nodeId.equals(tempNode.getId()))
				return tempNode;
		}
		
		return null;
	}

	/**
	 * @param nodeId
	 * @return
	 */
	private TreeNode findNode(String nodeNo)
	{
		TreeNode tempNode = null;
		
		for (Iterator<TreeNode> itr = treeData.iterator(); itr.hasNext();)
		{
			tempNode = (TreeNode) itr.next();

			if (nodeNo.equals(tempNode.getNodeNo()))
				return tempNode;
		}
		
		return null;
	}
	
	/**
	 * @return
	 */
	public String getExpandLevel()
	{
		return expandLevel;
	}

	/**
	 * @return
	 */
	public InputBoxTag getInputBox()
	{
		return inputBox;
	}

	/**
	 * @return
	 */
	public String getRefreshMode()
	{
		return refreshMode;
	}

	/**
	 * @return
	 */
	public RootTag getRoot()
	{
		return root;
	}

	/**
	 * @return
	 */
	public StyleTag getStyle()
	{
		return style;
	}

	/**
	 * @return
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @return
	 */
	public String getTreeName()
	{
		return treeName;
	}

	/**
	 * @return
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param string
	 */
	public void setExpandLevel(String string)
	{
		if (!"0".equals(string))
			expandLevel = string;
	}

	/**
	 * @param box
	 */
	public void setInputBox(InputBoxTag box)
	{
		inputBox = box;
	}

	/**
	 * @param string
	 */
	public void setRefreshMode(String string)
	{
		refreshMode = string;
	}

	/**
	 * @param root
	 */
	public void setRoot(RootTag root)
	{
		this.root = root;
	}

	/**
	 * @param style
	 */
	public void setStyle(StyleTag style)
	{
		this.style = style;
	}

	/**
	 * @param string
	 */
	public void setTarget(String string)
	{
		target = string;
	}

	/**
	 * @param string
	 */
	public void setTreeName(String string)
	{
		treeName = string;
	}

	/**
	 * @param string
	 */
	public void setType(String string)
	{
		type = string;
	}
	/**
	 * @return
	 */
	public String getHref()
	{
		return href;
	}

	/**
	 * @param string
	 */
	public void setHref(String string)
	{
		href = request.getContextPath() + string;
	}
	/**
	 * @param string
	 */
	public void setNodeNoWidth(String string)
	{
		nodeNoWidth = string;
	}

	/**
	 * @param showHref the showHref to set
	 */
	public void setShowHref(String showHref)
	{
		this.showHref = showHref;
	}
}
