package com.wafersystems.mrbs.web.summary;

import java.io.OutputStream;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.service.meeting.MeetingSummaryService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.meeting.MeetingSummary;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.Excel;

@Controller
@RequestMapping("/summary")
public class SummaryController {

	private static Logger logger = LoggerFactory.getLogger(SummaryController.class);
	private MeetingSummaryService summaryService;
    private MeeAdmDbdService meetingService;
    private MeetingMemberService memberService;
    static Collator collator = Collator.getInstance(Locale.CHINA);

    /**
     * 打开会诊管理员总结
     * @param meetingId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("indexForManager/{meetingId}")
    public String indexForManager(@PathVariable Integer meetingId, Model model)throws Exception{
    	logger.debug("start execute indexForManager");
    	try{
	    	if(null != meetingId){
	    		model.addAttribute("meetingId", meetingId);
	    		List<MeetingMember> members = memberService.getMembersByMeetingId(meetingId);
	    		Meeting meeting = meetingService.viewMeeting(meetingId);
	    		for (Iterator<MeetingMember> iterator = members.iterator(); iterator.hasNext();) {
					UserInfo meetingMember = iterator.next().getMember();
					if(meetingMember.getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION) 
							&& !meetingMember.getUserId().equals(meeting.getCreator().getUserId())){
						iterator.remove();
						continue;
					}
					for (MeetingSummary summary : meeting.getMeetingSummarys()) {
						//排除已经评价的人
						if(summary.getUser().getUserId().equals(meetingMember.getUserId())){
							iterator.remove();
						}
					}
				}
	    		this.sortMeetingMember(members);
	    		model.addAttribute("members", members);
	    		model.addAttribute("meeting", meeting);

	    		if(meeting.getMeetingSummarys() != null && meeting.getMeetingSummarys().size() > 0){
	    			List<MeetingSummary> summaryList = new ArrayList<MeetingSummary>(meeting.getMeetingSummarys());
	    			this.sortMeetingSummary(summaryList);
					model.addAttribute("summarys", summaryList);
	    		}
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	logger.debug("end execute indexForManager");
    	return "/meetingadmin/meetingSummaryByManager";
    }

    /**
     * 会诊管理员保存总结数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("saveDataForManager")
    public String saveDataForManager(HttpServletRequest request, HttpServletResponse response)throws Exception{
    	logger.debug("start execute saveData");
    	try{
	    	UserInfo sessionUser = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
	    	if(request.getParameter("meetingId") != null){
	    		Integer meetingId = Integer.parseInt(request.getParameter("meetingId"));
	    		Map<String, String[]> para_map = new HashMap<String, String[]>(request.getParameterMap());
	    		para_map.remove("meetingId");//去除会议id的参数
	    		Set<String> keySet = para_map.keySet();
	    		MeetingSummary summary = null;
	    		ArrayList<MeetingSummary> summarys = new ArrayList<MeetingSummary>(para_map.size());
	    		String summaryContent;
	    		for (String userId : keySet) {
	    			summaryContent = para_map.get(userId)[0];
	    			if(StringUtils.isBlank(summaryContent))
	    				continue;
	    			summary = new MeetingSummary();
	    			summary.setUser(new UserInfo(userId.toLowerCase()));
	    			summary.setMeeting(new Meeting(meetingId));
	    			summary.setSummary(para_map.get(userId)[0]);
	    			summary.setSubmitUser(sessionUser);//session用户
	    			summarys.add(summary);
				}
//	    		summarys.trimToSize();
//	    		if(summarys.size() > 0)
	    		summaryService.saveSummarysForManager(meetingId ,summarys);
	    	}
	    	logger.debug("end execute saveData");
    	}catch(Exception e){
    		logger.error("saveDataForManager", e);
    		response.getWriter().write("failed");
    	}
    	response.getWriter().write("succ");
    	return null;
    }

    /**
     * 打开普通用户或者专家总结页面
     * @param meetingId
     * @param model
     * @return
     * @throws Exception
     */
    @RequestMapping("indexForUser/{meetingId}")
    public String indexForUser(@PathVariable Integer meetingId, Model model) throws Exception{
    	logger.debug("start execute indexForUser");
    	try{
	    	if(null != meetingId){
	    		model.addAttribute("meetingId", meetingId);
	    		Meeting meeting = meetingService.viewMeeting(meetingId);
	    		model.addAttribute("meeting", meeting);
	    		
	    		if(meeting.getMeetingSummarys() != null && meeting.getMeetingSummarys().size() > 0){
	    			List<MeetingSummary> summaryList = new ArrayList<MeetingSummary>(meeting.getMeetingSummarys());
	    			this.sortMeetingSummary(summaryList);
					model.addAttribute("summarys", summaryList);
	    		}
	    	}
    	} catch(Exception e){
    		e.printStackTrace();
    	}
    	logger.debug("end execute indexForUser");
    	return "unified/meetingSummaryByUser";
    }

    /**
     * 非会诊管理员保存总结数据
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("saveDataForUser")
    public String saveDataForUser(HttpServletRequest request, HttpServletResponse response)throws Exception{
    	logger.debug("start execute saveData");
    	try{
	    	UserInfo sessionUser = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
	    	if(request.getParameter("meetingId") != null){
	    		Integer meetingId = Integer.parseInt(request.getParameter("meetingId"));
	    		String summaryContent = request.getParameter("summary");
	    		if(StringUtils.isNotBlank(summaryContent)){
	    			MeetingSummary summary = new MeetingSummary();
	    			summary.setUser(sessionUser);//代替的提交人
	    			summary.setMeeting(new Meeting(meetingId));
	    			summary.setSummary(summaryContent);
	    			summary.setSubmitUser(sessionUser);//session用户
	    			summaryService.saveSummaryForUser(meetingId, sessionUser.getUserId(), summary);
	    		}
	    	}
    	}catch(Exception e){
    		logger.error("saveDataForUser", e);
    		response.getWriter().write("failed");
    	}
    	response.getWriter().write("succ");
    	logger.debug("end execute saveData");
    	return null;
    }

	/*
	 * 导出会议总结excel
	 */
	@RequestMapping("/exportExcel/{meetingId}")
	public String exportExcel(HttpServletRequest request,HttpServletResponse response, @PathVariable Integer meetingId)throws Exception{
		logger.debug("导出excel");
		try{
		Meeting meeting = meetingService.viewMeeting(meetingId);
		Set<MeetingSummary> summarys = meeting.getMeetingSummarys();

		Excel excel = new Excel("TheMeetingSummary.xls");		
		excel.setCurrentSheet("诊治建议");

		int rowNumber = 2;
		List<MeetingSummary> summaryList = new ArrayList<MeetingSummary>(summarys);
		this.sortMeetingSummary(summaryList);
		for (Iterator<MeetingSummary> iterator = summaryList.iterator(); iterator.hasNext();) {
			int coloumNumber = 1;
			MeetingSummary meetingSummary = (MeetingSummary) iterator.next();
			excel.insert(rowNumber, coloumNumber++, meetingSummary.getUser().getName());
			excel.insert(rowNumber, coloumNumber++, meetingSummary.getSummary());
			if(!iterator.hasNext()){
				break;
			}
			excel.createRow(++rowNumber, --rowNumber);
			rowNumber++;
		}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("诊治建议".getBytes("GBK"), "ISO8859-1")+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		}catch(Exception e){
			logger.error("导出excel操作异常", e);
		}
		return null;
	}

    @Resource
	public void setMettingService(MeeAdmDbdService mettingService){
		this.meetingService=mettingService;
	}

	@Resource
	public void setSummaryService(MeetingSummaryService summaryService) {
		this.summaryService = summaryService;
	}

	@Resource
	public void setMemberService(MeetingMemberService memberService) {
		this.memberService = memberService;
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
}
