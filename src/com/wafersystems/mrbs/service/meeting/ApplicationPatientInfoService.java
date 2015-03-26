package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.ApplicationPatientInfo;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;

public interface ApplicationPatientInfoService {

	public void saveApplicationPatientInfo(ApplicationPatientInfo vo);

	public void updateApplicationPatientInfo(ApplicationPatientInfo vo);

	public void delApplicationPatientInfo(ApplicationPatientInfo vo);
	
	public ApplicationPatientInfo getApplicationPatientInfoById(int id);
	
	public ApplicationPatientInfo getApplicationPatientInfoByApplicationId(MeetingApplication ma);

}
