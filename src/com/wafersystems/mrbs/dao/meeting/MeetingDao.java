package com.wafersystems.mrbs.dao.meeting;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.wafersystems.mrbs.dao.base.GenericDao;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;

public interface MeetingDao extends GenericDao<Meeting>{
	
	/**
	 * 查询已经审批通过的会诊/视频讲座
	 * @param MeetingCriteriaVO
	 * @return
	 * @throws Throwable 
	 */
	public PageData<Meeting> getMeetingList(PageSortModel psm,MeetingCriteriaVO meetingCriteriaVO) throws Throwable;
	
	/**
	 * 判断会议时间是否在预约时间之内
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param meetingRoomId 会议室Id
	 * @return
	 * @throws Throwable 
	 */
	public int getMeetingCountByStartTimeAndEndTime(String startTime,String endTime,String meetingRoomId,String exceptMeetingId) throws Throwable;
	
	/**根据条件查询meeting信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<Meeting> searchMeetingList(PageSortModel psm, Map<String,Object> map);
	public List<Meeting> getMeetingListIndex(Short state);
	
	public List<Meeting> getVdeioMeetingListIndex(Short state);
	public Long countMeetingConsultation(Short state);
	public List<Meeting> getMeetingByDuration(Date startDate, Date endDate);
	public Meeting getMeetingByApplicationId(int application_id);

	public Map<String, Long> summaryReportByDepart(String startDate, String endDate);

	public Map<String, Long> summaryReportByExpert(String startDate, String endDate);

	public Map<String, Long> summaryReportByMeetingType(String startDate, String endDate);

	public Map<String, List<Meeting>> detailReportByMeetingType(String startDate, String endDate);

	public Map<String, List<Meeting>> detailReportByDepart(String startDate, String endDate);

	public Map<String, List<Meeting>> detailReportByExpert(String startDate, String endDate);

	/**
	 * 通过视频讲座id，查询到会诊
	 * @param videoAppId
	 * @return
	 */
	public Meeting getMeetingByVideoAppId(Integer videoAppId);
	
	public List<Meeting> getMeetingOrderByDesc();//获取所有的会议并按倒序排列
	public List<Meeting> getVideoStartTimeOrderByDesc();//获取所有的会议并按倒序排列
	public Meeting getMeetingByIcuMonitor(int icuMonitorId);
	/**
	 * 根据icuVisitId得到meeting
	 * @param icuVisitId
	 * @return
	 */
	public Meeting getMeetingByIcuVisit(int icuVisitId);
}
