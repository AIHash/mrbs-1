package com.wafersystems.mrbs.quartz;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.notice.factory.NoticeFactory;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.util.DateUtil;

@Component("statusJob")
public class MeetingStausJob {
	private static Logger logger = LoggerFactory.getLogger(MeetingStausJob.class);

	private MeeAdmDbdService meetingService;
	private SystemServiceLogService serviceLogService;
	private NoticeFactory noticeFactory;
	private Set<Integer> beforeCache = new HashSet<Integer>();

	public void doJob() {
		execJob();
	}

	private void execJob() {
		List<Meeting> meetings = meetingService.getNotEndMeeting();
		for (Meeting meeting : meetings) {
			// 开始时间
			long meetingStart = meeting.getStartTime().getTime();
			// 结束时间
			long meetingEnd = meeting.getEndTime().getTime();
			// 当前时间
			long currentTime = System.currentTimeMillis();

			long startMinusCurrent = meetingStart - currentTime;

			long endMinusCurrent = meetingEnd - currentTime;

			if(endMinusCurrent < 0)
				end(meeting);
			else if (startMinusCurrent > 0
					&& startMinusCurrent <= GlobalParam.before_minute * 60 * 1000
					&& !beforeCache.contains(meeting.getId()))
				notStart(meeting);
			else if (startMinusCurrent <= 0  && meeting.getState() != GlobalConstent.MEETING_STATE_START)
				start(meeting);
		}
	}

	// 处理未开始的会议
	private void notStart(Meeting meeting) {
		String meetingType = "";
		if(meeting.getMeetingKind() != null ){
			if(meeting.getMeetingKind().equals((short)1)){
				meetingType = "病历讨论";
			}else if(meeting.getMeetingKind().equals((short)2)){
				meetingType = "视频讲座";
			}else if(meeting.getMeetingKind().equals((short)3)){
				meetingType = "重症监护";
			}else{
				meetingType = "远程探视";
			}
			
		}
		UnifiedNotice notice = null;
		MtTelecon mtTelecon = null;
		for (MeetingMember member : meeting.getMembers()) {
			if(member.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)){
				notice = new UnifiedNotice();
				String content = MessageTag.getMessage("service.notice.content", 
							meeting.getTitle() + "," + meetingType + "," + DateUtil.getTimeStr(meeting.getStartTime()));

				notice.setTitle(MessageTag.getMessage("service.notice.title", meetingType));
				notice.setMessage(content);
				if(StringUtils.isNotBlank(member.getMember().getMail())){
					notice.setReceiver(member.getMember().getMail());
					noticeFactory.addMailNotice(notice);
				}
				if(StringUtils.isNotBlank(member.getMember().getMobile())){
					mtTelecon = new MtTelecon();
					mtTelecon.setSmId("0");
					mtTelecon.setSrcId("0");
					mtTelecon.setMobile(member.getMember().getMobile());
					mtTelecon.setContent(content);
					mtTelecon.setSendTime(null);
					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_012);
					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
					mtTelecon.setResendTimes(0);
					noticeFactory.addMtTelecon(mtTelecon);	
				}
			}
		}
		beforeCache.add(meeting.getId());
	}

	// 开始的会议
	public void start(Meeting meeting) {
		logger.debug( meeting.getTitle() + " meeting start ");
		meeting.setState(GlobalConstent.MEETING_STATE_START);
		meetingService.updateMeeting(meeting);
		addServiceLog(meeting.getTitle(), MessageTag.getMessage("service.notice.start"));
	}

	// 处理结束的会议
	private void end(Meeting meeting) {
		logger.debug( meeting.getTitle() + " meeting end ");
		meeting.setState(GlobalConstent.MEETING_STATE_END);
		meetingService.updateMeeting(meeting);
		if(beforeCache.contains(meeting.getId()))
			beforeCache.remove(meeting.getId());

		addServiceLog(meeting.getTitle(), MessageTag.getMessage("service.notice.end"));
	}

	// 增加服务日志
	private void addServiceLog(String title, String status) {
		SystemServiceLog log = new SystemServiceLog();
		log.setContent(MessageTag.getMessage("service.notice.status.change", title) + status);
		log.setObjectId(GlobalConstent.SERVICE_MEETING_STATUS_TAG);
		log.setCreateTime(new Date());
		log.setResult(GlobalConstent.SYSTEM_LOG_SUCCESS);
		log.setName(MessageTag.getMessage("service.status.desc"));
		serviceLogService.saveSystemServiceLog(log);
	}

	@Resource
	public void setMeetingService(MeeAdmDbdService meetingService) {
		this.meetingService = meetingService;
	}

	@Resource
	public void setServiceLogService(SystemServiceLogService serviceLogService) {
		this.serviceLogService = serviceLogService;
	}

	@Resource
	public void setNoticeFactory(NoticeFactory noticeFactory) {
		this.noticeFactory = noticeFactory;
	}

	@PostConstruct
	public void init() {
		logger.debug("会议状态服务开始运行");
		GlobalParam.serviceDesc.put(GlobalConstent.SERVICE_MEETING_STATUS_TAG,
				MessageTag.getMessage("service.status.desc"));
	}

	@PreDestroy
	public void destroy() {
		logger.debug("会议状态服务停止运行");
	}
}
