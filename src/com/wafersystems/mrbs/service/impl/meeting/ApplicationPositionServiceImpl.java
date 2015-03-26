package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicationPositionDao;
import com.wafersystems.mrbs.service.meeting.ApplicationPositionService;
import com.wafersystems.mrbs.vo.meeting.ApplicationPosition;

@Service(value = "applicationPositionService")
public class ApplicationPositionServiceImpl implements ApplicationPositionService {

	private ApplicationPositionDao applicationPositionDao;
	
	@Override
	public ApplicationPosition getApplicationPosition(Short id) {
		return applicationPositionDao.get(id);
	}

	@Resource(type = ApplicationPositionDao.class)
	public void setApplicationPositionDao(
			ApplicationPositionDao applicationPositionDao) {
		this.applicationPositionDao = applicationPositionDao;
	}

	@Override
	public List<ApplicationPosition> getAllApplicationPosition() {
		return applicationPositionDao.findAll();
	}

}
