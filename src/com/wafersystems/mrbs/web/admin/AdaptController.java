package com.wafersystems.mrbs.web.admin;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.GlobalParam;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.Module;
import com.wafersystems.mrbs.vo.right.Function;
import com.wafersystems.mrbs.vo.user.Department;
import com.wafersystems.mrbs.vo.user.UserInfo;
import com.wafersystems.util.DateUtil;

@Controller
@RequestMapping(value = "/index")
public class AdaptController {

	private static final Logger logger = LoggerFactory.getLogger(AdaptController.class);

	@RequestMapping(value = "/")
	public String frame(HttpSession session) {
		UserInfo user = (UserInfo) session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		session.setAttribute("userName", user.getName());
		Department dept = user.getDeptId();
		session.setAttribute("department", dept == null ? "" : dept.getName());
		String time = DateUtil.getCurrentTime();
		session.setAttribute("loginTime", time);
		logger.debug("Admin [" + user.getName() + "] login at time [" + time + "]");
		return "admin/frame";
	}

	@RequestMapping(value = "/title")
	public String title() {
		return "admin/title";
	}

	@RequestMapping(value = "/blank")
	public String blank() {
		return "admin/blank";
	}

	@RequestMapping(value = "/splitbar")
	public String splitbar() {
		return "admin/splitbar";
	}

	@RequestMapping(value = "/right")
	public String right() {
		return "admin/right";
	}

	@RequestMapping(value = "/mainFrames")
	public String mainFrames() {
		return "admin/mainFrames";
	}

	@RequestMapping(value = "/left")
	public String left(HttpSession session,HttpServletRequest request) throws UnsupportedEncodingException {
		UserInfo user = (UserInfo) session.getAttribute(GlobalConstent.USER_LOGIN_SESSION);
		Set<Function> functions = user.getRole().getFunctions();
		Iterator<Function> it = functions.iterator();
		List<Module> modules = new ArrayList<Module>();
		while (it.hasNext()) {
			Function fun = it.next();
			Module mod = new Module();
			mod.setId(fun.getId().toString());
			mod.setName(MessageTag.getMessage(session, fun.getName()));
			mod.setNodeNo(fun.getNodeNo());
			mod.setParentNodeNo(fun.getParentNodeNo());
			mod.setUrl(fun.getUrl());
			mod.setImageUrl(fun.getImageUrl());
			mod.setSwapUrl(fun.getSwapUrl());
			modules.add(mod);
		}
		session.setAttribute("user", user);
		session.setAttribute("encodeName", URLEncoder.encode(user.getName(), "utf-8"));
		String hanaUrl = getHanaUrl(request);
		logger.info("hana Url"+hanaUrl);
		session.setAttribute("HAINA_URL", hanaUrl.substring(hanaUrl.lastIndexOf("//") + 2));
		session.setAttribute("modules", modules);
		return "admin/left";
	}
	
	public String getHanaUrl(HttpServletRequest request){
		String requestUrl = request.getRequestURL().toString();
		String urlWasPort = requestUrl.split("/")[2];
		String urlWas = urlWasPort.split(":")[0];
		logger.info("Out net IP"+urlWas);
		if(GlobalConstent.SYSTEM_OUT_NET.equals(urlWas)){
			return GlobalParam.haina_url_outnet;
		}else{
			return GlobalParam.haina_url;
		}
	}

}
