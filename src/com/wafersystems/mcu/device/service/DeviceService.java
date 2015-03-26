package com.wafersystems.mcu.device.service;

import com.wafersystems.mcu.device.model.Device;
import com.wafersystems.mcu.device.model.DeviceRestartLog;

public interface DeviceService {

	/**
	 * <p>@Desc <code>device.feature.add</code>
	 * 给网真服务器添加许可证或者功能。需要从思科或者其他代理经销商那里获取注册码
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param key 使用这个唯一注册码去增加会议容量或者添加其他可选的功能
	 */
	public void addDeviceFeature(String key);
	
	/**
	 * <p>@Desc <code>device.feature.remove</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param key
	 */
	public void removeDeviceFeature(String key);
	
	/**
	 * <p>@Desc <code></code>
	 * 返回高水平状态的信息设备
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @return
	 */
	public Device query();
	
	/**
	 * <p>@Desc <code>device.network.query</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @return
	 */
	public Device queryDeviceNetwork();
	/**
	 * <p>@Desc <code>device.health.query</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @return
	 */
	public Device queryDeviceHealth();
	
	/**
	 * <p>@Desc <code>device.restartlog.query</code>
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @return
	 */
	public DeviceRestartLog queryDeviceLog();
	
	/**
	 * <p>@Desc <code>device.restart</code>
	 * true：关闭设备
	 * false（默认）：重启设备
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @param shutdownOnly
	 */
	public void restartDevice(Boolean shutdownOnly);
}
