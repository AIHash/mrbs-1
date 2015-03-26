package com.wafersystems.mrbs.service.meeadmdashbaord;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingApplicationCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.VideoMeetingAppCriteriaVO;

/**会议管理员对会议管理和操作
 * @author renwei
 *
 */

public interface MeeAdmDbdService {
	/**
	 * 审核病历讨论
	 * @param user 审核人
	 * @param meetingApplicationId 要审核的病历讨论Id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param selectedUserIds 所选择的参会人
	 * @param meetingRoomId 会议室Id
	 */
	public void saveAuditCaseDiscussion(UserInfo user,String meetingApplicationId,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Throwable;
	
	/**
	 * 审核视频讲座
	 * @param user 审核人
	 * @param videoMeetingApplicationId 要审核的视频讲座Id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param selectedUserIds 所选择的参会人
	 * @param meetingRoomId 会议室Id
	 */
	public void saveAuditVideoLectures(UserInfo user,String videoMeetingApplicationId,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Throwable;

	
	/**申请会议拒绝
	 * @param id  MeetingApplication的ID
	 * @param refuseReason 拒绝原来和理由
	 */
	public void refuseMeeting(Integer id,UserInfo userInfo,String refuseReason);

	/**查看会议详细信息
	 * @param id Meeting的id
	 * @return
	 */
	public Meeting viewMeeting(Integer id);
	
	/**查看视频讲座详细信息
	 * @param videoId VideoMeetingApp的id
	 * @return
	 * */
	public VideoMeetingApp viewVideo(Integer videoId);
	
	/**查看视频讲座详细信息
	 * @param videoId VideoMeetingApp的id
	 * @return
	 * */
	public Meeting viewMeetingByVideoapplicationId(Integer videoApplicationId);
	
	/**查看视频讲座详细信息
	 * @param videoId VideoMeetingApp的id
	 * @return
	 * */
	public MeetingApplication viewMeetingApplication(Integer meetingApplicationId);
	
	/**查看病历讨论详细信息
	 * @param videoId VideoMeetingApp的id
	 * @return
	 * */
	public Meeting viewMeetingByApplicationId(Integer applicationId);	
	
	/**根据条件查询meeting信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<Meeting> searchMeetingList(PageSortModel psm, Map<String,Object> map);
	/**根据条件查询meetingapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<MeetingApplication> searchMeetingAppList(PageSortModel psm, Map<String,Object> map)throws Throwable;

	/**根据条件查询meetingapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<MeetingApplication> searchMeetingAppList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO)throws Throwable;

	/**根据条件查询共同体中申请病历讨论meetingapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<MeetingApplication> searchUnifiedMeetingAppList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO)throws Throwable;	
	
	/**根据条件查询vedioapplication信息，分页处理
	 * @param psm
	 * @param map
	 * @return
	 */
	public List<VideoMeetingApp> searchVedioMeetingAppList(PageSortModel psm,VideoMeetingAppCriteriaVO videoMeetingAppCriteriaVO) throws Throwable;

	/**根据部门id获取专家信息
	 * @param id
	 * @return
	 */
	public Set<UserInfo> getUserInfoByDepart(String id);	

	/**根据部门父节点获取专家信息
	 * @param id
	 * @return
	 */
	public Set<Department> getUserInfoByParentDepartCode(String parentDeptCode);		
	/**根据部门id获取专家信息
	 * @param id
	 * @return
	 */
	public Department getUserInfoByDepartId(String deptId);	
	
	/**根据部门id获取该部门信息
	 * @param id
	 * @return
	 */
	public Department getDeptByDeptId(int deptId);
	
	//得到所有专家所在的部门 
	public List<Department> getdeptmentunified(String departvalue);
	
	/**得到所有专家所在部门
	 * @param id
	 * @return
	 */
	public List<Department> getAllDepts();
	
	/**根据用户组id获取用户组成员
	 * @param id
	 * @return
	 */
	public Set<UserInfo> getMemberByUserGroup(String id);
	
	public Set<UserInfo> getUserByDeptCode(String deptcode);

	/**
	 * 修改会议状态
	 */
	public void updateMeeting(Meeting meeting);
	
	/**
	 * 更新meeting表中MCU相关参数信息
	 * <p>@Author baininghan
	 * <p>@Date 2015年2月27日
	 * @param conferenceGUID 网真会议主键ID
	 * @param CallMCU MCU定时循环标记
	 * @param tcsURL 录播播放链接地址
	 * @param id Meeting的主键ID
	 */
	public void updateMCU(String conferenceGUID, int callMCU, String tcsURL, int id);

	/**
	 * 查找未结束的会议
	 */
	public List<Meeting> getNotEndMeeting();
	
	public List<Meeting> getMeetingForCallParticipants();

	/**
	 * 某一段时间的会议
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<Meeting> getMeetingByDuration(Date startDate, Date endDate);

	public Map<String, Long> summary2Report(Integer category, Date startDate, Date endDate);

	public Map<String, List<Meeting>> detail2Report(Integer category, Date startDate, Date endDate);

	public Meeting getMeetingByApplicationId(int application_id);

	/**
	 * 查询已经审批通过的会诊/视频讲座
	 * @param MeetingCriteriaVO
	 * @return
	 * @throws Throwable 
	 */
	public PageData<Meeting> getMeetingList(PageSortModel psm,MeetingCriteriaVO meetingCriteriaVO) throws Throwable;

	void refuseVideoMeeting(Integer id, UserInfo userInfo, String refuseReason);
	/**
	 * 根据部门Id取得允许参加会议的用户信息
	 * @param id 部门Id
	 * @return Set<UserInfo>
	 */
	public Set<UserInfo> getUserInfoOfExpertsByDepartId(String id);
	/**
	 * 查询已经结束的会诊
	 * @param user
	 * @param kind 申请的类型（远程会诊为1；视频讲座为2）
	 * @return
	 */
	public List<Meeting> getOverMeeting(UserInfo user,Short kind)throws Exception;
	
	/**
	 * 查询已经评价的信息，用于报表（会议为被接受且已经结束的会议）
	 * @param kind 申请的类型（远程会诊为1；视频讲座为2）
	 * @param startDate 报表统计开始时间
	 * @param endDate 报表统计结束时间
	 * @return List<Meeting>
	 * @throws Exception 
	 */
	public List<Meeting> getMeetingForReport(Short kind,String startDate,String endDate)throws Exception;

	/**
	 * 视频讲座审批通过发送的邮件
	 * @param videoId 视频讲座id
	 * @param expertNotice 专家通知信息
	 * @param communityNotice 共同体通知信息
	 * @param outNotice 院际专家信息
	 * @param expertsSelectedUserIds 选中的院内专家
	 * @param communitySelectedUserIds 选中的共同体
	 * @throws Exception
	 */
	public void sendVideoNotice(int videoId, String expertNotice, String communityNotice, String outNotice,String expertsSelectedUserIds,String communitySelectedUserIds)throws Exception;

	/**
	 * 远程会诊审批通过发送的邮件
	 * @param videoId 远程会诊id
	 * @param expertNotice 专家通知信息
	 * @param communityNotice 共同体通知信息
	 * @param outNotice 院际专家信息
	 * @param expertsSelectedUserIds 选中的院内专家
	 * @param communitySelectedUserIds 选中的共同体
	 * @param interSelectedUserIds 选中的院际专家
	 * @throws Exception
	 */
	public void sendApplicationNotice(int appId, String expertNotice, String communityNotice, String outNotice,String expertsSelectedUserIds,String communitySelectedUserIds,String interSelectedUserIds)throws Exception;

	/**
	 * 通过视频讲座id查询到会议
	 * @param videoAppId
	 * @return
	 */
	public Meeting getMeetingByVideoAppId(final Integer videoAppId);
	
	/**
	 * 判断会议时间是否在预约时间之内
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param meetingRoomId 会议室Id
	 * @return
	 * @throws Throwable 
	 */
	public int getMeetingCountByStartTimeAndEndTime(String startTime,String endTime,String meetingRoomId,String exceptMeetingId) throws Throwable;

	/**
	 * 删除指定ID的远程会诊申请
	 * @param appId
	 * @return
	 * @throws Exception
	 */
	public int deleteTeleApp(String accPath, Integer appId)throws Throwable;

	/**
	 * 删除指定ID的视频讲座
	 * @param accPath
	 * @param appId
	 * @return
	 * @throws Throwable
	 */
	public int deleteVideoApp(String accPath,Integer appId)throws Throwable;

	/**
	 * 删除指定id的会议
	 * @param accPath
	 * @param meetingId
	 * @return
	 * @throws Throwable
	 */
	public int deleteMeetingById(String accPath,Integer meetingId)throws Throwable;
	
	/**
	 * 获取所有病历讨论并按开始时间的倒序排列
	 * */
	public List<Meeting> getMeetingOrderByStartTimeDesc()throws Throwable;
	
	/**
	 * 获取所有视频讲座并按开始时间的倒序排列
	 * */
	public List<Meeting> getVideoOrderByStartTimeDesc()throws Throwable;;
	/**
	 * add by wangzhenglin
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询管理员界面监护或监护信息
	 */
	public List<ICUMonitor> searchICUMonitorList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable;
	
	/**
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询共同体和专家重症监护界面列表信息
	 */
	public List<ICUMonitor> searchUnifiedICUMonitAppList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable;
	/**
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询共同体和专家远程探视界面列表信息
	 */
	public List<ICUVisitation> searchICUVisitList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable;
	/**
	 * 通过监护ID获取监护信息
	 * @param icuMonitorId
	 * @return
	 */
	public ICUMonitor viewICUMonitor(Integer icuMonitorId);
	public Meeting getMeetingByIcuMonitor(int icuMonitorId);
	public int deleteIcuMonitor(String accPath,Integer appId)throws Throwable;
	
	/**
	 * add by wangzhenglin
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询管理员界面远程探视信息信息
	 */
	public List<ICUVisitation> searchApplyICUVisitList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable;
	/**
	 *根据ID删除远程探视信息 
	 * @throws Throwable
	 */
	public int deleteIcuVisit(Integer id)throws Throwable;
	/**
	 * 根据id得到远程探视信息
	 * @param iCUVisitId
	 * @return
	 */
	public ICUVisitation getICUVisitById(Integer iCUVisitId);
	/**
	 * 根据visitId查询meeting
	 * @param icuMonitorId
	 * @return
	 */
	public Meeting getMeetingByIcuVisitId(int icuVisitId);
}
