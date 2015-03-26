package com.wafersystems.mrbs.web.log;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.SystemOperationLogService;
import com.wafersystems.mrbs.service.user.UserService;
import com.wafersystems.mrbs.vo.SystemOperationLog;
import com.wafersystems.mrbs.web.criteriavo.AutoCompleteItem;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping(value="/log/operation")
public class OperationLogController {

	private static final Logger logger = LoggerFactory.getLogger(OperationLogController.class);

	private SystemOperationLogService systemOperationLogService;
	private UserService userService;

	@RequestMapping(value="/init", method=RequestMethod.GET)
	public String init(Model model) {
		logger.debug("进入日志查询");
		model.addAttribute("SYSTEM_CURRENT_DATE", DateUtil.getCurrentDate());
		return "log/operation/index";
	}

	@RequestMapping(value="/query")
	public String query(HttpServletRequest request, HttpSession session, Model model) {
		List<SystemOperationLog> datas;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date= new Date();
		Date startDate = DateUtil.getDate(dateFormat.format(date));
		Date endDate = DateUtil.getDate(dateFormat.format(date));
		Boolean result = null;
		//user
		String userId = request.getParameter("userId");

		//start_date
		String start_date = request.getParameter("startDate");
		if(!StrUtil.isEmptyStr(start_date)){
			startDate = DateUtil.getDate(start_date);
		}
		//end_date
		String end_date = request.getParameter("endDate");
		if(!StrUtil.isEmptyStr(end_date)){
			endDate = DateUtil.getDate(end_date);
		}
		//end_date
		String state = request.getParameter("state");
		if(!StrUtil.isEmptyStr(state)){
			result = GlobalConstent.SYSTEM_LOG_SUCCESS == new Integer(state);
		}
		PageSortModel psm = new PageSortModel(request); //eXtremeTable
		datas = systemOperationLogService.getSystemOperationLogList(psm, userId, startDate, endDate, result);
		session.setAttribute(GlobalConstent.REPORT_DATA, datas);
		return "log/operation/list";
	}

	@RequestMapping("/searchUser")
	@ResponseBody
	public List<AutoCompleteItem> searchUser(String term, HttpServletResponse response) throws Throwable{
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		List<AutoCompleteItem> list = userService.getUserListByUserName(term);
		if(list == null || list.size() == 0){
			list = Arrays.asList(new AutoCompleteItem("无法匹配:" + term, "-1"));
		}
		return list;
	}

	@Resource(type = SystemOperationLogService.class)
	public void setSystemOperationLogService(SystemOperationLogService systemOperationLogService) {
		this.systemOperationLogService = systemOperationLogService;
	}

	@Resource
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
}