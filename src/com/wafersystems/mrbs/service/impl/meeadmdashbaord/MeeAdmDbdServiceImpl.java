package com.wafersystems.mrbs.service.impl.meeadmdashbaord;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mcu.MCUParams;
import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.dao.icu.ICUMonitorDao;
import com.wafersystems.mrbs.dao.icu.ICUVisitDao;
import com.wafersystems.mrbs.dao.icu.IcuNoticeDetailDao;
import com.wafersystems.mrbs.dao.mas.MtTeleconDao;
import com.wafersystems.mrbs.dao.meeting.AccessoriesDao;
import com.wafersystems.mrbs.dao.meeting.Icd10DicDao;
import com.wafersystems.mrbs.dao.meeting.MeetingApplicationDao;
import com.wafersystems.mrbs.dao.meeting.MeetingDao;
import com.wafersystems.mrbs.dao.meeting.MeetingMemberDao;
import com.wafersystems.mrbs.dao.meeting.MeetingSummaryDao;
import com.wafersystems.mrbs.dao.meeting.NoticeDetailDao;
import com.wafersystems.mrbs.dao.meeting.SatisfactionManagerDao;
import com.wafersystems.mrbs.dao.meeting.VideoMeetingAppDao;
import com.wafersystems.mrbs.dao.user.DepartmentDao;
import com.wafersystems.mrbs.dao.user.UserDao;
import com.wafersystems.mrbs.dao.user.UserGroupDao;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.notice.factory.NoticeFactory;
import com.wafersystems.mrbs.vo.mas.MtTelecon;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingApplicationCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.VideoMeetingAppCriteriaVO;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.NoticeInfo;
import com.wafersystems.util.StrUtil;

@Service
public class MeeAdmDbdServiceImpl implements MeeAdmDbdService {
	Logger logger = LoggerFactory.getLogger(MeeAdmDbdServiceImpl.class);
	private MeetingApplicationDao meetingApplicationDao;
	private MeetingDao meetingDao;
	private UserGroupDao userGroupDao;
	private DepartmentDao departmentDao;
	private Icd10DicDao icd10DicDao;
	private SatisfactionManagerDao satisfactionManagerDao;
	private UserDao userDao;
	private MeetingMemberDao meetingMemberDao; 
	private MeetingSummaryDao summaryDao;
	private AccessoriesDao accessoriesDao;
//	private DepartmentService departmentService;
//	private UserService userService;
//	private UserTypeService userTypeService;
	private NoticeFactory noticeFactory;
	private VideoMeetingAppDao videoMeetingAppDao;
	private MtTeleconDao mtTeleconDao;
	private NoticeDetailDao noticeDetailDao;
	private ICUMonitorDao iCUMonitorDao;
	private ICUVisitDao icuVisitDao;
	private IcuNoticeDetailDao  icuNoticeDetailDao;
	public Meeting getMeetingByVideoAppId(final Integer videoAppId){
		return meetingDao.getMeetingByVideoAppId(videoAppId);
	}

	@Override
	/**
	 * 审核病历讨论
	 * @param user 审核人
	 * @param meetingApplicationId 要审核的病历讨论Id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param selectedUserIds 所选择的参会人
	 * @param meetingRoomId 会议室Id
	 */
	public void saveAuditCaseDiscussion(UserInfo user,String meetingApplicationId,String startTime,String endTime,String selectedUserIds,String meetingRoomId) throws Throwable {
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditCaseDiscussion  meetingApplicationId="+meetingApplicationId);
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditCaseDiscussion  startTime="+startTime);
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditCaseDiscussion  endTime="+endTime);
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditCaseDiscussion  selectedUserIds="+selectedUserIds);
			MeetingApplication meetingApplication=this.meetingApplicationDao.get(Integer.valueOf(meetingApplicationId));
			Meeting meeting = new Meeting();
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			meeting.setApplicationId(meetingApplication);
			meeting.setTitle(meetingApplication.getPurpose());
			meeting.setContent(meetingApplication.getMedicalRecord());
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			meeting.setLevel(meetingApplication.getLevel());
			meeting.setMeetingType(meetingApplication.getMeetingType());
			meeting.setCreator(meetingApplication.getRequester());
			meeting.setHolder(user);
			
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_TELECONSULTATION);

			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);

			meeting.setState(GlobalConstent.MEETING_STATE_PENDING);
			this.meetingDao.save(meeting);

			//修改申请的状态
			meetingApplication.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
			meetingApplication.setStartTime(startDate);
			meetingApplication.setEndTime(endDate);
			meetingApplication.setExpectedTime(startDate);
			this.meetingApplicationDao.update(meetingApplication);
			//所选择的参会人
			String [] attendUserIdObjs = null;
			if (!StrUtil.isEmptyStr(selectedUserIds)) {
				if (selectedUserIds.endsWith("|")) {
					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
				}
				attendUserIdObjs = selectedUserIds.split("\\|");
			}

			//保存参与会诊的人员
			MeetingMember meetingMember=null;
			UserInfo userInfo = null;
			UnifiedNotice mailNotice = null;
			//短信
			MtTelecon mtTelecon=null;
			//添加参会人
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				for(String userId:attendUserIdObjs){
					meetingMember=new MeetingMember();
					meetingMember.setMeetingId(meeting);
					userInfo=userDao.get(userId);
					meetingMember.setMember(userInfo);
					meetingMember.setAttendState(Short.valueOf(GlobalConstent.APPLICATION_STATE_NONE));
					//当参会者为专家时，参会人数为1
					if(userInfo!=null&&userInfo.getUserType()!=null&&userInfo.getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
						meetingMember.setAttendNo((short)1);
						//Bug #5690 审核病历讨论或视频讲座时邀请的专家默认参会
						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					}else{
						meetingMember.setAttendNo((short)0);
					}
					//审批通过当申请户默认我已接受
					if(userId.equals(meetingApplication.getRequester().getUserId())){
						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					}
					this.meetingMemberDao.save(meetingMember);
				}
				//申请人
				userInfo = meetingApplication.getRequester();
				//申请人为启用状态
				if(userInfo.getState()==1){
					//通知申请者，申请通过
					mailNotice = new UnifiedNotice();				
					mailNotice.setReceiver(userInfo.getMail());
					mailNotice.setTitle(NoticeInfo.getMessage("man_successnotice_title"));
					mailNotice.setMessage(NoticeInfo.getNoticeMessage("man_successnotice_content", meeting,userInfo.getName()));
					String url="";
					try {
						url = URLEncoder.encode("/unifiedindex/emailApplication?applicationid="+meeting.getId(), "utf-8");
					} catch (Exception e) {
						e.printStackTrace();
					}
					mailNotice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" + "?uid="+userInfo.getUserId()+"&pwd="+userInfo.getPassword()+"&direct="+url);
					this.noticeFactory.addMailNotice(mailNotice);
					
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					if(StringUtils.isNotBlank(userInfo.getMobile())){					
						mtTelecon = new MtTelecon();
						mtTelecon.setSmId("0");
						mtTelecon.setSrcId("0");
						mtTelecon.setMobile(userInfo.getMobile());
						mtTelecon.setContent(userInfo.getName()+"您好，您在" + dateFormat.format(meetingApplication.getApplicationTime()) + "申请的病历讨论《"+meetingApplication.getPurpose()+"》已经审核通过");
						//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
						mtTelecon.setSendTime(null);
						mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
						mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_002);
						mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
						mtTelecon.setResendTimes(0);
						this.noticeFactory.addMtTelecon(mtTelecon);
					}
				}
				
			}
		}catch(Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.saveAuditCaseDiscussion",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdServiceImpl.saveAuditCaseDiscussion",e);
			throw e;
		}
	}

	/**
	 * 审核视频讲座
	 * @param user 审核人
	 * @param videoMeetingApplicationId 要审核的视频讲座Id
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param selectedUserIds 所选择的参会人
	 * @param meetingRoomId 会议室Id
	 */
	public void saveAuditVideoLectures(UserInfo user,String videoMeetingApplicationId,String startTime,String endTime,String selectedUserIds,String meetingRoomId)throws Throwable{
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditVideoLectures  videoMeetingApplicationId="+videoMeetingApplicationId);
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditVideoLectures  startTime="+startTime);
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditVideoLectures  endTime="+endTime);
			logger.debug("Enter MeeAdmDbdServiceImpl.saveAuditVideoLectures  selectedUserIds="+selectedUserIds);
			VideoMeetingApp videoMeetingApplication=this.videoMeetingAppDao.get(Integer.valueOf(videoMeetingApplicationId));
			
			Meeting meeting = new Meeting();
			//开始时间
			Date startDate = DateUtil.getDateTimeByHours(startTime);
			//结束时间
			Date endDate = DateUtil.getDateTimeByHours(endTime);
			meeting.setVideoapplicationId(videoMeetingApplication);
			meeting.setTitle(videoMeetingApplication.getLectureContent());
			meeting.setContent(videoMeetingApplication.getLectureContent());
			meeting.setStartTime(startDate);
			meeting.setEndTime(endDate);
			meeting.setLevel(videoMeetingApplication.getLevel());
			meeting.setMeetingType(videoMeetingApplication.getMeetingType());
			meeting.setCreator(videoMeetingApplication.getVideoRequester());
			meeting.setHolder(user);
			meeting.setMeetingKind(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
			
			MeetingRoom meetingRoom = new MeetingRoom();
			if (!StrUtil.isEmptyStr(meetingRoomId)) {
				meetingRoom.setId(Integer.valueOf(meetingRoomId));
			}else{
				meetingRoom.setId(1);
			}
			meeting.setMeetingRoomId(meetingRoom);
			
			meeting.setState(Short.valueOf(GlobalConstent.MEETING_STATE_PENDING));
			this.meetingDao.save(meeting);
			
			//修改申请的状态
			videoMeetingApplication.setState(GlobalConstent.MEETING_APPLICATION_STATE_PASS);
			videoMeetingApplication.setStartTime(startDate);
			videoMeetingApplication.setEndTime(endDate);
			int appVideoUserType = Integer.parseInt(meeting.getCreator().getUserType().getValue().toString());
			if(appVideoUserType != 2){
				videoMeetingApplication.setSuggestDate(startDate);
			}
			this.videoMeetingAppDao.update(videoMeetingApplication);
			//所选择的参会人
			String [] attendUserIdObjs = null;
			if (!StrUtil.isEmptyStr(selectedUserIds)) {
				if (selectedUserIds.endsWith("|")) {
					selectedUserIds = selectedUserIds.substring(0, selectedUserIds.length() - 1);
				}
				attendUserIdObjs = selectedUserIds.split("\\|");
			}

			//保存参与会诊的人员
			MeetingMember meetingMember=null;
			UserInfo userInfo = null;
			UnifiedNotice mailNotice = null;
			//短信
			MtTelecon mtTelecon=null;
			//添加参会人
			if(attendUserIdObjs!=null&&attendUserIdObjs.length>0){
				//将主讲人放入申请表中
				userInfo=userDao.get(attendUserIdObjs[0]);
				videoMeetingApplication.setUserName(userInfo);
				videoMeetingApplication.setDeptName(userInfo.getDeptId());
				this.videoMeetingAppDao.update(videoMeetingApplication);
				for(String userId:attendUserIdObjs){
					meetingMember=new MeetingMember();
					meetingMember.setMeetingId(meeting);
					userInfo=userDao.get(userId);
					meetingMember.setMember(userInfo);
					meetingMember.setAttendState(Short.valueOf(GlobalConstent.APPLICATION_STATE_NONE));
					//当参会者为专家时，参会人数为1
					if(userInfo!=null&&userInfo.getUserType()!=null&&userInfo.getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
						meetingMember.setAttendNo((short)1);
						//Bug #5690 审核病历讨论或视频讲座时邀请的专家默认参会
						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					}else{
						meetingMember.setAttendNo((short)0);
					}
					//审批通过当申请户默认我已接受
					if(userId.equals(videoMeetingApplication.getVideoRequester().getUserId())){
						meetingMember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					}
					this.meetingMemberDao.save(meetingMember);
				}
				//通知申请者，申请通过
				//如果申请者为会议管理员，则审核通过后不发送通知
				int typeId=videoMeetingApplication.getVideoRequester().getRole().getId();
				//如果申请者状态为启用状态
				short state=videoMeetingApplication.getVideoRequester().getState();
				if(typeId!=4&&state==1){
						mailNotice = new UnifiedNotice();
						userInfo = videoMeetingApplication.getVideoRequester();
						mailNotice.setReceiver(userInfo.getMail());
						mailNotice.setTitle(NoticeInfo.getMessage("man_successnotice_video_title"));
						mailNotice.setMessage(NoticeInfo.getNoticeMessage("man_successnotice_video_content", meeting,userInfo.getName()));
						String url="";
						 try {
							 url = URLEncoder.encode("/unifiedindex/emailVideoApplication?applicationid="+videoMeetingApplication.getId(), "utf-8");
						 } catch (Exception e) {
							 e.printStackTrace();
						 }
						 mailNotice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" +"?uid="+userInfo.getUserId()+"&pwd="+userInfo.getPassword()+"&direct="+url);
						 this.noticeFactory.addMailNotice(mailNotice);
			
						 SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
						 if(!videoMeetingApplication.getVideoRequester().getUserType().getValue().equals(GlobalConstent.USER_TYPE_MEETING_ADMIN) && StringUtils.isNotBlank(videoMeetingApplication.getVideoRequester().getMobile())){
						    mtTelecon = new MtTelecon();
							mtTelecon.setSmId("0");
							mtTelecon.setSrcId("0");
							mtTelecon.setMobile(videoMeetingApplication.getVideoRequester().getMobile());
							mtTelecon.setContent(userInfo.getName()+"您好，您在" + dateFormat.format(videoMeetingApplication.getAppDate()) + "申请的视频讲座《"+videoMeetingApplication.getLectureContent()+"》已经审核通过");
							//Date sendTime = new Date(new Date().getTime());
							mtTelecon.setSendTime(null);
							mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
							mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_005);
							mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
							mtTelecon.setResendTimes(0);
							this.noticeFactory.addMtTelecon(mtTelecon);
						 }
			    }
			}
		}catch(Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.saveAuditVideoLectures",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdServiceImpl.saveAuditVideoLectures",e);
			throw e;
		}
	}

	@Override
	public void refuseMeeting(Integer id, UserInfo userInfo,String refuseReason) {
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.refuseMeeting refuseReason="+refuseReason);
			MeetingApplication ma=this.meetingApplicationDao.get(id);
			ma.setRefuseReason(refuseReason);
			ma.setState(Short.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED));
			meetingApplicationDao.update(ma);
			//通知申请者，申请拒绝
			UserInfo ui=ma.getRequester();
			//判断申请者为启用状态
			if(ui.getState()==1){
				UnifiedNotice notice=null;
				notice=new UnifiedNotice();			
				notice.setReceiver(ui.getMail());
				notice.setTitle(NoticeInfo.getMessage("man_falsenotice_title"));
				notice.setMessage(NoticeInfo.getNoticeMessage("man_falsenotice_content", ma,ui.getName()));
				String url="";
				try {
					url = URLEncoder.encode("/unifiedindex/emailApplication?applicationid="+ma.getId(), "utf-8");
				} catch (Exception e) {
					e.printStackTrace();
				}
				notice.setAcceptUrl(GlobalParam.outter_access_ip + "/login/notice" + "?uid="+ui.getUserId()+"&pwd="+ui.getPassword()+"&direct="+url);
				this.noticeFactory.addMailNotice(notice);
		
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				if(StringUtils.isNotBlank(ui.getMobile())){	
					MtTelecon mtTelecon = new MtTelecon();
					mtTelecon.setSmId("0");
					mtTelecon.setSrcId("0");
					mtTelecon.setMobile(ui.getMobile());
					mtTelecon.setContent(ui.getName()+"您好，您在" + dateFormat.format(ma.getApplicationTime()) + "申请的病历讨论《"+ma.getPurpose()+"》审核未通过，拒绝原因是："+ma.getRefuseReason()+"，请重新申请");
					//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
					mtTelecon.setSendTime(null);
					mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
					mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_003);
					mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
					mtTelecon.setResendTimes(0);
					this.noticeFactory.addMtTelecon(mtTelecon);					
				}
			}
			
		}catch(Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.refuseMeeting",e);
		}
	}

	@Override
	public void refuseVideoMeeting(Integer id, UserInfo userInfo,String refuseReason) {
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.refuseMeeting refuseVideoMeeting="+refuseReason);
			VideoMeetingApp ma=this.videoMeetingAppDao.get(id);
			ma.setRefuseReason(refuseReason);
			ma.setState(Short.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED));
			videoMeetingAppDao.update(ma);
			//通知申请者，申请拒绝
			//如果申请者为会议管理员或不处于启用状态，则不需要发送通知
			if(ma.getVideoRequester().getRole().getId()!=4&&ma.getVideoRequester().getState()==1){
			UnifiedNotice notice=null;
			notice=new UnifiedNotice();
			UserInfo ui=ma.getVideoRequester();
			notice.setReceiver(ui.getMail());
			notice.setTitle(NoticeInfo.getMessage("man_falseVidonotice_title"));
			notice.setMessage(NoticeInfo.getNoticeMessage("man_falseVidonotice_content", ma,ui.getName()));
			String url="";
			 try {
				   url = URLEncoder.encode("/unifiedindex/emailVideoApplication?applicationid="+ma.getId(), "utf-8");
				  } catch (Exception e) {
				   e.printStackTrace();
				  }
			notice.setAcceptUrl(GlobalParam.outter_access_ip  + "/login/notice" + "?uid="+ui.getUserId()+"&pwd="+ui.getPassword()+"&direct="+url);
			this.noticeFactory.addMailNotice(notice);
	
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			if(!ma.getVideoRequester().getUserType().getValue().equals(GlobalConstent.USER_TYPE_MEETING_ADMIN) &&StringUtils.isNotBlank( ma.getVideoRequester().getMobile())){
				MtTelecon mtTelecon = new MtTelecon();
				mtTelecon.setSmId("0");
				mtTelecon.setSrcId("0");
				mtTelecon.setMobile(ma.getVideoRequester().getMobile());
				mtTelecon.setContent(ma.getVideoRequester().getName()+"您好，您在" + dateFormat.format(ma.getAppDate()) + "申请的视频讲座《"+ma.getLectureContent()+"》审核未通过，拒绝原因是："+ma.getRefuseReason()+"，请重新申请");
				//Date sendTime = new Date(new Date().getTime());
				mtTelecon.setSendTime(null);
				mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_006);
				mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
				mtTelecon.setResendTimes(0);
				this.noticeFactory.addMtTelecon(mtTelecon);	
			}
		  }
		}catch(Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.refuseVideoMeeting",e);
		}
	}

	@Override
	public Meeting viewMeeting(Integer id) { 
		return this.meetingDao.get(id);
	}

	/**查看视频讲座详细信息
	 * @param id Meeting的id
	 * @return
	 * */
	@Override
	public VideoMeetingApp viewVideo(Integer videoId) {
		return this.videoMeetingAppDao.get(videoId);
	}
	
	/**通过视频讲座的id查看Meeting中的视频讲座
	 * @param id Meeting的videoapplicationId
	 * @return
	 * */   
	@Override
	public Meeting viewMeetingByVideoapplicationId(Integer videoApplicationId) {
		return this.getMeetingByVideoAppId(videoApplicationId);
	}
	
	/**通过病历讨论的id查看MeetingApplication中的病历讨论
	 * @param id Meeting的videoapplicationId
	 * @return
	 * */
	@Override
	public MeetingApplication viewMeetingApplication(
			Integer meetingApplicationId) {
		// TODO Auto-generated method stub
		return this.meetingApplicationDao.get(meetingApplicationId);
	}
	
	@Override
	public List<MeetingApplication> searchMeetingAppList(PageSortModel psm,
			Map<String, Object> map) throws Throwable {
		return this.meetingApplicationDao.searchMeetingApplicationList(psm, map);
	}

	@Override
	public List<MeetingApplication> searchMeetingAppList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO) throws Throwable{
		return this.meetingApplicationDao.searchMeetingApplicationList(psm, meetingApplicationCriteriaVO);
	}
	
    /**
     * 查询共同体中的申请病历讨论信息
     * */
	@Override
	public List<MeetingApplication> searchUnifiedMeetingAppList(PageSortModel psm, MeetingApplicationCriteriaVO meetingApplicationCriteriaVO) throws Throwable{
		return this.meetingApplicationDao.searchUnifiedMeetingApplicationList(psm, meetingApplicationCriteriaVO);
	}
	@Override
	public List<Meeting> searchMeetingList(PageSortModel psm,
			Map<String, Object> map) {
		return meetingDao.searchMeetingList(psm, map);
	}

	@Override
	public Set<UserInfo> getMemberByUserGroup(String id) {
		return this.userGroupDao.get(Short.valueOf(id)).getMembers();
	}

	@Override
	public Set<UserInfo> getUserInfoByDepart(String id) {
		return this.departmentDao.getUserInfoByDepart(Integer.valueOf(id));
	}
	
	/**
	 * 根据部门Id取得允许参加会议的用户信息
	 * @param id 部门Id
	 * @return Set<UserInfo>
	 */
	public Set<UserInfo> getUserInfoOfExpertsByDepartId(String id) {
		return this.departmentDao.getUserInfoOfExpertsByDepartId(Integer.valueOf(id));
	}

	@Override
	public Department getUserInfoByDepartId(String deptId) {
		return this.departmentDao.getUserInfoByDepartId(Integer.valueOf(deptId));
	}	

    /**
     * 根据科室的父节点找到该科室的所有专家
     * */
	@Override
	public Set<Department> getUserInfoByParentDepartCode(String parentDeptCode) {
		return departmentDao.getUserInfoByParentDeptCode(parentDeptCode);
	}

	/**
	 * 修改会议状态
	 */
	public void updateMeeting(Meeting meeting){
		meetingDao.update(meeting);
	}

	/**
	 * 更新meeting表中MCU相关参数信息
	 * <p>@Author baininghan
	 * <p>@Date 2015年2月27日
	 * @param conferenceGUID 网真会议主键ID
	 * @param CallMCU MCU定时循环标记
	 * @param tcsURL 录播播放链接地址
	 * @param id Meeting的主键ID
	 */
	public void updateMCU(String conferenceGUID, int callMCU, String tcsURL, int id){
		String hql = "set o.conferenceGUID=?,o.haveCallMCU=?,o.tcsURL=?,o.id=?";
		meetingDao.executeDMLForMCU(hql, new Object[]{conferenceGUID, callMCU, tcsURL, id});
	}
	
	/**
	 * 查找未结束的会议
	 */
	public List<Meeting> getNotEndMeeting(){
		return meetingDao.findTbyHql("from Meeting m where m.state != ?", 
				new Object[]{GlobalConstent.MEETING_STATE_END});
	}
	
	public List<Meeting> getMeetingForCallParticipants(){
		return meetingDao.findTbyHql("from Meeting m where  m.haveCallMCU = ?", 
				new Object[]{MCUParams.HaveCallMCU});
	}

	public Meeting getMeetingByApplicationId(int application_id){
		return this.meetingDao.getMeetingByApplicationId(application_id);
	}

	/**
	 * 查询已经结束的会诊
	 * @param user
	 * @param kind 申请的类型（远程会诊为1；视频讲座为2）
	 * @return
	 * @throws Exception 
	 */
	@Override
	public List<Meeting> getOverMeeting(UserInfo user,Short kind) throws Exception 
	{
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.getOverMeeting");
			LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
			String hql=" and state='"+GlobalConstent.MEETING_STATE_END+"'  and meetingKind='"+kind+"'" +
					   " and exists (select s.member from o.members as s where s.member='"+user.getUserId()+"' and s.attendState='"+String.valueOf(GlobalConstent.APPLICATION_STATE_ACCEPT)+"')";
			linkmap.put("startTime", "desc");
			List<Meeting> results=meetingDao.limitFindByHql(-1, -1, hql, null, linkmap);
//			List<Meeting> meetingList = new ArrayList<Meeting>();
//			for (Meeting meeting : results) {
//				for (SatisfactionManager main : meeting.getSatisfactionManager()) {
//					if(main.getUser().getUserId().equals(user.getUserId())){
//						meeting.setIsSummaryOff(true);
//						meetingList.add(meeting);
//						break;
//					}else{
//						meetingList.add(meeting);
//					}
//				}
//			}
//			return meetingList;
			return results;
		}catch (Exception e) {
			logger.error("Error MeeAdmDbdServiceImpl.getOverMeeting",e);
			throw e;
		} 
	}
	
	/**
	 * 查询已经评价的信息，用于报表（会议为被接受且已经结束的会议）
	 * @param kind 申请的类型（远程会诊为1；视频讲座为2;重症监护为3）
	 * @param startDate 报表统计开始时间
	 * @param endDate 报表统计结束时间
	 * @return List<Meeting>
	 * @throws Exception 
	 */
	@Override
	public List<Meeting> getMeetingForReport(Short kind,String startDate,String endDate)throws Exception{
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.getMeetingForReport");
			LinkedHashMap<String,Object> linkmap=new LinkedHashMap<String,Object>();
			String hql=" and state='"+GlobalConstent.MEETING_STATE_END+"'  and meetingKind='"+kind+"'" ;
            if(kind != 4){
            	hql += " and exists (select s.member from o.members as s where s.attendState='"+String.valueOf(GlobalConstent.APPLICATION_STATE_ACCEPT)+"')";
            }
					  
			if(!StrUtil.isEmptyStr(startDate)&&!StrUtil.isEmptyStr(endDate))
			 {
				 hql+=" and startTime>='"+startDate+" 00:00:00'"+" and startTime<='"+endDate+" 23:59:59' and endTime<='"+endDate+" 23:59:59'"+" and endTime>='"+startDate+" 00:00:00'";
				 //hql+=" and endTime<='"+endDate+" 23:59:59'"+" and endTime>='"+startDate+" 00:00:00'";
			 }
			linkmap.put("startTime", "desc");
			linkmap.put("creator", "desc");
			List<Meeting> results=meetingDao.limitFindByHql(-1, -1, hql, null, linkmap);
			return results;
		}catch (Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.getMeetingForReport",e);
			throw e;
		}
	}

	/**
	 * 查询已经审批通过的会诊/视频讲座
	 * @param MeetingCriteriaVO
	 * @return
	 * @throws Throwable 
	 */
	public PageData<Meeting> getMeetingList(PageSortModel psm,MeetingCriteriaVO meetingCriteriaVO) throws Throwable{
		try{
			logger.debug("Enter MeeAdmDbdServiceImpl.getMeetingList");
			return meetingDao.getMeetingList(psm, meetingCriteriaVO);
			
		}catch(Exception e){
			logger.error("Error MeeAdmDbdServiceImpl.getMeetingList",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdServiceImpl.getMeetingList",e);
			throw e;
		}
	}

	@Override
	public List<Meeting> getMeetingByDuration(Date startDate, Date endDate){
		endDate = DateUtils.addHours(endDate, 24);
		return meetingDao.getMeetingByDuration(startDate, endDate);
	}

	@Override
	public Map<String, Long> summary2Report(Integer category, Date startDate, Date endDate){
		endDate = DateUtils.addHours(endDate, 24);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = format.format(startDate);
		String endStr = format.format(endDate);

		switch (category) {
		case 1:
			return meetingDao.summaryReportByMeetingType(startStr, endStr);

		case 2:
			return meetingDao.summaryReportByDepart(startStr, endStr);

		case 3:
			return meetingDao.summaryReportByExpert(startStr, endStr);
		}
		return null;
	}

	@Override
	public Map<String, List<Meeting>> detail2Report(Integer category, Date startDate, Date endDate){
		endDate = DateUtils.addHours(endDate, 24);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String startStr = format.format(startDate);
		String endStr = format.format(endDate);

		switch (category) {
		case 1:
			return meetingDao.detailReportByMeetingType(startStr, endStr);

		case 2:
			return meetingDao.detailReportByDepart(startStr, endStr);

		case 3:
			return meetingDao.detailReportByExpert(startStr, endStr);
		}
		return null;
	}

	@Override
	public Department getDeptByDeptId(int deptId) {	
		return this.departmentDao.getDeptByDeptId(deptId);
	}

	@Override
	public List<Department> getdeptmentunified(String departvalue) {
		String hql = "From Department d where d.deptcode <>'001001' and d.deptcode like '001001%' and d.name like'%"+departvalue+"%' order by deptcode";
		List<Department> results = departmentDao.findTbyHql(hql);
		if (results != null && results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				Department vo = results.get(i);
				String depecode = vo.getDeptcode();
				int length = (depecode.length()) / 3 - 3;
				for (int j = 0; j < length; j++) {
					vo.setName("——" + vo.getName());
				}
			}
		}
		return results;
	}

	@Override
	public List<Department> getAllDepts() {
		String hql = "From Department d where d.deptcode <>'001001' and d.deptcode like '001001%' order by deptcode";
		List<Department> results = departmentDao.findTbyHql(hql);
		if (results != null && results.size() > 0) {
			for (int i = 0; i < results.size(); i++) {
				Department vo = results.get(i);
				String depecode = vo.getDeptcode();
				int length = (depecode.length()) / 3 - 3;
				for (int j = 0; j < length; j++) {
					vo.setName("——" + vo.getName());
				}
			}
		}
		return results;
	}

	@Override
	public List<VideoMeetingApp> searchVedioMeetingAppList(PageSortModel psm,VideoMeetingAppCriteriaVO videoMeetingAppCriteriaVO) throws Throwable {
		return this.videoMeetingAppDao.searchVideoMeetingApplicationList(psm,videoMeetingAppCriteriaVO);
	}

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
	@Override
	public void sendVideoNotice(int videoId, String expertNotice, String communityNotice, String outNotice,String expertsSelectedUserIds,String communitySelectedUserIds)throws Exception{
		try{
			VideoMeetingApp videoApp = videoMeetingAppDao.get(videoId);
			UserInfo member = null;//参会人
			//所选择的院内专家
			String[] attendExpertUserIds = this.splitAttendMembersUserId(expertsSelectedUserIds);
			for (String expertUserId : attendExpertUserIds) {
				member = userDao.get(expertUserId);
				sendMsg(member, expertNotice, videoId);
			}

			//所选择的共同体
			String[] attendCommunityUserIds = this.splitAttendMembersUserId(communitySelectedUserIds);
			for (String communityUserId : attendCommunityUserIds) {
				member = userDao.get(communityUserId);
				sendMsg(member, communityNotice, videoId);
			}

			//发送次数+1
			int times = videoApp.getNoticeSendTimes() == null ? 0 :  videoApp.getNoticeSendTimes() + 1 ;
			videoApp.setNoticeSendTimes(times);
			videoMeetingAppDao.update(videoApp);
		}catch(Exception e){
			logger.error("sendVideoNotice, 视频讲座添加通知出错", e);
		}
	}

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
	@Override
	public void sendApplicationNotice(int appId, String expertNotice, String communityNotice, String outNotice,String expertsSelectedUserIds,String communitySelectedUserIds,String interSelectedUserIds)throws Exception{
		try{
			MeetingApplication ma = meetingApplicationDao.get(appId);
			UserInfo member = null;//参会人
			//所选择的院内专家
			String[] attendExpertUserIds = this.splitAttendMembersUserId(expertsSelectedUserIds);
			for (String expertUserId : attendExpertUserIds) {
				member = userDao.get(expertUserId);
				sendMeetingMsg(member, expertNotice, appId);
			}

			//所选择的共同体
			String[] attendCommunityUserIds = this.splitAttendMembersUserId(communitySelectedUserIds);
			for (String communityUserId : attendCommunityUserIds) {
				member = userDao.get(communityUserId);
				sendMeetingMsg(member, communityNotice, appId);
			}
			//所选择的院际专家
			String[] attendOutUserIds = this.splitAttendMembersUserId(interSelectedUserIds);
			for (String outUserId : attendOutUserIds) {
				member = userDao.get(outUserId);
				sendMeetingMsg(member, outNotice, appId);
			}
			//发送次数+1
			int times = ma.getNoticeSendTimes() == null ? 1 :  ma.getNoticeSendTimes() + 1 ;
			ma.setNoticeSendTimes(times);
			meetingApplicationDao.update(ma);
		}catch(Exception e){
			logger.error("sendApplicationNotice, 远程会诊添加通知出错", e);
		}
	}
	
	/**
	 * 判断会议时间是否在预约时间之内
	 * @param startTime 开始时间
	 * @param endTime 结束时间
	 * @param meetingRoomId 会议室Id
	 * @return
	 * @throws Throwable 
	 */
	public int getMeetingCountByStartTimeAndEndTime(String startTime,String endTime,String meetingRoomId,String exceptMeetingId) throws Throwable{
		try{
			return meetingDao.getMeetingCountByStartTimeAndEndTime(startTime, endTime,meetingRoomId,exceptMeetingId);
		}catch(Exception e){
			logger.error("Error UserServiceImpl.getUserListByUserNameOrDeptName",e);
			throw e;
		}catch(Throwable e){
			logger.error("Error UserServiceImpl.getUserListByUserNameOrDeptName",e);
			throw e;
		}
	}

	@Override
	public int deleteTeleApp(String accPath,Integer appId)throws Throwable{
		MeetingApplication meetingApp = meetingApplicationDao.get(appId);
		if(meetingApp.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){//审核通过
			Meeting meeting = meetingDao.getMeetingByApplicationId(appId);
			summaryDao.delSummaryForManagerSaveData(meeting.getId());
			//satisfactionDao.delSatisfactionByMeetingId(meeting.getId());
			satisfactionManagerDao.delSatisfactionManagerByMeetingId(meeting.getId());
			//参会人不用删除，删除会议时，自动删除
			//meetingMemberDao.delMembersByMeetingId(meeting.getId());
			meetingDao.delete(meeting.getId());
		}
		//删除附件
		int accSize = accessoriesDao.deleteAccessoriesAndFileByAppId(accPath, appId);
		logger.debug("删除远程会诊id为" + appId + "附件" + accSize + "个");
		//删除科室
		int depsSize = meetingApplicationDao.delDeptsByAppId(appId);
		logger.debug("删除远程会诊id为" + appId + "科室" + depsSize + "个");
		//删除ICD初诊判断
		int icdSize = icd10DicDao.delIcdByAppId(appId);
		logger.debug("删除远程会诊id为" + appId + "ICD初诊判断" + icdSize + "个");
		//删除通知明细数据		
		int noticeDetailSize= noticeDetailDao.deleteNoticeDetailByAppId(appId);
		logger.debug("删除远程会诊id为" + appId + "通知信息" + noticeDetailSize+"条");
		//删除申请
		meetingApplicationDao.delete(appId);
		//病例提交人不用删除，删除申请时，自动删除
	//	meetingApplicationDao.delApplicationAuthorInfoByAppId(meetingApp.getAuthorInfo().getId());
		//删除患者信息不用删除，删除申请时，自动删除
	//	meetingApplicationDao.delApplicationPatientInfoByAppId(meetingApp.getPatientInfo().getId());
		return 0;
	}

	@Override
	public int deleteVideoApp(String accPath,Integer videoAppId)throws Throwable{
		VideoMeetingApp videoApp = videoMeetingAppDao.get(videoAppId);
		if(videoApp.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){//审核通过
			Meeting meeting = meetingDao.getMeetingByVideoAppId(videoAppId);
			//非会诊管理员评价不用删除，删除会议时，自动删除
			//satisfactionDao.delSatisfactionByMeetingId(meeting.getId());
			satisfactionManagerDao.delSatisfactionManagerByMeetingId(meeting.getId());
			//参会人不用删除，删除会议时，自动删除
			//meetingMemberDao.delMembersByMeetingId(meeting.getId());
			meetingDao.delete(meeting.getId());
		}
		//删除附件
		int accSize = accessoriesDao.deleteAccessoriesAndFileByVideoId(accPath, videoAppId);
		logger.debug("删除远程会诊id为" + videoAppId + "附件" + accSize + "个");
		//删除申请
		videoMeetingAppDao.delete(videoAppId);
		return 0;
	}

	public int deleteMeetingById(String accPath,Integer meetingId)throws Throwable{
		Meeting meeting = meetingDao.get(meetingId);
		if(meeting.getMeetingKind().equals((short)1)){//远程会诊
			this.deleteTeleApp(accPath, meeting.getApplicationId().getId());
		}else if(meeting.getMeetingKind().equals((short)3)){
			this.deleteIcuMonitor(accPath, meeting.getiCUMonitorId().getId());
		}else{
			this.deleteVideoApp(accPath, meeting.getVideoapplicationId().getId());
		}
		return 0;
	}

	@Override
	public Set<UserInfo> getUserByDeptCode(String deptcode) {
		return null;
	}

	@Resource(type = AccessoriesDao.class)
	public void setAccessoriesDao(AccessoriesDao accessoriesDao) {
		this.accessoriesDao = accessoriesDao;
	}

	@Resource(type = NoticeFactory.class)
	public void setNoticeFactory(NoticeFactory noticeFactory) {
		this.noticeFactory = noticeFactory;
	}

	@Resource(type = MeetingMemberDao.class)
	public void setMeetingMemberDao(MeetingMemberDao meetingMemberDao) {
		this.meetingMemberDao = meetingMemberDao;
	}

	@Resource(type = UserDao.class)
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Resource(type = DepartmentDao.class)
	public void setDepartmentDao(DepartmentDao departmentDao) {
		this.departmentDao = departmentDao;
	}

	@Resource(type = UserGroupDao.class)
	public void setUserGroupDao(UserGroupDao userGroupDao) {
		this.userGroupDao = userGroupDao;
	}

	@Resource(type = MeetingApplicationDao.class)
	public void setMeetingApplicationDao(MeetingApplicationDao meetingApplicationDao) {
		this.meetingApplicationDao = meetingApplicationDao;
	}

	@Resource(type = MeetingDao.class)
	public void setMeetingDao(MeetingDao meetingDao) {
		this.meetingDao = meetingDao;
	}

	@Resource(type = VideoMeetingAppDao.class)
	public void setVideoMeetingDao(VideoMeetingAppDao videoMeetingAppDao) {
		this.videoMeetingAppDao = videoMeetingAppDao;
	}

//	@Resource(type = DepartmentService.class)
//	public void setDepartmentService(DepartmentService departmentService) {
//		this.departmentService = departmentService;
//	}
//
//	@Resource(type = UserService.class)
//	public void setUserService(UserService userService) {
//		this.userService = userService;
//	}
//
//	@Resource(type = UserTypeService.class)
//	public void setUserTypeService(UserTypeService userTypeService) {
//		this.userTypeService = userTypeService;
//	}

	@Resource
	public void setIcd10DicDao(Icd10DicDao icd10DicDao) {
		this.icd10DicDao = icd10DicDao;
	}

	@Resource
	public void setSummaryDao(MeetingSummaryDao summaryDao) {
		this.summaryDao = summaryDao;
	}

	@Resource
	public void setSatisfactionManagerDao(
			SatisfactionManagerDao satisfactionManagerDao) {
		this.satisfactionManagerDao = satisfactionManagerDao;
	}
	
	@Resource
	public void setMtTeleconDao(MtTeleconDao mtTeleconDao){
		this.mtTeleconDao=mtTeleconDao;
	}
	
	@Resource
	public void setNoticeDetailDao(NoticeDetailDao noticeDetailDao){
		this.noticeDetailDao=noticeDetailDao;
	}
	@Resource
	public void setiCUMonitorDao(ICUMonitorDao iCUMonitorDao){
		this.iCUMonitorDao=iCUMonitorDao;
	}
	
	@Resource
	public void setIcuVisitDao(ICUVisitDao icuVisitDao){
		this.icuVisitDao=icuVisitDao;
	}
	
	@Resource
	public void setIcuNoticeDetailDao(IcuNoticeDetailDao icuNoticeDetailDao) {
		this.icuNoticeDetailDao = icuNoticeDetailDao;
	}

	private String[] splitAttendMembersUserId(String userIds){
		String [] attendUserIdObjs;
		if (!StrUtil.isEmptyStr(userIds) && userIds.endsWith("|")) {
			userIds = userIds.substring(0, userIds.length() - 1);
			 attendUserIdObjs = userIds.split("\\|");
		}else{
			attendUserIdObjs = new String[0];
		}
		return attendUserIdObjs;
	}

	/**
	 * 添加视频讲座通知
	 * 
	 * @param user 通知人
	 * @param msg 内容
	 * @param meetingId 视频讲座id
	 * @throws UnsupportedEncodingException
	 */
	private void sendMsg(UserInfo user, String msg, int id) throws UnsupportedEncodingException{
		try{
			//邮件
			VideoMeetingApp videoApp = videoMeetingAppDao.get(id);
			UnifiedNotice mailNotice = new UnifiedNotice();
			mailNotice.setTitle(NoticeInfo.getMessage("successVoidTitle"));
			String url = URLEncoder.encode("/unifiedindex/emailMeeting?meetingid="	+ id, "utf-8");
			mailNotice.setAcceptUrl(GlobalParam.outter_access_ip  + "/login/notice" + "?uid=" + user.getUserId() + "&pwd=" + user.getPassword()+ "&direct=" + url);
			
			//短信
			MtTelecon mtTelecon = new MtTelecon();			
			String srcId=String.valueOf(mtTeleconDao.getMaxSrcId());						
			mtTelecon.setSmId(srcId);
			mtTelecon.setSrcId(srcId);
			//Date sendTime = new Date(new Date().getTime());
			mtTelecon.setSendTime(null);
			mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
			//判断接收人是否为专家
			if(user.getRole().getId()==3){
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_010);
			}else{
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_008);
			}
			//判断接收人是否为申请者
			if(user.getUserId().equals(videoApp.getVideoRequester().getUserId())){
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_011);
			}
			mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
			mtTelecon.setResendTimes(0);
			mtTelecon.setAcceptSmsUserId(user.getUserId());
			mtTelecon.setApplicationId(id);					
						
			if(StringUtils.isNotBlank(user.getMail())){
				mailNotice.setReceiver(user.getMail());
				mailNotice.setMessage(msg);
				this.noticeFactory.addMailNotice(mailNotice);
			}	
			if(StringUtils.isNotBlank(user.getMobile())){
				mtTelecon.setMobile(user.getMobile());
				mtTelecon.setContent(msg);
				this.noticeFactory.addMtTelecon(mtTelecon);	
			}
		}catch(Exception e){
			logger.error("MeeAdmDbdServiceImpl sendMsg error",e);
			e.printStackTrace();
		}catch(Throwable e){
			logger.error("MeeAdmDbdServiceImpl sendMsg error",e);
			e.printStackTrace();
		}
	}
	/**
	 * 添加病例讨论通知
	 * 
	 * @param user 通知人
	 * @param msg 内容
	 * @param meetingId 病例讨论id
	 * @throws UnsupportedEncodingException
	 */
	private void sendMeetingMsg(UserInfo user, String msg, int id) throws UnsupportedEncodingException{
		try{
			MeetingApplication meetingApplication = meetingApplicationDao.get(id);
			//邮件
			UnifiedNotice mailNotice = new UnifiedNotice();
			mailNotice.setTitle(NoticeInfo.getMessage("successMedicalRecordsTitle"));
			String url = URLEncoder.encode("/unifiedindex/emailMeeting?meetingid="	+ id, "utf-8");
			mailNotice.setAcceptUrl(GlobalParam.outter_access_ip  + "/login/notice" + "?uid=" + user.getUserId() + "&pwd=" + user.getPassword()+ "&direct=" + url);			
			
			//短信
			MtTelecon mtTelecon = new MtTelecon();	
			String srcId=String.valueOf(mtTeleconDao.getMaxSrcId());				
			mtTelecon.setSmId(srcId);
			mtTelecon.setSrcId(srcId);
			//Date sendTime = new Date(new Date().getTime() + GlobalConstent.TO_MANAGER_SMS_DELAY * 1000 * 60);
			mtTelecon.setSendTime(null);
			mtTelecon.setDataExpiredFlag(GlobalConstent.DATE_EXPIRED_FLAG_N);
			//判断接收人是否为专家
			if(user.getRole().getId()==3){
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_009);
			}else{
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_007);
			}
			//如果接收人为申请者
			if(user.getUserId().equals(meetingApplication.getRequester().getUserId())){
				mtTelecon.setSmsServiceCode(GlobalConstent.SMS_SERVICE_CODE_011);
			}
			
			mtTelecon.setState(GlobalConstent.NOTICE_NOT_SEND);
			mtTelecon.setResendTimes(0);
			mtTelecon.setAcceptSmsUserId(user.getUserId());
			mtTelecon.setApplicationId(id);			
			
			if(StringUtils.isNotBlank(user.getMail())){
				mailNotice.setReceiver(user.getMail());
				mailNotice.setMessage(msg);
				this.noticeFactory.addMailNotice(mailNotice);
			}		
			if(StringUtils.isNotBlank(user.getMobile())){	
				mtTelecon.setMobile(user.getMobile());
				mtTelecon.setContent(msg);
				this.noticeFactory.addMtTelecon(mtTelecon);				
			}
		}catch(Exception e){
			logger.error("MeeAdmDbdServiceImpl sendMsg error",e);
			e.printStackTrace();
		}catch(Throwable e){
			logger.error("MeeAdmDbdServiceImpl sendMsg error",e);
			e.printStackTrace();
		}
	}

	/**
	 * 获取所有的会议并按开始时间的倒序排列
	 * */
	@Override
	public List<Meeting> getMeetingOrderByStartTimeDesc() throws Throwable {
		return meetingDao.getMeetingOrderByDesc();
	}

	/**
	 * 获取所有视频讲座并按开始时间的倒序排列
	 * */
	@Override
	public List<Meeting> getVideoOrderByStartTimeDesc() throws Throwable {
		return meetingDao.getVideoStartTimeOrderByDesc();
	}

	/**通过病历讨论的applicationId，查询一个Meeting
	 * 
	 * */
	@Override
	public Meeting viewMeetingByApplicationId(Integer applicationId) {
		return this.meetingDao.getMeetingByApplicationId(applicationId);
	}
	/**
	 * add by wangzhenglin
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询监护或监护信息
	 */
	@Override
	public List<ICUMonitor> searchICUMonitorList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable{
		return this.iCUMonitorDao.searchICUMonitorList(psm, iCUMonitorCriteriaVO);
	}
	/**
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询共同体和专家重症监护界面列表信息
	 */
	@Override
	public List<ICUMonitor> searchUnifiedICUMonitAppList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable{
		return this.iCUMonitorDao.searchUnifiedICUMonitAppList(psm, iCUMonitorCriteriaVO);
	}
	/**
	 * @param psm
	 * @param iCUMonitorCriteriaVO
	 * @return
	 * @throws Throwable
	 * 查询共同体和专家远程监护界面列表信息
	 */
	@Override
	public List<ICUVisitation> searchICUVisitList(PageSortModel psm, ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable{
		return this.icuVisitDao.searchICUICUVisitList(psm, iCUMonitorCriteriaVO);
	}
	/**
	 * 通过监护ID获取监护信息
	 * @param icuMonitorId
	 * @return
	 */
	@Override
	public ICUMonitor viewICUMonitor(
			Integer icuMonitorId) {
		// TODO Auto-generated method stub
		return this.iCUMonitorDao.get(icuMonitorId);
	}
	@Override
	public Meeting getMeetingByIcuMonitor(int icuMonitorId){
		return this.meetingDao.getMeetingByIcuMonitor(icuMonitorId);
	}
	/**
	 * add by wangzhenglin 
	 * 重症监护界面的删除功能
	 */
	@Override
	public int deleteIcuMonitor(String accPath,Integer appId)throws Throwable{
		ICUMonitor meetingApp = iCUMonitorDao.get(appId);
		if(meetingApp.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){//审核通过
			/**如果审核通过，需要删除meeting表的数据*/
			Meeting meeting = meetingDao.getMeetingByIcuMonitor(appId);
			summaryDao.delSummaryForManagerSaveData(meeting.getId());
			//satisfactionDao.delSatisfactionByMeetingId(meeting.getId());
			satisfactionManagerDao.delSatisfactionManagerByMeetingId(meeting.getId());
			//参会人不用删除，删除会议时，自动删除
			//meetingMemberDao.delMembersByMeetingId(meeting.getId());
			meetingDao.delete(meeting.getId());
		}
		//删除附件
		int accSize = accessoriesDao.deleteAccessoriesAndFileByIcuMonitor(accPath, appId);
		logger.debug("删除重症监护为" + appId + "附件" + accSize + "个");
		//删除科室
		int depsSize = iCUMonitorDao.delDeptsByMonitorId(appId);
		logger.debug("删除重症监护id为" + appId + "科室" + depsSize + "个");
		//删除ICD初诊判断
		int icdSize = icd10DicDao.delIcdByMonitorId(appId);
		logger.debug("删除重症监护id为" + appId + "ICD初诊判断" + icdSize + "个");
		//删除通知明细数据		
		int noticeDetailSize= icuNoticeDetailDao.deleteIcuNoticeDetailByIcuId(appId);
		logger.debug("删除重症监护id为" + appId + "通知信息" + noticeDetailSize+"条");
		//删除申请
		iCUMonitorDao.delete(appId);
		//病例提交人不用删除，删除申请时，自动删除
	//	meetingApplicationDao.delApplicationAuthorInfoByAppId(meetingApp.getAuthorInfo().getId());
		//删除患者信息不用删除，删除申请时，自动删除
	//	meetingApplicationDao.delApplicationPatientInfoByAppId(meetingApp.getPatientInfo().getId());
		return 0;
	}

	@Override
	public List<ICUVisitation> searchApplyICUVisitList(PageSortModel psm,
			ICUMonitorCriteriaVO iCUMonitorCriteriaVO) throws Throwable {
		// TODO Auto-generated method stub
		return this.icuVisitDao.searchApplyICUVisitList(psm, iCUMonitorCriteriaVO);
	}

	@Override
	public int deleteIcuVisit(Integer id) throws Throwable {
		ICUVisitation visit = icuVisitDao.get(id);
		if(visit.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){//审核通过
			Meeting meeting = getMeetingByIcuVisitId(id);
			meetingDao.delete(meeting.getId());
		}
		 icuVisitDao.delete(id);
		 return 0;
	}

	@Override
	public ICUVisitation getICUVisitById(Integer iCUVisitId) {
		// TODO Auto-generated method stub
		return this.icuVisitDao.get(iCUVisitId);
	}

	@Override
	public Meeting getMeetingByIcuVisitId(int icuVisitId) {
		// TODO Auto-generated method stub
		return  this.meetingDao.getMeetingByIcuVisit(icuVisitId);
	}
	 
}
