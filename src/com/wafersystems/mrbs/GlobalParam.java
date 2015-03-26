package com.wafersystems.mrbs;

import java.util.HashMap;
import java.util.Map;

/**
 * MRBS 系统全局常量
 * @author Moon
 * 本类中的属性全部小写，并且与数据库中的名称匹配（因为从数据库中加载这些属性使用反射）
 * 例如：
 * 数据库			本类中的属性
 * TMS-ADDRESS		tms_address
 * LANGUAGE			language
 * 由于页面中使用英文状态下的"_"作为分隔标志，所以库中存储的key不能用"_"
 *
 */
public class GlobalParam {

	public static boolean LICENSE_STATE = false;

	//服务日志的类型
	public static Map<String, String> serviceDesc = new HashMap<String,String>();

	//read from db when init

	public static String language = "zh_CN";

	//提前通知的时间
	public static int before_minute = 5;

	//外网可访问MRBS的ip或域名
	public static String outter_access_ip = "http://192.168.0.230:8080/";

	//TMS URL地址
	public static String tms_address = "http://192.168.0.96/tms";

	//人民医院寻院问药
	//public static String rmyy_url_1 = "http://www.phbjmu.edu.cn/mass";

	//人民医院院务邮箱
	//public static String rmyy_url_2 = "http://www.pkuph.cn:81/";

	//同步科室和医生数据库ip
	public static String sync_data_inner_ip = "172.16.0.214";

	//同步科室和医生数据库名
	public static String sync_data_db_name = "chisdb_bjrm_mz";

	//同步科室和医生数据库用户名
	public static String sync_data_db_user = "llh";

	//同步科室和医生数据库密码
	public static String sync_data_db_pwd = "";

	// 海纳内网链接
	public static String haina_url = "http://172.21.52.34";
	
	// 海纳外网链接
	public static String haina_url_outnet = "http://211.160.74.130";
	
	// 发送短信数据库ip
	public static String sms_db_ip = "10.7.131.244";

	// 发送短信数据库名
	public static String sms_db_name = "mas";

	// 发送短信数据库用户名
	public static String sms_db_user = "4040";

	// 发送短信数据库密码
	public static String sms_db_pwd = "4040";

	// 接口编码
	public static String sms_api_code = "telecon";
}
