package com.wafersystems.mcu;

import java.net.MalformedURLException;
import java.util.Date;

import redstone.xmlrpc.XmlRpcException;
import redstone.xmlrpc.XmlRpcFault;

import com.wafersystems.mcu.conference.model.Conference;
import com.wafersystems.mcu.conference.service.ConferenceService;
import com.wafersystems.mcu.conference.service.impl.ConferenceServiceImpl;
import com.wafersystems.mcu.participant.model.Participant;
import com.wafersystems.mrbs.vo.meeting.Meeting;

/**
 * <p>@Desc 将本地库中的对象打包为XML-PRC对象
 * <p>@Author baininghan
 * <p>@Date 2014年12月3日
 * <p>@version 1.0
 *
 */
public class Packaging {

	/**
	 * <p>@Desc 根据meeting对象获取创建的Conference会议
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月3日
	 * @return
	 */
	public static Conference packagConferenceForCreate(Meeting meeting){
		
		Conference conference = new Conference();
		
		/* 必填项，其他可选 */
		conference.setConferenceName(meeting.getId().toString());
		
		long meetingEnd = meeting.getEndTime().getTime();
		long duration = meetingEnd - new Date().getTime();// mcu提前5分钟创建，则会议结束时间要与meeting一致，结束时间延迟5分钟
		
		/* 可选项 */
		conference.setPersistent(Boolean.FALSE);//定义所有参会人连接设备断开30秒之后，或者duration持续时间到期之后，会议将在MCU上被取消
		if(!conference.getPersistent()){conference.setDuration((new Long(duration).intValue())/1000);}//会议的持续时间秒数，persistent = false 才有效，（此处持续时间即是meeting的持续时间）
		
		conference.setLocked(Boolean.FALSE);//定义会议被锁定，会议开始之后，参会人不可以手动拨号加入会议，只能会议主动邀请
		if(conference.getLocked()){conference.setLockDuration(new Integer(1000));}//会议锁定的持续时间，当locked=true时有效，（此处锁定时间即是meeting的持续时间）
		
		conference.setUseWarning(Boolean.TRUE);//会议快结束时，是否进行提示 “This conference is about to end”

		//以下参数省略，可根据实际情况酌情配置
		
		/*
		 * 会议的数字编号，如果 guestNumericId 配置，将替代 numericId 用来访问这个会议
		 */
		conference.setNumericID(meeting.getId().toString());
		conference.setRegisterWithGatekeeper(Boolean.TRUE);
		conference.setRegisterWithSIPRegistrar(Boolean.FALSE);
		
/*		conference.setTsURI("http://mytps:80");
		conference.setH239ContributionEnabled(Boolean.TRUE);//定义会议内容是否允许共享
		
		conference.setUseLobbyScreen(Boolean.TRUE);//定义会议是否在前台/大厅显示
		conference.setLobbyMessage("Lobby Message");//前厅显示消息
		
		conference.setAudioPortLimit(new Integer(100));//这个会议限制的音频端口数量上限
		conference.setVideoPortLimit(new Integer(100));//这个会议限制的视频端口数量上限
		
		conference.setAutomaticGainControl(Boolean.FALSE);//定义是否启用了自动增益控制，否则采取默认设置
		conference.setEncryptionRequired(Boolean.FALSE);//定义是否采用加密控制，默认 FALSE
		
		
		conference.setPin("");//依赖NumericID，如果NumericID没有，设置此项会报错
		
		conference.setEncryptionRequired(Boolean.FALSE);//定义是否采用加密控制，默认 FALSE
		
		// and other setter
*/		
		return conference;
	}
	
	/**
	 * <p>@Desc 根据meeting对象获取修改的Conference会议
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月26日
	 * @param meeting
	 * @return
	 */
	public static void packagConferenceForSet(Meeting meeting){
		// 如果MCU上还没有创建Conference，则返回
		String conferenceGUID = meeting.getConferenceGUID();
		if(conferenceGUID == null || "".equals(conferenceGUID) || "null".equals(conferenceGUID))return;
		
		ConferenceService service = new ConferenceServiceImpl();
		try {
			Conference conference = setConference(meeting);
//			conference.setDuration(-1);// 清除持续时间，会议始终存在
			service.setConference(conference);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (XmlRpcException e) {
			e.printStackTrace();
		} catch (XmlRpcFault e) {
			e.printStackTrace();
		}
	}
	
	private static Conference setConference(Meeting meeting){
		
		Conference conference = new Conference();
		
		/* 必填项，其他可选 */
		conference.setConferenceGUID(meeting.getConferenceGUID());
//		conference.setConferenceName(meeting.getTitle());
		
		long meetingEnd = meeting.getEndTime().getTime();
		long duration = meetingEnd - new Date().getTime();
		
		/* 可选项 */
		conference.setPersistent(Boolean.FALSE);//定义所有参会人连接设备断开30秒之后，或者duration持续时间到期之后，会议将在MCU上被取消
		if(!conference.getPersistent()){conference.setDuration((new Long(duration).intValue())/1000);}//会议的持续时间秒数，persistent = false 才有效，（此处持续时间即是meeting的持续时间）
//		if(!conference.getPersistent()){conference.setDuration(-1);}
		
		conference.setLocked(Boolean.FALSE);//定义会议被锁定，会议开始之后，参会人不可以手动拨号加入会议，只能会议主动邀请
		if(conference.getLocked()){conference.setLockDuration(new Integer(1000));}//会议锁定的持续时间，当locked=true时有效，（此处锁定时间即是meeting的持续时间）
		
		conference.setUseWarning(Boolean.TRUE);//会议快结束时，是否进行提示 “This conference is about to end”

		//以下参数省略，可根据实际情况酌情配置
		
		/*
		 * 会议的数字编号，如果 guestNumericId 配置，将替代 numericId 用来访问这个会议
		 */
		//conference.setNumericID(meeting.getId().toString());
		//conference.setRegisterWithGatekeeper(Boolean.TRUE);
		//conference.setRegisterWithSIPRegistrar(Boolean.FALSE);
		
/*		conference.setTsURI("http://mytps:80");
		conference.setH239ContributionEnabled(Boolean.TRUE);//定义会议内容是否允许共享
		conference.setUseLobbyScreen(Boolean.TRUE);//定义会议是否在前台/大厅显示
		conference.setLobbyMessage("Lobby Message");//前厅显示消息
		conference.setAudioPortLimit(new Integer(100));//这个会议限制的音频端口数量上限
		conference.setVideoPortLimit(new Integer(100));//这个会议限制的视频端口数量上限
		conference.setAutomaticGainControl(Boolean.FALSE);//定义是否启用了自动增益控制，否则采取默认设置
		conference.setEncryptionRequired(Boolean.FALSE);//定义是否采用加密控制，默认 FALSE
		conference.setPin("");//依赖NumericID，如果NumericID没有，设置此项会报错
		conference.setEncryptionRequired(Boolean.FALSE);//定义是否采用加密控制，默认 FALSE
		
		// and other setter
*/		
		return conference;
	}

	/**
	 * <p>@Desc 初始化Participant的一些属性，address除外需要额外进行设置
	 * <p>@Author baininghan
	 * <p>@Date 2014年12月15日
	 * @return
	 */
	public static Participant initParticipant(){
		Participant participant = new Participant();
		
		//participant.setType("t3");//指定终端设备的类型
		//participant.setMaster(Boolean.FALSE);
		//participant.setOneTableIndex(new Integer(1));
		
		//participant.setMaxBitRate(new Integer(100));
		participant.setRecordingDevice(Boolean.FALSE);//是否是一个录像设备
		/*
		participant.setDtmf("");
		participant.setAudioContentIndex(new Integer(0));
		participant.setContentIndex(new Integer(0));//依赖于AudioContentIndex
		participant.setCamerasCrossed(Boolean.FALSE);//摄像头是交叉的
		*/
		participant.setTxAspectRatio("only16to9");//only16to9  only4to3
		
		//连接断掉之后是否重新连接，true：连接断掉 5秒之后第一次连接，然后分别是15s，30s，60s，120s，尝试5次
		//默认FALSE，不重新连接
		participant.setAutoReconnect(Boolean.TRUE);
		//participant.setAlwaysReconnect(Boolean.TRUE);
		
		/*
		 * true：当至少有一个参会人加入会议之后，会议才开始  status：Waiting for other calls
		 * false：会议创建之后立马开始 status：Attempting to re-establish call
		 */
		participant.setDeferConnect(Boolean.FALSE);
		//participant.setAutoDisconnect(Boolean.FALSE);//定义当其他参会人断开连接之后，会议是否自动挂断
		
		//单屏显示器的显示方式，可选： single, activePresence, equal, or prominent.
		participant.setDefaultLayoutSingleScreen("activePresence");
		//多屏显示器的显示方式，可选：One of single or activePresence.
		participant.setDefaultLayoutMultiScreen("activePresence");
		participant.setForceDefaultLayout(Boolean.TRUE);//强制使用用户默认的显示方式
		participant.setAutomaticGainControl(Boolean.TRUE);//开启自动增益性控制
		participant.setAllowStarSixMuting(Boolean.TRUE);//定义是否启用 *6 组合按键 来禁言/解禁言
		
		
		return participant;
	}
	
}
