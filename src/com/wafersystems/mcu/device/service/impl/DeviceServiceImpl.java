package com.wafersystems.mcu.device.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;

import org.apache.commons.collections.map.HashedMap;

import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.JsonValueProcessorImpl;
import com.wafersystems.mcu.XMLClientFactory;
import com.wafersystems.mcu.device.model.Device;
import com.wafersystems.mcu.device.model.DeviceRestartLog;
import com.wafersystems.mcu.device.service.DeviceService;

public class DeviceServiceImpl implements DeviceService {

	@Override
	public void addDeviceFeature(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeDeviceFeature(String key) {
		// TODO Auto-generated method stub

	}

	@Override
	public Device query() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Device queryDeviceNetwork() {
		try {
			XmlRpcClient client = XMLClientFactory.getMCUClient();
			List list = new LinkedList();
			
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
			JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
			
			JSONObject jo = JSONObject.fromObject(client.invoke("device.network.query", list), cfg);
			if(jo.containsKey("portA")){
				System.out.println(jo.get("portA"));
			}
			if(jo.containsKey("dns")){
				System.out.println(jo.get("dns"));
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Device queryDeviceHealth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DeviceRestartLog queryDeviceLog() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void restartDevice(Boolean shutdownOnly) {
		// TODO Auto-generated method stub

	}

}
