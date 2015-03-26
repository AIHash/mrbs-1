package com.wafersystems.mrbs.dao.meeting;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;

public interface SatisfactionDao extends GenericDao<Satisfaction>{
	/**
	 * 根据会议id，删除与会诊相关的共同体评价
	 * @param meetingId
	 */
	public Integer delSatisfactionByMeetingId(int meetingId);
}
