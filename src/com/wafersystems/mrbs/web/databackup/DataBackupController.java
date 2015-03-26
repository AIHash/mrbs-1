package com.wafersystems.mrbs.web.databackup;

import java.io.FileNotFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.wafersystems.mrbs.service.DataBackupService;
import com.wafersystems.util.DateUtil;
import com.wafersystems.util.StrUtil;

@Controller
@RequestMapping("data")
public class DataBackupController {

	private static Logger logger = LoggerFactory.getLogger(DataBackupController.class);
	private DataBackupService backupService;

	@RequestMapping(value="index", method = RequestMethod.GET)
	public String index(HttpServletRequest request)throws Exception{
		String contextPath = request.getServletContext().getRealPath("/");
		contextPath = contextPath.substring(0, contextPath.indexOf("\\") + 1);
		request.setAttribute("backupPath", contextPath);
		return "data/index";
	}

	@RequestMapping(value="tip", method = RequestMethod.POST)
	public String next(String backupPath, HttpServletRequest request) throws Exception{
		if(!StrUtil.isEmptyStr(backupPath)){
			backupPath = backupPath.trim();
		}
		String accFilePath = request.getServletContext().getRealPath("/download");
		//可用空间
		long freeSpace = backupService.getFreeSpace(backupPath);
		//备份文件大小
		long backupFileSize = backupService.getBackupDataSize(accFilePath);

		if(freeSpace == -1L){//
			request.setAttribute("warning", -1);
		}else if((freeSpace - backupFileSize) < 500){//备份空间小于500M时提示用户
			request.setAttribute("warning", 0);
		}

		request.setAttribute("backupPath", backupPath);
		request.setAttribute("dataBackupFileName", "mrbs_"+ DateUtil.getCurrentDate().replace('-', '_') + "_" + DateUtil.getCurrentTime().replace(':', '_') + ".zip");
		request.setAttribute("dataBackupFileSize", backupService.getBackupDataSize(accFilePath));

		return "data/dataBackupInfo";
	}

	@RequestMapping(value="backup")
	public String backup(String dataBackupFileName,String backupPath, HttpServletResponse response, HttpServletRequest request) throws Exception{
		response.setCharacterEncoding("UTF-8");
		try {
			boolean isSuccess = backupService.backup(backupPath, dataBackupFileName, request.getSession().getServletContext().getRealPath("/"));
			if(isSuccess){
				logger.debug("backup succ.");
				response.getWriter().write("{\"state\":\"success\"}");
			}else{
				logger.error("backup failed.");
				response.getWriter().write("{\"state\":\"failed\"}");
			}
		} catch (FileNotFoundException e) {
			logger.error("backup disk noexist, backup failed.",e);
			response.getWriter().write("{\"state\":\"notExist\"}");
		} catch (Exception e) {
			logger.error("backup failed.",e);
			response.getWriter().write("{\"state\":\"failed\"}");
		}
		return null;
	}

	@Resource
	public void setBackupService(DataBackupService backupService) {
		this.backupService = backupService;
	}

}