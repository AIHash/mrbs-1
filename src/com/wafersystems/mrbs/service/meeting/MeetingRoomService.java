package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.BaseException;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;

public interface MeetingRoomService {

	public void saveMeetingRoom(MeetingRoom meetingRoom) throws BaseException;

	public void updateMeetingRoom(MeetingRoom meetingRoom) throws BaseException;

	public void delMeetingRoom(MeetingRoom meetingRoom) throws BaseException;
	
	public MeetingRoom getMeetingRoomBySn(String sn);
	
	public MeetingRoom getMeetingRoomById(Integer id);
	
	public List<MeetingRoom> getMeetingRoom(PageSortModel psm);

}
