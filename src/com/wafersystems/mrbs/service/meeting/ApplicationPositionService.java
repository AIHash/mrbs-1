package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.ApplicationPosition;

public interface ApplicationPositionService {
	public ApplicationPosition getApplicationPosition(Short id);
	
	public List<ApplicationPosition> getAllApplicationPosition();
}
