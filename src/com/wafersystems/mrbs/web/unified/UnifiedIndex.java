package com.wafersystems.mrbs.web.unified;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.service.meeting.SatisfactionService;
import com.wafersystems.mrbs.service.meeting.VideoApplicationPurposeService;
import com.wafersystems.mrbs.service.meeting.VideoMeetingService;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.ICUMonitorCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingApplicationCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.VideoMeetingAppCriteriaVO;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping(value="/unifiedindex")
public class UnifiedIndex {

	private static final Logger logger = LoggerFactory.getLogger(UnifiedIndex.class);
	private VideoMeetingService videoMeetingService;
	private MeeAdmDbdService meeAdmDbdService;
	private SatisfactionService satisfactionService;
	private MeetingMemberService meetingmemberervice;
	
	/**远程会诊申请信息查询页面*/
	@RequestMapping(value="/applyForConsultationQuery")
	public String applyForConsultationQuery(HttpServletRequest request,Model model)
	{
/*		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);*/
		return "unified/applyForConsultationQuery";
	}
	
	/**远程会诊申请信息展现页面
	 * @throws Throwable */
	@RequestMapping(value="/applyForConsultationList")
	public String applyForConsultationList(HttpServletRequest request, Model model) throws Throwable
	{
		UserInfo user=(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		MeetingApplicationCriteriaVO meetingApplicationCriteriaVO = new MeetingApplicationCriteriaVO();
		meetingApplicationCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		meetingApplicationCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		meetingApplicationCriteriaVO.setExpectedTime(request.getParameter("expectedTime"));
		meetingApplicationCriteriaVO.setApplicationTime(request.getParameter("applicationTime"));
		meetingApplicationCriteriaVO.setPatientName(request.getParameter("patientName"));
		meetingApplicationCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		meetingApplicationCriteriaVO.setMeetingType(request.getParameter("meetingType"));
		meetingApplicationCriteriaVO.setState(request.getParameter("state"));
		meetingApplicationCriteriaVO.setRequesterId(user.getUserId());
		List<MeetingApplication> datas = meeAdmDbdService.searchUnifiedMeetingAppList(new PageSortModel(request), meetingApplicationCriteriaVO);
		
		String requestParameter = "expectedTime="+request.getParameter("expectedTime")+"&applicationTime="+request.getParameter("applicationTime")+
									"&meetingType.id="+request.getParameter("meetingType.id")+"&state="+request.getParameter("state");
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);

	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
	    model.addAttribute("MEETING_LEVEL_EMERGENCY",GlobalConstent.MEETING_LEVEL_EMERGENCY);
	    model.addAttribute("MEETING_LEVEL_COMMON",GlobalConstent.MEETING_LEVEL_COMMON);
		return "unified/applyForConsultationList";
	}
	
	/**申请视频讲座查询页面*/
	@RequestMapping(value="/applyForLectureQuery")
	public String applyForLectureQuery(HttpServletRequest request,Model model)
	{
		
/*		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);*/
	    
		return "unified/applyForLectureQuery";
	}
	
	/**申请视频讲座展现页面
	 * @throws Throwable */
	@RequestMapping(value="/applyForLectureList")
	public String applyForLectureList(HttpServletRequest request, Model model) throws Throwable
	{
		UserInfo user=(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		VideoMeetingAppCriteriaVO videoMeetingAppCriteriaVO = new VideoMeetingAppCriteriaVO();
		videoMeetingAppCriteriaVO.setRequesterId(user.getUserId());
		videoMeetingAppCriteriaVO.setSuggestDate(request.getParameter("suggestDate"));
		videoMeetingAppCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		videoMeetingAppCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		videoMeetingAppCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		videoMeetingAppCriteriaVO.setAppDate(request.getParameter("appDate"));
		videoMeetingAppCriteriaVO.setDeptName(request.getParameter("deptName"));
		videoMeetingAppCriteriaVO.setUserId(request.getParameter("userId"));
		videoMeetingAppCriteriaVO.setVideoApplicationPurpose(request.getParameter("videoApplicationPurpose"));
		videoMeetingAppCriteriaVO.setState(request.getParameter("state"));
		List<VideoMeetingApp> datas = meeAdmDbdService.searchVedioMeetingAppList(new PageSortModel(request), videoMeetingAppCriteriaVO);
		String requestParameter = "suggestDate="+request.getParameter("suggestDate")+"&appDate="+request.getParameter("appDate")+
									"&videoApplicationPurpose.id="+request.getParameter("videoApplicationPurpose.id")+"&state="+request.getParameter("state")+
									"&deptName.id="+request.getParameter("deptName.id")+"&userName.userId="+request.getParameter("userName.userId");;
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);

	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
		return "unified/applyForLectureList";
	}
	
	/** 受邀会诊查询画面*/
	@RequestMapping(value="/invitedConsultationQuery")
	public String invitedConsultationQuery(HttpServletRequest request, Model model)
	{
		return "unified/invitedConsultationQuery";
	}
	
	
	// 接受邀请
	@RequestMapping(value="acceptedOrRefused")
	public String acceptedOrRefused(HttpSession session, HttpServletRequest request, Model model,HttpServletResponse response)throws Throwable {
		String meetingId=request.getParameter("meetingId");
		try {
			logger.debug("acceptedOrRefused, meetingId:" + meetingId);
			if(!StrUtil.isEmptyStr(meetingId)){
				String flag=request.getParameter("flag");
				UserInfo user=(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
				Meeting meetingview = null;
				if(!StrUtil.isEmptyStr(meetingId)) {
					int	id=Integer.parseInt(meetingId);
					meetingview = meeAdmDbdService.viewMeeting(id);
				}
				if(meetingview!=null) {
					MeetingMember meetingmember=new MeetingMember();
					for(Iterator<MeetingMember> it=meetingview.getMembers().iterator();it.hasNext();) {
					      meetingmember = it.next();
					      if(meetingmember.getMember().getUserId().equalsIgnoreCase(user.getUserId())) {
					    	   if("accept".equalsIgnoreCase(flag)) {
					    	      meetingmember.setAttendState(GlobalConstent.APPLICATION_STATE_ACCEPT);
					    	   }else {
					    		   meetingmember.setAttendState(GlobalConstent.APPLICATION_STATE_REFUSED);
					    	   }
					    	   meetingmemberervice.updateMeetingMember(meetingmember);
					    	  break;
					      }
					}
				}
			}
			response.getWriter().print("succ");
		} catch (Throwable e) {
			logger.debug("Execute acceptedOrRefused Error  meetingId:" + meetingId, e);
			response.getWriter().print("error");
		}
		return null;
	}
	
	/** 受邀会诊显示画面*/
	@RequestMapping(value="/invitedConsultationList")
	public String invitedConsultationList(HttpSession session,HttpServletRequest request,HttpServletResponse response, Model model) {
		try {
			logger.debug("Enter UnifiedIndex.invitedConsultationList");
			MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			meetingCriteriaVO.setAcceptUserId(user.getUserId());
			meetingCriteriaVO.setMeetingStartTime(request.getParameter("startTime"));
			meetingCriteriaVO.setMeetingEndTime(request.getParameter("endTime"));
			meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
			meetingCriteriaVO.setMeetingType(request.getParameter("meetingType"));
			meetingCriteriaVO.setState(request.getParameter("state"));
			meetingCriteriaVO.setInvitedState(request.getParameter("invitedState"));
			meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_TELECONSULTATION));
		    
			List<Meeting> invitelist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
			//设定会诊受邀状态
			MeetingMember meetingmember=null;
			for(Meeting meeting:invitelist){
				if(meeting.getMembers()!=null){
					for(Iterator<MeetingMember> it=meeting.getMembers().iterator();it.hasNext();){
						meetingmember = it.next();
						if(meetingmember.getMember().getUserId().equalsIgnoreCase(user.getUserId())){
							//暂时将beforeMin中放入会诊状态，只用于页面显示
							meeting.setBeforeMin(meetingmember.getAttendState());
						}
					 }
				}
			}
			request.setAttribute("report_data", invitelist);
		    String requestParameter = "startTime="+request.getParameter("startTime")+"&meetingType="+request.getParameter("meetingType")+
										"&invitedState="+request.getParameter("invitedState")+"&state="+request.getParameter("state");
		    model.addAttribute("requestParameter",requestParameter);
		    return "unified/invitedConsultationList";
		} catch (Throwable e) {
			logger.error("Error UnifiedIndex.invitedConsultationList",e);
		}
		return null;
	}
	
	/** 已安排会诊查询画面*/
	@RequestMapping(value="/arrangedConsultationQuery")
	public String arrangedConsultationQuery(HttpServletRequest request, Model model)
	{		
		return "unified/arrangedConsultationQuery";
	}
	
	/** 已安排会诊显示画面*/
	@RequestMapping(value="/arrangedConsultationList")
	public String arrangedConsultationList(HttpServletRequest request, Model model)
	{
		try {
			logger.debug("Enter UnifiedIndex.arrangedConsultationList");
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
			meetingCriteriaVO.setAcceptUserId(user.getUserId());
			meetingCriteriaVO.setMeetingStartTime(request.getParameter("meetingStartTime"));
			meetingCriteriaVO.setMeetingEndTime(request.getParameter("meetingEndTime"));
			meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
			meetingCriteriaVO.setMeetingType(request.getParameter("meetingType"));
			meetingCriteriaVO.setState(request.getParameter("state"));
			meetingCriteriaVO.setInvitedState(String.valueOf(GlobalConstent.APPLICATION_STATE_ACCEPT));
			meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_TELECONSULTATION));
			
			List<Meeting> invitelist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
			request.setAttribute("report_data", invitelist);
			
			String requestParameter = "meetingStartTime="+request.getParameter("meetingStartTime")+"&meetingEndTime="+request.getParameter("meetingEndTime")+
										"&meetingType="+request.getParameter("meetingType")+"&state="+request.getParameter("state");
			model.addAttribute("requestParameter",requestParameter);
			model.addAttribute("currUser",user);
			
			return "unified/arrangedConsultationList";
		} catch (Throwable e) {
			logger.error("Error UnifiedIndex.arrangedConsultationList",e);
		}
		return null;
	}
	
	/** 讲座安排查询画面*/
	@RequestMapping(value="/lectureArrangementQuery")
	public String lectureArrangementQuery(HttpServletRequest request, Model model)
	{
		return "unified/lectureArrangementQuery";
	}
	
	/** 讲座安排显示画面*/
	@RequestMapping(value="/lectureArrangementList")
	public String lectureArrangementList(HttpServletRequest request, Model model)
	{
		try {
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
			meetingCriteriaVO.setAcceptUserId(user.getUserId());
			meetingCriteriaVO.setMeetingStartTime(request.getParameter("startTime"));
			meetingCriteriaVO.setMeetingEndTime(request.getParameter("endTime"));
			meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
			meetingCriteriaVO.setState(request.getParameter("state"));
			meetingCriteriaVO.setDepartmentName(request.getParameter("departmentName"));
			meetingCriteriaVO.setMainUserId(request.getParameter("mainUserId"));
			//meetingCriteriaVO.setPurposeName(request.getParameter("purposeName"));
			meetingCriteriaVO.setInvitedState(request.getParameter("invitedState"));
			meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES));
		    
			List<Meeting> invitelist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
			//设定讲座受邀状态
			MeetingMember meetingmember=null;
			for(Meeting meeting:invitelist){
				if(meeting.getMembers()!=null){
					for(Iterator<MeetingMember> it=meeting.getMembers().iterator();it.hasNext();){
						meetingmember = it.next();
						if(meetingmember.getMember().getUserId().equalsIgnoreCase(user.getUserId())){
							//暂时将beforeMin中放入讲座邀请状态，只用于页面显示
							meeting.setBeforeMin(meetingmember.getAttendState());
						}
					 }
				}
			}
			request.setAttribute("report_data", invitelist);
			String requestParameter = "startTime="+request.getParameter("startTime")+"&purposeName="+request.getParameter("purposeName")+
										"&departmentName="+request.getParameter("departmentName")+"&mainUserId="+request.getParameter("mainUserId")+
										"&invitedState="+request.getParameter("invitedState")+"&state="+request.getParameter("state");
			model.addAttribute("requestParameter",requestParameter);
			return "unified/lectureArrangementList";
		} catch (Throwable e) {
			logger.error("Error UnifiedIndex.lectureArrangementList",e);
		}
		return null;
	}
	
	/**视频讲座申请信息详情页面*/
	@RequestMapping(value="/vedioapplictionView/{applicationId}")
	public String applictionView(HttpServletRequest request, @PathVariable Integer applicationId)
	{	
		VideoMeetingApp application = videoMeetingService.getVideoMeetingApplicationById(applicationId);
		request.setAttribute("videoapplicationview", application);
		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(application.getId());
		request.setAttribute("unifiedAppMeeting", meeting);
		return "unified/applicationView";
	}

	/**邮箱进入会诊管理页面*/
	@RequestMapping(value="/emailMeeting")
	public String emailMeeting(HttpServletRequest request, Model model)
	{	
		String meetingid=request.getParameter("meetingid");
		request.setAttribute("meetingid", meetingid);
		return arrangedConsultationQuery(request, model);
	}

	/**邮箱进入申请管理页面*/
	@RequestMapping(value="/emailApplication")
	public String emailApplication(HttpServletRequest request, Model model)
	{	
		String applicationid=request.getParameter("applicationid");
		request.setAttribute("applicationid", applicationid);
		return applyForLectureQuery(request, model);
	}
	
	/**邮箱进入视频讲座申请管理页面*/
	@RequestMapping(value="/emailVideoApplication")
	public String emailVideoApplication(HttpServletRequest request, Model model)
	{	
		String applicationid=request.getParameter("applicationid");
		request.setAttribute("applicationid", applicationid);
		return applyForConsultationQuery(request, model);
	}
	private boolean opintionState(Integer meetingid,UserInfo user){
		
		Object[] queryParams = new Object[2];
		queryParams[0] = meetingid;
		queryParams[1] = user.getUserId();
		return satisfactionService.getOpintionState(queryParams);
	}
	/**重症监护申请信息查询页面*/
	@RequestMapping(value="/applyForICUMonitQuery")
	public String applyForMonitQuery(HttpServletRequest request,Model model)
	{
/*		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);*/
		return "unified/applyForICUMonitQuery";
	}
	
	/**重症监护申请信息展现页面
	 * @throws Throwable */
	@RequestMapping(value="/applyForICUMonitList")
	public String applyFoMonitList(HttpServletRequest request, Model model) throws Throwable
	{
		UserInfo user=(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		ICUMonitorCriteriaVO iCUMonitorCriteriaVO = new ICUMonitorCriteriaVO();
		iCUMonitorCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		iCUMonitorCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		iCUMonitorCriteriaVO.setExpectedTime(request.getParameter("expectedTime"));
		iCUMonitorCriteriaVO.setApplicationTime(request.getParameter("applicationTime"));
		iCUMonitorCriteriaVO.setPatientName(request.getParameter("patientName"));
		iCUMonitorCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		iCUMonitorCriteriaVO.setMeetingType(request.getParameter("meetingType"));
		iCUMonitorCriteriaVO.setState(request.getParameter("state"));
		iCUMonitorCriteriaVO.setRequesterId(user.getUserId());
		List<ICUMonitor> datas = meeAdmDbdService.searchUnifiedICUMonitAppList(new PageSortModel(request), iCUMonitorCriteriaVO);
		
		String requestParameter = "expectedTime="+request.getParameter("expectedTime")+"&applicationTime="+request.getParameter("applicationTime")+
									"&meetingType.id="+request.getParameter("meetingType.id")+"&state="+request.getParameter("state");
		model.addAttribute("requestParameter",requestParameter);
		model.addAttribute("report_data", datas);

	    model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
	    model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
	    model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
	    model.addAttribute("MEETING_LEVEL_EMERGENCY",GlobalConstent.MEETING_LEVEL_EMERGENCY);
	    model.addAttribute("MEETING_LEVEL_COMMON",GlobalConstent.MEETING_LEVEL_COMMON);
		return "unified/applyForICUMonitList";
	}

	
	/**远程探视申请信息查询页面*/
	@RequestMapping(value="/applyForVisitQuery")
	public String applyForVisitQuery(HttpServletRequest request,Model model)
	{
		return "unified/applyForICUVisitQuery";
	}
	/**远程探视申请信息列表页面*/
	@RequestMapping(value="/applyForICUVisitList")
	public  String applyForICUVisitList(HttpServletRequest request, Model model) throws Throwable{
		
		UserInfo user=(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		ICUMonitorCriteriaVO iCUMonitorCriteriaVO = new ICUMonitorCriteriaVO();
		iCUMonitorCriteriaVO.setExpectedTimeStart(request.getParameter("expectedTimeStart"));
		iCUMonitorCriteriaVO.setExpectedTimeEnd(request.getParameter("expectedTimeEnd"));
		iCUMonitorCriteriaVO.setPatientName(request.getParameter("patientName"));
		iCUMonitorCriteriaVO.setKeyWord(request.getParameter("keyWord"));
		iCUMonitorCriteriaVO.setState(request.getParameter("state"));
		iCUMonitorCriteriaVO.setRequesterId(user.getUserId());
		List<ICUVisitation> datas = meeAdmDbdService.searchICUVisitList(new PageSortModel(request), iCUMonitorCriteriaVO);
		model.addAttribute("report_data", datas);
		model.addAttribute("MEETING_APPLICATION_STATE_PENDING",GlobalConstent.MEETING_APPLICATION_STATE_PENDING);
		model.addAttribute("MEETING_APPLICATION_STATE_PASS",GlobalConstent.MEETING_APPLICATION_STATE_PASS);
		model.addAttribute("MEETING_APPLICATION_STATE_REFUSED",GlobalConstent.MEETING_APPLICATION_STATE_REFUSED);
		model.addAttribute("MEETING_LEVEL_EMERGENCY",GlobalConstent.MEETING_LEVEL_EMERGENCY);
		model.addAttribute("MEETING_LEVEL_COMMON",GlobalConstent.MEETING_LEVEL_COMMON);
		return "unified/applyForICUVisitList";
	}
	
	/**重症监护申请信息查询页面*/
	@RequestMapping(value="/arrangedICUVisitQuery")
	public String arrangedICUVisitQuery(HttpServletRequest request,Model model)
	{
		return "unified/arrangedICUVisitQuery";
	}
	/** 已安排会诊显示画面*/
	@RequestMapping(value="/arrangedICUVisitList")
	public String arrangedICUVisitList(HttpServletRequest request, Model model)
	{
		try {
			logger.debug("Enter UnifiedIndex.arrangedConsultationList");
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
			meetingCriteriaVO.setAcceptUserId(user.getUserId());
			meetingCriteriaVO.setMeetingStartTime(request.getParameter("meetingStartTime"));
			meetingCriteriaVO.setMeetingEndTime(request.getParameter("meetingEndTime"));
			meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
			meetingCriteriaVO.setState(request.getParameter("state"));
			meetingCriteriaVO.setInvitedState(String.valueOf(GlobalConstent.APPLICATION_STATE_ACCEPT));
			meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_ICUVISIT));
			
			List<Meeting> invitelist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
			request.setAttribute("report_data", invitelist);
			
			String requestParameter = "meetingStartTime="+request.getParameter("meetingStartTime")+"&meetingEndTime="+request.getParameter("meetingEndTime")+
										"&state="+request.getParameter("state");
			model.addAttribute("requestParameter",requestParameter);
			model.addAttribute("currUser",user);
			
			return "unified/arrangedICUVisitList";
		} catch (Throwable e) {
			logger.error("Error UnifiedIndex.arrangedConsultationList",e);
		}
		return null;
	}
	@Resource(type = SatisfactionService.class)
	public void setSatisfactionService(SatisfactionService satisfactionService) {
		this.satisfactionService = satisfactionService;
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
	@Resource(type = VideoApplicationPurposeService.class)
	public void setVideoApplicationPurposeService(
			VideoApplicationPurposeService videoApplicationPurposeService) {
	}
	@Resource(type = VideoMeetingService.class)
	public void setVideoMeetingService(VideoMeetingService videoMeetingService) {
		this.videoMeetingService = videoMeetingService;
	}
	@Resource(type = MeetingMemberService.class)
	public void setMeetingmemberervice(MeetingMemberService meetingmemberervice) {
		this.meetingmemberervice = meetingmemberervice;
	}

}