package com.wafersystems.mrbs.dao.meeting;

import java.util.List;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.web.criteriavo.VideoMeetingAppCriteriaVO;


public interface VideoMeetingAppDao extends GenericDao<VideoMeetingApp> {

	public List<VideoMeetingApp> getVideoMeetingList(Short state);
	
	public Long countAccraditation(Short state);
	
	public List<VideoMeetingApp> searchVideoMeetingApplicationList(PageSortModel psm,VideoMeetingAppCriteriaVO videoMeetingAppCriteriaVO) throws Throwable;
}
