package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.ApplicationAuthorInfo;

public interface ApplicationAuthorInfoService {

	public void saveApplicationAuthorInfo(ApplicationAuthorInfo vo);

	public void updateApplicationAuthorInfo(ApplicationAuthorInfo vo);

	public void delApplicationAuthorInfo(ApplicationAuthorInfo vo);
	
	public ApplicationAuthorInfo getApplicationAuthorInfoById(int id);

}
