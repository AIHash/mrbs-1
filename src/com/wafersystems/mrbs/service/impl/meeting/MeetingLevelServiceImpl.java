package com.wafersystems.mrbs.service.impl.meeting;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.MeetingLevelDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.MeetingLevelService;
import com.wafersystems.mrbs.vo.meeting.MeetingLevel;

@Service
public class MeetingLevelServiceImpl implements MeetingLevelService {

	private MeetingLevelDao dao;

	@Override
	@MrbsLog(desc="group.meetingLeve_create")
	public void saveMeetingLevel(MeetingLevel vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.meetingLeve_update")
	public void updateMeetingLevel(MeetingLevel vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.meetingLeve_delete")
	public void delMeetingLevel(MeetingLevel vo) {
		dao.delete(vo);

	}
	
	@Override
	public MeetingLevel getMeetingLevelById(short id){
		return dao.get(id);
	}

	@Resource(type = MeetingLevelDao.class)
	public void setMeetingLevelDao(MeetingLevelDao dao) {
		this.dao = dao;
	}

}