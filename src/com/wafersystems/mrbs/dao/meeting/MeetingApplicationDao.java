package com.wafersystems.mrbs.dao.meeting;

import java.util.List;
import java.util.Map;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.web.criteriavo.MeetingApplicationCriteriaVO;

public interface MeetingApplicationDao extends GenericDao<MeetingApplication>{

	public List<MeetingApplication> getMeetingApplicationList(Short state);

	/**根据条件查询meetingapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<MeetingApplication> searchMeetingApplicationList(PageSortModel psm, Map<String,Object> map) throws Throwable;

	/**根据条件查询meetingapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<MeetingApplication> searchMeetingApplicationList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO) throws Throwable;

	/**根据条件查询共同体的病历讨论meetingapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<MeetingApplication> searchUnifiedMeetingApplicationList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO) throws Throwable;
	/**
	 * 删除远程会诊申请的科室
	 * @param appId
	 * @return
	 */
	public Integer delDeptsByAppId(Integer appId);

	/**
	 * 删除远程会诊申请的病例提交人
	 * @param appId
	 */
	public void delApplicationAuthorInfoByAppId(Integer appId);

	/**
	 * 除远程会诊申请的病例提交人
	 * @param appId
	 */
	public void delApplicationPatientInfoByAppId(Integer appId);
}
