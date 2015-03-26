package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.SatisfactionMain;

public interface SatisfactionMainService {

	public void saveSatisfactionMain(SatisfactionMain vo);

	public void updateSatisfactionMain(SatisfactionMain vo);

	public void delSatisfactionMain(SatisfactionMain vo);
	
	public SatisfactionMain getSatisfactionMainById(int id);

	public void saveOrUpdateSatisfactionMain(Object[] array, int scope,String suggestion);

}
