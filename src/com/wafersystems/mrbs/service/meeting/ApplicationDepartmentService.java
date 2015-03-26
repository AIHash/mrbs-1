package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.ApplicationDepartment;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;

public interface ApplicationDepartmentService {

	public void saveApplicationDepartment(ApplicationDepartment vo);

	public void updateApplicationDepartment(ApplicationDepartment vo);

	public void delApplicationDepartment(ApplicationDepartment vo);
	
	public ApplicationDepartment getApplicationDepartmentById(int id);
	
	public List<ApplicationDepartment>  getApplicationDepartmentByApplicationId(MeetingApplication ma);

}
