package com.wafersystems.mrbs.service;

import java.util.Date;
import java.util.List;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.LogFailedException;
import com.wafersystems.mrbs.vo.SystemOperationLog;

public interface SystemOperationLogService {

	public void saveSystemOperationLog(SystemOperationLog vo) throws LogFailedException;

	public void delSystemOperationLog(SystemOperationLog vo);
	
	public SystemOperationLog getSystemOperationLogById(int id);
	
	public List<SystemOperationLog> getSystemOperationLogList(PageSortModel psm, String userId, Date startDate, Date ednDate, Boolean result);

}