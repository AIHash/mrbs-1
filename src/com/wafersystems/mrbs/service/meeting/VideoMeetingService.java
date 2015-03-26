package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface VideoMeetingService {
	public void saveVideoMeeting(VideoMeetingApp vo);

	public List<VideoMeetingApp> getVedioMeetingApplication(UserInfo vo);

	public VideoMeetingApp getVideoMeetingApplicationById(int id);

	public void updateVedioMeetingApp(VideoMeetingApp app)throws Exception;
}
