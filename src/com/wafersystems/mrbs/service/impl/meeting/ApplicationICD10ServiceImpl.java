package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicationICD10Dao;
import com.wafersystems.mrbs.service.meeting.ApplicationICD10Service;
import com.wafersystems.mrbs.vo.meeting.ApplicationICD10;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;

@Service
public class ApplicationICD10ServiceImpl implements ApplicationICD10Service {

	private ApplicationICD10Dao dao;

	@Override
	public void saveApplicationICD10(ApplicationICD10 vo) {
		dao.save(vo);
	}

	@Override
	public void updateApplicationICD10(ApplicationICD10 vo) {
		dao.update(vo);
	}

	@Override
	public void delApplicationICD10(ApplicationICD10 vo) {
		dao.delete(vo);

	}
	
	@Override
	public ApplicationICD10 getApplicationICD10ById(int id){
		return dao.get(id);
	}

	@Resource(type = ApplicationICD10Dao.class)
	public void setApplicationICD10Dao(ApplicationICD10Dao dao) {
		this.dao = dao;
	}

	@Override
	public List<ApplicationICD10> getApplicationICD10ByApplicationId(
			MeetingApplication ma) {
		String hql = " from ApplicationICD10 s where s.meetingApplication.id="+ma.getId();
		return dao.findTbyHql(hql);
	}

}