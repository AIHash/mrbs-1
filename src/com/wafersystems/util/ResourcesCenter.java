package com.wafersystems.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wafersystems.mrbs.GlobalParam;

/**
 * 功能描述：语言资源管理对象类
 *
 * 创建人：	Moon
 * 创建日期：2010-5-25
 * 版本号：	V1.0
 */
public class ResourcesCenter {
	
	private final static Logger logger = LoggerFactory.getLogger(ResourcesCenter.class);
	
	/**
	 * 资源内容参数分割符
	 */
	public static final String PARAMETERS_SPLIT_STR = "#@#";
	
	public static final String DEFAULT_LANGUAGE = null;
	
	/**
	 * 多个语言资源可选包
	 */
	private static Map<String, Map<String,String>> RESOURCES_CENTER = new HashMap<String, Map<String,String>>();
	
	/**
	 * 预加载默认语言
	 */
	static
	{
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    if (null == classLoader)
		       classLoader = ResourcesCenter.class.getClassLoader();
		    
		    InputStream is = classLoader.getResourceAsStream("Resources_" + GlobalParam.language + ".properties");
			PropertyResourceBundle rBundle  = new PropertyResourceBundle(is);	 
			Enumeration<String> enur = rBundle.getKeys();
			String keyName = null;
			String unicodeValue = null;
			Map<String,String> option = new HashMap<String,String>();
			while (enur.hasMoreElements())
			{
				keyName = enur.nextElement();
				//unicodeValue = StrUtil.getUnicodeStr(new String(rBundle.getString(keyName).getBytes("ISO-8859-1")));
				unicodeValue = rBundle.getString(keyName);
				unicodeValue = unicodeValue.replace('{', '#');
				unicodeValue = unicodeValue.replace('}', '#');
				option.put(keyName.toLowerCase(), unicodeValue);//从资源文件获取的键值采用小写.

			}
			is.close();
			logger.debug("加载语言系统默认语言（" + GlobalParam.language + "）完成！");
			RESOURCES_CENTER.put(GlobalParam.language.toLowerCase(), option);
		    
		}catch(Exception e){
			logger.error("多语言资源文件加载失败:", e);
		}
	}
	
	/**
	 * 重新加载语言资源
	 */
	private static void reloadLanguage(String pin){
		try{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		    if (null == classLoader)
		       classLoader = ResourcesCenter.class.getClassLoader();
			InputStream is = classLoader.getResourceAsStream("Resources_" + pin + ".properties");
			PropertyResourceBundle rBundle  = new PropertyResourceBundle(is);	 
			Enumeration<String> enur = rBundle.getKeys();
			String keyName = null;
			String unicodeValue = null;
			Map<String,String> option = new HashMap<String,String>();
			while (enur.hasMoreElements())
			{
				keyName = enur.nextElement();
				//unicodeValue = StrUtil.getUnicodeStr(new String(rBundle.getString(keyName).getBytes("ISO-8859-1")));
				unicodeValue = rBundle.getString(keyName);
				unicodeValue = unicodeValue.replace('{', '#');
				unicodeValue = unicodeValue.replace('}', '#');
				option.put(keyName.toLowerCase(), unicodeValue);//从资源文件获取的键值采用小写.
//					logger.debug(keyName + "=" + unicodeValue);
			}
			is.close();
			logger.debug("加载语言（" + pin + "）完成！");
			RESOURCES_CENTER.put(pin.toLowerCase(), option);
		}catch(Exception e){
			logger.error("语言" + pin + "的资源文件加载失败:", e);
		}
	}
	

	/**
	 * 取得系统默认语言资源
	 * @return
	 */
	public static Map<String, String> getDefaultResource(){
		return RESOURCES_CENTER.get(GlobalParam.language.toLowerCase());
	}
	
	/**
	 * 按指定的语言取得语言资源
	 * @param language
	 * @return
	 */
	public static Map<String, String> getResourceByLanguage(String language){
		if(StrUtil.isEmptyStr(language))
			return getDefaultResource();
		else {
			if(!RESOURCES_CENTER.containsKey(language.toLowerCase())){
				reloadLanguage(language);
				
				Map<String, String> temp = RESOURCES_CENTER.get(language);
				if(temp == null)
					temp = getDefaultResource();
				return temp;
			}else{
				return RESOURCES_CENTER.get(language.toLowerCase());
			}
		}
	}
	
	
	/**
	 * 取得指定语言资源信息
	 * @param keyName
	 * @param language
	 * @return
	 */
	public static String getMessage(String language, String keyName)
	{
		//		键值采用小写.
		return getMessage(language, keyName, null);
	}
	
	
	/**
	 * 取得默认语言资源信息 —— 带参数
	 * @param language
	 * @param keyname
	 * @param Parameters
	 * @return
	 */
	public static String getMessage(String language, String keyname, String Parameters)
	{
		String resource = keyname.toLowerCase();
		String parameters = Parameters;
		Map<String, String> resources = getResourceByLanguage(language);

		try
		{
			//键值采用小写.
			String keyName = keyname;

			if (null != keyName && !"".equals(keyName.trim()))
			{
				resource = resources.get(keyName.toLowerCase());
				if (null == resource)
				{
					resource = keyName;
					if (-1 != keyName.indexOf("."))
						logger.warn("资源文件没有定义:" + keyName);
				}
				else
				{
					if (null != parameters && !"".equals(parameters.trim()))
					{
						//把资源文件中的“,”替换成MessageTag.PARAMETERS_SPLIT_STR分隔符
						parameters = parameters.replaceAll(",", ResourcesCenter.PARAMETERS_SPLIT_STR);
						String[] params = parameters.split(ResourcesCenter.PARAMETERS_SPLIT_STR);
						for (int i = 0; i < params.length; i++)
						{
							//logger.debug(i + "-->" + params[i]);
							if (null == resources.get(params[i].toLowerCase()))
								resource = resource.replace("#" + i + "#", params[i]);
							else
								resource = resource.replace("#" + i + "#", resources.get(params[i].toLowerCase()));
						}
					}
				}
			}
		}
		catch (Exception e)
		{
			logger.warn("获取资源失败,keyname="+ keyname +"; parameters="+parameters+":",e);
			resource=keyname;
		}		
		
		return resource;
	}
	
}
