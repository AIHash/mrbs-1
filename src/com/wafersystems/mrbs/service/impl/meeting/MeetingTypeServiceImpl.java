package com.wafersystems.mrbs.service.impl.meeting;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.MeetingTypeDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.MeetingTypeService;
import com.wafersystems.mrbs.vo.meeting.MeetingType;

@Service
public class MeetingTypeServiceImpl implements MeetingTypeService {

	private MeetingTypeDao meetingTypeDao;

	@Override
	@MrbsLog(desc="group.meetingType_create")
	public void saveMeetingType(MeetingType meetingType) {
		meetingTypeDao.save(meetingType);
	}

	@Override
	@MrbsLog(desc="group.meetingType_update")
	public void updateMeetingType(MeetingType meetingType) {
		meetingTypeDao.update(meetingType);
	}

	@Override
	@MrbsLog(desc="group.meetingType_delete")
	public void delMeetingType(MeetingType meetingType) {
		meetingTypeDao.delete(meetingType);

	}
	@Override
	public MeetingType getMeetingTypeById(short id) {
		return meetingTypeDao.get(id);
	}

	@Resource(type = MeetingTypeDao.class)
	public void setMeetingTypeDao(MeetingTypeDao meetingTypeDao) {
		this.meetingTypeDao = meetingTypeDao;
	}

	@Override
	public MeetingType getMeetingTypeByType(short type) {
		String sql = "select * from meeting_type m where m.value = " + type;
		return meetingTypeDao.findBySqlMeetingSql(sql);
	}



}