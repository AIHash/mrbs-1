package com.wafersystems.mrbs.web.report;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.Excel;

@Controller
@RequestMapping("detail")
@Deprecated
public class DetailReport {

	private static Logger logger = LoggerFactory.getLogger(DetailReport.class);
	private MeeAdmDbdService meetingService;

	@RequestMapping("report")	//明细
	public String summary(String type, Integer category, Date startDate, Date endDate, 
			Model model, HttpServletResponse response)throws Exception {

		String title = "", column = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String header = "填报日期:" + dateFormat.format(new Date()) + "\n"
				+ "填报人:会议管理员" + "\n" + "报表统计时间:" + dateFormat.format(startDate)
				+ "至" + dateFormat.format(endDate);
//				+ "填报人:会议管理员" + "\n" + "报表统计时间:" + dateFormat.format(dateFormat.parse("2011-07-01"))
//				+ "至" + dateFormat.format(dateFormat.parse("2011-07-31"));

		//Map<String, List<Meeting>> data = meetingService.detail2Report(category, dateFormat.parse("2011-07-01"), dateFormat.parse("2011-07-31"));
		Map<String, List<Meeting>> data = meetingService.detail2Report(category, dateFormat.parse("2011-01-01"), dateFormat.parse("2011-10-31"));
		System.out.println(data.size());
		switch (category) {
		case 1:// 会诊类型
			title = "会诊类型分类明细统计";
			column = "会诊类型";
			break;

		case 2:// 科室
			title = "科室分类明细统计";
			column = "会诊科室";
			break;

		case 3:// 专家
			title = "专家分类明细统计";
			column = "专家姓名";
			break;
		}

		if(type.equals("preview")){
			model.addAttribute("title", title);
			model.addAttribute("column", column);
			model.addAttribute("data", data);
			model.addAttribute("today", new Date());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			return "meetingadmin/detail_report";
		}

		Excel excel = new Excel("DetailedReportsSample.xls");
		excel.setCurrentSheet(1);
		excel.insert(1, 1, title);

		excel.insert(2, 1, header);

		excel.insert(4, 2, column);

		int index = 1;
		int rowNum = 5;
		for (String key : data.keySet()) {
			int columNum = 1;
			for (Meeting meeting : data.get(key)) {
				excel.insert(rowNum, columNum, String.valueOf(index++));							//1
				excel.insert(rowNum, ++columNum, key);												//2
				excel.insert(rowNum, ++columNum, dateFormat.format(meeting.getStartTime()));		//3
				excel.insert(rowNum, ++columNum, meeting.getTitle());								//4
				excel.insert(rowNum, ++columNum, String.valueOf(getMeetingMemberNum(meeting)));		//5
				
				excel.createRow(++rowNum, --rowNum);
				rowNum++;
				columNum = 1;
			}
		}

		excel.autoMergeColumn(5, 2);

		response.setContentType("application/x-msdownload;");
        response.setHeader("Content-disposition", "attachment; filename=" + new String("明细报表样例".getBytes("GBK"),"ISO8859-1") + "_" + DateUtil.getCurrentDate() + ".xls\""); 
        response.setHeader("Connection", "close");

		OutputStream os = response.getOutputStream();
		excel.getContent().write(os);
		os.flush();
		os.close();

		return null;
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

	private int getMeetingMemberNum(Meeting m){
		int num = 0;
		for (MeetingMember mm : m.getMembers()) {
			if(mm.getAttendState() == GlobalConstent.APPLICATION_STATE_ACCEPT){
				num += mm.getAttendNo();
			}
		}
		return num;
	}
}
