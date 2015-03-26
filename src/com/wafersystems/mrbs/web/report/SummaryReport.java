package com.wafersystems.mrbs.web.report;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.service.meeadmdashbaord.MeeAdmDbdService;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.Excel;

@Controller
@RequestMapping("/summary")
public class SummaryReport {

	private MeeAdmDbdService meetingService;

	@RequestMapping(value="/report")	//汇总
	public String summary(String type, Integer category, Date startDate, Date endDate,
			Model model,HttpServletResponse response)
			throws Exception {

		String title = "", column = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String header = "填报日期:" + dateFormat.format(new Date()) + "\n"
//				+ "填报人:会议管理员" + "\n" + "报表统计时间:" + dateFormat.format(startDate)
//				+ "至" + dateFormat.format(endDate);
				+ "填报人:会议管理员" + "\n" + "报表统计时间:" + dateFormat.format(dateFormat.parse("2011-07-01"))
				+ "至" + dateFormat.format(dateFormat.parse("2011-07-31"));

		Map<String, Long> data = meetingService.summary2Report(category, dateFormat.parse("2011-07-01"), dateFormat.parse("2011-07-31"));
		switch (category) {
		case 1:// 会诊类型
			title = "会诊类型分类汇总统计";
			column = "会诊类型";
			break;

		case 2:// 科室
			title = "科室分类汇总统计";
			column = "会诊科室";
			break;

		case 3:// 专家
			title = "专家分类汇总统计";
			column = "专家姓名";
			break;
		}

		if(type != null && type.equals("preview")){
			model.addAttribute("title", title);
			model.addAttribute("column", column);
			model.addAttribute("data", data);
			model.addAttribute("today", new Date());
			model.addAttribute("startDate", startDate);
			model.addAttribute("endDate", endDate);
			
			return "meetingadmin/summary_report";
		}

		Excel excel = new Excel("SummaryReportSample.xls");
		excel.setCurrentSheet(1);
		excel.insert(1, 1, title);

		excel.insert(2, 1, header);

		excel.insert(4, 2, column);

		int rowNum = 5;
		int columNum = 1;
		int index = 1;
		for (String key : data.keySet()) {
			excel.insert(rowNum, columNum, String.valueOf(index++));
			excel.insert(rowNum, ++columNum, key);
			excel.insert(rowNum, ++columNum, String.valueOf(data.get(key)));
			excel.createRow(++rowNum, --rowNum);
			rowNum++;
			columNum = 1;
		}

		response.setContentType("Application/Octet-Stream");
		response.setHeader("Content-Disposition",
				"attachment; filename=\"" + new String("summary".getBytes(), "iso-8859-1") + "_" + DateUtil.getCurrentDate() + ".xls\"");

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
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				dateFormat, false));
	}
}
