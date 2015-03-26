package com.wafersystems.mrbs.service.meeting;

import java.util.List;

import com.wafersystems.mrbs.vo.meeting.VideoApplicationPurpose;

public interface VideoApplicationPurposeService {
	public VideoApplicationPurpose getVideoApplication(Short id);
	
	public List<VideoApplicationPurpose> getAllVideoApplicationPurpose();
	
}
