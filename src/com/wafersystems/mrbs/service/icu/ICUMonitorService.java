package com.wafersystems.mrbs.service.icu;

import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface ICUMonitorService {

	/**
	 * 保存监护申请
	 * @param userinfo
	 * @param expectedTime
	 * @param files
	 * @param hainaDatas
	 * @param deptmentid
	 * @param icd10Dic
	 * @param application
	 * @return
	 */
	public ICUMonitor saveICUMonit(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,ICUMonitor application);
	/**
	 * 通过ID获取对应的重症监护信息
	 * @param id
	 * @return
	 */
	public ICUMonitor getICUMonitById(int id);
	/**
	 * add by wangzhenglin 更新，修改重症监护信息
	 * @param userinfo
	 * @param expectedTime
	 * @param files
	 * @param hainaDatas
	 * @param deptmentid
	 * @param icd10Dic
	 * @param application
	 */
	public void updateICUMonitor(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,ICUMonitor iCUMonitor);
	/**
	 * add by wangzhenglin 审核重症监护
	 * @param user
	 * @param meetingApplicationId
	 * @param startTime
	 * @param endTime
	 * @param selectedUserIds
	 * @param meetingRoomId
	 * @throws Throwable
	 */
	public void saveAuditIcuMonitor(UserInfo user,String icuId,String startTime,String endTime,String selectedUserIds,String meetingRoomId,String devicesNo) throws Throwable;
	/**
	 * add by wangzhenglin 修改审核通过但是未结束的重症监护信息
	 * @param userinfo
	 * @param files
	 * @param hainaDatas
	 * @param expectedTime
	 * @param deptmentid
	 * @param icd10Dic
	 * @param iCUMonitor
	 * @param startTime
	 * @param endTime
	 * @param selectedUserIds
	 * @param meetingRoomId
	 * @throws Exception
	 */
	public void updateICUMonitor(UserInfo userinfo,String[] files,String[] hainaDatas, String expectedTime,
			String[] deptmentid,String[] icd10Dic,ICUMonitor iCUMonitor,String startTime,String endTime,
			String selectedUserIds,String meetingRoomId,String devicesNo)throws Exception;
	
	public void saveICUVisit(UserInfo userinfo, String expectedTime, ICUVisitation visitation);
	/**
	 * 审核拒绝通过提交时间
	 * @param id
	 * @param userInfo
	 * @param refuseReason
	 */
	public void refuseIcuMonitorPass(Integer id, UserInfo userInfo,String refuseReason) ;
	/**
	 * 审核通过后，管理员通过通知功能，通知指定的专家共同体
	 * @param appId
	 * @param expertNotice
	 * @param communityNotice
	 * @param outNotice
	 * @param expertsSelectedUserIds
	 * @param communitySelectedUserIds
	 * @param interSelectedUserIds
	 * @throws Exception
	 */
	public void sendIcuMonitorNotice(int appId, String expertNotice, String communityNotice, String outNotice,String expertsSelectedUserIds,String communitySelectedUserIds,String interSelectedUserIds)throws Exception;
	
	/**
	 * 根据远程探视ID得到远程探视信息
	 * @param id
	 * @return
	 */
	public ICUVisitation getICUVisitationById(Integer id); 
	/**
	 * 修改远程探视
	 * @param userinfo
	 * @param expectedTime
	 * @param visitation
	 */
	public void updateICUVisit(UserInfo userinfo,String expectedTime,ICUVisitation visitation);
	/**
	 * 远程探视审核拒绝
	 * @param id
	 * @param userInfo
	 * @param refuseReason
	 */
	public void refuseIcuVisitPass(Integer id, UserInfo userInfo,String refuseReason) ;
	/**
	 * 远程探视审核通过
	 * @param user
	 * @param icuId
	 * @param startTime
	 * @param endTime
	 * @param meetingRoomId
	 * @param mobileDevicesId
	 * @throws Throwable
	 */
	public void saveAuditIcuVisit(UserInfo user,String icuId,String startTime,String endTime,String meetingRoomId,String mobileDevicesId) throws Throwable;
	/**
	 * 根据meetingId得到移动设备信息
	 */
	public MeetingMobileDevices getMobileDevicesByMeetingId(Integer meetingId);
	
	public void EditICUVisitForPass(UserInfo userinfo,String expectedTime,ICUVisitation visit,String startTime,String endTime,String meetingRoomId,String mobileDevices);
	/**
	 * 管理员添加远程探视
	 * @param userinfo
	 * @param startTime
	 * @param endTime
	 * @param meetingRoomId
	 * @param mobileDevices
	 * @param visitation
	 */
	public void managerSaveICUVisit(UserInfo userinfo,String startTime,String endTime,String meetingRoomId,String mobileDevices,ICUVisitation visitation);
}
