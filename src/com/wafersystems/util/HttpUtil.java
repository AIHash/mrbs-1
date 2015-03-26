package com.wafersystems.util;

/**
 * Copyright (c) 2006-2008 Wafer System Xi'an Software Corporation. 
 * All Rights Reserved.
 *
 * This SOURCE CODE FILE, which has been provided by Wafer System Software as part of 
 * a Wafer System Software product for use ONLY by licensed users of the product, includes
 * CONFIDENTIAL and PROPRIETARY information of Wafer System Software.
 *
 * USE OF THIS SOFTWARE IS GOVERNED BY THE TERMS AND CONDITIONS OF THE LICENSE STATEMENT 
 * AND LIMITED WARRANTY FURNISHED WITH THE PRODUCT.
 *
 * IN PARTICULAR, YOU WILL INDEMNIFY AND HOLD WAFER SYSTEM SOFTWARE, ITS RELATED COMPANIES 
 * AND ITS SUPPLIERS, HARMLESS FROM AND AGAINST ANY CLAIMS OR LIABILITIES ARISING OUT OF 
 * THE USE, REPRODUCTION, OR DISTRIBUTION OF YOUR PROGRAMS, INCLUDING ANY CLAIMS OR 
 * LIABILITIES ARISING OUT OF OR RESULTING FROM THE USE, MODIFICATION, OR DISTRIBUTION OF 
 * PROGRAMS OR FILES CREATED FROM, BASED ON, AND/OR DERIVED FROM THIS SOURCE CODE FILE.
 */

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.log4j.Logger;

import com.wafersystems.util.StrUtil;

/**
 * 功能描述：Http工具类
 *
 * 创建人：	AnDong
 * 创建日期：	2008-10-18 下午02:03:57
 * 版本号：	V1.0
 */
public class HttpUtil
{
	private static Logger logger = Logger.getLogger(HttpUtil.class);

	private HttpUtil(){}
	
	/**
	 * 打开指定的URL地址
	 * 
	 * @param hClient	HttpClient对象
	 * @param url		URL路径
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String openURL(HttpClient hClient, String url) throws Exception
	{
		HttpMethod method = null;
		try
		{
			//建立Get方法
			method = new GetMethod(url);
			// 打开URL连接
			int result = hClient.executeMethod(method);
			//查看cookie信息
			//getCookie(method.getURI().getHost(), method.getURI().getPort(), "/" , false , hClient.getState().getCookies());

			//检查是否重定向
			result = checkRedirect(method.getURI().getHost(), method.getURI().getPort(), hClient, method, result);

			if (result != HttpStatus.SC_OK)
				logger.error(method.getPath() + "打开错误：" + method.getStatusLine().toString() + "\r\n" + method.getResponseBodyAsString());

			return method.getResponseBodyAsString();
		}
		finally
		{
			method.releaseConnection();
		}
	}

	/**
	 * 查看当前访问URL的Cookie信息
	 * 
	 * 
	 */
	public static void getCookie(String ip, int port, String path, boolean secure, Cookie[] httpCookies)
	{
		CookieSpec cookiespec = CookiePolicy.getDefaultSpec(); 
		Cookie[] cookies = cookiespec.match(ip, port, path , secure , httpCookies); 
		if (cookies.length == 0)
			logger.debug("没有Cookie信息");
		else
		{
			logger.debug("Cookie信息：");
			for ( int i = 0; i < cookies.length; i++)
				logger.debug(cookies[i].toString());
		}
	}

	/**
	 * 检查请求重定向逻辑
	 * 
	 * @param ip			IP地址
	 * @param port			端口号
	 * @param hClient		HttpClient对象
	 * @param method		HttpMethod对象
	 * @param lastResult	上次方法执行结果
	 * 
	 * @return
	 */
	public static int checkRedirect(String ip, int port, HttpClient hClient, HttpMethod method, int lastResult) throws Exception
	{
		int result = HttpStatus.SC_OK;

		if ((lastResult == HttpStatus.SC_MOVED_TEMPORARILY) || (lastResult == HttpStatus.SC_MOVED_PERMANENTLY) ||
			(lastResult == HttpStatus.SC_SEE_OTHER)|| (lastResult == HttpStatus.SC_TEMPORARY_REDIRECT))
		{
			//获取重定向后的URL地址
			Header header = method.getResponseHeader("location");
			if (header != null)
			{
				String newURI = header.getValue();
				if (StrUtil.isEmptyStr(newURI))
					newURI = "http://" + ip + ":" + port + "/";
				
				//新的URL没有带协议和IP地址信息
				if (!newURI.startsWith("http"))
					newURI = "http://" + ip + ":" + port + newURI;
				logger.warn("重定向至：" + newURI);
				method = new GetMethod(newURI);
				result = hClient.executeMethod(method);
			}
		}
		
		return result;
	}
}

