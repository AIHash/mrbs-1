package com.wafersystems.mrbs.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.wafersystems.mrbs.dao.SystemOperationLogDao;
import com.wafersystems.mrbs.dao.base.PageSortModel;
import com.wafersystems.mrbs.exception.LogFailedException;
import com.wafersystems.mrbs.service.SystemOperationLogService;
import com.wafersystems.mrbs.vo.SystemOperationLog;
import com.wafersystems.util.DateUtil;

@Service
public class SystemOperationLogServiceImpl implements SystemOperationLogService {
	
	Logger logger = LoggerFactory.getLogger(SystemOperationLogService.class);

	private SystemOperationLogDao dao;

	@Override
	public void saveSystemOperationLog(SystemOperationLog vo)throws LogFailedException {
		try{
			dao.save(vo);
		}catch(Exception e){
			logger.error("记录系统操作日志异常", e);
			throw new LogFailedException();
		}
	}

	@Override
	public void delSystemOperationLog(SystemOperationLog vo) {
		dao.delete(vo);

	}
	
	@Override
	public SystemOperationLog getSystemOperationLogById(int id){
		return dao.get(id);
	}
	
	@Override
	public List<SystemOperationLog> getSystemOperationLogList(PageSortModel psm, String userId, Date startDate, Date endDate, Boolean result){
		String wherejpql = "";
		List<Object> tempParams = new ArrayList<Object>(4);
		if(userId != null){
			wherejpql += " and o.user.name like ?";
			tempParams.add("%" + userId + "%");
		}
		if(startDate != null){
			wherejpql += " and logDate >= ?";
			tempParams.add(startDate);
		}
		if(endDate != null){
			wherejpql += " and logDate < ?";
			tempParams.add(DateUtil.addSeconds(endDate, 24*60*60));
		}
		if(result != null){
			wherejpql += " and result = ?";
			tempParams.add(result);
		}
		Object[] queryParams = tempParams.toArray();
		LinkedHashMap<String, Object> orderby = new LinkedHashMap<String, Object>();
		orderby.put("logDate", "desc");
		return dao.getScrollData(psm, wherejpql, queryParams, orderby).getResultlist();
	}

	@Resource(type = SystemOperationLogDao.class)
	public void setSystemOperationLogDao(SystemOperationLogDao dao) {
		this.dao = dao;
	}

}