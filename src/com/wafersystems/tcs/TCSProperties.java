package com.wafersystems.tcs;

import com.wafersystems.util.PropertiesFile;

/**
 * TCS（录播服务）配置参数获取类
 * <p>@Author baininghan
 * <p>@Date 2015年2月3日
 * <p>@version 1.0
 *
 */
public class TCSProperties {

	/** TCS 参数配置文件 */
	private static String MCU_PROPERTIES = "mcu.properties";
	private static PropertiesFile params = null;
	
	static{
		try {
			params = new PropertiesFile(MCU_PROPERTIES);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return String 获得TCS呼叫号码
	 */
	public static String tcsNumber(){
		return params.get("tcs_number");
	}
	/**
	 * @return String 获得TCS通信协议
	 */
	public static String tcsCallProtocol(){
		return params.get("tcs_call_protocol");
	}
	/**
	 * @return String 获得TCS用户名
	 */
	public static String tcsAdmin(){
		return params.get("tcs_admin");
	}
	/**
	 * @return String 获得TCS视频播放密码
	 */
	public static String tcsPassword(){
		return params.get("tcs_password");
	}
	/**
	 * @return boolean 是否继承元数据
	 */
	public static boolean tcsSetMetadata() {
		return params.get("tcs_SetMetadata").equals("true") ? true : false;
	}
	/**
	 * @return String TCS 带宽
	 */
	public static String tcsBitrate() {
		return params.get("tcs_bitrate");
	}
	/**
	 * @return boolean TCS 录播视频是否循环执行
	 */
	public static boolean isRecurring() {
		return params.get("isRecurring").equals("true") ? true : false;
	}
	
	public static void main(String[] args) {
		System.out.println(TCSProperties.tcsAdmin());
		System.out.println(TCSProperties.tcsCallProtocol());
		System.out.println(TCSProperties.tcsNumber());
		System.out.println(TCSProperties.tcsPassword());
		System.out.println(TCSProperties.tcsSetMetadata());
		System.out.println(TCSProperties.tcsBitrate());
		System.out.println(TCSProperties.isRecurring());
	}
}
