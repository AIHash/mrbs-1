package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.Satisfaction;

public interface SatisfactionService {

	public void saveSatisfaction(Satisfaction vo);

	public void updateSatisfaction(Satisfaction vo);

	public void delSatisfaction(Satisfaction vo);
	
	public Satisfaction getSatisfactionById(int id);

	public void saveOrUpdateSatisfaction(Object[] array,Short localNumber,String evalvalue1,String evalvalue2,String evalvalue3, String suggestion);
	
	public boolean getOpintionState(Object[] array);
	/**
	 * 根据meetingId和userId取得Satisfaction
	 */
	public Satisfaction getSatisfaction(Object[] array);


}
