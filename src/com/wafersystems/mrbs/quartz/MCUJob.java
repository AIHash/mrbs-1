package com.wafersystems.mrbs.quartz;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.MCUParams;
import com.wafersystems.mcu.Packaging;
import com.wafersystems.mcu.conference.model.Conference;
import com.wafersystems.mcu.conference.service.ConferenceService;
import com.wafersystems.mcu.conference.service.impl.ConferenceServiceImpl;
import com.wafersystems.mcu.participant.model.Participant;
import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.icu.MeetingMobileDevicesDao;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;
import com.wafersystems.tcs.SocketCall;
import com.wafersystems.tcs.TCSProperties;
import com.wafersystems.tcs.vo.ConferenceVO;

/**
 * <p>@Desc 轮询本地库已审核通过的会议，进行会诊系统的相关业务操作
 * <p>@Author baininghan
 * <p>@Date 2014年12月2日
 * <p>@version 1.0
 *
 */
@Component("mcuJob")
public class MCUJob {

	private static Logger logger = LoggerFactory.getLogger(MCUJob.class);
	
	@Autowired
	private MeeAdmDbdService meetingService;
	@Autowired
	private SystemServiceLogService serviceLogService;
	@Autowired
	private MeetingMobileDevicesDao meetingMobileDevicesDao;
	
	public void doJob(){
		logger.info("--------------------------- Start polling MCU Devices -----------------------");
		exeJob();
		logger.info("--------------------------- End polling MCU Devices ------------------------");
	}
	
	/**
	 * <p>@Desc 具体的 MCU 轮询工作任务
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月2日
	 * <pre>
	 * （1）所有轮询的会议必须是已经审核的状态，默认遍历的Meeting表中的记录已经是审核过的会议
	 * （2）轮询未开始的会议
	 * （3）轮询会议的开始时间，如果开始时间与系统单签时间在5分钟内则发起MCU请求创建一个会议
	 * （4）默认设置<code>conference.create</code>调用方法的参数<code>startTime</code>为0，会议直接开始
	 * </pre>
	 */
	public void exeJob(){
		/*
		 * 会议开始前5分钟，创建mcu会议
		 */
		List<Meeting> meetings = meetingService.getNotEndMeeting();//查找未结束的会议
		// 遍历本地库中所有的会议，获取会议的开始时间，如果开始时间与当前时间相等或者小于5分钟，则开始创建这个会议
		for(Meeting meeting : meetings){
			if(meeting.getState() == GlobalConstent.MEETING_STATE_PENDING && meeting.getMeetingType().getValue() != 4){//会议状态必须是未开始 ：1，并且过滤离线会议
				long meetingStart = meeting.getStartTime().getTime();
				long startMinusCurrent = meetingStart - System.currentTimeMillis();
				long ncTime = MCUParams.getInstance().getTime() * 60 * 1000;
				
				if(startMinusCurrent <= ncTime && (meeting.getHaveCallMCU() == null || meeting.getHaveCallMCU()!=MCUParams.HaveCallMCU)){//会议开始前5分钟通知MCU，并且在MCU上还没有创建过会议
					try {
						callMCU(meeting);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	/**
	 * <p>@Desc 调度MCU，会议开始前（默认5分钟）在mcu上创建一个会议
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月15日
	 * @param meeting
	 * @throws XmlRpcFault 
	 * @throws XmlRpcException 
	 * @throws MalformedURLException 
	 */
	public void callMCU(Meeting meeting) throws MalformedURLException, XmlRpcException, XmlRpcFault{
		ConferenceService service = new ConferenceServiceImpl();
		Conference conference = Packaging.packagConferenceForCreate(meeting);
		logger.info("--------------------------- Start conference.create with MCU ------------------------");
		String conferenceGUID = service.createConference(conference);//创建一个会议，返回会议标示ID
		meeting.setConferenceGUID(conferenceGUID);
		meeting.setHaveCallMCU(MCUParams.HaveCallMCU);
		logger.info("--------------------------- End conference.create with MCU for conferenceGUID : "+ conferenceGUID+"----------");
		
		List<Participant> participants = new ArrayList<Participant>();// 邀请/呼叫参会人
		List<String> list = new ArrayList<String>();// 用于保存参与人的设备网真号
		
		// 根据会议类型获取不同的网真号码
		int meetingKind = meeting.getMeetingKind();
		if(GlobalConstent.APPLICATION_TYPE_ICUVISIT == meetingKind){// 远程探视
			MeetingMobileDevices meetingMobileDevices = meetingMobileDevicesDao.getMobileDevicesByMeetingId(meeting.getId());
			String meetingRoomIcuDeviceNO = meeting.getMeetingRoomId().getIcuDeviceNO();
			String icuDeviceNo = meetingMobileDevices.getDevicesNo();
			list.add(MCUParams.getInstance().getcallProtocol() + ":" + meetingRoomIcuDeviceNO);
			list.add(MCUParams.getInstance().getcallProtocol() + ":" + icuDeviceNo);
		}else if(GlobalConstent.APPLICATION_TYPE_ICUMONITOR == meetingKind){ //重症监护
			MeetingMobileDevices meetingMobileDevices = meetingMobileDevicesDao.getMobileDevicesByMeetingId(meeting.getId());
			String icuDeviceNo = meetingMobileDevices.getDevicesNo();
			list.add(MCUParams.getInstance().getcallProtocol() + ":" + icuDeviceNo);
			Set<MeetingMember> set = meeting.getMembers();
			for (MeetingMember member : set) {// 4. 获取当前会议的参会人 address
				String address = member.getMember().getVideoPoint();// 共同体或者专家关联的设备IP地址
				address = MCUParams.getInstance().getcallProtocol() + ":" + address;
				if (!list.contains(address)) {// 多个专家可能使用视频终端IP相同，因此在此处去重
					list.add(address);
				}
			}
		}else{// 病例讨论、视频会议、重症监护
			Set<MeetingMember> set = meeting.getMembers();
			for (MeetingMember member : set) {// 4. 获取当前会议的参会人 address
				String address = member.getMember().getVideoPoint();// 共同体或者专家关联的设备IP地址
				address = MCUParams.getInstance().getcallProtocol() + ":" + address;
				if (!list.contains(address)) {// 多个专家可能使用视频终端IP相同，因此在此处去重
					list.add(address);
				}
			}
		}
		
		for (String address : list) {
			Participant participant = Packaging.initParticipant();
			participant.setAddress(MCUParams.getInstance().getcallProtocol() + ":" + address);
			participants.add(participant);
		}
		/** 邀请所有参会人 */
		service.inviteParticipants(conferenceGUID, participants);
		
		/** 开启 TCS 录播服务 */
		if(MCUParams.getInstance().enableTCS()){
			String tcsURL = enableTCS(meeting);
			meeting.setTcsURL(tcsURL);/** 将录播视频播放的URL保存进meeting */
		}
		
		meetingService.updateMCU(meeting.getConferenceGUID(), meeting.getHaveCallMCU(), meeting.getTcsURL(), meeting.getId());
	}
	
	/**
	 * 呼叫参会人设备
	 * @author baininghan
	 */
	@Deprecated
	public void callParticipants() {
		List<Meeting> meetings = meetingService.getMeetingForCallParticipants();// 1. 查询所有meeting.conferenceGUID 不为空的对象，同时会议状态为未开始
		for (Meeting meeting : meetings) {
			if(new Date().getTime() >= meeting.getStartTime().getTime()){// 2. 已经开始的mcu会议才可以呼叫参会人
				String conferenceGUID = meeting.getConferenceGUID();// 3. 获取当前会议的 conferenceGUID

				Set<MeetingMember> set = meeting.getMembers();
				List<String> list = new ArrayList<String>();// 用于保存参与人的设备地址
				for (MeetingMember member : set) {// 4. 获取当前会议的参会人 address
					String address = member.getMember().getVideoPoint();// 共同体或者专家关联的设备IP地址
					address = MCUParams.getInstance().getcallProtocol() + ":" + address;
					if (!list.contains(address)) {// 多个专家可能使用视频终端IP相同，因此在此处去重
						list.add(address);
					}
				}

				List<Participant> participants = new ArrayList<Participant>();// 邀请/呼叫参会人
				for (String address : list) {
					Participant participant = Packaging.initParticipant();
					participant.setAddress(MCUParams.getInstance().getcallProtocol() + ":" + address);
					participants.add(participant);
				}
				logger.info("-------------- Start conference.invite with conferenceGUID :"+ conferenceGUID + " ------------------------");
				ConferenceService service = new ConferenceServiceImpl();
				String tcsURL="";
				try {
					service.inviteParticipants(conferenceGUID, participants);
					/** 开启 TCS 录播服务 */
					if(MCUParams.getInstance().enableTCS()){
						tcsURL= enableTCS(meeting);
						meeting.setTcsURL(tcsURL);/** 将录播视频播放的URL保存进meeting */
					}
				} catch (Exception e) {
					e.printStackTrace();
					meeting.setHaveCallMCU(MCUParams.HaveCallParticipant);
				} finally{
					meeting.setHaveCallMCU(MCUParams.HaveCallParticipant);
					meeting.setTcsURL(tcsURL);/** 将录播视频播放的URL保存进meeting */
					meetingService.updateMeeting(meeting);// 将 conferenceGUID 保存进 本地库
					logger.info("-------------- End conference.invite with conferenceGUID :"+ conferenceGUID + " ------------------------");
				}
				
			}
		}
	}
	
	/**
	 * TCS（视频录播）服务业务方法
	 * <p>@Author baininghan
	 * <p>@Date 2015年2月3日
	 * @param String 视频播放的链接URL字符串
	 */
	private static String enableTCS(Meeting meeting){
		String tcsURL = "";
		try {
			logger.info("-------------- Start conference.invite with meeting :"+ meeting.getId() + " ------------------------");
			SocketCall socketCall = new SocketCall();
			
			/** 预创建一个会议 */
			long duration = meeting.getEndTime().getTime() - new Date().getTime();
			Map<String, Object> conferenceIDMaps = socketCall.requestConferenceID(
											TCSProperties.tcsAdmin(),/** 视频所有者 */
											TCSProperties.tcsPassword(), /** 视频播放密码，如果为空，则视频打开不需要密码 */
											new Date(), 
											new Long(duration).intValue(), 
											"TCS" + meeting.getId(), 
											"", 
											TCSProperties.isRecurring());/** 视频录播是否循环执行 */
			String conferenceID = (String)conferenceIDMaps.get("RequestConferenceIDResult");
			
			/** 根据预创建的会议ID拨号 8012 加入录播服务 */
			socketCall.dial(meeting.getId() + "", 
											TCSProperties.tcsBitrate(), /** 视频录播带宽 */
											conferenceID, 
											TCSProperties.tcsNumber(), /** 视频 E.163 alias */
											TCSProperties.tcsCallProtocol(), /** 视频录播通信协议 */
											TCSProperties.tcsSetMetadata(), /** inherit conference metadata from the alias */
											"");
			
			/** 根据预创建的会议ID获得录播的播放URL字符串 */
			Map<String, Object> tcsURLMaps = socketCall.getConference(conferenceID, TCSProperties.tcsAdmin());
			ConferenceVO cvo = (ConferenceVO)tcsURLMaps.get("ConferenceVO");
			
			tcsURL =  cvo.getURL();
		} catch (Exception e) {
			logger.error("------------- 视频录播服务开启过程失败 ------------------------ " + e.getMessage());
		} finally{
			logger.info("-------------- End conference.invite with meeting :"+ meeting.getId());
		}
		
		return tcsURL;
	}
}
