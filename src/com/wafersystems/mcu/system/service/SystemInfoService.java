package com.wafersystems.mcu.system.service;

import java.net.MalformedURLException;

import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.system.mode.SystemInfo;

public interface SystemInfoService {

	/**
	 * <p>@Desc <code>system.info</code>
	 * Returns the current status of the queried system.
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * @return
	 * @throws XmlRpcFault 
	 * @throws XmlRpcException 
	 * @throws MalformedURLException 
	 */
	public SystemInfo getSystemInfo() throws XmlRpcException, XmlRpcFault, MalformedURLException;
}
