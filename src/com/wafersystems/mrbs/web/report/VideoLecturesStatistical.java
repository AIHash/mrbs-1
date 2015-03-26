package com.wafersystems.mrbs.web.report;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.wafersystems.mrbs.vo.meeting.VideoMeetingApp;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.mrbs.web.report.vo.StatisticalDetailVO;
import com.wafersystems.mrbs.web.report.vo.StatisticalDetailVO.CommAndCountVo;
import com.wafersystems.mrbs.web.report.vo.VideoLecturesStatisticalVO;
import com.wafersystems.util.Excel;

@Controller
@RequestMapping("videoStati")
public class VideoLecturesStatistical {

	private static Logger logger = LoggerFactory.getLogger(VideoLecturesStatistical.class);
	private MeeAdmDbdService meetingService;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/**
	 * 进入视频讲座页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("index")
	public String index()throws Exception{
		return "report/video_index";
	}

	/**
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param model
	 * @param type 1-明细；2-汇总
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videoLecturesStatisticalPreview")
	public String videoLecturesStatisticalPreview(Date startDate, Date endDate, Model model, Integer type) throws Exception{
		Map<String, Object> data = this.getVideoLecturesStatisticalVOList(startDate, endDate);
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
		if(type == 1){//明细的预览页面
			model.addAttribute("summary", videoLecturesStatisticalVOList);
		} else if (type == 2){//汇总的预览页面
			model.addAttribute("detail", statisticalDetailVOList);
		}
		return "report/video_index";
	}

	/**
	 * 视频讲座参会情况统计报表
	 * @param startDate 开始统计时间
	 * @param endDate 结束统计时间
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/videoLecturesStatisticalExcel")
	public String videoLecturesStatisticalExcel(Date startDate, Date endDate, HttpServletResponse response,HttpSession session) throws Exception{

		Excel excel = new Excel("VideoLecturesStatements.xls");
		excel.setCurrentSheet("视频讲座参会情况汇总报表");
		
		UserInfo user = (UserInfo) session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +
				"填报人：" + user.getName() + "\n" +
				"报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));

		Map<String, Object> data = this.getVideoLecturesStatisticalVOList(startDate, endDate);
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
		excel.setCurrentSheet("视频讲座参会情况明细报表");
		excel.insert(2, 1, "填报日期：" + dateFormat.format(new Date()) + "\n" +
				"填报人：" + user.getName() + "\n" +
				"报表统计时间：" + dateFormat.format(startDate) + "至" + dateFormat.format(endDate));
		
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
						excel.autoMergeColumn(sheet2Row - j, 8);
					}

					if(i == intCount - 1 && j == (commsSize - 1)){
						break;
					}
					excel.createRow(++sheet2Row, --sheet2Row);
					sheet2Row++;
				}
			}
		}

		response.setContentType("application/x-msdownload;");
		response.setHeader("Content-disposition", "attachment; filename=" + new String("视频讲座参会统计报表_".getBytes("GBK"), "ISO8859-1")+dateFormat.format(new Date())+".xls");
		response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();
		return null;
	}

	//根据起止时间查询已经结束且被接受的视频讲座会议
	public Map<String,Object> getVideoLecturesStatisticalVOList(Date startDate, Date endDate){
		logger.debug("Error ReportController.getVideoLecturesStatisticalVOList");
		Map<String, Object> results = new HashMap<String, Object>();
		
		List<VideoLecturesStatisticalVO> videoLecturesStatisticalVOList = new ArrayList<VideoLecturesStatisticalVO>();
		List<StatisticalDetailVO> statisticalDetailVOList = new ArrayList<StatisticalDetailVO>();//明细
		try{
			List<Meeting> meetings = meetingService.getMeetingForReport(GlobalConstent.APPLICATION_TYPE_VIDEOLECTURES,
					dateFormat.format(startDate), dateFormat.format(endDate));
			VideoLecturesStatisticalVO videoLecturesStatisticalVO = null;
			StatisticalDetailVO statisticalDetailVO = null;
			//社区个数
			Integer intCommunityCount = 0;
			//参会人数
			Integer intPeopleCount = 0;
			//视频讲座报表序号
			Integer videoNumId = 1;

			for (Meeting meeting : meetings) {
				videoLecturesStatisticalVO = new VideoLecturesStatisticalVO();
				statisticalDetailVO = new StatisticalDetailVO();
				//序号
				videoLecturesStatisticalVO.setNumId(videoNumId);
				statisticalDetailVO.setNumId(videoNumId);
				//编号
				videoLecturesStatisticalVO.setNumber(meeting.getId().toString());
				statisticalDetailVO.setNumber(meeting.getId().toString());
				//视频日期
				videoLecturesStatisticalVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
				statisticalDetailVO.setVideoDate(dateFormat.format(meeting.getStartTime()));
				//视频内容
				videoLecturesStatisticalVO.setVideoContent(meeting.getContent());
				statisticalDetailVO.setVideoContent(meeting.getContent());
				
				//视频讲座VO
				VideoMeetingApp videoMeetingApp = meeting.getVideoapplicationId();
				if(videoMeetingApp!=null){
					//临床科室
					if(videoMeetingApp.getDeptName()!=null){
						videoLecturesStatisticalVO.setClinicalDepartmentName(videoMeetingApp.getDeptName().getName());
						statisticalDetailVO.setClinicalDepartmentName(videoMeetingApp.getDeptName().getName());
					}
					//会诊专家
					if(videoMeetingApp.getUserName()!=null){
						videoLecturesStatisticalVO.setExpertsName(videoMeetingApp.getUserName().getName());
						statisticalDetailVO.setExpertsName(videoMeetingApp.getUserName().getName());
					}
				}
				//社区个数
				intCommunityCount = 0;
				//参会人数
				intPeopleCount = 0;
				
				StatisticalDetailVO.CommAndCountVo commAndCountVo = null;
				List<StatisticalDetailVO.CommAndCountVo> commAndCountVoList = new ArrayList<StatisticalDetailVO.CommAndCountVo>();
				
				//是否存在共同体标记，不存在为true，存在为false
				boolean flag=true;				
				for (MeetingMember meetingMember : meeting.getMembers()){
					//判断是否存在共同体
					if(meetingMember.getAttendState() != null && meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)
							&&meetingMember.getMember()!=null&&meetingMember.getMember().getUserType()!=null
							&&meetingMember.getMember().getUserType().getValue()!=null
							&&meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION)){
						flag=false;
						break;
					}
				}
				
				//会议成员
				for (MeetingMember meetingMember : meeting.getMembers()){
					if(meetingMember.getAttendState() != null && meetingMember.getAttendState().equals(GlobalConstent.APPLICATION_STATE_ACCEPT)
							&&meetingMember.getMember()!=null&&meetingMember.getMember().getUserType()!=null
							&&meetingMember.getMember().getUserType().getValue()!=null){
						//判断是否为共同体 
						if(meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_UNION)){
							intCommunityCount+=1;
							if(meetingMember.getAttendNo()!= null){
								intPeopleCount+=Integer.valueOf(meetingMember.getAttendNo());
								//共同体数据
								commAndCountVo = new StatisticalDetailVO.CommAndCountVo();
								commAndCountVo.setCommunity(meetingMember.getMember().getName());
								commAndCountVo.setLecturesPeopleCount(meetingMember.getAttendNo().toString());
								commAndCountVoList.add(commAndCountVo);
							}

						}else if(flag&&meetingMember.getMember().getUserType().getValue().equals(GlobalConstent.USER_TYPE_PROFESSIONAL)){
							commAndCountVo = new StatisticalDetailVO.CommAndCountVo();
							commAndCountVo.setCommunity("");
							commAndCountVo.setLecturesPeopleCount("0");
							commAndCountVoList.add(commAndCountVo);
						}
						
					}
				}

				statisticalDetailVO.setComms(commAndCountVoList);

				//社区
				videoLecturesStatisticalVO.setCommunity(intCommunityCount.toString());
				//听课人数
				videoLecturesStatisticalVO.setLecturesPeopleCount(intPeopleCount.toString());
				statisticalDetailVO.setCount(intPeopleCount.toString());
				videoLecturesStatisticalVOList.add(videoLecturesStatisticalVO);
				
				statisticalDetailVOList.add(statisticalDetailVO);
				videoNumId++;
				
			}
		} catch (Exception e) {
			logger.error("Error ReportController.getVideoLecturesStatisticalVOList",e);
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
}
