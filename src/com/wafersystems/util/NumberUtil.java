package com.wafersystems.util;

/*
 * Title: Voice Mate System
 * Copyright: Copyright (c) 2004
 * Company: Wafer Systems Ltd
 * All rights reserved.
 *
 * Created on 2001-1-1 by AnDong
 * JDK version used : 1.4.1_02
 */

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 数字工具类
 *
 * @author AnDong
 */
public class NumberUtil
{
    private final static Logger logger = Logger.getLogger(NumberUtil.class);
	/**
	 * 默认除法运算精度
	 */
	private static final int DEF_DIV_SCALE = 10; 	
	
    private NumberUtil(){}
    
    /**
     * 判断传入的字符是不是数字
     * 
     * @param number
     * @return
     */
    public static boolean isNumber(String number)
    {
    	if (null == number || "".equals(number.trim()))
    		return false;
    	else
    	{
    		for (int i = 0; i < number.length(); i++)
			{
				if (number.charAt(i) < '0' || number.charAt(i) > '9')
					return false;
			}
    		
    		return true;
    	}
    }
    
    /**
     * 百分比格式
     * 
     * @param number	要格式化的数字
     */
    public static String getPercentage(double number)
    {
        NumberFormat nf = NumberFormat.getPercentInstance();
        
        nf.setMaximumFractionDigits(2);
        nf.setMinimumFractionDigits(2);
        
        return nf.format(number);
    }

    /**
     * 获取金额格式
     * 
     * @param number	金额
     * @param digits	小数位个数
     */
    public static String getMoney(double number, int digits)
    {
        NumberFormat nf = NumberFormat.getInstance();

        //分组标志
        nf.setGroupingUsed(false);
        //小数位数
        nf.setMinimumFractionDigits(digits);
        nf.setMaximumFractionDigits(digits);
        
        return nf.format(number);
    }
    
    /**
     * 格式化double类型数字输出形式（去除小数位无用的0）
     * 
     * @param number
     * @return
     */
    public static String format(double number)
    {
    	if (number == (int) number)
    		return String.valueOf((int) number);
    	else
    		return String.valueOf(number);
    }
    
    //----------------------------------------------------------------------------
    //								精确的四则运算
    //----------------------------------------------------------------------------
	/**
	 * 提供精确的加法运算。
	 * 
	 * @param v1	被加数
	 * @param v2    加数
	 * 
	 * @return 两个参数的和
	 */
	public static double add(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.add(b2).doubleValue();
	}

	/**
	 * 提供精确的减法运算。
	 * 
	 * @param v1	被减数
	 * @param v2	减数
	 * 
	 * @return 两个参数的差
	 */
	public static double sub(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.subtract(b2).doubleValue();
	}

	/**
	 * 提供精确的乘法运算。
	 * 
	 * @param v1	被乘数
	 * @param v2	乘数
	 * 
	 * @return 两个参数的积
	 */
	public static double mul(double v1, double v2)
	{
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.multiply(b2).doubleValue();
	}

	/**
	 * 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入。
	 * 
	 * @param v1	被除数
	 * @param v2	除数
	 * 
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2)
	{
		return div(v1, v2, DEF_DIV_SCALE);
	}

	/**
	 * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
	 * 
	 * @param v1		被除数
	 * @param v2		除数
	 * @param scale		表示表示需要精确到小数点以后几位。
	 * 
	 * @return 两个参数的商
	 */
	public static double div(double v1, double v2, int scale)
	{
		if (scale < 0)
			throw new IllegalArgumentException("运算精度不能小于0");

		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));

		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

    //----------------------------------------------------------------------------
    //								随机数方法区域
    //----------------------------------------------------------------------------
	/**
	 * 产生浮点类型的随机数
	 * 
	 * @param from		起始值
	 * @param to		结束值
	 * @param scale		精度（小数位数）
	 * @return
	 */
	public static float getRandomFloat(float from, float to, int scale)
	{
        if(from >= to)
            return -1F;
        
        float value = -1F;
        Random random = new Random();

        if((int)(to - from) > 0)
            value = from + (float)random.nextInt((int)(to - from));	//整数随机数字
        else
            value = from;	//小数随机数字

        float temp;
        for(temp = to; temp >= to; temp = value + random.nextFloat());	//循环获取第1个小于to的随机小数
        
       	NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(scale);

        return Float.parseFloat(nf.format(temp));
	}

	/**
	 * 产生整型的随机数
	 * 
	 * @param from		起始值
	 * @param to		结束值
	 * @return
	 */
	public static int getRandomInt(int from, int to)
	{
		if (from >= to)
			return -1;
		else
		{
			int value = -1;
			Random random = new Random();
			
			// 整数随机数字
			value = from + random.nextInt((int)(to - from));
			
			return value;
		}
	}

	/**
	 * 测试方法
	 *
	 * @param args
	 */
	public static void main(String[] args)
	{
		logger.debug(NumberUtil.getMoney(123540111.132555, 3));
		logger.debug(NumberUtil.getMoney(1.100000000000000, 3));
//		logger.debug(NumberUtil.isNumber("111102243434324234230077423424"));
//		logger.debug(NumberUtil.isNumber("90999993333i9'"));
//		logger.debug(NumberUtil.mul(0.15, 1000));
	}
}
