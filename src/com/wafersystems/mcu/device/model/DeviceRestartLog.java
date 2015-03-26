package com.wafersystems.mcu.device.model;

import java.util.Date;

/**
 * <p>@Desc 设备重启的日志信息
 * <p>@Author baininghan
 * <p>@Date 2014年12月2日
 * <p>@version 1.0
 *
 */
public class DeviceRestartLog {

	private Date time;
	/**
	 * 可选值参考内部类 Reason
	 */
	private String reason;
	
	/**
	 * @return the time
	 */
	public Date getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(Date time) {
		this.time = time;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	
	static class Reason{
		public static String User_requested_shutdown = "User requested shutdown";
		public static String User_requested_reboot_from_web_interface = "User requested reboot from web interface";
		public static String User_requested_upgrade = "User requested upgrade";
		public static String User_requested_reboot_from_console = "User requested reboot from console";
		public static String User_requested_reboot_from_API = "User requested reboot from API";
		public static String User_requested_reboot_from_FTP = "User requested reboot from FTP";
		public static String User_requested_shutdown_from_supervisor = "User requested shutdown from supervisor";
		public static String User_requested_reboot_from_supervisor = "User requested reboot from supervisor";
		public static String User_reset_configuration = "User reset configuration";
		public static String Cold_boot = "Cold boot";
		public static String unknown = "unknown";
	}
}
