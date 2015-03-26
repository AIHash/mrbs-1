package com.wafersystems.mrbs.service.impl.meeting;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.meeting.Icd10DicDao;
import com.wafersystems.mrbs.service.meeting.Icd10DicService;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;

@Service
public class Icd10DicServiceImpl implements Icd10DicService {

	private Icd10DicDao icd10DicDao;

	@Override
	public List<ICD10DIC> getIcd10DicList() {
		return icd10DicDao.findAll();
	}

	@Override
	public List<ICD10DIC> getIcd10DicList(String department) {
		return icd10DicDao.getIcd10DicList(department);
	}

	@Resource
	public void setIcd10DicDao(Icd10DicDao icd10DicDao) {
		this.icd10DicDao = icd10DicDao;
	}

	@Override
	public ICD10DIC getIcd10DicListById(int id) {
		return icd10DicDao.get(id);
	}

	public Integer delIcdByAppId(Integer appId){
		return icd10DicDao.delIcdByAppId(appId);
	}

	public Map<Integer, String> getAllIcdDepartment(){
		return icd10DicDao.getAllIcdDepartment();
	}

	public List<ICD10DIC> getIcd10DicListLikeString(String s){
		return icd10DicDao.getIcd10DicListLikeString(s);
	}
	public List<ICD10DIC> getIcd10DicListNotContainId(Integer[] icd10Ids){
		return icd10DicDao.getIcd10DicListNotContainId(icd10Ids);
	}
}