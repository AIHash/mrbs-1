package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.SatisfactionManager;

public interface SatisfactionManagerService {

	public void saveSatisfactionManager(SatisfactionManager vo);

	public void updateSatisfactionManager(SatisfactionManager vo);

	public void delSatisfactionManager(SatisfactionManager vo);
	
	public SatisfactionManager getSatisfactionManagerById(int id);
	
	public void saveMeetingStartRealTime(Meeting meeting);

	public void saveOrUpdateSatisfactionManager(Object[] array, int scope,String meetingTime,Meeting meeting,String realStartTime,String realEndTime);
	
	public SatisfactionManager getSatisfactionManager(Object[] array);
}
