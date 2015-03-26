package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.MeetingType;

public interface MeetingTypeService {

	public void saveMeetingType(MeetingType meetingType);

	public void updateMeetingType(MeetingType meetingType);

	public void delMeetingType(MeetingType meetingType);
	
	public MeetingType getMeetingTypeById(short id); 
	
	public MeetingType getMeetingTypeByType(short type); 

}
