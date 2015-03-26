package com.wafersystems.mrbs.web.unified;

import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.icu.ICUMonitorService;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.ApplicationOperateService;
import com.wafersystems.mrbs.service.meeting.EvaluationService;
import com.wafersystems.mrbs.service.meeting.Icd10DicService;
import com.wafersystems.mrbs.service.meeting.MeetingApplicationService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.service.meeting.SatisfactionManagerService;
import com.wafersystems.mrbs.service.meeting.SatisfactionService;
import com.wafersystems.mrbs.service.notice.factory.NoticeFactory;
import com.wafersystems.mrbs.service.user.DepartmentService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.service.user.UserTypeService;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.ApplicationDepartment;
import com.wafersystems.mrbs.vo.meeting.ApplicationICD10;
import com.wafersystems.mrbs.vo.meeting.Evaluation;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;
import com.wafersystems.mrbs.vo.meeting.ICUDepartment;
import com.wafersystems.mrbs.vo.meeting.ICUICD10;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping(value="/unified")
public class UnifiedController {

	private static final Logger logger = LoggerFactory.getLogger(UnifiedController.class);
	private MeetingApplicationService meetingApplicationService;
	private MeeAdmDbdService meeAdmDbdService;
	private MeetingMemberService meetingmemberervice;
	private NoticeFactory noticefactory;//邮件通知服务
	private EvaluationService evaluationService;
	private Icd10DicService icd10dicService;
	private SatisfactionService satisfactionService;
	private SatisfactionManagerService satisfactionManagerService;
	private UserService userService;
	private UserTypeService userTypeService;
	private DepartmentService departmentService;
	private ApplicationOperateService applicationOperateService;
    private MeetingMemberService memberService;
    private ICUMonitorService iCUMonitorService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    static Collator collator = Collator.getInstance(Locale.CHINA);

	/**申请视频讲座页面*/
	@RequestMapping(value="/applicationVideo")
	public String applicationVideo(HttpServletRequest request, Model model){	
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		
		request.setAttribute("user", user);
		//model.addAttribute("applicant", applicantService.getApplicantList(user));

		model.addAttribute("APPLICATION_STATE_NONE", GlobalConstent.APPLICATION_STATE_NONE);
		model.addAttribute("APPLICATION_STATE_ACCEPT", GlobalConstent.APPLICATION_STATE_ACCEPT);
		return "unified/videoApplication";
	}

	/**申请会议页面*/
	@RequestMapping(value="/application")
	public String application(HttpServletRequest request) {
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		request.setAttribute("user", user);

		List<ICD10DIC> datas = icd10dicService.getIcd10DicListLikeString(null);
		int max = datas.size();
		max = datas.size() > 100 ? 100 : max;
		datas = datas.subList(0, max);
		request.setAttribute("icd10Dic", datas);

		List<Department> depts = departmentService.getDepartmentNoHospital();
		request.setAttribute("deptments", depts);

		return "unified/addApplication";
	}

	/**ajax方法获得ICD10诊疗数据
	 * */
	@RequestMapping(value="/getDiagnosis")
	@ResponseBody
	public List<ICD10DIC> getDiagnosis(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");
		List<ICD10DIC> datas = new ArrayList<ICD10DIC>();
		String queryIcd10Name = request.getParameter("queryIcd10Name");//获取速查条件
		String icd10Ids = request.getParameter("icd10Ids");//获取已选择的icd10的id
		
		if(!StrUtil.isEmptyStr(queryIcd10Name)){
			datas = icd10dicService.getIcd10DicListLikeString(queryIcd10Name);
		}else{
			//当icd10的id空时
			Integer[] icd10IdObjs = null;
			if (!StrUtil.isEmptyStr(icd10Ids)) {
				if (icd10Ids.endsWith("|")) {
					icd10Ids = icd10Ids.substring(0, icd10Ids.length() - 1);
				}
				String [] strIcd10IdObjs = icd10Ids.split("\\|");
				int count = strIcd10IdObjs.length;
				icd10IdObjs = new Integer[count];
				for(int i=0;i<count;i++){
					icd10IdObjs[i]=Integer.valueOf(strIcd10IdObjs[i]);
				}
			}
			datas = icd10dicService.getIcd10DicListNotContainId(icd10IdObjs);
			int max = datas.size();
			max = datas.size() > 100 ? 100 : max;
			datas = datas.subList(0, max);
		}
		List<SelectItem> list = new ArrayList<SelectItem>();
		String s = "";
		SelectItem cv;
		if(datas!=null&&!datas.isEmpty()){
			for (ICD10DIC icd10dic : datas) {
				cv = new SelectItem();
				cv.setId(icd10dic.getId().toString());
				cv.setName(icd10dic.getDiagnosis());
				list.add(cv);
			}
		}
		list = this.ComparatorByName(list);
		ObjectMapper mapper = new ObjectMapper();
		SerializationConfig sc = mapper.getSerializationConfig();
		sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		mapper.setSerializationConfig(sc);
		s = mapper.writeValueAsString(list);
		response.getWriter().write(s);
		return null;

	}
	
	private List<SelectItem> ComparatorByName(List<SelectItem> list){
		Collections.sort(list, new Comparator<SelectItem>() {
   			Collator collator = Collator.getInstance(Locale.CHINA);
			@Override
			public int compare(SelectItem o1, SelectItem o2) {
				return collator.compare(o1.getName(), o2.getName());
			}
		});
		return list;
	}

	@RequestMapping(value="/getDepts")
	@ResponseBody
	public List<ICD10DIC> getDepts(HttpServletResponse response, String queryDepartmentName) throws Exception{
		response.setCharacterEncoding("UTF-8");
		List<Department> datas = new ArrayList<Department>();
   		if(!StrUtil.isEmptyStr(queryDepartmentName)){
			datas = departmentService.getDeptsByNameLikeString(queryDepartmentName);
		}else{
			datas = departmentService.getDepartmentNoHospital();
		}
   		
		List<SelectItem> list = new ArrayList<SelectItem>();
		String s = "";
		SelectItem cv;
		for (Department dept : datas) {
				cv = new SelectItem();
				cv.setId(dept.getId().toString());
				cv.setName(dept.getName());
				list.add(cv);
		}
		list = this.ComparatorByName(list);
		ObjectMapper mapper = new ObjectMapper();
		SerializationConfig sc = mapper.getSerializationConfig();
		sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
		mapper.setSerializationConfig(sc);
		s = mapper.writeValueAsString(list);
		response.getWriter().write(s);
		return null;
	}
	
	/**
	 * 增加远程会诊
	 * @param request
	 * @param application
	 * @return
	 */
	@RequestMapping(value="/add")
	public String add(HttpServletRequest request, MeetingApplication application){
		UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		String expectedTime = request.getParameter("expectedtime");		
		String[] files=(String[])request.getParameterValues("myfiles");
		String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
		String[] deptmentid = request.getParameterValues("departmentSelected");
		String[] icd10Dic = request.getParameterValues("icd10Selected");		
		try{
		    //增加病例讨论 
			applicationOperateService.saveMeetingApplication(userinfo, expectedTime, files, hainaDatas, deptmentid, icd10Dic, application);			
			request.getSession().setAttribute("returnmessage", "申请病历讨论成功！");
		}catch(Exception e){
			logger.error("申请病历讨论失败", e);
			request.getSession().setAttribute("returnmessage", "申请病历讨论失败！");
		}
		//sendMessage(request,application); 
		return "redirect:/unified/application";
	}

	//打开会诊修改页面
	@RequestMapping("/applictionEdit/{teleId}")
	public String editPage(@PathVariable Integer teleId, Model model, HttpSession session) throws Exception{
		logger.debug("Execute editPage");
		MeetingApplication application = meetingApplicationService.getMeetingApplicationById(teleId);
		
		Set<Accessories> temp = application.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if(null != temp && temp.size() > 0){			
			for(Accessories acc: temp){
				if(acc.getType().getId()!=2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}					
			}
			application.setAccessories(accs);
		}
        model.addAttribute("accsImage", accsImage);
		
		model.addAttribute("app", application);
		//取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if(application.getApplicationICD10s() != null && !application.getApplicationICD10s().isEmpty()){
			for (ApplicationICD10 applicationICD10 : application.getApplicationICD10s()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		//取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if(application.getMainDept()!=null){
			cv = new SelectItem();
			cv.setId(application.getMainDept().getId().toString());
			cv.setName(application.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if(application.getDepts() != null && !application.getDepts().isEmpty()){
			for (ApplicationDepartment appDept : application.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
		logger.debug("Execute Over editPage");
		return "/unified/applicationEdit";
	}

	//修改	
	@RequestMapping(value="/edit")
	public String edit(HttpServletRequest request, MeetingApplication application){
		UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		String expectedTime = request.getParameter("expectedtime");
		
		String[] files=(String[])request.getParameterValues("myfiles");
		String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
		String[] icd10Dic = request.getParameterValues("icd10Selected");
		String[] deptmentid = request.getParameterValues("departmentSelected");
		try{
			//修改病历讨论
			applicationOperateService.updateMeetingApplication(userinfo, expectedTime, files, hainaDatas, deptmentid, icd10Dic, application);
			request.getSession().setAttribute("returnmessage", "修改病历讨论成功！");
		}catch(Exception e){
			logger.error("修改病历讨论失败", e);
			request.getSession().setAttribute("returnmessage", "修改病历讨论失败！");
		}		
		return "redirect:/unifiedindex/applyForConsultationQuery/";
	}

	//增加视频讲座
	@RequestMapping(value="/addVideoApplicationPurpose")
	public String addVideoApplicationPurpose(HttpServletRequest request, VideoMeetingApp videoMeetingApp){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String[] files=(String[])request.getParameterValues("myfiles");
			String expecttime = request.getParameter("expecttime");
			//讲座内容将双引号做转意处理，否则页面title属性无法正常显示
			String content=videoMeetingApp.getLectureContent().replace("\"", "&quot;").replace("<", " &lt;");
			videoMeetingApp.setLectureContent(content);
			applicationOperateService.saveVideoApplication(user, expecttime, files, videoMeetingApp);
			request.getSession().setAttribute("returnmessage", "视频讲座申请成功！");
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "视频讲座申请失败！");
			e.printStackTrace();			
		}
		return "unified/videoApplication";
	}


	/**病例讨论评价的详情信息和受邀状态*/
	@RequestMapping(value="/meetViewAndInvitedState/{meetingid}")
	public String meetingViewAndInvitedState(Model model, @PathVariable Integer meetingid) {
		Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
		MeetingApplication mapplication = meetingview.getApplicationId();
		model.addAttribute("meeting", meetingview);
		model.addAttribute("mapplication", mapplication);
		Short invitedState = 0;
		
		//得到所有受邀成员
		List<MeetingMember> meetingMembers = memberService.getMembersByMeetingId(meetingid);
		this.sortMeetingMember(meetingMembers);
		List<UserInfo> noneAcceptPeople = new ArrayList<UserInfo>();
		List<UserInfo> acceptPeople = new ArrayList<UserInfo>();
		List<UserInfo> refusedAcceptPeople = new ArrayList<UserInfo>();
		UserInfo invitedUser = new UserInfo();
		
		//取出每一个受邀成员，并判断每一个受邀成员的状态
		for(MeetingMember meetingmember : meetingMembers){
			invitedState = meetingmember.getAttendState();
			invitedUser = meetingmember.getMember();
			if(invitedState == GlobalConstent.APPLICATION_STATE_NONE){
				noneAcceptPeople.add(invitedUser);
			}
			if(invitedState == GlobalConstent.APPLICATION_STATE_ACCEPT){
				acceptPeople.add(invitedUser);
			}
			if(invitedState == GlobalConstent.APPLICATION_STATE_REFUSED){
				refusedAcceptPeople.add(invitedUser);
			}
		}
		if(acceptPeople!=null&&!acceptPeople.isEmpty()){//判断已接受的
			int count = acceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				acceptPeople.add(user1);
				acceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				acceptPeople.add(user1);
			}
		}
		model.addAttribute("acceptPeople", acceptPeople);
		if(noneAcceptPeople!=null&&!noneAcceptPeople.isEmpty()){//判断未处理的
			int count = noneAcceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				noneAcceptPeople.add(user1);
				noneAcceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				noneAcceptPeople.add(user1);
			}
		}
		model.addAttribute("noneAcceptPeople", noneAcceptPeople);
		if(refusedAcceptPeople!=null&&!refusedAcceptPeople.isEmpty()){//判断已拒绝的
			int count = refusedAcceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				refusedAcceptPeople.add(user1);
				refusedAcceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				refusedAcceptPeople.add(user1);
			}
		}
		model.addAttribute("refusedAcceptPeople", refusedAcceptPeople);
//		MeetingApplication ma = this.applicationService.getMeetingApplicationById(meetingId);
		MeetingApplication application = meetingview.getApplicationId();
		Set<Accessories> temp = application.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if(null != temp && temp.size() > 0){
			
			for(Accessories acc: temp){
				if(acc.getType().getId()!=2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}					
			}
			application.setAccessories(accs);
		}
		model.addAttribute("application", application);
		model.addAttribute("accsImage", accsImage);
		//取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if(application.getApplicationICD10s() != null && !application.getApplicationICD10s().isEmpty()){
			for (ApplicationICD10 applicationICD10 : application.getApplicationICD10s()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		//取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if(application.getMainDept()!=null){
			cv = new SelectItem();
			cv.setId(application.getMainDept().getId().toString());
			cv.setName(application.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if(application.getDepts() != null && !application.getDepts().isEmpty()){
			for (ApplicationDepartment appDept : application.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
//		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//		model.addAttribute("meeting", meeting);

//		model.addAttribute("HAINA_URL", GlobalParam.haina_url);
		return "/meetingadmin/meetingAppviewAndInvitedState";
//		return "unified/meetingView";
	}
	
	/**病例讨论评价的详情信息和受邀状态*/
	@RequestMapping(value="/meetingExamineViewAndInvitedState/{meetingid}")
	public String meetingExamineViewAndInvitedState(Model model, @PathVariable Integer meetingid) {
		//Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
		Meeting meetingview = null;
		//根据病历讨论的申请Id查得MeetingApplication
		MeetingApplication meetingApplication = meeAdmDbdService.viewMeetingApplication(meetingid);
		model.addAttribute("meetingApplication", meetingApplication);
		if(meetingApplication!=null&&meetingApplication.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			meetingview = meeAdmDbdService.viewMeetingByApplicationId(meetingApplication.getId());
			model.addAttribute("meeting", meetingview);
			Short invitedState = 0;
			
			//得到所有受邀成员
			Meeting meeting = meeAdmDbdService.getMeetingByApplicationId(meetingApplication.getId());
			List<MeetingMember> meetingMembers = memberService.getMembersByMeetingId(meeting.getId());
			this.sortMeetingMember(meetingMembers);
			List<UserInfo> noneAcceptPeople = new ArrayList<UserInfo>();
			List<UserInfo> acceptPeople = new ArrayList<UserInfo>();
			List<UserInfo> refusedAcceptPeople = new ArrayList<UserInfo>();
			UserInfo invitedUser = new UserInfo();
			
			//取出每一个受邀成员，并判断每一个受邀成员的状态
			for(MeetingMember meetingmember : meetingMembers){
				invitedState = meetingmember.getAttendState();
				invitedUser = meetingmember.getMember();
				if(invitedState == GlobalConstent.APPLICATION_STATE_NONE){
					noneAcceptPeople.add(invitedUser);
				}
				if(invitedState == GlobalConstent.APPLICATION_STATE_ACCEPT){
					acceptPeople.add(invitedUser);
				}
				if(invitedState == GlobalConstent.APPLICATION_STATE_REFUSED){
					refusedAcceptPeople.add(invitedUser);
				}
			}
			if(acceptPeople!=null&&!acceptPeople.isEmpty()){//判断已接受的
				int count = acceptPeople.size();
				if(count == 1 || count%3==1){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					UserInfo user2 = new UserInfo();
					user2.setName(" ");
					acceptPeople.add(user1);
					acceptPeople.add(user2);
				}else if(count==2||count%3==2){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					acceptPeople.add(user1);
				}
			}
			model.addAttribute("acceptPeople", acceptPeople);
			if(noneAcceptPeople!=null&&!noneAcceptPeople.isEmpty()){//判断未处理的
				int count = noneAcceptPeople.size();
				if(count == 1 || count%3==1){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					UserInfo user2 = new UserInfo();
					user2.setName(" ");
					noneAcceptPeople.add(user1);
					noneAcceptPeople.add(user2);
				}else if(count==2||count%3==2){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					noneAcceptPeople.add(user1);
				}
			}
			model.addAttribute("noneAcceptPeople", noneAcceptPeople);
			if(refusedAcceptPeople!=null&&!refusedAcceptPeople.isEmpty()){//判断已拒绝的
				int count = refusedAcceptPeople.size();
				if(count == 1 || count%3==1){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					UserInfo user2 = new UserInfo();
					user2.setName(" ");
					refusedAcceptPeople.add(user1);
					refusedAcceptPeople.add(user2);
				}else if(count==2||count%3==2){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					refusedAcceptPeople.add(user1);
				}
			}
			model.addAttribute("refusedAcceptPeople", refusedAcceptPeople);	
			MeetingApplication application = meetingview.getApplicationId();
			Set<Accessories> temp = application.getAccessories();
			Set<Accessories> accs = new TreeSet<Accessories>();
			Set<Accessories> accsImage = new TreeSet<Accessories>();
			if(null != temp && temp.size() > 0){
				
				for(Accessories acc: temp){
					if(acc.getType().getId()!=2)
						accs.add(acc);
					else {
						accsImage.add(acc);
					}					
				}
				application.setAccessories(accs);
			}
			model.addAttribute("application", application);
			model.addAttribute("accsImage", accsImage);
			//取得已选的ICD10
			List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
			SelectItem cv;
			if(application.getApplicationICD10s() != null && !application.getApplicationICD10s().isEmpty()){
				for (ApplicationICD10 applicationICD10 : application.getApplicationICD10s()) {
					cv = new SelectItem();
					cv.setId(applicationICD10.getIcd().getId().toString());
					cv.setName(applicationICD10.getIcd().getDiagnosis());
					selectedICD10s.add(cv);
				}
			}
			selectedICD10s = this.ComparatorByName(selectedICD10s);
			model.addAttribute("selectedICD10s", selectedICD10s);
			//取得已选的科室
			List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
			if(application.getMainDept()!=null){
				cv = new SelectItem();
				cv.setId(application.getMainDept().getId().toString());
				cv.setName(application.getMainDept().getName());
				selectedDeparts.add(cv);
			}
			if(application.getDepts() != null && !application.getDepts().isEmpty()){
				for (ApplicationDepartment appDept : application.getDepts()) {
					cv = new SelectItem();
					cv.setId(appDept.getDepartment().getId().toString());
					cv.setName(appDept.getDepartment().getName());
					selectedDeparts.add(cv);
				}
			}
			selectedDeparts = this.ComparatorByName(selectedDeparts);
			model.addAttribute("selectedDeparts", selectedDeparts);
//			Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//			model.addAttribute("meeting", meeting);

//			model.addAttribute("HAINA_URL", GlobalParam.haina_url);
		}

		if((meetingApplication.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PENDING)) || (meetingApplication.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED))){
			MeetingApplication application = meeAdmDbdService.viewMeetingApplication(meetingid);;
			//MeetingApplication application = meetingview.getApplicationId();
			model.addAttribute("application", application);
			Set<Accessories> temp = application.getAccessories();
			Set<Accessories> accs = new TreeSet<Accessories>();
			Set<Accessories> accsImage = new TreeSet<Accessories>();
			if(null != temp && temp.size() > 0){
				
				for(Accessories acc: temp){
					if(acc.getType().getId()!=2)
						accs.add(acc);
					else {
						accsImage.add(acc);
					}					
				}
				application.setAccessories(accs);
			}
			model.addAttribute("application", application);
			model.addAttribute("accsImage", accsImage);
			//取得已选的ICD10
			List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
			SelectItem cv;
			if(application.getApplicationICD10s() != null && !application.getApplicationICD10s().isEmpty()){
				for (ApplicationICD10 applicationICD10 : application.getApplicationICD10s()) {
					cv = new SelectItem();
					cv.setId(applicationICD10.getIcd().getId().toString());
					cv.setName(applicationICD10.getIcd().getDiagnosis());
					selectedICD10s.add(cv);
				}
			}
			selectedICD10s = this.ComparatorByName(selectedICD10s);
			model.addAttribute("selectedICD10s", selectedICD10s);
			//取得已选的科室
			List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
			if(application.getMainDept()!=null){
				cv = new SelectItem();
				cv.setId(application.getMainDept().getId().toString());
				cv.setName(application.getMainDept().getName());
				selectedDeparts.add(cv);
			}
			if(application.getDepts() != null && !application.getDepts().isEmpty()){
				for (ApplicationDepartment appDept : application.getDepts()) {
					cv = new SelectItem();
					cv.setId(appDept.getDepartment().getId().toString());
					cv.setName(appDept.getDepartment().getName());
					selectedDeparts.add(cv);
				}
			}
			selectedDeparts = this.ComparatorByName(selectedDeparts);
			model.addAttribute("selectedDeparts", selectedDeparts);
//			Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//			model.addAttribute("meeting", meeting);

//			model.addAttribute("HAINA_URL", GlobalParam.haina_url);			
		}

		return "/meetingadmin/meetingAppviewAndInvitedState";
//		return "unified/meetingView";
	}
	
	/**
	 * 对参会人先按照类型排序，再按照汉语拼音排序
	 * @param list
	 * @return
	 */
	private List<MeetingMember> sortMeetingMember(List<MeetingMember> list){
		Collections.sort(list, new Comparator<MeetingMember>() {
			@Override
			public int compare(MeetingMember o1, MeetingMember o2) {
				return o1.getMember().getUserType().equals(o2.getMember().getUserType()) ? 
						collator.compare(o1.getMember().getName(), o2.getMember().getName()) : o1.getMember().getUserType().getValue() - o2.getMember().getUserType().getValue();
			}
		});
		return list;
	}
	/**已安排病历讨论的查看功能（共同体和专家权限下）
	 * @throws Exception 
	 * */
	@RequestMapping(value="/arrangedmeetView/{meetingid}")
	public String arrangedmeetView(Model model, @PathVariable Integer meetingid,HttpServletRequest request) throws Exception {
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		model.addAttribute("userinfor", user);
		Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
		model.addAttribute("meeting", meetingview);
		Object[] queryParams = new Object[2];
		queryParams[0] = meetingview.getId();
		queryParams[1] = user.getUserId();
		Satisfaction satisfaction = satisfactionService.getSatisfaction(queryParams);//根据meetingId和userId得到一个Satisfaction
		model.addAttribute("satisfaction", satisfaction);
		Short userTypeValue = user.getUserType().getValue();
		List<Evaluation> evaluationList = null;
		if(userTypeValue == 5){
			evaluationList=evaluationService.getEvaluationListByuserTypeValue(GlobalConstent.USER_TYPE_UNION);
		}
		model.addAttribute("evaluationList", evaluationList);
//		MeetingApplication ma = this.applicationService.getMeetingApplicationById(meetingId);
		MeetingApplication application = meetingview.getApplicationId();
		Set<Accessories> temp = application.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if(null != temp && temp.size() > 0){
			
			for(Accessories acc: temp){
				if(acc.getType().getId()!=2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}					
			}
			application.setAccessories(accs);
		}
		model.addAttribute("application", application);
		model.addAttribute("accsImage", accsImage);
		//取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if(application.getApplicationICD10s() != null && !application.getApplicationICD10s().isEmpty()){
			for (ApplicationICD10 applicationICD10 : application.getApplicationICD10s()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		//取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if(application.getMainDept()!=null){
			cv = new SelectItem();
			cv.setId(application.getMainDept().getId().toString());
			cv.setName(application.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if(application.getDepts() != null && !application.getDepts().isEmpty()){
			for (ApplicationDepartment appDept : application.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
//		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//		model.addAttribute("meeting", meeting);

//		model.addAttribute("HAINA_URL", GlobalParam.haina_url);
		return "/meetingadmin/arrangedMeetingAppView";
	}
	
	/**会诊信息详情页面*/
	@RequestMapping(value="/meetView/{meetingid}")
	public String meetingView(Model model, @PathVariable Integer meetingid,HttpServletRequest request){
		Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
		model.addAttribute("meeting", meetingview);
//		MeetingApplication ma = this.applicationService.getMeetingApplicationById(meetingId);
		MeetingApplication application = meetingview.getApplicationId();
		Set<Accessories> temp = application.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if(null != temp && temp.size() > 0){
			
			for(Accessories acc: temp){
				if(acc.getType().getId()!=2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}					
			}
			application.setAccessories(accs);
		}
		model.addAttribute("application", application);
		model.addAttribute("accsImage", accsImage);
		//取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if(application.getApplicationICD10s() != null && !application.getApplicationICD10s().isEmpty()){
			for (ApplicationICD10 applicationICD10 : application.getApplicationICD10s()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		//取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if(application.getMainDept()!=null){
			cv = new SelectItem();
			cv.setId(application.getMainDept().getId().toString());
			cv.setName(application.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if(application.getDepts() != null && !application.getDepts().isEmpty()){
			for (ApplicationDepartment appDept : application.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
//		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//		model.addAttribute("meeting", meeting);

//		model.addAttribute("HAINA_URL", GlobalParam.haina_url);
		return "/meetingadmin/meetingappview";
//		return "unified/meetingView";
	}	

	/**申请信息详情页面*/
	@RequestMapping(value="/applictionView/{applicationId}")
	public String applictionView(Model model, @PathVariable Integer applicationId){	
		
		MeetingApplication application = this.meetingApplicationService.getMeetingApplicationById(applicationId);
		model.addAttribute("application", application);

		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(application.getId());
		model.addAttribute("meeting", meeting);

//		model.addAttribute("HAINA_URL", GlobalParam.haina_url);
		
		return "unified/applicationView";
	}

	/**申请信息详情页面*/
	@RequestMapping(value="/appEdit/{applicationId}")
	public String applictionEdit(Model model, @PathVariable Integer applicationId){	
		MeetingApplication view = meetingApplicationService.getMeetingApplicationById(applicationId);
		model.addAttribute("applicationview", view);

		model.addAttribute("APPLICATION_STATE_NONE", GlobalConstent.APPLICATION_STATE_NONE);
		model.addAttribute("APPLICATION_STATE_ACCEPT", GlobalConstent.APPLICATION_STATE_ACCEPT);

		return "unified/applicationEdit";
	}

	/**显示会诊评价页面*/
	@RequestMapping(value="/meetOpintion")
	public String meetingOpintion(HttpSession session, HttpServletRequest request){	
		String meetingid=request.getParameter("opintionmeetingid");
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		int id=0;
		Short userTypeValue = user.getUserType().getValue();
		if(!StrUtil.isEmptyStr(meetingid)){
			id=Integer.parseInt(meetingid);
			UnifiedUserType ut = userTypeService.getUserTypeByValue(userTypeValue);
			Meeting view= meeAdmDbdService.viewMeeting(id);
			List<Evaluation> evaluation = evaluationService.getEvaluationList(ut);
			request.setAttribute("meeting", view);
			request.setAttribute("evallist", evaluation);
			Object[] queryParams = new Object[2];
			queryParams[0] = id;
			queryParams[1] = user.getUserId();
			Satisfaction satisfaction = satisfactionService.getSatisfaction(queryParams);
			request.setAttribute("satisfaction", satisfaction);
		}   
//		if(userType.equals("2")){
//			SatisfactionManager sf = new SatisfactionManager();
//			Object[] queryParams = new Object[2];
//			queryParams[0] = Integer.parseInt(meetingid);
//			queryParams[1] = ((UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION)).getUserId();
//		    sf =  satisfactionManagerService.getSatisfactionManager(queryParams);
//		    request.setAttribute("satisfactionManager", sf);
//		    return "unified/meetOpintion_manager";
//		}
//		else
		return "unified/meetOpintion_group";
        
	}

	/**会诊会议管理员评价页面*/
	@RequestMapping(value="/addmanageropintion")
	public String addmanageropintion(HttpServletRequest request, HttpServletResponse response)
	throws Exception{	

		String evalvalue = request.getParameter("evalvalue");
		String meetingid = request.getParameter("meetingid");
		String meetingTime = request.getParameter("meetingTime");
		String realStartTime = request.getParameter("realStartTime");
		String realEndTime = request.getParameter("realEndTime");
		Meeting meeting = new Meeting();
		if(!StrUtil.isEmptyStr(meetingid)){
			meeting = meeAdmDbdService.viewMeeting(Integer.parseInt(meetingid));
		}
		if (!StrUtil.isEmptyStr(evalvalue)) {
			int score = Integer.parseInt(evalvalue);

			Object[] queryParams = new Object[2];
			queryParams[0] = Integer.parseInt(meetingid);
			queryParams[1] = ((UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION)).getUserId();
			satisfactionManagerService.saveOrUpdateSatisfactionManager(queryParams,score, meetingTime,meeting,realStartTime,realEndTime);
			
		}
		response.getWriter().write("success");
		return null;
	}

	/**会诊共同体评价页面*/
	@RequestMapping(value="/addgroupopintion")
	public String addgroupopintion(@RequestParam Short localNumber,
			@RequestParam String evalvalue1,@RequestParam String evalvalue2,@RequestParam String evalvalue3,
			@RequestParam Integer meetingid,@RequestParam String suggestion, HttpSession session, HttpServletResponse response)
	throws Exception{	
		try{
			response.setCharacterEncoding("UTF-8");
			Object[] queryParams = new Object[2];
			queryParams[0] = meetingid;
			queryParams[1] = ((UserInfo)session.getAttribute(GlobalConstent.USER_LOGIN_SESSION)).getUserId();
			satisfactionService.saveOrUpdateSatisfaction(queryParams,localNumber,evalvalue1,evalvalue2,evalvalue3, suggestion);
			//将参会人数更新至MeetingMember中的attendNo
			meetingmemberervice.updateMeetingMember(queryParams, localNumber);
			response.getWriter().write("success");
		}catch(Exception e){
			e.printStackTrace();
			response.getWriter().write("failed");
		}
		return null;
	}

	//查看视频讲座
	@RequestMapping("/viewVideoApp/{appId}")
	public String viewVideoApp(@PathVariable Integer appId, Model model) throws Exception{
		Meeting meetingview = meeAdmDbdService.viewMeeting(appId);
		VideoMeetingApp vmma = meetingview.getVideoapplicationId();
		model.addAttribute("vmapp", vmma);
		if(vmma.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			model.addAttribute("meeting", meetingview);
		}
		return "/meetingadmin/viewVideoApplication";
	}
	
	//讲座安排的查看页面（以共同体或专家登陆的讲座安排功能）
	@RequestMapping("/viewVideoArrangedApp/{appId}")
	public String viewVideoArrangedApp(@PathVariable Integer appId, Model model,HttpServletRequest request) throws Exception{
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		model.addAttribute("userinfor", user);

		Meeting meetingview = meeAdmDbdService.viewMeeting(appId);
		Object[] queryParams = new Object[2];
		queryParams[0] = meetingview.getId();
		queryParams[1] = user.getUserId(); 
		Satisfaction satisfaction = satisfactionService.getSatisfaction(queryParams);//根据meetingId和userId得到一个Satisfaction
		Short userTypeValue = user.getUserType().getValue();
		List<Evaluation> evaluationList = null;
		model.addAttribute("satisfaction", satisfaction);
		if(userTypeValue == 5){
			evaluationList=evaluationService.getEvaluationListByuserTypeValue(GlobalConstent.USER_TYPE_UNION);
		}
		model.addAttribute("evaluationList", evaluationList);
		VideoMeetingApp vmma = meetingview.getVideoapplicationId();
		model.addAttribute("vmapp", vmma);
		if(vmma.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			model.addAttribute("meeting", meetingview);
		}
		return "/meetingadmin/viewVideoArrangedApplication";
	}
	
    //获得会诊管理员中讲座评价的查看功能的详细信息
	@RequestMapping("/viewEvaluationVideoApp/{appId}")
	public String viewEvaluationVideoApp(@PathVariable Integer appId, Model model) throws Exception{
		Meeting meetingview = meeAdmDbdService.viewMeeting(appId);          
		VideoMeetingApp vmma = meetingview.getVideoapplicationId();
		model.addAttribute("vmapp", vmma);
		if(vmma.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			model.addAttribute("meeting", meetingview);			
		}
		//得到所有受邀成员
		List<MeetingMember> videoMembers = memberService.getMembersByMeetingId(appId);
		this.sortMeetingMember(videoMembers);//对参会人先按照类型排序，再按照汉语拼音排序
		List<UserInfo> noneAcceptPeople = new ArrayList<UserInfo>();
		List<UserInfo> acceptPeople = new ArrayList<UserInfo>();
		List<UserInfo> refusedAcceptPeople = new ArrayList<UserInfo>();
		UserInfo invitedUser = new UserInfo();	
		Short invitedState = 0;
		//取出每一个受邀成员，并判断每一个受邀成员的状态
		for(MeetingMember videomember : videoMembers){
			invitedState = videomember.getAttendState();
			invitedUser = videomember.getMember();
			if(invitedState == GlobalConstent.APPLICATION_STATE_NONE){
				noneAcceptPeople.add(invitedUser);
			}
			if(invitedState == GlobalConstent.APPLICATION_STATE_ACCEPT){
				acceptPeople.add(invitedUser);
			}
			if(invitedState == GlobalConstent.APPLICATION_STATE_REFUSED){
				refusedAcceptPeople.add(invitedUser);
			}
		}
		if(acceptPeople!=null&&!acceptPeople.isEmpty()){//判断已接受的
			int count = acceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				acceptPeople.add(user1);
				acceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				acceptPeople.add(user1);
			}
		}
		model.addAttribute("acceptPeople", acceptPeople);
		if(noneAcceptPeople!=null&&!noneAcceptPeople.isEmpty()){//判断未处理的
			int count = noneAcceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				noneAcceptPeople.add(user1);
				noneAcceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				noneAcceptPeople.add(user1);
			}
		}
		model.addAttribute("noneAcceptPeople", noneAcceptPeople);
		if(refusedAcceptPeople!=null&&!refusedAcceptPeople.isEmpty()){//判断已拒绝的
			int count = refusedAcceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				refusedAcceptPeople.add(user1);
				refusedAcceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				refusedAcceptPeople.add(user1);
			}
		}
		model.addAttribute("refusedAcceptPeople", refusedAcceptPeople);
		return "/meetingadmin/videoAppviewAndInvitedState";
	}	
	//获得会诊管理员中讲座审核的查看功能的详细信息
	@RequestMapping("/viewVideoExamineApp/{appId}")
	public String viewVideoExamineApp(@PathVariable Integer appId, Model model) throws Exception{
		//Meeting meetingview = meeAdmDbdService.viewMeeting(appId);
		Meeting meetingview = null;
		VideoMeetingApp vmma = meeAdmDbdService.viewVideo(appId);  
		model.addAttribute("vmapp", vmma);
		if(vmma.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			//如果该视频讲座为审核通过的状态，通过该讲座的id在Meeting中找到该Meeting
			meetingview = meeAdmDbdService.viewMeetingByVideoapplicationId(appId);
			model.addAttribute("meeting", meetingview);
			
			//得到所有受邀成员
			List<MeetingMember> videoMembers = memberService.getMembersByMeetingId(meetingview.getId());
			this.sortMeetingMember(videoMembers);//对参会人先按照类型排序，再按照汉语拼音排序
			List<UserInfo> noneAcceptPeople = new ArrayList<UserInfo>();
			List<UserInfo> acceptPeople = new ArrayList<UserInfo>();
			List<UserInfo> refusedAcceptPeople = new ArrayList<UserInfo>();
			UserInfo invitedUser = new UserInfo();	
			Short invitedState = 0;
			//取出每一个受邀成员，并判断每一个受邀成员的状态
			for(MeetingMember videomember : videoMembers){
				invitedState = videomember.getAttendState();
				invitedUser = videomember.getMember();
				if(invitedState == GlobalConstent.APPLICATION_STATE_NONE){
					noneAcceptPeople.add(invitedUser);
				}
				if(invitedState == GlobalConstent.APPLICATION_STATE_ACCEPT){
					acceptPeople.add(invitedUser);
				}
				if(invitedState == GlobalConstent.APPLICATION_STATE_REFUSED){
					refusedAcceptPeople.add(invitedUser);
				}
			}
			if(acceptPeople!=null&&!acceptPeople.isEmpty()){//判断已接受的
				int count = acceptPeople.size();
				if(count == 1 || count%3==1){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					UserInfo user2 = new UserInfo();
					user2.setName(" ");
					acceptPeople.add(user1);
					acceptPeople.add(user2);
				}else if(count==2||count%3==2){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					acceptPeople.add(user1);
				}
			}
			model.addAttribute("acceptPeople", acceptPeople);
			if(noneAcceptPeople!=null&&!noneAcceptPeople.isEmpty()){//判断未处理的
				int count = noneAcceptPeople.size();
				if(count == 1 || count%3==1){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					UserInfo user2 = new UserInfo();
					user2.setName(" ");
					noneAcceptPeople.add(user1);
					noneAcceptPeople.add(user2);
				}else if(count==2||count%3==2){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					noneAcceptPeople.add(user1);
				}
			}
			model.addAttribute("noneAcceptPeople", noneAcceptPeople);
			if(refusedAcceptPeople!=null&&!refusedAcceptPeople.isEmpty()){//判断已拒绝的
				int count = refusedAcceptPeople.size();
				if(count == 1 || count%3==1){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					UserInfo user2 = new UserInfo();
					user2.setName(" ");
					refusedAcceptPeople.add(user1);
					refusedAcceptPeople.add(user2);
				}else if(count==2||count%3==2){
					UserInfo user1 = new UserInfo();
					user1.setName(" ");
					refusedAcceptPeople.add(user1);
				}
			}
			model.addAttribute("refusedAcceptPeople", refusedAcceptPeople);			
		}
		
		return "/meetingadmin/videoAppviewAndInvitedState";
	}	
	//打开修改视频讲座会诊申请
	@RequestMapping(value = "/viewvedioappmeeting/{meetingId}")
	public String viewVideoAppMeeting(HttpServletRequest request,@PathVariable Integer meetingId) {
		VideoMeetingApp vmma = this.meetingApplicationService.getVideoMeetingApplicationById(meetingId);
		request.setAttribute("vmapp", vmma);
		return "/unified/editVideoApplication";
	}

	//修改视频讲座
	@RequestMapping("/editVideoApplicationPurpose")
	public String editVideoMeetingApp(HttpServletRequest request, HttpServletResponse response) throws Exception{
		try{
			String[] files=(String[])request.getParameterValues("myfiles");
			String expecttime = request.getParameter("expecttime");//会诊时间
			//String deptmentid = request.getParameter("deptmentid");//主讲人部门
			String deptpersonid = request.getParameter("deptpersonid");//主讲人姓名
			//String applicationPositionId = request.getParameter("positionld");//职称
			//讲座内容将双引号做转意处理，否则页面title属性无法正常显示
			String lectureContent = request.getParameter("lectureContent").replace("\"", "&quot;").replace("<", " &lt;");//讲座内容			
			String videoApplicationPurposeid=request.getParameter("videoApplicationPurposeid");//讲座目的
			String meetinglevel=request.getParameter("meetinglevel");//级别
			String teachingObject=request.getParameter("teachingObject");//授课对象
			String id = request.getParameter("id");	//视频讲座id
			applicationOperateService.updateVideoApplication(files,expecttime, deptpersonid, lectureContent, videoApplicationPurposeid, meetinglevel,id,teachingObject);
		}catch(Exception e){
			logger.error("修改讲座申请出错", e);
			response.getWriter().print("error");
		}
		logger.debug("Execute over editVideoMeetingApp");
		return "redirect:/unifiedindex/applyForLectureQuery";
	}
	
	/**
	 * add by wangzhenglin
	 * @param request
	 * @return
	 * 申请ICU重症监护
	 */
	@RequestMapping("/icuMonit")
	public String  icuMonit(HttpServletRequest request){
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		request.setAttribute("user", user);
		List<Department> depts = departmentService.getDepartmentNoHospital();
		request.setAttribute("deptments", depts);

		return "unified/addIcuMonit";
	}
	/**add by wangzhenglin
	 * 添加监护申请
	 * @param request
	 * @param application
	 * @return
	 */
	@RequestMapping("/addIcuMonit")
	public String addIcuMonit(HttpServletRequest request, ICUMonitor application)throws Exception{
		System.out.print("========================");
		UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		String expectedTime = request.getParameter("expectedtime");		
		String[] files=(String[])request.getParameterValues("myfiles");
		String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
		String[] deptmentid = request.getParameterValues("departmentSelected");
		String[] icd10Dic = request.getParameterValues("icd10Selected");		
		try{
		    //增加重症监护
			iCUMonitorService.saveICUMonit(userinfo, expectedTime, files, hainaDatas, deptmentid, icd10Dic, application);			
			request.getSession().setAttribute("returnmessage", "申请重症监护成功！");
		}catch(Exception e){
			logger.error("申请重症监护失败", e);
			request.getSession().setAttribute("returnmessage", "申请重症监护失败！");
		}
		//sendMessage(request,application); 
		return "redirect:/unified/icuMonit";
	
	}
	//打开会重症监护改页面
	/**
	 * add by wangzhenglin 打开重症监护修改页面
	 * @param teleId
	 * @param model
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ICUMonitorEdit/{teleId}")
	public String editICUMonitorPage(@PathVariable Integer teleId, Model model, HttpSession session) throws Exception{
			logger.debug("Execute editPage");
			//MeetingApplication application = meetingApplicationService.getMeetingApplicationById(teleId);
			ICUMonitor iCUMonitor =iCUMonitorService.getICUMonitById(teleId);
			Set<Accessories> temp = iCUMonitor.getAccessories();
			Set<Accessories> accs = new TreeSet<Accessories>();
			Set<Accessories> accsImage = new TreeSet<Accessories>();
			if(null != temp && temp.size() > 0){			
				for(Accessories acc: temp){
					if(acc.getType().getId()!=2)
						accs.add(acc);
					else {
						accsImage.add(acc);
					}					
				}
				iCUMonitor.setAccessories(accs);
			}
	        model.addAttribute("accsImage", accsImage);
			
			model.addAttribute("app", iCUMonitor);
			//取得已选的ICD10
			List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
			SelectItem cv;
			if(iCUMonitor.getIcuICD10() != null && !iCUMonitor.getIcuICD10().isEmpty()){
				for (ICUICD10 applicationICD10 : iCUMonitor.getIcuICD10()) {
					cv = new SelectItem();
					cv.setId(applicationICD10.getIcd().getId().toString());
					cv.setName(applicationICD10.getIcd().getDiagnosis());
					selectedICD10s.add(cv);
				}
			}
			selectedICD10s = this.ComparatorByName(selectedICD10s);
			model.addAttribute("selectedICD10s", selectedICD10s);
			//取得已选的科室
			List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
			if(iCUMonitor.getMainDept()!=null){
				cv = new SelectItem();
				cv.setId(iCUMonitor.getMainDept().getId().toString());
				cv.setName(iCUMonitor.getMainDept().getName());
				selectedDeparts.add(cv);
			}
			if(iCUMonitor.getDepts() != null && !iCUMonitor.getDepts().isEmpty()){
				for (ICUDepartment appDept : iCUMonitor.getDepts()) {
					cv = new SelectItem();
					cv.setId(appDept.getDepartment().getId().toString());
					cv.setName(appDept.getDepartment().getName());
					selectedDeparts.add(cv);
				}
			}
			selectedDeparts = this.ComparatorByName(selectedDeparts);
			model.addAttribute("selectedDeparts", selectedDeparts);
			logger.debug("Execute Over IcueditPage");
			return "/unified/icuMonitorEdit";
		}

		//修改监护信息	
		@RequestMapping(value="/editIcuMontorBegin")
		public String editICUMonitor(HttpServletRequest request, ICUMonitor application){
			UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String expectedTime = request.getParameter("expectedtime");
			
			String[] files=(String[])request.getParameterValues("myfiles");
			String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
			String[] icd10Dic = request.getParameterValues("icd10Selected");
			String[] deptmentid = request.getParameterValues("departmentSelected");
			try{
				//修改病历讨论
				iCUMonitorService.updateICUMonitor(userinfo, expectedTime, files, hainaDatas, deptmentid, icd10Dic, application);
				request.getSession().setAttribute("returnmessage", "修改重症监护成功！");
			}catch(Exception e){
				logger.error("修改重症监护失败", e);
				request.getSession().setAttribute("returnmessage", "修改重症监护失败！");
			}		
			return "redirect:/unifiedindex/applyForICUMonitQuery/";
		}
		/**
		 * add by wangzhenglin 重症监护，审核界面的查看功能
		 * @param model
		 * @param meetingid
		 * @return
		 */
		@RequestMapping(value="/icuMonitorExamineViewAndInvitedState/{icuMontorId}")
		public String icuMonitorExamineViewAndInvitedState(Model model, @PathVariable Integer icuMontorId) {
			Meeting meetingview = null;
			//根据Id查得ICUMonitor
			ICUMonitor iCUMonitor = meeAdmDbdService.viewICUMonitor(icuMontorId);
			model.addAttribute("meetingApplication", iCUMonitor);
			//审核通过的重症监护
			if(iCUMonitor!=null&&iCUMonitor.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
				this.icuMonitorStartPass(model,meetingview,iCUMonitor);
			}
			//审核未通过或者等待审核的监护数据获取
			if((iCUMonitor.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PENDING)) || (iCUMonitor.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED))){
				icuMonitorRefusedORPending(model,icuMontorId);
			}

			return "/meetingadmin/meetingIcuMonitorviewAndInvitedState";
		}

	private void icuMonitorStartPass(Model model,Meeting meetingview,ICUMonitor iCUMonitor){

		meetingview = meeAdmDbdService.getMeetingByIcuMonitor(iCUMonitor.getId());
		model.addAttribute("meeting", meetingview);
		Short invitedState = 0;
		
		//得到所有受邀成员
		List<MeetingMember> meetingMembers = memberService.getMembersByMeetingId(meetingview.getId());
		this.sortMeetingMember(meetingMembers);
		List<UserInfo> noneAcceptPeople = new ArrayList<UserInfo>();
		List<UserInfo> acceptPeople = new ArrayList<UserInfo>();
		List<UserInfo> refusedAcceptPeople = new ArrayList<UserInfo>();
		UserInfo invitedUser = new UserInfo();
		
		//取出每一个受邀成员，并判断每一个受邀成员的状态
		for(MeetingMember meetingmember : meetingMembers){
			invitedState = meetingmember.getAttendState();
			invitedUser = meetingmember.getMember();
			if(invitedState == GlobalConstent.APPLICATION_STATE_NONE){
				noneAcceptPeople.add(invitedUser);
			}
			if(invitedState == GlobalConstent.APPLICATION_STATE_ACCEPT){
				acceptPeople.add(invitedUser);
			}
			if(invitedState == GlobalConstent.APPLICATION_STATE_REFUSED){
				refusedAcceptPeople.add(invitedUser);
			}
		}
		if(acceptPeople!=null&&!acceptPeople.isEmpty()){//判断已接受的
			int count = acceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				acceptPeople.add(user1);
				acceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				acceptPeople.add(user1);
			}
		}
		model.addAttribute("acceptPeople", acceptPeople);
		if(noneAcceptPeople!=null&&!noneAcceptPeople.isEmpty()){//判断未处理的
			int count = noneAcceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				noneAcceptPeople.add(user1);
				noneAcceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				noneAcceptPeople.add(user1);
			}
		}
		model.addAttribute("noneAcceptPeople", noneAcceptPeople);
		if(refusedAcceptPeople!=null&&!refusedAcceptPeople.isEmpty()){//判断已拒绝的
			int count = refusedAcceptPeople.size();
			if(count == 1 || count%3==1){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				UserInfo user2 = new UserInfo();
				user2.setName(" ");
				refusedAcceptPeople.add(user1);
				refusedAcceptPeople.add(user2);
			}else if(count==2||count%3==2){
				UserInfo user1 = new UserInfo();
				user1.setName(" ");
				refusedAcceptPeople.add(user1);
			}
		}
		model.addAttribute("refusedAcceptPeople", refusedAcceptPeople);	
		ICUMonitor iCUMonito = meetingview.getiCUMonitorId();
		Set<Accessories> temp = iCUMonito.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if(null != temp && temp.size() > 0){
			
			for(Accessories acc: temp){
				if(acc.getType().getId()!=2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}					
			}
			iCUMonito.setAccessories(accs);
		}
		model.addAttribute("application", iCUMonito);
		model.addAttribute("accsImage", accsImage);
		//取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if(iCUMonito.getIcuICD10() != null && !iCUMonito.getIcuICD10().isEmpty()){
			for (ICUICD10 applicationICD10 : iCUMonito.getIcuICD10()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		//取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if(iCUMonito.getMainDept()!=null){
			cv = new SelectItem();
			cv.setId(iCUMonito.getMainDept().getId().toString());
			cv.setName(iCUMonito.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if(iCUMonito.getDepts() != null && !iCUMonito.getDepts().isEmpty()){
			for (ICUDepartment appDept : iCUMonito.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
//		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//		model.addAttribute("meeting", meeting);

//		model.addAttribute("HAINA_URL", GlobalParam.haina_url);
	
	}
	private void icuMonitorRefusedORPending(Model model,Integer icuMontorId){

		ICUMonitor application = meeAdmDbdService.viewICUMonitor(icuMontorId);;
		//MeetingApplication application = meetingview.getApplicationId();
		model.addAttribute("application", application);
		Set<Accessories> temp = application.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if(null != temp && temp.size() > 0){
			
			for(Accessories acc: temp){
				if(acc.getType().getId()!=2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}					
			}
			application.setAccessories(accs);
		}
		model.addAttribute("application", application);
		model.addAttribute("accsImage", accsImage);
		//取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if(application.getIcuICD10() != null && !application.getIcuICD10().isEmpty()){
			for (ICUICD10 applicationICD10 : application.getIcuICD10()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		//取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if(application.getMainDept()!=null){
			cv = new SelectItem();
			cv.setId(application.getMainDept().getId().toString());
			cv.setName(application.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if(application.getDepts() != null && !application.getDepts().isEmpty()){
			for (ICUDepartment appDept : application.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
//		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//		model.addAttribute("meeting", meeting);

//		model.addAttribute("HAINA_URL", GlobalParam.haina_url);			
	
	}
	@Resource(type = ICUMonitorService.class)
	public void setiCUMonitService(ICUMonitorService iCUMonitorService) {
		this.iCUMonitorService = iCUMonitorService;
	}
	//add by wangzhenglin end
	@Resource(type = MeetingApplicationService.class)
	public void setMeetingApplicationService(MeetingApplicationService meetingApplicationService) {
		this.meetingApplicationService = meetingApplicationService;
	}

	@Resource(type = MeetingMemberService.class)
	public void setMeetingmemberervice(MeetingMemberService meetingmemberervice) {
		this.meetingmemberervice = meetingmemberervice;
	}

	@Resource(type = NoticeFactory.class)
	public void setNoticefactory(NoticeFactory noticefactory) {
		this.noticefactory = noticefactory;
	}

	@Resource(type = EvaluationService.class)
	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}

	@Resource(type = SatisfactionService.class)
	public void setSatisfactionService(SatisfactionService satisfactionService) {
		this.satisfactionService = satisfactionService;
	}

	@Resource(type = UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource(type = UserTypeService.class)
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}

	@Resource
	public void setMeeAdmDbdService(MeeAdmDbdService meeAdmDbdService) {
		this.meeAdmDbdService = meeAdmDbdService;
	}

	@Resource
	public void setIcd10dicService(Icd10DicService icd10dicService) {
		this.icd10dicService = icd10dicService;
	}

	@Resource
	public void setSatisfactionMainService(
			SatisfactionManagerService satisfactionManagerService) {
		this.satisfactionManagerService = satisfactionManagerService;
	}

	@Resource
	public void setDepartmentService(DepartmentService departmentService) {
		this.departmentService = departmentService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

    
	@Resource(type = ApplicationOperateService.class)
	public void setApplicationAddService(ApplicationOperateService applicationOperateService) {
		this.applicationOperateService = applicationOperateService;
	}
	
	/**
	 * @param memberService the memberService to set
	 */
	@Resource
	public void setMemberService(MeetingMemberService memberService) {
		this.memberService = memberService;
	}
	
	
	/**
	 * 申请远程探视
	 *  
	 */
	@RequestMapping(value="/icuVisit")
	public String icuVisit(HttpServletRequest request) {
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		request.setAttribute("user", user);
		return "unified/addIcuVisit";
	}
	/**
	 * 
	 * @param request
	 * @param application
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addIcuVisit")
	public String addIcuVisit(HttpServletRequest request, ICUVisitation application)throws Exception{
		UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		String expectedTime = request.getParameter("expectedtime");		
		try{
		    //增加病例讨论 
			iCUMonitorService.saveICUVisit(userinfo, expectedTime, application);			
			request.getSession().setAttribute("returnmessage", "申请远程探视成功！");
		}catch(Exception e){
			logger.error("申请远程探视失败", e);
			request.getSession().setAttribute("returnmessage", "申请远程探视失败！");
		}
		return "redirect:/unified/icuVisit";
	
	}
	
	/**
	 * 审核页面远程探视查看页面信息
	 * @param model
	 * @param meetingid
	 * @return
	 */
	@RequestMapping(value="/viewicuVisitofManager/{icuVisitId}")
	public String viewicuVisitofManager(Model model, @PathVariable Integer icuVisitId) {
		//根据Id查得ICUMonitor
		ICUVisitation visit = meeAdmDbdService.getICUVisitById(icuVisitId);
		model.addAttribute("meetingApplication", visit);
		//审核通过的远程探视
		if(visit!=null&&visit.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			Meeting meetingview = meeAdmDbdService.getMeetingByIcuVisitId(visit.getId());
			model.addAttribute("meeting", meetingview);
			ICUVisitation visit0 = meetingview.getiCUVisitationId();
			model.addAttribute("application", visit0);
		}
		//审核未通过或者等待审核的监护数据获取
		if((visit.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PENDING)) || (visit.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_REFUSED))){
			model.addAttribute("application", visit);
		}

		return "/meetingadmin/viewicuVisitofManager";
	}
	
	
	/**
	 * 已安排的远程探视
	 * @param model
	 * @param meetingid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/arrangedICUVisitView/{meetingid}")
	public String arrangedICUVisitView(Model model, @PathVariable Integer meetingid,HttpServletRequest request) throws Exception {
		UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		model.addAttribute("userinfor", user);
		Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
		model.addAttribute("meeting", meetingview);
		return "/meetingadmin/arrangedICUVisitView";
	}
}