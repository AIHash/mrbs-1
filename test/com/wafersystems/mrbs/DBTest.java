package com.wafersystems.mrbs;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.wafersystems.mrbs.service.SystemOperationLogService;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.service.meeting.AccessoriesService;
import com.wafersystems.mrbs.service.meeting.AccessoriesTypeService;
import com.wafersystems.mrbs.service.meeting.ApplicantService;
import com.wafersystems.mrbs.service.meeting.ApplicationOperationStepService;
import com.wafersystems.mrbs.service.meeting.EvaluationService;
import com.wafersystems.mrbs.service.meeting.MeetingApplicationService;
import com.wafersystems.mrbs.service.meeting.MeetingLevelService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.service.meeting.MeetingRoomService;
import com.wafersystems.mrbs.service.meeting.MeetingTypeService;
import com.wafersystems.mrbs.service.meeting.SummaryService;
import com.wafersystems.mrbs.service.meeting.VideoRECService;
import com.wafersystems.mrbs.service.notice.NoticeService;
import com.wafersystems.mrbs.service.notice.NoticeTypeService;
import com.wafersystems.mrbs.service.param.ParamPackageService;
import com.wafersystems.mrbs.service.param.UnifiedParameterService;
import com.wafersystems.mrbs.service.right.RoleService;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.service.user.UserGroupService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.service.user.UserTypeService;
import com.wafersystems.mrbs.vo.SystemOperationLog;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;
import com.wafersystems.mrbs.vo.meeting.Applicant;
import com.wafersystems.mrbs.vo.meeting.ApplicationOperationStep;
import com.wafersystems.mrbs.vo.meeting.Evaluation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingLevel;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingRoom;
import com.wafersystems.mrbs.vo.meeting.MeetingType;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;
import com.wafersystems.mrbs.vo.meeting.Summary;
import com.wafersystems.mrbs.vo.meeting.VideoREC;
import com.wafersystems.mrbs.vo.notice.NoticeType;
import com.wafersystems.mrbs.vo.notice.UnifiedNotice;
import com.wafersystems.mrbs.vo.param.ParamPackage;
import com.wafersystems.mrbs.vo.param.UnifiedParameter;
import com.wafersystems.mrbs.vo.right.Role;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;
import com.wafersystems.mrbs.vo.user.UserGroup;
import com.wafersystems.mrbs.vo.user.UserInfo;

public class DBTest extends TestCase{

	private MeetingApplicationService meetingApplicationService;
	private MeetingLevelService meetingLevelService;
	private DepartmentService departmentService;
	private AccessoriesService accessoriesService;
	private ApplicantService applicantService;
	private MeetingTypeService meetingTypeService;
	private UserService userService;
	private UserTypeService userTypeService;
	private AccessoriesTypeService accessoriesTypeService;
	private MeetingRoomService meetingRoomService;
	private NoticeTypeService noticeTypeService;
	private NoticeService noticeService;
	private RoleService roleService;
	private MeetingMemberService meetingMemberService;
	private UserGroupService userGroupService;
	private EvaluationService evaluationService;
	private SummaryService summaryService;
	private VideoRECService videoRECService;
	private ApplicationOperationStepService applicationOperationStepService;
	private SystemServiceLogService systemServiceLogService;
	private SystemOperationLogService systemOperationLogService;
	private ParamPackageService paramPackageService;
	private UnifiedParameterService unifiedParameterService;
	
	
	/**
	 * Vo
	 */
	UnifiedUserType userType;
	Department department;
	AccessoriesType accessoriesType;
	MeetingLevel meetingLevel;
	MeetingRoom meetingRoom;
	MeetingType meetingType;
	NoticeType noticeType;
	Role role;
	UnifiedNotice unifiedNotice;
	UserInfo userInfo;
	Accessories accessories;
	Applicant applicant;
	MeetingApplication meetingApplication;
	Meeting meeting;
	MeetingMember meetingMember;
	UserGroup userGroup;
	Evaluation evaluation;
	Satisfaction satisfaction;
	Summary summary;
	VideoREC videoREC;
	ApplicationOperationStep applicationOperationStep;
	SystemServiceLog systemServiceLog;
	SystemOperationLog systemOperationLog;
	ParamPackage paramPackage;
	UnifiedParameter unifiedParameter;
	
	@Override
	protected void setUp() throws Exception {
		ApplicationContext context = new FileSystemXmlApplicationContext("./WebContent/WEB-INF/spring/root-context.xml");
		meetingLevelService = context.getBean(MeetingLevelService.class);
		meetingApplicationService = context.getBean(MeetingApplicationService.class);
		departmentService = context.getBean(DepartmentService.class);
		accessoriesService = context.getBean(AccessoriesService.class);
		accessoriesTypeService = context.getBean(AccessoriesTypeService.class);
		userService = context.getBean(UserService.class);
		meetingTypeService = context.getBean(MeetingTypeService.class);
		applicantService = context.getBean(ApplicantService.class);
		userTypeService = context.getBean(UserTypeService.class);
		meetingRoomService = context.getBean(MeetingRoomService.class);
		noticeTypeService = context.getBean(NoticeTypeService.class);
		noticeService = context.getBean(NoticeService.class);
		roleService = context.getBean(RoleService.class);
		meetingMemberService = context.getBean(MeetingMemberService.class);
		userGroupService = context.getBean(UserGroupService.class);
		summaryService = context.getBean(SummaryService.class);
		videoRECService = context.getBean(VideoRECService.class);
		applicationOperationStepService = context.getBean(ApplicationOperationStepService.class);
		systemServiceLogService = context.getBean(SystemServiceLogService.class);
		systemOperationLogService = context.getBean(SystemOperationLogService.class);
		evaluationService = context.getBean(EvaluationService.class);
		paramPackageService = context.getBean(ParamPackageService.class);
		unifiedParameterService = context.getBean(UnifiedParameterService.class);
	}
	
	@Test
	public void testUserTypeAdd(){
		if(userType == null){
//			userType = new UserType();
//			userType.setName("AAAA");
//			userType.setValue((short)8);
//			userTypeService.saveUserType(userType);
			userTypeService.getUserTypeByValue(new Short("5"));
		}
	}
	
	@Test
	public void testAccessoriesTypeAdd(){
		if(accessoriesType == null){
			accessoriesType = new AccessoriesType();
			accessoriesType.setName("检查");
			accessoriesType.setValue((short)1);
			accessoriesTypeService.saveAccessoriesType(accessoriesType);
		}
	}
	
	@Test
	public void testMeetingTypeAdd(){
		if(meetingType == null){
			meetingType = new MeetingType();
			meetingType.setName("远程会诊");
			meetingType.setValue((short)1);
			meetingTypeService.saveMeetingType(meetingType);
		}
	}
	
	@Test
	public void testNoticeTypeAdd(){
		if(noticeType == null){
			noticeType = new NoticeType();
			noticeType.setName("短信通知");
			noticeType.setValue((short)1);
			noticeTypeService.saveNoticeType(noticeType);
		}
	}
	
	@Test
	public void testMeetingLevelAdd(){
		if(meetingLevel == null){
			meetingLevel = new MeetingLevel();
			meetingLevel.setName("指定专家");
			meetingLevel.setValue((short)1);
			meetingLevel.setRemark("云南已出远程会诊");
			meetingLevelService.saveMeetingLevel(meetingLevel);
		}
	}
	
	@Test
	public void testEvaluationAdd(){
		if(evaluation == null){
			evaluation = new Evaluation();
			evaluation.setName("满意");
//			evaluation.setRemark("90分");
//			evaluation.setValue((short)5);
			evaluationService.saveEvaluation(evaluation);
		}
	}
	
	@Test
	public void testRoleAdd(){
		if(role == null){
			role = new Role();
			role.setName("Default");
			role.setType(0);
			role.setRemark("默认角色配置");
			roleService.saveRole(role);
		}
	}
	
	@Test
	public void testDepartMentAdd(){
		try{
		if(department == null){
			department = new Department();
			department.setName("人民医院");
			department.setDeptcode("-1");
			department.setRemark("北京大学人民医院");
			departmentService.saveDepartment(department);
			Department deptsub = new Department();
			deptsub.setDeptcode("001");
			deptsub.setName("心内科");
			Set<Department> depts = new HashSet<Department>();
			depts.add(deptsub);
			deptsub.setParentDetp(department);
			department.setSubDepts(depts);
			departmentService.saveDepartment(deptsub);
		}
		}catch(Exception e){}
	}
	
	@Test
	public void testMeetingRoomAdd(){
		try {
			if(meetingRoom == null){
				meetingRoom = new MeetingRoom();
				meetingRoom.setMailAddress("guojh@wafersystems.com");
				meetingRoom.setMaxHours((short)4);
				meetingRoom.setName("什邡人民医院会诊");
				meetingRoom.setNeedConfirm(false);
				meetingRoom.setPurpose("远程会诊");
				meetingRoom.setRemark("可视频会议");
				meetingRoom.setSeats((short)16);
				meetingRoom.setSize("4*4");
				meetingRoom.setSn("A101");
				meetingRoom.setState((short)0);
				meetingRoomService.saveMeetingRoom(meetingRoom);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	/**
	 * pre - testNoticeTypeAdd()
	 */
	@Test
	public void testUnifiedNoticeAdd(){
		if(unifiedNotice == null){
			testNoticeTypeAdd();	
			unifiedNotice = new UnifiedNotice();
			unifiedNotice.setAcceptUrl("www.baidu.com");
			unifiedNotice.setBusinessId("00123992");
			unifiedNotice.setDetailUrl("www.baidu.com");
			unifiedNotice.setDocUrl("www.baidu.com");
			unifiedNotice.setMessage("我不像加班");
			unifiedNotice.setReceiver("guojh@wafersystems.com");
			unifiedNotice.setRejectUrl("www.baidu.com");
			unifiedNotice.setResendTimes(0);
			unifiedNotice.setSendTime(new Date());
			unifiedNotice.setState((short)0);
			unifiedNotice.setTitle("我要杀");
			unifiedNotice.setType(noticeType);
			noticeService.saveNotice(unifiedNotice);
		}
	}
	
	/**
	 * pre - testDepartMentAdd()
	 * 	   - testRoleAdd()
	 * 	   - testUserTypeAdd()
	 */
	@Test
	public void testUserInfoAdd(){
		try{
			if(userInfo == null){
				testDepartMentAdd();
				testRoleAdd();
				testUserTypeAdd();
				userInfo = new UserInfo();
				userInfo.setCreateTime(new Date());
				userInfo.setDeptId(department);
				userInfo.setGender((short)0);
				userInfo.setMail("guojh@wafersystems.com");
				userInfo.setMobile("1234564746");
				userInfo.setName("王雷");
				userInfo.setPassword("111111");
				userInfo.setRole(role);
				userInfo.setState((short)0);
				userInfo.setUserId("destroyed");
				userInfo.setUserType(userType);
				userInfo.setVideoPoint("192.168.0.2");
				userService.saveUser(userInfo);
				UserInfo userInfo2 = new UserInfo();
				userInfo2.setCreateTime(new Date());
				userInfo2.setDeptId(department);
				userInfo2.setCreator(userInfo);
				userInfo2.setGender((short)0);
				userInfo2.setMail("guojh2@wafersystems.com");
				userInfo2.setMobile("1234564746");
				userInfo2.setName("王雷2");
				userInfo2.setPassword("000000");
				userInfo2.setRole(role);
				userInfo2.setState((short)0);
				userInfo2.setUserId("destroyed2");
				userInfo2.setUserType(userType);
				userInfo2.setVideoPoint("192.168.0.3");
				userService.saveUser(userInfo2);
			}
		}catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * pre - testUserInfoAdd()
	 *     - testAccessoriesTypeAdd()
	 */
	@Test
	public void testAccessoriesAdd(){
		if(accessories == null){
			testUserInfoAdd();
			testAccessoriesTypeAdd();
			accessories = new Accessories();
			accessories.setName("骨科诊疗报告");
			accessories.setOwner(userInfo);
			accessories.setPath("E:/patch/ev.doc");
			accessories.setRemark("no saved");
			accessories.setType(accessoriesType);
			accessoriesService.saveAccessories(accessories);
		}
	}
	
	/**
	 * pre - testUserInfoAdd()
	 */
	@Test
	public void testApplicantAdd(){
		if(applicant == null){
			testUserInfoAdd();
			applicant = new Applicant();
			applicant.setDepartment("心内科");
			applicant.setEmail("xxx@163.com");
			applicant.setMobile("13545673254");
			applicant.setName("张辉");
			applicant.setOwner(userInfo);
			applicant.setTelephone("0988324352");
			applicantService.saveApplicant(applicant);
		}
	}

	/**
	 * pre - testApplicantAdd()
	 * 	   - testMeetingLevelAdd()
	 * 	   - testMeetingTypeAdd()
	 * 	   - testAccessoriesAdd()
	 * 	   - testDepartMentAdd()
	 * 	   - testUserInfoAdd()
	 */
	@Test
	public void testMeetingApplicationAdd(){
		if(meetingApplication == null){
			testMeetingLevelAdd();
			testMeetingTypeAdd();
			testApplicantAdd();
			testAccessoriesAdd();
			testUserInfoAdd();
			testDepartMentAdd();
			meetingApplication = new MeetingApplication();
			Set<Accessories> accs = new HashSet<Accessories>();
			meetingApplication.setAccessories(accs);
			accs.add(accessories);
//			meetingApplication.setApplicant(applicant);
//			meetingApplication.setContent("需要远程会诊");
//			meetingApplication.setDepartment(department);
		//	meetingApplication.setEndTime(new Date());
			meetingApplication.setLevel(meetingLevel);
//			meetingApplication.setPorpose("提高满意度");
			meetingApplication.setRequester(userInfo);
		//	meetingApplication.setStartTime(new Date());
			meetingApplication.setState((short)0);
//			meetingApplication.setTitle("云南楚易申请远程会诊");
			meetingApplication.setMeetingType(meetingType);
			meetingApplicationService.saveMeetingApplication(meetingApplication);
		}
	}
	
	/**
	 * pre  - testMeetingRoomAdd()
	 * 		- testMeetingTypeAdd()
	 * 		- testUserInfoAdd()
	 * 		- testMeetingApplicationAdd()
	 * 		- testMeetingLevelAdd()
	 */
	@Test
	public void testMeetingAdd(){
		if(meeting == null){
			testMeetingRoomAdd();
			testMeetingTypeAdd();
			testUserInfoAdd();
			testMeetingApplicationAdd();
			testMeetingLevelAdd();
			meeting = new Meeting();
			meeting.setApplicationId(meetingApplication);
			meeting.setBeforeMin((short)30);
			meeting.setContent("已经开启远程会议");
			meeting.setCreator(userInfo);
			meeting.setEndTime(new Date());
			meeting.setHolder(userInfo);
			meeting.setLevel(meetingLevel);
			meeting.setMeetingRoomId(meetingRoom);
			meeting.setMeetingType(meetingType);
			meeting.setRemark("备注无");
			meeting.setStartTime(new Date());
			meeting.setState((short)0);
			meeting.setTitle("远程会议");
		}
	}
	
	/**
	 * pre  - testUserInfoAdd()
	 * 		- testMeetingAdd()
	 */
	@Test
	public void testMeetingMemberAdd(){
		if(meetingMember == null){
			testUserInfoAdd();
			testMeetingAdd();
			meetingMember = new MeetingMember();
			meetingMember.setAckTime(new Date());
			meetingMember.setAttendNo((short)8);
			meetingMember.setAttendState((short)0);
			meetingMember.setMeetingId(meeting);
			meetingMember.setMember(userInfo);
			meetingMemberService.saveMeetingMember(meetingMember);
		}
	}

	/**
	 * pre  - testUserInfoAdd()
	 */
	@Test
	public void testUserGroupAdd(){
		if(userGroup == null){
			testUserInfoAdd();
			userGroup = new UserGroup();
			userGroup.setName("培训组");
			userGroup.setRemark("北京大学人民医院培训项目组");
			Set<UserInfo> members = new HashSet<UserInfo>();
			members.add(userInfo);
			userGroup.setMembers(members);
			userGroupService.saveUserGroup(userGroup);
		}
	}
	
	/**
	 * pre  - testUserInfoAdd()
	 * 		- testMeetingAdd()
	 * 		- testEvaluationAdd()
	 */
	@Test
	public void testSatisfactionAdd(){
		if(satisfaction == null){
			testUserInfoAdd();
			testMeetingAdd();
			testEvaluationAdd();
			satisfaction = new Satisfaction();
//			satisfaction.setEvaluation(evaluation);
			satisfaction.setMeeting(meeting);
//			satisfaction.setProvider(userInfo);
//			satisfaction.setSuggestion("请提高工作质量");
		}
	}
	
	/**
	 * pre  - testUserInfoAdd()
	 * 		- testMeetingAdd()
	 * 		- testAccessoriesAdd()
	 */
	@Test
	public void testSummary(){
		if(summary == null){
			testUserInfoAdd();
			testMeetingAdd();
			testAccessoriesAdd();
			summary = new Summary();
			summary.setAccessories(accessories);
			summary.setContent("没有总结");
			summary.setHolder(userInfo);
			summary.setMeeting(meeting);
			summaryService.saveSummary(summary);
		}
	}
	
	/**
	 * pre  - testMeetingAdd()
	 */
	@Test
	public void testVideoREC(){
		if(videoREC == null){
			testMeetingAdd();
			videoREC = new VideoREC();
			videoREC.setMeeting(meeting);
			videoREC.setName(System.currentTimeMillis() + "");
			videoREC.setUrl("http://102.32.43.12/old.wav");
			videoRECService.saveVideoREC(videoREC);
		}
	}
	
	/**
	 * pre  - testUserInfoAdd()
	 * 		- testMeetingApplicationAdd()
	 */
	@Test
	public void testApplicationOperationStep(){
		if(applicationOperationStep == null){
			testUserInfoAdd();
			testMeetingApplicationAdd();
			applicationOperationStep = new ApplicationOperationStep();	
			applicationOperationStep.setApplication(meetingApplication);
			applicationOperationStep.setOwner(userInfo);
			applicationOperationStep.setReason("无条件拒绝");
			applicationOperationStep.setRequestId("20110403021300");
			applicationOperationStep.setState((short)0);
			applicationOperationStep.setStep(0);
			applicationOperationStepService.saveApplicationOperationStep(applicationOperationStep);
		}
	}
	
	@Test
	public void testSystemServiceLogAdd(){
		if(systemServiceLog == null){
			systemServiceLog = new SystemServiceLog();	
			systemServiceLog.setContent("会议自动开启");
			systemServiceLog.setCreateTime(new Date());
			systemServiceLog.setName("No12");
			systemServiceLog.setObjectId("No12");
			systemServiceLog.setResult((short)0);
			systemServiceLogService.saveSystemServiceLog(systemServiceLog);
		}
	}
	
	/**
	 * pre  - testUserInfoAdd()
	 */
	@Test
	public void testSystemOperationLogAdd(){
		try{
			if(systemOperationLog == null){
				testUserInfoAdd();
				systemOperationLog = new SystemOperationLog();
				systemOperationLog.setContent("删除用户");
				systemOperationLog.setLogDate(new Date());
				systemOperationLog.setModuleNo("001001");
				systemOperationLog.setType("AAAAA");
				systemOperationLog.setUser(userInfo);
				systemOperationLogService.saveSystemOperationLog(systemOperationLog);
			}
		}catch(Exception e){
			
		}
	}
	
	@Test
	public void testParamPackageAdd(){
		if(paramPackage == null){
			paramPackage = paramPackageService.getParamPackageById("SYSTEM");
		}
	}
	
	/**
	 * pre  - testParamPackageAdd()
	 */
	@Test
	public void testUnifiedParameter(){
		if(unifiedParameter == null){
			testParamPackageAdd();
			unifiedParameter = new UnifiedParameter();
			unifiedParameter.setPackageKey(paramPackage);
			unifiedParameter.setParamDESC("姚明");
			unifiedParameter.setParamKey("YAO");
			unifiedParameter.setValue("Y");
			unifiedParameterService.saveUnifiedParameter(unifiedParameter);
		}
	}
	
	@Test
	public void testAll(){
		testUserTypeAdd();
		testAccessoriesTypeAdd();
		testMeetingTypeAdd();
		testNoticeTypeAdd();
		testMeetingLevelAdd();
		testEvaluationAdd();
		testRoleAdd();
		testDepartMentAdd();
		testMeetingRoomAdd();
		testUnifiedNoticeAdd();
		testUserInfoAdd();
		testAccessoriesAdd();
		testApplicantAdd();
		testMeetingApplicationAdd();
		testMeetingAdd();
		testMeetingMemberAdd();
		testUserGroupAdd();
		testSatisfactionAdd();
		testSummary();
		testVideoREC();
		testApplicationOperationStep();
		testSystemServiceLogAdd();
		testSystemOperationLogAdd();
		testParamPackageAdd();
		testUnifiedParameter();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

}
