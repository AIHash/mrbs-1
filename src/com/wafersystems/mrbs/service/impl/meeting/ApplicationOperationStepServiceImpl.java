package com.wafersystems.mrbs.service.impl.meeting;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicationOperationStepDao;
import com.wafersystems.mrbs.service.meeting.ApplicationOperationStepService;
import com.wafersystems.mrbs.vo.meeting.ApplicationOperationStep;

@Service
public class ApplicationOperationStepServiceImpl implements ApplicationOperationStepService {

	private ApplicationOperationStepDao dao;

	@Override
	public void saveApplicationOperationStep(ApplicationOperationStep vo) {
		dao.save(vo);
	}

	@Override
	public void updateApplicationOperationStep(ApplicationOperationStep vo) {
		dao.update(vo);
	}

	@Override
	public void delApplicationOperationStep(ApplicationOperationStep vo) {
		dao.delete(vo);

	}
	
	@Override
	public ApplicationOperationStep getApplicationOperationStepById(int id){
		return dao.get(id);
	}

	@Resource(type = ApplicationOperationStepDao.class)
	public void setApplicationOperationStepDao(ApplicationOperationStepDao dao) {
		this.dao = dao;
	}

}