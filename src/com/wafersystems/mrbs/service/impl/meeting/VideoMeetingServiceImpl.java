package com.wafersystems.mrbs.service.impl.meeting;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.VideoMeetingAppDao;
import com.wafersystems.mrbs.service.meeting.VideoMeetingService;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Service(value = "videoMeetingSerivice")
public class VideoMeetingServiceImpl implements VideoMeetingService {

	private VideoMeetingAppDao videoMeetingAppDao;
	
	@Override
	public void saveVideoMeeting(VideoMeetingApp vo) {
		videoMeetingAppDao.save(vo);
	}

	@Resource(type = VideoMeetingAppDao.class)
	public void setVideoMeetingDao(VideoMeetingAppDao videoMeetingAppDao) {
		this.videoMeetingAppDao = videoMeetingAppDao;
	}

	@Override
	public List<VideoMeetingApp> getVedioMeetingApplication(UserInfo vo) {
		LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
		String hql=" and videoRequester='"+vo.getUserId()+"'";		
		linkmap.put("suggestDate", "desc");
		linkmap.put("appDate", "desc");
		List<VideoMeetingApp> results=videoMeetingAppDao.limitFindByHql(0, 7, hql, null, linkmap);
		return results;

	}

	public VideoMeetingApp getVideoMeetingApplicationById(int id) {
		return videoMeetingAppDao.get(id);
	}

	public void updateVedioMeetingApp(VideoMeetingApp app)throws Exception{
		videoMeetingAppDao.update(app);
	}
}
