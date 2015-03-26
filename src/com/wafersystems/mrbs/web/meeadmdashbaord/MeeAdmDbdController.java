package com.wafersystems.mrbs.web.meeadmdashbaord;

import java.io.IOException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.wafersystems.mcu.MCUParams;
import com.wafersystems.mcu.Packaging;
import com.wafersystems.mcu.conference.service.ConferenceService;
import com.wafersystems.mcu.conference.service.impl.ConferenceServiceImpl;
import com.wafersystems.mcu.participant.model.Participant;
import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.icu.ICUMonitorService;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.ApplicationOperateService;
import com.wafersystems.mrbs.service.meeting.MeetingApplicationService;
import com.wafersystems.mrbs.service.meeting.NoticeDetailService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.ApplicationDepartment;
import com.wafersystems.mrbs.vo.meeting.ApplicationICD10;
import com.wafersystems.mrbs.vo.meeting.ICD10DIC;
import com.wafersystems.mrbs.vo.meeting.ICUDepartment;
import com.wafersystems.mrbs.vo.meeting.ICUICD10;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;
import com.wafersystems.mrbs.vo.meeting.MeetingSummary;
import com.wafersystems.mrbs.vo.meeting.NoticeDetail;
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;
import com.wafersystems.mrbs.web.user.DeptController;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.NoticeInfo;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping(value = "/meeadmdbd")
public class MeeAdmDbdController {

	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

	private MeeAdmDbdService meeAdmDbdService;
	private UserService userService;
	private MeetingApplicationService applicationService;
	private ApplicationOperateService applicationOperateService;
	private NoticeDetailService noticeDetailService;
	private ICUMonitorService iCUMonitorService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static Collator collator = Collator.getInstance(Locale.CHINA);

	/**
	 * ajax方法获得模糊查询的用户数据
	 */
	@RequestMapping(value="/getUserSelectItem")
	@ResponseBody
	public List<ICD10DIC> getUserSelectItem(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			response.setCharacterEncoding("UTF-8");
			String queryInnaiUserName = request.getParameter("queryUserName");//获取速查条件
			String userIds = request.getParameter("userIds");//获取已选择的user的id
			String departmentKind = request.getParameter("departmentKind");//部门类型
			String notInUserId = "";
			if (!StrUtil.isEmptyStr(userIds)) {
				if (userIds.endsWith("|")) {
					userIds = userIds.substring(0, userIds.length() - 1);
				}
				String [] strUserIdObjs = userIds.split("\\|");
				int count = strUserIdObjs.length;
				
				for(int i=0;i<count;i++){
					notInUserId+="'"+strUserIdObjs[i]+"',";
				}
			}
			if(!StrUtil.isEmptyStr(notInUserId)&&notInUserId.length()>1){
				notInUserId = notInUserId.substring(0,notInUserId.length()-1);
			}
			List<SelectItem> list = userService.getUserListByUserNameOrDeptName(departmentKind, queryInnaiUserName, notInUserId);
			if(String.valueOf(GlobalConstent.HIS_DEPT_CODE).equalsIgnoreCase(departmentKind)){
				int max = list.size();
				max = list.size() > 100 ? 100 : max;
				list = list.subList(0, max);
			}
			String s = "";
			ObjectMapper mapper = new ObjectMapper();
			SerializationConfig sc = mapper.getSerializationConfig();
			sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
			mapper.setSerializationConfig(sc);
			s = mapper.writeValueAsString(list);
			response.getWriter().write(s);
		}catch(Exception e){
			logger.error("Error MeeAdmDbdController.getUserSelectItem",e);
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdController.getUserSelectItem",e);
		}
		return null;
	}
	
	/**
	 * ajax方法判断审核的会议预约时间是否在已经预约过的会议时间中
	 */
	@RequestMapping(value="/checkTimeRepeat")
	@ResponseBody
	public List<ICD10DIC> checkTimeRepeat(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try{
			response.setCharacterEncoding("UTF-8");
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			String exceptMeetingId = request.getParameter("exceptMeetingId");//要除去检核的当前meeting
			String meetingTypestr = request.getParameter("meetingTypestr");
			int count  = 0;
			if(!meetingTypestr.equals("4")){
				count = meeAdmDbdService.getMeetingCountByStartTimeAndEndTime(startTime, endTime,meetingRoomId,exceptMeetingId);
			}
			String s = "";
			if(count>0){
				s = "repeat";
			}else{
				s = "ok";
			}
			response.getWriter().write(s);
			response.getWriter().flush();
		}catch(Exception e){
			logger.error("Error MeeAdmDbdController.checkTimeRepeat",e);
			e.printStackTrace();
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdController.checkTimeRepeat",e);
		}
		return null;
	}
	
	/**会议管理员申请视频讲座页面*/
	@RequestMapping(value="/applicationVideo")
	public String applicationVideo(HttpServletRequest request, Model model){	
		return "/meetingadmin/videoApplication";
	}

	//增加视频讲座
	@RequestMapping(value="/addVideoApplicationPurpose")
	public String addVideoApplicationPurpose(HttpServletRequest request, VideoMeetingApp videoMeetingApp){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String[] files=(String[])request.getParameterValues("myfiles");//附件
			//讲座内容将双引号做转意处理，否则页面title属性无法正常显示
			String content=videoMeetingApp.getLectureContent().replace("\"", "&quot;").replace("<", " &lt;");
			videoMeetingApp.setLectureContent(content);
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			applicationOperateService.saveVideoApplicationByManager(user, files, videoMeetingApp,startTime,endTime,selectedUserIds,meetingRoomId);
			request.getSession().setAttribute("returnmessage", "视频讲座申请成功！");
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "视频讲座申请失败！");
			e.printStackTrace();			
		}
		return "meetingadmin/videoApplication";
	}
	
	/**视频讲座补录页面*/
	@RequestMapping(value="/collectionVideoLectures")
	public String collectionVideoLectures(HttpServletRequest request, Model model){
		String cuurDate = dateFormat.format(new Date());
		model.addAttribute("cuurDate", cuurDate);
		return "/meetingadmin/videoLecturesCollection";
	}
	
	//补录视频讲座
	@RequestMapping(value="/addVideoLecturesCollection")
	public String addVideoLecturesCollection(HttpServletRequest request, VideoMeetingApp videoMeetingApp){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String[] files=(String[])request.getParameterValues("myfiles");//附件
			//讲座内容将双引号做转意处理，否则页面title属性无法正常显示
			String content=videoMeetingApp.getLectureContent().replace("\"", "&quot;");
			videoMeetingApp.setLectureContent(content);
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			applicationOperateService.saveVideoLecturesCollection(user, files, videoMeetingApp,startTime,endTime,selectedUserIds,meetingRoomId);
			request.getSession().setAttribute("returnmessage", "视频讲座补录成功！");
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "视频讲座补录失败！");
			e.printStackTrace();			
		}
		return "meetingadmin/videoLecturesCollection";
	}
	
	
	//打开审核通过并且未结束的视频讲座修改画面
	@RequestMapping(value = "/viewvedioappmeetingForPass/{meetingId}")
	public String viewVideoAppMeetingForPass(HttpServletRequest request,@PathVariable Integer meetingId) {
		VideoMeetingApp vmma = this.applicationService.getVideoMeetingApplicationById(meetingId);
		Date currDate = new Date();
		List<SelectItem> expertsList = new ArrayList<SelectItem>();
		List<SelectItem> communityList = new ArrayList<SelectItem>();
		//审核通过并且未结束
		long starttime = 0;
		if(vmma!=null&&vmma.getState().toString().equals(String.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_PASS))
				&&vmma.getEndTime().getTime()>=currDate.getTime()){
			Meeting meeting = meeAdmDbdService.getMeetingByVideoAppId(vmma.getId());
			if(meeting!=null){
				Set<MeetingMember> members = meeting.getMembers();
				MeetingMember meetingmember=null;
				SelectItem selectItem = null;
				for(Iterator<MeetingMember> it=members.iterator();it.hasNext();){
					meetingmember = it.next();
					UserInfo userInfo = meetingmember.getMember();
					selectItem = new SelectItem();
					selectItem.setId(userInfo.getUserId());
					selectItem.setName(userInfo.getName()+"("+userInfo.getDeptId().getName()+")");
					String parentDeptCode = userInfo.getDeptId().getParentDeptCode();
					if(!StrUtil.isEmptyStr(parentDeptCode)&&parentDeptCode.length()>6){
						parentDeptCode = parentDeptCode.substring(0, 6);
					}
					if((parentDeptCode.equals(GlobalConstent.HIS_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.HIS_DEPT_CODE))
						    &&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						expertsList.add(selectItem);
					}else if((parentDeptCode.equals(GlobalConstent.COMMUNITY_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.COMMUNITY_DEPT_CODE))
							&&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						communityList.add(selectItem);
					}
				 }
				starttime = meeting.getStartTime().getTime();
				request.setAttribute("exceptMeetingId", meeting.getId());
				request.setAttribute("meeting", meeting);
			}
		}
		//保存选中的所有的邀请人
		String  selectstr = "";
		if(expertsList != null && expertsList.size()>0){
			for(int i=0; i<expertsList.size();i++){
				SelectItem obj = expertsList.get(i);
				selectstr +=obj.getId()+"|";
			}
			
		}
		if(communityList != null && communityList.size() > 0){
			for(int i=0; i<communityList.size();i++){
				SelectItem obj = communityList.get(i);
				selectstr +=obj.getId()+"|";
			}
		}
		
		//读取会议呼叫mcu提前时间量
		//会议在开始前nctime时间内不能修改
		long  nctime = MCUParams.getInstance().getTime(); 
		long dt = new Date().getTime();
		long tp = (starttime- dt )/(60*1000);
		boolean  flag = false;
		if(tp <= nctime){
			flag = false;
		}else{
			flag = true;
		}
		request.setAttribute("checkUpdateStartTime", flag);
		request.setAttribute("selectstr", selectstr);
		request.setAttribute("currDate", new Date());
		request.setAttribute("expertsList", expertsList);
		request.setAttribute("communityList", communityList);
		request.setAttribute("vmapp", vmma);
		return "/meetingadmin/editVideoApplicationByManager";
	}
	
	//修改审核过但未结束的视频讲座	
	@RequestMapping(value="/editVideoApplicationForPass")
	public String editVideoApplicationForPass(HttpServletRequest request, VideoMeetingApp videoMeetingApp){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String[] files=(String[])request.getParameterValues("myfiles");//附件
			//讲座内容将双引号做转意处理，否则页面title属性无法正常显示
			String content=videoMeetingApp.getLectureContent().replace("\"", "&quot;").replace("<", " &lt;");
			videoMeetingApp.setLectureContent(content);
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			String userId = request.getParameter("userId");
			String selectstr = request.getParameter("selectedstr");
			request.getSession().setAttribute("returnmessage", "视频讲座修改成功！");
			//根据修改时间和选择邀请人修改MCU
			//如果增加邀请人，启动网真设备，删除邀请人关闭设备
			//修改结束时间，根据时间关闭或启动设备
			//得到修改前所有邀请的人
			String[] olditem = selectstr.split("\\|");
			String[] newitem = selectedUserIds.split("\\|");
			String tselectedUserIds = this.getSelectedUserId(olditem,newitem,selectedUserIds);
			String newselectedUserIds = "";
			if(tselectedUserIds !=null && !"".equals(tselectedUserIds)){
				newselectedUserIds = tselectedUserIds;
			}else{
				newselectedUserIds = selectedUserIds;
			}
			applicationOperateService.updateVideoApplication(user, files, videoMeetingApp,startTime,endTime,newselectedUserIds,meetingRoomId);
			Meeting meeting = meeAdmDbdService.getMeetingByVideoAppId(videoMeetingApp.getId());
			this.ManageMcu(meeting, olditem, newitem);
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "视频讲座修改失败！");
			e.printStackTrace();			
		}		
		return "redirect:/meeadIndex/videoLecturesQuery/";
	}
	
	//打开修改非管理员提交的未审核视频讲座
	@RequestMapping(value = "/viewVideoAppMeeting/{meetingId}")
	public String viewVideoAppMeeting(HttpServletRequest request,@PathVariable Integer meetingId) {
		VideoMeetingApp vmma = this.applicationService.getVideoMeetingApplicationById(meetingId);
		request.setAttribute("vmapp", vmma);
		return "/meetingadmin/editVideoApplication";
	}
	
	//修改非管理员提交的未审核视频讲座
	@RequestMapping("/editVideoApplication")
	public String editVideoApplication(HttpServletRequest request, HttpServletResponse response) throws Exception{
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
		return "redirect:/meeadIndex/videoLecturesQuery/";
	}
	
	//打开审核通过并且未结束的病历讨论修改画面
	@RequestMapping(value = "/applicationEditForPass/{applicationId}")
	public String applicationEditForPass(HttpServletRequest request,@PathVariable Integer applicationId, Model model) {
		MeetingApplication application = applicationService.getMeetingApplicationById(applicationId);
		
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
		
		
		Date currDate = new Date();
		List<SelectItem> expertsList = new ArrayList<SelectItem>();
		List<SelectItem> communityList = new ArrayList<SelectItem>();
		List<SelectItem> intersList = new ArrayList<SelectItem>();
		long  starttime = 0;
		//审核通过并且未结束
		if(application!=null&&application.getState().toString().equals(String.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_PASS))
				&&application.getEndTime().getTime()>=currDate.getTime()){
			Meeting meeting = meeAdmDbdService.getMeetingByApplicationId(application.getId());
			if(meeting!=null){
				Set<MeetingMember> members = meeting.getMembers();
				MeetingMember meetingmember=null;
				SelectItem selectItem = null;
				for(Iterator<MeetingMember> it=members.iterator();it.hasNext();){
					meetingmember = it.next();
					UserInfo userInfo = meetingmember.getMember();
					selectItem = new SelectItem();
					selectItem.setId(userInfo.getUserId());
					selectItem.setName(userInfo.getName()+"("+userInfo.getDeptId().getName()+")");
					String parentDeptCode = userInfo.getDeptId().getParentDeptCode();
					if(!StrUtil.isEmptyStr(parentDeptCode)&&parentDeptCode.length()>6){
						parentDeptCode = parentDeptCode.substring(0, 6);
					}
					if((parentDeptCode.equals(GlobalConstent.HIS_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.HIS_DEPT_CODE))
						    &&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						expertsList.add(selectItem);
					}else if((parentDeptCode.equals(GlobalConstent.COMMUNITY_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.COMMUNITY_DEPT_CODE))
							&&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						communityList.add(selectItem);
					}else if((parentDeptCode.equals(GlobalConstent.OUT_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.OUT_DEPT_CODE))
							&&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						intersList.add(selectItem);
					}
				 }
				starttime = meeting.getStartTime().getTime();
				request.setAttribute("exceptMeetingId", meeting.getId());
				request.setAttribute("meeting", meeting);
			}
		}
		//保存选中的所有的邀请人
		String  selectstr = "";
		if(expertsList != null && expertsList.size()>0){
			for(int i=0; i<expertsList.size();i++){
				SelectItem obj = expertsList.get(i);
				selectstr +=obj.getId()+"|";
			}
			
		}
		if(communityList != null && communityList.size() > 0){
			for(int i=0; i<communityList.size();i++){
				SelectItem obj = communityList.get(i);
				selectstr +=obj.getId()+"|";
			}
		}
		if(intersList != null && intersList.size() > 0){
			for(int i=0; i<intersList.size();i++){
				SelectItem obj = intersList.get(i);
				selectstr +=obj.getId()+"|";
			}
		}
		//读取会议呼叫mcu提前时间量
		//会议在开始前nctime时间内不能修改
		long  nctime = MCUParams.getInstance().getTime(); 
		long dt = new Date().getTime();
		long tp = (starttime- dt )/(60*1000);
		boolean  flag = false;
		if(tp <= nctime){
			flag = false;
		}else{
			flag = true;
		}
		request.setAttribute("selectstr", selectstr);
		request.setAttribute("currDate", new Date());
		request.setAttribute("expertsList", expertsList);
		request.setAttribute("communityList", communityList);
		request.setAttribute("intersList", intersList);
		request.setAttribute("expectedTime", dateFormat.format(application.getExpectedTime()));
		request.setAttribute("checkUpdateStartTime", flag);
		return "/meetingadmin/applicationEditForPass";
	}
	
	//修改审核过但未结束的病历讨论	
	@RequestMapping(value="/editApplicationForPass")
	public String editApplicationForPass(HttpServletRequest request, MeetingApplication application){
		try{
			if(application.getId()!=null){
					
				String[] files=(String[])request.getParameterValues("myfiles");
				String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
				String[] icd10Dic = request.getParameterValues("icd10Selected");
				String[] deptmentid = request.getParameterValues("departmentSelected");
				String selectstr = request.getParameter("selectedstr");
				String userId = request.getParameter("userId");
				UserInfo userinfo = userService.getUserByUserId(userId);
				String startTime = request.getParameter("startTime");//开始时间
				String endTime = request.getParameter("endTime");//结束时间
				String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
				String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
				String expectedTime = request.getParameter("expectedTime");//会议室Id
				//根据修改时间和选择邀请人修改MCU
				//如果增加邀请人，启动网真设备，删除邀请人关闭设备
				//修改结束时间，根据时间关闭或启动设备
				//得到修改前所有邀请的人
				String[] olditem = selectstr.split("\\|");
				String[] newitem = selectedUserIds.split("\\|");
				String tselectedUserIds = this.getSelectedUserId(olditem,newitem,selectedUserIds);
				String newselectedUserIds = "";
				if(tselectedUserIds !=null && !"".equals(tselectedUserIds)){
					newselectedUserIds = tselectedUserIds;
				}else{
					newselectedUserIds = selectedUserIds;
				}
				 
				//修改病历讨论
 			    //applicationOperateService.updateMeetingApplication(userinfo, files, hainaDatas,expectedTime, deptmentid, icd10Dic, application, startTime, endTime, selectedUserIds, meetingRoomId);
				applicationOperateService.updateMeetingApplication(userinfo, files, hainaDatas,expectedTime, deptmentid, icd10Dic, application, startTime, endTime, newselectedUserIds, meetingRoomId);
				Meeting meeting = meeAdmDbdService.viewMeetingByApplicationId(application.getId());
				this.ManageMcu(meeting, olditem, newitem);
			}
			request.getSession().setAttribute("returnmessage", "病历讨论修改成功！");
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "病历讨论修改失败！");
			e.printStackTrace();			
		}		
		return "redirect:/meeadIndex/teleconferenceQuery/";
	}
	
	//打开未审核的病历讨论修改画面
	@RequestMapping(value = "/applicationEdit/{applicationId}")
	public String applicationEdit(HttpServletRequest request,@PathVariable Integer applicationId, Model model) {
		MeetingApplication application = applicationService.getMeetingApplicationById(applicationId);
		
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
		return "/meetingadmin/applicationEdit";
	}
	
	//修改未审核的病历讨论
	@RequestMapping("/editApplication")
	public String editApplication(HttpServletRequest request, HttpServletResponse responset, MeetingApplication application) throws Exception{
		try{
			if(application.getId()!=null){
					String expectedTime = request.getParameter("expectedtime");
					
					String[] files=(String[])request.getParameterValues("myfiles");
					String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
					String[] icd10Dic = request.getParameterValues("icd10Selected");
					String[] deptmentid = request.getParameterValues("departmentSelected");
					String userId = request.getParameter("userId");
					UserInfo userinfo = userService.getUserByUserId(userId);
					//修改病历讨论
					applicationOperateService.updateMeetingApplication(userinfo, expectedTime, files, hainaDatas, deptmentid, icd10Dic, application);
			}
			request.getSession().setAttribute("returnmessage", "修改病历讨论成功！");
		}catch(Exception e){
			logger.error("修改病历讨论失败", e);
			request.getSession().setAttribute("returnmessage", "修改病历讨论失败！");
		}	
		logger.debug("Execute over editApplication");
		return "redirect:/meeadIndex/teleconferenceQuery/";
	}
	
	/**病历讨论补录页面*/
	@RequestMapping(value="/consultationCollection")
	public String consultationCollection(HttpServletRequest request, Model model){
		String cuurDate = dateFormat.format(new Date());
		model.addAttribute("cuurDate", cuurDate);
		return "/meetingadmin/consultationCollection";
	}
	
	//补录病历讨论
	@RequestMapping(value="/addConsultationCollection")
	public String addApplicationCollection(HttpServletRequest request, MeetingApplication application){
		try{
			UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String[] files=(String[])request.getParameterValues("myfiles");
			String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
			String[] deptmentid = request.getParameterValues("departmentSelected");
			String[] icd10Dic = request.getParameterValues("icd10Selected");
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			applicationOperateService.saveMeetingApplicationCollection(userinfo, files, hainaDatas, deptmentid, icd10Dic, application, startTime, endTime, selectedUserIds, meetingRoomId);
			request.getSession().setAttribute("returnmessage", "病历讨论补录成功！");
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "病历讨论补录失败！");
			e.printStackTrace();			
		}
		return "meetingadmin/consultationCollection";
	}	
	// 定向病历讨论通知画面
	@RequestMapping(value = "/teleconferenceAuditNotice")
	public String teleconferenceAuditNotice(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		Meeting meeting = meeAdmDbdService.getMeetingByApplicationId(id);
		if(meeting!=null){
			Set<MeetingMember> members = meeting.getMembers();
			MeetingMember meetingmember=null;
//			String expertsName = "";
//			String internationalCourtName = "";
//			String communityName = "";
			//flag1 标记是否存在处于删除状态或不允许参会状态的受邀院内专家，true为不存在，false为存在
			boolean flag1=true;
			//flag2 标记是否存在处于删除状态或不允许参会状态的受邀院际专家，true为不存在，false为存在
			boolean flag2=true;
			//flag3标记是否存在处于删除状态或不允许参会状态的受邀共同体，true为不存在，false为存在
			boolean flag3=true;
			List<SelectItem> expertsList = new ArrayList<SelectItem>();
			List<SelectItem> interList = new ArrayList<SelectItem>();
			List<SelectItem> communityList = new ArrayList<SelectItem>();
			SelectItem selectItem = null;
			for(Iterator<MeetingMember> it=members.iterator();it.hasNext();){
				meetingmember = it.next();
				UserInfo userInfo = meetingmember.getMember();
				selectItem = new SelectItem();
				selectItem.setId(userInfo.getUserId());
				selectItem.setName(userInfo.getName()+"("+userInfo.getDeptId().getName()+")");
				String parentDeptCode = userInfo.getDeptId().getParentDeptCode();
				if(!StrUtil.isEmptyStr(parentDeptCode)&&parentDeptCode.length()>6){
					parentDeptCode = parentDeptCode.substring(0, 6);
				}
				if(parentDeptCode.equals(GlobalConstent.HIS_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.HIS_DEPT_CODE)){					
					if(userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						//添加正常状态的院内专家
						expertsList.add(selectItem);
					}else{
						//存在处于删除状态或不允许参会状态的受邀院内专家
						flag1=false;
					}					
				}else if(parentDeptCode.equals(GlobalConstent.OUT_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.OUT_DEPT_CODE)){
					if(userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						//添加正常状态的院际专家
						interList.add(selectItem);
					}else{
						//存在处于删除状态或不允许参会状态的受邀院际专家
						flag2=false;
					}										
				}else if(parentDeptCode.equals(GlobalConstent.COMMUNITY_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.COMMUNITY_DEPT_CODE)){
					if(userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
							&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
						//添加正常状态的共同体专家
						communityList.add(selectItem);
					}else{
						//存在处于删除状态或不允许参会状态的受邀共同体
						flag3=false;
					}					
				}
			 }
			if((expertsList==null||expertsList.isEmpty())&&flag1){
				expertsList = null;
			}
			if((interList==null||interList.isEmpty())&&flag2){
				interList = null;
			}
			if((communityList==null||communityList.isEmpty())&&flag3){
				communityList = null;
			}
			request.setAttribute("expertsList", expertsList);
			request.setAttribute("interList", interList);
			request.setAttribute("communityList", communityList);
		}
		String starttime="";
		if(null!=meeting.getStartTime())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getStartTime());
		}
		String []param={starttime,meeting.getTitle()};
		String unifiedVaue = NoticeInfo.getMessage("successMedicalRecordsNotice_content",param);
		request.setAttribute("unifiedVaue", unifiedVaue);
		request.setAttribute("meetingApplicationId", id);
		
		return "/meetingadmin/teleconferenceAuditNotice";
	}

	// 定向病历讨论通知画面
	@RequestMapping(value = "/teleconferenceNotice")
	public String teleconferenceNotice(HttpServletRequest request,HttpServletResponse response)throws Exception{
		response.setCharacterEncoding("UTF-8");
		
		String expertsNotice = request.getParameter("expertsNotice");
		//通知不为空时，对“<”作转意处理，解决通知内容为html时无法显示的问题
		if(expertsNotice!=null) 
			expertsNotice=expertsNotice.replace("<", " &lt;");
		
		String internationalCourtNotice = request.getParameter("internationalCourtNotice");
		//通知不为空时，对“<”作转意处理，解决通知内容为html时无法显示的问题
		if(internationalCourtNotice!=null)
			internationalCourtNotice=internationalCourtNotice.replace("<", " &lt;");
		
		String communityNotice = request.getParameter("communityNotice");
		//通知不为空时，对“<”作转意处理，解决通知内容为html时无法显示的问题
		if(communityNotice!=null)
			communityNotice=communityNotice.replace("<", " &lt;");
		
		String meetingApplicationId = request.getParameter("meetingApplicationId");
		String expertsSelectedUserIds = request.getParameter("selectedInnaiUserIds");//选择的院内专家
		String communitySelectedUserIds = request.getParameter("selectedcommunityUserIds");//选择的共同体
		String interSelectedUserIds = request.getParameter("selectedInterUserIds");//选择的院际专家
		Integer id = Integer.valueOf(request.getParameter("meetingApplicationId"));
		String sendTime=dateFormat.format(new Date());
		
		try {
			meeAdmDbdService.sendApplicationNotice(Integer.parseInt(meetingApplicationId), expertsNotice, communityNotice, internationalCourtNotice,expertsSelectedUserIds,communitySelectedUserIds,interSelectedUserIds);
			noticeDetailService.saveNoticeDetail(id, sendTime, expertsSelectedUserIds, communitySelectedUserIds, interSelectedUserIds);
			response.getWriter().write("success");
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error("Error MeeAdmDbdController.teleconferenceNotice",e);
			response.getWriter().write("fail");
			response.getWriter().flush();
		}
		return null;
	}
	
	// 定向病历讨论通知画面
	@RequestMapping(value = "/noticeDetail")
	public String noticeDetail(HttpServletRequest request) {
		String meetingApplicationId=request.getParameter("refusemeetingid");
		List<NoticeDetail> noticeDetailList=noticeDetailService.getNoticeDetailByMeetingApplicationId(Integer.valueOf(meetingApplicationId));
		int i=1;
		for(NoticeDetail noticeDetail: noticeDetailList){
			String[] ids = noticeDetail.getUserName().split("\\|");			
			if(ids.length>0){
				String name="";
				for(String userId :ids){
					UserInfo user = userService.getUserByUserId(userId);
					name+=user.getName()+"("+user.getDeptId().getName()+")"+",";
				}
				if(name.length()>0){
					name=name.substring(0, name.length()-1);
					noticeDetail.setUserName(name);
					noticeDetail.setId(i);
					i++;
				}
				
			}
		}
		request.setAttribute("noticeDetailList", noticeDetailList);
		return "/meetingadmin/noticeDetail";
	}

	// 定向视频讲座通知画面
	@RequestMapping(value = "/vidoAuditNotice")
	public String vidoAuditNotice(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		Meeting meeting = meeAdmDbdService.getMeetingByVideoAppId(id);
		if(meeting!=null){
			Set<MeetingMember> members = meeting.getMembers();
			MeetingMember meetingmember=null;
//			String expertsName = "";
//			String communityName = "";
			List<SelectItem> expertsList = new ArrayList<SelectItem>();
			List<SelectItem> communityList = new ArrayList<SelectItem>();
			SelectItem selectItem = null;
			for(Iterator<MeetingMember> it=members.iterator();it.hasNext();){
				meetingmember = it.next();
				UserInfo userInfo = meetingmember.getMember();
				selectItem = new SelectItem();
				selectItem.setId(userInfo.getUserId());
				selectItem.setName(userInfo.getName()+"("+userInfo.getDeptId().getName()+")");
				String parentDeptCode = userInfo.getDeptId().getParentDeptCode();
				if(!StrUtil.isEmptyStr(parentDeptCode)&&parentDeptCode.length()>6){
					parentDeptCode = parentDeptCode.substring(0, 6);
				}
				if((parentDeptCode.equals(GlobalConstent.HIS_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.HIS_DEPT_CODE))
					    &&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
						&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
					expertsList.add(selectItem);
				}else if((parentDeptCode.equals(GlobalConstent.COMMUNITY_DEPT_CODE)||userInfo.getDeptId().getDeptcode().equals(GlobalConstent.COMMUNITY_DEPT_CODE))
						&&userInfo.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
						&&userInfo.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
					communityList.add(selectItem);
				}
			 }
			//if(expertsList==null||expertsList.isEmpty()){
			//	expertsList = null;
			//}
			//if(communityList==null||communityList.isEmpty()){
			//	communityList = null;
			//}
			request.setAttribute("expertsList", expertsList);
			request.setAttribute("communityList", communityList);
		}
		String starttime="";
		if(null!=meeting.getStartTime())
		{
			starttime=DateUtil.getDateTimeStr(meeting.getStartTime());
		}
		String []param={starttime,meeting.getTitle()};
		String unifiedVaue = NoticeInfo.getMessage("successVoidNotice_content",param);
		request.setAttribute("unifiedVaue", unifiedVaue);
		request.setAttribute("meetingApplicationId", id);
		
		return "/meetingadmin/vidoAuditNotice";
	}

	// 定向视频讲座通知画面
	@RequestMapping(value = "/vidoNotice")
	public String vidoNotice(HttpServletRequest request,HttpServletResponse response) throws Exception{
		response.setCharacterEncoding("UTF-8");		
		String expertsNotice = request.getParameter("expertsNotice");
		//通知不为空时，对“<”作转意处理，解决通知内容为html时无法显示的问题
		if(expertsNotice!=null)
			expertsNotice=expertsNotice.replace("<", " &lt;");
		
		String communityNotice = request.getParameter("communityNotice");
		//通知不为空时，对“<”作转意处理，解决通知内容为html时无法显示的问题
		if(communityNotice!=null)
			communityNotice=communityNotice.replace("<", " &lt;");
		
		String vidoApplicationId = request.getParameter("vidoApplicationId");
		String expertsSelectedUserIds = request.getParameter("selectedInnaiUserIds");//选择的院内专家
		String communitySelectedUserIds = request.getParameter("selectedcommunityUserIds");//选择的共同体
		try {
			meeAdmDbdService.sendVideoNotice(Integer.parseInt(vidoApplicationId), expertsNotice, communityNotice, expertsNotice,expertsSelectedUserIds,communitySelectedUserIds);
			response.getWriter().write("success");
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error("Error MeeAdmDbdController.vidoNotice",e);
			response.getWriter().write("fail");
			response.getWriter().flush();
		}
		return null;
	}

	//审批病历讨论
	@ResponseBody
	@RequestMapping(value = "/auditCaseDiscussion")
	public Map<String, Object> auditCaseDiscussion(HttpServletRequest request,HttpServletResponse response) {
		try{
			response.setCharacterEncoding("UTF-8");
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			
			String meetingApplicationId = request.getParameter("meetingApplicationId");//要审核的病历讨论Id
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			
			if(!StrUtil.isEmptyStr(meetingApplicationId)&&!StrUtil.isEmptyStr(startTime)
					&&!StrUtil.isEmptyStr(endTime)&&!StrUtil.isEmptyStr(selectedUserIds)
					&&!StrUtil.isEmptyStr(meetingRoomId)){
				this.meeAdmDbdService.saveAuditCaseDiscussion(user, meetingApplicationId,startTime,endTime,selectedUserIds,meetingRoomId);
			}
			
			Map<String, Object> mapreturn = new HashMap<String, Object>();
			mapreturn.put("flag", true);
			return mapreturn;
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdController.saveAuditCaseDiscussion",e);
		}
		Map<String, Object> mapreturn = new HashMap<String, Object>();
		mapreturn.put("flag", false);
		return mapreturn;
	}

	// 审批通过
	@ResponseBody
	@RequestMapping(value = "/auditVideoLectures")
	public Map<String, Object> auditVideoLectures(HttpServletRequest request,HttpServletResponse response) {
		try{
			response.setCharacterEncoding("UTF-8");
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			
			String videoMeetingApplicationId = request.getParameter("videoMeetingApplicationId");//要审核的病历讨论Id
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			if(!StrUtil.isEmptyStr(videoMeetingApplicationId)&&!StrUtil.isEmptyStr(startTime)
					&&!StrUtil.isEmptyStr(endTime)&&!StrUtil.isEmptyStr(selectedUserIds)
					&&!StrUtil.isEmptyStr(meetingRoomId)){
				this.meeAdmDbdService.saveAuditVideoLectures(user, videoMeetingApplicationId,startTime,endTime,selectedUserIds,meetingRoomId);
			}
			
			Map<String, Object> mapreturn = new HashMap<String, Object>();
			mapreturn.put("flag", true);
			return mapreturn;
		}catch(Throwable e){
			logger.error("Error MeeAdmDbdController.saveAuditCaseDiscussion",e);
		}
		Map<String, Object> mapreturn = new HashMap<String, Object>();
		mapreturn.put("flag", false);
		return mapreturn;
	}

	// 根据科室编号得到一个用户
	@ResponseBody
	@RequestMapping(value = "/keshiUser")
	public Department getUserByDeptId(HttpServletRequest request,HttpServletResponse response, String deptId) {
		response.setCharacterEncoding("UTF-8");
		try {
			Department dept = this.meeAdmDbdService.getDeptByDeptId(Integer.valueOf(deptId));
			String deptCode = dept.getDeptcode();
			response.getWriter().append(deptCode).flush();
		} catch (IOException e) {
			logger.error("--------------->>>>>>getUserByDeptId()方法出錯！");
			//e.printStackTrace();
		}
		return null;
	}

	// 得到所有参会专家的科室
	@ResponseBody
	@RequestMapping(value = "/expertkeshi")
	public String getAlldept(HttpServletRequest request,HttpServletResponse response) {
		String s = "";
		List<Department> depts;
		List<CopyToView> list = new ArrayList<CopyToView>();
		CopyToView cv;
			try {
				String departvalue = request.getParameter("departvalue");
				if(departvalue != null && !departvalue.trim().equals("")){
					depts = this.meeAdmDbdService.getdeptmentunified(departvalue);
					for(Department d : depts){
						cv = new CopyToView();
						cv.setId(d.getId().toString());
						cv.setName(d.getName());	
						list.add(cv);
					}
					ObjectMapper mapper = new ObjectMapper();
					SerializationConfig sc = mapper.getSerializationConfig();
					sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
					mapper.setSerializationConfig(sc);
					s = mapper.writeValueAsString(list);
					response.getWriter().write(s);
				}else{
					depts = this.meeAdmDbdService.getAllDepts();
					for(Department d : depts){
						cv = new CopyToView();
						cv.setId(d.getId().toString());
						cv.setName(d.getName());	
						list.add(cv);
					}
					ObjectMapper mapper = new ObjectMapper();
					SerializationConfig sc = mapper.getSerializationConfig();
					sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
					mapper.setSerializationConfig(sc);
					s = mapper.writeValueAsString(list);
					response.getWriter().write(s);
				}
				
				} catch (Exception e) {
					e.printStackTrace();
				} 			
		return null;
	}

	// 根据用户编号得到一个用户
	@ResponseBody
	@RequestMapping(value = "/obtainUser")
	public UserInfo getUserByUserId(HttpServletRequest request,HttpServletResponse response, String idNum) {
		response.setCharacterEncoding("UTF-8");
		UserInfo user = this.userService.getUserByUserId(idNum);
		String deptCode = user.getDeptId().getDeptcode();
		Department dept = this.meeAdmDbdService.getUserInfoByDepartId(user.getDeptId().getId().toString());
		try {
			if (dept.getSubDepts().size() == 0) {
				try {
					response.getWriter().append(deptCode).flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Set<Department> depts = this.meeAdmDbdService.getUserInfoByParentDepartCode(dept.getDeptcode());
				String ks = "";
				if (dept.getUsers().size() != 0) {
					ks = dept.getDeptcode();
					try {
						response.getWriter().append(ks).flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					for (Department keshi : depts) {
						ks = keshi.getDeptcode();
						if (ks == deptCode) {
							try {
								response.getWriter().append(ks).flush();
							} catch (IOException e) {
								logger.error("--------------->>>>>>getUserByUserId()方法出錯！");
								//e.printStackTrace();
							}
						}
					}
				}
			}			
		} catch (Exception e) {
			logger.error("--------------->>>>>>getUserByUserId()方法錯誤！");
			//e.printStackTrace();
		}
		return null;
	}

	// 定向到审批通过页面
	@RequestMapping(value = "/redirctmeetingpass")
	public String addPassValue(HttpServletRequest request) {
		String id = request.getParameter("requestmeetid");
		request.setAttribute("requestmeetid_add", id);

		String meetingappserchflag = request.getParameter("meetingappserchflag");
		request.setAttribute("meetingappserchflag", meetingappserchflag);

		MeetingApplication ma = applicationService.getMeetingApplicationById(Integer.parseInt(id));
		UserInfo requestUser = ma.getRequester();
		if(requestUser!=null&&requestUser.getUserType().getValue().toString().equals(String.valueOf(GlobalConstent.USER_TYPE_PROFESSIONAL))
				&&requestUser.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
				&&requestUser.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
			request.setAttribute("innaiRequestUser", requestUser);
		}else if(requestUser!=null&&requestUser.getUserType().getValue().toString().equals(String.valueOf(GlobalConstent.USER_TYPE_UNION))
				&&requestUser.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
				&&requestUser.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
			request.setAttribute("commonRequestUser", requestUser);
		}
		try {
			List<Meeting> meetings = meeAdmDbdService.getMeetingOrderByStartTimeDesc();
			if(meetings != null && !meetings.isEmpty()){
				int meetingsCount = meetings.size();
				if(meetingsCount == 1 || meetingsCount % 2 == 1){
					Meeting m = new Meeting();
					m.setStartTime(null);
					m.setEndTime(null);
					meetings.add(m);
				}
			}
			request.setAttribute("meetings", meetings);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		request.setAttribute("mApplication", ma);
		return "/meetingadmin/meetingpass";
	}

	// 定向到视频讲座审批通过页面
	@RequestMapping(value = "/redirctvideomeetingpass")
	public String addVideoPassValue(HttpServletRequest request) {
		String id = request.getParameter("requestmeetid");
		request.setAttribute("requestmeetid_add", id);

		VideoMeetingApp ma = applicationService.getVideoMeetingApplicationById(Integer.parseInt(id));
//		if(requestUser!=null&&requestUser.getUserType().getValue().toString().equals(String.valueOf(GlobalConstent.USER_TYPE_PROFESSIONAL))){
//			request.setAttribute("innaiRequestUser", requestUser);
//		}
		UserInfo requestUser = ma.getVideoRequester();//申请人
		UserInfo mainSpeakerUser = ma.getUserName();//主讲人
		if(mainSpeakerUser!=null&&mainSpeakerUser.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
				&&mainSpeakerUser.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
			request.setAttribute("innaiRequestUser", mainSpeakerUser);
		}
		if(requestUser!=null&&requestUser.getUserType().getValue().toString().equals(String.valueOf(GlobalConstent.USER_TYPE_UNION))
				&&requestUser.getState().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))
				&&requestUser.getAllowedOrRefusedFlag().toString().equals(String.valueOf(GlobalConstent.USER_STATE_ON))){
			request.setAttribute("commonRequestUser", requestUser);
		}
		try {
			List<Meeting> videoes = meeAdmDbdService.getVideoOrderByStartTimeDesc();
			if(videoes != null && !videoes.isEmpty()){
				int videoesCount = videoes.size();
				if(videoesCount == 1 || videoesCount % 2 == 1){
					Meeting m = new Meeting();
					m.setStartTime(null);
					m.setEndTime(null);
					videoes.add(m);
				}
			}			
			request.setAttribute("videoes", videoes);
		} catch (Throwable e) {
			e.printStackTrace();
		}		
		request.setAttribute("mApplication", ma);
		return "/meetingadmin/videomeetingpass";
	}

	// 定向到拒绝页面
	@RequestMapping(value = "/redirctrefuse")
	public String redirctRefuseMeeting(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		String flag = request.getParameter("meetingappserchflag");
		request.setAttribute("meetingappserchflag", flag);
		request.setAttribute("refusemeetingid_add", id);
		return "/meetingadmin/meetingapprefuse";
	}

	// 定向到视频讲座拒绝页面
	@RequestMapping(value = "/redirctvideomeetingrefuse")
	public String redirctRefuseVideoMeeting(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		String flag = request.getParameter("meetingappserchflag");
		request.setAttribute("meetingappserchflag", flag);
		request.setAttribute("refusemeetingid_add", id);
		return "/meetingadmin/videomeetingapprefuse";
	}

	// 审批拒绝
	@ResponseBody
	@RequestMapping(value = "/refuse")
	public Map<String, Object> refuseMeeting(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				GlobalConstent.USER_LOGIN_SESSION);
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		String refuseReason = request.getParameter("refuseReson");
		this.meeAdmDbdService.refuseMeeting(id, user, refuseReason);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		return map;
	}

	// 视频讲座审批拒绝
	@ResponseBody
	@RequestMapping(value = "/refusevideo")
	public Map<String, Object> refuseVideoMeeting(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		String refuseReason = request.getParameter("refuseReson");
		this.meeAdmDbdService.refuseVideoMeeting(id, user, refuseReason);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		return map;
	}

	// 查看会诊申请
	@RequestMapping(value = "/viewappmeeting/{meetingId}")
	public String viewAppMeeting(@PathVariable Integer meetingId, Model model) {
		MeetingApplication application = this.applicationService.getMeetingApplicationById(meetingId);
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
		Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(application.getId());
		model.addAttribute("meeting", meeting);

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
		return "/meetingadmin/meetingappview";
	}

	//查看视频讲座
	@RequestMapping("/viewVideoApp/{appId}")
	public String viewVideoApp(@PathVariable Integer appId, Model model) throws Exception{
		VideoMeetingApp vmma = this.applicationService.getVideoMeetingApplicationById(appId);
		model.addAttribute("vmapp", vmma);
		if(vmma.getState().equals(GlobalConstent.MEETING_APPLICATION_STATE_PASS)){
			Meeting meeting = meeAdmDbdService.getMeetingByVideoAppId(appId);
			model.addAttribute("meeting", meeting);
		}
		return "/meetingadmin/viewVideoApplication";
	}

	// 查看会诊
	@RequestMapping(value = "/viewmeeting/{meetingId}")
	public String viewMeeting(HttpServletRequest request, @PathVariable Integer meetingId) {
		Meeting m = this.meeAdmDbdService.viewMeeting(meetingId);
		request.setAttribute("viewmeetingdetail", m);
		if (m.getMeetingKind() == 1) {
			MeetingApplication meetingapplication = applicationService.getMeetingApplicationById(m.getApplicationId().getId());
			request.setAttribute("meetingapplication", meetingapplication);
		} else if (m.getMeetingKind() == 2) {
			VideoMeetingApp meetingapplication = applicationService.getVideoMeetingApplicationById(m.getVideoapplicationId().getId());
			request.setAttribute("meetingapplication", meetingapplication);
		}
		return "/meetingadmin/meetingview";
	}

	// 删除远程会诊申请
	@RequestMapping("delTeleApp/{appId}")
	public String delTeleApp(HttpServletRequest request,HttpServletResponse response, 
				@PathVariable Integer appId)throws Throwable {
		String downloadPath = request.getServletContext().getRealPath("/");
		try {
			logger.debug("Start execute delTeleApp, id:" + appId);
			meeAdmDbdService.deleteTeleApp(downloadPath, appId);
			response.getWriter().print("succ");
		} catch (Throwable e) {
			logger.debug("Execute delTeleApp Error ", e);
			response.getWriter().print("error");
		}
		logger.debug("Over execute delTeleApp, id:" + appId);
		return null;
	}

	// 删除会议
	@RequestMapping("delMeeting/{meetingId}")
	public String delMeeting(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer meetingId) throws Throwable {
		String downloadPath = request.getServletContext().getRealPath("/");
		try {
			logger.debug("Start execute delMeeting, id:" + meetingId);
			meeAdmDbdService.deleteMeetingById(downloadPath, meetingId);
			response.getWriter().print("succ");
		} catch (Throwable e) {
			logger.debug("Execute delMeeting Error ", e);
			response.getWriter().print("error");
		}
		logger.debug("Over execute delMeeting, id:" + meetingId);
		return null;
	}

	// 删除视频讲座申请
	@RequestMapping("delVideoApp/{appId}")
	public String delVideoApp(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable Integer appId) throws Throwable {
		String downloadPath = request.getServletContext().getRealPath("/");
		try {
			logger.debug("Start execute delVideoApp, id:" + appId);
			meeAdmDbdService.deleteVideoApp(downloadPath, appId);
			response.getWriter().print("succ");
		} catch (Throwable e) {
			logger.debug("Execute delVideoApp Error ", e);
			response.getWriter().print("error");
		}
		logger.debug("Over execute delVideoApp, id:" + appId);
		return null;
	}

	/**
	 * 根据部门Id获得专家
	 * @param response
	 * @param depart
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetZhuanJian")
	public String ajaxGetZhuanJian(HttpServletResponse response, Integer depart) {
		response.setCharacterEncoding("UTF-8");
		Set<UserInfo> set = new HashSet<UserInfo>();
		
		//delete by rengeng
//		Department department = this.meeAdmDbdService.getUserInfoByDepartId(String.valueOf(depart));
//		set = getUnifiedUsers(department);
		//add by rengeng,根据部门Id取得允许参加会议的用户信息
		logger.debug("Enter MeeAdmDbdController.ajaxGetZhuanJian"+depart);
		try {
			set = this.meeAdmDbdService.getUserInfoOfExpertsByDepartId(String.valueOf(depart));
			List<CopyToView> list = new ArrayList<CopyToView>();
			String s = "";
			CopyToView cv;
			for (UserInfo ui : set) {
				if (ui.getState().intValue() == 1) {
					cv = new CopyToView();
					cv.setId(ui.getUserId());
					cv.setName(ui.getName());
					list.add(cv);
				}
			}

			ObjectMapper mapper = new ObjectMapper();
			SerializationConfig sc = mapper.getSerializationConfig();
			sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
			mapper.setSerializationConfig(sc);
			s = mapper.writeValueAsString(list);
			response.getWriter().write(s);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error MeeAdmDbdController.ajaxGetZhuanJian"+depart,e);
		}

		return null;
	}
	
	/**
	 * 根据部门Id获得共同体
	 * @param response
	 * @param depart
	 * @return
	 */
	@RequestMapping(value = "/ajaxGetCommon")
	public String ajaxGetCommon(HttpServletResponse response, Integer depart) {
		response.setCharacterEncoding("UTF-8");
		Set<UserInfo> set = new HashSet<UserInfo>();
		logger.debug("Enter MeeAdmDbdController.ajaxGetCommon"+depart);
		try {
			Department department = this.meeAdmDbdService.getUserInfoByDepartId(String.valueOf(depart));
			set = getUnifiedUsers(department);
			List<CopyToView> list = new ArrayList<CopyToView>();
			String s = "";
			CopyToView cv;
			for (UserInfo ui : set) {
				if (ui.getState().intValue() == 1) {
					cv = new CopyToView();
					cv.setId(ui.getUserId());
					cv.setName(ui.getName());
					list.add(cv);
				}
			}
		
			ObjectMapper mapper = new ObjectMapper();
			SerializationConfig sc = mapper.getSerializationConfig();
			sc.withDateFormat(new SimpleDateFormat("yyyy-MM-dd hh:mm:ss"));
			mapper.setSerializationConfig(sc);
			s = mapper.writeValueAsString(list);
			response.getWriter().write(s);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Error MeeAdmDbdController.ajaxGetCommon"+depart,e);
		}

		return null;
	}
	
	// 获取共同体
	@ResponseBody
	@RequestMapping(value = "/ajaxGetUserByGroup")
	public String ajaxGetUserByGroup(HttpServletRequest request,
			HttpServletResponse response) throws JsonGenerationException, JsonMappingException, IOException {
		String usegroupid_s = request.getParameter("usegroupid_s");
		response.setCharacterEncoding("UTF-8");
		Set<UserInfo> set = this.meeAdmDbdService.getMemberByUserGroup(usegroupid_s);
		List<CopyToView> list = new ArrayList<CopyToView>(set.size());
		CopyToView cv;
		for (UserInfo ui : set) {
			cv = new CopyToView();
			cv.setId(ui.getUserId());
			cv.setName(ui.getName());
			list.add(cv);
		}
		String s = new ObjectMapper().writeValueAsString(list);
		response.getWriter().write(s);
		return null;
	}

	@RequestMapping("getAllDetailForTele/{meetingId}")
	public String getAllDetailForTele(@PathVariable Integer meetingId, Model model){
		Meeting meeting = meeAdmDbdService.viewMeeting(meetingId);
		model.addAttribute("mt", meeting);

		MeetingApplication application = meeting.getApplicationId();

		//处理附件
		Set<Accessories> tempAllAccs = application.getAccessories();
		Set<Accessories> allAccs = new HashSet<Accessories>(tempAllAccs.size());
		Set<Accessories> accsImage = new HashSet<Accessories>(tempAllAccs.size());
		for (Accessories accessories : tempAllAccs) {
			if(accessories.getType().getValue().equals((short)2)){
				accsImage.add(accessories);
			}else{
				allAccs.add(accessories);
			}
		}
		allAccs = new TreeSet<Accessories>(allAccs);
		application.setAccessories(allAccs);
		model.addAttribute("application", application);

		accsImage = new TreeSet<Accessories>(accsImage);
		model.addAttribute("accsImage", accsImage);
		//处理总结
		if(meeting.getMeetingSummarys() != null && meeting.getMeetingSummarys().size() > 0){
			List<MeetingSummary> summaryList = new ArrayList<MeetingSummary>(meeting.getMeetingSummarys());
			this.sortMeetingSummary(summaryList);
			model.addAttribute("summarys", summaryList);
		}
		return "/meetingadmin/meeting_all_detail";
	}

	class CopyToView {
		private String id;
		private String name;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Resource(type = MeeAdmDbdService.class)
	public void setMeeAdmDbdService(MeeAdmDbdService meeAdmDbdService) {
		this.meeAdmDbdService = meeAdmDbdService;
	}

	@Resource(type = MeetingApplicationService.class)
	public void setApplicationService(
			MeetingApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Resource(type = UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Resource(type = ICUMonitorService.class)
	public void setICUMonitorService(ICUMonitorService iCUMonitorService) {
		this.iCUMonitorService = iCUMonitorService;
	}

	/**
	 * @param applicationOperateService the applicationOperateService to set
	 */
	@Resource(type = ApplicationOperateService.class)
	public void setApplicationOperateService(ApplicationOperateService applicationOperateService) {
		this.applicationOperateService = applicationOperateService;
	}
	
	@Resource(type = NoticeDetailService.class)
	public void setNoticeDetailService(NoticeDetailService noticeDetailService) {
		this.noticeDetailService = noticeDetailService;
	}
    
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
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

	/**
	 * 对MeetingSummary的评价人先按照类型排序，再按照汉语拼音排序
	 * @param list
	 * @return
	 */
	private List<MeetingSummary> sortMeetingSummary(List<MeetingSummary> list){
		Collections.sort(list, new Comparator<MeetingSummary>() {
			@Override
			public int compare(MeetingSummary o1, MeetingSummary o2) {
				return o1.getUser().getUserType().equals(o2.getUser().getUserType()) ? 
						collator.compare(o1.getUser().getName(), o2.getUser().getName()) : o1.getUser().getUserType().getValue() - o2.getUser().getUserType().getValue();
			}
		});
		return list;
	}

	private Set<UserInfo> getUnifiedUsers(Department department) {
		Set<UserInfo> set = new HashSet<UserInfo>();
		set = this.meeAdmDbdService.getUserInfoByDepart(String.valueOf(department.getId()));
		if (department.getSubDepts().size() != 0) {
			for (Department depart : department.getSubDepts()) {
				set.addAll(getUnifiedUsers(depart));
			}
		}
		return set;
	}
	
	// add by baininghan
		//根据邀请人增加删除启动或关闭网真设备
		public String getSelectedUserId(String[] oldstr,String[] newstr,String selectedUserIds){
			String useridstr = "";
			String[] olditem = oldstr;
	    	String[] newitem = newstr;
	    	//去掉原来邀请人和修改后邀请人相同的;
	    	//剩余的原来邀请人为删除掉的人
	    	//剩余的修改后的人为新增的人
	    	for(int i=0; i<oldstr.length;i++){
	    		String oldit = oldstr[i];
	    		for(int j=0;j<newstr.length;j++){
	    			String newit = newstr[j];
	    			if(oldit.equals(newit)){
	    				olditem[i]="";
	    				newitem[j]="";
	    				continue;
	    			}
	    		}
	    	}
	    	
	    	//根据新的参会用户得到所有用户信息
	    	List<UserInfo> users1 = new ArrayList<UserInfo>();
	    	String[] s=selectedUserIds.split("\\|");
	    	for(int a=0;a<s.length;a++){
	    		String userid = s[a];
	    		if(userid != null && !"".equals(userid)){
	    			UserInfo userinfo = userService.getUserByUserId(userid);
	    			users1.add(userinfo);
	    		}
	    	}
	    	 //得到所有的要删除参会人的用户信息
	    	List<UserInfo> users2 = new ArrayList<UserInfo>();
	    	for(int a=0;a<olditem.length;a++){
	    		String userid = olditem[a];
	    		if(userid != null && !"".equals(userid)){
	    			UserInfo userinfo = userService.getUserByUserId(userid);
	    			users2.add(userinfo);
	    		}
	    	}
	    	//得到所有的要删除参会人的用户信息
	    	List<UserInfo> users3 = new ArrayList<UserInfo>();
	    	for(int a=0;a<newitem.length;a++){
	    		String userid = newitem[a];
	    		if(userid != null && !"".equals(userid)){
	    			UserInfo userinfo = userService.getUserByUserId(userid);
	    			users3.add(userinfo);
	    		}
	    	}
	    	
	    	//根据要删除的参会人细心和参会人信息，找出相同videopoint的参会人信息并且过滤掉相同videopoint参会人
	    	Map<String,UserInfo> mmp = new HashMap<String,UserInfo>();
	    	for(int i=0;i<users1.size();i++){
	    		UserInfo u1 = users1.get(i);
	    		boolean flag = false;
	    		for(int j=0;j<users2.size();j++){
	    			UserInfo u2 = users2.get(j);
	    			 if(u2.getVideoPoint().equals(u1.getVideoPoint())){
	    				flag = true;
	    				continue;
	    			 }
	    		}
	    		if(!flag){
	    			 mmp.put(u1.getUserId(), u1);
	    		}
	    	}
	    	if(users3!= null && users3.size()>0){
	    		for(int m=0;m<users3.size();m++){
	        		UserInfo u3 = users3.get(m);
	        		mmp.put(u3.getUserId(), u3);
	        	}
	    	}
	    	
	    	Set<String>  set =  mmp.keySet();  
	        Iterator  iterator = set.iterator();   
	        while(iterator.hasNext()) {
	        	String   key = (String) iterator.next();
	        	useridstr +=key +"|";
	        }
			return useridstr;
		}
	 
	/**
	 * 
	 * @param oldstr:原来的邀请人
	 * @param newstr：修改后的邀请人
	 */
	public void  ManageMcu(Meeting meeting,String[] oldstr,String[] newstr){
    	ConferenceService conferenceService = new ConferenceServiceImpl();
    	logger.info("--------------------------- 根据修改邀请人启动或关闭设备开始 -----------------------");
    	String conferenceGUID = meeting.getConferenceGUID();
    	//保存原来邀请人和修改后的邀请人
    	String[] olditem = oldstr;
    	String[] newitem = newstr;
    	//去掉原来邀请人和修改后邀请人相同的;
    	//剩余的原来邀请人为删除掉的人
    	//剩余的修改后的人为新增的人
    	for(int i=0; i<oldstr.length;i++){
    		String oldit = oldstr[i];
    		for(int j=0;j<newstr.length;j++){
    			String newit = newstr[j];
    			if(oldit.equals(newit)){
    				olditem[i]="";
    				newitem[j]="";
    				continue;
    			}
    		}
    	}
    	/** 判断删除和添加的参会人绑定的网真号码是否一样，如果一样，关闭和启动设备方法不执行 */
    	for (int i = 0; i < newitem.length; i++) {
    		if(newitem[i].equals(""))continue;
    		UserInfo userinfo = userService.getUserByUserId(newitem[i]);
    		String newitemVP = userinfo.getVideoPoint();
    		for (int j = 0; j < olditem.length; j++) {
    			if(olditem[j].equals(""))continue;
    			UserInfo userinfo2 = userService.getUserByUserId(olditem[j]);
        		String olditemVP = userinfo2.getVideoPoint();
				if(olditemVP.equals(newitemVP)){
					newitem[i] = "";
					olditem[j] = "";
				}
			}
		}
    	
    	//得到删除参会人的用户信息
    	List<UserInfo> users = new ArrayList<UserInfo>();
    	for(int a=0;a<olditem.length;a++){
    		String userid = olditem[a];
    		if(userid != null && !"".equals(userid)){
    			UserInfo userinfo = userService.getUserByUserId(userid);
    			  users.add(userinfo); 
    		}
    	}
    	
     	//通过map取掉videoPoint重复的用户
    	Map<String,UserInfo> map= new HashMap<String,UserInfo>();
    	if(users != null &&users.size()>0){
    		for(int i=0;i<users.size();i++){
    			UserInfo o = users.get(i);
        		map.put(o.getVideoPoint(), o);
        	}
    	}
    	
    	//查找删除的邀请人信息
    	String  participantList = "";//它是由用逗号分隔开的参会人IP地址, 10.2.171.232, 10.47.2.246,
    	Set<String>  set =  map.keySet();  
        Iterator  iterator = set.iterator();   
        while(iterator.hasNext()) {
        	String   key = (String) iterator.next();
        	UserInfo  userinfo  = map.get(key);
        	String pro = MCUParams.getInstance().getcallProtocol() + ":" + userinfo.getVideoPoint();
        	if(!participantList.contains(pro)){/** 去重 */
        		participantList += pro+",";
        	}
        }
    	//关闭设备
    	try{
    		if(!"".equals(participantList)){
        		conferenceService.uninviteConference(conferenceGUID, null, participantList);
    			logger.info("删除：" + participantList);
        	}
    	}catch(Exception e){
    		logger.info("删除：" + participantList);
    		logger.error("Error MeeAdmDbdController.ManageMcu",e);
    	}
    	
    	
    	//-----------------------添加用户，开启设备--------------------------//
    	Set<MeetingMember> members = meeting.getMembers();
    	 Iterator<MeetingMember>  it = members.iterator(); 
    	 List<UserInfo> ulist = new ArrayList<UserInfo>();
    	 while(it.hasNext()){
    		 MeetingMember o = it.next();
    		 ulist.add(o.getMember());
    	 }
    	 
    	 List<UserInfo> users2 = new ArrayList<UserInfo>();
    	 for(int n=0;n<newitem.length;n++){
     		String userid = newitem[n];
     		if(userid != null && !"".equals(userid)){
     			UserInfo userinfo = userService.getUserByUserId(userid);
     			users2.add(userinfo);
     		}
     	}
    	 
    	 //把所有要添加的参会人和已经存在的参会人进行比较，得到要添加参会人和存在的参会人存在多人对应一个网真好
    	 Map<String,UserInfo> ump = new HashMap<String,UserInfo>();
    	 for(int u1=0;u1<users2.size();u1++){
    		 UserInfo u = users2.get(u1);
    		 for(int u2=0;u2<ulist.size();u2++){
    			 UserInfo o = ulist.get(u2);
    			 if(u.getVideoPoint().equals(o.getVideoPoint())){
    				 ump.put(u.getUserId(), u);
    				 continue;
    			 }
    		 }
    	 }
     	
    	//查找新增邀请人信息
    	List<Participant> participants = new ArrayList<Participant>();
    	for(int n=0;n<newitem.length;n++){
    		String userid = newitem[n];
    		if(userid != null && !"".equals(userid)){
    			Participant obj = Packaging.initParticipant();
    			UserInfo uo = ump.get(userid);
    			if(uo == null){
    				UserInfo userinfo = userService.getUserByUserId(userid);
    				String address = MCUParams.getInstance().getcallProtocol() + ":" + userinfo.getVideoPoint();
    				/** 去重 */
    				boolean flag = false;// 没有重复的
    				for (Participant p : participants) {
						String add = p.getAddress();
						if (add != null && add.equals(address)) {
							flag = true;
							break;
						}
					}
    				if(!flag){
    					obj.setAddress(address);//会议终端
    					participants.add(obj);
    				}
    			}
    		 }
    			
    	 }
    	 try{
    		if(participants != null && participants.size() > 0){
    			for(Participant p : participants){
    				logger.info("====邀请 ： " + p.getAddress());
    			}
    			conferenceService.inviteParticipants(conferenceGUID, participants);
    		}
    	}catch(Exception e){
    		logger.error("Error MeeAdmDbdController.ManageMcu",e);
    	} 
    	logger.info("--------------------------- 根据修改邀请人启动或关闭设备结束 -----------------------");
    }

	/**
	 * add by wangzhenglin 查看重症监护申请-----专家和共同体操作
	 * 
	 * @param meetingId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewappICUMonit/{icuMonitId}")
	public String viewappICUMonit(@PathVariable Integer icuMonitId, Model model) {
		ICUMonitor iCUMonitor = this.iCUMonitorService
				.getICUMonitById(icuMonitId);
		Set<Accessories> temp = iCUMonitor.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if (null != temp && temp.size() > 0) {

			for (Accessories acc : temp) {
				if (acc.getType().getId() != 2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}
			}
			iCUMonitor.setAccessories(accs);
		}
		model.addAttribute("application", iCUMonitor);
		model.addAttribute("accsImage", accsImage);
		// Meeting meeting =
		Meeting meeting =  this.meeAdmDbdService.getMeetingByIcuMonitor(iCUMonitor.getId());
		model.addAttribute("meeting",meeting);

		// 取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if (iCUMonitor.getIcuICD10() != null
				&& !iCUMonitor.getIcuICD10().isEmpty()) {
			for (ICUICD10 applicationICD10 : iCUMonitor.getIcuICD10()) {
				cv = new SelectItem();
				cv.setId(applicationICD10.getIcd().getId().toString());
				cv.setName(applicationICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		// 取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if (iCUMonitor.getMainDept() != null) {
			cv = new SelectItem();
			cv.setId(iCUMonitor.getMainDept().getId().toString());
			cv.setName(iCUMonitor.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if (iCUMonitor.getDepts() != null && !iCUMonitor.getDepts().isEmpty()) {
			for (ICUDepartment appDept : iCUMonitor.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
		return "/meetingadmin/meetingicuappview";
	}

	// 删除重症监护申请
	@RequestMapping("delIcuMonitor/{icuMonitorId}")
	public String delIcuMonitor(HttpServletRequest request,
			HttpServletResponse response, @PathVariable Integer icuMonitorId)
			throws Throwable {
		String downloadPath = request.getServletContext().getRealPath("/");
		try {
			logger.debug("Start execute delIcuMonitor, id:" + icuMonitorId);
			meeAdmDbdService.deleteIcuMonitor(downloadPath, icuMonitorId);
			response.getWriter().print("succ");
		} catch (Throwable e) {
			logger.debug("Execute delIcuMonitor Error ", e);
			response.getWriter().print("error");
		}
		logger.debug("Over execute delIcuMonitor, id:" + icuMonitorId);
		return null;
	}
	
	// 打开审核通过并且未结束的远程探视修改画面
	@RequestMapping(value = "/icuMonitorEditForPass/{icuId}")
	public String icuMonitorEditForPass(HttpServletRequest request,
			@PathVariable Integer icuId, Model model) {
		ICUMonitor iCUMonitor = iCUMonitorService.getICUMonitById(icuId);

		Set<Accessories> temp = iCUMonitor.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if (null != temp && temp.size() > 0) {
			for (Accessories acc : temp) {
				if (acc.getType().getId() != 2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}
			}
			iCUMonitor.setAccessories(accs);
		}
		model.addAttribute("accsImage", accsImage);

		model.addAttribute("app", iCUMonitor);
		// 取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;
		if (iCUMonitor.getIcuICD10() != null
				&& !iCUMonitor.getIcuICD10().isEmpty()) {
			for (ICUICD10 icuICD10 : iCUMonitor.getIcuICD10()) {
				cv = new SelectItem();
				cv.setId(icuICD10.getIcd().getId().toString());
				cv.setName(icuICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		// 取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if (iCUMonitor.getMainDept() != null) {
			cv = new SelectItem();
			cv.setId(iCUMonitor.getMainDept().getId().toString());
			cv.setName(iCUMonitor.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if (iCUMonitor.getDepts() != null && !iCUMonitor.getDepts().isEmpty()) {
			for (ICUDepartment appDept : iCUMonitor.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);

		Date currDate = new Date();
		List<SelectItem> expertsList = new ArrayList<SelectItem>();
		List<SelectItem> communityList = new ArrayList<SelectItem>();
		List<SelectItem> intersList = new ArrayList<SelectItem>();
		long starttime = 0;
		// 审核通过并且未结束
		if (iCUMonitor != null
				&& iCUMonitor
						.getState()
						.toString()
						.equals(String
								.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_PASS))
				&& iCUMonitor.getEndTime().getTime() >= currDate.getTime()) {
			Meeting meeting = meeAdmDbdService
					.getMeetingByIcuMonitor(iCUMonitor.getId());
			if (meeting != null) {
				Set<MeetingMember> members = meeting.getMembers();
				MeetingMember meetingmember = null;
				SelectItem selectItem = null;
				for (Iterator<MeetingMember> it = members.iterator(); it
						.hasNext();) {
					meetingmember = it.next();
					UserInfo userInfo = meetingmember.getMember();
					selectItem = new SelectItem();
					selectItem.setId(userInfo.getUserId());
					selectItem.setName(userInfo.getName() + "("
							+ userInfo.getDeptId().getName() + ")");
					String parentDeptCode = userInfo.getDeptId()
							.getParentDeptCode();
					if (!StrUtil.isEmptyStr(parentDeptCode)
							&& parentDeptCode.length() > 6) {
						parentDeptCode = parentDeptCode.substring(0, 6);
					}
					if ((parentDeptCode.equals(GlobalConstent.HIS_DEPT_CODE) || userInfo
							.getDeptId().getDeptcode()
							.equals(GlobalConstent.HIS_DEPT_CODE))
							&& userInfo
									.getState()
									.toString()
									.equals(String
											.valueOf(GlobalConstent.USER_STATE_ON))
							&& userInfo
									.getAllowedOrRefusedFlag()
									.toString()
									.equals(String
											.valueOf(GlobalConstent.USER_STATE_ON))) {
						expertsList.add(selectItem);
					} else if ((parentDeptCode
							.equals(GlobalConstent.COMMUNITY_DEPT_CODE) || userInfo
							.getDeptId().getDeptcode()
							.equals(GlobalConstent.COMMUNITY_DEPT_CODE))
							&& userInfo
									.getState()
									.toString()
									.equals(String
											.valueOf(GlobalConstent.USER_STATE_ON))
							&& userInfo
									.getAllowedOrRefusedFlag()
									.toString()
									.equals(String
											.valueOf(GlobalConstent.USER_STATE_ON))) {
						communityList.add(selectItem);
					} else if ((parentDeptCode
							.equals(GlobalConstent.OUT_DEPT_CODE) || userInfo
							.getDeptId().getDeptcode()
							.equals(GlobalConstent.OUT_DEPT_CODE))
							&& userInfo
									.getState()
									.toString()
									.equals(String
											.valueOf(GlobalConstent.USER_STATE_ON))
							&& userInfo
									.getAllowedOrRefusedFlag()
									.toString()
									.equals(String
											.valueOf(GlobalConstent.USER_STATE_ON))) {
						intersList.add(selectItem);
					}
				}
				starttime = meeting.getStartTime().getTime();
				request.setAttribute("exceptMeetingId", meeting.getId());
				request.setAttribute("meeting", meeting);
				
				//查询重症监护设备
				MeetingMobileDevices mm= iCUMonitorService.getMobileDevicesByMeetingId(meeting.getId());
				model.addAttribute("mm", mm);

			
			}
		}
		// 保存选中的所有的邀请人
		String selectstr = "";
		if (expertsList != null && expertsList.size() > 0) {
			for (int i = 0; i < expertsList.size(); i++) {
				SelectItem obj = expertsList.get(i);
				selectstr += obj.getId() + "|";
			}

		}
		if (communityList != null && communityList.size() > 0) {
			for (int i = 0; i < communityList.size(); i++) {
				SelectItem obj = communityList.get(i);
				selectstr += obj.getId() + "|";
			}
		}
		// 读取会议呼叫mcu提前时间量
		// 会议在开始前nctime时间内不能修改
		long nctime = MCUParams.getInstance().getTime();
		long dt = new Date().getTime();
		long tp = (starttime - dt) / (60 * 1000);
		boolean flag = false;
		if (tp <= nctime) {
			flag = false;
		} else {
			flag = true;
		}
		request.setAttribute("selectstr", selectstr);
		request.setAttribute("currDate", new Date());
		request.setAttribute("expertsList", expertsList);
		request.setAttribute("communityList", communityList);
		request.setAttribute("intersList", intersList);
		request.setAttribute("expectedTime",
				dateFormat.format(iCUMonitor.getExpectedTime()));
		request.setAttribute("checkUpdateStartTime", flag);
		return "/meetingadmin/icuMonitorEditForPass";
	}

	// 修改审核过但未结束的远程探视
	@RequestMapping(value = "/editIcuMonitorForPass")
	public String editIcuMonitorForPass(HttpServletRequest request,
			ICUMonitor iCUMonitor) {
		try {
			if (iCUMonitor.getId() != null) {

				String[] files = (String[]) request
						.getParameterValues("myfiles");
				String[] hainaDatas = (String[]) request
						.getParameterValues("hainaValue");
				String[] icd10Dic = request.getParameterValues("icd10Selected");
				String[] deptmentid = request
						.getParameterValues("departmentSelected");
				String selectstr = request.getParameter("selectedstr");
				String userId = request.getParameter("userId");
				UserInfo userinfo = userService.getUserByUserId(userId);
				String startTime = request.getParameter("startTime");// 开始时间
				String endTime = request.getParameter("endTime");// 结束时间
				String selectedUserIds = request
						.getParameter("selectedUserIds");// 获取所选择的参会人
				String meetingRoomId = request.getParameter("meetingRoom");// 会议室Id
				String expectedTime = request.getParameter("expectedTime");// 会议室Id
				String devicesNo = request.getParameter("devicesNo");
				//MeetingMobileDevices mm = (MeetingMobileDevices)request.getParameter("mm");
				// 根据修改时间和选择邀请人修改MCU
				// 如果增加邀请人，启动网真设备，删除邀请人关闭设备
				// 修改结束时间，根据时间关闭或启动设备
				String[] olditem = selectstr.split("\\|");
				String[] newitem = selectedUserIds.split("\\|");
				String tselectedUserIds = this.getSelectedUserId(olditem,newitem,selectedUserIds);
				String newselectedUserIds = "";
				if(tselectedUserIds !=null && !"".equals(tselectedUserIds)){
					newselectedUserIds = tselectedUserIds;
				}else{
					newselectedUserIds = selectedUserIds;
				}
				// 得到修改前所有邀请的人
				iCUMonitorService.updateICUMonitor(userinfo, files, hainaDatas, expectedTime, deptmentid, icd10Dic, iCUMonitor, startTime, endTime, newselectedUserIds, meetingRoomId,devicesNo);

				Meeting meeting = meeAdmDbdService
						.getMeetingByIcuMonitor(iCUMonitor.getId());
				this.ManageMcu(meeting, olditem, newitem);
			}
			request.getSession().setAttribute("returnmessage", "远程探视修改成功！");
		} catch (Exception e) {
			request.getSession().setAttribute("returnmessage", "远程探视修改失败！");
			e.printStackTrace();
		}
		return "redirect:/meeadIndex/icuMonitQuery/";
	}

	// 打开未审核的重症监护修改画面
	@RequestMapping(value = "/icuMonitorEdit/{icuId}")
	public String icuMonitorEdit(HttpServletRequest request,
			@PathVariable Integer icuId, Model model) {
		ICUMonitor iCUMonitor = iCUMonitorService.getICUMonitById(icuId);
		Set<Accessories> temp = iCUMonitor.getAccessories();
		Set<Accessories> accs = new TreeSet<Accessories>();
		Set<Accessories> accsImage = new TreeSet<Accessories>();
		if (null != temp && temp.size() > 0) {
			for (Accessories acc : temp) {
				if (acc.getType().getId() != 2)
					accs.add(acc);
				else {
					accsImage.add(acc);
				}
			}
			iCUMonitor.setAccessories(accs);
		}
		model.addAttribute("accsImage", accsImage);

		model.addAttribute("app", iCUMonitor);
		// 取得已选的ICD10
		List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
		SelectItem cv;

		if (iCUMonitor.getIcuICD10() != null
				&& !iCUMonitor.getIcuICD10().isEmpty()) {
			for (ICUICD10 icuICD10 : iCUMonitor.getIcuICD10()) {
				cv = new SelectItem();
				cv.setId(icuICD10.getIcd().getId().toString());
				cv.setName(icuICD10.getIcd().getDiagnosis());
				selectedICD10s.add(cv);
			}
		}
		selectedICD10s = this.ComparatorByName(selectedICD10s);
		model.addAttribute("selectedICD10s", selectedICD10s);
		// 取得已选的科室
		List<SelectItem> selectedDeparts = new ArrayList<SelectItem>();
		if (iCUMonitor.getMainDept() != null) {
			cv = new SelectItem();
			cv.setId(iCUMonitor.getMainDept().getId().toString());
			cv.setName(iCUMonitor.getMainDept().getName());
			selectedDeparts.add(cv);
		}
		if (iCUMonitor.getDepts() != null && !iCUMonitor.getDepts().isEmpty()) {
			for (ICUDepartment appDept : iCUMonitor.getDepts()) {
				cv = new SelectItem();
				cv.setId(appDept.getDepartment().getId().toString());
				cv.setName(appDept.getDepartment().getName());
				selectedDeparts.add(cv);
			}
		}
		selectedDeparts = this.ComparatorByName(selectedDeparts);
		model.addAttribute("selectedDeparts", selectedDeparts);
		logger.debug("Execute Over editPage");
		return "/meetingadmin/icuMonitorEdit";
	}

	// 修改未审核的重症监护
	@RequestMapping("/editIcuMonitor")
	public String editIcuMonitor(HttpServletRequest request,
			HttpServletResponse responset, ICUMonitor iCUMonitor)
			throws Exception {
		try {
			if (iCUMonitor.getId() != null) {
				String expectedTime = request.getParameter("expectedtime");

				String[] files = (String[]) request
						.getParameterValues("myfiles");
				String[] hainaDatas = (String[]) request
						.getParameterValues("hainaValue");
				String[] icd10Dic = request.getParameterValues("icd10Selected");
				String[] deptmentid = request
						.getParameterValues("departmentSelected");
				String userId = request.getParameter("userId");
				UserInfo userinfo = userService.getUserByUserId(userId);
				iCUMonitorService.updateICUMonitor(userinfo, expectedTime, files, hainaDatas, deptmentid, icd10Dic, iCUMonitor);
			}
			request.getSession().setAttribute("returnmessage", "修改重症监护成功！");
		} catch (Exception e) {
			logger.error("修改重症监护失败", e);
			request.getSession().setAttribute("returnmessage", "修改重症监护失败！");
		}
		logger.debug("Execute over editIcuMonitor");
		return "redirect:/meeadIndex/icuMonitQuery/";
	}

	/**
	 * 查看远程探视
	 * @param icuVisitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewappICUVisit/{icuVisitId}")
	public String viewappICUVisit(@PathVariable Integer icuVisitId, Model model) {
		//ICUMonitor iCUMonitor = this.iCUMonitorService.getICUMonitById(icuVisitId);
		ICUVisitation visitation = this.iCUMonitorService.getICUVisitationById(icuVisitId); 
		model.addAttribute("application", visitation);
		logger.debug("Execute Over viewappICUVisit");
		return "/meetingadmin/icuVisitView";
	}
	/**
	 * 进入到修改远程探视页面
	 * @param icuVisitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewICUVisitEdit/{icuVisitId}")
	public String viewICUVisitEdit(@PathVariable Integer icuVisitId, Model model) {
		ICUVisitation visitation = this.iCUMonitorService.getICUVisitationById(icuVisitId); 
		model.addAttribute("app", visitation);
		logger.debug("Execute Over viewICUVisitEdit");
		return "/meetingadmin/icuVisitEdit";
	}
	
	// 修改未审核的远程监护
	@RequestMapping("/editIcuVisit")
	public String editIcuVisit(HttpServletRequest request,HttpServletResponse responset, ICUVisitation visitation)
			throws Exception {
		try{
			if(visitation.getId() != null){
				String expectedTime = request.getParameter("expectedtime");
				String userId = request.getParameter("userId");
				UserInfo userinfo = userService.getUserByUserId(userId);
				this.iCUMonitorService.updateICUVisit(userinfo, expectedTime, visitation);
				request.getSession().setAttribute("returnmessage", "修改远程探视成功！");
			}
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "修改远程探视失败！");
			logger.error("修改远程探视失败", e);
			e.printStackTrace();
		}
		logger.debug("Execute over editIcuVisit");
		return "redirect:/unifiedindex/applyForVisitQuery";
	}
	
	/**
	 *管理员进入到远程探视修改
	 * @param icuVisitId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/viewICUVisitEditForMan/{icuVisitId}")
	public String viewICUVisitEditForMan(@PathVariable Integer icuVisitId, Model model) {
		ICUVisitation visitation = this.iCUMonitorService.getICUVisitationById(icuVisitId); 
		model.addAttribute("app", visitation);
		logger.debug("Execute Over viewICUVisitEdit");
		return "/icuMonitorAdmin/icuVisitEdit";
	}
	
	// 管理员修改未审核的远程监护
	@RequestMapping("/editIcuVisitForMan")
	public String editIcuVisitForMan(HttpServletRequest request,HttpServletResponse responset, ICUVisitation visitation)
			throws Exception {
		try{
			if(visitation.getId() != null){
				String expectedTime = request.getParameter("expectedtime");
				String userId = request.getParameter("userId");
				UserInfo userinfo = userService.getUserByUserId(userId);
				this.iCUMonitorService.updateICUVisit(userinfo, expectedTime, visitation);
				request.getSession().setAttribute("returnmessage", "修改远程探视成功！");
			}
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "修改远程探视失败！");
			logger.error("修改远程探视失败", e);
			e.printStackTrace();
		}
		logger.debug("Execute over editIcuVisit");
		return "redirect:/meeadIndex/icuVisitQuery";
	}
	/**
	 *  删除远程探视申请
	 * @param request
	 * @param response
	 * @param icuVisitId
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("delIcuVisit/{icuVisitId}")
	public String delIcuVisit(HttpServletRequest request,HttpServletResponse response, @PathVariable Integer icuVisitId)throws Throwable {
		try {
			logger.debug("Start execute delIcuMonitor, id:" + icuVisitId);
			
			meeAdmDbdService.deleteIcuVisit(icuVisitId);
			response.getWriter().print("succ");
		} catch (Throwable e) {
			logger.debug("Execute delIcuMonitor Error ", e);
			response.getWriter().print("error");
		}
		logger.debug("Over execute delIcuMonitor, id:" + icuVisitId);
		return null;
	}
	/**
	 * 修改远程探视
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/editICUVisitForPass")
	public String editICUVisitForPass(HttpServletRequest request,HttpServletResponse responset,ICUVisitation visit){
		try{
			String userId = request.getParameter("userId");
			UserInfo userinfo = userService.getUserByUserId(userId);
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			String expectedTime = request.getParameter("expectedTime");
			String mobileDevices = request.getParameter("mobileDevices");
			String visitId = request.getParameter("id");
			
			//修改病历讨论
			iCUMonitorService.EditICUVisitForPass(userinfo, expectedTime, visit, startTime, endTime, meetingRoomId,mobileDevices);
			
			 
			
		}catch(Throwable e){
			logger.error("Error ICUMonitorController.auditIcuMonitor",e);
		} 
		 
		return "redirect:/meeadIndex/icuVisitQuery/";
	}
	
	 /**
	  * 会议管理申请远程探视
	  * @param request
	  * @param model
	  * @return
	  */
	@RequestMapping(value="/addmanagerOfIcuVisitView")
	public String addmanagerOfIcuVisitView(HttpServletRequest request, Model model){	
		return "/meetingadmin/addmanagerOfIcuVisit";
	}
	
	 /**
	  * 管理员添加远程探视
	  * @param request
	  * @param videoMeetingApp
	  * @return
	  */
	@RequestMapping(value="/addManagerOfICUVisit")
	public String addManagerOfICUVisit(HttpServletRequest request, ICUVisitation visit){
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			String mobileDevices = request.getParameter("mobileDevices");
			this.iCUMonitorService.managerSaveICUVisit(user, startTime, endTime, meetingRoomId, mobileDevices, visit);
			request.getSession().setAttribute("returnmessage", "远程探视申请成功！");
		}catch(Exception e){
			request.getSession().setAttribute("returnmessage", "远程探视申请失败！");
			e.printStackTrace();			
		}
		return "redirect:/meeadmdbd/addmanagerOfIcuVisitView";
	}
	/**
	 * 得到会议录播的信息
	 * @param request
	 * @param response
	 * @param id
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value = "/getMeetByVideo/{id}")
	public String getMeetByVideo(HttpServletRequest request,HttpServletResponse response, @PathVariable Integer id) throws Throwable{
		Meeting meeting = meeAdmDbdService.viewMeeting(id);
		try {
			if(meeting.getTcsURL() != null && !"".equals(meeting.getTcsURL())){
				response.getWriter().print(meeting.getTcsURL());
			}else{
				response.getWriter().print("error");
			}
			
		} catch (Throwable e) {
			logger.debug("Execute delIcuMonitor Error ", e);
			response.getWriter().print("error");
		}
		return null;
		
	}
}