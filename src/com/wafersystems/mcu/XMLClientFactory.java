package com.wafersystems.mcu;

import java.net.MalformedURLException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import redstone.xmlrpc.XmlRpcClient;

public class XMLClientFactory {

	public static XmlRpcClient getMCUClient() throws MalformedURLException{
		Lock lock = new ReentrantLock();
		lock.lock();
		try {
			XmlRpcClient client = new XmlRpcClient("http://" + MCUParams.getInstance().getMCUIP() +"/RPC2", true);
			return client;
		} finally {
			lock.unlock();
		}
	}
	
}
