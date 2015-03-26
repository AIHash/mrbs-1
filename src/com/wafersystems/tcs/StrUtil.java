package com.wafersystems.tcs;

import java.util.HashMap;
import java.util.Map;

import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.PatternCompiler;
import org.apache.oro.text.regex.PatternMatcher;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 字符串工具类
 *
 * @author AnDong
 */
public class StrUtil
{
	/**
	 * Logger
	 */
	private final static Logger logger = LoggerFactory.getLogger(StrUtil.class);
	/**
	 * Perl5Compiler
	 */
    private final static PatternCompiler compiler = new Perl5Compiler();
	/**
	 * Perl5Matcher
	 */
    private final static PatternMatcher matcher = new Perl5Matcher();
    /**
     * 正则表达式缓冲
     */
	private static Map<String, Pattern> REGEX_PATTERN = new HashMap<String, Pattern>();
	
    private StrUtil(){}

	/**
	 * 空字符串判断方法（空指针、空字符串、"null"字符串）
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmptyStr(String str)
	{
		if (null == str || "".equals(str.trim()) || "null".equals(str.trim().toLowerCase()))
			return true;
		else
			return false;
	}
	
    /**
     * 将空指针，null字符串格式化为空字符串(去处头尾的空格)
     *
     * @param str	目标字符串
     */
    public static String formatStr(String str)
    {
        if (null == str || str.equals("null"))
            return "";
        else

        return str.trim();
    }

    /**
     * 将指定的字符串数组转换为int[]
     *
     * @param str	目标字符串数组
     */
    public static int[] toIntArray(String[] str)
    {
        if (null == str)
            return new int[0];
        else
        {
        	int[] ints = new int[str.length];
        	for (int i = 0; i < str.length; i++)
        		ints[i] = Integer.parseInt(str[i]);

        	return ints;
        }
    }

    /**
     * 将指定的字符串数组转换为Integer[]
     *
     * @param value	int数组
     */
    public static Integer[] toIntegerArray(int[] value)
    {
        if (null == value)
            return new Integer[0];
        else
        {
        	Integer[] target = new Integer[value.length];
        	for (int i = 0; i < value.length; i++)
        		target[i] = new Integer(value[i]);

        	return target;
        }
    }
    
    /**
     * 将数组转换为逗号分割的Sql字符串格式（不包括"()"符号）
     * 如：域名 in ( ?,?,?)  组装括号内的数值(1,2,3)或('1','2','3')
     *
     * @param array		Object   参数数组列表（目前只支持Integer[]，String[]类型的参数）
     *
     * @return String   符合In语法规则的Sql语句
     */
    public static String arrayToSqlStr(Object array)
    {
        Object[] parameter = null;

        String delimiter = "";
        //判断数组类型，整数和字符在sql写法中不一致；
        if (array instanceof Integer[])
        {
        	parameter = (Integer[]) array;
            //logger.debug("int");
        }

        if (array instanceof String[])
        {
            delimiter = "'";
            parameter = (String[]) array;
            //logger.debug("String");
        }

        StringBuffer sqlStr = new StringBuffer();
        for (int i = 0; i < parameter.length; i++)
        	sqlStr.append(delimiter)
        		  .append((Object)parameter[i].toString().trim())
        		  .append(delimiter)
        		  .append(",");

        sqlStr.deleteCharAt(sqlStr.length() - 1); //去掉最后一个逗号；

        return sqlStr.toString();
    }

    /**
     * 获取下拉列表的编号部分内容
     *
     * @param values	下拉列表内容列表
     *
     * @return
     */
    public static int[] getFirstPartOfSelectValues(String[] values)
    {
    	if (null == values)
    		return new int[0];
    	else
    	{
    		int[] ids = new int[values.length];
    		for (int i = 0; i < values.length; i++)
			{
    			ids[i] = Integer.parseInt(values[i].split(":")[0]);
			}

    		return ids;
    	}
    }

    /**
     * 获取下拉列表的名称内容列表
     *
     * @param values	下拉列表内容列表
     *
     * @return
     */
    public static String[] getSecondPartOfSelectValues(String[] values)
    {
    	if (null == values)
    		return new String[0];
    	else
    	{
    		String[] names = new String[values.length];
    		for (int i = 0; i < values.length; i++)
			{
    			names[i] = values[i].split(":")[1];
			}

    		return names;
    	}
    }

	/**
	 * 将带格式的普通文本转换为Html格式
	 */
	public static String textToHtml(String in)
	{
		StringBuffer out = new StringBuffer();
		for (int i = 0; in != null && i < in.length(); i++)
		{
			char c = in.charAt(i);
			if (c == '\n') out.append("<br>");
			else
				// if (c == ' ')
				// out.append("&nbsp;");
				// else
				out.append(c);
		}

		return out.toString();
	}
    
	/**
	 * 转换sql特殊字符
	 * 对于纯sql要将'\'转为'\\\\'（至少mysql是这样）
	 * 对于Restrictions.like方法需将'\'转为'\\'（个人认为是因为mysql的原因）
	 * @param in
	 * @return
	 */
	public static String sqlConvert(String in, boolean isPureSql)
	{
		char[] temp = in.toCharArray();
		StringBuffer rtnStrBuf = new StringBuffer();
		for(int i=0;i<temp.length;i++){
			switch(temp[i]){
				case '\\' : rtnStrBuf.append('\\').append(temp[i]);
							if(isPureSql) rtnStrBuf.append("\\\\");
							break;
				case '\'' : rtnStrBuf.append('\\').append(temp[i]);
							break;
				case '\"' : rtnStrBuf.append('\\').append(temp[i]);
							break;
				case '%' : rtnStrBuf.append('\\').append(temp[i]);
							break;
				case '_' : rtnStrBuf.append('\\').append(temp[i]);
							break;
				default :  rtnStrBuf.append(temp[i]);
			}
		}
		return rtnStrBuf.toString();
	}
	
	/**
	 * 将Html格式转换为带格式的普通文本
	 */
	public static String htmlToText(String in)
	{
		String TextStr = "";
		int i = 0, pos = 0;

		for (i = 0; in != null && i < in.length() - 4;)
		{
			pos = in.indexOf("<br>", i);
			if (pos != -1)
			{
				TextStr = TextStr + in.substring(i, pos) + "\n";
				i = pos + 4;
			}
			else
			{
				TextStr = TextStr + in.substring(i);
				break;
			}
		}

		return TextStr;
	}
	
	/**
	 * 判断字符串是否以特定的模式开头
	 * 
	 * @param value		字符串
	 * @param pattern	模式
	 * 
	 * @return
	 */
	public synchronized static boolean isMatchPrefix(String value, String pattern)
	{
		if (isEmptyStr(value) || isEmptyStr(pattern))
			return false;
		else
		{
			try
			{
				Pattern pt = null;
				if (REGEX_PATTERN.containsKey(pattern))
					pt = REGEX_PATTERN.get(pattern);
				else
				{
					//及时编译模式
					pt = compiler.compile(pattern);
					//缓冲该模式
					REGEX_PATTERN.put(pattern, pt);
					logger.info("添加新模式：" + pattern);
				}
				
				return matcher.matchesPrefix(value, pt);
			}
			catch (Exception e)
			{
				logger.error("正则表达式错误：" + value + "：" + pattern, e);
				return false;
			}
		}
	}
	
	/**
	 * 判断字符串是否完全满足特定的模式
	 * 
	 * @param value		字符串
	 * @param pattern	模式
	 * 
	 * @return
	 */
	public synchronized static boolean isMatch(String value, String pattern)
	{
		if (isEmptyStr(value))
			return false;
		else
		{
			try
			{
				Pattern pt = null;
				if (REGEX_PATTERN.containsKey(pattern))
					pt = REGEX_PATTERN.get(pattern);
				else
				{
					//及时编译模式
					pt = compiler.compile(pattern);
					//缓冲该模式
					REGEX_PATTERN.put(pattern, pt);
					logger.info("添加模式：" + pattern);
				}
					
				return matcher.matches(value, pt);
			}
			catch (Exception e)
			{
				logger.error("正则表达式错误：" + value + "：" + pattern, e);
				return false;
			}
		}
	}
	
	/**
	 * 判断是否是IP地址格式
	 * 
	 * @param value		字符串
	 * 
	 * @return
	 */
	public static boolean isIPAddress(String value)
	{
		if (isEmptyStr(value))
			return false;
		else
			return isMatch(value, "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");
	}
	
    /**
     * 去除字符串数组元素的空格[]
     *
     * @param str	目标字符串数组
     */
    public static String[] formatArray(String[] str)
    {
        if (null == str)
            return new String[0];
        else
        {
        	String[] result = new String[str.length];
        	for (int i = 0; i < str.length; i++)
        		result[i] = str[i].trim();

        	return result;
        }
    }	

	/**
	 * Unicode字符串长度截取方法
	 * 
	 * @param source
	 * @param begin
	 * @param to
	 * @return
	 */
	public static String subStringByUnicode(String source, int begin, int length, boolean addMore) throws Exception
	{
        byte[] bt = source.getBytes();
        if (length >= bt.length)
        	return source;
        else
        {
    		String result = "";
    		int unicodeLength = 0;
            for (int i = 0; i < length; i++)
            {   
            	// 打印出字节数组中的值，可以看出只要是汉字[采用GBK编码时占两个字节/UTF-8占3个字节]，并且为两个负整数   
            	// 由于字节的第一位都是1,所以它就是由负数来表示的   
            	//System.out.println(bt[i]);
            	if (bt[i] < 0)
            		unicodeLength++;
            }
            
            // 打印出按实际出入的字节数的所截字符串   
            //System.out.println(new String(bt,0,length));   
      
            // 判断最后一个是否为负值，而且是半个汉字。   
            if (bt[length] < 0 && (0 != (unicodeLength % 2)))
            	result = new String(bt, 0, --length);
            else
            	result = new String(bt, 0, length);   

            if (addMore)
            	result += "...";
            return result;
        }
	}
	
	/**
     * 只保留字符串数组中的字母
     *
     * @param str	目标字符串数组
     */
	public static String stringFilter(String str) 
	{

		if (!isEmptyStr(str)) 
		{
			StringBuffer tempStr = new StringBuffer("");
			for (int i = 0; i < str.length(); i++) 
			{
				if (Character.isLetter(str.charAt(i))) 
				{
					tempStr.append(str.charAt(i));
				}
			}
			return tempStr.toString();
		}
		return str;
	}

	/**
	 * 测试方法
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
//		logger.info(DateUtil.getCurrentDate() + "_" + DateUtil.getCurrentTime());
//		logger.info(isIPAddress("192.168.10.102"));
//		logger.info(isIPAddress("192.168.102"));
//		logger.info(isIPAddress("192.168.10.2121"));
		System.out.println("123456:" + isMatch("123456", "[\\w ]*"));
		System.out.println("abcdefg:" + isMatch("abcdefg", "[\\w ]*"));
		System.out.println("ABCXYZ:" + isMatch("ABCXYZ", "[\\w ]*"));
		System.out.println("123abcXYZ:" + isMatch("123abcXYZ", "[\\w ]*"));
		System.out.println("123-abc-XYZ:" + isMatch("123-abc-XYZ", "[\\w ]*"));
		System.out.println("123_abc_XYZ:" + isMatch("123_abc_XYZ", "[\\w ]*"));
		System.out.println("123,abc,XYZ:" + isMatch("123,abc,XYZ", "[\\w ]*"));
		System.out.println("123 abc XYZ:" + isMatch("123 abc XYZ", "[\\w ]*"));
		System.out.println("中文 123:" + isMatch("中文 123", "[\\w ]*"));
	}
}

