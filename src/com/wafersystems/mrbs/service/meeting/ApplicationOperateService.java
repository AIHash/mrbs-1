package com.wafersystems.mrbs.service.meeting;

import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;

public interface ApplicationOperateService {
	
	//添加病历讨论
	public MeetingApplication saveMeetingApplication(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,MeetingApplication application);
	
	//修改病历讨论
	public void updateMeetingApplication(UserInfo userinfo,String expectedTime,String[] files,String[] hainaDatas, String[] deptmentid,String[] icd10Dic,MeetingApplication application);

	/**
	 * 会议管理员补录病历讨论
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param hainaDatas 海纳附件
	 * @param deptmentid 科室
	 * @param icd10Dic icd初诊
	 * @param application 病历讨论
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public MeetingApplication saveMeetingApplicationCollection(UserInfo userinfo,String[] files,String[] hainaDatas, 
			String[] deptmentid,String[] icd10Dic,MeetingApplication application,String startTime,String endTime,
			String selectedUserIds,String meetingRoomId)throws Exception;
	
	/**
	 * 会议管理员修改病历讨论
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param hainaDatas 海纳附件
	 * @param expectedTime 期望时间
	 * @param deptmentid 科室
	 * @param icd10Dic icd初诊
	 * @param application 病历讨论
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void updateMeetingApplication(UserInfo userinfo,String[] files,String[] hainaDatas, String expectedTime,
			String[] deptmentid,String[] icd10Dic,MeetingApplication application,String startTime,String endTime,
			String selectedUserIds,String meetingRoomId)throws Exception;
	
	//添加视频讲座
	public void saveVideoApplication(UserInfo userinfo,String expectedTime,String[] files,VideoMeetingApp videoMeetingApp)throws Exception;
	
	/**
	 * 会议管理员添加视频讲座
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param videoMeetingApp 视频讲座主表内容
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void saveVideoApplicationByManager(UserInfo userinfo,String[] files,VideoMeetingApp videoMeetingApp,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Exception;
	
	/**
	 * 会议管理员补录视频讲座
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param videoMeetingApp 视频讲座主表内容
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void saveVideoLecturesCollection(UserInfo userinfo,String[] files,VideoMeetingApp videoMeetingApp,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Exception;
	
	/**
	 * 会议管理员修改审核通过的视频讲座
	 * @param userinfo 添加用户
	 * @param files 附件
	 * @param videoMeetingApp 视频讲座主表内容
	 * @param startTime 审核开始时间
	 * @param endTime 审核结束时间
	 * @param selectedUserIds 受邀人
	 * @param meetingRoomId 会议室
	 */
	public void updateVideoApplication(UserInfo userinfo,String[] files,VideoMeetingApp videoMeetingApp,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Exception;
	
	//修改视频讲座
	public void updateVideoApplication(String[] files,String expecttime,String deptpersonid,String lectureContent,String videoApplicationPurposeid,String meetinglevel,String id,String teachingObject)throws Exception;
}
