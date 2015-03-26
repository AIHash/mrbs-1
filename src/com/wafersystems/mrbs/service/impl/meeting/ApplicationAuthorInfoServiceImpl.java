package com.wafersystems.mrbs.service.impl.meeting;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicationAuthorInfoDao;
import com.wafersystems.mrbs.service.meeting.ApplicationAuthorInfoService;
import com.wafersystems.mrbs.vo.meeting.ApplicationAuthorInfo;

@Service
public class ApplicationAuthorInfoServiceImpl implements ApplicationAuthorInfoService {

	private ApplicationAuthorInfoDao dao;

	@Override
	public void saveApplicationAuthorInfo(ApplicationAuthorInfo vo) {
		dao.save(vo);
	}

	@Override
	public void updateApplicationAuthorInfo(ApplicationAuthorInfo vo) {
		dao.update(vo);
	}

	@Override
	public void delApplicationAuthorInfo(ApplicationAuthorInfo vo) {
		dao.delete(vo);

	}
	
	@Override
	public ApplicationAuthorInfo getApplicationAuthorInfoById(int id){
		return dao.get(id);
	}

	@Resource(type = ApplicationAuthorInfoDao.class)
	public void setApplicationAuthorInfoDao(ApplicationAuthorInfoDao dao) {
		this.dao = dao;
	}

}