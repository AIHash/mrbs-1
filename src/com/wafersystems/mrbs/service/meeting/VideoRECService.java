package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.VideoREC;

public interface VideoRECService {

	public void saveVideoREC(VideoREC vo);

	public void updateVideoREC(VideoREC vo);

	public void delVideoREC(VideoREC vo);
	
	public VideoREC getVideoRECById(int id);

}
