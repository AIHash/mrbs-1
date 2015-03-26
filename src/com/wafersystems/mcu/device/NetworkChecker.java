package com.wafersystems.mcu.device;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.wafersystems.mcu.participant.model.Participant;
import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.SystemServiceLog;

public class NetworkChecker {

	private static Logger logger = LoggerFactory.getLogger(NetworkChecker.class);

	@Autowired
	private SystemServiceLogService serviceLogService;
	
	/**
	 * <p>检查主机端口是否打开
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月29日
	 * @param host
	 * @param port
	 * @return
	 */
	public static boolean isOpen(String host, int port) {
		try {
			InetAddress address = InetAddress.getByName(host);
			Socket socket = new Socket(address, port);
			socket.setSoTimeout(5000);
			socket.close();
			return true;
		} catch (IOException e) {
			logger.error("Check socket connectivity", e);
			return false;
		}
	}

	/**
	 * <p>检查参数host IP 是否可以pin通
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月29日
	 * @param host
	 * @return
	 */
	public static boolean ping(String host) {
		boolean result = false;
		try {
			// if(System.currentTimeMillis()%3==0) return true;
			Process process = Runtime.getRuntime().exec("ping " + host);
			InputStreamReader r = new InputStreamReader(process.getInputStream(), "GBK");
			LineNumberReader returnData = new LineNumberReader(r);
			
			String returnMsg = "";
			String line = "";
			while ((line = returnData.readLine()) != null) {
				returnMsg += line;
			}
			
			String numStr = returnMsg.substring(returnMsg.indexOf("(") + 1,returnMsg.indexOf("%"));
			if (Integer.parseInt(numStr) >= 50) {
				logger.warn("Network connection to " + host + " can not flow freely, " + numStr + "% loss");
			} else if (returnMsg.contains("TTL")) {
				result = true;
				logger.debug("Network connection to " + host + " is ok");
			} else {
				logger.warn("Network connection to " + host + " is unreachable");
			}
		} catch (Exception e) {
		}
		return result;
	}
	
	public boolean checkNetWorkParticipant(Participant participant){
		String ip = participant.getAddress();
		if(!ping(ip)){
			SystemServiceLog log = new SystemServiceLog();
			log.setContent("Network connection to Participant's IP " + ip + " is unreachable");
			log.setObjectId(GlobalConstent.SERVICE_MCU_STATUS_TAG);
			log.setCreateTime(new Date());
			log.setResult(GlobalConstent.SYSTEM_LOG_FAILED);
			log.setName(MessageTag.getMessage("service.mcu.status"));
			serviceLogService.saveSystemServiceLog(log);
			
			return false;
		}else{
			return true;
		}
	}
	
}
