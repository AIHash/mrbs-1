package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.VideoApplicationPurposeDao;
import com.wafersystems.mrbs.dao.user.UserDao;
import com.wafersystems.mrbs.service.meeting.VideoApplicationPurposeService;
import com.wafersystems.mrbs.vo.meeting.VideoApplicationPurpose;

@Service(value = "videoApplicationPurposeService")
public class VideoApplicationPurposeServiceImpl implements VideoApplicationPurposeService {

	private VideoApplicationPurposeDao videoApplicationPurposeDao;
	
	@Override
	public VideoApplicationPurpose getVideoApplication(Short id) {
		return videoApplicationPurposeDao.get(id);
	}

	@Resource(type = VideoApplicationPurposeDao.class)
	public void setVideoApplicationPurposeDao(VideoApplicationPurposeDao videoApplicationPurposeDao) {
		this.videoApplicationPurposeDao = videoApplicationPurposeDao;
	}

	@Override
	public List<VideoApplicationPurpose> getAllVideoApplicationPurpose() {
		// TODO Auto-generated method stub
		return videoApplicationPurposeDao.findAll();
	}



}
