package com.wafersystems.mrbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.GlobalConstent;
import com.wafersystems.mrbs.dao.SystemServiceLogDao;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.service.SystemServiceLogService;
import com.wafersystems.mrbs.vo.SystemServiceLog;
import com.wafersystems.util.DateUtil;

@Service("serviceLogService")
public class SystemServiceLogServiceImpl implements SystemServiceLogService {

	private SystemServiceLogDao dao;

	@Override
	public void saveSystemServiceLog(SystemServiceLog vo) {
		dao.save(vo);
	}

	@Override
	public void updateSystemServiceLog(SystemServiceLog vo) {
		dao.update(vo);
	}

	@Override
	public void delSystemServiceLog(SystemServiceLog vo) {
		dao.delete(vo);

	}

	@Override
	public SystemServiceLog getSystemServiceLogById(int id) {
		return dao.get(id);
	}

	@Override
	public List<SystemServiceLog> getSystemOperationLogList(PageSortModel psm,
			SystemServiceLog serviceLog, Date startDate, Date endDate) {
		String wherejpql = "";
		List<Object> tempParams = new ArrayList<Object>(4);
		if (StringUtils.isNotBlank(serviceLog.getObjectId())
				&& !GlobalConstent.SERVICE_ALL.equals(serviceLog.getObjectId())) {
			wherejpql += " and objectId = ? ";
			tempParams.add(serviceLog.getObjectId());
		}

		if (startDate != null) {
			wherejpql += " and createTime >= ? ";
			tempParams.add(startDate);
		}

		if (endDate != null) {
			wherejpql += " and createTime < ? ";
			tempParams.add(DateUtil.addSeconds(endDate, 24 * 60 * 60));
		}

		if (serviceLog.getResult() != null  && serviceLog.getResult() != GlobalConstent.SYSTEM_LOG_ALL) {
			wherejpql += " and result = ? ";
			tempParams.add(serviceLog.getResult());
		}

		Object[] queryParams = tempParams.toArray();
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("createTime", "desc");
		return dao.getScrollData(psm, wherejpql, queryParams, orderby)
				.getResultlist();
	}

	@Resource(type = SystemServiceLogDao.class)
	public void setSystemServiceLogDao(SystemServiceLogDao dao) {
		this.dao = dao;
	}
}