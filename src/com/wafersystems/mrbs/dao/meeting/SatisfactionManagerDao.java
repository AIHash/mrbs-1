package com.wafersystems.mrbs.dao.meeting;

import com.wafersystems.mrbs.dao.base.GenericDao;

import com.wafersystems.mrbs.vo.meeting.SatisfactionManager;

public interface SatisfactionManagerDao extends GenericDao<SatisfactionManager>{
	public Integer delSatisfactionManagerByMeetingId(int meetingId);
}
