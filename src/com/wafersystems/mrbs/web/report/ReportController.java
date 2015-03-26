package com.wafersystems.mrbs.web.report;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.report.vo.MemberCount;
import com.wafersystems.mrbs.web.report.vo.SummaryVo;
import com.wafersystems.mrbs.web.report.vo.VideoCount;
import com.wafersystems.util.DateUtil;

@Controller
@RequestMapping("/export")
@Deprecated
public class ReportController {

	private static Logger logger = LoggerFactory.getLogger(ReportController.class);
	private MeeAdmDbdService meetingService;
	private MeetingMemberService memberService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 显示某一次会议的报表
	 * @param meetingId 某次会议的id
	 * @param format 导出文件的格式(pdf/xls)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("summary")
	public ModelAndView summary(Integer meetingId, String format) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();

		format = StringUtils.isBlank(format) ? "pdf" : format;
		model.put("format", format);
		
		Meeting meeting = meetingService.viewMeeting(meetingId);

		model.put("vo", meeting2Summary(meeting));
		return new ModelAndView("summary", model);
	}

	/**
	 * 远程视频参会人数统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param format 导出文件的格式(pdf/xls)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("memberCount")
	public ModelAndView memberCount(Date startDate, Date endDate, String format, HttpServletRequest request) throws Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Map<String, Object> model = new HashMap<String, Object>();

		format = StringUtils.isBlank(format) ? "pdf" : format;
		model.put("format", format);

		List<Meeting> meetings = meetingService.getMeetingByDuration(startDate, endDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<MemberCount> list = meeting2MemberCount(meetings, user.getName(), dateFormat.format(startDate), dateFormat.format(endDate));
		model.put("vo", list);
		return new ModelAndView("memberCount", model);
	}

	/**
	 * 参加视频次数统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param format 导出文件的格式(pdf/xls)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("videoCount")
	public ModelAndView videoCount(Date startDate, Date endDate, String format, HttpServletRequest request) throws Exception {
		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Map<String, Object> model = new HashMap<String, Object>();

		format = StringUtils.isBlank(format) ? "pdf" : format;
		model.put("format", format);

		List<MeetingMember> members = memberService.getMeetingMemberByDuration(startDate, endDate);

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		List<VideoCount> list = member2VideoCount(members, user.getName(), dateFormat.format(startDate), dateFormat.format(endDate));

		model.put("vo", list);
		return new ModelAndView("videoCount", model);
	}

	
	/**
	 * 视频讲座参会情况统计报表预览
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
/*	@RequestMapping("detail/preview")
	public String detailPreview(Date startDate, Date endDate,Model model) throws Exception{
		List<Meeting> meetings = meetingService.getMeetingByDuration(startDate, endDate);
		//去除未参会的成员
		Iterator<Meeting> listIt = meetings.iterator();
		while(listIt.hasNext()){
			Meeting meeting = listIt.next();
			Iterator<MeetingMember> it = meeting.getMembers().iterator();
			boolean hasUnion = false;
			while(it.hasNext()){
				MeetingMember member =	it.next();
				if(member.getAttendState() != GlobalConstent.APPLICATION_STATE_ACCEPT)
					it.remove();
				else
					if(member.getMember().getUserType().getValue() == GlobalConstent.USER_TYPE_UNION)
						hasUnion = true;
						
			}
			if(meeting.getMembers().size() == 0  || !hasUnion)//参会人为空或只有专家参会的不进行数据统计
				listIt.remove();
		}

		model.addAttribute("meetings", meetings);
		model.addAttribute("startDate", startDate);
		model.addAttribute("endDate", endDate);
		model.addAttribute("today", new Date());
		model.addAttribute("APPLICATION_STATE_ACCEPT", GlobalConstent.APPLICATION_STATE_ACCEPT);
		model.addAttribute("USER_TYPE_PROFESSIONAL", GlobalConstent.USER_TYPE_PROFESSIONAL);
		return "meetingadmin/detail_preview";
	}
*/
	@Resource
	public void setMeetingService(MeeAdmDbdService meetingService) {
		this.meetingService = meetingService;
	}

	@Resource
	public void setMemberService(MeetingMemberService memberService) {
		this.memberService = memberService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	/**
	 * 参会人员转换成视频次数统计报表
	 * @param members
	 * @param fillFormPerson
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<VideoCount> member2VideoCount(List<MeetingMember> members, String fillFormPerson, String startDate, String endDate){
		List<VideoCount> result = new ArrayList<VideoCount>();
		Map<String, Integer> temp = new HashMap<String, Integer>();
		for (MeetingMember member : members) {
			if(member.getAttendState() == GlobalConstent.APPLICATION_STATE_ACCEPT){
				if(temp.containsKey(member.getMember().getName())){
					temp.put(member.getMember().getName(), temp.get(member.getMember().getName()) + 1);
				}else{
					temp.put(member.getMember().getName(), 1);
				}
			}
		}

		VideoCount videoCount = null;
		int id = 0;
		for (String  name : temp.keySet()) {

			videoCount = new VideoCount();

			videoCount.setFillFormDate("填表时间：" + DateUtil.getCurrentDate());
			videoCount.setFillFormPerson("填表人："  + fillFormPerson);
			videoCount.setStatisticalTime("统计时间：" + startDate + " 到 " + endDate);

			videoCount.setItemId(String.valueOf( ++id));
			videoCount.setItemName(name);
			videoCount.setItemCount(String.valueOf(temp.get(name)));
			result.add(videoCount);
		}
		Collections.sort(result);
		return result;
	}

	/**
	 * 远程视频参会人数统计报表
	 * @param meetings
	 * @param fillFormPerson
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	private List<MemberCount> meeting2MemberCount(List<Meeting> meetings, String fillFormPerson, String startDate, String endDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		MemberCount memberCount = null;
		List<MemberCount> result = new ArrayList<MemberCount>();
		for (Meeting meeting : meetings) {
			memberCount = new MemberCount();

			memberCount.setFillFormDate("填表时间：" + DateUtil.getCurrentDate());
			memberCount.setFillFormPerson("填表人："  + fillFormPerson);
			memberCount.setStatisticalTime("统计时间：" + startDate + " 到 " + endDate);

			memberCount.setTimeItemDesc(DateUtil.getCurrentDate());
			memberCount.setItemId(String.valueOf(meeting.getId()));
			memberCount.setItemName(meeting.getTitle());
			memberCount.setTest(dateFormat.format(meeting.getStartTime()));

			int count = 0;
			int coumNum = 0;//社区数
			for (MeetingMember meetingMember : meeting.getMembers()) {
				UserInfo user = meetingMember.getMember();
				if(meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)){
					if(user.getUserType().getValue() == GlobalConstent.USER_TYPE_PROFESSIONAL){
						memberCount.setItemDep(user.getDeptId().getName());
						memberCount.setItemExp(user.getName());
					} else {
						coumNum ++;
						count += meetingMember.getAttendNo();
					}
				}
			}

			if(count == 0)//参会人数为0时，忽略统计
				continue;

			memberCount.setComNum(String.valueOf(coumNum));
			memberCount.setCount(String.valueOf(count));
			result.add(memberCount);
		}
		return result;
	}

	/**
	 * 会议信息转换成会议总结的数据
	 * @param meeting
	 * @return
	 */
	private List<SummaryVo> meeting2Summary(Meeting meeting){
		List<SummaryVo> result = new ArrayList<SummaryVo>();
		UserInfo expert = null;
		int acceptCount = 0;
		List<MeetingMember> memberList = new ArrayList<MeetingMember>();
		for (MeetingMember meetingMember : meeting.getMembers()) {
			if (meetingMember.getAttendState() == GlobalConstent.APPLICATION_STATE_ACCEPT) {
				if (meetingMember.getMember().getUserType().getId() == GlobalConstent.USER_TYPE_PROFESSIONAL) {
					expert = meetingMember.getMember();
				} else {
					acceptCount += meetingMember.getAttendNo();
					memberList.add(meetingMember);
				}
			}
		}

		if(expert == null){
			logger.error("科室专家未接受本会议，报表忽略本次会议");
			return Collections.emptyList();
		}

		SummaryVo vo = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat();
		int middleNum = 1;
		if(memberList.size() == 1)
			middleNum = 1;
		else if(memberList.size() % 2 == 0)
			middleNum = memberList.size() / 2;
		else if(memberList.size() % 2 != 0)
			middleNum = (memberList.size() - 1) / 2;

		for (int i = 0; i < memberList.size(); i++) {
			vo = new SummaryVo();
			MeetingMember member = memberList.get(i);

			dateFormat.applyPattern("yyyy-MM-dd");
			vo.setMeetingDate(dateFormat.format(meeting.getStartTime()));
			
			dateFormat.applyPattern("HH:mm");
			vo.setMeetingTime(dateFormat.format(meeting.getStartTime()) + "-" + dateFormat.format(meeting.getEndTime()));
			vo.setMeetingLocation(meeting.getMeetingRoomId().getName());
			vo.setMeetingCreator(meeting.getCreator().getName());
			vo.setCreatorMobile(meeting.getCreator().getMobile());
			vo.setMeetingRecorder(meeting.getHolder().getName());
			vo.setMeetingHolder(expert.getDeptId().getName() + expert.getName());
			vo.setMeetingTitle(meeting.getTitle());
			vo.setHolderMobile(expert.getMobile());

			if(i == (middleNum - 1))
				vo.setCommunity("功能社区");

			vo.setCommunityName(member.getMember().getName());
			vo.setCommunityMember(member.getMember().getName());
			vo.setCommunityMail(member.getMember().getMail());
			vo.setCommunityMobile(member.getMember().getMobile());
			vo.setCommunityNum(member.getAttendNo() + "");

			vo.setAllNum(acceptCount + "");
			result.add(vo);
		}
		return result;
	}
}
