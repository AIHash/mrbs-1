package com.wafersystems.mrbs.dao.meeting;

import java.util.List;
import java.util.Map;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;

public interface Icd10DicDao extends GenericDao<ICD10DIC> {

	List<String> getIcd10DicDepartmentList();

	List<ICD10DIC> getIcd10DicList(String department);

	public Integer delIcdByAppId(Integer appId);

	public Map<Integer, String> getAllIcdDepartment();

	public List<ICD10DIC> getIcd10DicListLikeString(String s);
	
	/**
	 * 取得不包含指定id的Icd10
	 * @param icd10Ids 指定id
	 * @return
	 */
	public List<ICD10DIC> getIcd10DicListNotContainId(final Integer[] icd10Ids);
	/**
	 * 删除重症监护对应的诊断病情信息
	 * @param monitorId
	 * @return
	 */
	public Integer delIcdByMonitorId(Integer monitorId);
}
