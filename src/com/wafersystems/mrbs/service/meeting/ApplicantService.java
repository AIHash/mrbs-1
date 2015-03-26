package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.Applicant;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface ApplicantService {

	public void saveApplicant(Applicant vo);

	public void updateApplicant(Applicant vo);

	public void delApplicant(Applicant vo);
	
	public Applicant getApplicantById(int id);
	
	public List<Applicant> getApplicantList(UserInfo vo);

}
