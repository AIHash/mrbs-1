package com.wafersystems.mcu;

import java.io.File;
import java.net.URLDecoder;

import org.apache.log4j.Logger;

import com.wafersystems.util.PropertiesFile;

/**
 * <p>@Desc MCU some related parameters
 * <p>@see <code>mcu.properties</code>
 * <p>@Author baininghan
 * <p>@Date 2014年12月25日
 * <p>@version 1.0
 *
 */
public class MCUParams {

	/**
	 * <pre>
	 * 已经呼叫过参会人
	 * HaveCallParticipant = 2
	 */
	public final static int HaveCallParticipant = 2;
	/**
	 * <pre>
	 * 定义会议是否已经经过MCU定时扫描
	 * HaveCallMCU=1：已扫描，下次扫描时自动忽略
	 */
	public final static int HaveCallMCU = 1;
	/**
	 * <pre>
	 * 定义会议是否已经经过MCU定时扫描
	 * NOTHaveCallMCU=0：未扫描
	 */
	public final static int NOTHaveCallMCU = 0 ;
	
	private static Logger logger = Logger.getLogger(MCUParams.class);
	/**
	 * MCU parameter file path
	 */
	private static String MCU_PROPERTIES = "mcu.properties";
	private static PropertiesFile params = null;
	private String runtimeRootPath = null;
	private static MCUParams mcu = null;
	
	/**
	 * 单例
	 * 
	 * @return MCUParams的实例
	 */
	public static MCUParams getInstance() {
		if (mcu == null) {
			mcu = new MCUParams();
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		}
		return mcu;
	}
	
	/**
	 * 获取系统运行时的根目录（支持目录和jar两种运行方式）
	 * 
	 * @return
	 */
	@Deprecated
	public String getRuntimeRootPath() {
		if (null == runtimeRootPath) {
			runtimeRootPath = MCUParams.class.getProtectionDomain()
					.getCodeSource().getLocation().getPath();

			// 解码特殊字符
			try {
				runtimeRootPath = URLDecoder.decode(runtimeRootPath, "utf-8");
			} catch (Exception e) {
				logger.error("String decode error:", e);
			}

			if (runtimeRootPath.endsWith(".jar")) {
				// jar包方式
				File file = new File(runtimeRootPath);
				runtimeRootPath = file.getParent() + "/";
			}
		}

		return runtimeRootPath;
	}
	
	
	/**
	 * <p>@Desc Obtain the MCU server user name
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月25日
	 * @return
	 */
	public String getAuthenticationUser(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return params.get("authenticationUser");
	}
	
	/**
	 * <p>@Desc Access to MCU service's password
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月25日
	 * @return
	 */
	public String getAuthenticationPassword(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return params.get("authenticationPassword");
	}
	
	/**
	 * <p>@Desc TelePresence server IP address
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月25日
	 * @return
	 */
	public String getMCUIP(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		return params.get("authenticationMCUIP");
	}
	
	/**
	 * <p>@Desc 根据会议的开始时间，MCU启动设备的提前时间量
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月29日
	 * @return
	 */
	public long getTime(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		//如果properties文件中没有取到值，则默认取 5 分钟
		String start_call_time = "5";
		String key = params.get("start_call_time");
		if(!"".equals(key) && key != null && !"null".equals(key)){
			start_call_time = key;
		}
		return new Long(start_call_time).longValue();
	}
	
	/**
	 * 获取网真呼叫通信协议，默认为h323
	 * @author baininghan
	 */
	public String getcallProtocol(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		//如果properties文件中没有取到值，则默认取 5 分钟
		String callProtocol = "h323";
		String key = params.get("mcu_call_protocol");
		if(!"".equals(key) && key != null && !"null".equals(key)){
			callProtocol = key;
		}
		return callProtocol;
	}
	
	/**
	 * 获取 TCS 录播服务的网真呼叫号码
	 * @author baininghan
	 */
	public String getTCSNum(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		//如果properties文件中没有取到值，则默认取 5 分钟
		String TCSNum = "7012";
		String key = params.get("tcs_number");
		if(!"".equals(key) && key != null && !"null".equals(key)){
			TCSNum = key;
		}
		return TCSNum;
	}
	
	/**
	 * 开启 TCS 录播服务，true：开启；false：关闭
	 * @author baininghan
	 */
	public boolean enableTCS(){
		if(params == null){
			try {
				params = new PropertiesFile(MCU_PROPERTIES);
			} catch (Exception e) {
				logger.error(e);
			}
		}
		String key = params.get("enable_tcs");
		if(!"".equals(key) && key != null && !"null".equals(key) && "true".equals(key)){
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println(MCUParams.getInstance().getTCSNum() + ":"+MCUParams.getInstance().enableTCS());
		if(MCUParams.getInstance().enableTCS()){
			System.out.println("----------------");
		}
	}
}
