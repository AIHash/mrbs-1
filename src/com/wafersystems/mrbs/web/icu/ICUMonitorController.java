package com.wafersystems.mrbs.web.icu;

import java.io.IOException;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wafersystems.mcu.MCUParams;
import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.icu.ICUMonitorService;
import com.wafersystems.mrbs.service.icu.IcuNoticeDetailService;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.EvaluationService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.service.meeting.SatisfactionManagerService;
import com.wafersystems.mrbs.service.meeting.SatisfactionService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.service.user.UserTypeService;
import com.wafersystems.mrbs.vo.meeting.Accessories;
import com.wafersystems.mrbs.vo.meeting.Evaluation;
import com.wafersystems.mrbs.vo.meeting.ICUDepartment;
import com.wafersystems.mrbs.vo.meeting.ICUICD10;
import com.wafersystems.mrbs.vo.meeting.ICUMonitor;
import com.wafersystems.mrbs.vo.meeting.ICUVisitation;
import com.wafersystems.mrbs.vo.meeting.IcuNoticeDetail;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingApplication;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingMobileDevices;
import com.wafersystems.mrbs.vo.meeting.Satisfaction;
import com.wafersystems.mrbs.vo.meeting.SatisfactionManager;
import com.wafersystems.mrbs.vo.user.UnifiedUserType;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.criteriavo.MeetingCriteriaVO;
import com.wafersystems.mrbs.web.criteriavo.SelectItem;
import com.wafersystems.mrbs.web.user.DeptController;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.NoticeInfo;
import com.wafersystems.util.StrUtil;
 
/**
 * 
 * @author Administrator 
 *
 * @Description: 重症监护和远程探视逻辑处理
 *
 * @date 2015年1月22日 下午2:46:35 
 */
  
@Controller
@RequestMapping(value = "/icumonitor")
public class ICUMonitorController {
	private static final Logger logger = LoggerFactory.getLogger(DeptController.class);

	private MeeAdmDbdService meeAdmDbdService;
	private ICUMonitorService iCUMonitorService;
	private IcuNoticeDetailService icuNoticeDetailService;
	private UserService userService;
	private EvaluationService evaluationService;
	private SatisfactionManagerService satisfactionManagerService;
	private SatisfactionService satisfactionService;
	private UserTypeService userTypeService;
	private MeetingMemberService meetingmemberervice;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	static Collator collator = Collator.getInstance(Locale.CHINA);
	/**
	 * add by wangzhenglin
	 * 重症监护审核通过界面跳转
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/redirctIcuMonitorpass")
	public String redirctIcuMonitorpass(HttpServletRequest request) {
		String id = request.getParameter("requestIcuMonitid");
		request.setAttribute("requestmeetid_add", id);

		String meetingappserchflag = request.getParameter("meetingappserchflag");
		request.setAttribute("meetingappserchflag", meetingappserchflag);
		ICUMonitor iCUMonitor = iCUMonitorService.getICUMonitById(Integer.parseInt(id));
		UserInfo requestUser = iCUMonitor.getRequester();
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
		request.setAttribute("mApplication", iCUMonitor);
		return "/icuMonitorAdmin/icuMonitorpass";
	}
	/**审核通过重症监护提交事件
	 * */
	@ResponseBody
	@RequestMapping(value = "/auditIcuMonitor")
	public Map<String, Object> auditIcuMonitor(HttpServletRequest request,HttpServletResponse response) {
		try{
			response.setCharacterEncoding("UTF-8");
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			
			String icuId = request.getParameter("meetingApplicationId");//要审核的病历讨论Id
			String startTime = request.getParameter("startTime");//开始时间
			String endTime = request.getParameter("endTime");//结束时间
			String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
			String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
			String devicesNo = request.getParameter("devicesNo");
			if(!StrUtil.isEmptyStr(icuId)&&!StrUtil.isEmptyStr(startTime)
					&&!StrUtil.isEmptyStr(endTime)&&!StrUtil.isEmptyStr(selectedUserIds)
					&&!StrUtil.isEmptyStr(meetingRoomId)){
				this.iCUMonitorService.saveAuditIcuMonitor(user, icuId, startTime, endTime, selectedUserIds, meetingRoomId,devicesNo);
			}
			
			Map<String, Object> mapreturn = new HashMap<String, Object>();
			mapreturn.put("flag", true);
			return mapreturn;
		}catch(Throwable e){
			logger.error("Error ICUMonitorController.auditIcuMonitor",e);
		}
		Map<String, Object> mapreturn = new HashMap<String, Object>();
		mapreturn.put("flag", false);
		return mapreturn;
	}
	// 定向到拒绝页面
	@RequestMapping(value = "/redirctrefuse")
	public String redirctRefuseMeeting(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		String flag = request.getParameter("meetingappserchflag");
		request.setAttribute("meetingappserchflag", flag);
		request.setAttribute("refusemeetingid_add", id);
		return "/icuMonitorAdmin/icuMonitorRefuse";
	}
	// 审批拒绝
	@ResponseBody
	@RequestMapping(value = "/refuseicuMonitor")
	public Map<String, Object> refuseicuMonitor(HttpServletRequest request, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		UserInfo user = (UserInfo) request.getSession().getAttribute(
				GlobalConstent.USER_LOGIN_SESSION);
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		String refuseReason = request.getParameter("refuseReson");
		this.iCUMonitorService.refuseIcuMonitorPass(id, user, refuseReason);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("flag", true);
		return map;
	}
	/**
	 * 重症监护审核的通知见面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/icuMonitorAuditNotice")
	public String teleconferenceAuditNotice(HttpServletRequest request) {
		Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
		Meeting meeting = meeAdmDbdService.getMeetingByIcuMonitor(id);
		if(meeting!=null){
			Set<MeetingMember> members = meeting.getMembers();
			MeetingMember meetingmember=null;
//				String expertsName = "";
//				String internationalCourtName = "";
//				String communityName = "";
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
		String unifiedVaue = NoticeInfo.getMessage("successIcuMonitorNotice_content",param);
		request.setAttribute("unifiedVaue", unifiedVaue);
		request.setAttribute("meetingApplicationId", id);
		
		return "/icuMonitorAdmin/icuMonitorAuditNotice";
	}

	/**
	 * 重症监护通知界面提交事件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/icuMonitorSendNotice")
	public String icuMonitorSendNotice(HttpServletRequest request,HttpServletResponse response)throws Exception{
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
		Integer id = Integer.valueOf(request.getParameter("meetingApplicationId"));//icumonitor的ID
		String sendTime=dateFormat.format(new Date());
		
		try {
			iCUMonitorService.sendIcuMonitorNotice(Integer.parseInt(meetingApplicationId), expertsNotice, communityNotice, internationalCourtNotice,expertsSelectedUserIds,communitySelectedUserIds,interSelectedUserIds);
			icuNoticeDetailService.saveIcuMonitorNoticeDetail(id, sendTime, expertsSelectedUserIds, communitySelectedUserIds, interSelectedUserIds);
			response.getWriter().write("success");
			response.getWriter().flush();
		} catch (Exception e) {
			logger.error("Error icumonitor.icuMonitorSendNotice",e);
			response.getWriter().write("fail");
			response.getWriter().flush();
		}
		return null;
	}

	/**
	 * add by  wangzhenglin 
	 * 定向重症监护通知明细界面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/noticeDetail")
	public String noticeDetail(HttpServletRequest request) {
		String icuId=request.getParameter("refusemeetingid");
		List<IcuNoticeDetail> noticeDetailList=icuNoticeDetailService.getNoticeDetailByicuMonitorId(Integer.valueOf(icuId));
		int i=1;
		for(IcuNoticeDetail noticeDetail: noticeDetailList){
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
		return "/icuMonitorAdmin/icuMonitorNoticeDetail";
	}
	/*============================get set===========================================*/
	@Resource(type = MeeAdmDbdService.class)
	public void setMeeAdmDbdService(MeeAdmDbdService meeAdmDbdService) {
		this.meeAdmDbdService = meeAdmDbdService;
	}
	@Resource(type = ICUMonitorService.class)
	public void setiCUMonitorService(ICUMonitorService iCUMonitorService) {
		this.iCUMonitorService = iCUMonitorService;
	}

	@Resource(type = IcuNoticeDetailService.class)
	public void setIcuNoticeDetailService(
			IcuNoticeDetailService icuNoticeDetailService) {
		this.icuNoticeDetailService = icuNoticeDetailService;
	}
	@Resource(type = UserService.class)
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	@Resource(type = EvaluationService.class)
	public void setEvaluationService(EvaluationService evaluationService) {
		this.evaluationService = evaluationService;
	}
	@Resource(type = SatisfactionManagerService.class)
	public void setSatisfactionManagerService(
			SatisfactionManagerService satisfactionManagerService) {
		this.satisfactionManagerService = satisfactionManagerService;
	}
	@Resource(type = SatisfactionService.class)
	public void setSatisfactionService(SatisfactionService satisfactionService) {
		this.satisfactionService = satisfactionService;
	}
	@Resource(type = UserTypeService.class)
	public void setUserTypeService(UserTypeService userTypeService) {
		this.userTypeService = userTypeService;
	}
	@Resource(type = MeetingMemberService.class)
	public void setMeetingmemberervice(MeetingMemberService meetingmemberervice) {
		this.meetingmemberervice = meetingmemberervice;
	}
    
	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}
		// 定向到远程探视拒绝页面
		@RequestMapping(value = "/redirctRefuseIcuVist")
		public String redirctRefuseIcuVist(HttpServletRequest request) {
			Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
			String flag = request.getParameter("meetingappserchflag");
			request.setAttribute("meetingappserchflag", flag);
			request.setAttribute("refusemeetingid_add", id);
			return "/icuMonitorAdmin/icuVisitRefuse";
		}
		// 审批拒绝
		@ResponseBody
		@RequestMapping(value = "/refuseicuVisit")
		public Map<String, Object> refuseicuVisit(HttpServletRequest request, HttpServletResponse response) {
			response.setCharacterEncoding("UTF-8");
			UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			Integer id = Integer.valueOf(request.getParameter("refusemeetingid"));
			String refuseReason = request.getParameter("refuseReson");
			this.iCUMonitorService.refuseIcuVisitPass(id, user, refuseReason);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("flag", true);
			return map;
		}
		/**重症监护管理员评价涉及到的action   beging*/
		
		/**
		 * add by wangzhenglin
		 * 重症监护管理员评价页面
		 * @param model
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		@RequestMapping(value = "/adminIcuMonitorEvaluationQuery")
		public String teleconferenceEvaluationQuery(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
			try {
				logger.debug("Enter icumonitor.adminIcuMonitorEvaluationQuery");
//				UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
//				List<Meeting> meetinglist = meeAdmDbdService.getOverMeeting(user,GlobalConstent.APPLICATION_TYPE_TELECONSULTATION);
//				model.addAttribute("meeting_state_pending",meetinglist);
				return "icuMonitorAdmin/adminIcuMonitorEvaluationQuery";
			} catch (Exception e) {
				response.getWriter().write("fail");
				logger.error("Error icumonitor.adminIcuMonitorEvaluationQuery",e);
			} 
			return null;
			
		}
		/**
		 * 重症监护，管理员评价页面数据查询
		 * @param model
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		
		@RequestMapping(value = "/adminIcuMonitorEvaluationList")
		public String teleconferenceEvaluationList(Model model,HttpServletRequest request,HttpServletResponse response) throws IOException {
			try {
				logger.debug("Enter icumonitor.adminIcuMonitorEvaluationList");
				MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
				meetingCriteriaVO.setMeetingStartTime(request.getParameter("startTime"));
				meetingCriteriaVO.setMeetingEndTime(request.getParameter("endTime"));
				meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
				meetingCriteriaVO.setState(request.getParameter("state"));
				meetingCriteriaVO.setRequesterUserName(request.getParameter("requesterUserName"));
				meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_ICUMONITOR));
			    List<Meeting> meetinglist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
			    request.setAttribute("report_data", meetinglist);
			    String requestParameter = "startTime="+request.getParameter("startTime")+"&meetingType="+request.getParameter("meetingType")+
											"&state="+request.getParameter("state")+"&requesterUserName="+request.getParameter("requesterUserName");
			    model.addAttribute("requestParameter",requestParameter);
				return "icuMonitorAdmin/adminIcuMonitorEvaluationList";
			} catch (Throwable e) {
				response.getWriter().write("fail");
				logger.error("Error icumonitor.adminIcuMonitorEvaluationList",e);
			}
			return null;
			
		}
		/**远程监护，管理评价页面的查看功能弹出查看页面处理逻辑*/
		@RequestMapping(value="/icuMonitorViewAndInvitedState/{meetingid}")
		public String meetingViewAndInvitedState(Model model, @PathVariable Integer meetingid) {
			Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
			ICUMonitor icuMonitor = meetingview.getiCUMonitorId();
			model.addAttribute("meeting", meetingview);
			model.addAttribute("mapplication", icuMonitor);
			Short invitedState = 0;
			
			//得到所有受邀成员
			List<MeetingMember> meetingMembers = new ArrayList<MeetingMember>();
			meetingMembers.addAll(meetingview.getMembers());
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
//			MeetingApplication ma = this.applicationService.getMeetingApplicationById(meetingId);
			Set<Accessories> temp = icuMonitor.getAccessories();
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
				icuMonitor.setAccessories(accs);
			}
			model.addAttribute("application", icuMonitor);
			model.addAttribute("accsImage", accsImage);
			//取得已选的ICD10
			List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
			SelectItem cv;
			if(icuMonitor.getIcuICD10() != null && !icuMonitor.getIcuICD10().isEmpty()){
				for (ICUICD10 applicationICD10 : icuMonitor.getIcuICD10()) {
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
			if(icuMonitor.getMainDept()!=null){
				cv = new SelectItem();
				cv.setId(icuMonitor.getMainDept().getId().toString());
				cv.setName(icuMonitor.getMainDept().getName());
				selectedDeparts.add(cv);
			}
			if(icuMonitor.getDepts() != null && !icuMonitor.getDepts().isEmpty()){
				for (ICUDepartment appDept : icuMonitor.getDepts()) {
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
			return "/icuMonitorAdmin/icuMonitorviewAndInvitedState";
//			return "unified/meetingView";
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
		/**显示管理员评价页面----ICU评价界面点击评价功能跳出的页面*/
		@RequestMapping(value="/meetOpintion")
		public String meetingOpintion(HttpSession session, HttpServletRequest request){	
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
			try {
				logger.debug("Enter icumonitor.meetingOpintion");
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
				    request.setAttribute("APPLICATION_TYPE_ICUMONITOR",GlobalConstent.APPLICATION_TYPE_ICUMONITOR);
				    request.setAttribute("appMembers", appMembers);//获得所有参会成员
				}
			    return "icuMonitorAdmin/icuMonitorOpintion_manager";
			} catch (Exception e) {
				logger.error("Enter icumonitor.meetingOpintion",e);
			} 
			return null;
			
		}
		/**会议管理员评价页面tijiao*/
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
		/**重症监护管理员评价涉及到的action   end*/

	/**
	 * 远程探视审核通过页面
	 * @param request
	 * @return
	 */
		@RequestMapping(value = "/redirctICUVisitPass")
		public String redirctICUVisitPass(HttpServletRequest request) {
			String id = request.getParameter("requestmeetid");
			request.setAttribute("requestmeetid_add", id);

			ICUVisitation ma = meeAdmDbdService.getICUVisitById(Integer.parseInt(id)); 
			request.setAttribute("mApplication", ma);
			return "/icuMonitorAdmin/IcuVisitpass";
		}
		
		@RequestMapping(value = "/icuVisitpass")
		public Map<String, Object> icuVisitpass(HttpServletRequest request,HttpServletResponse response){
			try{
				response.setCharacterEncoding("UTF-8");
				UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
				
				String icuId = request.getParameter("icuVisitId");//要审核的病历讨论Id
				String startTime = request.getParameter("startTime");//开始时间
				String endTime = request.getParameter("endTime");//结束时间
				String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
				String mobileDevicesId = request.getParameter("mobileDevices");
				if(!StrUtil.isEmptyStr(icuId)&&!StrUtil.isEmptyStr(startTime)
						&&!StrUtil.isEmptyStr(endTime)&&!StrUtil.isEmptyStr(meetingRoomId)
						&&!StrUtil.isEmptyStr(mobileDevicesId)){
					this.iCUMonitorService.saveAuditIcuVisit(user, icuId, startTime, endTime, meetingRoomId,mobileDevicesId);
				}
				response.getWriter().write("succ");
				response.getWriter().flush();
			}catch(Throwable e){
				logger.error("Error ICUMonitorController.auditIcuMonitor",e);
			} 
			 
			return null;
		}
 
		
		/**add  by  wangzhenglin
		 * 
		 * 专家和共同体，受邀和评论功能对应逻辑和action   begin*/
		
		/** 受邀监护查询画面*/
		@RequestMapping(value="/invitedIcuMonitorQuery")
		public String invitedIcuMonitorQuery(HttpServletRequest request, Model model)
		{
			return "icuMonitorUnified/invitedIcuMonitorQuery";
		}
		/** 受邀监护显示画面*/
		@RequestMapping(value="/invitedIcuMonitorList")
		public String invitedIcuMonitorList(HttpSession session,HttpServletRequest request,HttpServletResponse response, Model model) {
			try {
				logger.debug("Enter icumonitor.invitedConsultationList");
				MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
				UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
				meetingCriteriaVO.setAcceptUserId(user.getUserId());
				meetingCriteriaVO.setMeetingStartTime(request.getParameter("startTime"));
				meetingCriteriaVO.setMeetingEndTime(request.getParameter("endTime"));
				meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
				meetingCriteriaVO.setMeetingType(request.getParameter("meetingType"));
				meetingCriteriaVO.setState(request.getParameter("state"));
				meetingCriteriaVO.setInvitedState(request.getParameter("invitedState"));
				meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_ICUMONITOR));
			    
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
			    return "icuMonitorUnified/invitedIcuMonitorList";
			} catch (Throwable e) {
				logger.error("Error icumonitor.invitedConsultationList",e);
			}
			return null;
		}
		/**重症监护专家和共同体受邀页面查看功能*/
		@RequestMapping(value="/meetView/{meetingid}")
		public String meetingView(Model model, @PathVariable Integer meetingid,HttpServletRequest request){
			Meeting meetingview = meeAdmDbdService.viewMeeting(meetingid);
			model.addAttribute("meeting", meetingview);
//			MeetingApplication ma = this.applicationService.getMeetingApplicationById(meetingId);
			ICUMonitor iCUMonitor = meetingview.getiCUMonitorId();
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
			model.addAttribute("application", iCUMonitor);
			model.addAttribute("accsImage", accsImage);
			//取得已选的ICD10
			List<SelectItem> selectedICD10s = new ArrayList<SelectItem>();
			SelectItem cv;
			if(iCUMonitor.getIcuICD10()!= null && !iCUMonitor.getIcuICD10().isEmpty()){
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
//			Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//			model.addAttribute("meeting", meeting);

//			model.addAttribute("HAINA_URL", GlobalParam.haina_url);
			return "/meetingadmin/meetingappview";
//			return "unified/meetingView";
		}
		/**add  by  wangzhenglin
		 * 
		 * 专家和共同体，受邀和评论功能对应逻辑和action   begin*/
		
		
		
		//打开审核通过并且未结束的视频讲座修改画面
		@RequestMapping(value = "/viewEditICUVisitForPass/{icuId}")
		public String viewEditICUVisitForPass(HttpServletRequest request,@PathVariable Integer icuId) {
			ICUVisitation visit = this.iCUMonitorService.getICUVisitationById(icuId);
			Date currDate = new Date();
		  
			if(visit!=null&&visit.getState().toString().equals(String.valueOf(GlobalConstent.MEETING_APPLICATION_STATE_PASS))
					&&visit.getEndTime().getTime()>=currDate.getTime()){
				Meeting meeting = meeAdmDbdService.getMeetingByIcuVisitId(icuId);
				if(meeting!=null){
					MeetingMobileDevices mmd = iCUMonitorService.getMobileDevicesByMeetingId(meeting.getId());
					request.setAttribute("exceptMeetingId", meeting.getId());
					request.setAttribute("meeting", meeting);
					request.setAttribute("mmd", mmd);
				}
			}
			request.setAttribute("expectedTime",dateFormat.format(visit.getExpectedTime()));
				request.setAttribute("currDate", new Date());
				request.setAttribute("app", visit);
				return "/icuMonitorAdmin/viewEditICUVisitForPass";
		}
		
 
		 /* 专家和共同体，受邀和评论功能对应逻辑和action   end*/
		
		/**add by wangzhenglin  begin   专家和共同体对应的已安排的重症监护功能*/
		/** 已安排监护查询画面*/
		@RequestMapping(value="/arrangedIcuMonitorQuery")
		public String arrangedConsultationQuery(HttpServletRequest request, Model model)
		{		
			return "icuMonitorUnified/arrangedIcuMonitorQuery";
		}
		
		/** 已安排监护显示画面*/
		@RequestMapping(value="/arrangedIcuMonitorList")
		public String arrangedIcuMonitorList(HttpServletRequest request, Model model)
		{
			try {
				logger.debug("Enter icumonitor.arrangedConsultationList");
				UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
				MeetingCriteriaVO meetingCriteriaVO = new MeetingCriteriaVO();
				meetingCriteriaVO.setAcceptUserId(user.getUserId());
				meetingCriteriaVO.setMeetingStartTime(request.getParameter("meetingStartTime"));
				meetingCriteriaVO.setMeetingEndTime(request.getParameter("meetingEndTime"));
				meetingCriteriaVO.setKeyWord(request.getParameter("keyWord"));
				meetingCriteriaVO.setMeetingType(request.getParameter("meetingType"));
				meetingCriteriaVO.setState(request.getParameter("state"));
				meetingCriteriaVO.setInvitedState(String.valueOf(GlobalConstent.APPLICATION_STATE_ACCEPT));
				meetingCriteriaVO.setMeetingKind(String.valueOf(GlobalConstent.APPLICATION_TYPE_ICUMONITOR));
				
				List<Meeting> invitelist = meeAdmDbdService.getMeetingList(new PageSortModel(request),meetingCriteriaVO).getResultlist();
				request.setAttribute("report_data", invitelist);
				
				String requestParameter = "meetingStartTime="+request.getParameter("meetingStartTime")+"&meetingEndTime="+request.getParameter("meetingEndTime")+
											"&meetingType="+request.getParameter("meetingType")+"&state="+request.getParameter("state");
				model.addAttribute("requestParameter",requestParameter);
				model.addAttribute("currUser",user);
				
				return "icuMonitorUnified/arrangedIcuMonitorList";
			} catch (Throwable e) {
				logger.error("Error icumonitor.arrangedIcuMonitorList",e);
			}
			return null;
		}
		/**已安排监护的查看功能（共同体和专家权限下）
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
//			MeetingApplication ma = this.applicationService.getMeetingApplicationById(meetingId);
			ICUMonitor application = meetingview.getiCUMonitorId();
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
//			Meeting meeting = this.meeAdmDbdService.getMeetingByApplicationId(ma.getId());
//			model.addAttribute("meeting", meeting);

//			model.addAttribute("HAINA_URL", GlobalParam.haina_url);
			return "/icuMonitorUnified/arrangedMeetingICUView";
		}
		/**显示专家和共同体评价页面*/
		@RequestMapping(value="/meetingOpintionForUnified")
		public String meetingOpintionForUnified(HttpSession session, HttpServletRequest request){	
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

			return "icuMonitorUnified/meetOpintionForUnified";
	        
		}
		/**会诊共同体评价页面*/
		@RequestMapping(value="/addgroupopintionForUnified")
		public String addgroupopintionForUnified(@RequestParam Short localNumber,
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
		
		@RequestMapping("/addIcuMonitForAdmin")
		public String addIcuMonitForAdmin(){
			return "icuMonitorAdmin/addIcuMonitForAdmin";
		}
		/**add by wangzhenglin
		 * 添加监护申请----管理员
		 * @param request
		 * @param application
		 * @return
		 * @throws Throwable 
		 */
		@RequestMapping("/addIcuMonit")
		public String addIcuMonit(HttpServletRequest request, ICUMonitor application)throws Throwable{
			try {
				UserInfo userinfo =(UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
				String[] files=(String[])request.getParameterValues("myfiles");
				String[] hainaDatas = (String[])request.getParameterValues("hainaValue");
				String[] deptmentid = request.getParameterValues("departmentSelected");
				String[] icd10Dic = request.getParameterValues("icd10Selected");	
				
				
				String startTime = request.getParameter("startTime");//开始时间
				String endTime = request.getParameter("endTime");//结束时间
				String selectedUserIds = request.getParameter("selectedUserIds");//获取所选择的参会人
				String meetingRoomId = request.getParameter("meetingRoom");//会议室Id
				String devicesNo = request.getParameter("devicesNo");
	
			    //增加重症监护
				application.setExpectedTime(dateFormat.parse(startTime));
				iCUMonitorService.saveICUMonit(userinfo, startTime, files, hainaDatas, deptmentid, icd10Dic, application);			
				
				if(!StrUtil.isEmptyStr(startTime)
						&&!StrUtil.isEmptyStr(endTime)&&!StrUtil.isEmptyStr(selectedUserIds)
						&&!StrUtil.isEmptyStr(meetingRoomId)){
					this.iCUMonitorService.saveAuditIcuMonitor(userinfo, application.getId().toString(), startTime, endTime, selectedUserIds, meetingRoomId,devicesNo);
				}
				request.getSession().setAttribute("returnmessage", "申请重症监护成功！");

			} catch (Exception e) {
				logger.error("申请重症监护失败", e);
				request.getSession().setAttribute("returnmessage", "申请重症监护失败！");
			}
			return "redirect:/icumonitor/addIcuMonitForAdmin";

			
			


		
		}
}
