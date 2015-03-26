package com.wafersystems.mrbs.service.impl.meeting;

import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.ApplicantDao;
import com.wafersystems.mrbs.logmng.ann.MrbsLog;
import com.wafersystems.mrbs.service.meeting.ApplicantService;
import com.wafersystems.mrbs.vo.meeting.Applicant;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Service
public class ApplicantServiceImpl implements ApplicantService {

	private ApplicantDao dao;

	@Override
	@MrbsLog(desc="group.application_create")
	public void saveApplicant(Applicant vo) {
		dao.save(vo);
	}

	@Override
	@MrbsLog(desc="group.application_updateApplicant")
	public void updateApplicant(Applicant vo) {
		dao.update(vo);
	}

	@Override
	@MrbsLog(desc="group.application_delApplicant")
	public void delApplicant(Applicant vo) {
		dao.delete(vo);

	}
	
	@Override
	public Applicant getApplicantById(int id){
		return dao.get(id);
	}

	@Resource(type = ApplicantDao.class)
	public void setApplicantDao(ApplicantDao dao) {
		this.dao = dao;
	}

	@Override
	public List<Applicant> getApplicantList(UserInfo vo) 
	{
		LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
		String hql=" and owner='"+vo.getUserId()+"'";		
		linkmap.put("id", "desc");
		List<Applicant> results=dao.limitFindByHql(0, 5, hql, null, linkmap);
		return results;
	}

}