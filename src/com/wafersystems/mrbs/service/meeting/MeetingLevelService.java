package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.MeetingLevel;

public interface MeetingLevelService {

	public void saveMeetingLevel(MeetingLevel vo);

	public void updateMeetingLevel(MeetingLevel vo);

	public void delMeetingLevel(MeetingLevel vo);
	
	public MeetingLevel getMeetingLevelById(short id);

}
