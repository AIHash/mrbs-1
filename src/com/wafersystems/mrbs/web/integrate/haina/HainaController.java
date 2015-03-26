package com.wafersystems.mrbs.web.integrate.haina;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axis2.AxisFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.integrate.haina.facade.HainaCall;
import com.wafersystems.mrbs.vo.user.UserInfo;

@Controller
@RequestMapping("integrate")
public class HainaController {

	private final static Logger logger = LoggerFactory.getLogger(HainaController.class);
	private HainaCall hainaCall;
	
	@RequestMapping("index")
	public String index()throws Exception{
		return "integrate/haina_index";
	}

	@RequestMapping("query")
	public String queryData(Date startDate, Date endDate, HttpServletRequest request, HttpServletResponse response) throws IOException{
		try{
			UserInfo user = (UserInfo)request.getSession().getAttribute(GlobalConstent.USER_LOGIN_SESSION);
	
			String json = hainaCall.searchStudy(user.getUserId(), startDate, endDate);
			if(json!= null)
				response.getWriter().write("{\"msg\":" + json + "}");
			else
				response.getWriter().write("{\"msg\":\"nodata\"}");

		}catch(AxisFault e){
			logger.error("连接海纳服务器出错", e);
			response.getWriter().write("{\"msg\":\"error\"}");
		} catch (Exception e) {
			
		}
		return null;
	}

	@Resource
	public void setHainaCall(HainaCall hainaCall) {
		this.hainaCall = hainaCall;
	}

	@InitBinder
	protected void initBinder(WebDataBinder dataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		dataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	}

}
