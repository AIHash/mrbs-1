package com.wafersystems.mrbs.web.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.util.DateUtil;

@Controller
@RequestMapping(value = "/log/service")
public class ServiceLogController {

	private static final Logger logger = LoggerFactory.getLogger(OperationLogController.class);

	private SystemServiceLogService logService;

	@RequestMapping(value = "/init", method = RequestMethod.GET)
	public String init(Model model, HttpServletRequest request) {
		logger.debug("进入服务日志查询");
		model.addAttribute("SYSTEM_LOG_SUCCESS", GlobalConstent.SYSTEM_LOG_SUCCESS);
		model.addAttribute("SYSTEM_LOG_FAILED",	GlobalConstent.SYSTEM_LOG_FAILED);
		model.addAttribute("SYSTEM_LOG_ALL", GlobalConstent.SYSTEM_LOG_ALL);
		model.addAttribute("SERVICE_LOG_TYPE", GlobalParam.serviceDesc);
		model.addAttribute("SERVICE_ALL", GlobalConstent.SERVICE_ALL);
		model.addAttribute("SYSTEM_CURRENT_DATE", DateUtil.getCurrentDate());

		return "log/service/index";
	}

	@RequestMapping(value = "/query")
	public String query(SystemServiceLog serviceLog, Date startDate,
			Date endDate, HttpServletRequest request, Model model) {
		PageSortModel psm = new PageSortModel(request); // eXtremeTable
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if(startDate==null||startDate.equals("")){			
			Date date= new Date();
			startDate = DateUtil.getDate(dateFormat.format(date));
			endDate = DateUtil.getDate(dateFormat.format(date));
		}
		List<SystemServiceLog> datas = logService.getSystemOperationLogList(psm, serviceLog, startDate, endDate);

		model.addAttribute("SERVICE_LOG_TYPE", GlobalParam.serviceDesc);
		request.setAttribute(GlobalConstent.REPORT_DATA, datas);
		return "log/service/list";
	}

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

	@Resource(type = SystemServiceLogService.class)
	public void setLogService(SystemServiceLogService logService) {
		this.logService = logService;
	}
}