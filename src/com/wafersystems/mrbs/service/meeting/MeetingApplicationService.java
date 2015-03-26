package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface MeetingApplicationService {

	public void saveMeetingApplication(MeetingApplication vo);

	public void updateMeetingApplication(MeetingApplication vo);

	public void delMeetingApplication(MeetingApplication vo);
	
	public MeetingApplication getMeetingApplicationById(int id);
	
	public VideoMeetingApp getVideoMeetingApplicationById(int id);
	
	public List<MeetingApplication> getApplication(UserInfo vo);
	
	public PageData<MeetingApplication> getApplication(UserInfo vo,HttpServletRequest request);
	
	public Integer delDeptsByAppId(Integer appId);
}
