package com.wafersystems.util;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XML文件解析工具类
 * 
 * @author AnDong
 */
public final class XML
{
	/**
	 * Logger for this class
	 */
	private final static Logger logger = LoggerFactory.getLogger(XML.class);
	
	private String filePath = null;
	private SAXBuilder builder = null; 
	private Document doc = null;
	private Element root = null;
	
	/**
	 * 构造函数，初始化参数(处理已经存在的XML文件)
	 * 
	 * @param filePath	要处理的XML文件绝对路径
	 * 
	 * @throws FileNotFoundException
	 * @throws JDOMException
	 * @throws IOException
	 */
	public XML(String filePath) throws FileNotFoundException, JDOMException, IOException
	{
		this.filePath = filePath;
		builder = new SAXBuilder();
		doc = builder.build(new FileInputStream(filePath));
		root = doc.getRootElement();
	}
	/**
	 * 构造函数,初始化参数(解析一个xml格式的字符串,并存储到指定路径中)
	 * @param xmlString (必要参数)要处理的XML格式字符串
	 * @param filePath  (如果不存储则可以置空)要存储xml的路径
	 * @throws JDOMException
	 * @throws IOException
	 * Add by NGY 2008-03-25
	 */
	public XML(String xmlString,String filePath) throws  JDOMException, IOException 
	{		
		this.filePath = filePath;
		builder = new SAXBuilder();
		doc = builder.build(new java.io.StringReader(xmlString));		
		root = doc.getRootElement();
	}

	
	
	/**
	 * 获取指定节点路径的元素列表
	 * 
	 * @param path	节点元素路径（使用.分割）
	 * 
	 * @return	节点元素列表
	 */
	public List getChildren(String path)
	{
		if (null == path || "".equals(path.trim()))
			return new ArrayList();
		
		path = path.replace('.', '#');
		String[] nodePath = path.split("#");
		//logger.info("Deep level:" + nodePath.length);
		List children = null;
		Element node = root;
		for (int i = 0; i < nodePath.length; i++)
		{
			//logger.info("path:" + nodePath[i]);
			children = node.getChildren(nodePath[i]);
			node = (Element) children.get(0);
		}
		
		return node.getChildren();
	}
	
	/**
	 * 获取指定节点路径的元素信息
	 * 
	 * @param path	元素路径信息
	 * 
	 * @return	元素信息
	 */
	@SuppressWarnings("unchecked")
	public Element getElement(String path)
	{
		if (null == path || "".equals(path.trim()))
			return null;
		
		path = path.replace('.', '#');
		String[] nodePath = path.split("#");
		
		List children = null;
		Element node = root;
		for (int i = 0; i <nodePath.length; i++)
		{
			children = node.getChildren(nodePath[i]);
			node = (Element) children.get(0);
		}
		
		return node;
	}
	
	/**
	 * 获取指定的节点内容为一个新的XML文件片段
	 */
	public String getFragment(String path)
	{
		return getContents(getElement(path));
	}
	
	/**
	 * 获取节点内容
	 * 
	 * @param node
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String getContents(Element node)
	{
		String tmp = "<" + node.getName() + ">";
		List sons = node.getChildren(); 
		if (sons.size() == 0)
			return tmp += node.getTextTrim() + "</" + node.getName() + ">";
		else
		{
			for (Iterator itr = sons.iterator(); itr.hasNext();)
				tmp += getContents((Element) itr.next());
			
			tmp += "</" + node.getName() + ">"; 
		}
		
		return tmp;
	}
	
	/**
	 * 保存当前所做修改内容
	 * 
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public void save() throws FileNotFoundException, IOException
	{
		XMLOutputter printDoc = new XMLOutputter();
		printDoc.output(doc, new FileOutputStream(filePath));
	}
	
	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		try
		{
			//*****************解析xml文件******************
//			XML xml = new XML("E:/java_project/IntegrationTest/web/WEB-INF/system-config.xml");
//			System.out.println(xml.getFragment("service.mail-alert"));
			
			
//			*****************解析xml字符串******************
			String xmlStr="<?xml version='1.0' encoding='utf-8'?> <CiscoIPPhoneResponse> <ResponseItem URL='http://192.168.0.115:8800/action/ps/notifyManage?infoid=17&amp;number=829025' Data='Success' Status='0' /></CiscoIPPhoneResponse>";
			XML xml = new XML(xmlStr,"c://testxml.xml");
			System.out.println(xml.getElement("ResponseItem").getAttributeValue("URL").trim());
			xml.save();
			
		}
		catch (Exception e)
		{
			logger.error("Error:", e);
		}
	}
}

