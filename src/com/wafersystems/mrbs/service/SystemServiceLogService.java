package com.wafersystems.mrbs.service;

import java.util.Date;
import java.util.List;

import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.vo.SystemServiceLog;

public interface SystemServiceLogService {

	public void saveSystemServiceLog(SystemServiceLog vo);

	public void updateSystemServiceLog(SystemServiceLog vo);

	public void delSystemServiceLog(SystemServiceLog vo);
	
	public SystemServiceLog getSystemServiceLogById(int id);

	public List<SystemServiceLog> getSystemOperationLogList(PageSortModel psm, SystemServiceLog serviceLog, Date startDate, Date endDate);
}
