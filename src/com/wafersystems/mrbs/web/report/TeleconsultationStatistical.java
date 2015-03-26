package com.wafersystems.mrbs.web.report;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
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

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.mrbs.vo.meeting.Meeting;
import com.wafersystems.mrbs.vo.meeting.MeetingMember;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.report.vo.StatisticalDetailVO;
import com.wafersystems.mrbs.web.report.vo.StatisticalDetailVO.CommAndCountVo;
import com.wafersystems.mrbs.web.report.vo.VideoLecturesStatisticalVO;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.Excel;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping("teleStati")
public class TeleconsultationStatistical {

	private static Logger logger = LoggerFactory.getLogger(TeleconsultationStatistical.class);
	private MeeAdmDbdService meetingService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 进入视频讲座页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public String index()throws Exception{
		return "report/tele_index";
	}

	/**
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param model
	 * @param type 1-明细；2-汇总
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/teleconsultationStatisticalPreview")
	public String teleconsultationStatisticalPreview(Date startDate, Date endDate, Model model, Integer type) throws Exception{

		Map<String, Object> data = this.getteleconsultationStatisticalVOList(startDate, endDate);
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = (List<VideoLecturesStatisticalVO>) data.get("summary");//汇总
		List<StatisticalDetailVO> statisticalDetailVOList = (List<StatisticalDetailVO>) data.get("detail");//明细
		if(statisticalDetailVOList==null)
			statisticalDetailVOList=new ArrayList<StatisticalDetailVO>();		
		if(videoLecturesStatisticalVOList==null)
			videoLecturesStatisticalVOList=new ArrayList<VideoLecturesStatisticalVO>();
		
		model.addAttribute("today", new Date());
		model.addAttribute("startDate", dateFormat.format(startDate));
		model.addAttribute("endDate", dateFormat.format(endDate));

		type = type == null ? 2 : type;//防止没选择类型
		model.addAttribute("type", type);
		if(type == 1){//汇总的预览页面
			model.addAttribute("summary", videoLecturesStatisticalVOList);
		} else if (type == 2){//明细的预览页面
			model.addAttribute("detail", statisticalDetailVOList);
		}
		return "report/tele_index";
	}

	/**
	 * 远程会诊参会情况统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/teleconsultationStatisticalExcel")
	public String teleconsultationStatisticalExcel(Date startDate, Date endDate, HttpServletResponse response,HttpSession session) throws Exception{

		Excel excel = new Excel("TeleconsultationStatistical.xls");
		excel.setCurrentSheet("病历讨论参会情况汇总报表");

		UserInfo user = (UserInfo) session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" + "填报人："+ user.getName() +"\n" + "报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));
		
		Map<String, Object> data = this.getteleconsultationStatisticalVOList(startDate, endDate);
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = (List<VideoLecturesStatisticalVO>) data.get("summary");
		if(videoLecturesStatisticalVOList!=null&&!videoLecturesStatisticalVOList.isEmpty()){
			int rowNumber = 4;
			int intCount = videoLecturesStatisticalVOList.size();
			VideoLecturesStatisticalVO videoLecturesStatistical = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				videoLecturesStatistical=videoLecturesStatisticalVOList.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	videoLecturesStatistical.getNumId().toString());
				//视频日期
				excel.insert(rowNumber, coloumNumber++,	videoLecturesStatistical.getVideoDate());
				//提交病历单位
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getSubmitCompany().getName());
				//视频内容
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getVideoContent());
				//临床科室
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getClinicalDepartmentName());
				//会诊专家
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getExpertsName());
				//社区
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getCommunity());
				//听课人数
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getLecturesPeopleCount());
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}

		//明细
		List<StatisticalDetailVO> StatisticalDetailVOList = (List<StatisticalDetailVO>) data.get("detail");
		excel.setCurrentSheet("病历讨论参会情况明细报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +"填报人："+ user.getName() +"\n" + "报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));
				
		if(StatisticalDetailVOList != null && !StatisticalDetailVOList.isEmpty()){
			int sheet2Row = 4;
			int intCount = StatisticalDetailVOList.size();
			StatisticalDetailVO statisticalDetailVO = null;
			for(int i=0; i < intCount; i++){
				statisticalDetailVO = StatisticalDetailVOList.get(i);
						int commsSize = statisticalDetailVO.getComms().size();
						CommAndCountVo commAndCountVo = null;
						for (int j = 0; j < commsSize; j++) {
							int coloumNumber = 1;
							commAndCountVo = statisticalDetailVO.getComms().get(j);
							//编号
							excel.insert(sheet2Row, coloumNumber++,	statisticalDetailVO.getNumId().toString());
							//视频日期
							excel.insert(sheet2Row, coloumNumber++,	statisticalDetailVO.getVideoDate());
							//提交病历单位
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getSubmitCompany().getName());
							//视频内容
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getVideoContent());
							//临床科室
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getClinicalDepartmentName());
							//会诊专家
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getExpertsName());
							//社区
							excel.insert(sheet2Row, coloumNumber++, commAndCountVo.getCommunity());
							//听课人数
							excel.insert(sheet2Row, coloumNumber++, commAndCountVo.getLecturesPeopleCount());
							//总人数
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getCount());
							if (commsSize > 1 && j == (commsSize - 1)) {
								excel.autoMergeColumn(sheet2Row - j, 1);
								excel.autoMergeColumn(sheet2Row - j, 2);
								excel.autoMergeColumn(sheet2Row - j, 3);
								excel.autoMergeColumn(sheet2Row - j, 4);
								excel.autoMergeColumn(sheet2Row - j, 5);
								excel.autoMergeColumn(sheet2Row - j, 6);
								excel.autoMergeColumn(sheet2Row - j, 9);
							}

							if(i == (intCount - 1) && j == (commsSize - 1) ){
								break;
							}
							excel.createRow(++sheet2Row, --sheet2Row);
							sheet2Row++;
						}
					}
				}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("病历讨论参会统计报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}

	//根据起止时间查询已经结束且被接受的远程会诊会议
	public Map<String, Object> getteleconsultationStatisticalVOList(Date startDate, Date endDate){
		logger.debug("Error ReportController.getVideoLecturesStatisticalVOList");
		Map<String, Object> results = new HashMap<String, Object>();
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = new ArrayList<VideoLecturesStatisticalVO>();
		List<StatisticalDetailVO> statisticalDetailVOList = new ArrayList<StatisticalDetailVO>();//明细
		try{
			List<Meeting> meetings = meetingService.getMeetingForReport(GlobalConstent.APPLICATION_TYPE_TELECONSULTATION,
					dateFormat.format(startDate), dateFormat.format(endDate));
			VideoLecturesStatisticalVO videoLecturesStatisticalVO = null;
			StatisticalDetailVO statisticalDetailVO = null;
			//社区个数
			Integer intCommunityCount = 0;
			//参会人数
			Integer intPeopleCount = 0;
			//临床科室
			String clinicalDepartmentNames="";
			//会诊专家
			String expertsNames="";
			//病历讨论报表序号
			int numId = 1;

			for (Meeting meeting : meetings) {
				videoLecturesStatisticalVO = new VideoLecturesStatisticalVO();
				statisticalDetailVO = new StatisticalDetailVO();
				//序号
				videoLecturesStatisticalVO.setNumId(numId);
				statisticalDetailVO.setNumId(numId);
				//编号
				videoLecturesStatisticalVO.setNumber(meeting.getId().toString());
				statisticalDetailVO.setNumber(meeting.getId().toString());
				//视频日期
				videoLecturesStatisticalVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
				statisticalDetailVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
//				//远程会诊VO
//				MeetingApplication meetingApplication = meeting.getApplicationId();
				//会诊目的
				videoLecturesStatisticalVO.setVideoContent(meeting.getTitle());
				statisticalDetailVO.setVideoContent(meeting.getTitle());
				
				videoLecturesStatisticalVO.setSubmitCompany(meeting.getCreator());//汇总中的病历提交单位
				statisticalDetailVO.setSubmitCompany(meeting.getCreator());//明细中的病历提交单位
				//社区个数
				intCommunityCount = 0;
				//参会人数
				intPeopleCount = 0;
				//临床科室
				clinicalDepartmentNames="";
				//会诊专家
				expertsNames="";
				
				StatisticalDetailVO.CommAndCountVo commAndCountVo = null;
				List<StatisticalDetailVO.CommAndCountVo> commAndCountVoList = new ArrayList<StatisticalDetailVO.CommAndCountVo>();
				
				//是否存在共同体标记，不存在为true，存在为false
				boolean flag1 = true;
				for (MeetingMember meetingMember : meeting.getMembers()){
					if(meetingMember.getAttendState()!=null&&meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)
							&&meetingMember.getMember()!=null&&meetingMember.getMember().getUserType()!=null
							&&meetingMember.getMember().getUserType().getValue()!=null){
							//判断是否存在共同体
							if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION)){
								flag1=false;
								break;
							} 
				    }
				}
				
				//判断一个会议是否存在多个专家，true为不存在，false为存在
				boolean flag2=true;
				//会议成员
				for (MeetingMember meetingMember : meeting.getMembers()){
					//判断是否接受会议
					if(meetingMember.getAttendState()!=null&&meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)
						&&meetingMember.getMember()!=null&&meetingMember.getMember().getUserType()!=null
						&&meetingMember.getMember().getUserType().getValue()!=null){
						//判断是否为共同体
						if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION)){
							intCommunityCount+=1;
							if(meetingMember.getAttendNo()!=null){
								intPeopleCount+=Integer.valueOf(meetingMember.getAttendNo());
	
								commAndCountVo = new StatisticalDetailVO.CommAndCountVo();
								commAndCountVo.setCommunity(meetingMember.getMember().getName());
								commAndCountVo.setLecturesPeopleCount(meetingMember.getAttendNo().toString());
								commAndCountVoList.add(commAndCountVo);								
							}
						}
						//判断是否为专家
						if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){							
							if(flag1&&flag2){
								//某一会议，flag1判断参会成员不含有共同体（flag2判断存在多个专家的情况，该段代码只执行一次）
								//否则明细报表中会出现不合理的多行合并情况
								commAndCountVo = new StatisticalDetailVO.CommAndCountVo();
								commAndCountVo.setCommunity("");
								commAndCountVo.setLecturesPeopleCount("0");
								commAndCountVoList.add(commAndCountVo);
								flag2=false;
							}
							if(!clinicalDepartmentNames.contains(meetingMember.getMember().getDeptId().getName())){
								clinicalDepartmentNames+=meetingMember.getMember().getDeptId().getName()+"、";
							}
							if(!expertsNames.contains(meetingMember.getMember().getName())){
								expertsNames+=meetingMember.getMember().getName()+"、";
							}
						}
					}
				}
				statisticalDetailVO.setComms(commAndCountVoList);
				//临床科室
				if(!StrUtil.isEmptyStr(clinicalDepartmentNames)&&clinicalDepartmentNames.length()>1){
					clinicalDepartmentNames = clinicalDepartmentNames.substring(0, clinicalDepartmentNames.length()-1);
					videoLecturesStatisticalVO.setClinicalDepartmentName(clinicalDepartmentNames);
					statisticalDetailVO.setClinicalDepartmentName(clinicalDepartmentNames);
				}
				//会诊专家
				if(!StrUtil.isEmptyStr(expertsNames)&&expertsNames.length()>1){
					expertsNames = expertsNames.substring(0, expertsNames.length()-1);
					videoLecturesStatisticalVO.setExpertsName(expertsNames);
					statisticalDetailVO.setExpertsName(expertsNames);
				}
				//社区
				videoLecturesStatisticalVO.setCommunity(intCommunityCount.toString());
				//听课人数
				videoLecturesStatisticalVO.setLecturesPeopleCount(intPeopleCount.toString());
				statisticalDetailVO.setCount(intPeopleCount.toString());
				
				videoLecturesStatisticalVOList.add(videoLecturesStatisticalVO);
				statisticalDetailVOList.add(statisticalDetailVO);
				numId++;
			}
		} catch (Exception e) {
			logger.error("Error ReportController.getteleconsultationStatisticalVOList",e);
		}
		results.put("summary", videoLecturesStatisticalVOList);
		results.put("detail", statisticalDetailVOList);
		return results;
	}

	@Resource
	public void setMeetingService(MeeAdmDbdService meetingService) {
		this.meetingService = meetingService;
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	/**  
	 * 得到本月的第一天  
	 * @return  
	 */  
	private Date getMonthFirstDay() {   
	    Calendar calendar = Calendar.getInstance();   
	    calendar.set(Calendar.DAY_OF_MONTH, calendar   
	            .getActualMinimum(Calendar.DAY_OF_MONTH));   
	  
	    return calendar.getTime();   
	}   

	/**  
	 * 得到本月的最后一天  
	 *   
	 * @return  
	 */  
	private Date getMonthLastDay() {   
	    Calendar calendar = Calendar.getInstance();   
	    calendar.set(Calendar.DAY_OF_MONTH, calendar   
	            .getActualMaximum(Calendar.DAY_OF_MONTH));   
	    return calendar.getTime();   
	}   
	
	/**
	 * 进入重症监护报表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("indexICUMonit")
	public String indexICUMonit()throws Exception{
		return "report/icuMonit_index";
	}
	
	/**
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param model
	 * @param type 1-明细；2-汇总
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ICUMonitorPreview")
	public String ICUMonitorPreview(Date startDate, Date endDate, Model model, Integer type) throws Exception{

		Map<String, Object> data = this.getICUMonitVOList(startDate, endDate);
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = (List<VideoLecturesStatisticalVO>) data.get("summary");//汇总
		List<StatisticalDetailVO> statisticalDetailVOList = (List<StatisticalDetailVO>) data.get("detail");//明细
		if(statisticalDetailVOList==null)
			statisticalDetailVOList=new ArrayList<StatisticalDetailVO>();		
		if(videoLecturesStatisticalVOList==null)
			videoLecturesStatisticalVOList=new ArrayList<VideoLecturesStatisticalVO>();
		
		model.addAttribute("today", new Date());
		model.addAttribute("startDate", dateFormat.format(startDate));
		model.addAttribute("endDate", dateFormat.format(endDate));

		type = type == null ? 2 : type;//防止没选择类型
		model.addAttribute("type", type);
		if(type == 1){//汇总的预览页面
			model.addAttribute("summary", videoLecturesStatisticalVOList);
		} else if (type == 2){//明细的预览页面
			model.addAttribute("detail", statisticalDetailVOList);
		}
		return "report/icuMonit_index";
	}
	
	//根据起止时间查询已经结束且被接受的远程会诊会议
	public Map<String, Object> getICUMonitVOList(Date startDate, Date endDate){
		logger.debug("Error ReportController.getICUMonitVOList");
		Map<String, Object> results = new HashMap<String, Object>();
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = new ArrayList<VideoLecturesStatisticalVO>();
		List<StatisticalDetailVO> statisticalDetailVOList = new ArrayList<StatisticalDetailVO>();//明细
		try{
			List<Meeting> meetings = meetingService.getMeetingForReport(GlobalConstent.APPLICATION_TYPE_ICUMONITOR,
					dateFormat.format(startDate), dateFormat.format(endDate));
			VideoLecturesStatisticalVO videoLecturesStatisticalVO = null;
			StatisticalDetailVO statisticalDetailVO = null;
			//社区个数
			Integer intCommunityCount = 0;
			//参会人数
			Integer intPeopleCount = 0;
			//临床科室
			String clinicalDepartmentNames="";
			//会诊专家
			String expertsNames="";
			//病历讨论报表序号
			int numId = 1;

			for (Meeting meeting : meetings) {
				videoLecturesStatisticalVO = new VideoLecturesStatisticalVO();
				statisticalDetailVO = new StatisticalDetailVO();
				//序号
				videoLecturesStatisticalVO.setNumId(numId);
				statisticalDetailVO.setNumId(numId);
				//编号
				videoLecturesStatisticalVO.setNumber(meeting.getId().toString());
				statisticalDetailVO.setNumber(meeting.getId().toString());
				//视频日期
				videoLecturesStatisticalVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
				statisticalDetailVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
//					//远程会诊VO
//					MeetingApplication meetingApplication = meeting.getApplicationId();
				//会诊目的
				videoLecturesStatisticalVO.setVideoContent(meeting.getTitle());
				statisticalDetailVO.setVideoContent(meeting.getTitle());
				
				videoLecturesStatisticalVO.setSubmitCompany(meeting.getCreator());//汇总中的病历提交单位
				statisticalDetailVO.setSubmitCompany(meeting.getCreator());//明细中的病历提交单位
				//社区个数
				intCommunityCount = 0;
				//参会人数
				intPeopleCount = 0;
				//临床科室
				clinicalDepartmentNames="";
				//会诊专家
				expertsNames="";
				
				StatisticalDetailVO.CommAndCountVo commAndCountVo = null;
				List<StatisticalDetailVO.CommAndCountVo> commAndCountVoList = new ArrayList<StatisticalDetailVO.CommAndCountVo>();
				
				//是否存在共同体标记，不存在为true，存在为false
				boolean flag1 = true;
				for (MeetingMember meetingMember : meeting.getMembers()){
					if(meetingMember.getAttendState()!=null&&meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)
							&&meetingMember.getMember()!=null&&meetingMember.getMember().getUserType()!=null
							&&meetingMember.getMember().getUserType().getValue()!=null){
							//判断是否存在共同体
							if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION)){
								flag1=false;
								break;
							} 
				    }
				}
				
				//判断一个会议是否存在多个专家，true为不存在，false为存在
				boolean flag2=true;
				//会议成员
				for (MeetingMember meetingMember : meeting.getMembers()){
					//判断是否接受会议
					if(meetingMember.getAttendState()!=null&&meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)
						&&meetingMember.getMember()!=null&&meetingMember.getMember().getUserType()!=null
						&&meetingMember.getMember().getUserType().getValue()!=null){
						//判断是否为共同体
						if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION)){
							intCommunityCount+=1;
							if(meetingMember.getAttendNo()!=null){
								intPeopleCount+=Integer.valueOf(meetingMember.getAttendNo());
	
								commAndCountVo = new StatisticalDetailVO.CommAndCountVo();
								commAndCountVo.setCommunity(meetingMember.getMember().getName());
								commAndCountVo.setLecturesPeopleCount(meetingMember.getAttendNo().toString());
								commAndCountVoList.add(commAndCountVo);								
							}
						}
						//判断是否为专家
						if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){							
							if(flag1&&flag2){
								//某一会议，flag1判断参会成员不含有共同体（flag2判断存在多个专家的情况，该段代码只执行一次）
								//否则明细报表中会出现不合理的多行合并情况
								commAndCountVo = new StatisticalDetailVO.CommAndCountVo();
								commAndCountVo.setCommunity("");
								commAndCountVo.setLecturesPeopleCount("0");
								commAndCountVoList.add(commAndCountVo);
								flag2=false;
							}
							if(!clinicalDepartmentNames.contains(meetingMember.getMember().getDeptId().getName())){
								clinicalDepartmentNames+=meetingMember.getMember().getDeptId().getName()+"、";
							}
							if(!expertsNames.contains(meetingMember.getMember().getName())){
								expertsNames+=meetingMember.getMember().getName()+"、";
							}
						}
					}
				}
				statisticalDetailVO.setComms(commAndCountVoList);
				//临床科室
				if(!StrUtil.isEmptyStr(clinicalDepartmentNames)&&clinicalDepartmentNames.length()>1){
					clinicalDepartmentNames = clinicalDepartmentNames.substring(0, clinicalDepartmentNames.length()-1);
					videoLecturesStatisticalVO.setClinicalDepartmentName(clinicalDepartmentNames);
					statisticalDetailVO.setClinicalDepartmentName(clinicalDepartmentNames);
				}
				//会诊专家
				if(!StrUtil.isEmptyStr(expertsNames)&&expertsNames.length()>1){
					expertsNames = expertsNames.substring(0, expertsNames.length()-1);
					videoLecturesStatisticalVO.setExpertsName(expertsNames);
					statisticalDetailVO.setExpertsName(expertsNames);
				}
				//社区
				videoLecturesStatisticalVO.setCommunity(intCommunityCount.toString());
				//听课人数
				videoLecturesStatisticalVO.setLecturesPeopleCount(intPeopleCount.toString());
				statisticalDetailVO.setCount(intPeopleCount.toString());
				
				videoLecturesStatisticalVOList.add(videoLecturesStatisticalVO);
				statisticalDetailVOList.add(statisticalDetailVO);
				numId++;
			}
		} catch (Exception e) {
			logger.error("Error ReportController.getICUMonitVOList",e);
		}
		results.put("summary", videoLecturesStatisticalVOList);
		results.put("detail", statisticalDetailVOList);
		return results;
	}
	
	/**
	 * 远程会诊参会情况统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ICUmonitExcel")
	public String ICUmonitExcel(Date startDate, Date endDate, HttpServletResponse response,HttpSession session) throws Exception{

		Excel excel = new Excel("icuMonitor.xls");
		excel.setCurrentSheet("重症监护参会情况汇总报表");

		UserInfo user = (UserInfo) session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" + "填报人："+ user.getName() +"\n" + "报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));
		
		Map<String, Object> data = this.getICUMonitVOList(startDate, endDate);
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = (List<VideoLecturesStatisticalVO>) data.get("summary");
		if(videoLecturesStatisticalVOList!=null&&!videoLecturesStatisticalVOList.isEmpty()){
			int rowNumber = 4;
			int intCount = videoLecturesStatisticalVOList.size();
			VideoLecturesStatisticalVO videoLecturesStatistical = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				videoLecturesStatistical=videoLecturesStatisticalVOList.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	videoLecturesStatistical.getNumId().toString());
				//视频日期
				excel.insert(rowNumber, coloumNumber++,	videoLecturesStatistical.getVideoDate());
				//提交病历单位
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getSubmitCompany().getName());
				//视频内容
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getVideoContent());
				//临床科室
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getClinicalDepartmentName());
				//会诊专家
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getExpertsName());
				//社区
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getCommunity());
				//听课人数
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getLecturesPeopleCount());
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}

		//明细
		List<StatisticalDetailVO> StatisticalDetailVOList = (List<StatisticalDetailVO>) data.get("detail");
		excel.setCurrentSheet("重症监护参会情况明细报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +"填报人："+ user.getName() +"\n" + "报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));
				
		if(StatisticalDetailVOList != null && !StatisticalDetailVOList.isEmpty()){
			int sheet2Row = 4;
			int intCount = StatisticalDetailVOList.size();
			StatisticalDetailVO statisticalDetailVO = null;
			for(int i=0; i < intCount; i++){
				statisticalDetailVO = StatisticalDetailVOList.get(i);
						int commsSize = statisticalDetailVO.getComms().size();
						CommAndCountVo commAndCountVo = null;
						for (int j = 0; j < commsSize; j++) {
							int coloumNumber = 1;
							commAndCountVo = statisticalDetailVO.getComms().get(j);
							//编号
							excel.insert(sheet2Row, coloumNumber++,	statisticalDetailVO.getNumId().toString());
							//视频日期
							excel.insert(sheet2Row, coloumNumber++,	statisticalDetailVO.getVideoDate());
							//提交病历单位
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getSubmitCompany().getName());
							//视频内容
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getVideoContent());
							//临床科室
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getClinicalDepartmentName());
							//会诊专家
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getExpertsName());
							//社区
							excel.insert(sheet2Row, coloumNumber++, commAndCountVo.getCommunity());
							//听课人数
							excel.insert(sheet2Row, coloumNumber++, commAndCountVo.getLecturesPeopleCount());
							//总人数
							excel.insert(sheet2Row, coloumNumber++, statisticalDetailVO.getCount());
							if (commsSize > 1 && j == (commsSize - 1)) {
								excel.autoMergeColumn(sheet2Row - j, 1);
								excel.autoMergeColumn(sheet2Row - j, 2);
								excel.autoMergeColumn(sheet2Row - j, 3);
								excel.autoMergeColumn(sheet2Row - j, 4);
								excel.autoMergeColumn(sheet2Row - j, 5);
								excel.autoMergeColumn(sheet2Row - j, 6);
								excel.autoMergeColumn(sheet2Row - j, 9);
							}

							if(i == (intCount - 1) && j == (commsSize - 1) ){
								break;
							}
							excel.createRow(++sheet2Row, --sheet2Row);
							sheet2Row++;
						}
					}
				}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("重症监护参会统计报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}
	
	/**
	 * 进入远程探视报表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("indexICUVisit")
	public String indexICUVisit()throws Exception{
		return "report/icuVisit_index";
	}
	/**
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param model
	 * @param type 1-明细；2-汇总
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ICUVisitPreview")
	public String ICUVisitPreview(Date startDate, Date endDate, Model model) throws Exception{
		Map<String, Object> data = this.getICUVisitVOList(startDate,endDate);
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = (List<VideoLecturesStatisticalVO>) data.get("summary");
	    model.addAttribute("summary", videoLecturesStatisticalVOList);
	    model.addAttribute("today", new Date());
		model.addAttribute("startDate", dateFormat.format(startDate));
		model.addAttribute("endDate", dateFormat.format(endDate));
		return "report/icuVisit_index";
	}
	
	//根据起止时间查询已经结束且被接受的远程会诊会议
		public Map<String, Object> getICUVisitVOList(Date startDate, Date endDate){
			Map<String, Object> results = new HashMap<String, Object>();
			try {
				List<Meeting> meetings = meetingService.getMeetingForReport(GlobalConstent.APPLICATION_TYPE_ICUVISIT,
						dateFormat.format(startDate), dateFormat.format(endDate));
				
				List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = new ArrayList<VideoLecturesStatisticalVO>();
				VideoLecturesStatisticalVO videoLecturesStatisticalVO = null;
				int numId = 1;
				if(meetings == null ){
					meetings = new ArrayList<Meeting>();
				}else{
					for (Meeting meeting : meetings) {
						videoLecturesStatisticalVO = new VideoLecturesStatisticalVO();
						//序号
						videoLecturesStatisticalVO.setNumId(numId);
						//编号
						videoLecturesStatisticalVO.setNumber(meeting.getId().toString());
						videoLecturesStatisticalVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
						videoLecturesStatisticalVO.setVideoContent(meeting.getTitle());
						videoLecturesStatisticalVO.setSubmitCompany(meeting.getCreator());//汇总中的病历提交单位
						videoLecturesStatisticalVO.setStartTime(DateUtil.getDateTimeStr(meeting.getStartTime()));
						videoLecturesStatisticalVO.setEndTime(DateUtil.getDateTimeStr(meeting.getEndTime()));
						numId++;
						videoLecturesStatisticalVOList.add(videoLecturesStatisticalVO);
					}
					results.put("summary", videoLecturesStatisticalVOList);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			return results;
		}
	/**
	 * 远程探视参会情况统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ICUvisitExcel")
	public String ICUvisitExcel(Date startDate, Date endDate, HttpServletResponse response,HttpSession session) throws Exception{

		Excel excel = new Excel("icuVisit.xls");
		excel.setCurrentSheet("远程探视情况汇总报表");

		UserInfo user = (UserInfo) session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" + "填报人："+ user.getName() +"\n" + "报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));
		
		Map<String, Object> data = this.getICUVisitVOList(startDate, endDate);
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = (List<VideoLecturesStatisticalVO>) data.get("summary");
		if(videoLecturesStatisticalVOList!=null&&!videoLecturesStatisticalVOList.isEmpty()){
			int rowNumber = 4;
			int intCount = videoLecturesStatisticalVOList.size();
			VideoLecturesStatisticalVO videoLecturesStatistical = null;
			for(int i=0;i<intCount;i++){
				int coloumNumber = 1;
				videoLecturesStatistical=videoLecturesStatisticalVOList.get(i);
				//编号
				excel.insert(rowNumber, coloumNumber++,	videoLecturesStatistical.getNumId().toString());
				excel.insert(rowNumber, coloumNumber++,	videoLecturesStatistical.getVideoDate());
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getSubmitCompany().getName());
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getVideoContent());
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getStartTime());
				excel.insert(rowNumber, coloumNumber++, videoLecturesStatistical.getEndTime());
				if(i==intCount-1){
					break;
				}
				excel.createRow(++rowNumber, --rowNumber);
				rowNumber++;
			}
		}
		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("远程探视情况汇总报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}
}
