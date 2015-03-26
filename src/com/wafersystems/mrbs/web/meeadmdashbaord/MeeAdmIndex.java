package com.wafersystems.mrbs.web.meeadmdashbaord;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageData;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.AccessoriesService;
import com.wafersystems.mrbs.service.meeting.AccessoriesTypeService;
import com.wafersystems.mrbs.service.meeting.EvaluationService;
import com.wafersystems.mrbs.service.meeting.SatisfactionManagerService;
import com.wafersystems.mrbs.service.meeting.SatisfactionService;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.AccessoriesType;
import com.wafersystems.mrbs.vo.meeting.Evaluation;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;
import com.wafersystems.mrbs.vo.meeting.SatisfactionManager;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.ManagementOfAccessoriesVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingApplicationCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.VideoMeetingAppCriteriaVO;
import com.wafersystems.util.StrUtil;
@Controller
@RequestMapping(value = "/meeadIndex")
public class MeeAdmIndex {

	private MeeAdmDbdService meeAdmDbdService;
	private EvaluationService evaluationService;
	private SatisfactionManagerService satisfactionManagerService;
	private SatisfactionService satisfactionService;
	private DepartmentService departmentService;
	private UserService userService;
	private AccessoriesTypeService accessoriesTypeService;
	private AccessoriesService accessoriesService;
	private static final Logger logger = LoggerFactory.getLogger(MeeAdmIndex.class);
	
	
	/**
	 * 电子病历弹出页面
	 */
	@RequestMapping(value="/platformIntegrationEMRQuery")
	public String platformIntegrationEMR(Model model,HttpServletRequest request,HttpServletResponse response) {

		return "meetingadmin/platformIntegrationEMR";
	}
	
	/**审批远程会诊查询页面*/
	@RequestMapping(value="/teleconferenceQuery")
	public String teleconferenceQuery(HttpServletRequest request,Model model)
	{
/*		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);*/
		return "meetingadmin/teleconferenceQuery";
	}
	
	/**审批远程会诊展现页面
	 * @throws Throwable */
	@RequestMapping(value="/teleconferenceList")
	public String teleconferenceList(HttpServletRequest request, Model model) throws Throwable
	{
		MeetingApplicationCriteriaVO meetingApplicationCriteriaVO = new MeetingApplicationCriteriaVO();
		meetingApplicationCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		meetingApplicationCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		meetingApplicationCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		meetingApplicationCriteriaVO.setMeetingType(request.getParameter("meetingType"));
		meetingApplicationCriteriaVO.setRequesterId(request.getParameter("requesterId"));
		meetingApplicationCriteriaVO.setState(request.getParameter("state"));
		List<MeetingApplication> datas = meeAdmDbdService.searchMeetingAppList(new PageSortModel(request),meetingApplicationCriteriaVO);
		String requestParameter = "expectedTimeStart="+request.getParameter("expectedTimeStart")+"&expectedTimeEnd="+request.getParameter("expectedTimeEnd")+
		"&meetingType.id="+request.getParameter("meetingType.id")+"&state="+request.getParameter("state");
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);
		model.addAttribute("currDate", new Date());
	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
	    model.addAttribute("MEETING_LEVEL_EMERGENCY",GlobalConstent.MEETING_LEVEL_EMERGENCY);
	    model.addAttribute("MEETING_LEVEL_COMMON",GlobalConstent.MEETING_LEVEL_COMMON);
		return "meetingadmin/teleconferenceList";
	}
	
	/**审批视频讲座查询页面*/
	@RequestMapping(value="/videoLecturesQuery")
	public String videoLecturesQuery(HttpServletRequest request,Model model)
	{
/*		
		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);*/
	    
		return "meetingadmin/videoLecturesQuery";
	}
	
	/**审批视频讲座展现页面
	 * @throws Throwable */
	@RequestMapping(value="/videoLecturesList")
	public String videoLecturesList(HttpServletRequest request, Model model) throws Throwable
	{
		VideoMeetingAppCriteriaVO videoMeetingAppCriteriaVO = new VideoMeetingAppCriteriaVO();
		videoMeetingAppCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		videoMeetingAppCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		videoMeetingAppCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		videoMeetingAppCriteriaVO.setDeptName(request.getParameter("deptName"));
		videoMeetingAppCriteriaVO.setUserId(request.getParameter("userId"));
		videoMeetingAppCriteriaVO.setVideoApplicationPurpose(request.getParameter("videoApplicationPurpose"));
		videoMeetingAppCriteriaVO.setState(request.getParameter("state"));
		videoMeetingAppCriteriaVO.setRequesterId(request.getParameter("requesterId"));
		List<VideoMeetingApp> datas = meeAdmDbdService.searchVedioMeetingAppList(new PageSortModel(request),videoMeetingAppCriteriaVO);
		String requestParameter = "expectedTimeStart="+request.getParameter("expectedTimeStart")+"&expectedTimeEnd="+request.getParameter("expectedTimeEnd")+
									/*"&videoApplicationPurpose.id="+request.getParameter("videoApplicationPurpose.id")+*/"&state="+request.getParameter("state")+
									"&deptName.id="+request.getParameter("deptName.id")+"&userName.userId="+request.getParameter("userName.userId");
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);
		model.addAttribute("currDate", new Date());
		
	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
		return "meetingadmin/videoLecturesList";
	}

	/**
	 * 会诊评价
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/teleconferenceEvaluationQuery")
	public String teleconferenceEvaluationQuery(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			logger.debug("Enter MeeAdmIndex.teleconferenceEvaluationQuery");
//			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
//			List<Meeting> meetinglist = meeAdmDbdService.getOverMeeting(user,GlobalConstent.APPLICATION_TYPE_TELECONSULTATION);
//			model.addAttribute("meeting_state_pending",meetinglist);
			return "meetingadmin/teleconferenceEvaluationQuery";
		} catch (Exception e) {
			response.getWriter().write("fail");
			logger.error("Error MeeAdmIndex.teleconferenceEvaluationQuery",e);
		} 
		return null;
		
	}
	/**
	 * 附件管理的查询
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/managementOfattachment")
	public String managementOfattachment(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			logger.debug("Enter MeeAdmIndex.managementOfattachment");
			List<AccessoriesType> accessoriesTypes = accessoriesTypeService.getAllAccessoriesTypes();
			request.setAttribute("accessoriesTypes", accessoriesTypes);
			return "meetingadmin/managementOfAttachmentQuery";
		} catch (Exception e) {
			response.getWriter().write("fail");
			logger.error("Error MeeAdmIndex.managementOfattachment",e);
		} 
		return null;
		
	}
	/**
	 * 附件管理的展现
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/managementOfAttachmentList")
	public String managementOfAttachmentList(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			logger.debug("Enter MeeAdmIndex.managementOfAttachmentList");
			ManagementOfAccessoriesVO managementOfAccessoriesVo = new ManagementOfAccessoriesVO();
			managementOfAccessoriesVo.setAttachmentAttribution(request.getParameter("attachmentAttribution"));//获得附件归属
			managementOfAccessoriesVo.setAccessoriesName(request.getParameter("accessoriesName"));//获得附件名称
			managementOfAccessoriesVo.setAccessoriesType(request.getParameter("accessoriesType"));//获得附件类型
			managementOfAccessoriesVo.setAttachmentUploadPerson(request.getParameter("attachmentUploadPerson"));//获得附件上传人
			//managementOfAccessoriesVo.setManualInput(request.getParameter("manualInput"));
			UserInfo userinfo = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			PageData<Accessories> accessories = accessoriesService.getAllAccessories(new PageSortModel(request),managementOfAccessoriesVo,userinfo);
			request.setAttribute("report_data", accessories);
 		    String requestParameter = "attachmentAttribution="+request.getParameter("attachmentAttribution")+"&accessoriesName="+request.getParameter("accessoriesName")+
			"&accessoriesType="+request.getParameter("accessoriesType")+"&attachmentUploadPerson="+request.getParameter("attachmentUploadPerson")+"&manualInput="+request.getParameter("manualInput");
		    model.addAttribute("requestParameter",requestParameter);
		    return "meetingadmin/managementOfAttachmentList";
		} catch (Throwable e) {
			response.getWriter().write("fail");
			logger.error("Error MeeAdmIndex.managementOfAttachmentList",e);
		}
		return null;
		
	}
	/**
	 * 会诊评价
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/teleconferenceEvaluationList")
	public String teleconferenceEvaluationList(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			logger.debug("Enter MeeAdmIndex.teleconferenceEvaluationList");
			MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
			meetingCriteriaVO.setMeetingStartTime(request.getParameter("startTime"));
			meetingCriteriaVO.setMeetingEndTime(request.getParameter("endTime"));
			meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
			meetingCriteriaVO.setState(request.getParameter("state"));
			meetingCriteriaVO.setRequesterUserName(request.getParameter("requesterUserName"));
			meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_TELECONSULTATION));
		    List<Meeting> meetinglist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
		    request.setAttribute("report_data", meetinglist);
		    String requestParameter = "startTime="+request.getParameter("startTime")+"&meetingType="+request.getParameter("meetingType")+
										"&state="+request.getParameter("state")+"&requesterUserName="+request.getParameter("requesterUserName");
		    model.addAttribute("requestParameter",requestParameter);
			return "meetingadmin/teleconferenceEvaluationList";
		} catch (Throwable e) {
			response.getWriter().write("fail");
			logger.error("Error MeeAdmIndex.teleconferenceEvaluationList",e);
		}
		return null;
		
	}
	
	/**
	 * 讲座评价查询画面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/videoLecturesEvaluationQuery")
	public String videoLecturesEvaluationQuery(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			logger.debug("Enter MeeAdmIndex.teleconferenceEvaluationQuery");
//			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
//			List<Meeting> meetinglist = meeAdmDbdService.getOverMeeting(user,GlobalConstent.APPLICATION_TYPE_TELECONSULTATION);
//			model.addAttribute("meeting_state_pending",meetinglist);
			return "meetingadmin/videoLecturesEvaluationQuery";
		} catch (Exception e) {
			response.getWriter().write("fail");
			logger.error("Error MeeAdmIndex.videoLecturesEvaluationQuery",e);
		} 
		return null;
		
	}
	
	/**
	 * 讲座评价展现画面
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/videoLecturesEvaluationList")
	public String videoLecturesEvaluationList(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
		try {
			logger.debug("Enter MeeAdmIndex.teleconferenceEvaluationList");
			MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
			meetingCriteriaVO.setMeetingStartTime(request.getParameter("startTime"));
			meetingCriteriaVO.setMeetingEndTime(request.getParameter("endTime"));
			meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
			meetingCriteriaVO.setState(request.getParameter("state"));
			meetingCriteriaVO.setDepartmentName(request.getParameter("departmentName"));
			meetingCriteriaVO.setMainUserId(request.getParameter("mainUserId"));
			meetingCriteriaVO.setPurposeName(request.getParameter("purposeName"));
			meetingCriteriaVO.setRequesterUserName(request.getParameter("requesterUserName"));
			meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES));
		    List<Meeting> meetinglist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
		    request.setAttribute("report_data", meetinglist);
		    String requestParameter = "startTime="+request.getParameter("startTime")+"&state="+request.getParameter("state")+
										"&departmentName="+request.getParameter("departmentName")+"&mainUserId="+request.getParameter("mainUserId")+
										"&purposeName="+request.getParameter("purposeName")+"&requesterUserName="+request.getParameter("requesterUserName");
		    model.addAttribute("requestParameter",requestParameter);
			return "meetingadmin/videoLecturesEvaluationList";
		} catch (Throwable e) {
			response.getWriter().write("fail");
			logger.error("Error MeeAdmIndex.videoLecturesEvaluationList",e);
		}
		return null;
		
	}
	
	@RequestMapping("/searchDept")
	@ResponseBody
	public List<AutoCompleteItem> searchDept(String term, HttpServletResponse response) throws Throwable{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<AutoCompleteItem> list = departmentService.getDeptListByDeptName(term);
		if(list == null || list.size() == 0){
			list = Arrays.asList(new AutoCompleteItem("无法匹配:" + term, "-1"));
		}
		return list;
	}
	
	@RequestMapping("/searchUser")
	@ResponseBody
	public List<AutoCompleteItem> searchUser(String term, HttpServletResponse response) throws Throwable{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<AutoCompleteItem> list = userService.getUserListByUserName(term);
		if(list == null || list.size() == 0){
			list = Arrays.asList(new AutoCompleteItem("无法匹配:" + term, "-1"));
		}
		return list;
	}
	
//	/**
//	 * 讲座评价
//	 * @param model
//	 * @param request
//	 * @param response
//	 * @return
//	 * @throws IOException
//	 */
//	@RequestMapping(value = "/lectureEvaluation")
//	public String lectureEvaluation(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
//		try {
//			logger.debug("Enter MeeAdmIndex.lectureEvaluation");
//			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
//			List<Meeting> meetinglist = meeAdmDbdService.getOverMeeting(user,GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
//			model.addAttribute("meeting_state_pending",meetinglist);
//			return "meetingadmin/evaluation";
//		} catch (Exception e) {
//			response.getWriter().write("fail");
//			logger.error("Error MeeAdmIndex.lectureEvaluation",e);
//		} 
//		return null;
//	}
	
	/**显示管理员评价页面*/
	@RequestMapping(value="/meetOpintion")
	public String meetingOpintion(HttpSession session, HttpServletRequest request){	
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		try {
			logger.debug("Enter MeeAdmIndex.meetingOpintion");
			String meetingid=request.getParameter("opintionmeetingid");
			int id=0;
			if(!StrUtil.isEmptyStr(meetingid)){
				id=Integer.parseInt(meetingid);
				Meeting view= meeAdmDbdService.viewMeeting(id);
				UnifiedUserType appUserType = view.getCreator().getUserType();//得到申请者的用户类型
				Set<MeetingMember> appMembers = view.getMembers();//获得所有参会成员
				
				//获得会诊管理员的评价
				List<Evaluation> evaluation=evaluationService.getEvaluationListByuserTypeValue(GlobalConstent.USER_TYPE_MEETING_ADMIN);
				SatisfactionManager sf = new SatisfactionManager();
				Object[] queryParams = new Object[2];
				queryParams[0] = Integer.parseInt(meetingid);
				queryParams[1] = ((UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION)).getUserId();
			    sf =  satisfactionManagerService.getSatisfactionManager(queryParams);
			    Satisfaction satisfaction = satisfactionService.getSatisfaction(queryParams);
			    
			    //获得申请者的评价
			    //List<Evaluation> applicationEvaluation = evaluationService.getEvaluationList(appUserType);
			    List<Evaluation> applicationEvaluation = new ArrayList<Evaluation>();
			    List<Satisfaction> expertSatisfactionList = new ArrayList<Satisfaction>();
			    List<Satisfaction> unifiedSatisfactionList = new ArrayList<Satisfaction>();
			    for(MeetingMember meetingMember :appMembers){
			    	Short invitedAttendState = meetingMember.getAttendState();
			    	if(invitedAttendState == 2 && view.getState() == 3){
				    	Short attendMeetingUserType = meetingMember.getMember().getUserType().getValue();
				    	if(attendMeetingUserType == 4){
					    	Object[] meetingMemberQueryParams = new Object[2];
					    	meetingMemberQueryParams[0] = Integer.parseInt(meetingid);
					    	meetingMemberQueryParams[1] = meetingMember.getMember().getUserId();
						    Satisfaction expertSatisfaction=satisfactionService.getSatisfaction(meetingMemberQueryParams);
						    if(expertSatisfaction != null){
							    expertSatisfactionList.add(expertSatisfaction);
						    }
				    	}
				    	if(attendMeetingUserType == 5){
					    	Object[] meetingMemberQueryParams = new Object[2];
					    	meetingMemberQueryParams[0] = Integer.parseInt(meetingid);
					    	meetingMemberQueryParams[1] = meetingMember.getMember().getUserId();
						    Satisfaction unifiedSatisfaction=satisfactionService.getSatisfaction(meetingMemberQueryParams);
						    if(unifiedSatisfaction != null){
							    unifiedSatisfactionList.add(unifiedSatisfaction);
						    }	
						    applicationEvaluation = evaluationService.getEvaluationList(meetingMember.getMember().getUserType());
				    	}
			    	}
			    }
				request.setAttribute("meeting", view);//得到该病历讨论
				request.setAttribute("evallist", evaluation);//会诊管理员的评价
			    request.setAttribute("userType", appUserType);//申请改病历讨论的用户类型
			    request.setAttribute("satisfactionManager", sf);
			    request.setAttribute("satisfaction", satisfaction);
			    request.setAttribute("expertSatisfactionList", expertSatisfactionList);
			    request.setAttribute("unifiedSatisfactionList", unifiedSatisfactionList);
			    request.setAttribute("applicationEvaluation", applicationEvaluation);//申请者的评价
			    request.setAttribute("APPLICATION_TYPE_TELECONSULTATION",GlobalConstent.APPLICATION_TYPE_TELECONSULTATION);
			    request.setAttribute("APPLICATION_TYPE_VIDEOLECTURES",GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
			    request.setAttribute("appMembers", appMembers);//获得所有参会成员
			}
		    return "meetingadmin/meetOpintion_manager";
		} catch (Exception e) {
			logger.error("Error MeeAdmIndex.meetingOpintion",e);
		} 
		return null;
		
	}
	/**
	 * add bu wangzhenglin 
	 * @param request
	 * @param model
	 * @return
	 * 重症监护跳转---会议管理
	 */
	@RequestMapping(value="/icuMonitQuery")
	public String icuMonitQuery(HttpServletRequest request,Model model)
	{
/*		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);*/
		return "meetingadmin/icuMonitQuery";
	}
	
	/**
	 * add by wangzhenglin 
	 * @param request
	 * @param model
	 * @return
	 * @throws Throwable
	 * 重症监护审核查询页面--会议管理员
	 */
	@RequestMapping(value="/icuMonitList")
	public String icuMonitList(HttpServletRequest request, Model model) throws Throwable
	{
		ICUMonitorCriteriaVO iCUMonitorCriteriaVO = new ICUMonitorCriteriaVO();
		iCUMonitorCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		iCUMonitorCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		iCUMonitorCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		iCUMonitorCriteriaVO.setMeetingType(request.getParameter("meetingType"));
		iCUMonitorCriteriaVO.setRequesterId(request.getParameter("requesterId"));
		iCUMonitorCriteriaVO.setState(request.getParameter("state"));
		List<ICUMonitor> datas = meeAdmDbdService.searchICUMonitorList(new PageSortModel(request),iCUMonitorCriteriaVO);
		String requestParameter = "expectedTimeStart="+request.getParameter("expectedTimeStart")+"&expectedTimeEnd="+request.getParameter("expectedTimeEnd")+
		"&meetingType.id="+request.getParameter("meetingType.id")+"&state="+request.getParameter("state");
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);
		model.addAttribute("currDate", new Date());
	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
	    model.addAttribute("MEETING_LEVEL_EMERGENCY",GlobalConstent.MEETING_LEVEL_EMERGENCY);
	    model.addAttribute("MEETING_LEVEL_COMMON",GlobalConstent.MEETING_LEVEL_COMMON);
		return "meetingadmin/icuMonitList";
	}
	
	
	
	/**
	 * 跳转远程探视审核页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/icuVisitQuery")
	public String icuVisitQuery(HttpServletRequest request,Model model){
 		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED); 
		return "meetingadmin/icuVisitQuery";
	}
	/**
	 * @param request
	 * @param model
	 * @return
	 * @throws Throwable
	 * 远程探视审核查询页面--会议管理员
	 */
	@RequestMapping(value="/icuVisitList")
	public String icuVisitList(HttpServletRequest request, Model model) throws Throwable
	{
		ICUMonitorCriteriaVO iCUMonitorCriteriaVO = new ICUMonitorCriteriaVO();
		iCUMonitorCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		iCUMonitorCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		iCUMonitorCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		iCUMonitorCriteriaVO.setRequesterId(request.getParameter("requesterId"));
		iCUMonitorCriteriaVO.setState(request.getParameter("state"));
		List<ICUVisitation> datas = meeAdmDbdService.searchApplyICUVisitList(new PageSortModel(request),iCUMonitorCriteriaVO);
		String requestParameter = "expectedTimeStart="+request.getParameter("expectedTimeStart")+"&expectedTimeEnd="+request.getParameter("expectedTimeEnd")+
		"&state="+request.getParameter("state");
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);
		model.addAttribute("currDate", new Date());
	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
	    model.addAttribute("MEETING_LEVEL_EMERGENCY",GlobalConstent.MEETING_LEVEL_EMERGENCY);
	    model.addAttribute("MEETING_LEVEL_COMMON",GlobalConstent.MEETING_LEVEL_COMMON);
		return "meetingadmin/icuVisitList";
	}
	
	
	@Resource
	public void setMeeAdmDbdService(MeeAdmDbdService meeAdmDbdService) {
		this.meeAdmDbdService = meeAdmDbdService;
	}
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
	
	@Resource
	public void setSatisfactionMainService(
			SatisfactionManagerService satisfactionManagerService) {
		this.satisfactionManagerService = satisfactionManagerService;
	}
	
	@Resource(type = EvaluationService.class)
	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}
    
	@Resource(type = DepartmentService.class)
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}
	
	@Resource(type = UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource(type = AccessoriesTypeService.class)
	public void setAccessoriesTypeService(AccessoriesTypeService accessoriesTypeService){
		this.accessoriesTypeService = accessoriesTypeService;
	}
	
	@Resource(type = AccessoriesService.class)
	public void setAccessoriesService(AccessoriesService accessoriesService) {
		this.accessoriesService = accessoriesService;
	}

	@Resource(type = SatisfactionService.class)
	public void setSatisfactionService(SatisfactionService satisfactionService) {
		this.satisfactionService = satisfactionService;
	}
	
	

}
