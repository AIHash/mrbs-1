package com.wafersystems.mcu.system.service.impl;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import net.sf.ezmorph.object.DateMorpher;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.JSONUtils;
import redstone.xmlrpc.XmlRpcClient;
import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.JsonValueProcessorImpl;
import com.wafersystems.mcu.XMLClientFactory;
import com.wafersystems.mcu.system.mode.SystemInfo;
import com.wafersystems.mcu.system.service.SystemInfoService;

public class SystemInfoServiceImpl implements SystemInfoService {

	@Override
	public SystemInfo getSystemInfo() throws XmlRpcException, XmlRpcFault, MalformedURLException {
		XmlRpcClient client = XMLClientFactory.getMCUClient();
		
		JsonConfig cfg=new JsonConfig();
		cfg.registerJsonValueProcessor(Date.class, new JsonValueProcessorImpl());
		JSONUtils.getMorpherRegistry().registerMorpher(new DateMorpher(new String[] {"yyyy-MM-dd HH:mm:ss"}) );
		List list = new LinkedList();
		JSONObject jo = JSONObject.fromObject(client.invoke("system.info", list), cfg);
		
		return (SystemInfo)JSONObject.toBean(jo);
	}

}
