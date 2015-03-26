package com.wafersystems.mrbs.web.param;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.wafersystems.mrbs.exception.UpdateFailedException;
import com.wafersystems.mrbs.listener.BaseDataServiceListener;
import com.wafersystems.mrbs.service.param.ParamPackageService;
import com.wafersystems.mrbs.tag.MessageTag;
import com.wafersystems.mrbs.vo.param.ParamPackage;
import com.wafersystems.mrbs.vo.param.UnifiedParameter;

@Controller
@RequestMapping(value="/system/param")
public class ParameterController {

	private static final Logger logger = LoggerFactory.getLogger(ParameterController.class);

	private ParamPackageService paramPackageService;
	private BaseDataServiceListener baseDataServiceListener;

	@RequestMapping(value="/edit", method=RequestMethod.GET)
	public String edit(HttpSession session) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		logger.debug("进入系统参数配置");
		List<ParamPackage> datas = paramPackageService.getAll();
		Set<UnifiedParameter> temp;
		for (ParamPackage paramPackage : datas) {
			for (UnifiedParameter unifiedParameter : paramPackage.getSubParameter()) {
				if(unifiedParameter.getParamKey().equals("SMS-DB-PWD") || unifiedParameter.getParamKey().equals("SYNC-DATA-DB-PWD")){
					unifiedParameter.setValue(new String(decoder.decodeBuffer(unifiedParameter.getValue())));
				}
			}
			temp = new TreeSet<UnifiedParameter>(paramPackage.getSubParameter());
			paramPackage.setSubParameter(temp);
		}
		session.setAttribute("ParamPackage", datas);
		return "param/edit";
	}

	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(HttpServletRequest request, HttpSession session,HttpServletResponse response) {
		response.setCharacterEncoding("utf-8");
		BASE64Encoder encoder = new BASE64Encoder();
		try {
			Map<String,ParamPackage> datas = new HashMap<String, ParamPackage>();
			Enumeration<String> en = request.getParameterNames();
			while (en.hasMoreElements()) {
				String name = en.nextElement();
				String value = request.getParameter(name);
				if (name.contains("_")) {
					String[] pairs = name.split("_");
					if (!datas.containsKey(pairs[0])) {
						datas.put(pairs[0], paramPackageService.getParamPackageById(pairs[0]));
					}
					if (datas.get(pairs[0]).getSubParameter() != null) {
						Iterator<UnifiedParameter> it = datas.get(pairs[0]).getSubParameter().iterator();
						while (it.hasNext()) {
							UnifiedParameter up = it.next();
							if (up.getParamKey().equals(pairs[1])) {
								if(up.getParamKey().equals("SMS-DB-PWD") || up.getParamKey().equals("SYNC-DATA-DB-PWD")){
									value = encoder.encode(value.getBytes());
								}
								up.setValue(value);
								break;
							}
						}
					}
				}
			}
			paramPackageService.updateParamPackageBatch(datas.values());
			baseDataServiceListener.loadPara();
			response.getWriter().write("check_successful");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			if(e instanceof UpdateFailedException){
				session.setAttribute("message", MessageTag.getMessage("comm.failedexception","admin.system_param_update"));
			}else{
				session.setAttribute("message", MessageTag.getMessage("comm.operation_result_failure"));
			}
			 try {
				response.getWriter().write("check_failed");
			 } catch (IOException e1) {
			 }
			return null;
		} 
	}

	@Resource(type = ParamPackageService.class)
	public void setParamPackageService(ParamPackageService paramPackageService) {
		this.paramPackageService = paramPackageService;
	}

	@Resource(name = "initListener")
	public void setBaseDataServiceListener(
			BaseDataServiceListener baseDataServiceListener) {
		this.baseDataServiceListener = baseDataServiceListener;
	}
	
}

