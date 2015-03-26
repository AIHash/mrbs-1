package com.wafersystems.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 日期工具类
 *
 * @author AnDong
 */
public class DateUtil
{
    private final static Logger logger = LoggerFactory.getLogger(DateUtil.class);

    /**
     * 日期分隔符
     */
    private final static String DATE_SPLIT_SIGN = "-";

    /**
     * 时间分隔符
     */
    private final static String TIME_SPLIT_SIGN = ":";
    /**
     * UTC日期格式
     */
    private final static SimpleDateFormat UTC_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
    
    private DateUtil()
    {}

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate()
    {
        String dateStr = "";

        int temp = 0;
        Calendar calendar = Calendar.getInstance();

        dateStr = calendar.get(Calendar.YEAR) + DATE_SPLIT_SIGN;

        temp = calendar.get(Calendar.MONTH) + 1;
        if (temp < 10)
        {
            dateStr += "0";
        }
        dateStr += temp + DATE_SPLIT_SIGN;

        temp = calendar.get(Calendar.DAY_OF_MONTH);
        if (temp < 10)
        {
            dateStr += "0";
        }
        dateStr += temp;

        return dateStr;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentTime()
    {
        String timeStr = "";

        int temp = 0;
        Calendar calendar = Calendar.getInstance();

        temp = calendar.get(Calendar.HOUR_OF_DAY);
        if (temp < 10)
        {
            timeStr += "0";
        }
        timeStr += temp + TIME_SPLIT_SIGN;

        temp = calendar.get(Calendar.MINUTE);
        if (temp < 10)
        {
            timeStr += "0";
        }
        timeStr += temp + TIME_SPLIT_SIGN;

        temp = calendar.get(Calendar.SECOND);
        if (temp < 10)
        {
            timeStr += "0";
        }
        timeStr += temp;

        return timeStr;
    }

    /**
     * 获取Date格式的日期信息
     * 
     * @param date	日期（yyyy-MM-dd）
     * 
     * @return Date
     */
    public static Date getDate(String date)
    {
        if (date == null)
            return null;
        else
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                return sdf.parse(date);
            }
            catch (Exception e)
            {
            	logger.error("日期格式转换错误：", e);
                return null;
            }
        }
    }
    
    /**
     * 获取Date格式的日期时间信息
     * 
     * @param dateTime	日期时间（yyyy-MM-dd HH:mm:ss）
     * 
     * @return Date
     */
    public static Date getDateTime(String dateTime)
    {
        if (dateTime == null)
            return null;
        else
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                return sdf.parse(dateTime);
            }
            catch (Exception e)
            {
            	logger.error("日期时间格式转换错误：", e);
                return null;
            }
        }
    }
    
    /**
     * 获取Date格式的日期时间信息
     * 
     * @param dateTime	日期时间（yyyy-MM-dd HH:mm）
     * 
     * @return Date
     */
    public static Date getDateTime_H(String dateTime)
    {
        if (dateTime == null)
            return null;
        else
        {
            try
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return sdf.parse(dateTime);
            }
            catch (Exception e)
            {
            	logger.error("日期时间格式转换错误：", e);
                return null;
            }
        }
    }
  public static Date getDateTimeByHours(String dateTime){
	  if (dateTime == null)
          return null;
      else
      {
          try
          {
              SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
              return sdf.parse(dateTime);
          }
          catch (Exception e)
          {
          	logger.error("日期时间格式转换错误：", e);
              return null;
          }
      }
  }
    
    /**
     * 将日期转化为 "yyyy-MM-dd HH:mm:ss" 类型字符串
     * 
     * @param dateTime
     * 
     * @return
     */
    public static String getDateTimeStr(Object dateTime)
    {
    	try
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
    		 return sdf.format(dateTime); 
		}
    	catch (Exception e)
    	{
			logger.error("转化日期格式错误：", e);
			return null;
		}
    }
    
    /**
     * 将日期转化为 "yyyy/MM/dd HH:mm:ss" 类型字符串
     * 
     * @param dateTime
     * 
     * @return
     */
    public static String getDateTimeStrFancl(Object dateTime)
    {
    	try
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        
    		 return sdf.format(dateTime); 
		}
    	catch (Exception e)
    	{
			logger.error("转化日期格式错误：", e);
			return null;
		}
    }
    
    /**
     * 将日期转化为 "yyyy-MM-dd" 类型字符串
     * 
     * @param dateTime
     * 
     * @return
     */
    public static String getDateStr(Object dateTime)
    {
    	try
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        
    		 return sdf.format(dateTime); 
		}
    	catch (Exception e)
    	{
			logger.error("转化日期格式错误：", e);
			return null;
		}
    }
    
    /**
     * 将日期转化为 "yyyy/MM/dd" 类型字符串
     * 
     * @param dateTime
     * 
     * @return
     */
    public static String getDateStr1(Object dateTime)
    {
    	try
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        
    		 return sdf.format(dateTime); 
		}
    	catch (Exception e)
    	{
			logger.error("转化日期格式错误：", e);
			return null;
		}
    }
    
    public static String getTimeStr(Object dateTime)
    {
    	try
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
    		 return sdf.format(dateTime); 
		}
    	catch (Exception e)
    	{
			logger.error("转化日期格式错误：", e);
			return null;
		}
    }
    
    
    /**
     * 将日期转化为 "yyyy-MM-dd" 类型字符串
     * 
     * @param dateTime
     * 
     * @return
     */
    public static String getDate()
    {
    	try
    	{
    		 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    		 
    		 return sdf.format(new Date()); 
		}
    	catch (Exception e)
    	{
			logger.error("转化日期格式错误：", e);
			return null;
		}
    }
    
    
    
    /**
     * 比较日期大小
     *
     * @param fromDate		开始日期（yyyy-MM-dd）
     * @param toDate		结束日期（yyyy-MM-dd）
     *
     * @return boolean      false：日期参数为null；日期参数非法；起始日期大于结束日期。
     * 						true：日期格式正确，结束日期大于起始日期。
     */
    public static boolean compareDate(String fromDate, String toDate)
    {
        if (fromDate == null || toDate == null)
            return false;

        try
        {
            //时间字符串符合yyyy-MM-dd模式;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date from = sdf.parse(fromDate);
            Date to = sdf.parse(toDate);

            if (from.getTime() > to.getTime())
                return false;
        }
        catch (Exception e)
        {
            logger.error("日期转换错误:", e);
            return false;
        }

        return true;
    }

    /**
     * 比较2个日期时间的大小
     * 
     * @param from	起始日期时间
     * @param to	结束日期时间
     * 
     * @return boolean	true：日期时间格式有效 && 起始日期时间<=结束日期时间
     * 					false:日期时间格式无效 || 起始日期时间>结束日期时间
     */
    public static boolean compareDateTime(String from, String to)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date fromDateTime = sdf.parse(from);
			Date toDateTime = sdf.parse(to);

			if (fromDateTime.getTime() > toDateTime.getTime())
				return false;
		}
		catch (Exception e)
		{
			logger.error("日期时间格式错误：", e);
			return false;
		}
		
		return true;
	}
    

   /**
    *     * 比较2个日期时间的大小
    * 
    * @param from	起始日期时间
    * @param to	结束日期时间
    * 
    * @return boolean	true：日期时间格式有效 && 起始日期时间<=结束日期时间
    * 					false:日期时间格式无效 || 起始日期时间>结束日期时间
    */
   public static boolean compareDateTimeEQ(String from, String to)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			Date fromDateTime = sdf.parse(from);
			Date toDateTime = sdf.parse(to);

			if (fromDateTime.getTime() >= toDateTime.getTime())
				return false;
		}
		catch (Exception e)
		{
			logger.error("日期时间格式错误：", e);
			return false;
		}
		
		return true;
	}
    
    /**
     * 日期格式校验
     * 
     * @param date		日期（yyyy-MM-dd）
     * @return boolean  校验成功返回true,否则返回false;
     */
    public static boolean isDate(String date)
    {
        if (date == null)
            return false;

        try
        {
            //时间字符串符合yyyy-MM-dd模式;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            sdf.parse(date);
            
            return true;
        }
        catch (Exception e)
        {
        	logger.error("日期格式校验错误：", e);
            return false;
        }
    }

    /**
     * 日期时间格式校验
     * 
     * @param date		日期（yyyy-MM-dd HH:mm:ss）
     * 
     * @return boolean  校验成功返回true,否则返回false;
     */
    public static boolean isDateTime(String dateTime)
    {
        if (dateTime == null)
            return false;

        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.parse(dateTime);
            
            return true;
        }
        catch (Exception e)
        {
        	logger.error("日期时间格式校验错误：", e);
            return false;
        }
    }

    /**
    * 转换UTC格式日期
    * Example:"1126173288 000"毫秒 -->Thu Sep 08 17:54:48 CST 2005
    * @param utcDate String  //秒数 （Cisco话单中是秒）
    */
    public static synchronized String getTrueDateFromUtcStr(String utcDate)
    {
        return UTC_DATE_FORMAT.format(new Date(Long.parseLong(utcDate) * 1000));
    }
    
    /**
     * 得到某个日期所属月份的最大日期值；
     * 
     * @param date String
     * @return int
     */
    public static int getMaxDayOfMonth(String date)
    {
        String[] mydate = date.split("-");
        
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.YEAR, Integer.parseInt(mydate[0]));
        gcal.set(Calendar.MONTH, Integer.parseInt(mydate[1]) - 1);
        gcal.set(Calendar.DAY_OF_MONTH, 1);
        //gcal.set(Calendar.DATE, Integer.parseInt(mydate[2]));
        //System.out.println(" Max : " +
        //                   gcal.getActualMaximum(Calendar.DAY_OF_MONTH));
        
        return gcal.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取指定日期的后N天日期信息
     * 
     * @param date 		日期（yyyy-MM-dd）
     * @param days		天数
     * 
     * @return String
     */
    public static String afterDays(String date, int days)
    {
    	return addDays(date, Math.abs(days));
    }

    /**
     * 获取指定日期的前N天日期信息
     * 
     * @param date 		日期（yyyy-MM-dd）
     * @param days		天数
     * 
     * @return String
     */
    public static String beforDays(String date, int days)
    {
    	return addDays(date, 0 - Math.abs(days));
    }
   
    
    
    /**
     * 获取指定日期的后N小时日期信息
     * 
     * @param date 		日期（yyyy-MM-dd HH:mm:ss）
     * @param hour	小时
     * 
     * @return String
     */
    public static String afterHour(String date, int hour)
    {
    	return addHour(date, Math.abs(hour));
    }
    
    /**
     * 获取指定日期的后N分钟日期信息
     * 
     * @param date 		日期（yyyy-MM-dd HH:mm:ss）
     * @param minute	分钟
     * 
     * @return String
     */
    public static String afterMinute(String date, int minute)
    {
    	return addMinute(date, Math.abs(minute));
    }

    /**
     * 获取指定日期的前N分钟日期信息
     * 
     * @param date 		日期（yyyy-MM-dd HH:mm:ss）
     * @param minute	分钟
     * 
     * @return String
     */
    public static String beforMinute(String date, int minute)
    {
    	return addMinute(date, 0 - Math.abs(minute));
    }
    
    /**
     * 获取指定日期的后N天日期信息
     * 
     * @param date 		日期（yyyy-MM-dd）
     * @param days		天数（-1表示减去相应的天数）
     * 
     * @return String
     */
    private static String addDays(String date, int days)
    {
        //logger.debug("Enter addOneDayToCurrentDay()");
        String[] mydate = date.split("-");
        
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.YEAR, Integer.parseInt(mydate[0]));
        gcal.set(Calendar.MONTH, Integer.parseInt(mydate[1]) - 1);
        gcal.set(Calendar.DATE, Integer.parseInt(mydate[2]));
        gcal.add(Calendar.DATE, days);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String addDate = sdf.format(gcal.getTime());
        
        //logger.debug("Exit addOneDayToCurrentDay()");
        return addDate;
    }
    
    /**
     * 获取指定日期的后N天日期信息
     * 
     * @param date 		日期（yyyy-MM-dd）
     * @param minute	分钟（-1表示减去相应的天数）
     * 
     * @return String
     */
    public static String addMinute(String date, int minute)
    {
        //logger.debug("Enter addOneDayToCurrentDay()");
        String[] mydate = date.split(" ")[0].split("-");
        
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.YEAR, Integer.parseInt(mydate[0]));
        gcal.set(Calendar.MONTH, Integer.parseInt(mydate[1]) - 1);
        gcal.set(Calendar.DATE, Integer.parseInt(mydate[2]));

        String[] time = date.split(" ")[1].split(":");
        gcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        gcal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        gcal.set(Calendar.SECOND, Integer.parseInt(time[2]));
        
        gcal.add(Calendar.MINUTE, minute);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addDate = sdf.format(gcal.getTime());

        //logger.debug("Exit addOneDayToCurrentDay()");
        return addDate;
    }
    
    
    /**
     * 获取指定日期的后N小时日期信息
     * @param date
     * @param hour
     * @return
     */
    public static String addHour(String date, int hour)
    {
        //logger.debug("Enter addOneDayToCurrentDay()");
        String[] mydate = date.split(" ")[0].split("-");
        
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.YEAR, Integer.parseInt(mydate[0]));
        gcal.set(Calendar.MONTH, Integer.parseInt(mydate[1]) - 1);
        gcal.set(Calendar.DATE, Integer.parseInt(mydate[2]));

        String[] time = date.split(" ")[1].split(":");
        gcal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
        gcal.set(Calendar.MINUTE, Integer.parseInt(time[1]));
        gcal.set(Calendar.SECOND, Integer.parseInt(time[2]));
        
        gcal.add(Calendar.HOUR, hour);
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String addDate = sdf.format(gcal.getTime());

        //logger.debug("Exit addOneDayToCurrentDay()");
        return addDate;
    }
    
    
    
    /**
     * 获取指定日期时间增加N秒后的日期时间
     * 
     * @param dateTime	日期时间（yyyy-MM-dd HH:mm:ss）
     * @param seconds	秒
     * 
     * @return String
     */
    public static String addSeconds(String dateTime, int seconds)
    {
    	if (isDateTime(dateTime))
    	{
        	GregorianCalendar gcal = new GregorianCalendar();
            gcal.setTime(getDateTime(dateTime));
            gcal.add(Calendar.SECOND, seconds);
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(gcal.getTime());
    	}
    	else
    		return null;
    }
    
    /**
     * 获取指定日期时间增加N秒后的日期时间
     * 
     * @param dateTime	日期时间（yyyy-MM-dd HH:mm:ss）
     * @param seconds	秒
     * 
     * @return Date  （yyyy-MM-dd HH:mm:ss）
     */
    public static Date addSeconds(Date dateTime, int seconds)
    {
		Date dateTimeAdd = null;
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateTimeStr = sdf.format(dateTime);
			
			if (isDateTime(dateTimeStr))
			{
				GregorianCalendar gcal = new GregorianCalendar();
				gcal.setTime(dateTime);
				gcal.add(Calendar.SECOND, seconds);
				dateTimeAdd = sdf.parse(sdf.format(gcal.getTime()));
			}
			else
				dateTimeAdd = null;
		}
		catch (Exception e)
		{
			logger.error("日期秒数操作错误：", e);
		}
		
		return dateTimeAdd;
    }
    
    /**
	 * 获取指定日期对应的星期信息
	 * 
	 * @param date
	 *            String
	 * 
	 * @return int
	 */
    public static int getWeek(String date)
    {
        //logger.debug("Enter getWeekDayFromDate()");
        String[] mydate = date.split("-");
        
        GregorianCalendar gcal = new GregorianCalendar();
        gcal.set(Calendar.YEAR, Integer.parseInt(mydate[0]));
        gcal.set(Calendar.MONTH, Integer.parseInt(mydate[1]) - 1);
        gcal.set(Calendar.DATE, Integer.parseInt(mydate[2]));
        
        return gcal.get(Calendar.DAY_OF_WEEK);
    }
    
    /**
     * 获取两个日期时间的间隔天数
     *
     * @param from 起始日期时间(yyyy-MM-dd HH:mm:ss)
     * @param to 结束日期时间(yyyy-MM-dd HH:mm:ss)
     *
     * @return long 间隔天数(二者之差的绝对值)
     */
    public static long getDays(String from, String to)
    {
        long days = -1;
        
        try
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date fromDateTime = sdf.parse(from);
            Date toDateTime = sdf.parse(to);
            //毫秒转化为天数
            days = (toDateTime.getTime() - fromDateTime.getTime())/(1000 * 60 * 60 *24);
 
            //不足一天按整一天算,比如今天与明天相差不是0天而是1天
            return Math.abs(days);
        }
        catch (Exception e)
        {
            logger.error("日期时间格式错误：", e);
            return days;
        }
    }
    
    
    
    
    public static String transFormatDate(String dateTime)
    {		
    	   	
    	String[] arr = dateTime.substring(0, 10).split("-");
    	
    	StringBuffer buf =new StringBuffer();
    	
    	for (int i = 0; i < arr.length; i++) {
    		buf.append(arr[i]);
		}
      	
		return buf.toString();	
    }
    
    
    

	/**
	 * 测试方法
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		System.out.println("======" + getTrueDateFromUtcStr("1236059371"));
//		try
//		{
//			System.out.println("开始连接ftp");
//			FtpClient ftp = new FtpClient("192.168.0.12",22);
//			ftp.login("wafer","cdrcmr"); 
//			System.out.println("连接成功");
//  
//		}
//		catch(Exception e)
//		{
//			System.out.println("连接失败");
//			
//			e.printStackTrace();
//		}
    	
	}
}
