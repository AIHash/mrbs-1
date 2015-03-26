package com.wafersystems.mrbs.service.impl.meeting;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.VideoRECDao;
import com.wafersystems.mrbs.service.meeting.VideoRECService;
import com.wafersystems.mrbs.vo.meeting.VideoREC;

@Service
public class VideoRECServiceImpl implements VideoRECService {

	private VideoRECDao dao;

	@Override
	public void saveVideoREC(VideoREC vo) {
		dao.save(vo);
	}

	@Override
	public void updateVideoREC(VideoREC vo) {
		dao.update(vo);
	}

	@Override
	public void delVideoREC(VideoREC vo) {
		dao.delete(vo);

	}
	
	@Override
	public VideoREC getVideoRECById(int id){
		return dao.get(id);
	}

	@Resource(type = VideoRECDao.class)
	public void setVideoRECDao(VideoRECDao dao) {
		this.dao = dao;
	}

}