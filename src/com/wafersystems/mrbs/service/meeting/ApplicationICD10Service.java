package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.ApplicationICD10;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;

public interface ApplicationICD10Service {

	public void saveApplicationICD10(ApplicationICD10 vo);

	public void updateApplicationICD10(ApplicationICD10 vo);

	public void delApplicationICD10(ApplicationICD10 vo);

	public ApplicationICD10 getApplicationICD10ById(int id);

	public List<ApplicationICD10> getApplicationICD10ByApplicationId(MeetingApplication ma);

}
