package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicationDepartmentDao;
import com.wafersystems.mrbs.service.meeting.ApplicationDepartmentService;
import com.wafersystems.mrbs.vo.meeting.ApplicationDepartment;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;

@Service
public class ApplicationDepartmentServiceImpl implements ApplicationDepartmentService {

	private ApplicationDepartmentDao dao;

	@Override
	public void saveApplicationDepartment(ApplicationDepartment vo) {
		dao.save(vo);
	}

	@Override
	public void updateApplicationDepartment(ApplicationDepartment vo) {
		dao.update(vo);
	}

	@Override
	public void delApplicationDepartment(ApplicationDepartment vo) {
		dao.delete(vo);

	}
	
	@Override
	public ApplicationDepartment getApplicationDepartmentById(int id){
		return dao.get(id);
	}

	@Resource(type = ApplicationDepartmentDao.class)
	public void setApplicationDepartmentDao(ApplicationDepartmentDao dao) {
		this.dao = dao;
	}

	@Override
	public List<ApplicationDepartment> getApplicationDepartmentByApplicationId(
			MeetingApplication ma) {
		String hql = " from ApplicationDepartment s where s.meetingApplication.id="+ma.getId();
		return dao.findTbyHql(hql);
	}

}