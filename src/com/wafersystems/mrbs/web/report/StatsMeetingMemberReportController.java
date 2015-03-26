package com.wafersystems.mrbs.web.report;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.meeting.MeetingMemberService;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.report.vo.StatisticalDetailVO;
import com.wafersystems.mrbs.web.report.vo.StatsMeetingMember;
import com.wafersystems.mrbs.web.report.vo.VideoLecturesStatisticalVO;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.Excel;

@Controller
@RequestMapping("/statsMeetingMemberReport")
public class StatsMeetingMemberReportController {
	private MeetingMemberService meetingMemberService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	/**
	 * 进入参加视频人数统计报表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("member_index")
	public String memberIndex()throws Exception{
		return "report/meeting_member_index";
	}
	
	/**
	 * 进入参加视频次数统计报表
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("frequency_index")
	public String frequencyIndex()throws Exception{
		return "report/meeting_member_frequency_index";
	}
	
	///teleStati/index
	/**
	 * 参加视频次数统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/statsMeetingMemberByFrequencyExcel")
	public String statsMeetingMemberByFrequencyExcel(Date startDate, Date endDate, HttpServletResponse response,HttpServletRequest request) throws Exception{

		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Excel excel = new Excel("InVideoFrequencyStatistics.xls");
		excel.setCurrentSheet("参加视频次数统计报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +
				"填报人："+user.getName()+"\n" +
				"报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));

		List<StatsMeetingMember> statsMeetingMemberlist = meetingMemberService.statsMeetingMemberByFrequency(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
		if(statsMeetingMemberlist!=null&&!statsMeetingMemberlist.isEmpty()){
			int rowNumber = 4;
			int intCount = statsMeetingMemberlist.size();
			StatsMeetingMember statsMeetingMember = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				statsMeetingMember=statsMeetingMemberlist.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getNumber());
				//名称
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getName());
				//次数
				excel.insert(rowNumber, coloumNumber++, statsMeetingMember.getTimesCount());
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("参加视频次数统计报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}
	
	/**
	 * 参加视频人数统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/statsMeetingMemberByPeopleCountExcel")
	public String statsMeetingMemberByPeopleCountExcel(Date startDate, Date endDate, HttpServletResponse response,HttpServletRequest request) throws Exception{

		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Excel excel = new Excel("ToVideoNumber.xls");
		excel.setCurrentSheet("参加视频人数统计报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +
				"填报人："+user.getName()+"\n" +
				"报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));

		List<StatsMeetingMember> statsMeetingMemberlist = meetingMemberService.statsMeetingMemberByPeopleCount(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);
		if(statsMeetingMemberlist!=null&&!statsMeetingMemberlist.isEmpty()){
			int rowNumber = 4;
			int intCount = statsMeetingMemberlist.size();
			StatsMeetingMember statsMeetingMember = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				statsMeetingMember=statsMeetingMemberlist.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getNumber());
				//名称
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getName());
				//次数
				excel.insert(rowNumber, coloumNumber++, statsMeetingMember.getMemberCount());
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("参加视频人数统计报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}
	
	/*
	 * 参加视频人数统计报表预览
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videoLecturesStatisticalPreview")
	public String videoLecturesStatisticalPreview(Date startDate, Date endDate, Model model,HttpServletResponse response,HttpServletRequest request) throws Exception{
		model.addAttribute("today", new Date());
		model.addAttribute("startDate", dateFormat.format(startDate));
		model.addAttribute("endDate", dateFormat.format(endDate));
		List<StatsMeetingMember> statsMeetingMemberlist = meetingMemberService.statsMeetingMemberByPeopleCount(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);		
		
		if(statsMeetingMemberlist==null)//参加视频次数统计报表预览页面
			statsMeetingMemberlist=new ArrayList<StatsMeetingMember>();
			
		model.addAttribute("statsMeetingMemberlist", statsMeetingMemberlist);
		return "report/meeting_member_index";
		
		
	}
	
	/*
	 * 参加视频次数统计报表预览
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videoLecturesStatisticalPreviewByFrequency")
	public String videoLecturesStatisticalPreviewByFrequency(Date startDate, Date endDate, Model model, Integer type) throws Exception{
		model.addAttribute("today", new Date());
		model.addAttribute("startDate", dateFormat.format(startDate));
		model.addAttribute("endDate", dateFormat.format(endDate));
		List<StatsMeetingMember>  statsMeetingMemberlist = meetingMemberService.statsMeetingMemberByFrequency(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES);		
		
		if(statsMeetingMemberlist==null)//参加视频次数统计报表预览页面
			statsMeetingMemberlist=new ArrayList<StatsMeetingMember>();
		
		model.addAttribute("statsMeetingMemberlist", statsMeetingMemberlist);
		return "report/meeting_member_frequency_index";

	}
	
	/*
	 * 根据参会人类型统计参加视频次数/人数报表预览
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videoLecturesStatisticalPreviewByMemberType")
	public String videoLecturesStatisticalPreviewByMemberType(Date startDate, Date endDate, Model model, Short type) throws Exception{
		model.addAttribute("today", new Date());
		model.addAttribute("startDate", dateFormat.format(startDate));
		model.addAttribute("endDate", dateFormat.format(endDate));
		List<StatsMeetingMember>  statsMeetingMemberlist = meetingMemberService.statsMeetingMemberByMemberType(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES,type);		
		
		if(statsMeetingMemberlist==null)//参加视频次数统计报表预览页面
			statsMeetingMemberlist=new ArrayList<StatsMeetingMember>();
		
		model.addAttribute("statsMeetingMemberlist", statsMeetingMemberlist);
		model.addAttribute("type", type);
		return "report/meeting_member_frequency_index";

	}
	
	/**
	 * 参加视频人数/次数统计报表导出
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/statsMeetingMemberByMemberTypeExcel")
	public String statsMeetingMemberByMemberTypeExcel(Date startDate, Date endDate, HttpServletResponse response,HttpServletRequest request) throws Exception{

		UserInfo user = (UserInfo) request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Excel excel = new Excel("toVideoByMemberType.xls");
		excel.setCurrentSheet("参加视频讲座（专家）统计报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +
				"填报人："+user.getName()+"\n" +
				"报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));         
		List<StatsMeetingMember> statsMeetingMemberlist = meetingMemberService.statsMeetingMemberByMemberType(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES,GlobalConstent.USER_TYPE_PROFESSIONAL);
		if(statsMeetingMemberlist!=null&&!statsMeetingMemberlist.isEmpty()){
			int rowNumber = 4;
			int intCount = statsMeetingMemberlist.size();
			StatsMeetingMember statsMeetingMember = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				statsMeetingMember=statsMeetingMemberlist.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getNumber());
				//名称
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getName());
				//人数
				excel.insert(rowNumber, coloumNumber++, statsMeetingMember.getTimesCount());
				//次数
				excel.insert(rowNumber, coloumNumber++, statsMeetingMember.getMemberCount());
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}
		
		
		excel.setCurrentSheet("参加视频讲座（共同体）统计报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +
				"填报人："+user.getName()+"\n" +
				"报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));         
		List<StatsMeetingMember> statsMeetingCommunitylist = meetingMemberService.statsMeetingMemberByMemberType(dateFormat.format(startDate), dateFormat.format(endDate), GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES,GlobalConstent.USER_TYPE_UNION);
		if(statsMeetingCommunitylist!=null&&!statsMeetingCommunitylist.isEmpty()){
			int rowNumber = 4;
			int intCount = statsMeetingCommunitylist.size();
			StatsMeetingMember statsMeetingMember = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				statsMeetingMember=statsMeetingCommunitylist.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getNumber());
				//名称
				excel.insert(rowNumber, coloumNumber++,	statsMeetingMember.getName());
				//人数
				excel.insert(rowNumber, coloumNumber++, statsMeetingMember.getTimesCount());
				//次数
				excel.insert(rowNumber, coloumNumber++, statsMeetingMember.getMemberCount());				
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("参加视频讲座统计报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}
	
	
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@Resource
	public void setMeetingMemberService(MeetingMemberService meetingMemberService) {
		this.meetingMemberService = meetingMemberService;
	}
}
