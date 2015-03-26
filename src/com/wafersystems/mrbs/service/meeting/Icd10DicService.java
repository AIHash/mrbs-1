package com.wafersystems.mrbs.service.meeting;

import java.util.List;
import java.util.Map;

import com.wafersystems.mrbs.vo.meeting.ICD10DIC;

public interface Icd10DicService {

	public List<ICD10DIC> getIcd10DicList();

	public List<ICD10DIC> getIcd10DicList(String department);

	public ICD10DIC getIcd10DicListById(int id);

	public Integer delIcdByAppId(Integer appId);

	/**
	 * 得到全部ICD部门
	 * @return
	 */
	public Map<Integer, String> getAllIcdDepartment();
	
	public List<ICD10DIC> getIcd10DicListLikeString(String s);
	/**
	 * 取得不包含指定id的Icd10
	 * @param icd10Ids 指定id
	 * @return
	 */
	public List<ICD10DIC> getIcd10DicListNotContainId(final Integer[] icd10Ids);

}
