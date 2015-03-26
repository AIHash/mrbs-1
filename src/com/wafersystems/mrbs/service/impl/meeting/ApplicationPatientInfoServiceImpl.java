package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicationPatientInfoDao;
import com.wafersystems.mrbs.service.meeting.ApplicationPatientInfoService;
import com.wafersystems.mrbs.vo.meeting.ApplicationPatientInfo;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;

@Service
public class ApplicationPatientInfoServiceImpl implements ApplicationPatientInfoService {

	private ApplicationPatientInfoDao dao;

	@Override
	public void saveApplicationPatientInfo(ApplicationPatientInfo vo) {
		dao.save(vo);
	}

	@Override
	public void updateApplicationPatientInfo(ApplicationPatientInfo vo) {
		dao.update(vo);
	}

	@Override
	public void delApplicationPatientInfo(ApplicationPatientInfo vo) {
		dao.delete(vo);

	}
	
	@Override
	public ApplicationPatientInfo getApplicationPatientInfoById(int id){
		return dao.get(id);
	}

	@Resource(type = ApplicationPatientInfoDao.class)
	public void setApplicationPatientDao(ApplicationPatientInfoDao dao) {
		this.dao = dao;
	}

	@Override
	public ApplicationPatientInfo getApplicationPatientInfoByApplicationId(
			MeetingApplication ma) {
		String hql = " from ApplicationPatientInfo s where s.meetingApplication.id=" + ma.getId();
		List<ApplicationPatientInfo> list = dao.findTbyHql(hql);
		if(list != null && list.size() > 0)
			return list.get(0);
		return null;
	}

}